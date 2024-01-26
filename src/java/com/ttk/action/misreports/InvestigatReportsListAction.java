
/**
  * @ (#) InvestigatReportsListAction.java April 24, 2008
  * Project      : TTK HealthCare Services
  * File         : InvestigatReportsListAction.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : April 24, 2008
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
 * This action class is used to display the Investigation Reports.
 */

public class InvestigatReportsListAction extends TTKAction {
	
	private static final Logger log=Logger.getLogger(InvestigatReportsListAction.class);
	//String for Forwarding
    private static final String strAllInvestigatReportsList="allinvestigatreportslist";
    private static final String strInvestigationMonitorReport="investigationmonitorreport";
    
    
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
			log.debug("Inside the doDefault method of InvestigatReportsListAction");
			setLinks(request);
			
			if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm) form).initialize(mapping);// reset the form data
			}//end of if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			return this.getForward(strAllInvestigatReportsList, mapping, request);			
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
  public ActionForward doInvestigationMonitor(ActionMapping mapping, ActionForm form,HttpServletRequest request,
  		                         HttpServletResponse response) throws TTKException {
		try {
			log.debug("Inside the doInvestigationMonitor method of InvestigatReportsListAction");
			setLinks(request);
			((DynaActionForm) form).initialize(mapping);// reset the form data

			return this.getForward(strInvestigationMonitorReport, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
		}// end of catch(Exception exp)
	}// end of doCrdetails
}//end of public class InvestigatReportsListAction extends TTKAction
