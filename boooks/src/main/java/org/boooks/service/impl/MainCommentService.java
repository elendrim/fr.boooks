package org.boooks.service.impl;

import java.util.List;

import org.boooks.db.dao.IBookDAO;
import org.boooks.db.dao.IMainCommentDAO;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.MainComment;
import org.boooks.db.entity.UserEntity;
import org.boooks.service.IMainCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class MainCommentService implements IMainCommentService {
	
    @Autowired 
    private IBookDAO bookDAO;
    
    @Autowired
    private IMainCommentDAO mainCommentDAO;

	
	@Override
	@Transactional(readOnly=true)
	public Page<MainComment> findByBookId(Long bookId, Pageable pageable) {
		return mainCommentDAO.findByBookId(bookId, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public List<MainComment> getByUser(UserEntity user) {
		return mainCommentDAO.findByUserId(user.getId());
	}

	@Override
	@Transactional(readOnly=false)
	public MainComment save(MainComment mainComment) {
		return mainCommentDAO.save(mainComment);
	}
	
	
}
