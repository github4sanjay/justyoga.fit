package com.justyoga.review.domain.model.mysql;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "reviews")
@Audited
@AuditOverrides({@AuditOverride(forClass = Base.class)})
@Data
@EqualsAndHashCode(callSuper = true)
public class Review extends Base {

    @Column(name = "rating", nullable = false)
    private Double rating = 0.0;

    @Lob
    @Column(name = "review_text")
    private String reviewText;

    @Lob
    @Column(name = "review_content")
    private String reviewContent;

    @Column(name = "reviewed_by", nullable = false, columnDefinition = "BINARY(16)")
    private UUID reviewedBy;

    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "is_published", nullable = false)
    private boolean published = true;
}
