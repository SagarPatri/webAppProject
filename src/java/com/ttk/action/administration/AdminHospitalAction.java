/**
 * @ (#) AdminHospitalAction.java Nov 08, 2005
 * Project       : Vidal Health TPA Services
 * File          : AdminHospitalAction.java
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Nov 08, 2005
 * @author       : Bhaskar Sandra
 * Modified by   : Lancy A
 * Modified date : Mar 14, 2006
 * Reason        : Changes in coding standards
 * Modified by   : Ramakrishna K M
 * Modified date : Mar 17, 2006
 * Reason        : Enhancements
 */

package com.ttk.action.administration;

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
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ProductVO;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.HospitalDetailVO;
import com.ttk.dto.empanelment.HospitalVO;
import com.ttk.dto.enrollment.PolicyDetailVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is reusable for searching the list of Asociated Hospitals  in Administration Products and Policies.
 * This class also provides option for removing the Asociated Hospitals.
 */

public class AdminHospitalAction extends TTKAction {
	 //Getting Logger for this Class file
	private static Logger log = Logger.getLogger( AdminHospitalAction.class ); 
	
	//Modes.
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	//private static final String strDisassociate="Disassociate";
	
	
	// Action mapping forwards.
	private static final String strProductHospitallist="producthospitallist";
	private static final String strPolicyHospitallist="policyhospitallist";
	private static final String strAddUpdateProductHospital="addupdateproducthospital";
	private static final String strAddUpdatePolicyHospital="addupdatepolicyhospital";
	private static final String strCopayment="copayment";	
	
