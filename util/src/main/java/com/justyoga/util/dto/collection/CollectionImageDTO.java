package com.justyoga.util.dto.collection;

import com.justyoga.util.dto.BaseDTO;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class CollectionImageDTO extends BaseDTO {

    @NotNull private UUID collectionId;

    @NotNull private UUID imageId;
}
