package com.justyoga.profile.web.resource;

import com.justyoga.profile.web.service.interfaces.YogaCertificateUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.profile.UserYogaCertificateDTO;
import com.justyoga.util.dto.profile.YogaCertificateDTO;
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
public class YogaCertificateResource {

    private final YogaCertificateUiService yogaCertificateUiService;

    @Autowired
    public YogaCertificateResource(YogaCertificateUiService yogaCertificateUiService) {
        this.yogaCertificateUiService = yogaCertificateUiService;
    }

    @GetMapping(value = "/yoga-certificate")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<YogaCertificateDTO>>> getAllYogaCertificate() {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, yogaCertificateUiService.getAllYogaCertificate()),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/yoga-certificate")
    @ResponseBody
    public ResponseEntity<BaseResponse<YogaCertificateDTO>> saveAllYogaCertificate(
            @RequestBody @Valid YogaCertificateDTO yogaCertificateDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        yogaCertificateUiService.saveYogaCertificate(yogaCertificateDTO)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/yoga-certificate-collection")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<YogaCertificateDTO>>> saveAllYogaCertificate(
            @RequestBody @Valid List<YogaCertificateDTO> yogaCertificateDTOS,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        yogaCertificateUiService.saveAllYogaCertificate(yogaCertificateDTOS)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/yoga-certificate/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> deleteYogaCertificate(
            @PathVariable UUID id, @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, yogaCertificateUiService.deleteYogaCertificate(id)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/user-yoga-certificate/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> deleteTrainerYogaCertificate(
            @PathVariable UUID id, @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        yogaCertificateUiService.deleteUserYogaCertificate(id, userId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/user-yoga-certificate")
    @ResponseBody
    public ResponseEntity<BaseResponse<UserYogaCertificateDTO>> saveTrainerYogaCertificate(
            @RequestBody @Valid UserYogaCertificateDTO trainerYogaCertificateDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        yogaCertificateUiService.saveUserYogaCertificate(
                                trainerYogaCertificateDTO, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/user-yoga-certificate")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<UserYogaCertificateDTO>>>
            findTrainerYogaCertificateByTrainerId(@RequestParam UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        yogaCertificateUiService.findUserYogaCertificateByTrainerId(userId)),
                HttpStatus.OK);
    }
}
