package com.justyoga.review.web.service.impl;

import com.justyoga.review.domain.model.mysql.Review;
import com.justyoga.review.domain.model.mysql.ReviewLike;
import com.justyoga.review.service.interfaces.ReviewLikeService;
import com.justyoga.review.service.interfaces.ReviewService;
import com.justyoga.review.web.service.interfaces.ReviewLikeUiService;
import com.justyoga.util.dto.review.ReviewLikeDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewLikeUiServiceImpl implements ReviewLikeUiService {

    private final ReviewLikeService reviewLikeService;
    private final ReviewService reviewService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewLikeUiServiceImpl(
            ReviewLikeService reviewLikeService,
            ReviewService reviewService,
            ModelMapper modelMapper) {
        this.reviewLikeService = reviewLikeService;
        this.reviewService = reviewService;
        this.modelMapper = modelMapper;
    }

    private ReviewLikeDTO convert(ReviewLike entity) {
        return modelMapper.map(entity, ReviewLikeDTO.class);
    }

    private ReviewLike convert(ReviewLikeDTO dto) {
        return modelMapper.map(dto, ReviewLike.class);
    }

    @Override
    public ReviewLikeDTO findById(UUID id) {
        var like = reviewLikeService.findById(id);
        if (like == null) throw new AppException("Like not found", AppStatusCode.NOT_FOUND);
        else return convert(like);
    }

    @Override
    public List<ReviewLikeDTO> findAllByReviewId(UUID id) {
        return reviewLikeService.findAllByReviewId(id).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(UUID id, UUID currentUser) {
        ReviewLike reviewLike = reviewLikeService.findById(id);
        if (reviewLike == null) throw new AppException("Like not found", AppStatusCode.NOT_FOUND);
        if (!currentUser.equals(reviewLike.getUserId())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return reviewLikeService.delete(id);
    }

    @Override
    public ReviewLikeDTO save(ReviewLikeDTO reviewCommentDTO, UUID currentUser) {

        if (!currentUser.equals(reviewCommentDTO.getUserId())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        Review review = reviewService.findById(reviewCommentDTO.getReviewId());

        if (review == null)
            throw new AppException("Review not found", AppStatusCode.INVALID_REQUEST);
        ReviewLike byReviewIdAndUserId =
                reviewLikeService.findByReviewIdAndUserId(
                        reviewCommentDTO.getReviewId(), reviewCommentDTO.getUserId());
        if (byReviewIdAndUserId != null)
            throw new AppException("User already liked this review", AppStatusCode.INVALID_REQUEST);
        var like = reviewLikeService.save(convert(reviewCommentDTO));
        return convert(like);
    }
}
