package com.justyoga.profile.domain.model.mysql;

import java.util.UUID;
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
@Table(name = "videos")
@Audited
@AuditOverrides({@AuditOverride(forClass = Base.class)})
@Data
@EqualsAndHashCode(callSuper = true)
public class Video extends Base {

    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Type(type = "text")
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "duration", nullable = false)
    private String duration;

    @Type(type = "text")
    @Column(name = "cover_pic", nullable = false)
    private String coverPic;

    @Type(type = "text")
    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;
}
