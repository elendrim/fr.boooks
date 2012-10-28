package org.boooks.service.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.books.utils.BoooksDataFactory;
import org.boooks.db.dao.*;
import org.boooks.db.entity.*;
import org.boooks.jcr.dao.IBookJcrDAO;
import org.boooks.jcr.entity.BookData;
import org.boooks.service.IBookService;
import org.boooks.service.IGenreService;
import org.boooks.service.ITypeService;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class BookService implements IBookService {

    @Autowired 
    private IBookDAO bookDAO;
    
    @Autowired 
    private IBookJcrDAO bookJcrDAO;
    
	@Autowired
    private IGenreDAO genreDAO;
	
	@Autowired
    private ITypeDAO typeDAO;
    
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
		
		
		byte[] dataBytes = df.df.getRandomText(df.df.getNumberBetween(200, 1000) * book.getNbPage()).getBytes();
		String contentType = "text/plain";
		
		book = this.save(book, dataBytes, contentType);
		
		return book;
	}

	
}
