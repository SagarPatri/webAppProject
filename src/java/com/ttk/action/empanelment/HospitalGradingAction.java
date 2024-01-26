/**
 * @ (#) HospitalGradingAction.java Oct 15, 2005
 * Project       : TTK HealthCare Services
 * File          : HospitalGradingAction.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Oct 15, 2005
 *
 * @author       : Srikanth H M
 * Modified by   : Raghavendra T M
 * Modified date : March 10,2006
 * Reason        :
 */

package com.ttk.action.empanelment;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.validator.DynaValidatorForm;

import com.ttk.action.TTKAction;
import com.ttk.business.empanelment.GradingManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.empanelment.GradingServicesVO;
import com.ttk.dto.empanelment.GradingVO;
import com.ttk.dto.empanelment.InfraStructureVO;
import com.ttk.dto.onlineforms.OnlineFacilityVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for add/edit hospital grading information,saving hospital service information
 * add/edit medical infrastructure information,saving clinical support services information,
 * other hospital information and overall infrastructure information
 */

public class HospitalGradingAction extends TTKAction{
	private static Logger log = Logger.getLogger( HospitalGradingAction.class );
	
	//  Action mapping forwards
	private static final String strGrading="grading";
	private static final String strGeneral="general";
	private static final String strServices="services";
	private static final String strMedicalInfraStructure="medicalInfraStructure";
	private static final String strClinicalSupport="clinicalSupport";
	private static final String strOtherDetail="otherDetail";
	private static final String strGradingInfraStr="gradingInfraStr";
	private static final String strAddGrading="addGrading";
	
	//Exception Message Identifier
    private static final String strHospGradingError="grading";
	
    

