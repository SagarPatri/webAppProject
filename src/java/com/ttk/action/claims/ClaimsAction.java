/**
* @ (#) ClaimsAction.java Jul 14, 2006
* Project       : TTK HealthCare Services
* File          : ClaimsAction.java
* Author        : Chandrasekaran J
* Company       : Span Systems Corporation
* Date Created  : Jul 14, 2006

* @author       : Chandrasekaran J
* Modified by   :
* Modified date :
* Reason :
*/

package com.ttk.action.claims;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.claims.ClaimManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.preauth.ActivityDetailsVO;
import com.ttk.dto.preauth.ClinicianDetailsVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.preauth.ProviderDetailsVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of Claims
 */

public class ClaimsAction extends TTKAction
{
	private static Logger log = Logger.getLogger(ClaimsAction.class);

	//declrations of modes
	private static final String strForward="Forward";
	private static final String strBackward="Backward";

	//Action mapping forwards.
	private static final  String strClaimslist="claimslist";
	private static final  String strClaimDetail="claimdetail";

    private static final int ASSIGN_ICON=7;

    //Exception Message Identifier
    private static final String strClaimSearchError="hospitalsearch";
    private static final String strProviderList="providerSearchList";
	private static final String strClinicianList="clinicianSearchList";
	private static final String strDiagnosisCodeList="diagnosisSearchList";
	private static final String strActivityCodeList="activityCodeList";
	private static final String strActivitydetails="activitydetails";
	private static final String strMemberList="memberSearchList";
	
	
	
