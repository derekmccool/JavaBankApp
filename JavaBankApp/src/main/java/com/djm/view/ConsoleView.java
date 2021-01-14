package com.djm.view;

import java.util.List;

import com.djm.model.Account;
import com.djm.model.AccountType;
import com.djm.model.Customer;
import com.djm.model.PendingTransfer;

public class ConsoleView {
    
    private ConsoleIO io;

    public ConsoleView(ConsoleIO io){
        this.io = io;
    }

    final String[] MAIN_MENU_OPTIONS = { "Create New Account", "Login", "Exit" };
    final String[] CUSTOMER_MENU_OPTIONS = {"Deposit", "Withdrawal", "Transfer funds", 
                                            "Display Customer Info", "Change password", "View recent transactions", "Add Account",
                                            "Logout"};

    public void printWelcome(){

        System.out.println("---------------------------------------------------");
        System.out.println("-------------------  WELCOME  ---------------------");
        System.out.println("---------------------------------------------------");
    }

    public int printMenu(){

        for(int i = 0; i < MAIN_MENU_OPTIONS.length; i++){
            io.print((i+1) + ". " + MAIN_MENU_OPTIONS[i]);
        }

        return io.readInt("Please select an option: ", 1, MAIN_MENU_OPTIONS.length);
    }

    public int userOptions(){
        System.out.println("---------------------------------------------------");
        System.out.println("--------------------  MENU  -----------------------");
        System.out.println("---------------------------------------------------");
        for(int i = 0; i < CUSTOMER_MENU_OPTIONS.length; i++){
            io.print((i+1) + ". " + CUSTOMER_MENU_OPTIONS[i]);
        }

        return io.readInt("Please select an option: ", 1, CUSTOMER_MENU_OPTIONS.length);
    }

	public void exitMessage() {
        System.out.println("---------------------------------------------------");
        System.out.println("-------------------  GOODBYE  ---------------------");
        System.out.println("---------------------------------------------------");
    }
    
    public String[] createCustomer(){
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

    public double getUserAmount(String prompt){
        return io.readDouble(prompt);
    }


	public void displayCustomerInfo(Customer customer) {
        System.out.println("---------------------------------------------------");
        System.out.println("------------------  USER INFO  --------------------");
        System.out.println("---------------------------------------------------");
        System.out.println("Username: " + customer.getUsername());
        System.out.println("Customer Id: " + customer.getCustomerId());
        System.out.println("---------------------------------------------------");
        for(Account account : customer.getAccounts()){
            System.out.println("Account Id: " + account.getAccountNumber());
            System.out.println("Balance: $" + account.getBalance());
        }
    }
    
    public void displayAccountInfo(Account account){
        System.out.println("---------------------------------------------------");
        System.out.println("----------------  ACCOUNT INFO  -------------------");
        System.out.println("---------------------------------------------------");
        System.out.println("Account Type: " + account.getAccountType());
        System.out.println("Account Id: " + account.getAccountNumber());
        System.out.println("Balance: $" + account.getBalance());
    }

	public boolean yesNoAnswer(String prompt) {

        switch(io.readInt(prompt + " 1.Yes 2.No :")){
            case 1:
                return true;
            case 2:
                return false;
            default:
                return false;
        }
        
    }
    
    public String[] loginPrompt(){
        System.out.println("---------------------------------------------------");
        System.out.println("--------------------  LOGIN  ----------------------");
        System.out.println("---------------------------------------------------");
        String username = io.readString("Username: ");
        String password = io.readString("Password: ");
        String[] loginInfo = {username, password};

        return loginInfo;
    }

    public int accountSelect(List<Account> accounts){
        Account account;
        for(int i = 0; i < accounts.size(); i ++){
            account = accounts.get(i);
            System.out.println((i+1) + ". " + account.getAccountType() + " Account Number: " + 
                                account.getAccountNumber() + " Balance: $" + account.getBalance());
        }
        System.out.println((accounts.size() + 1 ) + ". " + "Return to menu");

        return io.readInt("Choose an option: ", 1, (accounts.size() + 1));
        
    }

    public int accountToTransfer(List<Account> accounts){
        Account account;
        for(int i = 0; i < accounts.size(); i ++){
            account = accounts.get(i);
            System.out.println((i+1) + ". " + account.getAccountType() + " Account Number: " + 
                                account.getAccountNumber() + " Balance: $" + account.getBalance());
        }

        return io.readInt("Choose an option: ", 1, (accounts.size()));
        
    }

    public void printTransactions(List<String> transactions){
        System.out.println("---------------------------------------------------");
        System.out.println("-------------  RECENT TRANSACTIONS  ---------------");
        System.out.println("---------------------------------------------------");
        for(String transaction : transactions){
            System.out.println(transaction);
        }
    }

	public String getTransferCustomerUsername() {
        return io.readString("Enter transfer customer username: ");
	}

	public void printPendingTransfer(PendingTransfer pendingTransfer) {
        System.out.println("You have a pending transfer from CustomerID: " + pendingTransfer.getSendingAccount().getCustomerid());
        System.out.println("For $" + pendingTransfer.getTransferAmount());  
	}
  
}
