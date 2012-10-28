package org.boooks.service;

import org.books.utils.BoooksDataFactory;
import org.boooks.db.entity.UserEntity;
import org.boooks.exception.BusinessException;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.transaction.annotation.Transactional;

public interface IUserService {

	@Transactional  
	UserEntity findUserByEmail(String email);
	
	@Transactional  
	UserEntity registerUser(UserEntity userEntity) throws BusinessException ;

	@Transactional
	UserEntity registerUser(UserEntity userEntity, Boolean sendEmail) throws BusinessException;
	
	@Transactional
	void activate(String email, String tempkey) throws Exception;

	@Transactional
	UserEntity saveUser(UserEntity userEntity) throws BusinessException;

	@Transactional
	void deleteUser(String email);
	
	@Transactional
	UserEntity createDummyUser(Boolean admin, BoooksDataFactory df) throws Exception;

	  
}
