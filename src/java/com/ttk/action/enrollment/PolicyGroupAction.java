/**
 * @ (#) PolicyGroupAction.java Feb 3, 2006
 * Project      : TTK HealthCare Services
 * File         : PolicyGroupAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Feb 3, 2006
 *
 * @author       :  Chandrasekaran J
 * Modified by   :  Arun K N
 * Modified date :  May 10, 2007
 * Reason        :  Conversion to DispatchAction
 */

package com.ttk.action.enrollment;

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
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.business.enrollment.PolicyManager;
import com.ttk.business.misreports.ReportManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.enrollment.PolicyGroupVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This class is reusable for searching of list of corporate in Enrollment,Pre-Authorization,Claims,
 * Finance,Customer Care,Support flows.
 * This class also provides option for selection of any corporatre information.
 */

public class PolicyGroupAction extends TTKAction
{

    private static Logger log = Logger.getLogger( PolicyGroupAction.class );
    //string for setting mode
    private static final String strForward="Forward";
    private static final String strBackward="Backward";

    //string for forwarding
    private static final String strBatchPolicyDetails="batchpolicydetails";
    private static final String strGrpPolicyDetails="grppolicydetails";
    private static final String strCorpPolicyDetails="corppolicydetails";
    private static final String strNonCorpPolicyDetails="noncorppolicydetails";
    private static final String strBankFloatAccDetails="bankfloataccedit";
    private static final String strCardBatchPolicyDetails="cardbatchpolicydetails";
    private static final String strPreAuthDetails="preauthdetails";
    private static final String strClaimsdetail="claimsdetail";
    private static final String strBatchGrpList="batchgrouplist";
    private static final String strIndPolicyGrpList="indpolicygrplist";
    private static final String strCorpGroupList="corpgrouplist";
    private static final String strNonCorpGroupList="noncorpgrouplist";
    private static final String strCardBatchGrpList="cardbatchgrouplist";
    private static final String strBankFloatAccGrpList="bankfloatacclist";
    private static final String strPreAuthGrpList="preauthgrplist";
    private static final String strClaimgrplist="claimgrplist";
    private static final String strCallcenterCorporate="callcentercorporate";
    private static final String strCallcenterSearch="callcentersearch";
    private static final String strCallcenterDetailsCorporate="callcenterdetailscorporate";
    private static final String strCallcenterDetails="callcenterdetails";
    private static final String strInvoiceGroupList="invoicegrouplist";
    private static final String strInvoiceGroupClose="invoicegroupclose";
    private static final String strOnlineAccGrpList="onlineaccgrouplist";
    private static final String strOnlineAccSearch="onlinegroupclose";
    private static final String strAccountGrpList="accountgrplist";
    private static final String strAccountInfoList="accountinfolist";
    private static final String strGrpPolicyList="grppolicylist";
    private static final String strCorPolicyList="corpolicylist";
    private static final String strClaimCallpendGrpList="claimcallpendinglist";
    private static final String strClaimCallpendingList="claimcallpending";
    private static final String strIOBGrpList="iobgrplist";
    private static final String strIOBReports="iobreports";
    private static final String strAccentureReports="accenturereports";
    private static final String strGroupList="grouplist";
    private static final String strPreAuthClmRpt="preauthclmrpt";
   
    //string for comparision
    private static final String strEnrollment="Enrollment";
    private static final String strInwardEntry="Inward Entry";
    private static final String strSubEnrollment="Enrollment";
    private static final String strGrpPolicy="Ind. Policy as Group";
    private static final String strCorPolicy="Corporate Policy";
    private static final String strNonCorPolicy="Non-Corporate Policy";
    private static final String strCardPrinting="Card Printing";
    private static final String strBankFloatAcc="Float Account";
    private static final String strSupport="Support";
    private static final String strProcessing="Processing";
    private static final String strInvoice="Invoice";
    private static final String strPreAuthorization="Pre-Authorization";
    private static final String strClaims="Claims";
    private static final String strCustomerCare="Customer Care";
    private static final String strSearch="Search";
    private static final String strCallDetails="Call Details";
    private static final String strOnlineAcc="Online Information";
    private static final String strAccountInfo="Account Info";
    private static final String strClaimCallpending="Reports";
    private static final String strReports="Reports";
    //Added as per KOC 1216B Change Request 
    private static final String strMaintenance="Maintenance";
    private static final String strEnrollBufferSearch="enrollbuffersearch";
    private static final String strBuGroupList="bgrouplist";
    //Added as per KOC 1216B Change Request 
	//Added for IBM....11
    private static final String strIBMDeletionCutOffReports ="ibmdeletioncutoffreports";
    private static final String strIBMAdditioncutoffMaxRecReports = "ibmadditioncutoffMaxRecreports";
    private static final String strIBMReoptinDetailReports="ibmreoptindetailreports";
    private static final String strIBMReconDetailReports="ibmrecondetailreports";
    private static final String strIBMChildBornDetailReports="ibmchildborndetailreports";
    private static final String strIBMDailyDetailReports="ibmdailydetailreports";
    private static final String strIBMPreauthDetailReports="ibmpreauthdetailreports";
    private static final String strIBMGrpList="ibmgrplist";
    private static final String strIbmReports="IBMReports";//added by Praveen
	private static final String strCorporate="Corporate";//added by Praveen
	private static final String strIBMNewDeletionReports="ibmnewdeletionreports";
    //Ended..
    //Exception Message Identifier
	private static final String strWebConfig="webconfig";
	//bk
	
