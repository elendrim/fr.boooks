package org.boooks.service;

import org.boooks.db.entity.UserEntity;
import org.boooks.exception.BusinessException;
import org.springframework.transaction.annotation.Transactional;

public interface IUserService {

	@Transactional  
	UserEntity findUserByEmail(String email);
	
	@Transactional  
	UserEntity registerUser(UserEntity userEntity) throws BusinessException ;
	
	@Transactional
	void activate(String email, String tempkey) throws Exception;
	  
}
