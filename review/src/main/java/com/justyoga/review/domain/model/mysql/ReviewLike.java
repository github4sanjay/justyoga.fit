package com.justyoga.review.domain.model.mysql;

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
@Table(name = "review_likes")
@Audited
@AuditOverrides({@AuditOverride(forClass = Base.class)})
@Data
@EqualsAndHashCode(callSuper = true)
public class ReviewLike extends Base {

    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "review_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID reviewId;
}
