/**
 * @ (#) OnlineAssistanceAction.java Oct 23, 2008
 * Project 	     : TTK HealthCare Services
 * File          : OnlineAssistanceAction.java
 * Author        : Balakrishna E
 * Company       : Span Systems Corporation
 * Date Created  : Oct 23, 2008
 *
 * @author       : Balakrishna E
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.support;

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
import com.ttk.business.support.SupportManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.onlineforms.OnlineAssistanceDetailVO;
import com.ttk.dto.onlineforms.OnlineAssistanceVO;
import com.ttk.dto.onlineforms.OnlineQueryVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is reusable for searching of List of Online assistance in support flow.
 *  This class also provides option for adding investigation information in support, pre-auth and claims flow.
 */
public class OnlineAssistanceAction extends TTKAction  {

	private static final Logger log = Logger.getLogger( OnlineAssistanceAction.class ); // Getting Logger for this Class file

	private  static final String strForward="Forward";
	private  static final String strBackward="Backward";

	//  Action mapping forwards
	private  static final String strAssistance="assistancelist";
	private static final String strQueryDetail="querydetail";
	//private  static final String strInvestigationDetails="invsdetails";

	//Exception Message Identifier
    private static final String strQueryError="OnlineAssistance";

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
													HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside OnlineAssistanceAction  doDefault");
			TableData tableData=TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("OnlineAssistanceTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			//clear the dynaform if visting from left links for the first time
			((DynaActionForm)form).initialize(mapping);//reset the form data
			return this.getForward(strAssistance, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strQueryError));
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
			log.debug("Inside OnlineAssistanceAction doSearch");
			SupportManager supportManager = this.getSupportManagerObject();
			ArrayList alIntimationList = null;
			TableData tableData=TTKCommon.getTableData(request);
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
					return (mapping.findForward(strAssistance));
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("OnlineAssistanceTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.setSortData("0001");
				tableData.setSortColumnName("REQUEST_ID");
				tableData.setSortOrder("DESC");
				tableData.modifySearchData("search");
			}//end of else
			alIntimationList= supportManager.getSupportQueryList(tableData.getSearchData());
			tableData.setData(alIntimationList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strAssistance, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strQueryError));
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
	public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doBackward method of OnlineAssistanceAction");
			setLinks(request);
