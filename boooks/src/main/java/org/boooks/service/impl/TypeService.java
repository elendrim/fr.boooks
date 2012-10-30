package org.boooks.service.impl;

import java.util.List;

import org.boooks.db.dao.ITypeDAO;
import org.boooks.db.entity.Type;
import org.boooks.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class TypeService implements ITypeService {

    @Autowired 
    private ITypeDAO typeDAO;
    
    
    @Override
    @Transactional(readOnly=true)
	public List<Type> getAll() {
		return typeDAO.findAll(new Sort(Sort.Direction.ASC, "liType"));
	}
    
    @Override
    @Transactional(readOnly=true)
	public Type getById(Long id) {
		return typeDAO.findOne(id);
	}

}
