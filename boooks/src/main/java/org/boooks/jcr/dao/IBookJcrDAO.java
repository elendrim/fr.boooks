package org.boooks.jcr.dao;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.jcr.RepositoryException;

import org.boooks.db.entity.Book;
import org.boooks.jcr.entity.BookData;

public interface IBookJcrDAO {  
      
	Book createOrUpdate(Book book, byte[] dataBytes, String mimeType) throws RepositoryException, MalformedURLException;  
      
    Book getById(long id) throws RepositoryException, MalformedURLException;

	BookData getBookData(long id) throws RepositoryException, IOException;
     
}  