package com.justyoga.place.domain.model.mysql;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "subLocalities_level_1")
@Audited
@AuditOverrides({@AuditOverride(forClass = Base.class)})
public class SubLocalityLevel1 extends Base {

    @Column(nullable = false)
    private String name;

    @Column(name = "locality_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID localityId;

    public SubLocalityLevel1() {}

    public SubLocalityLevel1(String name, UUID localityId) {
        this.name = name;
        this.localityId = localityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getLocalityId() {
        return localityId;
    }

    public void setLocalityId(UUID localityId) {
        this.localityId = localityId;
    }
}
