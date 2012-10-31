package org.boooks.db.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(Author.class)
public class Author_ {
	
	public static volatile SingularAttribute<Author, String> name;
	
}
