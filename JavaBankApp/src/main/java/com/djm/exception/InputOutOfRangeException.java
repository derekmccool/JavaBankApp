package com.djm.exception;

public class InputOutOfRangeException extends Exception{


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public InputOutOfRangeException(int min, int max) {
		super("Input must be between " + min + "-" + max);
	}
}
