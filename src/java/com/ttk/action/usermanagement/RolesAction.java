/**
 * @ (#) RolesAction.javaDec 28, 2005
 * Project      : TTK HealthCare Services
 * File         : RolesAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Dec 28, 2005
 *
 * @author       :  Chandrasekaran J
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
import org.dom4j.Document;
import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.common.SecurityManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.common.security.RoleDocumentHelper;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.security.RoleVO;
import formdef.plugin.util.FormUtils;


/**
 * This class is used to search roles in User Management in Administration flow.
 * This also provides option for adding new role into application.
 */

public class RolesAction extends TTKAction
{
	private static Logger log = Logger.getLogger( RolesAction.class );
	//declarations of the modes
	/*private static final String strDefault="Default";
	private static final String strRoleList="RoleList";*/
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	private static final String strDeleteList="DeleteList";
	private static final String strDelete="Delete";
	/*private static final String strAddEditRole="AddEditRole";
	private static final String strUpdateRole="UpdateRole";*/
	private static final String strClose="Close";

	//declarations of the forward paths
	private static final String strRoleListPath="rolelist";
	private static final String strEditUserRole="edituserrole";
	private static final String strUpdateUserRole="updateuserrole";
	//private static final String strFailure="failure";

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
			log.debug("Inside RolesAction doDefaults");
			setLinks(request);
			TableData  tableData =TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("RolesTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			request.getSession().setAttribute("searchparam", null);

			return this.getForward(strRoleListPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"role"));
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
			log.debug("Inside RolesAction doSearch");
			setLinks(request);
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			SecurityManager securityManagerObject=this.getSecurityManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strRoleListPath));
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
				tableData.createTableInfo("RolesTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alRole= securityManagerObject.getRoleList(tableData.getSearchData());
			tableData.setData(alRole, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strRoleListPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"role"));
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
			log.debug("Inside RolesAction doForward");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			SecurityManager securityManagerObject=this.getSecurityManagerObject();
			tableData.modifySearchData(strForward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			ArrayList alFeedback = securityManagerObject.getRoleList(tableData.getSearchData());
			tableData.setData(alFeedback, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strRoleListPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"role"));
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
			log.debug("Inside RolesAction doBackward");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strBackward);//modify the search data
			SecurityManager securityManagerObject=this.getSecurityManagerObject();
			ArrayList alFeedback = securityManagerObject.getRoleList(tableData.getSearchData());
			tableData.setData(alFeedback, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the table data object to session
			return this.getForward(strRoleListPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"role"));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside RolesAction doDeleteList");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			SecurityManager securityManagerObject=this.getSecurityManagerObject();
			StringBuffer sbfDeleteId = new StringBuffer("|");
			sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().getAttribute("tableData")));
			log.debug("Sequence Id to be Deleted !!!  : "+sbfDeleteId.toString());
			//delete the Role Details
			int iCount = securityManagerObject.deleteRole(sbfDeleteId.append("|").toString());
			int iStartRowCount=0;
			if(iCount>0)
			{
				//refresh the Cache of User Type
				this.refreshCache(null,"Delete");
			}//end of if(iCount>0)

			//refresh the grid with search data in session
			ArrayList alRole = null;//hospitalObject.getFeedbackList(tableData.getSearchData());
			//fetch the data from previous set of rowcounts, if all the records are deleted for the current set of
				//search criteria
			if(iCount == tableData.getData().size())
			{
			 tableData.modifySearchData(strDeleteList);//modify the search data
			 iStartRowCount=Integer.parseInt((String)tableData.getSearchData().get(tableData.getSearchData().size()-2));
			 if(iStartRowCount > 0)
			 {
				alRole = securityManagerObject.getRoleList(tableData.getSearchData());
			 }//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
			   alRole = securityManagerObject.getRoleList(tableData.getSearchData());
			}// end of else
			tableData.setData(alRole, strDeleteList);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strRoleListPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"role"));
		}//end of catch(Exception exp)
   }//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
			log.debug("Inside RolesAction doDeleteList");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			int iStartRowCount=0;
			SecurityManager securityManagerObject=this.getSecurityManagerObject();
			StringBuffer sbfDeleteId = new StringBuffer("|");
			sbfDeleteId.append(String.valueOf(((RoleVO)((TableData)request.getSession().getAttribute("tableData"))
								.getData().get(Integer.parseInt(request.getParameter("rownum")))).getRoleSeqID()));
			//delete the Role Details
			int iCount = securityManagerObject.deleteRole(sbfDeleteId.append("|").toString());
			if(iCount>0)
			{
				//refresh the Cache of User Type
				this.refreshCache(null,"Delete");
			}//end of if(iCount>0)
			//refresh the grid with search data in session
			ArrayList alRole = null;
			//fetch the data from previous set of rowcounts, if all the records are deleted for the current set of
			//search criteria
			if(iCount == tableData.getData().size())
			{
			 tableData.modifySearchData(strDelete);//modify the search data
			 iStartRowCount=Integer.parseInt((String)tableData.getSearchData().get(tableData.getSearchData().size()-2));
			 if(iStartRowCount > 0)
			 {
				alRole = securityManagerObject.getRoleList(tableData.getSearchData());
			 }//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				alRole = securityManagerObject.getRoleList(tableData.getSearchData());
			}// end of else
			tableData.setData(alRole, strDeleteList);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strRoleListPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"role"));
		}//end of catch(Exception exp)
	}//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside RolesAction doAdd");
			setLinks(request);
			DynaActionForm frmUserRoleDetails=(DynaActionForm)form;
			//RoleVO roleVO=new RoleVO();
			Document userProfileDoc=null;
			//initialize the form bean in add mode
			frmUserRoleDetails.initialize(mapping);
			userProfileDoc=RoleDocumentHelper.getUserProfileXML();
			request.setAttribute("UserProfileDoc",userProfileDoc);
			return this.getForward(strEditUserRole,mapping,request);

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"role"));
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
	public ActionForward doViewRole(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside RolesAction doDeleteList");
		   setLinks(request);
		   DynaActionForm frmUserRoleDetails=(DynaActionForm)form;
		   SecurityManager securityManagerObject=this.getSecurityManagerObject();
		   RoleVO roleVO=new RoleVO();
		   Document userProfileDoc=null;
		   roleVO=(RoleVO)((TableData)request.getSession().getAttribute("tableData")).getData().get
										(Integer.parseInt(request.getParameter("rownum")));
		   //call the DAO to get the Role Details and Privilages information
		   roleVO=securityManagerObject.getRole(roleVO.getRoleSeqID());
		   frmUserRoleDetails=(DynaActionForm)FormUtils.setFormValues("frmUserRoleDetails",roleVO,this,mapping,request);
		   request.setAttribute("frmUserRoleDetails",frmUserRoleDetails);
		   userProfileDoc=RoleDocumentHelper.getMergedDocument(roleVO.getPrivilege());
		   request.setAttribute("UserProfileDoc",userProfileDoc);

			return this.getForward(strEditUserRole,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"role"));
		}//end of catch(Exception exp)
	}//end of doEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside RolesAction doSave");
			setLinks(request);
			String[] strPermissions=null;
			Document userProfileDoc=null;
			RoleVO roleVO=new RoleVO();
			DynaActionForm frmUserRoleDetails=(DynaActionForm)form;
			SecurityManager securityManagerObject=this.getSecurityManagerObject();
			//get the applicable permissions
			strPermissions=request.getParameterValues("chkPermissions");
			userProfileDoc=RoleDocumentHelper.getModifiedDocument(strPermissions);

			//load the VO from formutils
			roleVO=(RoleVO)FormUtils.getFormValues(frmUserRoleDetails,this,mapping,request);
			roleVO.setPrivilege(userProfileDoc);
			roleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));

			//get the merged document and put that in requset scope,
			//so that if any run time error occurs page is displayed properly
			if(roleVO.getRoleSeqID()!=null)
				userProfileDoc=RoleDocumentHelper.getMergedDocument(roleVO.getPrivilege());
			else
				userProfileDoc=RoleDocumentHelper.getUserProfileXML();
			request.setAttribute("UserProfileDoc",userProfileDoc);

			//call the DAO to save role details into database
			long lngRoleSeqID=securityManagerObject.saveRole(roleVO);
			if(lngRoleSeqID>0)
			{
				if(roleVO.getRoleSeqID()!=null)
				{
					this.refreshCache(null,"Edit");     //refresh all the Cache of User Type
					//requery to get the updated role details
					roleVO=securityManagerObject.getRole(roleVO.getRoleSeqID());
					frmUserRoleDetails=(DynaActionForm)FormUtils.setFormValues("frmUserRoleDetails",roleVO,this,
																				mapping,request);
					request.setAttribute("frmUserRoleDetails",frmUserRoleDetails);
					userProfileDoc=RoleDocumentHelper.getMergedDocument(roleVO.getPrivilege());
					request.setAttribute("UserProfileDoc",userProfileDoc);
					request.setAttribute("updated","message.savedSuccessfully");    //set the appropriate message
				}//end of if(roleVO.getRoleSeqID()!=null)
				else
				{
					this.refreshCache(roleVO.getUserType(),"Add");  //refresh the corresponding Cache
					frmUserRoleDetails.initialize(mapping);     //initialize the form to add further roles
					userProfileDoc=RoleDocumentHelper.getUserProfileXML();
					request.setAttribute("UserProfileDoc",userProfileDoc);
					request.setAttribute("updated","message.addedSuccessfully");    //set the appropriate message
				}//end of else
			}//end of if(iUpdate>0)
			return this.getForward(strUpdateUserRole,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"role"));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside RolesAction doClose");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			SecurityManager securityManagerObject=this.getSecurityManagerObject();
			if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			{
				//refresh the data in cancel mode, in order to get the new records if any.
				ArrayList alRole = securityManagerObject.getRoleList(tableData.getSearchData());
				tableData.setData(alRole, strClose);
				request.getSession().setAttribute("tableData",tableData);
			}//end if
			return this.getForward(strRoleListPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"role"));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
		//loop through to populate delete sequence id's and get the value from session for the matching check
			//box value
		for(int i=0; i<strChk.length;i++)
		{
			if(strChk[i]!=null)
			{
			  //extract the sequence id to be deleted from the value object
			  if(i == 0)
			  {
				sbfDeleteId.append(((RoleVO)tableData.getData().get(Integer.parseInt(strChk[i]))).
									getRoleSeqID().intValue());
			  }//end of if(i == 0)
			else
			  {
				sbfDeleteId.append("|").append(((RoleVO)tableData.getData().get(Integer.parseInt(strChk[i]))).
												getRoleSeqID().intValue());
			   }//end of else
			}//end of if(strChk[i]!=null)
		}//end of for(int i=0; i<strChk.length;i++)
	}//end of if(strChk!=null&&strChk.length!=0)
	return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)

	/**
	 * Returns the ArrayList which contains the populated search criteria elements.
	 * @param frmSearchRoles DynaActionForm will contains the values of corresponding fields.
	 * @param strAsscProcCode String which contain already associated procedure codes.
	 * @param request HttpServletRequest object which contains the search parameter that is built.
	 * @return alSearchParams ArrayList.
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSearchRoles, HttpServletRequest request)
	{
	  //build the column names along with their values to be searched
	  ArrayList<Object> alSearchParams = new ArrayList<Object>();
	  alSearchParams.add(new SearchCriteria("ROLE_NAME", TTKCommon.replaceSingleQots(
												(String)frmSearchRoles.get("sRoleName"))));
	  alSearchParams.add(new SearchCriteria("USER_GENERAL_TYPE_ID", (String)frmSearchRoles.get("sTpaUsers"),"equals"));
	  return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmSearchRoles, HttpServletRequest request)

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
			}//end of if(securityManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "role");
		}//end of catch
		return securityManager;
	}//end getSecurityManagerObject()

	/**
	 * This method refreshes the Cache of corresponding User Type
	 * whenever the role is added.
	 * It refreshes all the Cache of User Type whenever Roles are edited or deleted
	 * @param strIdentifier String Cache to be refreshed
	 * @param strMode String indicates the mode in which Cache to refreshed
	 * @throws TTKException if any run time error occures
	 */
	private void refreshCache(String strIdentifier,String strMode) throws TTKException
	{
		
		if(strMode.equals("Delete") || strMode.equals("Edit") )//Added Add for Broker Change Req
		{
			//refresh all the User Type Cache
			Cache.refresh("HOS");
			Cache.refresh("INS");
			Cache.refresh("TTK");
			Cache.refresh("CAL");
			Cache.refresh("COR");
			Cache.refresh("AGN");
			Cache.refresh("DMC");
			Cache.refresh("BRO");
		}//end of if(strMode.equals("Delete") || strMode.equals("Edit"))
		else if(strIdentifier!=null && strMode.equals("Add"))
		{
			//refresh the corresponding User Type Cache
			Cache.refresh(strIdentifier);
			Cache.refresh("TTK");//Added to refresh when Broker is addded, to see new data in Association
		}//end of else if(strIdentifier!=null && strMode.equals("Add"))
	}//end of refreshCache(String strIdentifier,String strMode)
}// end of RolesAction