package org.boooks.web.controller;

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
