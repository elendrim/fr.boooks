package org.boooks.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.boooks.db.entity.SecurityRoleEntity;
import org.boooks.db.entity.UserEntity;
import org.boooks.web.security.UserBoooks;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("assembler")
public class Assembler {

  @Transactional(readOnly = true)
  public User buildUserFromUserEntity(UserEntity userEntity) {

    String email = userEntity.getEmail();
    String firstname = userEntity.getFirstname();
    String lastname = userEntity.getLastname();
    String password = userEntity.getPassword();
    boolean enabled = userEntity.isActive();
    boolean accountNonExpired = userEntity.isActive();
    boolean credentialsNonExpired = userEntity.isActive();
    boolean accountNonLocked = userEntity.isActive();

    Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    for (SecurityRoleEntity role : userEntity.getRoles()) {
    	authorities.add(new SimpleGrantedAuthority(role.getId().getRoleName()));
    }
    
    return new UserBoooks(email, firstname, lastname, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
  }
}