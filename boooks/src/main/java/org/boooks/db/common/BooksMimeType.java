package org.boooks.db.common;

public enum BooksMimeType {

	PDF("application/pdf"),
	EPUB("application/epub+zip"),
	TEXT("text/plain");

	private String mimeType;
	
	BooksMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
	
	public String getMimeType() {
		return mimeType;
	}
	
	public static BooksMimeType searchByMimeType (String mimeType ) {
		for (BooksMimeType booksMimeType : values()) {
			if ( booksMimeType.mimeType.equals(mimeType )) {
				return booksMimeType;
			}
		}
		return null;
	}
	
}
