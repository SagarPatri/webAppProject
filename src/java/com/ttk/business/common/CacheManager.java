/**
 * @ (#) CacheManager.java 12th Sep 2005
 * Project      : TTK HealthCare Services
 * File         : CacheManager.java
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

import com.ttk.common.exception.TTKException;

import java.util.ArrayList;

import javax.ejb.*;
import javax.servlet.http.HttpServletRequest;

@Local
public interface CacheManager{
	
	// govind
	public ArrayList loadDynamicObjects(String strIdentifier,String strDynParam) throws TTKException;
	
	/**
	 * This method returns an arraylist loaded with CacheObjects for the appropriate identifier   
	 * @param strIdentifier String identifier for the CacheObject's to be loaded
	 * @return ArrayList of CacheObject's
	 * @exception throws TTKException
	 */
	public ArrayList loadObjects(String strIdentifier) throws TTKException;
	// Shortfall CR KOC1179
	/**
	 * This method returns an arraylist loaded with CacheObjects for the appropriate identifier   
	 * @param strIdentifier String identifier for the CacheObject's to be loaded
	 * @return ArrayList of CacheObject's
	 * @exception throws TTKException
	 */
	public ArrayList loadObjects1(String strIdentifier,Long strClaimSeqId) throws TTKException;
	
	public ArrayList loadObjectsIns(String strIdentifier,String auth) throws TTKException;
	

	public ArrayList loadObjects1(String strIdentifier,String strCondParam,HttpServletRequest request) throws TTKException;
		
}//end of class CacheManager