package com.justyoga.profile.service.impl;

import com.justyoga.profile.domain.dao.UserBasicInfoRepository;
import com.justyoga.profile.domain.model.mysql.UserBasicInfo;
import com.justyoga.profile.service.interfaces.UserBasicInfoService;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.Optional;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class BasicInfoServiceImpl implements UserBasicInfoService {

    private final UserBasicInfoRepository userBasicInfoRepository;

    @Autowired
    public BasicInfoServiceImpl(UserBasicInfoRepository userBasicInfoRepository) {
        this.userBasicInfoRepository = userBasicInfoRepository;
    }

    @Override
    public UserBasicInfo findById(UUID id) {
        return userBasicInfoRepository.findById(id).orElse(null);
    }

    @Override
    public UserBasicInfo findByUserId(UUID id) {
        return userBasicInfoRepository.findByUserId(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserBasicInfo save(UserBasicInfo userBasicInfo) {
        return userBasicInfoRepository.save(userBasicInfo);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<UserBasicInfo> trainer = userBasicInfoRepository.findById(id);
        if (trainer.isPresent()) {
            userBasicInfoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Page<UserBasicInfo> findAllBySubLocalityLevel2Id(
            UUID subLocalityLevel2Id, Integer page, Integer count, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return userBasicInfoRepository.findAllBySubLocalityLevel2Id(
                subLocalityLevel2Id, pageable.get());
    }

    @Override
    public Page<UserBasicInfo> findAllBySubLocalityLevel1Id(
            UUID subLocalityLevel1Id, Integer page, Integer count, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return userBasicInfoRepository.findAllBySubLocalityLevel1Id(
                subLocalityLevel1Id, pageable.get());
    }

    @Override
    public Page<UserBasicInfo> findAllByLocalityId(
            UUID id, Integer page, Integer count, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return userBasicInfoRepository.findAllByLocalityId(id, pageable.get());
    }

    @Override
    public Page<UserBasicInfo> findAllByAdministrativeAreaLevel1Id(
            UUID id, Integer page, Integer count, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return userBasicInfoRepository.findAllByAdministrativeAreaLevel1Id(id, pageable.get());
    }

    @Override
    public Page<UserBasicInfo> findAllByCountryId(
            UUID id, Integer page, Integer count, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return userBasicInfoRepository.findAllByCountryId(id, pageable.get());
    }

    @Override
    public Page<UserBasicInfo> findAll(Integer page, Integer count, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return userBasicInfoRepository.findAll(pageable.get());
    }
}
