/**
 * @ (#) OnlinePoliciesAction.java July 24,2006
 * Project       : TTK HealthCare Services
 * File          : OnlinePoliciesAction.java
 * Author        : Harsha Vardhan B N
 * Company       : Span Systems Corporation
 * Date Created  : July 24,2006
 *
 * @author       :
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;

import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.action.tree.TreeData;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.OnlineAccessPolicyVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This class is used to view the List of Policies
 */
public class OnlinePoliciesAction extends TTKAction {
	private static Logger log = Logger.getLogger( OnlinePoliciesAction.class );
	//  Action mapping forwards.
	private static final String strForward ="Forward";
	private static final String strBackward ="Backward";
    private static final String strPoliciesList="policieslist"; 
    private static final String strCorporateList="corporatelist";
    //Exception Message Identifier
    private static final String strOnlinePolicies="onlinePolicies";
    private static final String strOnlinePolicyDetails="onlinepolicydetails";
    private static final String strFailure="failure";
    
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
    
    public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.debug("Inside OnlinePoliciesAction doDefault");
    		TableData  tableData =TTKCommon.getTableData(request);
    		OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
    		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
            	((DynaActionForm)form).initialize(mapping);
            }//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
    		//Remove the Unwanted things from session
            request.getSession().removeAttribute("SelectedPolicy");
            String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)
            											request.getSession().getAttribute("UserSecurityProfile"));
            if(userSecurityProfile.getLoginType().equals(""))
			{
                ActionMessages actionMessages = new ActionMessages();
                ActionMessage actionMessage = new ActionMessage("error.security.unauthorized");
                actionMessages.add("global.error",actionMessage);
                saveErrors(request,actionMessages);
                return mapping.findForward(strFailure);
            }//end of if(((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"))
            																			//.getLoginType().equals(""))
        	if(!strPageID.equals(""))
			{
				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return (mapping.findForward(strPoliciesList));
			}///end of if(!strPageID.equals(""))
			
			if(userSecurityProfile.getLoginType().equals("B"))
			{
				tableData.createTableInfo("OnlinePoliciesSearchTable",null);
			}else
			{
				tableData.createTableInfo("OnlinePoliciesTable",null);
			}
			if((userSecurityProfile.getLoginType().equals("E"))||(userSecurityProfile.getLoginType().equals("I")))
			{
				((Column)((ArrayList)tableData.getTitle()).get(3)).setVisibility(false);
			}// end of if()
			//fetch the data from the data access layer and set the data to table object
			tableData.setSearchData(this.populateSearchCriteria(request));
			ArrayList alPolicyList=onlineAccessManagerObject.getOnlinePolicyList(tableData.getSearchData());
			tableData.setData(alPolicyList, "search");
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strPoliciesList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request, mapping, new TTKException(exp, strOnlinePolicies));
    	}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    
    
//kocbroker
    public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception{
        try{
            setOnlineLinks(request);
            log.debug("Inside the doSearch method of OnlinePoliciesAction");
			OnlineAccessManager onlineAccessManager = null;
			TableData tableData=null;
			String strPageID =""; 
			String strSortID ="";	
			ArrayList alOnlineAccList = null;
			
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
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
					return (mapping.findForward(strPoliciesList));
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
				tableData.createTableInfo("OnlinePoliciesSearchTable",null);
				tableData.setSearchData(this.populateSearchCriteriaBro((DynaActionForm)form,request));						
				tableData.modifySearchData("Policies");
				//sorting based on investSeqID in descending order													
			}//end of else
			alOnlineAccList=onlineAccessManagerObject.getOnlinePolicySearchList(tableData.getSearchData());
			tableData.setData(alOnlineAccList, "Policies");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strPoliciesList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
        	return this.processOnlineExceptions(request, mapping, new TTKException(exp, strOnlinePolicies));
        }//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                            //HttpServletResponse response)
    
    private ArrayList<Object> populateSearchCriteriaBro(DynaActionForm frmOnlinePolicies,HttpServletRequest request) throws TTKException
    {
//      build the column names along with their values to be searched
        ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();
        UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
        //prepare the search BOX parameters
        String strUserID = userSecurityProfile.getUSER_ID();
        alSearchBoxParams.add(strUserID);
        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePolicies.getString("sPolicyNumber")));
    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePolicies.getString("sGroupId")));
    	alSearchBoxParams.add(userSecurityProfile.getLoginType());
    	alSearchBoxParams.add(TTKCommon.getUserSeqId(request));
    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePolicies.getString("sGroupName")));
		
    	
    	return alSearchBoxParams;
    }//end of populateSearchCriteria(DynaActionForm frmOnlineAccountInfo,HttpServletRequest request)

    
    public ActionForward doSelectCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
		setOnlineLinks(request);
		log.debug("Inside OnlinePoliciesAction doSelectCorporate");
		DynaActionForm frmOnlinePolicies=(DynaActionForm)form;
		frmOnlinePolicies.set("frmChanged","changed");
		request.getSession().setAttribute("frmOnlinePolicies",frmOnlinePolicies);
		return mapping.findForward(strCorporateList);
		}//end of try
		catch(TTKException expTTK)
			{
			return this.processOnlineExceptions(request,mapping,expTTK);
				}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processOnlineExceptions(request,mapping,new TTKException(exp,strOnlinePolicies));
		}//end of catch(Exception exp)
    }//end of doSelectCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
    
    
