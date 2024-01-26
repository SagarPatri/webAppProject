
/**
 * @ (#) PreAuthGeneralAction.java May 10, 2006
 * Project       : TTK HealthCare Services
 * File          : PreAuthGeneralAction.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : May 10, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.claims;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;
import org.dom4j.Namespace;
import org.dom4j.Node;
import org.dom4j.Visitor;
import org.dom4j.io.SAXReader;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.claims.CeedSoapSaajClient;
import com.ttk.business.claims.ClaimManager;
import com.ttk.business.claims.NamesapceChangingVisitor;
import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.administration.EventVO;
import com.ttk.dto.administration.WorkflowVO;
import com.ttk.dto.claims.ClaimDetailVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.preauth.ActivityDetailsVO;
import com.ttk.dto.preauth.AdditionalDetailVO;
import com.ttk.dto.preauth.CeedResponseVo;
import com.ttk.dto.preauth.ClaimantVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
import com.ttk.dto.preauth.ObservationDetailsVO;
import com.ttk.dto.preauth.PreAuthDetailVO;
import com.ttk.dto.preauth.PreAuthHospitalVO;
import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.preauth.ShortfallVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;
/**
 * This class is reused for adding pre-auth/claims in pre-auth and claims flow.
 */

public class ClaimGeneralAction extends TTKAction {
	
	private static Logger log = Logger.getLogger(ClaimGeneralAction.class);

	private static final String strClaimDetail = "ClaimDetails";
	private static final String strClaimdetails = "claimdetails";
	//	declare forward paths

	private static final String strPre_Authorization = "Pre-Authorization";
	private static final String strClaims = "Claims";

	private static final String strActivitydetails = "activitydetails";
	private static final String strActivityDetails = "ActivityDetails";
	private static final String strAuthorizationError = "authorizations";
	private static final String strClaimViewHistory = "claimViewHistory";
	private static final String strCLaimHistoryList = "claimHistoryList";
	private static final String strPreAuthViewHistory = "preauthViewHistory";
	private static final String strBenefitDetails = "benefitDetails";
	
	/**
	 * This method is used to navigate to detail screen to edit selected record.
	 * Finally it forwards to the appropriate view based on the specified
	 * forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws IOException 
	 * @throws Exception
	 *             if any error occurs
	 */
	
	public ActionForward doView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			
			
			request.getSession().removeAttribute("claimEditNode");
			request.getSession().removeAttribute("CEEDResult");
			request.getSession().removeAttribute("ceedResponse");
			setLinks(request);
			log.debug("Inside ClaimGeneralAction doView ");
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			HttpSession session = request.getSession();
			ClaimManager claimObject = this.getClaimManagerObject();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			
			
			PreAuthDetailVO preAuthDetailVO = null;
			StringBuffer strCaption = new StringBuffer();
			strCaption.append(" Edit");
			String isWebBoard="false";
			if(request.getAttribute("isWebBoard")!=null)
				isWebBoard=(String) request.getAttribute("isWebBoard");
			
			// check if user trying to hit the tab directly with out selecting
			// the pre-auth
			if (ClaimsWebBoardHelper.checkWebBoardId(request) == null) {
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.Claims.required");
				session.setAttribute("claimDiagnosis", null);
				session.setAttribute("claimActivities", null);
				session.setAttribute("claimShortfalls", null);	
				session.setAttribute("ceedResponse", null);	
				session.setAttribute("claimEditNode", null);	
				frmClaimGeneral.initialize(mapping);
				session.setAttribute("frmClaimGeneral", frmClaimGeneral);
				// throw expTTK;
				request.setAttribute("errorMsg", "Please Select Claim Details");
				return this.getForward(strClaimdetails, mapping, request);
			}
			long claimSeqID = ClaimsWebBoardHelper.getClaimsSeqId(request);
			
			//System.out.println("updateActivityTariffPrice123s");
			// updating activity prise
		   // updateActivityTariffPrice(claimSeqID);
			//System.out.println("updateActivityTariffPrice123e");
			
			 
				// call the business layer to get the Claim detail
			preAuthDetailVO = claimObject.getClaimDetails(claimSeqID);
			
		
			
			
		preAuthDetailVO = preAuthDetailVO == null ? new PreAuthDetailVO(): preAuthDetailVO;
				preAuthDetailVO.setClaimSeqID(claimSeqID);

	       
			
			session.setAttribute("ceedResponse", null);	
			session.setAttribute("claimEditNode", null);	
		
			frmClaimGeneral=setFormValues(preAuthDetailVO, mapping, request);
			
			if ("N".equals(frmClaimGeneral.getString("networkProviderType"))) {
				session.setAttribute("providerStates",
						TTKCommon.getStates(frmClaimGeneral
								.getString("providerCountry")));
				session.setAttribute("providerAreas", TTKCommon
						.getAreas(frmClaimGeneral.getString("providerEmirate")));
				  }
			
			session.setAttribute("encounterTypes",
					preAuthObject.getEncounterTypes(frmClaimGeneral
							.getString("benefitType")));

			frmClaimGeneral.set("caption", strCaption.toString());
			
			if("ECL".equals(preAuthDetailVO.getModeOfClaim()) &&((preAuthDetailVO.getCount_flag())>1) ){
				
				
				request.setAttribute("errorMsg","Invalid Member Id: More than one member found for the searched Member id. Please select any one member id from the search results.");
				frmClaimGeneral.set("memberSeqID","");
			//  frmClaimGeneral.set("memberId","");
				frmClaimGeneral.set("patientName", "");
				frmClaimGeneral.set("memberAge","");
			//	frmClaimGeneral.set("emirateId", "");
				frmClaimGeneral.set("payerName", "");
				frmClaimGeneral.set("payerId", "");
				frmClaimGeneral.set("insSeqId","");
				frmClaimGeneral.set("policySeqId", "");
				frmClaimGeneral.set("patientGender", "");
				frmClaimGeneral.set("policyNumber", "");
				frmClaimGeneral.set("corporateName","");
				frmClaimGeneral.set("policyStartDate", "");
				frmClaimGeneral.set("policyEndDate", "");
				frmClaimGeneral.set("nationality", "");
				frmClaimGeneral.set("sumInsured","");
				frmClaimGeneral.set("availableSumInsured","");
				frmClaimGeneral.set("vipYorN","");
				frmClaimGeneral.set("preMemInceptionDt","");
				frmClaimGeneral.set("productName","");
				frmClaimGeneral.set("eligibleNetworks","");
				frmClaimGeneral.set("payerAuthority","");
				//frmClaimGeneral.set("patient_id","");
				
			}
			
			else if("ECL".equals(preAuthDetailVO.getModeOfClaim()) && preAuthDetailVO.getClaimNo()!=null && preAuthDetailVO.getClaimNo()!="" && preAuthDetailVO.getCount_flag()==0)
			{
				
				if(preAuthDetailVO.getCount_flag2()==1)
				{
					
					frmClaimGeneral = (DynaActionForm) FormUtils.setFormValues("frmClaimGeneral", preAuthDetailVO, this, mapping, request);
				}
				
				if(preAuthDetailVO.getCount_flag2()==0)
				{
					
					request.setAttribute("errorMsg", "Invalid Member id / Emirate id: Member Id/Emirate Id not found.");
					frmClaimGeneral.set("memberSeqID","");
				//	frmClaimGeneral.set("memberId","");
					frmClaimGeneral.set("patientName", "");
					frmClaimGeneral.set("memberAge","");
				//	frmClaimGeneral.set("emirateId", "");
					frmClaimGeneral.set("payerName", "");
					frmClaimGeneral.set("payerId", "");
					frmClaimGeneral.set("insSeqId","");
					frmClaimGeneral.set("policySeqId", "");
					frmClaimGeneral.set("patientGender", "");
					frmClaimGeneral.set("policyNumber", "");
					frmClaimGeneral.set("corporateName","");
					frmClaimGeneral.set("policyStartDate", "");
					frmClaimGeneral.set("policyEndDate", "");
					frmClaimGeneral.set("nationality", "");
					frmClaimGeneral.set("sumInsured",""); 
					frmClaimGeneral.set("availableSumInsured","");
					frmClaimGeneral.set("vipYorN","");
					frmClaimGeneral.set("preMemInceptionDt","");
					frmClaimGeneral.set("productName","");
					frmClaimGeneral.set("eligibleNetworks","");
					frmClaimGeneral.set("payerAuthority","");
					
					//frmClaimGeneral.set("patient_id","");
				}
				
				if(preAuthDetailVO.getCount_flag2()>1)
				{
					
					request.setAttribute("errorMsg","Invalid Emirate Id: More than one member found for the searched Emirate Id. Please select any one Emirate id from the search results.");
					frmClaimGeneral.set("memberSeqID","");
					frmClaimGeneral.set("memberId","");
					frmClaimGeneral.set("patientName", "");
					frmClaimGeneral.set("memberAge","");
				//	frmClaimGeneral.set("emirateId", "");
					frmClaimGeneral.set("payerName", "");
					frmClaimGeneral.set("payerId", "");
					frmClaimGeneral.set("insSeqId","");
					frmClaimGeneral.set("policySeqId", "");
					frmClaimGeneral.set("patientGender", "");
					frmClaimGeneral.set("policyNumber", "");
					frmClaimGeneral.set("corporateName","");
					frmClaimGeneral.set("policyStartDate", "");
					frmClaimGeneral.set("policyEndDate", "");
					frmClaimGeneral.set("nationality", "");
					frmClaimGeneral.set("sumInsured","");
					frmClaimGeneral.set("availableSumInsured","");
					frmClaimGeneral.set("vipYorN","");
					frmClaimGeneral.set("preMemInceptionDt","");
					frmClaimGeneral.set("productName","");
					frmClaimGeneral.set("eligibleNetworks","");
					frmClaimGeneral.set("payerAuthority","");
					//frmClaimGeneral.set("patient_id","");
				}
				
			}
			
			else if(preAuthDetailVO.getClaimNo()!=null && preAuthDetailVO.getClaimNo()!="" && preAuthDetailVO.getCount_flag()==0){
				
				request.setAttribute("errorMsg", "Invalid Member ID: Member ID not found.");
				frmClaimGeneral.set("memberSeqID","");
				//frmClaimGeneral.set("memberId","");
				frmClaimGeneral.set("patientName", "");
				frmClaimGeneral.set("memberAge","");
				frmClaimGeneral.set("emirateId", "");
				frmClaimGeneral.set("payerName", "");
				frmClaimGeneral.set("payerId", "");
				frmClaimGeneral.set("insSeqId","");
				frmClaimGeneral.set("policySeqId", "");
				frmClaimGeneral.set("patientGender", "");
				frmClaimGeneral.set("policyNumber", "");
				frmClaimGeneral.set("corporateName","");
				frmClaimGeneral.set("policyStartDate", "");
				frmClaimGeneral.set("policyEndDate", "");
				frmClaimGeneral.set("nationality", "");
				frmClaimGeneral.set("sumInsured",""); 
				frmClaimGeneral.set("availableSumInsured","");
				frmClaimGeneral.set("vipYorN","");
				frmClaimGeneral.set("preMemInceptionDt","");
				frmClaimGeneral.set("productName","");
				frmClaimGeneral.set("eligibleNetworks","");
				frmClaimGeneral.set("payerAuthority","");
				
				//frmClaimGeneral.set("patient_id","");
			}
			
				if(preAuthDetailVO.getMemberWeight()!=null)
				{
					if(memberAgeValidation(frmClaimGeneral))
					frmClaimGeneral.set("memberwtflag","Y");
				}
				
				
				if(preAuthDetailVO.getCount_flag() == 1)
				{	
					frmClaimGeneral.set("disableMemberId","disable");
				}
				if(preAuthDetailVO.getCount_flag() == 0 || preAuthDetailVO.getCount_flag() >1)
				{	
					frmClaimGeneral.set("disableMemberId","enable");
				}
				
				session.setAttribute("frmClaimGeneral", frmClaimGeneral);
					
			Document document=null;
			if(preAuthDetailVO.getClaimNo()!=null)
		  	{
				
			  	SAXReader saxReader=new SAXReader();
			  	String path=TTKPropertiesReader.getPropertyValue("ClaimCeedPath");
			  	File file=new File(path+preAuthDetailVO.getClaimNo()+".xml");
			  	if(file.exists())
			  	{
			  		 document=saxReader.read(file);
			  		saveCeedRules(request,document);
			  	}
		  	}
			
			List<Node> claimEditNode			=	(List<Node>) request.getSession().getAttribute("claimEditNode");
			
            
            String reviewOrAlert	=	"";
            String[] editDetails			=	null;
            String reviewOrAlertTitle		=	"";
           HashMap<String,CeedResponseVo> ceedResData=new HashMap<String, CeedResponseVo>();
           
          
           
           
           
           if(document!=null){
           	  Node envelopeNode	=	document.selectSingleNode(document.getRootElement().getPath());
   	            
 	            Node bodyNode		=	document.selectSingleNode(envelopeNode.getPath()+"/soap:Body");
 	            
 	            Node dhcgegResponseNode		=	document.selectSingleNode(bodyNode.getPath()+"/DHCEGResponse");
 	          
 	            Node dhcegResultNode		=	document.selectSingleNode(dhcgegResponseNode.getPath()+"/DHCEGResult");
 	            
 	          //  Node responseNode			=	doc.selectSingleNode(dhcgegResponseNode.getPath()+"/response");
 	          
        	   //Node dhcegResultNode		=	doc.selectSingleNode("//DHCEGResult");
	            if(dhcegResultNode!=null){
	            	request.getSession().setAttribute("CEEDResult",dhcegResultNode.getText());
	            }

           }

           
           
          

           
            if(document!=null && claimEditNode!=null)
            {
				if(claimEditNode.size()==0)
    				reviewOrAlert	=	"OK";
				else{
						reviewOrAlert		=	"";
						reviewOrAlertTitle	=	"";
				}
            
            
for(Node claimEditNodes : claimEditNode){
        		
	        	List<Node> claimEditEditNode			=claimEditNodes.selectNodes("./Edit");		//ceedResponseDoc.selectNodes(claimEditNodes.getPath()+"/Edit");
	        	if(claimEditEditNode.size()!=0)
	        		reviewOrAlert		=	"OK";
	        	for(Node claimEditEditNodes : claimEditEditNode){
		        	
	        	List<Node> claimActivityEditNode		=claimEditNodes.selectNodes("./ActivityEdit");	//ceedResponseDoc.selectNodes(claimEditNodes.getPath()+"/ActivityEdit");
	        	for(Node claimActivityEditNodes : claimActivityEditNode){
	        		CeedResponseVo ceedResponseVo=new CeedResponseVo();
	        		//CONDITIONS
		        	//if(claimActivityEditNodes.valueOf("ID").equals(""+activityDetails.getActivitySeqId()))
		        	//{
		        		if(claimActivityEditNodes.valueOf("EditId").equals(claimEditEditNodes.valueOf("ID")))
		        		{

		        			reviewOrAlert	=	claimEditEditNodes.valueOf("Rank");
		        			if("R".equals(reviewOrAlert))
		        				reviewOrAlert	=	"Review";
		        			else if("A".equals(reviewOrAlert))
		        				reviewOrAlert	=	"Alert";
		        			reviewOrAlertTitle=	claimEditEditNodes.valueOf("Comment");
		        			editDetails		=	new String[7];
		        			editDetails[0]	=	claimEditEditNodes.valueOf("ID");
		        			editDetails[1]	=	claimEditEditNodes.valueOf("Type");
		        			editDetails[2]	=	claimEditEditNodes.valueOf("SubType");
		        			editDetails[3]	=	claimEditEditNodes.valueOf("Code");
		        			editDetails[4]	=	claimEditEditNodes.valueOf("Comment");
		        			editDetails[5]	=	claimActivityEditNodes.valueOf("ID");
		        			editDetails[6]	=	claimActivityEditNodes.valueOf("EditId");
		        			request.getSession().setAttribute("editDetails"+claimActivityEditNodes.valueOf("ID"), editDetails);
	        				}
		        		ceedResponseVo.setReviewOrAlert(reviewOrAlert);
		        		ceedResponseVo.setReviewOrAlertTitle(reviewOrAlertTitle);
		        		ceedResData.put(claimActivityEditNodes.valueOf("ID"),ceedResponseVo);
		        		
	        			//}
        			}
	        	}
	        }
            }
           
            request.getSession().setAttribute("ceedResMapData", ceedResData);
            String validateFlag=null;
			if(request.getParameter("validateFlag")!=null)
				validateFlag=request.getParameter("validateFlag");
			if("false".equals(validateFlag)|| validateFlag==null){
				validateFlag="false";
			}else{
				validateFlag="true";
			}
			if("ECL".equals(preAuthDetailVO.getModeOfClaim()) && "false".equals(validateFlag) && "false".equals(isWebBoard)){
				frmClaimGeneral.initialize(mapping);
				frmClaimGeneral = (DynaActionForm) FormUtils.setFormValues("frmClaimGeneral", preAuthDetailVO, this, mapping, request);
				session.setAttribute("frmClaimGeneral", frmClaimGeneral);
				return mapping.findForward("validatePreAuth");
			}
			
			return this.getForward(strClaimdetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest
		
	
	
		// request,HttpServletResponse response)

	/**
	 * This method is used to get the details of the selected record from
	 * web-board. Finally it forwards to the appropriate view based on the
	 * specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	
	
private void saveCeedRules(HttpServletRequest request,Document document){

		
		HttpSession session=request.getSession();
		session.setAttribute("ceedResponse",document);
        
        String namespaceArr[]	=	{"http://schemas.xmlsoap.org/soap/envelope/","http://www.w3.org/2001/XMLSchema-instance",
        							"http://www.w3.org/2001/XMLSchema","http://www.Dimensions-healthcare.net/DHCEG/CommonTypes"
        							};
        
        //REMOVE NAMESPACE CODE
        Namespace oldNs = 	null;
        Namespace newNs =	null;
        Visitor visitor	=	null;
        for(int i=0;i<namespaceArr.length;i++)
        {
        	oldNs = Namespace.get(namespaceArr[i]);
 	        newNs = Namespace.get("", "");
 	        visitor = new NamesapceChangingVisitor(oldNs, newNs);
 	       document.accept(visitor);
        }
       
//        System.out.println("doc after removing namespace::\n"+doc.asXML());
        //REMOVE NAMESPACE CODE
        
      /*  Node envelopeNode	=	document.selectSingleNode(document.getRootElement().getPath());
        //System.out.println("env name::"+envelopeNode.getPath());
        
        Node bodyNode		=	document.selectSingleNode(envelopeNode.getPath()+"/soap:Body");
        
        Node dhcgegResponseNode		=	document.selectSingleNode(bodyNode.getPath()+"/DHCEGResponse");
      
        Node dhcegResultNode		=	document.selectSingleNode(dhcgegResponseNode.getPath()+"/DHCEGResult");
        
        Node responseNode			=	document.selectSingleNode(dhcgegResponseNode.getPath()+"/response");*/
        List<Node> claimEditNode			=	null;
        // claimEditNode			=	doc.selectNodes(responseNode.getPath()+"/ClaimEdit");
        if(document.asXML().contains("ClaimEdit"))
        {
    	   //	claimEditNode			=	document.selectNodes(responseNode.getPath()+"/ClaimEdit");
    	   	claimEditNode			=	document.selectNodes("//ClaimEdit");

    	   	session.setAttribute("claimEditNode",claimEditNode);
        }
     	else
     	{
     		session.setAttribute("claimEditNode",claimEditNode);
     	}
        
       
	}
	
