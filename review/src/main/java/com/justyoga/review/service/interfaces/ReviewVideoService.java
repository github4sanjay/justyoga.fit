package com.justyoga.review.service.interfaces;

import com.justyoga.review.domain.model.mysql.ReviewVideo;
import java.util.List;
import java.util.UUID;

public interface ReviewVideoService {
    ReviewVideo findById(UUID id);

    List<ReviewVideo> findAllByReviewId(UUID reviewId);

    ReviewVideo save(ReviewVideo entity);

    boolean delete(UUID id);

    Integer deleteByReviewId(UUID reviewId);
}
