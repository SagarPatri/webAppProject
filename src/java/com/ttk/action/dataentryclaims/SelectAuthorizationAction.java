/**
 * @ (#)  SelectAuthorizationAction.java July 17,2006
 * Project      : TTK HealthCare Services
 * File         : SelectAuthorizationAction.java
 * Author       : Harsha Vardhan B N
 * Company      : Span Systems Corporation
 * Date Created : July 17,2006
 *
 * @author       :
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
import com.ttk.business.dataentryclaims.ParallelClaimManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.preauth.PreAuthDetailVO;
import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of Authorization.
 * This class also provides option selecting the any authorization.
 */

public class SelectAuthorizationAction extends TTKAction {
	private static Logger log = Logger.getLogger( SelectAuthorizationAction.class );

	//   Modes.
	private static final String strBackward="Backward";
	private static final String strForward="Forward";

	//  Action mapping forwards.
	private static final String strAuthList="dataentryauthlist";
	private static final String strClaimsGeneral="dataentryclaimsgeneral";

	//Exception Message Identifier
	private static final String strAuthSearchError="AuthSearch";

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
			log.debug("Inside the doDefault method of SelectAuthorizationAction");
			setLinks(request);
			DynaActionForm frmSelectAuth =(DynaActionForm)form;
			StringBuffer strCaption= new StringBuffer();
			TableData tableData = TTKCommon.getTableData(request);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
			if(frmPreAuthGeneral!=null)
			{
				DynaActionForm frmClaimantDetails=(DynaActionForm)frmPreAuthGeneral.get("claimantDetailsVO");
				frmSelectAuth.set("sEnrollTypeID",(String)frmClaimantDetails.get("enrollmentID"));
				strCaption.append(" - [").append(ClaimsWebBoardHelper.getClaimantName(request)).append("]").
									append(" [").append((String)frmClaimantDetails.get("enrollmentID")).append("]");
			}//end of if(frmPreAuthGeneral!=null)
			else{
				strCaption.append(" - [").append(ClaimsWebBoardHelper.getClaimantName(request)).append("]");
			}//end of else
			if(ClaimsWebBoardHelper.getClaimantName(request)!=null)
			{
				frmSelectAuth.set("sClaimantName",(String)ClaimsWebBoardHelper.getClaimantName(request));
			}//end of if(ClaimsWebBoardHelper.getClaimantName(request)!=null)
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("SelectAuthorizationTable",new ArrayList());
			frmSelectAuth.set("caption",strCaption.toString());
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strAuthList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAuthSearchError));
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
			log.debug("Inside the doSearch method of SelectAuthorizationAction");
			setLinks(request);
			StringBuffer strCaption= new StringBuffer();
			ParallelClaimManager claimManagerObject=this.getClaimManagerObject();
			DynaActionForm frmSelectAuth =(DynaActionForm)form;
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
			if(frmPreAuthGeneral!=null)
			{
				DynaActionForm frmClaimantDetails=(DynaActionForm)frmPreAuthGeneral.get("claimantDetailsVO");
				frmSelectAuth.set("sEnrollTypeID",(String)frmClaimantDetails.get("enrollmentID"));
				strCaption.append(" - [").append(ClaimsWebBoardHelper.getClaimantName(request)).append("]").
									append(" [").append((String)frmClaimantDetails.get("enrollmentID")).append("]");
			}//end of if(frmPreAuthGeneral!=null)
			else{
				strCaption.append(" - [").append(ClaimsWebBoardHelper.getClaimantName(request)).append("]");
			}//end of else
			if(ClaimsWebBoardHelper.getClaimantName(request)!=null)
			{
				frmSelectAuth.set("sClaimantName",(String)ClaimsWebBoardHelper.getClaimantName(request));
			}//end of if(ClaimsWebBoardHelper.getClaimantName(request)!=null)
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strAuthList));
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				// create the required grid table
				tableData.createTableInfo("SelectAuthorizationTable",null);
				//fetch the data from the data access layer and set the data to table object
				tableData.setSearchData(this.populateSearchCriteria(frmSelectAuth,request));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alAuthList=claimManagerObject.getPreauthList(tableData.getSearchData());
			tableData.setData(alAuthList, "search");
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strAuthList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAuthSearchError));
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
			log.debug("Inside the doBackward method of SelectAuthorizationAction");
			setLinks(request);
			StringBuffer strCaption= new StringBuffer();
			ParallelClaimManager claimManagerObject=this.getClaimManagerObject();
			DynaActionForm frmSelectAuth =(DynaActionForm)form;
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
			if(frmPreAuthGeneral!=null)
			{
				DynaActionForm frmClaimantDetails=(DynaActionForm)frmPreAuthGeneral.get("claimantDetailsVO");
				frmSelectAuth.set("sEnrollTypeID",(String)frmClaimantDetails.get("enrollmentID"));
				strCaption.append(" - [").append(ClaimsWebBoardHelper.getClaimantName(request)).append("]").
								append(" [").append((String)frmClaimantDetails.get("enrollmentID")).append("]");
			}//end of if(frmPreAuthGeneral!=null)
			else{
				strCaption.append(" - [").append(ClaimsWebBoardHelper.getClaimantName(request)).append("]");
			}//end of else
			if(ClaimsWebBoardHelper.getClaimantName(request)!=null)
			{
				frmSelectAuth.set("sClaimantName",(String)ClaimsWebBoardHelper.getClaimantName(request));
			}//end of if(ClaimsWebBoardHelper.getClaimantName(request)!=null)
			tableData.modifySearchData(strBackward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			ArrayList alAuthList = claimManagerObject.getPreauthList(tableData.getSearchData());
			tableData.setData(alAuthList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strAuthList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAuthSearchError));
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
			log.debug("Inside the doForward method of SelectAuthorizationAction");
			setLinks(request);
			StringBuffer strCaption= new StringBuffer();
			ParallelClaimManager claimManagerObject=this.getClaimManagerObject();
			DynaActionForm frmSelectAuth =(DynaActionForm)form;
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
			if(frmPreAuthGeneral!=null)
			{
				DynaActionForm frmClaimantDetails=(DynaActionForm)frmPreAuthGeneral.get("claimantDetailsVO");
				frmSelectAuth.set("sEnrollTypeID",(String)frmClaimantDetails.get("enrollmentID"));
				strCaption.append(" - [").append(ClaimsWebBoardHelper.getClaimantName(request)).append("]").
									append(" [").append((String)frmClaimantDetails.get("enrollmentID")).append("]");
			}//end of if(frmPreAuthGeneral!=null)
			else{
				strCaption.append(" - [").append(ClaimsWebBoardHelper.getClaimantName(request)).append("]");
			}//end of else
			if(ClaimsWebBoardHelper.getClaimantName(request)!=null)
			{
				frmSelectAuth.set("sClaimantName",(String)ClaimsWebBoardHelper.getClaimantName(request));
			}//end of if(ClaimsWebBoardHelper.getClaimantName(request)!=null)
			tableData.modifySearchData(strForward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			ArrayList alAuthList = claimManagerObject.getPreauthList(tableData.getSearchData());
			tableData.setData(alAuthList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strAuthList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAuthSearchError));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to get the Selected Record's details.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSelectAuth method of SelectAuthorizationAction");
			setLinks(request);
			StringBuffer strCaption= new StringBuffer();
			DynaActionForm frmSelectAuth =(DynaActionForm)form;
			PreAuthDetailVO preAuthDetailVO = null;
			Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
			if(frmPreAuthGeneral!=null)
			{
				DynaActionForm frmClaimantDetails=(DynaActionForm)frmPreAuthGeneral.get("claimantDetailsVO");
				frmSelectAuth.set("sEnrollTypeID",(String)frmClaimantDetails.get("enrollmentID"));
				strCaption.append(" - [").append(ClaimsWebBoardHelper.getClaimantName(request)).append("]").
									append(" [").append((String)frmClaimantDetails.get("enrollmentID")).append("]");
			}//end of if(frmPreAuthGeneral!=null)
			else{
				strCaption.append(" - [").append(ClaimsWebBoardHelper.getClaimantName(request)).append("]");
			}//end of else
			if(ClaimsWebBoardHelper.getClaimantName(request)!=null)
			{
				frmSelectAuth.set("sClaimantName",(String)ClaimsWebBoardHelper.getClaimantName(request));
			}//end of if(ClaimsWebBoardHelper.getClaimantName(request)!=null)
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				preAuthDetailVO=(PreAuthDetailVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				frmPreAuthGeneral.set("preAuthHospitalVO",FormUtils.setFormValues("frmPreAuthHospital",
														preAuthDetailVO.getPreAuthHospitalVO(),this,mapping,request));
				if(preAuthDetailVO.getEnrollDtlSeqID()!=null)
				{
					frmPreAuthGeneral.set("enrollDtlSeqID",preAuthDetailVO.getEnrollDtlSeqID().toString());
				}//end of if(preAuthDetailVO.getEnrollDtlSeqID()!=null)
				if(preAuthDetailVO.getPreAuthSeqID()!=null)
				{
					frmPreAuthGeneral.set("preAuthSeqID",preAuthDetailVO.getPreAuthSeqID().toString());
				}//end of if(preAuthDetailVO.getPreAuthSeqID()!=null)
				frmPreAuthGeneral.set("authNbr",preAuthDetailVO.getAuthNbr());
				frmPreAuthGeneral.set("prevHospClaimSeqID","");
				frmPreAuthGeneral.set("approvedAmt",preAuthDetailVO.getApprovedAmt().toString());
				frmPreAuthGeneral.set("preauthTypeDesc",preAuthDetailVO.getPreauthTypeDesc());
				frmPreAuthGeneral.set("receivedDate",TTKCommon.getFormattedDate(preAuthDetailVO.getReceivedDate()));
				frmPreAuthGeneral.set("receivedTime",preAuthDetailVO.getReceivedTime());
				frmPreAuthGeneral.set("receivedDay",preAuthDetailVO.getReceivedDay());
				frmPreAuthGeneral.set("frmChanged","changed");
				request.getSession().removeAttribute("frmSelectAuth");
				request.getSession().removeAttribute("tableData");
			}//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			toolBar.getConflictIcon().setVisible(toolBar.getConflictIcon().isVisible() &&
													frmPreAuthGeneral.get("discPresentYN").equals("Y"));
			return mapping.findForward(strClaimsGeneral);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAuthSearchError));
		}//end of catch(Exception exp)
	}//end of doSelectAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  //HttpServletResponse response)

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
			log.debug("Inside the doClose method of SelectAuthorizationAction");
			setLinks(request);
			DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
			Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");
			toolBar.getConflictIcon().setVisible(toolBar.getConflictIcon().isVisible() &&
													frmPreAuthGeneral.get("discPresentYN").equals("Y"));
			return mapping.findForward(strClaimsGeneral);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAuthSearchError));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


	/**
	 * Returns the ArrayList which contains the populated search criteria elements
	 * @param frmSelectAuth DynaActionForm will contains the values of corresponding fields
	 * @param request HttpServletRequest object which contains the search parameter that is built
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSelectAuth,HttpServletRequest request)throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add((String)frmSelectAuth.get("sEnrollTypeID"));
		alSearchParams.add((String)frmSelectAuth.get("sClaimantName"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSelectAuth.get("sAuthNo")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSelectAuth.get("sHospitalName")));
		alSearchParams.add((String)frmSelectAuth.get("sPreAuthType"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSelectAuth.get("sStartDate")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSelectAuth.get("sEndDate")));
		alSearchParams.add((String)frmSelectAuth.get("sTtkBranch"));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		alSearchParams.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmSelectAuth,HttpServletRequest request)

	/**
	 * Returns the ParallelClaimManager session object for invoking methods on it.
	 * @return ParallelClaimManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ParallelClaimManager getClaimManagerObject() throws TTKException
	{
		ParallelClaimManager claimManager = null;
		try
		{
			if(claimManager == null)
			{
				InitialContext ctx = new InitialContext();
				claimManager = (ParallelClaimManager) ctx.lookup("java:global/TTKServices/business.ejb3/ParallelClaimManagerBean!com.ttk.business.dataentryclaims.ParallelClaimManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strAuthSearchError);
		}//end of catch
		return claimManager;
	}//end getClaimManagerObject()
}//end SelectAuthorizationAction