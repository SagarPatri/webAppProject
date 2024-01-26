/**
 * @ (#) InsuranceCompanyAction.java Feb 2, 2006
 * Project 		: TTK HealthCare Services
 * File 			: InsuranceCompanyAction.java
 * Author 		: Pradeep R
 * Company 		: Span Systems Corporation
 * Date Created 	: Feb 2, 2006
 *
 * @author 		: Pradeep R
 * Modified by 	: Arun K N
 * Modified date: May 10, 2007
 * Reason 		: Conversion to dispatch action
 */

package com.ttk.action.dataentryenrollment;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.ttk.business.dataentryenrollment.ParallelPolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.empanelment.InsuranceVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This class is reusable one, used to search for Insurance Company to individual policy,individual policy as group,
 * corporate policies and non corporate policies in enrollment and endorsement flow,Preauth,Claims and Finance.
 */
public class InsuranceCompanyAction extends TTKAction
{
    private static Logger log = Logger.getLogger( InsuranceCompanyAction.class );
    private static final String strBackward="Backward";
    private static final String strForward="Forward";
    private static final String strInsuranceList="insurancelist";
    //links
    private static final String strMaintenance="Maintenance";
    private static final String strInwardEntry="Inward Entry"; 
    //sub links
    private static final String strEnrollment="Enrollment";
    private static final String strIndividualPolicy="Individual Policy";
    private static final String strIndPolicyGroup="Ind. Policy as Group";
    private static final String strCorporatePolicy="Corporate Policy";
    private static final String strNonCorpPolicy="Non-Corporate Policy";
    private static final String strCardPrinting="Card Printing";
    private static final String strProcessing="Processing";
    private static final String strBankFloatAcc="Float Account";
    private static final String strInvoice="Generate Invoice";
    private static final String strPreAuthorization="Pre-Authorization";
    private static final String strClaims="DataEntryClaims";
    private static final String strCustomerCare="Customer Care";
    private static final String strSearch="Search";
    private static final String strCallDetails="Call Details";
    private static final String strFinance="Finance";
    //for other forwards
    private static final String strInwardCompanyList="inwardcompanylist";
    private static final String strIndividualCompanyList="individualcompanylist";
    private static final String strIndGroupCompanyList="indgroupcompanylist";
    private static final String strCorporateCompanyList="corporatecompanylist";
    private static final String strNonCorpCompanyList="noncorpcompanylist";
    private static final String strCardBatchCompanyList="cardbatchcompanylist";
    private static final String strBankFloatAccList="bankfloatacclist";
    private static final String strPreauthdetailinsurance="preauthdetailinsurance";
    private static final String strClaimsdetailinsurance="dataentryclaimsdetailinsurance";
    private static final String strInwardEdit="inwardedit";
    private static final String strIndividualEdit="individualedit";
    private static final String strIndividualGroupEdit="individualgroupedit";
    private static final String strCorporateEdit="corporateedit";
    private static final String strNonCorporateEdit="noncorporateedit";
    private static final String strCardBatchEdit="cardbatchedit";
    private static final String strBankFloatAccEdit="bankfloataccedit";
    private static final String strPreauthdetail="preauthdetail";
    private static final String strClaimsdetail="dataentryclaimsdetail";
    private static final String strCallcenterInsurance="callcenterinsurance";
    private static final String strCallcenterSearch="callcentersearch";
    private static final String strCallcenterDetailsInsurance="callcenterdetailsinsurance";
    private static final String strCallcenterDetails="callcenterdetails";
    private static final String strInvoiceInsurancelist="invoiceinsurancelist";
    private static final String strInvoiceSearch="invoicesearch";
    private static final String strFinanceInsurance="financeinsurance";
    private static final String strFinanceTPAComm="financetpacomm";
    private static final String strChangeDOBO="changedobo";
    private static final String strChangePolicyPrd="changepolicyprd";
    
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
            log.debug("InsuranceCompanyAction.......... Inside doDefault");
            TableData  insuranceTableData =null;

