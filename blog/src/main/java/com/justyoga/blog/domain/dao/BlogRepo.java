package com.justyoga.blog.domain.dao;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

import com.justyoga.blog.domain.model.mysql.Blog;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import javax.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

public interface BlogRepo extends JpaRepository<Blog, UUID> {

    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
    @Query(value = "select t from Blog t ")
    Stream<Blog> streamAll();

    List<Blog> findAllByUserId(UUID userId);

    Page<Blog> findAllByUserId(UUID userId, Pageable pageable);
}
