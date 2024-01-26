/**
 * @ (#) AccountsAction.java Sep 28, 2005
 * Project       : TTK HealthCare Services
 * File          : AccountsAction.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Sep 28, 2005
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.empanelment;

import java.util.ArrayList;
import java.util.HashMap;

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
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.empanelment.AccountDetailVO;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.HospitalAuditVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used to add/edit the Hospital bank account information.
 */

public class AccountsAction extends TTKAction {
	private static Logger log = Logger.getLogger( AccountsAction.class );
	
	//Action mapping forwards.
	private static final String strAccountInfo="accountsinfo";
	private static final String strAddAccounts="addaccounts";
	
	//Exception Message Identifier
    private static final String strAccountsError="accounts";
    
    private static final String strTableData="tableData";

//projectX
	private static final String strAccountDefault="accountsdefault";
    
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
    public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		
    		log.info("Inside doDefault method of AccountsAction");
    		
    		TableData tableData = TTKCommon.getTableData(request);
    		
    		DynaActionForm frmSearchAccount =(DynaActionForm)form;
    		frmSearchAccount.initialize(mapping);     //reset the form data
            
    		//remove the selected office from the session
            tableData = new TableData();//create new table data object
            //create the required grid table
            tableData.createTableInfo("AccountsSearchTable",new ArrayList());
            request.getSession().setAttribute("tableData",tableData);
            ((DynaActionForm)form).initialize(mapping);//reset the form data
			
