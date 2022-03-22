package com.justyoga.util.dto.blog;

import com.justyoga.util.dto.BaseDTO;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class BlogDTO extends BaseDTO {
    @NotNull private UUID userId;
    @NotEmpty private String blogTitle;
    @NotEmpty private String blogText;
    @NotEmpty private String blogContent;
    private Boolean published;
    private String coverUrl;
}
