/**
 * @ (#) PEDAction.java Feb 6, 2006
 * Project 		: TTK HealthCare Services
 * File 		: PEDAction.java
 * Author 		: Pradeep R
 * Company 		: Span Systems Corporation
 * Date Created : Feb 6, 2006
 *
 * @author 		: Pradeep R
 * Modified by 	:
 * Modified date:
 * Reason 		:
 */

package com.ttk.action.enrollment;

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
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.action.tree.TreeData;
import com.ttk.business.claims.ClaimManager;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.business.enrollment.MemberManagerBean;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.CodingClaimsWebBoardHelper;
import com.ttk.common.CodingWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.PEDVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is reusable one,used to list PEDs,add/edit and delete PED,
 * in enrollment,endorsement,pre-auth and claims flows.
 * This class also provides option for listing ICD codes in enrollment,endorsement,
 * pre-auth and claims flows.
 */

public class PEDAction extends TTKAction
{
    private static Logger log = Logger.getLogger( PEDAction.class );
    
    //sub links
    private static final String strIndividualPolicy="Individual Policy";
    private static final String strIndPolicyGroup="Ind. Policy as Group";
    private static final String strCorporatePolicy="Corporate Policy";
    private static final String strNonCorpPolicy="Non-Corporate Policy";
    
    //for forwarding on clicking root icon
    private static final String strIndividualPED="individualped";
    private static final String strIndPolicyGroupPED="indpolicygroupped";
    private static final String strCorporatePED="corporateped";
    private static final String strNonCorporatePED="noncorporateped";
    
    //for adding New PED and edit PED from ped detail screen
    private static final String strAddIndPEDDetail="addindpeddetail";
    private static final String strAddIndGrpDetail="addindgrppeddetail";
    private static final String strAddCrpPEDDetail="addcrppeddetail";
    private static final String strAddNONCrpPEDDetil="addnoncrpeddetail";
    
    //on click of close in ADD PED screen
    private static final String strIndividualClose="closeindividual";
    private static final String strIndPolicyGroupClose="closeindpolicygroup";
    private static final String strCorporateClose="closecorporate";
    private static final String strCorporateRenewClose="closerenewcorporate";
    private static final String strNonCorporateClose="closenoncorporate";
    
    //add ped detail screen
    private static final String strEnrollICDList="enrollicdlist";
    //private static final String strAddPED="addped";
    
    //forwards for pre-auth
    private static final String strPreAuth="preauth";
    private static final String strProcessing="Processing";
    private static final String strPreAuthUpdate="preauthupdate";
    private static final String strPreAuthPEDClose="preauthpedclose"; // on click of close button in the ped list screen
    private static final String strPreAuthDetailClose="preauthdetailclose";
    private static final String strCodePreauthDetailClose="codepreauthdetailclose";
    private static final String strPreAuthICDList="preauthicdlist";
    private static final String strPreAuthSave="preauthsave";
    private static final String strClaimSave="claimsave";
    private static final String strCodePreauthPed="codepreauth";
    private static final String strCodePreauthPEDClose="codepreauthpedclose";
    private static final String strCleanupPreauthPed="cleanuppreauthped";
    private static final String strCloseCleanupPreauthPed="closecleanuppreauth";
    //forwards for claims
    private static final String strClaimsPed= "claimsped";
    private static final String strClaimsUpdate="claimsupdate";
    private static final String strCodingPreUpdate="codingpreupdate"; 
    private static final String strCodingClmUpdate="codingclmupdate";
    
    private static final String strClaimsDetailClose="claimsdetailclose";
    private static final String strCodeClaimsDetailClose="codeclaimsdetailclose";
    private static final String strClaimsPEDClose="claimspedclose";
    private static final String strClaimsICDList="claimsicdlist";
    private static final String strCodeClaimsPed="codeclaimsped";
    private static final String strCodeClaimsPEDClose="codeclaimspedclose";
    private static final String strCleanupClaimsPed="cleanupclaimsped";
    private static final String strCloseCleanupClaimsPed="closecleanupclaims";
    //Links
    private static final String strPreAuthorization="Pre-Authorization";
    private static final String strCoding="Coding";
    private static final String strClaims="Claims";
    private static final String strEnrollment="Enrollment";
    
    //Exception Message Identifier
    private static final String strPEDError="addped";

