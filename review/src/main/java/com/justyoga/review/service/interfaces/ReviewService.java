package com.justyoga.review.service.interfaces;

import com.justyoga.review.domain.model.mysql.Review;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewService {

    Review findById(UUID id);

    @Transactional(propagation = Propagation.REQUIRED)
    boolean delete(UUID id);

    Review save(Review review);

    List<Review> findAllByUserId(UUID placeId);

    Page<Review> findAllByUserId(Integer page, Integer count, String sort, UUID userId);

    Page<Review> findAll(Integer page, Integer count, String sort);
}
