/**
 * @ (#) QueryListAction.java Oct 17, 2008
 * Project      : TTK HealthCare Services
 * File         : QueryListAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Oct 17, 2008
 *
 * @author       :  Chandrasekaran J
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 * Modified by   :  
 */
package com.ttk.action.onlineforms;

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
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.onlineforms.OnlineAssistanceDetailVO;
import com.ttk.dto.onlineforms.OnlineAssistanceVO;
import com.ttk.dto.onlineforms.OnlineIntimationVO;
import com.ttk.dto.onlineforms.OnlineQueryVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class QueryListAction extends TTKAction
{
	private static final Logger log = Logger.getLogger( QueryListAction.class );
	//for forwarding
	private static final String strQueryList="querylist";
	private static final String strQueryDetail="querydetail";
	private static final String strSaveQueryDetail="savequerydetail";
	//for setting modes
	private static final String strForward ="Forward";
	private static final String strBackward ="Backward";
	
	//Exception Message Identifier
	private static final String strQuery="query";
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
			HttpServletResponse response) throws TTKException{
		try{
			setOnlineLinks(request);
			log.debug("Inside QueryListAction doDefault");
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
			getAttribute("UserSecurityProfile");
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append(" - [").append(userSecurityProfile.getPolicyNo()).append("]");
			DynaActionForm frmQueryList= (DynaActionForm) form;	
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//get the Table Data From the session 
			//StringBuffer sbfCaption= new StringBuffer();
			ArrayList alQueryList=null;
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			//String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strPageID.equals(""))
			{
				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.
						getParameter("pageId"))));
				return this.getForward(strQueryList, mapping, request);
			}// end of if(!strSortID.equals(""))
			else
			{
				tableData.createTableInfo("QueryListTable",null);
				tableData.modifySearchData("search");
			}// end of else
			alQueryList=onlineAccessObject.getOnlineQueryList(userSecurityProfile.getPolicyGrpSeqID());
			//frmQueryList.set("caption",sbfCaption.toString());
			tableData.setData(alQueryList,"search");
			frmQueryList.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("tableData",tableData);
			request.getSession().setAttribute("frmQueryList",frmQueryList);
			return this.getForward(strQueryList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request,mapping,new TTKException(exp,strQuery));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doBackward method of QueryListAction");
			setOnlineLinks(request);
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
			getAttribute("UserSecurityProfile");
