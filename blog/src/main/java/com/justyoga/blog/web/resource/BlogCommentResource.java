package com.justyoga.blog.web.resource;

import com.justyoga.blog.web.service.interfaces.BlogCommentUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.blog.BlogCommentDTO;
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
public class BlogCommentResource {

    private final BlogCommentUiService blogCommentUiService;

    @Autowired
    public BlogCommentResource(BlogCommentUiService blogCommentUiService) {
        this.blogCommentUiService = blogCommentUiService;
    }

    @GetMapping(value = "/comments/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<BlogCommentDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogCommentUiService.findById(id)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/comments/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogCommentUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/blogs/{blog-id}/comments")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<BlogCommentDTO>>> findAllByBlogId(
            @PathVariable("blog-id") UUID blogId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogCommentUiService.findAllByBlogId(blogId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/comments")
    @ResponseBody
    public ResponseEntity<BaseResponse<BlogCommentDTO>> save(
            @RequestBody BlogCommentDTO blogCommentDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, blogCommentUiService.save(blogCommentDTO, userId)),
                HttpStatus.OK);
    }
}
