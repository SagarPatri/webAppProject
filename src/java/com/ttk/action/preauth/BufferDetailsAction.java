/**
* @ (#) BufferDetailsAction.java Jul 3, 2006
* Project       : TTK HealthCare Services
* File          : BufferDetailsAction.java
* Author        : Chandrasekaran J
* Company       : Span Systems Corporation
* Date Created  : Jul 3, 2006

* @author       : Chandrasekaran J
* Modified by   :
* Modified date :
* Reason :
*/

package com.ttk.action.preauth;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;





import com.ttk.common.TTKPropertiesReader;
import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.preauth.PreAuthSupportManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.onlineforms.EmployeeFileUplodedVO;
import com.ttk.dto.preauth.AuthorizationVO;
import com.ttk.dto.preauth.BufferVO;
import com.ttk.dto.preauth.BufferDetailVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is reusable for viewing the buffer information in pre-auth and claims flow.
 * This class also provides option for saving buffer information.
 */


public class BufferDetailsAction extends TTKAction
{
	private static Logger log = Logger.getLogger(BufferDetailsAction.class);

	//Setting the ActionForward
	private static final String strBufferDetails="bufferdetails";
	private static final String strClaimsBufferDetails="claimsbufferdetails";
	private static final String strClaimsSupportDoc="claimssupportdoc";
	private static final String strSupportDoc="supportdoc";

	//string for comparision
	private static final String strPreAuthorization="Pre-Authorization";
	private static final String strClaims="Claims";

	//Exception Message Identifier
	private static final String strBufferError="support";

	/**
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
																	HttpServletResponse response) throws Exception {
		try{
			setLinks(request);
			log.debug("Inside BufferDetails Action doAdd");
			BufferDetailVO bufferDetailVO =new BufferDetailVO();
			String strForward="";
			String strClaimantname="";
			DynaActionForm frmBufferDetails= (DynaActionForm) form;
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink=TTKCommon.getActiveLink(request);
			
			String strClaimType=request.getParameter("patclmClaimType") != null?request.getParameter("patclmClaimType"):"NRML";
			String strBufferType=request.getParameter("patclmBufferType")!= null?request.getParameter("patclmBufferType"):"CORB";
			
			
			String strEnrollmentID="";
			if(strLink.equals(strClaims)){
				strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals(strClaims))
			else if(strLink.equals(strPreAuthorization))
			{
				strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end fo else
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("Buffer Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
			if(strLink.equals(strPreAuthorization))
			{
				strForward=strBufferDetails;
			}//end of if(strLink.equals(strPreAuthorization))
			else if(strLink.equals(strClaims))
			{
				strForward=strClaimsBufferDetails;
			}// end of else if(strLink.equals(strClaims))
			
							
			//Changes as per KOC 1216B change array length to 8
			Object[] objArrayResult = new Object[11];
			//Changes as per KOC 1216B
			 ArrayList<Object> alBufferAuthoritylList = new ArrayList<Object>();
			 if(strLink.equals("Claims"))
				{
			     alBufferAuthoritylList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				}else
				{
				alBufferAuthoritylList.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));	
				}
			    alBufferAuthoritylList.add(TTKCommon.getActiveLink(request));
			    alBufferAuthoritylList.add(strClaimType);
			    alBufferAuthoritylList.add(strBufferType);
			
			    
			    objArrayResult = preAuthSupportManagerObject.getBufferAuthority(alBufferAuthoritylList);
			/*if(strLink.equals("Claims"))
			{
				//objArrayResult = preAuthSupportManagerObject.getBufferAuthority(
										//ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getActiveLink(request));
			}//end of if(strLink.equals("Claims"))
			else
			{
				objArrayResult = preAuthSupportManagerObject.getBufferAuthority(
									PreAuthWebBoardHelper.getPreAuthSeqId(request),TTKCommon.getActiveLink(request));
			}//end of else
*/			if(objArrayResult[0]!=null){
				bufferDetailVO.setAdminAuthTypeID(objArrayResult[0].toString());
			}//end of if(objArrayResult[0]!=null)
			if(objArrayResult[1]!=null){
				bufferDetailVO.setAvailBufferAmt((BigDecimal)objArrayResult[1]);
			}//end of if(objArrayResult[1]!=null)
			if(objArrayResult[2]!=null){
				bufferDetailVO.setBufferRemarks((String)objArrayResult[2]);				
			}//end of if(objArrayResult[2]!=null)
			//1216B ChangeRequest Modifications going to add
			if(objArrayResult[3]!=null){
				bufferDetailVO.setBufferMode(objArrayResult[3].toString());
			}//end of if(objArrayResult[3]!=null)
			if(objArrayResult[4]!=null){
			  bufferDetailVO.setHrInsurerBuffAmount((BigDecimal)objArrayResult[4]);
			 }//end of if(objArrayResult[4]!=null)
			if(objArrayResult[5]!=null){
				bufferDetailVO.setMemberBufferAmt((BigDecimal)objArrayResult[5]);
			}//end of if(objArrayResult[5]!=null)
			if(objArrayResult[6]!=null){
				bufferDetailVO.setUtilizedMemberBuffer((BigDecimal)objArrayResult[6]);
			}//end of if(objArrayResult[6]!=null)
			if(objArrayResult[7]!=null){
				bufferDetailVO.setBufferFamilyCap((BigDecimal)objArrayResult[7]);
			}//end of if(objArrayResult[7]!=null)
			
