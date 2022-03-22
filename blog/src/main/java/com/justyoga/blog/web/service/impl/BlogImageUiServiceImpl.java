package com.justyoga.blog.web.service.impl;

import com.justyoga.blog.cache.BucketVsUrl;
import com.justyoga.blog.domain.model.mysql.Blog;
import com.justyoga.blog.domain.model.mysql.BlogImage;
import com.justyoga.blog.service.interfaces.BlogImageService;
import com.justyoga.blog.service.interfaces.BlogService;
import com.justyoga.blog.web.service.interfaces.BlogImageUiService;
import com.justyoga.client.library.MediaClient;
import com.justyoga.util.dto.blog.BlogImageDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BlogImageUiServiceImpl implements BlogImageUiService {

    private final BlogImageService blogImageService;
    private final BlogService blogService;
    private final MediaClient mediaClient;
    private final BucketVsUrl bucketVsUrl;
    private final ModelMapper modelMapper;

    @Autowired
    public BlogImageUiServiceImpl(
            BlogImageService blogImageService,
            BlogService blogService,
            MediaClient mediaClient,
            BucketVsUrl bucketVsUrl,
            ModelMapper modelMapper) {
        this.blogImageService = blogImageService;
        this.blogService = blogService;
        this.mediaClient = mediaClient;
        this.bucketVsUrl = bucketVsUrl;
        this.modelMapper = modelMapper;
    }

    private BlogImageDTO convert(BlogImage entity) {
        var blogImageDTO = modelMapper.map(entity, BlogImageDTO.class);
        blogImageDTO.setUrl(getUrl(entity.getUrl()));
        return blogImageDTO;
    }

    private BlogImage convert(BlogImageDTO dto) {
        return modelMapper.map(dto, BlogImage.class);
    }

    @Override
    public BlogImageDTO findById(UUID id) {
        BlogImage blogImage = blogImageService.findById(id);
        if (blogImage == null) throw new AppException("Image not found", AppStatusCode.NOT_FOUND);
        return convert(blogImage);
    }

    @Override
    public List<BlogImageDTO> findAllByBlogId(UUID id) {
        return blogImageService.findAllByBlogId(id).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(UUID id, UUID currentUser) {
        if (currentUser == null)
            throw new AppException("Current user is required", AppStatusCode.UNAUTHORIZED_ERROR);
        if (id == null)
            throw new AppException("Blog id is required", AppStatusCode.INVALID_REQUEST);
        var blogImage = blogImageService.findById(id);
        if (blogImage == null) throw new AppException("Image not found", AppStatusCode.NOT_FOUND);
        Blog blogDTO = blogService.findById(blogImage.getBlogId());
        if (blogDTO == null) throw new AppException("Blog not found", AppStatusCode.NOT_FOUND);
        if (!currentUser.equals(blogDTO.getUserId())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return blogImageService.delete(id);
    }

    @Override
    public BlogImageDTO save(String image, UUID blogId, UUID currentUser) {
        if (currentUser == null)
            throw new AppException("Current user is required", AppStatusCode.UNAUTHORIZED_ERROR);
        if (image == null)
            throw new AppException("Image is required", AppStatusCode.INVALID_REQUEST);
        Blog blogDTO = blogService.findById(blogId);
        if (blogDTO == null) throw new AppException("Blog not found", AppStatusCode.NOT_FOUND);
        if (!currentUser.equals(blogDTO.getUserId())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        BlogImage save = blogImageService.save(convert(new BlogImageDTO(image, blogId)));
        return convert(save);
    }

    private String getUrl(String bucketName) {
        if (bucketName == null) return null;
        String url = bucketVsUrl.get(bucketName);
        if (url == null) {
            ResponseEntity<BaseResponse<String>> coverUrlResponse =
                    mediaClient.getUrl(bucketName, 1L, TimeUnit.DAYS);
            if (coverUrlResponse.getStatusCode() == HttpStatus.OK
                    && coverUrlResponse.getBody() != null
                    && coverUrlResponse.getBody().getStatus() == Status.SUCCESS) {
                url = coverUrlResponse.getBody().getData();
                bucketVsUrl.put(bucketName, url);
            }
        }
        return url;
    }
}
