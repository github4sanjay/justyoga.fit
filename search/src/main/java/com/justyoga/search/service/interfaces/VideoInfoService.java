package com.justyoga.search.service.interfaces;

import com.justyoga.search.domain.model.VideoInfo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface VideoInfoService {
    Page<VideoInfo> find(Integer page, Integer count, String sort, String order);

    Page<VideoInfo> findAllByIds(Integer page, Integer count, String sort, List<UUID> userIds);

    Page<VideoInfo> findAllByTrainerAndCountryId(
            Integer page, Integer count, String sort, UUID countryId, Boolean trainer);

    Page<VideoInfo> findAllByTrainerAndAdministrativeAreaLevel1Id(
            Integer page,
            Integer count,
            String sort,
            UUID administrativeAreaLevel1Id,
            Boolean trainer);

    Page<VideoInfo> findAllByTrainerAndLocalityId(
            Integer page, Integer count, String sort, UUID localityId, Boolean trainer);

    Page<VideoInfo> findAllByTrainerAndSubLocalityLevel1Id(
            Integer page, Integer count, String sort, UUID subLocalityLevel1Id, Boolean trainer);

    Page<VideoInfo> findAllByTrainerAndSubLocalityLevel2Id(
            Integer page, Integer count, String sort, UUID subLocalityLevel2Id, Boolean trainer);

    Page<VideoInfo> findAllByTrainer(Integer page, Integer count, String sort, Boolean trainer);

    Page<VideoInfo> findAllByCountryId(
            Integer page, Integer count, String sortBy, String orderBy, UUID countryId);

    Page<VideoInfo> findAllByAdministrativeAreaLevel1Id(
            Integer page,
            Integer count,
            String sortBy,
            String orderBy,
            UUID administrativeAreaLevel1Id);

    Page<VideoInfo> findAllByLocalityId(
            Integer page, Integer count, String sortBy, String orderBy, UUID localityId);

    Page<VideoInfo> findAllBySubLocalityLevel1Id(
            Integer page, Integer count, String sortBy, String orderBy, UUID subLocalityLevel1Id);

    Page<VideoInfo> findAllBySubLocalityLevel2Id(
            Integer page, Integer count, String sortBy, String orderBy, UUID subLocalityLevel2Id);

    Page<VideoInfo> findAllByUserId(
            Integer page, Integer count, String sortBy, String orderBy, UUID userId);
}
