package com.justyoga.search.service.interfaces;

import com.justyoga.search.domain.model.UserInfo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface UserInfoService {
    Page<UserInfo> find(Integer page, Integer count, String sort);

    Page<UserInfo> findAllByIds(Integer page, Integer count, String sort, List<UUID> userIds);

    Page<UserInfo> findAllByTrainerAndCountryId(
            Integer page,
            Integer count,
            String sort,
            String order,
            UUID countryId,
            Boolean trainer);

    Page<UserInfo> findAllByTrainerAndAdministrativeAreaLevel1Id(
            Integer page,
            Integer count,
            String sort,
            String order,
            UUID administrativeAreaLevel1Id,
            Boolean trainer);

    Page<UserInfo> findAllByTrainerAndLocalityId(
            Integer page,
            Integer count,
            String sort,
            String order,
            UUID localityId,
            Boolean trainer);

    Page<UserInfo> findAllByTrainerAndSubLocalityLevel1Id(
            Integer page,
            Integer count,
            String sort,
            String order,
            UUID subLocalityLevel1Id,
            Boolean trainer);

    Page<UserInfo> findAllByTrainerAndSubLocalityLevel2Id(
            Integer page,
            Integer count,
            String sort,
            String order,
            UUID subLocalityLevel2Id,
            Boolean trainer);

    Page<UserInfo> findAllByTrainer(
            Integer page, Integer count, String sort, String order, Boolean trainer);
}