	/**

    /**
     * This method is used to initialize the search grid.
     * Finally it forwards to the appropriate view based on the specified forward mappings
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
    		log.debug("Inside the doDefault method of ClaimsAction");
    		setLinks(request);
    		String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
    										request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//clear the dynaform if visiting from left links for the first time
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			DynaActionForm frmClaimsList=(DynaActionForm)form;
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("ClaimsTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			frmClaimsList.set("sTtkBranch",strDefaultBranchID);
			return this.getForward(strClaimslist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimSearchError));
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
    		log.debug("Inside the doSearch method of ClaimsAction");
    		setLinks(request);
    		ClaimManager claimManagerObject=this.getClaimManagerObject();
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//PreAuthVO preAuthVO=new PreAuthVO;
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
					return mapping.findForward(strClaimslist);
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
				tableData.createTableInfo("ClaimsTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
                this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
				tableData.modifySearchData("search");
			}//end of else
			
			
			ArrayList alClaimsList= claimManagerObject.getClaimList(tableData.getSearchData());
			tableData.setData(alClaimsList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strClaimslist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimSearchError));
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
    		ClaimManager claimManagerObject=this.getClaimManagerObject();
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
    		tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alClaimsList = claimManagerObject.getClaimList(tableData.getSearchData());
			tableData.setData(alClaimsList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strClaimslist, mapping, request);   //finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimSearchError));
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
    		ClaimManager claimManagerObject=this.getClaimManagerObject();
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
    		tableData.modifySearchData(strForward);//modify the search data
			ArrayList alClaimsList = claimManagerObject.getClaimList(tableData.getSearchData());
			tableData.setData(alClaimsList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strClaimslist, mapping, request);   //finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimSearchError));
		}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to copy the selected records to web-board.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    													HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doCopyToWebBoard method of ClaimsAction");
    		setLinks(request);
    		this.populateWebBoard(request);
			return this.getForward(strClaimslist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimSearchError));
		}//end of catch(Exception exp)
    }//end of doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doView method of ClaimsAction");
    		setLinks(request);
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
    		DynaActionForm frmClaimsList=(DynaActionForm)form;
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				PreAuthVO preAuthVO=(PreAuthVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				this.addToWebBoard(preAuthVO, request);
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward(strClaimDetail);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimSearchError));
		}//end of catch(Exception exp)
    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmClaimsList formbean which contains the search fields
     * @param request HttpServletRequest
     * @return ArrayList contains search parameters
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmClaimsList,HttpServletRequest request)
    {
    	//build the column names along with their values to be searched
    	ArrayList<Object> alSearchParams = new ArrayList<Object>();
    	alSearchParams.add(frmClaimsList.getString("sInvoiceNO"));
    	alSearchParams.add(frmClaimsList.getString("sBatchNO"));
    	alSearchParams.add(frmClaimsList.getString("sPolicyNumber"));
    	alSearchParams.add(frmClaimsList.getString("sClaimNO"));
    	alSearchParams.add(frmClaimsList.getString("sClaimType"));
    	alSearchParams.add(frmClaimsList.getString("sRecievedDate"));
    	alSearchParams.add(frmClaimsList.getString("sSettlementNO"));
    	alSearchParams.add(frmClaimsList.getString("sEnrollmentId"));
    	alSearchParams.add(frmClaimsList.getString("sClaimantName"));
		alSearchParams.add(frmClaimsList.getString("sStatus"));
		alSearchParams.add(frmClaimsList.getString("sProviderName"));
		alSearchParams.add(frmClaimsList.getString("sPayerName"));
		alSearchParams.add(frmClaimsList.getString("sModeOfClaim"));
		alSearchParams.add(frmClaimsList.getString("sGlobalNetMemID"));
		alSearchParams.add(TTKCommon.getUserSeqId(request));	
		alSearchParams.add(frmClaimsList.getString("sSpecifyName"));
		alSearchParams.add(frmClaimsList.getString("spreauthCategory"));
		alSearchParams.add(frmClaimsList.getString("sEmiratesID"));		
		alSearchParams.add(frmClaimsList.getString("subBatchID"));
		alSearchParams.add(frmClaimsList.getString("sProcessType"));
		alSearchParams.add(frmClaimsList.getString("dhpoMemberId"));
		
    	return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmClaimsList,HttpServletRequest request)
    
    
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
	public ActionForward providerSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside ClaimAction providerSearch");
			setLinks(request);
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			TableData providerListData = null;
			if((request.getSession()).getAttribute("providerListData") != null)
			{
				providerListData = (TableData)(request.getSession()).getAttribute("providerListData");
			}//end of if((request.getSession()).getAttribute("icdListData") != null)
			else
			{
				providerListData = new TableData();
			}//end of else
			
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));		
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(strPageID.equals(""))
				{
					providerListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					providerListData.modifySearchData("sort");//modify the search data                    
				}///end of if(!strPageID.equals(""))
				else
				{
				log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
				providerListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return this.getForward(strProviderList, mapping, request);
				}//end of else
	          }//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else{
				providerListData.createTableInfo("ProviderListTable",null);
				providerListData.setSearchData(this.populateProvidersSearchCriteria((DynaActionForm)form,request));
				providerListData.modifySearchData("search");				
			}//end of else
			
			ArrayList alProviderList=null;
			alProviderList= preAuthObject.getProviderList(providerListData.getSearchData());
		     int count = 0;
		     count++;
		     request.getSession().setAttribute("count",count);
			providerListData.setData(alProviderList, "search");
			//set the table data object to session
			request.getSession().setAttribute("providerListData",providerListData);
			return this.getForward(strProviderList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProviderList));
		}//end of catch(Exception exp)
	}//end of providerSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward doSelectProviderId(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	try{
	setLinks(request);
	log.debug("Inside ClaimAction doSelectProviderId ");
	HttpSession session=request.getSession();
	TableData providerListData = (TableData)session.getAttribute("providerListData");

	if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals(""))){
		System.out.println("Vikram==="+Integer.parseInt((String)request.getParameter("rownum")));
		System.out.println("Vikram1==="+providerListData.getRowInfo(0));
		ProviderDetailsVO providerDetailsVO=(ProviderDetailsVO)providerListData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));

	DynaActionForm frmClaimGeneral=(DynaActionForm)session.getAttribute("frmClaimGeneral");
	String claimSeqID=(String)frmClaimGeneral.get("claimSeqID");

	    frmClaimGeneral.set("providerSeqId", providerDetailsVO.getProviderSeqId().toString());
	    frmClaimGeneral.set("providerId", providerDetailsVO.getProviderId());
	    frmClaimGeneral.set("providerName", providerDetailsVO.getProviderName());
	 
	    frmClaimGeneral.set("provAuthority", providerDetailsVO.getProviderAuthority());
	    frmClaimGeneral.set("providerSpecificRemarks", providerDetailsVO.getProviderSpecificRemarks());
	    frmClaimGeneral.set("providerType", providerDetailsVO.getProviderType());
	    frmClaimGeneral.set("vatTrnCode",providerDetailsVO.getVatTrnCode());
	    frmClaimGeneral.set("requestedAmountcurrencyType",providerDetailsVO.getCurencyType());
	    session.setAttribute("frmClaimGeneral",frmClaimGeneral);
		if(!(claimSeqID==null||claimSeqID.length()<1)){
			session.setAttribute("claimDiagnosis", null);
			session.setAttribute("claimActivities", null);
			session.setAttribute("claimShortfalls", null);	
			session.setAttribute("ceedResponse", null);	
			session.setAttribute("claimEditNode", null);	
			
		}
		
	}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	return this.getForward(strClaimDetail, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
	return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
	return this.processExceptions(request, mapping, new TTKException(exp,strProviderList));
	}//end of catch(Exception exp)
	}//end of doSelectProviderId(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPreAuthList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateProvidersSearchCriteria(DynaActionForm frmPreAuthList,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sProviderId")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sProviderName")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateActivityCodeSearchCriteria(DynaActionForm frmProductList,HttpServletRequest request)

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
	public ActionForward doProviderForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside ClaimAction doProviderForward");
			setLinks(request);
			TableData providerListData = (TableData)request.getSession().getAttribute("providerListData");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			providerListData.modifySearchData(strForward);//modify the search data
			ArrayList alProviderList = preAuthObject.getProviderList(providerListData.getSearchData());
			providerListData.setData(alProviderList, strForward);//set the table data
			request.getSession().setAttribute("providerListData",providerListData);   //set the table data object to session
			return this.getForward(strProviderList, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProviderList));
		}//end of catch(Exception exp)
	}//end of doProviderForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
	public ActionForward doProviderBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside ClaimAction doProviderBackward");
			setLinks(request);
			TableData providerListData = (TableData)request.getSession().getAttribute("providerListData");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			providerListData.modifySearchData(strBackward);//modify the search data
			ArrayList alProviderList = preAuthObject.getProviderList(providerListData.getSearchData());
			providerListData.setData(alProviderList, strBackward);//set the table data
			request.getSession().setAttribute("providerListData",providerListData);   //set the table data object to session
			return this.getForward(strProviderList, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProviderList));
		}//end of catch(Exception exp)
	}//end of doProviderBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
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
	public ActionForward clinicianSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside ClaimAction clinicianSearch");
			setLinks(request);
			HttpSession session=request.getSession();
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			TableData clinicianListData = null;
			DynaActionForm frmPreAuthList=(DynaActionForm)form;
			
			
			if("Y".equals(request.getParameter("Entry"))){
				//frmPreAuthList.initialize(mapping);
				DynaActionForm	frmClaimGeneral=(DynaActionForm)session.getAttribute("frmClaimGeneral");
				
				//String strproviderId = frmClaimGeneral.getString("providerId").toString();
				//String strproviderType = frmClaimGeneral.getString("providerType").toString();
				
				
				
				 String strproviderId = frmClaimGeneral.getString("providerId");
                 String strproviderType = frmClaimGeneral.getString("providerType");
		
				
				int m = strproviderId.indexOf("(");
			 
				     if(m>0)
				     {
				       strproviderId =	strproviderId.substring(0, m);
				         if(strproviderType!=null && strproviderType.length()!=0)
				         {
				        	 
				        if(strproviderType.equals("PHR") || strproviderType.equals("OPT") || strproviderType.equals("DCN"))
				        {
				        	session.setAttribute("providerType", strproviderType);
				        }
				        else 
				        {
				        	session.setAttribute("providerType", "");
				        }
				         }
				        
				     }
				     else{
				    	 
				    	 if((strproviderType !=null)&&(strproviderType.equals("PHR") || strproviderType.equals("OPT") || strproviderType.equals("DCN")))
				    	 {
				    		 session.setAttribute("providerType", strproviderType);
				    	 }
				    	 else
				    	 {
				    		 session.setAttribute("providerType", "");
				    	 }	 
				     }
				  
				     frmPreAuthList.set("sProviderId",strproviderId);
				     frmPreAuthList.set("sProviderName",frmClaimGeneral.getString("providerName"));
				     frmPreAuthList.set("sProviderType", strproviderType);
			}
			if((request.getSession()).getAttribute("clinicianListData") != null)
			{
				clinicianListData = (TableData)session.getAttribute("clinicianListData");
			}//end of if((request.getSession()).getAttribute("icdListData") != null)
			else
			{
				clinicianListData = new TableData();
			}//end of else
			
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));		
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(strPageID.equals(""))
				{
					clinicianListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					clinicianListData.modifySearchData("sort");//modify the search data                    
				}///end of if(!strPageID.equals(""))
				else
				{
				log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
				clinicianListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return this.getForward(strClinicianList, mapping, request);
				}//end of else
	          }//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else{
				clinicianListData.createTableInfo("ClinicianListTable",null);
				clinicianListData.setSearchData(this.populateClinicianSearchCriteria(frmPreAuthList,request));
				clinicianListData.modifySearchData("search");				
			}//end of else
			
			ArrayList alClinicianList=null;
			alClinicianList= preAuthObject.getClinicianList(clinicianListData.getSearchData());
			clinicianListData.setData(alClinicianList, "search");
			//set the table data object to session
			session.setAttribute("clinicianListData",clinicianListData);
			return this.getForward(strClinicianList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClinicianList));
		}//end of catch(Exception exp)
	}//end of clinicianSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


	public ActionForward doSelectClinicianId(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	try{
	setLinks(request);
	log.debug("Inside ClaimAction doSelectclinicianId ");
	HttpSession session=request.getSession();
	TableData clinicianListData = (TableData)session.getAttribute("clinicianListData");
String forward=strClaimDetail;
	if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	{
		ClinicianDetailsVO clinicianDetailsVO=(ClinicianDetailsVO)clinicianListData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
		
		if("activityClinicianSearch".equals(session.getAttribute("forwardMode"))){
			DynaActionForm frmActivityDetails=(DynaActionForm)session.getAttribute("frmActivityDetails");
			frmActivityDetails.set("clinicianId", clinicianDetailsVO.getClinicianId());
			session.setAttribute("frmActivityDetails",frmActivityDetails);
			forward="activitydetails";
		}else{
		
		DynaActionForm frmClaimGeneral=(DynaActionForm)session.getAttribute("frmClaimGeneral");
		String claimSeqID=frmClaimGeneral.getString("claimSeqID");
			
		frmClaimGeneral.set("clinicianId", clinicianDetailsVO.getClinicianId());
		frmClaimGeneral.set("clinicianName", clinicianDetailsVO.getClinicianName());
		frmClaimGeneral.set("consultationType", clinicianDetailsVO.getClinicianConsultation());
		if(memberAgeValidation(frmClaimGeneral)){
			
			if("".equals(frmClaimGeneral.getString("memberWeight")))
			{
			frmClaimGeneral.set("memberwtflag", "Y");
			
			ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = new ActionMessage("error.member.patient");
            actionMessages.add("global.error",actionMessage);
            saveErrors(request,actionMessages);
            session.setAttribute("frmClaimGeneral", frmClaimGeneral);
            return this.getForward("claimdetail", mapping, request);
			}
			
		}
		else if(memberAgeValidation(frmClaimGeneral)==false)
		{
			frmClaimGeneral.set("memberwtflag", "N");
		}
		   session.setAttribute("frmClaimGeneral",frmClaimGeneral);
			if(!(claimSeqID==null||claimSeqID.length()<1)){
				session.setAttribute("claimDiagnosis", null);
				session.setAttribute("claimActivities", null);
				session.setAttribute("claimShortfalls", null);	
				session.setAttribute("ceedResponse", null);	
				session.setAttribute("claimEditNode", null);		
			}
			forward=strClaimDetail;
		}
	}
	return this.getForward(forward, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
	return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
	return this.processExceptions(request, mapping, new TTKException(exp,strClinicianList));
	}//end of catch(Exception exp)
	}//end of doSelectclinicianId(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

/**
 * closeClinicians forward 
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward closeClinicians(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{
log.debug("Inside ClaimsAction closeClinicians");

	return mapping.findForward("activitydetails");
}//end of try
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,"preauthdetail"));
}//end of catch(Exception exp)
}//end of closeClinicians(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
public ActionForward activityCodeSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
	try{
		log.debug("Inside ClaimsAction activityCodeSearch");
		/*System.out.println("Inside ClaimsAction activityCodeSearch");*/
		setLinks(request);
		PreAuthManager preAuthObject=this.getPreAuthManagerObject();
		HttpSession session=request.getSession();
		DynaActionForm frmActivityDetails=(DynaActionForm)session.getAttribute("frmActivityDetails");
		TableData activityCodeListData = null;
		if(session.getAttribute("activityCodeListData") != null)
		{
			activityCodeListData = (TableData)session.getAttribute("activityCodeListData");
		}//end of if((request.getSession()).getAttribute("icdListData") != null)
		else
		{
			activityCodeListData = new TableData();
		}//end of else
		
		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
		//if the page number or sort id is clicked
		if(!strPageID.equals("") || !strSortID.equals(""))
		{
			if(strPageID.equals(""))
			{
				activityCodeListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				activityCodeListData.modifySearchData("sort");//modify the search data                    
			}///end of if(!strPageID.equals(""))
			else
			{
			log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
			activityCodeListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
			return this.getForward(strActivityCodeList, mapping, request);
			}//end of else
          }//end of if(!strPageID.equals("") || !strSortID.equals(""))
		else{
			activityCodeListData.createTableInfo("ActivityCodeListTable",null);
			activityCodeListData.setSearchData(this.populateActivityCodeSearchCriteria(frmActivityDetails.getString("claimSeqID"),(DynaActionForm)form,request));
			activityCodeListData.modifySearchData("search");				
		}//end of else
		
