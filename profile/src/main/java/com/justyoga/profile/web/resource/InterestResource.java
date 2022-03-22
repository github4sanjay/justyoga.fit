package com.justyoga.profile.web.resource;

import com.justyoga.profile.web.service.interfaces.InterestUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.profile.InterestDTO;
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
public class InterestResource {

    private final InterestUiService uiService;

    @Autowired
    public InterestResource(InterestUiService uiService) {
        this.uiService = uiService;
    }

    @GetMapping(value = "/interests/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<InterestDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, uiService.findById(id)), HttpStatus.OK);
    }

    @GetMapping(value = "/interests")
    @ResponseBody
    public ResponseEntity<BaseResponse<InterestDTO>> getByUserId(
            @RequestParam(value = "userId") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, uiService.findByUserId(userId)), HttpStatus.OK);
    }

    @GetMapping(value = "/interest-collection")
    @ResponseBody
    public ResponseEntity<BaseResponse<PageDTO<InterestDTO>>> getMulti(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer count,
            @RequestParam(required = false) String sort) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, uiService.find(page, count, sort)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/interests")
    @ResponseBody
    public ResponseEntity<BaseResponse<InterestDTO>> saveOrUpdate(
            @RequestBody @Valid InterestDTO trainerDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, uiService.saveOrUpdate(trainerDTO, userId)),
                HttpStatus.OK);
    }
}
