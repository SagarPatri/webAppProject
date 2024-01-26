/**
 *   @ (#) BrokerReportsAction.java Dec 29, 2015
 *   Project 	   : Dubai Project
 *   File          : BrokerReportsAction.java
 *   Author        : Nagababu K
 *   Company       : RCS
 *   Date Created  : Dec 29, 2015
 *
 *   @author       :  Nagababu K
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 *
 */

package com.ttk.action.broker;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
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
import com.ttk.business.broker.OnlineBrokerManager;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;

/**
 * This class is used for Searching the List Policies to see the Account Info.
 * This also provides deletion and updation of products.
 */
public class BrokerReportsAction extends TTKAction {

    private static Logger log = Logger.getLogger( BrokerReportsAction.class );

    //Modes.
    //private static final String strBackward="Backward";
    //private static final String strForward="Forward";

    // Action mapping forwards.
    private static final String strReportSearch="reportSearch";
    private static final String strReportList="reportList";
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
            log.debug("Inside the doDefault method of BrokerReportsAction");
            setOnlineLinks(request);            
            DynaActionForm frmBroReports =(DynaActionForm)form;
            frmBroReports.initialize(mapping);     //reset the form data
            return this.getForward(strReportList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processBrokerExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processBrokerExceptions(request, mapping, new TTKException(exp,strReportSearch));
        }//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
    
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
    public ActionForward doViewReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the doViewReports method of BrokerReportsAction");
            setOnlineLinks(request);  
            HttpSession session=request.getSession();
            OnlineBrokerManager brokerManager = null;
            
            DynaActionForm frmInsReports =(DynaActionForm)form;
            
            brokerManager=this.getBrokerManagerObject();
            String brokerCode=(String)session.getAttribute("brokerCode");
            String reportID=frmInsReports.getString("reportID");
            String reportLabel=frmInsReports.getString("reportLabel");            
            frmInsReports.initialize(mapping);  
            frmInsReports.set("reportID", reportID);
            frmInsReports.set("reportLabel", reportLabel);
            session.setAttribute("corporateNameList", brokerManager.getCorporateList(brokerCode));
            if("BroTATFSINDIVIDUAL".equals(reportID)){
            	 session.setAttribute("policyNumberList",brokerManager.getINDPolicyNumberList(brokerCode));
            }else{
            	session.setAttribute("policyNumberList",new ArrayList<>());
            }
            
