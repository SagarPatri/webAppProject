/**

 * @ (#) MemberAction.java Feb 2nd, 2006

 * Project 		: TTK HealthCare Services

 * File 		: MemberAction.java

 * Author 		: Krishna K H

 * Company 		: Span Systems Corporation

 * Date Created : Feb 2nd, 2006

 *

 * @author 		: Krishna K H

 * Modified by 	: Arun K N

 * Modified date: May 11, 2007

 * Reason 		: Conversion to Dispatch Action

 */



package com.ttk.action.enrollment;



import java.io.PrintWriter;

import java.sql.Connection;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.ArrayList;

import java.util.Date;

import java.util.HashMap;
import java.util.List;

import java.util.List;



import javax.naming.InitialContext;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;







import javax.servlet.http.HttpSession;



import org.apache.log4j.Logger;

import org.apache.struts.action.ActionForm;

import org.apache.struts.action.ActionForward;

import org.apache.struts.action.ActionMapping;

import org.apache.struts.action.DynaActionForm;

import org.dom4j.Document;



import com.ttk.action.TTKAction;

import com.ttk.action.table.TableData;

import com.ttk.action.tree.Node;

import com.ttk.action.tree.TreeData;

import com.ttk.business.empanelment.HospitalManager;

import com.ttk.business.enrollment.MemberManager;

import com.ttk.business.enrollment.PolicyManager;

import com.ttk.business.webservice.ValidationRuleManager;

import com.ttk.common.TTKCommon;

import com.ttk.common.WebBoardHelper;

import com.ttk.common.exception.TTKException;

import com.ttk.common.security.RuleXMLHelper;

import com.ttk.dao.ResourceManager;

import com.ttk.dto.common.Toolbar;

import com.ttk.dto.enrollment.MemberAddressVO;

import com.ttk.dto.enrollment.MemberDetailVO;

import com.ttk.dto.enrollment.MemberVO;

import com.ttk.dto.enrollment.PolicyDetailVO;

import com.ttk.dto.enrollment.PolicyVO;



import formdef.plugin.util.FormUtils;



/**

 * This class is used to search for members,add/edit member details,approve for card printing,

 *  validate the defined rules for each family in enrollment and endorsement flow.

 * This class also provides option for deleting the enrollment.

 */





public class MemberAction extends TTKAction

{

    private static final String strDelete = "Delete";

    private static final String strBackward="Backward";

    private static final String strForward="Forward";

    private static final String strIndMemberDetails="indmemberdetails";

    private static final String strGrpPolicyDetails="grpmemberdetails";

    private static final String strIndAddMemberDetails="indaddnewmember";

    private static final String strCorAddMemberDetails="coraddnewmember";

    private static final String strNonCorAddMemberDetails="noncoraddnewmember";

    private static final String strGrpAddMemberDetails="grpaddnewmember";

    private static final String strCorpMemberDetails="cormemberdetails";

    private static final String strCorpRenewMemberDetails="cormemberrenewdetails";

    private static final String strNonCorpMemberDetails="noncorpmemberdetails";



    // For sub link name

    private static final String strIndividualPolicy="Individual Policy";

    private static final String strIndPolicyasGroup="Ind. Policy as Group";

    private static final String strCorporatePolicy="Corporate Policy";

    private static final String strNonCorporatePolicy="Non-Corporate Policy";



    private static final String strEnrollment="ENM";

    private static final String strEndorsement="END";

    //declaration of other constants used in this class



    //declaration of constants specifying the position of icons to be disabled based on condtions and permissions



    private static final int RENEW_ICON=0;

    private static final int ROOT_CARD_ICON=3;

    private static final int ADD_ICON=5;

    private static final int R00T_CANCEL_ICON=6;

    private static final int ROOT_DELETE_ICON=7;

    /*private static final int ROOT_PASS_ICON=8;

    private static final int R00T_UNCHECK_ICON=9;

    private static final int R00T_FAIL_ICON=10;*/

    //private static final int R00T_CHANGEPWD_ICON=11;



    private static final int CHILD_CANCEL_ICON=4;

    private static final int CHILD_DELETE_ICON=5;

    private static final int ROOT_RULE_ICON=2;

    private static final int MEMBER_RULE_ICON=1;

    private static final int CHILD_CARD_ICON=2;

    private static Logger log = Logger.getLogger( MemberAction.class );

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

    	

        try

