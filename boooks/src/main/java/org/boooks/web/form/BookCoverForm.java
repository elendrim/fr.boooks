package org.boooks.web.form;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class BookCoverForm {
	

	private long id;
	
	private CommonsMultipartFile fileCover;

	public CommonsMultipartFile getFileCover() {
		return fileCover;
	}

	public void setFileCover(CommonsMultipartFile fileCover) {
		this.fileCover = fileCover;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
