/**
 * @ (#) DailyTransferAction.java August 8, 2009
 * Project      : TTK HealthCare Services
 * File         : DailyTransferAction.java
 * Author       : Navin Kumar R
 * Company      : Span Systems Corporation
 * Date Created : August 8, 2009
 *
 * @author       : 
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.finance;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
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
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.finance.TDSRemittanceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.finance.DailyTransferVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for below purpose:
 * 1. Search the List of claims.
 * 2. Generate Daily Transfer to the claims.
 */
public class DailyTransferAction extends TTKAction {

	private static final Logger log = Logger.getLogger(DailyTransferAction.class );

	//Modes.
	private static final String strPageBackward="Backward";
	private static final String strPageForward="Forward";
	
	//Action mapping forwards.
	private static final String strDailyTransferList="dailytransferlist";
	private static final String strGenerateDailyTransfer="generatedailytransfer";
	private static final String strReportdisplay="reportdisplay";
	
	//Exception Message Identifier
	private static final String strDailyTransfer="dailytransfer";
	
	private static final String strDailyTransferTable="DailyTransferTable";
	private static final String strTableData="tableData";
	
	/**
	 * This method is used to initialize the search grid.
	 * Finally it forwards to the appropriate view based on the specified forward mappings.
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													 HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doDefault method of DailyTransferAction");
			setLinks(request);
			String strForward = "";
			String strTable = "";
			if("Y".equals(TTKCommon.checkNull(request.getParameter("Entry"))))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if("Y".equals(TTKCommon.checkNull(request.getParameter("Entry"))))
			//get the table data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			strForward = strDailyTransferList;
			strTable = strDailyTransferTable;
			tableData.createTableInfo(strTable,new ArrayList<Object>());
			request.getSession().setAttribute(strTableData,tableData);			
			
			DynaActionForm frmViewDailyTransfer= (DynaActionForm)form;
			frmViewDailyTransfer.initialize(mapping);						
			frmViewDailyTransfer.set("sEndDate", TTKCommon.getFormattedDate(new Date()));			
			request.getSession().setAttribute("frmViewDailyTransfer",frmViewDailyTransfer);
			
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDailyTransfer));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
	
	/**
	 * This method is used to search the data with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings.
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doSearch method of DailyTransferAction");
			setLinks(request);
			String strForward = "";
			String strTable = "";
			strForward = strDailyTransferList;
			strTable = strDailyTransferTable;
			DynaActionForm frmDailyTransfer = (DynaActionForm)form;
			log.info("sDailyRemitStatusID "+frmDailyTransfer.get("sDailyRemitStatusID"));
			if("Y".equals(TTKCommon.checkNull(request.getParameter("Entry")))){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if("Y".equals(TTKCommon.checkNull(request.getParameter("Entry"))))
			//get the session bean from the bean pool for each executing thread
			TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
			//get the table data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!"".equals(strPageID) || !"".equals(strSortID))
			{
				if(!"".equals(strPageID))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strForward);
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo(strTable,null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form));
				if(TTKCommon.checkNull(frmDailyTransfer.get("sDailyRemitStatusID")).equals("TDSC"))
				{
					((Column)((ArrayList)tableData.getTitle()).get(6)).setVisibility(false);
				}					
				tableData.modifySearchData("search");
			}//end of else
			
			ArrayList<Object> alDailyTransferList = new ArrayList<Object>();
			alDailyTransferList = tdsRemittanceManager.getDailyTransferList(tableData.getSearchData());
			tableData.setData(alDailyTransferList, "search");
			//set the table data object to session
			request.getSession().setAttribute(strTableData,tableData);
			//finally return to the grid screen
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDailyTransfer));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
							//HttpServletResponse response)
	
	/**
	 * this method will add search criteria fields and values to the arraylist and will return it.
	 * @param frmViewDailyTransfer formbean which contains the search fields
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmViewDailyTransfer) throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();		
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewDailyTransfer.get("sInsuranceCompanyID")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewDailyTransfer.get("sDailyRemitStatusID")));
		alSearchParams.add((String)frmViewDailyTransfer.get("sStartDate"));
		alSearchParams.add((String)frmViewDailyTransfer.get("sEndDate"));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmProductList)
	
	/**
	 * This method is used to get the previous set of records with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													  HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doBackward method of DailyTransferAction");
			setLinks(request);
			String strForward = "";
			strForward = strDailyTransferList;
			//get the session bean from the bean pool for each excecuting thread
			TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strPageBackward);
			ArrayList<Object> alDailyTransferList = tdsRemittanceManager.getDailyTransferList(tableData.getSearchData());
			tableData.setData(alDailyTransferList, strPageBackward);
			request.getSession().setAttribute(strTableData,tableData);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDailyTransfer));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest
			//request,HttpServletResponse response)

	/**
	 * This method is used to get the next set of records with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													 HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doForward method of DailyTransferAction");
			setLinks(request);
			String strMapForward = "";
			strMapForward = strDailyTransferList;
			//get the session bean from the bean pool for each excecuting thread
			TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strPageForward);
			ArrayList<Object> alDailyTransferList = tdsRemittanceManager.getDailyTransferList(tableData.getSearchData());
			tableData.setData(alDailyTransferList, strPageForward);
			request.getSession().setAttribute(strTableData,tableData);
			return this.getForward(strMapForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDailyTransfer));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
	
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
	public ActionForward doGenerateDailyTransfer(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doGenerateDailyTransfer method of DailyTransferAction");
			setLinks(request);
			DynaActionForm frmDailyTransferDetail= (DynaActionForm)form;
			frmDailyTransferDetail.initialize(mapping);
			frmDailyTransferDetail = (DynaActionForm)FormUtils.setFormValues("frmDailyTransferDetail", new DailyTransferVO(), 
																					 this, mapping, request);
			StringBuffer sbfId = new StringBuffer("|");
			sbfId.append(populateCheckBoxId(request, (TableData)request.getSession().getAttribute("tableData")));
			sbfId.append("|");
			frmDailyTransferDetail.set("frmSelectedIds", sbfId.toString());			
			request.getSession().setAttribute("frmDailyTransferDetail",frmDailyTransferDetail);
			return this.getForward(strGenerateDailyTransfer, mapping, request);
		}//end of try		
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDailyTransfer));
		}//end of catch(Exception exp)
	}//end of doGenerateDailyTransfer(ActionMapping mapping,ActionForm form,HttpServletRequest request,
							//HttpServletResponse response)
	
	/**
	 * This method is used to update the record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
								HttpServletResponse response) throws TTKException{
		try{
			setLinks(request);
			log.debug("Inside the doSave method of DailyTransferAction");
			ArrayList<Object> alDailyTransferDetailList = new ArrayList<Object>();
			DynaActionForm frmDailyTransferDetail= (DynaActionForm)form;	
			
			/*//if check box is not selected, the same page is retained.
			if(!"on".equalsIgnoreCase(request.getParameter("dailyTransferModify"))) {
				return this.getForward(strGenerateDailyTransfer, mapping, request);
			}//end of if(!"on".equalsIgnoreCase(request.getParameter("dailyTransferModify")))
*/						
			//Update Details.		
			alDailyTransferDetailList.add(TTKCommon.checkNull(frmDailyTransferDetail.get("frmSelectedIds").toString()));
			alDailyTransferDetailList.add(TTKCommon.checkNull(frmDailyTransferDetail.get("dailyTransferDate").toString()));
			alDailyTransferDetailList.add((Long)TTKCommon.getUserSeqId(request));
			
