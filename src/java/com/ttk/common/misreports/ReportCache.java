/**
  * @ (#) ReportCache.java May 29, 2007
  * Project      : TTK HealthCare Services
  * File         : ReportCache.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : 
  *
  * @author       :  Ajay Kumar
  * Modified by   :
  * Modified date :
  * Reason        :
  */

package com.ttk.common.misreports;

import java.io.Serializable;
import java.util.ArrayList;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;

import com.ttk.business.common.ReportCacheManager;
import com.ttk.common.exception.TTKException;

public class ReportCache implements Serializable {
	
	private static Logger log = Logger.getLogger( ReportCache.class );
	
	private static ArrayList allOfficeInfoCode = null; //to load from table TPA_OFFICE_INFO
    private static ArrayList alInsCompanyName = null;//to load from table TPA_INS_INFO 
    private static ArrayList alInsDoBo = null;//to load from table TPA_INS_INFO 
    private static ArrayList allhospitalName = null; //to load from table tpa_hosp_info
    private static ArrayList alOfficeInfo = null;//to load from table TPA_OFFICE_INFO
    private static ArrayList alInsHO = null;//to load from table TPA_INS_INFO
    private static ArrayList alEnrollmentTypeCode = null;//to load from table TPA_ENROLMENT_TYPE_CODE
    private static ArrayList alLiabilityStatus = null;//to load from table TPA_GENERAL_CODE
    private static ArrayList allinwardRegister = null; //to load from table TPA_GENERAL_CODE
    private static ArrayList alMISReportsCoding = null; //to load from table TPA_GENERAL_CODE
    private static ArrayList alInvestAgencyName = null; //to load from table INVESTIGATION_AGENCY_DETAILS
    private static ArrayList alPeriod = null;
    private static ArrayList alPreAuthStatus = null;    
    private static ArrayList allInsName = null; 
    
    
    /**
     * Returns the ArrayList object which contains the cache objects
     * @param strIdentifier String object which identifies the arraylist to be populated
     * @return ArrayList object which contains the cache objects 
     * @exception throws TTKException  
     */
	public static ArrayList getCacheObject(String strIdentifier) throws TTKException
	{
	    log.debug("Inside getCacheObject ");
        
        if(strIdentifier.equals("ttkOfficeInfo"))
        {
        	allOfficeInfoCode = loadObjects(strIdentifier, allOfficeInfoCode);
            return allOfficeInfoCode;
        }//end of if(strIdentifier.equals("officeInfo"))
        else if(strIdentifier.equals("insCompany"))
        {
            alInsCompanyName = loadObjects(strIdentifier,alInsCompanyName);
            return alInsCompanyName;
        }//end of if(strIdentifier.equals("insuranceCompany"))alInsDoBo
        else if(strIdentifier.equals("insDoBo"))
        {
        	alInsDoBo = loadObjects(strIdentifier,alInsDoBo);
            return alInsDoBo;
        }//end of if(strIdentifier.equals("insuranceCompany"))alInsDoBo
        else if(strIdentifier.equals("hospitalNameInfo"))
        {
        	allhospitalName = loadObjects(strIdentifier, allhospitalName);
            return allhospitalName;
        }//end of if(strIdentifier.equals("hospitalNameInfo"))
        else if(strIdentifier.equals("inwardRegisterInfo"))
        {
        	allinwardRegister = loadObjects(strIdentifier, allinwardRegister);
            return allinwardRegister;
        }//end of if(strIdentifier.equals("hospitalNameInfo"))
        else if(strIdentifier.equals("insHO"))
        {
        	alInsHO = loadObjects(strIdentifier, alInsHO);
            return alInsHO;
        }//end of if(strIdentifier.equals("insHO"))
        else if(strIdentifier.equals("officeInfo"))
        {
        	alOfficeInfo = loadObjects(strIdentifier, alOfficeInfo);
            return alOfficeInfo;
        }//end of if(strIdentifier.equals("officeInfo"))
        else if(strIdentifier.equals("enrollTypeCode"))
        {
        	alEnrollmentTypeCode = loadObjects(strIdentifier, alEnrollmentTypeCode);
            return alEnrollmentTypeCode;
        }//end of if(strIdentifier.equals("enrollTypeCode"))
        else if(strIdentifier.equals("liabilityStatus"))
        {
        	alLiabilityStatus = loadObjects(strIdentifier, alLiabilityStatus);
            return alLiabilityStatus;
        }//end of if(strIdentifier.equals("liabilityStatus"))
        else if(strIdentifier.equals("misReportsCoding"))
        {
        	alMISReportsCoding = loadObjects(strIdentifier, alMISReportsCoding);
            return alMISReportsCoding;
        }//end of if(strIdentifier.equals("misReportsCoding"))
        else if(strIdentifier.equals("investAgencyDetails"))
        {
        	alInvestAgencyName = loadObjects(strIdentifier, alInvestAgencyName);
            return alInvestAgencyName;
        }//end of else if(strIdentifier.equals("investAgencyDetails"))
        else if(strIdentifier.equals("period"))
        {
        	alPeriod = loadObjects(strIdentifier, alPeriod);
            return alPeriod;
        }//end of else if(strIdentifier.equals("period"))
        else if(strIdentifier.equals("preauthStatus"))
        {
        	alPreAuthStatus = loadObjects(strIdentifier, alPreAuthStatus);
            return alPreAuthStatus;
        }
        
        
        
        else if(strIdentifier.equals("inscmpnyNameList"))
        {
        	allInsName = loadObjects(strIdentifier, allInsName);
            return allInsName;
        }//end of if(strIdentifier.equals("hospitalNameInfo"))
        
        
        else
			return null;
    }//end of getCacheObject(String strIdentifier)
	
