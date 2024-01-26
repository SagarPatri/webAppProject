/**
  * @ (#) EnrolReportsListAction.java May 18, 2007
  * Project      : TTK HealthCare Services
  * File         : EnrolReportsListAction.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : 
  *
  * @author      :  Ajay Kumar
  * Modified by  : Balakrishna Erram
  * Modified date: April 14, 2009
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
 * This action class is used to display the Enrol Reports.
 */

public class EnrolReportsListAction extends TTKAction {
	
	private static final Logger log = Logger.getLogger( EnrolReportsListAction.class );
	
	//string for forwarding
    private static final String stridcardsreportlist="idcardsreportlist";
    private static final String strEnrollmentMonitorform ="enrollmentmonitorform";
    private static final String strAccentureEnrolform ="accentureenrolform";
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
    public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            					   HttpServletResponse response) throws TTKException
    {
    	try{
    		//log.debug("Inside the doDefault method of EnrolReportsListAction");
    		setLinks(request);
    		if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    		{
    			((DynaActionForm) form).initialize(mapping);// reset the form data
    		}//end of if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    		return this.getForward(stridcardsreportlist, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
		}// end of catch(Exception exp)
	}// end of doDefault
    
      /**
	  * This method is used to display to next screen when hyperlinked is clicked.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
    public ActionForward doEnrolMonitordetails(ActionMapping mapping,
            ActionForm form,
            HttpServletRequest request,
            HttpServletResponse response) throws TTKException{
    	try{
    		//log.debug("Inside the doEnrolMonitordetails method of EnrolReportsListAction");
    		setLinks(request);
    		
    		((DynaActionForm)form).initialize(mapping);//reset the form data            
    		
    		return this.getForward(strEnrollmentMonitorform, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
		}//end of catch(Exception exp)
		
    
    }//end of doEnrolMonitordetails
    
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
    public ActionForward doAccentureEnrolReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            					   HttpServletResponse response) throws TTKException
       {
    	 try
    	 {
    		 //log.debug("Inside the doAccentureEnrolReport method of EnrolReportsListAction");
     	     setLinks(request);
     	      request.getSession().removeAttribute("frmMISReports");
     		 ((DynaActionForm)form).initialize(mapping);//reset the form data            
     		return this.getForward(strAccentureEnrolform, mapping, request);
    	 }//end of try
    	 catch(TTKException expTTK)
    	 {
    		 return this.processExceptions(request, mapping, expTTK);
    	 }//end of catch(TTKException expTTK)
    	 catch(Exception exp)
 		{
 			return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
 		}//end of catch(Exception exp)
      }//end of doAccentureEnrolReport()
}//end of EnrolReportsListAction
