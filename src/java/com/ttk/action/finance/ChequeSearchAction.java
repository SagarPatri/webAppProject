/**
 * @ (#) ChequeSearchAction.java June 12, 2006
 * Project       : TTK HealthCare Services
 * File          : ChequeSearchAction.java
 * Author        : Harsha Vardhan B N
 * Company       : Span Systems Corporation
 * Date Created  : June 12, 2006
 *
 * @author       :
 * Modified by   : Harsha Vardhan B N
 * Modified date : June 13, 2006
 * Reason        :
 */

package com.ttk.action.finance;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

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
import org.apache.struts.validator.DynaValidatorForm;
import org.dom4j.io.SAXReader;

import com.lowagie.text.Row;
import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.finance.ChequeManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.BankAddressVO;
import com.ttk.dto.finance.ChequeDetailVO;
import com.ttk.dto.finance.ChequeVO;
import com.ttk.action.finance.Batch;
import com.ttk.action.finance.FileDetail;

import formdef.plugin.util.FormUtils;



/**
 * This class is used for searching the  List of Cheques.
 */
public class ChequeSearchAction extends TTKAction {
	private static Logger log = Logger.getLogger( ChequeSearchAction.class );
	//   Modes.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";
    //  Action mapping forwards.
    private static final String strChequeList="chequelist";
    private static final String strChequeDetail="chequedetail";
    //Exception Message Identifier
    private static final String strChequeSearch="ChequeSearch";
    private static final String struploadbatchdetaillog="uploadbatchdetail";
	private FileInputStream inputStream3;

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
    		setLinks(request);
    		log.debug("Inside ChequeSearchAction doDefault ");
    		TableData tableData = TTKCommon.getTableData(request);

