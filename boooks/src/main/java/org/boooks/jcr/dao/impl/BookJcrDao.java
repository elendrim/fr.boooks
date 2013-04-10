package org.boooks.jcr.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

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
import javax.jcr.version.VersionManager;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
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

	
	private static String sanitize(String s){
		s = s.replace(":", "-");
		s = s.replace(" ", "-");
		s = s.replace("_", "-");
		return s;
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
//    		UUID uuid = UUID.randomUUID();
//    		
    		// Store metadata content 
    		Node node = root.addNode(sanitize(book.getTitle()));
    		node.addMixin("mix:versionable");
    		node.setProperty("id", book.getId());
    		node.setProperty("userId", book.getUser().getId());
    		node.setProperty("description", book.getDescription());
    		node.setProperty("keywords", book.getKeywords());
    		Calendar calendar = Calendar.getInstance();
    		calendar.setTime(book.getPublishDate());
    		node.setProperty("publishDate", calendar);
    		node.setProperty("title", book.getTitle());
    		node.setProperty("resume", book.getResume());
    		node.setProperty("authors", StringUtils.join(book.getAuthors(), ','));
    		if ( book.getGenre() != null ) {
    			node.setProperty("genre", book.getGenre().getLiGenre() );
    		}
    		
    		if ( book.getType() != null ) {
    			node.setProperty("type", book.getType().getLiType() );
    		}
    		
    		// Store files info 
    		for (Entry<BooksMimeType, BookData> entrySet : booksMap.entrySet()) {
    			Node file = node.addNode(sanitize(entrySet.getValue().getFilename()),"nt:file");
        		Node content = file.addNode("jcr:content","nt:resource");
        		inputStream = new ByteArrayInputStream(entrySet.getValue().getBytes());
        		Binary binary = session.getValueFactory().createBinary(inputStream);
        		content.setProperty("jcr:data",binary);
        		content.setProperty("jcr:mimeType", entrySet.getKey().getMimeType());
        		content.setProperty("jcr:lastModified", Calendar.getInstance());
			}
    		
    		if ( cover != null ) {
    			Node coverNode = node.addNode("cover");
    			Node file = coverNode.addNode(sanitize(cover.getFilename()),"nt:file");
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
				    		
				VersionManager vm = session.getWorkspace().getVersionManager();
				vm.checkout(node.getPath());
	
				if (! node.getName().equals( sanitize(book.getTitle()) )) {
					session.move(node.getPath(), "/" + sanitize(book.getTitle()));
				}
				
				node.addMixin("mix:versionable");
	    		node.setProperty("id", book.getId());
	    		node.setProperty("userId", book.getUser().getId());
	    		node.setProperty("description", book.getDescription());
	    		node.setProperty("keywords", book.getKeywords());
	    		Calendar calendar = Calendar.getInstance();
	    		calendar.setTime(book.getPublishDate());
	    		node.setProperty("publishDate", calendar);
	    		node.setProperty("title", book.getTitle());
	    		node.setProperty("resume", book.getResume());
	    		node.setProperty("authors", StringUtils.join(book.getAuthors(), ','));
	    		if ( book.getGenre() != null ) {
	    			node.setProperty("genre", book.getGenre().getLiGenre() );
	    		}
	    		
	    		if ( book.getType() != null ) {
	    			node.setProperty("type", book.getType().getLiType() );
	    		}
	    		
	    		session.save();
	    		vm.checkin(node.getPath());
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
    		
			QueryManager queryMgr = session.getWorkspace().getQueryManager();
			Query query = queryMgr.createQuery(expression.toString(),Query.JCR_SQL2);
//			query.bindValue("id", new LongValue(bookId));
			QueryResult result = query.execute(); 
			RowIterator rowIterator = result.getRows();

			if (rowIterator.hasNext() ) {
				
				Node node = rowIterator.nextRow().getNode("book");
				
				VersionManager vm = session.getWorkspace().getVersionManager();
				vm.checkout(node.getPath());
				
				if ( node.hasNode("cover")) {
    				node.getNode("cover").remove();
    			}
		        
				if ( cover != null ) {
					Node coverNode = JcrUtils.getOrAddNode(node, "cover");
					
	    			Node file = JcrUtils.getOrAddNode(coverNode, sanitize(cover.getFilename()), "nt:file");
	        		Node content = JcrUtils.getOrAddNode(file,"jcr:content","nt:resource");
	        		inputStream = new ByteArrayInputStream(cover.getBytes());
	        		Binary binary = session.getValueFactory().createBinary(inputStream);
	        		content.setProperty("jcr:data",binary);
	        		content.setProperty("jcr:mimeType", cover.getMimeType());
	        		content.setProperty("jcr:lastModified", Calendar.getInstance());
	    		}
	    		session.save(); 
	    		vm.checkin(node.getPath());
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
	    		
	    		if ( node.hasProperty("authors") ) {
	    			String[] authors =StringUtils.split(node.getProperty("authors").getString(), ",");
	    			List<Author> authorList = new ArrayList<Author>();
	    			
	    			for (String authorname : authors) {
	    				Author author = new Author();
    					author.setName(authorname);
    					authorList.add(author);
					}
	    			
	    			book.setAuthors(authorList);
	    		}
	    		
	    		if ( node.hasProperty("genre") ) {
	    			Genre genre = new Genre();
	    			genre.setLiGenre(node.getProperty("genre").getString());
	    			book.setGenre(genre);
	    		}
	    		if ( node.hasProperty("type") ) {
	    			Type type = new Type();
	    			type.setLiType(node.getProperty("type").getString());
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