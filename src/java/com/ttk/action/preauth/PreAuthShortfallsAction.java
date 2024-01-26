/**
 * @ (#) PreAuthShortfallsAction.java July 7, 2015
 * Project 	     : ProjectX
 * File          : PreAuthShortfallsAction.java
 * Author        : Nagababu K
 * Company       : RCS Technologies
 * Date Created  : July 7, 2015
 *
 * @author       :  Nagababu K
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.action.preauth;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.claims.ClaimManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.business.preauth.PreAuthSupportManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.preauth.ActivityDetailsVO;
import com.ttk.dto.preauth.ClinicianDetailsVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.preauth.ProviderDetailsVO;
import com.ttk.dto.preauth.ShortfallVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of Claims
 */

public class PreAuthShortfallsAction extends TTKAction
{
	private static Logger log = Logger.getLogger(PreAuthShortfallsAction.class);

	//declrations of modes
	private static final String strForward="Forward";
	private static final String strBackward="Backward";

	//Action mapping forwards.
	private static final  String strPreAuthShortfallDetails="preauthshortfalldetails";

    //Exception Message Identifier
    private static final String strClaimSearchError="hospitalsearch";
    
    private static final String strPreauthShortFall="preauthshortfall";
    private static final String strSupportError="support";
	/**

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
		log.debug("Inside PreAuthShortfallsAction doDefault");
		TableData tableData =TTKCommon.getTableData(request);
		//create the required grid table
		tableData.createTableInfo("PreAuthShortfallsTable",new ArrayList());
		request.getSession().setAttribute("tableData",tableData);
		((DynaActionForm)form).initialize(mapping);//reset the form data			
		return this.getForward(strPreauthShortFall, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp,strPreauthShortFall));
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
			log.debug("Inside PreAuthShortfallsAction doSearch");
			setLinks(request);
			HttpSession session=request.getSession();
			//PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			TableData tableData =session.getAttribute("tableData")==null?new TableData():(TableData)session.getAttribute("tableData");
						
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strPreauthShortFall);
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
				tableData.createTableInfo("PreAuthShortfallsTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				//this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
				//tableData.setSortColumnName("SHORTFALL_ID");
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alShortfallList= preAuthSupportManagerObject.getShortfallsList(tableData.getSearchData());
			tableData.setData(alShortfallList, "search");
			//set the table data object to session
			session.setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strPreauthShortFall, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthShortFall));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPreAuthList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmClaimList,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimList.getString("sShortfallNO")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimList.getString("sPreAuthNO")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimList.getString("sStatus")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimList.getString("sPolicyNumber")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimList.getString("sEnrollmentId")));
		alSearchParams.add(TTKCommon.replaceSingleQots("PAT"));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmProductList)

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
    		log.debug("Inside the doBackward method of PreAuthShortfallsAction");
    		setLinks(request);
    		PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
    		tableData.modifySearchData(strBackward);//modify the search data
    		ArrayList alShortfallList= preAuthSupportManagerObject.getShortfallsList(tableData.getSearchData());
			tableData.setData(alShortfallList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strPreauthShortFall, mapping, request);   //finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthShortFall));
		}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside the doForward method of PreAuthShortfallsAction");
    		setLinks(request);
    		PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
    		tableData.modifySearchData(strForward);//modify the search data
    		ArrayList alShortfallList= preAuthSupportManagerObject.getShortfallsList(tableData.getSearchData());
			tableData.setData(alShortfallList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strPreauthShortFall, mapping, request);   //finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthShortFall));
		}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside the doCopyToWebBoard method of PreAuthShortfallsAction");
    		setLinks(request);
    		request.getSession().setAttribute("closeShortfalls", "goSearch");
    		this.populateWebBoard(request);
			return this.getForward(strPreauthShortFall, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthShortFall));
		}//end of catch(Exception exp)
    }//end of doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doView method of PreAuthShortfallsAction");
    		setLinks(request);
    		//get the tbale data from session if exists
    		HttpSession session=request.getSession();
    		//get the tbale data from session if exists
			TableData tableData =(TableData)session.getAttribute("tableData");//TTKCommon.getTableData(request);
			
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				ShortfallVO shortfallVO=(ShortfallVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				//session.setAttribute("searchPreAuthShortfallVO", shortfallVO);
				session.setAttribute("closeShortfalls", "goSearch");
				this.addToWebBoard(shortfallVO, request);
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward(strPreAuthShortfallDetails);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreauthShortFall));
		}//end of catch(Exception exp)
    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * Returns the PreAuthManager session object for invoking methods on it.
     * @return PreAuthManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private ClaimManager getClaimManagerObject() throws TTKException
    {
    	ClaimManager claimManager = null;
    	try
    	{
    		if(claimManager == null)
    		{
    			InitialContext ctx = new InitialContext();
    			claimManager = (ClaimManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimManagerBean!com.ttk.business.claims.ClaimManager");
    		}//end if
    	}//end of try
    	catch(Exception exp)
    	{
    		throw new TTKException(exp, strClaimSearchError);
    	}//end of catch
    	return claimManager;
    }//end getClaimManagerObject()
    
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
			throw new TTKException(exp, strClaimSearchError);
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
    	ShortfallVO shortfallVO=null;

    	if(strChk!=null&&strChk.length!=0)
    	{
    		for(int i=0; i<strChk.length;i++)
    		{
    			cacheObject = new CacheObject();
    			shortfallVO=(ShortfallVO)tableData.getData().get(Integer.parseInt(strChk[i]));
    			cacheObject.setCacheId(this.prepareWebBoardId(shortfallVO));
    			cacheObject.setCacheDesc(shortfallVO.getShortfallNo());
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
    private void addToWebBoard(ShortfallVO shortfallVO, HttpServletRequest request)throws TTKException
    {
    	Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
    	ArrayList<Object> alCacheObject = new ArrayList<Object>();
    	CacheObject cacheObject = new CacheObject();
    	cacheObject.setCacheId(this.prepareWebBoardId(shortfallVO)); //set the cacheID
    	cacheObject.setCacheDesc(shortfallVO.getShortfallNo());
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
    private String prepareWebBoardId(ShortfallVO shortfallVO)throws TTKException
    {
    	StringBuffer sbfCacheId=new StringBuffer();
    	String shortfallSeqID=shortfallVO.getShortfallSeqID()==null?" ":shortfallVO.getShortfallSeqID().toString();
    	String PreAuthSeqID=shortfallVO.getPreAuthSeqID()==null?" ":shortfallVO.getPreAuthSeqID().toString();
    	String preparedSeqIds=shortfallSeqID+"|"+PreAuthSeqID;
    	sbfCacheId.append(preparedSeqIds);
    	sbfCacheId.append("~#~").append(shortfallVO.getShortfallNo()!=null? String.valueOf(shortfallVO.getShortfallNo()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getPreAuthNo()!=null? String.valueOf(shortfallVO.getPreAuthNo()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getEnrollmentID()!=null? String.valueOf(shortfallVO.getEnrollmentID()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getPolicyNo()!=null? String.valueOf(shortfallVO.getPolicyNo()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getStatusDesc()!=null? String.valueOf(shortfallVO.getStatusDesc()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getPolicyNo()!=null? String.valueOf(shortfallVO.getPolicyNo()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getStatusDesc()!=null? String.valueOf(shortfallVO.getStatusDesc()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getPolicyNo()!=null? String.valueOf(shortfallVO.getPolicyNo()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getStatusDesc()!=null? String.valueOf(shortfallVO.getStatusDesc()):" ");
    	
    	return sbfCacheId.toString();
    }//end of prepareWebBoardId(ShortfallVO shortfallVO,String strIdentifier)throws TTKException
  
    /**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthSupportManager getPreAuthSupportManagerObject() throws TTKException
	{
		PreAuthSupportManager preAuthSupportManager = null;
		try
		{
			if(preAuthSupportManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthSupportManager = (PreAuthSupportManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthSupportManagerBean!com.ttk.business.preauth.PreAuthSupportManager");

			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strSupportError);
		}//end of catch
		return preAuthSupportManager;
	}//end getPreAuthSupportManagerObject()
}//end of PreAuthShortfallsAction