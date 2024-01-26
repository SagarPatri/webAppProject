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

package com.ttk.action.dataentrycoding;

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
import com.ttk.business.claims.ClaimManager;
import com.ttk.business.coding.CodingManager;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.CodingClaimsWebBoardHelper;
import com.ttk.common.CodingWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.enrollment.PEDVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is reusable one,used to list PEDs,add/edit and delete PED,
 * in enrollment,endorsement,pre-auth and claims flows.
 * This class also provides option for listing ICD codes in enrollment,endorsement,
 * pre-auth and claims flows.
 */

public class PEDCodingAction extends TTKAction
{
    private static Logger log = Logger.getLogger( PEDCodingAction.class );
    //sub links
    private static final String strICDList="icdlist";
    //forwards for pre-auth
    private static final String strProcessing="Processing";
    private static final String strCodePreauthPed="codepreauth";
    private static final String strCodePreauthPEDClose="codepreauthpedclose";
    //forwards for claims
    private static final String strCodingPreUpdate="codingpreupdate"; 
    private static final String strCodingClmUpdate="codingclmupdate";
    
     private static final String strCodeClaimsPed="codeclaimsped";
    private static final String strCodeClaimsPEDClose="codeclaimspedclose";
    //Links
    private static final String strPreAuthorization="Pre-Authorization";
    private static final String strClaims="Claims";
    
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
    public ActionForward doSearchPreauthPED(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSearchPreauthPED method of PEDAction");
    		setLinks(request);
    		DynaActionForm frmAddPED=(DynaActionForm)form;
    		frmAddPED.initialize(mapping);
    		PEDVO pedVO = new PEDVO();
    		TableData  PEDTableData =null;
    		String strForward="";
    		String strLink=TTKCommon.getActiveLink(request);
    		String strSubLink = TTKCommon.getActiveSubLink(request);
    		MemberManager memberManagerObject=this.getMemberManagerObject();
    		if((request.getSession()).getAttribute("PEDTableData") == null)
            {
                PEDTableData = new TableData();
            }//end of if((request.getSession()).getAttribute("PEDTableData") == null)
            else
            {
                PEDTableData = (TableData)(request.getSession()).getAttribute("PEDTableData");
            }//end of else
    		

            PEDTableData = new TableData();
            // create the required grid table
           	PEDTableData.createTableInfo("AddPEDTable",null);
            if("Code CleanUp".equals(strLink)){
            	((Column)((ArrayList)PEDTableData.getTitle()).get(0)).setIsLink(false);//disable the hyper link
            	((Column)((ArrayList)PEDTableData.getTitle()).get(5)).setVisibility(false);
            }
            ArrayList alMember=new ArrayList();
            StringBuffer sbfCaption= new StringBuffer();
            if(strSubLink.equals("PreAuth")){
					alMember=memberManagerObject.getPreauthPEDList(CodingWebBoardHelper.getMemberSeqId(request),
							CodingWebBoardHelper.getPreAuthSeqId(request),TTKCommon.getUserSeqId(request),"PAT");
	                sbfCaption.append("[ ").append(CodingWebBoardHelper.getClaimantName(request)).append(" ] [ ").
	                append(CodingWebBoardHelper.getWebBoardDesc(request)).append(" ]");
	                if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
	    				sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
	                strForward=strCodePreauthPed;
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
				}//end of else if(strSubLink.equals("Claims"))
			 PEDTableData.setData(alMember);
            //setting the form and table objects to session
            request.getSession().setAttribute("PEDTableData",PEDTableData);
            frmAddPED = (DynaActionForm)FormUtils.setFormValues("frmAddPED",pedVO,this,mapping,request);
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
	public ActionForward doViewICDList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			DynaActionForm frmAddPED =(DynaActionForm)form;
			frmAddPED.set("frmChanged","changed");
			return mapping.findForward(strICDList);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strPEDError));
		}//end of catch(Exception exp)
	}//end of doViewICDList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

    
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
    		setLinks(request);
    		DynaActionForm frmAddPED = (DynaActionForm)form;
    		String strSubLinks=TTKCommon.getActiveSubLink(request);
    		PEDVO pedVO = new PEDVO();
    		String strForward="";
    		// if true then it is from Pre_Auth flow else it is for Enrollment flow

    		if(strSubLinks.equals("PreAuth"))
    		{
    			strForward=strCodePreauthPEDClose;
    		}//end of if(strSubLinks.equals("PreAuth"))
    		else if(strSubLinks.equals("Claims"))
    		{
    			strForward=strCodeClaimsPEDClose;
    		}//end of else if(strSubLinks.equals("Claims"))
    		frmAddPED = (DynaActionForm)FormUtils.setFormValues("frmAddPED",pedVO,this,mapping,request);
            request.getSession().setAttribute("frmAddPED",frmAddPED);
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
    		setLinks(request);
    		String strForward="";
    		// if true then it is from Pre_Auth flow else it is for Enrollment flow

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
    		
    		String strName=(String)frmAddPED.get("memberName");
    		String strGetCaption=frmAddPED.getString("caption");
    		String strCaption1=null;
    		String strRowNum=(String)frmAddPED.get("rownum");
    		String strPEDType = null;
    		Long lngPEDSeqID = null;
    		if(!(TTKCommon.checkNull((String)frmAddPED.get("rownum")).equals("")))//edit flow
    		{
    			pedVO = (PEDVO)PEDTableData.getRowInfo(Integer.parseInt((String)(frmAddPED).get("rownum")));

    			log.info("strPEDType is : "+pedVO.getPEDType());
    			log.info("lngPEDSeqID is : "+pedVO.getSeqID());
    			strPEDType=pedVO.getPEDType();
    			log.info("After assigning strPEDType is : "+strPEDType);
    			lngPEDSeqID=pedVO.getSeqID();
    		}//end of if(!(TTKCommon.checkNull((String)frmAddPED.get("rownum")).equals("")))
    		//resetting all the required information(s)
    		frmAddPED = (DynaActionForm)FormUtils.setFormValues("frmAddPED",pedVO,this,mapping,request);
    		frmAddPED.set("PEDSeqID",lngPEDSeqID.toString());
    		frmAddPED.set("otherDesc",pedVO.getDescription());
    		frmAddPED.set("ICD",pedVO.getICDCode());
    		frmAddPED.set("rownum",strRowNum);
    		frmAddPED.set("caption",strGetCaption);
    		frmAddPED.set("caption1",strCaption1);
    		frmAddPED.set("memberName",strName);
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
    		setLinks(request);
    		String strSubLinks=TTKCommon.getActiveSubLink(request);
    		String strForward="";
            CodingManager codingManager = this.getCodingManagerObject();
            // if true then it is from Pre_Auth flow else it is for Enrollment flow
            strForward = this.getSaveForwardPath(request);
            DynaActionForm frmAddPED=(DynaActionForm)form;
            //Getting the required information before initializing the form
            String strCaption=frmAddPED.getString("caption");
                        
            PEDVO pedVO = new PEDVO();
            MemberManager memberManagerObject=this.getMemberManagerObject();
            pedVO=(PEDVO)FormUtils.getFormValues(frmAddPED,this,mapping,request);
            pedVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            int iCount=0;
            TableData PEDTableData= null;           
            StringBuffer sbfCaption= new StringBuffer();
            
            if(strSubLinks.equals("PreAuth"))  //PreAuth flow
            {
                pedVO.setMemberSeqID(CodingWebBoardHelper.getMemberSeqId(request));
                iCount=codingManager.saveCodingPED(pedVO, CodingWebBoardHelper.getPreAuthSeqId(request), "PAT");
            }//end of if(strSubLinks.equals(strProcessing))
            else if(strSubLinks.equals("Claims"))      //Claims flow
            {
                pedVO.setMemberSeqID(CodingClaimsWebBoardHelper.getMemberSeqId(request));
                iCount=codingManager.saveCodingPED(pedVO, CodingClaimsWebBoardHelper.getClaimsSeqId(request), "CLM");
            }//end of else if(strLink.equals(strClaims))
            if(iCount>0)
            {
            	request.setAttribute("updated","message.savedSuccessfully");
            	ArrayList alMember=new ArrayList();
    			
            	PEDTableData = (TableData)(request.getSession()).getAttribute("PEDTableData");
            	
            	if(strSubLinks.equals("PreAuth")){
					alMember=memberManagerObject.getPreauthPEDList(CodingWebBoardHelper.getMemberSeqId(request),
							CodingWebBoardHelper.getPreAuthSeqId(request),TTKCommon.getUserSeqId(request),"PAT");
	                sbfCaption.append("[ ").append(CodingWebBoardHelper.getClaimantName(request)).append(" ] [ ").
	                append(CodingWebBoardHelper.getWebBoardDesc(request)).append(" ]");
	                
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLinks.equals("Claims")){
					alMember=memberManagerObject.getPreauthPEDList(CodingClaimsWebBoardHelper.getMemberSeqId(request),
							CodingClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request),"CLM");
	                sbfCaption.append("[ ").append(CodingClaimsWebBoardHelper.getClaimantName(request)).append(" ] [ ").
	                append(CodingClaimsWebBoardHelper.getWebBoardDesc(request)).append(" ]");
	            }//end of else if(strSubLink.equals("Claims"))
            	
            	PEDTableData.setData(alMember);
                request.getSession().setAttribute("PEDTableData",PEDTableData);
                frmAddPED.initialize(mapping);
            }//end of if(iCount>0)
            //refresh the cache
            
            Cache.refresh("icdDescription");     
            frmAddPED.set("caption",strCaption);
            frmAddPED.set("otherDesc","");
    		frmAddPED.set("ICD","");
            frmAddPED.set("caption",strCaption);
            frmAddPED.set("frmChanged","");
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
    		setLinks(request);
    		String strSubLinks=TTKCommon.getActiveSubLink(request);
    		String strLink=TTKCommon.getActiveLink(request);
            String strSwitchType="";
            String strForward="";
            
            DynaActionForm formAddPED=(DynaActionForm)form;
            formAddPED.set("switchType",strSwitchType);
            if(strSubLinks.equals("PreAuth"))
        	{
        		strForward=strCodingPreUpdate;
        	}//end of if(strSubLinks.equals("PreAuth"))
        	else if(strSubLinks.equals("Claims"))
        	{
        		strForward=strCodingClmUpdate;
        	}//end of if(strSubLinks.equals("Claims"))
            TableData  PEDTableData =null;
            if((request.getSession()).getAttribute("PEDTableData") == null)
            {
                PEDTableData = new TableData();
            }//end of if((request.getSession()).getAttribute("PEDTableData") == null)
            else
            {
                PEDTableData = (TableData)(request.getSession()).getAttribute("PEDTableData");
            }//end of else
            
            StringBuffer sbfDeleteId = new StringBuffer();
            //fetch the id(s) to be deleted
            sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().getAttribute("PEDTableData")));
            ArrayList <Object>alParameter=new ArrayList<Object>();
            //Based on the link the control calls either deletePED()in Enrollment flow or deletePATGeneral() in Pre_Auth flow
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
            Cache.refresh("icdDescription");
            //refresh the grid with search data
            ArrayList alPEDGroup = null;
            PEDTableData = new TableData();
            // create the required grid table
           	PEDTableData.createTableInfo("AddPEDTable",null);
            if("Coding".equals(strLink)||"Code CleanUp".equals(strLink)){
            	((Column)((ArrayList)PEDTableData.getTitle()).get(0)).setIsLink(false);//disable the hyper link
            	((Column)((ArrayList)PEDTableData.getTitle()).get(5)).setVisibility(false);
            }
            // Based on Enrollment or Pre_Auth flow or claims we call below method for requrey.

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
    public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doReset method of PEDAction");
    		setLinks(request);
    		String strSubLinks=TTKCommon.getActiveSubLink(request);
    		String strForward="";
            MemberManager memberManagerObject=this.getMemberManagerObject();
            // if true then it is from Pre_Auth flow else it is for Enrollment flow
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
                frmAddPED = (DynaActionForm)FormUtils.setFormValues("frmAddPED",pedVO,this,mapping,request);
                frmAddPED.initialize(mapping);
                frmAddPED.set("editYN","Y");
            }//end of else
            //resetting all the required information(s)
            frmAddPED.set("addEdit",strAddEdit);
            frmAddPED.set("caption",strGetCaption);
            frmAddPED.set("caption1",strCaption1);
            frmAddPED.set("memberName",strName);
            
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
	public ActionForward doGetDescription(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			String strForward="";
			strForward = this.getSaveForwardPath(request);
			DynaActionForm frmAddPED = (DynaActionForm)form;
			
			CodingManager codingManager = this.getCodingManagerObject();
			Object[] objArrayResult = codingManager.getExactICD(request.getParameter("ICDCode"));
			frmAddPED.set("description",objArrayResult[0]);
			frmAddPED.set("PEDCodeID",objArrayResult[1].toString());
			frmAddPED.set("ICDCode",request.getParameter("ICDCode"));
			frmAddPED.set("seqID",request.getParameter("seqID").toString());
			frmAddPED.set("PEDType",request.getParameter("PEDType"));
			request.getSession().setAttribute("frmAddPED",frmAddPED);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strPEDError));
		}//end of catch(Exception exp)
	}//end of public ActionForward doViewAssociateProcedure(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	 			//HttpServletResponse response)
	
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
        
        if(strChk!=null&&strChk.length!=0)
        {
            for(int i=0; i<strChk.length;i++)
            {
                if(strChk[i]!=null)
                {
                    //extract the sequence id to be deleted from the value object
                    if(i == 0)
                    {
                            sbfDeleteId.append("|").append(String.valueOf(((PEDVO)PEDTableData.getData().
                            get(Integer.parseInt(strChk[i]))).getSeqID().intValue())).append("|").append(String.valueOf(
                            ((PEDVO)PEDTableData.getData().get(Integer.parseInt(strChk[i]))).getEditYN()));
                        
                    }//end of if(i == 0)
                    else
                    {
                            sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((PEDVO)PEDTableData.
                            			  getData().get(Integer.parseInt(strChk[i]))).getSeqID().intValue())).
                            			  append("|").append(String.valueOf(((PEDVO)PEDTableData.getData().
                            			  get(Integer.parseInt(strChk[i]))).getEditYN()));
                        
                    }//end of else
                }//end of if(strChk[i]!=null)
            }//end of for(int i=0; i<strChk.length;i++)
            sbfDeleteId=sbfDeleteId.append("|");
        }//end of if(strChk!=null&&strChk.length!=0)
        return sbfDeleteId.toString();
    }//end of populateDeleteId(HttpServletRequest request, TableData PEDTableData)
    
    /**
     * Returns a string which contains the Forward Path
     * @param request HttpServletRequest object which contains information required for buiding the Forward Path
     * @return String which contains the comma separated sequence id's to be deleted
     */
    private String getSaveForwardPath(HttpServletRequest request) throws TTKException{
    	String strSubLinks=TTKCommon.getActiveSubLink(request);
        String strForward="";
        try{
        	if(strSubLinks.equals("PreAuth"))
        	{
        		strForward=strCodingPreUpdate;
        	}//end of if(strSubLinks.equals("PreAuth"))
        	else if(strSubLinks.equals("Claims"))
        	{
        		strForward=strCodingClmUpdate;
        	}//end of if(strSubLinks.equals("Claims"))

        }//end of try
    	catch(Exception exp)
        {
            throw new TTKException(exp, strPEDError);
        }//end of catch
        return strForward;
    }//end of getSaveForwardPath(HttpServletRequest request)
    
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
	 * Returns the MemberManager session object for invoking methods on it.
	 * @return MemberManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private CodingManager getCodingManagerObject() throws TTKException
	{
		CodingManager codingManager = null;
		try
		{
			if(codingManager == null)
			{
				InitialContext ctx = new InitialContext();
				codingManager = (CodingManager) ctx.lookup("java:global/TTKServices/business.ejb3/CodingManagerBean!com.ttk.business.coding.CodingManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strPEDError);
		}//end of catch
		return codingManager;
	}//end getCodingManagerObject()
	
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


