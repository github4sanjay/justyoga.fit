package com.justyoga.search.domain.dao;

import com.justyoga.search.domain.model.ImageInfo;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageInfoRepository extends JpaRepository<ImageInfo, UUID> {
    Page<ImageInfo> findAllByIdIn(Collection<UUID> ids, Pageable pageable);

    Page<ImageInfo> findAllByTrainer(Boolean trainer, Pageable pageable);

    Page<ImageInfo> findAllByTrainerAndCountryId(
            Boolean trainer, UUID countryId, Pageable pageable);

    Page<ImageInfo> findAllByTrainerAndLocalityId(
            Boolean trainer, UUID localityId, Pageable pageable);

    Page<ImageInfo> findAllByTrainerAndAdministrativeAreaLevel1Id(
            Boolean trainer, UUID administrativeAreaLevel1Id, Pageable pageable);

    Page<ImageInfo> findAllByTrainerAndSubLocalityLevel1Id(
            Boolean trainer, UUID subLocalityLevel1Id, Pageable pageable);

    Page<ImageInfo> findAllByTrainerAndSubLocalityLevel2Id(
            Boolean trainer, UUID subLocalityLevel2Id, Pageable pageable);

    Page<ImageInfo> findAllByCountryId(UUID countryId, Pageable pageable);

    Page<ImageInfo> findAllByLocalityId(UUID localityId, Pageable pageable);

    Page<ImageInfo> findAllByAdministrativeAreaLevel1Id(
            UUID administrativeAreaLevel1Id, Pageable pageable);

    Page<ImageInfo> findAllBySubLocalityLevel1Id(UUID subLocalityLevel1Id, Pageable pageable);

    Page<ImageInfo> findAllBySubLocalityLevel2Id(UUID subLocalityLevel2Id, Pageable pageable);

    Page<ImageInfo> findAllByUserId(UUID userId, Pageable pageable);
}
