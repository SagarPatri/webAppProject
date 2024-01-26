/**
 * @ (#) ClaimsIntimationAction.java Mar 24, 2008
 * Project      : TTK HealthCare Services
 * File         : ClaimsIntimationAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Mar 24, 2008
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
import com.ttk.action.table.TableData;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.onlineforms.OnlineIntimationVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class ClaimsIntimationAction extends TTKAction
{
	private static Logger log = Logger.getLogger( ClaimsIntimationAction.class );
	//for forwarding
	private static final String strClaimsIntimationList="claimsintimationlist";
	private static final String strEditClaimsIntimation="editclaimsintimation";
	private static final String strSaveClaimsIntimation="saveclaimsintimation";
	private static final String strEmpClaimsIntimationList="empclaimsintimationlist";
	private static final String strEmpEditClaimsIntimation="empeditclaimsintimation";
	private static final String strEmpSaveClaimsIntimation="empsaveclaimsintimation";
	private static final String strClaimIntimation="Claims Intimation";
	//On select IDs kocbroker
	private static final String strClaimBroIntimation="Claims";
	private static final String strClaimsIntimationBroList="claimsintimationbrolist";
	
	//Exception Message Identifier
	private static final String strClaimsIntimation="claimsintimation";

	/**
	 * This method is called from the struts framework.
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
			log.debug("Inside the doAdd method of ClaimsIntimationAction");
			setOnlineLinks(request);
			DynaActionForm frmClaimsIntimationDetails = (DynaActionForm)form;
			DynaActionForm frmPreAuthIntimation=(DynaActionForm)request.getSession()
											.getAttribute("frmPreAuthIntimation");
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
												getAttribute("UserSecurityProfile");
			String strForwardPath="";
			String strEmpNbr ="";
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				strForwardPath=strEditClaimsIntimation;
				strEmpNbr= "";
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals(strClaimIntimation))
			{
				strForwardPath=strEmpEditClaimsIntimation;
				strEmpNbr = userSecurityProfile.getUSER_ID();
			}//end of else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals
											//(strClaimIntimation))
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append(" - [").append(frmPreAuthIntimation.get("policyNbr")).append("]").append(" [")
								.append(frmPreAuthIntimation.get("EnrollmentNbr")).append("]").append(" [")
								.append(frmPreAuthIntimation.get("memberName")).append("]");
			Long lngPolicyGroupSeqId=null;
			lngPolicyGroupSeqId=TTKCommon.getLong(frmPreAuthIntimation.getString("policyGrpSeqID"));
			String strPolicyNbr = (String) frmPreAuthIntimation.get("policyNbr");
			ArrayList alMemberName=new ArrayList();
			alMemberName=onlineAccessObject.getOnlineMember(lngPolicyGroupSeqId);
			OnlineIntimationVO onlineIntimationVO = new OnlineIntimationVO();
			onlineIntimationVO = onlineAccessObject.getEmpContactInfo(strEmpNbr, lngPolicyGroupSeqId, strPolicyNbr);

			frmClaimsIntimationDetails= (DynaActionForm)FormUtils.setFormValues("frmClaimsIntimationDetails",
																onlineIntimationVO,this,mapping,request);
			request.getSession().setAttribute("alMemberName",alMemberName);
			frmClaimsIntimationDetails.set("showSave","Y");
			frmClaimsIntimationDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmClaimsIntimationDetails",frmClaimsIntimationDetails);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strClaimsIntimation));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
    public ActionForward doViewIntimationDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.debug("Inside ClaimsIntimationAction doViewIntimationDetails");
    		DynaActionForm frmClaimsIntimationDetails= (DynaActionForm) form;
    		DynaActionForm frmPreAuthIntimation=(DynaActionForm)request.getSession()
    										.getAttribute("frmPreAuthIntimation");
    		OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
 			OnlineIntimationVO onlineIntimationVO = new OnlineIntimationVO();
			TableData tableData = TTKCommon.getTableData(request);
			Long lngIntimationSeqID = null;
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append(" - [").append(frmPreAuthIntimation.get("policyNbr")).append("]").append(" [")
								.append(frmPreAuthIntimation.get("EnrollmentNbr")).append("]").append(" [")
									.append(frmPreAuthIntimation.get("memberName")).append("]");
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				onlineIntimationVO=(OnlineIntimationVO)tableData.getRowInfo(Integer.parseInt(
															request.getParameter("rownum")));
				lngIntimationSeqID= onlineIntimationVO.getIntimationSeqID();
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
				getAttribute("UserSecurityProfile");
			String strForwardPath="";
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				onlineIntimationVO= onlineAccessObject.selectIntimation(lngIntimationSeqID,TTKCommon.getActiveSubLink(request));
				strForwardPath=strEditClaimsIntimation;
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals(strClaimIntimation))
			{
				onlineIntimationVO= onlineAccessObject.selectIntimation(lngIntimationSeqID,"Claims");	
				strForwardPath=strEmpEditClaimsIntimation;
			}//end of else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals
					//(strClaimIntimation))
			
			
			//On select IDs kocbroker
			else if(userSecurityProfile.getLoginType().equals("B") && strActiveSubLink.equals(strClaimBroIntimation))
			{
				onlineIntimationVO= onlineAccessObject.selectIntimation(lngIntimationSeqID,"Claims");	
				strForwardPath=strClaimsIntimationBroList;
			}//end of else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals
					//(strClaimIntimation))
			
			frmClaimsIntimationDetails= (DynaActionForm)FormUtils.setFormValues("frmClaimsIntimationDetails",
																	onlineIntimationVO,this,mapping,request);
			Long lngPolicyGroupSeqId=null;
			lngPolicyGroupSeqId=TTKCommon.getLong(frmPreAuthIntimation.getString("policyGrpSeqID"));
			ArrayList alMemberName=new ArrayList();
			alMemberName=onlineAccessObject.getOnlineMember(lngPolicyGroupSeqId);
			request.getSession().setAttribute("alMemberName",alMemberName);
			if(frmClaimsIntimationDetails.get("submittedYN").equals("Y"))
			{
				frmClaimsIntimationDetails.set("showSave","N");
			}//end of if(frmClaimsIntimationDetails.get("submittedYN").equals("Y"))
			else if(frmClaimsIntimationDetails.get("submittedYN").equals("N")||
					frmClaimsIntimationDetails.get("submittedYN").equals(""))
			{
				frmClaimsIntimationDetails.set("showSave","Y");
			}//end of else if(frmClaimsIntimationDetails.get("submittedYN").equals("N")||
							//frmClaimsIntimationDetails.get("submittedYN").equals(""))
			frmClaimsIntimationDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmClaimsIntimationDetails",frmClaimsIntimationDetails);
    		return this.getForward(strForwardPath,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request,mapping,new TTKException(exp,strClaimsIntimation));
    	}//end of catch(Exception exp)
    }//end of doViewIntimationDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										//HttpServletResponse response)
    
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
			
			log.debug("Inside ClaimsIntimationAction doSave");
			setOnlineLinks(request);
			DynaActionForm frmClaimsIntimationDetails =(DynaActionForm)form;
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
															getAttribute("UserSecurityProfile");
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			OnlineIntimationVO onlineIntimationVO = new OnlineIntimationVO();
			DynaActionForm frmPreAuthIntimation=(DynaActionForm)request.getSession().
										getAttribute("frmPreAuthIntimation");
			Long lngPolicyGroupSeqId=null;
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append(" - [").append(frmPreAuthIntimation.get("policyNbr")).append("]").append(" [")
					.append(frmPreAuthIntimation.get("EnrollmentNbr")).append("]").append(" [")
						.append(frmPreAuthIntimation.get("memberName")).append("]");
			lngPolicyGroupSeqId=TTKCommon.getLong(frmPreAuthIntimation.getString("policyGrpSeqID"));
			onlineIntimationVO=(OnlineIntimationVO)FormUtils.getFormValues(frmClaimsIntimationDetails,
																this,mapping,request);
			
			onlineIntimationVO.setPolicyGrpSeqID(lngPolicyGroupSeqId);
			if(userSecurityProfile.getLoginType().equals("H")){
				onlineIntimationVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E")){
//				onlineIntimationVO.setUpdatedBy(TTKCommon.getLong(userSecurityProfile.getUSER_ID()));
				onlineIntimationVO.setUpdatedBy(new Long(1));
			}//end of if(userSecurityProfile.getLoginType().equals("E"))
			long lngCount=onlineAccessObject.saveClaimIntimation(onlineIntimationVO,userSecurityProfile.getGroupID());
			if(lngCount>0)
			{
				if(frmClaimsIntimationDetails.get("intimationNbr").equals(""))
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of if(frmClaimsIntimationDetails.get("intimationNbr").equals(""))
				else
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of else
			}//end of if(lngCount>0)
			String strForwardPath="";
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				onlineIntimationVO= onlineAccessObject.selectIntimation(lngCount,TTKCommon.getActiveSubLink(request));
				strForwardPath=strSaveClaimsIntimation;
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals(strClaimIntimation))
			{
				onlineIntimationVO= onlineAccessObject.selectIntimation(lngCount,"Claims");	
				strForwardPath=strEmpSaveClaimsIntimation;
			}//end of else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals
					//(strClaimIntimation))
			frmClaimsIntimationDetails= (DynaActionForm)FormUtils.setFormValues("frmClaimsIntimationDetails",onlineIntimationVO,
 					this,mapping,request);
			
			ArrayList alMemberName=new ArrayList();
			alMemberName=onlineAccessObject.getOnlineMember(lngPolicyGroupSeqId);
			request.getSession().setAttribute("alMemberName",alMemberName);
			if(frmClaimsIntimationDetails.get("submittedYN").equals("Y"))
			{
				frmClaimsIntimationDetails.set("showSave","N");
			}//end of if(frmClaimsIntimationDetails.get("submittedYN").equals("Y"))
			else if(frmClaimsIntimationDetails.get("submittedYN").equals("N")||
						frmClaimsIntimationDetails.get("submittedYN").equals(""))
			{
				frmClaimsIntimationDetails.set("showSave","Y");
			}//end of else if(frmClaimsIntimationDetails.get("submittedYN").equals("N")|
						//|frmClaimsIntimationDetails.get("submittedYN").equals(""))
			frmClaimsIntimationDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmClaimsIntimationDetails",frmClaimsIntimationDetails);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request,mapping,new TTKException(exp,strClaimsIntimation));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside doReset of ClaimsIntimationAction");
			setOnlineLinks(request);
			DynaActionForm frmClaimsIntimationDetails =(DynaActionForm)form;
			DynaActionForm frmPreAuthIntimation=(DynaActionForm)request.getSession().
										getAttribute("frmPreAuthIntimation");
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
							getAttribute("UserSecurityProfile");
			String strForwardPath="";
			String strEmpNbr ="";
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			Long lngPolicyGroupSeqId=null;
			lngPolicyGroupSeqId=TTKCommon.getLong(frmPreAuthIntimation.getString("policyGrpSeqID"));
			String strPolicyNbr = (String) frmPreAuthIntimation.get("policyNbr");
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				strForwardPath=strEditClaimsIntimation;
				strEmpNbr= "";
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals(strClaimIntimation))
			{
				strForwardPath=strEmpEditClaimsIntimation;
				strEmpNbr = userSecurityProfile.getUSER_ID();
			}//end of else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals
					//(strClaimIntimation))
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			OnlineIntimationVO onlineIntimationVO = new OnlineIntimationVO();
			onlineIntimationVO = onlineAccessObject.getEmpContactInfo(strEmpNbr, lngPolicyGroupSeqId, strPolicyNbr);

			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append(" - [").append(frmPreAuthIntimation.get("policyNbr")).append("]").append(" [")
						.append(frmPreAuthIntimation.get("EnrollmentNbr")).append("]").append(" [")
						.append(frmPreAuthIntimation.get("memberName")).append("]");
			if(frmClaimsIntimationDetails.get("intimationSeqID")!=null && 
					!frmClaimsIntimationDetails.get("intimationSeqID").equals(""))// on clicking the edit link
			{
				if(userSecurityProfile.getLoginType().equals("H"))
				{
					onlineIntimationVO=onlineAccessObject.selectIntimation(TTKCommon.getLong
							(frmClaimsIntimationDetails.getString("intimationSeqID")),TTKCommon.getActiveSubLink(request));
				}//end of if(userSecurityProfile.getLoginType().equals("H"))
				else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals(strClaimIntimation))
				{
					onlineIntimationVO=onlineAccessObject.selectIntimation(TTKCommon.getLong
							(frmClaimsIntimationDetails.getString("intimationSeqID")),"Claims");	
				}//end of else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals
						//(strClaimIntimation))
			}//end of if(!strSelectedRoot.equals(""))
			frmClaimsIntimationDetails= (DynaActionForm)FormUtils.setFormValues("frmClaimsIntimationDetails",
															onlineIntimationVO,this,mapping,request);
			ArrayList alMemberName=new ArrayList();
			alMemberName=onlineAccessObject.getOnlineMember(lngPolicyGroupSeqId);
			request.getSession().setAttribute("alMemberName",alMemberName);
			if(frmClaimsIntimationDetails.get("submittedYN").equals("Y"))
			{
				frmClaimsIntimationDetails.set("showSave","N");
			}//end of if(frmClaimsIntimationDetails.get("submittedYN").equals("Y"))
			else if(frmClaimsIntimationDetails.get("submittedYN").equals("N")||
					frmClaimsIntimationDetails.get("submittedYN").equals(""))
			{
				frmClaimsIntimationDetails.set("showSave","Y");
			}//end of else if(frmClaimsIntimationDetails.get("submittedYN").equals("N")||
								//frmClaimsIntimationDetails.get("submittedYN").equals(""))
			frmClaimsIntimationDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmClaimsIntimationDetails",frmClaimsIntimationDetails);
			return this.getForward(strForwardPath,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strClaimsIntimation));
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
    		log.debug("Inside doClose of ClaimsIntimationAction");
    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
					getAttribute("UserSecurityProfile");
    		String strForwardPath="";
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				strForwardPath=strClaimsIntimationList;
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals(strClaimIntimation))
			{
				strForwardPath=strEmpClaimsIntimationList;
			}//end of else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals
					//(strClaimIntimation))
			//On select IDs kocbroker
			else if(userSecurityProfile.getLoginType().equals("B") && strActiveSubLink.equals(strClaimBroIntimation))
			{
				strForwardPath=strClaimsIntimationList;
			}//end of else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals
					//(strClaimIntimation))
    		OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			{
				ArrayList alIntimationList=onlineAccessObject.getIntimationList(tableData.getSearchData());
				tableData.setData(alIntimationList);
				request.getSession().setAttribute("tableData",tableData);
				//reset the forward links after adding the records if any
				tableData.setForwardNextLink();
			}//end of if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
    		return this.getForward(strForwardPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strClaimsIntimation));
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
			throw new TTKException(exp, strClaimsIntimation);
		}//end of catch
		return onlineAccessManager;
	}//end getOnlineAccessManager()
}//end of ClaimsIntimationAction class
