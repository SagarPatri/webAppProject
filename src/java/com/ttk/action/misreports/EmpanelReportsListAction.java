package com.ttk.action.misreports;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.misreports.ImageEnquiryManager;
import com.ttk.business.reports.TTKReportManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.SearchCriteria;

public class EmpanelReportsListAction extends TTKAction {

	 private static final Logger log = Logger.getLogger(EmpanelReportsListAction.class);
		
	  //declare forward paths
	  private static final String strEmpanelReportlist="empanelMISreportlist";
	  private static final String strEmpanelmentMonitorform ="empanelmentmonitorform";
	  private static final String strReportdisplay ="reportdisplay";
	 /**
	  * This method is used to initialize the search grid.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			                         HttpServletResponse response)throws TTKException
	    {
	    	try
	    	{
	    		  setLinks(request);
	    			 ((DynaActionForm)form).initialize(mapping);//reset the form data
	    			 request.setAttribute("reportTypes", request.getParameter("ReportType"));
	    			return this.getForward(strEmpanelReportlist, mapping, request);
	    		
	    	}//end of try
	    	catch(TTKException expTTK)
	    	{
	    		return this.processExceptions(request, mapping, expTTK);	
	    	}//end of catch(TTKException expTTK)
	    	catch (Exception exp) {
				return this.processExceptions(request, mapping, new TTKException(exp,"reportdetail"));
			}//end of catch (Exception exp)
	 }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	                     //HttpServletResponse response)throws Exception
	 
	 
	 public ActionForward doEmpanelmentRemittance(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 HttpServletResponse response)throws TTKException
			 {
		 try
		 {
			 log.debug("Inside the doEmpanelmentRemittance method of EmpanelReportsListAction");
			 setLinks(request);
			 ((DynaActionForm) form).initialize(mapping);// reset the form data

			 return this.getForward(strEmpanelmentMonitorform,mapping,request);	
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);	
		 }//end of catch(TTKException expTTK)
		 catch (Exception exp) {
			 return this.processExceptions(request, mapping, new TTKException(exp,"reportdetail"));
		 }//end of catch (Exception exp)
			 }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest reque

	 
	 
	 public ActionForward doGenerateDetailReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 HttpServletResponse response)throws TTKException
			 {
		 try{
			 log.info(" doGenerateDetailReport in EmpanelReportsListAction ");
			 setLinks(request);
			  //added for KOC-1276
			 try
			 { 
				 TTKReportManager tTKReportManager	=	null;
				 DynaActionForm frmMISReports	=	(DynaActionForm) form;
				 tTKReportManager	=	this.getTTKReportManager();
				 ArrayList alData	=	null;
				 String argList		=	request.getParameter("argList");
				 String repType		=	request.getParameter("repType");
				 alData				=	tTKReportManager.getDetailedReport(argList,repType);
				 request.setAttribute("alData", alData);
				 
				 return (mapping.findForward("empanelutillization"));
			 }//end of try
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 }//end of catch (Exception e)
			 return (mapping.findForward(strReportdisplay));
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request, mapping, new TTKException(exp,"reportdetail"));
		 }//end of catch(Exception exp)

			 }
	 
	 
	 public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 HttpServletResponse response) throws TTKException{
		 try{
			 setLinks(request);
			 log.info("Inside the doClose method of EmpanelReportsListAction");
			 ((DynaActionForm)form).initialize(mapping);//reset the form data

			 return this.getForward(strEmpanelReportlist,mapping,request);
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }// end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
		 }// end of catch(Exception exp)
	 }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	 
	  private TTKReportManager getTTKReportManager() throws TTKException
	     {
	         TTKReportManager tTKReportManager = null;
	         try
	         {
	             if(tTKReportManager == null)
	             {
	                 InitialContext ctx = new InitialContext();
	                 tTKReportManager = (TTKReportManager) ctx.lookup("java:global/TTKServices/business.ejb3/TTKReportManagerBean!com.ttk.business.reports.TTKReportManager");
	             }//end if
	         }//end of try
	         catch(Exception exp)
	         {
	             throw new TTKException(exp, "tTKReport");
	         }//end of catch
	         return tTKReportManager;
	     }//end getTTKReportManager() 
	 
}
		    
