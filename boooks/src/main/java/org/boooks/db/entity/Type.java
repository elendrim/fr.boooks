package org.boooks.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TYPE")
public class Type {
	
	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="TYPE_ID")
	private Long id;
	
	@Column(name="LI_TYPE")
	private String liType;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLiType() {
		return liType;
	}

	public void setLiType(String liType) {
		this.liType = liType;
	}
	
	

}
