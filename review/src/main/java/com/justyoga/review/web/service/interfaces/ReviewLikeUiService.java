package com.justyoga.review.web.service.interfaces;

import com.justyoga.util.dto.review.ReviewLikeDTO;
import java.util.List;
import java.util.UUID;

public interface ReviewLikeUiService {
    ReviewLikeDTO save(ReviewLikeDTO reviewCommentDTO, UUID userId);

    ReviewLikeDTO findById(UUID id);

    List<ReviewLikeDTO> findAllByReviewId(UUID id);

    Boolean delete(UUID id, UUID currentUser);
}
