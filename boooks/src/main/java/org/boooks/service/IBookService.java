package org.boooks.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.jcr.RepositoryException;

import org.boooks.db.entity.Book;
import org.boooks.jcr.entity.BookData;

public interface IBookService {

	List<Book> getAll();

	Book getBookDbById(long id);
	
	Book save(Book book, byte[] dataBytes, String mimeType) throws RepositoryException, MalformedURLException;

	Book getBookJcrById(long id) throws RepositoryException, MalformedURLException;

	BookData getBookData(long id) throws RepositoryException, IOException;

}
