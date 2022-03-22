package com.justyoga.util.dto.place;

import com.justyoga.util.dto.BaseDTO;
import java.io.Serializable;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class SubLocalityLevel2DTO extends BaseDTO implements Serializable {

    @NotEmpty private String name;
    @NotNull private UUID subLocalityLevel1Id;
}
