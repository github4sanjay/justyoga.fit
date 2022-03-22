package com.justyoga.search.service.impl;

import com.justyoga.search.domain.dao.UserInfoRepository;
import com.justyoga.search.domain.model.UserInfo;
import com.justyoga.search.service.interfaces.UserInfoService;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public Page<UserInfo> find(Integer page, Integer count, String sortBy) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return userInfoRepository.findAll(pageable.get());
    }

    @Override
    public Page<UserInfo> findAllByIds(
            Integer page, Integer count, String sortBy, List<UUID> userIds) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return userInfoRepository.findAllByIdIn(userIds, pageable.get());
    }

    @Override
    public Page<UserInfo> findAllByTrainerAndCountryId(
            Integer page,
            Integer count,
            String sortBy,
            String orderBy,
            UUID countryId,
            Boolean trainer) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return userInfoRepository.findAllByTrainerAndCountryId(trainer, countryId, pageable.get());
    }

    @Override
    public Page<UserInfo> findAllByTrainerAndAdministrativeAreaLevel1Id(
            Integer page,
            Integer count,
            String sortBy,
            String orderBy,
            UUID administrativeAreaLevel1Id,
            Boolean trainer) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return userInfoRepository.findAllByTrainerAndAdministrativeAreaLevel1Id(
                trainer, administrativeAreaLevel1Id, pageable.get());
    }

    @Override
    public Page<UserInfo> findAllByTrainerAndLocalityId(
            Integer page,
            Integer count,
            String sortBy,
            String orderBy,
            UUID localityId,
            Boolean trainer) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return userInfoRepository.findAllByTrainerAndLocalityId(
                trainer, localityId, pageable.get());
    }

    @Override
    public Page<UserInfo> findAllByTrainerAndSubLocalityLevel1Id(
            Integer page,
            Integer count,
            String sortBy,
            String orderBy,
            UUID subLocalityLevel1Id,
            Boolean trainer) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return userInfoRepository.findAllByTrainerAndSubLocalityLevel1Id(
                trainer, subLocalityLevel1Id, pageable.get());
    }

    @Override
    public Page<UserInfo> findAllByTrainerAndSubLocalityLevel2Id(
            Integer page,
            Integer count,
            String sortBy,
            String orderBy,
            UUID subLocalityLevel2Id,
            Boolean trainer) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return userInfoRepository.findAllByTrainerAndSubLocalityLevel2Id(
                trainer, subLocalityLevel2Id, pageable.get());
    }

    @Override
    public Page<UserInfo> findAllByTrainer(
            Integer page, Integer count, String sortBy, String orderBy, Boolean trainer) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return userInfoRepository.findAllByTrainer(trainer, pageable.get());
    }
}
