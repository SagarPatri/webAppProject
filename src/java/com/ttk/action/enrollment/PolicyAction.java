/**
 * @ (#) PolicyAction.java Feb 2, 2006
 * Project      : TTK HealthCare Services
 * File         : PolicyAction.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Feb 2, 2006
 *
 * @author       :  Arun K N
 * Modified by   :  Arun K N
 * Modified date :  May 8, 2007
 * Reason        : Conversion to Dispatch Action
 */

package com.ttk.action.enrollment;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.enrollment.PolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.enrollment.PolicyDetailVO;
import com.ttk.dto.enrollment.PolicyVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import com.ttk.action.tree.TreeData;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.dto.enrollment.MemberVO;
/**
 * This class is reusable one, used to search for individual policy,individual policy as group,
 * corporate policies and non corporate policies in enrollment and endorsement flow.
 * This class also provides option for deleting the selected policies.
 * This class also provides facility to add the selected policies to the webboard.
 *
 */
public class PolicyAction extends TTKAction{

    private static Logger log = Logger.getLogger(PolicyAction.class);
    //declrations of modes
    private static final String strForward="Forward";
    private static final String strBackward="Backward";
    private static final String strDeleteList="DeleteList";

    //declare forward paths
    private static final String strIndPolicyList="indpolicylist";
    private static final String strGrpPolicyList="grppolicylist";
    private static final String strCorpPolicyList="corppolicylist";
    private static final String strNonCorpPolicyList="noncorppolicylist";
    private static final String strMembersDetail="membersdetail";
    private static final String strEndorsementDetails="endorsementdetails";
    private static final String strPolicyDetail="policydetail";
    private static final String strGroupList="grouplist";

    //declaration of other constants used in this class
    private static final String strIndPolicy="Individual Policy";
    private static final String strIndGrpPolicy="Ind. Policy as Group";
    private static final String strCorporatePolicy="Corporate Policy";
    private static final String strNonCorporatePolicy="Non-Corporate Policy";
    private static final String strEnrollment="ENM";
    private static final String strEndorsement="END";
    private static final String strBenefitDetails="BenefitDetails";

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
        	
