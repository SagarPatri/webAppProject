/**
 * @ (#) AccountsHistoryAction.javaNov 26, 2005
 * Project      : TTK HealthCare Services
 * File         : AccountsHistoryAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Nov 26, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.empanelment;

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
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.AccountDetailVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of  history information of empanelment fees.
 * This class also provides option for deletion of bills.
 */

public class AccountsHistoryAction extends TTKAction
{
    private static Logger log = Logger.getLogger( AccountsHistoryAction.class );    
    
    //for setting modes
    private static final String strForward ="Forward";
	private static final String strBackward ="Backward";
		
    //forward links
	private static final String strAccountsHistory="accountshistory";
	private static final String strEditAccountsHisory="editaccountshistory";
	
	//Exception Message Identifier
    private static final String strAccountsHistoryError="accounthistory";
		
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
    		log.debug("Inside doDefault method of AccountsHistroyAction");
    		setLinks(request);
    		DynaActionForm frmAccountsHistory=(DynaActionForm)form;
    		StringBuffer sbfCaption= new StringBuffer();
			TableData  tableData =TTKCommon.getTableData(request);
        	//create new table data object
            tableData = new TableData();
            //create the required grid table
            tableData.createTableInfo("AccountsHistoryTable",new ArrayList());
            request.getSession().setAttribute("tableData",tableData);// Setting the tableData to session
            ((DynaActionForm)form).initialize(mapping);//reset the form data
           	sbfCaption.append("Empanelment Fee Details -").append("[").append(TTKCommon.getWebBoardDesc(request)).
           	append("]");
        	frmAccountsHistory.set("caption",sbfCaption.toString());
           return this.getForward(strAccountsHistory, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsHistoryError));
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
			String strType="";
			StringBuffer sbfCaption= new StringBuffer();
			TableData  tableData =TTKCommon.getTableData(request);
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			Long lngHospitalSeqId=TTKCommon.getWebBoardId(request);//get the web board id
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
            {
                ((DynaActionForm)form).initialize(mapping);//reset the form data
            }// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
            ArrayList alHistoryList=null;
            String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
            DynaActionForm frmAccountsHistory = (DynaActionForm)form;
            strType=frmAccountsHistory.getString("empHistory");//getting hte value of the type from dynaform and set it to a string variable 
            if(!strPageID.equals("") || !strSortID.equals(""))
            {
                if(!strPageID.equals(""))
                {    
                    tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
                    return this.getForward(strAccountsHistory, mapping, request);
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
                tableData.createTableInfo("AccountsHistoryTable",null);
                tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,lngHospitalSeqId));
                tableData.modifySearchData("search");
            }//end of else
           
            if(strType.equals("EEF"))//checks the type is equal to empanelment fee(EEF)
            {
            	alHistoryList = hospitalObject.getEmpanelmentFeeHistoryList(tableData.getSearchData());
            	sbfCaption.append("Empanelment Fee Details -").append("[").append(TTKCommon.getWebBoardDesc(request)).
            	append("]");
            }// end of if(strType.equals("EEF"))
            else
            {
            	alHistoryList = hospitalObject.getBankGuaranteeHistoryList(tableData.getSearchData());
            	sbfCaption.append("Bank Guarantee Details -").append("[").append(TTKCommon.getWebBoardDesc(request)).
            	append("]");
            }//end of else
           
            tableData.setData(alHistoryList,"search");
            //set the table data object to session
            request.getSession().setAttribute("tableData",tableData);
            frmAccountsHistory.set("caption",sbfCaption.toString());
            //finally return to the grid screen
            return this.getForward(strAccountsHistory, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsHistoryError));
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
			String strType=null;
			StringBuffer sbfCaption= new StringBuffer();
			TableData  tableData =TTKCommon.getTableData(request);
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			DynaActionForm frmAccountsHistory = (DynaActionForm)form;
        	strType=frmAccountsHistory.getString("empHistory");
        	ArrayList alHistoryList = null;
        	tableData.modifySearchData(strForward);
        	if(strType.equals("EEF"))//checks the type is equal to empanelment fee(EEF)
        	{            
        		alHistoryList =hospitalObject.getEmpanelmentFeeHistoryList(tableData.getSearchData());
        		sbfCaption.append("Empanelment Fee Details -").append("[").append(TTKCommon.getWebBoardDesc(request)).
        		append("]");
            	frmAccountsHistory.set("caption",sbfCaption.toString());
        	}// end of if(strType.equals("EEF"))
        	else
        	{
        		alHistoryList =hospitalObject.getBankGuaranteeHistoryList(tableData.getSearchData());
        		sbfCaption.append("Bank Guarantee Details -").append("[").append(TTKCommon.getWebBoardDesc(request)).
        		append("]");
        		frmAccountsHistory.set("caption",sbfCaption.toString());
        	}//end of else
        	tableData.setData(alHistoryList, strForward);
        	request.getSession().setAttribute("tableData",tableData);//set the table data object to session
        	//finally return to the grid screen
            return this.getForward(strAccountsHistory, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsHistoryError));
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
			String strType=null;
			StringBuffer sbfCaption= new StringBuffer();
			TableData  tableData =TTKCommon.getTableData(request);
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			DynaActionForm frmAccountsHistory = (DynaActionForm)form;
        	strType=frmAccountsHistory.getString("empHistory");
        	ArrayList alHistoryList = null;
        	tableData.modifySearchData(strBackward);
        	if(strType.equals("EEF"))//checks the type is equal to empanelment fee(EEF)
        	{            
        		alHistoryList =hospitalObject.getEmpanelmentFeeHistoryList(tableData.getSearchData());
        		sbfCaption.append("Empanelment Fee Details -").append("[").append(TTKCommon.getWebBoardDesc(request)).
        																   append("]");
            	frmAccountsHistory.set("caption",sbfCaption.toString());
        	}// end of if(strType.equals("EEF"))
        	else
        	{
        		alHistoryList =hospitalObject.getBankGuaranteeHistoryList(tableData.getSearchData());
        		sbfCaption.append("Bank Guarantee Details -").append("[").append(TTKCommon.getWebBoardDesc(request)).
        																  append("]");
        		frmAccountsHistory.set("caption",sbfCaption.toString());
        	}//end of else
        	tableData.setData(alHistoryList, strBackward);
        	request.getSession().setAttribute("tableData",tableData);//set the table data object to session
        	//finally return to the grid screen
            return this.getForward(strAccountsHistory, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsHistoryError));
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
    public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		String strType=null;
			StringBuffer sbfCaption= new StringBuffer();
			HospitalManager hospitalObject=this.getHospitalManagerObject();
    		DynaActionForm frmAccountsHistoryDetail = (DynaActionForm)form;
            AccountDetailVO accountDetailVO = null;
            strType=frmAccountsHistoryDetail.getString("empHistory");
            String strStartDate = null;
            String strEndDate = null;
            if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))// To check whether the control is in edit mode or in add mode
            {
                if(strType.equals("EEF")){    //checks the type is equal to empanelment fee(EEF)
                    Long lngEmpanelSeqId= null;
                    accountDetailVO = (AccountDetailVO)((TableData)request.getSession().getAttribute("tableData")).
                    getData().get(Integer.parseInt((String)frmAccountsHistoryDetail.get("rownum")));
                    lngEmpanelSeqId=accountDetailVO.getEmplSeqId();
                    strStartDate = accountDetailVO.getFormattedStartDate();
                    strEndDate = accountDetailVO.getFormattedEndDate();
                    accountDetailVO=hospitalObject.getEmpanelmentFeeDetails(lngEmpanelSeqId);
                    frmAccountsHistoryDetail = (DynaActionForm)FormUtils.setFormValues("frmAccountsHistoryDetail", 
                    		accountDetailVO, this, mapping, request);
                    frmAccountsHistoryDetail.set("addressVO",FormUtils.setFormValues("frmAccountAddress",
                    		accountDetailVO.getPayOrdBankAddressVO(),this,mapping,request));
                    frmAccountsHistoryDetail.set("emplSeqId",lngEmpanelSeqId.toString());
                    sbfCaption.append("Empanelment Fee Details -").append("[").append(TTKCommon.getWebBoardDesc(request))
                    .append("]").append("[").append(strStartDate).append(" to ").append(strEndDate).append("]");
                }//end of if(strType.equals("EEF"))
                
                else // If the Empanel Seq Id is null then bank gaurantee details are developed
                {
                    Long lngBankGauntSeqID = null;
                    accountDetailVO = (AccountDetailVO)((TableData)request.getSession().getAttribute("tableData")).
                    getData().get(Integer.parseInt((String)frmAccountsHistoryDetail.get("rownum")));
                    lngBankGauntSeqID = accountDetailVO.getBankGuantSeqId();
                    strStartDate = accountDetailVO.getFormattedStartDate();
                    strEndDate = accountDetailVO.getFormattedEndDate();
                    accountDetailVO = hospitalObject.getBankGuaranteeDetails(lngBankGauntSeqID);
                    frmAccountsHistoryDetail = (DynaActionForm)FormUtils.setFormValues("frmAccountsHistoryDetail", 
                    		accountDetailVO, this, mapping, request);
                    frmAccountsHistoryDetail.set("bankGuantSeqId",lngBankGauntSeqID.toString());
                    sbfCaption.append("Bank Guarantee Details -").append("[").append(TTKCommon.getWebBoardDesc(request)).
                    append("]").append("[").append(strStartDate).append(" to ").append(strEndDate).append("]");
                }// end of else
            }// end of if(!((String)(accountHistoryForm).get("rownum")).equals(""))
            request.getSession().setAttribute("frmAccountsHistoryDetail",frmAccountsHistoryDetail);
            frmAccountsHistoryDetail.set("caption",sbfCaption.toString());
            //finally return to the grid screen
            return this.getForward(strEditAccountsHisory, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsHistoryError));
		}//end of catch(Exception exp)
    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
            return this.getForward(strAccountsHistory, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsHistoryError));
		}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method builds all the search parameters to ArrayList and places them in session 
     * @param accountHistoryForm DynaActionForm
     * @param lngHospitalSeqId which will have long value 
     * @return alSearchParams ArrayList
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm accountHistoryForm,Long lngHospitalSeqId)
    {
        //build the column names along with their values to be searched 
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(new SearchCriteria("HOSP_SEQ_ID",String.valueOf(lngHospitalSeqId)));
        alSearchParams.add(new SearchCriteria("FROM_DATE", (String)accountHistoryForm.get("startdate")));
        alSearchParams.add(new SearchCriteria("TO_DATE", (String)accountHistoryForm.get("enddate")));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmAccountsHistory,Long lngHospitalSeqId)
    
    /**
     * Returns the HospitalManager session object for invoking methods on it.
     * @return HospitalManager session object which can be used for method invocation 
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
            throw new TTKException(exp, strAccountsHistoryError);
        }//end of catch
        return hospitalManager;
    }//end getHospitalManagerObject()   
}// end of AccountsHistoryAction class
