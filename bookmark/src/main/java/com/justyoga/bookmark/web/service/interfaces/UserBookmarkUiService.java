package com.justyoga.bookmark.web.service.interfaces;

import com.justyoga.util.dto.bookmark.UserBookmarkDTO;
import com.justyoga.util.page.PageDTO;
import java.util.List;
import java.util.UUID;

public interface UserBookmarkUiService {

    UserBookmarkDTO save(UserBookmarkDTO reviewDTO, UUID currentUser);

    UserBookmarkDTO findById(UUID id);

    List<UserBookmarkDTO> find(UUID id);

    PageDTO<UserBookmarkDTO> find(
            Integer page,
            Integer count,
            String sortBy,
            String orderBy,
            UUID userId,
            UUID bookmarkedBy);

    Boolean delete(UUID id, UUID currentUser);
}
