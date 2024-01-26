/**
 * @ (#) MedicalAilmentAction.java May 10th, 2006
 * Project 	     : TTK HealthCare Services
 * File          : MedicalAilmentAction.java
 * Author        : Raghavendra T M
 * Company       : Span Systems Corporation
 * Date Created  : May 10th, 2006
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.dataentrypreauth;

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
import com.ttk.business.dataentrypreauth.ParallelPreAuthManager;
//added for KOC-Decoupling
import com.ttk.business.dataentryadministration.ParallelTariffManager;
//ended
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.preauth.AssociatedIllnessVO;
import com.ttk.dto.preauth.PreAuthAilmentVO;
import com.ttk.dto.administration.TariffItemVO;
import formdef.plugin.util.FormUtils;


/**
 * This class is reusable for adding the medical ailmenet information in  pre-auth and claims flow.
 */

public class MedicalAilmentAction extends TTKAction  {

	private static Logger log = Logger.getLogger( MedicalAilmentAction.class ); // Getting Logger for this Class file

	//  Action mapping forwards
	private static final String strEditMedicalAilment="ailmentdetails";
	private static final String strEditClaimMedicalAilment="dataentryclaimsailmentdetails";
	private static final String strSaveAilmentDetails="dataentrysaveailment";
	private static final String strAilmentPromote = "dataentryailmentpromote";
	private static final String strAilmentRevert = "dataentryailmentrevert";

	//Exception Message Identifier
	private static final String strMedicalAilmentError="Medical Ailment";

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
	public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside MedicalAilmentAction  doView");
			ParallelPreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			String strFwdMode = null;
			String strLinkMode = null;
			Long lngSeqId = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			StringBuffer sbfCaption= new StringBuffer();
			PreAuthAilmentVO preauthAilmentVO = null;
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			//added for KOC-Decoupling
			TableData diagnosisData=null; 
			ArrayList alDiagnosis=new ArrayList();
			ArrayList<Object> alDiagnosisList = new ArrayList<Object>();
			
			if(strLink.equals("Pre-Authorization"))
			{
				strLinkMode = "PAT";
				strFwdMode = strEditMedicalAilment;
				lngSeqId = PreAuthWebBoardHelper.getPreAuthSeqId(request);
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				alDiagnosisList.add(null);	//added for KOC-Decoupling		
				
				
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("DataEntryClaims")||strLink.equals("Claims"))
			{
				strLinkMode = "CLM";
				strFwdMode = strEditClaimMedicalAilment;
				lngSeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				alDiagnosisList.add(lngSeqId); //added for KOC-Decoupling
			}//end of else if(strLink.equals("Claims"))
			
			//added for KOC-Decoupling
			alDiagnosis = preAuthManagerObject.getDiagnosisList(alDiagnosisList);//added for KOC-Decoupling
			diagnosisData = new TableData();
			diagnosisData.createTableInfo("DiagnosisTable",alDiagnosis);
			request.getSession().setAttribute("DiagnosisTable",diagnosisData);		
			preauthAilmentVO = preAuthManagerObject.getAilmentDetail(lngSeqId,TTKCommon.getUserSeqId(request),strLinkMode);
			
			frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",preauthAilmentVO, this, mapping, request);
			frmAilmentDetails.set("caption",sbfCaption.toString());
			frmAilmentDetails.set("durationTypeId",preauthAilmentVO.getDurationTypeID());
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])preauthAilmentVO.getAssocIllnessVO().toArray(new AssociatedIllnessVO[0]));
			if(strLink.equals("DataEntryClaims"))
			{
				frmAilmentDetails.set("linkMode","DECLM");
			}
			else
			{
				frmAilmentDetails.set("linkMode",strLinkMode);
			}
			request.getSession().setAttribute("frmAilmentDetails",frmAilmentDetails);
			return this.getForward(strFwdMode,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)
	}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)	
	
	/**added for CR KOC-Decoupling
	*  This method is used to navigate to package search screen to select specified package for diagnosis.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 *
	 */
	public ActionForward doViewPackage(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.debug("Inside MedicalAilmentAction doViewPackage");
			String strLink = "";
			String strClaimantName = "";
			String strWebBoardDesc = "";
			StringBuffer sbfCaption= new StringBuffer();
			TableData packageListData = null;
			
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			
			if((request.getSession()).getAttribute("packageListData") != null){
				packageListData = (TableData)(request.getSession()).getAttribute("packageListData");
			}//end of if((request.getSession()).getAttribute("packageListData") != null)
			else{
				packageListData = new TableData();
			}//end of else
			packageListData = new TableData();  //create new table data object
			packageListData.createTableInfo("PackageListTable",new ArrayList());     //create the required grid table
			request.getSession().setAttribute("packageListData",packageListData);
			((DynaActionForm)form).initialize(mapping); //reset the form data
			
			if(strLink.equals("Pre-Authorization"))
			{
				
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
			}
			else if(strLink.equals("DataEntryClaims")||strLink.equals("Claims"))
			{
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			}			
			frmAilmentDetails.set("caption",sbfCaption.toString());
			return mapping.findForward("diagnosispackagelist");			
			
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)		
	}	

	/**added for KOC-Decoupling
	 * 
	 * 
	 * 
	 */
	public ActionForward doPackageSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			String strPath="";
			log.debug("Inside MedicalAilmentAction doSearch");
			ParallelTariffManager tariffItemObject = this.getTariffManagerObject();
			TableData packageListData = null;
			if((request.getSession()).getAttribute("packageListData") != null){
				packageListData = (TableData)(request.getSession()).getAttribute("packageListData");
			}//end of if((request.getSession()).getAttribute("packageListData") != null)
			else{
				packageListData = new TableData();
			}//end of else

			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					packageListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(
																					request.getParameter("pageId"))));
					return this.getForward(strPath, mapping, request);
				}///end of if(!strPageID.equals(""))
				else
				{
					packageListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					packageListData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				packageListData.createTableInfo("PackageListTable",null);
				packageListData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				packageListData.modifySearchData("search");
			}//end of else
			//fetch the data from the data access layer and set the data to table object
			ArrayList alTariffItem= tariffItemObject.getTariffItemList(packageListData.getSearchData(),
																						"Pre-Authorization");
			packageListData.setData(alTariffItem, "search");
			//set the table data object to session
			request.getSession().setAttribute("packageListData",packageListData);
			return mapping.findForward("diagnosispackagelist");
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)
		
	}
	
	/**
	 *added for KOC-Decoupling
	 ***/
	public ActionForward doSelectPackage(ActionMapping mapping,ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		
		try
		{
			setLinks(request);
			String strLinks=TTKCommon.getActiveLink(request);
			String strSubLinks=TTKCommon.getActiveSubLink(request);
			String strPath="";
			log.debug("Inside MedicalAilmentAction doSelectPackage");
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			TableData packageListData = null;
			if((request.getSession()).getAttribute("packageListData") != null){
				packageListData = (TableData)(request.getSession()).getAttribute("packageListData");
			}//end of if((request.getSession()).getAttribute("packageListData") != null)
			else{
				packageListData = new TableData();
			}//end of else
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				TariffItemVO tariffItemVO=(TariffItemVO)packageListData.getRowInfo(Integer.parseInt((String)
																				request.getParameter("rownum")));
				if(frmAilmentDetails!=null)
				{
					frmAilmentDetails.set("tariffItemId", tariffItemVO.getTariffItemId());
					frmAilmentDetails.set("tariffItemName",tariffItemVO.getTariffItemName()); 
					//frmAilmentDetails.set("tariffItemVO",FormUtils.setFormValues("frmAssociate",tariffItemVO,this,mpping,request));
				}//end of if(frmICDPCSCoding!=null)
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward("dataentrysavediagnosis");
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)	
	}
		
	
	/**
	 * Returns the ArrayList which contains the populated search criteria elements.
	 * @param frmPackageSearch DynaActionForm will contains the values of corresponding fields.
	 * @param request HttpServletRequest object which contains the search parameter that is built.
	 * @return alSearchParams ArrayList.
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmPackageSearch,HttpServletRequest request) throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		String strLink=TTKCommon.getActiveLink(request);
		if(strLink.equals("Pre-Authorization")){
			alSearchParams.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
			alSearchParams.add(null);
		}//end of if(strLink.equals(strPreauth))
		else if(strLink.equals("DataEntryClaims")){
			alSearchParams.add(null);
			alSearchParams.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
		}//end of else if(strLink.equals(strClaims))		
		alSearchParams.add(TTKCommon.replaceSingleQots((String)	frmPackageSearch.get("sPackageName")));		
		
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmPackageSearch)
	
	/**
	 * This method is used to Save the Ailment Details.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside MedicalAilment Action doSave");
			ParallelPreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			String strLinkMode = null;
			Long lngSeqId = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			PreAuthAilmentVO preauthAilmentVO = null;
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			AssociatedIllnessVO associatedIllnessVO = null;
			StringBuffer sbfCaption= new StringBuffer();
			if(strLink.equals("Pre-Authorization"))
			{
				strLinkMode = "PAT";
				lngSeqId = PreAuthWebBoardHelper.getPreAuthSeqId(request);
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("DataEntryClaims"))
			{
				strLinkMode = "CLM";
				lngSeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of else if(strLink.equals("Claims"))
			frmAilmentDetails=(DynaActionForm)form;
			preauthAilmentVO =null;
			associatedIllnessVO = new AssociatedIllnessVO();
			preauthAilmentVO=(PreAuthAilmentVO)FormUtils.getFormValues(frmAilmentDetails, "frmAilmentDetails",this, mapping, request);
			ArrayList<Object> alassocIllnessVO = new ArrayList<Object>();
			Long[] lngSelectedAssocSeqID = (Long[])frmAilmentDetails.get("selectedAssocSeqID");
			String[] strSelectedAssocIllTypeID = (String[])frmAilmentDetails.get("selectedAssocIllTypeID");
			String[] strSelectedDurationTypeID = request.getParameterValues("durationTypeID");
			String[] strSelectedAssocIllDuration = request.getParameterValues("assocIllDuration");//SelectAssocIllTypeDuration//(Integer[])frmAilmentDetails.get("assocIllDuration");

			for(int i=0;i<lngSelectedAssocSeqID.length;i++)
			{
				associatedIllnessVO = new AssociatedIllnessVO();
				associatedIllnessVO.setAssocIllTypeID(strSelectedAssocIllTypeID[i]);
				associatedIllnessVO.setDurationTypeID(strSelectedDurationTypeID[i]);
				if(strSelectedAssocIllDuration[i].equals("")||strSelectedAssocIllDuration[i].equals(null))
				{
					associatedIllnessVO.setAssocIllDuration(null);
				}//end of if(strSelectedAssocIllDuration[i].equals("")||strSelectedAssocIllDuration[i].equals(null))
				else
				{
					associatedIllnessVO.setAssocIllDuration((strSelectedAssocIllDuration[i]));
				}//end of else
				associatedIllnessVO.setAssocSeqID(lngSelectedAssocSeqID[i]);
				alassocIllnessVO.add(associatedIllnessVO);
			}//end of for
			preauthAilmentVO.setAssocIllnessVO(alassocIllnessVO);
			preauthAilmentVO.setDurationTypeID(request.getParameter("durationTypeId"));
			if(strLink.equals("Pre-Authorization"))
			{
				preauthAilmentVO.setPreAuthSeqID(lngSeqId);
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("DataEntryClaims")||strLink.equals("Claims"))
			{
				preauthAilmentVO.setClaimSeqID(lngSeqId);
			}//end of else if(strLink.equals("Claims"))
			preauthAilmentVO.setAilmentSeqID(TTKCommon.getLong(frmAilmentDetails.getString("ailmentSeqID")));
			preauthAilmentVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
			Long iResult =preAuthManagerObject.saveAilment(preauthAilmentVO);
			
			if((iResult!=0))
			{
				//setting updated message to add and edit modes	appropriatly
				if(!TTKCommon.checkNull(frmAilmentDetails.getString("ailmentSeqID")).equals(""))
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}// end of if(!(frmAilmentDetails.getString("batchNbr").equals("")))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
			}//end of if(iResult!=0)
			preauthAilmentVO = preAuthManagerObject.getAilmentDetail(lngSeqId,TTKCommon.getUserSeqId(request),strLinkMode);
			frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",preauthAilmentVO, this, mapping, request);
			frmAilmentDetails.set("caption",sbfCaption.toString());
			frmAilmentDetails.set("durationTypeId",frmAilmentDetails.getString("durationTypeID"));
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])preauthAilmentVO.getAssocIllnessVO().toArray(new AssociatedIllnessVO[0]));
			if(strLink.equals("DataEntryClaims"))
			{
				frmAilmentDetails.set("linkMode","DECLM");
			}
			else
			{
				frmAilmentDetails.set("linkMode",strLinkMode);
			}
			request.getSession().setAttribute("frmAilmentDetails",frmAilmentDetails);
			return this.getForward(strSaveAilmentDetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	//doSaveDiagnosis- added for KOC-Decoupling
	public ActionForward doSaveDiagnosis(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.debug("Inside MedicalAilment Action doSave");
			ParallelPreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			String strLinkMode = null;
			Long lngSeqId = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			PreAuthAilmentVO preauthAilmentVO = null;
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			ArrayList<Object> alDiagnosisList = new ArrayList<Object>();
			StringBuffer sbfCaption= new StringBuffer();
			if(strLink.equals("Pre-Authorization"))
			{
				strLinkMode = "PAT";
				lngSeqId = PreAuthWebBoardHelper.getPreAuthSeqId(request);
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				alDiagnosisList.add(null);	
				
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("DataEntryClaims"))
			{
				strLinkMode = "CLM";
				lngSeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				alDiagnosisList.add(lngSeqId);
				
			}//end of else if(strLink.equals("Claims"))
			frmAilmentDetails=(DynaActionForm)form;
			preauthAilmentVO =null;
			preauthAilmentVO =(PreAuthAilmentVO)FormUtils.getFormValues(frmAilmentDetails, "frmAilmentDetails",this, mapping, request);			
			//preauthAilmentVO.setAilmentSeqID(TTKCommon.getLong(frmAilmentDetails.getString("ailmentSeqID")));
			preauthAilmentVO.setDiagnosisSeqId((Long)frmAilmentDetails.get("diagnosisSeqId"));
			preauthAilmentVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
			
			if(strLink.equals("Pre-Authorization"))
			{
				preauthAilmentVO.setPreAuthSeqID(lngSeqId);
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("DataEntryClaims")||strLink.equals("Claims"))
			{
				preauthAilmentVO.setClaimSeqID(lngSeqId);
			}//end of else if(strLink.equals("Claims"))
			
			
			Long iResult = preAuthManagerObject.saveDiagnosis(preauthAilmentVO);
			if((iResult!=0))
			{
				//setting updated message to add and edit modes	appropriatly
				if(!TTKCommon.checkNull(frmAilmentDetails.get("diagnosisSeqId")).equals(""))
				{
					request.setAttribute("updated","message.savedSuccessfully");
				}// end of if(!(frmAilmentDetails.getString("batchNbr").equals("")))
				else
				{
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
			}//end of if(iResult!=0)
			ArrayList alDiagnosis=new ArrayList();
			TableData diagnosisData=null; //added for KOC-Decoupling
			alDiagnosis = preAuthManagerObject.getDiagnosisList(alDiagnosisList);
			diagnosisData = new TableData();
			diagnosisData.createTableInfo("DiagnosisTable",alDiagnosis);
			request.getSession().setAttribute("DiagnosisTable",diagnosisData);		
			//return this.getForward(strSaveAilmentDetails,mapping,request);
			
			//added for form refresh and get the previous data other than diagnosis
			preauthAilmentVO = preAuthManagerObject.getAilmentDetail(lngSeqId,TTKCommon.getUserSeqId(request),strLinkMode);
			frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",preauthAilmentVO, this, mapping, request);
			frmAilmentDetails.set("caption",sbfCaption.toString());
			frmAilmentDetails.set("durationTypeId",frmAilmentDetails.getString("durationTypeID"));
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])preauthAilmentVO.getAssocIllnessVO().toArray(new AssociatedIllnessVO[0]));
			if(strLink.equals("DataEntryClaims"))
			{
				frmAilmentDetails.set("linkMode","DECLM");
			}
			else
			{
				frmAilmentDetails.set("linkMode",strLinkMode);
			}
			request.getSession().setAttribute("frmAilmentDetails",frmAilmentDetails);
			//ended
			return mapping.findForward("dataentrysavediagnosis");
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)		
		
	}	
	
	//added for KOC- Decoupling
	/**check this method for changes for CR KOC-Decoupling
	 * This method is used to get the delete record from the grid screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	
	public ActionForward doDelete(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.info("Inside  MedicalAilmentAction doDelete");
			String strForward="";
			String strLink = TTKCommon.getActiveLink(request);
			String strLinkMode = null;
			Long lngSeqId = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			StringBuffer sbfCaption= new StringBuffer();
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			TableData diagnosisDataTable=null; //added for KOC-Decoupling
			if((request.getSession()).getAttribute("DiagnosisTable") != null)
			{
				diagnosisDataTable = (TableData)(request.getSession()).getAttribute("DiagnosisTable");
			}//end of if((request.getSession()).getAttribute("AilmentTable") != null)
			PreAuthAilmentVO preauthAilmentVO = new PreAuthAilmentVO();			
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				preauthAilmentVO=(PreAuthAilmentVO)((TableData)request.getSession().getAttribute("DiagnosisTable")).
				          getData().get(Integer.parseInt(request.getParameter("rownum")));
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			
			ParallelPreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			preAuthManagerObject.deleteDiagnosis(preauthAilmentVO.getDiagnosisSeqId());
			ArrayList<Object> alDiagnosisList = new ArrayList<Object>();
			if(strLink.equals("Pre-Authorization"))
			{
				strLinkMode = "PAT";
				lngSeqId = PreAuthWebBoardHelper.getPreAuthSeqId(request);
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				alDiagnosisList.add(null);	
				
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("DataEntryClaims"))
			{
				strLinkMode = "CLM";
				lngSeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				alDiagnosisList.add(lngSeqId);
				
			}//end of else if(strLink.equals("Claims"))
			/*ArrayList alDiagnosis=new ArrayList();
			alDiagnosis = preAuthManagerObject.getDiagnosisList(alDiagnosisList);
			diagnosisTable = new TableData();
			diagnosisTable.createTableInfo("DiagnosisTable",alDiagnosis);
			request.getSession().setAttribute("DiagnosisTable",alDiagnosis);
		*/			
			ArrayList alDiagnosis=new ArrayList();
			alDiagnosis = preAuthManagerObject.getDiagnosisList(alDiagnosisList);
			diagnosisDataTable = new TableData();
			diagnosisDataTable.createTableInfo("DiagnosisTable",alDiagnosis);
			request.getSession().setAttribute("DiagnosisTable",diagnosisDataTable);	
			
			preauthAilmentVO = preAuthManagerObject.getAilmentDetail(lngSeqId,TTKCommon.getUserSeqId(request),strLinkMode);
			frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",preauthAilmentVO, this, mapping, request);
			frmAilmentDetails.set("caption",sbfCaption.toString());
			frmAilmentDetails.set("durationTypeId",preauthAilmentVO.getDurationTypeID());
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])preauthAilmentVO.getAssocIllnessVO().toArray(new AssociatedIllnessVO[0]));
			if(strLink.equals("DataEntryClaims"))
			{
				frmAilmentDetails.set("linkMode","DECLM");
			}
			else
			{
				frmAilmentDetails.set("linkMode",strLinkMode);
			}
			request.getSession().setAttribute("frmAilmentDetails",frmAilmentDetails);			
			return mapping.findForward("dataentrysavediagnosis");
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strMedicalAilmentError));
		}//end of catch(Exception exp)
		
		
	}
		
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
	
	public ActionForward doViewDiagnosis(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		
		try{
			
			setLinks(request);
			log.debug("Inside MedicalAilmentAction doView");
			String strLink = TTKCommon.getActiveLink(request);
			String strLinkMode = null;
			Long lngSeqId = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			PreAuthAilmentVO preauthAilmentVO = null;
			PreAuthAilmentVO diagnosisVO = null;
			ParallelPreAuthManager PreAuthManagerObject=this.getPreAuthManagerObject();
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			String strRowNum=frmAilmentDetails.getString("rownum");
			TableData DiagnosisTable =(TableData) request.getSession().getAttribute("DiagnosisTable");
			StringBuffer sbfCaption= new StringBuffer();
			if(strLink.equals("Pre-Authorization"))
			{
				strLinkMode = "PAT";
				//strFwdMode = strEditMedicalAilment;
				lngSeqId = PreAuthWebBoardHelper.getPreAuthSeqId(request);
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
								
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("DataEntryClaims")||strLink.equals("Claims"))
			{
				strLinkMode = "CLM";
				lngSeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                					sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				
			}//end of else if(strLink.equals("Claims"))
			if(!(TTKCommon.checkNull(strRowNum).equals("")))//edit flow
			{
				diagnosisVO =(PreAuthAilmentVO)DiagnosisTable.getRowInfo(Integer.parseInt((String)(frmAilmentDetails).get("rownum")));
				diagnosisVO = PreAuthManagerObject.getDiagnosisDetail(diagnosisVO.getDiagnosisSeqId());						
			}
			//frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",diagnosisVO,this,mapping,request);
			
			preauthAilmentVO = PreAuthManagerObject.getAilmentDetail(lngSeqId,TTKCommon.getUserSeqId(request),strLinkMode);
			frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",preauthAilmentVO, this, mapping, request);
			frmAilmentDetails.set("caption",sbfCaption.toString());
			frmAilmentDetails.set("durationTypeId",preauthAilmentVO.getDurationTypeID());
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])preauthAilmentVO.getAssocIllnessVO().toArray(new AssociatedIllnessVO[0]));
			if(strLink.equals("DataEntryClaims"))
			{
				frmAilmentDetails.set("linkMode","DECLM");
			}
			else
			{
				frmAilmentDetails.set("linkMode",strLinkMode);
			}			
			
			
			frmAilmentDetails.set("diagnosisSeqId",diagnosisVO.getDiagnosisSeqId());
			frmAilmentDetails.set("diagnosisDesc",diagnosisVO.getDiagnosisDesc());
			frmAilmentDetails.set("diagnosisType",diagnosisVO.getDiagnosisType());
			frmAilmentDetails.set("diagHospGenTypeId",diagnosisVO.getDiagHospGenTypeId());
			frmAilmentDetails.set("diagTreatmentPlanGenTypeId",diagnosisVO.getDiagTreatmentPlanGenTypeId());			
			frmAilmentDetails.set("freqOfVisit",diagnosisVO.getFreqOfVisit());
			frmAilmentDetails.set("noOfVisits",diagnosisVO.getNoOfVisits());	
			frmAilmentDetails.set("tariffGenTypeId",diagnosisVO.getTariffGenTypeId());
			request.getSession().setAttribute("frmAilmentDetails",frmAilmentDetails);
			return mapping.findForward("dataentrysavediagnosis");
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)		
		
	}	
	//added for CR KOC-Decoupling
	/**
	 * This method is used to Save the Ailment Details.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDataEntryPromote(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside MedicalAilment Action doDataEntryPromote");
			ParallelPreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			String strLinkMode = null;
			Long lngSeqId = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			PreAuthAilmentVO preauthAilmentVO = null;
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			AssociatedIllnessVO associatedIllnessVO = null;
			StringBuffer sbfCaption= new StringBuffer();
			if(strLink.equals("DataEntryClaims"))
			{
				strLinkMode = "CLM";
				lngSeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of else if(strLink.equals("Claims"))
			frmAilmentDetails=(DynaActionForm)form;
			preauthAilmentVO =null;
			associatedIllnessVO = new AssociatedIllnessVO();
			preauthAilmentVO=(PreAuthAilmentVO)FormUtils.getFormValues(frmAilmentDetails, "frmAilmentDetails",this, mapping, request);
			ArrayList<Object> alassocIllnessVO = new ArrayList<Object>();
			Long[] lngSelectedAssocSeqID = (Long[])frmAilmentDetails.get("selectedAssocSeqID");
			String[] strSelectedAssocIllTypeID = (String[])frmAilmentDetails.get("selectedAssocIllTypeID");
			String[] strSelectedDurationTypeID = request.getParameterValues("durationTypeID");
			String[] strSelectedAssocIllDuration = request.getParameterValues("assocIllDuration");//SelectAssocIllTypeDuration//(Integer[])frmAilmentDetails.get("assocIllDuration");

			for(int i=0;i<lngSelectedAssocSeqID.length;i++)
			{
				associatedIllnessVO = new AssociatedIllnessVO();
				associatedIllnessVO.setAssocIllTypeID(strSelectedAssocIllTypeID[i]);
				associatedIllnessVO.setDurationTypeID(strSelectedDurationTypeID[i]);
				if(strSelectedAssocIllDuration[i].equals("")||strSelectedAssocIllDuration[i].equals(null))
				{
					associatedIllnessVO.setAssocIllDuration(null);
				}//end of if(strSelectedAssocIllDuration[i].equals("")||strSelectedAssocIllDuration[i].equals(null))
				else
				{
					associatedIllnessVO.setAssocIllDuration((strSelectedAssocIllDuration[i]));
				}//end of else
				associatedIllnessVO.setAssocSeqID(lngSelectedAssocSeqID[i]);
				alassocIllnessVO.add(associatedIllnessVO);
			}//end of for
			preauthAilmentVO.setAssocIllnessVO(alassocIllnessVO);
			preauthAilmentVO.setDurationTypeID(request.getParameter("durationTypeId"));
			if(strLink.equals("DataEntryClaims"))
			{
				preauthAilmentVO.setClaimSeqID(lngSeqId);
			}//end of else if(strLink.equals("Claims"))
			preauthAilmentVO.setAilmentSeqID(TTKCommon.getLong(frmAilmentDetails.getString("ailmentSeqID")));
			preauthAilmentVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
			Long iResult = preAuthManagerObject.saveDataEntryPromote(preauthAilmentVO);
			preauthAilmentVO = preAuthManagerObject.getAilmentDetail(lngSeqId,TTKCommon.getUserSeqId(request),strLinkMode);
			frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",preauthAilmentVO, this, mapping, request);
			frmAilmentDetails.set("caption",sbfCaption.toString());
			frmAilmentDetails.set("durationTypeId",frmAilmentDetails.getString("durationTypeID"));
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])preauthAilmentVO.getAssocIllnessVO().toArray(new AssociatedIllnessVO[0]));
			if(strLink.equals("DataEntryClaims"))
			{
				frmAilmentDetails.set("linkMode","DECLM");
			}
			else
			{
				frmAilmentDetails.set("linkMode",strLinkMode);
			}
			request.getSession().setAttribute("frmAilmentDetails",frmAilmentDetails);
			return this.getForward(strAilmentPromote,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	/**
	 * This method is used to Save the Ailment Details.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDataEntryRevert(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside MedicalAilment Action doDataEntryRevert");
			ParallelPreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			String strLinkMode = null;
			Long lngSeqId = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			PreAuthAilmentVO preauthAilmentVO = null;
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			AssociatedIllnessVO associatedIllnessVO = null;
			StringBuffer sbfCaption= new StringBuffer();
			if(strLink.equals("DataEntryClaims"))
			{
				strLinkMode = "CLM";
				lngSeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of else if(strLink.equals("Claims"))
			frmAilmentDetails=(DynaActionForm)form;
			preauthAilmentVO =null;
			associatedIllnessVO = new AssociatedIllnessVO();
			preauthAilmentVO=(PreAuthAilmentVO)FormUtils.getFormValues(frmAilmentDetails, "frmAilmentDetails",this, mapping, request);
			ArrayList<Object> alassocIllnessVO = new ArrayList<Object>();
			Long[] lngSelectedAssocSeqID = (Long[])frmAilmentDetails.get("selectedAssocSeqID");
			String[] strSelectedAssocIllTypeID = (String[])frmAilmentDetails.get("selectedAssocIllTypeID");
			String[] strSelectedDurationTypeID = request.getParameterValues("durationTypeID");
			String[] strSelectedAssocIllDuration = request.getParameterValues("assocIllDuration");//SelectAssocIllTypeDuration//(Integer[])frmAilmentDetails.get("assocIllDuration");

			for(int i=0;i<lngSelectedAssocSeqID.length;i++)
			{
				associatedIllnessVO = new AssociatedIllnessVO();
				associatedIllnessVO.setAssocIllTypeID(strSelectedAssocIllTypeID[i]);
				associatedIllnessVO.setDurationTypeID(strSelectedDurationTypeID[i]);
				if(strSelectedAssocIllDuration[i].equals("")||strSelectedAssocIllDuration[i].equals(null))
				{
					associatedIllnessVO.setAssocIllDuration(null);
				}//end of if(strSelectedAssocIllDuration[i].equals("")||strSelectedAssocIllDuration[i].equals(null))
				else
				{
					associatedIllnessVO.setAssocIllDuration((strSelectedAssocIllDuration[i]));
				}//end of else
				associatedIllnessVO.setAssocSeqID(lngSelectedAssocSeqID[i]);
				alassocIllnessVO.add(associatedIllnessVO);
			}//end of for
			preauthAilmentVO.setAssocIllnessVO(alassocIllnessVO);
			preauthAilmentVO.setDurationTypeID(request.getParameter("durationTypeId"));
			if(strLink.equals("DataEntryClaims"))
			{
				preauthAilmentVO.setClaimSeqID(lngSeqId);
			}//end of else if(strLink.equals("Claims"))
			preauthAilmentVO.setAilmentSeqID(TTKCommon.getLong(frmAilmentDetails.getString("ailmentSeqID")));
			preauthAilmentVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
			Long iResult =preAuthManagerObject.saveDataEntryRevert(preauthAilmentVO);
			preauthAilmentVO = preAuthManagerObject.getAilmentDetail(lngSeqId,TTKCommon.getUserSeqId(request),strLinkMode);
			frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",preauthAilmentVO, this, mapping, request);
			frmAilmentDetails.set("caption",sbfCaption.toString());
			frmAilmentDetails.set("durationTypeId",frmAilmentDetails.getString("durationTypeID"));
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])preauthAilmentVO.getAssocIllnessVO().toArray(new AssociatedIllnessVO[0]));
			if(strLink.equals("DataEntryClaims"))
			{
				frmAilmentDetails.set("linkMode","DECLM");
			}
			else
			{
				frmAilmentDetails.set("linkMode",strLinkMode);
			}
			request.getSession().setAttribute("frmAilmentDetails",frmAilmentDetails);
			return this.getForward(strAilmentRevert,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)
	}//end of doDataEntryRevert

	
	
	//ended

	/**
	 * This method is used to the Submit the Ailment.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSubmit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside MedicalAilment Action doSubmit");
			String strLink = TTKCommon.getActiveLink(request);
			String strLinkMode = null;
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			AssociatedIllnessVO associatedIllnessVO = null;
			if(strLink.equals("Pre-Authorization"))
			{
				strLinkMode = "PAT";
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims"))
			{
				strLinkMode = "CLM";
			}//end of else if(strLink.equals("Claims"))
			frmAilmentDetails=(DynaActionForm)form;
			associatedIllnessVO = new AssociatedIllnessVO();
			ArrayList<Object> alassocIllnessVO = new ArrayList<Object>();
			Long[] lngSelectedAssocSeqID = (Long[])frmAilmentDetails.get("selectedAssocSeqID");
			String[] strSelectedAssocIllTypeDesc = request.getParameterValues("assocIllTypeDesc");
			String[] strSelectedAssocIllDuration = request.getParameterValues("assocIllDuration");
			String[] strSelectedAssocIllTypeID = (String[])frmAilmentDetails.get("selectedAssocIllTypeID");
			String[] strSelectedDurationTypeID = request.getParameterValues("durationTypeID");
			for(int i=0;i<lngSelectedAssocSeqID.length;i++)
			{
				associatedIllnessVO = new AssociatedIllnessVO();
				associatedIllnessVO.setAssocIllTypeID(strSelectedAssocIllTypeID[i]);
				associatedIllnessVO.setDurationTypeID(strSelectedDurationTypeID[i]);
				associatedIllnessVO.setAssocIllTypeDesc(strSelectedAssocIllTypeDesc[i]);
				if(strSelectedAssocIllDuration[i].equals("")||strSelectedAssocIllDuration[i].equals(null))
				{
					associatedIllnessVO.setAssocIllDuration(null);
				}//end of if(strSelectedAssocIllDuration[i].equals("")||strSelectedAssocIllDuration[i].equals(null))
				else{
					associatedIllnessVO.setAssocIllDuration(strSelectedAssocIllDuration[i]);
				}//end of else
				associatedIllnessVO.setAssocSeqID(lngSelectedAssocSeqID[i]);
				alassocIllnessVO.add(associatedIllnessVO);
			}//end of for
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])alassocIllnessVO.toArray(new AssociatedIllnessVO[0]));
			if(strLink.equals("DataEntryClaims"))
			{
				frmAilmentDetails.set("linkMode","DECLM");
			}
			else
			{
				frmAilmentDetails.set("linkMode",strLinkMode);
			}
			return mapping.findForward("submitform");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)
	}//end of doSubmit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to reset the Ailment values.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside MedicalAilment Action doReset");
			ParallelPreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			String strFwdMode = null;
			String strLinkMode = null;
			Long lngSeqId = null;
			String strClaimantName = "";
			String strWebBoardDesc = "";
			PreAuthAilmentVO preauthAilmentVO = null;
			DynaActionForm frmAilmentDetails = (DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			if(strLink.equals("Pre-Authorization"))
			{
				strLinkMode = "PAT";
				strFwdMode = strEditMedicalAilment;
				lngSeqId = PreAuthWebBoardHelper.getPreAuthSeqId(request);
				strClaimantName = PreAuthWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = PreAuthWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("DataEntryClaims"))
			{
				strLinkMode = "CLM";
				strFwdMode = strEditClaimMedicalAilment;
				lngSeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
				strClaimantName = ClaimsWebBoardHelper.getClaimantName(request);
				strWebBoardDesc = ClaimsWebBoardHelper.getWebBoardDesc(request);
				sbfCaption.append(" - [ "+strClaimantName+" ]"+" [ "+strWebBoardDesc+" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of else if(strLink.equals("Claims"))
			preauthAilmentVO = new PreAuthAilmentVO();
			preauthAilmentVO = preAuthManagerObject.getAilmentDetail(lngSeqId,TTKCommon.getUserSeqId(request),strLinkMode);
			frmAilmentDetails = (DynaActionForm)FormUtils.setFormValues("frmAilmentDetails",preauthAilmentVO, this, mapping, request);
			frmAilmentDetails.set("caption",sbfCaption.toString());
			frmAilmentDetails.set("durationTypeId",preauthAilmentVO.getDurationTypeID());
			frmAilmentDetails.set("assocIllnessList",(AssociatedIllnessVO[])preauthAilmentVO.getAssocIllnessVO().toArray(new AssociatedIllnessVO[0]));
			if(strLink.equals("DataEntryClaims"))
			{
				frmAilmentDetails.set("linkMode","DECLM");
			}
			else
			{
				frmAilmentDetails.set("linkMode",strLinkMode);
			}
			request.getSession().setAttribute("frmAilmentDetails",frmAilmentDetails);
			return this.getForward(strFwdMode,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMedicalAilmentError));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ParallelPreAuthManager getPreAuthManagerObject() throws TTKException
	{
		ParallelPreAuthManager preAuthManager = null;
		try
		{
			if(preAuthManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthManager = (ParallelPreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/ParallelPreAuthManagerBean!com.ttk.business.dataentrypreauth.ParallelPreAuthManager");
								
			}//end of if(preAuthManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strMedicalAilmentError);
		}//end of catch
		return preAuthManager;
	}//end of getPreAuthManagerObject()
	
	private ParallelTariffManager getTariffManagerObject() throws TTKException
	{
		ParallelTariffManager tariffManager = null;
		try
		{
			if(tariffManager==null)
			{
				InitialContext ctx = new InitialContext();
				tariffManager = (ParallelTariffManager)ctx.lookup("java:global/TTKServices/business.ejb3/ParallelTariffManagerBean!com.ttk.business.dataentryadministration.ParallelTariffManager");					
			}
		}
		catch(Exception exp)
		{
			throw new TTKException(exp, strMedicalAilmentError);
		}
		return tariffManager;
	}
	
}//end of MedicalAilmentAction