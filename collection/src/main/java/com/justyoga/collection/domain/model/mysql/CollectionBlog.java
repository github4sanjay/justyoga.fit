package com.justyoga.collection.domain.model.mysql;

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
@Table(name = "collection_blogs")
@Audited
@AuditOverrides({@AuditOverride(forClass = Base.class)})
@Data
@EqualsAndHashCode(callSuper = true)
public class CollectionBlog extends Base {

    @Column(name = "blog_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID blogId;

    @Column(name = "collection_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID collectionId;
}
