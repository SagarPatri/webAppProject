/**
 * @ (#) AssociateUserAction.java Dec 28, 2005
 * Project       : TTK HealthCare Services
 * File          : AssociateUserAction.java
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Dec 28, 2005
 * @author       : Bhaskar Sandra
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
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.usermanagement.UserManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.security.GroupVO;
import com.ttk.dto.usermanagement.UserListVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This class is used for searching the Associated/Un-associated Users for the selected Group.
 * This class also provides option of associating/un-associating user's to selected group.
 */

public class AssociateUserAction extends TTKAction {

	private static Logger log = Logger.getLogger( AssociateUserAction.class ); // Getting Logger for this Class file

	private static final String strAssociateUserList="associateuserlist";
	private static final String strForward="Forward";
	private static final String strBackward="Backward";
	private static final String strAssociate="doAssociate";
	private static final String strUGA="UGA";

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
			log.debug("Inside AssociateUserAction doSearch");
			setLinks(request);
			DynaActionForm frmAssociateUser=(DynaActionForm)form;
			TableData associateUserData=null;
			UserManager userManagerObject=this.getUserManagerObject();
			String strDefaultBranchID ="";
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
				((DynaActionForm)form).initialize(mapping);//reset the form data
			
			if((request.getSession()).getAttribute("associateUserData") == null)
			{
				associateUserData = new TableData();
				strDefaultBranchID= String.valueOf(((UserSecurityProfile)request.getSession().
						getAttribute("UserSecurityProfile")).getBranchID());
				frmAssociateUser.set("officeInfo",strDefaultBranchID);
			}
			else
			{
				associateUserData = (TableData)(request.getSession()).getAttribute("associateUserData");
			}
			
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					associateUserData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(
																		request.getParameter("pageId"))));
					return (mapping.findForward(strAssociateUserList));
				}///end of if(!strPageID.equals(""))
				else
				{
					associateUserData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					associateUserData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				//mask the unwanted columns
				associateUserData.createTableInfo("UserListTable",null);
				((Column)((ArrayList)associateUserData.getTitle()).get(1)).setVisibility(false);
				((Column)((ArrayList)associateUserData.getTitle()).get(6)).setVisibility(false);
				((Column)((ArrayList)associateUserData.getTitle()).get(7)).setVisibility(false);
				((Column)((ArrayList)associateUserData.getTitle()).get(8)).setVisibility(false);
				((Column)((ArrayList)associateUserData.getTitle()).get(9)).setVisibility(false);
				((Column)((ArrayList)associateUserData.getTitle()).get(10)).setVisibility(false);
				((Column)((ArrayList)associateUserData.getTitle()).get(11)).setVisibility(false);
				((Column)((ArrayList)associateUserData.getTitle()).get(12)).setVisibility(false);
				//kocbroker
				((Column)((ArrayList)associateUserData.getTitle()).get(13)).setVisibility(false);
				((Column)((ArrayList)associateUserData.getTitle()).get(14)).setVisibility(false);
				associateUserData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				associateUserData.modifySearchData("search");
			}//end of else
			//Populating Data Which matches the search criteria
			ArrayList alUserList=userManagerObject.groupRegistrationUserList(associateUserData.getSearchData());
			if(frmAssociateUser.get("associateUsers").equals(""))
			{
				frmAssociateUser.set("associateUsers",strUGA);
			}//end of if(frmAssociateUser.get("associateUsers").equals(""))
			associateUserData.setData(alUserList,"search");
			
			//set the table data object to session
			request.getSession().setAttribute("associateUserData",associateUserData);
			//finally return to the grid screen
			return this.getForward(strAssociateUserList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"associateuser"));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to associate the users to a group.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAssociate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside AssociateUserAction doAssociate");
			setLinks(request);
			TableData associateUserData=null;
			if((request.getSession()).getAttribute("associateUserData") == null)
			{
				associateUserData = new TableData();
			}
			else
			{
				associateUserData = (TableData)(request.getSession()).getAttribute("associateUserData");
			}
			UserManager userManagerObject=this.getUserManagerObject();
			DynaActionForm frmUserList=(DynaActionForm)form;
			String strConcatenatedSeqID= this.getConcatenatedSeqID(request,
												(TableData)request.getSession().getAttribute("associateUserData"));
			int iCount=0;
			iCount=userManagerObject.updateGroupRegistration(strConcatenatedSeqID,
					new Long((String)frmUserList.get("grpBranchSeqId")),TTKCommon.getUserSeqId(request));
			ArrayList alUserList=null;
		    //fetch the data from previous set of rowcounts, if all records are deleted for the current set of rowcounts
			if(iCount == associateUserData.getData().size())
			{
				associateUserData.modifySearchData("Delete");//modify the search data
				int iStartRowCount = Integer.parseInt((String)associateUserData.getSearchData()
													.get(associateUserData.getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alUserList=userManagerObject.groupRegistrationUserList(associateUserData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if
			else
			{
				alUserList=userManagerObject.groupRegistrationUserList(associateUserData.getSearchData());
			}
			associateUserData.setData(alUserList,"Delete");
			//set the table data object to session
			request.getSession().setAttribute("associateUserData",associateUserData);
			return this.getForward(strAssociateUserList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"associateuser"));
		}//end of catch(Exception exp)
	}//end of doAssociate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to unassociate the users from a group.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doRemove(ActionMapping mapping,ActionForm form,HttpServletRequest request,
							HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside AssociateUserAction doRemove");
			setLinks(request);
			TableData associateUserData=null;
			if((request.getSession()).getAttribute("associateUserData") == null)
			{
				associateUserData = new TableData();
			}
			else
			{
				associateUserData = (TableData)(request.getSession()).getAttribute("associateUserData");
			}
			UserManager userManagerObject=this.getUserManagerObject();
//			DynaActionForm frmUserList=(DynaActionForm)form;
			String strConcatenatedSeqID= this.getConcatenatedSeqID(request,
										 (TableData)request.getSession().getAttribute("associateUserData"));
			int iCount=0;
			iCount=userManagerObject.removeUsers(strConcatenatedSeqID);

			ArrayList alUserList=null;
			//fetch the data from previous set of rowcounts, if all records are deleted for the current set of rowcounts
			if(iCount == associateUserData.getData().size())
			{
				associateUserData.modifySearchData("Delete");//modify the search data
				int iStartRowCount = Integer.parseInt((String)associateUserData.getSearchData()
										.get(associateUserData.getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alUserList=userManagerObject.groupRegistrationUserList(associateUserData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if
			else
			{
				alUserList=userManagerObject.groupRegistrationUserList(associateUserData.getSearchData());
			}
			associateUserData.setData(alUserList,"Delete");
			//set the table data object to session
			request.getSession().setAttribute("associateUserData",associateUserData);
			return this.getForward(strAssociateUserList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"associateuser"));
		}//end of catch(Exception exp)
	}//end of doRemove(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside AssociateUserAction doForward");
			setLinks(request);
			TableData associateUserData=null;
			UserManager userManagerObject=this.getUserManagerObject();
			if((request.getSession()).getAttribute("associateUserData") == null)
			{
				associateUserData = new TableData();
			}
			else
			{
				associateUserData = (TableData)(request.getSession()).getAttribute("associateUserData");
			}
			associateUserData.modifySearchData(strForward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			ArrayList alUserList = userManagerObject.groupRegistrationUserList(associateUserData.getSearchData());
			associateUserData.setData(alUserList,strForward);
			//set the table data object to session
			request.getSession().setAttribute("associateUserData",associateUserData);
			return this.getForward(strAssociateUserList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"associateuser"));
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
			log.debug("Inside AssociateUserAction doBackward");
			setLinks(request);
			TableData associateUserData=null;
			UserManager userManagerObject=this.getUserManagerObject();
			if((request.getSession()).getAttribute("associateUserData") == null)
			{
				associateUserData = new TableData();
			}
			else
			{
				associateUserData = (TableData)(request.getSession()).getAttribute("associateUserData");
			}
			associateUserData.modifySearchData(strBackward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			ArrayList alUserList = userManagerObject.groupRegistrationUserList(associateUserData.getSearchData());
			associateUserData.setData(alUserList,strBackward);
			//set the table data object to session
			request.getSession().setAttribute("associateUserData",associateUserData);
			return this.getForward(strAssociateUserList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"associateuser"));
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
			log.debug("Inside AssociateUserAction doClose");
			setLinks(request);
			request.getSession().removeAttribute("associateUserData");
			return mapping.findForward("usergroup");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"associateuser"));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**Returns the ArrayList of concatenated string of contact_seq_id delimeted by '|'.
	 * @param HttpServletRequest
	 * @return ArrayList
	 */
	private String getConcatenatedSeqID(HttpServletRequest request,TableData associateUserData) {
		StringBuffer sbfConcatenatedSeqId=new StringBuffer("|");
		String strChOpt[]=request.getParameterValues("chkopt");
		if((strChOpt!=null)&& strChOpt.length!=0)
		{
			for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
			{
				if(strChOpt[iCounter]!=null)
				{
					if(iCounter==0)
					{
						if(request.getParameter("mode").equals(strAssociate))
						{
							sbfConcatenatedSeqId.append(String.valueOf(((UserListVO)associateUserData.getRowInfo(
									Integer.parseInt(strChOpt[iCounter]))).getContactSeqId()));
						}//end of if(request.getParameter("mode").equals(strAssociate))
						else
						{
							sbfConcatenatedSeqId.append(String.valueOf(((UserListVO)associateUserData.getRowInfo(
									Integer.parseInt(strChOpt[iCounter]))).getGroupUserSeqID()));
						} //end of else
					}//end of if(iCounter==0)
					else
					{
						if(request.getParameter("mode").equals(strAssociate))
						{
							sbfConcatenatedSeqId.append("|").append(String.valueOf(((UserListVO)associateUserData.
									getRowInfo(Integer.parseInt(strChOpt[iCounter]))).getContactSeqId()));
						} //end of if(request.getParameter("mode").equals(strAssociate))
						else
						{
							sbfConcatenatedSeqId.append("|").append(String.valueOf(((UserListVO)associateUserData.
									getRowInfo(Integer.parseInt(strChOpt[iCounter]))).getGroupUserSeqID()));
						}//end of else
					}//end of else
				} // end of if if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		sbfConcatenatedSeqId.append("|");
		return sbfConcatenatedSeqId.toString();
	} // end of getUsers(HttpServletRequest request)

	/**
	 * Returns the UserManager session object for invoking methods on it.
	 * @return UserManager session object which can be used for method invokation
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
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strAssociateUserList);
		}//end of catch
		return userManager;
	}//end getUserManagerObject()

	/**
	 * Return the ArrayList populated with Search criteria elements
	 * @param DynaActionForm
	 * @param HttpServletRequest
	 * @return ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmUserList,HttpServletRequest request)
	{
		ArrayList <Object> alSearchParams =new ArrayList<Object>();
		String strAssociateUserType="";
		GroupVO groupVO=null;
		groupVO=(GroupVO)request.getAttribute("GroupVO");
		if(groupVO!=null)
		{
			frmUserList.set("grpBranchSeqId",groupVO.getGroupBranchSeqID().toString());
			frmUserList.set("grpName",groupVO.getGroupName());
		}
		if(((String)frmUserList.get("associateUsers")).equals(strUGA)||
				((String)frmUserList.get("associateUsers")).equals(""))
		{
			strAssociateUserType="Y";
		}
		else
		{
			strAssociateUserType="N";
		}
		alSearchParams.add(new SearchCriteria("GROUP_SEQ_ID",(String)frmUserList.get("grpBranchSeqId")));
		alSearchParams.add(new SearchCriteria("ASSOC_TYPE",strAssociateUserType ,"equals"));
		alSearchParams.add(new SearchCriteria("CONTACT_NAME",
				TTKCommon.replaceSingleQots((String)frmUserList.get("sName"))));
		alSearchParams.add(new SearchCriteria("D.ROLE_SEQ_ID", (String)frmUserList.get("userRoles"),"equals"));
		alSearchParams.add(new SearchCriteria("EMPLOYEE_NUMBER",
				TTKCommon.replaceSingleQots((String)frmUserList.get("sEmpNo")),"equals"));
		alSearchParams.add(new SearchCriteria("B.TPA_OFFICE_SEQ_ID", (String)frmUserList.get("officeInfo"),"equals"));
		//request.getSession().setAttribute("associatesearchparam",alSearchParams);
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmUserList,HttpServletRequest request)
}//end of AssociateUserAction