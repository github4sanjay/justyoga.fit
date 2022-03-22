package com.justyoga.profile.domain.dao;

import com.justyoga.profile.domain.model.mysql.UserBasicInfo;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBasicInfoRepository extends JpaRepository<UserBasicInfo, UUID> {
    UserBasicInfo findByUserId(UUID userId);

    Page<UserBasicInfo> findAllBySubLocalityLevel2Id(UUID id, Pageable pageable);

    Page<UserBasicInfo> findAllBySubLocalityLevel1Id(UUID id, Pageable pageable);

    Page<UserBasicInfo> findAllByAdministrativeAreaLevel1Id(UUID id, Pageable pageable);

    Page<UserBasicInfo> findAllByCountryId(UUID id, Pageable pageable);

    Page<UserBasicInfo> findAllByLocalityId(UUID id, Pageable pageable);
}
