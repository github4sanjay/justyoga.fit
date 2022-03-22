package com.justyoga.review.web.service.interfaces;

import com.justyoga.util.dto.review.ReviewCommentDTO;
import java.util.List;
import java.util.UUID;

public interface ReviewCommentUiService {
    ReviewCommentDTO save(ReviewCommentDTO reviewCommentDTO, UUID userId);

    ReviewCommentDTO findById(UUID id);

    List<ReviewCommentDTO> findAllByReviewId(UUID id);

    Boolean delete(UUID id, UUID currentUser);
}
