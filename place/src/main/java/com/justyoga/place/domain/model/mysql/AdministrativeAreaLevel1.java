package com.justyoga.place.domain.model.mysql;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "administrative_area_level_1")
@Audited
@AuditOverrides({@AuditOverride(forClass = Base.class)})
public class AdministrativeAreaLevel1 extends Base {

    @Column(nullable = false)
    private String name;

    @Column(name = "country_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID countryId;

    public AdministrativeAreaLevel1(String name, UUID countryId) {
        this.name = name;
        this.countryId = countryId;
    }

    public AdministrativeAreaLevel1() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getCountryId() {
        return countryId;
    }

    public void setCountryId(UUID countryId) {
        this.countryId = countryId;
    }
}
