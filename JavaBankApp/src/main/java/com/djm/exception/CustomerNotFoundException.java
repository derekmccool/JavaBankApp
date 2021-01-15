package com.djm.exception;

public class CustomerNotFoundException extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    public CustomerNotFoundException(String username){
        super("No customer with username: " + username + " found.");
    }
}
