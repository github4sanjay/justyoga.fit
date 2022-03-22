package com.justyoga.review.web.service.impl;

import com.justyoga.client.library.MediaClient;
import com.justyoga.review.cache.BucketVsUrl;
import com.justyoga.review.domain.model.mysql.Review;
import com.justyoga.review.domain.model.mysql.ReviewImage;
import com.justyoga.review.service.interfaces.ReviewImageService;
import com.justyoga.review.service.interfaces.ReviewService;
import com.justyoga.review.web.service.interfaces.ReviewImageUiService;
import com.justyoga.util.dto.review.ReviewImageDTO;
import com.justyoga.util.exception.AppException;
import com.justyoga.util.exception.AppStatusCode;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReviewImageUiServiceImpl implements ReviewImageUiService {

    private final ReviewImageService reviewImageService;
    private final ReviewService reviewService;
    private final MediaClient mediaClient;
    private final BucketVsUrl bucketVsUrl;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewImageUiServiceImpl(
            ReviewImageService reviewImageService,
            ReviewService reviewService,
            MediaClient mediaClient,
            BucketVsUrl bucketVsUrl,
            ModelMapper modelMapper) {
        this.reviewImageService = reviewImageService;
        this.reviewService = reviewService;
        this.mediaClient = mediaClient;
        this.bucketVsUrl = bucketVsUrl;
        this.modelMapper = modelMapper;
    }

    private ReviewImageDTO convert(ReviewImage entity) {
        var blogImageDTO = modelMapper.map(entity, ReviewImageDTO.class);
        blogImageDTO.setUrl(getUrl(entity.getUrl()));
        return blogImageDTO;
    }

    private ReviewImage convert(ReviewImageDTO dto) {
        return modelMapper.map(dto, ReviewImage.class);
    }

    @Override
    public ReviewImageDTO findById(UUID id) {
        var reviewImage = reviewImageService.findById(id);
        if (reviewImage == null) throw new AppException("Image not found", AppStatusCode.NOT_FOUND);
        return convert(reviewImage);
    }

    @Override
    public List<ReviewImageDTO> findAllByReviewId(UUID id) {
        return reviewImageService.findAllByReviewId(id).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(UUID id, UUID currentUser) {
        ReviewImage reviewImage = reviewImageService.findById(id);
        if (reviewImage == null) throw new AppException("Image not found", AppStatusCode.NOT_FOUND);
        Review review = reviewService.findById(reviewImage.getReviewId());
        if (review == null)
            throw new AppException("Review not found", AppStatusCode.INVALID_REQUEST);
        if (!currentUser.equals(review.getReviewedBy())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return reviewImageService.delete(id);
    }

    @Override
    public ReviewImageDTO save(String image, UUID reviewId, UUID currentUser) {
        Review review = reviewService.findById(reviewId);
        if (review == null) throw new AppException("Review not found", AppStatusCode.NOT_FOUND);
        if (!currentUser.equals(review.getReviewedBy())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }

        ReviewImage save = reviewImageService.save(convert(new ReviewImageDTO(image, reviewId)));
        return convert(save);
    }

    private String getUrl(String bucketName) {
        if (bucketName == null) return null;
        String url = bucketVsUrl.get(bucketName);
        if (url == null) {
            ResponseEntity<BaseResponse<String>> coverUrlResponse =
                    mediaClient.getUrl(bucketName, 1L, TimeUnit.DAYS);
            if (coverUrlResponse.getStatusCode() == HttpStatus.OK
                    && coverUrlResponse.getBody() != null
                    && coverUrlResponse.getBody().getStatus() == Status.SUCCESS) {
                url = coverUrlResponse.getBody().getData();
                bucketVsUrl.put(bucketName, url);
            }
        }
        return url;
    }
}
