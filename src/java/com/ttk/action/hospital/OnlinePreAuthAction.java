package com.ttk.action.hospital;

/**
 * as per Hospital Login
 * @ (#) OnlinePreAuthAction Mar 24, 2014
 *  Author       :Kishor kumar S h
 * Company      : Rcs Technologies
 * Date Created : April 29 ,2015
 *
 * @author       :Kishor kumar S h
 * Modified by   :
 * Modified date :
 * Reason        :
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.onlineforms.OnlinePreAuthManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.empanelment.LabServiceVO;
import com.ttk.dto.onlineforms.providerLogin.PreAuthSearchVO;
import com.ttk.dto.preauth.ActivityDetailsVO;
import com.ttk.dto.preauth.CashlessDetailVO;
import com.ttk.dto.preauth.ClinicianDetailsVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
import com.ttk.dto.preauth.DrugDetailsVO;
import com.ttk.dto.preauth.ObservationDetailsVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for Searching the List Policies to see the Online Account Info.
 * This also provides deletion and updation of products.
 */
public class OnlinePreAuthAction extends TTKAction {

private static HashMap<String, String> providerList	=	null;
private static HashMap<String, String> icdSedc	=	null;
private static HashMap<String, String> activityCode	=	null;
private static HashMap<String, String> activityDesc	=	null;
private static HashMap<String, String> drugCode	=	null;
private static HashMap<String, String> drugDesc	=	null;

    private static final Logger log = Logger.getLogger( OnlinePreAuthAction.class );

    //Modes.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";

    // Action mapping forwards.
    private static final String strOnlinePreAuth	=	"onlinePreAuth";
    private static String strForwardValidate		=	"onlinePreAuth";
    private static String strPreauthInvoice			=	"preauthInvoice";
    private static final String strAddConsumables	=	"addConsumables";
    private static final String strAddPharmacy		=	"addPharmacy";

	 private static final String strONLINEPREAUTH	=	"onlinePreAuth";
	 private static final String strAddActivityDetails=	"addActivityDetails";
	 private static final String strONLINEPREAUTHSUCCESS=	"preAuthSuccess";
	 private static final String strCliniciansList=	"onlineClinicianList";
	 
    
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
            log.debug("Inside the doDefault method of OnlinePreAuthAction");
            setOnlineLinks(request);
            
            return this.getForward(strOnlinePreAuth, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
        }//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)

    
    
         
   
