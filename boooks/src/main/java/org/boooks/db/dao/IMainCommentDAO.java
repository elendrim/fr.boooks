package org.boooks.db.dao;

import java.util.List;

import org.boooks.db.entity.MainComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IMainCommentDAO extends JpaRepository<MainComment, Long> { 

	@Query("select mc FROM MainComment mc " +
			"join fetch mc.book " +
			"join fetch mc.user " +
			"join fetch mc.subComments sc " +
			"join fetch sc.user " +
			"where mc.book.id = :bookId")
	List<MainComment> findByBookId(@Param("bookId") Long bookId);
	
	@Query("SELECT mc FROM MainComment mc WHERE USER_ID = :userId")
	List<MainComment> findByUserId(@Param("userId") Long userId);
	
}

