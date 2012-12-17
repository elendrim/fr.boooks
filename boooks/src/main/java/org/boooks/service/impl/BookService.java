package org.boooks.service.impl;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.boooks.db.common.BooksMimeType;
import org.boooks.db.dao.IBookDAO;
import org.boooks.db.dao.IGenreDAO;
import org.boooks.db.dao.ITypeDAO;
import org.boooks.db.dao.imp.SearchBookDAO;
import org.boooks.db.entity.Author;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.Genre;
import org.boooks.db.entity.Type;
import org.boooks.db.entity.UserEntity;
import org.boooks.jcr.dao.IBookJcrDAO;
import org.boooks.jcr.entity.BookData;
import org.boooks.jcr.entity.FileData;
import org.boooks.service.IAuthorService;
import org.boooks.service.IBookService;
import org.boooks.utils.BoooksDataFactory;
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
    private IGenreDAO genreDAO;
	
	@Autowired
    private ITypeDAO typeDAO;
    
    
    @Autowired
    private IAuthorService authorService;
    
 	@Override
	@Transactional(readOnly=true)
	public Book getBookDbById(long id) {
		return bookDAO.getById(id);
	}
	
	@Override
	@Transactional(readOnly=false, rollbackFor={RepositoryException.class, MalformedURLException.class})
	public Book save(Book book, Map<BooksMimeType, BookData> booksMap, FileData cover) throws RepositoryException, MalformedURLException {
		
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
		book = bookJcrDAO.save(book, booksMap, cover);
		
		return book; 
	}
	
	@Override
	@Transactional(readOnly=false, rollbackFor={RepositoryException.class, MalformedURLException.class})
	public Book update(Book book) throws RepositoryException, MalformedURLException {
		
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
		
		/** refresh **/
		book = bookDAO.getById(book.getId());
		
		/** save the book into JCR **/
		book = bookJcrDAO.update(book);
		
		return book;
	}
	
	@Override
	@Transactional(readOnly=false)
	public void updateCover(long id, FileData coverData) throws RepositoryException, MalformedURLException {
		bookJcrDAO.updateCover(id, coverData);
	}
	

	@Override
	@Transactional(readOnly=true)
	public Book getBookJcrById(long id) throws RepositoryException, MalformedURLException {
		return bookJcrDAO.getById(id);
	}
	
	@Override
	@Transactional(readOnly=true)
	public BookData getBookData(long id, String mimeType) throws RepositoryException, IOException {
		return bookJcrDAO.getBookData(id, mimeType);
	}
	
	@Override
	@Transactional(readOnly=true)
	public FileData getCoverData(long id) throws RepositoryException, IOException {
		return bookJcrDAO.getCoverData(id);
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
	
	
	/*
	 * I didn't know where to put it, maybe in the test part someday
	 * Use DataFactory for creating stuff
	 * 
	 * @see org.boooks.service.IUserService#createDummyUser(java.lang.Boolean)
	 */
	@Override
	@Transactional
	public Book createDummyBook(UserEntity user, BoooksDataFactory df) throws Exception {
		
		//fill the genre and type if it don't exists
		String[] genres = {"Science fiction", "Policier", "Amour", "Fantastique", "Heroic Fantasy"};
		String[] types = {"Nouvelle", "Roman", "Essai", "Thèse", "Manga"};
		
		if(genreDAO.count() == 0){
			for(String genre: genres){
				Genre g = new Genre();
				g.setLiGenre(genre);
				genreDAO.save(g);
			}
		}
		
		if(typeDAO.count() == 0){
			for(String type: types){
				Type t = new Type();
				t.setLiType(type);
				typeDAO.save(t);
			}
		}
		
		//fill the book
		Book book = new Book();
		
		book.setTitle(df.getBoookTitle());
		book.setGenre(df.df.getItem(genreDAO.findAll()));
		book.setType(df.df.getItem(typeDAO.findAll()));
		
		book.setNbPage(df.df.getNumberBetween(50, 500));
		book.setResume(df.df.getRandomText(100, 3000));
		book.setPublishDate(new Date());
		book.setPublishDate(df.df.getDate(new Date(), -1000, 0));
		book.setUser(user);
		
		
		byte[] dataBytes = df.df.getRandomText(df.df.getNumberBetween(200, 1000) * book.getNbPage()).getBytes();
		
		
		Map<BooksMimeType, BookData> bookDataList = new HashMap<BooksMimeType, BookData>();
		BookData bookData = new BookData();
		bookData.setBytes(dataBytes);
		bookData.setFilename("filename");
		bookData.setMimeType(BooksMimeType.TEXT.getMimeType());
		bookData.setTitle(book.getTitle());
		bookDataList.put(BooksMimeType.TEXT, bookData);
		
		book = this.save(book, bookDataList, null);
		
		return book;
	}

	@Override
	public List<BooksMimeType> getBookMimeType(long id) throws RepositoryException, IOException {
		return bookJcrDAO.getBookMimeType(id);
	}


	
}
