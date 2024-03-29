package com.justyoga.blog.domain.model.mysql;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "blog_images")
@Audited
@AuditOverrides({@AuditOverride(forClass = Base.class)})
@Data
@EqualsAndHashCode(callSuper = true)
public class BlogImage extends Base {

    @Type(type = "text")
    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "blog_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID blogId;
}
