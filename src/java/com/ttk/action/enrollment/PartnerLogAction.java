/**
 * @ (#) PartnerLogAction.java Feb 15, 2006
 * Project      : TTK HealthCare Services
 * File         : PartnerLogAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Feb 15, 2006
 *
 * @author      :  Chandrasekaran J
 * Modified by  :
 * Modified date:
 * Reason       :
 */

package com.ttk.action.enrollment;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
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
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.business.empanelment.LogManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.CodingClaimsWebBoardHelper;
import com.ttk.common.CodingWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.WebConfigLinkVO;
import com.ttk.dto.empanelment.LogVO;
import com.ttk.dto.enrollment.PolicyDetailVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is reusable for showing alert information  Hospital,individual policy,individual policy as group,
 * corporate policies and non corporate policies in Empanlement,Endoresement, Pre-Auth, Claims flows.
 */
public class PartnerLogAction extends TTKAction
{
	private static Logger log = Logger.getLogger(PartnerLogAction.class);
	//string for forwarding
	private static final String strHospLogList="hosploglist";
	
	private static final String strPtnrLogList="ptnrloglist";
	private static final String strIndLogList="indloglist";
	private static final String strGrpLogList="grouploglist";
	private static final String strCorLogList="corloglist";
	private static final String strNonCorLogList="noncorloglist";
	private static final String strPreAuthLogList="preauthloglist";
	private static final String strClaimsAlertList="claimsalertlist";
	private static final String strDataEntryClaimsAlertList="dataentryclaimsalertlist";
	private static final String strCodingPreAuthAlert="codingpreauthalert";
	private static final String strCodingClaimsAlert="codingclaimsalert";
	private static final String strDataEntryCodingClaimsAlert="dataentrycodingclaimsalert";
	private static final String strCodingCleanupPreAuthAlert="codingcleanuppreauthalert";
	private static final String strCodingCleanupClaimsAlert="codingcleanupclaimsalert";
	
	//string for comparision
	private static final String strEnrollment="Enrollment";
	private static final String strEmpanelment="Empanelment";
	private static final String strPreAuthorization="Pre-Authorization";
	private static final String strClaims="Claims";
	private static final String strDataEntryClaims="DataEntryClaims"; //ParallelAlert
	private static final String strHospital="Hospital";
	private static final String strPartner="Partner";
	private static final String strIndPolicy="Individual Policy";
	private static final String strGrpPolicy="Ind. Policy as Group";
	private static final String strCorPolicy="Corporate Policy";
	private static final String strNonCorPolicy="Non-Corporate Policy";
	private static final String strProcessing="Processing";
	private static final String strEnm="ENM";
	private static final String strEnd="END";
	private static final String strCoding="Coding";
	private static final String strDataEntryCoding="DataEntryCoding";
	private static final String strCodeCleanup="Code CleanUp";
	private static final String strPreAuth="PreAuth";
	private static final String strClaimsCoding="Claims";
	
