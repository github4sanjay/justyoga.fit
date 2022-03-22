package com.justyoga.util.dto.profile;

import com.justyoga.util.dto.AddressDTO;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserBasicInfoDTO extends AddressDTO {
    @NotNull private UUID userId;
    @NotNull private Long experienceYears = 0L;
    @NotNull private Long experienceMonths = 0L;
    @NotNull private Long age = 0L;
    private Boolean active;
}
