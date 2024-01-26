/**
 * @ (#) EnrollmentSearchAction.java May 4th 2006
 * Project 		: TTK HealthCare Services
 * File 		: EnrollmentSearchAction.java
 * Author 		: Krupa J
 * Company 		: Span Systems Corporation
 * Date Created : May 4th 2006
 *
 * @author 		: Krupa J
 * Modified by 	:
 * Modified date:
 * Reason 		:
 */

package com.ttk.action.dataentrypreauth;

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
import com.ttk.action.table.TableData;
import com.ttk.business.claims.ClaimManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.preauth.ClaimantNewVO;
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.preauth.PreAuthHospitalVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is reusable for searching of hospitals in pre-auth,claims,call center, claims inward entry flow.
 * This class also rovides opiton to select the required enrollment.
 */
public class EnrollmentSearchAction extends TTKAction
{
	private static Logger log = Logger.getLogger( EnrollmentSearchAction.class );

	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	private static final String strPreAuthDetail="preauthdetail";
	private static final String strClaimdetails="claimdetails";
	private static final String strPreAuthorization="Pre-Authorization";
	private static final String strClaims="DataEntryClaims";
	private static final String strClaimList="dataentryclaimlist";

	//forwards
	private static final String strInwardEnrollmentList="inwardenrollmentlist";
	private static final String strInwardClaimList="inwardclaimlist";
	private static final String strPreAuthenrollmentList="preauthenrollmentlist";
	private static final String strClaimEnrollmentList="dataentryclaimenrollmentlist";
	private static final String strClaimDetail="dataentryclaimdetail";
	private static final String strCallcenterEnrollment="callcenterenrollment";
	private static final String strCallcenterSearch="callcentersearch";
	private static final String strCallcenterDetailsEnrollment="callcenterdetailsenrollment";
	private static final String strCallcenterDetails="callcenterdetails";

	//string for comparision
	private static final String strInwardClaims="Claims";
	private static final String strPreAuthProcessing="Processing";
	private static final String strCustomerCare="Customer Care";
	private static final String strSearch="Search";
	private static final String strCallDetails="Call Details";

