package com.djm.service;

import com.djm.dao.CustomerDaoImpl;
import com.djm.exception.CustomerNameTakenException;
import com.djm.exception.InsufficientFundsExeption;
import com.djm.exception.MaximumAccountsReachedException;
import com.djm.model.Account;
import com.djm.model.Customer;
import com.djm.model.PendingTransfer;

public class CustomerService {

    private CustomerDaoImpl dao;

    public CustomerService(CustomerDaoImpl dao) {
        this.dao = dao;
    }

    public Customer createCustomer(String username, String password) throws CustomerNameTakenException {
        if (dao.getUserByUsername(username) == null) {
            return dao.addCustomer(new Customer(username, password));

        } else {
            throw new CustomerNameTakenException(username);
        }
    }

    public Customer getCustomerByUsername(String username) {
        return dao.getUserByUsername(username);
    }

    public void addAccountToCustomer(Customer customer, Account account) throws MaximumAccountsReachedException {
        if (customer.getAccounts().size() < Customer.MAX_ACCOUNTS) {
            customer.addAccount(account);
        } else {
            throw new MaximumAccountsReachedException(Customer.MAX_ACCOUNTS);
        }
    }

    public void depositToAccount(Customer customer, Account account, double amount) {
        account.deposit(amount);
        customer.addTransaction("$" + amount + "deposited to " + account.getAccountType() + " Account number: "
                + account.getAccountNumber());
    }

    public void withdrawFromAccount(Customer customer, Account account, double amount)
            throws InsufficientFundsExeption {
        account.withdrawal(amount);
        customer.addTransaction("$" + amount + "withdrew from " + account.getAccountType() + " Account number: " + account.getAccountNumber());
    }

    public void acceptTransfer(Customer customer, Account account, PendingTransfer pendingTransfer){
            account.deposit(pendingTransfer.getTransferAmount());
            customer.addTransaction("$" + pendingTransfer.getTransferAmount() + "transfered to " + account.getAccountType() + " Account number: "
            + account.getAccountNumber() + " from " + pendingTransfer.getSendingAccount());
            customer.getPendingTransfers().remove(pendingTransfer);
    }

    public void denyTransfer(Customer customer, PendingTransfer pendingTransfer){
            pendingTransfer.getSendingAccount().deposit(pendingTransfer.getTransferAmount());
            customer.getPendingTransfers().remove(pendingTransfer);
    }


}
