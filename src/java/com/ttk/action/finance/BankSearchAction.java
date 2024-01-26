/**
 * @ (#) BankSearchAction.java June 12, 2006
 * Project 		: TTK HealthCare Services
 * File 		: BankSearchAction.java
 * Author 		: Arun K M
 * Company 		: Span Systems Corporation
 * Date Created : June 12, 2006
 *
 * @author 		: Arun K M
 * Modified by 	:
 * Modified date :
 * Reason 		:
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
import com.ttk.dto.finance.BankVO;

/**
 * This class is used for searching of List of Banks.
 */
public class BankSearchAction extends TTKAction
{
	private static Logger log = Logger.getLogger(BankSearchAction.class );
	//Modes of Bank List
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	//Action mapping forwards
    private static final String stracctbanklist="acctbanklist";
    private static final String strBankacctdetail="bankacctdetail";
    private static final String strBank="bank";
	
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
    		log.debug("Inside BankSearchAction doDefault"); 
    		TableData  tableData =TTKCommon.getTableData(request);
    		DynaActionForm frmBankList = (DynaActionForm)form;
    		StringBuffer strCaption=new StringBuffer();
    		//create new table data object
    		tableData = new TableData();
    		//create the required grid table
    		tableData.createTableInfo("BankListTable",new ArrayList());
    		request.getSession().setAttribute("tableData",tableData);
    		((DynaActionForm)form).initialize(mapping);		//reset the form data
    		frmBankList.set("caption",strCaption.toString());
    		request.getSession().setAttribute("searchparam", null);
    		return this.getForward(stracctbanklist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
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
    		log.debug("Inside BankSearchAction doSearch"); 
    		TableData  tableData =TTKCommon.getTableData(request);
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
    		//if the page number or sort id is clicked
    		if(!strPageID.equals("") || !strSortID.equals(""))
    		{
    			if(!strPageID.equals(""))
    			{
    				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    				return (mapping.findForward(stracctbanklist));
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
    			tableData.createTableInfo("BankListTable",null);
    			//fetch the data from the data access layer and set the data to table object
    			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
    			tableData.modifySearchData("search");
    		}//end of else
    		ArrayList alBankList= bankAccountManagerObject.getBankList(tableData.getSearchData());
    		tableData.setData(alBankList, "search");
    		//set the table data object to session
    		request.getSession().setAttribute("tableData",tableData);
    		//finally return to the grid screen
    		return this.getForward(stracctbanklist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
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
    		log.debug("Inside BankSearchAction doForward"); 
    		TableData  tableData =TTKCommon.getTableData(request);
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		tableData.modifySearchData(strForward);//modify the search data
    		ArrayList alBankList = bankAccountManagerObject.getBankList(tableData.getSearchData());
    		tableData.setData(alBankList, strForward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
    		return this.getForward(stracctbanklist, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
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
    		log.debug("Inside BankSearchAction doBackward"); 
    		TableData  tableData =TTKCommon.getTableData(request);
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		ArrayList alBankList = bankAccountManagerObject.getBankList(tableData.getSearchData());
    		tableData.setData(alBankList, strBackward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
    		return this.getForward(stracctbanklist, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
    	}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doSelectBank(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside BankSearchAction doSelectBank"); 
    		BankVO bankVO=null;
    		TableData  tableData =TTKCommon.getTableData(request);
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			bankVO = (BankVO)tableData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
    		}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		else
    		{
    			TTKException expTTK = new TTKException();
    			expTTK.setMessage("error.bankname.required");
    			throw expTTK;
    		}//end of else
    		DynaActionForm frmBankAcctGeneral=(DynaActionForm)request.getSession().getAttribute("frmBankAcctGeneral");
    		frmBankAcctGeneral.set("bankSeqID",bankVO.getBankSeqID().toString());
    		frmBankAcctGeneral.set("bankName",bankVO.getBankName());
    		frmBankAcctGeneral.set("officeTypeDesc",bankVO.getOfficeTypeDesc());
    		frmBankAcctGeneral.set("frmChanged","changed");
    		//}//end of else if(strActiveSubLink.equals("Bank Account"))
    		return this.getForward(strBankacctdetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
    	}//end of catch(Exception exp)
    }//end of doSelectBank(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside BankSearchAction doClose"); 
    		return this.getForward(strBankacctdetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
    	}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
	/**
     * Returns the BankManager session object for invoking methods on it.
     * @return BankManager session object which can be used for method invocation
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
            }//end of if(bankManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "bank");
        }//end of catch
        return bankAccountManager;
    }//end getBankManagerObject()

    /**
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmBankList formbean which contains the search fields
     * @param request HttpServletRequest
     * @return ArrayList contains search parameters
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmBankList,HttpServletRequest request)
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmBankList.getString("sBankName")));
        alSearchParams.add((String)frmBankList.get("sOfficeType"));
        alSearchParams.add((String)frmBankList.get("sCity"));
        alSearchParams.add(TTKCommon.getUserSeqId(request));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmBankList,HttpServletRequest request)
}// end of BankSearchAction.java