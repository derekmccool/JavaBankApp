package com.djm;

import com.djm.controller.BankController;
import com.djm.dao.CustomerDaoImpl;
import com.djm.service.CustomerService;
import com.djm.view.ConsoleIO;
import com.djm.view.ConsoleView;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ConsoleIO io = new ConsoleIO();
        ConsoleView view = new ConsoleView(io);
        CustomerDaoImpl dao = new CustomerDaoImpl();
        CustomerService service = new CustomerService(dao);
        BankController controller = new BankController(view, service);

        controller.bankRunner();
    }
}
