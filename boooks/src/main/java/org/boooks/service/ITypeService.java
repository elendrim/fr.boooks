package org.boooks.service;

import java.util.List;

import org.boooks.db.entity.Type;

public interface ITypeService {

	List<Type> getAll();
	
	Type getById(Long id);

}
