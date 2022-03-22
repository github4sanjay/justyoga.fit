package com.justyoga.review.web.service.impl;

import com.justyoga.review.domain.model.mysql.Review;
import com.justyoga.review.domain.model.mysql.ReviewComment;
import com.justyoga.review.service.interfaces.ReviewCommentService;
import com.justyoga.review.service.interfaces.ReviewService;
import com.justyoga.review.web.service.interfaces.ReviewCommentUiService;
import com.justyoga.util.dto.review.ReviewCommentDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.string.StringUtil;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewCommentUiServiceImpl implements ReviewCommentUiService {

    private final ReviewCommentService reviewCommentService;
    private final ReviewService reviewService;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewCommentUiServiceImpl(
            ReviewCommentService reviewCommentService,
            ReviewService reviewService,
            ModelMapper modelMapper) {
        this.reviewCommentService = reviewCommentService;
        this.reviewService = reviewService;
        this.modelMapper = modelMapper;
    }

    private ReviewCommentDTO convert(ReviewComment entity) {
        var reviewCommentDTO = modelMapper.map(entity, ReviewCommentDTO.class);
        reviewCommentDTO.setText(StringUtil.getStringFromBase64(entity.getText()));
        return reviewCommentDTO;
    }

    private ReviewComment convert(ReviewCommentDTO dto) {
        dto.setText(StringUtil.getBase64ForString(dto.getText()));
        return modelMapper.map(dto, ReviewComment.class);
    }

    @Override
    public ReviewCommentDTO findById(UUID id) {
        var reviewComment = reviewCommentService.findById(id);
        if (reviewComment == null)
            throw new AppException("Comment not found", AppStatusCode.NOT_FOUND);
        else return convert(reviewComment);
    }

    @Override
    public List<ReviewCommentDTO> findAllByReviewId(UUID id) {
        return reviewCommentService.findAllByReviewId(id).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(UUID id, UUID currentUser) {
        ReviewComment comment = reviewCommentService.findById(id);
        if (comment == null) throw new AppException("Comment not found", AppStatusCode.NOT_FOUND);
        Review review = reviewService.findById(comment.getReviewId());
        if (review == null)
            throw new AppException("Review not found", AppStatusCode.INVALID_REQUEST);
        if (!currentUser.equals(review.getReviewedBy())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return reviewCommentService.delete(id);
    }

    @Override
    public ReviewCommentDTO save(ReviewCommentDTO reviewCommentDTO, UUID currentUser) {
        if (!currentUser.equals(reviewCommentDTO.getUserId())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        Review review = reviewService.findById(reviewCommentDTO.getReviewId());
        if (review == null)
            throw new AppException("Review not found", AppStatusCode.INVALID_REQUEST);
        var comment = reviewCommentService.save(convert(reviewCommentDTO));
        return convert(comment);
    }
}
