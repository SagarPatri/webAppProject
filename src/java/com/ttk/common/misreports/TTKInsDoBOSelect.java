/**
  * @ (#) TTKInsDoBOSelect.java Jun 04, 2007
  * Project      : TTK HealthCare Services
  * File         : TTKInsDoBOSelect.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : Jun 04,2007
  *
  * @author       :  Ajay Kumar
  * Modified by   :
  * Modified date :
  * Reason        :
  */

package com.ttk.common.misreports;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;

import com.ttk.business.misreports.ReportManager;
import com.ttk.common.exception.TTKException;

/**
 * This class has the common methods usefull across the project
 *
 */
public class TTKInsDoBOSelect {
	
	private static Logger log = Logger.getLogger( TTKInsDoBOSelect.class );
	
	private static ReportManager reportObject = null;
	private static HashMap<Object,Object> hmInsCompanyList = null;
	private static ArrayList alInsCompanyList = new ArrayList();
	private static HashMap<Object,Object> hmDoBoList = null;
	private static ArrayList alDoBoList = new ArrayList();
	
	/**
	 * This methods select the insurance company name based on the branch name
	 *
	 */
	public static ArrayList getInsCompany(ActionForm form)throws Exception
	{
		DynaActionForm frmTttkReports = (DynaActionForm)form;
		reportObject= TTKInsDoBOSelect.getReportManager();
		hmInsCompanyList=reportObject.getInsCompanyInfo();
		alInsCompanyList = (ArrayList)hmInsCompanyList.get(frmTttkReports.getString("tTKBranchCode"));
		if(alInsCompanyList==null)
		{
	    	alInsCompanyList=new ArrayList();
		}//end of if(alInsCompanyList==null)
		return alInsCompanyList;
	}//end of getInsCompany
	
	/**
	 * This methods select the DoBo code based on the insurance company name
	 *
	 */
	public static ArrayList getInsCompDoBo(ActionForm form)throws Exception
	{
		DynaActionForm frmTttkReports = (DynaActionForm)form;
		reportObject= TTKInsDoBOSelect.getReportManager();
		hmDoBoList=reportObject.getInsComDoBoCode();
        alDoBoList = (ArrayList)hmDoBoList.get(frmTttkReports.getString("tTKBranchCode").concat(frmTttkReports.getString("insCompanyCode")));
        if(alDoBoList==null)
		{
			alDoBoList=new ArrayList();
		}//end of  if(alDoBoList==null)
		return alDoBoList;
	}//end of getInsCompDoBo
	
	/**
	 * This methods select the insurance company name based on the branch name
	 *
	 */
	public static ArrayList getInsCompanyDetail(ActionForm form)throws Exception
	{
		DynaActionForm frmTttkReports = (DynaActionForm)form;
		reportObject= TTKInsDoBOSelect.getReportManager();
		hmInsCompanyList=reportObject.getInsCompanyDetail();
		alInsCompanyList = (ArrayList)hmInsCompanyList.get(frmTttkReports.getString("tTKBranchCode"));
		if(alInsCompanyList==null)
		{
	    	alInsCompanyList=new ArrayList();
		}//end of if(alInsCompanyList==null)
		return alInsCompanyList;
	}//end of getInsCompanyDetail
	
	/**
	 * This methods select the DoBo code based on the insurance company name
	 *
	 */
	public static ArrayList getInsuranceCompanyDoBoCode(ActionForm form)throws Exception
	{
		DynaActionForm frmTttkReports = (DynaActionForm)form;
		long lngOfficeSeqID = Long.parseLong(frmTttkReports.getString("tTKBranchCode"));
		long lngInsSeqID = Long.parseLong(frmTttkReports.getString("insCompanyCode"));
		
		log.debug("lngOfficeSeqID is : "+lngOfficeSeqID);
		log.debug("lngInsSeqID is : "+lngInsSeqID);
		
		reportObject= TTKInsDoBOSelect.getReportManager();
		alDoBoList=reportObject.getInsuranceCompanyDoBoCode(lngOfficeSeqID,lngInsSeqID);
        if(alDoBoList==null)
		{
			alDoBoList=new ArrayList();
		}//end of  if(alDoBoList==null)
		return alDoBoList;
	}//end of getInsuranceCompanyDoBoCode
	
	/**
	 * Returns the ReportManager session object for invoking methods on it.
	 * @return ReportManager session object which can be used for method invokation 
	 * @exception throws TTKException  
	 */
	private static ReportManager getReportManager() throws TTKException
	{
		ReportManager reportManager = null;
		try
		{
			if(reportManager == null)
			{
				InitialContext ctx = new InitialContext();
				reportManager = (ReportManager) ctx.lookup("java:global/TTKServices/business.ejb3/ReportManagerBean!com.ttk.business.misreports.ReportManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "tTKReport");
		}//end of catch
		return reportManager;
	}//end getTTKReportManager(
}//end of TTKInsDoBOSelect
