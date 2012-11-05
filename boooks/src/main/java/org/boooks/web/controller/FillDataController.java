package org.boooks.web.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.validation.Valid;

import org.boooks.utils.BoooksDataFactory;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.SexEnum;
import org.boooks.db.entity.UserEntity;
import org.boooks.exception.BusinessException;
import org.boooks.service.IBookService;
import org.boooks.service.IMainCommentService;
import org.boooks.service.IUserService;
import org.boooks.service.impl.BookService;
import org.boooks.web.form.UserRegisterForm;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("fill")
public class FillDataController {

	@Autowired
    private IUserService userService;
	
	@Autowired
    private IBookService bookService;
	
	@Autowired
	private IMainCommentService mainCommentService;
	
	@RequestMapping(value="/fill_data", method = RequestMethod.GET)
    public String registration(Model model) {
		
		BoooksDataFactory df = new BoooksDataFactory();
		
		int size = 1000;
		
		try {

			//create as least an administrator
			userService.createDummyUser(true, df);
			
			//create users
			for(int i = 0;i < size;i++){
				UserEntity user = userService.createDummyUser(false, df);
				
				//10% of them are writers
				//if(df.chance(10)){
					//which have between 1 and 10 books
					int nbBooks = df.df.getNumberBetween(1, 10);
					System.out.println("adding "+nbBooks + " books");
					for(int nb = 0; i< nbBooks; i++){
						Book book = bookService.createDummyBook(user, df);
						
						mainCommentService.createDummyMainComment(user, book, df);
						mainCommentService.createDummyMainComment(user, book, df);
						mainCommentService.createDummyMainComment(user, book, df);
						mainCommentService.createDummyMainComment(user, book, df);
					}
						
				//}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "fill/fill_data";
    }
	
}