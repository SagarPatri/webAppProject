/**
 * @ (#) TDSReportsAction.java Mar 26, 2010
 * Project      : TTK HealthCare Services
 * File         : TDSReportsAction.java
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : Mar 26, 2010
 *
 * @author       :  Balakrishna Erram
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

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ttk.action.TTKAction;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;

public class TDSReportsAction extends TTKAction{
	private static final Logger log = Logger.getLogger( TDSReportsAction.class );

	//  Action mapping forwards.
	private static final String strReportdisplay="reportdisplay";

	//Exception Message Identifier
	private static final String strReportExp="report";

	/**
	 * This method is used to generate the report.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws TTKException if any error occurs
	 */
	public ActionForward doGenDailyReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doGenerateFinanceBatchReport method of ReportsAction");
			setLinks(request);
			JasperReport jasperReport,emptyReport;
			JasperPrint jasperPrint;
			TTKReportDataSource ttkReportDataSource = null;
			String jrxmlfile, strReportID = "" ;
			jrxmlfile = request.getParameter("fileName");
			strReportID = request.getParameter("reportID");
			ttkReportDataSource = new TTKReportDataSource(strReportID,request.getParameter("parameter"));
			jasperReport = JasperCompileManager.compileReport(jrxmlfile);
			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			ByteArrayOutputStream boas=new ByteArrayOutputStream();
			if(ttkReportDataSource.getResultData().next())
			{
				ttkReportDataSource.getResultData().beforeFirst();
				jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);				 
			}//end of if(ttkReportDataSource.getResultData().next()))
			else
			{
				jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
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
			log.info("report generated sucessfully ");
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
	}//end of doGenDailyReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)		
}//end of TDSReportsAction