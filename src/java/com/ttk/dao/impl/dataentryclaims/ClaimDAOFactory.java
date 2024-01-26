/**
 * @ (#) ClaimDAOFactory.java Jul 15, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ClaimDAOFactory.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 15, 2006
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dao.impl.dataentryclaims;

import org.apache.log4j.Logger;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class ClaimDAOFactory extends DAOFactory{
	
	private static Logger log = Logger.getLogger(ClaimDAOFactory.class );
	
	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException {
        BaseDAO baseDAO = null;
        log.info("Inside ClaimDAOFactory: "+strIdentifier);
        
        if (strIdentifier.equals("claim"))
        {
            baseDAO = new ClaimDAOImpl();
        }//end of if (strIdentifier.equals("claim"))
        else if (strIdentifier.equals("bill"))
        {
            baseDAO = new ClaimBillDAOImpl();
        }//end of else if (strIdentifier.equals("bill"))
       /* else if (strIdentifier.equals("cashbenefit"))
        {
            baseDAO = new ClaimBenefitDAOImpl();
        }//end of else if (strIdentifier.equals("cashbenefit"))
        //KOC for 1339 MAIL READER
        else if (strIdentifier.equals("claimintimation"))
        {
            baseDAO = new ClaimIntimationSmsDAOImpl();
        }//end of else if (strIdentifier.equals("claimintimation"))
*/      //KOC for 1339 MAIL READER
        log.info("Returning : "+baseDAO);
        return baseDAO;
    }//end of getDAO(String strIdentifier)

}//end of ClaimDAOFactory
