package org.boooks.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.RepositoryException;

import org.boooks.db.dao.IAuthorDAO;
import org.boooks.db.dao.IBookDAO;
import org.boooks.db.dao.imp.SearchBookDAO;
import org.boooks.db.entity.Author;
import org.boooks.db.entity.Book;
import org.boooks.jcr.dao.IBookJcrDAO;
import org.boooks.jcr.entity.BookData;
import org.boooks.service.IAuthorService;
import org.boooks.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BookService implements IBookService {
	
	
    @Autowired 
    private IBookDAO bookDAO;
    
    @Autowired 
    private SearchBookDAO findBookDAO;
    
    @Autowired 
    private IBookJcrDAO bookJcrDAO;
    
    @Autowired
    private IAuthorService authorService;
    
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
		
		/** save authors **/
		List<Author> authors = new ArrayList<Author>();
		for (Author author : book.getAuthors()) {
			Author authorBase = authorService.getAuthorByName(author.getName());
			if ( authorBase == null ) {
				authorBase = authorService.save(author);
			}
			authors.add(authorBase);
		}
		book.setAuthors(authors);
		
		/** save the book into DB **/
		book = bookDAO.save(book);
		
		/** save the book into JCR **/
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
	public Page<Book> findBooks(final String email, final String author, Pageable pageable) {
		return findBookDAO.findBook(email, author, pageable);
	}

	@Override
	@Transactional
	public Page<Book> findAllBooks(Pageable pageable) {
		
		List<Book> books = bookDAO.findAllBooks(pageable);
		long total = bookDAO.count(); 
		PageImpl<Book> page = new PageImpl<Book>(books, pageable, total);
		return page;
	}
	
}
