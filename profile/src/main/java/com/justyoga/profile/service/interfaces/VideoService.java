package com.justyoga.profile.service.interfaces;

import com.justyoga.profile.domain.model.mysql.Video;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface VideoService {
    List<Video> findByUserId(UUID trainerId);

    Page<Video> findAllByUserId(Integer page, Integer count, String sort, UUID userId);

    Page<Video> findAll(Integer page, Integer count, String sort);

    Video save(Video dto);

    boolean delete(UUID id);

    void deleteAllByUserId(UUID id);

    Video findById(UUID id);

    List<Video> getAll();

    List<Video> saveAll(List<Video> dtoList);
}