	//Exception Message Identifier
	private static final String strHospitalssExp="hospitalss";
	
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
			log.debug("Inside the doDefault method of AdminHospitalAction");
			setLinks(request);
			String strForwards="";
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
												getAttribute("UserSecurityProfile")).getBranchID());
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			DynaActionForm frmAdminHospital = (DynaActionForm)request.getSession().getAttribute("frmAdminHospital");
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				strForwards=strProductHospitallist;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				strForwards=strPolicyHospitallist;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			if(TTKCommon.getWebBoardId(request)==null)
			{
				TTKException expTTK = new TTKException();
				if(TTKCommon.getActiveSubLink(request).equals("Products")){
					expTTK.setMessage("error.product.required");
				}//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
				else if(TTKCommon.getActiveSubLink(request).equals("Policies")){
					expTTK.setMessage("error.policy.required");
				}//end of if(TTKCommon.getActiveSubLink(request).equals("Policies"))
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request)==null)
			TableData tableData=null;
			//Check whether tableDataRevisePlan is null
			if(request.getSession().getAttribute("tableData")!=null)
				tableData= (TableData)(request.getSession()).getAttribute("tableData");
			tableData = new TableData();
			//get the current product by calling the database and add that to session
			ProductVO productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
			request.getSession().setAttribute("productVO",productVO);
			DynaActionForm frmHospitals=(DynaActionForm)form;
			//create the required grid table
			if(((String)frmHospitals.get("associateCode")).equals("EML")){
				tableData.createTableInfo("EmpHospitalTable",new ArrayList());
			}//end of if(((String)frmHospitals.get("associateCode")).equals("EML"))
			else{
				tableData.createTableInfo("HospitalTable",new ArrayList());
			}//end of else
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			frmAdminHospital.set("sOfficeInfo",strDefaultBranchID);
			getCaption(frmAdminHospital,request);
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospitalssExp));
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
			log.debug("Inside the doSearch method of AdminHospitalAction");
			setLinks(request);
			String strForwards="";
			HospitalManager hospitalManagerObject=this.getHospitalManagerObject();
			DynaActionForm frmAdminHospital = (DynaActionForm)request.getSession().getAttribute("frmAdminHospital");
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				strForwards=strProductHospitallist;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				strForwards=strPolicyHospitallist;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			TableData tableData=null;
			//Check whether tableData is null
			if(request.getSession().getAttribute("tableData")!=null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableData");
			}//end of if(request.getSession().getAttribute("tableData")!=null)
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return this.getForward(strForwards, mapping, request);
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
				if(((String)frmAdminHospital.get("associateCode")).equals("EML")){
					tableData.createTableInfo("EmpHospitalTable",null);
				}//end of if(((String)frmAdminHospital.get("associateCode")).equals("EML"))
				else{
					tableData.createTableInfo("HospitalTable",null);
				}//end of else
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alHospitalList=null;
			if(((String)frmAdminHospital.get("associateCode")).equals("EML")){
				//Populating Data Which matches the search criteria
				alHospitalList=hospitalManagerObject.getEmpanelledHospitalList(tableData.getSearchData()); 
			}//end of if(((String)frmAdminHospital.get("associateCode")).equals("EML"))
			else{
				//Populating Data Which matches the search criteria
				alHospitalList=hospitalManagerObject.getAssociatedExcludedList(tableData.getSearchData());
			}//end of else
			tableData.setData(alHospitalList, "search");
			//if Searched withVidal Health TPANetwork then Copay icon should be visible
			if("EML".equals(((DynaActionForm)form).getString("associateCode"))){
				((Column)((ArrayList)tableData.getTitle()).get(4)).setVisibility(true);
			}//end of if("ASL".equals(((DynaActionForm)form).getString("associateCode")))
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			getCaption(frmAdminHospital,request);
			return this.getForward(strForwards, mapping, request);  
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospitalssExp));
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
			log.debug("Inside the doBackward method of AdminHospitalAction");
			setLinks(request);
			String strForwards="";
			HospitalManager hospitalManagerObject=this.getHospitalManagerObject();
			DynaActionForm frmAdminHospital = (DynaActionForm)request.getSession().getAttribute("frmAdminHospital");
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				strForwards=strProductHospitallist;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				strForwards=strPolicyHospitallist;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			TableData tableData=null;
			// Check whether tableData is null
			if(request.getSession().getAttribute("tableData")!=null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableData");
			}//end of if(request.getSession().getAttribute("tableData")!=null)
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alHospitalList = null;
			if(((String)frmAdminHospital.get("associateCode")).equals("EML")){
				// Populating Data Which matches the search criteria
				alHospitalList=hospitalManagerObject.getEmpanelledHospitalList(tableData.getSearchData()); 
			}//end of if(((String)frmAdminHospital.get("associateCode")).equals("EML"))
			else{
				// Populating Data Which matches the search criteria
				alHospitalList=hospitalManagerObject.getAssociatedExcludedList(tableData.getSearchData()); 
			}//end of else
			tableData.setData(alHospitalList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strForwards, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospitalssExp));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doForward method of AdminHospitalAction");
			setLinks(request);
			String strForwards="";
			HospitalManager hospitalManagerObject=this.getHospitalManagerObject();
			DynaActionForm frmAdminHospital = (DynaActionForm)request.getSession().getAttribute("frmAdminHospital");
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				strForwards=strProductHospitallist;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				strForwards=strPolicyHospitallist;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			TableData tableData=null;
			if(request.getSession().getAttribute("tableData")!=null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableData");
			}//end of if(request.getSession().getAttribute("tableData")!=null)
			tableData.modifySearchData(strForward);//modify the search data
			ArrayList alHospitalList = null;
			if(((String)frmAdminHospital.get("associateCode")).equals("EML")){
				// Populating Data Which matches the search criteria
				alHospitalList=hospitalManagerObject.getEmpanelledHospitalList(tableData.getSearchData());
			}//end of if(((String)frmAdminHospital.get("associateCode")).equals("EML"))
			else{
				// Populating Data Which matches the search criteria
				alHospitalList=hospitalManagerObject.getAssociatedExcludedList(tableData.getSearchData()); 
			}//end of else
			tableData.setData(alHospitalList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strForwards, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospitalssExp));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to navigate to detail screen to view selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														  HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doViewHospital method of AdminHospitalAction");
			setLinks(request);
			String strForwards="";
			StringBuffer sbfCaption1= new StringBuffer();
			sbfCaption1.append("Edit");
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();			
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
												getAttribute("UserSecurityProfile")).getBranchID());
			HospitalDetailVO hospitalDetailVO=null;
			
			DynaActionForm hospitalForm=null;
			HospitalManager hospitalManagerObject=this.getHospitalManagerObject();
			TableData tableData=null;
			if(request.getSession().getAttribute("tableData")!=null)
				tableData= (TableData)(request.getSession()).getAttribute("tableData");
			if(!((String)request.getParameter("rownum")).equals(""))
			{
				HospitalVO hospitalVO=(HospitalVO)tableData.getRowInfo(Integer.parseInt((String)
																request.getParameter("rownum")));
				hospitalDetailVO= hospitalManagerObject.getHospitalDetail(hospitalVO.getHospSeqId());
				hospitalForm = (DynaActionForm)FormUtils.setFormValues("frmAdminAddUpdateHospital",hospitalDetailVO,
																							  this,mapping,request);
				hospitalForm.set("addressVO", (DynaActionForm)FormUtils.setFormValues("frmAdminHospitalAddress",
														hospitalDetailVO.getAddressVO(),this,mapping,request));				
				HashMap hmCityList = hospitalManagerObject.getCityInfo();
				ArrayList alCityList = new ArrayList();
				if(hmCityList!=null){
					alCityList = (ArrayList)hmCityList.get(hospitalDetailVO.getAddressVO().getStateCode());
				}//end of if(hmCityList!=null)
				hospitalForm.set("alCityList",alCityList);
			}//end of  if(!((String)request.getParameter("rownum")).equals(""))
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				ProductVO productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
				sbfCaption1.append(" [ ").append(productVO.getCompanyName()).append(" ]");
				sbfCaption1.append(" [ ").append(productVO.getProductName()).append(" ]");
				strForwards=strAddUpdateProductHospital;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products")
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				PolicyDetailVO policyDetailVO=productPolicyManagerObject.getPolicyDetail(
					  TTKCommon.getWebBoardId(request), TTKCommon.getUserSeqId(request));
				sbfCaption1.append(" [ ").append(policyDetailVO.getCompanyName()).append(" ]");
				sbfCaption1.append(" [ ").append(policyDetailVO.getPolicyNbr()).append(" ]");
				strForwards=strAddUpdatePolicyHospital;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			hospitalForm.set("caption",sbfCaption1.toString());
			request.getSession().setAttribute("frmAdminAddUpdateHospital",hospitalForm);
			hospitalForm.set("tpaOfficeSeqId",strDefaultBranchID);
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospitalssExp));
		}//end of catch(Exception exp)
	}//end of doViewHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doAdd method of AdminHospitalAction");
			setLinks(request);			
			String strForwards="";
			String strAssociateCode="";
			StringBuffer sbfCaption1= new StringBuffer();
			sbfCaption1.append("Add");
			DynaActionForm frmAdminHospital = (DynaActionForm)request.getSession().getAttribute("frmAdminHospital");
			strAssociateCode=(String)frmAdminHospital.get("associateCode");
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();			
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
												getAttribute("UserSecurityProfile")).getBranchID());
			HospitalDetailVO hospitalDetailVO=null;
			
			DynaActionForm hospitalForm=null;
			hospitalDetailVO=new HospitalDetailVO();
			hospitalDetailVO.setStausGnrlTypeId(strAssociateCode);
			AddressVO addressVO=new AddressVO();
			hospitalDetailVO.setAddressVO(addressVO);
			hospitalForm = (DynaActionForm)FormUtils.setFormValues("frmAdminAddUpdateHospital",
														hospitalDetailVO,this,mapping,request);
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				ProductVO productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
				sbfCaption1.append(" [ ").append(productVO.getCompanyName()).append(" ]");
				sbfCaption1.append(" [ ").append(productVO.getProductName()).append(" ]");
				strForwards=strAddUpdateProductHospital;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products")
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				PolicyDetailVO policyDetailVO=productPolicyManagerObject.getPolicyDetail(TTKCommon.getWebBoardId
																	(request), TTKCommon.getUserSeqId(request));				
				sbfCaption1.append(" [ ").append(policyDetailVO.getCompanyName()).append(" ]");				
				sbfCaption1.append(" [ ").append(policyDetailVO.getPolicyNbr()).append(" ]");
				strForwards=strAddUpdatePolicyHospital;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			hospitalForm.set("caption",sbfCaption1.toString());
			request.getSession().setAttribute("frmAdminAddUpdateHospital",hospitalForm);
			hospitalForm.set("tpaOfficeSeqId",strDefaultBranchID);
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospitalssExp));
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
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												  HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSave method of AdminHospitalAction");
			setLinks(request);
			String strForwards="";
			String strAssociateCode="";
			DynaActionForm hospitalForm=null;
			DynaActionForm frmAdminHospital = (DynaActionForm)request.getSession().getAttribute("frmAdminHospital");
			strAssociateCode=(String)frmAdminHospital.get("associateCode");
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			HospitalManager hospitalManagerObject=this.getHospitalManagerObject();			
			HospitalDetailVO hospitalDetailVO=null;
			AddressVO addressVO = null;
			StringBuffer sbfCaption1= new StringBuffer();
			DynaActionForm frmHospitalDetail=(DynaActionForm)form;
			hospitalDetailVO = (HospitalDetailVO)FormUtils.getFormValues(frmHospitalDetail, this, mapping, request);
			ActionForm frmAdminHospitalAddress=(ActionForm)frmHospitalDetail.get("addressVO");
			addressVO=(AddressVO)FormUtils.getFormValues(frmAdminHospitalAddress,
								 "frmAdminHospitalAddress",this,mapping,request);
			hospitalDetailVO.setAddressVO(addressVO);
			hospitalDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));////User Id
			hospitalDetailVO.setProdPolicySeqId(TTKCommon.getWebBoardId(request));
			hospitalDetailVO.setStausGnrlTypeId(strAssociateCode);
			hospitalDetailVO.setActiveLink(TTKCommon.getActiveLink(request));
			long lresult=hospitalManagerObject.addUpdateHospital(hospitalDetailVO);
			if(lresult!=0){
				ArrayList alCityList = new ArrayList();
				if(frmHospitalDetail.get("hospSeqId")!=null && !frmHospitalDetail.get("hospSeqId").equals(""))
				{
					request.setAttribute("updated","message.savedSuccessfully");
					hospitalDetailVO= hospitalManagerObject.getHospitalDetail(hospitalDetailVO.getHospSeqId());
					sbfCaption1.append("Edit");
					HashMap hmCityList = hospitalManagerObject.getCityInfo();
					if(hmCityList!=null){
						alCityList = (ArrayList)hmCityList.get(hospitalDetailVO.getAddressVO().getStateCode());
					}//end of if(hmCityList!=null)
				}//end of if(frmHospitalDetail.get("hospSeqId")!=null && !frmHospitalDetail.
					//get("hospSeqId").equals(""))
				else
				{
					frmHospitalDetail.initialize(mapping);
					hospitalDetailVO=new HospitalDetailVO();
					hospitalDetailVO.setStausGnrlTypeId(strAssociateCode);
					addressVO=new AddressVO();
					hospitalDetailVO.setAddressVO(addressVO);
					request.setAttribute("updated","message.addedSuccessfully");
					sbfCaption1.append("Add");
				}//end of if(frmHospitalDetail.get("HospSeqId").equals(""))
				hospitalForm = (DynaActionForm)FormUtils.setFormValues("frmAdminAddUpdateHospital",
														    hospitalDetailVO,this,mapping,request);
				hospitalForm.set("addressVO", (DynaActionForm)FormUtils.setFormValues("frmAdminHospitalAddress",
														 hospitalDetailVO.getAddressVO(),this,mapping,request));
				hospitalForm.set("alCityList",alCityList);
			}//end of if(lresult!=0)
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				ProductVO productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
				sbfCaption1.append(" [ ").append(productVO.getCompanyName()).append(" ]");
				sbfCaption1.append(" [ ").append(productVO.getProductName()).append(" ]");
				strForwards=strAddUpdateProductHospital;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products")
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				PolicyDetailVO policyDetailVO=productPolicyManagerObject.getPolicyDetail(TTKCommon.getWebBoardId(
																	  request), TTKCommon.getUserSeqId(request));
				sbfCaption1.append(" [ ").append(policyDetailVO.getCompanyName()).append(" ]");
				sbfCaption1.append(" [ ").append(policyDetailVO.getPolicyNbr()).append(" ]");
				strForwards=strAddUpdatePolicyHospital;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))			
			hospitalForm.set("caption",sbfCaption1.toString());
			request.getSession().setAttribute("frmAdminAddUpdateHospital",hospitalForm);
			return this.getForward(strForwards,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospitalssExp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
		log.debug("Inside the doChangeWebBoard method of AdminHospitalAction");
		return doDefault(mapping,form,request,response);
	}//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
												  HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doReset method of AdminHospitalAction");
			setLinks(request);
			String strForwards="";
			DynaActionForm hospitalForm=null;
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			HospitalManager hospitalManagerObject=this.getHospitalManagerObject();			
			HospitalDetailVO hospitalDetailVO=null;
			AddressVO addressVO = null;
			StringBuffer sbfCaption1= new StringBuffer();
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
											   getAttribute("UserSecurityProfile")).getBranchID());
			DynaActionForm frmHospitalDetail=(DynaActionForm)form;
			if(frmHospitalDetail.get("hospSeqId")!=null&&!frmHospitalDetail.get("hospSeqId").equals(""))
			{
				//calling business layer to get the hospital detail
				hospitalDetailVO = hospitalManagerObject.getHospitalDetail(TTKCommon.getLong((String)
																frmHospitalDetail.get("hospSeqId")));
				hospitalForm = (DynaActionForm)FormUtils.setFormValues("frmAdminAddUpdateHospital",
															hospitalDetailVO,this,mapping,request);
				hospitalForm.set("addressVO", (DynaActionForm)FormUtils.setFormValues("frmAdminHospitalAddress",
														 hospitalDetailVO.getAddressVO(),this,mapping,request));
				sbfCaption1.append("Edit ");
				HashMap hmCityList = hospitalManagerObject.getCityInfo();
				ArrayList alCityList = new ArrayList();
				if(hmCityList!=null){
					alCityList = (ArrayList)hmCityList.get(hospitalDetailVO.getAddressVO().getStateCode());
				}//end of if(hmCityList!=null)
				hospitalForm.set("alCityList",alCityList);
			}//end of if(frmHospitalDetail.get("hospSeqId")!=null&&!frmHospitalDetail.get("hospSeqId").equals(""))
			else
			{
				frmHospitalDetail.initialize(mapping);
				hospitalDetailVO = new HospitalDetailVO();
				addressVO = new AddressVO();
				hospitalDetailVO.setAddressVO(addressVO);
				hospitalForm = (DynaActionForm)FormUtils.setFormValues("frmAdminAddUpdateHospital",
															hospitalDetailVO,this,mapping,request);
				hospitalForm.set("addressVO", (DynaActionForm)FormUtils.setFormValues("frmAdminHospitalAddress",
														 hospitalDetailVO.getAddressVO(),this,mapping,request));
				sbfCaption1.append("Add ");
			}//end of else
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				ProductVO productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
				sbfCaption1.append(" [ ").append(productVO.getCompanyName()).append(" ]");
				sbfCaption1.append(" [ ").append(productVO.getProductName()).append(" ]");
				strForwards=strAddUpdateProductHospital;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products")
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				PolicyDetailVO policyDetailVO=productPolicyManagerObject.getPolicyDetail(TTKCommon.getWebBoardId(
																	  request), TTKCommon.getUserSeqId(request));
				sbfCaption1.append(" [ ").append(policyDetailVO.getCompanyName()).append(" ]");
				sbfCaption1.append(" [ ").append(policyDetailVO.getPolicyNbr()).append(" ]");
				strForwards=strAddUpdatePolicyHospital;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			hospitalForm.set("caption",sbfCaption1.toString());
			request.getSession().setAttribute("frmAdminAddUpdateHospital",hospitalForm);
			hospitalForm.set("tpaOfficeSeqId",strDefaultBranchID);
			return this.getForward(strForwards,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospitalssExp));
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
			log.debug("Inside the doClose method of AdminHospitalAction");
			setLinks(request);
			String strForwards="";
			HospitalManager hospitalManagerObject=this.getHospitalManagerObject();
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				strForwards=strProductHospitallist;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				strForwards=strPolicyHospitallist;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			TableData tableData=null;
			if(request.getSession().getAttribute("tableData")!=null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableData");
			}//end of if(request.getSession().getAttribute("tableData")!=null)
			request.getSession().removeAttribute("associateCode");
			request.getSession().removeAttribute("HospitalVO");
			if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			{
				DynaActionForm frmHospitalDetail=(DynaActionForm)form;
				ArrayList alHospitalList=null;
				//refresh the data in Close mode, in order to get the new records if any.
				if(((String)frmHospitalDetail.get("associateCode")).equals("EML")){
					alHospitalList=hospitalManagerObject.getEmpanelledHospitalList(tableData.getSearchData());
				}//end of if(((String)frmHospitalDetail.get("associateCode")).equals("EML"))
				else{
					alHospitalList=hospitalManagerObject.getAssociatedExcludedList(tableData.getSearchData());
				}//end of else
				if(frmHospitalDetail.get("associateCode")!=null){
					frmHospitalDetail.set("associateCode",frmHospitalDetail.get("associateCode"));
				}//end of if(frmHospitalDetail.get("associateCode")!=null)
				tableData.setData(alHospitalList);
				request.getSession().setAttribute("tableData",tableData);
				getCaption(frmHospitalDetail,request);
			}//end of if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospitalssExp));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doStateChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														 HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doStateChange method of AdminHospitalAction");
			setLinks(request);
			String strForwards="";
			HospitalDetailVO hospitalDetailVO=null;
			HospitalManager hospitalManagerObject=this.getHospitalManagerObject();
			DynaActionForm frmHospitalDetail=(DynaActionForm)form;
			hospitalDetailVO = (HospitalDetailVO)FormUtils.getFormValues(frmHospitalDetail, this, mapping, request);
			HashMap hmCityList = hospitalManagerObject.getCityInfo();
			ArrayList alCityList = new ArrayList();
			if(hmCityList!=null){
				alCityList = (ArrayList)hmCityList.get(hospitalDetailVO.getAddressVO().getStateCode());
			}//end of if(hmCityList!=null)
			if(alCityList==null){
				alCityList=new ArrayList();
			}//end of if(alCityList==null)
			frmHospitalDetail.set("frmChanged","changed");
			frmHospitalDetail.set("alCityList",alCityList);
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				strForwards=strAddUpdateProductHospital;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				strForwards=strAddUpdatePolicyHospital;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospitalssExp));
		}//end of catch(Exception exp)
	}//end of doStateChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
	/**
	 * This method is used to associate the hospital.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAssociate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													   HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doAssociate method of AdminHospitalAction");
			setLinks(request);
			String strForwards="";
			String strAssociateCode="";
			HospitalManager hospitalManagerObject=this.getHospitalManagerObject();
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				strForwards=strProductHospitallist;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				strForwards=strPolicyHospitallist;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies")) 
			TableData tableData=null;
			if(request.getSession().getAttribute("tableData")!=null)
				tableData= (TableData)(request.getSession()).getAttribute("tableData");
			DynaActionForm frmAdminHospital = (DynaActionForm)request.getSession().getAttribute("frmAdminHospital");
			strAssociateCode=(String)frmAdminHospital.get("associateCode");
			DynaActionForm frmHospitals=(DynaActionForm)form;
			ArrayList alDisAssoicateHospList=this.getHospList(request);
			strAssociateCode="ASL";
			int iResult=hospitalManagerObject.associateExcludeHospital(TTKCommon.getWebBoardId(request),
															   strAssociateCode,alDisAssoicateHospList);
			log.debug("iResult value is :"+iResult);
			//fetch the records from the criteria found in session
			ArrayList alHospitalList=null;
			int iCount=TTKCommon.deleteStringLength((String)alDisAssoicateHospList.get(0), "|");
			if(((String)frmHospitals.get("associateCode")).equals("EML")){
				// Populating Data Which matches the search criteria
				alHospitalList=hospitalManagerObject.getEmpanelledHospitalList(tableData.getSearchData());
			}//end of if(((String)frmHospitals.get("associateCode")).equals("EML"))
			else{
				// Populating Data Which matches the search criteria
				alHospitalList=hospitalManagerObject.getAssociatedExcludedList(tableData.getSearchData());
			}//end of else
			//fetch the data from previous set of rowcounts, if all the records are deleted for the current 
			//set of search criteria
			if(alHospitalList.size() == 0 || iCount == tableData.getData().size())
			{
				tableData.modifySearchData("Delete");//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
																			getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alHospitalList=hospitalManagerObject.getEmpanelledHospitalList(tableData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if(alHospitalList.size() == 0 || iCount == tableData.getData().size())
			tableData.setData(alHospitalList, "Delete");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospitalssExp));
		}//end of catch(Exception exp)
	}//end of doAssociate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
	/**
	 * This method is used to exclude the hospital.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doExclude(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												     HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doExclude method of AdminHospitalAction");
			setLinks(request);
			String strForwards="";
			String strAssociateCode="";
			HospitalManager hospitalManagerObject=this.getHospitalManagerObject();
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				strForwards=strProductHospitallist;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				strForwards=strPolicyHospitallist;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			TableData tableData=null;
			if(request.getSession().getAttribute("tableData")!=null)
				tableData= (TableData)(request.getSession()).getAttribute("tableData");
			DynaActionForm frmAdminHospital = (DynaActionForm)request.getSession().getAttribute("frmAdminHospital");
			strAssociateCode=(String)frmAdminHospital.get("associateCode");
			DynaActionForm frmHospitals=(DynaActionForm)form;
			ArrayList alDisAssoicateHospList=this.getHospList(request);
			strAssociateCode="EXL";
			int iResult=hospitalManagerObject.associateExcludeHospital(TTKCommon.getWebBoardId(request),
																strAssociateCode,alDisAssoicateHospList);
			log.debug("iResult value is :"+iResult);
			//fetch the records from the criteria found in session
			ArrayList alHospitalList=null;
			int iCount=TTKCommon.deleteStringLength((String)alDisAssoicateHospList.get(0), "|");
			if(((String)frmHospitals.get("associateCode")).equals("EML")){
				// Populating Data Which matches the search criteria
				alHospitalList=hospitalManagerObject.getEmpanelledHospitalList(tableData.getSearchData()); 	
			}//end of if(((String)frmHospitals.get("associateCode")).equals("EML"))
			else{
				// Populating Data Which matches the search criteria
				alHospitalList=hospitalManagerObject.getAssociatedExcludedList(tableData.getSearchData());
			}//end of else
			//fetch the data from previous set of rowcounts, if all the records are deleted for the 
			//current set of search criteria
			if(alHospitalList.size() == 0 || iCount == tableData.getData().size())
			{
				tableData.modifySearchData("Delete");//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
																			getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alHospitalList=hospitalManagerObject.getEmpanelledHospitalList(tableData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if(alHospitalList.size() == 0 || iCount == tableData.getData().size())
			tableData.setData(alHospitalList, "Delete");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospitalssExp));
		}//end of catch(Exception exp)
	}//end of doExclude(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to remove the hospital from the list.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doRemove(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doRemove method of AdminHospitalAction");
			setLinks(request);
			String strForwards="";
			HospitalManager hospitalManagerObject=this.getHospitalManagerObject();
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				strForwards=strProductHospitallist;
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				strForwards=strPolicyHospitallist;
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			TableData tableData=null;
			if(request.getSession().getAttribute("tableData")!=null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableData");
			}//end of if(request.getSession().getAttribute("tableData")!=null)
			ArrayList alDisAssoicateHospList=this.getHospList(request);
			int iResult=hospitalManagerObject.disassociateHospital(alDisAssoicateHospList);
			log.debug("iResult Value is :"+iResult);
			ArrayList alHospitalList=null;
			int iCount=TTKCommon.deleteStringLength((String)alDisAssoicateHospList.get(0), "|");
			// Populating Data Which matches the search criteria
			alHospitalList=hospitalManagerObject.getAssociatedExcludedList(tableData.getSearchData()); 
			//fetch the data from previous set of rowcounts, if all the records are deleted for the current 
			//set of search criteria
			if(alHospitalList.size() == 0 || iCount == tableData.getData().size())
			{
				tableData.modifySearchData("Delete");//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
																			getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alHospitalList=hospitalManagerObject.getAssociatedExcludedList(tableData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if(alHospitalList.size() == 0 || iCount == tableData.getData().size())
			else
			{
				alHospitalList=hospitalManagerObject.getAssociatedExcludedList(tableData.getSearchData());
			}//end of else
			tableData.setData(alHospitalList, "Delete");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospitalssExp));
		}//end of catch(Exception exp)
	}//end of doRemove(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method called when Copay icon is clicked in the grid or Copay button is clicked	 
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doCopay(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														  HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doCopay method of AdminHospitalAction");
			setLinks(request);
			TableData tableData=null;
			ArrayList<Object> alHospCopay = null;
			if(request.getSession().getAttribute("tableData")!=null){
				tableData= (TableData)(request.getSession()).getAttribute("tableData");
			}//end of if(request.getSession().getAttribute("tableData")!=null)				
			if(!("").equals(request.getParameter("rownum")))
			{
				HospitalVO hospitalVO=(HospitalVO)tableData.getRowInfo(
					Integer.parseInt((String)request.getParameter("rownum")));				
				alHospCopay = new ArrayList<Object>();
				alHospCopay.add("|"+hospitalVO.getHospSeqId()+"|");
				request.setAttribute("alHospcopay",alHospCopay);
				request.setAttribute("hospitalVO",hospitalVO);
				log.info("copay amt is " + hospitalVO.getCopayAmt());
				log.info("copay percentage is " + hospitalVO.getCopayPerc());
			}//end of  if(!((String)request.getParameter("rownum")).equals(""))
			else {				
				//returns hospseqid concatenated with '|'
				String strConcatHospSeqID = ((this.getHospList(request).get(0))).toString();
				alHospCopay = new ArrayList<Object>();
				alHospCopay.add(strConcatHospSeqID);
				request.setAttribute("alHospcopay",alHospCopay);
			}//end of else
			return mapping.findForward(strCopayment);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospitalssExp));
		}//end of catch(Exception exp)
	}//end of doCopay(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
	/**
	 * This method called when Copay icon is clicked in the grid or Copay button is clicked	 
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDeleteCopay(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														  HttpServletResponse response) throws Exception{
		try{
			log.info("Inside the doDeleteCopay method of AdminHospitalAction");
			setLinks(request);
			HospitalManager hospitalManager = this.getHospitalManagerObject();
			//TableData tableData=null;
			ArrayList<Object> alHospCopay = new ArrayList<Object>();
			long lngProdPolSeqID = TTKCommon.getWebBoardId(request);
			/*if(request.getSession().getAttribute("tableData")!=null){
				tableData= (TableData)(request.getSession()).getAttribute("tableData");
			}//end of if(request.getSession().getAttribute("tableData")!=null)			
*/			log.info("rownum is :"+request.getParameter("rownum"));
//			if(!("").equals(request.getParameter("rownum")))//			{
								
				String strHospSeqIDs = this.getHospSeqIDList(request);
				alHospCopay.add(strHospSeqIDs);
				alHospCopay.add(lngProdPolSeqID);
				int intCount = hospitalManager.deleteAssocHospCopay(alHospCopay);
				log.info("Count value is : " + intCount);	
				if(intCount > 0)
				{
					request.setAttribute("updated","message.removedSuccessfully");/////
				}
