package org.boooks.db.dao;

import java.util.List;

import org.boooks.db.entity.TempKey;
import org.boooks.db.entity.TempKeyPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ITempKeyDAO extends JpaRepository<TempKey, TempKeyPK> {

	@Query("from TempKey where id.email = :email and id.tempkey = :tempkey")  
	TempKey findByEmailAndTempKey(@Param("email") String email, @Param("tempkey") String tempKey);

	@Query("from TempKey where id.email = :email")  
	List<TempKey> findByEmail(@Param("email") String email); 

}
