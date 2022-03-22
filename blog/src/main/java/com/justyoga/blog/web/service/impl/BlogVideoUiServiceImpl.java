package com.justyoga.blog.web.service.impl;

import com.justyoga.blog.cache.BucketVsUrl;
import com.justyoga.blog.domain.model.mysql.Blog;
import com.justyoga.blog.domain.model.mysql.BlogVideo;
import com.justyoga.blog.service.interfaces.BlogService;
import com.justyoga.blog.service.interfaces.BlogVideoService;
import com.justyoga.blog.web.config.WebConfig;
import com.justyoga.blog.web.service.interfaces.BlogVideoUiService;
import com.justyoga.client.library.MediaClient;
import com.justyoga.util.dto.blog.BlogVideoDTO;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BlogVideoUiServiceImpl implements BlogVideoUiService {

    private final BlogVideoService blogVideoService;
    private final BlogService blogService;
    private final MediaClient mediaClient;
    private final BucketVsUrl bucketVsUrl;
    private final ModelMapper modelMapper;

    @Autowired
    public BlogVideoUiServiceImpl(
            @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper,
            BlogVideoService blogVideoService,
            BlogService blogService,
            MediaClient mediaClient,
            BucketVsUrl bucketVsUrl) {
        this.blogVideoService = blogVideoService;
        this.blogService = blogService;
        this.mediaClient = mediaClient;
        this.bucketVsUrl = bucketVsUrl;
        this.modelMapper = modelMapper;
    }

    private BlogVideoDTO convert(BlogVideo entity) {
        var blogVideoDTO = modelMapper.map(entity, BlogVideoDTO.class);
        blogVideoDTO.setUrl(getUrl(entity.getUrl()));
        return blogVideoDTO;
    }

    private BlogVideo convert(BlogVideoDTO dto) {
        return modelMapper.map(dto, BlogVideo.class);
    }

    @Override
    public BlogVideoDTO findById(UUID id) {
        var blogVideo = blogVideoService.findById(id);
        if (blogVideo == null) throw new AppException("Video not found", AppStatusCode.NOT_FOUND);
        return convert(blogVideo);
    }

    @Override
    public List<BlogVideoDTO> findAllByBlogId(UUID id) {
        return blogVideoService.findAllByBlogId(id).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(UUID id, UUID currentUser) {
        if (currentUser == null)
            throw new AppException("Current user is required", AppStatusCode.UNAUTHORIZED_ERROR);
        if (id == null)
            throw new AppException("Blog id is required", AppStatusCode.INVALID_REQUEST);
        BlogVideo blogVideo = blogVideoService.findById(id);
        if (blogVideo == null) throw new AppException("Video not found", AppStatusCode.NOT_FOUND);
        Blog blogDTO = blogService.findById(blogVideo.getBlogId());
        if (blogDTO == null) throw new AppException("Blog not found", AppStatusCode.NOT_FOUND);
        if (!currentUser.equals(blogDTO.getUserId())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return blogVideoService.delete(id);
    }

    @Override
    public BlogVideoDTO save(String video, UUID blogId, UUID currentUser) {
        if (currentUser == null)
            throw new AppException("Current user is required", AppStatusCode.UNAUTHORIZED_ERROR);
        if (video == null)
            throw new AppException("Video is required", AppStatusCode.INVALID_REQUEST);
        Blog blogDTO = blogService.findById(blogId);
        if (blogDTO == null) throw new AppException("Blog not found", AppStatusCode.NOT_FOUND);
        if (!currentUser.equals(blogDTO.getUserId())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }

        BlogVideo save = blogVideoService.save(convert(new BlogVideoDTO(video, blogId)));
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
