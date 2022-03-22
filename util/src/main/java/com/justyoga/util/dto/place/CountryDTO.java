package com.justyoga.util.dto.place;

import com.justyoga.util.dto.BaseDTO;
import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CountryDTO extends BaseDTO implements Serializable {
    @NotEmpty private String name;
}
