/**
 * @ (#) Viewlog.debug Oct 27, 2006
 * Project      : TTK HealthCare Services
 * File         : Viewlog.debug
 * Author       : Arun K.M
 * Company      : Span Systems Corporation
 * Date Created : Oct 27, 2006
 *
 * @author       : Arun K.M
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

package com.ttk.action.finance;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ibm.icu.text.SimpleDateFormat;
import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.finance.FloatAccountManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.ChequeVO;

/**
 * This class is used for searching of List of payments.
 * This class also allows to view the xl report
 */
public class ViewPaymentAdviceAction extends TTKAction
{
    private static Logger log = Logger.getLogger(ViewPaymentAdviceAction.class );
    //Modes of Float List
    private static final String strBackward="Backward";
    private static final String strForward="Forward";
    //Action mapping forwards
    private static final String strclaimslistdetails="claimslistdetails";
    private static final String strReportdisplay="reportdisplay";
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
    		log.debug("Inside ViewPaymentAdviceAction doDefault");
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
    		tableData.createTableInfo("PaymentBatchTable",new ArrayList());
    		request.getSession().setAttribute("tableData",tableData);
    		((DynaActionForm)form).initialize(mapping);//reset the form data
    		request.getSession().setAttribute("searchparam", null);
    		return this.getForward(strclaimslistdetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
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
    public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	log.debug("Inside ViewPaymentAdviceAction doChangeWebBoard");
    	return doDefault(mapping,form,request,response);
    }//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doViewPaymentXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.info("Inside ViewPaymentAdviceAction doViewPaymentXL");
    		log.debug("Inside ViewPaymentAdviceAction doViewPaymentXL");
    		return (mapping.findForward(strReportdisplay));
    	}
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
    	}//end of catch(Exception exp)
    }//end of doViewPaymentXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doViewFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.info("Inside ViewPaymentAdviceAction doViewFile");
    		String strBatchFileName = "";
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		TableData  tableData =TTKCommon.getTableData(request);
    		strBatchFileName = ((ChequeVO)tableData.getRowInfo(Integer.parseInt(
    															request.getParameter("rownum")))).getFileName();
    		DynaActionForm frmFloatAccDetails=(DynaActionForm) request.getSession().getAttribute("frmFloatAccDetails");
    		String strNewFile = null;
    		String strAlternateFileName=null;
    		request.setAttribute("alternateFileName",null);
    		long enbdCount=floatAccountManagerObject.getENBDCountandAccNum(Long.parseLong( (String) frmFloatAccDetails.get("floatAcctSeqID")),strBatchFileName);
    		
    		doSearch(mapping, form, request, response);
    		
    		if(strBatchFileName.startsWith("ENBD"))
    		{
    		if(strBatchFileName.endsWith("Consolidated"))
    		{
    			request.setAttribute("alternateFileName",strBatchFileName+".xls");
    			strBatchFileName=strBatchFileName.substring(0,24);
    			strBatchFileName=strBatchFileName+"Consolidated";
    			
    		}
    		else if (strBatchFileName.endsWith("Detail")) {
    			request.setAttribute("alternateFileName",strBatchFileName+".xls");
    			strBatchFileName=strBatchFileName.substring(0,24);
    			strBatchFileName=strBatchFileName+"Detail";
			}
    		else {
    			request.setAttribute("alternateFileName",strBatchFileName+".txt");
    			strBatchFileName=strBatchFileName.substring(0,24);
			}
    		}
    		if(strBatchFileName.startsWith("UTI"))
    		{
    		
    			strNewFile = floatAccountManagerObject.getBatchFileName(strBatchFileName);
    			log.info("File name information is :"+strNewFile);
    			if(strNewFile != null)
    			{
    				strNewFile = strNewFile+".xls";
    			}//end of if(!strNewFile.equals("")||strNewFile != null)
    			else
    			{
    				strNewFile = strBatchFileName+".xls";
    			}//end of else        		
    		}//end of if(strBatchFileName.startsWith("UTI"))
			// Change added for BOA CR KOC1220
    		else if(strBatchFileName.startsWith("BOA") || strBatchFileName.startsWith("CITI")|| strBatchFileName.startsWith("ENBD"))
    		{
    			
    			String accnumber = null;
        		if(frmFloatAccDetails.get("accountNO")!=null)
        		accnumber=(String) frmFloatAccDetails.get("accountNO");
        		accnumber=accnumber.substring(accnumber.length()-3);
        		SimpleDateFormat dateFormater=new SimpleDateFormat("ddMMYYYY");
    			//System.out.println(dateFormater.format(new Date()).toString());
    			
    			if(strBatchFileName.contains("Detail") || strBatchFileName.contains("Cons"))
    			{
    				strNewFile = strBatchFileName+".xls";
    			//	if(strBatchFileName.startsWith("ENBD"))
    			//	{
			//strAlternateFileName="ENBD-"+accnumber+"-"+dateFormater.format(new Date()).toString();
			//if(strBatchFileName.contains("Detail"))strAlternateFileName=strAlternateFileName+"-"+enbdCount+"Detail.xls";
			//if(strBatchFileName.contains("Cons"))strAlternateFileName=strAlternateFileName+"-"+enbdCount+"Consolidated.xls";    				//strAlternateFileName="ENBD-"+
			
			
			//request.setAttribute("alternateFileName",strAlternateFileName);
    				//}
    			}
    			else
    			{
    				
    				strNewFile = strBatchFileName+".txt";
    			//	if(strBatchFileName.startsWith("ENBD"))
    			//	{
			//strAlternateFileName="ENBD-"+accnumber+"-"+dateFormater.format(new Date()).toString()+"-"+enbdCount+".txt";
			//request.setAttribute("alternateFileName",strAlternateFileName);
			    				//strAlternateFileName="ENBD-"+
    			//	}
    			}
    		} //End change added for BOA CR KOC1220
    		else
    		{
    			strNewFile = strBatchFileName+".xls";
    		}//end of else
    		File file = null;
    		
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			//log.info("Inside if");
    			String strAuthpdf = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+strNewFile;
    			file = new File(strAuthpdf);
    			if(file.exists())
    			{
    				log.info("Inside exist");
    				strAuthpdf = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+strNewFile;
    				request.setAttribute("fileName",strAuthpdf);
    			}//end of if(file.exists())
    		}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		return this.getForward(strclaimslistdetails, mapping, request);//finally return to the grid screen
    	}
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
    	}//end of catch(Exception exp)
    }//end of doViewFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   
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
    		log.debug("Inside ViewPaymentAdviceAction doSearch");
    		log.info("Inside ViewPaymentAdviceAction doSearch");
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
    				return (mapping.findForward(strclaimslistdetails));
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
    			tableData.createTableInfo("PaymentBatchTable",null);
    			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
    			tableData.setSortData("0101");
    			tableData.setSortColumnName("BATCH_DATE");
    			tableData.setSortOrder("DESC");
    			tableData.modifySearchData("search");
    		}//end of else

    		ArrayList alTransaction= floatAccountManagerObject.getViewPaymentAdviceList(tableData.getSearchData());
    		tableData.setData(alTransaction, "search");
    		//set the table data object to session
    		request.getSession().setAttribute("tableData",tableData);
    		//finally return to the grid screen
    		return this.getForward(strclaimslistdetails, mapping, request);
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
    		log.debug("Inside ViewPaymentAdviceAction doForward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		tableData.modifySearchData(strForward);//modify the search data
    		ArrayList alBankList = floatAccountManagerObject.getViewPaymentAdviceList(tableData.getSearchData());
    		tableData.setData(alBankList, strForward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
    		return this.getForward(strclaimslistdetails, mapping, request);//finally return to the grid screen
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
    		log.debug("Inside ViewPaymentAdviceAction doBackward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		ArrayList alBankList = floatAccountManagerObject.getViewPaymentAdviceList(tableData.getSearchData());
    		tableData.setData(alBankList, strBackward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
    		return this.getForward(strclaimslistdetails, mapping, request);//finally return to the grid screen
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
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmViewPayments formbean which contains the search fields
     * @param request HttpServletRequest
     * @return ArrayList contains search parameters
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmViewPayments,HttpServletRequest request)
    throws TTKException
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(TTKCommon.getWebBoardId(request));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewPayments.get("sFileName")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewPayments.get("sBatchNum")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewPayments.get("sClaimSettelmentNumber")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewPayments.get("sBatchDate")));
        alSearchParams.add(TTKCommon.getUserSeqId(request));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmFloatAccounts,HttpServletRequest request)
}// end of ViewPaymentSearchAction.java

