package com.justyoga.util.dto.collection;

import com.justyoga.util.dto.AddressDTO;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CollectionDTO extends AddressDTO {

    @NotEmpty private String name;

    @NotEmpty(message = "Description cannot be empty")
    @Size(min = 10, max = 500, message = "Description must be between 10 and 500 characters long")
    private String description;

    private String coverUrl;
}
