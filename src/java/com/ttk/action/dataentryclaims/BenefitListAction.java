/**
* @ (#) BenefitListAction.java
* Project       : TTK HealthCare Services
* File          : BenefitListAction.java
* Author        : Balaji C R B
* Company       : Span Systems Corporation
* Date Created  : July 02,2008

* @author       : Balaji C R B
* Modified by   :
* Modified date :
* Reason :
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
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.claims.ClaimBenefitManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.claims.ClaimBenefitVO;

/**
 * This class is used for searching of Claims
 */

public class BenefitListAction extends TTKAction
{
	private static Logger log = Logger.getLogger(BenefitListAction.class);

	//declrations of modes
	private static final String strForward="Forward";
	private static final String strBackward="Backward";

	//Action mapping forwards.
	private static final  String strClaimsBenefitlist="claimbenefitlist";

	//Exception Message Identifier
    private static final String strClaimBenefitSearchError="claimbenefitsearch";

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
    		log.debug("Inside the doDefault method of BenefitListAction");
    		setLinks(request);
    		DynaActionForm frmBenefitList = (DynaActionForm)form;
    		String strDefaultStartDate = TTKCommon.getAddedDate(-30);
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//clear the dynaform if visiting from left links for the first time
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				frmBenefitList.initialize(mapping);//reset the form data
				
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			//DynaActionForm frmBenefitList=(DynaActionForm)form;
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("BenefitListTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			frmBenefitList.initialize(mapping);//reset the form data
			frmBenefitList.set("sStartDate",strDefaultStartDate);
			frmBenefitList.set("sCaseStatus","CPE");
			return this.getForward(strClaimsBenefitlist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimBenefitSearchError));
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
    		log.debug("Inside the doSearch method of BenefitListAction");
    		setLinks(request);
    		ClaimBenefitManager claimBenefitManagerObject=this.getClaimBenfitManagerObject();
    		String strCaseStatus = ((DynaActionForm)form).getString("sCaseStatus");
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			log.debug("strCaseStatus is " + strCaseStatus);			
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strClaimsBenefitlist);
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
				tableData.createTableInfo("BenefitListTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
                tableData.modifySearchData("search");
			}//end of else
			ArrayList alClaimsBenefitList= claimBenefitManagerObject.getClaimBenefitList(tableData.getSearchData());
			tableData.setData(alClaimsBenefitList, "search");
			if("CPR".equals(strCaseStatus)){
				((Column)((ArrayList)tableData.getTitle()).get(5)).setVisibility(false);							
			}
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strClaimsBenefitlist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimBenefitSearchError));
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
    		log.debug("Inside the doBackward method of ClaimsAction");
    		setLinks(request);
    		ClaimBenefitManager claimBenefitManagerObject=this.getClaimBenfitManagerObject();
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
    		tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alClaimsBenefitList = claimBenefitManagerObject.getClaimBenefitList(tableData.getSearchData());
			tableData.setData(alClaimsBenefitList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strClaimsBenefitlist, mapping, request);   //finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimBenefitSearchError));
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
    		log.debug("Inside the doForward method of ClaimsAction");
    		setLinks(request);
    		ClaimBenefitManager claimBenefitManagerObject=this.getClaimBenfitManagerObject();
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
    		tableData.modifySearchData(strForward);//modify the search data
			ArrayList alClaimsBenefitList = claimBenefitManagerObject.getClaimBenefitList(tableData.getSearchData());
			tableData.setData(alClaimsBenefitList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strClaimsBenefitlist, mapping, request);   //finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimBenefitSearchError));
		}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to create Cash Benefit Claim
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doGenerateCashBenefitClaim(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    													HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doGenerateCashBenefitClaim method of BenefitListAction");
    		setLinks(request);
    		ClaimBenefitManager claimBenefitManagerObject=this.getClaimBenfitManagerObject();    		
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//todo tableData.getAllSelectedCheckBoxInfo();			
			String[] strCheckedArr = request.getParameterValues("chkopt");	
			DynaActionForm frmBenefitList = (DynaActionForm)form;
			String strApproveRejectFlag = frmBenefitList.getString("sApproveRejectFlag");
			ClaimBenefitVO claimBenefitVO = (ClaimBenefitVO)tableData.getRowInfo(Integer.parseInt(strCheckedArr[0]));			
			Long lngParentClaimSeqId = claimBenefitVO.getParentClaimSeqID();
			Long lngUserSeqId = TTKCommon.getUserSeqId(request);
			log.debug("claim seq id is " + lngParentClaimSeqId);
			log.debug("user seq id is " + lngUserSeqId);
			log.debug("flag is " + strApproveRejectFlag);
			ArrayList<Object> alParams = new ArrayList<Object>();
			alParams.add(lngParentClaimSeqId);
			alParams.add(lngUserSeqId);
			alParams.add(strApproveRejectFlag);
			int iResult = claimBenefitManagerObject.getCreateCashBenefitClaim(alParams);
			if(iResult>0 && "APR".equals(strApproveRejectFlag))
			{
				request.setAttribute("claimcreated","message.cashBenefitClaimApproved");
			}//end of if(iResult>0)
			else if(iResult>0 && "REJ".equals(strApproveRejectFlag)){
				request.setAttribute("claimcreated","message.cashBenefitClaimRejected");
			}//end of else if(iResult>0 && "REJ".equals(strApproveRejectFlag))
			ArrayList alClaimsBenefitList = claimBenefitManagerObject.getClaimBenefitList(tableData.getSearchData());
			tableData.setData(alClaimsBenefitList, "search");//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strClaimsBenefitlist, mapping, request);   //finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimBenefitSearchError));
		}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   
    /**
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmClaimsList formbean which contains the search fields
     * @param request HttpServletRequest
     * @return ArrayList contains search parameters
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmBenefitList,HttpServletRequest request)
    {
    	//build the column names along with their values to be searched
    	ArrayList<Object> alSearchParams = new ArrayList<Object>();
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmBenefitList.getString("sClaimNumber")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmBenefitList.getString("sClaimSettelmentNumber")));
    	alSearchParams.add((String)frmBenefitList.getString("sStartDate"));
    	alSearchParams.add((String)frmBenefitList.getString("sEndDate"));
    	if("CPE".equals(frmBenefitList.getString("sCaseStatus"))){
    		alSearchParams.add("N");
    	}//end of if("CPE".equals(frmClaimsList.getString("sCaseStatus")))
    	else if("CPR".equals(frmBenefitList.getString("sCaseStatus"))){
    		alSearchParams.add("Y");
    	}//end of else if("CPR".equals(frmClaimsList.getString("sCaseStatus"))) 
    	else {
    		alSearchParams.add("");
    	}//end of else    	    	
    	return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmClaimsList,HttpServletRequest request)

    /**
     * Returns the ClaimBenefitManager session object for invoking methods on it.
     * @return ClaimBenefitManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private ClaimBenefitManager getClaimBenfitManagerObject() throws TTKException
    {
    	ClaimBenefitManager claimBenefitManager = null;
    	try
    	{
    		if(claimBenefitManager == null)
    		{
    			InitialContext ctx = new InitialContext();
    			claimBenefitManager = (ClaimBenefitManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimBenefitManagerBean!com.ttk.business.claims.ClaimBenefitManager");
    		}//end if
    	}//end of try
    	catch(Exception exp)
    	{
    		throw new TTKException(exp, strClaimBenefitSearchError);
    	}//end of catch
    	return claimBenefitManager;
    }//end getClaimManagerObject() 
}//end of BenefitListAction