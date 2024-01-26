/**
 * @ (#) OnlineSelectHospitalAction.java 
 * Project      : TTK HealthCare Services
 * File         : OnlineSelectHospitalAction.java
 * Author       : Balaji C R B
 * Company      : Span Systems Corporation
 * Date Created : March 11, 2008
 *
 * @author       : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
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
import com.ttk.dto.enrollment.OnlineAccessPolicyVO;
import com.ttk.dto.onlineforms.OnlineHospitalVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;


/**
 * This class is reusable for searching of hospitals in pre-auth and claims
 * This class also provides option to select the rquired hospital.
 */

public class OnlineSelectHospitalAction extends TTKAction {
	
	private static Logger log = Logger.getLogger( OnlineSelectHospitalAction.class );
	
	//declarations of mode
	private static final String strForward ="Forward";
	private static final String strBackward ="Backward";
	
	//declarations of constants
	private static final String strPreAuthorization="Pre-Auth";
	private static final String strPreAuthorizationIntimation="Pre-Auth Intimation";
	
	//declarations of forwards links
	private static final String strPreAuthHospitalList="preauthhospitallist";
	private static final String strPreAuthHospitalListEmp="preauthhospitallistemp";
	private static final String strPreAuthDetail="preauthdetail";
	
	//Exception Message Identifier
	private static final String strHospSearch="hospitalsearch";
	
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
			setOnlineLinks(request);
			log.info("Inside OnlineSelectHospitalAction doDefault");
			String strHospitalList="";
			TableData hospitalListData = null;
			UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			if((request.getSession()).getAttribute("hospitalListData") != null){
				hospitalListData = (TableData)(request.getSession()).getAttribute("hospitalListData");
			}//end of if((request.getSession()).getAttribute("hospitalListData") != null)
			else{
				hospitalListData = new TableData();
			}//end of else
			strHospitalList = this.getForwardPath(request,userSecurityProfile);
			hospitalListData = new TableData();    //create new table data object
			hospitalListData.createTableInfo("OnlinePreAuthHospitalTable",new ArrayList());//create the required grid table
			request.getSession().setAttribute("hospitalListData",hospitalListData);
			((DynaActionForm)form).initialize(mapping); //reset the form data
			return this.getForward(strHospitalList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strHospSearch));
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
			setOnlineLinks(request);
			log.info("Inside OnlineSelectHospitalAction doSearch");
			String strHospitalList="";	
			UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			OnlineAccessPolicyVO onlinePolicyVO=null;
			Long lngPolicySeqID = null;
			DynaActionForm frmPreAuthIntimation = null;
			//get the session bean from the bean pool
			OnlineAccessManager onlineAccessManagerObj=this.getOnlineAccessManagerObject();
			if(userSecurityProfile.getLoginType().equals("H")){
				onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
				lngPolicySeqID = onlinePolicyVO.getPolicySeqID();
			}//end of if(userSecurityProfile.getLoginType().equals("H")){
			else if(userSecurityProfile.getLoginType().equals("E")){  
				frmPreAuthIntimation=(DynaActionForm)request.getSession().getAttribute("frmPreAuthIntimation");
				lngPolicySeqID = TTKCommon.getLong(frmPreAuthIntimation.getString("policySeqID"));
				log.info("*****policy seq id is : " + lngPolicySeqID);
			}
			TableData hospitalListData = null;	
			if((request.getSession()).getAttribute("hospitalListData") != null){
				hospitalListData = (TableData)(request.getSession()).getAttribute("hospitalListData");
			}//end of if((request.getSession()).getAttribute("hospitalListData") != null)
			else{
				hospitalListData = new TableData();
			}//end of else
			strHospitalList = this.getForwardPath(request,userSecurityProfile);
			//clear the dynaform if visiting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))			
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					hospitalListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(
							request.getParameter("pageId"))));
					return this.getForward(strHospitalList, mapping, request);
				}///end of if(!strPageID.equals(""))
				else
				{
					hospitalListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					hospitalListData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				hospitalListData.createTableInfo("OnlinePreAuthHospitalTable",null);
				hospitalListData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,lngPolicySeqID));
				hospitalListData.modifySearchData("search");
			}//end of else
			//fetch the data from the data access layer and set the data to table object			
			ArrayList alHospital=onlineAccessManagerObj.getHospitalList(hospitalListData.getSearchData());
			hospitalListData.setData(alHospital, "search");
			//set the table data object to session
			request.getSession().setAttribute("hospitalListData",hospitalListData);
			//finally return to the grid screen
			return this.getForward(strHospitalList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strHospSearch));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			setOnlineLinks(request);
			log.info("Inside OnlineSelectHospitalAction doForward");
			String strHospitalList="";
			UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			//get the session bean from the bean pool
			OnlineAccessManager onlineAccessManagerObj=this.getOnlineAccessManagerObject();
			TableData hospitalListData = null;
			if((request.getSession()).getAttribute("hospitalListData") != null){
				hospitalListData = (TableData)(request.getSession()).getAttribute("hospitalListData");
			}//end of if((request.getSession()).getAttribute("hospitalListData") != null)
			else{
				hospitalListData = new TableData();
			}//end of else
			strHospitalList = this.getForwardPath(request,userSecurityProfile);
			hospitalListData.modifySearchData(strForward);//modify the search data
			ArrayList alHospitalList=onlineAccessManagerObj.getHospitalList(hospitalListData.getSearchData());			
			hospitalListData.setData(alHospitalList, strForward);//set the table data
			//set the table data object to session
			request.getSession().setAttribute("hospitalListData",hospitalListData);
			//finally return to the grid screen
			return this.getForward(strHospitalList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strHospSearch));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			setOnlineLinks(request);
			log.info("Inside OnlineSelectHospitalAction doBackWard");
			String strHospitalList="";
			UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			//get the session bean from the bean pool
			OnlineAccessManager onlineAccessManagerObj=this.getOnlineAccessManagerObject();
			TableData hospitalListData = null;
			if((request.getSession()).getAttribute("hospitalListData") != null){
				hospitalListData = (TableData)(request.getSession()).getAttribute("hospitalListData");
			}//end of if((request.getSession()).getAttribute("hospitalListData") != null)
			else{
				hospitalListData = new TableData();
			}//end of else
			strHospitalList = this.getForwardPath(request,userSecurityProfile);
			hospitalListData.modifySearchData(strBackward);//modify the search data
			ArrayList alHospitalList=onlineAccessManagerObj.getHospitalList(hospitalListData.getSearchData());
			hospitalListData.setData(alHospitalList, strBackward);//set the table data
			//set the table data object to session
			request.getSession().setAttribute("hospitalListData",hospitalListData);
			//finally return to the grid screen
			return this.getForward(strHospitalList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strHospSearch));
		}//end of catch(Exception exp)
	}//end of doBackWard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to Select Hospital.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.info("Inside OnlineSelectHospitalAction doSelectHospital ");
			TableData hospitalListData = null;
			String strGeneralDetail="";
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strHospitalValue=null;
			String strHospitalName=null;
			Long lngHospSeqID=null;
			String strAddress=null;
			String strState=null;			
			
			if((request.getSession()).getAttribute("hospitalListData") != null){
				hospitalListData = (TableData)(request.getSession()).getAttribute("hospitalListData");
			}//end of if((request.getSession()).getAttribute("hospitalListData") != null)
			else{
				hospitalListData = new TableData();
			}//end of else
			OnlineHospitalVO onlineHospitalVO=null;
			DynaActionForm frmPreAuthIntimationDetails=null;
			if(strActiveSubLink.equals(strPreAuthorization)||strActiveSubLink.equals(strPreAuthorizationIntimation))   //setting the forward paths based on the ActiveLink
			{
				//info this for gng back to previous detail screen where hospital details values have to be loaded
				strGeneralDetail=strPreAuthDetail;
			}//end of if(strActiveSubLink.equals(strPreAuthorization)||strActiveSubLink.equals(strPreAuthorizationIntimation)) 		
			frmPreAuthIntimationDetails=(DynaActionForm)request.getSession().getAttribute("frmPreAuthIntimationDetails");
			strHospitalValue = frmPreAuthIntimationDetails.getString("hospitalvalue");
			//on click of edit in hospital search screen
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				//get the selected hospital info
				onlineHospitalVO = (OnlineHospitalVO)hospitalListData.getRowInfo(
						Integer.parseInt(request.getParameter("rownum")));
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			//when Pre-Auth Request comes			
			if(onlineHospitalVO!=null)
			{
				strHospitalName=onlineHospitalVO.getHospitalName();
				lngHospSeqID=onlineHospitalVO.getHospSeqId();
				strAddress=onlineHospitalVO.getAddress();
				strState=onlineHospitalVO.getStateName();	
				log.debug("HospitalName is" + strHospitalName);
				log.debug("hosp seq id is " + lngHospSeqID);
				log.debug("address is " + strAddress);
				log.debug("state is " + strState);				
			}//end of if(onlineHospitalVo!=null)
			if(strActiveSubLink.equals(strPreAuthorization)||strActiveSubLink.equals(strPreAuthorizationIntimation))
			{
				//getting frmOnlineHospital from session 
				//and loading the selected hospital information
				DynaActionForm frmOnlineHospital = (DynaActionForm)frmPreAuthIntimationDetails.get("onlineHospitalVO");				
				if(strHospitalValue.equals("first")){
					frmOnlineHospital.set("hospitalName1",strHospitalName);
					frmOnlineHospital.set("hospSeqId1",lngHospSeqID+"");
					frmOnlineHospital.set("address1",strAddress);
					frmOnlineHospital.set("stateName1",strState);
					frmOnlineHospital.set("doctorName1","");
					frmOnlineHospital.set("doctorPhoneNbr1","");
					frmOnlineHospital.set("estimatedCost1",null);
					frmOnlineHospital.set("roomType1","");
				}//end of if(strHospitalIndex.equals("first"))
				else if(strHospitalValue.equals("second")){
					frmOnlineHospital.set("hospitalName2",strHospitalName);
					frmOnlineHospital.set("hospSeqId2",lngHospSeqID+"");
					frmOnlineHospital.set("address2",strAddress);
					frmOnlineHospital.set("stateName2",strState);
					frmOnlineHospital.set("doctorName2","");
					frmOnlineHospital.set("doctorPhoneNbr2","");
					frmOnlineHospital.set("estimatedCost2",null);
					frmOnlineHospital.set("roomType2","");
				}//end of if(strHospitalIndex.equals("second"))
				else if(strHospitalValue.equals("third")){
					frmOnlineHospital.set("hospitalName3",strHospitalName);
					frmOnlineHospital.set("hospSeqId3",lngHospSeqID+"");
					frmOnlineHospital.set("address3",strAddress);
					frmOnlineHospital.set("stateName3",strState);
					frmOnlineHospital.set("doctorName3","");
					frmOnlineHospital.set("doctorPhoneNbr3","");
					frmOnlineHospital.set("estimatedCost3",null);
					frmOnlineHospital.set("roomType3","");
				}//end of if(strHospitalIndex.equals("third"))
				frmPreAuthIntimationDetails.set("onlineHospitalVO",frmOnlineHospital);
				frmPreAuthIntimationDetails.set("frmChanged","changed");					
			}//end of if(strActiveSubLink.equals(strPreAuthorization)||strActiveSubLink.equals(strPreAuthorizationIntimation))	
			request.getSession().removeAttribute("hospitalListData");
			request.getSession().removeAttribute("frmSelectHospital");
			request.getSession().setAttribute("frmPreAuthIntimationDetails",frmPreAuthIntimationDetails);
			return this.getForward(strGeneralDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strHospSearch));
		}//end of catch(Exception exp)
	}//end of doSelectHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to close the hospital list and going back to Pre-Auth Intimation screen
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
			setOnlineLinks(request);
			log.info("Inside OnlineSelectHospitalAction doClose ");
			String strGeneralDetail="";
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if(strActiveSubLink.equals(strPreAuthorization)||strActiveSubLink.equals(strPreAuthorizationIntimation))
					 //setting the forward paths based on the ActiveLink
			{
				//info this for gng back to previous detail screen where hospital details values have to be loaded
				strGeneralDetail=strPreAuthDetail;
			}//end of if(strActiveSubLink.equals(strPreAuthorization)||(strActiveSubLink.equals(strPreAuthorizationIntimation))		
			return this.getForward(strGeneralDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strHospSearch));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	/**
	 * Returns a string which contains the Forward Path
	 * @param request HttpServletRequest object which contains information required for buiding the Forward Path
	 * @return String which contains the comma separated sequence id's to be deleted
	 */
	private String getForwardPath(HttpServletRequest request,UserSecurityProfile userSecurityProfile) throws TTKException{
		String strHospitalList="";
		String strActiveSubLink=TTKCommon.getActiveSubLink(request);		
		try{
			if(strActiveSubLink.equals(strPreAuthorization)||strActiveSubLink.equals(strPreAuthorizationIntimation))   //setting the forward paths based on the ActiveLink
			{
				log.info("inside test1 strHospitallist is " + strPreAuthHospitalList );
				if(userSecurityProfile.getLoginType().equals("H")){
					strHospitalList=strPreAuthHospitalList;
				}//end of if(userSecurityProfile.getLoginType().equals("H"))
				else if(userSecurityProfile.getLoginType().equals("E")){
					strHospitalList=strPreAuthHospitalListEmp;
				}//end of else if(userSecurityProfile.getLoginType().equals("E"))
				
			}//end of if(strActiveSubLink.equals(strPreAuthorization)||strActiveSubLink.equals(strPreAuthorizationIntimation))		
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strHospSearch);
		}//end of catch
		return strHospitalList;
	}//end of getForwardPath(HttpServletRequest request)
	
	/**
	 * Returns the ArrayList which contains the populated search criteria elements.
	 * @param frmSelectHospital DynaActionForm will contains the values of corresponding fields.
	 * @param request HttpServletRequest object which contains the search parameter that is built.
	 * @return alSearchParams ArrayList.
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSelectHospital,HttpServletRequest request,Long lngPolicySeqID)
	throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
		alSearchParams.add(lngPolicySeqID);
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSelectHospital.get("sHospitalName")));
		alSearchParams.add((String)frmSelectHospital.get("stateCode"));
		alSearchParams.add((String)frmSelectHospital.get("cityCode"));
		if(userSecurityProfile.getLoginType().equals("H")) {
			alSearchParams.add(TTKCommon.getUserSeqId(request));
		}else if(userSecurityProfile.getLoginType().equals("E")) {
			alSearchParams.add(new Long(1));
		}//end of else
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmSelectHospital,HttpServletRequest request)
	
	
	/**
	 * Returns the onlineAccessManager session object for invoking methods on it.
	 * @return onlineAccessManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private OnlineAccessManager getOnlineAccessManagerObject() throws TTKException
	{
		OnlineAccessManager onlineAccessManager = null;
		try
		{
			if(onlineAccessManager == null)
			{
				InitialContext ctx = new InitialContext();
				onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
				log.debug("Inside getOnlineAccessManagerObject: onlineAccessManager: " + onlineAccessManager);
			}//end if if(onlineAccessManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strHospSearch);
		}//end of catch
		return onlineAccessManager;
	}//end of getOnlineAccessManagerObject()	
	
}//end of OnlineSelectHospitalAction