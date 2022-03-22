package com.justyoga.review.web.resource;

import com.justyoga.review.web.service.interfaces.ReviewVideoUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.review.ReviewVideoDTO;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@Validated
public class ReviewVideoResource {

    private final ReviewVideoUiService reviewVideoUiService;

    @Autowired
    public ReviewVideoResource(ReviewVideoUiService reviewVideoUiService) {
        this.reviewVideoUiService = reviewVideoUiService;
    }

    @GetMapping(value = "/videos/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<ReviewVideoDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, reviewVideoUiService.findById(id)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/videos/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, reviewVideoUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/reviews/{review-id}/videos")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<ReviewVideoDTO>>> findAllByReviewId(
            @PathVariable("review-id") UUID reviewId) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, reviewVideoUiService.findAllByReviewId(reviewId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/reviews/{review-id}/videos")
    @ResponseBody
    public ResponseEntity<BaseResponse<ReviewVideoDTO>> save(
            @RequestBody @Valid Url video,
            @PathVariable("review-id") UUID reviewId,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        reviewVideoUiService.save(video.getUrl(), reviewId, userId)),
                HttpStatus.OK);
    }
}
