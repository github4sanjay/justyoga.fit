package com.justyoga.user.domain.dao;

import com.justyoga.user.domain.model.mysql.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    RoleEntity findByAuthority(String authority);
}