    private OnlinePreAuthManager getOnlineAccessManagerObject() throws TTKException
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
            throw new TTKException(exp, strOnlinePreAuth);
        }//end of catch
        return onlinePreAuthManager;
    }//end of getOnlineAccessManagerObject()

    
    
    public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setOnlineLinks(request);
    		log.debug("Inside the doProceedPreAuthSubmission method of OnlineCashlessHospAction");
    		DynaActionForm frmOnlinePreAuth =(DynaActionForm)request.getSession().getAttribute("frmCashlessAdd");
    		//frmCashlessAdd.initialize(mapping);     //reset the form data
    		HttpSession session	=	request.getSession();
    		CashlessDetailVO cashlessDetailVO	=	new CashlessDetailVO();
    		
    		OnlinePreAuthManager onlinePreAuthManager = this.getOnlineAccessManagerObject();
    		String rownum	=	(String)request.getParameter("rownum");
			TableData tableData = (TableData) request.getSession().getAttribute("tableData");
			PreAuthSearchVO preAuthSearchVO	=	null;
			preAuthSearchVO = (PreAuthSearchVO)tableData.getRowInfo(Integer.parseInt(rownum));
			request.getSession().setAttribute("lPreAuthIntSeqId", preAuthSearchVO.getPatAuthSeqId());
			Object[] preauthAllresult	=	onlinePreAuthManager.getOnlinePartialPreAuthDetails(preAuthSearchVO.getPatAuthSeqId());
    		
			cashlessDetailVO = (CashlessDetailVO) preauthAllresult[0];
			ArrayList<DiagnosisDetailsVO> diagnosis = (ArrayList<DiagnosisDetailsVO>) preauthAllresult[1];
			ArrayList<ActivityDetailsVO> activities = (ArrayList<ActivityDetailsVO>) preauthAllresult[2];
			ArrayList<DrugDetailsVO> aldrugs = (ArrayList<DrugDetailsVO>) preauthAllresult[3];
			session.setAttribute("preauthDiagnosis", diagnosis);
			session.setAttribute("preauthActivities", activities);
			session.setAttribute("preauthDrugs", aldrugs);
			
    		DiagnosisDetailsVO diagnosisDetailsVO	=	new DiagnosisDetailsVO();//diagnosis details
    		ActivityDetailsVO activityDetailsVO		=	new ActivityDetailsVO();//activity Details
    		/*cashlessDetailVO = (CashlessDetailVO)FormUtils.getFormValues(frmOnlinePreAuth,"frmCashlessAdd",
    				this,mapping,request);*/

    		cashlessDetailVO.setDiagnosisDetailsVO(diagnosisDetailsVO);
    		cashlessDetailVO.setActivityDetailsVO(activityDetailsVO);
    		cashlessDetailVO.setDrugDetailsVO(new DrugDetailsVO());
    		
    		DynaActionForm onlinePreAuthForm = setFormValues(cashlessDetailVO,mapping,request);
    		
    		request.getSession().setAttribute("frmOnlinePreAuth", onlinePreAuthForm);
    		request.getSession().setAttribute("preauthDiagnosis", diagnosis);
    		request.getSession().setAttribute("preauthActivities", activities);
    		request.getSession().setAttribute("preauthDrugs", aldrugs);
    		
    		request.setAttribute("fromFlag", request.getAttribute("fromFlag"));
    		return this.getForward(strONLINEPREAUTH, mapping, request);
    		}//end of try
    	catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
		/**
		 * @param mapping ActionMapping
		 * @param form 
		 * @param request
		 * @param response
		 * @return true if vidal ID is already got OTP
		 * @throws Exception
		 */
		public ActionForward doValidateEnrollId(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                HttpServletResponse response) throws Exception{
			try{
				setOnlineLinks(request);
				log.debug("Inside the doValidateEnrollId method of OnlinePreAuthAction");
				DynaActionForm frmOnlinePreAuth =(DynaActionForm)form;
				String vidalId	=	frmOnlinePreAuth.getString("vidalId");
				
				UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
				OnlinePreAuthManager onlinePreAuthManager= null;
				CashlessDetailVO cashlessDetailVO	=	null;
				
				onlinePreAuthManager = this.getOnlineAccessManagerObject();
				String flag	=	"";
				//below function to check the OTP has generated for the vidal ID  on that day
				flag	= onlinePreAuthManager.getValidateVidalId(vidalId);
			//	flag	=	"Y";
				if("Y".equals(flag))
				{
					cashlessDetailVO	= onlinePreAuthManager.geMemberDetailsOnEnrollId(vidalId,(String)request.getSession().getAttribute("benifitTypeVal"));
					frmOnlinePreAuth = (DynaActionForm)FormUtils.setFormValues("frmOnlinePreAuth",
							cashlessDetailVO,this,mapping,request);
					request.setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
					frmOnlinePreAuth.set("enrollId", vidalId);
					frmOnlinePreAuth.set("providerName", userSecurityProfile.getHospitalName());
					
					strForwardValidate	=	"showpreauth";
				}
				else
				{
					strForwardValidate	=	"doEligibilityCheck";
				}
				//	frmOnlinePreAuth.set("enrollId", request.getParameter("enrollId"));
				//finally return to the grid screen
				request.setAttribute("vidalId", vidalId);
				request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
				return this.getForward(strForwardValidate, mapping, request);
			}//end of try
		catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
		}//end of catch(Exception exp)
		}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
		
		
		
		/**
	     * 
	     * @param mapping doaddConsumablesList
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public ActionForward doaddConsumablesList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
	    	try{
	    		log.debug("Inside the doaddConsumablesList method of ProviderPrescriptionAction");
	    		setOnlineLinks(request);
	    		/*UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	    		//get the session bean from the bean pool for each excecuting thread
	    		ArrayList alMinorServices	=	null;
	    		OnlineAccessManager onlineAccessManager=null;
	    		onlineAccessManager = this.getOnlineAccessManagerObject();
	    		LaboratoryServicesVO laboratoryServicesVO =new LaboratoryServicesVO();
				DynaActionForm frmCashlessPrescription = (DynaActionForm)FormUtils.setFormValues("frmCashlessPrescription",
						laboratoryServicesVO, this, mapping, request);
				
				alMinorServices=onlineAccessManager.getLabServices("LABORATORY",userSecurityProfile.getHospSeqId());
				if(alMinorServices==null){
					alMinorServices=new ArrayList();
				}//end of if(alGrading==null)
				frmCashlessPrescription.set("hospLabs",alMinorServices);
				request.setAttribute("hospLabs",alMinorServices);
				frmCashlessPrescription.set("caption","");
				request.setAttribute("frmCashlessPrescription",frmCashlessPrescription);
				request.setAttribute("Prescription_Type", "Consumables");*/
				return this.getForward(strAddConsumables, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processOnlineExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
			}//end of catch(Exception exp)
	    }//end of doaddConsumablesList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	    
	    
	    /**
	     * 
	     * @param mapping doaddConsumablesList
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public ActionForward doaddPharmacyList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
	    	try{
	    		log.debug("Inside the doAddPharmacyList method of ProviderPrescriptionAction");
	    		setOnlineLinks(request);
				return this.getForward(strAddPharmacy, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processOnlineExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
			}//end of catch(Exception exp)
	    }//end of doAddPharmacyList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	    
	    
	    /**
		 * Saving the Online PreAuth Details General basic Details
		 */
	    
	    public ActionForward doSaveGeneral(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                HttpServletResponse response) throws Exception{
			try{
				setOnlineLinks(request);
				log.debug("Inside the doSaveGeneral method of OnlinePreAuthAction");
				DynaActionForm frmOnlinePreAuth =(DynaActionForm)form;
				CashlessDetailVO cashlessDetailVO = null;
				cashlessDetailVO	=	(CashlessDetailVO)FormUtils.getFormValues(frmOnlinePreAuth,"frmOnlinePreAuth",
         				this,mapping,request);
					
				UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));

				OnlinePreAuthManager onlinePreAuthManager	=	null;
				onlinePreAuthManager = this.getOnlineAccessManagerObject();
				Long	lPreAuthIntSeqId	=	null;
				
				//lPreAuthIntSeqId	= 	onlinePreAuthManager.savePreAuthDetails(cashlessDetailVO,null,userSecurityProfile,null);
				
				cashlessDetailVO	=	null;
				if(lPreAuthIntSeqId>0)
				{
					cashlessDetailVO=	onlinePreAuthManager.getPreAuthDetails(lPreAuthIntSeqId);
				}
				DiagnosisDetailsVO diagnosisDetailsVO	=	new DiagnosisDetailsVO();
				cashlessDetailVO.setDiagnosisDetailsVO(diagnosisDetailsVO);
				
				frmOnlinePreAuth = setFormValues(cashlessDetailVO,mapping,request);
				
				request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
				//request.getSession().setAttribute("cashlessDetailVO", cashlessDetailVO);
				request.getSession().setAttribute("lPreAuthIntSeqId", lPreAuthIntSeqId);
				
				request.setAttribute("updated", "message.savedSuccessfully");
				request.setAttribute("MemberSave", "MemberSave"); 
				return this.getForward(strONLINEPREAUTH, mapping, request);
			//	return this.getForward(strPreauthInvoice, mapping, request);
			}//end of try
		catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
		}//end of catch(Exception exp)
		}//end of doSaveGeneral(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
	    
		/**
		 * Saving the Online PreAuth Details and File Upload
		 */
		
		
		public ActionForward doSavePreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                HttpServletResponse response) throws Exception{
			try{
				setOnlineLinks(request);
				log.debug("Inside the doSavePreAuth method of OnlinePreAuthAction");
				DynaActionForm frmOnlinePreAuth =(DynaActionForm)form;
				String vidalId	=	frmOnlinePreAuth.getString("vidalId");
				
			
				
				 CashlessDetailVO cashlessDetailVO = null;
				 cashlessDetailVO	=	(CashlessDetailVO)FormUtils.getFormValues(frmOnlinePreAuth,"frmOnlinePreAuth",
         				this,mapping,request);
					
					
				HashMap prescriptions	=	(HashMap) request.getSession().getAttribute("prescriptions");
				ArrayList allabs		= null;
				ArrayList alRadios		= null;
				ArrayList alSurgeries	= null;
				ArrayList alConsumables	= null;
				ArrayList alMinors		= null;
				if(prescriptions!=null){
					allabs			= (ArrayList)prescriptions.get("LABORATORY");
					alRadios		= (ArrayList)prescriptions.get("RADIOLOGY");
					alSurgeries		= (ArrayList)prescriptions.get("SURGERIES");
					alConsumables	= (ArrayList)prescriptions.get("CONSUMABLES");
					alMinors		= (ArrayList)prescriptions.get("MINORSURGERIES");
				}
				
				Set KeySet	=	 prescriptions.keySet();
				Iterator itr = (Iterator) prescriptions.keySet().iterator();
				LabServiceVO labServiceVO	=	null;
				StringBuffer medicalIds		=	new StringBuffer();
				
			    while (itr.hasNext()){
			      allabs	=	(ArrayList)prescriptions.get(itr.next());
			      for(int k=0;k<allabs.size();k++){
			    	  labServiceVO	=	(LabServiceVO) allabs.get(k);
			    	  medicalIds		=	medicalIds.append("|").append(labServiceVO.getMedicalTypeId());
			      }
			    }
			    medicalIds.append("|");
				UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
				
				OnlinePreAuthManager onlinePreAuthManager= null;
				onlinePreAuthManager = this.getOnlineAccessManagerObject();
				int iResult	=	0;
				FormFile formFile = null;
				formFile = (FormFile)frmOnlinePreAuth.get("uploadFile");
				
				//F I L E UPLOAD S T A R T S
				FileOutputStream outputStream = null;
        		String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("mouUploads"));
    	        File folder = new File(path);
				if(!folder.exists()){
					folder.mkdir();
				}
				String finalPath=(path+formFile+"_"+new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date()));
				outputStream = new FileOutputStream(new File(finalPath));
				outputStream.write(formFile.getFileData());
				outputStream.close();
				//F I L E UPLOAD E N D S
				
				
				cashlessDetailVO.setFileName(formFile.toString());
				
				String fileExtn = GetFileExtension(formFile.toString());
				if((fileExtn.equalsIgnoreCase("pdf"))   || (fileExtn.equalsIgnoreCase("doc")) 
                        || (fileExtn.equalsIgnoreCase("docx")) || (fileExtn.equalsIgnoreCase("xls"))   
                        || (fileExtn.equalsIgnoreCase("xlsx")) || fileExtn.equalsIgnoreCase("jpeg") 
                        || fileExtn.equalsIgnoreCase("jpg") || fileExtn.equalsIgnoreCase("png"))
				{
					
				}
			//	iResult	= onlinePreAuthManager.savePreAuthDetails(cashlessDetailVO,prescriptions,userSecurityProfile,medicalIds.toString());
				
				
					
				//	frmOnlinePreAuth.set("enrollId", request.getParameter("enrollId"));
				//finally return to the grid screen
				request.setAttribute("vidalId", vidalId);
				request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
				return this.getForward(strPreauthInvoice, mapping, request);
			}//end of try
		catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
		}//end of catch(Exception exp)
		}//end of doSavePreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
		
		
		
		
		/**
	     * 
	     * @param mapping doAddConsumables
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public ActionForward doConsumablesAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
	    	try{
	    		log.debug("Inside the doConsumablesAdd method of OnlinePreAuthAction");
	    		setOnlineLinks(request);
	    		OnlinePreAuthManager onlinePreAuthManager= null;
				onlinePreAuthManager = this.getOnlineAccessManagerObject();
	    		
				DynaActionForm frmOnlineConsumables 	=	(DynaActionForm)form;
				String consDesc	=	frmOnlineConsumables.getString("consumableDesc");
				LabServiceVO	labServiceVO	=	new LabServiceVO();
				
				ArrayList alConsumables	=	null;
				
				/*
				 * Activity Seq Id and Activity Codes
				 */
				labServiceVO	=	onlinePreAuthManager.getConsumableDetails(consDesc);
				labServiceVO.setUnitPrice(new BigDecimal(TTKCommon.checkNull(frmOnlineConsumables.get("addUnitPrice").toString())));
				labServiceVO.setQuantity(new Integer( TTKCommon.checkNull(frmOnlineConsumables.get("addQuantity").toString())));
				labServiceVO.setGross(new BigDecimal(TTKCommon.checkNull(frmOnlineConsumables.get("addGross").toString())));
				labServiceVO.setDiscount(new BigDecimal(TTKCommon.checkNull(frmOnlineConsumables.get("addDiscount").toString())));
				labServiceVO.setDiscGross(new BigDecimal(TTKCommon.checkNull(frmOnlineConsumables.get("addDiscountedGross").toString())));
				labServiceVO.setPatientShare(new BigDecimal(TTKCommon.checkNull(frmOnlineConsumables.get("addPatientShare").toString())));
				labServiceVO.setNetAmount(new BigDecimal(TTKCommon.checkNull(frmOnlineConsumables.get("addNetAmount").toString())));
				labServiceVO.setAddedBy(TTKCommon.getUserSeqId(request));
				Long intimationSeqID	=	(Long) request.getSession().getAttribute("PAT_INTIMATION_SEQ_ID");
				
				frmOnlineConsumables.initialize(mapping);
				/*
				 * Save the PreAuth Consumables line item wise
				 */
				int iSaveConsumables	=	onlinePreAuthManager.savePreAuthConsumables(labServiceVO,intimationSeqID);
				
				/*
				 * Getting the Inserted Data and Showing in the Grid
				 */
				if(iSaveConsumables>0)
				{
					alConsumables	=	onlinePreAuthManager.getPreAuthConsumables(intimationSeqID);
				}
				/*alConsumables	=	(ArrayList) request.getSession().getAttribute("alConsumables");
				if(alConsumables==null){
					alConsumables	=	new ArrayList();
					alConsumables.add(labServiceVO);
				}
				else{
					alConsumables.add(labServiceVO);
				}*/
				request.getSession().setAttribute("alConsumables", alConsumables);
				return this.getForward(strAddConsumables, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processOnlineExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
			}//end of catch(Exception exp)
	    }//end of doConsumablesAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	    
		
	    
	    
	  
	    /**
	     * 
	     * @param mapping Pharmacy Details
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public ActionForward doGetConsumablesDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
	    	try{
	    		log.debug("Inside the doGetConsumablesDetails method of OnlinePreAuthAction");
	    		setOnlineLinks(request);
	    		OnlinePreAuthManager onlinePreAuthManager= null;
				onlinePreAuthManager = this.getOnlineAccessManagerObject();
	    		
				DynaActionForm frmOnlinePharmacy 	=	(DynaActionForm)form;
				String pharmacyDesc	=	frmOnlinePharmacy.getString("pharmacyDescSearch");
				LabServiceVO	labServiceVO	=	new LabServiceVO();
				
				labServiceVO	=	onlinePreAuthManager.getPreAuthPharamcyDetails(pharmacyDesc);

				frmOnlinePharmacy = (DynaActionForm)FormUtils.setFormValues("frmOnlinePharmacy",
						labServiceVO,this,mapping,request);
				
				return this.getForward(strAddPharmacy, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processOnlineExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
			}//end of catch(Exception exp)
	    }//end of doGetConsumablesDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	    
	    
		//This method is used to get the extension of the file attached
	    //DonE for INTX - KISHOR KUMAR S H
	    private static String GetFileExtension(String fname2)
	    {
	        String fileName = fname2;
	        String fname="";
	        String ext="";
	        int mid= fileName.lastIndexOf(".");
	        fname=fileName.substring(0,mid);
	        ext=fileName.substring(mid+1,fileName.length());
	        return ext;
	    }
	    
	    
	    /*
		 * Online PREAUTH SUBMISSION ENTRY SCREEN
		 */
	    public ActionForward doProceedPreAuthSubmission(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                HttpServletResponse response) throws Exception{
		try{
		setOnlineLinks(request);
		log.debug("Inside the doProceedPreAuthSubmission method of OnlineCashlessHospAction");
		DynaActionForm frmOnlinePreAuth =(DynaActionForm)request.getSession().getAttribute("frmCashlessAdd");
		//frmCashlessAdd.initialize(mapping);     //reset the form data
		CashlessDetailVO cashlessDetailVO	=	new CashlessDetailVO();
		DiagnosisDetailsVO diagnosisDetailsVO	=	new DiagnosisDetailsVO();//diagnosis details
		ActivityDetailsVO activityDetailsVO		=	new ActivityDetailsVO();//activity Details
		cashlessDetailVO = (CashlessDetailVO)FormUtils.getFormValues(frmOnlinePreAuth,"frmCashlessAdd",
				this,mapping,request);

		cashlessDetailVO.setDiagnosisDetailsVO(diagnosisDetailsVO);
		cashlessDetailVO.setActivityDetailsVO(activityDetailsVO);
		cashlessDetailVO.setDrugDetailsVO(new DrugDetailsVO());
		
		
		DynaActionForm onlinePreAuthForm = setFormValues(cashlessDetailVO,mapping,request);
		
		request.getSession().setAttribute("frmOnlinePreAuth", onlinePreAuthForm);
		request.getSession().setAttribute("preauthDiagnosis", null);
		request.getSession().setAttribute("preauthActivities", null);
		request.getSession().setAttribute("preauthDrugs", null);
		return this.getForward(strONLINEPREAUTH, mapping, request);
		}//end of try
		catch(TTKException expTTK)
	{
	return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
		catch(Exception exp)
	{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
	}//end of catch(Exception exp)
	}//end of doProceedPreAuthSubmission(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		 //HttpServletResponse response)
		 
		 
	    
	    /*
	     * TO Save Diagnosis details
	     */

	    public ActionForward doSaveDiags(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                HttpServletResponse response) throws Exception{
		try{
		setOnlineLinks(request);
		log.debug("Inside the doSaveDiags method of OnlineCashlessHospAction");
		HttpSession session				=	request.getSession();
		DynaActionForm frmOnlinePreAuth =	(DynaActionForm)session.getAttribute("frmOnlinePreAuth");
		ArrayList<DiagnosisDetailsVO> preauthDiagnosis	=	(ArrayList<DiagnosisDetailsVO>) (session.getAttribute("preauthDiagnosis")==null?new ArrayList<DiagnosisDetailsVO>():session.getAttribute("preauthDiagnosis"));
		
		CashlessDetailVO cashlessDetailVO	 	=	new CashlessDetailVO();
		DiagnosisDetailsVO diagnosisDetailsVO	=	new DiagnosisDetailsVO();//diagnosis details
		DiagnosisDetailsVO diagnosisDetailsVO1	=	new DiagnosisDetailsVO();//diagnosis details
		cashlessDetailVO 	= 	getFormValues(frmOnlinePreAuth,mapping,request);
		diagnosisDetailsVO	=	cashlessDetailVO.getDiagnosisDetailsVO();
		diagnosisDetailsVO1.setIcdCode("");
		diagnosisDetailsVO1.setAilmentDescription("");
		cashlessDetailVO.setDiagnosisDetailsVO(diagnosisDetailsVO1);
		frmOnlinePreAuth	=	setFormValues(cashlessDetailVO,mapping,request);
		
		if(preauthDiagnosis.size()==0)
			diagnosisDetailsVO.setPrimaryAilment("Y");
		else
			diagnosisDetailsVO.setPrimaryAilment("N");
		if(preauthDiagnosis.size()==1)
			diagnosisDetailsVO.setAuthType("Y");
		else
			diagnosisDetailsVO.setAuthType("N");
		preauthDiagnosis.add(diagnosisDetailsVO);
			
		/*OnlinePreAuthManager onlinePreAuthManager	=	this.getOnlineAccessManagerObject();
		cashlessDetailVO		= 	getFormValues(frmOnlinePreAuth,mapping,request);
		diagnosisDetailsVO		=	cashlessDetailVO.getDiagnosisDetailsVO();
		UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
		Long lPreAuthIntSeqId	=	(Long) request.getSession().getAttribute("lPreAuthIntSeqId");
		int iResult 			=	onlinePreAuthManager.saveDiagnosisDetails(diagnosisDetailsVO,lPreAuthIntSeqId,userSecurityProfile.getUSER_SEQ_ID());
		preauthDiagnosis	=	onlinePreAuthManager.getDiagDetails(lPreAuthIntSeqId);
		DynaActionForm onlinePreAuthForm			=	setFormValues(cashlessDetailVO,mapping,request);*/
		
		// focus object Code S T A R T S
		String focusObj		=	request.getParameter("focusObj");
		request.setAttribute("focusObj", focusObj);
		// focus object Code E N D S 
		request.setAttribute("focusFieldID", "diagnosisBtnID");
		
		request.getSession().setAttribute("preauthDiagnosis", preauthDiagnosis);
		request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
		return this.getForward(strONLINEPREAUTH, mapping, request);
		}//end of try
		catch(TTKException expTTK)
	{
	return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
		catch(Exception exp)
	{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
	}//end of catch(Exception exp)
	}//end of doSaveDiags(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		 //HttpServletResponse response)


	public ActionForward deleteDiagnosisDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside PreAuthGenealAction deleteDiagnosis ");
			HttpSession session=request.getSession();
			DynaActionForm frmOnlinePreAuth=(DynaActionForm)form;
			ArrayList<DiagnosisDetailsVO> preauthDiagnosis	=	(ArrayList<DiagnosisDetailsVO>) (session.getAttribute("preauthDiagnosis")==null?new ArrayList<DiagnosisDetailsVO>():session.getAttribute("preauthDiagnosis"));

			int rownum	=	Integer.parseInt(request.getParameter("rownum"));
			DiagnosisDetailsVO diagnosisDetailsVO	=	preauthDiagnosis.get(rownum);
			
			preauthDiagnosis.remove(diagnosisDetailsVO);
			/*for(int k=0;k<preauthDiagnosis.size();k++){
				diagnosisDetailsVO	=	preauthDiagnosis.get(k);
				if(diagnosisDetailsVO.getIcdCode().equals(rownum))
					preauthDiagnosis.remove(diagnosisDetailsVO);
			}*/
			/*diagnosisDetailsVO.setAuthType("PAT"); 
			diagnosisDetailsVO.setPreAuthSeqID(new Long(preAuthSeqID));
			diagnosisDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));		  
			onlinePreAuthManager.deleteDiagnosisDetails(diagnosisDetailsVO);*/
			request.setAttribute("successMsg","Diagnosis Details Deleted Successfully");	
			
			/*Object[]	preauthAllresult=onlinePreAuthManager.getOnlinePreAuthDetails(diagnosisDetailsVO.getPreAuthSeqID());
			cashLessDetailVO=(CashlessDetailVO)preauthAllresult[0];*/
			//ArrayList<DiagnosisDetailsVO> diagnosis=(ArrayList<DiagnosisDetailsVO>)preauthDiagnosis;
			//frmOnlinePreAuth = setFormValues(cashLessDetailVO,mapping,request);
			
			request.getSession().setAttribute("preauthDiagnosis", preauthDiagnosis);
			request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
				
			return this.getForward(strONLINEPREAUTH, mapping, request);
		}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
	}//end of catch(Exception exp)
}//end of deleteDiagnosisDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


	public ActionForward deleteActivityDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside PreAuthGenealAction deleteActivityDetails ");
			HttpSession session=request.getSession();
			DynaActionForm frmOnlinePreAuth=(DynaActionForm)form;
			ArrayList<ActivityDetailsVO> preauthActivities	=	(ArrayList<ActivityDetailsVO>) (session.getAttribute("preauthActivities")==null?new ArrayList<ActivityDetailsVO>():session.getAttribute("preauthActivities"));

			int rownum	=	Integer.parseInt(request.getParameter("rownum"));
			ActivityDetailsVO activityDetailsVO	=	preauthActivities.get(rownum);
			
			preauthActivities.remove(activityDetailsVO);
			request.setAttribute("successMsg","Diagnosis Details Deleted Successfully");	
			
			request.getSession().setAttribute("preauthActivities", preauthActivities);
			request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
				
			return this.getForward(strONLINEPREAUTH, mapping, request);
		}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
	}//end of catch(Exception exp)
}//end of deleteActivityDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	
	public ActionForward deleteDrugDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside PreAuthGenealAction deleteDrugDetails ");
			HttpSession session=request.getSession();
			DynaActionForm frmOnlinePreAuth=(DynaActionForm)form;
			ArrayList<DrugDetailsVO> preauthDrugs	=	(ArrayList<DrugDetailsVO>) (session.getAttribute("preauthDrugs")==null?new ArrayList<ActivityDetailsVO>():session.getAttribute("preauthDrugs"));

			int rownum	=	Integer.parseInt(request.getParameter("rownum"));
			DrugDetailsVO drugDetailsVO	=	preauthDrugs.get(rownum);
			
			preauthDrugs.remove(drugDetailsVO);
			request.setAttribute("successMsg","Diagnosis Details Deleted Successfully");	
			
			request.getSession().setAttribute("preauthDrugs", preauthDrugs);
			request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
				
			return this.getForward(strONLINEPREAUTH, mapping, request);
		}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
	}//end of catch(Exception exp)
}//end of deleteDrugDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	
	public ActionForward doAddDiagnosisDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doAddDiagnosisDetails method of OnlinePreAuthAction");
			setOnlineLinks(request);
			
			HttpSession session=request.getSession();
			TableData diagnosisCodeListData = new TableData();  //create new table data object
			diagnosisCodeListData.createTableInfo("DiagnosisCodeListTable",new ArrayList()); 
			session.setAttribute("diagnosisCodeListData",diagnosisCodeListData);//create the required grid table
			request.setAttribute("typeOfCodes", "Diagnosis");
			request.setAttribute("drugSearch", "diagSearch");
			return this.getForward(strAddActivityDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
		}//end of catch(Exception exp)
	}//end of doAddDiagnosisDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	
