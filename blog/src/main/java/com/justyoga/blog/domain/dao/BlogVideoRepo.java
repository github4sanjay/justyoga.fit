package com.justyoga.blog.domain.dao;

import com.justyoga.blog.domain.model.mysql.BlogVideo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogVideoRepo extends JpaRepository<BlogVideo, UUID> {
    List<BlogVideo> findAllByBlogId(UUID blogId);
}
