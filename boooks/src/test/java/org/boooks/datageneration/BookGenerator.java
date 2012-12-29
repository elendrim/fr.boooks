package org.boooks.datageneration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.boooks.db.common.BooksMimeType;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.Genre;
import org.boooks.db.entity.Type;
import org.boooks.db.entity.UserEntity;
import org.boooks.jcr.entity.BookData;
import org.boooks.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookGenerator {

	@Autowired
	private GenreGenerator genreGen;
	
	@Autowired
	private TypeGenerator typeGen;

	@Autowired
	private UserEntityGenerator userGen;
	
	@Autowired
	private IBookService bookService;
	
	
	public Book create(DataGenerator dg) throws Exception{
		return this.create(dg, null, null, null);
	}
	
	
	
	public Book create(DataGenerator dg, UserEntity user, Genre genre, Type type) throws Exception{
		
		Book b = BookGenerator.fill(new Book(), dg);
		
		b.setUser(user == null ? userGen.create(dg) : user);
		b.setGenre(genre == null ? genreGen.create(dg) : genre);
		b.setType(type == null ? typeGen.create(dg) : type);

		bookService.save(b, fillBookDataList(b, dg), null);
		
		return b;
	}
	
	
	
	
	static public Book fill(Book book, DataGenerator dg){
		
		book.setTitle(dg.getBoookTitle());
		book.setNbPage(dg.getNumberBetween(50, 1000));
		book.setResume(dg.getRandomText(100, 3000));
		
		book.setPublishDate(dg.getDate(new Date(), -1000, 0));
		
		return book;
	}
	
	
	
	static public Map<BooksMimeType, BookData> fillBookDataList(Book book, DataGenerator dg){

		byte[] dataBytes = dg.getRandomText(dg.getNumberBetween(200, 1000) * book.getNbPage()).getBytes();
		Map<BooksMimeType, BookData> bookDataList = new HashMap<BooksMimeType, BookData>();
		
		//creating the txt form
		BookData bookData = new BookData();
		bookData.setBytes(dataBytes);
		bookData.setFilename(book.getTitle().replace(" ", "_") + ".txt");
		bookData.setTitle(book.getTitle());
		bookData.setMimeType(BooksMimeType.TEXT.getMimeType());
		
		bookDataList.put(BooksMimeType.TEXT, bookData);
		
		
		return bookDataList;
	}
	
	
	
	
	
}
