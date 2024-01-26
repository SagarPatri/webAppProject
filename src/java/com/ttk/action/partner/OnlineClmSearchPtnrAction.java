/**
 * as per Hospital Login
 * @ (#) OnlineCLMSearchHospActionMar 24, 2014
 *  Author       :Satya Moganti
 * Company      : Rcs Technologies
 * Date Created : march 24 ,2014
 *
 * @author       :Satya moganti
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.partner;

import java.util.ArrayList;
import java.util.Calendar;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.hospital.HospPreAuthVO;
//import com.ttk.dto.onlineforms.OnlineInsPolicyVO;
//import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import java.util.Date;//kocnewhosp1
import java.text.*;//kocnewhosp1

/**
 * This class is used for Searching the List Policies to see the Online Account Info.
 * This also provides deletion and updation of products.
 */
public class OnlineClmSearchPtnrAction extends TTKAction {

    private static final Logger log = Logger.getLogger( OnlineClmSearchPtnrAction.class );

    //Modes.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";

    private static final String strRegular="Regular";    //identfier for Regular Pre-Auth
	private static final String strEnhanced="Enhanced";  //identifier for Enhanced Pre-Auth
	private static final String strRegularPreAuth="PAT";
	private static final String strEnhancedPreAuth="ICO";

    // Action mapping forwards.
    //Exception Message Identifier
    private static final String strHospSerachInfo="onlinehospitalinfo";
    private static final String strHosClaimDetail="hospclaimdetail";
    private static final String strHosNotify="ptnrnotify";

    private static final String strOnlineClaimList="partneronlineclmlist";

    
    
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
            log.info("Inside the doDefault method of OnlineClmSearchHospAction");
            setOnlineLinks(request);
            TableData tableData =null;

