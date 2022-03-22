package com.justyoga.review.web.resource;

import com.justyoga.review.web.service.interfaces.ReviewCommentUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.review.ReviewCommentDTO;
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
public class ReviewCommentResource {

    private final ReviewCommentUiService reviewCommentUiService;

    @Autowired
    public ReviewCommentResource(ReviewCommentUiService reviewCommentUiService) {
        this.reviewCommentUiService = reviewCommentUiService;
    }

    @GetMapping(value = "/comments/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<ReviewCommentDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, reviewCommentUiService.findById(id)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/comments/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, reviewCommentUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/reviews/{review-id}/comments")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<ReviewCommentDTO>>> findAllByReviewId(
            @PathVariable("review-id") UUID reviewId) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, reviewCommentUiService.findAllByReviewId(reviewId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/comments")
    @ResponseBody
    public ResponseEntity<BaseResponse<ReviewCommentDTO>> save(
            @RequestBody @Valid ReviewCommentDTO reviewCommentDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, reviewCommentUiService.save(reviewCommentDTO, userId)),
                HttpStatus.OK);
    }
}