		private static final String strgrouppricing="grouppricing";
		private static final String strPricing="Software Insurance Pricing";
		private static final String strgroupprofile="groupprofile";
		//end bk
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
            setLinks(request);
            log.debug("Inside the doDefault of PolicyGroupAction");
            String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
                    getAttribute("UserSecurityProfile")).getBranchID());
            StringBuffer strCaption=new StringBuffer();
            DynaActionForm frmPolicyGroupList =null;
            DynaActionForm frmPolicyAccountInfo =null;
            DynaActionForm frmReportList =null;
            DynaActionForm frmMISReports = null;
	    
            //Added as per KOC 1216B ChangeRequest  
            DynaActionForm frmEnrollBufferSearch = null;
            //Added as per KOC 1216B ChangeRequest
            
            String strSubLink=TTKCommon.getActiveSubLink(request);
            if(TTKCommon.getActiveLink(request).equals("Online Information"))
            {
            	frmPolicyGroupList = (DynaActionForm)request.getSession().getAttribute("frmGeneral");
            	String strGroupID =(String) frmPolicyGroupList.get("sGroupId");
            	String strGroupName =(String) frmPolicyGroupList.get("sGroupName");
            	frmPolicyGroupList.set("sGroupId",strGroupID);
            	frmPolicyGroupList.set("sGroupName",strGroupName);    
            	frmPolicyGroupList.set("frmChanged","changed");
            }//end of if(TTKCommon.getActiveLink(request).equals("Online Information"))
            else if(TTKCommon.getActiveLink(request).equals("Account Info"))
            {
            	frmPolicyGroupList = (DynaActionForm)form;
            	frmPolicyAccountInfo = (DynaActionForm)request.getSession().getAttribute("frmPolicyAccountInfo");
            	String strGroupID =(String) frmPolicyAccountInfo.getString("sGroupId");
            	if(strGroupID != null)
            	{
            		frmPolicyGroupList.set("sGroupId",strGroupID);
            	}//end of if(strGroupID != null)
            }//end of else if(TTKCommon.getActiveLink(request).equals("Account Info"))
            else if(TTKCommon.getActiveLink(request).equals(strReports)&& strSubLink.equals(strSubEnrollment))
            {
            	frmPolicyGroupList = (DynaActionForm)form;
            	frmReportList = (DynaActionForm)request.getSession().getAttribute("frmReportList");
            	String strGroupID =(String) frmReportList.getString("groupId");
            	if(strGroupID != null)
            	{
            		frmPolicyGroupList.set("sGroupId",strGroupID);
            	}//end of if(strGroupID != null)
            }//end of else if(TTKCommon.getActiveLink(request).equals(strReports)&& strSubLink.equals(strSubEnrollment))
			//Added for IBM....11.1

            else if(TTKCommon.getActiveLink(request).equals(strIbmReports)&& strSubLink.equals(strCorporate))
			{
				frmPolicyGroupList = (DynaActionForm)form;
				frmReportList = (DynaActionForm)request.getSession().getAttribute("frmReportList");
				String strGroupID =(String) frmReportList.getString("groupId");
				if(strGroupID != null)
				{
					frmPolicyGroupList.set("sGroupId",strGroupID);
				}//end of if(strGroupID != null)
			}//end of else if(TTKCommon.getActiveLink(request).equals(strReports)&& strSubLink.equals(strSubEnrollment))


            //Ended...

            else if(TTKCommon.getActiveLink(request).equals("MIS Reports")&& strSubLink.equals("Enrollment"))
            {
            	frmPolicyGroupList = (DynaActionForm)form;
            	frmMISReports = (DynaActionForm)request.getSession().getAttribute("frmMISReports");
            	String strGroupID =(String) frmMISReports.getString("groupId");
            	if(strGroupID != null)
            	{
            		frmPolicyGroupList.set("sGroupId",strGroupID);
            	}//end of if(strGroupID != null)
            }//end of else if(TTKCommon.getActiveLink(request).equals("MIS Reports")&& strSubLink.equals("Enrollment"))
            else if(TTKCommon.getActiveLink(request).equals("MIS Reports")&& (strSubLink.equals("Pre-Authorization ")))
            {
            	frmPolicyGroupList = (DynaActionForm)form;
            	frmMISReports = (DynaActionForm)request.getSession().getAttribute("frmMISReports");
            	String strGroupID =(String) frmMISReports.getString("groupId");
            	if(strGroupID != null)
            	{
            		frmPolicyGroupList.set("sGroupId",strGroupID);
            	}//end of if(strGroupID != null)
            }//end of else if(TTKCommon.getActiveLink(request).equals("MIS Reports")&& (strSubLink.equals("Pre-Authorization ")))
            else if(TTKCommon.getActiveLink(request).equals(strReports)&& strSubLink.equals(strPreAuthorization))
            {
            	frmPolicyGroupList = (DynaActionForm)form;
            	frmReportList = (DynaActionForm)request.getSession().getAttribute("frmReportList");
            	String strGroupID =(String) frmReportList.getString("groupId");
            	if(strGroupID != null)
            	{
            		frmPolicyGroupList.set("sGroupId",strGroupID);
            	}//end of if(strGroupID != null)
            }//end of else if(TTKCommon.getActiveLink(request).equals(strReports)&& strSubLink.equals(strSubEnrollment))
            //Added as per KOC 1216B ChangeRequest
            else if(TTKCommon.getActiveLink(request).equals(strMaintenance)&& strSubLink.equals(strSubEnrollment))
            {
            	frmPolicyGroupList = (DynaActionForm)form;
            	frmEnrollBufferSearch = (DynaActionForm)request.getSession().getAttribute("frmEnrollBufferSearch");
            	String strGroupID =(String) frmEnrollBufferSearch.getString("sGroupId");
            	if(strGroupID != null)
            	{
            		frmPolicyGroupList.set("sGroupId",strGroupID);
            	}//end of if(strGroupID != null)
            }//end of else if(TTKCommon.getActiveLink(request).equals(strMaintenance)&& strSubLink.equals(strSubEnrollment))
          //Added as per KOC 1216B ChangeRequest
	  else
            {
            	frmPolicyGroupList = (DynaActionForm)form;
            	((DynaActionForm)form).initialize(mapping);//reset the form data
            }//end of else
            
            TableData tableDataGroupName=null;
            if((request.getSession()).getAttribute("tableDataGroupName") == null)
            {
                tableDataGroupName = new TableData();
            }// end of if((request.getSession()).getAttribute("tableDataGroupName") == null)
            else
            {
                tableDataGroupName = (TableData)(request.getSession()).getAttribute("tableDataGroupName");
            }// end of else
            tableDataGroupName= new TableData();
            if("Finance".equals(TTKCommon.getActiveLink(request)) && "Float Account".equals(TTKCommon.getActiveSubLink(request)))
            {
            	tableDataGroupName.createTableInfo("PolicyGroupTableFinance",null);
            	request.getSession().setAttribute("tableDataGroupNameFinance",tableDataGroupName);
            }
            else{
            	tableDataGroupName.createTableInfo("PolicyGroupTable",null);
            	request.getSession().setAttribute("tableDataGroupName",tableDataGroupName);
            }
            
        	strCaption.append(" - ["+TTKCommon.getWebBoardDesc(request)+"]");
        	frmPolicyGroupList.set("caption",strCaption.toString());
        	frmPolicyGroupList.set("officeInfo", strDefaultBranchID);
            return this.getForward(getForwardPath(request), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request,mapping,expTTK);
        }//end of catch(ETTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request,mapping,new TTKException(exp,"groupList"));
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
            setLinks(request);
            //log.debug("Inside the doSearch of PolicyGroupAction");
            TableData tableDataGroupName=null;
            String strSubLink=TTKCommon.getActiveSubLink(request);
            String strLink=TTKCommon.getActiveLink(request);
            if((request.getSession()).getAttribute("tableDataGroupName") == null)
            {
                tableDataGroupName = new TableData();
            }// end of if((request.getSession()).getAttribute("tableDataGroupName") == null)
            else
            {
                tableDataGroupName = (TableData)(request.getSession()).getAttribute("tableDataGroupName");
            }// end of else

            String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
            //if the page number or sort id is clicked
            if(!strPageID.equals("") || !strSortID.equals(""))
            {
                if(!strPageID.equals(""))
                {
                    tableDataGroupName.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.
                    		getParameter("pageId"))));
                    return (mapping.findForward(getForwardPath(request)));
                }// end of if(!strPageID.equals(""))
                else
                {
                    tableDataGroupName.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
                    tableDataGroupName.modifySearchData("sort");//modify the search data
                }// end of else
            }// end of if(!strPageID.equals("") || !strSortID.equals(""))
            else
            {
                //create the required grid table
                tableDataGroupName.createTableInfo("PolicyGroupTable",null);
                tableDataGroupName.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
                tableDataGroupName.modifySearchData("search");
            }// end of else

            PolicyManager policyManagerObject=this.getPolicyManagerObject();
            ReportManager reportManagerObject = this.getReportManager();
            ArrayList alGroupList= null;
            /*if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
            {
            	alGroupList = policyManagerObject.getGroupListCorporate(tableDataGroupName.getSearchData());     	
            }// end of if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
            else {
            	alGroupList = policyManagerObject.getGroupList(tableDataGroupName.getSearchData());
            }//end of else            
            */
            if(strLink.equals("MIS Reports")&& (strSubLink.equals("Enrollment")))
            {
            	alGroupList = reportManagerObject.getGroupList(tableDataGroupName.getSearchData());     	
            }// end of if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
            else if(strLink.equals("MIS Reports")&& (strSubLink.equals("Pre-Authorization ")))
            {
            	alGroupList = reportManagerObject.getGroupList(tableDataGroupName.getSearchData());     	
            }//end of else if(strLink.equals("MIS Reports")&& (strSubLink.equals("Pre-Authorization "))) 
            else {
            	alGroupList = policyManagerObject.getGroupList(tableDataGroupName.getSearchData());
            }//end of else
            //alGroupList = policyManagerObject.getGroupList(tableDataGroupName.getSearchData());
            tableDataGroupName.setData(alGroupList,"search");
            //set the table data object to session
            request.getSession().setAttribute("tableDataGroupName",tableDataGroupName);
            return this.getForward(getForwardPath(request), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request,mapping,expTTK);
        }//end of catch(ETTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request,mapping,new TTKException(exp,"groupList"));
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
    public ActionForward doSearchFinance(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
            setLinks(request);
            //log.debug("Inside the doSearch of PolicyGroupAction");
            TableData financeTableDataGroupName=null;
            String strSubLink=TTKCommon.getActiveSubLink(request);
            String strLink=TTKCommon.getActiveLink(request);
            if((request.getSession()).getAttribute("financeTableDataGroupName") == null)
            {
            	financeTableDataGroupName = new TableData();
            }// end of if((request.getSession()).getAttribute("tableDataGroupName") == null)
            else
            {
            	financeTableDataGroupName = (TableData)(request.getSession()).getAttribute("financeTableDataGroupName");
            }// end of else

            String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
            //if the page number or sort id is clicked
            if(!strPageID.equals("") || !strSortID.equals(""))
            {
                if(!strPageID.equals(""))
                {
                	financeTableDataGroupName.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.
                    		getParameter("pageId"))));
                    return (mapping.findForward(getForwardPath(request)));
                }// end of if(!strPageID.equals(""))
                else
                {
                	financeTableDataGroupName.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
                	financeTableDataGroupName.modifySearchData("sort");//modify the search data
                }// end of else
            }// end of if(!strPageID.equals("") || !strSortID.equals(""))
            else
            {
                //create the required grid table
            	financeTableDataGroupName.createTableInfo("PolicyGroupTable",null);
            	financeTableDataGroupName.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
            	financeTableDataGroupName.modifySearchData("search");
            }// end of else

            PolicyManager policyManagerObject=this.getPolicyManagerObject();
            ReportManager reportManagerObject = this.getReportManager();
            ArrayList alGroupList= null;
            alGroupList = policyManagerObject.getGroupListFinance(financeTableDataGroupName.getSearchData());
            //alGroupList = policyManagerObject.getGroupList(tableDataGroupName.getSearchData());
            financeTableDataGroupName.setData(alGroupList,"search");
            //set the table data object to session
            request.getSession().setAttribute("financeTableDataGroupName",financeTableDataGroupName);
            return this.getForward(getForwardPath(request), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request,mapping,expTTK);
        }//end of catch(ETTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request,mapping,new TTKException(exp,"groupList"));
        }//end of catch(Exception exp)
    }//end of doSearchFinance(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
        try
        {
            setLinks(request);
            log.debug("Inside the doBackward of PolicyGroupAction");
            TableData tableDataGroupName=null;//TTKCommon.getTableData(request);
            if("Finance".equals(TTKCommon.getActiveLink(request)) && "Float Account".equals(TTKCommon.getActiveSubLink(request)))
            {
	            if((request.getSession()).getAttribute("tableDataGroupName") == null)
	            {
	                tableDataGroupName = new TableData();
	            }// end of if((request.getSession()).getAttribute("tableDataGroupName") == null)
	            else
	            {
	                tableDataGroupName = (TableData)(request.getSession()).getAttribute("tableDataGroupName");
	            }// end of else
            }else{
            	
            	if((request.getSession()).getAttribute("tableDataGroupName") == null)
	            {
	                tableDataGroupName = new TableData();
	            }// end of if((request.getSession()).getAttribute("tableDataGroupName") == null)
	            else
	            {
	                tableDataGroupName = (TableData)(request.getSession()).getAttribute("tableDataGroupName");
	            }// end of else
            }
            PolicyManager policyManagerObject=this.getPolicyManagerObject();
            tableDataGroupName.modifySearchData(strBackward);//modify the search data
            ArrayList alGroupList = policyManagerObject.getGroupList(tableDataGroupName.getSearchData());
            tableDataGroupName.setData(alGroupList, strBackward);//set the table data
            //set the table data object to session
            request.getSession().setAttribute("tableDataGroupName",tableDataGroupName);
            return this.getForward(getForwardPath(request), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request,mapping,expTTK);
        }//end of catch(ETTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request,mapping,new TTKException(exp,"groupList"));
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
    public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
            setLinks(request);
            log.debug("Inside the doForward of PolicyGroupAction");
            TableData tableDataGroupName=null;//TTKCommon.getTableData(request);
            if("Finance".equals(TTKCommon.getActiveLink(request)) && "Float Account".equals(TTKCommon.getActiveSubLink(request)))
            {
	            if((request.getSession()).getAttribute("tableDataGroupName") == null)
	            {
	                tableDataGroupName = new TableData();
	            }// end of if((request.getSession()).getAttribute("tableDataGroupName") == null)
	            else
	            {
	                tableDataGroupName = (TableData)(request.getSession()).getAttribute("tableDataGroupName");
	            }// end of else
            }else{
            	if((request.getSession()).getAttribute("tableDataGroupName") == null)
	            {
	                tableDataGroupName = new TableData();
	            }// end of if((request.getSession()).getAttribute("tableDataGroupName") == null)
	            else
	            {
	                tableDataGroupName = (TableData)(request.getSession()).getAttribute("tableDataGroupName");
	            }// end of else
            }
            PolicyManager policyManagerObject=this.getPolicyManagerObject();
            tableDataGroupName.modifySearchData(strForward);//modify the search data
            ArrayList alGroupList = policyManagerObject.getGroupList(tableDataGroupName.getSearchData());
            tableDataGroupName.setData(alGroupList, strForward);//set the table data
            //set the table data object to session
            request.getSession().setAttribute("tableDataGroupName",tableDataGroupName);
            return this.getForward(getForwardPath(request), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request,mapping,expTTK);
        }//end of catch(ETTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request,mapping,new TTKException(exp,"groupList"));
        }//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


    /**
     * This method is will update the selected Group's compnay's info in the required form and
     * moves it the next screen.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSelectGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	
            setLinks(request);
            log.debug("Inside the doSelectGroup of PolicyGroupAction");
            String strSubLink=TTKCommon.getActiveSubLink(request);
            String strLink=TTKCommon.getActiveLink(request);
            String strActiveTab=TTKCommon.getActiveTab(request);
            DynaActionForm frmGeneral = null;
            DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
            PolicyGroupVO policyGroupVO=null;
            if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
            	
            	//bk
            	if(strLink.equals(strPricing))
                {
                    frmGeneral = (DynaActionForm) request.getSession().getAttribute("frmSwPricing");
                    frmGeneral.set("frmChanged","changed");
                }
            	//end bk
            	
                if(strLink.equals(strInwardEntry)&& strSubLink.equals(strSubEnrollment))
                {
                    frmGeneral = (DynaActionForm) request.getSession().getAttribute("frmPolicyDetail");
                    frmGeneral.set("frmChanged","changed");
                }// end of if(strLink.equals(strInwardEntry)&& strSubLink.equals(strSubEnrollment))
                if(((strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))
                        ||(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
                        ||(strLink.equals(strEnrollment)&& strSubLink.equals(strNonCorPolicy))) && strActiveTab.equals("Policy Details"))
                {
                    frmGeneral= (DynaActionForm) request.getSession().getAttribute("frmPolicyDetails");
                    frmGeneral.set("frmChanged","changed");
                }//end of if((strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))||(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))||(strLink.equals(strEnrollment)&& strSubLink.equals(strNonCorPolicy)))
                if(((strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))
                        ||(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))) && strActiveTab.equals("Search"))
                {
                    frmGeneral= (DynaActionForm) request.getSession().getAttribute("frmPolicyList");
                    frmGeneral.set("frmChanged","changed");
                }//end of if((strLink.equals(strEnrollment)
                if(strSubLink.equals(strBankFloatAcc))
                {
                    frmGeneral=(DynaActionForm)request.getSession().getAttribute("frmFloatAccDetails");
                    frmGeneral.set("frmChanged","changed");
                }//end of if(strSubLink.equals(strBankFloatAcc))
                if(strLink.equals(strSupport)&& strSubLink.equals(strCardPrinting))
                {
                    frmGeneral = (DynaActionForm) request.getSession().getAttribute("frmCreateCardBatch");
                    //frmGeneral.set("frmChanged","changed");
                }// end of if(strLink.equals(strSupport)&& strSubLink.equals(strCardPrinting))
                if(strSubLink.equals(strProcessing))
                {

                    frmGeneral=(DynaActionForm)frmPreAuthGeneral.get("claimantDetailsVO");
                    frmPreAuthGeneral.set("frmChanged","changed");
                }//end of if(strSubLink.equals(strProcessing))
                if(strSubLink.equals(strInvoice))
                {
                    frmGeneral=(DynaActionForm) request.getSession().getAttribute("frmInvoice");
                    if(request.getSession().getAttribute("tableData") != null)
                    {
                        TableData tableData =(TableData)request.getSession().getAttribute("tableData");
                        tableData.modifySearchData("search");
                        tableData.setData(null, "search");  //clear the searched data when insurance company or group is cleared
                        request.getSession().setAttribute("tableData",tableData);
                    }//end of if(request.getSession().getAttribute("tableData") != null)
                }//end of if(strSubLink.equals(strInvoice))
               
                if("Finance".equals(strLink) && "Float Account".equals(strSubLink))
                {
                	 policyGroupVO = (PolicyGroupVO)((TableData)request.getSession().getAttribute("financeTableDataGroupName")).
                     getData().get(Integer.parseInt(request.getParameter("rownum")));
                	
                }else
                {
                	policyGroupVO = (PolicyGroupVO)((TableData)request.getSession().getAttribute("tableDataGroupName")).
	                getData().get(Integer.parseInt(request.getParameter("rownum")));
                }
                
                if(strLink.equals(strOnlineAcc))
                {
                	DynaActionForm frmOnlineAccountInfo=(DynaActionForm)request.getSession().
                	getAttribute("frmOnlineAccountInfo");
                	if(frmOnlineAccountInfo != null)
                	{
                		frmOnlineAccountInfo.set("sGroupName",policyGroupVO.getGroupName());
                		frmOnlineAccountInfo.set("sGroupId",policyGroupVO.getGroupID());
                		//frmOnlineAccountInfo.set("seqID",policyGroupVO.getGroupRegnSeqID().toString());
                		frmOnlineAccountInfo.set("frmChanged","changed");
                	}//end of if(frmOnlineAccountInfo != null)                	
                }//end of if(strLink.equals(strOnlineAcc))
                else if(strLink.equals(strAccountInfo))
                {
                	DynaActionForm frmPolicyAccountInfo=(DynaActionForm)request.getSession().
                	getAttribute("frmPolicyAccountInfo");
                	if(frmPolicyAccountInfo.getString("sGroupId") != null)
                	{
                		frmPolicyAccountInfo.set("sGroupId",policyGroupVO.getGroupID());
                		frmPolicyAccountInfo.set("sGroupName",policyGroupVO.getGroupName());
                		frmPolicyAccountInfo.set("sGroupRegnSeqID",policyGroupVO.getGroupRegnSeqID().toString());
                		frmPolicyAccountInfo.set("frmChanged","changed");
                	}//end of if(frmPolicyAccountInfo.getString("sGroupId") != null)                	
                }//end of if(strLink.equals(strAccountInfo))
               //Added as per KOC 1216B Change request
                else if(strLink.equals(strMaintenance)&&strSubLink.equals(strSubEnrollment) )
                {
                	DynaActionForm frmEnrollBufferSearch=(DynaActionForm)request.getSession().
                	getAttribute("frmEnrollBufferSearch");
                	if(frmEnrollBufferSearch.getString("sGroupId") != null)
                	{
                		frmEnrollBufferSearch.set("sGroupId",policyGroupVO.getGroupID());
                		frmEnrollBufferSearch.set("sGroupName",policyGroupVO.getGroupName());
                		frmEnrollBufferSearch.set("sGroupRegnSeqID",policyGroupVO.getGroupRegnSeqID().toString());
                		frmEnrollBufferSearch.set("frmChanged","changed");
                	}//end of if(frmPolicyAccountInfo.getString("sGroupId") != null)                	
                }//end of if(strLink.equals(strAccountInfo))
                //Added as per KOC 1216B change request
                
                else if(strLink.equals(strReports)&& strSubLink.equals(strSubEnrollment))
                {
                	DynaActionForm frmReportList=(DynaActionForm)request.getSession().
                	getAttribute("frmReportList");
                	if(frmReportList.getString("groupId") != null)
                	{
                		frmReportList.set("groupId",policyGroupVO.getGroupID());                		
                	}//end of if(frmPolicyAccountInfo.getString("sGroupId") != null)
                	
                }//end of else if(strLink.equals(strReports)&& strSubLink.equals(strSubEnrollment))
				//Added for IBM....11.2
                else if(strLink.equals(strIbmReports)&& strSubLink.equals(strCorporate))
				{
					DynaActionForm frmReportList=(DynaActionForm)request.getSession().
					getAttribute("frmReportList");
					if(frmReportList.getString("groupId") != null)
					{
						frmReportList.set("groupId",policyGroupVO.getGroupID());
					}//end of if(frmPolicyAccountInfo.getString("sGroupId") != null)
				}//end of else if(strLink.equals(strReports)&& strSubLink.equals(strSubEnrollment))

			   //Ended.
              //bk
                else if(strPricing.equalsIgnoreCase(strLink))
                {
                  
                    frmGeneral.set("clientCode",policyGroupVO.getGroupID());
                    frmGeneral.set("clientName",policyGroupVO.getGroupName());
                  
                }
             //end bk

                else if(strLink.equals("MIS Reports")&& (strSubLink.equals(strSubEnrollment)))
                {
                	DynaActionForm frmMISReports=(DynaActionForm)request.getSession().
                	getAttribute("frmMISReports");
                	if(frmMISReports.getString("groupId") != null)
                	{
                		frmMISReports.set("groupId",policyGroupVO.getGroupID());                		
                	}//end of if(frmPolicyAccountInfo.getString("sGroupId") != null)
                }//end of else if(strLink.equals("MIS Reports")&& strSubLink.equals(strSubEnrollment))
                else if(strLink.equals("MIS Reports")&& (strSubLink.equals("Pre-Authorization ")))
                {
                	DynaActionForm frmMISReports=(DynaActionForm)request.getSession().
                	getAttribute("frmMISReports");
                	if(frmMISReports.getString("groupId") != null)
                	{
                		frmMISReports.set("groupId",policyGroupVO.getGroupID());                		
                	}//end of if(frmPolicyAccountInfo.getString("sGroupId") != null)
                }//else if(strLink.equals("MIS Reports")&& (strSubLink.equals(strPreAuthorization)))
                else if(strLink.equals(strEnrollment) && strActiveTab.equals("Search"))
                {
                	DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().
                	getAttribute("frmPolicyList");
                	if(frmPolicyList.getString("sGroupId") != null)
                	{
                		frmPolicyList.set("sGroupId",policyGroupVO.getGroupID());
                		if(!policyGroupVO.getPolicyNo().equals("")){
                			frmPolicyList.set("sPolicyNumber",policyGroupVO.getPolicyNo());
                		}//end of if(policyGroupVO.getPolicyNo()!="")           		
                		frmPolicyList.set("sGroupName",policyGroupVO.getGroupName());
//                		frmPolicyList.set("sGroupRegnSeqID",policyGroupVO.getGroupRegnSeqID().toString());
                		frmPolicyList.set("frmChanged","changed");
                	}//end of if(frmPolicyAccountInfo.getString("sGroupId") != null)                	
                }//end of if(strLink.equals(strAccountInfo))
                else if(strLink.equals(strCustomerCare))
                {
                    if(strActiveTab.equals(strSearch))
                    {
                        DynaActionForm frmCallCenterList=(DynaActionForm)request.getSession().
                        								  getAttribute("frmCallCenterList");
                        if(frmCallCenterList != null)
                        {
                            frmCallCenterList.set("id",policyGroupVO.getGroupName());
                            frmCallCenterList.set("name",policyGroupVO.getGroupID());
                            frmCallCenterList.set("seqID",policyGroupVO.getGroupRegnSeqID().toString());
                        }//end of if(frmCallCenterList != null)
                    }//end of if(strActiveTab.equals(strSearch))
                    else if(strActiveTab.equals(strCallDetails))
                    {
                        DynaActionForm frmCallCenterDetails=(DynaActionForm)request.getSession().
                        									 getAttribute("frmCallCenterDetails");
                        if(frmCallCenterDetails != null)
                        {
                            frmCallCenterDetails.set("groupName",policyGroupVO.getGroupName());
                            frmCallCenterDetails.set("groupID",policyGroupVO.getGroupID());
                            frmCallCenterDetails.set("groupRegnSeqID",String.valueOf(policyGroupVO.
                            		getGroupRegnSeqID()));
                            frmCallCenterDetails.set("frmChanged","changed");
                        }//end of if(frmCallCenterDetails != null)
                    }//end of else if(strActiveTab.equals(strCallDetails))
                }//end of if(strActiveLink.equals(strCustomerCare))
                else if(strLink.equals(strClaimCallpending))
                {
                	DynaActionForm frmReportList=(DynaActionForm)request.getSession().
                	getAttribute("frmReportList");
                	if(frmReportList.getString("groupId") != null)
                	{
                		frmReportList.set("groupId",policyGroupVO.getGroupID());
                		frmReportList.set("groupName",policyGroupVO.getGroupName());
//                		frmReportList.set("frmChanged","changed");
                	}
                }//end of else if(strLink.equals(strClaimCallpending))
                else if(strLink.equals(strReports)&& strSubLink.equals(strPreAuthorization))
                {
                	DynaActionForm frmReportList=(DynaActionForm)request.getSession().
                	getAttribute("frmReportList");
                	if(frmReportList.getString("groupId") != null)
                	{
                		frmReportList.set("groupId",policyGroupVO.getGroupID());                		
                	}//end of if(frmPolicyAccountInfo.getString("sGroupId") != null)
                }//end of else if(strLink.equals(strReports)&& strSubLink.equals(strPreAuthorization))
                else if("Finance".equals(strLink) && "Float Account".equals(strSubLink))
                {
                    frmGeneral.set("policyNo",policyGroupVO.getPolicyNo());
                    frmGeneral.set("groupID",policyGroupVO.getGroupID());
                    frmGeneral.set("groupName",policyGroupVO.getGroupName());
                    frmGeneral.set("groupRegnSeqID",policyGroupVO.getGroupRegnSeqID().toString());
                    frmGeneral.set("policyCategory",policyGroupVO.getPolicyCategory().toString());
                }//end of else
                else
                {
                    frmGeneral.set("groupID",policyGroupVO.getGroupID());
                    frmGeneral.set("groupName",policyGroupVO.getGroupName());
                    frmGeneral.set("groupRegnSeqID",policyGroupVO.getGroupRegnSeqID().toString());
                }//end of else
            }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            request.getSession().removeAttribute("frmPolicyGroupList");
            request.getSession().removeAttribute("tableDataGroupName");

            if(strSubLink.equals(strProcessing))
            {
                Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");
                toolBar.getConflictIcon().setVisible(toolBar.getConflictIcon().isVisible() &&
                		frmPreAuthGeneral.get("discPresentYN").equals("Y"));
            }
            
          
            
            return this.getForward(getSelectPath(request,policyGroupVO),mapping,request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request,mapping,expTTK);
        }//end of catch(ETTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request,mapping,new TTKException(exp,"groupList"));
        }//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


    
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
    	log.debug("Inside the doClose of PolicyGroupAction");
        return doSelectGroup(mapping,form,request,response);
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method builds all the search parameters to ArrayList and places them in session
     * @param searchGroupForm DynaActionForm
     * @param request HttpServletRequest
     * @return alSearchParams ArrayList
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm searchGroupForm,HttpServletRequest request)
    	throws TTKException
    {
    	DynaActionForm frmPolicyAccountInfo=(DynaActionForm)request.getSession().getAttribute("frmPolicyAccountInfo");
    	String strSubLink=TTKCommon.getActiveSubLink(request);
        String strLink=TTKCommon.getActiveLink(request);
        
        //build the column names along with their values to be searched
      
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        
       // if(strSubLink.equals(strBankFloatAcc))
        if("Finance".equals(TTKCommon.getActiveLink(request)) && "Float Account".equals(TTKCommon.getActiveSubLink(request)))
        {
        	alSearchParams.add(TTKCommon.replaceSingleQots((String)searchGroupForm.get("sPolicy")));
        }
        alSearchParams.add(TTKCommon.replaceSingleQots((String)searchGroupForm.get("sGroupId")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)searchGroupForm.get("sGroupName")));
        alSearchParams.add((String)searchGroupForm.get("officeInfo"));
        
        if(strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))
        {
            alSearchParams.add("ING");
        }//end of if(strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))
        else if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
        {
        	alSearchParams.add("COR");        	
        }// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
        else if(strLink.equals(strEnrollment)&& strSubLink.equals(strNonCorPolicy))
        {
            alSearchParams.add("NCR");
        }//end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strNonCorPolicy))
        else if(strLink.equals(strInwardEntry)&& strSubLink.equals(strSubEnrollment))
        {   DynaActionForm frmPolicyDetail=(DynaActionForm)request.getSession().getAttribute("frmPolicyDetail");
        	alSearchParams.add((String)frmPolicyDetail.get("policyTypeID"));
        }//end of else if(strLink.equals(strInwardEntry)&& strSubLink.equals(strSubEnrollment))
        else if(strSubLink.equals(strBankFloatAcc))
        {
            alSearchParams.add("COR");
        }// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
        else if(strLink.equals(strSupport)&& strSubLink.equals(strCardPrinting))
        {   DynaActionForm frmCreateCardBatch=(DynaActionForm)request.getSession().
        	getAttribute("frmCreateCardBatch");
        	alSearchParams.add((String)frmCreateCardBatch.get("enrolTypeID"));
        //alSearchParams.add("ING");
        }//end of if(strLink.equals(strSupport)&& strSubLink.equals(strCardPrinting))
        else if(strSubLink.equals(strProcessing))
        {
        	DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
        	DynaActionForm frmClaimantDetails=(DynaActionForm)frmPreAuthGeneral.get("claimantDetailsVO");
            alSearchParams.add((String)frmClaimantDetails.get("policyTypeID"));
        }//end of else if(strSubLink.equals(strProcessing))
        else if(strLink.equals(strCustomerCare))
        {
            alSearchParams.add("COR");
        }//end of else if(strLink.equals(strCustomerCare))
        else if(strLink.equals(strOnlineAcc))
        {
            alSearchParams.add("COR");
        }//end of else if(strLink.equals(strOnlineAcc))
        else if(strLink.equals(strAccountInfo))
        {
        	alSearchParams.add(frmPolicyAccountInfo.getString("sPolicyType"));
        }// end of else if(strLink.equals(strAccountInfo))
        else if(strLink.equals(strReports)&& strSubLink.equals(strSubEnrollment))
        {
        	DynaActionForm frmReportList=(DynaActionForm)request.getSession().
        	getAttribute("frmReportList");
        	if(frmReportList!=null){
        		String strReportID = frmReportList.getString("reportID");
            	if("IOBBatRpt".equals(strReportID)){
            		alSearchParams.add("ING");
            	}//end of if("IOBBatRpt".equals(strReportID)) 
            	else if("AccentureRpt".equals(strReportID)){
            		alSearchParams.add("COR");            		
            	}//end of else if("AccentureRpt".equals(strReportID))
        	}//end of if(frmReportList!=null)
        }// end of else if(strLink.equals(strReports)&& strSubLink.equals(strSubEnrollment))
        else if(strLink.equals("MIS Reports")&& (strSubLink.equals(strSubEnrollment)))
        {
        	DynaActionForm frmMISReports=(DynaActionForm)request.getSession().
        	getAttribute("frmMISReports");
        }// end of else if(strLink.equals("MIS Reports")&& strSubLink.equals(strSubEnrollment))
        else if(strLink.equals("MIS Reports")&& (strSubLink.equals("Pre-Authorization ")))
        {
        	DynaActionForm frmMISReports=(DynaActionForm)request.getSession().
        	getAttribute("frmMISReports");
        }//end of else if(strLink.equals("MIS Reports")&& (strSubLink.equals("Pre-Authorization ")))
        else if(strSubLink.equals("Invoice"))
        {
            DynaActionForm frmInvoice=(DynaActionForm)request.getSession().getAttribute("frmInvoice");
            alSearchParams.add((String)frmInvoice.get("senrollmentType"));
        }//end of else if(strSubLink.equals("Invoice"))
        else if(strLink.equals(strReports)&& strSubLink.equals(strPreAuthorization))
        {
        	DynaActionForm frmReportList=(DynaActionForm)request.getSession().
        	getAttribute("frmReportList");
        	if(frmReportList!=null){
        		String strReportID = frmReportList.getString("reportID");
            		alSearchParams.add("COR");            		
        	}//end of if(frmReportList!=null)
        }//end of else if(strLink.equals(strReports)&& strSubLink.equals(strPreAuthorization))
        else
        {
        	alSearchParams.add("");
        }//end of else
        /*if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
        {
        	alSearchParams.add(searchGroupForm.getString("sPolicyNo"));        	
        }// end of if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))        
*/        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm searchGroupForm,HttpServletRequest request)

    /**
     * Returns the PolicyManager session object for invoking DAO methods on it.
     * @return PolicyManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private PolicyManager getPolicyManagerObject() throws TTKException
    {
        PolicyManager policyManager = null;
        try
        {
            if(policyManager == null)
            {
                InitialContext ctx = new InitialContext();
                policyManager = (PolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/PolicyManagerBean!com.ttk.business.enrollment.PolicyManager");
            }//end of if(policyManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "groupList");
        }//end of catch
        return policyManager;
    }//end getPolicyManagerObject

    /**
     * This method returns the forward path for next view based on the Flow
     *
     * @param request HttpServletRequest currnet request
     * @return strForwardPath String forward path for the next view
     */
    private String getForwardPath(HttpServletRequest request) throws TTKException
    {
        String strForwardPath=null;

        String strActiveLink=TTKCommon.getActiveLink(request);
        String strActiveSubLink=TTKCommon.getActiveSubLink(request);
        String strActiveTab=TTKCommon.getActiveTab(request);
        if(strActiveLink.equals(strInwardEntry)&& strActiveSubLink.equals(strSubEnrollment))
        {
            strForwardPath=strBatchGrpList;
        }// end of if(strLink.equals(strInwardEntry)&& strSubLink.equals(strSubEnrollment))
        else if(strActiveLink.equals(strEnrollment)&& strActiveSubLink.equals(strGrpPolicy))
        {
            strForwardPath=strIndPolicyGrpList;
        }// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))
        else if(strActiveLink.equals(strEnrollment)&& strActiveSubLink.equals(strCorPolicy))
        {
            strForwardPath=strCorpGroupList;
        }// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
        else if(strActiveLink.equals(strEnrollment)&& strActiveSubLink.equals(strNonCorPolicy))
        {
            strForwardPath=strNonCorpGroupList;
        }// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strNonCorPolicy))
        else if(strActiveSubLink.equals(strBankFloatAcc))
        {
            strForwardPath=strBankFloatAccGrpList;
        }// end of else if(strSubLink.equals(strBankFloatAcc))
        else if(strActiveLink.equals(strOnlineAcc))
        {
            strForwardPath=strOnlineAccGrpList;
        }// end of else if(strSubLink.equals(strOnlineAcc))
        else if(strActiveLink.equals(strSupport)&& strActiveSubLink.equals(strCardPrinting))
        {
            strForwardPath=strCardBatchGrpList;
        }// end of else if(strLink.equals(strSupport)&& strSubLink.equals(strCardPrinting))
        else if(strActiveSubLink.equals(strInvoice))
        {
            strForwardPath=strInvoiceGroupList;
        }//end of else if(strActiveSubLink.equals(strInvoice))
        if(strActiveLink.equals(strPreAuthorization))
        {
            strForwardPath=strPreAuthGrpList;
        }//end of if(strActiveLink.equals(strPreAuthorization))
        else if(strActiveLink.equals(strClaims))
        {
            strForwardPath=strClaimgrplist;
        }//end of else if(strActiveLink.equals(strClaims))

        if(strActiveLink.equals(strCustomerCare))
        {
            if(strActiveTab.equals(strSearch))
            {
                strForwardPath=strCallcenterCorporate;
            }//end of if(strActiveTab.equals(strSearch))
            else if(strActiveTab.equals(strCallDetails))
            {
                strForwardPath=strCallcenterDetailsCorporate;
            }//end of else if(strActiveTab.equals(strCallDetails))
        }//end of if(strLink(strCustomerCare))
        if(strActiveLink.equals(strAccountInfo))
        {
        	strForwardPath=strAccountGrpList;
        }//end of if(strActiveLink.equals(strAccountInfo))
        if(strActiveLink.equals(strClaimCallpending))
        {
        	strForwardPath=strClaimCallpendGrpList;
        }//end of if(strActiveLink.equals(strClaimCallpending))
        if(strActiveLink.equals(strReports)&& strActiveSubLink.equals(strSubEnrollment))
        {
        	strForwardPath=strIOBGrpList;
        }//end of if(strActiveLink.equals(strReports)&& strActiveSubLink.equals(strSubEnrollment))
		 //Added for IBM...11.3
        if(strActiveLink.equals(strIbmReports)&& strActiveSubLink.equals(strCorporate))
		{
			strForwardPath=strIBMGrpList;
		}//end of if(strActiveLink.equals(strReports)&& strActiveSubLink.equals(strSubEnrollment))

       //Ended..
        if(strActiveLink.equals("MIS Reports")&& strActiveSubLink.equals(strSubEnrollment))
        {
        	strForwardPath="aecenture";
        }//end of if(strActiveLink.equals("MIS Reports")&& strActiveSubLink.equals(strSubEnrollment))
        if(strActiveLink.equals("MIS Reports")&& (strActiveSubLink.equals("Pre-Authorization ")))
        {
        	strForwardPath="grplist";
        }//end of if(strActiveLink.equals("MIS Reports")&& strActiveSubLink.equals(strPreAuthorization))
        if(strActiveLink.equals(strReports)&& strActiveSubLink.equals(strPreAuthorization))
        {
        	strForwardPath=strGroupList;
        }//end of if(strActiveLink.equals(strReports)&& strActiveSubLink.equals(strPreAuthorization))
	  //Added as per KOC 1216B Change request
        if(strActiveLink.equals(strMaintenance)&& strActiveSubLink.equals(strSubEnrollment))
        {
        	strForwardPath=strBuGroupList;
        }//end of if(strActiveLink.equals(strMaintenance)&& strActiveSubLink.equals(strSubEnrollment))
        
      //bk
        if(strActiveLink.equalsIgnoreCase(strPricing))
        {
        	strForwardPath=strgrouppricing;
        }//end of if(strActiveLink.equals(strMaintenance)&& strActiveSubLink.equals(strSubEnrollment))
        
        //Added as per KOC 1216B Change request
        
        return strForwardPath;
    }//end of getForwardPath(HttpServletRequest request)


    /**
     * This method returns the forward path for next view based on the Flow
     *
     * @param request HttpServletRequest currnet request
     * @return strForwardPath String forward path for the next view
     */
    private String getSelectPath(HttpServletRequest request,PolicyGroupVO policyGroupVO) throws TTKException
    {
        String strSelectPath=null;

        String strLink=TTKCommon.getActiveLink(request);
        String strSubLink=TTKCommon.getActiveSubLink(request);
        String strActiveTab=TTKCommon.getActiveTab(request);
        
      //bk
        if(strLink.equals(strPricing))
        {
            strSelectPath=strgroupprofile;
        }
        //end bk
        if(strLink.equals(strInwardEntry)&& strSubLink.equals(strSubEnrollment))
        {
            strSelectPath=strBatchPolicyDetails;
        }// end of if(strLink.equals(strInwardEntry)&& strSubLink.equals(strSubEnrollment))
  //Added as per KOC 1216B CHANGE REQUEST
        if(strLink.equals(strMaintenance)&& strSubLink.equals(strSubEnrollment))
        {
            strSelectPath=strEnrollBufferSearch;
        }// end of if(strLink.equals(strInwardEntry)&& strSubLink.equals(strSubEnrollment))
        //Added as per KOC 1216B changerequest
        else if(strLink.equals(strEnrollment))
        {
        	if(strSubLink.equals(strGrpPolicy) && strActiveTab.equals("Search"))
        	{
        		strSelectPath=strGrpPolicyList;
        	}//end of if(strSubLink.equals(strGrpPolicy) && strActiveTab.equals("Search"))
        	else if(strSubLink.equals(strCorPolicy) && strActiveTab.equals("Search"))
        	{
        		strSelectPath=strCorPolicyList;
        	}//end of else if(strSubLink.equals(strGrpPolicy) && strActiveTab.equals("Search"))
        	else
        	{
        		if(strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))
        		{
        			strSelectPath=strGrpPolicyDetails;
        		}// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))
        		else if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
        		{
        			strSelectPath=strCorpPolicyDetails;
        		}// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
        		else if(strLink.equals(strEnrollment)&& strSubLink.equals(strNonCorPolicy))
        		{
        			strSelectPath=strNonCorpPolicyDetails;
        		}// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strNonCorPolicy))
        	}//end of else
        }//end of if(strLink.equals(strEnrollment))
        else if(strSubLink.equals(strBankFloatAcc))
        {
            strSelectPath=strBankFloatAccDetails;
        }// end of else if(strSubLink.equals(strBankFloatAcc))
        else if(strLink.equals(strOnlineAcc))
        {
        	strSelectPath=strOnlineAccSearch;
        }// end of else if(strSubLink.equals(strOnlineAcc))
        else if(strLink.equals(strSupport)&& strSubLink.equals(strCardPrinting))
        {
            strSelectPath=strCardBatchPolicyDetails;
        }// end ofelse if(strLink.equals(strSupport)&& strSubLink.equals(strCardPrinting))

        else if(strSubLink.equals(strInvoice))
        {
            strSelectPath=strInvoiceGroupClose;
        }//end of else if(strSubLink.equals(strInvoice))

        if(strLink.equals(strPreAuthorization))
        {
            strSelectPath=strPreAuthDetails;
        }//end of if(strLink.equals(strPreAuthorization))
        else if(strLink.equals(strClaims))
        {
            strSelectPath=strClaimsdetail;
        }//end of else if(strLink.equals(strClaims))

        if(strLink.equals(strCustomerCare))
        {
            if(strActiveTab.equals(strSearch))
            {
                strSelectPath=strCallcenterSearch;
            }//end of if(strActiveTab.equals(strSearch))
            else if(strActiveTab.equals(strCallDetails))
            {
                strSelectPath=strCallcenterDetails;
            }//end of else if(strActiveTab.equals(strCallDetails))
        }//end of if(strLink(strCustomerCare))
        if(strLink.equals(strAccountInfo))
        {
        	strSelectPath=strAccountInfoList;
        }//end of if(strActiveLink.equals(strAccountInfo))
        if(strLink.equals(strClaimCallpending))
        {
        	strSelectPath=strClaimCallpendingList;
        }//end of if(strActiveLink.equals(strClaimCallpending))
        if(strLink.equals(strReports)&& strSubLink.equals(strSubEnrollment))
        {
        	DynaActionForm frmReportList=(DynaActionForm)request.getSession().
        	getAttribute("frmReportList");
        	String strReportID = frmReportList.getString("reportID");
        	//log.info("strReportID is " + strReportID);
        	if("IOBBatRpt".equals(strReportID)){
        		strSelectPath=strIOBReports;
        	}//end of if("IOBBatRpt".equals(strReportID)) 
        	else if("AccentureRpt".equals(strReportID)){
        		strSelectPath=strAccentureReports;
        		this.loadReportPolicy(frmReportList,request,policyGroupVO);
        	}//end of else if("AccentureRpt".equals(strReportID))
        }//end of if(strLink.equals(strReports)&& strSubLink.equals(strSubEnrollment))
		 //Added for IBM.....11.4
        if(strLink.equals(strIbmReports)&& strSubLink.equals(strCorporate))
		{

			DynaActionForm frmReportList=(DynaActionForm)request.getSession().
			getAttribute("frmReportList");
			String strReportID = frmReportList.getString("reportID");
				//log.info("strReportID is " + strReportID);

			if("IBMDeletion".equals(strReportID))
			{
				strSelectPath=strIBMDeletionCutOffReports;
		        	this.loadIBMReportPolicy(frmReportList,request,policyGroupVO);

			 }
			if("IBMGrpPreAuthRpt".equals(strReportID))
			  {
			    strSelectPath=strIBMPreauthDetailReports;
				this.loadIBMReportPolicy(frmReportList,request,policyGroupVO);
			}
			 if("IBMAdditionMaxRec".equals(strReportID))
			 {
				strSelectPath=strIBMAdditioncutoffMaxRecReports;
				this.loadIBMReportPolicy(frmReportList,request,policyGroupVO);
			  }
			 if("Reoptin".equals(strReportID))
			  {
			    strSelectPath=strIBMReoptinDetailReports;
				this.loadIBMReportPolicy(frmReportList,request,policyGroupVO);
			} 
			 if("MontlyRecon".equals(strReportID))
			  {
			    strSelectPath=strIBMReconDetailReports;
				this.loadIBMReportPolicy(frmReportList,request,policyGroupVO);
				this.loadReportPolicy(frmReportList, request, policyGroupVO);
			}
			if("ChildBorn".equals(strReportID))
			  {
			    strSelectPath=strIBMChildBornDetailReports;
				this.loadIBMReportPolicy(frmReportList,request,policyGroupVO);
			}
			if("DailyReport".equals(strReportID))
			  {
			    strSelectPath=strIBMDailyDetailReports;
				this.loadIBMReportPolicy(frmReportList,request,policyGroupVO);
			}
			if("NewIBMDeletion".equals(strReportID))
			  {
			    strSelectPath=strIBMNewDeletionReports;
				this.loadIBMReportPolicy(frmReportList,request,policyGroupVO);
			}
		}

        //Ended.
        if(strLink.equals("MIS Reports")&& strSubLink.equals(strSubEnrollment))
        {
        	DynaActionForm frmMISReports=(DynaActionForm)request.getSession().
        	getAttribute("frmMISReports");
        	String strReportID = frmMISReports.getString("reportID");
        	 if("AccentureReport".equals(strReportID)){
        		strSelectPath="accenturereport";
        		this.loadReportCorpPolicy(frmMISReports,request,policyGroupVO);
        	}//end of else if("AccentureReport".equals(strReportID))
        	 
        }//end of if(strLink.equals("MIS Reports")&& strSubLink.equals(strSubEnrollment))
        if(strLink.equals("MIS Reports")&& strSubLink.equals("Pre-Authorization "))
        {
        	DynaActionForm frmMISReports=(DynaActionForm)request.getSession().
        	getAttribute("frmMISReports");
        		strSelectPath="grppreauthreport";
        		this.loadReportCorpPolicy(frmMISReports,request,policyGroupVO);
        }//end of if(strLink.equals("MIS Reports")&& strSubLink.equals("Pre-Authorization "))
        if(strLink.equals(strReports)&& strSubLink.equals(strPreAuthorization))
        {
        	DynaActionForm frmReportList=(DynaActionForm)request.getSession().
        	getAttribute("frmReportList");
        	String strReportID = frmReportList.getString("reportID");
        	//log.info("strReportID is " + strReportID);
        		strSelectPath=strPreAuthClmRpt;
        		this.loadReportPolicy(frmReportList,request,policyGroupVO);
        }//end of if(strLink.equals(strReports)&& strSubLink.equals(strPreAuthorization))
        return strSelectPath;
    }//end of getClosePath(HttpServletRequest request)

    /**
     * This method loads the policy no list in frmReportList based on the group id     
     */
    private void loadReportCorpPolicy(DynaActionForm frmMISReports,HttpServletRequest request,
    		PolicyGroupVO policyGroupVO) throws TTKException
    {
    	ReportManager reportManagerObject = this.getReportManager();
    	//Long lngGrpRegSeqID = TTKCommon.getLong(frmReportList.getString("groupId"));
    	ArrayList alPolicyNo = null;
    	if(policyGroupVO!=null){
    		alPolicyNo=reportManagerObject.getReportPolicyList(policyGroupVO.getGroupRegnSeqID());
    		frmMISReports.set("alPolicyNo",alPolicyNo);
    	}//end of if(policyGroupVO.getGroupRegnSeqID()!=null)
    	request.getSession().setAttribute("frmMISReports",frmMISReports);
    }//end of loadReportPolicy(DynaActionForm frmReportList,HttpServletRequest request,
    //PolicyGroupVO policyGroupVO) throws TTKException
    
    /**
     * This method loads the policy no list in frmReportList based on the group id     
     */
    private void loadReportPolicy(DynaActionForm frmReportList,HttpServletRequest request,
    		PolicyGroupVO policyGroupVO) throws TTKException
    {
    	ProductPolicyManager prodPolicyManager = this.getProductPolicyManagerObject();
    	//Long lngGrpRegSeqID = TTKCommon.getLong(frmReportList.getString("groupId"));
    	ArrayList alPolicyNo = null;
    	if(policyGroupVO!=null){
    		alPolicyNo=prodPolicyManager.getReportPolicyList(policyGroupVO.getGroupRegnSeqID());
    		frmReportList.set("alPolicyNo",alPolicyNo);
    	}//end of if(policyGroupVO.getGroupRegnSeqID()!=null)
    	request.getSession().setAttribute("frmReportList",frmReportList);
    }//end of loadReportPolicy(DynaActionForm frmReportList,HttpServletRequest request,
    //PolicyGroupVO policyGroupVO) throws TTKException
	//Added by Praveen
    /**
     * This method loads the policy no list in frmReportList based on the group id
     */
    private void loadIBMReportPolicy(DynaActionForm frmReportList,HttpServletRequest request,
    		PolicyGroupVO policyGroupVO) throws TTKException
    {
    	ProductPolicyManager prodPolicyManager = this.getProductPolicyManagerObject();
    	//Long lngGrpRegSeqID = TTKCommon.getLong(frmReportList.getString("groupId"));
    	//ArrayList alPolicyNo = null;
    	String IBMPolicyNo = "";
    	
    	if(policyGroupVO!=null){
    		//alPolicyNo=prodPolicyManager.getReportPolicyList(policyGroupVO.getGroupRegnSeqID());
    		IBMPolicyNo = Long.toString(policyGroupVO.getGroupRegnSeqID());
    		frmReportList.set("IBMPolicyNo",IBMPolicyNo);
    	}//end of if(policyGroupVO.getGroupRegnSeqID()!=null)
    	request.getSession().setAttribute("frmReportList",frmReportList);
    }//end of loadReportPolicy(DynaActionForm frmReportList,HttpServletRequest request,
    //PolicyGroupVO policyGroupVO) throws TTKException
    
    
    
    //Ended.
    /**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return productPolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ProductPolicyManager getProductPolicyManagerObject() throws TTKException
	{
		ProductPolicyManager productPolicyManager = null;
		try
		{
			if(productPolicyManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyManager = (ProductPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProductPolicyManagerBean!com.ttk.business.administration.ProductPolicyManager");
			}//end of if(productPolicyManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strWebConfig);
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManagerObject()
	
	/**
	 * Returns the ReportManager session object for invoking methods on it.
	 * @return ReportManager session object which can be used for method invokation 
	 * @exception throws TTKException  
	 */
	private  ReportManager getReportManager() throws TTKException
	{
		ReportManager reportManager = null;
		try
		{
			if(reportManager == null)
			{
				InitialContext ctx = new InitialContext();
				reportManager = (ReportManager) ctx.lookup("java:global/TTKServices/business.ejb3/ReportManagerBean!com.ttk.business.misreports.ReportManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "tTKReport");
		}//end of catch
		return reportManager;
	}//end getTTKReportManager
}// end of PolicyGroupAction.java
