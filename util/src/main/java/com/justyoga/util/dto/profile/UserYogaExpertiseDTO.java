package com.justyoga.util.dto.profile;

import com.justyoga.util.dto.BaseDTO;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserYogaExpertiseDTO extends BaseDTO {
    @NotNull private UUID userId;
    @NotNull private UUID yogaExpertiseId;
}