//			}//end of  if(!((String)request.getParameter("rownum")).equals(""))
//			else {				
//				//returns hospseqid concatenated with '|'
//				String strConcatHospSeqID = ((this.getHospList(request).get(0))).toString();
//				alHospCopay = new ArrayList<Object>();
//				alHospCopay.add(strConcatHospSeqID);
//				request.setAttribute("alHospcopay",alHospCopay);
//			}//end of else
				doSearch(mapping,form,request,response);
			return mapping.findForward(strProductHospitallist);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospitalssExp));
		}//end of catch(Exception exp)
	}//end of doDeleteCopay(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
	/**
	 * This method is used to manipulate the caption used in the screens.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @throws Exception if any error occurs
	 */    
	private void getCaption(DynaActionForm frmAdminHospital,HttpServletRequest request)throws TTKException
	{
		try
		{
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			frmAdminHospital = (DynaActionForm)request.getSession().getAttribute("frmAdminHospital");
			String strCompanyName="";
			if(TTKCommon.getActiveSubLink(request).equals("Products"))
			{
				ProductVO productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
				strCompanyName=productVO.getCompanyName();
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Products")
			else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			{
				PolicyDetailVO policyDetailVO=productPolicyManagerObject.getPolicyDetail(TTKCommon.getWebBoardId(
																	  request), TTKCommon.getUserSeqId(request));
				strCompanyName=policyDetailVO.getCompanyName();
			}//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
			String strAssociateCode="";
			StringBuffer sbfCaption= new StringBuffer();
			strAssociateCode=(String)frmAdminHospital.get("associateCode");
			if(strAssociateCode.equals("") || strAssociateCode.equals("ASL"))
			{
				sbfCaption.append("List of  Associated Hospitals - [").append(strCompanyName).append("]");
			}//end of if(strAssociateCode.equals("") || strAssociateCode.equals("ASL"))
			else if(strAssociateCode.equals("EML"))
			{
				sbfCaption.append("List of  Empanelled Hospitals - [").append(strCompanyName).append("]");
			}//end of if(strAssociateCode.equals("EML"))
			else if(strAssociateCode.equals("EXL"))
			{
				sbfCaption.append("List of  Excluded  Hospitals - [").append(strCompanyName).append("]");
			}//end of if(strAssociateCode.equals("EXL"))
			frmAdminHospital.set("caption",String.valueOf(sbfCaption));
		}
		catch(Exception exp)
		{
			throw new TTKException(exp, strHospitalssExp);
		}//end of catch
	}// end of getCaption(DynaActionForm frmOffice,HttpServletRequest request)
	
	/**	@description This method returns ArrayList of hospitals id's and user sequence 
	 * id which have been choosen by using Check box(s).
	 * 	@param HttpServletRequest request
	 * 	@return ArrayList
	 */
	private ArrayList getHospList(HttpServletRequest request) {
		ArrayList <Object>alHospitalList=new ArrayList<Object>();
		StringBuffer sbfId=new StringBuffer();
		String strChOpt[]=request.getParameterValues("chkopt");
		TableData tableData=(TableData)request.getSession().getAttribute("tableData");
		if((strChOpt!=null)&&strChOpt.length!=0)
		{
			if(request.getParameter("mode").equals("doRemove"))
			{
				for(int intCounter=0;intCounter<strChOpt.length;intCounter++)
				{
					if(strChOpt[intCounter]!=null)
					{
						if(intCounter==0){
							sbfId.append(String.valueOf(((HospitalVO)tableData.getRowInfo(Integer.parseInt
									(strChOpt[intCounter]))).getProdHospSeqId()));
						}//end of if(intCounter==0)
						else{
							sbfId=sbfId.append("|").append(String.valueOf(((HospitalVO)tableData.getRowInfo
									(Integer.parseInt(strChOpt[intCounter]))).getProdHospSeqId()));
						}//end of else
					} // end of if(strChOpt[intCounter]!=null)
					
				} //end of for loop
			} //if(request.getParameter("mode").equals(strDisassociate))
			else
			{
				for(int intCounter=0;intCounter<strChOpt.length;intCounter++)
				{
					if(strChOpt[intCounter]!=null)
					{
						if(intCounter==0){
							sbfId.append(String.valueOf(((HospitalVO)tableData.getRowInfo(Integer.parseInt
									(strChOpt[intCounter]))).getHospSeqId()));
						}//end of if(intCounter==0)
						else{
							sbfId=sbfId.append("|").append(String.valueOf(((HospitalVO)tableData.getRowInfo(
									Integer.parseInt(strChOpt[intCounter]))).getHospSeqId()));
						}//end of else
					} // end of if(strChOpt[intCounter]!=null)
				} // end of for loop
			} // end of else
		}//end of if((strChOpt!=null)&&strChOpt.length!=0)		
		alHospitalList.add("|"+sbfId+"|");
		alHospitalList.add(TTKCommon.getUserSeqId(request));
		return alHospitalList;
	}//end of getHospList(HttpServletRequest request)
	
	/**	@description This method returns ArrayList of hospitals id's and user sequence 
	 * id which have been choosen by using Check box(s).
	 * 	@param HttpServletRequest request
	 * 	@return ArrayList
	 */
	private String getHospSeqIDList(HttpServletRequest request) {
		StringBuffer sbfId=new StringBuffer();
		String strChOpt[]=request.getParameterValues("chkopt");
		TableData tableData=(TableData)request.getSession().getAttribute("tableData");
		
		for(int intCounter=0;intCounter<strChOpt.length;intCounter++)
		{
			if(strChOpt[intCounter]!=null)
			{
				if(intCounter==0){
					sbfId.append(String.valueOf(((HospitalVO)tableData.getRowInfo(Integer.parseInt
							(strChOpt[intCounter]))).getHospSeqId()));
				}//end of if(intCounter==0)
				else{
					sbfId=sbfId.append("|").append(String.valueOf(((HospitalVO)tableData.getRowInfo(
							Integer.parseInt(strChOpt[intCounter]))).getHospSeqId()));
				}//end of else
			} // end of if(strChOpt[intCounter]!=null)
		} // end of for loop	
		return "|"+sbfId+"|";
	}//end of getHospList(HttpServletRequest request)
	
	/**
	 * Returns the HospitalManager session object for invoking methods on it.
	 * @return HospitalManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private HospitalManager getHospitalManagerObject() throws TTKException
	{
		HospitalManager hospitalManager = null;
		try
		{
			if(hospitalManager == null)
			{
				InitialContext ctx = new InitialContext();
				hospitalManager = (HospitalManager) ctx.lookup("java:global/TTKServices/business.ejb3/HospitalManagerBean!com.ttk.business.empanelment.HospitalManager");
			}//end if(hospitalManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "AdminHospitalList");
		}//end of catch
		return hospitalManager;
	}//end getHospitalManagerObject()
	
	/**
	 * Return the ArrayList populated with Search criteria elements
	 * @param DynaActionForm
	 * @param HttpServletRequest
	 * @return ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmAdminProductHospital,HttpServletRequest request)
	{
		ArrayList <Object> alSearchParams =new ArrayList<Object>();
		String strAssociateCode=((String)frmAdminProductHospital.get("associateCode")).equals("")?"ASL":
													(String)frmAdminProductHospital.get("associateCode");
		alSearchParams.add(new SearchCriteria("PROD_POLICY_SEQ_ID",TTKCommon.getWebBoardId(request).toString()));
		if(!strAssociateCode.equals("EML"))
		{   
			alSearchParams.add(new SearchCriteria("STATUS_GENERAL_TYPE_ID",strAssociateCode));
		}//end of if(!strAssociateCode.equals("EML"))
		alSearchParams.add(new SearchCriteria("EMPANEL_NUMBER",TTKCommon.replaceSingleQots((String)
										 frmAdminProductHospital.get("sEmpanelmentNO")),"equals"));
		alSearchParams.add(new SearchCriteria("HOSP_NAME",TTKCommon.replaceSingleQots((String)
												  frmAdminProductHospital.get("sHospName"))));
		alSearchParams.add(new SearchCriteria("TPA_CITY_CODE.CITY_TYPE_ID",(String)frmAdminProductHospital.
																			   get("sCityCode"),"equals"));
		alSearchParams.add(new SearchCriteria("TPA_OFFICE_INFO.TPA_OFFICE_SEQ_ID",(String)
									frmAdminProductHospital.get("sOfficeInfo"),"equals"));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmAdminProductHospital,HttpServletRequest request)
	
	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return ProductPolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ProductPolicyManager getProductPolicyManagerObject() throws TTKException
	{
		ProductPolicyManager productPolicyManager = null;
		try
		{
			if(productPolicyManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyManager = (ProductPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProductPolicyManagerBean!com.ttk.business.administration.ProductPolicyManager");
			}//end if(productPolicyManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "ProductManager");
		}//end of catch
		return productPolicyManager;
	}//end of getProductPolicyManagerObject()
} // end of AdminHospitalAction