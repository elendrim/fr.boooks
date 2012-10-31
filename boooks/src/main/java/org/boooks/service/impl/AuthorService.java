package org.boooks.service.impl;

import org.boooks.db.dao.IAuthorDAO;
import org.boooks.db.entity.Author;
import org.boooks.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthorService implements IAuthorService {

	@Autowired
	private IAuthorDAO authorDAO;
	
	@Override
	@Transactional(readOnly=true)
	public Author getAuthorByName(String name) {
		return authorDAO.getByName(name);
	}

	@Override
	@Transactional(readOnly=false)
	public Author save(Author author) {
		return authorDAO.save(author);
	}

}
