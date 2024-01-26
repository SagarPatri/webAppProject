/**
* @ (#) BulkPDFAction.java Jan 02, 2013
* Project       : TTK HealthCare Services
* File          : BulkPDFAction.java
* Author        : Manohar 
* Company       : RCS
* Date Created  : Jan 02, 2013

* @author       : KOC 1179 
* Modified by   :
* Modified date :
* Reason :
*/

package com.ttk.action.claims;

import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.naming.InitialContext;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRXmlUtils;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.claims.ClaimManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.claims.BulkGeneratedPDFVO;
import com.ttk.dto.claims.ClaimShortfallVO;
import com.ttk.dto.claims.ShortfallRequestDetailsVO;
import com.ttk.dto.claims.ShortfallStatusVO;
import com.ttk.dto.finance.ChequeVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of Claim Shortfall's to be sent Manually
 */

public class BulkPDFAction extends TTKAction
{
	private static Logger log = Logger.getLogger(BulkPDFAction.class);

	//Action mapping forwards.claimssfDetails
	private static final  String strClaimsSFCorr="claimssfcorr";
	//private static final  String strClaimsSFEmail="shortfallsendemail";
	//private static final  String strClaimsSFDetails="claimssfDetails";
	 private static final String strReportdisplay="reportdisplay";

	 private static final  String strBulkPDFDetails="bulkpdfsearch";
    //Exception Message Identifier
    private static final String strClaimShortfallError="cliamsshortfall";

    /**
     * This method is used to initialize the search grid.
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
    										HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doDefault method of BulkPDFAction");
    		setLinks(request);
    			
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//clear the dynaform if visiting from left links for the first time
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			
			DynaActionForm frmBulkPdf=(DynaActionForm)form;
			//create new table data object
			tableData = new TableData();
			
			//create the required grid table
			tableData.createTableInfo("BulkPDFTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			
			
			return mapping.findForward(strBulkPDFDetails);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallError));
		}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   
    
    
     
    
	
	
        
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
    												HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSearch method of BulkPDFAction");
    		setLinks(request);
    		ClaimManager claimManagerObject=this.getClaimManagerObject();
    	
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strBulkPDFDetails);
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
				tableData.createTableInfo("BulkPDFTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
              //  this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
				tableData.modifySearchData("search");
			}//end of else
			
			ArrayList alBulkPDFList= claimManagerObject.viewShortFallAdvice(tableData.getSearchData());
			tableData.setData(alBulkPDFList,"search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return mapping.findForward(strBulkPDFDetails);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallError));
		}//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
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
   public ActionForward doViewFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewFile method of BulkPDFAction");
    		setLinks(request);
    		ClaimManager claimManagerObject=this.getClaimManagerObject();
    		//String str=request.getParameter("fileName");		    		
    		//String strFile = str+".pdf";   
    		//String strFile=claimManagerObject.getBatchFileName("");
    		
    		TableData  tableData =TTKCommon.getTableData(request);
    		BulkGeneratedPDFVO bulkGeneratedPDFVO = ((BulkGeneratedPDFVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum"))));
    		String strBatchFileName=bulkGeneratedPDFVO.getFileName()+".pdf";
    		File file = null;  		
    	    String bulkDestination=TTKPropertiesReader.getPropertyValue("bulkshortfalldir")+strBatchFileName;   		
    		file = new File(bulkDestination);
    		if(file.exists())
    		{
    			bulkDestination = TTKPropertiesReader.getPropertyValue("bulkshortfalldir")+strBatchFileName;
    			request.setAttribute("fileName",bulkDestination);
    		}//end of if(file.exists())
    		
    		return mapping.findForward(strBulkPDFDetails);//finally return to the grid screen
			
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallError));
		}//end of catch(Exception exp)
    }//end of doViewFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
    /**
     * This method is used to download the pdf file generated for claim shortfall.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewFilePdf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewFilePdf method of BulkPDFAction");
    		setLinks(request);
    		return (mapping.findForward(strReportdisplay));
			
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallError));
		}//end of catch(Exception exp)
    }//end of doViewFilePdf(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
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
    public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doClose method of BulkPDFAction"); 	
		//	request.getSession().removeAttribute("frmShortfallResendEmail");
    		return mapping.findForward(strBulkPDFDetails);//finally return to the grid screen
    	}//end of try
    	/*catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)*/
    	
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallError));
		}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    /**
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmClaimsList formbean which contains the search fields
     * @param request HttpServletRequest
     * @return ArrayList contains search parameters
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmBulkPdf,HttpServletRequest request)
    {
    	//build the column names along with their values to be searched
    	ArrayList<Object> alSearchParams = new ArrayList<Object>();
    	
    	alSearchParams.add((String)frmBulkPdf.getString("sFileName"));
    	alSearchParams.add((String)frmBulkPdf.getString("sBatchNumber"));
    	
    //	dtDateOfBirth=TTKCommon.getUtilDate((String)frmBulkPdf.get("sBatchDate"));
       	alSearchParams.add((String)frmBulkPdf.get("sBatchDate"));
    	
     
    	/*alSearchParams.add((String)frmClaimsSFCorr.getString("sShortfallStatus"));
    	alSearchParams.add((String)frmClaimsSFCorr.getString("sEmailIDStatus"));*/
    	
    	
    	
    	return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmClaimsList,HttpServletRequest request)
    
    /**
     * Returns the PreAuthManager session object for invoking methods on it.
     * @return PreAuthManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private ClaimManager getClaimManagerObject() throws TTKException
    {
    	ClaimManager claimManager = null;
    	try
    	{
    		if(claimManager == null)
    		{
    			InitialContext ctx = new InitialContext();
    			claimManager = (ClaimManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimManagerBean!com.ttk.business.claims.ClaimManager");
    		}//end if
    	}//end of try
    	catch(Exception exp)
    	{
    		throw new TTKException(exp, strClaimShortfallError);
    	}//end of catch
    	return claimManager;
    }//end getClaimManagerObject()  
    
    
    
    
    
    
}//end of ClaimsSFCorrAction