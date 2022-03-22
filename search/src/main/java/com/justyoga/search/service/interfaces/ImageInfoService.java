package com.justyoga.search.service.interfaces;

import com.justyoga.search.domain.model.ImageInfo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface ImageInfoService {
    Page<ImageInfo> find(Integer page, Integer count, String sort, String order);

    Page<ImageInfo> findAllByIds(Integer page, Integer count, String sort, List<UUID> userIds);

    Page<ImageInfo> findAllByTrainerAndCountryId(
            Integer page, Integer count, String sort, UUID countryId, Boolean trainer);

    Page<ImageInfo> findAllByTrainerAndAdministrativeAreaLevel1Id(
            Integer page,
            Integer count,
            String sort,
            UUID administrativeAreaLevel1Id,
            Boolean trainer);

    Page<ImageInfo> findAllByTrainerAndLocalityId(
            Integer page, Integer count, String sort, UUID localityId, Boolean trainer);

    Page<ImageInfo> findAllByTrainerAndSubLocalityLevel1Id(
            Integer page, Integer count, String sort, UUID subLocalityLevel1Id, Boolean trainer);

    Page<ImageInfo> findAllByTrainerAndSubLocalityLevel2Id(
            Integer page, Integer count, String sort, UUID subLocalityLevel2Id, Boolean trainer);

    Page<ImageInfo> findAllByTrainer(Integer page, Integer count, String sort, Boolean trainer);

    Page<ImageInfo> findAllByCountryId(
            Integer page, Integer count, String sortBy, String orderBy, UUID countryId);

    Page<ImageInfo> findAllByAdministrativeAreaLevel1Id(
            Integer page,
            Integer count,
            String sortBy,
            String orderBy,
            UUID administrativeAreaLevel1Id);

    Page<ImageInfo> findAllByLocalityId(
            Integer page, Integer count, String sortBy, String orderBy, UUID localityId);

    Page<ImageInfo> findAllBySubLocalityLevel1Id(
            Integer page, Integer count, String sortBy, String orderBy, UUID subLocalityLevel1Id);

    Page<ImageInfo> findAllBySubLocalityLevel2Id(
            Integer page, Integer count, String sortBy, String orderBy, UUID subLocalityLevel2Id);

    Page<ImageInfo> findAllByUserId(
            Integer page, Integer count, String sortBy, String orderBy, UUID userId);
}
