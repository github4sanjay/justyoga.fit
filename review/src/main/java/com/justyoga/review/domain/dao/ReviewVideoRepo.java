package com.justyoga.review.domain.dao;

import com.justyoga.review.domain.model.mysql.ReviewLike;
import com.justyoga.review.domain.model.mysql.ReviewVideo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewVideoRepo extends JpaRepository<ReviewVideo, UUID> {

    List<ReviewVideo> findAllByReviewId(UUID reviewId);

    List<ReviewLike> deleteByReviewId(UUID reviewId);
}
