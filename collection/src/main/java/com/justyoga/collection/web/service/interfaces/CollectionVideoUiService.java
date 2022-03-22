package com.justyoga.collection.web.service.interfaces;

import com.justyoga.util.dto.collection.CollectionVideoDTO;
import java.util.List;
import java.util.UUID;

public interface CollectionVideoUiService {

    CollectionVideoDTO findById(UUID id);

    List<CollectionVideoDTO> findAllByCollectionId(UUID id);

    Boolean delete(UUID id, UUID currentUser);

    CollectionVideoDTO save(CollectionVideoDTO collectionVideoDTO, UUID currentUser);
}
