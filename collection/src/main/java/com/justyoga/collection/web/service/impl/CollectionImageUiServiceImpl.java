package com.justyoga.collection.web.service.impl;

import com.justyoga.client.profile.ProfileClient;
import com.justyoga.collection.domain.model.mysql.Collection;
import com.justyoga.collection.domain.model.mysql.CollectionImage;
import com.justyoga.collection.service.interfaces.CollectionImageService;
import com.justyoga.collection.service.interfaces.CollectionService;
import com.justyoga.collection.web.service.interfaces.CollectionImageUiService;
import com.justyoga.util.dto.collection.CollectionImageDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionImageUiServiceImpl implements CollectionImageUiService {

    private final CollectionImageService collectionImageService;
    private final CollectionService collectionService;
    private final ProfileClient profileClient;
    private final ModelMapper modelMapper;

    @Autowired
    public CollectionImageUiServiceImpl(
            CollectionImageService collectionImageService,
            CollectionService collectionService,
            ProfileClient profileClient,
            ModelMapper modelMapper) {
        this.collectionImageService = collectionImageService;
        this.collectionService = collectionService;
        this.profileClient = profileClient;
        this.modelMapper = modelMapper;
    }

    private CollectionImage convert(CollectionImageDTO dto) {
        return modelMapper.map(dto, CollectionImage.class);
    }

    private CollectionImageDTO convert(CollectionImage entity) {
        return modelMapper.map(entity, CollectionImageDTO.class);
    }

    @Override
    public CollectionImageDTO findById(UUID id) {
        var collectionImage = collectionImageService.findById(id);
        if (collectionImage == null)
            throw new AppException("Collection video not found", AppStatusCode.NOT_FOUND);
        else return convert(collectionImage);
    }

    @Override
    public List<CollectionImageDTO> findAllByCollectionId(UUID id) {
        return collectionImageService.findAllByCollectionId(id).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(UUID id, UUID currentUser) {
        return collectionImageService.delete(id);
    }

    @Override
    public CollectionImageDTO save(CollectionImageDTO collectionImageDTO, UUID currentUser) {
        Collection collectionDTO = collectionService.findById(collectionImageDTO.getCollectionId());
        if (collectionDTO == null)
            throw new AppException("Collection not found", AppStatusCode.NOT_FOUND);

        try {
            var image = profileClient.getImage(collectionImageDTO.getImageId());
            if (image == null || image.getBody() == null || image.getBody().getData() == null) {
                throw new AppException("Image not found", AppStatusCode.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new AppException("Image not found", AppStatusCode.NOT_FOUND);
        }

        CollectionImage collectionImage =
                collectionImageService.findByCollectionIdAndImageId(
                        collectionImageDTO.getCollectionId(), collectionImageDTO.getImageId());
        if (collectionImage != null)
            throw new AppException(
                    "Image already exist with this collection", AppStatusCode.INVALID_REQUEST);

        return convert(collectionImageService.save(convert(collectionImageDTO)));
    }
}
