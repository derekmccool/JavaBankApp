package com.djm.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    
    static int counter = 10000;
    public static final int MAX_ACCOUNTS = 3;
    private int customerId;
    private String username;
    private String password;
    private List<Account> accounts = new ArrayList<>();
    private List<PendingTransfer> pendingTransfers = new ArrayList<>();
    private List<String> transactions = new ArrayList<>();
    private CustomerSettings customerSettings;

    public Customer() {
    }

    public Customer( String username, String password) {
        this.customerId = counter;
        this.username = username;
        this.password = password;
        this.customerSettings = new CustomerSettings();
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

    public boolean correctPassword(String password) {
        return this.password.equals(password);
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

    public void addTransaction(String transaction) {
        this.transactions.add(transaction);
    }

    public List<PendingTransfer> getPendingTransfers() {
        return this.pendingTransfers;
    }

    public void addPendingTransfer(PendingTransfer pendingTransfer) {
        this.pendingTransfers.add(pendingTransfer);
    }

   
}
