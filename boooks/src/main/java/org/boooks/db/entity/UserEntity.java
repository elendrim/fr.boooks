package org.boooks.db.entity;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="USER")
public class UserEntity {

	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)  
	@Column(name="USER_ID")
	private Long id;
	
	@Column(name="NAME")
	private String name;

	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="ACTIVE")
	private boolean active;
	
	@OneToMany(mappedBy="user")
	private List<SecurityRoleEntity> roles;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isActive() {
		return active;
	}
	
	public void setActive(boolean active) {
		this.active = active;
	}

	public List<SecurityRoleEntity> getRoles() {
		return roles;
	}
	
	public void setRoles(List<SecurityRoleEntity> roles) {
		this.roles = roles;
	}

}
