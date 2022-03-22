package com.justyoga.collection.web.resource;

import com.justyoga.collection.web.service.interfaces.CollectionUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.collection.CollectionDTO;
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
public class CollectionResource {

    private final CollectionUiService collectionUiService;

    @Autowired
    public CollectionResource(CollectionUiService collectionUiService) {
        this.collectionUiService = collectionUiService;
    }

    @GetMapping(value = "/collections/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<CollectionDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, collectionUiService.findById(id)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/collections/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, collectionUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/collections")
    @ResponseBody
    public ResponseEntity<BaseResponse<PageDTO<CollectionDTO>>> get(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer count,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String order) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, collectionUiService.find(page, count, sort, order)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/collections")
    @ResponseBody
    public ResponseEntity<BaseResponse<CollectionDTO>> save(
            @RequestBody @Valid CollectionDTO collectionDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, collectionUiService.save(collectionDTO, userId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/collections/{id}/cover")
    @ResponseBody
    public ResponseEntity<BaseResponse<CollectionDTO>> save(
            @PathVariable("id") UUID id,
            @RequestBody @Valid Url image,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, collectionUiService.saveCover(id, image.getUrl(), userId)),
                HttpStatus.OK);
    }
}
