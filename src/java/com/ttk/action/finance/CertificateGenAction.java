/**
 * @ (#) CertificateGenAction.java 21st April, 2010
 * Project       : TTK HealthCare Services
 * File          : CertificateGenAction.java
 * Author        : Balakrishna E
 * Company       : Span Systems Corporation
 * Date Created  : 21st April, 2010
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.finance;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.finance.TDSRemittanceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.CertificateVO;

public class CertificateGenAction extends TTKAction{

	 //private static final Logger log = Logger.getLogger(CertificateGenAction.class );

	 //Action forwards
	 private static final String strCertificategen ="certificategen";
	 private static final String strGenerateInd ="generateindividual";
	 private static final String strGenerateBatch ="generatebatch";
	 private static final String strTableData="tableData";

	 /**
     * This method is used to initialize the enrollment list.
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
    							   HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		return this.getForward(strCertificategen, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, "finance"));
    	}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    				//HttpServletResponse response)

    /**
     * This method is used to navigate to the Generate Batch screen
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doGenerateBatch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							   HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
			setLinks(request);
			String strForward = "";
			String strTable = "CertificateTable";
			//get the session bean from the bean pool for each executing thread
			TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
			//get the table data from session if exists
			TableData tableData = TTKCommon.getTableData(request);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!"".equals(strPageID) || !"".equals(strSortID))
			{
				if(!"".equals(strPageID))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strForward);
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
				tableData.createTableInfo(strTable,null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList<Object> alMonthlyRemitList = new ArrayList<Object>();
			alMonthlyRemitList = tdsRemittanceManager.selectFinYearList(tableData.getSearchData());
			tableData.setData(alMonthlyRemitList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strGenerateBatch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, "finance"));
    	}//end of catch(Exception exp)
    }//end of doGenerateBatch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	   				//HttpServletResponse response)

    /**
     * This method is used to navigate to the Generate Batch screen
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doRegenerateBatch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		//get the session bean from the bean pool for each executing thread
    		TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
    		//TableData tableData = TTKCommon.getTableData(request);
    		ArrayList<Object> alRegenBatch = new ArrayList<Object>();
    		CertificateVO certificateVO=(CertificateVO)((TableData)request.getSession().getAttribute("tableData")).
    		getData().get(Integer.parseInt(request.getParameter("rownum")));
    		alRegenBatch.add(certificateVO.getFinanceYear());
    		alRegenBatch.add("CGB");
    		alRegenBatch.add(certificateVO.getBatchSeqID());
    		alRegenBatch.add(TTKCommon.getUserSeqId(request));
    		alRegenBatch.add("");	
    		alRegenBatch.add(certificateVO.getTdsBatchQuarter());
    		int iResult = tdsRemittanceManager.generateTdsBatch(alRegenBatch);
    		if(iResult > 0)
    		{
    			request.setAttribute("updated", "message.regeneratedSuccessfully");
    			TableData tableData = TTKCommon.getTableData(request);
    			ArrayList<Object> alReGenBatchDtls = new ArrayList<Object>();
    			alReGenBatchDtls = tdsRemittanceManager.selectFinYearList(tableData.getSearchData());
    			tableData.setData(alReGenBatchDtls);
    			//set the table data object to session
    			request.getSession().setAttribute("tableData",tableData);
    		}//end of if(alBatchDetails != null)
    		//set the table data object to session
    		//finally return to the grid screen					
    		return this.getForward(strGenerateBatch, mapping, request);     	
        }//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, "finance"));
    	}//end of catch(Exception exp)
    }//end of doRegenerateBatch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    //HttpServletResponse response)

    /**
	     * This method is used to navigate to the Generate Individual screen
	     * @param mapping The ActionMapping used to select this instance
	     * @param form The optional ActionForm bean for this request (if any)
	     * @param request The HTTP request we are processing
	     * @param response The HTTP response we are creating
	     * @return ActionForward Where the control will be forwarded, after this request is processed
	     * @throws TTKException if any error occurs
	     */
	    @SuppressWarnings("unchecked")
		public ActionForward doGenerateInd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    							   HttpServletResponse response) throws TTKException{
	    	try
	    	{
	    		setLinks(request);
	    		DynaActionForm frmCertGenInd =(DynaActionForm)form;
	    		ArrayList<Object> altdsHospList=new ArrayList<Object>();
	    		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
	    		String strForward = strGenerateInd;
	    		String strTable = "HospitalSearchTable";
	    		//get the table data from session if exists
	    		TableData hospitalData =TTKCommon.getTableData(request);
	    		//create new table data object
	    		hospitalData = new TableData();
	    		//create the required grid table
	    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));

	    		if(strSortID.equals(""))
	    		{
	    			hospitalData.createTableInfo(strTable,null);
	    			hospitalData.modifySearchData("search");
	    		}//end of if(strSortID.equals(""))
	    		else
	    		{
	    			hospitalData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
	    			hospitalData.modifySearchData("sort");//modify the search data
	    		}// end of else
	    		hospitalData.setData(altdsHospList,"search");
	    		hospitalData.createTableInfo(strTable,new ArrayList<Object>());
	    		if(strActiveSubLink.equals("TDS Configuration"))
				{
					((Column)((ArrayList)hospitalData.getTitle()).get(3)).setVisibility(false);
					((Column)((ArrayList)hospitalData.getTitle()).get(0)).setIsLink(false);
					((Column)((ArrayList)hospitalData.getTitle()).get(0)).setColumnWidth("41%");
					((Column)((ArrayList)hospitalData.getTitle()).get(1)).setColumnWidth("29%");
					((Column)((ArrayList)hospitalData.getTitle()).get(2)).setColumnWidth("30%");
				}
	    		request.getSession().setAttribute(strTableData,hospitalData);
	    		request.getSession().setAttribute("frmCertGenInd",frmCertGenInd);
	    		//((DynaActionForm)form).initialize(mapping);//reset the form data
	    		return this.getForward(strForward, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
	    	{
	    		return this.processExceptions(request, mapping, expTTK);
	    	}//end of catch(TTKException expTTK)
	    	catch(Exception exp)
	    	{
	    		return this.processExceptions(request, mapping, new TTKException(exp, "finance"));
	    	}//end of catch(Exception exp)
	    }//end of doGenerateInd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	   				//HttpServletResponse response)

    /**
	 * This method will add search criteria fields and values to the arraylist and will return it.
	 * @param frmViewMonthlyRemit formbean which contains the search fields
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmGenerateBatch)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add("");
		alSearchParams.add("CGB");
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmProductList)

    /**
     * Returns the BankAccountManager session object for invoking methods on it.
     * @return BankAccountManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private TDSRemittanceManager getTDSRemittanceManagerObject() throws TTKException
    {
    	TDSRemittanceManager tdsRemittanceManager = null;
        try
        {
            if(tdsRemittanceManager == null)
            {
                InitialContext ctx = new InitialContext();
                tdsRemittanceManager = (TDSRemittanceManager) ctx.lookup("java:global/TTKServices/business.ejb3/TDSRemittanceManagerBean!com.ttk.business.finance.TDSRemittanceManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "tds");
        }//end of catch
        return tdsRemittanceManager;
    }//end getTDSRemittanceManagerObject()

}//end of CertificateGenAction