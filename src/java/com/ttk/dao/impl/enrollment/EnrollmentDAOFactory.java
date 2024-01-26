/**
 * @ (#) EnrollmentDAOFactory.java Jan 31, 2006
 * Project       : TTK HealthCare Services
 * File          : EnrollmentDAOFactory.java
 * Author        : 
 * Company       : Span Systems Corporation
 * Date Created  : Jan 31, 2006
 * @author       : Suresh.M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dao.impl.enrollment;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class EnrollmentDAOFactory extends DAOFactory {
	
	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @param strIdentifier String object identifier for the required data access object
	 * @return BaseDAO object
	 * @exception throws TTKException
	 */
	public BaseDAO getDAO(String strIdentifier) throws TTKException {
		BaseDAO baseDAO = null;
		if (strIdentifier.equals("batch"))
		{
			baseDAO = new BatchDAOImpl();
		}//end of if (strIdentifier.equals("batch"))
        else if(strIdentifier.equals("enrollment"))
        {
            baseDAO = new PolicyDAOImpl();
        }//end of else if(strIdentifier.equals("enrollment"))
        else if(strIdentifier.equals("member"))
        {
            baseDAO = new MemberDAOImpl();
        }//end of else if(strIdentifier.equals("member"))
        else if(strIdentifier.equals("cardPrint"))
        {
            baseDAO = new CardPrintingDAOImpl();
        }//end of else if(strIdentifier.equals("cardPrint"))
        else if(strIdentifier.equals("courier"))
        {
            baseDAO = new CourierDAOImpl();
        }//end of else if(strIdentifier.equals("courier"))
    	return baseDAO;
	}// End of getDAO(String strIdentifier)
	
}// End of EnrollmentDAOFactory
