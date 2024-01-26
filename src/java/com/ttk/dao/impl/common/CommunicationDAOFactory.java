/**
 * @ (#) CommunicationDAOFactory.java Dec 21, 2006
 * Project      :
 * File         : CommunicationDAOFactory.java
 * Author       : Balakrishna E
 * Company      :
 * Date Created : Dec 21, 2006
 *
 * @author       :  Balakrishna E
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.dao.impl.common;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class CommunicationDAOFactory extends DAOFactory {

    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException{
        BaseDAO baseDAO = null;
        if(strIdentifier.equals("communication"))
        {
            baseDAO = new CommunicationDAOImpl();
        }//end of if(strIdentifier.equals("communication"))
        return baseDAO;
    }//end of getDAO(String strIdentifier)

}//end of CommunicationDAOFactory
