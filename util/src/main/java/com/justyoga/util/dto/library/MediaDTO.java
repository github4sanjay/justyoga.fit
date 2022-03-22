package com.justyoga.util.dto.library;

import com.justyoga.util.dto.profile.MediaType;
import java.util.concurrent.TimeUnit;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MediaDTO {
    @NotEmpty private String bucketName;
    @NotNull private MediaType mediaType;
    @NotNull private Long expiration;
    @NotNull private TimeUnit timeUnit;
}
