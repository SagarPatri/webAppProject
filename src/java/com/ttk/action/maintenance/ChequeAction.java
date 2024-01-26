/**
 * @ (#)ChequeAction.java 14th Apr 2010
 * Project      : TTK HealthCare Services
 * File         : ChequeAction
 * Author       : Manikanta Kumar G G
 * Company      : Span Systems Corporation
 * Date Created : 14th Apr 2010
 *
 * @author       :  Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.maintenance;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;


public class ChequeAction extends TTKAction {

	private static final Logger log = Logger.getLogger(ChequeAction.class );
	private static final String strFinmainprintcheque="chequeprint";
	private static final String strReportdisplay="reportdisplay";
	
	/**
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
			log.debug("Inside ChequeAction doDefault");
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			return this.getForward("chequedetails", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strFinmainprintcheque));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doClose method of ChequeAction");
			setLinks(request);
			request.getSession().removeAttribute("frmReportList");
			return mapping.findForward("close");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strFinmainprintcheque));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
     * This method is used to print the check.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doPrintCheque(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    								   HttpServletResponse response) throws TTKException{
    	try{
    		setLinks(request);
    		log.debug("Inside ChequeAction doPrintCheque");
    		DynaActionForm frmReportList = (DynaActionForm)form;
    		JasperReport jasperReport;
    		StringBuffer sbJrxmlFileName = new StringBuffer(frmReportList.getString("fileName"));
    		JasperPrint jasperPrint;
    		TTKReportDataSource reportDataSource = null;
    		ByteArrayOutputStream boas=new ByteArrayOutputStream();    		
    		File file = new File("generalreports/"+(sbJrxmlFileName.append(".jrxml")).toString());
			if(file.exists())
			{
				reportDataSource = new TTKReportDataSource("CheBatchNo",request.getParameter("batchNo"));
			}//end of if(file.exists())
			else
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.jrxmlfile.required");
				throw expTTK;
			}//end of else
    		jasperReport = JasperCompileManager.compileReport("generalreports/"+sbJrxmlFileName.toString());
    		HashMap<String, Object> hashMap = new HashMap<String, Object>();
    		jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,reportDataSource);
    		JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
    		if(reportDataSource !=null)
			{
				request.setAttribute("popup","popup");
				request.getSession().setAttribute("boas",boas);
			}//end of if(reportDataSource !=null)
    		return this.getForward("chequedetails", mapping, request);
    	}//end of try
    	catch (JRException e) {
    		TTKException expTTK = new TTKException();
    		if(e.getCause() instanceof FileNotFoundException)
    		{
    			expTTK.setMessage("error.jrxmlfile.required");
    		}//end of if(e.getCause() instanceof FileNotFoundException)
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch (JRException e)
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strFinmainprintcheque));
		}//end of catch(Exception exp)
    }//end of doPrintCheque(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doClaimsCheque(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    									HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside ChequeAction doClaimsCheque");
    		return (mapping.findForward(strReportdisplay));
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,"chequeprint"));
		}//end of catch(Exception exp)
    }//end of doClaimsCheque(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
}//end of ChequeAction