            DynaActionForm frmClmHospSearch =(DynaActionForm)form;
            frmClmHospSearch.initialize(mapping);     //reset the form data
            //get the tbale data from session if exists
            tableData =TTKCommon.getTableData(request);
            //create new table data object
            tableData = new TableData();
            //create the required grid table
            tableData.createTableInfo("HospClaimsTable",new ArrayList());
            request.getSession().setAttribute("tableData",tableData);
            //((DynaActionForm)form).initialize(mapping);//reset the form data
            return this.getForward(strOnlineClaimList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
        }//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
    
    
    public ActionForward doDashBoardData(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.info("Inside the doDashBoardData method of OnlinePatSearchPtnrAction");
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
			OnlineAccessManager onlineAccessManagerObject = null;
			String findForward="";
			//TableData tableData=null;	
			Document docClaims=null;
			Document docPreauth=null;
			
			String sFromDate=TTKCommon.checkNull(request.getParameter("sFromDate"));
			String sToDate=TTKCommon.checkNull(request.getParameter("sToDate"));
			String preAuthClm	=	TTKCommon.checkNull(request.getParameter("preAuthClm"));
			request.getSession().setAttribute("preAuthClm", preAuthClm);
			request.setAttribute("preAuthClm", preAuthClm);
			onlineAccessManagerObject = this.getOnlineAccessManagerObject();
				
			if("CLM".equals(preAuthClm)){
			 docClaims=(Document)onlineAccessManagerObject.getPartnerDashBoard(userSecurityProfile.getPtnrSeqId(),sFromDate,sToDate,"Claims");
			 request.getSession().removeAttribute("onlineptnrhomeclaim");
			 request.getSession().setAttribute("onlineptnrhomeclaim",docClaims);
			
			}//end of if(TTKCommon.getActiveTab(request).equalsIgnoreCase("Claims"))
			else if("PAT".equals(preAuthClm)){
			 docPreauth=(Document)onlineAccessManagerObject.getPartnerDashBoard(userSecurityProfile.getPtnrSeqId(),sFromDate,sToDate,"Preauth");
			 request.getSession().removeAttribute("onlineptnrhomepreauth");
			 request.getSession().setAttribute("onlineptnrhomepreauth",docPreauth);
			}//end of else if(TTKCommon.getActiveTab(request).equalsIgnoreCase("Pre-Auth"))
			else{
				 docPreauth=(Document)onlineAccessManagerObject.getPartnerDashBoard(userSecurityProfile.getPtnrSeqId(),sFromDate,sToDate,"Preauth");
				 docClaims=(Document)onlineAccessManagerObject.getPartnerDashBoard(userSecurityProfile.getPtnrSeqId(),sFromDate,sToDate,"Claims");
				 request.getSession().removeAttribute("onlineptnrhomepreauth");
				 request.getSession().removeAttribute("onlineptnrhomeclaim");
				 request.getSession().setAttribute("onlineptnrhomepreauth",docPreauth);
				 request.getSession().setAttribute("onlineptnrhomeclaim",docClaims);
			}//end of else 
			if("CLM".equals(preAuthClm)){
				 findForward="partnerclaimdashboard";
			}
			else if("PAT".equals(preAuthClm)) {
				 findForward="partnerpreauthdashboard";
			}
			else{
				findForward="partnerpreauthdashboard";
			}
			//set the table data object to session
			//finally return to the grid screen
			return mapping.findForward(findForward);
	
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
		}//end of catch(Exception exp)
	}//end ofDashBoardData(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception

    
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
    public ActionForward doDashBoardDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doDefault method of OnlineClmSearchPtnrAction");
            setOnlineLinks(request);
            TableData tableData =null;

            DynaActionForm frmClmHospSearch =(DynaActionForm)form;
            
            return this.getForward("partnerclaimdashboard", mapping, request);//partnerclaimdashboard
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
        }//end of catch(Exception exp)
    }//end of doDashBoardDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
    
    //kocnewhosp1 
    /*
     * this Method is used to search the pre-auths previous days by clicking previous button
     * */
    public ActionForward doDashBoardDataPrev(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside the doDashBoardDataPrev method of OnlineClmSearchPtnrAction");
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
			OnlineAccessManager onlineAccessManagerObject = null;
			String findForward="";
			//TableData tableData=null;	
			Document docClaims=null;
			Document docPreauth=null;
			
			String sFromDate=TTKCommon.checkNull(request.getParameter("sFromDate"));
			String sToDate=TTKCommon.checkNull(request.getParameter("sToDate"));
			String preAuthClm=(String) (request.getParameter("preAuthClm")==""?request.getSession().getAttribute("preAuthClm"):TTKCommon.checkNull(request.getParameter("preAuthClm")));
			request.setAttribute("preAuthClm", preAuthClm);
			onlineAccessManagerObject = this.getOnlineAccessManagerObject();
				
			if("CLM".equals(preAuthClm))		{
				sFromDate=TTKCommon.checkNull(request.getParameter("sClmStartDate"));
				sToDate=TTKCommon.checkNull(request.getParameter("sClmEndDate"));

				// calculating Previous day
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar c = Calendar.getInstance();
				Calendar c1 = Calendar.getInstance();
				c.setTime(sdf.parse(sToDate)); // From date.
				c1.setTime(sdf.parse(sToDate)); // From date.
				

				c.add(Calendar.MONTH, -1);// Subtracting 1 Month
				c.set(Calendar.DAY_OF_MONTH, 1);//First day of the Month
				

				 //c1.add(Calendar.MONTH, 1);// For calculating last day of the month
				 c1.set(Calendar.DAY_OF_MONTH, 1);// For calculating last day of the month
				 c1.add(Calendar.DATE, -1);// For calculating last day of the month
				
				
				//c1.add(Calendar.MONTH,0);// Subtracting 1 Month
				sFromDate	=	sdf.format(c.getTime());
				sToDate	=	sdf.format(c1.getTime());
				
				 
				 docClaims=(Document)onlineAccessManagerObject.getPartnerDashBoard(userSecurityProfile.getPtnrSeqId(),sFromDate,sToDate,"Claims");
				 request.getSession().removeAttribute("onlineptnrhomeclaim");
				 request.getSession().setAttribute("onlineptnrhomeclaim",docClaims);
				
			
			
			}//end of if(TTKCommon.getActiveTab(request).equalsIgnoreCase("Claims"))
			else if("PAT".equals(preAuthClm)){
				
				sFromDate=TTKCommon.checkNull(request.getParameter("sPatStartDate"));
				sToDate=TTKCommon.checkNull(request.getParameter("sPatEndDate"));
				// calculating Previous day
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar c = Calendar.getInstance();
				 //c.setTime(sdf.parse(strCurrentDate)); // Now use today date.
				c.setTime(sdf.parse(sToDate)); // To date.
				 c.add(Calendar.DATE, -1); // Subtracting 1 day
				 sFromDate	=	sdf.format(c.getTime());
				 sToDate	=	sdf.format(c.getTime());
				
			 docPreauth=(Document)onlineAccessManagerObject.getPartnerDashBoard(userSecurityProfile.getPtnrSeqId(),sFromDate,sToDate,"Preauth");
			 request.getSession().removeAttribute("onlineptnrhomepreauth");
			 request.getSession().setAttribute("onlineptnrhomepreauth",docPreauth);
			}//end of else if(TTKCommon.getActiveTab(request).equalsIgnoreCase("Pre-Auth"))
			else{
				 docPreauth=(Document)onlineAccessManagerObject.getPartnerDashBoard(userSecurityProfile.getPtnrSeqId(),sFromDate,sToDate,"Preauth");
				 docClaims=(Document)onlineAccessManagerObject.getPartnerDashBoard(userSecurityProfile.getPtnrSeqId(),sFromDate,sToDate,"Claims");
				 request.getSession().removeAttribute("onlineptnrhomepreauth");
				 request.getSession().removeAttribute("onlineptnrhomeclaim");
				 request.getSession().setAttribute("onlineptnrhomepreauth",docPreauth);
				 request.getSession().setAttribute("onlineptnrhomeclaim",docClaims);
			}//end of else 
			if("PAT".equals(preAuthClm))
			{
				 findForward="partnerpreauthdashboard";
			}
			else if("CLM".equals(preAuthClm)) {
				 findForward="partnerclaimdashboard";
			}
			else{
				findForward="partnerpreauthdashboard";
			}
			//set the table data object to session
			//finally return to the grid screen
			return mapping.findForward(findForward);
	
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
		}//end of catch(Exception exp)
	}//end doDashBoardDataPrev(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception

    
    public ActionForward doDashBoardDataNext(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.info("Inside the doDashBoardDataNext method of OnlineClmSearchPtnrAction");
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
			OnlineAccessManager onlineAccessManagerObject = null;
			String findForward="";
			//TableData tableData=null;	
			Document docClaims=null;
			Document docPreauth=null;
			
			String sFromDate=TTKCommon.checkNull(request.getParameter("sFromDate"));
			String sToDate=TTKCommon.checkNull(request.getParameter("sToDate"));
			String preAuthClm=(String) (request.getParameter("preAuthClm")==""?request.getSession().getAttribute("preAuthClm"):TTKCommon.checkNull(request.getParameter("preAuthClm")));
			request.setAttribute("preAuthClm", preAuthClm);
			onlineAccessManagerObject = this.getOnlineAccessManagerObject();
				
			if("CLM".equals(preAuthClm))		{
				sFromDate=TTKCommon.checkNull(request.getParameter("sClmStartDate"));
				sToDate=TTKCommon.checkNull(request.getParameter("sClmEndDate"));

				// calculating Next day
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar c = Calendar.getInstance();
				Calendar c1 = Calendar.getInstance();
				c.setTime(sdf.parse(sToDate)); // To date.
				c1.setTime(sdf.parse(sToDate)); // To date.
				
				 
				 c.add(Calendar.MONTH, 1);// Next Month
				 c.set(Calendar.DAY_OF_MONTH, 1);//First day of the Month
				 
				 c1.add(Calendar.MONTH,2);// Adding 2 Month
				 c1.set(Calendar.DAY_OF_MONTH, 1);// For calculating last day of the month
				 c1.add(Calendar.DATE, -1);// For calculating last day of the month
				 
				 sFromDate	=	sdf.format(c.getTime());
				 sToDate	=	sdf.format(c1.getTime());
					 
				 
			
				
			 docClaims=(Document)onlineAccessManagerObject.getPartnerDashBoard(userSecurityProfile.getPtnrSeqId(),sFromDate,sToDate,"Claims");
			 request.getSession().removeAttribute("onlineptnrhomeclaim");
			 request.getSession().setAttribute("onlineptnrhomeclaim",docClaims);
			
			}//end of if(TTKCommon.getActiveTab(request).equalsIgnoreCase("Claims"))
			else if("PAT".equals(preAuthClm)){
				
				sFromDate=TTKCommon.checkNull(request.getParameter("sPatStartDate"));
				sToDate=TTKCommon.checkNull(request.getParameter("sPatEndDate"));
				// calculating Previous day
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				Calendar c = Calendar.getInstance();
				c.setTime(sdf.parse(sToDate)); // To date.
				 c.add(Calendar.DATE, 1); // Adding 1 day
				 sFromDate	=	sdf.format(c.getTime());
				 sToDate	=	sdf.format(c.getTime());
				
			 docPreauth=(Document)onlineAccessManagerObject.getPartnerDashBoard(userSecurityProfile.getPtnrSeqId(),sFromDate,sToDate,"Preauth");
			 request.getSession().removeAttribute("onlineptnrhomepreauth");
			 request.getSession().setAttribute("onlineptnrhomepreauth",docPreauth);
			}//end of else if(TTKCommon.getActiveTab(request).equalsIgnoreCase("Pre-Auth"))
			else{
				 docPreauth=(Document)onlineAccessManagerObject.getPartnerDashBoard(userSecurityProfile.getPtnrSeqId(),sFromDate,sToDate,"Preauth");
				 docClaims=(Document)onlineAccessManagerObject.getPartnerDashBoard(userSecurityProfile.getPtnrSeqId(),sFromDate,sToDate,"Claims");
				 request.getSession().removeAttribute("onlineptnrhomepreauth");
				 request.getSession().removeAttribute("onlineptnrhomeclaim");
				 request.getSession().setAttribute("onlineptnrhomepreauth",docPreauth);
				 request.getSession().setAttribute("onlineptnrhomeclaim",docClaims);
			}//end of else 
			if("PAT".equals(preAuthClm))
			{
				 findForward="partnerpreauthdashboard";
			}
			else if("CLM".equals(preAuthClm)) {
				 findForward="partnerclaimdashboard";
			}
			else{
				findForward="partnerpreauthdashboard";
			}
			//set the table data object to session
			//finally return to the grid screen
			return mapping.findForward(findForward);
	
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
		}//end of catch(Exception exp)
	}//end doDashBoardDataNext(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

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
            log.debug("Inside the doSearch method of OnlineClmSearchHospAction");
			OnlineAccessManager onlineAccessManager = null;
			TableData tableData=null;
			String strPageID =""; 
			String strSortID ="";	
			ArrayList alOnlineAccList = null;
			onlineAccessManager = this.getOnlineAccessManagerObject();
			tableData=(TableData)request.getSession().getAttribute("tableData");
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strOnlineClaimList));
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
				tableData.createTableInfo("HospClaimsTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));						
				tableData.modifySearchData("search");
				//sorting based on investSeqID in descending order													
			}//end of else
			alOnlineAccList= onlineAccessManager.getHospClaimsList(tableData.getSearchData());
			tableData.setData(alOnlineAccList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strOnlineClaimList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
        }//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                            //HttpServletResponse response)
    
    
    public ActionForward doDashBoardSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside the doSearch method of OnlinePatSearchHospAction");
			OnlineAccessManager onlineAccessManager = null;
			TableData tableData=null;
			
			
			DynaActionForm frmClmHospSearch =(DynaActionForm)form;
			frmClmHospSearch.initialize(mapping);     //reset the form data
			frmClmHospSearch.set("sStatus",TTKCommon.checkNull(request.getParameter("sStatus")));
			frmClmHospSearch.set("sClmStartDate",TTKCommon.checkNull((request.getParameter("sClmStartDate"))));//!=null) ?  TTKCommon.checkNull(request.getParameter("sFromDate")) :  TTKCommon.checkNull(request.getParameter("sClmStartDate")));
			frmClmHospSearch.set("sClmEndDate",TTKCommon.checkNull((request.getParameter("sClmEndDate"))));//!=null) ?  TTKCommon.checkNull(request.getParameter("sToDate")) :  TTKCommon.checkNull(request.getParameter("sToDate")));
			ArrayList alOnlineAccList = null;
			onlineAccessManager = this.getOnlineAccessManagerObject();
			
			 tableData =TTKCommon.getTableData(request);
				//create the required grid table
			tableData.createTableInfo("HospClaimsTable",null);
			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));						
			tableData.modifySearchData("search");
				//sorting based on investSeqID in descending order		
			alOnlineAccList= onlineAccessManager.getHospClaimsList(tableData.getSearchData());
			tableData.setData(alOnlineAccList, "search");
			
			
			frmClmHospSearch.set("sClmStartDate",null);
			frmClmHospSearch.set("sClmEndDate",null);
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strOnlineClaimList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

    
    
    
  
    /*
     * 
     */
    		
  
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
            log.debug("Inside the doBackward method of OnlineClmSearchHospAction");
            setOnlineLinks(request);
            //get the session bean from the bean pool for each excecuting thread
            OnlineAccessManager onlineAccessManager=null;
            TableData tableData =null;
            ArrayList alPolicyList =null;
            onlineAccessManager =this.getOnlineAccessManagerObject();
            tableData = TTKCommon.getTableData(request);
            tableData.modifySearchData(strBackward);
            alPolicyList = onlineAccessManager.getHospClaimsList(tableData.getSearchData());
            tableData.setData(alPolicyList, strBackward);
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward(strOnlineClaimList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
        }//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest
            //request,HttpServletResponse response)

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
            log.debug("Inside the doForward method of OnlineClmSearchHospAction");
            setOnlineLinks(request);
            //get the session bean from the bean pool for each excecuting thread
            OnlineAccessManager onlineAccessManager=null;
            TableData tableData =null;
            ArrayList alPolicyList =null;
            onlineAccessManager =this.getOnlineAccessManagerObject();
            tableData = TTKCommon.getTableData(request);
            tableData.modifySearchData(strForward);
            alPolicyList = onlineAccessManager.getHospClaimsList(tableData.getSearchData());
            tableData.setData(alPolicyList, strForward);
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward(strOnlineClaimList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
        }//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
    
   
    private OnlineAccessManager getOnlineAccessManagerObject() throws TTKException
    {
    	OnlineAccessManager onlineAccessManager = null;
        try
        {
            if(onlineAccessManager == null)
            {
                InitialContext ctx = new InitialContext();
                //onlineAccessManager = (OnlineAccessManager) ctx.lookup(OnlineAccessManager.class.getName());
                onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strHospSerachInfo);
        }//end of catch
        return onlineAccessManager;
    }//end of getOnlineAccessManagerObject()

    
    
    
    
    private ArrayList<Object> populateDashBoardSearch(HttpServletRequest request) throws TTKException
	{
		// build the column names along with their values to be searched
		ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();
		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
		//prepare the search BOX parameters
		Long lngHospSeqId =userSecurityProfile.getHospSeqId();
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)request.getParameter("sStatus")));
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(lngHospSeqId);
		return alSearchBoxParams;
	}//end of populateSearchCriteria(DynaActionForm frmPatHospSearch,HttpServletRequest request)  
	
    
    
        /**
	     * this method will add search criteria fields and values to the arraylist and will return it
	     * @param frmClmHospSearch formbean which contains the search fields
	     * @return ArrayList contains search parameters
	     */
	    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmClmHospSearch,HttpServletRequest request) throws TTKException
	    {
	//      build the column names along with their values to be searched
	        ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();
	        UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	        //prepare the search BOX parameters
	      //  Long lngHospSeqId =userSecurityProfile.getHospSeqId();
    	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sClaimNumber")));
    	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sAuthNumber")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sEnrollmentNumber")));
	    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sStatus")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sPolicyNumber")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sDOA")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sDateOfClm")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sClmStartDate")));
			alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sClmEndDate")));   //sPatStartDate,sPatEndDate,sClmStartDate,sClmEndDate
	        alSearchBoxParams.add(userSecurityProfile.getHospSeqId());
	        //new Req
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sPatientName")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sDischargeDate")));
	    	return alSearchBoxParams;
	    }//end of populateSearchCriteria(DynaActionForm frmClmHospSearch,HttpServletRequest request) 
	    
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
				return this.getForward(strOnlineClaimList, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
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
	    		setOnlineLinks(request);
	    		//get the tbale data from session if exists
				TableData tableData =TTKCommon.getTableData(request);
	    		DynaActionForm frmClmHospSearch=(DynaActionForm)form;
				if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				{
					HospPreAuthVO preAuthVO=(HospPreAuthVO)tableData.getRowInfo(Integer.parseInt((String)
																			(request.getParameter("rownum"))));
					this.addToWebBoard(preAuthVO, request);
				}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				return mapping.findForward(strHosClaimDetail);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
			}//end of catch(Exception exp)
	    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	    
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
	    	HospPreAuthVO preAuthVO=null;

	    	if(strChk!=null&&strChk.length!=0)
	    	{
	    		for(int i=0; i<strChk.length;i++)
	    		{
	    			cacheObject = new CacheObject();
	    			preAuthVO=(HospPreAuthVO)tableData.getData().get(Integer.parseInt(strChk[i]));
	    			cacheObject.setCacheId(this.prepareWebBoardId(preAuthVO));
	    			cacheObject.setCacheDesc(preAuthVO.getPreAuthNo());
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
	    private void addToWebBoard(HospPreAuthVO preAuthVO, HttpServletRequest request)throws TTKException
	    {
	    	Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
	    	ArrayList<Object> alCacheObject = new ArrayList<Object>();
	    	CacheObject cacheObject = new CacheObject();
	    	cacheObject.setCacheId(this.prepareWebBoardId(preAuthVO)); //set the cacheID
	    	cacheObject.setCacheDesc(preAuthVO.getPreAuthNo());
	    	alCacheObject.add(cacheObject);
	    	//if the object(s) are added to the web board, set the current web board id
	    	toolbar.getWebBoard().addToWebBoardList(alCacheObject);
	    	toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());

	    	//webboardinvoked attribute will be set as true in request scope
	    	//to avoid the replacement of web board id with old value if it is called twice in same request scope
	    	request.setAttribute("webboardinvoked", "true");
	    }//end of addToWebBoard(HospPreAuthVO preAuthVO, HttpServletRequest request,String strIdentifier)throws TTKException

	    /**
	     * This method prepares the Weboard id for the selected Policy
	     * @param HospPreAuthVO  preAuthVO for which webboard id to be prepared
	     * * @param String  strIdentifier whether it is preauth or enhanced preauth
	     * @return Web board id for the passedVO
	     */
	    private String prepareWebBoardId(HospPreAuthVO preAuthVO)throws TTKException
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



	    
	    
	  

	    //kocnewhosp1
	    public ActionForward doViewNotify(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception{
			try{
				Document onlinehome=null;
			log.debug("Inside the doViewNotify method of ClaimsAction");
			setOnlineLinks(request);
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			DynaActionForm frmClmHospSearch=(DynaActionForm)form;
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
			HospPreAuthVO preAuthVO=(HospPreAuthVO)tableData.getRowInfo(Integer.parseInt((String)
												(request.getParameter("rownum"))));
			this.addToWebBoard(preAuthVO, request);
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile"));
			String strGroupID = userSecurityProfile.getGroupID();
			String strUserID = userSecurityProfile.getUSER_ID();
			
			OnlineAccessManager onlineAccessManager=null;
            ArrayList alPolicyList =null;
            onlineAccessManager =this.getOnlineAccessManagerObject();            
            alPolicyList = onlineAccessManager.getOnlineHomeDetails(strGroupID,null,new Long(0),"HOS",strUserID);
            
            onlinehome = (Document) alPolicyList.get(0);
			request.setAttribute("pwdalert",(String)alPolicyList.get(1));
			request.setAttribute("windowprdalert", (String)alPolicyList.get(2));
			request.setAttribute("onlinehome",onlinehome);
			
			
			return mapping.findForward(strHosNotify);
			}//end of try
			catch(TTKException expTTK)
			{
			return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
			}//end of catch(Exception exp)

}//end of doViewNotify(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	    

	    
}//end of OnlineClmSearchHospAction
