package com.djm.controller;

import java.time.LocalDate;
import java.util.List;

import com.djm.exception.CustomerNameTakenException;
import com.djm.exception.InsufficientFundsExeption;
import com.djm.exception.MaximumAccountsReachedException;
import com.djm.model.Account;
import com.djm.model.AccountType;
import com.djm.model.Customer;
import com.djm.model.PendingTransfer;
import com.djm.service.CustomerService;
import com.djm.view.ConsoleView;

public class BankController {

    private ConsoleView view;
    private CustomerService service;

    public BankController(ConsoleView view, CustomerService service) {
        this.view = view;
        this.service = service;
    }

    public boolean keepRunning = true;
    private Customer currentCustomer = null;
    private boolean loggedIn = false;
    private int loginAttempts = 3;

    public void bankRunner() {
        displayMenu();
    }

    public void displayMenu() {

        switch (view.printMenu()) {
            case 1:
                createCustomer();
                break;
            case 2:
                login();
                break;
            case 3:
                exitBankApp();
                break;
        }
    }

    public void exitBankApp() {
        view.exitMessage();
        System.exit(0);
    }

    public void createCustomer() {
        boolean accountInvalid = true;
        String[] customerInfo;
        do {
            customerInfo = view.createCustomer();
            String username = customerInfo[0];
            String password = customerInfo[1];
            String confirmPass = customerInfo[2];
            if (password.equals(confirmPass)) {
                try{
                    accountInvalid = false;
                    service.createCustomer(username, password);
                    currentCustomer = service.getCustomerByUsername(username);

                }catch(CustomerNameTakenException e){
                    System.out.println(e.getMessage());
                }
            } else {
                view.printWarning("PASSWORD DOES NOT MATCH");
            }
        } while (accountInvalid);
        addAccount();
    }

    public void addAccount() {
        AccountType[] accountTypes = AccountType.values();
        Account newAccount = new Account();
        boolean infoConfirmed = true;
        do {
            newAccount.setAccountType(view.addAccount(accountTypes));
            newAccount.setBalance(view.getUserAmount("INITIAL DEPOSIT AMOUNT: $"));
            view.displayAccountInfo(newAccount);
            infoConfirmed = view.yesNoAnswer("IS THIS INFORMATION CORRECT?");
        } while (!infoConfirmed);

        try{
            service.addAccountToCustomer(currentCustomer, newAccount);
        }catch(MaximumAccountsReachedException e){
            view.printWarning(e.getMessage().toUpperCase());
        }

        userMenu();

    }

    public void login() {

        String[] loginInfo = view.loginPrompt();
        Customer customer = service.getCustomerByUsername(loginInfo[0]);
        if (customer != null) {

            if (loginInfo[1].equals(customer.getPassword())) {
                currentCustomer = customer;
                loggedIn = true;
                userMenu();
            } else {
                loginAttempts--;
                view.printWarning("USERNAME AND PASSWORD DO NOT MATCH");
                view.printWarning("LOGIN ATTEMPTS REMAININ: " + loginAttempts);
                displayMenu();
            }
        } else {
            loginAttempts--;
            view.printWarning("USERNAME DOES NOT EXIST");
            view.printWarning("LOGIN ATTEMPTS REMAININ: " + loginAttempts);
            displayMenu();
        }

        if (loginAttempts == 0) {
            view.printWarning("TOO MANY LOGIN ATTEMPTS");
            exitBankApp();
        }

    }

    public void userMenu() {

        pendingTransfersPrompt();

        switch (view.userOptions(currentCustomer.getUsername())) {

            case 1:
                depositMenu();
                break;
            case 2:
                withdrawalMenu();
                break;
            case 3:
                transferMenu();
                break;
            case 4:
                view.displayCustomerInfo(currentCustomer);
                userMenu();
            case 5:
                changePassword();
                break;
            case 6:
                viewTransactions();
            case 7:
                addAccount();
            case 8:
                currentCustomer = null;
                displayMenu();

        }

    }

    private void viewTransactions() {
        List<String> transactions = currentCustomer.getTransactions();
        if(transactions.size() > 5){
            List<String> lastFive = transactions.subList(transactions.size() - 6, transactions.size() -1);
            view.printTransactions(lastFive);
        }
        else{
            view.printTransactions(transactions);
        }
        userMenu();
    }

    public void depositMenu() {
        int choice = view.accountSelect(currentCustomer.getAccounts());
        double deposit = 0.00;
        if (choice == (currentCustomer.getAccounts().size() + 1)) {
            userMenu();
        } else {
            Account account =  currentCustomer.getAccounts().get(choice - 1);
            deposit = view.getUserAmount("DEPOSIT AMOUNT: $");
            if (deposit > 0) {
                service.depositToAccount(currentCustomer, account, deposit);
                userMenu();
            } else {
                view.printWarning("DEPOSIT CANNOT BE A NEGATIVE NUMBER");
                depositMenu();
            }
        }
    }

    public void withdrawalMenu() {
        int choice = view.accountSelect(currentCustomer.getAccounts());
        double withdrawal = 0.00;
        if (choice == (currentCustomer.getAccounts().size() + 1)) {
            userMenu();
        } else {
            Account account =  currentCustomer.getAccounts().get(choice - 1);
            withdrawal = view.getUserAmount("WITHDRAWAL AMOUNT: $");
            if (withdrawal > 0) {
                try {
                    service.withdrawFromAccount(currentCustomer, account, withdrawal);
                    userMenu();
                } catch (InsufficientFundsExeption e) {
                    view.printWarning(e.getMessage().toUpperCase());
                }
                userMenu();
            }else{
                view.printWarning("WITHDRAWAL CANNOT BE A NEGATIVE NUMBER");
                depositMenu();
            }
        }
    }

    public void transferMenu(){
        int choice = view.accountSelect(currentCustomer.getAccounts());
        double withdrawal = 0.00;
        if (choice == (currentCustomer.getAccounts().size() + 1)) {
            userMenu();
        } else {
            Account account =  currentCustomer.getAccounts().get(choice - 1);
            Customer transferCustomer = service.getCustomerByUsername(view.getTransferCustomerUsername());
            withdrawal = view.getUserAmount("TRANSFER AMOUNT: $");
            if (withdrawal > 0) {
                try {
                    service.sendTransfer(currentCustomer, transferCustomer, account, withdrawal);
                    userMenu();
                } catch (InsufficientFundsExeption e) {
                    view.printWarning(e.getMessage().toUpperCase());
                }
                userMenu();
            }else{
                view.printWarning("WITHDRAWAL CANNOT BE A NEGATIVE NUMBER");
                depositMenu();
            }
        }

    }

    public void changePassword(){

    }

    public void pendingTransfersPrompt(){
        PendingTransfer pendingTransfer;
        while(!currentCustomer.getPendingTransfers().isEmpty()){
            for(int i = 0; i < currentCustomer.getPendingTransfers().size(); i ++){
                pendingTransfer = currentCustomer.getPendingTransfers().get(i);
                view.printPendingTransfer(pendingTransfer);
                if(view.yesNoAnswer("WOULD YOU LIKE TO ACCEPT THIS TRANSFER?")){
                    int choice = view.accountToTransfer(currentCustomer.getAccounts()) - 1;
                    Account account = currentCustomer.getAccounts().get(choice);
                    service.acceptTransfer(currentCustomer, account, pendingTransfer);
                }else{
                    service.denyTransfer(currentCustomer, pendingTransfer);
                }
            }
        }

    }

}


