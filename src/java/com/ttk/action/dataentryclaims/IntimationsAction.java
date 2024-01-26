/**
 * @ (#) IntimationsAction.java Sep 12th, 2006
 * Project       : TTK HealthCare Services
 * File          : IntimationsAction.java
 * Author        : Krishna K H
 * Company       : Span Systems Corporation
 * Date Created  : Sep 12th, 2006
 *
 * @author       :  Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.dataentryclaims;

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
import com.ttk.business.preauth.PreAuthSupportManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.claims.ClaimIntimationVO;


/**
 * This class used for Listing of Claim Intimations.
 * This class also provides option for selection of any claim intimation.
 */

public class IntimationsAction extends TTKAction  {

	private static Logger log = Logger.getLogger( IntimationsAction.class ); // Getting Logger for this Class file

	//  Modes
	private static final String strForward="Forward";
	private static final String strBackward="Backward";
	//  Action mapping forwards
	private static final String strIntimationslist = "intimations";
	//Exception Message Identifier
	private static final String strClaimIntimationError="ClaimIntimation";

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
			log.debug("Inside the doDefault method of IntimationsAction");
			setLinks(request);
			TableData tableData=TTKCommon.getTableData(request);
			DynaActionForm frmIntimations=(DynaActionForm)form;
			String strEnrollmentID="";
			if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
					ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			{
				strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
			}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)frmIntimations).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			DynaActionForm frmIntimationsList=(DynaActionForm)form;
			StringBuffer strCaption=new StringBuffer();
			strCaption.append("[ ").append(ClaimsWebBoardHelper.getClaimantName(request)).append(" ]").append(strEnrollmentID);
			frmIntimations.set("caption",strCaption.toString());
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("IntimationsTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			frmIntimationsList.set("caption",frmIntimations.getString("caption"));
			return this.getForward(strIntimationslist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimIntimationError));
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
			log.debug("Inside the doSearch method of IntimationsAction");
			setLinks(request);
			PreAuthSupportManager preAuthSupportManager=this.getPreAuthSupManagerObject();
			TableData tableData=TTKCommon.getTableData(request);
			DynaActionForm frmIntimations=(DynaActionForm)form;
			StringBuffer strCaption=new StringBuffer();
			String strEnrollmentID="";
			if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
					ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			{
				strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
			}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&& ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			strCaption.append("[ ").append(ClaimsWebBoardHelper.getClaimantName(request)).append(" ]").append(strEnrollmentID);
			frmIntimations.set("caption",strCaption.toString());
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strIntimationslist);
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
				tableData.createTableInfo("IntimationsTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)frmIntimations,request));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alIntimationList= preAuthSupportManager.getClaimIntimationList(tableData.getSearchData());
			tableData.setData(alIntimationList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strIntimationslist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimIntimationError));
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
										HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doBackward method of IntimationsAction");
			setLinks(request);
			PreAuthSupportManager preAuthSupportManager=this.getPreAuthSupManagerObject();
			TableData tableData=TTKCommon.getTableData(request);
			tableData.modifySearchData(strBackward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			ArrayList alIntimationList = preAuthSupportManager.getClaimIntimationList(tableData.getSearchData());
			tableData.setData(alIntimationList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strIntimationslist, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimIntimationError));
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
	public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doForward method of IntimationsAction");
			setLinks(request);
			PreAuthSupportManager preAuthSupportManager=this.getPreAuthSupManagerObject();
			TableData tableData=TTKCommon.getTableData(request);
			tableData.modifySearchData(strForward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			ArrayList alIntimationList = preAuthSupportManager.getClaimIntimationList(tableData.getSearchData());
			tableData.setData(alIntimationList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strIntimationslist, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimIntimationError));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
										HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doClose method of IntimationsAction");
			setLinks(request);
			String strSupportDoc = "supportdoc";
			TableData tableData=TTKCommon.getTableData(request);
			String strRowID = TTKCommon.checkNull(request.getParameter("rownum"));
			DynaActionForm frmSupportDoc=(DynaActionForm)request.getSession().getAttribute("frmSuppDoc");
			ClaimIntimationVO claimIntimationVO = new ClaimIntimationVO();
			if(!strRowID.equals(""))
			{
				claimIntimationVO= (ClaimIntimationVO)tableData.getRowInfo(Integer.parseInt(strRowID));
				request.getSession().setAttribute("claimIntimationVO",claimIntimationVO);
			}//end of if(!strRowID.equals(""))
			frmSupportDoc.set("frmChanged","changed");
			return this.getForward(strSupportDoc, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimIntimationError));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param frmIntimation DynaActionForm
	 * @param request HttpServletRequest
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmIntimation,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		try
		{
			alSearchParams.add(ClaimsWebBoardHelper.getMemberSeqId(request));
			if(!TTKCommon.checkNull(frmIntimation.getString("hospitalName")).equals("")){
				alSearchParams.add((String)frmIntimation.get("hospitalName"));
			}//end of if(!TTKCommon.checkNull(frmIntimation.getString("hospitalName")).equals(""))
			else{
				alSearchParams.add(null);
			}//end of else
			if(!TTKCommon.checkNull(frmIntimation.getString("startDate")).equals("")){
				alSearchParams.add((String)frmIntimation.get("startDate"));
			}//end of if(!TTKCommon.checkNull(frmIntimation.getString("startDate")).equals(""))
			else{
				alSearchParams.add(null);
			}//end of else
			if(!TTKCommon.checkNull(frmIntimation.getString("endDate")).equals("")){
				alSearchParams.add((String)frmIntimation.get("endDate"));
			}//end of if(!TTKCommon.checkNull(frmIntimation.getString("endDate")).equals(""))
			else{
				alSearchParams.add(null);
			}//end of else
			alSearchParams.add(TTKCommon.getUserSeqId(request));
		}catch(Exception e){ e.printStackTrace();}
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm searchFeedbackForm,HttpServletRequest request)

	/**
	 * Returns the PreAuthManager session object for invoking methods on it.
	 * @return PreAuthManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthSupportManager getPreAuthSupManagerObject() throws TTKException
	{
		PreAuthSupportManager preAuthSupportManager = null;
		try
		{
			if(preAuthSupportManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthSupportManager = (PreAuthSupportManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthSupportManagerBean!com.ttk.business.preauth.PreAuthSupportManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strClaimIntimationError);
		}//end of catch
		return preAuthSupportManager;
	}//end getPreAuthSupManagerObject()
}//end of IntimationsAction