package com.justyoga.user.domain.model.mysql;

import javax.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "roles")
@Audited
@Data
@NoArgsConstructor
public class RoleEntity {

    private static final long serialVersionUID = -8186644851823152209L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column private String authority;

    public RoleEntity(String authority) {
        this.authority = authority;
    }
}
