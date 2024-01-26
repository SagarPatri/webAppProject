/**
 * @ (#) ..AuthorizationDetailsAction.java May 18, 2006
 * Project       : TTK HealthCare Services
 * File          : AuthorizationDetailsAction.java
 * Author        : Chandrasekaran J
 * Company       : Span Systems Corporation
 * Date Created  : May 18, 2006
 *
 * @author       : Chandrasekaran J
 * Modified by   : Manikanta Kumar G G
 * Modified date : Aug 28 ,2010
 * Reason        : Service Tax Changes
 */

package com.ttk.action.preauth;

import java.io.File;
import java.math.BigDecimal;
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
import com.ttk.action.table.TableData;
import com.ttk.business.claims.ClaimManager;
import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.ClaimsWebBoardHelper;

import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.claims.ClauseVO;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.preauth.AuthorizationVO;
import com.ttk.dto.preauth.SIInfoVO;
import com.ttk.dto.preauth.BalanceCopayDeductionVO;//added as per KOC 1140 and 1142(1165)
import formdef.plugin.util.FormUtils;

/**
 * This class is reusable for viewing the authorization, settlement in the pre-auth and claims flow.
 * This class also provides option for saving the same.
 */

public class AuthorizationDetailsAction extends TTKAction
{
    private static Logger log = Logger.getLogger(AuthorizationDetailsAction.class);

    //Action mapping forwards.
    private static final String strAuthorization="authorization";
    private static final String strClaimsSettlement="settlement";

    //string for comparision
    private static final String strPreAuthorization="Pre-Authorization";
    private static final String strClaims="Claims";

