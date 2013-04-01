package org.boooks.datageneration;

import org.boooks.db.dao.ITempKeyDAO;
import org.boooks.db.entity.SexEnum;
import org.boooks.db.entity.UserEntity;
import org.boooks.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserEntityGenerator {
	
	@Autowired
	private ITempKeyDAO tempKeyDao;
	
	@Autowired
	private IUserService userService;
		
	
	public UserEntity create(DataGenerator dg) throws Exception{
		
		UserEntity user = new UserEntity();
		UserEntityGenerator.fill(user, dg);
		
		String email = user.getEmail();
		
		user.setActive(false);

		user = userService.registerUser(user, false);
		
		String tempKey = tempKeyDao.findByEmail(user.getEmail()).get(0).getTempKeyPK().getTempkey();
		
		userService.activate(email, tempKey);
				
		return user;
	}
	
	
	
	static public UserEntity fill(UserEntity user, DataGenerator dg){
		
		user.setFirstname(dg.getFirstName());
		user.setLastname(dg.getLastName());
		user.setEmail(dg.getUniqueEmailAddress());
		user.setBirthDate(dg.getBirthDate());	
		user.setSex( dg.getItem(SexEnum.values()) );

		String defaultPassword = "pass";
		
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
		user.setPassword(shaPasswordEncoder.encodePassword(defaultPassword, null));
			
		return user;
	}
	
	

}
