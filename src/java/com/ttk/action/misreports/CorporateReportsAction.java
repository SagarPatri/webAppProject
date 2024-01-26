/**
  * @ (#) CorporateReportsAction.java July 13, 2007
  * Project      : TTK HealthCare Services
  * File         : CorporateReportsAction.java
  * Author       : Ajay Kumar
  * Company      : WebEdge Technologies Pvt.Ltd.
  * Date Created : 
  *
  * @author      :  Ajay Kumar
  * Modified by  : Balakrishna Erram
  * Modified date: April 14, 2009
  * Company      : Span Infotech Pvt.Ltd.
  * Reason       : Code Review
  */

package com.ttk.action.misreports;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;

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
import org.dom4j.Document;
import org.dom4j.Element;

import com.ttk.action.TTKAction;
import com.ttk.business.misreports.ReportManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.misreports.ReportsDataSource;
import com.ttk.dto.misreports.ReportDetailVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This action class is used to display the Corporate Reports.
 */
public class CorporateReportsAction extends TTKAction {
	
	private static final Logger log = Logger.getLogger( CorporateReportsAction.class );
	
	//string for forwarding
    private static final String strCorporateMonitorReports="corporatemonitorreports";
    private static final String strReportdisplay="reportdisplay";
    private static final String strAllCorporateReportsList="allcorporatereportslist";
    
    //Exception Message Identifier
	private static final String strReportExp="report";
	 
