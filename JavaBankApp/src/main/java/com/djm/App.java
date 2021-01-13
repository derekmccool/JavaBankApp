package com.djm;

import com.djm.controller.BankController;
import com.djm.dao.CustomerDao;
import com.djm.dao.CustomerDaoImpl;
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
        CustomerDao dao = new CustomerDaoImpl();
        BankController controller = new BankController(view, dao);

        controller.bankRunner();
    }
}
