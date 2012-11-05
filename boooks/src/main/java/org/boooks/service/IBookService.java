package org.boooks.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.jcr.RepositoryException;

import org.boooks.utils.BoooksDataFactory;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.UserEntity;
import org.boooks.jcr.entity.BookData;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.transaction.annotation.Transactional;

public interface IBookService {

	@Transactional
	List<Book> getAll();

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

}
