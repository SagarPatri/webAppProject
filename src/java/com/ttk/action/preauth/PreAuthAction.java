/**
 * @ (#) PreAuthAction.java Apr 27, 2006
 * Project      : TTK HealthCare Services
 * File         : PreAuthAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Apr 27, 2006
 *
 * @author       :  Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.preauth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.enrollment.MemberAddressVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.preauth.ClinicianDetailsVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.preauth.ProviderDetailsVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This class is used for searching of pre-auth.
 * This class also provides option for deletion of pre-auth.
 */

public class PreAuthAction extends TTKAction
{
	private static Logger log = Logger.getLogger(PreAuthAction.class);
	//declrations of modes
	private static final String strForward="Forward";
	private static final String strBackward="Backward";
	private static final String strDeleteList="DeleteList";
	private static final String strPreauthSearch="preauthsearch";

	//Declarations of constants
	private static final String strRegular="Regular";    //identfier for Regular Pre-Auth
	private static final String strEnhanced="Enhanced";  //identifier for Enhanced Pre-Auth
	private static final String strRegularPreAuth="PAT";
	private static final String strEnhancedPreAuth="ICO";
	private static final int ASSIGN_ICON=9;

	//Action mapping forwards.
	private static final String strPreauthlist="preauthlist";
	private static String strPreAuthDetails;
	
	
	private static final String strPreAuthDetail="preauthdetail";
	private static final String strProviderList="providerSearchList";
	private static final String strClinicianList="clinicianSearchList";
	private static final String strPreauthEnhancementList="preauthEnhancementList";
	private static final String strActivityCodeList="activityCodeList";
	private static final String strActivitydetails="activitydetails";
	private static final String strActivityDetails="ActivityDetails";
	private static final String strMemberList="memberSearchList";
	private static final String strOralPreAuthDetail = "oralPreAuthDetail";
	
	
	
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
																HttpServletResponse response)throws Exception {
		try{
			setLinks(request);
		
			log.debug("Inside PreAuthAction doDefault");
			DynaActionForm frmPreAuthList=(DynaActionForm)form;
			frmPreAuthList.set("activeSubLink",TTKCommon.getActiveSubLink(request));
			TableData tableData =TTKCommon.getTableData(request);
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
											request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("PreAuthTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			int countPrauthlist = preAuthObject.getCountOralPreAuth();
			request.getSession().setAttribute("countPrauthlist",countPrauthlist);
			frmPreAuthList.set("sTtkBranch",strDefaultBranchID);
			frmPreAuthList.set("activeSubLink",TTKCommon.getActiveSubLink(request));
			request.getSession().setAttribute("frmPreAuthList",frmPreAuthList);
			return this.getForward(strPreauthlist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doDefaultEnhancement(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside PreAuthAction doDefaultEnhancement");
			DynaActionForm frmPreAuthList=(DynaActionForm)form;
			TableData tableData =new TableData();
			//create the required grid table
			tableData.createTableInfo("PreAuthEnhancementTable",new ArrayList());
			request.getSession().setAttribute("preAuthEnhancementListData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			request.getSession().setAttribute("frmPreAuthList",frmPreAuthList);
			return this.getForward(strPreauthEnhancementList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthEnhancementList));
		}//end of catch(Exception exp)
	}//end of doDefaultEnhancement(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
			
			log.debug("Inside PreAuthAction doSearch");
			setLinks(request);
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			TableData tableData =TTKCommon.getTableData(request);
		
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
					return mapping.findForward(strPreauthlist);
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else{
				//create the required grid table
				tableData.createTableInfo("PreAuthTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				//tableData.setSortColumnName("PRE_AUTH_NUMBER");
				//this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
				tableData.modifySearchData("search");
			}//end of else
			
			ArrayList alPreauthList= preAuthObject.getPreAuthList(tableData.getSearchData());
			tableData.setData(alPreauthList, "search");
			//set the table data object to session
		    request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strPreauthlist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
			log.debug("Inside PreAuthAction activityCodeSearch");
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
				activityCodeListData.setSearchData(this.populateActivityCodeSearchCriteria(frmActivityDetails.getString("preAuthSeqID"),(DynaActionForm)form,request));
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
	private ArrayList<Object> populateActivityCodeSearchCriteria(String preAuthSeqID,DynaActionForm frmActivityList,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivityList.getString("sActivityCode")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivityList.getString("sActivityCodeDesc")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivityList.getString("sSearchType")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivityList.getString("sAuthType")));
		//alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivityList.getString("sNetworkType")));
		//alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivityList.getString("sServiceType")));
		//alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivityList.getString("sActivityType")));
		alSearchParams.add(preAuthSeqID);
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivityList.getString("sCategory")));
		return alSearchParams;
		
	}//end of populateActivityCodeSearchCriteria(DynaActionForm frmProductList,HttpServletRequest request)

	public ActionForward doSelectActivityCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	try{
	setLinks(request);
	log.debug("Inside PreAuthAction doSelectActivityCode ");
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
			log.debug("Inside PreAuthAction doActivityCodeForward");
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
			log.debug("Inside PreAuthAction doActivityCodeBackward");
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

	public ActionForward closeActivityCodes(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	try{
	setLinks(request);
	log.debug("Inside PreAuthGenealAction closeActivityCodes");
	((DynaActionForm)form).initialize(mapping);
		return mapping.findForward(strActivityDetails);
	}//end of try
	catch(TTKException expTTK)
	{
	return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
	return this.processExceptions(request, mapping, new TTKException(exp,strPreAuthDetail));
	}//end of catch(Exception exp)

	}//end of closeActivityCodes(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doEnhancementSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside PreAuthAction doEnhancementSearch");
			setLinks(request);
			HttpSession session=request.getSession();
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			DynaActionForm frmPreAuthList=(DynaActionForm)form;
			TableData preAuthEnhancementListData = null;
			if(session.getAttribute("preAuthEnhancementListData") != null)
			{
				preAuthEnhancementListData = (TableData)session.getAttribute("preAuthEnhancementListData");
			}//end of if((request.getSession()).getAttribute("icdListData") != null)
			else
			{
				preAuthEnhancementListData = new TableData();
			}//end of else
			
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(strPageID.equals(""))
				{
					preAuthEnhancementListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					preAuthEnhancementListData.modifySearchData("sort");//modify the search data                    
				}///end of if(!strPageID.equals(""))
				else
				{
				log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
				preAuthEnhancementListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return this.getForward(strPreauthEnhancementList, mapping, request);
				}//end of else
	          }//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else{
				preAuthEnhancementListData.createTableInfo("PreAuthEnhancementTable",null);
				preAuthEnhancementListData.setSearchData(this.populateEnhancementSearchCriteria((DynaActionForm)form,request));
				preAuthEnhancementListData.modifySearchData("search");				
			}//end of else
			
			ArrayList alPreAuthEnhancementList=null;
			alPreAuthEnhancementList= preAuthObject.getPreAuthEnhancementList(preAuthEnhancementListData.getSearchData());
			preAuthEnhancementListData.setData(alPreAuthEnhancementList, "search");
			//set the table data object to session
			session.setAttribute("preAuthEnhancementListData",preAuthEnhancementListData);
			session.setAttribute("frmPreAuthList",frmPreAuthList);
			return this.getForward(strPreauthEnhancementList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthEnhancementList));
		}//end of catch(Exception exp)
	}//end of doEnhancementSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
			log.debug("Inside PreAuthAction clinicianSearch");
			setLinks(request);
		
			HttpSession session=request.getSession();
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			TableData clinicianListData = null;
			DynaActionForm frmPreAuthList=(DynaActionForm)form;
			
			if("Y".equals(request.getParameter("Entry"))){
				DynaActionForm	frmPreAuthGeneral=(DynaActionForm)session.getAttribute("frmPreAuthGeneral");
				
				 String strproviderId = frmPreAuthGeneral.getString("providerId");
                 String strproviderType = frmPreAuthGeneral.getString("providerName");
				
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
				frmPreAuthList.set("sProviderName",frmPreAuthGeneral.getString("providerName"));
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
	log.debug("Inside PreAuthGenealAction doSelectclinicianId ");
	HttpSession session=request.getSession();
	TableData clinicianListData = (TableData)session.getAttribute("clinicianListData");
    String forward=strPreAuthDetail;
	if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	{
		ClinicianDetailsVO clinicianDetailsVO=(ClinicianDetailsVO)clinicianListData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
		
		if("activityClinicianSearch".equals(session.getAttribute("forwardMode"))){
			DynaActionForm frmActivityDetails=(DynaActionForm)session.getAttribute("frmActivityDetails");
			frmActivityDetails.set("clinicianId", clinicianDetailsVO.getClinicianId());
			session.setAttribute("frmActivityDetails",frmActivityDetails);
			forward="activitydetails";
		}else{
		
		DynaActionForm frmPreAuthGeneral=(DynaActionForm)session.getAttribute("frmPreAuthGeneral");
			
		frmPreAuthGeneral.set("clinicianId", clinicianDetailsVO.getClinicianId());
		frmPreAuthGeneral.set("clinicianName", clinicianDetailsVO.getClinicianName());
		frmPreAuthGeneral.set("consultationType", clinicianDetailsVO.getClinicianConsultation());
		
		if(memberAgeValidation(frmPreAuthGeneral)){
			
			if("".equals(frmPreAuthGeneral.getString("memberWeight")))
			{
				frmPreAuthGeneral.set("memberwtflag", "Y");
			
			ActionMessages actionMessages = new ActionMessages();
            ActionMessage actionMessage = new ActionMessage("error.member.patient");
            actionMessages.add("global.error",actionMessage);
            saveErrors(request,actionMessages);
            session.setAttribute("frmPreAuthGeneral", frmPreAuthGeneral);
            return this.getForward("preauthdetail", mapping, request);
			}
			
		}
		else if(memberAgeValidation(frmPreAuthGeneral)==false)
		{
			frmPreAuthGeneral.set("memberwtflag", "N");
		}
		
		
		   session.setAttribute("frmPreAuthGeneral",frmPreAuthGeneral);
			
			forward=strPreAuthDetail;
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
			log.debug("Inside PreAuthAction doClinicianForward");
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
			log.debug("Inside PreAuthAction doClinicianBackward");
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
	public ActionForward providerSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside PreAuthAction providerSearch");
			setLinks(request);
			
			String sourcetype = request.getParameter("preAuthRecvTypeID");
			
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
			
			else if("DHP".equals(sourcetype) || "ONL1".equals(sourcetype)){
				providerListData.createTableInfo("ProviderListTable",null);
			}
			
			else{
				providerListData.createTableInfo("ProviderListNewTable",null);
								
			}//end of else
			
			
			providerListData.setSearchData(this.populateProvidersSearchCriteria((DynaActionForm)form,request));
			providerListData.modifySearchData("search");
			
			
			ArrayList alProviderList=null;
			alProviderList= preAuthObject.getProviderList(providerListData.getSearchData());
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
	log.debug("Inside PreAuthGenealAction doSelectProviderId ");
	HttpSession session=request.getSession();
	TableData providerListData = (TableData)session.getAttribute("providerListData");

	if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	{
		ProviderDetailsVO providerDetailsVO=(ProviderDetailsVO)providerListData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));

	    DynaActionForm frmPreAuthGeneral=(DynaActionForm)session.getAttribute("frmPreAuthGeneral");
        frmPreAuthGeneral.set("providerSeqId", providerDetailsVO.getProviderSeqId().toString());
		frmPreAuthGeneral.set("providerId", providerDetailsVO.getProviderId());
		frmPreAuthGeneral.set("providerName", providerDetailsVO.getProviderName());
		frmPreAuthGeneral.set("providerSpecificRemarks",providerDetailsVO.getProviderSpecificRemarks());
		frmPreAuthGeneral.set("provAuthority",providerDetailsVO.getProviderAuthority());
		frmPreAuthGeneral.set("vatTrnCode",providerDetailsVO.getVatTrnCode());
		session.setAttribute("frmPreAuthGeneral",frmPreAuthGeneral);
		
	}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	return this.getForward(strPreAuthDetail, mapping, request);
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
			log.debug("Inside PreAuthAction doProviderForward");
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
			log.debug("Inside PreAuthAction doProviderBackward");
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
			log.debug("Inside PreAuthAction doForward");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			tableData.modifySearchData(strForward);//modify the search data
			ArrayList alPreauthList = preAuthObject.getPreAuthList(tableData.getSearchData());
			tableData.setData(alPreauthList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strPreauthlist, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
			log.debug("Inside PreAuthAction doClose");
			setLinks(request);
			return this.getForward(strPreAuthDetail, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside PreAuthAction doBackward");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alPreauthList = preAuthObject.getPreAuthList(tableData.getSearchData());
			tableData.setData(alPreauthList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strPreauthlist, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
	public ActionForward doEnhancementBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside PreAuthAction doEnhancementBackward");
			setLinks(request);
			TableData  preAuthEnhancementListData = (TableData)(request.getSession()).getAttribute("preAuthEnhancementListData");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			preAuthEnhancementListData.modifySearchData(strBackward);//modify the search data
			ArrayList alPreAuthEnhancementList = preAuthObject.getPreAuthList(preAuthEnhancementListData.getSearchData());
			preAuthEnhancementListData.setData(alPreAuthEnhancementList, strBackward);//set the table data
			request.getSession().setAttribute("preAuthEnhancementListData",preAuthEnhancementListData);   //set the table data object to session
			return this.getForward(strPreauthEnhancementList, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthEnhancementList));
		}//end of catch(Exception exp)
	}//end of doEnhancementBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
	public ActionForward doEnhancementForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside PreAuthAction doEnhancementForward");
			setLinks(request);
			TableData  preAuthEnhancementListData = (TableData)(request.getSession()).getAttribute("preAuthEnhancementListData");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			preAuthEnhancementListData.modifySearchData(strForward);//modify the search data
			ArrayList alPreAuthEnhancementList = preAuthObject.getPreAuthList(preAuthEnhancementListData.getSearchData());
			preAuthEnhancementListData.setData(alPreAuthEnhancementList, strForward);//set the table data
			request.getSession().setAttribute("preAuthEnhancementListData",preAuthEnhancementListData);   //set the table data object to session
			return this.getForward(strPreauthEnhancementList, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthEnhancementList));
		}//end of catch(Exception exp)
	}//end of doEnhancementForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to get the delete records from the grid screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside PreAuthAction doDeleteList");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			StringBuffer sbfDeleteId = new StringBuffer("|");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			int iCount=0;
			//populate the delete string which contains the sequence id's to be deleted
			sbfDeleteId.append(populateDeleteId(request,(TableData)request.getSession().getAttribute("tableData")));
			ArrayList <Object>alDeleteMode=new ArrayList<Object>();
			alDeleteMode.add(strRegularPreAuth);
			alDeleteMode.add(sbfDeleteId.toString());
			alDeleteMode.add(null);//PAT_ENROLL_DETAIL_SEQ_ID is not required.
			alDeleteMode.add(null);//PAT_GENERAL_DETAIL_SEQ_ID is not required
			alDeleteMode.add(TTKCommon.getUserSeqId(request));//User id
			//delete the selected preauth based on the flow
			iCount = preAuthObject.deletePATGeneral(alDeleteMode);

			//refresh the grid with search data in session
			ArrayList alPreauthList = null;
			//fetch the data from previous set of rowcounts, if all the records are deleted for the current set
			//of search criteria
			if(iCount == tableData.getData().size())
			{
				tableData.modifySearchData(strDeleteList);//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(
																			tableData.getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alPreauthList= preAuthObject.getPreAuthList(tableData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				alPreauthList= preAuthObject.getPreAuthList(tableData.getSearchData());
			}//end of else
			tableData.setData(alPreauthList, strDeleteList);
			if(iCount>0)
			{
				//delete the Pre-Auth from the web board if any
				request.setAttribute("cacheId",sbfDeleteId.append("|").toString());
				PreAuthWebBoardHelper.deleteWebBoardId(request,"SeqId");
			}//end of if(iCount>0)
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strPreauthlist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

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
			log.debug("Inside PreAuthAction doCopyToWebBoard");
			setLinks(request);
			this.populateWebBoard(request);
			return this.getForward(strPreauthlist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
		}//end of catch(Exception exp)
	}//end of CopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doViewPreauth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside PreAuthAction doViewPreauth");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmPreAuthList=(DynaActionForm)form;
			HttpSession session = request.getSession();
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				PreAuthVO preAuthVO=(PreAuthVO)tableData.getRowInfo(Integer.parseInt((String)(frmPreAuthList).get("rownum")));
				
			
				if("ORAL".equals(preAuthVO.getOralORsystemStatus()))
				{
					request.setAttribute("invoked",null);
					request.setAttribute("preAuthVO",preAuthVO);
					strPreAuthDetails="preauthoraldetail";
				}
				else
				{
					session.setAttribute("PreauthSeqId", preAuthVO.getPreAuthSeqID());
					strPreAuthDetails="preauthdetail";
					this.addToWebBoard(preAuthVO, request,strRegular);
				}
				
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward(strPreAuthDetails);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
		}//end of catch(Exception exp)
	}//end of doViewPreauth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doViewEnhanced(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside PreAuthAction doViewEnhanced");
			setLinks(request);
			TableData preAuthEnhancementListData = (TableData)request.getSession().getAttribute("preAuthEnhancementListData");
			DynaActionForm frmPreAuthList=(DynaActionForm)form;
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				PreAuthVO preAuthVO=(PreAuthVO)preAuthEnhancementListData.getRowInfo(Integer.parseInt((String)(frmPreAuthList).get("rownum")));
				request.getSession().setAttribute("enhancementPreAuthSeqID",preAuthVO.getPreAuthSeqID());
				request.setAttribute("invoked",null);
				//this.addToWebBoard(preAuthVO, request,strEnhanced);
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward(strPreAuthDetail);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
		}//end of catch(Exception exp)
	}//end of doViewEnhanced(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)


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
log.debug("Inside PreAuthGenealAction closeClinicians");
	return mapping.findForward("activitydetails");
}//end of try
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,"preauthdetail"));
}//end of catch(Exception exp)
}//end of closeClinicians(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPreAuthList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmPreAuthList,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sPreAuthNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sProviderName")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sEnrollmentId")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sClaimantName")));
		alSearchParams.add((String)frmPreAuthList.getString("sRecievedDate"));
		alSearchParams.add((String)frmPreAuthList.getString("sTtkBranch"));
		alSearchParams.add((String)frmPreAuthList.getString("sAssignedTo"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sSpecifyName")));
		alSearchParams.add((String)frmPreAuthList.getString("sAmount"));
		alSearchParams.add((String)frmPreAuthList.getString("sSource"));
		alSearchParams.add((String)frmPreAuthList.getString("sStatus"));
		alSearchParams.add((String)frmPreAuthList.getString("sWorkFlow"));
		alSearchParams.add((String)frmPreAuthList.getString("sPreAuthType"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sPolicyNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sEmployeeNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sCorporateName")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		alSearchParams.add(TTKCommon.getUserGroupList(request));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sSchemeName")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sCertificateNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sInsurerAppStatus")));//bajaj
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sPayerName")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sAuthorizationNO")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sGlobalNetMemID")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("spreauthCategory")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sEmiratesID")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sProcessType"))); 
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("dhpoMemberId"))); 
    	return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmProductList)

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPreAuthList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateEnhancementSearchCriteria(DynaActionForm frmPreAuthList,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sPreAuthNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sClaimantName")));
		alSearchParams.add((String)frmPreAuthList.getString("sRecievedDate"));
		alSearchParams.add((String)frmPreAuthList.getString("sStatus"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sPolicyNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sEnrollmentId")));		
		alSearchParams.add(TTKCommon.getUserSeqId(request));
    	return alSearchParams;
	}//end of populateEnhancementSearchCriteria(DynaActionForm frmProductList)

	
	/**
	 * This method supresess the Assign Icon Column by checking against the Assign and Special Permissions
	 * from the User's Role.
	 * @param tableData TableData object which contains the Grid Information
	 * @param frmPreAuthList formbean which contains the search fields
	 * @param request HttpServletRequest object
	 * @throws TTKException if any run time exception occures
	 */
	private void setColumnVisiblity(TableData tableData,DynaActionForm frmPreAuthList,HttpServletRequest request)
																									throws TTKException
	{
		String strAssignTo=frmPreAuthList.getString("sAssignedTo");
		boolean blnVisibility=false;
		if(strAssignTo.equals("SLF") && TTKCommon.isAuthorized(request,"Assign"))//For Self Check the Assign Permission
		{
			blnVisibility=true;
		}//end of if(strAssignTo.equals("SLF") && TTKCommon.isAuthorized(request,"Assign"))
		else        //Check for the special Permission to show ICON for Others and Unassigned Pre-auth
		{
			if(TTKCommon.isAuthorized(request,"AssignAll"))
			{
				blnVisibility=true;
			}//end of if(TTKCommon.isAuthorized(request,"AssignAll"))
		}//end of else
		((Column)((ArrayList)tableData.getTitle()).get(8)).setVisibility(true); //koc 11 koc11
		((Column)((ArrayList)tableData.getTitle()).get(ASSIGN_ICON)).setVisibility(blnVisibility);
	}//end of setColumnVisiblity(TableData tableData,DynaActionForm frmPreAuthList,HttpServletRequest request)

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
			//loop through to populate delete sequence id's and get the value from session for the matching
			//check box value
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append(String.valueOf(((PreAuthVO)tableData.getData().get(
																	Integer.parseInt(strChk[i]))).getPreAuthSeqID()));
					}// end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((PreAuthVO)
											tableData.getData().get(Integer.parseInt(strChk[i]))).getPreAuthSeqID()));
					}// end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		log.debug("DELETE IDS !!!!!!! "+sbfDeleteId);
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)

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
			throw new TTKException(exp, strPreauthSearch);
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
				cacheObject.setCacheId(this.prepareWebBoardId(preAuthVO,strRegular));
				cacheObject.setCacheDesc(preAuthVO.getPreAuthNo());
				//System.out.println(cacheObject.toString()+"<------cacheObject");
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
	private void addToWebBoard(PreAuthVO preAuthVO, HttpServletRequest request,String strIdentifier)throws TTKException
	{
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		CacheObject cacheObject = new CacheObject();
		cacheObject.setCacheId(this.prepareWebBoardId(preAuthVO,strIdentifier)); //set the cacheID
		cacheObject.setCacheDesc(TTKCommon.checkNull(preAuthVO.getPreAuthNo()));
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
	private String prepareWebBoardId(PreAuthVO preAuthVO,String strIdentifier)throws TTKException
	{
		StringBuffer sbfCacheId=new StringBuffer();
		sbfCacheId.append(preAuthVO.getPreAuthSeqID()!=null? String.valueOf(preAuthVO.getPreAuthSeqID()):" ");
		sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getEnrollmentID()).equals("")? " ":preAuthVO.getEnrollmentID());
		sbfCacheId.append("~#~").append(preAuthVO.getEnrollDtlSeqID()!=null? String.valueOf(preAuthVO.getEnrollDtlSeqID()):" ");
		sbfCacheId.append("~#~").append(preAuthVO.getPolicySeqID()!=null? String.valueOf(preAuthVO.getPolicySeqID()):" ");
		sbfCacheId.append("~#~").append(preAuthVO.getMemberSeqID()!=null? String.valueOf(preAuthVO.getMemberSeqID()):" ");
		if(strIdentifier.equals(strRegular))// to check it is a regular pre auth
		{
			sbfCacheId.append("~#~").append(strRegularPreAuth);//if it is a reular preauth then append with string identifier
		}//end of if(strIdentifier.equals(strRegular))
		else if(strIdentifier.equals(strEnhanced))//to check it is a enhanced preauth
		{
			if(preAuthVO.getEnhanceIconYN().equals("Y"))// to check whethere there is enhanced icon
			{
				sbfCacheId.append("~#~").append(strEnhancedPreAuth);
			}//end of if(preAuthVO.getEnhanceIconYN().equals("Y"))
			else
			{
				sbfCacheId.append("~#~").append(strRegularPreAuth);
			}//end of else
		}// end of else if(strIdentifier.equals(strEnhanced))
		sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getClaimantName()).equals("")? " ":preAuthVO.getClaimantName());
		sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getBufferAllowedYN()).equals("")? " ":preAuthVO.getBufferAllowedYN());
		sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getShowBandYN()).equals("")? " ":preAuthVO.getShowBandYN());
		sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getCoding_review_yn()).equals("")? " ":preAuthVO.getCoding_review_yn());
		return sbfCacheId.toString();
	}//end of prepareWebBoardId(PreAuthVO preAuthVO,String strIdentifier)throws TTKException
	
	
	
	
	
	
	public ActionForward memberSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
        try{
        log.debug("Inside PreAuthAction memberSearch");
               setLinks(request);
        PreAuthManager preAuthObject=this.getPreAuthManagerObject();
        request.getSession().removeAttribute("ListmemberDetailVO");
        TableData memberListData = null;
        DynaActionForm frmPreAuthList=(DynaActionForm)form;
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
   String strEnrID=TTKCommon.checkNull(frmPreAuthList.getString("sEnrollmentId"));
	String strEmrID=TTKCommon.checkNull(frmPreAuthList.getString("sEnrollmentId"));//this is for emitate id so add emitate id in place of enrollment
    if(strEnrID.length()>0){
   if(alMemberList==null||alMemberList.size()==0)throw new TTKException("error.invalid.memberid");
    }
   int count = 0;
   count++;
   request.getSession().setAttribute("count",count);
  
		if((strEnrID.length()>0||strEmrID.length()>0)&&alMemberList!=null&&alMemberList.size()==1){				
		//set the table data object to session
		frmPreAuthList.set("benefitType","");
		 request.getSession().removeAttribute("benifitTypeVal");
		 request.getSession().setAttribute("ListmemberDetailVO",alMemberList);
	}
	else if(alMemberList.size()>=1)
	{
	//end of bikki
    memberListData.setData(alMemberList, "search");
   //set the table data object to session
   request.getSession().setAttribute("memberListData",memberListData);
	}
  
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
      log.debug("Inside PreAuthAction doMemberBackward");
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
           log.debug("Inside PreAuthAction doMeberForward");
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
	log.debug("Inside PreAuthGenealAction closeClinicians");
	
	String strActTab=TTKCommon.getActiveTab(request);
	
	if("Oral Preauth Approval".equals(strActTab)){
		return mapping.findForward("oralPreAuthDetail");
	}
	else{
		return mapping.findForward("systemPreAuthDetails");
		
	}
	
		
	}//end of try
	catch(Exception exp)
	{
	return this.processExceptions(request, mapping, new TTKException(exp,"preauthdetail"));
	}//end of catch(Exception exp)
	}
	
	public ActionForward doSelectMemberId(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	try{
	setLinks(request);
	log.debug("Inside PreAuthGenealAction doSelectMemberId ");
	HttpSession session=request.getSession();
	TableData memberListData = (TableData)session.getAttribute("memberListData");
	String strForward="";
	String strActTab=TTKCommon.getActiveTab(request);

	if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	{
		
		MemberDetailVO MemberDetailVO=(MemberDetailVO)memberListData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
		
		if("Oral Preauth Approval".equals(strActTab)){
			DynaActionForm frmPreAuthoral=(DynaActionForm)session.getAttribute("frmPreAuthoral");

			frmPreAuthoral.set("memberSeqID", MemberDetailVO.getMemberSeqID().toString());
			frmPreAuthoral.set("memberId", MemberDetailVO.getMemberId());
			frmPreAuthoral.set("patientName", MemberDetailVO.getPatientName());
			frmPreAuthoral.set("memberAge",TTKCommon.checkNull(MemberDetailVO.getMemberAge()).toString());
			frmPreAuthoral.set("emirateId", MemberDetailVO.getEmirateId());
			frmPreAuthoral.set("payerName", MemberDetailVO.getPayerName());
			frmPreAuthoral.set("payerId", MemberDetailVO.getPayerId());
			frmPreAuthoral.set("insSeqId",MemberDetailVO.getInsSeqId().toString());
			frmPreAuthoral.set("policySeqId", MemberDetailVO.getPolicySeqId().toString());
			frmPreAuthoral.set("patientGender", MemberDetailVO.getPatientGender());
			frmPreAuthoral.set("policyNumber", MemberDetailVO.getPolicyNumber());
			frmPreAuthoral.set("corporateName",MemberDetailVO.getCorporateName());
			frmPreAuthoral.set("policyStartDate", MemberDetailVO.getStart_date());
			frmPreAuthoral.set("policyEndDate", MemberDetailVO.getEnd_date());
			frmPreAuthoral.set("nationality", MemberDetailVO.getNationality());
			frmPreAuthoral.set("sumInsured",TTKCommon.checkNull(MemberDetailVO.getSumInsured()).toString());
			frmPreAuthoral.set("availableSumInsured",TTKCommon.checkNull(MemberDetailVO.getAvailableSumInsured()).toString());
			frmPreAuthoral.set("vipYorN",MemberDetailVO.getVipYorN());
			frmPreAuthoral.set("preMemInceptionDt",MemberDetailVO.getPreMemInceptionDt());
			frmPreAuthoral.set("productName",MemberDetailVO.getProductName());
			frmPreAuthoral.set("eligibleNetworks",MemberDetailVO.getEligibleNetworks());
			frmPreAuthoral.set("payerAuthority",MemberDetailVO.getProvAuthority());
			frmPreAuthoral.set("memberDOB",MemberDetailVO.getMemberDOB());
			
			session.setAttribute("frmPreAuthoral",frmPreAuthoral);
			strForward=strOralPreAuthDetail;
		}else{
		
	DynaActionForm frmPreAuthGeneral=(DynaActionForm)session.getAttribute("frmPreAuthGeneral");
	
	
		frmPreAuthGeneral.set("memberSeqID", MemberDetailVO.getMemberSeqID().toString());
		frmPreAuthGeneral.set("memberId", MemberDetailVO.getMemberId());
		frmPreAuthGeneral.set("patientName", MemberDetailVO.getPatientName());
		frmPreAuthGeneral.set("memberAge",TTKCommon.checkNull(MemberDetailVO.getMemberAge()).toString());
		frmPreAuthGeneral.set("emirateId", MemberDetailVO.getEmirateId());
		frmPreAuthGeneral.set("payerName", MemberDetailVO.getPayerName());
		frmPreAuthGeneral.set("payerId", MemberDetailVO.getPayerId());
		frmPreAuthGeneral.set("insSeqId",MemberDetailVO.getInsSeqId().toString());
		frmPreAuthGeneral.set("policySeqId", MemberDetailVO.getPolicySeqId().toString());
		frmPreAuthGeneral.set("patientGender", MemberDetailVO.getPatientGender());
		frmPreAuthGeneral.set("policyNumber", MemberDetailVO.getPolicyNumber());
		frmPreAuthGeneral.set("corporateName",MemberDetailVO.getCorporateName());
		frmPreAuthGeneral.set("policyStartDate", MemberDetailVO.getStart_date());
		frmPreAuthGeneral.set("policyEndDate", MemberDetailVO.getEnd_date());
		frmPreAuthGeneral.set("nationality", MemberDetailVO.getNationality());
		
		frmPreAuthGeneral.set("sumInsured",TTKCommon.checkNull(MemberDetailVO.getSumInsured()).toString());
		
		frmPreAuthGeneral.set("availableSumInsured",TTKCommon.checkNull(MemberDetailVO.getAvailableSumInsured()).toString());
		frmPreAuthGeneral.set("vipYorN",MemberDetailVO.getVipYorN());
		frmPreAuthGeneral.set("preMemInceptionDt",MemberDetailVO.getPreMemInceptionDt());
		frmPreAuthGeneral.set("productName",MemberDetailVO.getProductName());
		frmPreAuthGeneral.set("eligibleNetworks",MemberDetailVO.getEligibleNetworks());
		frmPreAuthGeneral.set("payerAuthority",MemberDetailVO.getProvAuthority());
		frmPreAuthGeneral.set("preMemExitDt",MemberDetailVO.getMemberExitDate());
		frmPreAuthGeneral.set("memberDOB",MemberDetailVO.getMemberDOB());
		frmPreAuthGeneral.set("mobileIsdCode",MemberDetailVO.getMemberAddressVO().getMobileIsdCode());
		frmPreAuthGeneral.set("mobileNbr",MemberDetailVO.getMemberAddressVO().getMobileNbr());
		
		session.setAttribute("frmPreAuthGeneral",frmPreAuthGeneral);
		session.setAttribute("enhancedCase","FRESHCASE");
		strForward=strPreAuthDetail;
		
		}
		
		
	}
	else if("Oral Preauth Approval".equals(strActTab)&&session.getAttribute("ListmemberDetailVO")!=null)
	{
		DynaActionForm frmPreAuthoral=(DynaActionForm)session.getAttribute("frmPreAuthoral");
		 ArrayList <MemberDetailVO>ListmemberDetailVO=(ArrayList<MemberDetailVO>)request.getSession().getAttribute("ListmemberDetailVO");
		 for(MemberDetailVO  MemberDetailVO: ListmemberDetailVO)
		 {

		frmPreAuthoral.set("memberSeqID", MemberDetailVO.getMemberSeqID().toString());
		frmPreAuthoral.set("memberId", MemberDetailVO.getMemberId());
		frmPreAuthoral.set("patientName", MemberDetailVO.getPatientName());
		frmPreAuthoral.set("memberAge",TTKCommon.checkNull(MemberDetailVO.getMemberAge()).toString());
		frmPreAuthoral.set("emirateId", MemberDetailVO.getEmirateId());
		frmPreAuthoral.set("payerName", MemberDetailVO.getPayerName());
		frmPreAuthoral.set("payerId", MemberDetailVO.getPayerId());
		frmPreAuthoral.set("insSeqId",MemberDetailVO.getInsSeqId().toString());
		frmPreAuthoral.set("policySeqId", MemberDetailVO.getPolicySeqId().toString());
		frmPreAuthoral.set("patientGender", MemberDetailVO.getPatientGender());
		frmPreAuthoral.set("policyNumber", MemberDetailVO.getPolicyNumber());
		frmPreAuthoral.set("corporateName",MemberDetailVO.getCorporateName());
		frmPreAuthoral.set("policyStartDate", MemberDetailVO.getStart_date());
		frmPreAuthoral.set("policyEndDate", MemberDetailVO.getEnd_date());
		frmPreAuthoral.set("nationality", MemberDetailVO.getNationality());
		frmPreAuthoral.set("sumInsured",TTKCommon.checkNull(MemberDetailVO.getSumInsured()).toString());
		frmPreAuthoral.set("availableSumInsured",TTKCommon.checkNull(MemberDetailVO.getAvailableSumInsured()).toString());
		frmPreAuthoral.set("vipYorN",MemberDetailVO.getVipYorN());
		frmPreAuthoral.set("preMemInceptionDt",MemberDetailVO.getPreMemInceptionDt());
		frmPreAuthoral.set("productName",MemberDetailVO.getProductName());
		frmPreAuthoral.set("eligibleNetworks",MemberDetailVO.getEligibleNetworks());
		frmPreAuthoral.set("payerAuthority",MemberDetailVO.getProvAuthority());
		frmPreAuthoral.set("memberDOB",MemberDetailVO.getMemberDOB());
		 }
		session.setAttribute("frmPreAuthoral",frmPreAuthoral);
		strForward=strOralPreAuthDetail;
}
	
	else
	{
		if((session.getAttribute("ListmemberDetailVO")!=null))
		{
		DynaActionForm frmPreAuthGeneral=(DynaActionForm)session.getAttribute("frmPreAuthGeneral");
		 ArrayList <MemberDetailVO>ListmemberDetailVO=(ArrayList<MemberDetailVO>)request.getSession().getAttribute("ListmemberDetailVO");
		 for(MemberDetailVO  MemberDetailVO: ListmemberDetailVO)
		 {
		frmPreAuthGeneral.set("memberSeqID", MemberDetailVO.getMemberSeqID().toString());
		frmPreAuthGeneral.set("memberId", MemberDetailVO.getMemberId());
		frmPreAuthGeneral.set("patientName", MemberDetailVO.getPatientName());
		frmPreAuthGeneral.set("memberAge",TTKCommon.checkNull(MemberDetailVO.getMemberAge()).toString());
		frmPreAuthGeneral.set("emirateId", MemberDetailVO.getEmirateId());
		frmPreAuthGeneral.set("payerName", MemberDetailVO.getPayerName());
		frmPreAuthGeneral.set("payerId", MemberDetailVO.getPayerId());
		frmPreAuthGeneral.set("insSeqId",MemberDetailVO.getInsSeqId().toString());
		frmPreAuthGeneral.set("policySeqId", MemberDetailVO.getPolicySeqId().toString());
		frmPreAuthGeneral.set("patientGender", MemberDetailVO.getPatientGender());
		frmPreAuthGeneral.set("policyNumber", MemberDetailVO.getPolicyNumber());
		frmPreAuthGeneral.set("corporateName",MemberDetailVO.getCorporateName());
		frmPreAuthGeneral.set("policyStartDate", MemberDetailVO.getStart_date());
		frmPreAuthGeneral.set("policyEndDate", MemberDetailVO.getEnd_date());
		frmPreAuthGeneral.set("nationality", MemberDetailVO.getNationality());
		frmPreAuthGeneral.set("sumInsured",TTKCommon.checkNull(MemberDetailVO.getSumInsured()).toString());
		frmPreAuthGeneral.set("availableSumInsured",TTKCommon.checkNull(MemberDetailVO.getAvailableSumInsured()).toString());
		frmPreAuthGeneral.set("vipYorN",MemberDetailVO.getVipYorN());
		frmPreAuthGeneral.set("preMemInceptionDt",MemberDetailVO.getPreMemInceptionDt());
		frmPreAuthGeneral.set("productName",MemberDetailVO.getProductName());
		frmPreAuthGeneral.set("eligibleNetworks",MemberDetailVO.getEligibleNetworks());
		frmPreAuthGeneral.set("payerAuthority",MemberDetailVO.getProvAuthority());
		frmPreAuthGeneral.set("preMemExitDt",MemberDetailVO.getMemberExitDate());
		frmPreAuthGeneral.set("memberDOB",MemberDetailVO.getMemberDOB());
		frmPreAuthGeneral.set("mobileIsdCode",TTKCommon.checkNull(MemberDetailVO.getMemberAddressVO().getMobileIsdCode()));
		frmPreAuthGeneral.set("mobileNbr",MemberDetailVO.getMemberAddressVO().getMobileNbr());
		session.removeAttribute("ListmemberDetailVO");
		 }
		session.setAttribute("frmPreAuthGeneral",frmPreAuthGeneral);
		strForward=strPreAuthDetail;
		}
	}
	return this.getForward(strForward, mapping, request);
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
	
	
	
	public boolean	memberAgeValidation(DynaActionForm frmPreAuthGeneral)throws Exception{
		String strProvAuthority=frmPreAuthGeneral.getString("provAuthority");
		String strEncounterTypeId=frmPreAuthGeneral.getString("encounterTypeId");
		boolean status=false;
		if("DHA".equals(strProvAuthority)&&("3".equals(strEncounterTypeId)||"4".equals(strEncounterTypeId))){
	
			String strAdmissionDate=frmPreAuthGeneral.getString("admissionDate");
			String strMemberDOB=frmPreAuthGeneral.getString("memberDOB");
			
			if(strMemberDOB!=null&&!"".equals(strMemberDOB)){	
				
				if(strAdmissionDate!=null&&!"".equals(strAdmissionDate)){
				SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
				Date date1=simpleDateFormat.parse(strMemberDOB);
	
				Date date2=simpleDateFormat.parse(strAdmissionDate);
				
				long sd=(8*24*60*60*1000);
				long dif=date2.getTime()-date1.getTime();
				if(dif<sd){
	
					status=true;
					int days = (int) (dif / (1000*60*60*24));
					frmPreAuthGeneral.set("patientdays",days+"");
				}
			}
			}
	
		}
	return status;
	}
	
	
	
	
	public ActionForward doMemberRuleData(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
	        log.debug("Inside PreAuthAction doMemberRuleData");
	               setLinks(request);
	        PreAuthManager preAuthObject=this.getPreAuthManagerObject();

	   DynaActionForm frmPreAuthList=(DynaActionForm)form;
		String strBenefitType=TTKCommon.checkNull(frmPreAuthList.getString("benefitType"));
	    
		if(strBenefitType.length()!=0)
		{
		String memberSeqID = request.getParameter("memberSeqID");
		String[][] tobBenefits	=	preAuthObject.getMemberRuleData(strBenefitType,memberSeqID);
	    request.getSession().setAttribute("tobBenefitsForMemElig", tobBenefits);
		request.getSession().setAttribute("benifitTypeVal", strBenefitType);
		strBenefitType="";
		}
		else
		{
			  request.getSession().removeAttribute("tobBenefitsForMemElig");
			  request.getSession().removeAttribute("benifitTypeVal");
		}
		

		
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
	
	
	
	
}//end of PreAuthAction