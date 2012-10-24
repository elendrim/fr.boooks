package org.boooks.db.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TEMP_KEY")
public class TempKey {

	@EmbeddedId
	private TempKeyPK tempKeyPK;

	public TempKeyPK getTempKeyPK() {
		return tempKeyPK;
	}

	public void setTempKeyPK(TempKeyPK tempKeyPK) {
		this.tempKeyPK = tempKeyPK;
	}
	
}

