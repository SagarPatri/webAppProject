/**
 * @ (#) SmartHealthXmlDownloadAction.java NOV 14, 2016
 * Project       : Project X
 * File          : SmartHealthXmlDownloadAction.java
 * Author        : Nagababu Kamadi
 * Company       : RCS
 * Date Created  : NOV 14, 2016
 * @author       : Nagababu Kamadi
 *
 * Modified by   :  
 * Modified date : 
 * Reason        :
 */

package com.ttk.action.support;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.support.SupportManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.DhpoWebServiceVO;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.preauth.PreAuthVO;

/**
 * This action class used for Smart Health Xml files DownloadAction. * 
 */

public class SmartHealthXmlDownloadAction extends TTKAction {

	private  Logger log = Logger.getLogger( SmartHealthXmlDownloadAction.class ); // Getting Logger for this Class file
	
	private static final String strSearchClaimXmlFiles="searchClaimXmlFiles";
	private static final String strSearchPreAuthXmlFiles="searchPreAuthXmlFiles";
	private static final String strXmlDownError="SmartHealthDownload";
	private  static final String strForward="Forward";
	private  static final String strBackward="Backward";
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
			log.debug("Inside SmartHealthXmlDownloadAction doDefault");
			setLinks(request);
			
			TableData tableData=TTKCommon.getTableData(request);
			DynaActionForm frmSmartHealthXml=((DynaActionForm)form);
			String authMode="PAT";

			if("Claim Xml Download".equals(TTKCommon.getActiveTab(request))){
				authMode="CLM";
				}
			//DynaActionForm frmIntimationList=(DynaActionForm)form;
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("SmartHealthXmlTable",new ArrayList());
			
			if("PAT".equals(authMode)){
				((Column)((ArrayList)tableData.getTitle()).get(6)).setVisibility(false);
				((Column)((ArrayList)tableData.getTitle()).get(7)).setVisibility(false);
				}
			
			
			request.getSession().setAttribute("authMode",authMode);
			request.getSession().setAttribute("tableData",tableData);
			//clear the dynaform if visting from left links for the first time
			
			frmSmartHealthXml.initialize(mapping);//reset the form data
			frmSmartHealthXml.set("downloadedYN", "N");
			if("CLM".equals(authMode)){
			return this.getForward(strSearchClaimXmlFiles,mapping,request);
			}else{
				return this.getForward(strSearchPreAuthXmlFiles,mapping,request);
			}

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
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
																HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside SmartHealthXmlDownloadAction  doSearch");
			SupportManager supportManager = this.getSupportManagerObject();
			
			ArrayList alXmlList = null;
			
