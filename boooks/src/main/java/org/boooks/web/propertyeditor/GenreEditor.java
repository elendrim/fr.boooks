package org.boooks.web.propertyeditor;

import java.beans.PropertyEditorSupport;

import org.boooks.db.entity.Genre;
import org.springframework.util.StringUtils;

public class GenreEditor extends PropertyEditorSupport {

	public void setAsText(String text) {
		
		
		if (StringUtils.hasText(text)) {
			Genre genre = new Genre();
			genre.setId(Long.valueOf(text));
			setValue(genre);
		} else {
			setValue(null);
		}
	}
	
	@Override
    public String getAsText() {
        Genre genre = (Genre) getValue();
        if (genre != null) {
        	return genre.getId().toString();
        } 
        
        return null;
    }
	
	

}
