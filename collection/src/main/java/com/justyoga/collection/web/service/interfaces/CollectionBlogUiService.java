package com.justyoga.collection.web.service.interfaces;

import com.justyoga.util.dto.collection.CollectionBlogDTO;
import java.util.List;
import java.util.UUID;

public interface CollectionBlogUiService {

    CollectionBlogDTO findById(UUID id);

    List<CollectionBlogDTO> findAllByCollectionId(UUID id);

    Boolean delete(UUID id, UUID currentUser);

    CollectionBlogDTO save(CollectionBlogDTO collectionVideoDTO, UUID currentUser);
}
