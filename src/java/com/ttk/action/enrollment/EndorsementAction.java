/**
 * @ (#) EndorsementAction.java 9th Feb 2006
 * Project      : TTK HealthCare Services
 * File         : EndorsementAction.java
 * Author       : Raghavendra T M
 * Company      : Span Systems Corporation
 * Date Created : 9th Feb 2006
 *
 * @author       : Raghavendra T M
 * Modified by   : Arun K N
 * Modified date : 10th May 2007
 * Reason        : Conversion to Dispatch Action
 */

package com.ttk.action.enrollment;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.enrollment.PolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.EventVO;
import com.ttk.dto.administration.WorkflowVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.enrollment.EndorsementVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This is a reusable class used for displaying the Endosement information.
 * This class also provides option for Addition , Updation of Endosement information
 * and this also provides the option of adding the policy in webboard in 
 * Individual Policy,Ind. Policy as Group,Corporate Policy and Non-Corporate Policy
 * of Enrollment.
 */
public class EndorsementAction extends TTKAction {
	private static Logger log = Logger.getLogger( EndorsementAction.class );
    // Modes
    private static final String strEndorsementDetails="endorsementdetails";
    //declaration of other constants used in this class
    private static final String strIndPolicyType="IP";
    private static final String strGrpPolicyType="IG";
    private static final String strCorpPolicyType="CP";
    private static final String strNonCorpPolicyType="NC";
    private static final String strIndPolicy="Individual Policy";
    private static final String strGrpPolicy="Ind. Policy as Group";
    private static final String strCorPolicy="Corporate Policy";
    private static final String strNonCorPolicy="Non-Corporate Policy";

