package com.justyoga.search.web.resource;

import com.justyoga.search.domain.model.BlogInfo;
import com.justyoga.search.web.service.interfaces.BlogInfoUiService;
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
public class BlogInfoResource {

    private final BlogInfoUiService blogInfoUiService;

    @Autowired
    public BlogInfoResource(BlogInfoUiService blogInfoUiService) {
        this.blogInfoUiService = blogInfoUiService;
    }

    @GetMapping(value = "/blog-info")
    @ResponseBody
    public ResponseEntity<BaseResponse<PageDTO<BlogInfo>>> get(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer count,
            @RequestParam(required = false, defaultValue = "updatedAt") String sort,
            @RequestParam(required = false, defaultValue = "desc") String order,
            @RequestParam(required = false) UUID countryId,
            @RequestParam(required = false) UUID administrativeAreaLevel1Id,
            @RequestParam(required = false) UUID subLocalityLevel1Id,
            @RequestParam(required = false) UUID localityId,
            @RequestParam(required = false) UUID subLocalityLevel2Id,
            @RequestParam(required = false) Boolean trainer,
            @RequestParam(required = false) UUID userId) {

        return new ResponseEntity<>(
                new BaseResponse<>(
                        Status.SUCCESS,
                        blogInfoUiService.find(
                                page,
                                count,
                                sort,
                                order,
                                trainer,
                                countryId,
                                administrativeAreaLevel1Id,
                                localityId,
                                subLocalityLevel1Id,
                                subLocalityLevel2Id,
                                userId)),
                HttpStatus.OK);
    }
}
