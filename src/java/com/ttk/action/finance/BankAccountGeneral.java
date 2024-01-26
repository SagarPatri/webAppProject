/**
 * @ (#) BankAccountGeneral.java Jun 8, 2006
 * Project : TTK HealthCare Services
 * File : BankAccountGeneral.java
 * Author : Srikanth H M
 * Company : Span Systems Corporation
 * Date Created : Jun 8, 2006
 *
 * @author : Srikanth H M
 * Modified by :
 * Modified date :
 * Reason :
 */

package com.ttk.action.finance;

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
import com.ttk.business.finance.BankAccountManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.finance.BankAccountDetailVO;
import com.ttk.dto.finance.BankAccountVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for displaying the Bank Account Details.
 * This class also provides option for Updating the Bank Account Details.
 */
public class BankAccountGeneral  extends TTKAction {
	private static Logger log = Logger.getLogger( BankAccountGeneral.class );
	//  Exception Message Identifier
	private static final String strBankAcctDetails="BankAcctDetails";
	//  declare forward paths
	private static final String strBankacctdetail="bankacctdetail";
	private static final String strSelectBankPath="selectbank";
	
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
	public ActionForward doViewBankAccount(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										   HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside BankAccountGeneral doViewBankAccount");
			DynaActionForm frmBankAcctGeneral= (DynaActionForm)form;
			StringBuffer strCaption=new StringBuffer();
			String strEditMode = "";
			BankAccountDetailVO bankAccountDetailVO=null;
			BankAccountManager bankAcctObject=this.getBankAccountManagerObject();
			frmBankAcctGeneral.initialize(mapping);
			if(TTKCommon.getWebBoardId(request) == null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.accountno.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request) == null)
			strCaption.append("- Edit");
			strEditMode = "Edit";
			// calling business layer to get the bank account detail
			bankAccountDetailVO=bankAcctObject.getBankAccountDetail(TTKCommon.getWebBoardId(request),
																	TTKCommon.getUserSeqId(request));
			frmBankAcctGeneral= (DynaActionForm)FormUtils.setFormValues("frmBankAcctGeneral", bankAccountDetailVO, 
																		 this, mapping, request);
			frmBankAcctGeneral.set("status",bankAccountDetailVO.getStatusTypeID());
			frmBankAcctGeneral.set("editmode",strEditMode);
			frmBankAcctGeneral.set("caption",strCaption.toString());
			//keep the frmBankAcctGeneral in session scope
			request.getSession().setAttribute("frmBankAcctGeneral",frmBankAcctGeneral);
			return this.getForward(strBankacctdetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankAcctDetails));
		}//end of catch(Exception exp)
	}//end of doViewBankAccount(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																					//HttpServletResponse response)
	
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
		log.debug("Inside BankAccountGeneral doChangeWebBoard");
		return doViewBankAccount(mapping,form,request,response);
	}//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			setLinks(request);
			log.debug("Inside BankAccountGeneral doAdd");
			BankAccountDetailVO bankAccountDetailVO=null;
			DynaActionForm frmBankAcctGeneral= (DynaActionForm)form;
			StringBuffer strCaption=new StringBuffer();
			String strEditMode = "";
			String strStatus="ASA";
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
														  getAttribute("UserSecurityProfile")).getBranchID());
			bankAccountDetailVO=new BankAccountDetailVO();
			bankAccountDetailVO.setAccountType("Current Account");//account type will be always Current Account
			frmBankAcctGeneral= (DynaActionForm)FormUtils.setFormValues("frmBankAcctGeneral", bankAccountDetailVO, 
																		this, mapping, request);
			strCaption.append(" - Add");
			strEditMode = "Add";
			frmBankAcctGeneral.set("statusTypeID",strStatus);
			frmBankAcctGeneral.set("caption",strCaption.toString());
			frmBankAcctGeneral.set("editmode",strEditMode);
			frmBankAcctGeneral.set("officeSeqID", strDefaultBranchID);
			//keep the frmBankAcctGeneral in session scope
			request.getSession().setAttribute("frmBankAcctGeneral",frmBankAcctGeneral);
			return this.getForward(strBankacctdetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankAcctDetails));
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
			setLinks(request);
			log.debug("Inside BankAccountGeneral doSave");
			BankAccountDetailVO bankAccountDetailVO=null;
			DynaActionForm frmBankAcctGeneral= (DynaActionForm)form;
			BankAccountManager bankAcctObject=this.getBankAccountManagerObject();
			String strEditMode = "";
			StringBuffer strCaption=new StringBuffer();
			bankAccountDetailVO = (BankAccountDetailVO)FormUtils.getFormValues(frmBankAcctGeneral, 
								   this, mapping, request);
			bankAccountDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id
			if("".equals(TTKCommon.checkNull(request.getParameter("tdsPurposeYN"))))// if TDS Purpose checkbox is not selected
			{
				bankAccountDetailVO.setTdsPurposeYN("N");
			}//end of if("".equals(TTKCommon.checkNull(request.getParameter("tdsPurposeYN"))))
			else
			{
				bankAccountDetailVO.setTdsPurposeYN("Y");
			}//end of else if("".equals(TTKCommon.checkNull(request.getParameter("tdsPurposeYN"))))
			// calling business layer to save the bank account detail
			Long iUpdate=bankAcctObject.saveBankAccount(bankAccountDetailVO);
			// set the appropriate message
			if(iUpdate > 0)
			{
				if(frmBankAcctGeneral.get("accountSeqID")!=null && !frmBankAcctGeneral.get("accountSeqID").equals(""))
				{
					request.setAttribute("updated","message.savedSuccessfully");
					request.setAttribute("cacheId", ""+iUpdate);
					request.setAttribute("cacheDesc",bankAccountDetailVO.getAccountName());
					//finally modify the web board details, if the account no. is changed
					TTKCommon.modifyWebBoardId(request);
					
				}//end of if(frmBankAcctGeneral.get("accountSeqID")!=null && !frmBankAcctGeneral.get("accountSeqID")
																										//.equals(""))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
					bankAccountDetailVO.setAccountSeqID(iUpdate); 	
					this.addToWebBoard(bankAccountDetailVO,request);
				}//end of else  if(frmBankAcctGeneral.get("accountSeqID")!=null && !frmBankAcctGeneral
																					//.get("accountSeqID").equals(""))
				// clear the form object
				frmBankAcctGeneral.initialize(mapping);
				bankAccountDetailVO=bankAcctObject.getBankAccountDetail(TTKCommon.getWebBoardId(request),
																		TTKCommon.getUserSeqId(request));
				strEditMode = "Edit";
				strCaption.append("- Edit");
				frmBankAcctGeneral= (DynaActionForm)FormUtils.setFormValues("frmBankAcctGeneral", 
																	   bankAccountDetailVO, this, mapping, request);
				if(frmBankAcctGeneral.get("accountSeqID")==null || frmBankAcctGeneral.get("accountSeqID").equals(""))
				{
					frmBankAcctGeneral.set("accountSeqID",iUpdate.toString());
				}
				else
				{
					frmBankAcctGeneral.set("status",bankAccountDetailVO.getStatusTypeID());
				}
				frmBankAcctGeneral.set("caption",strCaption.toString());
				frmBankAcctGeneral.set("editmode",strEditMode);
				//keep the frmBankAcctGeneral in session scope
				request.getSession().setAttribute("frmBankAcctGeneral",frmBankAcctGeneral);
			}//end of if(iUpdate > 0)
			return this.getForward(strBankacctdetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankAcctDetails));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to reload the screen when the reset button is pressed.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
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
			setLinks(request);
			log.debug("Inside BankAccountGeneral doReset");
			DynaActionForm frmBankAcctGeneral= (DynaActionForm)form;
			StringBuffer strCaption=new StringBuffer();
			BankAccountDetailVO bankAccountDetailVO=null;
			String strEditMode = "";
			BankAccountManager bankAcctObject=this.getBankAccountManagerObject();
			String strStatus="ASA";
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
														  getAttribute("UserSecurityProfile")).getBranchID());
			if(frmBankAcctGeneral.get("accountSeqID")!=null && !frmBankAcctGeneral.get("accountSeqID").equals(""))
			{
				strCaption.append("- Edit");
				bankAccountDetailVO=bankAcctObject.getBankAccountDetail(TTKCommon.getWebBoardId(request),
																		TTKCommon.getUserSeqId(request));
				strEditMode = "Edit";
			}//end of if(frmBankAcctGeneral.get("accountSeqID")!=null && !frmBankAcctGeneral.get("accountSeqID")
																										//.equals(""))
			else
			{
				strEditMode = "Add";
				strCaption.append("- Add ");
				bankAccountDetailVO=new BankAccountDetailVO();
				bankAccountDetailVO.setAccountType("Current Account");
			}//end of else if(frmBankAcctGeneral.get("accountSeqID")!=null && !frmBankAcctGeneral.get("accountSeqID")
																										//.equals(""))
			frmBankAcctGeneral= (DynaActionForm)FormUtils.setFormValues("frmBankAcctGeneral", bankAccountDetailVO, 
																		this, mapping, request);
			if(frmBankAcctGeneral.get("accountSeqID")==null || frmBankAcctGeneral.get("accountSeqID").equals(""))
			{
				frmBankAcctGeneral.set("statusTypeID",strStatus);
			}
			else
			{
				frmBankAcctGeneral.set("status",bankAccountDetailVO.getStatusTypeID());
			}
			frmBankAcctGeneral.set("caption",strCaption.toString());
			frmBankAcctGeneral.set("editmode",strEditMode);
			frmBankAcctGeneral.set("officeSeqID", strDefaultBranchID);
			//keep the frmBankAcctGeneral in session scope
			request.getSession().setAttribute("frmBankAcctGeneral",frmBankAcctGeneral);
			return this.getForward(strBankacctdetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankAcctDetails));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doViewBank(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside BankAccountGeneral doViewBank");
			return mapping.findForward(strSelectBankPath);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankAcctDetails));
		}//end of catch(Exception exp)
	}//end of doViewBank(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Adds the selected item to the web board and makes it as the selected item in the web board
	 * @param bankAccountVO BankAccountVO object which contain the information of bank account
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 */
	private void addToWebBoard(BankAccountVO bankAccountVO, HttpServletRequest request)
	{
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		CacheObject cacheObject = new CacheObject();
		cacheObject.setCacheId(""+bankAccountVO.getAccountSeqID());
		cacheObject.setCacheDesc(bankAccountVO.getAccountName());
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		alCacheObject.add(cacheObject);
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
		//set weboardinvoked as true to avoid change of webboard id twice in same request
		request.setAttribute("webboardinvoked","true");
	}//end of addToWebBoard(BankAccountVO bankAccountVO, HttpServletRequest request)
	
	/**
	 * Returns the BankAccountManager session object for invoking methods on it.
	 * @return BankAccountManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private BankAccountManager getBankAccountManagerObject() throws TTKException
	{
		BankAccountManager bankAccountManager = null;
		try
		{
			if(bankAccountManager == null)
			{
				InitialContext ctx = new InitialContext();
				bankAccountManager = (BankAccountManager) ctx.lookup("java:global/TTKServices/business.ejb3/BankAccountManagerBean!com.ttk.business.finance.BankAccountManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strBankAcctDetails);
		}//end of catch
		return bankAccountManager;
	}//end of getPreAuthManagerObject()
}//end of BankAccountGeneral
