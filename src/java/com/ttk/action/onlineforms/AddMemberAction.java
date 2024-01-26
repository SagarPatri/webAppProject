/**
* @ (#) AddMemberDetailAction.java Feb 7th, 2006
* Project 		: TTK HealthCare Services
* File 			: AddMemberDetailAction.java
* Author 		: Krishna K H
* Company 		: Span Systems Corporation
* Date Created 	: Feb 7th, 2006
*
* @author 		: Krishna K H
* Modified by 	: 
* Modified date :
* Reason 		:
*/

package com.ttk.action.onlineforms;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.tree.Node;
import com.ttk.action.tree.TreeData;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberAddressVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.enrollment.MemberVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is reusable one, used to Add members for individual policy,individual policy as group,
 * corporate policies and non corporate policies in enrollment and online forms flow.
 */
public class AddMemberAction extends TTKAction
{
    private static final String strIndPolicyDetails="indmemberdetails";
    private static final String strGrpPolicyDetails="grpmemberdetails";
    private static final String strCorpPolicyDetails="cormemberdetails";
    private static final String strNonCorpPolicyDetails="noncorpmemberdetails";
    private static final String strIndAddMember="indaddnewmember";
    private static final String strGrpAddMember="grpaddnewmember";
    private static final String strCorpAddMember="coraddnewmember";
    private static final String strNonCorpAddMember="noncoraddnewmember";
   
    //  For sub link name
    private static final String strIndividualPolicy="Individual Policy";
    private static final String strIndPolicyasGroup="Ind. Policy as Group";
    private static final String strCorporatePolicy="Corporate Policy";
    private static final String strNonCorporatePolicy="Non-Corporate Policy";
    //private int CHILD_CANCEL_ICON=5;

 	private static Logger log = Logger.getLogger( AddMemberAction.class );

