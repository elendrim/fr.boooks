package org.boooks.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.jcr.RepositoryException;

import org.boooks.db.dao.IBookDAO;
import org.boooks.db.entity.Book;
import org.boooks.jcr.dao.IBookJcrDAO;
import org.boooks.jcr.entity.BookData;
import org.boooks.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Book> getAll() {
		return bookDAO.findAll();
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
		book.setAuthor("TEST USER");
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
	
}
