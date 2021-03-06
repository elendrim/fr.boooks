package org.boooks.db.dao;

import org.boooks.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IUserDAO extends JpaRepository<UserEntity, Long> {

	@Query("from UserEntity u left join fetch u.roles where u.email = :email")  
	UserEntity findByEmail(@Param("email") String email);

}
