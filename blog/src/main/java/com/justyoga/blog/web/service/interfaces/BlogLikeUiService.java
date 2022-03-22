package com.justyoga.blog.web.service.interfaces;

import com.justyoga.util.dto.blog.BlogLikeDTO;
import java.util.List;
import java.util.UUID;

public interface BlogLikeUiService {
    BlogLikeDTO save(BlogLikeDTO blogCommentDTO, UUID userId);

    BlogLikeDTO findById(UUID id);

    List<BlogLikeDTO> findAllByBlogId(UUID id);

    Boolean delete(UUID id, UUID currentUser);
}
