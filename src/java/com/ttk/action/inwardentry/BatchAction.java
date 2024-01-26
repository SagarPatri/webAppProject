/**
 * @ (#) BatchAction.java 2nd Feb 2006
 * Project      : TTK HealthCare Services
 * File         : BatchAction.java
 * Author       : Raghavendra T M
 * Company      : Span Systems Corporation
 * Date Created : 2nd Feb 2006
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.inwardentry;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import com.ttk.action.table.TableData;
import com.ttk.business.enrollment.PolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.enrollment.BatchVO;
import com.ttk.dto.enrollment.PolicyVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching the List of Batch .
 * This class also provides option for Deletion, Addition and Updation of Batch.
 */
public class BatchAction extends TTKAction {
	
	private static Logger log = Logger.getLogger( BatchAction.class );
	
	//Modes
	private static final String strForward="Forward";
	private static final String strBackward="Backward";
	private static final String strInwardEntry="Inward Entry";
	private static final String strEnrollment="Enrollment";
	
	//  Action mapping forwards
	private static final String strBatchList="batchlist";
	private static final String strEditBatchDetails="batchdetails";
	private static final String strUpdateBatch="savebatch";
	
	//Exception Message Identifier
	private static final String strInwardentryExp="inwardentry";
	
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
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doDefault method of BatchAction");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			tableData = new TableData();//create new table data object
			//create the required grid table
			tableData.createTableInfo("BatchSearchTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			DynaActionForm frmBatchList= (DynaActionForm) form;
			request.getSession().setAttribute("frmBatch",null);//reset the form data
			frmBatchList.set("ClarifyTypeID","BCN");	//setting default Clarification Status to (not required)
			return this.getForward(strBatchList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInwardentryExp));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSearch method of BatchAction");
			setLinks(request);
			PolicyManager batchPolicyObject=this.getPolicyManagerObject();
			ArrayList alBatchList = null;
			TableData tableData = TTKCommon.getTableData(request);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strBatchList));
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("BatchSearchTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				//sorting based on enrol_batch_seq_id in descending order
				tableData.setSortData("0001");
				tableData.setSortColumnName("ENROL_BATCH_SEQ_ID");
				tableData.setSortOrder("DESC");
				tableData.modifySearchData("search");
			}//end of else
			alBatchList=batchPolicyObject.getBatchList(tableData.getSearchData()); // Populating Data Which matches the search criteria
			tableData.setData(alBatchList,"search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strBatchList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInwardentryExp));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doBackward method of BatchAction");
			setLinks(request);
			PolicyManager batchPolicyObject=this.getPolicyManagerObject();
			ArrayList alBatchList = null;
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strBackward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			alBatchList = batchPolicyObject.getBatchList(tableData.getSearchData());
			tableData.setData(alBatchList,strBackward);
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strBatchList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInwardentryExp));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doForward method of BatchAction");
			setLinks(request);
			PolicyManager batchPolicyObject=this.getPolicyManagerObject();
			ArrayList alBatchList = null;
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strForward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			alBatchList = batchPolicyObject.getBatchList(tableData.getSearchData());
			tableData.setData(alBatchList,strForward);
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strBatchList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInwardentryExp));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to navigate to  detail screen to edit selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewBatch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doViewBatch method of BatchAction");
			setLinks(request);
			PolicyManager batchPolicyObject=this.getPolicyManagerObject();
			HashMap hmInsProducts = null;
			BatchVO batchVO = null;
			StringBuffer strCaption=new StringBuffer();
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmBatchDetails= null;
			frmBatchDetails = (DynaActionForm)form;//request.getSession().getAttribute("frmBatch");
			String strLink = TTKCommon.getActiveLink(request);
			Long lngBatchSeqID = null;
			// setting the batch details values based on batchSeqID from form/rownum to the appropriate modes
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				if(strLink.equals(strInwardEntry))
				{
					batchVO = (BatchVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
					lngBatchSeqID = batchVO.getBatchSeqID();
				}//end of if(strLink.equals(strInwardEntry))
				else if(strLink.equals(strEnrollment))
				{
					TableData  policyListData= (TableData)request.getSession().getAttribute("policyListData");
					PolicyVO policyVO=(PolicyVO)policyListData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
					lngBatchSeqID = policyVO.getBatchSeqID();
				}//end of if(strLink.equals(strEnrollment))
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			else if(!(TTKCommon.checkNull((String)frmBatchDetails.get("batchSeqID")).equals("")))
			{
				lngBatchSeqID = TTKCommon.getLong(frmBatchDetails.getString("batchSeqID"));
			}//end of if(!(TTKCommon.checkNull((String)frmBatchDetails.get("batchSeqID")).equals("")))
			else
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.batch.required");
				throw expTTK;
			}//end of else
			batchVO=batchPolicyObject.getBatch(lngBatchSeqID);
			DynaActionForm  frmBatch = (DynaActionForm)FormUtils.setFormValues("frmBatch",batchVO, this, mapping, request);
			frmBatch.set("hidInsuranceSeqID",frmBatch.get("insuranceSeqID"));
			frmBatch.set("hidCompanyName",frmBatch.get("companyName"));
			frmBatch.set("hidOfficeCode",frmBatch.get("officeCode"));											
			//setting viewmode and caption is based on mode and batch completion details
			if(!(String.valueOf(frmBatch.get("inwardCompleted")).equals("")))
			{
				frmBatch.set("viewmodebatch",true);
			}// end of if(!(String.valueOf(frmBatch.get("inwardCompleted")).equals("")))
			else
			{
				frmBatch.set("viewmodebatch",false);
			}// end of else
			if(strLink.equals(strEnrollment))
			{
				strCaption.append(" - Edit["+frmBatch.get("batchNbr")+"]");
				frmBatch.set("caption",strCaption.toString());
				frmBatch.set("viewmodebatch",true);
			}// end of if(strLink.equals(strEnrollment))
			else
			{
				strCaption.append(" - Edit");
				frmBatch.set("caption",strCaption.toString());
			}// end of else
			if(!(TTKCommon.checkNull(frmBatch.get("insuranceSeqID").toString()).equals("")))
			{
				hmInsProducts = batchPolicyObject.getProductInfo(TTKCommon.getLong(frmBatch.getString("insuranceSeqID")));
			}//end of if(!(TTKCommon.checkNull(frmBatch.get("insuranceSeqID").toString()).equals("")))
			if(hmInsProducts != null)
			{
				for(int i=0; i < hmInsProducts.size(); i++)
				{
					request.getSession().setAttribute("hmInsProducts",hmInsProducts);
				}//end of for(int i=0; i < hmInsProducts.size(); i++)
			}//end of if(hmInsProducts != null)
			request.getSession().setAttribute("frmBatch",frmBatch);
			return mapping.findForward(strEditBatchDetails);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInwardentryExp));
		}//end of catch(Exception exp)
	}//end of doViewBatch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is called from the struts framework.
	 * This method is used to navigate to detail screen to add a record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doAdd method of BatchAction");
			setLinks(request);
			PolicyManager batchPolicyObject=this.getPolicyManagerObject();
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			HashMap hmInsProducts = null;
			BatchVO batchVO = null;
			StringBuffer strCaption=new StringBuffer();
			batchVO = new BatchVO();
			DynaActionForm  frmBatch = (DynaActionForm)FormUtils.setFormValues("frmBatch",batchVO, this, mapping, request);
			strCaption.append(" - Add");
			if(frmBatch.get("insuranceSeqID")!= null)
			{
				hmInsProducts = batchPolicyObject.getProductInfo(TTKCommon.getLong(frmBatch.get("insuranceSeqID").toString()));
			}//end of if(frmBatch.get("insuranceSeqID")!= null)
			if(userSecurityProfile.getBranchID()!=null){
				frmBatch.set("officeSeqID", userSecurityProfile.getBranchID().toString());
			}//end of if(userSecurityProfile.getBranchID()!=null)
			else{
				frmBatch.set("officeSeqID", "");
			}//end of else
			frmBatch.set("clarifyTypeID","BCN");//setting default Clarification Status to (not required)
			frmBatch.set("enteredNbrPolicy","0");//setting default No. of Policies Entered to zero
			frmBatch.set("viewmodebatch",false);
			frmBatch.set("caption",strCaption.toString());
			request.getSession().setAttribute("hmInsProducts",hmInsProducts);
			request.getSession().setAttribute("frmBatch",frmBatch);
			return mapping.findForward(strEditBatchDetails);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInwardentryExp));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSave method of BatchAction");
			setLinks(request);
			PolicyManager batchPolicyObject=this.getPolicyManagerObject();
			HashMap hmInsProducts = null;
			BatchVO batchVO = null;
			StringBuffer strCaption=new StringBuffer();
			DynaActionForm frmBatchSave=(DynaActionForm)form;
			strCaption.append(" - Edit");
			batchVO=(BatchVO)FormUtils.getFormValues(frmBatchSave, "frmBatch",this, mapping, request);
			batchVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
			long lngResult = batchPolicyObject.saveBatch(batchVO);
			if((lngResult!=0))
			{	//setting updated message to add and edit modes	appropriatly
				if(!(frmBatchSave.getString("batchNbr").equals("")))
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}// end of if(!(frmBatchSave.getString("batchNbr").equals("")))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
				if((!frmBatchSave.getString("batchNbr").equals("")))
				{
					lngResult = TTKCommon.getLong(frmBatchSave.getString("batchSeqID"));
				}//end of if((!frmBatchSave.getString("batchNbr").equals("")))
			}//end of if(iResult!=0)
			batchVO = batchPolicyObject.getBatch(lngResult);
			DynaActionForm frmBatch = (DynaActionForm)FormUtils.setFormValues("frmBatch",batchVO, this, mapping, request);
			frmBatch.set("hidInsuranceSeqID",frmBatch.get("insuranceSeqID"));
			frmBatch.set("hidCompanyName",frmBatch.get("companyName"));
			frmBatch.set("hidOfficeCode",frmBatch.get("officeCode"));
			hmInsProducts = batchPolicyObject.getProductInfo(TTKCommon.getLong(frmBatch.get("insuranceSeqID").toString()));
			//setting viewmode and caption is based on mode and batch completion details
			frmBatch.set("caption",strCaption.toString());
			if(!(String.valueOf(frmBatch.get("inwardCompleted")).equals("")))
			{
				frmBatch.set("viewmodebatch",true);
			}//end of if(!(String.valueOf(frmBatch.get("inwardCompleted")).equals(""))) 
			else
			{
				frmBatch.set("viewmodebatch",false);
			}//end of else
			request.getSession().setAttribute("hmInsProducts",hmInsProducts);
			//frmBatch.set("hmInsProducts",hmInsProducts);
			request.getSession().setAttribute("frmBatch",frmBatch);
			return mapping.findForward(strUpdateBatch);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInwardentryExp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	public ActionForward doSaveBatchIns(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSaveBatchIns method of BatchAction");
			setLinks(request);
			PolicyManager batchPolicyObject=this.getPolicyManagerObject();
			HashMap hmInsProducts = null;
			BatchVO batchVO = null;
			StringBuffer strCaption=new StringBuffer();
			DynaActionForm frmBatchSave=(DynaActionForm)form;
				
			long insSeqId	=	(long) TTKCommon.getLong(frmBatchSave.getString("hidInsuranceSeqID"));
			
			strCaption.append(" - Edit");
			batchVO=(BatchVO)FormUtils.getFormValues(frmBatchSave, "frmBatch",this, mapping, request);
			batchVO.setInsuranceSeqID(insSeqId);
			batchVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
			long lngResult = batchPolicyObject.saveBatch(batchVO);
			if((lngResult!=0))
			{	//setting updated message to add and edit modes	appropriatly
				if(!(frmBatchSave.getString("batchNbr").equals("")))
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}// end of if(!(frmBatchSave.getString("batchNbr").equals("")))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
				if((!frmBatchSave.getString("batchNbr").equals("")))
				{
					lngResult = TTKCommon.getLong(frmBatchSave.getString("batchSeqID"));
				}//end of if((!frmBatchSave.getString("batchNbr").equals("")))
			}//end of if(iResult!=0)
			batchVO = batchPolicyObject.getBatch(lngResult);
			DynaActionForm frmBatch = (DynaActionForm)FormUtils.setFormValues("frmBatch",batchVO, this, mapping, request);
			frmBatch.set("hidInsuranceSeqID",frmBatch.get("insuranceSeqID"));
			frmBatch.set("hidCompanyName",frmBatch.get("companyName"));
			frmBatch.set("hidOfficeCode",frmBatch.get("officeCode"));
			//hmInsProducts = batchPolicyObject.getProductInfo(TTKCommon.getLong(frmBatch.get("insuranceSeqID").toString()));
			hmInsProducts = batchPolicyObject.getProductInfo(insSeqId);
			//setting viewmode and caption is based on mode and batch completion details
			frmBatch.set("caption",strCaption.toString());
			if(!(String.valueOf(frmBatch.get("inwardCompleted")).equals("")))
			{
				frmBatch.set("viewmodebatch",true);
			}//end of if(!(String.valueOf(frmBatch.get("inwardCompleted")).equals(""))) 
			else
			{
				frmBatch.set("viewmodebatch",false);
			}//end of else
			request.getSession().setAttribute("hmInsProducts",hmInsProducts);
			//frmBatch.set("hmInsProducts",hmInsProducts);
			request.getSession().setAttribute("frmBatch",frmBatch);
			return mapping.findForward(strUpdateBatch);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInwardentryExp));
		}//end of catch(Exception exp)
	}//end of doSaveBatchIns(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to delete the record from the list.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doDeleteList method of BatchAction");
			setLinks(request);
			PolicyManager batchPolicyObject=this.getPolicyManagerObject();
			ArrayList alBatchList = null;
			TableData tableData = TTKCommon.getTableData(request);
			String strConcatenatedSeqID= this.getConcatenatedSeqID(request,(TableData)request.getSession().getAttribute("tableData"));
			int iCount=0;
			ArrayList<Object> alDeleteBatch = new ArrayList<Object>();
			alDeleteBatch.add("BATCH");
			alDeleteBatch.add(strConcatenatedSeqID);
			alDeleteBatch.add(null);
			alDeleteBatch.add(null);
			alDeleteBatch.add(null);
			alDeleteBatch.add(TTKCommon.getUserSeqId(request));
			// Remove the selected Id
			iCount=batchPolicyObject.deleteBatch(alDeleteBatch);
			//fetch the data from previous set of rowcounts, if all records are deleted for the current set of rowcounts
			if(iCount == tableData.getData().size())
			{
				tableData.modifySearchData("Delete");//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alBatchList=batchPolicyObject.getBatchList(tableData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end of if(iCount == tableData.getData().size())
			else
			{
				alBatchList=batchPolicyObject.getBatchList(tableData.getSearchData());
			}// end of else
			tableData.setData(alBatchList,"Delete");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strBatchList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInwardentryExp));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doReset method of BatchAction");
			setLinks(request);
			PolicyManager batchPolicyObject=this.getPolicyManagerObject();
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			HashMap hmInsProducts = null;
			BatchVO batchVO = null;
			StringBuffer strCaption=new StringBuffer();
			DynaActionForm BatchForm=(DynaActionForm)form;
			Boolean bViewModeBatch = (Boolean)BatchForm.get("viewmodebatch");
			batchVO = new BatchVO();
			if(BatchForm.get("batchSeqID")!=null && !BatchForm.get("batchSeqID").equals(""))
			{
				batchVO.setBatchSeqID(TTKCommon.getLong((String)BatchForm.get("batchSeqID")));
				//calling business layer to get the batch detail
				batchVO = batchPolicyObject.getBatch(batchVO.getBatchSeqID());
				BatchForm=(DynaActionForm)FormUtils.setFormValues("frmBatch",batchVO, this,mapping,request);
				strCaption.append(" - Edit");
			}//end of if(generalForm.get("batchSeqID")!=null && !generalForm.get("batchSeqID").equals(""))
			else
			{
				if(userSecurityProfile.getBranchID()!=null){					
					BatchForm=(DynaActionForm)FormUtils.setFormValues("frmBatch",new BatchVO(), this,mapping,request);
					BatchForm.set("officeSeqID", userSecurityProfile.getBranchID().toString());
					BatchForm.set("clarifyTypeID","BCN");					
				}//end of if(userSecurityProfile.getBranchID()!=null)
				else{					
					BatchForm.set("officeSeqID", "");
					BatchForm=(DynaActionForm)FormUtils.setFormValues("frmBatch",new BatchVO(), this,mapping,request);
					BatchForm.set("clarifyTypeID","BCN");
				}//end of else
				strCaption.append(" - Add");
			}//else
			BatchForm.set("hidInsuranceSeqID",BatchForm.get("insuranceSeqID"));
			BatchForm.set("hidCompanyName",BatchForm.get("companyName"));
			BatchForm.set("hidOfficeCode",BatchForm.get("officeCode"));
			if(BatchForm.get("insuranceSeqID")!= null)
			{
				hmInsProducts = batchPolicyObject.getProductInfo(TTKCommon.getLong(BatchForm.get("insuranceSeqID").toString()));
			}//end of if(BatchForm.get("insuranceSeqID")!= null)
			// setting viewmode and caption in reset mode
			BatchForm.set("caption",strCaption.toString());
			BatchForm.set("viewmodebatch",bViewModeBatch);
			request.getSession().setAttribute("hmInsProducts",hmInsProducts);
			request.getSession().setAttribute("frmBatch",BatchForm);
			return this.getForward(strEditBatchDetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInwardentryExp));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	
	/**
     * This method is used to getInsCode based on professional ID
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
     public ActionForward getInsCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the getInsCode method of HospitalSearchAction");
            		setLinks(request);
            		ArrayList alProfessionals	=	null;
            	 	PolicyManager batchPolicyObject=this.getPolicyManagerObject();
            	 	String insCode	=	request.getParameter("insCode");
            	 	alProfessionals= batchPolicyObject.getInsCode(insCode);
            	 	PrintWriter out = response.getWriter();  
        	        response.setContentType("text/xml");  
        	        response.setHeader("Cache-Control", "no-cache");  
        	        response.setStatus(HttpServletResponse.SC_OK);  
        	        if(alProfessionals!=null)
        	        	if(alProfessionals.get(0)!=null || alProfessionals.get(0)!="")
        	        		out.write(alProfessionals.get(0)+","+alProfessionals.get(1));  
        	        
        	        out.flush();  
            		
            		return null;
            	}//end of try
            	catch(TTKException expTTK)
        		{
        			return this.processExceptions(request, mapping, expTTK);
        		}//end of catch(TTKException expTTK)
        		catch(Exception exp)
        		{
        			return this.processExceptions(request, mapping, new TTKException(exp,strInwardentryExp));
        		}//end of catch(Exception exp)
            }//end of getInsCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    
    
    

	/**
	 * This method is used to navigate to the next screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doBatchChangeOffice(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doBatchChangeOffice method of BatchAction");
			setLinks(request);
			return mapping.findForward("batchaddedit");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInwardentryExp));
		}//end of catch(Exception exp)
	}//end of doBatchChangeOffice(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
						sbfConcatenatedSeqId.append(String.valueOf(((BatchVO)tableData.getRowInfo(Integer.parseInt(strChOpt[iCounter]))).getBatchSeqID()));
					}//end of if(iCounter==0)
					else
					{
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((BatchVO)tableData.getRowInfo(Integer.parseInt(strChOpt[iCounter]))).getBatchSeqID()));
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		sbfConcatenatedSeqId.append("|");
		return sbfConcatenatedSeqId.toString();
	} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)
	
	/**
	 * Returns the PolicyManager session object for invoking methods on it.
	 * @return PolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PolicyManager getPolicyManagerObject() throws TTKException
	{
		PolicyManager policyManager = null;
		try
		{
			if(policyManager == null)
			{
				InitialContext ctx = new InitialContext();
				policyManager = (PolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/PolicyManagerBean!com.ttk.business.enrollment.PolicyManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strBatchList);
		}//end of catch
		return policyManager;
	}//end of getPolicyManagerObject()
	
	/**
	 * Return the ArrayList populated with Search criteria elements
	 * @param DynaActionForm
	 * @param HttpServletRequest
	 * @return ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmBatchList,HttpServletRequest request)
	{
		ArrayList<Object> alSearchParams =new ArrayList<Object>();
		BatchVO batchVO=null;
		batchVO=(BatchVO)request.getAttribute("BatchVO");
		if(batchVO!=null)
		{
			frmBatchList.set("insSeqId",batchVO.getInsuranceSeqID().toString());
		}//end of if(batchVO!=null)
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmBatchList.get("BatchNbr")));
		alSearchParams.add((String)frmBatchList.get("InsuranceSeqID"));
		alSearchParams.add((String)frmBatchList.get("startDate"));
		alSearchParams.add((String)frmBatchList.get("endDate"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmBatchList.get("OfficeCode")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		alSearchParams.add((String)frmBatchList.get("ClarifyTypeID"));
		alSearchParams.add((String)frmBatchList.get("BatchStatusID"));
		alSearchParams.add((String)frmBatchList.get("EntryModeID"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmBatchList.get("PolicyNbr")));
		alSearchParams.add((String)frmBatchList.get("batchType"));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmBatchList,HttpServletRequest request)
	
}//end of BatchAction

