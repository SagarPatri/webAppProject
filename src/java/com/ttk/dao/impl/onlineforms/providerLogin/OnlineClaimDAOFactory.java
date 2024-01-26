package com.ttk.dao.impl.onlineforms.providerLogin;

/**
 * @ (#) OnlineClaimDAOFactory.java May 22 2017
 * Project 	     : RCS TEch
 * File         	 : OnlineClaimDAOFactory.java
 * Author        : Kishor kumar S H
 * Company       : RCS TEch
 * Date Created  : May 22 2017
 *
 * @author       :  Kishor kumar S H
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */


//import org.apache.log4j.Logger;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;
import com.ttk.dao.impl.onlineforms.providerLogin.ClaimsDAOImpl;

public class OnlineClaimDAOFactory extends DAOFactory{
    
//    private static Logger log = Logger.getLogger(OnlineClaimDAOFactory.class );
    
    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException {
        BaseDAO baseDAO = null;
        if (strIdentifier.equals("providerClaim"))
        {
        	baseDAO = new ClaimsDAOImpl();
        }//end of else if (strIdentifier.equals("providerClaim"))
        return baseDAO;
    }//end of getDAO(String strIdentifier)

}//end of OnlineClaimDAOFactory
