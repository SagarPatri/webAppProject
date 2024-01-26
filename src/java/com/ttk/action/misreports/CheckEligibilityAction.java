package com.ttk.action.misreports;

import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
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
import com.ttk.business.onlineforms.OnlinePreAuthManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.common.CacheObject;

public class CheckEligibilityAction extends TTKAction{
	
	
	private static final Logger log = Logger.getLogger( CheckEligibilityAction.class );
	
	 private static final String strCheckEligibilityLog="checkEligibilityLog";
	 private static final String strReportExp="report";
	 private static final String strReportdisplay="reportdisplay1";
	
	 public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			   HttpServletResponse response) throws TTKException{
		 
		 			try{
		 					setLinks(request);
		 	 			/*	if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		 						{
		 							((DynaActionForm) form).initialize(mapping);// reset the form data
		 						}//end of if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		 						
		 						*/
		 					
		 					((DynaActionForm) form).initialize(mapping);// reset the form data	
		 					
	 					return this.getForward(strCheckEligibilityLog, mapping, request);
		 				}// end of try
		 				catch (TTKException expTTK) {
		 					return this.processExceptions(request, mapping, expTTK);
		 				}// end of catch(TTKException expTTK)
		 				catch (Exception exp) {
		 					return this.processExceptions(request, mapping, new TTKException(exp, strCheckEligibilityLog));
		 				}// end of catch(Exception exp)
	 				}// end of doDefault
	
	
	 
	 
	 public ActionForward changeAuthority(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			   HttpServletResponse response) throws TTKException{
		 
			try{
				log.debug("Inside the changeAuthority method of CheckEligibilityAction");
				  DynaActionForm frmMISReports =(DynaActionForm)form;
					OnlinePreAuthManager onlinePreAuthManager= null;
					onlinePreAuthManager = this.getOnlineAccessManagerObject();
				  ArrayList<Object> hmProvider1 = null;
					ArrayList<CacheObject> hmProvidetLst = null;
				  
		     	String authority = 	frmMISReports.getString("payerCodes");
			
		     	hmProvider1 = onlinePreAuthManager.getInsCompnyList(authority);//for hospital List
		     	hmProvidetLst=(ArrayList<CacheObject>) hmProvider1.get(0);
		     	hmProvidetLst=(ArrayList<CacheObject>) hmProvider1.get(0);
				
				frmMISReports.set("alInsuranceCompany",hmProvidetLst);
				
					return this.getForward(strCheckEligibilityLog, mapping, request);
				}// end of try
				catch (TTKException expTTK) {
					return this.processExceptions(request, mapping, expTTK);
				}// end of catch(TTKException expTTK)
				catch (Exception exp) {
					return this.processExceptions(request, mapping, new TTKException(exp, strCheckEligibilityLog));
				}// end of catch(Exception exp)
		 
	 }
	 
	 public ActionForward doGenerateReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			   HttpServletResponse response) throws TTKException{
		 
			try{
				log.debug("Inside the doGenerateReport method of CheckEligibilityAction");
				  DynaActionForm frmMISReports =(DynaActionForm)form;
				  
					JasperReport mainJasperReport,emptyReport;
		    		TTKReportDataSource mainTtkReportDataSource = null;
		    		JasperPrint mainJasperPrint = null;
				
		    		String payerCodes = request.getParameter("authority") == null || request.getParameter("authority").equals("")?"ALL":request.getParameter("authority");
					String sHospitalName = request.getParameter("sHospitalName") == null || request.getParameter("sHospitalName").equals("")?"ALL" :request.getParameter("sHospitalName");
					String insCompanyName = request.getParameter("insCompanyName")==null || request.getParameter("insCompanyName").equals("")?"ALL" :request.getParameter("insCompanyName");
					String sEnrolmentId = request.getParameter("sEnrolmentId")==null || request.getParameter("sEnrolmentId").equals("")?"ALL" :request.getParameter("sEnrolmentId");
					String startDate = request.getParameter("startDate");
					String endDate = request.getParameter("endDate");
					String reportLink = request.getParameter("reportlinkname");
					
					String strParam ="|"+payerCodes+ "|"+insCompanyName+"|"+sHospitalName+"|"+sEnrolmentId+"|"+startDate+"|"+endDate+"|";	
					
					String jrxmlfile=null;
		    		jrxmlfile=request.getParameter("fileName");
		    		
		    		try
		    		{
		    		emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
	    			
	    			HashMap<String,String> hashMap = new HashMap<String,String>();
	    			
	    			ByteArrayOutputStream boas=new ByteArrayOutputStream();
	    			hashMap.put("parameter",strParam.substring(2,strParam.length()));
	    			
	    			
	    			mainTtkReportDataSource = new TTKReportDataSource("MemberCheckEligibilityParam", strParam);
	    			ResultSet main_report_RS = mainTtkReportDataSource.getResultData();
	    			
	    			
	    			mainJasperReport = JasperCompileManager.compileReport(jrxmlfile);
	    			if (main_report_RS != null && main_report_RS.next()) {
	    				main_report_RS.beforeFirst();
	    				mainJasperPrint = JasperFillManager.fillReport(mainJasperReport, hashMap, mainTtkReportDataSource);
	    				  				
	    			} else {
	    				
	    				mainJasperPrint = JasperFillManager.fillReport(emptyReport,hashMap, new JREmptyDataSource());
	    			}
	    			
	    			if("EXCEL".equals(request.getParameter("reportType")))
		    		{
	    			
	    			JRXlsExporter jExcelApiExporter = new JRXlsExporter();
    				jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,mainJasperPrint);
    				jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
    				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
    				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
    				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    				jExcelApiExporter.exportReport();
		    		}
	    			
    				request.setAttribute("boas",boas);
    				frmMISReports.set("letterPath", reportLink);
					
				
					//return this.getForward(strCheckEligibilityLog, mapping, request);
				}// end of try
			catch(Exception exp)
        	{
        		return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
        	}
    		return (mapping.findForward(strReportdisplay));
    	}
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
    	}
		 
	 }
	 
	 
	 
	 
	 
	 public ActionForward closeEligibility(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			   HttpServletResponse response) throws TTKException{
		 
		 			try{
		 					
		 				   ((DynaActionForm) form).initialize(mapping);// reset the form data
		 				
		 					return this.getForward("closeEligibility", mapping, request);
		 				}// end of try
		 				catch (TTKException expTTK) {
		 					return this.processExceptions(request, mapping, expTTK);
		 				}// end of catch(TTKException expTTK)
		 				catch (Exception exp) {
		 					return this.processExceptions(request, mapping, new TTKException(exp, strCheckEligibilityLog));
		 				}// end of catch(Exception exp)
	 				}// end of doDefault 
	 
	 
	 
	 
	 
	 
	  private OnlinePreAuthManager getOnlineAccessManagerObject() throws TTKException
	    {
	    	OnlinePreAuthManager onlinePreAuthManager= null;
	        try
	        {
	            if(onlinePreAuthManager == null)
	            {
	                InitialContext ctx = new InitialContext();
	                //onlineAccessManager = (OnlineAccessManager) ctx.lookup(OnlineAccessManager.class.getName());
	                onlinePreAuthManager = (OnlinePreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlinePreAuthManagerBean!com.ttk.business.onlineforms.OnlinePreAuthManager");
	            }//end if
	        }//end of try
	        catch(Exception exp)
	        {
	            throw new TTKException(exp, "onlinePreAuth");
	        }//end of catch
	        return onlinePreAuthManager;
	    }//end of getOnlineAccessManagerObject()
	 

}
