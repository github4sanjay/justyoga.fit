package com.justyoga.search.domain.dao;

import com.justyoga.search.domain.model.UserInfo;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
    Page<UserInfo> findAllByIdIn(Collection<UUID> ids, Pageable pageable);

    Page<UserInfo> findAllByTrainer(Boolean trainer, Pageable pageable);

    Page<UserInfo> findAllByTrainerAndCountryId(Boolean trainer, UUID countryId, Pageable pageable);

    Page<UserInfo> findAllByTrainerAndLocalityId(
            Boolean trainer, UUID localityId, Pageable pageable);

    Page<UserInfo> findAllByTrainerAndAdministrativeAreaLevel1Id(
            Boolean trainer, UUID administrativeAreaLevel1Id, Pageable pageable);

    Page<UserInfo> findAllByTrainerAndSubLocalityLevel1Id(
            Boolean trainer, UUID subLocalityLevel1Id, Pageable pageable);

    Page<UserInfo> findAllByTrainerAndSubLocalityLevel2Id(
            Boolean trainer, UUID subLocalityLevel2Id, Pageable pageable);
}
