
/**
 * @ (#) CitibankClaimsReportAction.java
 * Project      : TTK HealthCare Services
 * File         : CitibankClaimsReportAction.java
 * Author       : Balaji C R B
 * Company      : Span Infotech India Pvt. Ltd
 * Date Created : 
 *
 * @author       : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.misreports;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
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
import com.ttk.common.misreports.TTKInsDoBOSelect;
import com.ttk.common.reports.JRTxtExporter;
import com.ttk.common.reports.JRTxtExporterParameter;
import com.ttk.dao.impl.misreports.ReportsDataSource;


/**
 * This action class is used to display the Citibank Claims Reports.
 */
public class CitibankClaimsReportAction extends TTKAction {
	
	private static final Logger log = Logger.getLogger( CitibankClaimsReportAction.class );
	
	//string for forwarding
	private static final String strCitibankClaimsreport= "citibankclaimsreport";
	private static final String strallclaimsreportslist="allclaimsreportslist";
	private static final String strReportdisplay="reportdisplay1";
	
	//Exception Message Identifier
	private static final String strReportExp="citibankclaimsreport";
	
	/**
	 * This method is used to intialize the form while coming to the Citibank Claims report parameter
	 * screen for the first time.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		try{
			setLinks(request);
			log.debug("inside doDefault method of CitibankClaimsReportAction");
			DynaActionForm frmCitiClaimsRpt =(DynaActionForm)form;
			if("Y".equals(request.getParameter("Entry"))){
				frmCitiClaimsRpt.initialize(mapping);
			}//end of if("Y".equals(request.getParameter("Entry")))			
			return this.getForward(strCitibankClaimsreport,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                     	//HttpServletResponse response)
	/**
	 * This method is used to load Insurance Company list  based on theVidal Health TPABranch selected.
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
			log.debug("Inside the doChangeInsCompany method of ClaimsReportsAction");
			setLinks(request);
			DynaActionForm frmCitiClaimsRpt = (DynaActionForm) form;
			ArrayList<Object> alInsCompanyList = new ArrayList();
			alInsCompanyList = TTKInsDoBOSelect.getInsCompanyDetail(frmCitiClaimsRpt);
			frmCitiClaimsRpt.set("openReport","no");
			frmCitiClaimsRpt.set("alInsCompanyList", alInsCompanyList);
			return this.getForward(strCitibankClaimsreport,mapping,request);
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
	public ActionForward doGenerateCitibankClaimsReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		try{
			log.info("Inside the doGenerateCitibankClaimsReport method of CitibankClaimsReportsAction");
			setLinks(request);
			DynaActionForm frmCitiClaimsRpt = (DynaActionForm)form;
			String strReportType = frmCitiClaimsRpt.getString("sReportType");
			String strStartDate= frmCitiClaimsRpt.getString("sStartDate");
			String strEndDate = frmCitiClaimsRpt.getString("sEndDate");
			String strTTKBranchCode = frmCitiClaimsRpt.getString("tTKBranchCode");
			String strInsCompcode = frmCitiClaimsRpt.getString("sInsCompany");
			String strPolicyType = frmCitiClaimsRpt.getString("sPolicyType");
			String strReportFormat = frmCitiClaimsRpt.getString("sReportFormat");
			String strJRXMLFile = "";
			String strReportId="";
			StringBuffer strBuff = new StringBuffer("|");
			String strParameter = "";
			strBuff.append(strStartDate+ "|");
			strBuff.append(strEndDate+ "|");
			strBuff.append(strTTKBranchCode+ "|");
			strBuff.append(strInsCompcode+ "|");
			strBuff.append(strPolicyType+ "|");
			strParameter=strBuff.toString();
			//log.info("parameter is " + strParameter);
			JasperReport jasperReport,emptyReport;
			JasperPrint jasperPrint = null;
			ReportsDataSource ttkReportsDataSource = null;
			HashMap<Object,Object> hashMap = new HashMap<Object,Object>();
			ByteArrayOutputStream boas = new ByteArrayOutputStream();
			/*log.info("******");
			 log.info(strReportType);
			 log.info(strStartDate);
			 log.info(strEndDate);
			 log.info(strTTKBranchCode);
			 log.info(strInsCompcode);
			 log.info(strPolicyType);
			 log.info(strReportFormat);
			 log.info("*******");*/
			if("Claims Intimation".equals(strReportType)){
				if("EXCEL".equals(strReportFormat)){
					strJRXMLFile="reports/misreports/ClaimsIntimation.jrxml";
				}//end of if("EXCEL".equals(strReportFormat)){
				else {
					strJRXMLFile="reports/misreports/ClaimsIntimation.jrxml";
				}//end of else
				strReportId="ClaimsIntimation";				
			}//end of if("Claims Intimation".equals(strReportType))
			else if("Claims Rejection".equals(strReportType)){
				if("EXCEL".equals(strReportFormat)){
					strJRXMLFile="reports/misreports/ClaimsRejection.jrxml";
				}//end of 
				else {
					strJRXMLFile="reports/misreports/ClaimsRejection.jrxml";
				}//end of else				
				strReportId="ClaimsRejection";
			}//end of else if("Claims Rejection".equals(strReportType))
			else if ("Claims Settlement".equals(strReportType)){
				if("EXCEL".equals(strReportFormat)){
					strJRXMLFile="reports/misreports/ClaimsSettlement.jrxml";
				}//end of 
				else {
					strJRXMLFile="reports/misreports/ClaimsSettlement.jrxml";
				}//end of else				
				strReportId="ClaimsSettlement";
			}//end of else if ("Claims Settlement".equals(strReportType))
			try{
				jasperReport = JasperCompileManager.compileReport(strJRXMLFile);
				emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
				hashMap.put("Start Date",strStartDate);
				hashMap.put("End Date",strEndDate);
				hashMap.put("TTKBranchCode",strTTKBranchCode);
				hashMap.put("InsCompCode",strInsCompcode);
				hashMap.put("PolicyType",strPolicyType);
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
				else if("TXT".equals(strReportFormat))
				{
					JRTxtExporter jrTxtExporter = new JRTxtExporter();
					jrTxtExporter.setParameter(JRTxtExporterParameter.JASPER_PRINT,jasperPrint);
					jrTxtExporter.setParameter(JRTxtExporterParameter.PAGE_COLUMNS,"300");
					jrTxtExporter.setParameter(JRTxtExporterParameter.PAGE_ROWS,"0");
					jrTxtExporter.setParameter(JRTxtExporterParameter.OUTPUT_STREAM,boas);
					jrTxtExporter.exportReport();
				}// end of else if("TXT".equals(strReportFormat))
				request.getSession().setAttribute("boas",boas);
				frmCitiClaimsRpt.set("openReport","yes");
			}//end of try
			catch (JRException e)
			{				
				return this.processExceptions(request, mapping, new TTKException(e,strReportExp));
			}//end of catch (JRException e)
			//return (mapping.findForward(strReportdisplay));
			return this.getForward(strCitibankClaimsreport,mapping,request);
		}// end of try   	
		catch(TTKException expTTK)
		{
			log.debug("inside ttkexception");
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			log.debug("inside exception");
			return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
		}// end of catch(Exception exp)
	}// end of doGenerateCitibankClaimsReport
	
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
			log.debug("Inside the doDisplayReport method of CitibankClaimsReportsAction");
			//((DynaActionForm)form).initialize(mapping);//reset the form data
			ByteArrayOutputStream baos = null;
			baos = (ByteArrayOutputStream)request.getSession().getAttribute("boas");
			request.setAttribute("boas",baos);
			request.getSession().removeAttribute("boas");		
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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws TTKException{
		try{
			setLinks(request);
			log.debug("Inside the doClose method of CitibankClaimsReportsAction");
			((DynaActionForm)form).initialize(mapping);//reset the form data
			request.getSession().removeAttribute("boas");	
			return this.getForward(strallclaimsreportslist,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
		}// end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
}//end of ClaimsReportsAction
