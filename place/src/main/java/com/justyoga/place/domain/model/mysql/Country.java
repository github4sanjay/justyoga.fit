package com.justyoga.place.domain.model.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "country")
@Audited
@AuditOverrides({@AuditOverride(forClass = Base.class)})
public class Country extends Base {

    @Column(nullable = false)
    private String name;

    public Country(String name) {
        this.name = name;
    }

    public Country() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
