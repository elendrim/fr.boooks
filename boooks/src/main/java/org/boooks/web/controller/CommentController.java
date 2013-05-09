package org.boooks.web.controller;

import java.security.Principal;
import java.util.Date;

import javax.validation.Valid;

import org.boooks.db.dao.IMainCommentDAO;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.MainComment;
import org.boooks.db.entity.UserEntity;
import org.boooks.service.IBookService;
import org.boooks.service.IMainCommentService;
import org.boooks.service.IUserService;
import org.boooks.web.form.BookForm;
import org.boooks.web.form.MainCommentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("comment")
public class CommentController {
	
	private static final int PAGE_COMMENT_SIZE = 10;
	
	@Autowired
    private IBookService bookService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
    private IMainCommentService mainCommentService;
	
//	
//	@RequestMapping(value="/bookComment", method = RequestMethod.GET)
//	public String commentByBook(@RequestParam long bookId, ModelMap model){
//		model.addAttribute("mainCommentList", mainCommentService.getByBook(bookId));
//		//model.addAttribute()
//		return "comment/bookComment";
//	}
	
	@RequestMapping(value="/list", method = RequestMethod.GET )
    public String searchByauthor(@RequestParam(defaultValue = "1") int p, @RequestParam Long bookId,  ModelMap model, Principal principal) {

		Pageable pageable = new PageRequest(p - 1, PAGE_COMMENT_SIZE, Sort.Direction.DESC, "id");
        
        Page<MainComment> mainCommentPage = mainCommentService.findByBookId(bookId, pageable);
        
        int current = mainCommentPage.getNumber() +1 ;
        int begin = Math.max(1, current - 3);
        int end = Math.min(begin + 6, mainCommentPage.getTotalPages());
        
        model.addAttribute("bookId", bookId);
        model.addAttribute("mainCommentPage", mainCommentPage);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        
        return "comment/list";
    }
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
	public String addComment(@Valid MainCommentForm mainCommentForm, BindingResult result, ModelMap model, Principal principal){
		
		if ( result.hasErrors() ) {
			model.addAttribute("mainComment", mainCommentForm);	
			return "comment/bookComment";
		}
		
		UserEntity user = userService.findUserByEmail(principal.getName());
		
		Book book = bookService.getBookDbById(mainCommentForm.getBookId());
		
		MainComment mainComment = new MainComment();
		mainComment.setBook(book);
		mainComment.setModifDate(new Date());
		mainComment.setText(mainCommentForm.getComment());
		mainComment.setUser(user);
		
		mainCommentService.save(mainComment);
		return "redirect:/book/view.htm?id="+ book.getId();
	}
	
	

}
