/**
 * @ (#) TDSCertificateGenHelper.java May 11, 2010
 * Project 	     : TTK HealthCare Services
 * File          : TDSCertificateGenHelper.java
 * Author        : BALAKRISHNA E
 * Company       : Span Systems Corporation
 * Date Created  : May 11, 2010
 *
 * @author       :	BALAKRISHNA E
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */
package com.ttk.common;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.ttk.business.finance.TDSRemittanceManager;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.finance.CertificateVO;

public class TDSCertificateGenHelper implements Job 
{
	private static final Logger log = Logger.getLogger( TDSCertificateGenHelper.class );

	/**
	 * This method is used to get the list of Tds certificate generate schedule records.
	 * jobExecutionContext. JobExecutionContext Object
	 */
	public void execute(JobExecutionContext jobExecutionContext) {	
		try{
			log.debug("Inside the doGenerateAuthReport method of ReportsAction");
			TDSRemittanceManager tdsRemittanceManager = this.getTDSRemittanceManagerObject();
			ArrayList<CertificateVO> alScheduleList =tdsRemittanceManager.getGenTdsCertSchedList();
			CertificateVO certificateVO =null; 
			if(alScheduleList != null)
			{
				for(int i=0;i<alScheduleList.size(); i++)
				{
					certificateVO = new CertificateVO();
					certificateVO = (CertificateVO)alScheduleList.get(i);
					tdsCertificateGen(certificateVO);
				}//end of for(int i=0;i<alScheduleList.size(); i++)
				saveDocGenInfo(certificateVO.getBatchSeqID());
			}//end of if(alScheduleList != null)

		}//end of try
		catch(Exception e){
			new Exception("tdscertsche");
		}//end of catch(Exception e)
	}//end of execute(JobExecutionContext arg0) 

	/**
	 * This method is used to get the list of Individual Tds certificate generate schedule records.
	 * @throws TTKException if any error occurs
	 */
	public void indTdsCertGeneration() throws TTKException {	
		try{
			log.debug("Inside the doGenerateAuthReport method of ReportsAction");
			TDSRemittanceManager tdsRemittanceManager = this.getTDSRemittanceManagerObject();
			ArrayList<CertificateVO> alScheduleList =tdsRemittanceManager.getGenTdsCertIndSchedList();
			CertificateVO certificateVO =null; 
			if(alScheduleList != null)
			{
				for(int i=0;i<alScheduleList.size(); i++)
				{
					certificateVO = new CertificateVO();
					certificateVO = (CertificateVO)alScheduleList.get(i);
					tdsCertificateGen(certificateVO);
				}//end of for(int i=0;i<alScheduleList.size(); i++)
				saveDocGenInfo(certificateVO.getBatchSeqID());
			}//end of if(alScheduleList != null)

		}//end of try
		catch(Exception e){
			new Exception("tdscertsche");
		}//end of catch(Exception e)
	}//end of indTdsCertGeneration() 
	
