package com.justyoga.review.domain.model.mysql;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
public class Address extends Base {

    @Column(name = "formattedAddress", nullable = false)
    private String formattedAddress;

    @Column(name = "geohash_1", nullable = false)
    private String geohash1;

    @Column(name = "geohash_5", nullable = false)
    private String geohash5;

    @Column(name = "geohash_50", nullable = false)
    private String geohash50;

    @Column(name = "geohash_150", nullable = false)
    private String geohash150;

    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "country_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID countryId;

    @Column(name = "locality_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID localityId;

    @Column(name = "administrativeAreaLevel1_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID administrativeAreaLevel1Id;

    @Column(name = "subLocalityLevel1_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID subLocalityLevel1Id;

    @Column(name = "subLocalityLevel2_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID subLocalityLevel2Id;
}
