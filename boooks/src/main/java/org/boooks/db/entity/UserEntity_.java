package org.boooks.db.entity;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;


@StaticMetamodel(UserEntity.class)
public class UserEntity_ {
    public static volatile SingularAttribute<UserEntity, String> email;
}