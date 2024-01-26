/**
 * @ (#) SelectHospitalAction.java May 6, 2006
 * Project      : TTK HealthCare Services
 * File         : SelectHospitalAction.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : May 6, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.preauth;

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
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.preauth.PreAuthHospitalVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;


/**
 * This class is reusable for searching of hospitals in pre-auth,claims,courier,call center flow.
 * This class also provides option to select the rquired hospital.
 */

public class SelectHospitalAction extends TTKAction {

	private static Logger log = Logger.getLogger( SelectHospitalAction.class );

	//declarations of mode
	private static final String strForward ="Forward";
	private static final String strBackward ="Backward";

	//declarations of constants
	private static final String strPreAuthorization="Pre-Authorization";
	private static final String strClaims="Claims";
	//private static final String strDataEntryClaims="DataEntryClaims";
	private static final String strSupport="Support";
	private static final String strCustomerCare="Customer Care";
	private static final String strSearch="Search";
	private static final String strCallDetails="Call Details";

	//declarations of forwards links
	private static final String strPreAuthHospitalList="preauthhospitallist";
	private static final String strCourierHospitalList="courierhospitallist";
	private static final String strClaimsHospitalList="claimshospitallist";
	private static final String strPreAuthDetail="preauthdetail";
	private static final String strCourierDetail="courierdetail";
	private static final String strClaimsdetail="claimsdetail";
	private static final String strCallcenterHospital="callcenterhospital";
	private static final String strCallcenterSearch="callcentersearch";
	private static final String strCallcenterDetailsHospital="callcenterdetailshospital";
	private static final String strCallcenterDetails="callcenterdetails";

	//Exception Message Identifier
	private static final String strHospSearch="hospitalsearch";

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
			setLinks(request);
			log.debug("Inside SelectHospitalAction doDefault");
			String strHospitalList="";
			TableData hospitalListData = null;
			if((request.getSession()).getAttribute("hospitalListData") != null){
				hospitalListData = (TableData)(request.getSession()).getAttribute("hospitalListData");
			}//end of if((request.getSession()).getAttribute("hospitalListData") != null)
			else{
				hospitalListData = new TableData();
			}//end of else
			strHospitalList = this.getForwardPath(request);
			hospitalListData = new TableData();    //create new table data object
			hospitalListData.createTableInfo("PreAuthHospitalTable",new ArrayList());//create the required grid table
			