	/**
	 * This method is used to invoke the tdsGenReports method.
	 * @param certificateVO, CertificateVO which contains the empanelment number and financial year
	 * @throws TTKException if any error occurs
	 */
	public void tdsCertificateGen(CertificateVO certificateVO) throws TTKException
	{
		JasperReport jasTDSCertGenRpt,jasTDSAnneSumRpt,jasTDSAnneClmRpt,jasTDSCovLettRpt;
		try{
			StringBuffer sb = new StringBuffer();
			sb.append("|").append(certificateVO.getHospitalID()).append("|").append(certificateVO.getFinanceYear())
																								.append("|").append(certificateVO.getBatchSeqID()).append("|").append(certificateVO.getTdsBatchQuarter()).append("|");
			TTKReportDataSource ttkTDSCertGenDS=null;
			TTKReportDataSource ttkTDSCertGenSubDS=null;
			TTKReportDataSource ttkRptTDSAnneSumDS=null;
			TTKReportDataSource ttkRptTDSAnneClmDS=null;
			TTKReportDataSource ttkRptTDSCovLettDS=null;

			ttkTDSCertGenDS = new TTKReportDataSource("TDSCertGen",sb.toString());
			ttkTDSCertGenSubDS = new TTKReportDataSource("AckQuaterInfo",sb.toString());
			ttkRptTDSAnneSumDS = new TTKReportDataSource("TDSAnneSum",sb.toString());
			ttkRptTDSAnneClmDS = new TTKReportDataSource("TDSAnneClmWise",sb.toString());
			ttkRptTDSCovLettDS = new TTKReportDataSource("TDSCovLett",sb.toString());

			String strEmpanelNo = certificateVO.getHospitalID();
			String strQuarter= null;
			int iFinYear = Integer.parseInt(certificateVO.getFinanceYear());
			long lngBatchNo = certificateVO.getBatchSeqID();
			int iFinTo = iFinYear+1;
			 if(certificateVO.getTdsBatchQtrDesc().equals("Annual"))
			 {
				 strQuarter="A";
			 }//end of if(certificateVO.getTdsBatchQtrDesc().equals("Annual"))
			 else
			 {
				 strQuarter=certificateVO.getTdsBatchQtrDesc();
			 }//end of else
			StringBuffer sbTDSCertGenFile = new StringBuffer();
			StringBuffer sbTDSAnneSumFile = new StringBuffer();
			StringBuffer sbTDSAnneClmFile = new StringBuffer();
			StringBuffer sbTDSCovLettFile = new StringBuffer();

			sbTDSCertGenFile.append(TTKPropertiesReader.getPropertyValue("PdfPendPrint")).append(strEmpanelNo).append("_")
			.append(lngBatchNo).append("_").append(iFinYear).append("-").append(iFinTo).append("_").append(strQuarter).append("_1_Certificate.pdf");
			sbTDSAnneSumFile.append(TTKPropertiesReader.getPropertyValue("PendDigitalSign")).append(strEmpanelNo).append("_")
//			.append(lngBatchNo).append("_").append(iFinYear).append("-").append(iFinTo).append("_2_AnnexureSummary.xls");
			.append(lngBatchNo).append("_").append(iFinYear).append("-").append(iFinTo).append("_").append(strQuarter).append("_2_AnnexureSummary.pdf");
			sbTDSAnneClmFile.append(TTKPropertiesReader.getPropertyValue("ExcelPendPrint")).append(strEmpanelNo).append("_")
			.append(lngBatchNo).append("_").append(iFinYear).append("-").append(iFinTo).append("_").append(strQuarter).append("_3_AnnexureClaimwisebreakup.xls");
			sbTDSCovLettFile.append(TTKPropertiesReader.getPropertyValue("PdfPendPrint")).append(strEmpanelNo).append("_")
			.append(lngBatchNo).append("_").append(iFinYear).append("-").append(iFinTo).append("_").append(strQuarter).append("_4_CoveringLetter.pdf");

			jasTDSCertGenRpt = JasperCompileManager.compileReport("reports/tds/TDSCertificate.jrxml");

			jasTDSAnneSumRpt = JasperCompileManager.compileReport("reports/tds/TDSAnnexureSummaryRpt.jrxml");
			jasTDSAnneClmRpt = JasperCompileManager.compileReport("reports/tds/TDSAnnexureClaimsWise.jrxml");
			jasTDSCovLettRpt = JasperCompileManager.compileReport("reports/tds/TDSCoveringLetter.jrxml");

			TTKReportDataSource[] ttkRptTDSDs={ttkTDSCertGenDS,ttkRptTDSAnneSumDS,ttkRptTDSAnneClmDS,ttkRptTDSCovLettDS}; 
			JasperReport[] jasRptArry={jasTDSCertGenRpt,jasTDSAnneSumRpt,jasTDSAnneClmRpt,jasTDSCovLettRpt};
			StringBuffer[] strRptGenFile={sbTDSCertGenFile,sbTDSAnneSumFile,sbTDSAnneClmFile,sbTDSCovLettFile};

			for(int i=0; i<strRptGenFile.length;i++)
			{
				try
				{
					tdsGenReports(ttkRptTDSDs[i],jasRptArry[i],strRptGenFile[i],ttkTDSCertGenSubDS); 
				}//end of try
				catch(TTKException ttkExp)
				{
					throw new TTKException(ttkExp, "error.jrexp");
				}//end of catch(TTKException ttkExp)
				finally
				{
					continue;
				}//end of finally
			}//end of for(int i=0; i<strRptGenFile.length;i++)

		}//end of try
		catch (JRException e)
		{
			e.printStackTrace();
		}//end of catch(JRException e)
		catch(Exception exp)
		{
			throw new TTKException(exp, "error.jrexp");
		}//end of catch(Exception exp)
	}//end of tdsCertificateGen(CertificateVO certificateVO)

