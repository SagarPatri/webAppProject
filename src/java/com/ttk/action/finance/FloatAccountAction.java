/**
 * @ (#) FloatAccountAction.java Jun 10, 2006
 * Project 	     : TTK HealthCare Services
 * File          : FloatAccountAction.java
 * Author        : Raghavendra T M
 * Company       : Span Systems Corporation
 * Date Created  : Jun 10, 2006
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.finance;

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
import com.ttk.business.finance.FloatAccountManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.empanelment.InsuranceVO;
import com.ttk.dto.finance.AssocGroupVO;
import com.ttk.dto.finance.FloatAccountDetailVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for displaying the Float Account Details.
 * This class also provides option for Adding and Updating the details.
 */
public class FloatAccountAction extends TTKAction  {
	private static Logger log = Logger.getLogger( FloatAccountAction.class ); // Getting Logger for this Class file
	//  Action mapping forwards
	private static final String strFloatAccDetails="floataccdetails";
	private static final String strSaveFloatAccDetails="savefloataccdetails";
	private static final String strChangeCorporate="changecorporate";
	private static final String strChangeOffice="changeoffice";
	private static final String strSelectBankAccount="selectbankaccount";
	private static final String strAssociatedCorporate="associatedcorporate";

	//  Exception Message Identifier
    private static final String strFloatAcct="Float Account Details";
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
    public ActionForward doViewFloatAccount(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside FloatAccountAction doViewFloatAccount");
    		FloatAccountDetailVO floatAccountDetailVO = null;			
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		StringBuffer strCaption = new StringBuffer();
    		String strEditMode = "";
    		if(TTKCommon.getWebBoardId(request)==null)
    		{
    			TTKException expTTK = new TTKException();
    			expTTK.setMessage("error.floatno.required");
    			throw expTTK;
    		}//end of if(TTKCommon.getWebBoardId(request)==null)
    		// setting the floatAcc details values based on floatAcctSeqID from form/rownum to the appropriate modes
    	
    		floatAccountDetailVO=floatAccountManagerObject.getFloatAccountDetail(TTKCommon.getWebBoardId(request),
    				new Long(TTKCommon.getUserSeqId(request).toString()));
    		DynaActionForm  frmFloatAccDetails = (DynaActionForm)FormUtils.setFormValues("frmFloatAccDetails",
    				floatAccountDetailVO, this, mapping, request);
    		strCaption.append(" - ["+floatAccountDetailVO.getFloatNo()+"]");
    		frmFloatAccDetails.set("caption",strCaption.toString());
    		strEditMode = "Edit";
    		frmFloatAccDetails.set("editmode",strEditMode);
 
    		if(floatAccountDetailVO.getCurrentBalance()==null||floatAccountDetailVO.getCurrentBalance().compareTo(TTKCommon.getBigDecimal("0"))==0)
    		{
    			frmFloatAccDetails.set("currentBalance","0.00");
    		}//end of if(floatAccountDetailVO.getCurrentBalance().compareTo(TTKCommon.getBigDecimal("0"))==0)
   		
    		frmFloatAccDetails.set("directBilling",floatAccountDetailVO.getDirectBillingYN());
    		
    		frmFloatAccDetails.set("status",floatAccountDetailVO.getStatusDesc());
    		frmFloatAccDetails.set("alAssocGrpList",floatAccountDetailVO.getAssocGrpList());
    		request.getSession().setAttribute("frmFloatAccDetails",frmFloatAccDetails);		
   		
    		return this.getForward(strFloatAccDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doViewFloatAccount(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
    	log.debug("Inside FloatAccountAction doChangeWebBoard");
    	return doViewFloatAccount(mapping,form,request,response);
    }//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    /**
     * This method is called from the struts framework.
     * This method is used to navigate to detail screen to add a record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    						   HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside FloatAccountAction doAdd");
    		FloatAccountDetailVO floatAccountDetailVO = null;
    		floatAccountDetailVO = new FloatAccountDetailVO();
    		StringBuffer strCaption = new StringBuffer();
    		String strStatus="ASA";
    		DynaActionForm  frmFloatAccDetails = (DynaActionForm)FormUtils.setFormValues("frmFloatAccDetails",
     																  floatAccountDetailVO, this, mapping, request);
    		frmFloatAccDetails.set("floatType","FTR");
    		String strEditMode = "Add";
    		strCaption.append(" - Add");
    		frmFloatAccDetails.set("caption",strCaption.toString());
    		frmFloatAccDetails.set("statusDesc",strStatus);
    		frmFloatAccDetails.set("editmode",strEditMode);
    		request.getSession().setAttribute("frmFloatAccDetails",frmFloatAccDetails);
    		return this.getForward(strFloatAccDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside FloatAccountAction doSave");
    		DynaActionForm frmFloatAccDetails=(DynaActionForm)form;
    		FloatAccountDetailVO floatAccountDetailVO = null;
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		StringBuffer strCaption = new StringBuffer();
    		String strEditMode = "";
    		
    		floatAccountDetailVO=(FloatAccountDetailVO)FormUtils.getFormValues(frmFloatAccDetails,this, 
    																		   mapping, request);
   	
    		floatAccountDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
    		
    		
    		if("DBL".equals(TTKCommon.checkNull(frmFloatAccDetails.get("directBilling"))))
			{
    			floatAccountDetailVO.setDirectBillingYN("DBL");
			}
			else
			{
				floatAccountDetailVO.setDirectBillingYN("GBL");
			}
    		
    		
    		
    		
   		
    		Long iResult =floatAccountManagerObject.saveFloatAccount(floatAccountDetailVO);
    		if((iResult > 0))
    		{
    			//setting updated message in the request
    			if(floatAccountDetailVO.getFloatAcctSeqID()!=null)
    			{
    				//finally modify the web board details, if the account no. is changed
    				floatAccountDetailVO = floatAccountManagerObject.getFloatAccountDetail(iResult,
    																			  TTKCommon.getUserSeqId(request));
    				strCaption.append(" - ["+floatAccountDetailVO.getFloatNo()+"]");
   				
    				if(floatAccountDetailVO.getCurrentBalance()==null||floatAccountDetailVO.getCurrentBalance().compareTo(TTKCommon.getBigDecimal("0"))==0)
    				{
    					frmFloatAccDetails.set("currentBalance","0.00");
    				}//end of if(floatAccountDetailVO.getCurrentBalance().compareTo(TTKCommon.getBigDecimal("0"))==0)
    				request.setAttribute("updated","message.savedSuccessfully");
    				request.setAttribute("cacheId", ""+iResult);
    				request.setAttribute("cacheDesc",floatAccountDetailVO.getFloatAcctName());
    				//finally modify the web board details, if the account no. is changed
    				TTKCommon.modifyWebBoardId(request);
    			}// end of if(!(TTKCommon.checkNull((String)frmFloatAccDetails.get("floatAcctSeqID")).equals("")))
    			else
    			{
    				floatAccountDetailVO.setFloatAcctSeqID(iResult);
    				strCaption.append(" - Edit");
    				floatAccountDetailVO = floatAccountManagerObject.getFloatAccountDetail(iResult,
    																			   TTKCommon.getUserSeqId(request));
    				this.addToWebBoard(floatAccountDetailVO,request);
    				request.setAttribute("updated","message.addedSuccessfully");
    			}//end of else
    		}//end of if(iResult!=0)
    		
    		DynaActionForm  frmFloatAccDetail = (DynaActionForm)FormUtils.setFormValues("frmFloatAccDetails",
    																  floatAccountDetailVO, this, mapping, request);
    		if((TTKCommon.checkNull((String)frmFloatAccDetail.get("floatAcctSeqID")).equals("")))
    		{
    			frmFloatAccDetail.set("floatType","FTR");
    		}//end of if((TTKCommon.checkNull((String)frmFloatAccDetail.get("floatAcctSeqID")).equals("")))
    		else
    		{
    			frmFloatAccDetail.set("floatType",floatAccountDetailVO.getFloatType());
    			frmFloatAccDetail.set("status",floatAccountDetailVO.getStatusDesc());
    		}//end of else
    		
    		frmFloatAccDetail.set("directBilling",floatAccountDetailVO.getDirectBillingYN());
    		
    		frmFloatAccDetail.set("caption",strCaption.toString());
    		frmFloatAccDetail.set("editmode",strEditMode);
    		frmFloatAccDetail.set("currentBalance",frmFloatAccDetails.getString("currentBalance"));
    		frmFloatAccDetail.set("alAssocGrpList",floatAccountDetailVO.getAssocGrpList());
   
    		request.getSession().setAttribute("frmFloatAccDetails",frmFloatAccDetail);
    		return this.getForward(strSaveFloatAccDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doUpdate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    	try{
    		setLinks(request);
    		log.debug("Inside FloatAccountAction doReset");
    		DynaActionForm frmFloatAccDetail=(DynaActionForm)form;
    		FloatAccountDetailVO floatAccountDetailVO = null;
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		String strEditMode = "";
    		StringBuffer strCaption = new StringBuffer();
    		String strStatus="ASA";
    		floatAccountDetailVO = new FloatAccountDetailVO();
    		if(!(TTKCommon.checkNull(frmFloatAccDetail.getString("floatAcctSeqID")).equals("")))
    		{
    			//calling business layer to get the batch detail
    			floatAccountDetailVO = floatAccountManagerObject.getFloatAccountDetail(Long.parseLong
    														(frmFloatAccDetail.getString("floatAcctSeqID")),
    														new Long(TTKCommon.getUserSeqId(request).toString()));
    			strEditMode = "Edit";
    			if(floatAccountDetailVO.getCurrentBalance().compareTo(TTKCommon.getBigDecimal("0"))==0)
    				frmFloatAccDetail.set("currentBalance","0.00");
    		}//end of if(frmFloatAccDetail.get("floatAcctSeqID")!=null && !frmFloatAccDetail.get("floatAcctSeqID").equals(""))
    		else
    		{
    			strEditMode = "Add";
    			strCaption.append(" - Add");
    		}
    		DynaActionForm frmFloatAccDetails =(DynaActionForm)FormUtils.setFormValues("frmFloatAccDetails",
    																	floatAccountDetailVO, this,mapping,request);
    		// setting viewmode and caption in reset mode
    		if(!(TTKCommon.checkNull((String)frmFloatAccDetails.get("floatAcctSeqID")).equals("")))
    		{
    			strCaption.append(" - ["+floatAccountDetailVO.getFloatNo()+"]");
    			frmFloatAccDetails.set("floatType",floatAccountDetailVO.getFloatType());
    		}//end of if(!(TTKCommon.checkNull((String)frmFloatAccDetails.get("floatAcctSeqID")).equals("")))
    		else
    		{
    			frmFloatAccDetails.set("statusDesc",strStatus);
    			frmFloatAccDetails.set("floatType","FTR");
    			frmFloatAccDetails.set("status",floatAccountDetailVO.getStatusDesc());
    		}//end of else
    		frmFloatAccDetail.set("directBilling",floatAccountDetailVO.getDirectBillingYN());
    		frmFloatAccDetails.set("caption",strCaption.toString());
    		frmFloatAccDetails.set("editmode",strEditMode);
    		frmFloatAccDetails.set("currentBalance",frmFloatAccDetail.getString("currentBalance"));
    		request.getSession().setAttribute("frmFloatAccDetails",frmFloatAccDetails);
    		return this.getForward(strFloatAccDetails,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doSelectBankAccount(ActionMapping mapping,ActionForm form,
    									HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside FloatAccountAction doSelectBankAccount");
    		DynaActionForm frmGeneral=(DynaActionForm)form;
    		frmGeneral.set("frmChanged","changed");
    		return (mapping.findForward(strSelectBankAccount));
    	}
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doSelectBankAccount(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doCorporateChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		                               HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside FloatAccountAction doCorporateChange");    		    		
    		return mapping.findForward(strChangeCorporate);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doCorporateChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doOfficeChange(ActionMapping mapping,ActionForm form,
    		                            HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside FloatAccountAction doOfficeChange");
    		DynaActionForm frmGeneral=(DynaActionForm)form;
    		frmGeneral.set("frmChanged","changed");
    		return mapping.findForward(strChangeOffice);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strFloatAcct));
    	}//end of catch(Exception exp)
    }//end of doCorporateChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
	/**
	 * Adds the selected item to the web board and makes it as the selected item in the web board
	 * @param bankAccountVO BankAccountVO object which contain the information of bank account
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 */
	private void addToWebBoard(FloatAccountDetailVO floatAccountDetailVO, HttpServletRequest request)
	{
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		CacheObject cacheObject = new CacheObject();
		cacheObject.setCacheId(""+floatAccountDetailVO.getFloatAcctSeqID());
		cacheObject.setCacheDesc(floatAccountDetailVO.getFloatAcctName());
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		alCacheObject.add(cacheObject);
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
		//set weboardinvoked as true to avoid change of webboard id twice in same request
		request.setAttribute("webboardinvoked","true");
	}//addToWebBoard(FloatAccountDetailVO floatAccountDetailVO, HttpServletRequest request)
	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private FloatAccountManager getFloatAccountManagerObject() throws TTKException
	{
		FloatAccountManager floatAccountManager = null;
		try
		{
			if(floatAccountManager == null)
			{
				InitialContext ctx = new InitialContext();
				floatAccountManager = (FloatAccountManager) ctx.lookup("java:global/TTKServices/business.ejb3/FloatAccountManagerBean!com.ttk.business.finance.FloatAccountManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "PreAuthSupportManager");
		}//end of catch
		return floatAccountManager;
	}//end of getFloatAccountManagerObject()

	/**
     * This method is used to get the details of the associated corporate of a float account.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewAssociatedCorp(ActionMapping mapping,ActionForm form,
    		                            HttpServletRequest request,HttpServletResponse response) throws Exception
    {
    	log.debug("Inside FloatAccountAction doViewAssociatedCorp");
    	try{
    		setLinks(request);  
    		DynaActionForm frmFloatAccDetails=(DynaActionForm)form;    		
			TableData corporateData=null;
			ArrayList<Object> arAssociatedCorp = new ArrayList<Object>();
			FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
			arAssociatedCorp =  floatAccountManagerObject.getFloatAssocGrpList(Long.valueOf(frmFloatAccDetails.get("floatAcctSeqID").toString()));
			corporateData = new TableData();
			corporateData.createTableInfo("CorporateTable",arAssociatedCorp);
			if(!TTKCommon.isAuthorized(request, "Delete Corporate"))
			{
				corporateData.obj1.getTableHeader().remove(2);
			}//end of if(!TTKCommon.isAuthorized(request, "Monthly Remittance Details"))
			request.getSession().setAttribute("CorporateTable",corporateData);				
			frmFloatAccDetails.set("policyNo",null);		
			frmFloatAccDetails.set("groupID",null);
			frmFloatAccDetails.set("groupName",null);
			frmFloatAccDetails.set("groupRegnSeqID",null);
			return this.getForward(strAssociatedCorporate, mapping, request);
		}//end of try
		catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strFloatAcct));
    	}//end of catch(Exception exp)		
    }//end of doViewAssociatedCorp(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	
  
    /**
     * This method is used to save the details of the associated corporate of a float account.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward selectInsProducts(ActionMapping mapping,ActionForm form,										
    		                            HttpServletRequest request,HttpServletResponse response) throws Exception
    {
    	log.debug("Inside FloatAccountAction selectInsProducts");
    	try{
    		setLinks(request);  
    		
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		

    		ArrayList<CacheObject> selectedProductsList = new ArrayList<CacheObject>();
   	if(request.getParameter("insSeqID")!=null){
    		selectedProductsList=floatAccountManagerObject.getSelectedProducts(request.getParameter("insSeqID"));
   	}
    		
   	request.setAttribute("dataList",selectedProductsList);
		
			return mapping.findForward("showDynamiclist");
		}//end of try
		catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strFloatAcct));
    	}//end of catch(Exception exp)		
    }//end of doAssociateCorp(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
    
    
    public ActionForward selectNetwork(ActionMapping mapping,ActionForm form,
            HttpServletRequest request,HttpServletResponse response) throws Exception
{
log.debug("Inside selectNetwork Action ");
try{
setLinks(request);  
FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
ArrayList<CacheObject> arrayList = new ArrayList<>();

CacheObject object1=new CacheObject();
object1.setCacheId("GCNH");
object1.setCacheDesc("GOVT NETWORK");

CacheObject object2=new CacheObject();
object2.setCacheId("PCNH");
object2.setCacheDesc("PRIVATE NETWORK");   

CacheObject object3=new CacheObject();
object3.setCacheId("CTM");
object3.setCacheDesc("MEMBER");

CacheObject object4=new CacheObject();
object4.setCacheId("DBL");
object4.setCacheDesc("DIRECT BILLING");


arrayList.add(object1);
arrayList.add(object2);
arrayList.add(object3);
arrayList.add(object4);

request.setAttribute("dataList",arrayList);

return mapping.findForward("showDynamiclist1");
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strFloatAcct));
}//end of catch(Exception exp)		
}
    
    
    
    
    /**
     * This method is used to save the details of the associated corporate of a float account.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doAssociateCorp(ActionMapping mapping,ActionForm form,
    		                            HttpServletRequest request,HttpServletResponse response) throws Exception
    {
    	log.debug("Inside FloatAccountAction doAssociateCorp");
    	try{
    		setLinks(request);  
    		DynaActionForm frmGeneral=(DynaActionForm)form;
    		DynaActionForm frmFloatAccDetails=(DynaActionForm)form;
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		AssocGroupVO assocGroupVO = new AssocGroupVO();
    		assocGroupVO.setFloatSeqID(Long.valueOf(frmFloatAccDetails.get("floatAcctSeqID").toString()));
    		assocGroupVO.setGroupRegSeqID(Long.valueOf(frmFloatAccDetails.get("groupRegnSeqID").toString()));
    		assocGroupVO.setPolicyNo(frmFloatAccDetails.getString("policyNo"));
    		assocGroupVO.setPolicyCategory(frmFloatAccDetails.get("policyCategory").toString());
    		assocGroupVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			floatAccountManagerObject.saveFloatAssocGrp(assocGroupVO);
			request.setAttribute("updated","message.associatedSuccessfully");
			frmGeneral.set("frmChanged","");
			return doViewAssociatedCorp(mapping, form, request, response);
		}//end of try
		catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strFloatAcct));
    	}//end of catch(Exception exp)		
    }//end of doAssociateCorp(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
    
    /**
     * This method is used to delete the associated corporate of a float account.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doDeleteAssociatedCorp(ActionMapping mapping,ActionForm form,
    		                            HttpServletRequest request,HttpServletResponse response) throws Exception
    {
    	log.debug("Inside FloatAccountAction doDeleteAssociatedCorp");
    	try{
    		setLinks(request);  
    		AssocGroupVO assocGroupVO = null;
    		TableData tableData = (TableData)request.getSession().getAttribute("CorporateTable");
    		DynaActionForm frmFloatAccDetails=(DynaActionForm)form;
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		    		
    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
    			assocGroupVO=(AssocGroupVO)tableData.getRowInfo(Integer.parseInt((String)(frmFloatAccDetails).get("rownum")));
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		
			floatAccountManagerObject.deleteFloatAssocGrp(assocGroupVO.getFloatGrpAssSeqID());					
			return doViewAssociatedCorp(mapping, form, request, response);
		}//end of try
		catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strFloatAcct));
    	}//end of catch(Exception exp)		
    }//end of doDeleteAssociatedCorp(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
    
    /**
     * This method is used to clear the selected corporate before association.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doClearCorp(ActionMapping mapping,ActionForm form,
    		                            HttpServletRequest request,HttpServletResponse response) throws Exception
    {
    	log.debug("Inside FloatAccountAction doClearCorp");
    	try{
    		setLinks(request);  
    		DynaActionForm frmFloatAccDetails=(DynaActionForm)form;
    		frmFloatAccDetails.set("policyNo",null);
    		frmFloatAccDetails.set("groupID",null);
			frmFloatAccDetails.set("groupName",null);
			frmFloatAccDetails.set("groupRegnSeqID",null);
			DynaActionForm frmGeneral=(DynaActionForm)form;
    		frmGeneral.set("frmChanged","");
			return this.getForward(strAssociatedCorporate, mapping, request);
		}//end of try
		catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strFloatAcct));
    	}//end of catch(Exception exp)		
    }//end of doClearCorp(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
}//end of FloatAccountManager
