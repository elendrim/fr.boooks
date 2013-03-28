package org.boooks.db.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="BUY")
public class Buy {
	
	// id = user + book
	@EmbeddedId
	private BuyPK buyPk;
	
	//user
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID", insertable=false, updatable=false)
	private UserEntity userEntity;
	
	//book
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="BOOK_ID", insertable=false, updatable=false)
	private Book book;
	
	//date d'achat
	@Column(name="DT_CRE", nullable=false)
	private Date dtCre;
	
	// prix
	@Column(name="PRICE", nullable=false, precision=9, scale=2)
	private BigDecimal price;

	public BuyPK getBuyPk() {
		return buyPk;
	}

	public void setBuyPk(BuyPK buyPk) {
		this.buyPk = buyPk;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}

	public void setUserEntity(UserEntity userEntity) {
		this.userEntity = userEntity;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getDtCre() {
		return dtCre;
	}

	public void setDtCre(Date dtCre) {
		this.dtCre = dtCre;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

}
