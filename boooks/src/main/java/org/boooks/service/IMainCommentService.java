package org.boooks.service;

import java.util.List;

import org.boooks.db.entity.MainComment;
import org.boooks.db.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface IMainCommentService {

	@Transactional
	Page<MainComment> findByBookId(Long bookId, Pageable pageable) ;
	
	@Transactional
	List<MainComment> getByUser(UserEntity user);
	
	@Transactional
	MainComment save(MainComment mainComment);
	
	
}
