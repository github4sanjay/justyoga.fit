package com.justyoga.review.service.impl;

import com.justyoga.review.domain.dao.ReviewCommentRepo;
import com.justyoga.review.domain.model.mysql.ReviewComment;
import com.justyoga.review.service.interfaces.ReviewCommentService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewCommentServiceImpl implements ReviewCommentService {

    private final ReviewCommentRepo reviewCommentRepo;

    @Autowired
    public ReviewCommentServiceImpl(ReviewCommentRepo reviewCommentRepo) {
        this.reviewCommentRepo = reviewCommentRepo;
    }

    @Override
    public ReviewComment findById(UUID id) {
        return reviewCommentRepo.findById(id).orElse(null);
    }

    @Override
    public List<ReviewComment> findAllByReviewId(UUID reviewId) {
        return reviewCommentRepo.findAllByReviewId(reviewId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ReviewComment save(ReviewComment reviewComment) {
        return reviewCommentRepo.saveAndFlush(reviewComment);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<ReviewComment> reviewLike = reviewCommentRepo.findById(id);
        if (reviewLike.isPresent()) {
            reviewCommentRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteByReviewId(UUID reviewId) {
        List<ReviewComment> reviewComments = reviewCommentRepo.deleteByReviewId(reviewId);
        return reviewComments.size();
    }
}
