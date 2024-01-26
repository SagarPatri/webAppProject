/**
 * @ (#) PolicyAccountInfoAction.java Jul 26, 2007
 * Project      : Vidal Health TPA Services
 * File         : PolicyAccountInfoAction.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jul 26, 2007
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.accountinfo;

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
import com.ttk.business.accountinfo.AccountInfoManager;
import com.ttk.common.PolicyAccInfoWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.accountinfo.PolicyAccountInfoDetailVO;
import com.ttk.dto.accountinfo.PolicyAccountInfoVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for Searching the List Policies to see the Account Info.
 * This also provides deletion and updation of products.
 */
public class PolicyAccountInfoAction extends TTKAction {

    private static Logger log = Logger.getLogger( PolicyAccountInfoAction.class );

    //Modes.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";

    // Action mapping forwards.
    private static final String strPolicyList="policylist";
    private static final String strPolicyDetail="policydetail";
    private static final String strMemberdetail="memberdetail";
    private static final String strEndorsement="endorsementslist";
    private static final String strGroupList="grouplist";

    //Exception Message Identifier
    private static final String strAccountInfo="accountinfo";

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
            log.debug("Inside the doDefault method of PolicyAccountInfoAction");
            setLinks(request);

            DynaActionForm frmPolicyAccountInfo =(DynaActionForm)form;
            frmPolicyAccountInfo.initialize(mapping);     //reset the form data

