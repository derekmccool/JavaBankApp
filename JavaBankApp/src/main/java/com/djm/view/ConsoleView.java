package com.djm.view;

import com.djm.model.Account;
import com.djm.model.AccountType;
import com.djm.model.Customer;

public class ConsoleView {
    
    private ConsoleIO io;

    public ConsoleView(ConsoleIO io){
        this.io = io;
    }

    public void printWelcome(){

        System.out.println("---------------------------------------------------");
        System.out.println("-------------------  WELCOME  ---------------------");
        System.out.println("---------------------------------------------------");
    }

    public int printMenu(String[] options){

        for(int i = 0; i < options.length; i++){
            io.print((i+1) + ". " + options[i]);
        }

        return io.readInt("Please select an option: ", 1, options.length);
    }

    public int userOptions(){
        String[] options = {"Deposit", "Withdrawal", "Transfer funds", "Display Customer Info", "Change password", "Logout"};
        for(int i = 0; i < options.length; i++){
            io.print((i+1) + ". " + options[i]);
        }

        return io.readInt("Please select an option: ");
    }

	public void exitMessage() {
        System.out.println("---------------------------------------------------");
        System.out.println("-------------------  GOODBYE  ---------------------");
        System.out.println("---------------------------------------------------");
    }
    
    public String[] createAccount(){
        String username = io.readString("Enter an account username: ");
        String password = io.readString("Enter a password: ");
        String confirmPass = io.readString("Confirm password: ");
        String[] accountInfo = {username, password, confirmPass};
        return accountInfo;
    }

    public AccountType addAccount(AccountType[] accounts){

        System.out.println("What type of account would you like to add?");
        for(int i = 0; i < accounts.length; i++){
            System.out.println((i+1) + " " + accounts[i]);
        }
        int choice = io.readInt("Please select an option: ", 1, accounts.length);
        return (accounts[choice - 1]);
    }

    public double initialDeposit(Account account){
        return io.readDouble("Initial deposit: $");

    }

	public void confirmNewAccount(Customer customer) {
        System.out.println("Username: " + customer.getUsername());
        System.out.println("Customer Id: " + customer.getCustomerId());
        System.out.println("---------------------------------------------------");
        for(Account account : customer.getAccounts()){
            System.out.println("Account Id: " + account.getAccountNumber());
            System.out.println("Balance: $" + account.getBalance());
        }
    }
    
    public void displayAccountInfo(Account account){
        System.out.println("Account Type: " + account.getAccountType());
        System.out.println("Account Id: " + account.getAccountNumber());
        System.out.println("Balance: $" + account.getBalance());
    }

	public boolean yesNoAnswer(String prompt) {
        System.out.println("1. Yes");
        System.out.println("2. No");
        switch(io.readInt(prompt)){
            case 1:
                return true;
            case 2:
                return false;
            default:
                return false;
        }
        
    }
    
    public String[] loginPrompt(){
        String username = io.readString("Username: ");
        String password = io.readString("Password: ");
        String[] loginInfo = {username, password};

        return loginInfo;
    }
}
