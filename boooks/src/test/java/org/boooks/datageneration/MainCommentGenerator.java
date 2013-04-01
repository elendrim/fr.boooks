package org.boooks.datageneration;

import java.util.Date;

import org.boooks.db.dao.IMainCommentDAO;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.MainComment;
import org.boooks.db.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainCommentGenerator {

	@Autowired private IMainCommentDAO mainCommentDao;
	@Autowired private BookGenerator bookGen;
	@Autowired private UserEntityGenerator userGen;
	
	
	public MainComment create(DataGenerator dg) throws Exception{
		return create(dg, null, null);
	}
	
	public MainComment create(DataGenerator dg, UserEntity user, Book book) throws Exception{
		
		MainComment mc = fill(new MainComment(), dg);

		mc.setBook(book == null ? bookGen.create(dg) : book);
		mc.setUser(user == null ? userGen.create(dg) : user);
		
		mainCommentDao.save(mc);
		
		return mc;
	}
	
	
	
	static public MainComment fill(MainComment mc, DataGenerator dg){
		
		mc.setModifDate(new Date());
		mc.setText(dg.getText(100, 1000));
		mc.setTitle(dg.getText(1, 10));
		
		return mc;
	}
	
	
	
	
}
