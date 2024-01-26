 /**
  * @ (#) OnlineInsCompFileUploadAction.java January 22, 2014
  * Project      : Vidal Health TPA
  * File         : OnlineInsCompFileUploadAction.java
  * Author       : Thirumalai K P
  * Company      : Span Systems Corporation
  * Date Created : January 22, 2014
  *
  * @author       :Thirumalai K P
  * Modified by   :
  * Modified date :
  * Reason        : File upload in insurance company login -- ins file upload
  */

package com.ttk.action.onlineforms;

import java.io.File;
import java.io.FileOutputStream;
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
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.onlineforms.InsFileUploadVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class OnlineInsCompFileUploadAction extends TTKAction{

	private static final Logger log = Logger.getLogger( OnlineInsCompFileUploadAction.class );

	 //declaration of forward paths
	 private static final String strInsFileUpload="insfileuploadt";
	 private static final String strUploadSuccess="insfileuploadsuccess";
	 private static final String strUploadFailure="insfileuploadfail";
	 
	 private static final String strHospitalFileUpload="hospitalfileupload";
	 private static final String strHospitalDocuments="Documents";
	 private static final String strHospUploadSuccess="uploadsuccess";
	 private static final String strHosUploadFailure="uploadfail";
	 //declaration of constants	 
	 private static final String strInsCompFileUpload="File Upload";

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
	 public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		 try{
				log.debug("Inside the doDefault method of OnlineInsCompFileUploadAction");
				setOnlineLinks(request);
				String strForwardPath="";
				DynaActionForm frmInsFileUpload = (DynaActionForm)form;
				InsFileUploadVO inFileUploadVO = new InsFileUploadVO();
				UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
				String strActiveSubLink=TTKCommon.getActiveSubLink(request);
				String strCaption = "";

				if(strActiveSubLink.equalsIgnoreCase(strInsCompFileUpload))
				{
				strCaption = "["+TTKCommon.checkNull(userSecurityProfile.getUSER_ID())+"]"+"["+TTKCommon.checkNull(userSecurityProfile.getGroupID())+"]"+"["+TTKCommon.checkNull(userSecurityProfile.getUSER_SEQ_ID())+"]";
				inFileUploadVO.setInsCompCode(userSecurityProfile.getGroupID());
				inFileUploadVO.setLoginUserId(userSecurityProfile.getUSER_ID());
				inFileUploadVO.setUserSeqId(userSecurityProfile.getUSER_SEQ_ID());
				}
				else if(strActiveSubLink.equalsIgnoreCase(strHospitalDocuments))				{
					strCaption = "["+TTKCommon.checkNull(userSecurityProfile.getUSER_ID())+"]"+"["+TTKCommon.checkNull(userSecurityProfile.getEmpanelNumber())+"]"+"["+TTKCommon.checkNull(userSecurityProfile.getHospSeqId())+"]";
					inFileUploadVO.setEmpanelCode(userSecurityProfile.getEmpanelNumber());
					inFileUploadVO.setLoginUserId(userSecurityProfile.getUSER_ID());
					inFileUploadVO.setUserSeqId(userSecurityProfile.getHospSeqId());
				}
				
				frmInsFileUpload = (DynaActionForm)FormUtils.setFormValues("frmInsFileUpload",inFileUploadVO,this,mapping,request);
				frmInsFileUpload.set("caption",strCaption);
				request.getSession().setAttribute("frmInsFileUpload",frmInsFileUpload);
				
				if(strActiveSubLink.equalsIgnoreCase(strInsCompFileUpload))
				{
					strForwardPath=strInsFileUpload;
				}//end of if(strActiveSubLink.equalsIgnoreCase(strInsCompReports))
				
				else if(strActiveSubLink.equalsIgnoreCase(strHospitalDocuments))
				{
					strForwardPath=strHospitalFileUpload;
				}

				return this.getForward(strForwardPath, mapping, request);
		 }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processOnlineExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processOnlineExceptions(request, mapping, new TTKException(exp,strInsFileUpload));
		 }//end of catch(Exception exp)
	 }//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
	 /**
	  * This method returns actual submit Pdf Files with remarks and Type of Document
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	public ActionForward doSubmitUpload(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		log.info("Inside the doSubmitUpload method of OnlineInsCompFileUploadAction");
		DynaActionForm frmInsFileUpload = (DynaActionForm)form;
		ArrayList alFileDetails = null;
		Long iResult = null;
		String updated = "";
		String notify = "";
		String fileName = "";
		InsFileUploadVO inFileUploadVO = null;
		FileOutputStream outputStream = null;
		FormFile formFile = null;

		Pattern pattern = null;
		Matcher matcher = null;
	
		int fileSize = 2*1024*1024;
		
		try {
			TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");   
			DateFormat df =new SimpleDateFormat("ddMMyyyy HH:mm:ss:S");
			df.setTimeZone(tz);  
			OnlineAccessManager onlineAccessManagerObject = this.getOnlineAccessManager();
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			//Get the FormFile object from ActionForm.
			inFileUploadVO =(InsFileUploadVO)FormUtils.getFormValues(frmInsFileUpload, "frmInsFileUpload",this, mapping, request);
			String strCaption = "";
			strCaption = "["+TTKCommon.checkNull(inFileUploadVO.getLoginUserId())+"]"+"["+TTKCommon.checkNull(inFileUploadVO.getInsCompCode())+"]"+"["+TTKCommon.checkNull(inFileUploadVO.getUserSeqId())+"]";
			
			formFile = (FormFile)frmInsFileUpload.get("file");
			String 	strTimeStamp = ((df.format(Calendar.getInstance(tz).getTime())).replaceAll(" ", "_")).replaceAll(":", ""); 
						
			pattern = Pattern.compile( "[^a-zA-Z0-9_\\-\\.\\s*]" );
			matcher = pattern.matcher( formFile.getFileName() );
			
			if(!matcher.find())
			{
				fileName = strTimeStamp+"_"+formFile.getFileName();
				inFileUploadVO.setFileName(fileName);
				inFileUploadVO.setUpdatedBy(new Long(1));
				alFileDetails = this.populateSearchCriteria(inFileUploadVO);
				
				String path = TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("inscompfileattachement"))+userSecurityProfile.getGroupID()+"/";

				File folder = new File(path);
				
				if(!folder.exists())
				{
					folder.mkdir();
				}
				
/*				File folder1 = new File(path);
				
				if(!folder1.exists())
				{
					folder1.mkdir();
				}
*/				
				String finalPath = (path+fileName);
				String strFileExt = formFile.getFileName().substring(formFile.getFileName().lastIndexOf(".")+1,formFile.getFileName().length());
			
				if(formFile.getFileSize()<=fileSize)
				{
					if(strFileExt.equals("xls") || (strFileExt.equals("xlsx")))
					{
						outputStream = new FileOutputStream(new File(finalPath));
						outputStream.write(formFile.getFileData());
						
						iResult = onlineAccessManagerObject.saveInsCompUploadDetails(alFileDetails);
						if(iResult>0)
						{
						   	updated = "File "+fileName+" is successfully uploaded.";
						}//end of if(iResult>0)

						inFileUploadVO.setFile(null);
						frmInsFileUpload = (DynaActionForm)FormUtils.setFormValues("frmInsFileUpload",inFileUploadVO,this,mapping,request);
				    }//end of if(strFileExt.equalsIgnoreCase("xls"))
					else if(strFileExt.equals("txt"))
					{
						outputStream = new FileOutputStream(new File(finalPath));
						outputStream.write(formFile.getFileData());
						
						iResult = onlineAccessManagerObject.saveInsCompUploadDetails(alFileDetails);
						if(iResult>0)
						{
						   	updated = "File "+fileName+" is successfully uploaded.";
						}//end of if(iResult>0)

						inFileUploadVO.setFile(null);
						frmInsFileUpload = (DynaActionForm)FormUtils.setFormValues("frmInsFileUpload",inFileUploadVO,this,mapping,request);
					}//end of else if(strFileExt.equalsIgnoreCase("xlsx"))
					else
					{
						//  updated="selected "+formFile.getFileName()+" file is not in pdf format,Please select pdf file";
						notify = "Selected "+formFile.getFileName()+" file is not in excel format, Please select Excel or CSV file.";
						frmInsFileUpload = (DynaActionForm)FormUtils.setFormValues("frmInsFileUpload",inFileUploadVO,this,mapping,request);
					}//end else of if(strFileExt.equalsIgnoreCase("xls"))
	            }//end of if(formFile.getFileSize()<=fileSize)
	           else
	           {
	        	   notify = "Selected "+formFile.getFileName()+" file size should be less than or equal to 2 MB.";
	           }//end  else of if(formFile.getFileSize()<=fileSize)
		    }
			else
			{
				//updated="selected "+formFile.getFileName()+" file is not in pdf format,Please select pdf file";
				notify = "Selected "+formFile.getFileName()+" file should not have Special Characters like }!@#$%^&amp;*()])[][{}!@#$%^&";
			}
				frmInsFileUpload.set("updated",updated);
				request.getSession().setAttribute("frmInsFileUpload", frmInsFileUpload);
				request.setAttribute("updated",updated);
				request.setAttribute("notify",notify);
				frmInsFileUpload.set("caption",strCaption);
				return mapping.findForward(strUploadSuccess);
		}//try
		 catch(TTKException expTTK)
		 {
			 return this.processOnlineExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsFileUpload));
		}//end of catch(Exception exp)
		finally {
			if (outputStream != null) {
				outputStream.close();
			}//if (outputStream != null)
		}//finally
	}

	
	
	 /**
	  * This method returns actual submit Pdf Files with remarks and Type of Document
	  * Finally it forwards to the appropriate view based on the specified forward mappings
	  * @param mapping The ActionMapping used to select this instance
	  * @param form The optional ActionForm bean for this request (if any)
	  * @param request The HTTP request we are processing
	  * @param response The HTTP response we are creating
	  * @return ActionForward Where the control will be forwarded, after this request is processed
	  * @throws Exception if any error occurs
	  */
	public ActionForward doHospitalUpload(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		log.info("Inside the doHospitalUpload method of OnlineInsCompFileUploadAction");
		DynaActionForm frmInsFileUpload = (DynaActionForm)form;
		ArrayList alFileDetails = null;
		Long iResult = null;
		String updated = "";
		String notify = "";
		String fileName = "";
		InsFileUploadVO inFileUploadVO = null;
		FileOutputStream outputStream = null;
		FormFile formFile = null;

		
		String path ="";
		Pattern pattern = null;
		Matcher matcher = null;
	
		int fileSize = 1*1024*1024;
		
		try {
			TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");   
			DateFormat df =new SimpleDateFormat("ddMMyyyy HH:mm:ss:S");
			df.setTimeZone(tz);  
			OnlineAccessManager onlineAccessManagerObject = this.getOnlineAccessManager();
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			//Get the FormFile object from ActionForm.
			inFileUploadVO =(InsFileUploadVO)FormUtils.getFormValues(frmInsFileUpload, "frmInsFileUpload",this, mapping, request);
			String strCaption = "";
			strCaption = "["+TTKCommon.checkNull(userSecurityProfile.getUSER_ID())+"]"+"["+TTKCommon.checkNull(userSecurityProfile.getEmpanelNumber())+"]"+"["+TTKCommon.checkNull(userSecurityProfile.getHospSeqId())+"]";
			
			formFile = (FormFile)frmInsFileUpload.get("file");
			String 	strTimeStamp = ((df.format(Calendar.getInstance(tz).getTime())).replaceAll(" ", "_")).replaceAll(":", ""); 
						
			pattern = Pattern.compile( "[^a-zA-Z0-9_\\.\\-\\s*]" );
			matcher = pattern.matcher(formFile.getFileName());
			
			if((((String)frmInsFileUpload.get("vidalId")).equalsIgnoreCase("")) )
			{	
				notify = "Please enter valid Vidal/TTK Id";
				frmInsFileUpload = (DynaActionForm)FormUtils.setFormValues("frmInsFileUpload",inFileUploadVO,this,mapping,request);
				//frmInsFileUpload.set("updated",updated);
				request.getSession().setAttribute("frmInsFileUpload", frmInsFileUpload);
				//request.setAttribute("updated",updated);
				request.setAttribute("notify",notify);
				frmInsFileUpload.set("caption",strCaption);
				return mapping.findForward(strHosUploadFailure);
			}
				/*else  if((((String)frmInsFileUpload.get("policyNumber")).equalsIgnoreCase("")) || (((String)frmInsFileUpload.get("policyType")).equalsIgnoreCase("INS")))
			{
				notify = "Please enter Policy Number";
				frmInsFileUpload = (DynaActionForm)FormUtils.setFormValues("frmInsFileUpload",inFileUploadVO,this,mapping,request);
				//frmInsFileUpload.set("updated",updated);
				request.getSession().setAttribute("frmInsFileUpload", frmInsFileUpload);
				//request.setAttribute("updated",updated);
				request.setAttribute("notify",notify);
				frmInsFileUpload.set("caption",strCaption);
				return mapping.findForward(strHosUploadFailure);
			}*/
	
			
			if(((String)formFile.getFileName()).equalsIgnoreCase(""))
			{
				notify = "Please select a valid file Location";
				frmInsFileUpload = (DynaActionForm)FormUtils.setFormValues("frmInsFileUpload",inFileUploadVO,this,mapping,request);
				//frmInsFileUpload.set("updated",updated);
				request.getSession().setAttribute("frmInsFileUpload", frmInsFileUpload);
			//	request.setAttribute("updated",updated);
				request.setAttribute("notify",notify);
				frmInsFileUpload.set("caption",strCaption);
				return mapping.findForward(strHosUploadFailure);

			}//end of  if(((String)formFile.getFileName()).equalsIgnoreCase(""))
			else
			{
				
				if(!matcher.find())
				{
				fileName = inFileUploadVO.getVidalId()+"_"+formFile.getFileName();
				inFileUploadVO.setFileName(fileName);
				inFileUploadVO.setUpdatedBy(new Long(1));
				alFileDetails = this.populateSearchCriteria1(inFileUploadVO);
				alFileDetails.add(userSecurityProfile.getHospSeqId());
				if(inFileUploadVO.getUploadFileType().equalsIgnoreCase("PAT"))
				{
					path = TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("hospitalpatdocuments"))+userSecurityProfile.getEmpanelNumber()+"/";
				}
				else{
					path = TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("hospitalclmdocuments"))+userSecurityProfile.getEmpanelNumber()+"/";
				}
		
				File folder = new File(path);
				
				if(!folder.exists())
				{
					folder.mkdir();
				}
				path=path+((String)frmInsFileUpload.get("vidalId")).trim()+"/";
				File folder1 = new File(path);
				
				if(!folder1.exists())
				{
					folder1.mkdir();
				}
			
				String finalPath = (path+fileName);
				String strFileExt = formFile.getFileName().substring(formFile.getFileName().lastIndexOf(".")+1,formFile.getFileName().length());
			
				if(formFile.getFileSize()<=fileSize)
				{
					if(strFileExt.equalsIgnoreCase("pdf"))
					{
						outputStream = new FileOutputStream(new File(finalPath));
					    outputStream.write(formFile.getFileData());
						
						iResult = onlineAccessManagerObject.saveHospitalFileUpload(alFileDetails);
						if(iResult>0)
						{
						   	updated = "File "+fileName+" is successfully uploaded.";
						}//end of if(iResult>0)

						inFileUploadVO.setFile(null);
						inFileUploadVO.setVidalId(null);
						inFileUploadVO.setRemarks(null);
						inFileUploadVO.setPolicyNumber(null);
						
						frmInsFileUpload = (DynaActionForm)FormUtils.setFormValues("frmInsFileUpload",inFileUploadVO,this,mapping,request);
				   }//end of if(strFileExt.equalsIgnoreCase("pdf"))
					/*else if(strFileExt.equals("txt"))
					{
						outputStream = new FileOutputStream(new File(finalPath));
						outputStream.write(formFile.getFileData());
						
						iResult = onlineAccessManagerObject.saveHospitalFileUpload(alFileDetails);
						if(iResult>0)
						{
						   	updated = "File "+fileName+" is successfully uploaded.";
						}//end of if(iResult>0)

						inFileUploadVO.setFile(null);
						frmInsFileUpload = (DynaActionForm)FormUtils.setFormValues("frmInsFileUpload",inFileUploadVO,this,mapping,request);
					}//end of else if(strFileExt.equalsIgnoreCase("xlsx"))
					 */					
						else
					{
								//  updated="selected "+formFile.getFileName()+" file is not in pdf format,Please select pdf file";
								notify = "Selected "+formFile.getFileName()+" file is not in pdf format, Please select pdf file.";
								frmInsFileUpload = (DynaActionForm)FormUtils.setFormValues("frmInsFileUpload",inFileUploadVO,this,mapping,request);
				   }//end else of if(strFileExt.equalsIgnoreCase("xls"))
	            }//end of if(formFile.getFileSize()<=fileSize)
	           else
	           {
	        	   notify = "Selected "+formFile.getFileName()+" file size should be less than or equal to 1 MB.";
	           }//end  else of if(formFile.getFileSize()<=fileSize)
		     }//end of if(!matcher.find())
			else
			{
				//updated="selected "+formFile.getFileName()+" file is not in pdf format,Please select pdf file";
				notify = "Selected "+formFile.getFileName()+" file should not have Special Characters like }!@#$%^&amp;*)(][{";
			}//end of else part of if(!matcher.find())
		 }//end of else part if(((String)formFile.getFileName()).equalsIgnoreCase(""))

				frmInsFileUpload.set("updated",updated);
				request.getSession().setAttribute("frmInsFileUpload", frmInsFileUpload);
				request.setAttribute("updated",updated);
				request.setAttribute("notify",notify);
				frmInsFileUpload.set("caption",strCaption);
				return mapping.findForward(strHospUploadSuccess);
		}//try
		 catch(TTKException expTTK)
		 {
			 return this.processOnlineExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		catch(Exception exp)
		{	
			return this.processExceptions(request, mapping, new TTKException(exp,strInsFileUpload));
		}//end of catch(Exception exp)
		
	}

	
	
	
	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param frmCardPrinting DynaActionForm
	 * @param request HttpServletRequest
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(InsFileUploadVO  inFileUploadVO)throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alFileDetails = new ArrayList<Object>();
		alFileDetails.add(inFileUploadVO.getFileName());
		alFileDetails.add(inFileUploadVO.getInsCompCode());
		alFileDetails.add(inFileUploadVO.getLoginUserId());
		alFileDetails.add(inFileUploadVO.getUploadFileType());
		alFileDetails.add(inFileUploadVO.getUserSeqId());
		return alFileDetails;
	}//end of populateSearchCriteria(DynaActionForm searchFeedbackForm,HttpServletRequest request)	
	
	
	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param frmCardPrinting DynaActionForm
	 * @param request HttpServletRequest
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria1(InsFileUploadVO  inFileUploadVO)throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alFileDetails = new ArrayList<Object>();
		
		alFileDetails.add(inFileUploadVO.getVidalId());
		alFileDetails.add(inFileUploadVO.getPolicyNumber());
		alFileDetails.add(inFileUploadVO.getFileName());
		//alFileDetails.add(inFileUploadVO.getEmpanelCode());
		//alFileDetails.add(inFileUploadVO.getLoginUserId());
		alFileDetails.add(inFileUploadVO.getUploadFileType());
		alFileDetails.add(inFileUploadVO.getSubFileType());
		alFileDetails.add(inFileUploadVO.getRemarks());
		return alFileDetails;
	}//end of populateSearchCriteria1(DynaActionForm searchFeedbackForm,HttpServletRequest request)	
	
	/**
	 * Returns the OnlineAccessManager session object for invoking methods on it.
	 * @return OnlineAccessManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private OnlineAccessManager getOnlineAccessManager() throws TTKException
	{
		OnlineAccessManager onlineAccessManager = null;
		try
		{
			if(onlineAccessManager == null)
			{
				InitialContext ctx = new InitialContext();
				onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
			}//end ofif(onlineAccessManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "");
		}//end of catch
		return onlineAccessManager;
	}//end getOnlineAccessManager()	
}
