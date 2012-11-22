package org.boooks.jcr.dao;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;

import org.boooks.db.common.BooksMimeType;
import org.boooks.db.entity.Book;
import org.boooks.jcr.entity.BookData;
import org.boooks.jcr.entity.FileData;

public interface IBookJcrDAO {  
      
	Book createOrUpdate(Book book, Map<BooksMimeType, BookData> booksMap, FileData cover) throws RepositoryException, MalformedURLException;  
      
    Book getById(long id) throws RepositoryException, MalformedURLException;

	BookData getBookData(long bookId, String mimeType) throws RepositoryException, IOException ;
     
	List<BooksMimeType> getBookMimeType(long bookId) throws RepositoryException, IOException ;
	
	FileData getCoverData(long bookId) throws RepositoryException, IOException;
	
}  