package com.justyoga.search.service.impl;

import com.justyoga.search.domain.dao.VideoInfoRepository;
import com.justyoga.search.domain.model.VideoInfo;
import com.justyoga.search.service.interfaces.VideoInfoService;
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
public class VideoInfoServiceImpl implements VideoInfoService {
    private final VideoInfoRepository videoInfoRepository;

    @Autowired
    public VideoInfoServiceImpl(VideoInfoRepository videoInfoRepository) {
        this.videoInfoRepository = videoInfoRepository;
    }

    @Override
    public Page<VideoInfo> find(Integer page, Integer count, String sortBy, String orderBy) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return videoInfoRepository.findAll(pageable.get());
    }

    @Override
    public Page<VideoInfo> findAllByIds(
            Integer page, Integer count, String sort, List<UUID> userIds) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return videoInfoRepository.findAllByIdIn(userIds, pageable.get());
    }

    @Override
    public Page<VideoInfo> findAllByTrainerAndCountryId(
            Integer page, Integer count, String sort, UUID countryId, Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return videoInfoRepository.findAllByTrainerAndCountryId(trainer, countryId, pageable.get());
    }

    @Override
    public Page<VideoInfo> findAllByTrainerAndAdministrativeAreaLevel1Id(
            Integer page,
            Integer count,
            String sort,
            UUID administrativeAreaLevel1Id,
            Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return videoInfoRepository.findAllByTrainerAndAdministrativeAreaLevel1Id(
                trainer, administrativeAreaLevel1Id, pageable.get());
    }

    @Override
    public Page<VideoInfo> findAllByTrainerAndLocalityId(
            Integer page, Integer count, String sort, UUID localityId, Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return videoInfoRepository.findAllByTrainerAndLocalityId(
                trainer, localityId, pageable.get());
    }

    @Override
    public Page<VideoInfo> findAllByTrainerAndSubLocalityLevel1Id(
            Integer page, Integer count, String sort, UUID subLocalityLevel1Id, Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return videoInfoRepository.findAllByTrainerAndSubLocalityLevel1Id(
                trainer, subLocalityLevel1Id, pageable.get());
    }

    @Override
    public Page<VideoInfo> findAllByTrainerAndSubLocalityLevel2Id(
            Integer page, Integer count, String sort, UUID subLocalityLevel2Id, Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return videoInfoRepository.findAllByTrainerAndSubLocalityLevel2Id(
                trainer, subLocalityLevel2Id, pageable.get());
    }

    @Override
    public Page<VideoInfo> findAllByTrainer(
            Integer page, Integer count, String sort, Boolean trainer) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return videoInfoRepository.findAllByTrainer(trainer, pageable.get());
    }

    @Override
    public Page<VideoInfo> findAllByCountryId(
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
        return videoInfoRepository.findAllByCountryId(countryId, pageable.get());
    }

    @Override
    public Page<VideoInfo> findAllByAdministrativeAreaLevel1Id(
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
        return videoInfoRepository.findAllByAdministrativeAreaLevel1Id(
                administrativeAreaLevel1Id, pageable.get());
    }

    @Override
    public Page<VideoInfo> findAllByLocalityId(
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
        return videoInfoRepository.findAllByLocalityId(localityId, pageable.get());
    }

    @Override
    public Page<VideoInfo> findAllBySubLocalityLevel1Id(
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
        return videoInfoRepository.findAllBySubLocalityLevel1Id(
                subLocalityLevel1Id, pageable.get());
    }

    @Override
    public Page<VideoInfo> findAllBySubLocalityLevel2Id(
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
        return videoInfoRepository.findAllBySubLocalityLevel2Id(
                subLocalityLevel2Id, pageable.get());
    }

    @Override
    public Page<VideoInfo> findAllByUserId(
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
        return videoInfoRepository.findAllByUserId(userId, pageable.get());
    }
}