			TableData tableData=TTKCommon.getTableData(request);
			String authMode=(String)request.getSession().getAttribute("authMode");
			
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))

			//else get the dynaform data from session
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
					if("CLM".equals(authMode)){
						return this.getForward(strSearchClaimXmlFiles,mapping,request);
						}else{
							return this.getForward(strSearchPreAuthXmlFiles,mapping,request);
						}
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("SmartHealthXmlTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
				//sorting based on investSeqID in descending order
				
			}//end of else
			alXmlList= supportManager.getSmartHealthXmlList(tableData.getSearchData());
			tableData.setData(alXmlList, "search");
			//set the table data object to session
			if("PAT".equals(authMode)){
				((Column)((ArrayList)tableData.getTitle()).get(6)).setVisibility(false);
				((Column)((ArrayList)tableData.getTitle()).get(7)).setVisibility(false);
				}
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			if("CLM".equals(authMode)){
				return this.getForward(strSearchClaimXmlFiles,mapping,request);
				}else{
					return this.getForward(strSearchPreAuthXmlFiles,mapping,request);
				}

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strXmlDownError));
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
															HttpServletResponse response) throws Exception {

		try
		{
			setLinks(request);
			log.debug("Inside SmartHealthXmlDownloadAction doForward");
			SupportManager supportManager = this.getSupportManagerObject();
			String authMode=(String)request.getSession().getAttribute("authMode");
			ArrayList alXmlList = null;
			TableData tableData=TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			tableData.modifySearchData(strForward);//modify the search data
			alXmlList = supportManager.getSmartHealthXmlList(tableData.getSearchData());
			tableData.setData(alXmlList, strForward);//set the table data
			
			if("PAT".equals(authMode)){
				((Column)((ArrayList)tableData.getTitle()).get(6)).setVisibility(false);
				((Column)((ArrayList)tableData.getTitle()).get(7)).setVisibility(false);
				}
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			if("CLM".equals(authMode)){
				return this.getForward(strSearchClaimXmlFiles,mapping,request);
				}else{
					return this.getForward(strSearchPreAuthXmlFiles,mapping,request);
				}
			}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strXmlDownError));
		}//end of catch(Exception exp)
	}//end of doInvestigationList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
																HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside SmartHealthXmlDownloadAction  doBackward");
			SupportManager supportManager = this.getSupportManagerObject();
			String authMode=(String)request.getSession().getAttribute("authMode");
			ArrayList alXmlList = null;
			TableData tableData=TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			tableData.modifySearchData(strBackward);//modify the search data
			alXmlList = supportManager.getSmartHealthXmlList(tableData.getSearchData());
			tableData.setData(alXmlList, strBackward);//set the table data
			if("PAT".equals(authMode)){
				((Column)((ArrayList)tableData.getTitle()).get(6)).setVisibility(false);
				((Column)((ArrayList)tableData.getTitle()).get(7)).setVisibility(false);
				}
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			if("CLM".equals(authMode)){
				return this.getForward(strSearchClaimXmlFiles,mapping,request);
				}else{
					return this.getForward(strSearchPreAuthXmlFiles,mapping,request);
				}
			}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strXmlDownError));
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
	public ActionForward downloadXmlFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CardPrintingAction downloadXmlFile");
			setLinks(request);
			SupportManager supportManager = this.getSupportManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			  PrintWriter out=response.getWriter();
				String authMode=(String)request.getSession().getAttribute("authMode");
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				DhpoWebServiceVO webServiceVO=(DhpoWebServiceVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				
				DhpoWebServiceVO serviceVO = supportManager.downLoadSmartHealthXmlFiles(webServiceVO.getFileSeqID(),request.getParameter("downloadMode"),authMode);
				
				if(serviceVO!=null){
					 
						if(serviceVO.getXmlFileReader()!=null){
					
					  response.setContentType("application/txt");
				      response.setHeader("Content-Disposition", "attachment;filename="+serviceVO.getFileName());
					  
				       Reader reader=serviceVO.getXmlFileReader();
				       
				      int ch;
                      while ((ch = reader.read()) != -1) out.write(ch);
                      
                      reader.close();
                      out.flush(); 
                      out.close();
	                }//if(serviceVO.getXmlFileReader()!=null){
						
				}//if(serviceVO!=null){
				
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return null;
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strXmlDownError));
		}//end of catch(Exception exp)
	}//end of doViewXmlFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward bulkDownloadXmlFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
    try{
    log.debug("Inside CardPrintingAction downloadBulkXmlFile");
    setLinks(request);
    SupportManager supportManager = this.getSupportManagerObject();
    
    
    String[] strChk = request.getParameterValues("chkopt");
	TableData tableData1 = (TableData)request.getSession().getAttribute("tableData");
	StringBuilder fileseqId=new StringBuilder();
	ArrayList<DhpoWebServiceVO> allDhpoWebServiceVO=new ArrayList<>();
	 // PrintWriter out=response.getWriter();
	if(strChk!=null&&strChk.length!=0)
	{ 
		
    //..code to add URLs to the list
    byte[] buf = new byte[2048];
    // Create the ZIP file
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ZipOutputStream out = new ZipOutputStream(baos);
    File filedirecory=new File(TTKPropertiesReader.getPropertyValue("BulkDownloadTempFile"));
	   filedirecory.mkdirs();
	   File file=new File(filedirecory.getAbsolutePath()+"/tempfile.xml");
	   
	   for(int i=0; i<strChk.length;i++)
		{
		   DhpoWebServiceVO webServiceVO=(DhpoWebServiceVO)tableData1.getData().get(Integer.parseInt(strChk[i]));
		   fileseqId.append(webServiceVO.getFileSeqID()+"|");
		}
	   String authMode=(String)request.getSession().getAttribute("authMode");
		  allDhpoWebServiceVO= supportManager.bulkDownLoadSmartHealthXmlFiles(fileseqId,request.getParameter("downloadMode"),authMode);
		for(int i=0; i<allDhpoWebServiceVO.size();i++)
		{
	 // DhpoWebServiceVO webServiceVO=(DhpoWebServiceVO)tableData1.getData().get(Integer.parseInt(strChk[i]));
			 DhpoWebServiceVO serviceVO=allDhpoWebServiceVO.get(i);
	  if(serviceVO!=null){

	       if(serviceVO.getXmlFileReader()!=null){
	    	   file.createNewFile();
	    	  // System.out.println("file path======"+file.getAbsolutePath());
	    	   FileOutputStream fout=new FileOutputStream(file);
	    	   Reader reader=serviceVO.getXmlFileReader();
	    	   int ch;
	    	   while((ch=reader.read())!=-1) fout.write(ch);fout.close();
	    	   FileInputStream fis = new FileInputStream(file.getAbsoluteFile());
	    	   file.delete();
	   	    BufferedInputStream bis = new BufferedInputStream(fis);
	   	    // Add ZIP entry to output stream.
	   	 String entryname = serviceVO.getFileName();
	   	// System.out.println("entry name=="+entryname);
	   	    out.putNextEntry(new ZipEntry(entryname));
	   	    int bytesRead;
	   	    while ((bytesRead = bis.read(buf)) != -1) {
	   	    out.write(buf, 0, bytesRead);
	   	    }
	   	    out.closeEntry();
	   	    bis.close();
	   	    fis.close();
	          
	             }//if(serviceVO.getXmlFileReader()!=null){
	 
	             }//if(serviceVO!=null)
		}
		file.delete();
		out.flush();
	    baos.flush();
	    out.close();
	    baos.close();
	    ServletOutputStream sos = response.getOutputStream();
	    response.setContentType("application/zip");
	    response.setHeader("Content-Disposition", "attachment; filename=\"AllXmlFiles.ZIP\"");
	    sos.write(baos.toByteArray());
	    out.flush();
	    out.close();
	    sos.flush();
	}
    
         return null;
        }//end of try
       catch(TTKException expTTK)
        {
       return this.processExceptions(request, mapping, expTTK);
         }//end of catch(TTKException expTTK)
        catch(Exception exp)
         {
        return this.processExceptions(request, mapping, new TTKException(exp,strXmlDownError));
         }//end of catch(Exception exp)
       }//end of doViewXmlFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	
	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param frmCardPrinting DynaActionForm
	 * @param request HttpServletRequest
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSmartHealthXml,HttpServletRequest request)
	{
		String authMode=(String)request.getSession().getAttribute("authMode");
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add((String)frmSmartHealthXml.get("downloadedYN"));
		alSearchParams.add((String)frmSmartHealthXml.get("xmlFilesRecievedFrom"));
		alSearchParams.add((String)frmSmartHealthXml.get("xmlFilesRecievedTo"));
		alSearchParams.add((String)frmSmartHealthXml.get("fileName"));
		alSearchParams.add((String)frmSmartHealthXml.get("fileID"));	
		alSearchParams.add((String)frmSmartHealthXml.get("DHPOTransactionDateFrom"));		
		alSearchParams.add((String)frmSmartHealthXml.get("DHPOTransactionDateTo"));		
		alSearchParams.add((String)frmSmartHealthXml.get("providerId"));	
		alSearchParams.add(authMode);	
		
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmInvestigation,HttpServletRequest request)

	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private SupportManager getSupportManagerObject() throws TTKException
	{
		SupportManager supportManager = null;
		try
		{
			if(supportManager == null)
			{
				InitialContext ctx = new InitialContext();
				supportManager = (SupportManager) ctx.lookup("java:global/TTKServices/business.ejb3/SupportManagerBean!com.ttk.business.support.SupportManager");
			}//end of if(supportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strXmlDownError);
		}//end of catch
		return supportManager;
	}//end of getsupportManagerObject()
}//end of CardPrintingAction