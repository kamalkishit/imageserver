package com.humanize.imageserver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.humanize.imageserver.exception.Exception;
import com.humanize.imageserver.exception.ImageCreationException;
import com.humanize.imageserver.exception.ImageNotFoundException;

@ControllerAdvice
class GlobalControllerException {
	
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ImageNotFoundException.class)
    @ResponseBody
    public Exception handleImageNotFoundException(ImageNotFoundException exception) {
    	return new Exception(exception.getErrorCode(), exception.getErrorMsg());
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(ImageCreationException.class)
    @ResponseBody
    public Exception handleImageNotFoundException(ImageCreationException exception) {
    	return new Exception(exception.getErrorCode(), exception.getErrorMsg());
    }
}