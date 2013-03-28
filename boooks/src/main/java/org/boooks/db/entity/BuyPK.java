package org.boooks.db.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BuyPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="USER_ID", nullable=false)
	private Long userId;
	
	@Column(name="BOOK_ID", nullable=false)
	private Long bookId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	
	
}
