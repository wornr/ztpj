package pl.zut.edu.ztpj.db.dao.implementation.mysql;

import pl.zut.edu.ztpj.db.dao.IDaoFactory;
import pl.zut.edu.ztpj.db.dao.interfaces.IDaoDirector;
import pl.zut.edu.ztpj.db.dao.interfaces.IDaoTrader;

public class DaoMySql implements IDaoFactory {

	@Override
	public IDaoDirector getDaoDirector() {
		return new DaoDirector();
	}
	
	@Override
	public IDaoTrader getDaoTrader() {
		return new DaoTrader();
	}
}
