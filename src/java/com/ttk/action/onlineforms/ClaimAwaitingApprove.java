/**
 * @ (#) ClaimAwaitingApprove.java  1274 Cxhange Request
        :
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
import org.apache.struts.taglib.tiles.GetAttributeTag;

import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.business.onlineforms.OnlineFeedbackManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.enrollment.PolicyDetailVO;
import com.ttk.dto.enrollment.PolicyVO;
import com.ttk.dto.onlineforms.BajajAlliannzFormVO;
import com.ttk.dto.onlineforms.ClaimFormVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of List of Policies.
 * This class also provides option for Saving the details.
 */
public class ClaimAwaitingApprove extends TTKAction {

	private static Logger log = Logger.getLogger( ClaimAwaitingApprove.class );
	//Modes
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	private static final String  strPreauth="PRE";
	private static final String  strClaim="CLM";
	//Action Mappings
	 String strStatus="";
	private static final String strDefault1="predefault1";
	private static final String strDefault2="clmdefault1";
	private static final String strPolicyDetail="policydetail";
	private static final String strPolicyClauses="policyClauses";
	private static final String strPolicyCoverage="policycoverage";
	private static final String strConfig="policyconfiguration";

	//Exception Message Identifier
	private static final String strOnlineForms="onlineforms";

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
		log.debug("Inside the doDefault method of ClaimAwaitingApprove");
			setLinks(request);
			DynaActionForm frmClaimAwaitingApprove =(DynaActionForm)form;
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			String strActiveLink=TTKCommon.getActiveLink(request);
			TableData tableData =TTKCommon.getTableData(request);//get the table data from session if exists
			tableData = new TableData();//create new table data object
			//create the required grid table
			tableData.createTableInfo("AwaitingClaimsTable",new ArrayList());
		
			//((Column)((ArrayList)tableData.getTitle()).get(1)).setVisibility(true);
			request.getSession().setAttribute("tableData",tableData);
			//make query to get user group to load to combo box
			//reset the form data
			((DynaActionForm)form).initialize(mapping);
		/*		((DynaActionForm)form).set("sEnrollmentType","COR");*/
	
		frmClaimAwaitingApprove.set("sTtkBranch",strDefaultBranchID);
		frmClaimAwaitingApprove.set("switchType","CLM");
		request.getSession().setAttribute("sTtkBranch", strDefaultBranchID);
		
