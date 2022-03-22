package com.justyoga.search.service.interfaces;

import com.justyoga.search.domain.model.BlogInfo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface BlogInfoService {
    Page<BlogInfo> find(Integer page, Integer count, String sort, String order);

    Page<BlogInfo> findAllByIds(Integer page, Integer count, String sort, List<UUID> userIds);

    Page<BlogInfo> findAllByTrainerAndCountryId(
            Integer page, Integer count, String sort, UUID countryId, Boolean trainer);

    Page<BlogInfo> findAllByTrainerAndAdministrativeAreaLevel1Id(
            Integer page,
            Integer count,
            String sort,
            UUID administrativeAreaLevel1Id,
            Boolean trainer);

    Page<BlogInfo> findAllByTrainerAndLocalityId(
            Integer page, Integer count, String sort, UUID localityId, Boolean trainer);

    Page<BlogInfo> findAllByTrainerAndSubLocalityLevel1Id(
            Integer page, Integer count, String sort, UUID subLocalityLevel1Id, Boolean trainer);

    Page<BlogInfo> findAllByTrainerAndSubLocalityLevel2Id(
            Integer page, Integer count, String sort, UUID subLocalityLevel2Id, Boolean trainer);

    Page<BlogInfo> findAllByTrainer(Integer page, Integer count, String sort, Boolean trainer);

    Page<BlogInfo> findAllByCountryId(
            Integer page, Integer count, String sortBy, String orderBy, UUID countryId);

    Page<BlogInfo> findAllByAdministrativeAreaLevel1Id(
            Integer page,
            Integer count,
            String sortBy,
            String orderBy,
            UUID administrativeAreaLevel1Id);

    Page<BlogInfo> findAllByLocalityId(
            Integer page, Integer count, String sortBy, String orderBy, UUID localityId);

    Page<BlogInfo> findAllBySubLocalityLevel1Id(
            Integer page, Integer count, String sortBy, String orderBy, UUID subLocalityLevel1Id);

    Page<BlogInfo> findAllBySubLocalityLevel2Id(
            Integer page, Integer count, String sortBy, String orderBy, UUID subLocalityLevel2Id);

    Page<BlogInfo> findAllByUserId(
            Integer page, Integer count, String sort, String order, UUID userId);
}
