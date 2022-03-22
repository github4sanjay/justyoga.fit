package com.justyoga.util.dto.bookmark;

import com.justyoga.util.dto.BaseDTO;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserBookmarkDTO extends BaseDTO {
    @NotNull private UUID userId;
    @NotNull private UUID bookmarkedBy;
}
