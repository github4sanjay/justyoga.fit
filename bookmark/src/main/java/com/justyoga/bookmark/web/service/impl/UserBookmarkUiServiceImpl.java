package com.justyoga.bookmark.web.service.impl;

import com.justyoga.bookmark.service.interfaces.UserBookmarkService;
import com.justyoga.bookmark.web.service.interfaces.UserBookmarkUiService;
import com.justyoga.util.dto.bookmark.UserBookmarkDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.page.PageDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBookmarkUiServiceImpl implements UserBookmarkUiService {

    private final UserBookmarkService userBookmarkService;

    @Autowired
    public UserBookmarkUiServiceImpl(UserBookmarkService userBookmarkService) {
        this.userBookmarkService = userBookmarkService;
    }

    @Override
    public UserBookmarkDTO save(UserBookmarkDTO userBookmarkDTO, UUID currentUser) {
        if (currentUser == null)
            throw new AppException("Current user is required", AppStatusCode.UNAUTHORIZED_ERROR);
        if (userBookmarkDTO == null)
            throw new AppException("Review is required", AppStatusCode.INVALID_REQUEST);
        if (userBookmarkDTO.getBookmarkedBy() == null)
            throw new AppException("Bookmarked by is required", AppStatusCode.INVALID_REQUEST);
        if (userBookmarkDTO.getUserId() == null)
            throw new AppException("Bookmark user id is required", AppStatusCode.INVALID_REQUEST);

        if (currentUser.equals(userBookmarkDTO.getUserId())) {
            throw new AppException(
                    "Not allowed to review yourself", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        if (!currentUser.equals(userBookmarkDTO.getBookmarkedBy())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }

        return userBookmarkService.save(userBookmarkDTO);
    }

    @Override
    public UserBookmarkDTO findById(UUID id) {
        return userBookmarkService.findById(id);
    }

    @Override
    public List<UserBookmarkDTO> find(UUID id) {
        return userBookmarkService.findAllByUserId(id);
    }

    @Override
    public PageDTO<UserBookmarkDTO> find(
            Integer page,
            Integer count,
            String sortBy,
            String orderBy,
            UUID userId,
            UUID bookmarkedBy) {
        if (page == null) page = 0;
        if (count == null) count = 10;
        if (userId != null) {
            return userBookmarkService.findAllByUserId(page, count, sortBy, orderBy, userId);
        }
        if (bookmarkedBy != null) {
            return userBookmarkService.findAllByBookmarkedBy(
                    page, count, sortBy, orderBy, bookmarkedBy);
        }
        return userBookmarkService.findAll(page, count, sortBy, orderBy);
    }

    @Override
    public Boolean delete(UUID id, UUID currentUser) {
        if (currentUser == null)
            throw new AppException("Current user is required", AppStatusCode.UNAUTHORIZED_ERROR);
        if (id == null)
            throw new AppException("Review id is required", AppStatusCode.INVALID_REQUEST);
        UserBookmarkDTO byId = userBookmarkService.findById(id);
        if (!currentUser.equals(byId.getBookmarkedBy())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return userBookmarkService.delete(id);
    }
}
