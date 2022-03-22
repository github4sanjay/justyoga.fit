package com.justyoga.profile.service.interfaces;

import com.justyoga.profile.domain.model.mysql.UserBasicInfo;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface UserBasicInfoService {

    UserBasicInfo findById(UUID id);

    UserBasicInfo findByUserId(UUID id);

    UserBasicInfo save(UserBasicInfo dto);

    boolean delete(UUID id);

    Page<UserBasicInfo> findAllBySubLocalityLevel2Id(
            UUID subLocalityLevel2Id, Integer page, Integer count, String sort);

    Page<UserBasicInfo> findAllBySubLocalityLevel1Id(
            UUID subLocalityLevel1Id, Integer page, Integer count, String sort);

    Page<UserBasicInfo> findAllByLocalityId(UUID id, Integer page, Integer count, String sort);

    Page<UserBasicInfo> findAllByAdministrativeAreaLevel1Id(
            UUID id, Integer page, Integer count, String sort);

    Page<UserBasicInfo> findAllByCountryId(UUID id, Integer page, Integer count, String sort);

    Page<UserBasicInfo> findAll(Integer page, Integer count, String sort);
}
