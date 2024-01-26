/**
 * @ (#) TaxConfigurationAction.java Sep 16, 2010
 * Project       : TTK HealthCare Services
 * File          : TaxConfigurationAction.java
 * Author        : Manikanta Kumar G G
 * Company       : Span Systems Corporation
 * Date Created  : Sep 16, 2010
 *
 * @author       : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.administration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.common.exception.TTKException;

public class TaxConfigurationAction extends TTKAction 
{
	private static final Logger log = Logger.getLogger( TaxConfigurationAction.class );
	
	private static final String strConfiguration="taxconfiguration";			
	private static final String strClose="close";
	
	/**
	 * This method is used to View the Configuration List Screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws TTKException if any error occurs
	 */
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
			{
				log.debug("Inside the doDefault method of ConfigurationAction");
				try
				{
				setLinks(request);			
				DynaActionForm frmtdsConfiguration = (DynaActionForm)form;
				frmtdsConfiguration.initialize(mapping);
				return this.getForward(strConfiguration, mapping, request);
				}//end of try
				catch(TTKException expTTK)
				{
				return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
				return this.processExceptions(request, mapping, new TTKException(exp,strConfiguration));
				}//end of catch(Exception exp)
			}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to navigate to previous screen when closed button is clicked.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												   HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doClose method of TaxConfigurationAction");
			setLinks(request);
			return this.getForward(strClose, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strConfiguration));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
}//end of TaxConfigurationAction
