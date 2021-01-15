package com.djm.exception;

import java.math.BigDecimal;

public class InsufficientFundsExeption extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public InsufficientFundsExeption(BigDecimal balance) {
		super("Insufficient funds. Current balance is: $" + balance);
	}
}
