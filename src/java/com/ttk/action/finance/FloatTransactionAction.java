/**
 * @ (#) FloatTransactionAction.java June 10th, 2006
 * Project      : TTK HealthCare Services
 * File         : FloatTransactionAction.java
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : June 10th, 2006
 *
 * @author       :  Krupa J
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
import com.ttk.business.finance.FloatAccountManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.finance.TransactionVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of List of Float Transaction.
 * This class also provides option for Adding and Updation of transaction.
 */

public class FloatTransactionAction extends TTKAction
{
	private static final Logger log = Logger.getLogger( FloatTransactionAction.class );
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	//forwards
	private static final String strTransList="translist";
	private static final String strTransDetails="transdetails";
	private static final String strFloatAcct ="floataccount";
	
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
			log.debug("Inside FloatTransactionAction doDefault");
			if(TTKCommon.getWebBoardId(request) == null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.floatno.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request) == null)
			TableData  tableData =TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("FloatTransactionTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			request.getSession().setAttribute("searchparam", null);
			return this.getForward(strTransList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,
    									  HttpServletRequest request,HttpServletResponse response) throws Exception{
    	log.debug("Inside FloatTransactionAction doChangeWebBoard");
    	return doDefault(mapping,form,request,response);
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside FloatTransactionAction doSearch");
    		DynaActionForm frmTransaction=(DynaActionForm)form;
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
    		//if the page number or sort id is clicked
    		if(!strPageID.equals("") || !strSortID.equals(""))
    		{
    			if(!strPageID.equals(""))
    			{
    				log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
    				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    				return (mapping.findForward(strTransList));
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
    			tableData.createTableInfo("FloatTransactionTable",null);
    			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
    			tableData.modifySearchData("search");
    		}//end of else
    		ArrayList alTransaction= floatAccountManagerObject.getFloatTransactionList(tableData.getSearchData());
    		if((alTransaction.size()>0)&&(((TransactionVO)alTransaction.get(0)).getFloatBalance()!=null))
    		{
    			frmTransaction.set("floatBalance",((TransactionVO)alTransaction.get(0)).getFloatBalance().toString());
    		}
    		else
    		{
    			frmTransaction.set("floatBalance",null);
    		}
    		tableData.setData(alTransaction, "search");
    		//set the table data object to session
    		request.getSession().setAttribute("tableData",tableData);
    		//finally return to the grid screen
    		return this.getForward(strTransList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
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
    		log.debug("Inside FloatTransactionAction doForward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		tableData.modifySearchData(strForward);//modify the search data
    		ArrayList alTransaction = floatAccountManagerObject.getFloatTransactionList(tableData.getSearchData());
    		tableData.setData(alTransaction, strForward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
    		return this.getForward(strTransList, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
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
    		log.debug("Inside FloatTransactionAction doBackward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		ArrayList alTransaction = floatAccountManagerObject.getFloatTransactionList(tableData.getSearchData());
    		tableData.setData(alTransaction, strBackward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
    		return this.getForward(strTransList, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside FloatTransactionAction doBackward");
    		TransactionVO transactionVO=null;
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		StringBuffer strCaption= new StringBuffer();
    		String strAddmode = "";
    		DynaActionForm frmFloatTrans= (DynaActionForm)form;
    		frmFloatTrans.initialize(mapping);
    		transactionVO = new TransactionVO();
    		transactionVO.setAccountSeqID(TTKCommon.getWebBoardId(request));
    		strCaption.append("Transaction Details  - ").append("Add").append(" [").
    				   append(TTKCommon.getWebBoardDesc(request)).append("]");
    		strAddmode = "ADD";
    		frmFloatTrans= (DynaActionForm)FormUtils.setFormValues("frmFloatTrans", transactionVO, 
    																this, mapping, request);
    		frmFloatTrans.set("floatTypeID",floatAccountManagerObject.getFloatType(TTKCommon.getWebBoardId(request)));
    		frmFloatTrans.set("caption",String.valueOf(strCaption));
    		frmFloatTrans.set("addmode",strAddmode);
    		
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		ArrayList<CacheObject> alCurrencyMaster	=	bankAccountManagerObject.getCurrencyMaster();
    		request.getSession().setAttribute("alCurrencyMaster", alCurrencyMaster);
    		frmFloatTrans.set("alCurrencyMaster", alCurrencyMaster);
    		frmFloatTrans.set("currency", "OMR");
    		
    		request.getSession().setAttribute("frmFloatTrans",frmFloatTrans);
    		return this.getForward(strTransDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
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
    public ActionForward doViewTransaction(ActionMapping mapping,ActionForm form,
    									   HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside FloatTransactionAction doViewTransaction");
    		TransactionVO transactionVO=null;
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		StringBuffer strCaption= new StringBuffer();
    		String strAddmode = "";
    		DynaActionForm frmFloatTrans= (DynaActionForm)form;
    		transactionVO = (TransactionVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
    		transactionVO = floatAccountManagerObject.getFloatTransactionDetail( transactionVO.getTransSeqID(),
    											TTKCommon.getWebBoardId(request), TTKCommon.getUserSeqId(request));
    		strCaption.append("Transaction Details  - ").append("Edit").append(" [").
    										             append(TTKCommon.getWebBoardDesc(request)).append("]");
    		strAddmode = "EDIT";
    		frmFloatTrans= (DynaActionForm)FormUtils.setFormValues("frmFloatTrans", transactionVO, this, 
    																mapping, request);


    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		ArrayList<CacheObject> alCurrencyMaster	=	bankAccountManagerObject.getCurrencyMaster();
    		request.getSession().setAttribute("alCurrencyMaster", alCurrencyMaster);
    		frmFloatTrans.set("alCurrencyMaster", alCurrencyMaster);
    		frmFloatTrans.set("currency", transactionVO.getCurrency());
    		
    		frmFloatTrans.set("caption",String.valueOf(strCaption));
    		frmFloatTrans.set("addmode",strAddmode);
    		request.getSession().setAttribute("frmFloatTrans",frmFloatTrans);
    		return this.getForward(strTransDetails, mapping, request);
    	}
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doViewTransaction(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside FloatTransactionAction doSave");
    		TransactionVO transactionVO=null;
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		StringBuffer strCaption= new StringBuffer();
    		String strAddmode = "";
    		DynaActionForm frmFloatTrans= (DynaActionForm)form;
    		transactionVO = (TransactionVO)FormUtils.getFormValues(frmFloatTrans, this, mapping, request);
    		transactionVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id
    		long lngcount=floatAccountManagerObject.saveFloatTransaction(transactionVO);
    		if(lngcount>0)
    		{
    			if(!(TTKCommon.checkNull((String)frmFloatTrans.get("transSeqID")).equals("")))
    				//if(transactionVO.getTransSeqID()!=null)
    			{
    				request.setAttribute("updated","message.savedSuccessfully");
    				strCaption.append("Transaction Details  - ").append("Edit").append(" [").
    						   append(TTKCommon.getWebBoardDesc(request)).append("]");
    				transactionVO = floatAccountManagerObject.getFloatTransactionDetail( transactionVO.getTransSeqID(),
    										     TTKCommon.getWebBoardId(request), TTKCommon.getUserSeqId(request));
    				strAddmode = "EDIT";
    			}//end of if(transactionVO.getTransSeqId()!=null)
    			else
    			{
    				request.setAttribute("updated","message.addedSuccessfully");
    				strCaption.append("Transaction Details  - ").append("Add").append(" [").
    														append(TTKCommon.getWebBoardDesc(request)).append("]");
    				transactionVO.setAccountSeqID(TTKCommon.getWebBoardId(request));
    				transactionVO.setTransSeqID(lngcount);
    				strAddmode = "ADD";
    			}//end of else
    		}//end of if(icount>0)
    				transactionVO = floatAccountManagerObject.getFloatTransactionDetail(transactionVO.getTransSeqID(),
    											TTKCommon.getWebBoardId(request), TTKCommon.getUserSeqId(request));
    				transactionVO.setAccountSeqID(TTKCommon.getWebBoardId(request));
    		frmFloatTrans = (DynaActionForm)FormUtils.setFormValues("frmFloatTrans", transactionVO, this,
    				mapping, request);
    		frmFloatTrans.set("caption",strCaption.toString());
    		frmFloatTrans.set("addmode",strAddmode);
    		frmFloatTrans.set("currency", transactionVO.getCurrency());
    		request.getSession().setAttribute("frmFloatTrans",frmFloatTrans);
    		return this.getForward(strTransDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
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
    		log.debug("Inside FloatTransactionAction doReset");
    		DynaActionForm frmFloatTrans= (DynaActionForm)form;
    		TransactionVO transactionVO=null;
    		StringBuffer strCaption= new StringBuffer();
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		String strAddmode = "";
    		transactionVO=new TransactionVO();
    		if(frmFloatTrans.get("transSeqID")!=null && !frmFloatTrans.get("transSeqID").equals(""))
    		{
    			strCaption.append("Transaction Details  - ").append("Edit").append(" [").
				 				append(TTKCommon.getWebBoardDesc(request)).append("]");
    			transactionVO = floatAccountManagerObject.getFloatTransactionDetail(TTKCommon.getLong((String)frmFloatTrans.get("transSeqID")),
								TTKCommon.getWebBoardId(request), TTKCommon.getUserSeqId(request));
    			strAddmode = "EDIT";
    		}//end of if(frmTransDetails.get("transSeqID")!=null && !frmTransDetails.get("transSeqID").equals(""))
    		else
    		{
    			frmFloatTrans.initialize(mapping);
    			transactionVO=new TransactionVO();
    			strCaption.append("Transaction Details  - ").append("Add").append(" [").
				 append(TTKCommon.getWebBoardDesc(request)).append("]");
    			strAddmode = "ADD";
    		}//end of else
    		transactionVO.setAccountSeqID(TTKCommon.getWebBoardId(request));
    		frmFloatTrans = (DynaActionForm)FormUtils.setFormValues("frmFloatTrans", transactionVO, this,mapping,request);
    		frmFloatTrans.set("caption",strCaption.toString());
    		frmFloatTrans.set("addmode",strAddmode);
    		request.getSession().setAttribute("frmFloatTrans",frmFloatTrans);
    		return this.getForward(strTransDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to reverse the transaction
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doReverseTransaction(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside FloatTransactionAction doReverseTransaction");
    		DynaActionForm frmTransaction=(DynaActionForm)form;
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		TableData  tableData =TTKCommon.getTableData(request);
    		String strMode=TTKCommon.checkNull(request.getParameter("mode"));
    		ArrayList <Object>alSeqIDs=new ArrayList<Object>();
    		alSeqIDs.add(TTKCommon.getWebBoardId(request));
    		alSeqIDs.add(populateFloatTransactionSeqIDs(
    										request, (TableData)request.getSession().getAttribute("tableData")));
    		alSeqIDs.add(TTKCommon.getUserSeqId(request));
    		int iResult=floatAccountManagerObject.reverseTransaction(alSeqIDs);
    		if(iResult > 0)
    		{
    			ArrayList alTransaction=null;
    			if(iResult == tableData.getData().size())
    			{
    				tableData.modifySearchData(strMode);//modify the search data
    				int iStartRowCount = Integer.parseInt((
    									String)tableData.getSearchData().get(tableData.getSearchData().size()-2));
    				if(iStartRowCount > 0)
    				{
    					alTransaction= floatAccountManagerObject.getFloatTransactionList(tableData.getSearchData());
    				}//end of if(iStartRowCount > 0)
    			}//end of if(iResult == tableData.getData().size())
    			else
    			{
    				alTransaction= floatAccountManagerObject.getFloatTransactionList(tableData.getSearchData());
    			}//end of else
    			if((alTransaction.size()>0)&&(((TransactionVO)alTransaction.get(0)).getFloatBalance()!=null))
    			{
    				frmTransaction.set("floatBalance",(
    											(TransactionVO)alTransaction.get(0)).getFloatBalance().toString());
    			}
    			else
    			{
    				frmTransaction.set("floatBalance",null);
    			}
    			tableData.setData(alTransaction, strMode);
    			request.getSession().setAttribute("tableData",tableData);
    			request.setAttribute("updated","message.reverse");
    		}//end of if(iResult > 0)
    		return this.getForward(strTransList, mapping, request);
    		
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doReverseTransaction(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    																					//HttpServletResponse response)
    
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
    public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside FloatTransactionAction doClose");
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		DynaActionForm frmTransaction=(DynaActionForm)form;
    		if(tableData.getSearchData().size()>1)
    		{
    			ArrayList alTransaction = floatAccountManagerObject.getFloatTransactionList(tableData.getSearchData());
    			if((alTransaction.size()>0)&&(((TransactionVO)alTransaction.get(0)).getFloatBalance()!=null))
    			{
    				frmTransaction.set("floatBalance",((TransactionVO)alTransaction.get(0)).getFloatBalance().toString());
    			}
    			else
    			{
    				frmTransaction.set("floatBalance",null);
    			}
    			tableData.setData(alTransaction, "search");
    			//set the table data object to session
    			request.getSession().setAttribute("tableData",tableData);
    			
    		}//end of if(tableData.getSearchData().size()>1)
    		return this.getForward(strTransList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmTransaction DynaActionForm
	 * @return ArrayList contains search parameters
	 */

	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmTransaction,HttpServletRequest request)throws TTKException
	{
		// build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.getWebBoardId(request));
		alSearchParams.add(TTKCommon.replaceSingleQots((String) frmTransaction.get("transactionNbr")));
		alSearchParams.add(((String) frmTransaction.get("transactionType")));
		alSearchParams.add(((String) frmTransaction.get("startDate")));
		alSearchParams.add(((String) frmTransaction.get("endDate")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}// end of populateSearchCriteria(DynaActionForm frmTransaction,HttpServletRequest request)


	/**
     * Returns a string which contains the | separated sequence id's
     * @param request HttpServletRequest object which contains information about the selected check boxes
     * @param tableData TableData object which contains the value objects
     * @return ArrayList which contains the | separated sequence id's
     * @throws TTKException If any run time Excepton occures
     */
	private String populateFloatTransactionSeqIDs(HttpServletRequest request, TableData tableData)throws TTKException
    {
        String[] strChk = request.getParameterValues("chkopt");
        StringBuffer sbfTransSeqID = new StringBuffer();
        if(strChk!=null&&strChk.length!=0)
        {

            for(int i=0; i<strChk.length;i++)
            {
                if(strChk[i]!=null)
                {
                	//extract the sequence id from the value object
                    if(i == 0)
                    {
                    	sbfTransSeqID.append("|").append(String.valueOf(((TransactionVO)
                    						tableData.getData().get(Integer.parseInt(strChk[i]))).getTransSeqID()));
                    }//end of if(i == 0)
                    else
                    {
                    	sbfTransSeqID=sbfTransSeqID.append("|").append(String.valueOf(((TransactionVO)
                    						tableData.getData().get(Integer.parseInt(strChk[i]))).getTransSeqID()));
                    }//end of else
                }//end of if(strChk[i]!=null)
            }//end of for(int i=0; i<strChk.length;i++)
        }//end of if(strChk!=null&&strChk.length!=0)
        sbfTransSeqID.append("|");
        return sbfTransSeqID.toString();
    }//end of populateSeqIDs(HttpServletRequest request, TableData otherPolicyData)

	/**
	 * Returns the ContactManager session object for invoking methods on it.
	 * @return ContactManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private FloatAccountManager getfloatAccountManagerObject() throws TTKException
	{
		FloatAccountManager floatAccountManager = null;
		try
		{
			if(floatAccountManager == null)
			{
				InitialContext ctx = new InitialContext();
				floatAccountManager = (FloatAccountManager) ctx.lookup("java:global/TTKServices/business.ejb3/FloatAccountManagerBean!com.ttk.business.finance.FloatAccountManager");
			}//end of if(contactManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "floataccount");
		}//end of catch
		return floatAccountManager;
	}//end getfloatAccountManagerObject()
	
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

}//end of FloatTransactionAction class
