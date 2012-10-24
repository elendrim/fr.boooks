package org.boooks.db.dao;

import org.boooks.db.entity.SecurityRoleEntity;
import org.boooks.db.entity.SecurityRoleEntityPk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleDAO extends JpaRepository<SecurityRoleEntity, SecurityRoleEntityPk> {

}
