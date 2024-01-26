/**
 * @ (#) InsuranceCompanyAction.java Nov 21, 2005
 * Project 		: TTK HealthCare Services
 * File 			: InsuranceCompanyAction.java
 * Author 		: Pradeep R
 * Company 		: Span Systems Corporation
 * Date Created 	: Nov 21, 2005
 *
 * @author 		: Pradeep R
 * Modified by 	: Lancy A
 * Modified date : Mar 10, 2006
 * Reason 		: Changes in coding standards
 */

package com.ttk.action.empanelment;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.business.empanelment.InsuranceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.common.tags.preauth.IcsPcsHistory;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.InsuranceDetailVO;
import com.ttk.dto.empanelment.InsuranceVO;
//added for CR KOC Mail-SMS Notification for Cigna
import com.ttk.dto.empanelment.MailNotificationVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used to search the insurance company,add that to webboard. and
 * also has the mode add/edit and delete the insurance companies at various
 * level.
 */

public class InsuranceCompanyAction extends TTKAction {

	private static Logger log = Logger.getLogger(InsuranceCompanyAction.class);
	// declaration of modes
	private static final String strForward = "Forward";
	private static final String strBackward = "Backward";
	// declaration of forward paths
	private static final String strInsuranceList = "insurancelist";
	private static final String strCompanySummary = "companysummary";
	private static final String strInscompanyDetail = "editinscompany";

	// Exception Message Identifier
	private static final String strInsError = "insurance";

