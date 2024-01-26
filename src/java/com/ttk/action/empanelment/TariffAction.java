/**
 * @ (#) TariffAction.java Oct 21, 2005
 * Project       : TTK HealthCare Services
 * File          : TariffAction.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Oct 21, 2005
 *
 * @author       : Srikanth H M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.empanelment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.ttk.action.TTKAction;
import com.ttk.action.empanelment.TariffXML.PackageDetails;
import com.ttk.action.empanelment.TariffXML.Pricerefdetails;
import com.ttk.action.empanelment.TariffXML.TariffXMLTag;
import com.ttk.action.empanelment.TariffXML.Tariffdetails;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.TariffManager;
import com.ttk.business.reports.TTKReportManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.TariffPlanVO;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.InsuranceVO;

/**
 * This class is used for searching the hospital tariffs.
 */

public class TariffAction extends TTKAction {
	private static Logger log = Logger.getLogger(TariffAction.class);

	// Action mapping forwards.
	private static final String strHospitalTariffList = "tarifflist";
	private static final String strRevisionHistoryList = "revisionhistory";

    //  Modes.
	private static final String strBackward = "Backward";
	private static final String strForward = "Forward";

	// Exception Message Identifier
	private static final String strHospTariffError = "hospitalTariff";
    private static final String strTariffUploaded	=	"tariffUploads";

	// Tariff for Adminidtration
	private static final String	strUploadTariff		=	"uploadtariff";
	private static final String	strSelectCorporates	=	"selctCorporates";
	private static final String	strTariffUploadedEmpanelment	=	"tariffUploadsEmpanelment";

	// Tariff For Empanelment
	private static final String	strUploadTariffEmpanelment		=	"uploadtariffEmpanelment";
	private static final String	strHospitalTariffEmpanelmentList		=	"tarifflistEmpanelment";
	private static final String	strSearchTariffEmpanelment		=	"searchTariffEmpanelment";
	private static final String strDownloadTariffEmpanelment	=	"downloadTariffEmpanelment";
	 private static final String strReportdisplay="reportdisplay";
	

	/**
	 * This method is used to initialize the search grid. Finally it forwards to
	 * the appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
     */
	
	
	
	
	