			//get the session bean from the bean pool for each executing thread
			TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
			// calling business layer to save the bank account detail
			int iUpdate=tdsRemittanceManager.setDailyTransfer(alDailyTransferDetailList);
			
			//set the appropriate message
			if(iUpdate>0) {
				request.setAttribute("updated","message.saved");    
			}//end of if(iUpdate>0) 			
			return this.getForward(strGenerateDailyTransfer, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strDailyTransfer));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to reload the screen when the reset button is pressed.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doReset method of DailyTransferAction");
			setLinks(request);
			DynaActionForm frmDailyTransferDetail=(DynaActionForm)form;
			frmDailyTransferDetail.initialize(mapping);
			frmDailyTransferDetail = (DynaActionForm)FormUtils.setFormValues("frmDailyTransferDetail", new DailyTransferVO(), 
					 																	this, mapping, request);
			request.getSession().setAttribute("frmDailyTransferDetail",frmDailyTransferDetail);
			return this.getForward(strGenerateDailyTransfer, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDailyTransfer));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
	
	/**
	 * This method is used to navigate to daily transfer list screen.
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
			log.debug("Inside the doClose method of DailyTransferAction");
			setLinks(request);			
			return this.doDefault(mapping, form, request, response);
		}//end of try		
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDailyTransfer));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
							//HttpServletResponse response)
	
	/**
	 * Returns the TDSRemittanceManager session object for invoking methods on it.
	 * @return TDSRemittanceManager session object which can be used for method invokation
	 * @throws TTKException if any error occurs
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
			throw new TTKException(exp, strDailyTransfer);
		}//end of catch
		return tdsRemittanceManager;
	}//end getTDSRemittanceManagerObject()
	
	/**
	 * Returns a string which contains the pipe separated sequence id's 
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the comma separated sequence id's to be deleted
	 */
	private String populateCheckBoxId(HttpServletRequest request, TableData tableData) throws TTKException
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfId = new StringBuffer();
		try{
			if(strChk!=null&&strChk.length!=0)
			{
				//loop through to populate sequence id's and get the value from session for the matching check
				//box value
				for(int i=0; i<strChk.length;i++)
				{
					if(strChk[i]!=null)
					{
						//extract the sequence id from the value object
						if(i == 0)
						{
							sbfId.append(((DailyTransferVO)tableData.getData().get(Integer.parseInt(strChk[i]))).
									getMasterSeqID().intValue());
						}//end of if(i == 0)
						else
						{
							sbfId.append("|").append(((DailyTransferVO)tableData.getData().get(Integer.parseInt(strChk[i]))).
									getMasterSeqID().intValue());
						}//end of else
					}//end of if(strChk[i]!=null)
				}//end of for(int i=0; i<strChk.length;i++)
			}//end of if(strChk!=null&&strChk.length!=0)
		}catch(Exception exp)
		{
			throw new TTKException(exp, strDailyTransfer);
		}//end of catch
		return sbfId.toString();
	}//end of populateCheckBoxId(HttpServletRequest request, TableData tableData)
	
	/**
	  * This method is used to generate Daily Transfer Report
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	public ActionForward showDailyReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 											HttpServletResponse response) throws TTKException
    {
		try{
			log.info("Inside the showDailyReport method of DailyTransferAction");
			setLinks(request);
			JasperReport jasperReport,emptyReport;
			JasperPrint jasperPrint;			
			TTKReportDataSource ttkReportDataSource = null;			 
			String strReportID = request.getParameter("reportID");	
			String jrxmlfile = request.getParameter("fileName"); 
			String strInsuranceCompany = request.getParameter("insuranceCompany");
			String strStartDate = request.getParameter("startDate");
			String strEndDate = request.getParameter("endDate");
			String strParam = "|"+strInsuranceCompany+"|"+strStartDate+"|"+strEndDate+"|";
			ttkReportDataSource = new TTKReportDataSource(strReportID, strParam);			 
			try
			{
				jasperReport = JasperCompileManager.compileReport(jrxmlfile);
				emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
				HashMap<String, Object> hashMap = new HashMap<String, Object>();
				ByteArrayOutputStream boas=new ByteArrayOutputStream();
				if(ttkReportDataSource.getResultData().next())
				{
					ttkReportDataSource.getResultData().beforeFirst();
					jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap, ttkReportDataSource);					 
				}//end of if(ttkReportDataSource.getResultData().next()))
				else
				{		
					jasperPrint = JasperFillManager.fillReport(emptyReport, hashMap, new JREmptyDataSource());					
				}//end of else				 				 
				 
				JRXlsExporter jExcelApiExporter = new JRXlsExporter();
				jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				jExcelApiExporter.exportReport();				
				request.setAttribute("boas",boas);
			}//end of try
			catch (Exception e)
			{
				e.printStackTrace();
			}//end of catch (Exception e)
			return mapping.findForward(strReportdisplay);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDailyTransfer));
		}//end of catch(Exception exp)
    }//end of showDailyReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
}//end of DailyTransferAction