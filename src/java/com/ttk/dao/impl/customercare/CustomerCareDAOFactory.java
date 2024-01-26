/**
 * @ (#) CustomerCareDAOFactory.java August 21, 2006
 * Project 	     : TTK HealthCare Services
 * File          : CustomerCareDAOFactory.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : August 21, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dao.impl.customercare;
import org.apache.log4j.Logger;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class CustomerCareDAOFactory extends DAOFactory
{
	private static Logger log = Logger.getLogger(CustomerCareDAOFactory.class );
    
    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException {
        BaseDAO baseDAO = null;
        log.info("Inside CustomerCareDAOFactory: "+strIdentifier);
        if (strIdentifier.equals("callcenter"))
        {
            baseDAO = new CallCenterDAOImpl();
        }//end of if (strIdentifier.equals("callcenter"))
        log.info("Returning : "+baseDAO);
        return baseDAO;
    }//end of getDAO(String strIdentifier)	
}//end of CustomerCareDAOFactory