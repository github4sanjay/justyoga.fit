package com.justyoga.util.dto.blog;

import com.justyoga.util.dto.BaseDTO;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class BlogLikeDTO extends BaseDTO {
    @NotNull private UUID userId;
    @NotNull private UUID blogId;
}
