/**
 * as per Hospital Login
 * @ (#) ProviderPresciptionAction Mar 24, 2015
 *  Author       :Kishor kumar S h
 * Company      : Rcs Technologies
 * Date Created : Oct 29 ,2014
 *
 * @author       :Kishor kumar S h
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.hospital;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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
import com.ttk.business.administration.ConfigurationManager;
import com.ttk.business.empanelment.GradingManager;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.business.onlineforms.OnlinePreAuthManager;
import com.ttk.business.preauth.MemberHistoryManager;
import com.ttk.common.HospPreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.empanelment.GradingServicesVO;
import com.ttk.dto.empanelment.HospitalAuditVO;
import com.ttk.dto.empanelment.LabServiceVO;
import com.ttk.dto.empanelment.LaboratoryServicesVO;
import com.ttk.dto.empanelment.PreRequisiteVO;
import com.ttk.dto.hospital.HospPreAuthVO;
//import com.ttk.dto.onlineforms.OnlineInsPolicyVO;
//import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import com.ttk.dto.preauth.CashlessDetailVO;
import com.ttk.dto.preauth.CashlessVO;

import formdef.plugin.util.FormUtils;

import java.util.Date;//kocnewhosp1
import java.text.*;//kocnewhosp1

/**
 * This class is used for Searching the List Policies to see the Online Account Info.
 * This also provides deletion and updation of products.
 */
public class ProviderPrescriptionAction extends TTKAction {

    private static final Logger log = Logger.getLogger( OnlineCashlessHospAction.class );

    //Modes.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";

    // Action mapping forwards.

    private static final String strHospSerachInfo="onlinehospitalinfo";
    private static final String strAddLaboratories="addLaboratories";   
    private static final String strOnlineClaimList="cashlessAdd";  
    private static final String strAddConsumables="addConsumables";
    
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
            log.info("Inside the doDefault method of ProviderPrescriptionAction");
            setOnlineLinks(request);
            
