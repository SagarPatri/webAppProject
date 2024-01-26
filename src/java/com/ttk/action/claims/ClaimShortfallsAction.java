/**
 * @ (#) ClaimShortfallsAction.java July 7, 2015
 * Project 	     : ProjectX
 * File          : ClaimShortfallsAction.java
 * Author        : Nagababu K
 * Company       : RCS Technologies
 * Date Created  : July 7, 2015
 *
 * @author       :  Nagababu K
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */

package com.ttk.action.claims;

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

public class ClaimShortfallsAction extends TTKAction
{
	private static Logger log = Logger.getLogger(ClaimShortfallsAction.class);

	//declrations of modes
	private static final String strForward="Forward";
	private static final String strBackward="Backward";

	//Action mapping forwards.
	private static final  String strClaimShortfallslist="claimshortfalllist";
	private static final  String strClaimShortfallDetails="claimshortfalldetails";

    private static final int ASSIGN_ICON=8;

    //Exception Message Identifier
    private static final String strClaimSearchError="hospitalsearch";
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
    		log.debug("Inside the doDefault method of ClaimShortfallsAction");
    		setLinks(request);
    		//get the tbale data from session if exists
			TableData tableData = new TableData();
			//clear the dynaform if visiting from left links for the first time
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			//create the required grid table
			tableData.createTableInfo("ClaimShortfallsTable",new ArrayList());
			this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
			request.getSession().setAttribute("tableData",tableData);
			//((DynaActionForm)form).initialize(mapping);//reset the form data
			return this.getForward(strClaimShortfallslist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallslist));
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
    		log.debug("Inside the doSearch method of ClaimShortfallsAction");
    		setLinks(request);
    		ClaimManager claimManagerObject=this.getClaimManagerObject();
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//PreAuthVO preAuthVO=new PreAuthVO;
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strClaimShortfallslist);
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
				tableData.createTableInfo("ClaimShortfallsTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
                this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
				tableData.modifySearchData("search");
			}//end of else
			
			ArrayList alClaimsShortfallList= claimManagerObject.getClaimShortfallList(tableData.getSearchData());
			tableData.setData(alClaimsShortfallList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strClaimShortfallslist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallslist));
		}//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside the doBackward method of ClaimShortfallsAction");
    		setLinks(request);
    		ClaimManager claimManagerObject=this.getClaimManagerObject();
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
    		tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alClaimsShortfallList = claimManagerObject.getClaimShortfallList(tableData.getSearchData());
			tableData.setData(alClaimsShortfallList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strClaimShortfallslist, mapping, request);   //finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallslist));
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
    		log.debug("Inside the doForward method of ClaimShortfallsAction");
    		setLinks(request);
    		ClaimManager claimManagerObject=this.getClaimManagerObject();
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
    		tableData.modifySearchData(strForward);//modify the search data
			ArrayList alClaimsShortfallList = claimManagerObject.getClaimShortfallList(tableData.getSearchData());
			tableData.setData(alClaimsShortfallList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strClaimShortfallslist, mapping, request);   //finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallslist));
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
    		log.debug("Inside the doCopyToWebBoard method of ClaimShortfallsAction");
    		setLinks(request);
    		this.populateWebBoard(request);
			return this.getForward(strClaimShortfallslist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallslist));
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
    		log.debug("Inside the doView method of ClaimShortfallsAction");
    		setLinks(request);
    		//get the tbale data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				ShortfallVO shortfallVO=(ShortfallVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				request.getSession().setAttribute("searchShortfallVO", shortfallVO);
				request.getSession().setAttribute("closeShortfalls", "goSearch");
				this.addToWebBoard(shortfallVO, request);
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward(strClaimShortfallDetails);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimShortfallslist));
		}//end of catch(Exception exp)
    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmClaimsList formbean which contains the search fields
     * @param request HttpServletRequest
     * @return ArrayList contains search parameters
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmClaimsShortfallList,HttpServletRequest request)
    {
    	//build the column names along with their values to be searched
    	ArrayList<Object> alSearchParams = new ArrayList<Object>();
    	alSearchParams.add(frmClaimsShortfallList.getString("sShortfallNO"));
    	alSearchParams.add(frmClaimsShortfallList.getString("sInvoiceNO"));
    	alSearchParams.add(frmClaimsShortfallList.getString("sBatchNO"));
    	alSearchParams.add(frmClaimsShortfallList.getString("sPolicyNumber"));
    	alSearchParams.add(frmClaimsShortfallList.getString("sClaimNO"));
    	alSearchParams.add(frmClaimsShortfallList.getString("sClaimType"));
    	alSearchParams.add(frmClaimsShortfallList.getString("sSettlementNO"));
    	alSearchParams.add(frmClaimsShortfallList.getString("sEnrollmentId"));
		alSearchParams.add(frmClaimsShortfallList.getString("sStatus"));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
    	return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmClaimsList,HttpServletRequest request)
   
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
    	String batchSeqID=shortfallVO.getShortfallSeqID()==null?" ":shortfallVO.getShortfallSeqID().toString();
    	String claimSeqID=shortfallVO.getClaimSeqID()==null?" ":shortfallVO.getClaimSeqID().toString();
    	String preparedSeqIds=batchSeqID+"|"+claimSeqID;
    	sbfCacheId.append(preparedSeqIds);
    	sbfCacheId.append("~#~").append(shortfallVO.getShortfallNo()!=null? String.valueOf(shortfallVO.getShortfallNo()):" ");
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(shortfallVO.getEnrollmentID()).equals("")?" ":shortfallVO.getEnrollmentID());
    	sbfCacheId.append("~#~").append(shortfallVO.getEnrollDtlSeqID()!=null?String.valueOf(shortfallVO.getEnrollDtlSeqID()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getPolicySeqID()!=null? String.valueOf(shortfallVO.getPolicySeqID()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getMemberSeqID()!=null? String.valueOf(shortfallVO.getMemberSeqID()):" ");
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(shortfallVO.getClaimantName()).equals("")? " ":shortfallVO.getClaimantName());
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(shortfallVO.getBufferAllowedYN()).equals("")? " ":shortfallVO.getBufferAllowedYN());
    	sbfCacheId.append("~#~").append(shortfallVO.getClmEnrollDtlSeqID()!=null? String.valueOf(shortfallVO.getClmEnrollDtlSeqID()):" ");
    	sbfCacheId.append("~#~").append(shortfallVO.getAmmendmentYN()!=null? String.valueOf(shortfallVO.getAmmendmentYN()):" ");
    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(shortfallVO.getCoding_review_yn()).equals("")? " ":shortfallVO.getCoding_review_yn());
    	return sbfCacheId.toString();
    }//end of prepareWebBoardId(ShortfallVO shortfallVO,String strIdentifier)throws TTKException

    /**
     * This method supresess the Assign Icon Column by checking against the Assign and Special Permissions
     *  from the User's Role.
     * @param tableData TableData object which contains the Grid Information
     * @param frmClaimList formbean which contains the search fields
     * @param request HttpServletRequest object
     * @throws TTKException if any run time exception occures
     */
    private void setColumnVisiblity(TableData tableData,DynaActionForm frmClaimList,HttpServletRequest request)
    												throws TTKException
    {
    	String strAssignTo=frmClaimList.getString("sAssignedTo");
    	boolean blnVisibility=false;
    	//For Self Check the Assign Permission
    	if(strAssignTo.equals("SLF") && TTKCommon.isAuthorized(request,"Assign"))
    	{
    		blnVisibility=true;
    	}//end of if(strAssignTo.equals("SLF") && TTKCommon.isAuthorized(request,"Assign"))
    	else         //Check for the special Permission to show ICON for Others and Unassigned Claim
    	{
    		if(TTKCommon.isAuthorized(request,"AssignAll"))
    		{
    			blnVisibility=true;
    		}//end of if(TTKCommon.isAuthorized(request,"AssignAll"))
    	}//end of else
    	((Column)((ArrayList)tableData.getTitle()).get(ASSIGN_ICON)).setVisibility(blnVisibility);
    }//end of setColumnVisiblity(TableData tableData,DynaActionForm frmPreAuthList,HttpServletRequest request)
}//end of ClaimShortfallsAction