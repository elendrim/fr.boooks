package org.boooks.db.dao;

import org.boooks.db.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IAuthorDAO extends JpaRepository<Author, Long> { 
	
	@Query("select a from Author a where a.name = :name")  
	Author getByName(@Param("name") String name);
	
}