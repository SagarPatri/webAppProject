/**
 * @ (#) AddMemberDetailAction.java Feb 7th, 2006
 * Project 		: TTK HealthCare Services
 * File 		: AddMemberDetailAction.java
 * Author 		: Krishna K H
 * Company 		: Span Systems Corporation
 * Date Created : Feb 7th, 2006
 *
 * @author 		: Krishna K H
 * Modified by 	:
 * Modified date:
 * Reason 		:
 */

package com.ttk.action.enrollment;

import java.util.ArrayList;
import java.util.Date;
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
import com.ttk.business.administration.RuleManager;
import com.ttk.business.enrollment.MemberManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberAddressVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.enrollment.MemberVO;

import formdef.plugin.util.FormUtils;


/**
 * This action class isused to add/update the member details to the Policy Group/Family
 * and this is reusable in Individual Policy,Ind. Policy as Group,Corporate Policy and Non-Corporate Policy
 * of Enrollment and Endosement flows.
 */

public class AddMemberDetailAction extends TTKAction
{
	//declaration of forward paths
	private static final String strIndPolicyDetails="indmemberdetails";
	private static final String strGrpPolicyDetails="grpmemberdetails";
	private static final String strCorpPolicyDetails="cormemberdetails";
	private static final String strCorpRenewMemberDetails="correnewmemberdetails";
	private static final String strNonCorpPolicyDetails="noncorpmemberdetails";
	private static final String strIndAddMember="indaddnewmember";
	private static final String strGrpAddMember="grpaddnewmember";
	private static final String strCorpAddMember="coraddnewmember";
	private static final String strNonCorpAddMember="noncoraddnewmember";
	//For sub link name
	private static final String strIndividualPolicy="Individual Policy";
	private static final String strIndPolicyasGroup="Ind. Policy as Group";
	private static final String strCorporatePolicy="Corporate Policy";
	private static final String strNonCorporatePolicy="Non-Corporate Policy";
	private static int intCHILD_CANCEL_ICON=4;
	private static Logger log = Logger.getLogger( AddMemberDetailAction.class );
	
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
			log.debug("Inside AddMemberDetailAction doDefault");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			
			MemberManager memberManager=this.getMemberManager();
			ArrayList<Object> alSearchParam = new ArrayList<Object>();
			TreeData treeData = TTKCommon.getTreeData(request);
			
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strPolicyDetails=getPolicyForwardPath(strActiveSubLink);
			String strSubLinkCode=getSubLinkCode(strActiveSubLink);
			