	public ActionForward doChangeWebBoard(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("Inside ClaimGeneralAction doChangeWebBoard");
		request.setAttribute("isWebBoard", "true");
		return doView(mapping, form, request, response);
	}// end of doChangeWebBoard(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is called from the struts framework. This method is used to
	 * navigate to detail screen to add a record. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
     */
	public ActionForward viewHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGeneralAction viewHistory ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			UserSecurityProfile userSecurityProfile = (UserSecurityProfile) request
					.getSession().getAttribute("UserSecurityProfile");
			ArrayList alPrevClaimList = null;
			PreAuthDetailVO preAuthDetailVO = null;
			PreAuthHospitalVO preAuthHospitalVO = null;
			AdditionalDetailVO additionalDetailVO = null;
			ClaimantVO claimantVO = null;
			ClaimDetailVO claimDetailVO = null;
			StringBuffer strCaption = new StringBuffer();
			String strActiveTab = TTKCommon.getActiveLink(request);
			String strFlowType = "";
			String strDetail = "";
			String strPreAuthFlow = "PRE";
			String strClaimFlow = "CLM";

			if (strActiveTab.equals(strPre_Authorization)) {
				strFlowType = strPreAuthFlow;
				strDetail = "preauthdetail";
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strFlowType = strClaimFlow;
				strDetail = "claimsdetail";
			}// end of if(strActiveTab.equals(strClaims))
			preAuthDetailVO = new PreAuthDetailVO();
			preAuthHospitalVO = new PreAuthHospitalVO();
			additionalDetailVO = new AdditionalDetailVO();
			claimantVO = new ClaimantVO();
			preAuthDetailVO.setDiscPresentYN("N");
			preAuthDetailVO.setCompletedYN("N");
			preAuthDetailVO.setPreAuthTypeID("MAN");// from add mode pre-auth
													// type will be manual.
			preAuthDetailVO.setPreAuthRecvTypeID("FAX");// default is fax
			preAuthDetailVO.setPreAuthHospitalVO(preAuthHospitalVO);
			preAuthDetailVO.setAdditionalDetailVO(additionalDetailVO);
			preAuthDetailVO.setClaimantVO(claimantVO);
			preAuthDetailVO.setPriorityTypeID("MID");// from add mode priority
														// will be medium.
			frmPreAuthGeneral = (DynaActionForm) FormUtils.setFormValues(
					"frmPreAuthGeneral", preAuthDetailVO, this, mapping,
					request);
			frmPreAuthGeneral.set("preAuthHospitalVO", FormUtils.setFormValues(
					"frmPreAuthHospital",
					preAuthDetailVO.getPreAuthHospitalVO(), this, mapping,
					request));
			frmPreAuthGeneral.set("claimantDetailsVO", FormUtils.setFormValues(
					"frmClaimantDetails", preAuthDetailVO.getClaimantVO(),
					this, mapping, request));
			frmPreAuthGeneral.set("additionalDetailVO", FormUtils
					.setFormValues("frmAdditionalDetail",
							preAuthDetailVO.getAdditionalDetailVO(), this,
							mapping, request));
			claimDetailVO = new ClaimDetailVO();
			frmPreAuthGeneral.set("claimDetailVO", FormUtils.setFormValues(
					"frmClaimDetail", claimDetailVO, this, mapping, request));
			frmPreAuthGeneral.set("preAuthTypeDesc", "Manual");// to display as
																// manual in the
																// add mode.
			frmPreAuthGeneral.set("flowType", strFlowType.toString());// set the
																		// value
																		// from
																		// which
																		// flow
																		// ur
																		// are.
			strCaption.append("Add");
			if (userSecurityProfile.getBranchID() != null) {
				frmPreAuthGeneral.set("officeSeqID", userSecurityProfile
						.getBranchID().toString());
			}// end of if(userSecurityProfile.getBranchID()!=null)
			else {
				frmPreAuthGeneral.set("officeSeqID", "");
			}// end of else
			frmPreAuthGeneral.set("caption", strCaption.toString());
			frmPreAuthGeneral.set("alPrevClaimList", alPrevClaimList);
			// keep the frmPolicyDetails in session scope
			request.getSession().setAttribute("frmPreAuthGeneral",
					frmPreAuthGeneral);
			this.documentViewer(request, preAuthDetailVO);
			ActionForward actionForward = this.getForward(strDetail, mapping,
					request);
			return actionForward;
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of viewHistory(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward AddClaim(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			HttpSession session = request.getSession();
			log.debug("Inside ClaimGeneralAction AddClaim ");
			DynaActionForm frmCalimGeneral = (DynaActionForm) form;
			StringBuffer strCaption = new StringBuffer();
			strCaption.append("Add");
			frmCalimGeneral.initialize(mapping);
			frmCalimGeneral.set("networkProviderType", "Y");
			frmCalimGeneral.set("caption", strCaption.toString());
			frmCalimGeneral.set("preAuthNoStatus", "FRESH");

			session.setAttribute("frmCalimGeneral", frmCalimGeneral);
			session.setAttribute("diagnosis", null);
			session.setAttribute("activities", null);
			session.setAttribute("shortfalls", null);
			// this.documentViewer(request,preAuthDetailVO);
			return this.getForward(strClaimdetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of AddClaim(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	/**
	 * This method is used to add/update the record. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 * 
	 * 
	 */
	public ActionForward validationOTP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PreAuthManager preAuthObject = this.getPreAuthManagerObject();
		String otpNumber = request.getParameter("otpNumber");
		otpNumber = otpNumber == null ? "" : otpNumber.trim();
		String memberId = request.getParameter("mid");
		memberId = memberId == null ? "" : memberId.trim();
		Map<String, String> holdResult;
		try {
			holdResult = preAuthObject.getOtpresult(memberId,
					TTKCommon.getUserSeqId(request), otpNumber);

		} catch (Exception e) {
			holdResult = null;
		e.printStackTrace();
	 }

		request.setAttribute("holdResult", holdResult);
    return mapping.findForward("preauthOtpValidation");
	}
	
	
	public ActionForward getPatientAgeYearMonthDays(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		try {
			log.debug("Inside ClaimGeneralAction getPatientAgeYearMonthDays");
			setLinks(request);
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			
			request.setAttribute("JS_Focus_ID","dischargeDate");
			if(memberAgeValidation(frmClaimGeneral)){
				frmClaimGeneral.set("memberwtflag", "Y");
				
				ActionMessages actionMessages = new ActionMessages();
	            ActionMessage actionMessage = new ActionMessage("error.member.patient");
	            actionMessages.add("global.error",actionMessage);
	            saveErrors(request,actionMessages);
	    	
	    		request.getSession().setAttribute("frmClaimGeneral",
						frmClaimGeneral);
	            return this.getForward(strClaimdetails, mapping, request);
			}
			else if(memberAgeValidation(frmClaimGeneral)==false)
			{
				frmClaimGeneral.set("memberwtflag", "N");
			}
			
			
		
		request.getSession().setAttribute("frmClaimGeneral",
					frmClaimGeneral);
			return this.getForward(strClaimdetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of getPatientAgeYearMonthDays(ActionMapping mapping,ActionForm form,HttpServletRequest
	// request,HttpServletResponse response)

	/**
	 * This method is used to add/update the record. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
     */
	public ActionForward doSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGeneralAction doSave");
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			HttpSession session = request.getSession();
			
			
			if(memberAgeValidation(frmClaimGeneral)){
				if("".equals(frmClaimGeneral.getString("memberWeight")))
				{
				frmClaimGeneral.set("memberwtflag", "Y");
				
				ActionMessages actionMessages = new ActionMessages();
	            ActionMessage actionMessage = new ActionMessage("error.member.patient");
	            actionMessages.add("global.error",actionMessage);
	            saveErrors(request,actionMessages);
	            session.setAttribute("frmClaimGeneral", frmClaimGeneral);
	            return this.getForward(strClaimdetails, mapping, request);
				}
				
			}
			else if(memberAgeValidation(frmClaimGeneral)==false)
			{
				frmClaimGeneral.set("memberwtflag", "N");
			}
				
	
			PreAuthDetailVO preAuthDetailVO = null;
			StringBuffer strCaption = new StringBuffer();
			String successMsg;
			String memberwarnig;
			String exitdate = frmClaimGeneral.getString("exitdate");
			ClaimManager claimObject = this.getClaimManagerObject();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();

			preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(
					frmClaimGeneral, this, mapping, request);
			preAuthDetailVO.setAddedBy((TTKCommon.getUserSeqId(request)));
			String claimNo = preAuthDetailVO.getClaimNo();
			Long claimSeqID = preAuthDetailVO.getClaimSeqID();
			if (claimNo == null || claimNo.length() < 1)
				successMsg = "Claim Details Added Successfully";
			else
				successMsg = "Claim Details Updated Successfully";
			if(preAuthDetailVO.getEnablericopar()==null)
			{
				preAuthDetailVO.setEnablericopar("N");
				
			}
			
			String processType = frmClaimGeneral.getString("processType");
			String claimType = frmClaimGeneral.getString("claimType");
			String networkProviderType = frmClaimGeneral.getString("networkProviderType");
			if("DBL".equals(processType) && "CTM".equals(claimType) ){
				
				String convertedAmt = preAuthDetailVO.getConvertedAmount();
				
				preAuthDetailVO.setVatAddedReqAmnt(convertedAmt);
				
			}
			else if("GBL".equals(processType) && "CTM".equals(claimType) && "N".equals(networkProviderType)){
				
				String providerCountry = frmClaimGeneral.getString("providerCountry");
				
				if(providerCountry != "123"){
					
					String convertedAmt = preAuthDetailVO.getConvertedAmount();
					
					preAuthDetailVO.setVatAddedReqAmnt(convertedAmt);
					
				}
				
			}
			
			
			
			
			
         claimObject.saveClaimDetails(preAuthDetailVO);
         preAuthDetailVO = claimObject.getClaimDetails(claimSeqID);
			
			 memberwarnig=preAuthDetailVO.getMemActiveStatus();
			
             session.setAttribute("warningmessage", memberwarnig);
			
			if ("N".equals(frmClaimGeneral.getString("networkProviderType"))) {
				session.setAttribute("providerStates",
						TTKCommon.getStates(frmClaimGeneral
								.getString("providerCountry")));
				session.setAttribute("providerAreas", TTKCommon
						.getAreas(frmClaimGeneral.getString("providerEmirate")));
			  }
			session.setAttribute("encounterTypes",
					preAuthObject.getEncounterTypes(frmClaimGeneral
							.getString("benefitType")));

			if(frmClaimGeneral.getString("memberWeight")!=null)
			{
				if(memberAgeValidation(frmClaimGeneral))
				frmClaimGeneral.set("memberwtflag", "Y");
			}
			frmClaimGeneral = setFormValues(preAuthDetailVO, mapping,request);
			session.setAttribute("frmClaimGeneral", frmClaimGeneral);
			

			this.addToWebBoard(preAuthDetailVO, request, "CLM");

			strCaption.append(" Edit");
			strCaption.append(" [ " + frmClaimGeneral.getString("patientName")
					+ " ]");
			strCaption.append(" [ " + frmClaimGeneral.getString("memberId")
					+ " ]");
			frmClaimGeneral.set("caption", strCaption.toString());

			request.setAttribute("successMsg", successMsg);
			return this.getForward(strClaimdetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest
		// request,HttpServletResponse response)

	public ActionForward addDiagnosisDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGeneralAction addDiagnosisDetails ");
			HttpSession session = request.getSession();
			StringBuffer strCaption = new StringBuffer();
			PreAuthDetailVO preAuthDetailVO = null;
			DiagnosisDetailsVO diagnosisDetailsVO = null;
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			ClaimManager claimObject = this.getClaimManagerObject();
		String successMsg;
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			

			String benefitType = (String) frmClaimGeneral.getString("benefitType");
			String productAuthority = (String) frmClaimGeneral.getString("productAuthority");
			String preOneMedicalCondition=request.getParameter("preOneMedicalCondition");
			String primaryAilment = frmClaimGeneral.getString("primaryAilment");
			if(("MOH".equals(productAuthority))&&("DAYC".equals(benefitType)||"IPT".equals(benefitType))&&(preOneMedicalCondition==null||"".equals(preOneMedicalCondition))&&"Y".equals(primaryAilment)){
					TTKException expTTK = new TTKException();
			expTTK.setMessage("error.per.one.medical.condition");
			throw expTTK;
			}
			
			String claimSeqID = frmClaimGeneral.getString("claimSeqID");
			String icdCode = frmClaimGeneral.getString("icdCode");
			String icdCodeSeqId = frmClaimGeneral.getString("icdCodeSeqId");
			String ailmentDescription = frmClaimGeneral
					.getString("ailmentDescription");
			String presentingComplaints = frmClaimGeneral
					.getString("presentingComplaints");
			String authType = frmClaimGeneral.getString("authType");

	//		String primaryAilment = request.getParameter("primaryAilment");
			String diagSeqId = frmClaimGeneral.getString("diagSeqId");
			String infotype = frmClaimGeneral.getString("infoType");
			String infocode = frmClaimGeneral.getString("infoCode");
			String preCronTypeID = (String) frmClaimGeneral.get("preCronTypeID");
			int ailmentDuration =  Integer.parseInt( frmClaimGeneral.getString("ailmentDuration"));
			String ailmentDurationFlag = (String) frmClaimGeneral.get("ailmentDurationFlag");
			String relPresentIllness = (String) frmClaimGeneral.get("relPresentIllness");
			String relevantFindings = (String) frmClaimGeneral.get("relevantFindings");
			Long longDiagSeqId = (diagSeqId == null || diagSeqId.length() < 1) ? null
					: new Long(diagSeqId);

			diagnosisDetailsVO = new DiagnosisDetailsVO();
		  diagnosisDetailsVO.setAuthType(authType);
		  diagnosisDetailsVO.setDiagSeqId(longDiagSeqId);
		  diagnosisDetailsVO.setClaimSeqID(new Long(claimSeqID));
		  diagnosisDetailsVO.setIcdCode(icdCode);
		  diagnosisDetailsVO.setAilmentDescription(ailmentDescription);
		  diagnosisDetailsVO.setPresentingComplaints(presentingComplaints);
		  diagnosisDetailsVO.setPrimaryAilment(primaryAilment);
		  diagnosisDetailsVO.setIcdCodeSeqId(new Long(icdCodeSeqId));
		  diagnosisDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));
		  diagnosisDetailsVO.setInfoCode(infocode);
		  diagnosisDetailsVO.setInfoType(infotype);
		  diagnosisDetailsVO.setPreCronTypeID(preCronTypeID);
			diagnosisDetailsVO.setDurAilment(ailmentDuration);
			diagnosisDetailsVO.setDurationFlag(ailmentDurationFlag);
			diagnosisDetailsVO.setRelPresentIllness(relPresentIllness);
			diagnosisDetailsVO.setRelevantFindings(relevantFindings);
			successMsg = (diagSeqId == null || diagSeqId.length() < 1) ? "Diagnosis Details Added Successfully"
					: "Diagnosis Details Updated Successfully";

		diagnosisDetailsVO.setPreOneMedicalCondition(request.getParameter("preOneMedicalCondition"));
		  preAuthObject.saveDiagnosisDetails(diagnosisDetailsVO);

		  preAuthDetailVO = claimObject.getClaimDetails(new Long(claimSeqID));
			frmClaimGeneral=setFormValues(preAuthDetailVO, mapping, request);
		
			frmClaimGeneral.set("icdCode", "");
			frmClaimGeneral.set("icdCodeSeqId", "");
			frmClaimGeneral.set("ailmentDescription", "");
			frmClaimGeneral.set("primaryAilment", "");
			frmClaimGeneral.set("diagSeqId", "");
			
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmClaimGeneral))
				frmClaimGeneral.set("memberwtflag","Y");
			}
					session.setAttribute("frmClaimGeneral", frmClaimGeneral);

			this.addToWebBoard(preAuthDetailVO, request, "CLM");

					strCaption.append(" Edit");
			strCaption.append(" [ " + frmClaimGeneral.getString("patientName")
					+ " ]");
			strCaption.append(" [ " + frmClaimGeneral.getString("memberId")
					+ " ]");
			frmClaimGeneral.set("caption", strCaption.toString());
			request.setAttribute("JS_Focus_ID","icdbtnID");
			
			request.setAttribute("successMsg", successMsg);
		return this.getForward(strClaimdetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of addDiagnosisDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward editDiagnosisDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGenealAction editDiagnosis ");
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			
			DiagnosisDetailsVO diagnosisDetailsVO2 = ((ArrayList<DiagnosisDetailsVO>) frmClaimGeneral.get("diagnosisList")).get(new Integer(request
					.getParameter("rownum")).intValue());

			frmClaimGeneral.set("primaryAilment",
					diagnosisDetailsVO2.getPrimaryAilment());
			frmClaimGeneral.set("ailmentDescription",
					diagnosisDetailsVO2.getAilmentDescription());
			frmClaimGeneral.set("icdCode", diagnosisDetailsVO2.getIcdCode());
			frmClaimGeneral.set("icdCodeSeqId", diagnosisDetailsVO2
					.getIcdCodeSeqId().toString());
			frmClaimGeneral.set("diagSeqId", diagnosisDetailsVO2.getDiagSeqId()
					.toString());
			frmClaimGeneral.set("infoCode",diagnosisDetailsVO2.getInfoCode().toString());
			frmClaimGeneral.set("infoType",diagnosisDetailsVO2.getInfoType().toString());
			frmClaimGeneral.set("preCronTypeYN",diagnosisDetailsVO2.getPreCronTypeYN());
			frmClaimGeneral.set("preCronTypeID",diagnosisDetailsVO2.getPreCronTypeID());
			frmClaimGeneral.set("preOneMedicalCondition",diagnosisDetailsVO2.getPreOneMedicalCondition());
			request.getSession().setAttribute("frmClaimGeneral",frmClaimGeneral);
			
			request.setAttribute("JS_Focus_ID","actImageID");
			return this.getForward(strClaimdetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of editDiagnosisDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward deleteDiagnosisDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGenealAction deleteDiagnosis ");
			StringBuffer strCaption = new StringBuffer();
			PreAuthDetailVO preAuthDetailVO = null;
			ClaimManager claimObject = this.getClaimManagerObject();
			DiagnosisDetailsVO diagnosisDetailsVO = null;
			HttpSession session = request.getSession();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();

			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			String claimSeqID = (String) frmClaimGeneral.get("claimSeqID");

			diagnosisDetailsVO = ((ArrayList<DiagnosisDetailsVO>)frmClaimGeneral.get("diagnosisList") ).get(new Integer(request.getParameter("rownum")).intValue());
		  diagnosisDetailsVO.setAuthType("CLM");
		  diagnosisDetailsVO.setClaimSeqID(new Long(claimSeqID));
			diagnosisDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));

		      preAuthObject.deleteDiagnosisDetails(diagnosisDetailsVO);
			request.setAttribute("successMsg",
					"Diagnosis Details Deleted Successfully");

			preAuthDetailVO = claimObject.getClaimDetails(new Long(claimSeqID));
			frmClaimGeneral=setFormValues(preAuthDetailVO, mapping, request);
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmClaimGeneral))
				frmClaimGeneral.set("memberwtflag","Y");
			}
			
