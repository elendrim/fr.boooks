package org.boooks.rest.controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.jcr.RepositoryException;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.tika.config.TikaConfig;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypeException;
import org.boooks.db.common.BooksMimeType;
import org.boooks.db.common.ScaleUtils;
import org.boooks.db.entity.Buy;
import org.boooks.db.entity.BuyPK;
import org.boooks.db.entity.UserEntity;
import org.boooks.jcr.entity.BookData;
import org.boooks.jcr.entity.FileData;
import org.boooks.service.IBookService;
import org.boooks.service.IBuyService;
import org.boooks.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping("book")
public class BookRestController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookRestController.class);
	
	@Autowired
	private IUserService userService;

	@Autowired
	private IBookService bookService;
	
	@Autowired
    private IBuyService buyService;
	
	@SuppressWarnings("serial")
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	public class UnauthorizedException extends RuntimeException {
	}
	
	@SuppressWarnings("serial")
	@ResponseStatus(value = HttpStatus.PAYMENT_REQUIRED)
	public class PaymentRequiredException extends RuntimeException {
	}
	
	
	@RequestMapping(
			value = "/candownload/{id}/{format}", 
			method = RequestMethod.GET,
			produces="application/json"
		)
	public @ResponseBody Boolean candownload(@PathVariable("id") Long id, @PathVariable("format") String format, Principal principal)  {
		
		if ( principal == null ) {
			throw new UnauthorizedException();
		}
		UserEntity user = userService.findUserByEmail(principal.getName());
		
		BuyPK buyId = new BuyPK();
		buyId.setBookId(id);
		buyId.setUserId(user.getId());
        Buy buy = buyService.getById(buyId);
        
        if ( buy == null ) {
        	throw new PaymentRequiredException();
        }
    	
        return Boolean.TRUE; 
	}
	
	@RequestMapping(value = "/file/{id}/{format}", method = RequestMethod.GET)
	public void getFile(@PathVariable("id") Long id, @PathVariable("format") String format, Principal principal, HttpServletResponse response) throws RepositoryException, IOException, MimeTypeException {
	    // get your file as InputStream
		
		if ( principal == null ) {
			throw new UnauthorizedException();
		}
		UserEntity user = userService.findUserByEmail(principal.getName());
		
		BuyPK buyId = new BuyPK();
		buyId.setBookId(id);
		buyId.setUserId(user.getId());
        Buy buy = buyService.getById(buyId);
        
        if ( buy == null ) {
        	throw new PaymentRequiredException();
        }
    	
		
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
	
	
	@RequestMapping(value = "/cover/{id}", method = RequestMethod.GET, produces = "image/png")
	public void getCoverFile(
			@PathVariable("id") Long id, 
			@RequestParam( value="w", required=false, defaultValue="160") int width, 
			@RequestParam( value="h", required=false, defaultValue="0") int height,
			HttpServletResponse response) throws RepositoryException, IOException, MimeTypeException {
	    // get your file as InputStream
		
		FileData coverData = bookService.getCoverData(id);
		
		InputStream input = null;
		try {
			if ( coverData != null ) {
				input = new ByteArrayInputStream(coverData.getBytes());
				
				BufferedImage src = ImageIO.read(input);
				
				
				if ( width == 0 ) {
					double scale = ScaleUtils.imageScale(src.getHeight(),  height);
					width = (int) (src.getWidth() * scale);
				} else if ( height == 0 ) {
					double scale = ScaleUtils.imageScale(src.getWidth(),  width);
					height = (int) (src.getHeight() * scale);
				}
				
				BufferedImage dst = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				
				Image scaleImage = src.getScaledInstance( width, height, Image.SCALE_AREA_AVERAGING );
				Graphics2D g2d = dst.createGraphics();
				g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
				g2d.drawImage( scaleImage, 0, 0, width, height, null );
				g2d.dispose();
				
				boolean writable = ImageIO.write(dst, "PNG", response.getOutputStream());
				if( !writable && LOGGER.isErrorEnabled() ){
					LOGGER.error("No writer found !");
				}
				response.setContentType("image/png");      
			    response.flushBuffer();
			} 
		} catch (Exception e ) {
			 LOGGER.error("Unable to get image ", e);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}


}