		ArrayList alActivityCodeList=null;
		alActivityCodeList= preAuthObject.getActivityCodeList(activityCodeListData.getSearchData());
		activityCodeListData.setData(alActivityCodeList, "search");
		//set the table data object to session
		request.getSession().setAttribute("activityCodeListData",activityCodeListData);
		return this.getForward(strActivityCodeList, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp,strActivityCodeList));
	}//end of catch(Exception exp)
}//end of activityCodeSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
/**
 * this method will add search criteria fields and values to the arraylist and will return it
 * @param frmPreAuthList formbean which contains the search fields
 * @param request HttpServletRequest
 * @return ArrayList contains search parameters
 */
private ArrayList<Object> populateActivityCodeSearchCriteria(String claimSeqID,DynaActionForm frmActivitiesList,HttpServletRequest request)
{
	//build the column names along with their values to be searched
	ArrayList<Object> alSearchParams = new ArrayList<Object>();
	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivitiesList.getString("sActivityCode")));
	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivitiesList.getString("sActivityCodeDesc")));
	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivitiesList.getString("sSearchType")));
	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivitiesList.getString("sAuthType")));
	alSearchParams.add(claimSeqID);
	alSearchParams.add(TTKCommon.getUserSeqId(request));
	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivitiesList.getString("sCategory")));
	return alSearchParams;
}//end of populateActivityCodeSearchCriteria(DynaActionForm frmProductList,HttpServletRequest request)




