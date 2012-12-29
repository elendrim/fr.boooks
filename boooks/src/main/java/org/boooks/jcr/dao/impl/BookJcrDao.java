package org.boooks.jcr.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.jcr.Binary;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.query.Query;
import javax.jcr.query.QueryManager;
import javax.jcr.query.QueryResult;
import javax.jcr.query.RowIterator;

import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.commons.JcrUtils;
import org.boooks.db.common.BooksMimeType;
import org.boooks.db.entity.Author;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.Genre;
import org.boooks.db.entity.Type;
import org.boooks.db.entity.UserEntity;
import org.boooks.jcr.dao.IBookJcrDAO;
import org.boooks.jcr.entity.BookData;
import org.boooks.jcr.entity.FileData;
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
 * Query JCR_SQL2 :
 *     http://svn.apache.org/viewvc/jackrabbit/trunk/jackrabbit-spi-commons/src/test/resources/org/apache/jackrabbit/spi/commons/query/sql2/test.sql2.txt?view=markup
 * 
 * @author gregory
 *
 */
@Component
public class BookJcrDao implements IBookJcrDAO {            
	
	private Logger LOGGER = LoggerFactory.getLogger(BookJcrDao.class);
    
	@Value("${jcr.url}")
	private  String jcrUrl;

	
	static private String sanitize(String s){
		return s.replace(":", "_");
	}
	
	static private String sanitize(BookData bd){
		return sanitize(bd.getFilename());
	}
	