			String strSelectedRoot=request.getParameter("selectedroot");
			String strSelectedNode=request.getParameter("selectednode");
			MemberDetailVO memberDetailVO= new MemberDetailVO();
			MemberAddressVO memberAddressVO=new MemberAddressVO();
			MemberVO memberVO =new MemberVO();
			//from tree with with min data
			memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
			//get the Relationship list based on the Ins. Company
			ArrayList alRelationShip = memberManager.getRelationshipCode(memberVO.getAbbrCode());
			alSearchParam.add(memberVO.getMemberSeqID());
			alSearchParam.add(strSubLinkCode);
			alSearchParam.add(strSwitchType);
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
			//for self address will be address of enrollment address
			if(!memberDetailVO.getRelationTypeID().equals(strRelTypeID))
			{
				memberAddressVO=memberDetailVO.getMemberAddressVO();
			}//end of if(!memberDetailVO.getRelationTypeID().equals(strRelTypeID))
			String strHiddenName = (String)frmAddMember.get("hiddenName");
			if(!frmAddMember.getString("relationTypeID").equalsIgnoreCase(strRelTypeID))
			{
				frmAddMember.set("name","");
				frmAddMember.set("disableYN","Y");
			}//end of if(!frmAddMember.getString("relationTypeID").equalsIgnoreCase(strRelTypeID))
			else
			{
				frmAddMember.set("disableYN","N");
			}//end of else if(!frmAddMember.getString("relationTypeID").equalsIgnoreCase(strRelTypeID))
			frmAddMember.set("hiddenName",strHiddenName);
			frmAddMember.set("memberAddressVO", (DynaActionForm)FormUtils.setFormValues("frmMemberAddress",
					memberAddressVO,this,mapping,request));
			frmAddMember.set("alRelationShip",alRelationShip);
			frmAddMember.set("caption","Edit");
			request.getSession().setAttribute("frmAddMember",frmAddMember);
			return this.getForward(strPolicyDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"memberdeail"));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside AddMemberDetailAction doReset");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			//DynaActionForm frmAddMember =(DynaActionForm)request.getSession().getAttribute("frmAddMember");
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			
			DynaActionForm frmMemberDetail =(DynaActionForm)request.getSession().getAttribute("frmAddMember");
			ArrayList<Object> alSearchParam = new ArrayList<Object>();
			String strHiddenName = (String)frmMemberDetail.get("hiddenName");
			String strRelationID =(String)frmMemberDetail.get("relationID");
			String strGenderID = (String)frmMemberDetail.get("genderYN");
			
			//get the Relationship list based on the Ins. Company
			ArrayList alRelationShip = (ArrayList)frmMemberDetail.get("alRelationShip");
			Long lngPolicyGroupSeqID = TTKCommon.getLong((String)frmMemberDetail.get("policyGroupSeqID"));
			Date dtPolicyEndDate=TTKCommon.getUtilDate((String)frmMemberDetail.get("policyEndDate"));
			Date dtPolicyStartDate=TTKCommon.getUtilDate((String)frmMemberDetail.get("policyStartDate"));
			Date dtEffectiveDate=TTKCommon.getUtilDate((String)frmMemberDetail.get("effectiveDate"));
			
			MemberManager memberManager=this.getMemberManager();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strAddMember=getMemberForwardPath(strActiveSubLink);
			String strSubLinkCode=getSubLinkCode(strActiveSubLink);
			String strPolicyTypeCode=getPolicyTypeCode(strActiveSubLink);
			
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
				alSearchParam.add(strSwitchType);
				memberDetailVO = memberManager.getMember(alSearchParam);
				//for self address will be address of enrollment address
				if(!memberDetailVO.getRelationTypeID().equals("NSF"))
				{
					memberAddressVO=memberDetailVO.getMemberAddressVO();
				}//end of if(!memberDetailVO.getRelationTypeID().equals("NSF"))
				strRelationID=memberDetailVO.getRelationTypeID();
				strGenderID=memberDetailVO.getGenderYN();
				
				memberDetailVO.setRelationTypeID(memberDetailVO.getRelationTypeID()+"#"+memberDetailVO.getGenderYN());
			}//end of if(frmMemberDetail.get("memberSeqID")!=null )
			else
			{
				//select status as Active while adding a member
				memberDetailVO.setStatus("POA");     
				memberDetailVO.setStatusDesc("Active");
				memberDetailVO.setExitDate(dtPolicyEndDate);
				if(strIndividualPolicy.equals(strActiveSubLink))
				{
					memberDetailVO.setInceptionDate(dtPolicyStartDate);
				}//end of if(strIndividualPolicy.equals(strActiveSubLink))
			}//end of else
			
			//set the policy end date and endorsement effective date again to VO
			memberDetailVO.setPolicyEndDate(dtPolicyEndDate);
			memberDetailVO.setPolicyStartDate(dtPolicyStartDate);
			memberDetailVO.setEffectiveDate(dtEffectiveDate);
			
			DynaActionForm frmAddMember = (DynaActionForm)FormUtils.setFormValues("frmAddMember",memberDetailVO,
					this,mapping,request);
			frmAddMember.set("memberAddressVO", (DynaActionForm)FormUtils.setFormValues("frmMemberAddress",
					memberAddressVO,this,mapping,request));
			//adding nationalities and maritalStatuses in select boxes
           
			String strRelTypeID = "NSF#"+memberDetailVO.getGenderYN();
			if(!memberDetailVO.getRelationTypeID().equalsIgnoreCase(strRelTypeID))
			{
				frmAddMember.set("disableYN","Y");
			}//end of if(!memberDetailVO.getRelationTypeID().equalsIgnoreCase(strRelTypeID))
			else
			{
				frmAddMember.set("disableYN","N");
			}//end of else if(!memberDetailVO.getRelationTypeID().equalsIgnoreCase(strRelTypeID))
			if(!strGenderID.equals("OTH"))
			{
				frmAddMember.set("genderTypeID",strGenderID);
			}//end of if(!strGenderID.equals("OTH"))
			frmAddMember.set("hiddenName",strHiddenName);
			frmAddMember.set("relationID",strRelationID);
			frmAddMember.set("alRelationShip",alRelationShip);
			frmAddMember.set("genderYN",strGenderID);
			frmAddMember.set("caption",strCaption);
			frmAddMember.set("corporate",strPolicyTypeCode);
			request.getSession().setAttribute("frmAddMember",frmAddMember);
			return this.getForward(strAddMember, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"memberdeail"));
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
			HttpServletResponse response) throws Exception
			{
		try{
			log.debug("Inside AddMemberDetailAction doSave");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			
			DynaActionForm frmMemberDetail =(DynaActionForm)form;
			DynaActionForm frmAddMember =(DynaActionForm)form;
			String authorityType=frmAddMember.getString("prodPolicyAuthority");
			String memberTypeId=frmAddMember.getString("member_type_id");		
			String birthcerticateId=frmAddMember.getString("birth_certificate_id");	
			String gdrfa_file_number=frmAddMember.getString("gdrfa_file_number");
			if("DHA".equals(authorityType)){
				if("6".equals(memberTypeId)){
					if("".equals(birthcerticateId) ||birthcerticateId==null )
						throw new TTKException("error.enrollment.member.required");
				}if("4".equals(memberTypeId) || "5".equals(memberTypeId)){
					if("".equals(gdrfa_file_number) ||gdrfa_file_number==null )
						throw new TTKException("error.enrollment.gdrfafilenumber.required");
				}
			}
			String strCaption = (String)frmMemberDetail.get("caption");
			String strHiddenName = (String)frmMemberDetail.get("hiddenName");
			ArrayList alRelationShip = (ArrayList)frmMemberDetail.get("alRelationShip");
			Date dtPolicyEndDate=TTKCommon.getUtilDate((String)frmMemberDetail.get("policyEndDate"));
			Date dtPolicyStartDate=TTKCommon.getUtilDate((String)frmMemberDetail.get("policyStartDate"));
			Date dtEffectiveDate=TTKCommon.getUtilDate((String)frmMemberDetail.get("effectiveDate"));
			MemberManager memberManager=this.getMemberManager();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strAddMember=getMemberForwardPath(strActiveSubLink);
			String strSubLinkCode=getSubLinkCode(strActiveSubLink);
			String strPolicyTypeCode=getPolicyTypeCode(strActiveSubLink);
			String strRelationID= "";
			MemberDetailVO memberDetailVO= new MemberDetailVO();
			String strEnrollmentNbr="";
			
			Long lngPolicyGroupSeqID = null;
			ArrayList<Object> alSearchParam = new ArrayList<Object>();
			alSearchParam = new ArrayList<Object>();
			if(request.getParameter("PEDPresentYN")==null)// if PED Present checkbox in not selected
			{
				frmMemberDetail.set("PEDPresentYN","N");
			}//end of if(request.getParameter("PEDPresentYN")==null)
			if(request.getParameter("photoPresentYN")==null)// if Photo Present checkbox in not selected
			{
				memberDetailVO.setPhotoPresentYN("N");
			}//end of if(request.getParameter("photoPresentYN")==null)
			if(request.getParameter("cardPrintYN")==null)// if Print Card checkbox in not selected
			{
				memberDetailVO.setCardPrintYN("N");
			}//end of if(request.getParameter("cardPrintYN")==null)
			if(request.getParameter("stopPatClmYN")==null)// if Stop Preauth/Caim checkbox in not selected
			{
				memberDetailVO.setStopPatClmYN("N");
			}//end of if(request.getParameter("stopPatClmYN")==null)
			if(request.getParameter("flagYN")==null)// if Flag checkbox in Member in not selected
			{
				memberDetailVO.setFlagYN("N");
			}
			//get the value from form and store it to the respective VO's
			memberDetailVO=(MemberDetailVO)FormUtils.getFormValues(frmMemberDetail,this,mapping,request);
			if(TTKCommon.checkNull(request.getParameter("hyperTensCoverYN"))!="")
			{
				memberDetailVO.setHyperTensCoverYN("Y");
			}//end of if(TTKCommon.checkNull(request.getParameter("hyperTensCoverYN"))!="")
			else
			{
				memberDetailVO.setHyperTensCoverYN("N");
			}// end of else
			if(TTKCommon.checkNull(request.getParameter("diabetesCoverYN"))!="")
			{
				memberDetailVO.setDiabetesCoverYN("Y");
			}// end of if(TTKCommon.checkNull(request.getParameter("diabetesCoverYN"))!="")
			else
			{
				memberDetailVO.setDiabetesCoverYN("N");
			}// end of else
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
			
			if(strSwitchType.equals("END")) // for Endorsement flow set the Endorsement Seq ID from webBoard
			{
				memberDetailVO.setSeqID(WebBoardHelper.getEndorsementSeqId(request));
			}//end of if(strSwitchType.equals("END"))
			else // for Enrollment flow set the Policy Seq ID from webBoard
			{
				memberDetailVO.setSeqID(WebBoardHelper.getPolicySeqId(request));
			}//end of else
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
			DynaActionForm frmPolicyDetails=(DynaActionForm) request.getSession().getAttribute("frmPolicyDetails");
			DynaActionForm frmEndorsementDetails=(DynaActionForm) request.getSession().getAttribute("frmEndorsementDetails");
			//if(request.getSession().getAttribute("policySeqID")!=null) memberDetailVO.setPolicySeqID((Long) request.getSession().getAttribute("policySeqID"));
			
			memberDetailVO.setPolicySeqID(WebBoardHelper.getPolicySeqId(request));
			
			Long lngMemSeqID=memberManager.saveMember(memberDetailVO,strSwitchType,strSubLinkCode);
			int iResult = 0;
			if(lngMemSeqID!=null){
				iResult = memberManager.saveMember1(memberDetailVO,lngMemSeqID,strSwitchType);
			}
			log.info("Member Seq ID is ..............."+lngMemSeqID);
			
			
			if(lngMemSeqID!=null && lngMemSeqID.longValue()<0 && iResult!=0)
			{
				log.info("Errors are there..........");
				RuleManager ruleManager=this.getRuleManagerObject();
				//RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();
				ArrayList alValidationError=ruleManager.getValidationErrorList(lngMemSeqID);
				
				//prepare Error messages
				//ArrayList alErrorMessage=ruleXMLHelper.prepareErrorMessage(alValidationError,request);
				request.setAttribute("BUSINESS_ERRORS",alValidationError);
				return this.getForward(strAddMember, mapping, request);
			}//end of if(lngMemSeqID!=null && lngMemSeqID.longValue()<0)
			else
			{
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
				alSearchParam.add(strSwitchType);
				if(lngMemSeqID!=null && strCaption.indexOf("Edit")>=0)
				{ 
					memberDetailVO=memberManager.getMember(alSearchParam);
					//for self address will be address of enrollment address
					if(!memberDetailVO.getRelationTypeID().equals("NSF"))
					{
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
					//set the Inception date as policy start date when adding the member
					if(strIndividualPolicy.equals(strActiveSubLink))
					{
						memberDetailVO.setInceptionDate(dtPolicyStartDate);
					}//end of if(strIndividualPolicy.equals(strActiveSubLink))
				}//end of if(lngMemberSeqID==null)
				//set the policy end date and endorsement effective date again to VO
				
				memberDetailVO.setPolicyEndDate(dtPolicyEndDate);
				memberDetailVO.setPolicyStartDate(dtPolicyStartDate);
				memberDetailVO.setEffectiveDate(dtEffectiveDate);
	            memberDetailVO.setMarginAEDPER("PER");
	            memberDetailVO.setDhpoMemberId("");
	            memberDetailVO.setPolicySequence("");
				frmAddMember = (DynaActionForm)FormUtils.setFormValues("frmAddMember",memberDetailVO,
						this,mapping,request);
				
				frmAddMember.set("memberAddressVO", (DynaActionForm)FormUtils.setFormValues("frmMemberAddress",
						memberDetailVO.getMemberAddressVO(),this,mapping,request));
				frmAddMember.set("caption",strCaption);
				
				frmAddMember.set("hiddenName",strHiddenName);
				//frmAddMember.set("hyperTensCoverYN", strHyperTensCoverYN);
				if(!memberDetailVO.getRelationTypeID().equalsIgnoreCase(strRelTypeID))
				{
					frmAddMember.set("disableYN","Y");
				}//end of if(!memberDetailVO.getRelationTypeID().equalsIgnoreCase(strRelTypeID))
				else
				{
					frmAddMember.set("disableYN","N");
				}//end of else
				
				String portedYN =  (String) request.getSession().getAttribute("portedYN");
				 ArrayList alMemberType = memberManager.getMemberType();
				 ArrayList alEntityType = memberManager.getEntityAndFeeType("Entity");
				frmAddMember.set("portedYN", portedYN);
				frmAddMember.set("genderYN",memberDetailVO.getGenderTypeID());
				frmAddMember.set("relationID",strRelationID);
				frmAddMember.set("corporate",strPolicyTypeCode);
				frmAddMember.set("alRelationShip",alRelationShip);
				frmAddMember.set("alMemberType",alMemberType);
				 frmAddMember.set("alEntityType",alEntityType);
				 MemberVO memberVO1=(MemberVO) request.getSession().getAttribute("memberVO");
		          
				
				if(memberVO1!=null)
				{
					frmAddMember.set("eventCompleteYN",memberVO1.getEventCompleteYN());  
					frmAddMember.set("capitaionFlag",memberVO1.getCapitaionFlag());
				}
				else
				{
					frmAddMember.set("eventCompleteYN","N");
					frmAddMember.set("eventCompleteYN","N");
				}
				
				
				/*if(frmPolicyDetails!=null)
				{
					frmAddMember.set("eventCompleteYN", frmPolicyDetails.getString("eventCompleteYN")); 
				}
				else
				{
					frmAddMember.set("eventCompleteYN", "N"); 
				}*/
				request.getSession().setAttribute("frmAddMember",frmAddMember);
				request.setAttribute("mode",null);
			}//end of else
			return this.getForward(strAddMember, mapping, request);
			
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"memberdeail"));
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
			log.debug("Inside AddMemberDetailAction doViewMember");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			
			// Fetch the value of selected id
			DynaActionForm generalForm = (DynaActionForm)form;
			MemberVO memberVO= new MemberVO();
			TreeData treeData = TTKCommon.getTreeData(request);
			ArrayList<Object> alSearchParam = new ArrayList<Object>();
			MemberManager memberManager=this.getMemberManager();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strAddMember=getMemberForwardPath(strActiveSubLink);
			String strSubLinkCode=getSubLinkCode(strActiveSubLink);
			
			alSearchParam = new ArrayList<Object>();
			MemberDetailVO memberDetailVO = new MemberDetailVO();
			MemberAddressVO memberAddressVO=new MemberAddressVO();
			String strSelectedRoot = (String)generalForm.get("selectedroot");
			String strSelectedNode = (String)generalForm.get("selectednode");
			treeData.setSelectedRoot(-1);   //sets the selected rows
			
			//from tree with with min data
			memberVO=(MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
			alSearchParam.add(memberVO.getMemberSeqID());
			alSearchParam.add(strSubLinkCode);
			alSearchParam.add(strSwitchType);
			if(memberVO.getMemberSeqID()!=null)
			{
				memberDetailVO = memberManager.getMember(alSearchParam);
			}//end of if(memberVO.getMemberSeqID()!=null)
			DynaActionForm frmAddMember = (DynaActionForm)FormUtils.setFormValues("frmAddMember",memberDetailVO,
					this,mapping,request);
			memberAddressVO=memberDetailVO.getMemberAddressVO();
			frmAddMember.set("memberAddressVO", (DynaActionForm)FormUtils.setFormValues("frmMemberAddress",
					memberAddressVO,this,mapping,request));
			request.getSession().setAttribute("frmAddMember",frmAddMember);
			return this.getForward(strAddMember, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"memberdeail"));
		}//end of catch(Exception exp)
	}//end of doViewContacts(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	
	/**
	 * This method is used to get the delete record from the detail screen.
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
			log.debug("Inside AddMemberDetailAction doDelete");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			DynaActionForm frmMemberDetail = (DynaActionForm)form;
			String strFlag = "";
			String strSeqID = "";
			List<String> iResult;
			Long lngEndorsementSeqID = null;
			ArrayList<Object> alSearchParam = new ArrayList<Object>();
			TreeData treeData = TTKCommon.getTreeData(request);
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strSubLinkCode=getSubLinkCode(strActiveSubLink);
			String strPolicyDetails=getPolicyForwardPath(strActiveSubLink);
			MemberManager memberManager=this.getMemberManager();
			treeData.setSelectedRoot(-1);   //sets the selected rows
			strFlag = "MEMBER";
			strSeqID = (String)frmMemberDetail.get("memberSeqID");
			if(strSwitchType.equals("END")) // for Endorsement flow get the Endorsenet Seq ID from webBoard
			{
				lngEndorsementSeqID = WebBoardHelper.getEndorsementSeqId(request);
			}//end of if(strSwitchType.equals("END"))
			//create parameter to delete the member detail
			alSearchParam.add(strSubLinkCode);
			alSearchParam.add(strSwitchType);
			alSearchParam.add(strFlag);
			alSearchParam.add(strSeqID);
			alSearchParam.add(lngEndorsementSeqID);
			alSearchParam.add(TTKCommon.getUserSeqId(request));
			iResult= memberManager.deleteMember(alSearchParam);
			log.debug("Result value is "+iResult);
			 Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);
			 if(strActiveSubLink.trim().equals("Corporate Policy"))
             {
             	 String policy_status ="";
                  policy_status =    memberManager.getPolicyStatus(pol_seq_id);
                  strPolicyDetails=getForwardPath(strActiveSubLink,policy_status);
                  request.getSession().setAttribute("policy_status", policy_status);
            
             }
			
			
			return this.getForward(strPolicyDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"memberdeail"));
		}//end of catch(Exception exp)
	}//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.info("Inside AddMemberDetailAction doClose");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);

			ArrayList<Object> alSearchParam = new ArrayList<Object>();
			TreeData treeData = TTKCommon.getTreeData(request);
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strSubLinkCode=getSubLinkCode(strActiveSubLink);
			MemberManager memberManager = null;
			String strPolicyDetails=null;

			MemberVO memberVO = (MemberVO)treeData.getSelectedObject(String.valueOf(treeData.getSelectedRoot()),null);
			if(memberVO!=null)
			{
				//create search parameter to get the dependent list
				alSearchParam.add(memberVO.getPolicyGroupSeqID());
				alSearchParam.add(strSwitchType);
				alSearchParam.add(strSubLinkCode);
				//get the dependent list from database
			    memberManager=this.getMemberManager();
				memberVO.setMemberList(memberManager.getDependentList(alSearchParam));
				ArrayList<Object> alNodeSetting=new ArrayList<Object>();
				Node chNode=null;
				for(int i=0;memberVO.getMemberList()!=null && i<memberVO.getMemberList().size();i++)
				{
					chNode=treeData.copyNodeSetting();//new Node();
					if(((MemberVO)memberVO.getMemberList().get(i)).getCancleYN().equalsIgnoreCase("Y"))
					{
						chNode.setTextColor("red");
						chNode.setIconVisible(intCHILD_CANCEL_ICON,false);
					}//end of if(((MemberVO)alNodeMembers.get(i)).getCancleYN().equalsIgnoreCase("Y"))
					if(((MemberVO)memberVO.getMemberList().get(i)).getMemeligFlag().equalsIgnoreCase("N"))
					{
						chNode.setTextColor("red");
						chNode.setIconVisible(intCHILD_CANCEL_ICON,false);
					}
					
					else
					{
						chNode.setTextColor("black");
						if(strSwitchType.equals("END") && TTKCommon.isAuthorized(request,"Cancel"))
						{
							chNode.setIconVisible(intCHILD_CANCEL_ICON,true);
						}//end of if(strSwitchType.equals("END") && TTKCommon.isAuthorized(request,"Cancel"))
					}//end of else
					alNodeSetting.add(chNode);
				}//end of for(int i=0;i<alNodeMembers.size();i++)
				treeData.setNodeSettings(alNodeSetting);
			}//end of if(memberVO!=null)
			
			
			Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);
			String policy_status ="";
              if(strActiveSubLink.trim().equals("Corporate Policy"))
			{ 
				if(request.getSession().getAttribute("policy_status")!=null)
				{
				   policy_status =   (String)request.getSession().getAttribute("policy_status");
				   strPolicyDetails  =	getForwardPath(strActiveSubLink,policy_status);
				}
			 else if(pol_seq_id!=null  || pol_seq_id.longValue()!=0)
			{
                 if(policy_status.length()==0)
				{
					policy_status="FTS";
				}
			    strPolicyDetails=getForwardPath(strActiveSubLink,policy_status);
			}
			}
			else
			{
                strPolicyDetails=getPolicyForwardPath(strActiveSubLink);
            }
			
			return this.getForward(strPolicyDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			
			return this.processExceptions(request, mapping, new TTKException(exp,"memberdeail"));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	/**
	 * This method is used to load the gender based on the relationship.
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
			log.debug("Inside AddMemberDetailAction doChangeRelationship");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strAddMember=getMemberForwardPath(strActiveSubLink);
			DynaActionForm frmAddMember = (DynaActionForm)form;
			String strHiddenName = (String)frmAddMember.get("hiddenName");
			String strRelationTypeID = frmAddMember.getString("relationTypeID");
			String strGenderID = strRelationTypeID.substring(strRelationTypeID.indexOf("#")+1,
					strRelationTypeID.length());
			if(strRelationTypeID!=null && strRelationTypeID.length()>0)
			{
				strRelationTypeID=strRelationTypeID.substring(0,strRelationTypeID.indexOf("#"));
			}//end of if(strRelationTypeID!=null && strRelationTypeID.length()>0)
			if(!frmAddMember.getString("relationTypeID").equalsIgnoreCase("NSF#OTH"))
			{
				frmAddMember.set("name","");
				frmAddMember.set("disableYN","Y");
			}//end of if(!frmAddMember.getString("relationTypeID").equalsIgnoreCase("NSF#OTH"))
			else
			{
				frmAddMember.set("name",strHiddenName);
				frmAddMember.set("disableYN","N");
			}//end of else
			if(!strGenderID.equals("OTH"))
			{
				frmAddMember.set("genderTypeID",strGenderID);
			}//end of if(!strGenderID.equals("OTH"))
			else
			{
				frmAddMember.set("genderTypeID","");
			}//end of else
			frmAddMember.set("relationID",strRelationTypeID);
			frmAddMember.set("genderYN",strGenderID);
			frmAddMember.set("hiddenName",strHiddenName);
			frmAddMember.set("frmChanged","changed");
			return this.getForward(strAddMember, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"memberdeail"));
		}//end of catch(Exception exp)
	}//end of doChangeRelationship(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	
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
	 * This method returns the forward path for next view based on the Flow
	 *
	 * @param strActiveSubLink String current sublink
	 * @return strForwardPath String forward path for the next view
	 */
	private String getPolicyForwardPath(String strActiveSubLink)
	{
		String strForwardPath=null;
		
		if(strActiveSubLink.equals(strIndividualPolicy))
		{
			strForwardPath=strIndPolicyDetails;
		}//end of if(strActiveSubLink.equals("Individual Policy"))
		else if(strActiveSubLink.equals(strIndPolicyasGroup))
		{
			strForwardPath=strGrpPolicyDetails;
		}//end of else if(strActiveSubLink.equals(strIndPolicyasGroup))
		else if(strActiveSubLink.equals(strCorporatePolicy))
		{
			strForwardPath=strCorpPolicyDetails;
		}//end of else if(strActiveSubLink.equals(strCorporatePolicy))
		else if(strActiveSubLink.equals(strNonCorporatePolicy))
		{
			strForwardPath=strNonCorpPolicyDetails;
		}//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
		return strForwardPath;
	}//end of getForwardPath(String strActiveSubLink)
	
	/**
	 * This method returns the forward path for next view based on the Flow
	 *
	 * @param strActiveSubLink String current sublink
	 * @return strForwardPath String forward path for the next view
	 */
	private String getMemberForwardPath(String strActiveSubLink)
	{
		String strForwardPath=null;
		
		if(strActiveSubLink.equals(strIndividualPolicy))
		{
			strForwardPath=strIndAddMember;
		}//end of if(strActiveSubLink.equals("Individual Policy"))
		else if(strActiveSubLink.equals(strIndPolicyasGroup))
		{
			strForwardPath=strGrpAddMember;
		}//end of else if(strActiveSubLink.equals(strIndPolicyasGroup))
		else if(strActiveSubLink.equals(strCorporatePolicy))
		{
			strForwardPath=strCorpAddMember;
		}//end of else if(strActiveSubLink.equals(strCorporatePolicy))
		else if(strActiveSubLink.equals(strNonCorporatePolicy))
		{
			strForwardPath=strNonCorpAddMember;
		}//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
		return strForwardPath;
	}//end of getForwardPath(String strActiveSubLink)
	
	/**
	 * This method returns the SubLinkCode path for next view based on the Flow
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
		}//end of if(strActiveSubLink.equals("Individual Policy"))
		else if(strActiveSubLink.equals(strIndPolicyasGroup))
		{
			strSubLinkCode="IG";
		}//end of else if(strActiveSubLink.equals(strIndPolicyasGroup))
		else if(strActiveSubLink.equals(strCorporatePolicy))
		{
			strSubLinkCode="CP";
		}//end of else if(strActiveSubLink.equals(strCorporatePolicy))
		else if(strActiveSubLink.equals(strNonCorporatePolicy))
		{
			strSubLinkCode="NC";
		}//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
		return strSubLinkCode;
	}//end of getForwardPath(String strActiveSubLink)
	
	/**
	 * This method returns the Policy Type for next view based on the Flow
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
		}//end of if(strActiveSubLink.equals("Individual Policy"))
		else if(strActiveSubLink.equals(strIndPolicyasGroup))
		{
			strPolicyTypeCode="ING";
		}//end of else if(strActiveSubLink.equals(strIndPolicyasGroup))
		else if(strActiveSubLink.equals(strCorporatePolicy))
		{
			strPolicyTypeCode="COR";
		}//end of else if(strActiveSubLink.equals(strCorporatePolicy))
		else if(strActiveSubLink.equals(strNonCorporatePolicy))
		{
			strPolicyTypeCode="NCR";
		}//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
		return strPolicyTypeCode;
	}//end of getForwardPath(String strActiveSubLink)
	
	/**
     * Returns the RuleManager session object for invoking methods on it.
     * @return RuleManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private RuleManager getRuleManagerObject() throws TTKException
    {
        RuleManager ruleManager = null;
        try
        {
            if(ruleManager == null)
            {
                InitialContext ctx = new InitialContext();
                ruleManager = (RuleManager) ctx.lookup("java:global/TTKServices/business.ejb3/RuleManagerBean!com.ttk.business.administration.RuleManager");
                log.debug("Inside RuleManager: RuleManager: " + ruleManager);
            }//end if(ruleManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "memberdetail");
        }//end of catch
        return ruleManager;
    }//end of getRuleManagerObject()
    
    
    private String getForwardPath(String strActiveSubLink, String policy_status) {
		// TODO Auto-generated method stub
    	String strForwardPath=null;
    	if(strActiveSubLink.equals(strCorporatePolicy)  &&  policy_status.trim().equals("FTS"))
        {
            strForwardPath=strCorpPolicyDetails;

        } 
    	//end of if(strActiveSubLink.equals(strCorporatePolicy))
    	else if(strActiveSubLink.equals(strCorporatePolicy)  &&  policy_status.trim().equals("RTS"))
        {
    		
            strForwardPath=strCorpRenewMemberDetails;
           
        } 
		return strForwardPath;
	}
    
    
    
    
     
    public ActionForward doMemberTypeChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside AddMemberDetailAction doChangeRelationship");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strAddMember=getMemberForwardPath(strActiveSubLink);
			DynaActionForm frmAddMember = (DynaActionForm)form;
			
			
			String strHiddenName = (String)frmAddMember.get("member_type_id");
			if("4".equals(strHiddenName)||"5".equals(strHiddenName)){
				frmAddMember.set("memberFlag","Y");
			}
			else{
				frmAddMember.set("memberFlag","N");
			}
			
			
			frmAddMember.set("member_type_id",strHiddenName);
			
			
			
    
			return this.getForward(strAddMember, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"memberdeail"));
		}//end of catch(Exception exp)
	}//end of doChangeRelationship(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
    
    
    
    

}//end of class AddMemberDetailAction