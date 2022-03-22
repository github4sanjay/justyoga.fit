package com.justyoga.collection.web.service.interfaces;

import com.justyoga.util.dto.collection.CollectionImageDTO;
import java.util.List;
import java.util.UUID;

public interface CollectionImageUiService {

    CollectionImageDTO findById(UUID id);

    List<CollectionImageDTO> findAllByCollectionId(UUID id);

    Boolean delete(UUID id, UUID currentUser);

    CollectionImageDTO save(CollectionImageDTO collectionVideoDTO, UUID currentUser);
}
