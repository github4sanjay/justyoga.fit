package com.justyoga.profile.service.interfaces;

import com.justyoga.profile.domain.model.mysql.Image;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface ImageService {
    List<Image> findByUserId(UUID trainerId);

    Page<Image> findAllByUserId(Integer page, Integer count, String sort, UUID userId);

    Page<Image> findAll(Integer page, Integer count, String sort);

    Image save(Image dto);

    boolean delete(UUID id);

    void deleteAllByUserId(UUID id);

    Image findById(UUID id);
}