            log.debug("Inside the Default method of PolicyAction");
            DynaActionForm frmPolicyList=(DynaActionForm)form;  //get the DynaActionForm instance
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            //call the setlinks method
            this.setLinks(request,strSwitchType);
            String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession()
                    .getAttribute("UserSecurityProfile")).getBranchID());

            //clear the dynaform if visting from left links for the first time
            if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
            {
                frmPolicyList.initialize(mapping);//reset the form data
                frmPolicyList.set("sTtkBranch",strDefaultBranchID);
                frmPolicyList.set("switchType",strEnrollment);  //set switch mode to enrollment
                strSwitchType=strEnrollment;
            }//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            //get the forward path
            String strForwardPath=getForwardPath(strActiveSubLink);

            //build the Caption
            frmPolicyList.set("caption",buildCaption(strSwitchType,strActiveSubLink));

            //create the grid table ans set it in appropriate scope
            TableData policyListData = new TableData();
            policyListData.createTableInfo("PolicyEnrollmentTable",new ArrayList());
            this.setColumnVisiblity(policyListData,strActiveSubLink,strEnrollment);
            this.setColumnWidth(policyListData,strActiveSubLink,strEnrollment);
            request.getSession().setAttribute("policyListData",policyListData);
            return this.getForward(strForwardPath, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"policyList"));
        }//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to Switch between Enrollment flow and Endorsement flow
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSwitchTo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.debug("Inside the doSwitchTo method of PolicyAction");
            DynaActionForm frmPolicyList=(DynaActionForm)form;  //get the DynaActionForm instance
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            //call the setlinks method
            this.setLinks(request,strSwitchType);
            String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession()
                    .getAttribute("UserSecurityProfile")).getBranchID());

            TableData policyListData = new TableData();
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            //initialize the formbean when user switches the mode
            frmPolicyList.initialize(mapping);
            frmPolicyList.set("sTtkBranch",strDefaultBranchID);
            frmPolicyList.set("switchType",strSwitchType);

            //load the toolbar based on Switch value
            Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
            toolbar.setLinks(TTKCommon.getActiveLink(request)+"."+
                    TTKCommon.getActiveSubLink(request)+"."+strSwitchType+"."+TTKCommon.getActiveTab(request));

            //load the appropriate table and set the column visibality
            if(strSwitchType.equals(strEnrollment))
            {
                policyListData.createTableInfo("PolicyEnrollmentTable",null);
            }//end of if(strSwitchType.equals(strEnrollment))
            else if(strSwitchType.equals(strEndorsement))
            {
                policyListData.createTableInfo("PolicyEndorsementTable",null);
            }//end of else if(strSwitchType.equals(strEndorsement))
            this.setColumnVisiblity(policyListData,strActiveSubLink,strSwitchType);
            this.setColumnWidth(policyListData,strActiveSubLink,strSwitchType);

            //build the caption
            frmPolicyList.set("caption",buildCaption(strSwitchType,strActiveSubLink));
            request.getSession().setAttribute("policyListData",policyListData);

            return this.getForward(getForwardPath(strActiveSubLink), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"policyList"));
        }//end of catch(Exception exp)
    }//end of doSwitchTo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
        	log.debug("Inside the doSearch method of PolicyAction");
            DynaActionForm frmPolicyList=(DynaActionForm)form;  //get the DynaActionForm instance
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            //call the setlinks method
            this.setLinks(request,strSwitchType);

            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            //get the session from the bean pool for each exceuting thread
            PolicyManager policyManagerObject=this.getPolicyManagerObject();

            ArrayList alPolicyList=null;
            TableData policyListData=null;

            //get the table data from session if exists
            if((request.getSession()).getAttribute("policyListData") != null)
            {
                policyListData = (TableData)(request.getSession()).getAttribute("policyListData");
            }//end of if((request.getSession()).getAttribute("policyListData") != null)
            else
            {
                policyListData = new TableData();
            }//end of else

            String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
            //if the page number or sort id is clicked
            if(!strPageID.equals("") || !strSortID.equals(""))
            {
                if(!strPageID.equals(""))
                {
                    policyListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.
                    		getParameter("pageId"))));
                    return (mapping.findForward(getForwardPath(strActiveSubLink)));
                }///end of if(!strPageID.equals(""))
                else
                {
                    policyListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
                    policyListData.modifySearchData("sort");//modify the search data
                }//end of else
            }//end of if(!strPageID.equals("") || !strSortID.equals(""))
            else
            {
                //create the required grid table based on the SwitchType
                if(strSwitchType.equals(strEnrollment))
                {
               	
                    policyListData.createTableInfo("PolicyEnrollmentTable",null);
                  
                }//end of  if(strSwitchType.equals(strEnrollment))
                else if(strSwitchType.equals(strEndorsement))
                {
                    policyListData.createTableInfo("PolicyEndorsementTable",null);
                }//end of else if(strSwitchType.equals(strEndorsement))

                policyListData.setSearchData(this.populateSearchCriteria(frmPolicyList,request,strActiveSubLink));
                policyListData.setSortData("0001");
                if(strSwitchType.equals(strEnrollment)){
                	policyListData.setSortColumnName("POLICY_SEQ_ID,tpa_enrollment_id");
                }//end of if(strSwitchType.equals(strEnrollment))
                else if(strSwitchType.equals(strEndorsement)){
                	policyListData.setSortColumnName("ENDORSEMENT_SEQ_ID,tpa_enrollment_id");
                }//end of if(strSwitchType.equals(strEndorsement))
                policyListData.setSortOrder("ASC");
                this.setColumnVisiblity(policyListData,strActiveSubLink,strSwitchType);
                this.setColumnWidth(policyListData,strActiveSubLink,strSwitchType);
                policyListData.modifySearchData("search");
            }//end of else
            //call the DAO to get the records
            alPolicyList = policyManagerObject.getPolicyList(policyListData.getSearchData());
            policyListData.setData(alPolicyList,"search");

            request.getSession().setAttribute("policyListData",policyListData);
            request.getSession().setAttribute("frmPolicyList",frmPolicyList);
            return this.getForward(getForwardPath(strActiveSubLink), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"policyList"));
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
        	log.debug("Inside the doForward method of PolicyAction");
        	DynaActionForm frmPolicyList=(DynaActionForm)form;  //get the DynaActionForm instance
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            //call the setlinks method
            this.setLinks(request,strSwitchType);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            //get the session from the bean pool for each exceuting thread
            PolicyManager policyManagerObject=this.getPolicyManagerObject();

            //get the table data from session if exists
            TableData policyListData=null;
            if((request.getSession()).getAttribute("policyListData") != null)
            {
                policyListData = (TableData)(request.getSession()).getAttribute("policyListData");
            }//end of if((request.getSession()).getAttribute("policyListData") != null)
            else
            {
                policyListData = new TableData();
            }//end of else

            policyListData.modifySearchData(strForward);
            //call the DAO to get the records
            ArrayList alPolicyList = policyManagerObject.getPolicyList(policyListData.getSearchData());
            policyListData.setData(alPolicyList,strForward);
            //set the table data object to session
            request.getSession().setAttribute("policyListData",policyListData);
            return this.getForward(getForwardPath(strActiveSubLink), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"policyList"));
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
        try
        {
        	log.debug("Inside the doBackward method of PolicyAction");
            DynaActionForm frmPolicyList=(DynaActionForm)form;
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            //call the setlinks method
            this.setLinks(request,strSwitchType);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            //get the session from the bean pool for each exceuting thread
            PolicyManager policyManagerObject=this.getPolicyManagerObject();

            //get the table data from session if exists
            TableData policyListData=null;
            if((request.getSession()).getAttribute("policyListData") != null)
            {
                policyListData = (TableData)(request.getSession()).getAttribute("policyListData");
            }//end of if((request.getSession()).getAttribute("policyListData") != null)
            else
            {
                policyListData = new TableData();
            }//end of else

            policyListData.modifySearchData(strBackward);
            ArrayList alPolicyList = policyManagerObject.getPolicyList(policyListData.getSearchData());
            policyListData.setData(alPolicyList,strBackward);
            request.getSession().setAttribute("policyListData",policyListData);
            return this.getForward(getForwardPath(strActiveSubLink), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"policyList"));
        }//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
        	log.debug("Inside the doCopyToWebBoard method of PolicyAction");
            DynaActionForm frmPolicyList=(DynaActionForm)form;  //get the DynaActionForm instance
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            //call the setlinks method
            this.setLinks(request,strSwitchType);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            this.populateWebBoard(request,strSwitchType);
            return this.getForward(getForwardPath(strActiveSubLink), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"policyList"));
        }//end of catch(Exception exp)
    }//end of doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to delete the selected records from the grid
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
        	log.debug("Inside the doDeleteList method of PolicyAction");
            DynaActionForm frmPolicyList=(DynaActionForm)form;  //get the DynaActionForm instance
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            //call the setlinks method
            this.setLinks(request,strSwitchType);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            //get the session from the bean pool for each exceuting thread
            PolicyManager policyManagerObject=this.getPolicyManagerObject();

            StringBuffer sbfDeleteId = new StringBuffer("|");
            int iCount=0;
            //populate the delete string which contains the sequence id's to be deleted
            sbfDeleteId.append(populateDeleteId(request,(TableData)request.getSession().
            		getAttribute("policyListData"),strSwitchType));

            ArrayList <Object>alDeleteMode=new ArrayList<Object>();
            if(strSwitchType.equals(strEnrollment))
            {
                alDeleteMode.add("ENROLLMENT");
            }//end of if(strSwitchType.equals(strEnrollment))
            else
            {
                alDeleteMode.add("ENDORSEMENT");
            }//end of else if(strSwitchType.equals(strEnrollment))

            alDeleteMode.add(sbfDeleteId.toString());
            alDeleteMode.add(null);//policy_seq_id/endorsement_seq_id is not required. //delete check for null
            alDeleteMode.add(strSwitchType);//Mode can be 'ENM','END'
            alDeleteMode.add(getPolicyType(strActiveSubLink));  //Type can be 'IP',IG,CP,NC
            alDeleteMode.add(TTKCommon.getUserSeqId(request));//User id

            //delete the selected policies based on the flow
            if(strSwitchType.equals(strEnrollment))     //call delete policy method for the enrollment flow
            {
                iCount = policyManagerObject.deletePolicy(alDeleteMode);
            }//end of if(strSwitchType.equals(strEnrollment))
            else if(strSwitchType.equals(strEndorsement))   //call the delete endorsement for endorsement flow
            {
                iCount=policyManagerObject.deleteEndorsement(alDeleteMode);
            }//end of else if(strSwitchType.equals(strEndorsement))

            //get the table data from session if exists
            TableData policyListData=null;
            if((request.getSession()).getAttribute("policyListData") != null)
            {
                policyListData = (TableData)(request.getSession()).getAttribute("policyListData");
            }//end of if((request.getSession()).getAttribute("policyListData") != null)
            else
            {
                policyListData = new TableData();
            }//end of else


            //refresh the grid with search data in session
            ArrayList alPolicyList = null;
            //fetch the data from previous set of rowcounts, if all the records are deleted for the current set of search criteria
            if(iCount == policyListData.getData().size())
            {
                policyListData.modifySearchData(strDeleteList);//modify the search data
                int iStartRowCount = Integer.parseInt((String)policyListData.getSearchData().
                        get(policyListData.getSearchData().size()-2));
                if(iStartRowCount > 0)
                {
                    alPolicyList = policyManagerObject.getPolicyList(policyListData.getSearchData());
                }//end of if(iStartRowCount > 0)
            }//end if(iCount == policyListData.getData().size())
            else
            {
                alPolicyList = policyManagerObject.getPolicyList(policyListData.getSearchData());
            }//end of else
            policyListData.setData(alPolicyList, strDeleteList);
            if(iCount>0)
            {
                //delete the Policies from the web board if any
                request.setAttribute("cacheId",sbfDeleteId.append("|").toString());
                WebBoardHelper.deleteWebBoardId(request);
            }//end of if(iCount>0)
            request.getSession().setAttribute("policyListData",policyListData);
            return this.getForward(getForwardPath(strActiveSubLink), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"policyList"));
        }//end of catch(Exception exp)
    }//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doViewPolicyDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
        	log.debug("Inside the doViewPolicyDetails method of PolicyAction");
            DynaActionForm frmPolicyList=(DynaActionForm)form;  //get the DynaActionForm instance
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));            
        
            
            //call the setlinks method
            this.setLinks(request,strSwitchType);

            //get the table data from session if exists
            TableData policyListData=null;
            if((request.getSession()).getAttribute("policyListData") != null)
            {
                policyListData = (TableData)(request.getSession()).getAttribute("policyListData");
            }//end of if((request.getSession()).getAttribute("policyListData") != null)
            else
            {
                policyListData = new TableData();
            }//end of else

            if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
             PolicyVO policyVO=(PolicyVO)policyListData.getRowInfo(Integer.parseInt((String)
                				  (frmPolicyList).get("rownum")));
             //System.out.println("   doViewPolicyDetails : ooooooooooo "+policyVO.getPolicySeqID());
             request.getSession().setAttribute("policySeqID",policyVO.getPolicySeqID());
             request.getSession().setAttribute("capitationFlag",policyVO.getCapitationflag());
                //add the selected item to the web board and make it as default selected
                this.addToWebBoard(policyVO, request,strSwitchType);
            }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            request.getSession().setAttribute("switchType", strSwitchType);
            return mapping.findForward(strPolicyDetail);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"policyList"));
        }//end of catch(Exception exp)
    }//end of doViewPolicyDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


    /**
     * This method navigates the user to Endorsement detail screen
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewEndorsement(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
        	log.debug("Inside the doViewEndorsement method of PolicyAction");
            DynaActionForm frmPolicyList=(DynaActionForm)form;  //get the DynaActionForm instance
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            //call the setlinks method
            this.setLinks(request,strSwitchType);

            //get the table data from session if exists
            TableData policyListData=null;
            if((request.getSession()).getAttribute("policyListData") != null)
            {
                policyListData = (TableData)(request.getSession()).getAttribute("policyListData");
            }//end of if((request.getSession()).getAttribute("policyListData") != null)
            else
            {
                policyListData = new TableData();
            }//end of else

            if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
                PolicyVO policyVO=(PolicyVO)policyListData.getRowInfo(Integer.parseInt((String)
                				  (frmPolicyList).get("rownum")));
                request.getSession().setAttribute("policySeqID",policyVO.getPolicySeqID());
                request.getSession().setAttribute("capitationFlag",policyVO.getCapitationflag());
                //add the selected item to the web board and make it as default selected
                this.addToWebBoard(policyVO, request,strSwitchType);
            }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))

            return mapping.findForward(strEndorsementDetails);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"policyList"));
        }//end of catch(Exception exp)
    }//end of doViewEndorsement(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


    /**
     * This method navigates the user to Member screen
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewMembers(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
        	log.debug("Inside the doViewMembers method of PolicyAction");
            DynaActionForm frmPolicyList=(DynaActionForm)form;  //get the DynaActionForm instance
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            PolicyManager policyObject=this.getPolicyManagerObject();
            //call the setlinks method
            this.setLinks(request,strSwitchType);

            //get the table data from session if exists
            TableData policyListData=null;
            if((request.getSession()).getAttribute("policyListData") != null)
            {
                policyListData = (TableData)(request.getSession()).getAttribute("policyListData");
            }//end of if((request.getSession()).getAttribute("policyListData") != null)
            else
            {
                policyListData = new TableData();
            }//end of else

            if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
                PolicyVO policyVO=(PolicyVO)policyListData.getRowInfo(Integer.parseInt((String)
                		  		  (frmPolicyList).get("rownum")));
                request.getSession().setAttribute("policySeqID",policyVO.getPolicySeqID());
                request.getSession().setAttribute("capitationFlag",policyVO.getCapitationflag());
                request.setAttribute("policyVO",policyVO); //add the VO to request if you are navigating to members list screen
                //add the selected item to the web board and make it as default selected
                this.addToWebBoard(policyVO, request,strSwitchType);
                
                /*Required data for softcopy upload.*/
                ArrayList<Object> alPolicy = new ArrayList<Object>();
                PolicyDetailVO policyDetailVO=null;
                alPolicy.add(policyVO.getPolicySeqID());
                alPolicy.add(strSwitchType);//Enrollment or Endorsement
                alPolicy.add(getPolicyType(strActiveSubLink));
                policyDetailVO= policyObject.getPolicy(alPolicy);   
                request.getSession().setAttribute("policy_num",policyDetailVO.getPolicySeqID());
                request.getSession().setAttribute("productTyp_num",policyDetailVO.getProductSeqID());
                request.getSession().setAttribute("insComp_num",policyDetailVO.getInsuranceSeqID());
                request.getSession().setAttribute("grpId_num", policyDetailVO.getGroupRegnSeqID());
                request.getSession().setAttribute("capitation_type", policyDetailVO.getCapitationflag());
                request.getSession().setAttribute("policy_category", policyDetailVO.getPolicyCategory());
                request.getSession().setAttribute("portedYN", policyDetailVO.getPortedYN());
                request.getSession().setAttribute("productAuthority", policyDetailVO.getProductAuthority());
                request.getSession().setAttribute("officeCode", policyDetailVO.getOfficeCode());
            }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            
            return mapping.findForward(strMembersDetail);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"policyList"));
        }//end of catch(Exception exp)
    }//end of doViewMembers(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


    //New Method added by sagar for view of benefit details in Enrollment
    public ActionForward viewBenefitDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		try {
			setLinks(request);			
			log.info("Inside ClaimGeneralAction addActivityDetails ");
			HttpSession session = request.getSession();
			DynaActionForm frmMember = (DynaActionForm) session.getAttribute("frmMember");//frmEnrollBenefits
			DynaActionForm frmEnrollBenefits = (DynaActionForm) form;
			TreeData treeData=null;
			
			treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			String strSelectedRoot=request.getParameter("selectedroot");
            String strSelectedNode=request.getParameter("selectednode");
            MemberVO memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
            String enrollId="";
            Long memberId= null;
            BigDecimal SumInsured=null;
            
            if(memberVO!=null){
            	session.setAttribute("memberVO", memberVO);
            	 enrollId= (String) memberVO.getEnrollmentID();
            	 memberId=  memberVO.getMemberSeqID();
            	 SumInsured= memberVO.getPolicySumInsured();
                
            }
            else{
            	MemberVO VOobject= (MemberVO)session.getAttribute("memberVO");
            	enrollId= (String) VOobject.getEnrollmentID();
            	memberId=  VOobject.getMemberSeqID();
            	SumInsured= VOobject.getPolicySumInsured();

            }

			String benefitTypes= (String)request.getParameter("benefitType");
			String benefitTypes1= (String)request.getParameter("benefitType");
			PolicyManager policyObject=this.getPolicyManagerObject();
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
					return mapping.findForward("");
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
				tableData.createTableInfo("BenefitTable",null);//DisplayBenefitsTable  BenefitTable
				
				
				if("OPTS".equals(benefitTypes)||"OPT".equals(benefitTypes))
					benefitTypes="OUT-PATIENT";
				if("DNTL".equals(benefitTypes)||"DNT".equals(benefitTypes))
					benefitTypes="DENTAL";
				if("IMTI".equals(benefitTypes)||"OMTI".equals(benefitTypes)||"MAT".equals(benefitTypes))
					benefitTypes="MATERNITY";
				if("OPTC".equals(benefitTypes)||"OPL".equals(benefitTypes))
					benefitTypes="OPTICAL";
				if("IPT".equals(benefitTypes))
					benefitTypes="In-Patient";
				if("OTH".equals(benefitTypes))
					benefitTypes="OTHERS";
				if("ALL".equals(benefitTypes))
					benefitTypes="All";
				if("PED".equals(benefitTypes))
					benefitTypes="PED";
				if("CHRC".equals(benefitTypes))
					benefitTypes="CHRONIC CONDITION";
				if("AYR".equals(benefitTypes))
					benefitTypes="AYURVEDA/HOMEOPATHY";
				if("HEA".equals(benefitTypes))
					benefitTypes="HEALTH CHECKUP/WELLNESS";
				
				ArrayList<Object> allinfo=new ArrayList<Object>();
				allinfo.add(enrollId);
				allinfo.add(memberId);
				allinfo.add(TTKCommon.getActiveLink(request));
				allinfo.add(frmMember.getString("sPolicySeqID"));
				allinfo.add(benefitTypes);
				tableData.setSearchData(allinfo);
				tableData.modifySearchData("search");
			}//end of else
			
		ArrayList<Object> alBenefitList= policyObject.getBenefitDetails(tableData.getSearchData());
		ArrayList<Object> alDataList= policyObject.getFieldsDetails(enrollId);
		
		tableData.setData((ArrayList<Object>)alBenefitList.get(0), "search");
		//set the table data object to session
		request.getSession().setAttribute("tableData",tableData);
		if("OPTS".equals(benefitTypes)||"OPT".equals(benefitTypes))
			benefitTypes="OUT-PATIENT";
		if("DNTL".equals(benefitTypes)||"DNT".equals(benefitTypes))
			benefitTypes="DENTAL";
		if("IMTI".equals(benefitTypes)||"OMTI".equals(benefitTypes)||"MAT".equals(benefitTypes))
			benefitTypes="MATERNITY";
		if("OPTC".equals(benefitTypes)||"OPL".equals(benefitTypes))
			benefitTypes="OPTICAL";
		if("IPT".equals(benefitTypes))
			benefitTypes="In-Patient";
		if("OTH".equals(benefitTypes))
			benefitTypes="OTHERS";
		if("ALL".equals(benefitTypes))
			benefitTypes="All";
		if("PED".equals(benefitTypes))
			benefitTypes="PED";
		if("CHRC".equals(benefitTypes))
			benefitTypes="CHRONIC CONDITION";
		if("AYR".equals(benefitTypes))
			benefitTypes="AYURVEDA/HOMEOPATHY";
		if("HEA".equals(benefitTypes))
			benefitTypes="HEALTH CHECKUP/WELLNESS";
		
		frmEnrollBenefits.set("benefitType",frmEnrollBenefits.get("benefitType"));
		//request.setAttribute("benefitType",benefitTypes1);
		request.setAttribute("availableSumInsured",alBenefitList.get(1));
		request.setAttribute("utilizeSuminsured",alBenefitList.get(2));
		request.setAttribute("PolicyStartDate",alDataList.get(0));
		request.setAttribute("PolicyNo",alDataList.get(1));
		request.setAttribute("PolicyEndDate",alDataList.get(3));
		request.setAttribute("MemberInceptionDate",alDataList.get(4));
		request.setAttribute("SumInsured", alDataList.get(5));
		request.setAttribute("memberId", enrollId);
		
		//request.setAttribute("otherRemarks",alBenefitList.get(2));
	return mapping.findForward(strBenefitDetails);

		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strMembersDetail));
		}// end of catch(Exception exp)
	}
    
    
    
    
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
        	log.debug("Inside the doClose method of PolicyAction");
            DynaActionForm frmPolicyList=(DynaActionForm)form;  //get the DynaActionForm instance
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            //call the setlinks method
            this.setLinks(request,strSwitchType);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            return this.getForward(getForwardPath(strActiveSubLink), mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"policyList"));
        }//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside PolicyAction doSelectGroup");
    		DynaActionForm frmPolicyList=(DynaActionForm)form;
