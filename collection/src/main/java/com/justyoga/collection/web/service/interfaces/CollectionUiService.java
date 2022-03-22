package com.justyoga.collection.web.service.interfaces;

import com.justyoga.util.dto.collection.CollectionDTO;
import com.justyoga.util.page.PageDTO;
import java.util.UUID;

public interface CollectionUiService {

    CollectionDTO save(CollectionDTO collectionDTO, UUID currentUser);

    CollectionDTO findById(UUID id);

    Boolean delete(UUID id, UUID currentUser);

    PageDTO<CollectionDTO> find(Integer page, Integer count, String sort, String order);

    CollectionDTO saveCover(UUID collectionId, String image, UUID userId);
}
