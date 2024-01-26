/**
 * @ (#) OnlineAccessDAOFactory.java Jul 19, 2007
 * Project 	     : TTK HealthCare Services
 * File          : OnlineAccessDAOFactory.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 19, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dao.impl.onlineforms;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;
import com.ttk.dao.impl.onlineforms.insuranceLogin.InsCorpDAOImpl;
import com.ttk.dao.impl.onlineforms.providerLogin.OnlineProviderDAOImpl;


public class OnlineAccessDAOFactory extends DAOFactory{
	
	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException{
        BaseDAO baseDAO = null;
        if (strIdentifier.equals("onlineforms"))
        {
            baseDAO = new OnlineAccessDAOImpl();
        }//end of if (strIdentifier.equals("onlineforms"))
        
        else if (strIdentifier.equals("insLoginCorp"))
        {
            baseDAO = new InsCorpDAOImpl();
        }//end of if (strIdentifier.equals("insLoginCorp"))
        else if (strIdentifier.equals("providerLogin"))
        {
            baseDAO = new OnlineProviderDAOImpl();
        }//end of if (strIdentifier.equals("providerLogin"))
        
        return baseDAO;
    }//end of getDAO(String strIdentifier)
    
    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getPreAuthDAO(String strIdentifier) throws TTKException{
        BaseDAO baseDAO = null;
        if (strIdentifier.equals("onlinepreauth"))
        {
            baseDAO = new OnlinePreAuthDAOImpl();
        }//end of if (strIdentifier.equals("onlinepreauth"))
        return baseDAO;
    }//end of getPreAuthDAO(String strIdentifier)
    
    
    

}//end of OnlineAccessDAOFactory
