package com.justyoga.bookmark.service.impl;

import com.justyoga.bookmark.domain.dao.UserBookmarkRepo;
import com.justyoga.bookmark.domain.model.mysql.UserBookmark;
import com.justyoga.bookmark.service.interfaces.UserBookmarkService;
import com.justyoga.bookmark.web.config.WebConfig;
import com.justyoga.util.dto.bookmark.UserBookmarkDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.page.PageDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserBookmarkServiceImpl implements UserBookmarkService {

    private final UserBookmarkRepo userBookmarkRepo;

    private final ModelMapper modelMapper;

    @Autowired
    public UserBookmarkServiceImpl(
            UserBookmarkRepo userBookmarkRepo,
            @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper) {
        this.userBookmarkRepo = userBookmarkRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserBookmarkDTO findById(UUID id) {
        Optional<UserBookmark> byId = userBookmarkRepo.findById(id);
        return byId.map(userBookmark -> modelMapper.map(userBookmark, UserBookmarkDTO.class))
                .orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<UserBookmark> repoById = userBookmarkRepo.findById(id);
        if (repoById.isPresent()) {
            userBookmarkRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public UserBookmarkDTO save(UserBookmarkDTO dto) {
        UserBookmark save = userBookmarkRepo.save(modelMapper.map(dto, UserBookmark.class));
        return modelMapper.map(save, UserBookmarkDTO.class);
    }

    @Override
    public List<UserBookmarkDTO> findAllByUserId(UUID placeId) {
        List<UserBookmarkDTO> newList = new ArrayList<>();
        userBookmarkRepo
                .findAllByUserId(placeId)
                .thenAccept(
                        reviews ->
                                reviews.forEach(
                                        userBookmark -> {
                                            UserBookmarkDTO map =
                                                    modelMapper.map(
                                                            userBookmark, UserBookmarkDTO.class);
                                            newList.add(map);
                                        }));
        return newList;
    }

    @Override
    public PageDTO<UserBookmarkDTO> findAllByUserId(
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
        Page<UserBookmark> allByUserId = userBookmarkRepo.findAllByUserId(userId, pageable.get());
        List<UserBookmarkDTO> list = new ArrayList<>();
        allByUserId
                .getContent()
                .forEach(
                        userBookmark -> {
                            UserBookmarkDTO reviewDTO =
                                    modelMapper.map(userBookmark, UserBookmarkDTO.class);
                            list.add(reviewDTO);
                        });
        allByUserId.getTotalElements();

        return PageDTO.<UserBookmarkDTO>builder()
                .content(list)
                .first(allByUserId.isFirst())
                .hasContent(allByUserId.hasContent())
                .hasNext(allByUserId.hasNext())
                .hasPrevious(allByUserId.hasPrevious())
                .last(allByUserId.isLast())
                .number(allByUserId.getNumber())
                .numberOfElements(allByUserId.getNumberOfElements())
                .size(allByUserId.getSize())
                .totalElements(allByUserId.getTotalElements())
                .totalPages(allByUserId.getTotalPages())
                .build();
    }

    @Override
    public PageDTO<UserBookmarkDTO> findAll(
            Integer page, Integer count, String sortBy, String orderBy) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        Page<UserBookmark> allBySubLocalityLevel2Id = userBookmarkRepo.findAll(pageable.get());
        List<UserBookmarkDTO> list = new ArrayList<>();
        allBySubLocalityLevel2Id
                .getContent()
                .forEach(
                        trainer -> {
                            UserBookmarkDTO reviewDTO =
                                    modelMapper.map(trainer, UserBookmarkDTO.class);
                            list.add(reviewDTO);
                        });
        return PageDTO.<UserBookmarkDTO>builder()
                .content(list)
                .first(allBySubLocalityLevel2Id.isFirst())
                .hasContent(allBySubLocalityLevel2Id.hasContent())
                .hasNext(allBySubLocalityLevel2Id.hasNext())
                .hasPrevious(allBySubLocalityLevel2Id.hasPrevious())
                .last(allBySubLocalityLevel2Id.isLast())
                .number(allBySubLocalityLevel2Id.getNumber())
                .numberOfElements(allBySubLocalityLevel2Id.getNumberOfElements())
                .size(allBySubLocalityLevel2Id.getSize())
                .totalElements(allBySubLocalityLevel2Id.getTotalElements())
                .totalPages(allBySubLocalityLevel2Id.getTotalPages())
                .build();
    }

    @Override
    public PageDTO<UserBookmarkDTO> findAllByBookmarkedBy(
            Integer page, Integer count, String sortBy, String orderBy, UUID bookmarkedBy) {
        Sort sort;
        if ("asc".equalsIgnoreCase(orderBy)) {
            sort = Sort.by(sortBy).ascending();
        } else {
            sort = Sort.by(sortBy).descending();
        }
        Optional<Pageable> pageable = PageRequest.of(page, count, sort).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        Page<UserBookmark> allBySubLocalityLevel2Id =
                userBookmarkRepo.findAllByBookmarkedBy(bookmarkedBy, pageable.get());
        List<UserBookmarkDTO> list = new ArrayList<>();
        allBySubLocalityLevel2Id
                .getContent()
                .forEach(
                        trainer -> {
                            UserBookmarkDTO reviewDTO =
                                    modelMapper.map(trainer, UserBookmarkDTO.class);
                            list.add(reviewDTO);
                        });
        return PageDTO.<UserBookmarkDTO>builder()
                .content(list)
                .first(allBySubLocalityLevel2Id.isFirst())
                .hasContent(allBySubLocalityLevel2Id.hasContent())
                .hasNext(allBySubLocalityLevel2Id.hasNext())
                .hasPrevious(allBySubLocalityLevel2Id.hasPrevious())
                .last(allBySubLocalityLevel2Id.isLast())
                .number(allBySubLocalityLevel2Id.getNumber())
                .numberOfElements(allBySubLocalityLevel2Id.getNumberOfElements())
                .size(allBySubLocalityLevel2Id.getSize())
                .totalElements(allBySubLocalityLevel2Id.getTotalElements())
                .totalPages(allBySubLocalityLevel2Id.getTotalPages())
                .build();
    }
}