    /**
     * This method is used to view the Endorsement details of selected policy
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewEndorsement(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside doViewEndorsement method of EndorsementAction");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            PolicyManager endorsementObject=this.getPolicyManagerObject();
            String strForwardPath=getForwardPath(strActiveSubLink);
            if(WebBoardHelper.checkWebBoardId(request)==null)
            {
                TTKException expTTK = new TTKException();
                expTTK.setMessage("error.endorsement.required");
                throw expTTK;
            }//end of if (WebBoardHelper.checkWebBoardId(request)==null)
            ArrayList alInsProducts = new ArrayList();
            //get the Endorsement details from database
            EndorsementVO endorsementVO=endorsementObject.getEndorsement(WebBoardHelper.getEndorsementSeqId(request));
            DynaActionForm  frmEndorsementDetails = (DynaActionForm)FormUtils.setFormValues("frmEndorsementDetails",
                    endorsementVO, this, mapping, request);

            if(!(TTKCommon.checkNull(frmEndorsementDetails.get("insuranceSeqID").toString()).equals("")))
            {
                alInsProducts = endorsementObject.getProductList(TTKCommon.getLong(
                						frmEndorsementDetails.getString("insuranceSeqID")),
                frmEndorsementDetails.getString("policyTypeID"));
            }//end of if(!(TTKCommon.checkNull(frmEndorsementDetails.get("insuranceSeqID").toString()).equals("")))

            frmEndorsementDetails.set("alInsProducts",alInsProducts);
            frmEndorsementDetails.set("caption",WebBoardHelper.getPolicyNumber(request));
            frmEndorsementDetails.set("policyTypeID",getPolicyType(strActiveSubLink));
            request.getSession().setAttribute("frmEndorsementDetails",frmEndorsementDetails);
            request.getSession().setAttribute("switchType", strSwitchType);
            request.getSession().setAttribute("EnrollBatchSeqID", endorsementVO.getEnrollBatchSeqID());
            this.documentViewer(request,endorsementVO);
            return this.getForward(strForwardPath, mapping, request);
        }// end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "endorsementdetails"));
        }//end of catch(Exception exp)
    }//end of doViewEndorsement(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    																//HttpServletResponse response)

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
    	log.debug("Inside doChangeWebBoard method of doChangeWebBoard");
        //action is similar to View the Endorsement details of the Policy from new Web board item
        return doViewEndorsement(mapping,form,request,response);
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    																//HttpServletResponse response)

    /**
     * This method is used to reload the screen when the reset button is pressed.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
    	log.debug("Inside doReset method of doChangeWebBoard");
        //action is similar to View the Endorsement details of the Policy from new Web board item
        return doViewEndorsement(mapping,form,request,response);
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to add/update the record.
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
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside doSave method of DomiciliaryAction");
			  
        	DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            PolicyManager endorsementObject=this.getPolicyManagerObject();
            String strForwardPath=getForwardPath(strActiveSubLink);

            EndorsementVO endorsementVO = new EndorsementVO();
            DynaActionForm frmEndorsementDetails = (DynaActionForm)
            											request.getSession().getAttribute("frmEndorsementDetails");
            frmEndorsementDetails.set("policyType",getPolicyType(strActiveSubLink));
            endorsementVO=(EndorsementVO)FormUtils.getFormValues(frmEndorsementDetails,
                    "frmEndorsementDetails",this, mapping, request);
            endorsementVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
            //call the business object to save the endorsement
            int iResult = endorsementObject.saveEndorsement(endorsementVO);
            if((iResult!=0))
            {
                if(!(frmEndorsementDetails.getString("endorsementSeqID").equals("")))
                {
                    request.setAttribute("updated","message.savedSuccessfully");
                }// end of if(!(frmEndorsementDetails.getString("endorsementSeqID").equals("")))
                else
                {
                    request.setAttribute("updated","message.addedSuccessfully");
                }//end of else

                endorsementVO = endorsementObject.getEndorsement(TTKCommon.getLong(frmEndorsementDetails.
                        getString("endorsementSeqID")));
                Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
                ArrayList<Object> alCacheObject = new ArrayList<Object>();
                CacheObject cacheObject = new CacheObject();
                cacheObject.setCacheId(this.prepareWebBoardId(endorsementVO)); //set the cacheID
                cacheObject.setCacheDesc(endorsementVO.getEndorsementNbr());
                alCacheObject.add(cacheObject);
                //if the object(s) are added to the web board, set the current web board id
                toolbar.getWebBoard().addToWebBoardList(alCacheObject);
                toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
                request.setAttribute("webboardinvoked", "true");
                ArrayList alInsProducts = new ArrayList();
                frmEndorsementDetails = (DynaActionForm)FormUtils.setFormValues("frmEndorsementDetails",
                        endorsementVO,this,mapping, request);
                if(!(TTKCommon.checkNull(frmEndorsementDetails.get("insuranceSeqID").toString()).equals("")))
                {
                  alInsProducts = endorsementObject.getProductList(TTKCommon.getLong(
                   frmEndorsementDetails.getString("insuranceSeqID")),frmEndorsementDetails.getString("policyTypeID"));
                }//end of if(!(TTKCommon.checkNull(frmEndorsementDetails.get("insuranceSeqID").toString()).equals("")))
                frmEndorsementDetails.set("alInsProducts",alInsProducts);
                frmEndorsementDetails.set("caption",WebBoardHelper.getPolicyNumber(request));
                frmEndorsementDetails.set("policyTypeID",getPolicyType(strActiveSubLink));
                request.getSession().setAttribute("frmEndorsementDetails",frmEndorsementDetails);
                this.documentViewer(request,endorsementVO);
            }//end of if(iResult!=0)
            return this.getForward(strForwardPath, mapping, request);
        }// end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "endorsementdetails"));
        }//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to do Review/Promote the Policy to next level
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doReview(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside doReview method of DomiciliaryAction");
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            DynaActionForm frmEndorsementDetail= (DynaActionForm)form;
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            PolicyManager endorsementObject=this.getPolicyManagerObject();
            String strForwardPath=getForwardPath(strActiveSubLink);

            EndorsementVO endorsementVO = (EndorsementVO)FormUtils.getFormValues(frmEndorsementDetail,
                    this,mapping,request);
            endorsementVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id

            //call business layer to review/promote endorsement
            endorsementVO=endorsementObject.saveEndorsementReview(endorsementVO,
                    strSwitchType,getPolicyType(strActiveSubLink));

            frmEndorsementDetail.set("eventSeqID",endorsementVO.getEventSeqID().toString());
            frmEndorsementDetail.set("eventName",endorsementVO.getEventName());
            frmEndorsementDetail.set("reviewCount",endorsementVO.getReviewCount().toString());
            frmEndorsementDetail.set("requiredReviewCnt",endorsementVO.getRequiredReviewCnt().toString());
            frmEndorsementDetail.set("review",endorsementVO.getReview());

            //check whether user is having the permession for next Event.
            boolean blnReviewPermession=checkReviewPermession(endorsementVO.getEventSeqID(),strSwitchType,request);
            if(!blnReviewPermession)
            {
                //delete the Policies from the web board if any
                if(strSwitchType.equals("ENM"))
                {
                    request.setAttribute("cacheId","|"+WebBoardHelper.getPolicySeqId(request)+"|");
                }//end of if(strSwitchType.equals("ENM"))
                else if(strSwitchType.equals("END"))
                {
                    request.setAttribute("cacheId","|"+WebBoardHelper.getEndorsementSeqId(request)+"|");
                }//end of else if(strSwitchType.equals("END"))

                WebBoardHelper.deleteWebBoardId(request);
                request.setAttribute("webboardinvoked","true");
                //After deleting if web board is null display the error message.
                if(WebBoardHelper.checkWebBoardId(request)==null)
                {
                    TTKException expTTK = new TTKException();
                    frmEndorsementDetail.set("display","display");
                    if(strSwitchType.equals("ENM"))
                    {
                        expTTK.setMessage("error.enrollment.required");
                    }//end of if(strSwitchType.equals("ENM"))
                    else
                    {
                        expTTK.setMessage("error.endorsement.required");
                    }//end of else
                    throw expTTK;
                }//end of if(WebBoardHelper.checkWebBoardId(request)==null)
                else
                {
                    //requery to get the saved data
                    endorsementVO=endorsementObject.getEndorsement(WebBoardHelper.getEndorsementSeqId(request));
                    frmEndorsementDetail.initialize(mapping);
                    frmEndorsementDetail= (DynaActionForm)FormUtils.setFormValues("frmEndorsementDetails",
                            endorsementVO, this, mapping, request);
                    frmEndorsementDetail.set("display",null);
                    request.getSession().setAttribute("frmEndorsementDetails",frmEndorsementDetail);
                    this.documentViewer(request,endorsementVO);
                }//end of else
            }//end of if(!blnReviewPermession)
            return this.getForward(strForwardPath, mapping, request);
        }// end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "endorsementdetails"));
        }//end of catch(Exception exp)
    }//end of doReview(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside doClose method of DomiciliaryAction");
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            DynaActionForm frmEndorsementDetail= (DynaActionForm)form;
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            String strForwardPath=getForwardPath(strActiveSubLink);

            frmEndorsementDetail.set("frmChanged","changed");
            return this.getForward(strForwardPath, mapping, request);
        }// end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "endorsementdetails"));
        }//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


    /**
     * This method is will update the selected insurance compnay's info in the required form and
     * moves it the next screen.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSelectInsuranceCompany(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside doSelectInsuranceCompany method of DomiciliaryAction");
            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            return mapping.findForward("endorsementedit");
        }// end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"endorsementdetails"));
        }//end of catch(Exception exp)
    }//end of doSelectInsuranceCompany(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    																			//HttpServletResponse response)

   
    /**
     * This method prepares the Weboard id for the selected Policy
     * @param policyVO  PolicyVO for which webboard id to be prepared
     * @param strSwitchType identifier for enrollment or endorsement flow
     * @return Web board id for the passedVO
     */
    private String prepareWebBoardId(EndorsementVO endorsementVO)throws TTKException
    {
        StringBuffer sbfCacheId=new StringBuffer();
        //if Endorsement Seq id is not there then empty String with space is used as identifier
        sbfCacheId.append(endorsementVO.getEndorsementSeqID()!=null? String.valueOf(
        																endorsementVO.getEndorsementSeqID()):" "); 
        // 	if policy seq id is not there then empty String with space is used as identifier
        sbfCacheId.append("~#~").append(endorsementVO.getPolicySeqID()!=null?
        														String.valueOf(endorsementVO.getPolicySeqID()):" ");  
        sbfCacheId.append("~#~").append(TTKCommon.checkNull(String.valueOf(endorsementVO.getPolicyNbr())));
        sbfCacheId.append("~#~").append(TTKCommon.checkNull(endorsementVO.getPolicyYN()).equals("")?
        								"N":endorsementVO.getPolicyYN());     //identifier to check for valid Policy
        //if Group Id is not there then empty String with space is used as identifier
        sbfCacheId.append("~#~").append(TTKCommon.checkNull(endorsementVO.getGroupID()).equals("")
        		? " ":endorsementVO.getGroupID());  
        //  if Group Name is not there then empty String with space is used as identifier
        sbfCacheId.append("~#~").append(TTKCommon.checkNull(endorsementVO.getGroupName()).equals("")
        		? " ":endorsementVO.getGroupName());
        //if Completed_yn is not there then empty String with space is used as identifier
        sbfCacheId.append("~#~").append(" ");  
        log.debug("Cache id is........"+sbfCacheId.toString());
        return sbfCacheId.toString();
    }//end of prepareWebBoardId(PolicyVO policyVO,String strSwitchType)

