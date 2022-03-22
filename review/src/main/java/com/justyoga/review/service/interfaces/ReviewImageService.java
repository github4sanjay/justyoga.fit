package com.justyoga.review.service.interfaces;

import com.justyoga.review.domain.model.mysql.ReviewImage;
import java.util.List;
import java.util.UUID;

public interface ReviewImageService {
    ReviewImage findById(UUID id);

    List<ReviewImage> findAllByReviewId(UUID reviewId);

    ReviewImage save(ReviewImage entity);

    boolean delete(UUID id);

    Integer deleteByReviewId(UUID reviewId);
}
