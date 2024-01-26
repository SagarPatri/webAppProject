package com.ttk.action.finance;

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
import org.apache.struts.validator.DynaValidatorForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.action.tree.TreeData;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.business.finance.BankAccountManager;
import com.ttk.business.finance.BankManager;
import com.ttk.business.finance.CustomerBankDetailsManager;
//import com.ttk.business.finance.CustomerBankDetailsManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.empanelment.AccountDetailVO;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.HospitalAuditVO;
import com.ttk.dto.enrollment.PolicyVO;
import com.ttk.dto.finance.BankAccountVO;
import com.ttk.dto.finance.BankAddressVO;
import com.ttk.dto.finance.BankDetailVO;
import com.ttk.dto.finance.BankVO;
import com.ttk.dto.finance.CustomerBankDetailsVO;
//import com.ttk.dto.finance.CustomerBankDetailsVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;
/**
 * This class is added for cr koc 1103
 * added eft
 */
public class CustomerBankDetailsAction extends TTKAction {
	private static final String strBanklist ="banklist";
	private static final String strBankDetails ="bankdetails";
	private static final String strBankDetails1 ="bankdetails1";
	private static final String strBankContacts="bankcontactlist";
	private static final String strBankDetail="bankdetail";
	private static final String strBankAccountList="bankaccountlist";
	private static final String strBankAccountDetail="bankaccountgeneral";
	private static final String strBankHospAccountDetail="hospbankaccountgeneral";
	private static final String strBankAccountDetailMember="bankaccountgeneralmember";
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	private  static final String strPolicy="POLC";
	private  static final String strClaim="CLAM";
	private  static final String strHospital="HOSP";
	//private static final String strEnrollment="ENM";
    //private static final String strEndorsement="END";
    private static final String strValidateAccounts="validateAccounts";
    private static final String strReviewValidateAccounts="reviewvalidateAccounts";
    private static final String strBankHospAccountReviewDetail="hospReviewDetails";  
    private static final String strBankHospRevisedAccountReviewDetail="hospReviewAccountReviewDetails";    
    private static final String strAccountsError="accounts";
    private static final String strBankPtnrAccountReviewDetail="ptnrReviewDetails";    
	private static Logger log = Logger.getLogger(CustomerBankDetailsAction.class );
	
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			   HttpServletResponse response) throws Exception{
    try{
    DynaActionForm frmCustomerBankDetailsSearch =(DynaActionForm)form;
    String strSwitchType=TTKCommon.checkNull((String)frmCustomerBankDetailsSearch.get("switchType"));
	if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    {
		frmCustomerBankDetailsSearch.initialize(mapping);//reset the form data
        //frmPolicyList.set("sTtkBranch",strDefaultBranchID);
		frmCustomerBankDetailsSearch.set("switchType",strPolicy);  //set switch mode to enrollment
        strSwitchType=strPolicy;
    }//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
	//log.debug("Inside BankAccountSearchAction doDefault");
	//this.setLinks(request,strSwitchType);

	checkWebboardVisabulity(request);
	frmCustomerBankDetailsSearch.set("caption",buildCaption(strSwitchType));
	TableData tableData = TTKCommon.getTableData(request);
	
	String strSubLink=TTKCommon.getActiveSubLink(request);
	StringBuffer strCaption=new StringBuffer();
	String strForwardList="";
	if(strSubLink.equals("Cust. Bank Details"))
	{
		strForwardList=strBanklist;
	}// end of if(strSubLink.equals("Bank Account"))
	

    /*String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
												  getAttribute("UserSecurityProfile")).getBranchID());*/
	/*DynaActionForm frmSearchBankAccount =(DynaActionForm)form;

    frmSearchBankAccount.initialize(mapping);//reset the form data
*/
    tableData = new TableData();
	tableData.createTableInfo("CustomerBankPolicyTable",new ArrayList());
	
	
	request.getSession().setAttribute("tableData",tableData);
	//frmSearchBankAccount.set("sTtkBranch",strDefaultBranchID);
	return this.getForward(strForwardList, mapping, request);
    }//end of try
       catch(TTKException expTTK)
        {
        return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
       catch(Exception exp)
       {
         return this.processExceptions(request, mapping, new TTKException(exp, strBankDetail));
       }//end of catch(Exception exp)
     }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	
	
	/**
	 * This method is used to search the data with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
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
			
			//setLinks(request);
			log.debug("Inside CustomerBankDetailsAction doSearch");
			TableData tableData = TTKCommon.getTableData(request);
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardList="";
			DynaActionForm frmCustomerBankDetailsSearch =(DynaActionForm)form;
			//BankAccountManager bankAccountObject=this.getBankAccountManagerObject();
			String strSwitchType=TTKCommon.checkNull((String)frmCustomerBankDetailsSearch.get("switchType"));
			 //this.setLinks(request,strSwitchType);
			CustomerBankDetailsManager customerBankDetailsManager=this.getCustomerBankDetailsManagerObject();
			if(strSubLink.equals("Cust. Bank Details"))
			{
				strForwardList=strBankAccountList;
			}// end of if(strSubLink.equals("Bank Account"))
			
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			
			
			
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strForwardList));
				}///end of if(!strPageID.equals(""))
				else
				{
					
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{   
				// create the required grid table
			if(strSwitchType.equals(strClaim))
			{
				tableData.createTableInfo("CustomerBankClaimTable",null);
			}
			else if(strSwitchType.equals(strPolicy))
			{
				tableData.createTableInfo("CustomerBankPolicyTable",null);
			}
			else if(strSwitchType.equals(strHospital))
			{
				tableData.createTableInfo("CustomerBankHospitalTable",null);
			}
				
				
				//fetch the data from the data access layer and set the data to table object
				tableData.setSearchData(this.populateSearchCriteria(frmCustomerBankDetailsSearch,request));
				
				// this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
				tableData.modifySearchData("search");
				
				
			}//end of else
					
			ArrayList alBankAccount=customerBankDetailsManager.getCustomerBankAccountList(tableData.getSearchData());
			
			CustomerBankDetailsVO customerBankDetailsVO=new CustomerBankDetailsVO();
			try
			{
			customerBankDetailsVO=(CustomerBankDetailsVO)alBankAccount.get(0);
			}
			catch(IndexOutOfBoundsException iobe)
			{
			log.info("in IndexOutOfBoundsException");	
			}
			String strPolciType=frmCustomerBankDetailsSearch.getString("sPolicyType");
			String strclmSetelNmbr=frmCustomerBankDetailsSearch.getString("sClaimSettmentNumber");
			String strclainno=frmCustomerBankDetailsSearch.getString("sClaimNumber");
			if(strPolciType.equalsIgnoreCase("IND") || strPolciType.equalsIgnoreCase("ING") || strPolciType.equalsIgnoreCase("NCR"))
			{
			((Column)((ArrayList)tableData.getTitle()).get(0)).setIsLink(false);//setVisibility(false);
			((Column)((ArrayList)tableData.getTitle()).get(5)).setVisibility(false);
			}
		   // }//strSwitchType.equals(strHospital
						           
			
			if(strPolciType.equalsIgnoreCase("COR"))
			{
			String strCheckIssuedTo=TTKCommon.checkNull(customerBankDetailsVO.getCheckIssuedTo());
			if(strCheckIssuedTo.equals("Corporate"))//IQC
			{
				((Column)((ArrayList)tableData.getTitle()).get(1)).setIsLink(false);//setVisibility(false);
			}
			else if(strCheckIssuedTo.equals("Individual"))//IQI
			{
				((Column)((ArrayList)tableData.getTitle()).get(0)).setIsLink(false);//setVisibility(false);
			}
		  }//end if cor
			String cliamPolType=TTKCommon.checkNull(customerBankDetailsVO.getEnrType());
			if(!strclainno.equals("") ||!strclmSetelNmbr.equals("") )
			{
				if(cliamPolType.equalsIgnoreCase("COR"))
				{
				String strCheckIssuedTo=TTKCommon.checkNull(customerBankDetailsVO.getCheckIssuedTo());
				if(strCheckIssuedTo.equals("Corporate"))//IQC
				{
					((Column)((ArrayList)tableData.getTitle()).get(1)).setIsLink(false);//setVisibility(false);
				}
				else if(strCheckIssuedTo.equals("Individual"))//IQI
				{
					((Column)((ArrayList)tableData.getTitle()).get(0)).setIsLink(false);//setVisibility(false);
				}
			  }//end if cor
			   if(cliamPolType.equalsIgnoreCase("IND") || cliamPolType.equalsIgnoreCase("ING") || cliamPolType.equalsIgnoreCase("NCR"))
				{
				((Column)((ArrayList)tableData.getTitle()).get(0)).setIsLink(false);//setVisibility(false);
				((Column)((ArrayList)tableData.getTitle()).get(7)).setVisibility(false);
				}
			  // request.getSession().setAttribute("claimsettelnmr",customerBankDetailsVO.getClmSetmentno());
				//request.getSession().setAttribute("claimnmr",customerBankDetailsVO.getClaimNo());
			}
			tableData.setData(alBankAccount, "search");
			
			
			request.getSession().setAttribute("tableData",tableData);
			checkWebboardVisabulity(request);
			//finally return to the grid screen
			
			return this.getForward(strForwardList, mapping, request);
			
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, "BankAccountSearch"));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
			
			//setLinks(request);
			log.debug("Inside CustomerBankDetailsAction doViewBankAccount");
			DynaValidatorForm frmCustomerBankDetailsSearch = (DynaValidatorForm)form;
			String strSwitchType=TTKCommon.checkNull((String)frmCustomerBankDetailsSearch.get("switchType"));
			strSwitchType=strSwitchType.concat("Polc");
			this.setLinks(request,strSwitchType);
			BankAccountVO bankAccountVO=null;
			CustomerBankDetailsVO customerBankDetailsVO=null;
			TableData tableData = TTKCommon.getTableData(request);
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardDetails="";
			if(strSubLink.equals("Cust. Bank Details"))
			{
				strForwardDetails=strBankAccountDetail;
			}// end of if(strSubLink.equals("Cust. Bank Details"))
			
			customerBankDetailsVO =new CustomerBankDetailsVO();
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				customerBankDetailsVO = (CustomerBankDetailsVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				//add the selected item to the web board and make it as default selected
				
				if(strSubLink.equals("Cust. Bank Details"))
				{
					//this.addToWebBoard(bankAccountVO, request);
					this.addToWebBoard(customerBankDetailsVO, request);
				}
				
			}//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward(strForwardDetails);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, "BankAccountSearch"));
		}//end of catch(Exception exp)
	}//end of doViewBankAccount(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse//response)
	
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
	public ActionForward doViewHospBankAccount(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										   HttpServletResponse response) throws Exception{
		try{
			
			//setLinks(request);
			log.debug("Inside CustomerBankDetailsAction doViewBankAccount");
			DynaValidatorForm frmCustomerBankDetailsSearch = (DynaValidatorForm)form;
			BankAccountVO bankAccountVO=null;
			CustomerBankDetailsVO customerBankDetailsVO=null;
			TableData tableData = TTKCommon.getTableData(request);
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strSwitchType=TTKCommon.checkNull((String)frmCustomerBankDetailsSearch.get("switchType"));
			this.setLinks(request,strSwitchType);
			String strForwardDetails="";
			if(strSubLink.equals("Cust. Bank Details"))
			{
				strForwardDetails=strBankHospAccountDetail;
			}// end of if(strSubLink.equals("Bank Account"))
			
			customerBankDetailsVO =new CustomerBankDetailsVO();
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				customerBankDetailsVO = (CustomerBankDetailsVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				
				if(strSubLink.equals("Cust. Bank Details"))
				{
					//this.addToWebBoard(bankAccountVO, request);
					this.addToWebBoard(customerBankDetailsVO, request);
				}
				
			}//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward(strForwardDetails);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, "BankAccountSearch"));
		}//end of catch(Exception exp)
	}//end of doViewBankAccount(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse//response)
	
	
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
			checkWebboardVisabulity(request);
			log.debug("Inside CustomerBankDetailsAction doForward");
			TableData tableData = TTKCommon.getTableData(request);
			//BankAccountManager bankAccountObject=this.getBankAccountManagerObject();
			CustomerBankDetailsManager customerBankDetailsManager=this.getCustomerBankDetailsManagerObject();
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardList="";
			if(strSubLink.equals("Cust. Bank Details"))
			{
				strForwardList=strBankAccountList;
			}// end of if(strSubLink.equals("Bank Account"))
			
			tableData.modifySearchData(strForward);//modify the search data
			//fetch the data from the data access layer and set the data to table object

           
			//ArrayList alBankAccount = bankAccountObject.getCustomerBankAccountList(tableData.getSearchData());
		    ArrayList alBankAccount =customerBankDetailsManager.getCustomerBankAccountList(tableData.getSearchData());
			tableData.setData(alBankAccount, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			//TTKCommon.documentViewer(request);
			return this.getForward(strForwardList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, "BankAccountSearch"));
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
									HttpServletResponse response) throws Exception{
		try{
			checkWebboardVisabulity(request);
			log.debug("Inside BankAccountSearchAction doBackward");
			TableData tableData = TTKCommon.getTableData(request);
			//BankAccountManager bankAccountObject=this.getBankAccountManagerObject();
			CustomerBankDetailsManager customerBankDetailsManager=this.getCustomerBankDetailsManagerObject();
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardList="";
			if(strSubLink.equals("Cust. Bank Details"))
			{
				strForwardList=strBankAccountList;
			}// end of if(strSubLink.equals("Bank Account"))
			
		   tableData.modifySearchData(strBackward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
          
			ArrayList alBankAccount =customerBankDetailsManager.getCustomerBankAccountList(tableData.getSearchData());
			tableData.setData(alBankAccount, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			//TTKCommon.documentViewer(request);
			return this.getForward(strForwardList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, "BankAccountSearch"));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
     * This method is used to Switch between Enrollment flow and Endorsement flow
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSwitchTo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.info("Inside the doSwitchTo method of finance action");
            DynaActionForm frmCustomerBankDetailsSearch=(DynaActionForm)form;  //get the DynaActionForm instance
            TableData tableData = TTKCommon.getTableData(request);
            String strSwitchType=TTKCommon.checkNull((String)frmCustomerBankDetailsSearch.get("switchType"));
           
            this.setLinks(request,strSwitchType);
             tableData = new TableData();
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            //initialize the formbean when user switches the mode
            frmCustomerBankDetailsSearch.initialize(mapping);
            //frmPolicyList.set("sTtkBranch",strDefaultBranchID);
            frmCustomerBankDetailsSearch.set("switchType",strSwitchType);
            request.getSession().setAttribute("switchType",strSwitchType);

           
            //load the appropriate table and set the column visibality
            if(strSwitchType.equals(strClaim))
            {
            	tableData.createTableInfo("CustomerBankClaimTable",null);
            }//end of if(strSwitchType.equals(strClaim))
            else if(strSwitchType.equals(strPolicy))
            {
            	tableData.createTableInfo("CustomerBankPolicyTable",null);
            }//end of else if(strSwitchType.equals(strPolicy))
            else if(strSwitchType.equals(strHospital))
            {
            	tableData.createTableInfo("CustomerBankHospitalTable",null);
            }
           

            //build the caption
            frmCustomerBankDetailsSearch.set("caption",buildCaption(strSwitchType));
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward(strBankAccountList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"policyList"));
        }//end of catch(Exception exp)
    }//end of doSwitchTo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method navigates the user to Member screen
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doMemberAccount(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
        	
			//setLinks(request);
			log.debug("Inside CustomerBankDetailsAction doViewBankAccount");
			DynaValidatorForm frmBankAccountDetail = (DynaValidatorForm)form;
			DynaValidatorForm frmCustomerBankDetailsSearch = (DynaValidatorForm)form;
			String strSwitchType=TTKCommon.checkNull((String)frmCustomerBankDetailsSearch.get("switchType"));
			strSwitchType=strSwitchType.concat("Mem");
			this.setLinks(request,strSwitchType);
			request.setAttribute("switchTypeFinance",strSwitchType);
			BankAccountVO bankAccountVO=null;
			CustomerBankDetailsVO customerBankDetailsVO=null;
			TableData tableData = TTKCommon.getTableData(request);
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardDetails=strBankAccountDetailMember;;
			
			customerBankDetailsVO =new CustomerBankDetailsVO();
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				customerBankDetailsVO = (CustomerBankDetailsVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				
				if(strSubLink.equals("Cust. Bank Details"))
				{
					String s="";
					this.addToWebBoard(customerBankDetailsVO, request,s);
				}
			
			}//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward(strForwardDetails);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"policyList"));
        }//end of catch(Exception exp)
    }//end of doViewMembers(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
	 * This method is used to copy the selected records to web-board.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										  HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside BankAccountSearchAction doCopyToWebBoard");
			DynaActionForm frmCustomerBankDetailsSearch= (DynaActionForm) form;
			TableData tableData = (TableData)request.getSession().getAttribute("tableData");
			String strSwitchType=TTKCommon.checkNull((String)frmCustomerBankDetailsSearch.get("switchType"));
			if(strSwitchType.equals("HOSP"))
			{
				this.populateHospitalWebBoard(request);
			}
    		else
				{
    			/*if(((Column)((ArrayList)tableData.getTitle()).get(1)).isLink())
                {            	
                    strSwitchType=strSwitchType.concat("Mem");         		
                }
    			 else if(((Column)((ArrayList)tableData.getTitle()).get(0)).isLink())
    	            {
    	            strSwitchType=strSwitchType.concat("Polc");
    	    		}*/
				this.populateWebBoard(request);
				
				}
			//this.setLinks(request,strSwitchType);
			return this.getForward(strBankAccountList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, "BankAccountSearch"));
		}//end of catch(Exception exp)
	}//end of doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse
																											//response)
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
    public ActionForward doChangePolicyType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    															HttpServletResponse response) throws Exception{
    	try{
    		 
    		log.debug("Inside CallCenterSearchAction doChangeLogType");
    		String strPolicyType="";
    		DynaActionForm frmCustomerBankDetailsSearch= (DynaActionForm) form;
    		String strSwitchType=TTKCommon.checkNull((String)frmCustomerBankDetailsSearch.get("switchType"));
            this.setLinks(request,strSwitchType);
    		String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession()
                    .getAttribute("UserSecurityProfile")).getBranchID());           
    		strPolicyType = (String)frmCustomerBankDetailsSearch.get("sPolicyType");
    		((DynaActionForm)form).initialize(mapping);		//reset the form data
    		TableData tableData =TTKCommon.getTableData(request);
            //create new table data object
            tableData = new TableData();
            //create the required grid table
            tableData.createTableInfo("CustomerBankPolicyTable",new ArrayList());
            request.getSession().setAttribute("tableData",tableData);
            //String strSwitchType=TTKCommon.checkNull((String)frmCustomerBankDetailsSearch.get("switchType"));
            frmCustomerBankDetailsSearch.set("switchType",strPolicy);
            frmCustomerBankDetailsSearch.set("sPolicyType",strPolicyType);  
    		return this.getForward(strBankAccountList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,"BankAccountSearch"));
    	}//end of catch(Exception exp)
    }//end of doChangePolicyType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    	//HttpServletResponse response)
	
	
	/**
	 * Check the ACL permission and set the display property of icons.
	 * @param request The HTTP request we are processing
	 * @param treeData TreeData for which permission has to check
	 */
	private void checkTreePermission(HttpServletRequest request,TreeData treeData) throws TTKException
	{
		// check the permision and set the tree for not to display respective icon
		if(TTKCommon.isAuthorized(request,"Add")==false)
		{
			treeData.getTreeSetting().getRootNodeSetting().setIconVisible(0,false);
		}//end of if(TTKCommon.isAuthorized(request,"Add")==false)
		if(TTKCommon.isAuthorized(request,"Edit")==false)
		{
			treeData.getTreeSetting().getRootNodeSetting().setIconVisible(1,false);
			treeData.getTreeSetting().getChildNodeSetting().setIconVisible(0,false);
		}//end of if(TTKCommon.isAuthorized(request,"Edit")==false)
		if(TTKCommon.isAuthorized(request,"Delete")==false)
		{
			treeData.getTreeSetting().getChildNodeSetting().setIconVisible(2,false);
		}// end of if(TTKCommon.isAuthorized(request,"Delete")==false)
	}//end of checkTreePermission(HttpServletRequest request,TreeData treeData)

	/**
	 * Returns the ArrayList which contains the populated search criteria elements
	 * @param frmSearchBankAccount DynaActionForm will contains the values of corresponding fields
	 * @param request HttpServletRequest object which contains the search parameter that is built
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmCustomerBankDetailsSearch,HttpServletRequest request)
	throws TTKException
     {
           //build the column names along with their values to be searched
          ArrayList<Object> alSearchObjects = new ArrayList<Object>();
            ArrayList<Object> alSearchBoxParams = new ArrayList<Object>();
            String switchType=(String)frmCustomerBankDetailsSearch.get("switchType");
           
            if(switchType.equals(strClaim))
            {            
            	alSearchObjects.add("CLAM");
            	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCustomerBankDetailsSearch.get("sClaimNumber")));
                alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCustomerBankDetailsSearch.get("sClaimSettmentNumber")));
                alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCustomerBankDetailsSearch.get("sClaimentName")));
                alSearchBoxParams.add((String)frmCustomerBankDetailsSearch.get("sTtkBranch"));
                alSearchBoxParams.add(null);//FOR BINDING
                alSearchObjects.add("");
                
            }
            else if(switchType.equals(strPolicy))
            {
            	alSearchObjects.add("POLC");
            
          //alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchBankAccount.get("sAccountNumber")));
            alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCustomerBankDetailsSearch.get("sPolicyNumber")));
            alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCustomerBankDetailsSearch.get("sEnrollNumber")));
            alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCustomerBankDetailsSearch.get("sInsuredName")));
            alSearchBoxParams.add((String)frmCustomerBankDetailsSearch.get("sTtkBranch"));
            alSearchBoxParams.add((String)frmCustomerBankDetailsSearch.get("sGroupId"));
            alSearchObjects.add(TTKCommon.replaceSingleQots((String)frmCustomerBankDetailsSearch.get("sPolicyType")));
            
            }
            else if(switchType.equals(strHospital))
            {
            	alSearchObjects.add(TTKCommon.replaceSingleQots((String)frmCustomerBankDetailsSearch.get("switchType")));
            	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCustomerBankDetailsSearch.get("sEmpanelmentNo")));
                alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCustomerBankDetailsSearch.get("sHospitalName")));
                alSearchBoxParams.add((String)frmCustomerBankDetailsSearch.get("sTtkBranch"));
               
                alSearchBoxParams.add(null);
                alSearchBoxParams.add(null);
                alSearchObjects.add("");
                
               
            }
                               
           
          //  alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)(TTKCommon.getUserSeqId(request))));
            String[] strSearchObjects = alSearchBoxParams.toArray(new String[0]);
           
            alSearchObjects.add(strSearchObjects);
            request.getSession().setAttribute("alSearchObjects",alSearchObjects);
         // alSearchObjects.add(alSearchBoxParams);
     return alSearchObjects;
}//end of populateSearchCriteria(DynaActionForm frmSearchBankAccount)
	
	/**
	 * @param bankAccountVO BankAccountVO object which contain the information of BankAccount
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 */
	private void addToWebBoard(CustomerBankDetailsVO customerBankDetailsVO, HttpServletRequest request)
	{
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		CacheObject cacheObject = new CacheObject();
		
		if(!TTKCommon.checkNull(customerBankDetailsVO.getPolicySeqID()).equals(""))
		{
		cacheObject.setCacheId(""+customerBankDetailsVO.getPolicySeqID());
		cacheObject.setCacheDesc(customerBankDetailsVO.getPolicyNumber());
		}
		else
		{
			cacheObject.setCacheId(""+customerBankDetailsVO.getHospitalSeqId());
			cacheObject.setCacheDesc(customerBankDetailsVO.getHospitalEmnalNumber());
		}
		
		//cacheObject.setCacheDesc(customerBankDetailsVO.getInsureName());
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		alCacheObject.add(cacheObject);
		//if the object(s) are added to the web board, set the current web board id
		//if(toolbar.getWebBoard().addToWebBoardList(alCacheObject))
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		
		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
		
		
		//request.getSession().setAttribute("toolbar",toolbar);
		//set weboardinvoked as true to avoid change of webboard id twice in same request
		request.setAttribute("webboardinvoked","true");
	}//end of addToWebBoard(BankAccountVO bankAccountVO, HttpServletRequest request)
	
	/**
	 * @param bankAccountVO BankAccountVO object which contain the information of BankAccount
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 */
	private void addToWebBoard(CustomerBankDetailsVO customerBankDetailsVO, HttpServletRequest request,String s)
	{
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		CacheObject cacheObject = new CacheObject();
		
		cacheObject.setCacheId(""+customerBankDetailsVO.getPolicyGroupSeqID());
		cacheObject.setCacheDesc(customerBankDetailsVO.getEnrollNmbr());
		
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		alCacheObject.add(cacheObject);
		//if the object(s) are added to the web board, set the current web board id
		//if(toolbar.getWebBoard().addToWebBoardList(alCacheObject))
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		
		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
				
		//request.getSession().setAttribute("toolbar",toolbar);
		//set weboardinvoked as true to avoid change of webboard id twice in same request
		request.setAttribute("webboardinvoked","true");
	}//end of addToWebBoard(BankAccountVO bankAccountVO, HttpServletRequest request)
	

	/**
	 * Populates the web board in the session with the selected items in the grid
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 */
	private void populateWebBoard(HttpServletRequest request)throws TTKException
	{
		String[] strChk = request.getParameterValues("chkopt");
		TableData tableData = (TableData)request.getSession().getAttribute("tableData");
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		CacheObject cacheObject = null;
		HttpSession session=request.getSession();
		String strSwitchType="";
		DynaActionForm generalForm1=(DynaActionForm)session.getAttribute("frmCustomerBankDetailsSearch");
		strSwitchType=TTKCommon.checkNull((String)generalForm1.get("switchType"));
		//TableData tableData = TTKCommon.getTableData(request);
		if(((Column)((ArrayList)tableData.getTitle()).get(1)).isLink())
		{
		if(strSwitchType.equals("POLC") || strSwitchType.equals("CLAM"))
		{
		
		if(strChk!=null&&strChk.length!=0)
		{
		//loop through to populate delete sequence id's and get the value from session for the matching check box value
			for(int i=0; i<strChk.length;i++)
			{
				cacheObject = new CacheObject();
				String cacheid=((CustomerBankDetailsVO) tableData.getData().get(Integer.parseInt(strChk[i]))).getPolicyGroupSeqID().toString();
				//cacheObject.setCacheId(""+((CustomerBankDetailsVO)tableData.getData().get(Integer.parseInt(strChk[i]))).getPolicyGroupSeqID());
				cacheObject.setCacheId(cacheid);
                cacheObject.setCacheDesc(((CustomerBankDetailsVO)tableData.getData().get(Integer.parseInt(strChk[i]))).getEnrollNmbr());
				alCacheObject.add(cacheObject);
			}//end of for(int i=0; i<strChk.length;i++)
			if(cacheObject.getCacheId()!="")
			{
				strSwitchType=strSwitchType.concat("Mem");
				this.setLinks(request,strSwitchType);
				if(toolbar != null)
				{
					toolbar.getWebBoard().addToWebBoardList(alCacheObject);
				}//end of if(toolbar != null)
			}
			
		}//end of if(strChk!=null&&strChk.length!=0)
		}//if(strSwitchType.equals("POLC") || strSwitchType.equals("CLAM"))
		}//if column
		else if(((Column)((ArrayList)tableData.getTitle()).get(0)).isLink())
		{
			if(strSwitchType.equals("POLC") || strSwitchType.equals("CLAM"))
			{
			
			if(strChk!=null&&strChk.length!=0)
			{
			//loop through to populate delete sequence id's and get the value from session for the matching check box value
				for(int i=0; i<strChk.length;i++)
				{
					cacheObject = new CacheObject();
					String cacheid=((CustomerBankDetailsVO) tableData.getData().get(Integer.parseInt(strChk[i]))).getPolicySeqID().toString();
					//cacheObject.setCacheId(""+((CustomerBankDetailsVO)tableData.getData().get(Integer.parseInt(strChk[i]))).getPolicyGroupSeqID());
					cacheObject.setCacheId(cacheid);
	                cacheObject.setCacheDesc(((CustomerBankDetailsVO)tableData.getData().get(Integer.parseInt(strChk[i]))).getPolicyNumber());
					alCacheObject.add(cacheObject);
				}//end of for(int i=0; i<strChk.length;i++)
				if(cacheObject.getCacheId()!="")
				{
					strSwitchType=strSwitchType.concat("Polc");
					this.setLinks(request,strSwitchType);
					if(toolbar != null)
					{
						toolbar.getWebBoard().addToWebBoardList(alCacheObject);
					}//end of if(toolbar != null)
				}
				
			}//end of if(strChk!=null&&strChk.length!=0)
			}//if(strSwitchType.equals("POLC") || strSwitchType.equals("CLAM"))
			}//if else
		
	}//end of populateWebBoard(HttpServletRequest request)
	
	/**
     * This method is prepares the Caption based on the flow and retunrs it
     *
     * @param strSwitchType String Identfier for Enrollment/Endorsement flow
     * @param strActiveSubLink String current sublink
     * @return String prepared Caption
     */
    private String buildCaption(String strSwitchType)
    {
        StringBuffer sbfCaption=new StringBuffer();
        if(strSwitchType.equals(strPolicy))
        {
        	sbfCaption.append("List of Bank Accounts in Scheme Level");
        }//end of if(strSwitchType.equals(strEnrollment))
        else if(strSwitchType.equals(strClaim))
        {
        	sbfCaption.append("List of Bank Accounts in Claim  Level");
        }
        else if(strSwitchType.equals(strHospital))
        {
        	sbfCaption.append("List of Bank Accounts For Hospital");
        }
        return sbfCaption.toString();
    }//end of  buildCaption(String strSwitchType, String strActiveSubLink)
    
    /**
     * This method populates the web board in the session with the selected items from the grid
     * @param request HttpServletRequest object which contains information about the selected check boxes
     * @param strSwitchType String  identifier of enrollment or endorsement flow
     * @throws TTKException If any run time Excepton occures
     */
    private void populateHospitalWebBoard(HttpServletRequest request)throws TTKException
    {
        String[] strChk = request.getParameterValues("chkopt");
        TableData tableData = (TableData)request.getSession().getAttribute("tableData");
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        HttpSession session=request.getSession();
		String strSwitchType="";
		DynaActionForm generalForm1=(DynaActionForm)session.getAttribute("frmCustomerBankDetailsSearch");
		strSwitchType=TTKCommon.checkNull((String)generalForm1.get("switchType"));
		this.setLinks(request,strSwitchType);
        ArrayList<Object> alCacheObject = new ArrayList<Object>();
        CacheObject cacheObject = null;
        PolicyVO policyVO=null;

        if(strChk!=null&&strChk.length!=0)
        {
            for(int i=0; i<strChk.length;i++)
            {
            	cacheObject = new CacheObject();
				cacheObject.setCacheId(""+((CustomerBankDetailsVO)tableData.getData().get(Integer.parseInt(strChk[i]))).getHospitalSeqId());
                cacheObject.setCacheDesc(((CustomerBankDetailsVO)tableData.getData().get(Integer.parseInt(strChk[i]))).getHospitalEmnalNumber());
                alCacheObject.add(cacheObject);
            }//end of for(int i=0; i<strChk.length;i++)
        }//end of if(strChk!=null&&strChk.length!=0)
        if(toolbar != null)
            toolbar.getWebBoard().addToWebBoardList(alCacheObject);
    }//end of populateWebBoard(HttpServletRequest request,String strSwitchType)

	/**
	 * Returns the HospitalManager session object for invoking methods on it.
	 * @return HospitalManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private CustomerBankDetailsManager getCustomerBankDetailsManagerObject() throws TTKException
	{
		CustomerBankDetailsManager customerBankDetailsManager = null;
		try
		{
			if(customerBankDetailsManager == null)
			{
				InitialContext ctx = new InitialContext();
				customerBankDetailsManager = (CustomerBankDetailsManager) ctx.lookup("java:global/TTKServices/business.ejb3/CustomerBankDetailsManagerBean!com.ttk.business.finance.CustomerBankDetailsManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strAccountsError);
		}//end of catch
		return customerBankDetailsManager;
	}//end getHospitalManagerObject()
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
  
  
  //intX
  //To Review the hospitals waiting from empanelment in finance section.
  
	  public ActionForward doViewHospReviewList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		          HttpServletResponse response) throws Exception{
		  try
		  {
			  String flag ="";
			  //setLinks(request);
			  log.debug("Inside CustomerBankDetailsAction doViewHospReviewList");
				
				DynaActionForm frmCustomerBankAccountList = (DynaActionForm) request.getSession().getAttribute("frmCustomerBankAccountList");
				if("PTR".equals(frmCustomerBankAccountList.get("switchHospOrPtr")))
					return this.doViewPtnrReviewList(mapping, form, request, response);
			  flag = (String)request.getParameter("Flag");
			  TableData tableData = TTKCommon.getTableData(request);
			  String strSubLink=TTKCommon.getActiveSubLink(request);
			  String strForwardList="";
			  DynaActionForm frmCustomerBankDetailsSearch =(DynaActionForm)form;
			  
			  //BankAccountManager bankAccountObject=this.getBankAccountManagerObject();
			  this.setLinks(request,"HOSP");
			  CustomerBankDetailsManager customerBankDetailsManager=this.getCustomerBankDetailsManagerObject();
			  String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
              String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			  if(!strPageID.equals("") || !strSortID.equals(""))
			  {
				  if(!strPageID.equals(""))
				  {
					  tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					  if(flag==null)
					  {
					  return (mapping.findForward(strValidateAccounts));
					  }
					  else{
						  return (mapping.findForward(strReviewValidateAccounts));
					  }
				  }///end of if(!strPageID.equals(""))
				  else
				  {
					  tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					  tableData.modifySearchData("sort");//modify the search data
				  }//end of else
			  }//end of if(!strPageID.equals("") || !strSortID.equals(""))
			
			  else
			  {   
				  // create the required grid table
				  tableData.createTableInfo("HospReviewTable",null);
				  //fetch the data from the data access layer and set the data to table object
				  // this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
				  tableData.modifySearchData("search");
			  }//end of else
			  ArrayList alBankAccount=null;
			  if(flag==null)
			  { 
				  alBankAccount=customerBankDetailsManager.getCustomerBankAccountReviewList();	
			  }
			  else if(flag.length()!=0 && flag.trim().equals("Rnw"))
			  {
				  alBankAccount=customerBankDetailsManager.getCustomerBankAccountReviewList(flag);
			  }
			  CustomerBankDetailsVO customerBankDetailsVO=new CustomerBankDetailsVO();
			  try
			  {
				  customerBankDetailsVO=(CustomerBankDetailsVO)alBankAccount.get(0);
			  }
			  catch(IndexOutOfBoundsException iobe)
			  {
				  log.info("in IndexOutOfBoundsException");	
			  }
               // Web board not using for intX checkWebboardVisabulity(request);
			  //finally return to the grid screen
			  if(flag==null)
			  {
				  tableData.setData(alBankAccount, "search");
				  request.getSession().setAttribute("tableData",tableData);
				  request.getSession().setAttribute("flagaccount",flag);
				  return this.getForward(strValidateAccounts, mapping, request);
			  }
			  else
			  {
				  tableData.setData(alBankAccount, "search");
				  request.getSession().setAttribute("tableData",tableData);
				  request.getSession().setAttribute("flagaccount",flag);
				  return this.getForward(strReviewValidateAccounts, mapping, request);	
			  }

		  }//end of try
		  catch(TTKException expTTK)
		  {
			  return this.processExceptions(request, mapping, expTTK);
		  }//end of catch(TTKException expTTK)
		  catch(Exception exp)
		  {
			  return this.processExceptions(request, mapping, new TTKException(exp, "BankAccountSearch"));
		  }//end of catch(Exception exp)
	  }//end of doViewHospReviewList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  //HttpServletResponse response)
	  
	  
	  
  
  
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
	    public ActionForward doBackwardAccValidation(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
	    	try{
	    		setLinks(request);
	    		log.debug("Inside the doBackward method of HospitalSearchAction");
				TableData tableData = TTKCommon.getTableData(request);
				CustomerBankDetailsManager customerBankDetailsManager=this.getCustomerBankDetailsManagerObject();
				
	    		tableData.modifySearchData(strBackward);//modify the search data
	    		//fetch the data from the data access layer and set the data to table object
	          	String flag = 	(String)request.getSession().getAttribute("flagaccount");
	          	ArrayList alBankAccount=null;
	         	if(flag!=null)
	        	{
	    	     alBankAccount=customerBankDetailsManager.getCustomerBankAccountReviewList(flag);	
	        	 tableData.setData(alBankAccount, strBackward);//set the table data
		    	 request.getSession().setAttribute("tableData",tableData);//set the table data object to session
		    		TTKCommon.documentViewer(request);
		    		return this.getForward(strReviewValidateAccounts, mapping, request);
	        	}
	    	  else
	    	  {
	        	 alBankAccount=customerBankDetailsManager.getCustomerBankAccountReviewList();	
	        	 tableData.setData(alBankAccount, strBackward);//set the table data
		    	 request.getSession().setAttribute("tableData",tableData);//set the table data object to session
		    		TTKCommon.documentViewer(request);
		    		return this.getForward(strValidateAccounts, mapping, request);
	    	  }
	    			
	    		
	    	
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"BankAccountSearch"));
			}//end of catch(Exception exp)
	    }//end of doBackwardAccValidation(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	    public ActionForward doForwardAccValidation(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
	    	try{
	    		setLinks(request);
	    		log.debug("Inside the doForward method of HospitalSearchAction");
				TableData tableData = TTKCommon.getTableData(request);
				CustomerBankDetailsManager customerBankDetailsManager=this.getCustomerBankDetailsManagerObject();
	    		tableData.modifySearchData(strForward);//modify the search data
	    		//fetch the data from the data access layer and set the data to table object
	    		ArrayList alBankAccount=null;
	    	   	String flag = 	(String)request.getSession().getAttribute("flagaccount");
		    	if(flag!=null)
		    	{
		    		 alBankAccount=customerBankDetailsManager.getCustomerBankAccountReviewList(flag);	
		    		 tableData.setData(alBankAccount, strForward);//set the table data
			    	 request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			    	  TTKCommon.documentViewer(request);
			    	  return this.getForward(strReviewValidateAccounts, mapping, request);
		    	}
		    	else
		    	{
		    		 alBankAccount=customerBankDetailsManager.getCustomerBankAccountReviewList();	
		    		tableData.setData(alBankAccount, strForward);//set the table data
			    	request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			    	TTKCommon.documentViewer(request);
			    	return this.getForward(strValidateAccounts, mapping, request);
		    	}
	    	
	    	
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"BankAccountSearch"));
			}//end of catch(Exception exp)
	    }//end of doForwardAccValidation(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	    
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
		public ActionForward doViewHospBankAccountReviewDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
											   HttpServletResponse response) throws Exception{
			try{
				//setLinks(request);
				log.info("Inside CustomerBankDetailsAction doViewHospBankAccountReviewDetails");
				DynaActionForm frmCustomerBankAccountList = (DynaActionForm)form;
				CustomerBankDetailsVO customerBankDetailsVO=null;
				TableData tableData = TTKCommon.getTableData(request);
				String strSwitchType=TTKCommon.checkNull((String)frmCustomerBankAccountList.get("switchType"));
				CustomerBankDetailsManager customerBankDetailsManager=this.getCustomerBankDetailsManagerObject();
				this.setLinks(request,strSwitchType);
				
				HashMap hmCityList = null;
				ArrayList alCityList = new ArrayList();
				customerBankDetailsVO =new CustomerBankDetailsVO();
				if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				{
					customerBankDetailsVO = (CustomerBankDetailsVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
					
					customerBankDetailsVO = customerBankDetailsManager.getHospitaBanklDetail(customerBankDetailsVO.getHospSeqID());
					
					//getting the finance files uploaded at empanelment level
					ArrayList<String> alFileUploads	=	customerBankDetailsManager.getFilesUploadedAtEmpnl(customerBankDetailsVO.getHospSeqID());
					request.getSession().setAttribute("alFileUploads", alFileUploads);
					//Done getting files 
					
					if(customerBankDetailsVO.getBankState()!="")
						hmCityList=customerBankDetailsManager.getCityInfo(customerBankDetailsVO.getBankState());
					String countryCode	=	"";
					if(hmCityList!=null)
					{
						alCityList = (ArrayList)hmCityList.get(customerBankDetailsVO.getBankState());
						countryCode	=	(String)(hmCityList.get("CountryId"));
					}//end of if(hmCityList!=null)
					//if(countryCode!="")
					//	customerBankDetailsVO.setCountryCode(countryCode);
					
					frmCustomerBankAccountList = (DynaActionForm)FormUtils.setFormValues("frmCustomerBankAccountList",
							customerBankDetailsVO,this,mapping,request);
					
					ArrayList<Object> alHospData=new ArrayList<Object>();
		    		alHospData=populateAccountInfo(frmCustomerBankAccountList,mapping,request);
		    		if(alHospData!=null)
		    			request.getSession().setAttribute("alHospData",alHospData);
					
		        	frmCustomerBankAccountList.set("alCityList",alCityList);
					frmCustomerBankAccountList.set("hiddenReview", customerBankDetailsVO.getReviewedYN());
				}//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				request.getSession().setAttribute("frmCustomerBankAccountList", frmCustomerBankAccountList);
				request.setAttribute("bankcity", customerBankDetailsVO.getBankcity());
				frmCustomerBankAccountList.set("caption", "Edit("+customerBankDetailsVO.getHospitalName()+")");
				frmCustomerBankAccountList.set("switchHospOrPtr", "HOSP");
				return this.getForward(strBankHospAccountReviewDetail, mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp, "BankAccountSearch"));
			}//end of catch(Exception exp)
		}//end of doViewHospBankAccountReviewDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse//response)
		
	  
	  
		public ActionForward doViewHospBankReviewAccountReviewDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception{
			try{
				//setLinks(request);
				log.info("Inside CustomerBankDetailsAction doViewHospBankReviewAccountReviewDetails");
				DynaActionForm frmCustomerBankAccountList = (DynaActionForm)form;
				CustomerBankDetailsVO customerBankDetailsVO=null;
				TableData tableData = TTKCommon.getTableData(request);
				String strSwitchType=TTKCommon.checkNull((String)frmCustomerBankAccountList.get("switchType"));
				
				
				
				CustomerBankDetailsManager customerBankDetailsManager=this.getCustomerBankDetailsManagerObject();
				this.setLinks(request,strSwitchType);

				HashMap hmCityList = null;
				ArrayList alCityList = new ArrayList();
				customerBankDetailsVO =new CustomerBankDetailsVO();
				if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				{
					customerBankDetailsVO = (CustomerBankDetailsVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));

					customerBankDetailsVO = customerBankDetailsManager.getHospitaBanklDetail(customerBankDetailsVO.getHospSeqID());

					//getting the finance files uploaded at empanelment level
					ArrayList<String> alFileUploads	=	customerBankDetailsManager.getFilesUploadedAtEmpnl(customerBankDetailsVO.getHospSeqID());
					request.getSession().setAttribute("alFileUploads", alFileUploads);
					//Done getting files 

					if(customerBankDetailsVO.getBankState()!="")
						hmCityList=customerBankDetailsManager.getCityInfo(customerBankDetailsVO.getBankState());
					String countryCode	=	"";
					if(hmCityList!=null)
					{
						alCityList = (ArrayList)hmCityList.get(customerBankDetailsVO.getBankState());
						countryCode	=	(String)(hmCityList.get("CountryId"));
					}//end of if(hmCityList!=null)
					if(countryCode!="")
						customerBankDetailsVO.setCountryCode(countryCode);

					frmCustomerBankAccountList = (DynaActionForm)FormUtils.setFormValues("frmCustomerBankAccountList",
							customerBankDetailsVO,this,mapping,request);

					ArrayList<Object> alHospData=new ArrayList<Object>();
					alHospData=populateAccountInfo(frmCustomerBankAccountList,mapping,request);
					if(alHospData!=null)
						request.getSession().setAttribute("alHospData",alHospData);

					frmCustomerBankAccountList.set("alCityList",alCityList);
					frmCustomerBankAccountList.set("hiddenReview", customerBankDetailsVO.getReviewedYN());
				}//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				frmCustomerBankAccountList.set("switchHospOrPtr", strSwitchType);
				request.getSession().setAttribute("frmCustomerBankAccountList", frmCustomerBankAccountList);
				request.setAttribute("bankcity", customerBankDetailsVO.getBankcity());
				
				frmCustomerBankAccountList.set("caption", "Edit("+customerBankDetailsVO.getHospitalName()+")");
				return this.getForward(strBankHospRevisedAccountReviewDetail, mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp, "BankAccountSearch"));
			}//end of catch(Exception exp)
		}//end of doViewHospBankAccountReviewDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse//response)


		
	  /**
	     * This method is used to load cities based on the selected state.
	     * Finally it forwards to the appropriate view based on the specified forward mappings
	     *
	     * @param mapping The ActionMapping used to select this instance
	     * @param form The optional ActionForm bean for this request (if any)
	     * @param request The HTTP request we are processing
	     * @param response The HTTP response we are creating
	     * @return ActionForward Where the control will be forwarded, after this request is processed
	     * @throws Exception if any error occurs
	     */
	    public ActionForward doChangeState(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    								   HttpServletResponse response) throws Exception{
	    	try{
	    		setLinks(request);
	    		
	    		log.info("Inside doChangeState method of CustomerBankDetailsAccountList");
	    		String flag = request.getParameter("Flag");
                DynaActionForm frmCustomerBankAccountList = (DynaActionForm)form;
                 AccountDetailVO accountDetailVO=null;
				CustomerBankDetailsVO customerBankDetailsVO=null;
				HashMap hmCityList = null;
				ArrayList alCityList = null;
				String countryCode		="";
				String hospOrPtr = request.getParameter("hospOrPtr");
				CustomerBankDetailsManager customerBankDetailsManager=this.getCustomerBankDetailsManagerObject();
				customerBankDetailsVO = (CustomerBankDetailsVO)FormUtils.getFormValues(frmCustomerBankAccountList,"frmCustomerBankAccountList",this,
						   mapping,request);
				log.info("customerBankDetailsVO.getBankState()::"+customerBankDetailsVO.getBankState()+"kish");
				if(!"".equals(customerBankDetailsVO.getBankState()))
				{
	            hmCityList=customerBankDetailsManager.getCityInfo(customerBankDetailsVO.getBankState());
	            countryCode	=	(String)(hmCityList.get("CountryId"))==null?"":(String)(hmCityList.get("CountryId"));
				if(hmCityList!=null)
				{
					alCityList = (ArrayList)hmCityList.get(customerBankDetailsVO.getBankState());
				}//end of if(hmCityList!=null)
				}
				if(alCityList==null)
				{
					alCityList=new ArrayList();
				}//end of if(alCityList==null)
				customerBankDetailsVO.setCountryCode(countryCode);
				frmCustomerBankAccountList = (DynaActionForm)FormUtils.setFormValues("frmCustomerBankAccountList",
						customerBankDetailsVO,this,mapping,request);
				frmCustomerBankAccountList.set("frmChanged","changed");
				frmCustomerBankAccountList.set("alCityList",alCityList);
				/*request.getSession().setAttribute("frmCustomerBankAccountList",frmCustomerBankAccountList);
				if(flag==null)
				{
				    return this.getForward(strBankHospAccountReviewDetail, mapping, request);
				}
				else if(flag.equals("Rnw") && flag!=null)
				{
					return this.getForward(strBankHospRevisedAccountReviewDetail, mapping, request);
				}
				return this.getForward(strBankHospAccountReviewDetail, mapping, request);*/
				
				if("HOSP".equals(hospOrPtr)){
					frmCustomerBankAccountList.set("switchHospOrPtr", "HOSP");
					request.getSession().setAttribute("frmCustomerBankAccountList",frmCustomerBankAccountList);
					return this.getForward(strBankHospAccountReviewDetail, mapping, request);
					}
					else{
					frmCustomerBankAccountList.set("switchHospOrPtr", "PTR");
					request.getSession().setAttribute("frmCustomerBankAccountList",frmCustomerBankAccountList);
					return this.getForward(strBankPtnrAccountReviewDetail, mapping, request);
					}
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp, strAccountsError));
			}//end of catch(Exception exp)
	    }//end of doChangeState(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
 
	    
	    
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
	    public ActionForward doReviewSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
	    	try{
	    		setLinks(request);
	    		log.info("Inside doReviewSave method of CustomerBankDetailsAccountList");
	    		DynaActionForm frmCustomerBankAccountList = (DynaActionForm)form;

	    		frmCustomerBankAccountList.set("flagValidate","true");  //Rishi Sharma
				String strTarget="";
				CustomerBankDetailsManager customerBankDetailsManager=this.getCustomerBankDetailsManagerObject();
				HashMap hmCityList = null;
				ArrayList alCityList = null;
				AccountDetailVO accountDetailVO=null;
				CustomerBankDetailsVO customerBankDetailsVO	=	null;
				int iUpdate=0;
	    		ArrayList<Object> alModifiedHospInfo=new ArrayList<Object>();
				accountDetailVO=new AccountDetailVO();
	            String strCaption = (String)frmCustomerBankAccountList.get("caption");
				
	            customerBankDetailsVO = (CustomerBankDetailsVO)FormUtils.getFormValues(frmCustomerBankAccountList,"frmCustomerBankAccountList",this,
						   mapping,request);
	            
	            alModifiedHospInfo=populateAccountInfo(frmCustomerBankAccountList,mapping,request);
	            boolean bFlag=true;
				//call the checkDifference method to check whether any change is made in the bank information
				if(request.getSession().getAttribute("alHospData")!=null)
				{
					ArrayList tt	=	(ArrayList)request.getSession().getAttribute("alHospData");
				   bFlag=isModified(alModifiedHospInfo,(ArrayList)request.getSession().getAttribute("alHospData"));
				}//end of if(request.getSession().getAttribute("alHospInfo")!=null)
				if(bFlag==false)
				{
					strTarget="referencedetail";
					return this.getForward(strTarget, mapping, request);
				}//end of if(bFlag==false)
				else
				{
					frmCustomerBankAccountList = (DynaActionForm)FormUtils.setFormValues("frmCustomerBankAccountList",
							customerBankDetailsVO,this,mapping,request);
					customerBankDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));
					iUpdate = customerBankDetailsManager.addUpdateReviewAccount(customerBankDetailsVO);
					strTarget=strBankHospAccountReviewDetail;
				}//end of if else
				
				if(iUpdate > 0)
				{
	                request.setAttribute("updated","message.savedSuccessfully");
	            }//end of if(iUpdate > 0)
				frmCustomerBankAccountList.initialize(mapping);
				//make a requery
				customerBankDetailsVO = customerBankDetailsManager.getHospitaBanklDetail(customerBankDetailsVO.getHospSeqID());

				frmCustomerBankAccountList = (DynaActionForm) FormUtils.setFormValues("frmCustomerBankAccountList", customerBankDetailsVO, this,mapping, request);

	            hmCityList=customerBankDetailsManager.getCityInfo(customerBankDetailsVO.getBankState());
				if(hmCityList!=null)
				{
					alCityList = (ArrayList)hmCityList.get(customerBankDetailsVO.getBankState());
				}//end of if(hmCityList!=null)
				if(alCityList==null)
				{
					alCityList=new ArrayList();
				}//end of if(alCityList==null)

				frmCustomerBankAccountList.set("caption",strCaption);
				frmCustomerBankAccountList.set("alCityList",alCityList);
				frmCustomerBankAccountList.set("hiddenReview", customerBankDetailsVO.getReviewedYN());
				frmCustomerBankAccountList.set("switchHospOrPtr", "HOSP");
	            request.getSession().setAttribute("frmCustomerBankAccountList",frmCustomerBankAccountList);
	            return this.getForward(strTarget, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp, strAccountsError));
			}//end of catch(Exception exp)
	    }//end of doReviewSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	    
	    
	    
	    public ActionForward doRevisedReviewSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
	    	try{
	    		setLinks(request);
	    		log.info("Inside doRevisedReviewSave method of CustomerBankDetailsAccountList");
	    		DynaActionForm frmCustomerBankAccountList = (DynaActionForm)form;
				String strTarget="";
				CustomerBankDetailsManager customerBankDetailsManager=this.getCustomerBankDetailsManagerObject();
				HashMap hmCityList = null;
				ArrayList alCityList = null;
				AccountDetailVO accountDetailVO=null;
				CustomerBankDetailsVO customerBankDetailsVO	=	null;
				int iUpdate=0;
	    		ArrayList<Object> alModifiedHospInfo=new ArrayList<Object>();
				accountDetailVO=new AccountDetailVO();
	            String strCaption = (String)frmCustomerBankAccountList.get("caption");
	            customerBankDetailsVO = (CustomerBankDetailsVO)FormUtils.getFormValues(frmCustomerBankAccountList,"frmCustomerBankAccountList",this,
						   mapping,request);
	            
	            alModifiedHospInfo=populateAccountInfo(frmCustomerBankAccountList,mapping,request);
	            boolean bFlag=true;
				//call the checkDifference method to check whether any change is made in the bank information
				if(request.getSession().getAttribute("alHospData")!=null)
				{
					ArrayList tt	=	(ArrayList)request.getSession().getAttribute("alHospData");
					bFlag=isModified(alModifiedHospInfo,(ArrayList)request.getSession().getAttribute("alHospData"));
				}//end of if(request.getSession().getAttribute("alHospInfo")!=null)
				if(bFlag==false)
				{
					strTarget="referencereviewdetail";
					return this.getForward(strTarget, mapping, request);
				}//end of if(bFlag==false)
				else
				{
					frmCustomerBankAccountList = (DynaActionForm)FormUtils.setFormValues("frmCustomerBankAccountList",
							customerBankDetailsVO,this,mapping,request);
					customerBankDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));
					iUpdate = customerBankDetailsManager.addUpdateRevisedReviewAccount(customerBankDetailsVO);
					strTarget=strBankHospRevisedAccountReviewDetail;
				}//end of if else
				
				if(iUpdate > 0)
				{
	                request.setAttribute("updated","message.savedSuccessfully");
	            }//end of if(iUpdate > 0)
				frmCustomerBankAccountList.initialize(mapping);
				//make a requery
				customerBankDetailsVO = customerBankDetailsManager.getHospitaBanklDetail(customerBankDetailsVO.getHospSeqID());

				frmCustomerBankAccountList = (DynaActionForm) FormUtils.setFormValues("frmCustomerBankAccountList", customerBankDetailsVO, this,mapping, request);

	            hmCityList=customerBankDetailsManager.getCityInfo(customerBankDetailsVO.getBankState());
				if(hmCityList!=null)
				{
					alCityList = (ArrayList)hmCityList.get(customerBankDetailsVO.getBankState());
				}//end of if(hmCityList!=null)
				if(alCityList==null)
				{
					alCityList=new ArrayList();
				}//end of if(alCityList==null)

				frmCustomerBankAccountList.set("caption",strCaption);
				frmCustomerBankAccountList.set("alCityList",alCityList);
				frmCustomerBankAccountList.set("hiddenReview", customerBankDetailsVO.getReviewedYN());

	            request.getSession().setAttribute("frmCustomerBankAccountList",frmCustomerBankAccountList);
	            return this.getForward(strTarget, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp, strAccountsError));
			}//end of catch(Exception exp)
	    }//end of doReviewSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	    
	    
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
		public ActionForward doReferenceReviewDetail(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			try {
				//setLinks(request);
				// Web board not using for intX checkWebboardVisabulity(request);
				log.info("Inside doReferenceReviewDetail method of CustomerBankDetailsAccountList");
				String flag = request.getParameter("Flag");
				DynaActionForm frmCustomerBankAccountList = (DynaActionForm) form;
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
				alHospInfo = populateAccountInfo(frmCustomerBankAccountList, mapping,
						request);
				// Set the alHospInfo into session
				request.getSession().setAttribute("alHospInfo", alHospInfo);
				customerBankDetailsVO = (CustomerBankDetailsVO)FormUtils.getFormValues(frmCustomerBankAccountList,"frmCustomerBankAccountList",this,
						   mapping,request);
				// logs
				
				// end logs
				customerBankDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));
					//Long iUpdate = customerBankDetailsManager.addUpdateReviewAccount(customerBankDetailsVO);
					int iUpdate = customerBankDetailsManager.addUpdateReviewAccount(customerBankDetailsVO);
					//frmCustomerBankAccountList.initialize(mapping);
					
					customerBankDetailsVO = customerBankDetailsManager.getHospitaBanklDetail(customerBankDetailsVO.getHospSeqID());
					//customerBankDetailsVO = customerBankDetailsManager.getHospBankAccountDetail(new Long(TTKCommon.checkNull(customerBankDetailsVO.getHospitalSeqId())), TTKCommon.getUserSeqId(request));

				HashMap hmCityList = null;
				ArrayList alCityList = null;
				
				hmCityList=customerBankDetailsManager.getCityInfo(customerBankDetailsVO.getBankState());
				String countryCode	=	"";
				if(hmCityList!=null)
				{
					alCityList = (ArrayList)hmCityList.get(customerBankDetailsVO.getBankState());
					countryCode=	(String)(hmCityList.get("CountryId"));
				}//end of if(hmCityList!=null)
				if(alCityList==null)
					alCityList	=	new ArrayList();
					
				customerBankDetailsVO.setCountryCode(countryCode);
				
				frmCustomerBankAccountList = (DynaActionForm) FormUtils.setFormValues("frmCustomerBankAccountList", customerBankDetailsVO, this,mapping, request);
				frmCustomerBankAccountList.set("alCityList", alCityList);
				request.setAttribute("bankcity", customerBankDetailsVO.getBankcity());
				// getMemberBankAccountdetail1(TTKCommon.getWebBoardId(request),TTKCommon.getUserSeqId(request));
				strEditMode = "Edit";
				
				frmCustomerBankAccountList.set("caption", "Edit("+customerBankDetailsVO.getHospitalName()+")");
				frmCustomerBankAccountList.set("editmode", strEditMode);
				frmCustomerBankAccountList.set("hiddenReview", customerBankDetailsVO.getReviewedYN());
				frmCustomerBankAccountList.set("switchHospOrPtr", "HOSP");
				// keep the frmBankAcctGeneral in session scope
				request.getSession().setAttribute("frmCustomerBankAccountList",frmCustomerBankAccountList);
				request.setAttribute("updated", "message.savedSuccessfully");
				if(flag==null)
				{	
				return this.getForward(strBankHospAccountReviewDetail, mapping, request);
				}
				else if(flag!=null && flag.length()!=0)
				{
					return this.getForward(strBankHospRevisedAccountReviewDetail, mapping, request);
				}
		   return this.getForward(strBankHospAccountReviewDetail, mapping, request);
			}// end of try
			catch (TTKException expTTK) {
				return this.processExceptions(request, mapping, expTTK);
			}// end of catch(TTKException expTTK)
			catch (Exception exp) {
				return this.processExceptions(request, mapping, new TTKException(
						exp, strAccountsError));
			}// end of catch(Exception exp)
		}// end of doReferenceReviewDetail(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)
		
	    /**
		 * Add the required account information to arrayList to check whether any changes are made
		 * @param accountForm DynaActionForm object to which contain the accounts information.
		 * @param request HttpServletRequest  object which contains accounts information.
	     * @param mapping The ActionMapping used to select this instance
	     * @param request The HTTP request we are processing
		 * @return alAccountInfoNew ArrayList which contian accounts information.
		 */
	    
		private ArrayList<Object> populateAccountInfo(DynaActionForm accountForm,ActionMapping mapping,
				HttpServletRequest request) throws TTKException
		{
			ArrayList<Object> alAccountInfoNew=new ArrayList<Object>();
	        try
	        {
	            alAccountInfoNew.add((String)accountForm.get("bankname"));
	            alAccountInfoNew.add((String)accountForm.get("bankAccno"));
	            alAccountInfoNew.add((String)accountForm.get("bankAccName"));
	            alAccountInfoNew.add((String)accountForm.get("bankBranch"));
	            alAccountInfoNew.add((String)accountForm.get("address1"));
	            alAccountInfoNew.add((String)accountForm.get("address2"));
	            alAccountInfoNew.add((String)accountForm.get("address3"));
	            alAccountInfoNew.add((String)accountForm.get("bankState"));
	            alAccountInfoNew.add((String)accountForm.get("bankcity"));
	            alAccountInfoNew.add((String)accountForm.get("pinCode"));
	            alAccountInfoNew.add((String)accountForm.get("countryCode"));
	        }//end of try
	        catch(Exception exp)
	        {
	            throw new TTKException(exp, strAccountsError);
	        }//end of catch
			return alAccountInfoNew;
		}//end of populateAccountInfo(HttpServletRequest request,DynaActionForm accountForm)
		
		/**
		 * Returns the boolean value by comparing the two arraylist.
		 * @param  alHospInfoNew ArrayList which contains account new information.
		 * @param alHospInfoOld ArrayList which contains the account old information.
		 * @return false when change is found when comparing the two ArrayList else return true
		 */
		private boolean isModified(ArrayList alHospInfoNew,ArrayList alHospInfoOld)
		{
			for(int i=0;i<alHospInfoOld.size();i++)
			{
				if(!alHospInfoNew.get(i).equals(alHospInfoOld.get(i)))
					return false;
			}//end of for(int i=0;i<alHospInfoOld.size();i++)
			return true;
		}//end of isModified(ArrayList alHospInfoNew,ArrayList alHospInfoOld)
		
		//To Review the partner waiting from empanelment in finance section.
		  
		  public ActionForward doViewPtnrReviewList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			          HttpServletResponse response) throws Exception{
				try{
					//setLinks(request);
					log.debug("Inside CustomerBankDetailsAction doViewPtnrReviewList");
					TableData tableData = TTKCommon.getTableData(request);
					String strSubLink=TTKCommon.getActiveSubLink(request);
					String strForwardList="";
					DynaActionForm frmCustomerBankDetailsSearch =(DynaActionForm)form;
					//BankAccountManager bankAccountObject=this.getBankAccountManagerObject();
					 this.setLinks(request,"PTR");
					CustomerBankDetailsManager customerBankDetailsManager=this.getCustomerBankDetailsManagerObject();
					
					String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
					String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));

					if(!strPageID.equals("") || !strSortID.equals(""))
					{
						if(!strPageID.equals(""))
						{
							tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
							return (mapping.findForward(strValidateAccounts));
						}///end of if(!strPageID.equals(""))
						else
						{
							tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
							tableData.modifySearchData("sort");//modify the search data
						}//end of else
					}//end of if(!strPageID.equals("") || !strSortID.equals(""))
					else
					{   
						// create the required grid table
						tableData.createTableInfo("PtnrReviewTable",null);
						//fetch the data from the data access layer and set the data to table object
						// this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
						tableData.modifySearchData("search");
					}//end of else
							
					ArrayList alBankAccount=customerBankDetailsManager.getPartnerBankAccountReviewList();			
					CustomerBankDetailsVO customerBankDetailsVO=new CustomerBankDetailsVO();
					try
					{
						customerBankDetailsVO=(CustomerBankDetailsVO)alBankAccount.get(0);
					}
					catch(IndexOutOfBoundsException iobe)
					{
						log.info("in IndexOutOfBoundsException");	
					}
								           
					tableData.setData(alBankAccount, "search");
				
					request.getSession().setAttribute("tableData",tableData);
					// Web board not using for intX checkWebboardVisabulity(request);
					//finally return to the grid screen
					
					return this.getForward(strValidateAccounts, mapping, request);
					
				}//end of try
				catch(TTKException expTTK)
				{
					return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request, mapping, new TTKException(exp, "BankAccountSearch"));
				}//end of catch(Exception exp)
			}//end of doViewHospReviewList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
		  
		  public ActionForward doViewPtnrBankAccountReviewDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				  HttpServletResponse response) throws Exception{
			  try{
				  //setLinks(request);
				  log.info("Inside CustomerBankDetailsAction doViewPtnrBankAccountReviewDetails");
				  DynaActionForm frmCustomerBankAccountList = (DynaActionForm)form;
				  CustomerBankDetailsVO customerBankDetailsVO=null;
				  TableData tableData = TTKCommon.getTableData(request);
				  String strSwitchType=TTKCommon.checkNull((String)frmCustomerBankAccountList.get("switchType"));
				  CustomerBankDetailsManager customerBankDetailsManager=this.getCustomerBankDetailsManagerObject();
				  this.setLinks(request,strSwitchType);

				  HashMap hmCityList = null;
				  ArrayList alCityList = new ArrayList();
				  customerBankDetailsVO =new CustomerBankDetailsVO();
				  if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				  {
					  customerBankDetailsVO = (CustomerBankDetailsVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));

					  customerBankDetailsVO = customerBankDetailsManager.getPartnerBankDetail(customerBankDetailsVO.getPtnrSeqID());

					  //getting the finance files uploaded at empanelment level
					  //ArrayList<String> alFileUploads	=	customerBankDetailsManager.getFilesUploadedAtEmpnl(customerBankDetailsVO.getPtnrSeqID());
					  ArrayList<String> alFileUploads = new ArrayList<String>();
					  request.getSession().setAttribute("alFileUploads", alFileUploads);
					  //Done getting files 
					  
										  
					  if(customerBankDetailsVO.getBankState()!="")
						  hmCityList=customerBankDetailsManager.getCityInfo(customerBankDetailsVO.getBankState());
					  String countryCode	=	"";
					  if(hmCityList!=null)
					  {
						  alCityList = (ArrayList)hmCityList.get(customerBankDetailsVO.getBankState());
						  countryCode	=	(String)(hmCityList.get("CountryId"));
					  }//end of if(hmCityList!=null)
					 // if(countryCode!="")
					 // customerBankDetailsVO.setCountryCode(countryCode);
					 
					  
					  frmCustomerBankAccountList = (DynaActionForm)FormUtils.setFormValues("frmCustomerBankAccountList",
							  customerBankDetailsVO,this,mapping,request);

					  ArrayList<Object> alHospData=new ArrayList<Object>();
					  alHospData=populateAccountInfo(frmCustomerBankAccountList,mapping,request);
					  if(alHospData!=null)
						  request.getSession().setAttribute("alHospData",alHospData);

					  frmCustomerBankAccountList.set("alCityList",alCityList);
					  frmCustomerBankAccountList.set("hiddenReview", customerBankDetailsVO.getReviewedYN());
					
				  }//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				 
				  request.getSession().setAttribute("frmCustomerBankAccountList", frmCustomerBankAccountList);
				  request.setAttribute("bankcity", customerBankDetailsVO.getBankcity());
				  frmCustomerBankAccountList.set("caption", "Edit("+customerBankDetailsVO.getPartnerName()+")");
				  frmCustomerBankAccountList.set("switchHospOrPtr", "PTR");
				  return this.getForward(strBankPtnrAccountReviewDetail, mapping, request);
			  }//end of try
			  catch(TTKException expTTK)
			  {
				  return this.processExceptions(request, mapping, expTTK);
			  }//end of catch(TTKException expTTK)
			  catch(Exception exp)
			  {
				  return this.processExceptions(request, mapping, new TTKException(exp, "BankAccountSearch"));
			  }//end of catch(Exception exp)
		  }//end of doViewHospBankAccountReviewDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse//response)


		  
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
		    public ActionForward doPartnerReviewSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		    		HttpServletResponse response) throws Exception{
		    	try{
		    		setLinks(request);
		    		log.info("Inside doPartnerReviewSave method of CustomerBankDetailsAccountList");
		    		DynaActionForm frmCustomerBankAccountList = (DynaActionForm)form;
		    		frmCustomerBankAccountList.set("flagValidate","true");
					String strTarget="";
					CustomerBankDetailsManager customerBankDetailsManager=this.getCustomerBankDetailsManagerObject();
					HashMap hmCityList = null;
					ArrayList alCityList = null;
					AccountDetailVO accountDetailVO=null;
					CustomerBankDetailsVO customerBankDetailsVO	=	null;
					int iUpdate=0;
		    		ArrayList<Object> alModifiedHospInfo=new ArrayList<Object>();
					accountDetailVO=new AccountDetailVO();
		            String strCaption = (String)frmCustomerBankAccountList.get("caption");
					
		            customerBankDetailsVO = (CustomerBankDetailsVO)FormUtils.getFormValues(frmCustomerBankAccountList,"frmCustomerBankAccountList",this,
							   mapping,request);
		            
		            alModifiedHospInfo=populateAccountInfo(frmCustomerBankAccountList,mapping,request);
		            boolean bFlag=true;
					//call the checkDifference method to check whether any change is made in the bank information
					if(request.getSession().getAttribute("alHospData")!=null)
					{
						ArrayList tt	=	(ArrayList)request.getSession().getAttribute("alHospData");
						bFlag=isModified(alModifiedHospInfo,(ArrayList)request.getSession().getAttribute("alHospData"));
					}//end of if(request.getSession().getAttribute("alHospInfo")!=null)
					if(bFlag==false)
					{
						strTarget="referencedetail";
						return this.getForward(strTarget, mapping, request);
					}//end of if(bFlag==false)
					else
					{
						frmCustomerBankAccountList = (DynaActionForm)FormUtils.setFormValues("frmCustomerBankAccountList",
						customerBankDetailsVO,this,mapping,request);
						frmCustomerBankAccountList.set("flagValidate",null);
						customerBankDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));
						iUpdate = customerBankDetailsManager.addUpdatePartnerReviewAccount(customerBankDetailsVO);
						strTarget=strBankPtnrAccountReviewDetail;
					}//end of if else
					
					if(iUpdate > 0)
					{
		                request.setAttribute("updated","message.savedSuccessfully");
		            }//end of if(iUpdate > 0)
					frmCustomerBankAccountList.initialize(mapping);
					//make a requery
					customerBankDetailsVO = customerBankDetailsManager.getPartnerBankDetail(customerBankDetailsVO.getPtnrSeqID());

					frmCustomerBankAccountList = (DynaActionForm) FormUtils.setFormValues("frmCustomerBankAccountList", customerBankDetailsVO, this,mapping, request);

		            hmCityList=customerBankDetailsManager.getCityInfo(customerBankDetailsVO.getBankState());
					if(hmCityList!=null)
					{
						alCityList = (ArrayList)hmCityList.get(customerBankDetailsVO.getBankState());
					}//end of if(hmCityList!=null)
					if(alCityList==null)
					{
						alCityList=new ArrayList();
					}//end of if(alCityList==null)

					frmCustomerBankAccountList.set("caption",strCaption);
					frmCustomerBankAccountList.set("alCityList",alCityList);
					frmCustomerBankAccountList.set("hiddenReview", customerBankDetailsVO.getReviewedYN());
					frmCustomerBankAccountList.set("switchHospOrPtr", "PTR");
				
		            request.getSession().setAttribute("frmCustomerBankAccountList",frmCustomerBankAccountList);
		            return this.getForward(strTarget, mapping, request);
		    	}//end of try
		    	catch(TTKException expTTK)
				{
					return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request, mapping, new TTKException(exp, strAccountsError));
				}//end of catch(Exception exp)
		    }//end of doReviewSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
		    
		    
			  
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
			public ActionForward doPartnerReferenceReviewDetail(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				try {
					//setLinks(request);
					// Web board not using for intX checkWebboardVisabulity(request);
					log.info("Inside doPartnerReferenceReviewDetail method of CustomerBankDetailsAccountList");
					DynaActionForm frmCustomerBankAccountList = (DynaActionForm) form;
					frmCustomerBankAccountList.set("flagValidate",null);
					String strEditMode = "";
					StringBuffer strCaption = new StringBuffer();

					// BankAccountManager
					// bankAcctObject=this.getBankAccountManagerObject();
					CustomerBankDetailsManager customerBankDetailsManager = this
					.getCustomerBankDetailsManagerObject();
					AccountDetailVO accountDetailVO = null;
					CustomerBankDetailsVO customerBankDetailsVO = null;

					
					ArrayList<Object> alHospInfo = new ArrayList<Object>();
					// Call populateAccountInfo to store Partner Bank Account
					// Information in arraylist
					alHospInfo = populateAccountInfo(frmCustomerBankAccountList, mapping,
							request);
					// Set the alHospInfo into session
					request.getSession().setAttribute("alHospInfo", alHospInfo);
					customerBankDetailsVO = (CustomerBankDetailsVO)FormUtils.getFormValues(frmCustomerBankAccountList,"frmCustomerBankAccountList",this,
							   mapping,request);
					// logs
					
					// end logs
					customerBankDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));
						//Long iUpdate = customerBankDetailsManager.addUpdateReviewAccount(customerBankDetailsVO);
						int iUpdate = customerBankDetailsManager.addUpdatePartnerReviewAccount(customerBankDetailsVO);
						//frmCustomerBankAccountList.initialize(mapping);
						
						customerBankDetailsVO = customerBankDetailsManager.getPartnerBankDetail(customerBankDetailsVO.getPtnrSeqID());
						//customerBankDetailsVO = customerBankDetailsManager.getHospBankAccountDetail(new Long(TTKCommon.checkNull(customerBankDetailsVO.getHospitalSeqId())), TTKCommon.getUserSeqId(request));

					HashMap hmCityList = null;
					ArrayList alCityList = null;
					
					hmCityList=customerBankDetailsManager.getCityInfo(customerBankDetailsVO.getBankState());
					String countryCode	=	"";
					if(hmCityList!=null)
					{
						alCityList = (ArrayList)hmCityList.get(customerBankDetailsVO.getBankState());
						countryCode=	(String)(hmCityList.get("CountryId"));
					}//end of if(hmCityList!=null)
					if(alCityList==null)
						alCityList	=	new ArrayList();
						
					customerBankDetailsVO.setCountryCode(countryCode);
					
					frmCustomerBankAccountList = (DynaActionForm) FormUtils.setFormValues("frmCustomerBankAccountList", customerBankDetailsVO, this,mapping, request);
					frmCustomerBankAccountList.set("alCityList", alCityList);
					request.setAttribute("bankcity", customerBankDetailsVO.getBankcity());
					// getMemberBankAccountdetail1(TTKCommon.getWebBoardId(request),TTKCommon.getUserSeqId(request));
					strEditMode = "Edit";
					
					frmCustomerBankAccountList.set("caption", "Edit("+customerBankDetailsVO.getPartnerName()+")");
					frmCustomerBankAccountList.set("editmode", strEditMode);
					frmCustomerBankAccountList.set("hiddenReview", customerBankDetailsVO.getReviewedYN());
					frmCustomerBankAccountList.set("switchHospOrPtr", "PTR");
					// keep the frmBankAcctGeneral in session scope
					request.getSession().setAttribute("frmCustomerBankAccountList",frmCustomerBankAccountList);
					request.setAttribute("updated", "message.savedSuccessfully");
					return this.getForward(strBankPtnrAccountReviewDetail, mapping, request);
				}// end of try
				catch (TTKException expTTK) {
					return this.processExceptions(request, mapping, expTTK);
				}// end of catch(TTKException expTTK)
				catch (Exception exp) {
					return this.processExceptions(request, mapping, new TTKException(
							exp, strAccountsError));
				}// end of catch(Exception exp)
			}// end of doReferenceReviewDetail(ActionMapping mapping,ActionForm
			// form,HttpServletRequest request,HttpServletResponse response)
			
			
		
}
