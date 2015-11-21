package com.humanize.imageserver.exception;

import java.lang.Exception;

public class AmazonS3ImageNotFoundException extends Exception {

	private int errorCode;
	private String errorMsg;
	
	public AmazonS3ImageNotFoundException(int errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
