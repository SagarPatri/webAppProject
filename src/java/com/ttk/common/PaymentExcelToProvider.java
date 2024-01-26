package com.ttk.common;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import javax.naming.InitialContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.common.CommunicationOptionVO;

public class PaymentExcelToProvider implements Job{

	private static Logger log = Logger.getLogger( PaymentMailToProvider.class );
	private static final String strEmailScheduler="PaymentExcelToProvider";
	CommunicationManager communicationManager = null;
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
	
		
		try {
			communicationManager = this.getCommunicationManagerObject();
			  String strFileName="";
	            JasperReport jasperReport,emptyReport;
	            JasperPrint jasperPrint;
	            String strPath=TTKPropertiesReader.getPropertyValue("ProviderPaymentExceldir");

	             String jrxmlFile = "reports/claims/ProvidesPaymentReport.jrxml";
	             String strParameter="";
	             String strReportID="ProvidePaymentRpt";
	             TTKReportDataSource ttkReportDataSource=null;
	            
	           //  CommunicationOptionVO cOptionVO = null;
			   ArrayList<CommunicationOptionVO> alJasperPrintList = new ArrayList<CommunicationOptionVO>();
			
			 alJasperPrintList = communicationManager.getProviderList();
			 
			 int count =0;
			 
			 if(alJasperPrintList != null)
	            { 
				for(CommunicationOptionVO cOptionVO:alJasperPrintList)
				{
					long longHospseqid=cOptionVO.getProvideSeqId();
					String paymentDate="";	 
					String provideLicenseNo="";
					String strPaymentDate ="";
					strParameter= String.valueOf(longHospseqid);
						
					ttkReportDataSource = new TTKReportDataSource(strReportID, strParameter);
					jasperReport = JasperCompileManager.compileReport(jrxmlFile);
					emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
					HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
						
					//ResultSet rs = ttkReportDataSource.getResultData();
					Date date = new Date();  
					SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
					String strDate= formatter.format(date);  
					ResultSet rs=ttkReportDataSource.getResultData();
					if(rs!=null && rs.next()){
						
						provideLicenseNo= rs.getString("PROVIDER_LICENSE_NO");
						paymentDate= rs.getString("PAYMENT_DATE");
			        	
						strPaymentDate= strPaymentDate.replaceAll("/", "-");
			        	
						rs.beforeFirst();
					     jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
					}
					else
					{
					     jasperPrint = JasperFillManager.fillReport(emptyReport,hashMap,new JREmptyDataSource());
					}//end of else
						
					
					//strFileName= provideLicenseNo+"_"+strPaymentDate;
					strFileName= provideLicenseNo+"_"+strDate;
					String fileLocation=(strPath+strFileName);
					 JRXlsExporter exporterXL = new JRXlsExporter();
					 exporterXL.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
					 exporterXL.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME,(fileLocation+".xls"));
					 exporterXL.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
					 exporterXL.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
					 exporterXL.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
					 exporterXL.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
					 exporterXL.exportReport();
					 count++;
					}
				
				 if(alJasperPrintList.size() == count){
				PaymentMailToProvider mail = new PaymentMailToProvider();
				mail.execute(arg0);
				 }
				
				
				
				
	            }
	            
			
		} catch (TTKException e) {


			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	private CommunicationManager getCommunicationManagerObject() throws TTKException
	{
		CommunicationManager communicationManager = null;
		try
		{
			if(communicationManager == null)
			{
				InitialContext ctx = new InitialContext();
				communicationManager = (CommunicationManager) ctx.lookup("java:global/TTKServices/business.ejb3/CommunicationManagerBean!com.ttk.business.common.messageservices.CommunicationManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strEmailScheduler);
		}//end of catch
		return communicationManager;
	}//end getCommunicationManagerObject()

	

}
