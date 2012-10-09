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

@Controller
@RequestMapping("book")
public class BookController {

	@Autowired
    private IBookService bookService;
	
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
    public String list( ModelMap model) {
        List<Book> bookList = bookService.getAll();
        model.addAttribute("bookList", bookList);
        return "book/list";
    }
	
	@RequestMapping(value="/view", method = RequestMethod.GET)
    public String view( @RequestParam long id, ModelMap model) throws RepositoryException, MalformedURLException {
		
        Book book = bookService.getBookJcrById(id);
        model.addAttribute("book", book);
        return "book/view";
		
    }
	
	@RequestMapping(value="/add", method = RequestMethod.GET)
    public String add(ModelMap model) {
		BookForm bookForm = new BookForm();
		model.addAttribute("bookForm", bookForm);
        return "book/add";
    }
	
	@RequestMapping(value="/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("book") BookForm bookForm, ModelMap model, BindingResult result) throws RepositoryException, MalformedURLException {
		
		Book book = new Book();
		
		book.setTitle(bookForm.getTitle());
		book.setGenre(genreService.getById(bookForm.getGenre().getId()));
		book.setType(typeService.getById(bookForm.getType().getId()));
		book.setNbPage(bookForm.getNbPage());
		book.setResume(bookForm.getResume());
		book.setPublishDate(new Date());
		
		byte[] dataBytes = bookForm.getFileData().getBytes();
		String contentType = bookForm.getFileData().getContentType();
		
		Book savedBook = bookService.save(book, dataBytes, contentType);
		
		model.addAttribute("id", savedBook.getId());
        return "redirect:/book/view.htm";
		
    }
	
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