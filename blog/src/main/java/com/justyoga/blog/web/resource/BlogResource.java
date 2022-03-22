package com.justyoga.blog.web.resource;

import com.justyoga.blog.web.service.interfaces.BlogUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.blog.BlogDTO;
import com.justyoga.util.page.PageDTO;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@Validated
public class BlogResource {

    private final BlogUiService blogUiService;

    @Autowired
    public BlogResource(BlogUiService blogUiService) {
        this.blogUiService = blogUiService;
    }

    @GetMapping(value = "/blogs/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<BlogDTO>> getByBlogId(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogUiService.findById(id)), HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/blogs/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/blogs")
    @ResponseBody
    public ResponseEntity<BaseResponse<PageDTO<BlogDTO>>> get(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer count,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogUiService.find(page, count, sort, userId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/blogs")
    @ResponseBody
    public ResponseEntity<BaseResponse<BlogDTO>> save(
            @RequestBody BlogDTO blogDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogUiService.save(blogDTO, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/blogs/user/{user_id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<BlogDTO>>> getByUserId(
            @PathVariable("user_id") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogUiService.findByUserId(userId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/blogs/{id}/cover")
    @ResponseBody
    public ResponseEntity<BaseResponse<BlogDTO>> save(
            @PathVariable("id") UUID id,
            @RequestBody Url url,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, blogUiService.saveCover(id, url.getUrl(), userId)),
                HttpStatus.OK);
    }
}
