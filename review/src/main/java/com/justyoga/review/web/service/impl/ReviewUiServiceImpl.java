package com.justyoga.review.web.service.impl;

import com.justyoga.review.domain.model.mysql.Review;
import com.justyoga.review.service.interfaces.*;
import com.justyoga.review.web.config.WebConfig;
import com.justyoga.review.web.service.interfaces.ReviewUiService;
import com.justyoga.util.dto.review.ReviewDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.page.PageDTO;
import com.justyoga.util.string.StringUtil;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class ReviewUiServiceImpl implements ReviewUiService {

    private final ReviewService reviewService;
    private final ReviewImageService reviewImageService;
    private final ReviewVideoService reviewVideoService;
    private final ReviewLikeService reviewLikeService;
    private final ReviewCommentService reviewCommentService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewUiServiceImpl(
            ReviewService reviewService,
            ReviewImageService reviewImageService,
            ReviewVideoService reviewVideoService,
            ReviewLikeService reviewLikeService,
            ReviewCommentService reviewCommentService,
            @Qualifier(WebConfig.MODEL_MAPPER) ModelMapper modelMapper) {
        this.reviewService = reviewService;
        this.reviewImageService = reviewImageService;
        this.reviewVideoService = reviewVideoService;
        this.reviewLikeService = reviewLikeService;
        this.reviewCommentService = reviewCommentService;
        this.modelMapper = modelMapper;
    }

    private ReviewDTO convert(Review entity) {
        ReviewDTO reviewDTO = modelMapper.map(entity, ReviewDTO.class);
        reviewDTO.setReviewContent(
                StringUtil.getStringFromBase64(
                        reviewDTO.getReviewContent())); // converting to string from Base64
        reviewDTO.setReviewText(StringUtil.getStringFromBase64(reviewDTO.getReviewText()));
        return reviewDTO;
    }

    private Review convert(ReviewDTO reviewDTO) {
        reviewDTO.setReviewContent(StringUtil.getBase64ForString(reviewDTO.getReviewContent()));
        reviewDTO.setReviewText(StringUtil.getBase64ForString(reviewDTO.getReviewText()));
        return modelMapper.map(reviewDTO, Review.class);
    }

    @Override
    public ReviewDTO save(ReviewDTO reviewDTO, UUID currentUser) {

        if (currentUser.equals(reviewDTO.getUserId())) {
            throw new AppException(
                    "Not allowed to review yourself", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        if (!currentUser.equals(reviewDTO.getReviewedBy())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }

        var review = reviewService.save(convert(reviewDTO));
        return convert(review);
    }

    @Override
    public ReviewDTO findById(UUID id) {
        var review = reviewService.findById(id);
        if (review == null) throw new AppException("Review not found", AppStatusCode.NOT_FOUND);
        else return convert(review);
    }

    @Override
    public List<ReviewDTO> find(UUID id) {
        return reviewService.findAllByUserId(id).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public PageDTO<ReviewDTO> find(Integer page, Integer count, String sort, UUID userId) {
        if (page == null) page = 0;
        if (count == null) count = 10;
        Page<Review> reviews;
        if (userId != null) {
            reviews = reviewService.findAllByUserId(page, count, sort, userId);
        } else {
            reviews = reviewService.findAll(page, count, sort);
        }
        return PageDTO.<ReviewDTO>builder()
                .content(
                        reviews.getContent().stream()
                                .map(this::convert)
                                .collect(Collectors.toList()))
                .first(reviews.isFirst())
                .hasContent(reviews.hasContent())
                .hasNext(reviews.hasNext())
                .hasPrevious(reviews.hasPrevious())
                .last(reviews.isLast())
                .number(reviews.getNumber())
                .numberOfElements(reviews.getNumberOfElements())
                .size(reviews.getSize())
                .totalElements(reviews.getTotalElements())
                .totalPages(reviews.getTotalPages())
                .build();
    }

    @Override
    public Boolean delete(UUID id, UUID currentUser) {
        Review byId = reviewService.findById(id);
        if (!currentUser.equals(byId.getReviewedBy())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        reviewImageService.deleteByReviewId(id);
        reviewCommentService.deleteByReviewId(id);
        reviewLikeService.deleteByReviewId(id);
        reviewVideoService.deleteByReviewId(id);
        return reviewService.delete(id);
    }
}
