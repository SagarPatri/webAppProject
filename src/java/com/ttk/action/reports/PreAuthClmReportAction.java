
/**
 * @ (#) PreAuthClmReportAction.java Mar 3, 2011
 * Project       : TTK HealthCare Services
 * File          : PreAuthClmReportAction.java
 * Author        : ManiKanta Kumar G G
 * Company       : Span Systems Corporation
 * Date Created  : Mar 3, 2011
 *
 * @author       :  ManiKanta Kumar G G
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.action.reports;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;

import com.ttk.action.TTKAction;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;

public class PreAuthClmReportAction extends TTKAction
{
	//private static Logger log = Logger.getLogger(PreAuthClmReportAction.class);
	
	private static final String strPreAuthClmReportDetail = "preauthclmrptdetail" ;
	private static final String strSelectGroup="selectgroup";
	private static final String strPreAuthRpts="preauthreports";
	private static final String strReportdisplay="reportdisplay";
	
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
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			DynaActionForm frmReportList= (DynaActionForm) form;
			frmReportList.initialize(mapping);
			return this.getForward(strPreAuthClmReportDetail,mapping,request);
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
     * This method is called on click of Select Group icon
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSelectGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try
    	{
    		setLinks(request);
    		DynaActionForm frmReportList=(DynaActionForm)form;
    		request.getSession().setAttribute("frmReportList",frmReportList);
    		return mapping.findForward(strSelectGroup);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
    	}//end of catch(Exception exp)
    }//end of doSelectGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     //HttpServletResponse response)
    
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
			setLinks(request);
			Document reportsListDoc=null;
			((DynaActionForm)form).initialize(mapping);//reset the form data
			//get the reports list Document
			reportsListDoc=TTKCommon.getDocument("ReportsList.xml");
			request.setAttribute("ReportsListDoc",reportsListDoc);
			return mapping.findForward(strPreAuthRpts);
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
	
	/**
	  * This method is used to generate the report.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doGeneratePreAuthClmRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try
		 {
			 setLinks(request);
			 JasperReport jasperReport,emptyReport;
			 JasperPrint jasperPrint;
			 TTKReportDataSource ttkReportDataSource = null;
			 String strJrxmlfile, strReportID = "" ;
			 String strFrom = request.getParameter("sFrom");
			 String strTo = request.getParameter("sTo");
			 String strReportType = request.getParameter("reportType");
			 
			 strJrxmlfile = request.getParameter("fileName");
			 strReportID = request.getParameter("reportID");
			 
			 ttkReportDataSource = new TTKReportDataSource(strReportID,request.getParameter("parameter"));
			 jasperReport = JasperCompileManager.compileReport(strJrxmlfile);
			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			 HashMap<String, String> hashMap = new HashMap<String, String>();
			 //hashMap.put("GroupID",strGroupId);
			 hashMap.put("sFrom",strFrom);
			 hashMap.put("sTo",strTo);
			 hashMap.put("sReportType",strReportType);
			 
			 ByteArrayOutputStream boas=new ByteArrayOutputStream();
			 
			 if(!(ttkReportDataSource.getResultData().next()))
			 {
				 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
			 }//end of if(!(ttkReportDataSource.getResultData().next()))
			 else
			 {
				 ttkReportDataSource.getResultData().beforeFirst();
				 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
			 }//end of if(ttkReportDataSource.getResultData().next())
			 if(request.getParameter("reportType").equals("EXL"))//if the report type is Excel
			 {
				 JRXlsExporter jExcelApiExporter = new JRXlsExporter();
				 jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
				 jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
				 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				 jExcelApiExporter.exportReport();
			 }//end of if(request.getParameter("reportType").equals("EXL"))
			 else//if report type if PDF
			 {
				 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);	 
			 }//end of else
			 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
			 request.setAttribute("boas",boas);
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)		
		 return (mapping.findForward(strReportdisplay));
	 }//end of doGeneratePreAuthClmRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
}//end of PreAuthClmReportAction