			return this.getForward(strAccountDefault, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsError));
		}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
	
    

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
    public ActionForward doViewPartnerAccounts(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		String strForward = "";
			String strTable = "";
    		log.debug("Inside doView method of AccountsAction");
    		if(TTKCommon.getWebBoardId(request)==null)
            {
            	TTKException expTTK = new TTKException();
                expTTK.setMessage("error.partner.required");
                throw expTTK;
            }//end of if(TTKCommon.getWebBoardId(request)==null)
    		
			
			AccountDetailVO accountDetailVO=null;
			DynaActionForm accountForm = (DynaActionForm)form;
			
			
			HospitalManager partnerObject=this.getHospitalManagerObject();
			HashMap hmCityList = null;
			ArrayList alCityList = null;
			ArrayList alCityCode = null;
			Long PartnerSeqId= new Long(TTKCommon.getWebBoardId(request));//get the web board id
    		String strCaption="Edit ";
            ((DynaActionForm)form).initialize(mapping);//reset the form data
			
            accountDetailVO=partnerObject.getPartnerAccountDetails(PartnerSeqId);
            
            
           // accountDetailVO=partnerObject.getAccount(PartnerSeqId);
			
		//	accountDetailVO.getBankAddressVO().setStateCode("DOH");//seting DOHA as default - which will fetch country also
        	accountDetailVO.getBankAddressVO().setStateCode("DXB");//seting DUBAI as default - which will fetch country also
            hmCityList=partnerObject.getCityInfo(accountDetailVO.getBankAddressVO().getStateCode());
			String countryCode	=	"";
			if(hmCityList!=null)
			{
				alCityList = (ArrayList)hmCityList.get(accountDetailVO.getBankAddressVO().getStateCode());
				alCityCode = (ArrayList)hmCityList.get(accountDetailVO.getPayOrdBankAddressVO().getStateCode());
				countryCode=	(String)(hmCityList.get("CountryId"));
			}//end of if(hmCityList!=null)
			//accountDetailVO.getBankAddressVO().setCountryCode(countryCode);
			if(alCityList==null)
			{
				alCityList=new ArrayList();
			}//end of if(alCityList==null)
			if(alCityCode==null){
				alCityCode=new ArrayList();
			}//end of if(alCityCode==null)
			accountForm = setFormValues(accountDetailVO,mapping,request);
            if(accountDetailVO.getHospBankSeqId() == null){
            	strCaption="Add ";
            }//end of if(accountDetailVO.getHospBankSeqId() == null)
            
            //for intX
            if(accountDetailVO.getEmplNum().equalsIgnoreCase("Empanelled"))
            {
            	request.setAttribute("emplNum", accountDetailVO.getEmplNum());
            }
            request.getSession().setAttribute("emplNum", accountDetailVO.getEmplNum());
            accountForm.set("alCityList",alCityList);
            accountForm.set("alCityCode",alCityCode);
            accountForm.set("caption",strCaption);
           accountForm.set("partnerOrProvider","Partner");
            
            
            request.getSession().setAttribute("frmAccounts",accountForm);
            
            //intX
            
        	@SuppressWarnings("rawtypes")
			ArrayList alaccounts	=	new ArrayList();
        	alaccounts	=	partnerObject.getPartnerAccountIntX(PartnerSeqId);
        //	alaccounts	=	partnerObject.getAccountIntX(PartnerSeqId);
			request.getSession().setAttribute("alaccounts",alaccounts);	
			
            //ENDS

			
			return this.getForward(strAccountInfo, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsError));
		}//end of catch(Exception exp)
    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
 
	
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
    		setLinks(request);
    		String strForward = "";
			String strTable = "";
    		log.debug("Inside doView method of AccountsAction");
    		if(TTKCommon.getWebBoardId(request)==null)
            {
            	TTKException expTTK = new TTKException();
                expTTK.setMessage("error.hospital.required");
                throw expTTK;
            }//end of if(TTKCommon.getWebBoardId(request)==null)
    		
			
			AccountDetailVO accountDetailVO=null;
			DynaActionForm accountForm = (DynaActionForm)form;
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			HashMap hmCityList = null;
			ArrayList alCityList = null;
			ArrayList alCityCode = null;
			Long lHospitalSeqId= new Long(TTKCommon.getWebBoardId(request));//get the web board id
    		String strCaption="Edit ";
            ((DynaActionForm)form).initialize(mapping);//reset the form data
			accountDetailVO=hospitalObject.getAccount(lHospitalSeqId);
			hmCityList=hospitalObject.getCityInfo(accountDetailVO.getBankAddressVO().getStateCode());
			
			String countryCode	=	"";
			if(hmCityList!=null)
			{
				alCityList = (ArrayList)hmCityList.get(accountDetailVO.getBankAddressVO().getStateCode());
				alCityCode = (ArrayList)hmCityList.get(accountDetailVO.getPayOrdBankAddressVO().getStateCode());
				countryCode=	(String)(hmCityList.get("CountryId"));
			}//end of if(hmCityList!=null)
		//	accountDetailVO.getBankAddressVO().setCountryCode(countryCode);
			if(alCityList==null)
			{
				alCityList=new ArrayList();
			}//end of if(alCityList==null)
			if(alCityCode==null){
				alCityCode=new ArrayList();
			}//end of if(alCityCode==null)
			accountForm = setFormValues(accountDetailVO,mapping,request);
            if(accountDetailVO.getHospBankSeqId() == null){
            	strCaption="Add ";
            }//end of if(accountDetailVO.getHospBankSeqId() == null)
            
            //for intX
            /*if(accountDetailVO.getEmplNum().equalsIgnoreCase("Empanelled"))
            {
            	request.setAttribute("emplNum", accountDetailVO.getEmplNum());
            }*/
            request.getSession().setAttribute("emplNum", accountDetailVO.getEmplNum());
            accountForm.set("alCityList",alCityList);
            accountForm.set("alCityCode",alCityCode);
            accountForm.set("caption",strCaption);
            request.getSession().setAttribute("frmAccounts",accountForm);
            
            //intX
            
        	@SuppressWarnings("rawtypes")
			ArrayList alaccounts	=	new ArrayList();
        	alaccounts	=	hospitalObject.getAccountIntX(lHospitalSeqId);
			request.getSession().setAttribute("alaccounts",alaccounts);	

            //ENDS

			
			return this.getForward(strAccountInfo, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsError));
		}//end of catch(Exception exp)
    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
    	log.debug("Inside doChangeWebBoard method of AccountsAction");
    	//ChangeWebBoard method will call doView() method internally.
    	String strsublink = TTKCommon.getActiveSubLink(request);
    	if("Hospital".equals(strsublink))
    	return doView(mapping,form,request,response);
    	else /*if("Partner".equals(strsublink))*/
    		return doViewPartnerAccounts(mapping,form,request,response);
		
    
    		
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to load cities based on the selected state.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doChangeState(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    								   HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside doChangeState method of AccountsAction");
    		DynaActionForm accountForm = (DynaActionForm)form;
			AccountDetailVO accountDetailVO=null;
			HashMap hmCityList = null;
			ArrayList alCityList = null;
			ArrayList alCityCode = null;
			HospitalManager hospitalObject=this.getHospitalManagerObject();
    		accountDetailVO= getFormValues((DynaActionForm)form,mapping,request);
            hmCityList=hospitalObject.getCityInfo(accountDetailVO.getBankAddressVO().getStateCode());
            String countryCode="";
            if(hmCityList!=null)
            {	
            	countryCode	=	(String)(hmCityList.get("CountryId"));
            }
			if(hmCityList!=null)
			{
				alCityList = (ArrayList)hmCityList.get(accountDetailVO.getBankAddressVO().getStateCode());
				alCityCode = (ArrayList)hmCityList.get(accountDetailVO.getPayOrdBankAddressVO().getStateCode());
			}//end of if(hmCityList!=null)
			if(alCityList==null)
			{
				alCityList=new ArrayList();
			}//end of if(alCityList==null)
			if(alCityCode==null){
				alCityCode=new ArrayList();
			}//end of if(alCityCode==null)
			
			
			accountDetailVO.getBankAddressVO().setCountryCode(countryCode);
			accountForm = setFormValues(accountDetailVO,mapping,request);
			
            if(TTKCommon.checkNull(accountDetailVO.getEmplFeeChrgYn()).equals("")){
            	accountForm.set("hidpoEmpFeeCharged","N");
            }//end of if(TTKCommon.checkNull(accountDetailVO.getEmplFeeChrgYn()).equals(""))
            else{
            	accountForm.set("hidpoEmpFeeCharged",accountDetailVO.getEmplFeeChrgYn());
            }//end of else
            accountForm.set("frmChanged","changed");
			accountForm.set("alCityList",alCityList);
			accountForm.set("alCityCode",alCityCode);
			String strpartnerorprovioder = request.getParameter("partnerOrProvider");
			if("Partner".equals(strpartnerorprovioder))
			{
				accountForm.set("partnerOrProvider","Partner");
			}
			request.getSession().setAttribute("frmAccounts",accountForm);
			return this.getForward(strAccountInfo, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsError));
		}//end of catch(Exception exp)
    }//end of doChangeState(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside doSave method of AccountsAction");
			DynaActionForm accountForm = (DynaActionForm)form;
	        String strTarget="";
		    String ibanNumber = (String)accountForm.get("ibanNumber");
		
			if(ibanNumber.length()!=23)
			{
				throw new TTKException("error.ibanno");
			}
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			HashMap hmCityList = null;
			ArrayList alCityList = null;
			ArrayList alCityCode = null;
			Long lHospitalSeqId= new Long(TTKCommon.getWebBoardId(request));//get the web board id
			AccountDetailVO accountDetailVO=null;
			int iUpdate=0;
    		ArrayList<Object> alModifiedHospInfo=new ArrayList<Object>();
			accountDetailVO=new AccountDetailVO();
            String strCaption = (String)accountForm.get("caption");
			//Call populateAccountInfo to store Hospital Bank Account Information in arraylist
			alModifiedHospInfo=populateAccountInfo(accountForm,mapping,request);
			boolean bFlag=true;
			//call the checkDifference method to check whether any change is made in the bank information
			if(request.getSession().getAttribute("alHospInfo")!=null)
			{
				bFlag=isModified(alModifiedHospInfo,(ArrayList)request.getSession().getAttribute("alHospInfo"));
			}//end of if(request.getSession().getAttribute("alHospInfo")!=null)
			if(bFlag==false)
			{
				strTarget="referencedetail";
                accountForm.set("flagValidate","true");
				return this.getForward(strTarget, mapping, request);
			}//end of if(bFlag==false)
			else
			{
				accountDetailVO= getFormValues((DynaActionForm)form,mapping,request);
				iUpdate = hospitalObject.addUpdateAccount(accountDetailVO);
				strTarget="addaccounts";
			}//end of if else
			if(iUpdate > 0)
			{
				if(accountForm.get("hospBankSeqId")==null || accountForm.get("hospBankSeqId").equals("")){
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of if(accountForm.get("hospBankSeqId")==null || accountForm.get("hospBankSeqId").equals(""))
                else{
                	request.setAttribute("updated","message.savedSuccessfully");
                }//end of else
            }//end of if(iUpdate > 0)
			accountForm.initialize(mapping);
			//make a requery
			accountDetailVO=hospitalObject.getAccount(lHospitalSeqId);
			hmCityList=hospitalObject.getCityInfo();
			if(hmCityList!=null)
			{
				alCityList = (ArrayList)hmCityList.get(accountDetailVO.getBankAddressVO().getStateCode());
				alCityCode = (ArrayList)hmCityList.get(accountDetailVO.getPayOrdBankAddressVO().getStateCode());
			}//end of if(hmCityList!=null)
			if(alCityList==null)
			{
				alCityList=new ArrayList();
			}//end of if(alCityList==null)
			if(alCityCode==null){
				alCityCode=new ArrayList();
			}//end of if(alCityCode==null)	
			
			ArrayList alaccounts	=	new ArrayList();
        	alaccounts	=	hospitalObject.getAccountIntX(lHospitalSeqId);
			request.getSession().setAttribute("alaccounts",alaccounts);	
			
	        DynaActionForm hospitalForm = setFormValues(accountDetailVO,mapping,request);
            hospitalForm.set("caption",strCaption);
            hospitalForm.set("alCityList",alCityList);
            hospitalForm.set("alCityCode",alCityCode);
            request.getSession().setAttribute("frmAccounts",hospitalForm);
            return this.getForward(strTarget, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsError));
		}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
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
    public ActionForward doSavePartnerAccount(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside doSave method of AccountsAction");
			DynaActionForm accountForm = (DynaActionForm)form;
	        String strTarget="";
		    String ibanNumber = (String)accountForm.get("ibanNumber");
		  
			/*if(ibanNumber.length()!=23)
			{
				throw new TTKException("error.ibanno");
			}*/
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			HashMap hmCityList = null;
			ArrayList alCityList = null;
			ArrayList alCityCode = null;
			
			//String partnerOrProvider=request.getParameter("partnerOrProvider");
			
			Long lPartnerSeqId= new Long(TTKCommon.getWebBoardId(request));//get the web board id
			AccountDetailVO accountDetailVO=null;
			int iUpdate=0;
    		ArrayList<Object> alModifiedPtnrInfo=new ArrayList<Object>();
			accountDetailVO=new AccountDetailVO();
            String strCaption = (String)accountForm.get("caption");
			//Call populateAccountInfo to store Hospital Bank Account Information in arraylist
			alModifiedPtnrInfo=populateAccountInfo(accountForm,mapping,request);
			boolean bFlag=true;
			//call the checkDifference method to check whether any change is made in the bank information
			if(request.getSession().getAttribute("alHospInfo")!=null)
			{
				bFlag=isModified(alModifiedPtnrInfo,(ArrayList)request.getSession().getAttribute("alHospInfo"));
			}//end of if(request.getSession().getAttribute("alHospInfo")!=null)
			
			if(bFlag==false)
			{
		     	strTarget="referencedetail";
                accountForm.set("flagValidate","true");
				return this.getForward(strTarget, mapping, request);
			}//end of if(bFlag==false)
			else
			{
				accountDetailVO= getFormValues((DynaActionForm)form,mapping,request);
				iUpdate = hospitalObject.addUpdatePartnerAccount(accountDetailVO);
				//iUpdate = hospitalObject.addUpdateAccount(accountDetailVO);
				strTarget="addaccounts";
			}//end of if else
			if(iUpdate > 0)
			{
				if(accountForm.get("hospBankSeqId")==null || accountForm.get("hospBankSeqId").equals("")){
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of if(accountForm.get("hospBankSeqId")==null || accountForm.get("hospBankSeqId").equals(""))
                else{
                	request.setAttribute("updated","message.savedSuccessfully");
                }//end of else
            }//end of if(iUpdate > 0)
			accountForm.initialize(mapping);
			//make a requery
			accountDetailVO=hospitalObject.getPartnerAccountDetails(lPartnerSeqId);
			hmCityList=hospitalObject.getCityInfo();
			if(hmCityList!=null)
			{
				alCityList = (ArrayList)hmCityList.get(accountDetailVO.getBankAddressVO().getStateCode());
				alCityCode = (ArrayList)hmCityList.get(accountDetailVO.getPayOrdBankAddressVO().getStateCode());
			}//end of if(hmCityList!=null)
			if(alCityList==null)
			{
				alCityList=new ArrayList();
			}//end of if(alCityList==null)
			if(alCityCode==null){
				alCityCode=new ArrayList();
			}//end of if(alCityCode==null)	
			
			request.getSession().setAttribute("alaccounts",hospitalObject.getPartnerAccountIntX(lPartnerSeqId));	
			
			
	        DynaActionForm hospitalForm = setFormValues(accountDetailVO,mapping,request);
            hospitalForm.set("caption",strCaption);
            hospitalForm.set("alCityList",alCityList);
            hospitalForm.set("alCityCode",alCityCode);
            hospitalForm.set("partnerOrProvider","Partner");
            request.getSession().setAttribute("frmAccounts",hospitalForm);
            return this.getForward(strTarget, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsError));
		}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    

    
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
    		setLinks(request);
    		log.debug("Inside doClose method of AccountsAction");
			DynaActionForm accountForm = (DynaActionForm)form;
    		accountForm.set("flagValidate",null);
            request.getSession().setAttribute("frmAccounts",accountForm);
    		return this.getForward(strAccountInfo, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsError));
		}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to add the reference information.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doReferenceDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside doReferenceDetail method of AccountsAction");
			DynaActionForm accountForm = (DynaActionForm)form;
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			AccountDetailVO accountDetailVO=null;
			HashMap hmCityList = null;
			ArrayList alCityList = null;
			ArrayList alCityCode = null;
			Long lHospitalSeqId= new Long(TTKCommon.getWebBoardId(request));//get the web board id
    		String strCaption = (String)accountForm.get("caption");
			ArrayList<Object> alHospInfo=new ArrayList<Object>();
			//Call populateAccountInfo to store Hospital Bank Account Information in arraylist
			alHospInfo=populateAccountInfo(accountForm,mapping,request);
			//Set the alHospInfo into session
			request.getSession().setAttribute("alHospInfo",alHospInfo);
		    accountDetailVO= getFormValues((DynaActionForm)form,mapping,request);
			hospitalObject.addUpdateAccount(accountDetailVO);
			//make a requery
			accountDetailVO=hospitalObject.getAccount(lHospitalSeqId);
			hmCityList=hospitalObject.getCityInfo();
			if(hmCityList!=null)
			{
				alCityList = (ArrayList)hmCityList.get(accountDetailVO.getBankAddressVO().getStateCode());
				alCityCode = (ArrayList)hmCityList.get(accountDetailVO.getPayOrdBankAddressVO().getStateCode());
			}//end of if(hmCityList!=null)
			if(alCityList==null)
			{
				alCityList=new ArrayList();
			}//end of if(alCityList==null)
			if(alCityCode==null){
				alCityCode=new ArrayList();
			}//end of if(alCityCode==null)
		    DynaActionForm hospitalForm = setFormValues(accountDetailVO,mapping,request);
		    hospitalForm.set("caption",strCaption);
            accountForm.set("flagValidate",null);
            hospitalForm.set("alCityList",alCityList);
            hospitalForm.set("alCityCode",alCityCode);
            request.getSession().setAttribute("frmAccounts",hospitalForm);
            request.setAttribute("updated","message.savedSuccessfully");
			return this.getForward(strAddAccounts, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsError));
		}//end of catch(Exception exp)
    }//end of doReferenceDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the HospitalManager session object for invoking methods on it.
	 * @return HospitalManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private HospitalManager getHospitalManagerObject() throws TTKException
	{
		HospitalManager hospManager = null;
		try
		{
			if(hospManager == null)
			{
				InitialContext ctx = new InitialContext();
				hospManager = (HospitalManager) ctx.lookup("java:global/TTKServices/business.ejb3/HospitalManagerBean!com.ttk.business.empanelment.HospitalManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strAccountsError);
		}//end of catch
		return hospManager;
	}//end getHospitalManagerObject()

	/**
	 * Returns the boolean value by comparing the two arraylist.
	 * @param  alHospInfoNew ArrayList which contains account new information.
	 * @param alHospInfoOld ArrayList which contains the account old information.
	 * @return false when change is found when comparing the two ArrayList else return true
	 */
	private boolean isModified(ArrayList alHospInfoNew,ArrayList alHospInfoOld)
	{
		for(int i=0;i<alHospInfoOld.size();i++)
		{
			if(!alHospInfoNew.get(i).equals(alHospInfoOld.get(i)))
				return false;
		}//end of for(int i=0;i<alHospInfoOld.size();i++)
		return true;
	}//end of isModified(ArrayList alHospInfoNew,ArrayList alHospInfoOld)

     /**
     * Populates the form element to value object .
     * @param accountDetailVO AccountDetailVO object.
     * @param mapping The ActionMapping used to select this instance.
     * @param request HttpServletRequest  object which contains hospital information.
     * @return hospDetailVO HospitalDetailVO object.
     */
    private AccountDetailVO getFormValues(DynaActionForm generalForm,ActionMapping mapping,
    		HttpServletRequest request) throws TTKException
    {
        try
        {
        	setLinks(request);
        	AccountDetailVO accountDetailVO =null;
            AddressVO bankAddressVO = null;
            AddressVO payOrderAddressVO = null;
            HospitalAuditVO accountAuditVO=null;
            accountDetailVO = (AccountDetailVO)FormUtils.getFormValues(generalForm,"frmAccounts",this,
            														   mapping,request);
            if(request.getParameter("emplFeeChrgYn")==null || ((String)request.getParameter("emplFeeChrgYn")).
            										equals(""))// if Empanel. Fee Charged checkbox in not selected
            {
                accountDetailVO.setEmplFeeChrgYn("N");
            }//end of if(request.getParameter("emplFeeChrgYn")==null)
            if(request.getParameter("bankGuantReqYN")==null || ((String)request.getParameter("bankGuantReqYN")).
            											  equals(""))// if Bank Guarantee checkbox in not selected
            {
                accountDetailVO.setBankGuantReqYN("N");
            }//end of if(request.getParameter("bankGuantReqYN")==null)
            accountDetailVO.setHospSeqId(TTKCommon.getWebBoardId(request));
            ActionForm payOrderAddressForm=(ActionForm)generalForm.get("payOrdBankAddressVO");
            ActionForm bankAddressForm=(ActionForm)generalForm.get("bankAddressVO");
            ActionForm accountAuditForm=(ActionForm)generalForm.get("auditDetailsVO");
            bankAddressVO=(AddressVO)FormUtils.getFormValues(bankAddressForm,"frmBankAddress",this,mapping,request);
            if(((String)generalForm.get("emplFeeChrgYn")).equals("Y"))
            {
                payOrderAddressVO=(AddressVO)FormUtils.getFormValues(payOrderAddressForm,"frmPayOrderAddress",
                													this,mapping,request);
                accountDetailVO.setPayOrdAmount(TTKCommon.getBigDecimal(accountDetailVO.getPayOrdAmountWord()));
            }//end of if(((String)accountForm.get("hidpoEmpFeeCharged")).equals("Y"))
            else
            {
                payOrderAddressVO = new AddressVO();
                payOrderAddressVO.setAddrSeqId(((AddressVO)FormUtils.getFormValues(payOrderAddressForm,
                										"frmPayOrderAddress",this,mapping,request)).getAddrSeqId());
                accountDetailVO.setPayOrdType("");
                accountDetailVO.setPayOrdNmbr("");
                accountDetailVO.setPayOrdAmount(null);
                accountDetailVO.setPayOrdRcvdDate(null);
                accountDetailVO.setPayOrdBankName("");
                accountDetailVO.setChkIssuedDate(null);
            }//end of else
            accountAuditVO=(HospitalAuditVO)FormUtils.getFormValues(accountAuditForm,"frmAccountAuditVO",
            														this,mapping,request);
            accountDetailVO.setPayOrdBankAddressVO(payOrderAddressVO);
            accountDetailVO.setBankAddressVO(bankAddressVO);
            accountDetailVO.setAuditDetailsVO(accountAuditVO);
            accountDetailVO.setGuaranteeAmount(TTKCommon.getBigDecimal(accountDetailVO.getGuaranteeAmountWord()));
            accountDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));////User Id
            return accountDetailVO;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strAccountsError);
        }//end of catch
    }//end of getFormValues(DynaActionForm generalForm,ActionMapping mapping,HttpServletRequest request)

    /**
     * Populates the value object to form element.
     * @param accountDetailVO AccountDetailVO object.
     * @param mapping The ActionMapping used to select this instance.
     * @param request HttpServletRequest  object which contains hospital information.
     * @return DynaActionForm object.
     */
    private DynaActionForm setFormValues(AccountDetailVO accountDetailVO,ActionMapping mapping,
    		HttpServletRequest request) throws TTKException
    {
        ArrayList<Object> alHospInfo=new ArrayList<Object>();
        try
        {
            if(accountDetailVO.getHospBankSeqId()!=null)
            {
                //Store Hospital Bank Account Information in ArrayList, to check whether any changes is made while updating
                alHospInfo.add(accountDetailVO.getBankName());
                alHospInfo.add(accountDetailVO.getAccountNumber());
                alHospInfo.add(accountDetailVO.getActInNameOf());
                alHospInfo.add(accountDetailVO.getBranchName());
                alHospInfo.add(accountDetailVO.getBankAddressVO().getAddress1());
                alHospInfo.add(accountDetailVO.getBankAddressVO().getAddress2());
                alHospInfo.add(accountDetailVO.getBankAddressVO().getAddress3());
                alHospInfo.add(accountDetailVO.getBankAddressVO().getStateCode());
                alHospInfo.add(accountDetailVO.getBankAddressVO().getCityCode());
                alHospInfo.add(accountDetailVO.getBankAddressVO().getPinCode());
                alHospInfo.add(accountDetailVO.getBankAddressVO().getCountryCode());
            }//end of if(accountDetailVO.getHospBankSeqId()!=null)
            //Set the alHospInfo into session
            request.getSession().setAttribute("alHospInfo",alHospInfo);

            DynaActionForm accountForm = (DynaActionForm)FormUtils.setFormValues("frmAccounts",accountDetailVO,
            																	 this,mapping,request);
            if(accountDetailVO.getPayOrdBankAddressVO()!=null)
            {
                accountForm.set("payOrdBankAddressVO", (DynaActionForm)FormUtils.setFormValues("frmPayOrderAddress",
                									accountDetailVO.getPayOrdBankAddressVO(),this,mapping,request));
            }//end of if(hospitalDetailVO.getAddressVO()!=null)
            else
            {
                accountForm.set("payOrdBankAddressVO", (DynaActionForm)FormUtils.setFormValues("frmPayOrderAddress",
                															 new AddressVO(),this,mapping,request));
            }//end of else
            if(accountDetailVO.getBankAddressVO()!=null)
            {
                accountForm.set("bankAddressVO", (DynaActionForm)FormUtils.setFormValues("frmBankAddress",
                										 accountDetailVO.getBankAddressVO(),this,mapping,request));
            }//end of if(hospitalDetailVO.getHospitalAuditVO()!=null)
            else
            {
                accountForm.set("bankAddressVO", (DynaActionForm)FormUtils.setFormValues("frmBankAddress",new AddressVO(),
                																		 this,mapping,request));
            }//end of else
            if(accountDetailVO.getAuditDetailsVO()!=null)
            {
                accountForm.set("auditDetailsVO", (DynaActionForm)FormUtils.setFormValues("frmAccountAuditVO",
                										accountDetailVO.getAuditDetailsVO(),this,mapping,request));
            }//end of if(hospitalDetailVO.getDocumentDetailVO()!=null)
            else
            {
                accountForm.set("auditDetailsVO", (DynaActionForm)FormUtils.setFormValues("frmAccountAuditVO",
                													  new HospitalAuditVO(),this,mapping,request));
            }//end of else
            if(TTKCommon.checkNull(accountDetailVO.getEmplFeeChrgYn()).equals(""))
                accountForm.set("hidpoEmpFeeCharged","N");
            else
                accountForm.set("hidpoEmpFeeCharged",accountDetailVO.getEmplFeeChrgYn());

            request.getSession().setAttribute("empFeeCharged",accountDetailVO.getEmplFeeChrgYn());
            return accountForm;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strAccountsError);
        }//end of catch
    }//end of setFormValues(HospitalDetailVO hospitalDetailVO,ActionMapping mapping,HttpServletRequest request)

    /**
	 * Add the required account information to arrayList to check whether any changes are made
	 * @param accountForm DynaActionForm object to which contain the accounts information.
	 * @param request HttpServletRequest  object which contains accounts information.
     * @param mapping The ActionMapping used to select this instance
     * @param request The HTTP request we are processing
	 * @return alAccountInfoNew ArrayList which contian accounts information.
	 */
	private ArrayList<Object> populateAccountInfo(DynaActionForm accountForm,ActionMapping mapping,
			HttpServletRequest request) throws TTKException
	{
		ArrayList<Object> alAccountInfoNew=new ArrayList<Object>();
        AddressVO bankAddressVO = null;
        ActionForm bankAddressForm=(ActionForm)accountForm.get("bankAddressVO");
        try
        {
            bankAddressVO=(AddressVO)FormUtils.getFormValues(bankAddressForm,"frmBankAddress",this,mapping,request);
            alAccountInfoNew.add((String)accountForm.get("bankName"));
            alAccountInfoNew.add((String)accountForm.get("accountNumber"));
            alAccountInfoNew.add((String)accountForm.get("actInNameOf"));
            alAccountInfoNew.add((String)accountForm.get("branchName"));
            alAccountInfoNew.add(bankAddressVO.getAddress1());
            alAccountInfoNew.add(bankAddressVO.getAddress2());
            alAccountInfoNew.add(bankAddressVO.getAddress3());
            alAccountInfoNew.add(bankAddressVO.getStateCode());
            alAccountInfoNew.add(bankAddressVO.getCityCode());
            alAccountInfoNew.add(bankAddressVO.getPinCode());
            alAccountInfoNew.add(bankAddressVO.getCountryCode());
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strAccountsError);
        }//end of catch
		return alAccountInfoNew;
	}//end of populateAccountInfo(HttpServletRequest request,DynaActionForm accountForm)
	
    public ActionForward doPartnerReferenceDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside doReferenceDetail method of AccountsAction");
			DynaActionForm accountForm = (DynaActionForm)form;
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			AccountDetailVO accountDetailVO=null;
			HashMap hmCityList = null;
			ArrayList alCityList = null;
			ArrayList alCityCode = null;
			Long lPartnerSeqId= new Long(TTKCommon.getWebBoardId(request));//get the web board id
    		String strCaption = (String)accountForm.get("caption");
			ArrayList<Object> alHospInfo=new ArrayList<Object>();
			//Call populateAccountInfo to store Hospital Bank Account Information in arraylist
			alHospInfo=populateAccountInfo(accountForm,mapping,request);
			//Set the alHospInfo into session
			request.getSession().setAttribute("alHospInfo",alHospInfo);
		    accountDetailVO= getFormValues((DynaActionForm)form,mapping,request);
			hospitalObject.addUpdatePartnerAccount(accountDetailVO);
			//addUpdatePartnerAccount
			//make a requery
			accountDetailVO=hospitalObject.getPartnerAccountDetails(lPartnerSeqId);//getPartnerAccountDetails
			hmCityList=hospitalObject.getCityInfo();
			if(hmCityList!=null)
			{
				alCityList = (ArrayList)hmCityList.get(accountDetailVO.getBankAddressVO().getStateCode());
				alCityCode = (ArrayList)hmCityList.get(accountDetailVO.getPayOrdBankAddressVO().getStateCode());
			}//end of if(hmCityList!=null)
			if(alCityList==null)
			{
				alCityList=new ArrayList();
			}//end of if(alCityList==null)
			if(alCityCode==null){
				alCityCode=new ArrayList();
			}//end of if(alCityCode==null)
		    DynaActionForm hospitalForm = setFormValues(accountDetailVO,mapping,request);
		    hospitalForm.set("caption",strCaption);
            accountForm.set("flagValidate",null);
            hospitalForm.set("alCityList",alCityList);
            hospitalForm.set("alCityCode",alCityCode);
            hospitalForm.set("partnerOrProvider","Partner");
            request.getSession().setAttribute("frmAccounts",hospitalForm);
            request.setAttribute("updated","message.savedSuccessfully");
            
            
            
			return this.getForward(strAddAccounts, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAccountsError));
		}//end of catch(Exception exp)
    }//end of doReferenceDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
}//end of clas AccountsAction