			if(objArrayResult[8]!=null){
				bufferDetailVO.setClaimType((String)objArrayResult[8]);				
			}//end of if(objArrayResult[2]!=null)
			else{
				bufferDetailVO.setClaimType("");	
			}
			if(objArrayResult[9]!=null){
				if(objArrayResult[8].equals("NRML"))
				{
				  bufferDetailVO.setBufferType((String)objArrayResult[9]);
				}else
				{
					bufferDetailVO.setBufferType("");
				}
				 if(objArrayResult[8].equals("CRTL"))
				 {
				    bufferDetailVO.setBufferType1((String)objArrayResult[9]);
				}else
				{
					bufferDetailVO.setBufferType1("");
				}
		     }//end of if(objArrayResult[2]!=null)
			
		
			//1216B ChangeRequest Modifications 				
			frmBufferDetails = (DynaActionForm)FormUtils.setFormValues("frmBufferDetails",bufferDetailVO,this,
																							mapping,request);
			frmBufferDetails.set("caption",String.valueOf(strCaption));
			frmBufferDetails.set("HrAppYN",((String)objArrayResult[10]));
			//Added as per kOC 1216B change request
			this.formValue(bufferDetailVO,frmBufferDetails,request);
			//Added as per kOC 1216B change request
			request.getSession().setAttribute("frmBufferDetails",frmBufferDetails);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBufferError));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to Add or Edit Buffer Details.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside BufferDetailsAction doView");
			BufferDetailVO bufferDetailVO =null;
			BufferVO bufferVO=null;
			String strForward="";
			String strClaimantname="";
			String strEnrollmentID="";
			String strBufferType="";
			DynaActionForm frmBufferDetails= (DynaActionForm) form;
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink=TTKCommon.getActiveLink(request);
	
			/*String strClaimType=request.getParameter("claimType");
			if(strClaimType.equals("CRTL"))
			{
				 strBufferType=request.getParameter("bufferType1");
			}
			else  if(strClaimType.equals("NRML"))
			{
			     strBufferType=request.getParameter("bufferType");
			}
			String strHrAppYN=request.getParameter("hrapproval");
			String strfileName=request.getParameter("fileName");*/
			
			if(strLink.equals(strClaims)){
				strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals(strClaims))
			else if(strLink.equals(strPreAuthorization))
			{
				strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("Buffer Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
			if(strLink.equals(strPreAuthorization))
			{
				strForward=strBufferDetails;
			}//end of if(strLink.equals(strPreAuthorization))
			else if(strLink.equals(strClaims))
			{
				strForward=strClaimsBufferDetails;
			}// end of else if(strLink.equals(strClaims))
			//if rownumber is found populate the form object
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				bufferVO=(BufferVO)((TableData)request.getSession().getAttribute("tableData")).getData().get(
										Integer.parseInt(TTKCommon.checkNull(request.getParameter("rownum"))));
				if(strLink.equals("Claims"))
				{
					bufferDetailVO=preAuthSupportManagerObject.getBufferDetail(bufferVO.getBufferDtlSeqID(),
																TTKCommon.getUserSeqId(request),"CLM");
				}//end of if(strLink.equals("Claims"))
				else
				{
					bufferDetailVO=preAuthSupportManagerObject.getBufferDetail(bufferVO.getBufferDtlSeqID(),
																TTKCommon.getUserSeqId(request),"PAT");
				}//end of else
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			frmBufferDetails = (DynaActionForm)FormUtils.setFormValues("frmBufferDetails",bufferDetailVO,this,
																						mapping,request);
			frmBufferDetails.set("caption",String.valueOf(strCaption));
            frmBufferDetails.set("patclmClaimType",bufferDetailVO.getClaimType());
			frmBufferDetails.set("patclmBufferType",bufferDetailVO.getBufferType());
			//frmBufferDetails.set("hrapproval",strHrAppYN);
			if(bufferDetailVO.getFileName().endsWith("pdf") || bufferDetailVO.getFileName().endsWith("doc") || bufferDetailVO.getFileName().endsWith("docx"))
			
				{
				frmBufferDetails.set("fileExistYN",bufferDetailVO.getFileName());
				}
		    //Added as per kOC 1216B change request
			this.formValue(bufferDetailVO,frmBufferDetails,request);
			//Added as per kOC 1216B change request
			
			request.getSession().setAttribute("frmBufferDetails",frmBufferDetails);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBufferError));
		}//end of catch(Exception exp)
	}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to Update the Buffer Details.
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
												HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside BufferDetailsAction doSave");
			BufferDetailVO bufferDetailVO =null;
			String strForward="";
			String strClaimantname="";
			String strEnrollmentID="";
			Long lngFileData=null;
			String updated="";
			String strNotify="";
			String fileName="";
			
			Pattern pattern =null;
			Matcher matcher =null;
			FileOutputStream outputStream = null;
			FormFile formFile = null;
			int fileSize=1*1024*1024;
			TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");   
			DateFormat df =new SimpleDateFormat("ddMMyyyy HH:mm:ss:S");
			df.setTimeZone(tz);  
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			
			
			DynaActionForm frmBufferDetails= (DynaActionForm) form;
			
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink=TTKCommon.getActiveLink(request);
			String strBufferType=request.getParameter("bufferType");
			String strClaimType=request.getParameter("claimType");
			String strHrAppYN=request.getParameter("hrapproval");
			if(strLink.equals(strClaims)){
				strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals(strClaims))
			else if(strLink.equals(strPreAuthorization))
			{
				strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("Buffer Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
			if(strLink.equals(strPreAuthorization))
			{
				strForward=strBufferDetails;
			}//end of if(strLink.equals(strPreAuthorization))
			else if(strLink.equals(strClaims))
			{
				strForward=strClaimsBufferDetails;
			}// end of else if(strLink.equals(strClaims))
			Long lngBuffDetSeqId = null;
			
			bufferDetailVO=(BufferDetailVO)FormUtils.getFormValues(frmBufferDetails, this, mapping, request);
			
			//Get the FormFile object from ActionForm.
		
			ArrayList<Object> alFileAUploadList = new ArrayList<Object>();
			alFileAUploadList.add(TTKCommon.getUserSeqId(request));//2
		
			formFile =(FormFile) frmBufferDetails.get("file");
			
			//pattern = Pattern.compile( "[^a-zA-Z0-9\\.\\s*]" );
			pattern = Pattern.compile( "[^a-zA-Z0-9_\\.\\-\\s*]" );
			matcher = pattern.matcher( formFile.getFileName() );
			String 	strTimeStamp=((df.format(Calendar.getInstance(tz).getTime())).replaceAll(" ", "_")).replaceAll(":", ""); 
			
			
			if(bufferDetailVO.getStatusTypeID().equals("BAP"))
			{
				bufferDetailVO.setRejectedBy(null);
				bufferDetailVO.setRejectedDate(null);
				bufferDetailVO.setRejectedTime(null);
				bufferDetailVO.setRejectedDay(null);
				if(strHrAppYN.equals("N"))
				{
				 bufferDetailVO.setFileName(null);
				
				 
				}
				/*else
				{
					bufferDetailVO.setFileName(fileName);
				}*/
					
			}//end of if(bufferDetailVO.getStatusTypeID().equals("BAP"))
			if(bufferDetailVO.getStatusTypeID().equals("BRJ"))
			{
				bufferDetailVO.setApprovedAmt(null);
				bufferDetailVO.setApprovedBy(null);
				bufferDetailVO.setApprovedDate(null);
				bufferDetailVO.setApprovedTime(null);
				bufferDetailVO.setApprovedDay(null);
				bufferDetailVO.setFileName(null);
			}//end of if(bufferDetailVO.getStatusTypeID().equals("BRJ"))
			if(strLink.equals(strClaims))
			{
				bufferDetailVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
				bufferDetailVO.setPreAuthSeqID(null);
				bufferDetailVO.setPolicySeqID(ClaimsWebBoardHelper.getPolicySeqId(request));
				bufferDetailVO.setMemberSeqID(ClaimsWebBoardHelper.getMemberSeqId(request));
				if(strHrAppYN.equals("N"))
				{
				 bufferDetailVO.setFileName(null);
				}
			/*	else
				{
					bufferDetailVO.setFileName(fileName);
				}*/
			}//end of if(strLink.equals(strClaims))
			else
			{
				bufferDetailVO.setPreAuthSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				bufferDetailVO.setClaimSeqID(null);
				bufferDetailVO.setPolicySeqID(PreAuthWebBoardHelper.getPolicySeqId(request));
				bufferDetailVO.setMemberSeqID(PreAuthWebBoardHelper.getMemberSeqId(request));
			}//end of else
			bufferDetailVO.setClaimType(strClaimType);
			bufferDetailVO.setBufferType(strBufferType);
			if(strHrAppYN.equals("N"))
			{
			 bufferDetailVO.setFileName(null);
			}
			/*else
			{
				bufferDetailVO.setFileName(fileName);
			}*/
			 bufferDetailVO.setHrAppYN(strHrAppYN);
			 bufferDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			
			
			
		if((strHrAppYN.equals("Y")) && (bufferDetailVO.getStatusTypeID().equals("BAP")))
		{
			//added for hyundai buffer
			if(!matcher.find())
			{
				 
				 //bufferDetailVO.setUpdatedBy(new Long(1));
			     String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("bufferattachdestination"));
				
				File folder = new File(path);
				if(!folder.exists()){
					folder.mkdir();
				}
				fileName="CLM_BUF_"+strTimeStamp+"_"+formFile.getFileName();
				String finalPath=(path+fileName);
				//System.out.println("Content type    :"+formFile.getContentType());
				
			
				String strFileExt=formFile.getFileName().substring(formFile.getFileName().lastIndexOf(".")+1,formFile.getFileName().length());

				
				
				   
					if((strFileExt.equalsIgnoreCase("doc")) || (strFileExt.equalsIgnoreCase("docx"))|| (strFileExt.equalsIgnoreCase("pdf")))
				         {   
						     if(formFile.getFileSize()<=fileSize)
						   { 
							outputStream = new FileOutputStream(new File(finalPath));
							outputStream.write(formFile.getFileData());
									
				            }//end of if(strFileExt.equalsIgnoreCase("html") || strFileExt.equalsIgnoreCase("mhtml")||strFileExt.equalsIgnoreCase("msg") || strFileExt.equalsIgnoreCase("rar")|| strFileExt.equalsIgnoreCase("zip") || strFileExt.equalsIgnoreCase("pdf") || strFileExt.equalsIgnoreCase("doc") || strFileExt.equalsIgnoreCase("docx") || strFileExt.equalsIgnoreCase("txt") || strFileExt.equalsIgnoreCase("odt") || strFileExt.equalsIgnoreCase("jrxml") || strFileExt.equalsIgnoreCase("xls") || strFileExt.equalsIgnoreCase("xlsx") )
						     else{
									strNotify="selected "+formFile.getFileName()+" size  Shold be less than or equal to 1 MB";
								}//end of else part if(formFile.getFileSize()<=fileSize)
					   
						   
				              }//end of if(formFile.getFileSize()<=fileSize)
					  else{

							//updated="selected "+formFile.getFileName()+" file is not in pdf format,Please select pdf file";
							strNotify="selected "+formFile.getFileName()+" file should be any of these extensions (.pdf,.doc,.docx)";
							frmBufferDetails = (DynaActionForm)FormUtils.setFormValues("frmBufferDetails",bufferDetailVO,this,
									mapping,request);
						}//end ofelse(strFileExt.equalsIgnoreCase("pdf"))
				
			}//end of if(!matcher.find())
			else
			{
				//updated="selected "+formFile.getFileName()+" file is not in pdf format,Please select pdf file";
				strNotify="selected "+formFile.getFileName()+" file Should not have Special Characters like }!@#$%^&amp;*(])[{+";

			}
			
		 }//end of if(bufferDetailVO.getHrAppYN().equals('Y') && bufferDetailVO.getStatusTypeID().equals("BAP"))
			
				//end added for hyundai buffer
			if(strNotify.equals(""))
			{   
				bufferDetailVO.setFileName(fileName);	
				 if(strLink.equals(strClaims))
				{
					lngBuffDetSeqId=preAuthSupportManagerObject.saveBufferDetail(bufferDetailVO,"CLM");
				}//end of if(strLink.equals(strClaims))
				else
				{
					lngBuffDetSeqId=preAuthSupportManagerObject.saveBufferDetail(bufferDetailVO,"PAT");
				}//end of else
				if(lngBuffDetSeqId>0)
				{
					if(frmBufferDetails.get("bufferDtlSeqID")!=null && !frmBufferDetails.get("bufferDtlSeqID").equals(""))
					{
						//set the appropriate message
							
						request.setAttribute("updated","message.savedSuccessfully");
					}//end of if
					else
					{
						//set the appropriate message
						request.setAttribute("updated","message.addedSuccessfully");
					}//end of else
				
					
				}//end of if(lngUpdate>0)
					bufferDetailVO=preAuthSupportManagerObject.getBufferDetail(lngBuffDetSeqId,
																			TTKCommon.getUserSeqId(request),"Preauthorization");
					frmBufferDetails = (DynaActionForm)FormUtils.setFormValues("frmBufferDetails",bufferDetailVO,this,
																			mapping,request);
			}
		
			log.info("bufferDetailVO.getFileName():::"+bufferDetailVO.getFileName());
			//Added as per kOC 1216B change request
			this.formValue(bufferDetailVO,frmBufferDetails,request);
			//Added as per kOC 1216B change request
			request.getSession().setAttribute("frmBufferDetails",frmBufferDetails);
			
			frmBufferDetails.set("HrAppYN",strHrAppYN);
			if(bufferDetailVO.getFileName().endsWith("pdf") || bufferDetailVO.getFileName().endsWith("doc") || bufferDetailVO.getFileName().endsWith("docx"))
			
				{
				frmBufferDetails.set("fileExistYN",bufferDetailVO.getFileName());
				}
			request.setAttribute("notify",strNotify);
			frmBufferDetails.set("caption",String.valueOf(strCaption));
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBufferError));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to Send the request when the user clicks SendRequest button.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSendRequest(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside BufferDetailsAction doSendRequest");
			BufferDetailVO bufferDetailVO =null;
			String strForward="";
			String strClaimantname="";
			String strEnrollmentID="";
			DynaActionForm frmBufferDetails= (DynaActionForm) form;
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink=TTKCommon.getActiveLink(request);
			if(strLink.equals(strClaims)){
				strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals(strClaims))
			else if(strLink.equals(strPreAuthorization))
			{
				strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("Buffer Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
			if(strLink.equals(strPreAuthorization))
			{
				strForward=strBufferDetails;
			}//end of if(strLink.equals(strPreAuthorization))
			else if(strLink.equals(strClaims))
			{
				strForward=strClaimsBufferDetails;
			}// end of else if(strLink.equals(strClaims))
			Long lngBuffDetSeqId = null;
			bufferDetailVO=(BufferDetailVO)FormUtils.getFormValues(frmBufferDetails, this, mapping, request);
			if(bufferDetailVO.getStatusTypeID().equals("BAP"))
			{
				bufferDetailVO.setRejectedBy(null);
				bufferDetailVO.setRejectedDate(null);
				bufferDetailVO.setRejectedTime(null);
				bufferDetailVO.setRejectedDay(null);
			}//end of if(bufferDetailVO.getStatusTypeID().equals("BAP"))
			if(bufferDetailVO.getStatusTypeID().equals("BRJ"))
			{
				bufferDetailVO.setApprovedAmt(null);
				bufferDetailVO.setApprovedBy(null);
				bufferDetailVO.setApprovedDate(null);
				bufferDetailVO.setApprovedTime(null);
				bufferDetailVO.setApprovedDay(null);
			}//end of if(bufferDetailVO.getStatusTypeID().equals("BRJ"))
			bufferDetailVO.setStatusTypeID("BSR");
			if(strLink.equals(strClaims))
			{
				bufferDetailVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
				bufferDetailVO.setPreAuthSeqID(null);
				bufferDetailVO.setPolicySeqID(ClaimsWebBoardHelper.getPolicySeqId(request));
				bufferDetailVO.setMemberSeqID(ClaimsWebBoardHelper.getMemberSeqId(request));
			}//end of if(strLink.equals(strClaims))
			else
			{
				bufferDetailVO.setPreAuthSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				bufferDetailVO.setClaimSeqID(null);
				bufferDetailVO.setPolicySeqID(PreAuthWebBoardHelper.getPolicySeqId(request));
				bufferDetailVO.setMemberSeqID(PreAuthWebBoardHelper.getMemberSeqId(request));
			}//end of else
			bufferDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			if(strLink.equals(strClaims))
			{
				lngBuffDetSeqId=preAuthSupportManagerObject.saveBufferDetail(bufferDetailVO,"CLM");
			}//end of if(strLink.equals(strClaims))
			else
			{
				lngBuffDetSeqId=preAuthSupportManagerObject.saveBufferDetail(bufferDetailVO,"PAT");
			}//end of else
			if(lngBuffDetSeqId>0)
			{
				if(frmBufferDetails.get("bufferDtlSeqID")!=null && !frmBufferDetails.get("bufferDtlSeqID").equals(""))
				{
					//set the appropriate message
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if
				else
				{
					//set the appropriate message
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
			}//end of if(lngUpdate>0)
			bufferDetailVO=preAuthSupportManagerObject.getBufferDetail(lngBuffDetSeqId,
																	TTKCommon.getUserSeqId(request),"Preauthorization");
			frmBufferDetails = (DynaActionForm)FormUtils.setFormValues("frmBufferDetails",bufferDetailVO,this,
																		mapping,request);
			frmBufferDetails.set("caption",String.valueOf(strCaption));
			
			//Added as per kOC 1216B change request
			this.formValue(bufferDetailVO,frmBufferDetails,request);
			//Added as per kOC 1216B change request
			request.getSession().setAttribute("frmBufferDetails",frmBufferDetails);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBufferError));
		}//end of catch(Exception exp)
	}//end of doSendRequest(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to Reset the Buffer Details.
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
											HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside BufferDetailsAction doReset");
			BufferDetailVO bufferDetailVO =null;
			String strForward="";
			String strClaimantname="";
			String strEnrollmentID="";
			String strClaimType=request.getParameter("patclmClaimType") ;//!= null?request.getParameter("patclmClaimType"):"NRML";
			String strBufferType=request.getParameter("patclmBufferType");//!= null?request.getParameter("patclmBufferType"):"CORB";
			log.info("strClaimType::"+strClaimType);
			log.info("strBufferType::"+strBufferType);
			
			
			DynaActionForm frmBufferDetails= (DynaActionForm) form;
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink=TTKCommon.getActiveLink(request);
			if(strLink.equals(strClaims)){
				strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals(strClaims))
			else if(strLink.equals(strPreAuthorization))
			{
				strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("Buffer Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
			if(strLink.equals(strPreAuthorization))
			{
				strForward=strBufferDetails;
			}//end of if(strLink.equals(strPreAuthorization))
			else if(strLink.equals(strClaims))
			{
				strForward=strClaimsBufferDetails;
			}// end of else if(strLink.equals(strClaims))
			if(!(TTKCommon.checkNull(frmBufferDetails.getString("bufferDtlSeqID")).equals("")))
			{
				if(strLink.equals("Claims"))
				{
					bufferDetailVO=preAuthSupportManagerObject.getBufferDetail(TTKCommon.getLong(
								frmBufferDetails.getString("bufferDtlSeqID")),TTKCommon.getUserSeqId(request),"CLM");
				}//end of if(strLink.equals("Claims"))
				else
				{
					bufferDetailVO=preAuthSupportManagerObject.getBufferDetail(TTKCommon.getLong(
							frmBufferDetails.getString("bufferDtlSeqID")),TTKCommon.getUserSeqId(request),"PAT");
				}//end of else
			}//end of if(!(TTKCommon.checkNull(frmBufferDetails.getString("bufferDtlSeqID")).equals("")))
			else
			{
				bufferDetailVO=new BufferDetailVO();
				//Object[] objArrayResult = new Object[3];
			//	Modified as per KOC 1216B Change request
				Object[] objArrayResult = new Object[11];
          		  //	Modified as per KOC 1216B Change request 
			/*	if(strLink.equals("Claims"))
				{
					objArrayResult = preAuthSupportManagerObject.getBufferAuthority(
					ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getActiveLink(request));
				}//end of if(strLink.equals("Claims"))
				else
				{
					objArrayResult = preAuthSupportManagerObject.getBufferAuthority(
					PreAuthWebBoardHelper.getPreAuthSeqId(request),TTKCommon.getActiveLink(request));
				}//end of else
*/				 ArrayList<Object> alBufferAuthoritylList = new ArrayList<Object>();
				 if(strLink.equals("Claims"))
					{
				     alBufferAuthoritylList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
					}else
					{
					alBufferAuthoritylList.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));	
					}
				    alBufferAuthoritylList.add(TTKCommon.getActiveLink(request));
				    alBufferAuthoritylList.add(strClaimType);
				    alBufferAuthoritylList.add(strBufferType);
				
				    
				    objArrayResult = preAuthSupportManagerObject.getBufferAuthority(alBufferAuthoritylList);
				
				
				if(objArrayResult[0]!=null){
					bufferDetailVO.setAdminAuthTypeID(objArrayResult[0].toString());
				}//end of if(objArrayResult[0]!=null)
				if(objArrayResult[1]!=null){
					bufferDetailVO.setAvailBufferAmt((BigDecimal)objArrayResult[1]);
				}//end of if(objArrayResult[1]!=null)
				if(objArrayResult[2]!=null){
					bufferDetailVO.setBufferRemarks((String)objArrayResult[2]);				
				}//end of if(objArrayResult[2]!=null)
				//1216B ChangeRequest Modifications going to add
				
				if(objArrayResult[3]!=null){
					bufferDetailVO.setBufferMode(objArrayResult[3].toString());
				}//end of if(objArrayResult[3]!=null)
				if(objArrayResult[4]!=null){
				  bufferDetailVO.setHrInsurerBuffAmount((BigDecimal)objArrayResult[4]);
				 }//end of if(objArrayResult[4]!=null)
				if(objArrayResult[5]!=null){
					bufferDetailVO.setMemberBufferAmt((BigDecimal)objArrayResult[5]);
				}//end of if(objArrayResult[5]!=null)
				if(objArrayResult[6]!=null){
					bufferDetailVO.setUtilizedMemberBuffer((BigDecimal)objArrayResult[6]);
				}//end of if(objArrayResult[6]!=null)
				if(objArrayResult[7]!=null){
					bufferDetailVO.setBufferFamilyCap((BigDecimal)objArrayResult[7]);
				}//end of if(objArrayResult[7]!=null)
				if(objArrayResult[8]!=null){
					bufferDetailVO.setClaimType((String)objArrayResult[8]);				
				}//end of if(objArrayResult[2]!=null)
				else{
					bufferDetailVO.setClaimType("");	
				}
				if(objArrayResult[9]!=null){
					if(objArrayResult[8].equals("NRML"))
					{
					  bufferDetailVO.setBufferType((String)objArrayResult[9]);
					}else
					{
						bufferDetailVO.setBufferType("");
					}
					 if(objArrayResult[8].equals("CRTL"))
					 {
					    bufferDetailVO.setBufferType1((String)objArrayResult[9]);
					}else
					{
						bufferDetailVO.setBufferType1("");
					}
			     }//end of if(objArrayResult[2]!=null)
					
				//1216B ChangeRequest Modifications 
			}//end of else
			frmBufferDetails = (DynaActionForm)FormUtils.setFormValues("frmBufferDetails",bufferDetailVO,this,
																					mapping,request);
			frmBufferDetails.set("caption",String.valueOf(strCaption));
			//Added as per kOC 1216B change request
			this.formValue(bufferDetailVO,frmBufferDetails,request);
			//Added as per kOC 1216B change request
			
			request.getSession().setAttribute("frmBufferDetails",frmBufferDetails);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBufferError));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
