/**
 * @ (#) 1352 nov 2013 
 * Project       : TTK HealthCare Services
 * File          : EmployeeUploadFileList.java
 * Reason        :File Upload console in Employee Login
 */
package com.ttk.action.onlineforms;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.naming.InitialContext;
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
import com.ttk.action.tree.TreeData;
import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.BatchVO;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.OnlineAccessPolicyVO;
import com.ttk.dto.onlineforms.EmployeeFileUplodedVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import formdef.plugin.util.FormUtils;

/**
 * This action class used for searching of card printing batches.
 * This class also provides option for creating batches, generate label, print card, cancel batch.
 */

public class EmployeeUploadFileList extends TTKAction {

	private  Logger log = Logger.getLogger( EmployeeUploadFileList.class ); // Getting Logger for this Class file
	private static final String strFileDownloadList="filedownloadlist";
	//	Modes
	private static final String strForward="Forward";
	private static final String strBackward="Backward";
	private static final String strReportdisplay="reportdisplay";
	private static final String strMemberDetails="closeUploadFile";
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
			log.debug("Inside EmployeeUploadFileList  doSearch");
			setLinks(request);
			String strComma="";
			String filedownloadbatch = "";
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			DynaActionForm frmUploadedFiles=(DynaActionForm)form;
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManager();
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			StringBuffer strFileDownloadPath = new StringBuffer();
			ArrayList alfileDownloadList = null;
			TableData tableData=null;
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData");
			
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if((request.getSession()).getAttribute("tableFileUpload") != null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableFileUpload");
			}//end of if((request.getSession()).getAttribute("tableFileDownload") != null)
			else
			{
				tableData = new TableData();
			}//end of else
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
					return (mapping.findForward("uploadedfilelist"));					
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{	
				OnlineAccessPolicyVO onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
				MemberVO memberVO=(MemberVO)treeData.getSelectedObject((String)request.getParameter("selectedroot"),null);
				
				frmUploadedFiles.set("policyGroupSeqId",memberVO.getPolicyGroupSeqID());
			//create the required grid table
				tableData.createTableInfo("FileUploadedTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
				frmUploadedFiles.set("policyGroupSeqId",memberVO.getPolicyGroupSeqID());
			}//end of else
				alfileDownloadList= onlineAccessManagerObject.getEmployeeFilesList(tableData.getSearchData());
				tableData.setData(alfileDownloadList, "search");
				frmUploadedFiles.set("FileUploadPath",strFileDownloadPath.toString());
				tableData.setData(alfileDownloadList);
				request.getSession().setAttribute("tableFileUpload",tableData);
				request.getSession().setAttribute("frmUploadedFiles",frmUploadedFiles);
			//finally return to the grid screen
			return mapping.findForward("uploadedfilelist");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"online"));
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
    		log.debug("Inside the EmployeeUploadFileList doViewFile  ");
    		setLinks(request);
    		String fileuploadbatch = "";
    		String fileDestination="";
    	
    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		fileuploadbatch = TTKPropertiesReader.getPropertyValue("attachdestination")+userSecurityProfile.getGroupID()+"/"+userSecurityProfile.getTPAEnrolNbr()+"/";
			TableData tableData=null;
    		if((request.getSession()).getAttribute("tableFileUpload") != null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableFileUpload");
			
			}//end of if((request.getSession()).getAttribute("tableFileDownload") != null)
			else
			{
				tableData = new TableData();
			}//end of else
    		EmployeeFileUplodedVO employeeFileUplodedVO  = ((EmployeeFileUplodedVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum"))));
    		String strBatchFileName=employeeFileUplodedVO.getFileName();
    		File file = null;  		
    		fileDestination=fileuploadbatch+strBatchFileName;  
    		log.info("fileDestination::::::::"+fileDestination);
    		file = new File(fileDestination);
    		if(file.exists())
    		{
    		request.setAttribute("fileName",fileDestination);
    		}//end of if(file.exists())
    		return mapping.findForward("uploadedfilelist");//finally return to the grid screen
	   	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"online"));
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
    		log.debug("Inside EmployeeUploadFileList doViewFilePdf");
    		setLinks(request);
    	    return (mapping.findForward(strReportdisplay));
			
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"online"));
		}//end of catch(Exception exp)
    }//end of doViewFilePdf(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
	
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
			log.debug("Inside the doClose method of EmployeeUploadFileList");
			setOnlineLinks(request);    	
		
			return mapping.findForward(strMemberDetails);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,""));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside the doForward method of EmployeeUploadFileList");
			setLinks(request);
			String strComma="";
			String filedownloadbatch = "";
			DynaActionForm frmUploadedFiles=(DynaActionForm)form;
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManager();
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData");
	    		StringBuffer strFileDownloadPath = new StringBuffer();
			ArrayList alfileDownloadList = null;
			TableData tableData=null;
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if((request.getSession()).getAttribute("tableFileUpload") != null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableFileUpload");
			}//end of if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			else
			{
				tableData = new TableData();
			}//end of else
			tableData.modifySearchData(strForward);//modify the search data
		
			alfileDownloadList= onlineAccessManagerObject.getEmployeeFilesList(tableData.getSearchData());
			tableData.setData(alfileDownloadList, strForward);
			frmUploadedFiles.set("FileUploadPath",strFileDownloadPath.toString());
			request.getSession().setAttribute("tableFileUpload",tableData);
			request.getSession().setAttribute("frmUploadedFiles",frmUploadedFiles);
		//finally return to the grid screen
		return mapping.findForward("uploadedfilelist");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
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
			log.debug("Inside the doBackward method of EmployeeUploadFileList");
			setLinks(request);
			DynaActionForm frmUploadedFiles=(DynaActionForm)form;
			String strComma="";
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManager();
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData");
			StringBuffer strFileDownloadPath = new StringBuffer();
			ArrayList alfileDownloadList = null;
			TableData tableData=null;
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if((request.getSession()).getAttribute("tableFileUpload") != null)
			{
			tableData= (TableData)(request.getSession()).getAttribute("tableFileUpload");
			}//end of if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			else
			{
			tableData = new TableData();
			}//end of else
			tableData.modifySearchData(strBackward);//modify the search data
			alfileDownloadList= onlineAccessManagerObject.getEmployeeFilesList(tableData.getSearchData());
			tableData.setData(alfileDownloadList, strBackward);
			frmUploadedFiles.set("FileUploadPath",strFileDownloadPath.toString());
			tableData.setData(alfileDownloadList);
			request.getSession().setAttribute("tableFileUpload",tableData);
			request.getSession().setAttribute("frmUploadedFiles",frmUploadedFiles);
			//finally return to the grid screen
			return mapping.findForward("uploadedfilelist");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param frmUploadedFiles DynaActionForm
	 * @param request HttpServletRequest
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmUploadedFiles,HttpServletRequest request)throws TTKException
	{
	log.debug("Inside the populateSearchCriteria method of EmployeeUploadFileList");
	//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.checkNull(frmUploadedFiles.get("policyGroupSeqId")));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm searchFeedbackForm,HttpServletRequest request)
	
	/**
	 * Returns the OnlineAccessManager session object for invoking methods on it.
	 * @return OnlineAccessManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private OnlineAccessManager getOnlineAccessManager() throws TTKException
	{
		OnlineAccessManager onlineAccessManager = null;
		try
		{
			if(onlineAccessManager == null)
			{
				InitialContext ctx = new InitialContext();
				onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
			}//end ofif(onlineAccessManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "");
		}//end of catch
		return onlineAccessManager;
	}//end getOnlineAccessManager()
	
}//end of EmployeeUploadFileList