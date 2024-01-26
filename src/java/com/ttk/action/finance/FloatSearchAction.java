/**
 * @ (#) FloatSearchAction.java June 13,2006
 * Project       : TTK HealthCare Services
 * File          : FloatSearchAction.java
 * Author        : Arun K.M
 * Company       : Span Systems Corporation
 * Date Created  : June 13, 2006
 *
 * @author       : Arun K.M
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
import com.ttk.business.finance.FloatAccountManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.finance.FloatAccountVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

	/**
	 * This class is used for searching the List of Float Accounts.
	 * This class also provides option for deletion of Float Accounts.
	 */
	public class FloatSearchAction extends TTKAction {
	private static Logger log = Logger.getLogger( FloatSearchAction.class );
	//   Modes.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";
    private static final String strDeleteList="DeleteList";
    //  Action mapping forwards.
    private static final String strfloatlist="floatlist";
    //  Exception Message Identifier
    private static final String strFloatAcct="floataccount";

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
    		log.debug("Inside FloatSearchAction doDefault");
    		TableData  tableData =TTKCommon.getTableData(request);
    		DynaActionForm frmFloatList = (DynaActionForm)form;
    		String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
    															getAttribute("UserSecurityProfile")).getBranchID());
    		//create new table data object
    		tableData = new TableData();
    		//create the required grid table
    		tableData.createTableInfo("FloatListTable",new ArrayList());
    		request.getSession().setAttribute("tableData",tableData);
    		((DynaActionForm)form).initialize(mapping);//reset the form data
    		request.getSession().setAttribute("alSubStatus",null);
    		frmFloatList.set("sTTKBranch",strDefaultBranchID);
    		return this.getForward(strfloatlist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strFloatAcct));
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
    		log.debug("Inside FloatSearchAction doSearch");
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
    		if(!strPageID.equals("") || !strSortID.equals(""))
    		{
    			if(!strPageID.equals(""))
    			{
    				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    				return (mapping.findForward(strfloatlist));
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
    			tableData.createTableInfo("FloatListTable",null);
    			//fetch the data from the data access layer and set the data to table object
    			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
    			tableData.modifySearchData("search");
    		}//end of else
    		ArrayList alFloat=floatAccountManagerObject.getFloatAccountList(tableData.getSearchData());
    		tableData.setData(alFloat, "search");
    		request.getSession().setAttribute("tableData",tableData);
    		//finally return to the grid screen
    		return this.getForward(strfloatlist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is called from the struts framework.
     * This method is used to delete a record.
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
    		log.debug("Inside FloatSearchAction doDeleteList");
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		TableData  tableData =TTKCommon.getTableData(request);
    		StringBuffer sbfDeleteId = new StringBuffer("|");
    		int iCount=0;
    		//populate the delete string which contains the sequence id's to be deleted
    		sbfDeleteId.append(populateDeleteId(request,(TableData)request.getSession().getAttribute("tableData")));
    		ArrayList<Object> alDeleteList = new ArrayList<Object>();
    		alDeleteList.add(sbfDeleteId.toString());
    		alDeleteList.add(TTKCommon.getUserSeqId(request));
    		//delete the selected FloatAccount based on the flow
    		iCount=floatAccountManagerObject.deleteFloatAccount(alDeleteList);
    		//refresh the grid with search data in session
    		ArrayList alBankList = null;
    		//fetch the data from previous set of rowcounts, if all the records are deleted for the current set of
    																					//search criteria
    		if(iCount == tableData.getData().size())
    		{
    			tableData.modifySearchData(strDeleteList);//modify the search data
    			int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().
    												   get(tableData.getSearchData().size()-2));
    			if(iStartRowCount > 0)
    			{
    				alBankList=floatAccountManagerObject.getFloatAccountList(tableData.getSearchData());
    			}//end of if(iStartRowCount > 0)
    		}//end if(iCount == tableData.getData().size())
    		else
    		{
    			alBankList=floatAccountManagerObject.getFloatAccountList(tableData.getSearchData());
    		}//end of else
    		tableData.setData(alBankList, strDeleteList);
    		if(iCount>0)
    		{
    			//delete the Float account from the web board if any
    			request.setAttribute("cacheId",sbfDeleteId.append("|").toString());
    			TTKCommon.deleteWebBoardId(request);
    		}//end of if(iCount>0)
    		request.getSession().setAttribute("tableData",tableData);
    		return this.getForward(strfloatlist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside FloatSearchAction doForward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		tableData.modifySearchData(strForward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alFloat = floatAccountManagerObject.getFloatAccountList(tableData.getSearchData());
    		tableData.setData(alFloat, strForward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
    		return this.getForward(strfloatlist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strFloatAcct));
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
    		log.debug("Inside FloatSearchAction doBackward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alFloat = floatAccountManagerObject.getFloatAccountList(tableData.getSearchData());
    		tableData.setData(alFloat, strBackward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
    		return this.getForward(strfloatlist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strFloatAcct));
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
    public ActionForward doViewFloatAccount(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside FloatSearchAction doViewFloatAccount");
    		FloatAccountVO floatAccountVO=null;
    		TableData  tableData =TTKCommon.getTableData(request);
    		//create a new FloatAccount object
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			floatAccountVO = (FloatAccountVO)tableData.getRowInfo(Integer.parseInt((String)
    																  request.getParameter("rownum")));
    			//add the selected item to the web board and make it as default selected
    			this.addToWebBoard(floatAccountVO, request);
    		}//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		return mapping.findForward("floataccdetails");
    	}
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doViewFloatAccount(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		setLinks(request);
    		log.debug("Inside FloatSearchAction doCopyToWebBoard");
    		this.populateWebBoard(request);
    		return this.getForward(strfloatlist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
	/**
	 * Returns the FloatAccountManager session object for invoking methods on it.
	 * @return FloatAccountManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private FloatAccountManager getFloatAccountManagerObject() throws TTKException
	{
		FloatAccountManager floatAccountManager = null;
		try
		{
			if(floatAccountManager == null)
			{
				InitialContext ctx = new InitialContext();
				floatAccountManager = (FloatAccountManager) ctx.lookup("java:global/TTKServices/business.ejb3/FloatAccountManagerBean!com.ttk.business.finance.FloatAccountManager");
			}//end if(hospManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strFloatAcct);
		}//end of catch
		return floatAccountManager;
	}//end getFloatAccountList()

	/**
	 * Returns the ArrayList which contains the populated search criteria elements
	 * @param  frmFloatlist DynaActionForm will contains the values of corresponding fields
	 * @param request HttpServletRequest object which contains the search parameter that is built
	 * @return alSearchParams ArrayList
	 * @throws TTKException
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmFloatlist,HttpServletRequest request) throws TTKException
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmFloatlist.getString("sFloatNo")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmFloatlist.getString("sFloatName")));
        alSearchParams.add((String)frmFloatlist.get("sFloatType"));
        alSearchParams.add((String)frmFloatlist.get("sStatus"));
        alSearchParams.add(TTKCommon.getLong((String)frmFloatlist.get("sTTKBranch")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmFloatlist.getString("sBankName")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmFloatlist.getString("sBankAccountNo")));
        alSearchParams.add(TTKCommon.getLong((String)frmFloatlist.get("sInsuranceCompany")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmFloatlist.getString("sCompanyCode")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmFloatlist.getString("sClaimSettleNumber"))); 
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmFloatlist.getString("productAuthority")));
        alSearchParams.add(TTKCommon.getUserSeqId(request));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmFloatlist)

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
				cacheObject.setCacheId(""+((FloatAccountVO)tableData.getData().get(Integer.parseInt(strChk[i]))).getFloatAcctSeqID());
				cacheObject.setCacheDesc(((FloatAccountVO)tableData.getData().get(Integer.parseInt(strChk[i]))).getFloatAcctName());
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
	 * @param floatAccountVO FloatAccountVO object which contain the information of FloatAccount
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 */
	private void addToWebBoard(FloatAccountVO floatAccountVO, HttpServletRequest request)
	{
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		CacheObject cacheObject = new CacheObject();
		cacheObject.setCacheId(""+floatAccountVO.getFloatAcctSeqID());
		cacheObject.setCacheDesc(floatAccountVO.getFloatAcctName());
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		alCacheObject.add(cacheObject);
		//if the object(s) are added to the web board, set the current web board id
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
		//set webboard invoked as true to avoid setting the weboard id twice in same request
		request.setAttribute("webboardinvoked", "true");
	}//end of addToWebBoard(FloatAccountVO floatAccountVO, HttpServletRequest request)



	/**
	 * This method returns a string which contains the comma separated sequence id's to be deleted,
	 * in Bank flow pipe seperated Bank seq ids  are sent to the called method
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param BankListData TableData object which contains the value objects
	 * @return String which contains the comma separated sequence id's to be deleted
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
	                    sbfDeleteId.append(String.valueOf(((FloatAccountVO)tableData.getData().
	                    								    get(Integer.parseInt(strChk[i]))).getFloatAcctSeqID()));
	                }// end of if(i == 0)
	                else
	                {
	                    sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((FloatAccountVO)
	                    				tableData.getData().get(Integer.parseInt(strChk[i]))).getFloatAcctSeqID()));
	                }// end of else
	            }//end of if(strChk[i]!=null)
	        }//end of for(int i=0; i<strChk.length;i++)
	        sbfDeleteId.append("|");
	    }//end of if(strChk!=null&&strChk.length!=0)
	    return sbfDeleteId.toString();
	}//end of FloatAccountDeleteId(HttpServletRequest request, TableData tableData)

}//end of class FloatSearchAction