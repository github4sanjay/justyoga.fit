package com.justyoga.profile.domain.dao;

import com.justyoga.profile.domain.model.mysql.Image;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, UUID> {

    List<Image> findAllByUserId(UUID id);

    Page<Image> findAllByUserId(UUID userId, Pageable pageable);

    void deleteAllByUserId(UUID id);
}
