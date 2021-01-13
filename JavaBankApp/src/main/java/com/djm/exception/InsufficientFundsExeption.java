package com.djm.exception;

public class InsufficientFundsExeption extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public InsufficientFundsExeption(double balance) {
		super("Insufficient funds, withdrawal cannot be greater than current account balance. Current balance is: $" + balance);
	}
}
