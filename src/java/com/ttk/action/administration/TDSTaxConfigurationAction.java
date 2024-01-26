/**
* @ (#) TDSTaxConfigurationAction.java July 29, 2009
* Project 		: TTK HealthCare Services
* File 			: TDSTaxConfigurationAction.java
* Author 		: Balakrishna Erram
* Company 		: Span Systems Corporation
* Date Created 	: July 29, 2009
*
* @author 		: Balakrishna Erram
* Modified by 	: 
* Modified date : 
* Reason 		: 
*/

package com.ttk.action.administration;

import java.io.ByteArrayOutputStream;
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
import com.ttk.action.table.TableData;
import com.ttk.action.tree.TreeData;
import com.ttk.business.administration.ConfigurationManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.administration.TDSSubCategoryVO;


/**
 * This class is used for displaying the details of Configure Properties.
 */
public class TDSTaxConfigurationAction extends TTKAction{
	
	private static final Logger log = Logger.getLogger( TDSTaxConfigurationAction.class );
	
	// Forward Path
	private static final String strClose="close";
	private static final String strTDSAttrRate="tdsattrrate";
	private static final String strTDSAttrRateexp="tdsattrrateexp";
	private static final String strReportdisplay="reportdisplay";
	
	/**
	 * This method is used to navigate to  detail screen to edit selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewTaxPerc(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws TTKException{
		try{
			log.info("Inside the doViewTaxPerc method of TDSTaxConfigurationAction");
			setLinks(request);
			TreeData treeData =(TreeData) request.getSession().getAttribute("treeData");
			DynaActionForm frmtdsRateList =(DynaActionForm)form;
			String strSelectedRoot=TTKCommon.checkNull(request.getParameter("selectedroot"));
			String strSelectedNode=TTKCommon.checkNull(request.getParameter("selectednode"));
			TDSSubCategoryVO tdsSubCategoryVO=((TDSSubCategoryVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode));
			String strTDSSubCatTypeID=tdsSubCategoryVO.getTdsSubCatTypeID();
			String strTDSSubCatName=tdsSubCategoryVO.getTdsSubCatName();
			ArrayList<Object> altdsCatRateList=new ArrayList<Object>();
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append("TDS Attribute Revisions - ");
			
			TableData tableData =TTKCommon.getTableData(request);
			ConfigurationManager  tdsConfigurationManager =this.getConfManager();
			if(tableData==null){
				//create new table data object
				tableData = new TableData();
			}//end of if(tableData==null) 	
			//create the required grid table
			String strTable = "TDSAttrRevisionTable";
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(strSortID.equals(""))
			{
				tableData.createTableInfo(strTable,null);
				tableData.modifySearchData("search");
			}//end of if(strSortID.equals(""))
			else
			{
				tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));				
				tableData.modifySearchData("sort");//modify the search data	
			}// end of else	
			altdsCatRateList= tdsConfigurationManager.getSelectRevisionList(strTDSSubCatTypeID);
			tableData.setData(altdsCatRateList,"search");
			sbfCaption.append(" [ ").append(strTDSSubCatName).append(" ]");
			request.getSession().setAttribute("tableData",tableData);
			frmtdsRateList.set("caption",sbfCaption.toString());
			//frmtdsRateList.set("caption", strTDSSubCatName);
			frmtdsRateList.set("tdsCatTypeID", strTDSSubCatTypeID);
			frmtdsRateList.set("tdsSubCatName", strTDSSubCatName);
			request.getSession().setAttribute("frmtdsRateList",frmtdsRateList);
			return this.getForward(strTDSAttrRate , mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTDSAttrRateexp));
		}//end of catch(Exception exp)
	}//end of doViewProperties(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	/**
	 * This method is used to search the data with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
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
			log.info("Inside the doSearch method of TDSTaxConfigurationAction");
			setLinks(request);
			TreeData treeData =(TreeData) request.getSession().getAttribute("treeData");
			DynaActionForm frmtdsRateList =(DynaActionForm)form;
			String strSelectedRoot=TTKCommon.checkNull(request.getParameter("selectedroot"));
			String strSelectedNode=TTKCommon.checkNull(request.getParameter("selectednode"));
			TDSSubCategoryVO tdsSubCategoryVO=((TDSSubCategoryVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode));
			String strTDSSubCatTypeID=tdsSubCategoryVO.getTdsSubCatName();
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append("TDS Attribute Revisions - ");
			log.info("TDS Category Type ID is :"+strTDSSubCatTypeID);
			ArrayList<Object> altdsCatRateList=new ArrayList<Object>();
			ArrayList<Object> altdsCatRateParams=new ArrayList<Object>();
			altdsCatRateParams.add(strTDSSubCatTypeID);
			TableData tableData =TTKCommon.getTableData(request);
			ConfigurationManager  tdsConfigurationManager =this.getConfManager();
			if(tableData==null){
				//create new table data object
				tableData = new TableData();
			}//end of if(tableData==null) 	
			//create the required grid table
			String strTable = "TDSAttrRevisionTable";
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(strSortID.equals(""))
			{
				tableData.createTableInfo(strTable,null);
				tableData.modifySearchData("search");
			}//end of if(strSortID.equals(""))
			else
			{
				tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableData.setSearchData(altdsCatRateParams);
				tableData.modifySearchData("sort");//modify the search data	
			}// end of else	
			altdsCatRateList= tdsConfigurationManager.getSelectRevisionList(strTDSSubCatTypeID);
			sbfCaption.append(" [ ").append(strTDSSubCatTypeID).append(" ]");
			tableData.setData(altdsCatRateList,"search");
			request.getSession().setAttribute("tableData",tableData);
			//frmtdsRateList.set("caption", strTDSSubCatTypeID);
			frmtdsRateList.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmtdsRateList",frmtdsRateList);
			return this.getForward(strTDSAttrRate , mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTDSAttrRateexp));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside the doClose method of TDSTaxConfigurationAction");
			setLinks(request);
//			return (mapping.findForward(strClose));
			return this.getForward(strClose, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTDSAttrRateexp));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the TDSConfigurationManager session object for invoking methods on it.
	 * @return TDSConfigurationManager session object which can be used for method invokation 
	 * @exception throws TTKException  
	 */
	private ConfigurationManager getConfManager() throws TTKException
	{
		ConfigurationManager ConfManager = null;
		try 
		{
			if(ConfManager == null)
			{
				InitialContext ctx = new InitialContext();
				ConfManager = (ConfigurationManager) ctx.lookup("java:global/TTKServices/business.ejb3/ConfigurationManagerBean!com.ttk.business.administration.ConfigurationManager");
			}//end if
		}//end of try 
		catch(Exception exp) 
		{
			throw new TTKException(exp, strTDSAttrRateexp);
		}//end of catch
		return ConfManager;
	}//end getTDSConfManager()
	
	/**
	  * This method is used to generate Hospital Wise Report
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward showHospitalReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 											HttpServletResponse response) throws TTKException
     {
		 try{
			 log.info("Inside the showHospitalReport method of TDSTaxConfigurationAction");
			 setLinks(request);
			 JasperReport jasperReport,emptyReport;
			 JasperPrint jasperPrint;
			 TTKReportDataSource ttkReportDataSource = null;			 
			 String strReportID =request.getParameter("reportID");	
			 String jrxmlfile=request.getParameter("fileName"); 			 
			 ttkReportDataSource = new TTKReportDataSource(strReportID);			 
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
			 return this.processExceptions(request, mapping, new TTKException(exp,strTDSAttrRateexp));
		 }//end of catch(Exception exp)
	 }//end of showHospitalReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
	 
	 
	 
}//end of TDSTaxConfigurationAction class