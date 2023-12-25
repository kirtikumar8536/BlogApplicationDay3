package com.blog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    // this is unchecked exception-the exception which are not checked by compiler
    //private String message;

    public ResourceNotFoundException(String message) {
        super(message);// call parent class constructor
        //this.message = message;
        System.out.println("in RNFEC: "+message);
    }
}
