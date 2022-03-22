package com.justyoga.review.domain.dao;

import com.justyoga.review.domain.model.mysql.ReviewComment;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewCommentRepo extends JpaRepository<ReviewComment, UUID> {

    List<ReviewComment> findAllByReviewId(UUID reviewId);

    List<ReviewComment> deleteByReviewId(UUID reviewId);
}
