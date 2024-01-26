package com.ttk.action.finance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.business.finance.BankAccountManager;
import com.ttk.business.finance.CustomerBankDetailsManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.empanelment.AccountDetailVO;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.HospitalAuditVO;
import com.ttk.dto.finance.BankAccountDetailVO;
import com.ttk.dto.finance.CustomerBankDetailsVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is added for cr koc 1103 added eft
 */
public class CustomerBankAcctGeneralAction extends TTKAction {
	private static Logger log = Logger.getLogger(CustomerBankAcctGeneralAction.class);
	// Exception Message Identifier
	private static final String strBankAcctDetails = "BankAcctDetails";
	// declare forward paths
	private static final String strBankacctdetail = "bankacctdetail";
	
	private static final String strReferencedetail = "referencedetail";
	private static final String strAccountsError = "accounts";
    private static final String strBankHospAccountReviewDetail="hospReviewDetails";    

	
	public ActionForward doViewPolicyBankAccount(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			
			//setLinks(request);
			log.debug("Inside BankAccountGeneral doViewBankAccount");
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			StringBuffer strCaption = new StringBuffer();
			String strEditMode = "";
			CustomerBankDetailsVO customerBankDetailsVO = null;
			customerBankDetailsVO = (CustomerBankDetailsVO) FormUtils.getFormValues(frmCustomerBankAcctGeneral, this, mapping, request);
			HttpSession session=request.getSession();
			String strSwitchType="";
			DynaActionForm generalForm1=(DynaActionForm)session.getAttribute("frmCustomerBankDetailsSearch");
			strSwitchType=TTKCommon.checkNull((String)generalForm1.get("switchType"));
			strSwitchType=strSwitchType.concat("Polc");
			this.setLinks(request,strSwitchType);
			
			CustomerBankDetailsManager customerBankDetailsManager = this.getCustomerBankDetailsManagerObject();
			frmCustomerBankAcctGeneral.initialize(mapping);
			if (TTKCommon.getWebBoardId(request) == null) {
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.policyaccountno.required"); 
				throw expTTK;
			}// end of if(TTKCommon.getWebBoardId(request) == null)
			strCaption.append("- Edit");
			strEditMode = "Edit";

			customerBankDetailsVO = customerBankDetailsManager.getPolicyBankAccountDetail(TTKCommon.getWebBoardId(request), TTKCommon.getUserSeqId(request));
			// TTKCommon.getWebBoardId(request),

			frmCustomerBankAcctGeneral = (DynaActionForm) FormUtils.setFormValues("frmCustomerBankAcctGeneral", customerBankDetailsVO, this,mapping, request);
			setFormValues(customerBankDetailsVO, mapping, request);
			

			//TTKCommon.deleteWebBoardId(request);

			HashMap hmCityList = null;
			ArrayList alCityList = null;
			ArrayList alCityCode = null;

			hmCityList = customerBankDetailsManager.getbankStateInfo();
			if (hmCityList != null) {
				alCityList = (ArrayList) hmCityList.get(TTKCommon
						.checkNull(frmCustomerBankAcctGeneral.get("bankname")));

			}// end of if(hmCityList!=null)
			if (alCityList == null) {
				alCityList = new ArrayList();
			}// end of if(alCityList==null)
			HashMap hmDistList = null;
			ArrayList alDistList = null;

			
			String bankState = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankState"));
			
			String bankName = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankname"));
			String bankDistict = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankcity"));
			
			hmDistList = customerBankDetailsManager.getBankCityInfo(bankState,
					bankName);
			if (hmDistList != null) {
				alDistList = (ArrayList) hmDistList.get(TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankState")));

			}// end of if(hmCityList!=null)
			if (alDistList == null) {
				alDistList = new ArrayList();
			}// end of if(alCityList==null)
			HashMap hmBranchList = null;
			ArrayList alBranchList = null;
			hmBranchList = customerBankDetailsManager.getBankBranchtInfo(bankState,bankName, bankDistict);
			if (hmBranchList != null) {
				alBranchList = (ArrayList) hmBranchList.get(TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankcity")));

			}// end of if(hmCityList!=null)
			if (alBranchList == null) {
				alBranchList = new ArrayList();
			}// end of if(alCityList==null)
			frmCustomerBankAcctGeneral.set("alBranchList", alBranchList);
			request.getSession().setAttribute("alBranchList", alBranchList);
			frmCustomerBankAcctGeneral.set("alDistList", alDistList);
			request.getSession().setAttribute("alDistList", alDistList);
			frmCustomerBankAcctGeneral.set("alCityList", alCityList);
			request.getSession().setAttribute("alCityList", alCityList);
			// request.getSession().setAttribute("alCityList",new ArrayList());
			frmCustomerBankAcctGeneral.set("editmode", strEditMode);
			frmCustomerBankAcctGeneral.set("caption", strCaption.toString());

			request.getSession().setAttribute("frmCustomerBankAcctGeneral",frmCustomerBankAcctGeneral);
			// request.getSession().removeAttribute("toolbar");
			return this.getForward(strBankacctdetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strBankAcctDetails));
		}// end of catch(Exception exp)
	}// end of doViewBankAccount(ActionMapping mapping,ActionForm
	// form,HttpServletRequest request,

	// HttpServletResponse response)
	
	
	
	public ActionForward doViewMemberAccountReviewDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			
			//setLinks(request);
			log.debug("Inside BankAccountGeneral doViewMemberAccountReviewDetails");
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			
			frmCustomerBankAcctGeneral.set("caption", "Review Provider Accounts");

