package org.boooks.db.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SecurityRoleEntityPk implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="ROLE_NAME", nullable = false)
	private String roleName;
    
    @Column(name="USER_ID", nullable = false)
    private Long userId;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


}