	private static final String strBrowseList="browselist";
	//  Exception Message Identifier
	private static final String strWebconfig="webconfig";
	private static final String strLinkDetailsList="linklist";

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
			log.debug("Inside PartnerLogAction doDefault");
			log.info("Inside PartnerLogAction doDefault");
			((DynaActionForm)form).initialize(mapping);//reset the form data
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strLink=TTKCommon.getActiveLink(request);
			if(strLink.equals(strEnrollment))
			{
				handleSetLink(request);
				DynaActionForm frmPolicyList=(DynaActionForm)form;
				frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
				String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
				if(WebBoardHelper.checkWebBoardId(request)==null)
				{
					TTKException expTTK = new TTKException();
					if(strSwitchType.equals(strEnm))
					{
						expTTK.setMessage("error.enrollment.required");
					}//end of if(strSwitchType.equals(strEnm))
					else
					{
						expTTK.setMessage("error.endorsement.required");
					}//end of else
					throw expTTK;
				}//end of if(WebBoardHelper.checkWebBoardId(request)==null)
				
			}//end of if(strLink.equals(strEnrollment))
			else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital))
			{
				this.setLinks(request);
				if(TTKCommon.getWebBoardId(request)==null)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.hospital.required");
					throw expTTK;
				}//end of if(TTKCommon.getWebBoardId(request)==null)
			}//end of else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital))
			else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strPartner))
			{
				this.setLinks(request);
				if(TTKCommon.getWebBoardId(request)==null)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.partner.required");
					throw expTTK;
				}//end of if(TTKCommon.getWebBoardId(request)==null)
			}//end of else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strPartner))
			else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
			{
				log.debug("going to call setlinks in pre-auth log");
				this.setLinks(request);
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
			}//end of else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
			else if((strLink.equals(strClaims)) || (strLink.equals(strDataEntryClaims)))
			{
				log.debug("going to call setlinks in claims alert");				
				this.setLinks(request);
				log.info("going to call setlinks in Claim and DataEntryclaims alert");
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
			}//end of else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
			else if((strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))||(strLink.equals(strCodeCleanup)&& strSubLink.equals(strPreAuth)))
			{
				log.debug("going to call setlinks in coding preauth alert");
				this.setLinks(request);
				if(CodingWebBoardHelper.checkWebBoardId(request)==null)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.PreAuthorization.required");
					throw expTTK;
				}//end of else if(strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))
			}//end of else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))//ParallelAlert
			else if(((strLink.equals(strCoding) || strLink.equals(strDataEntryCoding))&& strSubLink.equals(strClaimsCoding))||(strLink.equals(strCodeCleanup)&& strSubLink.equals(strClaimsCoding)))
			{
				log.debug("going to call setlinks in coding claims alert");
				this.setLinks(request);
				if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Claims.required");
					throw expTTK;
				}//end of if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
			}//end of else if(strLink.equals(strCoding)&& strSubLink.equals(strClaimsCoding))
			DynaActionForm frmPartnerLog=(DynaActionForm)form;
			String strLog=getForwardPath(request);
			StringBuffer strCaption=buildCaption(request);
			//create new table data object
			TableData tableDataLog = new TableData();
			//create the required grid table
			tableDataLog.createTableInfo("PartnerLogTable",new ArrayList());
			if(strLink.equals(strEnrollment))
			{
				((Column)((ArrayList)tableDataLog.getTitle()).get(1)).setVisibility(false);
			}//end of if(strLink.equals(strEnrollment))
			request.getSession().setAttribute("tableDataLog",tableDataLog);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			frmPartnerLog.set("caption",String.valueOf(strCaption));
			return this.getForward(strLog, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"log"));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
		try{
			log.debug("Inside PartnerLogAction doChangeWebBoard");

			return doDefault( mapping, form, request, response);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"log"));
		}//end of catch(Exception exp)
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)


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
			log.debug("Inside PartnerLogAction doSearch");
			handleSetLink(request);
			ArrayList alLogList=null;
			DynaActionForm frmPartnerLog=(DynaActionForm)form;
			LogManager logManagerObj=this.getLogManagerObject();
			String strLog=getForwardPath(request);
			//get the table data from session if exists
			TableData tableDataLog=null;
			if((request.getSession()).getAttribute("tableDataLog") == null)
			{
				tableDataLog = new TableData();
			}// end of if((request.getSession()).getAttribute("tableDataLog") == null)
			else
			{
				tableDataLog = (TableData)(request.getSession()).getAttribute("tableDataLog");
			}// end of else
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strLink=TTKCommon.getActiveLink(request);
			String strSwitchType="";
			if(strLink.equals(strEnrollment))
			{
				DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
				strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			}//end of if(strLink.equals(strEnrollment))
			Long lngSeqId=getSeqId(request,strSwitchType);
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strSortID.equals(""))
			{
				tableDataLog.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableDataLog.modifySearchData("sort");//modify the search data
			}// end of if(!strSortID.equals(""))
			else
			{
				tableDataLog.createTableInfo("PartnerLogTable",null);
				tableDataLog.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,
																		strSwitchType,lngSeqId));
				tableDataLog.setSortData("0001");
				tableDataLog.setSortOrder("DESC");
				tableDataLog.modifySearchData("search");
			}// end of else
			if(strLink.equals(strEnrollment))
			{
				((Column)((ArrayList)tableDataLog.getTitle()).get(1)).setVisibility(false);
				alLogList=logManagerObj.getPolicyLogList(tableDataLog.getSearchData());
			}// end of if(strLink.equals(strEnrollment))
			else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital))
			{
				alLogList=logManagerObj.getLogList(tableDataLog.getSearchData());
			}// end of  else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital))
			else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strPartner))
			{
				alLogList=logManagerObj.getLogList(tableDataLog.getSearchData());
			}// end of  else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital))
			else if((strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))||strLink.equals(strClaims)||strLink.equals(strDataEntryClaims)||
				(strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))||((strLink.equals(strCoding) || strLink.equals(strDataEntryCoding))&& strSubLink.equals(strClaimsCoding))||
				(strLink.equals(strCodeCleanup)&& strSubLink.equals(strPreAuth))||(strLink.equals(strCodeCleanup)&& strSubLink.equals(strClaimsCoding)))
			{
				alLogList=logManagerObj.getPreAuthLogList(tableDataLog.getSearchData());
			}// end of else if((strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))||strLink.equals(strClaims)||
													//(strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))||(
														//strLink.equals(strCoding)&& strSubLink.equals(strClaimsCoding)))
			tableDataLog.setData(alLogList,"search");
			request.getSession().setAttribute("tableDataLog",tableDataLog);
			StringBuffer strCaption=buildCaption(request);
			frmPartnerLog.set("caption",String.valueOf(strCaption));
			return this.getForward(strLog, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"log"));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
									HttpServletResponse response) throws Exception
	{
		try{
			log.debug("Inside PartnerLogAction doSave");
			handleSetLink(request);
			int iUpdate=0;
			DynaActionForm frmPartnerLog=(DynaActionForm)form;
			ArrayList alLogList=null;
			//get the table data from session if exists
			TableData tableDataLog=null;
			if((request.getSession()).getAttribute("tableDataLog") == null)
			{
				tableDataLog = new TableData();
			}// end of if((request.getSession()).getAttribute("tableDataLog") == null)
			else
			{
				tableDataLog = (TableData)(request.getSession()).getAttribute("tableDataLog");
			}// end of else
			LogManager logManagerObj=this.getLogManagerObject();
			LogVO logVO=new LogVO();
			DynaActionForm frmSaveLog = (DynaActionForm)form;
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strLink=TTKCommon.getActiveLink(request);
			String strSwitchType="";
			if(strLink.equals(strEnrollment))
			{
				DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
				strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			}//end of if(strLink.equals(strEnrollment))
			Long lngSeqId=getSeqId(request,strSwitchType);
			logVO=(LogVO)FormUtils.getFormValues(frmSaveLog, this, mapping, request);
			String strLog=getForwardPath(request);
			if(strLink.equals(strEnrollment))
			{
				if(strSwitchType.equals(strEnd))
				{
					logVO.setEndorsementSeqID(lngSeqId);
				}// end of if(strSwitchType.equals(strEnd))
				else
				{
					logVO.setPolicySeqID(lngSeqId);
				}//end of else
				logVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
				iUpdate=logManagerObj.addPolicyLog(logVO);
			}// end of if(strLink.equals(strEnrollment))
			else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital))
			{
				logVO.setHospSeqId(lngSeqId);//got the value from session
				logVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
				iUpdate=logManagerObj.addLog(logVO);
			}//end of else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital))
			else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strPartner))
			{
				logVO.setHospSeqId(lngSeqId);//got the value from session
				logVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			//	iUpdate=logManagerObj.addLog(logVO);
				iUpdate=logManagerObj.addPartnerLog(logVO);
			}//end of else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital))
			else if((strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))||
										(strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))||
										(strLink.equals(strCodeCleanup)&& strSubLink.equals(strPreAuth)))
			{
				logVO.setSeqID(lngSeqId);
				logVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
				iUpdate=logManagerObj.addPreAuthLog(logVO,strLink);
			}// end of else if((strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))||
												//(strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))) //ParallelAlert
			else if((strLink.equals(strClaims))|| (strLink.equals(strDataEntryClaims))|| ((strLink.equals(strCoding) || strLink.equals(strDataEntryCoding))&& strSubLink.equals(strClaimsCoding))||
					(strLink.equals(strCodeCleanup)&& strSubLink.equals(strClaimsCoding)))
			{
				logVO.setClaimSeqID(lngSeqId);
				logVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
				iUpdate=logManagerObj.addPreAuthLog(logVO,strLink);
			}// else if((strLink.equals(strClaims))||(strLink.equals(strCoding)&& strSubLink.equals(strClaimsCoding)))
			if(iUpdate >0)
			{
				//initialize the formbean after adding
				frmSaveLog.initialize(mapping);
				request.setAttribute("updated","message.addedSuccessfully");
				tableDataLog.setSortData("0001");
				tableDataLog.setSortOrder("DESC");
				if(strLink.equals(strEnrollment))
				{
					if(tableDataLog.getSearchData().size()>0)
					{
						//refresh the data in order to get the new records if any.
						alLogList=logManagerObj.getPolicyLogList(tableDataLog.getSearchData());
					}// end of if(tableDataLog.getSearchData().size()>0)
				}// end of if(strLink.equals(strEnrollment))
				else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital))
				{
					if(tableDataLog.getSearchData().size()>0)
					{
						//refresh the data in order to get the new records if any.
						alLogList=logManagerObj.getLogList(tableDataLog.getSearchData());
					}//end of if(tableDataLog.getSearchData().size()>0)
				}// end of  else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital)) //ParallelAlert
				else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strPartner))
				{
					if(tableDataLog.getSearchData().size()>0)
					{
						//refresh the data in order to get the new records if any.
						alLogList=logManagerObj.getLogList(tableDataLog.getSearchData());
					}//end of if(tableDataLog.getSearchData().size()>0)
				}// end of  else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strPartner)) //ParallelAlert
				else if((strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))||strLink.equals(strClaims)|| strLink.equals(strDataEntryClaims)||
					(strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))||((strLink.equals(strCoding) || strLink.equals(strDataEntryCoding))&& strSubLink.equals(strClaimsCoding))||
					(strLink.equals(strCodeCleanup)&& strSubLink.equals(strPreAuth))||(strLink.equals(strCodeCleanup)&& strSubLink.equals(strClaimsCoding)))
				{
					if(tableDataLog.getSearchData().size()>0)
					{
						//refresh the data in order to get the new records if any.
						alLogList=logManagerObj.getPreAuthLogList(tableDataLog.getSearchData());
					}//end of if(tableDataLog.getSearchData().size()>0)
				}// end of else if((strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))||
						//strLink.equals(strClaims)||(strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))||
							//(strLink.equals(strCoding)&& strSubLink.equals(strClaimsCoding)))
				tableDataLog.setData(alLogList,"search");
				request.getSession().setAttribute("tableDataLog",tableDataLog);
			}// end of if(iUpdate >0)
			//StringBuffer strCaption= new StringBuffer();
			StringBuffer strCaption=buildCaption(request);
			frmSaveLog.set("caption",String.valueOf(strCaption));
			frmPartnerLog.set("caption",String.valueOf(strCaption));
			return this.getForward(strLog, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"log"));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
     * This method is prepares the Caption based on the flow and retunrs it
     *
     * @param request The HTTP request we are processing
     * @return StringBuffer prepared Caption
     */
	private StringBuffer buildCaption(HttpServletRequest request)throws TTKException
	{
		StringBuffer strCaption=new StringBuffer();

		try
		{
			String strLink=TTKCommon.getActiveLink(request);
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strClaimantname="";
			String strEnrollmentID="";
			//Buidling the label to dispaly in JSP

			if(strLink.equals(strPreAuthorization))
			{
				strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals(strPreAuthorization))
			else if((strLink.equals(strClaims))||(strLink.equals(strDataEntryClaims)))
			{
				strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strLink.equals(strPreAuthorization))
			else if((strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))||
					(strLink.equals(strCodeCleanup)&& strSubLink.equals(strPreAuth)))
			{
				strClaimantname=CodingWebBoardHelper.getClaimantName(request);
				if(!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())&&
						CodingWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+CodingWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())&& CodingWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))
			else if(((strLink.equals(strCoding) || strLink.equals(strDataEntryCoding))&& strSubLink.equals(strClaimsCoding))||
					(strLink.equals(strCodeCleanup)&& strSubLink.equals(strClaimsCoding)))
			{
				strClaimantname=CodingClaimsWebBoardHelper.getClaimantName(request);
				if(!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						CodingClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())&& CodingClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strLink.equals(strCoding)&& strSubLink.equals(strClaimsCoding)) //ParallelAlert
			if(strLink.equals(strPreAuthorization)||strLink.equals(strClaims)||strLink.equals(strDataEntryClaims)||(strLink.equals(strCoding)&& 
					strSubLink.equals(strPreAuth))||((strLink.equals(strCoding) || strLink.equals(strDataEntryCoding))&& strSubLink.equals(strClaimsCoding))
					||(strLink.equals(strCodeCleanup)&& strSubLink.equals(strPreAuth))||
					(strLink.equals(strCodeCleanup)&& strSubLink.equals(strClaimsCoding)))
			{
				strCaption.append("Alert Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
			}//end of if(strLink.equals(strPreAuthorization)||strLink.equals(strClaims)||(strLink.equals(strCoding)&& 
					//strSubLink.equals(strPreAuth))||(strLink.equals(strCoding)&& strSubLink.equals(strClaimsCoding)))
			else
			{
				strCaption.append("Alert Details");
			}//end of else
		}catch(Exception exp)
		{
			throw new TTKException(exp, "contactAction");
		}//end of catch
		return strCaption;
	}//end of buildCaption(HttpServletRequest request)

	/**
     * This method is retunrs appropriate SeqId based on the flow.
     *
     * @param request The HTTP request we are processing
     * @param strSwitchType String Identfier for Enrollment/Endorsement flow
     * @return String prepared Caption
     */
	private Long getSeqId(HttpServletRequest request,String strSwitchType)throws TTKException
	{
		try
		{
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strLink=TTKCommon.getActiveLink(request);
			
			if(strLink.equals(strEnrollment))
			{
				if(strSwitchType.equals(strEnd))
				{
					return new Long(WebBoardHelper.getEndorsementSeqId(request));
				}// end of if(strSwitchType.equals(strEnd))
				else
				{
					return new Long(WebBoardHelper.getPolicySeqId(request));
				}// end of else
			}//end of if(strLink.equals(strEnrollment))
			else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital))
			{
				return new Long(TTKCommon.getWebBoardId(request));//get the web board id

			}//end of else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital))
			else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strPartner))
			{
				return new Long(TTKCommon.getWebBoardId(request));//get the web board id

			}//end of else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strPartner))
			else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
			{
				return new Long(PreAuthWebBoardHelper.getPreAuthSeqId(request));
			}//end of else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
			else if((strLink.equals(strClaims)) || (strLink.equals(strDataEntryClaims)))
			{
				return new Long(ClaimsWebBoardHelper.getClaimsSeqId(request));
			}//end of else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
			else if((strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))||
					(strLink.equals(strCodeCleanup)&& strSubLink.equals(strPreAuth)))
			{
				return new Long(CodingWebBoardHelper.getPreAuthSeqId(request));
			}//end of else if(strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))//ParallelAlert
			else if(((strLink.equals(strCoding) || strLink.equals(strDataEntryCoding))&& strSubLink.equals(strClaimsCoding))||
					(strLink.equals(strCodeCleanup)&& strSubLink.equals(strClaimsCoding)))
			{
				return new Long(CodingClaimsWebBoardHelper.getClaimsSeqId(request));
			}//end of else if(strLink.equals(strCoding)&& strSubLink.equals(strClaimsCoding))	
		}catch(Exception exp)
		{
			throw new TTKException(exp, "log");
		}//end of catch
		return null;
	}//end of getSeqId(HttpServletRequest request,String strSwitchType)

	/**
	 * This method used to set the setLinks.
	 *
	 * @param request The HTTP request we are processing.
	 * @return void
	 */
	private void handleSetLink(HttpServletRequest request)throws TTKException
	{
		String strLink=TTKCommon.getActiveLink(request);
		try
		{
			if(strLink.equals(strEnrollment))
			{
				DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
				String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
				this.setLinks(request,strSwitchType);
			}//end of if(strLink.equals(strEnrollment))
			else
			{
				this.setLinks(request);
			}//end of else

		}catch(Exception exp)
		{
			throw new TTKException(exp, "log");
		}//end of catch
	}//end of handleSetLink(HttpServletRequest request)

	/**
     * This method returns the forward path of next view based on the Flow
     *
     * @param request The HTTP request we are processing
     * @return strForwardPath String forward path for the next view
     */
	private String getForwardPath(HttpServletRequest request)throws TTKException
	{
		String strForwardPath=null;
		try
		{
			String strLink=TTKCommon.getActiveLink(request);
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strActiveTab	=	TTKCommon.getActiveTab(request);
			
			if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital))
			{
				strForwardPath= strHospLogList;
			}//end of else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital))
			
			if(strLink.equals(strEmpanelment)&& strSubLink.equals(strPartner))
			{
				strForwardPath= strPtnrLogList;
			}//end of else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital))
			
			if(strLink.equals(strEnrollment)&& strSubLink.equals(strIndPolicy))
			{
				strForwardPath= strIndLogList;
			}// end of if(strLink.equals(strEnrollment)&& strSubLink.equals(strIndPolicy))
			else if(strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))
			{
				strForwardPath= strGrpLogList;
			}// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))
			else if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
			{
				strForwardPath= strCorLogList;
			}// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
			else if(strLink.equals(strEnrollment)&& strSubLink.equals(strNonCorPolicy))
			{
				strForwardPath= strNonCorLogList;
			}// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strNonCorPolicy))
			else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
			{
				strForwardPath= strPreAuthLogList;
			}//end of else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
			else if((strLink.equals(strClaims)) || (strLink.equals(strDataEntryClaims)))
			{
				//strForwardPath= strClaimsAlertList;
				strForwardPath=((strLink.equals(strClaims))?strClaimsAlertList:strDataEntryClaimsAlertList);
				
			}//end of  else if(strLink.equals(strClaims))
			else if(strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))
			{
				strForwardPath= strCodingPreAuthAlert;
			}//end of else if(strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))
			else if((strLink.equals(strCoding) || strLink.equals(strDataEntryCoding))&& strSubLink.equals(strClaimsCoding))
			{
				//strForwardPath= strCodingClaimsAlert;
				strForwardPath=((strLink.equals(strClaims))?strCodingClaimsAlert:strDataEntryCodingClaimsAlert);
			}//end of else if(strLink.equals(strCoding)&& strSubLink.equals(strClaimsCoding))
			else if(strLink.equals(strCodeCleanup)&& strSubLink.equals(strPreAuth))
			{
				strForwardPath= strCodingCleanupPreAuthAlert;
			}//end of else if(strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))
			else if(strLink.equals(strCodeCleanup)&& strSubLink.equals(strClaimsCoding))
			{
				strForwardPath= strCodingCleanupClaimsAlert;
			}//end of else if(strLink.equals(strCoding)&& strSubLink.equals(strClaimsCoding))
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "log");
		}//end of catch
		return strForwardPath;
	}//end of getBonusListForwardPath(String strActiveSubLink)

	/**
	 * This method will build the search criteria parametrs in ArrayList and returns it
	 * @param frmLog DynaActionForm  form
	 * @param request HttpServletRequest
	 * @return
	 * @throws
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmLog,HttpServletRequest request,String strSwitchType,
													Long lngPartnerSeqId)throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		String strSubLink=TTKCommon.getActiveSubLink(request);
		String strLink=TTKCommon.getActiveLink(request);
		if(strLink.equals(strEnrollment))
		{
			if(strSwitchType.equals(strEnd))
			{
				Long lngEndosementSeqId=WebBoardHelper.getEndorsementSeqId(request);				
				alSearchParams.add(lngEndosementSeqId);
				alSearchParams.add(strSwitchType);
			}// end of if(strSwitchType.equals(strEnd))
			else if(strSwitchType.equals(strEnm))
			{
				Long lngPolicySeqId=WebBoardHelper.getPolicySeqId(request);
				alSearchParams.add(lngPolicySeqId);
				alSearchParams.add(strSwitchType);
			} // end of else if(strSwitchType.equals(strEnm))
		}//end of if(strLink.equals(strEnrollment))
		else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strPartner))
		{
			alSearchParams.add(String.valueOf(lngPartnerSeqId));//PTNR_SEQ_ID got the value from session
			alSearchParams.add((String)frmLog.get("partnerLogTypeCode"));//LOG_TYPE_ID
		}// end of else if(strLink.equals(strEmpanelment)&& strSubLink.equals(strHospital))
		else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
		{
			Long lngPreAuthSeqId=PreAuthWebBoardHelper.getPreAuthSeqId(request);
			alSearchParams.add(lngPreAuthSeqId);
			alSearchParams.add(null);
			alSearchParams.add((String)frmLog.get("sLogType"));
		}//end of else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
		else if((strLink.equals(strClaims)) || (strLink.equals(strDataEntryClaims)))//ParallelAlert
		{
			Long lngClaimSeqId=ClaimsWebBoardHelper.getClaimsSeqId(request);
			alSearchParams.add(null);
			alSearchParams.add(lngClaimSeqId);
			alSearchParams.add((String)frmLog.get("sLogType"));
		}// end of else if(strLink.equals(strClaims))
		else if((strLink.equals(strCoding)&& strSubLink.equals(strPreAuth))||
				(strLink.equals(strCodeCleanup)&& strSubLink.equals(strPreAuth)))
		{
			Long lngPreAuthSeqId=CodingWebBoardHelper.getPreAuthSeqId(request);
			alSearchParams.add(lngPreAuthSeqId);
			alSearchParams.add(null);
			alSearchParams.add((String)frmLog.get("sLogType"));
		}//end of else if((strLink.equals(strCoding)&& strSubLink.equals(strPreAuth)))
		else if(((strLink.equals(strCoding) || strLink.equals(strDataEntryCoding)) && strSubLink.equals(strClaimsCoding))||
				(strLink.equals(strCodeCleanup)&& strSubLink.equals(strClaimsCoding)))
		{
			Long lngClaimSeqId=CodingClaimsWebBoardHelper.getClaimsSeqId(request);
			alSearchParams.add(null);
			alSearchParams.add(lngClaimSeqId);
			alSearchParams.add((String)frmLog.get("sLogType"));
		}// end of else if((strLink.equals(strCoding)&& strSubLink.equals(strClaimsCoding)))
		alSearchParams.add((String)frmLog.get("sStartDate"));//START_DATE
		alSearchParams.add((String)frmLog.get("sEndDate"));//END_DATE		
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmLog,HttpServletRequest request,String strSwitchType,
		//Long lngHospitalSeqId)throws TTKException

	/**
	 * Returns the LogManager session object for invoking methods on it.
	 * @return LogManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private LogManager getLogManagerObject() throws TTKException
	{
		LogManager logManager = null;
		try
		{
			if(logManager == null)
			{
				InitialContext ctx = new InitialContext();
				logManager = (LogManager) ctx.lookup("java:global/TTKServices/business.ejb3/LogManagerBean!com.ttk.business.empanelment.LogManager");
			}//end if(logManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "log");
		}//end of catch(Exception exp)
		return logManager;
	}//end getLogManagerObject()
	
	
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
	public ActionForward doSelect(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside frmPartnerLog doSelect");
			if(TTKCommon.getWebBoardId(request) == null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.policy.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request) == null)
			DynaActionForm frmPartnerLog= null;
			frmPartnerLog = (DynaActionForm) request.getSession().getAttribute("frmPartnerLog");
			if(frmPartnerLog!=null){
				frmPartnerLog.set("path",((DynaActionForm)form).getString("path"));
			}//end of if(request.getSession().getAttribute("frmPartnerLog")!=null)
			else {
				frmPartnerLog = (DynaActionForm)form;
			}//end of else	
			if(((DynaActionForm)form).getString("path")!=null){
				frmPartnerLog.set("path",((DynaActionForm)form).getString("path"));
			}//end of if(((DynaActionForm)form).getString("path")!=null)
			else {
				frmPartnerLog.set("path","");
			}//end of else
			TableData tableDataLinkDetails = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			{
				tableDataLinkDetails=(TableData)(request.getSession()).getAttribute("tableDataLinkDetails");
			}//end of if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			else
			{
				tableDataLinkDetails=new TableData();
			}//end of else
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			ArrayList alLinkDetailsList=null;
			//ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strSortID.equals(""))
			{
				tableDataLinkDetails.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableDataLinkDetails.modifySearchData("sort");//modify the search data
			}// end of if(!strSortID.equals(""))
			else
			{
				tableDataLinkDetails.createTableInfo("HospLinkDetailsTable",null);
				tableDataLinkDetails.setSearchData(this.populateSearchCriteria(request));
				tableDataLinkDetails.modifySearchData("search");
			}// end of else

			
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			//alLinkDetailsList=LogManager.getWebConfigLinkList(tableDataLinkDetails.getSearchData());
			alLinkDetailsList=productpolicyObject.getWebConfigLinkList(tableDataLinkDetails.getSearchData());
			tableDataLinkDetails.setData(alLinkDetailsList);
			frmPartnerLog.set("showLinkYN","Y");
			frmPartnerLog.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("tableDataLinkDetails",tableDataLinkDetails);
			request.setAttribute("frmPartnerLog",frmPartnerLog);
			//return this.getForward(strLinkDetailsList, mapping, request);
			return mapping.findForward(strLinkDetailsList);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strWebconfig));
		}//end of catch(Exception exp)
	}//end of doSelect(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return ProductPolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ProductPolicyManager getProductPolicyManager() throws TTKException
	{
		ProductPolicyManager productPolicyManager = null;
		try
		{
			if(productPolicyManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyManager = (ProductPolicyManager) ctx.lookup(ProductPolicyManager.class.getName());
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strWebconfig);
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManager()
	
	
	/**
     * This method is used to close the current page and return to previous page.
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
			log.debug("Inside doClose of LinkDetailsAction");
			return this.getForward("hospuploads", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strWebconfig));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	
	
	
	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param request The HTTP request we are processing
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.getWebBoardId(request));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(Long lngMemberSeqId)
	
	
	/**
	 * This method builds all the files to populate in ArrayList 
	 * @param ArrayList alObj
	 * @return alObj ArrayList
	 */
	private ArrayList<Object> populateFilesToList(File[] fileArrayObj,ArrayList<Object> alObj) throws IOException{
		int iAlIndex = alObj.size();
		for(int j=0;j<fileArrayObj.length;j++,iAlIndex++){
			alObj.add(iAlIndex,(String)fileArrayObj[j].getPath().replaceAll("\\\\","\\\\\\\\"));
		}//end of for(intj=0;j<fileArrayObj.length;j++)
		return alObj;
	}//end of  populateFilesToList()
}// end of PartnerLogAction