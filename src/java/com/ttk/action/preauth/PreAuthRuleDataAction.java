/**
 * @ (#) ClauseDetails.java Jun 27, 2006
 * Project      : TTK HealthCare Services
 * File         : ClauseDetails.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jun 27, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.preauth;

import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;

import com.ttk.action.TTKAction;
import com.ttk.business.administration.RuleManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.RuleXMLHelper;
import com.ttk.dto.administration.RuleVO;

import formdef.plugin.util.FormUtils;

public class PreAuthRuleDataAction extends TTKAction {

    private static Logger log = Logger.getLogger( PreAuthRuleDataAction.class ); // Getting Logger for this Class file

    //declaration of forward paths
    private static final String strPreauthruledata="preauthruledata";
    private static final String strModifyPreauthRuledata="modifypreauthruledata";
    private static final String strPreauthCoverageList="preauthcoveragelist";
    private static final String strClaimRuleData="claimruledata";
    private static final String strModifyClaimRuledata="modifyclaimruledata";
    private static final String strClaimCoverageList="claimcoveragelist";

    //declaration of constants
    private static final String strPreAuthorization="Pre-Authorization";
    private static final String strClaims="Claims";

    private static int iAUTO_SELECTED=0;
    //private static int iRULE_SAVED=1;
    private static int iINITIATE_CHECK_COMPLETED=2;

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
            log.debug("PreAuthRuleDataAction Action - inside doDefault method....");
            setLinks(request);
            String strActiveLink=TTKCommon.getActiveLink(request);

            //check for the web board ID
            if(strActiveLink.equals(strPreAuthorization))
            {
                if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
                {
                    TTKException expTTK = new TTKException();
                    expTTK.setMessage("error.PreAuthorization.required");
                    throw expTTK;
                }//end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
                if(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y"))
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.PreAuthorization.codingflow");
					throw expTTK;
				}//end of if(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y"))
            }//end of if(strActiveLink.equals(strPreAuthorization))
            else if(strActiveLink.equals(strClaims))
            {
                if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
                {
                    TTKException expTTK = new TTKException();
                    expTTK.setMessage("error.Claims.required");
                    throw expTTK;
                }//end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
                if(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y"))
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Claims.codingflow");
					throw expTTK;
				}//end of if(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y"))
            }//end of else if(strActiveLink.equals(strClaims))
            else
            {
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = new ActionMessage("error.security.unauthorized");
                actionMessages.add("global.error",actionMessage);
                saveErrors(request,actionMessages);
                return mapping.findForward("failure");
            }//end of else

            RuleManager ruleManagerObject=this.getRuleManagerObject();
            DynaActionForm frmPreAuthRuleData=(DynaActionForm)form;
            RuleVO ruleDataVO=null;
            Document ruleDataDocument = null;
            RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();

            //load the Display nodes of Rules to Application scope if not loaded
            ServletContext sc=servlet.getServletContext();
            HashMap hmDisplayNodes=null;
            HashMap hmCopayResultNodes=null;
            if(sc.getAttribute("RULE_DISPLAY_NODES")==null)
            {
                hmDisplayNodes=ruleXMLHelper.loadDisplayNodes(TTKCommon.getDocument("MasterBaseRules.xml"));
                sc.setAttribute("RULE_DISPLAY_NODES",hmDisplayNodes);
            }//end of if(sc.getAttribute("RULE_DISPLAY_NODES")==null)
            else
            {
                hmDisplayNodes=(HashMap)sc.getAttribute("RULE_DISPLAY_NODES");
            }//end of else
            
            if(sc.getAttribute("RULE_COPAY_RESULT_NODES")==null)
            {
            	hmCopayResultNodes=ruleXMLHelper.loadDisplayNodes(TTKCommon.getDocument("MasterBaseRules.xml"));
                sc.setAttribute("RULE_COPAY_RESULT_NODES",hmCopayResultNodes);
            }//end of if(sc.getAttribute("RULE_COPAY_RESULT_NODES")==null)
            else
            {
            	hmCopayResultNodes=(HashMap)sc.getAttribute("RULE_COPAY_RESULT_NODES");
            }//end of else

//          initialze the formbean and remove the unwanted data from sessiom
            frmPreAuthRuleData.initialize(mapping);
            request.getSession().removeAttribute("RuleDataDocument");
            //request.getSession().removeAttribute("CombinedRuleDocument"); //used for merging

            //get the RuleData from the Database
            if(strActiveLink.equals(strPreAuthorization))
            {
                ruleDataVO=ruleManagerObject.getPAClaimsRule(PreAuthWebBoardHelper.getPreAuthSeqId(request),"PR");
            }//end of if(strActiveLink.equals(strPreAuthorization))
            else
            {
                ruleDataVO=ruleManagerObject.getPAClaimsRule(ClaimsWebBoardHelper.getClaimsSeqId(request),"CR");
            }//end of else

            if(ruleDataVO==null)
            {
                TTKException expTTK = new TTKException();
                if(strActiveLink.equals(strPreAuthorization))
                {
                    expTTK.setMessage("error.preAuthorization.ruledatanotfound");
                }//end of if(strActiveLink.equals(strPreAuthorization))
                else
                {
                    expTTK.setMessage("error.claims.ruledatanotfound");
                }//end of else
                throw expTTK;
            }//end of if(ruleDataVO==null)
            frmPreAuthRuleData =(DynaActionForm)FormUtils.setFormValues("frmPreAuthRuleData",ruleDataVO,this,
                    mapping,request);
            request.getSession().setAttribute("frmPreAuthRuleData",frmPreAuthRuleData);

            if(ruleDataVO.getRuleExecutionFlag().intValue()== iAUTO_SELECTED)
            {
                ruleDataDocument=ruleDataVO.getRuleDocument();
                if(ruleDataDocument!=null)
                {
                    //get the applicable coverages from the RuleData document
                    String[] strCoverageIds=ruleXMLHelper.getCoverageNodes(ruleDataDocument);
                    request.setAttribute("CoverageIDs",strCoverageIds);
                    request.getSession().setAttribute("RuleDataDocument",ruleDataDocument);
                    return this.getForward(getCoverageListPath(strActiveLink),mapping,request);
                }//end of if(ruleDataDocument!=null)
                else
                {
                    TTKException expTTK = new TTKException();
                    if(strActiveLink.equals(strPreAuthorization))
                    {
                        expTTK.setMessage("error.preAuthorization.ruledatanotfound");
                    }//end of if(strActiveLink.equals(strPreAuthorization))
                    else
                    {
                        expTTK.setMessage("error.claims.ruledatanotfound");
                    }//end of else
                    throw expTTK;
                }//end of else
            }//end of iif(ruleDataVO.getRuleExecutionFlag().intValue()== iAUTO_SELECTED)
            else
            {
                ruleDataDocument=ruleDataVO.getRuleDocument();
                request.getSession().setAttribute("RuleDataDocument",ruleDataDocument);
                return this.getForward(getRuledataPath(strActiveLink),mapping,request);
            }//end of else
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "preclarules"));
        }//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response);

    /**
     * This method is used to get the details of the selected record from web-board.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        log.debug("PreAuthRuleDataAction Action - inside doChangeWebBoard method....");
        return doDefault(mapping,form,request,response);
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to evaluate and get the Preauth/Claim Rule Results when Initiate Check
     * button is pressed
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doInitiateCheck(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
            log.debug("PreAuthRuleDataAction Action - inside doInitiateCheck method....");
            setLinks(request);
            String strActiveLink=TTKCommon.getActiveLink(request);

            RuleManager ruleManagerObject=this.getRuleManagerObject();
            DynaActionForm frmPreAuthRuleData=(DynaActionForm)form;
            RuleVO ruleDataVO=null;
            Document ruleDataDocument = null;
            RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();

            //process the rule document to find confidence factor
            if(strActiveLink.equals(strPreAuthorization))
            {
                ruleDataDocument = ruleManagerObject.processRule("P",PreAuthWebBoardHelper.getPreAuthSeqId(request));
            }//end of if(strActiveLink.equals(strPreAuthorization))
            else
            {
                ruleDataDocument = ruleManagerObject.processRule("C",ClaimsWebBoardHelper.getClaimsSeqId(request));
            }//end of else
            ruleDataDocument=ruleXMLHelper.removeRules(ruleDataDocument);

            //if document is processed save the processed document
            if(ruleDataDocument!=null)
            {
                ruleDataVO=(RuleVO)FormUtils.getFormValues(frmPreAuthRuleData,this,mapping,request);
                ruleDataVO.setSeqID(ruleDataVO.getRuleDataSeqID());
                ruleDataVO.setRuleDocument(ruleDataDocument);
                ruleDataVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
                long lngCnt=0;

                //Save the executed rules in the database after intiate check
                if(strActiveLink.equals(strPreAuthorization))
                {
                    lngCnt=ruleManagerObject.saveProdPolicyRule(ruleDataVO,"PI");
                }//end of if(strActiveLink.equals(strPreAuthorization))
                else
                {
                    lngCnt=ruleManagerObject.saveProdPolicyRule(ruleDataVO,"CI");
                }//end of if(strActiveLink.equals(strPreAuthorization))

                if(lngCnt>0)
                {
                    //requery to get the saved document
                    if(strActiveLink.equals(strPreAuthorization))
                    {
                        ruleDataVO=ruleManagerObject.getPAClaimsRule(PreAuthWebBoardHelper.getPreAuthSeqId(request),"PR");
                    }//end of if(strActiveLink.equals(strPreAuthorization))
                    else
                    {
                        ruleDataVO=ruleManagerObject.getPAClaimsRule(ClaimsWebBoardHelper.getClaimsSeqId(request),"CR");
                    }//end of else
                    request.setAttribute("updated","message.ruleExecutedSuccessfully");

                    ruleDataDocument=ruleDataVO.getRuleDocument();
                    frmPreAuthRuleData = (DynaActionForm)FormUtils.setFormValues("frmPreAuthRuleData",ruleDataVO,
                            this,mapping, request);
                    request.getSession().setAttribute("frmPreAuthRuleData",frmPreAuthRuleData);
                    request.getSession().setAttribute("RuleDataDocument",ruleDataDocument);
                }//end of if(lngCnt>0)
            }//end of if(ruleDataDocument!=null)
            return this.getForward(getRuledataPath(strActiveLink),mapping,request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "preclarules"));
        }//end of catch(Exception exp)
    }//end of doInitiateCheck(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response);

    /**
     * This method is used to evaluate and get the Preauth/Claim Rule Results when Initiate Check
     * button is pressed
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doReconfigure(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
            log.debug("PreAuthRuleDataAction Action - inside doInitiateCheck method....");
            setLinks(request);
            String strActiveLink=TTKCommon.getActiveLink(request);

            RuleManager ruleManagerObject=this.getRuleManagerObject();
            DynaActionForm frmPreAuthRuleData=(DynaActionForm)form;
            RuleVO ruleDataVO=null;
            Document ruleDataDocument = null;
            RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();
            ruleDataDocument=(Document)request.getSession().getAttribute("RuleDataDocument");

            ruleDataVO=(RuleVO)FormUtils.getFormValues(frmPreAuthRuleData,this, mapping, request);
            if(ruleDataVO.getRuleExecutionFlag().intValue() == iINITIATE_CHECK_COMPLETED)
            {
                //get the rules autoselected again
                if(strActiveLink.equals(strPreAuthorization))
                {
                    ruleDataVO=ruleManagerObject.getPAClaimsRule(PreAuthWebBoardHelper.getPreAuthSeqId(request),"PF");
                }//end of if(strActiveLink.equals(strPreAuthorization))
                else
                {
                    ruleDataVO=ruleManagerObject.getPAClaimsRule(ClaimsWebBoardHelper.getClaimsSeqId(request),"CF");
                }//end of else

                ruleDataDocument=ruleDataVO.getRuleDocument();
                frmPreAuthRuleData = (DynaActionForm)FormUtils.setFormValues("frmPreAuthRuleData",ruleDataVO,
                        this,mapping,request);
                request.getSession().setAttribute("frmPreAuthRuleData",frmPreAuthRuleData);
                request.getSession().setAttribute("RuleDataDocument",ruleDataDocument);
            }//end of if(ruleDataVO.getRuleExecutionFlag().intValue() == iINITIATE_CHECK_COMPLETED)

            //get the applicable coverages from the RuleData document
            String[] strCoverageIDs=ruleXMLHelper.getCoverageNodes(ruleDataDocument);
            request.setAttribute("CoverageIDs",strCoverageIDs);
            return this.getForward(getCoverageListPath(strActiveLink),mapping,request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "preclarules"));
        }//end of catch(Exception exp)
    }//end of doReconfigure(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response);

    /**
     * This method is used to update selected rules to be executed in PreAuth/Claim.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try
        {
            log.debug("PreAuthRuleDataAction Action - inside doSave method....");
            setLinks(request);
            String strActiveLink=TTKCommon.getActiveLink(request);

            RuleManager ruleManagerObject=this.getRuleManagerObject();
            DynaActionForm frmPreAuthRuleData=(DynaActionForm)form;
            RuleVO ruleDataVO=null;
            Document ruleDataDocument = null;
            RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();
            ruleDataDocument=(Document)request.getSession().getAttribute("RuleDataDocument");

            //get the refined ruleDataDocument based on the Coverages selected by User
            ruleDataDocument=ruleXMLHelper.updateCoverageSelectionStatus(ruleDataDocument,request);

            //save the RuleData to the database
            ruleDataVO=(RuleVO)FormUtils.getFormValues(frmPreAuthRuleData,this, mapping, request);
            ruleDataVO.setRuleDocument(ruleDataDocument);
            ruleDataVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            long lngRuleDataSeqID=0;

            //Clear the Current Results before Saving the modified Rules
            ruleDataDocument=ruleXMLHelper.clearRuleResults(ruleDataDocument);

            //Call the DAO to save the Ruledata
            if(ruleDataVO.getRuleDataSeqID()!=null)     //for updating RuleData
            {
                ruleDataVO.setSeqID(ruleDataVO.getRuleDataSeqID());
                if(strActiveLink.equals(strPreAuthorization))
                {
                    lngRuleDataSeqID=ruleManagerObject.saveProdPolicyRule(ruleDataVO,"PU");
                }//end of if(strActiveLink.equals(strPreAuthorization))
                else
                {
                    lngRuleDataSeqID=ruleManagerObject.saveProdPolicyRule(ruleDataVO,"CU");
                }//end of else
            }//end of if(ruleDataVO.getRuleDataSeqID()!=null)

            if(lngRuleDataSeqID>0)       //requery to get the updated ruledata from the database
            {
                //requery to get the updated ruledata from the database
                if(strActiveLink.equals(strPreAuthorization))
                {
                    ruleDataVO=ruleManagerObject.getPAClaimsRule(PreAuthWebBoardHelper.getPreAuthSeqId(request),"PR");
                }//end of if(strActiveLink.equals(strPreAuthorization))
                else
                {
                    ruleDataVO=ruleManagerObject.getPAClaimsRule(ClaimsWebBoardHelper.getClaimsSeqId(request),"CR");
                }//end of else

                request.setAttribute("updated","message.savedSuccessfully");
            }//end of if(lngRuleDataSeqID>0)
            ruleDataDocument=ruleDataVO.getRuleDocument();
            frmPreAuthRuleData = (DynaActionForm)FormUtils.setFormValues("frmPreAuthRuleData",ruleDataVO,this,
                    mapping,request);

            request.getSession().setAttribute("frmPreAuthRuleData",frmPreAuthRuleData);
            request.getSession().setAttribute("RuleDataDocument",ruleDataDocument);

            String strXpath="//clause/coverage[@allowed='3' and @selected='YES']/condition[@source='XML']";
            if(ruleDataDocument.selectSingleNode(strXpath)!=null)
            {
                return this.getForward(getModifyRuledataPath(strActiveLink),mapping,request);
            }//end of if(ruleDataDocument.selectSingleNode(strXpath)!=null)
            else
            {
                return this.getForward(getRuledataPath(strActiveLink),mapping,request);
            }//end of else
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"preclarules"));
        }//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response);

    /**
     * This method is used to update Information captured in the Rules which was not captured
     * in Database else where in the Application
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doUpdateRuleData(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                                HttpServletResponse response) throws Exception {
        try
        {
            log.debug("PreAuthRuleDataAction Action - inside doUpdateRuleData method....");
            setLinks(request);
            String strActiveLink=TTKCommon.getActiveLink(request);
            RuleManager ruleManagerObject=this.getRuleManagerObject();
            DynaActionForm frmPreAuthRuleData=(DynaActionForm)form;
            RuleVO ruleDataVO=null;
            Document ruleDataDocument = null;
            RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();
            ruleDataDocument=(Document)request.getSession().getAttribute("RuleDataDocument");

            if(ruleDataDocument!=null)  //update the data entered by the user in the screen
            {
                ruleDataDocument=ruleXMLHelper.updateRuleDataDocument(ruleDataDocument,request);

                //Clear the Current Results before Saving the modified Rules
                ruleDataDocument=ruleXMLHelper.clearRuleResults(ruleDataDocument);

                ruleDataVO=(RuleVO)FormUtils.getFormValues(frmPreAuthRuleData,this,mapping, request);
                ruleDataVO.setSeqID(ruleDataVO.getRuleDataSeqID());
                ruleDataVO.setRuleDocument(ruleDataDocument);
                ruleDataVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
                long lngRuleDataSeqID=0;

                //Call the DAO to save the Ruledata information for the preauth
                if(strActiveLink.equals(strPreAuthorization))
                {
                    lngRuleDataSeqID=ruleManagerObject.saveProdPolicyRule(ruleDataVO,"PU");
                }//end of if(strActiveLink.equals(strPreAuthorization))
                else
                {
                    lngRuleDataSeqID=ruleManagerObject.saveProdPolicyRule(ruleDataVO,"CU");
                }//end of else

                if(lngRuleDataSeqID>0)      //requery to get the updated ruledata from the database
                {
                    if(strActiveLink.equals(strPreAuthorization))
                    {
                        ruleDataVO=ruleManagerObject.getPAClaimsRule(PreAuthWebBoardHelper.getPreAuthSeqId(request),"PR");
                    }//end of if(strActiveLink.equals(strPreAuthorization))
                    else
                    {
                        ruleDataVO=ruleManagerObject.getPAClaimsRule(ClaimsWebBoardHelper.getClaimsSeqId(request),"CR");
                    }//end of else
                    request.setAttribute("updated","message.savedSuccessfully");
                }//end of if(lngRuleDataSeqID>0)

                ruleDataDocument=ruleDataVO.getRuleDocument();
                frmPreAuthRuleData = (DynaActionForm)FormUtils.setFormValues("frmPreAuthRuleData",ruleDataVO,this,
                        mapping,request);
                request.getSession().setAttribute("frmPreAuthRuleData",frmPreAuthRuleData);
                request.getSession().setAttribute("RuleDataDocument",ruleDataDocument);
            }//end of if(ruleDataDocument!=null)
            return this.getForward(getRuledataPath(strActiveLink),mapping,request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"preclarules"));
        }//end of catch(Exception exp)
    }//end of doUpdateRuleData(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response);

    /**
     * This method is used to collect the Information to be captured in the Rules which was not captured
     * in Database else where in the Application.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doModifyRuleData(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                                HttpServletResponse response) throws Exception {
        try
        {
            log.debug("PreAuthRuleDataAction Action - inside doModifyRuleData method....");
            setLinks(request);
            String strActiveLink=TTKCommon.getActiveLink(request);
            String strXpath="//clause/coverage[@allowed='3' and @selected='YES']/condition[@source='XML']";
            Document ruleDataDocument=(Document)request.getSession().getAttribute("RuleDataDocument");
            if(ruleDataDocument.selectSingleNode(strXpath)!=null)
            {
                return this.getForward(getModifyRuledataPath(strActiveLink),mapping,request);
            }//end of if(ruleDataDocument.selectSingleNode(strXpath)!=null)
            else
            {
                TTKException expTTK = new TTKException();
                expTTK.setMessage("error.rules.notpresent");
                throw expTTK;
            }//end of else
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"preclarules"));
        }//end of catch(Exception exp)
    }//end of doUpdateRuleData(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response);
/*Changes on 11th JAn 2012 KOc1099
     * 
     * 
     * 
     * 
     * 
     * 
     * */
    public ActionForward doSaveRemarks(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try
        {
        	setLinks(request);
            String strActiveLink=TTKCommon.getActiveLink(request);
            RuleManager ruleManagerObject=this.getRuleManagerObject();
            DynaActionForm frmPreAuthRuleData=(DynaActionForm)form;
            RuleVO ruleDataVO=null;
            Document ruleDataDocument = null;
            RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();
           
            ruleDataVO=(RuleVO)FormUtils.getFormValues(frmPreAuthRuleData,this,mapping, request);
            ruleDataDocument=(Document)request.getSession().getAttribute("RuleDataDocument");
            
            ruleDataDocument=ruleXMLHelper.updateRemarks(ruleDataDocument,request);
         //  
            ruleDataVO.setRuleDocument(ruleDataDocument);
          // 
            
           // 
            String OverrideCompletedYN=request.getParameter("OverrideCompletedYN");
           // 
            
          
            ruleDataVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
          
          //  ruleDataVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
          
        if(TTKCommon.checkNull(OverrideCompletedYN)!="")
          {
        	  ruleDataVO.setOverrideCompletedYN(OverrideCompletedYN); 
          }
          
          //-----------------------------------------------------------------------------//
          long lngRuleDataSeqID=0;

          //Clear the Current Results before Saving the modified Rules
          //ruleDataDocument=ruleXMLHelper.clearRuleResults(ruleDataDocument);

          //Call the DAO to save the Ruledata
          if(ruleDataVO.getRuleDataSeqID()!=null)     //for updating RuleData
          {
              ruleDataVO.setSeqID(ruleDataVO.getRuleDataSeqID());
              if(strActiveLink.equals(strPreAuthorization))
              {
                  lngRuleDataSeqID=ruleManagerObject.saveProdPolicyRule(ruleDataVO,"PRM");
                //  
              }//end of if(strActiveLink.equals(strPreAuthorization))
              else
              {
                  lngRuleDataSeqID=ruleManagerObject.saveProdPolicyRule(ruleDataVO,"CRM");
              }//end of else
          }//end of if(ruleDataVO.getRuleDataSeqID()!=null)

          if(lngRuleDataSeqID>0)       //requery to get the updated ruledata from the database
          {
              //requery to get the updated ruledata from the database
              if(strActiveLink.equals(strPreAuthorization))
              {
                  ruleDataVO=ruleManagerObject.getPAClaimsRule(PreAuthWebBoardHelper.getPreAuthSeqId(request),"PR");
              }//end of if(strActiveLink.equals(strPreAuthorization))
              else
              {
                  ruleDataVO=ruleManagerObject.getPAClaimsRule(ClaimsWebBoardHelper.getClaimsSeqId(request),"CR");
              }//end of else

              request.setAttribute("updated","message.savedSuccessfully");
          }//end of if(lngRuleDataSeqID>0)
          
          ruleDataDocument=ruleDataVO.getRuleDocument();
          frmPreAuthRuleData = (DynaActionForm)FormUtils.setFormValues("frmPreAuthRuleData",ruleDataVO,this,
                  mapping,request);

          request.getSession().setAttribute("frmPreAuthRuleData",frmPreAuthRuleData);
          request.getSession().setAttribute("RuleDataDocument",ruleDataDocument);
          
            
       
            
            //-----------------------------------------------------------------//
        /*String[]remarks=request.getParameterValues("remarks");
        for(int i=0;i<remarks.length;i++)
        {
        	
        }*/
        
            return this.getForward(getRuledataPath(strActiveLink),mapping,request);
            }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"preclarules"));
        }//end of catch(Exception exp)
    }//end of doSaveRemarks(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response);
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

    /**
     * This method returns the forward path of next view based on the Flow
     *
     * @param strActiveLink String current sublink
     * @return strClauseListPath String forward path for the next view
     */
    private String getCoverageListPath(String strActiveLink)
    {
        String strCoverageListPath="";
        if(strActiveLink.equals(strPreAuthorization))
        {
            strCoverageListPath=strPreauthCoverageList;
        }//end of if(strActiveLink.equals(strPreAuthorization))
        else if(strActiveLink.equals(strClaims))
        {
            strCoverageListPath=strClaimCoverageList;
        }//end of else if(strActiveLink.equals(strClaims))
        return strCoverageListPath;
    }//end of getClauseListPath(String strActiveSubLink)

    /**
     * This method returns the forward path of next view based on the Flow
     *
     * @param strActiveLink String current sublink
     * @return strClauseListPath String forward path for the next view
     */
    private String getRuledataPath(String strActiveLink)
    {
        String strRuledataPath="";
        if(strActiveLink.equals(strPreAuthorization))
        {
            strRuledataPath=strPreauthruledata;
        }//end of if(strActiveLink.equals(strPreAuthorization))
        else if(strActiveLink.equals(strClaims))
        {
            strRuledataPath=strClaimRuleData;
        }//end of else if(strActiveLink.equals(strClaims))
        return strRuledataPath;
    }//end of getRuledataPath(String strActiveSubLink)

    /**
     * This method returns the forward path of next view based on the Flow
     *
     * @param strActiveLink String current sublink
     * @return strClauseListPath String forward path for the next view
     */
    private String getModifyRuledataPath(String strActiveLink)
    {
        String strRuledataPath="";
        if(strActiveLink.equals(strPreAuthorization))
        {
            strRuledataPath=strModifyPreauthRuledata;
        }//end of if(strActiveLink.equals(strPreAuthorization))
        else if(strActiveLink.equals(strClaims))
        {
            strRuledataPath=strModifyClaimRuledata;
        }//end of else if(strActiveLink.equals(strClaims))
        return strRuledataPath;
    }//end of getModifyRuledataPath(String strActiveSubLink)
}//end of PreAuthRuleDataAction.java