	//Exception Message Identifier
	private static final String strEnrollmentSearchError="enrollmentsearch";

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
			HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside EnrollmentSearchAction doDefault");
			TableData  tableData =TTKCommon.getTableData(request);
			String strEnrollmentList="";
			strEnrollmentList = this.getForwardPath(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			//String strSubLink=TTKCommon.getActiveSubLink(request);
			//String strActiveLink=TTKCommon.getActiveLink(request);
			//String strActiveTab=TTKCommon.getActiveTab(request);
			//Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");
			if(strClaimList.equals(request.getParameter(strClaimList)))
			{
				tableData.createTableInfo("ClaimSearchTable",new ArrayList());
			}// end of  if(strClaimList.equals(request.getParameter(strClaimList)))
			else
			{
				tableData.createTableInfo("EnrollmentSearchTable",new ArrayList());
			}//end of else
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			request.getSession().setAttribute("searchparam", null);
			return this.getForward(strEnrollmentList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strEnrollmentSearchError));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.info("Inside doSearch doEnrollmentList ");
			PreAuthManager PreAuthManagerObject=this.getPreAuthManagerObject();
			ClaimManager claimManagerObject=this.getClaimManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			String strEnrollmentList="";
			strEnrollmentList = this.getForwardPath(request);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strEnrollmentList));										
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				if(strClaimList.equals(request.getParameter(strClaimList)))
				{
					tableData.createTableInfo("ClaimSearchTable",null);
				}// end of  if(strClaimList.equals(request.getParameter(strClaimList)))
				else
				{
					tableData.createTableInfo("EnrollmentSearchTable",null);
				}//end of else
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alEnrollment = new ArrayList();
			if(strClaimList.equals(request.getParameter(strClaimList)))
			{
				alEnrollment = claimManagerObject.getClaimShortfallList(tableData.getSearchData());
			}// end of  if(strClaimList.equals(request.getParameter(strClaimList)))
			else
			{
				if(TTKCommon.getActiveLink(request).equals("Pre-Authorization") && TTKCommon.getActiveLink(request).equals("DataEntryClaims"))
				{
					alEnrollment = PreAuthManagerObject.getEnrollmentList(tableData.getSearchData());
				}else
				{
					alEnrollment = PreAuthManagerObject.getClaimantList(tableData.getSearchData());				
				}//end else
			}
			tableData.setData(alEnrollment, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strEnrollmentList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strEnrollmentSearchError));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to get the Selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectEnrollment(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside EnrollmentSearchAction doSelectEnrollment");
			ClaimManager claimManagerObject=this.getClaimManagerObject();
			PreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			DynaActionForm frmGeneral=null;
			String strGeneralDetails="";
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strActiveTab=TTKCommon.getActiveTab(request);
			Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");
			if(strSubLink.equals(strPreAuthProcessing))
			{
				if(strActiveLink.equals(strPreAuthorization))
				{
					strGeneralDetails=strPreAuthDetail;
				}//end of if(strActiveLink.equals(strPreAuthorization))
				if(strActiveLink.equals(strClaims))
				{
					strGeneralDetails=strClaimDetail;
				}//end of if(strActiveLink.equals(strClaims))
			}// end of if(strLink.equals(strInwardEntry)&& strSubLink.equals(strSubEnrollment))
			else if(strSubLink.equals(strInwardClaims))
			{
				strGeneralDetails=strClaimdetails;
			}// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))
			if(strActiveLink.equals(strCustomerCare))
			{
				if(strActiveTab.equals(strSearch))
				{
					strGeneralDetails=strCallcenterSearch;
				}//end of if(strActiveTab.equals(strSearch))
				else if(strActiveTab.equals(strCallDetails))
				{
					strGeneralDetails=strCallcenterDetails;
				}//end of else if(strActiveTab.equals(strCallDetails))
			}//end of if(strActiveLink.equals(strCustomerCare))
			ClaimantVO claimantVO=null;
			ClaimantNewVO claimantNewVO=null;
			frmGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				if(TTKCommon.getActiveLink(request).equals("Pre-Authorization") && TTKCommon.getActiveLink(request).equals("DataEntryClaims"))
				{
					claimantNewVO = (ClaimantNewVO)((TableData)request.getSession().getAttribute("tableData")).getRowInfo(Integer.parseInt(request.getParameter("rownum")));
					claimantVO=(ClaimantVO)preAuthManagerObject.getSelectEnrollmentID(claimantVO.getMemberSeqID());
				}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization") && TTKCommon.getActiveLink(request).equals("Claims"))
				else
				{
					claimantVO = (ClaimantVO)((TableData)request.getSession().getAttribute("tableData")).getRowInfo(Integer.parseInt(request.getParameter("rownum")));
					claimantVO=(ClaimantVO)preAuthManagerObject.getSelectEnrollmentID(claimantVO.getMemberSeqID());
				}//end of else

				if(strSubLink.equals(strPreAuthProcessing))
				{
					frmGeneral.set("frmChanged","changed");
					if(strActiveLink.equals(strClaims))
					{
						if(((frmGeneral.get("authNbr") != null && (!"".equals(frmGeneral.get("authNbr")))) || (frmGeneral.get("prevHospClaimSeqID") != null)))
						{
							clearPreauthHospital(mapping,frmGeneral,request,claimManagerObject);
							HashMap hmPrevHospList = new HashMap();
							//adding hmPrevHospList while selecting new enrollmentid
							if(ClaimsWebBoardHelper.getMemberSeqId(request) != null && !"".equals(ClaimsWebBoardHelper.getMemberSeqId(request)))
							{
								DynaActionForm frmClaimantDetails=(DynaActionForm)frmGeneral.get("claimDetailVO");
								hmPrevHospList = claimManagerObject.getPrevHospList(ClaimsWebBoardHelper.getMemberSeqId(request),frmClaimantDetails.getString("claimTypeID"),ClaimsWebBoardHelper.getClaimsSeqId(request));
							}//end of if
							frmGeneral.set("hmPrevHospList",hmPrevHospList);
						}//end of if 
					}//end of if(strActiveTab.equals(strClaims))
					//if pre-auth process is completed, link only Enrollment-Id
					if(frmGeneral.get("completedYN").equals("Y"))
					{
						DynaActionForm frmClaimantDetails=(DynaActionForm)frmGeneral.get("claimantDetailsVO");
						frmClaimantDetails.set("enrollmentID",claimantVO.getEnrollmentID());
						frmClaimantDetails.set("policyNbr",claimantVO.getPolicyNbr());
						frmClaimantDetails.set("memberSeqID",claimantVO.getMemberSeqID().toString());
						frmClaimantDetails.set("policySeqID",claimantVO.getPolicySeqID().toString());

						//changes on Dec 6th KOC 1136
						frmClaimantDetails.set("groupID", claimantVO.getGroupID().toString());
						frmClaimantDetails.set("vipYN", claimantVO.getVipYN().toString());
						//changes on Dec 6th KOC 1136

						//						Convert it regular Preauth, if current status is Manual
						if(("MAN").equals(frmGeneral.get("preAuthTypeID")))
						{
							frmGeneral.set("preAuthTypeID","MRG");//change the pre-auth type to regular.
							frmGeneral.set("preAuthTypeDesc","Manual-Regular");
						}//end of if(("MAN").equals(frmGeneral.get("preAuthTypeID")))
					}//end of if(frmGeneral.get("completedYN").equals("Y"))
					//if pre-auth is under process, fetch all the data related to that particular enrollment
					else if(frmGeneral.get("completedYN").equals("N"))
					{

						frmGeneral.set("claimantDetailsVO",FormUtils.setFormValues("frmClaimantDetails",claimantVO,
								this,mapping,request));

						//Convert it regular Preauth, if current status is Manual
						if(("MAN").equals(frmGeneral.get("preAuthTypeID")))
						{
							frmGeneral.set("preAuthTypeID","REG");//change the pre-auth type to regular.
							frmGeneral.set("preAuthTypeDesc","Regular");
						}//end of if(("MAN").equals(frmGeneral.get("preAuthTypeID")))

					}//end of else if(frmGeneral.get("completedYN").equals("N"))
				}//end of if(strSubLink.equals(strPreAuthProcessing))
				if(strSubLink.equals(strInwardClaims))
				{
					ArrayList alPrevClaim = null;
					frmGeneral=(DynaActionForm)request.getSession().getAttribute("frmClaimDetails");
					DynaActionForm frmClaimEnrollment=(DynaActionForm)frmGeneral.get("claimantVO");
					frmClaimEnrollment.set("enrollmentID",claimantVO.getEnrollmentID());
					frmClaimEnrollment.set("name",claimantVO.getName());
					frmClaimEnrollment.set("policyNbr",claimantVO.getPolicyNbr());
					frmClaimEnrollment.set("policySeqID",String.valueOf(claimantVO.getPolicySeqID()));
					frmClaimEnrollment.set("memberSeqID",String.valueOf(claimantVO.getMemberSeqID()));
					frmClaimEnrollment.set("insSeqID",String.valueOf(claimantVO.getInsHeadOffSeqID()));
					frmClaimEnrollment.set("groupName",claimantVO.getGroupName());
					frmClaimEnrollment.set("policyHolderName",claimantVO.getPolicyHolderName());
					frmClaimEnrollment.set("employeeNbr",claimantVO.getEmployeeNbr());
					frmClaimEnrollment.set("employeeName",claimantVO.getEmployeeName());
					frmClaimEnrollment.set("insScheme",claimantVO.getInsScheme());
					frmClaimEnrollment.set("certificateNo",claimantVO.getCertificateNo());
					frmClaimEnrollment.set("insCustCode",claimantVO.getInsCustCode());
					frmClaimEnrollment.set("emailID",claimantVO.getEmailID());
					frmGeneral.set("frmChanged","changed");
					alPrevClaim = claimManagerObject.getPrevClaim(claimantVO.getMemberSeqID());
					if(alPrevClaim!=null && alPrevClaim.size() > 0){
						frmGeneral.set("alPrevClaim",alPrevClaim);
					}//end of if(alPrevClaim!=null && alPrevClaim.size() > 0)
					else{
						frmGeneral.set("alPrevClaim",new ArrayList());
					}//end of else
				}//end of if(strSubLink.equals(strInwardClaims))
				if(strActiveLink.equals(strCustomerCare))
				{
					if(strActiveTab.equals(strSearch))
					{
						DynaActionForm frmCallCenterList=(DynaActionForm)
						request.getSession().getAttribute("frmCallCenterList");
						if(frmCallCenterList != null)
						{
							frmCallCenterList.set("id",claimantVO.getEnrollmentID());
							frmCallCenterList.set("name",claimantVO.getName());
							frmCallCenterList.set("seqID",String.valueOf(claimantVO.getMemberSeqID()));
						}//end of if(frmCallCenterList != null)
					}//end of if(strActiveTab.equals(strSearch))
					else if(strActiveTab.equals(strCallDetails))
					{
						DynaActionForm frmCallCenterDetails=(DynaActionForm)
						request.getSession().getAttribute("frmCallCenterDetails");
						if(frmCallCenterDetails != null)
						{
							frmCallCenterDetails.set("policyNumber",claimantVO.getPolicyNbr());
							frmCallCenterDetails.set("claimantName",claimantVO.getName());
							frmCallCenterDetails.set("enrollmentID",claimantVO.getEnrollmentID());
							frmCallCenterDetails.set("insScheme",claimantVO.getInsScheme());
							frmCallCenterDetails.set("certificateNo",claimantVO.getCertificateNo());
							frmCallCenterDetails.set("creditCardNbr",claimantVO.getCreditCardNbr());
							frmCallCenterDetails.set("insCustCode",claimantVO.getInsCustCode());
							frmCallCenterDetails.set("memberSeqID",String.valueOf(claimantVO.getMemberSeqID()));
							frmCallCenterDetails.set("policySeqID",String.valueOf(claimantVO.getPolicySeqID()));
							frmCallCenterDetails.set("frmChanged","changed");
						}//end of if(frmCallCenterDetails != null)
					}//end of else if(strActiveTab.equals(strCallDetails))
				}//end of if(strActiveLink.equals(strCustomerCare))
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			request.getSession().removeAttribute("frmEnrollSearch");//remove the frmEnrollSearch from the session
			request.getSession().removeAttribute("tableData");//remove the tableData from the session
			if(strSubLink.equals(strPreAuthProcessing))
			{
				toolBar.getConflictIcon().setVisible(toolBar.getConflictIcon().isVisible() &&
						frmGeneral.get("discPresentYN").equals("Y"));
				return this.getForward(strGeneralDetails, mapping, request);
			}//end of if(strSubLink.equals(strPreAuthProcessing))
			else{
				return (mapping.findForward(strGeneralDetails));
			}//end of else
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strEnrollmentSearchError));
		}//end of catch(Exception exp)
	}//end of doSelectEnrollment(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	/**
	 * This method is used to get the Selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectClaim(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside EnrollmentSearchAction doSelectEnrollment");
			ClaimManager claimManagerObject=this.getClaimManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			DynaActionForm frmGeneral=null;
			String strGeneralDetails="";
			String strSubLink=TTKCommon.getActiveSubLink(request);
			/*String strActiveLink=TTKCommon.getActiveLink(request);
			String strActiveTab=TTKCommon.getActiveTab(request);
			Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");*/
			if(strSubLink.equals(strInwardClaims))
			{
				strGeneralDetails=strClaimdetails;
			}// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))

			ClaimantVO claimantVO=null;
			frmGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				claimantVO=(ClaimantVO)tableData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
				/*if(strSubLink.equals(strPreAuthProcessing))
				{
					frmGeneral.set("frmChanged","changed");
					//if pre-auth process is completed, link only Enrollment-Id
					if(frmGeneral.get("completedYN").equals("Y"))
					{
						DynaActionForm frmClaimantDetails=(DynaActionForm)frmGeneral.get("claimantDetailsVO");
						frmClaimantDetails.set("enrollmentID",claimantVO.getEnrollmentID());
						frmClaimantDetails.set("policyNbr",claimantVO.getPolicyNbr());
						frmClaimantDetails.set("memberSeqID",claimantVO.getMemberSeqID().toString());
						frmClaimantDetails.set("policySeqID",claimantVO.getPolicySeqID().toString());
					}//end of if(frmGeneral.get("completedYN").equals("Y"))
					//if pre-auth is under process, fetch all the data related to that particular enrollment
					else if(frmGeneral.get("completedYN").equals("N"))
					{
						frmGeneral.set("claimantDetailsVO",FormUtils.setFormValues("frmClaimantDetails",claimantVO,
																				this,mapping,request));

						frmGeneral.set("preAuthTypeID","REG");//change the pre-auth type to regular.
						frmGeneral.set("preAuthTypeDesc","Regular");
					}//end of else if(frmGeneral.get("completedYN").equals("N"))
				}//end of if(strSubLink.equals(strPreAuthProcessing))
				 */				if(strSubLink.equals(strInwardClaims))
				 {
					 ArrayList alPrevClaim = null;
					 ArrayList alShortfallVO = null;
					 ArrayList<Object> alSearchParams = new ArrayList<Object>();
					 frmGeneral=(DynaActionForm)request.getSession().getAttribute("frmClaimDetails");
					 DynaActionForm frmClaimEnrollment=(DynaActionForm)frmGeneral.get("claimantVO");
					 frmClaimEnrollment.set("enrollmentID",claimantVO.getEnrollmentID());
					 frmClaimEnrollment.set("name",claimantVO.getName());
					 frmClaimEnrollment.set("memberSeqID",String.valueOf(claimantVO.getMemberSeqID()));
					 frmClaimEnrollment.set("age",claimantVO.getAge().toString());
					 frmClaimEnrollment.set("genderDescription",claimantVO.getGenderDescription());
					 frmClaimEnrollment.set("claimNbr",claimantVO.getClaimNbr());
					 frmClaimEnrollment.set("clmSeqID",claimantVO.getClmSeqID().toString());
					 frmGeneral.set("frmChanged","changed");
					 alPrevClaim = claimManagerObject.getPrevClaim(claimantVO.getMemberSeqID());
					 if(alPrevClaim!=null && alPrevClaim.size() > 0){
						 frmGeneral.set("alPrevClaim",alPrevClaim);
					 }//end of if(alPrevClaim!=null && alPrevClaim.size() > 0)
					 else{
						 frmGeneral.set("alPrevClaim",new ArrayList());
					 }//end of else
					 alSearchParams.add(claimantVO.getClmSeqID());
					 alSearchParams.add(null);
					 alShortfallVO = claimManagerObject.getInwardShortfallDetail(alSearchParams);

					 if(alShortfallVO!=null && alShortfallVO.size() > 0){
						 frmGeneral.set("alShortfall",alShortfallVO);
					 }//end of if(alPrevClaim!=null && alPrevClaim.size() > 0)
					 else{
						 frmGeneral.set("alShortfall",new ArrayList());
					 }//end of else
				 }//end of if(strSubLink.equals(strInwardClaims))
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			request.getSession().removeAttribute("frmEnrollSearch");//remove the frmEnrollSearch from the session
			request.getSession().removeAttribute("tableData");//remove the tableData from the session

			return (mapping.findForward(strGeneralDetails));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strEnrollmentSearchError));
		}//end of catch(Exception exp)
	}//end of doSelectEnrollment(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

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
			HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside EnrollmentSearchAction doForward");
			PreAuthManager PreAuthManagerObject=this.getPreAuthManagerObject();
			ClaimManager claimManagerObject=this.getClaimManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			String strEnrollmentList="";
			strEnrollmentList = this.getForwardPath(request);
			tableData.modifySearchData(strForward);//modify the search data
			ArrayList alEnrollment = new ArrayList();
			if(strClaimList.equals(request.getParameter(strClaimList)))
			{
				alEnrollment = claimManagerObject.getClaimShortfallList(tableData.getSearchData());
			}// end of  if(strClaimList.equals(request.getParameter(strClaimList)))
			else
			{
				if(TTKCommon.getActiveLink(request).equals("Pre-Authorization") && TTKCommon.getActiveLink(request).equals("Claims"))
				{
					alEnrollment = PreAuthManagerObject.getEnrollmentList(tableData.getSearchData());
				}else
				{
					alEnrollment = PreAuthManagerObject.getClaimantList(tableData.getSearchData());				
				}//end else
			}//end of else
			tableData.setData(alEnrollment, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strEnrollmentList, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strEnrollmentSearchError));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("*Inside EnrollmentSearchAction doBackward");
			PreAuthManager PreAuthManagerObject=this.getPreAuthManagerObject();
			ClaimManager claimManagerObject=this.getClaimManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			String strEnrollmentList="";
			strEnrollmentList = this.getForwardPath(request);
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alEnrollment = new ArrayList();
			if(strClaimList.equals(request.getParameter(strClaimList)))
			{
				alEnrollment = claimManagerObject.getClaimShortfallList(tableData.getSearchData());
			}// end of  if(strClaimList.equals(request.getParameter(strClaimList)))
			else
			{
				if(TTKCommon.getActiveLink(request).equals("Pre-Authorization") && TTKCommon.getActiveLink(request).equals("Claims"))
				{
					alEnrollment = PreAuthManagerObject.getEnrollmentList(tableData.getSearchData());
				}else
				{
					alEnrollment = PreAuthManagerObject.getClaimantList(tableData.getSearchData());				
				}//end else
			}//end of else
			tableData.setData(alEnrollment, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strEnrollmentList, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strEnrollmentSearchError));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns a string which contains the Forward Path
	 * @param request HttpServletRequest object which contains information required for buiding the Forward Path
	 * @return String which contains the comma separated sequence id's to be deleted
	 */
	private String getForwardPath(HttpServletRequest request) throws TTKException{
		String strEnrollmentList="";
		String strSubLink=TTKCommon.getActiveSubLink(request);
		String strActiveLink=TTKCommon.getActiveLink(request);
		String strActiveTab=TTKCommon.getActiveTab(request);
		try{
			if(strSubLink.equals(strPreAuthProcessing))
			{
				if(strActiveLink.equals(strPreAuthorization))
				{
					strEnrollmentList=strPreAuthenrollmentList;
				}//end of if(strActiveLink.equals(strPreAuthorization))
				if(strActiveLink.equals(strClaims))
				{
					strEnrollmentList=strClaimEnrollmentList;
				}//end of if(strActiveLink.equals(strClaims))
			}// end of if(strLink.equals(strInwardEntry)&& strSubLink.equals(strSubEnrollment))
			else if(strSubLink.equals(strInwardClaims))
			{
				if(strClaimList.equals(request.getParameter(strClaimList)))
				{
					strEnrollmentList=strInwardClaimList;
				}//end of if(strClaimList.equals(request.getParameter(strClaimList)))
				else	
				{
					strEnrollmentList=strInwardEnrollmentList;
				}//end of else
			}// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))
			if(strActiveLink.equals(strCustomerCare))
			{
				if(strActiveTab.equals(strSearch))
				{
					strEnrollmentList=strCallcenterEnrollment;
				}//end of if(strActiveTab.equals(strSearch))
				else if(strActiveTab.equals(strCallDetails))
				{
					strEnrollmentList=strCallcenterDetailsEnrollment;
				}//end of else if(strActiveTab.equals(strCallDetails))
			}//end of if(strActiveLink.equals(strCustomerCare))
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strEnrollmentSearchError);
		}//end of catch
		return strEnrollmentList;
	}//end of getForwardPath(HttpServletRequest request)

	/**
	 * clear's the preauth and hospital information when the preauth exsists and try to change the enrollment info
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional DynaActionForm bean for this request (if any)
	 * @param request HttpServletRequest
	 * @param ClaimManager session object which can be used for method invokation
	 * @throws TTKException if any runtime exception occures
	 */
	private void clearPreauthHospital(ActionMapping mapping,DynaActionForm frmPreAuthGeneral,HttpServletRequest request,ClaimManager claimObject) throws TTKException
	{
		try{
			PreAuthHospitalVO preAuthHospitalVO=null;
			/*if(ClaimsWebBoardHelper.getEnrollDetailSeqId(request) != null){
				int iResult = claimObject.releasePreauth(ClaimsWebBoardHelper.getEnrollDetailSeqId(request));
			}//end of if(ClaimsWebBoardHelper.getEnrollDetailSeqId(request) != null)
			 */			frmPreAuthGeneral.set("enrollDtlSeqID","");
			 frmPreAuthGeneral.set("authNbr","");
			 frmPreAuthGeneral.set("prevHospClaimSeqID","");
			 frmPreAuthGeneral.set("receivedDate","");
			 frmPreAuthGeneral.set("receivedTime","");
			 frmPreAuthGeneral.set("receivedDay","");
			 frmPreAuthGeneral.set("approvedAmt","");
			 frmPreAuthGeneral.set("preauthTypeDesc","");
			 frmPreAuthGeneral.set("hmPrevHospList",new HashMap());
			 frmPreAuthGeneral.set("clmAdmissionDate","");
			 frmPreAuthGeneral.set("clmAdmissionTime","");
			 frmPreAuthGeneral.set("admissionDay","");
			 frmPreAuthGeneral.set("dischargeDate","");
			 frmPreAuthGeneral.set("dischargeTime","");
			 frmPreAuthGeneral.set("dischargeDay","");
			 frmPreAuthGeneral.set("frmChanged","changed");
			 preAuthHospitalVO=new PreAuthHospitalVO();
			 frmPreAuthGeneral.set("preAuthHospitalVO",FormUtils.setFormValues("frmPreAuthHospital",
					 preAuthHospitalVO,this,mapping,request));
		}
		catch(Exception exp)
		{
			throw new TTKException(exp, strEnrollmentSearchError);
		}//end of catch
	}//end of clearPreauthHospital(ActionMapping mapping,DynaActionForm frmPreAuthGeneral,HttpServletRequest request,ClaimManager claimObject)

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
			}//end of if(PolicyManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strEnrollmentSearchError);
		}//end of catch
		return preAuthManager;
	}//end getPreAuthManagerObject(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

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
			throw new TTKException(exp, strEnrollmentSearchError);
		}//end of catch
		return claimManager;
	}//end of getClaimManagerObject()

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmEnrollSearch DynaActionForm
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmEnrollSearch,HttpServletRequest request)
	throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		if(strClaimList.equals(request.getParameter(strClaimList)))
		{
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmEnrollSearch.get("enrollmentID")));
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmEnrollSearch.get("name")));
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmEnrollSearch.get("claimNbr")));
			alSearchParams.add(TTKCommon.getUserSeqId(request));
		}//end of if(strClaimList.equals(request.getParameter(strClaimList)))
		else
		{
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmEnrollSearch.get("enrollmentID")));
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmEnrollSearch.get("schemeName")));
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmEnrollSearch.get("certificateNo")));
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmEnrollSearch.get("name")));
			alSearchParams.add((String)frmEnrollSearch.get("genderTypeID"));
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmEnrollSearch.get("policyNbr")));
			alSearchParams.add((String)frmEnrollSearch.get("sInsuranceSeqID"));
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmEnrollSearch.get("sEmployeeNo")));
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmEnrollSearch.get("corpName")));
			alSearchParams.add(TTKCommon.getUserSeqId(request));
			alSearchParams.add((String)frmEnrollSearch.get("showLatest"));
		}//end of else
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmEnrollSearch)
}//end of EnrollmentSearchAction