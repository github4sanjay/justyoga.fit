package com.justyoga.util.dto.profile;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class LocationWiseEPDTO implements Serializable {

    private Set<UUID> educationalPlacePublicIds;
    private Map<UUID, Set<UUID>> categoryWiseEducationalPlacePublicIds;

    public LocationWiseEPDTO() {}

    public LocationWiseEPDTO(
            Set<UUID> educationalPlacePublicIds,
            Map<UUID, Set<UUID>> categoryWiseEducationalPlacePublicIds) {
        this.educationalPlacePublicIds = educationalPlacePublicIds;
        this.categoryWiseEducationalPlacePublicIds = categoryWiseEducationalPlacePublicIds;
    }

    public Set<UUID> getEducationalPlacePublicIds() {
        return educationalPlacePublicIds;
    }

    public void setEducationalPlacePublicIds(Set<UUID> educationalPlacePublicIds) {
        this.educationalPlacePublicIds = educationalPlacePublicIds;
    }

    public Map<UUID, Set<UUID>> getCategoryWiseEducationalPlacePublicIds() {
        return categoryWiseEducationalPlacePublicIds;
    }

    public void setCategoryWiseEducationalPlacePublicIds(
            Map<UUID, Set<UUID>> categoryWiseEducationalPlacePublicIds) {
        this.categoryWiseEducationalPlacePublicIds = categoryWiseEducationalPlacePublicIds;
    }
}
