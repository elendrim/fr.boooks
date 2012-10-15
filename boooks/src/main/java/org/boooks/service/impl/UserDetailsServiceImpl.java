package org.boooks.service.impl;

import org.boooks.db.dao.IUserDAO;
import org.boooks.db.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userDetailsService") 
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired 
  private IUserDAO userDao;
  
  @Autowired 
  private Assembler assembler;

  @Transactional(readOnly = true)
  public UserDetails loadUserByUsername(String username) 
		  throws UsernameNotFoundException, DataAccessException {

    
    UserEntity userEntity = userDao.findByName(username);
    if (userEntity == null)
      throw new UsernameNotFoundException("user not found");

    return assembler.buildUserFromUserEntity(userEntity);
  }
}