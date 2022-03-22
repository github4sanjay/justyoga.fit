package com.justyoga.profile.web.service.interfaces;

import com.justyoga.util.dto.profile.InterestDTO;
import com.justyoga.util.page.PageDTO;
import java.util.UUID;

public interface InterestUiService {
    InterestDTO findById(UUID id);

    InterestDTO findByUserId(UUID id);

    InterestDTO saveOrUpdate(InterestDTO trainerDTO, UUID userId);

    PageDTO<InterestDTO> find(Integer page, Integer count, String sort);
}
