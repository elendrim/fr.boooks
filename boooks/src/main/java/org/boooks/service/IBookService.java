package org.boooks.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.boooks.db.common.BooksMimeType;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.UserEntity;
import org.boooks.jcr.entity.BookData;
import org.boooks.utils.BoooksDataFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

public interface IBookService {

	@Transactional
	Book getBookDbById(long id);
	
	@Transactional
	Book save(Book book, Map<BooksMimeType, BookData> booksMap) throws RepositoryException, MalformedURLException;

	@Transactional
	Book getBookJcrById(long id) throws RepositoryException, MalformedURLException;

	@Transactional
	BookData getBookData(long id, String mimeType) throws RepositoryException, IOException;
	
	@Transactional
	Book createDummyBook(UserEntity user, BoooksDataFactory df) throws Exception;

	@Transactional
	Page<Book> findAllBooks(Pageable pageable);

	@Transactional
	Page<Book> findBooksByEmail(String email, Pageable pageable);

	@Transactional
	Page<Book> findBooksByAuthor(String author, Pageable pageable);

	@Transactional
	Page<Book> findBooksByQuery(String query, Pageable pageable);

	@Transactional
	List<BooksMimeType> getBookMimeType(long id) throws RepositoryException, IOException ;


}
