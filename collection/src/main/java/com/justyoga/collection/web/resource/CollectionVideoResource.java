package com.justyoga.collection.web.resource;

import com.justyoga.collection.web.service.interfaces.CollectionVideoUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.collection.CollectionVideoDTO;
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
public class CollectionVideoResource {

    private final CollectionVideoUiService collectionVideoUiService;

    @Autowired
    public CollectionVideoResource(CollectionVideoUiService collectionVideoUiService) {
        this.collectionVideoUiService = collectionVideoUiService;
    }

    @GetMapping(value = "/collection-videos/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<CollectionVideoDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, collectionVideoUiService.findById(id)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/collection-videos/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, collectionVideoUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/collections/{collection-id}/collection-videos")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<CollectionVideoDTO>>> findAllByReviewId(
            @PathVariable("collection-id") UUID collectionId) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        collectionVideoUiService.findAllByCollectionId(collectionId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/collection-videos")
    @ResponseBody
    public ResponseEntity<BaseResponse<CollectionVideoDTO>> save(
            @RequestBody @Valid CollectionVideoDTO collectionVideoDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, collectionVideoUiService.save(collectionVideoDTO, userId)),
                HttpStatus.OK);
    }
}
