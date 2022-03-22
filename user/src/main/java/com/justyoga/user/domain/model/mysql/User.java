package com.justyoga.user.domain.model.mysql;

import java.util.List;
import javax.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.AuditOverrides;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "users")
@Audited
@AuditOverrides({@AuditOverride(forClass = Base.class)})
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends Base {

    @Column(name = "firebase_uid", nullable = false)
    private String firebaseUid;

    @Column(name = "name")
    private String name;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "is_email_verified")
    private Boolean emailVerified;

    @Type(type = "text")
    @Column(name = "profile_pic")
    private String profilePic;

    @Lob
    @Column(name = "cover_pic")
    private String coverPic;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Type(type = "text")
    @Column(name = "provider_id")
    private String providerId;

    @Column(name = "password", nullable = false)
    private String password;

    @Type(type = "text")
    @Column(name = "description")
    private String description;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoleEntity> authorities;
}