public ActionForward doAddActivityDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
	try{
		log.debug("Inside the doAddActivityDetails method of OnlinePreAuthAction");
		setOnlineLinks(request);
		
		HttpSession session=request.getSession();
		TableData activityCodeListData = new TableData();  //create new table data object
		activityCodeListData.createTableInfo("ActivityCodeListTable",new ArrayList()); 
		session.setAttribute("activityCodeListData",activityCodeListData);//create the required grid table
		request.setAttribute("typeOfCodes", "Activity");
		request.setAttribute("drugSearch", "activitySearch");
		return this.getForward(strAddActivityDetails, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
	}//end of catch(Exception exp)
}//end of doAddActivityDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)




public ActionForward doAddDrugDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	HttpServletResponse response) throws Exception{
try{
	log.debug("Inside the doAddDrugDetails method of OnlinePreAuthAction");
	setOnlineLinks(request);
	
	HttpSession session=request.getSession();
	TableData drugCodeListData = new TableData();  //create new table data object
	drugCodeListData.createTableInfo("DrugListTable",new ArrayList()); 
	session.setAttribute("drugCodeListData",drugCodeListData);//create the required grid table
	request.setAttribute("drugSearch", "drugSearch");
	request.setAttribute("typeOfCodes", "Drug");
	
	return this.getForward(strAddActivityDetails, mapping, request);
}//end of try
catch(TTKException expTTK)
{
	return this.processOnlineExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
}//end of catch(Exception exp)
}//end of doAddDrugDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



public ActionForward diagCodeSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
	try{
	log.debug("Inside PreAuthAction diagCodeSearch");
	setOnlineLinks(request);
	OnlinePreAuthManager onlinePreAuthManager=this.getOnlineAccessManagerObject();
	PreAuthManager preAuthObject=this.getPreAuthManagerObject();
	HttpSession session=request.getSession();
	String drugSearch	=	(String) request.getParameter("drugSearch");
	TableData diagCodeListData = null;
	if(session.getAttribute("diagCodeListData") != null)
	{
		diagCodeListData = (TableData)session.getAttribute("diagCodeListData");
	}//end of if((request.getSession()).getAttribute("icdListData") != null)
	else
	{
		diagCodeListData = new TableData();
	}//end of else
	
	String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
	String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
	//if the page number or sort id is clicked
	if(!strPageID.equals("") || !strSortID.equals(""))
	{
	if(strPageID.equals(""))
	{
		diagCodeListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
		diagCodeListData.modifySearchData("sort");//modify the search data                    
	}///end of if(!strPageID.equals(""))
	else
	{
	log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
	diagCodeListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
	request.setAttribute("drugSearch", drugSearch);
	request.setAttribute("typeOfCodes", "Diagnosis");
	return this.getForward(strAddActivityDetails, mapping, request);
	}//end of else
	}//end of if(!strPageID.equals("") || !strSortID.equals(""))
	else{
		diagCodeListData.createTableInfo("DiagnosisCodeListTable",null);
		diagCodeListData.setSearchData(this.populateDiagCodeSearchCriteria((DynaActionForm)form,request));
		diagCodeListData.modifySearchData("search");				
	}//end of else
	
	ArrayList alActivityCodeList=null;
	alActivityCodeList= preAuthObject.getDiagnosisCodeList(diagCodeListData.getSearchData());
	diagCodeListData.setData(alActivityCodeList, "search");
	//set the table data object to session
	request.getSession().setAttribute("diagCodeListData",diagCodeListData);
	request.setAttribute("drugSearch", drugSearch);
	request.setAttribute("typeOfCodes", "Diagnosis");
	return this.getForward(strAddActivityDetails, mapping, request);
}//end of try
catch(TTKException expTTK)
{
return this.processOnlineExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
}//end of catch(Exception exp)
}//end of diagCodeSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
public ActionForward drugCodeSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
	try{
		log.debug("Inside PreAuthAction drugCodeSearch");
		setOnlineLinks(request);
		OnlinePreAuthManager onlinePreAuthManager=this.getOnlineAccessManagerObject();
		HttpSession session=request.getSession();
		String drugSearch	=	(String) request.getParameter("drugSearch");
		TableData drugCodeListData = null;
		if(session.getAttribute("drugCodeListData") != null)
		{
			drugCodeListData = (TableData)session.getAttribute("drugCodeListData");
		}//end of if((request.getSession()).getAttribute("icdListData") != null)
		else
		{
			drugCodeListData = new TableData();
		}//end of else
		
		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
		//if the page number or sort id is clicked
		if(!strPageID.equals("") || !strSortID.equals(""))
		{
			if(strPageID.equals(""))
			{
				drugCodeListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				drugCodeListData.modifySearchData("sort");//modify the search data                    
			}///end of if(!strPageID.equals(""))
			else
			{
			log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
			drugCodeListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
			request.setAttribute("drugSearch", drugSearch);
			request.setAttribute("typeOfCodes", "Drug");
			return this.getForward(strAddActivityDetails, mapping, request);
			}//end of else
          }//end of if(!strPageID.equals("") || !strSortID.equals(""))
		else{
			drugCodeListData.createTableInfo("DrugListTable",null);
			drugCodeListData.setSearchData(this.populateActivityCodeSearchCriteria((DynaActionForm)form,request));
			drugCodeListData.modifySearchData("search");				
		}//end of else
		
		ArrayList alActivityCodeList=null;
		alActivityCodeList= onlinePreAuthManager.getDrugCodeList(drugCodeListData.getSearchData());
		drugCodeListData.setData(alActivityCodeList, "search");
		//set the table data object to session
		request.getSession().setAttribute("drugCodeListData",drugCodeListData);
		request.setAttribute("drugSearch", drugSearch);
		request.setAttribute("typeOfCodes", "Drug");
		return this.getForward(strAddActivityDetails, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
	}//end of catch(Exception exp)
}//end of drugCodeSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



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
public ActionForward activityCodeSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
	try{
		log.debug("Inside PreAuthAction activityCodeSearch");
		setOnlineLinks(request);
		OnlinePreAuthManager onlinePreAuthManager=this.getOnlineAccessManagerObject();
		HttpSession session=request.getSession();
		DynaActionForm frmOnlinePreAuth=(DynaActionForm)session.getAttribute("frmOnlinePreAuth");
		TableData activityCodeListData = null;
		if(session.getAttribute("activityCodeListData") != null)
		{
			activityCodeListData = (TableData)session.getAttribute("activityCodeListData");
		}//end of if((request.getSession()).getAttribute("icdListData") != null)
		else
		{
			activityCodeListData = new TableData();
		}//end of else
		
		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
		//if the page number or sort id is clicked
		if(!strPageID.equals("") || !strSortID.equals(""))
		{
			if(strPageID.equals(""))
			{
				activityCodeListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				activityCodeListData.modifySearchData("sort");//modify the search data                    
			}///end of if(!strPageID.equals(""))
			else
			{
			log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
			activityCodeListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
			request.setAttribute("typeOfCodes", "Activity");
			request.setAttribute("drugSearch", request.getParameter("drugSearch"));
			return this.getForward(strAddActivityDetails, mapping, request);
			}//end of else
          }//end of if(!strPageID.equals("") || !strSortID.equals(""))
		else{
			activityCodeListData.createTableInfo("ActivityCodeListTable",null);
			activityCodeListData.setSearchData(this.populateActivityCodeSearchCriteria((DynaActionForm)form,request));
			activityCodeListData.modifySearchData("search");				
		}//end of else
		
		ArrayList alActivityCodeList=null;
		alActivityCodeList= onlinePreAuthManager.getActivityCodeList(activityCodeListData.getSearchData());
		activityCodeListData.setData(alActivityCodeList, "search");
		//set the table data object to session
		request.getSession().setAttribute("activityCodeListData",activityCodeListData);
		request.setAttribute("typeOfCodes", "Activity");
		request.setAttribute("drugSearch", request.getParameter("drugSearch"));
		return this.getForward(strAddActivityDetails, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
	}//end of catch(Exception exp)
}//end of activityCodeSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



public ActionForward doSelectDiagCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
	try{
	setOnlineLinks(request);
	log.debug("Inside OnlinePreAuthAction doSelectDiagCode ");
	HttpSession session=request.getSession();
	
	TableData diagCodeListData = (TableData)session.getAttribute("diagCodeListData");
	DiagnosisDetailsVO diagnosisDetailsVO=(DiagnosisDetailsVO)diagCodeListData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
	DynaActionForm frmActivitySearch	=	(DynaActionForm)form;
	
	 DynaActionForm frmOnlinePreAuth	=	(DynaActionForm)session.getAttribute("frmOnlinePreAuth");
	//activityDetailsVO	=	onlinePreAuthManager.getActivityCodeDeails(frmActivitySearch.get("sSearchType"),"");
	CashlessDetailVO cashlessDetailVO = null;
	cashlessDetailVO	=	(CashlessDetailVO)FormUtils.getFormValues(frmOnlinePreAuth,"frmOnlinePreAuth",
			this,mapping,request);
	cashlessDetailVO.setDiagnosisDetailsVO(diagnosisDetailsVO);
	frmOnlinePreAuth = setFormValues(cashlessDetailVO,mapping,request);
	frmOnlinePreAuth.set("sSearchType", frmActivitySearch.get("sSearchType")); 
	request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
	request.setAttribute("focusFieldID", "diagnosisBtnID");
	return mapping.findForward(strONLINEPREAUTH);
}//end of try
catch(TTKException expTTK)
	{
	return this.processOnlineExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
	{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
}//end of catch(Exception exp)
}//end of doSelectDiagCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


public ActionForward doSelectActivityCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
	try{
	setOnlineLinks(request);
	log.debug("Inside OnlinePreAuthAction doSelectActivityCode ");
	HttpSession session=request.getSession();
	
	TableData activityCodeListData = (TableData)session.getAttribute("activityCodeListData");
	ActivityDetailsVO activityDetailsVO=(ActivityDetailsVO)activityCodeListData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
	DynaActionForm frmActivitySearch	=	(DynaActionForm)form;
	
	 DynaActionForm frmOnlinePreAuth	=	(DynaActionForm)session.getAttribute("frmOnlinePreAuth");
	//activityDetailsVO	=	onlinePreAuthManager.getActivityCodeDeails(frmActivitySearch.get("sSearchType"),"");
	CashlessDetailVO cashlessDetailVO = null;
	cashlessDetailVO	=	(CashlessDetailVO)FormUtils.getFormValues(frmOnlinePreAuth,"frmOnlinePreAuth",
			this,mapping,request);
	cashlessDetailVO.setActivityDetailsVO(activityDetailsVO);
	frmOnlinePreAuth = setFormValues(cashlessDetailVO,mapping,request);
	frmOnlinePreAuth.set("sSearchType", frmActivitySearch.get("sSearchType")); 
	request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
	
	return mapping.findForward(strONLINEPREAUTH);
}//end of try
catch(TTKException expTTK)
	{
	return this.processOnlineExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
	{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
}//end of catch(Exception exp)
}//end of doSelectActivityCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


public ActionForward doSelectDrugCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
	try{
	setOnlineLinks(request);
	log.debug("Inside OnlinePreAuthAction doSelectDrugCode ");
	HttpSession session=request.getSession();
	
	TableData drugCodeListData = (TableData)session.getAttribute("drugCodeListData");
	DrugDetailsVO drugDetailsVO=(DrugDetailsVO)drugCodeListData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
	DynaActionForm frmActivitySearch	=	(DynaActionForm)form;
	DynaActionForm frmOnlinePreAuth	=	(DynaActionForm)session.getAttribute("frmOnlinePreAuth");
	//activityDetailsVO	=	onlinePreAuthManager.getActivityCodeDeails(frmActivitySearch.get("sSearchType"),"");
	CashlessDetailVO cashlessDetailVO = null;
	cashlessDetailVO	=	(CashlessDetailVO)FormUtils.getFormValues(frmOnlinePreAuth,"frmOnlinePreAuth",this,mapping,request);
	
	cashlessDetailVO.setDrugDetailsVO(drugDetailsVO);
	frmOnlinePreAuth	=	setFormValues(cashlessDetailVO,mapping,request);
	frmOnlinePreAuth.set("sSearchType", frmActivitySearch.get("sSearchType")); 
	request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
	request.setAttribute("gran", drugDetailsVO.getGranularUnit());
	
	return mapping.findForward(strONLINEPREAUTH);
}//end of try
catch(TTKException expTTK)
	{
	return this.processOnlineExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
	{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
}//end of catch(Exception exp)
}//end of doSelectDrugCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


