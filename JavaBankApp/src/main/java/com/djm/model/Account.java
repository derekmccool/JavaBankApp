package com.djm.model;

import com.djm.exception.InsufficientFundsExeption;

public class Account {

    private int customerid;
    private int accountNumber;
    private double balance;
    public AccountType accountType;
    static int counter = 100000;

    public Account() {
        this.accountNumber = counter;
    }

    public Account(int customerid, double balance, AccountType accountType) {
        this.customerid = customerid;
        this.accountNumber = counter;
        this.balance = balance;
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

    public double getBalance() {
        return this.balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public void withdrawal(double amount) throws InsufficientFundsExeption {
        if(amount < this.balance){
            setBalance(this.balance - amount);
        }else{
            throw new InsufficientFundsExeption(this.balance);
        }

    }

    public void deposit(double amount){

        if(amount > 0){
            setBalance(this.balance + amount);
        }
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
