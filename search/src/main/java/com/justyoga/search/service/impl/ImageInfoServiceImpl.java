package com.justyoga.search.service.impl;

import com.justyoga.search.domain.dao.ImageInfoRepository;
import com.justyoga.search.domain.model.ImageInfo;
import com.justyoga.search.service.interfaces.ImageInfoService;
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
public class ImageInfoServiceImpl implements ImageInfoService {
    private final ImageInfoRepository imageInfoRepository;

    @Autowired
    public ImageInfoServiceImpl(ImageInfoRepository imageInfoRepository) {
        this.imageInfoRepository = imageInfoRepository;
    }

    @Override
    public Page<ImageInfo> find(Integer page, Integer count, String sortBy, String orderBy) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return imageInfoRepository.findAll(pageable.get());
    }

    @Override
    public Page<ImageInfo> findAllByIds(
            Integer page, Integer count, String sort, List<UUID> userIds) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);

        return imageInfoRepository.findAllByIdIn(userIds, pageable.get());
    }

    @Override
    public Page<ImageInfo> findAllByTrainerAndCountryId(
            Integer page, Integer count, String sort, UUID countryId, Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return imageInfoRepository.findAllByTrainerAndCountryId(trainer, countryId, pageable.get());
    }

    @Override
    public Page<ImageInfo> findAllByTrainerAndAdministrativeAreaLevel1Id(
            Integer page,
            Integer count,
            String sort,
            UUID administrativeAreaLevel1Id,
            Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return imageInfoRepository.findAllByTrainerAndAdministrativeAreaLevel1Id(
                trainer, administrativeAreaLevel1Id, pageable.get());
    }

    @Override
    public Page<ImageInfo> findAllByTrainerAndLocalityId(
            Integer page, Integer count, String sort, UUID localityId, Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return imageInfoRepository.findAllByTrainerAndLocalityId(
                trainer, localityId, pageable.get());
    }

    @Override
    public Page<ImageInfo> findAllByTrainerAndSubLocalityLevel1Id(
            Integer page, Integer count, String sort, UUID subLocalityLevel1Id, Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return imageInfoRepository.findAllByTrainerAndSubLocalityLevel1Id(
                trainer, subLocalityLevel1Id, pageable.get());
    }

    @Override
    public Page<ImageInfo> findAllByTrainerAndSubLocalityLevel2Id(
            Integer page, Integer count, String sort, UUID subLocalityLevel2Id, Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return imageInfoRepository.findAllByTrainerAndSubLocalityLevel2Id(
                trainer, subLocalityLevel2Id, pageable.get());
    }

    @Override
    public Page<ImageInfo> findAllByTrainer(
            Integer page, Integer count, String sort, Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return imageInfoRepository.findAllByTrainer(trainer, pageable.get());
    }

    @Override
    public Page<ImageInfo> findAllByCountryId(
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
        return imageInfoRepository.findAllByCountryId(countryId, pageable.get());
    }

    @Override
    public Page<ImageInfo> findAllByAdministrativeAreaLevel1Id(
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
        return imageInfoRepository.findAllByAdministrativeAreaLevel1Id(
                administrativeAreaLevel1Id, pageable.get());
    }

    @Override
    public Page<ImageInfo> findAllByLocalityId(
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
        return imageInfoRepository.findAllByLocalityId(localityId, pageable.get());
    }

    @Override
    public Page<ImageInfo> findAllBySubLocalityLevel1Id(
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
        return imageInfoRepository.findAllBySubLocalityLevel1Id(
                subLocalityLevel1Id, pageable.get());
    }

    @Override
    public Page<ImageInfo> findAllBySubLocalityLevel2Id(
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
        return imageInfoRepository.findAllBySubLocalityLevel2Id(
                subLocalityLevel2Id, pageable.get());
    }

    @Override
    public Page<ImageInfo> findAllByUserId(
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
        return imageInfoRepository.findAllByUserId(userId, pageable.get());
    }
}
