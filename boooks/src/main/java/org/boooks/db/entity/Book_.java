package org.boooks.db.entity;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(Book.class)
public class Book_ {
	
	public static volatile SingularAttribute<Book, UserEntity> user;
	public static volatile SingularAttribute<Book, String> title;
	public static volatile ListAttribute<Book, Author> authors;
	
	
	
}