public ActionForward doSaveActivities(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
		try{
		setOnlineLinks(request);
		log.debug("Inside OnlinePreAuthAction doSaveActivities");
		HttpSession session=request.getSession();
		OnlinePreAuthManager onlinePreAuthManager=this.getOnlineAccessManagerObject();
		ArrayList<ActivityDetailsVO> preauthActivities	=	(ArrayList<ActivityDetailsVO>) (session.getAttribute("preauthActivities")==null?new ArrayList<ActivityDetailsVO>():session.getAttribute("preauthActivities"));
		DynaActionForm frmOnlinePreAuth		=	(DynaActionForm)form;
		CashlessDetailVO cashlessDetailVO 	= 	getFormValues(frmOnlinePreAuth,mapping,request);
		
		ActivityDetailsVO activityDetailsVO	=	new ActivityDetailsVO();
		activityDetailsVO	=	cashlessDetailVO.getActivityDetailsVO();
		Float qty	=	activityDetailsVO.getQuantity();
		cashlessDetailVO	=	(CashlessDetailVO)FormUtils.getFormValues(frmOnlinePreAuth,"frmOnlinePreAuth",
				this,mapping,request);
	    UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
		activityDetailsVO	=	 onlinePreAuthManager.getActivityCodeDetails(userSecurityProfile.getHospSeqId(),cashlessDetailVO.getEnrollId(),activityDetailsVO.getActivityCode(),cashlessDetailVO.getEncounterType(),cashlessDetailVO.getTreatmentDate());

		BigDecimal unitPrice = activityDetailsVO.getGrossAmount();
		BigDecimal discount = activityDetailsVO.getDiscount();
		double grossAmount = 0.0d;
		double discountAmt = 0.0d;
		double netAmt = 0.0d;
		double totAmt = netAmt;
		
		if(unitPrice == null || new BigDecimal(0).equals(unitPrice))
		{
			grossAmount = 0.0d;
			discountAmt = 0.0d;
			netAmt = 0.0d;
			totAmt = netAmt;
		}
		else
		{
			grossAmount =	unitPrice.doubleValue()*qty.doubleValue();
			discountAmt = 	discount.doubleValue()*qty.doubleValue();
			netAmt		=	grossAmount-discountAmt;
			totAmt = netAmt;
		}
		
			log.info(" ------------------ Save Activities Details -------------- ");
			log.info(" :  unitPrice(GrossAmount) 		= "+ unitPrice);
		    log.info(" :  discount 						= "+ discount);
		    log.info(" :  grossAmount 					= "+ grossAmount);
		    log.info(" :  discountAmt		 			= "+ discountAmt);
		    log.info(" :  netAmt		 				= "+ netAmt);
		    log.info(" :  totAmt		 				= "+ totAmt);
		    log.info(" --------------------------- END-------------------------- ");
		
		
		/*BigDecimal netAmt	=	activityDetailsVO.getNetAmount();
		double ttlAmt	=	0.0d;
		if(netAmt==null){
			TTKException expTTK = new TTKException();
			expTTK.setMessage("error.netAmountNull");
			throw new TTKException(expTTK , "netAmountNull");
			netAmt	=	new BigDecimal(0);
		}else{
			ttlAmt	=	qty.doubleValue()*netAmt.doubleValue();
		}*/
		
		
		    activityDetailsVO.setGrossAmount(new BigDecimal(grossAmount).setScale(2,RoundingMode.HALF_UP));		// 4 grossAmount
		    activityDetailsVO.setDiscount(new BigDecimal(discountAmt).setScale(2,RoundingMode.HALF_UP));		// 5 discountAmt
		    activityDetailsVO.setNetAmount(new BigDecimal(netAmt).setScale(2,RoundingMode.HALF_UP));			// 6 netAmt
		    activityDetailsVO.setQuantity(qty);																	// 7 qty
		    activityDetailsVO.setApprovedAmount(new BigDecimal(netAmt).setScale(2,RoundingMode.HALF_UP));		// 8 netAmt
		    activityDetailsVO.setActivityunitPrice(unitPrice);
		    activityDetailsVO.setActivityDiscount(discount);
		//activityDetailsVO.setEncounterType(cashlessDetailVO.getEncounterType());
		preauthActivities.add(activityDetailsVO);
		//activityDetailsVO1.setActivityCode("");activityDetailsVO1.setActivityCodeDesc("");activityDetailsVO1.setQuantity(new Float(0));
		cashlessDetailVO.setActivityDetailsVO(new ActivityDetailsVO());
		frmOnlinePreAuth = setFormValues(cashlessDetailVO,mapping,request);
		request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
		session.setAttribute("preauthActivities", preauthActivities);
		
		return mapping.findForward(strONLINEPREAUTH);
		}//end of try
	catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
	}//end of catch(Exception exp)
}//end of doSaveActivities(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



public ActionForward doSaveDrugs(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
		try{
		setOnlineLinks(request);
		log.debug("Inside OnlinePreAuthAction doSaveDrugs");
		HttpSession session=request.getSession();
		OnlinePreAuthManager onlinePreAuthManager=this.getOnlineAccessManagerObject();
		ArrayList<DrugDetailsVO> preauthDrugs=	(ArrayList<DrugDetailsVO>) (session.getAttribute("preauthDrugs")==null?new ArrayList<DrugDetailsVO>():session.getAttribute("preauthDrugs"));
		DynaActionForm frmOnlinePreAuth		=	(DynaActionForm)form;
		CashlessDetailVO cashlessDetailVO 	= 	getFormValues(frmOnlinePreAuth,mapping,request);
		
		DrugDetailsVO drugDetailsVO	=	new DrugDetailsVO();
		drugDetailsVO	=	cashlessDetailVO.getDrugDetailsVO();
		Float qty	=	drugDetailsVO.getDrugqty();
		String unit	=	drugDetailsVO.getDrugunit();
		String posology	=	drugDetailsVO.getPosology();
		String days	=	drugDetailsVO.getDays();
		cashlessDetailVO	=	(CashlessDetailVO)FormUtils.getFormValues(frmOnlinePreAuth,"frmOnlinePreAuth",
				this,mapping,request);
	    UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	   
	    drugDetailsVO	=	 onlinePreAuthManager.getDrugCodeDetails(userSecurityProfile.getHospSeqId(),cashlessDetailVO.getEnrollId(),drugDetailsVO.getDrugCode(),cashlessDetailVO.getTreatmentDate(),cashlessDetailVO.getEncounterType());
	    
	    Float unitPrice	=	(float) (drugDetailsVO.getUnitPrice()==null?0.0:drugDetailsVO.getUnitPrice().floatValue());
	    Float packagePrice=	(float) (drugDetailsVO.getPackagePrice()==null?0.0:drugDetailsVO.getPackagePrice().floatValue());
	    BigDecimal granularUnit	=	drugDetailsVO.getGranularUnit();
	    Float DisPrec	= (float) (drugDetailsVO.getDisPrec()==null?0.0:drugDetailsVO.getDisPrec().floatValue());	// 	disc_percentage
	    Float discount	= (float) (drugDetailsVO.getDiscount()==null?0.0:drugDetailsVO.getDiscount().floatValue());	// 	discount

	    //  1: Gross Price
			float grossPrice= 0;
		    if("PCKG".equals(unit))
		    	grossPrice	=	packagePrice*qty;
		    else
		    	grossPrice	=	unitPrice*qty;
	    
	    //  2 : Disc Amt
		    float discAmt= 0;
		    float discountPrice= 0;
		    if("PKG".equals(unit))
		    	discAmt	=	packagePrice*(DisPrec/100);
		    else
		    	discAmt	=	unitPrice*(DisPrec/100);
		    
		    discountPrice = discAmt*qty;
		  	  
		//  3:  Net Amt & Total Amt
		    float netamt= 0;
		    netamt = grossPrice-discountPrice;
		    
		    log.info(" ------------------ SaveDrugs Details -------------- ");
		    log.info(" :  packagePrice 		= "+ packagePrice);  
		    log.info(" :  unitPrice 		= "+ unitPrice);
		    log.info(" :  DisPrec 			= "+ DisPrec);
		    log.info(" :  discAmt 			= "+ discAmt);
		    log.info(" :  discountPrice 	= "+ discountPrice);
		    log.info(" :  netamt 			= "+ netamt);
		    log.info(" ------------------------- END------------------------ ");
	    
	    drugDetailsVO.setDrugqty(qty);
	    drugDetailsVO.setPosology(posology);
	    drugDetailsVO.setDays(days);
	    drugDetailsVO.setDrugunit(unit);
	    drugDetailsVO.setCalcGroassPrice(new BigDecimal(grossPrice).setScale(2,RoundingMode.HALF_UP));		// Gross Price
	    drugDetailsVO.setCalcDisAmt(new BigDecimal(discountPrice).setScale(2,RoundingMode.HALF_UP));		// Disc amt
	    drugDetailsVO.setCalcNetAmt(new BigDecimal(netamt).setScale(2,RoundingMode.HALF_UP));				// for both Net amt & Total Amt
	    drugDetailsVO.setUnitPrice(new BigDecimal(unitPrice).setScale(2,RoundingMode.HALF_UP));
	    drugDetailsVO.setDiscount(new BigDecimal(discount).setScale(2,RoundingMode.HALF_UP));
		preauthDrugs.add(drugDetailsVO);
		//drugDetailsVO1.setDrugCode("");drugDetailsVO1.setDrugDesc("");drugDetailsVO1.setDays("");drugDetailsVO1.setDrugqty(Float.parseFloat("0"));
		cashlessDetailVO.setDrugDetailsVO(new DrugDetailsVO());
		frmOnlinePreAuth = setFormValues(cashlessDetailVO,mapping,request);
		request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
		session.setAttribute("preauthDrugs", preauthDrugs);
		
		return mapping.findForward(strONLINEPREAUTH);
		}//end of try
	catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
	}//end of catch(Exception exp)
}//end of doSaveDrugs(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)







public ActionForward doDiagCodeForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{
log.debug("Inside OnlinePreAuthAction doDiagCodeForward");
setOnlineLinks(request);
TableData diagCodeListData = (TableData)request.getSession().getAttribute("diagCodeListData");
PreAuthManager preAuthObject=this.getPreAuthManagerObject();diagCodeListData.modifySearchData(strForward);//modify the search data
ArrayList alPreauthList = preAuthObject.getDiagnosisCodeList(diagCodeListData.getSearchData());
diagCodeListData.setData(alPreauthList, strForward);//set the table data
request.getSession().setAttribute("diagCodeListData",diagCodeListData);   //set the table data object to session
request.setAttribute("typeOfCodes", "Diagnosis");
request.setAttribute("drugSearch", request.getParameter("drugSearch"));
return this.getForward(strAddActivityDetails, mapping, request);   //finally return to the grid screen
}//end of try
catch(TTKException expTTK)
{
return this.processOnlineExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processOnlineExceptions(request, mapping, new TTKException(exp,strONLINEPREAUTH));
}//end of catch(Exception exp)
}//end of doDiagCodeForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
public ActionForward doDiagCodeBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{
log.debug("Inside OnlinePreAuthAction doDiagCodeBackward");
setOnlineLinks(request);
TableData diagCodeListData = (TableData)request.getSession().getAttribute("diagCodeListData");
PreAuthManager preAuthObject=this.getPreAuthManagerObject();diagCodeListData.modifySearchData(strForward);//modify the search data
diagCodeListData.modifySearchData(strBackward);//modify the search data
ArrayList alPreauthList = preAuthObject.getDiagnosisCodeList(diagCodeListData.getSearchData());
diagCodeListData.setData(alPreauthList, strBackward);//set the table data
request.getSession().setAttribute("diagCodeListData",diagCodeListData);   //set the table data object to session
request.setAttribute("typeOfCodes", "Diagnosis");
request.setAttribute("drugSearch", request.getParameter("drugSearch"));
return this.getForward(strAddActivityDetails, mapping, request);   //finally return to the grid screen
}//end of try
catch(TTKException expTTK)
{
return this.processOnlineExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,strONLINEPREAUTH));
}//end of catch(Exception exp)
}//end of doDiagCodeBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)






public ActionForward doActivityCodeForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{
log.debug("Inside OnlinePreAuthAction doActivityCodeForward");
setOnlineLinks(request);
TableData activityCodeListData = (TableData)request.getSession().getAttribute("activityCodeListData");
OnlinePreAuthManager onlinePreAuthManager=this.getOnlineAccessManagerObject();
activityCodeListData.modifySearchData(strForward);//modify the search data
ArrayList alPreauthList = onlinePreAuthManager.getActivityCodeList(activityCodeListData.getSearchData());
activityCodeListData.setData(alPreauthList, strForward);//set the table data
request.getSession().setAttribute("activityCodeListData",activityCodeListData);   //set the table data object to session
request.setAttribute("typeOfCodes", "Activity");
request.setAttribute("drugSearch", request.getParameter("drugSearch"));
return this.getForward(strAddActivityDetails, mapping, request);   //finally return to the grid screen
}//end of try
catch(TTKException expTTK)
{
return this.processOnlineExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processOnlineExceptions(request, mapping, new TTKException(exp,strONLINEPREAUTH));
}//end of catch(Exception exp)
}//end of doActivityCodeForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
public ActionForward doActivityCodeBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{
log.debug("Inside OnlinePreAuthAction doActivityCodeBackward");
setOnlineLinks(request);
TableData activityCodeListData = (TableData)request.getSession().getAttribute("activityCodeListData");
OnlinePreAuthManager onlinePreAuthManager=this.getOnlineAccessManagerObject();
activityCodeListData.modifySearchData(strBackward);//modify the search data
ArrayList alPreauthList = onlinePreAuthManager.getActivityCodeList(activityCodeListData.getSearchData());
activityCodeListData.setData(alPreauthList, strBackward);//set the table data
request.getSession().setAttribute("activityCodeListData",activityCodeListData);   //set the table data object to session
request.setAttribute("typeOfCodes", "Activity");
request.setAttribute("drugSearch", request.getParameter("drugSearch"));
return this.getForward(strAddActivityDetails, mapping, request);   //finally return to the grid screen
}//end of try
catch(TTKException expTTK)
{
return this.processOnlineExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,strONLINEPREAUTH));
}//end of catch(Exception exp)
}//end of doActivityCodeBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)







public ActionForward doDrugCodeForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{
log.debug("Inside OnlinePreAuthAction doDrugCodeForward");
String drugSearch	=	(String) request.getParameter("drugSearch");
setOnlineLinks(request);
TableData drugCodeListData = (TableData)request.getSession().getAttribute("drugCodeListData");
OnlinePreAuthManager onlinePreAuthManager=this.getOnlineAccessManagerObject();
drugCodeListData.modifySearchData(strForward);//modify the search data
ArrayList alPreauthList = onlinePreAuthManager.getDrugCodeList(drugCodeListData.getSearchData());
drugCodeListData.setData(alPreauthList, strForward);//set the table data
request.getSession().setAttribute("drugCodeListData",drugCodeListData);   //set the table data object to session
request.setAttribute("typeOfCodes", "Drug");
request.setAttribute("drugSearch", drugSearch);
return this.getForward(strAddActivityDetails, mapping, request);   //finally return to the grid screen
}//end of try
catch(TTKException expTTK)
{
return this.processOnlineExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processOnlineExceptions(request, mapping, new TTKException(exp,strONLINEPREAUTH));
}//end of catch(Exception exp)
}//end of doDrugCodeForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
public ActionForward doDrugCodeBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{
log.debug("Inside OnlinePreAuthAction doDrugCodeBackward");
setOnlineLinks(request);
String drugSearch	=	(String) request.getParameter("drugSearch");
TableData drugCodeListData = (TableData)request.getSession().getAttribute("drugCodeListData");
OnlinePreAuthManager onlinePreAuthManager=this.getOnlineAccessManagerObject();
drugCodeListData.modifySearchData(strBackward);//modify the search data
ArrayList alPreauthList = onlinePreAuthManager.getDrugCodeList(drugCodeListData.getSearchData());
drugCodeListData.setData(alPreauthList, strBackward);//set the table data
request.getSession().setAttribute("drugCodeListData",drugCodeListData);   //set the table data object to session
request.setAttribute("typeOfCodes", "Drug");
request.setAttribute("drugSearch", drugSearch);
return this.getForward(strAddActivityDetails, mapping, request);   //finally return to the grid screen
}//end of try
catch(TTKException expTTK)
{
return this.processOnlineExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,strONLINEPREAUTH));
}//end of catch(Exception exp)
}//end of doDrugCodeBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