				session.setAttribute("frmClaimGeneral", frmClaimGeneral);

			this.addToWebBoard(preAuthDetailVO, request, "CLM");

				strCaption.append(" Edit");
			strCaption.append(" [ " + frmClaimGeneral.getString("patientName")
					+ " ]");
			strCaption.append(" [ " + frmClaimGeneral.getString("memberId")
					+ " ]");
			frmClaimGeneral.set("caption", strCaption.toString());
			session.setAttribute("frmClaimGeneral", frmClaimGeneral);

			return this.getForward(strClaimdetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of deleteDiagnosisDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * setMaternityMode
	 * 
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	public ActionForward setMaternityMode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside ClaimGenealAction doGeneral");
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			frmClaimGeneral.set("gravida", "");
			frmClaimGeneral.set("para", "");
			frmClaimGeneral.set("live", "");
			frmClaimGeneral.set("abortion", "");
			String benefitTypes = frmClaimGeneral.getString("benefitType");
			
			  if(benefitTypes.equals("IPT") || benefitTypes.equals("MTI"))
			  {
				  frmClaimGeneral.set("encounterTypeId", "3");
			
			  }
			  else 
			  {
				  frmClaimGeneral.set("encounterTypeId", "");
				  
				  frmClaimGeneral.set("memberWeight", "");
				  frmClaimGeneral.set("memberwtflag", "N");

			  }
			request.getSession().setAttribute(
					"encounterTypes",
					preAuthObject.getEncounterTypes(frmClaimGeneral
							.getString("benefitType")));
			request.getSession().setAttribute("frmClaimGeneral",
					frmClaimGeneral);
	return mapping.findForward(strClaimdetails);
		}// end of try
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)

	}// end of doGeneral(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward  setDateMode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside ClaimGenealAction doGeneral");
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			String encounterTypeId=frmClaimGeneral.getString("encounterTypeId");
			if(memberAgeValidation(frmClaimGeneral)){
				frmClaimGeneral.set("memberwtflag", "Y");
				
				ActionMessages actionMessages = new ActionMessages();
	            ActionMessage actionMessage = new ActionMessage("error.member.patient");
	            actionMessages.add("global.error",actionMessage);
	            saveErrors(request,actionMessages);
	            request.getSession().setAttribute("frmClaimGeneral",
						frmClaimGeneral);
	            return this.getForward(strClaimdetails, mapping, request);
			}
			request.getSession().setAttribute("frmClaimGeneral",
					frmClaimGeneral);
	return mapping.findForward(strClaimdetails);
		}// end of try
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)

	}
	
	
	
	public ActionForward setProviderMode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGenealAction setProviderMode ");

			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			String networkProviderType = (String) frmClaimGeneral
					.get("networkProviderType");
			HttpSession session = request.getSession();
			if ("N".equals(networkProviderType)) {
				frmClaimGeneral.set("networkProviderType", "N");
			} else {
				frmClaimGeneral.set("providerAddress", "");
				frmClaimGeneral.set("providerPobox", "");
				frmClaimGeneral.set("providerArea", "");
				frmClaimGeneral.set("providerEmirate", "");
				frmClaimGeneral.set("providerCountry", "");
				frmClaimGeneral.set("networkProviderType", "Y");
		}
			frmClaimGeneral.set("providerName", "");
			frmClaimGeneral.set("providerId", "");
			frmClaimGeneral.set("providerSeqId", "");
			frmClaimGeneral.set("clinicianName", "");
			frmClaimGeneral.set("clinicianId", "");
			session.setAttribute("frmClaimGeneral", frmClaimGeneral);
			return this.getForward(strClaimdetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of setProviderMode(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward selectActivityCode(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			
			setLinks(request);
			log.debug("Inside ClaimGenealAction selectActivityCode ");

			HttpSession session = request.getSession();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			String codeFlag = request.getParameter("codeFlag");

			if ("remember".equalsIgnoreCase(codeFlag)) {// executing when
														// entered activity code
														// and onblure event
				DynaActionForm frmActivityDetails = (DynaActionForm) form;
				String activityStartDate = (String) frmActivityDetails
						.get("activityStartDate");
				String claimSeqID = (String) frmActivityDetails
						.get("claimSeqID");
				String claimNo = (String) frmActivityDetails.get("claimNo");
				String activityCode = (String) frmActivityDetails					
						.get("activityCode");
				activityCode = (activityCode == null) ? "" : activityCode
						.trim();
				String activityDtlSeqId = frmActivityDetails
						.getString("activityDtlSeqId");
				String clinicianId = frmActivityDetails
						.getString("clinicianId");
				String clinicianName = frmActivityDetails
						.getString("clinicianName");
				String authType = frmActivityDetails.getString("authType");
				String overrideYN = frmActivityDetails.getString("overrideYN");	
				String networkProviderType = frmActivityDetails
						.getString("networkProviderType");
				String sCategory=frmActivityDetails.getString("sCategory");
				String providerReqAmt=frmActivityDetails.getString("providerRequestedAmt");
				String convertedProviderReqAmt=frmActivityDetails.getString("convertedProviderReqAmt"); 
				String quantity = frmActivityDetails.getString("quantity");
				String condDenialCode=TTKCommon.checkNull(frmActivityDetails.getString("condDenialCode"));
				String payerAuthority=TTKCommon.checkNull(frmActivityDetails.getString("payerAuthority"));
				
				ArrayList<Object> denialDesc = null;
				ArrayList<CacheObject> denailDescription = null;
							
				denialDesc = preAuthObject.getDenialDescriptionsList(payerAuthority);
				denailDescription=(ArrayList<CacheObject>)denialDesc.get(0);
				
				ActivityDetailsVO activityDetailsVO = preAuthObject
						.getActivityCodeTariff(sCategory,activityCode, claimSeqID,
								activityStartDate, authType,overrideYN,condDenialCode);
				String activityTariffStatus = activityDetailsVO
						.getActivityTariffStatus();
				
				sCategory=activityDetailsVO.getsCategory();
				if (!(activityTariffStatus == null || activityTariffStatus
						.trim().length() < 1)) {
					request.setAttribute("activityTariffStatus",					
							activityTariffStatus);
	}

				if ("VALID".equals(activityDetailsVO.getDisplayMsg()))
					request.setAttribute("successMsg", "Activity Code Valid");
				else
					request.setAttribute("errorMsg", "Activity Code Not Valid");

	frmActivityDetails.initialize(mapping);

				frmActivityDetails = (DynaActionForm) FormUtils.setFormValues(
						"frmActivityDetails", activityDetailsVO, this, mapping,
						request);
				DynaActionForm frmClaimGeneral = (DynaActionForm)request.getSession().getAttribute("frmClaimGeneral");
				
				String modeOfClaim = frmClaimGeneral.getString("modeOfClaim");
	 frmActivityDetails.set("activityCode", activityCode);
	 frmActivityDetails.set("amountAllowed", "Y");
	 frmActivityDetails.set("claimSeqID", claimSeqID);
	 frmActivityDetails.set("clinicianName", clinicianName);
	 frmActivityDetails.set("activityStartDate", activityStartDate);
	 frmActivityDetails.set("claimNo", claimNo);
	 frmActivityDetails.set("clinicianId", clinicianId);
	 frmActivityDetails.set("activityDtlSeqId", activityDtlSeqId);
	 frmActivityDetails.set("overrideYN", overrideYN);					
	frmActivityDetails.set("networkProviderType",networkProviderType);
	
	frmActivityDetails.set("providerRequestedAmt",providerReqAmt);
	frmActivityDetails.set("convertedProviderReqAmt",convertedProviderReqAmt); 
	frmActivityDetails.set("quantity",quantity);
	/*if("ECL".equals(modeOfClaim))
	{
		frmActivityDetails.set("quantity", quantity);
	}*/
	frmActivityDetails.set("sCategory", sCategory);
	frmActivityDetails.set("payerAuthority",payerAuthority);
	
	System.out.println("-------------------- after claim activity code refresh  -------------------------");
	Float unitPrice=(float) (activityDetailsVO.getUnitPrice()==null?0.0:activityDetailsVO.getUnitPrice().floatValue());
	Float unitDiscount=(float) (activityDetailsVO.getDiscountPerUnit()==null?0.0:activityDetailsVO.getDiscountPerUnit().floatValue());
	float grossAmount= 0;
	float discountAmt= 0;
	float discountedGrossAmt= 0;
	
	System.out.println("1 : unitPrice 	 (from db)		= "+ unitPrice);
	System.out.println("2 : unitDiscount (from db) 		= "+ unitDiscount);
	System.out.println("3 : quantity 	 (from form)	= "+ quantity);
	
	String  qt="";
	
	if("".equals(quantity) || quantity == null)
		qt = "0";	
	else
		qt = quantity ;

	float qty=Float.parseFloat(qt); 
	grossAmount = qty * unitPrice;
	discountAmt = qty * unitDiscount;
	discountedGrossAmt = grossAmount - discountAmt;
	
	String GrossAmt =String.valueOf(grossAmount);
	String discount =String.valueOf(discountAmt);
	String discountedGross =String.valueOf(discountedGrossAmt);
	
	frmActivityDetails.set("grossAmount",GrossAmt);
	if("0.0".equalsIgnoreCase(discount))
		frmActivityDetails.set("discount","");
	else
		frmActivityDetails.set("discount",discount);	
	frmActivityDetails.set("discountedGross",discountedGross);
	frmActivityDetails.set("netAmount",discountedGross);
	
	System.out.println("-------------------------- end ---------------------------");
	
	
	frmActivityDetails.set("denialCodeList",denailDescription);
				session.setAttribute("frmActivityDetails", frmActivityDetails);
			} else if ("search".equals(codeFlag)) {// executing when searching
													// activity code selected
				DynaActionForm frmActivityDetails = (DynaActionForm) session
						.getAttribute("frmActivityDetails");
				String activityStartDate = (String) frmActivityDetails
						.get("activityStartDate");
				String claimSeqID = (String) frmActivityDetails
						.get("claimSeqID");
				String claimNo = (String) frmActivityDetails.get("claimNo");
				String activityDtlSeqId = frmActivityDetails
						.getString("activityDtlSeqId");
				String clinicianId = frmActivityDetails
						.getString("clinicianId");
				String clinicianName = frmActivityDetails
						.getString("clinicianName");
				String authType = frmActivityDetails.getString("authType");
				String overrideYN = frmActivityDetails.getString("overrideYN");
				String networkProviderType = frmActivityDetails
						.getString("networkProviderType");
				
				String providerReqAmt=frmActivityDetails.getString("providerRequestedAmt");
				String sCategory = frmActivityDetails.getString("sCategory");
				String condDenialCode=TTKCommon.checkNull(frmActivityDetails.getString("condDenialCode"));
				String payerAuthority=TTKCommon.checkNull(frmActivityDetails.getString("payerAuthority"));
				TableData activityCodeListData = (TableData) session
						.getAttribute("activityCodeListData");
				
				ArrayList<Object> denialDesc = null;
				ArrayList<CacheObject> denailDescription = null;
							
				denialDesc = preAuthObject.getDenialDescriptionsList(payerAuthority);
				denailDescription=(ArrayList<CacheObject>)denialDesc.get(0);
							 
				ActivityDetailsVO activityDetailsVO = (ActivityDetailsVO) activityCodeListData
						.getRowInfo(Integer.parseInt((String) request
								.getAttribute("rownum")));

				String activityCode = activityDetailsVO.getActivityCode();
				 sCategory=activityDetailsVO.getsCategory();
				/*System.out.println("sCategory in search================>"+sCategory)*/;
				activityDetailsVO = preAuthObject.getActivityCodeTariff(sCategory,
						activityCode, claimSeqID, activityStartDate, authType,overrideYN,condDenialCode);
		
				String activityTariffStatus = activityDetailsVO
						.getActivityTariffStatus();
				if (!(activityTariffStatus == null || activityTariffStatus
						.trim().length() < 1)) {
					request.setAttribute("activityTariffStatus",
							activityTariffStatus);
				}
		frmActivityDetails.initialize(mapping);

				frmActivityDetails = (DynaActionForm) FormUtils.setFormValues(
						"frmActivityDetails", activityDetailsVO, this, mapping,
						request);
	 frmActivityDetails.set("activityCode", activityCode);
	 
	 
	 
	 frmActivityDetails.set("amountAllowed", "Y");
	 frmActivityDetails.set("claimSeqID", claimSeqID);
	 frmActivityDetails.set("activityStartDate", activityStartDate);
	 frmActivityDetails.set("claimNo", claimNo);
	 frmActivityDetails.set("clinicianId", clinicianId);
	 frmActivityDetails.set("clinicianName", clinicianName);
	 frmActivityDetails.set("activityDtlSeqId", activityDtlSeqId);
	 frmActivityDetails.set("overrideYN", overrideYN);									
	 frmActivityDetails.set("networkProviderType",networkProviderType);
		
	frmActivityDetails.set("providerRequestedAmt",providerReqAmt);
	frmActivityDetails.set("sCategory", sCategory);
	frmActivityDetails.set("payerAuthority",payerAuthority);
	frmActivityDetails.set("denialCodeList",denailDescription);
				session.setAttribute("frmActivityDetails", frmActivityDetails);
 }

			return this.getForward(strActivitydetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strActivitydetails));
		}// end of catch(Exception exp)
	}// end of selectActivityCode(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
 * doGeneral forward the based on reforward parameter value
	 * 
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	public ActionForward doGeneral(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			log.debug("Inside ClaimGenealAction doGeneral");
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			String path = "Claims.Processing.General";
			HttpSession session = request.getSession();
			String reforward = request.getParameter("reforward");
			
			String claimno = frmClaimGeneral.getString("claimNo");
			
			
			if ("providerSearch".equalsIgnoreCase(reforward)) {
				TableData providerListData = new TableData(); // create new
																// table data
																// object
				providerListData.createTableInfo("ProviderListTable",new ArrayList());
				session.setAttribute("providerListData", providerListData);// create
				if(claimno.length()==0)
				{
					session.setAttribute("claimno", "");
					session.removeAttribute("frmPreAuthList");
					session.removeAttribute("count");
				}															// the
				else{
					session.removeAttribute("count");
					session.setAttribute("claimno", claimno);
					session.setAttribute("frmClaimGeneral", frmClaimGeneral);	
				}	
				 path = "providerSearchList";
			} 
			else if ("preauthdocUpload".equalsIgnoreCase(reforward)) {
				String policyseqid = "";
				ArrayList alPolicyDocs = new ArrayList();
				
				PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			  policyseqid =request.getParameter("policySeqId");
			   if (policyseqid.trim()!="" || policyseqid.trim().length()!=0)
			   {
				  Long policySeq_id= Long.valueOf(policyseqid);
				  alPolicyDocs = preAuthObject.getPolicyDocsUploads(policySeq_id);
			   }
			   request.setAttribute("alPolicyDocs", alPolicyDocs);
				path = "claimdocUpload";
				return mapping.findForward(path);
			}
			
			else if ("memberSearch".equalsIgnoreCase(reforward)) {
				String memberId = frmClaimGeneral.getString("memberId");
				
				TableData memberListData = new TableData(); // create new
																// table data
																// object
				
				memberListData.createTableInfo("MemberListTable",
						new ArrayList());
				session.setAttribute("memberListData", memberListData);
				
				
				frmClaimGeneral.set("sEnrollmentId", memberId);
				session.setAttribute("frmPreAuthList", frmClaimGeneral);
				path = "memberSearchList";
				return mapping.findForward(path);
			}
			
			
			
			else if ("clinicianSearch".equalsIgnoreCase(reforward)) {
				
			
				TableData clinicianListData = new TableData(); // create new
																// table data
																// object
				clinicianListData.createTableInfo("ClinicianListTable",
						new ArrayList());
				session.setAttribute("clinicianListData", clinicianListData);// create
																				// the
		    String providerType	=	frmClaimGeneral.getString("providerType");
		    String stradmissionDate=null;
		    if("".equals(frmClaimGeneral.getString("admissionDate")))
		    {
		    	
		    }
		    else{
		     stradmissionDate =frmClaimGeneral.getString("admissionDate");
		     frmClaimGeneral.set("admissionDate",stradmissionDate);  
		    }
		    
		   
		    // required
																				// grid
																				// table
				session.setAttribute("frmClaimGeneral", frmClaimGeneral);
				session.setAttribute("forwardMode", "");
				path = "clinicianSearchList";
			} else if ("diagnosisSearch".equalsIgnoreCase(reforward)) {
				
				
				frmClaimGeneral.set("icdCode", "");
				frmClaimGeneral.set("icdCodeSeqId", "");
				frmClaimGeneral.set("ailmentDescription", "");
				frmClaimGeneral.set("primaryAilment", "");

				TableData diagnosisCodeListData = new TableData(); // create new
																	// table
																	// data
																	// object
				diagnosisCodeListData.createTableInfo("DiagnosisCodeListTable",
						new ArrayList());
				session.setAttribute("diagnosisCodeListData",
						diagnosisCodeListData);// create the required grid table
				session.setAttribute("frmClaimGeneral", frmClaimGeneral);
				path = "diagnosisSearchList";
			} else if ("addActivityDetails".equalsIgnoreCase(reforward)) {					
				
				
				session.setAttribute("frmClaimGeneral", frmClaimGeneral);
				request.setAttribute("provRegAuthority",frmClaimGeneral.getString("provRegAuthority"));
				path = "Claims.Processing.General.ActivityDetails";
			} else if ("activityClinicianSearch".equalsIgnoreCase(reforward)) {
				
				DynaActionForm frmActivityDetails = (DynaActionForm) form;
				TableData clinicianListData = new TableData(); // create new
																// table data
																// object
				clinicianListData.createTableInfo("ClinicianListTable",
						new ArrayList());
				session.setAttribute("clinicianListData", clinicianListData);// create
																				// the
																				// required
																				// grid
																				// table
				session.setAttribute("forwardMode", "activityClinicianSearch");
				session.setAttribute("frmActivityDetails", frmActivityDetails);
				path = "clinicianSearchList";
			} else if ("activitySearchList".equalsIgnoreCase(reforward)) {
				DynaActionForm frmActivityDetails = (DynaActionForm) form;
				TableData activityCodeListData = new TableData(); // create new
																	// table
																	// data
																	// object
				activityCodeListData.createTableInfo("ActivityCodeListTable",
						new ArrayList());
				session.setAttribute("activityCodeListData",
						activityCodeListData);// create the required grid table
				session.setAttribute("frmActivityDetails", frmActivityDetails);
				path = "activitySearchList";
			} else if ("viewActivityDetails".equals(reforward)) {
				request.setAttribute("activityDtlSeqId",
						frmClaimGeneral.getString("activityDtlSeqId"));
				request.setAttribute("networkProviderType",
						frmClaimGeneral.getString("networkProviderType"));
				request.setAttribute("provRegAuthority",frmClaimGeneral.getString("provRegAuthority"));
				request.setAttribute("payerAuthority",frmClaimGeneral.getString("payerAuthority"));
				path = "viewActivityDetails";
			} else if ("overrideActivity".equals(reforward)) {										
				
				
				request.setAttribute("activityDtlSeqId",
						frmClaimGeneral.getString("activityDtlSeqId"));
				request.setAttribute("networkProviderType",
						frmClaimGeneral.getString("networkProviderType"));
				request.setAttribute("override",
						request.getParameter("override"));
				request.setAttribute("provRegAuthority",frmClaimGeneral.getString("provRegAuthority"));
				path = "overrideActivity";
			} else if ("claimshortfalls".equalsIgnoreCase(reforward)) {
				session.setAttribute("frmClaimGeneral", frmClaimGeneral);
	session.setAttribute("closeShortfalls", "goGeneral");
				path = "claimshortfalls";
			} else if ("viewShortfalls".equalsIgnoreCase(reforward)) {
				String claimSeqID = frmClaimGeneral.getString("claimSeqID");
				String shortFallSeqId = frmClaimGeneral
						.getString("shortFallSeqId");
				ShortfallVO shortfallVO = new ShortfallVO();
	shortfallVO.setClaimSeqID(new Long(claimSeqID));
	shortfallVO.setShortfallSeqID(new Long(shortFallSeqId));
	session.setAttribute("searchShortfallVO", shortfallVO);
	session.setAttribute("closeShortfalls", "goGeneral");
				path = "viewShortfalls";
			} else if ("close".equalsIgnoreCase(reforward)) {
				DynaActionForm dynForm = (DynaActionForm) form;
				String claimSeqID = dynForm.getString("claimSeqID");
				request.setAttribute("JS_Focus_ID","calculatebtnID");
	request.setAttribute("claimSeqID", claimSeqID);
				path = "Claims.Processing.General.GetAllClaimDetails";
			} else if ("goShortfallSearch".equalsIgnoreCase(reforward)) {
				path = "Claims.Shortfalls.Search";
			} else if ("selectAuthorizationdetails".equals(reforward)) {
	session.setAttribute("frmClaimGeneral", frmClaimGeneral);
				path = "authorizationList";
	return mapping.findForward(path);
			} else if ("closeHistoryDetails".equalsIgnoreCase(reforward)) {
				path = "claimHistoryList";
	return mapping.findForward(path);
			}  else if ("viewBenefitDetails".equalsIgnoreCase(reforward)) {
				session.setAttribute("frmClaimGeneral", frmClaimGeneral);
				path = "Claims.Processing.General.BenefitDetails";
			}
			 else if ("check".equalsIgnoreCase(reforward)) {
					session.setAttribute("frmClaimGeneral", frmClaimGeneral);
					path = "Claims.Processing.General.BenefitDetails";
				}
			
	return mapping.findForward(path);
		}// end of try
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)

	}// end of doGeneral(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward addActivityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			
			setLinks(request);
			log.debug("Inside ClaimGeneralAction addActivityDetails ");
			HttpSession session = request.getSession();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			DynaActionForm frmClaimGeneral = (DynaActionForm) session
					.getAttribute("frmClaimGeneral");
			String climtype=frmClaimGeneral.getString("claimType");
			
			String claimNo = frmClaimGeneral.getString("claimNo");
		
			String claimSeqID = frmClaimGeneral.getString("claimSeqID");
			String admissionDate = frmClaimGeneral.getString("admissionDate");
		
			String encounterTypeId = frmClaimGeneral
					.getString("encounterTypeId");
			String clinicianId = frmClaimGeneral.getString("clinicianId");
		
			String networkProviderType = frmClaimGeneral
					.getString("networkProviderType");
			String clinicianName = frmClaimGeneral.getString("clinicianName");
			
			String payerAuthority = (String) request.getAttribute("provRegAuthority");
			
			ArrayList<Object> denialDesc = null;
			ArrayList<CacheObject> denailDescription = null;
			//CacheObject denialDescription = null;
			
			denialDesc = preAuthObject.getDenialDescriptionsList(payerAuthority);
			denailDescription=(ArrayList<CacheObject>)denialDesc.get(0);
			
			boolean dateStatus = false;
			if ("1".equals(encounterTypeId) || "2".equals(encounterTypeId))
				dateStatus = true;

			DynaActionForm frmActivityDetails = (DynaActionForm) form;
	frmActivityDetails.initialize(mapping);
	//frmActivityDetails.set("claimtype",climtype);
	frmActivityDetails.set("claimNo", claimNo);
	frmActivityDetails.set("claimSeqID", claimSeqID);
	frmActivityDetails.set("clinicianId", clinicianId);
			frmActivityDetails.set("clinicianName", clinicianName);
	frmActivityDetails.set("networkProviderType", networkProviderType);
			frmActivityDetails.set("activityStartDate", admissionDate);// 1,2--NON-edit
			frmActivityDetails.set("amountAllowed", "Y");// Amount Allowed
															// default Checked
			 frmActivityDetails.set("denialCodeList",denailDescription);	
			 frmActivityDetails.set("payerAuthority",payerAuthority);
	session.setAttribute("dateStatus", dateStatus);
	session.setAttribute("frmActivityDetails", frmActivityDetails);
	return mapping.findForward(strActivitydetails);

		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of AddActivityDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)
	
	public ActionForward overrideActivityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			log.debug("Inside ClaimGeneralAction editActivityDetails ");
            String strforward="";
			setLinks(request);
			log.debug("Inside ClaimGeneralAction editActivityDetails ");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			String activityDtlSeqId = (String) request
					.getAttribute("activityDtlSeqId");
			String override = (String) request.getAttribute("override");
			String networkProviderType = (String) request
					.getAttribute("networkProviderType");
			
			String payerAuthority = (String) request.getAttribute("provRegAuthority");
			
			ArrayList<Object> denialDesc = null;
			ArrayList<CacheObject> denailDescription = null;
						
			denialDesc = preAuthObject.getDenialDescriptionsList(payerAuthority);
			denailDescription=(ArrayList<CacheObject>)denialDesc.get(0);
						 
			ActivityDetailsVO activityDetailsVO = preAuthObject
					.getActivityDetails(activityDtlSeqId == null ? 0 : Long
							.parseLong(activityDtlSeqId));
			LinkedHashMap<String, String> activityDenialList = new LinkedHashMap<String, String>();
			// splitting the denial codes and denial desc in case override
			if ("Y".equals(activityDetailsVO.getOverrideYN())) {

				String denialCode = activityDetailsVO.getDenialCode();
				String denialCodeDesc = activityDetailsVO
						.getDenialDescription();

				if (denialCode != null && denialCodeDesc != null) {
					String denialCodes[] = denialCode.split("[;]");
					String denialCodeDescs[] = denialCodeDesc.split("[;]");

					if (denialCodes.length == denialCodeDescs.length) {
						for (int i = 0; i < denialCodes.length; i++) {
							activityDenialList.put(denialCodes[i].trim(),
									denialCodeDescs[i]);
		}
	}
				}
	activityDetailsVO.setDenialCode(null);
	activityDetailsVO.setDenialDescription(null);
			}
			frmActivityDetails = (DynaActionForm) FormUtils.setFormValues(
					"frmActivityDetails", activityDetailsVO, this, mapping,
					request);

			frmActivityDetails.set("networkProviderType", networkProviderType);
					
			frmActivityDetails.set("overrideYN", override);
			request.getSession().setAttribute("activityDenialList",
					activityDenialList);
			String CondDenialCode = activityDetailsVO.getCondDenialCode();
			 frmActivityDetails.set("denialCodeList",denailDescription);	
			 frmActivityDetails.set("payerAuthority",payerAuthority);
			request.getSession().setAttribute("frmActivityDetails",
					frmActivityDetails);

			return this.getForward(strActivitydetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// overrideActivity()


	
	
	
	
	
	
	
	
	
	
	

	/*public ActionForward overrideActivityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		try {
			setLinks(request);
			System.out.println(" overrideActivity() strteend");

			log.debug("Inside ClaimGeneralAction editActivityDetails ");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			String activityDtlSeqId = (String) request
					.getAttribute("activityDtlSeqId");
			String override = (String) request.getAttribute("override");
			String networkProviderType = (String) request
					.getAttribute("networkProviderType");
			ActivityDetailsVO activityDetailsVO = preAuthObject
					.getActivityDetails(activityDtlSeqId == null ? 0 : Long
							.parseLong(activityDtlSeqId));

			LinkedHashMap<String, String> activityDenialList = new LinkedHashMap<String, String>();
			// splitting the denial codes and denial desc in case override
			if ("Y".equals(activityDetailsVO.getOverrideYN())) {

				String denialCode = activityDetailsVO.getDenialCode();
				String denialCodeDesc = activityDetailsVO
						.getDenialDescription();

				if (denialCode != null && denialCodeDesc != null) {
					String denialCodes[] = denialCode.split("[;]");
					String denialCodeDescs[] = denialCodeDesc.split("[;]");

					if (denialCodes.length == denialCodeDescs.length) {
						for (int i = 0; i < denialCodes.length; i++) {
							activityDenialList.put(denialCodes[i].trim(),
									denialCodeDescs[i]);
		}
	}
				}
	activityDetailsVO.setDenialCode(null);
	activityDetailsVO.setDenialDescription(null);
			}

			frmActivityDetails = (DynaActionForm) FormUtils.setFormValues(
					"frmActivityDetails", activityDetailsVO, this, mapping,
					request);

			frmActivityDetails.set("networkProviderType", networkProviderType);
			frmActivityDetails.set("overrideYN", override);
			request.getSession().setAttribute("activityDenialList",
					activityDenialList);
			request.getSession().setAttribute("frmActivityDetails",
					frmActivityDetails);
			System.out.println("enddddddddddddddddddddd");
			return this.getForward(strActivitydetails, mapping, request);
			
		}// end of try
		
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
		
	}// overrideActivity()
*/
	public ActionForward viewActivityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGeneralAction editActivityDetails ");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			String activityDtlSeqId = (String) request
					.getAttribute("activityDtlSeqId");
			String networkProviderType = (String) request
					.getAttribute("networkProviderType");
			
			String payerAuthority = (String) request.getAttribute("provRegAuthority");
			ArrayList<Object> denialDesc = null;
			ArrayList<CacheObject> denailDescription = null;
			
			denialDesc = preAuthObject.getDenialDescriptionsList(payerAuthority);
			denailDescription=(ArrayList<CacheObject>)denialDesc.get(0);
			
			ActivityDetailsVO activityDetailsVO = preAuthObject
					.getActivityDetails(activityDtlSeqId == null ? 0 : Long
							.parseLong(activityDtlSeqId));

			LinkedHashMap<String, String> activityDenialList = new LinkedHashMap<String, String>();
			// splitting the denial codes and denial desc in case override
			if ("Y".equals(activityDetailsVO.getOverrideYN())) {

				String denialCode = activityDetailsVO.getDenialCode();
				String denialCodeDesc = activityDetailsVO
						.getDenialDescription();

				if (denialCode != null && denialCodeDesc != null) {
					String denialCodes[] = denialCode.split("[;]");
					String denialCodeDescs[] = denialCodeDesc.split("[;]");

					if (denialCodes.length == denialCodeDescs.length) {
						for (int i = 0; i < denialCodes.length; i++) {
							activityDenialList.put(denialCodes[i].trim(),
									denialCodeDescs[i]);
		}
	}
				}
	activityDetailsVO.setDenialCode(null);
	activityDetailsVO.setDenialDescription(null);
			}

			frmActivityDetails = (DynaActionForm) FormUtils.setFormValues(
					"frmActivityDetails", activityDetailsVO, this, mapping,
					request);

			request.setAttribute("actDtlSeqId", activityDetailsVO.getActivitySeqId().toString());
			
			frmActivityDetails.set("networkProviderType", networkProviderType);
			request.getSession().setAttribute("activityDenialList",
					activityDenialList);
			 frmActivityDetails.set("denialCodeList",denailDescription);
			 frmActivityDetails.set("payerAuthority",payerAuthority);
			 	
			request.getSession().setAttribute("frmActivityDetails",
					frmActivityDetails);
			return this.getForward(strActivitydetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// editActivityDetails()

	public ActionForward deleteActivityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGenealAction deleteActivityDetails ");
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			StringBuffer strCaption = new StringBuffer();
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			String claimSeqID = (String) frmClaimGeneral.get("claimSeqID");
			String authType = frmClaimGeneral.getString("authType");
			String activityDtlSeqId = (String) frmClaimGeneral.get("activityDtlSeqId");

			preAuthObject.deleteActivityDetails(
					new Long(claimSeqID).longValue(),
					new Long(activityDtlSeqId).longValue(), authType);
			request.setAttribute("successMsg",
					"Activity Details Deleted Successfully");

			HttpSession session = request.getSession();
			PreAuthDetailVO preAuthDetailVO = null;
			ClaimManager claimObject = this.getClaimManagerObject();
			  frmClaimGeneral.initialize(mapping);
			  preAuthDetailVO = claimObject.getClaimDetails(new Long(claimSeqID));
			  frmClaimGeneral=setFormValues(preAuthDetailVO, mapping, request);
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmClaimGeneral))
				frmClaimGeneral.set("memberwtflag","Y");
			}
			
			  			session.setAttribute("frmClaimGeneral", frmClaimGeneral);

			this.addToWebBoard(preAuthDetailVO, request, "CLM");

			  			strCaption.append(" Edit");
			strCaption.append(" [ " + frmClaimGeneral.getString("patientName")
					+ " ]");
			strCaption.append(" [ " + frmClaimGeneral.getString("memberId")
					+ " ]");
			frmClaimGeneral.set("caption", strCaption.toString());
			  return this.getForward(strClaimdetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimdetails));
		}// end of catch(Exception exp)
	}// end of deleteActivityDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward saveActivityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
		setLinks(request);
		log.debug("Inside ClaimGenealAction saveActivityDetails");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			String successMsg;
			ActivityDetailsVO activityDetailsVO = new ActivityDetailsVO();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();

			activityDetailsVO = (ActivityDetailsVO) FormUtils.getFormValues(
					frmActivityDetails, "frmActivityDetails", this, mapping,
					request);
			activityDetailsVO.setAddedBy(TTKCommon.getUserSeqId(request));
			String amountAllowed = request.getParameter("amountAllowed");
			String overrideYN = request.getParameter("overrideYN");
			amountAllowed = (amountAllowed == null || "".equals(amountAllowed)) ? "N"
					: amountAllowed;
			overrideYN = (overrideYN == null || "".equals(overrideYN)) ? "N"
					: overrideYN;
			activityDetailsVO.setAmountAllowed(amountAllowed);
			
			DynaActionForm frmClaimGeneral=(DynaActionForm)request.getSession().getAttribute("frmClaimGeneral");
			
			String processType = frmClaimGeneral.getString("processType");
			String claimType = frmClaimGeneral.getString("claimType");
			String NetWrkType = frmClaimGeneral.getString("networkProviderType");
			if("DBL".equals(processType) && "CTM".equals(claimType) ){
				
				BigDecimal convertedAmt = activityDetailsVO.getConvertedProviderReqAmt();
				
				activityDetailsVO.setVatAddedReqAmnt(convertedAmt.doubleValue());
				
			}
			else if("GBL".equals(processType) && "CTM".equals(claimType) && "N".equals(NetWrkType)){
				
				String providerCountry = frmClaimGeneral.getString("providerCountry");
				
				if(providerCountry != "123"){
					
					BigDecimal convertedAmt = activityDetailsVO.getConvertedProviderReqAmt();
					
					activityDetailsVO.setVatAddedReqAmnt(convertedAmt.doubleValue());
					
				}
				
			}
			
			

			activityDetailsVO.setOverrideYN(overrideYN);
			
			if ("Y".equals(overrideYN)) {
				activityDetailsVO.setDenialCode(null);
				activityDetailsVO.setDenialDescription(null);

				LinkedHashMap<String, String> activityDenialList = (LinkedHashMap<String, String>) request.getSession().getAttribute("activityDenialList");
				if (activityDenialList != null && activityDenialList.size() > 0) {
					StringBuilder dcsBuilder = new StringBuilder();
					StringBuilder dcdsBuilder = new StringBuilder();

					Set<Entry<String, String>> set = activityDenialList
							.entrySet();
					for (Entry<String, String> entr : set) {
					 dcsBuilder.append(entr.getKey().trim());
					 dcsBuilder.append(";");
					 dcdsBuilder.append(entr.getValue().trim());
					 dcdsBuilder.append(";");
				 }

					activityDetailsVO.setDenialCode(dcsBuilder.toString());
					activityDetailsVO.setDenialDescription(dcdsBuilder
							.toString());

			}
			}
			preAuthObject.saveActivityDetails(activityDetailsVO);
			successMsg = (activityDetailsVO.getActivityDtlSeqId() == null || activityDetailsVO
					.getActivityDtlSeqId() == 0) ? "Activity Details Added Successffully"
					: "Activity Details Updated Successffully";

			String claimNo = frmActivityDetails.getString("claimNo");
			String claimSeqID = frmActivityDetails.getString("claimSeqID");
			String activityStartDate = frmActivityDetails
					.getString("activityStartDate");
			String clinicianId = frmActivityDetails.getString("clinicianId");
			String clinicianName = frmActivityDetails
					.getString("clinicianName");
			String networkProviderType = frmActivityDetails
					.getString("networkProviderType");
			// String overrideYN=frmActivityDetails.getString("overrideYN");
			frmActivityDetails.initialize(mapping);

			frmActivityDetails.set("claimNo", claimNo);
			frmActivityDetails.set("claimSeqID", claimSeqID);
			frmActivityDetails.set("clinicianId", clinicianId);
			frmActivityDetails.set("clinicianName", clinicianName);
			// frmActivityDetails.set("overrideYN", overrideYN);
			frmActivityDetails.set("networkProviderType", networkProviderType);
			// frmActivityDetails.set("activityDtlSeqId", activityDtlSeqId);
			frmActivityDetails.set("activityStartDate", activityStartDate);// 1,2--NON-edit
			frmActivityDetails.set("amountAllowed", "Y");// Amount Allowed
															// default Checked
			request.getSession().setAttribute("frmActivityDetails",
					frmActivityDetails);
			request.getSession().setAttribute("activityDenialList", null);
			request.setAttribute("successMsg", successMsg);
			request.getSession().setAttribute("ceedResponse", null);	
			request.getSession().setAttribute("claimEditNode", null);	
			return this.getForward(strActivitydetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strActivityDetails));
		}// end of catch(Exception exp)
	}// end of saveActivityDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward addDenialDesc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
		setLinks(request);
		log.debug("Inside ClaimGenealAction addDenialDesc");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			String denialCode = request.getParameter("denialCode");
			denialCode = (denialCode == null) ? "" : denialCode;
			String denialDesc = request.getParameter("denialDescription");
			denialDesc = (denialDesc == null) ? "" : denialDesc;
			HttpSession session = request.getSession();
			LinkedHashMap<String, String> activityDenialList = (LinkedHashMap<String, String>) session
					.getAttribute("activityDenialList");
			if (activityDenialList == null) {
				activityDenialList = new LinkedHashMap<String, String>();
		}
			activityDenialList.put(denialCode, denialDesc);
			frmActivityDetails.set("denialCode", "");
			frmActivityDetails.set("denialDescription", "");
			session.setAttribute("frmActivityDetails", frmActivityDetails);
			session.setAttribute("activityDenialList", activityDenialList);
			request.setAttribute("successMsg", "Added Successfully");
			return this.getForward(strActivitydetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strActivitydetails));
		}// end of catch(Exception exp)
	}// end of addDenialDesc(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward deleteDenialDesc(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
		setLinks(request);
		log.debug("Inside ClaimGenealAction deleteDenialDesc");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			String denialCode = request.getParameter("denialCode");

			HttpSession session = request.getSession();
			LinkedHashMap<String, String> activityDenialList = (LinkedHashMap<String, String>) session
					.getAttribute("activityDenialList");
			if (activityDenialList != null) {
			activityDenialList.remove(denialCode);
			session.setAttribute("activityDenialList", activityDenialList);
		}
		request.setAttribute("successMsg", "Deleted Successfully");
		frmActivityDetails.set("denialCode", "");
		frmActivityDetails.set("denialDescription", "");
		session.setAttribute("frmActivityDetails", frmActivityDetails);
			return this.getForward(strActivitydetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strActivitydetails));
		}// end of catch(Exception exp)
	}// end of deleteDenialDesc(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward resetActivityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
		setLinks(request);
		log.debug("Inside ClaimGenealAction resetActivityDetails");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			String claimNo = frmActivityDetails.getString("claimNo");
			String claimSeqID = frmActivityDetails.getString("claimSeqID");
			String activityStartDate = frmActivityDetails
					.getString("activityStartDate");
			String clinicianId = frmActivityDetails.getString("clinicianId");
			String clinicianName = frmActivityDetails
					.getString("clinicianName");
			String networkProviderType = frmActivityDetails
					.getString("networkProviderType");
			// String
			// activityDtlSeqId=frmActivityDetails.getString("activityDtlSeqId");
			frmActivityDetails.initialize(mapping);
			frmActivityDetails.set("claimNo", claimNo);
			frmActivityDetails.set("claimSeqID", claimSeqID);
			// frmActivityDetails.set("activityDtlSeqId", activityDtlSeqId);
			frmActivityDetails.set("activityStartDate", activityStartDate);// 1,2--NON-edit
			frmActivityDetails.set("clinicianId", clinicianId);
			frmActivityDetails.set("networkProviderType", networkProviderType);
			frmActivityDetails.set("clinicianName", clinicianName);
			frmActivityDetails.set("amountAllowed", "Y");// Amount Allowed
															// default Checked
			request.getSession().setAttribute("frmActivityDetails",
					frmActivityDetails);
			return this.getForward(strActivitydetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strActivityDetails));
		}// end of catch(Exception exp)
	}// end of resetActivityDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward observWindow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGeneralAction addObserv ");
			String preAuthSeqID = (String) request.getParameter("pstatussi");
			String activityDtlSeqId = (String) request
					.getParameter("activityDtlSeqId");
			DynaActionForm frmObservDetails = (DynaActionForm) form;
	frmObservDetails.initialize(mapping);
	frmObservDetails.set("activityDtlSeqId", activityDtlSeqId);
	frmObservDetails.set("preAuthSeqID", preAuthSeqID);
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			ArrayList<String[]> observations = preAuthObject
					.getAllObservDetails(new Long(activityDtlSeqId));
			request.getSession().setAttribute("observations", observations);
	return mapping.findForward("addObservations");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)

	}// end of addObserv(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward editObserDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGeneralAction editObserDetails ");
			String observSeqId = (String) request.getParameter("observSeqId");
			DynaActionForm frmObservDetails = (DynaActionForm) form;
			String preAuthSeqID = frmObservDetails.get("preAuthSeqID")
					.toString();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			ObservationDetailsVO observationDetailsVO = preAuthObject
					.getObservDetail(new Long(observSeqId));
	observationDetailsVO.setPreAuthSeqID(new Long(preAuthSeqID));
			frmObservDetails = (DynaActionForm) FormUtils.setFormValues(
					"frmObservDetails", observationDetailsVO, this, mapping,
					request);

			request.getSession().setAttribute("frmObservDetails",
					frmObservDetails);
	return mapping.findForward("addObservations");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)

	}// end of editObserDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward closeActivities(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGeneralAction closeActivities ");
			DynaActionForm frmActivityDetails = (DynaActionForm) form;
			// frmActivityDetails.set("preAuthSeqID", preAuthSeqID);
			String preAuthNo = frmActivityDetails.get("preAuthNo") == null ? ""
					: frmActivityDetails.get("preAuthNo").toString();
			String preAuthSeqID = frmActivityDetails.get("preAuthSeqID") == null ? "0"
					: frmActivityDetails.get("preAuthSeqID").toString();
			request.setAttribute("preAuthNo", preAuthNo);
			request.setAttribute("preAuthSeqID", preAuthSeqID);
	return mapping.findForward("preauthdetail");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)

	}// end of closeActivities(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward getMemberDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGeneralAction getMemberDetails ");
			ClaimManager claimObject = this.getClaimManagerObject();
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			String memberId = frmClaimGeneral.getString("memberId");
			memberId = (memberId == null) ? "" : memberId.trim();
			PreAuthDetailVO preAuthDetailVO = claimObject
					.getMemberDetails(memberId);
			
			
			
			//frmClaimGeneral.set("preAuthSeqID", "");
			//frmClaimGeneral.set("authNum", "");
			if (preAuthDetailVO == null) {
				request.setAttribute("errorMsg", "Given Member ID Is Invalid");
				frmClaimGeneral.set("memberSeqID", "");
				frmClaimGeneral.set("patientName", "");
				frmClaimGeneral.set("memberAge", "");
				frmClaimGeneral.set("emirateId", "");
				frmClaimGeneral.set("payerId", "");
				frmClaimGeneral.set("insSeqId", "");
				frmClaimGeneral.set("policySeqId", "");
				frmClaimGeneral.set("payerName", "");
				frmClaimGeneral.set("patientGender", "");
				frmClaimGeneral.set("policyNumber", "");
				frmClaimGeneral.set("corporateName", "");
				frmClaimGeneral.set("policyStartDate", "");
				frmClaimGeneral.set("policyEndDate", "");
				frmClaimGeneral.set("nationality", "");
				frmClaimGeneral.set("sumInsured", "");
				frmClaimGeneral.set("availableSumInsured", "");
				frmClaimGeneral.set("policyType", "");
  frmClaimGeneral.set("emirateId",""); 
				frmClaimGeneral.set("productName", "");
				frmClaimGeneral.set("payerAuthority", "");
frmClaimGeneral.set("vipYorN","");
frmClaimGeneral.set("clmMemInceptionDate","");
				frmClaimGeneral.set("memCoveredAlert", "Patient is not a covered member"); 
				
  }
			
			
			
			
			
			
			else{
	  request.setAttribute("successMsg","Member ID Is Valid");

				frmClaimGeneral.set("memberIDValidYN", "Y");
				frmClaimGeneral.set("memberSeqID",
						TTKCommon.checkNull(preAuthDetailVO.getMemberSeqID())
								.toString());
				frmClaimGeneral.set("patientName",
						preAuthDetailVO.getClaimantName());
				frmClaimGeneral.set("memberAge",
						TTKCommon.checkNull(preAuthDetailVO.getMemberAge())
								.toString());
				frmClaimGeneral	.set("emirateId", preAuthDetailVO.getEmirateId());
				frmClaimGeneral.set("payerId", preAuthDetailVO.getPayerId());
				frmClaimGeneral.set("insSeqId",
						TTKCommon.checkNull(preAuthDetailVO.getInsSeqId())
								.toString());
				frmClaimGeneral.set("policySeqId",
						TTKCommon.checkNull(preAuthDetailVO.getPolicySeqId())
								.toString());
				frmClaimGeneral
						.set("payerName", preAuthDetailVO.getPayerName());
				frmClaimGeneral.set("patientGender",
						preAuthDetailVO.getPatientGender());
				frmClaimGeneral.set("policyNumber",
						preAuthDetailVO.getPolicyNumber());
				frmClaimGeneral.set("corporateName",
						preAuthDetailVO.getCorporateName());
				frmClaimGeneral.set("policyStartDate",
						preAuthDetailVO.getPolicyStartDate());
				frmClaimGeneral.set("policyEndDate",
						preAuthDetailVO.getPolicyEndDate());
				frmClaimGeneral.set("nationality",
						preAuthDetailVO.getNationality());
				frmClaimGeneral.set("sumInsured",
						TTKCommon.checkNull(preAuthDetailVO.getSumInsured())
								.toString());
				frmClaimGeneral.set(
						"availableSumInsured",
						TTKCommon.checkNull(
								preAuthDetailVO.getAvailableSumInsured())
								.toString());
				frmClaimGeneral.set("policyType",
						preAuthDetailVO.getPolicyType());
				frmClaimGeneral.set("productName",preAuthDetailVO.getProductName());
				frmClaimGeneral.set("payerAuthority", preAuthDetailVO.getPayerAuthority());
				frmClaimGeneral.set("vipYorN",preAuthDetailVO.getVipYorN());
				frmClaimGeneral.set("policyCategory",preAuthDetailVO.getPolicyCategory());
	  
	 
				frmClaimGeneral.set("clmMemInceptionDate",preAuthDetailVO.getClmMemInceptionDate());
	  frmClaimGeneral.set("memCoveredAlert", ""); 
  }
			request.getSession().setAttribute("frmClaimGeneral",
					frmClaimGeneral);
			return mapping.findForward("claimdetails");
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// getMemberDetails

	public ActionForward calculateClaimAmount(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			long calcCeedStartTime = System.currentTimeMillis();
			long ceedStartTime = System.currentTimeMillis();
			//this.doCeedValidation(mapping, form, request, response);
			long totCeedTime = System.currentTimeMillis() - ceedStartTime;
			
			log.debug("Inside ClaimGenealAction calculateClaimAmount ");
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			HttpSession session = request.getSession();
			String claimSeqID = (String) frmClaimGeneral.get("claimSeqID");
			String hospitalSeqID = (String) frmClaimGeneral
					.get("providerSeqId");
			
			hospitalSeqID = (hospitalSeqID == null || hospitalSeqID.length() < 1) ? "0"
					: hospitalSeqID;
			StringBuffer strCaption = new StringBuffer();
			PreAuthDetailVO preAuthDetailVO = null;
			ClaimManager claimObject = this.getClaimManagerObject();
			
			
			// calculate amounts
				claimObject.calculateClaimAmount(new Long(claimSeqID), new Long(hospitalSeqID), TTKCommon.getUserSeqId(request));
			
			
			preAuthDetailVO= claimObject.getClaimDetails(new Long(claimSeqID));
			frmClaimGeneral=setFormValues(preAuthDetailVO, mapping, request);
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmClaimGeneral))
				frmClaimGeneral.set("memberwtflag","Y");
			}
			session.setAttribute("frmClaimGeneral", frmClaimGeneral);
			
	if("ECL".equals(preAuthDetailVO.getModeOfClaim()) &&((preAuthDetailVO.getCount_flag())>1) ){
				
				
				request.setAttribute("errorMsg","Invalid Member Id: More than one member found for the searched Member id. Please select any one member id from the search results.");
				frmClaimGeneral.set("memberSeqID","");
			//  frmClaimGeneral.set("memberId","");
				frmClaimGeneral.set("patientName", "");
				frmClaimGeneral.set("memberAge","");
			//	frmClaimGeneral.set("emirateId", "");
				frmClaimGeneral.set("payerName", "");
				frmClaimGeneral.set("payerId", "");
				frmClaimGeneral.set("insSeqId","");
				frmClaimGeneral.set("policySeqId", "");
				frmClaimGeneral.set("patientGender", "");
				frmClaimGeneral.set("policyNumber", "");
				frmClaimGeneral.set("corporateName","");
				frmClaimGeneral.set("policyStartDate", "");
				frmClaimGeneral.set("policyEndDate", "");
				frmClaimGeneral.set("nationality", "");
				frmClaimGeneral.set("sumInsured","");
				frmClaimGeneral.set("availableSumInsured","");
				frmClaimGeneral.set("vipYorN","");
				frmClaimGeneral.set("preMemInceptionDt","");
				frmClaimGeneral.set("productName","");
				frmClaimGeneral.set("eligibleNetworks","");
				frmClaimGeneral.set("payerAuthority","");
				frmClaimGeneral.set("patient_id","");//ADD NEW FIELD PATIENT_ID
				
			}
			
			else if("ECL".equals(preAuthDetailVO.getModeOfClaim()) && preAuthDetailVO.getClaimNo()!=null && preAuthDetailVO.getClaimNo()!="" && preAuthDetailVO.getCount_flag()==0)
			{
				
				if(preAuthDetailVO.getCount_flag2()==1)
				{
					
					frmClaimGeneral = (DynaActionForm) FormUtils.setFormValues("frmClaimGeneral", preAuthDetailVO, this, mapping, request);
				}
				
				if(preAuthDetailVO.getCount_flag2()==0)
				{
					
					request.setAttribute("errorMsg", "Invalid Member id / Emirate id: Member Id/Emirate Id not found.");
					frmClaimGeneral.set("memberSeqID","");
				//	frmClaimGeneral.set("memberId","");
					frmClaimGeneral.set("patientName", "");
					frmClaimGeneral.set("memberAge","");
				//	frmClaimGeneral.set("emirateId", "");
					frmClaimGeneral.set("payerName", "");
					frmClaimGeneral.set("payerId", "");
					frmClaimGeneral.set("insSeqId","");
					frmClaimGeneral.set("policySeqId", "");
					frmClaimGeneral.set("patientGender", "");
					frmClaimGeneral.set("policyNumber", "");
					frmClaimGeneral.set("corporateName","");
					frmClaimGeneral.set("policyStartDate", "");
					frmClaimGeneral.set("policyEndDate", "");
					frmClaimGeneral.set("nationality", "");
					frmClaimGeneral.set("sumInsured",""); 
					frmClaimGeneral.set("availableSumInsured","");
					frmClaimGeneral.set("vipYorN","");
					frmClaimGeneral.set("preMemInceptionDt","");
					frmClaimGeneral.set("productName","");
					frmClaimGeneral.set("eligibleNetworks","");
					frmClaimGeneral.set("payerAuthority","");
					
					frmClaimGeneral.set("patient_id","");//ADD NEW FIELD PATIENT_ID
				}
				
				if(preAuthDetailVO.getCount_flag2()>1)
				{
					
					request.setAttribute("errorMsg","Invalid Emirate Id: More than one member found for the searched Emirate Id. Please select any one Emirate id from the search results.");
					frmClaimGeneral.set("memberSeqID","");
					frmClaimGeneral.set("memberId","");
					frmClaimGeneral.set("patientName", "");
					frmClaimGeneral.set("memberAge","");
				//	frmClaimGeneral.set("emirateId", "");
					frmClaimGeneral.set("payerName", "");
					frmClaimGeneral.set("payerId", "");
					frmClaimGeneral.set("insSeqId","");
					frmClaimGeneral.set("policySeqId", "");
					frmClaimGeneral.set("patientGender", "");
					frmClaimGeneral.set("policyNumber", "");
					frmClaimGeneral.set("corporateName","");
					frmClaimGeneral.set("policyStartDate", "");
					frmClaimGeneral.set("policyEndDate", "");
					frmClaimGeneral.set("nationality", "");
					frmClaimGeneral.set("sumInsured","");
					frmClaimGeneral.set("availableSumInsured","");
					frmClaimGeneral.set("vipYorN","");
					frmClaimGeneral.set("preMemInceptionDt","");
					frmClaimGeneral.set("productName","");
					frmClaimGeneral.set("eligibleNetworks","");
					frmClaimGeneral.set("payerAuthority","");
					frmClaimGeneral.set("patient_id","");//ADD NEW FIELD PATIENT_ID
				}
				
			}
			
			else if(preAuthDetailVO.getClaimNo()!=null && preAuthDetailVO.getClaimNo()!="" && preAuthDetailVO.getCount_flag()==0){
				
				request.setAttribute("errorMsg", "Invalid Member ID: Member ID not found.");
				frmClaimGeneral.set("memberSeqID","");
				//frmClaimGeneral.set("memberId","");
				frmClaimGeneral.set("patientName", "");
				frmClaimGeneral.set("memberAge","");
				frmClaimGeneral.set("emirateId", "");
				frmClaimGeneral.set("payerName", "");
				frmClaimGeneral.set("payerId", "");
				frmClaimGeneral.set("insSeqId","");
				frmClaimGeneral.set("policySeqId", "");
				frmClaimGeneral.set("patientGender", "");
				frmClaimGeneral.set("policyNumber", "");
				frmClaimGeneral.set("corporateName","");
				frmClaimGeneral.set("policyStartDate", "");
				frmClaimGeneral.set("policyEndDate", "");
				frmClaimGeneral.set("nationality", "");
				frmClaimGeneral.set("sumInsured",""); 
				frmClaimGeneral.set("availableSumInsured","");
				frmClaimGeneral.set("vipYorN","");
				frmClaimGeneral.set("preMemInceptionDt","");
				frmClaimGeneral.set("productName","");
				frmClaimGeneral.set("eligibleNetworks","");
				frmClaimGeneral.set("payerAuthority","");
				
				frmClaimGeneral.set("patient_id","");//ADD NEW FIELD PATIENT_ID
			}
			/*
			 * CEED VALIDATION S T A R T S HERE
			 */
			
			
			
			/*
			 * CEED VALIDATION E N D S HERE
			 */
			
			this.addToWebBoard(preAuthDetailVO, request, "CLM");

			strCaption.append(" Edit");
			strCaption.append(" [ " + frmClaimGeneral.getString("patientName")
					+ " ]");
			strCaption.append(" [ " + frmClaimGeneral.getString("memberId")
					+ " ]");
			frmClaimGeneral.set("caption", strCaption.toString());
			request.setAttribute("successMsg", "Record updated successfully!");
		
		//	this.doCeedValidation(mapping, form, request, response);
			
			long totCeedCalcTime = System.currentTimeMillis() - calcCeedStartTime;
			long ceedCalcTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(totCeedCalcTime);
			long CeedCalcSec = ceedCalcTimeInSeconds % 60;
			long CeedCalcMin = ceedCalcTimeInSeconds / 60;
			log.info("Ceed + Calc  Response Time in seconds : "+ ceedCalcTimeInSeconds);
			
			long ceedTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(totCeedTime);
			long CeedSec = ceedTimeInSeconds % 60;
			long CeedMin = ceedTimeInSeconds / 60;
			log.info("Ceed Response Time in seconds  : "+ ceedTimeInSeconds);
			String claimNo = TTKCommon.checkNull(frmClaimGeneral.getString("claimNo"));
			log.info("---------------------------------------------------------------------------");
			log.info("claimNo               :            "+ claimNo);
			log.info("---------------------------------------------------------------------------");
			log.info("");
			log.info("ceed response         : "+"    min:"+CeedMin+    "   sec:"+CeedSec);
			log.info("");
			log.info("");
			log.info("cal + ceed response   : "+"    min:"+CeedCalcMin+"   sec:"+CeedCalcSec);
			log.info("");
			log.info("----------------------------------------------------------------------------");
			return this.getForward(strClaimdetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of calculateClaimAmount(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward saveAndCompleteClaim(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGenealAction saveAndCompleteClaim");
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;

			String claimStatus = frmClaimGeneral.getString("claimStatus");
			String approvedAmount = frmClaimGeneral.getString("approvedAmount");
			
			
			
			approvedAmount = (approvedAmount == null || approvedAmount.length() < 1) ? "0"
					: approvedAmount;

			if ("APR".equals(claimStatus)
					&& (new Double(approvedAmount).doubleValue() <= 0)) {

				request.setAttribute("errorMsg",
						"Approved Amount Should Be Greater Than Zero");
	return this.getForward(strClaimdetails, mapping, request);
			}
			PreAuthDetailVO preAuthDetailVO = new PreAuthDetailVO();
			preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(frmClaimGeneral, this, mapping, request);
			ClaimManager claimObject = this.getClaimManagerObject();
			StringBuffer strCaption = new StringBuffer();
			HttpSession session = request.getSession();
	preAuthDetailVO.setAddedBy(TTKCommon.getUserSeqId(request));
	preAuthDetailVO.setDenialCode(request.getParameter("denialcode") );
	claimObject.saveAndCompleteClaim(preAuthDetailVO);

	  preAuthDetailVO = claimObject.getClaimDetails(new Long(frmClaimGeneral.getString("claimSeqID")));
	  frmClaimGeneral=setFormValues(preAuthDetailVO, mapping, request);
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmClaimGeneral))
				frmClaimGeneral.set("memberwtflag","Y");
			}
				session.setAttribute("frmClaimGeneral", frmClaimGeneral);

			this.addToWebBoard(preAuthDetailVO, request, "CLM");
			String memberwarnig;
            String exitdate = frmClaimGeneral.getString("exitdate");


            if(exitdate==null||exitdate==""){

                 memberwarnig=preAuthDetailVO.getMemActiveStatus();

                 session.setAttribute("warningmessage", memberwarnig);
            }
            else{

                 memberwarnig=preAuthDetailVO.getMemActiveStatus();

                 session.setAttribute("warningmessage", 
                		 memberwarnig+exitdate);
            }
			
			
				strCaption.append(" Edit");
			strCaption.append(" [ " + frmClaimGeneral.getString("patientName")
					+ " ]");
			strCaption.append(" [ " + frmClaimGeneral.getString("memberId")
					+ " ]");
			frmClaimGeneral.set("caption", strCaption.toString());
			request.setAttribute("successMsg", "Claim completed successfully!");
			

			request.setAttribute("JS_Focus_ID","SAVEBTNIDFORCLAIM");
			return this.getForward(strClaimdetails, mapping, request);

		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of saveAndCompleteClaim(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward deleteShortfallsDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGenealAction deleteShortfallsDetails ");
			PreAuthDetailVO preAuthDetailVO = null;
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			ClaimManager claimObject = this.getClaimManagerObject();
			HttpSession session = request.getSession();
			StringBuffer strCaption = new StringBuffer();
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			String claimSeqID = (String) frmClaimGeneral.get("claimSeqID");
			
			String shortFallSeqId = (String) frmClaimGeneral.get("shortFallSeqId");

			preAuthObject.deleteShortfallsDetails(new String[] { claimSeqID,shortFallSeqId, "CLM" });
			request.setAttribute("successMsg","Shortfalls Details Deleted Successfully");

			preAuthDetailVO = claimObject.getClaimDetails(new Long(frmClaimGeneral.getString("claimSeqID")));
			frmClaimGeneral=setFormValues(preAuthDetailVO, mapping, request);
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmClaimGeneral))
				frmClaimGeneral.set("memberwtflag","Y");
			}
			
			
				session.setAttribute("frmClaimGeneral", frmClaimGeneral);

			this.addToWebBoard(preAuthDetailVO, request, "CLM");

				strCaption.append(" Edit");
			strCaption.append(" [ " + frmClaimGeneral.getString("patientName")
					+ " ]");
			strCaption.append(" [ " + frmClaimGeneral.getString("memberId")
					+ " ]");
			frmClaimGeneral.set("caption", strCaption.toString());
		return this.getForward(strClaimdetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of deleteShortfallsDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to add/update the record. Finally it forwards to the
	 * appropriate view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
 */
	
	public ActionForward generateClaimLetterMember(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			String claimSeqID = request.getParameter("claimSeqID");
			String claimSettelmentNo = request.getParameter("claimSettelmentNo");
			String claimStatus =  request.getParameter("claimStatus");
			String Format=request.getParameter("format");
			
			
			String claimType=request.getParameter("claimType");
			String processType=request.getParameter("processType");
			String networkProviderType=request.getParameter("networkProviderType");
			String providerCountry=request.getParameter("providerCountry");
			
			JasperReport mainJasperReport = null;
			JasperReport diagnosisJasperReport = null;
			JasperReport activityJasperReport = null;
			JasperPrint mainJasperPrint = null;
		String parameter = "";
		String mainJrxmlfile="";
		String activityJrxmlfile="";
		String strPdfFile="";
		//String diagnosisJrxmlfile="";
		TTKReportDataSource mainTtkReportDataSource = null;
		TTKReportDataSource diagnosisTtkReportDataSource = null;
		TTKReportDataSource activityTtkReportDataSource = null;
		 HashMap<String, Object> hashMap = new HashMap<String, Object>();
		parameter="|"+claimSeqID+"|"+claimStatus+"|CLM|";
		ByteArrayOutputStream boas = new ByteArrayOutputStream();
		
			
			if("GENERAL".equalsIgnoreCase(Format) ||"ASCANA".equalsIgnoreCase(Format))
			{
				 mainJrxmlfile = "generalreports/MemberClaimApprovalOrDenialLetter.jrxml";
				 
				 
				 
				 if("GBL".equals(processType) && "CTM".equals(claimType) && "123".equals(providerCountry)){
					 activityJrxmlfile = "generalreports/MemberClaimActivitiesVAT.jrxml";
				 }
				 else{ 
					 activityJrxmlfile = "generalreports/MemberClaimActivitiesDoc.jrxml";
				 }
				 
				 
			
			 
			 
			 
			 
			 mainTtkReportDataSource=new TTKReportDataSource("MemberClaimLetterFormat",parameter);
			 String diagnosisJrxmlfile = "generalreports/ClaimDiagnosisDoc.jrxml";
			 

			 diagnosisJrxmlfile = "generalreports/ClaimDiagnosisDoc.jrxml";
			 

				 boas = new ByteArrayOutputStream();

				 strPdfFile = TTKPropertiesReader
						.getPropertyValue("authorizationrptdir")
						+ claimSettelmentNo + ".pdf";
				JasperReport emptyReport = JasperCompileManager
						.compileReport("generalreports/EmptyReprot.jrxml");
				//mainJasperReport = JasperCompileManager.compileReport(mainJrxmlfile);
		/* HashMap<String, Object> hashMap = new HashMap<String, Object>();*/
			
			
			diagnosisTtkReportDataSource = new TTKReportDataSource("DiagnosisDetails",parameter);  		
			activityTtkReportDataSource = new TTKReportDataSource("ActivityDetails",parameter);

			ResultSet main_report_RS=mainTtkReportDataSource.getResultData();
			mainJasperReport = JasperCompileManager.compileReport(mainJrxmlfile);
			diagnosisJasperReport = JasperCompileManager.compileReport(diagnosisJrxmlfile);
			activityJasperReport = JasperCompileManager.compileReport(activityJrxmlfile);			  
			
				 hashMap.put("diagnosisDataSource",diagnosisTtkReportDataSource);
				 hashMap.put("diagnosis",diagnosisJasperReport);		
				 hashMap.put("activityDataSource",activityTtkReportDataSource);		
				 hashMap.put("activity",activityJasperReport);
				 //JasperFillManager.fillReport(activityJasperReport, hashMap, activityTtkReportDataSource);						 
		 
		 if (main_report_RS == null&!main_report_RS.next())
		 { 
			 mainJasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
		 }
		 else
		 { 
		 hashMap.put("final_remarks", main_report_RS.getString("final_remarks"));
			 main_report_RS.beforeFirst();
			 
					mainJasperPrint = JasperFillManager.fillReport(
							mainJasperReport, hashMap, mainTtkReportDataSource);
				}// end of else
				JasperExportManager.exportReportToPdfStream(mainJasperPrint, boas);
				//JasperExportManager.exportReportToPdfStream(mainJasperPrint, boas);
				JasperExportManager.exportReportToPdfFile(mainJasperPrint,strPdfFile);
			}
			else
			{
				switch (Format) {
				case "TAKAFUL":
					 mainJrxmlfile = "generalreports/ClaimTakafulLetter.jrxml";
					break;
				case "NLGI":
					 mainJrxmlfile = "generalreports/ClaimNlgiLetter.jrxml";
					break;
				case "OMAN":
					 mainJrxmlfile = "generalreports/ClaimOmanLetter.jrxml";
					break;

				default:
					break;
				}
				
			
			 mainTtkReportDataSource=new TTKReportDataSource("TakafulLetter",parameter);
			 
			 boas = new ByteArrayOutputStream();

			 strPdfFile = TTKPropertiesReader
					.getPropertyValue("authorizationrptdir")
					+ claimSettelmentNo + ".pdf";
			JasperReport emptyReport = JasperCompileManager
					.compileReport("generalreports/EmptyReprot.jrxml");
			
		ResultSet main_report_RS=mainTtkReportDataSource.getResultData();
		
		mainJasperReport = JasperCompileManager.compileReport(mainJrxmlfile);
		
	 
	 if (main_report_RS == null&!main_report_RS.next())
	 {
		 mainJasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
	 }
	 else
	 {
		 main_report_RS.beforeFirst();

				mainJasperPrint = JasperFillManager.fillReport(
						mainJasperReport, hashMap, mainTtkReportDataSource);
			}// end of else
			JasperExportManager.exportReportToPdfStream(mainJasperPrint, boas);
			//JasperExportManager.exportReportToPdfStream(mainJasperPrint, boas);
			JasperExportManager.exportReportToPdfFile(mainJasperPrint,
					strPdfFile);
			 
			}
			
	 frmClaimGeneral.set("letterPath", strPdfFile);
			request.setAttribute("boas", boas);
			return mapping.findForward("reportdisplay");// This forward goes to
														// the in web.xml file
														// BinaryServlet
		}// end of try
		catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of generateClaimLetter(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)
	
	
	
	
	
	
	
	
	
	public ActionForward generateClaimLetter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
		setLinks(request);
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			
			String claimSeqID = frmClaimGeneral.getString("claimSeqID");
			String claimSettelmentNo = frmClaimGeneral
					.getString("claimSettelmentNo");
			String claimStatus = frmClaimGeneral.getString("claimStatus");
			JasperReport mainJasperReport = null;
			JasperReport diagnosisJasperReport = null;
			JasperReport activityJasperReport = null;
			JasperPrint mainJasperPrint = null;
		String parameter = "";
		String mainJrxmlfile="";
		String activityJrxmlfile="";
		TTKReportDataSource mainTtkReportDataSource = null;
			TTKReportDataSource diagnosisTtkReportDataSource = null;
			TTKReportDataSource activityTtkReportDataSource = null;
		parameter="|"+claimSeqID+"|"+claimStatus+"|CLM|";
				 
			 mainJrxmlfile = "generalreports/ClaimApprovalOrDenialLetter.jrxml";
			 activityJrxmlfile = "generalreports/ClaimActivitiesDoc.jrxml";
			 mainTtkReportDataSource=new TTKReportDataSource("ClaimLetterFormat",parameter);
			
		 
		String diagnosisJrxmlfile = "generalreports/ClaimDiagnosisDoc.jrxml";
		 

			ByteArrayOutputStream boas = new ByteArrayOutputStream();

			String strPdfFile = TTKPropertiesReader
					.getPropertyValue("authorizationrptdir")
					+ claimSettelmentNo + ".pdf";
			JasperReport emptyReport = JasperCompileManager
					.compileReport("generalreports/EmptyReprot.jrxml");
			//mainJasperReport = JasperCompileManager.compileReport(mainJrxmlfile);
	 HashMap<String, Object> hashMap = new HashMap<String, Object>();
		
		
		diagnosisTtkReportDataSource = new TTKReportDataSource("DiagnosisDetails",parameter);  		
		activityTtkReportDataSource = new TTKReportDataSource("ActivityDetails",parameter);

		ResultSet main_report_RS=mainTtkReportDataSource.getResultData();
		
		mainJasperReport = JasperCompileManager.compileReport(mainJrxmlfile);
		diagnosisJasperReport = JasperCompileManager.compileReport(diagnosisJrxmlfile);
		activityJasperReport = JasperCompileManager.compileReport(activityJrxmlfile);			  
		
			 hashMap.put("diagnosisDataSource",diagnosisTtkReportDataSource);
			 hashMap.put("diagnosis",diagnosisJasperReport);		
			 hashMap.put("activityDataSource",activityTtkReportDataSource);		
			 hashMap.put("activity",activityJasperReport);
			 //JasperFillManager.fillReport(activityJasperReport, hashMap, activityTtkReportDataSource);						 
	 
	 if (main_report_RS == null&!main_report_RS.next())
	 {
		 mainJasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
	 }
	 else
	 {
		 main_report_RS.beforeFirst();
				mainJasperPrint = JasperFillManager.fillReport(
						mainJasperReport, hashMap, mainTtkReportDataSource);
			}// end of else
			JasperExportManager.exportReportToPdfStream(mainJasperPrint, boas);
			JasperExportManager.exportReportToPdfStream(mainJasperPrint, boas);
			JasperExportManager.exportReportToPdfFile(mainJasperPrint,
					strPdfFile);
	 frmClaimGeneral.set("letterPath", strPdfFile);
			request.setAttribute("boas", boas);
			return mapping.findForward("reportdisplay");// This forward goes to
														// the in web.xml file
														// BinaryServlet
		}// end of try
		catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of generateClaimLetter(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	/**
	 * This method is used to Send the Authorization Details generated Approval
	 * or Rejected letters to recipient. Finally it forwards to the appropriate
	 * view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
 */
	public ActionForward sendClaimLetter(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		try {
		setLinks(request);
		log.debug("inside ClaimGeneralAction sendClaimLetter");
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			String claimStatus = frmClaimGeneral.getString("claimStatus");
			String claimSettelmentNo = frmClaimGeneral
					.getString("claimSettelmentNo");
			String claimSeqID = frmClaimGeneral.getString("claimSeqID");
		String strIdentifier = "";
			CommunicationManager commManagerObject = this
					.getCommunicationManagerObject();

			if ("REJ".equals(claimStatus)) {
				strIdentifier = "CLAIM_MR_REJECTED";
			} else if ("APR".equals(claimStatus)) {
				strIdentifier = "CLAIM_MR_APPROVE";
			}

			// CommunicationOptionVO communicationOptionVO = null;
		Long lngUserID = TTKCommon.getUserSeqId(request);
			String strAuthpdf = TTKPropertiesReader
					.getPropertyValue("authorizationrptdir")
					+ claimSettelmentNo + ".pdf";
			// String[] strCommArray = {"SMS","EMAIL","FAX"};
		File file = new File(strAuthpdf);

			if (file.exists()) {
				/*commManagerObject.sendAuthorization(
						new Long(claimSeqID).longValue(), strIdentifier,
						lngUserID);*/
				request.setAttribute("successMsg", "Letter Sent Successfully");
			}// end of if(file.exists())
			else {
				request.setAttribute("errorMsg",
						"Please Generate The Letter Before Sending.");
				/*
				 * TTKException expTTK = new TTKException();
				 * expTTK.setMessage("error.authpdf"); throw expTTK;
				 */
			}// end of else
				// StringBuffer strCaption= new StringBuffer();
			request.setAttribute("JS_Focus_ID","savebtnid");
		return this.getForward(strClaimDetail, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of sendPreAuthLetter(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,
		// HttpServletResponse response)

	public ActionForward getAllClaimDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGenealAction getAllClaimDetails ");
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			Object objClaimSeqID = request.getAttribute("claimSeqID");
			String claimSeqID = objClaimSeqID == null ? "0" : objClaimSeqID
					.toString();
			StringBuffer strCaption = new StringBuffer();
			frmClaimGeneral.initialize(mapping);

			HttpSession session = request.getSession();
			PreAuthDetailVO preAuthDetailVO = null;
			ClaimManager claimObject = this.getClaimManagerObject();
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();

			preAuthDetailVO = claimObject.getClaimDetails(new Long(claimSeqID));
			frmClaimGeneral=setFormValues(preAuthDetailVO, mapping, request);
			if ("N".equals(frmClaimGeneral.getString("networkProviderType"))) {
				session.setAttribute("providerStates",
						TTKCommon.getStates(frmClaimGeneral
								.getString("providerCountry")));
				session.setAttribute("providerAreas", TTKCommon
						.getAreas(frmClaimGeneral.getString("providerEmirate")));
			}
			session.setAttribute("encounterTypes",
					preAuthObject.getEncounterTypes(frmClaimGeneral
							.getString("benefitType")));
			
			
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmClaimGeneral))
				frmClaimGeneral.set("memberwtflag","Y");
			}
			
			session.setAttribute("frmClaimGeneral", frmClaimGeneral);
			
			
			this.addToWebBoard(preAuthDetailVO, request, "CLM");

			strCaption.append(" Edit");
			strCaption.append(" [ " + frmClaimGeneral.getString("patientName")
					+ " ]");
			strCaption.append(" [ " + frmClaimGeneral.getString("memberId")
					+ " ]");
			frmClaimGeneral.set("caption", strCaption.toString());

			return this.getForward(strClaimdetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimdetails));
		}// end of catch(Exception exp)

	}// end of getAllClaimDetails(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward historyList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
	setLinks(request);
	log.debug("Inside ClaimGeneralAction historyList");
			HttpSession session = request.getSession();
			DynaActionForm frmHistoryList = (DynaActionForm) form;
			PreAuthManager preAuthObject = null;
			preAuthObject = this.getPreAuthManagerObject();

			if ("Y".equals(request.getParameter("Entry")))
				frmHistoryList.set("historyMode", "CLM");

			DynaActionForm frmClaimGeneral = (DynaActionForm) session
					.getAttribute("frmClaimGeneral");
			if (frmClaimGeneral == null
					|| frmClaimGeneral.getString("memberSeqID") == null
					|| frmClaimGeneral.getString("memberSeqID").length() < 1) {
				session.setAttribute("claimHistoryList", null);
			request.setAttribute("errorMsg", "Save Claim Details");
				return this.getForward(strCLaimHistoryList, mapping, request);// end
																				// of
																				// try
			}// end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
		// call the business layer to get the Pre-Auth detail
				// frmHistoryList.set("preAuthSeqID",
				// frmPreAuthGeneral.getString("preAuthSeqID"));
			ArrayList<String[]> authorizationList = preAuthObject
					.getPreauthHistoryList(
							new Long(frmClaimGeneral.getString("memberSeqID")),
							frmHistoryList.getString("historyMode"),null);

			session.setAttribute("claimHistoryList", authorizationList);
			session.setAttribute("frmHistoryList", frmHistoryList);
	return this.getForward(strCLaimHistoryList, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strCLaimHistoryList));
		}// end of catch(Exception exp)
	}// end of historyList(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	public ActionForward doViewHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGenealAction doViewHistory");
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			DynaActionForm frmHistoryList = (DynaActionForm) form;
			// check if user trying to hit the tab directly with out selecting
			// the hospital
			Document historyDoc = null;
			String authSeqID = request.getParameter("authSeqID");
			authSeqID = (authSeqID == null || authSeqID.length() < 1) ? "0"
					: authSeqID;
			String historyMode = frmHistoryList.getString("historyMode");
			if ("PAT".equals(historyMode)) {
				historyDoc = preAuthObject
						.getPreAuthHistory(new Long(authSeqID));
				request.setAttribute("preAuthHistoryDoc", historyDoc);
	return this.getForward(strPreAuthViewHistory, mapping, request);
			} else if ("CLM".equals(historyMode)) {
				historyDoc = preAuthObject.getClaimHistory(new Long(authSeqID));
				request.setAttribute("claimHistoryDoc", historyDoc);
	return this.getForward(strClaimViewHistory, mapping, request);
			}
			return this.getForward(strPreAuthViewHistory, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strCLaimHistoryList));
		}// end of catch(Exception exp)
	}// end of doViewHistory(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to Override. Finally it forwards to the appropriate
	 * view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward doOverride(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGeneralAction doOverride ");
			DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
			PreAuthDetailVO preAuthDetailVO = null;
			PreAuthManager preAuthObject = null;
			ClaimManager claimObject = null;
			String strActiveTab = TTKCommon.getActiveLink(request);
			String strDetail = "";
			if (strActiveTab.equals(strPre_Authorization)) {
				strDetail = "preauthdetail";
				preAuthObject = this.getPreAuthManagerObject();
				//	call the bussiness layer to override the completed PreAuth
				preAuthDetailVO = preAuthObject.overridePreauth(
						PreAuthWebBoardHelper.getPreAuthSeqId(request),
						PreAuthWebBoardHelper.getEnrollmentDetailId(request),
						TTKCommon.getUserSeqId(request));
			}// end of if(strActiveTab.equals(strPre_Authorization))
			if (strActiveTab.equals(strClaims)) {
				strDetail = "claimsdetail";
				claimObject = this.getClaimManagerObject();
				// call the bussiness layer to override the completed Claims
				preAuthDetailVO = claimObject.overrideClaim(
						ClaimsWebBoardHelper.getClaimsSeqId(request),
						TTKCommon.getUserSeqId(request));
			}// end of if(strActiveTab.equals(strClaims))

			if (preAuthDetailVO != null) {
				// set the review information back to form
				frmPreAuthGeneral.set("eventSeqID", preAuthDetailVO
						.getEventSeqID().toString());
				frmPreAuthGeneral.set("reviewCount", preAuthDetailVO
						.getReviewCount().toString());
				frmPreAuthGeneral.set("requiredReviewCnt", preAuthDetailVO
						.getRequiredReviewCnt().toString());
				frmPreAuthGeneral.set("review", preAuthDetailVO.getReview()
						.toString());
				frmPreAuthGeneral.set("eventName", preAuthDetailVO
						.getEventName().toString());

				// If Workflow is overrided set flag as N
				if (!(preAuthDetailVO.getEventName().contains("Complete"))) {
					frmPreAuthGeneral.set("showCodingOverrideYN", "Y");
					frmPreAuthGeneral.set("completedYN", "N");
				}// end of if("Complete".equals(preAuthDetailVO.getEventName()))
			}// end of if(preAuthDetailVO!=null)
			return this.getForward(strDetail, mapping, request);
		}// end of try

		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of doOverride(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to Override. Finally it forwards to the appropriate
	 * view based on the specified forward mappings
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this
	 *         request is processed
	 * @throws Exception
	 *             if any error occurs
	 */
	public ActionForward overridClaimDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGeneralAction overridClaimDetails");

			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			String overrideRemarks = frmClaimGeneral
					.getString("overrideRemarks");
			String claimSeqID = frmClaimGeneral.getString("claimSeqID");
			
			
			HttpSession session = request.getSession();
			PreAuthDetailVO preAuthDetailVO = null;
			ClaimManager claimObject = null;
				claimObject = this.getClaimManagerObject();
			long rowUpd = claimObject.overridClaimDetails(claimSeqID,
					overrideRemarks, TTKCommon.getUserSeqId(request));

			if (rowUpd >= 1)
				request.setAttribute("successMsg",
						"Claim Details Override Successfully");

			preAuthDetailVO = claimObject.getClaimDetails(new Long(frmClaimGeneral.getString("claimSeqID")));
			frmClaimGeneral=setFormValues(preAuthDetailVO, mapping, request);
			
			if(preAuthDetailVO.getMemberWeight()!=null)
			{
				if(memberAgeValidation(frmClaimGeneral))
				frmClaimGeneral.set("memberwtflag","Y");
			}
			request.setAttribute("JS_Focus_ID","SAVEBTNIDFORCLAIM");
				session.setAttribute("frmClaimGeneral", frmClaimGeneral);
				return this.getForward(strClaimDetail, mapping, request);
		}// end of try

		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimDetail));
		}// end of catch(Exception exp)
	}// end of doOverride(ActionMapping mapping,ActionForm
		// form,HttpServletRequest request,HttpServletResponse response)
	
	
	public ActionForward rejectClaim(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws TTKException
	{
		//ClaimManager claimObject=this.getClaimManagerObject();
		
		try {
			setLinks(request);
			log.debug("Inside ClaimGenealAction saveAndCompleteClaim");
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
            frmClaimGeneral.initialize(mapping);
			String claimStatus = frmClaimGeneral.getString("claimStatus");
			String approvedAmount = frmClaimGeneral.getString("approvedAmount");
			approvedAmount = (approvedAmount == null || approvedAmount.length() < 1) ? "0"
					: approvedAmount;

			if ("APR".equals(claimStatus)
					&& (new Double(approvedAmount).doubleValue() <= 0)) {

				request.setAttribute("errorMsg",
						"Approved Amount Should Be Greater Than Zero");
	return this.getForward(strClaimdetails, mapping, request);
			}
			PreAuthDetailVO preAuthDetailVO = new PreAuthDetailVO();
			preAuthDetailVO = (PreAuthDetailVO) FormUtils.getFormValues(
					frmClaimGeneral, this, mapping, request);
			ClaimManager claimObject = this.getClaimManagerObject();
			StringBuffer strCaption = new StringBuffer();
			HttpSession session = request.getSession();
	preAuthDetailVO.setAddedBy(TTKCommon.getUserSeqId(request));
	//claimObject.getRejectedClaimDetails(new Long(request.getParameter("claimSeqID")),request.getParameter("denailcode"));
	
	//preAuthDetailVO.setClaimSeqID(new Long(request.getParameter("claimSeqID")));
	
	preAuthDetailVO.setDenialCode(request.getParameter("denailcode"));
	preAuthDetailVO.setClaimStatus(request.getParameter("claimStatus"));
	
	 claimObject.saveAndCompleteClaim(preAuthDetailVO);

	 preAuthDetailVO = claimObject.getClaimDetails(new Long(request.getParameter("claimSeqID")));
	 frmClaimGeneral=setFormValues(preAuthDetailVO, mapping, request);
		if(preAuthDetailVO.getMemberWeight()!=null)
		{
			if(memberAgeValidation(frmClaimGeneral))
			frmClaimGeneral.set("memberwtflag","Y");
		}
			session.setAttribute("frmClaimGeneral", frmClaimGeneral);

		this.addToWebBoard(preAuthDetailVO, request, "CLM");

			strCaption.append(" Edit");
		strCaption.append(" [ " + frmClaimGeneral.getString("patientName")
				+ " ]");
		strCaption.append(" [ " + frmClaimGeneral.getString("memberId")
				+ " ]");
		frmClaimGeneral.set("caption", strCaption.toString());

		request.setAttribute("successMsg", "Claim completed successfully!");
		return this.getForward(strClaimdetails, mapping, request);

	}// end of try
	catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
	}// end of catch(TTKException expTTK)
	catch (Exception exp) {
		return this.processExceptions(request, mapping, new TTKException(
				exp, strClaimDetail));
	}// end of catch(Exception exp)
		
		
	}

	/**
	 * Returns the PreAuthManager session object for invoking methods on it.
	 * 
	 * @return PreAuthManager session object which can be used for method
	 *         invokation
	 * @exception throws TTKException
	 */
	public HashMap<String, String> getClaimDiagDetails() throws TTKException
	{
		ClaimManager claimManager=this.getClaimManagerObject();
		return claimManager.getClaimDiagDetails();
		
	}
	
	
	private PreAuthManager getPreAuthManagerObject() throws TTKException {
		PreAuthManager preAuthManager = null;
		try {
			if (preAuthManager == null) {
				InitialContext ctx = new InitialContext();
				preAuthManager = (PreAuthManager) ctx
						.lookup("java:global/TTKServices/business.ejb3/PreAuthManagerBean!com.ttk.business.preauth.PreAuthManager");
			}// end if
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, strClaimDetail);
		}// end of catch
		return preAuthManager;
	}// end getPreAuthManagerObject()

	/**
	 * Returns the ClaimManager session object for invoking methods on it.
	 * 
	 * @return ClaimManager session object which can be used for method
	 *         invokation
	 * @exception throws TTKException
	 */
	private ClaimManager getClaimManagerObject() throws TTKException {
		ClaimManager claimManager = null;
		try {
			if (claimManager == null) {
				InitialContext ctx = new InitialContext();
				claimManager = (ClaimManager) ctx
						.lookup("java:global/TTKServices/business.ejb3/ClaimManagerBean!com.ttk.business.claims.ClaimManager");
			}// end if
		}// end of try
		catch (Exception exp) {
			throw new TTKException(exp, strClaimDetail);
		}// end of catch
		return claimManager;
	}// end getClaimManagerObject()

	 /**
	 * Returns the CommunicationManager session object for invoking methods on
	 * it.
	 * 
	 * @return CommunicationManager session object which can be used for method
	 *         invocation
		 * @exception throws TTKException
		 */
	private CommunicationManager getCommunicationManagerObject()
			throws TTKException {
			CommunicationManager communicationManager = null;
		try {
			if (communicationManager == null) {
					InitialContext ctx = new InitialContext();
				communicationManager = (CommunicationManager) ctx
						.lookup("java:global/TTKServices/business.ejb3/CommunicationManagerBean!com.ttk.business.common.messageservices.CommunicationManager");
			}// end if
		}// end of try
		catch (Exception exp) {
				throw new TTKException(exp, strAuthorizationError);
		}// end of catch
			return communicationManager;
	}// end of getCommunicationManagerObject()

	/**
	 * This method for document viewer information
	 * 
	 * @param request
	 *            HttpServletRequest object which contains Pre-Authorization
	 *            information.
	 * @param preAuthDetailVO
	 *            PreAuthDetailVO object which contains Pre-Authorization
	 *            information.
	 * @exception throws TTKException
	 */
	private void documentViewer(HttpServletRequest request,
			PreAuthDetailVO preAuthDetailVO) throws TTKException {
		ArrayList<String> alDocviewParams = new ArrayList<String>();
		alDocviewParams.add("leftlink=" + TTKCommon.getActiveLink(request));
		if (TTKCommon.getActiveLink(request).equalsIgnoreCase(
				strPre_Authorization)) {
			alDocviewParams.add("pre_auth_number="
					+ TTKCommon.getWebBoardDesc(request));
			alDocviewParams.add("dms_reference_number="
					+ TTKCommon.checkNull(preAuthDetailVO.getDMSRefID()));
		}// end of
			// if(TTKCommon.getActiveLink(request).equalsIgnoreCase(strPre_Authorization))
		else if (TTKCommon.getActiveLink(request).equalsIgnoreCase(strClaims)) {
			// alDocviewParams.add("claim_number="+TTKCommon.getWebBoardDesc(request));
			alDocviewParams
					.add("claimno=" + TTKCommon.getWebBoardDesc(request));
			alDocviewParams.add("dms_reference_number="
					+ preAuthDetailVO.getDMSRefID());
			// added for KOC-1267
			alDocviewParams.add("userid=" + preAuthDetailVO.getUser());
			alDocviewParams.add("roleid=INTERNAL");
		}// end of else

		if (request.getSession().getAttribute("toolbar") != null) {
			((Toolbar) request.getSession().getAttribute("toolbar"))
					.setDocViewParams(alDocviewParams);
		}// end of if(request.getSession().getAttribute("toolbar")!=null)
	}// end of documentViewer(HttpServletRequest request,PreAuthDetailVO
		// preAuthDetailVO)

	/**
	 * This method prepares the Weboard id for the selected Policy
	 * 
	 * @param preAuthDetailVO
	 *            preAuthVO for which webboard id to be prepared
	 * @param String
	 *            strIdentifier whether it is preauth or enhanced preauth
	 * @param String
	 *            strModule identifier Pre-Authorization/Claims
	 * @return Web board id for the passedVO
	 */
	private String prepareWebBoardId(PreAuthVO preAuthVO, String strIdentifier,
			String strModule) throws TTKException {
		StringBuffer sbfCacheId = new StringBuffer();
		if (strModule.equals(strPre_Authorization)) {
			sbfCacheId.append(preAuthVO.getPreAuthSeqID() != null ? String
					.valueOf(preAuthVO.getPreAuthSeqID()) : " ");
			sbfCacheId.append("~#~")
					.append(TTKCommon.checkNull(preAuthVO.getEnrollmentID())
							.equals("") ? " " : preAuthVO.getEnrollmentID());
			sbfCacheId.append("~#~").append(
					preAuthVO.getEnrollDtlSeqID() != null ? String
							.valueOf(preAuthVO.getEnrollDtlSeqID()) : " ");
			sbfCacheId.append("~#~").append(
					preAuthVO.getPolicySeqID() != null ? String
							.valueOf(preAuthVO.getPolicySeqID()) : " ");
			sbfCacheId.append("~#~").append(
					preAuthVO.getMemberSeqID() != null ? String
							.valueOf(preAuthVO.getMemberSeqID()) : " ");
			sbfCacheId.append("~#~").append(strIdentifier);
			sbfCacheId.append("~#~")
					.append(TTKCommon.checkNull(preAuthVO.getClaimantName())
							.equals("") ? " " : preAuthVO.getClaimantName());
			sbfCacheId.append("~#~").append(
					TTKCommon.checkNull(preAuthVO.getBufferAllowedYN()).equals(
							"") ? " " : preAuthVO.getBufferAllowedYN());
			sbfCacheId.append("~#~")
					.append(TTKCommon.checkNull(preAuthVO.getShowBandYN())
							.equals("") ? " " : preAuthVO.getShowBandYN());
			sbfCacheId
					.append("~#~")
					.append(TTKCommon
							.checkNull(preAuthVO.getCoding_review_yn()).equals(
									"") ? " " : preAuthVO.getCoding_review_yn());
		}// end of if(strModule.equals(strPre_Authorization))
		else if (strModule.equals(strClaims)) {
			sbfCacheId.append(preAuthVO.getClaimSeqID() != null ? String
					.valueOf(preAuthVO.getClaimSeqID()) : " ");
			sbfCacheId.append("~#~")
					.append(TTKCommon.checkNull(preAuthVO.getEnrollmentID())
							.equals("") ? " " : preAuthVO.getEnrollmentID());
			sbfCacheId.append("~#~").append(
					preAuthVO.getEnrollDtlSeqID() != null ? String
							.valueOf(preAuthVO.getEnrollDtlSeqID()) : " ");
			sbfCacheId.append("~#~").append(
					preAuthVO.getPolicySeqID() != null ? String
							.valueOf(preAuthVO.getPolicySeqID()) : " ");
			sbfCacheId.append("~#~").append(
					preAuthVO.getMemberSeqID() != null ? String
							.valueOf(preAuthVO.getMemberSeqID()) : " ");
			sbfCacheId.append("~#~")
					.append(TTKCommon.checkNull(preAuthVO.getClaimantName())
							.equals("") ? " " : preAuthVO.getClaimantName());
			sbfCacheId.append("~#~").append(
					TTKCommon.checkNull(preAuthVO.getBufferAllowedYN()).equals(
							"") ? " " : preAuthVO.getBufferAllowedYN());
			sbfCacheId.append("~#~").append(
					preAuthVO.getClmEnrollDtlSeqID() != null ? String
							.valueOf(preAuthVO.getClmEnrollDtlSeqID()) : " ");
			sbfCacheId.append("~#~").append(
					preAuthVO.getAmmendmentYN() != null ? String
							.valueOf(preAuthVO.getAmmendmentYN()) : " ");
			sbfCacheId
					.append("~#~")
					.append(TTKCommon
							.checkNull(preAuthVO.getCoding_review_yn()).equals(
									"") ? " " : preAuthVO.getCoding_review_yn());
		}// end of else
		return sbfCacheId.toString();
	}// end of prepareWebBoardId(PreAuthVO preAuthVO,String strIdentifier)throws
		// TTKException

	/**
	 * Adds the selected item to the web board and makes it as the selected item
	 * in the web board
	 * 
	 * @param preauthVO
	 *            object which contains the information of the preauth * @param
	 *            String strIdentifier whether it is preauth or enhanced preauth
	 * @param request
	 *            HttpServletRequest
	 * @throws TTKException
	 *             if any runtime exception occures
	 */
	private void addToWebBoard(PreAuthVO preAuthVO, HttpServletRequest request,
			String strIdentifier) throws TTKException {
		Toolbar toolbar = (Toolbar) request.getSession()
				.getAttribute("toolbar");
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		CacheObject cacheObject = new CacheObject();
		cacheObject.setCacheId(this.prepareWebBoardId(preAuthVO, strIdentifier,
				TTKCommon.getActiveLink(request))); // set the cacheID
		cacheObject.setCacheDesc(preAuthVO.getInvoiceNo());

		alCacheObject.add(cacheObject);
		// if the object(s) are added to the web board, set the current web
		// board id
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());

		// webboardinvoked attribute will be set as true in request scope
		// to avoid the replacement of web board id with old value if it is
		// called twice in same request scope
		request.setAttribute("webboardinvoked", "true");
	}// end of addToWebBoard(PreAuthVO preAuthVO, HttpServletRequest
		// request,String strIdentifier)throws TTKException

		/**
	 * Methods checks whether user is having permession for the next Event.
	 * 
	 * @param lngEventSeqId
	 *            Long Event_Seq_Id.
	 * @param strSwitchType
	 *            String SwitchType.
	 * @param request
	 *            HttpServletRequest object.
	 * @return blnPermession boolean.
	 */
	private boolean checkReviewPermession(Long lngEventSeqId,
			HttpServletRequest request, String strActiveTab) {
		boolean blnPermession = false;
		WorkflowVO workFlowVO = null;
		ArrayList alEventId = null;
		// get the HashMap from UserSecurityProfile
		HashMap hmWorkFlow = ((UserSecurityProfile) request.getSession()
				.getAttribute("UserSecurityProfile")).getWorkFlowMap();
		if (strActiveTab.equals(strPre_Authorization)) {
			if (hmWorkFlow != null && hmWorkFlow.containsKey(new Long(3)))
				workFlowVO = (WorkflowVO) hmWorkFlow.get(new Long(3));// to get
																		// the
																		// work
																		// flow
																		// of
																		// pre-auth
		}// end of if(strActiveTab.equals(strPre_Authorization))
		if (strActiveTab.equals(strClaims)) {
			if (hmWorkFlow != null && hmWorkFlow.containsKey(new Long(4)))
				workFlowVO = (WorkflowVO) hmWorkFlow.get(new Long(4));// to get
																		// the
																		// work
																		// flow
																		// of
																		// claims
		}// end of if(strActiveTab.equals(strClaims))
			// get the arrayList which is having event information of the
			// particular user.
		if (workFlowVO != null) {
			alEventId = workFlowVO.getEventVO();
		}// end of if(workFlowVO!=null)
			// compare the current policy EventSeqId with the User permession.
		if (alEventId != null) {
			for (int i = 0; i < alEventId.size(); i++) {
				if (lngEventSeqId == ((EventVO) alEventId.get(i))
						.getEventSeqID()) {
					blnPermession = true;
					break;
				}// end of
					// if(lngEventSeqId==((EventVO)alEventId.get(i)).getEventSeqID())
			}// end of for(iRactnt i=0;i<alEventId.size();i++)
		}// end of if(alEventId!=null)
		return blnPermession;
	}// end of checkReviewPermession(Long lngEventSeqId,HttpServletRequest
		// request,String strActiveTab)

	//CEED Validation
	
		public ActionForward doCeedValidation(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			try {
				
				request.getSession().removeAttribute("claimEditNode");
				request.getSession().removeAttribute("CEEDResult");
				request.getSession().removeAttribute("ceedResponse");
				setLinks(request);
				DynaActionForm frmClaimGeneral = (DynaActionForm) form;
				String claimNo		=	frmClaimGeneral.getString("claimNo");
				log.debug("Inside ClaimGeneralAction doSave");
				String claimSeqID	=	(String) frmClaimGeneral.get("claimSeqID");
				
				//GET CLAIM XML FROM DB  S T A R T S
				PreAuthManager preAuthObject = this.getPreAuthManagerObject();
				
				//File f=new File("C:\\Users\\lohith.m\\Desktop\\ceed sample.xml");
		       // SAXReader r=new SAXReader();
		        
				Document xmlData	=	preAuthObject.getXmlTOSendCEED(new Long(claimSeqID));
				
			//	Document xmlData	=	r.read(f);
			//	System.out.println("Indoc=======>"+xmlData.asXML());
	
				String strMode="CLM";
				String responseData=CeedSoapSaajClient.execute("1", xmlData,strMode,claimNo);
				
	          //READING XML RESPONSE FROM DESKTOP FILE
	            SAXReader reader	=	new SAXReader();
	            Document doc		=	(Document) reader.read(new StringReader(responseData));
	        // System.out.println("outdoc=======>"+doc.asXML());
	            
	            request.getSession().setAttribute("ceedResponse",doc);
	            
	            String namespaceArr[]	=	{"http://schemas.xmlsoap.org/soap/envelope/","http://www.w3.org/2001/XMLSchema-instance",
	            							"http://www.w3.org/2001/XMLSchema","http://www.Dimensions-healthcare.net/DHCEG/CommonTypes"
	            							};
	            
	            //REMOVE NAMESPACE CODE
	            Namespace oldNs = 	null;
	            Namespace newNs =	null;
	            Visitor visitor	=	null;
	            for(int i=0;i<namespaceArr.length;i++)
	            {
	            	oldNs = Namespace.get(namespaceArr[i]);
	     	        newNs = Namespace.get("", "");
	     	        visitor = new NamesapceChangingVisitor(oldNs, newNs);
	     	        doc.accept(visitor);
	            }
	           
	            //REMOVE NAMESPACE CODE
	            
	            
	            
	            if(doc!=null){
	            	  Node envelopeNode	=	doc.selectSingleNode(doc.getRootElement().getPath());
        	            
	  	            Node bodyNode		=	doc.selectSingleNode(envelopeNode.getPath()+"/soap:Body");
	  	            
	            Node dhcgegResponseNode		=	doc.selectSingleNode(bodyNode.getPath()+"/DHCEGResponse");
	          
	            Node dhcegResultNode		=	doc.selectSingleNode(dhcgegResponseNode.getPath()+"/DHCEGResult");
	            
	  	          //  Node responseNode			=	doc.selectSingleNode(dhcgegResponseNode.getPath()+"/response");
	            
	         	   //Node dhcegResultNode		=	doc.selectSingleNode("//DHCEGResult");
	 	            if(dhcegResultNode!=null){
	 	            	request.getSession().setAttribute("CEEDResult",dhcegResultNode.getText());
	 	            }

	            }

	            
	            
	           
	            List<Node> claimEditNode			=	null;
		        	
	            if(doc.asXML().contains("ClaimEdit"))
	            {
	               	//claimEditNode			=	doc.selectNodes(responseNode.getPath()+"/ClaimEdit");
	               	claimEditNode			=	doc.selectNodes("//ClaimEdit");

	                request.getSession().setAttribute("claimEditNode",claimEditNode);
		        	 }
	         	else
	         	{
	           	   request.getSession().setAttribute("claimEditNode",claimEditNode);
	         	}
		        	
	            String reviewOrAlert	=	"";
	            String[] editDetails			=	null;
	            String reviewOrAlertTitle		=	"";
	           HashMap<String,CeedResponseVo> ceedResData=new HashMap<String, CeedResponseVo>();
	            
	            if(doc!=null && claimEditNode!=null)
	            {
    				if(claimEditNode.size()==0)
        				reviewOrAlert	=	"OK";
    				else{
    						reviewOrAlert		=	"";
    						reviewOrAlertTitle	=	"";
    				}
	            
	            
for(Node claimEditNodes : claimEditNode){
            		
    	        	List<Node> claimEditEditNode			=claimEditNodes.selectNodes("./Edit");		//ceedResponseDoc.selectNodes(claimEditNodes.getPath()+"/Edit");
    	        	if(claimEditEditNode.size()!=0)
    	        		reviewOrAlert		=	"OK";
    	        	for(Node claimEditEditNodes : claimEditEditNode){
    		        	
    	        	List<Node> claimActivityEditNode		=claimEditNodes.selectNodes("./ActivityEdit");	//ceedResponseDoc.selectNodes(claimEditNodes.getPath()+"/ActivityEdit");
    	        	for(Node claimActivityEditNodes : claimActivityEditNode){
    	        		CeedResponseVo ceedResponseVo=new CeedResponseVo();
    	        		//CONDITIONS
    		        	//if(claimActivityEditNodes.valueOf("ID").equals(""+activityDetails.getActivitySeqId()))
    		        	//{
    		        		if(claimActivityEditNodes.valueOf("EditId").equals(claimEditEditNodes.valueOf("ID")))
    		        		{

    		        			reviewOrAlert	=	claimEditEditNodes.valueOf("Rank");
    		        			if("R".equals(reviewOrAlert))
    		        				reviewOrAlert	=	"Review";
    		        			else if("A".equals(reviewOrAlert))
    		        				reviewOrAlert	=	"Alert";
    		        			
    		        			if("Alert".equals(reviewOrAlert))
    		        			{
    		        				String Mode = "C";
    		        				String res = "A";
    		        				String activitySeqId = claimActivityEditNodes.valueOf("ID");
    		        				preAuthObject.ceedResponseDetails(Mode,new Long(claimSeqID),new Long(activitySeqId),res);
    		        			}else if("Review".equals(reviewOrAlert)){
    		        				String Mode = "C";
    		        				String res = "R";
    		        				String activitySeqId = claimActivityEditNodes.valueOf("ID");
    		        				preAuthObject.ceedResponseDetails(Mode,new Long(claimSeqID),new Long(activitySeqId),res);
    		        			}
    		        			reviewOrAlertTitle=	claimEditEditNodes.valueOf("Comment");
    		        			editDetails		=	new String[7];
    		        			editDetails[0]	=	claimEditEditNodes.valueOf("ID");
    		        			editDetails[1]	=	claimEditEditNodes.valueOf("Type");
    		        			editDetails[2]	=	claimEditEditNodes.valueOf("SubType");
    		        			editDetails[3]	=	claimEditEditNodes.valueOf("Code");
    		        			editDetails[4]	=	claimEditEditNodes.valueOf("Comment");
    		        			editDetails[5]	=	claimActivityEditNodes.valueOf("ID");
    		        			editDetails[6]	=	claimActivityEditNodes.valueOf("EditId");
    		        			request.getSession().setAttribute("editDetails"+claimActivityEditNodes.valueOf("ID"), editDetails);
		        				}
		        			ceedResponseVo.setReviewOrAlert(reviewOrAlert);
    		        		ceedResponseVo.setReviewOrAlertTitle(reviewOrAlertTitle);
    		        		ceedResData.put(claimActivityEditNodes.valueOf("ID"),ceedResponseVo);
    		        		
		        			//}
	        			}
    	        	}
    	        }
	            }
	            
request.getSession().setAttribute("ceedResMapData", ceedResData);

    
	            request.setAttribute("successMsg", "Record updated successfully!");
	            return this.getForward(strClaimdetails, mapping, request);
				
			}// end of try

			catch (TTKException expTTK) {
				return this.processExceptions(request, mapping, expTTK);
			}// end of catch(TTKException expTTK)
			catch (Exception exp) {
				return this.processExceptions(request, mapping, new TTKException(
						exp, strAuthorizationError));
			}// end of catch(Exception exp)
		}// end of doCeedValidation(ActionMapping mapping,ActionForm
			// form,HttpServletRequest request,HttpServletResponse response)

		
		
		
		
		
		
		
		
		
		
		
		private static void createSoapResponse(SOAPMessage soapResponse) throws Exception  {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			Source sourceContent = soapResponse.getSOAPPart().getContent();
			StreamResult result = new StreamResult(System.out);
			transformer.transform(sourceContent, result);
		 }
		
		
		public ActionForward viewBenefitDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			try {
				
				setLinks(request);
				log.debug("Inside ClaimGeneralAction addActivityDetails ");
				HttpSession session = request.getSession();
				String benefitType= (String)request.getParameter("benefitType");
				String benefitTypes1= (String)request.getParameter("benefitType");
				DynaActionForm frmClaimGeneral = (DynaActionForm) session.getAttribute("frmClaimGeneral");
				ClaimManager claimManagerObject=this.getClaimManagerObject();
	    		//get the tbale data from session if exists
				TableData tableData =TTKCommon.getTableData(request);
				//PreAuthVO preAuthVO=new PreAuthVO;
				//clear the dynaform if visting from left links for the first time
				//else get the dynaform data from session
				if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
				{
					((DynaActionForm)form).initialize(mapping);//reset the form data
				}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
				String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
				String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
				//if the page number or sort id is clicked
				if(!strPageID.equals("") || !strSortID.equals(""))
				{
					if(!strPageID.equals(""))
					{
						tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
						return mapping.findForward(strBenefitDetails);
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
					tableData.createTableInfo("BenefitTable",null);
					
					if("OPTS".equals(benefitType)||"OPT".equals(benefitType))
						benefitType="OUT-PATIENT";
					if("DNTL".equals(benefitType)||"DNT".equals(benefitType))
						benefitType="DENTAL";
					if("IMTI".equals(benefitType)||"OMTI".equals(benefitType)||"MAT".equals(benefitType))
						benefitType="MATERNITY";
					if("OPTC".equals(benefitType)||"OPL".equals(benefitType))
						benefitType="OPTICAL";
					if("IPT".equals(benefitType))
						benefitType="IN-PATIENT";
					if("ALL".equals(benefitType))
						benefitType="All";
					if("PED".equals(benefitType))
						benefitType="PED";
					if("CHRC".equals(benefitType))
						benefitType="CHRONIC CONDITION";
					if("AYR".equals(benefitType))
						benefitType="AYURVEDA/HOMEOPATHY";
					if("HEA".equals(benefitType))
						benefitType="HEALTH CHECKUP/WELLNESS";
					
					
					
					
					ArrayList<Object> allinfo=new ArrayList<Object>();
					allinfo.add(frmClaimGeneral.getString("policySeqId"));
					allinfo.add(frmClaimGeneral.getString("memberSeqID"));
					allinfo.add(TTKCommon.getActiveLink(request));
					allinfo.add(frmClaimGeneral.getString("claimSeqID"));
					allinfo.add(benefitType);
					tableData.setSearchData(allinfo);
	               // this.setColumnVisiblity(tableData,(DynaActionForm)form,request);
					tableData.modifySearchData("search");
				}//end of else
				
			
			ArrayList<Object> alBenefitList= claimManagerObject.getBenefitDetails(tableData.getSearchData());
			tableData.setData((ArrayList<Object>)alBenefitList.get(0), "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			request.setAttribute("availableSumInsured",alBenefitList.get(1));
			//request.setAttribute("otherRemarks",alBenefitList.get(2));			
			request.setAttribute("utilizeSuminsured",alBenefitList.get(2));	
			if("OPTS".equals(benefitType)||"OPT".equals(benefitType))
				benefitType="OUT-PATIENT";
			if("DNTL".equals(benefitType)||"DNT".equals(benefitType))
				benefitType="DENTAL";
			if("IMTI".equals(benefitType)||"OMTI".equals(benefitType)||"MAT".equals(benefitType))
				benefitType="MATERNITY";
			if("OPTC".equals(benefitType)||"OPL".equals(benefitType))
				benefitType="OPTICAL";
			if("IPT".equals(benefitType))
				benefitType="IN-PATIENT";
			if("ALL".equals(benefitType))
				benefitType="All";
			if("PED".equals(benefitType))
				benefitType="PED";
			if("CHRC".equals(benefitType))
				benefitType="CHRONIC CONDITION";
			if("AYR".equals(benefitType))
				benefitType="AYURVEDA/HOMEOPATHY";
			if("HEA".equals(benefitType))
				benefitType="HEALTH CHECKUP/WELLNESS";
			if("OTH".equals(benefitType))
				benefitType="Others";
			
			request.setAttribute("benefitType",benefitTypes1);
			
			
			return mapping.findForward(strBenefitDetails);

			}// end of try
			catch (TTKException expTTK) {
				return this.processExceptions(request, mapping, expTTK);
			}// end of catch(TTKException expTTK)
			catch (Exception exp) {
				return this.processExceptions(request, mapping, new TTKException(
						exp, strClaimDetail));
			}// end of catch(Exception exp)
		}// end of AddActivityDetails(ActionMapping mapping,ActionForm
			// form,HttpServletRequest request,HttpServletResponse response)
		
		
	public boolean	memberAgeValidation(DynaActionForm frmClaimGeneral)throws Exception{
			String strNetworkProviderType=frmClaimGeneral.getString("networkProviderType");
			String strProvAuthority=frmClaimGeneral.getString("provAuthority");
			String strEncounterTypeId=frmClaimGeneral.getString("encounterTypeId");
			boolean status=false;
			if("Y".equals(strNetworkProviderType)&&"DHA".equals(strProvAuthority)&&("3".equals(strEncounterTypeId)||"4".equals(strEncounterTypeId))){
				
				String strAdmissionDate=frmClaimGeneral.getString("admissionDate");
				String strMemberDOB=frmClaimGeneral.getString("memberDOB");
				if(strMemberDOB!=null&&!"".equals(strMemberDOB)){	
					if(strAdmissionDate!=null&&!"".equals(strAdmissionDate)){
					SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yyyy");
					Date date1=simpleDateFormat.parse(strMemberDOB);
					
					Date date2=simpleDateFormat.parse(strAdmissionDate);
					
					long sd=(8*24*60*60*1000);
					long dif=date2.getTime()-date1.getTime();
					if(dif<sd){
						
						status=true;
						int days = (int) (dif / (1000*60*60*24));
						frmClaimGeneral.set("patientdays",days+"");
						
					}
				}
			  }
			}
		return status;
		}
		
	
	public ActionForward deleteAuthorizationDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGenealAction deleteAuthorizationDetails ");
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			StringBuffer strCaption = new StringBuffer();
			DynaActionForm frmClaimGeneral = (DynaActionForm) form;
			HttpSession session = request.getSession();
			PreAuthDetailVO preAuthDetailVO = null;
			ClaimManager claimObject = this.getClaimManagerObject();
			String claimSeqID = (String) frmClaimGeneral.get("claimSeqID");
			
			preAuthDetailVO = claimObject.getClaimDetails(new Long(claimSeqID));
			
			String ClaimStatus=preAuthDetailVO.getClaimStatus();
			
			if(!("INP".equals(ClaimStatus)))
			{
				request.setAttribute("delErrorMsg"," Prior Authorization can be delinked only for the 'IN-PROGRESS' claims.");	
				return this.getForward(strClaimdetails, mapping, request);
			}	
			
			if("INP".equals(ClaimStatus))
			{	
				int count= 	preAuthObject.deleteAuthorizationDetails(new Long(claimSeqID));
			
				if(count == 0)
				{
					return this.getForward(strClaimdetails, mapping, request);
				}
			
				if(count > 0)
				{
					frmClaimGeneral.initialize(mapping);
					preAuthDetailVO = claimObject.getClaimDetails(new Long(claimSeqID));
					frmClaimGeneral=setFormValues(preAuthDetailVO, mapping, request);
					if(preAuthDetailVO.getMemberWeight()!=null)
					{
						if(memberAgeValidation(frmClaimGeneral))
							frmClaimGeneral.set("memberwtflag","Y");
					}
				
					this.addToWebBoard(preAuthDetailVO, request, "CLM");
	
					strCaption.append(" Edit");
					strCaption.append(" [ " + frmClaimGeneral.getString("patientName")+ " ]");
					strCaption.append(" [ " + frmClaimGeneral.getString("memberId")+ " ]");
					frmClaimGeneral.set("caption", strCaption.toString());
					request.setAttribute("successMsg","Prior Authorization successfully de-linked from claims");	
				}
			}
			session.setAttribute("frmClaimGeneral", frmClaimGeneral);
			return this.getForward(strClaimdetails, mapping, request);
		}// end of try
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strClaimdetails));
		}// end of catch(Exception exp)
	}
		
	public ActionForward doViewPreauthHistory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			setLinks(request);
			log.debug("Inside ClaimGenealAction doViewPreauthHistory");
			PreAuthManager preAuthObject = this.getPreAuthManagerObject();
			Document historyDoc = null;
			String authSeqID = request.getParameter("preAuthSeqID");
			authSeqID = (authSeqID == null || authSeqID.length() < 1) ? "0": authSeqID;
			historyDoc = preAuthObject.getPreAuthHistory(new Long(authSeqID));
			
			ArrayList alPreauthDocs = new ArrayList();
			alPreauthDocs = preAuthObject.getPreauthDocsUploads(authSeqID);
			
			if(!(alPreauthDocs.isEmpty()))
			{	
				request.getSession().setAttribute("alPreauthDocs", alPreauthDocs);
			}	
			request.setAttribute("authSeqID",authSeqID);
			request.setAttribute("preAuthHistoryDoc", historyDoc);
			return this.getForward("preauthhistorydetails2", mapping, request);
		}
		catch (TTKException expTTK) {
			return this.processExceptions(request, mapping, expTTK);
		}
		catch (Exception exp) {
			return this.processExceptions(request, mapping, new TTKException(
					exp, strCLaimHistoryList));
		}
	} 
	  
