/** @ (#) PreAuthGrpReportsAction.java 
 * Project     : TTK Healthcare Services
 * File        : PreAuthGrpReportsAction.java 
 * Author      : Manikanta Kumar G G
 * Company     : Span Systems Corporation
 * Date Created: Nov 29, 2010
 *
 * @author 		 : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
package com.ttk.action.misreports;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

import javax.naming.InitialContext;
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

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.misreports.ReportManager;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.misreports.ReportsDataSource;

public class PreAuthGrpReportsAction extends TTKAction 
{

	private static final Logger log = Logger.getLogger( PreAuthGrpReportsAction.class );
	
	private static final String strSelectGroup="selectgroup";
	private static final String strAllPreauthReportsList="allpreauthreportslist";
	private static final String strReportdisplay="reportdisplay";

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
    	try{
    		setLinks(request);
    		DynaActionForm frmMISReports=(DynaActionForm)form;
    		request.getSession().setAttribute("frmMISReports",frmMISReports);
    		return mapping.findForward(strSelectGroup);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,"reportdetail"));
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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
								 HttpServletResponse response) throws TTKException{
		try{
			setLinks(request);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			return this.getForward(strAllPreauthReportsList,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
		}// end of catch(Exception exp)
	}//end of doClose()
	
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
   public ActionForward doGenerateReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                          HttpServletResponse response) throws TTKException{
   	try{
   		setLinks(request);
   		JasperReport jasperReport,emptyReport;
   		JasperPrint jasperPrint;
   		ReportsDataSource ttkReportsDataSource = null;
   		String strGroupId = request.getParameter("groupId");
		String strFrom = request.getParameter("sFrom");
		String strTo = request.getParameter("sTo");
		String strReportType = request.getParameter("reportType");
		String strReportID = request.getParameter("reportID");
		String strStatus = request.getParameter("sStatus");
		String strParam= "|"+request.getParameter("parameter")+"|"+strFrom+"|"+strTo+"|"+strStatus+"|";
   		String jrxmlfile=null;
   		jrxmlfile="reports/misreports/PreAuthReport.jrxml";
   	    ttkReportsDataSource = new ReportsDataSource(strReportID,strParam);
   		try
   		{
   			jasperReport = JasperCompileManager.compileReport(jrxmlfile);
   			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
   			HashMap<String,String> hashMap = new HashMap<String,String>();
   			ByteArrayOutputStream boas=new ByteArrayOutputStream();
   			hashMap.put("GroupID",strGroupId);
		    hashMap.put("sFrom",strFrom);
			hashMap.put("sTo",strTo);
			hashMap.put("sReportType",strReportType);
   			if(!(ttkReportsDataSource.getResultData().next()))
   			{
   				jasperPrint = JasperFillManager.fillReport(emptyReport,hashMap,new JREmptyDataSource());
   			}// end of if(!(ttkReportsDataSource.getResultData().next()))
   			else
   			{
   				jasperPrint = JasperFillManager.fillReport(jasperReport,hashMap,new ReportsDataSource(request.getParameter("reportID"),strParam));
   			}// end of else
   			
   			if(request.getParameter("reportType").equals("EXL"))// if the report type is Excel
   			{
   				JRXlsExporter jExcelApiExporter = new JRXlsExporter();
   				jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
   				jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
   				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
   				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
   				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
   				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
   				jExcelApiExporter.exportReport();
   			}// end of  if(request.getParameter("reportType").equals("EXL"))
   			else // if report type if PDF
   			{
   				JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
   				
   			}// end of else
   			// keep the byte array out stream in request scope to write
   			// in the BinaryStreamServlet
   			request.setAttribute("boas",boas);
   		}// end of try
   		catch(Exception exp)
       	{
       		return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
       	}// end of catch(Exception exp)
   		return (mapping.findForward(strReportdisplay));
   	}// end of try
   	catch(TTKException expTTK)
   	{
   		return this.processExceptions(request, mapping, expTTK);
   	}// end of catch(TTKException expTTK)
   	catch(Exception exp)
   	{
   		return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
   	}// end of catch(Exception exp)
   }// end of doGenerateReport
   
   
}