 	private static final String strAddMember="groupdetail";
	
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
 			log.debug("Inside AddMemberAction doDefault");
 			String strSelectedRoot=request.getParameter("selectedroot");
 			String strSelectedNode=request.getParameter("selectednode");
 			MemberDetailVO memberDetailVO= new MemberDetailVO();
 			MemberAddressVO memberAddressVO=new MemberAddressVO();
 			MemberVO memberVO =new MemberVO();
 			TreeData treeData = TTKCommon.getTreeData(request);
 			MemberManager memberManager=this.getMemberManager();
 			ArrayList<Object> alSearchParam = new ArrayList<Object>();
 			String strSubLinkCode="IP";
 			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
 			ArrayList alCityList = new ArrayList();
 			String strPolicyDetails="memberlist";
 			if(strActiveSubLink.equals(strIndividualPolicy))
 			{
 				strSubLinkCode="IP";
 				strPolicyDetails=strIndPolicyDetails;
 			}//end of if(strActiveSubLink.equals(strIndividualPolicy))
 			else if(strActiveSubLink.equals(strIndPolicyasGroup))
 			{
 				strSubLinkCode="IG";
 				strPolicyDetails=strGrpPolicyDetails;
 			}//end of else if(strActiveSubLink.equals(strIndPolicyasGroup))
 			else if(strActiveSubLink.equals(strCorporatePolicy))
 			{
 				strSubLinkCode="CP";
 				strPolicyDetails=strCorpPolicyDetails;
 			}//end of else if(strActiveSubLink.equals(strCorporatePolicy))
 			else if(strActiveSubLink.equals(strNonCorporatePolicy))
 			{
 				strSubLinkCode="NC";
 				strPolicyDetails=strNonCorpPolicyDetails;
 			}//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
 			//from tree with with min data
 			memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode); 
 			//get the Relationship list based on the Ins. Company
 			ArrayList alRelationShip = memberManager.getRelationshipCode(memberVO.getAbbrCode());
 			alSearchParam.add(memberVO.getMemberSeqID());
 			alSearchParam.add(strSubLinkCode);
 			alSearchParam.add("ENM");//strSwitchType
 			if(memberVO.getMemberSeqID()!=null)
 			{
 				memberDetailVO = memberManager.getMember(alSearchParam);
 			}//end of if(memberVO.getMemberSeqID()!=null)
 			else
 			{
 				//in add mode enrollment number have to be set
 				memberDetailVO.setEmployeeNbr(memberVO.getEnrollmentID());
 			}//end of else
 			memberDetailVO.setRelationTypeID(memberDetailVO.getRelationTypeID()+"#"+memberDetailVO.getGenderYN());
 			DynaActionForm frmAddMember = (DynaActionForm)FormUtils.setFormValues("frmAddMember",memberDetailVO,
 																				  this,mapping,request);
 			memberDetailVO.setAbbrCode(memberVO.getAbbrCode());
 			String strRelTypeID = "NSF#"+memberDetailVO.getGenderYN();
 			// for self address will be address of enrollment address
 			if(!memberDetailVO.getRelationTypeID().equals(strRelTypeID)){
 				memberAddressVO=memberDetailVO.getMemberAddressVO();
 			}//end of if(!memberDetailVO.getRelationTypeID().equals(strRelTypeID))    
 			String strHiddenName = (String)frmAddMember.get("hiddenName");
 			
 			if(!frmAddMember.getString("relationTypeID").equalsIgnoreCase(strRelTypeID)){
 				frmAddMember.set("name","");
 				frmAddMember.set("disableYN","Y");
 			}//end of if(!frmAddMember.getString("relationTypeID").equalsIgnoreCase(strRelTypeID))
 			else{
 				frmAddMember.set("disableYN","N");
 			}//end of else
 			frmAddMember.set("hiddenName",strHiddenName);
 			frmAddMember.set("memberAddressVO", (DynaActionForm)FormUtils.setFormValues("frmMemberAddress",
 																			  memberAddressVO,this,mapping,request));
 			frmAddMember.set("alRelationShip",alRelationShip);
 			frmAddMember.set("alCityList",alCityList);
 			frmAddMember.set("caption","Edit");
 			request.getSession().setAttribute("frmAddMember",frmAddMember);
 			return this.getForward(strPolicyDetails, mapping, request);
 		}//end of try
 		catch(TTKException expTTK)
 		{
 			return this.processOnlineExceptions(request, mapping, expTTK);
 		}//end of catch(TTKException expTTK)
 		catch(Exception exp)
 		{
 			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strAddMember));
 		}//end of catch(Exception exp)
 	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

 	/**
 	 * This method is used to load the sub status based on the selected status.
 	 * Finally it forwards to the appropriate view based on the specified forward mappings
 	 *
 	 * @param mapping The ActionMapping used to select this instance
 	 * @param form The optional ActionForm bean for this request (if any)
 	 * @param request The HTTP request we are processing
 	 * @param response The HTTP response we are creating
 	 * @return ActionForward Where the control will be forwarded, after this request is processed
 	 * @throws Exception if any error occurs
 	 */
 	public ActionForward doChangeCity(ActionMapping mapping,ActionForm form,HttpServletRequest request,
 								      HttpServletResponse response) throws Exception{
 		try{
 			setOnlineLinks(request);
 			log.debug("Inside AddMemberAction doChangeCity");
 			MemberDetailVO memberDetailVO=new MemberDetailVO();
 			DynaActionForm frmAddMember = (DynaActionForm)form;
 			memberDetailVO=(MemberDetailVO)FormUtils.getFormValues(frmAddMember,this,mapping,request);
 			ActionForm memberAddressForm=(ActionForm)frmAddMember.get("memberAddressVO");
 			MemberAddressVO memberAddressVO=(MemberAddressVO)FormUtils.getFormValues(memberAddressForm,
 																		"frmMemberAddress",this,mapping,request);
 			memberDetailVO.setMemberAddressVO(memberAddressVO);
 			HashMap hmCityList = null;
 			HospitalManager hospitalObject=this.getHospitalManagerObject();
 			ArrayList alCityList = new ArrayList();
 			String strAddMember="addeditmember";
 			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
 			if(strActiveSubLink.equals(strIndividualPolicy))
 			{
 				strAddMember=strIndAddMember;
 			}//end of if(strActiveSubLink.equals(strIndividualPolicy))
 			else if(strActiveSubLink.equals(strIndPolicyasGroup))
 			{
 				strAddMember=strGrpAddMember;
 			}//end of else if(strActiveSubLink.equals(strIndPolicyasGroup))
 			else if(strActiveSubLink.equals(strCorporatePolicy))
 			{
 				strAddMember=strCorpAddMember;
 			}//end of else if(strActiveSubLink.equals(strCorporatePolicy))
 			else if(strActiveSubLink.equals(strNonCorporatePolicy))
 			{
 				strAddMember=strNonCorpAddMember;
 			}//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
 			hmCityList=hospitalObject.getCityInfo();
 			if(hmCityList!=null){
 				alCityList = (ArrayList)hmCityList.get(memberDetailVO.getMemberAddressVO().getStateCode());
 			}//end of if(hmCityList!=null)
 			if(alCityList==null){
 				alCityList=new ArrayList();
 			}//end of if(alCityList==null)
 			frmAddMember.set("frmChanged","changed");
 			frmAddMember.set("alCityList",alCityList);
 			return this.getForward(strAddMember, mapping, request);
 		}//end of try
 		catch(TTKException expTTK)
 		{
 			return this.processOnlineExceptions(request, mapping, expTTK);
 		}//end of catch(TTKException expTTK)
 		catch(Exception exp)
 		{
 			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strAddMember));
 		}//end of catch(Exception exp)
 	}//end of doEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
 	/**
     * This method is used to reload the screen when the reset button is pressed.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							 HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.debug("Inside AddMemberAction doReset");
    		MemberManager memberManager=this.getMemberManager();
    		HashMap hmCityList = null;
    		ArrayList alCityList = new ArrayList();
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
    		String strSubLinkCode="IP";
    		String strAddMember="addeditmember";
    		if(strActiveSubLink.equals(strIndividualPolicy))
            {
                strSubLinkCode="IP";
                strAddMember=strIndAddMember;
            }//end of if(strActiveSubLink.equals(strIndividualPolicy))
            else if(strActiveSubLink.equals(strIndPolicyasGroup))
            {
                strSubLinkCode="IG";
                strAddMember=strGrpAddMember;
            }//end of else if(strActiveSubLink.equals(strIndPolicyasGroup))
            else if(strActiveSubLink.equals(strCorporatePolicy))
            {
               strSubLinkCode="CP";
               strAddMember=strCorpAddMember;
            }//end of else if(strActiveSubLink.equals(strCorporatePolicy))
            else if(strActiveSubLink.equals(strNonCorporatePolicy))
            {
               strSubLinkCode="NC";
               strAddMember=strNonCorpAddMember;
            }//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
    		DynaActionForm frmMemberDetail =(DynaActionForm)request.getSession().getAttribute("frmAddMember");
    		ArrayList<Object> alSearchParam = new ArrayList<Object>();
            String strHiddenName = (String)frmMemberDetail.get("hiddenName");
            String strRelationID =(String)frmMemberDetail.get("relationID");
            String strGenderID = (String)frmMemberDetail.get("genderYN");
            //get the Relationship list based on the Ins. Company
            ArrayList alRelationShip = (ArrayList)frmMemberDetail.get("alRelationShip");
            Long lngPolicyGroupSeqID = TTKCommon.getLong((String)frmMemberDetail.get("policyGroupSeqID"));
            Date dtPolicyEndDate=TTKCommon.getUtilDate((String)frmMemberDetail.get("policyEndDate"));
            Date dtEffectiveDate=TTKCommon.getUtilDate((String)frmMemberDetail.get("effectiveDate"));

            MemberDetailVO memberDetailVO= new MemberDetailVO();
            MemberAddressVO memberAddressVO=new MemberAddressVO();
            alSearchParam = new ArrayList<Object>();
            String strCaption = (String)frmMemberDetail.get("caption");
            //set the Policy GroupSeqID to VO
            memberDetailVO.setPolicyGroupSeqID(lngPolicyGroupSeqID);
            //get the value from form and store it to the respective VO's
            if(frmMemberDetail.get("memberSeqID")!=null ) // in add mode member sequence id will be null
            {
                alSearchParam.add(new Long((String)frmMemberDetail.get("memberSeqID")));
                alSearchParam.add(strSubLinkCode);
                alSearchParam.add("ENM");//strSwitchType
                memberDetailVO = memberManager.getMember(alSearchParam);
                //for self address will be address of enrollment address
                if(!memberDetailVO.getRelationTypeID().equals("NSF")){
                    memberAddressVO=memberDetailVO.getMemberAddressVO();
                }//end of if(!memberDetailVO.getRelationTypeID().equals("NSF"))
                strRelationID=memberDetailVO.getRelationTypeID();
                strGenderID=memberDetailVO.getGenderYN();
                memberDetailVO.setRelationTypeID(memberDetailVO.getRelationTypeID()+"#"+memberDetailVO.getGenderYN());
                if(hmCityList!=null){
                	alCityList = (ArrayList)hmCityList.get(memberDetailVO.getMemberAddressVO().getStateCode());
                }//end of if(hmCityList!=null)
            }//end of if(frmMemberDetail.get("memberSeqID")!=null )
            else
            {
                memberDetailVO.setStatus("POA");     //select status as Active while adding a member
                memberDetailVO.setStatusDesc("Active");
                memberDetailVO.setExitDate(dtPolicyEndDate);
            }//end of else

            //set the policy end date and endorsement effective date again to VO
            memberDetailVO.setPolicyEndDate(dtPolicyEndDate);
            memberDetailVO.setEffectiveDate(dtEffectiveDate);

            DynaActionForm frmAddMember = (DynaActionForm)FormUtils.setFormValues("frmAddMember",memberDetailVO,
            																	  this,mapping,request);
            frmAddMember.set("memberAddressVO", (DynaActionForm)FormUtils.setFormValues("frmMemberAddress",
            																 memberAddressVO,this,mapping,request));
            hmCityList=hospitalObject.getCityInfo();

            if(alCityList==null){
            	alCityList=new ArrayList();
            }//end of if(alCityList==null)

            String strRelTypeID = "NSF#"+memberDetailVO.getGenderYN();
            if(!memberDetailVO.getRelationTypeID().equalsIgnoreCase(strRelTypeID)){
            	frmAddMember.set("disableYN","Y");
            }//end of if(!memberDetailVO.getRelationTypeID().equalsIgnoreCase(strRelTypeID))
            else{
            	frmAddMember.set("disableYN","N");
            }//end of else
            if(!strGenderID.equals("OTH")){
            	frmAddMember.set("genderTypeID",strGenderID);
            }//end of if(!strGenderID.equals("OTH"))
            frmAddMember.set("hiddenName",strHiddenName);
            frmAddMember.set("relationID",strRelationID);
            frmAddMember.set("alRelationShip",alRelationShip);
            frmAddMember.set("genderYN",strGenderID);
            frmAddMember.set("caption",strCaption);
            request.getSession().setAttribute("frmAddMember",frmAddMember);
            return this.getForward(strAddMember, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
 		{
 			return this.processOnlineExceptions(request, mapping, expTTK);
 		}//end of catch(TTKException expTTK)
 		catch(Exception exp)
 		{
 			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strAddMember));
 		}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to add/update the record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.debug("Inside AddMemberAction doSave");
    		DynaActionForm frmMemberDetail =(DynaActionForm)form;
            String strCaption = (String)frmMemberDetail.get("caption");
            String strHiddenName = (String)frmMemberDetail.get("hiddenName");
            ArrayList alRelationShip = (ArrayList)frmMemberDetail.get("alRelationShip");
            Date dtPolicyEndDate=TTKCommon.getUtilDate((String)frmMemberDetail.get("policyEndDate"));
            Date dtEffectiveDate=TTKCommon.getUtilDate((String)frmMemberDetail.get("effectiveDate"));
            String strRelationID= "";
            MemberDetailVO memberDetailVO= new MemberDetailVO();
            String strEnrollmentNbr="";
            MemberManager memberManager=this.getMemberManager();
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            String strSubLinkCode="IP";
            String strAddMember="addeditmember";
            HashMap hmCityList = null;
            HospitalManager hospitalObject=this.getHospitalManagerObject();
            ArrayList alCityList = new ArrayList();
            if(strActiveSubLink.equals(strIndividualPolicy))
            {
                strAddMember=strIndAddMember;
                strSubLinkCode="IP";
            }//end of if(strActiveSubLink.equals(strIndividualPolicy))
            else if(strActiveSubLink.equals(strIndPolicyasGroup))
            {
                strAddMember=strGrpAddMember;
                strSubLinkCode="IG";
            }//end of else if(strActiveSubLink.equals(strIndPolicyasGroup))
            else if(strActiveSubLink.equals(strCorporatePolicy))
            {
                strAddMember=strCorpAddMember;
                strSubLinkCode="CP";
            }//end of else if(strActiveSubLink.equals(strCorporatePolicy))
            else if(strActiveSubLink.equals(strNonCorporatePolicy))
            {
                strAddMember=strNonCorpAddMember;
                strSubLinkCode="NC";
             }
            Long lngPolicyGroupSeqID = null;
            ArrayList<Object> alSearchParam = new ArrayList<Object>();
            if(request.getParameter("PEDPresentYN")==null)// if PED Present checkbox in not selected
            {
                frmMemberDetail.set("PEDPresentYN","N");
            }//end of if(request.getParameter("PEDPresentYN")==null)
            if(request.getParameter("photoPresentYN")==null)// if Photo Present checkbox in not selected
            {
                frmMemberDetail.set("photoPresentYN","N");
            }//end of if(request.getParameter("photoPresentYN")==null)
            if(request.getParameter("cardPrintYN")==null)// if Print Card checkbox in not selected
            {
                frmMemberDetail.set("cardPrintYN","N");
            }//end of if(request.getParameter("cardPrintYN")==null)
            //get the value from form and store it to the respective VO's
            memberDetailVO=(MemberDetailVO)FormUtils.getFormValues(frmMemberDetail,this,mapping,request);
            lngPolicyGroupSeqID = memberDetailVO.getPolicyGroupSeqID();
            strEnrollmentNbr=memberDetailVO.getEnrollmentNbr();
            String strRelationTypeID = memberDetailVO.getRelationTypeID();
            strRelationTypeID=strRelationTypeID.substring(0,strRelationTypeID.indexOf("#"));
            memberDetailVO.setRelationTypeID(strRelationTypeID);
            ActionForm memberAddressForm=(ActionForm)frmMemberDetail.get("memberAddressVO");
            MemberAddressVO memberAddressVO=(MemberAddressVO)FormUtils.getFormValues(memberAddressForm,
            																"frmMemberAddress",this,mapping,request);
            memberDetailVO.setMemberAddressVO(memberAddressVO);
            Long lngMemberSeqID=memberDetailVO.getMemberSeqID();
            if(lngMemberSeqID!=null)
            {
                memberDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
                if(memberDetailVO.getAddedBy()==null)
                {
                    memberDetailVO.setAddedBy(TTKCommon.getUserSeqId(request));
                }//end of if(memberDetailVO.getAddedBy()==null)
            }// end of if(lngMemberSeqID!=null)
            else
            {
                memberDetailVO.setAddedBy(TTKCommon.getUserSeqId(request));
            }//end of else
            Long lngMemSeqID=memberManager.saveMember(memberDetailVO,"ENM",strSubLinkCode);
            if(lngMemSeqID!=null)
            {
                if(lngMemberSeqID!=null)
                {
                    request.setAttribute("updated","message.savedSuccessfully");
                    if(memberDetailVO.getAddedBy()==null)
                    {
                        memberDetailVO.setAddedBy(TTKCommon.getUserSeqId(request));
                    }//end of if(memberDetailVO.getAddedBy()==null)
                }// end of if(lngMemberSeqID!=null)
                else
                {
                    request.setAttribute("updated","message.addedSuccessfully");
                }//end of else
            }//end of if(lngMemSeqID!=null)

            memberDetailVO= new MemberDetailVO();
            memberDetailVO.setPolicyGroupSeqID(lngPolicyGroupSeqID);
            memberDetailVO.setEnrollmentNbr(strEnrollmentNbr);
            memberAddressVO= new MemberAddressVO();
            memberDetailVO.setMemberAddressVO(memberAddressVO);
            //create a search parameter
            alSearchParam.add(lngMemSeqID);
            alSearchParam.add(strSubLinkCode);
            alSearchParam.add("ENM");//strSwitchType
            if(lngMemSeqID!=null && strCaption.indexOf("Edit")>=0)
            {
                memberDetailVO=memberManager.getMember(alSearchParam);
                //for self address will be address of enrollment address
                if(!memberDetailVO.getRelationTypeID().equals("NSF")){   
                    memberAddressVO=memberDetailVO.getMemberAddressVO();
                }//end of if(!memberDetailVO.getRelationTypeID().equals("NSF"))
                strRelationID=memberDetailVO.getRelationTypeID();
                memberDetailVO.setRelationTypeID(memberDetailVO.getRelationTypeID()+"#"+memberDetailVO.getGenderYN());
            }//end of if(lngMemSeqID!=null)
            String strRelTypeID = "NSF#"+memberDetailVO.getGenderYN();
            frmMemberDetail.initialize(mapping);
            if(lngMemberSeqID==null)    //set status as active to add another member
            {
                memberDetailVO.setStatus("POA");     //select status as Active while adding a member
                memberDetailVO.setStatusDesc("Active");
                //set the exit date as policy end date when adding the member
                memberDetailVO.setExitDate(dtPolicyEndDate); 
            }//end of if(lngMemberSeqID==null)
            //set the policy end date and endorsement effective date again to VO
            memberDetailVO.setPolicyEndDate(dtPolicyEndDate);
            memberDetailVO.setEffectiveDate(dtEffectiveDate);

            DynaActionForm frmAddMember = (DynaActionForm)FormUtils.setFormValues("frmAddMember",memberDetailVO,
            																	  this,mapping,request);
            frmAddMember.set("memberAddressVO", (DynaActionForm)FormUtils.setFormValues("frmMemberAddress",
            											 memberDetailVO.getMemberAddressVO(),this,mapping,request));
            hmCityList=hospitalObject.getCityInfo();
            if(hmCityList!=null){
            	alCityList = (ArrayList)hmCityList.get(memberDetailVO.getMemberAddressVO().getStateCode());
            }//end of if(hmCityList!=null)
            if(alCityList==null){
            	alCityList= new ArrayList();
            }//end of if(alCityList==null)
            frmAddMember.set("caption",strCaption);
            frmAddMember.set("hiddenName",strHiddenName);
            if(!memberDetailVO.getRelationTypeID().equalsIgnoreCase(strRelTypeID)){
            	frmAddMember.set("disableYN","Y");
            }//end of if(!memberDetailVO.getRelationTypeID().equalsIgnoreCase(strRelTypeID))
            else{
            	frmAddMember.set("disableYN","N");
            }//end of else
            frmAddMember.set("genderYN",memberDetailVO.getGenderTypeID());
            frmAddMember.set("relationID",strRelationID);
            frmAddMember.set("alCityList",alCityList);
            frmAddMember.set("alRelationShip",alRelationShip);
            request.getSession().setAttribute("frmAddMember",frmAddMember);
            request.setAttribute("mode",null);
            return this.getForward(strAddMember, mapping, request);
		}//end of try
    	catch(TTKException expTTK)
 		{
 			return this.processOnlineExceptions(request, mapping, expTTK);
 		}//end of catch(TTKException expTTK)
 		catch(Exception exp)
 		{
 			return this.processOnlineExceptions(request, mapping, new TTKException(exp, strAddMember));
 		}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doViewMember(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    								  HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.debug("Inside AddMemberAction doViewMember");
    		//Fetch the value of selected id
    		DynaActionForm generalForm = (DynaActionForm)form;
    		MemberVO memberVO= new MemberVO();
    		ArrayList<Object> alSearchParam = new ArrayList<Object>();
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		TreeData treeData = TTKCommon.getTreeData(request);
    		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
    		MemberManager memberManager=this.getMemberManager();
    		ArrayList alCityList = new ArrayList();
    		String strAddMember="addeditmember";
    		String strSubLinkCode="IP";
    		if(strActiveSubLink.equals(strIndividualPolicy))
    		{
    			strSubLinkCode="IP";
    			strAddMember=strIndAddMember;
    		}//end of if(strActiveSubLink.equals(strIndividualPolicy))
    		else if(strActiveSubLink.equals(strIndPolicyasGroup))
    		{
    			strSubLinkCode="IG";
    			strAddMember=strGrpAddMember;
    		}//end of else if(strActiveSubLink.equals(strIndPolicyasGroup))
    		else if(strActiveSubLink.equals(strCorporatePolicy))
    		{
    			strSubLinkCode="CP";
    			strAddMember=strCorpAddMember;
    		}//end of else if(strActiveSubLink.equals(strCorporatePolicy))
    		else if(strActiveSubLink.equals(strNonCorporatePolicy))
    		{
    			strSubLinkCode="NC";
    			strAddMember=strCorpAddMember;
    		}//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
    		MemberDetailVO memberDetailVO = new MemberDetailVO();
    		MemberAddressVO memberAddressVO=new MemberAddressVO();
    		String strSelectedRoot = (String)generalForm.get("selectedroot");
    		String strSelectedNode = (String)generalForm.get("selectednode");
    		treeData.setSelectedRoot(-1);   //sets the selected rows
    		//from tree with with min data
    		memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode); 
    		alSearchParam.add(memberVO.getMemberSeqID());
    		alSearchParam.add(strSubLinkCode);
    		alSearchParam.add("ENM");//strSwitchType
    		if(memberVO.getMemberSeqID()!=null){
    			memberDetailVO = memberManager.getMember(alSearchParam);
    		}//end of if(memberVO.getMemberSeqID()!=null)
    		DynaActionForm frmAddMember = (DynaActionForm)FormUtils.setFormValues("frmAddMember",memberDetailVO,
    																			  this,mapping,request);
    		memberAddressVO=memberDetailVO.getMemberAddressVO();
    		frmAddMember.set("memberAddressVO", (DynaActionForm)FormUtils.setFormValues("frmMemberAddress",
    																		  memberAddressVO,this,mapping,request));
    		HashMap hmCityList=hospitalObject.getCityInfo();
    		if(hmCityList!=null){
    			alCityList = (ArrayList)hmCityList.get(memberDetailVO.getMemberAddressVO().getStateCode());
    		}//end of if(hmCityList!=null)
    		if(alCityList==null){
    			alCityList=new ArrayList();
    		}//end of if(alCityList==null)
    		frmAddMember.set("alCityList",alCityList);
    		request.getSession().setAttribute("frmAddMember",frmAddMember);
    		return this.getForward(strAddMember, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request, mapping, new TTKException(exp, strAddMember));
    	}//end of catch(Exception exp)
    }//end of doViewMember(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is called from the struts framework.
     * This method is used to delete a record.
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
    		setOnlineLinks(request);
    		log.debug("Inside AddMemberAction doDelete");
    		DynaActionForm frmMemberDetail = (DynaActionForm)form;
    		String strFlag = "";
    		String strSeqID = "";
    		List<String> iResult;
    		Long lngEndorsementSeqID = null;
    		TreeData treeData = TTKCommon.getTreeData(request);
    		ArrayList<Object> alSearchParam = new ArrayList<Object>();
    		treeData.setSelectedRoot(-1);   //sets the selected rows
    		strFlag = "MEMBER";
    		strSeqID = (String)frmMemberDetail.get("memberSeqID");
    		String strPolicyDetails="memberlist";
    		String strSubLinkCode="IP";
    		MemberManager memberManager=this.getMemberManager();
    		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
    		if(strActiveSubLink.equals(strIndividualPolicy))
    		{
    			strPolicyDetails=strIndPolicyDetails;
    			strSubLinkCode="IP";
    		}//end of if(strActiveSubLink.equals(strIndividualPolicy))
    		else if(strActiveSubLink.equals(strIndPolicyasGroup))
    		{
    			strPolicyDetails=strGrpPolicyDetails;
    			strSubLinkCode="IG";
    		}//end of else if(strActiveSubLink.equals(strIndPolicyasGroup))
    		else if(strActiveSubLink.equals(strCorporatePolicy))
    		{
    			strPolicyDetails=strCorpPolicyDetails;
    			strSubLinkCode="CP";
    		}//end of else if(strActiveSubLink.equals(strCorporatePolicy))
    		else if(strActiveSubLink.equals(strNonCorporatePolicy))
    		{
    			strPolicyDetails=strNonCorpPolicyDetails;
    			strSubLinkCode="NC";
    		}//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
    		//create parameter to delete the member detail
    		alSearchParam.add(strSubLinkCode);
    		alSearchParam.add("ENM");//strSwitchType
    		alSearchParam.add(strFlag);
    		alSearchParam.add(strSeqID);
    		alSearchParam.add(lngEndorsementSeqID);
    		alSearchParam.add(TTKCommon.getUserSeqId(request));
    		iResult= memberManager.deleteMember(alSearchParam);
    		log.debug("iResult value is :"+iResult);
    		return this.getForward(strPolicyDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request, mapping, new TTKException(exp, strAddMember));
    	}//end of catch(Exception exp)
    }//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to load the sub status based on the selected status.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doChangeRelationship(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										  HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.debug("Inside AddMemberAction doChangeRelationship");
    		DynaActionForm frmAddMember = (DynaActionForm)form;
    		String strAddMember="addeditmember";
    		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
    		if(strActiveSubLink.equals(strIndividualPolicy))
    		{
    			strAddMember=strIndAddMember;
    		}//end of if(strActiveSubLink.equals(strIndividualPolicy))
    		else if(strActiveSubLink.equals(strIndPolicyasGroup))
    		{
    			strAddMember=strGrpAddMember;
    		}//end of else if(strActiveSubLink.equals(strIndPolicyasGroup))
    		else if(strActiveSubLink.equals(strCorporatePolicy))
    		{
    			strAddMember=strCorpAddMember;
    		}//end of else if(strActiveSubLink.equals(strCorporatePolicy))
    		else if(strActiveSubLink.equals(strNonCorporatePolicy))
    		{
    			strAddMember=strNonCorpAddMember;
    		}//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
    		String strHiddenName = (String)frmAddMember.get("hiddenName");
    		String strRelationTypeID = frmAddMember.getString("relationTypeID");
    		String strGenderID = strRelationTypeID.substring(strRelationTypeID.indexOf("#")+1,strRelationTypeID.length());
    		if(strRelationTypeID!=null && strRelationTypeID.length()>0){
    			strRelationTypeID=strRelationTypeID.substring(0,strRelationTypeID.indexOf("#"));
    		}//end of if(strRelationTypeID!=null && strRelationTypeID.length()>0)
    		if(!frmAddMember.getString("relationTypeID").equalsIgnoreCase("NSF#OTH")){
    			frmAddMember.set("name","");
    			frmAddMember.set("disableYN","Y");
    		}//end of if(!frmAddMember.getString("relationTypeID").equalsIgnoreCase("NSF#OTH"))
    		else{
    			frmAddMember.set("name",strHiddenName);
    			frmAddMember.set("disableYN","N");
    		}//end of else
    		if(!strGenderID.equals("OTH")){
    			frmAddMember.set("genderTypeID",strGenderID);
    		}//end of if(!strGenderID.equals("OTH"))
    		frmAddMember.set("relationID",strRelationTypeID);
    		frmAddMember.set("genderYN",strGenderID);
    		frmAddMember.set("hiddenName",strHiddenName);
    		frmAddMember.set("frmChanged","changed");
    		return this.getForward(strAddMember, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request, mapping, new TTKException(exp, strAddMember));
    	}//end of catch(Exception exp)
    }//end of doChangeRelationship(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
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
    		setOnlineLinks(request);
    		log.debug("Inside AddMemberAction doClose");
    		ArrayList<Object> alSearchParam = new ArrayList<Object>();
    		TreeData treeData = TTKCommon.getTreeData(request);
    		int CHILD_CANCEL_ICON=5;
    		MemberManager memberManager=this.getMemberManager();
    		String strSubLinkCode="IP";
    		String strPolicyDetails="memberlist";
    		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
    		if(strActiveSubLink.equals(strIndividualPolicy))
    		{
    			strPolicyDetails=strIndPolicyDetails;
    			strSubLinkCode="IP";
    		}//end of if(strActiveSubLink.equals(strIndividualPolicy))
    		else if(strActiveSubLink.equals(strIndPolicyasGroup))
    		{
    			strPolicyDetails=strGrpPolicyDetails;
    			strSubLinkCode="IG";
    		}//end of else if(strActiveSubLink.equals(strIndPolicyasGroup))
    		else if(strActiveSubLink.equals(strCorporatePolicy))
    		{
    			strPolicyDetails=strCorpPolicyDetails;
    			strSubLinkCode="CP";
    		}//end of else if(strActiveSubLink.equals(strCorporatePolicy))
    		else if(strActiveSubLink.equals(strNonCorporatePolicy))
    		{
    			strPolicyDetails=strNonCorpPolicyDetails;
    			strSubLinkCode="NC";
    		}//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
//    		alSearchParam = new ArrayList();
    		MemberVO memberVO = (MemberVO)treeData.getSelectedObject(String.valueOf(treeData.getSelectedRoot()),null);
    		if(memberVO!=null)
    		{
    			//create search parameter to get the dependent list
    			alSearchParam.add(memberVO.getPolicyGroupSeqID());
    			alSearchParam.add("ENM");//strSwitchType
    			alSearchParam.add(strSubLinkCode);
    			//get the dependent list from database
    			memberVO.setMemberList(memberManager.getDependentList(alSearchParam));
    			ArrayList<Object> alNodeSetting=new ArrayList<Object>();
    			Node chNode=null;
    			for(int i=0;memberVO.getMemberList()!=null && i<memberVO.getMemberList().size();i++)
    			{
    				chNode=treeData.copyNodeSetting();//new Node();
    				if(((MemberVO)memberVO.getMemberList().get(i)).getCancleYN().equalsIgnoreCase("Y"))
    				{
    					chNode.setTextColor("red");
    					chNode.setIconVisible(CHILD_CANCEL_ICON,false);
    				}//end of if(((MemberVO)alNodeMembers.get(i)).getCancleYN().equalsIgnoreCase("Y"))
    				else
    				{
    					chNode.setTextColor("black");
    				}//end of else
    				alNodeSetting.add(chNode);
    			}//end of for(int i=0;i<alNodeMembers.size();i++)
    			treeData.setNodeSettings(alNodeSetting);
    		}//end of if(memberVO!=null)
    		return this.getForward(strPolicyDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request, mapping, new TTKException(exp, strAddMember));
    	}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
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
            }//end if(memberManager == null)
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
}//end of class AddMemberDetailAction