	/**
	 * This method is used to initialize the search grid. Finally it forwards to
	 * the appropriate view based on the specified forward mappings
	 *
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doDefault(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside the doDefault method of InsuranceCompanyAction");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			// remove the selected office from the session
			request.getSession().removeAttribute("SelectedOffice");
			tableData = new TableData();// create new table data object
			// create the required grid table
			tableData.createTableInfo("InsuranceCompanyTable", new ArrayList());
			request.getSession().setAttribute("tableData", tableData);
			((DynaActionForm) form).initialize(mapping);// reset the form data
			return this.getForward(strInsuranceList, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)
	}// end of doDefault(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to search the data with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified
	 * forward mappings
	 *
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside the doSearch method of InsuranceCompanyAction");
			setLinks(request);
			String strPageID = TTKCommon.checkNull(request
					.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request
					.getParameter("sortId"));
			TableData tableData = TTKCommon.getTableData(request);
			InsuranceManager insuranceObject = (InsuranceManager) this
					.getInsuranceManagerObject();
			// if the page number or sort id is clicked
			if (!strPageID.equals("") || !strSortID.equals("")) {
				if (!strPageID.equals("")) {
					tableData.setCurrentPage(Integer.parseInt(TTKCommon
							.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strInsuranceList);
				}// /end of if(!strPageID.equals(""))
				else {
					tableData.setSortData(TTKCommon.checkNull(request
							.getParameter("sortId")));
					tableData.modifySearchData("sort");// modify the search data
				}// end of else
			}// end of if(!strPageID.equals("") || !strSortID.equals(""))
			else {
				// create the required grid table
				tableData.createTableInfo("InsuranceCompanyTable", null);
				tableData.setSearchData(this.populateSearchCriteria(
						(DynaActionForm) form, request));
				tableData.modifySearchData("search");
			}// end of else
			ArrayList alCompanies = insuranceObject
					.getInsuranceCompanyList(tableData.getSearchData());
			tableData.setData(alCompanies, "search");
			// set the table data object to session
			request.getSession().setAttribute("tableData", tableData);
			// finally return to the grid screen
			return this.getForward(strInsuranceList, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)
	}// end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	/**
	 * This method is used to get the previous set of records with the given
	 * search criteria. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 *
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doBackward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside the doBackward method of InsuranceCompanyAction");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			InsuranceManager insuranceObject = (InsuranceManager) this
					.getInsuranceManagerObject();
			tableData.modifySearchData(strBackward);// modify the search data
			ArrayList alCompanies = insuranceObject
					.getInsuranceCompanyList(tableData.getSearchData());
			tableData.setData(alCompanies, strBackward);// set the table data
			request.getSession().setAttribute("tableData", tableData);// set the
																		// table
																		// data
																		// object
																		// to
																		// session
			return this.getForward(strInsuranceList, mapping, request);// finally
																		// return
																		// to
																		// the
																		// grid
																		// screen
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)
	}// end of doBackward(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to get the next set of records with the given search
	 * criteria. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 *
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doForward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside the doForward method of InsuranceCompanyAction");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			InsuranceManager insuranceObject = (InsuranceManager) this
					.getInsuranceManagerObject();
			tableData.modifySearchData(strForward);// modify the search data
			ArrayList alCompanies = insuranceObject
					.getInsuranceCompanyList(tableData.getSearchData());
			tableData.setData(alCompanies, strForward);// set the table data
			request.getSession().setAttribute("tableData", tableData);// set the
																		// table
																		// data
																		// object
																		// to
																		// session
			return this.getForward(strInsuranceList, mapping, request);// finally
																		// return
																		// to
																		// the
																		// grid
																		// screen
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)
	}// end of doForward(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to copy the selected records to web-board. Finally it
	 * forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doCopyToWebBoard(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside the doCopyToWebBoard method of InsuranceCompanyAction");
			setLinks(request);
			this.populateWebBoard(request);
			return this.getForward(strInsuranceList, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)
	}// end of doCopyToWebBoard(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to copy the selected records to web-board and
	 * navigate the page to Company Details. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 *
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doViewCompanySummary(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.info("Inside the doViewCompanySummary method of InsuranceCompanyAction");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmSearchInsurance = (DynaActionForm) form;
			InsuranceVO insuranceVO = null;
			if (!(TTKCommon.checkNull(request.getParameter("rownum"))
					.equals(""))) {
				insuranceVO = (InsuranceVO) tableData.getRowInfo(Integer
						.parseInt((String) (frmSearchInsurance).get("rownum")));

				// add the selected item to the web board and make it as default
				// selected
				this.addToWebBoard(insuranceVO, request);
			}// end if()
			return mapping.findForward(strCompanySummary);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)
	}// end of doViewCompanySummary(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is called from the struts framework. This method is used to
	 * navigate to detail screen to add a record. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 *
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside the doAdd method of InsuranceCompanyAction");
			setLinks(request);
			DynaActionForm frmInsCompanyDetails = (DynaActionForm) form;
			InsuranceDetailVO insDetailVO = new InsuranceDetailVO();
			AddressVO addressVO=new AddressVO();
			StringBuffer sbfCaption = new StringBuffer();
			UserSecurityProfile userSecurityProfile = (UserSecurityProfile) request
					.getSession().getAttribute("UserSecurityProfile");

			// code to get the Next Abbreviation for Insurance
			InsuranceManager insuranceObject = (InsuranceManager) this
					.getInsuranceManagerObject();
			String insAbbrCode = insuranceObject.getMaxAbbrevationCode();
			log.info("before insAbbrCode::" + insAbbrCode);
			AutoIncrementAbbr autoIncrementAbbr = new AutoIncrementAbbr();
			insAbbrCode = autoIncrementAbbr.getInsAbbrevation(insAbbrCode);
			log.info("after insAbbrCode::" + insAbbrCode);
			ArrayList alCityList = new ArrayList();
			// load the Hashmap which will consists of office type to be added
			// based on the present office selected.
			HashMap<Object, Object> hmOfficeType = new HashMap<Object, Object>();
			hmOfficeType.put("IHO", "IRO");
			hmOfficeType.put("IRO", "IDO");
			hmOfficeType.put("IDO", "IBO");
			sbfCaption.append("Add");
			// get the parentcompany's information to add child company
			// contains the information of the selected office
			InsuranceVO headOfficeVO = new InsuranceVO();
			InsuranceVO insuranceVO = (InsuranceVO) request.getSession()
					.getAttribute("SelectedOffice");
			// returns the information of the head office
			// System.out.println("request.getSession().getAttribute HeadOffice; >>"+request.getSession().getAttribute("HeadOffice"));
			headOfficeVO = (InsuranceVO) request.getSession().getAttribute("HeadOffice");
			addressVO.setCountryCode("123");
			addressVO.setStateCode("DXB");
			insDetailVO.setAddress(addressVO);
			if (insuranceVO != null && headOfficeVO != null)// for adding
															// regional,divisional
															// and branch
															// offices
			{
				insDetailVO.setParentInsSeqID(insuranceVO.getInsuranceSeqID());
				insDetailVO.setOfficeType((String) hmOfficeType.get(insuranceVO
						.getOfficeType()));
				insDetailVO.setCompanyName(headOfficeVO.getCompanyName());
				insDetailVO.setSectorTypeCode(headOfficeVO.getSectorTypeCode());
				insDetailVO.setCompanyAbbreviation(headOfficeVO
						.getCompanyAbbreviation());
				insDetailVO.setSectorTypeDesc(headOfficeVO.getSectorTypeDesc());
				insDetailVO.setCommonAuthorityCode(headOfficeVO
						.getCommonAuthorityCode());
				sbfCaption.append(" [").append(insuranceVO.getBranchName())
						.append("]");
			}// end of if(insuranceVO!=null)
			else // for adding head office set office type as IHO
			{
				insDetailVO.setOfficeType("IHO");
			}// end of else
			insDetailVO.setCompanyStatus("Y"); // in add mode set the insurance
												// office as active by default
			frmInsCompanyDetails = this.setFormValues(insDetailVO, mapping,
					request);
			frmInsCompanyDetails.set("caption", sbfCaption.toString());// set
																		// the
																		// caption
																		// to
																		// displayed
																		// in
																		// jsp
																		// page
			frmInsCompanyDetails.set("companyAbbreviation", insAbbrCode);
			if (userSecurityProfile.getBranchID() != null) {
				frmInsCompanyDetails.set("TTKBranchCode", userSecurityProfile
						.getBranchID().toString());
			}// end of if(userSecurityProfile.getBranchID()!=null)
			else {
				frmInsCompanyDetails.set("TTKBranchCode", "");
			}// end of else
			frmInsCompanyDetails.set("alCityList", alCityList);
			request.getSession().setAttribute("frmInsCompanyDetails",
					frmInsCompanyDetails);
			return this.getForward(strInscompanyDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)
	}// end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	/**
	 * This method is used to navigate to detail screen to edit selected record.
	 * Finally it forwards to the appropriate view based on the specified
	 * forward mappings
	 *
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.info("Inside the doEdit method of InsuranceCompanyAction");
			setLinks(request);
			DynaActionForm frmInsCompanyDetails = (DynaActionForm) form;
			InsuranceDetailVO insDetailVO = null;
			StringBuffer sbfCaption = new StringBuffer();
			InsuranceManager insuranceObject = (InsuranceManager) this
					.getInsuranceManagerObject();
			HospitalManager hospitalObject = this.getHospitalManagerObject();
			HashMap hmCityList = null;
			/*
			 * UserSecurityProfile
			 * userSecurityProfile=(UserSecurityProfile)request.getSession().
			 * getAttribute("UserSecurityProfile");
			 */
			ArrayList alCityList = null;
			Long lngHeadOffInsSeqId = null;
			// initialize the form bean
			frmInsCompanyDetails.initialize(mapping);
			sbfCaption.append("Edit");
			// get the head off seq id from webboard
			lngHeadOffInsSeqId = new Long(TTKCommon.getWebBoardId(request));// get
																			// the
																			// web
																			// board
																			// id

