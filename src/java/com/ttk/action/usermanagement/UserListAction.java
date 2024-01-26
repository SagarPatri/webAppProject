/**
 * @ (#) UserListAction.java Dec 28, 2005
 * Project 		: TTK HealthCare Services
 * File 		: UserListAction.java
 * Author 		: Pradeep R
 * Company 		: Span Systems Corporation
 * Date Created : Dec 28, 2005
 *
 * @author 		: Pradeep R
 * Modified by 	:
 * Modified date:
 * Reason 		:
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
import com.ttk.common.security.Cache;
import com.ttk.dto.finance.AuthorisedVO;
import com.ttk.dto.usermanagement.UserListVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This class is reusable for searching of user in User Management, Bank Account in Administration and Finance flows.
 * This class also provides option for activate/inactivate user in the User Management flow.
 */

public class UserListAction extends TTKAction
{
	private static Logger log = Logger.getLogger( UserListAction.class );
	private static final String strForward="Forward";
	private static final String strBackward="Backward";
	private static final String strUserList="userlist";
	private static final String strAuthSignatories="authsignatories";
	private static final String strDeleteList="DeleteList";
	private static final String strHospital="HOS";
	private static final String strPartner="PTR";
	private static final String strActivate="ACT";
	private static final String strInsurance="INS";
	private static final String strAgent="AGN";
	private static final String strTTKUser="TTK";
	private static final String strBROUser="BRO";
	private static final String strDMCUser="DMC";
	private static final String strCallCenter="CAL";
	private static final String strCorporate="COR";

	//Changes Added for Password Policy CR KOC 1235
	private static final String strUserConfig="userconfiguration";
	//End changes for Password Policy CR KOC 1235

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
			log.debug("Inside UserListAction doDefault");
			setLinks(request);

