/**
 * @ (#) ClaimDetailsAction.java July 18, 2006
 * Project 	     : TTK HealthCare Services
 * File          : ClaimDetailsAction.java
 * Author        : Raghavendra T M
 * Company       : Span Systems Corporation
 * Date Created  : July 18, 2006
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.inwardentry;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.claims.ClaimManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.claims.ClaimInwardDetailVO;
import com.ttk.dto.claims.ClaimInwardVO;
import com.ttk.dto.claims.DocumentChecklistVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for displaying the details of the Claims.
 * This class also provides option for Adding and Updating the Claims.
 */
public class ClaimDetailsAction extends TTKAction  {
	
	private static Logger log = Logger.getLogger( ClaimDetailsAction.class );
	
	//Exception Message Identifier
	private static final String strInvestigationExp="Investigation";
	
	/**
	 * This method is used to initialize the screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the Default method of ClaimsDetailsAction");
			setLinks(request);
			TTKException expTTK = new TTKException();
			expTTK.setMessage("error.inwardno.required");
			throw expTTK;
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationExp));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to navigate to  detail screen to edit selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewClaimsDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doViewClaimsDetail method of ClaimsDetailsAction");
			setLinks(request);
			ClaimManager claimManagerObject=this.getClaimManagerObject();
			ClaimInwardVO claimInwardVO = null;
			ClaimInwardDetailVO claimInwardDetailVO = null;
			ArrayList alClaimDocumentList = null;
			TableData tableData=TTKCommon.getTableData(request);
			String strClaimType=null;
			//frmClaimDetails = (DynaActionForm)form;
			DynaActionForm frmClaimDetails1 = (DynaActionForm)form;
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				claimInwardVO = (ClaimInwardVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				strClaimType  = claimInwardVO.getClaimTypeID();
				
				if(claimInwardVO.getDocumentTypeID().equals("DTC")){
					claimInwardDetailVO= claimManagerObject.getClaimInwardDetail(claimInwardVO.getInwardSeqID(),strClaimType,TTKCommon.getUserSeqId(request));
				}//end of if(!claimInwardDetailVO.getDocumentTypeID().equals("DTC"))
				else{
					claimInwardDetailVO= claimManagerObject.getClaimInwardDetail(claimInwardVO.getInwardSeqID(),null,TTKCommon.getUserSeqId(request));
				}//end of else
				
				alClaimDocumentList = claimInwardDetailVO.getClaimDocumentVOList();
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			frmClaimDetails1 = (DynaActionForm)FormUtils.setFormValues("frmClaimDetails",claimInwardDetailVO, this, mapping, request);
			frmClaimDetails1.set("claimantVO",FormUtils.setFormValues("frmClaimEnrollment",claimInwardDetailVO.getClaimantVO(), this, mapping, request));
			frmClaimDetails1.set("alClaimDocumentList",alClaimDocumentList);
			frmClaimDetails1.set("alPrevClaim",claimInwardDetailVO.getPrevClaimList());
			frmClaimDetails1.set("alShortfall",this.populateShortfallVO(claimInwardDetailVO,frmClaimDetails1));
			frmClaimDetails1.set("claimType",strClaimType);
			request.getSession().setAttribute("frmClaimDetails",frmClaimDetails1);
			return this.getForward("claimdetails", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationExp));
		}//end of catch(Exception exp)
	}//end of doViewClaimsDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is called from the struts framework.
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
	public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doAdd method of ClaimsDetailsAction");
			setLinks(request);
			ClaimInwardDetailVO claimInwardDetailVO = null;
			ClaimantVO claimantVO = null;
			ArrayList alClaimDocumentList = null;
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			DynaActionForm frmClaimDetails1 = (DynaActionForm)form;
			frmClaimDetails1 = (DynaActionForm)form;
			frmClaimDetails1.initialize(mapping);
			//ClaimInwardVO claimInwardVO = new ClaimInwardVO();
			claimInwardDetailVO = new ClaimInwardDetailVO();
			claimantVO = new ClaimantVO();
			claimInwardDetailVO.setClaimantVO(claimantVO);
			if(userSecurityProfile.getBranchID()!=null)
			{
				claimInwardDetailVO.setOfficeSeqID(userSecurityProfile.getBranchID());
			}//end of if(userSecurityProfile.getBranchID()!=null)
			else
			{
				frmClaimDetails1.set("officeSeqID", "");
			}//end of else
			frmClaimDetails1.set("officeName",userSecurityProfile.getBranchName());
			frmClaimDetails1 = (DynaActionForm)FormUtils.setFormValues("frmClaimDetails",claimInwardDetailVO, this, mapping, request);
			frmClaimDetails1.set("claimantVO",FormUtils.setFormValues("frmClaimEnrollment",claimInwardDetailVO.getClaimantVO(), this, mapping, request));
			frmClaimDetails1.set("alClaimDocumentList",alClaimDocumentList);
			frmClaimDetails1.set("alPrevClaim",claimInwardDetailVO.getPrevClaimList());
			//frmClaimDetails1.set("alShortfall",this.populateShortfallVO(claimInwardDetailVO));
			//ArrayList alShortfall=this.populateShortfallVO(claimInwardDetailVO);
			frmClaimDetails1.set("alShortfall",this.populateShortfallVO(claimInwardDetailVO,frmClaimDetails1));
			frmClaimDetails1.set("claimType","");
			request.getSession().setAttribute("frmClaimDetails",frmClaimDetails1);
			return this.getForward("claimdetails", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationExp));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSave method of ClaimsDetailsAction");
			setLinks(request);
			ClaimManager claimManagerObject=this.getClaimManagerObject();
			//ClaimInwardVO claimInwardVO = null;
			ClaimInwardDetailVO claimInwardDetailVO = null;
			ClaimantVO claimantVO = null;
			DynaActionForm  frmClaimDetails = (DynaActionForm)form;
			ArrayList<Object> alClaimDocumentList = null;
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			long lngResult = 0;
			frmClaimDetails = (DynaActionForm)form;
			ArrayList alPrevClaim = (ArrayList)frmClaimDetails.get("alPrevClaim");
			ArrayList alShortfallVO = (ArrayList)frmClaimDetails.get("alShortfall");
			DocumentChecklistVO documentChecklistVO =null;
			//String strDocumentType = frmClaimDetails.getString("documentTypeID");
			String strsourceType = frmClaimDetails.getString("sourceTypeID");
			alClaimDocumentList = new ArrayList<Object>();
			if((TTKCommon.checkNull(strsourceType).equals("CPN")))
			{
				String[] strSelectDocumentListType = (String[])frmClaimDetails.get("SelectDocumentListType");
				String[] strSelectDocumentRcvdSeqID = (String[])frmClaimDetails.get("SelectDocumentRcvdSeqID");
				String[] strSheets = request.getParameterValues("sheets");
				String[] strDocumentTypeID = request.getParameterValues("DocTypeID");
				String[] strReasonTypeID = request.getParameterValues("selectedReasonTypeID");
				String[] strRemarks = request.getParameterValues("gridRemarks");
				String[] strDocumentRcvdYN = request.getParameterValues("selectedChkopt");
				String[] strDocumentName = request.getParameterValues("selectDocumentName");
				for(int i=0 ;i<strSelectDocumentListType.length ;i++)
				{
					documentChecklistVO = new DocumentChecklistVO();
					if(strSelectDocumentRcvdSeqID[i]!=null && !strSelectDocumentRcvdSeqID[i].equals("")){
						documentChecklistVO.setDocumentRcvdSeqID(Long.parseLong(strSelectDocumentRcvdSeqID[i]));
					}//end of if(strSelectDocumentRcvdSeqID[i]!=null && !strSelectDocumentRcvdSeqID[i].equals(""))
					documentChecklistVO.setDocumentRcvdYN(strDocumentRcvdYN[i]);
					documentChecklistVO.setDocumentListType(strSelectDocumentListType[i]);
					documentChecklistVO.setSheetsCount((strSheets[i]));
					documentChecklistVO.setDocumentTypeID(strDocumentTypeID[i]);
					if(TTKCommon.checkNull(strReasonTypeID[i])!=null && !TTKCommon.checkNull(strReasonTypeID[i]).equals(""))
					{
						documentChecklistVO.setReasonTypeID(strReasonTypeID[i]);
					}//end of if(TTKCommon.checkNull(strReasonTypeID[i])!=null && !TTKCommon.checkNull(strReasonTypeID[i]).equals(""))
					documentChecklistVO.setClaimSeqID(null);
					documentChecklistVO.setRemarks(TTKCommon.replaceSingleQots(strRemarks[i]));
					documentChecklistVO.setDocumentName(strDocumentName[i]);
					documentChecklistVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
					alClaimDocumentList.add(documentChecklistVO);
				}//end of for
			}//end of if((TTKCommon.checkNull(strsourceType).equals("CPN"))&&(!TTKCommon.checkNull(strDocumentType).equals("DTS")))
			
			//setting shortfallID in a form
			if(alShortfallVO != null)
			{
				for(int i=0; i<alShortfallVO.size() ; i++)
				{
					if(((String)((CacheObject)alShortfallVO.get(i)).getCacheId()).equals(frmClaimDetails.getString("shortfallSeqID")))
					{
						frmClaimDetails.set("shortfallID",((CacheObject)alShortfallVO.get(i)).getCacheDesc());
						break;
					}//end of if
				}//end of for(int i=0; i<alShortfallVO.size() ; i++)
			}//end of if(alShortfallVO != null)
			
			claimInwardDetailVO = (ClaimInwardDetailVO)FormUtils.getFormValues(frmClaimDetails, "frmClaimDetails",this, mapping, request);
			ActionForm frmEnrollment = (ActionForm)frmClaimDetails.get("claimantVO");
			claimantVO=(ClaimantVO)FormUtils.getFormValues(frmEnrollment, "frmClaimEnrollment",this, mapping, request);
			claimInwardDetailVO.setClaimantVO(claimantVO);
			claimInwardDetailVO.setClaimDocumentVOList(alClaimDocumentList);
			claimInwardDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			
			/*if(!claimInwardDetailVO.getDocumentTypeID().equals("DTC")){
				claimInwardDetailVO.setClaimTypeID("");
			}//end of if(!claimInwardDetailVO.getDocumentTypeID().equals("DTC"))
*/			
			lngResult = claimManagerObject.saveClaimInwardDetail(claimInwardDetailVO);
			if(lngResult != 0)
			{
				if(!(TTKCommon.checkNull((String)frmClaimDetails.get("inwardSeqID")).equals("")))
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}// end of if(!(TTKCommon.checkNull((String)frmClaimDetails.get("floatAcctSeqID")).equals("")))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
			}//end of if(lngResult != 0)
			
			if(claimInwardDetailVO.getDocumentTypeID().equals("DTC")){
				claimInwardDetailVO= claimManagerObject.getClaimInwardDetail(lngResult,claimInwardDetailVO.getClaimTypeID(),TTKCommon.getUserSeqId(request));
			}//end of if(claimInwardDetailVO.getDocumentTypeID().equals("DTC"))
			else{
				claimInwardDetailVO= claimManagerObject.getClaimInwardDetail(lngResult,null,TTKCommon.getUserSeqId(request));
			}//end of else
			
			alClaimDocumentList = claimInwardDetailVO.getClaimDocumentVOList();
			frmClaimDetails = (DynaActionForm)FormUtils.setFormValues("frmClaimDetails",claimInwardDetailVO, this, mapping, request);
			if(claimInwardDetailVO.getClaimantVO()==null)
			{
				claimInwardDetailVO.setClaimantVO(new ClaimantVO());
			}//end of if(claimInwardDetailVO.getClaimantVO()==null)
			frmClaimDetails.set("claimantVO",FormUtils.setFormValues("frmClaimEnrollment",claimInwardDetailVO.getClaimantVO(), this, mapping, request));
			if((TTKCommon.checkNull((String)frmClaimDetails.get("inwardSeqID")).equals("")))
			{
				if(userSecurityProfile.getBranchID()!=null)
				{
					frmClaimDetails.set("officeSeqID", userSecurityProfile.getBranchID().toString());
				}//end of if(userSecurityProfile.getBranchID()!=null)
				else
				{
					frmClaimDetails.set("officeSeqID", "");
				}//end of else
				frmClaimDetails.set("officeName",userSecurityProfile.getBranchName());
			}// end of if(!(TTKCommon.checkNull((String)frmClaimDetails.get("floatAcctSeqID")).equals("")))
			frmClaimDetails.set("alClaimDocumentList",alClaimDocumentList);
			frmClaimDetails.set("claimType",claimInwardDetailVO.getClaimTypeID());
			if(alPrevClaim!=null && alPrevClaim.size() > 0)
			{
				frmClaimDetails.set("alPrevClaim",alPrevClaim);
			}//end of if(alPrevClaim!=null && alPrevClaim.size() > 0)
			else
			{
				frmClaimDetails.set("alPrevClaim",new ArrayList());
			}//end of else
			if(alShortfallVO!=null && alShortfallVO.size() > 0)
			{
				frmClaimDetails.set("alShortfall",alShortfallVO);
			}//end of if(alShortfallVO!=null && alShortfallVO.size() > 0)
			else
			{
				frmClaimDetails.set("alShortfall",new ArrayList());
			}//end of else
			//frmClaimDetails.set("alClaimDocumentList",(DocumentChecklistVO[])claimInwardDetailVO.getClaimDocumentVOList().toArray(new DocumentChecklistVO[0]));
			request.getSession().setAttribute("frmClaimDetails",frmClaimDetails);
			return this.getForward("saveclaimdetails", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationExp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to navigate to  grid screen on click of the image.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeCourier(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doChangeCourier method of ClaimsDetailsAction");
			setLinks(request);
			return mapping.findForward("couriersearch");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationExp));
		}//end of catch(Exception exp)
	}//end of doChangeCourier(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to navigate to  grid screen on click of the image.
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
	public ActionForward doSelectEnrollmentID(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSelectEnrollmentID method of ClaimsDetailsAction");
			setLinks(request);
			return mapping.findForward("inwardenrollmentlist");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationExp));
		}//end of catch(Exception exp)
	}//end of doSelectEnrollmentID(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to navigate to  grid screen on click of the image.
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
	public ActionForward doSelectClaimID(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSelectClaimID method of ClaimsDetailsAction");
			setLinks(request);
			return mapping.findForward("inwardclaimlist");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationExp));
		}//end of catch(Exception exp)
	}//end of doSelectEnrollmentID(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to change the document type.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeDocType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the ChangeDocType method of ClaimsDetailsAction");
			setLinks(request);
			ClaimManager claimManagerObject=this.getClaimManagerObject();
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			ArrayList alClaimDocumentList = null;
			DynaActionForm frmClaimDetails=(DynaActionForm)form;
			ClaimInwardDetailVO claimInwardDetailVO = new ClaimInwardDetailVO();
			ClaimantVO claimantVO = new ClaimantVO();
			claimInwardDetailVO.setClaimantVO(claimantVO);
			if(userSecurityProfile.getBranchID()!=null)
			{
				claimInwardDetailVO.setOfficeSeqID(userSecurityProfile.getBranchID());
			}//end of if(userSecurityProfile.getBranchID()!=null)
			else
			{
				frmClaimDetails.set("officeSeqID", "");
			}//end of else
			frmClaimDetails.set("officeName",userSecurityProfile.getBranchName());
			if(!("sourceTypeID".equals(request.getParameter("focusID"))||("claimTypeID".equals(request.getParameter("focusID"))))){
				frmClaimDetails.set("claimantVO",FormUtils.setFormValues("frmClaimEnrollment",claimInwardDetailVO.getClaimantVO(), this, mapping, request));
			}//end of if(!("sourceTypeID".equals(request.getParameter("focusID"))||("claimTypeID".equals(request.getParameter("focusID")))))
			
			//if(frmClaimDetails.getString("documentTypeID").equals("DTC") && !TTKCommon.checkNull(frmClaimDetails.getString("claimTypeID")).equals(""))
			if(!TTKCommon.checkNull(frmClaimDetails.getString("claimTypeID")).equals("") && frmClaimDetails.getString("documentTypeID").equals("DTC"))
			{
				alClaimDocumentList = claimManagerObject.getClaimDocumentList(frmClaimDetails.getString("claimTypeID"));
			}//end of if(frmClaimDetails.getString("documentTypeID").equals("DTC") && !TTKCommon.checkNull(frmClaimDetails.getString("claimTypeID")).equals(""))
			else if(!TTKCommon.checkNull(frmClaimDetails.getString("documentTypeID")).equals("") && !frmClaimDetails.getString("documentTypeID").equals("DTC")){
				alClaimDocumentList = claimManagerObject.getClaimDocumentList(null);
			}//end of else
			
			frmClaimDetails.set("alClaimDocumentList",alClaimDocumentList);
			
			frmClaimDetails.set("alShortfall",this.populateShortfallVO(claimInwardDetailVO,frmClaimDetails));
			
			if("documentTypeID".equals(request.getParameter("focusID")))
			{
				frmClaimDetails.set("alPrevClaim",claimInwardDetailVO.getPrevClaimList());
			}//end of if("documentTypeID".equals(request.getParameter("focusID")))
			
			if(!frmClaimDetails.getString("documentTypeID").equals("DTC")){ 
				frmClaimDetails.set("claimTypeID","");
			}//end of if(!frmClaimDetails.getString("documentTypeID").equals("DTC"))
			
			if(!frmClaimDetails.getString("sourceTypeID").equals("CCR"))
			{
				frmClaimDetails.set("courierNbr","");
			}//end of if(!frmClaimDetails.getString("sourceTypeID").equals("CCR"))
			frmClaimDetails.set("frmChanged","changed");
			request.getSession().setAttribute("frmClaimDetails",frmClaimDetails);
			return mapping.findForward("claimdetails");  
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationExp));
		}//end of catch(Exception exp)
	}//end of doChangeDocType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to clear the Ids.
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
	public ActionForward doClearEnrollmentID(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doClearEnrollmentID method of ClaimsDetailsAction");
			setLinks(request);
			DynaActionForm frmClaimDetails = (DynaActionForm)request.getSession().getAttribute("frmClaimDetails");
			frmClaimDetails.set("claimantVO",FormUtils.setFormValues("frmClaimEnrollment",new ClaimantVO(),this,mapping,request));
			frmClaimDetails.set("frmChanged","changed");
			return mapping.findForward("claimdetails");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationExp));
		}//end of catch(Exception exp)
	}//end of doClearEnrollmentID(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to clear the Ids.
	 * This method is used to navigate to detail screen to add a record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 *//*
	public ActionForward doClearClaimID(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doClearClaimID method of ClaimsDetailsAction");
			setLinks(request);
			DynaActionForm frmClaimDetails = (DynaActionForm)request.getSession().getAttribute("frmClaimDetails");
			frmClaimDetails.set("claimantVO",FormUtils.setFormValues("frmClaimEnrollment",new ClaimantVO(),this,mapping,request));
			frmClaimDetails.set("frmChanged","changed");
			return mapping.findForward("claimdetails");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationExp));
		}//end of catch(Exception exp)
	}//end of doClearEnrollmentID(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
