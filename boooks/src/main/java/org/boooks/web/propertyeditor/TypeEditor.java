package org.boooks.web.propertyeditor;

import java.beans.PropertyEditorSupport;

import org.boooks.db.entity.Type;
import org.springframework.util.StringUtils;

public class TypeEditor extends PropertyEditorSupport {

	public void setAsText(String text) {
		
		if (StringUtils.hasText(text)) {
			Type type = new Type();
			type.setId(Long.valueOf(text));
			setValue(type);
		} else {
			setValue(null);
		}
	}
	
	@Override
    public String getAsText() {
        Type type = (Type) getValue();
        if (type != null) {
        	return type.getId().toString();
        } 
        
        return null;
    }
	
	

}
