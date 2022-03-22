package com.justyoga.review.service.impl;

import com.justyoga.review.domain.dao.ReviewVideoRepo;
import com.justyoga.review.domain.model.mysql.ReviewLike;
import com.justyoga.review.domain.model.mysql.ReviewVideo;
import com.justyoga.review.service.interfaces.ReviewVideoService;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewVideoServiceImpl implements ReviewVideoService {

    private final ReviewVideoRepo reviewVideoRepo;

    @Autowired
    public ReviewVideoServiceImpl(ReviewVideoRepo reviewVideoRepo) {
        this.reviewVideoRepo = reviewVideoRepo;
    }

    @Override
    public ReviewVideo findById(UUID id) {
        return reviewVideoRepo.findById(id).orElse(null);
    }

    @Override
    public List<ReviewVideo> findAllByReviewId(UUID reviewId) {

        return reviewVideoRepo.findAllByReviewId(reviewId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public ReviewVideo save(ReviewVideo entity) {
        return reviewVideoRepo.save(entity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<ReviewVideo> reviewLike = reviewVideoRepo.findById(id);
        if (reviewLike.isPresent()) {
            ReviewVideo entity = reviewLike.get();
            reviewVideoRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Integer deleteByReviewId(UUID reviewId) {
        List<ReviewLike> reviewLikes = reviewVideoRepo.deleteByReviewId(reviewId);
        return reviewLikes.size();
    }
}