            DynaActionForm frmCashlessAddNew =(DynaActionForm)form;
            frmCashlessAddNew.initialize(mapping);     //reset the form data
			request.getSession().setAttribute("frmCashlessAdd", frmCashlessAddNew);
            return this.getForward(strOnlineClaimList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineClaimList));
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
            log.debug("Inside the doSearch method of ProviderPrescriptionAction");
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
					return (mapping.findForward(strOnlineClaimList));
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
				tableData.createTableInfo("HospClaimsTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));						
				tableData.modifySearchData("search");
				//sorting based on investSeqID in descending order													
			}//end of else
			alOnlineAccList= onlineAccessManager.getHospClaimsList(tableData.getSearchData());
			tableData.setData(alOnlineAccList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strOnlineClaimList, mapping, request);
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
    public ActionForward doViewLaboratoriesList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		log.debug("Inside the doViewLaboratoriesList method of ProviderPrescriptionAction");
    		setOnlineLinks(request);
    		//get the session bean from the bean pool for each excecuting thread
    		ArrayList alLabServices	=	null;
    		OnlineAccessManager onlineAccessManager=null;
    		onlineAccessManager = this.getOnlineAccessManagerObject();
    		LaboratoryServicesVO laboratoryServicesVO =new LaboratoryServicesVO();
			DynaActionForm frmCashlessPrescription = (DynaActionForm)FormUtils.setFormValues("frmCashlessPrescription",
					laboratoryServicesVO, this, mapping, request);
			
    		alLabServices=onlineAccessManager.getLabServices("Laboratory",userSecurityProfile.getHospSeqId());
			if(alLabServices==null){
				alLabServices=new ArrayList();
			}//end of if(alGrading==null)
			
			DynaActionForm frmOnlinePreAuth	=	(DynaActionForm)form;
			
			
			frmCashlessPrescription.set("hospLabs",alLabServices);
			request.setAttribute("hospLabs",alLabServices);
			frmCashlessPrescription.set("caption","");
			request.setAttribute("frmCashlessPrescription",frmCashlessPrescription);
			request.setAttribute("Prescription_Type", "Laboratory");
			return this.getForward(strAddLaboratories, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doViewLaboratoriesList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doViewaddRadiologyList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		log.debug("Inside the doViewaddRadiologyList method of ProviderPrescriptionAction");
    		setOnlineLinks(request);
    		//get the session bean from the bean pool for each excecuting thread
    		ArrayList alRadiologyServices	=	null;
    		OnlineAccessManager onlineAccessManager=null;
    		onlineAccessManager = this.getOnlineAccessManagerObject();
    		LaboratoryServicesVO laboratoryServicesVO =new LaboratoryServicesVO();
			DynaActionForm frmCashlessPrescription = (DynaActionForm)FormUtils.setFormValues("frmCashlessPrescription",
					laboratoryServicesVO, this, mapping, request);
			
			alRadiologyServices=onlineAccessManager.getLabServices("Radiology",userSecurityProfile.getHospSeqId());
			if(alRadiologyServices==null){
				alRadiologyServices=new ArrayList();
			}//end of if(alGrading==null)
			frmCashlessPrescription.set("hospLabs",alRadiologyServices);
			request.setAttribute("hospLabs",alRadiologyServices);
			frmCashlessPrescription.set("caption","");
			request.setAttribute("frmCashlessPrescription",frmCashlessPrescription);
			request.setAttribute("Prescription_Type", "Radiology");
			return this.getForward(strAddLaboratories, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doViewaddRadiologyList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
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
    public ActionForward doaddSurgeryList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		log.debug("Inside the doAddSurgeryList method of ProviderPrescriptionAction");
    		setOnlineLinks(request);
    		//get the session bean from the bean pool for each excecuting thread
    		ArrayList alSurgeryServices	=	null;
    		OnlineAccessManager onlineAccessManager=null;
    		onlineAccessManager = this.getOnlineAccessManagerObject();
    		LaboratoryServicesVO laboratoryServicesVO =new LaboratoryServicesVO();
			DynaActionForm frmCashlessPrescription = (DynaActionForm)FormUtils.setFormValues("frmCashlessPrescription",
					laboratoryServicesVO, this, mapping, request);
			
			alSurgeryServices=onlineAccessManager.getLabServices("Surgeries",userSecurityProfile.getHospSeqId());
			if(alSurgeryServices==null){
				alSurgeryServices=new ArrayList();
			}//end of if(alGrading==null)
			frmCashlessPrescription.set("hospLabs",alSurgeryServices);
			request.setAttribute("hospLabs",alSurgeryServices);
			frmCashlessPrescription.set("caption","");
			request.setAttribute("frmCashlessPrescription",frmCashlessPrescription);
			request.setAttribute("Prescription_Type", "Surgery");
			return this.getForward(strAddLaboratories, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doaddSurgeryList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    
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
    
    public ActionForward doaddMinorsList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		log.debug("Inside the doAddMinorsList method of ProviderPrescriptionAction");
    		setOnlineLinks(request);
    		//get the session bean from the bean pool for each excecuting thread
    		ArrayList alMinorServices	=	null;
    		OnlineAccessManager onlineAccessManager=null;
    		onlineAccessManager = this.getOnlineAccessManagerObject();
    		LaboratoryServicesVO laboratoryServicesVO =new LaboratoryServicesVO();
			DynaActionForm frmCashlessPrescription = (DynaActionForm)FormUtils.setFormValues("frmCashlessPrescription",
					laboratoryServicesVO, this, mapping, request);
			
			alMinorServices=onlineAccessManager.getLabServices("Minor Surgeries",userSecurityProfile.getHospSeqId());
			if(alMinorServices==null){
				alMinorServices=new ArrayList();
			}//end of if(alGrading==null)
			frmCashlessPrescription.set("hospLabs",alMinorServices);
			request.setAttribute("hospLabs",alMinorServices);
			frmCashlessPrescription.set("caption","");
			request.setAttribute("frmCashlessPrescription",frmCashlessPrescription);
			request.setAttribute("Prescription_Type", "MinorSurgery");
			return this.getForward(strAddLaboratories, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doAddMinorsList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    
    
    
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
    public ActionForward doSaveLabs(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		log.info("Inside the doSaveLabs method of doViewLaboratoriesList");
    		setOnlineLinks(request);
    		//get the session bean from the bean pool for each excecuting thread
    		ArrayList alLabServices	=	null;
    		OnlineAccessManager onlineAccessManager=null;
    		onlineAccessManager = this.getOnlineAccessManagerObject();
    		LaboratoryServicesVO laboratoryServicesVO =new LaboratoryServicesVO();
			DynaActionForm frmCashlessPrescription = (DynaActionForm)FormUtils.setFormValues("frmCashlessPrescription",
					laboratoryServicesVO, this, mapping, request);
			
    		alLabServices=onlineAccessManager.getLabServices("LABORATORY",userSecurityProfile.getHospSeqId());
			if(alLabServices==null){
				alLabServices=new ArrayList();
			}//end of if(alGrading==null)
			frmCashlessPrescription.set("hospLabs",alLabServices);
			request.setAttribute("hospLabs",alLabServices);
			frmCashlessPrescription.set("caption","");
			request.setAttribute("frmCashlessPrescription",frmCashlessPrescription);
			return this.getForward(strAddLaboratories, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doSaveLabs(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)  
    
    
    /**
	 * @param mapping ActionMapping
	 * @param form 
	 * @param request
	 * @param response
	 * @return true if vidal ID is already got OTP
	 * @throws Exception
	 */
	public ActionForward doCloseLabs(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside the doCloseLabs method of ProviderPrescriptionAction");
			/*DynaActionForm frmOnlinePreAuth =(DynaActionForm)form;
			String vidalId	=	frmOnlinePreAuth.getString("vidalId");
			System.out.println("vidalId::"+vidalId);
			
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
			OnlinePreAuthManager onlinePreAuthManager= null;
			CashlessDetailVO cashlessDetailVO	=	null;
			
			onlinePreAuthManager = this.getOnlineAccessManagerObject();
			//below function to check the OTP has generated for the vidal ID  on that day
				cashlessDetailVO	= onlinePreAuthManager.geMemberDetailsOnEnrollId(vidalId);
				frmOnlinePreAuth = (DynaActionForm)FormUtils.setFormValues("frmOnlinePreAuth",
						cashlessDetailVO,this,mapping,request);
				request.setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
				frmOnlinePreAuth.set("enrollId", vidalId);
				frmOnlinePreAuth.set("providerName", userSecurityProfile.getHospitalName());
				
				strForwardValidate	=	"showpreauth";
			//	frmOnlinePreAuth.set("enrollId", request.getParameter("enrollId"));
			//finally return to the grid screen
			request.setAttribute("vidalId", vidalId);*/
			request.setAttribute("MemberSave", "MemberSave"); 
			return this.getForward("showpreauth", mapping, request);
		}//end of try
	catch(TTKException expTTK)
	{
	return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
	}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
	
	
		/**
		 * @param mapping ActionMapping
		 * @param form 
		 * @param request
		 * @param response
		 * @return true if vidal ID is already got OTP
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public ActionForward doNextLabs(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            HttpServletResponse response) throws Exception{
			try{
				setOnlineLinks(request);
				log.info("Inside the doNextLabs method of ProviderPrescriptionAction");
				//DynaActionForm frmAddLabs	=	(DynaActionForm) form;
				
				String[] selectLabs	=	request.getParameterValues("answer1List1"); 
				String selectedLabIds	=	"";
				for(int i=0;i<selectLabs.length;i++)
					selectedLabIds	=	selectedLabIds+",'"+selectLabs[i]+"'";
				
				if(selectLabs.length>0)
					selectedLabIds	=	selectedLabIds.substring(1);
				ArrayList alLabs	=	new ArrayList();
				LabServiceVO labServiceVO	=	new LabServiceVO();
				OnlinePreAuthManager onlinePreAuthManager= null;
				onlinePreAuthManager =  this.getOnlinePreAuthManagerObject();
				Long PAT_INTIMATION_SEQ_ID	=	(Long) request.getSession().getAttribute("PAT_INTIMATION_SEQ_ID");
				alLabs	= onlinePreAuthManager.getSelectedLabDetails(selectedLabIds,PAT_INTIMATION_SEQ_ID,(String)request.getSession().getAttribute("enrollId"));
				
				DynaActionForm frmOnlinePreAuth =(DynaActionForm)request.getSession().getAttribute("frmOnlinePreAuth");
				
				HashMap prescriptions	=	 (HashMap) request.getSession().getAttribute("prescriptions");
				if(prescriptions==null)
				{
					prescriptions	=	new HashMap();
					prescriptions.put("LABORATORY", alLabs);
				}
				else
					prescriptions.put("LABORATORY", alLabs);
				
				request.getSession().setAttribute("prescriptions", prescriptions);
				request.setAttribute("MemberSave", "MemberSave"); 
				return this.getForward("showpreauth", mapping, request);
			}//end of try
		catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
		}//end of doNextLabs(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	
		/**doNextRadio
		 * @param mapping ActionMapping
		 * @param form 
		 * @param request
		 * @param response
		 * @return true if vidal ID is already got OTP
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public ActionForward doNextRadio(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            HttpServletResponse response) throws Exception{
			try{
				setOnlineLinks(request);
				log.info("Inside the doNextRadio method of ProviderPrescriptionAction");
				//DynaActionForm frmAddLabs	=	(DynaActionForm) form;
				
				String[] selectRadios	=	request.getParameterValues("answer1List1"); 
				String selectedRadioIds	=	"";
				for(int i=0;i<selectRadios.length;i++)
					selectedRadioIds	=	selectedRadioIds+",'"+selectRadios[i]+"'";
				
				if(selectRadios.length>0)
					selectedRadioIds	=	selectedRadioIds.substring(1);
				
				ArrayList alRadios	=	new ArrayList();
				Long PAT_INTIMATION_SEQ_ID	=	(Long) request.getSession().getAttribute("PAT_INTIMATION_SEQ_ID");
				LabServiceVO labServiceVO	=	new LabServiceVO();
				OnlinePreAuthManager onlinePreAuthManager= null;
				onlinePreAuthManager =  this.getOnlinePreAuthManagerObject();
				alRadios	= onlinePreAuthManager.getSelectedLabDetails(selectedRadioIds,PAT_INTIMATION_SEQ_ID,(String)request.getSession().getAttribute("enrollId"));
				
				HashMap prescriptions	=	 (HashMap) request.getSession().getAttribute("prescriptions");
				if(prescriptions==null){
					prescriptions	=	new HashMap();
					prescriptions.put("RADIOLOGY", alRadios);
				}
				else
					prescriptions.put("RADIOLOGY", alRadios);
				
				request.getSession().setAttribute("prescriptions", prescriptions);
				request.setAttribute("MemberSave", "MemberSave"); 
				return this.getForward("showpreauth", mapping, request);
			}//end of try
		catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
		}//end of doNextRadio(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
		
		
		
		
		/**doNextRadio
		 * @param mapping ActionMapping
		 * @param form 
		 * @param request
		 * @param response
		 * @return true if vidal ID is already got OTP
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public ActionForward doNextSurgery(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            HttpServletResponse response) throws Exception{
			try{
				setOnlineLinks(request);
				log.info("Inside the doNextSurgery method of ProviderPrescriptionAction");
				//DynaActionForm frmAddLabs	=	(DynaActionForm) form;
				
				String[] selectSurgeries	=	request.getParameterValues("answer1List1"); 
				String selectedSurgeryIds	=	"";
				for(int i=0;i<selectSurgeries.length;i++)
					selectedSurgeryIds	=	selectedSurgeryIds+",'"+selectSurgeries[i]+"'";
				
				if(selectSurgeries.length>0)
					selectedSurgeryIds	=	selectedSurgeryIds.substring(1);
				
				ArrayList alSurgeries	=	new ArrayList();
				Long PAT_INTIMATION_SEQ_ID	=	(Long) request.getSession().getAttribute("PAT_INTIMATION_SEQ_ID");
				LabServiceVO labServiceVO	=	new LabServiceVO();
				OnlinePreAuthManager onlinePreAuthManager= null;
				onlinePreAuthManager =  this.getOnlinePreAuthManagerObject();
				alSurgeries	= onlinePreAuthManager.getSelectedLabDetails(selectedSurgeryIds,PAT_INTIMATION_SEQ_ID,(String)request.getSession().getAttribute("enrollId"));
				
				HashMap prescriptions	=	 (HashMap) request.getSession().getAttribute("prescriptions");
				if(prescriptions==null)
				{
					prescriptions	=	new HashMap();
					prescriptions.put("SURGERIES", alSurgeries);
				}
				else
					prescriptions.put("SURGERIES", alSurgeries);
				
				request.getSession().setAttribute("prescriptions", prescriptions);
				request.setAttribute("MemberSave", "MemberSave"); 
				return this.getForward("showpreauth", mapping, request);
			}//end of try
		catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
		}//end of doNextSurgery(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
		
		
		/**doNextRadio
		 * @param mapping ActionMapping
		 * @param form 
		 * @param request
		 * @param response
		 * @return true if vidal ID is already got OTP
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public ActionForward doNextMinorSurgery(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            HttpServletResponse response) throws Exception{
			try{
				setOnlineLinks(request);
				log.info("Inside the doNextMinorSurgery method of ProviderPrescriptionAction");
				//DynaActionForm frmAddLabs	=	(DynaActionForm) form;
				
				String[] selectMinors	=	request.getParameterValues("answer1List1"); 
				String selectedMinorIds	=	"";
				for(int i=0;i<selectMinors.length;i++)
					selectedMinorIds	=	selectedMinorIds+",'"+selectMinors[i]+"'";
				
				if(selectMinors.length>0)
					selectedMinorIds	=	selectedMinorIds.substring(1);
				
				ArrayList alMinors	=	new ArrayList();
				LabServiceVO labServiceVO	=	new LabServiceVO();
				OnlinePreAuthManager onlinePreAuthManager= null;
				onlinePreAuthManager =  this.getOnlinePreAuthManagerObject();
				Long PAT_INTIMATION_SEQ_ID	=	(Long) request.getSession().getAttribute("PAT_INTIMATION_SEQ_ID");
				alMinors	= onlinePreAuthManager.getSelectedLabDetails(selectedMinorIds,PAT_INTIMATION_SEQ_ID,(String)request.getSession().getAttribute("enrollId"));
				
				HashMap prescriptions	=	 (HashMap) request.getSession().getAttribute("prescriptions");
				if(prescriptions==null){
					prescriptions	=	new HashMap();
					prescriptions.put("MINORSURGERIES", alMinors);
				}
				else
					prescriptions.put("MINORSURGERIES", alMinors);
				
				
				request.getSession().setAttribute("prescriptions", prescriptions);
				request.setAttribute("MemberSave", "MemberSave"); 
				return this.getForward("showpreauth", mapping, request);
			}//end of try
		catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
		}//end of doNextMinorSurgery(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
		
		
		
		/**doNextConsumables
		 * @param mapping ActionMapping
		 * @param form 
		 * @param request
		 * @param response
		 * @return true if vidal ID is already got OTP
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public ActionForward doNextConsumables(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            HttpServletResponse response) throws Exception{
			try{
				setOnlineLinks(request);
				log.info("Inside the doNextConsumables method of ProviderPrescriptionAction");
				DynaActionForm frmOnlineConsumables	=	(DynaActionForm) form;
				frmOnlineConsumables.initialize(mapping);
				/*String[] selectConsumables	=	request.getParameterValues("answer1List1"); 
				String selectedConsumablesIds	=	"";
				for(int i=0;i<selectConsumables.length;i++)
					selectedConsumablesIds	=	selectedConsumablesIds+",'"+selectConsumables[i]+"'";
				
				if(selectConsumables.length>0)
					selectedConsumablesIds	=	selectedConsumablesIds.substring(1);*/
				
				
				ArrayList alConsumables	=	new ArrayList();
				LabServiceVO labServiceVO	=	new LabServiceVO();
				CashlessDetailVO cashlessDetailVO	=	new CashlessDetailVO();
				OnlinePreAuthManager onlinePreAuthManager= null;
				onlinePreAuthManager =  this.getOnlinePreAuthManagerObject();
				
				Long intimationSeqID	=	(Long) request.getSession().getAttribute("PAT_INTIMATION_SEQ_ID");
				if(intimationSeqID==null)
					intimationSeqID= new Long(0);
				
				alConsumables	=	onlinePreAuthManager.getPreAuthConsumables(intimationSeqID);
				
				cashlessDetailVO	=		onlinePreAuthManager.getPreAuthDetails(intimationSeqID);
				frmOnlineConsumables = (DynaActionForm)FormUtils.setFormValues("frmOnlineConsumables",
						cashlessDetailVO,this,mapping,request);
				
				//alConsumables	= onlinePreAuthManager.getSelectedLabDetails(selectedConsumablesIds);
				
				HashMap prescriptions	=	 (HashMap) request.getSession().getAttribute("prescriptions");
				if(prescriptions==null){
					prescriptions	=	new HashMap();
					prescriptions.put("CONSUMABLES", alConsumables);
				}
				else
					prescriptions.put("CONSUMABLES", alConsumables);
				
				request.getSession().setAttribute("prescriptions", prescriptions);
				request.setAttribute("MemberSave", "MemberSave"); 
				return this.getForward("showpreauth", mapping, request);
			}//end of try
		catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
		}//end of doNextConsumables(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
		
		
		/**doUploadAttahmens
		 * @param mapping ActionMapping
		 * @param form 
		 * @param request
		 * @param response
		 * @return true if vidal ID is already got OTP
		 * @throws Exception
		 */
		public ActionForward doUploadAttahmens(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            HttpServletResponse response) throws Exception{
			try{
				setOnlineLinks(request);
				log.info("Inside the doUploadAttahmens method of ProviderPrescriptionAction");
				DynaActionForm frmOnlineConsumables	=	(DynaActionForm) form;
				frmOnlineConsumables.initialize(mapping);
				
				return this.getForward("onlinePreAuthFulesUpload", mapping, request);
			}//end of try
		catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
		}//end of doUploadAttahmens(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
		
		
		
    /**
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmClmHospSearch formbean which contains the search fields
     * @return ArrayList contains search parameters
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmClmHospSearch,HttpServletRequest request) throws TTKException
    {
//      build the column names along with their values to be searched
        ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();
        UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
        //prepare the search BOX parameters
      //  Long lngHospSeqId =userSecurityProfile.getHospSeqId();
	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sClaimNumber")));
	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sAuthNumber")));
        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sEnrollmentNumber")));
    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sStatus")));
        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sPolicyNumber")));
        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sDOA")));
        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sDateOfClm")));
        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sClmStartDate")));
		alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sClmEndDate")));   //sPatStartDate,sPatEndDate,sClmStartDate,sClmEndDate
        alSearchBoxParams.add(userSecurityProfile.getHospSeqId());
        //new Req
        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sPatientName")));
        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sDischargeDate")));
    	return alSearchBoxParams;
    }//end of populateSearchCriteria(DynaActionForm frmClmHospSearch,HttpServletRequest request) 
    
    
    
    
    private OnlinePreAuthManager getOnlinePreAuthManagerObject() throws TTKException
    {
    	OnlinePreAuthManager onlinePreAuthManager= null;
        try
        {
            if(onlinePreAuthManager == null)
            {
                InitialContext ctx = new InitialContext();
                //onlineAccessManager = (OnlineAccessManager) ctx.lookup(OnlineAccessManager.class.getName());
                onlinePreAuthManager = (OnlinePreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlinePreAuthManagerBean!com.ttk.business.onlineforms.OnlinePreAuthManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strHospSerachInfo);
        }//end of catch
        return onlinePreAuthManager;
    }//end of getOnlineAccessManagerObject()
    
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
		 * This method builds all the search parameters to ArrayList and places them in session
		 * @param request The HTTP request we are processing
		 * @return alSearchParams ArrayList
		 */
		private ArrayList<Object> populateSearchCriteriaNew(HttpServletRequest request)
		{
			//build the column names along with their values to be searched
			ArrayList<Object> alSearchParams = new ArrayList<Object>();
			alSearchParams.add(TTKCommon.getWebBoardId(request));
			alSearchParams.add(TTKCommon.getUserSeqId(request));
			return alSearchParams;
		}//end of populateSearchCriteria(Long lngMemberSeqId)
		

		
}//end of ProviderPrescriptionAction
