package com.justyoga.blog.domain.dao;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

import com.justyoga.blog.domain.model.mysql.BlogImage;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

public interface BlogImageRepo extends JpaRepository<BlogImage, UUID> {

    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
    @Query(value = "select t from BlogImage t ")
    Stream<BlogImage> streamAll();

    List<BlogImage> findAllByBlogId(UUID blogId);
}
