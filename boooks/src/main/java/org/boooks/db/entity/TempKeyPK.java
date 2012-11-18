package org.boooks.db.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TempKeyPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="EMAIL", nullable=false)
	private String email;
	
	@Column(name="TEMP_KEY", nullable=false)
	private String tempkey;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTempkey() {
		return tempkey;
	}

	public void setTempkey(String tempkey) {
		this.tempkey = tempkey;
	}
	
}
