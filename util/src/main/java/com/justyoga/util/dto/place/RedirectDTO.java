package com.justyoga.util.dto.place;

import java.io.Serializable;

public class RedirectDTO implements Serializable {
    private LocationDTO location;
    private String redirect;

    public RedirectDTO() {}

    public RedirectDTO(LocationDTO location, String redirect) {
        this.location = location;
        this.redirect = redirect;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
        this.location = location;
    }

    public String getRedirect() {
        return redirect;
    }

    public void setRedirect(String redirect) {
        this.redirect = redirect;
    }
}
