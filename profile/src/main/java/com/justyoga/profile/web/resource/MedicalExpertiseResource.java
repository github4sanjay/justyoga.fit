package com.justyoga.profile.web.resource;

import com.justyoga.profile.web.service.interfaces.MedicalExpertiseUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.profile.MedicalExpertiseDTO;
import com.justyoga.util.dto.profile.UserMedicalExpertiseDTO;
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
public class MedicalExpertiseResource {

    private final MedicalExpertiseUiService medicalExpertiseUiService;

    @Autowired
    public MedicalExpertiseResource(MedicalExpertiseUiService medicalExpertiseUiService) {
        this.medicalExpertiseUiService = medicalExpertiseUiService;
    }

    @GetMapping(value = "/medical-expertise")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<MedicalExpertiseDTO>>> getAllMedicalExpertise() {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, medicalExpertiseUiService.getAllMedicalExpertise()),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/medical-expertise")
    @ResponseBody
    public ResponseEntity<BaseResponse<MedicalExpertiseDTO>> saveMedicalExpertise(
            @RequestBody @Valid MedicalExpertiseDTO medicalExpertiseDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userID) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        medicalExpertiseUiService.saveMedicalExpertise(medicalExpertiseDTO)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/medical-expertise-collection")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<MedicalExpertiseDTO>>> saveAllMedicalExpertise(
            @Valid @RequestBody List<MedicalExpertiseDTO> medicalExpertiseDTOList,
            @RequestHeader(value = "X-Authorization-UUID") UUID userID) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        medicalExpertiseUiService.saveAllMedicalExpertise(medicalExpertiseDTOList)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/medical-expertise/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> deleteMedicalExpertise(
            @PathVariable UUID id, @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, medicalExpertiseUiService.deleteMedicalExpertise(id)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/user-medical-expertise/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> deleteTrainerMedicalExpertise(
            @PathVariable UUID id, @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        medicalExpertiseUiService.deleteUserMedicalExpertise(id, userId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/user-medical-expertise")
    @ResponseBody
    public ResponseEntity<BaseResponse<UserMedicalExpertiseDTO>> saveTrainerMedicalExpertise(
            @RequestBody @Valid UserMedicalExpertiseDTO trainerMedicalExpertiseDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        medicalExpertiseUiService.saveUserMedicalExpertise(
                                trainerMedicalExpertiseDTO, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/user-medical-expertise")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<UserMedicalExpertiseDTO>>>
            findTrainerMedicalExpertiseByTrainerId(@RequestParam UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        medicalExpertiseUiService.findUserMedicalExpertiseByTrainerId(userId)),
                HttpStatus.OK);
    }
}