    /**
     * Methods checks whether user is having permession for the next Event.
     * @param lngEventSeqId Long Event_Seq_Id.
     * @param strSwitchType String SwitchType.
     * @param request HttpServletRequest object.
     * @return blnPermession boolean.
     */
    private boolean checkReviewPermession(Long lngEventSeqId,String strSwitchType,HttpServletRequest request)
    {
        boolean blnPermession=false;
        WorkflowVO workFlowVO=null;
        ArrayList alEventId=null;
        //get the HashMap from UserSecurityProfile
        HashMap hmWorkFlow=((UserSecurityProfile)request.getSession().getAttribute(
        		"UserSecurityProfile")).getWorkFlowMap();
        if(strSwitchType.equals("ENM"))
        {
            workFlowVO=(WorkflowVO)hmWorkFlow.get(new Long(1));
        }//end of if(strSwitchType.equals("ENM"))
        else if(strSwitchType.equals("END"))
        {
            workFlowVO=(WorkflowVO)hmWorkFlow.get(new Long(2));
        }//end of else if(strSwitchType.equals("END"))

        //get the arrayList which is having event information of the particular user.
        if(workFlowVO!=null)
        {
            alEventId=workFlowVO.getEventVO();
        }//end of if(workFlowVO!=null)
        //compare the current policy EventSeqId with the User permession.
        if(alEventId!=null)
        {
            for(int i=0;i<alEventId.size();i++)
            {
                if(lngEventSeqId==((EventVO)alEventId.get(i)).getEventSeqID())
                {
                    blnPermession=true;
                    break;
                }//end of if(lngEventSeqId==((EventVO)alEventId.get(i)).getEventSeqID())
            }//end of for(int i=0;i<alEventId.size();i++)
        }//end of if(alEventId!=null)
        return blnPermession;
    }//end of checkReviewPermession(Long lngEventSeqId,String strSwitchType,HttpServletRequest request)

