/**
  * @ (#) ReportCommonDAOFactory.java May 30, 2007
  * Project      : TTK HealthCare Services
  * File         : ReportCommonDAOFactory.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : 
  *
  * @author       :  Ajay Kumar
  * Modified by   :
  * Modified date :
  * Reason        :
  */

package com.ttk.dao.impl.common.misreports;

import org.apache.log4j.Logger;

import com.ttk.common.exception.TTKException;
import com.ttk.dao.BaseDAO;
import com.ttk.dao.factory.DAOFactory;
import com.ttk.dao.impl.usermanagement.LoginDAOImpl;

public class ReportCommonDAOFactory extends DAOFactory {
	
	private static Logger log = Logger.getLogger( ReportCommonDAOFactory.class );
	
	/**
     * This method returns the instance of the data access object to initiate the required tasks
     * @param strIdentifier String object identifier for the required data access object
     * @return BaseDAO object
     * @exception throws TTKException
     */
    public BaseDAO getDAO(String strIdentifier) throws TTKException{
        BaseDAO baseDAO = null;
        if(strIdentifier.equals("cache"))
            baseDAO = new ReportCacheDAOImpl();
        else if(strIdentifier.equals("loginuser"))
            baseDAO = new LoginDAOImpl();
        log.info("Returning : "+baseDAO);
        return baseDAO;
    }//end of getDAO(String strIdentifier)

}//end of ReportCommonDAOFactory
