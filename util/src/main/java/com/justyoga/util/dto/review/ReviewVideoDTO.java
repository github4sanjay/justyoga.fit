package com.justyoga.util.dto.review;

import com.justyoga.util.dto.BaseDTO;
import java.util.UUID;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
public class ReviewVideoDTO extends BaseDTO {

    @NotEmpty private String url;
    @NotNull private UUID reviewId;
}