//			get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			ArrayList alQueryList=null;
			SupportManager supportManager = this.getSupportManagerObject();
			tableData.modifySearchData(strBackward);//modify the search data
			alQueryList=supportManager.getSupportQueryList(tableData.getSearchData());
			tableData.setData(alQueryList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strAssistance, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strQueryError));
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
			log.debug("Inside the doForward method of OnlineAssistanceAction");
			setLinks(request);
			TableData tableData =TTKCommon.getTableData(request);
			ArrayList alQueryList=null;
			SupportManager supportManager = this.getSupportManagerObject();
			//modify the search data
			tableData.modifySearchData(strForward);
			alQueryList=supportManager.getSupportQueryList(tableData.getSearchData());
			//set the table data
			tableData.setData(alQueryList, strForward);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strAssistance, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strQueryError));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param frmCardPrinting DynaActionForm
	 * @param request HttpServletRequest
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmOnlineAssistList,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add((String)frmOnlineAssistList.get("StatusTypeID"));
		alSearchParams.add((String)frmOnlineAssistList.get("RequestNo"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmOnlineAssistList.get("PolicyNo")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmOnlineAssistList.get("EnrollmentID")));
		alSearchParams.add((String)(frmOnlineAssistList.get("FeedBackResponseID")));
		alSearchParams.add((String)(frmOnlineAssistList.get("FeedBackTypeID")));
		alSearchParams.add((String)frmOnlineAssistList.get("ReceivedAfter"));
		alSearchParams.add((String)frmOnlineAssistList.get("RespondedDate"));		
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmInvestigation,HttpServletRequest request)
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
    		setLinks(request);
    		log.debug("Inside QueryListAction doViewQueryDetails");
    		/*UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
				getAttribute("UserSecurityProfile");*/
    		DynaActionForm frmSupportQueryDetails =(DynaActionForm)form;
    		
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
			else
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.onlinequery.required");
				throw expTTK;
			}
			onlineAssistanceDetailVO= onlineAccessObject.getQueryHeaderInfo(lngQueryHdrSeqID,"SUP");
			frmSupportQueryDetails= (DynaActionForm)FormUtils.setFormValues("frmSupportQueryDetails",
					onlineAssistanceDetailVO,this,mapping,request);
			frmSupportQueryDetails.set("onlineQueryVO", FormUtils.setFormValues("frmSupportQuery",
					new OnlineQueryVO(),this,mapping,request));
			StringBuffer sbCaption = new StringBuffer();
					sbCaption.append("[ ").append(onlineAssistanceVO.getPolicyNbr()).append(" ][ ")
					.append(onlineAssistanceVO.getEnrollmentNbr()).append(" ]");
			frmSupportQueryDetails.set("caption",sbCaption.toString());
			ArrayList alQueryList=new ArrayList();
			alQueryList=onlineAssistanceDetailVO.getQueryVO();
			tableDataQueryList.createTableInfo("QuryInfoTable",alQueryList);
			tableDataQueryList.setData(alQueryList);
			request.getSession().setAttribute("tableDataQueryList",tableDataQueryList);
			request.getSession().setAttribute("frmSupportQueryDetails",frmSupportQueryDetails);
			request.getSession().setAttribute("OnlineRatingTypeID", onlineAssistanceDetailVO.getFeedbackAllowedYN());
    		return this.getForward(strQueryDetail,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strQueryError));
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
    		setLinks(request);
    		log.info("Inside QueryListAction doViewQueryInfo");
    		DynaActionForm frmSupportQueryDetails =(DynaActionForm)form;
    		OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
    		OnlineAssistanceDetailVO onlineAssistanceDetailVO = new OnlineAssistanceDetailVO();
			OnlineQueryVO onlineQueryVO = new OnlineQueryVO();
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
			frmSupportQueryDetails.set("onlineQueryVO", FormUtils.setFormValues("frmSupportQuery",
					onlineQueryVO,this,mapping,request));
			request.getSession().setAttribute("tableDataQueryList",tableDataQueryList);
			frmSupportQueryDetails.set("caption",frmSupportQueryDetails.getString("caption"));
			request.getSession().setAttribute("frmSupportQueryDetails",frmSupportQueryDetails);
    		return this.getForward(strQueryDetail,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strQueryError));
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
			
			log.info("Inside QueryListAction doSave");
			setLinks(request);
			DynaActionForm frmSupportQueryDetails =(DynaActionForm)form;
			String strCaption = frmSupportQueryDetails.getString("caption");
			SupportManager supportManager=this.getSupportManagerObject();
			OnlineAccessManager onlineAccessManager=this.getOnlineAccessManager();
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
			onlineAssistanceDetailVO=(OnlineAssistanceDetailVO)FormUtils.getFormValues(frmSupportQueryDetails,
																this,mapping,request);
			ActionForm frmSupportQuery=(ActionForm)frmSupportQueryDetails.get("onlineQueryVO");
			onlineQueryVO=(OnlineQueryVO)FormUtils.getFormValues(frmSupportQuery,"frmSupportQuery",
					this,mapping,request);
			onlineAssistanceDetailVO.setOnlineQueryVO(onlineQueryVO);
			onlineAssistanceDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			frmSupportQueryDetails.set("caption",frmSupportQueryDetails.getString("caption"));
			int intResult =supportManager.saveSupportQueryInfo(onlineAssistanceDetailVO);
			if(intResult!=0)
			{
				request.setAttribute("updated","message.savedSuccessfully");
			}//end of if(intResult!=0)
			onlineAssistanceDetailVO= onlineAccessManager.getQueryHeaderInfo(onlineAssistanceDetailVO.getQueryHdrSeqId(),"SUP");
			frmSupportQueryDetails= (DynaActionForm)FormUtils.setFormValues("frmSupportQueryDetails",onlineAssistanceDetailVO,
 					this,mapping,request);
			frmSupportQueryDetails.set("onlineQueryVO", FormUtils.setFormValues("frmSupportQuery",
					new OnlineQueryVO(),this,mapping,request));			
			frmSupportQueryDetails.set("caption",strCaption);
			ArrayList alQueryList=new ArrayList();
			alQueryList=onlineAssistanceDetailVO.getQueryVO();
			tableDataQueryList.createTableInfo("QuryInfoTable",alQueryList);
			tableDataQueryList.setData(alQueryList);
			tableDataQueryList.setSortData("0001");
			tableDataQueryList.setSortColumnName("SUBMITTED_DATE");
			tableDataQueryList.setSortOrder("DESC");
			request.getSession().setAttribute("tableDataQueryList",tableDataQueryList);
			request.getSession().setAttribute("frmSupportQueryDetails",frmSupportQueryDetails);
			return this.getForward(strQueryDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strQueryError));
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
			HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside doReset of QueryListAction");
			setLinks(request);

			DynaActionForm frmSupportQueryDetails =(DynaActionForm)form;
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
			OnlineAssistanceDetailVO onlineAssistanceDetailVO = new OnlineAssistanceDetailVO();
			OnlineQueryVO onlineQueryVO = new OnlineQueryVO();
			onlineAssistanceDetailVO.setOnlineQueryVO(onlineQueryVO);
			if(frmSupportQueryDetails.get("queryHdrSeqId")!=null && 
					!frmSupportQueryDetails.get("queryHdrSeqId").equals(""))// on clicking the edit link
			{
				onlineAssistanceDetailVO=onlineAccessObject.getQueryHeaderInfo(TTKCommon.getLong
						(frmSupportQueryDetails.getString("queryHdrSeqId")),"SUP");
			}//end of if(frmOnlineQueryDetails.get("queryHdrSeqId")!=null && 
			frmSupportQueryDetails= (DynaActionForm)FormUtils.setFormValues("frmSupportQueryDetails",
					onlineAssistanceDetailVO,this,mapping,request);
			frmSupportQueryDetails.set("onlineQueryVO", FormUtils.setFormValues("frmSupportQuery",
					new OnlineQueryVO(),this,mapping,request));
			frmSupportQueryDetails.set("caption",frmSupportQueryDetails.getString("caption"));
			ArrayList alQueryList=new ArrayList();
			alQueryList=onlineAssistanceDetailVO.getQueryVO();
			tableDataQueryList.createTableInfo("QuryInfoTable",alQueryList);
			tableDataQueryList.setData(alQueryList);
			request.getSession().setAttribute("tableDataQueryList",tableDataQueryList);
			request.getSession().setAttribute("frmSupportQueryDetails",frmSupportQueryDetails);
			return this.getForward(strQueryDetail,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strQueryError));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
			log.info("Inside the doNewQuery method of QueryListAction");
			setOnlineLinks(request);
			DynaActionForm frmSupportQueryDetails = (DynaActionForm)form;
			//OnlineAssistanceDetailVO onlineAssistanceDetailVO = new OnlineAssistanceDetailVO();
//			OnlineQueryVO onlineQueryVO = new OnlineQueryVO();
			//onlineAssistanceDetailVO.setOnlineQueryVO(onlineQueryVO);
			/*frmSupportQueryDetails= (DynaActionForm)FormUtils.setFormValues("frmSupportQueryDetails",
					onlineAssistanceDetailVO,this,mapping,request);*/
			frmSupportQueryDetails.set("onlineQueryVO", FormUtils.setFormValues("frmSupportQuery",
					new OnlineQueryVO(),this,mapping,request));
			frmSupportQueryDetails.set("caption",frmSupportQueryDetails.getString("caption"));
			request.getSession().setAttribute("frmSupportQueryDetails",frmSupportQueryDetails);
			return this.getForward(strQueryError, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strQueryError));
		}//end of catch(Exception exp)
	}//end of doNewQuery(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
    		log.info("Inside doClose of QueryListAction");
    		return doDefault(mapping,form,request,response);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strQueryError));
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
			throw new TTKException(exp, strQueryError);
		}//end of catch
		return onlineAccessManager;
	}//end getOnlineAccessManager()
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
			throw new TTKException(exp, strQueryError);
		}//end of catch
		return supportManager;
	}//end of getsupportManagerObject()
}//end of OnlineAssistanceAction