			// get the minimum info from the session to get all the details of
			// company to edit
			// get the minimum info of the selected office to edited
			InsuranceVO insuranceVO = (InsuranceVO) request.getSession()
					.getAttribute("SelectedOffice");

			// call the DAO to get all details from the minimum info
			insDetailVO = insuranceObject.getInsuranceCompanyDetail(
					lngHeadOffInsSeqId, insuranceVO.getInsuranceSeqID());
			hmCityList = hospitalObject.getCityInfo();
			
		/*	System.out.println("City list..." + hmCityList + "Address1..."
					+ insDetailVO);
			System.out.println("City list..." + hmCityList + "Address.2.."
					+ insDetailVO.getAddress());*/
			System.out.println("getAddress ->"+insDetailVO.getAddress());	
			System.out.println("getStateCode ->"+insDetailVO.getAddress().getStateCode());

			if (hmCityList != null) {
				alCityList = (ArrayList) hmCityList.get(insDetailVO
						.getAddress().getStateCode());
			}// end of if(hmCityList!=null)

			// set the form bean from the VO
			frmInsCompanyDetails = this.setFormValues(insDetailVO, mapping,
					request);
			if (!insDetailVO.getOfficeType().equals("IHO")) {
				sbfCaption.append(" [").append(insDetailVO.getCompanyName())
						.append("-").append(insDetailVO.getCompanyCode())
						.append("]");
			}// end of if(!insDetailVO.getOfficeType().equals("IHO"))
			frmInsCompanyDetails.set("caption", sbfCaption.toString());
			frmInsCompanyDetails.set("alCityList", alCityList);

			frmInsCompanyDetails
					.set("authority", insDetailVO.getAuthStandard());

