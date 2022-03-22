package com.justyoga.search.domain.dao;

import com.justyoga.search.domain.model.BlogInfo;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogInfoRepository extends JpaRepository<BlogInfo, UUID> {
    Page<BlogInfo> findAllByIdIn(Collection<UUID> ids, Pageable pageable);

    Page<BlogInfo> findAllByTrainer(Boolean trainer, Pageable pageable);

    Page<BlogInfo> findAllByTrainerAndCountryId(Boolean trainer, UUID countryId, Pageable pageable);

    Page<BlogInfo> findAllByTrainerAndLocalityId(
            Boolean trainer, UUID localityId, Pageable pageable);

    Page<BlogInfo> findAllByTrainerAndAdministrativeAreaLevel1Id(
            Boolean trainer, UUID administrativeAreaLevel1Id, Pageable pageable);

    Page<BlogInfo> findAllByTrainerAndSubLocalityLevel1Id(
            Boolean trainer, UUID subLocalityLevel1Id, Pageable pageable);

    Page<BlogInfo> findAllByTrainerAndSubLocalityLevel2Id(
            Boolean trainer, UUID subLocalityLevel2Id, Pageable pageable);

    Page<BlogInfo> findAllByCountryId(UUID countryId, Pageable pageable);

    Page<BlogInfo> findAllByLocalityId(UUID localityId, Pageable pageable);

    Page<BlogInfo> findAllByAdministrativeAreaLevel1Id(
            UUID administrativeAreaLevel1Id, Pageable pageable);

    Page<BlogInfo> findAllBySubLocalityLevel1Id(UUID subLocalityLevel1Id, Pageable pageable);

    Page<BlogInfo> findAllBySubLocalityLevel2Id(UUID subLocalityLevel2Id, Pageable pageable);

    Page<BlogInfo> findAllByUserId(UUID userId, Pageable pageable);
}
