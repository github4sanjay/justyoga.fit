package com.justyoga.util.dto.place;

import java.io.Serializable;

public class AutocompleteDTO implements Serializable {
    private String description;
    private String placeId;

    public AutocompleteDTO() {}

    public AutocompleteDTO(String description, String placeId) {
        this.description = description;
        this.placeId = placeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
