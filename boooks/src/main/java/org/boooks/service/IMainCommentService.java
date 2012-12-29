package org.boooks.service;

import java.util.List;

import org.boooks.db.entity.Book;
import org.boooks.db.entity.MainComment;
import org.boooks.db.entity.UserEntity;
import org.springframework.transaction.annotation.Transactional;

public interface IMainCommentService {

	@Transactional
	List<MainComment> getByBook(Book book);
	
	@Transactional
	List<MainComment> getByUser(UserEntity user);
	
	@Transactional
	MainComment save(MainComment mainComment);
	
	@Transactional
	MainComment update(MainComment mainComment);
	
}
