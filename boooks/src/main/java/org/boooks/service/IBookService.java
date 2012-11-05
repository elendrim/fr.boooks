package org.boooks.service;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.jcr.RepositoryException;

import org.boooks.utils.BoooksDataFactory;
import org.boooks.db.entity.Book;
import org.boooks.jcr.entity.BookData;
import org.boooks.db.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.fluttercode.datafactory.impl.DataFactory;

public interface IBookService {

	@Transactional
	Book getBookDbById(long id);
	
	@Transactional
	Book save(Book book, byte[] dataBytes, String mimeType) throws RepositoryException, MalformedURLException;

	@Transactional
	Book getBookJcrById(long id) throws RepositoryException, MalformedURLException;

	@Transactional
	BookData getBookData(long id) throws RepositoryException, IOException;
	
	@Transactional
	Book createDummyBook(UserEntity user, BoooksDataFactory df)
			throws Exception;

	@Transactional
	Page<Book> findAllBooks(Pageable pageable);

	@Transactional
	Page<Book> findBooksByEmail(String email, Pageable pageable);

	@Transactional
	Page<Book> findBooksByAuthor(String author, Pageable pageable);

	@Transactional
	Page<Book> findBooksByQuery(String query, Pageable pageable);

}
