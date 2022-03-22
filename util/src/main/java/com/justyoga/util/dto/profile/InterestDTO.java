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
public class InterestDTO extends BaseDTO {
    @NotNull private UUID userId;
    @NotNull private Boolean trainer = false;
    @NotNull private Boolean lookingForTrainer = false;
    @NotNull private Boolean blogger = false;
}
