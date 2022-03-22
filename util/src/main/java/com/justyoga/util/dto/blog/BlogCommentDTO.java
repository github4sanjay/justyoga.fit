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
public class BlogCommentDTO extends BaseDTO {
    @NotEmpty private String text;
    @NotNull private UUID userId;
    @NotNull private UUID blogId;
}
