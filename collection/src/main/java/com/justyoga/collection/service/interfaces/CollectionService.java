package com.justyoga.collection.service.interfaces;

import com.justyoga.collection.domain.model.mysql.Collection;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface CollectionService {

    Collection findById(UUID id);

    boolean delete(UUID id);

    Collection save(Collection review);

    Page<Collection> findAll(Integer page, Integer count, String sortBy, String orderBy);
}
