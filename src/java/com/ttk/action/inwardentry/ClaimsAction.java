/**
* @ (#) ClaimsAction.java July 18th, 2006
* Project      : TTK HealthCare Services
* File         : ClaimsAction.java
* Author       : Krupa J
* Company      : Span Systems Corporation
* Date Created : July 18th, 2006
*
* @author       :  Krupa J
* Modified by   :
* Modified date :
* Reason        :
*/
package com.ttk.action.inwardentry;

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
import com.ttk.business.claims.ClaimManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.claims.ClaimInwardVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This class is used for searching the List of Claims.
 * This class also provides option for Deleting the Claims.
 */
public class ClaimsAction extends TTKAction
{
	
	private static Logger log = Logger.getLogger(ClaimsAction.class );
	
	//Modes
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	
	//forwards
	private static final String strClaimsList="inwardclaimslist";
	private static final String strInwardClaim="INWARD";
	
	//Exception Message Identifier
	private static final String strPreauthExp="preauth";

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
			log.debug("Inside the doDefault method of ClaimsAction");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			DynaActionForm frmInwardClaimsList = (DynaActionForm)form;
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("InwardClaimsSearchTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			request.getSession().setAttribute("searchparam", null);
			frmInwardClaimsList.set("sTtkBranch",strDefaultBranchID);
			return this.getForward(strClaimsList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthExp));
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
			log.debug("Inside the doSearch method of ClaimsAction");
			setLinks(request);
			TableData  tableData =TTKCommon.getTableData(request);
			ClaimManager ClaimManagerObject=this.getClaimManagerObject();
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strClaimsList));
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
				tableData.createTableInfo("InwardClaimsSearchTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alClaim= ClaimManagerObject.getClaimInwardList(tableData.getSearchData());
			tableData.setData(alClaim, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strClaimsList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthExp));
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
			log.debug("Inside the doBackward method of ClaimsAction");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			ClaimManager ClaimManagerObject=this.getClaimManagerObject();
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alClaim = ClaimManagerObject.getClaimInwardList(tableData.getSearchData());
			tableData.setData(alClaim, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strClaimsList, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthExp));
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
			log.debug("Inside the doForward method of ClaimsAction");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			ClaimManager ClaimManagerObject=this.getClaimManagerObject();
			tableData.modifySearchData(strForward);//modify the search data
			ArrayList alClaim = ClaimManagerObject.getClaimInwardList(tableData.getSearchData());
			tableData.setData(alClaim, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strClaimsList, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthExp));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside the doDeleteList method of ClaimsAction");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			ClaimManager ClaimManagerObject=this.getClaimManagerObject();
			StringBuffer sbfDeleteId = new StringBuffer("|");
			int iCount=0;
			sbfDeleteId.append(populateDeleteId(request,(TableData)request.getSession().getAttribute("tableData")));
			ArrayList <Object>alDeleteMode=new ArrayList<Object>();
			alDeleteMode.add(strInwardClaim);
			alDeleteMode.add(sbfDeleteId.toString());
			alDeleteMode.add(null);//PAT_ENROLL_DETAIL_SEQ_ID is not required.
			alDeleteMode.add(null);//PAT_GENERAL_DETAIL_SEQ_ID is not required
			alDeleteMode.add(TTKCommon.getUserSeqId(request));//User id
			//delete the selected preauth based on the flow
			iCount = ClaimManagerObject.deleteClaimGeneral(alDeleteMode);
			//refresh the grid with search data in session
			ArrayList alClaim = null;
			//fetch the data from previous set of rowcounts, if all the records are deleted for the current set of search criteria
			if(iCount == tableData.getData().size())
			{
				tableData.modifySearchData("Delete");//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alClaim = ClaimManagerObject.getClaimInwardList(tableData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				alClaim = ClaimManagerObject.getClaimInwardList(tableData.getSearchData());
			}//end of else
			tableData.setData(alClaim,"Delete");
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strClaimsList, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthExp));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the ClaimManager session object for invoking methods on it.
	 * @return PreAuthManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ClaimManager getClaimManagerObject() throws TTKException
	{
		ClaimManager claimManager = null;
		try
		{
			if(claimManager == null)
			{
				InitialContext ctx = new InitialContext();
				claimManager = (ClaimManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimManagerBean!com.ttk.business.claims.ClaimManager");
			}//end of if(claimManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "preauth");
		}//end of catch
		return claimManager;
	}//end getClaimManagerObject()
	
	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmEnrollSearch DynaActionForm
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmInwardClaimsList,HttpServletRequest request)throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInwardClaimsList.get("sInwardNbr")));				
		alSearchParams.add((String)frmInwardClaimsList.get("sClaimType"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInwardClaimsList.get("sStartDate")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInwardClaimsList.get("sEndDate")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInwardClaimsList.get("sEnrollmentID")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInwardClaimsList.get("sGroupName")));
		alSearchParams.add((String)frmInwardClaimsList.get("sInwardStatus"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInwardClaimsList.get("sTtkBranch")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInwardClaimsList.get("sClaimNo")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInwardClaimsList.get("sSchemeName")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInwardClaimsList.get("sCertificateNo")));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmEnrollSearch)
	
	/**
	 * This method returns a string which contains the comma separated sequence id's to be deleted,
	 * in Enrollmemt flow pipe seperated policy seq ids and in endorsement flow pipe seperated endorsement seq ids
	 * are sent to the called method
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param policyListData TableData object which contains the value objects
	 * @return String which contains the comma separated sequence id's to be deleted
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableData)
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			//loop through to populate delete sequence id's and get the value from session for the matching check box value
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append(String.valueOf(((ClaimInwardVO)tableData.getData().get(Integer.parseInt(strChk[i]))).getInwardSeqID()));
					}// end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((ClaimInwardVO)tableData.getData().get(Integer.parseInt(strChk[i]))).getInwardSeqID()));
					}// end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)
	
}//end of ClaimsAction
