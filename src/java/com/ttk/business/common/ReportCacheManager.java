/**
  * @ (#) ReportCacheManager.java May 30, 2007
  * Project      : TTK HealthCare Services
  * File         : ReportCacheManager.java
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

import javax.ejb.Local;

import com.ttk.common.exception.TTKException;

@Local

public interface ReportCacheManager {
	/**
	 * This method returns an arraylist loaded with CacheObjects for the appropriate identifier   
	 * @param strIdentifier String identifier for the CacheObject's to be loaded
	 * @return ArrayList of CacheObject's
	 * @exception throws TTKException
	 */
	public ArrayList loadObjects(String strIdentifier) throws TTKException;

}//end of ReportCacheManager
