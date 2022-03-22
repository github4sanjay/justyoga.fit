package com.justyoga.review.domain.dao;

import com.justyoga.review.domain.model.mysql.ReviewLike;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepo extends JpaRepository<ReviewLike, UUID> {

    List<ReviewLike> findAllByReviewId(UUID reviewId);

    ReviewLike findByReviewIdAndUserId(UUID reviewId, UUID userId);

    List<ReviewLike> deleteByReviewId(UUID reviewId);
}
