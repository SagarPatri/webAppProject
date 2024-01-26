
/**
 * @ (#) CitiBankClaimsDetailAction.java 10th Feb 2009
 * Project      : TTK HealthCare Services
 * File         : CitiBankClaimsDetailAction.java
 * Author       : Balakrishna Erram
 * Company      : Span Infotech Pvt Ltd.
 * Date Created : 10th Feb 2009
 *
 * @author       :  Balakrishna Erram
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.misreports;

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
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.misreports.ReportsDataSource;


public class CitiBankClaimsDetailAction extends TTKAction {

	private static final Logger log = Logger.getLogger(CitiBankClaimsDetailAction.class );


	private static final String strAllFinanceReportsList="allfinancereportslist";
	private static final String strPaymentAdviceReport="orientalpaymentadvicereport";
	private static final String strReportdisplay="reportdisplay";
	private static final String strBank="bank";

	/**
	 * This method is used to initialize form.
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
			log.info("Inside MISPaymentAdviceAction doDefault");    		

			((DynaActionForm)form).initialize(mapping);//reset the form data
			request.getSession().setAttribute("searchparam", null);
			return this.getForward(strPaymentAdviceReport, mapping, request);
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
			log.debug("Inside the doGenerateReport method of MISPaymentAdviceAction");
			setLinks(request);
			JasperReport jasperReport,emptyReport;
			JasperPrint jasperPrint;
			ReportsDataSource ttkReportsDataSource = null;
			String strReportID = request.getParameter("reportID");
			String strParam =  request.getParameter("parameter");
			String reportType = request.getParameter("reportType");
			String strJrxmlfile="";
			if(strReportID.equalsIgnoreCase("CitiFinDetRpt"))
			{
				if(TTKCommon.checkNull(reportType).equals("EXCEL"))
				{
					strJrxmlfile = "reports/finance/CitibankClaimsDetailEXL.jrxml";
				}//end of if(TTKCommon.checkNull(reportType).equals("EXCEL"))
				else if(TTKCommon.checkNull(reportType).equals("PDF"))
				{
					strJrxmlfile = "reports/finance/CitibankClaimsDetail.jrxml";
				}//end of else if(TTKCommon.checkNull(reportType).equals("PDF"))
			}//end of if(strReportID.equalsIgnoreCase("CitiFinDetRpt"))
			else
			{
				strJrxmlfile = request.getParameter("filename");
			}//end of else
			ttkReportsDataSource = new ReportsDataSource(strReportID,strParam);
			try
			{
				jasperReport = JasperCompileManager.compileReport(strJrxmlfile);
				emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
				HashMap<String,String> hashMap = new HashMap<String,String>();
				ByteArrayOutputStream boas=new ByteArrayOutputStream();

				if(!(ttkReportsDataSource.getResultData().next()))
				{
					jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
				}// end of if(!(ttkReportsDataSource.getResultData().next()))
				else
				{				
					jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new ReportsDataSource(strReportID,strParam));
				}// end of else
				if(request.getParameter("reportType").equals("EXCEL"))//if the report type is Excel
				{
					JRXlsExporter jExcelApiExporter = new JRXlsExporter();
					jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
					jExcelApiExporter.exportReport();
				}//end of if(request.getParameter("reportType").equals("EXCEL"))
				else
				{
					JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
				}//end of else
				request.setAttribute("boas",boas);
			}// end of try		
			catch (TTKException e)
			{
				e.printStackTrace();
				return this.processExceptions(request, mapping, e);
			}// end of catch (Exception e)
			return (mapping.findForward(strReportdisplay));
		}// end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBank));
		}// end of catch(Exception exp)
	}// end of doGenerateReport

	/**
	 * This method is used to close the screen.
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
			log.debug("Inside the doClose method of MISPaymentAdviceAction");
			((DynaActionForm)form).initialize(mapping);//reset the form data
			return this.getForward(strAllFinanceReportsList,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }// end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
		 	 return this.processExceptions(request, mapping, new TTKException(exp, strBank));
		 }// end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

}//end of MISPaymentAdviceAction
