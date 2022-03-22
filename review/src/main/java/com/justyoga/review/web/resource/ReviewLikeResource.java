package com.justyoga.review.web.resource;

import com.justyoga.review.web.service.interfaces.ReviewLikeUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.review.ReviewLikeDTO;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import java.util.List;
import java.util.UUID;
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
public class ReviewLikeResource {

    private final ReviewLikeUiService reviewLikeUiService;

    @Autowired
    public ReviewLikeResource(ReviewLikeUiService reviewLikeUiService) {
        this.reviewLikeUiService = reviewLikeUiService;
    }

    @GetMapping(value = "/likes/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<ReviewLikeDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, reviewLikeUiService.findById(id)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/likes/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, reviewLikeUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/reviews/{review-id}/likes")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<ReviewLikeDTO>>> findAllByReviewId(
            @PathVariable("review-id") UUID reviewId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, reviewLikeUiService.findAllByReviewId(reviewId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/likes")
    @ResponseBody
    public ResponseEntity<BaseResponse<ReviewLikeDTO>> save(
            @RequestBody ReviewLikeDTO reviewCommentDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, reviewLikeUiService.save(reviewCommentDTO, userId)),
                HttpStatus.OK);
    }
}
