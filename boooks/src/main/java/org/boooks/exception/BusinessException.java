package org.boooks.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private String objectName;
	
	private String errorCode;

	public BusinessException(String objectName, String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.objectName = objectName;
    }
	
	public String getObjectName() {
		return objectName;
	}
	  
	  
	public String getErrorCode() {
		return errorCode;
	}


	
}