    /**
     * This method is used to navigate to detail screen to add a record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSearchPED(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSearchPED method of PEDAction");
    		DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
    		String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
    		this.setLinks(request,strSwitchType);
    		String strLink=TTKCommon.getActiveLink(request);
    		TreeData treeData=null;
    		TableData  PEDTableData =null;
    		String strForward="";
    		MemberManager memberManagerObject=this.getMemberManagerObject();
            if((request.getSession()).getAttribute("PEDTableData") == null)
            {
                PEDTableData = new TableData();
            }//end of if((request.getSession()).getAttribute("PEDTableData") == null)
            else
            {
                PEDTableData = (TableData)(request.getSession()).getAttribute("PEDTableData");
            }//end of else
    		treeData =(TreeData)request.getSession().getAttribute("treeData") ;
            //Fetching selected root and node value from request
            String strSelectedRoot=request.getParameter("selectedroot");
            String strSelectedNode=request.getParameter("selectednode");
            DynaActionForm formAddPED=(DynaActionForm)form;
            PEDVO pedVO= new PEDVO();
            strForward = this.getAddForwardPath(request);
            //getting the value object from tree based on selected root and node
            MemberVO memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
            pedVO.setMemberSeqID(memberVO.getMemberSeqID());
            String strFullName=memberVO.getName();
            //Extracting the required value for caption(s)
            String strName[]=strFullName.split("/");
            formAddPED = (DynaActionForm)FormUtils.setFormValues("frmAddPED",pedVO,this,mapping,request);
            formAddPED.set("switchType",strSwitchType);
            StringBuffer sbfCaption= new StringBuffer();
            sbfCaption.append("[ "+strName[0]+" ] [ ").append(WebBoardHelper.getPolicyNumber(request)).
            	append(" ] [ ").append(strName[0]).append(" ]");
            
			if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
			{
				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
			}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
            formAddPED.set("memberName",strName[0]);
            formAddPED.set("caption",sbfCaption.toString());
            //create new table data object
            PEDTableData = new TableData();
            // create the required grid table
            PEDTableData.createTableInfo("AddPEDTable",null);
            if("Code CleanUp".equals(strLink)){
            	((Column)((ArrayList)PEDTableData.getTitle()).get(0)).setIsLink(false);//disable the hyper link
            	((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);	//get(5) is changed to get(6) for koc 1278
            	((Column)((ArrayList)PEDTableData.getTitle()).get(7)).setVisibility(false);	//added for koc 1278
            }//end of if("Code CleanUp".equals(strLink)
            ((Column)((ArrayList)PEDTableData.getTitle()).get(1)).setVisibility(false);
            ArrayList alMember=memberManagerObject.getPEDList(memberVO.getMemberSeqID());
            PEDTableData.setData(alMember);
            formAddPED.set("flow","ENR");
            //setting the form and table objects to session
            formAddPED.set("memberSeqID",memberVO.getMemberSeqID().toString());
            request.getSession().setAttribute("frmAddPED",formAddPED);
            request.getSession().setAttribute("PEDTableData",PEDTableData);
            return this.getForward(strForward, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPEDError));
		}//end of catch(Exception exp)
    }//end of doSearchPED(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to navigate to detail screen to add a record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSearchPreauthPED(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSearchPreauthPED method of PEDAction");
    		setLinks(request);
    		String strSwitchType="";
    		TableData  PEDTableData =null;
    		String strForward="";
    		strSwitchType="ENM";
    		String strLink=TTKCommon.getActiveLink(request);
    		String strSubLink = TTKCommon.getActiveSubLink(request);
    		DynaActionForm frmAddPED=(DynaActionForm)request.getSession().getAttribute("frmAddPED");
    		frmAddPED.initialize(mapping);
    		MemberManager memberManagerObject=this.getMemberManagerObject();
            if((request.getSession()).getAttribute("PEDTableData") == null)
            {
                PEDTableData = new TableData();
            }//end of if((request.getSession()).getAttribute("PEDTableData") == null)
            else
            {
                PEDTableData = (TableData)(request.getSession()).getAttribute("PEDTableData");
            }//end of else
    		
            DynaActionForm frmPreAuthAddPED=(DynaActionForm)form;
            PEDTableData = new TableData();
            // create the required grid table
           	PEDTableData.createTableInfo("AddPEDTable",null);
            if("Code CleanUp".equals(strLink)){
            	((Column)((ArrayList)PEDTableData.getTitle()).get(0)).setIsLink(false);//disable the hyper link
            	((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);	//get(5) is changed to get(6) fro koc 1278
            	((Column)((ArrayList)PEDTableData.getTitle()).get(7)).setVisibility(false);//added for koc 1278
            }
            ArrayList alMember=new ArrayList();
            StringBuffer sbfCaption= new StringBuffer();
            if(strLink.equals(strPreAuthorization))
            {
                alMember=memberManagerObject.getPreauthPEDList(PreAuthWebBoardHelper.getMemberSeqId(request),
                		 PreAuthWebBoardHelper.getPreAuthSeqId(request),TTKCommon.getUserSeqId(request),"PAT");
                sbfCaption.append("[ ").append(PreAuthWebBoardHelper.getClaimantName(request)).append(" ] [ ").
                append(PreAuthWebBoardHelper.getWebBoardDesc(request)).append(" ]");
                if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
                {
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
                }//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
                ((Column)((ArrayList)PEDTableData.getTitle()).get(2)).setVisibility(false);//added for koc 1278
            	((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);//added for koc 1278
            }//end of if(strLink.equals(strPreAuthorization))
            if(strLink.equals(strClaims))
            {
                log.info("PED doSearchPED"+ClaimsWebBoardHelper.getMemberSeqId(request)+" "+ClaimsWebBoardHelper.getClaimsSeqId(request));
            	alMember=memberManagerObject.getPreauthPEDList(ClaimsWebBoardHelper.getMemberSeqId(request),
                		ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request),"CLM");
                sbfCaption.append("[ ").append(ClaimsWebBoardHelper.getClaimantName(request)).append(" ] [ ").
                 		append(PreAuthWebBoardHelper.getWebBoardDesc(request)).append(" ]");
                if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
                {
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
                }//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
                ((Column)((ArrayList)PEDTableData.getTitle()).get(2)).setVisibility(false);//added for koc 1278
                ((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);//added for koc 1278
            }//end of if(strLink.equals(strClaims))
            if(strLink.equals("Coding"))
			{
				if(strSubLink.equals("PreAuth")){
					alMember=memberManagerObject.getPreauthPEDList(CodingWebBoardHelper.getMemberSeqId(request),
							CodingWebBoardHelper.getPreAuthSeqId(request),TTKCommon.getUserSeqId(request),"PAT");
	                sbfCaption.append("[ ").append(CodingWebBoardHelper.getClaimantName(request)).append(" ] [ ").
	                append(CodingWebBoardHelper.getWebBoardDesc(request)).append(" ]");
	                if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
	                {
	    				sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
	                }//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
	                strForward=strCodePreauthPed;
	                ((Column)((ArrayList)PEDTableData.getTitle()).get(2)).setVisibility(false);//added for koc 1278
	                ((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);//added for koc 1278
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims")){
					alMember=memberManagerObject.getPreauthPEDList(CodingClaimsWebBoardHelper.getMemberSeqId(request),
							CodingClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request),"PAT");
	                sbfCaption.append("[ ").append(CodingClaimsWebBoardHelper.getClaimantName(request)).append(" ] [ ").
	                append(CodingClaimsWebBoardHelper.getWebBoardDesc(request)).append(" ]");
	                if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
	                {
	    				sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
	                }//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
	                strForward=strCodeClaimsPed;
	                ((Column)((ArrayList)PEDTableData.getTitle()).get(2)).setVisibility(false);//added for koc 1278
	                ((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);//added for koc 1278
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
            if(strLink.equals("Code CleanUp"))
			{
				if(strSubLink.equals("PreAuth")){
					alMember=memberManagerObject.getPreauthPEDList(CodingWebBoardHelper.getMemberSeqId(request),
							CodingWebBoardHelper.getPreAuthSeqId(request),TTKCommon.getUserSeqId(request),"PAT");
	                sbfCaption.append("[ ").append(CodingWebBoardHelper.getClaimantName(request)).append(" ] [ ").
	                append(CodingWebBoardHelper.getWebBoardDesc(request)).append(" ]");
	                if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
	                {
	    				sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
	                }//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
	                strForward=strCleanupPreauthPed;
	                ((Column)((ArrayList)PEDTableData.getTitle()).get(2)).setVisibility(false);//added for koc 1278
	                ((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);//added for koc 1278
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims")){
					alMember=memberManagerObject.getPreauthPEDList(CodingClaimsWebBoardHelper.getMemberSeqId(request),
							CodingClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request),"PAT");
	                sbfCaption.append("[ ").append(CodingClaimsWebBoardHelper.getClaimantName(request)).append(" ] [ ").
	                append(CodingClaimsWebBoardHelper.getWebBoardDesc(request)).append(" ]");
	                if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
	                {
	    				sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
	                }//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
	                strForward=strCleanupClaimsPed;
	                ((Column)((ArrayList)PEDTableData.getTitle()).get(2)).setVisibility(false);//added for koc 1278
	                ((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);//added for koc 1278
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Code CleanUp"))
            PEDTableData.setData(alMember);
            //setting the form and table objects to session
            request.getSession().setAttribute("PEDTableData",PEDTableData);
            frmPreAuthAddPED.set("switchType",strSwitchType);
            frmPreAuthAddPED.set("caption",sbfCaption.toString());
            frmPreAuthAddPED.set("flow","PRE");
            request.getSession().setAttribute("frmAddPED",frmPreAuthAddPED);
           if(strLink.equals(strPreAuthorization))
           {
               strForward=strPreAuth;
           }//end of if(strLink.equals(strPreAuthorization))
           if(strLink.equals(strClaims))
           {
               strForward=strClaimsPed;
           }//end of if(strLink.equals(strClaims))
            return this.getForward(strForward, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPEDError));
		}//end of catch(Exception exp)
    }//end of doSearchPreauthPED(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to navigate to detail screen to edit selected record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewICDCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewICDCode method of PEDAction");
    		String strSubLinks=TTKCommon.getActiveSubLink(request);
            String strSwitchType="";
            String strForward="";
            MemberManager memberManagerObject=this.getMemberManagerObject();
            // if true then it is from Pre_Auth flow else it is for Enrollment flow
            if(strSubLinks.equals("Processing"))
            {
                setLinks(request);
                strSwitchType="ENM";
            }//end of if(strSubLinks.equals("Processing"))
            else
            {
                DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
                strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
                this.setLinks(request,strSwitchType);
            }//end of else
            DynaActionForm frmAddPED =(DynaActionForm)form;
            Long lngPedCodeId=TTKCommon.getLong(frmAddPED.getString("PEDCodeID"));
            strForward = this.getSaveForwardPath(request);
            if(lngPedCodeId==null)
            {
                lngPedCodeId=new Long(-1);
            }//end of if(lngPedCodeId==null)
            PEDVO PedVO=memberManagerObject.getDescriptionList(lngPedCodeId);
            if(PedVO!=null)
            {
                frmAddPED.set("otherDesc",PedVO.getDescription());
                frmAddPED.set("ICD",PedVO.getICDCode());
            }//end of if(PedVO!=null)
            else
            {
                frmAddPED.set("otherDesc","");
                frmAddPED.set("ICD","");
            }//end of else
            frmAddPED.set("frmChanged","changed");
            return this.getForward(strForward, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPEDError));
		}//end of catch(Exception exp)
    }//end of doViewICDCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to navigate to detail screen to edit selected record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewICDList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewICDList method of PEDAction");
    		String strSubLinks=TTKCommon.getActiveSubLink(request);
    		String strLink=TTKCommon.getActiveLink(request);
            String strSwitchType="";
            // if true then it is from Pre_Auth flow else it is for Enrollment flow
            if(strSubLinks.equals("Processing"))
            {
                setLinks(request);
                strSwitchType="ENM";
            }//end of if(strSubLinks.equals("Processing"))
            else
            {
                DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
                strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
                this.setLinks(request,strSwitchType);
                //treeData =(TreeData)request.getSession().getAttribute("treeData") ;
            }//end of else
            DynaActionForm formAddPED=(DynaActionForm)form;
            formAddPED.set("switchType",strSwitchType);
            formAddPED.set("frmChanged","changed");
            if(strLink.equals(strPreAuthorization))
            {
                return mapping.findForward(strPreAuthICDList);
            }//end of if(strLink.equals(strPreAuthorization))
            else if(strLink.equals(strClaims))
            {
                return mapping.findForward(strClaimsICDList);
            }//end of else if(strLink.equals(strClaims))
            else
            {
                return mapping.findForward(strEnrollICDList);
            }//end of else
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPEDError));
		}//end of catch(Exception exp)
    }//end of doViewICDList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to delete the selected record(s) in Search Grid.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doDeleteList method of PEDAction");
    		String strSubLinks=TTKCommon.getActiveSubLink(request);
    		String strSwitchType="";
            //TreeData treeData=null;
            // if true then it is from Pre_Auth flow else it is for Enrollment flow
            if(strSubLinks.equals("Processing"))
            {
                setLinks(request);
                strSwitchType="ENM";
            }//end of if(strSubLinks.equals("Processing"))
            else
            {
                DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
                strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
                this.setLinks(request,strSwitchType);
                //treeData =(TreeData)request.getSession().getAttribute("treeData") ;
            }//end of else
            MemberManager memberManagerObject=this.getMemberManagerObject();
            String strForward="";
            String strPolicyType="";
            String strLink=TTKCommon.getActiveLink(request);
            if(strSubLinks.equals(strIndividualPolicy))
            {
                strPolicyType="IP";
            }//end of if(strSubLinks.equals(strIndividualPolicy))
            else if(strSubLinks.equals(strIndPolicyGroup))
            {
                strPolicyType="IG";
            }//end of else if(strSubLinks.equals(strIndPolicyGroup))
            else if(strSubLinks.equals(strCorporatePolicy))
            {
                strPolicyType="CP";
            }//end of else if(strSubLinks.equals(strCorporatePolicy))
            else if(strSubLinks.equals(strNonCorpPolicy))
            {
                strPolicyType="NC";
            }//end of else if(strSubLinks.equals(strNonCorpPolicy))
            DynaActionForm formAddPED=(DynaActionForm)form;
            formAddPED.set("switchType",strSwitchType);
            strForward = this.getAddForwardPath(request);
            TableData  PEDTableData =null;
            if((request.getSession()).getAttribute("PEDTableData") == null)
            {
                PEDTableData = new TableData();
            }//end of if((request.getSession()).getAttribute("PEDTableData") == null)
            else
            {
                PEDTableData = (TableData)(request.getSession()).getAttribute("PEDTableData");
            }//end of else
            Long lngMemberSeqId=TTKCommon.getLong(formAddPED.getString("memberSeqID"));
            StringBuffer sbfDeleteId = new StringBuffer();
            //fetch the id(s) to be deleted
            sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().getAttribute("PEDTableData")));
            ArrayList <Object>alParameter=new ArrayList<Object>();
            //Based on the link the control calls either deletePED()in Enrollment flow or deletePATGeneral() in Pre_Auth flow
            if(strLink.equals(strEnrollment))
            {
                alParameter.add("PED");
                alParameter.add(sbfDeleteId.toString());
                if(strSwitchType.equals("ENM"))
                {
                    alParameter.add(WebBoardHelper.getPolicySeqId(request));
                }//end of if(strSwitchType.equals("ENM"))
                else
                {
                    alParameter.add(WebBoardHelper.getEndorsementSeqId(request));
                }//end of else
                alParameter.add(strSwitchType);
                alParameter.add(strPolicyType);
                alParameter.add(TTKCommon.getUserSeqId(request));
                //delete the PED Details
                memberManagerObject.deletePED(alParameter);
            }//end of if(strLink.equals("Enrollment"))
            if(strLink.equals(strPreAuthorization) || strLink.equals(strClaims))// Pre_Auth flow and claims flow
            {
                PreAuthManager preAuthManager =this.getPreAuthManagerObject();
                ClaimManager claimManager=this.getClaimManagerObject();
                alParameter.add("PED");
                alParameter.add(sbfDeleteId.toString());
                if(strLink.equals(strPreAuthorization))
                {
                    alParameter.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request));//PAT_ENROLL_DETAIL_SEQ_ID
                    alParameter.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));//PAT_GENERAL_DETAIL_SEQ_ID
                    alParameter.add(TTKCommon.getUserSeqId(request));
                    preAuthManager.deletePATGeneral(alParameter);
                }//end of if(strLink.equals(strPreAuthorization))
                if(strLink.equals(strClaims))
                {
                    alParameter.add(ClaimsWebBoardHelper.getEnrollDetailSeqId(request));
                    alParameter.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
                    alParameter.add(TTKCommon.getUserSeqId(request));
                    claimManager.deleteClaimGeneral(alParameter);
                }//end of if(strLink.equals(strClaims))
             }//end of if(strLink.equals(strPreAuthorization) || strLink.equals(strClaims))
            Cache.refresh("icdDescription");
            //refresh the grid with search data
            ArrayList alPEDGroup = null;
            PEDTableData = new TableData();
            // create the required grid table
           	PEDTableData.createTableInfo("AddPEDTable",null);
            if("Coding".equals(strLink)||"Code CleanUp".equals(strLink)){
            	((Column)((ArrayList)PEDTableData.getTitle()).get(0)).setIsLink(false);//disable the hyper link
            	((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);	//get(5) is changed to get(6) for koc 1278
            	((Column)((ArrayList)PEDTableData.getTitle()).get(7)).setVisibility(false);//added for koc 1278
            }
            // Based on Enrollment or Pre_Auth flow or claims we call below method for requrey.

            if(strLink.equals(strPreAuthorization))
            {
                alPEDGroup=memberManagerObject.getPreauthPEDList(PreAuthWebBoardHelper.getMemberSeqId(request),
                		   PreAuthWebBoardHelper.getPreAuthSeqId(request),TTKCommon.getUserSeqId(request),"PAT");
                ((Column)((ArrayList)PEDTableData.getTitle()).get(2)).setVisibility(false);//added for koc 1278
            	((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);//added for koc 1278
            }//end of if(strLink.equals(strPreAuthorization))
            if(strLink.equals(strClaims))
            {
                alPEDGroup=memberManagerObject.getPreauthPEDList(ClaimsWebBoardHelper.getMemberSeqId(request),
                		   ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request),"CLM");
                ((Column)((ArrayList)PEDTableData.getTitle()).get(2)).setVisibility(false);//added for koc 1278
            	((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);//added for koc 1278
            }//end of if(strLink.equals(strClaims))
            if(strLink.equals(strEnrollment))
            {
                ((Column)((ArrayList)PEDTableData.getTitle()).get(1)).setVisibility(false);
                alPEDGroup = memberManagerObject.getPEDList(lngMemberSeqId);
            }//end of if(strLink.equals(strEnrollment))
            PEDTableData.setData(alPEDGroup);
            request.getSession().setAttribute("PEDTableData",PEDTableData);
            return this.getForward(strForward, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPEDError));
		}//end of catch(Exception exp)
    }//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to delete the selected record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doDelete method of PEDAction");
    		String strSubLinks=TTKCommon.getActiveSubLink(request);
    		String strSwitchType="";
            //TreeData treeData=null;
            // if true then it is from Pre_Auth flow else it is for Enrollment flow
            if(strSubLinks.equals("Processing"))
            {
                setLinks(request);
                strSwitchType="ENM";
            }//end of if(strSubLinks.equals("Processing"))
            else
            {
                DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
                strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
                this.setLinks(request,strSwitchType);
                //treeData =(TreeData)request.getSession().getAttribute("treeData") ;
            }//end of else
            String strLink=TTKCommon.getActiveLink(request);
            String strForward="";
            String strPolicyType="";
            MemberManager memberManagerObject=this.getMemberManagerObject();
            if(strSubLinks.equals(strIndividualPolicy))
            {
                strForward=strIndividualPED;
                strPolicyType="IP";
            }//end of if(strSubLinks.equals(strIndividualPolicy))
            else if(strSubLinks.equals(strIndPolicyGroup))
            {
                strForward=strIndPolicyGroupPED;
                strPolicyType="IG";
            }//end of else if(strSubLinks.equals(strIndPolicyGroup))
            else if(strSubLinks.equals(strCorporatePolicy))
            {
                strForward=strCorporatePED;
                strPolicyType="CP";
            }//end of else if(strSubLinks.equals(strCorporatePolicy))
            else if(strSubLinks.equals(strNonCorpPolicy))
            {
                strForward=strNonCorporatePED;
                strPolicyType="NC";
            }//end of else if(strSubLinks.equals(strNonCorpPolicy))
            else if(strLink.equals(strPreAuthorization))
            {
                strForward=strPreAuth;
            }//end of else if(strLink.equals(strPreAuthorization))
            else if(strLink.equals(strClaims))
            {
                strForward=strClaimsDetailClose;
            }//end of else if(strLink.equals(strClaims))
            TableData  PEDTableData =null;
            if((request.getSession()).getAttribute("PEDTableData") == null)
            {
                PEDTableData = new TableData();
            }//end of if((request.getSession()).getAttribute("PEDTableData") == null)
            else
            {
                PEDTableData = (TableData)(request.getSession()).getAttribute("PEDTableData");
            }//end of else
            DynaActionForm formAddPED=(DynaActionForm)form;
            formAddPED.set("switchType",strSwitchType);
            Long lngMemberSeqId=TTKCommon.getLong(formAddPED.getString("memberSeqID"));
            StringBuffer sbfDeleteId = new StringBuffer();
            //fetch the id(s) to be deleted
            if(strLink.equals(strPreAuthorization)||strLink.equals(strClaims))// Pre_Auth flow
            {
                sbfDeleteId.append("|").append(String.valueOf(formAddPED.getString("seqID"))).append("|").
                append(String.valueOf(formAddPED.getString("editYN"))).append("|");
            }//end of if(strLink.equals(strPreAuthorization)||strLink.equals(strClaims))
            else
            {
                sbfDeleteId.append("|").append(String.valueOf(formAddPED.getString("PEDSeqID"))).append("|");
            }//end of else

            ArrayList <Object>alParameter=new ArrayList<Object>();
            //Based on the link the control calls either deletePED()in Enrollment flow or deletePATGeneral() in Pre_Auth flow
            if(strLink.equals(strEnrollment))
            {
                alParameter.add("PED");
                alParameter.add(sbfDeleteId.toString());
                if(strSwitchType.equals("ENM"))
                {
                    alParameter.add(WebBoardHelper.getPolicySeqId(request));
                }//end of if(strSwitchType.equals("ENM"))
                else
                {
                    alParameter.add(WebBoardHelper.getEndorsementSeqId(request));
                }//end of else
                alParameter.add(strSwitchType);
                alParameter.add(strPolicyType);
                alParameter.add(TTKCommon.getUserSeqId(request));
                //delete the PED Details
                memberManagerObject.deletePED(alParameter);
            }//end of if(strLink.equals("Enrollment"))
            if(strLink.equals(strPreAuthorization) || strLink.equals(strClaims))// Pre_Auth flow and claims flow
            {
                PreAuthManager preAuthManager =this.getPreAuthManagerObject();
                ClaimManager claimManager=this.getClaimManagerObject();
                alParameter.add("PED");
                alParameter.add(sbfDeleteId.toString());
                if(strLink.equals(strPreAuthorization))
                {
                    alParameter.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request));//PAT_ENROLL_DETAIL_SEQ_ID
                    alParameter.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));//PAT_GENERAL_DETAIL_SEQ_ID
                    alParameter.add(TTKCommon.getUserSeqId(request));
                    preAuthManager.deletePATGeneral(alParameter);
                }//end of if(strLink.equals(strPreAuthorization))
                if(strLink.equals(strClaims))
                {
                    alParameter.add(ClaimsWebBoardHelper.getEnrollDetailSeqId(request));
                    alParameter.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
                    alParameter.add(TTKCommon.getUserSeqId(request));
                    claimManager.deleteClaimGeneral(alParameter);
                }//end of if(strLink.equals(strClaims))
             }//end of if(strLink.equals(strPreAuthorization) || strLink.equals(strClaims))
            Cache.refresh("icdDescription");
            //refresh the grid with search data
            ArrayList alPEDGroup = null;
            PEDTableData = new TableData();
            // create the required grid table
           	PEDTableData.createTableInfo("AddPEDTable",null);
            if("Coding".equals(strLink)||"Code CleanUp".equals(strLink)){
            	((Column)((ArrayList)PEDTableData.getTitle()).get(0)).setIsLink(false);//disable the hyper link
            	((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);//get(5) is changed to get(6) for koc 1278
            	((Column)((ArrayList)PEDTableData.getTitle()).get(7)).setVisibility(false);//added for koc 1278
            }//end of if("Coding".equals(strLink)||"Code CleanUp".equals(strLink))
            // Based on Enrollment or Pre_Auth flow or claims we call below method for requrey.

            if(strLink.equals(strPreAuthorization))
            {
                alPEDGroup=memberManagerObject.getPreauthPEDList(PreAuthWebBoardHelper.getMemberSeqId(request),
                		PreAuthWebBoardHelper.getPreAuthSeqId(request),TTKCommon.getUserSeqId(request),"PAT");
                ((Column)((ArrayList)PEDTableData.getTitle()).get(2)).setVisibility(false);//added for koc 1278
            	((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);//added for koc 1278
            }//end of if(strLink.equals(strPreAuthorization))
            if(strLink.equals(strClaims))
            {
                alPEDGroup=memberManagerObject.getPreauthPEDList(ClaimsWebBoardHelper.getMemberSeqId(request),
                		ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request),"CLM");
                ((Column)((ArrayList)PEDTableData.getTitle()).get(2)).setVisibility(false);//added for koc 1278
            	((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);//added for koc 1278
            }//end of if(strLink.equals(strClaims))
            if(strLink.equals(strEnrollment))
            {
                ((Column)((ArrayList)PEDTableData.getTitle()).get(1)).setVisibility(false);
                alPEDGroup = memberManagerObject.getPEDList(lngMemberSeqId);
            }//end of if(strLink.equals(strEnrollment))
            PEDTableData.setData(alPEDGroup);
            request.getSession().setAttribute("PEDTableData",PEDTableData);
            return this.getForward(strForward, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPEDError));
		}//end of catch(Exception exp)
    }//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doClosePED(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doClosePED method of PEDAction");
    		String strSubLinks=TTKCommon.getActiveSubLink(request);
    		String strLink=TTKCommon.getActiveLink(request);
            String strSwitchType="";
            // if true then it is from Pre_Auth flow else it is for Enrollment flow
            if(strSubLinks.equals("Processing"))
            {
                setLinks(request);
                strSwitchType="ENM";
            }//end of if(strSubLinks.equals("Processing"))
            else if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
            {
                setLinks(request);
                strSwitchType="ENM";
            }//end of if(strSubLinks.equals("Processing"))
            else
            {
                DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
                strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
                this.setLinks(request,strSwitchType);
            }//end of else
            String strForward="";
    		if(strSubLinks.equals(strIndividualPolicy))
            {
                strForward=strIndividualClose;
            }//end of if(strSubLinks.equals(strIndividualPolicy))
            else if(strSubLinks.equals(strIndPolicyGroup))
            {
                strForward=strIndPolicyGroupClose;
            }//end of else if(strSubLinks.equals(strIndPolicyGroup))
            else if(strSubLinks.equals(strCorporatePolicy))
            {
            	String policy_status = null;
            	Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);
            	MemberManagerBean memberManager  = new MemberManagerBean();
            	policy_status =    memberManager.getPolicyStatus(pol_seq_id);
            	if(policy_status.trim().equals("FTS"))
            	{
            		strForward=strCorporateClose;
            	}
            	else
            	{
            		strForward=strCorporateRenewClose;
            	}

            }//end of else if(strSubLinks.equals(strCorporatePolicy))
            else if(strSubLinks.equals(strNonCorpPolicy))
            {
                strForward=strNonCorporateClose;
            }//end of else if(strSubLinks.equals(strNonCorpPolicy))
            else if(strLink.equals(strPreAuthorization))
            {
                strForward=strPreAuthPEDClose;
            }//end of else if(strLink.equals(strPreAuthorization))
            else if(strLink.equals(strClaims))
            {
                strForward=strClaimsPEDClose;
            }//end of else if(strLink.equals(strClaims))
            else if(strLink.equals("Coding"))
			{
				if(strSubLinks.equals("PreAuth"))
				{
					strForward=strCodePreauthPEDClose;
				}//end of if(strSubLinks.equals("PreAuth"))
				else if(strSubLinks.equals("Claims"))
				{
					strForward=strCodeClaimsPEDClose;
				}//end of else if(strSubLinks.equals("Claims"))
			}//end of else if(strLink.equals("Coding"))
            else if(strLink.equals("Code CleanUp"))
			{
				if(strSubLinks.equals("PreAuth"))
				{
					strForward=strCloseCleanupPreauthPed;
				}//end of if(strSubLinks.equals("PreAuth"))
				else if(strSubLinks.equals("Claims"))
				{
					strForward=strCloseCleanupClaimsPed;
				}//end of else if(strSubLinks.equals("Claims"))
			}//end of else if(strLink.equals("Coding"))
    		request.getSession().removeAttribute("PEDTableData");
            return this.getForward(strForward, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPEDError));
		}//end of catch(Exception exp)
    }//end of doClosePED(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to navigate to detail screen to add a record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doAdd method of PEDAction");
    		String strSubLinks=TTKCommon.getActiveSubLink(request);
    		String strSwitchType="";
            // if true then it is from Pre_Auth flow else it is for Enrollment flow
            if(strSubLinks.equals("Processing"))
            {
                setLinks(request);
                strSwitchType="ENM";
            }//end of if(strSubLinks.equals("Processing"))
            else
            {
                DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
                strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
                this.setLinks(request,strSwitchType);
            }//end of else
            MemberManager memberManagerObject=this.getMemberManagerObject();
            String strForward="";
            DynaActionForm frmAddPED = (DynaActionForm)form;
            strForward = this.getSaveForwardPath(request);
            //create a new Product object
            PEDVO pedVO = new PEDVO();
            String strMemberSeqID = frmAddPED.getString("memberSeqID");
            pedVO.setMemberSeqID(TTKCommon.getLong(frmAddPED.getString("memberSeqID")));
            pedVO.setSeqID(new Long(0));
            String strName=(String)frmAddPED.get("memberName");
            String strGetCaption=frmAddPED.getString("caption");
            if(strSubLinks.equals(strProcessing))
            {
            	pedVO=memberManagerObject.getPED(pedVO.getSeqID(),"Pre-Authorization","");
            }//end of if(strSubLinks.equals(strProcessing))
            else
            {
                pedVO=memberManagerObject.getPED(pedVO.getSeqID(),"Enrollment","");
               // frmAddPED.set("seqID",pedVO.getMemberSeqID().toString());
            }//end of else
             
            String strAddEdit="Add";	// this is used to check whether to show the delete button or not
            String strCaption1=null;
            strCaption1="- Add - "+strGetCaption;
            frmAddPED = (DynaActionForm)FormUtils.setFormValues("frmAddPED",pedVO,this,mapping,request);
            frmAddPED.initialize(mapping);
            frmAddPED.set("editYN","Y");
            //resetting all the required information(s)
            frmAddPED.set("addEdit",strAddEdit);
            frmAddPED.set("caption",strGetCaption);
            frmAddPED.set("caption1",strCaption1);
            frmAddPED.set("memberName",strName);
            frmAddPED.set("switchType",strSwitchType);
            frmAddPED.set("description",pedVO.getDescription());
            frmAddPED.set("ICDCode",pedVO.getICDCode());
            frmAddPED.set("PEDCodeID",pedVO.getPEDCodeID().toString());
            //added for koc 1278
            frmAddPED.set("waitingPeriod", pedVO.getWaitingPeriod());
            frmAddPED.set("personalWaitingPeriod",pedVO.getPersonalWaitingPeriod());
            frmAddPED.set("ailmentTypeID", pedVO.getAilmentTypeID());
            //added for koc 1278
            //the below line is used in the  PEDDetailClose mode where on close we are getting the
            //  info based on this seq id taking from the frm..
            if(TTKCommon.getActiveLink(request).equals("Enrollment"))
            {
                frmAddPED.set("memberSeqID",strMemberSeqID);
            }//end of if(TTKCommon.getActiveLink(request).equals("Enrollment"))
            request.getSession().setAttribute("frmAddPED",frmAddPED);
            return this.getForward(strForward,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPEDError));
		}//end of catch(Exception exp)
    }//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to navigate to detail screen to edit selected record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewPED(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewPED method of PEDAction");
    		String strSubLinks=TTKCommon.getActiveSubLink(request);
    		String strSwitchType="";
            
            // if true then it is from Pre_Auth flow else it is for Enrollment flow
            if(strSubLinks.equals("Processing"))
            {
                setLinks(request);
                strSwitchType="ENM";
            }//end of if(strSubLinks.equals("Processing"))
            else
            {
                DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
                strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
                this.setLinks(request,strSwitchType);
            }//end of else
            MemberManager memberManagerObject=this.getMemberManagerObject();
            String strForward="";
            DynaActionForm frmAddPED = (DynaActionForm)form;
            strForward = this.getSaveForwardPath(request);
            TableData  PEDTableData =null;
            if((request.getSession()).getAttribute("PEDTableData") == null)
            {
                PEDTableData = new TableData();
            }//end of if((request.getSession()).getAttribute("PEDTableData") == null)
            else
            {
                PEDTableData = (TableData)(request.getSession()).getAttribute("PEDTableData");
            }//end of else

            //create a new Product object
            PEDVO pedVO = new PEDVO();
            pedVO.setMemberSeqID(TTKCommon.getLong(frmAddPED.getString("memberSeqID")));
            pedVO.setSeqID(TTKCommon.getLong(frmAddPED.getString("memberSeqID")));
            String strName=(String)frmAddPED.get("memberName");
            String strGetCaption=frmAddPED.getString("caption");
            String strAddEdit="Add";	// this is used to check whether to show the delete button or not
            String strCaption1=null;
            String strRowNum=(String)frmAddPED.get("rownum");
            if(!(TTKCommon.checkNull((String)frmAddPED.get("rownum")).equals("")))//edit flow
            {
                 pedVO = (PEDVO)PEDTableData.getRowInfo(Integer.parseInt((String)(frmAddPED).get("rownum")));
                 String strEditYN=pedVO.getEditYN();
                 // In the enrollment flow the value of strEdit will be empty, so we are setting its value to y,
                 // so that in enrollment we can display all the buttons
                 if(strEditYN.equals(""))
                 {
                     strEditYN="Y";
                 }//end of if(strEditYN.equals(""))
                 // in edit flow based on the enrollment or pre_auth flow we are passing the parameters to the getPED method
                if(strSubLinks.equals(strProcessing))
                {
                    pedVO=memberManagerObject.getPED(pedVO.getSeqID(),"Pre-Authorization",pedVO.getPEDType());
                    frmAddPED.set("PEDSeqID",strEditYN);
                }//end of if(strSubLinks.equals(strProcessing))
                else
                {
                    long lngPEDSeqID=pedVO.getPEDSeqID();
                    pedVO=memberManagerObject.getPED(lngPEDSeqID,"Enrollment","");
                    frmAddPED.set("seqID",pedVO.getMemberSeqID().toString());
                }//end of else
                frmAddPED = (DynaActionForm)FormUtils.setFormValues("frmAddPED",pedVO,this,mapping,request);
                frmAddPED.set("otherDesc",pedVO.getDescription());
                frmAddPED.set("ICD",pedVO.getICDCode());
                frmAddPED.set("rownum",strRowNum);
                frmAddPED.set("editYN",strEditYN);
                strCaption1="- Edit - "+strGetCaption;
                strAddEdit="Edit";
            }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            //resetting all the required information(s)
            frmAddPED.set("addEdit",strAddEdit);
            frmAddPED.set("caption",strGetCaption);
            frmAddPED.set("caption1",strCaption1);
            frmAddPED.set("memberName",strName);
            frmAddPED.set("switchType",strSwitchType);
            //added for koc 1278
            frmAddPED.set("waitingPeriod", pedVO.getWaitingPeriod());
            frmAddPED.set("personalWaitingPeriod",pedVO.getPersonalWaitingPeriod());
            frmAddPED.set("ailmentTypeID", pedVO.getAilmentTypeID());
            //added for koc 1278
            //the below line is used in the  PEDDetailClose mode where on close we are getting the
            //  info based on this seq id taking from the frm..
            if(TTKCommon.getActiveLink(request).equals("Enrollment"))
            {
                frmAddPED.set("memberSeqID",pedVO.getMemberSeqID().toString());
            }//end of if(TTKCommon.getActiveLink(request).equals("Enrollment"))
            request.getSession().setAttribute("frmAddPED",frmAddPED);
            return this.getForward(strForward,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPEDError));
		}//end of catch(Exception exp)
    }//end of doViewPED(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    	try{
    		log.debug("Inside the doSave method of PEDAction");
    		String strLink=TTKCommon.getActiveLink(request);
    		String strSubLinks=TTKCommon.getActiveSubLink(request);
    		String strSwitchType="";
            
            // if true then it is from Pre_Auth flow else it is for Enrollment flow
            if(strSubLinks.equals("Processing"))
            {
                setLinks(request);
                strSwitchType="ENM";
            }//end of if(strSubLinks.equals("Processing"))
            else
            {
                DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
                strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
                this.setLinks(request,strSwitchType);
            }//end of else
            String strForward="";
            MemberManager memberManagerObject=this.getMemberManagerObject();
            if(strSubLinks.equals(strIndividualPolicy))
            {
                strForward=strAddIndPEDDetail;
            }//end of if(strSubLinks.equals(strIndividualPolicy))
            else if(strSubLinks.equals(strIndPolicyGroup))
            {
                strForward=strAddIndGrpDetail;
            }//end of else if(strSubLinks.equals(strIndPolicyGroup))
            else if(strSubLinks.equals(strCorporatePolicy))
            {
                strForward=strAddCrpPEDDetail;
            }//end of else if(strSubLinks.equals(strCorporatePolicy))
            else if(strSubLinks.equals(strNonCorpPolicy))
            {
                strForward=strAddNONCrpPEDDetil;
            }//end of else if(strSubLinks.equals(strNonCorpPolicy))
            else if(strLink.equals(strPreAuthorization))
            {
                strForward=strPreAuthSave;
            }//end of else if(strLink.equals(strPreAuthorization))
            else if(strLink.equals(strClaims))
            {
                strForward=strClaimSave;
            }//end of else if(strLink.equals(strClaims))
            DynaActionForm frmAddPED=(DynaActionForm)form;
            //Getting the required information before initializing the form
            String strCaption=frmAddPED.getString("caption");
            String strCaption1=frmAddPED.getString("caption1");
            String strName=(String)frmAddPED.get("memberName");
            String strMemberSeqID=frmAddPED.getString("memberSeqID");
            strSwitchType=frmAddPED.getString("switchType");
            PEDVO pedVO = new PEDVO();
            pedVO=(PEDVO)FormUtils.getFormValues(frmAddPED,this,mapping,request);
            pedVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            int iCount=0;

            if(strLink.equals(strPreAuthorization))  //PreAuth flow
            {
                pedVO.setMemberSeqID(PreAuthWebBoardHelper.getMemberSeqId(request));
                iCount=memberManagerObject.savePED(pedVO,PreAuthWebBoardHelper.getPreAuthSeqId(request),
                		"Pre-Authorization","PAT");
            }//end of if(strSubLinks.equals(strProcessing))
            else if(strLink.equals(strClaims))      //Claims flow
            {
                pedVO.setMemberSeqID(ClaimsWebBoardHelper.getMemberSeqId(request));
                iCount=memberManagerObject.savePED(pedVO,ClaimsWebBoardHelper.getClaimsSeqId(request),
                		"Pre-Authorization","CLM");
            }//end of else if(strLink.equals(strClaims))
            else if(strLink.equals(strEnrollment))  //Enrollment flow
            {
                Long lngSeqid=null;
                if(strSwitchType.equals("ENM"))
                {
                    lngSeqid=WebBoardHelper.getPolicySeqId(request);
                }//end of if(strSwitchType.equals("ENM"))
                if(strSwitchType.equals("END"))
                {
                    lngSeqid=WebBoardHelper.getEndorsementSeqId(request);
                }//end of if(strSwitchType.equals("END"))
                iCount=memberManagerObject.savePED(pedVO,lngSeqid,"Enrollment",strSwitchType);
            }//end of else if(strLink.equals(strEnrollment))
            if(iCount>0)
            {
                if(!(TTKCommon.checkNull((String)frmAddPED.get("rownum")).equals("")))//edit flow
                {
                    request.setAttribute("updated","message.savedSuccessfully");
                }//end of if(!(TTKCommon.checkNull((String)formAddPED.get("rownum")).equals("")))
                else
                {
                    request.setAttribute("updated","message.addedSuccessfully");
                }//end of else
             }//end of if(iCount>0)
            //refresh the cache
            Cache.refresh("icdDescription");
            if((TTKCommon.checkNull((String)(frmAddPED).get("rownum")).equals("")))//add flow
            {
                frmAddPED.initialize(mapping);
                frmAddPED.set("editYN","Y");
            }//end of if((TTKCommon.checkNull((String)(formAddPED).get("rownum")).equals("")))
            //Resetting the required value back to the form
            
            frmAddPED.set("caption",strCaption);
            frmAddPED.set("caption1",strCaption1);
            frmAddPED.set("memberName",strName);
            frmAddPED.set("switchType",strSwitchType);
            frmAddPED.set("memberSeqID",strMemberSeqID);
            frmAddPED.set("frmChanged","");
            frmAddPED.set("description",pedVO.getDescription());
            frmAddPED.set("ICDCode",pedVO.getICDCode());
            frmAddPED.set("PEDCodeID",pedVO.getPEDCodeID().toString());
            //added for koc 1278
            frmAddPED.set("waitingPeriod", pedVO.getWaitingPeriod());
            frmAddPED.set("personalWaitingPeriod",pedVO.getPersonalWaitingPeriod());
            frmAddPED.set("ailmentTypeID", pedVO.getAilmentTypeID());
            //added for koc 1278
            request.getSession().setAttribute("frmAddPED",frmAddPED);
            return this.getForward(strForward, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPEDError));
		}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    	try{
    		log.debug("Inside the doReset method of PEDAction");
    		String strSubLinks=TTKCommon.getActiveSubLink(request);
    		String strSwitchType="";
            
            // if true then it is from Pre_Auth flow else it is for Enrollment flow
            if(strSubLinks.equals("Processing"))
            {
                setLinks(request);
                strSwitchType="ENM";
            }//end of if(strSubLinks.equals("Processing"))
            else
            {
                DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
                strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
                this.setLinks(request,strSwitchType);
            }//end of else
            String strForward="";
            MemberManager memberManagerObject=this.getMemberManagerObject();
            DynaActionForm frmAddPED = (DynaActionForm)form;
            String strMemberSeqID = frmAddPED.getString("memberSeqID");
            strForward = this.getSaveForwardPath(request);
            TableData  PEDTableData =null;
            if((request.getSession()).getAttribute("PEDTableData") == null)
            {
                PEDTableData = new TableData();
            }//end of if((request.getSession()).getAttribute("PEDTableData") == null)
            else
            {
                PEDTableData = (TableData)(request.getSession()).getAttribute("PEDTableData");
            }//end of else
            //create a new Product object
            PEDVO pedVO = new PEDVO();
            pedVO.setMemberSeqID(TTKCommon.getLong(frmAddPED.getString("memberSeqID")));
            pedVO.setSeqID(TTKCommon.getLong(frmAddPED.getString("memberSeqID")));
            String strName=(String)frmAddPED.get("memberName");
            String strGetCaption=frmAddPED.getString("caption");
            String strGetCaption1=frmAddPED.getString("caption1");
            String strAddEdit="Add";	// this is used to check whether to show the delete button or not
            String strCaption1=null;
            strCaption1=strGetCaption1;
            String strRowNum=(String)frmAddPED.get("rownum");
            if(!(TTKCommon.checkNull((String)frmAddPED.get("rownum")).equals("")))//edit flow
            {
                 pedVO = (PEDVO)PEDTableData.getRowInfo(Integer.parseInt((String)(frmAddPED).get("rownum")));
                 String strEditYN=pedVO.getEditYN();
                 // In the enrollment flow the value of strEdit will be empty, so we are setting its value to y,
                 // so that in enrollment we can display all the buttons
                 if(strEditYN.equals(""))
                 {
                     strEditYN="Y";
                 }//end of if(strEditYN.equals(""))
                 // in edit flow based on the enrollment or pre_auth flow we are passing the parameters to the getPED method
                if(strSubLinks.equals(strProcessing))
                {
                    pedVO=memberManagerObject.getPED(pedVO.getSeqID(),"Pre-Authorization",pedVO.getPEDType());
                    frmAddPED.set("PEDSeqID",strEditYN);
                }//end of if(strSubLinks.equals(strProcessing))
                else
                {
                    long lngPEDSeqID=pedVO.getPEDSeqID();
                    pedVO=memberManagerObject.getPED(lngPEDSeqID,"Enrollment","");
                    frmAddPED.set("seqID",pedVO.getMemberSeqID().toString());
                }//end of else
                frmAddPED = (DynaActionForm)FormUtils.setFormValues("frmAddPED",pedVO,this,mapping,request);
                frmAddPED.set("otherDesc",pedVO.getDescription());
                frmAddPED.set("ICD",pedVO.getICDCode());
                frmAddPED.set("rownum",strRowNum);
                frmAddPED.set("editYN",strEditYN);
                strCaption1=strGetCaption1;
                strAddEdit="Edit";
            }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            else // add flow
            {
            	pedVO.setSeqID(new Long(0));
            	if(strSubLinks.equals(strProcessing))
                {
                	pedVO=memberManagerObject.getPED(pedVO.getSeqID(),"Pre-Authorization","");
                }//end of if(strSubLinks.equals(strProcessing))
                else
                {
                    pedVO=memberManagerObject.getPED(pedVO.getSeqID(),"Enrollment","");
                   // frmAddPED.set("seqID",pedVO.getMemberSeqID().toString());
                }//end of else
                frmAddPED = (DynaActionForm)FormUtils.setFormValues("frmAddPED",pedVO,this,mapping,request);
                frmAddPED.initialize(mapping);
                frmAddPED.set("editYN","Y");
                frmAddPED.set("description",pedVO.getDescription());
                frmAddPED.set("ICDCode",pedVO.getICDCode());
                frmAddPED.set("PEDCodeID",pedVO.getPEDCodeID().toString());
               
            }//end of else
            //resetting all the required information(s)
            frmAddPED.set("addEdit",strAddEdit);
            frmAddPED.set("caption",strGetCaption);
            frmAddPED.set("caption1",strCaption1);
            frmAddPED.set("memberName",strName);
            frmAddPED.set("switchType",strSwitchType);
            //the below line is used in the  PEDDetailClose mode where on close we are getting the
            //  info based on this seq id taking from the frm..
            if(TTKCommon.getActiveLink(request).equals("Enrollment"))
            {
                frmAddPED.set("memberSeqID",strMemberSeqID);
            }//end of if(TTKCommon.getActiveLink(request).equals("Enrollment"))
            request.getSession().setAttribute("frmAddPED",frmAddPED);
            return this.getForward(strForward,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPEDError));
		}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doClosePEDDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doClosePEDDetail method of PEDAction");
    		String strLink=TTKCommon.getActiveLink(request);
    		String strSubLinks=TTKCommon.getActiveSubLink(request);
    		String strSwitchType="";
            
            // if true then it is from Pre_Auth flow else it is for Enrollment flow
            if(strSubLinks.equals("Processing"))
            {
                setLinks(request);
                strSwitchType="ENM";
            }//end of if(strSubLinks.equals("Processing"))
            else
            {
                DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
                strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
                this.setLinks(request,strSwitchType);
            }//end of else
            String strForward="";
            MemberManager memberManagerObject=this.getMemberManagerObject();
            strForward = this.getAddForwardPath(request);
            TableData  PEDTableData =null;
            if((request.getSession()).getAttribute("PEDTableData") == null)
            {
                PEDTableData = new TableData();
            }//end of if((request.getSession()).getAttribute("PEDTableData") == null)
            else
            {
                PEDTableData = (TableData)(request.getSession()).getAttribute("PEDTableData");
            }//end of else
            DynaActionForm formAddPED=(DynaActionForm)form;
            ArrayList alMember=new ArrayList();
           	PEDTableData.createTableInfo("AddPEDTable",null);
            if("Coding".equals(strLink)||"Code CleanUp".equals(strLink)){
            	((Column)((ArrayList)PEDTableData.getTitle()).get(0)).setIsLink(false);//disable the hyper link
            	((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);//get(5) is changed to get(6) for koc 1278
            	((Column)((ArrayList)PEDTableData.getTitle()).get(7)).setVisibility(false);//added for koc 1278
            }
            if(strLink.equals(strEnrollment))
            {
                ((Column)((ArrayList)PEDTableData.getTitle()).get(1)).setVisibility(false);
                alMember=memberManagerObject.getPEDList(TTKCommon.getLong((String)formAddPED.get("memberSeqID")));//new Long(21));
                formAddPED.set("flow","ENR");
            }//end of  if(!strLink.equals("Pre-Authorization")&& strLink.equals("Claims") )
            if(strLink.equals(strPreAuthorization))
            {
                alMember=memberManagerObject.getPreauthPEDList(PreAuthWebBoardHelper.getMemberSeqId(request),
                		PreAuthWebBoardHelper.getPreAuthSeqId(request),TTKCommon.getUserSeqId(request),"PAT");
                formAddPED.set("flow","PRE");
                ((Column)((ArrayList)PEDTableData.getTitle()).get(2)).setVisibility(false);//added for koc 1278
                ((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);//added for koc 1278
            }//end of if(strLink.equals(strPreAuthorization))
            if(strLink.equals(strClaims))
            {
                alMember=memberManagerObject.getPreauthPEDList(ClaimsWebBoardHelper.getMemberSeqId(request),
                		ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request),"CLM");
                formAddPED.set("flow","PRE");
                ((Column)((ArrayList)PEDTableData.getTitle()).get(2)).setVisibility(false);//added for koc 1278
                ((Column)((ArrayList)PEDTableData.getTitle()).get(6)).setVisibility(false);//added for koc 1278
            }//end of if(strLink.equals(strClaims))
            PEDTableData.setData(alMember);
            request.getSession().setAttribute("PEDTableData",PEDTableData);
            return this.getForward(strForward,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPEDError));
		}//end of catch(Exception exp)
    }//end of doClosePEDDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
        
    /**
     * Returns a string which contains the Forward Path
     * @param request HttpServletRequest object which contains information required for buiding the Forward Path
     * @return String which contains the comma separated sequence id's to be deleted
     */
    private String getAddForwardPath(HttpServletRequest request) throws TTKException{
    	String strLink=TTKCommon.getActiveLink(request);
        String strSubLinks=TTKCommon.getActiveSubLink(request);
        String strForward="";
        try{
    		if(strSubLinks.equals(strIndividualPolicy))
            {
                strForward=strIndividualPED;
            }//end of if(strSubLinks.equals(strIndividualPolicy))
            else if(strSubLinks.equals(strIndPolicyGroup))
            {
                strForward=strIndPolicyGroupPED;
            }//end of if(strSubLinks.equals(strIndPolicyGroup))
            else if(strSubLinks.equals(strCorporatePolicy))
            {
                strForward=strCorporatePED;
            }//end of if(strSubLinks.equals(strCorporatePolicy))
            else if(strSubLinks.equals(strNonCorpPolicy))
            {
                strForward=strNonCorporatePED;
            }//end of if(strSubLinks.equals(strNonCorpPolicy))
            else if(strLink.equals(strPreAuthorization))
            {
                strForward=strPreAuthDetailClose;
            }//end of if(strSubLinks.equals(strPreAuthorization))
            else if(strLink.equals(strClaims))
            {
                strForward=strClaimsDetailClose;
            }//end of if(strSubLinks.equals(strClaims))
            else if(strLink.equals("Coding"))
			{
				if(strSubLinks.equals("PreAuth")){
					strForward=strCodePreauthDetailClose;
				}//end of if(strSubLinks.equals("PreAuth")){
				else if(strSubLinks.equals("Claims")){
					strForward=strCodeClaimsDetailClose;
				}//end of else if(strSubLinks.equals("Claims")){
			}//end of else if(strLink.equals("Coding"))
    	}//end of try
    	catch(Exception exp)
        {
            throw new TTKException(exp, strPEDError);
        }//end of catch
        return strForward;
    }//end of getAddForwardPath(HttpServletRequest request)
    
