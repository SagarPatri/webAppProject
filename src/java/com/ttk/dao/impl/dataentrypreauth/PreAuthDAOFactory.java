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

package com.ttk.dao.impl.dataentrypreauth;

//import org.apache.log4j.Logger;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class PreAuthDAOFactory extends DAOFactory{
    
//    private static Logger log = Logger.getLogger(PreAuthDAOFactory.class );
    
    /**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException {
        BaseDAO baseDAO = null;
        if (strIdentifier.equals("preauth"))
        {
            baseDAO = new PreAuthDAOImpl();
        }//end of if (strIdentifier.equals("preauth"))
      /*  else if (strIdentifier.equals("support"))
        {
            baseDAO = new PreAuthSupportDAOImpl();
        }//end of else if (strIdentifier.equals("support"))
        else if (strIdentifier.equals("history"))
        {
            baseDAO = new MemberHistoryDAOImpl();
        }//end of else if (strIdentifier.equals("history"))
        else if (strIdentifier.equals("rule"))
        {
            baseDAO = new RuleDAOImpl();
        }//end of else if (strIdentifier.equals("rule"))
       */ else if (strIdentifier.equals("medical"))
        {
            baseDAO = new PreAuthMedicalDAOImpl();
        }//end of else if (strIdentifier.equals("medical"))
        /*else if (strIdentifier.equals("policyrules"))
        {
        	baseDAO = new RuleDAOImpl();
        }//end of else if (strIdentifier.equals("policyrules"))
        */return baseDAO;
    }//end of getDAO(String strIdentifier)

}//end of PreAuthDAOFactory