            String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
                    getAttribute("UserSecurityProfile")).getBranchID());
            StringBuffer strCaption=new StringBuffer();

            if((request.getSession()).getAttribute("insuranceTableData") != null)
            {
                insuranceTableData = (TableData)(request.getSession()).getAttribute("insuranceTableData");
            }//end ofif((request.getSession()).getAttribute("insuranceTableData") != null)
            else
            {
                insuranceTableData = new TableData();
            }//end of else

            DynaActionForm frmEnrlInsCompany =(DynaActionForm)form;

            //create new table data object
            insuranceTableData = new TableData();
            //create the required grid table
            insuranceTableData.createTableInfo("InsuranceTable",new ArrayList());
            request.getSession().setAttribute("insuranceTableData",insuranceTableData);

            ((DynaActionForm)form).initialize(mapping);//reset the form data
            strCaption.append(" - ["+TTKCommon.getWebBoardDesc(request)+"]");
            frmEnrlInsCompany.set("caption",strCaption.toString());
            request.getSession().setAttribute("searchparam", null);
            frmEnrlInsCompany.set("sTTKBranchCode", strDefaultBranchID);

            return this.getForward(getForwardPath(request), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request,mapping,expTTK);
        }//end of catch(ETTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request,mapping,new TTKException(exp,strInsuranceList));
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
            log.debug("InsuranceCompanyAction.......... Inside doSearch");
            TableData  insuranceTableData =null;
            if((request.getSession()).getAttribute("insuranceTableData") != null)
            {
                insuranceTableData = (TableData)(request.getSession()).getAttribute("insuranceTableData");
            }//end of if((request.getSession()).getAttribute("insuranceTableData") != null)
            else
            {
                insuranceTableData = new TableData();
            }//end of else

            String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));

            ParallelPolicyManager PolicyManagerObject=this.getPolicyManagerObject();
            //if the page number or sort id is clicked
            if(!strPageID.equals("") || !strSortID.equals(""))
            {
                if(!strPageID.equals(""))
                {
                    insuranceTableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
                    return (mapping.findForward(getForwardPath(request)));
                }///end of if(!strPageID.equals(""))
                else
                {
                    insuranceTableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
                    insuranceTableData.modifySearchData("sort");//modify the search data
                }//end of else
            }//end of if(!strPageID.equals("") || !strSortID.equals(""))
            else
            {
                //create the required grid table
                insuranceTableData.createTableInfo("InsuranceTable",null);
                insuranceTableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form));
                insuranceTableData.modifySearchData("search");
            }//end of else
            ArrayList alInsurance= PolicyManagerObject.getInsuranceCompanyList(insuranceTableData.getSearchData());
            insuranceTableData.setData(alInsurance, "search");
            //set the table data object to session
            request.getSession().setAttribute("insuranceTableData",insuranceTableData);
            //finally return to the grid screen
            return this.getForward(getForwardPath(request), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request,mapping,expTTK);
        }//end of catch(ETTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request,mapping,new TTKException(exp,strInsuranceList));
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
        try
        {
            setLinks(request);
            log.debug("InsuranceCompanyAction.......... Inside doBackward");
            TableData  insuranceTableData =null;
            if((request.getSession()).getAttribute("insuranceTableData") != null)
            {
                insuranceTableData = (TableData)(request.getSession()).getAttribute("insuranceTableData");
            }//end of if((request.getSession()).getAttribute("insuranceTableData") != null)
            else
            {
                insuranceTableData = new TableData();
            }//end of else
            ParallelPolicyManager PolicyManagerObject=this.getPolicyManagerObject();
            insuranceTableData.modifySearchData(strBackward);//modify the search data
            ArrayList alInsurance = PolicyManagerObject.getInsuranceCompanyList(insuranceTableData.getSearchData());
            insuranceTableData.setData(alInsurance, strBackward);//set the table data
            request.getSession().setAttribute("insuranceTableData",insuranceTableData);//set the insuranceTableData object to session
            return this.getForward(getForwardPath(request), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request,mapping,expTTK);
        }//end of catch(ETTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request,mapping,new TTKException(exp,strInsuranceList));
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
        try
        {
            setLinks(request);
            log.debug("InsuranceCompanyAction.......... Inside doForward");
            TableData  insuranceTableData =null;
            if((request.getSession()).getAttribute("insuranceTableData") != null)
            {
                insuranceTableData = (TableData)(request.getSession()).getAttribute("insuranceTableData");
            }//end of if((request.getSession()).getAttribute("insuranceTableData") != null)
            else
            {
                insuranceTableData = new TableData();
            }//end of else
            ParallelPolicyManager PolicyManagerObject=this.getPolicyManagerObject();
            insuranceTableData.modifySearchData(strForward);//modify the search data
            ArrayList alInsurance = PolicyManagerObject.getInsuranceCompanyList(insuranceTableData.getSearchData());
            insuranceTableData.setData(alInsurance, strForward);//set the table data
            request.getSession().setAttribute("insuranceTableData",insuranceTableData);//set the insuranceTableData object to session
            return this.getForward(getForwardPath(request), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request,mapping,expTTK);
        }//end of catch(ETTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request,mapping,new TTKException(exp,strInsuranceList));
        }//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


    /**
     * This method is will update the selected insurance compnay's info in the required form and
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
    public ActionForward doSelectInsuranceCompany(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
            setLinks(request);
            log.debug("InsuranceCompanyAction.......... Inside doSelectInsuranceCompany");
            TableData  insuranceTableData =null;
            if((request.getSession()).getAttribute("insuranceTableData") != null)
            {
                insuranceTableData = (TableData)(request.getSession()).getAttribute("insuranceTableData");
            }//end of if((request.getSession()).getAttribute("insuranceTableData") != null)
            else
            {
                insuranceTableData = new TableData();
            }//end of else

            DynaActionForm frmInsuranceDetail = (DynaActionForm)form;
            //create a new Product object
            InsuranceVO insuranceVO = new InsuranceVO();

            String strActiveLink=TTKCommon.getActiveLink(request);
            String strSubLinks=TTKCommon.getActiveSubLink(request);
            String strActiveTab=TTKCommon.getActiveTab(request);
            Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");

            if(strSubLinks.equals(strProcessing))
            {
                DynaActionForm frmPreAuthGeneral=(DynaActionForm)request.getSession().getAttribute("frmPreAuthGeneral");
                DynaActionForm frmClaimantDetails=(DynaActionForm)frmPreAuthGeneral.get("claimantDetailsVO");
                if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
                {
                    insuranceVO = (InsuranceVO)insuranceTableData.getRowInfo(Integer.parseInt(
                    											(String)(frmInsuranceDetail).get("rownum")));
                    if(frmPreAuthGeneral!=null)
                    {
                        frmClaimantDetails.set("insSeqID",insuranceVO.getInsuranceSeqID().toString());
                        frmClaimantDetails.set("companyCode",insuranceVO.getCompanyCodeNbr());
                        frmClaimantDetails.set("companyName",insuranceVO.getCompanyName());
                        frmPreAuthGeneral.set("frmChanged","changed");
                    }//end of if(frmPreAuthGeneral!=null)
                }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
                toolBar.getConflictIcon().setVisible(toolBar.getConflictIcon().isVisible() 
                									&& frmPreAuthGeneral.get("discPresentYN").equals("Y"));
            }//end of if(strSubLinks.equals(strProcessing))

            if(strSubLinks.equals(strCardPrinting))
            {
                ArrayList alInsProducts = null;
                HashMap hmInsProducts = null;
                ParallelPolicyManager endorsementObject=this.getPolicyManagerObject();
                DynaActionForm frmCreateCardBatch=(DynaActionForm)request.getSession().getAttribute(
                												"frmCreateCardBatch");
                if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
                {
                    insuranceVO = (InsuranceVO)insuranceTableData.getRowInfo(Integer.parseInt(
                    											(String)(frmInsuranceDetail).get("rownum")));
                    if(frmCreateCardBatch!=null)
                    {
                        frmCreateCardBatch.set("companyName",insuranceVO.getCompanyName());
                        frmCreateCardBatch.set("insuranceSeqID",insuranceVO.getInsuranceSeqID().toString());
                        frmCreateCardBatch.set("officeCode",insuranceVO.getCompanyCodeNbr());
                        if(!(TTKCommon.checkNull(frmCreateCardBatch.getString("insuranceSeqID")).equals("")))
                        {
                            hmInsProducts = endorsementObject.getProductInfo(TTKCommon.getLong(
                            							frmCreateCardBatch.getString("insuranceSeqID")));
                            //setting alInsProducts to session if enrolTypeID present
                            if(!(TTKCommon.checkNull(frmCreateCardBatch.getString("enrolTypeID")).equals("")))
                            {
                                alInsProducts =(ArrayList)hmInsProducts.get(frmCreateCardBatch.getString(
                                																"enrolTypeID"));
                                if((alInsProducts!=null))
                                {
                                    frmCreateCardBatch.set("alInsProducts",alInsProducts);
                                }//end of if((alInsProducts!=null))
                            }//end of if(!(TTKCommon.checkNull(frmCreateCardBatch.getString("enrolTypeID")).equals("")))
                        }//end of if(!(TTKCommon.checkNull(frmCreateCardBatch.getString("insuranceSeqID")).equals("")))
                        request.getSession().setAttribute("hmInsProducts",hmInsProducts);
                    }//end of if(frmCreateCardBatch!=null)
                }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            }//end of if(strSubLinks.equals(strCardPrinting))

            if(strActiveLink.equals(strInwardEntry))
            {
            	if(strSubLinks.equals(strEnrollment))
	            {
	                DynaActionForm frmBatch=(DynaActionForm)request.getSession().getAttribute("frmBatch");
	                if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	                {
	                    insuranceVO = (InsuranceVO)insuranceTableData.getRowInfo(Integer.parseInt((String)(
	                    													frmInsuranceDetail).get("rownum")));
	                    if(frmBatch!=null)
	                    {
	                        frmBatch.set("companyName",insuranceVO.getCompanyName());
	                        frmBatch.set("insuranceSeqID",insuranceVO.getInsuranceSeqID().toString());
	                        frmBatch.set("officeCode",insuranceVO.getCompanyCodeNbr());
	                        frmBatch.set("frmChanged","changed");
	                    }//end of if(frmBatch!=null)
	                }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	            }//end of if(strSubLinks.equals("Enrollment"))
            }//end of if(strActiveLink.equals(strInwardEntry))
            if(strSubLinks.equals(strBankFloatAcc))
            {
                DynaActionForm frmFloatAccDetails=(DynaActionForm)request.getSession().getAttribute(
                																				"frmFloatAccDetails");
                if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
                {
                    insuranceVO = (InsuranceVO)insuranceTableData.getRowInfo(Integer.parseInt((String)
                    														(frmInsuranceDetail).get("rownum")));
                    if(frmFloatAccDetails!=null)
                    {
                        frmFloatAccDetails.set("insComp",insuranceVO.getCompanyName());
                        frmFloatAccDetails.set("insSeqID",insuranceVO.getInsuranceSeqID().toString());
                        frmFloatAccDetails.set("insCompCode",insuranceVO.getCompanyCodeNbr());
                        frmFloatAccDetails.set("insTtkBranch",insuranceVO.getTTKBranch());
                        frmFloatAccDetails.set("insOfficeType",insuranceVO.getOfficeType());

                    }//end of if(frmFloatAccDetails!=null)
                }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            }//end of if(strSubLinks.equals(strBankFloatAcc))

            if(strActiveLink.equals(strCustomerCare))
            {
                if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
                {
                    insuranceVO = (InsuranceVO)insuranceTableData.getRowInfo(Integer.parseInt((String)
                    															(frmInsuranceDetail).get("rownum")));
                    if(strActiveTab.equals(strSearch))
                    {
                        DynaActionForm frmCallCenterList=(DynaActionForm)request.getSession().getAttribute(
                        																"frmCallCenterList");
                        if(frmCallCenterList != null)
                        {
                            frmCallCenterList.set("id",insuranceVO.getCompanyName());
                            frmCallCenterList.set("name",insuranceVO.getCompanyCodeNbr());
                            frmCallCenterList.set("seqID",insuranceVO.getInsuranceSeqID().toString());
                        }//end of if(frmCallCenterList != null)
                    }//end of if(strActiveTab.equals(strSearch))
                    else if(strActiveTab.equals(strCallDetails))
                    {
                        DynaActionForm frmCallCenterDetails=(DynaActionForm)request.getSession().getAttribute(
                        																	"frmCallCenterDetails");
                        if(frmCallCenterDetails != null)
                        {
                            frmCallCenterDetails.set("insCompName",insuranceVO.getCompanyName());
                            frmCallCenterDetails.set("insCompCodeNbr",insuranceVO.getCompanyCodeNbr());
                            frmCallCenterDetails.set("insSeqID",insuranceVO.getInsuranceSeqID().toString());
                            frmCallCenterDetails.set("frmChanged","changed");
                        }//end of if(frmCallCenterDetails != null)
                    }//end of else if(strActiveTab.equals(strCallDetails))
                }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            }//end of if(strActiveLink.equals(strCustomerCare))

            if(strSubLinks.equals(strInvoice))
            {
                DynaActionForm frmInvoice=(DynaActionForm)request.getSession().getAttribute("frmInvoice");
                if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
                {
                    insuranceVO = (InsuranceVO)insuranceTableData.getRowInfo(Integer.parseInt((String)
                    														(frmInsuranceDetail).get("rownum")));
                    if(frmInvoice!=null)
                    {
                        frmInvoice.set("insComp",insuranceVO.getCompanyName());
                        frmInvoice.set("insSeqID",insuranceVO.getInsuranceSeqID().toString());
                        frmInvoice.set("insCompCode",insuranceVO.getCompanyCodeNbr());

                    }//end of if(frmInvoice!=null)
                    if(request.getSession().getAttribute("tableData") != null)
                    {
                        TableData tableData =(TableData)request.getSession().getAttribute("tableData");
                        tableData.modifySearchData("search");
                        //clear the searched data when insurance company or group is cleared
                        tableData.setData(null, "search"); 
                        request.getSession().setAttribute("tableData",tableData);
                    }//end of if(request.getSession().getAttribute("tableData") != null)
                }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            }//end of if(strSubLinks.equals(strBankFloatAcc))
            
            if(strSubLinks.equals(strFinance))
            {
            	 DynaActionForm frmReportList=(DynaActionForm)request.getSession().getAttribute("frmReportList");
                 if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
                 {
                     insuranceVO = (InsuranceVO)insuranceTableData.getRowInfo(Integer.parseInt((String)(
                     													frmInsuranceDetail).get("rownum")));
                     if(frmReportList!=null)
                     {
                    	 frmReportList.set("companyName",insuranceVO.getCompanyName());
                    	 frmReportList.set("insuranceSeqID",insuranceVO.getInsuranceSeqID().toString());
                    	 frmReportList.set("officeCode",insuranceVO.getCompanyCodeNbr());
                     }//end of if(frmReportList!=null)
                 }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            }//end of if(strSubLinks.equals(strBankFloatAcc))

            if(strActiveLink.equals(strMaintenance))
            {
            	if(strSubLinks.equals(strEnrollment))
	            {
            		if(!(TTKCommon.checkNull(request.getSession().getAttribute("frmChangeDOBO")).equals("")))
	                {
	            		DynaActionForm frmChangeDOBO=(DynaActionForm)request.getSession().getAttribute("frmChangeDOBO");
		                if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		                {
		                	insuranceVO = (InsuranceVO)insuranceTableData.getRowInfo(Integer.parseInt((String)(
		                    													frmInsuranceDetail).get("rownum")));
		                    if(frmChangeDOBO!=null)
		                    {
		                    	frmChangeDOBO.set("companyName",insuranceVO.getCompanyName());
		                    	frmChangeDOBO.set("insuranceSeqID",insuranceVO.getInsuranceSeqID().toString());
		                    	frmChangeDOBO.set("companyCodeNbr",insuranceVO.getCompanyCodeNbr());
		                    	frmChangeDOBO.set("frmChanged","changed");
		                    }//end of if(frmChangeDOBO!=null)
		                }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	                }//end of if(!(TTKCommon.checkNull(request.getSession().getAttribute("frmChangeDOBO")).equals("")))
	                
            		if(!(TTKCommon.checkNull(request.getSession().getAttribute("frmChangePolicyPrd")).equals("")))
            		{
		                DynaActionForm frmChangePolicyPrd=(DynaActionForm)request.getSession().getAttribute("frmChangePolicyPrd");
		                if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		                {
		                	insuranceVO = (InsuranceVO)insuranceTableData.getRowInfo(Integer.parseInt((String)(
		                    													frmInsuranceDetail).get("rownum")));
		                    if(frmChangePolicyPrd!=null)
		                    {
		                    	frmChangePolicyPrd.set("companyName",insuranceVO.getCompanyName());
		                    	frmChangePolicyPrd.set("insuranceSeqID",insuranceVO.getInsuranceSeqID().toString());
		                    	frmChangePolicyPrd.set("companyCodeNbr",insuranceVO.getCompanyCodeNbr());
		                    	frmChangePolicyPrd.set("frmChanged","changed");
		                    }//end of if(frmChangePolicyPrd!=null)
		                }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            		}//end of if(!(TTKCommon.checkNull(request.getSession().getAttribute("frmChangePolicyPrd")).equals("")))
	            }//end of if(strSubLinks.equals("Enrollment"))
            }//end of if(strActiveLink.equals(strMaintenance))
            return this.getForward(getClosePath(request), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request,mapping,expTTK);
        }//end of catch(ETTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request,mapping,new TTKException(exp,strInsuranceList));
        }//end of catch(Exception exp)
    }//end of doSelectInsuranceCompany(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    	 log.debug("InsuranceCompanyAction.......... Inside doClose");
        return doSelectInsuranceCompany(mapping,form,request,response);

    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * Returns the ParallelPolicyManager session object for invoking methods on it.
     * @return ParallelPolicyManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private ParallelPolicyManager getPolicyManagerObject() throws TTKException
    {
        ParallelPolicyManager policyManager = null;
        try
        {
            if(policyManager == null)
            {
                InitialContext ctx = new InitialContext();
                policyManager = (ParallelPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ParallelPolicyManagerBean!com.ttk.business.dataentryenrollment.ParallelPolicyManager");
            }//end of if(ParallelPolicyManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strInsuranceList);
        }//end of catch
        return policyManager;
    }//end getPolicyManagerObject()

    /**
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmInsuranceList DynaActionForm
     * @return ArrayList contains search parameters
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmInsuranceList)
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(((String)frmInsuranceList.get("sInsuranceSeqID")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInsuranceList.get("sCompanyCode")));
        alSearchParams.add((String)frmInsuranceList.get("sTTKBranchCode"));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmInsuranceList)

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
        DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
        String strSwitchType="";

        if(frmPolicyList!=null)
        {
            strSwitchType=frmPolicyList.getString("switchType");
        }//end of if(frmPolicyList!=null)


        if(strActiveLink.equals(strPreAuthorization))
        {
            strForwardPath=strPreauthdetailinsurance;
        }//end of if(strActiveLink.equals(strPreAuthorization))
        else if(strActiveLink.equals(strClaims))
        {
            strForwardPath=strClaimsdetailinsurance;
        }//end of else if(strActiveLink.equals(strClaims))
        if(strActiveSubLink.equals(strEnrollment))
        {
            strForwardPath=strInwardCompanyList;
        }//end of if(strActiveSubLink.equals(strEnrollment))
        else if(strActiveSubLink.equals(strCardPrinting))
        {
            strForwardPath=strCardBatchCompanyList;
        }//end of else if(strActiveSubLink.equals(strCardPrinting))
        else if(strActiveSubLink.equals(strBankFloatAcc))
        {
            strForwardPath=strBankFloatAccList;
        }//end of else if(strActiveSubLink.equals(strBankFloatAcc))
        else if(strActiveSubLink.equals(strInvoice))
        {
            strForwardPath=strInvoiceInsurancelist;
        }//end of else if(strActiveSubLink.equals(strInvoice))
        else if(strActiveSubLink.equals(strFinance))
        {
        	strForwardPath=strFinanceInsurance;
        }//end of else if(strActiveSubLink.equals(strFinance))        

        if(strActiveLink.equals(strCustomerCare))
        {
            if(strActiveTab.equals(strSearch))
            {
                strForwardPath=strCallcenterInsurance;
            }//end of if(strActiveTab.equals(strSearch))
            else if(strActiveTab.equals(strCallDetails))
            {
                strForwardPath=strCallcenterDetailsInsurance;
            }//end of else if(strActiveTab.equals(strCallDetails))
        }//end of if(strActiveLink.equals(strCustomerCare))
        else if(strSwitchType.equals("END"))
        {
            if(strActiveTab.equals("Endorsement"))
            {
                if(strActiveSubLink.equals("Individual Policy"))
                {
                    strForwardPath="individualendorselist";
                }//end of if(strActiveSubLink.equals("Individual Policy"))
                else if(strActiveSubLink.equals("Ind. Policy as Group"))
                {
                    strForwardPath="indpolicyasgroupendorselist";
                }//end of else if(strActiveSubLink.equals("Ind. Policy as Group"))
                else if(strActiveSubLink.equals("Corporate Policy"))
                {
                    strForwardPath="corporateendorselist";
                }//end of else if(strActiveSubLink.equals("Corporate Policy"))
                else if(strActiveSubLink.equals("Non-Corporate Policy"))
                {
                    strForwardPath="noncorpendorselist";
                }//end of else if(strActiveSubLink.equals("Non-Corporate Policy"))
            }//end of if(strActiveTab.equals("Endorsement"))
            else
            {
                if(strActiveSubLink.equals(strIndividualPolicy))
                {
                    strForwardPath=strIndividualCompanyList;
                }//end of if(strActiveSubLink.equals(strIndividualPolicy))
                else if(strActiveSubLink.equals(strIndPolicyGroup))
                {
                    strForwardPath=strIndGroupCompanyList;
                }//end of else if(strActiveSubLink.equals(strIndPolicyGroup))
                else if(strActiveSubLink.equals(strCorporatePolicy))
                {
                    strForwardPath=strCorporateCompanyList;
                }//end of else if(strActiveSubLink.equals(strCorporatePolicy))
                else if(strActiveSubLink.equals(strNonCorpPolicy))
                {
                    strForwardPath=strNonCorpCompanyList;
                }//end of else if(strActiveSubLink.equals(strNonCorpPolicy))
            }//end of else
        }//end of else if(strSwitchType.equals("END"))
        return strForwardPath;
    }//end of getForwardPath(HttpServletRequest request)


    /**
     * This method returns the forward path for next view based on the Flow
     * when insurance company is selected or Close button is pressed.
     *
     * @param request HttpServletRequest currnet request
     * @return strClosePath String forward path for the next view
     */
    private String getClosePath(HttpServletRequest request)throws TTKException
    {
        String strClosePath=null;
        String strActiveLink=TTKCommon.getActiveLink(request);
        String strSubLinks=TTKCommon.getActiveSubLink(request);
        String strActiveTab=TTKCommon.getActiveTab(request);

        if(strSubLinks.equals(strEnrollment))
        {
            strClosePath=strInwardEdit;
        }//end of if(strSubLinks.equals(strEnrollment))
        else  if(strSubLinks.equals(strCardPrinting))
        {
            strClosePath=strCardBatchEdit;
        }//end of else  if(strSubLinks.equals(strCardPrinting))

        if(strSubLinks.equals(strIndividualPolicy))
        {
            strClosePath=strIndividualEdit;
        }//end of if(strSubLinks.equals(strIndividualPolicy))

        if(strSubLinks.equals(strIndPolicyGroup))
        {
            strClosePath=strIndividualGroupEdit;
        }//end of if(strSubLinks.equals(strIndPolicyGroup))
        else if(strSubLinks.equals(strCorporatePolicy))
        {
            strClosePath=strCorporateEdit;
        }//end of else if(strSubLinks.equals(strCorporatePolicy))
        else if(strSubLinks.equals(strNonCorpPolicy))
        {
            strClosePath=strNonCorporateEdit;
        }//end of else if(strSubLinks.equals(strNonCorpPolicy))
        else  if(strActiveLink.equals(strPreAuthorization))
        {
            strClosePath=strPreauthdetail;
        }//end of else  if(strActiveLink.equals(strPreAuthorization))
        else  if(strActiveLink.equals(strClaims))
        {
            strClosePath=strClaimsdetail;
        }//end of else  if(strActiveLink.equals(strClaims))
        else if(strSubLinks.equals(strBankFloatAcc))
        {
            strClosePath=strBankFloatAccEdit;
        }//end of else if(strSubLinks.equals(strBankFloatAcc))
        else if(strSubLinks.equals(strInvoice))
        {
            strClosePath=strInvoiceSearch;
        }//end of else if(strSubLinks.equals(strInvoice))
        else if(strSubLinks.equals(strFinance))
        {
        	strClosePath=strFinanceTPAComm;
        }//end of else if(strSubLinks.equals(strFinance))
        if(strActiveLink.equals(strCustomerCare))
        {
            if(strActiveTab.equals(strSearch))
            {
                strClosePath=strCallcenterSearch;
            }//end of if(strActiveTab.equals(strSearch))
            else if(strActiveTab.equals(strCallDetails))
            {
                strClosePath=strCallcenterDetails;
            }//end of else if(strActiveTab.equals(strCallDetails))
        }//end of if(strActiveLink.equals(strCustomerCare))
        if(strActiveLink.equals(strMaintenance))
        {
        	if(strSubLinks.equals(strEnrollment))
        	{
        		if(!(TTKCommon.checkNull(request.getSession().getAttribute("frmChangeDOBO")).equals("")))
                {
        			strClosePath=strChangeDOBO;
                }//end of if(!(TTKCommon.checkNull(request.getSession().getAttribute("frmChangeDOBO")).equals("")))
        		if(!(TTKCommon.checkNull(request.getSession().getAttribute("frmChangePolicyPrd")).equals("")))
                {
        			strClosePath=strChangePolicyPrd;
                }//end of if(!(TTKCommon.checkNull(request.getSession().getAttribute("frmChangePolicyPrdfrmChangePolicyPrd")).equals("")))
        	}//end of if(strSubLinks.equals(strEnrollment))
        }//end of if(strActiveLink.equals(strMaintenance))
        return strClosePath;
    }//end of getClosePath(HttpServletRequest request)
}//end of InsuranceCompanyAction.java


