package com.justyoga.review.domain.dao;

import com.justyoga.review.domain.model.mysql.ReviewImage;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewImageRepo extends JpaRepository<ReviewImage, UUID> {

    List<ReviewImage> findAllByReviewId(UUID reviewId);

    List<ReviewImage> deleteByReviewId(UUID reviewId);
}
