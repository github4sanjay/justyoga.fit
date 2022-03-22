package com.justyoga.blog.web.resource;

import com.justyoga.blog.web.service.interfaces.BlogLikeUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.blog.BlogLikeDTO;
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
public class BlogLikeResource {

    private final BlogLikeUiService blogLikeUiService;

    @Autowired
    public BlogLikeResource(BlogLikeUiService blogLikeUiService) {
        this.blogLikeUiService = blogLikeUiService;
    }

    @GetMapping(value = "/likes/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<BlogLikeDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogLikeUiService.findById(id)), HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/likes/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogLikeUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/blogs/{blog-id}/likes")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<BlogLikeDTO>>> findAllByBlogId(
            @PathVariable("blog-id") UUID blogId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogLikeUiService.findAllByBlogId(blogId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PutMapping(value = "/likes")
    @ResponseBody
    public ResponseEntity<BaseResponse<BlogLikeDTO>> save(
            @RequestBody BlogLikeDTO blogCommentDTO,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogLikeUiService.save(blogCommentDTO, userId)),
                HttpStatus.OK);
    }
}
