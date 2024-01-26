
/**
  * @ (#) PreAuthReportsListAction.java July 25, 2007
  * Project      : TTK HealthCare Services
  * File         : PreAuthReportsListAction.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : 
  *
  * @author      : Ajay Kumar
  * Modified by  : Balakrishna Erram
  * Modified date: April 15, 2009
  * Company      : Span Infotech Pvt.Ltd.
  * Reason       : Code Review
  */

package com.ttk.action.misreports;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;

/**
 * This action class is used to display the Prauth Reports.
 */
public class PreAuthReportsListAction extends TTKAction {
	
private static final Logger log = Logger.getLogger( PreAuthReportsListAction.class );
	
//	string for forwarding
    private static final String strAllPreauthReportsList="allpreauthreportslist";
    private static final String strPreauthorizationReports="preauthorizationreports";
    private static final String strPreauthorizationGrpReports="preauthorizationgrpreports";
    private static final String strPreauthorizationSmsReports="preauthorizationsmsreports";
    
    /**
	  * This method is used to initialize all the screen hyperlink.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
    public ActionForward doDefault(ActionMapping mapping, ActionForm form,HttpServletRequest request, 
    							   HttpServletResponse response) throws TTKException {
    	try {
    		//log.debug("Inside the doDefault method of PreAuthReportsListAction");
    		setLinks(request);
    		
    		if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    		{
    			((DynaActionForm) form).initialize(mapping);// reset the form data
    		}//end of if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    		return this.getForward(strAllPreauthReportsList, mapping, request);
    	}// end of try
    	catch (TTKException expTTK) {
    		return this.processExceptions(request, mapping, expTTK);
    	}// end of catch(TTKException expTTK)
    	catch (Exception exp) {
    		return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
    	}// end of catch(Exception exp)
    }// end of doDefault
    
    /**
	  * This method is used to initialize the screen.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
   public ActionForward doPreAuthDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws TTKException {
		try {
			//log.debug("Inside the doCrdetails method of PreAuthReportsListAction");
			setLinks(request);

			((DynaActionForm) form).initialize(mapping);// reset the form data

			return this.getForward(strPreauthorizationReports, mapping, request);

		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
		}// end of catch(Exception exp)

	}// end of doPreAuthDetails
   
   /**
	  * This method is used to display to next screen when hyper link is clicked.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
   public ActionForward doPreAuthGrpDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
         					   HttpServletResponse response) throws TTKException
    {
 	 try
 	 {
 		 //log.debug("Inside the doPreAuthGrpDetails method of PreAuthReportsListAction");
  	     setLinks(request);
  	     request.getSession().removeAttribute("frmMISReports");
  		 ((DynaActionForm)form).initialize(mapping);//reset the form data   
  		return this.getForward(strPreauthorizationGrpReports, mapping, request);
 	 }//end of try
 	 catch(TTKException expTTK)
 	 {
 		 return this.processExceptions(request, mapping, expTTK);
 	 }//end of catch(TTKException expTTK)
 	 catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
		}//end of catch(Exception exp)
   }//end of doPreAuthGrpDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  // HttpServletResponse response)
   
   /**
	  * This method is used to display to next screen when hyper link is clicked.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
 public ActionForward doPreAuthSmsDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
       					   HttpServletResponse response) throws TTKException
  {
	 try
	 {
		 //log.debug("Inside the doPreAuthGrpDetails method of PreAuthReportsListAction");
	     setLinks(request);
	     request.getSession().removeAttribute("frmMISReports");
		 ((DynaActionForm)form).initialize(mapping);//reset the form data   
		return this.getForward(strPreauthorizationSmsReports, mapping, request);
	 }//end of try
	 catch(TTKException expTTK)
	 {
		 return this.processExceptions(request, mapping, expTTK);
	 }//end of catch(TTKException expTTK)
	 catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
		}//end of catch(Exception exp)
 }//end of doPreAuthSmsDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	   //HttpServletResponse response)
   
 
 

 /**
	  * This method is used to display to next screen when hyper link is clicked.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
public ActionForward doPreAuthUtilization(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     					   HttpServletResponse response) throws TTKException
{
	 try
	 {
		 //log.debug("Inside the doPreAuthGrpDetails method of PreAuthReportsListAction");
	     setLinks(request);
		 ((DynaActionForm)form).initialize(mapping);//reset the form data
		 
        String repotType=  request.getParameter("ReportType");
		 
		 request.setAttribute("reportTypes", request.getParameter("ReportType"));
		 
		 if("PRP".equals(repotType)){
			 
			 return this.getForward("preAuthUtilization", mapping, request);
		 }
		 
		 else{
			 return this.getForward("claimsthUtilization", mapping, request);
			 
		 }
		 
		 
		 
	 }//end of try
	 catch(TTKException expTTK)
	 {
		 return this.processExceptions(request, mapping, expTTK);
	 }//end of catch(TTKException expTTK)
	 catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
		}//end of catch(Exception exp)
}//end of doPreAuthSmsDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	   //HttpServletResponse response)
 

}//end of PreAuthReportsListAction
