package com.justyoga.review.web.service.impl;

import com.justyoga.client.library.MediaClient;
import com.justyoga.review.cache.BucketVsUrl;
import com.justyoga.review.domain.model.mysql.Review;
import com.justyoga.review.domain.model.mysql.ReviewVideo;
import com.justyoga.review.service.interfaces.ReviewService;
import com.justyoga.review.service.interfaces.ReviewVideoService;
import com.justyoga.review.web.service.interfaces.ReviewVideoUiService;
import com.justyoga.util.dto.review.ReviewVideoDTO;
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
public class ReviewVideoUiServiceImpl implements ReviewVideoUiService {

    private final ReviewVideoService reviewVideoService;
    private final ReviewService reviewService;
    private final MediaClient mediaClient;
    private final BucketVsUrl bucketVsUrl;
    private final ModelMapper modelMapper;

    @Autowired
    public ReviewVideoUiServiceImpl(
            ReviewVideoService reviewVideoService,
            ReviewService reviewService,
            MediaClient mediaClient,
            BucketVsUrl bucketVsUrl,
            ModelMapper modelMapper) {
        this.reviewVideoService = reviewVideoService;
        this.reviewService = reviewService;
        this.mediaClient = mediaClient;
        this.bucketVsUrl = bucketVsUrl;
        this.modelMapper = modelMapper;
    }

    private ReviewVideoDTO convert(ReviewVideo entity) {
        var blogVideoDTO = modelMapper.map(entity, ReviewVideoDTO.class);
        blogVideoDTO.setUrl(getUrl(entity.getUrl()));
        return blogVideoDTO;
    }

    private ReviewVideo convert(ReviewVideoDTO dto) {
        return modelMapper.map(dto, ReviewVideo.class);
    }

    @Override
    public ReviewVideoDTO findById(UUID id) {
        var reviewVideo = reviewVideoService.findById(id);
        if (reviewVideo == null) throw new AppException("Video not found", AppStatusCode.NOT_FOUND);
        else return convert(reviewVideo);
    }

    @Override
    public List<ReviewVideoDTO> findAllByReviewId(UUID id) {
        return reviewVideoService.findAllByReviewId(id).stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    @Override
    public Boolean delete(UUID id, UUID currentUser) {
        ReviewVideo reviewVideo = reviewVideoService.findById(id);
        if (reviewVideo == null) throw new AppException("Video not found", AppStatusCode.NOT_FOUND);
        Review review = reviewService.findById(reviewVideo.getReviewId());
        if (review == null)
            throw new AppException("Review not found", AppStatusCode.INVALID_REQUEST);
        if (!currentUser.equals(review.getReviewedBy())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }
        return reviewVideoService.delete(id);
    }

    @Override
    public ReviewVideoDTO save(String video, UUID reviewId, UUID currentUser) {
        Review review = reviewService.findById(reviewId);
        if (review == null) throw new AppException("Review not found", AppStatusCode.NOT_FOUND);
        if (!currentUser.equals(review.getReviewedBy())) {
            throw new AppException(
                    "Not allowed for this operation", AppStatusCode.UNAUTHORIZED_ERROR);
        }

        var save = reviewVideoService.save(convert(new ReviewVideoDTO(video, reviewId)));
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
