package com.justyoga.blog.web.service.impl;

import com.justyoga.blog.domain.model.mysql.Blog;
import com.justyoga.blog.domain.model.mysql.BlogComment;
import com.justyoga.blog.service.interfaces.BlogCommentService;
import com.justyoga.blog.service.interfaces.BlogService;
import com.justyoga.blog.web.service.interfaces.BlogCommentUiService;
import com.justyoga.util.dto.blog.BlogCommentDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.string.StringUtil;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogCommentUiServiceImpl implements BlogCommentUiService {

    private final BlogCommentService blogCommentService;
    private final BlogService blogService;
    private final ModelMapper modelMapper;

    @Autowired
    public BlogCommentUiServiceImpl(
            BlogCommentService blogCommentService,
            BlogService blogService,
            ModelMapper modelMapper) {
        this.blogCommentService = blogCommentService;
        this.blogService = blogService;
        this.modelMapper = modelMapper;
    }

    private BlogCommentDTO convert(BlogComment entity) {
        var blogCommentDto = modelMapper.map(entity, BlogCommentDTO.class);
        blogCommentDto.setText(StringUtil.getStringFromBase64(entity.getText()));
        return blogCommentDto;
    }

    private BlogComment convert(BlogCommentDTO dto) {
        dto.setText(StringUtil.getBase64ForString(dto.getText()));
        return modelMapper.map(dto, BlogComment.class);
    }

    @Override
    public BlogCommentDTO findById(UUID id) {
        var blogComment = blogCommentService.findById(id);
        if (blogComment == null)
            throw new AppException("Comment not found", AppStatusCode.NOT_FOUND);
        else return convert(blogComment);
    }

    @Override
    public List<BlogCommentDTO> findAllByBlogId(UUID id) {
        return blogCommentService.findAllByBlogId(id).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(UUID id, UUID currentUser) {
        if (currentUser == null)
            throw new AppException("Current user is required", AppStatusCode.UNAUTHORIZED_ERROR);
        if (id == null)
            throw new AppException("Blog id is required", AppStatusCode.INVALID_REQUEST);
        BlogComment blogComment = blogCommentService.findById(id);
        if (blogComment == null)
            throw new AppException("Comment not found", AppStatusCode.NOT_FOUND);
        Blog blogDTO = blogService.findById(blogComment.getBlogId());
        if (blogDTO == null) throw new AppException("Blog not found", AppStatusCode.NOT_FOUND);
        if (!currentUser.equals(blogComment.getUserId())) {
            throw new AppException(
                    "Not allowed for user other than who commented it",
                    AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return blogCommentService.delete(id);
    }

    @Override
    public BlogCommentDTO save(BlogCommentDTO blogCommentDTO, UUID currentUser) {
        if (currentUser == null)
            throw new AppException("Current user is required", AppStatusCode.UNAUTHORIZED_ERROR);
        if (blogCommentDTO == null)
            throw new AppException("Comment is required", AppStatusCode.INVALID_REQUEST);
        if (!currentUser.equals(blogCommentDTO.getUserId())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        Blog blogDTO = blogService.findById(blogCommentDTO.getBlogId());
        if (blogDTO == null)
            throw new AppException("Blog not found", AppStatusCode.INVALID_REQUEST);
        var blogComment = blogCommentService.save(convert(blogCommentDTO));
        return convert(blogComment);
    }
}
