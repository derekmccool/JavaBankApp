package com.djm.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.djm.exception.CustomerNameTakenException;
import com.djm.exception.CustomerNotFoundException;
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

    public void exitBankApp() {
        view.exitMessage();
        System.exit(0);
    }

    public void createCustomer() {
        boolean accountCreated = false;
        String username = createUsername();
        String password = createPassword();

        while (!accountCreated) {
            try {
                service.createCustomer(username, password);
                currentCustomer = service.getCustomerByUsername(username);
                accountCreated = true;
                addAccount();
            } catch (CustomerNameTakenException e) {
                view.printWarning(e.getMessage().toUpperCase());
                accountCreated = view.yesNoAnswer("TRY AGAIN?");
            } catch (CustomerNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public String createUsername() {
        String username = "";
        boolean usernameUnique = false;
        int remainingAttempts = 3;
        List<String> usernameRequirements = new ArrayList<String>();

        do {

            do{
                username = view.readStringInput("USERNAME:");
                usernameRequirements = service.verifyUsername(username);
                if(!usernameRequirements.isEmpty()){
                    for (String requirement : usernameRequirements) {
                        view.printWarning(requirement);
                    }
                }
            }while(!usernameRequirements.isEmpty());

            usernameUnique = service.checkUsernameAvailable(username);
            if (!usernameUnique) {
                view.printWarning(username.toUpperCase() + " IS ALREADY IN USE.  PLEASE TRY AGAIN.");
                remainingAttempts--;
            }

            if (remainingAttempts == 0) {
                view.printWarning("SUSPICIOUS ACTIVITY DETECTED.");
                exitBankApp();
            }
        } while (!usernameUnique);

        return username;
    }

    public String createPassword() {

        String password = "";
        String confirmPass = "";
        boolean passwordConfirmed = false;

        List<String> passwordRequirements = new ArrayList<String>();

        while (!passwordConfirmed) {
            do {
                password = view.readStringInput("PASSWORD:");
                passwordRequirements = service.verifyPassword(password);
                if (!passwordRequirements.isEmpty()) {
                    for (String requirement : passwordRequirements) {
                        view.printWarning(requirement);
                    }
                }
            } while (!passwordRequirements.isEmpty());

            do {
                confirmPass = view.readStringInput("CONFIRM PASSWORD:");
                passwordConfirmed = password.equals(confirmPass);
            } while (!passwordConfirmed);
        }

        return password;
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

        try {
            service.addAccountToCustomer(currentCustomer, newAccount);
        } catch (MaximumAccountsReachedException e) {
            view.printWarning(e.getMessage().toUpperCase());
        }

        userMenu();

    }

    public void login() {

        String username = view.readStringInput("USERNAME:");
        String password = view.readStringInput("PASSWORD:");
        Customer customer = null;
        while(loginAttempts > 0){
            try {
                customer = service.getCustomerByUsername(username);
                if (service.correctPassword(customer, password)) {
                    currentCustomer = customer;
                    updateViewSettings();
                    userMenu();
                } else {
                    loginAttempts--;
                    view.printWarning("USERNAME AND PASSWORD DO NOT MATCH");
                    view.printWarning("LOGIN ATTEMPTS REMAININ: " + loginAttempts);
                    displayMenu();
                }
            } catch (CustomerNotFoundException e) {
                loginAttempts--;
                view.printWarning(e.getMessage().toUpperCase());
                view.printWarning("LOGIN ATTEMPTS REMAININ: " + loginAttempts);
    
            }
        }

        if (loginAttempts == 0) {
            view.printWarning("TOO MANY LOGIN ATTEMPTS");
            exitBankApp();
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
        BigDecimal deposit;
        if (choice == (currentCustomer.getAccounts().size() + 1)) {
            userMenu();
        } else {
            Account account =  currentCustomer.getAccounts().get(choice - 1);
            deposit = view.getUserAmount("DEPOSIT AMOUNT: $");
            if (deposit.compareTo(BigDecimal.ZERO) > -1) {
                service.depositToAccount(currentCustomer, account, deposit);
                view.displayMessageBanner("DEPOSITED $" + deposit + " TO ACCOUNT NUMBER:" + account.getAccountNumber());
                userMenu();
            } else {
                view.printWarning("DEPOSIT CANNOT BE A NEGATIVE NUMBER");
                depositMenu();
            }
        }
    }

    public void withdrawalMenu() {
        int choice = view.accountSelect(currentCustomer.getAccounts());
        BigDecimal withdrawal;;
        if (choice == (currentCustomer.getAccounts().size() + 1)) {
            userMenu();
        } else {
            Account account =  currentCustomer.getAccounts().get(choice - 1);
            withdrawal = view.getUserAmount("WITHDRAWAL AMOUNT: $");
            if (withdrawal.compareTo(BigDecimal.ZERO) > 0) {
                try {
                    service.withdrawFromAccount(currentCustomer, account, withdrawal);
                    view.displayMessageBanner("DEPOSITED $" + withdrawal + " TO ACCOUNT NUMBER:" + account.getAccountNumber());
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
        BigDecimal withdrawal;
        if (choice == (currentCustomer.getAccounts().size() + 1)) {
            userMenu();
        } else {
            Account account =  currentCustomer.getAccounts().get(choice - 1);
            Customer transferCustomer;
            try {
                transferCustomer = service.getCustomerByUsername(view.getTransferCustomerUsername());
                withdrawal = view.getUserAmount("TRANSFER AMOUNT: $");
                if (withdrawal.compareTo(BigDecimal.ZERO) > -1) {
                    try {
                        service.sendTransfer(currentCustomer, transferCustomer, account, withdrawal);
                        userMenu();
                    } catch (InsufficientFundsExeption e) {
                        view.printWarning(e.getMessage().toUpperCase());
                    }
                    userMenu();
                }else{
                    view.printWarning("WITHDRAWAL MUST BE A GREATER THAN $0.00");
                    depositMenu();
                }
            } catch (CustomerNotFoundException e) {
                view.printWarning(e.getMessage().toUpperCase());
                transferMenu();
            }
            
        }

    }

    public void changePassword(){
        int remainingAttempts = 3;
        String password = "";
        String newPassword = "";
        while(remainingAttempts > 0){
            password = view.readStringInput("CURRENT PASSWORD:");
            if(service.correctPassword(currentCustomer, password)){
                view.displayMessageBanner("UPDATE PASSWORD");
                newPassword = createPassword();
                if(service.updatePassword(currentCustomer, password, newPassword)){
                    view.displayMessageBanner("PASSWORD UPDATED");
                    break;
                }else{
                    remainingAttempts--;
                }
            }else{
                remainingAttempts--;
            }
            if(remainingAttempts == 0){
                view.printWarning("SUSPICIOUS ACTIVITY DETECTED.");
                exitBankApp();
            }
        }
        userMenu();
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

    public void updateViewSettings(){
        view.setUserSettings("*", "+", "_", 100);
    }

}


