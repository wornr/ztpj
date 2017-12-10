package pl.zut.edu.ztpj.db.dao.implementation.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.zut.edu.ztpj.config.DBMS;
import pl.zut.edu.ztpj.db.dao.interfaces.IDaoTrader;
import pl.zut.edu.ztpj.db.dto.Employee;
import pl.zut.edu.ztpj.db.dto.Trader;

public class DaoTrader implements IDaoTrader {

    @Override
    public Trader getTrader(Integer id) {
        String sql = "SELECT * FROM employees WHERE position = ? AND id = ?";
        
        try (Connection conn = DBMS.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "Trader");
            ps.setInt(2, id);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                return resultSetToObject(rs);
            }
        } catch (SQLException ex) {
            // TODO handle error
        }
        
        return null;
    }

    @Override
    public List<Trader> getTraders() {
        List<Trader> result = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE position = ?";
        
        try (Connection conn = DBMS.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "Trader");
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                result.add(resultSetToObject(rs));
            }
        } catch (SQLException ex) {
            // TODO handle error
        }
        
        return result;
    }

    @Override
    public void insertTrader(Employee trader) {
        String sql = "INSERT INTO employees (`firstName`, `lastName`, `salary`, `phoneNumber`, `position`, `provision`, `provisionLimit`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBMS.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, trader.getFirstName());
            ps.setString(2, trader.getLastName());
            ps.setInt(3, trader.getSalary());
            ps.setString(4, trader.getPhoneNumber());
            ps.setString(5, trader.getClass().getSimpleName());
            ps.setInt(6, ((Trader)trader).getProvision());
            ps.setInt(7, ((Trader)trader).getProvisionLimit());
                        
            ps.executeUpdate();
            
            conn.commit();
        } catch (SQLException ex) {
            // TODO handle error
        }
    }
    
    @Override
    public void updateTrader(Integer id, Employee trader) {
    	
    }

    @Override
    public void removeTrader(Integer id) {
    	String sql = "DELETE FROM employees WHERE id = ?";
        
        try (Connection conn = DBMS.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
                        
            ps.executeUpdate();
            
            conn.commit();
        } catch (SQLException ex) {
            // TODO handle error
        }
    }
    
    private Trader resultSetToObject(ResultSet rs) {
        Trader object = new Trader();
                
        try {
            object.setId(rs.getInt("id"));
            object.setFirstName(rs.getString("firstName"));
            object.setLastName(rs.getString("lastName"));
            object.setSalary(rs.getInt("salary"));
            object.setPhoneNumber(rs.getString("phoneNumber"));
            object.setProvision(rs.getInt("provision"));
            object.setProvisionLimit(rs.getInt("provisionLimit"));
        } catch (SQLException ex) {
            // TODO handle error
        }
        
        return object;
    }
}
