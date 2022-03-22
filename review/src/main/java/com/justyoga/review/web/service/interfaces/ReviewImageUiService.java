package com.justyoga.review.web.service.interfaces;

import com.justyoga.util.dto.review.ReviewImageDTO;
import java.util.List;
import java.util.UUID;

public interface ReviewImageUiService {
    ReviewImageDTO findById(UUID id);

    List<ReviewImageDTO> findAllByReviewId(UUID id);

    Boolean delete(UUID id, UUID currentUser);

    ReviewImageDTO save(String image, UUID reviewId, UUID userId);
}
