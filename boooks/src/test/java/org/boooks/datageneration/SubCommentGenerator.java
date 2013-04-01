package org.boooks.datageneration;

import java.util.Date;

import org.boooks.db.dao.ISubCommentDAO;
import org.boooks.db.entity.MainComment;
import org.boooks.db.entity.SubComment;
import org.boooks.db.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubCommentGenerator {
	
	@Autowired private MainCommentGenerator mainCommentGen;
	@Autowired private UserEntityGenerator userGen;
	@Autowired private ISubCommentDAO subCommentDao;
	
	
	public SubComment create(DataGenerator dg) throws Exception {
		return create(dg, null, null); 
	}
	
	public SubComment create(DataGenerator dg, UserEntity user, MainComment mainComment) throws Exception {
		
		SubComment sc = fill(new SubComment(), dg);
		
		sc.setUser(user == null ? userGen.create(dg) : user);
		sc.setMainComment(mainComment == null ? mainCommentGen.create(dg) : mainComment);
		
		subCommentDao.save(sc);
		
		return sc;
		
	}
	
	
	static public SubComment fill(SubComment sc, DataGenerator dg){
		
		sc.setModifDate(new Date());
		sc.setText(dg.getRandomText(10, 200));
		
		return sc;
	}
	
	
	
	
	

}