    /**
     * Returns a string which contains the Forward Path
     * @param request HttpServletRequest object which contains information required for buiding the Forward Path
     * @return String which contains the comma separated sequence id's to be deleted
     */
    private String getSaveForwardPath(HttpServletRequest request) throws TTKException{
    	String strLink=TTKCommon.getActiveLink(request);
        String strSubLinks=TTKCommon.getActiveSubLink(request);
        String strForward="";
    	try{
    		if(strSubLinks.equals(strIndividualPolicy))
            {
                strForward=strAddIndPEDDetail;
            }//end of if(strSubLinks.equals(strIndividualPolicy))
            else if(strSubLinks.equals(strIndPolicyGroup))
            {
                strForward=strAddIndGrpDetail;
            }//end of if(strSubLinks.equals(strIndPolicyGroup))
            else if(strSubLinks.equals(strCorporatePolicy))
            {
                strForward=strAddCrpPEDDetail;
            }//end of if(strSubLinks.equals(strCorporatePolicy))
            else if(strSubLinks.equals(strNonCorpPolicy))
            {
                strForward=strAddNONCrpPEDDetil;
            }//end of if(strSubLinks.equals(strNonCorpPolicy))
            else if(strLink.equals(strPreAuthorization))
            {
                strForward=strPreAuthUpdate;
            }//end of if(strSubLinks.equals(strPreAuthorization))
            else if(strLink.equals(strClaims))
            {
                strForward=strClaimsUpdate;
            }//end of if(strSubLinks.equals(strClaims))
    		else if(strLink.equals(strCoding))
            {
    			if(strSubLinks.equals("PreAuth"))
    			{
    				strForward=strCodingPreUpdate;
    			}//end of if(strSubLinks.equals("PreAuth"))
    			else if(strSubLinks.equals("Claims"))
    			{
    				strForward=strCodingClmUpdate;
    			}//end of else if(strSubLinks.equals("Claims"))
            }//end of if(strSubLinks.equals(strClaims))
    	}//end of try
    	catch(Exception exp)
        {
            throw new TTKException(exp, strPEDError);
        }//end of catch
        return strForward;
    }//end of getSaveForwardPath(HttpServletRequest request)
    
