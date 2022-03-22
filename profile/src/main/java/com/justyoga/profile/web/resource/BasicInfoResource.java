package com.justyoga.profile.web.resource;

import com.justyoga.profile.web.service.interfaces.UserBasicInfoUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.profile.UserBasicInfoDTO;
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
public class BasicInfoResource {

    private final UserBasicInfoUiService userBasicInfoUiService;

    @Autowired
    public BasicInfoResource(UserBasicInfoUiService userBasicInfoUiService) {
        this.userBasicInfoUiService = userBasicInfoUiService;
    }

    @GetMapping(value = "/basic-info/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<UserBasicInfoDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, userBasicInfoUiService.findUserBasicInfoById(id)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/basic-info")
    @ResponseBody
    public ResponseEntity<BaseResponse<UserBasicInfoDTO>> getBasicInfo(
            @RequestParam(value = "userId") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, userBasicInfoUiService.findByUserId(userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/basic-info-collection")
    @ResponseBody
    public ResponseEntity<BaseResponse<PageDTO<UserBasicInfoDTO>>> getBasicInfos(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer count,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) UUID countryId,
            @RequestParam(required = false) UUID administrativeAreaLevel1Id,
            @RequestParam(required = false) UUID subLocalityLevel1Id,
            @RequestParam(required = false) UUID localityId,
            @RequestParam(required = false) UUID subLocalityLevel2Id) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        userBasicInfoUiService.find(
                                page,
                                count,
                                sort,
                                countryId,
                                administrativeAreaLevel1Id,
                                localityId,
                                subLocalityLevel1Id,
                                subLocalityLevel2Id)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/basic-info")
    @ResponseBody
    public ResponseEntity<BaseResponse<UserBasicInfoDTO>> saveOrUpdate(
            @RequestBody @Valid UserBasicInfoDTO trainerDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, userBasicInfoUiService.saveOrUpdate(trainerDTO, userId)),
                HttpStatus.OK);
    }
}