            String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession()
                    .getAttribute("UserSecurityProfile")).getBranchID());

            frmPolicyAccountInfo.set("sTTKBranch",strDefaultBranchID);
            frmPolicyAccountInfo.set("sPolicyType","");
            //get the tbale data from session if exists
            TableData tableData =TTKCommon.getTableData(request);
            //create new table data object
            tableData = new TableData();
            //create the required grid table
            tableData.createTableInfo("PolicyAccountInfoTable",new ArrayList());
            request.getSession().setAttribute("tableData",tableData);
            //((DynaActionForm)form).initialize(mapping);//reset the form data
            return this.getForward(strPolicyList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,strAccountInfo));
        }//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)

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
            log.debug("Inside the doSearch method of PolicyAccountInfoAction");
            setLinks(request);
            //get the session bean from the bean pool for each excecuting thread
            AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();

            //get the tbale data from session if exists
            TableData tableData =TTKCommon.getTableData(request);
            String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
            //if the page number or sort id is clicked
            if(!strPageID.equals("") || !strSortID.equals(""))
            {
                if(!strPageID.equals(""))
                {
                    tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
                    return mapping.findForward(strPolicyList);
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
                tableData.createTableInfo("PolicyAccountInfoTable",null);
                tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));//KOC Cigna_insurance_resriction
                tableData.modifySearchData("search");
            }//end of else
            ArrayList alPolicyList= accountInfoManager.getPolicyList(tableData.getSearchData());
            tableData.setData(alPolicyList, "search");
            //set the table data object to session
            request.getSession().setAttribute("tableData",tableData);
            //finally return to the grid screen
            return this.getForward(strPolicyList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,strAccountInfo));
        }//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                            //HttpServletResponse response)
    
    /**
     * This method is used to search the data based on the Enrollment Id.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSearchOnEnrollmentID(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the doSearchOnEnrollmentID method of PolicyAccountInfoAction");
            setLinks(request);
            //get the session bean from the bean pool for each excecuting thread
            AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();

            DynaActionForm frmPolicyAccountInfo =(DynaActionForm)form;
            frmPolicyAccountInfo.initialize(mapping);     //reset the form data
            
            //get the tbale data from session if exists
            TableData tableData =TTKCommon.getTableData(request);
            frmPolicyAccountInfo.set("sEnrollmentNumber",TTKCommon.checkNull(request.
            		getParameter("sEnrollmentNumber")));
            
            //create the required grid table
            tableData.createTableInfo("PolicyAccountInfoTable",null);
            tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));//KOC Cigna_insurance_resriction
            tableData.modifySearchData("search");
            
            ArrayList alPolicyList= accountInfoManager.getPolicyList(tableData.getSearchData());
            tableData.setData(alPolicyList, "search");
            //set the table data object to session
            request.getSession().setAttribute("tableData",tableData);
            //finally return to the grid screen
            return this.getForward(strPolicyList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,strAccountInfo));
        }//end of catch(Exception exp)
    }//end of doSearchOnEnrollmentID(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    public ActionForward doChangePolicyType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    															HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside CallCenterSearchAction doChangeLogType");
    		String strPolicyType="";
    		DynaActionForm frmPolicyAccountInfo= (DynaActionForm) form;
    		String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession()
                    .getAttribute("UserSecurityProfile")).getBranchID());           
    		strPolicyType = (String)frmPolicyAccountInfo.get("sPolicyType");
    		((DynaActionForm)form).initialize(mapping);		//reset the form data
    		TableData tableData =TTKCommon.getTableData(request);
            //create new table data object
            tableData = new TableData();
            //create the required grid table
            tableData.createTableInfo("PolicyAccountInfoTable",new ArrayList());
            request.getSession().setAttribute("tableData",tableData);
            frmPolicyAccountInfo.set("sTTKBranch",strDefaultBranchID);
    		frmPolicyAccountInfo.set("sPolicyType",strPolicyType);  
    		return this.getForward(strPolicyList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strAccountInfo));
    	}//end of catch(Exception exp)
    }//end of doChangePolicyType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    	//HttpServletResponse response)

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
            log.debug("Inside the doBackward method of PolicyAccountInfoAction");
            setLinks(request);
            //get the session bean from the bean pool for each excecuting thread
            AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();
            TableData tableData = TTKCommon.getTableData(request);
            tableData.modifySearchData(strBackward);
            ArrayList alPolicyList = accountInfoManager.getPolicyList(tableData.getSearchData());
            tableData.setData(alPolicyList, strBackward);
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward(strPolicyList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,strAccountInfo));
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
            log.debug("Inside the doForward method of PolicyAccountInfoAction");
            setLinks(request);
            //get the session bean from the bean pool for each excecuting thread
            AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();
            TableData tableData = TTKCommon.getTableData(request);
            tableData.modifySearchData(strForward);
            ArrayList alPolicyList = accountInfoManager.getPolicyList(tableData.getSearchData());
            tableData.setData(alPolicyList, strForward);
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward(strPolicyList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,strAccountInfo));
        }//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)

    /**
     * This method is used to navigate to detail screen to view the details of selected record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewPolicyDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                          HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the doViewPolicyDetail method of PolicyAccountInfoAction");
            setLinks(request);
            //get the session bean from the bean pool for each excecuting thread
            AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();
            TableData tableData = TTKCommon.getTableData(request);
            DynaActionForm frmPolicyAccountInfoDetail=(DynaActionForm)form;
            
            PolicyAccountInfoVO policyAccountInfoVO = null;
            PolicyAccountInfoDetailVO policyAccountInfoDetailVO=null;
            if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
                policyAccountInfoVO = (PolicyAccountInfoVO)tableData.getRowInfo(Integer.parseInt(request.
                        getParameter("rownum")));

                //add the selected item to the web board and make it as default selected
                this.addToWebBoard(policyAccountInfoVO,request,false);

                //prepare the search params to get the policy detail
                ArrayList<Object> alSearchCriteria=new ArrayList<Object>();
                alSearchCriteria.add(policyAccountInfoVO.getPolicySeqID());
                alSearchCriteria.add(policyAccountInfoVO.getPolicyGroupSeqID());
                alSearchCriteria.add(policyAccountInfoVO.getPolicyTypeID());

                policyAccountInfoDetailVO=accountInfoManager.getPolicy(alSearchCriteria);

                frmPolicyAccountInfoDetail=(DynaActionForm)FormUtils.setFormValues("frmPolicyAccountInfoDetail",
                        policyAccountInfoDetailVO, this, mapping, request);
                frmPolicyAccountInfoDetail=(DynaActionForm)FormUtils.setFormValues("frmPolicyAccountInfoDetail",
                        policyAccountInfoDetailVO, this, mapping, request);
                frmPolicyAccountInfoDetail.set("groupAddressVO",FormUtils.setFormValues("frmGroupAddressDetails",
                		policyAccountInfoDetailVO.getGroupAddressObj(),this,mapping,request));
                frmPolicyAccountInfoDetail.set("familyAddressVO",FormUtils.setFormValues("frmFamilyAddress",
                		policyAccountInfoDetailVO.getFamilyAddressObj(),this,mapping,request));
                frmPolicyAccountInfoDetail.set("insuranceAddressVO",FormUtils.setFormValues("frmInsuranceAddress",
                		policyAccountInfoDetailVO.getInsuranceAddressObj(),this,mapping,request));
               request.getSession().setAttribute("frmPolicyAccountInfoDetail",frmPolicyAccountInfoDetail);
               }//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            else if(PolicyAccInfoWebBoardHelper.checkWebBoardId(request) != null)
            {
                //prepare the search params to get the policy detail
                ArrayList<Object> alSearchCriteria=new ArrayList<Object>();
                alSearchCriteria.add(PolicyAccInfoWebBoardHelper.getPolicySeqId(request));
                alSearchCriteria.add(PolicyAccInfoWebBoardHelper.getPolicyGroupSeqId(request));
                alSearchCriteria.add(PolicyAccInfoWebBoardHelper.getPolicyTypeID(request));

                policyAccountInfoDetailVO=accountInfoManager.getPolicy(alSearchCriteria);

                frmPolicyAccountInfoDetail=(DynaActionForm)FormUtils.setFormValues("frmPolicyAccountInfoDetail",
                        policyAccountInfoDetailVO, this, mapping, request);
                frmPolicyAccountInfoDetail.set("groupAddressVO",FormUtils.setFormValues("frmGroupAddressDetails",
                		policyAccountInfoDetailVO.getGroupAddressObj(),this,mapping,request));
                frmPolicyAccountInfoDetail.set("familyAddressVO",FormUtils.setFormValues("frmFamilyAddress",
                		policyAccountInfoDetailVO.getFamilyAddressObj(),this,mapping,request));
                frmPolicyAccountInfoDetail.set("insuranceAddressVO",FormUtils.setFormValues("frmInsuranceAddress",
                		policyAccountInfoDetailVO.getInsuranceAddressObj(),this,mapping,request));
                request.getSession().setAttribute("frmPolicyAccountInfoDetail",frmPolicyAccountInfoDetail);
                
               
            }//end of else if(TTKCommon.getWebBoardId(request) != null)
            else
            {
                TTKException expTTK = new TTKException();
                expTTK.setMessage("error.policy.required");
                throw expTTK;
            }//end of else
            return this.getForward(strPolicyDetail, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,strAccountInfo));
        }//end of catch(Exception exp)
    }//end of doViewPolicyDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    public ActionForward doViewPrevPolicyDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                          HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the doViewProducts method of PolicyAccountInfoAction");
            setLinks(request);
            //get the session bean from the bean pool for each excecuting thread
            AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();
            TableData tableData = TTKCommon.getTableData(request);
            DynaActionForm frmPolicyAccountInfoDetail=(DynaActionForm)form;
            
	    	
            //create a new Product object
            PolicyAccountInfoVO policyAccountInfoVO =null;
            PolicyAccountInfoDetailVO policyAccountInfoDetailVO=null;
            if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
                policyAccountInfoVO = (PolicyAccountInfoVO)tableData.getRowInfo(Integer.parseInt(request.
                        getParameter("rownum")));

                //add the selected item to the web board and make it as default selected
                this.addToWebBoard(policyAccountInfoVO,request,true);

                //prepare the search params to get the policy detail
                ArrayList<Object> alSearchCriteria=new ArrayList<Object>();
                alSearchCriteria.add(policyAccountInfoVO.getPrevPolicySeqID());
                alSearchCriteria.add(policyAccountInfoVO.getPrevPolicyGroupSeqID());
                alSearchCriteria.add(policyAccountInfoVO.getPolicyTypeID());

                policyAccountInfoDetailVO=accountInfoManager.getPolicy(alSearchCriteria);

                frmPolicyAccountInfoDetail=(DynaActionForm)FormUtils.setFormValues("frmPolicyAccountInfoDetail",
                        policyAccountInfoDetailVO, this, mapping, request);
                frmPolicyAccountInfoDetail.set("groupAddressVO",FormUtils.setFormValues("frmGroupAddressDetails",
                		policyAccountInfoDetailVO.getGroupAddressObj(),this,mapping,request));
                frmPolicyAccountInfoDetail.set("familyAddressVO",FormUtils.setFormValues("frmFamilyAddress",
                		policyAccountInfoDetailVO.getFamilyAddressObj(),this,mapping,request));
                frmPolicyAccountInfoDetail.set("insuranceAddressVO",FormUtils.setFormValues("frmInsuranceAddress",
                		policyAccountInfoDetailVO.getInsuranceAddressObj(),this,mapping,request));
                
    	    	request.getSession().setAttribute("frmPolicyAccountInfoDetail",frmPolicyAccountInfoDetail);
            }//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            return this.getForward(strPolicyDetail, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,strAccountInfo));
        }//end of catch(Exception exp)
    }//end of doViewPrevPolicyDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    public ActionForward doViewMemberDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                          HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the doViewMemberDetail method of PolicyAccountInfoAction");
            setLinks(request);
            TableData tableData = TTKCommon.getTableData(request);

            PolicyAccountInfoVO policyAccountInfoVO =null;
            if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
                policyAccountInfoVO = (PolicyAccountInfoVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));

                //add the selected item to the web board and make it as default selected
                this.addToWebBoard(policyAccountInfoVO,request,false);
          }//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            return mapping.findForward(strMemberdetail);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,strAccountInfo));
        }//end of catch(Exception exp)
    }//end of doViewMemberDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                //HttpServletResponse response)
    
    /**
     * This method is used to navigate to Endorsement list screen to show Endorsements.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewEndorsements(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                          HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the doViewEndorsements method of PolicyAccountInfoAction");
            setLinks(request);
            //get the session bean from the bean pool for each excecuting thread
            AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();
            DynaActionForm frmAccInfoEndorsement=(DynaActionForm)form; 
            frmAccInfoEndorsement.initialize(mapping);//reset the form
            
            
            ArrayList alEndorsementList = accountInfoManager.getEndorsementList(PolicyAccInfoWebBoardHelper.
            		getPolicySeqId(request));
            frmAccInfoEndorsement.set("caption",PolicyAccInfoWebBoardHelper.getWebBoardDesc(request));
            frmAccInfoEndorsement.set("EndorsementList",alEndorsementList);
            request.setAttribute("EndorsementList",alEndorsementList);
            return this.getForward(strEndorsement, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,strAccountInfo));
        }//end of catch(Exception exp)
    }
    
    /**
     * This method is used to navigate to Endorsement list screen to show Endorsements.
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
            log.debug("Inside the doClose method of PolicyAccountInfoAction");
            setLinks(request);
            return this.getForward(strPolicyDetail, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,strAccountInfo));
        }//end of catch(Exception exp)
    }
    

    /**
     * This method is used to get the details of the selected record from web-board.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                            HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the doChangeWebBoard method of PolicyAccountInfoAction");
            setLinks(request);
            if(PolicyAccInfoWebBoardHelper.checkWebBoardId(request)==null)
            {
                TTKException expTTK = new TTKException();
                expTTK.setMessage("error.policy.required");
                throw expTTK;
            }//end of if(PolicyAccInfoWebBoardHelper.checkWebBoardId(request)==null)

            DynaActionForm frmPolicyAccountInfoDetail=(DynaActionForm)form;
            PolicyAccountInfoDetailVO policyAccountInfoDetailVO=null;

            //get the session bean from the bean pool for each excecuting thread
            AccountInfoManager accountInfoManager=this.getAccountInfoManagerObject();

            ArrayList<Object> alSearchCriteria=new ArrayList<Object>();
            alSearchCriteria.add(PolicyAccInfoWebBoardHelper.getPolicySeqId(request));
            alSearchCriteria.add(PolicyAccInfoWebBoardHelper.getPolicyGroupSeqId(request));
            alSearchCriteria.add(PolicyAccInfoWebBoardHelper.getPolicyTypeID(request));

            policyAccountInfoDetailVO=accountInfoManager.getPolicy(alSearchCriteria);

            frmPolicyAccountInfoDetail=(DynaActionForm)FormUtils.setFormValues("frmPolicyAccountInfoDetail",
                    policyAccountInfoDetailVO, this, mapping, request);
            frmPolicyAccountInfoDetail.set("groupAddressVO",FormUtils.setFormValues("frmGroupAddressDetails",
            		policyAccountInfoDetailVO.getGroupAddressObj(),this,mapping,request));
            frmPolicyAccountInfoDetail.set("familyAddressVO",FormUtils.setFormValues("frmFamilyAddress",
            		policyAccountInfoDetailVO.getFamilyAddressObj(),this,mapping,request));
            frmPolicyAccountInfoDetail.set("insuranceAddressVO",FormUtils.setFormValues("frmInsuranceAddress",
            		policyAccountInfoDetailVO.getInsuranceAddressObj(),this,mapping,request));
            request.getSession().setAttribute("frmPolicyAccountInfoDetail",frmPolicyAccountInfoDetail);

            return this.getForward(strPolicyDetail, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,strAccountInfo));
        }//end of catch(Exception exp)
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
            log.debug("Inside the doCopyToWebBoard method of PolicyAccountInfoAction");
            setLinks(request);
            this.populateWebBoard(request);
            return this.getForward(strPolicyList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,strAccountInfo));
        }//end of catch(Exception exp)
    }//end of CopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)

    /**
     * This method is called on click of Select Group icon
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
    	try{
    		setLinks(request);
    		log.debug("Inside PolicyAccountInfoAction doSelectCorporate");
    		DynaActionForm frmPolicyAccountInfo=(DynaActionForm)form;
    		frmPolicyAccountInfo.set("frmChanged","changed");
    		request.getSession().setAttribute("frmPolicyAccountInfo",frmPolicyAccountInfo);
    		return mapping.findForward(strGroupList);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request,mapping,new TTKException(exp,strAccountInfo));
    	}//end of catch(Exception exp)
    }//end of doSelectGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     //HttpServletResponse response)
    
    /**
     * This method is used to
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doClearCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside PolicyAccountInfoAction doClearCorporate");
    		DynaActionForm frmPolicyAccountInfo=(DynaActionForm)form;
    		frmPolicyAccountInfo.set("sGroupId","");
    		frmPolicyAccountInfo.set("sGroupName","");
    		request.getSession().setAttribute("frmPolicyAccountInfo",frmPolicyAccountInfo);
    		return this.getForward(strPolicyList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strAccountInfo));
    	}//end of catch(Exception exp)
    }//end of doClearCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
      //HttpServletResponse response)
    
    /**
     * Returns the AccountInfoManager session object for invoking methods on it.
     * @return accountInfoManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private AccountInfoManager getAccountInfoManagerObject() throws TTKException
    {
        AccountInfoManager accountInfoManager = null;
        try
        {
            if(accountInfoManager == null)
            {
                InitialContext ctx = new InitialContext();
                accountInfoManager = (AccountInfoManager) ctx.lookup("java:global/TTKServices/business.ejb3/AccountInfoManagerBean!com.ttk.business.accountinfo.AccountInfoManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "product");
        }//end of catch
        return accountInfoManager;
    }//end of getAccountInfoManagerObject()

        /**
	     * this method will add search criteria fields and values to the arraylist and will return it
	     * @param frmPolicyAccountInfo formbean which contains the search fields
	     * @return ArrayList contains search parameters
	     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmPolicyAccountInfo,HttpServletRequest request)
    {
//      build the column names along with their values to be searched
        ArrayList<Object> alSearchObjects = new ArrayList<Object>();
        ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();

        //prepare the search BOX parameters
        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyAccountInfo.getString("sEnrollmentNumber")));
        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyAccountInfo.getString("sCustomerCode")));
    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyAccountInfo.getString("sPolicyNumber")));
    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyAccountInfo.getString("sCertificateNumber")));
    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyAccountInfo.getString("sSchemeNumber")));
    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyAccountInfo.getString("sOrderNumber")));
    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyAccountInfo.getString("sGroupId")));
    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyAccountInfo.getString("sEmployeeNumber")));
    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyAccountInfo.getString("sMemberName")));

    	alSearchBoxParams.add((String)frmPolicyAccountInfo.getString("sPolicyType"));
        alSearchBoxParams.add((String)frmPolicyAccountInfo.getString("sInsuranceCompany"));
        alSearchBoxParams.add((String)frmPolicyAccountInfo.getString("sTTKBranch"));
        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyAccountInfo.getString("sEmiratesID")));

        //set the array of Strings as third parameter
        String[] strSearchObjects = alSearchBoxParams.toArray(new String[0]);
        alSearchObjects.add(strSearchObjects);
        alSearchObjects.add(TTKCommon.getUserSeqId(request));//KOC Cigna_insurance_resriction
        return alSearchObjects;
    }//end of populateSearchCriteria(DynaActionForm frmPolicyAccountInfo)

    /**
     * Adds the selected item to the web board and makes it as the selected item in the web board
     * @param  preauthVO  object which contains the information of the preauth
     * * @param String  strIdentifier whether it is preauth or enhanced preauth
     * @param request HttpServletRequest
     * @throws TTKException if any runtime exception occures
     */
    private void addToWebBoard(PolicyAccountInfoVO policyAccountInfoVO, HttpServletRequest request,boolean blnIsPreviousPolicy)throws TTKException
    {
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        ArrayList<Object> alCacheObject = new ArrayList<Object>();
        CacheObject cacheObject = new CacheObject();
        cacheObject.setCacheId(this.prepareWebBoardId(policyAccountInfoVO,blnIsPreviousPolicy)); //set the cacheID

        if(blnIsPreviousPolicy)
        {
            cacheObject.setCacheDesc(policyAccountInfoVO.getPrevPolicyNbr());
        }//end of if(blnIsPreviousPolicy)
        else
        {
            cacheObject.setCacheDesc(policyAccountInfoVO.getPolicyNbr());
        }//end of else
        alCacheObject.add(cacheObject);
        //if the object(s) are added to the web board, set the current web board id
        toolbar.getWebBoard().addToWebBoardList(alCacheObject);
        toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());

        //webboardinvoked attribute will be set as true in request scope
        //to avoid the replacement of web board id with old value if it is called twice in same request scope
        request.setAttribute("webboardinvoked", "true");
    }//end of addToWebBoard(PreAuthVO preAuthVO, HttpServletRequest request,String strIdentifier)throws TTKException

    /**
     * This method populates the web board in the session with the selected items in the grid
     * @param request HttpServletRequest object which contains information about the selected check boxes
     */
    private void populateWebBoard(HttpServletRequest request) throws TTKException
    {
        String[] strChk = request.getParameterValues("chkopt");
        TableData tableData = (TableData)request.getSession().getAttribute("tableData");
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        ArrayList<Object> alCacheObject = new ArrayList<Object>();
        CacheObject cacheObject = null;
        PolicyAccountInfoVO policyAccountInfoVO=new PolicyAccountInfoVO();
        if(strChk!=null&&strChk.length!=0)
        {
            //loop through to populate delete sequence id's and get the value from session for the
            //matching check box value
            for(int i=0; i<strChk.length;i++)
            {
                cacheObject = new CacheObject();
                policyAccountInfoVO=(PolicyAccountInfoVO)tableData.getData().get(Integer.parseInt(strChk[i]));
                cacheObject.setCacheId(this.prepareWebBoardId(policyAccountInfoVO,false));
                cacheObject.setCacheDesc(policyAccountInfoVO.getPolicyNbr());
                alCacheObject.add(cacheObject);
            }//end of for(int i=0; i<strChk.length;i++)
        }//end of if(strChk!=null&&strChk.length!=0)

        if(toolbar != null)
            toolbar.getWebBoard().addToWebBoardList(alCacheObject);
    }//end of populateWebBoard(HttpServletRequest request)

    /**
     * This method prepares the Weboard id for the selected Policy
     * @param policyAccountInfoVO for which webboard id to be prepared
     * @param blnIsPreviousPolicy Indicates whether it is Previous policy
     * @return sbfCacheId String prepared web board id
     * @throws TTKException
     */
    private String prepareWebBoardId(PolicyAccountInfoVO policyAccountInfoVO,
            boolean blnIsPreviousPolicy)throws TTKException
    {
        StringBuffer sbfCacheId=new StringBuffer();

        if(blnIsPreviousPolicy)
        {
            sbfCacheId.append(policyAccountInfoVO.getPrevPolicySeqID()!=null ?String.valueOf(policyAccountInfoVO.
                    getPrevPolicySeqID()):" ");
            sbfCacheId.append("~#~").append(policyAccountInfoVO.getPrevPolicyGroupSeqID()!=null ? String.
                    valueOf(policyAccountInfoVO.getPrevPolicyGroupSeqID()):" ");
        }//end of if(blnIsPreviousPolicy)
        else
        {
            sbfCacheId.append(policyAccountInfoVO.getPolicySeqID()!=null ? String.valueOf(policyAccountInfoVO.
                    getPolicySeqID()):" ");
            sbfCacheId.append("~#~").append(policyAccountInfoVO.getPolicyGroupSeqID()!=null ? String.
                    valueOf(policyAccountInfoVO.getPolicyGroupSeqID()):" ");
        }//end of else
        sbfCacheId.append("~#~").append(TTKCommon.checkNull(policyAccountInfoVO.getPolicyTypeID()).
                equals("")? " ":policyAccountInfoVO.getPolicyTypeID());
        sbfCacheId.append("~#~").append(policyAccountInfoVO.getMemberSeqID()!=null ? String.
                valueOf(policyAccountInfoVO.getMemberSeqID()):" ");
        return sbfCacheId.toString();
    }//end of prepareWebBoardId(PreAuthVO preAuthVO,String strIdentifier)
}//end of PolicyAccountInfoAction.java
