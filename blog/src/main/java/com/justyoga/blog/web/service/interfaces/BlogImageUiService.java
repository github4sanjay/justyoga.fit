package com.justyoga.blog.web.service.interfaces;

import com.justyoga.util.dto.blog.BlogImageDTO;
import java.util.List;
import java.util.UUID;

public interface BlogImageUiService {
    BlogImageDTO findById(UUID id);

    List<BlogImageDTO> findAllByBlogId(UUID id);

    Boolean delete(UUID id, UUID currentUser);

    BlogImageDTO save(String image, UUID blogId, UUID userId);
}