private DynaActionForm setFormValues(PreAuthDetailVO preAuthDetailVO,ActionMapping mapping,HttpServletRequest request){
	DynaActionForm frmClaimGeneral=(DynaActionForm) FormUtils.setFormValues("frmClaimGeneral", preAuthDetailVO, this, mapping,request);
	frmClaimGeneral.set("diagnosisList", preAuthDetailVO.getDiagnosisList());
	frmClaimGeneral.set("activityList", preAuthDetailVO.getActivityList());
	frmClaimGeneral.set("shortfallList", preAuthDetailVO.getShortfallList());
	return frmClaimGeneral;
	
}

private void updateActivityTariffPrice(final Long claimSeqID){
  
	Runnable runnable= new Runnable() {
		public void run() {
			
			// updating activity prise
			 try {
				getClaimManagerObject().updateActivityTariffPrice(claimSeqID);
			} catch (TTKException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
new Thread(runnable).start();
}

public ActionForward doChangeCases(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	try {
		setLinks(request);
		log.debug("Inside ClaimGenealAction doChangeCases");
		return this.getForward(strClaimdetails, mapping, request);
	}
	catch (TTKException expTTK) {
		return this.processExceptions(request, mapping, expTTK);
	}
	catch (Exception exp) {
		return this.processExceptions(request, mapping, new TTKException(
				exp, strClaimDetail));
	}
} 

}// end of ClaimGeneralAction