public ActionForward closeActivityCodes(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{
setOnlineLinks(request);
log.debug("Inside OnlinePreAuthAction closeActivityCodes");
	return mapping.findForward(strONLINEPREAUTH);
}//end of try
catch(TTKException expTTK)
{
return this.processOnlineExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processOnlineExceptions(request, mapping, new TTKException(exp,strONLINEPREAUTH));
}//end of catch(Exception exp)

}//end of closeActivityCodes(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



public ActionForward doSaveOnlinePreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
		try{
		setOnlineLinks(request);
		log.debug("Inside OnlinePreAuthAction doSaveOnlinePreAuth");
		HttpSession session=request.getSession();
		DynaActionForm frmOnlinePreAuth	=	(DynaActionForm)session.getAttribute("frmOnlinePreAuth");
		
		request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
		request.setAttribute("readonly", "true");
		
		return mapping.findForward(strONLINEPREAUTH);
		}//end of try
	catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
	}//end of catch(Exception exp)
}//end of doSaveOnlinePreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



public ActionForward doSavePartialPreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside the doSavePartialPreAuth method of OnlinePreAuthAction");
			DynaActionForm frmOnlinePreAuth =(DynaActionForm)form;

			// UPLOAD FILE STARTS
			/*FormFile formFile = null;
			formFile = (FormFile)frmOnlinePreAuth.get("file");
			String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("tariffAdminUpload"));
			String fileString=new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+"-"+formFile;
			String finalPath=(path+fileString);
			FileOutputStream outputStream = null;
			outputStream = new FileOutputStream(new File(finalPath));
			if(formFile.getFileData()!=null)
				outputStream.write(formFile.getFileData());//Excel file Upload backUp
*/			// UPLOAD FILE ENDS
			
			
			CashlessDetailVO cashlessDetailVO 	= 	getFormValues(frmOnlinePreAuth,mapping,request);
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
			OnlinePreAuthManager onlinePreAuthManager	=	this.getOnlineAccessManagerObject();
			Long	lPreAuthIntSeqId	=	null;
			String[] arrResult	=	new String[2];
			
			
			/*Long lSuccess	=	onlinePreAuthManager.deleteExistngDataForPreAuth(lPreAuthIntSeqId);
			Thread.sleep(1000);
			
			System.out.println("lSuccess::"+lSuccess);*/
			/*System.out.println("String ::"+arrResult[2] );*/
			String treatmentDate	=	frmOnlinePreAuth.getString("treatmentDate");
			String clinicianId		=	frmOnlinePreAuth.getString("clinicianId");
			DiagnosisDetailsVO diagnosisDetailsVO	=	cashlessDetailVO.getDiagnosisDetailsVO();
			
			ArrayList<DiagnosisDetailsVO> diagnosis	=	(ArrayList<DiagnosisDetailsVO>)(request.getSession().getAttribute("preauthDiagnosis")==null?new ArrayList<>():request.getSession().getAttribute("preauthDiagnosis"));
			ArrayList<ActivityDetailsVO> alActivityDetails= (ArrayList<ActivityDetailsVO>)(request.getSession().getAttribute("preauthActivities")==null?new ArrayList<>():request.getSession().getAttribute("preauthActivities"));
			ArrayList<DrugDetailsVO> alDrugDetails= (ArrayList<DrugDetailsVO>)(request.getSession().getAttribute("preauthDrugs")==null?new ArrayList<>():request.getSession().getAttribute("preauthDrugs"));
			
			if(diagnosis == null || diagnosis.size()<1 )
			{	
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Diagnosis.not.added");     
					throw expTTK;
			}	
			
			if(alActivityDetails == null || alActivityDetails.size()<1 )
			{	
				if(alDrugDetails == null || alDrugDetails.size()<1)
				{
					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.Treatment.or.Drug.not.added");     
					throw expTTK;
				}
			}	
			arrResult	= 	onlinePreAuthManager.savePartialPreAuthDetails(cashlessDetailVO,null,userSecurityProfile,(String)request.getSession().getAttribute("benifitTypeVal"));
			lPreAuthIntSeqId	=	new Long(arrResult[0]);
			int iResultDiag	=	onlinePreAuthManager.saveDiagnosisDetails(diagnosis,lPreAuthIntSeqId,userSecurityProfile.getUSER_SEQ_ID());
			int iResultActi	=	onlinePreAuthManager.saveActivitiesDetails(alActivityDetails,lPreAuthIntSeqId,userSecurityProfile.getUSER_SEQ_ID(),treatmentDate,clinicianId);
			int iResultDrug	=	onlinePreAuthManager.saveDrugDetails(alDrugDetails,lPreAuthIntSeqId,userSecurityProfile.getUSER_SEQ_ID(),treatmentDate,clinicianId);
			
			StringBuffer sb	=	new StringBuffer();
			String seqIds	=	"";
			//TO DELETE THE CHILD TABLE DATA WHICH ARE NOT IN FRONT END - ICD CODES - DIAGNOSIS
			Long lSuccess	=	null;
			if(diagnosis.size()>0){
				for(DiagnosisDetailsVO vo :diagnosis)
				{	
					sb.append(vo.getDiagSeqId()+",");
				}
			}
			if(sb.length()>0)
				seqIds		=	sb.substring(0, sb.length()-1);
			lSuccess	=	onlinePreAuthManager.deleteExistngDataForPreAuth(seqIds,lPreAuthIntSeqId,"ICD");
			
			
			//TO DELETE THE CHILD TABLE DATA WHICH ARE NOT IN FRONT END - ACTIVITY CODES - ACTIVITIES AND DRUGS AT SAME TIME AS DATA IS IN SAME TABLE
			sb	=	new StringBuffer();
			seqIds	=	"";
			if(alActivityDetails.size()>0){
				for(ActivityDetailsVO actVo :alActivityDetails)
				{
					sb.append(actVo.getActivitySeqId()+",");
				}
			}if(alDrugDetails.size()>0){
				for(DrugDetailsVO drugVo :alDrugDetails)
				{
					sb.append(drugVo.getDrugSeqId()+",");
				}
			}
			if(sb.length()>0)
				seqIds		=	sb.substring(0, sb.length()-1);
			lSuccess	=	onlinePreAuthManager.deleteExistngDataForPreAuth(seqIds,lPreAuthIntSeqId,"ACT");
			//retreiving Diagnosis codes
			/*diagnosis		=	onlinePreAuthManager.getDiagDetails(lPreAuthIntSeqId);
			request.getSession().setAttribute("preauthDiagnosis", diagnosis);*/
			
			//retreiving Activity codes
			/*diagnosis		=	onlinePreAuthManager.getActivityCodeDetails(hospSeqId, mode, activityCode, treatmentDate)(lPreAuthIntSeqId);
			request.getSession().setAttribute("preauthDiagnosis", diagnosis);*/
			
			Object[] preauthAllresult	=	onlinePreAuthManager.getOnlinePartialPreAuthDetails(lPreAuthIntSeqId);
    		
			cashlessDetailVO = (CashlessDetailVO) preauthAllresult[0];
			diagnosis = (ArrayList<DiagnosisDetailsVO>) preauthAllresult[1];
			alActivityDetails = (ArrayList<ActivityDetailsVO>) preauthAllresult[2];
			alDrugDetails = (ArrayList<DrugDetailsVO>) preauthAllresult[3];
			request.getSession().setAttribute("preauthDiagnosis", diagnosis);
			request.getSession().setAttribute("preauthActivities", alActivityDetails);
			request.getSession().setAttribute("preauthDrugs", alDrugDetails);
			
			
			//GET ALL DATA FROM 
			
			request.getSession().setAttribute("lPreAuthIntSeqId", lPreAuthIntSeqId);
			request.setAttribute("onlinePreAuthRefNO", arrResult[1]);
			//cashlessDetailVO.setPreAuthRefNo(arrResult[1]);
			cashlessDetailVO.setPreAuthSeqID(lPreAuthIntSeqId);
			frmOnlinePreAuth = setFormValues(cashlessDetailVO,mapping,request);
			request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
			
			request.setAttribute("updated", "message.savedSuccessfully");
			
			//request.setAttribute("MemberSave", "MemberSave"); 
			return this.getForward(strONLINEPREAUTH, mapping, request);
		}//end of try
	catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
	}//end of catch(Exception exp)
}//end of Partial(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



public ActionForward doSubmitOnlinePreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside the doSaveGeneral method of OnlinePreAuthAction");
			DynaActionForm frmOnlinePreAuth =(DynaActionForm)form;
			CashlessDetailVO cashlessDetailVO = null;
			cashlessDetailVO	=	(CashlessDetailVO)FormUtils.getFormValues(frmOnlinePreAuth,"frmOnlinePreAuth",
     				this,mapping,request);
			
			// UPLOAD FILE STARTS
				/*FormFile formFile = null;
				formFile = (FormFile)frmOnlinePreAuth.get("file");
				String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("tariffAdminUpload"));
				String fileString=new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+"-"+formFile;
				String finalPath=(path+fileString);
				FileOutputStream outputStream = null;
				outputStream = new FileOutputStream(new File(finalPath));
				if(formFile.getFileData()!=null)
					outputStream.write(formFile.getFileData());//Excel file Upload backUp
				cashlessDetailVO.setFileName(fileString);*/
			// UPLOAD FILE ENDS
						
			
			
			String eligibilitySeqID =(String) request.getSession().getAttribute("eligibilitySedId");
			
			cashlessDetailVO.setEligibilitySedId(eligibilitySeqID);
			
			
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
			OnlinePreAuthManager onlinePreAuthManager	=	this.getOnlineAccessManagerObject();
			Long	lPreAuthIntSeqId	=	null;
			String[] arrResult	=	new String[2];
			arrResult	= 	onlinePreAuthManager.savePreAuthDetails(cashlessDetailVO,null,userSecurityProfile,(String)request.getSession().getAttribute("benifitTypeVal"));
			lPreAuthIntSeqId	=	new Long(arrResult[0]);
			/*System.out.println("lPreAuthIntSeqId::"+lPreAuthIntSeqId);
			System.out.println("String ::"+arrResult[2] );*/
			String treatmentDate	=	frmOnlinePreAuth.getString("treatmentDate");
			String clinicianId		=	frmOnlinePreAuth.getString("clinicianId");
			DiagnosisDetailsVO diagnosisDetailsVO	=	cashlessDetailVO.getDiagnosisDetailsVO();
			
			ArrayList<DiagnosisDetailsVO> diagnosis	=	(ArrayList<DiagnosisDetailsVO>)request.getSession().getAttribute("preauthDiagnosis");
			int iResultDiag	=	onlinePreAuthManager.saveDiagnosisDetails(diagnosis,lPreAuthIntSeqId,userSecurityProfile.getUSER_SEQ_ID());
			
			ArrayList<ActivityDetailsVO> alActivityDetails= (ArrayList<ActivityDetailsVO>)request.getSession().getAttribute("preauthActivities");
			int iResultActi	=	onlinePreAuthManager.saveActivitiesDetails(alActivityDetails,lPreAuthIntSeqId,userSecurityProfile.getUSER_SEQ_ID(),treatmentDate,clinicianId);
			
			ArrayList<DrugDetailsVO> alDrugDetails= (ArrayList<DrugDetailsVO>)request.getSession().getAttribute("preauthDrugs");
			int iResultDrug	=	onlinePreAuthManager.saveDrugDetails(alDrugDetails,lPreAuthIntSeqId,userSecurityProfile.getUSER_SEQ_ID(),treatmentDate,clinicianId);
			
			request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
			request.getSession().setAttribute("lPreAuthIntSeqId", lPreAuthIntSeqId);
			request.setAttribute("onlinePreAuthNO", arrResult[1]);
			
			request.setAttribute("updated", "message.savedSuccessfully");
			//request.setAttribute("MemberSave", "MemberSave"); 
			
			return this.getForward(strONLINEPREAUTHSUCCESS, mapping, request);
		}//end of try
	catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
	}//end of catch(Exception exp)
}//end of doSubmitOnlinePreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)




public ActionForward doDefaultClinician(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
	try{
		log.debug("Inside the doDefaultClinician method of OnlinePreAuthAction");
		setOnlineLinks(request);
		HttpSession session=request.getSession();
		DynaActionForm frmOnlinePreAuth=(DynaActionForm) (session.getAttribute("frmOnlinePreAuth")==null?form:session.getAttribute("frmOnlinePreAuth"));
		
		TableData clinicianListData = new TableData();  //create new table data object
		clinicianListData.createTableInfo("ClinicianListTable",new ArrayList()); 
		session.setAttribute("clinicianListData",clinicianListData);//create the required grid table
		
		return this.getForward(strCliniciansList, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineEnrollment"));
	}//end of catch(Exception exp)
}//end of doDefaultClinician(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


public ActionForward clinicianSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside OnlinePreAuthAction clinicianSearch");
			setOnlineLinks(request);
			HttpSession session=request.getSession();
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			TableData clinicianListData = null;
			DynaActionForm frmPreAuthList=(DynaActionForm)form;
		if((request.getSession()).getAttribute("clinicianListData") != null)
		{
			clinicianListData = (TableData)session.getAttribute("clinicianListData");
		}//end of if((request.getSession()).getAttribute("icdListData") != null)
		else
		{
			clinicianListData = new TableData();
		}//end of else
		
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));		
		//if the page number or sort id is clicked
		if(!strPageID.equals("") || !strSortID.equals(""))
		{
			if(strPageID.equals(""))
		{
			clinicianListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
			clinicianListData.modifySearchData("sort");//modify the search data                    
		}///end of if(!strPageID.equals(""))
		else
		{
			log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
			clinicianListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
			return this.getForward(strCliniciansList, mapping, request);
		}//end of else
		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
		else{
			clinicianListData.createTableInfo("ClinicianListTable",null);
			clinicianListData.setSearchData(this.populateClinicianSearchCriteria(frmPreAuthList,request));
			clinicianListData.modifySearchData("search");				
		}//end of else
		
			ArrayList alClinicianList=null;
			alClinicianList= preAuthObject.getProviderClinicianList(clinicianListData.getSearchData());
			clinicianListData.setData(alClinicianList, "search");
			//set the table data object to session
			session.setAttribute("clinicianListData",clinicianListData);
			return this.getForward(strCliniciansList, mapping, request);
}//end of try
catch(TTKException expTTK)
	{
	return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
	{
	return this.processExceptions(request, mapping, new TTKException(exp,strCliniciansList));
}//end of catch(Exception exp)
}//end of clinicianSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