public ActionForward doSelectActivityCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{
	/*System.out.println(".........doSelectActivityCode.....");*/
	setLinks(request);
	log.debug("Inside ClaimsAction doSelectActivityCode ");

	if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	{
		request.setAttribute("rownum",request.getParameter("rownum"));
	  }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	((DynaActionForm)form).initialize(mapping);
	return mapping.findForward(strActivitydetails);
	}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strActivityCodeList));
}//end of catch(Exception exp)
}//end of doSelectActivityCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
public ActionForward doActivityCodeForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
	try{
		log.debug("Inside ClaimsAction doActivityCodeForward");
		setLinks(request);
		TableData activityCodeListData = (TableData)request.getSession().getAttribute("activityCodeListData");
		PreAuthManager preAuthObject=this.getPreAuthManagerObject();
		activityCodeListData.modifySearchData(strForward);//modify the search data
		ArrayList alPreauthList = preAuthObject.getActivityCodeList(activityCodeListData.getSearchData());
		activityCodeListData.setData(alPreauthList, strForward);//set the table data
		request.getSession().setAttribute("activityCodeListData",activityCodeListData);   //set the table data object to session
		return this.getForward(strActivityCodeList, mapping, request);   //finally return to the grid screen
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp,strActivityCodeList));
	}//end of catch(Exception exp)
}//end of doActivityCodeForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
public ActionForward doActivityCodeBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
	try{
		log.debug("Inside ClaimsAction doActivityCodeBackward");
		setLinks(request);
		TableData activityCodeListData = (TableData)request.getSession().getAttribute("activityCodeListData");
		PreAuthManager preAuthObject=this.getPreAuthManagerObject();
		activityCodeListData.modifySearchData(strBackward);//modify the search data
		ArrayList alPreauthList = preAuthObject.getActivityCodeList(activityCodeListData.getSearchData());
		activityCodeListData.setData(alPreauthList, strBackward);//set the table data
		request.getSession().setAttribute("activityCodeListData",activityCodeListData);   //set the table data object to session
		return this.getForward(strActivityCodeList, mapping, request);   //finally return to the grid screen
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp,strActivityCodeList));
	}//end of catch(Exception exp)
}//end of doActivityCodeBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

