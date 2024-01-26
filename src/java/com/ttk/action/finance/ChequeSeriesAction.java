/**
 * @ (#) ChequeSeriesAction.java June 9th, 2006
 * Project      : TTK HealthCare Services
 * File         : ChequeSeriesAction.java
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : June 9th, 2006
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
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.ChequeVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for displaying the Cheque Series.
 * This class also provides option for deletion and updation of the cheque series.
 */
public class ChequeSeriesAction extends TTKAction
{
	private static Logger log = Logger.getLogger( ChequeSeriesAction.class );
	private static final String strDeleteChequeSeries="DeleteChequeSeries";
	private static final String strChangeWebBoard="doChangeWebBoard";
	//for forwarding
	private static final String strChequeSeries="chequeseries";
	//  Exception Message Identifier
    private static final String strUser="support";
	
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
			log.debug("Inside ChequeSeriesAction doDefault");
			if(TTKCommon.getWebBoardId(request) == null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.accountno.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request) == null)
			TableData  tableData =TTKCommon.getTableData(request);
			ArrayList alChequeSeriesList=null;
			BankAccountManager BankAccountManagerObject=this.getBankAccountManagerObject();
			String strMode = TTKCommon.checkNull(request.getParameter("mode"));
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")||strChangeWebBoard.equals(strMode))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strSortID.equals(""))
			{
				tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableData.modifySearchData("sort");//modify the search data
			}// end of if(!strSortID.equals(""))
			else
			{
				tableData.createTableInfo("ChequeSeriesTable",null);
				tableData.setSearchData(this.populateSearchCriteria(request));
				tableData.modifySearchData("search");
			}// end of else
			alChequeSeriesList=BankAccountManagerObject.getChequeSeriesList(tableData.getSearchData());
			tableData.setData(alChequeSeriesList);
			request.getSession().setAttribute("tableData",tableData);
			return (mapping.findForward(strChequeSeries));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strUser));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
		log.debug("Inside ChequeSeriesAction doChangeWebBoard");
		return doDefault(mapping,form,request,response);
	}//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse 
																										//response)
    
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
			log.debug("Inside ChequeSeriesAction doSave");
			if(TTKCommon.getWebBoardId(request) == null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.accountno.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request) == null)
			ChequeVO chequeVO =null;
			DynaActionForm frmChequeSeries=(DynaActionForm)form;
			BankAccountManager BankAccountManagerObject=this.getBankAccountManagerObject();
			ArrayList alChequeSeriesList=null;
			TableData  tableData =TTKCommon.getTableData(request);
			chequeVO=(ChequeVO)FormUtils.getFormValues(frmChequeSeries,this, mapping, request);
			chequeVO.setAccountSeqID(TTKCommon.getWebBoardId(request));
			chequeVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			Long lngUpdate=BankAccountManagerObject.saveChequeSeries(chequeVO);
			if(lngUpdate>0)
			{
				frmChequeSeries.initialize(mapping);
				if(chequeVO.getSeqID()!=null)
				{
					//set the appropriate message
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if(chequeVO.getSeqID()!=null)
				else
				{
					//set the appropriate message
					request.setAttribute("updated","message.addedSuccessfully");
				}//end else
				alChequeSeriesList=BankAccountManagerObject.getChequeSeriesList(tableData.getSearchData());
				tableData.setData(alChequeSeriesList);
				request.getSession().setAttribute("tableData",tableData);
			}// end of if(iUpdate>0)
			return (mapping.findForward(strChequeSeries));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strUser));
		}//end of catch(Exception exp)
	}//end of doUpdate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							  HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside ChequeSeriesAction doDelete");
    		StringBuffer sbfDeleteId = new StringBuffer();
        	ArrayList <Object>alChequeDelete=new ArrayList<Object>();
        	DynaActionForm frmChequeSeries=(DynaActionForm)form;
        	BankAccountManager BankAccountManagerObject=this.getBankAccountManagerObject();
        	ArrayList alChequeSeriesList=null;
        	TableData  tableData =TTKCommon.getTableData(request);
        	sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().getAttribute("tableData")));
        	alChequeDelete.add(String.valueOf(sbfDeleteId));
        	alChequeDelete.add(TTKCommon.getWebBoardId(request));
            alChequeDelete.add(TTKCommon.getUserSeqId(request));
        	frmChequeSeries.initialize(mapping);
        	int iCount=BankAccountManagerObject.deleteChequeSeries(alChequeDelete);
        	log.debug("iCount value is :"+iCount);
        	//refresh the data in order to get the new records if any.
        	alChequeSeriesList=BankAccountManagerObject.getChequeSeriesList(tableData.getSearchData());
        	tableData.setData(alChequeSeriesList);
        	request.getSession().setAttribute("tableData",tableData);
        	return (mapping.findForward(strDeleteChequeSeries));
		}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strUser));
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
    public ActionForward doViewChequeSeries(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside ChequeSeriesAction doViewChequeSeries");
    		ChequeVO chequeVO =null;
    		DynaActionForm frmChequeSeries=(DynaActionForm)form;
    		//if rownumber is found populate the form object
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			chequeVO=(ChequeVO)((TableData)request.getSession().getAttribute("tableData")).
    												getData().get(Integer.parseInt(request.getParameter("rownum")));
    			frmChequeSeries = (DynaActionForm)FormUtils.setFormValues("frmChequeSeries",chequeVO,
    																	   this,mapping,request);
    		}// end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		else
    		{
    			frmChequeSeries.initialize(mapping);
    		}// end of else
    		request.setAttribute("frmChequeSeries",frmChequeSeries);
    		return (mapping.findForward(strChequeSeries));
    	}
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strUser));
    	}//end of catch(Exception exp)
    }//end of doViewChequeSeries(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse
    																									//response)
    
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
    		log.debug("Inside ChequeSeriesAction doReset");
    		DynaActionForm frmChequeSeries=(DynaActionForm)form;
    		frmChequeSeries.initialize(mapping);
    		return this.getForward(strChequeSeries, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strUser));
    	}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
   /**
     * Returns a string which contains the | separated sequence id's to be deleted
     * @param request HttpServletRequest object which contains information about the selected check boxes
     * @param tableData TableData object which contains the value objects
     * @return String which contains the | separated sequence id's to be delete
     * @throws TTKException If any run time Excepton occures
     */
    private String populateDeleteId(HttpServletRequest request, TableData tableData)throws TTKException
    {
        String[] strChk = request.getParameterValues("chkopt");
        StringBuffer sbfDeleteId = new StringBuffer();
        if(strChk!=null&&strChk.length!=0)
        {
            for(int i=0; i<strChk.length;i++)
            {
                if(strChk[i]!=null)
                {
                    //extract the sequence id to be deleted from the value object
                    if(i == 0)
                    {
                        sbfDeleteId.append("|").append(String.valueOf(((ChequeVO)tableData.getData().
                        						   get(Integer.parseInt(strChk[i]))).getSeqID()));//setBankAccSeqId
                    }//end of if(i == 0)
                    else
                    {
                        sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((ChequeVO)tableData.getData().
                        						          get(Integer.parseInt(strChk[i]))).getSeqID().intValue()));
                    }//end of else
                }//end of if(strChk[i]!=null)
            }//end of for(int i=0; i<strChk.length;i++)
            sbfDeleteId=sbfDeleteId.append("|");
        }//end of if(strChk!=null&&strChk.length!=0)
        return String.valueOf(sbfDeleteId);
    }//end of populateDeleteId(HttpServletRequest request, TableData tableData)

    /**
     * This method builds all the search parameters to ArrayList and places them in session
     * @param Long lngBankAccntSeqId
     * @return alSearchParams ArrayList
     */
    private ArrayList<Object> populateSearchCriteria(HttpServletRequest request)
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(TTKCommon.getWebBoardId(request));
        alSearchParams.add(TTKCommon.getUserSeqId(request));
        return alSearchParams;
    }//end of populateSearchCriteria(Long lngMemberSeqId)

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
            throw new TTKException(exp, strUser);
        }//end of catch
        return bankAccountManager;
    }//end getMemberManager()
}//end of ChequeSeriesAction
