package org.boooks.web.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.Genre;
import org.boooks.db.entity.Type;
import org.boooks.jcr.entity.BookData;
import org.boooks.service.IBookService;
import org.boooks.service.IGenreService;
import org.boooks.service.ITypeService;
import org.boooks.web.form.BookForm;
import org.boooks.web.propertyeditor.GenreEditor;
import org.boooks.web.propertyeditor.TypeEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.boooks.db.dao.IMainCommentDAO;
import org.boooks.service.IBookService;
import org.boooks.service.IMainCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("comment")
public class CommentController {
	
	@Autowired
    private IBookService bookService;
	
	@Autowired
    private IMainCommentService mainCommentService;
	
    @Autowired
    private IMainCommentDAO mainCommentDAO;
	
	@RequestMapping(value="/bookComment", method = RequestMethod.GET)
	public String commentByBook(ModelMap model){
		//System.out.println("searching comment for the book "+bookId);
		model.addAttribute("commentList", mainCommentDAO.findAll());
		
		return "comment/bookComment";
	}
	
	
	
	/*
	@RequestMapping(value="/commentByBook", method = RequestMethod.GET)
	public String login(Long bookId) {
		
		
		
		return "login/login";
	}
 
	@RequestMapping(value="/loginfailed", method = RequestMethod.GET)
	public String loginerror(ModelMap model) {
 		model.addAttribute("error", "true");
		return "login/login";
 	}
 
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
 		return "login/login";
 	}
 	*/

}
