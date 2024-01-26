/**
* @ (#) MemberAction.java Jul 11th, 2006
* Project 		: TTK HealthCare Services
* File 			: MemberAction.java
* Author 		: Krishna K H
* Company 		: Span Systems Corporation
* Date Created 	: Jul 11th, 2006
*
* @author 		: Krishna K H
* Modified by 	: Ramakrishna K M
* Modified date : Feb 19th,2008
* Reason 		: Removed unwanted methods
*/

package com.ttk.action.onlineforms;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import org.dom4j.Document;

import com.ttk.action.TTKAction;
import com.ttk.action.tree.Node;
import com.ttk.action.tree.TreeData;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.OnlineAccessPolicyVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import com.ttk.dto.common.Toolbar;

import formdef.plugin.util.FormUtils;
/**
 * This class is reusable one, used to search members for individual policy and corporate policies in online forms
 * flow in tree Structure.
 */
public class OnlineMemberAction extends TTKAction
{
	private static final Logger log = Logger.getLogger( OnlineMemberAction.class );

	private static final String strSwitchType="ENM";
    private static final String strBackward="Backward";
    private static final String strForward="Forward";
    private static final String strReportdisplay="reportdisplay";

    //Exception Message Identifier
    private static final String strMember="groupdetail";
    private static final String strFailure="failure";
	//added for KOC-1267
	private static final String strClaims="Claims";
    /**
      * This method is used to initialize the search grid.
      * Finally it forwards to the appropriate view based on the specified forward mappings
      *
      * @param mapping The ActionMapping used to select this instance
      * @param form The optional ActionForm bean for this request (if any)
      * @param request The HTTP request we are processing
      * @param response The HTTP response we are creating
      * @return ActionForward Where the control will be forwarded, after this request is processed
      * @throws TTKException if any error occurs
      */
     public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws TTKException{
    	 try{
    		 setOnlineLinks(request);
    		 log.info("Inside OnlineMemberAction doDefault");
    		 DynaActionForm frmMember=(DynaActionForm)form;
    		 int CANCEL_ENROLLMENT_ICON = 3;
    		 int UPLOAD_ENROLLMENT_ICON = 1;//1352
    		 int VIEW_FILES_UPLOAD_ENROLLMENT_ICON=6;//1352 
    		 
    		 int CANCEL_ENROLLMENT_ICON_CHILD = 0;
    		 int OPTOUT1=5;
			 int E_CARD_ICON = 1;
    		 OnlineAccessPolicyVO onlinePolicyVO =null;
    		 ArrayList alMembers = null;
    		 String strCaption="";
			 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    		 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		 
    		 if(userSecurityProfile.getLoginType().equals("H") || userSecurityProfile.getLoginType().equals("B")){
    			 onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
    			 if(onlinePolicyVO==null)
    			 {
    				 TTKException expTTK = new TTKException();
    				 expTTK.setMessage("error.enrollment.required");
    				 frmMember.set("display","display");//this attribute is used in JSP to show the error message.
    				 throw expTTK;
    			 }//end of if(onlinePolicyVO==null)
    		 }//end of if(userSecurityProfile.getLoginType().equals("H"))

    		 TreeData treeData = TTKCommon.getTreeData(request);
    		 String strTreeName = "OnlineMemberTree";
    		 OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();

    		 if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    		 {
    			 ((DynaActionForm)form).initialize(mapping);//reset the form data
    		 }//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))

    		 if(userSecurityProfile.getLoginType().equals("H") || userSecurityProfile.getLoginType().equals("B")){
    			 strCaption="["+TTKCommon.checkNull(onlinePolicyVO.getPolicyNbr())+"]";
    			 frmMember.set("empaddyn",TTKCommon.checkNull(onlinePolicyVO.getEmployeeAddYN()));
    			 frmMember.set("policyStatus",TTKCommon.checkNull(onlinePolicyVO.getPolicyStatusTypeID()));
    		 }//end of if(userSecurityProfile.getLoginType().equals("H"))
	
    		 else
    		 {
    			 strCaption="["+TTKCommon.checkNull(userSecurityProfile.getPolicyNo())+"]";
    		 }//end of else
    		 treeData = new TreeData();
    		 //create the required tree
    		 treeData.createTreeInfo(strTreeName,null,true);
    		 //check the permision and set the tree for not to display respective icon
    		 this.checkTreePermission(request,treeData);
    		 request.getSession().setAttribute("treeData",null);
    		 frmMember.set("caption",strCaption);
    		 treeData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,userSecurityProfile.getLoginType()));
    		 treeData.modifySearchData("search");
    		 frmMember.set("display","display");//this attribute is used in JSP to show the error message.
    		
    		 if(!(userSecurityProfile.getLoginType().equals("H") || (userSecurityProfile.getLoginType().equals("B")))){
    			 //get the meberlist from database
    			 alMembers = onlineAccessManagerObject.getOnlineEnrollmentList(treeData.getSearchData());
    			// treeData.setSearchData(null);

    			 frmMember.set("display",null);//this attribute is used in JSP to show the error message.
    			 treeData.setRootData(alMembers);
    			 MemberVO tmpMemberVO = (MemberVO)alMembers.get(0);
 //1352
    			 if(TTKCommon.getActiveSubLink(request).equalsIgnoreCase("Enrollment") && userSecurityProfile.getLoginType().equals("E"))
    			 {
    				 if(tmpMemberVO.getUploadYN().equalsIgnoreCase("Y"))
    				 {
    			
    			 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,true);//koc1352
    			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,true);//koc1352
    				 }
    				 else if(tmpMemberVO.getUploadYN().equalsIgnoreCase("N"))
					 {
					 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,false);//koc1352
	    			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,false);//koc1352
			 
					 }
    		 }
    			 else{
					 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,false);//koc1352
        			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,false);//koc1352
    			 }//1352
    			if(tmpMemberVO.getPolicyOpted()!=null)
				 {
					 request.getSession().setAttribute("PolicyOpted",tmpMemberVO.getPolicyOpted());
					//request.setAttribute("PolicyOpted",tmpMemberVO.getPolicyOpted());
				 }
				 if(tmpMemberVO.getEnrollmentNbr()!=null)
				 {
					 request.getSession().setAttribute("OnMemActionEnrNo",tmpMemberVO.getEnrollmentNbr());
					 //request.setAttribute("OnMemActionEnrNo",tmpMemberVO.getEnrollmentNbr());
				 }
				 if(tmpMemberVO.getEnrollmentNbr().contains("I310-02")||tmpMemberVO.getEnrollmentNbr().contains("I310-002")||(!(tmpMemberVO.getEnrollmentNbr().contains("I310"))))    			 {
					 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(OPTOUT1,false);
				 }
				 if(tmpMemberVO.getPolicyOpted().equalsIgnoreCase("N"))
				 {
					treeData.getTreeSetting().getRootNodeSetting().setIconVisible(E_CARD_ICON,false);
				 }

    			 //End of Koc-1216

    			 //Added for IBM Age CR
				 if(tmpMemberVO.getPolicyStartDate()!=null)
				 {
					String FormattedDate = df.format(tmpMemberVO.getPolicyStartDate());
					request.getSession().setAttribute("policyDate",FormattedDate);
				 }
				 if(tmpMemberVO.getDateOfJoining()!=null)
				 {
				   String FormattedJoiningDate = df.format(tmpMemberVO.getDateOfJoining());
				   request.getSession().setAttribute("empDateOfJoining", FormattedJoiningDate);
				 }
				 //Ended

    			 ArrayList alNodeMembers = getDependants(request,tmpMemberVO.getPolicyGroupSeqID());
    			 tmpMemberVO.setMemberList(alNodeMembers);
    			 checkCancelMember(treeData,alNodeMembers,request);
    			 treeData.setSelectedRoot(0);
    			 frmMember.set("caption", "["+TTKCommon.checkNull(tmpMemberVO.getPolicyNbr())+"]");
    		 }//end of if(!userSecurityProfile.getLoginType().equals("H"))
    		 else{
    			 alMembers = onlineAccessManagerObject.getMemberList(treeData.getSearchData());
    			 if(alMembers!=null && alMembers.size()>0)
    			 {	 
				  //Added for KOC -1216
				  MemberVO tmpMemberVO = (MemberVO)alMembers.get(0);
    			 //MemberVO EnrVO = (MemberVO)alMembers.get(2);
    			// String OnMemActionEnrNo = EnrVO.getEnrollmentNbr();
				if(tmpMemberVO.getPolicyOpted()!=null)
    			 {
    				 request.getSession().setAttribute("PolicyOpted",tmpMemberVO.getPolicyOpted());
				 }
    			 if(tmpMemberVO.getEnrollmentNbr()!=null)
    			 {
    				 request.getSession().setAttribute("OnMemActionEnrNo",tmpMemberVO.getEnrollmentNbr());
					}
    			
    			 treeData.setSearchData(alMembers);
                 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(OPTOUT1,false);

				 if(tmpMemberVO.getPolicyStartDate()!=null)
				 {
					  String FormattedDate = df.format(tmpMemberVO.getPolicyStartDate());
					  request.getSession().setAttribute("policyDate",FormattedDate);
				 }
				  if(tmpMemberVO.getDateOfJoining()!=null)
				 {
					 String FormattedJoiningDate = df.format(tmpMemberVO.getDateOfJoining());
					 request.getSession().setAttribute("empDateOfJoining", FormattedJoiningDate);
				 }
				 if(TTKCommon.checkNull(onlinePolicyVO. getGroupCntCancelGenTypeID()).equals("WCN"))
			     {
			        treeData.getTreeSetting().getRootNodeSetting().setIconVisible(
			           CANCEL_ENROLLMENT_ICON,false);
			        treeData.getTreeSetting().getChildNodeSetting().setIconVisible(
			           CANCEL_ENROLLMENT_ICON_CHILD,false);
			     }//end ofif(TTKCommon.checkNull(onlinePolicyVO. getGroupCntCancelGenTypeID()).equals("WCN"))
    			
    			 
    			 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,false);//koc1352
    			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,false);//koc1352
    			 
    			 frmMember.set("display",null);//this attribute is used in JSP to show the error message.
    		 }
    			 else
    			 {
    				 treeData.setSearchData(alMembers);
    				 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(OPTOUT1,false);
    				 if(TTKCommon.checkNull(onlinePolicyVO. getGroupCntCancelGenTypeID()).equals("WCN"))
    			     {
    			        treeData.getTreeSetting().getRootNodeSetting().setIconVisible(
    			           CANCEL_ENROLLMENT_ICON,false);
    			        treeData.getTreeSetting().getChildNodeSetting().setIconVisible(
    			           CANCEL_ENROLLMENT_ICON_CHILD,false);
    			     }//end ofif(TTKCommon.checkNull(onlinePolicyVO. getGroupCntCancelGenTypeID()).equals("WCN"))
        			
        			 
        			 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,false);//koc1352
        			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,false);//koc1352
        			 
        			 frmMember.set("display",null);//this attribute is used in JSP to show the error message.
    			 }
    		
    	}//end of else
