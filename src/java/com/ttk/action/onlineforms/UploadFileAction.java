/**
 * @ (#) 1352 Feb 15, 2006
 * Project       : TTK HealthCare Services
 * File          : UploadFileAction.java
 * Reason        :File Upload console in Employee Login
 */

package com.ttk.action.onlineforms;

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

import sun.security.krb5.internal.*;

import com.ttk.action.TTKAction;
import com.ttk.action.tree.TreeData;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ConfigCopayVO;
import com.ttk.dto.enrollment.MemberVO;
import com.ttk.dto.enrollment.OnlineAccessPolicyVO;
import com.ttk.dto.onlineforms.UploadFileVO;
import com.ttk.dto.usermanagement.PasswordVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class UploadFileAction extends TTKAction {

	private static Logger log = Logger.getLogger( UploadFileAction.class );
	//Modes
	private static final String strSuccess="fileuploadsuccess";
	private static final String strFailure="fileuploadfail";
	private static final String strOnlineForms="online";
	private static final String strFileUpload="fileuploading";
	private static final String strMemberDetails="closeUploadFile";
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

			setOnlineLinks(request);
			DynaActionForm frmUpload= (DynaActionForm)form;
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManager();
			/*OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;*/
			UploadFileVO uploadFileVO=new UploadFileVO();
			//Long lngPolicyGroupSeqID=null;
			MemberVO memberVO =null;
			TreeData treeData = TTKCommon.getTreeData(request);
			String strSelectedRoot = TTKCommon.checkNull(request.getParameter("selectedroot"));
			String strSelectedNode = TTKCommon.checkNull(request.getParameter("selectednode"));
			if(!TTKCommon.checkNull(strSelectedRoot).equals(""))
			{
				memberVO = (MemberVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode); 			 				 				 				
			}//end of if(!TTKCommon.checkNull(strSelectedRoot).equals(""))

			String strCaption="";
			OnlineAccessPolicyVO onlinePolicyVO =null;   		 
			onlinePolicyVO = (OnlineAccessPolicyVO)request.getSession().getAttribute("SelectedPolicy");
			strCaption="[ "+TTKCommon.checkNull(memberVO.getPolicyNbr())+" ]"+"[ "+TTKCommon.checkNull(memberVO.getEnrollmentID())+" ]";
			uploadFileVO.setPolicyGroupSeqID(memberVO.getPolicyGroupSeqID().toString());
			uploadFileVO.setMemberSeqID(memberVO.getMemberSeqID().toString());
			uploadFileVO.setPolicySeqID(memberVO.getPolicySeqID().toString());	
			uploadFileVO.setRelationTypeID(memberVO.getRelationTypeID().toString());
			uploadFileVO.setRelationTypeID(memberVO.getPolicyNbr());
			uploadFileVO.setRelationTypeID(memberVO.getEnrollmentID());
			frmUpload = (DynaActionForm)FormUtils.setFormValues("frmUpload",uploadFileVO,	this,mapping,request);
			frmUpload.set("caption",strCaption);
			request.getSession().setAttribute("frmUpload",frmUpload);
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
		public ActionForward doSubmitFile(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		log.info("Inside submit File");
		DynaActionForm frmUpload = (DynaActionForm)form;
		ArrayList alFileDetails=null;
		Long lngFileData=null;
		String updated="";
		String strNotify="";
		String fileName="";
		UploadFileVO uploadFilesVO=null;
		FileOutputStream outputStream = null;
		FormFile formFile = null;
		  Pattern pattern =null;
		  Matcher matcher =null;
		int fileSize=1*1024*1024;
		try {
			TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");   
			DateFormat df =new SimpleDateFormat("ddMMyyyy HH:mm:ss:S");
			df.setTimeZone(tz);  
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManager();
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			//Get the FormFile object from ActionForm.
			uploadFilesVO =(UploadFileVO)FormUtils.getFormValues(frmUpload, "frmUpload",this, mapping, request);
			String strCaption="";
			strCaption="[ "+TTKCommon.checkNull(uploadFilesVO.getPolicyNbr())+" ]"+"[ "+TTKCommon.checkNull(uploadFilesVO.getEnrollmentID())+" ]";
			formFile = (FormFile)frmUpload.get("file");
			String 	strTimeStamp=((df.format(Calendar.getInstance(tz).getTime())).replaceAll(" ", "_")).replaceAll(":", ""); 
			
			
			pattern = Pattern.compile( "[^a-zA-Z0-9\\.\\s*]" );
			matcher = pattern.matcher( formFile.getFileName() );
		if(!matcher.find())
		{
			fileName=strTimeStamp+"_"+formFile.getFileName();
			
			uploadFilesVO.setFileName(fileName);
			uploadFilesVO.setUpdatedBy(new Long(1));
			alFileDetails=this.populateSearchCriteria(uploadFilesVO);
			String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("attachdestination"))+userSecurityProfile.getGroupID()+"/";
			/*if(path.equals(""))
			{
				path="E:\\home\\jboss\\jboss-4.0.2\\bin\\testFile\\";
			}
			//formFile.getFileName();*/

			File folder = new File(path);
			if(!folder.exists()){
				folder.mkdir();
			}
			path=path+userSecurityProfile.getTPAEnrolNbr()+"/";
			File folder1 = new File(path);
			if(!folder1.exists()){
				folder1.mkdir();
			}
			String finalPath=(path+fileName);
			String strFileExt=formFile.getFileName().substring(formFile.getFileName().lastIndexOf(".")+1,formFile.getFileName().length());
		
			if(formFile.getFileSize()<=fileSize)
			{
	
					if(strFileExt.equalsIgnoreCase("pdf"))
				{   
						if(!(uploadFilesVO.getRemarks().trim().equalsIgnoreCase("")))
						{
						outputStream = new FileOutputStream(new File(finalPath));
					    outputStream.write(formFile.getFileData());
					       lngFileData=onlineAccessManagerObject.saveEmployeeFileDetails(alFileDetails);
					              if(lngFileData>0)
					                {
					                       	updated="The file "+fileName+" is uploaded successfully.";
					                }//end of if(lngFileData>0)
					                /*else
					                {
						            new File(finalPath).delete();
						             }*/
					           uploadFilesVO.setRemarks("");
					           uploadFilesVO.setDocumentType("claim");
					           uploadFilesVO.setFile(null);
				 	           frmUpload = (DynaActionForm)FormUtils.setFormValues("frmUpload",uploadFilesVO,this,mapping,request);
				        }// end of if(!(uploadFilesVO.getRemarks().trim().equalsIgnoreCase("")))
				        else{
							//updated="Please enter valid  remarks ";
					        strNotify="Please enter valid  remarks ";
					        frmUpload = (DynaActionForm)FormUtils.setFormValues("frmUpload",uploadFilesVO,this,mapping,request);
				             }//end  else of if(!(uploadFilesVO.getRemarks().trim().equalsIgnoreCase("")))
			    }//end of if(strFileExt.equalsIgnoreCase("pdf"))
			else{

				             //  updated="selected "+formFile.getFileName()+" file is not in pdf format,Please select pdf file";
				               strNotify="selected "+formFile.getFileName()+" file is not in pdf format,Please select pdf file";
				               frmUpload = (DynaActionForm)FormUtils.setFormValues("frmUpload",uploadFilesVO,this,mapping,request);
			   }//end else of if(strFileExt.equalsIgnoreCase("pdf"))
            }//end of if(formFile.getFileSize()<=fileSize)
           else{
	            strNotify="selected "+formFile.getFileName()+" size  Shold be less than or equal to 1 MB";
                }//end  else of if(formFile.getFileSize()<=fileSize)
				
	    }
		
		else{
		//updated="selected "+formFile.getFileName()+" file is not in pdf format,Please select pdf file";
		strNotify="selected "+formFile.getFileName()+" file Should not have Special Characters like }!@#$%^&amp;*(])[{+";
		
		}
			frmUpload.set("updated",updated);
			request.getSession().setAttribute("frmUpload", frmUpload);
			request.setAttribute("updated",updated);
			request.setAttribute("notify",strNotify);
			frmUpload.set("caption",strCaption);
			return mapping.findForward(strSuccess);
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
			HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doClose method of UploadFileAction");
			setOnlineLinks(request);    	

			return mapping.findForward(strMemberDetails);
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
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param frmCardPrinting DynaActionForm
	 * @param request HttpServletRequest
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(UploadFileVO  uploadFilesVO)throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alFileDetails = new ArrayList<Object>();
		alFileDetails.add(uploadFilesVO.getFileName());
		alFileDetails.add(uploadFilesVO.getRemarks().trim());
		alFileDetails.add(uploadFilesVO.getDocumentType());
		alFileDetails.add(uploadFilesVO.getUpdatedBy());
		alFileDetails.add(uploadFilesVO.getPolicyGroupSeqID());
		alFileDetails.add(uploadFilesVO.getMemberSeqID());
		alFileDetails.add(uploadFilesVO.getPolicySeqID());
	    alFileDetails.add(uploadFilesVO.getPolicyGroupSeqID());
		return alFileDetails;
	}//end of populateSearchCriteria(DynaActionForm searchFeedbackForm,HttpServletRequest request)



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
	
	
}//end of  UploadFileAction.java
