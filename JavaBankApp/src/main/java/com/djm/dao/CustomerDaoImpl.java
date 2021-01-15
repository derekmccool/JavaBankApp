package com.djm.dao;

import java.util.HashMap;
import java.util.Map;

import com.djm.model.Account;
import com.djm.model.AccountType;
import com.djm.model.Customer;

public class CustomerDaoImpl implements CustomerDao {

    Map<String, Customer> customers = new HashMap<>();

    public CustomerDaoImpl(){
        this.seedData();
    }
    
    @Override
    public Customer addCustomer(Customer customer) {
        
        try{
            return customers.put(customer.getUsername(), customer);
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public Customer getUserByUsername(String username) {
        
        try{
            return customers.get(username);
        }catch(Exception e){
            return null;
        }
    }
    
    public void seedData(){
        for(int i = 0; i < 5; i++){
            String username = "dummy" + i;
            Customer newCustomer = new Customer(username, "123");
            newCustomer.addAccount(new Account(newCustomer.getCustomerId(), 500, AccountType.CHECKING));
            customers.put(username, newCustomer);
            System.out.println(getUserByUsername(username).getUsername());
        }
    }
}
