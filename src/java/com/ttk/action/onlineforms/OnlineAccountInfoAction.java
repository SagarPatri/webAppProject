/**
 * @ (#) OnlineAccountInfoAction.java Mar 24, 2008
 * Project      : TTK HealthCare Services
 * File         : OnlineAccountInfoAction.java
 * Author       : Balakrishna E
 * Company      : Span Systems Corporation
 * Date Created : Mar 24, 2008
 *
 * @author       :  Balakrishna E
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.onlineforms;

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
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.onlineforms.OnlineInsPolicyVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This class is used for Searching the List Policies to see the Online Account Info.
 * This also provides deletion and updation of products.
 */
public class OnlineAccountInfoAction extends TTKAction {

    private static final Logger log = Logger.getLogger( OnlineAccountInfoAction.class );

    //Modes.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";

    // Action mapping forwards.
    private static final String strOnlinePolicyList="onlinepolicylist";
    private static final String strCorporateList="corporatelist";
    private static final String strLogdetails = "logdetails";
    //Exception Message Identifier
    private static final String strOnlineAccInfo="onlineaccountinfo";

    private static final String strOnlineInsDashBoard="insDashBoard";
    

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
            log.info("Inside the doDefault method of OnlineAccountInfoAction");
            setOnlineLinks(request);
            TableData tableData =null;

            DynaActionForm frmOnlineAccountInfo =(DynaActionForm)form;
            frmOnlineAccountInfo.initialize(mapping);     //reset the form data
            //get the tbale data from session if exists
            tableData =TTKCommon.getTableData(request);
            //create new table data object
            tableData = new TableData();
            //create the required grid table
            tableData.createTableInfo("OnlineAccountInfoTable",new ArrayList());
            request.getSession().setAttribute("tableData",tableData);
            //((DynaActionForm)form).initialize(mapping);//reset the form data
            return this.getForward(strOnlinePolicyList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
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
            log.debug("Inside the doSearch method of OnlineAccountInfoAction");
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
					return (mapping.findForward(strOnlinePolicyList));
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
				tableData.createTableInfo("OnlineAccountInfoTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));						
				tableData.modifySearchData("search");
				//sorting based on investSeqID in descending order													
			}//end of else
			alOnlineAccList= onlineAccessManager.getInsEnrollmentList(tableData.getSearchData());
			tableData.setData(alOnlineAccList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strOnlinePolicyList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
        }//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
            alPolicyList = onlineAccessManager.getInsEnrollmentList(tableData.getSearchData());
            tableData.setData(alPolicyList, strBackward);
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward(strOnlinePolicyList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
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
            OnlineAccessManager onlineAccessManager=null;
            TableData tableData =null;
            ArrayList alPolicyList =null;
            onlineAccessManager =this.getOnlineAccessManagerObject();
            tableData = TTKCommon.getTableData(request);
            tableData.modifySearchData(strForward);
            alPolicyList = onlineAccessManager.getInsEnrollmentList(tableData.getSearchData());
            tableData.setData(alPolicyList, strForward);
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward(strOnlinePolicyList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
        }//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    public ActionForward doLogDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    															HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.info("Inside OnlineAccountInfoAction doLogDetails");
    		OnlineInsPolicyVO onlineInsPolicyVO=null;
    		TableData  tableData =(TableData)request.getSession().getAttribute("tableData");
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			onlineInsPolicyVO = (OnlineInsPolicyVO)tableData.getRowInfo(Integer.parseInt(
    																			request.getParameter("rownum")));
    			request.getSession().setAttribute("callCenterDetailVO",onlineInsPolicyVO);
    		}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		return mapping.findForward(strLogdetails);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request,mapping,new TTKException(exp,strOnlineAccInfo));
    	}//end of catch(Exception exp)
    }//end of doLogDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
      //HttpServletResponse response)

    /**
     * This method is used to
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSelectCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.debug("Inside OnlineAccountInfoAction doSelectCorporate");
    		DynaActionForm frmGeneral=(DynaActionForm)form;
    		frmGeneral.set("frmChanged","changed");
    		request.getSession().setAttribute("frmGeneral",frmGeneral);
    		return mapping.findForward(strCorporateList);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request,mapping,new TTKException(exp,strOnlineAccInfo));
    	}//end of catch(Exception exp)
    }//end of doSelectCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
                onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strOnlineAccInfo);
        }//end of catch
        return onlineAccessManager;
    }//end of getOnlineAccessManagerObject()

        /**
	     * this method will add search criteria fields and values to the arraylist and will return it
	     * @param frmOnlineAccountInfo formbean which contains the search fields
	     * @return ArrayList contains search parameters
	     */
	    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmOnlineAccountInfo,HttpServletRequest request) throws TTKException
	    {
	//      build the column names along with their values to be searched
	        ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();
	        UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	        //prepare the search BOX parameters
	        String strUserID = userSecurityProfile.getUSER_ID();
	        alSearchBoxParams.add(strUserID);
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlineAccountInfo.getString("sEnrollmentNumber")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlineAccountInfo.getString("sPolicyNumber")));
	    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlineAccountInfo.getString("sGroupId")));
	    	alSearchBoxParams.add(frmOnlineAccountInfo.getString("sGroupName"));
	    	alSearchBoxParams.add((String)frmOnlineAccountInfo.get("showLatest"));
	    	alSearchBoxParams.add((String)frmOnlineAccountInfo.get("memberName"));
	    	return alSearchBoxParams;
	    }//end of populateSearchCriteria(DynaActionForm frmOnlineAccountInfo,HttpServletRequest request)
	    
	    //added for brokerlogin
	    
         
	    
	    public ActionForward doSearchBroker(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                HttpServletResponse response) throws Exception{

	        try{
	            setOnlineLinks(request);
	            log.debug("Inside the doSearch method of OnlineAccountInfoAction");
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
						return (mapping.findForward(strOnlinePolicyList));
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
					tableData.createTableInfo("OnlineAccountInfoTable",null);
					tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));						
					tableData.modifySearchData("search");
					//sorting based on investSeqID in descending order													
				}//end of else
				alOnlineAccList= onlineAccessManager.getBroEnrollmentList(tableData.getSearchData());
				tableData.setData(alOnlineAccList, "search");
				//set the table data object to session
				request.getSession().setAttribute("tableData",tableData);
				//finally return to the grid screen
				return this.getForward(strOnlinePolicyList, mapping, request);
	        }//end of try
	        catch(TTKException expTTK)
	        {
	            return this.processOnlineExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
	        catch(Exception exp)
	        {
	            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
	        }//end of catch(Exception exp)
	    
	    	
	    }
}//end of OnlineAccountInfoAction
