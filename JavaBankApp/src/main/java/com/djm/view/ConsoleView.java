package com.djm.view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.djm.model.Account;
import com.djm.model.AccountType;
import com.djm.model.Customer;
import com.djm.model.PendingTransfer;

public class ConsoleView {
    
    private ConsoleIO io;
    private String border1 = "";
    private String border2 = "";
    private String borderline = "";
    private String borderSymbol1 = "-";
    private String borderSymbol2 = "=";
    private String borderSymbol3 = "=";

    private int width = 50;
    final String[] MAIN_MENU_OPTIONS = { "Create New Account", "Login", "Exit" };
    final String[] CUSTOMER_MENU_OPTIONS = {"Deposit", "Withdrawal", "Transfer funds", 
                                            "Display Customer Info", "Change password", "View recent transactions", "Add Account",
                                            "Logout"};


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    public ConsoleView(ConsoleIO io){
        this.io = io;
        this.width = 100;
        this.border1 = ANSI_PURPLE;
        this.border2 = ANSI_YELLOW;
        this.borderline = ANSI_PURPLE;
        this.setBorders();
    }

    private void resetBorders(){
        this.border1 = ANSI_PURPLE;
        this.border2 = ANSI_YELLOW;
        this.borderline = ANSI_PURPLE;
    }

    private void setBorders(){

        for(int i = 0; i < this.width; i ++){
            this.border1 += this.borderSymbol1;
            this.border2 += this.borderSymbol2;
            this.borderline += this.borderSymbol3;
        }
        this.border1 += ANSI_RESET;
        this.border2 += ANSI_RESET;
        this.borderline += ANSI_RESET;
    }

    public void setUserSettings(String border1, String border2, String borderline, int width){
        this.borderSymbol1 = border1;
        this.borderSymbol2 = border2;
        this.borderSymbol2 = borderline;
        this.width = width;
        resetBorders();
        this.setBorders();
    }


    public void borderBarTop(){   
        io.print(border1);
        io.print(border2);
    }

    public void borderBarBottom(){
        io.print(border2);
        io.print(border1);
    }

    
    public void borderline(){
        io.print(borderline + "\n");
    }

    public void borderLineNoExtraSpace(){
        io.print(border1);
    }
   

    public void displayMessageBanner(String message){
        borderBarTop();
        int sideSpaceCount = (width - message.length())/2;
        String spacesOnWordLine = "";
        for(int i = 0; i < sideSpaceCount; i ++){
            spacesOnWordLine += " ";
        }
        String messageLine = spacesOnWordLine + message;
        io.print(messageLine.toUpperCase());
        borderBarBottom();
    }

    public void headerLineWithSpacing(String lineInfo1, String lineInfo2, String lineInfo3){
        int whiteSpace = width;
        whiteSpace -= lineInfo1.length();
        whiteSpace -= lineInfo2.length();
        whiteSpace -= lineInfo3.length();
        String spacesOnLine = "";
        for(int i = 0; i < whiteSpace/2; i ++){
            spacesOnLine += " ";
        }
        io.print( lineInfo1 + spacesOnLine + lineInfo2 + spacesOnLine + lineInfo3);
    }

    public void lineWithSpacing(String lineInfo1, String lineInfo2){
        int whiteSpace = width;
        whiteSpace -= lineInfo1.length();
        whiteSpace -= lineInfo2.length();
        String spacesOnLine = "";
        for(int i = 0; i < whiteSpace; i ++){
            spacesOnLine += " ";
        }
        io.print( lineInfo1 + spacesOnLine + lineInfo2);
    }

  
    public int printMenu(){
        displayMessageBanner("WELCOME TO DOLLARSBANK");
        for(int i = 0; i < MAIN_MENU_OPTIONS.length; i++){
            io.print("|" + (i+1) + "| " + MAIN_MENU_OPTIONS[i].toUpperCase());
        }
        borderline();
        return io.readInt(1, MAIN_MENU_OPTIONS.length);
    }