        {

            log.debug("Inside doDefault method of MemberAction");

            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");

            DynaActionForm frmPolicyDetails=(DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");

            

            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            this.setLinks(request,strSwitchType);

            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            String strForwardPath=getForwardPath(strActiveSubLink);

            String strTreeName = getTreeName(strActiveSubLink);

            String strSubLinkCode=getSubLinkCode(strActiveSubLink);

            DynaActionForm frmMember=(DynaActionForm)form;

            frmMember.set("display",null);//this attribute is used in JSP to show the error message.

            if(WebBoardHelper.checkWebBoardId(request)==null)

            {

                TTKException expTTK = new TTKException();

                frmMember.set("display","display");//this attribute is used in JSP to show the error message.

                request.getSession().setAttribute("treeData",null);

                if(strSwitchType.equals(strEnrollment))

                {

                    expTTK.setMessage("error.enrollment.required");

                }//end of if(strSwitchType.equals("ENM"))

                else

                {

                    expTTK.setMessage("error.endorsement.required");

                }//end of else

                throw expTTK;

            }//end of if(WebBoardHelper.checkWebBoardId(request)==null)



            if(strSwitchType.equals(strEndorsement))

            {

                if(WebBoardHelper.getPolicySeqId(request)==null)

                {

                    TTKException expTTK = new TTKException();

                    frmMember.set("display","display");//this attribute is used in JSP to show the error message.

                    expTTK.setMessage("error.enrollment.policyrequired");

                    throw expTTK;

                }//end of if(WebBoardHelper.getPolicySeqId(request)==null)

                if(!WebBoardHelper.getPolicyYN(request).equals("Y"))

                {

                    frmMember.set("display","display");//this attribute is used in JSP to show the error message.

                    TTKException expTTK = new TTKException();

                    if(WebBoardHelper.getPolicyYN(request).equals("N"))

                    {

                        expTTK.setMessage("error.endorsement.policydetails.nopolicy");

                    }//end of if(WebBoardHelper.getPolicyYN(request).equals("N"))

                    if(WebBoardHelper.getPolicyYN(request).equals("O"))

                    {

                    	expTTK.setMessage("error.endorsement.policydetails.anotherworflow" );

                    }//end of if(WebBoardHelper.getPolicyYN(request).equals("O"))

                    throw expTTK;

                }//end of if(!WebBoardHelper.getPolicyYN(request).equals("Y"))

            }//end of if(strSwitchType.equals("END"))



            MemberManager memberManager=this.getMemberManager();

            TreeData treeData = TTKCommon.getTreeData(request);

            ArrayList<Object> alSearchParam = new ArrayList<Object>();

            MemberVO memberVO = null;



            if(strSwitchType.equals(strEnrollment))

            {

                alSearchParam.add(WebBoardHelper.getPolicySeqId(request));
                Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);
                if(strActiveSubLink.trim().equals("Corporate Policy"))
                {
                	 String policy_status ="";
                     policy_status =    memberManager.getPolicyStatus(pol_seq_id);
                     strForwardPath=getForwardPath(strActiveSubLink,policy_status);
                     request.getSession().setAttribute("policy_status", policy_status);     
                }
            }//end of if(strSwitchType.equals(strEnrollment))

            else

            {

                alSearchParam.add(WebBoardHelper.getEndorsementSeqId(request));

            }//end of else

            alSearchParam.add(strSwitchType);

            alSearchParam.add(strSubLinkCode);



            if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))

            {

                ((DynaActionForm)form).initialize(mapping);//reset the form data

            }//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))



            String strCaption="["+TTKCommon.checkNull(WebBoardHelper.getPolicyNumber(request))+"]";

            treeData = new TreeData();

            //create the required tree

            treeData.createTreeInfo(strTreeName,null,true);



            //check the permision and set the tree for not to display respective icon

            this.checkTreePermission(request,treeData,strSwitchType);

            request.getSession().setAttribute("treeData",null);

            frmMember.set("caption",strCaption);

            treeData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,

                    request,strSwitchType,strSubLinkCode));

            frmMember.set("display","display");

            //get the meberlist from database

            

       //     treeData.setSearchData(null);

            treeData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,

                    strSwitchType,strSubLinkCode));//Rishi

            treeData.modifySearchData("search");

            //get the meberlist from database

            ArrayList alMembers = memberManager.getMemberList(treeData.getSearchData());
		System.out.println("Members :::::"+alMembers);
		System.out.println("Members size:::::"+alMembers.size());
            frmMember.set("display",null);//this attribute is used in JSP to show the error message.

            // get the data from database for Individual Policy and Ind. Policy as Group tab

            // For Corporate Policy and Non-Corporate Policy tab on search load the member list

            // If user click on Enrollment No. then policyVO will be in request scope then display that Enrollment detail

           if(strActiveSubLink.equals(strIndividualPolicy) || strActiveSubLink.equals(strIndPolicyasGroup)  ||  strActiveSubLink.equals(strCorporatePolicy)

                    ||request.getAttribute("policyVO")!=null)

            {

             //treeData.setRootData(alMembers);

             treeData.setData(alMembers, "search");//Rishi

            }//end of if(strActiveSubLink.equals(strIndividualPolicy) || strActiveSubLink.equals(strIndPolicyasGroup) ||request.getAttribute("policyVO")!=null)



            //set the tree data object to session

            request.getSession().setAttribute("treeData",treeData);

            frmMember.set("caption",strCaption);

            if(alMembers!=null && alMembers.size()>0)

            {

                memberVO = (MemberVO)alMembers.get(0);

            }//if(alMembers!=null && alMembers.size()>0)

            

            this.checkRuleValid(treeData,strActiveSubLink);

            this.documentViewer(request, memberVO);

         

             request.getSession().setAttribute("memberVO", memberVO);

            

             

             if(strSwitchType.equals(strEnrollment))

             {

            	 if(strActiveSubLink.trim().equals("Corporate Policy"))

            	 {

            		 String policy_status ="";



            		 Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);

            		 

            		 policy_status =    memberManager.getPolicyStatus(pol_seq_id);

                     request.getSession().setAttribute("policy_status", policy_status);   

                     

            		 strForwardPath=getForwardPath(strActiveSubLink,policy_status);

            	 }

            	 else

            	 {

            		 

            		 strForwardPath=getForwardPath(strActiveSubLink);

            	 }

            	 

             }

            

             



        	 

             ArrayList<Object> alPolicy = new ArrayList<Object>(); 

             

             if(strSwitchType.equals(strEnrollment))

             {

            	 alPolicy.add(WebBoardHelper.getPolicySeqId(request));

             }//end of if(strSwitchType.equals(strEnrollment))

             else

             {

            	 alPolicy.add(WebBoardHelper.getEndorsementSeqId(request));

             }//end of else

        	

             /*Required data for softcopy upload.*/

             PolicyManager policyObject=this.getPolicyManagerObject();

           

             PolicyDetailVO policyDetailVO=null;

             alPolicy.add(strSwitchType);//Enrollment or Endorsement

             alPolicy.add(strActiveSubLink);

             

            

            

             policyDetailVO= policyObject.getPolicy(alPolicy);   

             request.getSession().setAttribute("policy_num",policyDetailVO.getPolicySeqID());

             request.getSession().setAttribute("productTyp_num",policyDetailVO.getProductSeqID());

             request.getSession().setAttribute("insComp_num",policyDetailVO.getInsuranceSeqID());

             request.getSession().setAttribute("grpId_num", policyDetailVO.getGroupRegnSeqID());

             request.getSession().setAttribute("capitation_type", policyDetailVO.getCapitationflag());

             request.getSession().setAttribute("policy_category", policyDetailVO.getPolicyCategory());
             request.getSession().setAttribute("productAuthority", policyDetailVO.getProductAuthority());
             request.getSession().setAttribute("officeCode", policyDetailVO.getOfficeCode());
    		 

        	 

             request.getSession().setAttribute("frmPolicyDetails", frmPolicyDetails);

             

          return this.getForward(strForwardPath, mapping, request);

        }//end of try

        catch(TTKException expTTK)

        {

            return this.processExceptions(request, mapping, expTTK);

        }//end of catch(TTKException expTTK)

        catch(Exception exp)

        {

            return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

        }//end of catch(Exception exp)

    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



    private String getForwardPath(String strActiveSubLink, String policy_status) {

		// TODO Auto-generated method stub

    	String strForwardPath=null;
    	
    	if(policy_status != null)
        {
    	
    	if(strActiveSubLink.equals(strCorporatePolicy)  &&  policy_status.trim().equals("FTS"))

        {

            strForwardPath=strCorpMemberDetails;



        } 

    	//end of if(strActiveSubLink.equals(strCorporatePolicy))

    	else if(strActiveSubLink.equals(strCorporatePolicy)  &&  policy_status.trim().equals("RTS"))

        {

    		

            strForwardPath=strCorpRenewMemberDetails;

           

        }

  

    	
        }
    	
		return strForwardPath;

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

    	log.info("Inside doChangeWebBoard method of MemberAction");

    	

        return doDefault(mapping,form,request,response);

    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)





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

        	log.debug("Inside doSearch method of MemberAction");

            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");

            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            this.setLinks(request,strSwitchType);

            TreeData treeData = TTKCommon.getTreeData(request);



            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            String strForwardPath=getForwardPath(strActiveSubLink);

            String strTreeName = getTreeName(strActiveSubLink);



            String strSubLinkCode=getSubLinkCode(strActiveSubLink);

            DynaActionForm frmMember=(DynaActionForm)form;

            frmMember.set("display",null);//this attribute is used in JSP to show the error message.



            MemberManager memberManager=this.getMemberManager();



            String strCaption="["+TTKCommon.checkNull(WebBoardHelper.getPolicyNumber(request))+"]";

             Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);

            String policy_status ="";

            //if the page number is clicked, display the appropriate page

            if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))

            {

                treeData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));

                treeData.setSelectedRoot(-1);

                if(strSwitchType.equals(strEnrollment))

                {

                 	 if(strActiveSubLink.trim().equals("Corporate Policy"))

                 	 {

                         pol_seq_id = WebBoardHelper.getPolicySeqId(request);

                 		 policy_status =    memberManager.getPolicyStatus(pol_seq_id);

                 		 request.getSession().setAttribute("policy_status", policy_status);     

                 		 strForwardPath=getForwardPath(strActiveSubLink,policy_status);

                 	 }

                 	 else

                 	 {

                 		 strForwardPath=getForwardPath(strActiveSubLink);

                 	 }

                  }

                return this.getForward(strForwardPath, mapping, request);

            }//end of if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))



            //create the required tree

            treeData = new TreeData();

            treeData.createTreeInfo(strTreeName,null,true);

            //check the permision and set the tree for not to display respective icon

            this.checkTreePermission(request,treeData,strSwitchType);

            treeData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,

                    strSwitchType,strSubLinkCode));

            treeData.modifySearchData("search");

            //get the meberlist from database

            ArrayList alMembers = memberManager.getMemberList(treeData.getSearchData());

            MemberVO memberVO = null;

            treeData.setData(alMembers, "search");

            //set the tree data object to session

            request.getSession().setAttribute("treeData",treeData);

            frmMember.set("caption",strCaption);

            if(alMembers!=null && alMembers.size()>0)

            {

                memberVO = (MemberVO)alMembers.get(0);

            }//if(alMembers!=null && alMembers.size()>0)

            //Display the appropreate Validate result Icon in tree

            this.checkRuleValid(treeData,strActiveSubLink);

            this.documentViewer(request,memberVO);

            if(strSwitchType.equals(strEnrollment))

            {

             	 if(strActiveSubLink.trim().equals("Corporate Policy"))

             	 {

                     pol_seq_id = WebBoardHelper.getPolicySeqId(request);

             		 policy_status =    memberManager.getPolicyStatus(pol_seq_id);

             		 request.getSession().setAttribute("policy_status", policy_status);     

             		 strForwardPath=getForwardPath(strActiveSubLink,policy_status);

             	 }

             	 else

             	 {

             		 strForwardPath=getForwardPath(strActiveSubLink);

             	 }

              }

            return this.getForward(strForwardPath, mapping, request);

        }//end of try

        catch(TTKException expTTK)

        {

            return this.processExceptions(request, mapping, expTTK);

        }//end of catch(TTKException expTTK)

        catch(Exception exp)

        {

            return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

        }//end of catch(Exception exp)

    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)





    /**

     * This method is used to show/Hide the dependent member list of the family

     * Finally it forwards to the appropriate view based on the specified forward mappings

     *

     * @param mapping The ActionMapping used to select this instance

     * @param form The optional ActionForm bean for this request (if any)

     * @param request The HTTP request we are processing

     * @param response The HTTP response we are creating

     * @return ActionForward Where the control will be forwarded, after this request is processed

     * @throws Exception if any error occurs

     */

    public ActionForward doShowHide(ActionMapping mapping,ActionForm form,HttpServletRequest request,

            HttpServletResponse response) throws Exception{

        try{

        	 String strForwardPath = null;

        	log.info("Inside doShowHide method of MemberAction");

            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");

            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            this.setLinks(request,strSwitchType);

            TreeData treeData = TTKCommon.getTreeData(request);

            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            String strSubLinkCode=getSubLinkCode(strActiveSubLink);

            DynaActionForm frmMember=(DynaActionForm)form;

            frmMember.set("display",null);//this attribute is used in JSP to show the error message.



            MemberManager memberManager=this.getMemberManager();

     	    String policy_status ="";

            Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);

            policy_status =    memberManager.getPolicyStatus(pol_seq_id);

            strForwardPath=getForwardPath(strActiveSubLink,policy_status);

            request.getSession().setAttribute("policy_status", policy_status);

            MemberVO memberVO = null;

            String strCaption="["+TTKCommon.checkNull(WebBoardHelper.getPolicyNumber(request))+"]";

            int iSelectedRoot = Integer.parseInt((String)frmMember.get("selectedroot"));

            //create the required tree

            ArrayList alNodeMembers = new ArrayList();

            ArrayList<Object> alSearchParam = new ArrayList<Object>();

            memberVO = ((MemberVO)treeData.getRootData().get(iSelectedRoot));



            //create search parameter to get the dependent list

            alSearchParam.add(memberVO.getPolicyGroupSeqID());

            alSearchParam.add(strSwitchType);

            alSearchParam.add(strSubLinkCode);



            //get the dependent list from database

            alNodeMembers=memberManager.getDependentList(alSearchParam);

          

            if(strActiveSubLink.trim().equals("Corporate Policy"))

            {

                 strForwardPath=getForwardPath(strActiveSubLink,policy_status);

            }

            else

            {

             strForwardPath=getForwardPath(strActiveSubLink);

            }

            this.checkCancelMember(treeData,alNodeMembers,request,strSwitchType);

            treeData.setSelectedRoot(iSelectedRoot);

            ((MemberVO)treeData.getRootData().get(iSelectedRoot)).setMemberList(alNodeMembers);

            treeData.setSelectedRoot(iSelectedRoot);

            request.getSession().setAttribute("treeData",treeData);

            frmMember.set("caption",strCaption);

            return this.getForward(strForwardPath, mapping, request);

        }//end of try

        catch(TTKException expTTK)

        {

            return this.processExceptions(request, mapping, expTTK);

        }//end of catch(TTKException expTTK)

        catch(Exception exp)

        {

            return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

        }//end of catch(Exception exp)

    }//end of doShowHide(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)





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

        	log.info("Inside doBackward method of MemberAction");

            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");

            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            this.setLinks(request,strSwitchType);



            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            String strForwardPath=getForwardPath(strActiveSubLink);

          

            String strSubLinkCode=getSubLinkCode(strActiveSubLink);

            TreeData treeData = TTKCommon.getTreeData(request);

            

            MemberManager memberManager=this.getMemberManager();

            Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);

            MemberVO memberVO = null;

            treeData.modifySearchData(strBackward);//modify the search data

            //get the meberlist from database

            ArrayList alMembers = memberManager.getMemberList(treeData.getSearchData());

            treeData.setData(alMembers, strBackward);//set the table data

            request.getSession().setAttribute("treeData",treeData);//set the table data object to session

             this.documentViewer(request, memberVO);

            String policy_status="";

            if(strSwitchType.equals(strEnrollment))

            {

              	 if(strActiveSubLink.trim().equals("Corporate Policy"))

              	 {

                     pol_seq_id = WebBoardHelper.getPolicySeqId(request);

              		 policy_status =    memberManager.getPolicyStatus(pol_seq_id);

              		 request.getSession().setAttribute("policy_status", policy_status);     

              		 strForwardPath=getForwardPath(strActiveSubLink,policy_status);

              	 }

              	 else

              	 {

              		 strForwardPath=getForwardPath(strActiveSubLink);

              	 }

               }

             

           return this.getForward(strForwardPath, mapping, request);

        }//end of try

        catch(TTKException expTTK)

        {

            return this.processExceptions(request, mapping, expTTK);

        }//end of catch(TTKException expTTK)

        catch(Exception exp)

        {

            return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

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

        	log.debug("Inside doForward method of MemberAction");

        	 String strForwardPath = null;

        	DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");

            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            String strSubLinkCode=getSubLinkCode(strActiveSubLink);

            this.setLinks(request,strSwitchType);

            TreeData treeData = TTKCommon.getTreeData(request);

           

            MemberManager memberManager=this.getMemberManager();

            Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);

            MemberVO memberVO = null;

            treeData.modifySearchData(strForward);//modify the search data

            //get the meberlist from database

            ArrayList alMembers = memberManager.getMemberList(treeData.getSearchData());

            treeData.setData(alMembers, strForward);//set the table data

            request.getSession().setAttribute("treeData",treeData);//set the table data object to session

            this.documentViewer(request, memberVO);

            String policy_status="";

            if(strSwitchType.equals(strEnrollment))

            {

           	 if(strActiveSubLink.trim().equals("Corporate Policy"))

           	 {

                 pol_seq_id = WebBoardHelper.getPolicySeqId(request);

           		 policy_status =    memberManager.getPolicyStatus(pol_seq_id);

           		 request.getSession().setAttribute("policy_status", policy_status);     

           		 strForwardPath=getForwardPath(strActiveSubLink,policy_status);

           	 }

           	 else

           	 {

           		 strForwardPath=getForwardPath(strActiveSubLink);

           	 }

            }

            return this.getForward(strForwardPath, mapping, request);

        }//end of try

        catch(TTKException expTTK)

        {

            return this.processExceptions(request, mapping, expTTK);

        }//end of catch(TTKException expTTK)

        catch(Exception exp)

        {

            return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

        }//end of catch(Exception exp)

    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)





    /**

     * This method is used to Approve the Card printing for the family or selected members

     * Finally it forwards to the appropriate view based on the specified forward mappings

     *

     * @param mapping The ActionMapping used to select this instance

     * @param form The optional ActionForm bean for this request (if any)

     * @param request The HTTP request we are processing

     * @param response The HTTP response we are creating

     * @return ActionForward Where the control will be forwarded, after this request is processed

     * @throws Exception if any error occurs

     */

    public ActionForward doApproveCard(ActionMapping mapping,ActionForm form,HttpServletRequest request,

            HttpServletResponse response) throws Exception{

        try{

        	log.info("Inside doApproveCard method of MemberAction");

            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");

            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            this.setLinks(request,strSwitchType);



            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            String strForwardPath=getForwardPath(strActiveSubLink);

            TreeData treeData = TTKCommon.getTreeData(request);

            Long lngSeqID = null;

            DynaActionForm generalForm = (DynaActionForm)form;



            MemberVO memberVO= new MemberVO();

            MemberManager memberManager=this.getMemberManager();

            ArrayList<Object> alSearchParam = new ArrayList<Object>();



            int iResult=0;

            String strSelectedRoot = (String)generalForm.get("selectedroot");

            String strSelectedNode = (String)generalForm.get("selectednode");

            treeData.setSelectedRoot(-1);   //sets the selected rows

            memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode); // from tree with with min data

            if(strActiveSubLink.trim().equals("Corporate Policy"))

            {

            	 String policy_status ="";

            	 Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);

                 policy_status =    memberManager.getPolicyStatus(pol_seq_id);

                 strForwardPath=getForwardPath(strActiveSubLink,policy_status);

                 request.getSession().setAttribute("policy_status", policy_status);

            }

            String strFlag="";

            //if selected root is null then the button is clicked to approve all for card printing.

            if(strSelectedRoot==null ||strSelectedRoot.equals(""))

            {

                strFlag="POLICY";

                lngSeqID=WebBoardHelper.getPolicySeqId(request);

            }//end of if(strSelectedRoot==null ||strSelectedRoot.equals(""))

            else if(strSelectedNode==null ||strSelectedNode.equals("")) // if selected node is null then root element is selected else node is selected

            {

                strFlag="GROUP";

                lngSeqID = memberVO.getPolicyGroupSeqID();

            }//end of (strSelectedNode==null || strSelectedNode.equals("")

            else

            {

                strFlag = "MEMBER";

                lngSeqID = memberVO.getMemberSeqID();

            }//end of else

            alSearchParam.add(getPolicyTypeCode(strActiveSubLink));

            alSearchParam.add(lngSeqID);

            alSearchParam.add(strFlag);

            alSearchParam.add(TTKCommon.getUserSeqId(request));

            iResult= memberManager.approveCardPrinting(alSearchParam);

            if(iResult > 0)

            {

                request.setAttribute("updated","message.approveCard");

            }//end of if(iResult == 0)



            return this.getForward(strForwardPath, mapping, request);

        }//end of try

        catch(TTKException expTTK)

        {

            return this.processExceptions(request, mapping, expTTK);

        }//end of catch(TTKException expTTK)

        catch(Exception exp)

        {

            return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

        }//end of catch(Exception exp)

    }//end of doApproveCard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



    /**

     * This method is used to validate the defined rules for each family.

     * Finally it forwards to the appropriate view based on the specified forward mappings

     *

     * @param mapping The ActionMapping used to select this instance

     * @param form The optional ActionForm bean for this request (if any)

     * @param request The HTTP request we are processing

     * @param response The HTTP response we are creating

     * @return ActionForward Where the control will be forwarded, after this request is processed

     * @throws Exception if any error occurs

     */

    public ActionForward doValidateRule(ActionMapping mapping,ActionForm form,HttpServletRequest request,

            HttpServletResponse response) throws Exception{

        try{

        	log.debug("Inside doValidateRule method of MemberAction");

            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");

            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            this.setLinks(request,strSwitchType);

            MemberVO memberVO = null;

            MemberManager memberManager=this.getMemberManager();

            RuleXMLHelper ruleXmlHelper=new RuleXMLHelper();

            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            String strForwardPath=getForwardPath(strActiveSubLink);

            TreeData treeData = TTKCommon.getTreeData(request);



            DynaActionForm generalForm = (DynaActionForm)form;

            String strSelectedRoot = (String)generalForm.get("selectedroot");

            String strSelectedNode = (String)generalForm.get("selectednode");

            treeData.setSelectedRoot(-1);   //sets the selected rows

            memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode); // from tree with with min data

            PolicyManager policyObject=this.getPolicyManagerObject();

            Document policyRule = policyObject.validateEnrollment("E",memberVO.getPolicyGroupSeqID());

            if(policyRule==null)

            {

                TTKException expTTK = new TTKException();

                expTTK.setMessage("error.enrollment.policy.rulenotdefined");

                throw expTTK;

            }//end of if(policyRule==null)



            //Merge the policy rule with Base rule to add display nodes

            Document baseRuleDoc=TTKCommon.getDocument("MasterBaseRules.xml");

            policyRule=ruleXmlHelper.mergeDisplayNodes(policyRule,baseRuleDoc);



            ValidationRuleManager validationRuleManager = this.getValidationRuleManagerObject();

            //clear the previous error stored in database

            validationRuleManager.clearRuleErrors(memberVO.getEnrollmentID(),"E");

            ArrayList alErrors = validationRuleManager.executeEnrollmentValidation(policyRule);

            if(alErrors!=null && alErrors.size()>0)

            {

                //Save the error

                validationRuleManager.saveRuleErrors(alErrors);

                //Update status as Rule failed

                validationRuleManager.updateValidationStatus("E",memberVO.getPolicyGroupSeqID(),"F");

            }//end of if(alErrors!=null && alErrors.size()>0)

            //requery to database

           

            if(strActiveSubLink.equals(strIndividualPolicy) || strActiveSubLink.equals(strIndPolicyasGroup)  || strActiveSubLink.equals(strCorporatePolicy))

            {

                treeData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,

                        request,strSwitchType,getSubLinkCode(strActiveSubLink)));

                treeData.modifySearchData("search");

                //get the meberlist from database

                ArrayList alMembers = memberManager.getMemberList(treeData.getSearchData());

                treeData.setRootData(alMembers);

                treeData.setSearchData(null);

            }

            else if(treeData.getSearchData()!=null && treeData.getSearchData().size()>0)

            {

                //get the meberlist from database

                ArrayList alMembers = memberManager.getMemberList(treeData.getSearchData());

                treeData.setRootData(alMembers);

                if(treeData.getSelectedRoot()>=0)

                {

                    memberVO = (MemberVO)treeData.getSelectedObject(String.valueOf(treeData.getSelectedRoot()),null);



                    ArrayList<Object> alSearchParam = new ArrayList<Object>();

                    alSearchParam.add(memberVO.getPolicyGroupSeqID());

                    alSearchParam.add(strSwitchType);

                    alSearchParam.add(getSubLinkCode(strActiveSubLink));



                    //get the dependent list from database

                    ArrayList alNodeMembers=memberManager.getDependentList(alSearchParam);

                    this.checkCancelMember(treeData,alMembers,request,strSwitchType);

                    ((MemberVO)treeData.getRootData().get(treeData.getSelectedRoot())).setMemberList(alNodeMembers);

                }//end of if(treeData.getSelectedRoot()>=0)

                // Search criteria is there only in Corporate Policy and Non-Corporate Policy tab

                if(strActiveSubLink.equals(strCorporatePolicy) || strActiveSubLink.equals(strNonCorporatePolicy))

                {

                    treeData.setForwardNextLink();

                }//end of if(strActiveSubLink.equals(strCorporatePolicy) || strActiveSubLink.equals(strNonCorporatePolicy))

                request.getSession().setAttribute("treeData",treeData);//set the table data object to session

            }//end of if(treeData.getSearchData().size()>0)

            //Display the appropreate Validate result Icon in tree

            this.checkRuleValid(treeData,strActiveSubLink);



            return this.getForward(strForwardPath, mapping, request);

        }//end of try

        catch(TTKException expTTK)

        {

            return this.processExceptions(request, mapping, expTTK);

        }//end of catch(TTKException expTTK)

        catch(Exception exp)

        {

            return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

        }//end of catch(Exception exp)

    }//end of doValidateRule(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



    /**

     * This method is used to Add a new member to Family

     * Finally it forwards to the appropriate view based on the specified forward mappings

     *

     * @param mapping The ActionMapping used to select this instance

     * @param form The optional ActionForm bean for this request (if any)

     * @param request The HTTP request we are processing

     * @param response The HTTP response we are creating

     * @return ActionForward Where the control will be forwarded, after this request is processed

     * @throws Exception if any error occurs

     */

    public ActionForward doAddMember(ActionMapping mapping,ActionForm form,HttpServletRequest request,

            HttpServletResponse response) throws Exception{

        try

        {

        	log.info("Inside doAddMember method of MemberAction");

            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");

            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            this.setLinks(request,strSwitchType);

            DynaActionForm frmMember=(DynaActionForm)form;

            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

              request.setAttribute("marginPercentage", "none");

            HashMap hmCityList = null;

            ArrayList alCityList = new ArrayList();

            TreeData treeData = TTKCommon.getTreeData(request);



            MemberManager memberManager=this.getMemberManager();

            Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);

            if(strActiveSubLink.trim().equals("Corporate Policy"))

            {

            	 String policy_status ="";

                 policy_status =    memberManager.getPolicyStatus(pol_seq_id);

                 request.getSession().setAttribute("policy_status", policy_status);

            }

            HospitalManager hospitalObject=this.getHospitalManagerObject();



            String strCaption=(String)frmMember.get("caption");

            

            String portedYN=(String)frmMember.get("portedYN");

            

            String strHiddenName="";

            String strSecondName="";

            String strFamilyName="";

            String strRelationID="";

            String strSelectedRoot = (String)frmMember.get("selectedroot");

            String strSelectedNode = (String)frmMember.get("selectednode");



            MemberDetailVO memberDetailVO = new MemberDetailVO();

            MemberAddressVO memberAddressVO=new MemberAddressVO();

            MemberVO memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);

         

            //get the Policy End Date and Endorsement EffectiveDate From the Root object

            Date dtPolicyEndDate=((MemberVO)treeData.getSelectedObject(strSelectedRoot,null)).getPolicyEndDate();

            Date dtPolicyStartDate=((MemberVO)treeData.getSelectedObject(strSelectedRoot,null)).getPolicyStartDate();

            Date dtEffectiveDate=((MemberVO)treeData.getSelectedObject(strSelectedRoot,null)).getEffectiveDate();



            //get the Relationship list based on the Ins. Company. Select Abbreviation Code from root node
           /* ArrayList alRelationShip = memberManager.getRelationshipCode(((MemberVO)treeData.
                    getSelectedObject(strSelectedRoot,null)).getAbbrCode());*/
            
            
            ArrayList alRelationShip = memberManager.getRelationshipCode(((MemberVO)treeData.

                    getSelectedObject(strSelectedRoot,null)).getAbbrCode());
            ArrayList alMemberType = memberManager.getMemberType();
            ArrayList alEntityType = memberManager.getEntityAndFeeType("Entity");
            ArrayList alTPAFeeType = memberManager.getEntityAndFeeType("TPAFEE");
 


            //added for IBM Endorsement Age CR

            memberDetailVO = memberManager.getMemberDetail(memberVO.getPolicyGroupSeqID());

            

            String dtDateOfJoining="";//added for IBM AGE CR

            String dtDateOfMarriage="";//added for IBM AGE CR

            String policyStartDate="";

            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            if(dtPolicyEndDate != null){

            	  policyStartDate = df.format(dtPolicyStartDate);

            }

           

            if(memberDetailVO.getDateOfJoining()!=null)

            {dtDateOfJoining = df.format(memberDetailVO.getDateOfJoining());

            }

            if(memberDetailVO.getDateOfMarriage()!=null)

            {dtDateOfMarriage = df.format(memberDetailVO.getDateOfMarriage());

            }

            //Ended IBM Endorsement Age CR

	        request.getSession().setAttribute("policyStartDate",policyStartDate);

            request.getSession().setAttribute("dateOfJoining",dtDateOfJoining);

            request.getSession().setAttribute("dateOfMarriage",dtDateOfMarriage);



            memberDetailVO.setPolicyGroupSeqID(memberVO.getPolicyGroupSeqID());

            memberDetailVO.setEnrollmentNbr(memberVO.getEnrollmentID());

            memberDetailVO.setStatus("POA");     //select status as Active while adding a member

            memberDetailVO.setStatusDesc("Active");

            memberDetailVO.setExitDate(dtPolicyEndDate);

            strCaption="Add ["+TTKCommon.checkNull(WebBoardHelper.getPolicyNumber(request))+"]";

            memberAddressVO.setCountryCode("123");
            memberAddressVO.setStateCode("DXB");
            memberDetailVO.setMemberAddressVO(memberAddressVO);

            //get the Policy holder name from Root and store in hidden field

         //   strHiddenName=((MemberVO)treeData.getSelectedObject(strSelectedRoot,null)).getName();

            strHiddenName=((MemberVO)treeData.getSelectedObject(strSelectedRoot,null)).getStrFirstName();

            strSecondName=((MemberVO)treeData.getSelectedObject(strSelectedRoot,null)).getStrSecondName();

            strFamilyName=((MemberVO)treeData.getSelectedObject(strSelectedRoot,null)).getStrFamilyName();

            //System.out.println("strHiddenName==========="+strHiddenName);

            //set the Policy End Date and Endorsement EffectiveDate to memberDetailVO

            memberDetailVO.setPolicyEndDate(dtPolicyEndDate);

            memberDetailVO.setPolicyStartDate(dtPolicyStartDate);

            memberDetailVO.setEffectiveDate(dtEffectiveDate);

           // DynaActionForm frmPolicyDetails=(DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");

            MemberVO memberVO1=(MemberVO) request.getSession().getAttribute("memberVO");

			if(memberVO1!=null)

			{

				

				

				

			memberDetailVO.setEventCompleteYN(memberVO1.getEventCompleteYN().trim());  

			memberDetailVO.setCapitaionFlag(memberVO1.getCapitaionFlag().trim());

			}

			else

			{

				

				

				memberDetailVO.setEventCompleteYN("N");

				memberDetailVO.setCapitaionFlag("1");

			}
			memberDetailVO.setDhpoMemberId("");
			memberDetailVO.setPolicySequence("");

            //in vo name is stored with enrollment no. like xxxxx-xxxx-NAME, take the last part for name

            strHiddenName=strHiddenName.substring(strHiddenName.lastIndexOf("-")+1);

            DynaActionForm frmAddMember = (DynaActionForm)FormUtils.setFormValues("frmAddMember",memberDetailVO,

                    this,mapping,request);      

            

            



            //To set the required information to the Add Group screen based on selected node

            frmAddMember = (DynaActionForm)FormUtils.setFormValues("frmAddMember",memberDetailVO,

                    this,mapping,request);

            request.getSession().setAttribute("providerStates",TTKCommon.getStates("123"));

            

            frmAddMember.set("memberAddressVO", (DynaActionForm)FormUtils.setFormValues("frmMemberAddress",

                    memberDetailVO.getMemberAddressVO(),this,mapping,request));

                     

            hmCityList=hospitalObject.getCityInfo();

            

           

            if(hmCityList!=null)

            {

                alCityList = (ArrayList)hmCityList.get(memberDetailVO.getMemberAddressVO().getStateCode());

            }//end of if(hmCityList!=null)

            if(alCityList==null)

            {

                alCityList=new ArrayList();

            }//end of if(alCityList==null)

            frmAddMember.set("caption",strCaption);

            frmAddMember.set("alCityList",alCityList);

            frmAddMember.set("alRelationShip",alRelationShip);
            frmAddMember.set("alMemberType",alMemberType);
            frmAddMember.set("alEntityType",alEntityType);
            frmAddMember.set("alTPAFeeType",alTPAFeeType);
            frmAddMember.set("relationID",strRelationID);

            if(!memberDetailVO.getRelationTypeID().equals("NSF#OTH"))

            {

                frmAddMember.set("disableYN","Y");

            }//end of if(!memberDetailVO.getRelationTypeID().equals("NSF#OTH"))

            else

            {

                frmAddMember.set("disableYN","N");

            }//end of else

            frmAddMember.set("hiddenName",strHiddenName.trim());

            frmAddMember.set("hiddensecondName",strSecondName.trim());

            frmAddMember.set("hiddenfamilyName",strFamilyName.trim());

            frmAddMember.set("corporate",getPolicyTypeCode(strActiveSubLink));
            frmAddMember.set("portedYN",portedYN);
