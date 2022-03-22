package com.justyoga.profile.domain.dao;

import com.justyoga.profile.domain.model.mysql.Video;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, UUID> {
    List<Video> findAllByUserId(UUID id);

    Page<Video> findAllByUserId(UUID userId, Pageable pageable);

    void deleteAllByUserId(UUID id);
}
