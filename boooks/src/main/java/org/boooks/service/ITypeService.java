package org.boooks.service;

import java.util.List;

import org.boooks.db.entity.Type;
import org.springframework.transaction.annotation.Transactional;

public interface ITypeService {

	@Transactional
	List<Type> getAll();
	
	@Transactional
	Type getById(Long id);

}
