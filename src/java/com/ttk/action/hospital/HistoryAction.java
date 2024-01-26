/**
 * @ (#) HistoryAction.java
 * Project      : TTK HealthCare Services
 * File         : HistoryAction.java
 *  Author       :Satya Moganti
 * Company      : Rcs Technologies
 * Date Created : march 24 ,2014
 *
 * @author       :Satya moganti
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.hospital;

import java.util.ArrayList;

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
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.action.tree.TreeData;
import com.ttk.business.accountinfo.AccountInfoManager;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.business.preauth.MemberHistoryManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.HospClaimsWebBoardHelper;
import com.ttk.common.HospPreAuthWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.OnlineAccessPolicyVO;
import com.ttk.dto.preauth.CitibankHistoryVO;
import com.ttk.dto.preauth.ClaimantHistoryVO;
import com.ttk.dto.preauth.PolicyHistoryVO;
import com.ttk.dto.preauth.PreAuthHistoryVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This class is reusable for searching of history of pre-auth and claim.
 *  This class also provides option for viewing the detail history of pre-auth/claim.
 */

public class HistoryAction extends TTKAction  {

	private static Logger log = Logger.getLogger( HistoryAction.class ); // Getting Logger for this Class file

	//	Modes
	private  static final String strForward="Forward";
	private  static final String strBackward="Backward";

	//  Action mapping forwards
	private static final String strPolicyHistory="pathistorydetails";
	private static final String strClaimPolicyHistory="claimhistorydetails";
	private static final String strOnlinePolicyHistory="accountinfohistorydetails";

	//Exception Message Identifier
    private static final String strHistoryError="Details";
    private static final String strClaims="Claims Status";
    private static final String strPreAuth="Cashless Status";

