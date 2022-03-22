package com.justyoga.blog.domain.model.mysql;

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
@Table(name = "blogs")
@Audited
@AuditOverrides({@AuditOverride(forClass = Base.class)})
@Data
@EqualsAndHashCode(callSuper = true)
public class Blog extends Base {

    @Lob
    @Column(name = "blog_title")
    private String blogTitle;

    @Lob
    @Column(name = "blog_text")
    private String blogText;

    @Lob
    @Column(name = "blog_content")
    private String blogContent;

    @Column(name = "user_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID userId;

    @Column(name = "is_published", nullable = false)
    private boolean published = true;

    @Type(type = "text")
    @Column(name = "cover_url")
    private String coverUrl;
}
