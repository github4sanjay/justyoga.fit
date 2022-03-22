package com.justyoga.blog.domain.dao;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

import com.justyoga.blog.domain.model.mysql.BlogComment;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

public interface BlogCommentRepo extends JpaRepository<BlogComment, UUID> {

    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
    @Query(value = "select t from BlogComment t")
    Stream<BlogComment> streamAll();

    List<BlogComment> findAllByBlogId(UUID blogId);
}
