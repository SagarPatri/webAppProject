/**
 * @ (#) SupportDocAction.java May 06, 2006
 * Project      : TTK HealthCare Services
 * File         : SupportDocAction.java
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : May 06, 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.dataentrypreauth;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;

import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.claims.ClaimManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.business.preauth.PreAuthSupportManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.RuleXMLHelper;
import com.ttk.dto.claims.ClaimIntimationVO;
import com.ttk.dto.claims.DocumentChecklistVO;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.preauth.BufferVO;
import com.ttk.dto.preauth.ShortfallVO;
import com.ttk.dto.preauth.SupportVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is reusable for searching support doc information in pre-auth and claims flow.
 */

public class SupportDocAction extends TTKAction
{
	private static Logger log = Logger.getLogger(SupportDocAction.class);

	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	private static final String strSubmit="submit";

	//Action mapping forwards for preauth
	private static final String strPreauthSupportDoc="preauthsupportdoc";
	private static final String strPreauthShortFall="preauthshortfall";

	//Action mapping forwards for claims
	private static final String strClaimsSupportDoc="claimssupportdoc";
	private static final String strClaimsShortFall="claimsshortfall";
	private static final String strClaimsDischarge="claimsdischarge";

	//Links
	private static final String strPreAuthorization="Pre-Authorization";
	private static final String strClaims="Claims";

    //Exception Message Identifier
    private static final String strSupportError="support";

