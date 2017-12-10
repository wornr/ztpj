package pl.zut.edu.ztpj.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import pl.zut.edu.ztpj.db.dao.IDaoFactory;
import pl.zut.edu.ztpj.db.dao.implementation.mysql.DaoMySql;

public class DBMS {
    
    protected DBMS() {}
    private static DBMS instance = null;
    public static DBMS getInstance() {
        if (instance == null) {
            instance = new DBMS();
        }
        
        return instance;
    }
    
    private Connection connection = null;
    private IDaoFactory dao = null;
        
    public Connection getConnection() {

        try {
            if (connection == null || connection.isClosed()) {
                Properties connectionProps = new Properties();
                connectionProps.put("user", Configuration.getInstance().getProperty("db.user"));
                connectionProps.put("password", Configuration.getInstance().getProperty("db.password"));

                connection = DriverManager.getConnection(
                "jdbc:" +
                Configuration.getInstance().getProperty("db.jdbc") +
                "://" +
                Configuration.getInstance().getProperty("db.host") +
                ":" +
                Configuration.getInstance().getProperty("db.port") +
                "/" +
                Configuration.getInstance().getProperty("db.name"),
                connectionProps);

                connection.setAutoCommit(false);
            }
        } catch (SQLException ex) {
                ex.printStackTrace();//return null; // TODO handle error
            }
        
        return connection;
    }
    
    public IDaoFactory getDao() {
        if (dao == null) {
            switch (Configuration.getInstance().getProperty("db.jdbc")) {
                case "mysql":
                    dao = new DaoMySql();
                    break;
            }
        }
        
        return dao;
    }
}
