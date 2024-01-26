/**
 * @ (#) RenewMemberAction.java Feb 3, 2006
 * Project 		: TTK HealthCare Services
 * File 		: RenewMemberAction.java
 * Author 		: Pradeep R
 * Company 		: Span Systems Corporation
 * Date Created : Feb 3, 2006
 *
 * @author 		: Pradeep R
 * Modified by 	:
 * Modified date:
 * Reason 		:
 */

package com.ttk.action.enrollment;

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
import com.ttk.business.enrollment.MemberManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberVO;

/**
 * This class is reusable for find the list of member can renewed in individual policy,individual policy as group,
 * corporate policies and non corporate policies in enrollment and endorsement flow.
 * This class also provides option for renewing the member in all the flow.
 */
public class RenewMemberAction extends TTKAction
{
	private static Logger log = Logger.getLogger( RenewMemberAction.class );
	//string for setting mode
	private static final String strRenewListMode="doSearch";
	private static final String strRenew="doRenew";
	private static final String strReset="doReset";
	//sub links
	private static final String strIndividualPolicy="Individual Policy";
	private static final String strIndPolicyGroup="Ind. Policy as Group";
	private static final String strCorporatePolicy="Corporate Policy";
	private static final String strNonCorpPolicy="Non-Corporate Policy";
	//for forwarding
	private static final String strRenewList="renewlist";
	private static final String strIndividualRenewList="individualrenewlist";
	private static final String strIndGroupRenewList="indgrouprenewlist";
	private static final String strCorporateRenewList="corporaterenewlist";
	private static final String strNonCorpRenewList="noncorprenewlist";
	private static final String strRenewClose="renewclose";

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
			log.debug("Inside RenewMemberAction doSearch");
			//Getting the switch value(Enrollment or Endosement)
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType= frmPolicyList.getString("switchType");
			this.setLinks(request,strSwitchType);
			String strType=getType(request);
			String strForward=getForwardPath(request);
			MemberManager memberManagerObject=this.getMemberManagerObject();
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			MemberVO memberVO=new MemberVO();
			TableData  renewListTableData =null;
			if((request.getSession()).getAttribute("renewListTableData") == null)
			{
				renewListTableData = new TableData();
			}//end of if((request.getSession()).getAttribute("renewListTableData") == null)
			else
			{
				renewListTableData = (TableData)(request.getSession()).getAttribute("renewListTableData");
			}//end of else
			DynaActionForm formRenewMember=(DynaActionForm)form;
			String strSelectedRoot=request.getParameter("selectedroot");
			String strMode=TTKCommon.checkNull(request.getParameter("doReset"));
			if(strMode.equals(strReset))
			{
				strSelectedRoot=formRenewMember.getString("selectedRoot");
			}//end of if(strMode.equals(strReset))
			int iSelectedRoot= TTKCommon.getInt(strSelectedRoot);
			memberVO=(MemberVO)treeData.getRootData().get(iSelectedRoot);
			//setting the required information in the form
			DynaActionForm frmRenewMember=(DynaActionForm)form;
			frmRenewMember.set("caption","Renew Members - [ "+memberVO.getName()+" ]");
			//preparing the parameter to get the member list
			ArrayList<Object> alParameters=new ArrayList<Object>();
			alParameters.add(memberVO.getPolicyGroupSeqID());
			alParameters.add(strSwitchType);
			alParameters.add(strType);
			//create new table data object
			renewListTableData = new TableData();
			//create the required grid table
			renewListTableData.createTableInfo("RenewListTable",null);
			ArrayList alMember=memberManagerObject.getRenewMemberList(alParameters);
			renewListTableData.setData(alMember);
			formRenewMember.set("selectedRoot",strSelectedRoot);
			formRenewMember.set("switchType",strSwitchType);
			request.getSession().setAttribute("frmRenewMember",formRenewMember);
			request.getSession().setAttribute("renewListTableData",renewListTableData);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strRenewList));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to renew the record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doRenew(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
			{
		try{
			log.debug("Inside RenewMemberAction doRenew");
			DynaActionForm formRenewMember=(DynaActionForm)form;
			//Getting the switch value(Enrollment or Endosement)
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType= frmPolicyList.getString("switchType");
			this.setLinks(request,strSwitchType);
			String strType=getType(request);
			String strForward=getForwardPath(request);
			MemberManager memberManagerObject=this.getMemberManagerObject();
			String strSelectedRoot=formRenewMember.getString("selectedRoot");
			TableData  renewListTableData =null;
			String strMemSeqId = "";
			int iSelectedRoot= TTKCommon.getInt(strSelectedRoot);
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			MemberVO memberVO=(MemberVO)treeData.getRootData().get(iSelectedRoot);
			strMemSeqId=populateMemberSeqId(request,(TableData)request.getSession().getAttribute("renewListTableData"));
			ArrayList<Object> alParameters=new ArrayList<Object>();
			alParameters.add(strMemSeqId);
			alParameters.add(TTKCommon.getUserSeqId(request));
			alParameters.add(memberVO.getPolicyGroupSeqID());
			alParameters.add(memberVO.getPolicySeqID());
			if(strSwitchType.equals("END"))
			{
				alParameters.add(WebBoardHelper.getEndorsementSeqId(request));
			}//end of if(strSwitchType.equals("END"))
			else
			{
				alParameters.add(memberVO.getEndorsementSeqID());
			}//end of else
			alParameters.add(strSwitchType);
			alParameters.add(strType);
			int iCount=memberManagerObject.saveRenewals(alParameters);
			if(iCount>0)
			{
				request.setAttribute("updated","message.renewedSuccessfully");
			}//end of if(iCount>0)

			//preparing the parameter to get the member list
			alParameters=new ArrayList<Object>();
			alParameters.add(memberVO.getPolicyGroupSeqID());
			alParameters.add(strSwitchType);
			alParameters.add(strType);
			//create new table data object
			renewListTableData = new TableData();
			//create the required grid table
			renewListTableData.createTableInfo("RenewListTable",null);
			ArrayList alMember=memberManagerObject.getRenewMemberList(alParameters);
			renewListTableData.setData(alMember);
			request.getSession().setAttribute("renewListTableData",renewListTableData);
			formRenewMember.set("switchType",strSwitchType);
			request.getSession().setAttribute("frmRenewMember",formRenewMember);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strRenewList));
		}//end of catch(Exception exp)
	}//end of doRenew(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside RenewMemberAction doClose");
			//Getting the switch value(Enrollment or Endosement)
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType= frmPolicyList.getString("switchType");
			this.setLinks(request,strSwitchType);
			/*String strType=getType(request);
			String strForward=getForwardPath(request);*/
			request.getSession().removeAttribute("renewListTableData");
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
			treeData.setSelectedRoot(-1);

			return (mapping.findForward(strRenewClose));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strRenewList));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside RenewMemberAction doReset");
			return doRenew(mapping, form, request, response);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strRenewList));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method returns the enrollment forward path for next view based on the Flow
	 *
	 * @param request The HTTP request we are processing.
	 * @return strForwardPath String forward path for the next view
	 */
	private String getForwardPath(HttpServletRequest request)throws TTKException
	{
		String strForwardPath="";
		try
		{
			String strSubLinks=TTKCommon.getActiveSubLink(request);
			String strMode=TTKCommon.checkNull(request.getParameter("mode"));
			if(strMode.equals(strRenewListMode)||strMode.equals(strRenew)|| strMode.equals(strReset))
			{
				if(strSubLinks.equals(strIndividualPolicy))
				{
					return strIndividualRenewList;
				}//end of if(strSubLinks.equals(strIndividualPolicy))
				else if(strSubLinks.equals(strIndPolicyGroup))
				{
					return strIndGroupRenewList;
				}//end of else if(strSubLinks.equals(strIndPolicyGroup))
				else if(strSubLinks.equals(strCorporatePolicy))
				{
					return strCorporateRenewList;
				}//end of else if(strSubLinks.equals(strCorporatePolicy))
				else if(strSubLinks.equals(strNonCorpPolicy))
				{
					return strNonCorpRenewList;
				}//end of else if(strSubLinks.equals(strNonCorpPolicy))
			}//end of if(strMode.equals(strRenewListMode)||strMode.equals(strRenew))
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strRenewList);
		}//end of catch

		return strForwardPath;
	}//end of getForwardPath(HttpServletRequest request)

	/**
	 * This method returns the type of policy.
	 *
	 * @param request The HTTP request we are processing.
	 * @return strType String which gives type of policy.
	 */
	private String getType(HttpServletRequest request)throws TTKException
	{
		String strType="";
		try
		{
			String strSubLinks=TTKCommon.getActiveSubLink(request);
			String strMode=TTKCommon.checkNull(request.getParameter("mode"));
			if(strMode.equals(strRenewListMode)||strMode.equals(strRenew)|| strMode.equals(strReset))
			{
				if(strSubLinks.equals(strIndividualPolicy))
				{
					strType= "IP";
				}//end of if(strSubLinks.equals(strIndividualPolicy))
				else if(strSubLinks.equals(strIndPolicyGroup))
				{
					strType= "IG";
				}//end of else
				else if(strSubLinks.equals(strCorporatePolicy))
				{
					strType= "CP";
				}//end of else if(strSubLinks.equals(strCorporatePolicy))
				else if(strSubLinks.equals(strNonCorpPolicy))
				{
					strType= "NC";
				}//end of else if(strSubLinks.equals(strNonCorpPolicy))
			}//end of if(strMode.equals(strRenewListMode)||strMode.equals(strRenew))
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strRenewList);
		}//end of catch
		return strType;
	}//end of getType(HttpServletRequest request)

	/**
	 * Returns a string which contains the | separated MemberSeqId(s) to be Renewed
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the | separated MemberSeqId(s) to be Renewed
	 */
	private String populateMemberSeqId(HttpServletRequest request, TableData renewListTableData)
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfMemberSeqId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfMemberSeqId.append("|").append(String.valueOf(((MemberVO)renewListTableData.getData().get(
								Integer.parseInt(strChk[i]))).getMemberSeqID().intValue()));
					}//end of if(i == 0)
					else
					{
						sbfMemberSeqId = sbfMemberSeqId.append("|").append(String.valueOf(((MemberVO)
						   renewListTableData.getData().get(Integer.parseInt(strChk[i]))).getMemberSeqID().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfMemberSeqId=sbfMemberSeqId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return sbfMemberSeqId.toString();
	}//end of populateMemberSeqId(HttpServletRequest request, TableData renewListTableData)throws TTKException

	/**
	 * Returns the MemberManager session object for invoking methods on it.
	 * @return MemberManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private MemberManager getMemberManagerObject() throws TTKException
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
			throw new TTKException(exp, "renewlist");
		}//end of catch
		return memberManager;
	}//end getMemberManagerObject() throws TTKException

}//end of RenewMemberAction