package com.justyoga.blog.service.interfaces;

import com.justyoga.blog.domain.model.mysql.Blog;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface BlogService {

    Blog findById(UUID id);

    boolean delete(UUID id);

    Blog save(Blog blog);

    List<Blog> findAllByUserId(UUID placeId);

    Page<Blog> findAllByUserId(Integer page, Integer count, String sort, UUID userId);

    Page<Blog> findAll(Integer page, Integer count, String sort);
}
