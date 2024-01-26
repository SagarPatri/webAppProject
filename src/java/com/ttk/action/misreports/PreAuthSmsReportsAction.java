/** @ (#) PreAuthSmsReportsAction.java 
 * Project     : TTK Healthcare Services
 * File        : PreAuthSmsReportsAction.java 
 * Author      : Manikanta Kumar G G
 * Company     : Span Systems Corporation
 * Date Created: Dec 30th, 2010
 *
 * @author 		 : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
package com.ttk.action.misreports;

import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
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
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.misreports.ReportsDataSource;


public class PreAuthSmsReportsAction extends TTKAction{
	
	private static final Logger log = Logger.getLogger( PreAuthSmsReportsAction.class );
	
	private static final String strAllPreauthReportsList="allpreauthreportslist";
	private static final String strReportdisplay="reportdisplay";
	
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
		try
		{
			//log.info("Inside doclose method of PreAuthSmsReportsAction");
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
	  * This method is used to generate the  PreAuth SMS report in MIS Reports Module.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
   public ActionForward doGeneratePreAuthSmsRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try
		 {
			 //log.info("Inside the doGeneratePreAuthSmsRpt method ");
			 setLinks(request);
			 int iNoOfCursor = 2;
			 JasperReport objJasperReport[] =new JasperReport[iNoOfCursor];
			 JasperReport emptyReport;
			 JasperPrint objJasperPrint[] = new JasperPrint[iNoOfCursor];
			 JasperPrint jasperPrint;
			 ArrayList<Object> alJasperPrintList = new ArrayList<Object>();
			 ByteArrayOutputStream boas=new ByteArrayOutputStream();
			 ReportsDataSource ttkReportsDataSource = null;
			 String jrxmlFiles[] = new String[]
				                                 {
						"reports/misreports/PreAuthSmsSummaryReport.jrxml",
						"reports/misreports/PreAuthSmsDetailReport.jrxml"
				                                 };
		     String strSheetNames[] = new String[]
				                                    {
						"Pre-Auth SMS Summary Report","Pre-Auth SMS Detail Report" };
			 String strFrom = request.getParameter("sFrom");
			 String strTo = request.getParameter("sTo");
			 String strReportType = request.getParameter("reportType");
			 String strPeriod = "";
			 if(strFrom =="")
			 {
				 strPeriod = request.getParameter("sPeriod");
			 }//end of if(strFrom =="")
			 else
			 {
				 strPeriod="";
			 }//end of else
			 String strParams[] = new String[] {"|"+request.getParameter("parameter")+"|"+strFrom+"|"+strTo+"|"+strPeriod+"|"					 
			                                   };
			 objJasperReport[0] = JasperCompileManager.compileReport(jrxmlFiles[0]);
			 objJasperReport[1] = JasperCompileManager.compileReport(jrxmlFiles[1]);
			 ttkReportsDataSource = new ReportsDataSource("PreAuthSMSReport",strParams[0],(iNoOfCursor+""));
			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			 HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
			 hashMap.put("sFrom",strFrom);
			 hashMap.put("sTo",strTo);
			 hashMap.put("sReportType",strReportType);
			 if(ttkReportsDataSource.isResultSetArrayEmpty())
				{
					emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
					jasperPrint = JasperFillManager.fillReport(emptyReport,hashMap,new JREmptyDataSource());
					alJasperPrintList.add(jasperPrint);
				}//end of if(ttkReportDataSource.isResultSetArrayEmpty())
			 else
			 {
				 for(int i=0;i<iNoOfCursor;i++)
				 {
						ResultSet rs = ttkReportsDataSource.getResultData(i);
						if(rs.next())
						{
							rs.beforeFirst();
							objJasperPrint[i] = JasperFillManager.fillReport(objJasperReport[i],hashMap,new JRResultSetDataSource(rs));
						}//end of if(rs.next())
						else 
						{
							objJasperPrint[i] = JasperFillManager.fillReport(emptyReport,hashMap,new JREmptyDataSource());
						}//end of else
						alJasperPrintList.add(objJasperPrint[i]);
					}//end of for(int i=0;i<iNoOfCursor;i++){
				 
			 }//end of if(ttkReportDataSource.getResultData().next())
			 if(request.getParameter("reportType").equals("EXL"))//if the report type is Excel
			 {
				 JRXlsExporter jExcelApiExporter = new JRXlsExporter();
					jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST,alJasperPrintList);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, "c:\\hay.xls");
					jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, strSheetNames);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
					jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
					jExcelApiExporter.exportReport();
			 }//end of if(request.getParameter("reportType").equals("EXL"))
			 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
			 request.setAttribute("boas",boas);
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request, mapping, new TTKException(exp,"reportdetail"));
		 }//end of catch(Exception exp)		
		 return (mapping.findForward(strReportdisplay));
	 }//end of doGeneratePreAuthSmsRpt()
}//end of PreAuthSmsReportsAction()