    //kocnewhosp1
    private static final String strDashBoard="PreAuthHomeDashBoard";
    private static final String strDashBoardPreAuth="PreAuthHomeDashBoardPreAuth";
    private static final String strDashBoardClaim="PreAuthHomeDashBoardClaim";
    private static final String strNewTab="Cashless DashBoard";

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
																HttpServletResponse response) throws Exception {
		log.debug("Inside HistoryAction doChangeWebBoard");
		return doView(mapping,form,request,response);
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  //HttpServletResponse response)

	
	/**
     * This method is used to get History Details.
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
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.info("Inside HistoryAction doView");
			Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");

			MemberHistoryManager memberHistoryManagerObject=this.getMemberHistoryManagerObject();
			
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");

			//AccountInfoManager accountInfoManagerObject=this.getAccountInfomanagerObject();
			//check if user trying to hit the tab directly with out selecting the hospital
			String strLink = TTKCommon.getActiveSubLink(request);
			//TreeData treeData = TTKCommon.getTreeData(request);
			//DynaActionForm frmHistoryDetail=(DynaActionForm)request.getAttribute("frmHistoryDetail");
			Document historyDoc=null;
			String strFwdHistory = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			String strEnrollmentID = "";
			StringBuffer strCaption=new StringBuffer();
			DynaActionForm frmHosHistoryDetail=(DynaActionForm)form;
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)frmHosHistoryDetail).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		/*	frmHosHistoryDetail.set("selectedroot",TTKCommon.checkNull(request.getParameter("selectedroot")));
			frmHosHistoryDetail.set("selectednode",TTKCommon.checkNull(request.getParameter("selectednode")));*/
			TableData tableData=TTKCommon.getTableData(request);
			
			if(strLink.equals(strPreAuth))//if it is from PreAuth flow
			{
			if(HospPreAuthWebBoardHelper.checkWebBoardId(request)==null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.PreAuthorization.required");
				throw expTTK;
			}//end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
			}
			if(strLink.equals(strClaims))//if it is from claims flow
			{
				//check if user trying to hit the tab directly with out selecting the claim
				if(HospClaimsWebBoardHelper.checkWebBoardId(request)==null)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Claims.required");
					throw expTTK;
				}//end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
			}
			
			if(strLink.equals(strPreAuth))
			{
				strFwdHistory = strPolicyHistory;
				strClaimantName = HospPreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = HospPreAuthWebBoardHelper.getWebBoardDesc(request);
				if(!"".equals(HospPreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						HospPreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+HospPreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(HospPreAuthWebBoardHelper.getEnrollmentId(request).trim())&& HospPreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals(strClaims))
			{
				strFwdHistory = strClaimPolicyHistory;
				strClaimantName = HospClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = HospClaimsWebBoardHelper.getWebBoardDesc(request);
				if(!"".equals(HospClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						HospClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+HospClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(HospClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	HospClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else if(strLink.equals("Claims"))
		
			if(!TTKCommon.checkNull(strClaimantName).equals("")){
				strCaption.append(" [ ").append(strClaimantName).append(" ]").toString();
			}//end of if(!TTKCommon.checkNull(strClaimantName).equals(""))
			frmHosHistoryDetail.set("caption",strCaption.toString());
			
			
			if(strLink.equals(strPreAuth))
			{
				historyDoc=memberHistoryManagerObject.getHospitalHistory("PAT",HospPreAuthWebBoardHelper.getPreAuthSeqId(request),userSecurityProfile.getHospSeqId());
			
			}//end of if(strLink.equals("Account Info"))
			else if(strLink.equalsIgnoreCase(strClaims))
			{
				historyDoc=memberHistoryManagerObject.getHospitalHistory("CLM",HospClaimsWebBoardHelper.getClaimsSeqId(request),userSecurityProfile.getHospSeqId());

			//historyDoc=//memberHistoryManagerObject.
			}//end of else
			
				request.setAttribute("historyDoc",historyDoc);
		//	}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				frmHosHistoryDetail.set("caption",strCaption.toString());
			return this.getForward(strFwdHistory,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHistoryError));
		}//end of catch(Exception exp)
	}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	

	
	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private MemberHistoryManager getMemberHistoryManagerObject() throws TTKException
	{
		MemberHistoryManager memberHistoryManager = null;
		try
		{
			if(memberHistoryManager == null)
			{
				InitialContext ctx = new InitialContext();
				//memberHistoryManager = (MemberHistoryManager) ctx.lookup(MemberHistoryManager.class.getName());
				memberHistoryManager = (MemberHistoryManager) ctx.lookup("java:global/TTKServices/business.ejb3/MemberHistoryManagerBean!com.ttk.business.preauth.MemberHistoryManager");
			}//end of if(memberHistoryManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strHistoryError);
		}//end of catch
		return memberHistoryManager;
	}//end of getMemberHistoryManagerObject()
	
	
	/** kocnewhosp1
	 * This Method navigates user to the Cashless Dashboard screen.
	 */
	
	
	public ActionForward doDashBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			//setLinks(request);
			/*String sublink	=	"";
			String leftlink	=	"";
			String tab		=	"";
			request.setAttribute(leftlink, "Hospital Information");
			request.setAttribute(sublink, "Home");
			request.setAttribute(tab, "Cashless DashBoard");*/
			
			/*setLinks(request);
			log.debug("Inside HistoryAction doDashBoard");
			Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");
			MemberHistoryManager memberHistoryManagerObject=this.getMemberHistoryManagerObject();
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
		*/	
			//setOnlineLinks(request);
			//setLinks(request);
			String strActiveTab=TTKCommon.getActiveTab(request);
			
			//return mapping.findForward(strDashBoard);
			return this.getForward(strDashBoard,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strHistoryError));
		}//end of catch(Exception exp)
	}
		
		public ActionForward doDashBoardDataPreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception{
			try{
				setOnlineLinks(request);
				//setLinks(request);
				
				/*String sublink	=	"";
				String leftlink	=	"";
				String tab		=	"";
				request.setAttribute(leftlink, "Hospital Information");
				request.setAttribute(sublink, "Home");
				request.setAttribute(tab, "Cashless DashBoard");*/
				String strForwardPath="";
				String strLink = TTKCommon.checkNull(request.getParameter("leftlink"));
		        String strSubLink = TTKCommon.checkNull(request.getParameter("sublink"));
		        String strTab = TTKCommon.checkNull(request.getParameter("tab"));
		        
				log.debug("Inside doDashBoardDataPreAuth doDashBoard");
				Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");
				//MemberHistoryManager memberHistoryManagerObject=this.getMemberHistoryManagerObject();
				//UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
				
				log.info("Inside doDashBoardDataPreAuth method of Historty Action");
				
				
				String strActiveTab=TTKCommon.getActiveTab(request);
				
				if(strTab.equals("Cashless DashBoard")){
					
					strForwardPath=strDashBoardPreAuth;
				}else if(strTab.equals("Claims DashBoard")){
					
					strForwardPath=strDashBoardClaim;
				}

				 
				//return mapping.findForward(strDashBoardPreAuth);
				return this.getForward(strForwardPath,mapping,request);
				 
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp, strHistoryError));
			}//end of catch(Exception exp)
			 
	}//end of doDashBoardDataPreAuth method
	
	
	
}//end of HistoryAction