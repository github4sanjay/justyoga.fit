package com.justyoga.collection.web.resource;

import com.justyoga.collection.web.service.interfaces.CollectionImageUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.collection.CollectionImageDTO;
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
public class CollectionImageResource {

    private final CollectionImageUiService collectionImageUiService;

    @Autowired
    public CollectionImageResource(CollectionImageUiService collectionImageUiService) {
        this.collectionImageUiService = collectionImageUiService;
    }

    @GetMapping(value = "/collection-images/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<CollectionImageDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, collectionImageUiService.findById(id)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/collection-images/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, collectionImageUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/collections/{collection-id}/collection-images")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<CollectionImageDTO>>> findAllByReviewId(
            @PathVariable("collection-id") UUID collectionId) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        collectionImageUiService.findAllByCollectionId(collectionId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/collection-images")
    @ResponseBody
    public ResponseEntity<BaseResponse<CollectionImageDTO>> save(
            @RequestBody @Valid CollectionImageDTO collectionImageDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, collectionImageUiService.save(collectionImageDTO, userId)),
                HttpStatus.OK);
    }
}
