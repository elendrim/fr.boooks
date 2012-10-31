package org.boooks.db.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="AUTHOR")
public class Author {

	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)  
	@Column(name="AUTHOR_ID", nullable=false, unique=true)
	private Long id;
	
	@Column(name="NAME", unique=true, nullable=false)
	private String name;
	
	@ManyToMany(mappedBy="authors")
	public List<Book> books;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}
}
