package pl.zut.edu.ztpj.db.dao.interfaces;

import java.util.List;

import pl.zut.edu.ztpj.db.dto.Employee;
import pl.zut.edu.ztpj.db.dto.Trader;

public interface IDaoTrader {
    
    public Trader getTrader(Integer id);
    public List<Trader> getTraders();
    public void insertTrader(Employee trader);
    public void updateTrader(Integer id, Employee trader);
    public void removeTrader(Integer id);
}
