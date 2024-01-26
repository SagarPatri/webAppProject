/**
 * @ (#) PreAuthIntimationAction.java Mar 11, 2008
 * Project      : TTK HealthCare Services
 * File         : PreAuthIntimationAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Mar 11, 2008
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
import com.ttk.action.tree.TreeData;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.onlineforms.OnlineHospitalVO;
import com.ttk.dto.onlineforms.OnlineIntimationVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class PreAuthIntimationAction extends TTKAction
{
	private static Logger log = Logger.getLogger( PreAuthIntimationAction.class );
	//for forwarding
	private static final String strPreAuthIntimationList="preauthintimationlist";
	private static final String strEmployeePreAuthIntimationList="employeepreauthintimationlist";
	private static final String strEditPreauthIntimation="editpreauthintimation";
	//On select IDs kocbroker
	private static final String strEditPreauthBroIntimation="editpreauthbrointimation";
	private static final String strEmpEditPreauthIntimation="empeditpreauthintimation";
	private static final String strSavePreauthIntimation="savepreauthintimation";
	private static final String strEmpSavePreauthIntimation="empsavepreauthintimation";
	private static final String strClaimsIntimationList="claimsintimationlist";
	private static final String strEmpClaimsIntimationList="empclaimsintimationlist";
	private static final String strHospitallist="hospitallist";
	private static final String strMemberList="memberlist";
	
	private static final String strPreAuth="Pre-Auth";
	private static final String strPreAuthIntimation="Pre-Auth Intimation";
	//On select IDs kocbroker
	private static final String strPreAuthBroIntimation="Pre-Auth";
	private static final String strClaimsIntimation="Claims Intimation";
	private static final String strClaims="Claims";
	
	//for setting modes
	private static final String strForward ="Forward";
	private static final String strBackward ="Backward";
	//Exception Message Identifier
	private static final String strPreauthIntimation="preauthintimation";
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
			log.debug("Inside PreAuthIntimationAction doDefault");
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
										getAttribute("UserSecurityProfile");
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			StringBuffer sbfCaption=new StringBuffer();
			StringBuffer sbfCaption1=new StringBuffer();
			String strForwardPath="";
			if(strActiveSubLink.equals(strPreAuth))
			{
				strForwardPath=strPreAuthIntimationList;
			}//end of if(strActiveSubLink.equals(strPreAuth))
			else if(strActiveSubLink.equals(strClaims))
			{
				strForwardPath=strClaimsIntimationList;
			}//end of else if(strActiveSubLink.equals(strClaims))
			DynaActionForm frmPreAuthIntimation= (DynaActionForm) form;	
			String strPolicyNbr="";
			if(userSecurityProfile.getLoginType().equals("H") || userSecurityProfile.getLoginType().equals("B"))
			{
				TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
				int iSelectedRoot= TTKCommon.getInt(request.getParameter("selectedroot"));
				MemberVO memberVO=(MemberVO)treeData.getRootData().get(iSelectedRoot);
				strPolicyNbr=memberVO.getPolicyNbr();
				sbfCaption.append(" - [").append(strPolicyNbr).append("]").append(" [")
				   .append(memberVO.getEnrollmentNbr()).append("]").append(" [")
				   .append(memberVO.getInsuredName()).append("]");
				sbfCaption1.append(memberVO.getEnrollmentNbr()).append("/").append(memberVO.getInsuredName());
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals(strPreAuthIntimation))
			{
				strPolicyNbr=userSecurityProfile.getPolicyNo();
				sbfCaption.append(" - [").append(strPolicyNbr).append("]").append(" [")
					.append(userSecurityProfile.getTPAEnrolNbr()).append("]").append(" [")
					.append(userSecurityProfile.getUserName()).append("]");
				strForwardPath=strEmployeePreAuthIntimationList;
			}//end of else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals
																	//(strPreAuthIntimation))
			else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals(strClaimsIntimation))
			{
				strPolicyNbr=userSecurityProfile.getPolicyNo();
				sbfCaption.append(" - [").append(strPolicyNbr).append("]").append(" [")
					.append(userSecurityProfile.getTPAEnrolNbr()).append("]").append(" [")
					.append(userSecurityProfile.getUserName()).append("]");
				strForwardPath=strEmpClaimsIntimationList;
			}//end of else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//get the Table Data From the session 
			//StringBuffer sbfCaption= new StringBuffer();
			ArrayList alIntimationList=null;
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			//String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strPageID.equals(""))
			{
				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.
						 getParameter("pageId"))));
				return this.getForward(strForwardPath, mapping, request);
			}// end of if(!strSortID.equals(""))
			else
			{
				tableData.createTableInfo("PreAuthIntimationTable",null);
				tableData.setSearchData(this.populateSearchCriteria(request));
				tableData.modifySearchData("search");
			}// end of else
			alIntimationList=onlineAccessObject.getIntimationList(tableData.getSearchData());
			if(userSecurityProfile.getLoginType().equals("H") || userSecurityProfile.getLoginType().equals("B"))
			{
				TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
				int iSelectedRoot= TTKCommon.getInt(request.getParameter("selectedroot"));
				MemberVO memberVO=(MemberVO)treeData.getRootData().get(iSelectedRoot);
				frmPreAuthIntimation.set("policyGrpSeqID",(String.valueOf(memberVO.getPolicyGroupSeqID())));
				frmPreAuthIntimation.set("memberName",memberVO.getInsuredName());
				frmPreAuthIntimation.set("policyNbr",strPolicyNbr);
				frmPreAuthIntimation.set("EnrollmentNbr",(memberVO.getEnrollmentNbr()));
				frmPreAuthIntimation.set("policySeqID",(String.valueOf(memberVO.getPolicySeqID())));
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E")||strActiveSubLink.equals(strPreAuthIntimation)||
					strActiveSubLink.equals(strClaimsIntimation))
			{
				frmPreAuthIntimation.set("policyGrpSeqID",(String.valueOf(userSecurityProfile.getPolicyGrpSeqID())));
				frmPreAuthIntimation.set("memberName",userSecurityProfile.getUserName());
				frmPreAuthIntimation.set("policyNbr",strPolicyNbr);
				frmPreAuthIntimation.set("EnrollmentNbr",(userSecurityProfile.getTPAEnrolNbr()));
				frmPreAuthIntimation.set("policySeqID",(String.valueOf(userSecurityProfile.getPolicySeqID())));
			}//end of else if(userSecurityProfile.getLoginType().equals("E")||strActiveSubLink.equals(strPreAuthIntimation)||
							//strActiveSubLink.equals(strClaimsIntimation))
			frmPreAuthIntimation.set("caption",sbfCaption.toString());
			tableData.setData(alIntimationList);
			request.getSession().setAttribute("tableData",tableData);
			request.getSession().setAttribute("frmPreAuthIntimation",frmPreAuthIntimation);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request,mapping,new TTKException(exp,strPreauthIntimation));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside the doBackward method of PreAuthIntimationAction");
			setOnlineLinks(request);
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
									getAttribute("UserSecurityProfile");
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardPath="";
			if(strActiveSubLink.equals(strPreAuth))
			{
				strForwardPath=strPreAuthIntimationList;
			}//end of if(strActiveSubLink.equals(strPreAuth))
			else if(strActiveSubLink.equals(strClaims))
			{
				strForwardPath=strClaimsIntimationList;
			}//end of else if(strActiveSubLink.equals(strClaims))
			else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals(strPreAuthIntimation))
			{
				strForwardPath=strEmployeePreAuthIntimationList;
			}//end of else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals
										//(strPreAuthIntimation))
			else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals(strClaimsIntimation))
			{
				strForwardPath=strEmpClaimsIntimationList;
			}// end of else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals
																				//(strClaimsIntimation))
//			get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			ArrayList alIntimationList=null;
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			tableData.modifySearchData(strBackward);//modify the search data
			alIntimationList = onlineAccessObject.getIntimationList(tableData.getSearchData());
			tableData.setData(alIntimationList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strPreauthIntimation));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	 * @throws Exception if any error occurs
	 */
	public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													 HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doForward method of PreAuthIntimationAction");
			setOnlineLinks(request);
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
						getAttribute("UserSecurityProfile");
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardPath="";
			if(strActiveSubLink.equals(strPreAuth))
			{
				strForwardPath=strPreAuthIntimationList;
			}//end of if(strActiveSubLink.equals(strPreAuth))
			else if(strActiveSubLink.equals(strClaims))
			{
				strForwardPath=strClaimsIntimationList;
			}//end of else if(strActiveSubLink.equals(strClaims))
			else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals(strPreAuthIntimation))
			{
				strForwardPath=strEmployeePreAuthIntimationList;
			}//end of else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals
										//(strPreAuthIntimation))
			else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals(strClaimsIntimation))
			{
				strForwardPath=strEmpClaimsIntimationList;
			}// end of else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals
																				//(strClaimsIntimation))
			TableData tableData =TTKCommon.getTableData(request);
			ArrayList alIntimationList=null;
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			//modify the search data
			tableData.modifySearchData(strForward);
			alIntimationList = onlineAccessObject.getIntimationList(tableData.getSearchData());
			//set the table data
			tableData.setData(alIntimationList, strForward);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strPreauthIntimation));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
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
			log.debug("Inside the doAdd method of PreAuthIntimationAction");
			setOnlineLinks(request);
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
											getAttribute("UserSecurityProfile");
			DynaActionForm frmPreAuthIntimationDetails = (DynaActionForm)form;
			DynaActionForm frmPreAuthIntimation=(DynaActionForm)request.getSession()
											.getAttribute("frmPreAuthIntimation");
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			StringBuffer sbfCaption=new StringBuffer();
			String strForwardPath="";
			String strEmpNbr ="";
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				strForwardPath=strEditPreauthIntimation;
				strEmpNbr= "";
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals(strPreAuthIntimation))
			{
				strForwardPath=strEmpEditPreauthIntimation;
				strEmpNbr = userSecurityProfile.getUSER_ID();
			}//end of else if(userSecurityProfile.getLoginType().equals("E"))
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
			OnlineHospitalVO onlineHospitalVO = new OnlineHospitalVO();
			onlineIntimationVO.setOnlineHospitalVO(onlineHospitalVO);
			frmPreAuthIntimationDetails= (DynaActionForm)FormUtils.setFormValues("frmPreAuthIntimationDetails",
																onlineIntimationVO,this,mapping,request);
			frmPreAuthIntimationDetails.set("onlineHospitalVO", FormUtils.setFormValues("frmOnlineHospital",
					onlineIntimationVO.getOnlineHospitalVO(),this,mapping,request));
			request.getSession().setAttribute("alMemberName",alMemberName);
			frmPreAuthIntimationDetails.set("showSave","Y");
			frmPreAuthIntimationDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmPreAuthIntimationDetails",frmPreAuthIntimationDetails);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strPreauthIntimation));
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
    		log.debug("Inside PreAuthIntimationAction doViewIntimationDetails");
    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
				getAttribute("UserSecurityProfile");
    		DynaActionForm frmPreAuthIntimationDetails= (DynaActionForm) form;
    		DynaActionForm frmPreAuthIntimation=(DynaActionForm)request.getSession()
    										.getAttribute("frmPreAuthIntimation");
    		OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
 			OnlineIntimationVO onlineIntimationVO = new OnlineIntimationVO();
			OnlineHospitalVO onlineHospitalVO = new OnlineHospitalVO();
			onlineIntimationVO.setOnlineHospitalVO(onlineHospitalVO);
			TableData tableData = TTKCommon.getTableData(request);
			Long lngIntimationSeqID = null;
			String strForwardPath="";
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				strForwardPath=strEditPreauthIntimation;
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals(strPreAuthIntimation))
			{
				strForwardPath=strEmpEditPreauthIntimation;
			}//end of else if(userSecurityProfile.getLoginType().equals("E"))
			
			//On select IDs kocbroker
			else if(userSecurityProfile.getLoginType().equals("B")&& strActiveSubLink.equals(strPreAuthBroIntimation))
			{
				strForwardPath=strEditPreauthBroIntimation;
			}//end of else if(userSecurityProfile.getLoginType().equals("E"))
			StringBuffer sbfCaption=new StringBuffer();
			StringBuffer sbfCaption1=new StringBuffer();
			sbfCaption.append(" - [").append(frmPreAuthIntimation.get("policyNbr")).append("]").append(" [")
								.append(frmPreAuthIntimation.get("EnrollmentNbr")).append("]").append(" [")
									.append(frmPreAuthIntimation.get("memberName")).append("]");
			sbfCaption1.append(frmPreAuthIntimation.get("EnrollmentNbr")).append("/").append(frmPreAuthIntimation.get("memberName"));
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				onlineIntimationVO=(OnlineIntimationVO)tableData.getRowInfo(Integer.parseInt(
															request.getParameter("rownum")));
				lngIntimationSeqID= onlineIntimationVO.getIntimationSeqID();
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				onlineIntimationVO= onlineAccessObject.selectIntimation(lngIntimationSeqID,TTKCommon.getActiveSubLink(request));
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals(strPreAuthIntimation))
			{
				onlineIntimationVO= onlineAccessObject.selectIntimation(lngIntimationSeqID,"Pre-Auth");
			}//end of else if(userSecurityProfile.getLoginType().equals("E"))
			
			//On select IDs kocbroker
			else if(userSecurityProfile.getLoginType().equals("B") && strActiveSubLink.equals(strPreAuthBroIntimation))
			{
				onlineIntimationVO= onlineAccessObject.selectIntimation(lngIntimationSeqID,"Pre-Auth");
			}//end of else if(userSecurityProfile.getLoginType().equals("E"))
			
			frmPreAuthIntimationDetails= (DynaActionForm)FormUtils.setFormValues("frmPreAuthIntimationDetails",
																	onlineIntimationVO,this,mapping,request);
			frmPreAuthIntimationDetails.set("onlineHospitalVO", FormUtils.setFormValues("frmOnlineHospital",
					onlineIntimationVO.getOnlineHospitalVO(),this,mapping,request));
			
			Long lngPolicyGroupSeqId=null;
			lngPolicyGroupSeqId=TTKCommon.getLong(frmPreAuthIntimation.getString("policyGrpSeqID"));
			ArrayList alMemberName=new ArrayList();
			alMemberName=onlineAccessObject.getOnlineMember(lngPolicyGroupSeqId);
			request.getSession().setAttribute("alMemberName",alMemberName);
			if(frmPreAuthIntimationDetails.get("submittedYN").equals("Y"))
			{
				frmPreAuthIntimationDetails.set("showSave","N");
			}//end of if(frmPreAuthIntimationDetails.get("submittedYN").equals("Y"))
			else if(frmPreAuthIntimationDetails.get("submittedYN").equals("N")||
					frmPreAuthIntimationDetails.get("submittedYN").equals(""))
			{
				frmPreAuthIntimationDetails.set("showSave","Y");
			}//end of else if(frmPreAuthIntimationDetails.get("submittedYN").equals("N")||
							//frmPreAuthIntimationDetails.get("submittedYN").equals(""))
			frmPreAuthIntimationDetails.set("caption",sbfCaption.toString());
			frmPreAuthIntimationDetails.set("caption1",sbfCaption1.toString());
			request.getSession().setAttribute("frmPreAuthIntimationDetails",frmPreAuthIntimationDetails);
    		return this.getForward(strForwardPath,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request,mapping,new TTKException(exp,strPreauthIntimation));
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
			
			log.debug("Inside PreAuthIntimationAction doSave");
			setOnlineLinks(request);
			DynaActionForm frmPreAuthIntimationDetails =(DynaActionForm)form;
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
															getAttribute("UserSecurityProfile");
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			OnlineIntimationVO onlineIntimationVO = new OnlineIntimationVO();
			OnlineHospitalVO onlineHospitalVO = new OnlineHospitalVO();
			DynaActionForm frmPreAuthIntimation=(DynaActionForm)request.getSession().
										getAttribute("frmPreAuthIntimation");
			Long lngPolicyGroupSeqId=null;
			String strForwardPath="";
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				strForwardPath=strSavePreauthIntimation;
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals(strPreAuthIntimation))
			{
				strForwardPath=strEmpSavePreauthIntimation;
			}//end of else if(userSecurityProfile.getLoginType().equals("E"))
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append(" - [").append(frmPreAuthIntimation.get("policyNbr")).append("]").append(" [")
					.append(frmPreAuthIntimation.get("EnrollmentNbr")).append("]").append(" [")
						.append(frmPreAuthIntimation.get("memberName")).append("]");
			lngPolicyGroupSeqId=TTKCommon.getLong(frmPreAuthIntimation.getString("policyGrpSeqID"));
			onlineIntimationVO=(OnlineIntimationVO)FormUtils.getFormValues(frmPreAuthIntimationDetails,
																this,mapping,request);
			ActionForm frmOnlineHospital=(ActionForm)frmPreAuthIntimationDetails.get("onlineHospitalVO");
			onlineHospitalVO=(OnlineHospitalVO)FormUtils.getFormValues(frmOnlineHospital,"frmOnlineHospital",
					this,mapping,request);
			onlineIntimationVO.setOnlineHospitalVO(onlineHospitalVO);
			onlineIntimationVO.setPolicyGrpSeqID(lngPolicyGroupSeqId);
			if(userSecurityProfile.getLoginType().equals("H")){
				onlineIntimationVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			}//end of  if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E")){
				//onlineIntimationVO.setUpdatedBy(TTKCommon.getLong(userSecurityProfile.getUSER_ID()));
				onlineIntimationVO.setUpdatedBy(new Long(1));
			}//end of if(userSecurityProfile.getLoginType().equals("E"))
				
			long lngCount=onlineAccessObject.savePreauthIntimation(onlineIntimationVO,userSecurityProfile.getGroupID());
			if(lngCount>0)
			{
				if(frmPreAuthIntimationDetails.get("intimationNbr").equals(""))
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of if(frmPreAuthIntimationDetails.get("intimationNbr").equals(""))
				else
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of else
			}//end of if(lngCount>0)
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				onlineIntimationVO= onlineAccessObject.selectIntimation(lngCount,TTKCommon.getActiveSubLink(request));
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E") && strActiveSubLink.equals(strPreAuthIntimation))
			{
				onlineIntimationVO= onlineAccessObject.selectIntimation(lngCount,"Pre-Auth");
			}//end of else if(userSecurityProfile.getLoginType().equals("E"))
			frmPreAuthIntimationDetails= (DynaActionForm)FormUtils.setFormValues("frmPreAuthIntimationDetails",onlineIntimationVO,
 					this,mapping,request);
			frmPreAuthIntimationDetails.set("onlineHospitalVO", FormUtils.setFormValues("frmOnlineHospital",
					onlineIntimationVO.getOnlineHospitalVO(),this,mapping,request));
			ArrayList alMemberName=new ArrayList();
			alMemberName=onlineAccessObject.getOnlineMember(lngPolicyGroupSeqId);
			request.getSession().setAttribute("alMemberName",alMemberName);
			if(frmPreAuthIntimationDetails.get("submittedYN").equals("Y"))
			{
				frmPreAuthIntimationDetails.set("showSave","N");
			}//end of if(frmPreAuthIntimationDetails.get("submittedYN").equals("Y"))
			else if(frmPreAuthIntimationDetails.get("submittedYN").equals("N")||
						frmPreAuthIntimationDetails.get("submittedYN").equals(""))
			{
				frmPreAuthIntimationDetails.set("showSave","Y");
			}//end of else if(frmPreAuthIntimationDetails.get("submittedYN").equals("N")|
						//|frmPreAuthIntimationDetails.get("submittedYN").equals(""))
			frmPreAuthIntimationDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmPreAuthIntimationDetails",frmPreAuthIntimationDetails);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request,mapping,new TTKException(exp,strPreauthIntimation));
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
			log.debug("Inside doReset of PreAuthIntimationAction");
			setOnlineLinks(request);
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
					getAttribute("UserSecurityProfile");
			DynaActionForm frmPreAuthIntimationDetails =(DynaActionForm)form;
			DynaActionForm frmPreAuthIntimation=(DynaActionForm)request.getSession().
										getAttribute("frmPreAuthIntimation");
			OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			Long lngPolicyGroupSeqId=null;
			lngPolicyGroupSeqId=TTKCommon.getLong(frmPreAuthIntimation.getString("policyGrpSeqID"));
			String strPolicyNbr = (String) frmPreAuthIntimation.get("policyNbr");
			OnlineIntimationVO onlineIntimationVO = new OnlineIntimationVO();
			
			String strForwardPath="";
			String strEmpNbr ="";
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				strForwardPath=strEditPreauthIntimation;
				strEmpNbr= "";
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals(strPreAuthIntimation))
			{
				strForwardPath=strEmpEditPreauthIntimation;
				strEmpNbr = userSecurityProfile.getUSER_ID();
			}//end of else if(userSecurityProfile.getLoginType().equals("E"))
			onlineIntimationVO = onlineAccessObject.getEmpContactInfo(strEmpNbr, lngPolicyGroupSeqId, strPolicyNbr);
			OnlineHospitalVO onlineHospitalVO = new OnlineHospitalVO();
			onlineIntimationVO.setOnlineHospitalVO(onlineHospitalVO);
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append(" - [").append(frmPreAuthIntimation.get("policyNbr")).append("]").append(" [")
						.append(frmPreAuthIntimation.get("EnrollmentNbr")).append("]").append(" [")
						.append(frmPreAuthIntimation.get("memberName")).append("]");
			if(frmPreAuthIntimationDetails.get("intimationSeqID")!=null && 
					!frmPreAuthIntimationDetails.get("intimationSeqID").equals(""))// on clicking the edit link
			{
				if(userSecurityProfile.getLoginType().equals("H"))
				{
					onlineIntimationVO=onlineAccessObject.selectIntimation(TTKCommon.getLong
							(frmPreAuthIntimationDetails.getString("intimationSeqID")),TTKCommon.getActiveSubLink(request));
				}//end of if(userSecurityProfile.getLoginType().equals("H"))
				else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals(strPreAuthIntimation))
				{
					onlineIntimationVO=onlineAccessObject.selectIntimation(TTKCommon.getLong
							(frmPreAuthIntimationDetails.getString("intimationSeqID")),"Pre-Auth");
				}//end of else if(userSecurityProfile.getLoginType().equals("E"))
				
			}//end of if(!strSelectedRoot.equals(""))
			frmPreAuthIntimationDetails= (DynaActionForm)FormUtils.setFormValues("frmPreAuthIntimationDetails",
															onlineIntimationVO,this,mapping,request);
			frmPreAuthIntimationDetails.set("onlineHospitalVO", FormUtils.setFormValues("frmOnlineHospital",
					onlineIntimationVO.getOnlineHospitalVO(),this,mapping,request));
			request.getSession().setAttribute("frmPreAuthIntimationDetails",frmPreAuthIntimationDetails);
			ArrayList alMemberName=new ArrayList();
			alMemberName=onlineAccessObject.getOnlineMember(lngPolicyGroupSeqId);
			request.getSession().setAttribute("alMemberName",alMemberName);
			if(frmPreAuthIntimationDetails.get("submittedYN").equals("Y"))
			{
				frmPreAuthIntimationDetails.set("showSave","N");
			}//end of if(frmPreAuthIntimationDetails.get("submittedYN").equals("Y"))
			else if(frmPreAuthIntimationDetails.get("submittedYN").equals("N")||
					frmPreAuthIntimationDetails.get("submittedYN").equals(""))
			{
				frmPreAuthIntimationDetails.set("showSave","Y");
			}//end of else if(frmPreAuthIntimationDetails.get("submittedYN").equals("N")||
								//frmPreAuthIntimationDetails.get("submittedYN").equals(""))
			frmPreAuthIntimationDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmPreAuthIntimationDetails",frmPreAuthIntimationDetails);
			return this.getForward(strForwardPath,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strPreauthIntimation));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to Select Hospital from the grid screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside PreAuthIntimationAction doSelectHospital ");
			return mapping.findForward(strHospitallist);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strPreauthIntimation));
		}//end of catch(Exception exp)
	}//end of doSelectHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
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
    		log.debug("Inside doClose of PreAuthIntimationAction");
    		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
												getAttribute("UserSecurityProfile");	
    		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardPath="";
			if(strActiveSubLink.equals(strPreAuth))
			{
				strForwardPath=strPreAuthIntimationList;
			}//end of if(strActiveSubLink.equals(strPreAuth))
			else if(strActiveSubLink.equals(strClaims))
			{
				strForwardPath=strClaimsIntimationList;
			}//end of else if(strActiveSubLink.equals(strClaims))
			else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals(strPreAuthIntimation))
			{
				strForwardPath=strEmployeePreAuthIntimationList;
			}//end of else if(userSecurityProfile.getLoginType().equals("E"))
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
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strPreauthIntimation));
		}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doCloseList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    								HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.debug("Inside doCloseList of PreAuthIntimationAction");
    		return this.getForward(strMemberList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strPreauthIntimation));
		}//end of catch(Exception exp)
    }//end of doCloseList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse 
    												//response)
    
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
	public ActionForward doDeleteHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside doCancelHospital of PreAuthIntimationAction");
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
										getAttribute("UserSecurityProfile");	
    		OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
    		DynaActionForm frmPreAuthIntimationDetails=(DynaActionForm)request.getSession().
			getAttribute("frmPreAuthIntimationDetails");
    		DynaActionForm frmOnlineHospital = (DynaActionForm)frmPreAuthIntimationDetails.get("onlineHospitalVO");
			Long lngHospitalIntSeqId=null;
			String strForwardPath="";
			if(userSecurityProfile.getLoginType().equals("H"))
			{
				strForwardPath=strEditPreauthIntimation;
			}//end of if(userSecurityProfile.getLoginType().equals("H"))
			else if(userSecurityProfile.getLoginType().equals("E"))
			{
				strForwardPath=strEmpEditPreauthIntimation;
			}//end of else if(userSecurityProfile.getLoginType().equals("E"))
			ArrayList <Object>alCancelHospital=new ArrayList<Object>();
			String strHospitalValue = frmPreAuthIntimationDetails.getString("hospitalvalue");
			if(strHospitalValue.equals("first"))
			{	
				if((frmOnlineHospital.get("hospSeqId1")!= null)||
						(! frmOnlineHospital.get("hospSeqId1").equals("")))
				{
					lngHospitalIntSeqId=TTKCommon.getLong(frmOnlineHospital.getString("hospIntSeqID1"));
				}//end of if((frmOnlineHospital.get("hospSeqId1")!= null)||
							//(! frmOnlineHospital.get("hospSeqId1").equals("")))
			}//end of if(strHospitalValue.equals("first"))	
			else if(strHospitalValue.equals("second"))
			{	
				if((frmOnlineHospital.get("hospSeqId2")!= null)||
						(! frmOnlineHospital.get("hospSeqId2").equals("")))
				{
					lngHospitalIntSeqId=TTKCommon.getLong(frmOnlineHospital.getString("hospIntSeqID2"));
				}//end of if((frmOnlineHospital.get("hospSeqId2")!= null)||
						//(! frmOnlineHospital.get("hospSeqId2").equals("")))
			}//end of else if(strHospitalValue.equals("second"))
			else if(strHospitalValue.equals("third"))
			{	
				if((frmOnlineHospital.get("hospSeqId3")!= null)||
						(! frmOnlineHospital.get("hospSeqId3").equals("")))
				{
					lngHospitalIntSeqId=TTKCommon.getLong(frmOnlineHospital.getString("hospIntSeqID3"));
				}//end of if((frmOnlineHospital.get("hospSeqId3")!= null)||(! frmOnlineHospital.get("hospSeqId3")
																//.equals("")))
			}//end of else if(strHospitalValue.equals("third"))	
			alCancelHospital.add("HOS");
			alCancelHospital.add(null);
			alCancelHospital.add(lngHospitalIntSeqId);
			int iCount=onlineAccessObject.deleteIntimation(alCancelHospital);
			log.debug("iCount value is :"+iCount);
			if(strHospitalValue.equals("first")){
				frmOnlineHospital.set("hospIntSeqID1","");
				frmOnlineHospital.set("hospitalName1","");
				frmOnlineHospital.set("hospSeqId1","");
				frmOnlineHospital.set("address1","");
				frmOnlineHospital.set("stateName1","");
				frmOnlineHospital.set("doctorName1","");
				frmOnlineHospital.set("doctorPhoneNbr1","");
				frmOnlineHospital.set("estimatedCost1",null);
				frmOnlineHospital.set("roomType1","");
			}//end of if(strHospitalIndex.equals("first"))
			else if(strHospitalValue.equals("second")){
				frmOnlineHospital.set("hospitalName2","");
				frmOnlineHospital.set("hospIntSeqID2","");
				frmOnlineHospital.set("hospSeqId2","");
				frmOnlineHospital.set("address2","");
				frmOnlineHospital.set("stateName2","");
				frmOnlineHospital.set("doctorName2","");
				frmOnlineHospital.set("doctorPhoneNbr2","");
				frmOnlineHospital.set("estimatedCost2",null);
				frmOnlineHospital.set("roomType2","");
			}//end of if(strHospitalIndex.equals("second"))
			else if(strHospitalValue.equals("third")){
				frmOnlineHospital.set("hospitalName3","");
				frmOnlineHospital.set("hospIntSeqID3","");
				frmOnlineHospital.set("hospSeqId3","");
				frmOnlineHospital.set("address3","");
				frmOnlineHospital.set("stateName3","");
				frmOnlineHospital.set("doctorName3","");
				frmOnlineHospital.set("doctorPhoneNbr3","");
				frmOnlineHospital.set("estimatedCost3",null);
				frmOnlineHospital.set("roomType3","");
			}//end of if(strHospitalIndex.equals("third"))
			frmPreAuthIntimationDetails.set("onlineHospitalVO",frmOnlineHospital);
			/*if(((frmOnlineHospital.get("hospIntSeqID1")== null)|| (frmOnlineHospital.get("hospIntSeqID1").equals(""))) && 
					((frmOnlineHospital.get("hospIntSeqID2")== null)|| (frmOnlineHospital.get("hospIntSeqID2").equals("")))&&
					((frmOnlineHospital.get("hospIntSeqID3")== null)|| (frmOnlineHospital.get("hospIntSeqID3").equals(""))))
			{
				request.setAttribute("selectHospital","error.onlineforms.selecthospital");
			}//end of if*/
			/*if((frmOnlineHospital.get("hospIntSeqID1").equals("")) && 
					(frmOnlineHospital.get("hospIntSeqID2").equals(""))&&
					(frmOnlineHospital.get("hospIntSeqID3").equals("")))
			{
				request.setAttribute("selectHospital","error.onlineforms.selecthospital");
			}//end of if*/
			frmPreAuthIntimationDetails.set("frmChanged","changed");
			request.getSession().setAttribute("frmPreAuthIntimationDetails",frmPreAuthIntimationDetails);
			return this.getForward(strForwardPath,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request,mapping,new TTKException(exp,strPreauthIntimation));
		}//end of catch(Exception exp)
	}//end of doDeleteHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside doClose of PreAuthIntimationAction");
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
										getAttribute("UserSecurityProfile");
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardPath="";
    		OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			StringBuffer sbfDeleteId = new StringBuffer();
			ArrayList <Object>alLinkDelete=new ArrayList<Object>();
			ArrayList alLinkDetailsList=null;
			//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().getAttribute("tableData")));
			if(strActiveSubLink.equals(strPreAuth)||strActiveSubLink.equals(strPreAuthIntimation))
			{
				alLinkDelete.add("PAT");
				strForwardPath=strPreAuthIntimationList;
			}//end of if(strActiveSubLink.equals(strPreAuth))
			else if(strActiveSubLink.equals(strClaims)||strActiveSubLink.equals(strClaimsIntimation))
			{
				alLinkDelete.add("CLM");
				strForwardPath=strClaimsIntimationList;
			}//end of else if(strActiveSubLink.equals(strClaims))
			else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals(strPreAuthIntimation))
			{
				strForwardPath=strEmployeePreAuthIntimationList;
			}//end of else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals
									//(strPreAuthIntimation))
			else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals(strClaimsIntimation))
			{
				strForwardPath=strEmpClaimsIntimationList;
			}//end of else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals
								//(strClaimsIntimation))
			alLinkDelete.add(String.valueOf(sbfDeleteId));
			alLinkDelete.add(TTKCommon.getUserSeqId(request));
			int iCount=onlineAccessObject.deleteIntimation(alLinkDelete);
			log.debug("iCount value is :"+iCount);
			//refresh the data in order to get the new records if any.
			alLinkDetailsList=onlineAccessObject.getIntimationList(tableData.getSearchData());
			tableData.setData(alLinkDetailsList);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strForwardPath,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request,mapping,new TTKException(exp,strPreauthIntimation));
		}//end of catch(Exception exp)
	}//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns a string which contains the | separated sequence id's to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the | separated sequence id's to be delete
	 * @throws TTKException If any run time Excepton occures
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableData)throws TTKException
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append("|").append(String.valueOf(((OnlineIntimationVO)tableData.getData().
								get(Integer.parseInt(strChk[i]))).getIntimationSeqID()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((OnlineIntimationVO)tableData.getData().
								get(Integer.parseInt(strChk[i]))).getIntimationSeqID().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId=sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return String.valueOf(sbfDeleteId);
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)
	
	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param request The HTTP request we are processing
	 * @return alSearchParams ArrayList
	 * @throws TTKException 
	 */
	private ArrayList<Object> populateSearchCriteria(HttpServletRequest request) throws TTKException
	{
		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
				getAttribute("UserSecurityProfile");
		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		
		if(userSecurityProfile.getLoginType().equals("H") || userSecurityProfile.getLoginType().equals("B"))
		{
			alSearchParams.add(TTKCommon.getActiveSubLink(request));
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			int iSelectedRoot= TTKCommon.getInt(request.getParameter("selectedroot"));
			MemberVO memberVO=(MemberVO)treeData.getRootData().get(iSelectedRoot);
			alSearchParams.add(memberVO.getPolicyGroupSeqID());
		}//end of if(userSecurityProfile.getLoginType().equals("H"))
		else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals(strPreAuthIntimation))
		{
			alSearchParams.add("Pre-Auth");
			alSearchParams.add(userSecurityProfile.getPolicyGrpSeqID());
		}//end of else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals
						//(strPreAuthIntimation))
		else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals(strClaimsIntimation))
		{
			alSearchParams.add("Claims");
			alSearchParams.add(userSecurityProfile.getPolicyGrpSeqID());
		}//end of else if(userSecurityProfile.getLoginType().equals("E")&& strActiveSubLink.equals
					//(strClaimsIntimation))
		return alSearchParams;
	}//end of populateSearchCriteria(HttpServletRequest request)
	
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
			throw new TTKException(exp, strPreauthIntimation);
		}//end of catch
		return onlineAccessManager;
	}//end getOnlineAccessManager()
}//end of PreAuthIntimationAction class
