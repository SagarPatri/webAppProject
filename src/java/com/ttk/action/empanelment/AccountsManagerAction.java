package com.ttk.action.empanelment;

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
import com.ttk.business.empanelment.GroupRegistrationManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.usermanagement.UserListVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

public class AccountsManagerAction extends TTKAction {
	
	private static Logger log = Logger.getLogger( AccountsManagerAction.class ); // Getting Logger for this Class file

	private static final String strAccntManager="accountsmanager";
	private static final String strGrpRegDetails="groupdetails";
	private static final String strForward="Forward";
	private static final String strBackward="Backward";
	
	private static final String strAccountsManager="AccountsManager";
	
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
			log.debug("Inside AccountsManagerAction doDefault");
			DynaActionForm frmAccntManager=(DynaActionForm)form;
			TableData tableData =TTKCommon.getTableData(request);
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
											request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("AccountsManagerTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			frmAccntManager.set("officeInfo",strDefaultBranchID);
			return this.getForward(strAccntManager, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAccountsManager));
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
			log.debug("Inside AccountsManagerAction doSearch");
			setLinks(request);
			TableData tableData=null;
			GroupRegistrationManager groupRegnManagerObject=this.getGroupRegnManagerObject();
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			if((request.getSession()).getAttribute("tableData") == null)
			{
				tableData = new TableData();
			}//end of if((request.getSession()).getAttribute("tableData") == null)
			else
			{
				tableData = (TableData)(request.getSession()).getAttribute("tableData");
			}//end of else
			
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strAccntManager));
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
				tableData.createTableInfo("AccountsManagerTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			//Populating Data Which matches the search criteria
			ArrayList alUserList=groupRegnManagerObject.selectAccountManager(tableData.getSearchData());
			tableData.setData(alUserList,"search");
			
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strAccntManager, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAccountsManager));
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
			log.debug("Inside AccountsManagerAction doForward");
			setLinks(request);
			TableData tableData=null;
			GroupRegistrationManager groupRegnManagerObject=this.getGroupRegnManagerObject();
			if((request.getSession()).getAttribute("tableData") == null)
			{
				tableData = new TableData();
			}//end of if((request.getSession()).getAttribute("tableData") == null)
			else
			{
				tableData = (TableData)(request.getSession()).getAttribute("tableData");
			}//end of else
			tableData.modifySearchData(strForward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			ArrayList alUserList=groupRegnManagerObject.selectAccountManager(tableData.getSearchData());
			tableData.setData(alUserList,strForward);
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strAccntManager, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAccountsManager));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				//HttpServletResponse response)


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
			log.debug("Inside AccountsManagerAction doBackward");
			setLinks(request);
			TableData tableData=null;
			GroupRegistrationManager groupRegnManagerObject=this.getGroupRegnManagerObject();
			if((request.getSession()).getAttribute("tableData") == null)
			{
				tableData = new TableData();
			}//end of if((request.getSession()).getAttribute("tableData") == null)
			else
			{
				tableData = (TableData)(request.getSession()).getAttribute("tableData");
			}//end of else
			tableData.modifySearchData(strBackward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			ArrayList alUserList=groupRegnManagerObject.selectAccountManager(tableData.getSearchData());
			tableData.setData(alUserList,strBackward);
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strAccntManager, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAccountsManager));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside AccountsManagerAction doClose");
			setLinks(request);
			request.getSession().removeAttribute("tableData");
			return mapping.findForward("accountsmanagerclose");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAccountsManager));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to Select Accounts Manager.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectAccountsManager(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside AccountsManagerAction doSelectAccountsManager ");
			TableData tableData = null;
			if((request.getSession()).getAttribute("tableData") != null){
				tableData = (TableData)(request.getSession()).getAttribute("tableData");
			}//end of if((request.getSession()).getAttribute("hospitalListData") != null)
			else{
				tableData = new TableData();
			}//end of else
			UserListVO userListVO = null;
			DynaActionForm frmGroupDetail=(DynaActionForm)request.getSession().getAttribute("frmGroupDetail");
			//on click of edit in hospital search screen
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				//get the selected hospital info
				userListVO = (UserListVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				//when Pre-Auth Request comes
				frmGroupDetail.set("accntManagerName",userListVO.getUserName());
				frmGroupDetail.set("accntManagerSeqID",userListVO.getContactSeqId().toString());
				frmGroupDetail.set("frmChanged","changed");
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			request.getSession().removeAttribute("tableData");
			request.getSession().removeAttribute("frmAccntManager");
			return this.getForward(strGrpRegDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsManager));
		}//end of catch(Exception exp)
	}//end of doSelectAccountsManager(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
     * Returns the GroupRegistrationManager session object for invoking methods on it.
     * @return GroupRegistrationManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private GroupRegistrationManager getGroupRegnManagerObject() throws TTKException
    {
    	GroupRegistrationManager groupRegnManager = null;
    	try
        {
            if(groupRegnManager == null)
            {
                InitialContext ctx = new InitialContext();
                groupRegnManager = (GroupRegistrationManager) ctx.lookup("java:global/TTKServices/business.ejb3/GroupRegistrationManagerBean!com.ttk.business.empanelment.GroupRegistrationManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strAccountsManager);
        }//end of catch
        return groupRegnManager;
    }//end getGroupRegnManagerObject()
	
    /**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmAccntManager formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmAccntManager,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmAccntManager.getString("sName")));
		alSearchParams.add((String)frmAccntManager.getString("userRoles"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmAccntManager.getString("sEmpNo")));
		alSearchParams.add((String)frmAccntManager.getString("officeInfo"));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmProductList)
}//end of AccountsManagerAction
