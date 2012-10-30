package org.boooks.web.controller;

import java.net.MalformedURLException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jcr.RepositoryException;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.Genre;
import org.boooks.db.entity.Type;
import org.boooks.db.entity.UserEntity;
import org.boooks.service.IBookService;
import org.boooks.service.IGenreService;
import org.boooks.service.ITypeService;
import org.boooks.service.IUserService;
import org.boooks.web.form.BookForm;
import org.boooks.web.propertyeditor.GenreEditor;
import org.boooks.web.propertyeditor.TypeEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("book")
public class BookController {
	
	private static final int PAGE_BOOK_SIZE = 24;

	@Autowired
    private IBookService bookService;
	
	@Autowired
    private IUserService userService;
	
	@Autowired
    private IGenreService genreService;
	
	@Autowired
    private ITypeService typeService;
	
	@ModelAttribute("genreList")
	public List<Genre> genreList(){
		return genreService.getAll();
	}
	
	@ModelAttribute("typeList")
	public List<Type> typeList(){
		return typeService.getAll();
	}
	
	@InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(Genre.class, new GenreEditor());
        dataBinder.registerCustomEditor(Type.class, new TypeEditor());
    }

    
	@RequestMapping(value="/list", method = { RequestMethod.GET, RequestMethod.POST } )
    public String list(@RequestParam(defaultValue = "1") int p, ModelMap model ) {
		
		Pageable pageable = new PageRequest(p - 1, PAGE_BOOK_SIZE, Sort.Direction.ASC, "publishDate");
        
        Page<Book> bookPage = bookService.findAll(pageable);
        
        int current = bookPage.getNumber() +1 ;
        int begin = Math.max(1, current - 3);
        int end = Math.min(begin + 6, bookPage.getTotalPages());
        
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        
        
        return "book/books";
    }
	
	@RequestMapping(value="/view", method = RequestMethod.GET)
    public String view( @RequestParam long id, ModelMap model) throws RepositoryException, MalformedURLException {
		
        Book book = bookService.getBookJcrById(id);
        model.addAttribute("book", book);
        return "book/view";
		
    }
	
	@RequestMapping(value="/add", method = RequestMethod.GET)
    public String add(ModelMap model,  Principal principal) {
		
		UserEntity user = userService.findUserByEmail(principal.getName());
		
		List<String> authorList = new ArrayList<String>();
		authorList.add(user.getFirstname());
		authorList.add(user.getLastname());
		String author = StringUtils.join(authorList, " ");
		
		
		BookForm bookForm = new BookForm();
		bookForm.setAuthor(author);
		model.addAttribute("bookForm", bookForm);
        return "book/add";
    }
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
    public String add(@Valid BookForm bookForm, BindingResult result, ModelMap model,  Principal principal) throws RepositoryException, MalformedURLException {
		
		if ( result.hasErrors() ) {
			model.addAttribute("bookForm", bookForm);	
			return "book/add";
		}
		
		UserEntity user = userService.findUserByEmail(principal.getName());
		
		Book book = new Book();
		
		book.setTitle(bookForm.getTitle());
		book.setGenre(genreService.getById(bookForm.getGenre().getId()));
		book.setType(typeService.getById(bookForm.getType().getId()));
		book.setNbPage(bookForm.getNbPage());
		book.setResume(bookForm.getResume());
		book.setPublishDate(new Date());
		book.setAuthor(bookForm.getAuthor());
		book.setUser(user);
		
		byte[] dataBytes = bookForm.getFileData().getBytes();
		String contentType = bookForm.getFileData().getContentType();
		
		Book savedBook = bookService.save(book, dataBytes, contentType);
		
		model.addAttribute("id", savedBook.getId());
        return "redirect:/book/view.htm";
    }
	
	@RequestMapping(value="/myPublications", method = RequestMethod.GET )
    public String myPublications(@RequestParam(defaultValue = "1") int p, ModelMap model, Principal principal) {

		Pageable pageable = new PageRequest(p - 1, PAGE_BOOK_SIZE, Sort.Direction.ASC, "publishDate");
        
        Page<Book> bookPage = bookService.findBooks(principal.getName(), pageable);
        
        int current = bookPage.getNumber() +1 ;
        int begin = Math.max(1, current - 3);
        int end = Math.min(begin + 6, bookPage.getTotalPages());
        
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        
        return "book/books";
    }
	
}