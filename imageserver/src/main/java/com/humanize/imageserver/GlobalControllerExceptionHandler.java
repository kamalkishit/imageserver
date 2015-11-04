package com.humanize.imageserver;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.humanize.imageserver.exception.ImageNotFoundException;
import com.humanize.imageserver.exception.NullHostedFileIdException;
import com.humanize.imageserver.exception.NullHostedFilePathException;

@ControllerAdvice
class GlobalControllerExceptionHandler {
	
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ImageNotFoundException.class)
    public void handleImageNotFoundException() {
        // Nothing to do
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullHostedFileIdException.class)
    public void handleNullHostedFileIdException() {
        // Nothing to do
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 409
    @ExceptionHandler(NullHostedFilePathException.class)
    public void handleNullHostedFilePathException() {
        // Nothing to do
    }
}