    //Exception Message Identifier
    private static final String strAuthorizationError="authorizations";

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
    public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    															HttpServletResponse response) throws Exception
    {
    	try
    	{
    		setLinks(request);
    		log.debug(" inside AuthorizationDetailsAction doView");
    		AuthorizationVO authorizationVO=null;
    		String strForward="";
    		String strClaimantname="";
    		String strEnrollmentID="";
    		String strLink=TTKCommon.getActiveLink(request);
    		String strActiveTab=TTKCommon.getActiveTab(request);
            DynaActionForm frmAuthorizationDetails= (DynaActionForm)form;
    		ArrayList<Object> alAuthorization = new ArrayList<Object>();
    		StringBuffer strCaption= new StringBuffer();
    		Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");
    		PreAuthManager preAuthObject=this.getPreAuthManagerObject();
    		if(strLink.equals(strPreAuthorization))
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
    		}//end of if(strLink.equals(strPreAuthorization))
    		else if(strLink.equals(strClaims))
    		{
    			if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
    			{
    				TTKException expTTK = new TTKException();
    				expTTK.setMessage("error.Claims.required");
    				throw expTTK;
    			}//end of if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
    			if(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y"))
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Claims.codingflow");
					throw expTTK;
				}//end of if(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y"))
    		}//end of  else if(strLink.equals(strClaims))
    		if(strLink.equals(strPreAuthorization))
    		{
    			if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
    					PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
    			{
    				strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
    			}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
    			strForward=strAuthorization;
    		}//end of if(strLink.equals(strPreAuthorization))
    		else if(strLink.equals(strClaims))
    		{
    			if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
    			{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
    			}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
    			strForward=strClaimsSettlement;
    		}// end of else if(strLink.equals(strClaims))
    		if(strLink.equals(strClaims))
    		{
    			strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
    			strCaption.append("Settlement Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
    		}//end of if(strLink.equals(strClaims))
    		else
    		{
    			strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
    			strCaption.append("Authorization Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
    		}//end of else
    		if(strLink.equals(strClaims))
    		{
    			alAuthorization.add(ClaimsWebBoardHelper.getMemberSeqId(request));
    			alAuthorization.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
    			alAuthorization.add(ClaimsWebBoardHelper.getClmEnrollDetailSeqId(request));
    			alAuthorization.add(TTKCommon.getUserSeqId(request));
    		}//end of if(strLink.equals(strClaims))
    		else
    		{
    			alAuthorization.add(PreAuthWebBoardHelper.getMemberSeqId(request));
    			alAuthorization.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
    			alAuthorization.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
    			alAuthorization.add(TTKCommon.getUserSeqId(request));
    		}//end of else
    		authorizationVO=preAuthObject.getAuthorizationDetail(alAuthorization,TTKCommon.getActiveLink(request));
    		if(strLink.equals(strPreAuthorization)){
    			authorizationVO.setEnrollDtlSeqID(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
    		}//end of if(strLink.equals(strPreAuthorization))
    		
    		authorizationVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
    		frmAuthorizationDetails= (DynaActionForm)FormUtils.setFormValues("frmAuthorizationDetails",
    																	authorizationVO, this, mapping, request);
    		toolBar.getConflictIcon().setVisible(toolBar.getConflictIcon().isVisible() &&
    															authorizationVO.getDiscPresentYN().equals("Y"));
    		frmAuthorizationDetails.set("status","N");
    		if((authorizationVO.getStatusTypeID().equals("REJ"))||(authorizationVO.getStatusTypeID().equals("APR"))||(authorizationVO.getStatusTypeID().equals("PCO")))
    		{
    			frmAuthorizationDetails.set("preauthstatus","Y");
    		}//end of if((authorizationVO.getStatusTypeID().equals("REJ"))||
    		/*if(((authorizationVO.getStatusTypeID().equals("REJ"))||(authorizationVO.getStatusTypeID().equals("APR")))&&(authorizationVO.getStatusTypeID().equals("INP")))
    		{
    			frmAuthorizationDetails.set("preauthstatus","N");
    		}//end of if((authorizationVO.getStatusTypeID().equals("REJ"))||
*/    		//(authorizationVO.getStatusTypeID().equals("PCN"))||(authorizationVO.getStatusTypeID().equals("APR")))
    		else
    		{
    			frmAuthorizationDetails.set("preauthstatus","N");
    		}//end of else
    		if(authorizationVO.getApprovedAmount()!= null)
    		{
    			if((authorizationVO.getStatusTypeID().equals("PCO"))||(authorizationVO.getStatusTypeID().equals("REJ"))
    				||(authorizationVO.getStatusTypeID().equals("PCN"))||
    				((authorizationVO.getStatusTypeID().equals("APR"))&&(authorizationVO.getApprovedAmount().
    				compareTo(authorizationVO.getRequestedAmount())==-1)))
    			{
    				frmAuthorizationDetails.set("status","Y");
    			}//end of if
    			else
    			{
    				frmAuthorizationDetails.set("status","N");
    			}//end of else
    		}// end of if(authorizationVO.getApprovedAmount()!= null)
    		//for discharge voucher status display in settelment tab
    		if(strLink.equals(strClaims)&&authorizationVO.getCompletedYN().equals("Y")
    				&&authorizationVO.getStatusTypeID().equals("APR")){
    			if("Y".equals(authorizationVO.getDVMessageYN()))
    			{
    				request.setAttribute("dischargeStatus","message.dischargeVoucherRequired");
    			}//end of if("Y".equals(authorizationVO.getDVMessageYN()))
    			else if("N".equals(authorizationVO.getDVMessageYN()))
    			{
        			request.setAttribute("dischargeStatus","message.dischargeVoucherNotRequired");
    			}//end of else if("N".equals(authorizationVO.getDVMessageYN()))
    			else if("O".equals(authorizationVO.getDVMessageYN()))
    			{
        			request.setAttribute("dischargeStatus","message.dischargeVoucherOptional");
    			}//end of else if("O".equals(authorizationVO.getDVMessageYN()))
    		}//end of if    		
    		if(authorizationVO.getUnAvailableSuminsured()>0)
    		{
    			request.setAttribute("updated","error.preauth.authorization.unavailablesuminsured");    			
    		}//end of if(authorizationVO.getUnAvailableSuminsured()>0)   
		
			//KOC 1286 for OPD
    		
    		if(authorizationVO.getOPDAmountYN().equals("Y"))
    		{
    			
    			frmAuthorizationDetails.set("opdAmountYN","Y");
    		}//end of if((authorizationVO.getStatusTypeID().equals("REJ"))||
    		//(authorizationVO.getStatusTypeID().equals("PCN"))||(authorizationVO.getStatusTypeID().equals("APR")))
    		else
    		{
    			
    			frmAuthorizationDetails.set("opdAmountYN","N");
    		}//end of else
    		if(authorizationVO.getClaimSubTypeID().equals("OPD"))
    		{
    			
    			frmAuthorizationDetails.set("claimsubtype","OPD");
    		}
            request.getSession().setAttribute("claimsubgenraltypeid",authorizationVO.getClaimSubGeneralTypeId());
                               
    		//KOC 1286 for OPD    
    		
			if(authorizationVO.getSeniorCitizenYN().equals("Y"))
			{
				
				//frmAuthorizationDetails.set("seniorCitizen","Please consider as a senior citizen");
				request.setAttribute("seniorCitizen","message.seniorCitizen");
			}
			//koc for griavance
			//denial process
			if(authorizationVO.getDenialBanyn().equals("Y"))
			{
				request.setAttribute("denialBanyn","error.preauth.claims..require.inscompany.decision.banner");
			}
			//denial process
    		//keep the frmAuthorizationDetails in session scope
    		frmAuthorizationDetails.set("caption",String.valueOf(strCaption));
    		//CR KOC - Mail-SMS template for cigna
    		frmAuthorizationDetails.set("mailNotify",authorizationVO.getMailNotifyYN());
    		this.formValue(authorizationVO,frmAuthorizationDetails,request);
    		request.getSession().setAttribute("frmAuthorizationDetails",frmAuthorizationDetails);
    		return this.getForward(strForward, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAuthorizationError));
    	}//end of catch(Exception exp)
    }// end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    																HttpServletResponse response) throws Exception
    {
    	log.debug("inside AuthorizationDetailsAction doChangeWebBoard");
    	return doView(mapping ,form, request, response);
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
    																HttpServletResponse response) throws Exception
    {
    	log.debug("inside AuthorizationDetailsAction doReset");
    	return doView(mapping ,form, request, response);
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
    															HttpServletResponse response) throws Exception {
    	try
    	{
    		setLinks(request);
    		log.debug("inside AuthorizationDetailsAction doSave");
    		AuthorizationVO authorizationVO=null;
    		String strForward="";
    		String strClaimantname="";
    		String strEnrollmentID="";
    		String strLink=TTKCommon.getActiveLink(request);
    		DynaActionForm frmAuthorizationDetails= (DynaActionForm)request.getSession().getAttribute("frmAuthorizationDetails");
    		String strTaxAmtPaid ="";
    		String strFinalApprAmt ="";
    		ArrayList<Object> alAuthorization = new ArrayList<Object>();
    		StringBuffer strCaption= new StringBuffer();
    		Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");
    		PreAuthManager preAuthObject=this.getPreAuthManagerObject();
    		
    		if(strLink.equals(strPreAuthorization))
    		{
    			if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
    			{
    				TTKException expTTK = new TTKException();
    				expTTK.setMessage("error.PreAuthorization.required");
    				throw expTTK;
    			}//end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
    		}//end of if(strLink.equals(strPreAuthorization))
    		else if(strLink.equals(strClaims))
    		{
    			if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
    			{
    				TTKException expTTK = new TTKException();
    				expTTK.setMessage("error.Claims.required");
    				throw expTTK;
    			}//end of if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
    		}//end of  else if(strLink.equals(strClaims))
    		if(strLink.equals(strPreAuthorization))
    		{
    			if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
    			{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
    			}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
    			strForward=strAuthorization;
    		}//end of if(strLink.equals(strPreAuthorization))
    		else if(strLink.equals(strClaims))
    		{
    			if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
    			{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
    			}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
    			strForward=strClaimsSettlement;
    		}// end of else if(strLink.equals(strClaims))
    		if(strLink.equals(strClaims))
    		{
    			strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
    			strCaption.append("Settlement Details -").append("[").append(strClaimantname).append("]")
    																			.append(strEnrollmentID);
    		}//end of if(strLink.equals(strClaims))
    		else
    		{
    			strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
    			strCaption.append("Authorization Details -").append("[").append(strClaimantname).append("]")
    																				.append(strEnrollmentID);
    		}//end of else
    		authorizationVO=new AuthorizationVO();
    		int iUpdate=0;
    		if(strLink.equals(strClaims))
    		{
    			alAuthorization.add(ClaimsWebBoardHelper.getMemberSeqId(request));
    			alAuthorization.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
    			alAuthorization.add(ClaimsWebBoardHelper.getClmEnrollDetailSeqId(request));
    			alAuthorization.add(TTKCommon.getUserSeqId(request));
    			authorizationVO = (AuthorizationVO)FormUtils.getFormValues(frmAuthorizationDetails, this,
    																					mapping, request);
    			authorizationVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
    			authorizationVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
    			//authorizationVO.setApprovedAmount(TTKCommon.getBigDecimal(frmAuthorizationDetails.getString("approvedAmount")));
    			strTaxAmtPaid = frmAuthorizationDetails.getString("taxAmtPaid");
    			strFinalApprAmt=frmAuthorizationDetails.getString("finalApprovedAmt");
    			authorizationVO.setTaxAmtPaid(TTKCommon.getBigDecimal(frmAuthorizationDetails.getString("taxAmtPaid")));
    			if("Y".equals(TTKCommon.checkNull(frmAuthorizationDetails.getString("mailNotify"))))
    			{
    				authorizationVO.setMailNotifyYN("Y");
    			}
    			else
    			{
    				authorizationVO.setMailNotifyYN("N");
    			}
				//Added for Policy Deductable - KOC - 1277
    			authorizationVO.setBalanceDedAmount(TTKCommon.getBigDecimal(frmAuthorizationDetails.getString("balanceDedAmount")));
    			
    		}//end of  if(strLink.equals(strClaims))
    		else
    		{
    			alAuthorization.add(PreAuthWebBoardHelper.getMemberSeqId(request));
    			alAuthorization.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
    			alAuthorization.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
    			alAuthorization.add(TTKCommon.getUserSeqId(request));
    			authorizationVO = (AuthorizationVO)FormUtils.getFormValues(frmAuthorizationDetails, this,
    																					mapping, request);
    			authorizationVO.setPreAuthSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
    			authorizationVO.setEnrollDtlSeqID(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
    			authorizationVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
    			if("Y".equals(TTKCommon.checkNull(frmAuthorizationDetails.getString("mailNotify"))))
    			{
    				authorizationVO.setMailNotifyYN("Y");
    			}
    			else
    			{
    				authorizationVO.setMailNotifyYN("N");
    			}
				//Added for Policy Deductable - KOC-1277
    			authorizationVO.setBalanceDedAmount(TTKCommon.getBigDecimal(frmAuthorizationDetails.getString("balanceDedAmount")));
    		}//end of else
    		if(!authorizationVO.getStatusTypeID().equals("PCO")&&!authorizationVO.getStatusTypeID().equals("REJ")&&
    			!authorizationVO.getStatusTypeID().equals("PCN")&& authorizationVO.getStatusTypeID().equals("APR")&&
    			!(authorizationVO.getApprovedAmount().compareTo(authorizationVO.getRequestedAmount())==-1))
    		{
    			authorizationVO.setReasonTypeID("");
    		}//end of if
    		iUpdate=preAuthObject.saveAuthorization(authorizationVO,TTKCommon.getActiveLink(request));
    		if(iUpdate > 0)
    		{
    			request.setAttribute("updated","message.savedSuccessfully");
    			authorizationVO=preAuthObject.getAuthorizationDetail(alAuthorization,TTKCommon.getActiveLink(request));
    			authorizationVO.setApprovedAmount(TTKCommon.getBigDecimal(frmAuthorizationDetails.getString("approvedAmount")));
    			if(strLink.equals(strPreAuthorization)){
        			authorizationVO.setEnrollDtlSeqID(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
        		}//end of if(strLink.equals(strPreAuthorization))
    			
    			//authorizationVO.setEnrollDtlSeqID(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
    			authorizationVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
    			frmAuthorizationDetails= (DynaActionForm)FormUtils.setFormValues("frmAuthorizationDetails",
    																		authorizationVO, this, mapping, request);
    			//frmAuthorizationDetails.set("taxAmtPaid", strTaxAmtPaid);
    			//frmAuthorizationDetails.set("finalApprovedAmt",strFinalApprAmt);
    			toolBar.getConflictIcon().setVisible(toolBar.getConflictIcon().isVisible() &&
    													authorizationVO.getDiscPresentYN().equals("Y"));
    		}//end of if(iUpdate > 0)
    		toolBar.getConflictIcon().setVisible(toolBar.getConflictIcon().isVisible() &&
    														authorizationVO.getDiscPresentYN().equals("Y"));
    		frmAuthorizationDetails.set("status","N");
    		if((authorizationVO.getStatusTypeID().equals("REJ"))||(authorizationVO.getStatusTypeID().equals("APR"))||(authorizationVO.getStatusTypeID().equals("PCO")))
    		{
    			frmAuthorizationDetails.set("preauthstatus","Y");
    		}//end of if
    		/*if(((authorizationVO.getStatusTypeID().equals("REJ"))||(authorizationVO.getStatusTypeID().equals("APR")))&&(authorizationVO.getStatusTypeID().equals("INP")))
    		{
    			frmAuthorizationDetails.set("preauthstatus","N");
    		}//end of if((authorizationVO.getStatusTypeID().equals("REJ"))||
*/    		else
    		{
    			frmAuthorizationDetails.set("preauthstatus","N");
    		}//end of else
    		if(authorizationVO.getApprovedAmount()!= null)
    		{
    			if((authorizationVO.getStatusTypeID().equals("PCO"))||(authorizationVO.getStatusTypeID().equals("REJ"))
    			 ||(authorizationVO.getStatusTypeID().equals("PCN"))||((authorizationVO.getStatusTypeID().equals("APR"))
    			  &&(authorizationVO.getApprovedAmount().compareTo(authorizationVO.getRequestedAmount())==-1)))
    			{
    				frmAuthorizationDetails.set("status","Y");
    			}//end of if
    			else
    			{
    				frmAuthorizationDetails.set("status","N");
    			}//end of else
    		}//end of if(authorizationVO.getApprovedAmount()!= null)
    		frmAuthorizationDetails.set("caption",String.valueOf(strCaption));
    		//CR KOC - Mail-SMS template for cigna
    		frmAuthorizationDetails.set("mailNotify",authorizationVO.getMailNotifyYN());
    		this.formValue(authorizationVO,frmAuthorizationDetails,request);
    		//KOC 1286 for OPD    
    		
			if(authorizationVO.getSeniorCitizenYN().equals("Y"))
			{
				
				//frmAuthorizationDetails.set("seniorCitizen","Please consider as a senior citizen");
				request.setAttribute("seniorCitizen","message.seniorCitizen");
			}
			//koc for griavance
			//denial process
			if(authorizationVO.getDenialBanyn().equals("Y"))
			{				
				request.setAttribute("denialBanyn","error.preauth.claims..require.inscompany.decision.banner");
			}
			//denial process
    		request.getSession().setAttribute("frmAuthorizationDetails",frmAuthorizationDetails);
    		return this.getForward(strForward, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAuthorizationError));
    	}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
	 * This method is used to select the rejection clauses for rejecting the claims.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
    public ActionForward doClauses(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception {
    	try
    	{
    		setLinks(request);
    		log.debug("inside AuthorizationDetailsAction doClauses");
    		DynaActionForm frmRejectionClauses= (DynaActionForm)form;
    		frmRejectionClauses.set("frmChanged","changed");

    		//return this.getForward("clauses", mapping, request);
    		return mapping.findForward("clauses");
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAuthorizationError));
    	}//end of catch(Exception exp)
    }//end of doSelectClause(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    	//HttpServletResponse response)

    /**
	 * This method is used to select the rejection clauses for rejecting the claims.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
    public ActionForward doSelectClause(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception {
    	try
    	{
    		setLinks(request);
    		log.debug("inside AuthorizationDetailsAction doSelectClause");
    		String strForwarPath="";
    		String strEnrollmentID="";
    		StringBuffer strCaption= new StringBuffer();
    		DynaActionForm frmRejectionClauses= (DynaActionForm)form;
    		ClaimManager claimObject=this.getClaimManagerObject();
    		ClauseVO clauseVO=null;
    		DynaActionForm frmAuthorizationDetails= (DynaActionForm)request.getSession().getAttribute("frmAuthorizationDetails");
    		ArrayList <Object>alClauseList=new ArrayList<Object>();
    		ArrayList <Object>alRejClauseList=new ArrayList<Object>();

    		if(TTKCommon.getActiveLink(request).equalsIgnoreCase("Pre-Authorization"))
    		{
    			strForwarPath="preauthclauses";
    			alClauseList.add(null);
    			alClauseList.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
    			if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
    			{
					strEnrollmentID= strEnrollmentID+" ["+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
    			}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
    			strCaption.append("[").append(PreAuthWebBoardHelper.getClaimantName(request)).append("]").append(strEnrollmentID);
    		}else
    		{
    			strForwarPath="claimsclauses";
    			alClauseList.add(TTKCommon.getLong(frmAuthorizationDetails.get("claimSeqID").toString()));
    			alClauseList.add(null);
    			if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
    			{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
    			}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&& ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
    			strCaption.append("[").append(ClaimsWebBoardHelper.getClaimantName(request)).append("]").append(strEnrollmentID);
    		}//end of else

    		alClauseList.add(TTKCommon.getLong(frmAuthorizationDetails.get("policySeqID").toString()));
    		alClauseList.add(frmAuthorizationDetails.get("enrolTypeID"));
    		alClauseList.add(frmAuthorizationDetails.get("claimAdmnDate"));
    		clauseVO=claimObject.getClauseDetail(alClauseList);
    		ArrayList alClauses = (ArrayList)clauseVO.getPolicyClauseVO();
    		frmRejectionClauses.set("rejHeaderInfo",clauseVO.getRejHeaderInfo());
    		frmRejectionClauses.set("rejFooterInfo",clauseVO.getRejFooterInfo());
    		frmRejectionClauses.set("rejFooterInfo",clauseVO.getRejFooterInfo());
    		frmRejectionClauses.set("claimTypeID",clauseVO.getClaimTypeID());
    		frmRejectionClauses.set("letterTypeID",clauseVO.getLetterTypeID());
    		frmRejectionClauses.set("listclauses",alClauses);
    		if(TTKCommon.getActiveLink(request).equalsIgnoreCase("Pre-Authorization"))
    		{
    			frmRejectionClauses.set("seqId",PreAuthWebBoardHelper.getPreAuthSeqId(request).toString());
    		}//end of if(TTKCommon.getActiveLink(request).equalsIgnoreCase("Pre-Authorization"))
    		else
    		{
    			frmRejectionClauses.set("seqId",ClaimsWebBoardHelper.getClaimsSeqId(request).toString());
    			//nhcpLetterType List loading in the settlement screen
    			alRejClauseList.add(clauseVO.getClaimTypeID());
    			alRejClauseList.add(clauseVO.getInsSeqID());
    			ArrayList alRejList = claimObject.getRejectionLetterList(alRejClauseList);
    			if(alRejList == null)
    			{
    				alRejList = new ArrayList();
    			}//end of if(alRejList == null)
    			frmRejectionClauses.set("nhcpLetterType",alRejList);    				
    		}//end of else
    		frmRejectionClauses.set("caption",strCaption.toString());
    		return this.getForward(strForwarPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAuthorizationError));
    	}//end of catch(Exception exp)
    }//end of doSelectClause(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    	//HttpServletResponse response)

    /**
	 * This method is used to save the rejection clauses for a particular claim.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
    public ActionForward doSaveClause(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception {
    	try
    	{
    		setLinks(request);
    		log.debug("inside AuthorizationDetailsAction doSaveClause");
    		String strForwarPath="";
    		String strEnrollmentID="";
    		ArrayList<Object> alClause=new ArrayList<Object>();
    		DynaActionForm frmRejectionClauses= (DynaActionForm)form;
    		ClaimManager claimObject=this.getClaimManagerObject();
    		ClauseVO clauseVO=null;

    		if(TTKCommon.getActiveLink(request).equalsIgnoreCase("Pre-Authorization"))
    		{
    			alClause.add(null);
    			alClause.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request));

    		}else
    		{
    			alClause.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
    			alClause.add(null);
    		}

    		alClause.add(frmRejectionClauses.get("clauseIds"));
    		alClause.add(frmRejectionClauses.get("rejHeaderInfo"));
    		alClause.add(frmRejectionClauses.get("rejFooterInfo"));
    		alClause.add(frmRejectionClauses.get("letterTypeID"));
    		alClause.add(TTKCommon.getUserSeqId(request));

    		String strClauses=claimObject.saveClauseDetail(alClause);
    		
    		request.setAttribute("updated","message.savedSuccessfully");

            DynaActionForm frmAuthorizationDetails= (DynaActionForm)request.getSession().getAttribute("frmAuthorizationDetails");
            frmAuthorizationDetails.set("clauseRemarks",strClauses);
            ArrayList <Object>alClauseList=new ArrayList<Object>();
            StringBuffer strCaption= new StringBuffer();
            if(TTKCommon.getActiveLink(request).equalsIgnoreCase("Pre-Authorization"))
    		{
            	if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
            	strCaption.append("[").append(ClaimsWebBoardHelper.getClaimantName(request)).append("]").append(strEnrollmentID);
    		}else
    		{
    			if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
    			strCaption.append("[").append(ClaimsWebBoardHelper.getClaimantName(request)).append("]").append(strEnrollmentID);
    		}
    		if(TTKCommon.getActiveLink(request).equalsIgnoreCase("Pre-Authorization"))
    		{
    			strForwarPath="preauthclauses";
    			alClauseList.add(null);
    			alClauseList.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
    		}else
    		{
    			strForwarPath="claimsclauses";
    			alClauseList.add(TTKCommon.getLong(frmAuthorizationDetails.get("claimSeqID").toString()));
    			alClauseList.add(null);
    		}

    		alClauseList.add(TTKCommon.getLong(frmAuthorizationDetails.get("policySeqID").toString()));
    		alClauseList.add(frmAuthorizationDetails.get("enrolTypeID"));
    		alClauseList.add(frmAuthorizationDetails.get("claimAdmnDate"));
    		clauseVO=claimObject.getClauseDetail(alClauseList);
    		ArrayList alClauses = (ArrayList)clauseVO.getPolicyClauseVO();
    		frmRejectionClauses.set("rejHeaderInfo",clauseVO.getRejHeaderInfo());
    		frmRejectionClauses.set("rejFooterInfo",clauseVO.getRejFooterInfo());
    		frmRejectionClauses.set("rejFooterInfo",clauseVO.getRejFooterInfo());
    		frmRejectionClauses.set("claimTypeID",clauseVO.getClaimTypeID());
    		frmRejectionClauses.set("letterTypeID",clauseVO.getLetterTypeID());
    		frmRejectionClauses.set("listclauses",alClauses);
    		if(TTKCommon.getActiveLink(request).equalsIgnoreCase("Pre-Authorization"))
    		{
    			frmRejectionClauses.set("seqId",PreAuthWebBoardHelper.getPreAuthSeqId(request).toString());
    		}else
    		{
    			frmRejectionClauses.set("seqId",ClaimsWebBoardHelper.getClaimsSeqId(request).toString());
    		}
    		frmRejectionClauses.set("caption",strCaption.toString());
    		return this.getForward(strForwarPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAuthorizationError));
    	}//end of catch(Exception exp)
    }//end of doSaveClause(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    	//HttpServletResponse response)

    /**
	 * This method is used to close the rejection caluses screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
    public ActionForward doCloseClause(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception {
    	try
    	{
    		setLinks(request);
    		log.debug("inside AuthorizationDetailsAction doCloseClause");
    		String strForwarPath="";
    		if(TTKCommon.getActiveLink(request).equalsIgnoreCase("Pre-Authorization"))
    		{
    			strForwarPath="authorization";
    		}else
    		{
    			strForwarPath="settlement";
    		}

    		return this.getForward(strForwarPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAuthorizationError));
    	}//end of catch(Exception exp)
    }//end of doCloseClause(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    	//HttpServletResponse response)

    /**
	 * This method is used to Send the Authorization Details generated Approval or Rejected letters to recipient.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
    public ActionForward doSendAuthorization(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception {
    	try
    	{
    		setLinks(request);
    		log.debug("inside AuthorizationDetailsAction doSendAuthorization");
    		String strForward="";
    		String strClaimantname="";
    		String strEnrollmentID="";
    		String strLink=TTKCommon.getActiveLink(request);
    		StringBuffer strCaption= new StringBuffer();
    		DynaActionForm frmAuthorizationDetails= (DynaActionForm)form;
    		AuthorizationVO authorizationVO=null;
    		String strIdentifier = "";
    		CommunicationManager commManagerObject=this.getCommunicationManagerObject();
    		if(strLink.equals(strPreAuthorization))
    		{
    			if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
    			{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
    			}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
    			strForward=strAuthorization;
    		}//end of if(strLink.equals(strPreAuthorization))
    		else if(strLink.equals(strClaims))
    		{
    			if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
    			{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
    			}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
    			strForward=strClaimsSettlement;
    		}// end of else if(strLink.equals(strClaims))
    		if(strLink.equals(strClaims))
    		{
    			strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
    			strCaption.append("Settlement Details -").append("[").append(strClaimantname).append("]")
    																			.append(strEnrollmentID);
    		}//end of if(strLink.equals(strClaims))
    		else
    		{
    			strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
    			strCaption.append("Authorization Details -").append("[").append(strClaimantname).append("]")
    																				.append(strEnrollmentID);
    		}//end of else
    		if(strLink.equals(strPreAuthorization)){
    			authorizationVO=new AuthorizationVO();
        		authorizationVO = (AuthorizationVO)FormUtils.getFormValues(frmAuthorizationDetails, this,mapping, request);
        		
        		if(authorizationVO.getStatusTypeID().equals("REJ")){
        			if(authorizationVO.getCigna_Ins_Cust().equals("Y"))
        			{
        				strIdentifier = "CIGNA_PAT_REJECT_EMAIL_AD|CIGNA_PAT_REJECT_EMAIL_PO";
        			}
        			else
        			{
        				strIdentifier = "PREAUTH_REJECTED";
        			}
        			
        		}//end of if(authorizationVO.getStatusTypeID().equals("REJ"))
        		else if(authorizationVO.getStatusTypeID().equals("APR")){
        			if((authorizationVO.getCigna_Ins_Cust().equals("Y")) && (authorizationVO.getMailNotifyYN().equals("Y")||authorizationVO.getMailNotifyYN().equals("on")))
        			{
        				strIdentifier = "CIGNA_PAT_FINAL_APP_EMAIL_AD|CIGNA_PAT_FINAL_APP_EMAIL_PO";        				
        			}
        			else if((authorizationVO.getMailNotifyYN().equals("N")) && (authorizationVO.getCigna_Ins_Cust().equals("Y")))
        			{
        				strIdentifier = "CIGNA_PAT_INITIAL_APP_EMAIL_AD|CIGNA_PAT_INITIAL_APP_EMAIL_PO";
        			}
        			else
        			{
        				strIdentifier = "PREAUTH_APPROVED";
        			}
        			
        		}//end of if(authorizationVO.getStatusTypeID().equals("APR"))
    		}//end of if(strLink.equals(strPreAuthorization))
    		else if(strLink.equals(strClaims)) {
    			authorizationVO=new AuthorizationVO();
        		authorizationVO = (AuthorizationVO)FormUtils.getFormValues(frmAuthorizationDetails, this,mapping, request);
        		        		
        		if(authorizationVO.getClaimTypeID().equals("CTM")){
        			if(authorizationVO.getStatusTypeID().equals("REJ")){
        				if(authorizationVO.getCigna_Ins_Cust().equals("Y"))
        				{
        					strIdentifier = "CIGNA_CLM_REJECT_EMAIL_AD|CIGNA_CLM_REJECT_EMAIL_PO";
        				}
        				else
        				{
        					strIdentifier = "CLAIM_MR_REJECTED";
        				}
            			
            		}//end of if(authorizationVO.getStatusTypeID().equals("REJ"))
            		else if(authorizationVO.getStatusTypeID().equals("APR")){
            			if(authorizationVO.getCigna_Ins_Cust().equals("Y"))
            			{
            				strIdentifier = "";       //CIGNA_CLM_SETTLE_EMAIL_AD|CIGNA_CLM_SETTLE_EMAIL_PO     				
            			}
            			else
            			{
            				strIdentifier = "CLAIM_MR_APPROVE";
            			}
            			
            		}//end of if(authorizationVO.getStatusTypeID().equals("APR"))
        			
            		else if(authorizationVO.getStatusTypeID().equals("PCO"))
        			{
        				if(authorizationVO.getCigna_Ins_Cust().equals("Y"))
                    	{
                    		strIdentifier = "CIGNA_CLM_CLOSER_EMAIL_AD|CIGNA_CLM_CLOSER_EMAIL_PO";            				
                    	}
        			}       			
        			
        		}//end of if(authorizationVO.getClaimTypeID().equals("CTM"))
        		else{
        			if(authorizationVO.getStatusTypeID().equals("REJ")){
        				if(authorizationVO.getCigna_Ins_Cust().equals("Y"))
        				{
        					strIdentifier = "CIGNA_CLM_REJECT_EMAIL_AD|CIGNA_CLM_REJECT_EMAIL_PO";
        					
        				}
        				else
        				{
        					strIdentifier = "CLAIM_NHCP_REJECTED";
        				}
            			
            		}//end of if(authorizationVO.getStatusTypeID().equals("REJ"))
            		else if(authorizationVO.getStatusTypeID().equals("APR")){
            			strIdentifier = "CLAIM_NHCP_APPROVE";
            		}//end of if(authorizationVO.getStatusTypeID().equals("APR"))
        		}//end of else
        	}//end of else if(strLink.equals(strClaims))
    		
    		
    		//CommunicationOptionVO communicationOptionVO = null;
    		Long lngUserID = TTKCommon.getUserSeqId(request);
    		String strAuthpdf = TTKPropertiesReader.getPropertyValue("authorizationrptdir")+request.getParameter("authorizationNo")+".pdf";
    		//String[] strCommArray = {"SMS","EMAIL","FAX"};
    		File file = new File(strAuthpdf);
    		
    			if(file.exists()){
        			//communicationOptionVO = new CommunicationOptionVO();
    				if(authorizationVO.getCigna_Ins_Cust().equals("Y"))
    	    		{
    	    			commManagerObject.sendCignaAuthorization(PreAuthWebBoardHelper.getPreAuthSeqId(request),strIdentifier,lngUserID);
    	    		}
    				else
    				{
    					commManagerObject.sendAuthorization(PreAuthWebBoardHelper.getPreAuthSeqId(request),strIdentifier,lngUserID);
    				}
        			
        			//communicationOptionVO.setFile(new File(strAuthpdf));
//        			commManagerObject.sendSMS(strCommArray[0]);
//        			commManagerObject.sendMessage(strCommArray[1]);
//        			commManagerObject.sendFax(strCommArray[2]);    			
        		}//end of if(file.exists())
        		else{
        			TTKException expTTK = new TTKException();
                    expTTK.setMessage("error.authpdf");
                    throw expTTK;
        		}//end of else
    		
    		
    		return this.getForward(strForward, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAuthorizationError));
    	}//end of catch(Exception exp)
    }//end of doSendAuthorization(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    	//HttpServletResponse response)

    /**
     * Returns the PreAuthManager session object for invoking methods on it.
     * @return PreAuthManager session object which can be used for method invokation
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
            throw new TTKException(exp, strAuthorizationError);
        }//end of catch
        return preAuthManager;
    }//end of getPreAuthManagerObject()

    /**
	 * Returns the ClaimManager session object for invoking methods on it.
	 * @return ClaimManager session object which can be used for method invocation
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
			throw new TTKException(exp, strAuthorizationError);
		}//end of catch
		return claimManager;
	}//end of getClaimManagerObject()
	
	/**
	 * Returns the CommunicationManager session object for invoking methods on it.
	 * @return CommunicationManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private CommunicationManager getCommunicationManagerObject() throws TTKException
	{
		CommunicationManager communicationManager = null;
		try
		{
			if(communicationManager == null)
			{
				InitialContext ctx = new InitialContext();
				communicationManager = (CommunicationManager) ctx.lookup("java:global/TTKServices/business.ejb3/CommunicationManagerBean!com.ttk.business.common.messageservices.CommunicationManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strAuthorizationError);
		}//end of catch
		return communicationManager;
	}//end of getCommunicationManagerObject()

    private void formValue(AuthorizationVO authorizationVO, DynaActionForm frmAuthorizationDetails,
    										HttpServletRequest request) throws TTKException
    {
    	String strLink=TTKCommon.getActiveLink(request);
    	authorizationVO.setMemberSeqID(PreAuthWebBoardHelper.getMemberSeqId(request));
    	BigDecimal bdTotalAmt=new BigDecimal(0.00);
    	BigDecimal bdTotalAmt1=new BigDecimal(0.00);
    	BigDecimal bdPreAuthCoPay=new BigDecimal(0.00);
    	BigDecimal bdPreAuthDiscAmt=new BigDecimal(0.00);
    	BigDecimal bdPreAuthFinalAppr=new BigDecimal(0.00);
    	BigDecimal bdPreAuthTariffAppAmt=new BigDecimal(0.00);
        //MODIFICATION added as per KOC 1216B CHANGE REQUEST
    	BigDecimal bdCopayBufferamount =new BigDecimal(0.00);
    	BigDecimal bdCopayAmount =new BigDecimal(0.00);
    	BigDecimal bdTotalCopayAmount =new BigDecimal(0.00);
    	bdCopayAmount=authorizationVO.getCopayAmount();
    	bdCopayBufferamount=authorizationVO.getCopayBufferamount();
    	//added for Hyundai buffer
    	
    	
    	
    	
    	if(authorizationVO.getCopayAmount().compareTo(TTKCommon.getBigDecimal("0"))==0 && authorizationVO.getCopayBufferamount().compareTo(TTKCommon.getBigDecimal("0"))==0 )
    	{
    		frmAuthorizationDetails.set("totcopayAmount","0.00");
    	}//end of if(authorizationVO.getTotalSumInsured().compareTo(TTKCommon.getBigDecimal("0"))==0)
    	
    	else{
    		bdTotalCopayAmount=bdTotalCopayAmount.add(bdCopayAmount).add(bdCopayBufferamount);
    		
    	
    		frmAuthorizationDetails.set("totcopayAmount",bdTotalCopayAmount.toString());
    		
    	}

    	//MODIFICATION added as per KOC 1216B CHANGE REQUEST
		//Changes as per KOC 1142 AND 1140 CHANGE REQUEST
		if(authorizationVO.getAvaRestrictedSIAmt().compareTo(TTKCommon.getBigDecimal("0"))==0){
			frmAuthorizationDetails.set("avaRestrictedSIAmt","0.00");
		}
		if(authorizationVO.getMemberRestrictedSIAmt().compareTo(TTKCommon.getBigDecimal("0"))==0){
			frmAuthorizationDetails.set("memberRestrictedSIAmt","0.00");
		}
		//Changes as per KOC 1142 AND 1140 CHANGE REQUEST    	
		bdPreAuthCoPay=authorizationVO.getPACopayAmt();
		bdPreAuthDiscAmt=authorizationVO.getPADiscountAmt();
		bdPreAuthFinalAppr=authorizationVO.getPAApprovedAmt();
    	if(authorizationVO.getTotalSumInsured().compareTo(TTKCommon.getBigDecimal("0"))==0)
    	{
    		frmAuthorizationDetails.set("totalSumInsured","0.00");
    	}//end of if(authorizationVO.getTotalSumInsured().compareTo(TTKCommon.getBigDecimal("0"))==0)
    	if(authorizationVO.getAvailSumInsured().compareTo(TTKCommon.getBigDecimal("0"))==0)
    	{
    		frmAuthorizationDetails.set("availSumInsured","0.00");
    	}//end of if(authorizationVO.getAvailSumInsured().compareTo(TTKCommon.getBigDecimal("0"))==0)
    	if(authorizationVO.getAvailCumBonus().compareTo(TTKCommon.getBigDecimal("0"))==0)
    	{
    		frmAuthorizationDetails.set("availCumBonus","0.00");
    	}//end of if(authorizationVO.getAvailCumBonus().compareTo(TTKCommon.getBigDecimal("0"))==0)
    	if(authorizationVO.getRequestedAmount().compareTo(TTKCommon.getBigDecimal("0"))==0)
    	{
    		frmAuthorizationDetails.set("requestedAmount","0.00");
    	}//end of if(authorizationVO.getRequestedAmount().compareTo(TTKCommon.getBigDecimal("0"))==0)
    	if(authorizationVO.getApprovedBufferAmount()!=null)
    	{
    		if(authorizationVO.getApprovedBufferAmount().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		{
    			frmAuthorizationDetails.set("approvedBufferAmount","0.00");
    		}//end of if(authorizationVO.getApprovedBufferAmount().compareTo(TTKCommon.getBigDecimal("0"))==0)
    	}//end of if(authorizationVO.getApprovedBufferAmount()!=null)
    	if(authorizationVO.getMaxAllowedAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    	{
    		frmAuthorizationDetails.set("maxAllowedAmt","0.00");
    	}//end of if(authorizationVO.getMaxAllowedAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    	if(authorizationVO.getApprovedAmount().compareTo(TTKCommon.getBigDecimal("0"))==0)
    	{
    		frmAuthorizationDetails.set("approvedAmount","0.00");
    	}//end of if(authorizationVO.getApprovedAmount().compareTo(TTKCommon.getBigDecimal("0"))==0)
        if(strLink.equals(strClaims))
	{
    		if(authorizationVO.getPAApprovedAmt()!=null)
    		{
    			if(authorizationVO.getPAApprovedAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    			{
    				frmAuthorizationDetails.set("PAApprovedAmt","0.00");
    			}//end of if(authorizationVO.getPAApprovedAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		}//end of if(authorizationVO.getPAApprovedAmt()!=null)
    		if(authorizationVO.getTaxAmtPaid() !=null)
    		{
    			if(authorizationVO.getTaxAmtPaid().compareTo(TTKCommon.getBigDecimal("0"))==0)
    			{
    				frmAuthorizationDetails.set("taxAmtPaid","0.00");
    			}//end of if(authorizationVO.getTaxAmtPaid().compareTo(TTKCommon.getBigDecimal("0"))==0) 
    		}//end of if(authorizationVO.getTaxAmtPaid() !=null)
    		if(authorizationVO.getApprovedBufferAmount()!=null)
        	{
	    		if(authorizationVO.getAvailSumInsured()!= null)
	    		{
	            	bdTotalAmt=bdTotalAmt.add(authorizationVO.getAvailSumInsured()).
	            	add(authorizationVO.getAvailCumBonus()).add(authorizationVO.getApprovedBufferAmount());
	    		}//endo of if(authorizationVO.getAvailSumInsured()!= null)
        	}//end of if(authorizationVO.getApprovedBufferAmount()!=null)
    		if(authorizationVO.getClaimSubTypeID().equals("HCU")){
    			bdTotalAmt = authorizationVO.getApprovedAmount();
    		}//end of if(authorizationVO.getClaimSubTypeID().equals("HCU"))
    		//1216A  added this line(authorizationVO.getAdditionalDomicialaryYN().equalsIgnoreCase("Y"))
    		if(authorizationVO.getClaimSubTypeID().equals("OPD") && authorizationVO.getAdditionalDomicialaryYN().equalsIgnoreCase("Y"))
    		{
    			if(authorizationVO.getApprovedBufferAmount()!=null)
    			{
    				bdTotalAmt1=bdTotalAmt1.add(authorizationVO.getAvailDomTrtLimit()).add(authorizationVO.getApprovedBufferAmount());
    				bdTotalAmt=bdTotalAmt1;
    			}//end of if(authorizationVO.getApprovedBufferAmount()!=null)
    			else
    			{
    				bdTotalAmt=authorizationVO.getAvailDomTrtLimit();
    			}//end of else
    		}//end of if(authorizationVO.getClaimSubTypeID().equals("OPD"))
    		if(bdPreAuthCoPay!=null || bdPreAuthDiscAmt!=null || bdPreAuthFinalAppr!=null)
    		{
    			bdPreAuthTariffAppAmt=bdPreAuthTariffAppAmt.add(bdPreAuthCoPay).add(bdPreAuthDiscAmt).add(bdPreAuthFinalAppr);
    		}//end of if(bdPreAuthCoPay!=null || bdPreAuthDiscAmt!=null || bdPreAuthFinalAppr!=null)
    		frmAuthorizationDetails.set("PATariffAppAmt",bdPreAuthTariffAppAmt.toString());
    		if(authorizationVO.getAvailDomTrtLimit().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
        		frmAuthorizationDetails.set("availDomTrtLimit","0.00");
        	}//end of if(authorizationVO.getAvailDomTrtLimit().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		if(authorizationVO.getPACopayAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
        		frmAuthorizationDetails.set("PACopayAmt","0.00");
        	}//end of if(authorizationVO.getPACopayAmtt().compareTo(TTKCommon.getBigDecimal("0"))==0)
		//Added as per KOC 1216B Change Request 
    		
    		if(authorizationVO.getPreCopayBufferamount().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
        		frmAuthorizationDetails.set("preCopayBufferamount","0.00");
        	}//end of if(authorizationVO.getPreCopayBufferamount().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		//Added as per KOC 1216B Change Request 
    	}//end of if(strLink.equals(strClaims))
    	else       //in preAuth flow calculate Approval limit
    	{
    		if(authorizationVO.getMemberSeqID() == null || authorizationVO.getMemberSeqID() == 0){
    			if(authorizationVO.getAvailCumBonus()!=null &&(!( authorizationVO.getAvailCumBonus().compareTo(TTKCommon.getBigDecimal("0.00"))==0.00)))
    			{
            		bdTotalAmt=bdTotalAmt.add(authorizationVO.getAvailCumBonus());
    			}//end of if(authorizationVO.getAvailCumBonus()!=null &&(!( authorizationVO.getAvailCumBonus().compareTo(TTKCommon.getBigDecimal("0.00"))==0.00)))
            	if(authorizationVO.getAvailSumInsured()!= null &&(!( authorizationVO.getAvailSumInsured().compareTo(TTKCommon.getBigDecimal("0.00"))==0.00)))
            	{
            		bdTotalAmt=bdTotalAmt.add(authorizationVO.getAvailSumInsured());
            	}//end of if(authorizationVO.getAvailSumInsured()!= null &&(!( authorizationVO.getAvailSumInsured().compareTo(TTKCommon.getBigDecimal("0.00"))==0.00)))
            	if(authorizationVO.getAvailSumInsured().compareTo(TTKCommon.getBigDecimal("0.00"))==0.00 && authorizationVO.getAvailCumBonus().compareTo(TTKCommon.getBigDecimal("0.00"))==0.00)
            	{
            		bdTotalAmt = authorizationVO.getApprovedAmount();
            	}//end of if(authorizationVO.getAvailSumInsured().compareTo(TTKCommon.getBigDecimal("0.00"))==0.00 && authorizationVO.getAvailCumBonus().compareTo(TTKCommon.getBigDecimal("0.00"))==0.00)
	    	 }//end of if(authorizationVO.getMemberSeqID() == null)
    		else {
    			if(authorizationVO.getPrevApprovedAmount().compareTo(TTKCommon.getBigDecimal("0"))==0)
    			{
    				frmAuthorizationDetails.set("prevApprovedAmount","0.00");
    			}//end of if(authorizationVO.getPrevApprovedAmount().compareTo(TTKCommon.getBigDecimal("0"))==0)
    			if(authorizationVO.getAvailSumInsured()!= null)
    			{
    				bdTotalAmt=bdTotalAmt.add(authorizationVO.getAvailSumInsured());
    			}//end of if(authorizationVO.getAvailSumInsured()!= null)
    			if(authorizationVO.getAvailCumBonus()!=null)
    			{
    				bdTotalAmt=bdTotalAmt.add(authorizationVO.getAvailCumBonus());
    			}//end of if(authorizationVO.getAvailCumBonus()!=null)
    			if(authorizationVO.getApprovedBufferAmount()!=null)
    			{
    				bdTotalAmt=bdTotalAmt.add(authorizationVO.getApprovedBufferAmount());
    			}//end of if(authorizationVO.getApprovedBufferAmount()!=null)
    		}//end of else
       }//end of else
    	frmAuthorizationDetails.set("totalAmount",bdTotalAmt.toString());
    }// end of formValue(AuthorizationVO authorizationVO, DynaActionForm frmAuthorizationDetails)
    
    /**
	 * This method is used to view the balance si details.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
    public ActionForward doViewBalanceSI(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception {
    	try
    	{
    		setLinks(request);
    		log.debug("inside AuthorizationDetailsAction doViewBalanceSI");
    		DynaActionForm frmSIAuthorizationDetails= (DynaActionForm)form;
    		DynaActionForm frmBalanceSIInfo = (DynaActionForm)form;
    		
    		PreAuthManager preAuthObject=this.getPreAuthManagerObject();
    		ArrayList<Object> alAuthorization = new ArrayList<Object>();
    		StringBuffer strCaption= new StringBuffer();    		
    		String strLink=TTKCommon.getActiveLink(request);
    		String strForwarPath="";
    		String strClaimantname="";    	
    		String strPreauthORClaimNo="";
    		String strEnrollmentID="";
    		
    		if(strLink.equals(strClaims))
    		{
    			alAuthorization.add(ClaimsWebBoardHelper.getMemberSeqId(request));
    			alAuthorization.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
    			alAuthorization.add(ClaimsWebBoardHelper.getClmEnrollDetailSeqId(request));
    			alAuthorization.add(TTKCommon.getUserSeqId(request));
    		}//end of if(strLink.equals(strClaims))
    		else
    		{
    			alAuthorization.add(PreAuthWebBoardHelper.getMemberSeqId(request));
    			alAuthorization.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
    			alAuthorization.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
    			alAuthorization.add(TTKCommon.getUserSeqId(request));
    		}//end of else  
    		
    		AuthorizationVO authorizationVO=preAuthObject.getAuthorizationDetail(alAuthorization,TTKCommon.getActiveLink(request));
    		SIInfoVO objSIInfoVO = preAuthObject.getBalanceSIDetail(authorizationVO.getMemberSeqID(), authorizationVO.getPolicyGrpSeqID(), authorizationVO.getBalanceSeqID());
			BalanceCopayDeductionVO balCopayDeducVO=null;
    		//Changes as Per Koc 1142 (Copay restriction) Change request
    		if(TTKCommon.getActiveLink(request).equalsIgnoreCase("Pre-Authorization"))
    		{
    		 balCopayDeducVO = preAuthObject.getcopayAdviced("Pre-Authorization",PreAuthWebBoardHelper.getPreAuthSeqId(request),authorizationVO.getMemberSeqID(), authorizationVO.getPolicyGrpSeqID(), authorizationVO.getBalanceSeqID());
    		
    		// 
    		// 
    		
    		}
    		else{
    			balCopayDeducVO = preAuthObject.getcopayAdviced("Claims",ClaimsWebBoardHelper.getClaimsSeqId(request),authorizationVO.getMemberSeqID(), authorizationVO.getPolicyGrpSeqID(), authorizationVO.getBalanceSeqID());
        		
        		
    			}
    		//Changes as Per Koc 1142 (Copay restriction) Change request

    		objSIInfoVO.setBalCopayDeducVO(balCopayDeducVO);
    		frmSIAuthorizationDetails= (DynaActionForm)FormUtils.setFormValues("frmSIAuthorizationDetails", objSIInfoVO, this, mapping, request);
    		
    		if(TTKCommon.getActiveLink(request).equalsIgnoreCase("Pre-Authorization"))
    		{
    			strForwarPath="preauthbalancesi";
    		}else
    		{
    			strForwarPath="claimbalancesi";
    		}
    		
    		if(strLink.equals(strPreAuthorization))
    		{
    			if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
    					PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
    			{
    				strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
    			}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
    		}//end of if(strLink.equals(strPreAuthorization))
    		else if(strLink.equals(strClaims))
    		{
    			if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
    			{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
    			}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)    			
    		}// end of else if(strLink.equals(strClaims))
    		if(strLink.equals(strClaims))
    		{
    			strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
    			strPreauthORClaimNo=ClaimsWebBoardHelper.getWebBoardDesc(request);
    			strCaption.append("[").append(strClaimantname).append("]").append("[").append(strPreauthORClaimNo).append("]").append(strEnrollmentID);
    		}//end of if(strLink.equals(strClaims))
    		else
    		{
    			strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
    			strPreauthORClaimNo=PreAuthWebBoardHelper.getWebBoardDesc(request);
    			strCaption.append("[").append(strClaimantname).append("]").append("[").append(strPreauthORClaimNo).append("]").append(strEnrollmentID);
    		}//end of else
    		frmSIAuthorizationDetails.set("caption",String.valueOf(strCaption));
    		
    		//setting balanceSIInfoVO info
    		frmBalanceSIInfo.initialize(mapping);
    		frmSIAuthorizationDetails.set("balanceSIInfoVO", FormUtils.setFormValues("frmBalanceSIInfo",objSIInfoVO.getBalSIInfoVO(),this,mapping,request));
    		frmBalanceSIInfo = (DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO");
    		if("".equals(TTKCommon.checkNull(objSIInfoVO.getBalSIInfoVO().getBufferAllocation())))
        	{						  
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("bufferAllocation", "0.00");
            }//end of if("".equals(TTKCommon.checkNull(objSIInfoVO.getBalSIInfoVO().getBufferAllocation())))
            
            //<!-- added for koc1289_1272  -->
    		if("".equals(TTKCommon.checkNull(objSIInfoVO.getRestorationPreauthClaimVO().getRestSumInsured())))
        	{						  
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("restorationPreauthClaimVO")).set("restSumInsured", "0.00");
        	}//end of if("".equals(TTKCommon.checkNull(objSIInfoVO.getBalSIInfoVO().getBufferAllocation())))
    		
    		//<!-- end added for koc1289_1272  -->

    		if(objSIInfoVO.getBalSIInfoVO().getTotalSumInsured().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("totalSumInsured","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getTotalSumInsured().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		if(objSIInfoVO.getBalSIInfoVO().getUtilizedSumInsured().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("utilizedSumInsured","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedSumInsured().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		if(objSIInfoVO.getBalSIInfoVO().getBonus().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("bonus","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getBonus().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		if(objSIInfoVO.getBalSIInfoVO().getUtilizedBonus().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("utilizedBonus","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBonus().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		if(objSIInfoVO.getBalSIInfoVO().getBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("bufferAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		  //added for hyundai Buffer 
    		//set2
    		
    		if(objSIInfoVO.getBalSIInfoVO().getNorMedicalBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    			
        	{
    			log.info("yes here it is coming"+objSIInfoVO.getBalSIInfoVO().getNorMedicalBufAmt());
    			
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("norMedicalBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		
    		if(objSIInfoVO.getBalSIInfoVO().getUtilizedNorMedicalBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("utilizedNorMedicalBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		
    		
    		//set3
    		if(objSIInfoVO.getBalSIInfoVO().getCriCorpusBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("criCorpusBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		if(objSIInfoVO.getBalSIInfoVO().getUtilizedCriCorpusBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("utilizedCriCorpusBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    //set4
    		if(objSIInfoVO.getBalSIInfoVO().getCriMedicalBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("criMedicalBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		if(objSIInfoVO.getBalSIInfoVO().getUtilizedCriMedicalBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("utilizedCriMedicalBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    
    	  //set5
    		if(objSIInfoVO.getBalSIInfoVO().getCriIllnessBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("criIllnessBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		if(objSIInfoVO.getBalSIInfoVO().getUtilizedCriIllnessBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("utilizedCriIllnessBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    
    	 
    		//end added for hyundai Buffer 
    		
    		
    		
             //added for hyundai Buffer 
    		//set1
    		if(objSIInfoVO.getBalSIInfoVO().getNorCorpusBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("norCorpusBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("utilizedBufferAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		
    	/*if(objSIInfoVO.getBalSIInfoVO().getUtilizedNorCorpusBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balanceSIInfoVO")).set("utilizedNorCorpusBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		
    		*/
    		frmSIAuthorizationDetails.set("memberBufferVO", FormUtils.setFormValues("frmMemberBuffer",objSIInfoVO.getMemberBufferVO(),this,mapping,request));
        	
        	frmSIAuthorizationDetails.set("stopPreClmVO", FormUtils.setFormValues("frmStopPreClaim",objSIInfoVO.getStopPreClmVO(),this,mapping,request));
            //log.info("stop stop stop stop :"+objSIInfoVO.getStopPreClmVO().getStopPatClmYN());
            //<!-- added for koc1289_1272  -->
        	frmSIAuthorizationDetails.set("restorationPreauthClaimVO", FormUtils.setFormValues("frmMemberRestoration",objSIInfoVO.getRestorationPreauthClaimVO(),this,mapping,request));
                       
    		request.getSession().setAttribute("frmSIAuthorizationDetails",frmSIAuthorizationDetails);
		//   Changes as per akOC 1216 Change Request(IBM) 
    		
    	
    		
    		
    		if(objSIInfoVO.getMemberBufferVO().getBufferAmtMember().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("memberBufferVO")).set("bufferAmtMember","0.00");
        	}//end of if(objSIInfoVO.getMemberBufferVO().getBufferAmtMember().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	if(objSIInfoVO.getMemberBufferVO().getUtilizedBufferAmtMember().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("memberBufferVO")).set("utilizedBufferAmtMember","0.00");
        	}//end of if(objSIInfoVO.getMemberBufferVO().getUtilizedBufferAmtMember().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		
        	  //added for hyundai Buffer 
    		//set2
    		
    		if(objSIInfoVO.getMemberBufferVO().getNorMedicalBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    			
        	{
    			log.info("yes here it is coming"+objSIInfoVO.getMemberBufferVO().getNorMedicalBufAmt());
    			
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("memberBufferVO")).set("norMedicalBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		
    		if(objSIInfoVO.getMemberBufferVO().getUtilizedNorMedicalBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("memberBufferVO")).set("utilizedNorMedicalBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		
    		
    		//set3
    		if(objSIInfoVO.getMemberBufferVO().getCriCorpusBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("memberBufferVO")).set("criCorpusBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		if(objSIInfoVO.getMemberBufferVO().getUtilizedCriCorpusBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("memberBufferVO")).set("utilizedCriCorpusBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    //set4
    		if(objSIInfoVO.getMemberBufferVO().getCriMedicalBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("memberBufferVO")).set("criMedicalBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		if(objSIInfoVO.getMemberBufferVO().getUtilizedCriMedicalBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("memberBufferVO")).set("utilizedCriMedicalBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    
    	  //set5
    		if(objSIInfoVO.getMemberBufferVO().getCriIllnessBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("memberBufferVO")).set("criIllnessBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		if(objSIInfoVO.getMemberBufferVO().getUtilizedCriIllnessBufAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("memberBufferVO")).set("utilizedCriIllnessBufAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getUtilizedBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
    
    	 
    		//end added for hyundai Buffer 
        	
    	
        	//   Changes as per akOC 1216 Change Request(IBM) 
    	//Modifications Changes as per KOC 1142 (Copay restriction)
        	frmSIAuthorizationDetails.set("balCopayDeducVO", FormUtils.setFormValues("frmCopayDeduction",objSIInfoVO.getBalCopayDeducVO(),this,mapping,request));
        	if(objSIInfoVO.getBalCopayDeducVO().getBdMaxCopayAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balCopayDeducVO")).set("bdMaxCopayAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	if(objSIInfoVO.getBalCopayDeducVO().getBdApprovedAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	{
    			((DynaActionForm) ((DynaActionForm) frmSIAuthorizationDetails).get("balCopayDeducVO")).set("bdApprovedAmt","0.00");
        	}//end of if(objSIInfoVO.getBalSIInfoVO().getBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
        	
        	
        	
        	//Modifications Changes as per KOC 1142(Copay restriction)
        	
        	log.info("stop stop stop stop :"+objSIInfoVO.getStopPreClmVO().getStopPatClmYN());
    		request.getSession().setAttribute("frmSIAuthorizationDetails",frmSIAuthorizationDetails);
    	    TableData  tableData =TTKCommon.getTableData(request);
    		if(objSIInfoVO.getSIBreakupList().isEmpty())
    		{
    			tableData.createTableInfo("SIBreakUpTable",new ArrayList<Object>());
    		}
    		else
    		{
    			tableData.createTableInfo("SIBreakUpTable",objSIInfoVO.getSIBreakupList());
    		}
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);			
    		return this.getForward(strForwarPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAuthorizationError));
    	}//end of catch(Exception exp)
    }//end of doViewBalanceSI(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    /**
	 * This method is used to calculate the tax amt paid.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
    
    public ActionForward doServTaxCal(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	try
    	{
    		setLinks(request);
    		log.debug("Inside AuthorizationDetailsAction doServTaxCal method");
    		DynaActionForm frmAuthorizationDetails= (DynaActionForm)form;
    		AuthorizationVO authorizationVO=null;
    		StringBuffer strCaption= new StringBuffer();
    		String strLink=TTKCommon.getActiveLink(request);
    		String strClaimantname=""; 
    		String strEnrollmentID="";
    		Object[] objArrayResult = new Object[3];
    		ClaimManager claimObject=this.getClaimManagerObject();
    		authorizationVO = (AuthorizationVO)FormUtils.getFormValues(frmAuthorizationDetails, this,
					mapping, request);
    		objArrayResult = claimObject.getServTaxCal(authorizationVO);
    		 if(strLink.equals(strClaims))
    		{
    			if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
    			{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
    			}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
    			
    			strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
    			strCaption.append("Settlement Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
    		}// end of  if(strLink.equals(strClaims))
    		
    		if(objArrayResult.length > 0)
    		{
    			authorizationVO.setTaxAmtPaid((BigDecimal)objArrayResult[0]);
    			authorizationVO.setFinalApprovedAmt((BigDecimal)objArrayResult[1]);
    			authorizationVO.setSerTaxCalPer((BigDecimal)objArrayResult[2]);
    			frmAuthorizationDetails= (DynaActionForm)FormUtils.setFormValues("frmAuthorizationDetails",
    					authorizationVO, this, mapping, request);
    			frmAuthorizationDetails.set("taxAmtPaid",(String)authorizationVO.getTaxAmtPaid().toString() );
    			frmAuthorizationDetails.set("caption",String.valueOf(strCaption));
    			frmAuthorizationDetails.set("finalApprovedAmt",(String)authorizationVO.getFinalApprovedAmt().toString());
    			frmAuthorizationDetails.set("status","N");
    		}//end of if(objArrayResult.length > 0)
    		this.formValue(authorizationVO,frmAuthorizationDetails,request);
    		frmAuthorizationDetails.set("taxAmtPaid", (String)authorizationVO.getTaxAmtPaid().toString());
    		request.getSession().setAttribute("frmAuthorizationDetails",frmAuthorizationDetails);
    		return this.getForward(strClaimsSettlement, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAuthorizationError));
    	}//end of catch(Exception exp)
    	
    	
    }// end of doServTaxCal(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	 //  HttpServletResponse response)
	    /** bajaj
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
    public ActionForward doIntimation(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    															HttpServletResponse response) throws Exception {
    	try
    	{
    		setLinks(request);
    		log.debug("inside AuthorizationDetailsAction doSave");
    		AuthorizationVO authorizationVO=null;
    		String strForward="";
    		String strClaimantname="";
    		String strEnrollmentID="";
    		String strLink=TTKCommon.getActiveLink(request);
    		DynaActionForm frmAuthorizationDetails= (DynaActionForm)request.getSession().getAttribute("frmAuthorizationDetails");
    		String strTaxAmtPaid ="";
    		String strFinalApprAmt ="";
    		ArrayList<Object> alAuthorization = new ArrayList<Object>();
    		StringBuffer strCaption= new StringBuffer();
    		Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");
    		PreAuthManager preAuthObject=this.getPreAuthManagerObject();
    		
    		if(strLink.equals(strPreAuthorization))
    		{
    			if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
    			{
    				TTKException expTTK = new TTKException();
    				expTTK.setMessage("error.PreAuthorization.required");
    				throw expTTK;
    			}//end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
    		}//end of if(strLink.equals(strPreAuthorization))
    		else if(strLink.equals(strClaims))
    		{
    			if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
    			{
    				TTKException expTTK = new TTKException();
    				expTTK.setMessage("error.Claims.required");
    				throw expTTK;
    			}//end of if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
    		}//end of  else if(strLink.equals(strClaims))
    		if(strLink.equals(strPreAuthorization))
    		{
    			if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
    			{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
    			}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
    			strForward=strAuthorization;
    		}//end of if(strLink.equals(strPreAuthorization))
    		else if(strLink.equals(strClaims))
    		{
    			if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
    			{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
    			}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
    			strForward=strClaimsSettlement;
    		}// end of else if(strLink.equals(strClaims))
    		if(strLink.equals(strClaims))
    		{
    			strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
    			strCaption.append("Settlement Details -").append("[").append(strClaimantname).append("]")
    																			.append(strEnrollmentID);
    		}//end of if(strLink.equals(strClaims))
    		else
    		{
    			strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
    			strCaption.append("Authorization Details -").append("[").append(strClaimantname).append("]")
    																				.append(strEnrollmentID);
    		}//end of else
    		authorizationVO=new AuthorizationVO();
    		int iUpdate=0;
    		if(strLink.equals(strClaims))
    		{
    			alAuthorization.add(ClaimsWebBoardHelper.getMemberSeqId(request));
    			alAuthorization.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
    			alAuthorization.add(ClaimsWebBoardHelper.getClmEnrollDetailSeqId(request));
    			alAuthorization.add(TTKCommon.getUserSeqId(request));
    			authorizationVO = (AuthorizationVO)FormUtils.getFormValues(frmAuthorizationDetails, this,
    																					mapping, request);
    			authorizationVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
    			authorizationVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
    			//authorizationVO.setApprovedAmount(TTKCommon.getBigDecimal(frmAuthorizationDetails.getString("approvedAmount")));
    			strTaxAmtPaid = frmAuthorizationDetails.getString("taxAmtPaid");
    			strFinalApprAmt=frmAuthorizationDetails.getString("finalApprovedAmt");
    			authorizationVO.setTaxAmtPaid(TTKCommon.getBigDecimal(frmAuthorizationDetails.getString("taxAmtPaid")));
    		}//end of  if(strLink.equals(strClaims))
    		else
    		{
    			alAuthorization.add(PreAuthWebBoardHelper.getMemberSeqId(request));
    			alAuthorization.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
    			alAuthorization.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
    			alAuthorization.add(TTKCommon.getUserSeqId(request));
    			authorizationVO = (AuthorizationVO)FormUtils.getFormValues(frmAuthorizationDetails, this,
    																					mapping, request);
    			authorizationVO.setPreAuthSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
    			authorizationVO.setEnrollDtlSeqID(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
    			authorizationVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
    		}//end of else
    		if(!authorizationVO.getStatusTypeID().equals("PCO")&&!authorizationVO.getStatusTypeID().equals("REJ")&&
    			!authorizationVO.getStatusTypeID().equals("PCN")&& authorizationVO.getStatusTypeID().equals("APR")&&
    			!(authorizationVO.getApprovedAmount().compareTo(authorizationVO.getRequestedAmount())==-1))
    		{
    			authorizationVO.setReasonTypeID("");
    		}//end of if
    		preAuthObject.sendInsIntimate(authorizationVO,TTKCommon.getActiveLink(request));
    		
    			//request.setAttribute("updated","message.savedSuccessfully");
    			authorizationVO=preAuthObject.getAuthorizationDetail(alAuthorization,TTKCommon.getActiveLink(request));
    			authorizationVO.setApprovedAmount(TTKCommon.getBigDecimal(frmAuthorizationDetails.getString("approvedAmount")));
    			if(strLink.equals(strPreAuthorization)){
        			authorizationVO.setEnrollDtlSeqID(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
				}//end of if(strLink.equals(strPreAuthorization))
    			
    			//authorizationVO.setEnrollDtlSeqID(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
    			authorizationVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
    			frmAuthorizationDetails= (DynaActionForm)FormUtils.setFormValues("frmAuthorizationDetails",
    																		authorizationVO, this, mapping, request);
    			//frmAuthorizationDetails.set("taxAmtPaid", strTaxAmtPaid);
    			//frmAuthorizationDetails.set("finalApprovedAmt",strFinalApprAmt);
    			toolBar.getConflictIcon().setVisible(toolBar.getConflictIcon().isVisible() &&
    													authorizationVO.getDiscPresentYN().equals("Y"));
    	
    		toolBar.getConflictIcon().setVisible(toolBar.getConflictIcon().isVisible() &&
    														authorizationVO.getDiscPresentYN().equals("Y"));
    		frmAuthorizationDetails.set("status","N");
    		if((authorizationVO.getStatusTypeID().equals("REJ"))||(authorizationVO.getStatusTypeID().equals("APR"))||(authorizationVO.getStatusTypeID().equals("PCO")))
    		{
    			frmAuthorizationDetails.set("preauthstatus","Y");
    		}//end of if
    		/*if(((authorizationVO.getStatusTypeID().equals("REJ"))||(authorizationVO.getStatusTypeID().equals("APR")))&&(authorizationVO.getStatusTypeID().equals("INP")))
    		{
    			frmAuthorizationDetails.set("preauthstatus","N");
    		}//end of if((authorizationVO.getStatusTypeID().equals("REJ"))||
*/    		else
    		{
    			frmAuthorizationDetails.set("preauthstatus","N");
    		}//end of else
    		if(authorizationVO.getApprovedAmount()!= null)
    		{
    			if((authorizationVO.getStatusTypeID().equals("PCO"))||(authorizationVO.getStatusTypeID().equals("REJ"))
    			 ||(authorizationVO.getStatusTypeID().equals("PCN"))||((authorizationVO.getStatusTypeID().equals("APR"))
    			  &&(authorizationVO.getApprovedAmount().compareTo(authorizationVO.getRequestedAmount())==-1)))
    			{
    				frmAuthorizationDetails.set("status","Y");
    			}//end of if
    			else
    			{
    				frmAuthorizationDetails.set("status","N");
    			}//end of else
    		}//end of if(authorizationVO.getApprovedAmount()!= null)
    		frmAuthorizationDetails.set("caption",String.valueOf(strCaption));
    		this.formValue(authorizationVO,frmAuthorizationDetails,request);
    		request.getSession().setAttribute("frmAuthorizationDetails",frmAuthorizationDetails);
    		return this.getForward(strForward, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAuthorizationError));
    	}//end of catch(Exception exp)
    }//end of doIntimation(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

}//end of AuthorizationDetailsAction