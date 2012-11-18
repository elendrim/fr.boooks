package org.boooks.web.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jcr.RepositoryException;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.boooks.db.common.BooksMimeType;
import org.boooks.db.entity.Author;
import org.boooks.db.entity.Book;
import org.boooks.db.entity.Genre;
import org.boooks.db.entity.Type;
import org.boooks.db.entity.UserEntity;
import org.boooks.jcr.entity.BookData;
import org.boooks.service.IAuthorService;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;


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
	
	@Autowired
    private IAuthorService authorService;
	
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

    @RequestMapping(value="/view", method = RequestMethod.GET)
    public String view( @RequestParam long id, ModelMap model) throws RepositoryException, MalformedURLException, IOException {
		
        Book book = bookService.getBookDbById(id);
        model.addAttribute("book", book);
        
        List<BooksMimeType> booksMimeTypeList = bookService.getBookMimeType(id);
        model.addAttribute("booksMimeTypeList", booksMimeTypeList);
        
        return "book/view";
		
    }
	
	@RequestMapping(value="/add", method = RequestMethod.GET)
    public String add(ModelMap model,  Principal principal) {
		
		UserEntity user = userService.findUserByEmail(principal.getName());
		
		List<String> authorName = new ArrayList<String>();
		authorName.add(user.getFirstname());
		authorName.add(user.getLastname());
		String author = StringUtils.join(authorName, " ");
		
		List<String> authorList = new ArrayList<String>();
		authorList.add(author);
		
		BookForm bookForm = new BookForm();
		bookForm.setAuthors(authorList);
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
		
		List<Author> authorList = new ArrayList<Author>();
		for (String authorName : bookForm.getAuthors()) {
			Author author = new Author();
			author.setName(authorName);
			authorList.add(author);
		}
		book.setAuthors(authorList);
		
		book.setUser(user);
		
		
		Map<BooksMimeType, BookData> booksMap = new HashMap<BooksMimeType, BookData>();
		if ( bookForm.getFilePdf() != null ) {
			CommonsMultipartFile file = bookForm.getFilePdf();
			BookData bookData = new BookData();
			bookData.setBytes(file.getBytes());
//			bookData.setMimeType(file.getContentType());
			// override the file mime type, to owers
			bookData.setMimeType(BooksMimeType.PDF.getMimeType());
			bookData.setFilename(file.getOriginalFilename());
			bookData.setTitle(bookForm.getTitle());
			booksMap.put(BooksMimeType.PDF, bookData);
		}
		if ( bookForm.getFileEpub() != null ) {
			CommonsMultipartFile file = bookForm.getFileEpub();
			BookData bookData = new BookData();
			bookData.setBytes(file.getBytes());
//			bookData.setMimeType(file.getContentType());
			// override the file mime type, to owers
			bookData.setMimeType(BooksMimeType.EPUB.getMimeType());
			bookData.setFilename(file.getOriginalFilename());
			bookData.setTitle(bookForm.getTitle());
			booksMap.put(BooksMimeType.EPUB, bookData);
		}
		if ( bookForm.getFileText() != null ) {
			CommonsMultipartFile file = bookForm.getFileText();
			BookData bookData = new BookData();
			bookData.setBytes(file.getBytes());
//			bookData.setMimeType(file.getContentType());
			// override the file mime type, to owers
			bookData.setMimeType(BooksMimeType.TEXT.getMimeType());
			bookData.setFilename(file.getOriginalFilename());
			bookData.setTitle(bookForm.getTitle());
			booksMap.put(BooksMimeType.TEXT, bookData);
		}
		
		
		Book savedBook = bookService.save(book, booksMap);
		
		model.addAttribute("id", savedBook.getId());
        return "redirect:/book/view.htm";
    }
	
	@RequestMapping(value="/myPublications", method = RequestMethod.GET )
    public String myPublications(@RequestParam(defaultValue = "1") int p, ModelMap model, Principal principal) {

		Pageable pageable = new PageRequest(p - 1, PAGE_BOOK_SIZE, Sort.Direction.ASC, "publishDate");
        
        Page<Book> bookPage = bookService.findBooksByEmail(principal.getName(), pageable);
        
        int current = bookPage.getNumber() +1 ;
        int begin = Math.max(1, current - 3);
        int end = Math.min(begin + 6, bookPage.getTotalPages());
        
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        
        return "book/myPublications";
    }
	
	@RequestMapping(value="/search", method = RequestMethod.GET )
    public String search(@RequestParam(defaultValue = "1") int p, @RequestParam(required=false) String q,  ModelMap model, Principal principal) {

		Pageable pageable = new PageRequest(p - 1, PAGE_BOOK_SIZE, Sort.Direction.ASC, "publishDate");
        
        Page<Book> bookPage = bookService.findBooksByQuery(q, pageable);
        
        int current = bookPage.getNumber() +1 ;
        int begin = Math.max(1, current - 3);
        int end = Math.min(begin + 6, bookPage.getTotalPages());
        
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        
        return "book/search";
    }
	
	@RequestMapping(value="/author", method = RequestMethod.GET )
    public String searchByauthor(@RequestParam(defaultValue = "1") int p, @RequestParam(required=false) String author,  ModelMap model, Principal principal) {

		Pageable pageable = new PageRequest(p - 1, PAGE_BOOK_SIZE, Sort.Direction.ASC, "publishDate");
        
        Page<Book> bookPage = bookService.findBooksByAuthor(author, pageable);
        
        int current = bookPage.getNumber() +1 ;
        int begin = Math.max(1, current - 3);
        int end = Math.min(begin + 6, bookPage.getTotalPages());
        
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("beginIndex", begin);
        model.addAttribute("endIndex", end);
        model.addAttribute("currentIndex", current);
        
        return "book/author";
    }
	
	
}