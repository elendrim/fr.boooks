package org.boooks.db.dao;

import java.util.List;

import org.boooks.db.entity.MainComment;
import org.boooks.db.entity.SubComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ISubCommentDAO extends JpaRepository<SubComment, Long> { 

	List<MainComment> findByMainComment(@Param("mainComment") MainComment mainComment);

}