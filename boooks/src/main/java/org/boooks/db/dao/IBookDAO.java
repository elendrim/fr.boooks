package org.boooks.db.dao;

import java.util.List;

import org.boooks.db.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IBookDAO extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> { 
	
	@Query("select b from Book b join fetch b.type join fetch b.genre left join fetch b.authors where b.id = :id")  
	Book getById(@Param("id") Long id);

	@Query("select b from Book b left join fetch b.authors ")
	List<Book> findAllBooks(Pageable pageable);
	
	
	@Query(
		value="select b from Book b join b.buyes buy left join fetch b.authors where buy.userEntity.id = :userId ",
		countQuery="select count(*) from Book b join b.buyes buy where buy.userEntity.id = :userId "
	)
	Page<Book> findBooksByPurchasing(@Param("userId") long userId, Pageable pageable);
		
}
