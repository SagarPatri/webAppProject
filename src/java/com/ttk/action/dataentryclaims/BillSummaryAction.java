/**
* @ (#) BillSummaryAction.java Jul 22, 2006
* Project 		: TTK HealthCare Services
* File			: BillSummaryAction.java
* Author 		: Pradeep R
* Company 		: Span Systems Corporation
* Date Created  : Jul 22, 2006
*
* @author 		: Pradeep R
* Modified by 	:
* Modified date :
* Reason :
*/

package com.ttk.action.dataentryclaims;

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
import com.ttk.business.claims.ClaimBillManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.claims.BillDetailVO;
import com.ttk.dto.claims.BillSummaryVO;
import com.ttk.dto.preauth.TariffAilmentVO;
import com.ttk.dto.preauth.TariffProcedureVO;

import formdef.plugin.util.FormUtils;

public class BillSummaryAction extends TTKAction
{
     private static Logger log = Logger.getLogger( BillSummaryAction.class );

     //declaration of forward paths
     private static final String strbillsummary="billsummary";
     private static final String strSubmitForm="submitbillform";

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
     public ActionForward doViewBillSummary(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     											HttpServletResponse response) throws Exception{
    	 try{
    		 log.debug("Inside BillSummaryAction doViewBillSummary");
    		 setLinks(request);
    		 if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
             {
                 TTKException expTTK = new TTKException();
                 expTTK.setMessage("error.Claims.required");
                 throw expTTK;
             }//end of if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
    		 ClaimBillManager billObject=this.getClaimBillManagerObject();    //get the business object
    		 DynaActionForm frmBillSummary =(DynaActionForm) form;
    		 StringBuffer strCaption= new StringBuffer();
    		 ArrayList<Object> alBillDetailInfo = new ArrayList<Object>();
    		 ArrayList<Object> alAilmentInfo= new ArrayList<Object>();
    		 BillSummaryVO billSummaryVO=null;
    		 billSummaryVO=billObject.getBillSummaryDetail(ClaimsWebBoardHelper.getClaimsSeqId(request),
    		 						TTKCommon.getUserSeqId(request));
    		 frmBillSummary = (DynaActionForm)FormUtils.setFormValues("frmBillSummary",billSummaryVO,this,
    		 															mapping,request);
    		 if(billSummaryVO!=null)
    		 {
    			 alBillDetailInfo=billSummaryVO.getBillDetailVOList();
    			 if(alBillDetailInfo !=null && alBillDetailInfo.size()>=1)
    			 {
    				 frmBillSummary.set("billInfo",(BillDetailVO[])alBillDetailInfo.toArray(new BillDetailVO[0]));
    			 }
    			 alAilmentInfo=billSummaryVO.getAilmentVOList();
    			 if(alAilmentInfo!=null && alAilmentInfo.size()>0)
    			 {
    				 frmBillSummary.set("ailmentInfo",(TariffAilmentVO[])alAilmentInfo.toArray(new TariffAilmentVO[0]));
    			 }//end of  if(alAilmentInfo!=null && alAilmentInfo.size()>0)
    			 int iReqAmtMisMatch=billSummaryVO.getReqAmtMisMatch();
    			 if(iReqAmtMisMatch ==1)
    			 {
    				 request.setAttribute("requestedAmountMismatch","message.requestedAmountGreater");
    			 }
    			 if(iReqAmtMisMatch ==2)
    			 {
    				 request.setAttribute("requestedAmountMismatch","message.requestedAmountLesser");
    			 }
    		 }//end of if(billSummaryVO!=null)
    		 request.setAttribute("tariffcalculation","message.tariffcalculation");
    		 strCaption.append("[ "+ClaimsWebBoardHelper.getClaimantName(request)+" ]");
    		 if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
    		 {
    			 strCaption.append(" [ "+ClaimsWebBoardHelper.getWebBoardDesc(request)+ " ]");
    			 strCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");					
    		 }//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))    		 
    		 frmBillSummary.set("caption",strCaption.toString());
    		 request.getSession().setAttribute("frmBillSummary",frmBillSummary);
    		 return this.getForward(strbillsummary,mapping,request);
    	 }//end of try
    	 catch(TTKException expTTK)
 		 {
 			return this.processExceptions(request, mapping, expTTK);
 		 }//end of catch(TTKException expTTK)
 		 catch(Exception exp)
 		 {
 			return this.processExceptions(request, mapping, new TTKException(exp,strbillsummary));
 		 }//end of catch(Exception exp)
     }//end of doViewBillSummary(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     	//HttpServletResponse response)

