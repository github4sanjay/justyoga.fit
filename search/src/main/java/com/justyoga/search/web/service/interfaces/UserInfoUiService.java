package com.justyoga.search.web.service.interfaces;

import com.justyoga.search.domain.model.UserInfo;
import com.justyoga.util.page.PageDTO;
import java.util.UUID;

public interface UserInfoUiService {
    PageDTO<UserInfo> find(
            Integer page,
            Integer count,
            String sort,
            String order,
            Boolean trainer,
            UUID countryId,
            UUID administrativeAreaLevel1Id,
            UUID localityId,
            UUID subLocalityLevel1Id,
            UUID subLocalityLevel2Id);
}
