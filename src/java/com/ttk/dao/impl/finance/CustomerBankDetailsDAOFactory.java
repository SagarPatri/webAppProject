package com.ttk.dao.impl.finance;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;
/**
 * This class is added for cr koc 1103
 * added eft
 */
public class CustomerBankDetailsDAOFactory extends DAOFactory {

	@Override
	public BaseDAO getDAO(String strIdentifier) throws TTKException {
		BaseDAO baseDAO = null;
		if (strIdentifier.equals("bankcity"))
		{
			baseDAO = new CustomerBankDetailsDAOImpl();
		}//end of if (strIdentifier.equals("bank"))\
		return baseDAO;
	}

}
