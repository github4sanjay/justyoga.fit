package com.justyoga.profile.domain.model.mysql;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "user_basic_info")
@Audited
@AuditOverrides({@AuditOverride(forClass = Address.class), @AuditOverride(forClass = Base.class)})
@Data
@EqualsAndHashCode(callSuper = true)
public class UserBasicInfo extends Address {

    @Column(name = "user_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "experience_years", nullable = false)
    private Long experienceYears = 0L;

    @Column(name = "experience_months", nullable = false)
    private Long experienceMonths = 0L;

    @Column(name = "age", nullable = false)
    private Long age = 0L;

    @Column(name = "is_active", nullable = false)
    private boolean active = false;
}
