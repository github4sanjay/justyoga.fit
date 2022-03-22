package com.justyoga.review.service.impl;

import com.justyoga.review.domain.dao.ReviewRepo;
import com.justyoga.review.domain.model.mysql.Review;
import com.justyoga.review.service.interfaces.ReviewService;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepo reviewRepo;

    @Autowired
    public ReviewServiceImpl(ReviewRepo reviewRepo) {
        this.reviewRepo = reviewRepo;
    }

    @Override
    public Review findById(UUID id) {
        return reviewRepo.findById(id).orElse(null);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean delete(UUID id) {
        Optional<Review> repoById = reviewRepo.findById(id);
        if (repoById.isPresent()) {
            reviewRepo.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Review save(Review review) {
        return reviewRepo.save(review);
    }

    @Override
    public List<Review> findAllByUserId(UUID placeId) {
        return reviewRepo.findAllByUserId(placeId);
    }

    @Override
    public Page<Review> findAllByUserId(Integer page, Integer count, String sort, UUID userId) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return reviewRepo.findAllByUserId(userId, pageable.get());
    }

    @Override
    public Page<Review> findAll(Integer page, Integer count, String sort) {
        Optional<Pageable> pageable = PageRequest.of(page, count).toOptional();
        if (pageable.isEmpty())
            throw new AppException("Page or count not valid", AppStatusCode.INVALID_REQUEST);
        return reviewRepo.findAll(pageable.get());
    }
}