//			get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			ArrayList alQueryList=null;
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			tableData.modifySearchData(strBackward);//modify the search data
			alQueryList=onlineAccessObject.getOnlineQueryList(userSecurityProfile.getPolicyGrpSeqID());
			tableData.setData(alQueryList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strQueryList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strQuery));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
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
			HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doForward method of QueryListAction");
			setOnlineLinks(request);
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
			getAttribute("UserSecurityProfile");
			TableData tableData =TTKCommon.getTableData(request);
			ArrayList alQueryList=null;
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			//modify the search data
			tableData.modifySearchData(strForward);
			alQueryList=onlineAccessObject.getOnlineQueryList(userSecurityProfile.getPolicyGrpSeqID());
			//set the table data
			tableData.setData(alQueryList, strForward);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strQueryList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strQuery));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	/**
	 * This method is called from the struts framework.
	 * This method is used to navigate to detail screen to add a record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doNewRequest(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doNewRequest method of QueryListAction");
			setOnlineLinks(request);
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
			getAttribute("UserSecurityProfile");
			StringBuffer sbfCaption=new StringBuffer();
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			sbfCaption.append(" - [").append(userSecurityProfile.getPolicyNo()).append("]");
			DynaActionForm frmOnlineQueryDetails = (DynaActionForm)form;
			TableData tableDataQueryList = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataQueryList")!=null) 
			{
				tableDataQueryList=(TableData)(request.getSession()).getAttribute("tableDataQueryList");
			}//end of if(request.getSession().getAttribute("tableDataCirculars")!=null)
			else
			{
				tableDataQueryList=new TableData();
			}//end of else
			ArrayList alQueryList=new ArrayList();
			OnlineIntimationVO onlineIntimationVO = new OnlineIntimationVO();
			String strEmpNbr= userSecurityProfile.getUSER_ID();
			Long lngPolicyGroupSeqId = userSecurityProfile.getPolicyGrpSeqID();
			String strPolicyNbr = userSecurityProfile.getPolicyNo();
			onlineIntimationVO = onlineAccessObject.getEmpContactInfo(strEmpNbr, lngPolicyGroupSeqId, strPolicyNbr);
			OnlineAssistanceDetailVO onlineAssistanceDetailVO = new OnlineAssistanceDetailVO();
			onlineAssistanceDetailVO.setEmailID(onlineIntimationVO.getEmailID());
			onlineAssistanceDetailVO.setMobileNbr(onlineIntimationVO.getMobileNbr());
			onlineAssistanceDetailVO.setPhoneNbr(onlineIntimationVO.getPhoneNbr());
			OnlineQueryVO onlineQueryVO = new OnlineQueryVO();
			onlineQueryVO.setSubmittedYN("Y");
			onlineQueryVO.setFeedBackSubmittedYN("Y");
			onlineAssistanceDetailVO.setOnlineQueryVO(onlineQueryVO);
			frmOnlineQueryDetails= (DynaActionForm)FormUtils.setFormValues("frmOnlineQueryDetails",
					onlineAssistanceDetailVO,this,mapping,request);
			frmOnlineQueryDetails.set("onlineQueryVO", FormUtils.setFormValues("frmOnlineQuery",
					onlineAssistanceDetailVO.getOnlineQueryVO(),this,mapping,request));
			alQueryList=onlineAssistanceDetailVO.getQueryVO();
			tableDataQueryList.createTableInfo("QuryInfoTable",null);
			//tableData.setSearchData(this.populateSearchCriteria(request));
			tableDataQueryList.modifySearchData("search");
			tableDataQueryList.setData(alQueryList,"search");
			frmOnlineQueryDetails.set("showSave","Y");
			frmOnlineQueryDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("tableDataQueryList",tableDataQueryList);
			request.getSession().setAttribute("frmOnlineQueryDetails",frmOnlineQueryDetails);
			return this.getForward(strQueryDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strQuery));
		}//end of catch(Exception exp)
	}//end of doNewRequest(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doViewQueryDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		try{
			setOnlineLinks(request);
			log.debug("Inside QueryListAction doViewQueryDetails");
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
			getAttribute("UserSecurityProfile");
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append(" - [").append(userSecurityProfile.getPolicyNo()).append("]");
			DynaActionForm frmOnlineQueryDetails =(DynaActionForm)form;
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			OnlineAssistanceDetailVO onlineAssistanceDetailVO = new OnlineAssistanceDetailVO();
			OnlineQueryVO onlineQueryVO = new OnlineQueryVO();
			OnlineAssistanceVO onlineAssistanceVO = new OnlineAssistanceVO();
			onlineAssistanceDetailVO.setOnlineQueryVO(onlineQueryVO);
			TableData tableData = TTKCommon.getTableData(request);
			TableData tableDataQueryList = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataQueryList")!=null) 
			{
				tableDataQueryList=(TableData)(request.getSession()).getAttribute("tableDataQueryList");
			}//end of if(request.getSession().getAttribute("tableDataCirculars")!=null)
			else
			{
				tableDataQueryList=new TableData();
			}//end of else
			Long lngQueryHdrSeqID = null;
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				onlineAssistanceVO=(OnlineAssistanceVO)tableData.getRowInfo(Integer.parseInt(
						request.getParameter("rownum")));
				lngQueryHdrSeqID= onlineAssistanceVO.getQueryHdrSeqId();
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			onlineAssistanceDetailVO= onlineAccessObject.getQueryHeaderInfo(lngQueryHdrSeqID,"WEB");
			frmOnlineQueryDetails= (DynaActionForm)FormUtils.setFormValues("frmOnlineQueryDetails",
					onlineAssistanceDetailVO,this,mapping,request);
			frmOnlineQueryDetails.set("onlineQueryVO", FormUtils.setFormValues("frmOnlineQuery",
					new OnlineQueryVO(),this,mapping,request));
			ArrayList alQueryList=new ArrayList();
			alQueryList=onlineAssistanceDetailVO.getQueryVO();
			tableDataQueryList.createTableInfo("QuryInfoTable",alQueryList);
			tableDataQueryList.setData(alQueryList);
			frmOnlineQueryDetails.set("caption",sbfCaption.toString());
			frmOnlineQueryDetails.set("showSave","Y");
			request.getSession().setAttribute("tableDataQueryList",tableDataQueryList);
			request.getSession().setAttribute("frmOnlineQueryDetails",frmOnlineQueryDetails);
			request.getSession().setAttribute("UserSecurityProfile", userSecurityProfile);
			return this.getForward(strQueryDetail,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request,mapping,new TTKException(exp,strQuery));
		}//end of catch(Exception exp)
	}//end of doViewQueryDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
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
	public ActionForward doViewQueryInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		try{
			setOnlineLinks(request);
			log.debug("Inside QueryListAction doViewQueryInfo");
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
			getAttribute("UserSecurityProfile");
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append(" - [").append(userSecurityProfile.getPolicyNo()).append("]");
			DynaActionForm frmOnlineQueryDetails =(DynaActionForm)form;
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			OnlineAssistanceDetailVO onlineAssistanceDetailVO = new OnlineAssistanceDetailVO();
			OnlineQueryVO onlineQueryVO = new OnlineQueryVO();
			onlineQueryVO.setFeedBackSubmittedYN("Y");
			onlineAssistanceDetailVO.setOnlineQueryVO(onlineQueryVO);
			//TableData tableData = TTKCommon.getTableData(request);
			TableData tableDataQueryList = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataQueryList")!=null) 
			{
				tableDataQueryList=(TableData)(request.getSession()).getAttribute("tableDataQueryList");
			}//end of if(request.getSession().getAttribute("tableDataCirculars")!=null)
			else
			{
				tableDataQueryList=new TableData();
			}//end of else
			Long lngQueryDtlSeqID = null;
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				onlineQueryVO=(OnlineQueryVO)tableDataQueryList.getRowInfo(Integer.parseInt(
						request.getParameter("rownum")));
				lngQueryDtlSeqID= onlineQueryVO.getQueryDtlSeqID();
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			onlineQueryVO= onlineAccessObject.getQueryInfo(lngQueryDtlSeqID);
			frmOnlineQueryDetails.set("onlineQueryVO", FormUtils.setFormValues("frmOnlineQuery",
					onlineQueryVO,this,mapping,request));
			frmOnlineQueryDetails.set("showSave",onlineQueryVO.getOnlineEditYN());
			frmOnlineQueryDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("tableDataQueryList",tableDataQueryList);
			request.getSession().setAttribute("frmOnlineQueryDetails",frmOnlineQueryDetails);
			return this.getForward(strQueryDetail,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request,mapping,new TTKException(exp,strQuery));
		}//end of catch(Exception exp)
	}//end of doViewQueryInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
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
			HttpServletResponse response) throws TTKException{
		try{
			
			log.debug("Inside QueryListAction doSave");
			setOnlineLinks(request);
			DynaActionForm frmOnlineQueryDetails =(DynaActionForm)form;
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
			getAttribute("UserSecurityProfile");
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append(" - [").append(userSecurityProfile.getPolicyNo()).append("]");
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			OnlineAssistanceDetailVO onlineAssistanceDetailVO = new OnlineAssistanceDetailVO();
			OnlineQueryVO onlineQueryVO = new OnlineQueryVO();
			TableData tableDataQueryList = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataQueryList")!=null) 
			{
				tableDataQueryList=(TableData)(request.getSession()).getAttribute("tableDataQueryList");
			}//end of if(request.getSession().getAttribute("tableDataCirculars")!=null)
			else
			{
				tableDataQueryList=new TableData();
			}//end of else
			if((userSecurityProfile.getOnlineRatingTypeID().equals("ORA")) &&((request.getParameter("onlineQueryVO.TTKRemarks")!=""))&& ((request.getParameter("onlineQueryVO.queryFeedbackTypeID")=="")))
			{
				TTKException expTTK = new TTKException();
    			expTTK.setMessage("error.feedback.required");
    			throw expTTK;
			}//end of if((userSecurityProfile.getOnlineRatingTypeID().equals("ORA")) &&(request.getParameter("onlineQueryVO.respondedDate")!=null )&& ((request.getParameter("onlineQueryVO.queryFeedbackTypeID")=="")||(request.getParameter("onlineQueryVO.queryFeedbackTypeID")==null)))
			onlineAssistanceDetailVO=(OnlineAssistanceDetailVO)FormUtils.getFormValues(frmOnlineQueryDetails,
					this,mapping,request);
			ActionForm frmOnlineQuery=(ActionForm)frmOnlineQueryDetails.get("onlineQueryVO");
			onlineQueryVO=(OnlineQueryVO)FormUtils.getFormValues(frmOnlineQuery,"frmOnlineQuery",
					this,mapping,request);
			onlineAssistanceDetailVO.setOnlineQueryVO(onlineQueryVO);
			onlineAssistanceDetailVO.setPolicyGrpSeqID(userSecurityProfile.getPolicyGrpSeqID());
			onlineAssistanceDetailVO.setPolicySeqID(userSecurityProfile.getPolicySeqID());
			onlineAssistanceDetailVO.setUpdatedBy(new Long(1));
			long lngQueryHdrSeqId=onlineAccessObject.saveQueryHeaderInfo(onlineAssistanceDetailVO);
			//frmOnlineQueryDetails.set("queryHdrSeqId",lngQueryHdrSeqId);
			if(lngQueryHdrSeqId!=0)
			{
				if(frmOnlineQueryDetails.get("queryHdrSeqId").equals(""))
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of if(frmPreAuthIntimationDetails.get("queryHdrSeqId").equals(""))
				else
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of else
			}//end of if(lngCount>0)
			onlineAssistanceDetailVO= onlineAccessObject.getQueryHeaderInfo(lngQueryHdrSeqId,"WEB");
			
			frmOnlineQueryDetails= (DynaActionForm)FormUtils.setFormValues("frmOnlineQueryDetails",onlineAssistanceDetailVO,
					this,mapping,request);
			frmOnlineQueryDetails.set("onlineQueryVO", FormUtils.setFormValues("frmOnlineQuery",
					new OnlineQueryVO(),this,mapping,request));
			ArrayList alQueryList=new ArrayList();
			alQueryList=onlineAssistanceDetailVO.getQueryVO();
			tableDataQueryList.createTableInfo("QuryInfoTable",alQueryList);
			tableDataQueryList.setData(alQueryList);
			frmOnlineQueryDetails.set("caption",sbfCaption.toString());
			frmOnlineQueryDetails.set("showSave","Y");
			request.getSession().setAttribute("tableDataQueryList",tableDataQueryList);
			request.getSession().setAttribute("frmOnlineQueryDetails",frmOnlineQueryDetails);
			return this.getForward(strSaveQueryDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request,mapping,new TTKException(exp,strQuery));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is called from the struts framework.
	 * This method is used to navigate to detail screen to add a record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doNewQuery(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doNewQuery method of QueryListAction");
			setOnlineLinks(request);
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
			getAttribute("UserSecurityProfile");
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append(" - [").append(userSecurityProfile.getPolicyNo()).append("]");
			DynaActionForm frmOnlineQueryDetails = (DynaActionForm)form;
			frmOnlineQueryDetails.set("onlineQueryVO", FormUtils.setFormValues("frmOnlineQuery",
					new OnlineQueryVO(),this,mapping,request));
			frmOnlineQueryDetails.set("caption",sbfCaption.toString());
			frmOnlineQueryDetails.set("showSave","Y");
			request.getSession().setAttribute("frmOnlineQueryDetails",frmOnlineQueryDetails);
			return this.getForward(strQueryDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strQuery));
		}//end of catch(Exception exp)
	}//end of doNewQuery(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside doReset of QueryListAction");
			setOnlineLinks(request);
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
			getAttribute("UserSecurityProfile");
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append(" - [").append(userSecurityProfile.getPolicyNo()).append("]");
			DynaActionForm frmOnlineQueryDetails =(DynaActionForm)form;
			TableData tableDataQueryList = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataQueryList")!=null) 
			{
				tableDataQueryList=(TableData)(request.getSession()).getAttribute("tableDataQueryList");
			}//end of if(request.getSession().getAttribute("tableDataCirculars")!=null)
			else
			{
				tableDataQueryList=new TableData();
			}//end of else
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			OnlineIntimationVO onlineIntimationVO = new OnlineIntimationVO();
			String strEmpNbr= userSecurityProfile.getUSER_ID();
			Long lngPolicyGroupSeqId = userSecurityProfile.getPolicyGrpSeqID();
			String strPolicyNbr = userSecurityProfile.getPolicyNo();
			onlineIntimationVO = onlineAccessObject.getEmpContactInfo(strEmpNbr, lngPolicyGroupSeqId, strPolicyNbr);
			OnlineAssistanceDetailVO onlineAssistanceDetailVO = new OnlineAssistanceDetailVO();
			onlineAssistanceDetailVO.setEmailID(onlineIntimationVO.getEmailID());
			onlineAssistanceDetailVO.setMobileNbr(onlineIntimationVO.getMobileNbr());
			onlineAssistanceDetailVO.setPhoneNbr(onlineIntimationVO.getPhoneNbr());
			OnlineQueryVO onlineQueryVO = new OnlineQueryVO();
			onlineAssistanceDetailVO.setOnlineQueryVO(onlineQueryVO);
			if(frmOnlineQueryDetails.get("queryHdrSeqId")!=null && 
					!frmOnlineQueryDetails.get("queryHdrSeqId").equals(""))// on clicking the edit link
			{
				onlineAssistanceDetailVO=onlineAccessObject.getQueryHeaderInfo(TTKCommon.getLong
						(frmOnlineQueryDetails.getString("queryHdrSeqId")),"WEB");
			}//end of if(frmOnlineQueryDetails.get("queryHdrSeqId")!=null && 
			frmOnlineQueryDetails= (DynaActionForm)FormUtils.setFormValues("frmOnlineQueryDetails",
					onlineAssistanceDetailVO,this,mapping,request);
			frmOnlineQueryDetails.set("onlineQueryVO", FormUtils.setFormValues("frmOnlineQuery",
					new OnlineQueryVO(),this,mapping,request));
			ArrayList alQueryList=new ArrayList();
			alQueryList=onlineAssistanceDetailVO.getQueryVO();
			tableDataQueryList.createTableInfo("QuryInfoTable",alQueryList);
			tableDataQueryList.setData(alQueryList);
			frmOnlineQueryDetails.set("caption",sbfCaption.toString());
			frmOnlineQueryDetails.set("showSave","Y");
			request.getSession().setAttribute("tableDataQueryList",tableDataQueryList);
			request.getSession().setAttribute("frmOnlineQueryDetails",frmOnlineQueryDetails);
			return this.getForward(strQueryDetail,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strQuery));
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
			HttpServletResponse response) throws TTKException{
		try{
			setOnlineLinks(request);
			log.debug("Inside doClose of QueryListAction");
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
			getAttribute("UserSecurityProfile");	
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			{
				ArrayList alQueryList=onlineAccessObject.getOnlineQueryList(userSecurityProfile.getPolicyGrpSeqID());
				tableData.setData(alQueryList);
				request.getSession().setAttribute("tableData",tableData);
				//reset the forward links after adding the records if any
				tableData.setForwardNextLink();
			}//end of if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			return this.getForward(strQueryList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strQuery));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			throw new TTKException(exp, strQuery);
		}//end of catch
		return onlineAccessManager;
	}//end getOnlineAccessManager()
}//end of QueryListAction class
