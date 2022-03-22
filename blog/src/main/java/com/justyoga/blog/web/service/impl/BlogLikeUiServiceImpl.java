package com.justyoga.blog.web.service.impl;

import com.justyoga.blog.domain.model.mysql.BlogLike;
import com.justyoga.blog.service.interfaces.BlogLikeService;
import com.justyoga.blog.service.interfaces.BlogService;
import com.justyoga.blog.web.service.interfaces.BlogLikeUiService;
import com.justyoga.util.dto.blog.BlogLikeDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlogLikeUiServiceImpl implements BlogLikeUiService {

    private final BlogLikeService blogLikeService;
    private final BlogService blogService;
    private final ModelMapper modelMapper;

    @Autowired
    public BlogLikeUiServiceImpl(
            BlogLikeService blogLikeService, BlogService blogService, ModelMapper modelMapper) {
        this.blogLikeService = blogLikeService;
        this.blogService = blogService;
        this.modelMapper = modelMapper;
    }

    private BlogLikeDTO convertEntityToDto(BlogLike entity) {
        return modelMapper.map(entity, BlogLikeDTO.class);
    }

    private BlogLike convertDtoToEntity(BlogLikeDTO dto) {
        return modelMapper.map(dto, BlogLike.class);
    }

    @Override
    public BlogLikeDTO findById(UUID id) {
        var blogLike = blogLikeService.findById(id);
        if (blogLike == null) throw new AppException("Like not found", AppStatusCode.NOT_FOUND);
        else return convertEntityToDto(blogLike);
    }

    @Override
    public List<BlogLikeDTO> findAllByBlogId(UUID id) {
        return blogLikeService.findAllByBlogId(id).stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(UUID id, UUID currentUser) {
        if (currentUser == null)
            throw new AppException("Current user is required", AppStatusCode.UNAUTHORIZED_ERROR);
        if (id == null)
            throw new AppException("Blog id is required", AppStatusCode.INVALID_REQUEST);
        var blogLike = blogLikeService.findById(id);
        if (blogLike == null) throw new AppException("Like not found", AppStatusCode.NOT_FOUND);
        var blog = blogService.findById(blogLike.getBlogId());
        if (blog == null) throw new AppException("Blog not found", AppStatusCode.NOT_FOUND);
        if (!currentUser.equals(blogLike.getUserId())) {
            throw new AppException(
                    "Not allowed for user other than who liked it",
                    AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return blogLikeService.delete(id);
    }

    @Override
    public BlogLikeDTO save(BlogLikeDTO blogLikeDTO, UUID currentUser) {
        if (currentUser == null)
            throw new AppException("Current user is required", AppStatusCode.UNAUTHORIZED_ERROR);
        if (blogLikeDTO == null)
            throw new AppException("bloglike  is required", AppStatusCode.INVALID_REQUEST);
        if (!currentUser.equals(blogLikeDTO.getUserId())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        var blog = blogService.findById(blogLikeDTO.getBlogId());
        if (blog == null) throw new AppException("Blog not found", AppStatusCode.NOT_FOUND);
        var blogLike = blogLikeService.save(convertDtoToEntity(blogLikeDTO));
        return convertEntityToDto(blogLike);
    }
}
