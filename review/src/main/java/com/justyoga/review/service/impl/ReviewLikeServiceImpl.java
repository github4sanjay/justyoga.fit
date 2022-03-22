package com.justyoga.review.service.impl;

import com.justyoga.review.domain.dao.ReviewLikeRepo;
import com.justyoga.review.domain.model.mysql.ReviewLike;
import com.justyoga.review.service.interfaces.ReviewLikeService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewLikeServiceImpl implements ReviewLikeService {

    private final ReviewLikeRepo reviewLikeRepo;

    @Autowired
    public ReviewLikeServiceImpl(ReviewLikeRepo reviewLikeRepo) {
        this.reviewLikeRepo = reviewLikeRepo;
    }

    @Override
    public ReviewLike findById(UUID id) {
        return reviewLikeRepo.findById(id).orElse(null);
    }

    @Override
    public List<ReviewLike> findAllByReviewId(UUID reviewId) {
        return reviewLikeRepo.findAllByReviewId(reviewId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ReviewLike save(ReviewLike like) {
        return reviewLikeRepo.saveAndFlush(like);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<ReviewLike> reviewLike = reviewLikeRepo.findById(id);
        if (reviewLike.isPresent()) {
            ReviewLike like = reviewLike.get();
            reviewLikeRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ReviewLike findByReviewIdAndUserId(UUID reviewId, UUID userId) {
        return reviewLikeRepo.findByReviewIdAndUserId(reviewId, userId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteByReviewId(UUID reviewId) {
        List<ReviewLike> reviewComments = reviewLikeRepo.deleteByReviewId(reviewId);
        return reviewComments.size();
    }
}
