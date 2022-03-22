package com.justyoga.review.web.resource;

import com.justyoga.review.web.service.interfaces.ReviewUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.review.ReviewDTO;
import com.justyoga.util.page.PageDTO;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
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
public class ReviewResource {

    private final ReviewUiService reviewUiService;

    @Autowired
    public ReviewResource(ReviewUiService reviewUiService) {
        this.reviewUiService = reviewUiService;
    }

    @GetMapping(value = "/reviews/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<ReviewDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, reviewUiService.findById(id)), HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/reviews/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, reviewUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/reviews")
    @ResponseBody
    public ResponseEntity<BaseResponse<PageDTO<ReviewDTO>>> get(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer count,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, reviewUiService.find(page, count, sort, userId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/reviews")
    @ResponseBody
    public ResponseEntity<BaseResponse<ReviewDTO>> save(
            @RequestBody @Valid ReviewDTO reviewDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, reviewUiService.save(reviewDTO, userId)),
                HttpStatus.OK);
    }
}
