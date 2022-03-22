package com.justyoga.bookmark.domain.dao;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

import com.justyoga.bookmark.domain.model.mysql.UserBookmark;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import javax.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.scheduling.annotation.Async;

public interface UserBookmarkRepo extends JpaRepository<UserBookmark, UUID> {

    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
    @Query(value = "select t from UserBookmark t ")
    Stream<UserBookmark> streamAll();

    @Async
    CompletableFuture<List<UserBookmark>> findAllByUserId(UUID placeId);

    Page<UserBookmark> findAllByUserId(UUID userId, Pageable pageable);

    Page<UserBookmark> findAllByBookmarkedBy(UUID bookmarkedBy, Pageable pageable);
}
