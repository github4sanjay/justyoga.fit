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
public class MedicalExpertiseDTO extends BaseDTO {
    @NotEmpty(message = "Name cannot be empty")
    @Size(min = 2, max = 32, message = "Name must be between 2 and 20 characters long")
    private String name;

    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters long")
    private String description;
}