//    		frmPolicyAccountInfo.set("frmChanged","changed");
    		request.getSession().setAttribute("frmPolicyList",frmPolicyList);
    		return mapping.findForward(strGroupList);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request,mapping,new TTKException(exp,"policyList"));
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
    		log.debug("Inside PolicyAction doClearCorporate");
    		DynaActionForm frmPolicyList=(DynaActionForm)form;
//    		 frmPolicyList.initialize(mapping);//reset the form data
    		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
    		String strForwardPath=getForwardPath(strActiveSubLink);
    		frmPolicyList.set("sGroupId","");
    		frmPolicyList.set("sGroupName","");
    		request.getSession().setAttribute("frmPolicyList",frmPolicyList);
    		return this.getForward(strForwardPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,"policyList"));
    	}//end of catch(Exception exp)
    }//end of doClearCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
      //HttpServletResponse response)

    /**
     * This method is used to set the selected colum's visibility, to false.
     * @param tableData TableData  is the instance of created Html Grid table
     * @param strActiveSubLink String  active sublink
     * @param strSwitchType String
     */
    private void setColumnVisiblity(TableData tableData,String strActiveSubLink,String strSwitchType)
    	throws TTKException
    {
        // These arrays contains the corresponding column number's whose visibility, is required to make as false.
        int iTemp[]=null;
        int iEnrollInd[]={2,3};         //for Individual Policy,Enrollment
        int iEnrollIndGroup[]={3};      //for Ind. Policy as Group,Enrollment and Non-Corporate Policy,Enrollment
        int iEnrollCorporate[]={};     //for Corporate Policy, Enrollment
        int iEndorseInd[]={3,4};        //for Individual Policy, Endorsement
        int iEndorseIndGroup[]={4};     //for Ind. Policy as Group, Endorsement and Non-Corporate Policy, Endorsement
        int iEndorseCorporate[]={4};    //for Corporate Policy, Endorsement

        if(strSwitchType.equals(strEnrollment))
        {
            if(strActiveSubLink.equals(strIndPolicy))
            {
                iTemp=iEnrollInd;
            }//end of if(strActiveSubLink.equals(strIndPolicy))
            else if(strActiveSubLink.equals(strIndGrpPolicy) || strActiveSubLink.equals(strNonCorporatePolicy))
            {
                iTemp=iEnrollIndGroup;
            }//end of else if(strActiveSubLink.equals(strIndGrpPolicy) || strActiveSubLink.equals(strNonCorporatePolicy))
            else if(strActiveSubLink.equals(strCorporatePolicy))
            {
                iTemp=iEnrollCorporate;
            }//end of else if(strActiveSubLink.equals(strCorporatePolicy))
        }//end of if(strSwitchType.equals(strEnrollment))
        else if(strSwitchType.equals(strEndorsement))
        {
            if(strActiveSubLink.equals(strIndPolicy))
            {
                iTemp=iEndorseInd;
            }//end of if(strActiveSubLink.equals(strIndPolicy))
            else if(strActiveSubLink.equals(strIndGrpPolicy) || strActiveSubLink.equals(strNonCorporatePolicy))
            {
                iTemp=iEndorseIndGroup;
            }//end of else if(strActiveSubLink.equals(strIndGrpPolicy) || strActiveSubLink.equals(strNonCorporatePolicy))
            else if(strActiveSubLink.equals(strCorporatePolicy))
            {
                iTemp=iEndorseCorporate;
            }//end of else if(strActiveSubLink.equals(strCorporatePolicy))
        }//end of else if(strSwitchType.equals(strEndorsement))
        if(iTemp!=null && iTemp.length>0)
        {
            for(int i=0;i<iTemp.length;i++)
            {
                ((Column)((ArrayList)tableData.getTitle()).get(iTemp[i])).setVisibility(false);
            }//end of for(int i=0;i<iTemp.length;i++)
        }//end of if(iTemp!=null && iTemp.length>0)
    }//end of setColumnVisiblity(TableData tableData,String strSubLink,String strSwitch)

    /**
     * This method is used to set the selected colum's width dynamically based on the enrollmentflow or endorsement flow
     * for different Policy types.
     * @param tableData TableData  is the instance of created Html Grid table
     * @param strActiveSubLink String  active sublink
     * @param strSwitchType String
     * @throws TTKException if any exception occures
     */
    private void setColumnWidth(TableData tableData,String strActiveSubLink,String strSwitchType)
    throws TTKException
    {
        //These arrays contains the column number's and corresponding width which will be changed
        String  strTemp[]=null;
        int iColumns[]=null;
        int iEnrollcolumns[]={}; //column nos whose width to be changed
        int iEndorseColumns[]={0,2,5};
        String strEnrollIndWidth[]={"20%","24%","24%"};  //corresponding widths for Individual Policy,Enrollment
        String strEnrollOtherWidth[]={"17%","18%","20%"};   //for Ind. Policy as Group,Non-Corporate Policy, and Corporate Policy
        String strEndorseIndWidth[]={"20%","20%","18%"};
        String strEndorseOtherWidth[]={"15%","14%","12%"};

        if(strSwitchType.equals(strEnrollment))
        {
            iColumns=iEnrollcolumns;
            if(strActiveSubLink.equals(strIndPolicy))
            {
                strTemp=strEnrollIndWidth;
            }//end of if(strActiveSubLink.equals(strIndPolicy))
            else if(strActiveSubLink.equals(strIndGrpPolicy) || strActiveSubLink.equals(strNonCorporatePolicy) 
            		|| strActiveSubLink.equals(strCorporatePolicy))
            {
                strTemp=strEnrollOtherWidth;
            }//end of else if(strActiveSubLink.equals(strIndGrpPolicy) || strActiveSubLink.equals(strNonCorporatePolicy) || strActiveSubLink.equals(strCorporatePolicy))
        }//end of if(strSwitchType.equals(strEnrollment))
        else if(strSwitchType.equals(strEndorsement))
        {
            iColumns=iEndorseColumns;
            if(strActiveSubLink.equals(strIndPolicy))
            {
                strTemp=strEndorseIndWidth;
            }//end of if(strActiveSubLink.equals(strIndPolicy))
            else if(strActiveSubLink.equals(strIndGrpPolicy) || strActiveSubLink.equals(strNonCorporatePolicy) 
            		|| strActiveSubLink.equals(strCorporatePolicy))
            {
                strTemp=strEndorseOtherWidth;
            }//end of else if(strActiveSubLink.equals(strIndGrpPolicy) || strActiveSubLink.equals(strNonCorporatePolicy) || strActiveSubLink.equals(strCorporatePolicy))
        }//end of else if(strSwitchType.equals(strEndorsement))
        if(iColumns!=null && strTemp!=null && iColumns.length>0)
        {
            for(int i=0;i<iColumns.length;i++)
                ((Column)((ArrayList)tableData.getTitle()).get(iColumns[i])).setColumnWidth(strTemp[i]);
        }//end of if(iColumns!=null && strTemp!=null && iColumns.length>0)
    }//end of setColumnWidth(TableData tableData,String strActiveSubLink,String strSwitchType)

    /**
     * This method will add search criteria fields and values to the arraylist and will return it
     * @param frmPolicyList current instance of form bean
     * @param request HttpServletRequest object
     * @param strActiveSubLink current Active sublink
     * @return alSearchObjects ArrayList of search params
     * @throws TTKException
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmPolicyList,HttpServletRequest request,
    		String strActiveSubLink)throws TTKException
    {
    	log.debug("Inside the populateSearchCriteria of PolicyAction");
        // build the column names along with their values to be searched
        ArrayList<Object> alSearchObjects = new ArrayList<Object>();
        ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();

        //set the switch type mode as first parameter
        if(frmPolicyList.get("switchType").equals(strEnrollment))
        {
            alSearchObjects.add("Y");
        }//end of if(frmPolicyList.get("switchType").equals(strEnrollment))
        else
        {
            alSearchObjects.add("N");
        }//end of else

        // set the current active sublink as second parameter
        if(strActiveSubLink.equals(strIndPolicy))
        {
            alSearchObjects.add("IND");
        }//end of if(strActiveSubLink.equals(strIndPolicy))
        else if (strActiveSubLink.equals(strIndGrpPolicy))
        {
            alSearchObjects.add("ING");
        }//end of else if (strActiveSubLink.equals(strIndGrpPolicy))
        else if (strActiveSubLink.equals(strCorporatePolicy))
        {
            alSearchObjects.add("COR");
        }//end of else if (strActiveSubLink.equals(strCorporatePolicy))
        else if (strActiveSubLink.equals(strNonCorporatePolicy))
        {
            alSearchObjects.add("NCR");
        }//end of else if (strActiveSubLink.equals(strNonCorporatePolicy))

        //prepare the search BOX parameters
        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sPolicyNumber")));
        alSearchBoxParams.add((String)frmPolicyList.getString("sInsuranceCompany"));
        alSearchBoxParams.add((String)frmPolicyList.getString("sTtkBranch"));
        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sBatchNumber")));
      

        if(frmPolicyList.get("switchType").equals(strEnrollment))
        {
            alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sEnrollmentNumber")));
            alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sMemberName")));
          
          
        }//end of if(frmPolicyList.get("switchType").equals(strEnrollment))
        else if(frmPolicyList.get("switchType").equals(strEndorsement))
        {
            alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sEndorsementNumber")));
            alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sCustEndorsementNumber")));
         
        
        }//end of  else if(frmPolicyList.get("switchType").equals(strEndorsement))

        alSearchBoxParams.add((String)frmPolicyList.getString("sWorkFlow"));
        
    
        
        if(frmPolicyList.get("switchType").equals(strEnrollment)){
        	if(!(strActiveSubLink.equals(strNonCorporatePolicy)))
            {
            	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sAgentCode")));
            }//end of if(!(strActiveSubLink.equals(strNonCorporatePolicy)))
            else
            {
            	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sCustomerCode")));
            }//end of else
        }//end of if(frmPolicyList.get("switchType").equals(strEnrollment))
        else if(frmPolicyList.get("switchType").equals(strEndorsement)){
        	if(!(strActiveSubLink.equals(strNonCorporatePolicy)))
            {
            	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sAgentCode")));
            }//end of if(!(strActiveSubLink.equals(strNonCorporatePolicy)))
            else
            {
            	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sSChemeName")));
            }//end of else
        }//end of else
        
        if(frmPolicyList.get("switchType").equals(strEnrollment)){
        	if(strActiveSubLink.equals(strIndPolicy))
            {
                alSearchBoxParams.add("");
            }//end fo if(strActiveSubLink.equals(strIndPolicy))
            else if(strActiveSubLink.equals(strIndGrpPolicy))
            {
                alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sGroupId")));
            }//end of else if(strActiveSubLink.equals(strIndGrpPolicy))
            else if (strActiveSubLink.equals(strCorporatePolicy))
            {
                alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sGroupId")));
                //alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sMemberId")));			// new cr: 
            }//end of else if (strActiveSubLink.equals(strCorporatePolicy))
            else if(strActiveSubLink.equals(strNonCorporatePolicy))
            {
            	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sSChemeName")));
            }//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
        }//end of if(frmPolicyList.get("switchType").equals(strEnrollment))
        else if(frmPolicyList.get("switchType").equals(strEndorsement)){
        	if(strActiveSubLink.equals(strIndPolicy))
            {
                alSearchBoxParams.add("");
            }//end fo if(strActiveSubLink.equals(strIndPolicy))
            else if(strActiveSubLink.equals(strIndGrpPolicy))
            {
                alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sGroupId")));
            }//end of else if(strActiveSubLink.equals(strIndGrpPolicy))
            else if (strActiveSubLink.equals(strCorporatePolicy))
            {
                alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sGroupId")));
                //alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sMemberId")));			// new cr: 
            }//end of else if (strActiveSubLink.equals(strCorporatePolicy))
            else if(strActiveSubLink.equals(strNonCorporatePolicy))
            {
            	alSearchBoxParams.add("");
            }//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
        }//end of else
        
        if(frmPolicyList.get("switchType").equals(strEnrollment)){
        	if(strActiveSubLink.equals(strNonCorporatePolicy))
            {
            	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sCertificateNumber")));
            }//end of if(strActiveSubLink.equals(strNonCorporatePolicy))
            else
            {
            	alSearchBoxParams.add("");
            }//end of else
        }//end of if(frmPolicyList.get("switchType").equals(strEnrollment))
        else if(frmPolicyList.get("switchType").equals(strEndorsement)){
        	alSearchBoxParams.add("");
        }//end of else
        
        alSearchBoxParams.add(frmPolicyList.getString("sMemberId"));
        //AK
        if(frmPolicyList.get("switchType").equals(strEnrollment))
        {
        	
        	 alSearchBoxParams.add(frmPolicyList.getString("ProductName"));
             alSearchBoxParams.add(frmPolicyList.getString("productAuthority"));
          
        }//end of if(frmPolicyList.get("switchType").equals(strEnrollment))
        else if(frmPolicyList.get("switchType").equals(strEndorsement))
        {

        	 alSearchBoxParams.add(frmPolicyList.getString("ProductName"));
             alSearchBoxParams.add(frmPolicyList.getString("productAuthority"));
        
        }//end of  else if(frmPolicyList.get("switchType").equals(strEndorsement))
      /*  alSearchBoxParams.add(frmPolicyList.getString("ProductName"));
        alSearchBoxParams.add(frmPolicyList.getString("productAuthority"));*/
        
        //EAK
        //set the array of Strings as third parameter
        
        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPolicyList.getString("sEmiratesID")));
        String[] strSearchObjects = alSearchBoxParams.toArray(new String[0]);
        alSearchObjects.add(strSearchObjects);
        alSearchObjects.add(TTKCommon.getUserSeqId(request));   //current logged in user seq id
        alSearchObjects.add(TTKCommon.getUserGroupList(request));
        
        return alSearchObjects;
    }//end of populateSearchCriteria()

    /**
     * This method returns a string which contains the comma separated sequence id's to be deleted,
     * in Enrollmemt flow pipe seperated policy seq ids and in endorsement flow pipe seperated endorsement seq ids
     * are sent to the called method
     * @param request HttpServletRequest object which contains information about the selected check boxes
     * @param policyListData TableData object which contains the value objects
     * @param strSwitchType String identifier for enrollment or endorsement flow
     * @return String which contains the comma separated sequence id's to be deleted
     */
    private String populateDeleteId(HttpServletRequest request, TableData policyListData,String strSwitchType)
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
                    if(strSwitchType.equals(strEnrollment))
                    {
                        if(i == 0)
                        {
                            sbfDeleteId.append(String.valueOf(((PolicyVO)policyListData.getData().
                            		get(Integer.parseInt(strChk[i]))).getPolicySeqID()));
                        }//end of if(i == 0)
                        else
                        {
                            sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((PolicyVO)policyListData.
                            		getData().get(Integer.parseInt(strChk[i]))).getPolicySeqID()));
                        }//end of else
                    }//end of if(strSwitchType.equals(strEnrollment))
                    else if(strSwitchType.equals(strEndorsement))
                    {
                    	if(i == 0)
                    	{
                    		sbfDeleteId.append(String.valueOf(((PolicyVO)policyListData.getData().
                    				get(Integer.parseInt(strChk[i]))).getEndorsementSeqID()));
                    	}//end of if(i == 0)
                    	else
                    	{
                    		sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((PolicyVO)policyListData.
                    				getData().get(Integer.parseInt(strChk[i]))).getEndorsementSeqID()));
                    	}//end of else
                    }//end of else if(strSwitchType.equals(strEndorsement))
                }//end of if(strChk[i]!=null)
            }//end of for(int i=0; i<strChk.length;i++)
            sbfDeleteId.append("|");
        }//end of if(strChk!=null&&strChk.length!=0)
        return sbfDeleteId.toString();
    }//end of populateDeleteId(HttpServletRequest request, TableData tableData)

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
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "policyList");
        }//end of catch
        return policyManager;
    }//end getPolicyManagerObject()

    /**
     * This method populates the web board in the session with the selected items from the grid
     * @param request HttpServletRequest object which contains information about the selected check boxes
     * @param strSwitchType String  identifier of enrollment or endorsement flow
     * @throws TTKException If any run time Excepton occures
     */
    private void populateWebBoard(HttpServletRequest request,String strSwitchType)throws TTKException
    {
        String[] strChk = request.getParameterValues("chkopt");
        TableData policyListData = (TableData)request.getSession().getAttribute("policyListData");
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        ArrayList<Object> alCacheObject = new ArrayList<Object>();
        CacheObject cacheObject = null;
        PolicyVO policyVO=null;

        if(strChk!=null&&strChk.length!=0)
        {
            for(int i=0; i<strChk.length;i++)
            {
                cacheObject = new CacheObject();
                policyVO=(PolicyVO)policyListData.getData().get(Integer.parseInt(strChk[i]));
                cacheObject.setCacheId(this.prepareWebBoardId(policyVO,strSwitchType));
                if(strSwitchType.equals(strEnrollment))
                {
                    cacheObject.setCacheDesc(policyVO.getPolicyNbr());
                }//end of if(strSwitchType.equals(strEnrollment))
                else if(strSwitchType.equals(strEndorsement))
                {
                    cacheObject.setCacheDesc(policyVO.getEndorsementNbr());
                }//end of else if(strSwitchType.equals(strEndorsement))
                alCacheObject.add(cacheObject);
            }//end of for(int i=0; i<strChk.length;i++)
        }//end of if(strChk!=null&&strChk.length!=0)
        if(toolbar != null)
            toolbar.getWebBoard().addToWebBoardList(alCacheObject);
    }//end of populateWebBoard(HttpServletRequest request,String strSwitchType)

    /**
     * This method addes the selected record to web board and makes it as the selected item in the web board
     *
     * @param  policyVO InsuranceVO object which contains the information of the Insurance Office
     * @param request HttpServletRequest
     * @param strSwitchType String  identifier of enrollment or endorsement flow
     * @throws TTKException if any runtime exception occures
     */
    private void addToWebBoard(PolicyVO policyVO, HttpServletRequest request,String strSwitchType)throws 
    	TTKException
    {
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        ArrayList<Object> alCacheObject = new ArrayList<Object>();
        CacheObject cacheObject = new CacheObject();
        cacheObject.setCacheId(this.prepareWebBoardId(policyVO,strSwitchType)); //set the cacheID
        if(strSwitchType.equals(strEnrollment))
        {
            cacheObject.setCacheDesc(policyVO.getPolicyNbr());
        }//end of if(strSwitchType.equals(strEnrollment))
        else if(strSwitchType.equals(strEndorsement))
        {
            cacheObject.setCacheDesc(policyVO.getEndorsementNbr());
        }//end of else if(strSwitchType.equals(strEndorsement))

        alCacheObject.add(cacheObject);
        //if the object(s) are added to the web board, set the current web board id
        toolbar.getWebBoard().addToWebBoardList(alCacheObject);
        toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
        //webboardinvoked attribute will be set as true in request scope
        //to avoid the replacement of web board id with old value if it is called twice in same request scope
        request.setAttribute("webboardinvoked", "true");
    }//end of addToWebBoard(PolicyVO policyVO, HttpServletRequest request,String strSwitchType)

    /**
     * This method prepares the Weboard id for the selected Policy
     * @param policyVO  PolicyVO for which webboard id to be prepared
     * @param strSwitchType identifier for enrollment or endorsement flow
     * @return Web board id for the passedVO
     */
    private String prepareWebBoardId(PolicyVO policyVO,String strSwitchType)throws TTKException
    {
        StringBuffer sbfCacheId=new StringBuffer();

        //set Policy Seq Id,policy No and endorsement Seq Ids will be concatanated
        //and set as the valueof Cache id
        if(strSwitchType.equals(strEnrollment))
        {
            sbfCacheId.append(policyVO.getPolicySeqID()!=null? String.valueOf(policyVO.getPolicySeqID()):" ");  //if policy seq id is not there then empty String with space is used as identifier
            //if Endorsement Seq id is not there then empty String with space is used as identifier
            sbfCacheId.append("~#~").append(policyVO.getEndorsementSeqID()!=null? String.valueOf(policyVO.
            		getEndorsementSeqID()):" "); 
        }//end of if(strSwitchType.equals(strEnrollment))
        else
        {
        	//if Endorsement Seq id is not there then empty String with space is used as identifier
            sbfCacheId.append(policyVO.getEndorsementSeqID()!=null? 
            		String.valueOf(policyVO.getEndorsementSeqID()):" ");
            sbfCacheId.append("~#~").append(policyVO.getPolicySeqID()!=null? String.valueOf(policyVO.
            		getPolicySeqID()):" ");  //if policy seq id is not there then empty String with space is used as identifier
        }//end of else

        sbfCacheId.append("~#~").append(TTKCommon.checkNull(String.valueOf(policyVO.getPolicyNbr())));
        sbfCacheId.append("~#~").append(TTKCommon.checkNull(policyVO.getPolicyYN()).equals("")?"N":
        	policyVO.getPolicyYN());//identifier to check for valid Policy
        sbfCacheId.append("~#~").append(TTKCommon.checkNull(policyVO.getGroupID()).equals("")? " ":
        	policyVO.getGroupID());//if Group Id is not there then empty String with space is used as identifier
        sbfCacheId.append("~#~").append(TTKCommon.checkNull(policyVO.getGroupName()).equals("")? " ":
        	policyVO.getGroupName());//if Group Name is not there then empty String with space is used as identifier
        sbfCacheId.append("~#~").append(TTKCommon.checkNull(policyVO.getCompletedYN()).equals("")? " ":
        	policyVO.getCompletedYN());//if Completed_yn is not there then empty String with space is used as identifier
        return sbfCacheId.toString();
    }//end of prepareWebBoardId(PolicyVO policyVO,String strSwitchType)

    /**
     * This method returns the forward path of next view based on the Flow
     *
     * @param strActiveSubLink String current sublink
     * @return strForwardPath String forward path for the next view
     */
    private String getForwardPath(String strActiveSubLink)
    {
        String strForwardPath=null;

        if(strActiveSubLink.equals(strIndPolicy))
        {
            strForwardPath=strIndPolicyList;
        }//end of if(strActiveSubLink.equals(strIndPolicy))
        else if (strActiveSubLink.equals(strIndGrpPolicy))
        {
        	log.info("insdie get forward path");
            strForwardPath=strGrpPolicyList;
        }//end of else if (strActiveSubLink.equals(strIndGrpPolicy))
        else if (strActiveSubLink.equals(strCorporatePolicy))
        {
            strForwardPath=strCorpPolicyList;
        }//end of else if (strActiveSubLink.equals(strCorporatePolicy))
        else if (strActiveSubLink.equals(strNonCorporatePolicy))
        {
            strForwardPath=strNonCorpPolicyList;
        }//end of else if (strActiveSubLink.equals(strNonCorporatePolicy))
        return strForwardPath;
    }//end of getForwardPath(String strActiveSubLink)


    /**
     * This method returns the identifier of the policy type based on the flow
     *
     * @param strActiveSubLink current sublink
     * @return strPolicyType String Policy type Identfier
     */
    private String getPolicyType(String strActiveSubLink)
    {
        String strPolicyType=null;

        if(strActiveSubLink.equals(strIndPolicy))
        {
            strPolicyType="IP";
        }//end of if(strActiveSubLink.equals(strIndPolicy))
        else if (strActiveSubLink.equals(strIndGrpPolicy))
        {
            strPolicyType="IG";
        }//end of else if (strActiveSubLink.equals(strIndGrpPolicy))
        else if (strActiveSubLink.equals(strCorporatePolicy))
        {
            strPolicyType="CP";
        }//end of else if (strActiveSubLink.equals(strCorporatePolicy))
        else if (strActiveSubLink.equals(strNonCorporatePolicy))
        {
            strPolicyType="NC";
        }//end of else if (strActiveSubLink.equals(strNonCorporatePolicy))
        return strPolicyType;
    }//end of getPolicyType(String strActiveSubLink)


    /**
     * This method is prepares the Caption based on the flow and retunrs it
     *
     * @param strSwitchType String Identfier for Enrollment/Endorsement flow
     * @param strActiveSubLink String current sublink
     * @return String prepared Caption
     */
    private String buildCaption(String strSwitchType, String strActiveSubLink)
    {
        StringBuffer sbfCaption=new StringBuffer();
        if(strSwitchType.equals(strEnrollment))
        {
            if(strActiveSubLink.equals(strIndPolicy))
            {
                sbfCaption.append("List of Individual Policies");
            }//end of if(strActiveSubLink.equals(strIndPolicy))
            else if (strActiveSubLink.equals(strIndGrpPolicy))
            {
                sbfCaption.append("List of Individual Policies as Group");
            }//end of else if (strActiveSubLink.equals(strIndGrpPolicy))
            else if (strActiveSubLink.equals(strCorporatePolicy))
            {
                sbfCaption.append("List of Corporate Policies");
            }//end of else if (strActiveSubLink.equals(strCorporatePolicy))
            else if (strActiveSubLink.equals(strNonCorporatePolicy))
            {
                sbfCaption.append("List of Non-Corporate Policies");
            }//end of else if (strActiveSubLink.equals(strNonCorporatePolicy))
        }//end of if(strSwitchType.equals(strEnrollment))
        else if(strSwitchType.equals(strEndorsement))
        {
            if(strActiveSubLink.equals(strIndPolicy))
            {
                sbfCaption.append("List of Endorsements");
            }//end of if(strActiveSubLink.equals(strIndPolicy))
            else if (strActiveSubLink.equals(strIndGrpPolicy))
            {
                sbfCaption.append("List of Endorsements");
            }//end of else if (strActiveSubLink.equals(strIndGrpPolicy))
            else if (strActiveSubLink.equals(strCorporatePolicy))
            {
                sbfCaption.append("List of Endorsements");
            }//end of else if (strActiveSubLink.equals(strCorporatePolicy))
            else if (strActiveSubLink.equals(strNonCorporatePolicy))
            {
                sbfCaption.append("List of Endorsements");
            }//end of else if (strActiveSubLink.equals(strNonCorporatePolicy))
        }//end of else if(strSwitchType.equals(strEndorsement))
        return sbfCaption.toString();
    }//end of  buildCaption(String strSwitchType, String strActiveSubLink)

    public ActionForward doCloseBenefits(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside PolicyAction doCloseBenefits");
			String path = strMembersDetail;
			DynaActionForm frmMember = (DynaActionForm) request
					.getSession().getAttribute("frmMember");
			/*String preAuthSeqID = frmMember.getString("preAuthSeqID");
			String preAuthNo = frmMember.getString("preAuthNo");
			request.setAttribute("preAuthNo", preAuthNo);
			request.setAttribute("preAuthSeqID", preAuthSeqID);*/
			return mapping.findForward(path);
		}// end of try
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, "enrollmentmembers"));
		}// end of catch(Exception exp)

	}
    
}//end of PolicyAction.java
