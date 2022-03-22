package com.justyoga.blog.service.interfaces;

import com.justyoga.blog.domain.model.mysql.BlogLike;
import java.util.List;
import java.util.UUID;

public interface BlogLikeService {
    BlogLike findById(UUID id);

    List<BlogLike> findAllByBlogId(UUID blogId);

    BlogLike save(BlogLike blogLike);

    boolean delete(UUID id);
}
