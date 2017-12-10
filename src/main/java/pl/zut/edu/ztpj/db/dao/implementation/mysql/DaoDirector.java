package pl.zut.edu.ztpj.db.dao.implementation.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.zut.edu.ztpj.config.DBMS;
import pl.zut.edu.ztpj.db.dao.interfaces.IDaoDirector;
import pl.zut.edu.ztpj.db.dto.Director;
import pl.zut.edu.ztpj.db.dto.Employee;

public class DaoDirector implements IDaoDirector {

    @Override
    public Director getDirector(Integer id) {
        String sql = "SELECT * FROM employees WHERE position = ? AND id = ?";
        
        try (Connection conn = DBMS.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "Director");
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
    public List<Director> getDirectors() {
        List<Director> result = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE position = ?";
        
        try (Connection conn = DBMS.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "Director");
            
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
    public void insertDirector(Employee director) {
        String sql = "INSERT INTO employees (`firstName`, `lastName`, `salary`, `phoneNumber`, `position`, `salaryAddition`, `cardNumber`, `costsLimitPerMonth`) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBMS.getInstance().getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, director.getFirstName());
            ps.setString(2, director.getLastName());
            ps.setInt(3, director.getSalary());
            ps.setString(4, director.getPhoneNumber());
            ps.setString(5, director.getClass().getSimpleName());
            ps.setInt(6, ((Director)director).getSalaryAddition());
            ps.setString(7, ((Director)director).getCardNumber());
            ps.setInt(8, ((Director)director).getCostsLimitPerMonth());
                        
            ps.executeUpdate();
            
            conn.commit();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void updateDirector(Integer id, Employee director) {
    	
    }

    @Override
    public void removeDirector(Integer id) {
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
    
    private Director resultSetToObject(ResultSet rs) {
        Director object = new Director();
                
        try {
            object.setId(rs.getInt("id"));
            object.setFirstName(rs.getString("firstName"));
            object.setLastName(rs.getString("lastName"));
            object.setSalary(rs.getInt("salary"));
            object.setPhoneNumber(rs.getString("phoneNumber"));
            object.setSalaryAddition(rs.getInt("salaryAddition"));
            object.setCardNumber(rs.getString("cardNumber"));
            object.setCostsLimitPerMonth(rs.getInt("costsLimitPerMonth"));
        } catch (SQLException ex) {
            // TODO handle error
        }
        
        return object;
    }
}