    public static void refresh(String strIdentifier) throws TTKException
    {
       
        //Ajay Change
        if(strIdentifier.equals("ttkOfficeInfo"))
        {
        	 allOfficeInfoCode=null;
        	allOfficeInfoCode = loadObjects(strIdentifier, allOfficeInfoCode);
            
        }//end of if(strIdentifier.equals("officeInfo"))
        else if(strIdentifier.equals("insCompany"))
        {
            alInsCompanyName = null;
            alInsCompanyName = loadObjects(strIdentifier,alInsCompanyName);
        }//end of if(strIdentifier.equals("insuranceCompany"))
        else if(strIdentifier.equals("insDoBo"))
        {
        	alInsDoBo=null;
        	alInsDoBo = loadObjects(strIdentifier,alInsDoBo);
            
        }//end of if(strIdentifier.equals("insuranceCompany"))alInsDoBo
        
        else if(strIdentifier.equals("insHO"))
        {
        	alInsHO=null;
        	alInsHO = loadObjects(strIdentifier,alInsHO);
        }//end of if(strIdentifier.equals("insHO"))
        else if(strIdentifier.equals("officeInfo"))
        {
        	alOfficeInfo=null;
        	alOfficeInfo = loadObjects(strIdentifier,alOfficeInfo);
        }//end of if(strIdentifier.equals("officeInfo"))
        else if(strIdentifier.equals("investAgencyDetails"))
        {
        	alInvestAgencyName=null;
        	alInvestAgencyName = loadObjects(strIdentifier, alInvestAgencyName);
        }//end of else if(strIdentifier.equals("investAgencyDetails"))
        //end Ajay Change
    }//end of public static void refresh(String strIdentifier)
    
    /**
     * Returns the ArrayList object which contains the cache objects
     * @param strIdentifier String object which identifies the arraylist to be populated
     * @param alCacheObjects ArrayList the array list to be populated with cache objects
     * @return ArrayList object which contains the cache objects 
     * @exception throws TTKException  
     */
	private static ArrayList loadObjects(String strIdentifier, ArrayList alCacheObjects) throws TTKException
    {
		if(alCacheObjects != null)
		    return alCacheObjects;
        else
            return getCacheManagerObject().loadObjects(strIdentifier);
    }//end of loadObjects(String strIdentifier, ArrayList alCacheObjects)
    
    /**
     * Returns the CacheManager object for invoking methods on it.
     * @return CacheManager object which can be used for calling the methods on session bean 
     * @exception throws TTKException  
     */
	private static ReportCacheManager getCacheManagerObject() throws TTKException
    {
        ReportCacheManager reportCacheManager = null;
        try 
        {
            if(reportCacheManager == null)
            {
                InitialContext ctx = new InitialContext();
                reportCacheManager = (ReportCacheManager) ctx.lookup("java:global/TTKServices/business.ejb3/ReportCacheManagerBean!com.ttk.business.common.ReportCacheManager");
            }//end if
        }//end of try 
        catch(Exception exp) 
        {
            throw new TTKException(exp, "cache");
        }//end of catch
        return reportCacheManager;
    }//end getCacheManagerObject()

}//end of ReportCache 
