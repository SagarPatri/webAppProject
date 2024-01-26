/**
 * @ (#) SecurityDAOFactory.java Dec 27, 2005
 * Project       : TTK HealthCare Services
 * File          : SecurityDAOFactory.java
 * Author        :
 * Company       : Span Systems Corporation
 * Date Created  : Dec 27, 2005
 * @author       : Suresh.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.dao.impl.security;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class SecurityDAOFactory extends DAOFactory {

	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	public BaseDAO getDAO(String strIdentifier) throws TTKException {
		BaseDAO baseDAO = null;
		if (strIdentifier.equals("security"))
		{
			baseDAO = new SecurityDAOImpl();
		}//end of if (strIdentifier.equals("security"))
		return baseDAO;
	}// End of getDAO(String strIdentifier)
}// End of SecurityDAOFactory