    /**
     * Returns the PolicyManager session object for invoking methods on it.
     * @return PolicyManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private PolicyManager getPolicyManagerObject() throws TTKException
    {
        PolicyManager policyManager = null;
        try
        {
            if(policyManager == null)
            {
                InitialContext ctx = new InitialContext();
                policyManager = (PolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/PolicyManagerBean!com.ttk.business.enrollment.PolicyManager");
                log.debug("Inside PolicyManager: policyManager: " + policyManager);
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strEndorsementDetails);
        }//end of catch
        return policyManager;
    }//end of getPolicyManagerObject()

    /**
     * This menthod for document viewer information
     * @param request HttpServletRequest object which contains hospital information.
     * @param endorsementVO PolicyDetailVO object which contains policy information.
     * @exception throws TTKException
     */
    private void documentViewer(HttpServletRequest request, EndorsementVO endorsementVO) throws TTKException
    {
        ArrayList<String> alDocviewParams = new ArrayList<String>();
        alDocviewParams.add("leftlink="+TTKCommon.getActiveLink(request));
        alDocviewParams.add("policy_number="+WebBoardHelper.getPolicyNumber(request));
        alDocviewParams.add("dms_reference_number="+endorsementVO.getDMSRefID());
        if(request.getSession().getAttribute("toolbar")!=null)
        {
            ((Toolbar)request.getSession().getAttribute("toolbar")).setDocViewParams(alDocviewParams);
        }//end of if(request.getSession().getAttribute("toolbar")!=null)
    }//end of documentViewer(HttpServletRequest request, PolicyDetailVO policyDetailVO)

