/**
  * @ (#) MISFinanceReportsAction.java
  * Project      : TTK HealthCare Services
  * File         : MISFinanceReportsAction.java
  * Author       : Balaji C R B
  * Company      : Spam Infotech India Pvt. Ltd
  * Date Created : 
  *
  * @author      : Balaji C R B
  * Modified by  : Balakrishna Erram
  * Modified date: April 15, 2009
  * Company      : Span Infotech Pvt.Ltd.
  * Reason       : Code Review
  */

package com.ttk.action.misreports;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
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
import com.ttk.common.exception.TTKException;
import com.ttk.common.misreports.TTKInsDoBOSelect;
import com.ttk.dao.impl.misreports.ReportsDataSource;

/**
 * This action class is used to display the MIS Reports-Finance
 */
public class MISFinanceReportsAction extends TTKAction {
	
	private static final Logger log = Logger.getLogger( MISFinanceReportsAction.class );
	
	//string for forwarding    
	private static final String strReportdisplay="reportdisplay";
    private static final String strAllFinanceReportsList="allfinancereportslist";
    private static final String strDOBOClaimsDetailReports="doboclaimsdetailreport";
    
    //Exception Message Identifier
	private static final String strReportExp="report";
	 
	/**
	 * This method is used to load Insurance Company based on theVidal Health TPABranch selected.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeBranch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			                            HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doChangeBranch method of MISFinanceReportsAction");
			setLinks(request);
			DynaActionForm frmMISFinanceReports = (DynaActionForm) form;
			ArrayList<Object> alInsCompanyList = new ArrayList<Object>();
			alInsCompanyList = TTKInsDoBOSelect.getInsCompanyDetail(frmMISFinanceReports);
			frmMISFinanceReports.set("frmChanged", "changed");
			frmMISFinanceReports.set("alInsCompanyList", alInsCompanyList);
			frmMISFinanceReports.set("alDoBoList",null);
			frmMISFinanceReports.set("openReport","No");
			request.getSession().removeAttribute("boas");
			return this.getForward(strDOBOClaimsDetailReports,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	 }//end of doChangeBranch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	                          //HttpServletResponse response)
	
	/**
	 * This method is used to load Do/Bo code based on the Insurance Company selected
	 * Finally it forwards to the appropriate view based on the specified forward mappings
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
			log.debug("Inside the doChangeInsCompany method of MISFinanceReportsAction");
			setLinks(request);
			DynaActionForm frmMISFinanceReports = (DynaActionForm) form;
			ArrayList<Object> alDoBoList = new ArrayList<Object>();
			alDoBoList = TTKInsDoBOSelect.getInsuranceCompanyDoBoCode(frmMISFinanceReports);
			frmMISFinanceReports.set("frmChanged", "changed");
			frmMISFinanceReports.set("alDoBoList", alDoBoList);
			frmMISFinanceReports.set("openReport","No");
			request.getSession().removeAttribute("boas");
			return this.getForward(strDOBOClaimsDetailReports,mapping,request);
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
			log.debug("Inside the doGenerateReport method of MISFinanceReportsAction");
			setLinks(request);
			DynaActionForm frmMISFinanceReports = (DynaActionForm)form;
			String tTKBranchCode = frmMISFinanceReports.getString("tTKBranchCode");
			String insCompanyCode = frmMISFinanceReports.getString("insCompanyCode");
			String insBoDoCode = frmMISFinanceReports.getString("insBoDoCode");
			String sChequeFromDate = frmMISFinanceReports.getString("sChequeFromDate");
			String sChequeToDate =frmMISFinanceReports.getString("sChequeToDate");
			String strReportFormat=frmMISFinanceReports.getString("reportFormat");
			String strJRXMLFile = "";
			String strReportId="DoBOClaimsDetail";
			StringBuffer strBuff = new StringBuffer("|S|");
			String strParameter = "";
			strBuff.append(tTKBranchCode+ "|");
			strBuff.append(insCompanyCode+ "|");
			strBuff.append(insBoDoCode+ "|");
			strBuff.append(sChequeFromDate+ "|");
			strBuff.append(sChequeToDate+ "|");
			strParameter=strBuff.toString();
			log.debug("parameter is " + strParameter);
			JasperReport jasperReport,emptyReport;
			JasperPrint jasperPrint = null;
			ReportsDataSource ttkReportsDataSource = null;
			HashMap<Object,Object> hashMap = new HashMap<Object,Object>();
			ByteArrayOutputStream boas = new ByteArrayOutputStream();
			if("EXCEL".equals(strReportFormat)){
				strJRXMLFile="reports/finance/ClaimsDetailEXL.jrxml";
			}//emd of if("EXL".equals(strReportFormat))
			else if("PDF".equals(strReportFormat)){
				strJRXMLFile="reports/finance/ClaimsDetail.jrxml";
			}//end of else if("PDF".equals(strReportFormat))				
			try{
				log.debug("jrxmlfile is " + strJRXMLFile);
				jasperReport = JasperCompileManager.compileReport(strJRXMLFile);
				emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
				hashMap.put("Start Date",sChequeFromDate);
				hashMap.put("End Date",sChequeToDate);
				hashMap.put("TTKBranchCode",tTKBranchCode);
				hashMap.put("InsCompCode",insCompanyCode);
				hashMap.put("DoBo",insBoDoCode);
				ttkReportsDataSource = new ReportsDataSource(strReportId,strParameter);
				if(!(ttkReportsDataSource.getResultData().next()))
				{
					jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
				}// end of else
				else
				{
					ttkReportsDataSource.getResultData().beforeFirst();
					jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportsDataSource);
				}// end of if(ttkReportDataSource.getResultData().next())
				if("EXCEL".equals(strReportFormat))// if the report type is Excel
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
				else if("PDF".equals(strReportFormat))
				{
					JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
				}// end of else if("TXT".equals(strReportFormat))
				request.getSession().setAttribute("boas",boas);
				frmMISFinanceReports.set("openReport","yes");
			}//end of try
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
			}// end of catch(Exception exp)	 
			return (mapping.findForward(strDOBOClaimsDetailReports));
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
 	 * This method is used to display the report set as ByteArrayOutputStream object in the session.
 	 * Finally it forwards to Report generating servlet
 	 *
 	 * @param mapping The ActionMapping used to select this instance
 	 * @param form The optional ActionForm bean for this request (if any)
 	 * @param request The HTTP request we are processing
 	 * @param response The HTTP response we are creating
 	 * @return ActionForward Where the control will be forwarded, after this request is processed
 	 * @throws Exception if any error occurs
 	 */
 	public ActionForward doDisplayReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws TTKException{
 		try{
 			setLinks(request);
 			log.debug("Inside the doDisplayReport method of MISFinanceReportsAction");
 			ByteArrayOutputStream baos = null;
 			baos = (ByteArrayOutputStream)request.getSession().getAttribute("boas");
 			request.setAttribute("boas",baos);
 			return mapping.findForward(strReportdisplay);
 		}//end of try
 		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
		}// end of catch(Exception exp)
 	}//end of doDisplayReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
 	
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
			 log.debug("Inside the doClose method of MISFinanceReportsAction");
			 ((DynaActionForm)form).initialize(mapping);//reset the form data		 	
			 return this.getForward(strAllFinanceReportsList,mapping,request);
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
}//end of MISFinanceReportsAction
