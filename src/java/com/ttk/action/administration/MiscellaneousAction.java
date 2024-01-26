/**
 * @ (#) MiscellaneousAction
 * Project       : TTK HealthCare Services
 * File          : MiscellaneousAction.java
 * Author        : Navin Kumar R
 * Company       : Span Systems Corporation
 * Date Created  : 7th September,2009
 *
 * @author       : 
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.administration;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.enrollment.PolicyDetailVO;

import formdef.plugin.util.FormUtils;

public class MiscellaneousAction extends TTKAction {
	
	private static final Logger log = Logger.getLogger(MiscellaneousAction.class );

	//Exception Message Identifier
	private static final String strMiscellaneous="miscellaneous";
	private static final String strMiscellaneouslist="miscellaneouslist";
	private static final String strReportdisplay="reportdisplay";
	
	//Exception Message Identifier
	 private static final String strReportExp="report";
	 
	/**
	  * This method is used to initialize the Miscellaneous list.
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
	   							   HttpServletResponse response) throws TTKException 
	{
		try{
			log.debug("Inside the doDefault method of MiscellaneousAction");
	   		setLinks(request);   
	   		DynaActionForm frmMiscellaneous=(DynaActionForm)form;
	   		frmMiscellaneous.initialize(mapping);//reset the form data
			ProductPolicyManager productpolicyObject=this.getProductPolicyManagerObject();
			if(TTKCommon.getWebBoardId(request)==null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.policy.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request)==null)
			else{
				Long lngProductPolicySeqId=TTKCommon.getWebBoardId(request);
				//get the Policy details from the Dao object
				PolicyDetailVO policyDetailVO= productpolicyObject.getPolicyDetail(lngProductPolicySeqId,
						TTKCommon.getUserSeqId(request));
				request.getSession().setAttribute("policyDetailVO",policyDetailVO);
			}//end of else
			frmMiscellaneous.set("policyseqid", TTKCommon.getWebBoardId(request).toString());
	   		return this.getForward(strMiscellaneouslist, mapping, request);
	   	}//end of try
	   	catch(TTKException expTTK)
	   	{
	   		return this.processExceptions(request, mapping, expTTK);
	   	}//end of catch(TTKException expTTK)
	   	catch(Exception exp)
	   	{
	   		return this.processExceptions(request, mapping, new TTKException(exp, strMiscellaneous));
	   	}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	  * This method is used to view the miscellaneous report details.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	public ActionForward doMiscellaneousDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			   									HttpServletResponse response) throws TTKException 
	{
		try{
			log.debug("Inside the doMiscellaneousDetail method of MiscellaneousAction");			
			setLinks(request);   
			DynaActionForm frmMiscellaneous=(DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			frmMiscellaneous.set("caption",sbfCaption.toString());
			frmMiscellaneous.set("reportName",request.getParameter("reportName"));
			frmMiscellaneous.set("selectRptType",request.getParameter("selectRptType"));
			//request.getSession().setAttribute("frmMiscellaneous", frmMiscellaneous);
			return this.getForward("miscellaneousdetails",mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strMiscellaneous));
		}//end of catch(Exception exp)
	}//end of doMiscellaneousDetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
	/**
	  * This method is used to generate Claims Acknowledgement Report
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doFamiliesWOSI(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 											HttpServletResponse response) throws TTKException{
		 try{
			 log.info("Inside the doFamiliesWOSI method of MiscellaneousAciton");
			 setLinks(request);
//			 String strActiveLink=TTKCommon.getActiveLink(request);
			 JasperReport jasperReport,emptyReport,jasperSubReport;
			 JasperPrint jasperPrint;
			 TTKReportDataSource ttkReportDataSource = null , ttkSubReportDataSource = null;
			 String strPolicySeqID = TTKCommon.getWebBoardId(request).toString();
			 String strParam = "|SUM MISSING|"+strPolicySeqID+"||";
			 String strReportID =request.getParameter("reportID");	
			 String jrxmlfile="reports/enrollment/EnrollSumInsureMissing.jrxml";
			 ttkReportDataSource = new TTKReportDataSource(strReportID,strParam);
			 try
			 {
				 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
				 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
				 HashMap<String, Object> hashMap = new HashMap<String, Object>();
				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
				 if(ttkReportDataSource.getResultData().next())
				 {
					 ttkReportDataSource.getResultData().beforeFirst();
					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);					 
				 }//end of if(ttkReportDataSource.getResultData().next()))
				 else
				 {		
					 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());					
				 }//end of else
				 JRXlsExporter jExcelApiExporter = new JRXlsExporter();
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
					 jExcelApiExporter.exportReport();		
				 //JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
				 request.setAttribute("boas",boas);
			 }//end of try
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 }//end of catch (Exception e)
			 return mapping.findForward(strReportdisplay);
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doFamiliesWOSI(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
	 /**
	  * This method is used to generate Miscellanous enrollment Report
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doMisEnrollRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 											HttpServletResponse response) throws TTKException{
		 try{
			 log.info("Inside the doFamiliesWOSI method of MiscellaneousAciton");
			 setLinks(request);
//			 String strActiveLink=TTKCommon.getActiveLink(request);
			 JasperReport jasperReport,emptyReport,jasperSubReport;
			 JasperPrint jasperPrint;
			 TTKReportDataSource ttkReportDataSource = null , ttkSubReportDataSource = null;
			 String strPolicySeqID = TTKCommon.getWebBoardId(request).toString();
			 String strSelectRptType = request.getParameter("selectRptType");
			 String strEnrNumber = TTKCommon.checkNull(request.getParameter("enrollmentNumber"));			 
			 String strParam = "|"+strSelectRptType+"|"+strPolicySeqID+"|"+strEnrNumber+"|";
			 log.info("Param list :"+strParam);
			 String strReportID =request.getParameter("reportID");	
			 String jrxmlfile=request.getParameter("fileName"); //"";
			 ttkReportDataSource = new TTKReportDataSource(strReportID,strParam);
			 try
			 {
				 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
				 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
				 HashMap<String, Object> hashMap = new HashMap<String, Object>();
				 ByteArrayOutputStream boas=new ByteArrayOutputStream();
				 if(ttkReportDataSource.getResultData().next())
				 {
					 ttkReportDataSource.getResultData().beforeFirst();
					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);					 
				 }//end of if(ttkReportDataSource.getResultData().next()))
				 else
				 {		
					 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());					
				 }//end of else
				 if(request.getParameter("reportType").equals("EXL"))//if the report type is Excel
				 {
					 JRXlsExporter jExcelApiExporter = new JRXlsExporter();
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
					 jExcelApiExporter.exportReport();
				 }else{
					 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);					
				 }//end of else
				 request.setAttribute("boas",boas);
			 }//end of try
			 catch (Exception e)
			 {
				 e.printStackTrace();
			 }//end of catch (Exception e)
			 return mapping.findForward(strReportdisplay);
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)
	 }//end of doMisEnrollRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
	 
	/**
	  * Returns the ProductPolicyManager session object for invoking methods on it.
	  * @return productPolicyManager session object which can be used for method invokation
 	  * @exception throws TTKException
	  */
	private ProductPolicyManager getProductPolicyManagerObject() throws TTKException
	{
		ProductPolicyManager productPolicyManager = null;
		try
		{
			if(productPolicyManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyManager = (ProductPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProductPolicyManagerBean!com.ttk.business.administration.ProductPolicyManager");
			}//end of if(productPolicyManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strMiscellaneous);
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManagerObject()
	
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
			log.debug("Inside the doChangeWebBoard method of MiscellaneousAciton");
			setLinks(request);
			DynaActionForm frmPolicies =(DynaActionForm)form;
			ProductPolicyManager productpolicyObject=this.getProductPolicyManagerObject();
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
			frmPolicies.set("isBufferAllowedSaved",policyDetailVO.getBufferRecYN());
			request.getSession().setAttribute("frmPoliciesEdit",frmPolicies);
			this.documentViewer(request,policyDetailVO);
			return this.getForward(strMiscellaneouslist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	}//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
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
	
}//end of MiscellaneousAction