package org.boooks.db.dao;

import java.util.List;

import org.boooks.db.entity.MainComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface IMainCommentDAO extends JpaRepository<MainComment, Long> { 

	@Query(value="select mc FROM MainComment mc " +
			"join mc.book " +
			"join fetch mc.user " +
			"left join fetch mc.subComments sc " +
			"left join fetch sc.user " +
			"where mc.book.id = :bookId",
			countQuery="select count(*) from MainComment mc where mc.book.id = :bookId"
		)
	Page<MainComment> findByBookId(@Param("bookId") Long bookId, Pageable pageable);
	
	@Query("SELECT mc FROM MainComment mc WHERE USER_ID = :userId")
	List<MainComment> findByUserId(@Param("userId") Long userId);
	
}

