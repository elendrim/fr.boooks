package org.boooks.rest.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
	
	@RequestMapping(value = "/file/{id}", method = RequestMethod.GET)
	public void getFile(@PathVariable("id") Long id, HttpServletResponse response) throws RepositoryException, IOException {
	    // get your file as InputStream
		
		BookData bookData = bookService.getBookData(id);
		
		response.setContentType(bookData.getMimeType());      
	    response.setHeader("Content-Disposition", "attachment; filename="+ bookData.getFilename());
		
	    InputStream is = new ByteArrayInputStream(bookData.getBytes());
	    // copy it to response's OutputStream
	    IOUtils.copy(is, response.getOutputStream());
	    response.flushBuffer();
	    
	}


}