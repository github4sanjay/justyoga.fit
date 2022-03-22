package com.justyoga.client.profile;

import com.justyoga.util.dto.profile.ImageDTO;
import com.justyoga.util.dto.profile.VideoDTO;
import com.justyoga.util.response.BaseResponse;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "profile")
public interface ProfileClient {
    @GetMapping(value = "/api/v1/images/{id}")
    ResponseEntity<BaseResponse<ImageDTO>> getImage(@PathVariable("id") UUID id);

    @GetMapping(value = "/api/v1/videos/{id}")
    ResponseEntity<BaseResponse<VideoDTO>> getVideo(@PathVariable("id") UUID id);
}
