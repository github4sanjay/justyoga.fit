package com.justyoga.place.domain.model.mysql;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "locality")
@Audited
@AuditOverrides({@AuditOverride(forClass = Base.class)})
public class Locality extends Base {

    @Column(nullable = false)
    private String name;

    @Column(
            name = "administrative_area_level_1_id",
            nullable = false,
            columnDefinition = "BINARY(16)")
    private UUID administrativeAreaLevel1Id;

    public Locality() {}

    public Locality(String name, UUID administrativeAreaLevel1Id) {
        this.name = name;
        this.administrativeAreaLevel1Id = administrativeAreaLevel1Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getAdministrativeAreaLevel1Id() {
        return administrativeAreaLevel1Id;
    }

    public void setAdministrativeAreaLevel1Id(UUID administrativeAreaLevel1Id) {
        this.administrativeAreaLevel1Id = administrativeAreaLevel1Id;
    }
}
