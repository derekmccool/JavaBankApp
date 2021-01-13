package com.djm.controller;

import com.djm.model.Account;
import com.djm.model.AccountType;
import com.djm.model.Customer;
import com.djm.view.ConsoleView;

public class BankController {
    
    private ConsoleView view;

    public BankController(ConsoleView view){
        this.view = view;
    }

    final String[] OPTIONS = {"Create New Account", "Login", "Exit"};
    public boolean keepRunning = true;


    public void bankRunner(){
        view.printWelcome();
        displayMenu();

    }

    public void displayMenu(){

        switch(view.printMenu(OPTIONS)){

            case 1:
                    createUser();
                    break;
            case 2:
                    System.out.println("Login Goes Here...");
                    displayMenu();
                    break;
            case 3:
                    exitBankApp();
                    break;
        }
    }

    public void exitBankApp(){
            view.exitMessage();
            System.exit(0);;
    }

    public void createUser(){
        boolean accountInvalid = true;
        String [] accountInfo;

        do{
            accountInfo = view.createAccount();
            String username = accountInfo[0];
            String password = accountInfo[1];
            String confirmPass = accountInfo[2];
            if(password.equals(confirmPass)){
                Customer customer = new Customer(username, password);
                addAccount(customer);
                view.confirmNewAccount(customer);
                accountInvalid = false;
             
            }else{
                System.out.println("Password does not match");
            }
        }while(accountInvalid);

    }

    public void addAccount(Customer customer){

        AccountType[] accountTypes = AccountType.values();
        boolean continueAdding = true;

        do{
            Account newAccount = new Account();
            boolean infoConfirmed = true;

            do{
                newAccount.setAccountType(view.addAccount(accountTypes));
                System.out.println("New account type: " + newAccount.getAccountType());
                newAccount.setBalance(view.initialDeposit(newAccount));
                view.displayAccountInfo(newAccount);
                infoConfirmed = view.yesNoAnswer("Is this information correct?");
            }while(!infoConfirmed);

            customer.addAccount(newAccount);
            continueAdding = view.yesNoAnswer("Add additional accounts?");

        }while(continueAdding);

    }
}
