package org.boooks.db.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="SECURITY_ROLE")
public class SecurityRoleEntity {
	
	@EmbeddedId
	private SecurityRoleEntityPk id;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", insertable=false, updatable=false)
	private UserEntity user;
	
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	public SecurityRoleEntityPk getId() {
		return id;
	}

	public void setId(SecurityRoleEntityPk id) {
		this.id = id;
	}
	
}
