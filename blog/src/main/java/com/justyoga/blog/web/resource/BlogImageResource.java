package com.justyoga.blog.web.resource;

import com.justyoga.blog.web.service.interfaces.BlogImageUiService;
import com.justyoga.util.annotation.MaintainUserContext;
import com.justyoga.util.dto.blog.BlogImageDTO;
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
public class BlogImageResource {

    private final BlogImageUiService blogImageUiService;

    @Autowired
    public BlogImageResource(BlogImageUiService blogImageUiService) {
        this.blogImageUiService = blogImageUiService;
    }

    @GetMapping(value = "/images/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<BlogImageDTO>> get(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogImageUiService.findById(id)), HttpStatus.OK);
    }

    @MaintainUserContext
    @DeleteMapping(value = "/images/{id}")
    @ResponseBody
    public ResponseEntity<BaseResponse<Boolean>> delete(
            @PathVariable("id") UUID id,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogImageUiService.delete(id, userId)),
                HttpStatus.OK);
    }

    @GetMapping(value = "/blogs/{blog-id}/images")
    @ResponseBody
    public ResponseEntity<BaseResponse<List<BlogImageDTO>>> findAllByBlogId(
            @PathVariable("blog-id") UUID blogId) {
        return new ResponseEntity<>(
                new BaseResponse<>(Status.SUCCESS, blogImageUiService.findAllByBlogId(blogId)),
                HttpStatus.OK);
    }

    @MaintainUserContext
    @PostMapping(value = "/blogs/{blog-id}/images")
    @ResponseBody
    public ResponseEntity<BaseResponse<BlogImageDTO>> save(
            @RequestBody Url image,
            @PathVariable("blog-id") UUID blogId,
            @RequestHeader(value = "X-Authorization-UUID") UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS, blogImageUiService.save(image.getUrl(), blogId, userId)),
                HttpStatus.OK);
    }
}