    public int userOptions(String username){
        displayMessageBanner("LOGGED IN AS: " + username.toUpperCase());
        for(int i = 0; i < CUSTOMER_MENU_OPTIONS.length; i++){
             io.print("|" + (i+1) + "| " + CUSTOMER_MENU_OPTIONS[i].toUpperCase());
        }
        borderline();
        return io.readInt(1, CUSTOMER_MENU_OPTIONS.length);
    }

	public void exitMessage() {
        displayMessageBanner("GOODBYE");
    }
    
    public String readStringInput(String prompt){
        return io.readString(prompt);
    }

  
    public AccountType addAccount(AccountType[] accounts){
        borderLineNoExtraSpace();
        System.out.println("WHAT TYPE OF ACCOUNT WOULD YOU LIKE TO ADD?");
        borderLineNoExtraSpace();
        for(int i = 0; i < accounts.length; i++){
            System.out.println((i+1) + " " + accounts[i]);
        }
        int choice = io.readInt(1, accounts.length);
        return (accounts[choice - 1]);
    }

    public BigDecimal getUserAmount(String prompt){
        return new BigDecimal(io.readDouble(prompt)).setScale(2, RoundingMode.HALF_UP);
    }


	public void displayCustomerInfo(Customer customer) {
        displayMessageBanner("CUSTOMER INFO");
        lineWithSpacing("USERNAME: " + customer.getUsername().toUpperCase(), "CUSTOMER ID: " + customer.getCustomerId());
        for(Account account : customer.getAccounts()){
            displayAccountInfo(account);
        }
    }
    
    public void displayAccountInfo(Account account){
        displayMessageBanner("ACCOUNTS");
        headerLineWithSpacing("ACCOUNT TYPE: " + account.getAccountType(), "ACCOUNT NUMBER: " + account.getAccountNumber(), "BALANCE: $" + account.getBalance());
        borderline(); 
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
        displayMessageBanner("LOGIN");
        String username = io.readString("USERNAME: ");
        String password = io.readString("PASSWORD: ");
        String[] loginInfo = {username, password};

        return loginInfo;
    }

    public int accountSelect(List<Account> accounts){
        displayMessageBanner("ACCOUNT SELECT");
        Account account;
        for(int i = 0; i < accounts.size(); i ++){
            account = accounts.get(i);
            lineWithSpacing("|" + (i+1) + "| " + account.getAccountType() + " ACCOUNT NUMBER: " + 
            account.getAccountNumber(), "BALANCE: $" + account.getBalance());
        }
        System.out.println("|" + (accounts.size() + 1 ) + "| "  + "RETURN TO MENU");

        return io.readInt(1, (accounts.size() + 1));
        
    }

    public int accountToTransfer(List<Account> accounts){
        displayMessageBanner("ACCOUNT SELECT");
        Account account;
        for(int i = 0; i < accounts.size(); i ++){
            account = accounts.get(i);
            lineWithSpacing("|" + (i+1) + "| " + account.getAccountType() + " ACCOUNT NUMBER: " + 
            account.getAccountNumber(), "BALANCE: $" + account.getBalance());
        }

        return io.readInt(1, (accounts.size()));
        
    }

    public void printTransactions(List<String> transactions){
        displayMessageBanner("RECENT TRANSACTIONS");

        for(String transaction : transactions){
            System.out.println(transaction);
        }
    }

	public String getTransferCustomerUsername() {
        return io.readString("ENTER RECIPIENT USERNAME: ");
	}

	public void printPendingTransfer(PendingTransfer pendingTransfer) {
        lineWithSpacing("YOU HAVE A PENDING TRANSFER FROM: " + pendingTransfer.getSendingAccount().getCustomerid(), "FOR $" + pendingTransfer.getTransferAmount());
	}
  
    public void printWarning(String message){
        borderLineNoExtraSpace();
        io.print(ANSI_RED + message + ANSI_RESET);
        borderLineNoExtraSpace();
    }
}
