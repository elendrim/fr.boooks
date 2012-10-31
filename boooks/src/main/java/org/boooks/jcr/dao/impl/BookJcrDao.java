package org.boooks.jcr.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;

import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.boooks.db.entity.Author;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.Genre;
import org.boooks.db.entity.Type;
import org.boooks.db.entity.UserEntity;
import org.boooks.jcr.dao.IBookJcrDAO;
import org.boooks.jcr.entity.BookData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * http://wiki.apache.org/jackrabbit/ExamplesPage
 * http://jackrabbit.apache.org/standalone-server.html
 * http://jackrabbit.apache.org/object-content-mapping.html
 * 
 * http://www.ibm.com/developerworks/java/library/j-jcr/
 * 
 * Add content :
 *     http://stackoverflow.com/questions/5155764/store-metadata-into-jackrabbit-repository
 *     
 * Remote Access :
 *     http://wiki.apache.org/jackrabbit/RemoteAccess
 * 
 * @author gregory
 *
 */
@Component
public class BookJcrDao implements IBookJcrDAO {            
	
	private Logger LOGGER = LoggerFactory.getLogger(BookJcrDao.class);
	
    
	@Value("${jcr.url}")
	private  String jcrUrl;
	
	@Override
	public Book createOrUpdate(Book book, byte[] dataBytes, String mimeType) throws RepositoryException, MalformedURLException {
		
		Repository repository = JcrUtils.getRepository(jcrUrl); 
		Session session = repository.login( new SimpleCredentials("admin", "admin".toCharArray()));
		ByteArrayInputStream inputStream = null;
		try { 
    		String user = session.getUserID(); 
    		String name = repository.getDescriptor(Repository.REP_NAME_DESC); 
    		LOGGER.debug( "Logged in as " + user + " to a " + name + " repository.");
    		
    		
    		Node root = session.getRootNode(); 
    		
    		// Store metadata content 
    		Node node = root.addNode("book");
    		node.setProperty("id", book.getId());
    		node.setProperty("userId", book.getUser().getId());
    		node.setProperty("description", book.getDescription());
    		node.setProperty("keywords", book.getKeywords());
    		Calendar calendar = Calendar.getInstance();
    		calendar.setTime(book.getPublishDate());
    		node.setProperty("publishDate", calendar);
    		node.setProperty("title", book.getTitle());
    		node.setProperty("resume", book.getResume());
    		
    		if ( book.getAuthors() != null ) {
    			Node authorsNode = node.addNode("authors");
    			for (Author author :  book.getAuthors() ) {
    				Node authorNode = authorsNode.addNode("author");
    				authorNode.setProperty("name", author.getName());	
				}
    		}
    		
    		if ( book.getGenre() != null ) {
    			Node genre = node.addNode("genre");
    			genre.setProperty("id", book.getGenre().getId() );
    			genre.setProperty("liGenre", book.getGenre().getLiGenre() );
    		}
    		
    		if ( book.getType() != null ) {
    			Node type = node.addNode("type");
    			type.setProperty("id", book.getType().getId() );
    			type.setProperty("liType", book.getType().getLiType() );
    		}
    		
    		// Store files info 
    		Node file = node.addNode("file","nt:file");
    		Node content = file.addNode("jcr:content","nt:resource");
    		inputStream = new ByteArrayInputStream(dataBytes);
    		Binary binary = session.getValueFactory().createBinary(inputStream);
    		content.setProperty("jcr:data",binary);
    		content.setProperty("jcr:mimeType", mimeType);
    		
    		session.save(); 
    		
    	} finally { 
    		IOUtils.closeQuietly(inputStream);
    		session.logout(); 
		}
		return book;
		
	}
	
