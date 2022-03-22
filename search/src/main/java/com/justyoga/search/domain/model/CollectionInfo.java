package com.justyoga.search.domain.model;

import java.util.Date;
import java.util.UUID;
import javax.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "collection_info")
@Data
public class CollectionInfo {

    @Id
    @Column(name = "id", updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Type(type = "text")
    @Column(name = "url")
    private String coverUrl;

    @Column(name = "blog_count", updatable = false)
    private Long blogCount = 0L;

    @Column(name = "image_count", updatable = false)
    private Long imageCount = 0L;

    @Column(name = "video_count", updatable = false)
    private Long videoCount = 0L;

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

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;

    @Column(name = "created_by", updatable = false, columnDefinition = "BINARY(16)")
    @CreatedBy
    private UUID createdBy;

    @Column(name = "modified_by", columnDefinition = "BINARY(16)")
    @LastModifiedBy
    private UUID modifiedBy;
}
