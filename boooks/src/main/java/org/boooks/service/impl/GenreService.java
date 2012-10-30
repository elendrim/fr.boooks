package org.boooks.service.impl;

import java.util.List;

import org.boooks.db.dao.IGenreDAO;
import org.boooks.db.entity.Genre;
import org.boooks.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class GenreService implements IGenreService {

    @Autowired 
    private IGenreDAO genreDAO;
    
    
    @Override
    @Transactional(readOnly=true)
	public List<Genre> getAll() {
		return genreDAO.findAll(new Sort(Sort.Direction.ASC, "liGenre"));
	}


	@Override
	@Transactional(readOnly=true)
	public Genre getById(Long id) {
		return genreDAO.findOne(id);
	}

}
