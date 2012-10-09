package org.boooks.db.dao;

import org.boooks.db.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IBookDAO extends JpaRepository<Book, Long> { 
	
	@Query("from Book b join fetch b.type join fetch b.genre where b.id = :id")  
	Book getById(@Param("id") long id);
	
}