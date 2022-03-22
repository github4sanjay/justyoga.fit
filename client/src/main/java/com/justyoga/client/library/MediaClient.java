package com.justyoga.client.library;

import com.justyoga.client.config.FeignSupportConfig;
import com.justyoga.util.response.BaseResponse;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "library", configuration = FeignSupportConfig.class)
public interface MediaClient {

    @PostMapping(value = "/api/v1/media-collection")
    ResponseEntity<BaseResponse<List<String>>> mediaCollection(
            @RequestPart MultipartFile[] images, @RequestPart String request);

    @PostMapping(value = "/api/v1/media", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<BaseResponse<String>> media(
            @RequestPart MultipartFile image, @RequestPart String request);

    @GetMapping("/api/v1/media")
    ResponseEntity<BaseResponse<String>> getUrl(
            @RequestParam String bucket,
            @RequestParam Long expiration,
            @RequestParam TimeUnit timeUnit);

    @DeleteMapping("/api/v1/media")
    ResponseEntity<BaseResponse<String>> delete(@RequestParam String bucket);
}
