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
@Table(name = "image_info")
@Data
public class ImageInfo {

    @Id
    @Column(name = "id", updatable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Type(type = "text")
    @Column(name = "url", nullable = false)
    private String url;

    @Type(type = "text")
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "firebase_uid", updatable = false)
    private String firebaseUid;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true, updatable = false)
    private String email;

    @Column(name = "is_email_verified", updatable = false)
    private Boolean emailVerified;

    @Type(type = "text")
    @Column(name = "profile_pic", updatable = false)
    private String profilePic;

    @Lob
    @Column(name = "user_cover_pic", updatable = false)
    private String userCoverPic;

    @Column(name = "phone_number", updatable = false)
    private String phoneNumber;

    @Type(type = "text")
    @Column(name = "provider_id", updatable = false)
    private String providerId;

    @Type(type = "text")
    @Column(name = "user_description", updatable = false)
    private String userDescription;

    @Column(name = "is_trainer", updatable = false)
    private Boolean trainer = false;

    @Column(name = "is_looking_for_trainer", updatable = false)
    private Boolean lookingForTrainer = false;

    @Column(name = "is_blogger", updatable = false)
    private Boolean blogger = false;

    @Column(name = "experience_years", updatable = false)
    private Long experienceYears = 0L;

    @Column(name = "experience_months", updatable = false)
    private Long experienceMonths = 0L;

    @Column(name = "age", updatable = false)
    private Long age = 0L;

    @Column(name = "formattedAddress", updatable = false)
    private String formattedAddress;

    @Column(name = "geohash_1", updatable = false)
    private String geohash1;

    @Column(name = "geohash_5", updatable = false)
    private String geohash5;

    @Column(name = "geohash_50", updatable = false)
    private String geohash50;

    @Column(name = "geohash_150", updatable = false)
    private String geohash150;

    @Column(name = "latitude", updatable = false)
    private Double latitude;

    @Column(name = "longitude", updatable = false)
    private Double longitude;

    @Column(name = "postal_code", updatable = false)
    private String postalCode;

    @Column(name = "country_id", updatable = false, columnDefinition = "BINARY(16)")
    private UUID countryId;

    @Column(name = "locality_id", updatable = false, columnDefinition = "BINARY(16)")
    private UUID localityId;

    @Column(
            name = "administrativeAreaLevel1_id",
            updatable = false,
            columnDefinition = "BINARY(16)")
    private UUID administrativeAreaLevel1Id;

    @Column(name = "subLocalityLevel1_id", updatable = false, columnDefinition = "BINARY(16)")
    private UUID subLocalityLevel1Id;

    @Column(name = "subLocalityLevel2_id", updatable = false, columnDefinition = "BINARY(16)")
    private UUID subLocalityLevel2Id;

    @Column(name = "rating", updatable = false)
    private Double rating = 0.0;

    @Column(name = "review_count", updatable = false)
    private Long reviewCount = 0L;

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
