package org.boooks.service.impl;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.boooks.db.dao.IAuthorDAO;
import org.boooks.db.dao.IBookDAO;
import org.boooks.db.dao.imp.SearchBookDAO;
import org.boooks.db.entity.Author;
import org.boooks.db.entity.Author_;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.Book_;
import org.boooks.db.entity.UserEntity;
import org.boooks.db.entity.UserEntity_;
import org.boooks.jcr.dao.IBookJcrDAO;
import org.boooks.jcr.entity.BookData;
import org.boooks.service.IAuthorService;
import org.boooks.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private SearchBookDAO findBookDAO;
    
    @Autowired 
    private IBookJcrDAO bookJcrDAO;
    
    @Autowired
    private IAuthorService authorService;
    
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
	public Page<Book> findBooksByQuery(String q, Pageable pageable) {
		return findBookDAO.findBookByQuery(q, pageable);
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public Page<Book> findBooksByAuthor(String author, Pageable pageable) {
		return findBookDAO.findBookByAuthor(author, pageable);
	}

	@Override
	@Transactional(readOnly=true)
	public Page<Book> findBooksByEmail(final String email, Pageable pageable) {
		
		return findBookDAO.findBookByEmail(email,  pageable);
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