/*
 * Clinician Forward
 */

public ActionForward doClinicianForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
	try{
	log.debug("Inside OnlinePreAuthAction doClinicianForward");
	setOnlineLinks(request);
	TableData clinicianListData = (TableData)request.getSession().getAttribute("clinicianListData");
	PreAuthManager preAuthObject=this.getPreAuthManagerObject();
	clinicianListData.modifySearchData(strForward);//modify the search data
	ArrayList alClinicianList = preAuthObject.getProviderClinicianList(clinicianListData.getSearchData());
	clinicianListData.setData(alClinicianList, strForward);//set the table data
	request.getSession().setAttribute("clinicianListData",clinicianListData);   //set the table data object to session
	return this.getForward(strCliniciansList, mapping, request);   //finally return to the grid screen
}//end of try
catch(TTKException expTTK)
	{
	return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
	{
	return this.processExceptions(request, mapping, new TTKException(exp,strCliniciansList));
}//end of catch(Exception exp)
}//end of doClinicianForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


/**
Clinician Backward
*/
public ActionForward doClinicianBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
	try{
	log.debug("Inside OnlinePreAuthAction doClinicianBackward");
	setOnlineLinks(request);
	TableData clinicianListData = (TableData)request.getSession().getAttribute("clinicianListData");
	PreAuthManager preAuthObject=this.getPreAuthManagerObject();
	clinicianListData.modifySearchData(strBackward);//modify the search data
	ArrayList alClinicianList = preAuthObject.getProviderClinicianList(clinicianListData.getSearchData());
	clinicianListData.setData(alClinicianList, strBackward);//set the table data
	request.getSession().setAttribute("clinicianListData",clinicianListData);   //set the table data object to session
	return this.getForward(strCliniciansList, mapping, request);   //finally return to the grid screen
}//end of try
catch(TTKException expTTK)
	{
	return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
	{
	return this.processExceptions(request, mapping, new TTKException(exp,strCliniciansList));
}//end of catch(Exception exp)
}//end of doClinicianBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


public ActionForward doSelectClinicianId(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{
setOnlineLinks(request);
log.debug("Inside PreAuthGenealAction doSelectclinicianId ");
HttpSession session=request.getSession();
TableData clinicianListData = (TableData)session.getAttribute("clinicianListData");
if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
{
	ClinicianDetailsVO clinicianDetailsVO=(ClinicianDetailsVO)clinicianListData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
	
	DynaActionForm frmOnlinePreAuth=(DynaActionForm)session.getAttribute("frmOnlinePreAuth");
	
	CashlessDetailVO cashlessDetailVO	=	getFormValues(frmOnlinePreAuth, mapping, request);
	
	/*frmOnlinePreAuth.set("clinicianId", clinicianDetailsVO.getClinicianId());
	frmOnlinePreAuth.set("clinicianName", clinicianDetailsVO.getClinicianName());*/

	cashlessDetailVO.setClinicianId(clinicianDetailsVO.getClinicianId());
	cashlessDetailVO.setClinicianName(clinicianDetailsVO.getClinicianName());
	cashlessDetailVO.setSpeciality(clinicianDetailsVO.getClinicianSpeciality());
	cashlessDetailVO.setConsultation(clinicianDetailsVO.getClinicianConsultation());
	frmOnlinePreAuth = setFormValues(cashlessDetailVO,mapping,request);
	session.setAttribute("frmOnlinePreAuth",frmOnlinePreAuth);
		//forward=strPreAuthDetail;
}
return this.getForward(strONLINEPREAUTH, mapping, request);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strCliniciansList));
}//end of catch(Exception exp)
}//end of doSelectclinicianId(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


/*
 * Validate Clinician ID
 */

public ActionForward doValidateClinician(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{
setOnlineLinks(request);
log.debug("Inside PreAuthGenealAction doValidateClinician ");
	HttpSession session=request.getSession();
	UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile) session.getAttribute("UserSecurityProfile");
	String clinicianId	=	request.getParameter("clinicianId");
	PreAuthManager preAuthObject=this.getPreAuthManagerObject();
	ClinicianDetailsVO clinicianDetailsVO	=	preAuthObject.getProviderClinicianDetails(clinicianId,userSecurityProfile.getHospSeqId());
	
	PrintWriter out = response.getWriter();  
    response.setContentType("text/xml");  
    response.setHeader("Cache-Control", "no-cache");  
    response.setStatus(HttpServletResponse.SC_OK);  
    if(clinicianDetailsVO!=null)
    	out.write(clinicianDetailsVO.getClinicianId()+","+clinicianDetailsVO.getClinicianName()+","+clinicianDetailsVO.getClinicianSpeciality()+","+clinicianDetailsVO.getClinicianConsultation()+",");
    out.flush();  
    
    
	/*cashlessDetailVO.setClinicianId(clinicianDetailsVO.getClinicianId());
	cashlessDetailVO.setClinicianName(clinicianDetailsVO.getClinicianName());
	cashlessDetailVO.setSpeciality(clinicianDetailsVO.getClinicianSpeciality());
	cashlessDetailVO.setConsultation(clinicianDetailsVO.getClinicianConsultation());
	frmOnlinePreAuth = setFormValues(cashlessDetailVO,mapping,request);
			
	session.setAttribute("frmOnlinePreAuth",frmOnlinePreAuth);
		//forward=strPreAuthDetail;
return this.getForward(strONLINEPREAUTH, mapping, request);*/
    return null;
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strCliniciansList));
}//end of catch(Exception exp)
}//end of doValidateClinician(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


/*
 * EditOnlinePreAuth
 */
public ActionForward doEditOnlinePreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside the doEditOnlinePreAuth method of OnlinePreAuthAction");
			DynaActionForm frmOnlinePreAuth =(DynaActionForm)request.getSession().getAttribute("frmOnlinePreAuth");
			CashlessDetailVO cashlessDetailVO = null;
			cashlessDetailVO	=	(CashlessDetailVO)FormUtils.getFormValues(frmOnlinePreAuth,"frmOnlinePreAuth",
     				this,mapping,request);

			request.setAttribute("readonly", "false");
			request.getSession().setAttribute("frmOnlinePreAuth", frmOnlinePreAuth);
			return this.getForward(strONLINEPREAUTH, mapping, request);
		}//end of try
	catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
	}//end of catch(Exception exp)
}//end of doEditOnlinePreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)




/*
 * 
 */
public ActionForward doIcdSearch(ActionMapping mapping, ActionForm form,
        HttpServletRequest request, HttpServletResponse response)
        throws Exception {
StringBuilder builder=new StringBuilder();

    try {

        String providerName=request.getParameter("providerName");
        String type	=	request.getParameter("type");
if(providerName!=null&&providerName.trim().length()>0){
        PreAuthManager preAuthObject = null;

        if("Code".equals(type)){
            providerName=providerName.toUpperCase();
        if(providerList==null){
            preAuthObject = this.getPreAuthManagerObject();
         providerList=preAuthObject.getProviders(type);
         Set<Entry<String, String>> entries =providerList.entrySet();
            for(Entry<String, String> entry:entries){
                if(entry.getKey().startsWith(providerName)){
                    builder.append(entry.getKey());
                    builder.append("#");
                    builder.append(entry.getValue());
                    builder.append("|");
                }else if(entry.getKey().startsWith(providerName)){
                    builder.append(entry.getKey());
                    builder.append("#");
                    builder.append(entry.getValue());
                    builder.append("|");
                }
            }

        }else{
             Set<Entry<String, String>> entries =providerList.entrySet();
            for(Entry<String, String> entry:entries){
                if(entry.getKey().startsWith(providerName)){
                    builder.append(entry.getKey());
                    builder.append("#");
                    builder.append(entry.getValue());
                    builder.append("|");
                }else if(entry.getKey().startsWith(providerName)){
                    builder.append(entry.getKey());
                    builder.append("#");
                    builder.append(entry.getValue());
                    builder.append("|");
                }
            }
        }
        }else if("Desc".equals(type)){
            if(icdSedc==null){
                preAuthObject = this.getPreAuthManagerObject();
                icdSedc=preAuthObject.getProviders(type);
             Set<Entry<String, String>> entries =icdSedc.entrySet();
                for(Entry<String, String> entry:entries){
                    if(entry.getKey().startsWith(providerName)){
                        builder.append(entry.getKey());
                        builder.append("#");
                        builder.append(entry.getValue());
                        builder.append("|");
                    }else if(entry.getKey().startsWith(providerName)){
                        builder.append(entry.getKey());
                        builder.append("#");
                        builder.append(entry.getValue());
                        builder.append("|");
                    }
                }

            }else{
                 Set<Entry<String, String>> entries =icdSedc.entrySet();
                for(Entry<String, String> entry:entries){
                    if(entry.getKey().toUpperCase().startsWith(providerName.toUpperCase())){
                        builder.append(entry.getKey());
                        builder.append("#");
                        builder.append(entry.getValue());
                        builder.append("|");
                    }else if(entry.getKey().toUpperCase().startsWith(providerName.toUpperCase())){
                        builder.append(entry.getKey());
                        builder.append("#");
                        builder.append(entry.getValue());
                        builder.append("|");
                    }
                }
            }
            }else if("activityCode".equals(type)){
            	providerName=providerName.toUpperCase();
                if(activityCode==null){
                    preAuthObject = this.getPreAuthManagerObject();
                    activityCode=preAuthObject.getProviders(type);
                 Set<Entry<String, String>> entries =activityCode.entrySet();
                    for(Entry<String, String> entry:entries){
                        if(entry.getKey().startsWith(providerName)){
                            builder.append(entry.getKey());
                            builder.append("#");
                            builder.append(entry.getValue());
                            builder.append("|");
                        }else if(entry.getKey().startsWith(providerName)){
                            builder.append(entry.getKey());
                            builder.append("#");
                            builder.append(entry.getValue());
                            builder.append("|");
                        }
                    }

                }else{
                     Set<Entry<String, String>> entries =activityCode.entrySet();
                    for(Entry<String, String> entry:entries){
                        if(entry.getKey().startsWith(providerName)){
                            builder.append(entry.getKey());
                            builder.append("#");
                            builder.append(entry.getValue());
                            builder.append("|");
                        }else if(entry.getKey().startsWith(providerName)){
                            builder.append(entry.getKey());
                            builder.append("#");
                            builder.append(entry.getValue());
                            builder.append("|");
                        }
                    }
                }
                }
            else if("activityDesc".equals(type)){
                if(activityDesc==null){
                    preAuthObject = this.getPreAuthManagerObject();
                    activityDesc=preAuthObject.getProviders(type);
                 Set<Entry<String, String>> entries =activityDesc.entrySet();
                    for(Entry<String, String> entry:entries){
                        if(entry.getKey().toUpperCase().startsWith(providerName.toUpperCase())){
                            builder.append(entry.getKey());
                            builder.append("#");
                            builder.append(entry.getValue());
                            builder.append("|");
                        }else if(entry.getKey().toUpperCase().startsWith(providerName.toUpperCase())){
                            builder.append(entry.getKey());
                            builder.append("#");
                            builder.append(entry.getValue());
                            builder.append("|");
                        }
                    }

                }else{
                     Set<Entry<String, String>> entries =activityDesc.entrySet();
                    for(Entry<String, String> entry:entries){
                        if(entry.getKey().toUpperCase().startsWith(providerName.toUpperCase())){
                            builder.append(entry.getKey());
                            builder.append("#");
                            builder.append(entry.getValue());
                            builder.append("|");
                        }else if(entry.getKey().toUpperCase().startsWith(providerName.toUpperCase())){
                            builder.append(entry.getKey());
                            builder.append("#");
                            builder.append(entry.getValue());
                            builder.append("|");
                        }
                    		}
                		}
            		}else if("drugCode".equals(type)){
                    	providerName=providerName.toUpperCase();
                        if(drugCode==null){
                            preAuthObject = this.getPreAuthManagerObject();
                            drugCode=preAuthObject.getProviders(type);
                         Set<Entry<String, String>> entries =drugCode.entrySet();
                            for(Entry<String, String> entry:entries){
                                if(entry.getKey().startsWith(providerName)){
                                    builder.append(entry.getKey());
                                    builder.append("#");
                                    builder.append(entry.getValue());
                                    builder.append("|");
                                }else if(entry.getKey().startsWith(providerName)){
                                    builder.append(entry.getKey());
                                    builder.append("#");
                                    builder.append(entry.getValue());
                                    builder.append("|");
                                }
                            }
                        }else{
                             Set<Entry<String, String>> entries =drugCode.entrySet();
                            for(Entry<String, String> entry:entries){
                                if(entry.getKey().startsWith(providerName)){
                                    builder.append(entry.getKey());
                                    builder.append("#");
                                    builder.append(entry.getValue());
                                    builder.append("|");
                                }else if(entry.getKey().startsWith(providerName)){
                                    builder.append(entry.getKey());
                                    builder.append("#");
                                    builder.append(entry.getValue());
                                    builder.append("|");
                                	}
                            	}
                        	}
                        }
            		else if("drugDesc".equals(type)){
            if(drugDesc==null){
                preAuthObject = this.getPreAuthManagerObject();
                drugDesc=preAuthObject.getProviders(type);
             Set<Entry<String, String>> entries =drugDesc.entrySet();
                for(Entry<String, String> entry:entries){
                    if(entry.getKey().toUpperCase().startsWith(providerName.toUpperCase())){
                        builder.append(entry.getKey());
                        builder.append("#");
                        builder.append(entry.getValue());
                        builder.append("|");
                    }else if(entry.getKey().toUpperCase().startsWith(providerName.toUpperCase())){
                        builder.append(entry.getKey());
                        builder.append("#");
                        builder.append(entry.getValue());
                        builder.append("|");
                    }
                }

            }else{
                 Set<Entry<String, String>> entries =drugDesc.entrySet();
                for(Entry<String, String> entry:entries){
                    if(entry.getKey().toUpperCase().startsWith(providerName.toUpperCase())){
                        builder.append(entry.getKey());
                        builder.append("#");
                        builder.append(entry.getValue());
                        builder.append("|");
                    }else if(entry.getKey().toUpperCase().startsWith(providerName.toUpperCase())){
                        builder.append(entry.getKey());
                        builder.append("#");
                        builder.append(entry.getValue());
                        builder.append("|");
                    }
                		}
            		}
        		}
    PrintWriter writer=response.getWriter();
    writer.write(builder.toString());
    writer.flush();
    writer.close();

}//if(providerName!=null&&providerName.trim().length()>0){
    return null;
        }catch(Exception e){
        return null;
    }
}



 private DynaActionForm setFormValues(CashlessDetailVO cashlessDetailVO,ActionMapping mapping,
    		HttpServletRequest request) throws TTKException
    {
        try
        {
            DynaActionForm hospitalForm = (DynaActionForm)FormUtils.setFormValues("frmOnlinePreAuth",
        							   cashlessDetailVO,this,mapping,request);
        if(cashlessDetailVO.getDiagnosisDetailsVO()!=null)
        {
            hospitalForm.set("diagnosisDetailsVO", (DynaActionForm)FormUtils.setFormValues("frmDiagnosisDetails",
            		cashlessDetailVO.getDiagnosisDetailsVO(),this,mapping,request));
        }//end of if(cashlessDetailVO.getAddressVO()!=null)
        else
        {
            hospitalForm.set("diagnosisDetailsVO", (DynaActionForm)FormUtils.setFormValues("frmDiagnosisDetails",
            		new DiagnosisDetailsVO(),this,mapping,request));
        }//end of else
        
        if(cashlessDetailVO.getActivityDetailsVO()!=null)
        {
            hospitalForm.set("activityDetailsVO", (DynaActionForm)FormUtils.setFormValues("frmActivityDetails",
            		cashlessDetailVO.getActivityDetailsVO(),this,mapping,request));
        }//end of if(cashlessDetailVO.getAddressVO()!=null)
        else
        {
            hospitalForm.set("activityDetailsVO", (DynaActionForm)FormUtils.setFormValues("frmActivityDetails",
            		new ActivityDetailsVO(),this,mapping,request));
        }//end of else
        
        
        if(cashlessDetailVO.getDrugDetailsVO()!=null)
        {
            hospitalForm.set("drugDetailsVO", (DynaActionForm)FormUtils.setFormValues("frmDrugsDetails",
            		cashlessDetailVO.getDrugDetailsVO(),this,mapping,request));
        }//end of if(cashlessDetailVO.getAddressVO()!=null)
        else
        {
            hospitalForm.set("drugDetailsVO", (DynaActionForm)FormUtils.setFormValues("frmDrugsDetails",
            		new DrugDetailsVO(),this,mapping,request));
        }//end of else
        return hospitalForm;
    }
    catch(Exception exp)
    {
        throw new TTKException(exp,strOnlinePreAuth);
    }//end of catch
}//end of setFormValues(cashlessDetailVO cashlessDetailVO,ActionMapping mapping,HttpServletRequest request)

		 
		 
		 
 private CashlessDetailVO getFormValues(DynaActionForm frmOnlinePreAuth,ActionMapping mapping,
    		HttpServletRequest request) throws TTKException
    {
        try
        {
        	CashlessDetailVO cashlessDetailVO 		=	null;
            DiagnosisDetailsVO diagnosisDetailsVO	=	null;
            ActivityDetailsVO activityDetailsVO		=	null;
            DrugDetailsVO drugDetailsVO				=	null;

            cashlessDetailVO = (CashlessDetailVO)FormUtils.getFormValues(frmOnlinePreAuth,"frmOnlinePreAuth",
        				this,mapping,request);

        ActionForm DiagForm=(ActionForm)frmOnlinePreAuth.get("diagnosisDetailsVO");
        diagnosisDetailsVO=(DiagnosisDetailsVO)FormUtils.getFormValues(DiagForm,"frmDiagnosisDetails",this,mapping,request);
        //To bring country based on state selection along with cities
        
        ActionForm activityForm=(ActionForm)frmOnlinePreAuth.get("activityDetailsVO");
        activityDetailsVO=(ActivityDetailsVO)FormUtils.getFormValues(activityForm,"frmActivityDetails",this,mapping,request);
        
        ActionForm drugForm=(ActionForm)frmOnlinePreAuth.get("drugDetailsVO");
        drugDetailsVO=(DrugDetailsVO)FormUtils.getFormValues(drugForm,"frmDrugsDetails",this,mapping,request);
        
        cashlessDetailVO.setDiagnosisDetailsVO(diagnosisDetailsVO);
        cashlessDetailVO.setActivityDetailsVO(activityDetailsVO);
        cashlessDetailVO.setDrugDetailsVO(drugDetailsVO);
        
        return cashlessDetailVO;
    }//end of try
    catch(Exception exp)
    {
        throw new TTKException(exp,strOnlinePreAuth);
    }//end of catch
}//end of getFormValues(DynaActionForm frmOnlinePreAuth,ActionMapping mapping,HttpServletRequest request)
 
		 
		 
		 
		 
		 
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

