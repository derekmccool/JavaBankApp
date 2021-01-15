package com.djm.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.djm.dao.CustomerDaoImpl;
import com.djm.exception.CustomerNameTakenException;
import com.djm.exception.CustomerNotFoundException;
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
        if (checkUsernameAvailable(username)) {
            return dao.addCustomer(new Customer(username.toLowerCase(), password));

        } else {
            throw new CustomerNameTakenException(username);
        }
    }

    public boolean checkUsernameAvailable(String username){
        return dao.getUserByUsername(username.toLowerCase()) == null;
    }

    public List<String> verifyUsername(String username){
        List<String> missingRequirements = new ArrayList<String>();

        if(username.length() < 3){
            missingRequirements.add("username must be atleast 3 characters in length.");
        }

        if(username.length() > 15){
            missingRequirements.add("username can not be longer than 15 characters in length.");
        }

        //check whitespace
        if(username.matches(".*\\s+.*")){
            missingRequirements.add("usernames cannot have spaces.");
        }

        return missingRequirements;
    }

    public List<String> verifyPassword(String password) {

        List<String> missingRequirements = new ArrayList<String>();

        if(password.length() < 7){
            missingRequirements.add("Password must be atleast 7 characters in length.");
        }

        if(password.length() > 12){
            missingRequirements.add("Password can not be longer than 12 characters in length.");
        }

        //check for lowercase
        if(!password.matches(".*[a-z].*")){
            missingRequirements.add("Password must have atleast 1 lowercase letter.");
        }
        
        //check for uppercase
        if(!password.matches(".*[A-Z].*")){
            missingRequirements.add("Password must have atleast 1 uppercase letter.");
        }
        
        //check for number
        if(!password.matches(".*[0-9].*")){
            missingRequirements.add("Password must have atleast 1 number.");
        }
        
        //check for special character
        if(password.matches(".[a-zA-Z0-9]*")){
            missingRequirements.add("Password must have atleast 1 special character.");
        }

        //check whitespace
        if(password.matches(".*\\s+.*")){
            missingRequirements.add("Password cannot have spaces.");
        }
        
        return missingRequirements;
    }

    public Customer getCustomerByUsername(String username) throws CustomerNotFoundException {

        if(dao.getUserByUsername(username) != null){
            return dao.getUserByUsername(username);
        }else{
            throw new CustomerNotFoundException(username);
        }
    }

    public boolean correctPassword(Customer customer, String password){
        return customer.correctPassword(password);
    }

    public boolean updatePassword(Customer customer, String oldPassword, String newPassword){
        if(correctPassword(customer, oldPassword)){
            customer.setPassword(newPassword);
            return true;
        }else{
            return false;
        }
}

    public void addAccountToCustomer(Customer customer, Account account) throws MaximumAccountsReachedException {
        if (customer.getAccounts().size() < Customer.MAX_ACCOUNTS) {
            customer.addAccount(account);
        } else {
            throw new MaximumAccountsReachedException(Customer.MAX_ACCOUNTS);
        }
    }

    public void depositToAccount(Customer customer, Account account, BigDecimal amount) {
        account.deposit(amount);
        customer.addTransaction("DEPOSIT TO ACCOUNT NUMBER: " + account.getAccountNumber() + " AMOUNT: $" + amount + " DATE: " + LocalDate.now());
    }

    public void withdrawFromAccount(Customer customer, Account account, BigDecimal amount)
            throws InsufficientFundsExeption {
        account.withdrawal(amount);
        customer.addTransaction("WITHDRAWAL FROM ACCOUNT NUMBER: " + account.getAccountNumber() + " AMOUNT: $" + amount + " DATE: " + LocalDate.now());
    }

    public void sendTransfer(Customer sender, Customer receiver, Account account, BigDecimal amount) throws InsufficientFundsExeption {
        account.withdrawal(amount);
        sender.addTransaction("TRANSFERED FROM ACCOUNT NUMBER: " + account.getAccountNumber() + " AMOUNT: $" + amount + " DATE: " + LocalDate.now() + "TO " + receiver.getUsername());
        receiver.addPendingTransfer(new PendingTransfer(account,
           receiver, amount));

    }

    public void acceptTransfer(Customer customer, Account account, PendingTransfer pendingTransfer){
            account.deposit(pendingTransfer.getTransferAmount());
            customer.addTransaction("RECEIVED TRANSFER FROM: " + account.getAccountNumber() + " AMOUNT: $" + pendingTransfer.getTransferAmount() + " DATE: " + LocalDate.now());
            customer.getPendingTransfers().remove(pendingTransfer);
    }

    public void denyTransfer(Customer customer, PendingTransfer pendingTransfer){
            pendingTransfer.getSendingAccount().deposit(pendingTransfer.getTransferAmount());
            customer.getPendingTransfers().remove(pendingTransfer);
    }

}
