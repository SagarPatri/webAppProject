/**
 * @ (#) CommonDAOFactory.java Sep 21, 2005
 * Project      : 
 * File         : CommonDAOFactory.java
 * Author       : Nagaraj D V
 * Company      : 
 * Date Created : Sep 21, 2005
 *
 * @author       :  Nagaraj D V
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
package com.ttk.dao.impl.common;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;
import com.ttk.dao.impl.usermanagement.LoginDAOImpl;

public class CommonDAOFactory extends DAOFactory {
    
    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException{
        BaseDAO baseDAO = null;
        if(strIdentifier.equals("cache"))
        {
            baseDAO = new CacheDAOImpl();
        }//end of if(strIdentifier.equals("cache"))
        else if(strIdentifier.equals("loginuser"))
        {
            baseDAO = new LoginDAOImpl();
        }//end of else if(strIdentifier.equals("loginuser"))
        return baseDAO;
    }//end of getDAO(String strIdentifier)

}//end of CommonDAOFactory
