package com.justyoga.review.service.interfaces;

import com.justyoga.review.domain.model.mysql.ReviewComment;
import java.util.List;
import java.util.UUID;

public interface ReviewCommentService {
    ReviewComment findById(UUID id);

    List<ReviewComment> findAllByReviewId(UUID reviewId);

    ReviewComment save(ReviewComment entity);

    boolean delete(UUID id);

    Integer deleteByReviewId(UUID reviewId);
}