    /**
     * Returns a string which contains the | separated sequence id's to be deleted
     * @param request HttpServletRequest object which contains information about the selected check boxes
     * @param PEDTableData TableData object which contains the value objects
     * @return String which contains the | separated sequence id's to be delete
     * @throws TTKException
     * @throws TTKException If any run time Excepton occures
     */
    private String populateDeleteId(HttpServletRequest request, TableData PEDTableData) throws TTKException
    {
        String[] strChk = request.getParameterValues("chkopt");
        StringBuffer sbfDeleteId = new StringBuffer();
        String strLinks= TTKCommon.getActiveLink(request);
        if(strChk!=null&&strChk.length!=0)
        {
            for(int i=0; i<strChk.length;i++)
            {
                if(strChk[i]!=null)
                {
                    //extract the sequence id to be deleted from the value object
                    if(i == 0)
                    {
                        if(strLinks.equals("Enrollment"))
                        {
                            sbfDeleteId.append("|").append(String.valueOf(((PEDVO)PEDTableData.getData().
                            		get(Integer.parseInt(strChk[i]))).getPEDSeqID().intValue()));
                        }//end of if(strLinks.equals("Enrollment"))
                        else
                        {
                            sbfDeleteId.append("|").append(String.valueOf(((PEDVO)PEDTableData.getData().
                            get(Integer.parseInt(strChk[i]))).getSeqID().intValue())).append("|").append(String.valueOf(
                            ((PEDVO)PEDTableData.getData().get(Integer.parseInt(strChk[i]))).getEditYN()));
                        }//end of else
                    }//end of if(i == 0)
                    else
                    {
                        if(strLinks.equals("Enrollment"))
                        {
                            sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((PEDVO)PEDTableData.
                            		getData().get(Integer.parseInt(strChk[i]))).getPEDSeqID().intValue()));
                        }//end of if(strLinks.equals("Enrollment"))
                        else
                        {
                            sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((PEDVO)PEDTableData.
                            			  getData().get(Integer.parseInt(strChk[i]))).getSeqID().intValue())).
                            			  append("|").append(String.valueOf(((PEDVO)PEDTableData.getData().
                            			  get(Integer.parseInt(strChk[i]))).getEditYN()));
                        }//end of else
                    }//end of else
                }//end of if(strChk[i]!=null)
            }//end of for(int i=0; i<strChk.length;i++)
            sbfDeleteId=sbfDeleteId.append("|");
        }//end of if(strChk!=null&&strChk.length!=0)
        return sbfDeleteId.toString();
    }//end of populateDeleteId(HttpServletRequest request, TableData PEDTableData)

    /**
     * Returns the MemberManager session object for invoking methods on it.
     * @return MemberManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private MemberManager getMemberManagerObject() throws TTKException
    {
        MemberManager memberManager = null;
        try
        {
            if(memberManager == null)
            {
                InitialContext ctx = new InitialContext();
                memberManager = (MemberManager) ctx.lookup("java:global/TTKServices/business.ejb3/MemberManagerBean!com.ttk.business.enrollment.MemberManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strPEDError);
        }//end of catch
        return memberManager;
    }//end getMemberManager()

    /**
     * Returns the preAuthManager session object for invoking methods on it.
     * @return preAuthManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private PreAuthManager getPreAuthManagerObject() throws TTKException
    {
        PreAuthManager preAuthManager = null;
        try
        {
            if(preAuthManager == null)
            {
                InitialContext ctx = new InitialContext();
                preAuthManager = (PreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthManagerBean!com.ttk.business.preauth.PreAuthManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strPEDError);
        }//end of catch
        return preAuthManager;
    }//end getMemberManager()


    /**
     * This method returns the claimManager session object for invoking DAO methods from it.
     * @return claimManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private ClaimManager getClaimManagerObject() throws TTKException
    {
        ClaimManager claimManager = null;
        try
        {
            if(claimManager == null)
            {
                InitialContext ctx = new InitialContext();
                claimManager = (ClaimManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimManagerBean!com.ttk.business.claims.ClaimManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strPEDError);
        }//end of catch
        return claimManager;
    }//end getClaimManagerObject()

}//end of class PEDAction


