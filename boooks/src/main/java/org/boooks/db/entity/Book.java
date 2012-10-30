package org.boooks.db.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="BOOK")
public class Book {
	
	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)  
	@Column(name="BOOK_ID", nullable=false, unique=true)
	private Long id;
	
	@Column(name="TITLE", nullable=false)
	private String title;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="TYPE_ID")
	private Type type;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="GENRE_ID")
	private Genre genre;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", nullable=false)
	private UserEntity user;
	
	@Column(name="RESUME")
	private String resume;
	
	@Column(name="NB_PAGE")
	private int nbPage;

	@Column(name="AUTHOR", nullable=false)
	private String author;

	@Column(name="DESCRIPTION")
	private String description;

	@Column(name="KEYWORDS")
	private String keywords;

	@Column(name="PUBLISH_DATE", nullable=false)
	private Date publishDate;

	public String getAuthor() {
		return author;
	}

	public String getDescription() {
		return description;
	}

	public String getKeywords() {
		return keywords;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public int getNbPage() {
		return nbPage;
	}

	public void setNbPage(int nbPage) {
		this.nbPage = nbPage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthor(String author) {
		this.author = author;		
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	
	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}
