/**
 * @ (#) WebServiceDAOFactory.java Jun 14, 2006
 * Project       : TTK HealthCare Services
 * File          : WebServiceDAOFactory.java
 * Author        :
 * Company       : Span Systems Corporation
 * Date Created  : Jun 14, 2006
 * @author       : Krishna K. H
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.dao.impl.webservice;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class WebServiceDAOFactory extends DAOFactory {

	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	public BaseDAO getDAO(String strIdentifier) throws TTKException {
		BaseDAO baseDAO = null;
		if (strIdentifier.equals("policy"))
		{
			baseDAO = new WebServiceDAOImpl();
		}//end of if (strIdentifier.equals("policy"))
     	return baseDAO;
	}// End of getDAO(String strIdentifier)

}// End of WebServiceDAOFactory
