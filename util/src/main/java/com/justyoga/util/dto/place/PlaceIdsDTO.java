package com.justyoga.util.dto.place;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaceIdsDTO {
    UUID countryId;
    UUID administrativeAreaLevel1Id;
    UUID localityId;
    UUID subLocalityLevel1Id;
    UUID subLocalityLevel2Id;
}
