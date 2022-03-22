package com.justyoga.collection.web.service.impl;

import com.justyoga.client.profile.ProfileClient;
import com.justyoga.collection.domain.model.mysql.Collection;
import com.justyoga.collection.domain.model.mysql.CollectionVideo;
import com.justyoga.collection.service.interfaces.CollectionService;
import com.justyoga.collection.service.interfaces.CollectionVideoService;
import com.justyoga.collection.web.service.interfaces.CollectionVideoUiService;
import com.justyoga.util.dto.collection.CollectionVideoDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollectionVideoUiServiceImpl implements CollectionVideoUiService {

    private final CollectionVideoService collectionVideoService;
    private final CollectionService collectionService;
    private final ProfileClient profileClient;
    private final ModelMapper modelMapper;

    @Autowired
    public CollectionVideoUiServiceImpl(
            CollectionVideoService collectionVideoService,
            CollectionService collectionService,
            ProfileClient profileClient,
            ModelMapper modelMapper) {
        this.collectionVideoService = collectionVideoService;
        this.collectionService = collectionService;
        this.profileClient = profileClient;
        this.modelMapper = modelMapper;
    }

    private CollectionVideo convert(CollectionVideoDTO dto) {
        return modelMapper.map(dto, CollectionVideo.class);
    }

    private CollectionVideoDTO convert(CollectionVideo entity) {
        return modelMapper.map(entity, CollectionVideoDTO.class);
    }

    @Override
    public CollectionVideoDTO findById(UUID id) {
        var video = collectionVideoService.findById(id);
        if (video == null)
            throw new AppException("Collection video not found", AppStatusCode.NOT_FOUND);
        else return convert(video);
    }

    @Override
    public List<CollectionVideoDTO> findAllByCollectionId(UUID id) {
        return collectionVideoService.findAllByCollectionId(id).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(UUID id, UUID currentUser) {
        return collectionVideoService.delete(id);
    }

    @Override
    public CollectionVideoDTO save(CollectionVideoDTO collectionVideoDTO, UUID currentUser) {

        Collection collectionDTO = collectionService.findById(collectionVideoDTO.getCollectionId());
        if (collectionDTO == null)
            throw new AppException("Collection not found", AppStatusCode.NOT_FOUND);

        try {
            var video = profileClient.getVideo(collectionVideoDTO.getVideoId());
            if (video == null || video.getBody() == null || video.getBody().getData() == null) {
                throw new AppException("Video not found", AppStatusCode.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new AppException("Video not found", AppStatusCode.NOT_FOUND);
        }

        CollectionVideo collectionVideo =
                collectionVideoService.findByCollectionIdAndVideoId(
                        collectionVideoDTO.getCollectionId(), collectionVideoDTO.getVideoId());
        if (collectionVideo != null)
            throw new AppException(
                    "Video already exist with this collection", AppStatusCode.INVALID_REQUEST);

        return convert(collectionVideoService.save(convert(collectionVideoDTO)));
    }
}
