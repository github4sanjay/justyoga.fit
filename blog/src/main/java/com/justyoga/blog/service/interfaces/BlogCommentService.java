package com.justyoga.blog.service.interfaces;

import com.justyoga.blog.domain.model.mysql.BlogComment;
import java.util.List;
import java.util.UUID;

public interface BlogCommentService {
    BlogComment findById(UUID id);

    List<BlogComment> findAllByBlogId(UUID blogId);

    BlogComment save(BlogComment entity);

    boolean delete(UUID id);
}
