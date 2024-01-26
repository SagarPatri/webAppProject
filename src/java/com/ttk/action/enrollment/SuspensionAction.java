/**
 * @ (#) SuspensionAction.java Feb 7, 2006
 * Project      : TTK HealthCare Services
 * File         : SuspensionAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Feb 7, 2006
 *
 * @author      :  Chandrasekaran J
 * Modified by  :
 * Modified date:
 * Reason       :
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
import com.ttk.business.enrollment.MemberManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberVO;
import formdef.plugin.util.FormUtils;

/**
 * This action reusable for getting suspension information for individual policy,individual policy as group,
 * corporate policies and non corporate policies in endorsement flow.
 * This class also provides facility to add the delete the suspension information.
 */

public class SuspensionAction extends TTKAction
{
	private static Logger log = Logger.getLogger(SuspensionAction.class);
	//string for setting mode
	/*private static final String strSuspensionList="SuspensionList";
	private static final String strSaveSuspension="SaveSuspensionDetails";
	private static final String strEditSuspension="EditSuspensionDetails";
	private static final String strDeleteSuspension="DeleteList";*/
	//string for forwarding
	private static final String strIndSuspension="indsuspensionlist";
	private static final String strGrpSuspension="grpsuspensionlist";
	private static final String strCorpSuspension="corpsuspensionlist";
	private static final String strNonCorpSuspension="noncorpsuspensionlist";
	//private static final String strFailure="failure";
	//string for comparision
	private static final String strEnrollment="Enrollment";
	private static final String strIndPolicy="Individual Policy";
	private static final String strGrpPolicy="Ind. Policy as Group";
	private static final String strCorPolicy="Corporate Policy";
	private static final String strNonCorPolicy="Non-Corporate Policy";

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
			log.debug("Inside SuspensionAction doSearch");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			MemberManager MemberManagerObject=this.getMemberManagerObject();
			ArrayList alSuspensionList=null;
			String strSuspension=getForwardPath(request);
			//To Get the member sequence id from the previos screen the form is taken from the session
			DynaActionForm frmAddMember =(DynaActionForm)request.getSession().getAttribute("frmAddMember");
			Long lngMemberSeqId =TTKCommon.getLong((String)frmAddMember.get("memberSeqID"));
			//get the table data from session if exists
			TableData  tableDataSuspension =null;
			if((request.getSession()).getAttribute("tableDataSuspension") == null)
			{
				tableDataSuspension = new TableData();
			}//end of if((request.getSession()).getAttribute("PEDTableData") == null)
			else
			{
				tableDataSuspension = (TableData)(request.getSession()).getAttribute("tableDataSuspension");
			}//end of else
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPolicyNbr = TTKCommon.getWebBoardDesc(request);
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("[").append(strPolicyNbr).append("]").append("[").
									append(frmAddMember.get("enrollmentID")).append("]");
			DynaActionForm frmSuspension=(DynaActionForm)form;
			frmSuspension.set("caption",String.valueOf(strCaption));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strSortID.equals(""))
			{
				tableDataSuspension.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableDataSuspension.modifySearchData("sort");//modify the search data
			}// end of if(!strSortID.equals(""))
			else
			{
				tableDataSuspension.createTableInfo("SuspensionTable",null);
				tableDataSuspension.setSearchData(this.populateSearchCriteria(lngMemberSeqId));
				tableDataSuspension.modifySearchData("search");
			}// end of else
			alSuspensionList=MemberManagerObject.getSuspensionList(tableDataSuspension.getSearchData());
			tableDataSuspension.setData(alSuspensionList);
			request.getSession().setAttribute("tableDataSuspension",tableDataSuspension);
			//finally return to the grid screen
			return (mapping.findForward(strSuspension));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"suspension"));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside SuspensionAction doSave");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			String strSuspension=getForwardPath(request);
			//To Get the member sequence id from the previos screen the form is taken from the session
			DynaActionForm frmAddMember =(DynaActionForm)request.getSession().getAttribute("frmAddMember");
			Long lngMemberSeqId=TTKCommon.getLong((String)frmAddMember.get("memberSeqID"));
			ArrayList alSuspensionList=null;
			MemberManager MemberManagerObject=this.getMemberManagerObject();
			DynaActionForm frmSuspension=(DynaActionForm)form;
			MemberVO memberVO =new MemberVO();
			String strPolicyType=getpolicyType(request);
			//get the table data from session if exists
			TableData  tableDataSuspension =null;
			if((request.getSession()).getAttribute("tableDataSuspension") == null)
			{
				tableDataSuspension = new TableData();
			}//end of if((request.getSession()).getAttribute("PEDTableData") == null)
			else
			{
				tableDataSuspension = (TableData)(request.getSession()).getAttribute("tableDataSuspension");
			}//end of else

			memberVO=(MemberVO)FormUtils.getFormValues(frmAddMember, this, mapping, request);
			String strPolicyNbr = TTKCommon.getWebBoardDesc(request);
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("[").append(strPolicyNbr).append("]").append("[").
							append(frmAddMember.get("enrollmentID")).append("]");
			if(!TTKCommon.checkNull(request.getParameter("rownum")).equals(""))
			{
				memberVO.setSupensionSeqID(((MemberVO)
						((TableData)request.getSession().getAttribute("tableDataSuspension")).getRowInfo(
								Integer.parseInt(request.getParameter("rownum")))).getSupensionSeqID());
			}//end of if(!TTKCommon.checkNull(request.getParameter("rownum")).equals(""))
			memberVO.setStartDate(TTKCommon.getUtilDate((String)frmSuspension.get("startDate")));
			memberVO.setEndDate(TTKCommon.getUtilDate((String)frmSuspension.get("endDate")));
			memberVO.setMemberSeqID(lngMemberSeqId);
			memberVO.setEndorsementSeqID(WebBoardHelper.getEndorsementSeqId(request));
			memberVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			int iUpdate=MemberManagerObject.saveSuspension(memberVO,strSwitchType,strPolicyType);

			if(iUpdate>0)
			{
				frmSuspension.initialize(mapping);
				if(memberVO.getSupensionSeqID()!=null)
				{
					if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
					{
						((TableData)request.getSession().getAttribute("tableDataSuspension")).getData().get(
								Integer.parseInt(request.getParameter("rownum")));
					}// end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
					//set the appropriate message
					request.setAttribute("updated","message.savedSuccessfully");
				}// end of if(memberVO.getSupensionSeqID()!=null)
				else
				{
					//set the appropriate message
					request.setAttribute("updated","message.addedSuccessfully");
				}//end else
				//refresh the data in order to get the new records if any.
				alSuspensionList=MemberManagerObject.getSuspensionList(tableDataSuspension.getSearchData());
				tableDataSuspension.setData(alSuspensionList);
				frmSuspension.set("caption",String.valueOf(strCaption));
				request.getSession().setAttribute("tableDataSuspension",tableDataSuspension);
			}// end of if(iUpdate>0)
			frmSuspension.set("rownum",null);
			return (mapping.findForward(strSuspension));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"suspension"));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside SuspensionAction doDeleteList");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			DynaActionForm frmSuspension=(DynaActionForm)form;
			String strSuspension=getForwardPath(request);
			//get the table data from session if exists
			TableData  tableDataSuspension =null;
			if((request.getSession()).getAttribute("tableDataSuspension") == null)
			{
				tableDataSuspension = new TableData();
			}//end of if((request.getSession()).getAttribute("PEDTableData") == null)
			else
			{
				tableDataSuspension = (TableData)(request.getSession()).getAttribute("tableDataSuspension");
			}//end of else
			MemberManager MemberManagerObject=this.getMemberManagerObject();
			String strPolicyType=getpolicyType(request);
			ArrayList alSuspensionList=null;
			StringBuffer sbfDeleteId = new StringBuffer();
			ArrayList <Object>alSuspensionDelete=new ArrayList<Object>();
			sbfDeleteId.append(populateDeleteId(request,
											(TableData)request.getSession().getAttribute("tableDataSuspension")));
			alSuspensionDelete.add("SUSPEND");
			alSuspensionDelete.add(String.valueOf(sbfDeleteId));

			if(strSwitchType.equals("ENM"))
			{
				alSuspensionDelete.add(WebBoardHelper.getPolicySeqId(request));
			}// end of if(strSwitchType.equals("ENM"))
			else
			{
				alSuspensionDelete.add(WebBoardHelper.getEndorsementSeqId(request));
			}// end of else
			alSuspensionDelete.add(strSwitchType);
			alSuspensionDelete.add(strPolicyType);

			alSuspensionDelete.add(TTKCommon.getUserSeqId(request));
			//delete the Suspension Details
			int iCount = MemberManagerObject.deleteSuspension(alSuspensionDelete);
			log.debug("iCount value is :"+iCount);
			//refresh the data in order to get the new records if any.
			alSuspensionList=MemberManagerObject.getSuspensionList(tableDataSuspension.getSearchData());
			tableDataSuspension.setData(alSuspensionList);
			frmSuspension.set("rownum",null);
			request.getSession().setAttribute("tableDataSuspension",tableDataSuspension);
			frmSuspension.initialize(mapping);
			return (mapping.findForward(strSuspension));

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"suspension"));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
	public ActionForward doViewSuspension(ActionMapping mapping,ActionForm form,HttpServletRequest request,
											HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside SuspensionAction doViewContacts");
			DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
			String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
			this.setLinks(request,strSwitchType);
			DynaActionForm frmSuspension=(DynaActionForm)form;
			String strPolicyNbr = TTKCommon.getWebBoardDesc(request);
			//To Get the member sequence id from the previos screen the form is taken from the session
			DynaActionForm frmAddMember =(DynaActionForm)request.getSession().getAttribute("frmAddMember");
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("[").append(strPolicyNbr).append("]").append("[").
													append(frmAddMember.get("enrollmentID")).append("]");
			String strSuspension=getForwardPath(request);
			//if rownumber is found populate the form object
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				MemberVO memberVO=(MemberVO)((TableData)request.getSession().getAttribute("tableDataSuspension")).
								getData().get(Integer.parseInt(request.getParameter("rownum")));
				frmSuspension = (DynaActionForm)FormUtils.setFormValues("frmSuspension",memberVO,
								this,mapping,request);
				frmSuspension.set("rownum",(String)request.getParameter("rownum"));
				frmSuspension.set("caption",String.valueOf(strCaption));
			}// end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			else
			{
				frmSuspension.initialize(mapping);
			}// end of else
			request.setAttribute("frmSuspension",frmSuspension);
			return (mapping.findForward(strSuspension));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"suspension"));
		}//end of catch(Exception exp)
	}//end of doViewSuspension(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  //HttpServletResponse response)

	/**
	 * Returns a string which contains the | separated sequence id's to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the | separated sequence id's to be delete
	 * @throws TTKException If any run time Excepton occures
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableDataSuspension)throws TTKException
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
						sbfDeleteId.append("|").append(String.valueOf(((MemberVO)tableDataSuspension.getData().
							get(Integer.parseInt(strChk[i]))).getSupensionSeqID().intValue()));//setSuspensionSeqID
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((MemberVO)tableDataSuspension.
								getData().get(Integer.parseInt(strChk[i]))).getSupensionSeqID().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId=sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return String.valueOf(sbfDeleteId);
	}//end of populateDeleteId(HttpServletRequest request, TableData tableDataSuspension)

	/**
	 * This method returns the suspension forward path for next view based on the Flow
	 *
	 * @param request The HTTP request we are processing.
	 * @return strForwardPath String forward path for the next view
	 */
	private String getForwardPath(HttpServletRequest request)throws TTKException
	{
		String strSuspension=null;
		try
		{
			String strLink=TTKCommon.getActiveLink(request);
			String strSubLink=TTKCommon.getActiveSubLink(request);
			if(strLink.equals(strEnrollment)&& strSubLink.equals(strIndPolicy))
			{
				strSuspension=strIndSuspension;

			}//end of if(strLink.equals(strEnrollment)&& strSubLink.equals(strIndPolicy))
			else if(strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))
			{
				strSuspension=strGrpSuspension;

			}//end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))
			else if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
			{
				strSuspension=strCorpSuspension;

			}// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
			else if(strLink.equals(strEnrollment)&& strSubLink.equals(strNonCorPolicy))
			{
				strSuspension=strNonCorpSuspension;

			}// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strNonCorPolicy))
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "icdlist");
		}//end of catch

		return strSuspension;
	}//end of getForwardPath(HttpServletRequest request)

	/**
	 * This method returns the type of policy.
	 *
	 * @param request The HTTP request we are processing.
	 * @return strPolicyType String which gives type of policy.
	 */
	private String getpolicyType(HttpServletRequest request)throws TTKException
	{
		String strPolicyType=null;
		try
		{
			String strLink=TTKCommon.getActiveLink(request);
			String strSubLink=TTKCommon.getActiveSubLink(request);
			if(strLink.equals(strEnrollment)&& strSubLink.equals(strIndPolicy))
			{
				strPolicyType="IP";
			}//end of if(strLink.equals(strEnrollment)&& strSubLink.equals(strIndPolicy))
			else if(strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))
			{
				strPolicyType="IG";
			}//end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strGrpPolicy))
			else if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
			{
				strPolicyType="CP";
			}// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strCorPolicy))
			else if(strLink.equals(strEnrollment)&& strSubLink.equals(strNonCorPolicy))
			{
				strPolicyType="NC";
			}// end of else if(strLink.equals(strEnrollment)&& strSubLink.equals(strNonCorPolicy))
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "icdlist");
		}//end of catch
		return strPolicyType;
	}//end of getpolicyType(HttpServletRequest request)

	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param Long lngMemberSeqId
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(Long lngMemberSeqId)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(String.valueOf(lngMemberSeqId));
		return alSearchParams;
	}//end of populateSearchCriteria(Long lngMemberSeqId)

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
			throw new TTKException(exp, "suspension");
		}//end of catch
		return memberManager;
	}//end getMemberManager()
}// end of SuspensionAction