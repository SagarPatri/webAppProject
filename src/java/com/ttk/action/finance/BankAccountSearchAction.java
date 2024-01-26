/**
 * @ (#) BankAccountSearchAction.java June 10, 2006
 * Project       : TTK HealthCare Services
 * File          : BankAccountSearchAction.java
 * Author        : Harsha Vardhan B N
 * Company       : Span Systems Corporation
 * Date Created  : June 10, 2006
 *
 * @author       :
 * Modified by   : ARUN K.M
 * Modified date : 25-10-2006
 * Reason        :
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
import org.apache.struts.validator.DynaValidatorForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.business.finance.BankAccountManager;
import com.ttk.dto.finance.BankAccountVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import com.ttk.action.table.Column;

/**
 * This class is used for searching of List of Bank Accounts.
 * This class also provides option for deletion of bank accounts.
 */
public class BankAccountSearchAction extends TTKAction {
	private static Logger log = Logger.getLogger( BankAccountSearchAction.class );
	//   Modes.
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	private static final String strDeleteList="DeleteList";
	//  Action mapping forwards.
	private static final String strBankAccountList="bankaccountlist";
	private static final String strBankAccountDetail="bankaccountgeneral";
	private static final String strBankFloatAccountList="bankfloataccountlist";
	private static final String strBankFloatAccountDetail="bankfloataccountgeneral";
	private static final String strDailytoBankSrc="dailytobanksrc";
	private static final String strDailyTDSRpt="dailytdsrpt";

	//  Exception Message Identifier
    private static final String strBankAccountSearch="BankAccountSearch";

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
			log.debug("Inside BankAccountSearchAction doDefault");
			TableData tableData = TTKCommon.getTableData(request);
			String strSubLink=TTKCommon.getActiveSubLink(request);
			StringBuffer strCaption=new StringBuffer();
			String strForwardList="";
			if(strSubLink.equals("Bank Account"))
			{
				strForwardList=strBankAccountList;
			}// end of if(strSubLink.equals("Bank Account"))
			else if(strSubLink.equals("Float Account"))
			{
				strForwardList=strBankFloatAccountList;
			}// end of if(strSubLink.equals("Float Account"))
			else if(strSubLink.equals("TDS"))
			{
				strForwardList=strDailytoBankSrc;
			}//end of else if(strSubLink.equals("TDS"))

