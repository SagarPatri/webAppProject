/**
 * @ (#) PreAuthAction.java Apr 27, 2006
 * Project      : TTK HealthCare Services
 * File         : PreAuthAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Apr 27, 2006
 *
 * @author       :  Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.dataentrypreauth;

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
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This class is used for searching of pre-auth.
 * This class also provides option for deletion of pre-auth.
 */

public class PreAuthAction extends TTKAction
{
	private static Logger log = Logger.getLogger(PreAuthAction.class);
	//declrations of modes
	private static final String strForward="Forward";
	private static final String strBackward="Backward";
	private static final String strDeleteList="DeleteList";
	private static final String strPreauthSearch="preauthsearch";

	//Declarations of constants
	private static final String strRegular="Regular";    //identfier for Regular Pre-Auth
	private static final String strEnhanced="Enhanced";  //identifier for Enhanced Pre-Auth
	private static final String strRegularPreAuth="PAT";
	private static final String strEnhancedPreAuth="ICO";
	private static final int	ASSIGN_ICON=9;

	//Action mapping forwards.
	private static final String strPreauthlist="preauthlist";
	private static final String strPreAuthDetail="preauthdetail";
	
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
			log.debug("Inside PreAuthAction doDefault");
			DynaActionForm frmPreAuthList=(DynaActionForm)form;
			TableData tableData =TTKCommon.getTableData(request);
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
											request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("PreAuthTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			frmPreAuthList.set("sTtkBranch",strDefaultBranchID);
			return this.getForward(strPreauthlist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
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
			log.debug("Inside PreAuthAction doSearch");
			setLinks(request);
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			TableData tableData =TTKCommon.getTableData(request);
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strPreauthlist);
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
				tableData.createTableInfo("PreAuthTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alPreauthList= preAuthObject.getPreAuthList(tableData.getSearchData());
			tableData.setData(alPreauthList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strPreauthlist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
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
			log.debug("Inside PreAuthAction doForward");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			tableData.modifySearchData(strForward);//modify the search data
			ArrayList alPreauthList = preAuthObject.getPreAuthList(tableData.getSearchData());
			tableData.setData(alPreauthList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strPreauthlist, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
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
			log.debug("Inside PreAuthAction doBackward");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alPreauthList = preAuthObject.getPreAuthList(tableData.getSearchData());
			tableData.setData(alPreauthList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strPreauthlist, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
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
			log.debug("Inside PreAuthAction doDeleteList");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			StringBuffer sbfDeleteId = new StringBuffer("|");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			int iCount=0;
			//populate the delete string which contains the sequence id's to be deleted
			sbfDeleteId.append(populateDeleteId(request,(TableData)request.getSession().getAttribute("tableData")));
			ArrayList <Object>alDeleteMode=new ArrayList<Object>();
			alDeleteMode.add(strRegularPreAuth);
			alDeleteMode.add(sbfDeleteId.toString());
			alDeleteMode.add(null);//PAT_ENROLL_DETAIL_SEQ_ID is not required.
			alDeleteMode.add(null);//PAT_GENERAL_DETAIL_SEQ_ID is not required
			alDeleteMode.add(TTKCommon.getUserSeqId(request));//User id
			//delete the selected preauth based on the flow
			iCount = preAuthObject.deletePATGeneral(alDeleteMode);

			//refresh the grid with search data in session
			ArrayList alPreauthList = null;
			//fetch the data from previous set of rowcounts, if all the records are deleted for the current set
			//of search criteria
			if(iCount == tableData.getData().size())
			{
				tableData.modifySearchData(strDeleteList);//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(
																			tableData.getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alPreauthList= preAuthObject.getPreAuthList(tableData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				alPreauthList= preAuthObject.getPreAuthList(tableData.getSearchData());
			}//end of else
			tableData.setData(alPreauthList, strDeleteList);
			if(iCount>0)
			{
				//delete the Pre-Auth from the web board if any
				request.setAttribute("cacheId",sbfDeleteId.append("|").toString());
				PreAuthWebBoardHelper.deleteWebBoardId(request,"SeqId");
			}//end of if(iCount>0)
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strPreauthlist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	/**
	 * This method is used to copy the selected records to web-board.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside PreAuthAction doCopyToWebBoard");
			setLinks(request);
			this.populateWebBoard(request);
			return this.getForward(strPreauthlist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
		}//end of catch(Exception exp)
	}//end of CopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doViewPreauth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside PreAuthAction doViewPreauth");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmPreAuthList=(DynaActionForm)form;
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				PreAuthVO preAuthVO=(PreAuthVO)tableData.getRowInfo(Integer.parseInt(
																			(String)(frmPreAuthList).get("rownum")));
				this.addToWebBoard(preAuthVO, request,strRegular);
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward(strPreAuthDetail);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
		}//end of catch(Exception exp)
	}//end of doViewPreauth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doViewEnhancedPreauth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside PreAuthAction doViewEnhancedPreauth");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmPreAuthList=(DynaActionForm)form;
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				PreAuthVO preAuthVO=(PreAuthVO)tableData.getRowInfo(Integer.parseInt((String)
																			(frmPreAuthList).get("rownum")));
				this.addToWebBoard(preAuthVO, request,strEnhanced);
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward(strPreAuthDetail);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthSearch));
		}//end of catch(Exception exp)
	}//end of doViewEnhancedPreauth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPreAuthList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmPreAuthList,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sPreAuthNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sHospitalName")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sEnrollmentId")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sClaimantName")));
		alSearchParams.add((String)frmPreAuthList.getString("sRecievedDate"));
		alSearchParams.add((String)frmPreAuthList.getString("sTtkBranch"));
		alSearchParams.add((String)frmPreAuthList.getString("sAssignedTo"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sSpecifyName")));
		alSearchParams.add((String)frmPreAuthList.getString("sAmount"));
		alSearchParams.add((String)frmPreAuthList.getString("sSource"));
		alSearchParams.add((String)frmPreAuthList.getString("sStatus"));
		alSearchParams.add((String)frmPreAuthList.getString("sWorkFlow"));
		alSearchParams.add((String)frmPreAuthList.getString("sPreAuthType"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sPolicyNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sEmployeeNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sCorporateName")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		alSearchParams.add(TTKCommon.getUserGroupList(request));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sSchemeName")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sCertificateNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sInsurerAppStatus")));//bajaj
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmProductList)

	/**
	 * This method supresess the Assign Icon Column by checking against the Assign and Special Permissions
	 * from the User's Role.
	 * @param tableData TableData object which contains the Grid Information
	 * @param frmPreAuthList formbean which contains the search fields
	 * @param request HttpServletRequest object
	 * @throws TTKException if any run time exception occures
	 */
	private void setColumnVisiblity(TableData tableData,DynaActionForm frmPreAuthList,HttpServletRequest request)
																									throws TTKException
	{
		String strAssignTo=frmPreAuthList.getString("sAssignedTo");
		boolean blnVisibility=false;
		if(strAssignTo.equals("SLF") && TTKCommon.isAuthorized(request,"Assign"))//For Self Check the Assign Permission
		{
			blnVisibility=true;
		}//end of if(strAssignTo.equals("SLF") && TTKCommon.isAuthorized(request,"Assign"))
		else        //Check for the special Permission to show ICON for Others and Unassigned Pre-auth
		{
			if(TTKCommon.isAuthorized(request,"AssignAll"))
			{
				blnVisibility=true;
			}//end of if(TTKCommon.isAuthorized(request,"AssignAll"))
		}//end of else
		((Column)((ArrayList)tableData.getTitle()).get(ASSIGN_ICON)).setVisibility(blnVisibility);
	}//end of setColumnVisiblity(TableData tableData,DynaActionForm frmPreAuthList,HttpServletRequest request)

	/**
	 * This method returns a string which contains the comma separated sequence id's to be deleted,
	 * in Enrollmemt flow pipe seperated policy seq ids and in endorsement flow pipe seperated endorsement seq ids
	 * are sent to the called method
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param policyListData TableData object which contains the value objects
	 * @return String which contains the comma separated sequence id's to be deleted
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableData)
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			//loop through to populate delete sequence id's and get the value from session for the matching
			//check box value
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append(String.valueOf(((PreAuthVO)tableData.getData().get(
																	Integer.parseInt(strChk[i]))).getPreAuthSeqID()));
					}// end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((PreAuthVO)
											tableData.getData().get(Integer.parseInt(strChk[i]))).getPreAuthSeqID()));
					}// end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		log.debug("DELETE IDS !!!!!!! "+sbfDeleteId);
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)

	/**
	 * Returns the PreAuthManager session object for invoking methods on it.
	 * @return PreAuthManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthManager getPreAuthManagerObject() throws TTKException
	{
		PreAuthManager preAuthManager = null;
		try
		{
			if(preAuthManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthManager = (PreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthManagerBean!com.ttk.business.preauth.PreAuthManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strPreauthSearch);
		}//end of catch
		return preAuthManager;
	}//end getPreAuthManagerObject()

	/**
	 * Populates the web board in the session with the selected items in the grid
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @throws TTKException If any run time Excepton occures
	 */
	private void populateWebBoard(HttpServletRequest request)throws TTKException
	{
		String[] strChk = request.getParameterValues("chkopt");
		TableData tableData = (TableData)request.getSession().getAttribute("tableData");
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		CacheObject cacheObject = null;
		PreAuthVO preAuthVO=null;

		if(strChk!=null&&strChk.length!=0)
		{
			for(int i=0; i<strChk.length;i++)
			{
				cacheObject = new CacheObject();
				preAuthVO=(PreAuthVO)tableData.getData().get(Integer.parseInt(strChk[i]));
				cacheObject.setCacheId(this.prepareWebBoardId(preAuthVO,strRegular));
				cacheObject.setCacheDesc(preAuthVO.getPreAuthNo());
				alCacheObject.add(cacheObject);
			}//end of for(int i=0; i<strChk.length;i++)
		}//end of if(strChk!=null&&strChk.length!=0)
		if(toolbar != null)
		{
			toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		}//end of if(toolbar != null)
	}//end of populateWebBoard(HttpServletRequest request)

	/**
	 * Adds the selected item to the web board and makes it as the selected item in the web board
	 * @param  preauthVO  object which contains the information of the preauth
	 * * @param String  strIdentifier whether it is preauth or enhanced preauth
	 * @param request HttpServletRequest
	 * @throws TTKException if any runtime exception occures
	 */
	private void addToWebBoard(PreAuthVO preAuthVO, HttpServletRequest request,String strIdentifier)throws TTKException
	{
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		CacheObject cacheObject = new CacheObject();
		cacheObject.setCacheId(this.prepareWebBoardId(preAuthVO,strIdentifier)); //set the cacheID
		cacheObject.setCacheDesc(preAuthVO.getPreAuthNo());
		alCacheObject.add(cacheObject);
		//if the object(s) are added to the web board, set the current web board id
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());

		//webboardinvoked attribute will be set as true in request scope
		//to avoid the replacement of web board id with old value if it is called twice in same request scope
		request.setAttribute("webboardinvoked", "true");
	}//end of addToWebBoard(PreAuthVO preAuthVO, HttpServletRequest request,String strIdentifier)throws TTKException

	/**
	 * This method prepares the Weboard id for the selected Policy
	 * @param preAuthVO  preAuthVO for which webboard id to be prepared
	 * * @param String  strIdentifier whether it is preauth or enhanced preauth
	 * @return Web board id for the passedVO
	 */
	private String prepareWebBoardId(PreAuthVO preAuthVO,String strIdentifier)throws TTKException
	{
		StringBuffer sbfCacheId=new StringBuffer();
		sbfCacheId.append(preAuthVO.getPreAuthSeqID()!=null? String.valueOf(preAuthVO.getPreAuthSeqID()):" ");
		sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getEnrollmentID()).equals("")? " ":preAuthVO.getEnrollmentID());
		sbfCacheId.append("~#~").append(preAuthVO.getEnrollDtlSeqID()!=null? String.valueOf(preAuthVO.getEnrollDtlSeqID()):" ");
		sbfCacheId.append("~#~").append(preAuthVO.getPolicySeqID()!=null? String.valueOf(preAuthVO.getPolicySeqID()):" ");
		sbfCacheId.append("~#~").append(preAuthVO.getMemberSeqID()!=null? String.valueOf(preAuthVO.getMemberSeqID()):" ");
		if(strIdentifier.equals(strRegular))// to check it is a regular pre auth
		{
			sbfCacheId.append("~#~").append(strRegularPreAuth);//if it is a reular preauth then append with string identifier
		}//end of if(strIdentifier.equals(strRegular))
		else if(strIdentifier.equals(strEnhanced))//to check it is a enhanced preauth
		{
			if(preAuthVO.getEnhanceIconYN().equals("Y"))// to check whethere there is enhanced icon
			{
				sbfCacheId.append("~#~").append(strEnhancedPreAuth);
			}//end of if(preAuthVO.getEnhanceIconYN().equals("Y"))
			else
			{
				sbfCacheId.append("~#~").append(strRegularPreAuth);
			}//end of else
		}// end of else if(strIdentifier.equals(strEnhanced))
		sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getClaimantName()).equals("")? " ":preAuthVO.getClaimantName());
		sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getBufferAllowedYN()).equals("")? " ":preAuthVO.getBufferAllowedYN());
		sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getShowBandYN()).equals("")? " ":preAuthVO.getShowBandYN());
		sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getCoding_review_yn()).equals("")? " ":preAuthVO.getCoding_review_yn());
		return sbfCacheId.toString();
	}//end of prepareWebBoardId(PreAuthVO preAuthVO,String strIdentifier)throws TTKException
}//end of PreAuthAction