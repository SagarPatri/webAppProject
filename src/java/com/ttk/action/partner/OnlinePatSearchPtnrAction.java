/**
 * as per Hospital Login
 * @ (#) OnlinePatSearchHospActionMar 24, 2014
 *  Author       :Satya Moganti
 * Company      : Rcs Technologies
 * Date Created : march 24 ,2014
 *
 * @author       :Satya moganti
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.partner;

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
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.hospital.HospPreAuthVO;
//import com.ttk.dto.onlineforms.OnlineInsPolicyVO;
//import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This class is used for Searching the List Policies to see the Online Account Info.
 * This also provides deletion and updation of products.
 */
public class OnlinePatSearchPtnrAction extends TTKAction {

	private static final Logger log = Logger.getLogger( OnlinePatSearchPtnrAction.class );

	//Modes.
	private static final String strBackward="Backward";
	private static final String strForward="Forward";

	private static final String strRegular="Regular";    //identfier for Regular Pre-Auth
	private static final String strEnhanced="Enhanced";  //identifier for Enhanced Pre-Auth
	private static final String strRegularPreAuth="PAT";
	private static final String strEnhancedPreAuth="ICO";


	// Action mapping forwards.
	//Exception Message Identifier
	private static final String strHospSerachInfo="onlinehospitalinfo";
	private static final String strHosPreAuthDetail="hospreauthdetail";
	//<forward name="hospreauthdetail" path="/PreAuthGeneralAction.do?mode=doView" />

	
	private static final String strPartnerOnlinePatList="partneronlinepatlist";  
	private static final String strPtnrSerachInfo="onlinepartnerinfo";
	
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
    public ActionForward doDashBoardDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doDashBoardDefault method of OnlinePatSearchPtnrAction");
            setOnlineLinks(request);
            
