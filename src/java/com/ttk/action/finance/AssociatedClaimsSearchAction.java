/**
 * @ (#) AssociatedClaimsSearchAction.java
 * Project       : TTK HealthCare Services
 * File          : AssociatedClaimsSearchAction.java
 * Author        : Balaji C R B
 * Company       : Span Systems Corporation
 * Date Created  : September 12,2007
 *
 * @author       : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.finance;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.ibm.icu.text.SimpleDateFormat;
import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.finance.FloatAccountManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.ChequeVO;
import com.ttk.dto.finance.DebitNoteVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching the List of Associated Claims.
 */
public class AssociatedClaimsSearchAction extends TTKAction
{
	private static final Logger log = Logger.getLogger( AssociatedClaimsSearchAction.class );
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
//	forwards
	private static final String strClaims="associatedclaimslist";
	private static final String strClose="debitnotedetails";	
	private static final String strClaimsError="associatedclaims";
	private static final String struploadbatchdetaillog="uploadbatchdetail";
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
			log.debug("Inside doDefault mode of AssociatedClaimsSearchAction");
			StringBuffer sbfCaption = new StringBuffer();
			if(TTKCommon.getWebBoardId(request) == null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.floatno.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request) == null)
			TableData  tableData =TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			DynaActionForm frmFloatAccDetails =(DynaActionForm)request.getSession().getAttribute("frmFloatAccDetails");
			DynaActionForm frmAssociatedClaims = (DynaActionForm)form;
			//create the required grid table
			tableData.createTableInfo("AssociateClaimsSearchTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			request.getSession().setAttribute("searchparam", null);
			sbfCaption.append("List of Claims - ").append(" [").append(frmFloatAccDetails.getString("floatNo")).append("]");
			frmAssociatedClaims.set("caption",String.valueOf(sbfCaption));
			frmAssociatedClaims.set("associatedList","DBU");
			return this.getForward(strClaims, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strClaimsError));
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
			log.debug("Inside doSearch of AssociatedClaimsSearchAction");
			setLinks(request);
			TableData  tableData =TTKCommon.getTableData(request);
			FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			StringBuffer strClaimsAmt = new StringBuffer();
			DynaActionForm frmAssociatedClaims=(DynaActionForm)form;
            //if the page number or column to sort is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
    		{
				if(!strPageID.equals(""))
    			{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    				return (mapping.findForward(strClaims));
    			}//end of if(!strPageID.equals(""))
				else
				{
    				tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
    				tableData.modifySearchData("sort");//modify the search data
				}//end of else
				
    		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else 
			{
				//create the required grid table
				tableData.createTableInfo("AssociateClaimsSearchTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alClaims= floatAccountManagerObject.getDebitNoteClaimList(tableData.getSearchData());
			tableData.setData(alClaims, "search");
			

    		//impl for loading claim amount in the grid
    		if(alClaims != null && alClaims.size() > 0 )
    		{
    			String strComma="";
    			for(int i=0 ;i<alClaims.size() ;i++)
    			{
    				if(i!=0)
    				{
    					strComma=",";
    				}//end of if(i!=0)
    				if(((ChequeVO)alClaims.get(i)).getClaimAmt()!=null)
    				{
    					strClaimsAmt  = strClaimsAmt.append(strComma+"\""+((ChequeVO)alClaims.get(i)).
    														getClaimAmt().toString()+"\"");
    				}//end of if(((ChequeVO)alClaims.get(i)).getClaimAmt()!=null)
    				else
    				{
    					strClaimsAmt  = strClaimsAmt.append(strComma+"\""+"0"+"\"");
    				}//end of else
    			}//end of for(int i=0 ;i<alClaims.size() ;i++)
    			frmAssociatedClaims.set("strClaimsAmt",strClaimsAmt.toString());
    			
    			
    		}//end of if(alClaims != null && alClaims.size() > 0 )
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strClaims, mapping, request);
		}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strClaimsError));
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
    		log.debug("Inside doForward of AssociatedClaimsSearchAction");
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		tableData.modifySearchData(strForward);//modify the search data
    		ArrayList alClaims = floatAccountManagerObject.getDebitNoteClaimList(tableData.getSearchData());
    		tableData.setData(alClaims, strForward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the tableData object to session	
    		return this.getForward(strClaims, mapping, request);//finally return to the grid screen    		
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strClaimsError));
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
    		log.debug("Inside doBackward of AssociatedClaimsSearchAction");
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		ArrayList alClaims = floatAccountManagerObject.getDebitNoteClaimList(tableData.getSearchData());
    		tableData.setData(alClaims, strBackward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
    		return this.getForward(strClaims, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strClaimsError));
		}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to Associate or Exclude the selected claim(s)
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doAssociateExclude(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside doAssociateExclude of AssociatedClaimsSearchAction");
    		setLinks(request);
    		ArrayList<Object> alClaimsList = new ArrayList<Object>();
    		String strClaimSeqIds = "";
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		TableData  tableData =TTKCommon.getTableData(request);
    		DynaActionForm frmDebitNoteDetails =(DynaActionForm) request.getSession().getAttribute("frmDebitNoteDetails");
    		strClaimSeqIds = this.getConcatenatedSeqID(request, (TableData)request.getSession().getAttribute("tableData"));
    		//alClaimsList.add(new Long("1"));//debit seq id
    		alClaimsList.add(new Long((String)frmDebitNoteDetails.get("debitNoteSeqID")));
    		alClaimsList.add(strClaimSeqIds);
    		if(request.getParameter("associatedList").equals("DBA")){
    			alClaimsList.add("DBU");
    		}//end of if(request.getParameter("associatedList").equals("DBA")){
    		else {
    			alClaimsList.add("DBA");
    		}//end of else 
    		alClaimsList.add(TTKCommon.getUserSeqId(request));//user seq id
    		int intResult = floatAccountManagerObject.associateClaims(alClaimsList);//to associate/Exclude
    		 //refresh the grid 
    		ArrayList alClaims = floatAccountManagerObject.getDebitNoteClaimList(tableData.getSearchData());
            if(alClaims.size() == 0 || intResult == tableData.getData().size())
      	    {
      	    	tableData.modifySearchData("Delete");
                int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
                					 getSearchData().size()-2));
                if(iStartRowCount > 0)
                 {
                	alClaims = floatAccountManagerObject.getDebitNoteClaimList(tableData.getSearchData());
                 }//end of if(iStartRowCount > 0)
             }//end if(alContact.size() == 0 || iCount == tableData.getData().size())
            tableData.setData(alClaims, "Delete");
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward(strClaims, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strClaimsError));
		}//end of catch(Exception exp)
    }//end of doAssociateExclude()
    
    /**
     * This method is used to close the current page and return to previous page.
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
    		log.debug("Inside doClose of AssociatedClaimsSearchAction");
    		StringBuffer sbfCaption = new StringBuffer();
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		DebitNoteVO debitNoteVO = null;
    		DynaActionForm frmFloatAccDetails =(DynaActionForm)request.getSession().getAttribute("frmFloatAccDetails");
    		DynaActionForm frmDebitNoteDetails =(DynaActionForm)request.getSession().getAttribute("frmDebitNoteDetails");
    		debitNoteVO=floatAccountManagerObject.getDebitNoteDetail(Long.parseLong(frmDebitNoteDetails.getString("debitNoteSeqID"))    ,
					  new Long(TTKCommon.getUserSeqId(request).toString()));
    		frmDebitNoteDetails = (DynaActionForm)FormUtils.setFormValues("frmDebitNoteDetails",
					debitNoteVO, this, mapping, request);
    		//added for thirumalai debit note 02/09/2013
    		frmDebitNoteDetails.set("debitType",debitNoteVO.getDebitNoteTypeID());
    		//added for thirumalai debit note
    		sbfCaption.append("Debit Note Details - ").append("Edit [").append(frmFloatAccDetails.getString("floatNo")).append("]");
			frmDebitNoteDetails.set("caption",String.valueOf(sbfCaption));
    		request.getSession().setAttribute("frmDebitNoteDetails",frmDebitNoteDetails);
    		return this.getForward(strClose, mapping, request);//finally return to the grid screen
    		//return mapping.findForward(strClose);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strClaimsError));
		}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    	
    
    public ActionForward    doLogDetailsAssociateExcelUploads(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	log.debug("Inside PaymentAdviceAction doLogDetailsExcelUploads");
    	try
    	{
    		setLinks(request);
    		String strActiveLink=TTKCommon.getActiveLink(request);
    		DynaActionForm frmAssociatedClaims=(DynaActionForm)form;
    		String flag = request.getParameter("Flag");
    		String startDate = frmAssociatedClaims.getString("startDate");
    		String endDate = frmAssociatedClaims.getString("endDate");
    		ArrayList alData	=	null; 
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		alData=floatAccountManagerObject.getLogDetailsExcelUpload(startDate,endDate,flag);
    		request.getSession().setAttribute("alData", alData);
    		return (mapping.findForward(struploadbatchdetaillog));
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strClaimsError));
    	}//end of catch(Exception exp)
    }
    
    

    public ActionForward  doShowAssociateClaimTemplate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	log.debug("Inside ClaimsSearchAction doShowAssociateClaimTemplate");
    	ByteArrayOutputStream baos=null;
		OutputStream sos = null;
		FileInputStream fis= null; 
		File file = null;
		BufferedInputStream bis =null;

    	try{
    		
    	
    			setLinks(request);
    			response.setContentType("application/txt");
    			response.setHeader("Content-Disposition", "attachment;filename=DebitNoteTemplate.xls");

    			String fileName =	TTKPropertiesReader.getPropertyValue("Invoicerptdir")+"ClaimSettlementNumber.xls";
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
                return (mapping.findForward(strClaims));
    	}
    		catch(TTKException expTTK)
    		{
    			return this.processExceptions(request, mapping, expTTK);
    		}//end of catch(TTKException expTTK)
    		catch(Exception exp)
    		{
    			return this.processExceptions(request,mapping,new TTKException(exp,strClaimsError));
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
    				return this.processExceptions(request,mapping,new TTKException(exp,strClaimsError));
    			}//                 
    		}
    	}


    		
    
    	
    public ActionForward  doUploadAssociatedClaims(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	log.debug("Inside ClaimsSearchAction doUploadClaimSettlementNumber");
    	try{
            ArrayList<Object> alClaimsList = new ArrayList<Object>();
    		String strClaimSeqIds = "";
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		String strNotify = null;
    		String remarks = null;
    		String noOfClaimSettlementNumber=null;
    		setLinks(request);
    		Object[] excelData=null;
    		DynaActionForm frmAssociatedClaims=(DynaActionForm)form;
    		TableData  tableData =TTKCommon.getTableData(request);
    		FormFile formFile = null;
    		String appendClaimsettlementnumber=null;
    		DynaActionForm frmDebitNoteDetails =(DynaActionForm) request.getSession().getAttribute("frmDebitNoteDetails");

    		formFile = (FormFile)frmAssociatedClaims.get("stmFile");
    		if(formFile==null||formFile.getFileSize()==0)
    		{
    			strNotify="Please select the .xls file";
    			request.getSession().setAttribute("notify", strNotify);
    		}
    		else
    		{
    			String[] arr=formFile.getFileName().split("[.]");
    			String fileType=arr[arr.length-1];
    			if(!("xls".equalsIgnoreCase(fileType)||"xlsx".equalsIgnoreCase(fileType)))
    			{
    				strNotify="File Type should be xls or xlsx";	        	  
    				request.getSession().setAttribute("notify", strNotify);           
    			}

    		}
    		if(formFile.getFileSize()>(1024*1024*1024)) 
    		{
    			strNotify="File Length Lessthan 3GB";	        	  
    			request.getSession().setAttribute("notify", strNotify);

    		}
    		if(strNotify!=null && strNotify.length()!=0)
    		{
    			return (mapping.findForward(strClaims));
    		}
    		else
    		{

    			String[] arr=formFile.getFileName().split("[.]");
    			String fileType=arr[arr.length-1];
    			excelData=this.getExcelData(request,formFile,fileType,9);
    			noOfClaimSettlementNumber=(String)excelData[0];
    			frmAssociatedClaims.set("noofclaimssettlementnum",noOfClaimSettlementNumber);
    			ArrayList<ArrayList<String>> excelDataRows=(ArrayList<ArrayList<String>>)excelData[1];
    			appendClaimsettlementnumber =  this.getConcatenatedClaimSettlementNumber(excelDataRows);
    			alClaimsList.add(new Long((String)frmDebitNoteDetails.get("debitNoteSeqID")));
    			alClaimsList.add(appendClaimsettlementnumber);
    			if(request.getParameter("associatedList").equals("DBA")){
        		
        			alClaimsList.add("DBU");
        		}//
    			else if(request.getParameter("associatedList").equals("DBU")){
    			
    				alClaimsList.add("DBA");
    			}//end of else 
    			alClaimsList.add(TTKCommon.getUserSeqId(request));
    			int intResult = floatAccountManagerObject.associateClaimsExcelUpload(alClaimsList);//to associate/Exclude
    			    			
    			request.getSession().removeAttribute("tableData");
    			//refresh the grid 

    			/*ArrayList alClaims = floatAccountManagerObject.getDebitNoteClaimList(tableData.getSearchData());
    			 if(alClaims.size() == 0 || intResult == tableData.getData().size())
    	      	    {
    	      	    	tableData.modifySearchData("Delete");
    	                int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
    	                					 getSearchData().size()-2));
    	                if(iStartRowCount > 0)
    	                 {
    	                	alClaims = floatAccountManagerObject.getDebitNoteClaimList(tableData.getSearchData());
    	                 }//end of if(iStartRowCount > 0)
    	             }//end if(alContact.size() == 0 || iCount == tableData.getData().size())
    			tableData.setData(alClaims, "Delete");
    			request.getSession().setAttribute("tableData",tableData);*/
    			
    			return mapping.findForward(strClaims);
    		}
    	
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strClaimsError));
    	}//end of catch(Exception exp)

    }

    
    
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
      	            for(short i=0;i<=9;i++)
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
  		     	                    if(HSSFDateUtil.isCellDateFormatted((HSSFCell) cell)){
  		     	                    	rowData.add(new SimpleDateFormat("dd-MM-YYYY").format(cell.getDateCellValue()));
  		     	                    }
  		     	                    else // NUMERIC CELL TYPE
  		     	                    	rowData.add(cell.getNumericCellValue () + "");
  		     	                    break;
  		     	                }
  		     	                case HSSFCell.CELL_TYPE_STRING :
  		     	                {
  		     	                	String richTextString = cell.getStringCellValue().trim();
  		     	                	richTextString	=	REGEX_PATTERN.matcher(richTextString).replaceAll("").trim();
  		     	                    rowData.add(richTextString);
  		     	                    break;
  		     	                }
  		     	                case HSSFCell.CELL_TYPE_BLANK :
  		     	                {	
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

    
    private String getConcatenatedClaimSettlementNumber(ArrayList<ArrayList<String>> excelDataRows) {
		// TODO Auto-generated method stub
		log.info("Inside ClaimsSearchAction class  getConcatenatedClaimSettlementNumber method ");
		StringBuffer sbfConcatenatedClaimSettlementNum=new StringBuffer("|");
		Iterator<ArrayList<String>>rit=excelDataRows.iterator();
		int iCounter=0;
		while(rit.hasNext())
		{
			ArrayList<String> rlist=rit.next();
             if((rlist!=null)&& rlist.size()!=0)
			{
				if(rlist.get(0)!=null)
				{
					if(iCounter==0)
					{
						sbfConcatenatedClaimSettlementNum.append(rlist.get(0));
                     }//end of if(iCounter==0)
					else
					{
						sbfConcatenatedClaimSettlementNum.append("|").append(rlist.get(0));
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
					} // end of if((strChOpt!=null)&& strChOpt.length!=0)
			iCounter++;
		}
		sbfConcatenatedClaimSettlementNum.append("|");
		return sbfConcatenatedClaimSettlementNum.toString();
	}

    
    
	/**
	 * Returns the ContactManager session object for invoking methods on it.
	 * @return ContactManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private FloatAccountManager getfloatAccountManagerObject() throws TTKException
	{
		FloatAccountManager floatAccountManager = null;
		try
		{
			if(floatAccountManager == null)
			{
				InitialContext ctx = new InitialContext();
				floatAccountManager = (FloatAccountManager) ctx.lookup("java:global/TTKServices/business.ejb3/FloatAccountManagerBean!com.ttk.business.finance.FloatAccountManager");
			}//end of if(contactManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "floataccount");
		}//end of catch
		return floatAccountManager;
	}//end getfloatAccountManagerObject().
	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmClaims DynaActionForm
	 * @return ArrayList contains search parameters
	 */

	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmAssociatedClaims,HttpServletRequest request)throws TTKException
	{
		// build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		DynaActionForm frmDebitNoteDetails =(DynaActionForm) request.getSession().getAttribute("frmDebitNoteDetails");
		alSearchParams.add(TTKCommon.getWebBoardId(request));
		alSearchParams.add(new Long((String)frmDebitNoteDetails.get("debitNoteSeqID")));
		
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmAssociatedClaims.get("claimSettNo")));		
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmAssociatedClaims.get("enrollmentId")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmAssociatedClaims.get("claimantName")));
		alSearchParams.add((String) frmAssociatedClaims.get("claimType"));
		alSearchParams.add((String) frmAssociatedClaims.get("policyType"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String) frmAssociatedClaims.get("payee")));
		// Changes added for Debit Note CR KOC1163
		alSearchParams.add((String) frmAssociatedClaims.get("paymethod"));
		// End changes added for Debit Note CR KOC1163
		alSearchParams.add((String) frmAssociatedClaims.get("associatedList"));
		return alSearchParams;
	}// end of populateSearchCriteria(DynaActionForm frmTransaction,HttpServletRequest request)
	
	/**Returns the String of concatenated string of contact_seq_id delimeted by '|'.
	 * @param HttpServletRequest
	 * @param TableData
	 * @return String
	 */
	private String getConcatenatedSeqID(HttpServletRequest request,TableData tableData) {
		StringBuffer sbfConcatenatedSeqId=new StringBuffer("|");
		String strChOpt[]=request.getParameterValues("chkopt");
		if((strChOpt!=null)&& strChOpt.length!=0)
		{
			for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
			{
				if(strChOpt[iCounter]!=null)
				{
					if(iCounter==0)
					{
						sbfConcatenatedSeqId.append(String.valueOf(((ChequeVO)tableData.getRowInfo(
																Integer.parseInt(strChOpt[iCounter]))).getSeqID()));
					}//end of if(iCounter==0)
					else
					{
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((ChequeVO)tableData.getRowInfo(Integer.
																		parseInt(strChOpt[iCounter]))).getSeqID()));
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		sbfConcatenatedSeqId.append("|");
		return sbfConcatenatedSeqId.toString();
	} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)
	
}//end of AssociatedClaimsSearchAction

