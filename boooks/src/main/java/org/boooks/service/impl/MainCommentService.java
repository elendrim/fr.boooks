package org.boooks.service.impl;

import java.util.Date;
import java.util.List;

import org.boooks.db.dao.IBookDAO;
import org.boooks.db.dao.IMainCommentDAO;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.MainComment;
import org.boooks.db.entity.UserEntity;
import org.boooks.service.IMainCommentService;
import org.boooks.utils.BoooksDataFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	@Transactional
	public
	List<MainComment> getByBook(Book book) {
		return mainCommentDAO.findByBookId(book.getId());
	}

	@Override
	@Transactional
	public
	List<MainComment> getByUser(UserEntity user) {
		return mainCommentDAO.findByUserId(user.getId());
	}

	@Override
	@Transactional
	public
	MainComment save(MainComment mainComment) {
		return mainCommentDAO.save(mainComment);
	}

	@Override
	@Transactional
	public
	MainComment update(MainComment mainComment) {
		return mainCommentDAO.save(mainComment);
	}
	
	
	
	@Override
	@Transactional
	public MainComment createDummyMainComment(UserEntity user, Book book, BoooksDataFactory df) throws Exception {
		
		MainComment mc = new MainComment();
		
		mc.setModifDate(df.df.getDateBetween(book.getPublishDate(), new Date()));
		mc.setText(df.getText(100, 1000));
		mc.setTitle(df.getText(1, 10));
		mc.setBook(book);
		mc.setUser(user);
		
		return this.save(mc);
	}
	
}
