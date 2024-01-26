/**
 * @ (#) ShowListAction.java Oct 16, 2007
 * Project 	     : TTK HealthCare Services
 * File          : ShowListAction.java
 * Author        : Arun K N
 * Company       : Span Systems Corporation
 * Date Created  : Oct 16, 2007
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.common;

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
import com.ttk.business.administration.RuleManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;

public class ShowListAction extends TTKAction {
	
	private static Logger log = Logger.getLogger( ShowListAction.class );
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
	public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try
		{
			log.info("Inside doView method of ShowListAction");
			setLinks(request);
			DynaActionForm frmShowList=(DynaActionForm)form;
			ArrayList alItem=null;
			String strFlag=TTKCommon.checkNull(request.getParameter("flag"));
			String strValue=TTKCommon.checkNull(request.getParameter("value"));
			
			log.info("Flag is 		:"+strFlag);
			log.info("Value is  	:"+strValue);
			//get the bussines object
			RuleManager ruleManagerObject=this.getRuleManagerObject();
			if(strFlag.equals("DAYCARE_PROCEDURE"))
			{
				alItem=ruleManagerObject.getDayCareProcedureList(strValue);
				frmShowList.set("caption","Day care procedures");
			}//end of if(strFlag.equals("DAYCARE_PROCEDURE"))
			//added for KOC-1310
			if(strFlag.equals("CANCER_ICD"))
			{
				alItem = ruleManagerObject.getCancerICDList(strValue);
				frmShowList.set("caption","Cancer ICD Codes");				
			}
			//ended
			frmShowList.set("itemList",alItem);
			return mapping.findForward("showlist");
		}//end of try
		catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "prodpolicyrule"));
        }//end of catch(Exception exp)
	}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	
	/**
     * Returns the RuleManager session object for invoking methods on it.
     * @return RuleManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private RuleManager getRuleManagerObject() throws TTKException
    {
        RuleManager ruleManager = null;
        try
        {
            if(ruleManager == null)
            {
                InitialContext ctx = new InitialContext();
                ruleManager = (RuleManager) ctx.lookup("java:global/TTKServices/business.ejb3/RuleManagerBean!com.ttk.business.administration.RuleManager");
                log.info("Inside RuleManager: RuleManager: " + ruleManager);
            }//end if(ruleManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "ruledata");
        }//end of catch
        return ruleManager;
    }//end of getRuleManagerObject()
}
