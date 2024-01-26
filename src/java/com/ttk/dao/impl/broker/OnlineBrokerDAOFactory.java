/**
 *   @ (#) BrokerDAOFactory.java Dec 29, 2015
 *   Project 	   : Dubai Project
 *   File          : BrokerDAOFactory.java
 *   Author        : Nagababu K
 *   Company       : RCS
 *   Date Created  : Dec 29, 2015
 *
 *   @author       :  Nagababu K
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 *
 */

package com.ttk.dao.impl.broker;

//import org.apache.log4j.Logger;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class OnlineBrokerDAOFactory extends DAOFactory{
    
    //private static Logger log = Logger.getLogger(BrokerDAOFactory.class );
    
    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException {
        BaseDAO baseDAO = null;
        if (strIdentifier.equals("broker"))
        {
            baseDAO = new OnlineBrokerDAOImpl();
        }//end of if (strIdentifier.equals("broker"))
       
        return baseDAO;
    }//end of getDAO(String strIdentifier)

}//end of BrokerDAOFactory
