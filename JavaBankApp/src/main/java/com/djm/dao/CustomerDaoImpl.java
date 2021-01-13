package com.djm.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.djm.model.Account;
import com.djm.model.Customer;

public class CustomerDaoImpl implements CustomerDao {

    Map<String, Customer> customers = new HashMap<>();

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

    @Override
    public Customer updatePassword(String password) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Account updateBalance(int accountId, double newBalance) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
