package com.justyoga.profile.web.service.interfaces;

import com.justyoga.util.dto.profile.VideoDTO;
import com.justyoga.util.page.PageDTO;
import java.util.List;
import java.util.UUID;

public interface VideoUiService {

    VideoDTO save(UUID userId, VideoDTO videoDTO);

    boolean deleteMedia(UUID id, UUID userID);

    List<VideoDTO> findByUserId(UUID trainerId);

    VideoDTO findById(UUID id);

    VideoDTO update(VideoDTO dto, UUID userId);

    PageDTO<VideoDTO> find(Integer page, Integer count, String sort, UUID userId);
}
