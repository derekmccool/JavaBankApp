package com.djm.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.djm.exception.InsufficientFundsExeption;

public class Account {

    private int customerid;
    private int accountNumber;
    private BigDecimal balance;
    public AccountType accountType;
    static int counter = 100000;

    public Account() {
        this.accountNumber = counter;
    }

    public Account(int customerid, double balance, AccountType accountType) {
        this.customerid = customerid;
        this.accountNumber = counter;
        this.balance = new BigDecimal(balance).setScale(2, RoundingMode.HALF_UP);
        this.accountType = accountType;
        counter++;
    }

    public int getCustomerid() {
        return this.customerid;
    }

    public void setCustomerid(int customerid) {
        this.customerid = customerid;
    }

    public int getAccountNumber() {
        return this.accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getBalance() {
        return this.balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void withdrawal(BigDecimal amount) throws InsufficientFundsExeption {
        if(this.balance.compareTo(amount) > -1){
            setBalance(this.balance.subtract(amount));
        }else{
            throw new InsufficientFundsExeption(this.balance);
        }

    }

    public void deposit(BigDecimal amount){

        setBalance(this.balance.add(amount));
        
    }

    @Override
    public String toString() {
        return "{" +
            " customerid='" + customerid + "'" +
            ", accountNumber='" + accountNumber + "'" +
            ", balance='" + balance + "'" +
            "}";
    }

}
