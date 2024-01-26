package com.ttk.action.finance;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import java.io.InputStream;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import java.io.IOException;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.util.JRXmlUtils;

//added for Mail-SMS Template
import java.io.FileOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
//ended
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.finance.FloatAccountManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.finance.ChequeVO;

public class AddressSearchAction extends TTKAction
{
	
	private static final Logger log = Logger.getLogger( AddressSearchAction.class );
	//forwards
	private static final String strAddress="addresslist";
	private static final String strReportdisplay="reportdisplay";
	//  Exception Message Identifier
    private static final String strAddressExp="address";    

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
			log.debug("Inside AddressSearchAction doDefault");
			DynaActionForm frmAddressLabel=(DynaActionForm)form;
				if(TTKCommon.getWebBoardId(request) == null)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.floatno.required");
					throw expTTK;
				}//end of if(TTKCommon.getWebBoardId(request) == null)
			//get the table data from session if exists
    		TableData  tableData =TTKCommon.getTableData(request);
    		//create new table data object
    		tableData = new TableData();
    		//create the required grid table
    		tableData.createTableInfo("AddressLableTable",new ArrayList());
    		request.getSession().setAttribute("tableData",tableData);
    		frmAddressLabel.initialize(mapping);//reset the form data
			return this.getForward(strAddress, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strAddressExp));
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
    		log.debug("Inside AddressSearchAction doSearch");
    		TableData  tableData =TTKCommon.getTableData(request);
    		String Cigna_YN = "";
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
    		//if the page number or sort id is clicked
    		if(!strPageID.equals("") || !strSortID.equals(""))
    		{
    			if(strPageID.equals(""))
    			{
    				tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
    				tableData.modifySearchData("sort");//modify the search data
    			}///end of if(!strPageID.equals(""))
    			else
    			{
    				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    				return (mapping.findForward(strAddress));
    			}//end of else
    		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
    		else
    		{
    			//create the required grid table
    			tableData.createTableInfo("AddressLableTable",null);
    			//fetch the data from the data access layer and set the data to table object
    			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
    			tableData.modifySearchData("search");
    		}//end of else
    		ArrayList alBatchClaim=floatAccountManagerObject.getBatchClaimList(tableData.getSearchData());
    		//added for Mail-SMS Template for Cigna
    		/*ChequeVO chequeVO = new ChequeVO();
    		for(int i=0;i<alBatchClaim.size();i++)
    		{
    			chequeVO = (ChequeVO)alBatchClaim.get(i);
    		}
    		Cigna_YN = chequeVO.getCigna_YN();
    		*/tableData.setData(alBatchClaim, "search");
    		//request.getSession().setAttribute("Cigna_YN",Cigna_YN);
    		request.getSession().setAttribute("tableData",tableData);
    		//finally return to the grid screen
    		return this.getForward(strAddress, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strAddressExp));
    	}//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to get the details of the selected record from web-board.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    									  HttpServletResponse response) throws Exception{
    	log.debug("Inside AddressSearchAction doChangeWebBoard");
    	return 	doDefault(mapping,form,request,response);
    }//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doGenerateXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside AddressSearchAction doGenerateXL");
    		String strPaymentSeqID = getConcatenatedSeqID(request,(TableData)
    	  			request.getSession().getAttribute("tableData"));
    		
    		String strGenerateXL="/AddressSearchAction.do?mode=doGenerateCoverLetter&parameter="+strPaymentSeqID
			  +"&fileName=reports/finance/CoverLetter.jrxml&reportID=CheqCovrLett&reportType=PDF";
    		request.setAttribute("GenerateXL",strGenerateXL);

    		return this.getForward(strAddress, mapping, request);
    	}catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strAddressExp));
    	}//end of catch(Exception exp)
    }//end of doGenerateXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doAdrsGenerateXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside AddressSearchAction doAddressLabel");
    		String strPaymentSeqID = getConcatenatedSeqID(request,(TableData)
    	  			request.getSession().getAttribute("tableData"));
    		String strAddressLabel="/AddressSearchAction.do?mode=doGenerateCoverLetter&parameter="+strPaymentSeqID+"&fileName=reports/finance/AddressLabels.jrxml&reportID=FinAddreLebel&reportType=EXL";
    		request.setAttribute("GenerateXL",strAddressLabel);
    		return (mapping.findForward(strAddress));
    		//return this.getForward(strLabelAddress, mapping, request);
    	}catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strAddressExp));
    	}//end of catch(Exception exp)
    }//end of doAddressLabel(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
	  * This method is used to generate the report.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doGenerateCoverLetter(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 log.debug("Inside the doGenerateCoverLetter method of AddressSearchAction");
			 setLinks(request);
			 JasperReport jasperReport, emptyReport;
			 JasperPrint jasperPrint = null;
			 //String strParam =null;
			 String strJrxml =request.getParameter("fileName");
			 String strParams = request.getParameter("parameter");
			 log.debug("file name is :"+strJrxml);
			 log.debug("parameter name is :"+strParams);
			  TTKReportDataSource ttkReportDataSource = new TTKReportDataSource(request.getParameter("reportID"),strParams);
			 try
			 {
				 jasperReport = JasperCompileManager.compileReport(strJrxml);	
				 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
				 HashMap<String,String> hashMap = new HashMap<String,String>();
				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
				 if(!(ttkReportDataSource.getResultData().next()))
				 {
					 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
				 }//end of if(!(ttkReportDataSource.getResultData().next()))
				 else
				 {
					 ttkReportDataSource.getResultData().previous();
					 jasperPrint = JasperFillManager.fillReport(jasperReport,hashMap,ttkReportDataSource);
				 }//end of else
				 if(request.getParameter("reportType").equals("EXL"))//if the report type is Excel
				 {
					 JRXlsExporter jExcelApiExporter = new JRXlsExporter();
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
					 jExcelApiExporter.exportReport();
				 }//end of if(request.getParameter("reportType").equals("EXL"))
				 else
				 {
					 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
				 }
				 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
				 request.setAttribute("boas",boas);
				 return (mapping.findForward(strReportdisplay));
			 }//end of try
			 catch (JRException e)
			 {
				 e.printStackTrace();
			 }//end of catch(JRException e)
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
			 return this.processExceptions(request, mapping, new TTKException(exp,strAddressExp));
		 }//end of catch(Exception exp)
	 }//end of doGenerateCoverLetter(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	    /**
	  * This method is used to generate the report.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doGenerateComputationSheet(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	 {
		 try
		 {
			 setLinks(request);
			 log.debug("Inside the doGenerateComputationSheet method of AddressSearchAction");
			 String strPaymentSeqID = getConcatenatedSeqID(request,(TableData)request.getSession().getAttribute("tableData"));
			 
			 String strCignaPaymentSeqID = getEFTConcatenatedSeqID(request,(TableData)request.getSession().getAttribute("tableData"));
			 String strLabel="/AddressSearchAction.do?mode=doGenerateComputation&parameter="+strPaymentSeqID;
			 String strclaimNos=getConcatenatedClmSettelNo(request,(TableData)request.getSession().getAttribute("tableData"));
			 String strAddressLabel="/AddressSearchAction.do?mode=doGenerateCoverLetter&parameter="+strPaymentSeqID+"&fileName=reports/finance/CoverLetter.jrxml&reportID=CheqCovrLett&reportType=PDF";
			 String strCignaAddressLabel = "/AddressSearchAction.do?mode=doGenerateCignaApprovalLtr&parameter="+strCignaPaymentSeqID+"&claimNos="+strclaimNos;
			 request.setAttribute("GenerateXL",strAddressLabel);
			 request.setAttribute("openreport",strLabel);
			 /*
			  * CIGNA IS NOT USING IN DUBAI APPLICATION; BLOCKING IT FROM GENERATING ZIP FOLDER
			  * request.setAttribute("CignaGenerateXL",strCignaAddressLabel);*/
			 
			 return (mapping.findForward(strAddress));
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request,mapping,new TTKException(exp,strAddressExp));
		 }//end of catch(Exception exp)
	 }//end of doGenerateComputationSheet(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	 //start changes for cr koc1105
	 
	 //added for Mail-SMS Template for Cigna
	  /**
	  * This method is used to generate the report.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */	 
	 public ActionForward doGenerateCignaApprovalLtr(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	 {
		 try
		 {
			 setLinks(request);
             log.debug("Inside the doGenerateCignaApprovalLtr method of AddressSearchAction");
             
             String strPaymentSeqID = request.getParameter("parameter");
             String strclaimNos = request.getParameter("claimNos");
             String strConcatenatedSeqID =strPaymentSeqID;
             String strConcatenatedClaimSettelNo =strclaimNos;
             String strConcatenatedClaimSettelNo1[]=strConcatenatedClaimSettelNo.split("\\|");
             String strConcatenatedSeqID1[]= strConcatenatedSeqID.split("\\|");
             //added for Cigna
             ArrayList<Object> strArray = new ArrayList<Object>(); 
             ArrayList<InputStream> filesShrtFullPath = new ArrayList<InputStream>();
            for(int  i=0;i<strConcatenatedSeqID1.length;i++)
			{
				
				strConcatenatedSeqID=strConcatenatedSeqID1[i];
				strConcatenatedClaimSettelNo=strConcatenatedClaimSettelNo1[i];
				
				String destFileName=TTKPropertiesReader.getPropertyValue("computaiondir")+strConcatenatedClaimSettelNo+".pdf";
			    strConcatenatedSeqID="|"+strConcatenatedSeqID1[i]+"|".trim();
			    strArray.add(destFileName); 
			
			 
				TTKReportDataSource ttkReportDataSourceAD= null;
				TTKReportDataSource ttkReportDataSourcePO= null;
				String jrxmlfileAD="generalreports/ClaimSettlementLtrAdvisor.jrxml";
				String jrxmlfilePO="generalreports/ClaimSettlementLtrPO.jrxml";
				ttkReportDataSourceAD = new TTKReportDataSource("CignaClaimAppvlLtrAdv",strConcatenatedSeqID);
				ttkReportDataSourcePO = new TTKReportDataSource("CignaClaimAppvlLtrAdv",strConcatenatedSeqID);
				
				JasperReport jasperReportAD;JasperReport jasperReportPO;JasperReport jasperSubReport;JasperReport emptyReport; 
				jasperReportAD = JasperCompileManager.compileReport(jrxmlfileAD);
				jasperReportPO = JasperCompileManager.compileReport(jrxmlfilePO);
				 
				JasperPrint jasperPrintAD;
				JasperPrint jasperPrintPO;
			 
				jasperSubReport = JasperCompileManager.compileReport("generalreports/ClaimSetEmailAdvSub.jrxml");
				emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
				HashMap<String, Object> hashMap1 = new HashMap<String, Object>();
				//ByteArrayOutputStream boas1=new ByteArrayOutputStream();
			
				hashMap1.put("MyDataSource",new TTKReportDataSource("CignaClaimSubAppvlLtrAdv",strConcatenatedSeqID));
				hashMap1.put("ClaimSetEmailAdvSub",jasperSubReport);
			
				if(ttkReportDataSourceAD.getResultData().next())
				{
					 ttkReportDataSourceAD.getResultData().beforeFirst();
					 jasperPrintAD = JasperFillManager.fillReport( jasperReportAD, hashMap1, ttkReportDataSourceAD);					 
				}//end of if(ttkReportDataSource.getResultData().next()))
				else
				{
					 jasperPrintAD = JasperFillManager.fillReport( emptyReport, hashMap1,new JREmptyDataSource());
				}//end of else
				 
				if(ttkReportDataSourcePO.getResultData().next())
				{
					ttkReportDataSourcePO.getResultData().beforeFirst();
					jasperPrintPO = JasperFillManager.fillReport( jasperReportPO, hashMap1, ttkReportDataSourcePO);					 
				}//end of if(ttkReportDataSource.getResultData().next()))
				else
				{
				 jasperPrintPO = JasperFillManager.fillReport( emptyReport, hashMap1,new JREmptyDataSource());
				}//end of else
				generateReport1(jasperPrintAD,jasperPrintPO,destFileName);
				filesShrtFullPath.add(new FileInputStream(destFileName));
				FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
				floatAccountManagerObject.generateMail(strConcatenatedSeqID,TTKCommon.getUserSeqId(request));
			 //request.getSession().setAttribute("boas",boas);
			}
         	String[] srcFiles = strArray.toArray(new String[strArray.size()]);
         	try {
	             
	            // create byte buffer
	        	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            ZipOutputStream zos = new ZipOutputStream(baos);
	            for (int i=0; i < srcFiles.length; i++) {
	                 
	                File srcFile = new File(srcFiles[i]);
	                FileInputStream fis = new FileInputStream(srcFile);
	                BufferedInputStream bis = new BufferedInputStream(fis); 
	                byte bytes[] = new byte[(int)srcFile.length()]; 
	                // begin writing a new ZIP entry, positions the stream to the start of the entry data
	                zos.putNextEntry(new ZipEntry(srcFile.getName()));
	                int length;
	               /* while ((length = fis.read(buffer)) > 0) {
	                    zos.write(buffer, 0, length);
	                }*/
	                int bytesRead;  
		            while ((bytesRead = bis.read(bytes)) != -1)  
		            {  
		              zos.write(bytes, 0, bytesRead);  
		            }  
	                zos.closeEntry();
	                // close the InputStream
	                fis.close();
	                bis.close();  
		            fis.close();  
		    	                 
	            }
	             zos.closeEntry();  
	             zos.flush();  
	             baos.flush();  
	             zos.close();  
	             baos.close();  
	             response.setContentType("application/zip" );  
	             response.setHeader("Content-Disposition","attachment;filename=\" CignaLetters.zip\"");  
	             OutputStream op = response.getOutputStream();  
	             op.write(baos.toByteArray());  
	             op.flush();  
	           
	            // close the ZipOutputStream
	            //zos.close();       	            
	             
	        }
	        catch (IOException ioe) {
	            System.out.println("Error creating zip file: " + ioe);
	        }
	        
        	 return (mapping.findForward(strAddress));
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request,mapping,new TTKException(exp,strAddressExp));
		 }//end of catch(Exception exp)
	 }//end of doGenerateCignaApprovalLtr(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	//ended
	 
	//added for Cigna

	 /**
	  * This method is used to generate the report.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	
	 //end
	  public ByteArrayOutputStream generateReport(JasperPrint jasperPrint1, JasperPrint jasperPrint2) throws JRException {
		  //throw the JasperPrint Objects in a list
		  java.util.List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		  jasperPrintList.add(jasperPrint1);
		  jasperPrintList.add(jasperPrint2);


		  ByteArrayOutputStream baos = new ByteArrayOutputStream();     
		  JRPdfExporter exporter = new JRPdfExporter();     
		  //Add the list as a Parameter
		  exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		  //this will make a bookmark in the exported PDF for each of the reports
		  exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
		  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);       
		  exporter.exportReport();      
		  //return baos.toByteArray();
		  return baos;
		}

	 
	 public String generateReport1(JasperPrint jasperPrint1, JasperPrint jasperPrint2,String strPdfFile) throws JRException {
		  //throw the JasperPrint Objects in a list
		  java.util.List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		  jasperPrintList.add(jasperPrint1);
		  jasperPrintList.add(jasperPrint2);

		  ByteArrayOutputStream baos = new ByteArrayOutputStream();     
		  JRPdfExporter exporter = new JRPdfExporter();     
		  //Add the list as a Parameter
		  exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		  //this will make a bookmark in the exported PDF for each of the reports
		//  exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
		  try {
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, new FileOutputStream(new File(strPdfFile)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  //exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);       
		  exporter.exportReport();      
		  //return baos.toByteArray();
		  return strPdfFile;
		}
	 //ended	
	 
	 //ended
	 
	 /**
	  * This method is used to generate the report.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doGenerateEftComputationSheet(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	 {
		 try
		 {
			 setLinks(request);
			 log.debug("Inside the doGenerateComputationSheet method of AddressSearchAction");
			 String strPaymentSeqID = getEFTConcatenatedSeqID(request,(TableData)request.getSession().getAttribute("tableData"));
			 String strclaimNos=getConcatenatedClmSettelNo(request,(TableData)request.getSession().getAttribute("tableData"));
		     String strLabel="/AddressSearchAction.do?mode=doGenerateEftComputation&parameter="+strPaymentSeqID+"&ClaimSettelList="+strclaimNos;
			 String strConcatenatedSeqID =strPaymentSeqID;
				 //request.getParameter("parameter");
			 String strConcatenatedClaimSettelNo =strclaimNos;
				 //request.getParameter("ClaimSettelList");
			String strConcatenatedClaimSettelNo1[]=strConcatenatedClaimSettelNo.split("\\|");
			String strConcatenatedSeqID1[]= strConcatenatedSeqID.split("\\|");
			String strChOpt[]=request.getParameterValues("chkopt");
			FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
			
			 TableData tableData=(TableData)request.getSession().getAttribute("tableData");
			for(int  i=0;i<strConcatenatedSeqID1.length;i++)
			{
				
				strConcatenatedSeqID=strConcatenatedSeqID1[i];
				strConcatenatedClaimSettelNo=strConcatenatedClaimSettelNo1[i];
				
				 String destFileName=TTKPropertiesReader.getPropertyValue("computaiondir")+strConcatenatedClaimSettelNo+".pdf";
			     strConcatenatedSeqID="|"+strConcatenatedSeqID1[i]+"|".trim();
			
			 log.info("strConcatenatedSeqID VALUE" + strConcatenatedSeqID);
			 DynaActionForm frmAddressLabel = (DynaActionForm)form;
			 JasperReport jasperReport, jasperSubReport, emptyReport;
			 JasperPrint jasperPrint;
			 TTKReportDataSource ttkReportDataSource= null;
			 String jrxmlfile="generalreports/FinanceMediClaimComputation.jrxml";
			 ttkReportDataSource = new TTKReportDataSource("FinMediClaimCom",strConcatenatedSeqID);
			 String cliamNo=(String)frmAddressLabel.get("sClaimSettleNumber");
			 log.info("file path for chacking"+TTKPropertiesReader.getPropertyValue("computaiondir"));
			 log.info("destination file in jboss"+destFileName);
			
			 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
			 jasperSubReport = JasperCompileManager.compileReport("generalreports/MediClaimSub.jrxml");
			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			 HashMap<String, Object> hashMap = new HashMap<String, Object>();
			 ByteArrayOutputStream boas=new ByteArrayOutputStream();
			 hashMap.put("MyDataSource",new TTKReportDataSource("FinMediClaimComLineItems",strConcatenatedSeqID));
			 hashMap.put("MediClaimSubReport",jasperSubReport);
			 
			 if(ttkReportDataSource.getResultData().next())
			 {
				 ttkReportDataSource.getResultData().beforeFirst();
				 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap, ttkReportDataSource);					 
			 }//end of if(ttkReportDataSource.getResultData().next()))
			 else
			 {
				 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
			 }//end of else
			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
             //JasperExportManager.exportReportToPdfFile(jasperPrint, "D://sample112.pdf");
			 
             JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
			 //TTKCommon.getUserID(request);
             floatAccountManagerObject.generateMail(strConcatenatedSeqID,TTKCommon.getUserSeqId(request));
           
			// request.getSession().setAttribute("boas",boas);
			}
			// request.setAttribute("openreport",strLabel);
			 return (mapping.findForward(strAddress));
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request,mapping,new TTKException(exp,strAddressExp));
		 }//end of catch(Exception exp)
	 }//end of doGenerateComputationSheet(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	 
	  
	 /**
	  * This method is used to print the check.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 /*public ActionForward doGenerateEftComputation(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	 {
		 try
		 {
			 setLinks(request);
			 log.info("doGenerate EftComputation method");
			 String strConcatenatedSeqID = request.getParameter("parameter");
			 String strConcatenatedClaimSettelNo = request.getParameter("ClaimSettelList");
			String strConcatenatedClaimSettelNo1[]=strConcatenatedClaimSettelNo.split("\\|");
			String strConcatenatedSeqID1[]= strConcatenatedSeqID.split("\\|");
			String strChOpt[]=request.getParameterValues("chkopt");
			FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
			
			 TableData tableData=(TableData)request.getSession().getAttribute("tableData");
			for(int  i=0;i<strConcatenatedSeqID1.length;i++)
			{
				
				strConcatenatedSeqID=strConcatenatedSeqID1[i];
				strConcatenatedClaimSettelNo=strConcatenatedClaimSettelNo1[i];
				
				 String destFileName=TTKPropertiesReader.getPropertyValue("computaiondir")+strConcatenatedClaimSettelNo+".pdf";
			     strConcatenatedSeqID="|"+strConcatenatedSeqID1[i]+"|".trim();
			
			 log.info("strConcatenatedSeqID VALUE" + strConcatenatedSeqID);
			 DynaActionForm frmAddressLabel = (DynaActionForm)form;
			 JasperReport jasperReport, jasperSubReport, emptyReport;
			 JasperPrint jasperPrint;
			 TTKReportDataSource ttkReportDataSource= null;
			 String jrxmlfile="generalreports/FinanceMediClaimComputation.jrxml";
			 ttkReportDataSource = new TTKReportDataSource("FinMediClaimCom",strConcatenatedSeqID);
			 String cliamNo=(String)frmAddressLabel.get("sClaimSettleNumber");
			 log.info("file path for chacking"+TTKPropertiesReader.getPropertyValue("computaiondir"));
			 log.info("destination file in jboss"+destFileName);
			
			 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
			 jasperSubReport = JasperCompileManager.compileReport("generalreports/MediClaimSub.jrxml");
			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			 HashMap<String, Object> hashMap = new HashMap<String, Object>();
			 ByteArrayOutputStream boas=new ByteArrayOutputStream();
			 hashMap.put("MyDataSource",new TTKReportDataSource("FinMediClaimComLineItems",strConcatenatedSeqID));
			 hashMap.put("MediClaimSubReport",jasperSubReport);
			 
			 if(ttkReportDataSource.getResultData().next())
			 {
				 ttkReportDataSource.getResultData().beforeFirst();
				 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap, ttkReportDataSource);					 
			 }//end of if(ttkReportDataSource.getResultData().next()))
			 else
			 {
				 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
			 }//end of else
			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
             //JasperExportManager.exportReportToPdfFile(jasperPrint, "D://sample112.pdf");
             JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
             //TTKCommon.getUserID(request);
             floatAccountManagerObject.generateMail(strConcatenatedSeqID,TTKCommon.getUserSeqId(request));
           
			 request.getSession().setAttribute("boas",boas);
			}
			 return (mapping.findForward(strReportdisplay));
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request,mapping,new TTKException(exp,strAddressExp));
		 }//end of catch(Exception exp)
	 }//end of doGenerateComputation(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
*/	 
	 //end changes for cr koc1105
	 /**
	  * This method is used to print the check.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doGenerateComputation(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	 {
		 try
		 {
			 setLinks(request);
			 log.debug("Inside AddressSearchAction doGenerateComputation");
			 String strConcatenatedSeqID = request.getParameter("parameter");
			 log.info("strConcatenatedSeqID " + strConcatenatedSeqID);
			 JasperReport jasperReport, jasperSubReport, emptyReport;
			 JasperPrint jasperPrint;
			 TTKReportDataSource ttkReportDataSource= null;
			 String jrxmlfile="generalreports/FinanceMediClaimComputation.jrxml";
			 ttkReportDataSource = new TTKReportDataSource("FinMediClaimCom",strConcatenatedSeqID);
			 
			 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
			 jasperSubReport = JasperCompileManager.compileReport("generalreports/MediClaimSub.jrxml");
			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			 HashMap<String, Object> hashMap = new HashMap<String, Object>();
			 ByteArrayOutputStream boas=new ByteArrayOutputStream();
			 hashMap.put("MyDataSource",new TTKReportDataSource("FinMediClaimComLineItems",strConcatenatedSeqID));
			 hashMap.put("MediClaimSubReport",jasperSubReport);
			 
			 if(ttkReportDataSource.getResultData().next())
			 {
				 ttkReportDataSource.getResultData().beforeFirst();
				 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap, ttkReportDataSource);					 
			 }//end of if(ttkReportDataSource.getResultData().next()))
			 else
			 {
				 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
			 }//end of else

			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
			 request.getSession().setAttribute("boas",boas);
			 return (mapping.findForward(strReportdisplay));
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request,mapping,new TTKException(exp,strAddressExp));
		 }//end of catch(Exception exp)
	 }//end of doGenerateComputation(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
	 * Returns the FloatAccountManager session object for invoking methods on it.
	 * @return FloatAccountManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private FloatAccountManager getFloatAccountManagerObject() throws TTKException
	{
		FloatAccountManager floatAccountManager = null;
		try
		{
			if(floatAccountManager == null)
			{
				InitialContext ctx = new InitialContext();
				floatAccountManager = (FloatAccountManager) ctx.lookup("java:global/TTKServices/business.ejb3/FloatAccountManagerBean!com.ttk.business.finance.FloatAccountManager");
			}//end if(hospManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strAddressExp);
		}//end of catch
		return floatAccountManager;
	}//end getFloatAccountList()
	
	/**
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmTransSearch formbean which contains the search fields
     * @param request HttpServletRequest
     * @return ArrayList contains search parameters
     * @throws TTKException
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmTransSearch,HttpServletRequest request) 
    {
    	//build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(TTKCommon.getWebBoardId(request));
        alSearchParams.add(frmTransSearch.get("startDate"));
        alSearchParams.add(frmTransSearch.get("endDate"));
        alSearchParams.add(frmTransSearch.get("sClaimSettleNumber"));
      //start changes for cr koc1103 and 1105
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmTransSearch.get("paymethod")));
		//end changes for cr koc1103 and 1105
        alSearchParams.add(TTKCommon.getUserSeqId(request));
        return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmTransSearch,HttpServletRequest request)
    
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
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((ChequeVO)tableData.getRowInfo
																(Integer.parseInt(strChOpt[iCounter]))).getSeqID()));
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		sbfConcatenatedSeqId.append("|");
		return sbfConcatenatedSeqId.toString();
	} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)
	
	//startchanges for cr koc1105
	/**Returns the String of concatenated string of contactClmSettelNo delimeted by '|'.
	 * @param HttpServletRequest
	 * @param TableData
	 * @return String
	 */
	private String getConcatenatedClmSettelNo(HttpServletRequest request,TableData tableData) {
		StringBuffer sbfConcatenatedSeqId=new StringBuffer();
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
															Integer.parseInt(strChOpt[iCounter]))).getClaimSettNo()));
					}//end of if(iCounter==0)
					else
					{
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((ChequeVO)tableData.getRowInfo
						//sbfConcatenatedSeqId.append(String.valueOf(((ChequeVO)tableData.getRowInfo
																(Integer.parseInt(strChOpt[iCounter]))).getClaimSettNo()));
						
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		sbfConcatenatedSeqId.append("|");
		
		return sbfConcatenatedSeqId.toString();
	} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)
	
	/**Returns the String of concatenated string of contact_seq_id delimeted by '|'.
	 * @param HttpServletRequest
	 * @param TableData
	 * @return String
	 */
	private String getEFTConcatenatedSeqID(HttpServletRequest request,TableData tableData) {
		StringBuffer sbfConcatenatedSeqId=new StringBuffer();
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
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((ChequeVO)tableData.getRowInfo
						//sbfConcatenatedSeqId.append(String.valueOf(((ChequeVO)tableData.getRowInfo
																(Integer.parseInt(strChOpt[iCounter]))).getSeqID()));
						
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		sbfConcatenatedSeqId.append("|");
		
		return sbfConcatenatedSeqId.toString();
	} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)
	//end changes for cr koc1105

}
