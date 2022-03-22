package com.justyoga.profile.web.resource;

import com.justyoga.profile.web.service.interfaces.YogaExpertiseUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.profile.UserYogaExpertiseDTO;
import com.justyoga.util.dto.profile.YogaExpertiseDTO;
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
public class YogaExpertiseResource {

    private final YogaExpertiseUiService yogaExpertiseUiService;

    @Autowired
    public YogaExpertiseResource(YogaExpertiseUiService yogaExpertiseUiService) {
        this.yogaExpertiseUiService = yogaExpertiseUiService;
    }

    @GetMapping(value = "/yoga-expertise")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<YogaExpertiseDTO>>> getAllYogaExpertise() {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, yogaExpertiseUiService.getAllYogaExpertise()),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/yoga-expertise")
    @ResponseBody
    public ResponseEntity<BaseResponse<YogaExpertiseDTO>> saveAllYogaExpertise(
            @RequestBody @Valid YogaExpertiseDTO yogaExpertiseDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, yogaExpertiseUiService.saveYogaExpertise(yogaExpertiseDTO)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/yoga-expertise-collection")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<YogaExpertiseDTO>>> saveAllYogaExpertise(
            @RequestBody @Valid List<YogaExpertiseDTO> yogaExpertiseDTOS,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        yogaExpertiseUiService.saveAllYogaExpertise(yogaExpertiseDTOS)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/yoga-expertise/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> deleteYogaExpertise(
            @PathVariable UUID id, @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, yogaExpertiseUiService.deleteYogaExpertise(id)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/user-yoga-expertise/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> deleteTrainerYogaExpertise(
            @PathVariable UUID id, @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, yogaExpertiseUiService.deleteUserYogaExpertise(id, userId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/user-yoga-expertise")
    @ResponseBody
    public ResponseEntity<BaseResponse<UserYogaExpertiseDTO>> saveTrainerYogaExpertise(
            @RequestBody @Valid UserYogaExpertiseDTO trainerYogaExpertiseDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        yogaExpertiseUiService.saveUserYogaExpertise(
                                trainerYogaExpertiseDTO, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/user-yoga-expertise")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<UserYogaExpertiseDTO>>>
            findTrainerYogaExpertiseByTrainerId(@RequestParam UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        yogaExpertiseUiService.findUserYogaExpertiseByTrainerId(userId)),
                HttpStatus.OK);
    }
}
