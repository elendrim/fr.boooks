package org.boooks.db.entity;


import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="MAIN_COMMENT")
public class MainComment {
	
	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)  
	@Column(name="MAIN_COMMENT_ID")
	private Long id;
	
	@Column(name="TITLE", length=1000)
	private String title;
	
	@Column(name="TEXT", length = 10000)
	private String text;

	@Column(name="MODIF_DATE")
	private Date modifDate;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BOOK_ID")
	private Book book;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	private UserEntity user;

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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getModifDate() {
		return modifDate;
	}

	public void setModifDate(Date modifDate) {
		this.modifDate = modifDate;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
	
	
	
}
