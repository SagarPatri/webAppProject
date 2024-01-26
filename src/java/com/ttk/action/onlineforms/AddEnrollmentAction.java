/**
 * @ (#) AddEnrollmentAction.java Jan 16, 2008
 * Project      : TTK HealthCare Services
 * File         : AddEnrollmentAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Jan 16, 2008
 *
 * @author       :  Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 * Modified by   :
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
import com.ttk.action.tree.TreeData;
import com.ttk.business.administration.RuleManager;
import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberAddressVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.OnlineAccessPolicyVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class AddEnrollmentAction extends TTKAction
{
	private static Logger log = Logger.getLogger( AddEnrollmentAction.class );
	//for forwarding
	private static final String strOnlineEmpDetails="onlineEmpDetails";
	private static final String strSaveOnlineEmpDetails="saveOnlineEmpDetails";
	private static final String strSendOnlineEmpDetails="sendOnlineEmpDetails";


	private static final String strClose="closeAddEnrollment";
	//  Exception Message Identifier
    private static final String strEnrollment="enrollment";

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
    public ActionForward doViewEmpDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.debug("Inside AddEnrollmentAction doViewEmpDetails");
    		DynaActionForm frmAddEnrollment= (DynaActionForm) form;
    		OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		StringBuffer sbfCaption=new StringBuffer();
    		OnlineAccessPolicyVO onlinePolicyVO=null;
 			String strSelectedRoot=request.getParameter("selectedroot");
 			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
 			int iSelectedRoot= TTKCommon.getInt(request.getParameter("selectedroot"));
 			MemberDetailVO memberDetailVO= new MemberDetailVO();
 			MemberAddressVO memberAddressVO=new MemberAddressVO();
 			memberDetailVO.setMemberAddressVO(memberAddressVO);
 			ArrayList<Object> alAddEnrollment=new ArrayList<Object>();
 			MemberVO memberVO=(MemberVO)treeData.getRootData().get(iSelectedRoot);
 			if(!strSelectedRoot.equals(""))// on clicking the edit link
 			{
 				alAddEnrollment.add(memberVO.getPolicyGroupSeqID());
 				alAddEnrollment.add(memberVO.getPolicySeqID());
 				memberDetailVO=onlineAccessObject.selectEnrollment(alAddEnrollment);
 				if(userSecurityProfile.getLoginType().equals("H"))
 	 			{
 					onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
 	 				sbfCaption=sbfCaption.append("Employee Details - Edit - [").append(onlinePolicyVO.getPolicyNbr()).append("] [").
 	 				append(userSecurityProfile.getGroupID()).append("] [").append(onlinePolicyVO.getGroupName()).append("]");
 	 			}//end of if(userSecurityProfile.getLoginType().equals("H"))
 	 			else if(userSecurityProfile.getLoginType().equals("E"))
 	 			{
 	 				sbfCaption=sbfCaption.append("Employee Details - Edit - [").append(userSecurityProfile.getPolicyNo()).append("] [").
 					append(userSecurityProfile.getGroupID()).append("] [").append(memberVO.getGroupName()).append("]");
 	 			}//end of else if(userSecurityProfile.getLoginType().equals("E"))
 	 			else if(userSecurityProfile.getLoginType().equals("OCI"))
 	 			{
 	 				sbfCaption=sbfCaption.append("Employee Details - Edit - [").append(userSecurityProfile.getPolicyNo()).append("]");
 	 			}//end of else if(userSecurityProfile.getLoginType().equals("OCI"))
 			}//end of if(!strSelectedRoot.equals(""))
 			frmAddEnrollment= (DynaActionForm)FormUtils.setFormValues("frmAddEnrollment",memberDetailVO,
	 					this,mapping,request);
	 		frmAddEnrollment.set("memberAddressVO", FormUtils.setFormValues("frmMemberAddress",
	 					memberDetailVO.getMemberAddressVO(),this,mapping,request));
	 		if(memberDetailVO.getEmpStatusTypeID().equals("POC"))
	 		{
	 			request.setAttribute("statusinfo","error.onlineforms.cancelled");
	 		}//end of if(memberDetailVO.getEmpStatusTypeID().equals("POC"))
 			ArrayList alLocation=new ArrayList();
 			alLocation = onlineAccessObject.getLocation(memberVO.getPolicySeqID());
			request.getSession().setAttribute("alLocation",alLocation);
			if(userSecurityProfile.getLoginType().equals("OCI"))
			{
				frmAddEnrollment.set("allowCityBankYN","Y");
			}//end of if(userSecurityProfile.getLoginType().equals("OCI"))
			else
			{
				frmAddEnrollment.set("allowCityBankYN","N");
			}//end of else
			frmAddEnrollment.set("allowModiYN",TTKCommon.checkNull(memberVO.getAllowModiYN()));
 			frmAddEnrollment.set("selectedRoot",strSelectedRoot);
 			frmAddEnrollment.set("caption",sbfCaption.toString());

 			//Added for IBM...1

			frmAddEnrollment.set("OPT",memberDetailVO.getStopOPtInYN());//Getting the checkbox values.
			frmAddEnrollment.set("windowsOPTYN",memberDetailVO.getwindowsOPTYN());//Getting windowsperiod
			frmAddEnrollment.set("chkpreauthclaims",memberDetailVO.getchkpreauthclaims());//Getting Pre-Auth/claim existence

			frmAddEnrollment.set("EmailPh",memberDetailVO.getValidEmailPhYN()); // Added for KOC-1216


			if(memberDetailVO.getchkpreauthclaims().equalsIgnoreCase("Y"))
			{
				request.setAttribute("chkPreAuth","message.chkPreAuthClaim");//Msg Display on Preauth-ADDED BY PRAVEEN
			}
			else if(!(memberDetailVO.getwindowsOPTYN().equalsIgnoreCase("Y")))
			{
				request.setAttribute("chkWindowsPeriod","message.chkWindowsPeriod");//Msg Display on WindowsPeriod-ADDED BY PRAVEEN
			}
			
			frmAddEnrollment.set("EmailId2",memberDetailVO.getMemberAddressVO().getEmailID2());
			frmAddEnrollment.set("MobileNo",memberDetailVO.getMemberAddressVO().getMobileNbr());
			frmAddEnrollment.set("MarriageHideYN",memberDetailVO.getMarriageHideYN());//koc for griavance
			

			//End of IBM....
 			request.getSession().setAttribute("frmAddEnrollment",frmAddEnrollment);
    		return this.getForward(strOnlineEmpDetails,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request,mapping,new TTKException(exp,strEnrollment));
    	}//end of catch(Exception exp)
    }//end of doViewEmpDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse
    																									//response)
    /**
	 *
	 * This method is used to navigate to detail screen to add a record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside AddEnrollmentAction doAdd");
			setOnlineLinks(request);
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			String strSelectedRoot=request.getParameter("selectedroot");
			OnlineAccessPolicyVO onlinePolicyVO=null;
			StringBuffer sbfCaption=new StringBuffer();
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			sbfCaption=sbfCaption.append("Employee Details - Add - [").append(onlinePolicyVO.getPolicyNbr()).append("] [").
			append(userSecurityProfile.getGroupID()).append("] [").append(onlinePolicyVO.getGroupName()).append("]");
			MemberDetailVO memberDetailVO= new MemberDetailVO();
			MemberAddressVO memberAddressVO=new MemberAddressVO();
			memberDetailVO.setMemberAddressVO(memberAddressVO);
			ArrayList<Object> alAddEnrollment=new ArrayList<Object>();
			alAddEnrollment.add(memberDetailVO.getPolicyGroupSeqID());
			alAddEnrollment.add(onlinePolicyVO.getPolicySeqID());
			memberDetailVO=onlineAccessObject.selectEnrollment(alAddEnrollment);
			DynaActionForm frmAddEnrollment= (DynaActionForm)FormUtils.setFormValues("frmAddEnrollment",memberDetailVO,
					this,mapping,request);
			frmAddEnrollment.set("memberAddressVO", FormUtils.setFormValues("frmMemberAddress",
					memberDetailVO.getMemberAddressVO(),this,mapping,request));
			frmAddEnrollment.set("selectedRoot",strSelectedRoot);
			frmAddEnrollment.set("caption",sbfCaption.toString());
			ArrayList alLocation=new ArrayList();
			alLocation = onlineAccessObject.getLocation(onlinePolicyVO.getPolicySeqID());
			request.getSession().setAttribute("alLocation",alLocation);
			request.getSession().setAttribute("frmAddEnrollment",frmAddEnrollment);
			return this.getForward(strOnlineEmpDetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strEnrollment));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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

			log.debug("Inside AddEnrollmentAction doSave");
			setOnlineLinks(request);
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			DynaActionForm frmAddEnrollment =(DynaActionForm)form;
 			int iSelectedRoot= TTKCommon.getInt(request.getParameter("selectedroot"));
			OnlineAccessPolicyVO onlinePolicyVO=null;
			String strUserType=null;
			//for Individual login no privilege to save enrollment
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
				strUserType="HR";
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E"))
			{
				strUserType="EMP";
			}//end of else if(userSecurityProfile.getLoginType().equals("E"))
			StringBuffer sbfCaption=new StringBuffer();
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			MemberDetailVO memberDetailVO = new MemberDetailVO();
			MemberAddressVO memberAddressVO = new MemberAddressVO();
			String strSelectedRoot=frmAddEnrollment.getString("selectedRoot");
			memberDetailVO=(MemberDetailVO)FormUtils.getFormValues(frmAddEnrollment,this,mapping,request);
			ActionForm frmMemberAddress=(ActionForm)frmAddEnrollment.get("memberAddressVO");
			memberAddressVO=(MemberAddressVO)FormUtils.getFormValues(frmMemberAddress,"frmMemberAddress",
					this,mapping,request);
			memberDetailVO.setMemberAddressVO(memberAddressVO);
			
			memberDetailVO.setEmailID2((String)frmAddEnrollment.get("EmailId2"));
			memberDetailVO.setMobileNo((String)frmAddEnrollment.get("MobileNo"));


			if(userSecurityProfile.getLoginType().equals("H"))
			{
				memberDetailVO.setPolicySeqID(onlinePolicyVO.getPolicySeqID());
				memberDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E"))
			{
				memberDetailVO.setPolicySeqID(memberDetailVO.getPolicySeqID());
				//memberDetailVO.setUpdatedBy(TTKCommon.getLong(userSecurityProfile.getUSER_ID()));
				memberDetailVO.setUpdatedBy(new Long(1));
			}//end of else if(userSecurityProfile.getLoginType().equals("E"))

			if(!strSelectedRoot.equals(""))//on save from edit mode
			{
				//For Editing this particular seq id.
				memberDetailVO.setPolicyGroupSeqID(memberDetailVO.getPolicyGroupSeqID());
			}//end of if(!strSelectedRoot.equals(""))
			else
			{
				memberDetailVO.setPolicyGroupSeqID(null);//adding new enrollment
			}//end of else

			//Added for IBM....2

			// added by Rekha 13.07.2012
			//if("Y".equals(TTKCommon.checkNull(frmAddEnrollment.get("OPT"))))
			if("N".equals(TTKCommon.checkNull(frmAddEnrollment.get("OPT"))))//Make checkbox enable or disable overwritten by Praveen Nov8.2012
			{
				//memberDetailVO.setStopOPtInYN("Y");
				memberDetailVO.setStopOPtInYN("N");//overwritten by Praveen Nov8.2012
			}
			else
			{
				//memberDetailVO.setStopOPtInYN("N");
				memberDetailVO.setStopOPtInYN("Y");//overwritten by Praveen Nov8.2012
			}
			// end added by Rekha 13.07.2012
			if("N".equals(TTKCommon.checkNull(frmAddEnrollment.get("EmailPh"))))//checked
			{
				memberDetailVO.setValidEmailPhYN("N");
			}
			else
			{
				memberDetailVO.setValidEmailPhYN("Y");//unchecked
			}

			//Add Ended......
			long lCount=onlineAccessObject.saveEnrollment(memberDetailVO,strUserType);
			if(strSelectedRoot.equals(""))// for initalizing the form on click of save button in add flow.
			{
				memberDetailVO= new MemberDetailVO();
				memberAddressVO=new MemberAddressVO();
				memberDetailVO.setMemberAddressVO(memberAddressVO);
				ArrayList<Object> alAddEnrollment=new ArrayList<Object>();
				alAddEnrollment.add(memberDetailVO.getPolicyGroupSeqID());
				alAddEnrollment.add(onlinePolicyVO.getPolicySeqID());
				memberDetailVO=onlineAccessObject.selectEnrollment(alAddEnrollment);
				frmAddEnrollment= (DynaActionForm)FormUtils.setFormValues("frmAddEnrollment",memberDetailVO,
						this,mapping,request);
				frmAddEnrollment.set("memberAddressVO", FormUtils.setFormValues("frmMemberAddress",
						memberDetailVO.getMemberAddressVO(),this,mapping,request));
				request.setAttribute("updated","message.addedSuccessfully");
				sbfCaption=sbfCaption.append("Employee Details - Add - [").append(onlinePolicyVO.getPolicyNbr()).append("] [").
				append(userSecurityProfile.getGroupID()).append("] [").append(onlinePolicyVO.getGroupName()).append("]");
			}//end of if(strSelectedRoot.equals(""))
			// Added Rule related validations as per Sreeraj instructions on 27/02/2008
			if(lCount <0){
					//log.info("Errors are there..........");
					RuleManager ruleManager=this.getRuleManagerObject();
					//RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();
					ArrayList alValidationError=ruleManager.getValidationErrorList(new Long(lCount));

					//prepare Error messages
					//ArrayList alErrorMessage=ruleXMLHelper.prepareErrorMessage(alValidationError,request);
					request.setAttribute("BUSINESS_ERRORS",alValidationError);
					return this.getForward(strSaveOnlineEmpDetails, mapping, request);
			}//end of addition

			if(lCount>0)
			{
				if(!strSelectedRoot.equals(""))//on save from edit mode
				{
					request.setAttribute("updated","message.savedSuccessfully");
					if(userSecurityProfile.getLoginType().equals("H"))
	 	 			{
	 					//onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
	 	 				sbfCaption=sbfCaption.append("Employee Details - Edit - [").append(onlinePolicyVO.getPolicyNbr()).append("] [").
	 	 				append(userSecurityProfile.getGroupID()).append("] [").append(onlinePolicyVO.getGroupName()).append("]");
	 	 			}//end of if(userSecurityProfile.getLoginType().equals("H"))
	 	 			else if(userSecurityProfile.getLoginType().equals("E"))
	 	 			{
	 	 				TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
	 	 				MemberVO memberVO=(MemberVO)treeData.getRootData().get(iSelectedRoot);
	 	 				sbfCaption=sbfCaption.append("Employee Details - Edit - [").append(userSecurityProfile.getPolicyNo()).append("] [").
	 					append(userSecurityProfile.getGroupID()).append("] [").append(memberVO.getGroupName()).append("]");
	 	 			}//end of else if(userSecurityProfile.getLoginType().equals("E"))
				}//end of if(!strSelectedRoot.equals(""))
			}//end of if(iCount>0)
			ArrayList alLocation=new ArrayList();
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				alLocation = onlineAccessObject.getLocation(onlinePolicyVO.getPolicySeqID());
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E"))
			{
				alLocation = onlineAccessObject.getLocation(memberDetailVO.getPolicySeqID());
			}//end of else if(userSecurityProfile.getLoginType().equals("E"))
			request.getSession().setAttribute("alLocation",alLocation);
			frmAddEnrollment.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmAddEnrollment",frmAddEnrollment);
			return this.getForward(strSaveOnlineEmpDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request,mapping,new TTKException(exp,strEnrollment));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doSend(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSend method of WebConfigAction");
			setOnlineLinks(request);
//			DynaActionForm frmAddEnrollment =(DynaActionForm)request.getSession().getAttribute("frmAddEnrollment");
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
 			int iSelectedRoot= TTKCommon.getInt(request.getParameter("selectedroot"));
			MemberVO memberVO=(MemberVO)treeData.getRootData().get(iSelectedRoot);
			MemberDetailVO memberDetailVO = new MemberDetailVO();
			ArrayList<Object> alAddEnrollment=new ArrayList<Object>();
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			alAddEnrollment.add(memberVO.getPolicyGroupSeqID());
			alAddEnrollment.add(memberVO.getPolicySeqID());
			memberDetailVO=onlineAccessObject.selectEnrollment(alAddEnrollment);
			Long lngUserID = null;
			if(userSecurityProfile.getLoginType().equals("H"))
	 		{
				lngUserID = TTKCommon.getUserSeqId(request);
	 		}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else
	 		{
	 			//lngUserID = TTKCommon.getLong(userSecurityProfile.getUSER_ID());
	 			lngUserID =new Long(1);
	 		}//end of else
			log.debug("memberVO tree datat ***************:"+memberVO.getPolicyGroupSeqID());
			String strOnlineEnrollment ="NEW_ONLINE_ENR_EMPLOYEE";
			String strEmailID = memberDetailVO.getMemberAddressVO().getEmailID();
			log.debug("email id is *(**********"+strEmailID);
			if(strEmailID==null || strEmailID=="")
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.weblogin.emailrequired");
				throw expTTK;
			}//end of if(CodingWebBoardHelper.checkWebBoardId(request)==null)

			Long lngPolicyGrpSeqID = Long.valueOf(memberVO.getPolicyGroupSeqID());

//			WebConfigInfoVO webConfigInfoVO = new WebConfigInfoVO();
			CommunicationManager communicationManager = this.getCommunicationManagerObject();
			/*log.info("lngPolicyGrpSeqID is :"+lngPolicyGrpSeqID);
			log.info("strOnlineEnrollment is :"+strOnlineEnrollment);
			log.info("User seq ID is :"+TTKCommon.getUserSeqId(request));*/

			communicationManager.sendOnlineSoftCopy(strOnlineEnrollment,lngPolicyGrpSeqID,lngUserID);
			request.setAttribute("updated","message.sendSuccessfully");
			return this.getForward(strSendOnlineEmpDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strEnrollment));
		}//end of catch(Exception exp)
	}//end of doSend(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside doReset of AddEnrollmentAction");
			setOnlineLinks(request);
			DynaActionForm frmAddEnrollment=(DynaActionForm)form;
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			OnlineAccessPolicyVO onlinePolicyVO=null;
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			StringBuffer sbfCaption=new StringBuffer();
			String strSelectedRoot=frmAddEnrollment.getString("selectedRoot");
			MemberDetailVO memberDetailVO= new MemberDetailVO();
			MemberAddressVO memberAddressVO=new MemberAddressVO();
			memberDetailVO.setMemberAddressVO(memberAddressVO);
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			ArrayList<Object> alAddEnrollment=new ArrayList<Object>();
			if(!strSelectedRoot.equals(""))// on clicking the edit link
			{
				MemberVO memberVO=(MemberVO)treeData.getRootData().get(TTKCommon.getInt(strSelectedRoot));
				alAddEnrollment.add(memberVO.getPolicyGroupSeqID());
				alAddEnrollment.add(memberVO.getPolicySeqID());
				memberDetailVO=onlineAccessObject.selectEnrollment(alAddEnrollment);

				if(userSecurityProfile.getLoginType().equals("H")){
					sbfCaption=sbfCaption.append("Employee Details - Edit - [").append(onlinePolicyVO.getPolicyNbr()).append("] [").
					append(userSecurityProfile.getGroupID()).append("] [").append(onlinePolicyVO.getGroupName()).append("]");
				}//end of if(userSecurityProfile.getLoginType().equals("H"))
				else{
					sbfCaption=sbfCaption.append("Employee Details - Edit - [").append(userSecurityProfile.getPolicyNo()).append("] [").
					append(userSecurityProfile.getGroupID()).append("] [").append(memberVO.getGroupName()).append("]");
				}//end of else
			}//end of if(!strSelectedRoot.equals(""))
			else
			{
				alAddEnrollment.add(memberDetailVO.getPolicyGroupSeqID());
				alAddEnrollment.add(onlinePolicyVO.getPolicySeqID());
				memberDetailVO=onlineAccessObject.selectEnrollment(alAddEnrollment);
				sbfCaption=sbfCaption.append("Employee Details - Add - [").append(onlinePolicyVO.getPolicyNbr()).append("] [").
				append(userSecurityProfile.getGroupID()).append("] [").append(onlinePolicyVO.getGroupName()).append("]");
			}//end of else
			frmAddEnrollment= (DynaActionForm)FormUtils.setFormValues("frmAddEnrollment",memberDetailVO,
					this,mapping,request);
			frmAddEnrollment.set("memberAddressVO", FormUtils.setFormValues("frmMemberAddress",
					memberDetailVO.getMemberAddressVO(),this,mapping,request));
			frmAddEnrollment.set("selectedRoot",strSelectedRoot);
			frmAddEnrollment.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmAddEnrollment",frmAddEnrollment);
			return this.getForward(strOnlineEmpDetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strEnrollment));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to close the current page and return to previous page.
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
    		log.debug("Inside doClose of AddEnrollmentAction");
    		return mapping.findForward(strClose);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strEnrollment));
		}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
	 * Returns the OnlineAccessManager session object for invoking methods on it.
	 * @return OnlineAccessManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private OnlineAccessManager getOnlineAccessManager() throws TTKException
	{
		OnlineAccessManager onlineAccessManager = null;
		try
		{
			if(onlineAccessManager == null)
			{
				InitialContext ctx = new InitialContext();
				onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
			}//end ofif(onlineAccessManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strEnrollment);
		}//end of catch
		return onlineAccessManager;
	}//end getOnlineAccessManager()

	 // Added the following piece of code for integrating Rule related validation
    // as per Sreeraj's instruction
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

    /**
	 * Returns the CommunicationManager session object for invoking methods on it.
	 * @return CommunicationManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private CommunicationManager getCommunicationManagerObject() throws TTKException
	{
		CommunicationManager communicationManager = null;
		try
		{
			if(communicationManager == null)
			{
				InitialContext ctx = new InitialContext();
				communicationManager = (CommunicationManager) ctx.lookup("java:global/TTKServices/business.ejb3/CommunicationManagerBean!com.ttk.business.common.messageservices.CommunicationManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "memberdetail");
		}//end of catch
		return communicationManager;
	}//end of getCommunicationManagerObject()
}//end of AddEnrollmentAction()
