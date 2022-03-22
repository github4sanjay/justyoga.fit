package com.justyoga.review.service.impl;

import com.justyoga.review.domain.dao.ReviewImageRepo;
import com.justyoga.review.domain.model.mysql.ReviewImage;
import com.justyoga.review.service.interfaces.ReviewImageService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewImageServiceImpl implements ReviewImageService {

    private final ReviewImageRepo reviewImageRepo;

    @Autowired
    public ReviewImageServiceImpl(ReviewImageRepo reviewImageRepo) {
        this.reviewImageRepo = reviewImageRepo;
    }

    @Override
    public ReviewImage findById(UUID id) {
        return reviewImageRepo.findById(id).orElse(null);
    }

    @Override
    public List<ReviewImage> findAllByReviewId(UUID reviewId) {
        return reviewImageRepo.findAllByReviewId(reviewId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ReviewImage save(ReviewImage image) {
        return reviewImageRepo.save(image);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<ReviewImage> reviewLike = reviewImageRepo.findById(id);
        if (reviewLike.isPresent()) {
            reviewImageRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteByReviewId(UUID reviewId) {
        List<ReviewImage> reviewComments = reviewImageRepo.deleteByReviewId(reviewId);
        return reviewComments.size();
    }
}
