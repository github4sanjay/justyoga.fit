package com.justyoga.collection.web.service.impl;

import com.justyoga.client.blog.BlogClient;
import com.justyoga.collection.domain.model.mysql.Collection;
import com.justyoga.collection.domain.model.mysql.CollectionBlog;
import com.justyoga.collection.service.interfaces.CollectionBlogService;
import com.justyoga.collection.service.interfaces.CollectionService;
import com.justyoga.collection.web.service.interfaces.CollectionBlogUiService;
import com.justyoga.util.dto.collection.CollectionBlogDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionBlogUiServiceImpl implements CollectionBlogUiService {

    private final CollectionBlogService collectionBlogService;
    private final CollectionService collectionService;
    private final BlogClient blogClient;
    private final ModelMapper modelMapper;

    @Autowired
    public CollectionBlogUiServiceImpl(
            CollectionBlogService collectionBlogService,
            CollectionService collectionService,
            BlogClient blogClient,
            ModelMapper modelMapper) {
        this.collectionBlogService = collectionBlogService;
        this.collectionService = collectionService;
        this.blogClient = blogClient;
        this.modelMapper = modelMapper;
    }

    private CollectionBlog convert(CollectionBlogDTO dto) {
        return modelMapper.map(dto, CollectionBlog.class);
    }

    private CollectionBlogDTO convert(CollectionBlog entity) {
        return modelMapper.map(entity, CollectionBlogDTO.class);
    }

    @Override
    public CollectionBlogDTO findById(UUID id) {
        var blog = collectionBlogService.findById(id);
        if (blog == null)
            throw new AppException("collection blog not found", AppStatusCode.NOT_FOUND);
        else return convert(blog);
    }

    @Override
    public List<CollectionBlogDTO> findAllByCollectionId(UUID id) {
        return collectionBlogService.findAllByCollectionId(id).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(UUID id, UUID currentUser) {
        return collectionBlogService.delete(id);
    }

    @Override
    public CollectionBlogDTO save(CollectionBlogDTO collectionBlogDTO, UUID currentUser) {

        Collection collectionDTO = collectionService.findById(collectionBlogDTO.getCollectionId());
        if (collectionDTO == null)
            throw new AppException("Collection not found", AppStatusCode.NOT_FOUND);

        try {
            var blog = blogClient.get(collectionBlogDTO.getBlogId());
            if (blog == null || blog.getBody() == null || blog.getBody().getData() == null) {
                throw new AppException("Blog not found", AppStatusCode.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new AppException("Blog not found", AppStatusCode.NOT_FOUND);
        }

        CollectionBlog collectionBlog =
                collectionBlogService.findByCollectionIdAndBlogId(
                        collectionBlogDTO.getCollectionId(), collectionBlogDTO.getBlogId());
        if (collectionBlog != null)
            throw new AppException(
                    "Blog already exist with this collection", AppStatusCode.INVALID_REQUEST);

        return convert(collectionBlogService.save(convert(collectionBlogDTO)));
    }
}
