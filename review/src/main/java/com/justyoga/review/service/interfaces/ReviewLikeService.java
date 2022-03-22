package com.justyoga.review.service.interfaces;

import com.justyoga.review.domain.model.mysql.ReviewLike;
import java.util.List;
import java.util.UUID;

public interface ReviewLikeService {
    ReviewLike findById(UUID id);

    List<ReviewLike> findAllByReviewId(UUID reviewId);

    ReviewLike save(ReviewLike reviewLike);

    boolean delete(UUID id);

    ReviewLike findByReviewIdAndUserId(UUID reviewId, UUID userId);

    Integer deleteByReviewId(UUID reviewId);
}
