package com.justyoga.util.dto.profile;

import com.justyoga.util.dto.BaseDTO;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class ImageDTO extends BaseDTO {
    @NotNull private UUID userId;
    @NotEmpty private String url;
    @NotEmpty private String title;
    @NotEmpty private String description;
}