            String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
														  getAttribute("UserSecurityProfile")).getBranchID());
			DynaActionForm frmSearchBankAccount =(DynaActionForm)form;

            frmSearchBankAccount.initialize(mapping);//reset the form data

            tableData = new TableData();
			tableData.createTableInfo("BankAccountTable",new ArrayList());
			if(strSubLink.equals("Float Account") || strSubLink.equals("TDS"))
			{
				((Column)((ArrayList)tableData.getTitle()).get(6)).setVisibility(false);
			}// end of if(strSubLink.equals("Float Account"))
			strCaption.append(" - ["+TTKCommon.getWebBoardDesc(request)+"]");
			frmSearchBankAccount.set("caption",strCaption.toString());
			request.getSession().setAttribute("tableData",tableData);
			frmSearchBankAccount.set("sTtkBranch",strDefaultBranchID);
			return this.getForward(strForwardList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankAccountSearch));
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
			setLinks(request);
			log.debug("Inside BankAccountSearchAction doSearch");
			TableData tableData = TTKCommon.getTableData(request);
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardList="";
			DynaActionForm frmSearchBankAccount =(DynaActionForm)form;
			BankAccountManager bankAccountObject=this.getBankAccountManagerObject();
			if(strSubLink.equals("Bank Account"))
			{
				strForwardList=strBankAccountList;
			}// end of if(strSubLink.equals("Bank Account"))
			else if(strSubLink.equals("Float Account"))
			{
				strForwardList=strBankFloatAccountList;
			}// end of if(strSubLink.equals("Float Account"))
			else if(strSubLink.equals("TDS"))
			{
				strForwardList=strDailytoBankSrc;
			}//end of else if(strSubLink.equals("TDS"))
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
				tableData.createTableInfo("BankAccountTable",null);
				if(strSubLink.equals("Float Account") || strSubLink.equals("TDS"))
				{
					((Column)((ArrayList)tableData.getTitle()).get(6)).setVisibility(false);
				}
				//fetch the data from the data access layer and set the data to table object
				tableData.setSearchData(this.populateSearchCriteria(frmSearchBankAccount,request));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alBankAccount=bankAccountObject.getBankAccountList(tableData.getSearchData());
			tableData.setData(alBankAccount, "search");
			request.getSession().setAttribute("tableData",tableData);
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
			log.debug("Inside BankAccountSearchAction doForward");
			TableData tableData = TTKCommon.getTableData(request);
			BankAccountManager bankAccountObject=this.getBankAccountManagerObject();
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardList="";
			if(strSubLink.equals("Bank Account"))
			{
				strForwardList=strBankAccountList;
			}// end of if(strSubLink.equals("Bank Account"))
			else if(strSubLink.equals("Float Account"))
			{
				strForwardList=strBankFloatAccountList;
			}// end of if(strSubLink.equals("Float Account"))
			else if(strSubLink.equals("TDS"))
			{
				strForwardList=strDailytoBankSrc;
			}//end of else if(strSubLink.equals("TDS"))
			tableData.modifySearchData(strForward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			ArrayList alBankAccount = bankAccountObject.getBankAccountList(tableData.getSearchData());
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
			setLinks(request);
			log.debug("Inside BankAccountSearchAction doBackward");
			TableData tableData = TTKCommon.getTableData(request);
			BankAccountManager bankAccountObject=this.getBankAccountManagerObject();
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardList="";
			if(strSubLink.equals("Bank Account"))
			{
				strForwardList=strBankAccountList;
			}// end of if(strSubLink.equals("Bank Account"))
			else if(strSubLink.equals("Float Account"))
			{
				strForwardList=strBankFloatAccountList;
			}// end of if(strSubLink.equals("Float Account"))
			else if(strSubLink.equals("TDS"))
			{
				strForwardList=strDailytoBankSrc;
			}//end of else if(strSubLink.equals("TDS"))
			tableData.modifySearchData(strBackward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			ArrayList alBankAccount = bankAccountObject.getBankAccountList(tableData.getSearchData());
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
     * This method is called from the struts framework.
     * This method is used to delete records.
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
    		log.debug("Inside BankAccountSearchAction doDeleteList");
    		BankAccountManager bankAccountObject=this.getBankAccountManagerObject();
    		TableData tableData = TTKCommon.getTableData(request);
    		StringBuffer sbfDeleteId = new StringBuffer("|");
    		int iCount=0;
    		//populate the delete string which contains the sequence id's to be deleted
    		sbfDeleteId.append(populateDeleteId(request,(TableData)request.getSession().getAttribute("tableData")));
    		ArrayList<Object> alDeleteList = new ArrayList<Object>();
    		alDeleteList.add(sbfDeleteId.toString());
    		alDeleteList.add(TTKCommon.getUserSeqId(request));
    		//delete the selected BankAccount based on the flow
    		iCount=bankAccountObject.deleteBankAccount(alDeleteList);
    		//refresh the grid with search data in session
    		ArrayList alBankAcctList = null;
    		//fetch the data from previous set of rowcounts, if all the records are deleted for the current set
    																							//of search criteria
    		if(iCount == tableData.getData().size())
    		{
    			tableData.modifySearchData(strDeleteList);//modify the search data
    			int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().
    												   get(tableData.getSearchData().size()-2));
    			if(iStartRowCount > 0)
    			{
    				alBankAcctList=bankAccountObject.getBankAccountList(tableData.getSearchData());
    			}//end of if(iStartRowCount > 0)
    		}//end if(iCount == tableData.getData().size())
    		else
    		{
    			alBankAcctList=bankAccountObject.getBankAccountList(tableData.getSearchData());
    		}//end of else
    		tableData.setData(alBankAcctList,strDeleteList);
    		if(iCount>0)
    		{
    			//delete the Pre-Auth from the web board if any
    			request.setAttribute("cacheId",sbfDeleteId.append("|").toString());
    			TTKCommon.deleteWebBoardId(request);
    		}//end of if(iCount>0)
    		request.getSession().setAttribute("tableData",tableData);
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
    }//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse
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
	public ActionForward doViewBankAccount(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										   HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside BankAccountSearchAction doViewBankAccount");
			DynaValidatorForm frmBankAccountDetail = (DynaValidatorForm)form;
			BankAccountVO bankAccountVO=null;
			TableData tableData = TTKCommon.getTableData(request);
			String strSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardDetails="";
			if(strSubLink.equals("Bank Account"))
			{
				strForwardDetails=strBankAccountDetail;
			}// end of if(strSubLink.equals("Bank Account"))
			else if(strSubLink.equals("Float Account"))
			{
				strForwardDetails=strBankFloatAccountDetail;
			}// end of if(strSubLink.equals("Float Account"))
			else if(strSubLink.equals("TDS"))
			{
				strForwardDetails=strDailyTDSRpt;

			}//end of else if(strSubLink.equals("TDS"))
			//create a new BankAccount object
			bankAccountVO = new BankAccountVO();
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				bankAccountVO = (BankAccountVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				//add the selected item to the web board and make it as default selected
				if(strSubLink.equals("Bank Account"))
				{
					this.addToWebBoard(bankAccountVO, request);
				}
				if(strSubLink.equals("Float Account"))
				{
					frmBankAccountDetail = (DynaValidatorForm) request.getSession().getAttribute("frmFloatAccDetails");
					frmBankAccountDetail.set("accountSeqID",String.valueOf(bankAccountVO.getAccountSeqID()));
					frmBankAccountDetail.set("accountNO",bankAccountVO.getAccountNO());
					frmBankAccountDetail.set("accountName",bankAccountVO.getAccountName());
					frmBankAccountDetail.set("bankName",bankAccountVO.getBankName());
					frmBankAccountDetail.set("officeTypeDesc",bankAccountVO.getOfficeTypeDesc());
					frmBankAccountDetail.set("branchName",bankAccountVO.getTtkBranch());
					request.getSession().removeAttribute("frmSearchBankAccount");
					request.getSession().removeAttribute("tableData");
				}// end of if(strSubLink.equals("Float Account"))
				if(strSubLink.equals("TDS")){
					//bankAccountVO = (BankAccountVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
					frmBankAccountDetail = (DynaValidatorForm) request.getSession().getAttribute("frmReportList");
					frmBankAccountDetail.set("bankAccountNo",bankAccountVO.getAccountNO());
					request.getSession().removeAttribute("frmSearchBankAccount");
					request.getSession().removeAttribute("tableData");
					//return mapping.findForward(strForwardDetails);
			    }//end of if(strSubLink.equals("TDS"))

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
	}//end of doViewBankAccount(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse
																											//response)

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
			setLinks(request);
			this.populateWebBoard(request);
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
	 * Returns the ArrayList which contains the populated search criteria elements
	 * @param frmSearchBankAccount DynaActionForm will contains the values of corresponding fields
	 * @param request HttpServletRequest object which contains the search parameter that is built
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSearchBankAccount,HttpServletRequest request)
						throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchBankAccount.get("sAccountNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchBankAccount.get("sAccountName")));
		alSearchParams.add((String)frmSearchBankAccount.get("sStatus"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchBankAccount.get("sBankName")));
		alSearchParams.add((String)frmSearchBankAccount.get("sOfficeType"));
		alSearchParams.add(TTKCommon.getLong((String)frmSearchBankAccount.get("sTtkBranch")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmSearchBankAccount)

	/**
	 * Populates the web board in the session with the selected items in the grid
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 */
	private void populateWebBoard(HttpServletRequest request)
	{
		String[] strChk = request.getParameterValues("chkopt");
		TableData tableData = (TableData)request.getSession().getAttribute("tableData");
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		CacheObject cacheObject = null;
		if(strChk!=null&&strChk.length!=0)
		{
		//loop through to populate delete sequence id's and get the value from session for the matching check box value
			for(int i=0; i<strChk.length;i++)
			{
				cacheObject = new CacheObject();
				cacheObject.setCacheId(""+((BankAccountVO)tableData.getData().get(Integer.parseInt(strChk[i]))).
																				  getAccountSeqID());
                cacheObject.setCacheDesc(((BankAccountVO)tableData.getData().get(Integer.parseInt(strChk[i]))).
                																  getAccountName());
				alCacheObject.add(cacheObject);
			}//end of for(int i=0; i<strChk.length;i++)
		}//end of if(strChk!=null&&strChk.length!=0)
		if(toolbar != null)
		{
			toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		}//end of if(toolbar != null)
	}//end of populateWebBoard(HttpServletRequest request)

	/**
	 * Adds the selected item to the web board and makes it as the selected item in the web board
	 * @param bankAccountVO BankAccountVO object which contain the information of BankAccount
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
		//if the object(s) are added to the web board, set the current web board id
		//if(toolbar.getWebBoard().addToWebBoardList(alCacheObject))
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
		//set weboardinvoked as true to avoid change of webboard id twice in same request
		request.setAttribute("webboardinvoked","true");
	}//end of addToWebBoard(BankAccountVO bankAccountVO, HttpServletRequest request)


	/**
	 * Returns array of string which contains the pipe separated Bank Account sequence id's to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String[] which contains the pipe separated Bank Account sequence id's to be deleted
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableData)
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
		//loop through to populate delete sequence id's and get the value from session for the matching check box value
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append(String.valueOf(((BankAccountVO)tableData.getData().
															  get(Integer.parseInt(strChk[i]))).getAccountSeqID()));
					}//end of if(i==0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((BankAccountVO)tableData.
													getData().get(Integer.parseInt(strChk[i]))).getAccountSeqID()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)

	/**
	 * Returns the BankAccountManager session object for invoking methods on it.
	 * @return BankAccountManager session object which can be used for method invocation
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
			}//end if(bankAccountManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "BankAccountSearch");
		}//end of catch
		return bankAccountManager;
	}//end getBankAccountManagerObject()
}//end of class BankAccountSearchAction