/**
 * @ (#) ClaimsPendingReportsAction.java July 05, 2008
 * Project      : TTK HealthCare Services
 * File         : ClaimsPendingReportsAction.java
 * Author       : Balakrishna E
 * Company      : Span Systems Corporation
 * Date Created : July 05, 2008
 *
 * @author       :  Balakrishna E
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.reports;

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
import org.dom4j.Document;

import com.ttk.action.TTKAction;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;

public class ClaimsPendingReportsAction extends TTKAction
{
	private static Logger log = Logger.getLogger( ClaimsPendingReportsAction.class );

	//declaration of forward paths
	private static final String strClaimsPenReportList="claimspenreportlist";
	private static final String strBatchDetail="batchdetail";
	private static final String strReportdisplay="reportdisplay";

	//Exception Message Identifier
	private static final String strReportExp="report";

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
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.info("Inside the Default method of ClaimsPendingReportsAction");
			setLinks(request);
			DynaActionForm frmReportList= (DynaActionForm) form;
			log.info("selectRptType value is "+frmReportList.get("selectRptType"));
			((DynaActionForm)form).initialize(mapping);//reset the form data
			return this.getForward(strClaimsPenReportList,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to navigate to detail screen to edit selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doReportType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doReportType method of ReportsAction");
			setLinks(request);
			String selectRptType="";
			DynaActionForm frmReportList= (DynaActionForm) form;
			selectRptType = (String)frmReportList.get("selectRptType");

			((DynaActionForm)form).initialize(mapping);		//reset the form data
			log.info("selectRptType value is :"+selectRptType);
			frmReportList.set("selectRptType",selectRptType); 
			frmReportList.set("reportName","Claims Pending Report");
			return this.getForward(strClaimsPenReportList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	}//end of doReportType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doClose method of ClaimsPendingReportsAction");
			setLinks(request);
			Document reportsListDoc=null;
			((DynaActionForm)form).initialize(mapping);//reset the form data
			//get the reports list Document
			reportsListDoc=TTKCommon.getDocument("ReportsList.xml");
			request.setAttribute("ReportsListDoc",reportsListDoc);
			return mapping.findForward(strClaimsPenReportList);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp) 
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to navigate to  batchdetails screen when Batch Number Icon is clicked.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doBatchNumber(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doBatchNumber method of ClaimsPendingReportsAction");
			setLinks(request);
			return (mapping.findForward(strBatchDetail));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	}//end of doBatchNumber(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
    /**
     * This method is used to clear GroupID, GroupName fields onClick of Clear Icon.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doClearCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside the doClearCorporate method of ClaimsPendingReportsAction");
    		DynaActionForm frmReportList=(DynaActionForm)form;
    		frmReportList.set("groupId","");
    		frmReportList.set("groupName","");
    		return this.getForward(strClaimsPenReportList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
    	}//end of catch(Exception exp)
    }//end of doClearCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
	  * This method is used to generate the TPACommissionRpt report .
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doEFTFinBatchRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
			try{
				log.debug("Inside doEFTFinBatchRpt of ReportsAction");
				setLinks(request);
				int iNoOfCursor = 4;
				JasperReport objJasperReport[] =new JasperReport[iNoOfCursor];
				JasperReport emptyReport;
				JasperPrint objJasperPrint[] = new JasperPrint[iNoOfCursor];
				JasperPrint jasperPrint;
				ArrayList<Object> alJasperPrintList = new ArrayList<Object>();
				ByteArrayOutputStream boas=new ByteArrayOutputStream();
				
				String jrxmlFiles[] = new String[]
				                                 {
						"reports/finance/Annexure.jrxml",
						"reports/finance/Annexure.jrxml",
						"reports/finance/Annexure.jrxml",
						"reports/finance/Annexure.jrxml"																				
                                                  };
				String strSheetNames[] = new String[]{"ABN-Annexure-1","DEUTSCHE-Annexure-2","HDFC-Annexure-3","Axis"};
				HashMap hashMap = new HashMap();				
				//parameters to pass for the procedure
				String strParameter = request.getParameter("parameter");
				//String strParameter = "|"+strInvoiceNbr+"|"+strInsuranceSeqID+"|";
				log.debug("EFT report parameters are :"+strParameter);
				for(int i=0;i<iNoOfCursor;i++)
				{
					objJasperReport[i] = JasperCompileManager.compileReport(jrxmlFiles[i]);
				}//end of for(int i=0;i<iNoOfCursor;i++)
				TTKReportDataSource ttkReportDataSource = null;
				ttkReportDataSource = new TTKReportDataSource("EFTAcceAnne",strParameter,(iNoOfCursor+""));
				if(ttkReportDataSource.isResultSetArrayEmpty())
				{
					emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
					jasperPrint = JasperFillManager.fillReport(emptyReport,hashMap,new JREmptyDataSource());
					alJasperPrintList.add(jasperPrint);
				}//end of if(ttkReportDataSource.isResultSetArrayEmpty())
				else 
				{
					for(int i=0;i<iNoOfCursor;i++){
						//log.debug("i value is :" + i);
						ResultSet rs = ttkReportDataSource.getResultData(i);
						if(rs.next())
						{
							rs.beforeFirst();
							objJasperPrint[i] = JasperFillManager.fillReport(objJasperReport[i],hashMap,new JRResultSetDataSource(rs));//new JREmptyDataSource());
						}//end of if(rs.next())
						else 
						{
							objJasperPrint[i] = JasperFillManager.fillReport(objJasperReport[i],hashMap,new JREmptyDataSource());
						}//end of else
						alJasperPrintList.add(objJasperPrint[i]);
					}//end of for(int i=0;i<iNoOfCursor;i++){
				}//end of else
				//for eporting to stream
				JRXlsExporter jExcelApiExporter = new JRXlsExporter();
				jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST,alJasperPrintList);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, "c:\\abc.xls");
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, strSheetNames);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				jExcelApiExporter.exportReport();				
				request.setAttribute("boas",boas);		
				
				return (mapping.findForward(strReportdisplay));	 
			}catch(TTKException expTTK)
			{
				return this.processExceptions(request , mapping ,expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
	    	{
	    		return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
	    	}//end of catch(Exception exp)
		}//end of doEFTFinBatchRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
}//end of ClaimsPendingReportsAction