    private static final String strAddSpecialities		=	"addspecialities";
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
    public ActionForward doViewGrading(ActionMapping mapping,ActionForm form,
    		HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewGrading method of HospitalGradingAction");
    		setLinks(request);
    		if(TTKCommon.getWebBoardId(request)==null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.hospital.required");
				throw expTTK;
			}//end of if
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
			GradingVO gradingVO=null;
    		gradingVO=gradingObject.getGradingInfo(TTKCommon.getWebBoardId(request));
			DynaActionForm  frmGradeDetails = (DynaActionForm)FormUtils.setFormValues("frmGradeDetails",
											  gradingVO, this, mapping, request);
			frmGradeDetails.set("gradeCode",gradingVO.getGradeTypeId());
			request.setAttribute("frmGradeDetails",frmGradeDetails);
			TTKCommon.documentViewer(request);
			return this.getForward(strGrading, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
		}//end of catch(Exception exp)
    }//end of doViewGrading(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    	log.debug("Inside doChangeWebBoard method of HospitalGradingAction");
    	//ChangeWebBoard method will call doViewGrading() method internally.
    	return doViewGrading(mapping,form,request,response);
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doViewGradingGeneral(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewGradingGeneral method of HospitalGradingAction");
    		setLinks(request);
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
			GradingVO gradingVO=null;
    		gradingVO=gradingObject.getGradingGeneralInfo(TTKCommon.getWebBoardId(request));
			DynaActionForm  frmGradingGeneral = (DynaActionForm)FormUtils.setFormValues("frmGradingGeneral",
												 gradingVO, this, mapping, request);
			frmGradingGeneral.set("caption",TTKCommon.getWebBoardDesc(request));
			request.setAttribute("frmGradingGeneral",frmGradingGeneral);
			return this.getForward(strGeneral, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
		}//end of catch(Exception exp)
    }//end of doViewGradingGeneral(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doSaveGradingGeneral(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSaveGradingGeneral method of HospitalGradingAction");
    		setLinks(request);
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
			GradingVO gradingVO=null;
    		DynaActionForm frmGradingGeneral=(DynaActionForm)request.getAttribute("frmGradingGeneral");
			gradingVO=new GradingVO();
			gradingVO=(GradingVO)FormUtils.getFormValues(frmGradingGeneral, "frmGradingGeneral",this, 
					  mapping, request);
			gradingVO.setHospSeqId(TTKCommon.getWebBoardId(request));
			gradingVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User ID
			//calling business layer to update the genral info
			int count=gradingObject.addUpdateGradingGeneral(gradingVO);
			if(count>0)
			{
				request.setAttribute("updated","message.saved");
			}//end of  if(count>0)
            frmGradingGeneral.set("caption",TTKCommon.getWebBoardDesc(request));
			return this.getForward(strGeneral, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
		}//end of catch(Exception exp)
    }//end of doSaveGradingGeneral(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside the doViewServiceList method of HospitalGradingAction");
    		setLinks(request);
    		ArrayList alGrading=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		GradingServicesVO gradingServicesVO=new GradingServicesVO();
			DynaActionForm frmAddServices = (DynaActionForm)FormUtils.setFormValues("frmAddServices",
											 gradingServicesVO, this, mapping, request);
			alGrading=gradingObject.getServices("SERV",TTKCommon.getWebBoardId(request));
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
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
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
    		log.debug("Inside the doSaveServices method of HospitalGradingAction");
    		setLinks(request);
    		ArrayList alGrading=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		DynaActionForm frmGradingServices = (DynaActionForm)request.getSession().getAttribute("frmAddServices");
			GradingServicesVO gradingServicesVO=new GradingServicesVO();
			gradingServicesVO=(GradingServicesVO)FormUtils.getFormValues(frmGradingServices, "frmAddServices",
							  this, mapping, request);
			gradingServicesVO.setAnswer1List((String[])frmGradingServices.get("selectedAnswer1List"));
			gradingServicesVO.setAnswer2List((String[])frmGradingServices.get("selectedAnswer2List"));
			gradingServicesVO.setMedicalTypeIdList((String[])frmGradingServices.get("selectedMedicalTypeId"));
			gradingServicesVO.setMedicalSeqIdList((Long[])frmGradingServices.get("selectedMedicalSeqId"));
			gradingServicesVO.setHospSeqId(TTKCommon.getWebBoardId(request));
			gradingServicesVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User ID
			int count=gradingObject.addUpdateServices(gradingServicesVO);
			if(count>0)
			{
				request.setAttribute("updated","message.saved");
			}//end of if(count>0)
			alGrading=gradingObject.getServices("SERV",TTKCommon.getWebBoardId(request));//hospital seq id from web board
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
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
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
    		log.debug("Inside the doViewMedicalServices method of HospitalGradingAction");
    		setLinks(request);
    		ArrayList alGrading=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		GradingServicesVO gradingServicesVO=new GradingServicesVO();
			DynaActionForm frmGradingMID = (DynaActionForm)FormUtils.setFormValues("frmGradingMID",
											gradingServicesVO, this, mapping, request);
			alGrading=gradingObject.getServices("MIDT",TTKCommon.getWebBoardId(request));//hospital seq id from web board
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
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
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
    		log.debug("Inside the doSaveMedicalServices method of HospitalGradingAction");
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
			gradingServicesVO.setHospSeqId(TTKCommon.getWebBoardId(request));
			gradingServicesVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User ID
			int count=gradingObject.addUpdateServices(gradingServicesVO);
			if(count>0)
			{
				alGrading=gradingObject.getServices("MIDT",TTKCommon.getWebBoardId(request));
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
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
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
    		log.debug("Inside the doViewClinicalServices method of HospitalGradingAction");
    		setLinks(request);
    		ArrayList alGrading=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		GradingServicesVO gradingServicesVO=new GradingServicesVO();
			DynaActionForm frmGradingClinicalSupport = (DynaActionForm)FormUtils.setFormValues("frmGradingClinicalSupport",
														gradingServicesVO, this, mapping, request);
			alGrading=gradingObject.getServices("CLSS",TTKCommon.getWebBoardId(request));//hospital seq id from web board
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
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
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
    		log.debug("Inside the doSaveClinicalServices method of HospitalGradingAction");
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
			gradingServicesVO.setHospSeqId(TTKCommon.getWebBoardId(request));
			gradingServicesVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User ID
			int count=gradingObject.addUpdateServices(gradingServicesVO);
			if(count>0)
			{
				request.setAttribute("updated","message.saved");
			}//end of if(count>0)
			alGrading=gradingObject.getServices("CLSS",TTKCommon.getWebBoardId(request));//hospital seq id from web board
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
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
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
    		log.debug("Inside the doViewOtherDetails method of HospitalGradingAction");
    		setLinks(request);
    		ArrayList alGrading=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		GradingServicesVO gradingServicesVO=new GradingServicesVO();
			DynaActionForm frmGradingOtherDetails = (DynaActionForm)FormUtils.setFormValues("frmOtherDetails",
													 gradingServicesVO, this, mapping, request);
			alGrading=gradingObject.getServices("OTDT",TTKCommon.getWebBoardId(request));//hospital seq id from web board
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
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
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
    		log.debug("Inside the doSaveOtherDetails method of HospitalGradingAction");
    		setLinks(request);
    		ArrayList alGrading=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		DynaActionForm frmGradOtherDetails = (DynaActionForm)request.getSession().getAttribute("frmOtherDetails");
			GradingServicesVO gradingServicesVO=new GradingServicesVO();
			gradingServicesVO =(GradingServicesVO)FormUtils.getFormValues(frmGradOtherDetails, "frmOtherDetails",
								this, mapping, request);
			gradingServicesVO.setAnswer1List((String[])frmGradOtherDetails.get("selectedAnswer1List"));
			gradingServicesVO.setMedicalTypeIdList((String[])frmGradOtherDetails.get("selectedMedicalTypeId"));
			gradingServicesVO.setMedicalSeqIdList((Long[])frmGradOtherDetails.get("selectedMedicalSeqId"));
			gradingServicesVO.setHospSeqId(TTKCommon.getWebBoardId(request));
			gradingServicesVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User ID
			int count=gradingObject.addUpdateServices(gradingServicesVO);
			if(count>0)
			{
				request.setAttribute("updated","message.saved");
			}//end of if(count>0)
			alGrading=gradingObject.getServices("OTDT",TTKCommon.getWebBoardId(request));//hospital seq id from web board
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
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
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
    		log.debug("Inside the doViewOverallDetails method of HospitalGradingAction");
    		setLinks(request);
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
			InfraStructureVO infrastrutureVO= null;
			infrastrutureVO=gradingObject.getInfraStructureInfo(TTKCommon.getWebBoardId(request));
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
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
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
    		log.debug("Inside the doSaveOverallDetails method of HospitalGradingAction");
    		setLinks(request);
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		DynaActionForm frmGradOverallDetails=(DynaActionForm)request.getAttribute("frmGradingInfraStr");
			frmGradOverallDetails.set("caption",TTKCommon.getWebBoardDesc(request));
			InfraStructureVO infrastrutureVO=new InfraStructureVO();
			infrastrutureVO =(InfraStructureVO)FormUtils.getFormValues(frmGradOverallDetails, 
					"frmGradingInfraStr",this, mapping, request);
			infrastrutureVO.setInfrastrSeqId((Long)frmGradOverallDetails.get("infrastrSeqId"));
			infrastrutureVO.setHospSeqId(TTKCommon.getWebBoardId(request));
			infrastrutureVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User ID
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
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
		}//end of catch(Exception exp)
    }//end of doSaveOverallDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doViewGradingInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewGradingInfo method of HospitalGradingAction");
    		setLinks(request);
    		GradingVO gradingVO=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
			//HOSPITAL SEQ ID FROM WEB BOARD
    		gradingVO=gradingObject.getGradingInfo(TTKCommon.getWebBoardId(request));
			DynaActionForm  frmUserGrading = (DynaActionForm)FormUtils.setFormValues("frmUserGrading",
											  gradingVO, this, mapping, request);
			//setting the formbean elements
			if(TTKCommon.isAuthorized(request,"Approve"))
			{
				frmUserGrading.set("gradecode",frmUserGrading.get("approvedGrade"));
			}//end of if(TTKCommon.isAuthorized(request,"Approve"))
			else
			{
				frmUserGrading.set("gradecode",frmUserGrading.get("gradeTypeId"));
			}//end of else
			frmUserGrading.set("caption",TTKCommon.getWebBoardDesc(request));
			request.setAttribute("frmUserGrading",frmUserGrading);
			return this.getForward(strAddGrading, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
		}//end of catch(Exception exp)
    }//end of doViewGradingInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doSaveGrading(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doSaveGrading method of HospitalGradingAction");
    		setLinks(request);
    		GradingVO gradingVO=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		DynaValidatorForm frmUserGrading = (DynaValidatorForm)form;
			gradingVO=new GradingVO();
            String strGradecode=frmUserGrading.getString("gradecode");
			gradingVO=(GradingVO)FormUtils.getFormValues(frmUserGrading, "frmUserGrading",this, mapping, request);
			gradingVO.setHospSeqId(TTKCommon.getWebBoardId(request));
			if(TTKCommon.isAuthorized(request,"Approve"))
			{
				gradingVO.setApprovedBy(TTKCommon.getUserSeqId(request));
				gradingVO.setApprovedGrade(frmUserGrading.getString("gradecode"));
			}//end of if(TTKCommon.isAuthorized(request,"Approve"))
			else
			{
				gradingVO.setGradeTypeId(frmUserGrading.getString("gradecode"));
			}//end of else
			gradingVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User ID
			int count=gradingObject.addUpdateGrading(gradingVO);
			if(count>0)
			{
				request.setAttribute("updated","message.saved");
			}//end of if(count>0)
            DynaActionForm  frmUserGrade = (DynaActionForm)FormUtils.setFormValues("frmUserGrading",gradingVO, 
            							    this, mapping, request);
			frmUserGrade.set("caption",TTKCommon.getWebBoardDesc(request));
            frmUserGrade.set("gradecode",strGradecode);
			request.setAttribute("frmUserGrading",frmUserGrade);
			return this.getForward(strAddGrading, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
		}//end of catch(Exception exp)
    }//end of doSaveGrading(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to save the Grade.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doGradeNow(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doGradeNow method of HospitalGradingAction");
    		setLinks(request);
    		GradingVO gradingVO=null;
    		//get the session bean from the bean pool for each excecuting thread
			GradingManager gradingObject=this.getGradingObject();
    		String strGradeNow=gradingObject.generateGrade(TTKCommon.getWebBoardId(request));
    		log.debug("strGradeNow value is :"+strGradeNow);
    		gradingVO=gradingObject.getGradingInfo(TTKCommon.getWebBoardId(request));
			DynaActionForm  frmGrading = (DynaActionForm)FormUtils.setFormValues("frmGradeDetails",
										  gradingVO, this, mapping, request);
			frmGrading.set("gradeCode",gradingVO.getGradeTypeId());
			request.setAttribute("frmGradeDetails",frmGrading);
			return this.getForward(strGrading, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospGradingError));
		}//end of catch(Exception exp)
    }//end of doGradeNow(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
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
    		log.debug("Inside the doAddServices method of HospitalGradingAction");
    		setLinks(request);
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
    		setLinks(request);
    		UserSecurityProfile userSecurityProfile =(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
    		OnlineFacilityVO onlineFacilityVO = new OnlineFacilityVO();	
    		
    		DynaActionForm generalForm = (DynaActionForm)form;
    		//generalForm.initialize(mapping);
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
}//end of HospitalGradingAction
