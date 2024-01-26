/**
 * @ (#) AssociateDocumentsAction.java
 * Project        : Vidal Health TPA Services
 * File           : AssociateDocumentsAction.java
 * Author         : Balaji C R B
 * Company        : Span Systems Corporation
 * Date Created   : August 25,2008
 *
 * @author        : Balaji C R B
 * Modified by    : 
 * Modified date  :
 * Reason         : 
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
import com.ttk.business.administration.ProdPolicyConfigManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ClauseDocumentVO;

import formdef.plugin.util.FormUtils;


/**
 * This class is used to get the List of Plan for the product.
 * This also provides addition of plan for products.
 */

public class AssociateDocumentsAction extends TTKAction
{
	private static Logger log = Logger.getLogger( AssociateDocumentsAction.class );
	
	//Action mapping forwards.
	private static final String strProductAssociatedDocumentsList="productassociateddocumentslist";
	private static final String strPolicyAssociatedDocumentsList="policyassociateddocumentslist";
	private static final String strDocumentsClose="documentclose";	
	private static final String strDocumentList="documentlist";
	
	//Exception Message Identifier
	private static final String strDocument="document";
	
	/**
	 * This method is used to get List of Plans for the Product.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward  doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	{
		try
		{
			log.debug("Inside the doDefault method of AssociateDocumentsAction");
			setLinks(request);
			String strTable = "";
			DynaActionForm frmAssociateDocuments= null;
			if( "Y".equals( (String)request.getParameter("Entry") ) ) {
				frmAssociateDocuments = (DynaActionForm)form;
				frmAssociateDocuments.initialize(mapping);
			}//end of if(request.getParameter("Entry").equals("Y"))
			else {
				frmAssociateDocuments = (DynaActionForm) request.getSession().getAttribute("frmAssociateDocuments");
				if(frmAssociateDocuments==null){
					frmAssociateDocuments = (DynaActionForm)form;
					frmAssociateDocuments.initialize(mapping);
				}//end of if(frmAssociateDocuments==null)
			}//end of else			
			StringBuffer sbfCaption=new StringBuffer();
			ArrayList alAssociatedDocumentsList = new ArrayList();
			ProdPolicyConfigManager  productPolicyConfig= null;
			TableData tableDataAssociatedDocuments =TTKCommon.getTableData(request);
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardPath=null;
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strProductAssociatedDocumentsList;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strPolicyAssociatedDocumentsList;
			}//end of else
			sbfCaption = sbfCaption.append(this.buildCaption(strActiveSubLink,request));			
			frmAssociateDocuments.set("caption",sbfCaption.toString());
			//get the table data from session if exists			
			if(tableDataAssociatedDocuments==null){
				//create new table data object
				tableDataAssociatedDocuments = new TableData();
			}//end of if(tableData==null) 	
			//create the required grid table
			strTable = "AssociatedDocumentsTable";
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(strSortID.equals(""))
			{
				tableDataAssociatedDocuments.createTableInfo(strTable,null);
				tableDataAssociatedDocuments.setSearchData(this.populateSearchCriteria(request));
				tableDataAssociatedDocuments.modifySearchData("search");
			}//end of if(strSortID.equals(""))
			else
			{
				tableDataAssociatedDocuments.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableDataAssociatedDocuments.modifySearchData("sort");//modify the search data	
			}// end of else	
			productPolicyConfig = this.getProductPolicyConfigObject();
			alAssociatedDocumentsList = productPolicyConfig.getClauseDocList(tableDataAssociatedDocuments.getSearchData());
			tableDataAssociatedDocuments.setData(alAssociatedDocumentsList,"search");
			request.getSession().setAttribute("tableDataAssociatedDocuments",tableDataAssociatedDocuments);
			request.setAttribute("frmAssociateDocuments",frmAssociateDocuments);
			return this.getForward(strForwardPath, mapping, request);
		} // end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDocument));
		}//end of catch(Exception exp)
	} // end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception 
	
	/**
	 * This method is called from the struts framework.
	 * This method is used to delete selected record(s)
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
			log.debug("Inside AssociateDocumentsAction doDelete");
			StringBuffer sbfDeleteId = new StringBuffer();
			ArrayList <Object>alDocumentDelete=new ArrayList<Object>();
			ArrayList <Object>alAssociatedDocumentsList=null;
			DynaActionForm frmAssociateDocuments=(DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			String strForwardPath=null;
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strProductAssociatedDocumentsList;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strPolicyAssociatedDocumentsList;
			}//end of else
			//sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");	
			sbfCaption = sbfCaption.append(this.buildCaption(strActiveSubLink,request));
			ProdPolicyConfigManager productPolicyConfig=this.getProductPolicyConfigObject();
			TableData tableDataAssociatedDocuments = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataAssociatedDocuments")!=null)
			{
				tableDataAssociatedDocuments=(TableData)(request.getSession()).getAttribute("tableDataAssociatedDocuments");
			}//end of if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			else
			{
				tableDataAssociatedDocuments=new TableData();
			}//end of else
			sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().getAttribute("tableDataAssociatedDocuments")));
			alDocumentDelete.add(String.valueOf(sbfDeleteId));
			alDocumentDelete.add(TTKCommon.getUserSeqId(request));
			frmAssociateDocuments.initialize(mapping);
			int iCount=productPolicyConfig.deleteClauseDocInfo(alDocumentDelete);
			log.debug("iCount value is :"+iCount);
			//refresh the data in order to get the new records if any.
			alAssociatedDocumentsList=productPolicyConfig.getClauseDocList(tableDataAssociatedDocuments.getSearchData());
			tableDataAssociatedDocuments.setData(alAssociatedDocumentsList);
			frmAssociateDocuments.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("tableDataAssociatedDocuments",tableDataAssociatedDocuments);
			request.setAttribute("frmAssociateDocuments",frmAssociateDocuments);
			return this.getForward(strForwardPath,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strDocument));
		}//end of catch(Exception exp)
	}//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
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
			log.debug("Inside AssociateDocumentsAction doSave");
			ClauseDocumentVO clauseDocumentVO=null;
			DynaActionForm frmAssociateDocuments=(DynaActionForm)form;
			ArrayList <Object> alAssociatedDocumentsList=null;
			StringBuffer sbfCaption= new StringBuffer();
			ProdPolicyConfigManager productPolicyConfig=this.getProductPolicyConfigObject();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardPath = null;
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strProductAssociatedDocumentsList;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strPolicyAssociatedDocumentsList;
			}//end of else
			//sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			sbfCaption = sbfCaption.append(this.buildCaption(strActiveSubLink,request));
			TableData tableDataAssociatedDocuments = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataAssociatedDocuments")!=null) 
			{
				tableDataAssociatedDocuments=(TableData)(request.getSession()).getAttribute("tableDataAssociatedDocuments");
			}//end of if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			else
			{
				tableDataAssociatedDocuments=new TableData();
				tableDataAssociatedDocuments.createTableInfo("AssociatedDocumentsTable",null);
				tableDataAssociatedDocuments.setSearchData(this.populateSearchCriteria(request));
				tableDataAssociatedDocuments.modifySearchData("search");
			}//end of else
			clauseDocumentVO=(ClauseDocumentVO)FormUtils.getFormValues(frmAssociateDocuments,this, mapping, request);
			clauseDocumentVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
			clauseDocumentVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			int inUpdate=productPolicyConfig.saveClauseDocInfo(clauseDocumentVO);
			if(inUpdate>0)
			{
				if(!frmAssociateDocuments.getString("clauseDocSeqID").equals(""))
				{
					//set the appropriate message
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if(frmLinkDetails.get("clauseDocSeqID")!=null)
				else
				{
					//set the appropriate message
					request.setAttribute("updated","message.addedSuccessfully");
				}//end else
				frmAssociateDocuments.initialize(mapping);
				alAssociatedDocumentsList=productPolicyConfig.getClauseDocList(tableDataAssociatedDocuments.getSearchData());
				tableDataAssociatedDocuments.setData(alAssociatedDocumentsList);				
				frmAssociateDocuments.set("caption",sbfCaption.toString());
				request.getSession().setAttribute("tableDataAssociatedDocuments",tableDataAssociatedDocuments);
				request.setAttribute("frmAssociateDocuments",frmAssociateDocuments);
			}// end of if(iUpdate>0)
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strDocument));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
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
			log.debug("Inside the doReset method of AssociateDocumentsAction");
			setLinks(request);
			ClauseDocumentVO clauseDocumentVO=null;
			DynaActionForm frmAssociateDocuments=(DynaActionForm)form;
			ProdPolicyConfigManager productPolicyConfig=this.getProductPolicyConfigObject();
			StringBuffer sbfCaption= new StringBuffer();
			String strForwardPath=null;
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strProductAssociatedDocumentsList;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strPolicyAssociatedDocumentsList;
			}//end of else
			//sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			sbfCaption = sbfCaption.append(this.buildCaption(strActiveSubLink,request));
			if(frmAssociateDocuments.get("clauseDocSeqID")!= null && (!frmAssociateDocuments.get("clauseDocSeqID").equals("")))
			{
				//to check
				clauseDocumentVO=productPolicyConfig.getClauseDocInfo(TTKCommon.getLong(frmAssociateDocuments.getString("clauseDocSeqID")));
				frmAssociateDocuments = (DynaActionForm)FormUtils.setFormValues("frmAssociateDocuments",clauseDocumentVO,
						this,mapping,request);
			}// end of if(frmLinkDetails.get("configLinkSeqID")!= null && (!frmLinkDetails.get("configLinkSeqID").equals("")))
			else if(frmAssociateDocuments.get("clauseDocSeqID")== null || frmAssociateDocuments.get("clauseDocSeqID").equals(""))
			{
				frmAssociateDocuments.initialize(mapping);
			}// end of else	if(frmLinkDetails.get("configLinkSeqID")== null || frmLinkDetails.get("configLinkSeqID").equals(""))
			frmAssociateDocuments.set("caption",sbfCaption.toString());
			request.setAttribute("frmAssociateDocuments",frmAssociateDocuments);
			return this.getForward(strForwardPath,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDocument));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
     * This method is used to list out the document file names from the server 
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
			log.debug("Inside AssociateDocumentsAction doBrowse");
			DynaActionForm frmAssociateDocuments = (DynaActionForm) form;
			String strDefaultDir = TTKPropertiesReader.getPropertyValue("AssociatedDocuments");
			ArrayList<Object> alFilePath = new ArrayList<Object>();
			//in case of links groupid folder should be included in the path
			/*StringBuffer  strLinks = new StringBuffer();
			strLinks = strLinks.append(TTKPropertiesReader.getPropertyValue("WebLoginDocs"));
			if(policyDetailVo.getGrpID()!=null){
				strLinks = strLinks.append("/"+ policyDetailVo.getGrpID());
			}//end of if(policyDetailVo.getGrpID()!=null){ 
*/			FilenameFilter docFilterObj = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					//return (name.endsWith(".doc") || name.endsWith(".txt") || name.endsWith(".pdf") || name.endsWith(".zip"));
					return name.contains(".");
				}//end of accept(File dir, String name)
			};//end of FilenameFilter docFilterObj = new FilenameFilter() 			
			File defaultDir = new File(strDefaultDir);
			File[] defFileArrObj = defaultDir.listFiles(docFilterObj);
			if(defFileArrObj.length>0){
				alFilePath = this.populateFilesToList(defFileArrObj,alFilePath);
			}//end of if(defFileArrObj.length>0)
			request.getSession().setAttribute("frmAssociateDocuments",frmAssociateDocuments);			
			request.setAttribute("alFileList",alFilePath);
			return this.getForward(strDocumentList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strDocument));
		}//end of catch(Exception exp)
	}//end of doBrowse(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside AssociateDocumentsAction doSelect");
			DynaActionForm frmAssociateDocuments= null;
			frmAssociateDocuments = (DynaActionForm) request.getSession().getAttribute("frmAssociateDocuments");
			ArrayList alAssociatedDocumentsList=null;
			ProdPolicyConfigManager productPolicyConfig=this.getProductPolicyConfigObject();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardPath=null;
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strProductAssociatedDocumentsList;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strPolicyAssociatedDocumentsList;
			}//end of else
			if(frmAssociateDocuments!=null){
				frmAssociateDocuments.set("docPath",((DynaActionForm)form).getString("docPath"));
			}//end of if(request.getSession().getAttribute("frmLinkDetails")!=null)
			else {
				frmAssociateDocuments = (DynaActionForm)form;
			}//end of else	
			if(((DynaActionForm)form).getString("docPath")!=null){
				frmAssociateDocuments.set("docPath",((DynaActionForm)form).getString("docPath"));
			}//end of if(((DynaActionForm)form).getString("docpath")!=null)
			else {
				frmAssociateDocuments.set("docPath","");
			}//end of else
			TableData tableDataAssociatedDocuments = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataAssociatedDocuments")!=null)
			{
				tableDataAssociatedDocuments=(TableData)(request.getSession()).getAttribute("tableDataAssociatedDocuments");
			}//end of if(request.getSession().getAttribute("tableDataAssociatedDocuments")!=null)
			else
			{
				tableDataAssociatedDocuments=new TableData();
			}//end of else
			StringBuffer sbfCaption= new StringBuffer();
			//sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			sbfCaption = sbfCaption.append(this.buildCaption(strActiveSubLink,request));
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strSortID.equals(""))
			{
				tableDataAssociatedDocuments.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableDataAssociatedDocuments.modifySearchData("sort");//modify the search data
			}// end of if(!strSortID.equals(""))
			else
			{
				tableDataAssociatedDocuments.createTableInfo("AssociatedDocumentsTable",null);
				tableDataAssociatedDocuments.setSearchData(this.populateSearchCriteria(request));
				tableDataAssociatedDocuments.modifySearchData("search");
			}// end of else
			alAssociatedDocumentsList=productPolicyConfig.getClauseDocList(tableDataAssociatedDocuments.getSearchData());
			tableDataAssociatedDocuments.setData(alAssociatedDocumentsList);
			frmAssociateDocuments.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("tableDataAssociatedDocuments",tableDataAssociatedDocuments);
			request.setAttribute("frmAssociateDocuments",frmAssociateDocuments);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strDocument));
		}//end of catch(Exception exp)
	}//end of doSelect(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	
	/**
	 * This method is used to navigate to previous screen when close  button is clicked.
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
		try
		{
			log.debug("Inside the doClose method of AssociateDocumentsAction");
			setLinks(request);			
			return mapping.findForward(strDocumentsClose);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDocument));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doViewDocumentLinkDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside LinkDetailsAction doViewLinkDetails");
			ClauseDocumentVO clauseDocumentVO=null;
			ProdPolicyConfigManager productPolicyConfig=this.getProductPolicyConfigObject();
			StringBuffer sbfCaption= new StringBuffer();
			String strForwardPath=null;
			String strActiveSubLink = TTKCommon.getActiveSubLink(request);
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strProductAssociatedDocumentsList;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strPolicyAssociatedDocumentsList;
			}//end of else
			//sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			DynaActionForm frmAssociateDocuments=(DynaActionForm)form;
			sbfCaption = sbfCaption.append(this.buildCaption(strActiveSubLink,request));			
			//if rownumber is found populate the form object
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{				
				clauseDocumentVO=(ClauseDocumentVO)((TableData)request.getSession().getAttribute("tableDataAssociatedDocuments")).
				getData().get(Integer.parseInt(request.getParameter("rownum")));
				clauseDocumentVO=productPolicyConfig.getClauseDocInfo(clauseDocumentVO.getClauseDocSeqID());
				frmAssociateDocuments = (DynaActionForm)FormUtils.setFormValues("frmAssociateDocuments",clauseDocumentVO,
						this,mapping,request);
			}// end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			else
			{
				frmAssociateDocuments.initialize(mapping);
			}// end of else
			//frmLinkDetails.set("showLinkYN","Y");
			frmAssociateDocuments.set("caption",sbfCaption.toString());
			request.setAttribute("frmAssociateDocuments",frmAssociateDocuments);
			return this.getForward(strForwardPath,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strDocument));
		}//end of catch(Exception exp)
	}//end of doViewLinkDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse
	//response)
	
	/**
	 * Returns the ProdPolicyConfigManager session object for invoking methods on it.
	 * @return productPolicyConfigManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private ProdPolicyConfigManager getProductPolicyConfigObject() throws TTKException
	{
		ProdPolicyConfigManager productPolicyConfigManager = null;
		try
		{
			if(productPolicyConfigManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyConfigManager = (ProdPolicyConfigManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProdPolicyConfigManagerBean!com.ttk.business.administration.ProdPolicyConfigManager");
			}//end of if(productPolicyConfigManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "strDocument");
		}//end of catch(Exception exp)
		return productPolicyConfigManager;
	}//end of getProductPolicyConfigObject()
	
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
	}//end of populateSearchCriteria(request)
	
	/**
	 * This method  prepares the Caption based on the flow and retunrs it
	 * @param strActiveSubLink current Active sublink
	 * @param request current HttpRequest
	 * @return String caption built
	 * @throws TTKException
	 */
	private String buildCaption(String strActiveSubLink,HttpServletRequest request)throws TTKException
	{
		StringBuffer sbfCaption=new StringBuffer();
		if(strActiveSubLink.equals("Products"))
		{
			DynaActionForm frmProductDetail =(DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			sbfCaption.append("[").append(frmProductDetail.getString("companyName")).append("]");
			sbfCaption.append("	[").append(frmProductDetail.getString("productName")).append("]");
		}//end of if(strActiveSubLink.equals("Products"))
		else if(strActiveSubLink.equals("Policies"))
		{
			DynaActionForm frmPoliciesDetail =(DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
			sbfCaption.append("[").append(frmPoliciesDetail.getString("companyName")).append("]");
			sbfCaption.append("	[").append(frmPoliciesDetail.getString("policyNbr")).append("]");
		}//end of else if(strActiveSubLink.equals("Policies"))
		return sbfCaption.toString();
	}//end of buildCaption(String strActiveSubLink,HttpServletRequest request)
	
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
	 * Returns a string which contains the | separated sequence id's to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the | separated sequence id's to be delete
	 * @throws TTKException If any run time Excepton occures
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableDataAssociatedDocuments)throws TTKException
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
						sbfDeleteId.append("|").append(String.valueOf(((ClauseDocumentVO)tableDataAssociatedDocuments.getData().
								get(Integer.parseInt(strChk[i]))).getClauseDocSeqID()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((ClauseDocumentVO)tableDataAssociatedDocuments.getData().
								get(Integer.parseInt(strChk[i]))).getClauseDocSeqID().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId=sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return String.valueOf(sbfDeleteId);
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)
}//end of AssociateDocumentsAction
