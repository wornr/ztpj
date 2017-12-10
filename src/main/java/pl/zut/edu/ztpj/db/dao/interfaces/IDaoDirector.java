package pl.zut.edu.ztpj.db.dao.interfaces;

import java.util.List;

import pl.zut.edu.ztpj.db.dto.Director;
import pl.zut.edu.ztpj.db.dto.Employee;

public interface IDaoDirector {
    
    public Employee getDirector(Integer id);
    public List<Director> getDirectors();
    public void insertDirector(Employee director);
    public void updateDirector(Integer id, Employee director);
    public void removeDirector(Integer id);
}
