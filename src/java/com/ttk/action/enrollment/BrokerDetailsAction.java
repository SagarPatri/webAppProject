/**
 * @ (#) PolicyDetailsAction.java Feb 3, 2006
 * Project       : TTK HealthCare Services
 * File          : PolicyDetailsAction.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Feb 3, 2006
 *
 * @author       : Srikanth H M
 * Modified by   : Arun K N
 * Modified date : May 8, 2007
 * Reason        : Conversion to dispatch action
 */

package com.ttk.action.enrollment;

import java.io.PrintWriter;
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
import com.ttk.business.empanelment.BrokerManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.BrokerVO;
import com.ttk.dto.empanelment.ContactVO;
/**
 * This class is reusable one, used to add/edit individual policy,individual
 * policy as group, corporate policies and non corporate policies in enrollment
 * and endorsement flow. This class is also used to list corporate /NonCorporate
 * Group for the policy,and promoting the policy to the next level.
 */
public class BrokerDetailsAction extends TTKAction {
	private static Logger log = Logger.getLogger(BrokerDetailsAction.class);

	private static final String strBrokerDetails = "brokerdetails";
	private static final String strBrokeruserdetails = "brokeruserdetails";
	private static final String strForward = "Forward";
	private static final String strBackward = "Backward";
	private static final String strcorppolicydetails = "corppolicydetails";
	private static final String strSaveBrokerList="SaveList";
	
	
	 private static final String strIndPolicy="Individual Policy";
	 private static final String strIndPolicyType="IP";
	 private static final String strIndGrpPolicy="Ind. Policy as Group";
	 private static final String strGrpPolicyType="IG";
	 private static final String strCorporatePolicy="Corporate Policy";
	 private static final String strCorpPolicyType="CP";
	 private static final String strNonCorporatePolicy="Non-Corporate Policy";
	 private static final String strNonCorpPolicyType="NC";
	 private static final String strEnrollment="ENM";
	    private static final String strEndorsement="END";
	
