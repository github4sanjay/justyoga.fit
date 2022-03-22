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
@Table(name = "interests")
@Audited
@AuditOverrides({@AuditOverride(forClass = Base.class)})
@Data
@EqualsAndHashCode(callSuper = true)
public class Interest extends Base {

    @Column(name = "user_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "is_trainer", nullable = false)
    private boolean trainer = false;

    @Column(name = "is_looking_for_trainer", nullable = false)
    private boolean lookingForTrainer = false;

    @Column(name = "is_blogger", nullable = false)
    private boolean blogger = false;
}
