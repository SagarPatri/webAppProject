/**
 * @ (#) LinkDetailsAction.java Jan 01, 2008
 * Project      : TTK HealthCare Services
 * File         : LinkDetailsAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Jan 01, 2008
 *
 * @author       :  Chandrasekaran J
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 * Modified by   :  
 */
package com.ttk.action.administration;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.WebConfigLinkVO;
import com.ttk.dto.enrollment.PolicyDetailVO;

import formdef.plugin.util.FormUtils;

public class LinkDetailsAction extends TTKAction
{
	
	private static Logger log = Logger.getLogger( LinkDetailsAction.class );
	//for forwarding
	private static final String strLinkDetailsList="linklist";
	private static final String strLinkDetails="linkdetails";
	private static final String strDeleteLink="linkdelete";
	private static final String strBrowseList="browselist";
	private static final String strClose="webconfigdetails";
	//  Exception Message Identifier
	private static final String strWebconfig="webconfig";
	
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
			log.debug("Inside LinkDetailsAction doDefault");
			if(TTKCommon.getWebBoardId(request) == null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.policy.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request) == null)
			DynaActionForm frmLinkDetails= (DynaActionForm) form;			
			TableData tableDataLinkDetails = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			{
				tableDataLinkDetails=(TableData)(request.getSession()).getAttribute("tableDataLinkDetails");
			}//end of if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			else
			{
				tableDataLinkDetails=new TableData();
			}//end of else
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			ArrayList alLinkDetailsList=null;
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strSortID.equals(""))
			{
				tableDataLinkDetails.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableDataLinkDetails.modifySearchData("sort");//modify the search data
			}// end of if(!strSortID.equals(""))
			else
			{
				tableDataLinkDetails.createTableInfo("LinkDetailsTable",null);
				tableDataLinkDetails.setSearchData(this.populateSearchCriteria(request));
				tableDataLinkDetails.modifySearchData("search");
			}// end of else
			alLinkDetailsList=productpolicyObject.getWebConfigLinkList(tableDataLinkDetails.getSearchData());
			tableDataLinkDetails.setData(alLinkDetailsList);
			frmLinkDetails.set("showLinkYN","Y");
			frmLinkDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("tableDataLinkDetails",tableDataLinkDetails);
			request.setAttribute("frmLinkDetails",frmLinkDetails);
			return this.getForward(strLinkDetailsList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strWebconfig));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doSelect(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside LinkDetailsAction doSelect");
			if(TTKCommon.getWebBoardId(request) == null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.policy.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request) == null)
			DynaActionForm frmLinkDetails= null;
			frmLinkDetails = (DynaActionForm) request.getSession().getAttribute("frmLinkDetails");
			if(frmLinkDetails!=null){
				frmLinkDetails.set("path",((DynaActionForm)form).getString("path"));
			}//end of if(request.getSession().getAttribute("frmLinkDetails")!=null)
			else {
				frmLinkDetails = (DynaActionForm)form;
			}//end of else	
			if(((DynaActionForm)form).getString("path")!=null){
				frmLinkDetails.set("path",((DynaActionForm)form).getString("path"));
			}//end of if(((DynaActionForm)form).getString("path")!=null)
			else {
				frmLinkDetails.set("path","");
			}//end of else
			TableData tableDataLinkDetails = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			{
				tableDataLinkDetails=(TableData)(request.getSession()).getAttribute("tableDataLinkDetails");
			}//end of if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			else
			{
				tableDataLinkDetails=new TableData();
			}//end of else
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			ArrayList alLinkDetailsList=null;
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strSortID.equals(""))
			{
				tableDataLinkDetails.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableDataLinkDetails.modifySearchData("sort");//modify the search data
			}// end of if(!strSortID.equals(""))
			else
			{
				tableDataLinkDetails.createTableInfo("LinkDetailsTable",null);
				tableDataLinkDetails.setSearchData(this.populateSearchCriteria(request));
				tableDataLinkDetails.modifySearchData("search");
			}// end of else
			alLinkDetailsList=productpolicyObject.getWebConfigLinkList(tableDataLinkDetails.getSearchData());
			tableDataLinkDetails.setData(alLinkDetailsList);
			frmLinkDetails.set("showLinkYN","Y");
			frmLinkDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("tableDataLinkDetails",tableDataLinkDetails);
			request.setAttribute("frmLinkDetails",frmLinkDetails);
			return this.getForward(strLinkDetailsList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strWebconfig));
		}//end of catch(Exception exp)
	}//end of doSelect(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to add/update the record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside LinkDetailsAction doSave");
			WebConfigLinkVO webConfigLinkVO=null;
			DynaActionForm frmLinkDetails=(DynaActionForm)form;
			ArrayList alLinkDetailsList=null;
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			TableData tableDataLinkDetails = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataLinkDetails")!=null) 
			{
				tableDataLinkDetails=(TableData)(request.getSession()).getAttribute("tableDataLinkDetails");
			}//end of if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			else
			{
				tableDataLinkDetails=new TableData();
			}//end of else
			webConfigLinkVO=(WebConfigLinkVO)FormUtils.getFormValues(frmLinkDetails,this, mapping, request);
			webConfigLinkVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
			webConfigLinkVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			int inUpdate=productpolicyObject.saveWebConfigLinkInfo(webConfigLinkVO);
			if(inUpdate>0)
			{
				if(frmLinkDetails.get("configLinkSeqID")!=null)
				{
					//set the appropriate message
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if(frmLinkDetails.get("configLinkSeqID")!=null)
				else
				{
					//set the appropriate message
					request.setAttribute("updated","message.addedSuccessfully");
				}//end else
				frmLinkDetails.initialize(mapping);
				alLinkDetailsList=productpolicyObject.getWebConfigLinkList(tableDataLinkDetails.getSearchData());
				tableDataLinkDetails.setData(alLinkDetailsList);
				frmLinkDetails.set("showLinkYN","Y");
				frmLinkDetails.set("caption",sbfCaption.toString());
				request.getSession().setAttribute("tableDataLinkDetails",tableDataLinkDetails);
				request.setAttribute("frmLinkDetails",frmLinkDetails);
			}// end of if(iUpdate>0)
			return this.getForward(strLinkDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strWebconfig));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is called from the struts framework.
	 * This method is used to delete a record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside LinkDetailsAction doDelete");
			StringBuffer sbfDeleteId = new StringBuffer();
			ArrayList <Object>alLinkDelete=new ArrayList<Object>();
			DynaActionForm frmLinkDetails=(DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			ArrayList alLinkDetailsList=null;
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			TableData tableDataLinkDetails = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataLinkDetails")!=null) 
			{
				tableDataLinkDetails=(TableData)(request.getSession()).getAttribute("tableDataLinkDetails");
			}//end of if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			else
			{
				tableDataLinkDetails=new TableData();
			}//end of else
			sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().getAttribute("tableDataLinkDetails")));
			alLinkDelete.add(String.valueOf(sbfDeleteId));
			alLinkDelete.add(TTKCommon.getUserSeqId(request));
			frmLinkDetails.initialize(mapping);
			int iCount=productpolicyObject.deleteWebConfigLinkInfo(alLinkDelete);
			log.debug("iCount value is :"+iCount);
			//refresh the data in order to get the new records if any.
			alLinkDetailsList=productpolicyObject.getWebConfigLinkList(tableDataLinkDetails.getSearchData());
			tableDataLinkDetails.setData(alLinkDetailsList);
			frmLinkDetails.set("showLinkYN","Y");
			frmLinkDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("tableDataLinkDetails",tableDataLinkDetails);
			request.setAttribute("frmLinkDetails",frmLinkDetails);
			return this.getForward(strDeleteLink,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strWebconfig));
		}//end of catch(Exception exp)
	}//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doViewLinkDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside LinkDetailsAction doViewLinkDetails");
			WebConfigLinkVO webConfigLinkVO=null;
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			DynaActionForm frmLinkDetails=(DynaActionForm)form;
			//if rownumber is found populate the form object
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				
				webConfigLinkVO=(WebConfigLinkVO)((TableData)request.getSession().getAttribute("tableDataLinkDetails")).
				getData().get(Integer.parseInt(request.getParameter("rownum")));
				log.info("webConfigLinkVO.getConfigLinkSeqID() "+webConfigLinkVO.getConfigLinkSeqID());
				webConfigLinkVO=productpolicyObject.getWebConfigLinkDetail(webConfigLinkVO.getConfigLinkSeqID());
				frmLinkDetails = (DynaActionForm)FormUtils.setFormValues("frmLinkDetails",webConfigLinkVO,
						this,mapping,request);
			}// end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			else
			{
				frmLinkDetails.initialize(mapping);
			}// end of else
			//frmLinkDetails.set("showLinkYN","Y");
			frmLinkDetails.set("caption",sbfCaption.toString());
			request.setAttribute("frmLinkDetails",frmLinkDetails);
			return this.getForward(strLinkDetailsList,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strWebconfig));
		}//end of catch(Exception exp)
	}//end of doViewLinkDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse
	//response)
	
	/**
	 * This method is used to reload the screen when the reset button is pressed.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doReset method of LinkDetailsAction");
			setLinks(request);
			WebConfigLinkVO webConfigLinkVO=null;
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			DynaActionForm frmLinkDetails=(DynaActionForm)form;
			if(frmLinkDetails.get("configLinkSeqID")!= null && (!frmLinkDetails.get("configLinkSeqID").equals("")))
			{
				webConfigLinkVO=productpolicyObject.getWebConfigLinkDetail(TTKCommon.getLong(frmLinkDetails.getString("configLinkSeqID")));
				frmLinkDetails = (DynaActionForm)FormUtils.setFormValues("frmLinkDetails",webConfigLinkVO,
						this,mapping,request);
			}// end of if(frmLinkDetails.get("configLinkSeqID")!= null && (!frmLinkDetails.get("configLinkSeqID").equals("")))
			else if(frmLinkDetails.get("configLinkSeqID")== null || frmLinkDetails.get("configLinkSeqID").equals(""))
			{
				frmLinkDetails.initialize(mapping);
			}// end of else	if(frmLinkDetails.get("configLinkSeqID")== null || frmLinkDetails.get("configLinkSeqID").equals(""))
			frmLinkDetails.set("showLinkYN","Y");
			frmLinkDetails.set("caption",sbfCaption.toString());
			request.setAttribute("frmLinkDetails",frmLinkDetails);
			return this.getForward(strLinkDetailsList,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strWebconfig));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
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
			log.debug("Inside doClose of LinkDetailsAction");
			return this.getForward(strClose, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strWebconfig));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns a string which contains the | separated sequence id's to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the | separated sequence id's to be delete
	 * @throws TTKException If any run time Excepton occures
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableDataLinkDetails)throws TTKException
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append("|").append(String.valueOf(((WebConfigLinkVO)tableDataLinkDetails.getData().
								get(Integer.parseInt(strChk[i]))).getConfigLinkSeqID()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((WebConfigLinkVO)tableDataLinkDetails.getData().
								get(Integer.parseInt(strChk[i]))).getConfigLinkSeqID().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId=sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return String.valueOf(sbfDeleteId);
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)
	
	/**
     * This method is used to list out the document/image file names from the server based on required path/groupID
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
	public ActionForward doBrowse(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside LinkDetailsAction doBrowse");
			DynaActionForm frmLinkDetails = (DynaActionForm) form; 
			PolicyDetailVO policyDetailVo = (PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
			String strType = frmLinkDetails.getString("linkTypeID");
			String strDefaultDir = TTKPropertiesReader.getPropertyValue("WebLoginDocs")+"/default";
			String strLogoDir = TTKPropertiesReader.getPropertyValue("WebLoginImg");
			String strCompanyName = TTKPropertiesReader.getPropertyValue("WebLoginImg");
			ArrayList<Object> alFilePath = new ArrayList<Object>();
			//in case of links groupid folder should be included in the path
			StringBuffer  strLinks = new StringBuffer();
			strLinks = strLinks.append(TTKPropertiesReader.getPropertyValue("WebLoginDocs"));
			if(policyDetailVo.getGrpID()!=null){
				strLinks = strLinks.append("/"+ policyDetailVo.getGrpID());
			}//end of if(policyDetailVo.getGrpID()!=null){ 
			//for filtering out image
			FilenameFilter imageFilterObj = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					//return (name.endsWith(".gif") || name.endsWith(".jpg"));
					return name.contains(".");
				}//end of accept(File dir, String name)
			};//end of FilenameFilter imageFilterOBj = new FilenameFilter()
			//for filtering out doc and txt files
			FilenameFilter docFilterObj = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					//return (name.endsWith(".doc") || name.endsWith(".txt") || name.endsWith(".pdf") || name.endsWith(".zip"));
					return name.contains(".");
				}//end of accept(File dir, String name)
			};//end of FilenameFilter docFilterObj = new FilenameFilter() 
			//		for Logo
			if(strType.equalsIgnoreCase("WLG")){ 
				File dir = new File(strLogoDir);
				if(!dir.exists()){
					log.info(strLogoDir + "doesn't exists");
				}//end of if(!dir.exists())
				else{
					File[] fileArrObj = dir.listFiles(imageFilterObj);
					alFilePath = this.populateFilesToList(fileArrObj,alFilePath);			
				}//end of else
			}//end of if(strType.equalsIgnoreCase("WLG"))
			//    	for Company Name
			else if(strType.equalsIgnoreCase("WLC")){
				File dir = new File(strCompanyName);
				if(!dir.exists()){
					log.info(strCompanyName + "doesn't exists");
				}//end of if(!dir.exists())
				else{
					File[] fileArrObj = dir.listFiles(imageFilterObj);
					alFilePath = this.populateFilesToList(fileArrObj,alFilePath);
				}//end of else
			}//end of else if(strType.equalsIgnoreCase("WLC"))
			//		for Links
			else if(strType.equalsIgnoreCase("WLL")){ 
				File dir = new File(strLinks.toString());
				File defaultDir = new File(strDefaultDir);
				if(!dir.exists()){
					log.info(strLinks + "doesn't exists");
					File[] defFileArrObj = defaultDir.listFiles(docFilterObj);
					alFilePath = this.populateFilesToList(defFileArrObj,alFilePath);
				}//end of if(!dir.exists())
				else{
					File[] defFileArrObj = defaultDir.listFiles(docFilterObj);
					alFilePath = this.populateFilesToList(defFileArrObj,alFilePath);
					File[] fileArrObj = dir.listFiles(docFilterObj);
					alFilePath = this.populateFilesToList(fileArrObj,alFilePath);
				}//end of else
			}//end of else if(strType.equalsIgnoreCase("WLL"))
			request.getSession().setAttribute("frmLinkDetails",frmLinkDetails);			
			request.setAttribute("alFileList",alFilePath);
			return this.getForward(strBrowseList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strWebconfig));
		}//end of catch(Exception exp)
	}//end of doBrowse(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param request The HTTP request we are processing
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.getWebBoardId(request));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(Long lngMemberSeqId)
	
	/**
	 * This method builds all the files to populate in ArrayList 
	 * @param ArrayList alObj
	 * @return alObj ArrayList
	 */
	private ArrayList<Object> populateFilesToList(File[] fileArrayObj,ArrayList<Object> alObj) throws IOException{
		int iAlIndex = alObj.size();
		for(int j=0;j<fileArrayObj.length;j++,iAlIndex++){
			alObj.add(iAlIndex,(String)fileArrayObj[j].getPath().replaceAll("\\\\","\\\\\\\\"));
		}//end of for(intj=0;j<fileArrayObj.length;j++)
		return alObj;
	}//end of  populateFilesToList()
	
	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return ProductPolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ProductPolicyManager getProductPolicyManager() throws TTKException
	{
		ProductPolicyManager productPolicyManager = null;
		try
		{
			if(productPolicyManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyManager = (ProductPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProductPolicyManagerBean!com.ttk.business.administration.ProductPolicyManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strWebconfig);
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManager()
}//end of LinkDetailsAction() class
