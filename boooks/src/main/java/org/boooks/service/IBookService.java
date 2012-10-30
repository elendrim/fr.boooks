package org.boooks.service;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.jcr.RepositoryException;

import org.boooks.db.entity.Book;
import org.boooks.jcr.entity.BookData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface IBookService {

	@Transactional
	Page<Book> findAll(Pageable pageable);
	
	@Transactional
	Page<Book> findBooks(String email, Pageable pageable) ;

	@Transactional
	Book getBookDbById(long id);
	
	@Transactional
	Book save(Book book, byte[] dataBytes, String mimeType) throws RepositoryException, MalformedURLException;

	@Transactional
	Book getBookJcrById(long id) throws RepositoryException, MalformedURLException;

	@Transactional
	BookData getBookData(long id) throws RepositoryException, IOException;

}
