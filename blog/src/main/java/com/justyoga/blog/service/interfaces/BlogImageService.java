package com.justyoga.blog.service.interfaces;

import com.justyoga.blog.domain.model.mysql.BlogImage;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface BlogImageService {
    BlogImage findById(UUID id);

    List<BlogImage> findAllByBlogId(UUID blogId);

    BlogImage save(BlogImage entity);

    List<BlogImage> saveAll(Collection<BlogImage> blogImageMappings);

    boolean delete(UUID id);
}