/*
 * 
 * method is utility method to make 0.00 when values are null from back end
 * 
 * 
 */
	
	 private void formValue(BufferDetailVO bufferDetailVO,DynaActionForm frmBufferDetails,HttpServletRequest request) throws TTKException{
		 
		/* if(bufferDetailVO.getRequestedAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
	    	{
			 frmBufferDetails.set("requestedAmt","0.00");
	    	}//end of if(bufferDetailVO.getRequestedAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
*/		 
		 if(bufferDetailVO.getMemberBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
	    	{
			 frmBufferDetails.set("memberBufferAmt","0.00");
	    	}//end of if(bufferDetailVO.getMemberBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
		 
		 if(bufferDetailVO.getUtilizedMemberBuffer().compareTo(TTKCommon.getBigDecimal("0"))==0)
	    	{
			 frmBufferDetails.set("utilizedMemberBuffer","0.00");
	    	}//end of if(bufferDetailVO.getUtilizedMemberBuffer().compareTo(TTKCommon.getBigDecimal("0"))==0)
		 
		 if(bufferDetailVO.getBufferFamilyCap().compareTo(TTKCommon.getBigDecimal("0"))==0)
	    	{
			 frmBufferDetails.set("bufferFamilyCap","0.00");
	    	}//end of if(bufferDetailVO.getBufferFamilyCap().compareTo(TTKCommon.getBigDecimal("0"))==0)
		 
		 if(bufferDetailVO.getHrInsurerBuffAmount().compareTo(TTKCommon.getBigDecimal("0"))==0)
	    	{
			 frmBufferDetails.set("hrInsurerBuffAmount","0.00");
	    	}//end of if(bufferDetailVO.getHrInsurerBuffAmount().compareTo(TTKCommon.getBigDecimal("0"))==0)
		 
		 if(bufferDetailVO.getAvailBufferAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
	    	{
			 frmBufferDetails.set("availBufferAmt","0.00");
	    	}//end of if(bufferDetailVO.getAvailBufferAmt.compareTo(TTKCommon.getBigDecimal("0"))==0)
		
		 
		/* if(bufferDetailVO.getApprovedAmt().compareTo(TTKCommon.getBigDecimal("0"))==0)
	    	{
			 frmBufferDetails.set("availBufferAmt","0.00");
	    	}//end of if(bufferDetailVO.getAvailBufferAmt.compareTo(TTKCommon.getBigDecimal("0"))==0)
*/
		 }

	
/**
	 * This method is used to close
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
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside BufferDetailsAction doClose");
			String strSupport="";
			String strClaimantname="";
			String strEnrollmentID="";
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink=TTKCommon.getActiveLink(request);
			if(strLink.equals(strClaims)){
				strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals(strClaims))
			else if(strLink.equals(strPreAuthorization))
			{
				strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of else
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("Buffer Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
			if(strLink.equals(strPreAuthorization))
			{
				strSupport=strSupportDoc;
			}//end of if(strLink.equals(strPreAuthorization))
			else if(strLink.equals(strClaims))
			{
				strSupport=strClaimsSupportDoc;
			}// end of else if(strLink.equals(strClaims))
			TableData tableData = TTKCommon.getTableData(request);
			if(tableData.getSearchData()!= null&& tableData.getSearchData().size()>0)
			{
				ArrayList alSupportDoc = preAuthSupportManagerObject.getSupportBufferList(tableData.getSearchData());
				tableData.setData(alSupportDoc, "Cancel");
				request.getSession().setAttribute("tableData",tableData);
				//reset the forward links after adding the records if any
				tableData.setForwardNextLink();
			}//end of if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			return this.getForward(strSupport, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBufferError));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthSupportManager getPreAuthSupportManagerObject() throws TTKException
	{
		PreAuthSupportManager preAuthSupportManager = null;
		try
		{
			if(preAuthSupportManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthSupportManager = (PreAuthSupportManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthSupportManagerBean!com.ttk.business.preauth.PreAuthSupportManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strBufferError);
		}//end of catch
		return preAuthSupportManager;
	}//end getPreAuthSupportManagerObject()
	
	/**
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
	public ActionForward doChangeValues(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception {
		try{
			setLinks(request);
			log.debug("Inside BufferDetails Action doAdd");
			BufferDetailVO bufferDetailVO =new BufferDetailVO();
			String strForward="";
			String strClaimantname="";
			DynaActionForm frmBufferDetails= (DynaActionForm) form;
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strLink=TTKCommon.getActiveLink(request);
			
			String strClaimType=request.getParameter("patclmClaimType") ;//!= null?request.getParameter("patclmClaimType"):"NRML";
			String strBufferType=request.getParameter("patclmBufferType");//!= null?request.getParameter("patclmBufferType"):"CORB";
			
			
			String strEnrollmentID="";
			if(strLink.equals(strClaims)){
				strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
				if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&
						ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())&&	ClaimsWebBoardHelper.getEnrollmentId(request)!= null)
			}//end of if(strLink.equals(strClaims))
			else if(strLink.equals(strPreAuthorization))
			{
				strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
				if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&&
						PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
				{
					strEnrollmentID= strEnrollmentID+" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+" ]";
				}//end of if(!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())&& PreAuthWebBoardHelper.getEnrollmentId(request)!= null)
			}//end fo else
			StringBuffer strCaption= new StringBuffer();
			strCaption.append("Buffer Details -").append("[").append(strClaimantname).append("]").append(strEnrollmentID);
			if(strLink.equals(strPreAuthorization))
			{
				strForward=strBufferDetails;
			}//end of if(strLink.equals(strPreAuthorization))
			else if(strLink.equals(strClaims))
			{
				strForward=strClaimsBufferDetails;
			}// end of else if(strLink.equals(strClaims))
			
							
			//Changes as per KOC 1216B change array length to 8
			Object[] objArrayResult = new Object[11];
			//Changes as per KOC 1216B
			 ArrayList<Object> alBufferAuthoritylList = new ArrayList<Object>();
			 if(strLink.equals("Claims"))
				{
			     alBufferAuthoritylList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				}else
				{
				alBufferAuthoritylList.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));	
				}
			    alBufferAuthoritylList.add(TTKCommon.getActiveLink(request));
			    alBufferAuthoritylList.add(strClaimType);
			    alBufferAuthoritylList.add(strBufferType);
			
			    
			    objArrayResult = preAuthSupportManagerObject.getBufferAuthority(alBufferAuthoritylList);
			/*if(strLink.equals("Claims"))
			{
				//objArrayResult = preAuthSupportManagerObject.getBufferAuthority(
										//ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getActiveLink(request));
			}//end of if(strLink.equals("Claims"))
			else
			{
				objArrayResult = preAuthSupportManagerObject.getBufferAuthority(
									PreAuthWebBoardHelper.getPreAuthSeqId(request),TTKCommon.getActiveLink(request));
			}//end of else
*/			if(objArrayResult[0]!=null){
				bufferDetailVO.setAdminAuthTypeID(objArrayResult[0].toString());
			}//end of if(objArrayResult[0]!=null)
			if(objArrayResult[1]!=null){
				bufferDetailVO.setAvailBufferAmt((BigDecimal)objArrayResult[1]);
			}//end of if(objArrayResult[1]!=null)
			if(objArrayResult[2]!=null){
				bufferDetailVO.setBufferRemarks((String)objArrayResult[2]);				
			}//end of if(objArrayResult[2]!=null)
			//1216B ChangeRequest Modifications going to add
			if(objArrayResult[3]!=null){
				bufferDetailVO.setBufferMode(objArrayResult[3].toString());
			}//end of if(objArrayResult[3]!=null)
			if(objArrayResult[4]!=null){
			  bufferDetailVO.setHrInsurerBuffAmount((BigDecimal)objArrayResult[4]);
			 }//end of if(objArrayResult[4]!=null)
			if(objArrayResult[5]!=null){
				bufferDetailVO.setMemberBufferAmt((BigDecimal)objArrayResult[5]);
			}//end of if(objArrayResult[5]!=null)
			if(objArrayResult[6]!=null){
				bufferDetailVO.setUtilizedMemberBuffer((BigDecimal)objArrayResult[6]);
			}//end of if(objArrayResult[6]!=null)
			if(objArrayResult[7]!=null){
				bufferDetailVO.setBufferFamilyCap((BigDecimal)objArrayResult[7]);
			}//end of if(objArrayResult[7]!=null)
			
			if(objArrayResult[8]!=null){
				bufferDetailVO.setClaimType((String)objArrayResult[8]);				
			}//end of if(objArrayResult[2]!=null)
			else{
				bufferDetailVO.setClaimType("");	
			}
			if(objArrayResult[9]!=null){
				if(objArrayResult[9].equals("NRML")){
				bufferDetailVO.setBufferType((String)objArrayResult[9]);
				}
				else if(objArrayResult[9].equals("CRTL")){
				bufferDetailVO.setBufferType1((String)objArrayResult[9]);
					}
		}//end of if(objArrayResult[2]!=null)
			else{
				bufferDetailVO.setBufferType("");	
				bufferDetailVO.setBufferType1("");
			}
		
			//1216B ChangeRequest Modifications 				
			frmBufferDetails = (DynaActionForm)FormUtils.setFormValues("frmBufferDetails",bufferDetailVO,this,
																							mapping,request);
			frmBufferDetails.set("caption",String.valueOf(strCaption));
			frmBufferDetails.set("HrAppYN",((String)objArrayResult[10]));
			//Added as per kOC 1216B change request
			this.formValue(bufferDetailVO,frmBufferDetails,request);
			//Added as per kOC 1216B change request
			request.getSession().setAttribute("frmBufferDetails",frmBufferDetails);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBufferError));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	//added for hyundai bufer
	
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
	 public ActionForward doViewFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    												HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the EmployeeUploadFileList doViewFile  ");
    		setLinks(request);
    		String fileuploadbatch = "";
    		String fileDestination="";
    		BufferDetailVO bufferDetailVO =null;
    		DynaActionForm frmBufferDetails= (DynaActionForm) form;
    		fileuploadbatch = TTKPropertiesReader.getPropertyValue("bufferattachdestination");
			
			String strBatchFileName=request.getParameter("fileExistYN");
			
			
    		File file = null;  		
    		fileDestination=fileuploadbatch+strBatchFileName;   	
    		log.info("fileDestination::::::"+fileDestination);
    		file = new File(fileDestination);
    	
    		if(file.exists() )
    		{
    			if(fileDestination.endsWith("pdf") || fileDestination.endsWith("doc") || fileDestination.endsWith("docx"))
    					{
    					request.setAttribute("fileExistYN",fileDestination);
    					}
    		}//end of if(file.exists())
    		return mapping.findForward("uploadedfilelist");//finally return to the grid screen
	   	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"online"));
		}//end of catch(Exception exp)
    }//end of doViewFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
		 public ActionForward doViewFilePdf(ActionMapping mapping,ActionForm form,HttpServletRequest request,
						HttpServletResponse response) throws Exception{
				 try{
						log.info("Inside BufferDetailsAction doViewFilePdf");
						setLinks(request);
						return (mapping.findForward("reportdisplay"));
				
					}//end of try
				    catch(TTKException expTTK)
					{
				    	return this.processExceptions(request, mapping, expTTK);
					}//end of catch(TTKException expTTK)
					catch(Exception exp)
					{
						return this.processExceptions(request, mapping, new TTKException(exp,"online"));
					}//end of catch(Exception exp)
		}//end of doViewFilePdf(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
			 
	 
	//end added for hyundai bufer
}//end of BufferDetailsAction