			DynaActionForm frmUserList= (DynaActionForm) form;
			String strLink=TTKCommon.getActiveLink(request);
			if(strLink.equals("Finance"))
			{
				if(TTKCommon.getWebBoardId(request) == null)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.accountno.required");
					throw expTTK;
				}//end of if(TTKCommon.getWebBoardId(request) == null)
			}//end of if(strLink.equals("Finance"))
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
															getAttribute("UserSecurityProfile")).getBranchID());
			TableData tableData =TTKCommon.getTableData(request);
			String strUserType="";
			tableData = new TableData();										//create new table data object
			// When the user comes to this screen for the first time, set the value for userType AND status
			frmUserList.initialize(mapping);
			frmUserList.set("sUserList",strTTKUser);
			frmUserList.set("sUserStatus",strActivate);
			tableData.createTableInfo("UserListTable",new ArrayList());	 		//create the required grid table
			strUserType=(String)frmUserList.get("sUserList");
			//setting the corresponding cache object to the listRole based on the user type
			frmUserList.set("listRole",Cache.getCacheObject(strUserType));
			//This method is used to set the selected colum's visibility to false.
			this.setColumnVisiblity(tableData,strUserType);
			request.getSession().setAttribute("tableData",tableData);
			frmUserList.set("sTTKBranch",strDefaultBranchID);
			if(strLink.equals("Administration"))
			{
				frmUserList.set("sublink","ADM");
				return this.getForward(strUserList, mapping, request);
			}//end of if(strLink.equals("Administration"))
			if(strLink.equals("Finance"))
			{
				frmUserList.set("sublink","FIN");
				//This is used to  avoid the displayVidal Health TPA Branch colum in the 2nd row of search table
				frmUserList.set("sUserList","FIN");
				return this.getForward(strAuthSignatories, mapping, request);
			}//end of if(strLink.equals("Finance"))
			return this.getForward(strAuthSignatories, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strUserList));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to load the user information based on the selected user type.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeUserType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
											HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside UserListAction doChangeUserType");
			setLinks(request);
			TableData tableData  = new TableData();
			tableData.createTableInfo("UserListTable",null);
			request.getSession().setAttribute("tableData",tableData);			//set the tabledata to session
			DynaActionForm frmUserList= (DynaActionForm) form;
			String strUserType=(String)frmUserList.get("sUserList");
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
														getAttribute("UserSecurityProfile")).getBranchID());
			frmUserList.initialize(mapping);
			frmUserList.set("sublink","ADM");
			frmUserList.set("sUserList",strUserType);
			frmUserList.set("sTTKBranch",strDefaultBranchID);
			//set the value of selected user type after reseting.
			frmUserList.set("listRole",Cache.getCacheObject(strUserType));
			frmUserList.set("sUserStatus",strActivate);
			//This method is used to set the selected colum's visibility to false.
			this.setColumnVisiblity(tableData,strUserType);
			return this.getForward(strUserList,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strUserList));
		}//end of catch(Exception exp)
	}//end of doChangeUserType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
			log.debug("Inside UserListAction doSearch");
			setLinks(request);
			String strForwards="";
			ArrayList alUserList=null;
			String strUserType="";
			UserManager userManagerObject = this.getUserManagerObject();
			DynaActionForm frmUserList= (DynaActionForm) form;
			strUserType=(String)frmUserList.get("sUserList");
			String strLink=TTKCommon.getActiveLink(request);
			TableData tableData =TTKCommon.getTableData(request);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(strLink.equals("Finance"))
			{
				strUserType=strTTKUser;
				frmUserList.set("sublink","FIN");
				//This is used to  avoid the displayVidal Health TPA Branch colum in the 2nd row of search table
				frmUserList.set("sUserList","FIN");
			}
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strUserList));
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
				tableData.createTableInfo("UserListTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,strLink));
				this.setColumnVisiblity(tableData,strUserType);
				tableData.modifySearchData("search");
			}//end of else

			if(strLink.equals("Finance"))
			{
				alUserList = userManagerObject.getSignatoryList(tableData.getSearchData());
			}//End of if(strLink.equals("Finance"))
			else
			{
				alUserList = userManagerObject.getUserList(tableData.getSearchData(),"");
			}
			tableData.setData(alUserList,"search");
			//add the search parameters to session
			//ArrayList<String> alSearchData = new ArrayList<String>();
			request.getSession().setAttribute("tableData",tableData);	//set the table data object to session
			if(strLink.equals("Administration"))
			{
				frmUserList.set("sublink","ADM");
				strForwards=strUserList;
			}//end of if(strLink.equals("Administration"))
			if(strLink.equals("Finance"))
			{
				strForwards=strAuthSignatories;
			}//end of if(strLink.equals("Finance"))
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strUserList));
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
			log.debug("Inside UserListAction doForward");
			setLinks(request);
			String strForwards="";
			ArrayList alUserList=null;
			DynaActionForm frmUserList= (DynaActionForm) form;
			TableData tableData =TTKCommon.getTableData(request);
			String strLink=TTKCommon.getActiveLink(request);
			UserManager userManagerObject = this.getUserManagerObject();
			tableData.modifySearchData(strForward);
			if(strLink.equals("Finance"))
			{
				alUserList = userManagerObject.getSignatoryList(tableData.getSearchData());
				frmUserList.set("sublink","FIN");
				frmUserList.set("sUserList","FIN");
				strForwards=strAuthSignatories;
			}//End of if(strLink.equals("Finance"))
			else
			{
				alUserList = userManagerObject.getUserList(tableData.getSearchData(),"");
				frmUserList.set("sublink","ADM");
				strForwards=strUserList;
			}
			tableData.setData(alUserList,strForward);
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strUserList));
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
			log.debug("Inside UserListAction doBackward");
			setLinks(request);
			String strForwards="";
			TableData tableData =TTKCommon.getTableData(request);
			DynaActionForm frmUserList= (DynaActionForm) form;
			String strLink=TTKCommon.getActiveLink(request);
			UserManager userManagerObject = this.getUserManagerObject();
			ArrayList alUserList=null;
			tableData.modifySearchData(strBackward);
			if(strLink.equals("Finance"))
			{
				alUserList = userManagerObject.getSignatoryList(tableData.getSearchData());
				frmUserList.set("sublink","FIN");
				frmUserList.set("sUserList","FIN");
				strForwards=strAuthSignatories;
			}//End of if(strLink.equals("Finance"))
			else
			{
				alUserList = userManagerObject.getUserList(tableData.getSearchData(),"");
				frmUserList.set("sublink","ADM");
				strForwards=strUserList;
			}
			tableData.setData(alUserList,strBackward);
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strForwards, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strUserList));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
											HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside UserListAction doChangeWebBoard");
			//call doDefault method as action is similar.
			return doDefault(mapping, form, request, response);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strUserList));
		}//end of catch(Exception exp)
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			log.debug("Inside UserListAction doDeleteList");
			setLinks(request);
			DynaActionForm frmUserList= (DynaActionForm) form;
			TableData tableData =TTKCommon.getTableData(request);
			StringBuffer sbfDeleteId = new StringBuffer();
			//populate the delete string which contains the sequence id's to be deleted
			sbfDeleteId.append(populateDeleteId(request,(TableData)request.getSession().getAttribute("tableData")));
			ArrayList <Object>alParams=new ArrayList<Object>();
			UserManager userManagerObject = this.getUserManagerObject();
			alParams.add(sbfDeleteId.toString());
			alParams.add(TTKCommon.getUserSeqId(request));//User id
			int iCount = userManagerObject.deleteSignatory(alParams);
			log.debug("iCount value is :"+iCount);
			//refresh the grid with search data in session
			ArrayList alSignatoryList = null;
			alSignatoryList = userManagerObject.getSignatoryList(tableData.getSearchData());
			tableData.setData(alSignatoryList, strDeleteList);
			frmUserList.set("sublink","FIN");
			frmUserList.set("sUserList","FIN");
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strAuthSignatories, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"role"));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	/**
	 * This method is used to make the user active/inactive.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doActivateInactivate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside UserListAction doActivateInactivate");
			setLinks(request);
			String strYesNO="";
			String strUserType="";
			UserManager userManagerObject = this.getUserManagerObject();
			TableData tableData =TTKCommon.getTableData(request);
			ArrayList alUserList=null;
			int iStartRowCount=0;
			DynaActionForm frmUserList= (DynaActionForm) form;
			strUserType=(String)frmUserList.get("sUserList");
			if(frmUserList.get("sUserStatus").equals(strActivate))
			{
				strYesNO="N";
			}//end of if(frmUserList.get("sUserStatus").equals(strActivate))
			else
			{
				strYesNO="Y";
			}//end of else
			int iCount=userManagerObject.setUserStatus(populateContactSeqId(request,
												(TableData)request.getSession().getAttribute("tableData")),strYesNO);
			alUserList = userManagerObject.getUserList(tableData.getSearchData(),"");
			if(alUserList.size() == 0 || iCount == tableData.getData().size())
			{
			 tableData.modifySearchData("Delete");
			 iStartRowCount=Integer.parseInt((String)tableData.getSearchData().get(tableData.getSearchData().size()-2));
			 if(iStartRowCount > 0)
			 {
			 	alUserList = userManagerObject.getUserList(tableData.getSearchData(),"");
			 }//end of if(iStartRowCount > 0)
			}//end if(alUserList.size() == 0 || iCount == tableData.getData().size())
			this.setColumnVisiblity(tableData,strUserType);//masking the visibility of table
			tableData.setData(alUserList,"Delete");
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strUserList, mapping, request); 				//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"role"));
		}//end of catch(Exception exp)
	}//end of doActivateInactivate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * Returns a string which contains the comma separated sequence id's to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the pipe separated sequence id's to be deleted
	 * @throws TTKException If any run time Excepton occures
	 */
	private String populateContactSeqId(HttpServletRequest request, TableData tableData)throws TTKException
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfContactSeqId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfContactSeqId.append("|").append(String.valueOf(((UserListVO)tableData.getData().get(
														   Integer.parseInt(strChk[i]))).getContactSeqId().intValue()));
					}//end of if(i == 0)
					else
					{
						sbfContactSeqId = sbfContactSeqId.append("|").append(String.valueOf(((UserListVO)
								tableData.getData().get(Integer.parseInt(strChk[i]))).getContactSeqId().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfContactSeqId=sbfContactSeqId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return sbfContactSeqId.toString();
	}//end of populateContactSeqId(HttpServletRequest request, TableData tableData)throws TTKException

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmUserList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 * @throws TTKException
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmUserList,HttpServletRequest request,String strLink)
										throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		if(strLink.equals("Administration"))
		{
			String strUserType=(String)frmUserList.get("sUserList");
			//building the search parameter's based on the obtained userType.
			alSearchParams.add(strUserType);												// user type
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmUserList.get("sUserId")));							// user id
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmUserList.get("sName")));							//	user name
			alSearchParams.add((String)frmUserList.get("sRoleId"));							// role id
			if(strUserType.equals(strHospital))
			{
				alSearchParams.add((String)frmUserList.get("sHospitalName"));   			//HOSP_NAME
				alSearchParams.add((String)frmUserList.get("sEmpanelmentNO")); 				//EMPANEL_NUMBER
				alSearchParams.add((String)frmUserList.get("sCityCode")); 					//CITY_TYPE_ID
			}//end of if(strUserType.equals(strHospital))
			else
			{
				alSearchParams.add("");
				alSearchParams.add("");
				alSearchParams.add("");
			}//end of else
			if(frmUserList.get("sUserStatus").equals(strActivate))
			{
				alSearchParams.add("Y");
			}//end of if(frmUserList.get("sUserStatus").equals(strActivate))
			else
			{
				alSearchParams.add("N");
			}//end of else
			if ((strUserType.equals(strInsurance))||(strUserType.equals(strAgent)))
			{
				alSearchParams.add((String)frmUserList.get("sInsuranceCompany"));			// Insurance Company:
				alSearchParams.add((String)frmUserList.get("sOfficeCode"));					//Office code
			}//end of ((strUserType.equals(strInsurance))||(strUserType.equals(strAgent)))
			else if (strUserType.equals(strBROUser)){//kocbroker
					alSearchParams.add((String)frmUserList.get("sBrokerCompany"));			// sBrokerCompany:
					alSearchParams.add((String)frmUserList.get("sOfficeCode"));	
				}
			else
			{
				alSearchParams.add("");
				alSearchParams.add("");
			}//end of else
			if((strUserType.equals(strTTKUser))||(strUserType.equals(strCallCenter))||(strUserType.equals(strDMCUser)))
			{
				alSearchParams.add((String)frmUserList.get("sTTKBranch"));					 //Vidal Health TPA Branch
			}//end of if((strUserType.equals(strTTKUser))||(strUserType.equals(strCallCenter)))
			else
			{
				alSearchParams.add("");
			}//end of else
			if (strUserType.equals(strCorporate))
			{
				alSearchParams.add((String)frmUserList.get("sGrpName"));					//Group Name
				alSearchParams.add((String)frmUserList.get("sGrpID"));						//Group Id
			}//end of if (strUserType.equals(strCorporate))
			else
			{
				alSearchParams.add("");
				alSearchParams.add("");
			}//end of else
		}//end of if(strLink.equals("Administration"))
		if(strLink.equals("Finance"))
		{
			alSearchParams.add(TTKCommon.getWebBoardId(request));	// web board value
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmUserList.get("sUserId")));// user id
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmUserList.get("sName")));	//	user name
			alSearchParams.add(TTKCommon.getLong((String)frmUserList.get("sRoleId")));			// role id
			alSearchParams.add(TTKCommon.getLong((String)frmUserList.get("sTTKBranch")));		//Vidal Health TPA Branch
			alSearchParams.add(TTKCommon.getUserSeqId(request));
		}//End of  if(strLink.equals("Finance"))
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmUserList)

	/**
	 * This method is used to set the selected colum's visibility, to false.
	 * @param tableData is the instance of created table
	 * @param strUserType is the variable which contains the selected value from the user type drop down
	 */
	/*private void setColumnVisiblity(TableData tableData,String strUserType)
	{
		// These arrays contains the corresponding column number's whose visibility, is required to make as false.
		int iTTKUser[]={0,4,6,7,8,9,10,11,12,13,14};				// Both for ttk user,call center users and DMC user
		int iNHCPUser[]={0,4,5,9,10,11,12,13,14};
		int iInsuranceUser[]={0,4,5,6,7,8,11,12,13,14};			// Both for insurance user and agent
		int iCorporateUser[]={0,4,5,6,7,8,9,10,13,14};
		int iBrokerUser[]={0,4,5,6,7,8,9,10,11,12};
		int iTemp[]=null;
		if((strUserType.equals(strTTKUser))||(strUserType.equals(strCallCenter))||(strUserType.equals(strDMCUser)) )
		{
			iTemp=iTTKUser;
		}// end of if((strUserType.equals(strTTKUser))||(strUserType.equals(strCallCenter)))
		else if(strUserType.equals(strHospital))
		{
			iTemp=iNHCPUser;
		}// end of else if(strUserType.equals(strHospital))
		else if ((strUserType.equals(strInsurance))||(strUserType.equals(strAgent)) )
		{
			iTemp=iInsuranceUser;
		}// end of else if ((strUserType.equals(strInsurance))||(strUserType.equals(strAgent)))
		else if (strUserType.equals(strCorporate))
		{
			iTemp=iCorporateUser;
		}// end of else if(strUserType.equals(strCorporate))
		else if (strUserType.equals(strBROUser))
		{
			iTemp=iBrokerUser;
		}// end of else if(strUserType.equals(strCorporate))

		for(int i=0;i<iTemp.length;i++)
			((Column)((ArrayList)tableData.getTitle()).get(iTemp[i])).setVisibility(false);
	}// end of private void setColumnVisiblity(TableData tableData,String strUserType)
*/
	private void setColumnVisiblity(TableData tableData,String strUserType)
	{
		// These arrays contains the corresponding column number's whose visibility, is required to make as false.
		int iTTKUser[]={0,4,6,7,8,9,10,11,12,13,14};				// Both for ttk user,call center users and DMC user
		int iNHCPUser[]={0,4,5,9,10,11,12,13,14};
		int iPartnerUser[]={0,4,5,9,10,11,12,13,14};
		int iInsuranceUser[]={0,4,5,6,7,8,11,12,13,14};			// Both for insurance user and agent
		int iCorporateUser[]={0,4,5,6,7,8,9,10,13,14};
		int iBrokerUser[]={0,4,5,6,7,8,9,10,11,12};
		int iTemp[]=null;
		if((strUserType.equals(strTTKUser))||(strUserType.equals(strCallCenter))||(strUserType.equals(strDMCUser)) )
		{
			iTemp=iTTKUser;
		}// end of if((strUserType.equals(strTTKUser))||(strUserType.equals(strCallCenter)))
		else if(strUserType.equals(strHospital))
		{
			iTemp=iNHCPUser;
		}// end of else if(strUserType.equals(strHospital))
		else if(strUserType.equals(strPartner))
		{
			iTemp=iPartnerUser;
		}// end of else if(strUserType.equals(strPartner))
		else if ((strUserType.equals(strInsurance))||(strUserType.equals(strAgent)) )
		{
			iTemp=iInsuranceUser;
		}// end of else if ((strUserType.equals(strInsurance))||(strUserType.equals(strAgent)))
		else if (strUserType.equals(strCorporate))
		{
			iTemp=iCorporateUser;
		}// end of else if(strUserType.equals(strCorporate))
		else if (strUserType.equals(strBROUser))
		{
			iTemp=iBrokerUser;
		}// end of else if(strUserType.equals(strCorporate))

		for(int i=0;i<iTemp.length;i++)
			((Column)((ArrayList)tableData.getTitle()).get(iTemp[i])).setVisibility(false);
	}// end of private void setColumnVisiblity(TableData tableData,String strUserType)

	
	
	
	/**
	 * Returns a string which contains the comma separated sequence id's to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the comma separated sequence id's to be deleted
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
						sbfDeleteId.append("|").append(String.valueOf(((AuthorisedVO)tableData.getData().get(
								Integer.parseInt(strChk[i]))).getAuthSeqID().intValue()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((AuthorisedVO)tableData.getData().
												get(Integer.parseInt(strChk[i]))).getAuthSeqID().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId=sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)

		//Changes Added for Password Policy CR KOC 1235
	/**
	 * This method is used to bring out the Configuration List screen
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doConfiguration method of UserListAction");
			setLinks(request);
			StringBuffer sbfCaption= new StringBuffer();
			DynaActionForm frmUserList = (DynaActionForm)request.getSession().getAttribute("frmUserList");
			request.getSession().setAttribute("ConfigurationTitle", sbfCaption.toString());
			return this.getForward(strUserConfig, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strUserConfig));
		}//end of catch(Exception exp)
	}//end of doConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	//End changes for Password Policy CR KOC 1235

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
			throw new TTKException(exp, strUserList);
		}//end of catch
		return userManager;
	}//end getUserManagerObject()
}// end of class UserListAction