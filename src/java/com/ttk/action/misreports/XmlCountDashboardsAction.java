/**
 * @ (#) PreAuthGeneralAction.java NOV 17,2017
 * Project       : TTK HealthCare Services
 * File          : XmlCountDashboardsAction.java
 * Author        : Deepthi Meesala
 * Company       :  RCS Technologies
 * Date Created  : NOV 17,2017
 */



package com.ttk.action.misreports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.misreports.ReportManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.misreports.ReportDetailVO;

public class XmlCountDashboardsAction extends TTKAction{

	
	private static final Logger log = Logger.getLogger( XmlCountDashboardsAction.class );
	
	//string for forwarding
    private static final String strXmlCountDashboards="xmlCountDashboards";
	
	 public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			   HttpServletResponse response) throws TTKException{
		 
		 			try{
		 					setLinks(request);
		 	 				if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		 						{
		 							((DynaActionForm) form).initialize(mapping);// reset the form data
		 						}//end of if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		 					return this.getForward(strXmlCountDashboards, mapping, request);
		 				}// end of try
		 				catch (TTKException expTTK) {
		 					return this.processExceptions(request, mapping, expTTK);
		 				}// end of catch(TTKException expTTK)
		 				catch (Exception exp) {
		 					return this.processExceptions(request, mapping, new TTKException(exp, strXmlCountDashboards));
		 				}// end of catch(Exception exp)
	 				}// end of doDefault	
	 
	 public ActionForward doGenerateReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			   HttpServletResponse response) throws TTKException{
		 try {
			
			 log.debug("Inside the doGenerateReport method of XmlCountDashboardsAction");
				setOnlineLinks(request);
				DynaActionForm frmXMLCountDashboardReports =(DynaActionForm)form;
				ArrayList<Object> dashboardlist = null;
				ReportManager dashboardManagerObject=this.getReportManager();
				ReportDetailVO dashboardvo = new ReportDetailVO();
				
				String sStartDate = request.getParameter("sStartDate");
				String sEndDate = request.getParameter("sEndDate");
				String xmlCountDashboards = request.getParameter("xmlDashboards");
			    String xmlDashboards = "|"+xmlCountDashboards+"|";
			
				dashboardvo.setsStartDate(sStartDate);
				dashboardvo.setsEndDate(sEndDate);
				dashboardvo.setXmlDashboards(xmlDashboards);
			    HashMap<String, ReportDetailVO>dashBoardMap =dashboardManagerObject.getXMLDashboardList(dashboardvo);
			    Set<Entry<String, ReportDetailVO>>  entrySet=dashBoardMap.entrySet();
				 
				 for(Entry<String, ReportDetailVO> entry:entrySet){
					 request.setAttribute(entry.getKey(),entry.getValue());
				 }
		} 
			catch(TTKException expTTK)
	    	{
	    		return this.processExceptions(request, mapping, expTTK);
	    	}
	    	catch(Exception exp)
	    	{
	    		return this.processExceptions(request, mapping, new TTKException(exp, "xmldashboards"));
	    	}
		 
		 return this.getForward(strXmlCountDashboards, mapping, request);
	 }
	 
	 public ActionForward onReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			   HttpServletResponse response) throws TTKException{
		 
		 try{
				log.debug("Inside XmlCountDashboardsAction doClose");
				//setLinks(request);
			
			       DynaActionForm dynForm=(DynaActionForm) form;
			       dynForm.initialize(mapping);
				return mapping.findForward("xmlCountDashboards");
				
		 }//end of try
			catch(Exception exp)
			{
			return this.processExceptions(request, mapping, new TTKException(exp,"xmlCountDashboards"));
			}//end of catch(Exception exp)
	 
			   }
	 
	 private ReportManager getReportManager() throws TTKException
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
	    }//end getTTKReportManager()

	 
	 
}
