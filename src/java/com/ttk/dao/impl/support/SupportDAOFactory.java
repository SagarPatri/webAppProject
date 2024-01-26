/**
 * @ (#) SupportDAOFactory.java Mar 14, 2008
 * Project 	     : TTK HealthCare Services
 * File          : SupportDAOFactory.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Mar 14, 2008
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dao.impl.support;

//import org.apache.log4j.Logger;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class SupportDAOFactory extends DAOFactory{
	
//	private static Logger log = Logger.getLogger(SupportDAOFactory.class );
	
	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException {
        BaseDAO baseDAO = null;
        if (strIdentifier.equals("support"))
        {
            baseDAO = new SupportDAOImpl();
        }//end of if (strIdentifier.equals("support"))
        return baseDAO;
    }//end of getDAO(String strIdentifier)

}//end of SupportDAOFactory
