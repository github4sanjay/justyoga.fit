package com.justyoga.blog.service.interfaces;

import com.justyoga.blog.domain.model.mysql.BlogVideo;
import java.util.List;
import java.util.UUID;

public interface BlogVideoService {
    BlogVideo findById(UUID id);

    List<BlogVideo> findAllByBlogId(UUID rlogId);

    BlogVideo save(BlogVideo entity);

    boolean delete(UUID id);
}
