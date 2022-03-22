package com.justyoga.profile.web.resource;

import com.justyoga.profile.web.service.interfaces.ImageUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.profile.ImageDTO;
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
public class ImageResource {

    private final ImageUiService imageUiService;

    @Autowired
    public ImageResource(ImageUiService imageUiService) {
        this.imageUiService = imageUiService;
    }

    @MaintainUserContext
    @PostMapping(value = "/images")
    @ResponseBody
    public ResponseEntity<BaseResponse<ImageDTO>> uploadIntroMediaImage(
            @RequestBody @Valid ImageDTO imageDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userID) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, imageUiService.save(userID, imageDTO)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/images/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> deleteIntroMedia(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, imageUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/images")
    @ResponseBody
    public ResponseEntity<BaseResponse<PageDTO<ImageDTO>>> find(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer count,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, imageUiService.find(page, count, sort, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/images/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<ImageDTO>> get(@PathVariable("id") UUID id) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, imageUiService.findById(id)), HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/images")
    @ResponseBody
    public ResponseEntity<BaseResponse<ImageDTO>> update(
            @RequestBody @Valid ImageDTO imageDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, imageUiService.update(imageDTO, userId)),
                HttpStatus.OK);
    }
}
