/**
 * @ (#) NewEnrollAction.java Dec 26,2007
 * Project       : TTK HealthCare Services
 * File          : NewEnrollAction.java
 * Author        : Raghavendra T M
 * Company       : Span Systems Corporation
 * Date Created  : Dec 26,2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.onlineforms;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.empanelment.GradingManager;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.GradingServicesVO;
import com.ttk.dto.empanelment.HospitalDetailVO;
import com.ttk.dto.empanelment.HospitalVO;
import com.ttk.dto.empanelment.InfraStructureVO;
import com.ttk.dto.onlineforms.OnlineFacilityVO;
import com.ttk.dto.usermanagement.PersonalInfoVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;


/**
 * This class is used to view the Home page
 */
public class NewEnrollAction extends TTKAction {
	private static Logger log = Logger.getLogger( NewEnrollAction.class );
	//  Action mapping forwards.
	private static final String strOnlineHome="onlinehome";
	private static final String strFailure="failure";
	
	//intx
	private static final String	strOnlineProvider			=	"onlineProvider";
	private static final String	strProviderServicesList		=	"providerServices";
	private static final String strServices					=	"services";
	private static final String strMedicalInfraStructure	=	"medicalInfraStructure";
	private static final String strClinicalSupport			=	"clinicalSupport";
	private static final String strOtherDetail				=	"otherDetail";
	private static final String strGradingInfraStr			=	"gradingInfraStr";
    private static final String strAddSpecialities			=	"addspecialities";
    private static final String strOnlineProviderContacts	=	"onlinecontacts";
    


	
	/**
	 * Returns the onlineAccessManager session object for invoking methods on it.
	 * @return onlineAccessManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private OnlineAccessManager getOnlineManagerObject() throws TTKException
	{
		OnlineAccessManager onlineManager = null;
		try
		{
			if(onlineManager == null)
			{
				InitialContext ctx = new InitialContext();
				onlineManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
				log.debug("Inside getOnlineManagerObject: onlineManager: " + onlineManager);
			}//end if(onlineManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strFailure);
		}//end of catch
		return onlineManager;
	}//end of getOnlineManagerObject()
	
	
	
	
	//homebroker
	private ArrayList<Object> populateSearchCriteria(HttpServletRequest request) throws TTKException
    {
    	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)
    												request.getSession().getAttribute("UserSecurityProfile");
    	//build the column names along with their values to be searched
    	ArrayList<Object> alSearchParams = new ArrayList<Object>();
    	
    	 if(userSecurityProfile.getLoginType().equals("B"))  //for Broker login
    	{
    		alSearchParams.add(userSecurityProfile.getUSER_ID());
    		alSearchParams.add(null);
    		alSearchParams.add(userSecurityProfile.getGroupID());
    		alSearchParams.add(null);
    		alSearchParams.add(userSecurityProfile.getLoginType());
    		alSearchParams.add(TTKCommon.getUserSeqId(request));
    	}//end of if(userSecurityProfile.getLoginType().equals("B"))
    	
    	
    	return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmSearchBankAccount)
	
	
	public ByteArrayOutputStream generateReport(JasperPrint jasperPrint1, JasperPrint jasperPrint2) throws JRException {
		  //throw the JasperPrint Objects in a list
		  java.util.List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
		  jasperPrintList.add(jasperPrint1);
		  jasperPrintList.add(jasperPrint2);


		  ByteArrayOutputStream baos = new ByteArrayOutputStream();     
		  JRPdfExporter exporter = new JRPdfExporter();     
		  //Add the list as a Parameter
		  exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jasperPrintList);
		  //this will make a bookmark in the exported PDF for each of the reports
		  exporter.setParameter(JRPdfExporterParameter.IS_CREATING_BATCH_MODE_BOOKMARKS, Boolean.TRUE);
		  exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);       
		  exporter.exportReport();      
		  //return baos.toByteArray();
		  return baos;
		}
	/*
	 * intX procedure codes from here
	 */
	/*
	 * This method takes to the new screen where we can enter the details for the New Empanelment details
	 * 
	 */
	public ActionForward doEnrollNewProvider(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside the doEnrollNewProvider method of NewEnrollAction");

			ArrayList<Object> alResult = new ArrayList<Object>();
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineManagerObject();
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile"));
			String strUserID = userSecurityProfile.getUSER_ID();
			HospitalDetailVO	hospitalDetailVO	=	null;
			Long firstTimeHospSeqId	=	userSecurityProfile.getHospSeqId();
			alResult = onlineAccessManagerObject.getOnlineProviderHomeDetails(strUserID);
			
			//if(firstTimeHospSeqId!=null){
				hospitalDetailVO = onlineAccessManagerObject.getHospitalDetail(strUserID);
			//}
				
				HashMap hmCityList = null;
				ArrayList alCityList = new ArrayList();
				if(hospitalDetailVO!=null)
				{
					hmCityList=onlineAccessManagerObject.getCityInfo(hospitalDetailVO.getAddressVO().getStateCode());
		            String countryCode	=	(String)(hmCityList.get("CountryId"));
		            if(hmCityList!=null){
		            	alCityList = (ArrayList)hmCityList.get(hospitalDetailVO.getAddressVO().getStateCode());
		            }//end of if(hmCityList!=null)
				}   
			if(hospitalDetailVO!=null)
			{
				
				long temp	=	hospitalDetailVO.getHospSeqId();
				userSecurityProfile.setHospSeqId(temp);
			}
			if(firstTimeHospSeqId==null)
				request.setAttribute("regAuthority", "regAuthority");
			
			DynaActionForm frmOnlineProvider = (DynaActionForm)form;
			if(hospitalDetailVO!=null){
				frmOnlineProvider = setFormValues(hospitalDetailVO,mapping,request);
			}

			frmOnlineProvider.set("alCityList",alCityList);
			frmOnlineProvider.set("regAuthority", alResult.get(0));
			frmOnlineProvider.set("hospitalName", alResult.get(1));
			frmOnlineProvider.set("authLicenceNo", alResult.get(2));
			frmOnlineProvider.set("caption", "Provider Details");
	        request.getSession().setAttribute("frmOnlineProvider",frmOnlineProvider);
			request.getSession().setAttribute("hospitalDetailVO", hospitalDetailVO);
	        request.setAttribute("LinkType", "general");
	        
	        return this.getForward(strOnlineProvider, mapping, request);
	        }//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, "onlineEnrollment"));
		}//end of catch(Exception exp)
	}//end of doEnrollNewProvider(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/*
	 * This method takes to the new screen where we can enter the details for the New Empanelment details
	 * 
	 */
	@SuppressWarnings("null")
	public ActionForward doInsuranceNewProvider(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside the doInsuranceNewProvider method of NewEnrollAction");

			ArrayList<Object> alResult = new ArrayList<Object>();
			int ihospSeqId	 =	0;
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineManagerObject();
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile"));
			String strUserID = userSecurityProfile.getUSER_ID();
			
			HospitalDetailVO hospitalDetailVO1	=	null;
			hospitalDetailVO1 = onlineAccessManagerObject.getHospitalDetail(strUserID);
			if(hospitalDetailVO1!=null)
			{
				long temp	=	hospitalDetailVO1.getHospSeqId();
				userSecurityProfile.setHospSeqId(temp);
			}
			if(hospitalDetailVO1==null){
					/*TTKException expTTK = new TTKException();
	                expTTK.setMessage("error.enrollNewProvider.required");
	                throw expTTK;*/
				request.setAttribute("NewEnroll", "Please Save Enroll Details first");
				return this.getForward(strOnlineHome, mapping, request);
			}
			
			OnlineAccessManager onlineAccessManager 	=	this.getOnlineManagerObject();
			HospitalDetailVO hospitalDetailVO	=	null;
			DynaActionForm frmOnlineProviderContacts = (DynaActionForm)form;
			frmOnlineProviderContacts.initialize(mapping);
			
			ArrayList alStdIsd	=	new ArrayList();
			alStdIsd	=	onlineAccessManager.getStdIsd(hospitalDetailVO1.getHospSeqId());
			frmOnlineProviderContacts.set("isdCode", alStdIsd.get(1));
			frmOnlineProviderContacts.set("stdCode", alStdIsd.get(0));
			
			hospitalDetailVO	=	onlineAccessManager.getContact(hospitalDetailVO1.getHospSeqId());
			
			if(hospitalDetailVO!=null){
			frmOnlineProviderContacts = (DynaActionForm)FormUtils.setFormValues("frmOnlineProviderContacts", hospitalDetailVO,
					this, mapping, request);
			frmOnlineProviderContacts.set("name", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getName()));
			frmOnlineProviderContacts.set("prefix", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getPrefix()));
			frmOnlineProviderContacts.set("activeYN", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getActiveYN()));
			frmOnlineProviderContacts.set("gender", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getGender()));
			frmOnlineProviderContacts.set("age", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getAge()));
			frmOnlineProviderContacts.set("mobileNbr", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getMobileNbr()));
			frmOnlineProviderContacts.set("primaryEmailID", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getPrimaryEmailID()));
			frmOnlineProviderContacts.set("secondaryEmailID", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getSecondaryEmailID()));
			frmOnlineProviderContacts.set("officePhone1", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getPhoneNbr1()));
			frmOnlineProviderContacts.set("designationTypeID", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getDesignationTypeID()));
			
			}
			frmOnlineProviderContacts.set("caption", "Insurance Details");
			frmOnlineProviderContacts.set("activeYN","Y");
			request.setAttribute("phnValues", "displayPhnValues");
	        request.getSession().setAttribute("frmOnlineProviderContacts",frmOnlineProviderContacts);
	        return this.getForward(strOnlineProviderContacts, mapping, request);
	        }//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, "onlineEnrollment"));
		}//end of catch(Exception exp)
	}//end of doInsuranceNewProvider(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
    		setOnlineLinks(request);
    		log.debug("Inside the doChangeState method of NewEnrollAction");
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineManagerObject();
			HashMap hmCityList = null;
			ArrayList alCityList = new ArrayList();
    		DynaActionForm frmOnlineProvider=(DynaActionForm)form;
            String stateCode	=	"";
            stateCode	=	(String)frmOnlineProvider.get("stateCode");
            request.getSession().setAttribute(stateCode, "stateCode");
            hmCityList=onlineAccessManagerObject.getCityInfo(stateCode);
            String countryCode	=	(String)(hmCityList.get("CountryId"));
            String isdcode	=	(String)(hmCityList.get("isdcode"));
            String stdcode	=	(String)(hmCityList.get("stdcode"));
            if(hmCityList!=null){
            	alCityList = (ArrayList)hmCityList.get(stateCode);
            }//end of if(hmCityList!=null)

             if(alCityList==null){
            	alCityList=new ArrayList();
            }//end of if(alCityList==null)
             //frmOnlineProvider.set("frmChanged","changed");
             
             frmOnlineProvider.set("caption", "Provider Details");
 	         request.setAttribute("general", "general");
 	        
             frmOnlineProvider.set("alCityList",alCityList);
             frmOnlineProvider.set("countryCode",countryCode);
             frmOnlineProvider.set("isdCode",isdcode);
             frmOnlineProvider.set("stdCode",stdcode);
            return this.getForward(strOnlineProvider,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
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
    @SuppressWarnings("null")
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));

    		log.debug("Inside the doSave method of NewEnrollAction");
			HashMap hmCityList = null;
			ArrayList alCityList = new ArrayList();
			HospitalDetailVO hospitalDetailVO=null;
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineManagerObject();
            DynaActionForm generalForm = (DynaActionForm)form;
            Long  lngHospSeqId=TTKCommon.getLong(TTKCommon.checkNull((String)generalForm.get("hospSeqId")));
            HospitalVO hospitalVO=null;
           
            hospitalDetailVO = getFormValues(generalForm,mapping,request);
            //if conditions for stop pre-auth and claim
            
            //get the address SEQ Id from the session which is set in get details
            HospitalDetailVO hospitalDetailVO2 = null;
            hospitalDetailVO2	=	(HospitalDetailVO)request.getSession().getAttribute("hospitalDetailVO"); 
            Long lAddSeqId	=	null;
            		if(hospitalDetailVO2!=null)
            			lAddSeqId	=	hospitalDetailVO2.getAddressVO().getAddrSeqId();
			
            //update the hospital details to data base
                lngHospSeqId = onlineAccessManagerObject.addUpdateHospital(hospitalDetailVO,userSecurityProfile.getUSER_SEQ_ID(),userSecurityProfile.getHospSeqId(),lAddSeqId);
            // setting the hospital seq id to session once seq Id is generated from save Enroll New Facility    
                userSecurityProfile.setHospSeqId(lngHospSeqId);
            //set the appropriate message
           if(lngHospSeqId > 0)
            {
                    request.setAttribute("updated","message.addedSuccessfully");
                    hospitalVO=new HospitalVO();
                    hospitalVO.setHospSeqId(lngHospSeqId);
                    hospitalVO.setHospitalName((String)generalForm.get("hospitalName"));
                   
            }//end of if(lngHospSeqId > 0)
            hospitalDetailVO = onlineAccessManagerObject.getHospitalDetail(userSecurityProfile.getUSER_ID());
              generalForm.initialize(mapping);
            String stateCode	=	(String)request.getSession().getAttribute("stateCode");
            hmCityList=onlineAccessManagerObject.getCityInfo(stateCode);
            if(hmCityList!=null){
            	alCityList = (ArrayList)hmCityList.get(hospitalDetailVO.getAddressVO().getStateCode());
            }//end of if(hmCityList!=null)

            DynaActionForm hospitalForm = setFormValues(hospitalDetailVO,mapping,request);
            hospitalForm.set("caption","Edit");
            hospitalForm.set("alCityList",alCityList);
            hospitalForm.set("landmarks", hospitalDetailVO.getLandmarks());
            
            
            /*
             * Save and Next code
             * 
             * */
			String strUserID = userSecurityProfile.getUSER_ID();
			
			HospitalDetailVO hospitalDetailVO1	=	null;
			hospitalDetailVO1 = onlineAccessManagerObject.getHospitalDetail(strUserID);
			if(hospitalDetailVO1!=null)
			{
				long temp	=	hospitalDetailVO1.getHospSeqId();
				userSecurityProfile.setHospSeqId(temp);
			}
			if(hospitalDetailVO1==null){
					/*TTKException expTTK = new TTKException();
	                expTTK.setMessage("error.enrollNewProvider.required");
	                throw expTTK;*/
				request.setAttribute("NewEnroll", "Please Save Enroll Details first");
				return this.getForward(strOnlineHome, mapping, request);
			}
			
			
			OnlineAccessManager onlineAccessManager 	=	this.getOnlineManagerObject();
			DynaActionForm frmOnlineProviderContacts = (DynaActionForm)form;
			ArrayList alStdIsd	=	new ArrayList();
			alStdIsd	=	onlineAccessManager.getStdIsd(hospitalDetailVO1.getHospSeqId());
			frmOnlineProviderContacts.set("isdCode", alStdIsd.get(1));
			frmOnlineProviderContacts.set("stdCode", alStdIsd.get(0));
			
			
			hospitalDetailVO	=	null;
			
			hospitalDetailVO	=	onlineAccessManager.getContact(hospitalDetailVO1.getHospSeqId());
			
			if(hospitalDetailVO!=null){
			frmOnlineProviderContacts = (DynaActionForm)FormUtils.setFormValues("frmOnlineProviderContacts", hospitalDetailVO,
					this, mapping, request);
			frmOnlineProviderContacts.set("name", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getName()));
			frmOnlineProviderContacts.set("prefix", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getPrefix()));
			frmOnlineProviderContacts.set("activeYN", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getActiveYN()));
			frmOnlineProviderContacts.set("gender", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getGender()));
			frmOnlineProviderContacts.set("age", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getAge()));
			frmOnlineProviderContacts.set("mobileNbr", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getMobileNbr()));
			frmOnlineProviderContacts.set("primaryEmailID", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getPrimaryEmailID()));
			frmOnlineProviderContacts.set("secondaryEmailID", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getSecondaryEmailID()));
			//frmOnlineProviderContacts.set("isdCode", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getIsdCode()));
			//frmOnlineProviderContacts.set("stdCode", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getStdCode()));
			frmOnlineProviderContacts.set("officePhone1", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getPhoneNbr1()));
			frmOnlineProviderContacts.set("designationTypeID", TTKCommon.checkNull(hospitalDetailVO.getPersonalInfoObj().getDesignationTypeID()));
			
			}
			frmOnlineProviderContacts.set("caption", "Contact Details");
			frmOnlineProviderContacts.set("activeYN","Y");
			request.setAttribute("phnValues", "displayPhnValues");
	        request.getSession().setAttribute("frmOnlineProviderContacts",frmOnlineProviderContacts);
            /*
             * 
             * 
             */
            request.getSession().setAttribute("frmOnlineProvider",hospitalForm);
            return this.getForward(strOnlineProviderContacts, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"NewEnrollAction"));
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
	@SuppressWarnings("null")
	public ActionForward doSaveUsers(ActionMapping mapping,ActionForm form,HttpServletRequest request,
								 HttpServletResponse response) throws Exception
	{
		try{
			log.debug("Inside NewEnrollAction doSaveUsers");
			setOnlineLinks(request);
    		UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));

			DynaActionForm frmOnlineProvider = (DynaActionForm)form;
			String strContactPath="";
			OnlineAccessManager onlineAccessManager 	=	this.getOnlineManagerObject();
			HospitalDetailVO hospitalDetailVO	=	new HospitalDetailVO();
			PersonalInfoVO	personalInfoVO	=	new PersonalInfoVO();
			DynaActionForm generalForm = (DynaActionForm)form;
			
			hospitalDetailVO = (HospitalDetailVO)FormUtils.getFormValues(generalForm,"frmOnlineProvider",
    				this,mapping,request);
			personalInfoVO.setName(TTKCommon.checkNull(request.getParameter("name")));
			personalInfoVO.setPrefix(TTKCommon.checkNull(request.getParameter("prefix")));
			personalInfoVO.setActiveYN(TTKCommon.checkNull(request.getParameter("activeYN")));
			personalInfoVO.setGender(TTKCommon.checkNull(request.getParameter("gender")));
			personalInfoVO.setAge(TTKCommon.checkNull(request.getParameter("age")));
			personalInfoVO.setDesignationTypeID(TTKCommon.checkNull(request.getParameter("designationTypeID")));
			personalInfoVO.setMobileNbr(TTKCommon.checkNull(request.getParameter("mobileNbr")));
			personalInfoVO.setPrimaryEmailID(TTKCommon.checkNull(request.getParameter("primaryEmailID")));
			personalInfoVO.setSecondaryEmailID(TTKCommon.checkNull(request.getParameter("secondaryEmailID")));
			personalInfoVO.setIsdCode(TTKCommon.checkNull(request.getParameter("isdCode")));
			personalInfoVO.setStdCode(TTKCommon.checkNull(request.getParameter("stdCode")));
			personalInfoVO.setPhoneNbr1(TTKCommon.checkNull(request.getParameter("officePhone1")));
			hospitalDetailVO.setPersonalInfoObj(personalInfoVO);
			
			long lUpdate=onlineAccessManager.saveHospContacts(hospitalDetailVO,userSecurityProfile.getHospSeqId(),userSecurityProfile.getUSER_SEQ_ID());
		
			if(lUpdate > 0)
			{
					request.setAttribute("updated","message.addedSuccessfully");
					//make a re query to get the details
					//hospitalDetailVO	=	onlineAccessManager.getContact(userSecurityProfile.getHospSeqId());
					/*hospitalDetailVO	=	onlineAccessManager.getContact(lUpdate);
					
					System.out.println("name::"+hospitalDetailVO.getPersonalInfoObj().getName());
					
					DynaActionForm frmOnlineProvider1 = (DynaActionForm)FormUtils.setFormValues("frmOnlineProvider", hospitalDetailVO,
															this, mapping, request);
					frmOnlineProvider1.set("name", hospitalDetailVO.getPersonalInfoObj().getName());
					frmOnlineProvider1.set("prefix", hospitalDetailVO.getPersonalInfoObj().getPrefix());
					frmOnlineProvider1.set("activeYN", hospitalDetailVO.getPersonalInfoObj().getActiveYN());
					frmOnlineProvider1.set("gender", hospitalDetailVO.getPersonalInfoObj().getGender());
					frmOnlineProvider1.set("age", hospitalDetailVO.getPersonalInfoObj().getAge());
					frmOnlineProvider1.set("mobileNbr", hospitalDetailVO.getPersonalInfoObj().getMobileNbr());
					frmOnlineProvider1.set("primaryEmailID", hospitalDetailVO.getPersonalInfoObj().getPrimaryEmailID());
					frmOnlineProvider1.set("secondaryEmailID", hospitalDetailVO.getPersonalInfoObj().getSecondaryEmailID());
					frmOnlineProvider1.set("isdCode", hospitalDetailVO.getPersonalInfoObj().getIsdCode());;
					frmOnlineProvider1.set("stdCode", hospitalDetailVO.getPersonalInfoObj().getStdCode());
					frmOnlineProvider1.set("officePhone1", hospitalDetailVO.getPersonalInfoObj().getPhoneNbr1());
				
//					frmOnlineProvider.set("caption","");
*/					request.getSession().setAttribute("frmOnlineProvider",frmOnlineProvider);
			}

			//return this.getForward(strOnlineProviderContacts, mapping, request);
	        return this.getForward(strProviderServicesList, mapping, request);

		}//end of try
		
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"NewEnrollAction"));
		}//end of catch(Exception exp)
	}//end of doSaveUsers(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    /**
     * Populates the form element to value object .
     * @param hospDetailVO HospitalDetailVO object.
     * @param mapping The ActionMapping used to select this instance.
     * @param request HttpServletRequest  object which contains hospital information.
     * @return hospDetailVO HospitalDetailVO object.
     */
    @SuppressWarnings("null")
	private HospitalDetailVO getFormValues(DynaActionForm generalForm,ActionMapping mapping,
    		HttpServletRequest request) throws TTKException
    {
        try
        {
            HospitalDetailVO hospDetailVO 	=	null;
            AddressVO addressVO 			=	new AddressVO();
           // PersonalInfoVO personalInfoVO	=	new PersonalInfoVO(); 
            
            hospDetailVO = (HospitalDetailVO)FormUtils.getFormValues(generalForm,"frmOnlineProvider",
            				this,mapping,request);
            
            addressVO.setAddress1(TTKCommon.checkNull(request.getParameter("address1")));
            addressVO.setAddress2(TTKCommon.checkNull(request.getParameter("address2")));
            addressVO.setAddress3(TTKCommon.checkNull(request.getParameter("address3")));
            addressVO.setStateCode(TTKCommon.checkNull(request.getParameter("stateCode")));
            addressVO.setCityCode(TTKCommon.checkNull(request.getParameter("cityCode")));
            addressVO.setPinCode(TTKCommon.checkNull(request.getParameter("pinCode")));
            addressVO.setCountryCode(TTKCommon.checkNull(request.getParameter("countryCode")));
            
            /*personalInfoVO.setName(TTKCommon.checkNull(request.getParameter("name")));
            personalInfoVO.setMobileNbr(TTKCommon.checkNull(request.getParameter("mobileNbr")));
            personalInfoVO.setDesignationTypeID(TTKCommon.checkNull(request.getParameter("designationTypeID")));
            personalInfoVO.setPrimaryEmailID(TTKCommon.checkNull(request.getParameter("primaryEmailID")));
            personalInfoVO.setSecondaryEmailID(TTKCommon.checkNull(request.getParameter("secondaryEmailID")));*/
            
            hospDetailVO.setAddressVO(addressVO);

            //hospDetailVO.setPersonalInfoObj(personalInfoVO);
            
            hospDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id
           
            return hospDetailVO;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp,"onlineEnrollment");
        }//end of catch
    }//end of getFormValues(DynaActionForm generalForm,ActionMapping mapping,HttpServletRequest request)
    
    /**
     * Populates the value object to form element.
     * @param hospDetailVO HospitalDetailVO object.
     * @param mapping The ActionMapping used to select this instance.
     * @param request HttpServletRequest  object which contains hospital information.
     * @return DynaActionForm object.
     */
    private DynaActionForm setFormValues(HospitalDetailVO hospitalDetailVO,ActionMapping mapping,
    		HttpServletRequest request) throws TTKException
    {
        try
        {
            DynaActionForm hospitalForm = (DynaActionForm)FormUtils.setFormValues("frmOnlineProvider",
            							   hospitalDetailVO,this,mapping,request);
            if(hospitalDetailVO!=null){
            hospitalForm.set("address1", TTKCommon.checkNull(hospitalDetailVO.getAddressVO().getAddress1()));
            hospitalForm.set("address2", TTKCommon.checkNull(hospitalDetailVO.getAddressVO().getAddress2()));
            hospitalForm.set("address3", TTKCommon.checkNull(hospitalDetailVO.getAddressVO().getAddress3()));
            hospitalForm.set("stateCode", TTKCommon.checkNull(hospitalDetailVO.getAddressVO().getStateCode()));
            hospitalForm.set("cityCode", TTKCommon.checkNull(hospitalDetailVO.getAddressVO().getCityCode()));
            hospitalForm.set("pinCode", TTKCommon.checkNull(hospitalDetailVO.getAddressVO().getPinCode()));
            hospitalForm.set("countryCode", TTKCommon.checkNull(hospitalDetailVO.getAddressVO().getCountryCode()));
            }
            
            return hospitalForm;
        }
        catch(Exception exp)
        {
            throw new TTKException(exp,"onlineEnrollment");
        }//end of catch
    }//end of setFormValues(HospitalDetailVO hospitalDetailVO,ActionMapping mapping,HttpServletRequest request)
    
    /*
	 * This method takes to the new screen where we can enter the details for the New Empanelment details
	 * 
	 */
	public ActionForward doViewServicesList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			
			setOnlineLinks(request);
			log.info("Inside the doViewServicesList method of NewEnrollAction");
			DynaActionForm frmOnlineProvider=(DynaActionForm)form;
			HospitalDetailVO hospitalDetailVO1	=	null;
			OnlineAccessManager onlineAccessManagerObject	=	this.getOnlineManagerObject();
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile"));
			String strUserID = userSecurityProfile.getUSER_ID();
			
			hospitalDetailVO1 = onlineAccessManagerObject.getHospitalDetail(strUserID);
			if(hospitalDetailVO1==null){
					/*TTKException expTTK = new TTKException();
	                expTTK.setMessage("error.enrollNewProvider.required");
	                throw expTTK;*/
				request.setAttribute("NewEnroll", "Please Save Enroll Details first");
				return this.getForward(strOnlineHome, mapping, request);
			}
			
	        request.getSession().setAttribute("frmOnlineProvider",frmOnlineProvider);
	        return this.getForward(strProviderServicesList, mapping, request);}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp, "onlineEnrollment"));
		}//end of catch(Exception exp)
	}//end of doViewServicesList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
    
	
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
    		setOnlineLinks(request);
    		log.debug("Inside the doClose method of OnlinHomeAction");
    		
          //  request.getSession().setAttribute("frmAddHospital",generalForm);
            return this.getForward(strOnlineHome, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
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
    public ActionForward doCloseServices(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.info("Inside the doCloseServices method of OnlinHomeAction");
    		
          //  request.getSession().setAttribute("frmAddHospital",generalForm);
            return this.getForward(strProviderServicesList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to display all the Grading links.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doServicesAdd(ActionMapping mapping,ActionForm form,
    		HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doAddServices method of NewEnrollAction");
    		setOnlineLinks(request);
    		String groupName	=	request.getParameter("serviceType");
    		
    		String 	header	=	groupName;
			if(header.equals("Primary Care Services") || header.equals("Secondary Care Services")|| header.equals("Tertiary Care Services"))
				header	=	"Primary Care Services";
			else if(header.equals("Staff Information") || header.equals("IPD Beds")|| header.equals("Operation Theatres")
					|| header.equals("Ambulances") || header.equals("OPD"))
				header	=	"Staff Information";
			else if(header.equals("Radiology") || header.equals("Laboratory")|| header.equals("Clinical Units")
					|| header.equals("Other Investigations"))
				header	=	"Radiology";
			else if(header.equals("Emergency Services") || header.equals("Establishment/Infrastructure")|| header.equals("Infection Control Measures")
					|| header.equals("Integrated Hospital Information System") || header.equals("Accreditation/Quality Grading"))
				header	=	"Emergency Services";
    		DynaActionForm  frmAddServices = (DynaActionForm)form;
    		frmAddServices.initialize(mapping);
    		frmAddServices.set("facilityHeader",groupName);
    		frmAddServices.set("caption",groupName);
    		frmAddServices.set("header",header);
    		request.setAttribute("header", header);
			return this.getForward(strAddSpecialities, mapping, request);
			//return mapping.findForward(strAddSpecialities);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doServicesAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doSaveSpecialities(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSaveSpecialities method of NewEmrollAction");
    		setOnlineLinks(request);
    		UserSecurityProfile userSecurityProfile =(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		OnlineFacilityVO onlineFacilityVO = new OnlineFacilityVO();	
    		
    		DynaActionForm generalForm = (DynaActionForm)form;
    		
    		onlineFacilityVO = (OnlineFacilityVO)FormUtils.getFormValues(generalForm,"frmAddSpecialities",
    				this,mapping,request);
    		onlineFacilityVO.setAddedBy(userSecurityProfile.getUSER_SEQ_ID());
    		
			GradingManager gradingObject=this.getGradingObject();
			int count=gradingObject.addUpdateServices(onlineFacilityVO);
			if(count>0)
			{
				request.setAttribute("updated","message.saved");
			}//end of if(count>0)
			
			String header	=	onlineFacilityVO.getFacilityHeader();
			if(header.equals("Primary Care Services") || header.equals("Secondary Care Services")|| header.equals("Tertiary Care Services"))
				header	=	"Primary Care Services";
			else if(header.equals("Staff Information") || header.equals("IPD Beds")|| header.equals("Operation Theatres")
					|| header.equals("Ambulances") || header.equals("OPD"))
				header	=	"Staff Information";
			else if(header.equals("Radiology") || header.equals("Laboratory")|| header.equals("Clinical Units")
					|| header.equals("Other Investigations"))
				header	=	"Radiology";
			else if(header.equals("Emergency Services") || header.equals("Establishment/Infrastructure")|| header.equals("Infection Control Measures")
					|| header.equals("Integrated Hospital Information System") || header.equals("Accreditation/Quality Grading"))
				header	=	"Emergency Services";
			generalForm.set("header",header);
			return this.getForward(strAddSpecialities, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doSaveSpecialities(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doViewServiceList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		log.info("Inside the doViewServiceList method of NewEnrollAction");
    		setOnlineLinks(request);
    		ArrayList alGrading=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		GradingServicesVO gradingServicesVO=new GradingServicesVO();
			DynaActionForm frmAddServices = (DynaActionForm)FormUtils.setFormValues("frmAddServices",
											 gradingServicesVO, this, mapping, request);
    		alGrading=gradingObject.getProvServices("SERV",userSecurityProfile.getUSER_ID());
			if(alGrading==null){
				alGrading=new ArrayList();
			}//end of if(alGrading==null)
			frmAddServices.set("hospGrad",alGrading);
			frmAddServices.set("caption",TTKCommon.getWebBoardDesc(request));
			request.setAttribute("frmAddServices",frmAddServices);
			return this.getForward(strServices, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doViewServiceList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
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
    public ActionForward doSaveServices(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.info("Inside the doSaveServices method of NewEnrollAction");
    		UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		setOnlineLinks(request);
    		ArrayList alGrading=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
			DynaActionForm generalForm = (DynaActionForm)form;
    		DynaActionForm frmGradingServices = (DynaActionForm)request.getSession().getAttribute("frmAddServices");
			GradingServicesVO gradingServicesVO=new GradingServicesVO();
			gradingServicesVO=(GradingServicesVO)FormUtils.getFormValues(frmGradingServices, "frmAddServices",this, mapping, request);
			gradingServicesVO.setAnswer1List((String[])frmGradingServices.get("selectedAnswer1List"));
			gradingServicesVO.setAnswer2List((String[])frmGradingServices.get("selectedAnswer2List"));
			gradingServicesVO.setMedicalTypeIdList((String[])frmGradingServices.get("selectedMedicalTypeId"));
			gradingServicesVO.setMedicalSeqIdList((Long[])frmGradingServices.get("selectedMedicalSeqId"));
			gradingServicesVO.setHospSeqId(userSecurityProfile.getHospSeqId());
			gradingServicesVO.setUpdatedBy(userSecurityProfile.getUSER_SEQ_ID());//User ID
			int count=gradingObject.addUpdateServices(gradingServicesVO);
			if(count>0)
			{
				request.setAttribute("updated","message.saved");
			}//end of if(count>0)
			alGrading=gradingObject.getProvServices("SERV",userSecurityProfile.getUSER_ID());//User ID
			DynaActionForm  frmAddServices = (DynaActionForm)FormUtils.setFormValues("frmAddServices",
											  gradingServicesVO, this, mapping, request);
			frmAddServices.set("hospGrad",alGrading);
			frmAddServices.set("caption",TTKCommon.getWebBoardDesc(request));
			request.setAttribute("frmAddServices",frmAddServices);
			return this.getForward(strServices, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doSaveServices(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
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
    public ActionForward doViewMedicalServices(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		log.info("Inside the doViewMedicalServices method of OnlinHomeAction");
    		setOnlineLinks(request);
    		ArrayList alGrading=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		GradingServicesVO gradingServicesVO=new GradingServicesVO();
			DynaActionForm frmGradingMID = (DynaActionForm)FormUtils.setFormValues("frmGradingMID",
											gradingServicesVO, this, mapping, request);
			alGrading=gradingObject.getProvServices("MIDT",userSecurityProfile.getUSER_ID());//hospital seq id from web board
			if(alGrading==null){
            	alGrading=new ArrayList();
            }//end of if(alGrading==null)
            frmGradingMID.set("hospGrad",alGrading);
			frmGradingMID.set("caption",TTKCommon.getWebBoardDesc(request));
			request.setAttribute("frmGradingMID",frmGradingMID);
			return this.getForward(strMedicalInfraStructure, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doViewMedicalServices(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doSaveMedicalServices(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSaveMedicalServices method of NewEnrollAction");
    		UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		setLinks(request);
    		ArrayList alGrading=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		DynaActionForm frmGradmedicalServices=(DynaActionForm)request.getSession().getAttribute("frmGradingMID");
			GradingServicesVO gradingServicesVO=new GradingServicesVO();
			gradingServicesVO =(GradingServicesVO)FormUtils.getFormValues(frmGradmedicalServices, "frmGradingMID",
								this, mapping, request);
			gradingServicesVO.setAnswer1List((String[])frmGradmedicalServices.get("selectedAnswer1"));
			gradingServicesVO.setMedicalTypeIdList((String[])frmGradmedicalServices.get("selectedMedicalTypeId"));
			gradingServicesVO.setMedicalSeqIdList((Long[])frmGradmedicalServices.get("selectedMedicalSeqId"));
			gradingServicesVO.setHospSeqId(userSecurityProfile.getHospSeqId());
			gradingServicesVO.setUpdatedBy(userSecurityProfile.getUSER_SEQ_ID());//User ID
			int count=gradingObject.addUpdateServices(gradingServicesVO);
			if(count>0)
			{
				//alGrading=gradingObject.getServices("MIDT",TTKCommon.getWebBoardId(request));
				alGrading=gradingObject.getProvServices("MIDT",userSecurityProfile.getUSER_ID());//hospital seq id from web board
				DynaActionForm  frmGradingMID = (DynaActionForm)FormUtils.setFormValues("frmGradingMID",
												 gradingServicesVO, this, mapping, request);
				frmGradingMID.set("hospGrad",alGrading);
				frmGradingMID.set("caption",TTKCommon.getWebBoardDesc(request));
				request.setAttribute("updated","message.saved");
				request.setAttribute("frmGradingMID",frmGradingMID);
			}//end of if(count>0)
			return this.getForward(strMedicalInfraStructure,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doSaveMedicalServices(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doViewClinicalServices(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewClinicalServices method of OnlinHomeAction");
    		UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		setOnlineLinks(request);
    		ArrayList alGrading=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		GradingServicesVO gradingServicesVO=new GradingServicesVO();
			DynaActionForm frmGradingClinicalSupport = (DynaActionForm)FormUtils.setFormValues("frmGradingClinicalSupport",
														gradingServicesVO, this, mapping, request);
			alGrading=gradingObject.getProvServices("CLSS",userSecurityProfile.getUSER_ID());//hospital seq id from web board
			if(alGrading==null){
            	alGrading=new ArrayList();
            }//end of if(alGrading==null)
            frmGradingClinicalSupport.set("hospGrad",alGrading);
			frmGradingClinicalSupport.set("caption",TTKCommon.getWebBoardDesc(request));
			request.setAttribute("frmGradingClinicalSupport",frmGradingClinicalSupport);
			return this.getForward(strClinicalSupport, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doViewClinicalServices(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doSaveClinicalServices(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSaveClinicalServices method of NewEnrollAction");
    		UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		setLinks(request);
    		ArrayList alGrading=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		DynaActionForm frmGradClincalServices=(DynaActionForm)request.getSession().getAttribute("frmGradingClinicalSupport");
			GradingServicesVO gradingServicesVO=new GradingServicesVO();
			gradingServicesVO =(GradingServicesVO)FormUtils.getFormValues(frmGradClincalServices, 
								"frmGradingClinicalSupport",this, mapping, request);
			gradingServicesVO.setAnswer1List((String[])frmGradClincalServices.get("selectedAnswer1List"));
			gradingServicesVO.setMedicalTypeIdList((String[])frmGradClincalServices.get("selectedMedicalTypeId"));
			gradingServicesVO.setMedicalSeqIdList((Long[])frmGradClincalServices.get("selectedMedicalSeqId"));
			gradingServicesVO.setHospSeqId(userSecurityProfile.getHospSeqId());
			gradingServicesVO.setUpdatedBy(userSecurityProfile.getUSER_SEQ_ID());//User ID
			int count=gradingObject.addUpdateServices(gradingServicesVO);
			if(count>0)
			{
				request.setAttribute("updated","message.saved");
			}//end of if(count>0)
//			alGrading=gradingObject.getServices("CLSS",TTKCommon.getWebBoardId(request));//hospital seq id from web board
			alGrading=gradingObject.getProvServices("CLSS",userSecurityProfile.getUSER_ID());//hospital seq id from web board
			DynaActionForm  frmGradingClinicalSupport = (DynaActionForm)FormUtils.
							setFormValues("frmGradingClinicalSupport",gradingServicesVO, this, mapping, request);
			frmGradingClinicalSupport.set("hospGrad",alGrading);
			frmGradingClinicalSupport.set("caption",TTKCommon.getWebBoardDesc(request));
			request.setAttribute("frmGradingClinicalSupport",frmGradingClinicalSupport);
			return this.getForward(strClinicalSupport, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doSaveClinicalServices(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doViewOtherDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewOtherDetails method of NewEnrollAction");
    		UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");

    		setOnlineLinks(request);
    		ArrayList alGrading=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		GradingServicesVO gradingServicesVO=new GradingServicesVO();
			DynaActionForm frmGradingOtherDetails = (DynaActionForm)FormUtils.setFormValues("frmOtherDetails",
													 gradingServicesVO, this, mapping, request);
			alGrading=gradingObject.getProvServices("OTDT",userSecurityProfile.getUSER_ID());//hospital seq id from web board
			if(alGrading==null){
            	alGrading=new ArrayList();
            }//end of if(alGrading==null)
			frmGradingOtherDetails.set("hospGrad",alGrading);
			frmGradingOtherDetails.set("caption",TTKCommon.getWebBoardDesc(request));
			request.setAttribute("frmOtherDetails",frmGradingOtherDetails);
			return this.getForward(strOtherDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doViewOtherDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doSaveOtherDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSaveOtherDetails method of NewEnrollAction");
    		UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		setOnlineLinks(request);
    		ArrayList alGrading=null;
    		//get the session bean from the bean pool for each executing thread
			GradingManager gradingObject=this.getGradingObject();
    		DynaActionForm frmGradOtherDetails = (DynaActionForm)request.getSession().getAttribute("frmOtherDetails");
			GradingServicesVO gradingServicesVO=new GradingServicesVO();
			gradingServicesVO =(GradingServicesVO)FormUtils.getFormValues(frmGradOtherDetails, "frmOtherDetails",
								this, mapping, request);
			gradingServicesVO.setAnswer1List((String[])frmGradOtherDetails.get("selectedAnswer1List"));
			gradingServicesVO.setMedicalTypeIdList((String[])frmGradOtherDetails.get("selectedMedicalTypeId"));
			gradingServicesVO.setMedicalSeqIdList((Long[])frmGradOtherDetails.get("selectedMedicalSeqId"));
			gradingServicesVO.setHospSeqId(userSecurityProfile.getHospSeqId());
			gradingServicesVO.setUpdatedBy(userSecurityProfile.getUSER_SEQ_ID());//User ID
			int count=gradingObject.addUpdateServices(gradingServicesVO);
			if(count>0)
			{
				request.setAttribute("updated","message.saved");
			}//end of if(count>0)
			//alGrading=gradingObject.getServices("OTDT",TTKCommon.getWebBoardId(request));//hospital seq id from web board
			alGrading=gradingObject.getProvServices("OTDT",userSecurityProfile.getUSER_ID());//hospital seq id from web board
			DynaActionForm  frmOtherDetails = (DynaActionForm)FormUtils.setFormValues("frmOtherDetails",
											   gradingServicesVO, this, mapping, request);
			frmOtherDetails.set("hospGrad",alGrading);
			frmOtherDetails.set("caption",TTKCommon.getWebBoardDesc(request));
			request.setAttribute("frmOtherDetails",frmOtherDetails);
			return this.getForward(strOtherDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doSaveOtherDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doViewOverallDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewOverallDetails method of NewEnrollAction");
    		UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");

    		setOnlineLinks(request);
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
			InfraStructureVO infrastrutureVO= null;
			infrastrutureVO=gradingObject.getProvInfraStructureInfo(userSecurityProfile.getUSER_ID());
			if(infrastrutureVO == null){
				infrastrutureVO = new InfraStructureVO();
			}//end of if(infrastrutureVO == null)
			DynaActionForm  frmGradOverallDetail = (DynaActionForm)FormUtils.setFormValues("frmGradingInfraStr",
												   infrastrutureVO, this, mapping, request);
			frmGradOverallDetail.set("builtUpArea",infrastrutureVO.getBuiltUpArea()!=null? 
						infrastrutureVO.getBuiltUpArea().toString():"");
			frmGradOverallDetail.set("openArea",infrastrutureVO.getOpenArea()!=null? 
					infrastrutureVO.getOpenArea().toString():"");
			frmGradOverallDetail.set("costOfArea",infrastrutureVO.getCostOfArea()!=null? 
					infrastrutureVO.getCostOfArea().toString():"");
			frmGradOverallDetail.set("infrastrSeqId",infrastrutureVO.getInfrastrSeqId());
			
			//projectX
			frmGradOverallDetail.set("location",infrastrutureVO.getLocation());
			frmGradOverallDetail.set("category",infrastrutureVO.getCategory());
			
			frmGradOverallDetail.set("caption",TTKCommon.getWebBoardDesc(request));
			request.setAttribute("frmGradingInfraStr",frmGradOverallDetail);
			return this.getForward(strGradingInfraStr, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doViewOverallDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
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
    public ActionForward doSaveOverallDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSaveOverallDetails method of NewEnrollAction");
    		UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		setOnlineLinks(request);
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		DynaActionForm frmGradOverallDetails=(DynaActionForm)request.getSession().getAttribute("frmGradingInfraStr");
			//frmGradOverallDetails.set("caption",TTKCommon.getWebBoardDesc(request));
			InfraStructureVO infrastrutureVO=new InfraStructureVO();
			infrastrutureVO =(InfraStructureVO)FormUtils.getFormValues(frmGradOverallDetails, 
					"frmGradingInfraStr",this, mapping, request);
			infrastrutureVO.setInfrastrSeqId((Long)frmGradOverallDetails.get("infrastrSeqId"));
			infrastrutureVO.setHospSeqId(userSecurityProfile.getHospSeqId());
			infrastrutureVO.setUpdatedBy(userSecurityProfile.getUSER_SEQ_ID());//User ID
			int count=gradingObject.addUpdateInfraStructureDetails(infrastrutureVO);
			if(count>0)
			{
				request.setAttribute("updated","message.saved");
			}//end of if(count>0)
			DynaActionForm  frmGradOverall = (DynaActionForm)FormUtils.setFormValues("frmGradingInfraStr",
					infrastrutureVO, this, mapping, request);
			frmGradOverall.set("caption",TTKCommon.getWebBoardDesc(request));
			request.setAttribute("frmGradingInfraStr",frmGradOverall);
			return this.getForward(strGradingInfraStr, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doSaveOverallDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    
    /**
	 * Returns the Grading  session object for invoking methods on it.
	 * @return Grading session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private GradingManager getGradingObject() throws TTKException
	{
		GradingManager gradingManager = null;
		try
		{
			if(gradingManager == null)
			{
				InitialContext ctx = new InitialContext();
				gradingManager = (GradingManager) ctx.lookup("java:global/TTKServices/business.ejb3/GradingManagerBean!com.ttk.business.empanelment.GradingManager");
			}//end of if(gradingManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "grading");
		}//end of catch(Exception exp)
		return gradingManager;
	}//end of getGradingObject()
   
}//end of class NewEnrollAction
