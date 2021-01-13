package com.djm.dao;

import com.djm.model.Account;
import com.djm.model.Customer;

public interface CustomerDao {
    
    Customer addCustomer(Customer customer);

    Customer getUserByUsername(String username);

    Customer updatePassword(String password);

    Account updateBalance(int accountId, double newBalance);

}
