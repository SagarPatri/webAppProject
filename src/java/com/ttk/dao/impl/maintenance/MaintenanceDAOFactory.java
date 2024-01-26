/**
 * @ (#) MaintenanceDAOFactory.java Oct 22, 2007
 * Project 	     : TTK HealthCare Services
 * File          : MaintenanceDAOFactory.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Oct 22, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dao.impl.maintenance;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class MaintenanceDAOFactory extends DAOFactory{
	
	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException {
        BaseDAO baseDAO = null;
        if (strIdentifier.equals("maintenance"))
        {
            baseDAO = new MaintenanceDAOImpl();
        }//end of if (strIdentifier.equals("maintenance"))
        return baseDAO;
    }//end of getDAO(String strIdentifier)
}//end of MaintenanceDAOFactory
