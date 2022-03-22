package com.justyoga.review.domain.dao;

import com.justyoga.review.domain.model.mysql.Review;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, UUID> {

    List<Review> findAllByUserId(UUID userId);

    Page<Review> findAllByUserId(UUID userId, Pageable pageable);
}
