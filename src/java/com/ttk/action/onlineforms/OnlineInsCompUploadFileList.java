 /**
  * @ (#) OnlineInsCompUploadFileList.java January 28, 2014
  * Project      : Vidal Health TPA
  * File         : OnlineInsCompUploadFileList.java
  * Author       : Thirumalai K P
  * Company      : Span Systems Corporation
  * Date Created : January 28, 2014
  *
  * @author       :Thirumalai K P
  * Modified by   :
  * Modified date :
  * Reason        : Viewing the File upload in insurance company login -- ins file upload
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
import com.ttk.dto.enrollment.OnlineAccessPolicyVO;
import com.ttk.dto.onlineforms.InsFileUploadVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import formdef.plugin.util.FormUtils;

/**
 * This action class used for searching of card printing batches.
 * This class also provides option for creating batches, generate label, print card, cancel batch.
 */

public class OnlineInsCompUploadFileList extends TTKAction {

	private  Logger log = Logger.getLogger( OnlineInsCompUploadFileList.class ); // Getting Logger for this Class file

	private static final String strFileDownloadList = "filedownloadlist";
	private static final String strForward = "Forward";
	private static final String strBackward = "Backward";
	private static final String strReportdisplay = "reportdisplay";
	private static final String strInsCompUpload = "closeUploadFile";

	private static final String strInsCompFileUpload = "File Upload";

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
			log.debug("Inside OnlineInsCompUploadFileList  doSearch");
			setLinks(request);
			UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			DynaActionForm frmInsUploadedFiles = (DynaActionForm)form;
			OnlineAccessManager onlineAccessManagerObject = this.getOnlineAccessManager();
			InsFileUploadVO insFileUploadVO = new InsFileUploadVO();
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			StringBuffer strFileDownloadPath = new StringBuffer();
			ArrayList alinsFileDownloadList = null;
			TableData tableData = null;
			if((request.getSession()).getAttribute("tableInsCompFileUpload") != null)
			{
				tableData = (TableData)(request.getSession()).getAttribute("tableInsCompFileUpload");
			}//end of if((request.getSession()).getAttribute("tableInsCompFileUpload") != null)
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
					return (mapping.findForward("insuploadedfilelist"));					
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{	
				insFileUploadVO.setLoginUserId(userSecurityProfile.getUSER_ID());

				frmInsUploadedFiles.set("loginUserId",insFileUploadVO.getLoginUserId());
				
				//create the required grid table
				tableData.createTableInfo("InsCompFileUploadedTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			
				alinsFileDownloadList = onlineAccessManagerObject.getInsCompUploadFilesList(tableData.getSearchData());
				tableData.setData(alinsFileDownloadList, "search");
				frmInsUploadedFiles.set("InsFileUploadPath",strFileDownloadPath.toString());
				tableData.setData(alinsFileDownloadList);
				request.getSession().setAttribute("tableInsCompFileUpload",tableData);
				request.getSession().setAttribute("frmInsUploadedFiles",frmInsUploadedFiles);
			//finally return to the grid screen
			return mapping.findForward("insuploadedfilelist");
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
    		log.debug("Inside the OnlineInsCompUploadFileList doViewFile");
    		setLinks(request);
    		String fileuploadbatch = "";
    		String fileDestination = "";
    	
    		UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		fileuploadbatch = TTKPropertiesReader.getPropertyValue("inscompfileattachement")+userSecurityProfile.getGroupID()+"/";
			TableData tableData = null;
    		if((request.getSession()).getAttribute("tableInsCompFileUpload") != null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableInsCompFileUpload");
			
			}//end of if((request.getSession()).getAttribute("tableFileDownload") != null)
			else
			{
				tableData = new TableData();
			}//end of else
    		InsFileUploadVO insFileUploadVO  = ((InsFileUploadVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum"))));
    		String strBatchFileName = insFileUploadVO.getFileName();
    		File file = null;  		
    		fileDestination = fileuploadbatch+strBatchFileName;   		
    		file = new File(fileDestination);
    		if(file.exists())
    		{
    		request.setAttribute("fileName",fileDestination);
    		}//end of if(file.exists())
    		return mapping.findForward("insuploadedfilelist");//finally return to the grid screen
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
    public ActionForward doFileDownload(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside OnlineInsCompUploadFileList doFileDownload");
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
			setLinks(request);    	
		
			return mapping.findForward(strInsCompUpload);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strInsCompFileUpload));
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
			log.debug("Inside the doForward method of OnlineInsCompUploadFileList");
			setLinks(request);
			DynaActionForm frmInsUploadedFiles = (DynaActionForm)form;
			OnlineAccessManager onlineAccessManagerObject = this.getOnlineAccessManager();
			StringBuffer strFileDownloadPath = new StringBuffer();
			ArrayList alinsFileDownloadList = null;
			TableData tableData = null;
			if((request.getSession()).getAttribute("tableInsCompFileUpload") != null)
			{
				tableData = (TableData)(request.getSession()).getAttribute("tableInsCompFileUpload");
			}//end of if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			else
			{
				tableData = new TableData();
			}//end of else
			tableData.modifySearchData(strForward);//modify the search data
		
			alinsFileDownloadList = onlineAccessManagerObject.getInsCompUploadFilesList(tableData.getSearchData());
			tableData.setData(alinsFileDownloadList, strForward);
			frmInsUploadedFiles.set("InsFileUploadPath",strFileDownloadPath.toString());
			request.getSession().setAttribute("tableInsCompFileUpload",tableData);
			request.getSession().setAttribute("frmInsUploadedFiles",frmInsUploadedFiles);
		//finally return to the grid screen
		return mapping.findForward("insuploadedfilelist");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"online"));
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
			log.debug("Inside the doBackward method of OnlineInsCompUploadFileList");
			setLinks(request);
			DynaActionForm frmInsUploadedFiles = (DynaActionForm)form;
			OnlineAccessManager onlineAccessManagerObject = this.getOnlineAccessManager();
			StringBuffer strFileDownloadPath = new StringBuffer();
			ArrayList alfileDownloadList = null;
			TableData tableData = null;
			if((request.getSession()).getAttribute("tableInsCompFileUpload") != null)
			{
			tableData= (TableData)(request.getSession()).getAttribute("tableInsCompFileUpload");
			}//end of if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			else
			{
			tableData = new TableData();
			}//end of else
			tableData.modifySearchData(strBackward);//modify the search data
			alfileDownloadList = onlineAccessManagerObject.getInsCompUploadFilesList(tableData.getSearchData());
			tableData.setData(alfileDownloadList, strBackward);
			frmInsUploadedFiles.set("InsFileUploadPath",strFileDownloadPath.toString());
			tableData.setData(alfileDownloadList);
			request.getSession().setAttribute("tableInsCompFileUpload",tableData);
			request.getSession().setAttribute("frmInsUploadedFiles",frmInsUploadedFiles);
			//finally return to the grid screen
			return mapping.findForward("insuploadedfilelist");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"online"));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param <InsFileUploadVO>
	 * @param frmInsUploadedFiles DynaActionForm
	 * @param request HttpServletRequest
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmInsUploadedFiles,HttpServletRequest request)throws TTKException
	{
		log.debug("Inside the populateSearchCriteria method of EmployeeUploadFileList");
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.checkNull(frmInsUploadedFiles.get("loginUserId")));
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
			}//end if(onlineAccessManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "");
		}//end of catch
		return onlineAccessManager;
	}//end getOnlineAccessManager()
	
}//end of OnlineInsCompUploadFileList