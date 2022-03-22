package com.justyoga.search.service.impl;

import com.justyoga.search.domain.dao.BlogInfoRepository;
import com.justyoga.search.domain.model.BlogInfo;
import com.justyoga.search.service.interfaces.BlogInfoService;
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
public class BlogInfoServiceImpl implements BlogInfoService {
    private final BlogInfoRepository blogInfoRepository;

    @Autowired
    public BlogInfoServiceImpl(BlogInfoRepository blogInfoRepository) {
        this.blogInfoRepository = blogInfoRepository;
    }

    @Override
    public Page<BlogInfo> find(Integer page, Integer count, String sortBy, String orderBy) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogInfoRepository.findAll(pageable.get());
    }

    @Override
    public Page<BlogInfo> findAllByIds(
            Integer page, Integer count, String sort, List<UUID> userIds) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogInfoRepository.findAllByIdIn(userIds, pageable.get());
    }

    @Override
    public Page<BlogInfo> findAllByTrainerAndCountryId(
            Integer page, Integer count, String sort, UUID countryId, Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogInfoRepository.findAllByTrainerAndCountryId(trainer, countryId, pageable.get());
    }

    @Override
    public Page<BlogInfo> findAllByTrainerAndAdministrativeAreaLevel1Id(
            Integer page,
            Integer count,
            String sort,
            UUID administrativeAreaLevel1Id,
            Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogInfoRepository.findAllByTrainerAndAdministrativeAreaLevel1Id(
                trainer, administrativeAreaLevel1Id, pageable.get());
    }

    @Override
    public Page<BlogInfo> findAllByTrainerAndLocalityId(
            Integer page, Integer count, String sort, UUID localityId, Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogInfoRepository.findAllByTrainerAndLocalityId(
                trainer, localityId, pageable.get());
    }

    @Override
    public Page<BlogInfo> findAllByTrainerAndSubLocalityLevel1Id(
            Integer page, Integer count, String sort, UUID subLocalityLevel1Id, Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogInfoRepository.findAllByTrainerAndSubLocalityLevel1Id(
                trainer, subLocalityLevel1Id, pageable.get());
    }

    @Override
    public Page<BlogInfo> findAllByTrainerAndSubLocalityLevel2Id(
            Integer page, Integer count, String sort, UUID subLocalityLevel2Id, Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogInfoRepository.findAllByTrainerAndSubLocalityLevel2Id(
                trainer, subLocalityLevel2Id, pageable.get());
    }

    @Override
    public Page<BlogInfo> findAllByTrainer(
            Integer page, Integer count, String sort, Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogInfoRepository.findAllByTrainer(trainer, pageable.get());
    }

    @Override
    public Page<BlogInfo> findAllByCountryId(
            Integer page, Integer count, String sortBy, String orderBy, UUID countryId) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogInfoRepository.findAllByCountryId(countryId, pageable.get());
    }

    @Override
    public Page<BlogInfo> findAllByAdministrativeAreaLevel1Id(
            Integer page,
            Integer count,
            String sortBy,
            String orderBy,
            UUID administrativeAreaLevel1Id) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogInfoRepository.findAllByAdministrativeAreaLevel1Id(
                administrativeAreaLevel1Id, pageable.get());
    }

    @Override
    public Page<BlogInfo> findAllByLocalityId(
            Integer page, Integer count, String sortBy, String orderBy, UUID localityId) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogInfoRepository.findAllByLocalityId(localityId, pageable.get());
    }

    @Override
    public Page<BlogInfo> findAllBySubLocalityLevel1Id(
            Integer page, Integer count, String sortBy, String orderBy, UUID subLocalityLevel1Id) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogInfoRepository.findAllBySubLocalityLevel1Id(subLocalityLevel1Id, pageable.get());
    }

    @Override
    public Page<BlogInfo> findAllBySubLocalityLevel2Id(
            Integer page, Integer count, String sortBy, String orderBy, UUID subLocalityLevel2Id) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogInfoRepository.findAllBySubLocalityLevel2Id(subLocalityLevel2Id, pageable.get());
    }

    @Override
    public Page<BlogInfo> findAllByUserId(
            Integer page, Integer count, String sortBy, String orderBy, UUID userId) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return blogInfoRepository.findAllByUserId(userId, pageable.get());
    }
}
