package com.djm.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    
    static int counter = 10000;

    private int customerId;
    private String username;
    private String password;
    private List<Account> accounts = new ArrayList<>();
    private List<String> transactions = new ArrayList<>();


    public Customer() {
    }

    public Customer( String username, String password) {
        this.customerId = counter;
        this.username = username;
        this.password = password;
        counter++;

    }

    public int getCustomerId() {
        return this.customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Account> getAccounts() {
        return this.accounts;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public List<String> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }


}