package com.justyoga.bookmark.service.interfaces;

import com.justyoga.util.dto.bookmark.UserBookmarkDTO;
import com.justyoga.util.page.PageDTO;
import java.util.List;
import java.util.UUID;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface UserBookmarkService {

    UserBookmarkDTO findById(UUID id);

    @Transactional(propagation = Propagation.REQUIRED)
    boolean delete(UUID id);

    UserBookmarkDTO save(UserBookmarkDTO review);

    List<UserBookmarkDTO> findAllByUserId(UUID placeId);

    PageDTO<UserBookmarkDTO> findAllByUserId(
            Integer page, Integer count, String sortBy, String orderBy, UUID userId);

    PageDTO<UserBookmarkDTO> findAll(Integer page, Integer count, String sortBy, String orderBy);

    PageDTO<UserBookmarkDTO> findAllByBookmarkedBy(
            Integer page, Integer count, String sortBy, String orderBy, UUID bookmarkedBy);
}