            ((DynaActionForm)form).initialize(mapping);//reset the form data
            //create new table data object
    		tableData = new TableData();
    		//create the required grid table
    		tableData.createTableInfo("ChequeListTable",new ArrayList());
    		request.getSession().setAttribute("tableData",tableData);
    		return this.getForward(strChequeList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strChequeSearch));
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
    		setLinks(request);
    		log.debug("Inside ChequeSearchAction doSearch ");
    		TableData tableData = TTKCommon.getTableData(request);
    		ChequeManager chequeObject=this.getChequeManagerObject();
    		DynaActionForm frmSearchCheques =(DynaActionForm)form;
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
    		if(!strPageID.equals("") || !strSortID.equals(""))
    		{
    			if(!strPageID.equals(""))
    			{
    				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    				return (mapping.findForward("chequelist"));
    			}///end of if(!strPageID.equals(""))
    			else
    			{
    				tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
    				tableData.modifySearchData("sort");//modify the search data
    			}//end of else
    		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
    		else
    		{
    			// create the required grid table
    			tableData.createTableInfo("ChequeListTable",null);
    			//fetch the data from the data access layer and set the data to table object
    			tableData.setSearchData(this.populateSearchCriteria(frmSearchCheques,request));
    			tableData.modifySearchData("search");
    		}//end of else
    		ArrayList alCheque=chequeObject.getChequeList(tableData.getSearchData());
		System.out.println("alCheque size = "+alCheque.size());
    		tableData.setData(alCheque, "search");
    		request.getSession().setAttribute("tableData",tableData);
    		//finally return to the grid screen
    		return this.getForward(strChequeList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strChequeSearch));
    	}//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    							   HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside ChequeSearchAction doForward ");
    		TableData tableData = TTKCommon.getTableData(request);
    		ChequeManager chequeObject=this.getChequeManagerObject();
    		tableData.modifySearchData(strForward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alCheque = chequeObject.getChequeList(tableData.getSearchData());
    		tableData.setData(alCheque, strForward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
    		//TTKCommon.documentViewer(request);
    		return this.getForward(strChequeList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strChequeSearch));
    	}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    								HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside ChequqSearchAction doViewCheque");
    		TableData tableData = TTKCommon.getTableData(request);
    		ChequeManager chequeObject=this.getChequeManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alCheque = chequeObject.getChequeList(tableData.getSearchData());
    		tableData.setData(alCheque, strBackward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
    		//TTKCommon.documentViewer(request);
    		return this.getForward(strChequeList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strChequeSearch));
    	}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doViewCheque(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    								  HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside ChequqSearchAction doViewCheque");
    		ChequeVO chequeVO=null;
    		TableData tableData = TTKCommon.getTableData(request);
    		DynaValidatorForm frmChequeDetail = (DynaValidatorForm)form;
    		//create a new Cheque object
    		chequeVO = new ChequeVO();
    		ChequeDetailVO chequeDetailVO = new ChequeDetailVO();
    		BankAddressVO bankAddressVO = new BankAddressVO ();
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			chequeVO = (ChequeVO)tableData.getRowInfo(Integer.parseInt((String)(frmChequeDetail).get("rownum")));
    			chequeDetailVO.setSeqID(chequeVO.getSeqID());
    			chequeDetailVO.setPaymentSeqId(chequeVO.getPaymentSeqId());
    			frmChequeDetail = (DynaValidatorForm)FormUtils.setFormValues("frmChequeDetail",chequeDetailVO,
    																		  this, mapping, request);
    			frmChequeDetail.set("bankAddressVO", (DynaActionForm)FormUtils.setFormValues("frmFinanceBankAddress",
    																		   bankAddressVO,this,mapping,request));
    			frmChequeDetail.set("caption","Cheque Details");
    			request.getSession().setAttribute("frmChequeDetail",frmChequeDetail);
    		}//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		else
    		{
    			TTKException expTTK = new TTKException();
    			expTTK.setMessage("error.Cheque.required");
    			throw expTTK;
    		}//end of else
    		return mapping.findForward(strChequeDetail);//this.getForward(strChequeDetail, mapping, request);
    	}
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strChequeSearch));
    	}//end of catch(Exception exp)
    }//end of doViewCheque(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							 HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		TableData tableData = TTKCommon.getTableData(request);
    		ChequeManager chequeObject=this.getChequeManagerObject();
    		if(tableData.getSearchData().size()>1)
    		{
    			ArrayList alCheque=chequeObject.getChequeList(tableData.getSearchData());
    			tableData.setData(alCheque, "search");
    			//set the table data object to session
    			request.getSession().setAttribute("tableData",tableData);
    		}//end of if(callcentertableData.getSearchData().size()>1)
    		return this.getForward(strChequeList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strChequeSearch));
    	}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the ArrayList which contains the populated search criteria elements
	 * @param frmSearchCheques DynaActionForm will contains the values of corresponding fields
	 * @param request HttpServletRequest object which contains the search parameter that is built
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSearchCheques,HttpServletRequest request) throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchCheques.get("sChequeNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchCheques.get("sFloatAccountNumber")));
		alSearchParams.add((String)frmSearchCheques.get("sStatus"));
		alSearchParams.add((String)frmSearchCheques.get("sStartDate"));
		alSearchParams.add((String)frmSearchCheques.get("sEndDate"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchCheques.get("sClaimSettleNumber")));
		alSearchParams.add((String)frmSearchCheques.get("sClaimType"));
		alSearchParams.add((String)frmSearchCheques.get("sInsuranceCompany"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchCheques.get("sCompanyCode")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchCheques.get("sBatchNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchCheques.get("sPolicyNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchCheques.get("sEnrollmentId")));
		alSearchParams.add((String)frmSearchCheques.get("sPaymentMethod"));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchCheques.get("sEmiratesID")));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmSearchCheques)

	/**
	 * Returns the ChequeManager session object for invoking methods on it.
	 * @return ChequeManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	//bikki

	public ActionForward doshowChequeTemplate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException {
	 log.debug("Inside ChequeSearchAction class  doshowChequeTemplate");
		ByteArrayOutputStream baos=null;
		OutputStream sos = null;
		FileInputStream fis= null; 
		File file = null;
		BufferedInputStream bis =null;
		try
		{
			setLinks(request);
				response.setContentType("application/txt");
				response.setHeader("Content-Disposition", "attachment;filename=ChequesTemplate.xls");
               
			String fileName =	TTKPropertiesReader.getPropertyValue("Invoicerptdir")+"ChequesTemplate.xls";
			file = new File(fileName);
			
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			baos=new ByteArrayOutputStream();
			int ch;
			while ((ch = bis.read()) != -1) baos.write(ch);
			sos = response.getOutputStream();
			baos.writeTo(sos);  
			baos.flush();      
			sos.flush(); 
            return (mapping.findForward(strChequeList));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strChequeSearch));
		}//end of catch(Exception exp)
		finally{
			try{
				if(baos!=null)baos.close();                                           
				if(sos!=null)sos.close();
				if(bis!=null)bis.close();
				if(fis!=null)fis.close();

			}
			catch(Exception exp)
			{
				return this.processExceptions(request,mapping,new TTKException(exp,strChequeSearch));
			}//                 
		}
	}
	
	 
	 public ActionForward doViewChequeErrorLog(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws TTKException {

			log.debug("Inside ChequeSearchAction doViewChequeErrorLog");
			try
			{
				setLinks(request);
				String strActiveLink=TTKCommon.getActiveLink(request);
				DynaActionForm frmSearchCheques=(DynaActionForm)form;
				String startDate = frmSearchCheques.getString("startDate");
				String endDate = frmSearchCheques.getString("endDate");
				String flag=request.getParameter("Flag");
				ArrayList alData	=	null; 
				 ChequeManager chequeObject=this.getChequeManagerObject();
				 alData=chequeObject.getLogExcelUpload(startDate,endDate,flag);
				request.getSession().setAttribute("alData", alData);
				return (mapping.findForward(struploadbatchdetaillog));
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request,mapping,new TTKException(exp,strChequeList));
			}//end of catch(Exception exp)

		}

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
	 
		public ActionForward doUploadCheque (ActionMapping mapping,ActionForm form,HttpServletRequest request,
				 HttpServletResponse response) throws Exception{
			 try{
				 log.debug("Inside ChequeSearchAction class doUploadCheque method");
				 setLinks(request);
				 String strNotify = null;
				 ChequeManager chequeObject=this.getChequeManagerObject();
				 
				 Object[] excelData=null;
				 DynaActionForm frmSearchCheques=(DynaActionForm)form;
				 FormFile formFile = null;
				
				 formFile = (FormFile)frmSearchCheques.get("stmFile");
				
				 if(formFile==null||formFile.getFileSize()==0)
				 {
					// strNotify="Please select the xls or xlsx file";
					 strNotify="Please select the .XL or .XLS file.";
					 request.getSession().setAttribute("notifyerror", strNotify);
				 }
				 else
				 {
					 String[] arr=formFile.getFileName().split("[.]");
					 String fileType=arr[arr.length-1];
					 //if(!("xls".equalsIgnoreCase(fileType)||"xlsx".equalsIgnoreCase(fileType)))
					 if(!("xl".equalsIgnoreCase(fileType)||"xls".equalsIgnoreCase(fileType)))
						 
					 {
						// strNotify="File Type should be xls or xlsx";
						 strNotify="FILE TYPE SHOULD BE .XL OR .XLS .";
						 request.getSession().setAttribute("notifyerror", strNotify);           
					 }
				 }
				 if(formFile.getFileSize()>(1024*1024*1024)) 
				 {
					 strNotify="File Length Lessthan 3GB";	        	  
					 request.getSession().setAttribute("notifyerror", strNotify);
				 }

				 if(strNotify!=null && strNotify.length()!=0)
				 {
					 return (mapping.findForward(strChequeList));
				 }
				 else
					{
						String[] arr=formFile.getFileName().split("[.]");
						String fileType=arr[arr.length-1];
						
						excelData=this.getExcelData(request,formFile,fileType,6);
						  String batchNo=convertExcelDataIntoXml(request,excelData,formFile,fileType);
						   String xmlFilePath=TTKPropertiesReader.getPropertyValue("dubaiXmlFinDir")+batchNo+".xml";
 	          	    	  File file2=new File(xmlFilePath);
 	          	    	  FileReader fileReader=new FileReader(file2);
 	          	    	  ChequeVO totalCntVo =null;
				         
						Object ChequeUpload= chequeObject.ChequeUploadExcel(batchNo,fileReader,(int)file2.length());
 	          	    	 
  	          	   if(ChequeUpload!=null){
  	          	    	
  	          	    request.setAttribute("successMsg","File uploaded successfully");
  	          	    }
				          
  	          	       ArrayList<String[]>TotalStatusCount=chequeObject.getTotalStatusCount(batchNo);
  	          	       
  	          	       HashMap<String,String>hm = new HashMap<String,String>();
  	          	         for(int i=0;i<TotalStatusCount.size();i++)
  	          	         {
  	          	        	 String x[]=TotalStatusCount.get(i);
  	          	        	 hm.put(x[0], x[1]);
  	          	         } 
  	          	          frmSearchCheques.set("totalStatusCount", hm);
  	          	    
  	          	           frmSearchCheques.set("successYN", "Y");
  	          	           String batchno=batchNo+".txt";
  	          	          
  	          	           //String batchno=batchNo+".xls";
				           frmSearchCheques.set("fileName", batchno);
				        
				          request.getSession().setAttribute("frmSearchCheques",frmSearchCheques); 
				     return (mapping.findForward(strChequeList));	 
					}

			 }//end of try
			 catch(TTKException expTTK)
			 {
				 return this.processExceptions(request, mapping, expTTK);
			 }//end of catch(TTKException expTTK)
			 catch(Exception exp)
			 {
				 return this.processExceptions(request, mapping, new TTKException(exp,strChequeSearch));
			 }//end of catch(Exception exp)
		 }//
		
		private Object[] getExcelData(HttpServletRequest request,FormFile formFile, String fileType, int column) throws FileNotFoundException, IOException {
			// TODO Auto-generated method stub
			InputStream fis=null;
	    	HSSFSheet sheet = null;// i; // sheet can be used as common for XSSF and HSSF WorkBook
	    	Reader reader		=	null;
	    	Object object[]=new Object[2];
	    	int numclaimsettlementnumber=0;
	 	     ArrayList<ArrayList<String>> excelDatar=new ArrayList<>();
			FileWriter fileWriter=	null;
			HSSFWorkbook workbook = null;
			
			fis = formFile.getInputStream(); 
			workbook =  (HSSFWorkbook) new HSSFWorkbook(fis);
	    	  //log("xls="+wb_hssf.getSheetName(0));
	    	 sheet = workbook.getSheetAt(0);
	    	
	 	        //Initializing the XML document
	 	    	final Pattern REGEX_PATTERN = Pattern.compile("[^\\p{ASCII}]+");
	 	        if(sheet==null)
	 	        	request.getSession().setAttribute("notify", "Please upload proper File");
	 	        else
	 	        {
	 	        	  Iterator<?> rows     = sheet.rowIterator ();
	 	               ArrayList<String> excelDatac;
	 	               
	      	        while (rows.hasNext ()) 
	      	        {
	      	        HSSFRow row =  (HSSFRow) rows.next(); 

	      	            if(row.getRowNum()==0)
	      	            	continue;
	      	          
	      	            Iterator<?> cells = row.cellIterator (); 
	      	            ArrayList<String> rowData = new ArrayList<String>();
	      	            for(short i=0;i<6;i++)
	      	            {
	      	            	HSSFCell cell	=	row.getCell(i);
	      	           
	      	            	
	      	            	if(cell==null)
	      	            		rowData.add("");
	      	            	else
	      	            		{ 
	      	            		switch (cell.getCellType ())
	  	     	                {
	  		     	                case HSSFCell.CELL_TYPE_NUMERIC :
	  		     	               
	  		     	                
	  		     	                {
	  		     	                    // Date CELL TYPE
	  		     	                    if(HSSFDateUtil.isCellDateFormatted((HSSFCell) cell))
	  		     	                     {
	  		     	               
	  		     	                    	rowData.add(new SimpleDateFormat("dd/MM/YYYY").format(cell.getDateCellValue()));
	  		     	           
	  		     	                    }
	  		     	                    else // NUMERIC CELL TYPE
	  		     	                    	rowData.add(cell.getNumericCellValue () + "");
	  	
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
	  		     	                {	//HSSFRichTextString blankCell	=	cell.get.getRichStringCellValue();
	  		     	                	
	  		     	              
	  		     	                    
	  		     	                	String blankCell	=	cell.getStringCellValue().trim().replaceAll("[^\\x00-\\x7F]", "");
	  		     	                	rowData.add(blankCell);
	  		     	                	break;
	  		     	                }
	  		     	                default:
	  		     	                {
	  		     	                    // types other than String and Numeric.
	  		     	                    System.out.println ("Type not supported.");
	  		     	                    break;
	  		     	                }
	  	     	                } // end switch
	  	            		}//else
	      	            	
	      	            }//for
	      	          
	      	          excelDatar.add(rowData);//adding Excel data to ArrayList
	      	        numclaimsettlementnumber++; 
	      	        } //end while
	      	     
	 	        }
	 	        object[0]=numclaimsettlementnumber+"";//adding no. of policies
				object[1]=excelDatar;//adding all rows data
	 		     return object;
		}
		
		
		
		private String convertExcelDataIntoXml(HttpServletRequest request,Object[] objects,FormFile formFile,String fileType) throws JAXBException {
			
				 
			    	String noOfPolicies=(String)objects[0];
					ArrayList<ArrayList<String>> excelDataRows=(ArrayList<ArrayList<String>>)objects[1];
			    	           String uploadType="BNO";

			    	             
                               Long uploadedBy1=(Long) TTKCommon.getUserSeqId(request);
                               String uploadedBy = uploadedBy1.toString();
                               
			    	
			        String batchNo=uploadType+"-"+ new SimpleDateFormat("yyyyMMddHHmmSSS").format(new Date());//policyType+"-"+ new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+"-"+vidalOfficeName+"-"+abrCode+"-"+uploadType+"-"+groupId;
			       
			       String policyFileName =batchNo+"."+fileType;  
			        
			       
			        //prepare the marshaling
			        
			        Batch batch=new Batch();
			        JAXBContext contextObj = JAXBContext.newInstance(Batch.class); 
					Marshaller marshallerObj = contextObj.createMarshaller(); 
					marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				    FileDetail fileDetail=new FileDetail(policyFileName,batchNo,noOfPolicies,uploadedBy,uploadType);
				   batch.setFileDetail(fileDetail);
			       
				 //write the data into xml file				    
		    		Iterator<ArrayList<String>>rit=excelDataRows.iterator();
		    		
		    		ArrayList<Policy> policyAl=new ArrayList<>();
		    		
			    		while(rit.hasNext()){
			    			 ArrayList<String> rlist=rit.next();
			    			 Policy policy=new Policy();
			    			 policy.setUploadstatus("Y");
			    			
			    			 Payments payments=new Payments();		    			 
	
			    			 payments.setSettlementnumber(rlist.get(0)); 
			    			 payments.setChequeno(rlist.get(1)); 
			    			 payments.setChequeamt(rlist.get(2)); 
			    			 payments.setStatus(rlist.get(3)); 
			    			 payments.setIssueddate(rlist.get(4));  
			    			 payments.setComments(rlist.get(5));
			    			 
			    			 policy.setPayments(payments);
			    			 
					    		policyAl.add(policy);		    		   		   
			    			}//while		    		
			    	
			    		 batch.setPolicy(policyAl); 		    			  
		    			  
		    			  File xmlPath=new File(TTKPropertiesReader.getPropertyValue("dubaiXmlFinDir"));
		    			  if(!xmlPath.exists())xmlPath.mkdirs();
		    			  String xmlFilePath=TTKPropertiesReader.getPropertyValue("dubaiXmlFinDir")+batchNo+".xml";  
		    			  marshallerObj.marshal(batch,new File(xmlFilePath));		    			  
		    		
                                   return batchNo; 
                                   
		}            
		
		 public ActionForward doViewLogFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,
					HttpServletResponse response) throws TTKException {
					
			 ByteArrayOutputStream baos=null;
			 OutputStream sos = null;
			 FileInputStream fis = null; 
			 BufferedInputStream bis =null;
			
			try {
				 String fileName=request.getParameter("fileName");
				 File file=new File(TTKPropertiesReader.getPropertyValue("dubaiLogFinDir")+fileName.trim());
				  response.setContentType("application/txt");
				  //response.setContentType("application/xls");
			      response.setHeader("Content-Disposition", "attachment;filename="+fileName);
			                                                       
		              fis= new FileInputStream(file);
		              bis = new BufferedInputStream(fis);
		              baos=new ByteArrayOutputStream();
		              int ch;
		                    while ((ch = bis.read()) != -1) baos.write(ch);
		                    sos = response.getOutputStream();
		                    baos.writeTo(sos);  
		                    baos.flush();      
		                    sos.flush();  
				
			} 
			catch(Exception exp)
			{
				return this.processExceptions(request,mapping,new TTKException(exp,strChequeList));
			}//end of catch(Exception exp)
		     finally{
		              try{
		                    if(baos!=null)baos.close();                                           
		                    if(sos!=null)sos.close();
		                    if(bis!=null)bis.close();
		                    if(fis!=null)fis.close();
		                    }catch(Exception exception){
		                    log.error(exception.getMessage(), exception);
		                    }                     
		            }
				return null;
		}
		
		
		
		
		
	private ChequeManager getChequeManagerObject() throws TTKException
	{
		ChequeManager chequeManager = null;
		try
		{
			if(chequeManager == null)
			{
				InitialContext ctx = new InitialContext();
				chequeManager = (ChequeManager) ctx.lookup("java:global/TTKServices/business.ejb3/ChequeManagerBean!com.ttk.business.finance.ChequeManager");
			}//end if(chequeManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "ChequeSearch");
		}//end of catch
		return chequeManager;
	}//end getChequeManagerObject()
}//end of class ChequeSearchAction