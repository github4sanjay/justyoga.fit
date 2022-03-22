package com.justyoga.profile.web.service.interfaces;

import com.justyoga.util.dto.profile.ImageDTO;
import com.justyoga.util.page.PageDTO;
import java.util.UUID;

public interface ImageUiService {

    ImageDTO save(UUID userId, ImageDTO imageDTO);

    boolean delete(UUID id, UUID userID);

    PageDTO<ImageDTO> find(Integer page, Integer count, String sort, UUID userId);

    ImageDTO findById(UUID id);

    ImageDTO update(ImageDTO imageDTO, UUID userID);
}
