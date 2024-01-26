/**
 * @ (#) MisDAOFactory.java Jul 31, 2007
 * Project 	     : TTK HealthCare Services
 * File          : MisDAOFactory.java
 * Author        : RamaKrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Jul 31, 2007
 *
 * @author       :  RamaKrishna K M
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.dao.impl.misreports;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;

public class MisDAOFactory extends DAOFactory{
	
	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException{
    	BaseDAO baseDAO = null;
    	
    	if (strIdentifier.equals("imageenquiry"))
            baseDAO = new ImageEnquiryDAOImpl();
    	else if (strIdentifier.equals("misreport"))
            baseDAO = new MisReportDAOImpl();
    	else if(strIdentifier.equals("ttkinsdoboselect"))
            baseDAO = new TTKInsDoBOSelectDAOImpl();
    	else if(strIdentifier.equals("paymentadvice"))
            baseDAO = new MISPaymentAdviceDAOImpl();
    	return baseDAO;
    }//end of getDAO(String strIdentifier)
    
}//end of MisDAOFactory
