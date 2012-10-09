package org.boooks.service;

import java.util.List;

import org.boooks.db.entity.Genre;

public interface IGenreService {

	List<Genre> getAll();
	
	Genre getById(Long id);

}