			request.getSession().setAttribute("frmInsCompanyDetails",
					frmInsCompanyDetails);
			return this.getForward(strInscompanyDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)
	}// end of doEdit(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	/**
	 * This method is used to add/update the record. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 *
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside the doSave method of InsuranceCompanyAction");
			setLinks(request);
			InsuranceManager insuranceObject = (InsuranceManager) this
					.getInsuranceManagerObject();
			HospitalManager hospitalObject = this.getHospitalManagerObject();
			HashMap hmCityList = null;
			DynaActionForm frmInsCompanyDetails = (DynaActionForm) form;
			String authority = frmInsCompanyDetails.getString("authStandard");
			InsuranceDetailVO insDetailVO = new InsuranceDetailVO();
			ArrayList alCityList = new ArrayList();
			Long lHeadOffInsSeqId = null;
			StringBuffer sbfCaption = new StringBuffer();
			HashMap<Object, Object> hmOfficeType = new HashMap<Object, Object>();
			hmOfficeType.put("IHO", "IRO");
			hmOfficeType.put("IRO", "IDO");
			hmOfficeType.put("IDO", "IBO");
			// prepare the vo to add/edit the compnay
			insDetailVO = this.getFormValues(frmInsCompanyDetails, mapping,
					request);
			// call the DAO method to update the Compnay

			HttpSession session = request.getSession();
			String productIds = (String) session.getAttribute("productList");

			insDetailVO.setProductIdList(productIds);

			Long lInsSeqId = insuranceObject
					.addUpdateInsuranceCompany(insDetailVO);
			if (lInsSeqId > 0) {
				if (insDetailVO.getInsuranceSeqID() == null) // executed when
																// office is
																// added
				{
					String strCompanyName = insDetailVO.getCompanyName();
					// initialize the form bean and InsuranceDetailVO
					frmInsCompanyDetails.initialize(mapping);
					sbfCaption.append("Add");
					insDetailVO = new InsuranceDetailVO();
					// get the parentcompany's information to add child company
					// get the selected office info
					InsuranceVO insuranceVO = (InsuranceVO) request
							.getSession().getAttribute("SelectedOffice");
					// get the head office info
					InsuranceVO headOfficeVO = (InsuranceVO) request
							.getSession().getAttribute("HeadOffice");
					// for adding regional,divisional and branch offices
					if (insuranceVO != null && headOfficeVO != null) {
						insDetailVO.setParentInsSeqID(insuranceVO
								.getInsuranceSeqID());
						insDetailVO.setOfficeType((String) hmOfficeType
								.get(insuranceVO.getOfficeType()));
						insDetailVO.setCompanyName(headOfficeVO
								.getCompanyName());
						insDetailVO.setSectorTypeCode(headOfficeVO
								.getSectorTypeCode());
						insDetailVO.setSectorTypeDesc(headOfficeVO
								.getSectorTypeDesc());
						insDetailVO.setCompanyAbbreviation(headOfficeVO
								.getCompanyAbbreviation());
						insDetailVO.setCommonAuthorityCode(headOfficeVO
								.getCommonAuthorityCode());

						sbfCaption.append(" [")
								.append(insuranceVO.getBranchName())
								.append("]");
					}// end of if(insuranceVO!=null)
					else {
						// to add the head office to web board and refresh the
						// cache
						insuranceVO = new InsuranceVO();
						insuranceVO.setInsuranceSeqID(lInsSeqId);
						insuranceVO.setCompanyName(strCompanyName);
						this.addToWebBoard(insuranceVO, request);
						// refresh the cache so that changes are seen in other
						// screens
						Cache.refresh("insuranceCompany");
						return mapping.findForward(strCompanySummary);
					}// end of else
					request.setAttribute("updated", "message.addedSuccessfully");
				}// end of if(frmCompanyDetails.get("insCompSeqId").equals(""))
				else {
					// get the head off seq id from webboard
					lHeadOffInsSeqId = new Long(
							TTKCommon.getWebBoardId(request));// get the web
																// board id
					// reload the form after updating in edit mode
					insDetailVO = insuranceObject.getInsuranceCompanyDetail(
							lHeadOffInsSeqId, lInsSeqId);
					// this.valueObjectToForm(insDetailVO,frmCompanyDetails,request);
					sbfCaption.append("Edit");
					if (insDetailVO.getOfficeType().equals("IHO")) {
						Cache.refresh("insuranceCompany");
						request.setAttribute("cacheId",
								String.valueOf(lInsSeqId));
						request.setAttribute("cacheDesc",
								insDetailVO.getCompanyName());
						// to modify the changes in webboard for chenges made
						// here
						TTKCommon.modifyWebBoardId(request);
					}// end of if(insDetailVO.getOfficeType().equals("IHO"))
					else {
						// if office type is other than the Head office add
						// office info to the caption
						sbfCaption.append(" [")
								.append(insDetailVO.getCompanyName())
								.append("-")
								.append(insDetailVO.getCompanyCode())
								.append("]");
					}// end of else
					hmCityList = hospitalObject.getCityInfo();
					if (hmCityList != null)
						alCityList = (ArrayList) hmCityList.get(insDetailVO
								.getAddress().getStateCode());
					request.setAttribute("updated", "message.savedSuccessfully");
				}// end of else
				frmInsCompanyDetails = this.setFormValues(insDetailVO, mapping,
						request);
				frmInsCompanyDetails.set("caption", sbfCaption.toString());
				frmInsCompanyDetails.set("alCityList", alCityList);
				frmInsCompanyDetails.set("authority", authority);
				request.getSession().setAttribute("frmInsCompanyDetails",
						frmInsCompanyDetails);
				// REFRESH THE CACHE TO GET THE UPDATED PAYER LIST IN DROP LISTS
				Cache.refresh("payerCode");
				Cache.refresh("payerCodeSearch");

				session.removeAttribute("productList");

			}// end of if(lInsSeqId>0)
			return this.getForward(strInscompanyDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)
	}// end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	/**
	 * This method is used to load cities based on the selected state. Finally
	 * it forwards to the appropriate view based on the specified forward
	 * mappings
	 *
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doChangeState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside the doChangeState method of InsuranceCompanyAction");
			setLinks(request);
			DynaActionForm frmInsCompanyDetails = (DynaActionForm) form;
			InsuranceDetailVO insDetailVO = null;
			HospitalManager hospitalObject = this.getHospitalManagerObject();
			HashMap hmCityList = null;
			ArrayList alCityList = null;

			insDetailVO = this.getFormValues(frmInsCompanyDetails, mapping,
					request);
			String stateCode = (String) insDetailVO.getAddress().getStateCode();
			request.getSession().setAttribute(stateCode, "stateCode");
			hmCityList = hospitalObject.getCityInfo(stateCode);
			// hmCityList=hospitalObject.getCityInfo();
			if (hmCityList != null) {
				alCityList = (ArrayList) hmCityList.get(insDetailVO
						.getAddress().getStateCode());
			}// end of if(hmCityList!=null)
			if (alCityList == null) {
				alCityList = new ArrayList();
			}// end of if(alCityList==null)
			String countryCode = (String) (hmCityList.get("CountryId"));
			int isdcode = TTKCommon
					.getInt((String) (hmCityList.get("isdcode")));
			int stdcode = TTKCommon
					.getInt((String) (hmCityList.get("stdcode")));

			if (insDetailVO.getAddress() != null) {
				insDetailVO.getAddress().setCountryCode(countryCode);
				insDetailVO.getAddress().setIsdCode(isdcode);
				insDetailVO.getAddress().setStdCode(stdcode);
				frmInsCompanyDetails.set("addressVO",
						(DynaActionForm) FormUtils.setFormValues(
								"frmInsCompanyAddress",
								insDetailVO.getAddress(), this, mapping,
								request));
			}// end of if(insDetailVO.getAddress()!=null)
			frmInsCompanyDetails.set("frmChanged", "changed");
			frmInsCompanyDetails.set("alCityList", alCityList);
			return this.getForward(strInscompanyDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)
	}// end of doChangeState(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to navigate to previous screen when closed button is
	 * clicked. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 *
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doClose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside the doClose method of InsuranceCompanyAction");
			setLinks(request);
			return this.getForward(strInsuranceList, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)
	}// end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	/**
	 * This method is used to delete the selected record(s) in Search Grid.
	 * Finally it forwards to the appropriate view based on the specified
	 * forward mappings
	 *
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doDeleteList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside the doDeleteList method of InsuranceCompanyAction");
			setLinks(request);
			InsuranceManager insuranceObject = (InsuranceManager) this
					.getInsuranceManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			String strDeleteId = "";
			// populate the delete string which contains the sequence id's to be
			// deleted
			strDeleteId = populateDeleteId(request, (TableData) request
					.getSession().getAttribute("tableData"));
			// delete the Insurance Details
			int iCount = insuranceObject.deleteInsuranceCompany(strDeleteId);
			// refresh the grid with search data in session
			ArrayList alInsurance = null;
			// fetch the data from previous set of rowcounts, if all the records
			// are deleted for the current set of search criteria
			if (iCount == tableData.getData().size()) {
				tableData.modifySearchData("DeleteList");// modify the search
															// data
				int iStartRowCount = Integer.parseInt((String) tableData
						.getSearchData().get(
								tableData.getSearchData().size() - 2));
				if (iStartRowCount > 0) {
					alInsurance = insuranceObject
							.getInsuranceCompanyList(tableData.getSearchData());
				}// end of if(iStartRowCount > 0)
			}// end if(iCount == tableData.getData().size())
			else {
				alInsurance = insuranceObject.getInsuranceCompanyList(tableData
						.getSearchData());
			}// end of else
			tableData.setData(alInsurance, "DeleteList");
			request.getSession().setAttribute("tableData", tableData);
			return this.getForward(strInsuranceList, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)
	}// end of doDeleteList(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to delete the selected record. Finally it forwards to
	 * the appropriate view based on the specified forward mappings
	 *
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside the doDelete method of InsuranceCompanyAction");
			setLinks(request);
			InsuranceManager insuranceObject = (InsuranceManager) this
					.getInsuranceManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			String strDeleteId = "";
			strDeleteId = ""
					+ ((InsuranceVO) ((TableData) request.getSession()
							.getAttribute("tableData")).getData().get(
							Integer.parseInt(request.getParameter("rownum"))))
							.getInsuranceSeqID();
			// delete the Insurance Details
			int iCount = insuranceObject.deleteInsuranceCompany(strDeleteId);
			// refresh the grid with search data in session
			ArrayList alInsurance = null;
			// fetch the data from previous set of rowcounts, if all the records
			// are deleted for the current set of search criteria
			if (iCount == tableData.getData().size()) {
				tableData.modifySearchData("Delete");// modify the search data
				int iStartRowCount = Integer.parseInt((String) tableData
						.getSearchData().get(
								tableData.getSearchData().size() - 2));
				if (iStartRowCount > 0) {
					alInsurance = insuranceObject
							.getInsuranceCompanyList(tableData.getSearchData());
				}// end of if(iStartRowCount > 0)
			}// end if(iCount == tableData.getData().size())
			else {
				alInsurance = insuranceObject.getInsuranceCompanyList(tableData
						.getSearchData());
			}// end of else
			tableData.setData(alInsurance, "Delete");
			request.getSession().setAttribute("tableData", tableData);
			return this.getForward(strInsuranceList, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)
	}// end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	/**
	 * Returns a string which contains the comma separated sequence id's to be
	 * deleted
	 * 
	 * @param request
	 *            HttpServletRequest object which contains information about the
	 *            selected check boxes
	 * @param tableData
	 *            TableData object which contains the value objects
	 * @return String which contains the comma separated sequence id's to be
	 *         deleted
	 * @throws TTKException
	 *             If any run time Excepton occures
	 */
	private String populateDeleteId(HttpServletRequest request,
			TableData tableData) throws TTKException {
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if (strChk != null && strChk.length != 0) {
			for (int i = 0; i < strChk.length; i++) {
				if (strChk[i] != null) {
					// extract the sequence id to be deleted from the value
					// object
					if (i == 0) {
						sbfDeleteId.append("|").append(
								String.valueOf(((InsuranceVO) tableData
										.getData().get(
												Integer.parseInt(strChk[i])))
										.getInsuranceSeqID().intValue()));
					}// end of if(i == 0)
					else {
						sbfDeleteId = sbfDeleteId.append("|").append(
								String.valueOf(((InsuranceVO) tableData
										.getData().get(
												Integer.parseInt(strChk[i])))
										.getInsuranceSeqID().intValue()));
					}// end of else
				}// end of if(strChk[i]!=null)
			}// end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId = sbfDeleteId.append("|");
		}// end of if(strChk!=null&&strChk.length!=0)
		return sbfDeleteId.toString();
	}// end of populateDeleteId(HttpServletRequest request, TableData tableData)

	/**
	 * Returns the InsuranceManager session object for invoking methods on it.
	 * 
	 * @return InsuranceManager session object which can be used for method
	 *         invokation
	 * @exception throws TTKException
	 */
	private InsuranceManager getInsuranceManagerObject() throws TTKException {
		InsuranceManager insuremanager = null;
		try {
			if (insuremanager == null) {
				InitialContext ctx = new InitialContext();
				insuremanager = (InsuranceManager) ctx
						.lookup("java:global/TTKServices/business.ejb3/InsuranceManagerBean!com.ttk.business.empanelment.InsuranceManager");
			}// end if
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, strInsError);
		}// end of catch
		return insuremanager;
	} // end of private InsuranceManager getInsManagerObject() throws
		// TTKException

	/**
	 * Returns the HospitalManager session object for invoking methods on it.
	 * 
	 * @return HospitalManager session object which can be used for method
	 *         invokation
	 * @exception throws TTKException
	 */
	private HospitalManager getHospitalManagerObject() throws TTKException {
		HospitalManager hospManager = null;
		try {
			if (hospManager == null) {
				InitialContext ctx = new InitialContext();
				hospManager = (HospitalManager) ctx
						.lookup("java:global/TTKServices/business.ejb3/HospitalManagerBean!com.ttk.business.empanelment.HospitalManager");
			}// end if(hospManager == null)
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, strInsError);
		}// end of catch
		return hospManager;
	}// end getHospitalManagerObject()

	/**
	 * Return the ArrayList populated with Search criteria elements
	 * 
	 * @param DynaActionForm
	 *            will contain the values that are entered in the corresponding
	 *            fields
	 * @param HttpServletRequest
	 *            object will contain the searchparameter that is built.
	 * @return ArrayList
	 * @throws TTKException
	 *             If any run time Excepton occures
	 */
	private ArrayList<Object> populateSearchCriteria(
			DynaActionForm frmSearchInsurance, HttpServletRequest request)
			throws TTKException {
		// build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(new SearchCriteria("EMPANELED_DATE",
				(String) frmSearchInsurance.get("sEmpanelDate")));
		alSearchParams.add(new SearchCriteria("INS_COMP_NAME", TTKCommon
				.replaceSingleQots((String) frmSearchInsurance
						.get("sCompanyName"))));
		alSearchParams.add(new SearchCriteria("INS_COMP_CODE_NUMBER", TTKCommon
				.replaceSingleQots((String) frmSearchInsurance
						.get("sCompanyCodeNbr"))));
		alSearchParams.add(new SearchCriteria("PAYER_AUTHORITY", TTKCommon
				.replaceSingleQots((String) frmSearchInsurance
						.get("productAuthority"))));

		return alSearchParams;
	}// end of populateSearchCriteria(DynaActionForm userForm)

	/**
	 * Populates the web board in the session with the selected items in the
	 * grid
	 * 
	 * @param request
	 *            HttpServletRequest object which contains information about the
	 *            selected check boxes
	 * @throws TTKException
	 *             If any run time Excepton occures
	 */
	private void populateWebBoard(HttpServletRequest request)
			throws TTKException {
		String[] strChk = request.getParameterValues("chkopt");
		TableData tableData = (TableData) request.getSession().getAttribute(
				"tableData");
		Toolbar toolbar = (Toolbar) request.getSession()
				.getAttribute("toolbar");
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		CacheObject cacheObject = null;
		if (strChk != null && strChk.length != 0) {
			for (int i = 0; i < strChk.length; i++) {
				cacheObject = new CacheObject();
				cacheObject.setCacheId(String.valueOf(((InsuranceVO) tableData
						.getData().get(Integer.parseInt(strChk[i])))
						.getInsuranceSeqID()));
				cacheObject.setCacheDesc(((InsuranceVO) tableData.getData()
						.get(Integer.parseInt(strChk[i]))).getCompanyName());
				alCacheObject.add(cacheObject);
			}// end of for(int i=0; i<strChk.length;i++)
		}// end of if(strChk!=null&&strChk.length!=0)
		if (toolbar != null)
			toolbar.getWebBoard().addToWebBoardList(alCacheObject);
	}// end of populateWebBoard(HttpServletRequest request)

	/**
	 * Adds the selected item to the web board and makes it as the selected item
	 * in the web board
	 * 
	 * @param insuranceVO
	 *            InsuranceVO object which contains the information of the
	 *            Insurance Office
	 * @param request
	 *            HttpServletRequest
	 * @throws TTKException
	 *             if any runtime exception occures
	 */
	private void addToWebBoard(InsuranceVO insuranceVO,
			HttpServletRequest request) throws TTKException {
		Toolbar toolbar = (Toolbar) request.getSession()
				.getAttribute("toolbar");
		CacheObject cacheObject = new CacheObject();
		cacheObject.setCacheId(String.valueOf(insuranceVO.getInsuranceSeqID()));
		cacheObject.setCacheDesc(insuranceVO.getCompanyName());
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		alCacheObject.add(cacheObject);
		// if the object(s) are added to the web board, set the current web
		// board id
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
		request.setAttribute("webboardinvoked", "true");
	}// end of addToWebBoard(InsuranceDetailVO insDetailVO, HttpServletRequest
		// request)

	/**
	 * Populates the value object to form element.
	 * 
	 * @param insDetailVO
	 *            InsuranceDetailVO object.
	 * @param mapping
	 *            The ActionMapping used to select this instance.
	 * @param request
	 *            HttpServletRequest current request object
	 * @return DynaActionForm object.
	 */
	private DynaActionForm setFormValues(InsuranceDetailVO insDetailVO,
			ActionMapping mapping, HttpServletRequest request)
			throws TTKException {
		try {
			DynaActionForm frmInsCompanyDetails = (DynaActionForm) FormUtils
					.setFormValues("frmInsCompanyDetails", insDetailVO, this,
							mapping, request);
			if (insDetailVO.getAddress() != null) {
				frmInsCompanyDetails.set("addressVO",
						(DynaActionForm) FormUtils.setFormValues(
								"frmInsCompanyAddress",
								insDetailVO.getAddress(), this, mapping,
								request));
			}// end of if(insDetailVO.getAddress()!=null)
			else {
				frmInsCompanyDetails.set("addressVO",
						(DynaActionForm) FormUtils.setFormValues(
								"frmInsCompanyAddress", new AddressVO(), this,
								mapping, request));
			}// end of else
				// added for CR KOC Mail-SMS Notification
			if (insDetailVO.getMailnotification() != null) {
				frmInsCompanyDetails.set("mailnotificationVO",
						(DynaActionForm) FormUtils.setFormValues(
								"frmInsCompanyMailNotification",
								insDetailVO.getMailnotification(), this,
								mapping, request));
			} else {
				frmInsCompanyDetails.set("mailnotificationVO",
						(DynaActionForm) FormUtils.setFormValues(
								"frmInsCompanyMailNotification",
								new MailNotificationVO(), this, mapping,
								request));
			}
			return frmInsCompanyDetails;
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, strInsError);
		}// end of catch
	}// end of setFormValues()

	/**
	 * Populates the form elements to value object .
	 * 
	 * @param frmInsCompanyDetails
	 *            DynaActionForm.
	 * @param mapping
	 *            The ActionMapping used to select this instance.
	 * @param request
	 *            HttpServletRequest cuurent request object
	 * @return insDetailVO InsuranceDetailVO object.
	 */
	private InsuranceDetailVO getFormValues(
			DynaActionForm frmInsCompanyDetails, ActionMapping mapping,
			HttpServletRequest request) throws TTKException {
		try {
			InsuranceDetailVO insDetailVO = null;
			AddressVO addressVO = null;
			MailNotificationVO mailnotificationVO = null;
			if (request.getParameter("companyStatus") == null) {
				frmInsCompanyDetails.set("companyStatus", "N");
			}// end of if(request.getParameter("companyStatus")==null)
			insDetailVO = (InsuranceDetailVO) FormUtils.getFormValues(
					frmInsCompanyDetails, "frmInsCompanyDetails", this,
					mapping, request);

			ActionForm addressForm = (ActionForm) frmInsCompanyDetails
					.get("addressVO");
			// added for CR KOC Mail-SMS Notification for Cigna
			ActionForm mailNotificationForm = (ActionForm) frmInsCompanyDetails
					.get("mailnotificationVO");
			mailnotificationVO = (MailNotificationVO) FormUtils.getFormValues(
					mailNotificationForm, "frmInsCompanyMailNotification",
					this, mapping, request);
			addressVO = (AddressVO) FormUtils.getFormValues(addressForm,
					"frmInsCompanyAddress", this, mapping, request);
			insDetailVO.setAddress(addressVO);
			insDetailVO.setMailnotification(mailnotificationVO);
			insDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			return insDetailVO;
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, "insurance");
		}// end of catch
	}// end of getFormValues()

	public ActionForward doSelectProduct(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			log.debug("Inside HospitalSearchAction doSelectProduct");

			// String strOfficeSeqID =
			// TTKCommon.checkNull(request.getParameter("insuranceSeqID"));
			// Long insSeqid=TTKCommon.getWebBoardId(request);

			DynaActionForm frmInsCompanyDetails = (DynaActionForm) form;
			// String path = "Empanelment.Insurance.Company Details";
			HttpSession session = request.getSession();
			String reforward = request.getParameter("reforward");

			String strOfficeSeqID = request.getParameter("insuranceSeqID");
			String regAuthority = request.getParameter("authStandard");
			String strCompanyName = request.getParameter("companyName");
			String companyCode = request.getParameter("companyCode");

			session.setAttribute("regAuthority", regAuthority);
			// Long lngHospSeqId = Long.parseLong(hospSeqId);

			setLinks(request);
			// HospitalManager hospitalObject=this.getHospitalManagerObject();
			InsuranceManager insuranceObject = (InsuranceManager) this
					.getInsuranceManagerObject();
			InsuranceDetailVO insDetailVO = new InsuranceDetailVO();

			insDetailVO.setRegAuthority(regAuthority);
			insDetailVO.setCompanyName(strCompanyName);
			insDetailVO.setCompanyCode(companyCode);

			if (!"".equals(TTKCommon.checkNull(strOfficeSeqID))) {
				insDetailVO.setInsuranceSeqID(Long.parseLong(strOfficeSeqID));
			}

			// if ("SelectProduct".equalsIgnoreCase(reforward)) {

			TableData tableData = new TableData(); // create new

			tableData.createTableInfo("ProductListVatTable", new ArrayList());
			tableData.modifySearchData("search");
			ArrayList alProductList = null;
			alProductList = insuranceObject.getVatProductList(insDetailVO);

			tableData.setData(alProductList, "search");
			request.getSession().setAttribute("tableData", tableData);

			session.setAttribute("frmInsCompanyDetails", frmInsCompanyDetails);

			String path = "doSelectProduct";
			return mapping.findForward(path);
			// }
			// session.setAttribute("frmInsCompanyDetails",
			// frmInsCompanyDetails);
			// return mapping.findForward(path);
		}

		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}

	}

	public ActionForward doCloseVat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside the doCloseVat method of InsuranceCompanyAction");
			setLinks(request);
			DynaActionForm frmInsCompanyDetails = (DynaActionForm) form;
			HttpSession session = request.getSession();
			// String regAuthority =
			// frmInsCompanyDetails.getString("regAuthority");

			String regAuthority = (String) session.getAttribute("regAuthority");

			frmInsCompanyDetails.set("authority", regAuthority);
			return this.getForward("editinscompany", mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)
	}// end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	public ActionForward saveVatProduct(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			log.info("Inside the saveVatProduct method of InsuranceCompanyAction");
			setLinks(request);
			DynaActionForm frmInsCompanyDetails = (DynaActionForm) form;
			String[] strChk = request.getParameterValues("chkopt");
			TableData tableData = (TableData) request.getSession()
					.getAttribute("tableData");
			String refFlag = "";
			String csv = "";
			StringBuffer sbProdIds = new StringBuffer("|");
			InsuranceManager insuranceObject = (InsuranceManager) this
					.getInsuranceManagerObject();
			String insSeqId = "";

			if (strChk != null && strChk.length != 0) {
				for (int i = 0; i < strChk.length; i++) {
					refFlag = "Y";

					String productId = String
							.valueOf(((InsuranceDetailVO) tableData.getData()
									.get(Integer.parseInt(strChk[i])))
									.getProductSeqId());
					insSeqId = String.valueOf(((InsuranceDetailVO) tableData
							.getData().get(Integer.parseInt(strChk[i])))
							.getInsuranceSeqID());
					sbProdIds = sbProdIds.append(productId);
					sbProdIds = sbProdIds.append("|");
				}

				HttpSession session = request.getSession();
				session.setAttribute("productList", sbProdIds.toString());
				// String
				// lInsSeqId=insuranceObject.saveVatProductList(insSeqId,sbProdIds.toString(),refFlag);
				request.setAttribute("updated", "message.addedSuccessfully");
			}

			return mapping.findForward("doSelectProduct");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)

	}

	public ActionForward doChangeAuthority(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		try {
			log.debug("Inside the doChangeAuthority method of InsuranceCompanyAction");
			setLinks(request);
			DynaActionForm frmInsCompanyDetails = (DynaActionForm) form;
			String healthAuthority = request.getParameter("authStandard");

			String vatpercent = "";
			if ("HAAD".equals(healthAuthority)) {

				vatpercent = "5.00";

			}

			else {
				vatpercent = "0.00";
			}

			frmInsCompanyDetails.set("authority", healthAuthority);
			frmInsCompanyDetails.set("vatPercent", vatpercent);

			// return this.getForward("doSelectProduct", mapping, request);
			return mapping.findForward(strInscompanyDetail);

		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strInsError));
		}// end of catch(Exception exp)

	}

} // end of InsuranceCompanyAction