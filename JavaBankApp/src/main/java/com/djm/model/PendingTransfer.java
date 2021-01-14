package com.djm.model;

public class PendingTransfer {
    
    private Account sendingAccount;
    private Customer receivingCustomer;
    private double transferAmount;

    public PendingTransfer() {
    }

    public PendingTransfer(Account sendingAccount, Customer receivingCustomer, double transferAmount) {
        this.sendingAccount = sendingAccount;
        this.receivingCustomer = receivingCustomer;
        this.transferAmount = transferAmount;
    }

    public Account getSendingAccount() {
        return this.sendingAccount;
    }

    public void setSendingAccount(Account sendingAccount) {
        this.sendingAccount = sendingAccount;
    }

    public Customer getReceivingCustomer() {
        return this.receivingCustomer;
    }

    public void setReceivingCustomer(Customer receivingCustomer) {
        this.receivingCustomer = receivingCustomer;
    }

    public double getTransferAmount() {
        return this.transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }


}
