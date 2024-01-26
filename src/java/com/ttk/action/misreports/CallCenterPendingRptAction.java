/**
 * @ (#) CallCenterPendingRptAction.java July 30th, 2008
 * Project      : TTK HealthCare Services
 * File         : CallCenterPendingRptAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : July 30th, 2008
 *
 * @author       : Chandrasekaran J
 * Modified by   : Balakrishna Erram
 * Modified date : April 9, 2009
 * Company       : Span Infotech Pvt.Ltd.
 * Reason        : Code Review
 */

package com.ttk.action.misreports;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;

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

import com.ttk.action.TTKAction;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.misreports.ReportsDataSource;

public class CallCenterPendingRptAction extends TTKAction
{
private static final Logger log = Logger.getLogger( CallCenterPendingRptAction.class );
	
	//string for forwarding
    private static final String strCallCenterReport="callcenterreport";
    private static final String strCallPendingParam="callpendingparam";
    private static final String strReportdisplay="reportdisplay";
   
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
            					   HttpServletResponse response) throws TTKException
    {
    	try{
    		log.debug("Inside the doDefault method of CallCenterPendingRptAction");
    		setLinks(request);
    		if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    			((DynaActionForm) form).initialize(mapping);// reset the form data
    		
    		return this.getForward(strCallCenterReport, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
		}// end of catch(Exception exp)
	}// end of doDefault
    
    /**
	  * This method is used to veiw the reportparam screen.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
 public ActionForward doVeiwCallPending(ActionMapping mapping,ActionForm form,HttpServletRequest request,
         							  HttpServletResponse response) throws TTKException
 {
 	try{
 		log.debug("Inside the doVeiwCallPending method of CallCenterPendingRptAction");
 		setLinks(request);
 		DynaActionForm frmCallCenterRptPending=(DynaActionForm) form;
 		frmCallCenterRptPending.set("sStatus","SPT");
 		return this.getForward(strCallPendingParam, mapping, request);
		}//end of try
 		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
		}//end of catch(Exception exp)
	}//end of doVeiwCallPending
 
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
       							  HttpServletResponse response) throws TTKException
  {
	try{
		log.info("Inside the doGenerateReport method of CallCenterPendingRptAction");
		setLinks(request);
		JasperReport jasperReport,emptyReport;
		JasperPrint jasperPrint;
		ReportsDataSource ttkReportsDataSource = null;
		String strParam = request.getParameter("parameter");
		String jrxmlfile=null;
		if(request.getParameter("reportType").equals("EXL"))
		{
			jrxmlfile=request.getParameter("fileName");
		}// end of if(request.getParameter("reportType").equals("EXL"))
		
		else if(request.getParameter("reportType").equals("PDF"))
		{
			jrxmlfile=request.getParameter("fileName");
		}//else if(request.getParameter("reportType").equals("PDF") 
		log.info("************filename is " + jrxmlfile);
		ttkReportsDataSource = new ReportsDataSource(request.getParameter("reportID"),strParam);
		
		try
		{
			//log.info("inside try");
			jasperReport = JasperCompileManager.compileReport(jrxmlfile);
			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			HashMap<String,String> hashMap = new HashMap<String,String>();
			ByteArrayOutputStream boas=new ByteArrayOutputStream();
			hashMap.put("Start Date",request.getParameter("startDate"));
			hashMap.put("End Date",request.getParameter("endDate"));
			hashMap.put("parameter",strParam.substring(2,strParam.length()));			
			
			if(!(ttkReportsDataSource.getResultData().next()))
			{
				//log.info("inside try1");
				jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
			}//end of else
			else
			{
				//log.info("inside try2");
				jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,
						new ReportsDataSource(request.getParameter("reportID"),strParam));
			}//end of if(ttkReportDataSource.getResultData().next())
			
			if(request.getParameter("reportType").equals("EXL"))//if the report type is Excel
			{
				//log.info("inside try3");
				JRXlsExporter jExcelApiExporter = new JRXlsExporter();
				jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, 
						Boolean.TRUE);
				jExcelApiExporter.exportReport();
			}//end of if(request.getParameter("reportType").equals("EXL"))
			else//if report type if PDF
			{
				//log.info("inside try4");
				JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
			}//end of else
			//keep the byte array out stream in request scope to write in the BinaryStreamServlet
			request.setAttribute("boas",boas);
		}//end of try
		catch (JRException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}//end of catch (Exception e)
		//return this.getForward(strReportdisplay, mapping, request);
		return (mapping.findForward(strReportdisplay));
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
	}//end of catch(Exception exp)
}//end of doGenerateReport

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
		 log.debug("Inside the doClose method of CallCenterPendingRptAction");
		 ((DynaActionForm)form).initialize(mapping);//reset the form data
		 
		 return this.getForward(strCallCenterReport,mapping,request);
	 }//end of try
	 catch(TTKException expTTK)
	 {
		 return this.processExceptions(request, mapping, expTTK);
	 }//end of catch(TTKException expTTK)
	 catch(Exception exp)
	 {
		 return this.processExceptions(request, mapping, new TTKException(exp, "reportdetail"));
	 }//end of catch(Exception exp)
 }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

}//end of CallCenterPendingRptAction