private ArrayList<Object> populateActivityCodeSearchCriteria(DynaActionForm frmActivityList,HttpServletRequest request)
{
    UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");

	//build the column names along with their values to be searched
	ArrayList<Object> alSearchParams = new ArrayList<Object>();
	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivityList.getString("sActivityCode")));
	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivityList.getString("sActivityCodeDesc")));
	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivityList.getString("sSearchType")));
	alSearchParams.add((Long)userSecurityProfile.getHospSeqId());
	alSearchParams.add((String)request.getSession().getAttribute("enrollId"));
	//alSearchParams.add(TTKCommon.getUserSeqId(request));
	return alSearchParams;
}//end of populateActivityCodeSearchCriteria(DynaActionForm frmProductList,HttpServletRequest request)

		
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
		throw new TTKException(exp, strOnlinePreAuth);
	}//end of catch
	return preAuthManager;
}//end getPreAuthManagerObject()


/**
 * this method will add search criteria fields and values to the arraylist and will return it
 * @param frmPreAuthList formbean which contains the search fields
 * @param request HttpServletRequest
 * @return ArrayList contains search parameters
 */
private ArrayList<Object> populateClinicianSearchCriteria(DynaActionForm frmPreAuthList,HttpServletRequest request)
{
	//build the column names along with their values to be searched
    UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");

	ArrayList<Object> alSearchParams = new ArrayList<Object>();
	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sClinicianId")));
	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sClinicianName")));
	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sProviderId")));
	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sProviderName")));
	alSearchParams.add(TTKCommon.getUserSeqId(request));
	alSearchParams.add(userSecurityProfile.getHospSeqId());
	return alSearchParams;
}//end of populateActivityCodeSearchCriteria(DynaActionForm frmProductList,HttpServletRequest request)


private ArrayList<Object> populateDiagCodeSearchCriteria(DynaActionForm frmActivityList,HttpServletRequest request)
{
    UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");

	//build the column names along with their values to be searched
	ArrayList<Object> alSearchParams = new ArrayList<Object>();
	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivityList.getString("sActivityCode")));
	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmActivityList.getString("sActivityCodeDesc")));
	alSearchParams.add(TTKCommon.getUserSeqId(request));
	return alSearchParams;
}//end of populateDiagCodeSearchCriteria(DynaActionForm frmProductList,HttpServletRequest request)

 
public ActionForward providerObservWindow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthGenealAction providerObservWindow() ");
			String activityDtlSeqId = (String) request.getParameter("activityDtlSeqId");
			String type = (String) request.getParameter("type");
			String preAuthSeqId = (String) TTKCommon.checkNull(request.getParameter("preAuthSeqId")); 
			preAuthSeqId=(preAuthSeqId==null||preAuthSeqId.equals("null"))?"":preAuthSeqId;
		
			DynaActionForm frmObservDetails = (DynaActionForm) form;
			frmObservDetails.initialize(mapping);
			frmObservDetails.set("preAuthSeqID", preAuthSeqId);
			frmObservDetails.set("activityRowNum", request.getParameter("activityRowNum"));
			frmObservDetails.set("type",type);
			
			String strActRow=request.getParameter("activityRowNum");
			
			 ArrayList<ActivityDetailsVO> alActivityDetails=null;
			 ArrayList<DrugDetailsVO> aldrugs=null;
			 if("A".equals(type))
			 {
				 alActivityDetails=  (ArrayList<ActivityDetailsVO>)request.getSession().getAttribute("preauthActivities"); 
				 ActivityDetailsVO	currntActs=alActivityDetails.get(Integer.parseInt(strActRow));
					Long 	actDtlsSeqID=currntActs.getActivityDtlSeqId();
					if(actDtlsSeqID!=null&&actDtlsSeqID!=0)
					{
						OnlinePreAuthManager onlinePreAuthManager	=	this.getOnlineAccessManagerObject();
						request.getSession().setAttribute("observationsList", onlinePreAuthManager.getProviderAllObservDetails(actDtlsSeqID));
					}
					else 
					{
						ArrayList<ObservationDetailsVO> obsrList=currntActs.getObservList();
						if(obsrList==null) obsrList=new ArrayList<>();
						request.getSession().setAttribute("observationsList", obsrList);
					}
					frmObservDetails.set("type",type);
					request.getSession().setAttribute("frmObservDetails",frmObservDetails);
			 } 
			 else   if("D".equals(type))
			 {	 
				 aldrugs=  (ArrayList<DrugDetailsVO>)request.getSession().getAttribute("preauthDrugs");
				 DrugDetailsVO	currntActs=aldrugs.get(Integer.parseInt(strActRow));
					Long 	actDtlsSeqID=currntActs.getDrugSeqId();
					if(actDtlsSeqID!=null&&actDtlsSeqID!=0)
					{
						OnlinePreAuthManager onlinePreAuthManager	=	this.getOnlineAccessManagerObject();
						request.getSession().setAttribute("observationsList", onlinePreAuthManager.getProviderAllObservDetails(actDtlsSeqID));
					}
					else 
					{	
						ArrayList<ObservationDetailsVO> obsrList=currntActs.getObservList();
						if(obsrList==null) obsrList=new ArrayList<>();
						request.getSession().setAttribute("observationsList", obsrList);
					}
					frmObservDetails.set("type",type);
					request.getSession().setAttribute("frmObservDetails",frmObservDetails);
			 }
			 
			return mapping.findForward("providerAddObser");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strOnlinePreAuth));
		}// end of catch(Exception exp)

	}

