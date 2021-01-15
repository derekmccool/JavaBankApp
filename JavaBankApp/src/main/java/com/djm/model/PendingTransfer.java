package com.djm.model;

import java.math.BigDecimal;

public class PendingTransfer {
    
    private Account sendingAccount;
    private Customer receivingCustomer;
    private BigDecimal transferAmount;

    public PendingTransfer() {
    }

    public PendingTransfer(Account sendingAccount, Customer receivingCustomer, BigDecimal transferAmount) {
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

    public BigDecimal getTransferAmount() {
        return this.transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }


}
