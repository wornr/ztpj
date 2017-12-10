package pl.zut.edu.ztpj;

import java.sql.SQLException;

public class Application {

    public static void main(String[] args) throws SQLException{
        EmployeeManagement employeeManagement = new EmployeeManagement();
        employeeManagement.menu();
        /*System.out.println("Directors:");
        for (Director director : DBMS.getInstance().getDao().getDaoDirector().getDirectors()) {
            System.out.println(director.getFirstName() + " " + director.getLastName());
        }
        
        System.out.println();
        
        System.out.println("Traders:");
        for (Trader trader : DBMS.getInstance().getDao().getDaoTrader().getTraders()) {
            System.out.println(trader.getFirstName() + " " + trader.getLastName());
        }*/
    }
}
