/**
 * @ (#) TransactionSearchAction.java June 09, 2006
 * Project      : TTK HealthCare Services
 * File         : TransactionSearchAction.java
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : June 09, 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
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

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.finance.BankAccountManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.finance.TransactionVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of List of Transactions.
 * This class also provides option for Addition and Updation of Transactions.
 */
public class TransactionSearchAction extends TTKAction
{
	private static Logger log = Logger.getLogger(TransactionSearchAction.class);
	//Modes.
	private static final String strBackward="Backward";
    private static final String strForward="Forward";
    // Action mapping forwards.
    private static final String strTransactionSearch="transactionsearch";
    private static final String strTransactionDetails="transactiondetails";
    //  Exception Message Identifier
    private static final String strBankAccount="bankaccount";
    
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
    		log.debug("Inside TransactionSearchAction doDefault");
    		if(TTKCommon.getWebBoardId(request) == null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.accountno.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request) == null)
    		TableData  tableData =TTKCommon.getTableData(request);
    		//create new table data object
    		tableData = new TableData();
    		//create the required grid table
    		tableData.createTableInfo("TransactionTable",new ArrayList());
    		request.getSession().setAttribute("tableData",tableData);
    		((DynaActionForm)form).initialize(mapping);		//reset the form data
    		request.getSession().setAttribute("searchparam", null);
    		return this.getForward(strTransactionSearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBankAccount));
    	}//end of catch(Exception exp)
    }//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside TransactionSearchAction doSearch");
    		TableData  tableData =TTKCommon.getTableData(request);
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		DynaActionForm frmTransSearch= (DynaActionForm) form;
    		//clear the dynaform if visting from left links for the first time
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
    				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    				return (mapping.findForward(strTransactionSearch));
    			}///end of if(!strPageID.equals(""))
    			else
    			{
    				tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
    				tableData.modifySearchData("sort");//modify the search data
    			}//end of else
    		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
    		else
    		{
    			tableData.createTableInfo("TransactionTable",null);
    			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
    			tableData.modifySearchData("search");
    		}//end of else
    		ArrayList alTransSearch= bankAccountManagerObject.getTransactionList(tableData.getSearchData());
    		if((alTransSearch.size()>0)&&(((TransactionVO)alTransSearch.get(0)).getFloatBalance()!=null))
    		{
    			frmTransSearch.set("bankBalance",((TransactionVO)alTransSearch.get(0)).getFloatBalance().toString());
    		}
    		else
    		{
    			frmTransSearch.set("bankBalance",null);
    		}
    		tableData.setData(alTransSearch, "search");
    		//set the table data object to session
    		request.getSession().setAttribute("tableData",tableData);
    		//finally return to the grid screen
    		return this.getForward(strTransactionSearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBankAccount));
    	}//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
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
    	log.debug("Inside TransactionSearchAction doChangeWebBoard");
    	return 	doDefault(mapping,form,request,response);
    }//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside TransactionSearchAction doForward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		tableData.modifySearchData(strForward);//modify the search data
    		ArrayList alTransSearch = bankAccountManagerObject.getTransactionList(tableData.getSearchData());
    		tableData.setData(alTransSearch, strForward);
    		request.getSession().setAttribute("tableData",tableData);
    		return this.getForward(strTransactionSearch, mapping, request);
    		
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBankAccount));
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
    		log.debug("Inside TransactionSearchAction doBackward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		ArrayList alTransSearch = bankAccountManagerObject.getTransactionList(tableData.getSearchData());
    		tableData.setData(alTransSearch, strBackward);
    		request.getSession().setAttribute("tableData",tableData);
    		return this.getForward(strTransactionSearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBankAccount));
    	}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    /**
     * This method is used to reverse the transaction.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doReverse(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							   HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside TransactionSearchAction doReverse");
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		TableData  tableData =TTKCommon.getTableData(request);
    		String strMode=TTKCommon.checkNull(request.getParameter("mode"));
    		DynaActionForm frmTransSearch= (DynaActionForm) form;
    		ArrayList <Object>alSeqIDs=new ArrayList<Object>();
    		alSeqIDs.add(TTKCommon.getWebBoardId(request));
    		alSeqIDs.add(populateFloatTransactionSeqIDs(request, (TableData)request.getSession().
    															  getAttribute("tableData")));
    		alSeqIDs.add(TTKCommon.getUserSeqId(request));
    		
    		int iResult=bankAccountManagerObject.reverseTransaction(alSeqIDs);
    		if(iResult > 0)
    		{
    			ArrayList alTransaction=null;
    			if(iResult == tableData.getData().size())
    			{
    				tableData.modifySearchData(strMode);//modify the search data
    				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().
    													   get(tableData.getSearchData().size()-2));
    				if(iStartRowCount > 0)
    				{
    					alTransaction= bankAccountManagerObject.getTransactionList(tableData.getSearchData());
    				}//end of if(iStartRowCount > 0)
    			}//end of if(iResult == tableData.getData().size())
    			else
    			{
    				alTransaction= bankAccountManagerObject.getTransactionList(tableData.getSearchData());
    			}//end of else
    			if((alTransaction.size()>0)&&(((TransactionVO)alTransaction.get(0)).getFloatBalance()!=null))
    			{
    				frmTransSearch.set("bankBalance",((TransactionVO)alTransaction.get(0)).getFloatBalance().toString());
    			}
    			else
    			{
    				frmTransSearch.set("bankBalance",null);
    			}
    			tableData.setData(alTransaction, strMode);
    			request.getSession().setAttribute("tableData",tableData);
    			request.setAttribute("updated","message.reverse");
    		}//end of if(iResult > 0)
    		return this.getForward(strTransactionSearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBankAccount));
    	}//end of catch(Exception exp)
    }//end of doReverse(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside TransactionSearchAction doAdd");
    		DynaActionForm frmTransDetails= (DynaActionForm)form;
    		TransactionVO transactionVO=null;
    		StringBuffer strCaption= new StringBuffer();
    		frmTransDetails.initialize(mapping);
    		transactionVO = new TransactionVO();
    		transactionVO.setAccountSeqID(TTKCommon.getWebBoardId(request));
    		transactionVO.setAccountNo(TTKCommon.getWebBoardDesc(request));
    		transactionVO.setAccountName(TTKCommon.getWebBoardDesc(request));
    		strCaption.append("Transaction Details  - ").append("Add").append(" [").
    													append(TTKCommon.getWebBoardDesc(request)).append("]");
    		DynaActionForm frmTransDetail= (DynaActionForm)FormUtils.setFormValues("frmTransDetails", transactionVO, 
    																				this, mapping, request);
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			frmTransDetail.set("transTypeID",frmTransDetails.getString("transTypeID"));
    		}
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		ArrayList<CacheObject> alCurrencyMaster	=	bankAccountManagerObject.getCurrencyMaster();
    		request.getSession().setAttribute("alCurrencyMaster", alCurrencyMaster);
    		frmTransDetail.set("alCurrencyMaster", alCurrencyMaster);
    		frmTransDetail.set("currency", "OMR");
    		frmTransDetail.set("caption",String.valueOf(strCaption));
    		request.getSession().setAttribute("frmTransDetails",frmTransDetail);
    		return this.getForward(strTransactionDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBankAccount));
    	}//end of catch(Exception exp)
    }//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doViewTransaction(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    									   HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside TransactionSearchAction doView");
    		TransactionVO transactionVO=null;
    		TableData  tableData =TTKCommon.getTableData(request);
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		StringBuffer strCaption= new StringBuffer();
    		DynaActionForm frmTransDetails= (DynaActionForm)form;
    		transactionVO = (TransactionVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
    		transactionVO = bankAccountManagerObject.getTransactionDetail(TTKCommon.getWebBoardId(request), 
    												transactionVO.getTransSeqID(), TTKCommon.getUserSeqId(request));
    		strCaption.append("Transaction Details  - ").append("Edit").append(" [").
    													 append(TTKCommon.getWebBoardDesc(request)).append("]");
    		String strTrans=transactionVO.getTransTypeID();
    		frmTransDetails.set("transTypeID",strTrans);
    		DynaActionForm frmTransDetail= (DynaActionForm)FormUtils.setFormValues("frmTransDetails", transactionVO, 
    																				this, mapping, request);
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			frmTransDetail.set("transTypeID",frmTransDetails.getString("transTypeID"));
    		}
    		frmTransDetail.set("caption",String.valueOf(strCaption));
    		
    		ArrayList<CacheObject> alCurrencyMaster	=	bankAccountManagerObject.getCurrencyMaster();
    		request.getSession().setAttribute("alCurrencyMaster", alCurrencyMaster);
    		frmTransDetail.set("alCurrencyMaster", alCurrencyMaster);
    		frmTransDetail.set("currency", transactionVO.getCurrency());
    		
    		request.getSession().setAttribute("frmTransDetails",frmTransDetail);
    		return this.getForward(strTransactionDetails, mapping, request);
    	}
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBankAccount));
    	}//end of catch(Exception exp)
    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside TransactionSearchAction doSave");
    		TransactionVO transactionVO=null;
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		StringBuffer strCaption= new StringBuffer();
    		DynaActionForm frmTransDetails= (DynaActionForm)form;
    		transactionVO = (TransactionVO)FormUtils.getFormValues(frmTransDetails, this, mapping, request);
    		transactionVO.setAccountSeqID(TTKCommon.getWebBoardId(request));
    		transactionVO.setUpdatedBy(TTKCommon.getUserSeqId(request));	//User Id
    		Long lCount=bankAccountManagerObject.saveTransaction(transactionVO);
    		if(lCount>0)
    		{
    			if(transactionVO.getTransSeqID()!=null)
    			{
    				request.setAttribute("updated","message.savedSuccessfully");
    				strCaption.append("Transaction Details  - ").append("Edit").append(" [").
    														  append(TTKCommon.getWebBoardDesc(request)).append("]");
    			}//end of if(transactionVO.getTransSeqID()!=null)
    			else
    			{
    				request.setAttribute("updated","message.addedSuccessfully");
    				strCaption.append("Transaction Details  - ").append("Add").append(" [").
    														  append(TTKCommon.getWebBoardDesc(request)).append("]");
    			}//end of else
    		}//end of if(lCount>0)
    		transactionVO = bankAccountManagerObject.getTransactionDetail(TTKCommon.getWebBoardId(request), 
    																	  lCount, TTKCommon.getUserSeqId(request));
    		DynaActionForm frmTransDetail = (DynaActionForm)FormUtils.setFormValues("frmTransDetails", transactionVO,
    																				this, mapping, request);
    		frmTransDetail.set("transTypeID",frmTransDetails.getString("transTypeID"));
    		frmTransDetail.set("caption",strCaption.toString());
    		request.setAttribute("frmTransDetails",frmTransDetail);
    		return this.getForward(strTransactionDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBankAccount));
    	}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		setLinks(request);
    		log.debug("Inside TransactionSearchAction doReset");
    		DynaActionForm frmTransDetails= (DynaActionForm)form;
    		TransactionVO transactionVO=null;
    		StringBuffer strCaption= new StringBuffer();
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		if(frmTransDetails.get("transSeqID")!=null && !frmTransDetails.get("transSeqID").equals(""))
    		{
    			strCaption.append("Transaction Details  - ").append("Edit").append(" [").
    														 append(TTKCommon.getWebBoardDesc(request)).append("]");
    			transactionVO = bankAccountManagerObject.getTransactionDetail(TTKCommon.getWebBoardId(request), 
    												   TTKCommon.getLong((String)frmTransDetails.get("transSeqID")), 
    												   TTKCommon.getUserSeqId(request));
    		}//end of if(frmTransDetails.get("transSeqID")!=null && !frmTransDetails.get("transSeqID").equals(""))
    		else
    		{
    			frmTransDetails.initialize(mapping);
    			transactionVO=new TransactionVO();
    			transactionVO.setAccountSeqID(TTKCommon.getWebBoardId(request));
    			transactionVO.setAccountNo(TTKCommon.getWebBoardDesc(request));
    			strCaption.append("Transaction Details  - ").append("Add").append(" [").
    														 append(TTKCommon.getWebBoardDesc(request)).append("]");
    		}//end of else
    		DynaActionForm frmTransDetail = (DynaActionForm)FormUtils.setFormValues("frmTransDetails", transactionVO,
    																				this, mapping, request);
    		if(frmTransDetails.get("transSeqID")!=null && !frmTransDetails.get("transSeqID").equals(""))
    		{
    			frmTransDetail.set("transTypeID",frmTransDetails.getString("transTypeID"));
    		}
    		frmTransDetail.set("caption",strCaption.toString());
    		request.setAttribute("frmTransDetails",frmTransDetail);
    		return this.getForward(strTransactionDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBankAccount));
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
    		setLinks(request);
    		log.debug("Inside TransactionSearchAction doReset");
    		DynaActionForm frmTransSearch= (DynaActionForm) form;
    		TableData  tableData =TTKCommon.getTableData(request);
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		if(tableData.getSearchData().size()>1)
    		{
    			ArrayList alTransSearch = bankAccountManagerObject.getTransactionList(tableData.getSearchData());
    			if((alTransSearch.size()>0)&&(((TransactionVO)alTransSearch.get(0)).getFloatBalance()!=null))
    			{
    				frmTransSearch.set("bankBalance",((TransactionVO)alTransSearch.get(0)).
    									getFloatBalance().toString());
    			}
    			else
    			{
    				frmTransSearch.set("bankBalance",null);
    			}
    			tableData.setData(alTransSearch, "search");
    			//set the table data object to session
    			request.getSession().setAttribute("tableData",tableData);
    		}//end of if(tableData.getSearchData().size()>1)
    		return this.getForward(strTransactionSearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBankAccount));
    	}//end of catch(Exception exp)
    }//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmTransSearch formbean which contains the search fields
     * @param request HttpServletRequest
     * @return ArrayList contains search parameters
     * @throws TTKException
     */

    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmTransSearch,HttpServletRequest request) 
    throws TTKException
    {
    	//build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(TTKCommon.getWebBoardId(request));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmTransSearch.get("transactionNbr")));
        alSearchParams.add(frmTransSearch.get("transactionType"));
        alSearchParams.add(frmTransSearch.get("startDate"));
        alSearchParams.add(frmTransSearch.get("endDate"));
        alSearchParams.add(TTKCommon.getUserSeqId(request));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmTransSearch,HttpServletRequest request)

	/**
     * Returns a string which contains the | separated sequence id's
     * @param request HttpServletRequest object which contains information about the selected check boxes
     * @param tableData TableData object which contains the value objects
     * @return ArrayList which contains the | separated sequence id's
     * @throws TTKException If any run time Excepton occures
     */
	private String populateFloatTransactionSeqIDs(HttpServletRequest request, TableData tableData)
	throws TTKException
    {
        String[] strChk = request.getParameterValues("chkopt");
        StringBuffer sbfTransSeqID = new StringBuffer();
        if(strChk!=null&&strChk.length!=0)
        {
            for(int i=0; i<strChk.length;i++)
            {
                if(strChk[i]!=null)
                {
                	log.debug("Inside if(strChk[i]!=null)");
                    //extract the sequence id from the value object
                    if(i == 0)
                    {
                    	log.debug("Inside if(i == 0)");
                    	sbfTransSeqID.append("|").append(String.valueOf(((TransactionVO)tableData.getData().
                    											get(Integer.parseInt(strChk[i]))).getTransSeqID()));
                    }//end of if(i == 0)
                    else
                    {
                    	log.debug("Inside if(i == 0) else block");
                    	sbfTransSeqID=sbfTransSeqID.append("|").append(String.valueOf(((TransactionVO)tableData
                    								.getData().get(Integer.parseInt(strChk[i]))).getTransSeqID()));
                    }//end of else
                }//end of if(strChk[i]!=null)
            }//end of for(int i=0; i<strChk.length;i++)
        }//end of if(strChk!=null&&strChk.length!=0)
        sbfTransSeqID.append("|");
        return sbfTransSeqID.toString();
    }//end of populateFloatTransactionSeqIDs(HttpServletRequest request, TableData tableData)

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
            }//end of if(bankAccountManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "bankaccount");
        }//end of catch
        return bankAccountManager;
    }//end getBankAccountManagerObject()
}// end of TransactionSearchAction.java