			//OPD_4_hosptial
			String GroupID=(String)request.getSession().getAttribute("groupId");
			String paymentType=(String)request.getSession().getAttribute("paymentType");
			//OPD_4_hosptial			
			request.getSession().setAttribute("hospitalListData",hospitalListData);
			((DynaActionForm)form).initialize(mapping); //reset the form data
			return this.getForward(strHospitalList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHospSearch));
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
													HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside SelectHospitalAction doSearch");
			String paymentType = (String)request.getSession().getAttribute("paymentType");//OPD_4_hosptial
			String strHospitalList="";
			HospitalManager hospitalObject=this.getHospitalManagerObject();//get the session bean from the bean pool
			TableData hospitalListData = null;
			if((request.getSession()).getAttribute("hospitalListData") != null){
				hospitalListData = (TableData)(request.getSession()).getAttribute("hospitalListData");
			}//end of if((request.getSession()).getAttribute("hospitalListData") != null)
			else{
				hospitalListData = new TableData();
			}//end of else
			strHospitalList = this.getForwardPath(request);
			//clear the dynaform if visiting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))

			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					hospitalListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(
																			request.getParameter("pageId"))));
					return this.getForward(strHospitalList, mapping, request);
				}///end of if(!strPageID.equals(""))
				else
				{
					hospitalListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					hospitalListData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				hospitalListData.createTableInfo("PreAuthHospitalTable",null);
				hospitalListData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				hospitalListData.modifySearchData("search");
			}//end of else
			//fetch the data from the data access layer and set the data to table object
			ArrayList alHospital=hospitalObject.getPreAuthHospitalList(hospitalListData.getSearchData());
			hospitalListData.setData(alHospital, "search");
			//set the table data object to session
			request.getSession().setAttribute("hospitalListData",hospitalListData);
			//finally return to the grid screen
			return this.getForward(strHospitalList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHospSearch));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside SelectHospitalAction doForward");
			String strHospitalList="";
			HospitalManager hospitalObject=this.getHospitalManagerObject();//get the session bean from the bean pool
			TableData hospitalListData = null;
			if((request.getSession()).getAttribute("hospitalListData") != null){
				hospitalListData = (TableData)(request.getSession()).getAttribute("hospitalListData");
			}//end of if((request.getSession()).getAttribute("hospitalListData") != null)
			else{
				hospitalListData = new TableData();
			}//end of else
			strHospitalList = this.getForwardPath(request);
			hospitalListData.modifySearchData(strForward);//modify the search data
			ArrayList alTariffItem=hospitalObject.getPreAuthHospitalList(hospitalListData.getSearchData());
			hospitalListData.setData(alTariffItem, strForward);//set the table data
			//set the table data object to session
			request.getSession().setAttribute("hospitalListData",hospitalListData);
			//finally return to the grid screen
			return this.getForward(strHospitalList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHospSearch));
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
	public ActionForward doBackWard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside SelectHospitalAction doBackWard");
			String strHospitalList="";
			HospitalManager hospitalObject=this.getHospitalManagerObject();  //get the session bean from the bean pool
			TableData hospitalListData = null;
			if((request.getSession()).getAttribute("hospitalListData") != null){
				hospitalListData = (TableData)(request.getSession()).getAttribute("hospitalListData");
			}//end of if((request.getSession()).getAttribute("hospitalListData") != null)
			else{
				hospitalListData = new TableData();
			}//end of else
			strHospitalList = this.getForwardPath(request);
			hospitalListData.modifySearchData(strBackward);//modify the search data
			ArrayList alTariffItem=hospitalObject.getPreAuthHospitalList(hospitalListData.getSearchData());
			hospitalListData.setData(alTariffItem, strBackward);//set the table data
			//set the table data object to session
			request.getSession().setAttribute("hospitalListData",hospitalListData);
			//finally return to the grid screen
			return this.getForward(strHospitalList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHospSearch));
		}//end of catch(Exception exp)
	}//end of doBackWard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to Select Hospital.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside SelectHospitalAction doSelectHospital ");
			TableData hospitalListData = null;
			String strGeneralDetail="";
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strActiveTab=TTKCommon.getActiveTab(request);
			Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");
			if((request.getSession()).getAttribute("hospitalListData") != null){
				hospitalListData = (TableData)(request.getSession()).getAttribute("hospitalListData");
			}//end of if((request.getSession()).getAttribute("hospitalListData") != null)
			else{
				hospitalListData = new TableData();
			}//end of else
			PreAuthHospitalVO preAuthHospitalVO=null;
			DynaActionForm frmPreAuthGeneral=null;
			if(strActiveLink.equals(strPreAuthorization))   //setting the forward paths based on the ActiveLink
			{
				strGeneralDetail=strPreAuthDetail;
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if (strActiveLink.equals(strClaims))//((strActiveLink.equals(strClaims)) || (strActiveLink.equals(strDataEntryClaims)))
			{
				strGeneralDetail=strClaimsdetail;
			}//end of else if (strActiveLink.equals(strClaims))
			else if(strActiveLink.equals(strSupport))
			{
				strGeneralDetail=strCourierDetail;
			}//end of else if(strActiveLink.equals(strSupport))

			if(strActiveLink.equals(strCustomerCare))
			{
				if(strActiveTab.equals(strSearch))
				{
					strGeneralDetail=strCallcenterSearch;
				}//end of if(strActiveTab.equals(strSearch))
				else if(strActiveTab.equals(strCallDetails))
				{
					strGeneralDetail=strCallcenterDetails;
				}//end of else if(strActiveTab.equals(strCallDetails))
			}//end of if(strActiveLink.equals(strCustomerCare))
			frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
			//on click of edit in hospital search screen
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				//get the selected hospital info
				preAuthHospitalVO = (PreAuthHospitalVO)hospitalListData.getRowInfo(
						Integer.parseInt(request.getParameter("rownum")));
				//when Pre-Auth Request comes
				if(strActiveLink.equals(strPreAuthorization)||strActiveLink.equals(strClaims))//||strActiveLink.equals(strDataEntryClaims)
				{
					//set the flag as discrepancy true if, hospital status is closed or dis-empaneled.
					if(!preAuthHospitalVO.getEmpanelStatusTypeID().equals("EMP"))
					{
						preAuthHospitalVO.setStatusDisYN("Y");
					}//end of if(preAuthHospitalVO.getEmpanelStatusTypeID().equals("DIS")||
					//preAuthHospitalVO.getEmpanelStatusTypeID().equals("CLS"))
					else
					{
						preAuthHospitalVO.setStatusDisYN("N");
					}//end of else if(preAuthHospitalVO.getEmpanelStatusTypeID().equals("DIS")||
					//preAuthHospitalVO.getEmpanelStatusTypeID().equals("CLS"))
					frmPreAuthGeneral.set("preAuthHospitalVO",FormUtils.setFormValues("frmPreAuthHospital",
																	preAuthHospitalVO,this,mapping,request));
					frmPreAuthGeneral.set("frmChanged","changed");
				}//end of if(strActiveLink.equals(strPreAuthorization))
				else if(strActiveLink.equals(strSupport))   //to send courier to a hospital
				{
					DynaActionForm frmCourierDetails=(DynaActionForm)
																request.getSession().getAttribute("frmCourierDetails");
					frmCourierDetails.set("hospSeqID",preAuthHospitalVO.getHospSeqId().toString());
					frmCourierDetails.set("hospName",preAuthHospitalVO.getHospitalName());
					frmCourierDetails.set("empanelmentNbr",preAuthHospitalVO.getEmplNumber());
					frmCourierDetails.set("frmChanged","changed");
				}//end of else if(strActiveLink.equals(strSupport))
				if(strActiveLink.equals(strCustomerCare))
				{
					if(strActiveTab.equals(strSearch))
					{
						DynaActionForm frmCallCenterList=(DynaActionForm)
															request.getSession().getAttribute("frmCallCenterList");
						if(frmCallCenterList != null)
						{
							frmCallCenterList.set("id",preAuthHospitalVO.getEmplNumber());
							frmCallCenterList.set("name",preAuthHospitalVO.getHospitalName());
							frmCallCenterList.set("seqID",preAuthHospitalVO.getHospSeqId().toString());
						}//end of if(frmCallCenterList != null)
					}//end of if(strActiveTab.equals(strSearch))
					else if(strActiveTab.equals(strCallDetails))
					{
						DynaActionForm frmCallCenterDetails=(DynaActionForm)
															request.getSession().getAttribute("frmCallCenterDetails");
						if(frmCallCenterDetails != null)
						{
							frmCallCenterDetails.set("empanelmentNbr",preAuthHospitalVO.getEmplNumber());
							frmCallCenterDetails.set("hospName",preAuthHospitalVO.getHospitalName());
							frmCallCenterDetails.set("hospSeqID",String.valueOf(preAuthHospitalVO.getHospSeqId()));
							frmCallCenterDetails.set("frmChanged","changed");
						}//end of if(frmCallCenterDetails != null)
					}//end of else if(strActiveTab.equals(strCallDetails))
				}//end of if(strActiveLink.equals(strCustomerCare))
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			toolBar.getConflictIcon().setVisible(toolBar.getConflictIcon().isVisible() && frmPreAuthGeneral.get("discPresentYN").equals("Y"));
			request.getSession().removeAttribute("hospitalListData");
			request.getSession().removeAttribute("frmSelectHospital");
			return this.getForward(strGeneralDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHospSearch));
		}//end of catch(Exception exp)
	}//end of doSelectHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns a string which contains the Forward Path
	 * @param request HttpServletRequest object which contains information required for buiding the Forward Path
	 * @return String which contains the comma separated sequence id's to be deleted
	 */
	private String getForwardPath(HttpServletRequest request) throws TTKException{
		String strHospitalList="";
		String strActiveLink=TTKCommon.getActiveLink(request);
		String strActiveTab=TTKCommon.getActiveTab(request);
		try{
			if(strActiveLink.equals(strPreAuthorization))   //setting the forward paths based on the ActiveLink
			{
				strHospitalList=strPreAuthHospitalList;
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if (strActiveLink.equals(strClaims))//((strActiveLink.equals(strClaims)) || (strActiveLink.equals(strDataEntryClaims)))
			{
				strHospitalList=strClaimsHospitalList;
			}//end of else if (strActiveLink.equals(strClaims))
			else if(strActiveLink.equals(strSupport))
			{
				strHospitalList=strCourierHospitalList;
			}//end of else if(strActiveLink.equals(strSupport))
			if(strActiveLink.equals(strCustomerCare))
			{
				if(strActiveTab.equals(strSearch))
				{
					strHospitalList=strCallcenterHospital;
				}//end of if(strActiveTab.equals(strSearch))
				else if(strActiveTab.equals(strCallDetails))
				{
					strHospitalList=strCallcenterDetailsHospital;
				}//end of else if(strActiveTab.equals(strCallDetails))
			}//end of if(strActiveLink.equals(strCustomerCare))
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strHospSearch);
		}//end of catch
		return strHospitalList;
	}//end of getForwardPath(HttpServletRequest request)

	/**
	 * Returns the ArrayList which contains the populated search criteria elements.
	 * @param frmSelectHospital DynaActionForm will contains the values of corresponding fields.
	 * @param request HttpServletRequest object which contains the search parameter that is built.
	 * @return alSearchParams ArrayList.
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSelectHospital,HttpServletRequest request)
																							throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSelectHospital.get("sEmpanelmentNo")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSelectHospital.get("sHospitalName")));
		alSearchParams.add((String)frmSelectHospital.get("stateCode"));
		alSearchParams.add((String)frmSelectHospital.get("cityCode"));
		if(TTKCommon.getActiveLink(request).equals(strSupport))
		{
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSelectHospital.get("sTTKReferenceNo")));
		}//end of if(TTKCommon.getActiveLink(request).equals("Support"))
		else
		{
			alSearchParams.add(null);
		}//end of else
		
		String GroupID=(String)request.getSession().getAttribute("groupId");//OPD_4_hosptial
		String paymentType=(String)request.getSession().getAttribute("paymentType");//OPD_4_hosptial				
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		alSearchParams.add(GroupID);//OPD_4_hosptial
		alSearchParams.add(paymentType);//OPD_4_hosptial
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmSelectHospital,HttpServletRequest request)

	/**
	 * Returns the HospitalManager session object for invoking methods on it.
	 * @return HospitalManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private HospitalManager getHospitalManagerObject() throws TTKException
	{
		HospitalManager hospManager = null;
		try
		{
			if(hospManager == null)
			{
				InitialContext ctx = new InitialContext();
				hospManager = (HospitalManager) ctx.lookup("java:global/TTKServices/business.ejb3/HospitalManagerBean!com.ttk.business.empanelment.HospitalManager");
			}//end if(hospManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strHospSearch);
		}//end of catch
		return hospManager;
	}//end getHospitalManagerObject()
}//end of SelectHospitalAction