//end kocbroker    
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
			log.debug("Inside the doBackward method of OnlinePoliciesAction");
			setOnlineLinks(request);
			//Get the session bean from the beand pool for each thread being excuted.
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
			TableData  tableData =TTKCommon.getTableData(request);
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alPolicyList=onlineAccessManagerObject.getOnlinePolicyList(tableData.getSearchData());
			tableData.setData(alPolicyList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strPoliciesList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePolicies));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
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
			log.debug("Inside the doForward method of OnlinePoliciesAction");
			setOnlineLinks(request);
			//Get the session bean from the beand pool for each thread being excuted.
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			//modify the search data
			tableData.modifySearchData(strForward);
			ArrayList alPolicyList=onlineAccessManagerObject.getOnlinePolicyList(tableData.getSearchData());
			//set the table data
			tableData.setData(alPolicyList, strForward);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strPoliciesList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePolicies));
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
    public ActionForward doSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.debug("Inside OnlinePoliciesAction doSelectPolicy");
    		DynaActionForm frmOnlineDetails=(DynaActionForm)form;
    		Document historyDoc=null;
    		OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
    		TableData  tableData =TTKCommon.getTableData(request);
    		OnlineAccessPolicyVO onlinePolicyVO = null;
    		ArrayList<Object> alParams = new ArrayList<Object>();
    		String strCaption="";
    		//if rownumber is found populate the form object
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			onlinePolicyVO =(OnlineAccessPolicyVO)tableData.getRowInfo(
    															Integer.parseInt(request.getParameter("rownum")));
    			request.getSession().setAttribute("SelectedPolicy",onlinePolicyVO);
    			 strCaption="["+TTKCommon.checkNull(onlinePolicyVO.getPolicyNbr())+"]";
    			alParams.add(onlinePolicyVO.getPolicySeqID());
    			alParams.add(onlinePolicyVO.getEmployeeNbr());
    			alParams.add(onlinePolicyVO.getMemberSeqID());
    			historyDoc=onlineAccessManagerObject.getOnlinePolicyDetail(alParams);
    		}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		
    		request.setAttribute("historyDoc",historyDoc);
    		UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile"));
    		request.setAttribute("logintype",userSecurityProfile.getLoginType());
    		frmOnlineDetails.set("caption",strCaption);
    		log.info("Login Type : "+userSecurityProfile.getLoginType());
    		return this.getForward(strOnlinePolicyDetails, mapping, request);
    		}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request, mapping, new TTKException(exp, strOnlinePolicies));
    	}//end of catch(Exception exp)
    }//end of doSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
   
    
    
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
    public ActionForward doViewPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.debug("Inside OnlinePoliciesAction doViewPolicy");
    		DynaActionForm frmOnlineDetails=(DynaActionForm)form;
    		MemberVO memberVO =null;
    		 TreeData treeData = TTKCommon.getTreeData(request);
    		Document historyDoc=null;
    		String strClaimantName = "";
    		frmOnlineDetails.set("selectedroot",TTKCommon.checkNull(request.getParameter("selectedroot")));
    		frmOnlineDetails.set("selectednode",TTKCommon.checkNull(request.getParameter("selectednode")));
    		String strSelectedRoot = (String)frmOnlineDetails.get("selectedroot");
			String strSelectedNode = (String)frmOnlineDetails.get("selectednode");
			if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
			{
				memberVO = (MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
				String strName1 =  memberVO.getName();
				strClaimantName =  strName1.substring(strName1.indexOf("/")+1,strName1.lastIndexOf("/")-1).trim();
			}//end of if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
			
   		    OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
    		ArrayList<Object> alParams = new ArrayList<Object>();
    		String strCaption="";
    		strCaption="[ "+strClaimantName+" ]"+"[ "+TTKCommon.checkNull(memberVO.getEnrollmentID())+" ]";
			alParams.add(null);
			alParams.add(null);
			alParams.add(memberVO.getMemberSeqID());
			
			
			historyDoc=onlineAccessManagerObject.getOnlinePolicyDetail(alParams);
    		request.setAttribute("historyDoc",historyDoc);
    		UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile"));
    		
    		
    		//ADDED FOR CR KOC1118 CHANGE 
    		userSecurityProfile.setMemSeqID(memberVO.getMemberSeqID());
    		request.getSession().setAttribute("UserSecurityProfile",userSecurityProfile);
    		
    		
    		request.setAttribute("logintype",userSecurityProfile.getLoginType());
    		frmOnlineDetails.set("caption",strCaption);
    		log.info("Login Type : "+userSecurityProfile.getLoginType());
    		return this.getForward(strOnlinePolicyDetails, mapping, request);
    		}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request, mapping, new TTKException(exp, strOnlinePolicies));
    	}//end of catch(Exception exp)
    }//end of doViewPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doSelectOnlinePolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.debug("Inside OnlinePoliciesAction doSelectOnlinePolicy");
    		TableData  tableData =TTKCommon.getTableData(request);
    		OnlineAccessPolicyVO onlinePolicyVO = null;
    		//if rownumber is found populate the form object
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		{
    			onlinePolicyVO =(OnlineAccessPolicyVO)tableData.getRowInfo(
    															Integer.parseInt(request.getParameter("rownum")));
    			request.getSession().setAttribute("SelectedPolicy",onlinePolicyVO);
    		}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		
    		return (mapping.findForward("onlineHrMember"));
    		}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request, mapping, new TTKException(exp, strOnlinePolicies));
    	}//end of catch(Exception exp)
    }//end of doSelectOnlinePolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.debug("Inside OnlinePoliciesAction doClose");
    		return mapping.findForward("onlinemember");
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processOnlineExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processOnlineExceptions(request,mapping,new TTKException(exp,strOnlinePolicies));
    	}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * Returns the ArrayList which contains the populated search criteria elements
     * @param request HttpServletRequest object which contains the search parameter that is built
     * @return alSearchParams ArrayList
     */
    private ArrayList<Object> populateSearchCriteria(HttpServletRequest request) throws TTKException
    {
    	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)
    												request.getSession().getAttribute("UserSecurityProfile");
    	//build the column names along with their values to be searched
    	ArrayList<Object> alSearchParams = new ArrayList<Object>();
    	if(userSecurityProfile.getLoginType().equals("E"))  //for corporate login
    	{
    		alSearchParams.add(userSecurityProfile.getUSER_ID());
    		alSearchParams.add(userSecurityProfile.getPolicyNo());
    		alSearchParams.add(userSecurityProfile.getGroupID());
    		alSearchParams.add(null);
    		alSearchParams.add(userSecurityProfile.getLoginType());
    		alSearchParams.add(TTKCommon.getUserSeqId(request));
    	}//end of if(userSecurityProfile.getLoginType().equals("E"))
    	else if(userSecurityProfile.getLoginType().equals("B"))  //for Broker login
    	{
    		alSearchParams.add(userSecurityProfile.getUSER_ID());
    		alSearchParams.add(null);
    		alSearchParams.add(userSecurityProfile.getGroupID());
    		alSearchParams.add(null);
    		alSearchParams.add(userSecurityProfile.getLoginType());
    		alSearchParams.add(TTKCommon.getUserSeqId(request));
    	}//end of if(userSecurityProfile.getLoginType().equals("B"))
    	else if(userSecurityProfile.getLoginType().equals("I"))     //for Individual Login
    	{
    		alSearchParams.add(null);
    		alSearchParams.add(userSecurityProfile.getPolicyNo());
    		alSearchParams.add(null);
    		alSearchParams.add(userSecurityProfile.getEnrollmentID());
    		alSearchParams.add(userSecurityProfile.getLoginType());
    		alSearchParams.add(TTKCommon.getUserSeqId(request));
    	}//end of else if(userSecurityProfile.getLoginType().equals("I"))
    	else if(userSecurityProfile.getLoginType().equals("H"))     //for HR Login
    	{
    		alSearchParams.add(userSecurityProfile.getUSER_ID());
    		alSearchParams.add(null);
    		alSearchParams.add(userSecurityProfile.getGroupID());
    		alSearchParams.add(null);
    		alSearchParams.add(userSecurityProfile.getLoginType());
    		alSearchParams.add(TTKCommon.getUserSeqId(request));
    	}//end of else if(userSecurityProfile.getLoginType().equals("H"))
    	return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmSearchBankAccount)

       
    /**
    * Returns the onlineAccessManager session object for invoking methods on it.
    * @return onlineAccessManager session object which can be used for method invocation
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
   			log.debug("Inside getOnlineAccessManagerObject: onlineAccessManager: " + onlineAccessManager);
   		}//end if
   	}//end of try
   	catch(Exception exp)
   	{
   		throw new TTKException(exp, strFailure);
   	}//end of catch
   	return onlineAccessManager;
   }//end of getOnlineAccessManagerObject()
}//end of class OnlinePoliciesAction
