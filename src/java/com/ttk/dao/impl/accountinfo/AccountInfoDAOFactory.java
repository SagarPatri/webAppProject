/**
 * @ (#) AccountInfoDAOFactory.java Jul 26, 2007
 * Project 	     : TTK HealthCare Services
 * File          : AccountInfoDAOFactory.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 26, 2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

package com.ttk.dao.impl.accountinfo;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;



public class AccountInfoDAOFactory extends DAOFactory{

	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException{
        BaseDAO baseDAO = null;
        if (strIdentifier.equals("accountinfo"))
        {
            baseDAO = new AccountInfoDAOImpl();
        }//end of if (strIdentifier.equals("accountinfo"))
        return baseDAO;
    }//end of getDAO(String strIdentifier)

}//end of AccountInfoDAOFactory
