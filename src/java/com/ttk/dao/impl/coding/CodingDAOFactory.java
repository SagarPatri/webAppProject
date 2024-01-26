/**
 * @ (#) PreAuthDAOFactory.java Apr 10, 2006
 * Project 	     : TTK HealthCare Services
 * File          : PreAuthDAOFactory.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Apr 10, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dao.impl.coding;

//import org.apache.log4j.Logger;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class CodingDAOFactory extends DAOFactory{
    
//    private static Logger log = Logger.getLogger(CodingDAOFactory.class );
    
    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException {
        BaseDAO baseDAO = null;
        if (strIdentifier.equals("coding"))
        {
            baseDAO = new CodingDAOImpl();
        }//end of if (strIdentifier.equals("coding"))
        return baseDAO;
    }//end of getDAO(String strIdentifier)
    
    

}//end of PreAuthDAOFactory