			request.getSession().setAttribute("frmCustomerBankAcctGeneral",frmCustomerBankAcctGeneral);
			// request.getSession().removeAttribute("toolbar");
			return this.getForward(strBankHospAccountReviewDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strBankAcctDetails));
		}// end of catch(Exception exp)
	}// end of doViewMemberAccountReviewDetails(ActionMapping mapping,ActionForm

	/**
	 * This method is used to reload the screen when the reset button is
	 * pressed. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doReset(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		try {
			checkWebboardVisabulity(request);
			log.debug("Inside BankAccountGeneral doReset");
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			CustomerBankDetailsVO customerBankDetailsVO = new CustomerBankDetailsVO();
			customerBankDetailsVO = (CustomerBankDetailsVO) FormUtils
			.getFormValues(frmCustomerBankAcctGeneral, this, mapping, request);
			StringBuffer strCaption = new StringBuffer();
			// CustomerBankDetailsVO customerBankDetailsVO=null;
			String strEditMode = "";
			// BankAccountManager
			// bankAcctObject=this.getBankAccountManagerObject();
			CustomerBankDetailsManager customerBankDetailsManager = this
			.getCustomerBankDetailsManagerObject();

			strCaption.append("- Edit");
			/*
			 * bankAccountDetailVO=bankAcctObject.getBankAccountDetail(TTKCommon.
			 * getWebBoardId(request), TTKCommon.getUserSeqId(request));
			 */
			if (!TTKCommon.checkNull(customerBankDetailsVO.getInsurenceSeqId()).equals("")) {
				customerBankDetailsVO = customerBankDetailsManager.getPolicyBankAccountDetail((Long) TTKCommon.checkNull(customerBankDetailsVO.getPolicySeqID()), TTKCommon.getUserSeqId(request));
			} else if (!TTKCommon.checkNull(customerBankDetailsVO.getPolicyGroupSeqID()).equals("")) {
				customerBankDetailsVO = customerBankDetailsManager.getMemberBankAccountDetail((Long) TTKCommon.checkNull(customerBankDetailsVO.getPolicyGroupSeqID()), TTKCommon.getUserSeqId(request));
			} else if (!TTKCommon.checkNull(
					customerBankDetailsVO.getHospitalSeqId()).equals("")) {
				customerBankDetailsVO = customerBankDetailsManager.getHospBankAccountDetail(new Long(TTKCommon.checkNull(customerBankDetailsVO.getHospitalSeqId())), TTKCommon.getUserSeqId(request));
			}

			

			frmCustomerBankAcctGeneral = (DynaActionForm) FormUtils.setFormValues("frmCustomerBankAcctGeneral", customerBankDetailsVO, this,mapping, request);
			strEditMode = "Edit";
			HashMap hmCityList = null;
			ArrayList alCityList = null;
			ArrayList alCityCode = null;


			hmCityList = customerBankDetailsManager.getbankStateInfo();
			if (hmCityList != null) {
				alCityList = (ArrayList) hmCityList.get(TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankname")));

			}// end of if(hmCityList!=null)
			if (alCityList == null) {
				alCityList = new ArrayList();
			}// end of if(alCityList==null)
			//
			String bankState = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankState"));
			// String
			// bankState=(String)TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankcity"));
			String bankName = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankname"));
			String bankDistict = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankcity"));
			HashMap hmDistList = null;
			ArrayList alDistList = null;
			hmDistList = customerBankDetailsManager.getBankCityInfo(bankState,bankName);
			if (hmDistList != null) {
				alDistList = (ArrayList) hmDistList.get(TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankState")));

			}// end of if(hmCityList!=null)
			if (alDistList == null) {
				alDistList = new ArrayList();
			}// end of if(alCityList==null)
			HashMap hmBranchList = null;
			ArrayList alBranchList = null;
			hmBranchList = customerBankDetailsManager.getBankBranchtInfo(bankState,bankName, bankDistict);
			if (hmBranchList != null) {
				alBranchList = (ArrayList) hmBranchList.get(TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankcity")));

			}// end of if(hmCityList!=null)
			if (alBranchList == null) {
				alBranchList = new ArrayList();
			}// end of if(alCityList==null)
			frmCustomerBankAcctGeneral.set("alBranchList", alBranchList);
			request.getSession().setAttribute("alBranchList", alBranchList);
			frmCustomerBankAcctGeneral.set("alDistList", alDistList);
			request.getSession().setAttribute("alDistList", alDistList);
			frmCustomerBankAcctGeneral.set("alCityList", alCityList);
			request.getSession().setAttribute("alCityList", alCityList);
			frmCustomerBankAcctGeneral.set("reviewYN",customerBankDetailsVO.getReviewedYN());

			// keep the frmBankAcctGeneral in session scope
			request.getSession().setAttribute("frmCustomerBankAcctGeneral",
					frmCustomerBankAcctGeneral);
			return this.getForward(strBankacctdetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strBankAcctDetails));
		}// end of catch(Exception exp)
	}// end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest
	// request,HttpServletResponse response)

	public ActionForward doViewHospBankAccount(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			
			//setLinks(request);
			log.debug("Inside CustmerBankAccGeneral doViewHospBankAccount");
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			StringBuffer strCaption = new StringBuffer();
			String strEditMode = "";
			CustomerBankDetailsVO customerBankDetailsVO = null;
			String strSwitchType="HOSP";
			this.setLinks(request,strSwitchType);
			customerBankDetailsVO = (CustomerBankDetailsVO) FormUtils.getFormValues(frmCustomerBankAcctGeneral, this, mapping, request);
			CustomerBankDetailsManager customerBankDetailsManager = this.getCustomerBankDetailsManagerObject();
			frmCustomerBankAcctGeneral.initialize(mapping);
			if (TTKCommon.getWebBoardId(request) == null) {
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.hospaccountno.required");
				throw expTTK;
			}// end of if(TTKCommon.getWebBoardId(request) == null)
			strCaption.append("- Edit");
			strEditMode = "Edit";

			customerBankDetailsVO = customerBankDetailsManager
			.getHospBankAccountDetail(TTKCommon.getWebBoardId(request),
					TTKCommon.getUserSeqId(request));
			setFormValues(customerBankDetailsVO, mapping, request);

			frmCustomerBankAcctGeneral = (DynaActionForm) FormUtils.setFormValues("frmCustomerBankAcctGeneral", customerBankDetailsVO, this,mapping, request);

			//TTKCommon.deleteWebBoardId(request);
			// request.getSession().setAttribute("alCityList",new ArrayList());
			HashMap hmCityList = null;
			ArrayList alCityList = null;
			ArrayList alCityCode = null;

			hmCityList = customerBankDetailsManager.getbankStateInfo();
			if (hmCityList != null) {
				alCityList = (ArrayList) hmCityList.get(TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankname")));

			}// end of if(hmCityList!=null)
			if (alCityList == null) {
				alCityList = new ArrayList();
			}// end of if(alCityList==null)
			HashMap hmDistList = null;
			ArrayList alDistList = null;

			customerBankDetailsManager = this.getCustomerBankDetailsManagerObject();

			
			String bankState = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankState"));

			String bankName = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankname"));
			String bankDistict = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankcity"));
			
			hmDistList = customerBankDetailsManager.getBankCityInfo(bankState,bankName);
			if (hmDistList != null) {
				alDistList = (ArrayList) hmDistList.get(TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankState")));

			}// end of if(hmCityList!=null)
			if (alDistList == null) {
				alDistList = new ArrayList();
			}// end of if(alCityList==null)
			HashMap hmBranchList = null;
			ArrayList alBranchList = null;
			hmBranchList = customerBankDetailsManager.getBankBranchtInfo(bankState,bankName, bankDistict);
			if (hmBranchList != null) {
				alBranchList = (ArrayList) hmBranchList.get(TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankcity")));

			}// end of if(hmCityList!=null)
			if (alBranchList == null) {
				alBranchList = new ArrayList();
			}// end of if(alCityList==null)
			frmCustomerBankAcctGeneral.set("alBranchList", alBranchList);
			request.getSession().setAttribute("alBranchList", alBranchList);
			frmCustomerBankAcctGeneral.set("alDistList", alDistList);
			request.getSession().setAttribute("alDistList", alDistList);
			frmCustomerBankAcctGeneral.set("alCityList", alCityList);
			request.getSession().setAttribute("alCityList", alCityList);
			frmCustomerBankAcctGeneral.set("editmode", strEditMode);
			frmCustomerBankAcctGeneral.set("caption", strCaption.toString());
			
			frmCustomerBankAcctGeneral.set("reviewYN",customerBankDetailsVO.getReviewedYN());

			// keep the frmBankAcctGeneral in session scope
			request.getSession().setAttribute("frmCustomerBankAcctGeneral",
					frmCustomerBankAcctGeneral);
			return this.getForward(strBankacctdetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strBankAcctDetails));
		}// end of catch(Exception exp)
	}// end of doViewBankAccount(ActionMapping mapping,ActionForm
	// form,HttpServletRequest request,

	// HttpServletResponse response)

	public ActionForward doViewMemberAccount(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			
			//setLinks(request);
			log.debug("Inside BankAccountGeneral doViewBankAccount");
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			StringBuffer strCaption = new StringBuffer();
			String strEditMode = "";
			CustomerBankDetailsVO customerBankDetailsVO = null;
			HttpSession session=request.getSession();
			String strSwitchType="";
			DynaActionForm generalForm1=(DynaActionForm)session.getAttribute("frmCustomerBankDetailsSearch");
			strSwitchType=TTKCommon.checkNull((String)generalForm1.get("switchType"));
			strSwitchType=strSwitchType.concat("Mem");
			this.setLinks(request,strSwitchType);
			
			customerBankDetailsVO = (CustomerBankDetailsVO) FormUtils.getFormValues(frmCustomerBankAcctGeneral, this, mapping, request);

			CustomerBankDetailsManager customerBankDetailsManager = this.getCustomerBankDetailsManagerObject();
			frmCustomerBankAcctGeneral.initialize(mapping);
			if (TTKCommon.getWebBoardId(request) == null) {
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.memberaccountno.required");
				throw expTTK;
			}// end of if(TTKCommon.getWebBoardId(request) == null)
			strCaption.append("- Edit");
			strEditMode = "Edit";

			customerBankDetailsVO = customerBankDetailsManager.getMemberBankAccountDetail(TTKCommon.getWebBoardId(request), TTKCommon.getUserSeqId(request));

			frmCustomerBankAcctGeneral = (DynaActionForm) FormUtils.setFormValues("frmCustomerBankAcctGeneral", customerBankDetailsVO, this,mapping, request);// TTKCommon.getWebBoardId(request),

			setFormValues(customerBankDetailsVO, mapping, request);
			//TTKCommon.deleteWebBoardId(request);
			HashMap hmCityList = null;
			ArrayList alCityList = null;
			ArrayList alCityCode = null;
			// DynaActionForm frmCustomerBankAcctGeneral= (DynaActionForm)form;

			hmCityList = customerBankDetailsManager.getbankStateInfo();
			if (hmCityList != null) {
				alCityList = (ArrayList) hmCityList.get(TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankname")));

			}// end of if(hmCityList!=null)
			if (alCityList == null) {
				alCityList = new ArrayList();
			}// end of if(alCityList==null)
			HashMap hmDistList = null;
			ArrayList alDistList = null;

			customerBankDetailsManager = this.getCustomerBankDetailsManagerObject();

			
			String bankState = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankState"));
			
			String bankName = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankname"));
			String bankDistict = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankcity"));
			
			hmDistList = customerBankDetailsManager.getBankCityInfo(bankState,
					bankName);
			if (hmDistList != null) {
				alDistList = (ArrayList) hmDistList.get(TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankState")));

			}// end of if(hmCityList!=null)
			if (alDistList == null) {
				alDistList = new ArrayList();
			}// end of if(alCityList==null)
			HashMap hmBranchList = null;
			ArrayList alBranchList = null;
			hmBranchList = customerBankDetailsManager.getBankBranchtInfo(bankState,bankName, bankDistict);
			if (hmBranchList != null) {
				alBranchList = (ArrayList) hmBranchList.get(TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankcity")));

			}// end of if(hmCityList!=null)
			if (alBranchList == null) {
				alBranchList = new ArrayList();
			}// end of if(alCityList==null)
			frmCustomerBankAcctGeneral.set("alBranchList", alBranchList);
			request.getSession().setAttribute("alBranchList", alBranchList);
			frmCustomerBankAcctGeneral.set("alDistList", alDistList);
			request.getSession().setAttribute("alDistList", alDistList);
			frmCustomerBankAcctGeneral.set("alCityList", alCityList);
			request.getSession().setAttribute("alCityList", alCityList);
			// request.getSession().setAttribute("alCityList",new ArrayList());
			frmCustomerBankAcctGeneral.set("editmode", strEditMode);
			frmCustomerBankAcctGeneral.set("caption", strCaption.toString());
			// keep the frmBankAcctGeneral in session scope
			request.getSession().setAttribute("frmCustomerBankAcctGeneral",
					frmCustomerBankAcctGeneral);
			return this.getForward(strBankacctdetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strBankAcctDetails));
		}// end of catch(Exception exp)
	}// end of doViewBankAccount(ActionMapping mapping,ActionForm
	// form,HttpServletRequest request,

	// HttpServletResponse response)

	// added For EFT
	/**
	 * This method is used to add/update the record. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param reques The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)throws Exception {
		try {
			HttpSession session=request.getSession();
			String strSwitchType="";
			DynaActionForm generalForm1=(DynaActionForm)session.getAttribute("frmCustomerBankDetailsSearch");
			strSwitchType=TTKCommon.checkNull((String)generalForm1.get("switchType"));
			strSwitchType=strSwitchType.concat("Polc");
			this.setLinks(request,strSwitchType);
			log.debug("Inside CustomerBankAccGeneral doSave");
			// BankAccountDetailVO bankAccountDetailVO=null;
			String strTarget = "";
			CustomerBankDetailsVO customerBankDetailsVO = new CustomerBankDetailsVO();
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			
			CustomerBankDetailsManager customerBankDetailsManager = this.getCustomerBankDetailsManagerObject();
			String strEditMode = "";
			StringBuffer strCaption = new StringBuffer();
			customerBankDetailsVO = (CustomerBankDetailsVO) FormUtils.getFormValues(frmCustomerBankAcctGeneral, this, mapping, request);
			customerBankDetailsVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User
						String switchTypeFinance = (String) request.getAttribute("switchTypeFinance");

			ArrayList<Object> alModifiedHospInfo = new ArrayList<Object>();
			alModifiedHospInfo = populateAccountInfo(frmCustomerBankAcctGeneral,mapping, request);

			boolean bFlag = true;
			// call the checkDifference method to check whether any change is
			// made in the bank information
			if (request.getSession().getAttribute("alHospInfofinance") != null) {
				
				bFlag = isModified(alModifiedHospInfo, (ArrayList) request.getSession().getAttribute("alHospInfofinance"));
				
			}// end of if(request.getSession().getAttribute("alHospInfo")!=null)
			if (bFlag == false) {
				strTarget = "referencedetail";
				frmCustomerBankAcctGeneral.set("flagValidate", "true");
				return this.getForward(strTarget, mapping, request);
			}// end of if(bFlag==false)

			// Long
			// iUpdate=bankAcctObject.saveBankAccountIfsc(customerBankDetailsVO);
			Long iUpdate = customerBankDetailsManager.saveBankAccountIfsc(customerBankDetailsVO);
			// set the appropriate message
			if (iUpdate > 0) {
				if (frmCustomerBankAcctGeneral.get("policySeqID") != null && !frmCustomerBankAcctGeneral.get("policySeqID").equals("")) {
					request.setAttribute("updated","message.savedSuccessfully");
					request.setAttribute("cacheId", "" + iUpdate);
					request.setAttribute("cacheDesc", customerBankDetailsVO.getInsureName());
					// finally modify the web board details, if the account no.
					// is changed
					TTKCommon.modifyWebBoardId(request);

				}// end of if(frmBankAcctGeneral.get("accountSeqID")!=null &&
				
				else {
					request.setAttribute("updated","message.addedSuccessfully");
					customerBankDetailsVO.setPolicySeqID(iUpdate);
					// bankAccountDetailVO.setAccountSeqID(iUpdate);
					this.addToWebBoard(customerBankDetailsVO, request);
				}// end of else if(frmBankAcctGeneral.get("accountSeqID")!=null
				
				frmCustomerBankAcctGeneral.initialize(mapping);
				customerBankDetailsVO = customerBankDetailsManager.getPolicyBankAccountDetail((Long) TTKCommon.checkNull(customerBankDetailsVO.getPolicySeqID()), TTKCommon.getUserSeqId(request));
				strEditMode = "Edit";
				strCaption.append("- Edit");
				frmCustomerBankAcctGeneral = (DynaActionForm) FormUtils.setFormValues("frmCustomerBankAcctGeneral", customerBankDetailsVO, this,mapping, request);
				if (frmCustomerBankAcctGeneral.get("policySeqID") == null || frmCustomerBankAcctGeneral.get("policySeqID").equals("")) {
					frmCustomerBankAcctGeneral.set("policySeqID", iUpdate.toString());
				} else {
					// frmCustomerBankAcctGeneral.set("status",customerBankDetailsVO.getStatusTypeID());
				}
				frmCustomerBankAcctGeneral.set("caption", strCaption.toString());
				frmCustomerBankAcctGeneral.set("editmode", strEditMode);
				// keep the frmBankAcctGeneral in session scope
				request.getSession().setAttribute("frmCustomerBankAcctGeneral",
						frmCustomerBankAcctGeneral);
			}// end of if(iUpdate > 0)
			return this.getForward(strBankacctdetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strBankAcctDetails));
		}// end of catch(Exception exp)
	}// end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest
	// request,HttpServletResponse response)

	/**
	 * This method is used to get the details of the selected record from
	 * web-board. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		log.debug("Inside BankAccountGeneral doChangeWebBoard");
		HttpSession session=request.getSession();
		String strSwitchType="";
		DynaActionForm generalForm1=(DynaActionForm)session.getAttribute("frmCustomerBankDetailsSearch");
		strSwitchType=TTKCommon.checkNull((String)generalForm1.get("switchType"));
		
		if(strSwitchType.equals("HOSP"))
		{
			return	doViewHospBankAccount(mapping, form, request, response);
		}
		else
		{
		return doViewMemberAccount(mapping, form, request, response);
		}
	}// end of ChangeWebBoard(ActionMapping mapping,ActionForm
	// form,HttpServletRequest request,

	// HttpServletResponse response)

	/**
	 * This method is used to add/update the record. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSaveMember(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		try {
			HttpSession session=request.getSession();
			String strSwitchType="";
			DynaActionForm generalForm1=(DynaActionForm)session.getAttribute("frmCustomerBankDetailsSearch");
			strSwitchType=TTKCommon.checkNull((String)generalForm1.get("switchType"));
			strSwitchType=strSwitchType.concat("Mem");
			this.setLinks(request,strSwitchType);
			log.debug("Inside BankAccountGeneral doSave");
			// BankAccountDetailVO bankAccountDetailVO=null;
			CustomerBankDetailsVO customerBankDetailsVO = new CustomerBankDetailsVO();
			String strTarget = "";
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			// BankAccountManager
			// bankAcctObject=this.getBankAccountManagerObject();
			CustomerBankDetailsManager customerBankDetailsManager = this.getCustomerBankDetailsManagerObject();
			String strEditMode = "";
			StringBuffer strCaption = new StringBuffer();
			customerBankDetailsVO = (CustomerBankDetailsVO) FormUtils
			.getFormValues(frmCustomerBankAcctGeneral, this, mapping, request);
			customerBankDetailsVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User
			// Id

			// calling business layer to save the bank account detail
			String switchTypeFinance = (String) request.getAttribute("switchTypeFinance");
			ArrayList<Object> alModifiedHospInfo = new ArrayList<Object>();
			alModifiedHospInfo = populateAccountInfo(frmCustomerBankAcctGeneral,mapping, request);

			boolean bFlag = true;
			// call the checkDifference method to check whether any change is
			// made in the bank information
			if (request.getSession().getAttribute("alHospInfofinance") != null) {
				
				
				bFlag = isModified(alModifiedHospInfo, (ArrayList) request.getSession().getAttribute("alHospInfofinance"));
				
			}// end of if(request.getSession().getAttribute("alHospInfo")!=null)
			if (bFlag == false) {
				strTarget = "referencedetail";
				frmCustomerBankAcctGeneral.set("flagValidate", "true");
				return this.getForward(strTarget, mapping, request);
			}// end of if(bFlag==false)
			// Long
			// iUpdate=bankAcctObject.saveMemberBankAccountIfsc(customerBankDetailsVO);
			Long iUpdate = customerBankDetailsManager.saveMemberBankAccountIfsc(customerBankDetailsVO);
			// set the appropriate message;
			if (iUpdate > 0) {
				
				if (frmCustomerBankAcctGeneral.get("policySeqID") != null && !frmCustomerBankAcctGeneral.get("policySeqID").equals("")) {
					request.setAttribute("updated","message.savedSuccessfully");
					request.setAttribute("cacheId", "" + iUpdate);
					request.setAttribute("cacheDesc", customerBankDetailsVO.getInsureName());
					// finally modify the web board details, if the account no.
					// is changed
					TTKCommon.modifyWebBoardId(request);

				}// end of if(frmBankAcctGeneral.get("accountSeqID")!=null &&
				// !frmBankAcctGeneral.get("accountSeqID")
				// .equals(""))
				else {
					request.setAttribute("updated","message.addedSuccessfully");
					
					this.addToWebBoard(customerBankDetailsVO, request);
				}// end of else if(frmBankAcctGeneral.get("accountSeqID")!=null
				
				frmCustomerBankAcctGeneral.initialize(mapping);
				customerBankDetailsVO = customerBankDetailsManager.getMemberBankAccountDetail((Long) TTKCommon.checkNull(customerBankDetailsVO.getPolicyGroupSeqID()), TTKCommon.getUserSeqId(request));
				// getMemberBankAccountdetail1(TTKCommon.getWebBoardId(request),TTKCommon.getUserSeqId(request));
				strEditMode = "Edit";
				strCaption.append("- Edit");
				frmCustomerBankAcctGeneral = (DynaActionForm) FormUtils.setFormValues("frmCustomerBankAcctGeneral", customerBankDetailsVO, this,mapping, request);
				if (frmCustomerBankAcctGeneral.get("policySeqID") == null || frmCustomerBankAcctGeneral.get("policySeqID").equals("")) {
					frmCustomerBankAcctGeneral.set("policyGroupSeqID", iUpdate.toString());
				} else {
					// frmCustomerBankAcctGeneral.set("status",customerBankDetailsVO.getStatusTypeID());
				}
				setFormValues(customerBankDetailsVO, mapping, request);
				frmCustomerBankAcctGeneral.set("caption", strCaption.toString());
				frmCustomerBankAcctGeneral.set("editmode", strEditMode);
				// keep the frmBankAcctGeneral in session scope
				request.getSession().setAttribute("frmCustomerBankAcctGeneral",
						frmCustomerBankAcctGeneral);
			}// end of if(iUpdate > 0)
			return this.getForward(strBankacctdetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strBankAcctDetails));
		}// end of catch(Exception exp)
	}// end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest
	// request,HttpServletResponse response)
	/**
	 * This method is used to add/update the record. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSaveHosp(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		try {
			HttpSession session=request.getSession();
			String strSwitchType="";
			DynaActionForm generalForm1=(DynaActionForm)session.getAttribute("frmCustomerBankDetailsSearch");
			strSwitchType=TTKCommon.checkNull((String)generalForm1.get("switchType"));
			this.setLinks(request,strSwitchType);
			String strTarget = "";
			log.debug("Inside BankAccountGeneral doSave");
			// BankAccountDetailVO bankAccountDetailVO=null;
			CustomerBankDetailsVO customerBankDetailsVO = new CustomerBankDetailsVO();
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			// BankAccountManager
			// bankAcctObject=this.getBankAccountManagerObject();
			CustomerBankDetailsManager customerBankDetailsManager = this
			.getCustomerBankDetailsManagerObject();
			String strEditMode = "";
			StringBuffer strCaption = new StringBuffer();
			customerBankDetailsVO = (CustomerBankDetailsVO) FormUtils
			.getFormValues(frmCustomerBankAcctGeneral, this, mapping, request);
             customerBankDetailsVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User
			// Id
			// calling business layer to save the bank account detail
			ArrayList<Object> alModifiedHospInfo = new ArrayList<Object>();
            // Call populateAccountInfo to store Hospital Bank Account
			// Information in arraylist
			alModifiedHospInfo = populateAccountInfo(frmCustomerBankAcctGeneral,mapping, request);
			boolean bFlag = true;
			// call the checkDifference method to check whether any change is
			// made in the bank information
			if (request.getSession().getAttribute("alHospInfofinance") != null) 
			{
				bFlag = isModified(alModifiedHospInfo, (ArrayList) request.getSession().getAttribute("alHospInfofinance"));
            }// end of if(request.getSession().getAttribute("alHospInfo")!=null)
		if (bFlag == false) 
		{	
			    request.getSession().setAttribute("hosp_seq_id", customerBankDetailsVO.getHospitalSeqId());
				strTarget = "referencedetail";
				frmCustomerBankAcctGeneral.set("flagValidate", "true");
				return this.getForward(strTarget, mapping, request);
		}// end of if(bFlag==false
			Long iUpdate = customerBankDetailsManager.saveHospBankAccountIfsc(customerBankDetailsVO);
			// set the appropriate message
			if (iUpdate >0) {
				
				if (frmCustomerBankAcctGeneral.get("hospitalSeqId") != null && !frmCustomerBankAcctGeneral.get("hospitalSeqId").equals("")) {
					request.setAttribute("updated","message.savedSuccessfully");
					request.setAttribute("cacheId", "" + iUpdate);
					request.setAttribute("cacheDesc", customerBankDetailsVO.getEmpanalDescription());
					// finally modify the web board details, if the account no.
					// is changed
					TTKCommon.modifyWebBoardId(request);

				}// end of if(frmBankAcctGeneral.get("accountSeqID")!=null &&
				// !frmBankAcctGeneral.get("accountSeqID")
				// .equals(""))
				else {
					request.setAttribute("updated",	"message.addedSuccessfully");
					
				}// end of else if(frmBankAcctGeneral.get("accountSeqID")!=null
				
				frmCustomerBankAcctGeneral.initialize(mapping);
				customerBankDetailsVO = customerBankDetailsManager.getHospBankAccountDetail(new Long(TTKCommon.checkNull(customerBankDetailsVO.getHospitalSeqId())), TTKCommon.getUserSeqId(request));
				// getMemberBankAccountdetail1(TTKCommon.getWebBoardId(request),TTKCommon.getUserSeqId(request));
                strEditMode = "Edit";
				strCaption.append("- Edit");
				frmCustomerBankAcctGeneral = (DynaActionForm) FormUtils.setFormValues("frmCustomerBankAcctGeneral", customerBankDetailsVO, this,mapping, request);
		        frmCustomerBankAcctGeneral.set("caption", strCaption.toString());
				frmCustomerBankAcctGeneral.set("editmode", strEditMode);
				frmCustomerBankAcctGeneral.set("reviewYN",TTKCommon.checkNull(customerBankDetailsVO.getReviewedYN()));
				// keep the frmBankAcctGeneral in session scope
				request.getSession().setAttribute("frmCustomerBankAcctGeneral",
						frmCustomerBankAcctGeneral);
			}// end of if(iUpdate > 0)
			return this.getForward(strBankacctdetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strBankAcctDetails));
		}// end of catch(Exception exp)
	}

	/**
	 * This method is used to add the reference information. Finally it forwards
	 * to the appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	
	
	public ActionForward doUpload(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
			{
		try{
			CustomerBankDetailsVO customerBankDetailsVO = null;
			setLinks(request);
		    Long lngFileData=null;
         	int iUpdate = 0;
      		String fileName="";
      		String strNotify="";
			String origFileName	=	"";
			Pattern pattern =null;
			Matcher matcher =null;
			FileOutputStream outputStream = null;
			FormFile formFile = null;
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			formFile = (FormFile) frmCustomerBankAcctGeneral.get("file");
			
			TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
			DateFormat df =new SimpleDateFormat("ddMMyyyy HH:mm:ss:S");
			df.setTimeZone(tz);  
			int fileSize=10*1024*1024;
			String hos_seqid = (String)request.getSession().getAttribute("hosp_seq_id");
			log.info("hos_seqid========="+hos_seqid);
			if(hos_seqid == null || hos_seqid.equals(""))
				hos_seqid = "0";
			Long hospSeqId	= Long.parseLong(hos_seqid);
			formFile = (FormFile) frmCustomerBankAcctGeneral.get("file");
			  
			pattern = Pattern.compile( "[^a-zA-Z0-9_\\.\\-\\s*]" );
			matcher = pattern.matcher( formFile.getFileName() );
			String 	strTimeStamp=((df.format(Calendar.getInstance(tz).getTime())).replaceAll(" ", "_")).replaceAll(":", ""); 
			fileName=strTimeStamp+"_"+formFile.getFileName();
			Long userSeqId	=	(Long) TTKCommon.getUserSeqId(request);
			origFileName	=	formFile.getFileName();
			InputStream inputStream	=	 formFile.getInputStream();//INPUT STREAM  FROM FILE UPLOADED
			int formFileSize	=	formFile.getFileSize();
			CustomerBankDetailsVO customerBankDetailsVOO = new CustomerBankDetailsVO();
      		customerBankDetailsVOO.setUpdatedBy(TTKCommon.getUserSeqId(request));
      		String hosp_seq_id	=	(String)request.getSession().getAttribute("hosp_seq_id");
            ArrayList<Object> alFileAUploadList = new ArrayList<Object>();
      		ArrayList<CustomerBankDetailsVO>alLinkDetailsList=null;
			alFileAUploadList.add(TTKCommon.getUserSeqId(request));//0
			CustomerBankDetailsManager customerBankDetailsManager = this
					.getCustomerBankDetailsManagerObject();

            if(!matcher.find())
            {
            	customerBankDetailsVO = getFormValues((DynaActionForm) form,mapping, request);
            	customerBankDetailsVO.setDocName(fileName);
            	alFileAUploadList.add(fileName);//1
            	alFileAUploadList.add(hospSeqId);//2
            	String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("mouUploads"));
            	if(path.equals(""))	{
            		path="D:\\\\home\\\\jboss\\\\jboss-as-7.1.1.Final\\\\bin\\\\moudocs\\\\";
            	}//
            	File folder = new File(path);
            	if(!folder.exists()){
            		folder.mkdir();
            	}
            	String finalPath=(path+fileName);	
            	String strFileExt=formFile.getFileName().substring(formFile.getFileName().lastIndexOf(".")+1,formFile.getFileName().length());
            	if(formFile.getFileSize()<=fileSize)
            	{       
            		outputStream = new FileOutputStream(new File(finalPath));
            		outputStream.write(formFile.getFileData());
            		alFileAUploadList.add(finalPath);//3 This code adds Total path of the file
            		iUpdate=customerBankDetailsManager.saveMouUploads(customerBankDetailsVO,alFileAUploadList,userSeqId,hosp_seq_id,origFileName,inputStream,formFileSize);
            		if(iUpdate>0)
            		{
            			frmCustomerBankAcctGeneral.initialize(mapping);
            			alLinkDetailsList=customerBankDetailsManager.getMouUploadsList(hosp_seq_id);
            			request.getSession().setAttribute("alLinkDetailsList", alLinkDetailsList);

            		}// end of if(iUpdate>0)

            		frmCustomerBankAcctGeneral = (DynaActionForm)FormUtils.setFormValues("frmCustomerBankAcctGeneral",customerBankDetailsVO,this,mapping,request);
            		frmCustomerBankAcctGeneral.set("flagValidate", "true");
            	}//end of if(formFile.getFileSize()<=fileSize)
            	else{
            		strNotify="selected "+formFile.getFileName()+" size should be less than or equal to 10 MB";
            	}//end of else part if(formFile.getFileSize()<=fileSize)
            }
            else
			{
            	//updated="selected "+formFile.getFileName()+" file is not in pdf format,Please select pdf file";
            	strNotify="selected "+formFile.getFileName()+" file Should not have Special Characters like }!@#$%^&amp;*(])[{+";
            }
            request.getSession().setAttribute("notify", strNotify);
            request.getSession().setAttribute("frmCustomerBankAcctGeneral", frmCustomerBankAcctGeneral);

            return this.getForward(strReferencedetail, mapping, request);
			
		}
        catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strAccountsError));
		}// end of catch(Exception exp)
		
			}
	public ActionForward doReferenceDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception
			{
		try {
			//setLinks(request);
			checkWebboardVisabulity(request);
			log.debug("Inside doReferenceDetail method of AccountsAction");
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			String strEditMode = "";
			StringBuffer strCaption = new StringBuffer();

			// BankAccountManager
			// bankAcctObject=this.getBankAccountManagerObject();
			CustomerBankDetailsManager customerBankDetailsManager = this
					.getCustomerBankDetailsManagerObject();
			AccountDetailVO accountDetailVO = null;
			CustomerBankDetailsVO customerBankDetailsVO = null;


			ArrayList<Object> alHospInfo = new ArrayList<Object>();
			// Call populateAccountInfo to store Hospital Bank Account
			// Information in arraylist
			alHospInfo = populateAccountInfo(frmCustomerBankAcctGeneral, mapping,
					request);
			// Set the alHospInfo into session
			request.getSession().setAttribute("alHospInfo", alHospInfo);
			customerBankDetailsVO = getFormValues((DynaActionForm) form,
					mapping, request);
			// logs

			// end logs
			if (!TTKCommon.checkNull(customerBankDetailsVO.getPolicyGroupSeqID()).equals("")) {

				Long iUpdate = customerBankDetailsManager.saveMemberBankAccountIfsc(customerBankDetailsVO);
				frmCustomerBankAcctGeneral.initialize(mapping);
				customerBankDetailsVO = customerBankDetailsManager.getMemberBankAccountDetail((Long) TTKCommon.checkNull(customerBankDetailsVO.getPolicyGroupSeqID()), TTKCommon.getUserSeqId(request));
		
				request.getSession().setAttribute("ins_seq_id", "");
				request.getSession().setAttribute("policy_seq_id", "");
				request.getSession().setAttribute("policy_group_seq_id", customerBankDetailsVO.getPolicyGroupSeqID().toString());
				request.getSession().setAttribute("hosp_seq_id", "");
			} else if (!TTKCommon.checkNull(customerBankDetailsVO.getInsurenceSeqId()).equals("")) {

				Long iUpdate = customerBankDetailsManager.saveBankAccountIfsc(customerBankDetailsVO);
				frmCustomerBankAcctGeneral.initialize(mapping);
				// customerBankDetailsVO=bankAcctObject.getPolicyBankAccountDetail((Long)TTKCommon.checkNull(customerBankDetailsVO.getPolicySeqID()),TTKCommon.getUserSeqId(request));
				customerBankDetailsVO = customerBankDetailsManager.getPolicyBankAccountDetail((Long) TTKCommon.checkNull(customerBankDetailsVO.getPolicySeqID()), TTKCommon.getUserSeqId(request));
			
				request.getSession().setAttribute("ins_seq_id", customerBankDetailsVO.getInsurenceSeqId().toString());
				request.getSession().setAttribute("policy_seq_id", customerBankDetailsVO.getPolicySeqID().toString());
				request.getSession().setAttribute("policy_group_seq_id", "");
				request.getSession().setAttribute("hosp_seq_id", "");
			} else if (!customerBankDetailsVO.getHospitalSeqId().equals("")) {
				Long iUpdate = customerBankDetailsManager
						.saveHospBankAccountIfsc(customerBankDetailsVO);
				frmCustomerBankAcctGeneral.initialize(mapping);
				customerBankDetailsVO = customerBankDetailsManager.getHospBankAccountDetail(new Long(TTKCommon.checkNull(customerBankDetailsVO.getHospitalSeqId())), TTKCommon.getUserSeqId(request));
				request.getSession().setAttribute("ins_seq_id", "");
				request.getSession().setAttribute("policy_seq_id", "");
				request.getSession().setAttribute("policy_group_seq_id", "");
				request.getSession().setAttribute("hosp_seq_id", customerBankDetailsVO.getHospitalSeqId());
			}
			HashMap hmCityList = null;
			ArrayList alCityList = null;
			ArrayList alCityCode = null;

			String bankName = (String) TTKCommon.checkNull(customerBankDetailsVO.getBankname());
			hmCityList = customerBankDetailsManager.getbankStateInfo();
			if (hmCityList != null) {
				alCityList = (ArrayList) hmCityList.get(bankName);

			}// end of if(hmCityList!=null)
			if (alCityList == null) {
				alCityList = new ArrayList();
			}// end of if(alCityList==null)
			frmCustomerBankAcctGeneral = (DynaActionForm) FormUtils.setFormValues("frmCustomerBankAcctGeneral", customerBankDetailsVO, this,mapping, request);


			frmCustomerBankAcctGeneral.set("alCityList", alCityList);
			request.getSession().setAttribute("alCityList", alCityList);
			// getMemberBankAccountdetail1(TTKCommon.getWebBoardId(request),TTKCommon.getUserSeqId(request));
			strEditMode = "Edit";
			strCaption.append("- Edit");

			setFormValues(customerBankDetailsVO, mapping, request);
			frmCustomerBankAcctGeneral.set("caption", strCaption.toString());
			frmCustomerBankAcctGeneral.set("editmode", strEditMode);
			// keep the frmBankAcctGeneral in session scope
			request.getSession().setAttribute("frmCustomerBankAcctGeneral",frmCustomerBankAcctGeneral);
			request.getSession().setAttribute("updated", "Message saved successfully");
			return this.getForward(strReferencedetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strAccountsError));
		}// end of catch(Exception exp)
			}// end of doReferenceDetail(ActionMapping mapping,ActionForm
	// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward doMoudocumentUpload(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
			{
	          	try
	          	{
	          		CustomerBankDetailsVO customerBankDetailsVO = new CustomerBankDetailsVO();
	          		customerBankDetailsVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
	          		DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm)form;
	          		String hosp_seq_id	=	(String)request.getSession().getAttribute("hosp_seq_id");
	          		ArrayList<Object> alFileAUploadList = new ArrayList<Object>();
	          		ArrayList alLinkDetailsList=null;
	    			alFileAUploadList.add(TTKCommon.getUserSeqId(request));//0
	          		CustomerBankDetailsManager customerBankDetailsManager = this.getCustomerBankDetailsManagerObject();
	          		Long lngFileData=null;
	          		int iUpdate = 0;
	          		String fileName="";
	          		String strNotify="";
	    			String origFileName	=	"";
	    			Pattern pattern =null;
	    			Matcher matcher =null;
	    			FileOutputStream outputStream = null;
	    			FormFile formFile = null;
	    			TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");
	    			DateFormat df =new SimpleDateFormat("ddMMyyyy HH:mm:ss:S");
	    			df.setTimeZone(tz);  
	    			int fileSize=10*1024*1024;
	    			String hos_seqid = (String)request.getSession().getAttribute("hosp_seq_id");
	    			Long hospSeqId	= Long.parseLong(hos_seqid);
	    			formFile = (FormFile) frmCustomerBankAcctGeneral.get("file");
	    			pattern = Pattern.compile( "[^a-zA-Z0-9_\\.\\-\\s*]" );
	    			matcher = pattern.matcher( formFile.getFileName() );
	    			String 	strTimeStamp=((df.format(Calendar.getInstance(tz).getTime())).replaceAll(" ", "_")).replaceAll(":", ""); 
	    			fileName=strTimeStamp+"_"+formFile.getFileName();
	    			Long userSeqId	=	(Long) TTKCommon.getUserSeqId(request);
	    			origFileName	=	formFile.getFileName();
	    			InputStream inputStream	=	 formFile.getInputStream();//INPUT STREAM  FROM FILE UPLOADED
	    			int formFileSize	=	formFile.getFileSize();
	    			if(!matcher.find())
	    			{
	    				customerBankDetailsVO.setDocName(fileName);
	    				alFileAUploadList.add(fileName);//1
	    				alFileAUploadList.add(hospSeqId);//2
	    				String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("mouUploads"));
	    				if(path.equals(""))	{
	    					path="D:\\\\home\\\\jboss\\\\jboss-as-7.1.1.Final\\\\bin\\\\moudocs\\\\";
	    				}//
	    				File folder = new File(path);
	    				if(!folder.exists()){
	    					folder.mkdir();
	    				}
	    				String finalPath=(path+fileName);
	    				String strFileExt=formFile.getFileName().substring(formFile.getFileName().lastIndexOf(".")+1,formFile.getFileName().length());
	    				if(formFile.getFileSize()<=fileSize)
	    				{
	    					        outputStream = new FileOutputStream(new File(path));
	    							outputStream.write(formFile.getFileData());	    							
	    							alFileAUploadList.add(finalPath);//3 This code adds Total path of the file
	    							iUpdate=customerBankDetailsManager.saveMouUploads(customerBankDetailsVO,alFileAUploadList,userSeqId,hosp_seq_id,origFileName,inputStream,formFileSize);
	    							if(iUpdate>0)
	    							{
	    								/*if(frmCustomerBankAcctGeneral.get("mouDocSeqID")!=null)
	    								{
	    									//set the appropriate message
	    									request.setAttribute("updated","message.savedSuccessfully");
	    								}//end of if(frmMOUDocs.get("configLinkSeqID")!=null)
	    								else
	    								{
	    									//set the appropriate message
	    									request.setAttribute("updated","message.addedSuccessfully");
	    								}//end else  */
	    								frmCustomerBankAcctGeneral.initialize(mapping);
	    						    	alLinkDetailsList=customerBankDetailsManager.getMouUploadsList(hosp_seq_id);
	    						    	request.getSession().setAttribute("alLinkDetailsList", alLinkDetailsList);
	    						    	
	    								//tableDataMouCertificates.setData(alLinkDetailsList);
	    								//frmCustomerBankAcctGeneral.set("caption",sbfCaption.toString());
	    								//request.getSession().setAttribute("tableDataMouCertificates",tableDataMouCertificates);
	    								//request.setAttribute("frmMOUDocs",frmMOUDocs);
	    							}// end of if(iUpdate>0)
    							
	    							frmCustomerBankAcctGeneral = (DynaActionForm)FormUtils.setFormValues("frmCustomerBankAcctGeneral",customerBankDetailsVO,this,mapping,request);
	    						
	    				}//end of if(formFile.getFileSize()<=fileSize)
	    				else{
	    					strNotify="selected "+formFile.getFileName()+" size  Shold be less than or equal to 10 MB";
	    				}//end of else part if(formFile.getFileSize()<=fileSize)
	    			}
	    			else{
	    				//updated="selected "+formFile.getFileName()+" file is not in pdf format,Please select pdf file";
	    				strNotify="selected "+formFile.getFileName()+" file Should not have Special Characters like }!@#$%^&amp;*(])[{+";

	    			}
	    			return mapping.findForward(strReferencedetail);
		        }//end of try
		        catch(Exception exp)
		        {
			         return this.processExceptions(request,mapping,new TTKException(exp,strAccountsError));
		        }//end of catch(Exception exp)

     	}

	

	/**
	 * This method is used to navigate to previous screen when closed button is
	 * clicked. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doClose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		try {
			checkWebboardVisabulity(request);
			log.info("Inside doClose method of AccountsAction");
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			frmCustomerBankAcctGeneral.set("flagValidate", null);
			CustomerBankDetailsManager customerBankDetailsManager = this
					.getCustomerBankDetailsManagerObject();
			HashMap hmCityList = null;
			ArrayList alCityList = null;
			ArrayList alCityCode = null;
			String strEditMode = "";
			
			StringBuffer strCaption = new StringBuffer();
			CustomerBankDetailsVO customerBankDetailsVO = null;
			
			
			String ins_seq_id	=	(String)request.getSession().getAttribute("ins_seq_id");
			String policy_group_seq_id	=	(String)request.getSession().getAttribute("policy_group_seq_id");
			String hosp_seq_id	=	(String)request.getSession().getAttribute("hosp_seq_id");

			
			   if (ins_seq_id!=null&&!ins_seq_id.equals("")) 
			    {
					frmCustomerBankAcctGeneral.initialize(mapping);
					String policy_seq_id	=	(String)request.getSession().getAttribute("policy_seq_id");
					customerBankDetailsVO = customerBankDetailsManager.getPolicyBankAccountDetail(new Long(TTKCommon.checkNull(policy_seq_id)), TTKCommon.getUserSeqId(request));
				}
			
			   else if (policy_group_seq_id!=null&&!policy_group_seq_id.equals("")) 
			    {
					frmCustomerBankAcctGeneral.initialize(mapping);
					customerBankDetailsVO = customerBankDetailsManager.getMemberBankAccountDetail(new Long(TTKCommon.checkNull(policy_group_seq_id)), TTKCommon.getUserSeqId(request));
				}
			
			   else if (hosp_seq_id!=null&&!hosp_seq_id.equals("")) 
			   {
				frmCustomerBankAcctGeneral.initialize(mapping);
				customerBankDetailsVO = customerBankDetailsManager.getHospBankAccountDetail(new Long(TTKCommon.checkNull(hosp_seq_id)), TTKCommon.getUserSeqId(request));
			   }
            String bankName = (String) TTKCommon.checkNull(customerBankDetailsVO.getBankname());
			hmCityList = customerBankDetailsManager.getbankStateInfo();
			if (hmCityList != null) {
				alCityList = (ArrayList) hmCityList.get(bankName);
				
			}// end of if(hmCityList!=null)
			if (alCityList == null) {
				alCityList = new ArrayList();
			}// end of if(alCityList==null)
			
			frmCustomerBankAcctGeneral = (DynaActionForm) FormUtils.setFormValues("frmCustomerBankAcctGeneral", customerBankDetailsVO, this,mapping, request);
            frmCustomerBankAcctGeneral.set("alCityList", alCityList);
			request.getSession().setAttribute("alCityList", alCityList);
			// getMemberBankAccountdetail1(TTKCommon.getWebBoardId(request),TTKCommon.getUserSeqId(request));
			strEditMode = "Edit";
			strCaption.append("- Edit");
            setFormValues(customerBankDetailsVO, mapping, request);
			frmCustomerBankAcctGeneral.set("caption", strCaption.toString());
			frmCustomerBankAcctGeneral.set("editmode", strEditMode);
			// keep the frmBankAcctGeneral in session scope
			request.getSession().setAttribute("frmCustomerBankAcctGeneral",frmCustomerBankAcctGeneral);
			request.setAttribute("updated", "message.savedSuccessfully");
			CustomerBankDetailsVO customerBankDetailsVOO = new CustomerBankDetailsVO();
      		customerBankDetailsVOO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			return this.getForward(strBankacctdetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strAccountsError));
		}// end of catch(Exception exp)
	}// end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest
	// request,HttpServletResponse response)

	/**
	 * This method is used to navigate to previous screen when closed button is
	 * clicked. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doCloseFinance(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		try {

			checkWebboardVisabulity(request);

			//setLinks(request);
			log.info("Inside doClose method of CustomerBankAccGeneral");
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			TableData tableData = TTKCommon.getTableData(request);
			// frmCustomerBankAcctGeneral.set("flagValidate",null);
			// request.getSession().setAttribute("frmCustomerBankAcctGeneral",frmCustomerBankAcctGeneral);
			//fetch the data from the data access layer and set the data to table object
			ArrayList<Object> alSearchObjects =   (ArrayList<Object>)request.getSession().getAttribute("alSearchObjects");
		    String hosp_name =	frmCustomerBankAcctGeneral.getString("hospitalName");
          //  alSearchBoxParams.set(1,hosp_name);
			tableData.setSearchData(alSearchObjects);
            // this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
			tableData.modifySearchData("search");
			CustomerBankDetailsManager customerBankDetailsManager=this.getCustomerBankDetailsManagerObject();
			ArrayList alBankAccount=customerBankDetailsManager.getCustomerBankAccountList(tableData.getSearchData());
			tableData.setData(alBankAccount, "search");
			request.getSession().setAttribute("tableData",tableData);
			String strBankAccountList = "bankaccountlist";
			return this.getForward(strBankAccountList, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strAccountsError));
		}// end of catch(Exception exp)
	}// end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest
	// request,HttpServletResponse response)

	
	
	
	/**
	 * This method is used to navigate to previous screen when closed button is
	 * clicked. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doCloseHospReview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		try {
			
		//	checkWebboardVisabulity(request);
			this.setLinks(request,"HOSP");
			//setLinks(request);
			log.debug("Inside doCloseHospReview method of CustomerBankAccGeneral");
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			// frmCustomerBankAcctGeneral.set("flagValidate",null);
			// request.getSession().setAttribute("frmCustomerBankAcctGeneral",frmCustomerBankAcctGeneral);
			
			return this.getForward("validateAccounts", mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strAccountsError));
		}// end of catch(Exception exp)
	}// end of doCloseHospReview(ActionMapping mapping,ActionForm form,HttpServletRequest
	// request,HttpServletResponse response)
	
	
	/**
	 * Adds the selected item to the web board and makes it as the selected item in the web board
	 * @param customerBankDetailsVO CustomerBankDetailsVO object which contain the information of BankAccount
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 */
	private void addToWebBoard(CustomerBankDetailsVO customerBankDetailsVO,
			HttpServletRequest request) {
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		CacheObject cacheObject = new CacheObject();
		
		Long l = (Long) TTKCommon.checkNull(customerBankDetailsVO.getPolicyGroupSeqID());
		if (l.equals(null) || l.equals(" ")) {
			cacheObject.setCacheId("" + customerBankDetailsVO.getPolicySeqID());
			cacheObject.setCacheDesc(customerBankDetailsVO.getPolicyNumber());
			
		} else {
			cacheObject.setCacheId(""+customerBankDetailsVO.getPolicyGroupSeqID());
			cacheObject.setCacheDesc(customerBankDetailsVO.getPolicyNumber());
			
		}

		// cacheObject.setCacheDesc(customerBankDetailsVO.getInsureName());
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		alCacheObject.add(cacheObject);

		toolbar.getWebBoard().addToWebBoardList(alCacheObject);

		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
		
		request.getSession().setAttribute("toolbar", toolbar);
		// set weboardinvoked as true to avoid change of webboard id twice in
		// same request
		request.setAttribute("webboardinvoked", "true");
	}// end of addToWebBoard(BankAccountVO bankAccountVO, HttpServletRequest
	// request)

	private void setWebBoardId(HttpServletRequest request) {
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		
		toolbar.getWebBoard().setCurrentId("");
		toolbar.getWebBoard().addToWebBoardList(null);
		request.getSession().setAttribute("toolbar", toolbar);
	}// end of setWebBoardId(HttpServletRequest request)


	/**
	 * Populates the value object to form element.
	 * @param customerBankDetailsVO CustomerBankDetailsVO object.
	 * @param mapping The ActionMapping used to select this instance.
	 * @param request HttpServletRequest object which contains hospital information.
	 * @return DynaActionForm object.
	 */
	private void setFormValues(CustomerBankDetailsVO customerBankDetailsVO,
			ActionMapping mapping, HttpServletRequest request)
	throws TTKException {
		ArrayList<Object> alHospInfofinance = new ArrayList<Object>();
		try {
			
			// Store Hospital Bank Account Information in ArrayList, to check
			// whether any changes is made while updating
			alHospInfofinance.add(customerBankDetailsVO.getBankname());
			alHospInfofinance.add(customerBankDetailsVO.getBankAccno());
			alHospInfofinance.add(customerBankDetailsVO.getPolicyNumber());
			alHospInfofinance.add(customerBankDetailsVO.getBankBranch());
			alHospInfofinance.add(customerBankDetailsVO.getBankAccType());
			alHospInfofinance.add(customerBankDetailsVO.getMicr());
			alHospInfofinance.add(customerBankDetailsVO.getIfsc());
			alHospInfofinance.add(customerBankDetailsVO.getNeft());
			alHospInfofinance.add(customerBankDetailsVO.getBankcity());
			
			
			 request.getSession().setAttribute("alHospInfofinance",alHospInfofinance);

		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, "");
		}// end of catch
	}// end of setFormValues(HospitalDetailVO hospitalDetailVO,ActionMapping
	// mapping,HttpServletRequest request)

	/**
	 * This method is used to load cities based on the selected state. Finally
	 * it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	//doChangeBank
	//doChangeState
	
	
	
	public ActionForward doChangeBank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		try {
			//setLinks(request);
			checkWebboardVisabulity(request);
			log.debug("Inside doChangeState method of AccountsAction");
			
			DynaActionForm accountForm = (DynaActionForm) form;
			AccountDetailVO accountDetailVO = null;
			HashMap hmCityList = null;
			ArrayList alCityList = null;
			ArrayList alCityCode = null;
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			CustomerBankDetailsManager customerBankDetailsManager = this
			.getCustomerBankDetailsManagerObject();
			
			
			hmCityList = customerBankDetailsManager.getbankStateInfo();
			if (hmCityList != null) {
				alCityList = (ArrayList) hmCityList.get(TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankname")));
				
			}// end of if(hmCityList!=null)
			if (alCityList == null) {
				alCityList = new ArrayList();
			}// end of if(alCityList==null)
			frmCustomerBankAcctGeneral.set("frmChanged", "changed");
			frmCustomerBankAcctGeneral.set("alCityList", alCityList);
			
			//get the bank code based on bank code
			String bankCode	=		customerBankDetailsManager.getBankCode((String)TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankname")));
			request.getSession().setAttribute("alCityList", alCityList);
			frmCustomerBankAcctGeneral.set("neft", bankCode);
			frmCustomerBankAcctGeneral.set("ifsc", "");
			
			// frmCustomerBankAcctGeneral.set("alCityCode",alCityCode);
			return this.getForward(strBankacctdetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strAccountsError));
		}// end of catch(Exception exp)
	}// end of doChangeState(ActionMapping mapping,ActionForm
	// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to load cities based on the selected state. Finally
	 * it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping  The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	//doChangeState
	//doChangeDistict
	public ActionForward doChangeState(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			//setLinks(request);
			checkWebboardVisabulity(request);
			log.debug("Inside doChangeState method of AccountsAction");
			
			DynaActionForm accountForm = (DynaActionForm) form;
			AccountDetailVO accountDetailVO = null;
			HashMap hmDistList = null;
			ArrayList alDistList = null;
			ArrayList alCityCode = null;
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			String bankState = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankState"));
			String bankName = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankname"));
			
			CustomerBankDetailsManager customerBankDetailsManager = this.getCustomerBankDetailsManagerObject();
			
			hmDistList = customerBankDetailsManager.getBankCityInfo(bankState,bankName);
			if (hmDistList != null) {
				alDistList = (ArrayList) hmDistList.get(TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankState")));
				
			}// end of if(hmCityList!=null)
			if (alDistList == null) {
				alDistList = new ArrayList();
			}// end of if(alCityList==null)
			frmCustomerBankAcctGeneral.set("frmChanged", "changed");
			frmCustomerBankAcctGeneral.set("alDistList", alDistList);
			request.getSession().setAttribute("alDistList", alDistList);
			// frmCustomerBankAcctGeneral.set("alCityCode",alCityCode);
			return this.getForward(strBankacctdetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strAccountsError));
		}// end of catch(Exception exp)
	}// end of doChangeState(ActionMapping mapping,ActionForm
	// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to load cities based on the selected state. Finally
	 * it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping  The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	//doChangeCity
	//doChangeBranch
	public ActionForward doChangeCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		try {
			//setLinks(request);
			checkWebboardVisabulity(request);
			log.debug("Inside doChangeState method of AccountsAction");
			
			DynaActionForm accountForm = (DynaActionForm) form;
			AccountDetailVO accountDetailVO = null;
			HashMap hmBranchList = null;
			ArrayList alBranchList = null;
			// ArrayList alCityCode = null;
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			String bankState = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankState"));
			String bankName = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankname"));
			String bankDistict = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankcity"));
			CustomerBankDetailsManager customerBankDetailsManager = this.getCustomerBankDetailsManagerObject();
			hmBranchList = customerBankDetailsManager.getBankBranchtInfo(bankState,bankName, bankDistict);
			if (hmBranchList != null) {
				alBranchList = (ArrayList) hmBranchList.get(TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankcity")));
				
			}// end of if(hmCityList!=null)
			if (alBranchList == null) {
				alBranchList = new ArrayList();
			}// end of if(alCityList==null)
			frmCustomerBankAcctGeneral.set("frmChanged", "changed");
			frmCustomerBankAcctGeneral.set("alBranchList", alBranchList);
			request.getSession().setAttribute("alBranchList", alBranchList);
			// frmCustomerBankAcctGeneral.set("alCityCode",alCityCode);
			return this.getForward(strBankacctdetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strAccountsError));
		}// end of catch(Exception exp)
	}// end of doChangeState(ActionMapping mapping,ActionForm
	// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to load cities based on the selected state. Finally
	 * it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	//doChangeBranch
	//doChangeIfsc
	public ActionForward doChangeBranch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		try {
			//setLinks(request);
			checkWebboardVisabulity(request);
			log.debug("Inside doChangeState method of AccountsAction");
		
			DynaActionForm accountForm = (DynaActionForm) form;
			AccountDetailVO accountDetailVO = null;
			HashMap hmBranchList = null;
			ArrayList alBranchList = null;
			// ArrayList alCityCode = null;
			DynaActionForm frmCustomerBankAcctGeneral = (DynaActionForm) form;
			CustomerBankDetailsVO customerBankDetailsVO = null;
			customerBankDetailsVO = (CustomerBankDetailsVO) FormUtils
			.getFormValues(frmCustomerBankAcctGeneral, this, mapping, request);
			// customerBankDetailsVO=bankAcctObject.getPolicyBankAccountDetail(TTKCommon.getWebBoardId(request),TTKCommon.getUserSeqId(request));
			// TTKCommon.getWebBoardId(request),

			String bankState = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankState"));
			String bankName = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankname"));
			String bankDistict = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankcity"));
			String bankBranch = (String) TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankBranch"));
			
			CustomerBankDetailsManager customerBankDetailsManager = this.getCustomerBankDetailsManagerObject();
			
			
			customerBankDetailsVO = customerBankDetailsManager.getBankIfscInfo(bankState, bankName, bankDistict, bankBranch);
			
			if (hmBranchList != null) {
				alBranchList = (ArrayList) hmBranchList.get(TTKCommon.checkNull(frmCustomerBankAcctGeneral.get("bankBranch")));
				
			}// end of if(hmCityList!=null)
			if (alBranchList == null) {
				alBranchList = new ArrayList();
			}// end of if(alCityList==null)
			String micr = TTKCommon.checkNull(customerBankDetailsVO.getMicr());
			String ifsc = TTKCommon.checkNull(customerBankDetailsVO.getIfsc());
			
			request.getSession().setAttribute("micr", micr);
			request.getSession().setAttribute("ifsc", ifsc);
			frmCustomerBankAcctGeneral.set("frmChanged", "changed");
			frmCustomerBankAcctGeneral.set("micr", micr);
			frmCustomerBankAcctGeneral.set("ifsc", ifsc);
			return this.getForward(strBankacctdetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strAccountsError));
		}// end of catch(Exception exp)
	}// end of doChangeState(ActionMapping mapping,ActionForm
	// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Add the required account information to arrayList to check whether any changes are made
	 * @param accountForm DynaActionForm object to which contain the accounts information.
	 * @param request HttpServletRequest object which contains accounts information.
	 * @param mapping The ActionMapping used to select this instance
	 * @param request The HTTP request we are processing
	 * @return alAccountInfoNew ArrayList which contian accounts information.
	 */
	private ArrayList<Object> populateAccountInfo(DynaActionForm frmCustomerBankAcctGeneral, ActionMapping mapping,HttpServletRequest request) throws TTKException {
		ArrayList<Object> alAccountInfoNew = new ArrayList<Object>();
		AddressVO bankAddressVO = null;
		// ActionForm
		// bankAddressForm=(ActionForm)accountForm.get("bankAddressVO");
		try {
			// bankAddressVO=(AddressVO)FormUtils.getFormValues(bankAddressForm,"frmBankAddress",this,mapping,request);
			alAccountInfoNew.add((String) frmCustomerBankAcctGeneral.getString("bankname"));
			alAccountInfoNew.add((String) frmCustomerBankAcctGeneral.getString("bankAccno"));
			alAccountInfoNew.add((String) frmCustomerBankAcctGeneral.getString("policyNumber"));
			alAccountInfoNew.add((String) frmCustomerBankAcctGeneral.getString("bankBranch"));
			alAccountInfoNew.add((String) frmCustomerBankAcctGeneral.getString("bankAccType"));
			alAccountInfoNew.add((String) frmCustomerBankAcctGeneral.getString("micr"));
			alAccountInfoNew.add((String) frmCustomerBankAcctGeneral.getString("ifsc"));
			alAccountInfoNew.add((String) frmCustomerBankAcctGeneral.getString("neft"));
			alAccountInfoNew.add((String) frmCustomerBankAcctGeneral.getString("bankcity"));
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, "");
		}// end of catch
		return alAccountInfoNew;
	}// end of populateAccountInfo(HttpServletRequest request,DynaActionForm
	// accountForm)

	/**
	 * Populates the form element to value object .
	 * @param customerBankDetailsVO CustomerBankDetailsVO object.
	 * @param mapping The ActionMapping used to select this instance.
	 * @param request HttpServletRequest object which contains hospital information.
	 * @return hospDetailVO HospitalDetailVO object.
	 */
	private CustomerBankDetailsVO getFormValues(DynaActionForm frmCustomerBankAcctGeneral, ActionMapping mapping,HttpServletRequest request) throws TTKException {
		try {
			//setLinks(request);
			CustomerBankDetailsVO customerBankDetailsVO = null;

			customerBankDetailsVO = (CustomerBankDetailsVO) FormUtils
			.getFormValues(frmCustomerBankAcctGeneral, "frmCustomerBankAcctGeneral",
					this, mapping, request);
			customerBankDetailsVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// //User
			// Id
			return customerBankDetailsVO;
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, strAccountsError);
		}// end of catch
	}// end of getFormValues(DynaActionForm generalForm,ActionMapping
	// mapping,HttpServletRequest request)

	/**
	 * Returns the boolean value by comparing the two arraylist.
	 * @param alHospInfoNew ArrayList which contains account new information.
	 * @param alHospInfoOld ArrayList which contains the account old information.
	 * @return false when change is found when comparing the two ArrayList else return true
	 */
	private boolean isModified(ArrayList alHospInfoNew, ArrayList alHospInfoOld) {
		for (int i = 0; i < alHospInfoOld.size(); i++) {
			
			if (!TTKCommon.checkNull(alHospInfoNew.get(i)).equals(
					TTKCommon.checkNull(alHospInfoOld.get(i))))
				return false;

		}// end of for(int i=0;i<alHospInfoOld.size();i++)
		return true;
	}// end of isModified(ArrayList alHospInfoNew,ArrayList alHospInfoOld)

	/**
	 * Returns the CustomerBankDetailsManager session object for invoking methods on it.
	 * @return CustomerBankDetailsManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private CustomerBankDetailsManager getCustomerBankDetailsManagerObject()
	throws TTKException {
		CustomerBankDetailsManager customerBankDetailsManager = null;
		try {
			if (customerBankDetailsManager == null) {
				InitialContext ctx = new InitialContext();
				customerBankDetailsManager = (CustomerBankDetailsManager) ctx.lookup("java:global/TTKServices/business.ejb3/CustomerBankDetailsManagerBean!com.ttk.business.finance.CustomerBankDetailsManager");
			}// end if
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, strAccountsError);
		}// end of catch
		return customerBankDetailsManager;
	}// end getHospitalManagerObject()
	/**
	 * this method update webboard visabulity after request
	 * @exception throws TTKException
	 */
  private void checkWebboardVisabulity(HttpServletRequest request)throws TTKException
  {
	   TableData tableData = (TableData)request.getSession().getAttribute("tableData");
		HttpSession session=request.getSession();
		String strSwitchType="";
		DynaActionForm generalForm1=(DynaActionForm)session.getAttribute("frmCustomerBankDetailsSearch");
		strSwitchType=TTKCommon.checkNull((String)generalForm1.get("switchType"));
		if(strSwitchType.equals("HOSP"))
		{
		
		}
		else
		{
		if(((Column)((ArrayList)tableData.getTitle()).get(1)).isLink())
		{
			strSwitchType=strSwitchType.concat("Mem");
		}
		else if(((Column)((ArrayList)tableData.getTitle()).get(0)).isLink())
			{
				strSwitchType=strSwitchType.concat("Polc");
			}
		}//else
		this.setLinks(request,strSwitchType);
  }//if checkWebboardVisabulity
}