    /**
     * This method returns the forward path for next view based on the Flow
     *
     * @param strActiveSubLink String current sublink
     * @return strForwardPath String forward path for the next view
     */
    private String getForwardPath(String strActiveSubLink)
    {
        String strForwardPath=null;

        if(strActiveSubLink.equals(strIndPolicy))
        {
            strForwardPath="individualpolicy";
        }//end of if(strActiveSubLink.equals(strIndPolicy))
        else if (strActiveSubLink.equals(strGrpPolicy))
        {
            strForwardPath="indpolicyasgroup";
        }//end of else if (strActiveSubLink.equals(strIndGrpPolicy))
        else if (strActiveSubLink.equals(strCorPolicy))
        {
            strForwardPath="corporatepolicy";
        }//end of else if (strActiveSubLink.equals(strCorporatePolicy))
        else if (strActiveSubLink.equals(strNonCorPolicy))
        {
            strForwardPath="noncorporatepolicy";
        }//end of else if (strActiveSubLink.equals(strNonCorporatePolicy))
        return strForwardPath;
    }//end of getForwardPath(String strActiveSubLink)

    /**
     * This method returns the identifier of the policy type based on the flow
     *
     * @param strActiveSubLink current sublink
     * @return strPolicyType String Policy type Identfier
     */
    private String getPolicyType(String strActiveSubLink)
    {
        String strPolicyType=null;

        if(strActiveSubLink.equals(strIndPolicy))
        {
            strPolicyType=strIndPolicyType;
        }//end of if(strActiveSubLink.equals(strIndPolicy))
        else if (strActiveSubLink.equals(strGrpPolicy))
        {
            strPolicyType=strGrpPolicyType;
        }//end of else if (strActiveSubLink.equals(strIndGrpPolicy))
        else if (strActiveSubLink.equals(strCorPolicy))
        {
            strPolicyType=strCorpPolicyType;
        }//end of else if (strActiveSubLink.equals(strCorporatePolicy))
        else if (strActiveSubLink.equals(strNonCorPolicy))
        {
            strPolicyType=strNonCorpPolicyType;
        }//end of else if (strActiveSubLink.equals(strNonCorporatePolicy))
        return strPolicyType;
    }//end of getPolicyType(String strActiveSubLink)
}//end of EndorsementAction

