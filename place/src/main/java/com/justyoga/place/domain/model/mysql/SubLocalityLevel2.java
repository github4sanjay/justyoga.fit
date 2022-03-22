package com.justyoga.place.domain.model.mysql;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "subLocalities_level_2")
@Audited
@AuditOverrides({@AuditOverride(forClass = Base.class)})
public class SubLocalityLevel2 extends Base {

    @Column(nullable = false)
    private String name;

    @Column(name = "subLocality_level_1_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID subLocalityLevel1Id;

    public SubLocalityLevel2() {}

    public SubLocalityLevel2(String name, UUID subLocalityLevel1Id) {
        this.name = name;
        this.subLocalityLevel1Id = subLocalityLevel1Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getSubLocalityLevel1Id() {
        return subLocalityLevel1Id;
    }

    public void setSubLocalityLevel1Id(UUID subLocalityLevel1Id) {
        this.subLocalityLevel1Id = subLocalityLevel1Id;
    }
}
