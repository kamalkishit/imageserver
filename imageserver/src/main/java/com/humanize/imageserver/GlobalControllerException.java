package com.humanize.imageserver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.humanize.imageserver.exception.ImageNotFoundException;
import com.humanize.imageserver.exception.NullHostedFileNameException;
import com.humanize.imageserver.exception.NullImagePathException;
import com.humanize.imageserver.exception.Exception;

@ControllerAdvice
class GlobalControllerException {
	
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseBody
    public Exception handleImageNotFoundException(ImageNotFoundException e) {
    	return new Exception(e.getErrorCode(), e.getErrorMsg());
        
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullHostedFileNameException.class)
    public void handleNullHostedFileNameException() {
        // Nothing to do
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullImagePathException.class)
    public void handleNullHostedFilePathException() {
        // Nothing to do
    }
    
    
}