	/**
	 * This method is used to load the sub status based on the selected status.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * This method work for based on the Type selected 
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			                            HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doChangeStatus method of CorporateReportsAction");
			setLinks(request);
			DynaActionForm frmCorporateReports = (DynaActionForm) form;
			frmCorporateReports.set("sStatus","ALL");
			frmCorporateReports.set("frmChanged", "changed");
			return this.getForward(strCorporateMonitorReports,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	 }//end of doChangeStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	                          //HttpServletResponse response)
	
	/**
	 * This method is used to load the sub status based on the selected status.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 *This method work for based on the Type selected 
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeLabel(ActionMapping mapping,ActionForm form,HttpServletRequest request,
									   HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doChangeLabel method of CorporateReportsAction");
			setLinks(request);
			DynaActionForm frmLabelReports = (DynaActionForm) form;
			frmLabelReports.set("frmChanged", "changed");
//			String strStatus = request.getParameter("sStatus");
			return this.getForward(strCorporateMonitorReports,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	}//end of doChangeLabel(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	 
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
    		 log.debug("Inside the doGenerateReport method of CorporateReportsAction");
    		 setLinks(request);
    		 DynaActionForm frmReportDetail =(DynaActionForm)form;
    		 JasperReport jasperReport,emptyReport;
    		 JasperPrint jasperPrint;
    		 ReportsDataSource ttkReportsDataSource = null;
    		 Document reportsListDoc=null;
    		 UserSecurityProfile userSecurityProfile=new UserSecurityProfile();
    		 userSecurityProfile=(UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile");
    		 ReportDetailVO reportDetailVO =new ReportDetailVO();
    		 ReportManager reportDetailObject=this.getReportManager();
    		 //get the reports list Document
    		 reportsListDoc=TTKCommon.getDocument("MisReports.xml");
    		 List reportsList = null;
    		 Element reportsElement = null;
    		 String strMaxValue = "";
    		 if(reportsListDoc != null)
    		 {
    			 reportsList = (List)reportsListDoc.selectNodes("//report");
    			 if(reportsList != null && !reportsList.isEmpty())
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
    		 }//if(reportsListDoc != null&& !reportsList.isEmpty())
    		 
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
    		 
    		 reportDetailVO.setUSER_ID(userSecurityProfile.getUSER_ID());
    		 reportDetailVO.setUserName(userSecurityProfile.getUserName());
    		 reportDetailVO.setUserLocation(userSecurityProfile.getBranchName());
    		 
    		 reportDetailVO.setStartDate(request.getParameter("startDate"));
    		 reportDetailVO.setEndDate(request.getParameter("endDate"));
    		 reportDetailVO.setReportId((String) frmReportDetail.get("reportID"));
    		 reportDetailVO.setReportName(request.getParameter("reportname"));
    		 reportDetailVO.setReportType((String) frmReportDetail.get("reportType"));
    		 reportDetailVO.setReportLink(request.getParameter("reportlinkname"));
    		 reportDetailVO.setGroupPolicyNo(request.getParameter("sPolicyNo"));
    		 reportDetailVO.setSType(request.getParameter("type"));
    		 reportDetailVO.setsClaimsType(request.getParameter("claimtype"));
    		 reportDetailVO.setSStatus(request.getParameter("status"));
    		 
    		 //call the DAO to save the Report parameter details
    		 int iCount=reportDetailObject.saveReportDetails(reportDetailVO); 
    		 if(iCount>=0)
    		 {
    			 log.info("Inside the doGenerateReport method of CorporateReportsAction in iCount");
    			 
    			 ttkReportsDataSource = new ReportsDataSource(request.getParameter("reportID"),strParam);
    		 }//end of if(iCount>=0)
    		 
    		 // ttkReportsDataSource = new ReportsDataSource(request.getParameter("reportID"),strParam);
    		 try
    		 {
    			 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
    			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
    			 HashMap<String,String> hashMap = new HashMap<String,String>();
    			 ByteArrayOutputStream boas=new ByteArrayOutputStream();
    			 hashMap.put("Start Date",request.getParameter("startDate"));
    			 hashMap.put("End Date",request.getParameter("endDate"));
    			 hashMap.put("sPolicyNo",request.getParameter("sPolicyNo"));
    			 hashMap.put("type",request.getParameter("type"));
    			 hashMap.put("claimtype",request.getParameter("claimtype"));
    			 hashMap.put("status",request.getParameter("status"));
    			 hashMap.put("startlabelvalue",request.getParameter("startlabelvalue"));
    			 hashMap.put("endlabelvalue",request.getParameter("endlabelvalue"));
    			 hashMap.put("parameter",strParam.substring(2,strParam.length()));
    			 if(!(ttkReportsDataSource.getResultData().next()))
    			 {
    				 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
    			 }// end of if(!(ttkReportsDataSource.getResultData().next()))
    			 else
    			 {
    				 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new ReportsDataSource(request.getParameter("reportID"),strParam));
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
    			 else// if report type if PDF
    			 {
    				 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
    				 
    			 }// end of else
    			 // keep the byte array out stream in request scope to write
    			 // in the BinaryStreamServlet
    			 request.setAttribute("boas",boas);
    		 }// end of try
    		 catch (Exception exp)
    		 {
    			 return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
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
	 public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			                      HttpServletResponse response) throws TTKException{
		 try{
			 setLinks(request);
			 log.debug("Inside the doClose method of CorporateReportsAction");
			 ((DynaActionForm)form).initialize(mapping);//reset the form data
			 
			 return this.getForward(strAllCorporateReportsList,mapping,request);
		 }//end of try
		 catch(TTKException expTTK)
    	 {
    		 return this.processExceptions(request, mapping, expTTK);
    	 }// end of catch(TTKException expTTK)
    	 catch(Exception exp)
    	 {
    		 return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
    	 }// end of catch(Exception exp)
	 }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
 
	 /**
		 * Returns the ReportManager session object for invoking methods on it.
		 * @return ReportManager session object which can be used for method invokation
		 * @exception throws TTKException
		 */
	 private ReportManager getReportManager() throws TTKException
	    {
	       ReportManager reportManager = null;
	        try
	        {
	            if(reportManager == null)
	            {
	                InitialContext ctx = new InitialContext();
	               reportManager = (ReportManager) ctx.lookup("java:global/TTKServices/business.ejb3/ReportManagerBean!com.ttk.business.misreports.ReportManager");
	            }//end if
	        }//end of try
	        catch(Exception exp)
	        {
	            throw new TTKException(exp, "tTKReport");
	        }//end of catch
	        return reportManager;
	    }//end getTTKReportManager()
	 
}//end of CorporateReportsAction
