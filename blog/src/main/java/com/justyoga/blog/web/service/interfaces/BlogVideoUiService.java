package com.justyoga.blog.web.service.interfaces;

import com.justyoga.util.dto.blog.BlogVideoDTO;
import java.util.List;
import java.util.UUID;

public interface BlogVideoUiService {
    BlogVideoDTO findById(UUID id);

    List<BlogVideoDTO> findAllByBlogId(UUID id);

    Boolean delete(UUID id, UUID currentUser);

    BlogVideoDTO save(String image, UUID blogId, UUID userId);
}
