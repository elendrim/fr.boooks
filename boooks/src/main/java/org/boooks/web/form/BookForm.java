package org.boooks.web.form;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.boooks.db.entity.Genre;
import org.boooks.db.entity.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class BookForm {
	
	private long id;
	
	@NotNull
	@NotEmpty
	private String title;
	
	private Type type;
	
	private Genre genre;
	
	private String resume;
	
	@NotNull
	private List<String> authors;
	
	private int nbPage;
	
	private CommonsMultipartFile fileData;

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public int getNbPage() {
		return nbPage;
	}

	public void setNbPage(int nbPage) {
		this.nbPage = nbPage;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

}
