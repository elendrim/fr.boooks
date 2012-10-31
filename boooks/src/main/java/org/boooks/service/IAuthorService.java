package org.boooks.service;

import org.boooks.db.entity.Author;
import org.springframework.transaction.annotation.Transactional;

public interface IAuthorService {

	@Transactional
	Author getAuthorByName(String name);

	@Transactional
	Author save(Author author);
	
}
