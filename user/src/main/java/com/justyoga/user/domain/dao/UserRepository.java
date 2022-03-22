package com.justyoga.user.domain.dao;

import com.justyoga.user.domain.model.mysql.User;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByFirebaseUid(String firebaseUid);

    User findTopByFirebaseUid(String firebaseUid);

    @Query("select u from User u")
    Stream<User> streamAllUser();

    @Async
    CompletableFuture<List<User>> findByFirebaseUidIn(Collection<String> firebaseIds);

    @Async
    CompletableFuture<List<User>> findByIdIn(Collection<UUID> ids);

    Page<User> findAllByIdIn(Collection<UUID> ids, Pageable pageable);
}