			request.getSession().setAttribute("frmClaimAwaitingApprove",frmClaimAwaitingApprove);
		strStatus="claimssearch";
			return mapping.findForward(strStatus);
			//return this.getForward(strStatus, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strOnlineForms));
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
			log.debug("Inside the doSearch method of AdminPoliciesAction");
			setLinks(request);
			DynaActionForm frmClaimAwaitingApprove =(DynaActionForm)form;
			frmClaimAwaitingApprove.set("caption", "Details");
			   String strSwitchType=TTKCommon.checkNull((String)frmClaimAwaitingApprove.get("switchType"));
			OnlineFeedbackManager feedbackManagerObject=this.getFeedbackManagerObject();
			TableData tableData =TTKCommon.getTableData(request);
			ArrayList alPolicyList=null;
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(strPageID.equals(""))
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					//return (mapping.findForward("claimssearch"));
					return this.getForward("claimssearch", mapping, request);
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				
				//create the required grid table	//
				  if(strSwitchType.equalsIgnoreCase("CLM"))
				  {
					  tableData.createTableInfo("AwaitingClaimsTable",null);
						//((Column)((ArrayList)tableData.getTitle()).get(1)).setVisibility(true);
			      }
				  else if(strSwitchType.equalsIgnoreCase("PRE")){
					  //((Column)((ArrayList)tableData.getTitle()).get(2)).setVisibility(true);
					  tableData.createTableInfo("AwaitingPreAuthTable",null);
				  }       
				tableData.setSearchData(this.populateSearchCriteria(frmClaimAwaitingApprove,request,strSwitchType));
				tableData.modifySearchData("search");
			}//end of else
			
			ArrayList  alClaimList = feedbackManagerObject.getClaimList(tableData.getSearchData(),strSwitchType);
			tableData.setData(alClaimList,"search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			request.getSession().setAttribute("frmClaimAwaitingApprove",frmClaimAwaitingApprove);					
			
		/*	//get the session bean from the bean pool for each excecuting threadl
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			//get the table data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			ArrayList alPolicyList=null;
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(strPageID.equals(""))
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strDefault));
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("PoliciesTable",null);
				tableData.setSearchData(this.populateSearchCriteria(frmClaimAwaitingApprove,request));
				tableData.modifySearchData("search");
			}//end of else
			//call the DAO to get the records
			alPolicyList = productpolicyObject.getPolicyList(tableData.getSearchData());
			tableData.setData(alPolicyList,"search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			request.getSession().setAttribute("frmPoliciesSearch",frmClaimAwaitingApprove)*/;
			
			
			return this.getForward("claimssearch", mapping, request);

			//return this.getForward(strDefault, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strOnlineForms));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
     try{
                       log.debug("Inside the  method of ");
                      
                        setLinks(request);
                        BajajAlliannzFormVO bajajAlliannzFormVO=null;
                        Long lngSeQID=null;
                        String strForwardPath="";
                        DynaActionForm frmSaveClaimAwaitingApprove =(DynaActionForm)form;
                        String strSwitchType;
                        OnlineFeedbackManager feedbackManagerObject=this.getFeedbackManagerObject();
                    	TableData tableData =TTKCommon.getTableData(request);
                    	String strRowNum=(String)request.getParameter("rownum");
                    	String strInsremarks=(String)request.getParameter("insremarks");
                    	String strTab=(String)request.getParameter("tab");
                    	String strMessage="";
                    	log.info("strInsremarks:::::::::"+strInsremarks);
                        if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
            			{  
                        	strMessage=(strTab.equalsIgnoreCase("Claims"))?"error.Claims.required":"error.PreAuthorization.required";
                        	 request.getSession().setAttribute("showYN","N");
            				//reset the form data
                        	((DynaActionForm)form).initialize(mapping);
                        	
            			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
            			///edit flow
                      //TTKCommon.checkNull((String)frmClaimAwaitingApprove.get("switchType"));
                    
                    	if(!(TTKCommon.checkNull(strRowNum).equals("")))
            			{
                    	 bajajAlliannzFormVO=(BajajAlliannzFormVO)((ArrayList)tableData.getData()).get(Integer.parseInt(TTKCommon.checkNull(strRowNum)));
                    	 strSwitchType=bajajAlliannzFormVO.getSwitchType();
                    	 //tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
                    	if(strSwitchType.equalsIgnoreCase("CLM"))
                    	{
                    	 lngSeQID= bajajAlliannzFormVO.getClaimSeqID();
                    	 strForwardPath=strDefault2;
                    	}
                    	else if(strSwitchType.equalsIgnoreCase("PRE"))
                    	{
                    		lngSeQID= bajajAlliannzFormVO.getPreAuthSeqID();
                    		strForwardPath=strDefault1;
                    	}
                    	//ArrayList alPolicyList=null;
                    	 
                    	frmSaveClaimAwaitingApprove.set("caption", "Check");
                        ClaimFormVO claimFormVO= (ClaimFormVO)feedbackManagerObject.getClaimXmlData(lngSeQID,strSwitchType);
                      
                        if(strSwitchType.equalsIgnoreCase("CLM"))
                    	{
                        claimFormVO.setSeqID(bajajAlliannzFormVO.getClaimSeqID());
                    	}
                    	else if(strSwitchType.equalsIgnoreCase("PRE"))
                    	{
                    		claimFormVO.setSeqID(bajajAlliannzFormVO.getPreAuthSeqID());
                    	}
                    
                        claimFormVO.setAllowYN(bajajAlliannzFormVO.getAllowedYN());
                        claimFormVO.setSwitchType(bajajAlliannzFormVO.getSwitchType());
                    
                        bajajAlliannzFormVO.setClaimFormVO(claimFormVO);
                        request.getSession().setAttribute("ClaimXmlDocument",claimFormVO.getClaimPreauthDocument());
                        frmSaveClaimAwaitingApprove = (DynaActionForm)FormUtils.setFormValues("frmSaveClaimAwaitingApprove",claimFormVO,this,mapping,request);
                      //  frmAwaitingApprovePage.set("caption","Details");
                   
                    //   request.getSession().setAttribute("reportSeqID",bajajAlliannzFormVO.getClaimSeqID());
                       request.getSession().setAttribute("showYN","Y");
                       this.documentViewer(request,bajajAlliannzFormVO);
    				}
                    else
            			{
                    	 request.getSession().setAttribute("showYN","N");
                    	  request.getSession().setAttribute("ClaimXmlDocument",null);
                    		TTKException expTTK = new TTKException();
            				expTTK.setMessage(strMessage);
            				throw expTTK;
            			}//end of else	
                    	request.getSession().setAttribute("strSwitchType",strSwitchType);
						request.getSession().setAttribute("frmSaveClaimAwaitingApprove",frmSaveClaimAwaitingApprove);
						return this.getForward(strForwardPath, mapping, request);
                         
          }//end of try
           catch(TTKException expTTK) {
        	 
                        return this.processExceptions(request, mapping, expTTK);
                                }//end of catch(TTKException expTTK)
           catch(Exception exp)
                              {
                             return this.processExceptions(request, mapping, new TTKException(exp,strOnlineForms));
                               }//end of catch(Exception exp)
}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
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
			log.debug("Inside the doBackward method of AdminPoliciesAction");
			setLinks(request);
			DynaActionForm frmClaimAwaitingApprove =(DynaActionForm)form;
			//get the session bean from the bean pool for each excecuting threadl
			 String strSwitchType=TTKCommon.checkNull((String)frmClaimAwaitingApprove.get("switchType"));
			OnlineFeedbackManager feedbackManagerObject=this.getFeedbackManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			//modify the search data
			tableData.modifySearchData(strBackward);
			ArrayList  alClaimList = feedbackManagerObject.getClaimList(tableData.getSearchData(),strSwitchType);
			tableData.setData(alClaimList, strBackward);
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);

			return (mapping.findForward("claimssearch"));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strOnlineForms));
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
			log.debug("Inside the doForward method of AdminPoliciesAction");
			setLinks(request);
			DynaActionForm frmClaimAwaitingApprove =(DynaActionForm)form;
			//get the session bean from the bean pool for each excecuting threadl
			 String strSwitchType=TTKCommon.checkNull((String)frmClaimAwaitingApprove.get("switchType"));
			//get the session bean from the bean pool for each excecuting threadl
			OnlineFeedbackManager feedbackManagerObject=this.getFeedbackManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			//modify the search data
			tableData.modifySearchData(strForward);
			ArrayList  alClaimList = feedbackManagerObject.getClaimList(tableData.getSearchData(),strSwitchType);
			//set the table data object to session
			tableData.setData(alClaimList, strForward);
			//finally return to the grid screen
			request.getSession().setAttribute("tableData",tableData);
			return (mapping.findForward("claimssearch"));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strOnlineForms));
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
			log.debug("Inside the doCopyToWebBoard method of AdminPoliciesAction");
			setLinks(request);
			this.populateWebBoard(request);
			return this.getForward(strStatus, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strOnlineForms));
		}//end of catch(Exception exp)
	}//end of CopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)


	
	/**
	 * This method is used to add/update the record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												  HttpServletResponse response) throws Exception{
		try{
			setLinks(request);		
			OnlineFeedbackManager feedbackManagerObject=this.getFeedbackManagerObject();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			
			String strForwardPath=null;
			DynaActionForm frmSaveClaimAwaitingApprove = (DynaActionForm)form;
			 ClaimFormVO claimFormVO=null;
			 ClaimFormVO claimFormVO1=null;
			//frmSaveClaimAwaitingApprove.set("caption",buildCaption(request));
			claimFormVO =(ClaimFormVO)FormUtils.getFormValues(frmSaveClaimAwaitingApprove, "frmSaveClaimAwaitingApprove",this, mapping, request);
			StringBuffer strConcatenatedSeqID=new StringBuffer();
			strConcatenatedSeqID.append("|").append(claimFormVO.getSeqID()).append("|");
			//String strApproveRejectStatus=claimFormVO.getClaimApprovlaStatus();
			String strSwitchType=claimFormVO.getSwitchType();
			//claimFormVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			String strRemarks=(String)request.getParameter("insurerRemarks");
			//String strApproveRejectStatus=(String)request.getParameter("insApprovalStatus");
			String strApproveRejectStatus=(String)frmSaveClaimAwaitingApprove.get("insApprovalStatus");
			Long lngUserID=TTKCommon.getUserSeqId(request);//1274A
			int iResult = feedbackManagerObject. saveInsurerApproveRejectStatus(strConcatenatedSeqID.toString(), strApproveRejectStatus, strRemarks, strSwitchType,lngUserID);
			if(iResult>0)
			{
					request.setAttribute("updated","message.saved");
					claimFormVO1 = (ClaimFormVO) feedbackManagerObject.getClaimXmlData(claimFormVO.getSeqID(), strSwitchType);
					
					frmSaveClaimAwaitingApprove = (DynaActionForm)FormUtils.setFormValues("frmSaveClaimAwaitingApprove",claimFormVO1, this, mapping, request);
					request.getSession().setAttribute("ClaimXmlDocument",claimFormVO1.getClaimPreauthDocument());
					 request.getSession().setAttribute("showYN","Y");
			}//end of if(iResult>0)
			//frmSaveClaimAwaitingApprove.set("caption",buildCaption(request));
			 //request.getSession().setAttribute("insApprovalStatus",);
			request.getSession().setAttribute("frmSaveClaimAwaitingApprove",frmSaveClaimAwaitingApprove);
		//	request.getSession().setAttribute("frmSaveClaimAwaitingApprove",frmSaveClaimAwaitingApprove);
			
			
			if(strSwitchType.equalsIgnoreCase("CLM"))
        	{
				strForwardPath=strDefault2;
        	}
        	else if(strSwitchType.equalsIgnoreCase("PRE"))
        	{
        		strForwardPath=strDefault1;
        	}
			//frmSaveClaimAwaitingApprove.set("insApprovalStatus",claimFormVO1.getInsApprovalStatus());
			
			return mapping.findForward(strForwardPath);
			
			
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strOnlineForms));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	public ActionForward doSetBulkStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws TTKException{
		
		try{
			log.debug("Inside the doSearch method of AdminPoliciesAction");

		setLinks(request);
		DynaActionForm frmClaimAwaitingApprove =(DynaActionForm)form;
		String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
				request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
		String strActiveTab=TTKCommon.getActiveTab(request);
		frmClaimAwaitingApprove.set("caption", "Details");
		String strRemarks=TTKCommon.checkNull((String)frmClaimAwaitingApprove.get("sRemarks"));
		 String strSwitchType=TTKCommon.checkNull((String)frmClaimAwaitingApprove.get("switchType"));
		String  strtStatus=TTKCommon.checkNull(request.getParameter("setStatus"));
		TableData  tableData =TTKCommon.getTableData(request);
		OnlineFeedbackManager feedbackManagerObject=this.getFeedbackManagerObject();
		String strConcatenatedSeqID=getConcatenatedSeqID(request, tableData,strSwitchType);
		String strActiveLink=TTKCommon.getActiveLink(request);
		Long lngUserID=TTKCommon.getUserSeqId(request);//1274A  
		int iResult=feedbackManagerObject.saveInsurerApproveRejectStatus(strConcatenatedSeqID, strtStatus, strRemarks, strSwitchType,lngUserID);   
		if(iResult>0)	{
				request.setAttribute("updated","message.saved");
				ArrayList  alClaimList = feedbackManagerObject.getClaimList((ArrayList)tableData.getSearchData(),strSwitchType);
				tableData.setData(alClaimList,"search");
		//((DynaActionForm)form).initialize(mapping);
		}//end of if(iResult>0)
		frmClaimAwaitingApprove.set("sTtkBranch",strDefaultBranchID);
		frmClaimAwaitingApprove.set("switchType",strSwitchType);
		frmClaimAwaitingApprove.set("sRemarks"," ");
		//frmClaimAwaitingApprove.set("switchType","CLM");
		request.setAttribute("sRemarks", " ");
		request.getSession().setAttribute("sTtkBranch", strDefaultBranchID);
		return mapping.findForward("claimssearch");
	}//end of try
	catch(TTKException expTTK)
	{
		if(expTTK.getMessage().equalsIgnoreCase("error.security.unauthorized"))
	{
		 ActionMessages actionMessages = new ActionMessages();
         ActionMessage actionMessage = new ActionMessage("error.security.unauthorized");
         actionMessages.add("global.error",actionMessage);
         saveErrors(request,actionMessages);
         return mapping.findForward("failure");
	}
		return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp,strOnlineForms));
	}//end of catch(Exception exp)
	}//end of doSetBulkStatus
  
	
    /**Returns the String of concatenated string of contact_seq_id delimeted by '|'.
	 * @param HttpServletRequest
	 * @param TableData
	 * @return String
	 */
	private String getConcatenatedSeqID(HttpServletRequest request,TableData tableData,String strSwitchType) {
		StringBuffer sbfConcatenatedSeqId=new StringBuffer();
		String strChOpt[]=request.getParameterValues("chkopt");
		if((strChOpt!=null)&& strChOpt.length!=0)
		{    
			sbfConcatenatedSeqId.append("|");
			for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
			{
				if(strChOpt[iCounter]!=null)
				{
					if(iCounter==0)
					{
						if(strSwitchType.equalsIgnoreCase("CLM")){
						sbfConcatenatedSeqId.append(String.valueOf(((BajajAlliannzFormVO)tableData.getRowInfo(
																Integer.parseInt(strChOpt[iCounter]))).getClaimSeqID()));
						}
						else if(strSwitchType.equalsIgnoreCase("PRE"))
						{
							sbfConcatenatedSeqId.append(String.valueOf(((BajajAlliannzFormVO)tableData.getRowInfo(
									Integer.parseInt(strChOpt[iCounter]))).getPreAuthSeqID()));
						}
					
					}//end of if(iCounter==0)
					else
					{
						if(strSwitchType.equalsIgnoreCase("CLM")){
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((BajajAlliannzFormVO)tableData.getRowInfo(Integer.
																		parseInt(strChOpt[iCounter]))).getClaimSeqID()));
						}
						else if(strSwitchType.equalsIgnoreCase("PRE"))
						{
							sbfConcatenatedSeqId.append("|").append(String.valueOf(((BajajAlliannzFormVO)tableData.getRowInfo(Integer.
									parseInt(strChOpt[iCounter]))).getPreAuthSeqID()));
						}
						
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		sbfConcatenatedSeqId.append("|");
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		//
		return sbfConcatenatedSeqId.toString();
	} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)
	
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
			log.debug("Inside the doChangeWebBoard method of AdminPoliciesAction");
			setLinks(request);
			DynaActionForm frmPolicies =(DynaActionForm)form;
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			//if web board id is found, set it as current web board id
			frmPolicies.initialize(mapping);
			Long lngProductPolicySeqId=TTKCommon.getWebBoardId(request);
			//get the Policy details from the Dao object
			PolicyDetailVO policyDetailVO=productpolicyObject.getPolicyDetail(lngProductPolicySeqId,
																   TTKCommon.getUserSeqId(request));
			ArrayList alUserGroup=new ArrayList();
			//make query to get user group to load to combo box
			if(policyDetailVO.getOfficeSeqID()!=null)
			{
				alUserGroup=productpolicyObject.getGroup(policyDetailVO.getOfficeSeqID());
			}//end of  if(policyDetailVO.getOfficeSeqID()!=null)
			request.getSession().setAttribute("alUserGroup",alUserGroup);
			frmPolicies = (DynaActionForm)FormUtils.setFormValues("frmPoliciesEdit",policyDetailVO,
																			 this,mapping,request);
			//isBufferAllowedSaved used to keep the Buffer Allowed checkbox readonly
			//if it is checked and saved before.

			//1216B cR
			frmPolicies.set("memberBuffer",policyDetailVO.getMemberBufferYN());
			//1216B cR
			frmPolicies.set("isBufferAllowedSaved",policyDetailVO.getBufferRecYN());
			frmPolicies.set("isBrokerrAllowedSaved",policyDetailVO.getBrokerRecYN());
			frmPolicies.set("stopPreAuth",policyDetailVO.getStopPreAuthsYN());
			frmPolicies.set("stopClaim",policyDetailVO.getStopClaimsYN());
			frmPolicies.set("opdClaim",policyDetailVO.getopdClaimsYN());//KOC 1286 for OPD Benefit
			frmPolicies.set("cashBenefit",policyDetailVO.getCashBenefitYN());// KOC 1270 for hospital cash benefit
			frmPolicies.set("convCashBenefit",policyDetailVO.getConvCashBenefitYN());// KOC 1270 for hospital cash benefit
			request.getSession().setAttribute("frmPoliciesEdit",frmPolicies);
			this.documentViewer(request,policyDetailVO);
			return this.getForward(strPolicyDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strOnlineForms));
		}//end of catch(Exception exp)
	}//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * This method is used to generate a xl sheet.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doGenerateXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doGenerateXL method of AdminPoliciesAction");
			setLinks(request);
			DynaActionForm frmPolicies =(DynaActionForm)form;
			PolicyDetailVO policyDetailVO =new PolicyDetailVO();
			policyDetailVO= (PolicyDetailVO)FormUtils.getFormValues(frmPolicies,this,mapping,request);
			String strGenerateXL="/ReportsAction.do?mode=doGenerateAdminXL&parameter="+policyDetailVO.getPolicySeqID()
										  +"&fileName=generalreports/RenewalMembersXL.jrxml&reportID=GenRenMemXLs&reportType=EXL";
			request.setAttribute("GenerateXL",strGenerateXL);
			return this.getForward(strPolicyDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strOnlineForms));
		}//end of catch(Exception exp)
	}//end of doGenerateXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
			log.debug("Inside the doConfiguration method of AdminPoliciesAction");
			setLinks(request);
			StringBuffer sbfCaption= new StringBuffer();
			DynaActionForm frmPoliciesEdit = (DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
			sbfCaption.append("[").append(frmPoliciesEdit.get("companyName")).append("]");
			sbfCaption.append("[").append(frmPoliciesEdit.get("policyNbr")).append("]");
			request.getSession().setAttribute("ConfigurationTitle", sbfCaption.toString());
			return this.getForward(strConfig, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strStatus));
		}//end of catch(Exception exp)
	}//end of doConfiguration(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//	HttpServletResponse response)

	/**
	 * This method is used to reload the screen when the reset button is pressed.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												   HttpServletResponse response) throws Exception{
		log.debug("Inside the doReset method of AdminPoliciesAction");
		return doView(mapping,form,request,response);
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	 /**
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmCodingClaimsList formbean which contains the search fields
     * @param request HttpServletRequest
     * @return ArrayList contains search parameters
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmClaimAwaitingApprove,HttpServletRequest request,String strSwitchType)
    {  //build the column names along with their values to be searched
    	ArrayList<Object> alSearchParams = new ArrayList<Object>();
    	alSearchParams.add(TTKCommon.getUserSeqId(request));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimAwaitingApprove.getString("sEnrollmentId")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimAwaitingApprove.getString("sPolicyNumber")));
    	
    	if(strSwitchType.equalsIgnoreCase("CLM"))
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimAwaitingApprove.getString("sClaimNumber")));
    	else if(strSwitchType.equalsIgnoreCase("PRE"))
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimAwaitingApprove.getString("sPreAuthNumber")));
    	alSearchParams.add((String)frmClaimAwaitingApprove.getString("sFrmDate"));
    	alSearchParams.add((String)frmClaimAwaitingApprove.getString("sToDate"));
      	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimAwaitingApprove.getString("sClaimStatus")));
       	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimAwaitingApprove.getString("sTtkBranch")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimAwaitingApprove.getString("sInsuranceRODO")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimAwaitingApprove.getString("sOperator")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimAwaitingApprove.getString("sClaimRecommendedAmount")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmClaimAwaitingApprove.getString("sTPAStatus"))); //denial process
    	
    	return alSearchParams;
    	
    	
    	
    }//end of populateSearchCriteria(DynaActionForm frmClaimsList,HttpServletRequest request)

	
	
	
	/**
	 * This method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPolicyList current instance of form bean
	 * @param request HttpServletRequest object
	 * @return alSearchObjects ArrayList of search params
	 * @throws TTKException
	 
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmPolicyList,
							HttpServletRequest request)
	{
		ArrayList<Object> alSearchParam=new ArrayList<Object>();
		String strSearchObjects[]={(TTKCommon.replaceSingleQots(frmPolicyList.getString("sPolicyNO"))),frmPolicyList.
			       getString("sEnrollmentType"),frmPolicyList.getString("sInsuranceCompany"),frmPolicyList.getString(
			             "sTTKBranch"),frmPolicyList.getString("sStatus"),(TTKCommon.replaceSingleQots(frmPolicyList.
			    	   getString("sGroupID"))),(TTKCommon.replaceSingleQots(frmPolicyList.getString("sGroupName")))};
		alSearchParam.add(strSearchObjects);
		alSearchParam.add(TTKCommon.getUserSeqId(request));
		return alSearchParam;
	}//end of populateSearchCriteria(DynaActionForm frmPolicyList,HttpServletRequest request)throws TTKException
*/
	/**
	 * Populates the web board in the session with the selected items in the grid
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 */
	private void populateWebBoard(HttpServletRequest request)
	{
		String[] strChk = request.getParameterValues("chkopt");
		TableData tableData = (TableData)request.getSession().getAttribute("tableData");
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		CacheObject cacheObject = null;
		if(strChk!=null&&strChk.length!=0)
		{
			//loop through to populate delete sequence id's and get the value from session for the matching
			//check box value
			for(int i=0; i<strChk.length;i++)
			{
				cacheObject = new CacheObject();
				cacheObject.setCacheId(""+((PolicyVO)tableData.getData().get(Integer.parseInt(strChk[i]))).
																					 getProdPolicySeqID());
				cacheObject.setCacheDesc(((PolicyVO)tableData.getData().get(Integer.parseInt(strChk[i]))).
																						  getPolicyNbr());
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
	 * @param  productVO ProductVO object which contains the information of the products
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 */
	private void addToWebBoard(PolicyVO policyVO, HttpServletRequest request)
	{
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		CacheObject cacheObject = new CacheObject();
		cacheObject.setCacheId(String.valueOf(policyVO.getProdPolicySeqID()));
		cacheObject.setCacheDesc(policyVO.getPolicyNbr());
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		alCacheObject.add(cacheObject);
		//if the object(s) are added to the web board, set the current web board id
		//if(toolbar.getWebBoard().addToWebBoardList(alCacheObject))
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
	}//end of addToWebBoard(HospitalVO hospitalVO, HttpServletRequest request)

	/**
	 * Returns the GroupRegistrationManager session object for invoking methods on it.
	 * @return GroupRegistrationManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ProductPolicyManager getProductPolicyManager() throws TTKException
	{
		ProductPolicyManager productPolicyManager = null;
		try
		{
			if(productPolicyManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyManager = (ProductPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProductPolicyManagerBean!com.ttk.business.administration.ProductPolicyManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strPolicyDetail);
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManager()

	
	/**
	 * Returns the FeedbackManager session object for invoking methods on it.
	 * @return FeedbackManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private OnlineFeedbackManager getFeedbackManagerObject() throws TTKException
	{
		OnlineFeedbackManager feedbackManager = null;
		try
		{
			if(feedbackManager == null)
			{
				InitialContext ctx = new InitialContext();
				feedbackManager = (OnlineFeedbackManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineFeedbackManagerBean!com.ttk.business.onlineforms.OnlineFeedbackManager");
			}//end of if(feedbackManager == null)
		}//end of try
		
		catch(Exception exp)
		{
			throw new TTKException(exp,strOnlineForms);
		}//end of catch
		return feedbackManager;
	}//end getFeedbackManagerObject()
	
	
	
	/**
	 * This method for document viewer information
	 * @param request HttpServletRequest object which contains hospital information.
	 * @param policyDetailVO PolicyDetailVO object which contains policy information.
	 * @exception throws TTKException
	 */
	private void documentViewer(HttpServletRequest request,PolicyDetailVO policyDetailVO)
	{
		//Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<String> alDocviewParams = new ArrayList<String>();
		alDocviewParams.add("leftlink=Enrollment");
		alDocviewParams.add("policy_number="+policyDetailVO.getPolicyNbr());
		alDocviewParams.add("dms_reference_number="+policyDetailVO.getDMSRefID());

		if(request.getSession().getAttribute("toolbar")!=null){
			((Toolbar)request.getSession().getAttribute("toolbar")).setDocViewParams(alDocviewParams);
		}//end of if(request.getSession().getAttribute("toolbar")!=null)
	}//end of documentViewer(HttpServletRequest request,PolicyDetailVO policyDetailVO)

	
	/**
	 * This method is used to navigate to previous screen when close  button is clicked.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doClose(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response) throws TTKException
	{
		try
		{
			setLinks(request);
			String strForward="close";
			DynaActionForm frmClaimAwaitingApprove =(DynaActionForm)form;
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			//request.getSession().removeAttribute("frmConfigCopay");
			//return this.getForward(strForward, mapping, request);
			String strSwitchType=(String) request.getSession().getAttribute("strSwitchType");
			frmClaimAwaitingApprove.set("sTtkBranch",strDefaultBranchID);
			frmClaimAwaitingApprove.set("switchType",strSwitchType);
			request.getSession().setAttribute("sTtkBranch", strDefaultBranchID);
			  request.getSession().setAttribute("ClaimXmlDocument",null);
				request.getSession().setAttribute("frmClaimAwaitingApprove",frmClaimAwaitingApprove);
			strStatus="claimssearch";
		//	return mapping.findForward("claimssearch");
		return doSearch(mapping,form,request,response);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, "Configuration"));
		}//end of catch(Exception exp)
	}// end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method for document viewer information
	 * @param request HttpServletRequest object which contains Pre-Authorization information.
	 * @param preAuthDetailVO PreAuthDetailVO object which contains Pre-Authorization information.
	 * @exception throws TTKException
	 */
	private void documentViewer(HttpServletRequest request,BajajAlliannzFormVO preAuthDetailVO) throws TTKException
	{
		ArrayList<String> alDocviewParams = new ArrayList<String>();
		alDocviewParams.add("leftlink="+TTKCommon.getActiveTab(request));
	
		
			//alDocviewParams.add("claim_number="+TTKCommon.getWebBoardDesc(request));
			alDocviewParams.add("claimno="+preAuthDetailVO.getPreAuthNo());
			alDocviewParams.add("dms_reference_number="+preAuthDetailVO.getPreAuthNo());
			alDocviewParams.add("userid="+TTKCommon.getUserID(request));
			alDocviewParams.add("roleid=INSURANCE");
		
		if(request.getSession().getAttribute("toolbar")!=null)
		{
			((Toolbar)request.getSession().getAttribute("toolbar")).setDocViewParams(alDocviewParams);
		}//end of if(request.getSession().getAttribute("toolbar")!=null)
	}//end of documentViewer(HttpServletRequest request,BajajAlliannzFormVO bajajAlliannzFormVO)
	
	/**
     * This method is used to Switch between Enrollment flow and Endorsement flow
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSwitchTo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	log.info("Inside the doSwitchTo method of  action");
            DynaActionForm frmClaimAwaitingApprove=(DynaActionForm)form;  //get the DynaActionForm instance
            TableData tableData = TTKCommon.getTableData(request);
            String strSwitchType=TTKCommon.checkNull((String)frmClaimAwaitingApprove.get("switchType"));
           
            this.setLinks(request,strSwitchType);
             tableData = new TableData();
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            //initialize the formbean when user switches the mode
            frmClaimAwaitingApprove.initialize(mapping);
            //frmPolicyList.set("sTtkBranch",strDefaultBranchID);
            frmClaimAwaitingApprove.set("switchType",strSwitchType);
            request.getSession().setAttribute("switchType",strSwitchType);
            
          
			//create the required grid table
			  if(strSwitchType.equalsIgnoreCase("CLM")){
				  tableData.createTableInfo("AwaitingClaimsTable",null);
				//((Column)((ArrayList)tableData.getTitle()).get(1)).setVisibility(true);
			  }
					
			  else if(strSwitchType.equalsIgnoreCase("PRE")){
				//  ((Column)((ArrayList)tableData.getTitle()).get(2)).setVisibility(true);
				  tableData.createTableInfo("AwaitingPreAuthTable",null);
			  }
            //build the caption
           // frmClaimAwaitingApprove.set("caption",buildCaption(strSwitchType));
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward("claimssearch", mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp,"PreAuthClaimApprove"));
        }//end of catch(Exception exp)
    }//end of doSwitchTo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
}//end of class ClaimAwaitingApprove
