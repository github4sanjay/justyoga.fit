package com.justyoga.search.domain.dao;

import com.justyoga.search.domain.model.VideoInfo;
import java.util.Collection;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoInfoRepository extends JpaRepository<VideoInfo, UUID> {

    Page<VideoInfo> findAllByIdIn(Collection<UUID> ids, Pageable pageable);

    Page<VideoInfo> findAllByTrainer(Boolean trainer, Pageable pageable);

    Page<VideoInfo> findAllByTrainerAndCountryId(
            Boolean trainer, UUID countryId, Pageable pageable);

    Page<VideoInfo> findAllByTrainerAndLocalityId(
            Boolean trainer, UUID localityId, Pageable pageable);

    Page<VideoInfo> findAllByTrainerAndAdministrativeAreaLevel1Id(
            Boolean trainer, UUID administrativeAreaLevel1Id, Pageable pageable);

    Page<VideoInfo> findAllByTrainerAndSubLocalityLevel1Id(
            Boolean trainer, UUID subLocalityLevel1Id, Pageable pageable);

    Page<VideoInfo> findAllByTrainerAndSubLocalityLevel2Id(
            Boolean trainer, UUID subLocalityLevel2Id, Pageable pageable);

    Page<VideoInfo> findAllByCountryId(UUID countryId, Pageable pageable);

    Page<VideoInfo> findAllByLocalityId(UUID localityId, Pageable pageable);

    Page<VideoInfo> findAllByAdministrativeAreaLevel1Id(
            UUID administrativeAreaLevel1Id, Pageable pageable);

    Page<VideoInfo> findAllBySubLocalityLevel1Id(UUID subLocalityLevel1Id, Pageable pageable);

    Page<VideoInfo> findAllBySubLocalityLevel2Id(UUID subLocalityLevel2Id, Pageable pageable);

    Page<VideoInfo> findAllByUserId(UUID userId, Pageable pageable);
}
