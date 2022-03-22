package com.justyoga.review.web.resource;

import com.justyoga.review.web.service.interfaces.ReviewImageUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.review.ReviewImageDTO;
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
public class ReviewImageResource {

    private final ReviewImageUiService reviewImageUiService;

    @Autowired
    public ReviewImageResource(ReviewImageUiService reviewImageUiService) {
        this.reviewImageUiService = reviewImageUiService;
    }

    @GetMapping(value = "/images/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<ReviewImageDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, reviewImageUiService.findById(id)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/images/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, reviewImageUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/reviews/{review-id}/images")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<ReviewImageDTO>>> findAllByReviewId(
            @PathVariable("review-id") UUID reviewId) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, reviewImageUiService.findAllByReviewId(reviewId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/reviews/{review-id}/images")
    @ResponseBody
    public ResponseEntity<BaseResponse<ReviewImageDTO>> save(
            @RequestBody @Valid Url image,
            @PathVariable("review-id") UUID reviewId,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        reviewImageUiService.save(image.getUrl(), reviewId, userId)),
                HttpStatus.OK);
    }
}
