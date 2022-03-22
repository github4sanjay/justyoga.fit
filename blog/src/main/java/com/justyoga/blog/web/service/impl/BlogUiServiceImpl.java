package com.justyoga.blog.web.service.impl;

import com.justyoga.blog.cache.BucketVsUrl;
import com.justyoga.blog.domain.model.mysql.Blog;
import com.justyoga.blog.service.interfaces.BlogService;
import com.justyoga.blog.web.config.WebConfig;
import com.justyoga.blog.web.service.interfaces.BlogUiService;
import com.justyoga.client.library.MediaClient;
import com.justyoga.util.dto.blog.BlogDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.page.PageDTO;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import com.justyoga.util.string.StringUtil;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BlogUiServiceImpl implements BlogUiService {

    private final BlogService blogService;
    private final ModelMapper modelMapper;
    private final MediaClient mediaClient;
    private final BucketVsUrl bucketVsUrl;

    @Autowired
    public BlogUiServiceImpl(
            BlogService blogService,
            @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper,
            MediaClient mediaClient,
            BucketVsUrl bucketVsUrl) {
        this.blogService = blogService;
        this.modelMapper = modelMapper;
        this.mediaClient = mediaClient;
        this.bucketVsUrl = bucketVsUrl;
    }

    private BlogDTO convertEntityToDto(Blog entity) {
        BlogDTO blogDTO = modelMapper.map(entity, BlogDTO.class);
        blogDTO.setBlogContent(
                StringUtil.getStringFromBase64(
                        blogDTO.getBlogContent())); // converting to string from Base64
        blogDTO.setBlogText(StringUtil.getStringFromBase64(blogDTO.getBlogText()));
        blogDTO.setCoverUrl(getUrl(blogDTO.getCoverUrl()));
        return blogDTO;
    }

    private Blog convertDtoToEntity(BlogDTO blogDTO) {
        if (blogDTO.getId() != null) {
            var blog = blogService.findById(blogDTO.getId());
            if (blog == null) throw new AppException("Blog not found", AppStatusCode.NOT_FOUND);
            blog.setBlogContent(StringUtil.getBase64ForString(blogDTO.getBlogContent()));
            blog.setBlogText(StringUtil.getBase64ForString(blogDTO.getBlogText()));
            blog.setBlogTitle(blogDTO.getBlogTitle());
            return blog;
        } else {
            blogDTO.setCoverUrl(null);
        }
        blogDTO.setBlogContent(StringUtil.getBase64ForString(blogDTO.getBlogContent()));
        blogDTO.setBlogText(StringUtil.getBase64ForString(blogDTO.getBlogText()));
        return modelMapper.map(blogDTO, Blog.class);
    }

    @Override
    public BlogDTO save(BlogDTO blogDTO, UUID currentUser) {
        if (currentUser == null)
            throw new AppException("Current user is required", AppStatusCode.UNAUTHORIZED_ERROR);
        if (blogDTO == null)
            throw new AppException("Blog is required", AppStatusCode.INVALID_REQUEST);
        if (blogDTO.getBlogTitle() == null)
            throw new AppException("Blog title is required", AppStatusCode.INVALID_REQUEST);
        if (blogDTO.getBlogContent() == null)
            throw new AppException("Blog content is required", AppStatusCode.INVALID_REQUEST);
        if (blogDTO.getBlogText() == null)
            throw new AppException("Blog text is required", AppStatusCode.INVALID_REQUEST);
        if (blogDTO.getUserId() == null)
            throw new AppException("Blog user id is required", AppStatusCode.INVALID_REQUEST);
        Blog entity = convertDtoToEntity(blogDTO);
        entity = blogService.save(entity);
        blogDTO = convertEntityToDto(entity);
        return blogDTO;
    }

    @Override
    public BlogDTO findById(UUID id) {
        Blog blog = blogService.findById(id);
        if (blog == null) throw new AppException("Blog not found", AppStatusCode.NOT_FOUND);
        return convertEntityToDto(blog);
    }

    @Override
    public List<BlogDTO> findByUserId(UUID userId) {
        return blogService.findAllByUserId(userId).stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PageDTO<BlogDTO> find(Integer page, Integer count, String sort, UUID userId) {
        Page<Blog> blogs;
        if (page == null) page = 0;
        if (count == null) count = 10;
        if (userId != null) {
            blogs = blogService.findAllByUserId(page, count, sort, userId);
        } else {
            blogs = blogService.findAll(page, count, sort);
        }
        List<BlogDTO> blogDTOS =
                blogs.stream().map(this::convertEntityToDto).collect(Collectors.toList());
        return PageDTO.<BlogDTO>builder()
                .content(blogDTOS)
                .first(blogs.isFirst())
                .hasContent(blogs.hasContent())
                .hasNext(blogs.hasNext())
                .hasPrevious(blogs.hasPrevious())
                .last(blogs.isLast())
                .number(blogs.getNumber())
                .numberOfElements(blogs.getNumberOfElements())
                .size(blogs.getSize())
                .totalElements(blogs.getTotalElements())
                .totalPages(blogs.getTotalPages())
                .build();
    }

    @Override
    public Boolean delete(UUID id, UUID currentUser) {
        if (currentUser == null)
            throw new AppException("Current user is required", AppStatusCode.UNAUTHORIZED_ERROR);
        if (id == null)
            throw new AppException("Blog id is required", AppStatusCode.INVALID_REQUEST);
        Blog byId = blogService.findById(id);
        if (!currentUser.equals(byId.getUserId())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return blogService.delete(id);
    }

    @Override
    public BlogDTO saveCover(UUID blogId, String url, UUID userId) {
        Blog blog = blogService.findById(blogId);
        if (blog == null) {
            throw new AppException("Blog not found", AppStatusCode.NOT_FOUND);
        }
        if (!userId.equals(blog.getUserId())) {
            throw new AppException(
                    "Not authorized for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }

        blog.setCoverUrl(url);
        blog = blogService.save(blog);
        return convertEntityToDto(blog);
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
