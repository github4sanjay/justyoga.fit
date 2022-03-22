package com.justyoga.profile.web.resource;

import com.justyoga.profile.web.service.interfaces.VideoUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.profile.VideoDTO;
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
public class VideoResource {

    private final VideoUiService videoUiService;

    @Autowired
    public VideoResource(VideoUiService videoUiService) {
        this.videoUiService = videoUiService;
    }

    @MaintainUserContext
    @PostMapping(value = "/videos")
    @ResponseBody
    public ResponseEntity<BaseResponse<VideoDTO>> uploadIntroMediaVideo(
            @RequestBody @Valid VideoDTO videoDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, videoUiService.save(userId, videoDTO)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/videos/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> deleteIntroMedia(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, videoUiService.deleteMedia(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/videos/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<VideoDTO>> get(@PathVariable("id") UUID id) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, videoUiService.findById(id)), HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/videos")
    @ResponseBody
    public ResponseEntity<BaseResponse<VideoDTO>> update(
            @RequestBody @Valid VideoDTO videoDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, videoUiService.update(videoDTO, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/videos")
    @ResponseBody
    public ResponseEntity<BaseResponse<PageDTO<VideoDTO>>> find(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer count,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, videoUiService.find(page, count, sort, userId)),
                HttpStatus.OK);
    }
}
