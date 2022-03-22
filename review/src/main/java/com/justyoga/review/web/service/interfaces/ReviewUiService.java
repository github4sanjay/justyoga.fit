package com.justyoga.review.web.service.interfaces;

import com.justyoga.util.dto.review.ReviewDTO;
import com.justyoga.util.page.PageDTO;
import java.util.List;
import java.util.UUID;

public interface ReviewUiService {

    ReviewDTO save(ReviewDTO reviewDTO, UUID currentUser);

    ReviewDTO findById(UUID id);

    List<ReviewDTO> find(UUID id);

    PageDTO<ReviewDTO> find(Integer page, Integer count, String sort, UUID userId);

    Boolean delete(UUID id, UUID currentUser);
}
