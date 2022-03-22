package com.justyoga.review.web.service.interfaces;

import com.justyoga.util.dto.review.ReviewVideoDTO;
import java.util.List;
import java.util.UUID;

public interface ReviewVideoUiService {
    ReviewVideoDTO findById(UUID id);

    List<ReviewVideoDTO> findAllByReviewId(UUID id);

    Boolean delete(UUID id, UUID currentUser);

    ReviewVideoDTO save(String video, UUID reviewId, UUID currentUser);
}