	/**
     * This method is used to search the data with the given search criteria.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
	public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside SupportDocAction doSearch");
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strSupportDoc="";
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
				strSupportDoc=strPreauthSupportDoc;		// Pre-Authorization Flow
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
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
				strSupportDoc=strClaimsSupportDoc;		// Claims Flow
			}//end of else if(strActiveLink.equals(strClaims))

			// Building the caption
			StringBuffer strCaption= new StringBuffer();
			DynaActionForm formSuppDoc= (DynaActionForm) form;
			formSuppDoc.set("frmChanged",null);
			String strEnrollmentID="";
			if(strActiveLink.equals(strPreAuthorization))
			{
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
								//PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
								//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strActiveLink.equals(strClaims))
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
				formSuppDoc.set("documentType","SRT");	// setting the document type to ShortFall
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			//This block is called when Claim Intemation is selected from Document Type drop down
			if(formSuppDoc.get("documentType").equals("SCI"))
			{
				ClaimIntimationVO claimIntimationVO =preAuthSupportManagerObject.getClaimIntimationDetail(
										ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
				strCaption.append("Claim Intimation Details - [").append(
											ClaimsWebBoardHelper.getClaimantName(request)).append("]").append(strEnrollmentID);
				formSuppDoc.set("caption",String.valueOf(strCaption));
				if(claimIntimationVO==null)
				{
					claimIntimationVO= new ClaimIntimationVO();
				}//end of if(claimIntimationVO==null)
				request.getSession().setAttribute("claimIntimationVO",claimIntimationVO);
				return this.getForward(strSupportDoc, mapping, request);
			}//end of if(formSuppDoc.get("documentType").equals("SCI"))

			if(!formSuppDoc.get("documentType").equals("DCL")&& !formSuppDoc.get("documentType").equals("SCI"))
			{
				String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
				String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
				//if the page number or sort id is clicked
				if(!strPageID.equals("") || !strSortID.equals(""))
				{
					if(!strPageID.equals(""))
					{
						tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
						return (mapping.findForward(strSupportDoc));
					}///end of if(!strPageID.equals(""))
					else
					{
						tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
						tableData.modifySearchData("sort");//modify the search data
					}//end of else
				}//end of if(!strPageID.equals("") || !strSortID.equals(""))
				else
				{
					tableData.createTableInfo("SupportDocTable",null);
					if(formSuppDoc.get("documentType").equals("SRT"))
					{
						((Column)((ArrayList)tableData.getTitle()).get(2)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(3)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(4)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(5)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(10)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(11)).setVisibility(false);
					}// end of if(formSuppDoc.get("documentType").equals("SRT"))
					else if(formSuppDoc.get("documentType").equals("IVN"))
					{
						((Column)((ArrayList)tableData.getTitle()).get(0)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(1)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(5)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(7)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(10)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(11)).setVisibility(false);
					}// end of else if(formSuppDoc.get("documentType").equals("IVN"))
					else if(formSuppDoc.get("documentType").equals("BUF"))
					{
						((Column)((ArrayList)tableData.getTitle()).get(0)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(1)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(2)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(3)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(4)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(5)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(6)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(7)).setVisibility(false);
					}// end of else if(formSuppDoc.get("documentType").equals("BUF"))
					else if(formSuppDoc.get("documentType").equals("DCV"))
					{
						((Column)((ArrayList)tableData.getTitle()).get(0)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(1)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(2)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(3)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(4)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(9)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(10)).setVisibility(false);
						((Column)((ArrayList)tableData.getTitle()).get(11)).setVisibility(false);
					}// end of else if(formSuppDoc.get("documentType").equals("DCV"))
					tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
					tableData.setSortData("0001");
					if(formSuppDoc.get("documentType").equals("BUF"))
					{
						tableData.setSortColumnName("ID");
					}//end of if(formSuppDoc.get("documentType").equals("BUF"))
					else
					{
						tableData.setSortColumnName("SEQ_ID");
					}//end of else
					tableData.setSortOrder("ASC");
					tableData.modifySearchData("search");
				}//end of else
				ArrayList alSupportDoc=new ArrayList();
				if(formSuppDoc.get("documentType").equals("BUF"))
				{
					alSupportDoc= preAuthSupportManagerObject.getSupportBufferList(tableData.getSearchData());
				}// end of if(formSuppDoc.get("documentType").equals("BUF"))
				else if(formSuppDoc.get("documentType").equals("DCV"))
				{
					alSupportDoc= preAuthSupportManagerObject.getDischargeVoucherList(tableData.getSearchData());
				}// end of else if(formSuppDoc.get("documentType").equals("DCV"))
				else
				{
					alSupportDoc= preAuthSupportManagerObject.getSupportDocumentList(tableData.getSearchData());
				}//end of else
				tableData.setData(alSupportDoc, "search");
				if(formSuppDoc.get("documentType").equals("DCV"))
				{
					strCaption.append("List of Discharge Voucher - [").append(ClaimsWebBoardHelper.getClaimantName(
																		request)).append("]").append(strEnrollmentID);
				}//end of if(formSuppDoc.get("documentType").equals("DCV"))
				else
				{
					if(strActiveLink.equals(strPreAuthorization))
					{
						strCaption.append("List of Support Documents - [").append(PreAuthWebBoardHelper.getClaimantName(
																		request)).append("]").append(strEnrollmentID);
					}//end of if(strActiveLink.equals(strPreAuthorization))
					else if(strActiveLink.equals(strClaims))
					{
						strCaption.append("List of Support Documents - [").append(ClaimsWebBoardHelper.getClaimantName(
																		request)).append("]").append(strEnrollmentID);
					}//end of else if(strActiveLink.equals(strClaims))
				}//end of else
				formSuppDoc.set("caption",String.valueOf(strCaption));
				//set the batch policy data object to session
				request.getSession().setAttribute("tableData",tableData);
			}//end of if(!formSuppDoc.get("documentType").equals("DCL")&& !formSuppDoc.get("documentType").equals("SCI"))
			else
				if(formSuppDoc.get("documentType").equals("DCL"))
				{
					ArrayList alSupportDocChkList = preAuthSupportManagerObject.getDocumentChecklist(
										ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
					formSuppDoc.set("alSupportDocChkList",alSupportDocChkList);
					strCaption.append("Document Checklist - [").append(ClaimsWebBoardHelper.getClaimantName(request)).
																		append("]").append(strEnrollmentID);
					formSuppDoc.set("caption",String.valueOf(strCaption));
					request.getSession().setAttribute("frmSuppDoc",formSuppDoc);
					return this.getForward("docchklist", mapping, request);
				}//end of if(formSuppDoc.get("documentType").equals("DCL"))
			//finally return to the grid screen
			return this.getForward(strSupportDoc, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to get the previous set of records with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside SupportDocAction doBackward");
			TableData  tableData =TTKCommon.getTableData(request);
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			DynaActionForm formSuppDoc= (DynaActionForm) form;
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strSupportDoc="";
			if(strActiveLink.equals(strPreAuthorization))
			{
				strSupportDoc=strPreauthSupportDoc;		// Pre-Authorization Flow
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				strSupportDoc=strClaimsSupportDoc;		// Claims Flow
			}//end of else if(strActiveLink.equals(strClaims))
			ArrayList alSupportDoc=new ArrayList();
			tableData.modifySearchData(strBackward);//modify the search data
			if(formSuppDoc.get("documentType").equals("BUF"))
			{
				alSupportDoc= preAuthSupportManagerObject.getSupportBufferList(tableData.getSearchData());
			}// end of if(formSuppDoc.get("documentType").equals("BUF"))
			else if(formSuppDoc.get("documentType").equals("DCV"))
			{
				alSupportDoc= preAuthSupportManagerObject.getDischargeVoucherList(tableData.getSearchData());
			}// end of else if(formSuppDoc.get("documentType").equals("DCV"))
			else if(formSuppDoc.get("documentType").equals("DCL"))
			{
				alSupportDoc= preAuthSupportManagerObject.getSupportDocumentList(tableData.getSearchData());
			}// end of else if(formSuppDoc.get("documentType").equals("DCV"))
			else
			{
				alSupportDoc= preAuthSupportManagerObject.getSupportDocumentList(tableData.getSearchData());
			}//end of else
			tableData.setData(alSupportDoc, strBackward);//set the support document data
			request.getSession().setAttribute("tableData",tableData);//set the support document data object to session
			return this.getForward(strSupportDoc, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to get the next set of records with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																		HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside SupportDocAction doForward");
			TableData  tableData =TTKCommon.getTableData(request);
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			DynaActionForm formSuppDoc= (DynaActionForm) form;
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strSupportDoc=null;
			if(strActiveLink.equals(strPreAuthorization))
			{
				strSupportDoc=strPreauthSupportDoc;		// Pre-Authorization Flow
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				strSupportDoc=strClaimsSupportDoc;		// Claims Flow
			}//end of else if(strActiveLink.equals(strClaims))
			ArrayList alSupportDoc=new ArrayList();
			tableData.modifySearchData(strForward);//modify the search data
			if(formSuppDoc.get("documentType").equals("BUF"))
			{
				alSupportDoc= preAuthSupportManagerObject.getSupportBufferList(tableData.getSearchData());
			}// end of if(formSuppDoc.get("documentType").equals("BUF"))
			else if(formSuppDoc.get("documentType").equals("DCV"))
			{
				alSupportDoc= preAuthSupportManagerObject.getDischargeVoucherList(tableData.getSearchData());
			}// end of else if(formSuppDoc.get("documentType").equals("DCV"))
			else if(formSuppDoc.get("documentType").equals("DCL"))
			{
				alSupportDoc= preAuthSupportManagerObject.getSupportDocumentList(tableData.getSearchData());
			}// end of else if(formSuppDoc.get("documentType").equals("DCV"))
			else
			{
				alSupportDoc= preAuthSupportManagerObject.getSupportDocumentList(tableData.getSearchData());
			}//end of else
			tableData.setData(alSupportDoc, strForward);//set the support document data
			request.getSession().setAttribute("tableData",tableData);//set the support document data object to session
			return this.getForward(strSupportDoc, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
		//log.debug("Inside SupportDocAction doChangeWebBoard");
		return doSearch(mapping,form,request,response);
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
	public ActionForward doAddShortFallDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																		HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside SupportDocAction doAddShortFallDetails");
			String strReasonYN="";
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strShortFall="";
			String strEnrollmentID="";
			if(strActiveLink.equals(strPreAuthorization))
			{
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
							//PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				strShortFall=strPreauthShortFall;
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
									//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				strShortFall=strClaimsShortFall;
			}//end of else if(strActiveLink.equals(strClaims))

			// Building the caption
			StringBuffer strCaption= new StringBuffer();
			DynaActionForm formSuppDoc= (DynaActionForm) form;
			formSuppDoc.set("frmChanged",null);
			ShortfallVO shortfallVO=null;
			DynaActionForm frmShortFall= (DynaActionForm)form;
			frmShortFall.initialize(mapping);
			shortfallVO=new ShortfallVO();
			if(strActiveLink.equals(strPreAuthorization))
			{
				strCaption.append("Shortfall Details - ").append("Add").append(" [").append(
									PreAuthWebBoardHelper.getClaimantName(request)).append("][").append(
									PreAuthWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				strCaption.append("Shortfall Details - ").append("Add").append(" [").append(
									ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
											ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
			}//end of else if(strActiveLink.equals(strClaims))
			strReasonYN="N";
			shortfallVO.setEditYN("Y");
			request.setAttribute("shortfalltype","Medical");
			shortfallVO.setStatusTypeID("OPN");
			frmShortFall= (DynaActionForm)FormUtils.setFormValues("frmShortFall", shortfallVO, this, mapping, request);
			frmShortFall.set("shortfallQuestions",shortfallVO.getShortfallQuestions());
			frmShortFall.set("caption",String.valueOf(strCaption));
			frmShortFall.set("reasonYN",strReasonYN);
			request.setAttribute("shortfallSeqID",shortfallVO.getShortfallSeqID());
			request.setAttribute("shortfallTypeID",shortfallVO.getShortfallTypeID());
			this.documentViewer(request,shortfallVO);
			String strCurrentDate=TTKCommon.getFormattedDateHour(TTKCommon.getDate());
			String str[]=strCurrentDate.split(" ");
			frmShortFall.set("hiddenDate",str[0]);
			frmShortFall.set("hiddenTime",str[1]);
			frmShortFall.set("hiddenTimeStamp",str[2]);
			request.getSession().setAttribute("frmShortFall",frmShortFall);
			return this.getForward(strShortFall, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doAddShortFallDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	 //HttpServletResponse response)

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
	public ActionForward doViewShortFallDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside SupportDocAction doViewShortFallDetails");
			String strReasonYN="";
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strShortFall="";
			String strEnrollmentID="";
			if(strActiveLink.equals(strPreAuthorization))
			{
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
								//PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				strShortFall=strPreauthShortFall;
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
								//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				strShortFall=strClaimsShortFall;
			}//end of else if(strActiveLink.equals(strClaims))
			// Building the caption
			StringBuffer strCaption= new StringBuffer();
			SupportVO supportVO=null;
			DynaActionForm formSuppDoc= (DynaActionForm) form;
			formSuppDoc.set("frmChanged",null);
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			ShortfallVO shortfallVO=null;
			DynaActionForm frmShortFall= (DynaActionForm)form;
			supportVO = (SupportVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
			ArrayList<Object> alShortfallList = new ArrayList<Object>();
			alShortfallList.add(supportVO.getSeqID());
			if(strActiveLink.equals(strPreAuthorization))
			{
				alShortfallList.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				alShortfallList.add(null);
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				alShortfallList.add(null);
				alShortfallList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
			}//end of else if(strActiveLink.equals(strClaims))
			alShortfallList.add(TTKCommon.getUserSeqId(request));
			shortfallVO=preAuthSupportManagerObject.getShortfallDetail(alShortfallList);
			request.setAttribute("shortfalltype",shortfallVO.getShortfallDesc());
			request.setAttribute("shortfallTypeID",shortfallVO.getShortfallTypeID());
			
			//xml merging for shortfall questions
            String strShortFallType = shortfallVO.getShortfallDesc();
            Document missingDocs = null;
            if(shortfallVO.getShortfallTypeID()!=null && (shortfallVO.getShortfallTypeID()).equals("INM"))
			{
				missingDocs = preAuthSupportManagerObject.selectMissingDocs(
				ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
			}//end of if
            Document QueriesDoc =shortfallVO.getShortfallQuestions();
            Document MergeShortFallXml = RuleXMLHelper.MergeShortFallXml(QueriesDoc,missingDocs,strShortFallType,request);
            ArrayList alMergeShortFall = RuleXMLHelper.getSelectedNodes(QueriesDoc,strShortFallType);
            
		    request.setAttribute("shortFallDoc",MergeShortFallXml);

		    if(strActiveLink.equals(strPreAuthorization))
			{
				strCaption.append("Shortfall Details - ").append("Edit").append(" [").append(
									PreAuthWebBoardHelper.getClaimantName(request)).append("][").append(
										PreAuthWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				strCaption.append("Shortfall Details - ").append("Edit").append(" [").append(
						ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
							ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
			}//end of else if(strActiveLink.equals(strClaims))
			//To display the Reason depending on the Status Type "close" and "overridden"
			if(shortfallVO.getStatusTypeID().equals("CLS") || shortfallVO.getStatusTypeID().equals("ORD"))
			{
				strReasonYN="Y";
			}//end of if(shortfallVO.getStatusTypeID().equals("CLS") || shortfallVO.getStatusTypeID().equals("ORD"))
			else
			{
				strReasonYN="N";
			}//end of else
			frmShortFall= (DynaActionForm)FormUtils.setFormValues("frmShortFall", shortfallVO, this, mapping, request);
			frmShortFall.set("shortfallQuestions",MergeShortFallXml);
			frmShortFall.set("caption",String.valueOf(strCaption));
			frmShortFall.set("reasonYN",strReasonYN);
			request.setAttribute("shortfallSeqID",shortfallVO.getShortfallSeqID());
			request.setAttribute("MergeShortFall",alMergeShortFall);
			this.documentViewer(request,shortfallVO);
			String strCurrentDate=TTKCommon.getFormattedDateHour(TTKCommon.getDate());
			String str[]=strCurrentDate.split(" ");
			frmShortFall.set("hiddenDate",str[0]);
			frmShortFall.set("hiddenTime",str[1]);
			frmShortFall.set("hiddenTimeStamp",str[2]);
			request.getSession().setAttribute("frmShortFall",frmShortFall);
			return this.getForward(strShortFall, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doViewShortFallDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doSaveShortFallDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																		HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			String strReasonYN="";
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strShortFall="";
			if(strActiveLink.equals(strPreAuthorization))
			{
				strShortFall=strPreauthShortFall;
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				strShortFall=strClaimsShortFall;
			}//end of else if(strActiveLink.equals(strClaims))
			// Building the caption
			StringBuffer strCaption= new StringBuffer();
			DynaActionForm formSuppDoc= (DynaActionForm) form;
			formSuppDoc.set("frmChanged",null);
			String strEnrollmentID="";
			if(strActiveLink.equals(strPreAuthorization))
			{
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
								//PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
									//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strActiveLink.equals(strClaims))
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			ShortfallVO shortfallVO=null;
            DynaActionForm frmShortFall= (DynaActionForm)form;
            shortfallVO = (ShortfallVO)FormUtils.getFormValues(frmShortFall, this, mapping, request);
            if(strActiveLink.equals(strPreAuthorization))
            {
                shortfallVO.setPreAuthSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
            }//end of if(strActiveLink.equals(strPreAuthorization))
            else if(strActiveLink.equals(strClaims))
            {
                shortfallVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
            }//end of else if(strActiveLink.equals(strClaims))
            shortfallVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id
            
            shortfallVO.setShortfallQuestions(this.getQueriesDoc(request,frmShortFall));
            //To set strReasonTypeID to empty string if status type is other then close and overridden
            if((!shortfallVO.getStatusTypeID().equals("CLS")) && (!shortfallVO.getStatusTypeID().equals("ORD")))
            {
                shortfallVO.setReasonTypeID("");
            }//end of if((!shortfallVO.getStatusTypeID().equals("CLS"))&&(!shortfallVO.getStatusTypeID().equals("ORD")))
            Long lCount=null;
            lCount=preAuthSupportManagerObject.saveShortfall(shortfallVO,"SAVE");
            if(lCount>0)
            {
                //To display the Reason depending on the Status Type "close" and "overridden"
                if(shortfallVO.getStatusTypeID().equals("CLS") || shortfallVO.getStatusTypeID().equals("ORD"))
                {
                    strReasonYN="Y";
                }//end of if(shortfallVO.getStatusTypeID().equals("CLS")||shortfallVO.getStatusTypeID().equals("ORD"))
                else
                {
                    strReasonYN="N";
                }//end of else
                if(shortfallVO.getShortfallSeqID()!=null)
                {
                    request.setAttribute("updated","message.savedSuccessfully");
                    if(strActiveLink.equals(strPreAuthorization))
                    {
                        strCaption.append("Shortfall Details - ").append("Edit").append(" [").append(
                        	PreAuthWebBoardHelper.getClaimantName(request)).append("][").append(
                        		PreAuthWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
                    }//end of if(strActiveLink.equals(strPreAuthorization))
                    else if(strActiveLink.equals(strClaims))
                    {
                        strCaption.append("Shortfall Details - ").append("Edit").append(" [").append(
                        	ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
                        		ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
                    }//end of else if(strActiveLink.equals(strClaims))
                }//end of if(shortfallVO.getShortfallSeqID()!=null)
                else
                {
                    request.setAttribute("updated","message.addedSuccessfully");
                    if(strActiveLink.equals(strPreAuthorization))
                    {
                        strCaption.append("Shortfall Details - ").append("Add").append(" [").append(
                        			PreAuthWebBoardHelper.getClaimantName(request)).append("][").append(
                        					PreAuthWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
                    }//end of if(strActiveLink.equals(strPreAuthorization))
                    else if(strActiveLink.equals(strClaims))
                    {
                        strCaption.append("Shortfall Details - ").append("Add").append(" [").append(
                        			ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
                        				ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
                    }//end of else if(strActiveLink.equals(strClaims))
                    shortfallVO = new ShortfallVO();
                    frmShortFall.initialize(mapping);
                    frmShortFall.set("statusTypeID", "OPN");
                }//end of else
            }//end of if(lCount>0)
            //strStatusTypeID=shortfallVO.getStatusTypeID();
            ArrayList<Object> alShortfallList = new ArrayList<Object>();
            alShortfallList.add(lCount);
            if(strActiveLink.equals(strPreAuthorization))
            {
                alShortfallList.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
                alShortfallList.add(null);
            }//end of if(strActiveLink.equals(strPreAuthorization))
            else if(strActiveLink.equals(strClaims))
            {
                alShortfallList.add(null);
                alShortfallList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
            }//end of else if(strActiveLink.equals(strClaims))
            alShortfallList.add(TTKCommon.getUserSeqId(request));
            shortfallVO=preAuthSupportManagerObject.getShortfallDetail(alShortfallList);
            frmShortFall = (DynaActionForm)FormUtils.setFormValues("frmShortFall", shortfallVO, this, mapping, request);
            frmShortFall.set("caption",strCaption.toString());
            frmShortFall.set("reasonYN",strReasonYN);
            request.setAttribute("shortfallTypeID",shortfallVO.getShortfallTypeID());
            //xml merging for shortfall questions
            String strShortFallType = shortfallVO.getShortfallDesc();
            Document QueriesDoc =shortfallVO.getShortfallQuestions();
            Document missingDocs = null;
            if(shortfallVO.getShortfallTypeID()!=null && (shortfallVO.getShortfallTypeID()).equals("INM"))
			{
				missingDocs = preAuthSupportManagerObject.selectMissingDocs(
				ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
			}//end of if
            Document MergeShortFallXml = RuleXMLHelper.MergeShortFallXml(QueriesDoc,missingDocs,strShortFallType,request);
            ArrayList alMergeShortFall = RuleXMLHelper.getSelectedNodes(QueriesDoc,strShortFallType);
            frmShortFall.set("shortfallQuestions",MergeShortFallXml);
            request.setAttribute("MergeShortFall",alMergeShortFall);
		    request.setAttribute("shortFallDoc",MergeShortFallXml);
            
            request.setAttribute("shortfalltype",shortfallVO.getShortfallDesc());
            request.setAttribute("shortfallSeqID",shortfallVO.getShortfallSeqID());
            this.documentViewer(request,shortfallVO);
            String strCurrentDate=TTKCommon.getFormattedDateHour(TTKCommon.getDate());
			String str[]=strCurrentDate.split(" ");
			frmShortFall.set("hiddenDate",str[0]);
			frmShortFall.set("hiddenTime",str[1]);
			frmShortFall.set("hiddenTimeStamp",str[2]);
            request.getSession().setAttribute("frmShortFall",frmShortFall);
            return this.getForward(strShortFall, mapping, request);
        }//end of try
		catch(TTKException expTTK)
		{
			DynaActionForm frmShortFall1 = (DynaActionForm) request.getSession().getAttribute("frmShortFall");
			Document QueriesDoc = this.getQueriesDoc(request,frmShortFall1);
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strShortFallType = frmShortFall1.getString("shortfallDesc");
			String strShortfallTypeID = frmShortFall1.getString("shortfallTypeID");
			request.setAttribute("shortfallTypeID",strShortfallTypeID);
			//long lngseqID = Long.parseLong((String) frmShortFall1.get("shortfallSeqID")); 
			String lngseqID=(!((String) frmShortFall1.get("shortfallSeqID")).equalsIgnoreCase(""))?((String) frmShortFall1.get("shortfallSeqID")):"";//bajaj
			//long lngseqID = Long.parseLong((String) frmShortFall1.get("shortfallSeqID")); 
          
            Document missingDocs = null;
            if(strShortfallTypeID!=null && (strShortfallTypeID).equals("INM"))
			{
				missingDocs = preAuthSupportManagerObject.selectMissingDocs(
				ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
			}//end of if(strShortfallTypeID!=null && (strShortfallTypeID).equals("INM"))
            Document MergeShortFallXml = RuleXMLHelper.MergeShortFallXml(QueriesDoc,missingDocs,strShortFallType,request);
            ArrayList alMergeShortFall = RuleXMLHelper.getSelectedNodes(QueriesDoc,strShortFallType);
            DynaActionForm frmShortFall = (DynaActionForm)form;
            frmShortFall.set("shortfallQuestions",MergeShortFallXml);
			request.setAttribute("MergeShortFall",alMergeShortFall);
		    request.setAttribute("shortFallDoc",MergeShortFallXml);
		    request.setAttribute("shortfalltype",strShortFallType);
		    request.setAttribute("shortfallSeqID",lngseqID);
			request.getSession().setAttribute("frmShortFall",frmShortFall);
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doSaveShortFallDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  //HttpServletResponse response)

	/**
     * This method is used to send the selected record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
	public ActionForward doSend(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																		HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside SupportDocAction doSend");
			String strReasonYN="";
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strShortFall="";
			if(strActiveLink.equals(strPreAuthorization))
			{
				strShortFall=strPreauthShortFall;
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				strShortFall=strClaimsShortFall;
			}//end of else if(strActiveLink.equals(strClaims))
			// Building the caption
			StringBuffer strCaption= new StringBuffer();
			DynaActionForm formSuppDoc= (DynaActionForm) form;
			formSuppDoc.set("frmChanged",null);
			String strEnrollmentID="";
			if(strActiveLink.equals(strPreAuthorization))
			{
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
								//PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
							//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strActiveLink.equals(strClaims))
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			ShortfallVO shortfallVO=null;
            DynaActionForm frmShortFall= (DynaActionForm)form;
            shortfallVO = (ShortfallVO)FormUtils.getFormValues(frmShortFall, this, mapping, request);
            if(strActiveLink.equals(strPreAuthorization))
            {
                shortfallVO.setPreAuthSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
            }//end of if(strActiveLink.equals(strPreAuthorization))
            else if(strActiveLink.equals(strClaims))
            {
                shortfallVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
            }//end of else if(strActiveLink.equals(strClaims))
            shortfallVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id
            shortfallVO.setShortfallQuestions(this.getQueriesDoc(request,frmShortFall));
            //To set strReasonTypeID to empty string if status type is other then close and overridden
            if((!shortfallVO.getStatusTypeID().equals("CLS")) && (!shortfallVO.getStatusTypeID().equals("ORD")))
            {
                shortfallVO.setReasonTypeID("");
            }//end of if((!shortfallVO.getStatusTypeID().equals("CLS"))&&(!shortfallVO.getStatusTypeID().equals("ORD")))
            Long lCount=null;
//            CommunicationOptionVO communicationOptionVO = new CommunicationOptionVO();
//            String[] strCommArray = {"SMS","EMAIL","FAX"};
//            CommunicationManager commManagerObject = this.getCommunicationManagerObject();
            String strAuthpdf = TTKPropertiesReader.getPropertyValue("shortfallrptdir")+
            request.getParameter("shortfallNo")+".pdf";
    		File file = new File(strAuthpdf);
//    		commManagerObject.sendSMS(strCommArray[0]);
    		if(file.exists()){
    			lCount=preAuthSupportManagerObject.saveShortfall(shortfallVO,"SENT");
    			if(lCount>0)
                {
                    //To display the Reason depending on the Status Type "close" and "overridden"
                    if(shortfallVO.getStatusTypeID().equals("CLS") || shortfallVO.getStatusTypeID().equals("ORD"))
                    {
                        strReasonYN="Y";
                    }//end of if(shortfallVO.getStatusTypeID().equals("CLS")||shortfallVO.getStatusTypeID().equals("ORD"))
                    else
                    {
                        strReasonYN="N";
                    }//end of else
                    if(shortfallVO.getShortfallSeqID()!=null)
                    {
                        request.setAttribute("updated","message.savedSuccessfully");
                        if(strActiveLink.equals(strPreAuthorization))
                        {
                            strCaption.append("Shortfall Details - ").append("Edit").append(" [").append(
                            	PreAuthWebBoardHelper.getClaimantName(request)).append("][").append(
                            		PreAuthWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
                        }//end of if(strActiveLink.equals(strPreAuthorization))
                        else if(strActiveLink.equals(strClaims))
                        {
                            strCaption.append("Shortfall Details - ").append("Edit").append(" [").append(
                            	ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
                            		ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
                        }//end of else if(strActiveLink.equals(strClaims))
                    }//end of if(shortfallVO.getShortfallSeqID()!=null)
                    else
                    {
                        request.setAttribute("updated","message.addedSuccessfully");
                        if(strActiveLink.equals(strPreAuthorization))
                        {
                            strCaption.append("Shortfall Details - ").append("Add").append(" [").append(
                            		PreAuthWebBoardHelper.getClaimantName(request)).append("][").append(
                            							PreAuthWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
                        }//end of if(strActiveLink.equals(strPreAuthorization))
                        else if(strActiveLink.equals(strClaims))
                        {
                            strCaption.append("Shortfall Details - ").append("Add").append(" [").append(
                            			ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
                            						ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
                        }//end of else if(strActiveLink.equals(strClaims))
                        shortfallVO = new ShortfallVO();
                        frmShortFall.initialize(mapping);
                        frmShortFall.set("statusTypeID", "OPN");
                    }//end of else
                }//end of if(lCount>0)
                
//                communicationOptionVO.setFile(new File(strAuthpdf));
//                
//    			commManagerObject.sendMessage(strCommArray[1]);
//    			commManagerObject.sendFax(strCommArray[2]);
    			
    			//strStatusTypeID=shortfallVO.getStatusTypeID();
                ArrayList<Object> alShortfallList = new ArrayList<Object>();
                alShortfallList.add(lCount);
                if(strActiveLink.equals(strPreAuthorization))
                {
                    alShortfallList.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
                    alShortfallList.add(null);
                }//end of if(strActiveLink.equals(strPreAuthorization))
                else if(strActiveLink.equals(strClaims))
                {
                    alShortfallList.add(null);
                    alShortfallList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
                }//end of else if(strActiveLink.equals(strClaims))
                alShortfallList.add(TTKCommon.getUserSeqId(request));
                shortfallVO=preAuthSupportManagerObject.getShortfallDetail(alShortfallList);
                frmShortFall = (DynaActionForm)FormUtils.setFormValues("frmShortFall", shortfallVO, this, mapping,request);
                frmShortFall.set("caption",strCaption.toString());
                frmShortFall.set("reasonYN",strReasonYN);
                request.setAttribute("shortfallTypeID",shortfallVO.getShortfallTypeID());
                
                //xml merging for shortfall questions
                String strShortFallType = shortfallVO.getShortfallDesc();
                Document QueriesDoc =shortfallVO.getShortfallQuestions();
                Document missingDocs = null;
                if(shortfallVO.getShortfallTypeID()!=null && (shortfallVO.getShortfallTypeID()).equals("INM"))
    			{
    				missingDocs = preAuthSupportManagerObject.selectMissingDocs(
    				ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
    			}//end of if
                Document MergeShortFallXml = RuleXMLHelper.MergeShortFallXml(QueriesDoc,missingDocs,strShortFallType,request);
                ArrayList alMergeShortFall = RuleXMLHelper.getSelectedNodes(QueriesDoc,strShortFallType);
                frmShortFall.set("shortfallQuestions",MergeShortFallXml);
                frmShortFall.set("queriesDoc",QueriesDoc);
                request.setAttribute("MergeShortFall",alMergeShortFall);
    		    request.setAttribute("shortFallDoc",MergeShortFallXml);
                
                request.setAttribute("shortfalltype",shortfallVO.getShortfallDesc());
                request.setAttribute("shortfallSeqID",shortfallVO.getShortfallSeqID());
                this.documentViewer(request,shortfallVO);
                request.getSession().setAttribute("frmShortFall",frmShortFall);
    		}//end of if(file.exists())
    		else{
    			TTKException expTTK = new TTKException();
                expTTK.setMessage("error.shortfallpdf");
                throw expTTK;
    		}//end of else
    		
    		//xml merging for shortfall questions
            String strShortFallType = shortfallVO.getShortfallDesc();
    		Document QueriesDoc =shortfallVO.getShortfallQuestions();
            Document missingDocs = null;
    		Document MergeShortFallXml = RuleXMLHelper.MergeShortFallXml(QueriesDoc,missingDocs,strShortFallType,request);
            ArrayList alMergeShortFall = RuleXMLHelper.getSelectedNodes(QueriesDoc,strShortFallType);
    		frmShortFall.set("caption",strCaption.toString());
            frmShortFall.set("reasonYN",strReasonYN);
            request.setAttribute("shortfallTypeID",shortfallVO.getShortfallTypeID());
            frmShortFall.set("shortfallQuestions",MergeShortFallXml);
            frmShortFall.set("queriesDoc",QueriesDoc);
            request.setAttribute("MergeShortFall",alMergeShortFall);
		    request.setAttribute("shortFallDoc",MergeShortFallXml);
            request.setAttribute("shortfalltype",shortfallVO.getShortfallDesc());
            request.setAttribute("shortfallSeqID",shortfallVO.getShortfallSeqID());
            
            return this.getForward(strShortFall, mapping, request);
        }//end of try
		catch(TTKException expTTK)
		{
			DynaActionForm frmShortFall1 = (DynaActionForm) request.getSession().getAttribute("frmShortFall");
			Document QueriesDoc = this.getQueriesDoc(request,frmShortFall1);
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strShortFallType = frmShortFall1.getString("shortfallDesc");
			String strShortfallTypeID = frmShortFall1.getString("shortfallTypeID");
			request.setAttribute("shortfallTypeID",strShortfallTypeID);
//			long lngseqID = Long.parseLong((String) frmShortFall1.get("shortfallSeqID"));
			String lngseqID=(!((String) frmShortFall1.get("shortfallSeqID")).equalsIgnoreCase(""))?((String) frmShortFall1.get("shortfallSeqID")):"";//bajaj
            Document missingDocs = null;
            if(strShortfallTypeID!=null && (strShortfallTypeID).equals("INM"))
			{
				missingDocs = preAuthSupportManagerObject.selectMissingDocs(
				ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
			}//end of if(strShortfallTypeID!=null && (strShortfallTypeID).equals("INM"))
            Document MergeShortFallXml = RuleXMLHelper.MergeShortFallXml(QueriesDoc,missingDocs,strShortFallType,request);
            ArrayList alMergeShortFall = RuleXMLHelper.getSelectedNodes(QueriesDoc,strShortFallType);
            DynaActionForm frmShortFall = (DynaActionForm)form;
            frmShortFall.set("shortfallQuestions",MergeShortFallXml);
			request.setAttribute("MergeShortFall",alMergeShortFall);
		    request.setAttribute("shortFallDoc",MergeShortFallXml);
		    request.setAttribute("shortfalltype",strShortFallType);
		    request.setAttribute("shortfallSeqID",lngseqID);
			request.getSession().setAttribute("frmShortFall",frmShortFall);
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doSend(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doSaveClaimIntimation(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside SupportDocumentAction doSaveClaimIntimation");
			//Building the caption
            StringBuffer strCaption= new StringBuffer();
            DynaActionForm formSuppDoc= (DynaActionForm) form;
            String strEnrollmentID="";
            if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
					ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
            {
				strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
            }//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
							//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
            formSuppDoc.set("frmChanged",null);
            strCaption.append("Claim Intimation Details - [").append(
            					ClaimsWebBoardHelper.getClaimantName(request)).append("]").append(strEnrollmentID);
            PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			Long callLogSeqID=TTKCommon.getLong(formSuppDoc.getString("hidCallLogSeqID"));
            ClaimIntimationVO claimIntimationVO=(ClaimIntimationVO)
            													request.getSession().getAttribute("claimIntimationVO");
            claimIntimationVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
            claimIntimationVO.setCallLogSeqID(callLogSeqID);
            claimIntimationVO.setAddedBy(TTKCommon.getUserSeqId(request));
            Long lngCount=preAuthSupportManagerObject.saveClaimIntimationDetail(claimIntimationVO);
            if(lngCount>0)
            {
                 request.setAttribute("updated","message.saved");
            }//end of if(lngCount>0)
            return this.getForward(strClaimsSupportDoc, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doSaveClaimIntimation(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doClearClaimIntimation(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																		HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside SupportDocumentAction doClearClaimIntimation");
			//Building the caption
            StringBuffer strCaption= new StringBuffer();
            String strEnrollmentID="";
            DynaActionForm formSuppDoc= (DynaActionForm) form;
            formSuppDoc.set("frmChanged",null);
            if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
					ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
            {
				strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
            }//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
							//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
            strCaption.append("Claim Intimation Details - [").append(
            				ClaimsWebBoardHelper.getClaimantName(request)).append("]").append(strEnrollmentID);
            ClaimIntimationVO claimIntimationVO =new ClaimIntimationVO(); 
            
          //KOC 1339 for mail
            PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			Long callLogSeqID=TTKCommon.getLong(formSuppDoc.getString("hidCallLogSeqID"));
            //ClaimIntimationVO claimIntimationVO=(ClaimIntimationVO)
            													//request.getSession().getAttribute("claimIntimationVO");
            claimIntimationVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
            claimIntimationVO.setCallLogSeqID(callLogSeqID);
            claimIntimationVO.setAddedBy(TTKCommon.getUserSeqId(request));
            Long lngCount=preAuthSupportManagerObject.removeClaimIntimationDetail(claimIntimationVO);
            
            
            if(lngCount>0)
            {           	
                 request.setAttribute("updated","message.removed");
                 //request.getSession().setAttribute("claimIntimationVO",claimIntimationVO);
            }//end of if(lngCount>0)
            
            //formSuppDoc.initialize(mapping);
          //KOC 1339 for mail
           
            request.getSession().setAttribute("claimIntimationVO",claimIntimationVO);
            formSuppDoc.set("frmChanged","changed");
            return this.getForward(strClaimsSupportDoc, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doClearClaimIntimation(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	/**
	 * This method is used to Change the Shortfall Type to Medical, Insurance or MissiedDocuments.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeShortFallType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside SupportDocumentAction doChangeShortFallType");
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strShortFall="";
			DynaActionForm formSuppDoc= (DynaActionForm) form;
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			if(strActiveLink.equals(strPreAuthorization))
			{
				strShortFall=strPreauthShortFall;
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				strShortFall=strClaimsShortFall;
			}//end of else if(strActiveLink.equals(strClaims))
			Document missingDocs = null;
			if(request.getParameter("shortfallTypeID")!=null && ((String)
																request.getParameter("shortfallTypeID")).equals("INM"))
			{
				missingDocs = preAuthSupportManagerObject.selectMissingDocs(
										ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
				request.setAttribute("shortFallDoc",missingDocs);
			}//end of if
			else{
				request.setAttribute("shortFallDoc",request.getAttribute("shortFallDoc"));
			}//end of else
			request.setAttribute("shortfallSeqID",request.getAttribute("shortfallSeqID"));
			request.setAttribute("shortfalltype",formSuppDoc.getString("shortfalltype"));
			formSuppDoc.set("reasonYN",formSuppDoc.getString("reasonYN"));
			formSuppDoc.set("frmChanged","changed");
			return this.getForward(strShortFall, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doChangeShortFallType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

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
			setLinks(request);
			//log.debug("Inside SupportDocAction doDeleteList");
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strSupportDoc="";
			if(strActiveLink.equals(strPreAuthorization))
			{
				strSupportDoc=strPreauthSupportDoc;		// Pre-Authorization Flow
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				strSupportDoc=strClaimsSupportDoc;		// Claims Flow
			}//end of else if(strActiveLink.equals(strClaims))
			// Building the caption
			DynaActionForm formSuppDoc= (DynaActionForm) form;
			formSuppDoc.set("frmChanged",null);
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			PreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			ClaimManager claimManagerObject=this.getClaimManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			StringBuffer sbfDeleteId = new StringBuffer("|");
			//populate the delete string which contains the sequence id's to be deleted
			sbfDeleteId.append(populateDeleteId(request, (TableData)
														request.getSession().getAttribute("tableData"),formSuppDoc));
			ArrayList<Object> alDelete = new ArrayList<Object>();
			int iCount = 0;
			if(formSuppDoc.get("documentType").equals("SRT"))
			{
				alDelete.add("SFL");
			}//end of if(formSuppDoc.get("documentType").equals("SRT"))
			else if(formSuppDoc.get("documentType").equals("IVN"))
			{
				alDelete.add("INV");
			}//end of else if(formSuppDoc.get("documentType").equals("IVN"))
			else if(formSuppDoc.get("documentType").equals("BUF"))
			{
				alDelete.add("BUFFER");
			}//end of else if(formSuppDoc.get("documentType").equals("BUF"))
			else if(formSuppDoc.get("documentType").equals("DCV"))
			{
				alDelete.add("VOUCHER");
			}//end of else if(formSuppDoc.get("documentType").equals("DCV"))
			alDelete.add(sbfDeleteId.append("|").toString());
			if(strActiveLink.equals(strPreAuthorization))
			{
				alDelete.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
				alDelete.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				alDelete.add(TTKCommon.getUserSeqId(request));
				//delete the Support Documents
				iCount = preAuthManagerObject.deletePATGeneral(alDelete);
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				alDelete.add(ClaimsWebBoardHelper.getEnrollDetailSeqId(request));
				alDelete.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				alDelete.add(TTKCommon.getUserSeqId(request));
				//delete the Support Documents
				iCount = claimManagerObject.deleteClaimGeneral(alDelete);
			}//end of else if(strActiveLink.equals(strClaims))
			
			//refresh the grid with search data in session
			ArrayList alSupportDoc = null;
			//fetch the data from previous set of rowcounts, if all the records are deleted for the current
			//set of search criteria
			if(iCount == tableData.getData().size())
			{
				tableData.modifySearchData("DeleteList");//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(
																				tableData.getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					if(formSuppDoc.get("documentType").equals("BUF"))
					{
						alSupportDoc= preAuthSupportManagerObject.getSupportBufferList(tableData.getSearchData());
					}// end of if(formSuppDoc.get("documentType").equals("BUF"))
					else if(formSuppDoc.get("documentType").equals("DCV"))
					{
						alSupportDoc= preAuthSupportManagerObject.getDischargeVoucherList(tableData.getSearchData());
					}// end of else if(formSuppDoc.get("documentType").equals("DCV"))
					else
					{
						alSupportDoc= preAuthSupportManagerObject.getSupportDocumentList(tableData.getSearchData());
					}//end of else
				}//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				if(formSuppDoc.get("documentType").equals("BUF"))
				{
					alSupportDoc= preAuthSupportManagerObject.getSupportBufferList(tableData.getSearchData());
				}// end of if(formSuppDoc.get("documentType").equals("BUF"))
				else if(formSuppDoc.get("documentType").equals("DCV"))
				{
					alSupportDoc= preAuthSupportManagerObject.getDischargeVoucherList(tableData.getSearchData());
				}// end of else if(formSuppDoc.get("documentType").equals("DCV"))
				else
				{
					alSupportDoc= preAuthSupportManagerObject.getSupportDocumentList(tableData.getSearchData());
				}//end of else
			}//end of else
			tableData.setData(alSupportDoc, "DeleteList");
			//set the support document data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strSupportDoc, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
			setLinks(request);
			//log.debug("Inside SupportDocAction doDeleteList");
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strSupportDoc="";
			if(strActiveLink.equals(strPreAuthorization))
			{
				strSupportDoc=strPreauthSupportDoc;		// Pre-Authorization Flow
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				strSupportDoc=strClaimsSupportDoc;		// Claims Flow
			}//end of else if(strActiveLink.equals(strClaims))
			// Building the caption
			DynaActionForm formSuppDoc= (DynaActionForm) form;
			formSuppDoc.set("frmChanged",null);
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			PreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			ClaimManager claimManagerObject=this.getClaimManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			StringBuffer sbfDeleteId = new StringBuffer("|");
			//populate the delete string which contains the sequence id's to be deleted
			if(formSuppDoc.get("documentType").equals("BUF"))
			{
				sbfDeleteId.append(String.valueOf(((BufferVO)((TableData)
						request.getSession().getAttribute("tableData")).getData().get(Integer.parseInt(
											request.getParameter("rownum")))).getBufferDtlSeqID()));
			}//end of if(formSuppDoc.get("documentType").equals("BUF"))
			else
			{
				sbfDeleteId.append(String.valueOf(((SupportVO)((TableData)
									request.getSession().getAttribute("tableData")).getData().get(Integer.parseInt(
															request.getParameter("rownum")))).getSeqID()));
			}//end of else
			ArrayList<Object> alDelete = new ArrayList<Object>();
			int iCount = 0;
			if(formSuppDoc.get("documentType").equals("SRT"))
			{
				alDelete.add("SFL");
			}//end of if(formSuppDoc.get("documentType").equals("SRT"))
			else if(formSuppDoc.get("documentType").equals("IVN"))
			{
				alDelete.add("INV");
			}//end of else if(formSuppDoc.get("documentType").equals("IVN"))
			else if(formSuppDoc.get("documentType").equals("BUF"))
			{
				alDelete.add("BUFFER");
			}//end of else if(formSuppDoc.get("documentType").equals("BUF"))
			else if(formSuppDoc.get("documentType").equals("DCV"))
			{
				alDelete.add("VOUCHER");
			}//end of else if(formSuppDoc.get("documentType").equals("DCV"))
			alDelete.add(sbfDeleteId.append("|").toString());
			if(strActiveLink.equals(strPreAuthorization))
			{
				alDelete.add(PreAuthWebBoardHelper.getEnrollmentDetailId(request));
				alDelete.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				alDelete.add(TTKCommon.getUserSeqId(request));
				//delete the Support Documents
				iCount = preAuthManagerObject.deletePATGeneral(alDelete);
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				alDelete.add(ClaimsWebBoardHelper.getEnrollDetailSeqId(request));
				alDelete.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				alDelete.add(TTKCommon.getUserSeqId(request));
				//delete the Support Documents
				iCount = claimManagerObject.deleteClaimGeneral(alDelete);
			}//end of else if(strActiveLink.equals(strClaims))
			//refresh the grid with search data in session
			ArrayList alSupportDoc = null;
			//fetch the data from previous set of rowcounts, if all the records are deleted for the current set of
			//search criteria
			if(iCount == tableData.getData().size())
			{
				tableData.modifySearchData("Delete");//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(
																				tableData.getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					if(formSuppDoc.get("documentType").equals("BUF"))
					{
						alSupportDoc= preAuthSupportManagerObject.getSupportBufferList(tableData.getSearchData());
					}// end of if(formSuppDoc.get("documentType").equals("BUF"))
					else if(formSuppDoc.get("documentType").equals("DCV"))
					{
						alSupportDoc= preAuthSupportManagerObject.getDischargeVoucherList(tableData.getSearchData());
					}// end of else if(formSuppDoc.get("documentType").equals("DCV"))
					else
					{
						alSupportDoc= preAuthSupportManagerObject.getSupportDocumentList(tableData.getSearchData());
					}//end of else
				}//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				if(formSuppDoc.get("documentType").equals("BUF"))
				{
					alSupportDoc= preAuthSupportManagerObject.getSupportBufferList(tableData.getSearchData());
				}// end of if(formSuppDoc.get("documentType").equals("BUF"))
				else if(formSuppDoc.get("documentType").equals("DCV"))
				{
					alSupportDoc= preAuthSupportManagerObject.getDischargeVoucherList(tableData.getSearchData());
				}// end of else if(formSuppDoc.get("documentType").equals("DCV"))
				else
				{
					alSupportDoc= preAuthSupportManagerObject.getSupportDocumentList(tableData.getSearchData());
				}//end of else
			}//end of else
			tableData.setData(alSupportDoc, "Delete");
			//set the support document data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strSupportDoc, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doResetShortFallDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																		HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside SupportDocAction doResetShortFallDetails");
			String strReasonYN="";
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strShortFall="";
			if(strActiveLink.equals(strPreAuthorization))
			{
				strShortFall=strPreauthShortFall;
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				strShortFall=strClaimsShortFall;
			}//end of else if(strActiveLink.equals(strClaims))
			// Building the caption
			StringBuffer strCaption= new StringBuffer();
			String strEnrollmentID="";
			if(strActiveLink.equals(strPreAuthorization))
			{
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
								//PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
								//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strActiveLink.equals(strClaims))
			//SupportVO supportVO=null;
			DynaActionForm formSuppDoc= (DynaActionForm) form;
			formSuppDoc.set("frmChanged",null);
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			ShortfallVO shortfallVO=new ShortfallVO();
			DynaActionForm frmShortFall= (DynaActionForm)form;
			ArrayList<Object> alShortfallList = new ArrayList<Object>();
			//shortfallVO = (ShortfallVO)FormUtils.getFormValues(frmShortFall, this, mapping, request);
			if(frmShortFall.get("shortfallSeqID")!=null && !frmShortFall.get("shortfallSeqID").equals(""))
			{
				//alShortfallList.add(supportVO.getSeqID());
				alShortfallList.add(Long.parseLong(frmShortFall.getString("shortfallSeqID")));
				if(strActiveLink.equals(strPreAuthorization))
				{
					alShortfallList.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
					alShortfallList.add(null);
				}//end of if(strActiveLink.equals(strPreAuthorization))
				else if(strActiveLink.equals(strClaims))
				{
					alShortfallList.add(null);
					alShortfallList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				}//end of else if(strActiveLink.equals(strClaims))
				alShortfallList.add(TTKCommon.getUserSeqId(request));
				shortfallVO=preAuthSupportManagerObject.getShortfallDetail(alShortfallList);
				if(strActiveLink.equals(strPreAuthorization))
				{
					strCaption.append("Shortfall Details - ").append("Edit").append(" [").append(
						PreAuthWebBoardHelper.getClaimantName(request)).append("][").append(
							PreAuthWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
				}//end of if(strActiveLink.equals(strPreAuthorization))
				else if(strActiveLink.equals(strClaims))
				{
					strCaption.append("Shortfall Details - ").append("Edit").append(" [").append(
						ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
							ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
				}//end of else if(strActiveLink.equals(strClaims))
				//To display the Reason depending on the Status Type "close" and "overridden"
				if(shortfallVO.getStatusTypeID().equals("CLS") || shortfallVO.getStatusTypeID().equals("ORD"))
				{
					strReasonYN="Y";
				}//end of if(shortfallVO.getStatusTypeID().equals("CLS") || shortfallVO.getStatusTypeID().equals("ORD"))
				else
				{
					strReasonYN="N";
				}//end of else
			}//end of if(frmShortFall.get("shortfallSeqID")!=null && !frmShortFall.get("shortfallSeqID").equals(""))
			else
			{
				frmShortFall.initialize(mapping);
				shortfallVO=new ShortfallVO();
				if(strActiveLink.equals(strPreAuthorization))
				{
					strCaption.append("Shortfall Details - ").append("Add").append(" [").append(
						PreAuthWebBoardHelper.getClaimantName(request)).append("][").append(
							PreAuthWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
				}//end of if(strActiveLink.equals(strPreAuthorization))
				else if(strActiveLink.equals(strClaims))
				{
					strCaption.append("Shortfall Details - ").append("Add").append(" [").append(
						ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
							ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
				}//end of else if(strActiveLink.equals(strClaims))
				strReasonYN="N";
				shortfallVO.setEditYN("Y");
				shortfallVO.setStatusTypeID("OPN");
			}//end of else
			frmShortFall = (DynaActionForm)FormUtils.setFormValues("frmShortFall", shortfallVO, this, mapping, request);
			frmShortFall.set("caption",strCaption.toString());
			frmShortFall.set("reasonYN",strReasonYN);
			request.setAttribute("shortfalltype",shortfallVO.getShortfallDesc());
			request.setAttribute("shortfallTypeID",shortfallVO.getShortfallTypeID());

			//xml merging for shortfall questions
            String strShortFallType = shortfallVO.getShortfallDesc();
            Document missingDocs = null;
            if(shortfallVO.getShortfallTypeID()!=null && (shortfallVO.getShortfallTypeID()).equals("INM"))
			{
				missingDocs = preAuthSupportManagerObject.selectMissingDocs(
				ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
			}//end of if
            Document QueriesDoc =shortfallVO.getShortfallQuestions();
            Document MergeShortFallXml = RuleXMLHelper.MergeShortFallXml(QueriesDoc,missingDocs,strShortFallType,request);
            ArrayList alMergeShortFall = RuleXMLHelper.getSelectedNodes(QueriesDoc,strShortFallType);
            frmShortFall.set("shortfallQuestions",MergeShortFallXml);
            request.setAttribute("MergeShortFall",alMergeShortFall);
		    request.setAttribute("shortFallDoc",MergeShortFallXml);
		    
			request.getSession().setAttribute("frmShortFall",frmShortFall);
			request.setAttribute("shortfallSeqID",shortfallVO.getShortfallSeqID());
			this.documentViewer(request,shortfallVO);
			return this.getForward(strShortFall, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doResetShortFallDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doSaveDocChkList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
											HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
		//	log.debug("Inside SupportDocumentAction doSaveDocChkList");
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			DynaActionForm frmGeneral=(DynaActionForm)form;
			DocumentChecklistVO documentChecklistVO =null;
			ArrayList<Object> alSupportDocChkList = new ArrayList<Object>();
			String[] strSelectDocumentListType = (String[])frmGeneral.get("SelectDocumentListType");
			String[] strSelectCategoryTypeID = (String[])frmGeneral.get("SelectCategoryTypeID");
			String[] strSelectDocumentRcvdSeqID = (String[])frmGeneral.get("SelectDocumentRcvdSeqID");
			String[] strSheets = request.getParameterValues("sheets");
			String[] strDocumentTypeID = request.getParameterValues("DocTypeID");
			String[] strReasonTypeID = request.getParameterValues("selectedReasonTypeID");
			String[] strRemarks = request.getParameterValues("gridRemarks");
			String[] strDocumentRcvdYN = request.getParameterValues("selectedChkopt");
			String[] strDocumentName = request.getParameterValues("selectDocumentName");
			String[] strCategoryName = request.getParameterValues("selectCategoryName");
			for(int i=0 ;i<strSelectDocumentListType.length ;i++)
			{
				documentChecklistVO = new DocumentChecklistVO();
				if(strSelectDocumentRcvdSeqID[i]!=null && !strSelectDocumentRcvdSeqID[i].equals("")){
					documentChecklistVO.setDocumentRcvdSeqID(Long.parseLong(strSelectDocumentRcvdSeqID[i]));
				}//end of if(strSelectDocumentRcvdSeqID[i]!=null && !strSelectDocumentRcvdSeqID[i].equals(""))
				documentChecklistVO.setDocumentRcvdYN(strDocumentRcvdYN[i]);
				documentChecklistVO.setDocumentListType(strSelectDocumentListType[i]);
				documentChecklistVO.setCategoryTypeID(strSelectCategoryTypeID[i]);
				documentChecklistVO.setSheetsCount((strSheets[i]));
				documentChecklistVO.setDocumentTypeID(strDocumentTypeID[i]);

				if(TTKCommon.checkNull(strReasonTypeID[i])!=null && !TTKCommon.checkNull(strReasonTypeID[i]).equals(""))
				{
					documentChecklistVO.setReasonTypeID(strReasonTypeID[i]);
				}//end of if

				documentChecklistVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
				documentChecklistVO.setRemarks(TTKCommon.replaceSingleQots(strRemarks[i]));
				documentChecklistVO.setDocumentName(strDocumentName[i]);
				documentChecklistVO.setCategoryName(strCategoryName[i]);
				documentChecklistVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
				alSupportDocChkList.add(documentChecklistVO);
			}//end of for
			int iResult = preAuthSupportManagerObject.saveDocumentChecklist(alSupportDocChkList);
			if((iResult != 0))
			{
				//setting updated message to add and edit modes	appropriatly
				request.setAttribute("updated","message.savedSuccessfully");
			}//end of if(iResult!=0)
			alSupportDocChkList = preAuthSupportManagerObject.getDocumentChecklist(
										ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
			frmGeneral.set("alSupportDocChkList",alSupportDocChkList);
			request.getSession().setAttribute("frmSuppDoc",frmGeneral);
			return mapping.findForward("saveclaimssupportdoc");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doSaveDocChkList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doResetDocChkList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																		HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside SupportDocAction doResetDocChkList");
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			StringBuffer strCaption= new StringBuffer();
			String strEnrollmentID="";
			if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
					ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			{
				strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
			}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
							//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			DynaActionForm frmSuppDoc=(DynaActionForm)form;
			ArrayList alSupportDocChkList = preAuthSupportManagerObject.getDocumentChecklist(
										ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
			frmSuppDoc.set("alSupportDocChkList",alSupportDocChkList);
			strCaption.append("Document Checklist - [").append(
						ClaimsWebBoardHelper.getClaimantName(request)).append("]").append(strEnrollmentID);
			frmSuppDoc.set("caption",String.valueOf(strCaption));
			request.getSession().setAttribute("frmSuppDoc",frmSuppDoc);
			return this.getForward("docchklist", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doResetDocChkList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																		HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside SupportDocAction doClose");
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strSupportDoc="";
			String strEnrollmentID="";
			if(strActiveLink.equals(strPreAuthorization))
			{
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
								//PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
									//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strActiveLink.equals(strClaims))
			if(strActiveLink.equals(strPreAuthorization))
			{
				strSupportDoc=strPreauthSupportDoc;		// Pre-Authorization Flow
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				strSupportDoc=strClaimsSupportDoc;		// Claims Flow
			}//end of else if(strActiveLink.equals(strClaims))
			// Building the caption
			StringBuffer strCaption= new StringBuffer();
			DynaActionForm formSuppDoc= (DynaActionForm) form;
			formSuppDoc.set("frmChanged",null);
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			ArrayList alSupportDoc = null;
			if(tableData.getSearchData().size()>1)
			{
				if(formSuppDoc.get("documentType").equals("SRT"))
				{
					alSupportDoc= preAuthSupportManagerObject.getSupportDocumentList(tableData.getSearchData());
				}//end of if(formSuppDoc.get("documentType").equals("SRT"))
				else if(formSuppDoc.get("documentType").equals("DCV"))
				{
					alSupportDoc= preAuthSupportManagerObject.getDischargeVoucherList(tableData.getSearchData());
				}//end of if(formSuppDoc.get("documentType").equals("DCV"))
				tableData.setData(alSupportDoc, "search");
				if(formSuppDoc.get("documentType").equals("DCV"))
				{
					strCaption.append("List of Discharge Voucher - [").append(
						ClaimsWebBoardHelper.getClaimantName(request)).append("]").append(strEnrollmentID);
				}//end of if(formSuppDoc.get("documentType").equals("DCV"))
				else
				{
					if(strActiveLink.equals(strPreAuthorization))
					{
						strCaption.append("List of Support Documents - [").append(
							PreAuthWebBoardHelper.getClaimantName(request)).append("]").append(strEnrollmentID);
					}//end of if(strActiveLink.equals(strPreAuthorization))
					else if(strActiveLink.equals(strClaims))
					{
						strCaption.append("List of Support Documents - [").append(
							ClaimsWebBoardHelper.getClaimantName(request)).append("]").append(strEnrollmentID);
					}//end of else if(strActiveLink.equals(strClaims))
				}//end of else
				formSuppDoc.set("caption",String.valueOf(strCaption));
				//set the batch policy data object to session
				request.getSession().setAttribute("tableData",tableData);
			}//end of if(tableData.getSearchData().size()>1)
			return this.getForward(strSupportDoc, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to submit the shortfall.
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
			setLinks(request);
			//log.debug("Inside SupportDocAction doSubmit");
			DynaActionForm frmGeneral=(DynaActionForm)form;
			DocumentChecklistVO documentChecklistVO =null;
			ArrayList<Object> alSupportDocChkList = new ArrayList<Object>();
			ActionMessage actionMessage=null;
			ActionMessages actionMessages = new ActionMessages();
			boolean errSheets=false;
			boolean errNumericSheets=false;
			boolean errReason=false;
			String strExpNumeric = "^[0-9]*$";
			int iYNLength = 0;
			String[] strSelectDocumentListType = (String[])frmGeneral.get("SelectDocumentListType");
			String[] strSelectCategoryTypeID = (String[])frmGeneral.get("SelectCategoryTypeID");
			String[] strSelectDocumentRcvdSeqID = (String[])frmGeneral.get("SelectDocumentRcvdSeqID");
			String[] strSheets = request.getParameterValues("sheets");
			String[] strDocumentTypeID = request.getParameterValues("DocTypeID");
			String[] strReasonTypeID = request.getParameterValues("selectedReasonTypeID");
			String[] strRemarks = request.getParameterValues("gridRemarks");
			String[] strDocumentRcvdYN = request.getParameterValues("selectedChkopt");
			String[] strDocumentName = request.getParameterValues("selectDocumentName");
			String[] strCategoryName = request.getParameterValues("selectCategoryName");

			for(int i=0 ;i<strSelectDocumentListType.length ;i++)
			{
				documentChecklistVO = new DocumentChecklistVO();
				if(strSelectDocumentRcvdSeqID[i]!=null && !strSelectDocumentRcvdSeqID[i].equals("")){
					documentChecklistVO.setDocumentRcvdSeqID(Long.parseLong(strSelectDocumentRcvdSeqID[i]));
				}//end of if(strSelectDocumentRcvdSeqID[i]!=null && !strSelectDocumentRcvdSeqID[i].equals(""))
				documentChecklistVO.setDocumentRcvdYN(strDocumentRcvdYN[i]);
				documentChecklistVO.setDocumentListType(strSelectDocumentListType[i]);
				documentChecklistVO.setCategoryTypeID(strSelectCategoryTypeID[i]);
				//if(strSheets[i]!=null && !strSheets[i].equals(""))
				if(strDocumentRcvdYN[i].equals("Y"))
				{
					if(strSheets[i].equals(""))
					{
						errSheets=true;
					}//end of if(strSheets[i].equals(""))
					if(!strSheets[i].matches(strExpNumeric))
					{
						errNumericSheets=true;
					}//end of if(!strSheets[i].matches(strExpNumeric))
					if(strDocumentTypeID[i].equals("DUP")&&strReasonTypeID[i].equals(""))
					{
						errReason=true;
					}//end of if(strDocumentTypeID[i].equals("DUP")&&strReasonTypeID[i].equals(""))
					iYNLength++;
				}//end of if(strDocumentRcvdYN[i].equals("Y"))
				else if(strDocumentRcvdYN[i].equals("N"))
				{
					if(!strSheets[i].matches(strExpNumeric))
					{
						errNumericSheets=true;
					}//end of if(!strSheets[i].matches(strExpNumeric))
				}//end of else if(strDocumentRcvdYN[i].equals("N"))
				documentChecklistVO.setSheetsCount((strSheets[i]));
				documentChecklistVO.setDocumentTypeID(strDocumentTypeID[i]);
				if(TTKCommon.checkNull(strReasonTypeID[i])!=null&&!TTKCommon.checkNull(strReasonTypeID[i]).equals(""))
				{
					documentChecklistVO.setReasonTypeID(strReasonTypeID[i]);
				}//end of if

				documentChecklistVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
				documentChecklistVO.setRemarks(strRemarks[i]);
				documentChecklistVO.setDocumentName(strDocumentName[i]);
				documentChecklistVO.setCategoryName(strCategoryName[i]);
				documentChecklistVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
				alSupportDocChkList.add(documentChecklistVO);
			}//end of for
			frmGeneral.set("alSupportDocChkList",alSupportDocChkList);

			if(iYNLength < 1)
			{
				actionMessage = new ActionMessage("error.doclist");
				actionMessages.add("global.error",actionMessage);
				saveErrors(request,actionMessages);
				return mapping.findForward("docchklist");
			}//end of if(iYNLength < 1)
			if(errSheets)
			{
				actionMessage = new ActionMessage("error.sheet");
				actionMessages.add("global.error",actionMessage);
				saveErrors(request,actionMessages);
				return mapping.findForward("docchklist");
			}//end of if(errSheets)
			if(errNumericSheets)
			{
				actionMessage = new ActionMessage("error.numericsheet");
				actionMessages.add("global.error",actionMessage);
				saveErrors(request,actionMessages);
				return mapping.findForward("docchklist");
			}//end of if(errNumericSheets)
			if(errReason)
			{
				actionMessage = new ActionMessage("error.reason");
				actionMessages.add("global.error",actionMessage);
				saveErrors(request,actionMessages);
				return mapping.findForward("docchklist");
			}//end of if(errReason)
			return mapping.findForward(strSubmit);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doSubmit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doAddDischargeVoucher(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside SupportDocAction doAddDischargeVoucher");
			// Building the caption
			StringBuffer strCaption= new StringBuffer();
			String strEnrollmentID="";
			if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
					ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			{
				strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
			}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
								//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			ShortfallVO shortfallVO=null;
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			DynaActionForm frmShortFall= (DynaActionForm)form;
			frmShortFall.initialize(mapping);
			shortfallVO=new ShortfallVO();
			strCaption.append("Discharge Voucher Details - ").append("Add").append(" [").append(
				ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
					ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
			frmShortFall= (DynaActionForm)FormUtils.setFormValues("frmShortFall", shortfallVO, this, mapping, request);
			frmShortFall.set("caption",String.valueOf(strCaption));
			frmShortFall.set("statusTypeID","DVT");
			request.getSession().setAttribute("frmShortFall",frmShortFall);
			preAuthSupportManagerObject.getDischargeVoucherRequired(ClaimsWebBoardHelper.getClaimsSeqId(request));
			return this.getForward(strClaimsDischarge, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doAddEditDischargeVoucher(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

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
	public ActionForward doViewDischargeVoucher(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside SupportDocAction doAddDischargeVoucher");
			// Building the caption
			StringBuffer strCaption= new StringBuffer();
			String strEnrollmentID="";
			if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
					ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			{
				strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
			}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
								//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			SupportVO supportVO=null;
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			ShortfallVO shortfallVO=null;
			DynaActionForm frmShortFall= (DynaActionForm)form;
			supportVO = (SupportVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
			shortfallVO=preAuthSupportManagerObject.getDischargeVoucherDetail(supportVO.getSeqID(),
																				TTKCommon.getUserSeqId(request));
			strCaption.append("Discharge Voucher Details - ").append("Edit").append(" [").append(
				ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
					ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
			frmShortFall= (DynaActionForm)FormUtils.setFormValues("frmShortFall", shortfallVO, this, mapping, request);
			frmShortFall.set("caption",String.valueOf(strCaption));
			request.getSession().setAttribute("frmShortFall",frmShortFall);
			return this.getForward(strClaimsDischarge, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doViewDischargeVoucher(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doSaveDischargeVoucher(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside SupportDocAction doSaveDischargeVoucher");
			StringBuffer strCaption= new StringBuffer();
			String strEnrollmentID="";
			if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
					ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			{
				strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
			}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
								//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			ShortfallVO shortfallVO=null;
			DynaActionForm frmShortFall= (DynaActionForm)form;
			shortfallVO = (ShortfallVO)FormUtils.getFormValues(frmShortFall, this, mapping, request);
			shortfallVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
			shortfallVO.setUpdatedBy(TTKCommon.getUserSeqId(request));	//User Id
			Long lCount=preAuthSupportManagerObject.saveDischargeVoucherDetail(shortfallVO);
			if(lCount>0)
			{
				if(shortfallVO.getShortfallSeqID()!=null)
				{
					request.setAttribute("updated","message.savedSuccessfully");
					strCaption.append("Discharge Voucher Details - ").append("Edit").append(" [").append(
							ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
									ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
				}//end of if(shortfallVO.getShortfallSeqID()!=null)
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
					strCaption.append("Discharge Voucher Details - ").append("Add").append(" [").append(
							ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
									ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
					shortfallVO = new ShortfallVO();
					frmShortFall.initialize(mapping);
				}//end of else
			}//end of if(lCount>0)
			shortfallVO=preAuthSupportManagerObject.getDischargeVoucherDetail(lCount, TTKCommon.getUserSeqId(request));
			frmShortFall = (DynaActionForm)FormUtils.setFormValues("frmShortFall", shortfallVO, this, mapping, request);
			frmShortFall.set("caption",strCaption.toString());
			request.getSession().setAttribute("frmShortFall",frmShortFall);
			return this.getForward(strClaimsDischarge, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doSaveDischargeVoucher(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doResetDischargeVoucher(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			//log.debug("Inside SupportDocAction doResetDischargeVoucher");
			StringBuffer strCaption= new StringBuffer();
			String strEnrollmentID="";
			if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
					ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			{
				strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
			}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
							//ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			//SupportVO supportVO=null;
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			ShortfallVO shortfallVO=new ShortfallVO();
			DynaActionForm frmShortFall= (DynaActionForm)form;
			if(frmShortFall.get("shortfallSeqID")!=null && !frmShortFall.get("shortfallSeqID").equals(""))
			{
				shortfallVO=preAuthSupportManagerObject.getDischargeVoucherDetail(Long.parseLong(
						         frmShortFall.getString("shortfallSeqID")),TTKCommon.getUserSeqId(request));
				strCaption.append("Discharge Voucher Details - ").append("Edit").append(" [").append(
					ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
						ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
			}//end of if(frmShortFall.get("dchrgVoucherSeqID")!=null&&!frmShortFall.get("dchrgVoucherSeqID").equals(""))
			else
			{
				frmShortFall.initialize(mapping);
				shortfallVO=new ShortfallVO();
				strCaption.append("Discharge Voucher Details - ").append("Add").append(" [").append(
						ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
								ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]").append(strEnrollmentID);
			}//end of else
			frmShortFall = (DynaActionForm)FormUtils.setFormValues("frmShortFall", shortfallVO, this, mapping, request);
			frmShortFall.set("caption",strCaption.toString());
			request.getSession().setAttribute("frmShortFall",frmShortFall);
			return this.getForward(strClaimsDischarge, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strSupportError));
		}//end of catch(Exception exp)
	}//end of doResetDischargeVoucher(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	/**
	 * this method will get the selectd quries of the document.
	 * @param formSuppDoc formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return Document contains mearged Document.
	 * @throws TTKException
	 */
	private Document getQueriesDoc(HttpServletRequest request,DynaActionForm formSuppDoc) throws TTKException
	{
		String strShortFallType = formSuppDoc.getString("shortfalltype");
		Enumeration par=request.getParameterNames();
		ArrayList<Object> alParameter = new ArrayList<Object>();
		while(par.hasMoreElements())
		{
			alParameter.add(par.nextElement().toString());
		}//end of while
		Object strParameters[]=alParameter.toArray();
		Document baseDoc=null;
		Document modifiedDoc = null;
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// Now use the factory to create a DOM parser.
			DocumentBuilder parser = factory.newDocumentBuilder();
			//Dom reader object
			DOMReader reader=new DOMReader();
			//parse the xml file
			if(request.getParameter("shortfallTypeID")!=null && ((String)
															request.getParameter("shortfallTypeID")).equals("INM"))
			{
				PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
				baseDoc = preAuthSupportManagerObject.selectMissingDocs(ClaimsWebBoardHelper.getClaimsSeqId(request),
																					TTKCommon.getUserSeqId(request));
			}//end of if
			else{
				if(TTKCommon.getActiveLink(request).equals("Pre-Authorization")){
					baseDoc=reader.read(parser.parse(new FileInputStream(new File("shortfallqueries.xml"))));
				}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
				else if(TTKCommon.getActiveLink(request).equals("Claims")){
					baseDoc=reader.read(parser.parse(new FileInputStream(new File("claimshortfallqueries.xml"))));
				}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
			}//end of else


			modifiedDoc = RuleXMLHelper.removeElement(baseDoc,strParameters,request);
			//modifiedDoc = RuleXMLHelper.mergeXML(modifiedDoc);
		}//end of try
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch(Exception exp)
		List listTitle = baseDoc.selectNodes("/shortfall/section[@name='"+
									strShortFallType+"']/subsection | /shortfall/section[@name='Others']/subsection");
		for(int iTitle=0;iTitle<listTitle.size();iTitle++)
		{
			Element element = (Element)listTitle.get(iTitle);

			List listQueries = element.selectNodes("*");
			for(int iQuerie=0;iQuerie<listQueries.size();iQuerie++)
			{
				Element elementquerie = (Element)listQueries.get(iQuerie);
				//log.debug("elementquerie value is :"+elementquerie);
			}//end of for
		}//end of for(iQuerie=0;iQuerie;iQuerie<listQueries.size();iQuerie++)
		return modifiedDoc;
	}//end of populateSearchCriteria(HttpServletRequest request)

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmSupportDocList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 * @throws TTKException
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSupportDocList,HttpServletRequest request)
																								throws TTKException
	{
		//build the column names along with their values to be searched
		String strActiveLink=TTKCommon.getActiveLink(request);
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		if(frmSupportDocList.get("documentType").equals("BUF"))
		{
			if(strActiveLink.equals(strPreAuthorization))
			{
				alSearchParams.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				alSearchParams.add(TTKCommon.getUserSeqId(request));
				alSearchParams.add("PAT");
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else
			{
				alSearchParams.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				alSearchParams.add(TTKCommon.getUserSeqId(request));
				alSearchParams.add("CLM");
			}//end of else
		}//end of if(frmSupportDocList.get("documentType").equals("BUF"))
		if(frmSupportDocList.get("documentType").equals("IVN"))
		{
			if(strActiveLink.equals(strPreAuthorization))
			{
				alSearchParams.add("IVN");
				alSearchParams.add("PAT");
				alSearchParams.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				alSearchParams.add(TTKCommon.getUserSeqId(request));
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else
			{
				alSearchParams.add("IVN");
				alSearchParams.add("CLM");
				alSearchParams.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				alSearchParams.add(TTKCommon.getUserSeqId(request));
			}//end of else
		}//end of if(frmSupportDocList.get("documentType").equals("IVN"))
		else if(frmSupportDocList.get("documentType").equals("DCV"))
		{
			alSearchParams.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
			alSearchParams.add(TTKCommon.getUserSeqId(request));
		}//end of else if(frmSupportDocList.get("documentType").equals("DCV"))
		else if(frmSupportDocList.get("documentType").equals("SRT"))
		{
			alSearchParams.add(frmSupportDocList.get("documentType"));
			if(strActiveLink.equals(strPreAuthorization))
			{
				alSearchParams.add("PAT");
				alSearchParams.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				alSearchParams.add("CLM");
				alSearchParams.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
			}//end of else if(strActiveLink.equals(strClaims))
			alSearchParams.add(TTKCommon.getUserSeqId(request));
		}//end of else if(frmSupportDocList.get("documentType").equals("SRT"))
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmSupportDocList,HttpServletRequest request)

	/**
	 * This menthod for document viewer information
	 * @param request HttpServletRequest object which contains hospital information.
	 * @param policyDetailVO PolicyDetailVO object which contains policy information.
	 * @exception throws TTKException
	 */
	private void documentViewer(HttpServletRequest request, ShortfallVO shortfallVO) throws TTKException
	{
		ArrayList<String> alDocviewParams = new ArrayList<String>();
		alDocviewParams.add("leftlink="+TTKCommon.getActiveLink(request));
		alDocviewParams.add("shortfall_id="+shortfallVO.getShortfallNo());
		alDocviewParams.add("pre_auth_number="+TTKCommon.getWebBoardDesc(request));
		if(request.getSession().getAttribute("toolbar")!=null)
			((Toolbar)request.getSession().getAttribute("toolbar")).setDocViewParams(alDocviewParams);
	}//end of documentViewer(HttpServletRequest request, PolicyDetailVO policyDetailVO)

	/**
	 * Returns a string which contains the comma separated sequence id's to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the comma separated sequence id's to be deleted
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableData, DynaActionForm formSuppDoc)
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			//loop through to populate delete sequence id's and get the value from session for the matching check
			//box value
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						if(formSuppDoc.get("documentType").equals("BUF"))
						{
							sbfDeleteId.append(((BufferVO)tableData.getData().get(
													Integer.parseInt(strChk[i]))).getBufferDtlSeqID().intValue());
						}//end of if(formSuppDoc.get("documentType").equals("BUF"))
						else
						{
							sbfDeleteId.append(((SupportVO)tableData.getData().get(
													Integer.parseInt(strChk[i]))).getSeqID().intValue());
						}//end of else
					}//end of if(i == 0)
					else
					{
						if(formSuppDoc.get("documentType").equals("BUF"))
						{
							sbfDeleteId.append("|").append(((BufferVO)tableData.getData().get(
													Integer.parseInt(strChk[i]))).getBufferDtlSeqID().intValue());
						}//end of if(formSuppDoc.get("documentType").equals("BUF"))
						else
						{
							sbfDeleteId.append("|").append(((SupportVO)tableData.getData().get(
													Integer.parseInt(strChk[i]))).getSeqID().intValue());
						}//end of else
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
		}//end of if(strChk!=null&&strChk.length!=0)
		//log.debug("DELETE IDS !!!!!!! "+sbfDeleteId.toString());
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData, DynaActionForm formSuppDoc)

	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthSupportManager getPreAuthSupportManagerObject() throws TTKException
	{
		PreAuthSupportManager preAuthSupportManager = null;
		try
		{
			if(preAuthSupportManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthSupportManager = (PreAuthSupportManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthSupportManagerBean!com.ttk.business.preauth.PreAuthSupportManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strSupportError);
		}//end of catch
		return preAuthSupportManager;
	}//end getPreAuthSupportManagerObject()

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
			}//end of if(preAuthManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strSupportError);
		}//end of catch
		return preAuthManager;
	}//end getPreAuthManagerObject()

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
			throw new TTKException(exp, strSupportError);
		}//end of catch
		return claimManager;
	}//end getClaimManagerObject()
	
	/**
	 * Returns the CommunicationManager session object for invoking methods on it.
	 * @return CommunicationManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	/*private CommunicationManager getCommunicationManagerObject() throws TTKException
	{
		CommunicationManager communicationManager = null;
		try
		{
			if(communicationManager == null)
			{
				InitialContext ctx = new InitialContext();
				communicationManager = (CommunicationManager) ctx.lookup(CommunicationManager.class.getName());
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strSupportError);
		}//end of catch
		return communicationManager;
	}//end of getCommunicationManagerObject()
*/
}// end of SupportDocAction