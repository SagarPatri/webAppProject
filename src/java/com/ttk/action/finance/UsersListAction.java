/**
 * @ (#) UsersListAction.java Jun 10, 2006
 * Project 	     : TTK HealthCare Services
 * File          : UsersListAction.java
 * Author        : Raghavendra T M
 * Company       : Span Systems Corporation
 * Date Created  : Jun 10, 2006
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.finance;


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
import com.ttk.business.usermanagement.UserManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.usermanagement.UserListVO;

/**
 * This class is used for searching of List of Users.
 */
public class UsersListAction extends TTKAction  {
	private static Logger log = Logger.getLogger( UsersListAction.class );
	//	Modes
	private static final String strForward="Forward";
	private static final String strBackward="Backward";
	//  Action mapping forwards
	private static final String strUsersList = "selectuserslist";
	private static final String strSelectUserDetails = "selectuserdetail";
	//  Exception Message Identifier
	private static final String strUserList="UsersList";

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
    		log.debug("Inside UsersListAction doDefault");
    		setLinks(request);
    		TableData tableData=null;
    		StringBuffer strCaption=new StringBuffer();
    		String strSubLink=TTKCommon.getActiveSubLink(request);
    		String strForwardList="";
    		DynaActionForm frmUsersList = (DynaActionForm)form;
    		if(strSubLink.equals("Bank Account"))
			{
				strForwardList = strUsersList;
			}// end of if(strSubLink.equals("Bank Account"))
			
