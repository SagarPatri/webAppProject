
package com.ttk.action.onlineforms.partnerLogin; 
/**
 * @ (#) ProviderClaimsAction.java 17th Nov 2017
 * Project      : TTK HealthCare Services
 * File         : ProviderClaimsAction.java
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 17th Nov 2017
 *
 * @author       :  Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.ibm.icu.math.BigDecimal;
import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.onlineforms.OnlinePreAuthManager;
import com.ttk.business.onlineforms.providerLogin.OnlineProviderManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.onlineforms.providerLogin.ClaimDetailVO;
import com.ttk.dto.onlineforms.providerLogin.PreAuthSearchVO;
import com.ttk.dto.preauth.ActivityDetailsVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
import com.ttk.dto.preauth.DrugDetailsVO;
import com.ttk.dto.preauth.ShortfallVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for Searching the List Policies to see the Online Account Info.
 * This also provides deletion and updation of products.
 */
public class PartnerClaimsAction extends TTKAction {

    private static final Logger log = Logger.getLogger( PartnerClaimsAction.class );


    private static final String strClaimsUpload="claimsUpload";
    private static final String strClaimsUploadError="claimsUpload";
    private static final String strClaimsSearch="claimsSearch";
    private static final String strViewClaimDetails="viewPartnerClaimDetails";
    private static final String strBackward="Backward";
    private static final String strForward="Forward";
    private static final String strViewShortfallDetails="viewShortfallDetails";

    
    /**
	    * 
	    * @param mapping
	    * @param form
	    * @param request
	    * @param response
	    * @return
	    * @throws Exception
	    */
	   public ActionForward doSearchClaim(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		           HttpServletResponse response) throws Exception{
			try{
				log.debug("Inside the doSearchClaim method of PartnerClaimsAction");
				setOnlineLinks(request);
				
				DynaActionForm frmOnlineClaimLog =(DynaActionForm)form;
				ArrayList<Object> alOnlineClaimsList = null;
				TableData tableData=null;
				String strPageID =""; 
				String strSortID ="";	
				
				OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
				
				tableData= TTKCommon.getTableData(request);
			
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);
			}
			strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
					if(!strPageID.equals(""))
				{
						tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
						return (mapping.findForward("claimsSearch"));
				}
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");
				}
			}
			else
			{
				tableData.createTableInfo("PartnerClaimSearchTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));						
				tableData.modifySearchData("search");
				
			}
			alOnlineClaimsList= onlineProviderManager.getPartnerClaimSearchList(tableData.getSearchData());
			tableData.setData(alOnlineClaimsList, "search");
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strClaimsSearch, mapping, request);
			}//end of try
		catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}
		catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"strClaimsUploadError"));
		}//end of catch(Exception exp)
	}//end of doSearchClaim(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	   
    

		/*
		 * Provider Login Claims Log Search screen
		 */

		   public ActionForward doClaimsLog(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			           HttpServletResponse response) throws Exception{
			try{
					log.debug("Inside the doClaimsLog method of PartnerClaimsAction");
					setOnlineLinks(request);
					
					DynaActionForm frmOnlineClaimLog =(DynaActionForm)form;
					frmOnlineClaimLog.initialize(mapping);     
					
					TableData tableData=TTKCommon.getTableData(request);
					tableData.createTableInfo("PartnerClaimSearchTable",null);//ClaimSearchTable
					request.getSession().setAttribute("tableData",tableData);
					
					return this.getForward(strClaimsSearch, mapping, request);
				}
				catch(TTKException expTTK)
				{
					return this.processOnlineExceptions(request, mapping, expTTK);
				}
					catch(Exception exp)
				{
						return this.processOnlineExceptions(request, mapping, new TTKException(exp,"strClaimsUploadError"));
				}
		   }
	   
		   /**
		    * this method will add search criteria fields and values to the arraylist and will return it
		    * @param frmOnlinePreAuthLog formbean which contains the search fields
		    * @return ArrayList contains search parameters
		    */
		   private ArrayList<Object> populateSearchCriteria(DynaActionForm frmOnlinePreAuthLog,HttpServletRequest request) throws TTKException
		   {
			   
				UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile");

					//build the column names along with their values to be searched
			       	ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();
			       	//prepare the search BOX parameters
			        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("trtmtFromDate")));//0
			       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("trtmtToDate")));//1
			   	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("clmFromDate")));//2
			        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("clmToDate")));//3
			       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("patientName")));//4
			       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("status")));//5
			   	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("invoiceNumber")));//6
			       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("batchNumber")));//7
			        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("patientCardId")));//8
			       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("claimNumber")));//9
			        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("providerName")));//10
			        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("country")));//11 
			        alSearchBoxParams.add((String)userSecurityProfile.getEmpanelNumber());//12 Empanel Number
				
		   	return alSearchBoxParams;
		   }//end of populateSearchCriteria(DynaActionForm frmOnlinePreAuthLog,HttpServletRequest request) 
		   

		   
		   
		   
		   
		   
		   
    
