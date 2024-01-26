/**
 * @ (#) UserGroupAction.java 28th Dec 2005
 * Project      : TTK HealthCare Services
 * File         : UserGroupAction.java
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : 28th Dec 2005
 *
 * @author       :  Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.usermanagement;

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
import com.ttk.business.common.SecurityManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.security.GroupVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching the User Grooup.
 * This class also provides option for adding and deleting the User Group.
 */

public class UserGroupAction extends TTKAction
{
	private static Logger log = Logger.getLogger( UserGroupAction.class );
	//Declare the modes used in this action
	/*private static final String strDefault="Default";
	private static final String strAssociateUserList="associateuserlist";
	private static final String strUserGroupList="usergrouplist";*/
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	/*private static final String strSaveUserGroup="SaveUserGroup";
	private static final String strAddEditUserGroup="AddEditUserGroup";*/
	private static final String strDeleteList="DeleteList";
	private static final String strDelete="Delete";
	private static final String strClose="Close";

	//Declare the forword used in this action
	private static final String strUsers="users";
	private static final String strAssociateUserlist="associateuserlist";
	private static final String strEditUsergroup="editusergroup";

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
			setLinks(request);
			log.debug("Inside UserGroupAction doDefault");
			DynaActionForm frmEditUserGroup = (DynaActionForm)form;
			TableData tableData = TTKCommon.getTableData(request);
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
											request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			//create new table data object
			tableData = new TableData();
			//clear the dynaform if visiting from left links for the first time
			((DynaActionForm)form).initialize(mapping);//reset the form data
			//create the required grid table
			tableData.createTableInfo("UserGroupTable",null);
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			frmEditUserGroup.set("listofficeInfo", strDefaultBranchID);
			return this.getForward(strUsers, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"user"));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside UserGroupAction doSearch");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			SecurityManager securityManagerObject = this.getSecurityManagerObject();
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strUsers));
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
				tableData.createTableInfo("UserGroupTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alUserGroup= securityManagerObject.getGroupList(tableData.getSearchData());
			tableData.setData(alUserGroup, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);

			//finally return to the grid screen
			return this.getForward(strUsers, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"user"));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside UserGroupAction doForward");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			SecurityManager securityManagerObject=this.getSecurityManagerObject();
			tableData.modifySearchData(strForward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			ArrayList alUserGroups = securityManagerObject.getGroupList(tableData.getSearchData());
			tableData.setData(alUserGroups, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strUsers, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"user"));
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
	 * @throws Exception if any error occurs
	 */
	public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside UserGroupAction doBackward");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			SecurityManager securityManagerObject=this.getSecurityManagerObject();
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alUserGroups = securityManagerObject.getGroupList(tableData.getSearchData());
			tableData.setData(alUserGroups, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strUsers, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"user"));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside UserGroupAction doSave");
			setLinks(request);
			GroupVO groupVO=new GroupVO();
			StringBuffer strCaption=new StringBuffer();
			strCaption.append("User Group Details -");
			SecurityManager securityManagerObject=this.getSecurityManagerObject();
			DynaActionForm frmEditUserGroup = (DynaActionForm)form;
			groupVO = (GroupVO) FormUtils.getFormValues(frmEditUserGroup, this, mapping, request);
			log.debug("Group sequence id is "+groupVO.getGroupSeqID());
			groupVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Sequence ID
			long iUpdate = securityManagerObject.saveGroup(groupVO);
			if(iUpdate > 0)
			{
				if(groupVO.getGroupSeqID()!= null)
				{
					//This if block is needed while resetting the data in edit mode to over write the table data
					if(!((String)frmEditUserGroup.get("rownum")).equals(""))
					{
						((TableData)request.getSession().getAttribute("tableData")).getData().set(
											Integer.parseInt((String)frmEditUserGroup.get("rownum")), groupVO);
					}//end of if(!((String)userGroupForm.get("rownum")).equals(""))
					//set the appropriate message
					request.setAttribute("updated","message.savedSuccessfully");
					strCaption.append("Edit ");
				}// end of if(groupVO.getGroupSeqID()!= null)
				else
				{
					frmEditUserGroup.initialize(mapping);
					//set the appropriate message
					request.setAttribute("updated","message.addedSuccessfully");
					strCaption.append("Add ");
				}//end else
			}//end of if(iUpdate > 0)
			frmEditUserGroup.set("caption",String.valueOf(strCaption));
			return this.getForward(strEditUsergroup, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"user"));
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
			log.debug("Inside UserGroupAction doReset");
			setLinks(request);
			DynaActionForm frmEditUserGroup = (DynaActionForm)form;
			GroupVO groupVO=null;
			StringBuffer strCaption=new StringBuffer();
			strCaption.append("User Group Details -");
			SecurityManager securityManagerObject=this.getSecurityManagerObject();
			if(!TTKCommon.checkNull((String)frmEditUserGroup.get("groupBranchSeqID")).equals(""))
			{
				Long lGroupBranchSeqID=TTKCommon.getLong((String)frmEditUserGroup.get("groupBranchSeqID"));
				groupVO=securityManagerObject.getGroup(lGroupBranchSeqID);
				strCaption.append("Edit ");
			}//end of if
			else
			{
				groupVO=new GroupVO();
				strCaption.append("Add ");
			}//end of else if
			frmEditUserGroup = (DynaActionForm)FormUtils.setFormValues("frmEditUserGroup",groupVO,this,mapping,request);
			frmEditUserGroup.set("caption",String.valueOf(strCaption));
			request.getSession().setAttribute("frmEditUserGroup",frmEditUserGroup);

			return this.getForward(strEditUsergroup,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"user"));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
			log.debug("Inside UserGroupAction doAdd");
			setLinks(request);
			DynaActionForm frmEditUserGroup=(DynaActionForm)form;
			StringBuffer strCaption=new StringBuffer();
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
															getAttribute("UserSecurityProfile")).getBranchID());
			strCaption.append("User Group Details -");
			//if rownumber is found populate the form object
			frmEditUserGroup.initialize(mapping);
			strCaption.append("Add ");
			frmEditUserGroup.set("caption",String.valueOf(strCaption));
			frmEditUserGroup.set("officeSeqID", strDefaultBranchID);
			return this.getForward(strEditUsergroup, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"user"));
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
	public ActionForward doViewUserGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,
											HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside UserGroupAction doViewUserGroup");
			setLinks(request);
			DynaActionForm frmEditUserGroup=(DynaActionForm)form;
			StringBuffer strCaption=new StringBuffer();
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
												getAttribute("UserSecurityProfile")).getBranchID());
			strCaption.append("User Group Details -");
			//if rownumber is found populate the form object
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				GroupVO groupVO = (GroupVO)((TableData)request.getSession().getAttribute("tableData")).getData().
												get(Integer.parseInt(request.getParameter("rownum")));
				frmEditUserGroup = (DynaActionForm)FormUtils.setFormValues("frmEditUserGroup",groupVO,
															this,mapping,request);
				strCaption.append("Edit ");
				request.getSession().setAttribute("frmEditUserGroup",frmEditUserGroup);
			}// end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			frmEditUserGroup.set("caption",String.valueOf(strCaption));
			//frmEditUserGroup.set("officeSeqID", strDefaultBranchID);
			return this.getForward(strEditUsergroup, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"user"));
		}//end of catch(Exception exp)
	}//end of doViewUserGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * This method is used to get the delete records from the grid screen.
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
			log.debug("Inside UserGroupAction doDeleteList");
			setLinks(request);
			StringBuffer sbfDeleteId = new StringBuffer("|");
			int iStartRowCount=0;
			SecurityManager securityManagerObject = this.getSecurityManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			//populate the delete string which contains the sequence id's to be deleted
			sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().getAttribute("tableData")));
			//delete the delete Group
			int iCount = securityManagerObject.deleteGroup(sbfDeleteId.append("|").toString());
			//refresh the grid with search data in session
			ArrayList alUserGroup = null;
			//fetch the data from previous set of rowcounts, if all the records are deleted for the current set
				//of search criteria
			if(iCount == tableData.getData().size())
			{
			 tableData.modifySearchData(strDeleteList);//modify the search data
			 iStartRowCount=Integer.parseInt((String)tableData.getSearchData().get(tableData.getSearchData().size()-2));
			 if(iStartRowCount > 0)
			 {
				alUserGroup = securityManagerObject.getGroupList(tableData.getSearchData());
			 }//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				alUserGroup = securityManagerObject.getGroupList(tableData.getSearchData());
			}// end of else
			tableData.setData(alUserGroup, strDeleteList);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strUsers, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"user"));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			log.debug("Inside UserGroupAction doDeleteList");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			int iStartRowCount=0;
			StringBuffer sbfDeleteId = new StringBuffer("|");
			//populate the delete string which contains the sequence id's to be deleted
			sbfDeleteId.append(String.valueOf(((GroupVO)((TableData)request.getSession().getAttribute("tableData")).
							getData().get(Integer.parseInt(request.getParameter("rownum")))).getGroupBranchSeqID()));
			SecurityManager securityManagerObject = this.getSecurityManagerObject();
			int iCount = securityManagerObject.deleteGroup(sbfDeleteId.append("|").toString());
			//refresh the grid with search data in session
			ArrayList alUserGroup = null;
			//fetch the data from previous set of rowcounts, if all the records are deleted for the current
			//set of search criteria
			if(iCount == tableData.getData().size())
			{
			 tableData.modifySearchData(strDelete);//modify the search data
			 iStartRowCount=Integer.parseInt((String)tableData.getSearchData().get(tableData.getSearchData().size()-2));
			 if(iStartRowCount > 0)
			 {
				alUserGroup = securityManagerObject.getGroupList(tableData.getSearchData());
			 }//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				alUserGroup = securityManagerObject.getGroupList(tableData.getSearchData());
			}// end of else
			tableData.setData(alUserGroup, strDelete);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strUsers, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"user"));
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
			log.debug("Inside UserGroupAction doClose");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			SecurityManager securityManagerObject = this.getSecurityManagerObject();
			if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			{
				//refresh the data in cancel mode, in order to get the new records if any.
				ArrayList alUserGroup = securityManagerObject.getGroupList(tableData.getSearchData());
				tableData.setData(alUserGroup, strClose);
				request.getSession().setAttribute("tableData",tableData);
			}//end of if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			return this.getForward(strUsers, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"user"));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to associate user to group.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAssociateUserList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside UserGroupAction doAssociateUserList");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			String strRowNo = "";
			GroupVO groupVO = null;
			DynaActionForm frmUserGrouplist = (DynaActionForm)form;
			strRowNo=(String)frmUserGrouplist.get("rownum");
			groupVO = ((GroupVO)tableData.getData().get(Integer.parseInt(strRowNo)));
			//	set the Group User data object to session
			request.setAttribute("GroupVO",groupVO);
			return mapping.findForward(strAssociateUserlist);

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"user"));
		}//end of catch(Exception exp)
	}//end of doAssociateUserList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)



	/**
	 * Returns a string which contains the comma separated sequence id's to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the comma separated sequence id's to be deleted
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableData)
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
		 //loop through to populate delete sequence id's and get the value from session for the matching check box value
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append(((GroupVO)tableData.getData().get(Integer.parseInt(strChk[i]))).
									getGroupBranchSeqID().intValue());
					}//end of if(i == 0)
					else
					{
						sbfDeleteId.append("|").append(((GroupVO)tableData.getData().get(Integer.parseInt(strChk[i]))).
								getGroupBranchSeqID().intValue());
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)

		}//end of if(strChk!=null&&strChk.length!=0)
		log.debug("DELETE IDS !!!!!!! "+sbfDeleteId.toString());
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)

	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSearchUser,HttpServletRequest request)
	{
	  //build the column names along with their values to be searched
	  ArrayList<Object> alSearchParams = new ArrayList<Object>();
	  alSearchParams.add(new SearchCriteria("GROUP_NAME", TTKCommon.replaceSingleQots((String)frmSearchUser.
												get("susergroup"))));
												
	//Modified TPA_OFFICE_SEQ_ID to C.TPA_OFFICE_SEQ_ID as per 11G Migration
	  alSearchParams.add(new SearchCriteria("C.TPA_OFFICE_SEQ_ID", (String)frmSearchUser.get("listofficeInfo"),"equals"));
	  return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmSearchUser,HttpServletRequest request)

	/**
	 * Returns the SecurityManager session object for invoking methods on it.
	 * @return SecurityManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private SecurityManager getSecurityManagerObject() throws TTKException
	{
		SecurityManager securityManager = null;
		try
		{
			if(securityManager == null)
			{
				InitialContext ctx = new InitialContext();
				securityManager = (SecurityManager) ctx.lookup("java:global/TTKServices/business.ejb3/SecurityManagerBean!com.ttk.business.common.SecurityManager");	//changed for jboss upgradation
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "security");
		}//end of catch(Exception exp)
		return securityManager;
	}//end getSecurityManagerObject()
}//end of class UserGroupAction