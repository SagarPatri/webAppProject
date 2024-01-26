/**
  * @ (#) ReportCacheManagerBean.java May 30, 2007
  * Project      : TTK HealthCare Services
  * File         : ReportCacheManagerBean.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : 
  *
  * @author       :  Ajay Kumar
  * Modified by   :
  * Modified date :
  * Reason        :
  */

package com.ttk.business.common;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagementType;

import org.apache.log4j.Logger;

import com.ttk.common.exception.TTKException;

import com.ttk.dao.impl.common.misreports.ReportCacheDAOImpl;
import com.ttk.dao.impl.common.misreports.ReportCommonDAOFactory;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class ReportCacheManagerBean implements ReportCacheManager {

	private static Logger log = Logger.getLogger( ReportCacheManagerBean.class );
    private ReportCacheDAOImpl dao = null;
	
	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @return CacheDAOImpl object
	 * @exception throws TTKException
	 */
	private ReportCacheDAOImpl getCacheDAO() throws TTKException
	{
		try
		{
			if(dao == null)
			{
				ReportCommonDAOFactory daoFactory = new ReportCommonDAOFactory();
				dao = (ReportCacheDAOImpl)daoFactory.getDAO("cache");
			}//end if()
			log.info("returning....."+dao);
			return dao;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error.dao");
		}//end of catch(Exception exp) 
	}//end getCacheDAO()
	
	/**
	 * This method returns an arraylist loaded with CacheObjects for the appropriate identifier   
	 * @param strIdentifier String identifier for the CacheObject's to be loaded
	 * @return ArrayList of CacheObject's
	 * @exception throws TTKException
	 */
	public ArrayList loadObjects(String strIdentifier) throws TTKException
	{
		return (ArrayList)this.getCacheDAO().loadObjects(strIdentifier);
	}//end of loadObjects(String strIdentifier)
}//end of ReportCacheManagerBean
