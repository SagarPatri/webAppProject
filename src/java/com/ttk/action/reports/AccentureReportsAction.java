/**
 * @ (#) AccentureReportsAction.java July 18, 2008
 * Project      : TTK HealthCare Services
 * File         : AccentureReportsAction.java 
 * Author       : Balaji C R B
 * Company      : Span Systems Corporation
 * Date Created : October 17, 2008
 *
 * @author       :  Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.reports;

import java.io.ByteArrayOutputStream;
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
import org.dom4j.Document;

import com.ttk.action.TTKAction;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;

public class AccentureReportsAction extends TTKAction
{
	private static Logger log = Logger.getLogger( AccentureReportsAction.class );

	//declaration of forward paths
	private static final String strAccentureRptDetails="accenturerptdetails";
	private static final String strEnrollmentRpts="enrollmentrpt";
	private static final String strReportdisplay="reportdisplay";
	private static final String strSelectGroup="selectgroup";

	//Exception Message Identifier
	private static final String strReportExp="report";
	private static final String strWebConfig="webconfig";

	/**
	 * This method is used to initialize the screen.
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
			HttpServletResponse response) throws Exception
	{
		try
		{
			//log.debug("Inside the Default method of AccentureReportsAction");
			setLinks(request);
			DynaActionForm frmReportList= (DynaActionForm) form;
			//log.debug("form is " + form);
			frmReportList.initialize(mapping);
			return this.getForward(strAccentureRptDetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			//log.debug("Inside the doClose method of AccentureReportsAction");
			setLinks(request);
			Document reportsListDoc=null;
			((DynaActionForm)form).initialize(mapping);//reset the form data
			//get the reports list Document
			reportsListDoc=TTKCommon.getDocument("ReportsList.xml");
			request.setAttribute("ReportsListDoc",reportsListDoc);
			return mapping.findForward(strEnrollmentRpts);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		}//end of catch(Exception exp) 
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)	
    
    /**
     * This method is called on click of Select Group icon
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSelectGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		//log.debug("Inside AccentureReportsAction doSelectGroup");
    		DynaActionForm frmReportList=(DynaActionForm)form;
    		request.getSession().setAttribute("frmReportList",frmReportList);
    		return mapping.findForward(strSelectGroup);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
    	}//end of catch(Exception exp)
    }//end of doSelectGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     //HttpServletResponse response)
    
    /**
     * This method is called on select of policy no. to load Report From and Report To fields
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		//log.debug("Inside AccentureReportsAction doSelectPolicy");
    		DynaActionForm frmReportList=(DynaActionForm)form;
    		DynaActionForm frmMISReports = (DynaActionForm)form;
    		ProductPolicyManager prodPolicyManager = this.getProductPolicyManagerObject();
    		Object[] objArray = null;
    		objArray = prodPolicyManager.getReportFromTo(TTKCommon.getLong(frmReportList.getString("policySeqID")));    		 
    		java.util.Date dtFrom = new java.util.Date();
    		java.util.Date dtTo = new java.util.Date();
    		java.text.SimpleDateFormat sdt;    	        
    		sdt = new java.text.SimpleDateFormat("dd/MM/yyyy");
    		String strFrom="";
    		String strTo="";
    		if(objArray!=null){
    			if( ((String)objArray[0]).length()!=0   ){
    				dtFrom.setDate(Integer.parseInt((String)objArray[0])  );
        			dtFrom.setMonth(dtTo.getMonth()-1);
        			strFrom = sdt.format(dtFrom);
    			}//end of if(!("".equals((String)objArray[0])))
    			if( ((String)objArray[1]).length()!=0   ){
    				dtTo.setDate( Integer.parseInt((String)objArray[1])  );
    				strTo = sdt.format(dtTo);
    			}  		
    			
    		}//end of if(!(("".equals((String)objArray[0])) && ("".equals((String)objArray[1])))){
    		if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment")))
    		{
    			//frmMISReports.set("sStartDate",strFrom );
    			//frmMISReports.set("sEndDate", strTo);
        		request.getSession().setAttribute("frmMISReports",frmMISReports);
    		}//end of if((TTKCommon.getActiveLink(request).equals("MIS Reports")&& TTKCommon.getActiveSubLink(request).equals("Enrollment"))) 
    		else
    		{
    			frmReportList.set("sReportFrom",strFrom );
    			frmReportList.set("sReportTo", strTo);
    			request.getSession().setAttribute("frmReportList",frmReportList);
    		}//end of else
    		return mapping.findForward(strAccentureRptDetails);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
    	}//end of catch(Exception exp)
    }//end of doSelectPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
     //HttpServletResponse response)  
   
    
    
    /**
	  * This method is used to generate the report.
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  *
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	 public ActionForward doGenerateAccentureRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
			 //log.debug("Inside the doGenerateIOBRpt method of AccentureReportsAction");
			 setLinks(request);
			 JasperReport jasperReport,emptyReport;
			 JasperPrint jasperPrint;
			 TTKReportDataSource ttkReportDataSource = null;
			 String jrxmlfile, strReportID = "" ;
			 String strGroupId = request.getParameter("groupId");
			 String strFrom = request.getParameter("sFrom");
			 String strTo = request.getParameter("sTo");
			 String strReportType = request.getParameter("reportType");
			 
			 jrxmlfile = request.getParameter("fileName");
			 strReportID = request.getParameter("reportID");
			 
			 ttkReportDataSource = new TTKReportDataSource(strReportID,request.getParameter("parameter"));
			 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
			 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			 HashMap<Object, Object> hashMap = new HashMap<Object, Object>();
			 hashMap.put("GroupID",strGroupId);
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
			 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
		 }//end of catch(Exception exp)		
		 return (mapping.findForward(strReportdisplay));
	 }//end of doGenerateIOBRpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
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
				throw new TTKException(exp, strWebConfig);
			}//end of catch
			return productPolicyManager;
		}//end getProductPolicyManagerObject()
}//end of AccentureReportsAction
