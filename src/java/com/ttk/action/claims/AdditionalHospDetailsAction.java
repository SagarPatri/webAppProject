/**
 * @ (#) AdditionalHospDetailsAction.java July 15,2006
 * Project       : TTK HealthCare Services
 * File          : AdditionalHospDetailsAction.java
 * Author        : Harsha Vardhan B N
 * Company       : Span Systems Corporation
 * Date Created  : July 15,2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.claims;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import com.ttk.action.TTKAction;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.claims.AdditionalHospitalDetailVO;
import com.ttk.dto.preauth.PreAuthMedicalVO;
import formdef.plugin.util.FormUtils;

public class AdditionalHospDetailsAction extends TTKAction {

	private static Logger log = Logger.getLogger( AdditionalHospDetailsAction.class );

	//  Action mapping forwards.
	private static final String strMedicalDetails="medicaldetails";
	private static final String strAdditionalHospDetails="additionalhospdetails";

	//Exception Message Identifier
	private static final String strClaimAdditionalHospDetail="additionalhospdetail";

	/**
	 * This method is used to get the current record.
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
			log.debug("Inside AdditionalHospDetailsAction doDefault");
			setLinks(request);
			DynaActionForm frmAdditionalHospDetails = (DynaActionForm)form;
			AdditionalHospitalDetailVO additionalHospDetailVO=null;
			StringBuffer strCaption= new StringBuffer();
			strCaption.append(" - [ ").append(ClaimsWebBoardHelper.getClaimantName(request)).
									append(" ]").append(" [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			{
				strCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
			}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			String strAddHospDtlSeqID = null;
			Long lngHospAssocSeqID = null;
			String strEditYN="";
			PreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			PreAuthMedicalVO preAuthMedicalVO=preAuthManagerObject.getClaimMedicalDetail(
					ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
			AdditionalHospitalDetailVO additionalHospitalDetailVO=preAuthMedicalVO.getAddHospitalDetailVO();
			if((additionalHospitalDetailVO)!= null)
			{
				lngHospAssocSeqID =  additionalHospitalDetailVO.getHospAssocSeqID();
			}//end of if((additionalHospitalDetailVO)!= null)
			if((additionalHospitalDetailVO)!= null )
			{
				strAddHospDtlSeqID = additionalHospitalDetailVO.getAddHospDtlSeqID().toString();
				strEditYN = additionalHospitalDetailVO.getEditYN().toString();
			}//end of if((additionalHospitalDetailVO)!= null )
			
			frmAdditionalHospDetails.set("addHospDtlSeqID",strAddHospDtlSeqID.toString());
			
			if(strAddHospDtlSeqID != null)
			{
				additionalHospDetailVO = preAuthManagerObject.getAdditionalHospitalDetail(
											lngHospAssocSeqID, TTKCommon.getUserSeqId(request));
			}//end of if(strAddHospDtlSeqID != null)
			else																  //     on click of add button
			{
				additionalHospDetailVO = new AdditionalHospitalDetailVO();
			}//end of else
			
			frmAdditionalHospDetails = (DynaActionForm)FormUtils.setFormValues("frmAdditionalHospDetails",
											additionalHospDetailVO, this, mapping, request);
			frmAdditionalHospDetails.set("caption",strCaption.toString());
			frmAdditionalHospDetails.set("EditYN",strEditYN);
			request.getSession().setAttribute("frmAdditionalHospDetails",frmAdditionalHospDetails);
			return this.getForward(strAdditionalHospDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimAdditionalHospDetail));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside AdditionalHospDetailsAction doSave");
			setLinks(request);
			DynaActionForm frmAdditionalHospDetails = (DynaActionForm)form;
			AdditionalHospitalDetailVO additionalHospDetailVO=null;
			StringBuffer strCaption= new StringBuffer();
			strCaption.append(" - [ ").append(ClaimsWebBoardHelper.getClaimantName(request)).append(" ]").
												append(" [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			{
				strCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
			}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			String strAddHospDtlSeqID = null;
			Long lngHospAssocSeqID = null;
			String strEditYN="";
			PreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			PreAuthMedicalVO preAuthMedicalVO=preAuthManagerObject.getClaimMedicalDetail(
					ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
			AdditionalHospitalDetailVO additionalHospitalDetailVO=preAuthMedicalVO.getAddHospitalDetailVO();
			if((additionalHospitalDetailVO)!= null)
			{
				lngHospAssocSeqID =  additionalHospitalDetailVO.getHospAssocSeqID();
			}//end of if((additionalHospitalDetailVO)!= null)
			if((additionalHospitalDetailVO)!= null )
			{
				strAddHospDtlSeqID = additionalHospitalDetailVO.getAddHospDtlSeqID().toString();
				strEditYN = additionalHospitalDetailVO.getEditYN().toString();
			}//end of if((additionalHospitalDetailVO)!= null )
			frmAdditionalHospDetails.set("addHospDtlSeqID",strAddHospDtlSeqID.toString());
			//frmAdditionalHospDetails=(DynaActionForm)form;

			if(request.getParameter("fullyEquippedYN")==null)
			{
				frmAdditionalHospDetails.set("fullyEquippedYN","N");
			}//end of if(request.getParameter("fullyEquippedYN")==null)
			additionalHospDetailVO = (AdditionalHospitalDetailVO)FormUtils.getFormValues(frmAdditionalHospDetails,
																			this, mapping, request);
			additionalHospDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));	//User Id
			additionalHospDetailVO.setHospAssocSeqID(lngHospAssocSeqID);
			Long lngAddHospDetailSeqId=preAuthManagerObject.saveAdditionalHospitalDetail(additionalHospDetailVO);
			if(lngAddHospDetailSeqId>0)
			{
				if(additionalHospDetailVO.getAddHospDtlSeqID()!=null)
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if(additionalHospDetailVO.getAddHospDtlSeqID()!=null)
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
			}//end of if(lngAddHospDetailSeqId>0)
			additionalHospDetailVO = preAuthManagerObject.getAdditionalHospitalDetail(lngHospAssocSeqID,
																TTKCommon.getUserSeqId(request));
			frmAdditionalHospDetails = (DynaActionForm)FormUtils.setFormValues("frmAdditionalHospDetails",
														additionalHospDetailVO, this, mapping, request);
			frmAdditionalHospDetails.set("caption",strCaption.toString());
			frmAdditionalHospDetails.set("EditYN",strEditYN);
			request.getSession().setAttribute("frmAdditionalHospDetails",frmAdditionalHospDetails);
			return this.getForward(strAdditionalHospDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimAdditionalHospDetail));
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
			log.debug("Inside AdditionalHospDetailsAction doReset");
			setLinks(request);
			DynaActionForm frmAdditionalHospDetails = (DynaActionForm)form;
			AdditionalHospitalDetailVO additionalHospDetailVO=null;
			StringBuffer strCaption= new StringBuffer();
			strCaption.append(" - [ ").append(ClaimsWebBoardHelper.getClaimantName(request)).append(" ]").
												append(" [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			{
				strCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
			}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			String strAddHospDtlSeqID = null;
			Long lngHospAssocSeqID = null;
			String strEditYN="";
			PreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			PreAuthMedicalVO preAuthMedicalVO=preAuthManagerObject.getClaimMedicalDetail(
							ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
			AdditionalHospitalDetailVO additionalHospitalDetailVO=preAuthMedicalVO.getAddHospitalDetailVO();
			if((additionalHospitalDetailVO)!= null)
			{
				lngHospAssocSeqID =  additionalHospitalDetailVO.getHospAssocSeqID();
			}//end of if((additionalHospitalDetailVO)!= null)
			if((additionalHospitalDetailVO)!= null )
			{
				strAddHospDtlSeqID = additionalHospitalDetailVO.getAddHospDtlSeqID().toString();
				strEditYN = additionalHospitalDetailVO.getEditYN().toString();
			}//end of if((additionalHospitalDetailVO)!= null )
			frmAdditionalHospDetails.set("addHospDtlSeqID",strAddHospDtlSeqID.toString());
			if(frmAdditionalHospDetails.get("addHospDtlSeqID")!=null &&
														!frmAdditionalHospDetails.get("addHospDtlSeqID").equals(""))
			{
				additionalHospDetailVO = preAuthManagerObject.getAdditionalHospitalDetail(lngHospAssocSeqID,
											TTKCommon.getUserSeqId(request));
			}//end of if
			else
			{
				frmAdditionalHospDetails.initialize(mapping);
				additionalHospDetailVO = new AdditionalHospitalDetailVO();
			}//end of else
			frmAdditionalHospDetails = (DynaActionForm)FormUtils.setFormValues("frmAdditionalHospDetails",
											additionalHospDetailVO, this, mapping, request);
			frmAdditionalHospDetails.set("caption",strCaption.toString());
			frmAdditionalHospDetails.set("EditYN",strEditYN);
			request.getSession().setAttribute("frmAdditionalHospDetails",frmAdditionalHospDetails);
			return this.getForward(strAdditionalHospDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimAdditionalHospDetail));
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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
									HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside AdditionalHospDetailsAction doClose");
			setLinks(request);
			return mapping.findForward(strMedicalDetails);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimAdditionalHospDetail));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the PreAuthManager session object for invoking methods on it.
	 * @return PreAuthManager session object which can be used for method invocation
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
			throw new TTKException(exp, strClaimAdditionalHospDetail);
		}//end of catch
		return preAuthManager;
	}//end of getPreAuthManagerObject()
}//end of AdditionalHospDetailsAction