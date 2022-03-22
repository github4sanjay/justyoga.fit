package com.justyoga.util.dto.place;

import java.io.Serializable;

public class LocationDetailedDTO implements Serializable {

    private CountryDTO country;
    private AdministrativeAreaLevel1DTO administrativeAreaLevel1;
    private LocalityDTO locality;
    private SubLocalityLevel1DTO subLocalityLevel1;
    private SubLocalityLevel2DTO subLocalityLevel2;

    public LocationDetailedDTO() {}

    public LocationDetailedDTO(
            CountryDTO country,
            AdministrativeAreaLevel1DTO administrativeAreaLevel1,
            LocalityDTO locality,
            SubLocalityLevel1DTO subLocalityLevel1,
            SubLocalityLevel2DTO subLocalityLevel2) {
        this.country = country;
        this.administrativeAreaLevel1 = administrativeAreaLevel1;
        this.locality = locality;
        this.subLocalityLevel1 = subLocalityLevel1;
        this.subLocalityLevel2 = subLocalityLevel2;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public AdministrativeAreaLevel1DTO getAdministrativeAreaLevel1() {
        return administrativeAreaLevel1;
    }

    public void setAdministrativeAreaLevel1(AdministrativeAreaLevel1DTO administrativeAreaLevel1) {
        this.administrativeAreaLevel1 = administrativeAreaLevel1;
    }

    public LocalityDTO getLocality() {
        return locality;
    }

    public void setLocality(LocalityDTO locality) {
        this.locality = locality;
    }

    public SubLocalityLevel1DTO getSubLocalityLevel1() {
        return subLocalityLevel1;
    }

    public void setSubLocalityLevel1(SubLocalityLevel1DTO subLocalityLevel1) {
        this.subLocalityLevel1 = subLocalityLevel1;
    }

    public SubLocalityLevel2DTO getSubLocalityLevel2() {
        return subLocalityLevel2;
    }

    public void setSubLocalityLevel2(SubLocalityLevel2DTO subLocalityLevel2) {
        this.subLocalityLevel2 = subLocalityLevel2;
    }
}
