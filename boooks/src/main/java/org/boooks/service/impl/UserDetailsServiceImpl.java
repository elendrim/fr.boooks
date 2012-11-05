package org.boooks.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.boooks.utils.BoooksDataFactory;
import org.boooks.db.dao.IRoleDAO;
import org.boooks.db.dao.ITempKeyDAO;
import org.boooks.db.dao.IUserDAO;
import org.boooks.db.entity.SecurityRoleEntity;
import org.boooks.db.entity.SecurityRoleEntityPk;
import org.boooks.db.entity.SexEnum;
import org.boooks.db.entity.TempKey;
import org.boooks.db.entity.TempKeyPK;
import org.boooks.db.entity.UserEntity;
import org.boooks.exception.BusinessException;
import org.boooks.service.IMailRegistrationService;
import org.boooks.service.IUserService;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService") 
public class UserDetailsServiceImpl implements UserDetailsService, IUserService {

	@Autowired 
	private IUserDAO userDAO;

	@Autowired 
	private IRoleDAO roleDAO;

	@Autowired 
	private ITempKeyDAO tempKeyDAO;

	@Autowired 
	private Assembler assembler;

	@Autowired
	private IMailRegistrationService mailRegistrationService;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException, DataAccessException {


		UserEntity userEntity = userDAO.findByEmail(username);
		if (userEntity == null)
			throw new UsernameNotFoundException("user not found");

		return assembler.buildUserFromUserEntity(userEntity);
	}

	@Transactional(readOnly=true)
	public UserEntity findUserByEmail(String email) {
		return userDAO.findByEmail(email);
	}


	@Override
	@Transactional(readOnly=false)
	public UserEntity registerUser(UserEntity userEntity) throws BusinessException {
		return registerUser(userEntity, true);
	}
	
	
	@Override
	@Transactional(readOnly=false)
	public UserEntity registerUser(UserEntity userEntity, Boolean sendEmail) throws BusinessException {

		UserEntity userAlreadyExist = userDAO.findByEmail(userEntity.getEmail());
		if ( userAlreadyExist != null ) {
			throw new BusinessException("email", "email.alreadyexist", "L'email existe déjà");
		}


		UserEntity userEntityBase = userDAO.save(userEntity);

		SecurityRoleEntity sre = new SecurityRoleEntity();
		SecurityRoleEntityPk srePk = new SecurityRoleEntityPk();
		srePk.setRoleName("USER");
		srePk.setUserId(userEntityBase.getId());
		sre.setId(srePk);

		roleDAO.save(sre);

		TempKey tempKey = new TempKey();
		TempKeyPK tempKeyPK = new TempKeyPK();
		tempKeyPK.setEmail(userEntityBase.getEmail());
		String uuid = UUID.randomUUID().toString();
		tempKeyPK.setTempkey(uuid);
		tempKey.setTempKeyPK(tempKeyPK);

		tempKey = tempKeyDAO.save(tempKey);


		if(sendEmail){
			mailRegistrationService.register(userEntityBase, tempKey);
		}

		return userEntityBase;
	}

	@Override
	@Transactional(readOnly=false)
	public void activate(String email, String tempkey) throws Exception {

		TempKey tempkeyBase = tempKeyDAO.findByEmailAndTempKey(email, tempkey);

		if ( tempkeyBase != null ) {
			UserEntity userEntity = userDAO.findByEmail(email);
			userEntity.setActive(true);
			userDAO.save(userEntity);

			tempKeyDAO.delete(tempkeyBase);
		} else {
			throw new Exception("La clé n'est pas trouvé. L'utilisateur n'a pu être activé.");
		}
	}

	@Override
	@Transactional(readOnly=false)
	public UserEntity saveUser(UserEntity userEntity) throws BusinessException  {
		return userDAO.save(userEntity);
	}

	@Override
	@Transactional(readOnly=false)
	public void deleteUser(String email) {

		List<TempKey> tempKey = tempKeyDAO.findByEmail(email);	
		tempKeyDAO.deleteInBatch(tempKey);

		List<SecurityRoleEntity> securityRoleEntities = roleDAO.findByEmail(email);
		roleDAO.deleteInBatch(securityRoleEntities);

		UserEntity userEntity = userDAO.findByEmail(email);
		userDAO.delete(userEntity);

	}

	/*
	 * I didn't know where to put it, maybe in the test part someday
	 * Use DataFactory for creating stuff
	 * 
	 * For testing sake, the password is always the string "pass"
	 * @see org.boooks.service.IUserService#createDummyUser(java.lang.Boolean)
	 */
	@Override
	@Transactional
	public UserEntity createDummyUser(Boolean admin, BoooksDataFactory df) throws Exception {
		//TODO set use the admin parameter
		//TODO set a test, for if we are in dev or procduction
		//see also : http://code.google.com/p/jpamock/
		
		UserEntity user = new UserEntity();
		
		
		String base_email = df.df.getEmailAddress();
		String email = base_email;
		
		//try to have a uniq email, because the data generator don't prove the unicity
		if(findUserByEmail(email) != null){
			for(int i=0;i<1000;i++){
				email = i + "_" + base_email;
				if(findUserByEmail(email) == null) break;
			}
		}
		
		user.setPassword("pass");
		user.setActive(false);
		user.setBirthDate(df.df.getBirthDate());
		user.setEmail(email);
		user.setFirstname(df.df.getFirstName());
		user.setLastname(df.df.getLastName());
		
		SexEnum sex = df.df.getItem(SexEnum.values());
		user.setSex(sex);
		
		ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);
		user.setPassword(shaPasswordEncoder.encodePassword(user.getPassword(), null));
	
	
		user = this.registerUser(user, false);
		
		String tempKey = tempKeyDAO.findByEmail(user.getEmail()).get(0).getTempKeyPK().getTempkey();
		this.activate(email, tempKey);
		
		return user;
	}

}