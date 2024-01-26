/**
 * @ (#) CourierDetailAction.javaMay 26, 2006
 * Project      : TTK HealthCare Services
 * File         : CourierDetailAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : May 26, 2006
 *
 * @author      :  Chandrasekaran J
 * Modified by  :
 * Modified date:
 * Reason       :
 */

package com.ttk.action.support;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.io.ByteArrayOutputStream;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JREmptyDataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter; 
import net.sf.jasperreports.engine.JasperPrint;

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
import com.ttk.business.enrollment.CourierManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.enrollment.CourierDetailVO;
import com.ttk.dto.enrollment.CourierVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import formdef.plugin.util.FormUtils;


/**
 * This class is used for adding courier.
 */

public class CourierDetailAction extends TTKAction
{
	private static Logger log = Logger.getLogger( CourierDetailAction.class );

	//declare forward paths
	private static final String strCourierlist="courierlist";
	private static final String strCourierDetail="courierdetail";
	private static final String strHospitallist="hospitallist";
	private static final String strForwardList="batchlist";
	private static final String strReportdisplay="reportdisplay";

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
	public ActionForward doViewCourier(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CourierDetailAction doViewCourier");
			setLinks(request);
			CourierDetailVO courierDetailVO=null;
			CourierVO courierVO=null;
			CourierManager courierMangerObject=this.getCourierManagerObject();
			DynaActionForm frmCourierDetails= (DynaActionForm)form;
			//if rownumber is found populate the form object
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				courierVO=(CourierVO)((TableData)request.getSession().getAttribute("tableData")).getData().get(
											Integer.parseInt(TTKCommon.checkNull(request.getParameter("rownum"))));
				courierDetailVO=courierMangerObject.getCourierDetail(courierVO.getCourierSeqID(),
											TTKCommon.getUserSeqId(request));
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			else
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.courier.required");
				throw expTTK;
			}//end of else
			frmCourierDetails = (DynaActionForm)FormUtils.setFormValues("frmCourierDetails",courierDetailVO,
															this,mapping,request);
			request.getSession().setAttribute("frmCourierDetails",frmCourierDetails);
			return this.getForward(strCourierDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"courierdetail"));
		}//end of catch(Exception exp)
	}//end of doViewCourier(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
			log.debug("Inside CourierDetailAction doAdd");
	
			setLinks(request);
			CourierDetailVO courierDetailVO=new CourierDetailVO();
			DynaActionForm frmCourierDetails= (DynaActionForm)form;
			String paramType=request.getParameter("paramType");
		
			
			
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
															getAttribute("UserSecurityProfile")).getBranchID());
			frmCourierDetails = (DynaActionForm)FormUtils.setFormValues("frmCourierDetails",courierDetailVO,
																			this,mapping,request);
			frmCourierDetails.set("courierTypeID",paramType);
			request.getSession().setAttribute("frmCourierDetails",frmCourierDetails);
			
			frmCourierDetails.set("officeSeqID",strDefaultBranchID);
			//return this.getForward(strCourierDetail, mapping, request);
			return mapping.findForward("courierdetail");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"courierdetail"));
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
								 HttpServletResponse response) throws Exception
	{
		try{
			log.debug("Inside CourierDetailAction doSave");
			setLinks(request);
			DynaActionForm frmCourierDetails= (DynaActionForm)form;
			CourierManager courierMangerObject=this.getCourierManagerObject();
			CourierDetailVO courierDetailVO=(CourierDetailVO)FormUtils.getFormValues(frmCourierDetails,
																		this, mapping, request);
			if(courierDetailVO.getCourierTypeID().equals("RCT"))
			{
				courierDetailVO.setContentTypeID(null);
				courierDetailVO.setContentDesc(null);
				//courierDetailVO.setStatusTypeID(null);
				courierDetailVO.setDeliveryDate(null);
				courierDetailVO.setDeliveryTime(null);
				courierDetailVO.setDeliveryDay(null);
				courierDetailVO.setProofDeliveryNbr(null);
				courierDetailVO.setDispatchDate(null);
				courierDetailVO.setDispatchTime(null);
				courierDetailVO.setDispatchDay(null);
				courierDetailVO.setHospSeqID(null);
				courierDetailVO.setCardBatchSeqID(null);
				courierDetailVO.setHospName(null);
				courierDetailVO.setEmpanelmentNbr(null);
				courierDetailVO.setCardBatchNbr(null);
				courierDetailVO.setBatchDate(null);
				courierDetailVO.setDispatchRemarks(null);
			}//end of if(courierDetailVO.getCourierTypeID().equals("RCT"))
			else
			{
			//	courierDetailVO.setDocketPODNbr(null);
				courierDetailVO.setNbrOfClaims(null);
				courierDetailVO.setNbrOfPolicies(null);
				courierDetailVO.setReceivedDate(null);
				courierDetailVO.setReceivedTime(null);
				courierDetailVO.setReceivedDay(null);
				courierDetailVO.setRcptRemarks(null);
				if(courierDetailVO.getContentTypeID().equals("HOP"))
				{
					courierDetailVO.setCardBatchSeqID(null);
				}//end of if(courierDetailVO.getContentTypeID().equals("HOP"))
				if(courierDetailVO.getContentTypeID().equals("ECD"))
				{
					courierDetailVO.setHospSeqID(null);
				}//end of if(courierDetailVO.getContentTypeID().equals("ECD"))
			}//end of else
			courierDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			Long lngUpdate=courierMangerObject.saveCourierDetail(courierDetailVO);
			if(lngUpdate>0)
			{
				if(frmCourierDetails.get("courierSeqID")!=null && !frmCourierDetails.get("courierSeqID").equals(""))
				{
					//set the appropriate message
					request.setAttribute("updated","message.savedSuccessfully");
					Cache.refresh("courierName");
				}//end of if(frmCourierDetails.get("courierSeqID")!=null &&
					//!frmCourierDetails.get("courierSeqID").equals(""))
				else
				{
					Cache.refresh("courierName");
					frmCourierDetails.initialize(mapping);
					courierDetailVO.setCourierTypeID("RCT");
					//set the appropriate message
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
			}//end of if(lngUpdate>0)
			courierDetailVO=courierMangerObject.getCourierDetail(lngUpdate,TTKCommon.getUserSeqId(request));
			frmCourierDetails = (DynaActionForm)FormUtils.setFormValues("frmCourierDetails",courierDetailVO,
													this,mapping,request);
			request.getSession().setAttribute("frmCourierDetails",frmCourierDetails);
			return this.getForward(strCourierDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"courierdetail"));
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
			log.debug("Inside CourierDetailAction doClose");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			CourierManager courierMangerObject=this.getCourierManagerObject();
			if(tableData.getSearchData()!= null&& tableData.getSearchData().size()>0)
			{
				ArrayList alCourier = courierMangerObject.getCourierList(tableData.getSearchData());

				tableData.setData(alCourier, "Cancel");
				request.getSession().setAttribute("tableData",tableData);
				//reset the forward links after adding the records if any
				tableData.setForwardNextLink();
			}//end of if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			return this.getForward(strCourierlist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"courierdetail"));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside CourierDetailAction doReset");
			setLinks(request);
			DynaActionForm frmCourierDetails= (DynaActionForm)form;
			CourierManager courierMangerObject=this.getCourierManagerObject();
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
															getAttribute("UserSecurityProfile")).getBranchID());
			CourierDetailVO courierDetailVO=null;
			if(!(TTKCommon.checkNull(frmCourierDetails.getString("courierSeqID")).equals("")))
			{
				courierDetailVO=courierMangerObject.getCourierDetail(TTKCommon.getLong(
										frmCourierDetails.getString("courierSeqID")),TTKCommon.getUserSeqId(request));
			}// end of if(!(TTKCommon.checkNull(frmCourierDetails.getString("rownum")).equals("")))
			else
			{
				courierDetailVO=new CourierDetailVO();
				courierDetailVO.setCourierTypeID("RCT");
			}//end of else
			frmCourierDetails = (DynaActionForm)FormUtils.setFormValues("frmCourierDetails",courierDetailVO,
													this,mapping,request);
			frmCourierDetails.set("officeSeqID",strDefaultBranchID);
			request.getSession().setAttribute("frmCourierDetails",frmCourierDetails);
			return this.getForward(strCourierDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"courierdetail"));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used for selecting hospital.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,
											HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CourierDetailAction doReset");
			setLinks(request);
			DynaActionForm frmCourierDetails= (DynaActionForm)form;
			frmCourierDetails.set("frmChanged","changed");

			return mapping.findForward(strHospitallist);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"courierdetail"));
		}//end of catch(Exception exp)
	}//end of doSelectHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * This method is used for selecting batch.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectBatch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CourierDetailAction doReset");
			setLinks(request);
			DynaActionForm frmCourierDetails= (DynaActionForm)form;
			frmCourierDetails.set("frmChanged","changed");

			return mapping.findForward(strForwardList);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"courierdetail"));
		}//end of catch(Exception exp)
	}//end of doSelectHospital(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * Returns the CourierManager session object for invoking methods on it.
	 * @return CourierManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private CourierManager getCourierManagerObject() throws TTKException
	{
		CourierManager courierManager = null;
		try
		{
			if(courierManager == null)
			{
				InitialContext ctx = new InitialContext();
				courierManager = (CourierManager) ctx.lookup("java:global/TTKServices/business.ejb3/CourierManagerBean!com.ttk.business.enrollment.CourierManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "courier");
		}//end of catch
		return courierManager;
	}//end getCourierManagerObject()
	
	
	
	
	//ADDED FOR 2KOC courier
	
	
	 public ActionForward doGenerateCourierRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 //log.debug("Inside the doGenerateCourierRpt method of CourierDetailAction");
			 setLinks(request);
			 JasperReport jasperReport= null,emptyReport;
			 JasperPrint jasperPrint;
			 TTKReportDataSource ttkReportDataSource = null;
			 String jrxmlfile, strReportID = "" ;
			 String parameter = request.getParameter("parameter");
			 String strCourierType = request.getParameter("CourierType");
			 String[] parts = parameter.split("|");
		
			 String strFrom = request.getParameter("sReportFrom");
			 String strTo = request.getParameter("sReportTo");
			 String strReportType = request.getParameter("reportType");
			
			 
			// File jrxmlfile = request.getParameter("fileName");
			 
			 strReportID = "InOutBound";
			 
			 ttkReportDataSource = new TTKReportDataSource(strReportID,parameter);
			// ttkReportDataSource = floatAccountManagerObject.generateOICPaymentAdvice(alGenerateXL);
			if(strCourierType.equalsIgnoreCase("RCT")){
			 jasperReport = JasperCompileManager.compileReport("generalreports/MRSInbound.jrxml");
			}
			else  if(strCourierType.equalsIgnoreCase("DSP")){
			 jasperReport = JasperCompileManager.compileReport("generalreports/MRSOutbound.jrxml");
			 }

			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			 HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
			 hashMap.put("CourierType",strCourierType);
			 hashMap.put("sFrom",strFrom);
			 hashMap.put("sTo",strTo);
			 hashMap.put("sReportType",strReportType);
			 
			 ByteArrayOutputStream boas=new ByteArrayOutputStream();
			 
			 if(!(ttkReportDataSource.getResultData().next()))
			 {
				 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
			 }//end of if(!(ttkReportDataSource.getResultData().next()))
			 else
			 {
				 ttkReportDataSource.getResultData().beforeFirst();
				 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);
			 }//end of if(ttkReportDataSource.getResultData().next())
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
			 }//end of if(request.getParameter("reportType").equals("EXL"))
			 else//if report type if PDF
			 {
				 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);	 
			 }//end of else
			 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
			 request.setAttribute("boas",boas);
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request, mapping, new TTKException(exp,"courierdetail"));
		 }//end of catch(Exception exp)		
		 return (mapping.findForward(strReportdisplay));
	 }//end of doGenerateIOBRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
	//END ADDED FOR 2KOC courier
	
}// end of CourierDetailAction