package org.boooks.service;

import java.util.List;

import org.boooks.db.entity.Genre;
import org.springframework.transaction.annotation.Transactional;

public interface IGenreService {

	@Transactional
	List<Genre> getAll();
	
	@Transactional
	Genre getById(Long id);

}
