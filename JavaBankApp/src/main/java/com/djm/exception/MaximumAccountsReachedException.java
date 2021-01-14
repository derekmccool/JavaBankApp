package com.djm.exception;

public class MaximumAccountsReachedException extends Exception{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public MaximumAccountsReachedException(int max) {
        super("You have already opened the maximum (" + max + ") accounts");
    }
}