/*
 *     (non-Javadoc)
 * @see com.ttk.action.TTKAction#doDefault(org.apache.struts.action.ActionMapping, 
 * org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
 * 
 * Do Default method to show claims uploads screen
 * 
 */
   public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           HttpServletResponse response) throws Exception{
	try{
			log.debug("Inside the doDefault method of ProviderClaimsAction");
			setOnlineLinks(request);
			
			DynaActionForm frmOnlineClaimLog =(DynaActionForm)form;
			frmOnlineClaimLog.initialize(mapping);     
			
			return this.getForward(strClaimsUpload, mapping, request);
		}
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}
			catch(Exception exp)
		{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,"strClaimsUploadError"));
		}
   }

   
   
   
   
   private boolean validateCellVal(String validateData){
	   boolean bool	=	true;;
	   
	   if(validateData.equals("")  || validateData.equals(" ") || validateData==null)
		   bool	=	false;
	   
	   return bool;
   }
   
   
   /*
    * Extract Claims Data
    */
   
   private ArrayList extractExcelData(HSSFSheet sheet,String[] batchNos,String hospSeqId,String addedBy){
	   
	   ArrayList<ArrayList<String>> claimsData	=	new ArrayList<ArrayList<String>>();
	   ArrayList<String> rowData = new ArrayList<String>();
	        Iterator<?> rows     = sheet.rowIterator ();
	        
	        while (rows.hasNext ()) 
	        {
	        	HSSFRow row =  (HSSFRow) rows.next(); 
	        	
	            if(row.getRowNum()==0)
	            	continue;
	         /*  To avoid first row of Excel	
          	*/
	            
	            Iterator<?> cells = row.cellIterator (); 
	             rowData = new ArrayList<String>();

	      	   rowData.add(batchNos[1]);
	      	   rowData.add(hospSeqId);
	      	   rowData.add(addedBy);
	      	  BigDecimal  bdecimalVal	=	null;; 
	            String temp	=	"";
	            for(short i=0;i<=31;i++)
	            {
	            	HSSFCell cell	=	row.getCell(i);
	            	if(cell!=null){
		            	if(cell.getCellType()==0){
		            		// Date CELL TYPE
	 	                    if(HSSFDateUtil.isCellDateFormatted((HSSFCell) cell)){
	 	                    	temp	=	new SimpleDateFormat("dd/MM/yyyy").format(cell.getDateCellValue());
	 	                    }
	 	                    else // NUMERIC CELL TYPE
	 	                    {
	 	                    	temp	=	cell.getNumericCellValue () + "";
	 	                 		//Convert Exponent value to Big Decimals
								bdecimalVal	=	new BigDecimal(temp);
								temp	=	bdecimalVal.longValue()+"";
	 	                    }
		            		rowData.add(temp);
		            	}else{
		            		rowData.add(row.getCell(i)+"");
		            	}
	            	}else{
	            		rowData.add("");
	            	}
	            }//for
	         claimsData.add(rowData);
	        } //end while
	        
	        return claimsData;
   }

   
   /*
    * Create Error Log
    */
   
   private HSSFWorkbook createErrorLog(HSSFWorkbook errWorkbook,ArrayList<String[]> alErrorMsg){
	   HSSFSheet errSheet			=	errWorkbook.createSheet("ErrorLog"); 
			HSSFRow errRow				=	null;
			String[] tempStrArrObj			=	null;
			
			HSSFCellStyle style			=	errWorkbook.createCellStyle();
		HSSFFont font					=	errWorkbook.createFont();
		
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setFontHeightInPoints((short)10);;
		font.setBoldweight((short)20);
		font.setColor(HSSFColor.WHITE.index);
		style.setFillBackgroundColor(HSSFColor.DARK_BLUE.index);
		style.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setFont(font);
			
			//Creating Headings
			errRow	=	errSheet.createRow(0);
			errRow.createCell((short) 0).setCellValue("Sl No.");
			
			errRow.createCell((short) 1).setCellValue("INVOICE_NO."); 
			errRow.createCell((short) 2).setCellValue("MEMBER_NAME"); 
			errRow.createCell((short) 3).setCellValue("ENROLLMENT_ID");
			errRow.createCell((short) 4).setCellValue("PREAPPROVAL_NO");
			errRow.createCell((short) 5).setCellValue("DATE_OF_TREATMEMT");
			errRow.createCell((short) 6).setCellValue("DATE_OF_DISCHARGE");
			errRow.createCell((short) 7).setCellValue("SYSTEM_OF_MEDICINE");
			errRow.createCell((short) 8).setCellValue("BENEFIT_TYPE");
			errRow.createCell((short) 9).setCellValue("ENCOUNTER_TYPE");
			errRow.createCell((short) 10).setCellValue("CLINICIAN_ID");
			errRow.createCell((short) 11).setCellValue("CLINICIAN_NAME");
			errRow.createCell((short) 12).setCellValue("SYMPTOMS");
			errRow.createCell((short) 13).setCellValue("PRINCIPAL_ICD_CODE");
			errRow.createCell((short) 14).setCellValue("ICD_DESCRIPTION");
			errRow.createCell((short) 15).setCellValue("SECONDARY_ICD_CODE1");
			errRow.createCell((short) 16).setCellValue("SECONDARY_ICD_CODE2");
			errRow.createCell((short) 17).setCellValue("SECONDARY_ICD_CODE3");
			errRow.createCell((short) 18).setCellValue("SECONDARY_ICD_CODE4");
			errRow.createCell((short) 19).setCellValue("SECONDARY_ICD_CODE5");
			errRow.createCell((short) 20).setCellValue("FIRST_INCIDENT_DATE");
			errRow.createCell((short) 21).setCellValue("FIRST_REPORTED_DATE");
			errRow.createCell((short) 22).setCellValue("INTERNAL_SERVICE_CODE");
			errRow.createCell((short) 23).setCellValue("SERVICE_DESCRIPTION");
			errRow.createCell((short) 24).setCellValue("CPT_CODE");
			errRow.createCell((short) 25).setCellValue("AMOUNT_CLAIMED");
			errRow.createCell((short) 26).setCellValue("QUANTITY");
			errRow.createCell((short) 27).setCellValue("CURRENCY");
			errRow.createCell((short) 28).setCellValue("OBSERVATION");
			errRow.createCell((short) 29).setCellValue("ERROR_MESSAGE");
			
			Iterator its	=	(Iterator) errRow.cellIterator();
			HSSFCell ccell	=	null;
			short k	=	0;
			while(its.hasNext())
			{
				ccell	=	(HSSFCell) its.next();
				ccell.setCellStyle(style);
				k++;
			}
			
			for(int j=0;j<alErrorMsg.size();j++){
			tempStrArrObj		=	alErrorMsg.get(j);
			errRow	=	errSheet.createRow(j+1);
			for(int i=0;i<tempStrArrObj.length;i++){
				errRow.createCell((short) i).setCellValue(tempStrArrObj[i]);
			}
		}
			return errWorkbook;
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
          
	
	   
	   
	  
	   
	   /*
	    *on Forward of search Claims 
	    */
	   public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	   		HttpServletResponse response) throws Exception{
	   	try{
	   		setOnlineLinks(request);
	   		log.debug("Inside the doForward method of ProviderClaimsAction");
				TableData tableData = (TableData) request.getSession().getAttribute("tableData");
				OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
	           
	   		tableData.modifySearchData(strForward);//modify the search data
	   		//fetch the data from the data access layer and set the data to table object
	   		ArrayList<Object> claimLogSearch = onlineProviderManager.getClaimSearchList(tableData.getSearchData());
	   		tableData.setData(claimLogSearch, strForward);//set the table data
	   		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
	   		return this.getForward(strClaimsSearch, mapping, request);
	   	}//end of try
	   	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"strClaimsUploadError"));
			}//end of catch(Exception exp)
	   }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	   
	   
	   /*
	    *on Backward of search Claims 
	    */
	   public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	   		HttpServletResponse response) throws Exception{
	   	try{
	   		setOnlineLinks(request);
	   		log.debug("Inside the doBackward method of ProviderClaimsAction");
				TableData tableData = (TableData) request.getSession().getAttribute("tableData");
				OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
	           
	   		tableData.modifySearchData(strBackward);//modify the search data
	   		//fetch the data from the data access layer and set the data to table object
	   		ArrayList<Object> claimLogSearch = onlineProviderManager.getClaimSearchList(tableData.getSearchData());
	   		tableData.setData(claimLogSearch, strBackward);//set the table data
	   		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
	   		return this.getForward(strClaimsSearch, mapping, request);
	   	}//end of try
	   	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"strClaimsUploadError"));
			}//end of catch(Exception exp)
	   }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	   
	   
	   
	   /**on Click on Status link showing the Claim details 
	    * 
	   * @param mapping The ActionMapping used to select this instance
	     * @param form The optional ActionForm bean for this request (if any)
	     * @param request The HTTP request we are processing
	     * @param response The HTTP response we are creating
	     * @return ActionForward Where the control will be forwarded, after this request is processed
	     * @throws Exception if any error occurs
	    */
	   public ActionForward doViewClaimDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	   		HttpServletResponse response) throws Exception{
	   	try{
	   		setOnlineLinks(request);
	   		DynaActionForm frmOnlineClaimsDetails = (DynaActionForm)form;
	   		log.debug("Inside the doViewClaimDetails method of PartnerClaimsAction");
	   			String rownum	=	(String)request.getParameter("rownum");
				OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
				TableData tableData = (TableData) request.getSession().getAttribute("tableData");
				PreAuthSearchVO preAuthSearchVO	=	null;
				preAuthSearchVO = (PreAuthSearchVO)tableData.getRowInfo(Integer.parseInt(rownum));
				Object[] objects	=	onlineProviderManager.getClaimDetails(preAuthSearchVO.getClmSeqId());
	           
				ClaimDetailVO claimDetailVO	=	(ClaimDetailVO) objects[0];
				frmOnlineClaimsDetails = (DynaActionForm)FormUtils.setFormValues("frmOnlineClaimsDetails",
						claimDetailVO, this, mapping, request);
				
				ArrayList<DiagnosisDetailsVO> diagnosis = (ArrayList<DiagnosisDetailsVO>) objects[1];
				ArrayList<ActivityDetailsVO> alActivityDetails = (ArrayList<ActivityDetailsVO>) objects[2];
				ArrayList<String[]> alShortFallList	=	(ArrayList<String[]>) objects[3];
				request.getSession().setAttribute("claimDiagnosis", diagnosis);
				request.getSession().setAttribute("claimActivities", alActivityDetails);
				request.getSession().setAttribute("alShortFallList", alShortFallList);
				request.getSession().setAttribute("clmSeqId", preAuthSearchVO.getClmSeqId());
				
				
				request.getSession().setAttribute("frmOnlineClaimsDetails", frmOnlineClaimsDetails);
				
	   		return this.getForward(strViewClaimDetails, mapping, request);
	   	}//end of try
	   	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"strClaimsUploadError"));
			}//end of catch(Exception exp)
	   }//end of doViewClaimDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	   

	   /*
	    * View Shortfalls from Providers
	    */
	   public ActionForward doViewShortfall(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		   		HttpServletResponse response) throws Exception{
		   	try{
		   		setOnlineLinks(request);
		   		log.debug("Inside the doViewShortfall method of ProviderClaimsAction");
		   		DynaActionForm frmShortFall = (DynaActionForm)form;
		   		Long shortfallSeqId		=	new Long(request.getParameter("shortfallSeqId"));
		   		Long clmSeqId				=	(long) request.getSession().getAttribute("clmSeqId");
		   		
		   		PreAuthManager preAuthObject=this.getPreAuthManagerObject();
		   	  ArrayList<Object> alShortfallList = new ArrayList<Object>();
	          alShortfallList.add(shortfallSeqId);
	          alShortfallList.add(clmSeqId);
	          alShortfallList.add(null);
	          alShortfallList.add(TTKCommon.getUserSeqId(request));
	          ShortfallVO   shortfallVO=preAuthObject.getShortfallDetail(alShortfallList);
	          
	          //Get Member and Provider Data for Shortfall
	          OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
	          String[] shortFallData	=	onlineProviderManager.getMemProviderForShortfall(shortfallSeqId,"CLM");
	          frmShortFall = (DynaActionForm)FormUtils.setFormValues("frmShortFall", shortfallVO, this, mapping, request);

	          request.getSession().setAttribute("preauthShortfallQueries",shortfallVO.getShortfallQuestions());
	          request.getSession().setAttribute("frmShortFall",frmShortFall);
	          request.getSession().setAttribute("shortFallData", shortFallData);
	          return this.getForward(strViewShortfallDetails, mapping, request);
		   	}//end of try
		   	catch(TTKException expTTK)
				{
					return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
				}//end of catch(Exception exp)
		   }//end of doViewAuthDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	   
	   
	   /*
	    * Save Shortfalls from Providers
	    */
	   
	   public ActionForward doSaveShortfall(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		   		HttpServletResponse response) throws Exception{
		   	try{
		   		setOnlineLinks(request);
		   		log.info("Inside the doSaveShortfall method of PartnerClaimsAction");
		   		DynaActionForm frmShortFall = (DynaActionForm)form;
		   		ShortfallVO shortfallVO = (ShortfallVO)FormUtils.getFormValues(frmShortFall, this, mapping, request);
				OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
				
				// UPLOAD FILE STARTS
				FormFile formFile = null;
				formFile = (FormFile)frmShortFall.get("file");
				if(formFile.getFileSize()!=0)
				{
					String fileExtn = GetFileExtension(formFile.toString());
					if(!"pdf".equals(fileExtn)){
						TTKException expTTK = new TTKException();
						expTTK.setMessage("error.pdf.only.required");
						throw expTTK;
					}
				}
				String successYN	=	onlineProviderManager.saveShorfallDocs(shortfallVO.getShortfallSeqID(),formFile);
				if(successYN.equals("Y"))
					request.setAttribute("updated","message.addedSuccessfully");
		   		return this.getForward(strViewShortfallDetails, mapping, request);
		   	}//end of try
		   	catch(TTKException expTTK)
				{
					return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
				}//end of catch(Exception exp)
		   }//end of doSaveShortfall(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	  
	   /*
	    * On CLick on Error log link in claims Uploads 
	    */
	   
	   public ActionForward  doDownloadClmLog(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws TTKException {
		    
			 ByteArrayOutputStream baos=null;
		    OutputStream sos = null;
		    FileInputStream fis = null; 
		    BufferedInputStream bis =null;
		  try{   
				
				String strFile	=	TTKPropertiesReader.getPropertyValue("providerClmErrLogDir")+request.getParameter("batchNo")+".xls";
    		    
				response.setContentType("application/vnd.ms-excel");
				response.addHeader("Content-Disposition","attachment; filename="+request.getParameter("batchNo")+".xls");

    			File f = new File(strFile);
	    		if(f.isFile() && f.exists()){
	    			fis = new FileInputStream(f);
	    		}//end of if(strFile !="")
	    		else
	    		{
	    			fis = new FileInputStream(TTKPropertiesReader.getPropertyValue("WebLoginDocs")+"/default/"+"Test.doc");
	    		}//end of else
		    		
    			bis = new BufferedInputStream(fis);
	    		baos=new ByteArrayOutputStream();
	    		int ch;
	    		while ((ch = bis.read()) != -1)
	    		{
	    			baos.write(ch);
	    		}//end of while ((ch = bis.read()) != -1)
	    		sos = response.getOutputStream();
	    		baos.writeTo(sos);
				      
		            }catch(Exception exp)
		            	{
		            		return this.processExceptions(request, mapping, new TTKException(exp,"strClaimsUploadError"));
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
	   
	   /*
	    * On CLick To Download Claoms Upload template 
	    */
	   
	   public ActionForward  doDownloadClmTemplate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws TTKException {
		    
			 ByteArrayOutputStream baos=null;
		    OutputStream sos = null;
		    FileInputStream fis = null; 
		    BufferedInputStream bis =null;
		  try{   
				
				String strFile	=	TTKPropertiesReader.getPropertyValue("providerClmTemplate")+"claimUploadTemplate.xls";
    		    
				response.setContentType("application/vnd.ms-excel");
				response.addHeader("Content-Disposition","attachment; filename=claimUploadTemplate.xls");

    			File f = new File(strFile);
	    		if(f.isFile() && f.exists()){
	    			fis = new FileInputStream(f);
	    		}//end of if(strFile !="")
	    		else
	    		{
	    			fis = new FileInputStream(TTKPropertiesReader.getPropertyValue("WebLoginDocs")+"/default/"+"Test.doc");
	    		}//end of else
		    		
    			bis = new BufferedInputStream(fis);
	    		baos=new ByteArrayOutputStream();
	    		int ch;
	    		while ((ch = bis.read()) != -1)
	    		{
	    			baos.write(ch);
	    		}//end of while ((ch = bis.read()) != -1)
	    		sos = response.getOutputStream();
	    		baos.writeTo(sos);
				      
		            }catch(Exception exp)
		            	{
		            		return this.processExceptions(request, mapping, new TTKException(exp,"strClaimsUploadError"));
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
	   
	   
	   /*
	    * Generate Claim Letter
	    */
	   public ActionForward generateClaimLetter(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			try {
				setOnlineLinks(request);
				DynaActionForm frmOnlineClaimsDetails = (DynaActionForm) form;
				
				String claimSeqID = frmOnlineClaimsDetails.getString("clmSeqId");
				String claimSettelmentNo = frmOnlineClaimsDetails
						.getString("settlementNo");
				String claimStatus = frmOnlineClaimsDetails.getString("clmStatus");
				JasperReport mainJasperReport = null;
				JasperReport diagnosisJasperReport = null;
				JasperReport activityJasperReport = null;
				JasperPrint mainJasperPrint = null;
			String parameter = "";
			String mainJrxmlfile="";
			String activityJrxmlfile="";
			TTKReportDataSource mainTtkReportDataSource = null;
				TTKReportDataSource diagnosisTtkReportDataSource = null;
				TTKReportDataSource activityTtkReportDataSource = null;
			parameter="|"+claimSeqID+"|"+claimStatus+"|CLM|";
					 
				 mainJrxmlfile = "generalreports/ClaimApprovalOrDenialLetter.jrxml";
				 activityJrxmlfile = "generalreports/ClaimActivitiesDoc.jrxml";
				 mainTtkReportDataSource=new TTKReportDataSource("ClaimLetterFormat",parameter);
				
			 
			String diagnosisJrxmlfile = "generalreports/ClaimDiagnosisDoc.jrxml";
			 

				ByteArrayOutputStream boas = new ByteArrayOutputStream();

				String strPdfFile = TTKPropertiesReader
						.getPropertyValue("authorizationrptdir")
						+ claimSettelmentNo + ".pdf";
				JasperReport emptyReport = JasperCompileManager
						.compileReport("generalreports/EmptyReprot.jrxml");
				//mainJasperReport = JasperCompileManager.compileReport(mainJrxmlfile);
		 HashMap<String, Object> hashMap = new HashMap<String, Object>();
			
			
			diagnosisTtkReportDataSource = new TTKReportDataSource("DiagnosisDetails",parameter);  		
			activityTtkReportDataSource = new TTKReportDataSource("ActivityDetails",parameter);

			ResultSet main_report_RS=mainTtkReportDataSource.getResultData();
			
			mainJasperReport = JasperCompileManager.compileReport(mainJrxmlfile);
			diagnosisJasperReport = JasperCompileManager.compileReport(diagnosisJrxmlfile);
			activityJasperReport = JasperCompileManager.compileReport(activityJrxmlfile);			  
			
				 hashMap.put("diagnosisDataSource",diagnosisTtkReportDataSource);
				 hashMap.put("diagnosis",diagnosisJasperReport);		
				 hashMap.put("activityDataSource",activityTtkReportDataSource);		
				 hashMap.put("activity",activityJasperReport);
				 //JasperFillManager.fillReport(activityJasperReport, hashMap, activityTtkReportDataSource);						 
		 
		 if (main_report_RS == null&!main_report_RS.next())
		 {
			 mainJasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
		 }
		 else
		 {
			 main_report_RS.beforeFirst();
					mainJasperPrint = JasperFillManager.fillReport(
							mainJasperReport, hashMap, mainTtkReportDataSource);
				}// end of else
				JasperExportManager.exportReportToPdfStream(mainJasperPrint, boas);
				JasperExportManager.exportReportToPdfStream(mainJasperPrint, boas);
				JasperExportManager.exportReportToPdfFile(mainJasperPrint,
						strPdfFile);
				request.setAttribute("boas", boas);
				return mapping.findForward("reportdisplay");// This forward goes to
															// the in web.xml file
															// BinaryServlet
			}// end of try
			catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
			}// end of catch(TTKException expTTK)
			catch (Exception exp) {
				return this.processExceptions(request, mapping, new TTKException(
						exp, "onlineproviderinfo"));
			}// end of catch(Exception exp)
		}// end of generateClaimLetter(ActionMapping mapping,ActionForm
			// form,HttpServletRequest request,
			// HttpServletResponse response)
	
	   
	   //getManagerObject
	private PreAuthManager getProviderClaimsManagerObject() throws TTKException
	{
		PreAuthManager preAuthManager = null;
		try
		{
			if(preAuthManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthManager = (PreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthManagerBean!com.ttk.business.preauth.PreAuthManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "onlineproviderinfo");
		}//end of catch
		return preAuthManager;
	}//end getPreAuthManagerObject()
	
	
	
	 private OnlineProviderManager getOnlineAccessManagerObject() throws TTKException
	    {
	    	OnlineProviderManager onlineProviderManager = null;
	        try
	        {
	            if(onlineProviderManager == null)
	            {
	                InitialContext ctx = new InitialContext();
	                //onlineAccessManager = (OnlineAccessManager) ctx.lookup(OnlineAccessManager.class.getName());
	                onlineProviderManager = (OnlineProviderManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineProviderManagerBean!com.ttk.business.onlineforms.providerLogin.OnlineProviderManager");
	            }//end if
	        }//end of try
	        catch(Exception exp)
	        {
	            throw new TTKException(exp, "onlineproviderinfo");
	        }//end of catch
	        return onlineProviderManager;
	    }//end of getOnlineAccessManagerObject()
	 	
	 
	 private PreAuthManager getPreAuthManagerObject() throws TTKException
		{
			PreAuthManager preAuthManager = null;
			try
			{
				if(preAuthManager == null)
				{
					InitialContext ctx = new InitialContext();
					preAuthManager = (PreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthManagerBean!com.ttk.business.preauth.PreAuthManager");
				}//end if
			}//end of try
			catch(Exception exp)
			{
				throw new TTKException(exp, "onlineproviderinfo");
			}//end of catch
			return preAuthManager;
		}//end getPreAuthManagerObject()
	 
}//end of ProviderClaimsAction