	/**
	 * This method is used to generate the list of Tds certificate,Annexure Summary, 
	 * Annexure Claim wise and TDS covering letter.
	 * @param ttkRptTDSDs, TTKReportDataSource which contains the Report DataSource.
	 * @param jasRptArry, JasperReport which contains the Jasper Report Templates.
	 * @param sbRptGenFile, StringBuffer which contains the Generated File Names.
	 * @param ttkTDSCertGenSubDS, TTKReportDataSource which contains the Sub report DataSource.
	 * @throws TTKException if any error occurs
	 */
	public void tdsGenReports(TTKReportDataSource ttkRptTDSDs,JasperReport jasRptArry,StringBuffer sbRptGenFile,
			TTKReportDataSource ttkTDSCertGenSubDS) throws TTKException
	{
		try{
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			JasperPrint jasPrintObj;
			JasperReport jasTDSCertGenSubRpt;
			if(sbRptGenFile.toString().endsWith("Certificate.pdf"))
			{
				jasTDSCertGenSubRpt = JasperCompileManager.compileReport("reports/tds/AckQuarterInfo.jrxml");
				hashMap.put("MyDatasource",ttkTDSCertGenSubDS);
				hashMap.put("AckQuarterInfo",jasTDSCertGenSubRpt);
			}//end of if(strRptGenFile.equals("sbTDSCertGenFile"))
			
			jasPrintObj = JasperFillManager.fillReport( jasRptArry, hashMap,ttkRptTDSDs);	
			
			if(sbRptGenFile.toString().endsWith(".pdf"))
			{
				JasperExportManager.exportReportToPdfFile(jasPrintObj,sbRptGenFile.toString());
			}//end of if(strRptGenFile.equals("sbTDSCertGenFile"))
			if(sbRptGenFile.toString().endsWith(".xls"))
			{
				JRXlsExporter jExlApiExpTDSAnneSum = new JRXlsExporter();
				jExlApiExpTDSAnneSum.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasPrintObj);
				jExlApiExpTDSAnneSum.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, sbRptGenFile.toString());
				jExlApiExpTDSAnneSum.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				jExlApiExpTDSAnneSum.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				jExlApiExpTDSAnneSum.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				jExlApiExpTDSAnneSum.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				jExlApiExpTDSAnneSum.exportReport();
			}//end of if(sbRptGenFile.toString().endsWith(".xls"))
			log.info("done");
			/*if(sbRptGenFile.toString().endsWith(".doc"))
			{
				JRRtfExporter jrRtfExporter = new JRRtfExporter();
				jrRtfExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasPrintObj);
				jrRtfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, sbRptGenFile.toString());
				jrRtfExporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
				jrRtfExporter.exportReport();
			}//end of if(sbRptGenFile.toString().endsWith(".doc"))
*/		}//end of try
		catch (JRException e)
		{
			e.printStackTrace();
		}//end of catch(JRException e)
		catch(Exception exp)
		{
			throw new TTKException(exp, "error.jrexp");
		}//end of catch(Exception exp)		
		finally
		{
			ttkRptTDSDs = null;
			jasRptArry = null;
			ttkTDSCertGenSubDS = null;
		}//end of finally
	}//end of tdsGenReports(CertificateVO certificateVO)
	
	/**
	 * This method is used to invoke the tdsGenReports method.
	 * @param certificateVO, CertificateVO which contains the empanelment number and financial year
	 * @throws TTKException if any error occurs
	 */
	public void saveDocGenInfo(Long lngBatchSeqID) throws TTKException
	{
		try
		{
			TDSRemittanceManager tdsRemittanceManager = this.getTDSRemittanceManagerObject();
			tdsRemittanceManager.saveDocGenInfo(lngBatchSeqID);
			log.info("record updated");
		}//end of try
		catch(Exception e){
			new Exception("tdscertsche");
		}//end of catch(Exception e)
	}//end of saveDocGenInfo(Long lngBatchSeqID)

	/**
	 * Returns the BankAccountManager session object for invoking methods on it.
	 * @return BankAccountManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private TDSRemittanceManager getTDSRemittanceManagerObject() throws TTKException
	{
		TDSRemittanceManager tdsRemittanceManager = null;
		try
		{
			if(tdsRemittanceManager == null)
			{
				InitialContext ctx = new InitialContext();
				tdsRemittanceManager = (TDSRemittanceManager) ctx.lookup("java:global/TTKServices/business.ejb3/TDSRemittanceManagerBean!com.ttk.business.finance.TDSRemittanceManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "tds");
		}//end of catch
		return tdsRemittanceManager;
	}//end getTDSRemittanceManagerObject()

}//end of TDSCertificateGenHelper
