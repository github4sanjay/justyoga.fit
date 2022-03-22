package com.justyoga.client.blog;

import com.justyoga.util.dto.blog.BlogDTO;
import com.justyoga.util.response.BaseResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "blog")
public interface BlogClient {
    @GetMapping(value = "/api/v1/blogs/{id}")
    ResponseEntity<BaseResponse<BlogDTO>> get(@PathVariable("id") UUID id);
}
