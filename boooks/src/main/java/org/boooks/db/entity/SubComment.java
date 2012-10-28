package org.boooks.db.entity;


import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="SUB_COMMENT")
public class SubComment {
	
	@Id  
    @GeneratedValue(strategy = GenerationType.AUTO)  
	@Column(name="SUB_COMMENT_ID")
	private Long id;
	
	@Column(name="TEXT", length = 10000)
	private String text;

	@Column(name="MODIF_DATE")
	private Date modifDate;
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID")
	private UserEntity user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MAIN_COMMENT_ID")
	private UserEntity mainComment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public UserEntity getMainComment() {
		return mainComment;
	}

	public void setMainComment(UserEntity mainComment) {
		this.mainComment = mainComment;
	}

	
	
}
