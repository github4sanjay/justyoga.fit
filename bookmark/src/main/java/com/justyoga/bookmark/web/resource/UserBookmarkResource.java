package com.justyoga.bookmark.web.resource;

import com.justyoga.bookmark.web.service.interfaces.UserBookmarkUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.bookmark.UserBookmarkDTO;
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
public class UserBookmarkResource {

    private final UserBookmarkUiService userBookmarkUiService;

    @Autowired
    public UserBookmarkResource(UserBookmarkUiService userBookmarkUiService) {
        this.userBookmarkUiService = userBookmarkUiService;
    }

    @GetMapping(value = "/bookmarks/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<UserBookmarkDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, userBookmarkUiService.findById(id)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/bookmarks/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, userBookmarkUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/bookmarks")
    @ResponseBody
    public ResponseEntity<BaseResponse<PageDTO<UserBookmarkDTO>>> get(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer count,
            @RequestParam(required = false, defaultValue = "updatedAt") String sort,
            @RequestParam(required = false, defaultValue = "desc") String order,
            @RequestParam(required = false) UUID userId,
            @RequestParam(required = false) UUID bookmarkedBy) {
        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        userBookmarkUiService.find(page, count, sort, order, userId, bookmarkedBy)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/bookmarks")
    @ResponseBody
    public ResponseEntity<BaseResponse<UserBookmarkDTO>> save(
            @RequestBody UserBookmarkDTO userBookmarkDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, userBookmarkUiService.save(userBookmarkDTO, userId)),
                HttpStatus.OK);
    }
}
