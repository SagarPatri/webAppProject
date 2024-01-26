
/**
  * @ (#) BufferReportsAction.java July 18, 2007
  * Project      : TTK HealthCare Services
  * File         : BufferReportsAction.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : 
  *
  * @author       : Ajay Kumar
  * Modified by   : Balakrishna Erram
  * Modified date : April 9, 2009
  * Company       : Span Infotech Pvt.Ltd.
  * Reason        : Code Review
  */

package com.ttk.action.misreports;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
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
import org.dom4j.Document;
import org.dom4j.Element;

import com.ttk.action.TTKAction;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.misreports.TTKInsDoBOSelect;
import com.ttk.dao.impl.misreports.ReportsDataSource;

/**
 * This action class is used to display the Claims Reports.
 */
public class BufferReportsAction extends TTKAction {
	
	private static final Logger log = Logger.getLogger( BufferReportsAction.class );
	
//	string for forwarding
    private static final String strBufferSummaryReports="buffersummaryreports";
    private static final String strReportdisplay="reportdisplay";
    private static final String strAllClaimsReportsList="allclaimsreportslist";
    
//  Exception Message Identifier
	 private static final String strReportExp="report";
	 
	 /**
		 * This method is used to load the sub status based on the selected status.
		 * Finally it forwards to the appropriate view based on the specified forward mappings
		 *
		 *This method work for based on the TTKBranch selected the insurance company name and used for idcpsreports.jsp
		 *
		 * @param mapping The ActionMapping used to select this instance
		 * @param form The optional ActionForm bean for this request (if any)
		 * @param request The HTTP request we are processing
		 * @param response The HTTP response we are creating
		 * @return ActionForward Where the control will be forwarded, after this request is processed
		 * @throws Exception if any error occurs
		 */
	    public ActionForward doChangeInsCompany(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		                                HttpServletResponse response) throws TTKException{
			 try{
				 log.debug("Inside the doChangeInsCompany method of BufferReportsAction");
				 setLinks(request);
				 DynaActionForm frmTtkReports = (DynaActionForm) form;
				 ArrayList<Object> alInsCompanyList = new ArrayList<Object>();
				 alInsCompanyList = TTKInsDoBOSelect.getInsCompany(frmTtkReports);
				 frmTtkReports.set("frmChanged", "changed");
				 frmTtkReports.set("alInsCompanyList", alInsCompanyList);
				 return this.getForward(strBufferSummaryReports,mapping,request);
			 }//end of try
			 catch(TTKException expTTK)
			 {
				 return this.processExceptions(request, mapping, expTTK);
			 }//end of catch(TTKException expTTK)
			 catch(Exception exp)
			 {
				 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
			 }//end of catch(Exception exp)
		 }//end of doChangeInsCompany(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	                                  //HttpServletResponse response)
		 