public ActionForward getProviderObservTypeDetail(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception 
	{
			try {
					setLinks(request);
					log.debug("Inside PreAuthGenealAction getProviderObservTypeDetail ");
					DynaActionForm frmObservDetails = (DynaActionForm) form;
					PreAuthManager preAuthObject = this.getPreAuthManagerObject();
					String observType = frmObservDetails.getString("observType");
					Object[] observTypeDetails = preAuthObject.getProviderObservTypeDetails(observType);	
					request.getSession().setAttribute("observCodes",observTypeDetails[0]);
					request.getSession().setAttribute("observValueTypes",observTypeDetails[1]);
					return mapping.findForward("providerAddObser");
				}// end of try
			catch (TTKException expTTK) {
					return this.processExceptions(request, mapping, expTTK);
				}// end of catch(TTKException expTTK)
			catch (Exception exp) {
					return this.processExceptions(request, mapping, new TTKException(
						exp, "strOnlinePreAuth"));
				}// end of catch(Exception exp)
		
	}
	
		public ActionForward editProviderObserDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			try {
					setLinks(request);
					log.debug("Inside PreAuthGenealAction editProviderObserDetails ");
					if(request.getParameter("rownum")!=null){
					ArrayList<ObservationDetailsVO> observationsList=(ArrayList<ObservationDetailsVO>)request.getSession().getAttribute("observationsList");
					DynaActionForm frmObservDetails = (DynaActionForm) form;
					String preAuthSeqID = frmObservDetails.getString("preAuthSeqID");
					String activityRowNum = frmObservDetails.getString("activityRowNum");
					
					ObservationDetailsVO obDetailsVO=observationsList.get(new Integer(request.getParameter("rownum")).intValue());
					DynaActionForm frmObservDetails2 = (DynaActionForm)FormUtils.setFormValues("frmObservDetails",obDetailsVO,this,mapping,request);
					
					frmObservDetails2.set("preAuthSeqID", preAuthSeqID);
					frmObservDetails2.set("activityRowNum", activityRowNum);
					frmObservDetails2.set("observRowNum", request.getParameter("rownum"));
					 
					 request.getSession().setAttribute("frmObservDetails", frmObservDetails2);
				}
				return mapping.findForward("providerAddObser");
			}// end of try
			catch (TTKException expTTK) {
				return this.processExceptions(request, mapping, expTTK);
			}// end of catch(TTKException expTTK)
			catch (Exception exp) {
				return this.processExceptions(request, mapping, new TTKException(
						exp, strOnlinePreAuth));
			}// end of catch(Exception exp)

		}
		
		
		public ActionForward delProviderObservDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
			try {
				setLinks(request);
				log.debug("Inside PreAuthGenealAction delProviderObservDetails ");
				DynaActionForm frmObservDetails = (DynaActionForm) form;
				HttpSession session=request.getSession();
				
				String authSeqID =frmObservDetails.getString("preAuthSeqID");
				String[] obsvrIndexIds = request.getParameterValues("chkopt");
				String type = request.getParameter("type");
				
				if("A".equals(type))
				{
						ArrayList<ActivityDetailsVO> alActivityDetails= (ArrayList<ActivityDetailsVO>)session.getAttribute("preauthActivities");
					
						String strActRow=request.getParameter("activityRowNum");
						
						ActivityDetailsVO	currntActs=alActivityDetails.get(Integer.parseInt(strActRow));
						ArrayList<ObservationDetailsVO> obsrList=currntActs.getObservList();
						Long actDtlsSeqID=	currntActs.getActivityDtlSeqId();
					
						if(actDtlsSeqID!=null&&actDtlsSeqID!=0)
						{
							if(obsvrIndexIds!=null&&obsvrIndexIds.length>0)
							{
								OnlinePreAuthManager onlinePreAuthManager	=	this.getOnlineAccessManagerObject();
								StringBuilder sb=new StringBuilder();
								ArrayList<ObservationDetailsVO> observationsList =(ArrayList<ObservationDetailsVO>)session.getAttribute("observationsList");
								sb.append("|");
								for (String strIndexID : obsvrIndexIds)
								{
									sb.append(observationsList.get(new Integer(strIndexID).intValue()).getObservSeqId());
									sb.append("|");
								}
							
								onlinePreAuthManager.deleteProObservDetails(new Long(authSeqID),sb.toString());
								
								ArrayList<ObservationDetailsVO> observsList=onlinePreAuthManager.getProviderAllObservDetails(actDtlsSeqID);
								session.setAttribute("observationsList", observsList);
								request.setAttribute("successMsg","Observation Details Deleted Successfully.");
							}
						}
						else
						{	
							if(obsvrIndexIds!=null&&obsvrIndexIds.length>0)
							{
							
								for (String strIndexID : obsvrIndexIds)
								{
									obsrList.set(new Integer(strIndexID).intValue(),null);
								}		
								while(obsrList.contains(null))obsrList.remove(null);
								
								currntActs.setObservList(obsrList);
								session.setAttribute("observationsList", obsrList);
								session.setAttribute("preauthActivities", alActivityDetails);
								request.setAttribute("successMsg","Observation Details Deleted Successfully.");
							}
						}
				}
				if("D".equals(type))
				{
						ArrayList<DrugDetailsVO> aldrugs= (ArrayList<DrugDetailsVO>)session.getAttribute("preauthDrugs");	/*99*/
					
						String strActRow=request.getParameter("activityRowNum");
						
						DrugDetailsVO	currntActs=aldrugs.get(Integer.parseInt(strActRow));
						ArrayList<ObservationDetailsVO> obsrList=currntActs.getObservList();
						Long 	actDtlsSeqID=currntActs.getDrugSeqId();	
					
						if(actDtlsSeqID!=null&&actDtlsSeqID!=0)
						{
							if(obsvrIndexIds!=null&&obsvrIndexIds.length>0)
							{
								OnlinePreAuthManager onlinePreAuthManager	=	this.getOnlineAccessManagerObject();
								StringBuilder sb=new StringBuilder();
								ArrayList<ObservationDetailsVO> observationsList =(ArrayList<ObservationDetailsVO>)session.getAttribute("observationsList");
								sb.append("|");
								for (String strIndexID : obsvrIndexIds)
								{
									sb.append(observationsList.get(new Integer(strIndexID).intValue()).getObservSeqId());
									sb.append("|");
								}
							
								onlinePreAuthManager.deleteProObservDetails(new Long(authSeqID),sb.toString());
								
								ArrayList<ObservationDetailsVO> observsList=onlinePreAuthManager.getProviderAllObservDetails(actDtlsSeqID);
								session.setAttribute("observationsList", observsList);
								request.setAttribute("successMsg","Observation Details Deleted Successfully.");
							}
						}
						else
						{	
							if(obsvrIndexIds!=null&&obsvrIndexIds.length>0)
							{
							
								for (String strIndexID : obsvrIndexIds)
								{
									obsrList.set(new Integer(strIndexID).intValue(),null);
								}		
								while(obsrList.contains(null))obsrList.remove(null);
								currntActs.setObservList(obsrList);
								session.setAttribute("observationsList", obsrList);
								session.setAttribute("preauthDrugs", aldrugs);
								request.setAttribute("successMsg","Observation Details Deleted Successfully.");
							}
						}
				}	
			frmObservDetails.set("type",type);
			return mapping.findForward("providerAddObser");
			}// end of try
			catch (TTKException expTTK) {
				return this.processExceptions(request, mapping, expTTK);
			}// end of catch(TTKException expTTK)
			catch (Exception exp) {
				return this.processExceptions(request, mapping, new TTKException(
						exp, strOnlinePreAuth));
			}// end of catch(Exception exp)
		}
		
		 public ActionForward viewProviderObsDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		    		HttpServletResponse response) throws Exception
		  {
			 			log.debug("inside PreauthGeneralAction viewProviderObsDetails()");
				 		String fileName=(String)request.getParameter("filename");
				 		String filaPath=TTKPropertiesReader.getPropertyValue("ObservationFileUpload");
				        
				        File file=new File(filaPath+fileName);
				         if(!file.exists()){
				        	 ActionMessages actionMessages = new ActionMessages();
			                 ActionMessage actionMessage = new ActionMessage("error.file.not.exist");
			                 actionMessages.add("global.error",actionMessage);
			                 saveErrors(request,actionMessages);
				        	 
				         }
				         response.setContentType("application/txt");
				         response.setHeader("Content-Disposition", "attachment;filename="+fileName);
		         
				         ServletOutputStream sos=response.getOutputStream();
				         FileInputStream fis=new FileInputStream(file);
				           byte[]fileData=new byte[fis.available()];
				           fis.read(fileData);
				           sos.write(fileData);
				           fis.close();
				           sos.flush();
				           sos.close();
						 return null;
	     }	// end of viewProviderObsDetails()	
		 
		 
		 public ActionForward addObservetions(ActionMapping mapping,ActionForm form,HttpServletRequest request,
					HttpServletResponse response) throws Exception{
					try{
						setOnlineLinks(request);
						log.info("Inside the addObservetions method of OnlinePreAuthAction");
						HttpSession session=request.getSession();
						DynaActionForm frmObservDetails = (DynaActionForm) form;
						
						Matcher matcher =null;
						Pattern pattern =null;
						if("FIL".equals(frmObservDetails.getString("observType"))) 
						{
							
							FormFile formFile = (FormFile)frmObservDetails.get("fileName");		// file 
							pattern = Pattern.compile( "[^a-zA-Z0-9_\\.\\-\\s*]" );
							matcher = pattern.matcher( formFile.getFileName() );
							
							if(matcher.find())
							{	
								
								 TTKException expTTK = new TTKException();
									expTTK.setMessage("error.filetype.upload.format");
									throw expTTK;
							}	
						}							
						
						String strPreAuthSeqID= request.getParameter("preAuthSeqID");
						String type = request.getParameter("type");
						if("A".equals(type))
						{
							DynaActionForm frmOnlinePreAuth=(DynaActionForm)session.getAttribute("frmOnlinePreAuth");
							if(TTKCommon.checkNull(frmOnlinePreAuth.getString("preAuthNo")).length()>0)
							{
								frmObservDetails.set("type",type);
								return mapping.findForward("providerAddObser");
							}
							// file code
							String strFileName="";
							if("FIL".equals(frmObservDetails.getString("observType"))) 
							{	
							
								strFileName=saveObservFile((FormFile)frmObservDetails.get("fileName"));
							}
							ArrayList<ActivityDetailsVO> alActivityDetails= (ArrayList<ActivityDetailsVO>)session.getAttribute("preauthActivities");
						
							String strActRow=request.getParameter("activityRowNum");
						
							ActivityDetailsVO	currntActs=alActivityDetails.get(Integer.parseInt(strActRow));
								
							Long actDtlsSeqID=	currntActs.getActivityDtlSeqId();
							
							ObservationDetailsVO observationDetailsVO = new ObservationDetailsVO();
		
							observationDetailsVO = (ObservationDetailsVO) FormUtils.getFormValues(frmObservDetails, this, mapping, request);
							
							if("FIL".equals(frmObservDetails.getString("observType"))) 
							{	
						//		strFileName=saveObservFile((FormFile)frmObservDetails.get("fileName"));
								observationDetailsVO.setObservValue(strFileName);
							}
							
					
							if(actDtlsSeqID!=null&&actDtlsSeqID!=0)
							{
								OnlinePreAuthManager onlinePreAuthManager	=	this.getOnlineAccessManagerObject();
								ArrayList<ObservationDetailsVO> obsrAl=new ArrayList<>();
								obsrAl.add(observationDetailsVO);
								onlinePreAuthManager.saveObservationsDetails(obsrAl,actDtlsSeqID,new Long(strPreAuthSeqID),TTKCommon.getUserSeqId(request));
								ArrayList<ObservationDetailsVO> obsrList=onlinePreAuthManager.getProviderAllObservDetails(actDtlsSeqID);
								session.setAttribute("observationsList", obsrList);
								if(observationDetailsVO.getObservSeqId()!=null)
									request.setAttribute("updated", "message.savedSuccessfully");
								else
									request.setAttribute("updated", "message.addedSuccessfully");
								frmObservDetails.set("type",type);
								return mapping.findForward("providerAddObser");
							}
						
							ArrayList<ObservationDetailsVO> obsrList=currntActs.getObservList();
							if(obsrList==null) obsrList=new ArrayList<>();
							String observRowNum=request.getParameter("observRowNum");
							
							if(TTKCommon.checkNull(observRowNum).length()>0)obsrList.set(new Integer(observRowNum).intValue(),observationDetailsVO);
							else obsrList.add(observationDetailsVO);
							
							currntActs.setObservList(obsrList);
							
							alActivityDetails.set(Integer.parseInt(strActRow), currntActs);
						
							frmObservDetails.initialize(mapping);
							frmObservDetails.set("preAuthSeqID",strPreAuthSeqID);
							frmObservDetails.set("activityRowNum", request.getParameter("activityRowNum"));
							frmObservDetails.set("type",type);
							
							session.setAttribute("frmObservDetails",frmObservDetails);
							session.setAttribute("observationsList", obsrList);
						
						
							session.setAttribute("preauthActivities", alActivityDetails);
							if(observRowNum!=null)
								request.setAttribute("updated", "message.savedSuccessfully");
							else
								request.setAttribute("updated", "message.addedSuccessfully");
						}
						if("D".equals(type))
						{
							DynaActionForm frmOnlinePreAuth=(DynaActionForm)session.getAttribute("frmOnlinePreAuth");
							if(TTKCommon.checkNull(frmOnlinePreAuth.getString("preAuthNo")).length()>0)
							{
								frmObservDetails.set("type",type);
								return mapping.findForward("providerAddObser");
							}
							// file code
							String strFileName="";
							if("FIL".equals(frmObservDetails.getString("observType"))) 
							{	
							
								strFileName=saveObservFile((FormFile)frmObservDetails.get("fileName"));
							}
							ArrayList<DrugDetailsVO> aldrugs= (ArrayList<DrugDetailsVO>)session.getAttribute("preauthDrugs");	// 77
						
							String strActRow=request.getParameter("activityRowNum");
						
							DrugDetailsVO	currntActs=aldrugs.get(Integer.parseInt(strActRow));
								
							Long 	actDtlsSeqID=currntActs.getDrugSeqId();
							
							ObservationDetailsVO observationDetailsVO = new ObservationDetailsVO();
		
							observationDetailsVO = (ObservationDetailsVO) FormUtils.getFormValues(frmObservDetails, this, mapping, request);
							
							if("FIL".equals(frmObservDetails.getString("observType"))) 
							{	
						//		strFileName=saveObservFile((FormFile)frmObservDetails.get("fileName"));
								observationDetailsVO.setObservValue(strFileName);
							}
							if(actDtlsSeqID!=null&&actDtlsSeqID!=0){
								OnlinePreAuthManager onlinePreAuthManager	=	this.getOnlineAccessManagerObject();
								ArrayList<ObservationDetailsVO> obsrAl=new ArrayList<>();
								obsrAl.add(observationDetailsVO);
								onlinePreAuthManager.saveObservationsDetails(obsrAl,actDtlsSeqID,new Long(strPreAuthSeqID),TTKCommon.getUserSeqId(request));
								ArrayList<ObservationDetailsVO> obsrList=onlinePreAuthManager.getProviderAllObservDetails(actDtlsSeqID);
								session.setAttribute("observationsList", obsrList);
								if(observationDetailsVO.getObservSeqId()!=null)
									request.setAttribute("updated", "message.savedSuccessfully");
								else
									request.setAttribute("updated", "message.addedSuccessfully");
								frmObservDetails.set("type",type);
								return mapping.findForward("providerAddObser");
							}
							ArrayList<ObservationDetailsVO> obsrList=currntActs.getObservList();
							if(obsrList==null) obsrList=new ArrayList<>();
							
							String observRowNum=request.getParameter("observRowNum");
							
							if(TTKCommon.checkNull(observRowNum).length()>0)obsrList.set(new Integer(observRowNum).intValue(),observationDetailsVO);
							else obsrList.add(observationDetailsVO);
							
							currntActs.setObservList(obsrList);
							
							aldrugs.set(Integer.parseInt(strActRow), currntActs);
						
							frmObservDetails.initialize(mapping);
							frmObservDetails.set("preAuthSeqID",strPreAuthSeqID);
							frmObservDetails.set("activityRowNum", request.getParameter("activityRowNum"));
							frmObservDetails.set("type",type);
							session.setAttribute("frmObservDetails",frmObservDetails);
							session.setAttribute("observationsList", obsrList);
							session.setAttribute("preauthDrugs", aldrugs);
							if(observRowNum!=null)
							request.setAttribute("updated", "message.savedSuccessfully");
							else  request.setAttribute("updated", "message.addedSuccessfully");
						}
						return mapping.findForward("providerAddObser");
					}//end of try
				catch(TTKException expTTK)
					{
						return this.processOnlineExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
					{
						return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlinePreAuth));
				}//end of catch(Exception exp)
	}

		private String saveObservFile(FormFile formFile)throws Exception
		{
				String fileName2;
				String filename = formFile.toString();
				
				InputStream inputStream = formFile.getInputStream(); 		
				int formFileSize	=	formFile.getFileSize();				
			    if(formFileSize==0)
			    {
		            TTKException expTTK = new TTKException();
		            expTTK.setMessage("error.formfilesize");
		            throw expTTK;
			    }
			    else
				{
			            //COPYNG FILE TO SERVER FOR BACKUP
			            FileOutputStream outputStream = null;
			            String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("ObservationFileUpload"));
			            File folder = new File(path);
			            if(!folder.exists()){
			                  folder.mkdirs();
			            }
			            //String finalPath=(path+new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+"_"+formFile);
			            String timeStamps	=	new SimpleDateFormat("dd-MM-yyyy-HH-mm-SSS").format(new Date());
			            String fileExc[]=formFile.getFileName().split("[.]");
			            String oFileName=formFile.getFileName().split("."+fileExc[fileExc.length-1])[0];
			            String filetypesName = fileExc[1];
			            if("mp4".equals(filetypesName) || "exe".equals(filetypesName) || "mp3".equals(filetypesName) || "wmv".equals(filetypesName))
			            	{
			 		            TTKException expTTK = new TTKException();
			 		            expTTK.setMessage("error.filetype.mp3.mp4.exe");
			 		            throw expTTK;
			            	}
		            else
		            {
		             fileName2=oFileName+"-"+timeStamps+"."+fileExc[fileExc.length-1];
		            String origFileName	=	formFile.toString().substring(0, formFile.toString().lastIndexOf("."))+
		            		timeStamps+formFile.toString().substring(formFile.toString().lastIndexOf("."));
		            String finalPath=(path+formFile.toString().substring(0, formFile.toString().lastIndexOf("."))+
		            		timeStamps+formFile.toString().substring(formFile.toString().lastIndexOf(".")));
		            
			            if(oFileName.length()>100)
			            {
		 		            TTKException expTTK = new TTKException();
		 		            expTTK.setMessage("error.fileNamePath.PolicyDOCUploads");
		 		            throw expTTK;
			            }
		            else
			            {
			            outputStream = new FileOutputStream(new File(path+fileName2));
					    outputStream.write(formFile.getFileData());		//Uploaded file backUp
			            // E N D S FETCH FILE FROM LOCAL SYSTEM
						if(inputStream!=null)
							inputStream.close();
						if(outputStream!=null)
							outputStream.close();
			            }
		            }   
				  }     
			   
			return   fileName2;
		}
}//end of OnlineClmSearchHospAction