//  		 set the tree data object to session
    		 request.getSession().setAttribute("treeData",treeData);
    		 if(userSecurityProfile.getLoginType().equals("B")){
    			 return mapping.findForward("onlinemembersearch");//kocbroker
    		 }
    		 else{
    			 return mapping.findForward("onlinemember");
    		 }
    	 }//end of try
    	 catch(TTKException expTTK)
    	 {
    		 return this.processOnlineExceptions(request, mapping, expTTK);
    	 }//end of catch(TTKException expTTK)
    	 catch(Exception exp)
    	 {
    		 return this.processOnlineExceptions(request, mapping, new TTKException(exp, strMember));
    	 }//end of catch(Exception exp)
     }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

     /**
      * This method is used to navigate to detail screen to edit selected record.
      * Finally it forwards to the appropriate view based on the specified forward mappings
      *
      * @param mapping The ActionMapping used to select this instance
      * @param form The optional ActionForm bean for this request (if any)
      * @param request The HTTP request we are processing
      * @param response The HTTP response we are creating
      * @return ActionForward Where the control will be forwarded, after this request is processed
      * @throws TTKException if any error occurs
      */
     public ActionForward doViewPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws TTKException{
    	 log.info("Inside OnlineMemberAction doViewPolicy");
    	 return doDefault(mapping,form,request,response);
     }//end of doViewPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
      * @throws TTKException if any error occurs
      */
     public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws TTKException{
    	 try{
    		 setOnlineLinks(request);
    		 log.info("Inside OnlineMemberAction doSearch");
    		 DynaActionForm frmMember=(DynaActionForm)form;
    		 TreeData treeData = TTKCommon.getTreeData(request);
    		 String strMemberDetails="onlinemember";
    		 String strTreeName = "OnlineMemberTree";
    		 OnlineAccessPolicyVO onlinePolicyVO =null;
    		 ArrayList alMembers=null;
    		 int CHANGE_PASSWORD_ICON=0;
    		 int MEMBER_DETAILS_ICON = 2;
    		 int CANCEL_ENROLLMENT_ICON = 3;
    		 int CANCEL_ENROLLMENT_ICON_CHILD = 0;
    		 int UPLOAD_ENROLLMENT_ICON=1;//1352
    		 int INTIMATION_ICON=4;
    		 int VIEW_FILES_UPLOAD_ENROLLMENT_ICON=6;//1352
    		 int OPTOUT1=5;
    		 OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
    		 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		 MemberVO memberVO = null;
    		 String strCaption= frmMember.getString("caption");
    		 //if the page number is clicked, display the appropriate page
    		 if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
    		 {
    			 treeData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    			 treeData.setSelectedRoot(-1);
    			 return this.getForward(strMemberDetails, mapping, request);
    		 }//end of if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
    		 //create the required tree
    		 treeData = new TreeData();
    		 treeData.createTreeInfo(strTreeName,null,true);
    		 //check the permision and set the tree for not to display respective icon
    		 this.checkTreePermission(request,treeData);
    		 treeData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,userSecurityProfile.getLoginType()));
    		 treeData.modifySearchData("search");
    		 //get the meberlist from database
    		 if(userSecurityProfile.getLoginType().equals("H") || userSecurityProfile.getLoginType().equals("B")){
    			 onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
    			 alMembers = onlineAccessManagerObject.getMemberList(treeData.getSearchData());

				 // Changes added for CR KOC1183
    			 if(alMembers!=null && alMembers.size()>0)
        		 {
    				 frmMember.set("noteCaption","shownote");
        		 }
    			 // Changes added for CR KOC1183
    		 }//end of if(!userSecurityProfile.getLoginType().equals("H"))
    		 else
    		 {
    			 alMembers = onlineAccessManagerObject.getOnlineEnrollmentList(treeData.getSearchData());
    		 }//end of else
    		 treeData.setData(alMembers, "search");
    		 frmMember.set("caption",strCaption);

    		 if(alMembers!=null && alMembers.size()>0)
    		 {
    			 for(int i=0;i<alMembers.size();i++){
    				 memberVO = (MemberVO)alMembers.get(i);
    				 MemberVO HRtmpMemberVO = (MemberVO)alMembers.get(0);
    				 if(userSecurityProfile.getLoginType().equals("H") || userSecurityProfile.getLoginType().equals("B")){

    					 if(onlinePolicyVO.getPolicyStatusTypeID().equals("POC")||memberVO.getAllowCancYN().equals("N"))
    				       {
    				        treeData.getTreeSetting().getRootNodeSetting().setIconVisible(
    				           CANCEL_ENROLLMENT_ICON,false);
    				        treeData.getTreeSetting().getChildNodeSetting().setIconVisible(
    				           CANCEL_ENROLLMENT_ICON_CHILD,false);
    				       }//end ofif(onlinePolicyVO.getPolicyStatusTypeID().equals("POC")||memberVO.getAllowCancYN().equals("N"))
    					 if(HRtmpMemberVO.getEnrollmentNbr().contains("I310-02")||HRtmpMemberVO.getEnrollmentNbr().contains("I310-002")||(!(HRtmpMemberVO.getEnrollmentNbr().contains("I310"))))    			 {
    						 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(OPTOUT1,false);
    					 }
    					 
    					 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,false);//koc1352
            			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,false);//koc1352
            			 //kocBroker
            			 if(userSecurityProfile.getLoginType().equals("B"))
            			 {
            				 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(MEMBER_DETAILS_ICON,false);//koc1352
            				 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(CHANGE_PASSWORD_ICON,false);//koc1352
            				 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(INTIMATION_ICON,false);//koc1352
            			 }
            			 
    					 
    				 }//end of if(userSecurityProfile.getLoginType().equals("H"))
    				 else {
    					 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(MEMBER_DETAILS_ICON,false);
    					 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(CANCEL_ENROLLMENT_ICON,false);
    					 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(CANCEL_ENROLLMENT_ICON_CHILD,
    							 														false);
    					 
    					 //1352
    					 if(TTKCommon.getActiveSubLink(request).equalsIgnoreCase("Enrollment") && userSecurityProfile.getLoginType().equals("E")  )
    	    			 {
    						 if(HRtmpMemberVO.getUploadYN().equalsIgnoreCase("Y"))
    						 {
    					 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,true);//koc1352
    	    			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,true);//koc1352
    						 }
    						 else if(HRtmpMemberVO.getUploadYN().equalsIgnoreCase("N"))
    						 {
   	    					 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,false);//koc1352
        	    			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,false);//koc1352
    						 }
    	    			 }
    	    			 else{
    	    				 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,false);//koc1352
    	        			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,false);//koc1352
    	    			 }
    					 //1352
    				 }//end of else
    			 }//end of for
    		 }//if(alMembers!=null && alMembers.size()>0)
    		 //set the tree data object to session
    		 request.getSession().setAttribute("treeData",treeData);
    		 return mapping.findForward("onlinemember");
    	 }//end of try
    	 catch(TTKException expTTK)
    	 {
    		 return this.processOnlineExceptions(request, mapping, expTTK);
    	 }//end of catch(TTKException expTTK)
    	 catch(Exception exp)
    	 {
    		 return this.processOnlineExceptions(request, mapping, new TTKException(exp, strMember));
    	 }//end of catch(Exception exp)
     }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

     /**
      * This method is used to show/hide the child nodes
      * Finally it forwards to the appropriate view based on the specified forward mappings
      *
      * @param mapping The ActionMapping used to select this instance
      * @param form The optional ActionForm bean for this request (if any)
      * @param request The HTTP request we are processing
      * @param response The HTTP response we are creating
      * @return ActionForward Where the control will be forwarded, after this request is processed
      * @throws TTKException if any error occurs
      */
     public ActionForward doShowHide(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws TTKException{
    	 try{
    		 setOnlineLinks(request);
    		 log.info("Inside OnlineMemberAction doShowHide");
    		 DynaActionForm frmMember=(DynaActionForm)form;
    		 String strCaption= frmMember.getString("caption");
    		 MemberVO memberVO = null;
    		 TreeData treeData = TTKCommon.getTreeData(request);
    		 OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
    		 int iSelectedRoot = Integer.parseInt((String)frmMember.get("selectedroot"));
    		 //create the required tree
    		 ArrayList alNodeMembers = new ArrayList();
    		 ArrayList<Object> alSearchParam= new ArrayList<Object>();
    		 memberVO = ((MemberVO)treeData.getRootData().get(iSelectedRoot));
    		 //create search parameter to get the dependent list
    		 alSearchParam.add(memberVO.getPolicyGroupSeqID());
    		 alSearchParam.add("CP");
    		 //added cr HOC1138 change
    		 UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)
 					request.getSession().getAttribute("UserSecurityProfile"));
    		// userSecurityProfile.setPolicyGrpSeqID(memberVO.getPolicyGroupSeqID());
    		 //userSecurityProfile.setTemplateName(memberVO.getTemplateName());
    		 userSecurityProfile.setTempPolaciGrpSeqID(memberVO.getPolicyGroupSeqID());
    		 userSecurityProfile.setStrTemplateName(memberVO.getTemplateName());
    		request.getSession().setAttribute("UserSecurityProfile",userSecurityProfile);


    		 //get the dependent list from database
    		 alNodeMembers=onlineAccessManagerObject.getDependentList(alSearchParam);
    		 checkCancelMember(treeData,alNodeMembers,request);
    		 // set the selected root
    		 treeData.setSelectedRoot(iSelectedRoot);
    		 ((MemberVO)treeData.getRootData().get(iSelectedRoot)).setMemberList(alNodeMembers);
    		 treeData.setSelectedRoot(iSelectedRoot);
    		 request.getSession().setAttribute("treeData",treeData);
    		 frmMember.set("caption",strCaption);
    		 return mapping.findForward("onlinemember");
    	 }//end of try
    	 catch(TTKException expTTK)
    	 {
    		 return this.processOnlineExceptions(request, mapping, expTTK);
    	 }//end of catch(TTKException expTTK)
    	 catch(Exception exp)
    	 {
    		 return this.processOnlineExceptions(request, mapping, new TTKException(exp, strMember));
    	 }//end of catch(Exception exp)
     }//end of doShowHide(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     					//HttpServletResponse response)

     /**
      * This method is used to get the next set of records with the given search criteria.
      * Finally it forwards to the appropriate view based on the specified forward mappings
      *
      * @param mapping The ActionMapping used to select this instance
      * @param form The optional ActionForm bean for this request (if any)
      * @param request The HTTP request we are processing
      * @param response The HTTP response we are creating
      * @return ActionForward Where the control will be forwarded, after this request is processed
      * @throws TTKException if any error occurs
      */
     public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws TTKException{
    	 try{
    		 setOnlineLinks(request);
    		 log.info("Inside OnlineMemberAction doForward");
    		 TreeData treeData = TTKCommon.getTreeData(request);
    		 OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
    		 String strMemberDetails="onlinemember";
    		 treeData.modifySearchData(strForward);//modify the search data
    		 //get the meberlist from database
    		 ArrayList alMembers = onlineAccessManagerObject.getMemberList(treeData.getSearchData());
    		 treeData.setData(alMembers, strForward);//set the table data
    		 request.getSession().setAttribute("treeData",treeData);//set the table data object to session
    		 return this.getForward(strMemberDetails, mapping, request);
    	 }//end of try
    	 catch(TTKException expTTK)
    	 {
    		 return this.processOnlineExceptions(request, mapping, expTTK);
    	 }//end of catch(TTKException expTTK)
    	 catch(Exception exp)
    	 {
    		 return this.processOnlineExceptions(request, mapping, new TTKException(exp, strMember));
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
      * @throws TTKException if any error occurs
      */
     public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws TTKException{
    	 try{
    		 setOnlineLinks(request);
    		 log.info("Inside OnlineMemberAction doBackward");
    		 TreeData treeData = TTKCommon.getTreeData(request);
    		 OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
    		 String strMemberDetails="onlinemember";
    		 treeData.modifySearchData(strBackward);//modify the search data
    		 //get the meberlist from database
    		 ArrayList alMembers = onlineAccessManagerObject.getMemberList(treeData.getSearchData());
    		 treeData.setData(alMembers, strBackward);//set the table data
    		 request.getSession().setAttribute("treeData",treeData);//set the table data object to session
    		 return this.getForward(strMemberDetails, mapping, request);
    	 }//end of try
    	 catch(TTKException expTTK)
    	 {
    		 return this.processOnlineExceptions(request, mapping, expTTK);
    	 }//end of catch(TTKException expTTK)
    	 catch(Exception exp)
    	 {
    		 return this.processOnlineExceptions(request, mapping, new TTKException(exp,strMember));
    	 }//end of catch(Exception exp)
     }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

     /**
      * This method is used to generate the E-Card.
      * Finally it forwards to the appropriate view based on the specified forward mappings
      *
      * @param mapping The ActionMapping used to select this instance
      * @param form The optional ActionForm bean for this request (if any)
      * @param request The HTTP request we are processing
      * @param response The HTTP response we are creating
      * @return ActionForward Where the control will be forwarded, after this request is processed
      * @throws TTKException if any error occurs
      */
     public ActionForward doGenerateEcard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws TTKException{
    	 try
    	 {
    		 setOnlineLinks(request);
    		 log.info("Inside OnlineMemberAction doGenerateEcard");
    		 JasperReport jasperReport,emptyReport,jasperReportSub;
    		 JasperPrint jasperPrint;
    		 ByteArrayOutputStream boas=null;
    		 String strPolicyGrSeqID = null;
    		 String jrxmlfile = "";
    		 String strTemplateName = "";
    		 OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
    		 if(TTKCommon.getActiveLink(request).equals("Customer Care")){
    			 strPolicyGrSeqID = (String)request.getParameter("parameter");
    		//	 strTemplateName = (String)request.getParameter("template");	// old
    			 strTemplateName = onlineAccessManagerObject.getTemplateName(strPolicyGrSeqID);	// new
    			
    		 }//end of if(TTKCommon.getActiveLink(request).equals("Customer Care"))
    		 else if(TTKCommon.getActiveLink(request).equals("Online Information")&&TTKCommon.getActiveSubLink(request).equals("Account Info")) {
    			 strPolicyGrSeqID = (String)request.getParameter("parameter");
    			 strTemplateName = (String)request.getParameter("template");
    		 }//end of else if
    		 else{
    			 MemberVO memberVO = null;
    			 DynaActionForm frmMember=(DynaActionForm)form;
    			 TreeData treeData = TTKCommon.getTreeData(request);
    			 request.setAttribute("mode",null);
    			 String strSelectedRoot = (String)frmMember.get("selectedroot");
    			 memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,null);
    			 strPolicyGrSeqID = memberVO.getPolicyGroupSeqID().toString();
    		 strTemplateName = memberVO.getTemplateName();
    		 }//end of else
    		 if(!(TTKCommon.checkNull(strTemplateName).equals("")))
    		 {
    			 jrxmlfile=TTKPropertiesReader.getPropertyValue("Ecards")+"E_"+strTemplateName+".jrxml";
    		 }else
    		 {
    			 if(TTKCommon.getActiveLink(request).equals("Customer Care"))
    			 {
    				 boas=new ByteArrayOutputStream();
    				 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
    				 jasperPrint = JasperFillManager.fillReport(emptyReport, new HashMap(),new JREmptyDataSource());
    				 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
    				 request.setAttribute("boas",boas);
    				 return mapping.findForward(strReportdisplay);
    			 }//end of if(TTKCommon.getActiveLink(request).equals("Customer Care")){
    			 else {
    				 boas=new ByteArrayOutputStream();
    				 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyCardReprot.jrxml");
    				 jasperPrint = JasperFillManager.fillReport(emptyReport, new HashMap<Object,Object>(),new JREmptyDataSource());
    				 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
    				 request.setAttribute("boas",boas);
    				 return mapping.findForward(strReportdisplay);
    			 }//end of else
    		 }//end of else

    		 try{
    			 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
    			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
    			 HashMap<String,Object> hashMap = new HashMap<String,Object>();
    			 long lngEmpty = 0;
    			 boas=new ByteArrayOutputStream();
    			
    			 TTKReportDataSource ttkReportDataSource = new TTKReportDataSource("CardPrint",strPolicyGrSeqID,lngEmpty);
    			 if(strPolicyGrSeqID.equals("")||strPolicyGrSeqID==null)
    			 {
    				 jasperPrint = JasperFillManager.fillReport(emptyReport, hashMap,new JREmptyDataSource());
    			 }//end of if(strPolicyGrSeqID.equals("")||strPolicyGrSeqID==null)
    			 else if(ttkReportDataSource.getResultData().next())
    			 {	 
    				 String emp_no = ttkReportDataSource.getResultData().getString("emp_no");	
    				 String deduc = ttkReportDataSource.getResultData().getString("deduc");	
    				 System.out.println("e-card :TemplateName ->"+strTemplateName);
    				 System.out.println("e-card :PolicyGrSeqID ->"+strPolicyGrSeqID);
    				 System.out.println("e-card :emp_no ->"+emp_no);
    				 System.out.println("e-card :deduc ->"+deduc);
    				 ttkReportDataSource.getResultData().beforeFirst();
    				 if(strTemplateName.equalsIgnoreCase("NC_APTRANSCO"))
    				 {
    					 jasperReportSub = JasperCompileManager.compileReport("ecardjrxml/E_NC_APTRANSCO_subreport.jrxml");
    					 hashMap.put("MyDatasource",new TTKReportDataSource("CardPrint",strPolicyGrSeqID,lngEmpty));
    					 hashMap.put("APTranscoSub",jasperReportSub);
    					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
    				 }else
    				 {
    					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
    				 }//end of else
    			 }//end of else if(!(new TTKReportDataSource("CardPrint",strPolicyGrSeqID).getResultData().next()))
    			 else
    			 {
    				 jasperPrint = JasperFillManager.fillReport(emptyReport, hashMap,new JREmptyDataSource());
    			 }//end of else
    			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
    			 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
    			 request.setAttribute("boas",boas);
    		 }//end of try
    		 catch (JRException e)
    		 {
    			 e.printStackTrace();
    		 }//end of catch(JRException e)
    		 catch (Exception e)
    		 {
    			 e.printStackTrace();
    		 }//end of catch (Exception e)
    		 return mapping.findForward(strReportdisplay);
    	 }//end of try
    	 catch(TTKException expTTK)
    	 {
    		 return this.processOnlineExceptions(request, mapping, expTTK);
    	 }//end of catch(TTKException expTTK)
    	 catch(Exception exp)
    	 {
    		 return this.processOnlineExceptions(request, mapping, new TTKException(exp, strMember));
    	 }//end of catch(Exception exp)
     }//end of doGenerateEcard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

     /**
      * This method is used to generate the E-Card.
      * Finally it forwards to the appropriate view based on the specified forward mappings
      *
      * @param mapping The ActionMapping used to select this instance
      * @param form The optional ActionForm bean for this request (if any)
      * @param request The HTTP request we are processing
      * @param response The HTTP response we are creating
      * @return ActionForward Where the control will be forwarded, after this request is processed
      * @throws TTKException if any error occurs
      */
     public ActionForward doECards(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws TTKException{
    	 try
    	 {
    		 setOnlineLinks(request);
    		 log.info("Inside OnlineMemberAction doECards");
    		 JasperReport jasperReport,emptyReport,jasperReportSub;
    		 JasperPrint jasperPrint=null;
    		 ByteArrayOutputStream boas=null;
    		 String strPolicyGrSeqID = null;
    		 String jrxmlfile = "";
    		 String strTemplateName = "";
    		 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		 strPolicyGrSeqID = userSecurityProfile.getPolicyGrpSeqID().toString();
    		 strTemplateName = userSecurityProfile.getTemplateName();
    		 if(!(TTKCommon.checkNull(strTemplateName).equals("")))
    		 {
    			 jrxmlfile=TTKPropertiesReader.getPropertyValue("Ecards")+"E_"+strTemplateName+".jrxml";//"COM_CM_G.jrxml";
    		 }else
    		 {
    			 boas=new ByteArrayOutputStream();
    			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyCardReprot.jrxml");
    			 jasperPrint = JasperFillManager.fillReport(emptyReport, new HashMap(),new JREmptyDataSource());
    			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
    			 request.setAttribute("boas",boas);
    			 return mapping.findForward(strReportdisplay);
    		 }//end of else
    		 try{
    			 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
    			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
    			 HashMap hashMap = new HashMap();
    			 long lngEmpty = 0;
    			 boas=new ByteArrayOutputStream();
    			 TTKReportDataSource ttkReportDataSource = new TTKReportDataSource("CardPrint",strPolicyGrSeqID,lngEmpty);
    			 if(ttkReportDataSource.getResultData().next())
    			 {
    				 ttkReportDataSource.getResultData().beforeFirst();
    				 if(strTemplateName.equalsIgnoreCase("NC_APTRANSCO"))
    				 {
    					 jasperReportSub = JasperCompileManager.compileReport("ecardjrxml/E_NC_APTRANSCO_subreport.jrxml");
    					 hashMap.put("MyDatasource",new TTKReportDataSource("CardPrint",strPolicyGrSeqID,lngEmpty));
    					 hashMap.put("APTranscoSub",jasperReportSub);
    					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
    				 }else
    				 {
    					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
    				 }//end of else
    			 }//end of else if(!(new TTKReportDataSource("CardPrint",strPolicyGrSeqID).getResultData().next()))
    			 else if(strPolicyGrSeqID.equals("")||strPolicyGrSeqID==null)
    			 {
    				 jasperPrint = JasperFillManager.fillReport(emptyReport, hashMap,new JREmptyDataSource());
    			 }//end of if(strPolicyGrSeqID.equals("")||strPolicyGrSeqID==null)
    			 /*else if(!(ttkReportDataSource.getResultData().next()))
    			 {
    				 jasperPrint = JasperFillManager.fillReport(emptyReport, hashMap,new JREmptyDataSource());
    			 }//end of else if(!(new TTKReportDataSource("CardPrint",strPolicyGrSeqID).getResultData().next()))
    			 else
    			 {
    				 ttkReportDataSource.getResultData().beforeFirst();
    				 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
    			 }//end of else*/
    			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
    			 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
    			 request.setAttribute("boas",boas);
    		 }//end of try
    		 catch (JRException e)
    		 {
    			 e.printStackTrace();
    		 }//end of catch(JRException e)
    		 catch (Exception e)
    		 {
    			 e.printStackTrace();
    		 }//end of catch (Exception e)
    		 return mapping.findForward(strReportdisplay);
    	 }//end of try
    	 catch(TTKException expTTK)
    	 {
    		 return this.processOnlineExceptions(request, mapping, expTTK);
    	 }//end of catch(TTKException expTTK)
    	 catch(Exception exp)
    	 {
    		 return this.processOnlineExceptions(request, mapping, new TTKException(exp, strMember));
    	 }//end of catch(Exception exp)
     }//end of doECards(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

     /** this mathod added for cr change
      * This method is used to generate the E-Card.
      * Finally it forwards to the appropriate view based on the specified forward mappings
      *
      * @param mapping The ActionMapping used to select this instance
      * @param form The optional ActionForm bean for this request (if any)
      * @param request The HTTP request we are processing
      * @param response The HTTP response we are creating
      * @return ActionForward Where the control will be forwarded, after this request is processed
      * @throws TTKException if any error occurs
      */
     public ActionForward doIECards(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws TTKException{
    	 try
    	 {
    		 setOnlineLinks(request);
    		 log.info("Inside OnlineMemberAction doECards");
    		 DynaActionForm frmOnlineDetails=(DynaActionForm)form;
     		MemberVO memberVO =null;
     		 TreeData treeData = TTKCommon.getTreeData(request);
     		Document historyDoc=null;
     		String strClaimantName = "";
     		frmOnlineDetails.set("selectedroot",TTKCommon.checkNull(request.getParameter("selectedroot")));
     		frmOnlineDetails.set("selectednode",TTKCommon.checkNull(request.getParameter("selectednode")));
     		String strSelectedRoot = (String)frmOnlineDetails.get("selectedroot");
 			String strSelectedNode = (String)frmOnlineDetails.get("selectednode");
    		 memberVO = (MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);

    		 UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)
 					request.getSession().getAttribute("UserSecurityProfile"));
    		 //Long memseqid=memberVO.getMemberSeqID();
    		 Long memseqid=userSecurityProfile.getMemSeqID();
    		JasperReport jasperReport,emptyReport,jasperReportSub;
    		 JasperPrint jasperPrint=null;
    		 ByteArrayOutputStream boas=null;
    		 String strPolicyGrSeqID = null;
    		 String jrxmlfile = "";
    		 String strTemplateName = "";
    		// UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		 //strPolicyGrSeqID = userSecurityProfile.getPolicyGrpSeqID().toString();
    		 //strTemplateName = userSecurityProfile.getTemplateName();
    		 if(userSecurityProfile.getLoginType().equals("E"))
    		 {
    		 strPolicyGrSeqID = userSecurityProfile.getPolicyGrpSeqID().toString();
    		 strTemplateName = userSecurityProfile.getTemplateName();
    		 }
    		 else if(userSecurityProfile.getLoginType().equals("H"))
    		 {
    			 strPolicyGrSeqID=userSecurityProfile.getTempPolaciGrpSeqID().toString();
    			 strTemplateName=userSecurityProfile.getStrTemplateName();
    		 }
    		 if(!(TTKCommon.checkNull(strTemplateName).equals("")))
    		 {
    			 jrxmlfile=TTKPropertiesReader.getPropertyValue("Ecards")+"E_"+strTemplateName+".jrxml";//"COM_CM_G.jrxml";
    		 }else
    		 {
    			 boas=new ByteArrayOutputStream();
    			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyCardReprot.jrxml");
    			 jasperPrint = JasperFillManager.fillReport(emptyReport, new HashMap(),new JREmptyDataSource());
    			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
    			 request.setAttribute("boas",boas);
    			 return mapping.findForward(strReportdisplay);
    		 }//end of else
    		 try{
    			 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
    			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
    			 HashMap hashMap = new HashMap();
    			 long lngEmpty = 0;
    			 boas=new ByteArrayOutputStream();
    			 TTKReportDataSource ttkReportDataSource = new TTKReportDataSource("CardPrint1",strPolicyGrSeqID,memseqid,lngEmpty);
    			 if(ttkReportDataSource.getResultData().next())
    			 {
    				 ttkReportDataSource.getResultData().beforeFirst();
    				 if(strTemplateName.equalsIgnoreCase("NC_APTRANSCO"))
    				 {
    					 jasperReportSub = JasperCompileManager.compileReport("ecardjrxml/E_NC_APTRANSCO_subreport.jrxml");
    					 hashMap.put("MyDatasource",new TTKReportDataSource("CardPrint1",strPolicyGrSeqID,memseqid,lngEmpty));
    					 hashMap.put("APTranscoSub",jasperReportSub);
    					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
    				 }else
    				 {
    					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
    				 }//end of else
    			 }//end of else if(!(new TTKReportDataSource("CardPrint",strPolicyGrSeqID).getResultData().next()))
    			 else if(strPolicyGrSeqID.equals("")||strPolicyGrSeqID==null)
    			 {
    				 jasperPrint = JasperFillManager.fillReport(emptyReport, hashMap,new JREmptyDataSource());
    			 }//end of if(strPolicyGrSeqID.equals("")||strPolicyGrSeqID==null)
    			 /*else if(!(ttkReportDataSource.getResultData().next()))
    			 {
    				 jasperPrint = JasperFillManager.fillReport(emptyReport, hashMap,new JREmptyDataSource());
    			 }//end of else if(!(new TTKReportDataSource("CardPrint",strPolicyGrSeqID).getResultData().next()))
    			 else
    			 {
    				 ttkReportDataSource.getResultData().beforeFirst();
    				 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
    			 }//end of else*/
    			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
    			 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
    			 request.setAttribute("boas",boas);
    		 }//end of try
    		 catch (JRException e)
    		 {
    			 e.printStackTrace();
    		 }//end of catch(JRException e)
    		 catch (Exception e)
    		 {
    			 e.printStackTrace();
    		 }//end of catch (Exception e)
    		 return mapping.findForward(strReportdisplay);
    	 }//end of try
    	 catch(TTKException expTTK)
    	 {
    		 return this.processOnlineExceptions(request, mapping, expTTK);
    	 }//end of catch(TTKException expTTK)
    	 catch(Exception exp)
    	 {
    		 return this.processOnlineExceptions(request, mapping, new TTKException(exp, strMember));
    	 }//end of catch(Exception exp)
     }//end of doECards(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


     /**
      * This method is used to navigate to detail screen to edit selected record.
      * Finally it forwards to the appropriate view based on the specified forward mappings
      *
      * @param mapping The ActionMapping used to select this instance
      * @param form The optional ActionForm bean for this request (if any)
      * @param request The HTTP request we are processing
      * @param response The HTTP response we are creating
      * @return ActionForward Where the control will be forwarded, after this request is processed
      * @throws TTKException if any error occurs
      */
     public ActionForward doViewMemberDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws TTKException{
    	 try{
    		 setOnlineLinks(request);
    		 log.info("Inside OnlineMemberAction doViewMemberDetails");
			  //added for KOC-1267
    		 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
        	 request.setAttribute("LoginType",userSecurityProfile.getLoginType());
    		 request.setAttribute("UserId",userSecurityProfile.getUSER_ID());
    		 //ended.		
        	 DynaActionForm frmHistoryDetail=(DynaActionForm)form;
    		 String strSubLink = TTKCommon.getActiveSubLink(request);
    		 MemberVO memberVO =null;
    		 TreeData treeData = TTKCommon.getTreeData(request);
    		 Document historyDoc=null;
    		 String strClaimantName = "";
    		 String strMemberDetails ="";
    		 String strHistoryType = "";
    		 frmHistoryDetail.set("selectedroot",TTKCommon.checkNull(request.getParameter("selectedroot")));
    		 frmHistoryDetail.set("selectednode",TTKCommon.checkNull(request.getParameter("selectednode")));
    		 String strSelectedRoot = (String)frmHistoryDetail.get("selectedroot");
    		 String strSelectedNode = (String)frmHistoryDetail.get("selectednode");
    		 if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
    		 {
    			 memberVO = (MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
    			 String strName1 =  memberVO.getName();
    			 strClaimantName =  strName1.substring(strName1.indexOf("/")+1,strName1.lastIndexOf("/")-1).trim();
    		 }//end of if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
    		 OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
    		 String strCaption="";
    		 strCaption="[ "+strClaimantName+" ]"+"[ "+TTKCommon.checkNull(memberVO.getEnrollmentID())+" ]";
    		 if(strSubLink.equals("Pre-Auth"))
    		 {
    			 strMemberDetails="onlinepreauthdetails";
    			 strHistoryType="HPR";
    		 }//end of if(strSubLink.equals("Pre-Auth"))
    		 else if(strSubLink.equals("Claims"))
    		 {
    			 strMemberDetails="onlineclaimdetails";
    			 strHistoryType="HCL";
    		 }//end of else if(strSubLink.equals("Claims"))
    		 historyDoc=onlineAccessManagerObject.getHistory(strHistoryType,memberVO.getMemberSeqID(),
    				 memberVO.getEnrollmentID());
			  //Added for KOC-1267 
    		 this.documentViewer(request,memberVO);//added for dms_reference
    		 //Ended.		 
    		 request.setAttribute("historyDoc",historyDoc);
    		 frmHistoryDetail.set("caption",strCaption);
    		 return mapping.findForward(strMemberDetails);
    	 }//end of try
    	 catch(TTKException expTTK)
    	 {
    		 return this.processOnlineExceptions(request, mapping, expTTK);
    	 }//end of catch(TTKException expTTK)
    	 catch(Exception exp)
    	 {
    		 return this.processOnlineExceptions(request, mapping, new TTKException(exp, strMember));
    	 }//end of catch(Exception exp)
     }//end of doViewMemberDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     							//HttpServletResponse response)
								
								
	  /**added for KOC-1267
  	 * This method for document viewer information
  	 * @param request HttpServletRequest object which contains Online Member information.
  	 * @param preAuthDetailVO PreAuthDetailVO object which contains Online Member information.
  	 * @exception throws TTKException
  	 */
  	private void documentViewer(HttpServletRequest request,MemberVO memberVO) throws TTKException
  	{
  		ArrayList<String> alDocviewParams = new ArrayList<String>();
  		alDocviewParams.add("leftlink="+TTKCommon.getActiveSubLink(request));
  		if(TTKCommon.getActiveSubLink(request).equalsIgnoreCase(strClaims)){
  		}//end of else

  		if(request.getSession().getAttribute("toolbar")!=null)
  		{
  			((Toolbar)request.getSession().getAttribute("toolbar")).setDocViewParams(alDocviewParams);
  		}//end of if(request.getSession().getAttribute("toolbar")!=null)
  	}//end of documentViewer(HttpServletRequest request,MemberVO memberVO)
  
     /**
      * This method is used to navigate to previous screen when close button is clicked.
      * Finally it forwards to the appropriate view based on the specified forward mappings
      *
      * @param mapping The ActionMapping used to select this instance
      * @param form The optional ActionForm bean for this request (if any)
      * @param request The HTTP request we are processing
      * @param response The HTTP response we are creating
      * @return ActionForward Where the control will be forwarded, after this request is processed
      * @throws TTKException if any error occurs
      */
     public ActionForward doCloseEnrollment(ActionMapping mapping,ActionForm form,HttpServletRequest request,
      		HttpServletResponse response) throws TTKException{
      	try{
      		setOnlineLinks(request);
      		log.info("Inside OnlineMemberAction doCloseEnrollment");
      		DynaActionForm frmMember=(DynaActionForm)form;
      		TreeData treeData = TTKCommon.getTreeData(request);
      		OnlineAccessPolicyVO onlinePolicyVO =null;
      		OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
      		onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
      		int CANCEL_ENROLLMENT_ICON = 3;
      		int CANCEL_ENROLLMENT_ICON_CHILD = 0;
      		int UPLOAD_ENROLLMENT_ICON=1;//1352
	    	int VIEW_FILES_UPLOAD_ENROLLMENT_ICON=6;//1352
      		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
      		MemberVO memberVO = null;
      		String strCaption="";
      		ArrayList<Object> alSearchParam = new ArrayList<Object>();
      		ArrayList alMembers = null;
      		onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
      		if(treeData.getSearchData()!=null && treeData.getSearchData().size()>0)
      		{
      			 if(!userSecurityProfile.getLoginType().equals("H"))
      			 {
        			 //get the meberlist from database
      				 alMembers = onlineAccessManagerObject.getOnlineEnrollmentList(treeData.getSearchData());
      			 }//end ofif(!userSecurityProfile.getLoginType().equals("H"))
      			 else
      			 {
      				 //get the meberlist from database
      				  alMembers = onlineAccessManagerObject.getMemberList(treeData.getSearchData());

      			 }//end of else
      			treeData.setRootData(alMembers);
      			if(treeData.getSelectedRoot()>=0)
      			{
      				memberVO = (MemberVO)treeData.getSelectedObject(String.valueOf(treeData.getSelectedRoot()),null);

      				alSearchParam = new ArrayList<Object>();
      				alSearchParam.add(memberVO.getPolicyGroupSeqID());
      				alSearchParam.add(strSwitchType);
      				alSearchParam.add("CP");
      				//get the dependent list from database
      				ArrayList alNodeMembers=onlineAccessManagerObject.getDependentList(alSearchParam);
      				checkCancelMember(treeData,alNodeMembers,request);
      				((MemberVO)treeData.getRootData().get(treeData.getSelectedRoot())).setMemberList(alNodeMembers);
      			}//end of if(treeData.getSelectedRoot()>=0)
      			treeData.setForwardNextLink();
      			if(userSecurityProfile.getLoginType().equals("H"))
      			{
      				strCaption="["+TTKCommon.checkNull(onlinePolicyVO.getPolicyNbr())+"]";
      				if(TTKCommon.checkNull(onlinePolicyVO. getGroupCntCancelGenTypeID()).equals("WCN"))
      			    {
      			        treeData.getTreeSetting().getRootNodeSetting().setIconVisible(
      			           CANCEL_ENROLLMENT_ICON,false);
      			        treeData.getTreeSetting().getChildNodeSetting().setIconVisible(
      			           CANCEL_ENROLLMENT_ICON_CHILD,false);
      			    }//end ofif(TTKCommon.checkNull(onlinePolicyVO. getGroupCntCancelGenTypeID()).equals("WCN"))
      				 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,false);//koc1352
        			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,false);//koc1352
      			}//end of if(userSecurityProfile.getLoginType().equals("H"))
      			else if(userSecurityProfile.getLoginType().equals("E") || userSecurityProfile.getLoginType().equals("OCI"))
      			{
      				strCaption="["+TTKCommon.checkNull(userSecurityProfile.getPolicyNo())+"]";
      				//1352
      				if(TTKCommon.getActiveSubLink(request).equalsIgnoreCase("Enrollment") && userSecurityProfile.getLoginType().equals("E"))
        			 {
      					if(memberVO.getUploadYN().equalsIgnoreCase("Y"))
      					{
					 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,true);//koc1352
        			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,true);//koc1352
     					}
      					 else if(memberVO.getUploadYN().equalsIgnoreCase("N"))
						 {
    		 			 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,false);//koc1352
    	    			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,false);//koc1352
						 }
        			 }
        			 else{
						 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,false);//koc1352
            			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,false);//koc1352
        			 }
      				//1352
      			}//end of else if(userSecurityProfile.getLoginType().equals("E") || userSecurityProfile.getLoginType().equals("OCI"))
      			frmMember.set("caption",strCaption);
      			request.getSession().setAttribute("treeData",treeData);//set the table data object to session
      		}//end of if(treeData.getSearchData()!=null && treeData.getSearchData().size()>0)
      		return this.getForward("onlineAddEnrollment", mapping, request);
      	}//end of try
      	catch(TTKException expTTK)
      	{
      		return this.processOnlineExceptions(request,mapping,expTTK);
      	}//end of catch(ETTKException expTTK)
      	catch(Exception exp)
      	{
      		return this.processOnlineExceptions(request,mapping,new TTKException(exp,strMember));
      	}//end of catch(Exception exp)
      }//end of doCloseEnrollment(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

     /**
      * This method is used to navigate to previous screen when close button is clicked.
      * Finally it forwards to the appropriate view based on the specified forward mappings
      *
      * @param mapping The ActionMapping used to select this instance
      * @param form The optional ActionForm bean for this request (if any)
      * @param request The HTTP request we are processing
      * @param response The HTTP response we are creating
      * @return ActionForward Where the control will be forwarded, after this request is processed
      * @throws TTKException if any error occurs
      */
     public ActionForward doCloseMemberDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
      		HttpServletResponse response) throws TTKException{
      	try{
      		setOnlineLinks(request);
      		log.info("Inside OnlineMemberAction doCloseMemberDetails");
      		DynaActionForm frmMember=(DynaActionForm)form;
      		TreeData treeData = TTKCommon.getTreeData(request);
      		OnlineAccessPolicyVO onlinePolicyVO =null;
      		int UPLOAD_ENROLLMENT_ICON=1;//1352
	    	int VIEW_FILES_UPLOAD_ENROLLMENT_ICON=6;//1352
		
      		OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
      		onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
      		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
      		MemberVO memberVO = null;
      		String strCaption="";
      		ArrayList<Object> alSearchParam = new ArrayList<Object>();
      		if(userSecurityProfile.getLoginType().equals("H") || userSecurityProfile.getLoginType().equals("B"))
      		{
      			strCaption="["+TTKCommon.checkNull(onlinePolicyVO.getPolicyNbr())+"]";
      		}//end of if(userSecurityProfile.getLoginType().equals("H"))
      		else if(userSecurityProfile.getLoginType().equals("E")||userSecurityProfile.getLoginType().equals("I"))
      		{
      			strCaption="["+TTKCommon.checkNull(userSecurityProfile.getPolicyNo())+"]";
      		}//end of if(userSecurityProfile.getLoginType().equals("E"))
      		frmMember.set("caption",strCaption);

      		//Updating the Tree Data for HR login
      		if(userSecurityProfile.getLoginType().equals("H") || userSecurityProfile.getLoginType().equals("B")){
      			if(treeData.getSearchData()!=null && treeData.getSearchData().size()>0)
      			{
      				//get the meberlist from database
      				ArrayList alMembers = onlineAccessManagerObject.getMemberList(treeData.getSearchData());
      				treeData.setRootData(alMembers);
      				if(treeData.getSelectedRoot()>=0)
      				{
      					memberVO = (MemberVO)treeData.getSelectedObject(String.valueOf(treeData.getSelectedRoot()),null);
      					alSearchParam = new ArrayList<Object>();
      					alSearchParam.add(memberVO.getPolicyGroupSeqID());
      					alSearchParam.add(strSwitchType);
      					alSearchParam.add("CP");
      					//get the dependent list from database
      					ArrayList alNodeMembers=onlineAccessManagerObject.getDependentList(alSearchParam);
      					checkCancelMember(treeData,alNodeMembers,request);
      					((MemberVO)treeData.getRootData().get(treeData.getSelectedRoot())).setMemberList(alNodeMembers);
      				}//end of if(treeData.getSelectedRoot()>=0)
      				 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,false);//koc1352
        			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,false);//koc1352
      				treeData.setForwardNextLink();
      				request.getSession().setAttribute("treeData",treeData);//set the table data object to session
      			}//end of if(treeData.getSearchData()!=null && treeData.getSearchData().size()>0)
      		}//end of if(userSecurityProfile.getLoginType().equals("H"))

      		//Updating the Tree Data for Corporate login
      		if(!(userSecurityProfile.getLoginType().equals("H") || (userSecurityProfile.getLoginType().equals("B")))){
      			//get the meberlist from database
      			ArrayList alMembers = onlineAccessManagerObject.getOnlineEnrollmentList(treeData.getSearchData());
      			//treeData.setSearchData(null);
      			frmMember.set("display",null);//this attribute is used in JSP to show the error message.
      			treeData.setRootData(alMembers);
      			MemberVO tmpMemberVO = (MemberVO)alMembers.get(0);
      			ArrayList alNodeMembers = getDependants(request,tmpMemberVO.getPolicyGroupSeqID());
      			tmpMemberVO.setMemberList(alNodeMembers);
				 if(TTKCommon.getActiveSubLink(request).equalsIgnoreCase("Enrollment") && userSecurityProfile.getLoginType().equals("E"))
    			 { //int UPLOAD_ENROLLMENT_ICON=1;//1352
			    		// int VIEW_FILES_UPLOAD_ENROLLMENT_ICON=6;//1352
         			
    				 if(tmpMemberVO.getUploadYN().equalsIgnoreCase("Y"))
    				 {
					treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,true);//koc1352
					treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON, true);//koc1352
    				 }
    				 else  if(tmpMemberVO.getUploadYN().equalsIgnoreCase("N"))
    				 {
    				 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,false);//koc1352
					treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,false);//koc1352
    				 }
    			 }
    			 else{
					 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,false);//koc1352
        			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,false);//koc1352
    			 }//1352
      			checkCancelMember(treeData,alNodeMembers,request);
      			treeData.setSelectedRoot(0);
      			request.getSession().setAttribute("treeData",treeData);//set the table data object to session
      		}//end of if(!userSecurityProfile.getLoginType().equals("H"))
      		return this.getForward("closememberdetails", mapping, request);
      	}//end of try
      	catch(TTKException expTTK)
      	{
      		return this.processOnlineExceptions(request,mapping,expTTK);
      	}//end of catch(ETTKException expTTK)
      	catch(Exception exp)
      	{
      		return this.processOnlineExceptions(request,mapping,new TTKException(exp,strMember));
      	}//end of catch(Exception exp)
      }//end of doCloseMemberDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     								//HttpServletResponse response)

     /**
      * This method is used to navigate to previous screen when close button is clicked.
      * Finally it forwards to the appropriate view based on the specified forward mappings
      *
      * @param mapping The ActionMapping used to select this instance
      * @param form The optional ActionForm bean for this request (if any)
      * @param request The HTTP request we are processing
      * @param response The HTTP response we are creating
      * @return ActionForward Where the control will be forwarded, after this request is processed
      * @throws TTKException if any error occurs
      */
     public ActionForward doCloseMemberDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     		HttpServletResponse response) throws TTKException{
     	try{
     		setOnlineLinks(request);
     		log.info("Inside OnlineMemberAction doCloseMemberDetail");
     		return mapping.findForward("onlinemember");
     	}//end of try
     	catch(TTKException expTTK)
     	{
     		return this.processOnlineExceptions(request,mapping,expTTK);
     	}//end of catch(ETTKException expTTK)
     	catch(Exception exp)
     	{
     		return this.processOnlineExceptions(request,mapping,new TTKException(exp,strMember));
     	}//end of catch(Exception exp)
     }//end of doCloseMemberDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     						      //HttpServletResponse response)

     /**
      * Returns the ArrayList which contains the populated search criteria elements
      * @param request HttpServletRequest object which contains the search parameter that is built
      * @return alSearchParams ArrayList
      */
     private ArrayList<Object> populateSearchCriteria(DynaActionForm frmMember,HttpServletRequest request,String strLoginType)
     {
    	 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)
    	 request.getSession().getAttribute("UserSecurityProfile");
    	 OnlineAccessPolicyVO onlinePolicyVO = null;
    	 //build the column names along with their values to be searched
    	 ArrayList<Object> alSearchParams = new ArrayList<Object>();
    	 if(userSecurityProfile.getLoginType().equals("E"))  //for corporate login
    	 {
    		 alSearchParams.add(userSecurityProfile.getUSER_ID());
			 //alSearchParams.add("120100/05092011");
    		 alSearchParams.add(userSecurityProfile.getPolicyNo());
			 alSearchParams.add(userSecurityProfile.getGroupID());
    		 alSearchParams.add(null);
    		 alSearchParams.add(userSecurityProfile.getLoginType());
    		 alSearchParams.add(TTKCommon.getUserSeqId(request));
    		 alSearchParams.add(null);
    	 }//end of if(userSecurityProfile.getLoginType().equals("E"))
    	 else if(userSecurityProfile.getLoginType().equals("I"))     //for Individual Login
    	 {
    		 alSearchParams.add(null);
    		 alSearchParams.add(userSecurityProfile.getPolicyNo());
    		 alSearchParams.add(null);
    		 alSearchParams.add(userSecurityProfile.getEnrollmentID());
    		 alSearchParams.add(userSecurityProfile.getLoginType());
    		 alSearchParams.add(TTKCommon.getUserSeqId(request));
    		 alSearchParams.add(null);
    	 }//end of else if(userSecurityProfile.getLoginType().equals("I"))
    	 else if(userSecurityProfile.getLoginType().equals("H"))     //for HR Login
    	 {
    		 onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
    		 alSearchParams.add(onlinePolicyVO.getPolicySeqID());
    		 alSearchParams.add(TTKCommon.replaceSingleQots((String)frmMember.get("sEnrollmentNbr")));
    		 alSearchParams.add(TTKCommon.replaceSingleQots((String)frmMember.get("sMemberName")));
    		 alSearchParams.add(TTKCommon.replaceSingleQots((String)frmMember.get("sCorpMemberName")));
    		 alSearchParams.add(TTKCommon.replaceSingleQots((String)frmMember.get("sEmployeeNum")));
    		 alSearchParams.add("ENM");
    		 alSearchParams.add("CP");
    	 }//end of else if(userSecurityProfile.getLoginType().equals("H"))
    	 else if(userSecurityProfile.getLoginType().equals("B"))     //for Broker Login
    	 {
    		 onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
    		 alSearchParams.add(onlinePolicyVO.getPolicySeqID());
    		 alSearchParams.add(TTKCommon.replaceSingleQots((String)frmMember.get("sEnrollmentNbr")));
    		 alSearchParams.add(TTKCommon.replaceSingleQots((String)frmMember.get("sMemberName")));
    		 alSearchParams.add(TTKCommon.replaceSingleQots((String)frmMember.get("sCorpMemberName")));
    		 alSearchParams.add(TTKCommon.replaceSingleQots((String)frmMember.get("sEmployeeNum")));
    		 alSearchParams.add("ENM");
    		 alSearchParams.add("CP");
    	 }//end of else if(userSecurityProfile.getLoginType().equals("B"))
    	 else if(userSecurityProfile.getLoginType().equals("OCI"))     //for Citibank Login
    	 {
    		 alSearchParams.add(null);
    		 alSearchParams.add(userSecurityProfile.getPolicyNo());
    		 alSearchParams.add(null);
    		 alSearchParams.add(userSecurityProfile.getEnrollmentID());
    		 alSearchParams.add(userSecurityProfile.getLoginType());
    		 alSearchParams.add(TTKCommon.getUserSeqId(request));
    		 alSearchParams.add(userSecurityProfile.getCertificateNbr());
    	 }//end of else if(userSecurityProfile.getLoginType().equals("OCI"))
    	 return alSearchParams;
     }//end of populateSearchCriteria(DynaActionForm frmSearchBankAccount)

     /**
      * Returns the onlineAccessManager session object for invoking methods on it.
      * @return onlineAccessManager session object which can be used for method invocation
      * @TTKException throws TTKException
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
    		 }//end if
    	 }//end of try
    	 catch(Exception exp)
    	 {
    		 throw new TTKException(exp, strFailure);
    	 }//end of catch
    	 return onlineAccessManager;
     }//end of getOnlineAccessManagerObject()

     private ArrayList getDependants(HttpServletRequest request,Long policyGroupSeqID) throws TTKException
     {
    	 OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
    	 //create the required tree
    	 ArrayList alNodeMembers = new ArrayList();
    	 ArrayList<Object> alSearchParam= new ArrayList<Object>();

    	 //create search parameter to get the dependent list
    	 alSearchParam.add(policyGroupSeqID);
    	 alSearchParam.add("CP");
    	 //get the dependent list from database
    	 alNodeMembers=onlineAccessManagerObject.getDependentList(alSearchParam);
    	 return alNodeMembers;
     }//end of getDependants(HttpServletRequest request,Long policyGroupSeqID)

     /**
      * Check the ACL permission and set the display property of icons.
      * @param request The HTTP request we are processing
      * @param treeData TreeData for which permission has to check
      */
     private void checkTreePermission(HttpServletRequest request,TreeData treeData) throws TTKException
     {
    	 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    	 String strActiveLink=TTKCommon.getActiveSubLink(request);
    	 log.debug("Active Link is : "+strActiveLink);
    	 int CHANGE_PASSWORD_ICON=0;
    	 int E_CARD_ICON = 1;
    	 int MEMBER_DETAILS_ICON = 2;
    	 int CANCEL_ENROLLMENT_ICON = 3;
    	 int CANCEL_ENROLLMENT_ICON_CHILD = 0;
    	 int INTIMATION_ICON=4;
    	 int OPTOUT1 = 5;//Added for KOC-1216
    	 int UPLOAD_ENROLLMENT_ICON=1;//1352
	    int VIEW_FILES_UPLOAD_ENROLLMENT_ICON=6;//1352

    	 if(!userSecurityProfile.getLoginType().equals("I"))
    	 {
    		 treeData.getTreeSetting().getRootNodeSetting().setIsLink(true);
    		 if(userSecurityProfile.getLoginType().equals("OCI")){
    			 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(MEMBER_DETAILS_ICON,false);
    		 }//end of if(userSecurityProfile.getLoginType().equals("OCI"))
    	 }//end of if(!onlinePolicyVO.getLoginType().equals("H"))
    	 else{
    		 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(MEMBER_DETAILS_ICON,false);
    	 }//end of else

//  	 check the permision and set the tree for not to display respective icon
    	 if(!(userSecurityProfile.getLoginType().equals("H") || (userSecurityProfile.getLoginType().equals("B"))))
    	 {
    		 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(CHANGE_PASSWORD_ICON,false);
    		 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(CANCEL_ENROLLMENT_ICON,false);
    		 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(CANCEL_ENROLLMENT_ICON_CHILD,false);
    		 
    		 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(1,false);//koc1352
    		// treeData.getTreeSetting().getChildNodeSetting().setIconVisible(2, false);//koc1352
    		 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(6,false);//koc1352

    	 }//end of if(!userSecurityProfile.getLoginType().equals("H"))

    	 if(strActiveLink.equals("Pre-Auth") || strActiveLink.equals("Claims")){

    		 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(CHANGE_PASSWORD_ICON,false);
    		 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(E_CARD_ICON,false);
    		 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(MEMBER_DETAILS_ICON,false);
    		 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(CANCEL_ENROLLMENT_ICON,false);
    		 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(CANCEL_ENROLLMENT_ICON_CHILD,false);
    		 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(OPTOUT1, false);//added by Praveen for IBM..KOC-1216
    		 
    		 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(1,false);//koc1352
    		// treeData.getTreeSetting().getChildNodeSetting().setIconVisible(2,false);//koc1352
    		 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(6,false);//koc1352
    	 }//end of if(strActiveLink.equals("Pre-Auth") || strActiveLink.equals("Claims"))
    	 if(strActiveLink.equals("Enrollment") || userSecurityProfile.getLoginType().equals("I") || userSecurityProfile.getLoginType().equals("E") ||userSecurityProfile.getLoginType().equals("OCI"))
    	 {
    		 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(INTIMATION_ICON,false);
    	 }//end of if(strActiveLink.equals("Enrollment") || strActiveLink.equals("Claims")
    	 
    		//1352
			if(TTKCommon.getActiveSubLink(request).equalsIgnoreCase("Enrollment") && userSecurityProfile.getLoginType().equals("E") )
			 {
			 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,true);//koc1352
			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,true);//koc1352
			 }
			
			 else{
			 treeData.getTreeSetting().getRootNodeSetting().setIconVisible(VIEW_FILES_UPLOAD_ENROLLMENT_ICON,false);//koc1352
 			 treeData.getTreeSetting().getChildNodeSetting().setIconVisible(UPLOAD_ENROLLMENT_ICON,false);//koc1352
			 }
			//1352
    	 
     }//end of checkTreePermission(HttpServletRequest request,TreeData treeData)

     /**
      * Check the Member cancel status and set the display property of icons.
      * @param request The HTTP request we are processing
      * @param treeData TreeData for which permission has to check
      * @throws TTKException
      */
     private void checkCancelMember(TreeData treeData,ArrayList alMembers,HttpServletRequest request)
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
    				 chNode.setIsLink(true);
    			 }//end of if(((MemberVO)alNodeMembers.get(i)).getCancleYN().equalsIgnoreCase("Y"))
    			 else
    			 {
    				 chNode.setTextColor("blue");
    				 chNode.setIsLink(true);
    			 }//end of else
    			 alNodeSetting.add(chNode);
    		 }//end of for(int i=0;i<alNodeMembers.size();i++)
    		 treeData.setNodeSettings(alNodeSetting);
    	 }//end of if(alMembers!=null)
     }//end of checkCancelMember(TreeData treeData,ArrayList alMembers,HttpServletRequest request)
}//end of class OnlineMemberAction