     /**
      * This method is used to submit the record.
      * Finally it forwards to the appropriate view based on the specified forward mappings
      *
      * @param mapping The ActionMapping used to select this instance
      * @param form The optional ActionForm bean for this request (if any)
      * @param request The HTTP request we are processing
      * @param response The HTTP response we are creating
      * @return ActionForward Where the control will be forwarded, after this request is processed
      * @throws Exception if any error occurs
      */
     public ActionForward doSubmit(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     										HttpServletResponse response) throws Exception{
    	 try{
    		 log.debug("Inside BillSummaryAction doSubmit");
    		 setLinks(request);
    		 DynaActionForm frmBillSummary =(DynaActionForm) form;
    		 ArrayList<Object> alBillDetailInfo = new ArrayList<Object>();
    		 ArrayList<Object> alAilmentInfo= new ArrayList<Object>();
    		 BillSummaryVO billSummaryVO=new BillSummaryVO();
    		 Long lngClaimSeqID = (Long)frmBillSummary.get("selectedClaimSeqID");
    		 Long[] lngselectedWardAccGroupSeqID = (Long[])frmBillSummary.get("selectedWardAccGroupSeqID");
    		 String[] lngSelectedWardTypeID = (String[])frmBillSummary.get("selectedWardTypeID");
    		 String[] strApplyYN = request.getParameterValues("hideDiscountYN");
    		 
    		 String[] strAccGrpDesc=request.getParameterValues("selectedAccGrpDesc");
    		 String[] strWardDesc=request.getParameterValues("selectedWardDesc");
    		 String[] strBillAmt = request.getParameterValues("selectedBillAmt");
    		 String[] strDiscPer = request.getParameterValues("selectedDiscPer");
    		 String[] strNetAmt = request.getParameterValues("selectedNetAmt");
    		 String[] strMaxAmt = request.getParameterValues("selectedMaxAmt");
    		 String[] strBillNotes = request.getParameterValues("selectedBillNotes");
    		 alBillDetailInfo = new ArrayList<Object>();
    		 for(int i=0;i<lngselectedWardAccGroupSeqID.length;i++)
    		 {
    			 
    			 BillDetailVO billDetailVO =new BillDetailVO();
    			 billDetailVO.setAccGroupDesc(strAccGrpDesc[i]);
    			 billDetailVO.setWardDesc(strWardDesc[i]);
    			 billDetailVO.setClaimBillAmt(strBillAmt[i]);
    			 
    			 billDetailVO.setWardTypeID(lngSelectedWardTypeID[i]);
    			 billDetailVO.setApplyDiscountYN(strApplyYN[i]);
    			 billDetailVO.setDiscountPercent(strDiscPer[i]);
    			 billDetailVO.setClaimNetAmount(strNetAmt[i]);
    			 billDetailVO.setClaimMaxAmount(strMaxAmt[i]);
    			 billDetailVO.setBillNotes(strBillNotes[i]);
    			 alBillDetailInfo.add(billDetailVO);
    		 }//end of for
    		 billSummaryVO.setBillDetailVOList(alBillDetailInfo);
    		 //Ailment Values
    		 Long[] lngAilmentCapsSeqID = (Long[])frmBillSummary.get("selectedAilmentCapsSeqID");
    		 Long[] lngICDPCSSeqID = (Long[])frmBillSummary.get("selectedICDPCSSeqID");
    		 String[] strApprovedAilmentAmt = request.getParameterValues("approvedAilmentAmt");
    		 String[] strMaxAilmentAllowedAmt = request.getParameterValues("maxAilmentAllowedAmt");
    		 String[] strAilmentNotes = request.getParameterValues("selectedAilmentNotes");
    		 String[] strDescription = request.getParameterValues("selectedDescription");
    		 
    		 Long[] lngAsscICDPCSSeqID = (Long[])frmBillSummary.get("asscICDPCSSeqID");
    		 Long[] lngPatProcSeqID = (Long[])frmBillSummary.get("patProcSeqID");
    		 Long[] lngProcSeqId = (Long[])frmBillSummary.get("procSeqID");
    		 String[] strProcedureAmt = request.getParameterValues("procedureAmt");
    		 String[] strProcDesc = request.getParameterValues("procDesc");
    		 alAilmentInfo = new ArrayList<Object>();
    		 ArrayList<Object> alProcedureList=null;
    		 if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
    		 {
    			 for(int i=0;i<lngAilmentCapsSeqID.length;i++)
    			 {
    				 TariffAilmentVO tariffAilmentVO = new TariffAilmentVO();
    				 tariffAilmentVO.setAilmentCapsSeqID((lngAilmentCapsSeqID[i]));//lngAilmentCapsSeqID[i]);
    				 tariffAilmentVO.setICDPCSSeqID((lngICDPCSSeqID[i]));//lngICDPCSSeqID[i]);
    				 tariffAilmentVO.setDescription(strDescription[i]);
    				 tariffAilmentVO.setApprovedAilmentAmt(strApprovedAilmentAmt[i]); //approvedAilmentAmt
    				 
    				 tariffAilmentVO.setMaxAilmentAllowedAmt((strMaxAilmentAllowedAmt[i]));
    				 tariffAilmentVO.setAilmentNotes(strAilmentNotes[i]);
    				 alProcedureList=null;
    				 for(int j=0;j<lngAsscICDPCSSeqID.length;j++)
    				 {
    					 if(lngAsscICDPCSSeqID[j].longValue()==lngICDPCSSeqID[i].longValue())
    					 {
    						 if(alProcedureList == null){
    							 alProcedureList = new ArrayList<Object>();							
    						 }//end of if(alLineItemList == null)
    						 TariffProcedureVO tariffProcedureVO=new TariffProcedureVO();
    						 tariffProcedureVO.setPatProcSeqID(lngPatProcSeqID[j]);
    						 tariffProcedureVO.setProcSeqID(lngProcSeqId[j]);
    						 tariffProcedureVO.setProcDesc(strProcDesc[j]);
    						 tariffProcedureVO.setProcedureAmt(strProcedureAmt[j]);
    						 alProcedureList.add(tariffProcedureVO);	
    					 }//end ofif(lngAsscICDPCSSeqID[j]==lngICDPCSSeqID[i])
    				 }//end of for
    				 tariffAilmentVO.setProcedureList(alProcedureList);
    				 alAilmentInfo.add(tariffAilmentVO);
    				 alProcedureList=tariffAilmentVO.getProcedureList();
    				 TariffProcedureVO tariffProcedureVO1=new TariffProcedureVO();    				 
    				 if(alProcedureList!=null)
    				 {
    					 for(int j=0;j<alProcedureList.size();j++)
    					 {
    						 tariffProcedureVO1=(TariffProcedureVO)alProcedureList.get(j);
    					 }//end of for
    				 }//end of if(alProcedureList!=null)
    				 log.debug("tariffProcedureVO1 value is :"+tariffProcedureVO1);
    			 }//end of for(int i=0;i<lngAilmentCapsSeqID.length;i++)
    		 }//end of if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
    		 billSummaryVO.setAilmentVOList(alAilmentInfo);
    		 
    		 billSummaryVO.setClaimSeqID(lngClaimSeqID);
    		 billSummaryVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
    		 frmBillSummary.set("billInfo",(BillDetailVO[])alBillDetailInfo.toArray(new BillDetailVO[0]));
    		 frmBillSummary.set("ailmentInfo",(TariffAilmentVO[])alAilmentInfo.toArray(new TariffAilmentVO[0]));
    		 return mapping.findForward(strSubmitForm);
    	 }//end of try
    	 catch(TTKException expTTK)
 		 {
 			return this.processExceptions(request, mapping, expTTK);
 		 }//end of catch(TTKException expTTK)
 		 catch(Exception exp)
 		 {
 			return this.processExceptions(request, mapping, new TTKException(exp,strbillsummary));
 		 }//end of catch(Exception exp)
     }//end of doSubmit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
     public ActionForward doViewBillDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     													HttpServletResponse response) throws Exception{
    	 try{
    		 log.debug("Inside BillSummaryAction doViewBillDetails ");
    		 setLinks(request);
    		 String strForward="lineitems";
    		 DynaActionForm frmLineItems =(DynaActionForm) form;
    		 log.debug("---- frmLineItems ----- "+frmLineItems);

             return mapping.findForward(strForward);
    	 }//end of try
    	 catch(TTKException expTTK)
 		 {
 			return this.processExceptions(request, mapping, expTTK);
 		 }//end of catch(TTKException expTTK)
 		 catch(Exception exp)
 		 {
 			return this.processExceptions(request, mapping, new TTKException(exp,strbillsummary));
 		 }//end of catch(Exception exp)
     }//end of doViewBillDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     	//HttpServletResponse response)

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
    		 log.debug("Inside BillSummaryAction doSave ");
    		 setLinks(request);
    		 DynaActionForm frmBillSummary =(DynaActionForm) form;
    		 StringBuffer strCaption= new StringBuffer();
    		 ArrayList<Object> alBillDetailInfo = new ArrayList<Object>();
    		 ArrayList<Object> alAilmentInfo= new ArrayList<Object>();
    		 BillSummaryVO billSummaryVO=new BillSummaryVO();
    		 ClaimBillManager billObject=this.getClaimBillManagerObject();    //get the business object
    		 Long[] lngselectedWardAccGroupSeqID = (Long[])frmBillSummary.get("selectedWardAccGroupSeqID");
    		 String[] lngSelectedWardTypeID = (String[])frmBillSummary.get("selectedWardTypeID");
    		 String[] strApplyYN = request.getParameterValues("hideDiscountYN");
    		 alBillDetailInfo = new ArrayList<Object>();
    		 for(int i=0;i<lngselectedWardAccGroupSeqID.length;i++)
    		 {
    			 BillDetailVO billDetailVO =new BillDetailVO();
    			 billDetailVO.setWardTypeID(lngSelectedWardTypeID[i]);
    			 billDetailVO.setApplyDiscountYN(strApplyYN[i]);
    			 alBillDetailInfo.add(billDetailVO);
    		 }//end of for
    		 billSummaryVO.setBillDetailVOList(alBillDetailInfo);
    		 //Ailment Values
    		 Long[] lngAilmentCapsSeqID = (Long[])frmBillSummary.get("selectedAilmentCapsSeqID");
    		 Long[] lngICDPCSSeqID = (Long[])frmBillSummary.get("selectedICDPCSSeqID");
    		 String[] strApprovedAilmentAmt = request.getParameterValues("approvedAilmentAmt");
    		 String[] strMaxAilmentAllowedAmt = request.getParameterValues("maxAilmentAllowedAmt");
    		 String[] strAilmentNotes = request.getParameterValues("selectedAilmentNotes");
    		 
    		 Long[] lngAsscICDPCSSeqID = (Long[])frmBillSummary.get("asscICDPCSSeqID");
    		 Long[] lngPatProcSeqID = (Long[])frmBillSummary.get("patProcSeqID");
    		 Long[] lngProcSeqId = (Long[])frmBillSummary.get("procSeqID");
    		 String[] strProcedureAmt = request.getParameterValues("procedureAmt");
    		 String[] strProcDesc = request.getParameterValues("procDesc");
    		 //frmPreAuthTariffDetail.set("validateAilment","Y");
    		 ArrayList<Object> alAilmentDetail = new ArrayList<Object>();
    		 ArrayList<Object> alProcedureList=null;
    		 if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
    		 {
    			 for(int i=0;i<lngAilmentCapsSeqID.length;i++)
    			 {
    				 TariffAilmentVO tariffAilmentVO = new TariffAilmentVO();
    				 tariffAilmentVO.setAilmentCapsSeqID((lngAilmentCapsSeqID[i]));//lngAilmentCapsSeqID[i]);
    				 tariffAilmentVO.setICDPCSSeqID((lngICDPCSSeqID[i]));//lngICDPCSSeqID[i]);
    				 //tariffAilmentVO.setApprovedAilmentAmt(strApprovedAilmentAmt[i]); //approvedAilmentAmt
    				 tariffAilmentVO.setAilmentApprovedAmt(TTKCommon.getBigDecimal(strApprovedAilmentAmt[i]));
    				 tariffAilmentVO.setMaxAilmentAllowedAmt((strMaxAilmentAllowedAmt[i]));
    				 tariffAilmentVO.setAilmentNotes(strAilmentNotes[i]);
    				 alProcedureList=null;
    				 for(int j=0;j<lngAsscICDPCSSeqID.length;j++)
    				 {
    					 if(lngAsscICDPCSSeqID[j].longValue()==lngICDPCSSeqID[i].longValue())
    					 {
    						 if(alProcedureList == null){
    							 alProcedureList = new ArrayList<Object>();							
    						 }//end of if(alLineItemList == null)
    						 TariffProcedureVO tariffProcedureVO=new TariffProcedureVO();
    						 tariffProcedureVO.setPatProcSeqID(lngPatProcSeqID[j]);
    						 tariffProcedureVO.setProcSeqID(lngProcSeqId[j]);
    						 tariffProcedureVO.setProcDesc(strProcDesc[j]);
    						 tariffProcedureVO.setProcedureAmt(strProcedureAmt[j]);
    						 alProcedureList.add(tariffProcedureVO);	
    					 }//end ofif(lngAsscICDPCSSeqID[j]==lngICDPCSSeqID[i])
    				 }//end of for
    				 tariffAilmentVO.setProcedureList(alProcedureList);
    				 alAilmentDetail.add(tariffAilmentVO);
    			 }//end of for(int i=0;i<lngAilmentCapsSeqID.length;i++)
    		 }//end of if(lngAilmentCapsSeqID !=null && lngAilmentCapsSeqID.length>0)
    		 billSummaryVO.setAilmentVOList(alAilmentDetail);
    		 billSummaryVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
    		 billSummaryVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
    		 int iCoutn=billObject.saveBillSummaryDetail(billSummaryVO);
    		 if(iCoutn>0)
    		 {
    			 request.setAttribute("updated","message.saved");
    			 billSummaryVO=billObject.getBillSummaryDetail(ClaimsWebBoardHelper.getClaimsSeqId(request),
    			 												TTKCommon.getUserSeqId(request));
    			 frmBillSummary = (DynaActionForm)FormUtils.setFormValues("frmBillSummary",billSummaryVO,this,
    			 															mapping,request);
    			 if(billSummaryVO!=null)
    			 {
				   alBillDetailInfo=billSummaryVO.getBillDetailVOList();
				   if(alBillDetailInfo !=null && alBillDetailInfo.size()>=1)
				   {
					 frmBillSummary.set("billInfo",(BillDetailVO[])alBillDetailInfo.toArray(new BillDetailVO[0]));
				   }//end of if(alBillDetailInfo !=null && alBillDetailInfo.size()>=1)
				   alAilmentInfo=billSummaryVO.getAilmentVOList();
				   if(alAilmentInfo!=null && alAilmentInfo.size()>0)
				   {
				     frmBillSummary.set("ailmentInfo",(TariffAilmentVO[])alAilmentInfo.toArray(new TariffAilmentVO[0]));
				   }//end of  if(alAilmentInfo!=null && alAilmentInfo.size()>0)
    			  }//end of if(billSummaryVO!=null)
	    			 strCaption.append("[ "+ClaimsWebBoardHelper.getClaimantName(request)+" ]");
	        		 if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
	    					strCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
	        		 frmBillSummary.set("caption",strCaption.toString());
	        		 request.getSession().setAttribute("frmBillSummary",frmBillSummary);
    		 }//end of if(iCoutn>0)
    		 return this.getForward(strbillsummary,mapping,request);
    	 }//end of try
    	 catch(TTKException expTTK)
 		 {
 			return this.processExceptions(request, mapping, expTTK);
 		 }//end of catch(TTKException expTTK)
 		 catch(Exception exp)
 		 {
 			return this.processExceptions(request, mapping, new TTKException(exp,strbillsummary));
 		 }//end of catch(Exception exp)
     }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

     /**
     * This method returns the ClaimBillManager session object for invoking DAO methods from it.
     * @return ClaimBillManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private ClaimBillManager getClaimBillManagerObject() throws TTKException
    {
        ClaimBillManager claimBillManager = null;
        try
        {
            if(claimBillManager == null)
            {
                InitialContext ctx = new InitialContext();
                claimBillManager = (ClaimBillManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimBillManagerBean!com.ttk.business.claims.ClaimBillManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strbillsummary);
        }//end of catch
        return claimBillManager;
    }//end getClaimBillManagerObject()
}//end of BillSummaryAction