package com.justyoga.util.dto;

import java.util.UUID;
import javax.validation.constraints.NotNull;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddressDTO extends BaseDTO {
    @NotNull private String formattedAddress;
    @NotNull private String geohash1;
    @NotNull private String geohash5;
    @NotNull private String geohash50;
    @NotNull private String geohash150;
    @NotNull private Double latitude;
    @NotNull private Double longitude;
    @NotNull private String postalCode;
    @NotNull private UUID countryId;
    @NotNull private UUID localityId;
    @NotNull private UUID administrativeAreaLevel1Id;
    @NotNull private UUID subLocalityLevel1Id;
    @NotNull private UUID subLocalityLevel2Id;
}
