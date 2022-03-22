package com.justyoga.search.web.resource;

import com.justyoga.search.web.service.interfaces.FeedUiService;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
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
public class FeedResource {

    private final FeedUiService feedUiService;

    @Autowired
    public FeedResource(FeedUiService feedUiService) {
        this.feedUiService = feedUiService;
    }

    @GetMapping(value = "/feed")
    @ResponseBody
    public ResponseEntity<BaseResponse<FeedUiService.Feed>> get(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer count,
            @RequestParam(required = false, defaultValue = "updatedAt") String sort,
            @RequestParam(required = false, defaultValue = "desc") String order,
            @RequestParam(required = false) UUID countryId,
            @RequestParam(required = false) UUID administrativeAreaLevel1Id,
            @RequestParam(required = false) UUID subLocalityLevel1Id,
            @RequestParam(required = false) UUID localityId,
            @RequestParam(required = false) UUID subLocalityLevel2Id,
            @RequestParam(required = false) UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        feedUiService.find(
                                page,
                                count,
                                sort,
                                order,
                                userId,
                                countryId,
                                administrativeAreaLevel1Id,
                                localityId,
                                subLocalityLevel1Id,
                                subLocalityLevel2Id)),
                HttpStatus.OK);
    }
}