//            String authority = (String) request.getSession().getAttribute("productAuthority");
			request.getSession().setAttribute("prodPolicyAuthority",request.getSession().getAttribute("productAuthority")); 
            
            frmAddMember.set("portedYN",portedYN);
//            String authority = (String) request.getSession().getAttribute("productAuthority");
			request.getSession().setAttribute("prodPolicyAuthority",request.getSession().getAttribute("productAuthority")); 
            
            if(strSwitchType.equals(strEnrollment)&&strActiveSubLink.equals("Individual Policy")){

            	frmAddMember.set("inceptionDate",TTKCommon.getFormattedDate(memberDetailVO.getPolicyStartDate()));

            }//end of if(strSwitchType.equals(strEnrollment)&&strActiveSubLink.equals("Individual Policy"))



            if(strSwitchType.equals(strEndorsement)){

            	frmAddMember.set("inceptionDate",TTKCommon.getFormattedDate(memberDetailVO.getEffectiveDate()));

            }//end of if(strSwitchType.equals(strEndorsement))

			request.getSession().setAttribute("enrollmentNo",memberDetailVO.getEnrollmentID());//added for IBM AGE CR

            /*if(strSwitchType.equals(strEnrollment)&&strActiveSubLink.equals("Individual Policy"))

            {

            PolicyDetailVO policyDetailVO=null;

            PolicyManager policyObject=this.getPolicyManagerObject();

            ArrayList<Object> alPolicy = new ArrayList<Object>();

            alPolicy.add(WebBoardHelper.getPolicySeqId(request));

            alPolicy.add(strSwitchType);//Enrollment or Endorsement

            alPolicy.add(strIndPolicyType);

            policyDetailVO= policyObject.getPolicy(alPolicy);

            frmAddMember.set("inceptionDate",TTKCommon.getFormattedDate(policyDetailVO.getStartDate()));

            }//end of if(strSwitchType.equals(strEnrollment)&&strActiveSubLink.equals("Individual Policy"))

            

*/          

			request.getSession().setAttribute("frmAddMember",frmAddMember);

            this.documentViewer(request,memberVO);

            return this.getForward(getMemberDetailPath(strActiveSubLink), mapping, request);

        }//end of try

        catch(TTKException expTTK)

        {

            return this.processExceptions(request, mapping, expTTK);

        }//end of catch(TTKException expTTK)

        catch(Exception exp)

        {

            return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

        }//end of catch(Exception exp)

    }//end of doAddMember(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



    /**

     * This method is used to view/edit the  member details.

     * Finally it forwards to the appropriate view based on the specified forward mappings

     *

     * @param mapping The ActionMapping used to select this instance

     * @param form The optional ActionForm bean for this request (if any)

     * @param request The HTTP request we are processing

     * @param response The HTTP response we are creating

     * @return ActionForward Where the control will be forwarded, after this request is processed

     * @throws Exception if any error occurs

     */

    public ActionForward doViewMember(ActionMapping mapping,ActionForm form,HttpServletRequest request,

            HttpServletResponse response) throws Exception{

        try

        {

    		log.info("Inside doViewMember method of MemberAction");

            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");

            DynaActionForm frmPolicyDetails=(DynaActionForm)request.getSession().getAttribute("frmPolicyDetails");

            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            this.setLinks(request,strSwitchType);

            DynaActionForm frmMember=(DynaActionForm)form;

            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            request.setAttribute("marginPercentage","block");

            HashMap hmCityList = null;

            ArrayList alCityList = new ArrayList();

            TreeData treeData = TTKCommon.getTreeData(request);



            MemberManager memberManager=this.getMemberManager();

            HospitalManager hospitalObject=this.getHospitalManagerObject();



            String strCaption=(String)frmMember.get("caption");

            Date dtPolicyEndDate=null;

            Date dtPolicyStartDate=null;

            Date dtEffectiveDate=null;

            String dtDateOfJoining="";//added for IBM AGE CR

			String dtDateOfMarriage="";//added for IBM AGE CR

            String strHiddenName="";

            String strRelationID="";

            String strSelectedRoot = (String)frmMember.get("selectedroot");

            //System.out.println("strSelectedRoot======"+strSelectedRoot);

            String strSelectedNode = (String)frmMember.get("selectednode");

            MemberVO memberVO= new MemberVO();

            MemberDetailVO memberDetailVO = new MemberDetailVO();

            

            MemberAddressVO memberAddressVO=new MemberAddressVO();

            memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode); // from tree with min data



            //get the Policy End Date and Endorsement EffectiveDate From the Root object

            dtPolicyEndDate=((MemberVO)treeData.getSelectedObject(strSelectedRoot,null)).getPolicyEndDate();

            dtPolicyStartDate=((MemberVO)treeData.getSelectedObject(strSelectedRoot,null)).getPolicyStartDate();

            dtEffectiveDate=((MemberVO)treeData.getSelectedObject(strSelectedRoot,null)).getEffectiveDate();



            //get the Relationship list based on the Ins. Company. Select Abbreviation Code from root node

            ArrayList alRelationShip = memberManager.getRelationshipCode(((MemberVO)treeData.

                    getSelectedObject(strSelectedRoot,null)).getAbbrCode());
            ArrayList alMemberType = memberManager.getMemberType();
            ArrayList alEntityType = memberManager.getEntityAndFeeType("Entity");
            ArrayList alTPAFeeType = memberManager.getEntityAndFeeType("TPAFEE");
 

            //if node is selected then it is edit mode else it is add mode

            if(!TTKCommon.checkNull((String)frmMember.get("selectednode")).equals(""))

            {

                //create search parameter to get the member detail



                ArrayList<Object> alSearchParam = new ArrayList<Object>();



                alSearchParam.add(memberVO.getMemberSeqID());

                alSearchParam.add(strSwitchType);

                alSearchParam.add(getSubLinkCode(strActiveSubLink));

                if(memberVO.getMemberSeqID()!=null)

                    memberDetailVO = memberManager.getMember(alSearchParam);

                strRelationID=memberDetailVO.getRelationTypeID();

                if(!memberDetailVO.getRelationTypeID().equals("NSF")) // for self address will be address of enrollment address

                    memberAddressVO=memberDetailVO.getMemberAddressVO();

                memberDetailVO.setRelationTypeID(memberDetailVO.getRelationTypeID()+"#"+memberDetailVO.getGenderYN());

                strCaption="Edit ["+TTKCommon.checkNull(WebBoardHelper.getPolicyNumber(request))+"]";

            }//end of if(!TTKCommon.checkNull((String)frmMemberList.get("selectednode")).equals(""))



			//added for IBM Age CR

			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

			String policyStartDate = df.format(dtPolicyStartDate);

			if(memberDetailVO.getDateOfJoining()!=null)

			{dtDateOfJoining = df.format(memberDetailVO.getDateOfJoining());

			}

			if(memberDetailVO.getDateOfMarriage()!=null)

			{dtDateOfMarriage = df.format(memberDetailVO.getDateOfMarriage());

			}

			

			if("N".equals(memberDetailVO.getMemeligFlag()))

				request.setAttribute("memeligFlag", "Member deleted because of modification in the member inception "

						+ "eligibility rule (s) at the Administration level");

			request.getSession().setAttribute("policyStartDate",policyStartDate);

			request.getSession().setAttribute("dateOfJoining",dtDateOfJoining);

			request.getSession().setAttribute("dateOfMarriage",dtDateOfMarriage);

			
			String authority = (String) request.getSession().getAttribute("productAuthority");
			String officeCode = (String) request.getSession().getAttribute("officeCode");
			request.getSession().setAttribute("prodPolicyAuthority",authority);
			if(officeCode==null || officeCode=="")
				request.getSession().setAttribute("officeCode",frmPolicyDetails.get("officeCode"));
			else
				request.getSession().setAttribute("officeCode",officeCode);
				
			
		   //Ended IBM Age CR
            //request.getSession().setAttribute("policyStatusForMember", memberDetailVO.getStatus());
            request.getSession().setAttribute("policyStatusForMember", memberDetailVO.getStatus());

            memberDetailVO.setMemberAddressVO(memberAddressVO);

            //get the Policy holder name from Root and store in hidden field

            

            strHiddenName=((MemberVO)treeData.getSelectedObject(strSelectedRoot,null)).getName();



            //set the Policy End Date and Endorsement EffectiveDate to memberDetailVO

            memberDetailVO.setPolicyEndDate(dtPolicyEndDate);

            memberDetailVO.setPolicyStartDate(dtPolicyStartDate);

            memberDetailVO.setEffectiveDate(dtEffectiveDate);

            memberDetailVO.setMarginAEDPER("PER");

            //in vo name is stored with enrollment no. like xxxxx-xxxx-NAME, take the last part for name

            strHiddenName=strHiddenName.substring(strHiddenName.lastIndexOf("-")+1);

            DynaActionForm frmAddMember = (DynaActionForm)FormUtils.setFormValues("frmAddMember",memberDetailVO,

                    this,mapping,request);



            request.getSession().setAttribute("providerStates",TTKCommon.getStates(memberDetailVO.getMemberAddressVO().getCountryCode()));                 

            request.getSession().setAttribute("providerAreas",TTKCommon.getAreas(memberDetailVO.getMemberAddressVO().getStateCode()));

           

			 

            MemberVO memberVO1=(MemberVO) request.getSession().getAttribute("memberVO");           

			if(memberVO1!=null)

			{

			memberDetailVO.setEventCompleteYN(memberVO1.getEventCompleteYN());  

			memberDetailVO.setCapitaionFlag(memberVO1.getCapitaionFlag());

			}

			else

			{

				memberDetailVO.setEventCompleteYN("N");

				memberDetailVO.setCapitaionFlag("1");

			}

			String portedYN =  (String) request.getSession().getAttribute("portedYN");

			frmAddMember.set("portedYN", portedYN);

            frmAddMember.set("memberAddressVO", (DynaActionForm)FormUtils.setFormValues("frmMemberAddress",memberDetailVO.getMemberAddressVO(),this,mapping,request));

            frmAddMember.set("eventCompleteYN", memberDetailVO.getEventCompleteYN());

            frmAddMember.set("capitaionFlag", memberDetailVO.getCapitaionFlag());

            hmCityList=hospitalObject.getCityInfo();

            if(hmCityList!=null)

            {

                alCityList = (ArrayList)hmCityList.get(memberDetailVO.getMemberAddressVO().getStateCode());

            }//end of if(hmCityList!=null)

            if(alCityList==null)

            {

                alCityList=new ArrayList();

            }//end of if(alCityList==null)

            frmAddMember.set("caption",strCaption);

            frmAddMember.set("alCityList",alCityList);

            frmAddMember.set("alRelationShip",alRelationShip);
            frmAddMember.set("alMemberType",alMemberType);
            frmAddMember.set("alEntityType",alEntityType);
            frmAddMember.set("alTPAFeeType",alTPAFeeType);
            frmAddMember.set("relationID",strRelationID);

            request.getSession().setAttribute("enrollmentNo",memberDetailVO.getEnrollmentID());

            if(!memberDetailVO.getRelationTypeID().equals("NSF#OTH"))

            {

                frmAddMember.set("disableYN","Y");

            }//end of if(!memberDetailVO.getRelationTypeID().equals("NSF#OTH"))

            else

            {

                frmAddMember.set("disableYN","N");

            }//end of else

            frmAddMember.set("hiddenName",strHiddenName.trim());

            frmAddMember.set("corporate",getPolicyTypeCode(strActiveSubLink));

            request.getSession().setAttribute("frmAddMember",frmAddMember);            

            this.documentViewer(request,memberVO);

    		 Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);

    		

    		  if(strActiveSubLink.trim().equals("Corporate Policy"))

              {

              	   String policy_status ="";

                   policy_status =    memberManager.getPolicyStatus(pol_seq_id);

                  request.getSession().setAttribute("policy_status", policy_status);     

              }

    		 

            ActionForward actionForward=this.getForward(getMemberDetailPath(strActiveSubLink), mapping, request);

            return actionForward;

        }//end of try

        catch(TTKException expTTK)

        {

            return this.processExceptions(request, mapping, expTTK);

        }//end of catch(TTKException expTTK)

        catch(Exception exp)

        {

            return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

        }//end of catch(Exception exp)

    }//end of doViewMember(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)







    /**

     * This method is used to delete the selected record.

     * Finally it forwards to the appropriate view based on the specified forward mappings

     *

     * @param mapping The ActionMapping used to select this instance

     * @param form The optional ActionForm bean for this request (if any)

     * @param request The HTTP request we are processing

     * @param response The HTTP response we are creating

     * @return ActionForward Where the control will be forwarded, after this request is processed

     * @throws Exception if any error occurs

     */

    public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,

            HttpServletResponse response) throws Exception{

        try{

        	log.info("Inside doDelete method of MemberAction");

            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");

            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            this.setLinks(request,strSwitchType);

            DynaActionForm frmMember=(DynaActionForm)form;

            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            TreeData treeData = TTKCommon.getTreeData(request);



            MemberManager memberManager=this.getMemberManager();

            String strFlag = "";

            String strSeqID = "";

            List<String> iResult;

            Long lngEndorsementSeqID = null;

            ArrayList alMembers = null;

            ArrayList<Object> alSearchParam = new ArrayList<Object>();

            ArrayList<Object> alSearchDepeParam = new ArrayList<Object>();

            String strSelectedRoot = (String)frmMember.get("selectedroot");

            String strSelectedNode = (String)frmMember.get("selectednode");

            //treeData.setSelectedRoot(-1);   //sets the selected rows

            MemberVO memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);



            //if selected node is null then root element is selected else node is selected

            if(strSelectedNode==null ||strSelectedNode.equals(""))

            {

                strFlag = "GROUP";

                strSeqID = memberVO.getPolicyGroupSeqID().toString();

            }//end of (strSelectedNode==null || strSelectedNode.equals("")

            else

            {

                strFlag = "MEMBER";

                strSeqID = memberVO.getMemberSeqID().toString();

            }//end of else

            if(strSwitchType.equals(strEndorsement)) // for Endorsement flow get the Endorsenet Seq ID from webBoard

            {

                lngEndorsementSeqID = WebBoardHelper.getEndorsementSeqId(request);

            }//end of if(strSwitchType.equals("END"))



            //create parameter to delete the member detail

            alSearchParam.add(strSwitchType);

            alSearchParam.add(getSubLinkCode(strActiveSubLink));

            alSearchParam.add(strFlag);

            alSearchParam.add(strSeqID);

            alSearchParam.add(lngEndorsementSeqID);

            alSearchParam.add(TTKCommon.getUserSeqId(request));
            alSearchParam.add(frmMember.get("deletionRemarks"));
            alSearchParam.add(frmMember.get("deletionRemarks"));

            //call business layer to delete the member or group

            iResult= memberManager.deleteMember(alSearchParam);
            
            if(String.valueOf(iResult.get(1)) != null && String.valueOf(iResult.get(1)).equals("D")){
         	   request.setAttribute("DeleteDateAndCreationDateFlag", "message.DeleteDateAndCreationDateFlag");
            }else if(String.valueOf(iResult.get(1)) != null && String.valueOf(iResult.get(1)).equals("P")){
         	   request.setAttribute("DeletionNotAllowedClaimPrauthExist", "message.DeletionNotAllowedClaimPrauthExist");
            }
            if(Integer.valueOf(iResult.get(0))>0 && strFlag.equals("GROUP"))
            {
            	if(Integer.valueOf(iResult.get(0)).equals(treeData.getRootData().size())  )
            	{

            		treeData.modifySearchData(strDelete);//modify the search data

            		int iStartRowCount = Integer.parseInt((String)treeData.getSearchData().

            				get(treeData.getSearchData().size()-2));

            		if(iStartRowCount > 0)

            		{

            			alMembers = memberManager.getMemberList(treeData.getSearchData());

            		}//end of if(iStartRowCount > 0)

            	}//end if(iDeleteCount == treeData.getRootData().size())

            	else

            	{

            		alMembers = memberManager.getMemberList(treeData.getSearchData());

            	}//end of else

            	treeData.setRootData(alMembers);

            	//Search criteria is there only in Corporate Policy and Non-Corporate Policy tab

            	if(strActiveSubLink.equals(strCorporatePolicy) || strActiveSubLink.equals(strNonCorporatePolicy))

            	{

            		treeData.setForwardNextLink();

            	}//end of if(strActiveSubLink.equals(strCorporatePolicy) || strActiveSubLink.equals(strNonCorporatePolicy))

            	treeData.setSelectedRoot(-1);

            	request.getSession().setAttribute("treeData",treeData);

            }// end of if(iResult>0)

            else

            {

            	alSearchDepeParam = new ArrayList<Object>();

            	alSearchDepeParam.add(memberVO.getPolicyGroupSeqID());

            	alSearchDepeParam.add(strSwitchType);

            	alSearchDepeParam.add(getSubLinkCode(strActiveSubLink));

                //get the dependent list from database

                ArrayList alNodeMembers=memberManager.getDependentList(alSearchDepeParam);

                this.checkCancelMember(treeData,alMembers,request,strSwitchType);

                ((MemberVO)treeData.getRootData().get(Integer.parseInt(strSelectedRoot))).setMemberList(alNodeMembers);

                if(Integer.valueOf(iResult.get(0))<=0)

                {

                	doSearch(mapping,form,request,response);

                }//end of if(iResult>0)

            }//end of else

           

            Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);

            

            if(strActiveSubLink.trim().equals("Corporate Policy"))

           { 

            	 String policy_status ="";

                 policy_status =    memberManager.getPolicyStatus(pol_seq_id);

                 return this.getForward(getForwardPath(strActiveSubLink,policy_status), mapping, request);

           }	

            

          

            return this.getForward(getForwardPath(strActiveSubLink), mapping, request);

        }//end of try

        catch(TTKException expTTK)

        {

        	

            return this.processExceptions(request, mapping, expTTK);

        }//end of catch(TTKException expTTK)

        catch(Exception exp)

        {

        	

            return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

        }//end of catch(Exception exp)

    }//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



    /**

     * This method is used to delete the selected record.

     * Finally it forwards to the appropriate view based on the specified forward mappings

     *

     * @param mapping The ActionMapping used to select this instance

     * @param form The optional ActionForm bean for this request (if any)

     * @param request The HTTP request we are processing

     * @param response The HTTP response we are creating

     * @return ActionForward Where the control will be forwarded, after this request is processed

     * @throws Exception if any error occurs

     */

    public ActionForward doCancel(ActionMapping mapping,ActionForm form,HttpServletRequest request,

            HttpServletResponse response) throws Exception{

        try{

        	log.info("Inside doCancel method of MemberAction");

            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");



            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            this.setLinks(request,strSwitchType);

            DynaActionForm frmMember=(DynaActionForm)form;

            

            

            String cancellationRemarks = request.getParameter("cancellationRemarks");

            

            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            TreeData treeData = TTKCommon.getTreeData(request);



            MemberManager memberManager=this.getMemberManager();



            String strFlag = "";

            String strSeqID = "";

            int iResult=0;

            Long lngEndorsementSeqID = null;

            ArrayList<Object> alSearchParam = new ArrayList<Object>();

            String strSelectedRoot = (String)frmMember.get("selectedroot");

            String strSelectedNode = (String)frmMember.get("selectednode");

            treeData.setSelectedRoot(-1);   //sets the selected rows

            MemberVO memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);



            //if selected node is null then root element is selected else node is selected

            if(strSelectedNode==null ||strSelectedNode.equals(""))

            {

                strFlag = "GROUP";

                strSeqID = memberVO.getPolicyGroupSeqID().toString();

            }//end of (strSelectedNode==null || strSelectedNode.equals("")

            else

            {

            	String memberinfo = memberVO.getName();

            	if(memberinfo.contains("Self")){

            		strFlag = "GROUP";

                    strSeqID = memberVO.getPolicyGroupSeqID().toString();

            	}else{

            	

                strFlag = "MEMBER";

                strSeqID = memberVO.getMemberSeqID().toString();

            	}

            }//end of else

            if(strSwitchType.equals("END")) // for Endorsement flow get the Endorsenet Seq ID from webBoard

            {

                lngEndorsementSeqID = WebBoardHelper.getEndorsementSeqId(request);

            }//end of if(strSwitchType.equals("END"))



            //create parameter to cancel the member detail

            alSearchParam.add(strSwitchType);

            alSearchParam.add(getSubLinkCode(strActiveSubLink));

            alSearchParam.add(strFlag);

            alSearchParam.add(strSeqID);

            alSearchParam.add(memberVO.getPolicySeqID());

            alSearchParam.add(lngEndorsementSeqID);

            alSearchParam.add(TTKCommon.getUserSeqId(request));

            

            

            alSearchParam.add(cancellationRemarks);

            

            iResult= memberManager.cancelMember(alSearchParam);

            log.debug("Result value is :"+iResult);

            return this.getForward(getForwardPath(strActiveSubLink), mapping, request);

        }//end of try

        catch(TTKException expTTK)

        {

            return this.processExceptions(request, mapping, expTTK);

        }//end of catch(TTKException expTTK)

        catch(Exception exp)

        {

            return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

        }//end of catch(Exception exp)

    }//end of doCancel(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)





    /**

     * This method is used to navigate to previous screen

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

        	String strSwitchType = "";

            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");



            if(frmPolicyList != null){

            	strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            }//end of if(frmPolicyList != null)



            this.setLinks(request,strSwitchType);

            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            TreeData treeData = TTKCommon.getTreeData(request);

            MemberManager memberManager=this.getMemberManager();

            if(treeData.getSearchData()!=null && treeData.getSearchData().size()>0)

            {

                //get the meberlist from database

                ArrayList alMembers = memberManager.getMemberList(treeData.getSearchData());

                treeData.setRootData(alMembers);

                if(treeData.getSelectedRoot()>=0)

                {

                    MemberVO memberVO = (MemberVO)treeData.getSelectedObject(String.valueOf(treeData.

                    					 getSelectedRoot()),null);

                    ArrayList<Object> alSearchParam = new ArrayList<Object>();

                    alSearchParam.add(memberVO.getPolicyGroupSeqID());

                    alSearchParam.add(strSwitchType);

                    alSearchParam.add(getSubLinkCode(strActiveSubLink));



                    //get the dependent list from database

                    ArrayList alNodeMembers=memberManager.getDependentList(alSearchParam);

                    this.checkCancelMember(treeData,alMembers,request,strSwitchType);

                    checkCancelMember(treeData,alNodeMembers,request,strSwitchType);

                    ((MemberVO)treeData.getRootData().get(treeData.getSelectedRoot())).setMemberList(alNodeMembers);

                }//end of if(treeData.getSelectedRoot()>=0)

                // Search criteria is there only in Corporate Policy and Non-Corporate Policy tab

                if(strActiveSubLink.equals(strCorporatePolicy) || strActiveSubLink.equals(strNonCorporatePolicy))

                {

                    treeData.setForwardNextLink();

                }//end of if(strActiveSubLink.equals(strCorporatePolicy) || strActiveSubLink.equals(strNonCorporatePolicy))

                request.getSession().setAttribute("treeData",treeData);//set the table data object to session

            }//end of if(treeData.getSearchData().size()>0)

            //Display the appropreate Validate result Icon in tree

            this.checkRuleValid(treeData,strActiveSubLink);

            Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);

            if(strActiveSubLink.trim().equals("Corporate Policy"))

            {   

            	 String policy_status ="";

                 policy_status =    memberManager.getPolicyStatus(pol_seq_id);

                 request.getSession().setAttribute("policy_status", policy_status);

                 return this.getForward(getForwardPath(strActiveSubLink,policy_status), mapping, request);

            }

            return this.getForward(getForwardPath(strActiveSubLink), mapping, request);

        }//end of try

        catch(TTKException expTTK)

        {

            return this.processExceptions(request, mapping, expTTK);

        }//end of catch(TTKException expTTK)

        catch(Exception exp)

        {

            return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

        }//end of catch(Exception exp)

    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



    /**

     * This method is used to Clear the associated Family and Member rules for this Policy.

     * Finally it forwards to the appropriate view based on the specified forward mappings

     *

     * @param mapping The ActionMapping used to select this instance

     * @param form The optional ActionForm bean for this request (if any)

     * @param request The HTTP request we are processing

     * @param response The HTTP response we are creating

     * @return ActionForward Where the control will be forwarded, after this request is processed

     * @throws Exception if any error occurs

     */

    public ActionForward doClearRules(ActionMapping mapping,ActionForm form,HttpServletRequest request,

            HttpServletResponse response) throws Exception{

        try{

            log.info("Inside doClearRules method of MemberAction");

            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");

            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            this.setLinks(request,strSwitchType);

            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            MemberManager memberManager=this.getMemberManager();



            //call the bussines to clear the Rules

            int iResultCount=memberManager.clearEnrollmentRules(WebBoardHelper.getPolicySeqId(request),

                    strSwitchType,getPolicyTypeCode(strActiveSubLink));



            if(iResultCount>0)

            {

                request.setAttribute("updated","message.enrollment.rulescleared");

            }//end of if(iResultCount>0)

            Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);

            if(strActiveSubLink.trim().equals("Corporate Policy"))

            {   

            	 String policy_status ="";

                 policy_status =    memberManager.getPolicyStatus(pol_seq_id);

                 request.getSession().setAttribute("policy_status", policy_status);

                 return this.getForward(getForwardPath(strActiveSubLink,policy_status), mapping, request);

            }

            return this.getForward(getForwardPath(strActiveSubLink), mapping, request);

        }//end of try

        catch(TTKException expTTK)

        {

            return this.processExceptions(request, mapping, expTTK);

        }//end of catch(TTKException expTTK)

        catch(Exception exp)

        {

            return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

        }//end of catch(Exception exp)

    }//end of doClearRules(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



    /**

     * This method will add search criteria fields and values to the arraylist and will return it

     * @param frmPolicyList current instance of form bean

     * @param request HttpServletRequest object

     * @param strActiveSubLink current Active sublink

     * @return alSearchObjects ArrayList of search params

     * @throws TTKException

     */

    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSearchUser,HttpServletRequest request,

    		String strSwitchType,String strSubLinkCode)throws TTKException

    {

        //build the column names along with their values to be searched

        ArrayList<Object> alSearchParams = new ArrayList<Object>();

        if(request.getAttribute("policyVO")!=null)

        {

            PolicyVO policyVO =(PolicyVO)request.getAttribute("policyVO");

           

            if(policyVO!=null)

            {

            	

            	

                frmSearchUser.set("sEnrollmentNbr",policyVO.getEnrollmentNbr());

            }//end of if(policyVO!=null)

        }//end of if(request.getParameter("policyVO")!=null)

        if(strSwitchType.equals("ENM"))

        {

            alSearchParams.add(WebBoardHelper.getPolicySeqId(request));

        }//end of if(strSwitchType.equals("ENM"))

        else

        {

            alSearchParams.add(WebBoardHelper.getEndorsementSeqId(request));					

        }//end of else



        if(strSubLinkCode.equals("IP") || strSubLinkCode.equals("IG"))

        {

            alSearchParams.add(null); //(String)frmSearchUser.get("sEnrollmentNbr")

            alSearchParams.add(null); //frmSearchUser.get("sMemberName")

            alSearchParams.add(null); //frmSearchUser.get("sEmpNo")

            alSearchParams.add(null); //frmSearchUser.get("sCorpMemberName")

            alSearchParams.add(null);

            alSearchParams.add(null);

            alSearchParams.add(strSwitchType); 

            alSearchParams.add(strSubLinkCode);

            alSearchParams.add("1"); //Start Row  

            alSearchParams.add("1"); //End Row

        }//end of if(strSubLinkCode.equals("IP") || strSubLinkCode.equals("IG"))

        else

        {

        	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchUser.get("sEnrollmentNbr")));	

            alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchUser.get("sMemberName")));		

            if(strSubLinkCode.equals("CP"))

            {

            	

            	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchUser.get("sCorpMemberName")));	

            	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchUser.get("sEmployeeNum")));		

            	

            }//end of if(strSubLinkCode.equals("CP"))

            else{

            	alSearchParams.add(null); //frmSearchUser.get("sEmpNo")

            	alSearchParams.add(null); //frmSearchUser.get("sCorpMemberName")

            }//end of else

            if(strSubLinkCode.equals("NC"))

            {

            	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchUser.get("sCertificateNumber")));

            	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchUser.get("sOrderNumber")));

            }//end of if(strSubLinkCode.equals("NC"))

            else

            {

            	

            	alSearchParams.add(null);							

            	alSearchParams.add(null);							

            }//end else

            alSearchParams.add(strSwitchType);						

            alSearchParams.add(strSubLinkCode);						

            

            if(strSubLinkCode.equals("CP"))

            {

            	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchUser.get("sMemberID")));	

            	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchUser.get("sEmiratesID")));		

            	

            }//end of if(strSubLinkCode.equals("CP"))

            

            if(request.getAttribute("policyVO")!=null) // only one enrollment detail has to fetch if policyVO is found in request

            {

                alSearchParams.add("1"); //Start Row				

                alSearchParams.add("1"); //End Row					

            }//end of if(request.getAttribute("policyVO")!=null)

        }//end of else

        

     

     

        

        return alSearchParams;

    }//end of populateSearchCriteria(DynaActionForm frmSearchUser,HttpServletRequest request)



    /**

     * Returns the MemberManager session object for invoking methods on it.

     * @return MemberManager session object which can be used for method invokation

     * @exception throws TTKException

     */

    private MemberManager getMemberManager() throws TTKException

    {

        MemberManager memberManager = null;

        try

        {

            if(memberManager == null)

            {

                InitialContext ctx = new InitialContext();

                memberManager = (MemberManager) ctx.lookup("java:global/TTKServices/business.ejb3/MemberManagerBean!com.ttk.business.enrollment.MemberManager");

            }//end if

        }//end of try

        catch(Exception exp)

        {

            throw new TTKException(exp, "memberdetail");

        }//end of catch

        return memberManager;

    }//end getMemberManager()



    /**

     * Returns the HospitalManager session object for invoking methods on it.

     * @return HospitalManager session object which can be used for method invokation

     * @exception throws TTKException

     */

    private HospitalManager getHospitalManagerObject() throws TTKException

    {

        HospitalManager hospManager = null;

        try

        {

            if(hospManager == null)

            {

                InitialContext ctx = new InitialContext();

                hospManager = (HospitalManager) ctx.lookup("java:global/TTKServices/business.ejb3/HospitalManagerBean!com.ttk.business.empanelment.HospitalManager");

            }//end if(hospManager == null)

        }//end of try

        catch(Exception exp)

        {

            throw new TTKException(exp, "memberdetail");

        }//end of catch

        return hospManager;

    }//end getHospitalManagerObject()



    /**

     * Check the Member cancel status and set the display property of icons.

     * @param request The HTTP request we are processing

     * @param treeData TreeData for which permission has to check

     * @throws TTKException

     */

    private void checkCancelMember(TreeData treeData,ArrayList alMembers,HttpServletRequest request,

    		String strSwitchType) throws TTKException

    {

        ArrayList<Object> alNodeSetting=new ArrayList<Object>();

        Node chNode=null;

        if(alMembers!=null)

        {

            for(int i=0;i<alMembers.size();i++)

            {

                chNode=treeData.copyNodeSetting();//new Node();

                if(((MemberVO)alMembers.get(i)).getCancleYN().equalsIgnoreCase("Y"))

                {

                    chNode.setTextColor("red");

                    chNode.setIconVisible(CHILD_CANCEL_ICON,false);

                }//end of if(((MemberVO)alNodeMembers.get(i)).getCancleYN().equalsIgnoreCase("Y"))

                else

                {

                    chNode.setTextColor("black");

                    if(strSwitchType.equals("END") && TTKCommon.isAuthorized(request,"Cancel"))

                    {

                        chNode.setIconVisible(CHILD_CANCEL_ICON,true);

                    }//end of if(strSwitchType.equals("END") && TTKCommon.isAuthorized(request,"Cancel"))

                }//end of else

                alNodeSetting.add(chNode);

            }//end of for(int i=0;i<alNodeMembers.size();i++)

            treeData.setNodeSettings(alNodeSetting);

        }//end of if(alMembers!=null)

    }//end of checkRuleValid(TreeData treeData,ArrayList alMembers)





    /**

     * Check the Validation status and set the display property of icons.

     * @param request The HTTP request we are processing

     * @param treeData TreeData for which permission has to check

     */

    private void checkRuleValid(TreeData treeData,String strActiveSubLink)//,ArrayList alMembers

    {

        ArrayList alMembers = treeData.getRootData();

        if(alMembers!=null && alMembers.size()>0)

        {

            ArrayList<Object> alNodeSetting=new ArrayList<Object>();

            Node chNode=null;

            //String srtValidationStatus="";

            for(int i=0;i<alMembers.size();i++)

            {

                chNode=treeData.copyRootSetting();//new Node();

                //srtValidationStatus = ((MemberVO)alMembers.get(i)).getValidationStatus();

                //For corporate and Non corporate Policy hide the renew Icon

              /*  if(strActiveSubLink.equals(strCorporatePolicy) || strActiveSubLink.equals(strNonCorporatePolicy))

                {

                    chNode.setIconVisible(RENEW_ICON,false);

                }//end of if(strActiveSubLink.equals(strCorporatePolicy) || strActiveSubLink.equals(strNonCorporatePolicy))

                //if policy TPA Policy status is Renewal then only display Renewal Icon

                else if(!((MemberVO)alMembers.get(i)).getTPAStatusTypeID().equals("TPR"))

                {

                    chNode.setIconVisible(RENEW_ICON,false);

                }*///end of if(!((MemberVO)alMembers.get(0)).getTPAStatusTypeID().equals("TPR"))



                /*chNode.setIconVisible(R00T_UNCHECK_ICON,false);

                chNode.setIconVisible(ROOT_PASS_ICON,false);

                chNode.setIconVisible(R00T_FAIL_ICON,false);*/

                /*if(srtValidationStatus.equalsIgnoreCase("U")||srtValidationStatus.equals(""))

                {

                    chNode.setIconVisible(R00T_UNCHECK_ICON,true);

                }//end of if(((MemberVO)alNodeMembers.get(i)).getCancleYN().equalsIgnoreCase("Y"))

                else if(srtValidationStatus.equalsIgnoreCase("P"))

                {

                    chNode.setIconVisible(ROOT_PASS_ICON,true);

                }//end of else

                else if(srtValidationStatus.equalsIgnoreCase("F"))

                {

                    chNode.setIconVisible(R00T_FAIL_ICON,true);

                }//end of else

*/                alNodeSetting.add(chNode);

            }//end of for(int i=0;i<alNodeMembers.size();i++)

            treeData.setRootSettings(alNodeSetting);

        }

    }//end of checkRuleValid(TreeData treeData,ArrayList alMembers)



    /**

     * Check the ACL permission and set the display property of icons.

     * @param request The HTTP request we are processing

     * @param treeData TreeData for which permission has to check

     */

    private void checkTreePermission(HttpServletRequest request,TreeData treeData,String strSwitchType)

    	throws TTKException

    {

        log.info("--------- inside check Tree permission --------------");

        //String strActiveSubLink=TTKCommon.getActiveSubLink(request);

        if(strSwitchType.equals("")||strSwitchType.equals("ENM"))

        {

            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(R00T_CANCEL_ICON,false);

            treeData.getTreeSetting().getChildNodeSetting().setIconVisible(CHILD_CANCEL_ICON,false);



            if(WebBoardHelper.getCompletedYN(request).equals("Y") && TTKCommon.isAuthorized(request,"Add")==true){

                treeData.getTreeSetting().getRootNodeSetting().setIconVisible(ROOT_CARD_ICON,true);

                treeData.getTreeSetting().getChildNodeSetting().setIconVisible(CHILD_CARD_ICON,true);

            }//end of if(WebBoardHelper.getCompletedYN(request).equals("Y"))

            else{

                treeData.getTreeSetting().getRootNodeSetting().setIconVisible(ROOT_CARD_ICON,false);

                treeData.getTreeSetting().getChildNodeSetting().setIconVisible(CHILD_CARD_ICON,false);

            }//end of else

        }//end of if(strSwitchType.equals("")||strSwitchType.equals("ENM"))

        /*if(strSwitchType.equals("ENM")&&!(strActiveSubLink.equals(strIndividualPolicy)|| strActiveSubLink.equals(strIndPolicyasGroup)))

        {

        	if(! (strActiveSubLink.equals(strCorporatePolicy))&&(TTKCommon.isAuthorized(request,"Special Permission")==false) )

	        {

	        	treeData.getTreeSetting().getRootNodeSetting().setIconVisible(R00T_CHANGEPWD_ICON,false);

	        }//end of if(! (strActiveSubLink.equals(strCorporatePolicy))&&(TTKCommon.isAuthorized(request,"Special Permission")==false) )

        }//END OF if(strSwitchType.equals("ENM")&&!(strActiveSubLink.equals(strIndividualPolicy)|| strActiveSubLink.equals(strIndPolicyasGroup)))*/

        // check the permision and set the tree for not to display respective icon

        if(TTKCommon.isAuthorized(request,"Add")==false)

        {

            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(ADD_ICON,false);

        }//end of if(TTKCommon.isAuthorized(request,"Add")==false)

        if(TTKCommon.isAuthorized(request,"Delete")==false)

        {

            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(ROOT_DELETE_ICON,false);

            treeData.getTreeSetting().getChildNodeSetting().setIconVisible(CHILD_DELETE_ICON,false);

        }// end of if(TTKCommon.isAuthorized(request,"Delete")==false)

        if(TTKCommon.isAuthorized(request,"Cancel")==false)

        {

            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(R00T_CANCEL_ICON,false);

            treeData.getTreeSetting().getChildNodeSetting().setIconVisible(CHILD_CANCEL_ICON,false);

        }// end of if(TTKCommon.isAuthorized(request,"Delete")==false)

        if(TTKCommon.isAuthorized(request,"DefineRule")==false)

        {

            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(ROOT_RULE_ICON,false);

            treeData.getTreeSetting().getChildNodeSetting().setIconVisible(MEMBER_RULE_ICON,false);

        }//end of if(TTKCommon.isAuthorized(request,"DefineRule")==false)

        if(TTKCommon.isAuthorized(request,"Renew Members")==false)

        {

            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(RENEW_ICON,false);

        }//end of if(TTKCommon.isAuthorized(request,"Renew Members")==false)

        log.debug("---------(Exit) inside check Tree permission --------------");

    }//end of checkTreePermission(HttpServletRequest request,TreeData treeData,String strSwitchType)



    private void documentViewer(HttpServletRequest request, MemberVO memberVO) throws TTKException

    {

        //Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");



        ArrayList<String> alMemberVO = new ArrayList<String>();

        alMemberVO.add("leftlink="+TTKCommon.getActiveLink(request));

        alMemberVO.add("policy_number="+WebBoardHelper.getPolicyNumber(request));

        if(memberVO != null){

            if(memberVO.getDMSRefID()!= null){

                alMemberVO.add("dms_reference_number="+memberVO.getDMSRefID());

            }//end of if(memberVO.getDMSRefID()!= null)

            else{

                alMemberVO.add("dms_reference_number="+null);

            }//end of else

        }

        else{

            alMemberVO.add("dms_reference_number="+null);

        }//end of else

        //toolbar.setDocViewParams(alhosvo);

        if(request.getSession().getAttribute("toolbar")!=null)

            ((Toolbar)request.getSession().getAttribute("toolbar")).setDocViewParams(alMemberVO);

    }//end of documentViewer(HttpServletRequest request)



    

    public ActionForward getPreAuthCount(ActionMapping mapping, ActionForm form,

			HttpServletRequest request, HttpServletResponse response)

			throws Exception {

	PrintWriter	writer= response.getWriter();

	int preauthCount=0;

		try{

	      



        	log.info("Inside getPreAuthCount method of MemberAction");

            DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");



            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));

            String endFlag="";

             endFlag = request.getParameter("endorsmentFlag");

            String policySeqID = request.getParameter("policySeqID");

            String strActiveSubLink=TTKCommon.getActiveSubLink(request);

            TreeData treeData = TTKCommon.getTreeData(request);





            String strFlag = "";

            String strSeqID = "";

            Long lngEndorsementSeqID = null;

            ArrayList<Object> alSearchParam = new ArrayList<Object>();

            String strSelectedRoot = (String)request.getParameter("selectedroot");

            String strSelectedNode = (String)request.getParameter("selectednode");

            treeData.setSelectedRoot(-1);   //sets the selected rows

            MemberVO memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);



            

            

            

            

            

            if("POL".equals(endFlag)){

            	

            	 endFlag="POL";

            	 strSeqID = policySeqID;

            }

            else{

            	

            	 //if selected node is null then root element is selected else node is selected

                if(strSelectedNode==null ||strSelectedNode.equals(""))

                {

                    strFlag = "GROUP";

                    strSeqID = memberVO.getPolicyGroupSeqID().toString();

                    endFlag="GRP";

                }//end of (strSelectedNode==null || strSelectedNode.equals("")

                else

                {

                	String memberinfo = memberVO.getName();

                	if(memberinfo.contains("Self")){

                		strFlag = "GROUP";

                        strSeqID = memberVO.getPolicyGroupSeqID().toString();

                        endFlag="GRP";

                	}else{

                    strFlag = "MEMBER";

                    strSeqID = memberVO.getMemberSeqID().toString();

                    endFlag="MEM";

                	}

                }//end of else

                if(strSwitchType.equals("END")) // for Endorsement flow get the Endorsenet Seq ID from webBoard

                {

                    lngEndorsementSeqID = WebBoardHelper.getEndorsementSeqId(request);

                }//end of if(strSwitchType.equals("END"))

            	

            }

           



            //create parameter to cancel the member detail

         /*   alSearchParam.add(strSwitchType);

            alSearchParam.add(getSubLinkCode(strActiveSubLink));

            alSearchParam.add(strFlag);

            alSearchParam.add(strSeqID);

            alSearchParam.add(memberVO.getPolicySeqID());

            alSearchParam.add(lngEndorsementSeqID);

            alSearchParam.add(TTKCommon.getUserSeqId(request));*/

            

            

            

            

                PolicyDetailVO policyDetailVO=new PolicyDetailVO();

                

                

                DynaActionForm frmEndorsementDetails = (DynaActionForm)

    					request.getSession().getAttribute("frmEndorsementDetails");

                String effectiveDate = frmEndorsementDetails.getString("effectiveDate");

            	

                

                policyDetailVO.setEndorsmentFlag(endFlag);

                policyDetailVO.setPolicySeqID(Long.valueOf(strSeqID));

                policyDetailVO.setEndorsmentEeffectiveDate(effectiveDate);

          	     PolicyManager policyObject=this.getPolicyManagerObject();

                

            	 preauthCount = policyObject.getPreauthClaimsCount(policyDetailVO);

                

			

			writer.print(preauthCount);

	      writer.flush();

	      writer.close();

	      return null;

		}catch(Exception exception){

			writer.print("Selected policy is Cancelled/Suspended so Please come from Endorsement.");

		      writer.flush();

		      writer.close();

			log.error(exception.getMessage(), exception);			

			}

        return null;

    }

    /**

     * Returns the PolicyManager session object for invoking methods on it.

     * @return policyManager session object which can be used for method invokation

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

            }//end if(policyManager == null)

        }//end of try

        catch(Exception exp)

        {

            throw new TTKException(exp, "policydetail");

        }//end of catch

        return policyManager;

    }//end getHospitalManagerObject()



    /**

     * Returns the PolicyManager session object for invoking methods on it.

     * @return policyManager session object which can be used for method invokation

     * @exception throws TTKException

     */

    private ValidationRuleManager getValidationRuleManagerObject() throws TTKException

    {

        ValidationRuleManager validationRuleManager = null;

        try

        {

            if(validationRuleManager == null)

            {

                InitialContext ctx = new InitialContext();

                validationRuleManager = (ValidationRuleManager) ctx.lookup("java:global/TTKServices/business.ejb3/ValidationRuleManagerBean!com.ttk.business.webservice.ValidationRuleManager");

            }//end if(policyManager == null)

        }//end of try

        catch(Exception exp)

        {

            throw new TTKException(exp, "policydetail");

        }//end of catch

        return validationRuleManager;

    }//end getHospitalManagerObject()





    /**

     * This method returns the forward path for next view based on the Flow

     *

     * @param strActiveSubLink String current sublink

     * @return strForwardPath String forward path for the next view

     */

    private String getForwardPath(String strActiveSubLink)

    {

        String strForwardPath=null;



        if(strActiveSubLink.equals(strIndividualPolicy))

        {

            strForwardPath=strIndMemberDetails;



        }//end of if(strActiveSubLink.equals(strIndividualPolicy))

        else if(strActiveSubLink.equals(strIndPolicyasGroup))

        {

            strForwardPath=strGrpPolicyDetails;



        }//end of if(strActiveSubLink.equals(strIndPolicyasGroup))

        else if(strActiveSubLink.equals(strCorporatePolicy))

        {

            strForwardPath=strCorpMemberDetails;



        } //end of if(strActiveSubLink.equals(strCorporatePolicy))

        else if(strActiveSubLink.equals(strNonCorporatePolicy))

        {

            strForwardPath=strNonCorpMemberDetails;



        }//end of if(strActiveSubLink.equals(strNonCorporatePolicy))
        return strForwardPath;

    }//end of getForwardPath(String strActiveSubLink)





    /**

     * This method returns the forward path for next view based on the Flow

     *

     * @param strActiveSubLink String current sublink

     * @return strForwardPath String forward path for the next view

     */

    private String getMemberDetailPath(String strActiveSubLink)

    {

        String strForwardPath=null;



        if(strActiveSubLink.equals(strIndividualPolicy))

        {

            strForwardPath=strIndAddMemberDetails;



        }//end of if(strActiveSubLink.equals(strIndividualPolicy))

        else if(strActiveSubLink.equals(strIndPolicyasGroup))

        {

            strForwardPath=strGrpAddMemberDetails;



        }//end of if(strActiveSubLink.equals(strIndPolicyasGroup))

        else if(strActiveSubLink.equals(strCorporatePolicy))

        {

            strForwardPath=strCorAddMemberDetails;



        } //end of if(strActiveSubLink.equals(strCorporatePolicy))

        else if(strActiveSubLink.equals(strNonCorporatePolicy))

        {

            strForwardPath=strNonCorAddMemberDetails;



        }//end of if(strActiveSubLink.equals(strNonCorporatePolicy))

        else if(strActiveSubLink.equals("Corporate Policy")){
        	strForwardPath="strCorporatePolicy";
        }
        return strForwardPath;

    }//end of getMemberDetailPath(String strActiveSubLink)





    /**

     * This method returns the Tree name to get the data from the session

     *

     * @param strActiveSubLink String current sublink

     * @return strForwardPath String forward path for the next view

     */

    private String getTreeName(String strActiveSubLink)

    {

        String strTreeName=null;



        if(strActiveSubLink.equals(strIndividualPolicy) ||strActiveSubLink.equals(strIndPolicyasGroup))

        {

            strTreeName="MemberTree";

        }//end of if(strActiveSubLink.equals(strIndividualPolicy))

        else if(strActiveSubLink.equals(strCorporatePolicy) ||strActiveSubLink.equals(strNonCorporatePolicy))

        {

            strTreeName="CorporateMemberTree";

        } //end of if(strActiveSubLink.equals(strCorporatePolicy))

        return strTreeName;

    }//end of getForwardPath(String strActiveSubLink)





    /**

     * This method returns the SubLink code based on the Policy flow

     *

     * @param strActiveSubLink String current sublink

     * @return strForwardPath String forward path for the next view

     */

    private String getSubLinkCode(String strActiveSubLink)

    {

        String strSubLinkCode=null;



        if(strActiveSubLink.equals(strIndividualPolicy))

        {

            strSubLinkCode="IP";

        }//end of if(strActiveSubLink.equals(strIndividualPolicy))

        else if(strActiveSubLink.equals(strIndPolicyasGroup))

        {

            strSubLinkCode="IG";

        }//end of if(strActiveSubLink.equals(strIndPolicyasGroup))

        else if(strActiveSubLink.equals(strCorporatePolicy))

        {

            strSubLinkCode="CP";

        } //end of if(strActiveSubLink.equals(strCorporatePolicy))

        else if(strActiveSubLink.equals(strNonCorporatePolicy))

        {

            strSubLinkCode="NC";

        }//end of if(strActiveSubLink.equals(strNonCorporatePolicy))

        return strSubLinkCode;

    }//end of getForwardPath(String strActiveSubLink)



    /**

     * This method returns the Policy Type Code based on the Flow

     *

     * @param strActiveSubLink String current sublink

     * @return strForwardPath String forward path for the next view

     */

    private String getPolicyTypeCode(String strActiveSubLink)

    {

        String strPolicyTypeCode=null;



        if(strActiveSubLink.equals(strIndividualPolicy))

        {

            strPolicyTypeCode="IND";

        }//end of if(strActiveSubLink.equals(strIndividualPolicy))

        else if(strActiveSubLink.equals(strIndPolicyasGroup))

        {

            strPolicyTypeCode="ING";

        }//end of if(strActiveSubLink.equals(strIndPolicyasGroup))

        else if(strActiveSubLink.equals(strCorporatePolicy))

        {

            strPolicyTypeCode="COR";

        } //end of if(strActiveSubLink.equals(strCorporatePolicy))

        else if(strActiveSubLink.equals(strNonCorporatePolicy))

        {

            strPolicyTypeCode="NCR";

        }//end of if(strActiveSubLink.equals(strNonCorporatePolicy))

        return strPolicyTypeCode;

    }//end of getForwardPath(String strActiveSubLink)

    

    

    

    

    public ActionForward onAuditLogsIcon(ActionMapping mapping,ActionForm form,HttpServletRequest request,

            HttpServletResponse response) throws Exception{

				

    	 String strActiveSubLink=TTKCommon.getActiveSubLink(request);

    	 HttpSession session = request.getSession();

    	 try

 		{

    		  MemberManager memberManager=this.getMemberManager();

    	 TableData tableData =TTKCommon.getTableData(request);

			

			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){

				((DynaActionForm)form).initialize(mapping);//reset the form data

			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))

			DynaActionForm frmHAAD=(DynaActionForm)form;

			

			  String strFlag = "";

              String strSeqID = "";

    		  TreeData treeData = TTKCommon.getTreeData(request);

    		  String strSelectedRoot = (String)frmHAAD.get("selectedroot");

              String strSelectedNode = (String)frmHAAD.get("selectednode");

              MemberVO memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);

              if(strSelectedNode==null ||strSelectedNode.equals(""))

              {

                  strFlag = "GROUP";

                  strSeqID = memberVO.getPolicyGroupSeqID().toString();

              }//end of (strSelectedNode==null || strSelectedNode.equals("")

              else

              {

                  strFlag = "MEMBER";

                  strSeqID = memberVO.getMemberSeqID().toString();

              }//end of else

			

              String reforward = request.getParameter("reforward");	

			

			tableData = new TableData();

			tableData.createTableInfo("AuditLogsTable",new ArrayList());

			request.getSession().setAttribute("tableData",tableData);

			

			String strReletion = memberManager.getMemberRelation(strSeqID);

			

			 frmHAAD.set("reforward",reforward);

			 frmHAAD.set("grpMemSeqId",strSeqID);

			 frmHAAD.set("alRelationShip",strReletion);

			session.setAttribute("frmMember", frmHAAD);

    	 

    	 if("groupMember".equals(reforward)){

    		 

    		 return this.getForward("GroupAuditLogsPage",mapping,request);

    	 }

    	 else{

    		 

    		 return this.getForward("MemberAuditLogsPage",mapping,request);

    	 }

    	

    	 

 		}

 		catch(TTKException expTTK)

 		{

 			return this.processExceptions(request, mapping, expTTK);

 		}

    	 

    	 

    }

    

      

    

    

    public ActionForward doSearchAuditLogs(ActionMapping mapping,ActionForm form,HttpServletRequest request,

            HttpServletResponse response) throws Exception{

    	

    	 try{

    		 setLinks(request);

    		  DynaActionForm frmMember=(DynaActionForm)form;

    		 

    		  MemberManager memberManager=this.getMemberManager();

    		 

    		  TableData tableData =TTKCommon.getTableData(request);

    		  

    		  String reforward = frmMember.getString("reforward");

    		String strSeqID = frmMember.getString("grpMemSeqId");

    		

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

  					 if("groupMember".equals(reforward)){

  		  	    		 

  		  	    		 return this.getForward("GroupAuditLogsPage",mapping,request);

  		  	    	 }

  		  	    	 else{

  		  	    		 

  		  	    		 return this.getForward("MemberAuditLogsPage",mapping,request);

  		  	    	 }

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

  				tableData.createTableInfo("AuditLogsTable",null);

  				tableData.setSearchData(this.populateSearchCriteriaAuditLogs((DynaActionForm)form,request));

                  //this.setColumnVisiblity(tableData,(DynaActionForm)form,request);

  				tableData.modifySearchData("search");

  			}//end of else

  			

  			

  			ArrayList alMemberLogList= memberManager.getAuditLogsList(tableData.getSearchData(),strSeqID,reforward);

  			

  			

  			tableData.setData(alMemberLogList, "search");

  			

  			//set the table data object to session

  			request.getSession().setAttribute("tableData",tableData);

  			

  			frmMember.set("reforward",reforward);

  			

  			

  			 if("groupMember".equals(reforward)){

  	    		 

  	    		 return this.getForward("GroupAuditLogsPage",mapping,request);

  	    	 }

  	    	 else{

  	    		 

  	    		 return this.getForward("MemberAuditLogsPage",mapping,request);

  	    	 }

    		 

  			

    	 }

    	 catch(TTKException expTTK)

         {

             return this.processExceptions(request, mapping, expTTK);

         }//end of catch(TTKException expTTK)

         catch(Exception exp)

         {

             return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

         }//end of catch(Exception exp)

    

    }

    

    

	private ArrayList<Object> populateSearchCriteriaAuditLogs(DynaActionForm frmMember,HttpServletRequest request)

    {

    	//build the column names along with their values to be searched

    	ArrayList<Object> alSearchParams = new ArrayList<Object>();

    	alSearchParams.add(frmMember.getString("fieldname"));

    	alSearchParams.add(frmMember.getString("fromDate"));

    	alSearchParams.add(frmMember.getString("toDate"));

    	alSearchParams.add(frmMember.getString("modifiedUserName"));

    	alSearchParams.add(frmMember.getString("modifiedUserRole"));

    	alSearchParams.add(frmMember.getString("custEndorsementNo"));

		alSearchParams.add(TTKCommon.getUserSeqId(request));

    	return alSearchParams;

    }//end of populateSearchCriteria(DynaActionForm frmClaimsList,HttpServletRequest request)

    

	

	

	   public ActionForward doForwardAuditLogs(ActionMapping mapping,ActionForm form,HttpServletRequest request,

				HttpServletResponse response) throws Exception{

	     try{

					log.info("Inside the forward method of MemberUploadAction");

					setLinks(request);

					 DynaActionForm frmMember=(DynaActionForm)form;

					MemberManager memberManager=this.getMemberManager();

					//get the tbale data from session if exists

					

					 String reforward = frmMember.getString("reforward");

					

					TableData tableData =TTKCommon.getTableData(request);

					

					String strSeqID = frmMember.getString("grpMemSeqId");

		    		  

					tableData.modifySearchData(strBackward);//modify the search data

					ArrayList alClaimsList = memberManager.getAuditLogsList(tableData.getSearchData(),strSeqID,reforward);

					tableData.setData(alClaimsList, strBackward);//set the table data

					request.getSession().setAttribute("tableData",tableData);   //set the table data object to session

					frmMember.set("reforward",reforward);

					 if("groupMember".equals(reforward)){

		  	    		 

		  	    		 return this.getForward("GroupAuditLogsPage",mapping,request);

		  	    	 }

		  	    	 else{

		  	    		 

		  	    		 return this.getForward("MemberAuditLogsPage",mapping,request);

		  	    	 }

					}//end of try

					catch(TTKException expTTK)

					{

					return this.processExceptions(request, mapping, expTTK);

					}//end of catch(TTKException expTTK)

					catch(Exception exp)

					{

					return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

					}//end of catch(Exception exp)

					}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

					

	

	

	   public ActionForward doBackwardAuditLogs(ActionMapping mapping,ActionForm form,HttpServletRequest request,

				HttpServletResponse response) throws Exception{

	     try{

			    log.debug("Inside the doForward method of MemberUploadAction");

			      setLinks(request);

			      DynaActionForm frmMember=(DynaActionForm)form;

			    MemberManager memberManager=this.getMemberManager();

			    TableData tableData =TTKCommon.getTableData(request);

			    

			    String strSeqID = frmMember.getString("grpMemSeqId");

			    String reforward = frmMember.getString("reforward");

			    

			    

			//get the tbale data from session if exists

			     tableData.modifySearchData(strForward);//modify the search data

			    ArrayList alClaimsList = memberManager.getAuditLogsList(tableData.getSearchData(),strSeqID,reforward);

			    tableData.setData(alClaimsList, strForward);//set the table data

			    request.getSession().setAttribute("tableData",tableData);   //set the table data object to session

			    frmMember.set("reforward",reforward);

			    if("groupMember".equals(reforward)){

	  	    		 

	  	    		 return this.getForward("GroupAuditLogsPage",mapping,request);

	  	    	 }

	  	    	 else{

	  	    		 

	  	    		 return this.getForward("MemberAuditLogsPage",mapping,request);

	  	    	 }

			    }//end of try

			    catch(TTKException expTTK)

			        {

			      return this.processExceptions(request, mapping, expTTK);

			        }//end of catch(TTKException expTTK)

			     catch(Exception exp)

			    {

			     return this.processExceptions(request, mapping, new TTKException(exp,"groupdetail"));

			    }//end of catch(Exception exp)

	   }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

}//end of class MemberAction





