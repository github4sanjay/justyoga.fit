package com.justyoga.util.dto.profile;

import com.justyoga.util.dto.BaseDTO;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class YogaExpertiseDTO extends BaseDTO {
    @NotEmpty
    @Size(min = 2, max = 32, message = "Name must be between 2 and 20 characters long")
    private String name;

    @NotEmpty
    @Size(min = 2, max = 32, message = "Description must be between 10 and 500 characters long")
    private String description;
}