            return this.getForward("partnerpreauthdashboard", mapping, request);//partnerpreauthdashboard
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strPtnrSerachInfo));
        }//end of catch(Exception exp)
    }//end of doDashBoardDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
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
			log.info("Inside the doDefault method of OnlinePatSearchHospAction");
			setOnlineLinks(request);
			TableData tableData =null;

			DynaActionForm frmPatHospSearch =(DynaActionForm)form;
			frmPatHospSearch.initialize(mapping);     //reset the form data
			//get the tbale data from session if exists
			tableData =TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("HospPreAuthTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			//((DynaActionForm)form).initialize(mapping);//reset the form data
			return this.getForward(strPartnerOnlinePatList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			setOnlineLinks(request);
			log.debug("Inside the doSearch method of OnlinePatSearchHospAction");
			OnlineAccessManager onlineAccessManager = null;
			TableData tableData=null;
			String strPageID =""; 
			String strSortID ="";	
			ArrayList alOnlineAccList = null;
			onlineAccessManager = this.getOnlineAccessManagerObject();
			tableData=(TableData)request.getSession().getAttribute("tableData");
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strPartnerOnlinePatList));
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
				tableData.createTableInfo("HospPreAuthTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));						
				tableData.modifySearchData("search");
				//sorting based on investSeqID in descending order													
			}//end of else
			alOnlineAccList= onlineAccessManager.getHospPreAuthList(tableData.getSearchData());
			tableData.setData(alOnlineAccList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strPartnerOnlinePatList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)




	public ActionForward doDashBoardSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside the doSearch method of OnlinePatSearchHospAction");
			OnlineAccessManager onlineAccessManager = null;
			TableData tableData=null;
			ArrayList alOnlineAccList = null;
			onlineAccessManager = this.getOnlineAccessManagerObject();
			
			DynaActionForm frmPatHospSearch =(DynaActionForm)form;
			frmPatHospSearch.initialize(mapping);     //reset the form data
			
			frmPatHospSearch.set("sStatus",TTKCommon.checkNull(request.getParameter("sStatus")));
			frmPatHospSearch.set("sPatStartDate",TTKCommon.checkNull(request.getParameter("sPatStartDate"))); //sPatStartDate,sPatEndDate,sClmStartDate,sClmEndDate
			frmPatHospSearch.set("sPatEndDate",TTKCommon.checkNull(request.getParameter("sPatEndDate")));
		
			tableData=(TableData)request.getSession().getAttribute("tableData");
			tableData =TTKCommon.getTableData(request);
			
				//create the required grid table
			tableData.createTableInfo("HospPreAuthTable",null);
			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));						
			tableData.modifySearchData("search");
				//sorting based on investSeqID in descending order		
				
			alOnlineAccList= onlineAccessManager.getHospPreAuthList(tableData.getSearchData());
			tableData.setData(alOnlineAccList, "search");
			//frmPatHospSearch.set("sStatus",null);
			frmPatHospSearch.set("sPatStartDate",null);
			frmPatHospSearch.set("sPatEndDate",null);
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strPartnerOnlinePatList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
		}//end of catch(Exception exp)
	}//end of doDashBoardSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			log.debug("Inside the doBackward method of PolicyAccountInfoAction");
			setOnlineLinks(request);
			//get the session bean from the bean pool for each excecuting thread
			OnlineAccessManager onlineAccessManager=null;
			TableData tableData =null;
			ArrayList alPolicyList =null;
			onlineAccessManager =this.getOnlineAccessManagerObject();
			tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strBackward);
			alPolicyList = onlineAccessManager.getHospPreAuthList(tableData.getSearchData());
			tableData.setData(alPolicyList, strBackward);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strPartnerOnlinePatList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest
	//request,HttpServletResponse response)

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
			log.debug("Inside the doForward method of OnlineAccountInfoAction");
			setOnlineLinks(request);
			//get the session bean from the bean pool for each excecuting thread
			//DynaActionForm frmPatHospSearch =(DynaActionForm)form;
			//frmPatHospSearch.initialize(mapping);
			
			OnlineAccessManager onlineAccessManager=null;
			TableData tableData =null;
			ArrayList alPolicyList =null;
			onlineAccessManager =this.getOnlineAccessManagerObject();
			tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strForward);
			alPolicyList = onlineAccessManager.getHospPreAuthList(tableData.getSearchData());
			tableData.setData(alPolicyList, strForward);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strPartnerOnlinePatList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	
	/**
	 * Returns the AccountInfoManager session object for invoking methods on it.
	 * @return accountInfoManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private OnlineAccessManager getOnlineAccessManagerObject() throws TTKException
	{
		OnlineAccessManager onlineAccessManager = null;
		try
		{
			if(onlineAccessManager == null)
			{
				InitialContext ctx = new InitialContext();
				//onlineAccessManager = (OnlineAccessManager) ctx.lookup(OnlineAccessManager.class.getName());
				onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strHospSerachInfo);
		}//end of catch
		return onlineAccessManager;
	}//end of getOnlineAccessManagerObject()

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPatHospSearch formbean which contains the search fields
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmPatHospSearch,HttpServletRequest request) throws TTKException
	{
		//      build the column names along with their values to be searched
		ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();
		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
		//prepare the search BOX parameters
		//Long lngHospSeqId =userSecurityProfile.getHospSeqId();
		alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPatHospSearch.getString("sPreAuthNumber")));
		alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPatHospSearch.getString("sAuthNumber")));
		alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPatHospSearch.getString("sEnrollmentNumber")));
		alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPatHospSearch.getString("sStatus")));
		alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPatHospSearch.getString("sPolicyNumber")));
		alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPatHospSearch.getString("sDOA")));
		alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPatHospSearch.getString("sDateOfPat")));
		alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPatHospSearch.getString("sPatStartDate")));//sPatStartDate,sPatEndDate,sClmStartDate,sClmEndDate
		alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPatHospSearch.getString("sPatEndDate")));
		alSearchBoxParams.add(userSecurityProfile.getHospSeqId());
		
		//new Req
        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmPatHospSearch.getString("sPatientName")));

		return alSearchBoxParams;
	}//end of populateSearchCriteria(DynaActionForm frmPatHospSearch,HttpServletRequest request)  


	private ArrayList<Object> populateDashBoardSearch(HttpServletRequest request) throws TTKException
	{
		//      build the column names along with their values to be searched
		ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();
		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
		//prepare the search BOX parameters
		//Long lngHospSeqId =userSecurityProfile.getHospSeqId();
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)request.getParameter("sStatus")));
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(userSecurityProfile.getHospSeqId());
		return alSearchBoxParams;
	}//end of populateSearchCriteria(DynaActionForm frmPatHospSearch,HttpServletRequest request)  
	
	

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
			return this.getForward(strPartnerOnlinePatList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
		}//end of catch(Exception exp)
	}//end of CopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

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
		HospPreAuthVO preAuthVO=null;

		if(strChk!=null&&strChk.length!=0)
		{
			for(int i=0; i<strChk.length;i++)
			{
				cacheObject = new CacheObject();
				preAuthVO=(HospPreAuthVO)tableData.getData().get(Integer.parseInt(strChk[i]));
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
	 * This method prepares the Weboard id for the selected Policy
	 * @param preAuthVO  preAuthVO for which webboard id to be prepared
	 * * @param String  strIdentifier whether it is preauth or enhanced preauth
	 * @return Web board id for the passedVO
	 */
	private String prepareWebBoardId(HospPreAuthVO preAuthVO,String strIdentifier)throws TTKException
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


	}//end of prepareWebBoardId(HospPreAuthVO preAuthVO,String strIdentifier)throws TTKException



	/**
	 * Adds the selected item to the web board and makes it as the selected item in the web board
	 * @param  preauthVO  object which contains the information of the preauth
	 * * @param String  strIdentifier whether it is preauth or enhanced preauth
	 * @param request HttpServletRequest
	 * @throws TTKException if any runtime exception occures
	 */
	private void addToWebBoard(HospPreAuthVO preAuthVO, HttpServletRequest request,String strIdentifier)throws TTKException
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
	public ActionForward doHospViewPreauth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			//log.info("Inside PreAuthAction doViewPreauth");
			setOnlineLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmPatHospSearch=(DynaActionForm)form;
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				HospPreAuthVO preAuthVO=(HospPreAuthVO)tableData.getRowInfo(Integer.parseInt(
						(String)(request.getParameter("rownum"))));
				this.addToWebBoard(preAuthVO, request,strRegular);
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward(strHosPreAuthDetail);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
		}//end of catch(Exception exp)
	}//end of doHospViewPreauth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
				HospPreAuthVO preAuthVO=(HospPreAuthVO)tableData.getRowInfo(Integer.parseInt((String)
						(frmPreAuthList).get("rownum")));
				this.addToWebBoard(preAuthVO, request,strEnhanced);
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward(strHosPreAuthDetail);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
		}//end of catch(Exception exp)
	}//end of doViewEnhancedPreauth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)


	
	 
	
	/* public ActionForward doPreauthDashBoardData(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception{
			try{
				setOnlineLinks(request);
				log.debug("Inside the doSearch method of OnlinePatSearchHospAction");
				UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
				OnlineAccessManager onlineAccessManagerObject = null;
				TableData tableData=null;			
				
				String strStype=TTKCommon.checkNull(request.getParameter("sType"));
				String sFromDate=TTKCommon.checkNull(request.getParameter("sFromDate"));
				String sToDate=TTKCommon.checkNull(request.getParameter("sToDate"));
				
				
				onlineAccessManagerObject = this.getOnlineAccessManagerObject();
			//	tableData=(TableData)request.getSession().getAttribute("tableData");
					
					
				Document docClaims=(Document)onlineAccessManagerObject.getPartnerDashBoard(userSecurityProfile.getPtnrSeqId(),sFromDate,sToDate,"Claims");
				Document docPreauth=(Document)onlineAccessManagerObject.getPartnerDashBoard(userSecurityProfile.getPtnrSeqId(),sFromDate,sToDate,"Preauth");
				
				request.getSession().removeAttribute("onlineptnrhomepreauth");
				request.getSession().removeAttribute("onlineptnrhomeclaim");
				
				request.getSession().setAttribute("onlineptnrhomepreauth",docPreauth);
				request.getSession().setAttribute("onlineptnrhomeclaim",docClaims);
				
				request.getSession().setAttribute("sType",strStype);
				//set the table data object to session
				//finally return to the grid screen
				return mapping.findForward("partnerhome");
		
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processOnlineExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
			}//end of catch(Exception exp)
		}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
*/


}//end of OnlinePatSearchHospAction
