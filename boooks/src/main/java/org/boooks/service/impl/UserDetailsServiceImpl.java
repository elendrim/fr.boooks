package org.boooks.service.impl;

import java.util.List;
import java.util.UUID;

import org.boooks.db.dao.IRoleDAO;
import org.boooks.db.dao.ITempKeyDAO;
import org.boooks.db.dao.IUserDAO;
import org.boooks.db.entity.SecurityRoleEntity;
import org.boooks.db.entity.SecurityRoleEntityPk;
import org.boooks.db.entity.TempKey;
import org.boooks.db.entity.TempKeyPK;
import org.boooks.db.entity.UserEntity;
import org.boooks.exception.BusinessException;
import org.boooks.service.IMailRegistrationService;
import org.boooks.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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



}