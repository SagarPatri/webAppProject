/**
 * @ (#) UserManagementDAOFactory.java Sep 21, 2005
 * Project      : 
 * File         : UserManagementDAOFactory.java
 * Author       : Nagaraj D V
 * Company      : 
 * Date Created : Sep 21, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dao.impl.usermanagement;

import org.apache.log4j.Logger;

import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class UserManagementDAOFactory extends DAOFactory {
    
    private static Logger log = Logger.getLogger( UserManagementDAOFactory.class );
    
    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) {
        BaseDAO baseDAO = null;
        log.info("Inside UserManagementDAOFactory: "+strIdentifier);
        if (strIdentifier.equals("user"))
        {
            baseDAO = new UserDAOImpl();
        }//end of if (strIdentifier.equals("user"))
        log.info("Returning : "+baseDAO);
        return baseDAO;
    }//end of getDAO(String strIdentifier)

}//end of class UserManagementDAOFactory
