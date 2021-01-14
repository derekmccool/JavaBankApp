package com.djm.exception;

public class CustomerNameTakenException extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    public CustomerNameTakenException(String username) {
		  super("user with " + username + " already exists");
    }
}