	public ActionForward doDefault(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside the doDefault method of BrokerDetailsAction");
			((DynaActionForm) form).initialize(mapping);// reset the form data
			setLinks(request);
			String strTable = "";
			DynaActionForm frmBrokerDetails = (DynaActionForm) form;
			ArrayList alPolicyDocs = new ArrayList();
			TableData tableData = TTKCommon.getTableData(request);
			// get the table data from session if exists
			if (tableData == null) {
				// create new table data object
				tableData = new TableData();
			}// end of if(tableData==null) // create the required grid table
			strTable = "BrokerDetailsTable";
			tableData.createTableInfo(strTable, null);
			tableData.setData(alPolicyDocs, "search");
			request.getSession().setAttribute("tableData", tableData);
			request.getSession().setAttribute("frmBrokerDetails", frmBrokerDetails);
			return mapping.findForward(strBrokerDetails);// this.getForward("brokerdetails" // mapping, // request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "endorsementdetails"));
		}// end of catch(Exception exp)
	}// end of doDefault(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward doSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside the doSearch method of BrokerDetailsAction");
			setLinks(request);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmBrokerDetails = (DynaActionForm) form;
			BrokerManager brokerObject = (BrokerManager) this.getBrokerManagerObject();
			// if the page number or sort id is clicked
			if (!strPageID.equals("") || !strSortID.equals("")) {
				if (!strPageID.equals("")) {
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strBrokerDetails);
				}// /end of if(!strPageID.equals(""))
				else {
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");// modify the search data
				}// end of else
			}// end of if(!strPageID.equals("") || !strSortID.equals(""))
			else {
				// create the required grid table
				tableData.createTableInfo("BrokerDetailsTable", null);
				tableData.setSearchData(this.populateSearchCriteria(
						frmBrokerDetails, request));
				tableData.modifySearchData("search");
			}// end of else
			ArrayList alCompanies = brokerObject.getBrokerDetails(tableData
					.getSearchData());
			tableData.setData(alCompanies, "search");
			// set the table data object to session
			request.getSession().setAttribute("tableData", tableData);
			// finally return to the grid screenstrBrokerDetails
			return mapping.findForward(strBrokerDetails);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "endorsementdetails"));
		}// end of catch(Exception exp)
	}// end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)
	public ActionForward doViewBrokers(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			log.info("Inside the doViewBrokers method of BrokerDetailsAction");
			setLinks(request);
			String strTable = "";
			Long brokerseqid=null;
			DynaActionForm frmBrokerDetails = (DynaActionForm) form;
			ArrayList alPolicyDocs = new ArrayList();
			BrokerManager brokerObject = (BrokerManager) this.getBrokerManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			// get the table data from session if exists
			BrokerVO brokerVO = null;
			if (tableData != null) {
				// create new table data object

				
				if (!(TTKCommon.checkNull(request.getParameter("rownum")).equals(""))) {
					brokerVO = (BrokerVO) tableData.getRowInfo(Integer.parseInt((String) (frmBrokerDetails).get("rownum")));
					// add the selected item to the web board and make it as
					// default selected
					frmBrokerDetails.set("brokerseqid", brokerVO.getInsuranceSeqID().toString());
					brokerseqid = (Long)brokerVO.getInsuranceSeqID();
				}// end if()
				frmBrokerDetails.set("companyName", brokerVO.getCompanyName().toString()); 
			}// end of if(tableData==null)
			else {
				
				tableData = new TableData();
				brokerseqid = (Long)brokerVO.getInsuranceSeqID();
			}
			// create the required grid table
			
			Long policySeqID = (Long)request.getSession().getAttribute("policySeqID");
			
			int count = brokerObject.getAssoDissoCount(policySeqID,brokerseqid);
			
			strTable = "InsuranceContactTable";
			tableData.createTableInfo(strTable, null);
			tableData.setData(alPolicyDocs, "search");
			request.getSession().setAttribute("tableData", tableData);
			request.getSession().setAttribute("frmBrokerDetails",frmBrokerDetails);

			return mapping.findForward("brokeruserdetails");
			// this.getForward("brokeruserdetails",// mapping// request);
		}// end of try
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "endorsementdetails"));
		}// end of catch(Exception exp)

	}// end of public ActionForward doViewBrokers(ActionMapping
		// mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

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
	public ActionForward doBackward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside the doBackward method of BrokerDetailsAction");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			BrokerManager brokerObject = (BrokerManager) this
					.getBrokerManagerObject();
			tableData.modifySearchData(strBackward);// modify the search data
			ArrayList alCompanies = brokerObject.getBrokerDetails(tableData.getSearchData());
			tableData.setData(alCompanies, strBackward);// set the table data
			request.getSession().setAttribute("tableData", tableData);
			// set the// table// data// object// to// session
			return mapping.findForward(strBrokerDetails);
			// finally return to // the grid screen
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "endorsementdetails"));
		}// end of catch(Exception exp)
	}// end of doBackward(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward doForward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			log.debug("Inside the doForward method of BrokerDetailsAction");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			BrokerManager brokerObject = (BrokerManager) this
					.getBrokerManagerObject();
			tableData.modifySearchData(strForward);// modify the search data
			ArrayList alCompanies = brokerObject.getBrokerDetails(tableData
					.getSearchData());
			tableData.setData(alCompanies, strForward);// set the table data
			request.getSession().setAttribute("tableData", tableData);// set the
																		// table
																		// data
																		// object
																		// to// session
			return mapping.findForward(strBrokerDetails);// finally return to
															// the grid screen
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "endorsementdetails"));
		}// end of catch(Exception exp)
	}// end of doForward(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward doClose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside the doClose method of BrokerDetailsAction");
			// setLinks(request);
			TTKCommon.getActiveLink(request);
			((DynaActionForm) form).initialize(mapping);// reset the form data
			 DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			 ArrayList<Object> alPolicy = new ArrayList<Object>();
			 if(strSwitchType.equals(strEnrollment))
	            {
	                alPolicy.add(WebBoardHelper.getPolicySeqId(request));
	                
	            }//end of if(strSwitchType.equals("ENM"))
	            else
	            {
	                alPolicy.add(WebBoardHelper.getEndorsementSeqId(request));
	                request.getSession().setAttribute("endorsementSeqId", WebBoardHelper.getEndorsementSeqId(request));
	            }//end of else if(strSwitchType.equals("ENM"))
			alPolicy.add(strSwitchType);//Enrollment or Endorsement
            alPolicy.add(getPolicyType(strActiveSubLink));
			
            
			  ArrayList arAssociatedCorp = new ArrayList();
	            BrokerManager brokerObject = (BrokerManager) this.getBrokerManagerObject();	
	            //TableData tableDataBro=request.getSession().getAttribute("tableDataBro") != null ?(TableData)request.getSession().getAttribute("tableDataBro"): new TableData();
	            TableData tableDataBro=null;
	            if(request.getSession().getAttribute("tableDataBro") != null)
	            	tableDataBro = (TableData)request.getSession().getAttribute("tableDataBro");
	            else
	            	tableDataBro = new TableData();
				tableDataBro.createTableInfo("BrokerDetailsListTable",new ArrayList<>());
				tableDataBro.setSearchData(alPolicy);
				tableDataBro.modifySearchData("search");
				arAssociatedCorp =  brokerObject.getBrokerDetailsAssocGrpList(tableDataBro.getSearchData());
			request.getSession().setAttribute("brokerCount", arAssociatedCorp.size());
			tableDataBro.setData(arAssociatedCorp, "search");	
			request.getSession().setAttribute("tableDataBro", tableDataBro);
			
			return doDefault(mapping, form, request, response);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "endorsementdetails"));
		}// end of catch(Exception exp)
	}// end

	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmBrokerDetails, HttpServletRequest request)throws TTKException {
		// build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(new SearchCriteria("INS_COMP_CODE_NUMBER", TTKCommon	.replaceSingleQots((String) frmBrokerDetails.get("sCompanyCode"))));
		alSearchParams.add(new SearchCriteria("INS_COMP_NAME", TTKCommon.replaceSingleQots((String) frmBrokerDetails.get("sCompanyName"))));
		alSearchParams.add(new SearchCriteria("A.BROKER_AUTHORITY", TTKCommon.replaceSingleQots((String) frmBrokerDetails.get("regAuthority"))));
		alSearchParams.add(new SearchCriteria("B.OFFICE_NAME", TTKCommon.replaceSingleQots((String) frmBrokerDetails.get("officeInfo"))));
		return alSearchParams;
	}// end of populateSearchCriteria(DynaActionForm userForm)
	public ActionForward doBrokerUserSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside the doBrokerUserSearch method of BrokerDetailsAction");
			setLinks(request);
			BrokerVO brokerVO = null;
			Long lngInsuranceSeqID = null;
			String strSubLink = TTKCommon.getActiveSubLink(request);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request .getParameter("sortId"));
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmBrokerDetails = (DynaActionForm) form;
			Long policySeqID = (Long)request.getSession().getAttribute("policySeqID");
		
			BrokerManager brokerObject = (BrokerManager) this .getBrokerManagerObject();
			if (strSubLink.equals("Broker")) {
				// get the insurance sequence id from session
				brokerVO = (BrokerVO) request.getSession().getAttribute("SelectedOffice");
				if (brokerVO != null) {
				
					lngInsuranceSeqID = brokerVO.getInsuranceSeqID();
				}// end of if(insuranceVO != null)
			}// end of if (strSubLink=="Insurance")
				// if the page number or sort id is clicked
			if (!strPageID.equals("") || !strSortID.equals("")) {
				if (!strPageID.equals("")) {
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strBrokeruserdetails);
				}// /end of if(!strPageID.equals(""))
				else {
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");// modify the search data
				}// end of else
			}// end of if(!strPageID.equals("") || !strSortID.equals(""))
			else {
				// create the required grid table
				tableData.createTableInfo("InsuranceContactTable", null);
				tableData.setSearchData(this.populateSearchCriteriaUser(frmBrokerDetails, request));
				tableData.modifySearchData("search");
			}// end of else
			ArrayList alCompanies = brokerObject.getBrokerDetailsList(tableData.getSearchData(),policySeqID);
			tableData.setData(alCompanies, "search");
			// set the table data object to session
			request.getSession().setAttribute("tableData", tableData);
			// finally return to the grid screenstrBrokerDetails
			return mapping.findForward(strBrokeruserdetails);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "endorsementdetails"));
		}// end of catch(Exception exp)

	}// end of public ActionForward doViewBrokers(ActionMapping
		// mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	private ArrayList<Object> populateSearchCriteriaUser(DynaActionForm frmBrokerDetails, HttpServletRequest request) throws TTKException {
		String strSubLink = TTKCommon.getActiveSubLink(request);
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(new SearchCriteria("INS_SEQ_ID", ""+ frmBrokerDetails.get("brokerseqid"), "equals"));
		alSearchParams.add(new SearchCriteria("CONTACT_NAME",(String) frmBrokerDetails.get("name"), "equals"));
		alSearchParams.add(new SearchCriteria("DESIGNATION_TYPE_ID",(String) frmBrokerDetails.get("designationBRO"), "equals"));
		alSearchParams.add(new SearchCriteria("ROLE_SEQ_ID",(String) frmBrokerDetails.get("userRoleBRO"), "equals"));
		alSearchParams.add(new SearchCriteria("USER_ID",(String) frmBrokerDetails.get("userID"), "equals"));
		
		 if(frmBrokerDetails.get("userStatus").equals("ACT"))
	        {
	        	if("Corporate Policy".equals(strSubLink)){
	        		alSearchParams.add(new SearchCriteria("ACTIVE_YN","Active","equals"));
	        	}else{
	            alSearchParams.add(new SearchCriteria("ACTIVE_YN","Active","equals"));
	        	}
	        }//end of if(searchContactForm.get("userStatus").equals("ACT"))        
	        else if(frmBrokerDetails.get("userStatus").equals("INA"))
	        {
	        	if("Corporate Policy".equals(strSubLink)){
	        		alSearchParams.add(new SearchCriteria("ACTIVE_YN","Inactive","equals"));
	        	}else{
	            alSearchParams.add(new SearchCriteria("ACTIVE_YN","Inactive","equals"));
	        	}
	        }//end of else
	    
		
		 alSearchParams.add(new SearchCriteria("USER_ASSO_DISASSO",(String) frmBrokerDetails.get("userAssoDisAsso"), "equals"));		///  new added
		 int a = alSearchParams.size();
		return alSearchParams;
	}// end of populateSearchCriteria(DynaActionForm
		// frmBrokerDetails,HttpServletRequest request, Long lHospitalSeqId,
		// Long lInsuranceSeqID) throws TTKException
	
	/**
	 * Returns the ContactManager session object for invoking methods on it.
	 * 
	 * @return ContactManager session object which can be used for method
	 *         invokation
	 * @exception throws TTKException
	 */
	private BrokerManager getBrokerManagerObject() throws TTKException {
		BrokerManager bromanager = null;
		try {
			if (bromanager == null) {
				InitialContext ctx = new InitialContext();
				bromanager = (BrokerManager) ctx .lookup("java:global/TTKServices/business.ejb3/BrokerManagerBean!com.ttk.business.empanelment.BrokerManager");
			}// end if
		}// end of try
		catch (Exception exp) {
			// throw new TTKException(exp, "strBroError");
			throw new TTKException(exp, "endorsementdetails");

		}// end of catch
		return bromanager;
	}

	private String populateAddId(HttpServletRequest request, TableData tableData) {
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfBrokercpdeId = new StringBuffer();
		if (strChk != null && strChk.length != 0) {
			// loop through to populate Add sequence id's and get the value
			// from session for the matching check box value
			for (int i = 0; i < strChk.length; i++) {
				if (strChk[i] != null) {
					// extract the sequence id to be Added from the value
					// object
					if (i == 0) {
						sbfBrokercpdeId.append(String.valueOf(((BrokerVO) tableData.getData().get(Integer.parseInt(strChk[i]))).getInsuranceSeqID()));
						
					}// end of if(i == 0)
					else {
						sbfBrokercpdeId.append("|").append(String.valueOf(((BrokerVO) tableData.getData().get(Integer.parseInt(strChk[i]))).getInsuranceSeqID()));
					}// end of else
				}// end of if(strChk[i]!=null)
			}// end of for(int i=0; i<strChk.length;i++)
			sbfBrokercpdeId.append("|");
		}// end of if(strChk!=null&&strChk.length!=0)
		return sbfBrokercpdeId.toString();
	}
	public ActionForward doViewAssociatedCorp(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		log.debug("Inside BrokerDetailsAction doViewAssociatedCorp");
		try {
			
			setLinks(request);
			BrokerVO brokerVO = null;
			DynaActionForm frmBrokerDetails = (DynaActionForm) form;
			TableData tableDataBro = TTKCommon.getTableData(request);
			StringBuffer sbfBrokercpdeId = new StringBuffer("|");
			int iCount = 0;
			ArrayList arrayList = new ArrayList();
			// populate the add string which contains the sequence id's to be Added
			sbfBrokercpdeId.append(populateAddId(request, (TableData) request.getSession().getAttribute("tableData")));
			arrayList.add(request.getSession().getAttribute("policySeqID"));
			arrayList.add(sbfBrokercpdeId.toString());
			arrayList.add(TTKCommon.getUserSeqId(request));
			
			arrayList.add(request.getSession().getAttribute("strSwitchType"));
			arrayList.add(request.getSession().getAttribute("endorsementSeqId"));
			ArrayList<Object> arAssociatedCorp = new ArrayList<Object>();
			BrokerManager brokerObject = (BrokerManager) this.getBrokerManagerObject();
			
			//Save the broker Details
			brokerObject.saveBrokerDeatils(arrayList);
			tableDataBro = new TableData();
			arrayList.set(0,request.getSession().getAttribute("strSwitchType").equals("ENM") ? request.getSession().getAttribute("policySeqID") : request.getSession().getAttribute("endorsementSeqId"));
			arrayList.set(1, request.getSession().getAttribute("strSwitchType"));
			tableDataBro.createTableInfo("BrokerDetailsListTable",new ArrayList<>());
			tableDataBro.setSearchData(arrayList);
			tableDataBro.modifySearchData("search");
			arAssociatedCorp = brokerObject.getBrokerDetailsAssocGrpList(tableDataBro.getSearchData());
			request.getSession().setAttribute("brokerCount", arAssociatedCorp.size());
			tableDataBro.setData(arAssociatedCorp, "search");
			request.getSession().setAttribute("tableDataBro", tableDataBro);
			request.setAttribute("Successmsg", "error.broker.record.Added");
			return mapping.findForward(strcorppolicydetails);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "endorsementdetails"));
		}// end of catch(Exception exp)
	}// end of doViewAssociatedCorp(ActionMapping mapping,ActionForm form,
		// HttpServletRequest request,HttpServletResponse response) throws
		// Exception

	public ActionForward doCloseBroker(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Inside BrokerDetailsAction doViewAssociatedCorp");
		try {
			setLinks(request);
			this.doSearch(mapping, form, request, response);
			
			TTKCommon.getActiveLink(request);
			((DynaActionForm) form).initialize(mapping);// reset the form data
			 DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			 ArrayList<Object> alPolicy = new ArrayList<Object>();
			 if(strSwitchType.equals(strEnrollment))
	            {
	                alPolicy.add(WebBoardHelper.getPolicySeqId(request));
	                
	            }//end of if(strSwitchType.equals("ENM"))
	            else
	            {
	                alPolicy.add(WebBoardHelper.getEndorsementSeqId(request));
	                request.getSession().setAttribute("endorsementSeqId", WebBoardHelper.getEndorsementSeqId(request));
	            }//end of else if(strSwitchType.equals("ENM"))
			alPolicy.add(strSwitchType);//Enrollment or Endorsement
            alPolicy.add(getPolicyType(strActiveSubLink));
            
			  ArrayList arAssociatedCorp = new ArrayList();
	            BrokerManager brokerObject = (BrokerManager) this.getBrokerManagerObject();	
	            //TableData tableDataBro=request.getSession().getAttribute("tableDataBro") != null ?(TableData)request.getSession().getAttribute("tableDataBro"): new TableData();
	            TableData tableDataBro=null;
	            if(request.getSession().getAttribute("tableDataBro") != null)
	            	tableDataBro = (TableData)request.getSession().getAttribute("tableDataBro");
	            else
	            	tableDataBro = new TableData();
				tableDataBro.createTableInfo("BrokerDetailsListTable",new ArrayList<>());
				tableDataBro.setSearchData(alPolicy);
				tableDataBro.modifySearchData("search");
				arAssociatedCorp =  brokerObject.getBrokerDetailsAssocGrpList(tableDataBro.getSearchData());
			
			request.getSession().setAttribute("brokerCount", arAssociatedCorp.size());
			tableDataBro.setData(arAssociatedCorp, "search");	
			request.getSession().setAttribute("tableDataBro", tableDataBro);
			
			
			
			
			
			
			
			
			
			
			
			
			
			return mapping.findForward(strcorppolicydetails);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "endorsementdetails"));
		}// end of catch(Exception exp)
	}// end of doCloseBroker(ActionMapping mapping,ActionForm form,
		// HttpServletRequest request,HttpServletResponse response) throws
		// Exception
	public ActionForward doDeleteBrokerDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		log.debug("Inside BrokerDetailsAction deleteBrokerDetails");
		  
		try {
			//setLinks(request);
			BrokerVO brokerVO = null;
			//ArrayList<Object> arAssociatedCorp = new ArrayList<Object>();
			TableData tableDataBro = (TableData)request.getSession().getAttribute("tableDataBro");
			DynaActionForm frmBrokerDetails = (DynaActionForm) form;
			BrokerManager brokerObject = (BrokerManager) this.getBrokerManagerObject();
			if (!(TTKCommon.checkNull(request.getParameter("rownum")).equals(""))) {
				brokerVO = (BrokerVO) tableDataBro.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
			}// end of // if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			//brokerObject.deleteFloatAssocGrp(brokerVO.getBrokerseqid());
			Long policySeqID = (Long)request.getSession().getAttribute("policySeqID");
			String SwitchType = (String)request.getSession().getAttribute("strSwitchType");
			Long endorsementSeqId=(Long)request.getSession().getAttribute("endorsementSeqId");
			String Brokerseqid = brokerVO.getBrokerseqid();
			int count = brokerObject.deleteFloatAssocGrp(Brokerseqid,policySeqID,endorsementSeqId,SwitchType);
			request.setAttribute("Delmsg", "error.broker.record.deleted");
			StringBuffer sbfBrokercpdeId = new StringBuffer("|");
			int iCount = 0;
			ArrayList arrayList = new ArrayList();
			// populate the add string which contains the sequence id's to be Added
			sbfBrokercpdeId.append(populateAddId(request, (TableData) request.getSession().getAttribute("tableData")));
			
			arrayList.add(request.getSession().getAttribute("policySeqID"));
			arrayList.add(sbfBrokercpdeId.toString());
			arrayList.add(TTKCommon.getUserSeqId(request));
			ArrayList<Object> arAssociatedCorp = new ArrayList<Object>();
			tableDataBro = new TableData();
			arrayList.set(0,request.getSession().getAttribute("strSwitchType").equals("ENM") ? request.getSession().getAttribute("policySeqID") : request.getSession().getAttribute("endorsementSeqId"));
			arrayList.set(1, request.getSession().getAttribute("strSwitchType"));
			tableDataBro.createTableInfo("BrokerDetailsListTable",new ArrayList<>());
			tableDataBro.setSearchData(arrayList);
			tableDataBro.modifySearchData("search");
			arAssociatedCorp = brokerObject.getBrokerDetailsAssocGrpList(tableDataBro.getSearchData());
			request.getSession().setAttribute("brokerCount", String.valueOf(arAssociatedCorp.size()));
			tableDataBro.setData(arAssociatedCorp, "search");
			request.getSession().setAttribute("tableDataBro", tableDataBro);
			return mapping.findForward(strcorppolicydetails);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(exp, "endorsementdetails"));
		}// end of catch(Exception exp)

	}// end of doDeleteAssociatedCorp(ActionMapping mapping,ActionForm form,
		private int Long(Object attribute) {
		// TODO Auto-generated method stub
		return 0;
	}

		private String String(Object attribute) {
			// TODO Auto-generated method stub
			return null;
		}
		// HttpServletRequest request,HttpServletResponse response) throws // Exception
	public ActionForward doAssosicateBrokersUser(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		    log.debug("Inside doAssosicateBrokersUser doSaveBrokers");
			try {/*getBrokerDetailsAssocGrpList*/
				setLinks(request);
				ContactVO contactVO = null;
				DynaActionForm frmBrokerDetails = (DynaActionForm) form;
				TableData tableData = TTKCommon.getTableData(request);
				StringBuffer ContactSeqId = new StringBuffer("|");
				int iCount = 0;
				ArrayList arrayList = new ArrayList();
				// populate the add string which contains the sequence id's to be Added
				ContactSeqId.append(populateSaveId(request, (TableData) request.getSession().getAttribute("tableData")));
				String brokerseqid = populateBrtokerSeqId(request, (TableData) request.getSession().getAttribute("tableData"));
				String SwitchType = (String)request.getSession().getAttribute("strSwitchType");
				Long endorsementSeqId=(Long)request.getSession().getAttribute("endorsementSeqId");
				arrayList.add(ContactSeqId.toString());
				arrayList.add(request.getSession().getAttribute("policySeqID"));
				arrayList.add(brokerseqid);
				arrayList.add(TTKCommon.getUserSeqId(request));
				arrayList.add(SwitchType);
			//	arrayList.add(endorsementSeqId);
				ArrayList<Object> arAssociatedCorp = new ArrayList<Object>();
				BrokerManager brokerObject = (BrokerManager) this.getBrokerManagerObject();
				tableData = new TableData();
				//arrayList.set(0,request.getSession().getAttribute("strSwitchType").equals("ENM") ? request.getSession().getAttribute("policySeqID") : request.getSession().getAttribute("endorsementSeqId"));
				//arrayList.set(1, request.getSession().getAttribute("strSwitchType"));
				tableData.createTableInfo("InsuranceContactTable",new ArrayList<>());
				tableData.setSearchData(arrayList);
				tableData.modifySearchData("search");
				 brokerObject.saveAssociatedBrokersUser(arrayList,endorsementSeqId);
				tableData.setData(arAssociatedCorp, "search");
				request.getSession().setAttribute("tableData", tableData);
				request.setAttribute("successMsg","Records Associated SuccessFully." );

				return doBrokerUserSearch(mapping, form, request, response);//mapping.findForward(strBrokeruserdetails);
			}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException( exp, "endorsementdetails"));
		}// end of catch(Exception exp)

	}// end of doSaveBrokers(ActionMapping mapping,ActionForm form,
	// HttpServletRequest request,HttpServletResponse response) throws // Exception
	
	private String populateSaveId(HttpServletRequest request, TableData tableData)
	{
	    String[] strChk = request.getParameterValues("chkopt");
	    StringBuffer ContactSeqId = new StringBuffer();
	    if(strChk!=null&&strChk.length!=0)
	    {
	        //loop through to populate save sequence id's and get the value from session for the matching check box value
	        for(int i=0; i<strChk.length;i++)
	        {
	           if(strChk[i]!=null)
	            {
	                //extract the sequence id to be save from the value object
	        	   if (i == 0) {
	        		   ContactSeqId.append(String.valueOf(((ContactVO) tableData.getData().get(Integer.parseInt(strChk[i]))).getContactSeqId()));
					}// end of if(i == 0)
					else {
						ContactSeqId.append("|").append(String.valueOf(((ContactVO) tableData.getData().get(Integer.parseInt(strChk[i]))).getContactSeqId()));

					}// end of else
	            }//end of if(strChk[i]!=null)
	        }//end of for(int i=0; i<strChk.length;i++)
	        ContactSeqId.append("|");
	    }//end of if(strChk!=null&&strChk.length!=0)
	    return ContactSeqId.toString();
	}//end of FloatAccountDeleteId(HttpServletRequest request, TableData tableData)
	
	private String populateBrtokerSeqId(HttpServletRequest request, TableData tableData)
	{
		
	    String[] strChk = request.getParameterValues("chkopt");
	    String Brokerseqid = new String();
	    if(strChk!=null&&strChk.length!=0)
	    {
	        //loop through to populate save sequence id's and get the value from session for the matching check box value
	        for(int i=0; i<strChk.length;i++)
	        {
	           if(strChk[i]!=null)
	            {
	                //extract the sequence id to be save from the value object
	        	   if (i == 0) {
	        		   Brokerseqid =(((ContactVO) tableData.getData().get(Integer.parseInt(strChk[i]))).getBrokerseqid());
					}// end of if(i == 0)
					else {
						Brokerseqid =(((ContactVO) tableData.getData().get(Integer.parseInt(strChk[i]))).getBrokerseqid());

					}// end of else
	            }//end of if(strChk[i]!=null)
	        }//end of for(int i=0; i<strChk.length;i++)   
	    }//end of if(strChk!=null&&strChk.length!=0)
	    return Brokerseqid;
	}
	
	public ActionForward doViewBrokersCountact(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.info("Inside the doViewBrokers method of BrokerDetailsAction");
			setLinks(request);
			String strTable = "";
			Long brokerseqid=null;
			DynaActionForm frmBrokerDetails = (DynaActionForm) form;
			BrokerManager brokerObject = (BrokerManager) this.getBrokerManagerObject();
			ArrayList alPolicyDocs = new ArrayList();
			BrokerVO brokerVO = null;
			TableData tableDataBro = (TableData)request.getSession().getAttribute("tableDataBro");
			if (tableDataBro != null) {
				
				if (!(TTKCommon.checkNull(request.getParameter("rownum")).equals(""))) {
					brokerVO = (BrokerVO) tableDataBro.getRowInfo(Integer.parseInt((String) (frmBrokerDetails).get("rownum")));
					String UserAssociaedStatusYN = brokerVO.getUserAssociaedStatus(); 
					if(UserAssociaedStatusYN.equals("YES"))
					{
						frmBrokerDetails.set("userAssoDisAsso", "ASSOCIATED");
					}
					else if(UserAssociaedStatusYN.equals("NO"))
					{
						frmBrokerDetails.set("userAssoDisAsso", "UNASSOCIATED");
					}
				
					if(brokerVO.getInsuranceSeqID()!=null){
						frmBrokerDetails.set("brokerseqid", brokerVO.getInsuranceSeqID().toString());
						brokerseqid = (Long)brokerVO.getInsuranceSeqID();
					}
					 if(brokerVO.getBrokerseqid()!=null&&brokerVO.getBrokerseqid()!=""){
						frmBrokerDetails.set("brokerseqid", brokerVO.getBrokerseqid().toString());
						brokerseqid = (Long)brokerVO.getInsuranceSeqID();
					}
					frmBrokerDetails.set("companyName", brokerVO.getCompanyName().toString()); 
					 
				}
			}
			else {
				tableDataBro = new TableData();
				brokerseqid = (Long)brokerVO.getInsuranceSeqID();
			}
			// create the required grid table
			Long policySeqID = (Long)request.getSession().getAttribute("policySeqID");
			int count = brokerObject.getAssoDissoCount(policySeqID,brokerseqid);
			TableData tableData = new TableData();
			strTable = "InsuranceContactTable";
			tableData.createTableInfo(strTable, null);
			tableData.setData(alPolicyDocs, "search");
			request.getSession().setAttribute("tableData", tableData);
			request.getSession().setAttribute("frmBrokerDetails",frmBrokerDetails);
			return mapping.findForward(strBrokeruserdetails);
			
		}// end of try
	
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "endorsementdetails"));
		}// end of catch(Exception exp)
	}
	

	public ActionForward doViewAdminUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.info("Inside the doViewAdminUser method of BrokerDetailsAction");
			setLinks(request);
			BrokerVO brokerVO = null;
			String strTable = "";
			DynaActionForm frmBrokerDetails = (DynaActionForm) form;
		//	Long policySeqID = (Long)request.getSession().getAttribute("policySeqID");
		//	System.out.println("doViewAdminUser(): brokerdetailAction : policySeqID====="+ policySeqID);
			String policySeqqID = request.getParameter("policySeqID");
			Long policySeqID = Long.parseLong(policySeqqID);
			ArrayList alPolicyDocs = new ArrayList();
			TableData tableDataBro = (TableData)request.getSession().getAttribute("tableDataBro");
			if (tableDataBro != null) {
				
				if (!(TTKCommon.checkNull(request.getParameter("rownum")).equals(""))) {
					brokerVO = (BrokerVO) tableDataBro.getRowInfo(Integer.parseInt((String) (frmBrokerDetails).get("rownum")));
				} 
				frmBrokerDetails.set("companyName", brokerVO.getCompanyName().toString());
			}
			else {
				tableDataBro = new TableData();
			}
			// create the required grid table
			TableData tableData = null;
			//strTable = "InsuranceContactTable";
			//tableData.createTableInfo(strTable, null);
			//tableData.setData(alPolicyDocs, "search");
			//request.getSession().setAttribute("tableData", tableData);
			//Long lngInsuranceSeqID;
			String strSubLink = TTKCommon.getActiveSubLink(request);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request .getParameter("sortId"));
			 tableData = TTKCommon.getTableData(request);
			//DynaActionForm frmBrokerDetails = (DynaActionForm) form;
			BrokerManager brokerObject = (BrokerManager) this .getBrokerManagerObject();
			//if (strSubLink.equals("Broker")) {
				// get the insurance sequence id from session
				//BrokerVO brokerVO = (BrokerVO) request.getSession().getAttribute("SelectedOffice");
				//if (brokerVO != null) {
				
					//lngInsuranceSeqID = brokerVO.getInsuranceSeqID();
				//}// end of if(insuranceVO != null)
			//}// end of if (strSubLink=="Insurance")
				// if the page number or sort id is clicked
			if (!strPageID.equals("") || !strSortID.equals("")) {
				if (!strPageID.equals("")) {
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strBrokeruserdetails);
				}// /end of if(!strPageID.equals(""))
				else {
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");// modify the search data
				}// end of else
			}// end of if(!strPageID.equals("") || !strSortID.equals(""))
			else {
				// create the required grid table
				
				
				frmBrokerDetails.set("brokerseqid",brokerVO.getBrokerseqid());
				frmBrokerDetails.set("userStatus","ACT");
				tableData.createTableInfo("BrokerUserAssociatedTable", null);
				tableData.setSearchData(this.populateSearchCriteriaUser(frmBrokerDetails, request));
				tableData.modifySearchData("search");
			}// end of else
			ArrayList alCompanies = brokerObject.getAssociatedBrokerDetailsList(tableData.getSearchData(),policySeqID );
			tableData.setData(alCompanies, "search");
			// set the table data object to session
			request.getSession().setAttribute("tableData", tableData);
			request.setAttribute("display","true");
			
			return mapping.findForward(strBrokeruserdetails);
		}// end of try
	
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "endorsementdetails"));
		}// end of catch(Exception exp)
	}
	
	public ActionForward doDisAssosicateBrokersUser(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    log.debug("Inside doDisAssosicateBrokersUser doSaveBrokers");
		try {
			setLinks(request);
			ContactVO contactVO = null;
			DynaActionForm frmBrokerDetails = (DynaActionForm) form;
			TableData tableData = TTKCommon.getTableData(request);
			StringBuffer ContactSeqId = new StringBuffer("|");
			int iCount = 0;
			ArrayList arrayList = new ArrayList();
			ContactSeqId.append(populateSaveId(request, (TableData) request.getSession().getAttribute("tableData")));
			String brokerseqid = populateBrtokerSeqId(request, (TableData) request.getSession().getAttribute("tableData"));
			String SwitchType = (String)request.getSession().getAttribute("strSwitchType");
			Long endorsementSeqId=(Long)request.getSession().getAttribute("endorsementSeqId");
			arrayList.add(ContactSeqId.toString());
			arrayList.add(request.getSession().getAttribute("policySeqID"));
			arrayList.add(brokerseqid);
			arrayList.add(TTKCommon.getUserSeqId(request));
			arrayList.add(SwitchType);
			
			ArrayList<Object> arAssociatedCorp = new ArrayList<Object>();
			BrokerManager brokerObject = (BrokerManager) this.getBrokerManagerObject();
			tableData = new TableData();
			tableData.createTableInfo("InsuranceContactTable",new ArrayList<>());
			tableData.setSearchData(arrayList);
			tableData.modifySearchData("search");
			 brokerObject.disAssociateBrokersUser(arrayList,endorsementSeqId);
			tableData.setData(arAssociatedCorp, "search");
			request.getSession().setAttribute("tableData", tableData);
			request.setAttribute("successMsg","Records DisAssociated SuccessFully." );
			
			
			
			
			return doBrokerUserSearch(mapping, form, request, response);//mapping.findForward(strBrokeruserdetails);
		}// end of try
	catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
	}// end of catch(TTKException expTTK)
	catch (Exception exp) {
		return this.processExceptions(request, mapping, new TTKException( exp, "endorsementdetails"));
	}// end of catch(Exception exp)

}// end of doSaveBrokers(ActionMapping mapping,ActionForm form,
// HttpServletRequest request,HttpServletResponse response) throws // Exception	
	
	public ActionForward getBrokerListStatus(ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String strData="0";
		
		try {
			PrintWriter printWriter=response.getWriter();
			BrokerManager brokerObject = (BrokerManager) this.getBrokerManagerObject();
			 strData=brokerObject.getBrokerAssocCount(new Long (request.getSession().getAttribute("policySeqID").toString()));
			 printWriter.write(strData);
			 printWriter.flush();
			 printWriter.close();
	}// end of catch(TTKException expTTK)
	catch (Exception exp) {
		exp.printStackTrace();
		
	}// end of catch(Exception exp)
		
return null;
}// end of doSaveBrokers(ActionMapping mapping,ActionForm form,
// HttpServletRequest request,HttpServletResponse response) throws // Exception	
	
	
	private String getPolicyType(String strActiveSubLink)
    {
        String strPolicyType=null;

        if(strActiveSubLink.equals(strIndPolicy))
        {
            strPolicyType=strIndPolicyType;
        }//end of if(strActiveSubLink.equals(strIndPolicy))
        else if (strActiveSubLink.equals(strIndGrpPolicy))
        {
            strPolicyType=strGrpPolicyType;
        }//end of else if (strActiveSubLink.equals(strIndGrpPolicy))
        else if (strActiveSubLink.equals(strCorporatePolicy))
        {
            strPolicyType=strCorpPolicyType;
        }//end of else if (strActiveSubLink.equals(strCorporatePolicy))
        else if (strActiveSubLink.equals(strNonCorporatePolicy))
        {
            strPolicyType=strNonCorpPolicyType;
        }//end of else if (strActiveSubLink.equals(strNonCorporatePolicy))
        return strPolicyType;
    }
	
	
	
	
	
	
	
}// end of BrokerDetailsAction

