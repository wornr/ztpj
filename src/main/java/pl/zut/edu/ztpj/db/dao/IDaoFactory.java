package pl.zut.edu.ztpj.db.dao;

import pl.zut.edu.ztpj.db.dao.interfaces.IDaoDirector;
import pl.zut.edu.ztpj.db.dao.interfaces.IDaoTrader;

public interface IDaoFactory {
    
    public IDaoDirector getDaoDirector();
    
    public IDaoTrader getDaoTrader();
}
