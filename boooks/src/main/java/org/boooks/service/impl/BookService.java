package org.boooks.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.jcr.RepositoryException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.boooks.db.dao.IBookDAO;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.Book_;
import org.boooks.db.entity.UserEntity;
import org.boooks.db.entity.UserEntity_;
import org.boooks.jcr.dao.IBookJcrDAO;
import org.boooks.jcr.entity.BookData;
import org.boooks.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BookService implements IBookService {
	
	
    @Autowired 
    private IBookDAO bookDAO;
    
    
    @Autowired 
    private IBookJcrDAO bookJcrDAO;
    
    @Override
    @Transactional(readOnly=true)
	public Page<Book> findAll(Pageable pageable) {
		return bookDAO.findAll(pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Book getBookDbById(long id) {
		return bookDAO.getById(id);
	}
	
	@Override
	@Transactional(readOnly=false, rollbackFor={RepositoryException.class, MalformedURLException.class})
	public Book save(Book book, byte[] dataBytes, String mimeType) throws RepositoryException, MalformedURLException {
		book = bookDAO.save(book);
		book = bookJcrDAO.createOrUpdate(book, dataBytes, mimeType);
		return book; 
	}

	@Override
	@Transactional(readOnly=true)
	public Book getBookJcrById(long id) throws RepositoryException, MalformedURLException {
		return bookJcrDAO.getById(id);
	}
	
	@Override
	@Transactional(readOnly=true)
	public BookData getBookData(long id) throws RepositoryException, IOException {
		return bookJcrDAO.getBookData(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Book> findBooks(final String email, Pageable pageable) {
		
		return bookDAO.findAll(new Specification<Book>() {

			@Override
			public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<UserEntity> bookPath = root.get(Book_.user);
				Path<String> emailPath = bookPath.get(UserEntity_.email);
				return cb.equal(emailPath, email);
			}
		}, pageable);
	}
	
}