	    /**
		 * This method is used to load the sub status based on the selected status.
		 * Finally it forwards to the appropriate view based on the specified forward mappings
		 *
		 *This method work for based on the insurance company name selected the  DoBo code and used for idcpsreports.jsp
		 *
		 * @param mapping The ActionMapping used to select this instance
		 * @param form The optional ActionForm bean for this request (if any)
		 * @param request The HTTP request we are processing
		 * @param response The HTTP response we are creating
		 * @return ActionForward Where the control will be forwarded, after this request is processed
		 * @throws Exception if any error occurs
		 */
	    public ActionForward doChangeInsComDoBo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		                                HttpServletResponse response) throws TTKException{
			 try{
				 log.debug("Inside the doChangeInsComDoBo method of BufferReportsAction");
				 setLinks(request);
				 DynaActionForm frmTtkReports = (DynaActionForm) form;
				 ArrayList<Object> alDoBoList = new ArrayList<Object>();
				 alDoBoList = TTKInsDoBOSelect.getInsCompDoBo(frmTtkReports);
				 frmTtkReports.set("frmChanged", "changed");
				 frmTtkReports.set("alDoBoList", alDoBoList);
				 return this.getForward(strBufferSummaryReports,mapping,request);
			 }//end of try
			 catch(TTKException expTTK)
			 {
				 return this.processExceptions(request, mapping, expTTK);
			 }//end of catch(TTKException expTTK)
			 catch(Exception exp)
			 {
				 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
			 }//end of catch(Exception exp)
		 }//end of doChangeInsComDoBo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    							  //HttpServletResponse response)
		 
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
	    		log.debug("Inside the doGenerateReport method of BufferReportsAction");
	    		setLinks(request);
	    		JasperReport jasperReport,emptyReport;
	    		JasperPrint jasperPrint;
	    		ReportsDataSource ttkReportsDataSource = null;
	    		Document reportsListDoc=null;
	    		//get the reports list Document
	    		reportsListDoc=TTKCommon.getDocument("MisReports.xml");
	    		List<Object> reportsList = null;
	    		Element reportsElement = null;
	    		String strMaxValue = "";
	    		if(reportsListDoc != null)
	    		{
	    			reportsList = (List)reportsListDoc.selectNodes("//report");
	    			if(reportsList != null && reportsList.size() > 0)
	    			{
	    				for(int i=0 ;i<reportsList.size() ;i++)
	    				{	
	    					reportsElement = (Element)reportsList.get(i);
	    					if(request.getParameter("reportID").equalsIgnoreCase(reportsElement.valueOf("@type")))
	    					{
	    						strMaxValue = reportsElement.valueOf("@maxValue");
	    						
	    					}//end of if(request.getParameter("reportID").equalsIgnoreCase(reportsElement.valueOf("@type")))
	    				}//for(int i=0 ;i<reportsList.size() ;i++)
	    			}//if(reportsList != null && reportsList.size() > 0)
	    		}//if(reportsListDoc != null)
	    		
	    		String strParam = request.getParameter("parameter")+strMaxValue+"|";
	    		
	    		String jrxmlfile=null;
	    		if(request.getParameter("reportType").equals("EXL"))
	    		{
	    			jrxmlfile=request.getParameter("fileName");
	    		}// if(request.getParameter("reportType").equals("EXL")
	    		if(request.getParameter("reportType").equals("PDF"))
	    		{
	    			jrxmlfile=request.getParameter("fileName");
	    		}// if(request.getParameter("reportType").equals("PDF")
	    		ttkReportsDataSource = new ReportsDataSource(request.getParameter("reportID"),strParam);
	    		try
	    		{
	    			jasperReport = JasperCompileManager.compileReport(jrxmlfile);
	    			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
	    			HashMap<String,String> hashMap = new HashMap<String,String>();
	    			ByteArrayOutputStream boas=new ByteArrayOutputStream();
	    			hashMap.put("insCompanyCode",request.getParameter("insCompanyCode"));
	    			hashMap.put("parameter",strParam.substring(2,strParam.length()));
	    			if(request.getParameter("Location").equals("ALL"))
	    			{
	    				hashMap.put("Location",request.getParameter("Location"));
	    			}//end of if(request.getParameter("Location").equals("ALL"))
	    			// hashMap.put("parameter",strParam);
	    			if(!(ttkReportsDataSource.getResultData().next()))
	    			{
	    				jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
	    				
	    			}// end of else
	    			else
	    			{
	    				jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new ReportsDataSource(request.getParameter("reportID"),strParam));
	    				
	    			}// end of if(ttkReportDataSource.getResultData().next())
	    			
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
	    			else// if report type if PDF
	    			{
	    				JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
	    				
	    			}// end of else
	    			// keep the byte array out stream in request scope to write
	    			// in the BinaryStreamServlet
	    			request.setAttribute("boas",boas);
	    		}// end of try
	    		catch (JRException e)
	    		{
	    			e.printStackTrace();
	    		}//end of catch (JRException e)
	    		catch (Exception e)
	    		{
	    			e.printStackTrace();
	    		}// end of catch (Exception e)
	    		return (mapping.findForward(strReportdisplay));
	 			}// end of try
	          catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}// end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
			}// end of catch(Exception exp)
	     }// end of doGenerateReport
	    
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
		 public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws TTKException{
			 try{
				 setLinks(request);
				 log.debug("Inside the doClose method of BufferReportsAction");
				 ((DynaActionForm)form).initialize(mapping);//reset the form data
				 
				 return this.getForward(strAllClaimsReportsList,mapping,request);
			 }//end of try
			 catch(TTKException expTTK){
			 	return this.processExceptions(request, mapping, expTTK);
			 }// end of catch(TTKException expTTK)
			 catch(Exception exp){
				return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
			 }// end of catch(Exception exp)
		 }//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
		
}//end of BufferReportsAction