    		//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("UsersListTable",new ArrayList());
			request.getSession().setAttribute("tableDataListOfUsers",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			strCaption.append(" - ["+TTKCommon.getWebBoardDesc(request)+"]");
			frmUsersList.set("caption",strCaption.toString());
			return this.getForward(strForwardList,mapping,request);
    	}//end of try
    	
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strUserList));
		}//end of catch(Exception exp)
    }//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		setLinks(request);
    		log.debug("Inside UsersListAction doSearch");
    		TableData tableData=null;
    		if((request.getSession()).getAttribute("tableDataListOfUsers") != null)
    		{
				tableData= (TableData)(request.getSession()).getAttribute("tableDataListOfUsers");
    		}//end of if((request.getSession()).getAttribute("tableDataListOfUsers") != null)
			else
			{
				tableData = new TableData();
			}//end of else
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if
    		String strSubLink=TTKCommon.getActiveSubLink(request);
    		String strForwardList="";
    		ArrayList alUsersList = null;
    		UserManager UserManagerObject=this.getUserManagerObject();
    		if(strSubLink.equals("Bank Account"))
			{
				strForwardList = strUsersList;
			}// end of if(strSubLink.equals("Bank Account"))
			//else get the dynaform data from session
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strForwardList));
				}//end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("UsersListTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			alUsersList= UserManagerObject.getUserList(tableData.getSearchData(),"Finance");
			tableData.setData(alUsersList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableDataListOfUsers",tableData);
			//finally return to the grid screen
			return this.getForward(strForwardList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strUserList));
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
    		setLinks(request);
    		log.debug("Inside UsersListAction doForward");
    		TableData tableData=null;
    		if((request.getSession()).getAttribute("tableDataListOfUsers") != null)
    		{
				tableData= (TableData)(request.getSession()).getAttribute("tableDataListOfUsers");
    		}//end of if((request.getSession()).getAttribute("tableDataListOfUsers") != null)
			else
			{
				tableData = new TableData();
			}//end of else
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if
    		ArrayList alUsersList = null;
    		UserManager UserManagerObject=this.getUserManagerObject();
    		String strSubLink=TTKCommon.getActiveSubLink(request);
    		String strForwardList="";
    		if(strSubLink.equals("Bank Account"))
    		{
    			strForwardList = strUsersList;
    		}// end of if(strSubLink.equals("Bank Account"))
    		tableData.modifySearchData(strForward);//modify the search data
    		alUsersList= UserManagerObject.getUserList(tableData.getSearchData(),"Finance");
    		tableData.setData(alUsersList, strForward);//set the table data
    		request.getSession().setAttribute("tableDataListOfUsers",tableData);//set the table data object to session
    		return this.getForward(strForwardList, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strUserList));
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
    		setLinks(request);
    		log.debug("Inside UsersListAction doBackward");
    		TableData tableData=null;
    		if((request.getSession()).getAttribute("tableDataListOfUsers") != null)
    		{
				tableData= (TableData)(request.getSession()).getAttribute("tableDataListOfUsers");
    		}//end of if((request.getSession()).getAttribute("tableDataListOfUsers") != null)
			else
			{
				tableData = new TableData();
			}//end of else
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if
    		ArrayList alUsersList = null;
    		UserManager UserManagerObject=this.getUserManagerObject();
    		String strSubLink=TTKCommon.getActiveSubLink(request);
    		String strForwardList="";
    		if(strSubLink.equals("Bank Account"))
    		{
    			strForwardList = strUsersList;
    		}// end of if(strSubLink.equals("Bank Account"))
    		tableData.modifySearchData(strBackward);//modify the search data
    		alUsersList= UserManagerObject.getUserList(tableData.getSearchData(),"Finance");
    		tableData.setData(alUsersList, strBackward);//set the table data
    		request.getSession().setAttribute("tableDataListOfUsers",tableData);//set the table data object to session
    		return this.getForward(strForwardList, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strUserList));
    	}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doViewUser(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside UsersListAction doViewUser");
    		UserListVO userListVO=null;
    		TableData tableData=null;
    		if((request.getSession()).getAttribute("tableDataListOfUsers") != null)
    		{
				tableData= (TableData)(request.getSession()).getAttribute("tableDataListOfUsers");
    		}//end of if((request.getSession()).getAttribute("tableDataListOfUsers") != null)
			else
			{
				tableData = new TableData();
			}//end of else
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if
    		String strSubLink=TTKCommon.getActiveSubLink(request);
    		String strForwardDetails="";
    		if(strSubLink.equals("Bank Account"))
    		{
    			strForwardDetails = strSelectUserDetails;
    		}// end of if(strSubLink.equals("Bank Account"))
    		DynaActionForm frmGeneral = (DynaActionForm) request.getSession().getAttribute("frmSelectUser");
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			userListVO = (UserListVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
    			if(frmGeneral!=null)
    			{
    				frmGeneral.set("frmChanged","changed");
    				frmGeneral.set("contactSeqId",String.valueOf(userListVO.getContactSeqId()));
    				frmGeneral.set("userID",userListVO.getUserID());
    				frmGeneral.set("userName",userListVO.getUserName());
    				frmGeneral.set("roleName",userListVO.getRoleName());//
    				frmGeneral.set("officeCode",userListVO.getName());
    			}//end of if
    		}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		request.getSession().removeAttribute("frmUsersList");
    		request.getSession().removeAttribute("tableDataListOfUsers");
    		return (mapping.findForward(strForwardDetails));
    	}
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strUserList));
    	}//end of catch(Exception exp)
    }//end of doViewUser(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param frmCardPrinting DynaActionForm
	 * @param request HttpServletRequest
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmUsersList,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmUsersList.get("UserID")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmUsersList.get("UserName")));
		alSearchParams.add(new Long(TTKCommon.getUserSeqId(request).toString()));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmInvestigation,HttpServletRequest request)

	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private UserManager getUserManagerObject() throws TTKException
	{
		UserManager userManager = null;
		try
		{
			if(userManager == null)
			{
				InitialContext ctx = new InitialContext();
				userManager = (UserManager) ctx.lookup("java:global/TTKServices/business.ejb3/UserManagerBean!com.ttk.business.usermanagement.UserManager");
			}//end of if(userManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "UserManager");
		}//end of catch
		return userManager;
	}//end of getUserManagerObject()
}//end of UsersListAction
