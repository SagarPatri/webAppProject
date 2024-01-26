package com.ttk.action.reports;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;

import com.ttk.action.TTKAction;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;

public class ClaimsDetailsReportAction extends TTKAction
{

	private static Logger log = Logger.getLogger( ClaimsDetailsReportAction.class );

	//declaration of forward paths
	private static final String strClaimsReportDetail="claimsreportdetail";
	private static final String strFinanceReportList="financereportlist";
	private static final String strBatchDetail="batchdetail";

	//Exception Message Identifier
	private static final String strReportExp="report";
	/**
	 * This method is used to initialize the screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.info("Inside the Default method of ClaimsDetailsReportAction");
			setLinks(request);
			DynaActionForm frmReportList= (DynaActionForm) form;
			log.info("selectRptType value is "+frmReportList.get("selectRptType"));
			((DynaActionForm)form).initialize(mapping);//reset the form data
			return this.getForward(strClaimsReportDetail,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doReportType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doReportType method of ClaimsDetailsReportAction");
			setLinks(request);
			String selectRptType="";
			DynaActionForm frmReportList= (DynaActionForm) form;
			selectRptType = (String)frmReportList.get("selectRptType");

			((DynaActionForm)form).initialize(mapping);		//reset the form data
			log.info("selectRptType value is :"+selectRptType);
			frmReportList.set("selectRptType",selectRptType); 
			
			frmReportList.set("reportName","Claims Detail Report");
			return this.getForward(strClaimsReportDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	}//end of doReportType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to navigate to  batchdetails screen when Batch Number Icon is clicked.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doBatchNumber(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doBatchNumber method of ClaimsDetailsReportAction");
			setLinks(request);
			return (mapping.findForward(strBatchDetail));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	}//end of doBatchNumber(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doClose method of ClaimsDetailsReportAction");
			setLinks(request);
			Document reportsListDoc=null;
			((DynaActionForm)form).initialize(mapping);//reset the form data
			//get the reports list Document
			reportsListDoc=TTKCommon.getDocument("ReportsList.xml");
			request.setAttribute("ReportsListDoc",reportsListDoc);
			return mapping.findForward(strFinanceReportList);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp) 
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
}//end of ClaimsDetailsReportAction