public ActionForward closeActivityCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{
setLinks(request);
log.debug("Inside ClaimsAction closeActivityCode ");
((DynaActionForm)form).initialize(mapping);
	return mapping.findForward("ActivityDetails");
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strClaimDetail));
}//end of catch(Exception exp)

}//end of closeActivityCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPreAuthList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateClinicianSearchCriteria(DynaActionForm frmPreAuthList,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sClinicianId")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sClinicianName")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sProviderId")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sProviderName")));
		
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sProviderType")));
		
		return alSearchParams;
	}//end of populateActivityCodeSearchCriteria(DynaActionForm frmProductList,HttpServletRequest request)

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
	public ActionForward doClinicianForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside ClaimAction doClinicianForward");
			setLinks(request);
			TableData clinicianListData = (TableData)request.getSession().getAttribute("clinicianListData");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			clinicianListData.modifySearchData(strForward);//modify the search data
			ArrayList alClinicianList = preAuthObject.getClinicianList(clinicianListData.getSearchData());
			clinicianListData.setData(alClinicianList, strForward);//set the table data
			request.getSession().setAttribute("clinicianListData",clinicianListData);   //set the table data object to session
			return this.getForward(strClinicianList, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClinicianList));
		}//end of catch(Exception exp)
	}//end of doClinicianForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
	public ActionForward doClinicianBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside ClaimAction doClinicianBackward");
			setLinks(request);
			TableData clinicianListData = (TableData)request.getSession().getAttribute("clinicianListData");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			clinicianListData.modifySearchData(strBackward);//modify the search data
			ArrayList alClinicianList = preAuthObject.getClinicianList(clinicianListData.getSearchData());
			clinicianListData.setData(alClinicianList, strBackward);//set the table data
			request.getSession().setAttribute("clinicianListData",clinicianListData);   //set the table data object to session
			return this.getForward(strClinicianList, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClinicianList));
		}//end of catch(Exception exp)
	}//end of doClinicianBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
	public ActionForward diagnosisCodeSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside ClaimAction diagnosisCodeSearch");
			setLinks(request);
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			HttpSession session=request.getSession();
			TableData diagnosisCodeListData = null;
			if(session.getAttribute("diagnosisCodeListData") != null)
			{
				diagnosisCodeListData = (TableData)session.getAttribute("diagnosisCodeListData");
			}//end of if((request.getSession()).getAttribute("icdListData") != null)
			else
			{
				diagnosisCodeListData = new TableData();
			}//end of else
			
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));		
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(strPageID.equals(""))
				{
					diagnosisCodeListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					diagnosisCodeListData.modifySearchData("sort");//modify the search data                    
				}///end of if(!strPageID.equals(""))
				else
				{
				log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
				diagnosisCodeListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return this.getForward(strDiagnosisCodeList, mapping, request);
				}//end of else
	          }//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else{
				diagnosisCodeListData.createTableInfo("DiagnosisCodeListTable",null);
				diagnosisCodeListData.setSearchData(this.populateDiagnosisCodeSearchCriteria((DynaActionForm)form,request));
				diagnosisCodeListData.modifySearchData("search");				
			}//end of else
			
			ArrayList alDiagnosisCodeList=null;
			alDiagnosisCodeList= preAuthObject.getDiagnosisCodeList(diagnosisCodeListData.getSearchData());
			diagnosisCodeListData.setData(alDiagnosisCodeList, "search");
			//set the table data object to session
			session.setAttribute("diagnosisCodeListData",diagnosisCodeListData);
			return this.getForward(strDiagnosisCodeList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDiagnosisCodeList));
		}//end of catch(Exception exp)
	}//end of diagnosisCodeSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPreAuthList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateDiagnosisCodeSearchCriteria(DynaActionForm frmDiagnosisList,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmDiagnosisList.getString("sIcdCode")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmDiagnosisList.getString("sAilmentDescription")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateDiagnosisCodeSearchCriteria(DynaActionForm frmProductList,HttpServletRequest request)

	public ActionForward doSelectDiagnosisCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	try{
	setLinks(request);
	log.debug("Inside ClaimAction doSelectDiagnosisCode ");
	HttpSession session=request.getSession();
	PreAuthManager preAuthObject=this.getPreAuthManagerObject();
	TableData diagnosisCodeListData = (TableData)session.getAttribute("diagnosisCodeListData");

	if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	{
		DiagnosisDetailsVO diagnosisDetailsVO=(DiagnosisDetailsVO)diagnosisCodeListData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));

		DynaActionForm frmClaimGeneral=(DynaActionForm)session.getAttribute("frmClaimGeneral");
		String claimSeqID=frmClaimGeneral.getString("claimSeqID");
		 
		DiagnosisDetailsVO diagnosisDetailsVO2=preAuthObject.getIcdCodeDetails(diagnosisDetailsVO.getIcdCode(),new Long(claimSeqID),"CLM");
		String primary=diagnosisDetailsVO2.getPrimaryAilment();
		if(primary==null||"".equals(primary)||"YES".equals(primary))frmClaimGeneral.set("primaryAilment","");
		else frmClaimGeneral.set("primaryAilment","Y");
		frmClaimGeneral.set("icdCode",diagnosisDetailsVO.getIcdCode());
		frmClaimGeneral.set("icdCodeSeqId",diagnosisDetailsVO.getIcdCodeSeqId().toString());
		frmClaimGeneral.set("ailmentDescription",diagnosisDetailsVO.getAilmentDescription());
		frmClaimGeneral.set("preCronTypeYN",diagnosisDetailsVO2.getPreCronTypeYN());
		request.setAttribute("JS_Focus_ID","icdbtnID");
		session.setAttribute("frmClaimGeneral",frmClaimGeneral);
	}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	return this.getForward(strClaimDetail, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
	return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
	return this.processExceptions(request, mapping, new TTKException(exp,strDiagnosisCodeList));
	}//end of catch(Exception exp)
	}//end of doSelectDiagnosisCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
	public ActionForward doDiagnosisCodeForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside ClaimAction doDiagnosisCodeForward");
			setLinks(request);
			TableData diagnosisCodeListData = (TableData)request.getSession().getAttribute("diagnosisCodeListData");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			diagnosisCodeListData.modifySearchData(strForward);//modify the search data
			ArrayList alDiagnosisList = preAuthObject.getDiagnosisCodeList(diagnosisCodeListData.getSearchData());
			diagnosisCodeListData.setData(alDiagnosisList, strForward);//set the table data
			request.getSession().setAttribute("diagnosisCodeListData",diagnosisCodeListData);   //set the table data object to session
			return this.getForward(strDiagnosisCodeList, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDiagnosisCodeList));
		}//end of catch(Exception exp)
	}//end of doDiagnosisCodeForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doDiagnosisCodeBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside ClaimAction doDiagnosisCodeBackward");
			setLinks(request);
			TableData diagnosisCodeListData = (TableData)request.getSession().getAttribute("diagnosisCodeListData");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			diagnosisCodeListData.modifySearchData(strBackward);//modify the search data
			ArrayList alDiagnosisList = preAuthObject.getDiagnosisCodeList(diagnosisCodeListData.getSearchData());
			diagnosisCodeListData.setData(alDiagnosisList, strBackward);//set the table data
			request.getSession().setAttribute("diagnosisCodeListData",diagnosisCodeListData);   //set the table data object to session
			return this.getForward(strDiagnosisCodeList, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDiagnosisCodeList));
		}//end of catch(Exception exp)
	}//end of doDiagnosisCodeBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside ClaimAction doClose");
			setLinks(request);
			return this.getForward(strClaimDetail, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimDetail));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * Returns the PreAuthManager session object for invoking methods on it.
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
    		}//end if
    	}//end of try
    	catch(Exception exp)
    	{
    		throw new TTKException(exp, strClaimSearchError);
    	}//end of catch
    	return claimManager;
    }//end getClaimManagerObject()
    
    /**
	 * Returns the PreAuthManager session object for invoking methods on it.
	 * @return PreAuthManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthManager getPreAuthManagerObject() throws TTKException
	{
		PreAuthManager preAuthManager = null;
		try
		{
			if(preAuthManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthManager = (PreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthManagerBean!com.ttk.business.preauth.PreAuthManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strClaimSearchError);
		}//end of catch
		return preAuthManager;
	}//end getPreAuthManagerObject()


    /**
     * Populates the web board in the session with the selected items in the grid
     * @param request HttpServletRequest object which contains information about the selected check boxes
     * @throws TTKException If any run time Excepton occures
     */
    private void populateWebBoard(HttpServletRequest request)throws TTKException
    {
    	String[] strChk = request.getParameterValues("chkopt");
    	TableData tableData = (TableData)request.getSession().getAttribute("tableData");
    	Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
    	ArrayList<Object> alCacheObject = new ArrayList<Object>();
    	CacheObject cacheObject = null;
    	PreAuthVO preAuthVO=null;

    	if(strChk!=null&&strChk.length!=0)
    	{
    		for(int i=0; i<strChk.length;i++)
    		{
    			cacheObject = new CacheObject();
    			preAuthVO=(PreAuthVO)tableData.getData().get(Integer.parseInt(strChk[i]));
    			cacheObject.setCacheId(this.prepareWebBoardId(preAuthVO));
    			cacheObject.setCacheDesc(preAuthVO.getInvoiceNo());
    			alCacheObject.add(cacheObject);
    		}//end of for(int i=0; i<strChk.length;i++)
    	}//end of if(strChk!=null&&strChk.length!=0)
    	if(toolbar != null)
    	{
    		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
    	}//end of if(toolbar != null)
    }//end of populateWebBoard(HttpServletRequest request)

    /**
     * Adds the selected item to the web board and makes it as the selected item in the web board
     * @param  preauthVO  object which contains the information of the preauth
     * * @param String  strIdentifier whether it is preauth or enhanced preauth
     * @param request HttpServletRequest
     * @throws TTKException if any runtime exception occures
     */
    private void addToWebBoard(PreAuthVO preAuthVO, HttpServletRequest request)throws TTKException
    {
    	Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
    	ArrayList<Object> alCacheObject = new ArrayList<Object>();
    	CacheObject cacheObject = new CacheObject();
    	cacheObject.setCacheId(this.prepareWebBoardId(preAuthVO)); //set the cacheID
    	cacheObject.setCacheDesc(preAuthVO.getInvoiceNo());
    	alCacheObject.add(cacheObject);
    	//if the object(s) are added to the web board, set the current web board id
    	toolbar.getWebBoard().addToWebBoardList(alCacheObject);
    	toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());

    	//webboardinvoked attribute will be set as true in request scope
    	//to avoid the replacement of web board id with old value if it is called twice in same request scope
    	request.setAttribute("webboardinvoked", "true");
    }//end of addToWebBoard(PreAuthVO preAuthVO, HttpServletRequest request,String strIdentifier)throws TTKException

    /**
     * This method prepares the Weboard id for the selected Policy
     * @param preAuthVO  preAuthVO for which webboard id to be prepared
     * * @param String  strIdentifier whether it is preauth or enhanced preauth
     * @return Web board id for the passedVO
     */
    private String prepareWebBoardId(PreAuthVO preAuthVO)throws TTKException
    {
    	StringBuffer sbfCacheId=new StringBuffer();
    	sbfCacheId.append(preAuthVO.getClaimSeqID()!=null? String.valueOf(preAuthVO.getClaimSeqID()):" ");
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getEnrollmentID()).equals("")?" ":preAuthVO.getEnrollmentID());
    	sbfCacheId.append("~#~").append(preAuthVO.getEnrollDtlSeqID()!=null?String.valueOf(preAuthVO.getEnrollDtlSeqID()):" ");
    	sbfCacheId.append("~#~").append(preAuthVO.getPolicySeqID()!=null? String.valueOf(preAuthVO.getPolicySeqID()):" ");
    	sbfCacheId.append("~#~").append(preAuthVO.getMemberSeqID()!=null? String.valueOf(preAuthVO.getMemberSeqID()):" ");
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getClaimantName()).equals("")? " ":preAuthVO.getClaimantName());
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getBufferAllowedYN()).equals("")? " ":preAuthVO.getBufferAllowedYN());
    	sbfCacheId.append("~#~").append(preAuthVO.getClmEnrollDtlSeqID()!=null? String.valueOf(preAuthVO.getClmEnrollDtlSeqID()):" ");
    	sbfCacheId.append("~#~").append(preAuthVO.getAmmendmentYN()!=null? String.valueOf(preAuthVO.getAmmendmentYN()):" ");
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getCoding_review_yn()).equals("")? " ":preAuthVO.getCoding_review_yn());
    	return sbfCacheId.toString();
    }//end of prepareWebBoardId(PreAuthVO preAuthVO,String strIdentifier)throws TTKException

    /**
     * This method supresess the Assign Icon Column by checking against the Assign and Special Permissions
     *  from the User's Role.
     * @param tableData TableData object which contains the Grid Information
     * @param frmClaimList formbean which contains the search fields
     * @param request HttpServletRequest object
     * @throws TTKException if any run time exception occures
     */
    private void setColumnVisiblity(TableData tableData,DynaActionForm frmClaimList,HttpServletRequest request)
    												throws TTKException
    {
    	String strAssignTo=frmClaimList.getString("sAssignedTo");
    	boolean blnVisibility=false;
    	//For Self Check the Assign Permission
    	if(strAssignTo.equals("SLF") && TTKCommon.isAuthorized(request,"Assign"))
    	{
    		blnVisibility=true;
    	}//end of if(strAssignTo.equals("SLF") && TTKCommon.isAuthorized(request,"Assign"))
    	else         //Check for the special Permission to show ICON for Others and Unassigned Claim
    	{
    		if(TTKCommon.isAuthorized(request,"AssignAll"))
    		{
    			blnVisibility=true;
    		}//end of if(TTKCommon.isAuthorized(request,"AssignAll"))
    	}//end of else
    	((Column)((ArrayList)tableData.getTitle()).get(ASSIGN_ICON)).setVisibility(blnVisibility);
    }//end of setColumnVisiblity(TableData tableData,DynaActionForm frmPreAuthList,HttpServletRequest request)
    
    
    
    
    
    public ActionForward memberSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
   			HttpServletResponse response) throws Exception{
           try{
           log.debug("Inside ClaimAction memberSearch");
                  setLinks(request);
           PreAuthManager preAuthObject=this.getPreAuthManagerObject();
           TableData memberListData = null;
              if((request.getSession()).getAttribute("memberListData") != null)
               {
           	   memberListData = (TableData)(request.getSession()).getAttribute("memberListData");
               }//end of if((request.getSession()).getAttribute("icdListData") != null)
            else
             {
           	 memberListData = new TableData();
              }//end of else

            String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));		
            //if the page number or sort id is clicked
         if(!strPageID.equals("") || !strSortID.equals(""))
          {
           if(strPageID.equals(""))
            {
           	memberListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
           	memberListData.modifySearchData("sort");//modify the search data                    
            }///end of if(!strPageID.equals(""))
          else
           {
                   log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
                   memberListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
                 return this.getForward(strMemberList, mapping, request);
              }//end of else
           }//end of if(!strPageID.equals("") || !strSortID.equals(""))
       else{
       	memberListData.createTableInfo("MemberListTable",null);
       	memberListData.setSearchData(this.populateMembersSearchCriteria((DynaActionForm)form,request));
       	memberListData.modifySearchData("search");				
      }//end of else

      ArrayList alMemberList=null;
      alMemberList= preAuthObject.getMemberList(memberListData.getSearchData());
     
      
      int count = 0;
	     count++;
	     request.getSession().setAttribute("count",count);
	     memberListData.setData(alMemberList, "search");
	      //set the table data object to session
      request.getSession().setAttribute("memberListData",memberListData);
      return this.getForward(strMemberList, mapping, request);
      }//end of try
      catch(TTKException expTTK)
      {
          return this.processExceptions(request, mapping, expTTK);
       }//end of catch(TTKException expTTK)
       catch(Exception exp)
       {
         return this.processExceptions(request, mapping, new TTKException(exp,strMemberList));
        }//end of catch(Exception exp)
      }
	
	
	private ArrayList<Object> populateMembersSearchCriteria(DynaActionForm frmPreAuthList,HttpServletRequest request)
  	{
  		//build the column names along with their values to be searched
  		ArrayList<Object> alSearchParams = new ArrayList<Object>();
  		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sEnrollmentId")));
          alSearchParams.add(TTKCommon.getUserSeqId(request));
  		return alSearchParams;
  	}//end of populateActivityCodeSearchCriteria(DynaActionForm frmProductList,HttpServletRequest request)

    
    
    
    
    
    
	 public ActionForward doMemberBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		        HttpServletResponse response) throws Exception{
try{
    log.debug("Inside ClaimAction doMemberBackward");
    setLinks(request);
    TableData memberListData = (TableData)request.getSession().getAttribute("memberListData");
    PreAuthManager preAuthObject=this.getPreAuthManagerObject();
    memberListData.modifySearchData(strBackward);//modify the search data
    ArrayList alMemberList = preAuthObject.getMemberList(memberListData.getSearchData());
    memberListData.setData(alMemberList, strBackward);//set the table data
     request.getSession().setAttribute("memberListData",memberListData);   //set the table data object to session
      return this.getForward(strMemberList, mapping, request);   //finally return to the grid screen
    }//end of try
catch(TTKException expTTK)
{
      return this.processExceptions(request, mapping, expTTK);
    }//end of catch(TTKException expTTK)
catch(Exception exp)
{
 return this.processExceptions(request, mapping, new TTKException(exp,strMemberList));
  }//end of catch(Exception exp)
}
    
    
    
	public ActionForward doMemberForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
  			HttpServletResponse response) throws Exception{
  try{
             log.debug("Inside ClaimAction doMeberForward");
             setLinks(request);
             TableData memberListData = (TableData)request.getSession().getAttribute("memberListData");
             PreAuthManager preAuthObject=this.getPreAuthManagerObject();
             memberListData.modifySearchData(strForward);//modify the search data
             ArrayList alMemberList = preAuthObject.getMemberList(memberListData.getSearchData());
             memberListData.setData(alMemberList, strForward);//set the table data
           request.getSession().setAttribute("memberListData",memberListData);   //set the table data object to session
           return this.getForward(strMemberList, mapping, request);   //finally return to the grid screen
           }//end of try
        catch(TTKException expTTK)
            {
             return this.processExceptions(request, mapping, expTTK);
             }//end of catch(TTKException expTTK)
         catch(Exception exp)
          {
             return this.processExceptions(request, mapping, new TTKException(exp,strMemberList));
          }//end of catch(Exception exp)
        }
    
    
    
    
	public ActionForward doCloseMember(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	try{
	log.debug("Inside ClaimsAction closeClinicians");
	
	String strActTab=TTKCommon.getActiveTab(request);
	
	return mapping.findForward("claimGeneralDetails");
	
	
	
		
	}//end of try
	catch(Exception exp)
	{
	return this.processExceptions(request, mapping, new TTKException(exp,"claimdetail"));
	}//end of catch(Exception exp)
	}
    
    
    
    
	public ActionForward doSelectMemberId(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
		  	setLinks(request);
		  	log.debug("Inside ClaimGenealAction doSelectMemberId ");
		  	HttpSession session=request.getSession();
		  	PreAuthManager preAuthObject=this.getPreAuthManagerObject();
		  	TableData memberListData = (TableData)session.getAttribute("memberListData");

		  	if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		  	{
		  		
		  		MemberDetailVO MemberDetailVO=(MemberDetailVO)memberListData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
		  		
		  		
		  	DynaActionForm frmClaimGeneral=(DynaActionForm)session.getAttribute("frmClaimGeneral");

		  	frmClaimGeneral.set("memberSeqID", MemberDetailVO.getMemberSeqID().toString());
		  	frmClaimGeneral.set("memberId", MemberDetailVO.getMemberId());
		  	frmClaimGeneral.set("patientName", MemberDetailVO.getPatientName());
		  	frmClaimGeneral.set("memberAge",MemberDetailVO.getMemberAge().toString());
		  	frmClaimGeneral.set("emirateId", MemberDetailVO.getEmirateId());
		  	frmClaimGeneral.set("payerName", MemberDetailVO.getPayerName());
		  	frmClaimGeneral.set("payerId", MemberDetailVO.getPayerId());
		  	frmClaimGeneral.set("insSeqId",MemberDetailVO.getInsSeqId().toString());
		  	frmClaimGeneral.set("policySeqId", MemberDetailVO.getPolicySeqId().toString());
		  	frmClaimGeneral.set("patientGender", MemberDetailVO.getPatientGender());
		  	frmClaimGeneral.set("policyNumber", MemberDetailVO.getPolicyNumber());
		  	frmClaimGeneral.set("corporateName",MemberDetailVO.getCorporateName());
		  	frmClaimGeneral.set("policyStartDate", MemberDetailVO.getStart_date());
		  	frmClaimGeneral.set("policyEndDate", MemberDetailVO.getEnd_date());
		  	frmClaimGeneral.set("nationality", MemberDetailVO.getNationality());
		  	frmClaimGeneral.set("sumInsured",MemberDetailVO.getSumInsured().toString());
		  	frmClaimGeneral.set("availableSumInsured",MemberDetailVO.getAvailableSumInsured().toString());
		  	frmClaimGeneral.set("vipYorN",MemberDetailVO.getVipYorN());
		  	frmClaimGeneral.set("clmMemInceptionDate",MemberDetailVO.getPreMemInceptionDt());
		  	frmClaimGeneral.set("productName",MemberDetailVO.getProductName());
		  	frmClaimGeneral.set("eligibleNetworks",MemberDetailVO.getEligibleNetworks());
		  	frmClaimGeneral.set("payerAuthority",MemberDetailVO.getProvAuthority());//accountNumber
			frmClaimGeneral.set("clmMemExitDate",MemberDetailVO.getMemberExitDate());	
		  		
		  	frmClaimGeneral.set("memberDOB",MemberDetailVO.getMemberDOB());	
		  		session.setAttribute("frmClaimGeneral",frmClaimGeneral);
		  		
		  		
		  	}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		  	return this.getForward(strClaimDetail, mapping, request);
		  	}//end of try
		  	catch(TTKException expTTK)
		  	{
		  	return this.processExceptions(request, mapping, expTTK);
		  	}//end of catch(TTKException expTTK)
		  	catch(Exception exp)
		  	{
		  	return this.processExceptions(request, mapping, new TTKException(exp,strMemberList));
		  	}
	}
    
    
    
    
	public boolean	memberAgeValidation(DynaActionForm frmClaimGeneral)throws Exception{
		String strNetworkProviderType=frmClaimGeneral.getString("networkProviderType");
		String strProvAuthority=frmClaimGeneral.getString("provAuthority");
		String strEncounterTypeId=frmClaimGeneral.getString("encounterTypeId");
		boolean status=false;
		if("Y".equals(strNetworkProviderType)&&"DHA".equals(strProvAuthority)&&("3".equals(strEncounterTypeId)||"4".equals(strEncounterTypeId))){
    
			String strAdmissionDate=frmClaimGeneral.getString("admissionDate");
			String strMemberDOB=frmClaimGeneral.getString("memberDOB");
		/*	if(strAdmissionDate==null && "".equals(strAdmissionDate) )
			{
				status=false;
			}
			else{*/
    
			if(strMemberDOB!=null&&!"".equals(strMemberDOB)){	
				if(strAdmissionDate==null && "".equals(strAdmissionDate) ){
				SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
				Date date1=simpleDateFormat.parse(strMemberDOB);
    
				Date date2=simpleDateFormat.parse(strAdmissionDate);
				long sd=(8*24*60*60*1000);
				long dif=date2.getTime()-date1.getTime();
    
				if(dif<sd){
					
					status=true;
					int days = (int) (dif / (1000*60*60*24));
					frmClaimGeneral.set("patientdays",days+"");
				}
			}
			}
			
		}
	return status;
	}
	
    
    
    
}//end of ClaimsAction