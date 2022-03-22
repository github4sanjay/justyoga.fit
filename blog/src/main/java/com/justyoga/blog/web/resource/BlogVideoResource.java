package com.justyoga.blog.web.resource;

import com.justyoga.blog.web.service.interfaces.BlogVideoUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.blog.BlogVideoDTO;
import com.justyoga.util.response.BaseResponse;
import com.justyoga.util.response.Status;
import java.util.List;
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
public class BlogVideoResource {

    private final BlogVideoUiService blogVideoUiService;

    @Autowired
    public BlogVideoResource(BlogVideoUiService blogVideoUiService) {
        this.blogVideoUiService = blogVideoUiService;
    }

    @GetMapping(value = "/videos/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<BlogVideoDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogVideoUiService.findById(id)), HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/videos/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogVideoUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/blogs/{blog-id}/videos")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<BlogVideoDTO>>> findAllByBlogId(
            @PathVariable("blog-id") UUID blogId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogVideoUiService.findAllByBlogId(blogId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/blogs/{blog-id}/videos")
    @ResponseBody
    public ResponseEntity<BaseResponse<BlogVideoDTO>> save(
            @RequestBody Url video,
            @PathVariable("blog-id") UUID blogId,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, blogVideoUiService.save(video.getUrl(), blogId, userId)),
                HttpStatus.OK);
    }
}
