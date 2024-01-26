/**
 * @ (#) PreAuthReferralAction.java May 10, 2006
 * Project       : TTK HealthCare Services
 * File          : PreAuthReferralAction.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : May 10, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.preauth;


import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.preauth.PreAuthReferralManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.empanelment.HospitalVO;
import com.ttk.dto.preauth.PreAuthReferralVO;

import formdef.plugin.util.FormUtils;


/**
 * This class is reused for adding pre-auth/claims in pre-auth and claims flow.
 */
public class PreAuthReferralAction extends TTKAction {

	private static Logger log = Logger.getLogger(PreAuthReferralAction.class);

	private static final String strSearch = "referralSearch";
	private static final String strReferralDetails = "referralDetails";
	private static final String strAddReferral = "addReferral";
	private static final String strReportdisplay="reportdisplay";

	 //   Modes.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";
	/*
	 * (non-Javadoc)
	 * @see com.ttk.action.TTKAction#doDefault(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public ActionForward doDefault(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			setLinks(request);
			log.debug("Inside PreAuthReferralAction doView ");
			DynaActionForm frmPreAuthReferral = (DynaActionForm) form;
			frmPreAuthReferral.initialize(mapping);
			
			TableData tableData = new TableData();
    		//create the required grid table
    		tableData.createTableInfo("ReferralSearchTable",new ArrayList());
    		request.getSession().setAttribute("tableData",tableData);
    		
			return this.getForward(strSearch, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strReferralDetails));
		}// end of catch(Exception exp)
	}// end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	
	/*
	 * Search Referrals
	 */
	 public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
	    	try{
	    		setLinks(request);
	    		log.debug("Inside the doSearch method of PreAuthReferralAction");
	    		PreAuthReferralManager preAuthReferralManagerObject=this.getPreAuthReferralManagerObject();
	    		TableData tableData = TTKCommon.getTableData(request);
	    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
	    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
	    		if(!strPageID.equals("") || !strSortID.equals(""))
	    		{
	    			if(!strPageID.equals(""))
	    			{
	    				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
	    				return (mapping.findForward(strSearch));
	    			}///end of if(!strPageID.equals(""))
	    			else
	    			{
	    				tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
	    				tableData.modifySearchData("sort");//modify the search data
	    			}//end of else
	    		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
	    		else
	    		{
	    			// create the required grid table
	    			tableData.createTableInfo("ReferralSearchTable",null);
	    			//fetch the data from the data access layer and set the data to table object
	    			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form));
	    			tableData.modifySearchData("search");
	    		}//end of else
	    		ArrayList alReferralList=preAuthReferralManagerObject.getReferralList(tableData.getSearchData());
	    		tableData.setData(alReferralList, "search");
	    		request.getSession().setAttribute("tableData",tableData);
	    		//finally return to the grid screen
	    		return this.getForward(strSearch, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strReferralDetails));
			}//end of catch(Exception exp)
	    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	 
	 public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
	    	try{
	    		setLinks(request);
	    		log.debug("Inside the doBackward method of PreAuthReferralAction");
				TableData tableData = TTKCommon.getTableData(request);
				PreAuthReferralManager preAuthReferralManagerObject=this.getPreAuthReferralManagerObject();
	    		tableData.modifySearchData(strBackward);//modify the search data
	    		//fetch the data from the data access layer and set the data to table object
	    		ArrayList alHospital = preAuthReferralManagerObject.getReferralList(tableData.getSearchData());
	    		tableData.setData(alHospital, strBackward);//set the table data
	    		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
	    		TTKCommon.documentViewer(request);
	    		return this.getForward(strSearch, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strReferralDetails));
			}//end of catch(Exception exp)
	    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	    /**
	     * This method is used to get the next set of records with the given search criteria.
	     * Finally it forwards to the appropriate view based on the specified forward mappings
	     *
	     * @param mapping The ActionMapping used to select this instance
	     * @param form The optional ActionForm bean for this request (if any)
	     * @param request The HTTP request we are processing
	     * @param response The HTTP response we are creating
	     * @return ActionForward Where the control will be forwarded, after this request is processed
	     * @throws Exception if any error occurs
	     */
	    public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
	    	try{
	    		setLinks(request);
	    		log.debug("Inside the doForward method of PreAuthReferralAction");
				TableData tableData = TTKCommon.getTableData(request);
				PreAuthReferralManager preAuthReferralManagerObject=this.getPreAuthReferralManagerObject();
	    		tableData.modifySearchData(strForward);//modify the search data
	    		//fetch the data from the data access layer and set the data to table object
	    		ArrayList alHospital = preAuthReferralManagerObject.getReferralList(tableData.getSearchData());
	    		tableData.setData(alHospital, strForward);//set the table data
	    		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
	    		TTKCommon.documentViewer(request);
	    		return this.getForward(strSearch, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strReferralDetails));
			}//end of catch(Exception exp)
	    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	    
	    
	 /*
	  * View Referral Data
	  */
	 public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
	    	try{
	    		setLinks(request);
	    		log.debug("Inside the doView method of PreAuthReferralAction");
	    		String strCaption	=	"";
				PreAuthReferralVO referralVO=null;
				PreAuthReferralManager preAuthReferralManagerObject=this.getPreAuthReferralManagerObject();
				TableData tableData = TTKCommon.getTableData(request);
	            if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
	            	((DynaActionForm)form).initialize(mapping);//reset the form data
	            }//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))

	            DynaActionForm frmPreAuthReferral = (DynaActionForm)form;
	            if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	            {
	            	referralVO = (PreAuthReferralVO)tableData.getRowInfo(Integer.parseInt((String)(frmPreAuthReferral).get("rownum")));
	            	 this.addToWebBoard(referralVO, request);
	            	referralVO = preAuthReferralManagerObject.getReferralDetails(referralVO.getReferralSeqId());
	            	 String ltrSntOrNt	=	referralVO.getLetterYN();
					 request.setAttribute("letterSent", ltrSntOrNt); 
					 request.setAttribute("letterGenYN", referralVO.getLetterGenYN()); 
	            	request.getSession().setAttribute("lRefSeqId",referralVO.getReferralSeqId());
	            	frmPreAuthReferral.initialize(mapping);
	                strCaption="Edit";
	                //Add the request object to DocumentViewer
	                TTKCommon.documentViewer(request);
	            }//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	            else if(TTKCommon.getWebBoardId(request) != null)//take the hospital_seq_id from web board
	            {
	                //if web board id is found, set it as current web board id
	            	referralVO = new PreAuthReferralVO();
	            	referralVO.setReferralSeqId(TTKCommon.getWebBoardId(request));
	                //calling business layer to get the hospital detail
	            	referralVO = preAuthReferralManagerObject.getReferralDetails(referralVO.getReferralSeqId());
	            	request.getSession().setAttribute("lRefSeqId",referralVO.getReferralSeqId());
	            	request.setAttribute("letterSent", referralVO.getLetterYN()); 
					 request.setAttribute("letterGenYN", referralVO.getLetterGenYN()); 
					 request.getSession().setAttribute("lRefSeqId",referralVO.getReferralSeqId());
	                strCaption="Edit";
	                //Add the request object to DocumentViewer
	                TTKCommon.documentViewer(request);
	            }//end of else if(TTKCommon.getWebBoardId(request) != null)
	            else
	            {
	            	
	                TTKException expTTK = new TTKException();
	                expTTK.setMessage("error.ReferralLetter.required");
	                throw expTTK;
	            	
	            }//end of if(strMode.equals("EditHospital"))
	            
	    		
	            DynaActionForm frmPreAuthReferralEdit = (DynaActionForm)FormUtils.setFormValues("frmPreAuthReferralEdit",
	            		referralVO,this,mapping,request);
	            frmPreAuthReferralEdit.set("caption",strCaption);

	            request.getSession().setAttribute("frmPreAuthReferralEdit",frmPreAuthReferralEdit);
	            return this.getForward(strAddReferral, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strReferralDetails));
			}//end of catch(Exception exp)
	    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
	/*
	 * Adding New Referrals
	 */
	
	public ActionForward doAdd(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			setLinks(request);
			log.info("Inside PreAuthReferralAction doAdd ");
			
			 DynaActionForm frmPreAuthReferralEdit = (DynaActionForm)FormUtils.setFormValues("frmPreAuthReferralEdit",
					 new PreAuthReferralVO(),this,mapping,request);
			 frmPreAuthReferralEdit.set("caption","Add");
				//frmPreAuthReferralEdit.initialize(mapping);
			 request.getSession().setAttribute("frmPreAuthReferralEdit",frmPreAuthReferralEdit);
			return this.getForward(strAddReferral, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strReferralDetails));
		}// end of catch(Exception exp)
	}// end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)
	
	
	
	public ActionForward getProviderDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside PreAuthReferralAction getProviderDetails ");
			String licenseId	=	request.getParameter("licenseId");
			if(licenseId.indexOf('[')!=-1)
				licenseId	=	licenseId.substring(licenseId.indexOf('[')+1,licenseId.indexOf(']'));
			PreAuthReferralManager preAuthReferralManagerObject=this.getPreAuthReferralManagerObject();
			String[] arrProviderDetails 	=	preAuthReferralManagerObject.getProviderDetails(licenseId);
			
			/*frmPreAuthReferralEdit.set("providerName", arrProviderDetails[0]);
			frmPreAuthReferralEdit.set("address", arrProviderDetails[1]);
			frmPreAuthReferralEdit.set("contactNo", arrProviderDetails[2]);
			frmPreAuthReferralEdit.set("emailId", arrProviderDetails[3]);
			frmPreAuthReferralEdit.set("providerId", arrProviderDetails[4]);
			request.getSession().setAttribute("frmPreAuthReferralEdit", frmPreAuthReferralEdit);
			return this.getForward(strAddReferral, mapping, request);*/
			

    		PrintWriter out = response.getWriter();  
	        response.setContentType("text/xml");  
	        response.setHeader("Cache-Control", "no-cache");  
	        response.setStatus(HttpServletResponse.SC_OK);  
	        if(arrProviderDetails!=null)
	        		out.write(arrProviderDetails[0]+"@@"+arrProviderDetails[1]+"@@"+arrProviderDetails[2]+"@@"+arrProviderDetails[3]+"@@"+arrProviderDetails[4]);  
	        out.flush();  
    		return null;
    	
    		
    		
			
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strReferralDetails));
		}// end of catch(Exception exp)
	}// end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	
	
	public ActionForward getMemberDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
        	log.debug("Inside the getLicenceNumbers method of HospitalSearchAction");
    		setLinks(request);
    		String[]  strMemName	=	null;
    		PreAuthReferralManager preAuthReferralManagerObject=this.getPreAuthReferralManagerObject();
    		String memberId	=	request.getParameter("memberId");;
    		
    		strMemName= preAuthReferralManagerObject.getMemberDetails(memberId);
        		PrintWriter out = response.getWriter();  
    	        response.setContentType("text/xml");  
    	        response.setHeader("Cache-Control", "no-cache");  
    	        response.setStatus(HttpServletResponse.SC_OK);  
    	        if(strMemName!=null)
    	        		out.write(strMemName[0]+","+strMemName[1]);  
    	        out.flush();  
    		return null;
    	}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strReferralDetails));
		}// end of catch(Exception exp)
	}// end of getMemberDetails(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)
	
	
	
	
	public ActionForward doReferralGeneralSave(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			setLinks(request);
			log.info("Inside PreAuthReferralAction doReferralGeneralSave ");
			
			 DynaActionForm frmPreAuthReferralEdit = (DynaActionForm)form;
			 
			 PreAuthReferralVO preAuthReferralVO = (PreAuthReferralVO)FormUtils.getFormValues(frmPreAuthReferralEdit,"frmPreAuthReferralEdit",
     				this,mapping,request);
			 preAuthReferralVO.setAddedBy(TTKCommon.getUserSeqId(request));
			 PreAuthReferralManager preAuthReferralManagerObject=this.getPreAuthReferralManagerObject();
			 Long lRefSeqId  	=	preAuthReferralManagerObject.saveReferralDetails(preAuthReferralVO);
			 if(lRefSeqId!=0){
				 preAuthReferralVO	=	preAuthReferralManagerObject.getReferralDetails(lRefSeqId);
				 this.addToWebBoard(preAuthReferralVO, request);
				 request.setAttribute("updated","message.savedSuccessfully");
				 request.setAttribute("letterSent", preAuthReferralVO.getLetterYN()); 
				 request.setAttribute("letterGenYN", preAuthReferralVO.getLetterGenYN()); 
				 request.getSession().setAttribute("lRefSeqId",lRefSeqId);
			 }
			 frmPreAuthReferralEdit = (DynaActionForm)FormUtils.setFormValues("frmPreAuthReferralEdit",
					 preAuthReferralVO,this,mapping,request);
			 frmPreAuthReferralEdit.set("caption","Edit");
			 request.getSession().setAttribute("frmPreAuthReferralEdit",frmPreAuthReferralEdit);
			return this.getForward(strAddReferral, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strReferralDetails));
		}// end of catch(Exception exp)
	}// end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)
	
	
	
	/*
	 * Generate Referral Report
	 */
	public ActionForward doGenerateReferralUpadteStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	 try{
		 log.info("Inside the doGenerateReferralUpadteStatus method of ReportsAction");
		 setLinks(request);
		 DynaActionForm frmPreAuthReferralEdit = (DynaActionForm)form;
		 String referralSeqId	=	(String) frmPreAuthReferralEdit.get("referralSeqId");
		 String referralId	=	(String) frmPreAuthReferralEdit.get("referralId");
		 JasperReport jasperReport,emptyReport;
		 JasperPrint jasperPrint;
		 TTKReportDataSource ttkReportDataSource = null;
		 //String fileWithDateTime=	"PreAuthReferral-"+new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date());
		 String destFileName	=	TTKPropertiesReader.getPropertyValue("preAuthReferralDir")+referralId+".pdf";
		 ttkReportDataSource = new TTKReportDataSource("preAuthReferral",referralSeqId,referralId,""+TTKCommon.getUserSeqId(request));

		 jasperReport = JasperCompileManager.compileReport("reports/preauth/PreAuth_Referral_Letter.jrxml");
		 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
		 HashMap hashMap = new HashMap();
		 ByteArrayOutputStream boas=new ByteArrayOutputStream();

		 request.setAttribute("reportType","EXL");
		 if(ttkReportDataSource.getResultData().next())
		 {
			 ttkReportDataSource.getResultData().beforeFirst();
			 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);				 
		 }//end of if(ttkReportDataSource.getResultData().next()))
		 else
		 {
			 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
		 }//end of if(ttkReportDataSource.getResultData().next())
		 
		/* JasperExportManager.exportReportToPdfStream(jasperPrint,boas);	 
		 JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);*/
		 
		 request.getSession().setAttribute("jasperPrint", jasperPrint);
		 request.getSession().setAttribute("boas", boas);
		 request.getSession().setAttribute("destFileName", destFileName);
		 
		 //getting all data again S T A R T
		 PreAuthReferralManager preAuthReferralManagerObject=this.getPreAuthReferralManagerObject();
		 PreAuthReferralVO referralVO = preAuthReferralManagerObject.getReferralDetails((Long) request.getSession().getAttribute("lRefSeqId"));
		 request.setAttribute("letterSent", referralVO.getLetterYN()); 
		 request.setAttribute("letterGenYN", referralVO.getLetterGenYN()); 
		 request.setAttribute("letterGenYNFlag", "Y"); 
		 frmPreAuthReferralEdit = (DynaActionForm)FormUtils.setFormValues("frmPreAuthReferralEdit",
         		referralVO,this,mapping,request);
		 frmPreAuthReferralEdit.set("caption","Edit");
         request.getSession().setAttribute("frmPreAuthReferralEdit",frmPreAuthReferralEdit);
         //getting all data again C L O S E D
			 
         return this.getForward(strAddReferral, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strReferralDetails));
		}// end of catch(Exception exp)
	}//end of doSendPreAuthReferral(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	/*
	 * Generate Referral doGenerateReferralUpadteStatus
	 */
	public ActionForward doGenerateReferral(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	 try{
		 log.info("Inside the doGenerateReferral method of ReportsAction");
		 
		 JasperPrint jasperPrint	=	(JasperPrint) request.getSession().getAttribute("jasperPrint");
		 ByteArrayOutputStream boas	=	(ByteArrayOutputStream) request.getSession().getAttribute("boas");
		 String destFileName		=	(String) request.getSession().getAttribute("destFileName");
		 
		 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);	 
		 JasperExportManager.exportReportToPdfFile(jasperPrint, destFileName);
		 
		 
		 request.setAttribute("boas",boas);
		 if(!(jasperPrint.equals("null")))
 		{
 			request.setAttribute("GenerateXL","message.GenerateXLSuccessfully");
 			log.info("report generated sucessfully ");
 		}//end of if(!(reportDataSource.equals("null")))
	 }//end of try
		 catch(Exception exp)
		 {
			 return this.processExceptions(request, mapping, new TTKException(exp,strReferralDetails));
		 }//end of catch(Exception exp)		
		 return (mapping.findForward(strReportdisplay));
	 }//end of doGenerateReferral(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	public ActionForward doSendPreAuthReferral(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{

		try {
			setLinks(request);
			log.info("Inside PreAuthReferralAction doSendPreAuthReferral ");
			
			Long lRefSeqId	=	(Long) request.getSession().getAttribute("lRefSeqId");
			 PreAuthReferralManager preAuthReferralManagerObject=this.getPreAuthReferralManagerObject();
			 Long lResult  	=	preAuthReferralManagerObject.mailSend(lRefSeqId,TTKCommon.getUserSeqId(request));
			 String ltrSntOrNt[]	=	preAuthReferralManagerObject.getmailSentStatus(lRefSeqId);
			 if(lResult!=0){
				 request.setAttribute("mailSent","message.mailSentSuccessfully");
				 request.setAttribute("letterSent", ltrSntOrNt[0]);
			 }
			 DynaActionForm frmPreAuthReferralEdit = (DynaActionForm) request.getSession().getAttribute("frmPreAuthReferralEdit");
			 frmPreAuthReferralEdit.set("referredDate", ltrSntOrNt[01]);
			return this.getForward(strAddReferral, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strReferralDetails));
		}// end of catch(Exception exp)
	}//end of doSendPreAuthReferral(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	
	/*
	 * Copy To WEBBOARD
	 */
	
	public ActionForward doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside the doStatusChange method of HospitalSearchAction");
    		this.populateWebBoard(request);
            return this.getForward(strSearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReferralDetails));
		}//end of catch(Exception exp)
    }//end of doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/*
	 * Copy To WEBBOARD
	 */
	
	public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	log.debug("Inside doChangeWebBoard method of HospitalSearchAction");
    	//ChangeWebBoard method will call doView() method internally.
    	return doView(mapping,form,request,response);
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	/**
     * Populates the web board in the session with the selected items in the grid
     * @param request HttpServletRequest object which contains information about the selected check boxes
     */
    private void populateWebBoard(HttpServletRequest request)
    {
        String[] strChk = request.getParameterValues("chkopt");
        TableData tableData = (TableData)request.getSession().getAttribute("tableData");
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        ArrayList<Object> alCacheObject = new ArrayList<Object>();
        CacheObject cacheObject = null;
        if(strChk!=null&&strChk.length!=0)
        {
            //loop through to populate delete sequence id's and get the value from session for the matching check box value
            for(int i=0; i<strChk.length;i++)
            {
                cacheObject = new CacheObject();
                cacheObject.setCacheId(""+((PreAuthReferralVO)tableData.getData().
                		get(Integer.parseInt(strChk[i]))).getReferralSeqId());
                cacheObject.setCacheDesc(((PreAuthReferralVO)tableData.getData().
                		get(Integer.parseInt(strChk[i]))).getReferralId());
                alCacheObject.add(cacheObject);
            }//end of for(int i=0; i<strChk.length;i++)
        }//end of if(strChk!=null&&strChk.length!=0)
        if(toolbar != null)
        {
            toolbar.getWebBoard().addToWebBoardList(alCacheObject);
        }//end of if(toolbar != null)
    }//end of populateWebBoard(HttpServletRequest request)
    
    
    /**
     * Adds the selected item to the web board and makes it as the selected item in the web board
     * @param hospitalVO HospitalVO object which contain the information of hospital
     * @param request HttpServletRequest object which contains information about the selected check boxes
     */
    private void addToWebBoard(PreAuthReferralVO referralVO, HttpServletRequest request)
    {
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        CacheObject cacheObject = new CacheObject();
        cacheObject.setCacheId(""+referralVO.getReferralSeqId());
        cacheObject.setCacheDesc(referralVO.getReferralId());
        ArrayList<Object> alCacheObject = new ArrayList<Object>();
        alCacheObject.add(cacheObject);
        //if the object(s) are added to the web board, set the current web board id
        toolbar.getWebBoard().addToWebBoardList(alCacheObject);
        toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
        //set weboardinvoked as true to avoid change of webboard id twice in same request
        request.setAttribute("webboardinvoked","true");
    }//end of addToWebBoard(HospitalVO hospitalVO, HttpServletRequest request)
	//CommunicationManager commManagerObject=this.getCommunicationManagerObject();
	/*
	 * Populate Search Parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmPreAuthReferral)
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthReferral.getString("referralID")));
	 	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthReferral.getString("vidalMemberID")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthReferral.getString("globalNetID")));
    	alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthReferral.getString("patOrMemName")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthReferral.getString("memCompName")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthReferral.getString("sPolicyNumber")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthReferral.getString("vidalBranch")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthReferral.getString("status")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthReferral.getString("providerName")));   
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthReferral.getString("payerName")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthReferral.getString("ltrFromDt")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthReferral.getString("ltrToDt")));   
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmSearchHospital)
	/**
	 * Returns the PreAuthManager session object for invoking methods on it.
	 * @return PreAuthManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthReferralManager getPreAuthReferralManagerObject() throws TTKException
	{
		PreAuthReferralManager preAuthReferralManager = null;
		try
		{
			if(preAuthReferralManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthReferralManager = (PreAuthReferralManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthReferralManagerBean!com.ttk.business.preauth.PreAuthReferralManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strSearch);
		}//end of catch
		return preAuthReferralManager;
	}//end getPreAuthManagerObject()
}// end of PreAuthReferralAction
