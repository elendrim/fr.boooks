package org.boooks.db.dao;

import org.boooks.db.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITypeDAO extends JpaRepository<Type, Long> { }