package com.ttk.action.preauth;
//1274A 
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;



import com.ttk.action.TTKAction;


import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;


import com.ttk.dto.preauth.UnfreezeUploadFileVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class UploadFileAction extends TTKAction {

	private static Logger log = Logger.getLogger( UploadFileAction.class );
	//Modes
	private static final String strSuccess="success";
	private static final String strSuccess1="success1";
	private static final String strFailure="fail";
	private static final String strPreAuth="preauth";
	private static final String strFileUpload="fileuploading";
	private static final String strPreAuthorization="Pre-Authorization";
	private static final String strClaims="Claims";
	//1352
	/**
	 * This method is used to get the information of the employee.
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
			log.debug("Inside the doDefault method of UploadFileAction");

			setLinks(request);
			DynaActionForm frmUnfreezePreauth= (DynaActionForm)form;
			PreAuthManager preAuthManager=this.getPreAuthManagerObject();
			/*OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;*/
			UnfreezeUploadFileVO uploadFileVO=new UnfreezeUploadFileVO();
			String strActiveLink=TTKCommon.getActiveLink(request);

			//Long lngPolicyGroupSeqID=null;
			ArrayList<Object> alFileAUploadList = new ArrayList<Object>();
			StringBuffer strCaption=new StringBuffer();

			strCaption.append("UnFreeze    -");			
			if(strActiveLink.equals(strPreAuthorization))
			{
				alFileAUploadList.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				alFileAUploadList.add(null);
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+PreAuthWebBoardHelper.getClaimantName(request)+ " ]");
					strCaption.append(" [ "+TTKCommon.getWebBoardDesc(request)+ " ]");
					strCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				alFileAUploadList.add(null);
				alFileAUploadList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+ClaimsWebBoardHelper.getClaimantName(request)+ " ]");
					strCaption.append(" [ "+TTKCommon.getWebBoardDesc(request)+ " ]");
					strCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}
			}//end of else if(strActiveLink.equals(strClaims))

			frmUnfreezePreauth.set("caption",strCaption.toString());
			frmUnfreezePreauth.set("remarks","");
			request.getSession().setAttribute("frmUnfreezePreauth",frmUnfreezePreauth);

			return mapping.findForward(strSuccess);
			//return this.getForward(strSuccess, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strFileUpload));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
     * This method returns actual submit (.html,.mhtml,.msg,.rar,.zip,.pdf,.doc,.docx,.odt,.xls,.xlsx,.xml) Files with remarks and Type of Document
  	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
     */	
	
	public ActionForward doSubmitFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		log.info("Inside submit File");
		DynaActionForm frmUnfreezePreauth = (DynaActionForm)form;
		String strActiveLink=TTKCommon.getActiveLink(request);
		//ArrayList alFileDetails=null;
		Long lngFileData=null;
		String updated="";
		String strNotify="";
		String fileName="";
		Pattern pattern =null;
		Matcher matcher =null;
		UnfreezeUploadFileVO uploadFilesVO=null;
		FileOutputStream outputStream = null;
		FormFile formFile = null;
		int fileSize=1*1024*1024;
		try {
			TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");   
			DateFormat df =new SimpleDateFormat("ddMMyyyy HH:mm:ss:S");
			df.setTimeZone(tz);  
			PreAuthManager preAuthManager=this.getPreAuthManagerObject();
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			//Get the FormFile object from ActionForm.

			uploadFilesVO =(UnfreezeUploadFileVO)FormUtils.getFormValues(frmUnfreezePreauth, "frmUnfreezePreauth",this, mapping, request);
			//Long lngPolicyGroupSeqID=null;

			StringBuffer strCaption=new StringBuffer();
			ArrayList<Object> alFileAUploadList = new ArrayList<Object>();
			strCaption.append("UnFreeze   -");			
			if(strActiveLink.equals(strPreAuthorization))
			{
				alFileAUploadList.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));//0
				alFileAUploadList.add(null);//1
		
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+PreAuthWebBoardHelper.getClaimantName(request)+ " ]");
					strCaption.append(" [ "+TTKCommon.getWebBoardDesc(request)+ " ]");
					strCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				alFileAUploadList.add(null);//0
				alFileAUploadList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));//1
		
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+ClaimsWebBoardHelper.getClaimantName(request)+ " ]");
					strCaption.append(" [ "+TTKCommon.getWebBoardDesc(request)+ " ]");
					strCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}
			}//end of else if(strActiveLink.equals(strClaims))

			alFileAUploadList.add(TTKCommon.getUserSeqId(request));//2
			formFile = (FormFile)frmUnfreezePreauth.get("file");
			//pattern = Pattern.compile( "[^a-zA-Z0-9\\.\\s*]" );
			pattern = Pattern.compile( "[^a-zA-Z0-9_\\.\\-\\s*]" );
			matcher = pattern.matcher( formFile.getFileName() );

			String 	strTimeStamp=((df.format(Calendar.getInstance(tz).getTime())).replaceAll(" ", "_")).replaceAll(":", ""); 
			fileName=strTimeStamp+"_"+formFile.getFileName();
			if(!matcher.find())
			{
				uploadFilesVO.setFileName(fileName);
				uploadFilesVO.setUpdatedBy(new Long(1));
				//	alFileDetails=this.populateSearchCriteria(uploadFilesVO);
				String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("claimattachdestination"));
                
			if(path.equals(""))	{
					path="\\home\\jboss\\jboss-4.0.2\\bin\\unfreezedocs\\";
				}//
				File folder = new File(path);
				if(!folder.exists()){
					folder.mkdir();
				}

				String finalPath=(path+fileName);
         
				String strFileExt=formFile.getFileName().substring(formFile.getFileName().lastIndexOf(".")+1,formFile.getFileName().length());

				if(formFile.getFileSize()<=fileSize)
				{

					if((strFileExt.equalsIgnoreCase("html")) || (strFileExt.equalsIgnoreCase("mhtml")) || (strFileExt.equalsIgnoreCase("msg"))
							                                 || (strFileExt.equalsIgnoreCase("rar"))   || (strFileExt.equalsIgnoreCase("zip")) 
							                                 || (strFileExt.equalsIgnoreCase("pdf"))   || (strFileExt.equalsIgnoreCase("doc")) 
							                                 || (strFileExt.equalsIgnoreCase("docx"))  || (strFileExt.equalsIgnoreCase("txt")) 
							                                 || (strFileExt.equalsIgnoreCase("odt"))   || (strFileExt.equalsIgnoreCase("xml")) 
							                                 || (strFileExt.equalsIgnoreCase("xls"))   || (strFileExt.equalsIgnoreCase("xlsx")))
					{   
						if(!(uploadFilesVO.getRemarks().trim().equalsIgnoreCase("")))
						{
							outputStream = new FileOutputStream(new File(finalPath));
							outputStream.write(formFile.getFileData());

							alFileAUploadList.add(fileName);//3
							alFileAUploadList.add(uploadFilesVO.getRemarks());//4
							lngFileData=preAuthManager.saveUpoadedFilename(alFileAUploadList);

							if(lngFileData>0)
							{
								updated="The file "+fileName+" is uploaded successfully.";
								uploadFilesVO.setOverrideYN("Y");
							}//end of if(lngFileData>0)
							else{
								uploadFilesVO.setOverrideYN("N");
							}
							
							uploadFilesVO.setRemarks("");
							uploadFilesVO.setFile(null);
							 frmUnfreezePreauth = (DynaActionForm)FormUtils.setFormValues("frmUnfreezePreauth",uploadFilesVO,this,mapping,request);
						}// end of if(!(uploadFilesVO.getRemarks().trim().equalsIgnoreCase("")))

						else{
							//updated="Please enter valid  remarks ";
							strNotify="Please enter valid  remarks ";
							frmUnfreezePreauth = (DynaActionForm)FormUtils.setFormValues("frmUnfreezePreauth",uploadFilesVO,this,mapping,request);
						}// end of else if(!(uploadFilesVO.getRemarks().trim().equalsIgnoreCase("")))
					}//end of if(strFileExt.equalsIgnoreCase("html") || strFileExt.equalsIgnoreCase("mhtml")||strFileExt.equalsIgnoreCase("msg") || strFileExt.equalsIgnoreCase("rar")|| strFileExt.equalsIgnoreCase("zip") || strFileExt.equalsIgnoreCase("pdf") || strFileExt.equalsIgnoreCase("doc") || strFileExt.equalsIgnoreCase("docx") || strFileExt.equalsIgnoreCase("txt") || strFileExt.equalsIgnoreCase("odt") || strFileExt.equalsIgnoreCase("jrxml") || strFileExt.equalsIgnoreCase("xls") || strFileExt.equalsIgnoreCase("xlsx") )
					else{

						//updated="selected "+formFile.getFileName()+" file is not in pdf format,Please select pdf file";
						strNotify="selected "+formFile.getFileName()+" file should be any of these extensions (.html,.mhtml,.msg,.rar,.zip,.pdf,.doc,.docx,.odt,.xls,.xlsx,.xml)";
						frmUnfreezePreauth = (DynaActionForm)FormUtils.setFormValues("frmUnfreezePreauth",uploadFilesVO,this,mapping,request);
					}//end ofelse(strFileExt.equalsIgnoreCase("pdf"))
				}//end of if(formFile.getFileSize()<=fileSize)
				else{
					strNotify="selected "+formFile.getFileName()+" size  Shold be less than or equal to 1 MB";
				}//end of else part if(formFile.getFileSize()<=fileSize)
			}//end of if(!matcher.find())
			else{
				//updated="selected "+formFile.getFileName()+" file is not in pdf format,Please select pdf file";
				strNotify="selected "+formFile.getFileName()+" file Should not have Special Characters like }!@#$%^&amp;*(])[{+";

			}
			frmUnfreezePreauth.set("updated",updated);
			request.getSession().setAttribute("frmUnfreezePreauth", frmUnfreezePreauth);

			request.setAttribute("updated",updated);
			request.setAttribute("notify",strNotify);
			frmUnfreezePreauth.set("caption",strCaption.toString());
			return mapping.findForward("success");
		}//try
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strFileUpload));
		}//end of catch(Exception exp)
		finally {
			if (outputStream != null) {
				outputStream.close();
			}//if (outputStream != null)
		}//finally
	}//end submit file


	public ActionForward doUnfreezePatClm(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doUnfreezePatClm method of UploadFileAction");
			setLinks(request);
			DynaActionForm frmUnfreezePreauth= (DynaActionForm)form;
			PreAuthManager preAuthManager=this.getPreAuthManagerObject(); 
			String strActiveLink=TTKCommon.getActiveLink(request);
			StringBuffer strCaption=new StringBuffer();
			long lngFileData=0;
			String updated="";
			UnfreezeUploadFileVO uploadFilesVO=null;

			ArrayList<Object> alFileAUploadList = new ArrayList<Object>();
			uploadFilesVO =(UnfreezeUploadFileVO)FormUtils.getFormValues(frmUnfreezePreauth, "frmUnfreezePreauth",this, mapping, request);
			strCaption.append("UnFreeze Claim  -");			
			if(strActiveLink.equals(strPreAuthorization))
			{
				alFileAUploadList.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				alFileAUploadList.add(null);
				
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+PreAuthWebBoardHelper.getClaimantName(request)+ " ]");
					strCaption.append(" [ "+TTKCommon.getWebBoardDesc(request)+ " ]");
					strCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				alFileAUploadList.add(null);
				alFileAUploadList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				//alFileAUploadList.add("CLM");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+ClaimsWebBoardHelper.getClaimantName(request)+ " ]");
					strCaption.append(" [ "+TTKCommon.getWebBoardDesc(request)+ " ]");
					strCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}
			}//end of else if(strActiveLink.equals(strClaims))
			alFileAUploadList.add(TTKCommon.getUserSeqId(request));

			lngFileData=preAuthManager.accountUnfreeze(alFileAUploadList);

			if(lngFileData>0)
			{
				updated="PreAuth/Claim Updates Successfully";
				uploadFilesVO.setOverrideYN("N");
				uploadFilesVO.setRemarks("");
				uploadFilesVO.setFile(null);
			}


			frmUnfreezePreauth.set("updated",updated);
			frmUnfreezePreauth = (DynaActionForm)FormUtils.setFormValues("frmUnfreezePreauth",uploadFilesVO,this,mapping,request);
			request.getSession().setAttribute("frmUnfreezePreauth", frmUnfreezePreauth);
			request.setAttribute("updated",updated);
			frmUnfreezePreauth.set("caption",strCaption.toString());
			return mapping.findForward(strSuccess);
			
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strFileUpload));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
				
			}//end of if(preAuthManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "strFileUpload");
		}//end of catch
		return preAuthManager;
	}//end getPreAuthManagerObject()
	
	
	
	

}
