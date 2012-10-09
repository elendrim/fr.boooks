package org.boooks.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GENRE")
public class Genre {
	

	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="GENRE_ID")
	private Long id;
	
	@Column(name="LI_GENRE")
	private String liGenre;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLiGenre() {
		return liGenre;
	}

	public void setLiGenre(String liGenre) {
		this.liGenre = liGenre;
	}


}
