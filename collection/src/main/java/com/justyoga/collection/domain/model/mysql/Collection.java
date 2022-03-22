package com.justyoga.collection.domain.model.mysql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "collections")
@Audited
@AuditOverrides({@AuditOverride(forClass = Address.class), @AuditOverride(forClass = Base.class)})
@Data
@EqualsAndHashCode(callSuper = true)
public class Collection extends Address {

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @Type(type = "text")
    @Column(name = "url")
    private String coverUrl;
}