	@Override
	public BookData getBookData(long bookId) throws RepositoryException, IOException {
		
		Repository repository = JcrUtils.getRepository(jcrUrl); 
		Session session = repository.login( new SimpleCredentials("admin", "admin".toCharArray()));
		
		InputStream inputStream = null;
		BookData bookData = null;
		Binary bin = null;
    	try { 
    		String user = session.getUserID(); 
    		String name = repository.getDescriptor(Repository.REP_NAME_DESC); 
    		LOGGER.debug( "Logged in as " + user + " to a " + name + " repository.");
    		
    		
    		// Retrieve content 
    		String expression = "SELECT * FROM [nt:unstructured] AS book WHERE id = "+ bookId;  
			QueryManager queryMgr = session.getWorkspace().getQueryManager();
			Query query = queryMgr.createQuery(expression,Query.JCR_SQL2);
//			query.bindValue("id", new LongValue(bookId));
			QueryResult result = query.execute();

			Node node = result.getNodes().nextNode();
    		Node file = node.getNode("file");
			Node content = file.getNode("jcr:content");
	        String mimeType = content.getProperty("jcr:mimeType").getString();
	        String filename = file.getIdentifier();
	        bin = content.getProperty("jcr:data").getBinary();
	        inputStream = bin.getStream();
	        byte[] bytes = IOUtils.toByteArray(inputStream);
	        bookData = new BookData();
	        bookData.setBytes(bytes);
	        bookData.setMimeType(mimeType);
	        bookData.setFilename(filename);
	        
    	} finally { 
    		IOUtils.closeQuietly(inputStream);
    		bin.dispose();
    		session.logout(); 
		}
    	
    	return bookData;
		
	}


	@Override
	public Book getById(long bookId) throws RepositoryException, MalformedURLException {
		Repository repository = JcrUtils.getRepository(jcrUrl); 
		Session session = repository.login( new SimpleCredentials("admin", "admin".toCharArray()));
		
		Book book = null;
		
    	try { 
    		String user = session.getUserID(); 
    		String repositoryname = repository.getDescriptor(Repository.REP_NAME_DESC); 
    		LOGGER.debug( "Logged in as " + user + " to a " + repositoryname + " repository.");
    		
    		
    		// Retrieve content 
    		String expression = "SELECT * FROM [nt:unstructured] AS book WHERE id = "+ bookId;  
			QueryManager queryMgr = session.getWorkspace().getQueryManager();
			Query query = queryMgr.createQuery(expression,Query.JCR_SQL2);
//			query.bindValue("id", new LongValue(bookId));
			QueryResult result = query.execute();

			Node node = result.getNodes().nextNode();
    		    		
    		book = new Book();
    		if ( node.hasProperty("id") ) {
    			book.setId(node.getProperty("id").getLong());
    		}
    		if ( node.hasProperty("userId") ) {
    			UserEntity userEntity = new UserEntity();
    			userEntity.setId(node.getProperty("userId").getLong());
    			book.setUser(userEntity);
    		}
    		if ( node.hasProperty("description") ) {
    			book.setDescription(node.getProperty("description").getString());
    		}
    		if ( node.hasProperty("keywords") ) {
    			book.setKeywords(node.getProperty("keywords").getString());
    		}
    		if ( node.hasProperty("publishDate") ) {
    			book.setPublishDate(node.getProperty("publishDate").getDate().getTime());
    		}
    		if ( node.hasProperty("title")) {
    			book.setTitle(node.getProperty("title").getString());
    		}
    		if ( node.hasProperty("resume")) {
    			book.setResume(node.getProperty("resume").getString());
    		}
    		
    		if ( book.getAuthors() != null ) {
    			Node authorsNode = node.addNode("authors");
    			for (Author author :  book.getAuthors() ) {
    				Node authorNode = authorsNode.addNode("author");
    				authorNode.setProperty("name", author.getName());	
				}
    		}
    		if ( node.hasNode("authors") ) {
    			List<Author> authors = new ArrayList<Author>();
    			Node authorsNode = node.getNode("authors");
    			if ( authorsNode.hasNode("author") ) {
    				Node authorNode = authorsNode.getNode("author");
    				if ( authorNode.hasProperty("name") ) {
    					String name = authorNode.getProperty("name").getString();
    					Author author = new Author();
    					author.setName(name);
    					authors.add(author);
    				}
    			}
    		}
    		
    		if ( node.hasNode("genre") ) {
    			Genre genre = new Genre();
    			Node genreNode = node.getNode("genre");
    			if ( genreNode.hasProperty("id")) {
    				genre.setId(genreNode.getProperty("id").getLong());
    			}
    			if ( genreNode.hasProperty("liGenre")) {
    				genre.setLiGenre(genreNode.getProperty("liGenre").getString());
    			}
    			book.setGenre(genre);
    		}
    		if ( node.hasNode("type") ) {
    			Type type = new Type();
    			Node typeNode = node.getNode("type");
    			if ( typeNode.hasProperty("id") ) {
    				type.setId(typeNode.getProperty("id").getLong());
    			}
    			if ( typeNode.hasProperty("liType")) {
    				type.setLiType(typeNode.getProperty("liType").getString());
    			}
    			book.setType(type);	
    		}
    		
    	} finally { 
    		session.logout(); 
		}
		return book;
	} 

  
     
}  