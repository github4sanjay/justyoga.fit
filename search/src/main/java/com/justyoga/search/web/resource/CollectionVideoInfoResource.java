package com.justyoga.search.web.resource;

import com.justyoga.search.domain.model.CollectionVideoInfo;
import com.justyoga.search.web.service.interfaces.CollectionVideoInfoUiService;
import com.justyoga.util.page.PageDTO;
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
public class CollectionVideoInfoResource {

    private final CollectionVideoInfoUiService collectionVideoInfoUiService;

    @Autowired
    public CollectionVideoInfoResource(CollectionVideoInfoUiService collectionVideoInfoUiService) {
        this.collectionVideoInfoUiService = collectionVideoInfoUiService;
    }

    @GetMapping(value = "/collection/video-info")
    @ResponseBody
    public ResponseEntity<BaseResponse<PageDTO<CollectionVideoInfo>>> get(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer count,
            @RequestParam(required = false, defaultValue = "updatedAt") String sort,
            @RequestParam(required = false, defaultValue = "desc") String order,
            @RequestParam(required = false) UUID collectionId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        collectionVideoInfoUiService.find(page, count, sort, order, collectionId)),
                HttpStatus.OK);
    }
}
