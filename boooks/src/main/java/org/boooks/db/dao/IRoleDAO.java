package org.boooks.db.dao;

import java.util.List;

import org.boooks.db.entity.SecurityRoleEntity;
import org.boooks.db.entity.SecurityRoleEntityPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IRoleDAO extends JpaRepository<SecurityRoleEntity, SecurityRoleEntityPk> {

	@Query("select r from SecurityRoleEntity r join r.user u where u.email = :email")  
	List<SecurityRoleEntity> findByEmail(@Param("email")String email);
	
}
