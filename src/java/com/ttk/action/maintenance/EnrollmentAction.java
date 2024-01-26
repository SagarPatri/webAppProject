/**
 * @ (#) EnrollmentAction
 * Project       : TTK HealthCare Services
 * File          : EnrollmentAction.java
 * Author        : Navin Kumar R
 * Company       : Span Systems Corporation
 * Date Created  : 24th August,2009
 *
 * @author       : 
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.maintenance;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ttk.action.TTKAction;
import com.ttk.common.exception.TTKException;

public class EnrollmentAction extends TTKAction{
	
	 private static final Logger log = Logger.getLogger(EnrollmentAction.class );

	 /**
     * This method is used to initialize the enrollment list.
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
    							   HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		log.debug("Inside EnrollmentAction doDefault");    		    		    
    		return this.getForward("enrollmentlist", mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, "enrollment"));
    	}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
}