	public ActionForward doDefault(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
    		log.info("Inside the doDefault method of TariffAction");
    		setLinks(request);
			if (TTKCommon.getWebBoardId(request) == null) {
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.hospital.required");
				throw expTTK;
			}// end of if(TTKCommon.getWebBoardId(request)==null)
			ArrayList alAssociatedTo = null;
			// get the session bean from the bean pool for each excecuting
			// thread
			TariffManager tariffObject = this.getTariffManager();
			Long lHospitalSeqId = new Long(TTKCommon.getWebBoardId(request));// get
																				// the
																				// web
																				// board
																				// id

			TableData tableData = TTKCommon.getTableData(request);
			// create new table data object
            tableData = new TableData();
			// create the required grid table
			tableData.createTableInfo("HospitalTariff", new ArrayList());
			request.getSession().setAttribute("tableData", tableData);
			request.getSession().setAttribute("searchparam", null);
			// make query to get associated company list to load to combo box
			alAssociatedTo = tariffObject
					.getAssociatedCompanyList(lHospitalSeqId);
			request.getSession().setAttribute("alAssociatedTo", alAssociatedTo);
			((DynaActionForm) form).initialize(mapping);// reset the form data
            TTKCommon.documentViewer(request);
			if ("Tariff".equals(TTKCommon.getActiveSubLink(request)))
            	return this.getForward(strHospitalTariffList, mapping, request);
            else
				return this.getForward(strHospitalTariffEmpanelmentList,
						mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strHospTariffError));
		}// end of catch(Exception exp)
	}// end of doDefault(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to search the data with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified
	 * forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
     */
	public ActionForward doSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
    		log.debug("Inside the doSearch method of TariffAction");
    		setLinks(request);
			// get the session bean from the bean pool for each excecuting
			// thread
			TariffManager tariffObject = this.getTariffManager();
			Long lHospitalSeqId = new Long(TTKCommon.getWebBoardId(request));// get
																				// the
																				// web
																				// board
																				// id
			TableData tableData = TTKCommon.getTableData(request);
			String strPageID = TTKCommon.checkNull(request
					.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request
					.getParameter("sortId"));
			if (!strPageID.equals("") || !strSortID.equals("")) {
				if (!strPageID.equals("")) {
					tableData.setCurrentPage(Integer.parseInt(TTKCommon
							.checkNull(request.getParameter("pageId"))));
    				return (mapping.findForward(strHospitalTariffList));
				}// /end of if(!strPageID.equals(""))
				else {
					tableData.setSortData(TTKCommon.checkNull(request
							.getParameter("sortId")));
					tableData.modifySearchData("sort");// modify the search data
				}// end of else
			}// end of if(!strPageID.equals("") || !strSortID.equals(""))
			else {
    			// create the required grid table
				tableData.createTableInfo("HospitalTariff", null);
				// fetch the data from the data access layer and set the data to
				// table object
				tableData.setSearchData(this.populateSearchCriteria(
						(DynaActionForm) form, lHospitalSeqId, request));
    			tableData.modifySearchData("search");
			}// end of else
			ArrayList alHospTariff = tariffObject
					.getHospitalTariffDetailList(tableData.getSearchData());
    		tableData.setData(alHospTariff, "search");
			request.getSession().setAttribute("tableData", tableData);
			// finally return to the grid screen
    		return this.getForward(strHospitalTariffList, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strHospTariffError));
		}// end of catch(Exception exp)
	}// end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

    /**
	 * This method is used to get the previous set of records with the given
	 * search criteria. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
     */
	public ActionForward doBackward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
    		log.debug("Inside the doBackward method of TariffAction");
    		setLinks(request);
			// get the session bean from the bean pool for each excecuting
			// thread
			TariffManager tariffObject = this.getTariffManager();
			ArrayList alHospTariff = null;
			TableData tableData = TTKCommon.getTableData(request);
			// fetch the data from the data access layer and set the data to
			// table object
			tableData.modifySearchData(strBackward);// modify the search data
			// fetch the data from the data access layer and set the data to
			// table object
			alHospTariff = tariffObject.getHospitalTariffDetailList(tableData
					.getSearchData());
			tableData.setData(alHospTariff, strBackward);// set the table data
			request.getSession().setAttribute("tableData", tableData);// set the
																		// table
																		// data
																		// object
																		// to
																		// session
    		TTKCommon.documentViewer(request);
    		//	finally return to the grid screen
		 	return this.getForward(strHospitalTariffList, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strHospTariffError));
		}// end of catch(Exception exp)
	}// end of doBackward(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

    /**
	 * This method is used to get the next set of records with the given search
	 * criteria. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
     */
	public ActionForward doForward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
    		log.debug("Inside the doForward method of TariffAction");
    		setLinks(request);
			// get the session bean from the bean pool for each excecuting
			// thread
			TariffManager tariffObject = this.getTariffManager();
			ArrayList alHospTariff = null;
			TableData tableData = TTKCommon.getTableData(request);
			// fetch the data from the data access layer and set the data to
			// table object
			tableData.modifySearchData(strForward);// modify the search data
			// fetch the data from the data access layer and set the data to
			// table object
			alHospTariff = tariffObject.getHospitalTariffDetailList(tableData
					.getSearchData());
			tableData.setData(alHospTariff, strForward);// set the table data
			request.getSession().setAttribute("tableData", tableData);// set the
																		// table
																		// data
																		// object
																		// to
																		// session
    		TTKCommon.documentViewer(request);
    		//	finally return to the grid screen
		 	return this.getForward(strHospitalTariffList, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strHospTariffError));
		}// end of catch(Exception exp)
	}// end of doForward(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

    /**
	 * This method is used to get the details of the selected record from
	 * web-board. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
     */
	public ActionForward doChangeWebBoard(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	log.debug("Inside doChangeWebBoard method of TariffAction");
		// ChangeWebBoard method will call doDefault() method internally.
		if ("Tariff".equals(TTKCommon.getActiveSubLink(request)))
			return doDefault(mapping, form, request, response);
    	else
			return doDefaultEmpnlTariff(mapping, form, request, response);
	}// end of doChangeWebBoard(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

    /**
	 * This method is used to get the details of the selected record from
	 * web-board. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
     */
	public ActionForward doViewRevisionHistory(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
    		log.debug("Inside the doViewRevisionHistory method of TariffAction");
    		setLinks(request);
    		TableData tableData = TTKCommon.getTableData(request);
			if (request.getParameter("rownum") != null) {
				InsuranceVO insuranceVO = (InsuranceVO) tableData
						.getRowInfo(Integer.parseInt((String) request
								.getParameter("rownum")));
				request.getSession().setAttribute("insuranceVO", insuranceVO);
			}// end of if(request.getParameter("rownum")!=null)
            return mapping.findForward(strRevisionHistoryList);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strHospTariffError));
		}// end of catch(Exception exp)
	}// end of doViewRevisionHistory(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

    /*
     * 
     * 
     */
	public ActionForward doShowUploadTariff(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
		log.info("Inside the doShowUploadTariff method of TariffAction");
		setLinks(request);
			((DynaActionForm) form).initialize(mapping);// reset the form data
	//	return this.getForward(strUploadTariff, mapping, request);
			if ("Tariff".equals(TTKCommon.getActiveSubLink(request)))
			return mapping.findForward(strUploadTariff);
		else
			return mapping.findForward(strUploadTariffEmpanelment);
		}// end of try
		catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strHospTariffError));
		}// end of catch(Exception exp)
	}// end of doShowUploadTariff(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

    /**
     * @param frmHospitalTariff
     * @param lHospitalSeqId
     * @param request
     * @return
     */
	 public ActionForward doUploadTariff(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{ 
	    	    String finalFileName	=	"";
	    		Reader reader		=	null;
	    		FileWriter fileWriter=	null;
	        	try{ 
	        		
	        		log.info("Inside the doUploadTariff method of TariffAction");
	        		setLinks(request);        		
	        		
	    			DynaActionForm frmTariff=(DynaActionForm)form;
	    			FormFile formFile = null;
	    			formFile = (FormFile)frmTariff.get("file");
	    			String networkType	=	"";
	    			String payerCode="";
	    			
	    			String tariffType	=	(String) frmTariff.get("tariffType");
	    			if("TPA021".equals((String) frmTariff.get("payerCode"))){
	    			 payerCode	=	"|"+"TPA033"+"|";
	    			}else 
	    				payerCode	=	"|"+(String) frmTariff.get("payerCode")+"|";
	    			String providerCode	=	"|"+(String) frmTariff.get("providerCode")+"|";
	    			if("HOSP".equals(tariffType))
	    			networkType	=	"|"+(String) frmTariff.get("networkType")+"|";
	    			else
	    				networkType	=	"|"+(String) frmTariff.get("networkType1")+"|";
	    		    String corpCode		=	"|"+(String) frmTariff.get("corpCode")+"|";
	    			String discAt		=	(String)frmTariff.get("discAt");

	    			long addedBy		=	TTKCommon.getUserSeqId(request);
	    			//String priceRefId	=	request.getParameter("priceRefId");
	        		String fileExtn 	= 	GetFileExtension(formFile.toString());
	        		
	        	HSSFWorkbook workbook = null;
	        	HSSFSheet sheet = null;// i; // sheet can be used as common for XSSF and HSSF WorkBook
	        	    
	        		/**
	        		 * extract data from excel and create xml
	        		 */
	        		InputStream inputStream = formFile.getInputStream(); 
	      	      if (fileExtn.equalsIgnoreCase("xls"))
	      	      {
	      	    	  //POIFSFileSystem fs = new POIFSFileSystem(inputStream);
	      	    	workbook =  (HSSFWorkbook) new HSSFWorkbook(inputStream);
	      	    	  //log("xls="+wb_hssf.getSheetName(0));
	      	    	 sheet = workbook.getSheetAt(0);
	      	      }
	        	    try {
	        	    	
	        	        //Initializing the XML document
	        	    	final Pattern REGEX_PATTERN = Pattern.compile("[^\\p{ASCII}]+");
	        	        if(sheet==null)
	        	        {
	        	        	request.setAttribute("updated", "Please upload proper File");
	        	        }	
	        	        else{
	        	        Iterator<?> rows     = sheet.rowIterator ();

	        	        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
	        	        ArrayList<TariffXMLTag> arrayList	=	null;
	        	        TariffXMLTag tariffXMLTag			=	null;
	        	        Tariffdetails tariffdetails			=	null;
	        	        arrayList	=	new ArrayList<TariffXMLTag>();
	        	        while (rows.hasNext ()) 
	        	        {
	        	        HSSFRow row =  (HSSFRow) rows.next(); 

	        	            if(row.getRowNum()==0)
	        	            	continue;
	        	            
	        	        // int rowNumber = row.getRowNum ();
	        	            // display row number

	        	            // get a row, iterate through cells.
	        	            Iterator<?> cells = row.cellIterator (); 
	        	            ArrayList<String> rowData = new ArrayList<String>();
	        	            for(short i=0;i<=10;i++)
	        	            {
	        	            	HSSFCell cell	=	row.getCell(i);
	        	            	
	        	            	if(cell==null)
	        	            	{
	        	            		rowData.add("");
	        	            	}	
	        	            	else
	        	            		{
	        	            		
	        	            		switch (cell.getCellType ())
	    	     	                {
	    		     	                case HSSFCell.CELL_TYPE_NUMERIC :
	    		     	                {
	    		     	                    // Date CELL TYPE
	    		     	                    if(HSSFDateUtil.isCellDateFormatted((HSSFCell) cell)){
	    		     	                    	rowData.add(new SimpleDateFormat("dd-MM-YYYY").format(cell.getDateCellValue()));
	    		     	                    }
	    		     	                    else
	    		     	                    { 
	    		     	                    	// NUMERIC CELL TYPE
	    		     	                    	rowData.add(cell.getNumericCellValue () + "");
	    		     	                    }
	    		     	                    break;
	    		     	                }
	    		     	                case HSSFCell.CELL_TYPE_STRING :
	    		     	                {
	    		     	                    // STRING CELL TYPE
	    		     	                    //String richTextString = cell.getStringCellValue().trim().replaceAll("[^\\x00-\\x7F]", "");
	    		     	                	String richTextString = cell.getStringCellValue().trim();
	    		     	                	richTextString	=	REGEX_PATTERN.matcher(richTextString).replaceAll("").trim();
	    		     	                    rowData.add(richTextString);
	    		     	                    break;
	    		     	                }
	    		     	                case HSSFCell.CELL_TYPE_BLANK :
	    		     	                {
	    		     	                	//HSSFRichTextString blankCell	=	cell.get.getRichStringCellValue();
	    		     	                	String blankCell	=	cell.getStringCellValue().trim().replaceAll("[^\\x00-\\x7F]", "");
	    		     	                	rowData.add(blankCell);
	    		     	                	break;
	    		     	                }
	    		     	                default:
	    		     	                {
	    		     	                    // types other than String and Numeric.
	    		     	                    break;
	    		     	                }
	    		     	              
	    	     	                } // end switch
	    	            		}//else
	        	            }//for
	        	            //JAXB
	        	     double discPerc	=	new Double(rowData.get(7));
	    	            if(rowData.get(7)!=null ||!"".equals(rowData.get(7)))
	    	            {
	    	            	discPerc	=	(discPerc/100)*new Double(rowData.get(6));
	    	            }
	    	            	//ADDING THE CONTENT OF EXCEL CELLS TO TARIFF JAVA FILE
	    	            	arrayList.add(new TariffXMLTag(rowData.get(0), rowData.get(1), rowData.get(2), rowData.get(3), 
	    	            			rowData.get(4), rowData.get(5), (rowData.get(6)==null || rowData.get(6)=="")?null:new BigDecimal(rowData.get(6)), new BigDecimal(discPerc).setScale(3, BigDecimal.ROUND_CEILING), 
	    	            			rowData.get(8), rowData.get(9),(rowData.get(7)==null || rowData.get(7)=="")?null:new BigDecimal(rowData.get(7))));
	    	            
	    	            
	    	           
	        	            
	        	            
	        	            data.add(rowData);//adding Excel data to ArrayList

	        	        } //end while
	        	        //JAXB
	        	        tariffdetails	=	new Tariffdetails();
	        	        PackageDetails packageDetails  	=	new PackageDetails(arrayList);
	        	        Pricerefdetails pricerefdetails	=	null;
	        	        
	        	        if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
	        	        {
	        	        	pricerefdetails	=	new Pricerefdetails(corpCode, payerCode, providerCode,addedBy, tariffType, networkType, packageDetails,discAt);
	        	        }
	        	        	else 
	        	        	{
	        	        	pricerefdetails	=	new Pricerefdetails(corpCode, payerCode, providerCode,addedBy, "HOSP", networkType, packageDetails,discAt);
	        	        	}
	        	        
	        	 	   tariffdetails.setPricerefdetails(pricerefdetails);
	        	 	   
	        	        
	    //Below code to Create xml and elements
	        	 	  /*  int numOfProduct = data.size();
	        	        Document document = DocumentHelper.createDocument();
	        	        Element rootElement = document.addElement("tariffdetails");
	        	        
	        	        Element pricerefdetails = rootElement.addElement("pricerefdetails");
	        	        if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
	        	        	pricerefdetails.addAttribute("tariffflag", tariffType);
	        	        else 
	        	        	pricerefdetails.addAttribute("tariffflag", "HOSP");
	        	        pricerefdetails.addAttribute("userid", ""+addedBy);
	        	        //pricerefdetails.addAttribute("pricerefno", priceRefId);
	        	        pricerefdetails.addAttribute("empanelnumber", providerCode);
	        	        pricerefdetails.addAttribute("insuranceid", payerCode);
	        	        pricerefdetails.addAttribute("corporateid", corpCode);
	        	        pricerefdetails.addAttribute("networktype", networkType);

	        	        Element packagedetails = pricerefdetails.addElement("packagedetails");
	        	        
	        	        for (int i = 1; i < numOfProduct; i++){
	        	        	Element productElement = packagedetails.addElement("tariff");
	        	            String enementNames[]	=	data.get(i).toString().split(",");
	        	            productElement.addAttribute("activitycode", enementNames[0].replace("[", "").trim());
	        	            productElement.addAttribute("internalcode", enementNames[1].trim());
	        	            //productElement.addAttribute("acttypeid", enementNames[2].trim());
	        	            productElement.addAttribute("servname", enementNames[2].trim());
	        	            productElement.addAttribute("activitydesc", enementNames[3].trim());
	        	            productElement.addAttribute("packageid", enementNames[4].trim());
	        	            productElement.addAttribute("bundleid", enementNames[5].trim());
	        	            productElement.addAttribute("grossamt", enementNames[6].trim());
	        	            //Float discAmt	=	(Float.parseFloat(enementNames[7].trim())/100)* Float.parseFloat(enementNames[6].trim());
	        	            productElement.addAttribute("discamt", enementNames[7].trim());
	        	            productElement.addAttribute("fromdate", enementNames[8].trim());
	        	            productElement.addAttribute("expirydate", enementNames[9].replace("]", "").trim());
	        	           // packagedetails.appendChild(productElement);
	        	        }
	    */
	        	        
	        	        
	        			FileOutputStream outputStream = null;
	            		String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("tariffAdminUpload"));
	        	        File folder = new File(path);
	    				if(!folder.exists()){
	    					folder.mkdir();
	    				}
	        	        String finalPath=(path+new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+formFile);
	        	        
	    				outputStream = new FileOutputStream(new File(finalPath));
	    				outputStream.write(formFile.getFileData());//Excel file Upload backUp
	        	        
	    				
	    				try {
	    					finalFileName	=	path+"tariff-"+new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+".xml";
	    					
	        	 			File file = new File(finalFileName);
	        	 			JAXBContext jaxbContext = JAXBContext.newInstance(Tariffdetails.class);
	        	 			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	        	 	 
	        	 			// output pretty printed
	        	 			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        	 	 
	        	 			
	        	 			jaxbMarshaller.marshal(tariffdetails, file);
	        	 		//	jaxbMarshaller.marshal(tariffdetails, System.out);
	        	 			//jaxbMarshaller.marshal(tariffdetails, System.out);
	        	 			
	        	 	 
	        	 		      } catch (JAXBException e) {
	        	 			e.printStackTrace();
	        	 		      }
	    				//convert document to xml
	    				/*finalFileName	=	path+"tariff-"+new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+".xml";
	        	        XMLWriter xmlWriter=new XMLWriter(new FileWriter(finalFileName));
	        	        xmlWriter.write(document);*/
	        	        
	        	        //closing the opened objects
	        	        //xmlWriter.close();
	        	        outputStream.close();
	            	    inputStream.close();
	            	    
	            	    /*if(workbook!=null)
	            	    	workbook.close();*/
	        	   
	        	    
	        	    TariffManager tariffObject=this.getTariffManager();
	        	    
	        	    File file 	=	new File(finalFileName);
	        	    FileInputStream inputStream2	=	new FileInputStream(file); 
	        	    FileReader xmlFile	=	new FileReader(file);
	        	    
	        	  //  Clob clob	=	tariffObject.uploadTariffEmpanelment(xmlFile,(int)file.length());
	        	    String clob	=	tariffObject.uploadTariffEmpanelment(inputStream2);
	        	    log.info("in Tariff Upload Action");
	        	    String defaultMsg="There is a Problem, Please Contact AdminStrator";
	                int fileLength=(clob==null)?defaultMsg.length():(int)clob.length();
	                char []carData=new char[fileLength]; 
	               
	                //creating Error log File
	                reader=(clob==null)?new StringReader(defaultMsg):new StringReader(clob);                          
	                reader.read(carData);
	                String newFileName	=	"LogFile"+new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+".xls";
	                File file2=new File(TTKPropertiesReader.getPropertyValue("tariffErrorLogs")+newFileName);
	                 fileWriter=new FileWriter(file2);                           
	                 fileWriter.write("Date::"+new Date()+"\n");                   
	                 fileWriter.write("========================================"+"\n");
	                 fileWriter.write(carData);
	                 fileWriter.flush();  
	                 if(clob!=null)
	                 {
	                	 request.setAttribute("updated", "Tariff data Uploaded Successfully");
	                 }
	                 frmTariff.initialize(mapping);
	                 frmTariff.set("newFileName", newFileName);
	                 request.setAttribute("newFileName", newFileName);
	        	        }//end else
	               
	        	    //return null;
	               		
	        	        }//end try
	        	    catch(IOException e)
	        	    {
	        	        System.out.println("IOException " + e.getMessage());
	        	    }
	        	    if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
	        	    {
	        	    	return mapping.findForward(strTariffUploaded);
	        	    }	
	        	    else
	        	    {
	               	    return mapping.findForward(strTariffUploadedEmpanelment);
	        	    }
	        	}//end of try
	        	catch(TTKException expTTK)
	    		{
	    			//log.info(expTTK.printStackTrace());
	        		return this.processExceptions(request, mapping, expTTK);
	    		}//end of catch(TTKException expTTK)
	    		catch(Exception exp)
	    		{
	    			return this.processExceptions(request, mapping, new TTKException(exp,strHospTariffError));
	    		}//end of catch(Exception exp)
	        	finally{
	        		try{
	    				if(reader!=null)
	    					reader.close();
	    			}
	    			catch(IOException ioExp)
	    			{
	    				log.error("Error in Reader");
	    			}
	    			try{
	    				if(fileWriter!=null)
	    					fileWriter.close();
	    			}catch(IOException ioExp)
	    			{
	    				log.error("Error in fileWriter");
	    			}
	        	}
	    }// end of doUploadTariff(ActionMapping mapping,ActionForm
			// form,HttpServletRequest request,HttpServletResponse response)

	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	private String getCellValue(HSSFCell cell) {
		if (cell == null)
			return "";
		else
			switch (cell.getCellType()) {

			case HSSFCell.CELL_TYPE_BLANK:
				return "";
			case HSSFCell.CELL_TYPE_STRING:
				return cell.getStringCellValue();
			case HSSFCell.CELL_TYPE_NUMERIC: {
				if (org.apache.poi.hssf.usermodel.HSSFDateUtil
						.isCellDateFormatted(cell))
					return dateFormat.format(cell.getDateCellValue());
				else {
					long longg = new Double(cell.getNumericCellValue())
							.longValue();
					return (longg < 1) ? "" : longg + "";
				}
			}
			case HSSFCell.CELL_TYPE_ERROR:
				return "";
			case HSSFCell.CELL_TYPE_FORMULA:
				return "";

			default:
				return "";
			}
	}

	// This method is used to get the extension of the file attached
	// DonE for INTX - KISHOR KUMAR S H
	private static String GetFileExtension(String fname2) {
        String fileName = fname2;
		String fname = "";
		String ext = "";
		int mid = fileName.lastIndexOf(".");
		fname = fileName.substring(0, mid);
		ext = fileName.substring(mid + 1, fileName.length());
        return ext;
    }

	private static void log(String message) {
            System.out.println(message);
    }

	/**
	 * DO SEARCH FOR EMPANELMENT TARIFFS This method is used to initialize the
	 * search grid. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
     */
	public ActionForward doDefaultEmpnlTariff(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.info("Inside the doDefaultEmpnlTariff method of TarifAction");
			setLinks(request);
			request.getSession().removeAttribute("alServiceTypes");
			if (TTKCommon.getWebBoardId(request) == null) {
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.hospital.required");
				throw expTTK;
			}// end of if(TTKCommon.getWebBoardId(request)==null)
			TableData tableData = TTKCommon.getTableData(request);
			// create new table data object
			tableData = (TableData) request.getSession().getAttribute(
					"tableData");
			// create the required grid table
			tableData.createTableInfo("TariffSearchTable", new ArrayList());
		//	((DynaActionForm)form).initialize(mapping);//reset the form data
			request.getSession().setAttribute("tableData", tableData);
			DynaActionForm frmSearchTariffEmpanelment = (DynaActionForm) form;
			Long lHospitalSeqId = new Long(TTKCommon.getWebBoardId(request));
			String strHospSeqID = lHospitalSeqId.toString();
			/*frmSearchTariffEmpanelment.set("providerCodeSearch", request.getSession().getAttribute("hospSeqIdforTariff"));*/
			frmSearchTariffEmpanelment.set("providerCodeSearch", strHospSeqID);
			request.getSession().setAttribute("frmSearchTariffItem",
					frmSearchTariffEmpanelment);
			return this
					.getForward(strSearchTariffEmpanelment, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strHospTariffError));
		}// end of catch(Exception exp)
	}// end of doDefaultEmpnlTariff(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

    /*
     * 
     * 
     */
	public ActionForward doCorporateDefault(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
		log.info("Inside the doCorporateDefault method of TariffAction");
		setLinks(request);
			((DynaActionForm) form).initialize(mapping);// reset the form data
		return mapping.findForward(strSelectCorporates);
		}// end of try
		catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strHospTariffError));
		}// end of catch(Exception exp)
	}// end of doCorporateDefault(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

    /*
	 * Returns the ArrayList which contains the populated search criteria
	 * elements.
	 * 
	 * @param frmHospitalTariff DynaActionForm will contains the values of
	 * corresponding fields.
	 * 
	 * @param lHospitalSeqId Long which contains hospital sequence id.
	 * 
	 * @param request HttpServletRequest object which contains the search
	 * parameter that is built.
	 * 
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(
			DynaActionForm frmHospitalTariff, Long lHospitalSeqId,
			HttpServletRequest request) {
		// build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(new SearchCriteria("HOSP_SEQ_ID", lHospitalSeqId
				.toString()));
		alSearchParams.add(new SearchCriteria("ASSOCIATED_SEQ_ID",
				(String) frmHospitalTariff.get("associatedTo"), "equals"));
		alSearchParams
				.add(new SearchCriteria("PRODUCT_POLICY_NO", TTKCommon
						.replaceSingleQots((String) frmHospitalTariff
								.get("proPolNo"))));
		// request.getSession().setAttribute("searchparam",alSearchParams);
		return alSearchParams;
	}// end of populateSearchCriteria

	/**
	 * Returns the TariffManager session object for invoking methods on it.
	 * 
	 * @return TariffManager session object which can be used for method
	 *         invokation
	 * @exception throws TTKException
	 */
	private TariffManager getTariffManager() throws TTKException {
		TariffManager hospTariff = null;
		try {
			if (hospTariff == null) {
				InitialContext ctx = new InitialContext();
				hospTariff = (TariffManager) ctx
						.lookup("java:global/TTKServices/business.ejb3/TariffManagerBean!com.ttk.business.administration.TariffManager");
			}// end if
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, strHospTariffError);
		}// end of catch
		return hospTariff;
	}// end getTariffManager()
	
	public ActionForward doGenerateProviderTariff(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws TTKException
	{
		try{
			 log.info(" Inside the  doGenerateProviderTariff method of TariffAction ");
			 setLinks(request);
			 try
			 { 
				 TariffManager hospTariff = null;
				 DynaActionForm frmTariffdownloadItemEmpanelment	=	(DynaActionForm) form;
				 hospTariff	=	this.getTariffManager();
			  	 ArrayList alData	=	null;
		         TariffPlanVO tariffPlanVO = null;
		  	     TableData tableData = TTKCommon.getTableData(request);
			  	 StringBuffer ContactSeqId = new StringBuffer("|");
			  	 
				 String networkType				=	request.getParameter("networkType");
				 String checkboxselectedYN		=	request.getParameter("checkboxselectedYN");
				 
				 String strChkBoxValues=request.getParameter("chkBoxValues");
			
				 String hospSeqId = (String)request.getSession().getAttribute("hospSeqIdforTariff");
	    		 Long  hospitalSeqId =Long.parseLong(hospSeqId);
	    		 if(TTKCommon.checkNull(strChkBoxValues).length()>0){
	    			 ArrayList<String> alselected = new ArrayList<>();
	    			 String arryChkBoxValues[]=strChkBoxValues.substring(0, strChkBoxValues.length()-1).split("[|]");
	    			 for(int i=0; i<arryChkBoxValues.length;i++)
	    		        {
	    		           
	    		        		  
	    		        		   String strUpdate=((TariffPlanVO) tableData.getData().get(Integer.parseInt(arryChkBoxValues[i]))).getsUploadedDate();
	    		        		   String strAddBy=((TariffPlanVO) tableData.getData().get(Integer.parseInt(arryChkBoxValues[i]))).getsAddedby();
	    		        		   alselected.add(strAddBy+"|"+strUpdate);
	    			    }
	    			 
	    			 alData	= hospTariff.getSelectedProviderTariffReport(alselected,networkType,hospitalSeqId);
	    		 }
	    		
	    		 else 
	    		 {
	    			 
	    			 alData	= hospTariff.getProviderTariffReport(networkType,hospitalSeqId);
	    		 }
	    		 request.setAttribute("alData", alData);
	             request.setAttribute("flag", "empanelmenttariffreport");
								 
				 return (mapping.findForward("EmpanelmentProvider"));
			 }//end of try
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 }//end of catch (Exception e)
			 return (mapping.findForward(strReportdisplay));
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request, mapping, new TTKException(exp,"reportdetail"));
		 }//end of catch(Exception exp)
	}

	public ActionForward doDownLoadTariff(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
		log.info("Inside the doDownLoadTariff method of TariffAction");
		setLinks(request);
			((DynaActionForm) form).initialize(mapping);// reset the form data
		return mapping.findForward(strSelectCorporates);
		}// end of try
		catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strDownloadTariffEmpanelment));
		}// end of catch(Exception exp)
	}
	
	public ActionForward doChangeNetworkTypeYN(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try {
			     	DynaActionForm frmTariffUploadItemEmpanelment = (DynaActionForm)form;
					String strNetworkTypeYN = frmTariffUploadItemEmpanelment.getString("networkTypeYN");
					String strPayer = frmTariffUploadItemEmpanelment.getString("payerCode");
					request.getSession().setAttribute("frmTariffUploadItemEmpanelment", frmTariffUploadItemEmpanelment);
				
				return mapping.findForward(strTariffUploadedEmpanelment);
				
		    }catch (Exception exp) {
					return this.processExceptions(request, mapping, new TTKException(
							exp, strTariffUploadedEmpanelment));
				}// end of catch(Exception exp)d of catch(Exception exp)
			}
	
	 public ActionForward doViewTariffUploadTemplateErrorLog(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 HttpServletResponse response) throws TTKException {
		 log.debug("Inside ReportsAction class doViewAuditUploadTemplateErrorLog method");
		 try
		 {
			 setLinks(request);
			 String strActiveLink=TTKCommon.getActiveLink(request);
			 DynaActionForm frmTariffUploadItemEmpanelment=(DynaActionForm)form;
			 String startDate = frmTariffUploadItemEmpanelment.getString("startDate");
			 String endDate = frmTariffUploadItemEmpanelment.getString("endDate");
			 ArrayList alData	=	null; 
			 TariffManager tariffObject=this.getTariffManager();
			 alData=tariffObject.doViewTariffUploadTemplateErrorLog(startDate,endDate);
			 request.setAttribute("alData", alData);
			 request.getSession().setAttribute("flag", "Tariff");
			 return (mapping.findForward("TariffUploadedEmpanelmentErrorlogs"));
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request,mapping,new TTKException(exp,"tariffUploadsEmpanelment"));
		 }//end of catch(Exception exp)

	 }

}// end of class TariffAction