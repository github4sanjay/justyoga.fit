package com.justyoga.collection.web.service.impl;

import com.justyoga.client.library.MediaClient;
import com.justyoga.collection.cache.BucketVsUrl;
import com.justyoga.collection.domain.model.mysql.Collection;
import com.justyoga.collection.service.interfaces.CollectionBlogService;
import com.justyoga.collection.service.interfaces.CollectionImageService;
import com.justyoga.collection.service.interfaces.CollectionService;
import com.justyoga.collection.service.interfaces.CollectionVideoService;
import com.justyoga.collection.web.service.interfaces.CollectionUiService;
import com.justyoga.util.dto.collection.CollectionDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.page.PageDTO;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CollectionUiServiceImpl implements CollectionUiService {

    private final CollectionService collectionService;
    private final CollectionImageService collectionImageService;
    private final CollectionVideoService collectionVideoService;
    private final CollectionBlogService collectionBlogService;
    private final MediaClient mediaClient;
    private final BucketVsUrl bucketVsUrl;
    private final ModelMapper modelMapper;

    @Autowired
    public CollectionUiServiceImpl(
            CollectionService collectionService,
            CollectionImageService collectionImageService,
            CollectionVideoService collectionVideoService,
            CollectionBlogService collectionBlogService,
            MediaClient mediaClient,
            BucketVsUrl bucketVsUrl,
            ModelMapper modelMapper) {
        this.collectionService = collectionService;
        this.collectionImageService = collectionImageService;
        this.collectionVideoService = collectionVideoService;
        this.collectionBlogService = collectionBlogService;
        this.mediaClient = mediaClient;
        this.bucketVsUrl = bucketVsUrl;
        this.modelMapper = modelMapper;
    }

    private CollectionDTO convert(Collection entity) {
        var blogImageDTO = modelMapper.map(entity, CollectionDTO.class);
        blogImageDTO.setCoverUrl(getUrl(entity.getCoverUrl()));
        return blogImageDTO;
    }

    private Collection convert(CollectionDTO dto) {
        if (dto.getId() != null) {
            var collection = collectionService.findById(dto.getId());
            if (collection != null) {
                dto.setCoverUrl(collection.getCoverUrl());
            } else {
                dto.setCoverUrl(null);
            }
        }
        return modelMapper.map(dto, Collection.class);
    }

    @Override
    public CollectionDTO save(CollectionDTO collectionDTO, UUID currentUser) {
        var save = collectionService.save(convert(collectionDTO));
        return convert(save);
    }

    @Override
    public CollectionDTO saveCover(UUID collectionId, String image, UUID userId) {
        Collection collection = collectionService.findById(collectionId);
        if (collection == null) {
            throw new AppException("Collection not found", AppStatusCode.NOT_FOUND);
        }
        collection.setCoverUrl(image);
        var save = collectionService.save(collection);
        return convert(save);
    }

    @Override
    public CollectionDTO findById(UUID id) {
        Collection collection = collectionService.findById(id);
        if (collection == null) {
            throw new AppException("Collection not found", AppStatusCode.NOT_FOUND);
        }
        return convert(collection);
    }

    @Override
    public Boolean delete(UUID id, UUID currentUser) {
        Collection byId = collectionService.findById(id);
        if (byId == null) {
            throw new AppException("Collection not found", AppStatusCode.NOT_FOUND);
        }
        collectionImageService.deleteByCollectionId(id);
        collectionBlogService.deleteByCollectionId(id);
        collectionVideoService.deleteByCollectionId(id);
        return collectionService.delete(id);
    }

    @Override
    public PageDTO<CollectionDTO> find(Integer page, Integer count, String sort, String order) {
        if (page == null) page = 0;
        if (count == null) count = 10;

        Page<Collection> collections = collectionService.findAll(page, count, sort, order);
        return PageDTO.<CollectionDTO>builder()
                .content(
                        collections.getContent().stream()
                                .map(this::convert)
                                .collect(Collectors.toList()))
                .first(collections.isFirst())
                .hasContent(collections.hasContent())
                .hasNext(collections.hasNext())
                .hasPrevious(collections.hasPrevious())
                .last(collections.isLast())
                .number(collections.getNumber())
                .numberOfElements(collections.getNumberOfElements())
                .size(collections.getSize())
                .totalElements(collections.getTotalElements())
                .totalPages(collections.getTotalPages())
                .build();
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
