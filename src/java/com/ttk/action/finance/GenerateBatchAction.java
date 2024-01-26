/**
 * @ (#) GenerateBatchAction.java 22nd April, 2010
 * Project       : TTK HealthCare Services
 * File          : GenerateBatchAction.java
 * Author        : Balakrishna E
 * Company       : Span Systems Corporation
 * Date Created  : 22nd April, 2010
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
import com.ttk.action.table.TableData;
import com.ttk.business.finance.TDSRemittanceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;

public class GenerateBatchAction extends TTKAction{
	
	// private static final Logger log = Logger.getLogger(GenerateBatchAction.class );
	 
	 //Action forwards
	 private static final String strGenerateBatch ="generatebatch";
	 private static final String strCertificategen ="certificategen";

	 /**
     * This method is used to initialize the enrollment list.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							   HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		((DynaActionForm)form).initialize(mapping);//reset the form data
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
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    				//HttpServletResponse response)
    
    /**
     * This method is used close the current screen to go to the previous screen.
     * Finally it forwards to the appropriate view based on the specified forward mappings
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
    		//DynaActionForm frmGenerateBatch= (DynaActionForm)form;
			//get the session bean from the bean pool for each executing thread
			TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
			ArrayList<Object> alGenBat = new ArrayList<Object>();
			alGenBat.add(request.getParameter("financeYear"));
			alGenBat.add("CGB");
			alGenBat.add(null);
			alGenBat.add(TTKCommon.getUserSeqId(request));
			alGenBat.add("");
			alGenBat.add(request.getParameter("tdsbatchQtr"));
			int iResult = tdsRemittanceManager.generateTdsBatch(alGenBat);
			
			if(iResult > 0)
			{
				request.setAttribute("updated", "message.generatedSuccessfully");				
				//get the table data from session if exists
				TableData tableData = TTKCommon.getTableData(request);
				ArrayList<Object> alBatchDetails = new ArrayList<Object>();
				alBatchDetails = tdsRemittanceManager.selectFinYearList(tableData.getSearchData());
				tableData.setData(alBatchDetails);
				//set the table data object to session
				request.getSession().setAttribute("tableData",tableData);
				//finally return to the grid screen			
			}//end of if(iResult > 0)
			else
			{
				request.setAttribute("failed", "message.notgeneratedSuccessfully");
			}//end of else
//    		return this.getForward(strGenerateBatch, mapping, request);
    		return (mapping.findForward(strGenerateBatch));
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, "finance"));
    	}//end of catch(Exception exp)
    }//end of doGenerate
    
    /**
     * This method is used close the current screen to go to the previous screen.
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
    }//end of doClose
    
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
    
}//end of GenerateBatchAction