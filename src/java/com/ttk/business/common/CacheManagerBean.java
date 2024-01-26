/**
 * @ (#) CacheManagerBean.java 12th Sep 2005
 * Project      : TTK HealthCare Services
 * File         : CacheManagerBean.java
 * Author       : Nagaraj D V
 * Company      : Span Systems Corporation
 * Date Created : 12th Sep 2005 
 *
 * @author       :  Nagaraj D V
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
 
package com.ttk.business.common;

import java.util.ArrayList;

import com.ttk.common.exception.TTKException; 
import com.ttk.dao.impl.common.CacheDAOImpl;
import com.ttk.dao.impl.common.CommonDAOFactory;

import javax.ejb.*;
import javax.servlet.http.HttpServletRequest;

@Stateless
@javax.ejb.TransactionManagement(TransactionManagementType.BEAN)

public class CacheManagerBean implements CacheManager{
	
    private CacheDAOImpl dao = null;
	
	/**
	 * This method returns the instance of the data access object to initiate the required tasks
	 * @return CacheDAOImpl object
	 * @exception throws TTKException
	 */
	private CacheDAOImpl getCacheDAO() throws TTKException
	{
		try
		{
			if(dao == null)
			{
				CommonDAOFactory daoFactory = new CommonDAOFactory();
				dao = (CacheDAOImpl)daoFactory.getDAO("cache");
			}//end if()
			return dao;
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "error.dao");
		}//end of catch(Exception exp) 
	}//end getCacheDAO()
	
	
	// govind
	public ArrayList loadDynamicObjects(String strIdentifier,String strDynParam) throws TTKException{
		return (ArrayList)this.getCacheDAO().loadDynamicObjects(strIdentifier, strDynParam);
	}
	
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
	
			/**
	 * This method returns an arraylist loaded with CacheObjects for the appropriate identifier   
	 * @param strIdentifier String identifier for the CacheObject's to be loaded
	 * @return ArrayList of CacheObject's
	 * @exception throws TTKException
	 */
	public ArrayList loadObjects1(String strIdentifier,Long strClaimSeqId) throws TTKException
	{
		return (ArrayList)this.getCacheDAO().loadObjects1(strIdentifier,strClaimSeqId);
	}//end of loadObjects(String strIdentifier)
	
	// govind
	public ArrayList loadObjectsIns(String strIdentifier,String strClaimSeqId) throws TTKException
	{
		return (ArrayList)this.getCacheDAO().loadObjectsINS(strIdentifier,strClaimSeqId);
	}//end of loadObjects(String strIdentifier)
	
	
	//Tariff - blocking Network types based on base network  -INTX
	public ArrayList loadObjects1(String strIdentifier,String strCondParam,HttpServletRequest request) throws TTKException
	{
		return (ArrayList)this.getCacheDAO().loadObjects1(strIdentifier,strCondParam,request);
	}//end of loadObjects(String strIdentifier)
	
	
}//end of class CacheManagerBean