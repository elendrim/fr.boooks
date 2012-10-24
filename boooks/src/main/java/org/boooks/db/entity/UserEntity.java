package org.boooks.db.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="USER")
public class UserEntity {

	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)  
	@Column(name="USER_ID", nullable=false)
	private Long id;
	
	@Column(name="PASSWORD", nullable=false)
	private String password;
	
	@Column(name="ACTIVE", nullable=false)
	private boolean active;
	
	@Column(name="FIRSTNAME", nullable=false)
	private String firstname;
	
	@Column(name="LASTNAME", nullable=false)
	private String lastname;
	
	@Column(name="EMAIL", unique=true, nullable=false)
	private String email;
	
	@Column(name="BIRTHDATE", nullable=false)
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	@Column(name="SEX", nullable=false)
	@Enumerated(EnumType.STRING)
	private SexEnum sex;

	@OneToMany(mappedBy="user", fetch=FetchType.LAZY)
	private List<SecurityRoleEntity> roles;
	
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public SexEnum getSex() {
		return sex;
	}

	public void setSex(SexEnum sex) {
		this.sex = sex;
	}
	
	public List<SecurityRoleEntity> getRoles() {
		return roles;
	}
	
	public void setRoles(List<SecurityRoleEntity> roles) {
		this.roles = roles;
	}

}
