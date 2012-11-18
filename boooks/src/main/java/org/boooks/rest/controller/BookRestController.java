package org.boooks.rest.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.UUID;

import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.boooks.db.common.BooksMimeType;
import org.boooks.jcr.entity.BookData;
import org.boooks.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("book")
public class BookRestController {

	@Autowired
    private IBookService bookService;
	
	@RequestMapping(value = "/file/{id}/{format}", method = RequestMethod.GET)
	public void getFile(@PathVariable("id") Long id, @PathVariable("format") String format, HttpServletResponse response) throws RepositoryException, IOException, MimeTypeException {
	    // get your file as InputStream
		
		BooksMimeType booksMimeType = BooksMimeType.valueOf(format);
		BookData bookData = bookService.getBookData(id, booksMimeType.getMimeType() );
		
		String filename = null;
		if ( bookData.getTitle() != null ) {
			filename = Normalizer.normalize(bookData.getTitle(), Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		} else if ( bookData.getFilename() != null ) { 
			filename = Normalizer.normalize(bookData.getFilename(), Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
		} else {
			filename = UUID.randomUUID().toString();
		}
		
		TikaConfig config = TikaConfig.getDefaultConfig();
		MimeType mimeType = config.getMimeRepository().forName(bookData.getMimeType());
		String extension = mimeType.getExtension();
		filename = filename + extension;
		
		response.setContentType(bookData.getMimeType());      
	    response.setHeader("Content-Disposition", "attachment; filename=\""+ filename + "\"");
		
	    InputStream is = new ByteArrayInputStream(bookData.getBytes());
	    // copy it to response's OutputStream
	    IOUtils.copy(is, response.getOutputStream());
	    response.flushBuffer();
	    
	}


}