	static private String sanitize(FileData fd){
		return sanitize(fd.getFilename());
	}
	
	
	@Override
	public Book save(Book book, Map<BooksMimeType, BookData> booksMap, FileData cover) throws RepositoryException, MalformedURLException {
		
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
    		for (Entry<BooksMimeType, BookData> entrySet : booksMap.entrySet()) {
    			Node file = node.addNode(sanitize(entrySet.getValue()),"nt:file");
        		Node content = file.addNode("jcr:content","nt:resource");
        		inputStream = new ByteArrayInputStream(entrySet.getValue().getBytes());
        		Binary binary = session.getValueFactory().createBinary(inputStream);
        		content.setProperty("jcr:data",binary);
        		content.setProperty("jcr:mimeType", entrySet.getKey().getMimeType());
        		content.setProperty("jcr:lastModified", Calendar.getInstance());
			}
    		
    		if ( cover != null ) {
    			Node coverNode = node.addNode("cover");
    			Node file = coverNode.addNode(sanitize(cover),"nt:file");
        		Node content = file.addNode("jcr:content","nt:resource");
        		inputStream = new ByteArrayInputStream(cover.getBytes());
        		Binary binary = session.getValueFactory().createBinary(inputStream);
        		content.setProperty("jcr:data",binary);
        		content.setProperty("jcr:mimeType", cover.getMimeType());
        		content.setProperty("jcr:lastModified", Calendar.getInstance());
    		}
    		
    		
    		
    		session.save(); 
    		
		} catch ( RepositoryException e) {
    		LOGGER.error("Error getting book data", e);
    		throw e;
    	} finally { 
    		IOUtils.closeQuietly(inputStream);
    		session.logout(); 
		}
		return book;
		
	}
	

	@Override
	public Book update(Book book) throws RepositoryException, MalformedURLException {

		Repository repository = JcrUtils.getRepository(jcrUrl); 
		Session session = repository.login( new SimpleCredentials("admin", "admin".toCharArray()));
		
		try { 
    		String user = session.getUserID(); 
    		String repositoryname = repository.getDescriptor(Repository.REP_NAME_DESC); 
    		LOGGER.debug( "Logged in as " + user + " to a " + repositoryname + " repository.");
    		
    		
    		// Retrieve content 
    		StringBuilder expression = new StringBuilder();
    		expression.append("SELECT * FROM [nt:unstructured] AS book ");
    		expression.append("WHERE id = "+ book.getId() +" " );
    		
			QueryManager queryMgr = session.getWorkspace().getQueryManager();
			Query query = queryMgr.createQuery(expression.toString(),Query.JCR_SQL2);
//			query.bindValue("id", new LongValue(bookId));
			QueryResult result = query.execute();
			RowIterator rowIterator = result.getRows();

			if (rowIterator.hasNext() ) {
				Node node = rowIterator.nextRow().getNode();
	    		    		
				// Store metadata content 
	    		node.setProperty("description", book.getDescription());
	    		node.setProperty("keywords", book.getKeywords());
	    		node.setProperty("resume", book.getResume());
	    		node.setProperty("title", book.getTitle());
	    		
	    		
	    		
//	    		
//	    		
//	    		Node authorsNode = JcrUtils.getOrAddNode(node, "authors");
//	    		
//	    		if ( book.getAuthors() != null &&  !book.getAuthors().isEmpty() ) {
//	    			while ( authorsNode.hasNode("author") ) {
//	    				authorsNode.getNode("author").remove();
//	    			}
//	    			for (Author author :  book.getAuthors() ) {
//	    				Node authorNode = authorsNode.addNode("author");
//	    				authorNode.setProperty("name", author.getName());	
//					}
//	    		} else {
//	    			while ( authorsNode.hasNode("author") ) {
//	    				authorsNode.getNode("author").remove();
//	    			}
//	    		}
//	    		
//	    		if ( book.getGenre() != null ) {
//	    			Node genre = JcrUtils.getOrAddNode(node, "genre");
//	    			genre.setProperty("id", book.getGenre().getId() );
//	    			genre.setProperty("liGenre", book.getGenre().getLiGenre() );
//	    		} else {
//	    			if ( node.hasNode("genre")) {
//	    				node.getNode("genre").remove();
//	    			}
//	    		}
//	    		
//	    		if ( book.getType() != null ) {
//	    			Node type = JcrUtils.getOrAddNode(node,"type");
//	    			type.setProperty("id", book.getType().getId() );
//	    			type.setProperty("liType", book.getType().getLiType() );
//	    		} else {
//	    			if ( node.hasNode("type") ) {
//	    				node.getNode("type").remove();
//	    			}
//	    		}
	    		
	    		session.save(); 
			}
    	} catch ( RepositoryException e) {
    		LOGGER.error("Error getting book data", e);
    		throw e;
    	} finally { 
    		session.logout(); 
		}
		return book;
	} 
	
	


	@Override
	public void updateCover(long bookId, FileData cover) throws RepositoryException, MalformedURLException {

		Repository repository = JcrUtils.getRepository(jcrUrl); 
		Session session = repository.login( new SimpleCredentials("admin", "admin".toCharArray()));
		ByteArrayInputStream inputStream = null;
		try { 
    		String user = session.getUserID(); 
    		String name = repository.getDescriptor(Repository.REP_NAME_DESC); 
    		LOGGER.debug( "Logged in as " + user + " to a " + name + " repository.");
    		
    		// Retrieve content 
    		StringBuilder expression = new StringBuilder();
    		
    		expression.append("select * from [nt:unstructured] as book ");
    		expression.append("where book.id = "+ bookId + " ");
    		expression.append("and ischildnode (book, [/]) ");
    		expression.append("and name(book) = 'book' ");
    		
			QueryManager queryMgr = session.getWorkspace().getQueryManager();
			Query query = queryMgr.createQuery(expression.toString(),Query.JCR_SQL2);
//			query.bindValue("id", new LongValue(bookId));
			QueryResult result = query.execute(); 
			RowIterator rowIterator = result.getRows();

			if (rowIterator.hasNext() ) {
				
				Node node = rowIterator.nextRow().getNode("book");
				
				if ( node.hasNode("cover")) {
    				node.getNode("cover").remove();
    			}
		        
				if ( cover != null ) {
					Node coverNode = JcrUtils.getOrAddNode(node, "cover");
					
	    			Node file = JcrUtils.getOrAddNode(coverNode, sanitize(cover), "nt:file");
	        		Node content = JcrUtils.getOrAddNode(file,"jcr:content","nt:resource");
	        		inputStream = new ByteArrayInputStream(cover.getBytes());
	        		Binary binary = session.getValueFactory().createBinary(inputStream);
	        		content.setProperty("jcr:data",binary);
	        		content.setProperty("jcr:mimeType", cover.getMimeType());
	        		content.setProperty("jcr:lastModified", Calendar.getInstance());
	    		}
	    		session.save(); 
			}
		} catch ( RepositoryException e) {
    		LOGGER.error("Error getting book data", e);
    		throw e;
    	} finally { 
    		IOUtils.closeQuietly(inputStream);
    		session.logout(); 
		}
		
	} 
	
	
	
	
	@Override
	public BookData getBookData(long bookId, String mimeType) throws RepositoryException, IOException {
		
		Repository repository = JcrUtils.getRepository(jcrUrl); 
		Session session = repository.login( new SimpleCredentials("admin", "admin".toCharArray()));
		
		InputStream inputStream = null;
		BookData bookData = null;
		Binary bin = null;
    	try { 
    		String user = session.getUserID(); 
    		String name = repository.getDescriptor(Repository.REP_NAME_DESC); 
    		LOGGER.debug( "Logged in as " + user + " to a " + name + " repository.");
    		
    		StringBuilder expression = new StringBuilder();
    		expression.append("select file.* from [nt:file] as file ");
    		expression.append("inner join [nt:unstructured] as book on ischildnode(file, book) ");
    		expression.append("inner join [nt:resource] as content on ischildnode(content, file) ");
    		expression.append("where  book.id = "+ bookId + " ");
    		expression.append("and content.[jcr:mimeType] = '"+ mimeType +"' ");
    		expression.append("and ischildnode (book, [/]) ");
    		expression.append("and name(book) = 'book' ");
    		
    		QueryManager queryMgr = session.getWorkspace().getQueryManager();
			Query query = queryMgr.createQuery(expression.toString(),Query.JCR_SQL2);
			QueryResult result = query.execute();
			RowIterator rowIterator = result.getRows();
			if ( rowIterator.hasNext() ) {
				Node file = rowIterator.nextRow().getNode("file");
				
				
				Node content = file.getNode("jcr:content");
				String contentMimeType = content.getProperty("jcr:mimeType").getString();
				bin = content.getProperty("jcr:data").getBinary();
		        inputStream = bin.getStream();
		        byte[] bytes = IOUtils.toByteArray(inputStream);
		        bookData = new BookData();
		        bookData.setFilename(file.getName());
		        bookData.setBytes(bytes);
		        bookData.setMimeType(contentMimeType);
		        
		        Node node = file.getParent();
				String title = null;
				if ( node.hasProperty("title") ) {
					title = node.getProperty("title").getString();
				}
		        bookData.setTitle(title);
		        
			}
    	} catch ( RepositoryException e) {
    		LOGGER.error("Error during download file", e);
    		throw e;
    	} finally { 
    		IOUtils.closeQuietly(inputStream);
    		if ( bin != null ) {
    			bin.dispose();
    		}
    		session.logout(); 
		}
    	
    	return bookData;
	}
	
	
	@Override
	public FileData getCoverData(long bookId) throws RepositoryException, IOException {
		
		Repository repository = JcrUtils.getRepository(jcrUrl); 
		Session session = repository.login( new SimpleCredentials("admin", "admin".toCharArray()));
		
		InputStream inputStream = null;
		FileData fileData = null;
		Binary bin = null;
    	try { 
    		String user = session.getUserID(); 
    		String name = repository.getDescriptor(Repository.REP_NAME_DESC); 
    		LOGGER.debug( "Logged in as " + user + " to a " + name + " repository.");
    		
    		StringBuilder expression = new StringBuilder();
    		expression.append("select file.* from [nt:file] as file ");
    		expression.append("inner join [nt:unstructured] as cover on ischildnode(file, cover) ");
    		expression.append("inner join [nt:unstructured] as book on ischildnode(cover, book) ");
    		expression.append("inner join [nt:resource] as content on ischildnode(content, file) ");
    		expression.append("where  book.id = "+ bookId + " ");
    		expression.append("and ischildnode (book, [/]) ");
    		expression.append("and name(cover) = 'cover' ");
    		expression.append("and name(book) = 'book' ");
    		
    		QueryManager queryMgr = session.getWorkspace().getQueryManager();
			Query query = queryMgr.createQuery(expression.toString(),Query.JCR_SQL2);
			QueryResult result = query.execute();
			RowIterator rowIterator = result.getRows();
			if ( rowIterator.hasNext() ) {
				Node file = rowIterator.nextRow().getNode("file");
				
				Node content = file.getNode("jcr:content");
				String contentMimeType = content.getProperty("jcr:mimeType").getString();
				bin = content.getProperty("jcr:data").getBinary();
		        inputStream = bin.getStream();
		        byte[] bytes = IOUtils.toByteArray(inputStream);
		        fileData = new BookData();
		        fileData.setFilename(file.getName());
		        fileData.setBytes(bytes);
		        fileData.setMimeType(contentMimeType);
		        
			}
    	} catch ( RepositoryException e) {
    		LOGGER.error("Error during download file", e);
    		throw e;
    	} finally { 
    		IOUtils.closeQuietly(inputStream);
    		if ( bin != null ) {
    			bin.dispose();
    		}
    		session.logout(); 
		}
    	
    	return fileData;
	}
	
	
	@Override
	public List<BooksMimeType> getBookMimeType(long bookId) throws RepositoryException, IOException {
		
		Repository repository = JcrUtils.getRepository(jcrUrl); 
		Session session = repository.login( new SimpleCredentials("admin", "admin".toCharArray()));
		
		List<BooksMimeType> booksMimeTypeList = new ArrayList<BooksMimeType>();
		
		try { 
    		String user = session.getUserID(); 
    		String name = repository.getDescriptor(Repository.REP_NAME_DESC); 
    		LOGGER.debug( "Logged in as " + user + " to a " + name + " repository.");
    		
    		StringBuilder expression = new StringBuilder();
    		expression.append("select file.* from [nt:file] as file ");
    		expression.append("inner join [nt:unstructured] as book on ischildnode(file, book) ");
    		expression.append("inner join [nt:resource] as content on ischildnode(content, file) ");
    		expression.append("where  book.id = "+ bookId + " ");
    		
    		QueryManager queryMgr = session.getWorkspace().getQueryManager();
			Query query = queryMgr.createQuery(expression.toString(),Query.JCR_SQL2);
			QueryResult result = query.execute();
			RowIterator rowIterator = result.getRows();
			while ( rowIterator.hasNext() ) {
				Node file = rowIterator.nextRow().getNode("file");
				
				Node content = file.getNode("jcr:content");
				String contentMimeType = content.getProperty("jcr:mimeType").getString();
				BooksMimeType booksMimeType = BooksMimeType.searchByMimeType(contentMimeType);
				booksMimeTypeList.add(booksMimeType);
			}
		} catch ( RepositoryException e) {
    		LOGGER.error("Error during download file", e);
    		throw e;
    	} finally { 
    		session.logout(); 
		}
    	
    	return booksMimeTypeList;
		
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
			RowIterator rowIterator = result.getRows();
			
			if (rowIterator.hasNext() ) {
				Node node = rowIterator.nextRow().getNode();
	    		    		
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
			}
    	} catch ( RepositoryException e) {
    		LOGGER.error("Error getting book data", e);
    		throw e;
    	} finally { 
    		session.logout(); 
		}
		return book;
	}


  
     
}  