package com.justyoga.blog.web.service.interfaces;

import com.justyoga.util.dto.blog.BlogCommentDTO;
import java.util.List;
import java.util.UUID;

public interface BlogCommentUiService {
    BlogCommentDTO save(BlogCommentDTO blogCommentDTO, UUID userId);

    BlogCommentDTO findById(UUID id);

    List<BlogCommentDTO> findAllByBlogId(UUID id);

    Boolean delete(UUID id, UUID currentUser);
}