            session.setAttribute("policyPeriodList",new ArrayList<>());
            session.setAttribute("frmInsReports", frmInsReports);
            return this.getForward(strReportSearch, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processBrokerExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processBrokerExceptions(request, mapping, new TTKException(exp,strReportSearch));
        }//end of catch(Exception exp)
    }//end of doViewReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,         //HttpServletResponse response)
    
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
    public ActionForward getPolicyNumberList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the getPolicyNumberList method of BrokerReportsAction");
            setOnlineLinks(request);  
            HttpSession session=request.getSession();
            OnlineBrokerManager brokerManager = null;
            
            DynaActionForm frmInsReports =(DynaActionForm)form;
            
            brokerManager=this.getBrokerManagerObject();
            String corporateName=frmInsReports.getString("corporateName");
            corporateName=(corporateName==null||corporateName.length()<1)?"0":corporateName;
            session.setAttribute("policyNumberList",brokerManager.getPolicyNumberList(new Long(corporateName)));
            session.setAttribute("policyPeriodList",new ArrayList<>());
            session.setAttribute("frmInsReports", frmInsReports);
            return this.getForward(strReportSearch, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processBrokerExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processBrokerExceptions(request, mapping, new TTKException(exp,strReportSearch));
        }//end of catch(Exception exp)
    }//end of getPolicyNumberList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    public ActionForward getPolicyPeriodList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the getPolicyPeriodList method of BrokerReportsAction");
            setOnlineLinks(request);  
            HttpSession session=request.getSession();
            OnlineBrokerManager brokerManager = null;
            
            DynaActionForm frmInsReports =(DynaActionForm)form;
            
            brokerManager=this.getBrokerManagerObject();
            String policyNumber=frmInsReports.getString("policyNumber");
            policyNumber=(policyNumber==null||policyNumber.length()<1)?"0":policyNumber;
            session.setAttribute("policyPeriodList",brokerManager.getPolicyPeriodList(new Long(policyNumber)));
            session.setAttribute("frmInsReports", frmInsReports);
            return this.getForward(strReportSearch, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processBrokerExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processBrokerExceptions(request, mapping, new TTKException(exp,strReportSearch));
        }//end of catch(Exception exp)
    }//end of getPolicyPeriodList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    
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
    public ActionForward onGenerateReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
	 try{
		 log.debug("Inside the onGenerateReports method of ReportsAction");
		 setLinks(request);
		 DynaActionForm frmInsReports = (DynaActionForm)form;
		 String reportID=frmInsReports.getString("reportID");	
		 String corporateNameText=request.getParameter("corporateNameText");
		 OutputStream  sos = response.getOutputStream();
		 SimpleDateFormat format=new SimpleDateFormat("-yyyy-MM-dd-HH-mm-sss");
		 String fileID=format.format(new Date());
		 response.setContentType("application/txt");
	     response.setHeader("Content-Disposition", "attachment;filename="+reportID+fileID+".xls");
	      
		 String jrxmlPath	="";
		 if("BroClaimUtilizationRep".equals(reportID)){
			 jrxmlPath="reports/broLogin/ClaimUtilizationRep.jrxml";
		 }else if("BroAuthUtilizationRep".equals(reportID)){
			 jrxmlPath="reports/broLogin/AuthUtilizationRep.jrxml";
		 }else if("BroTATForClaims".equals(reportID)){
			 jrxmlPath="reports/broLogin/TATForClaims.jrxml";
		 }else if("BroTATForPreAuth".equals(reportID)){
			 jrxmlPath="reports/broLogin/PreAuthTAT.jrxml";
		 }else if("BroTechnicalResultReport".equals(reportID)){
			 jrxmlPath="reports/broLogin/TechnicalResultReport.jrxml";
		 }else if("BroTATFSCORPOARTE".equals(reportID)){
			 jrxmlPath="reports/broLogin/EnrolmentTAT.jrxml";
		 }else if("BroTATFSINDIVIDUAL".equals(reportID)){
			 jrxmlPath="reports/broLogin/TATForIndividual.jrxml";
		 }else if("BroHIPA".equals(reportID)){
			 jrxmlPath="reports/broLogin/HIPA.jrxml";
		 }
		 JasperReport jasperReport,emptyReport;
		 JasperPrint jasperPrint;
		 TTKReportDataSource ttkReportDataSource = null;
		 ArrayList<String> alInputParams	=	new ArrayList<>(); 
		 alInputParams.add(0, (String) frmInsReports.get("fromDate"));
		 alInputParams.add(1, (String) frmInsReports.get("toDate"));
		 alInputParams.add(2, (String) (String)request.getSession().getAttribute("brokerCode"));
		 alInputParams.add(3, (String) frmInsReports.get("corporateName"));
		 alInputParams.add(4, (String) frmInsReports.get("memberID"));
		 alInputParams.add(5, (String) frmInsReports.get("policyNumber"));
		 alInputParams.add(6, (String) frmInsReports.get("policyPeriod"));
		 alInputParams.add(7, (String) frmInsReports.get("IBANNumber"));
		 
		 ttkReportDataSource = new TTKReportDataSource(reportID,alInputParams);

		 jasperReport = JasperCompileManager.compileReport(jrxmlPath);
		 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
		if("BroClaimUtilizationRep".equals(reportID))
			 emptyReport = JasperCompileManager.compileReport("reports/broLogin/Claim_NoRecords.jrxml");
		 else if("BroAuthUtilizationRep".equals(reportID))
			 emptyReport = JasperCompileManager.compileReport("reports/broLogin/Auth_NoRecords.jrxml");

		 HashMap<String,String> hashMap = new HashMap<String,String>();
		 
		 hashMap.put("fromDate", frmInsReports.getString("fromDate"));
		 hashMap.put("toDate", frmInsReports.getString("toDate"));
		 hashMap.put("corporateNameText", corporateNameText);
		 
		 if(ttkReportDataSource.getResultData().next())
		 {
			 ttkReportDataSource.getResultData().beforeFirst();
			 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);				 
		 }//end of if(ttkReportDataSource.getResultData().next()))
		 else
		 {
			 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
		 }//end of if(ttkReportDataSource.getResultData().next())
		
		     JRXlsExporter jExcelApiExporter = new JRXlsExporter();
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, sos);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			 jExcelApiExporter.exportReport();
		
			 sos.flush(); 
			 sos.close();
	 }//end of try	 
	 catch(Exception exp)
	 {
		 exp.printStackTrace();
		 
		 OutputStream  sos = response.getOutputStream();
		 
		 response.setContentType("application/txt");
	     response.setHeader("Content-Disposition", "attachment;filename=error.xls");
		 JasperReport emptyReport = JasperCompileManager.compileReport("generalreports/ErrorReport.jrxml");
		 JasperPrint jasperPrint = JasperFillManager.fillReport( emptyReport, new HashMap<String,String>(),new JREmptyDataSource());
	
	     JRXlsExporter jExcelApiExporter = new JRXlsExporter();
		 jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
		 jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, sos);
		 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		 jExcelApiExporter.exportReport();
	
		 sos.flush(); 
		 sos.close();
		 return null;
	 }//end of catch(Exception exp)		
	 return null;
 }//end of onGenerateReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    
    
    public ActionForward doViewBroTATForClaims(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.info("Inside the doViewBroTATForClaims method of InsuranceCorporateAction");

              HashMap<String, Object> hashMap=new HashMap<>(); 
			 DynaActionForm frmInsReports = (DynaActionForm)form;
			 ArrayList<String> alInputParams	=	new ArrayList<>(); 
			 alInputParams.add(0, (String) frmInsReports.get("fromDate"));
			 alInputParams.add(1, (String) frmInsReports.get("toDate"));
			 alInputParams.add(2, (String) (String)request.getSession().getAttribute("brokerCode"));
			 alInputParams.add(3, (String) frmInsReports.get("corporateName"));
			 alInputParams.add(4, (String) frmInsReports.get("memberID"));
			 alInputParams.add(5, (String) frmInsReports.get("policyNumber"));
			 alInputParams.add(6, (String) frmInsReports.get("policyPeriod"));
			// alInputParams.add(7, (String) frmInsReports.get("IBANNumber"));

			 OnlineBrokerManager brokerManager = null;
	            brokerManager=this.getBrokerManagerObject();
				 ArrayList<String[]> alClaimTatData	=	brokerManager.getClaimTatData(alInputParams);
			 ArrayList<String> alClaimsTatData	=	new ArrayList<>();
			 String[] clmData	=	null;
			 long[] noOfClaims = new long[alClaimTatData.size()];
			 for(int k=0;k<alClaimTatData.size();k++){
				 clmData	=	alClaimTatData.get(k);
				// for(int l=0;l<clmData.length;l++){
					 alClaimsTatData.add(clmData[0]);
					 
					 
						String dateStart = clmData[1]+"/"+clmData[2]+"/"+clmData[3];//claim recieved;
						String dateStop	 = clmData[4]+"/"+clmData[5]+"/"+clmData[6];//claim Approved;
						/*String dateStart = "01/14/2012 09:29:58";//claim recieved;
						String dateStop	 = "01/17/2012 10:31:48";//claim Approved;
						 */
						
						//HH converts hour in 24 hours format (0-23), day calculation
						SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

						Date d1 = null;
						Date d2 = null;
						
						try {
							d1 = format.parse(dateStop);
							d2 = format.parse(dateStart);

							//in milliseconds
							long diff = d2.getTime() - d1.getTime();

							/*long diffSeconds = diff / 1000 % 60;
							long diffMinutes = diff / (60 * 1000) % 60;
							long diffHours = diff / (60 * 60 * 1000) % 24;*/
							long diffDays = diff / (24 * 60 * 60 * 1000);
							noOfClaims[k]	=	diffDays;

						} catch (Exception e) {
							e.printStackTrace();
						}				 
			 }
			 

             int zeroTo5        =    0;
            int fiveTo15    =    0;
            int above15        =    0;
           
            int NzeroTo5    =    0;
            int NfiveTo15    =    0;
            int Nabove15    =    0;
           
            for(int k=0;k<noOfClaims.length;k++){
                 clmData    =    (String[])alClaimTatData.get(k);
                 if(clmData[0].equals("MEMBER"))
                 {
                     if(noOfClaims[k]<6)
                            zeroTo5++;
                     if(noOfClaims[k]>5 && noOfClaims[k]<=15)
                            fiveTo15++;
                     if(noOfClaims[k]>15)
                            above15++;
                 }else if(clmData[0].equals("NETWORK")){
                     if(noOfClaims[k]<16)
                         NzeroTo5++;
                     if(noOfClaims[k]>15 && noOfClaims[k]<=30)
                         fiveTo15++;
                     if(noOfClaims[k]>30)
                         Nabove15++;
                 }
            }
            hashMap.put("ZEROTO5", zeroTo5);
            hashMap.put("FIVETO15", fiveTo15);
            hashMap.put("ABOVE15", above15);
            hashMap.put("NZEROTO5", NzeroTo5);
            hashMap.put("NFIVETO15", NfiveTo15);
            hashMap.put("NABOVE15", Nabove15);            
      	 
			 
			 
		
       		 String reportID=frmInsReports.getString("reportID");	
       		 String corporateNameText=request.getParameter("corporateNameText");
       		 OutputStream  sos = response.getOutputStream();
       		 SimpleDateFormat format=new SimpleDateFormat("-yyyy-MM-dd-HH-mm-sss");
       		 String fileID=format.format(new Date());
       		 response.setContentType("application/txt");
       	     response.setHeader("Content-Disposition", "attachment;filename="+reportID+fileID+".xls");
       	      
       		 String jrxmlPath	="reports/broLogin/TATForClaims.jrxml";

       		 JasperReport jasperReport,emptyReport;
       		 JasperPrint jasperPrint;

       		 jasperReport = JasperCompileManager.compileReport(jrxmlPath);
       		 emptyReport = JasperCompileManager.compileReport("reports/broLogin/TATForClaims_NoRecords.jrxml");
       		
       		 hashMap.put("fromDate", frmInsReports.getString("fromDate"));
       		 hashMap.put("toDate", frmInsReports.getString("toDate"));
       		 hashMap.put("corporateNameText", corporateNameText);
       		 
       			 
       		if(zeroTo5==0 && fiveTo15==0 && above15==0 && NzeroTo5==0 && NfiveTo15==0 && Nabove15==0)
       			 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
       		else
       			jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new JREmptyDataSource());					 
       		
       		     JRXlsExporter jExcelApiExporter = new JRXlsExporter();
       			 jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
       			 jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, sos);
       			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
       			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
       			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
       			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
       			 jExcelApiExporter.exportReport();
       		
       			 sos.flush(); 
       			 sos.close();
       	return null;
       	 }//end of try	 
       	 catch(Exception exp)
       	 {
       		 exp.printStackTrace();
       		 
       		 OutputStream  sos = response.getOutputStream();
       		 
       		 response.setContentType("application/txt");
       	     response.setHeader("Content-Disposition", "attachment;filename=error.xls");
       		 JasperReport emptyReport = JasperCompileManager.compileReport("generalreports/ErrorReport.jrxml");
       		 JasperPrint jasperPrint = JasperFillManager.fillReport( emptyReport, new HashMap<String,String>(),new JREmptyDataSource());
       	
       	     JRXlsExporter jExcelApiExporter = new JRXlsExporter();
       		 jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
       		 jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, sos);
       		 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
       		 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
       		 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
       		 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
       		 jExcelApiExporter.exportReport();
       	
       		 sos.flush(); 
       		 sos.close();
       		 return null;
       		 
       	 }//end of catch(Exception exp)				
	}//end of doViewBroTATForClaims(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	public ActionForward doViewBroTATForPreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doViewBroTATForPreAuth method of InsuranceCorporateAction");

			 HashMap<String, Object> hashMap=new HashMap<>(); 
			 DynaActionForm frmInsReports = (DynaActionForm)form;
			 ArrayList<String> alInputParams	=	new ArrayList<>(); 
			 alInputParams.add(0, (String) frmInsReports.get("fromDate"));
			 alInputParams.add(1, (String) frmInsReports.get("toDate"));
			 alInputParams.add(2, (String) (String)request.getSession().getAttribute("brokerCode"));
			 alInputParams.add(3, (String) frmInsReports.get("corporateName"));
			 alInputParams.add(4, (String) frmInsReports.get("memberID"));
			 alInputParams.add(5, (String) frmInsReports.get("policyNumber"));
			 alInputParams.add(6, (String) frmInsReports.get("policyPeriod"));
			 alInputParams.add(7, (String) frmInsReports.get("IBANNumber"));


			 OnlineBrokerManager brokerManager = null;
	            brokerManager=this.getBrokerManagerObject();
	            ArrayList<String[]> alPreAuthTatData	=	brokerManager.getPreAuthTatData(alInputParams);
			 ArrayList<String> alClaimsTatData	=	new ArrayList<>();
			 String[] clmData	=	null;
			 long[] noOfPreAuths = new long[alPreAuthTatData.size()];
			 for(int k=0;k<alPreAuthTatData.size();k++){
				 clmData	=	alPreAuthTatData.get(k);
				// for(int l=0;l<clmData.length;l++){
					 alClaimsTatData.add(clmData[0]);
					 
					 
						String dateStart = clmData[1]+"/"+clmData[2]+"/"+clmData[3];//claim recieved;
						String dateStop	 = clmData[4]+"/"+clmData[5]+"/"+clmData[6];//claim Approved;
						/*String dateStart = "01/14/2012 09:29:58";//claim recieved;
						String dateStop	 = "01/17/2012 10:31:48";//claim Approved;
						 */
						
						//HH converts hour in 24 hours format (0-23), day calculation
						SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

						Date d1 = null;
						Date d2 = null;
						
						try {
							d1 = format.parse(dateStop);
							d2 = format.parse(dateStart);

							//in milliseconds
							long diff = d2.getTime() - d1.getTime();
							long diffMinutes = diff / (60 * 1000) ;
							noOfPreAuths[k]	=	diffMinutes;

						} catch (Exception e) {
							e.printStackTrace();
						}				 
			 }
			 
			 int zeroTo5		=	0;
				int fiveTo15	=	0;
				int above15		=	0;
				
				int NzeroTo5		=	0;
				int NfiveTo15	=	0;
				int Nabove15		=	0;
				for(int k=0;k<noOfPreAuths.length;k++){
					 clmData	=	(String[])alPreAuthTatData.get(k);
					 if(clmData[0].equals("OUT-PATIENT"))
					 {
						 if(noOfPreAuths[k]<31)
								zeroTo5++;
						 if(noOfPreAuths[k]>30 && noOfPreAuths[k]<=60)
								fiveTo15++;
						 if(noOfPreAuths[k]>60)
								above15++;
					 }else if(clmData[0].equals("IN-PATIENT")){
						 if(noOfPreAuths[k]<=180)
							 NzeroTo5++;
						 if(noOfPreAuths[k]>180)
							 fiveTo15++;
					 }
					
				}
				
				 hashMap.put("ZEROTO5", zeroTo5);
		            hashMap.put("FIVETO15", fiveTo15);
		            hashMap.put("ABOVE15", above15);
		            hashMap.put("NZEROTO5", NzeroTo5);
		            hashMap.put("NFIVETO15", NfiveTo15);
		            hashMap.put("NABOVE15", Nabove15);            
		      	 
					 
					 
				
		       		 String reportID=frmInsReports.getString("reportID");	
		       		 String corporateNameText=request.getParameter("corporateNameText");
		       		 OutputStream  sos = response.getOutputStream();
		       		 SimpleDateFormat format=new SimpleDateFormat("-yyyy-MM-dd-HH-mm-sss");
		       		 String fileID=format.format(new Date());
		       		 response.setContentType("application/txt");
		       	     response.setHeader("Content-Disposition", "attachment;filename="+reportID+fileID+".xls");
		       	      
		       		 String jrxmlPath	="reports/broLogin/TATForPreauth.jrxml";

		       		 JasperReport jasperReport,emptyReport;

		       		 JasperPrint jasperPrint;
		       		
		       		 

		       		 jasperReport = JasperCompileManager.compileReport(jrxmlPath);
		       		 emptyReport = JasperCompileManager.compileReport("reports/broLogin/TATForPreauth_NoRecords.jrxml");

		       		
		       		 hashMap.put("fromDate", frmInsReports.getString("fromDate"));
		       		 hashMap.put("toDate", frmInsReports.getString("toDate"));
		       		 hashMap.put("corporateNameText", corporateNameText);
		       		 
		       			 
		       if(zeroTo5==0 && fiveTo15==0 && above15==0 && NzeroTo5==0 && NfiveTo15==0 && Nabove15==0)
       			 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
       		else
       			jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new JREmptyDataSource());					 
		       		
		       		     JRXlsExporter jExcelApiExporter = new JRXlsExporter();
		       			 jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
		       			 jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, sos);
		       			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		       			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		       			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		       			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		       			 jExcelApiExporter.exportReport();
		       		
		       			 sos.flush(); 
		       			 sos.close();
		       	return null;
		       	 }//end of try	 
		       	 catch(Exception exp)
		       	 {
		       		 exp.printStackTrace();
		       		 
		       		 OutputStream  sos = response.getOutputStream();
		       		 
		       		 response.setContentType("application/txt");
		       	     response.setHeader("Content-Disposition", "attachment;filename=error.xls");
		       		 JasperReport emptyReport = JasperCompileManager.compileReport("generalreports/ErrorReport.jrxml");
		       		 JasperPrint jasperPrint = JasperFillManager.fillReport( emptyReport, new HashMap<String,String>(),new JREmptyDataSource());
		       	
		       	     JRXlsExporter jExcelApiExporter = new JRXlsExporter();
		       		 jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
		       		 jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, sos);
		       		 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		       		 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		       		 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		       		 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		       		 jExcelApiExporter.exportReport();
		       	
		       		 sos.flush(); 
		       		 sos.close();
		       		 return null;
		       		 
		}//end of catch(Exception exp)				
	}//end of doViewBroTATForPreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	
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
    public ActionForward goBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the goBack method of BrokerReportsAction");
            setOnlineLinks(request);            
            DynaActionForm frmBroDashBoard =(DynaActionForm)form;
            frmBroDashBoard.initialize(mapping);     //reset the form data
            String strFarward="Broker.Home.DashBoard";
            return mapping.findForward(strFarward);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processBrokerExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processBrokerExceptions(request, mapping, new TTKException(exp,strReportSearch));
        }//end of catch(Exception exp)
    }//end of goBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
    /**
	 * Returns the BrokerManager session object for invoking methods on it.
	 * @return BrokerManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private OnlineBrokerManager getBrokerManagerObject() throws TTKException
	{
		OnlineBrokerManager brokerManager = null;
		try
		{
			if(brokerManager == null)
			{
				InitialContext ctx = new InitialContext();
				brokerManager = (OnlineBrokerManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineBrokerManagerBean!com.ttk.business.broker.OnlineBrokerManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strReportSearch);
		}//end of catch
		return brokerManager;
	}//end getBrokerManagerObject()
}//end of BrokerReportsAction.java
