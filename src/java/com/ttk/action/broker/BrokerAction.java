/**
 *   @ (#) BrokerAction.java Dec 29, 2015
 *   Project 	   : Dubai Project
 *   File          : BrokerAction.java
 *   Author        : Nagababu K
 *   Company       : RCS
 *   Date Created  : Dec 29, 2015
 *
 *   @author       :  Nagababu K
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 *
 */

package com.ttk.action.broker;

import java.util.ArrayList;

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
import com.ttk.business.broker.OnlineBrokerManager;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.brokerlogin.BrokerVO;

/**
 * This class is used for Searching the List Policies to see the Account Info.
 * This also provides deletion and updation of products.
 */
public class BrokerAction extends TTKAction {

    private static Logger log = Logger.getLogger( BrokerAction.class );

    //Modes.
    //private static final String strBackward="Backward";
    //private static final String strForward="Forward";

    // Action mapping forwards.
    private static final String strBrokerHome="brokerHome";
    private static final String strBrokerLogDetails="BrokerLogDetails";
    private static final String strCorporateSearch="corporateSearch";

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
                                                     HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the doDefault method of BrokerAction");
            setOnlineLinks(request);            
            DynaActionForm frmBroDashBoard =(DynaActionForm)form;
            OnlineBrokerManager brokerManager=null;
            frmBroDashBoard.initialize(mapping);     //reset the form data
            brokerManager=this.getBrokerManagerObject();
            HttpSession session=request.getSession();
            
            String [] policyDtails=brokerManager.getPolicyDetails((String)session.getAttribute("broUserId"));
            frmBroDashBoard.set("numberOfPolicies",policyDtails[0] );
            frmBroDashBoard.set("numberOfLives",policyDtails[1] );
            frmBroDashBoard.set("totalGrossPremium",policyDtails[2] );
            frmBroDashBoard.set("totalErnedPremium", policyDtails[3]);
            session.setAttribute("frmBroDashBoard", frmBroDashBoard);
            return this.getForward(strBrokerHome, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processBrokerExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processBrokerExceptions(request, mapping, new TTKException(exp,strBrokerHome));
        }//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
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
    public ActionForward doViewLog(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the doViewLog method of BrokerAction");
            setOnlineLinks(request);            
            DynaActionForm frmBroDashBoard =(DynaActionForm)form;
            OnlineBrokerManager brokerManager=null;
            frmBroDashBoard.initialize(mapping);     //reset the form data
            brokerManager=this.getBrokerManagerObject();
            HttpSession session=request.getSession();
            String broUserId=(String)session.getAttribute("broUserId");
            ArrayList<BrokerVO> alBrokerVO=brokerManager.getLogDetails(broUserId);
            session.setAttribute("broLogDetails", alBrokerVO);
            return this.getForward(strBrokerLogDetails, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processBrokerExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processBrokerExceptions(request, mapping, new TTKException(exp,strBrokerHome));
        }//end of catch(Exception exp)
    }//end of doViewLog(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
    
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
    public ActionForward doClickLinks(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doClickLinks method of BrokerAction");
            setOnlineLinks(request);            
            DynaActionForm frmBroDashBoard =(DynaActionForm)form;
            frmBroDashBoard.initialize(mapping);     //reset the form data
            String strFarward="";
            
            if("Corporate".equals(request.getParameter("linkMode")))strFarward="Corporate";
            else if("Reports".equals(request.getParameter("linkMode")))strFarward="BrokerReports";
            else if("LogDetails".equals(request.getParameter("linkMode")))strFarward="LogDetails";
            else if("Profile".equals(request.getParameter("linkMode")))strFarward="Profile";
            else if("PasswordChange".equals(request.getParameter("linkMode")))strFarward="PasswordChange";
            return mapping.findForward(strFarward);
        }//end of try
        catch(TTKException expTTK)
        {
        	return this.processBrokerExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processBrokerExceptions(request, mapping, new TTKException(exp,strBrokerHome));
        }//end of catch(Exception exp)
    }//end of doClickLinks(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
    
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
    public ActionForward corporateSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the corporateSearch method of BrokerAction");
            setOnlineLinks(request);  
            return mapping.findForward(strCorporateSearch);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processBrokerExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processBrokerExceptions(request, mapping, new TTKException(exp,strBrokerHome));
        }//end of catch(Exception exp)
    }//end of corporateSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
    
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
    public ActionForward goBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the goBack method of BrokerAction");
            setOnlineLinks(request);            
            DynaActionForm frmBroDashBoard =(DynaActionForm)form;
            frmBroDashBoard.initialize(mapping);     //reset the form data
            String strFarward="Broker.Home.DashBoard";
            return mapping.findForward(strFarward);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processBrokerExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processBrokerExceptions(request, mapping, new TTKException(exp,strBrokerHome));
        }//end of catch(Exception exp)
    }//end of goBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
    /**
	 * Returns the BrokerManager session object for invoking methods on it.
	 * @return BrokerManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private OnlineBrokerManager getBrokerManagerObject() throws TTKException
	{
		OnlineBrokerManager brokerManager = null;
		try
		{
			if(brokerManager == null)
			{
				InitialContext ctx = new InitialContext();
				brokerManager = (OnlineBrokerManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineBrokerManagerBean!com.ttk.business.broker.OnlineBrokerManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strBrokerHome);
		}//end of catch
		return brokerManager;
	}//end getBrokerManagerObject()
}//end of BrokerAction.java