*/	
	/**
	 * This method is used for temporary storage of data before submiting the form for validation.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSubmit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSubmit method of ClaimsDetailsAction");
			setLinks(request);
			ArrayList<Object> alClaimDocumentList = null;
			DynaActionForm frmClaimDetails=(DynaActionForm)form;
			frmClaimDetails=(DynaActionForm)form;
			DocumentChecklistVO documentChecklistVO =null;
			//String strDocumentType = frmClaimDetails.getString("documentTypeID");
			String strsourceType = frmClaimDetails.getString("sourceTypeID");
			//String strClaimTypeID = frmClaimDetails.getString("claimTypeID");
			ActionMessage actionMessage=null;
			ActionMessages actionMessages = new ActionMessages();
			boolean errSheets=false;
			boolean errReason=false;
			//if((TTKCommon.checkNull(strsourceType).equals("CPN"))&&(!TTKCommon.checkNull(strDocumentType).equals("DTS")) && (!TTKCommon.checkNull(strClaimTypeID).equals("")))
			if((TTKCommon.checkNull(strsourceType).equals("CPN")))
			{
				//log.debug("Inside doSubmit");
				alClaimDocumentList = new ArrayList<Object>();
				String[] strSelectDocumentListType = (String[])frmClaimDetails.get("SelectDocumentListType");
				String[] strSelectDocumentRcvdSeqID = (String[])frmClaimDetails.get("SelectDocumentRcvdSeqID");
				String[] strSheets = request.getParameterValues("sheets");
				String[] strDocumentTypeID = request.getParameterValues("DocTypeID");
				String[] strReasonTypeID = request.getParameterValues("selectedReasonTypeID");
				String[] strRemarks = request.getParameterValues("gridRemarks");
				String[] strDocumentRcvdYN = request.getParameterValues("selectedChkopt");
				String[] strDocumentName = request.getParameterValues("selectDocumentName");
				for(int i=0 ;i<strSelectDocumentListType.length ;i++)
				{
					documentChecklistVO = new DocumentChecklistVO();
					if(strSelectDocumentRcvdSeqID[i]!=null && !strSelectDocumentRcvdSeqID[i].equals(""))
					{
						documentChecklistVO.setDocumentRcvdSeqID(Long.parseLong(strSelectDocumentRcvdSeqID[i]));
					}//end of if(strSelectDocumentRcvdSeqID[i]!=null && !strSelectDocumentRcvdSeqID[i].equals(""))
					documentChecklistVO.setDocumentRcvdYN(strDocumentRcvdYN[i]);
					documentChecklistVO.setDocumentListType(strSelectDocumentListType[i]);
					if(strDocumentRcvdYN[i].equals("Y"))
					{
						if(strSheets[i].equals(""))
						{
							errSheets=true;
						}//end of if(strSheets[i].equals(""))
						if(strDocumentTypeID[i].equals("DUP")&&strReasonTypeID[i].equals(""))
						{
							errReason=true;
						}//end of if(strDocumentTypeID[i].equals("DUP")&&strReasonTypeID[i].equals(""))
					}//end of if(strDocumentRcvdYN[i].equals("Y"))
					documentChecklistVO.setSheetsCount(strSheets[i]);
					documentChecklistVO.setDocumentTypeID(strDocumentTypeID[i]);
					if(TTKCommon.checkNull(strReasonTypeID[i])!=null && !TTKCommon.checkNull(strReasonTypeID[i]).equals(""))
					{
						documentChecklistVO.setReasonTypeID(strReasonTypeID[i]);
					}//end of if(TTKCommon.checkNull(strReasonTypeID[i])!=null && !TTKCommon.checkNull(strReasonTypeID[i]).equals(""))
					documentChecklistVO.setClaimSeqID(null);
					documentChecklistVO.setRemarks(strRemarks[i]);
					documentChecklistVO.setDocumentName(strDocumentName[i]);
					documentChecklistVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
					alClaimDocumentList.add(documentChecklistVO);
				}//end of for
			}//end of if
			//frmClaimDetails.set("alClaimDocumentList",(DocumentChecklistVO[])alClaimDocumentList.toArray(new DocumentChecklistVO[0]));
			frmClaimDetails.set("alClaimDocumentList",alClaimDocumentList);
			if(errSheets)
			{
				actionMessage = new ActionMessage("error.sheet");
				actionMessages.add("global.error",actionMessage);
				saveErrors(request,actionMessages);
				return mapping.findForward("claimdetails");
			}//end of if(errSheets)
			if(errReason)
			{
				actionMessage = new ActionMessage("error.reason");
				actionMessages.add("global.error",actionMessage);
				saveErrors(request,actionMessages);
				return mapping.findForward("claimdetails");
			}//end of if(errReason)
			return mapping.findForward("submitform"); 
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationExp));
		}//end of catch(Exception exp)
	}//end of doSubmit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doReset method of ClaimsDetailsAction");
			setLinks(request);
			ClaimManager claimManagerObject=this.getClaimManagerObject();
			ClaimInwardDetailVO claimInwardDetailVO = null;
			ClaimantVO claimantVO = null;
			DynaActionForm  frmClaimDetails = (DynaActionForm)form;
			ArrayList alClaimDocumentList = null;
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			frmClaimDetails=(DynaActionForm)form;
			claimInwardDetailVO = new ClaimInwardDetailVO();
			claimantVO = new ClaimantVO();
			claimInwardDetailVO.setClaimantVO(claimantVO);
			String strClaimType = null;
			
			if(TTKCommon.checkNull(frmClaimDetails.getString("documentTypeID")).equals("DTC")){
				strClaimType = frmClaimDetails.getString("claimType");
				alClaimDocumentList = claimManagerObject.getClaimDocumentList(frmClaimDetails.getString("claimTypeID"));
			}//end of if(TTKCommon.checkNull(frmClaimDetails.getString("documentTypeID")).equals("DTC"))
			else{
				alClaimDocumentList = claimManagerObject.getClaimDocumentList(null);
			}//end of else
			
			if(!(TTKCommon.checkNull(frmClaimDetails.getString("inwardSeqID")).equals("")))
			{
				claimInwardDetailVO= claimManagerObject.getClaimInwardDetail(TTKCommon.getLong(frmClaimDetails.getString("inwardSeqID")),strClaimType,TTKCommon.getUserSeqId(request));
				alClaimDocumentList = claimInwardDetailVO.getClaimDocumentVOList();
				strClaimType =claimInwardDetailVO.getClaimTypeID();
			}//end of if(!(TTKCommon.checkNull(frmClaimDetails.getString("inwardSeqID")).equals("")))
			else{
				if(userSecurityProfile.getBranchID()!=null){
					claimInwardDetailVO.setOfficeSeqID(userSecurityProfile.getBranchID());
				}//end of if(userSecurityProfile.getBranchID()!=null)
				else{
					frmClaimDetails.set("officeSeqID", "");
				}//end of else
			}//end of else
			frmClaimDetails = (DynaActionForm)FormUtils.setFormValues("frmClaimDetails",claimInwardDetailVO, this, mapping, request);
			frmClaimDetails.set("claimantVO",FormUtils.setFormValues("frmClaimEnrollment",claimInwardDetailVO.getClaimantVO(), this, mapping, request));
			frmClaimDetails.set("alClaimDocumentList",alClaimDocumentList);
			frmClaimDetails.set("alPrevClaim",claimInwardDetailVO.getPrevClaimList());
			//frmClaimDetails.set("alShortfall",this.populateShortfallVO(claimInwardDetailVO));
//			ArrayList alShortfall=this.populateShortfallVO(claimInwardDetailVO,frmClaimDetails);
			frmClaimDetails.set("alShortfall",this.populateShortfallVO(claimInwardDetailVO,frmClaimDetails));
			request.getSession().setAttribute("frmClaimDetails",frmClaimDetails);
			frmClaimDetails.set("claimType",strClaimType);
			return mapping.findForward("claimdetails");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInvestigationExp));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the ClaimManager session object for invoking methods on it.
	 * @return ClaimManager session object which can be used for method invokation
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
			}//end of if(claimManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "ClaimManager");
		}//end of catch
		return claimManager;
	}//end of getClaimManagerObject()

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmEnrollSearch DynaActionForm
	 * @return ArrayList contains search parameters
	 */
	private ArrayList populateShortfallVO(ClaimInwardDetailVO claimInwardDetailVO, DynaActionForm  frmClaimDetails)
																				throws TTKException
	{
		ClaimManager claimManagerObject=this.getClaimManagerObject();
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		ArrayList alValues = null;
		DynaActionForm frmEnrollment = (DynaActionForm)frmClaimDetails.get("claimantVO");
		if(frmEnrollment.get("clmSeqID")!=null)
		{
			alSearchParams.add(new Long(frmEnrollment.get("clmSeqID").toString()));
		}//end of if(frmEnrollment.get("clmSeqID")!=null)
		else
		{
			alSearchParams.add(claimInwardDetailVO.getClaimantVO().getClmSeqID());
		}//end of else
		if(frmClaimDetails.getString("shortfallID")!=null)
		{
			alSearchParams.add(frmClaimDetails.getString("shortfallID"));
		}//end of if(frmClaimDetails.getString("shortfallID")!=null)
		else
		{
			alSearchParams.add(claimInwardDetailVO.getShortfallID());
		}//end of else
		alValues = claimManagerObject.getInwardShortfallDetail(alSearchParams);
		if(alValues != null)
		{
			for(int i=0; i<alValues.size() ; i++)
			{
				if(((String)((CacheObject)alValues.get(i)).getCacheId()).equals(frmClaimDetails.getString("shortfallSeqID")))
				{
					frmClaimDetails.set("shortfallID",((CacheObject)alValues.get(i)).getCacheDesc());
					break;
				}//end of if
			}//end of for(int i=0; i<alValues.size() ; i++)
		}//end of if(alValues != null)
		else
		{
			alValues = new ArrayList();
		}//end of else
		return alValues;
	}//end of populateShortfallVO(DynaActionForm frmEnrollSearch)
	
}//end of ClaimDetails Action
