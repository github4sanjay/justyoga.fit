package com.justyoga.collection.web.resource;

import com.justyoga.collection.web.service.interfaces.CollectionBlogUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.collection.CollectionBlogDTO;
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
public class CollectionBlogResource {

    private final CollectionBlogUiService collectionBlogUiService;

    @Autowired
    public CollectionBlogResource(CollectionBlogUiService collectionBlogUiService) {
        this.collectionBlogUiService = collectionBlogUiService;
    }

    @GetMapping(value = "/collection-blogs/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<CollectionBlogDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, collectionBlogUiService.findById(id)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/collection-blogs/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, collectionBlogUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/collections/{collection-id}/collection-blogs")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<CollectionBlogDTO>>> findAllByReviewId(
            @PathVariable("collection-id") UUID collectionId) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        collectionBlogUiService.findAllByCollectionId(collectionId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/collection-blogs")
    @ResponseBody
    public ResponseEntity<BaseResponse<CollectionBlogDTO>> save(
            @RequestBody @Valid CollectionBlogDTO collectionBlogDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, collectionBlogUiService.save(collectionBlogDTO, userId)),
                HttpStatus.OK);
    }
}
