package com.justyoga.profile.web.service.interfaces;

import com.justyoga.util.dto.profile.UserBasicInfoDTO;
import com.justyoga.util.page.PageDTO;
import java.util.UUID;

public interface UserBasicInfoUiService {
    UserBasicInfoDTO findUserBasicInfoById(UUID id);

    UserBasicInfoDTO findByUserId(UUID id);

    UserBasicInfoDTO saveOrUpdate(UserBasicInfoDTO trainerDTO, UUID userId);

    PageDTO<UserBasicInfoDTO> find(
            Integer page,
            Integer count,
            String sort,
            UUID countryId,
            UUID administrativeAreaLevel1Id,
            UUID localityId,
            UUID subLocalityLevel1Id,
            UUID subLocalityLevel2Id);
}
