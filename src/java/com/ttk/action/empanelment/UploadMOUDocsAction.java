/**
 * @ (#) UploadMOUDocsAction.java 31 Dec 2014
 * Project      : TTK HealthCare Services
 * File         :UploadMOUDocsAction.java
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 31 Dec 2014
 *
 * @author       : Kishor kumar S H
 * Modified by   : 
 * Modified date : 31 Dec 2014
 * Reason        : To Add Methods (doBrowse,doSelect,populatesFilesToList)
 */

package com.ttk.action.empanelment;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
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
import com.ttk.action.table.TableData;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.empanelment.HospitalDAOImpl;
import com.ttk.dto.administration.MOUDocumentVO;
import com.ttk.dto.empanelment.TdsCertificateVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;


public class UploadMOUDocsAction extends TTKAction{

	private static final Logger log = Logger.getLogger( UploadMOUDocsAction.class );
	
	//Action mapping forwards.
	private static final String strHospitalMouCertificatesList="hospitalMoucertificatelist";
	private static final String strCertificateList="certificatelist";
	private static final String strCertificatesClose="uploadsclose";
	private static final String strReportdisplay="reportdisplay";
	private static final String strHospGenInfo="hospgeninfo";
		
	//Exception Message Identifier
	private static final String strCertificate="certificate";
	 private static final String strPreAuthDocsList=	"preAuthDocsList";
	 private static final String strCertificatesClosePreAuth=	"uploadsclosePreAuth";
	
	/**
	 * This method is used to get List of Associated certificates for the for the Hospital.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward  doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException 
	{
		try
		{   
			String hosp_seq_id	=	(String)request.getParameter("hosp_seq_id");
			log.debug("Inside the doDefault method of UploadMOUDocsAction");
			request.getSession().setAttribute("hosp_seq_id_mou_uploads", hosp_seq_id);
			setLinks(request);
			String strTable = "";
			StringBuffer sbfCaption= new StringBuffer();	
			String strEmpanelNumber=null;
			DynaActionForm frmMOUDocs= null;
			if( "Y".equals( (String)request.getParameter("Entry") ) ) {
				frmMOUDocs = (DynaActionForm)form;
				frmMOUDocs.initialize(mapping);
			}//end of if(request.getParameter("Entry").equals("Y"))
			else {
				frmMOUDocs = (DynaActionForm) request.getSession().getAttribute("frmAddHospital");
				request.getSession().setAttribute("frmMOUDocs", frmMOUDocs);
				sbfCaption.append("[").append(frmMOUDocs.get("hospitalName")).append("]");	
				strEmpanelNumber=(String)frmMOUDocs.get("emplNumber");
				request.getSession().setAttribute("ConfigurationTitle", sbfCaption.toString());
				request.getSession().setAttribute("EmpanelNumber",strEmpanelNumber);
			}//end of else			
			
			ArrayList<Object> alMouCertificatesList = new ArrayList<Object>();
			HospitalManager hospitalObject=null;
			TableData tableDataMouCertificates =TTKCommon.getTableData(request);
			
			
			//code from after clicking the pencil icon in general screen
			/*String strEmpanelNumber=null;
			frmMOUDocs = (DynaActionForm)request.getSession().getAttribute("frmAddHospital");
			sbfCaption.append("[").append(frmMOUDocs.get("hospitalName")).append("]");	
			strEmpanelNumber=(String)frmMOUDocs.get("emplNumber");
			request.getSession().setAttribute("ConfigurationTitle", sbfCaption.toString());
			request.getSession().setAttribute("EmpanelNumber",strEmpanelNumber);
			sbfCaption = sbfCaption.append(this.buildCaption(request));	*/
			
					
			frmMOUDocs.set("caption",sbfCaption.toString());
			//get the table data from session if exists			
			if(tableDataMouCertificates==null){
				//create new table data object
				tableDataMouCertificates = new TableData();
			}//end of if(tableData==null) 	
			//create the required grid table
			strTable = "MouUploadFilesTable";
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(strSortID.equals(""))
			{
				tableDataMouCertificates.createTableInfo(strTable,null);
				tableDataMouCertificates.setSearchData(this.populateSearchCriteria(request));
				tableDataMouCertificates.modifySearchData("search");
			}//end of if(strSortID.equals(""))
			else
			{
				tableDataMouCertificates.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableDataMouCertificates.modifySearchData("sort");//modify the search data	
			}// end of else	
			hospitalObject=this.getHospitalManagerObject();
			alMouCertificatesList = hospitalObject.getMouUploadsList(hosp_seq_id);
			tableDataMouCertificates.setData(alMouCertificatesList,"search");
			request.getSession().setAttribute("tableDataMouCertificates",tableDataMouCertificates);
			request.getSession().setAttribute("frmMOUDocs",frmMOUDocs);
			return this.getForward(strHospitalMouCertificatesList, mapping, request);
		} // end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCertificate));
		}//end of catch(Exception exp)
	} // end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response) throws TTKException 
	
	
	
	
	
	
	
	
	
	
	
	public ActionForward doSavePreAuthDocs(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception
	{
		try{
			MOUDocumentVO mouDocumentVO	=	new MOUDocumentVO();
			DynaActionForm frmMOUDocs=(DynaActionForm)form;
			mouDocumentVO=(MOUDocumentVO)FormUtils.getFormValues(frmMOUDocs,this, mapping, request);
			String lPreAuthIntSeqId	=	(String)request.getSession().getAttribute("lPreAuthIntSeqId");
			mouDocumentVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			ArrayList alLinkDetailsList=null;
			TableData tableDataMouCertificates = null;
			if(request.getSession().getAttribute("tableDataMouCertificates")!=null) 
			{
				tableDataMouCertificates=(TableData)(request.getSession()).getAttribute("tableDataMouCertificates");
			}//end of if(request.getSession().getAttribute("tableDataMouCertificates")!=null)
			else
			{
				tableDataMouCertificates=new TableData();
			}//end of else
			mouDocumentVO=(MOUDocumentVO)FormUtils.getFormValues(frmMOUDocs,this, mapping, request);
			mouDocumentVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			//ConfigurationManager servConfigurationManager=this.getConfManager();
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			//..............File Upload from Local System.................
			
			
			
			int iUpdate	=	0;
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			Long lngFileData=null;
			String updated="";
			String strNotify="";
			String fileName="";
			String origFileName	=	"";
			Pattern pattern =null;
			Matcher matcher =null;
			FileOutputStream outputStream = null;
			FormFile formFile = null;
			int fileSize=10*1024*1024;
			TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");   
			DateFormat df =new SimpleDateFormat("ddMMyyyy HH:mm:ss:S");
			df.setTimeZone(tz);  
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			mouDocumentVO =(MOUDocumentVO)FormUtils.getFormValues(frmMOUDocs, "frmMOUDocs",this, mapping, request);
			//Get the FormFile object from ActionForm.
			StringBuffer strCaption=new StringBuffer();
			ArrayList<Object> alFileAUploadList = new ArrayList<Object>();
			alFileAUploadList.add(TTKCommon.getUserSeqId(request));//0
			Long userSeqId	=	(Long) TTKCommon.getUserSeqId(request);
			String hosp_seq_id_mou_uploads	=	(String)request.getSession().getAttribute("hosp_seq_id_mou_uploads");
			Long hospSeqId	=	userSecurityProfile.getHospSeqId();
			formFile = (FormFile)frmMOUDocs.get("file");
			String fileDesc	=	(String)frmMOUDocs.get("description");
			
			
			//pattern = Pattern.compile( "[^a-zA-Z0-9\\.\\s*]" );
			pattern = Pattern.compile( "[^a-zA-Z0-9_\\.\\-\\s*]" );
			matcher = pattern.matcher( formFile.getFileName() );
			String 	strTimeStamp=((df.format(Calendar.getInstance(tz).getTime())).replaceAll(" ", "_")).replaceAll(":", ""); 
			fileName=strTimeStamp+"_"+formFile.getFileName();
			origFileName	=	formFile.getFileName();
			InputStream inputStream	=	 formFile.getInputStream();//INPUT STREAM  FROM FILE UPLOADED
			int formFileSize	=	formFile.getFileSize();
			if(!matcher.find())
			{
				mouDocumentVO.setDocName(fileName);

				alFileAUploadList.add(fileName);//1
				alFileAUploadList.add(fileDesc);//2
				alFileAUploadList.add(hospSeqId);//3
				
				//	alFileDetails=this.populateSearchCriteria(webconfigInsCompInfoVO);
				String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("mouUploads"));
				if(path.equals(""))	{
					path="D:\\\\home\\\\jboss\\\\jboss-as-7.1.1.Final\\\\bin\\\\moudocs\\\\";
				}//
				File folder = new File(path);
				if(!folder.exists()){
					folder.mkdir();
				}
				String finalPath=(path+fileName);
				String strFileExt=formFile.getFileName().substring(formFile.getFileName().lastIndexOf(".")+1,formFile.getFileName().length());

				if(formFile.getFileSize()<=fileSize)
				{

					
					/*if((strFileExt.equalsIgnoreCase("pdf"))   || (strFileExt.equalsIgnoreCase("doc")) 
                            || (strFileExt.equalsIgnoreCase("docx")) || (strFileExt.equalsIgnoreCase("xls"))   
                            || (strFileExt.equalsIgnoreCase("xlsx")))
					{ 
					COMMENTING THESE LINES FOR ALLOWING TO UPLOAD ALL KINDS OF FILES
					*/  
						
							outputStream = new FileOutputStream(new File(finalPath));
							outputStream.write(formFile.getFileData());

							//alFileAUploadList.add(fileName);//3 This just Adds file name
							alFileAUploadList.add(finalPath);//3 This code adds Total path of the file
							//alFileAUploadList.add(webconfigInsCompInfoVO.getRemarks());//4
							
							 
					            
							iUpdate=hospitalObject.savepreAuthUploads(mouDocumentVO,alFileAUploadList,userSeqId,lPreAuthIntSeqId,origFileName,inputStream,formFileSize);
							if(iUpdate>0)
							{
								if(frmMOUDocs.get("mouDocSeqID")!=null)
								{
									//set the appropriate message
									request.setAttribute("updated","message.savedSuccessfully");
								}//end of if(frmMOUDocs.get("configLinkSeqID")!=null)
								else
								{
									//set the appropriate message
									request.setAttribute("updated","message.addedSuccessfully");
								}//end else
								frmMOUDocs.initialize(mapping);
								alLinkDetailsList=hospitalObject.getPreAuthUploadsList(lPreAuthIntSeqId);
								tableDataMouCertificates.setData(alLinkDetailsList);
								frmMOUDocs.set("caption",sbfCaption.toString());
								request.getSession().setAttribute("tableDataMouCertificates",tableDataMouCertificates);
								request.getSession().setAttribute("tableData",tableDataMouCertificates);
								request.setAttribute("frmMOUDocs",frmMOUDocs);
							}// end of if(iUpdate>0)
							
							frmMOUDocs = (DynaActionForm)FormUtils.setFormValues("frmMOUDocs",mouDocumentVO,this,mapping,request);
						
						
					/*}//end of if(strFileExt.equalsIgnoreCase("html") || strFileExt.equalsIgnoreCase("mhtml")||strFileExt.equalsIgnoreCase("msg") || strFileExt.equalsIgnoreCase("rar")|| strFileExt.equalsIgnoreCase("zip") || strFileExt.equalsIgnoreCase("pdf") || strFileExt.equalsIgnoreCase("doc") || strFileExt.equalsIgnoreCase("docx") || strFileExt.equalsIgnoreCase("txt") || strFileExt.equalsIgnoreCase("odt") || strFileExt.equalsIgnoreCase("jrxml") || strFileExt.equalsIgnoreCase("xls") || strFileExt.equalsIgnoreCase("xlsx") )
					else{

						strNotify="selected "+formFile.getFileName()+" file should be any of these extensions (.pdf,.doc,.docx,.xls,.xlsx)";
						frmMOUDocs = (DynaActionForm)FormUtils.setFormValues("frmMOUDocs",mouDocumentVO,this,mapping,request);
					}//end ofelse(strFileExt.equalsIgnoreCase("pdf"))
			COMMENTING THESE LINES FOR ALLOWING TO UPLOAD ALL KINDS OF FILES	  */
							
							
				}//end of if(formFile.getFileSize()<=fileSize)
				else{
					strNotify="selected "+formFile.getFileName()+" size  Shold be less than or equal to 10 MB";
				}//end of else part if(formFile.getFileSize()<=fileSize)
			}//end of if(!matcher.find())
			else{
				//updated="selected "+formFile.getFileName()+" file is not in pdf format,Please select pdf file";
				strNotify="selected "+formFile.getFileName()+" file Should not have Special Characters like }!@#$%^&amp;*(])[{+";

			}
			
			//frmMOUDocs.set("updated",updated);
			request.getSession().setAttribute("frmUpload", frmMOUDocs);
			request.setAttribute("notify",strNotify);
		
			//frmMOUDocs.set("caption",strCaption);
			//..............File Upload from Local System Ends...........
			return mapping.findForward(strPreAuthDocsList);
		}//end of try
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strCertificate));
		}//end of catch(Exception exp)
	}//end of doSavePreAuthDocs(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				//HttpServletResponse response)
	
	
	
	
	
	
	
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
			MOUDocumentVO mouDocumentVO	=	new MOUDocumentVO();
			DynaActionForm frmMOUDocs=(DynaActionForm)form;
			mouDocumentVO=(MOUDocumentVO)FormUtils.getFormValues(frmMOUDocs,this, mapping, request);
			String hosp_seq_id	=	(String)request.getSession().getAttribute("hosp_seq_id_mou_uploads");
			mouDocumentVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			ArrayList alLinkDetailsList=null;
			TableData tableDataMouCertificates = null;
			if(request.getSession().getAttribute("tableDataMouCertificates")!=null) 
			{
				tableDataMouCertificates=(TableData)(request.getSession()).getAttribute("tableDataMouCertificates");
			}//end of if(request.getSession().getAttribute("tableDataMouCertificates")!=null)
			else
			{
				tableDataMouCertificates=new TableData();
			}//end of else
			mouDocumentVO=(MOUDocumentVO)FormUtils.getFormValues(frmMOUDocs,this, mapping, request);
			mouDocumentVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			//ConfigurationManager servConfigurationManager=this.getConfManager();
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			//..............File Upload from Local System.................
			
			
			
			int iUpdate	=	0;
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			Long lngFileData=null;
			String updated="";
			String strNotify="";
			String fileName="";
			String origFileName	=	"";
			Pattern pattern =null;
			Matcher matcher =null;
			FileOutputStream outputStream = null;
			FormFile formFile = null;
			int fileSize=10*1024*1024;
			TimeZone tz = TimeZone.getTimeZone("Asia/Calcutta");   
			DateFormat df =new SimpleDateFormat("ddMMyyyy HH:mm:ss:S");
			df.setTimeZone(tz);  
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
			mouDocumentVO =(MOUDocumentVO)FormUtils.getFormValues(frmMOUDocs, "frmMOUDocs",this, mapping, request);
			//Get the FormFile object from ActionForm.
			StringBuffer strCaption=new StringBuffer();
			ArrayList<Object> alFileAUploadList = new ArrayList<Object>();
			alFileAUploadList.add(TTKCommon.getUserSeqId(request));//0
			Long userSeqId	=	(Long) TTKCommon.getUserSeqId(request);
			String hosp_seq_id_mou_uploads	=	(String)request.getSession().getAttribute("hosp_seq_id_mou_uploads");
			Long hospSeqId	=	userSecurityProfile.getHospSeqId();
			formFile = (FormFile)frmMOUDocs.get("file");
			String fileDesc	=	(String)frmMOUDocs.get("description");
			
			
			//pattern = Pattern.compile( "[^a-zA-Z0-9\\.\\s*]" );
			pattern = Pattern.compile( "[^a-zA-Z0-9_\\.\\-\\s*]" );
			matcher = pattern.matcher( formFile.getFileName() );
			String 	strTimeStamp=((df.format(Calendar.getInstance(tz).getTime())).replaceAll(" ", "_")).replaceAll(":", ""); 
			fileName=strTimeStamp+"_"+formFile.getFileName();
			origFileName	=	formFile.getFileName();
			InputStream inputStream	=	 formFile.getInputStream();//INPUT STREAM  FROM FILE UPLOADED
			int formFileSize	=	formFile.getFileSize();
			if(!matcher.find())
			{
				mouDocumentVO.setDocName(fileName);

				alFileAUploadList.add(fileName);//1
				alFileAUploadList.add(fileDesc);//2
				alFileAUploadList.add(hospSeqId);//3
				
				//	alFileDetails=this.populateSearchCriteria(webconfigInsCompInfoVO);
				String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("mouUploads"));
				if(path.equals(""))	{
					path="D:\\\\home\\\\jboss\\\\jboss-as-7.1.1.Final\\\\bin\\\\moudocs\\\\";
				}//
				File folder = new File(path);
				if(!folder.exists()){
					folder.mkdir();
				}
				String finalPath=(path+fileName);
				String strFileExt=formFile.getFileName().substring(formFile.getFileName().lastIndexOf(".")+1,formFile.getFileName().length());

				if(formFile.getFileSize()<=fileSize)
				{

					
					/*if((strFileExt.equalsIgnoreCase("pdf"))   || (strFileExt.equalsIgnoreCase("doc")) 
                            || (strFileExt.equalsIgnoreCase("docx")) || (strFileExt.equalsIgnoreCase("xls"))   
                            || (strFileExt.equalsIgnoreCase("xlsx")))
					{ 
					COMMENTING THESE LINES FOR ALLOWING TO UPLOAD ALL KINDS OF FILES
					*/  
						
							outputStream = new FileOutputStream(new File(finalPath));
							outputStream.write(formFile.getFileData());

							//alFileAUploadList.add(fileName);//3 This just Adds file name
							alFileAUploadList.add(finalPath);//3 This code adds Total path of the file
							//alFileAUploadList.add(webconfigInsCompInfoVO.getRemarks());//4
							
							 
					            
							iUpdate=hospitalObject.saveMouUploads(mouDocumentVO,alFileAUploadList,userSeqId,hosp_seq_id,origFileName,inputStream,formFileSize);
							if(iUpdate>0)
							{
								if(frmMOUDocs.get("mouDocSeqID")!=null)
								{
									//set the appropriate message
									request.setAttribute("updated","message.savedSuccessfully");
								}//end of if(frmMOUDocs.get("configLinkSeqID")!=null)
								else
								{
									//set the appropriate message
									request.setAttribute("updated","message.addedSuccessfully");
								}//end else
								frmMOUDocs.initialize(mapping);
								alLinkDetailsList=hospitalObject.getMouUploadsList(hosp_seq_id_mou_uploads);
								tableDataMouCertificates.setData(alLinkDetailsList);
								frmMOUDocs.set("caption",sbfCaption.toString());
								request.getSession().setAttribute("tableDataMouCertificates",tableDataMouCertificates);
								request.setAttribute("frmMOUDocs",frmMOUDocs);
							}// end of if(iUpdate>0)
							
							frmMOUDocs = (DynaActionForm)FormUtils.setFormValues("frmMOUDocs",mouDocumentVO,this,mapping,request);
						
						
					/*}//end of if(strFileExt.equalsIgnoreCase("html") || strFileExt.equalsIgnoreCase("mhtml")||strFileExt.equalsIgnoreCase("msg") || strFileExt.equalsIgnoreCase("rar")|| strFileExt.equalsIgnoreCase("zip") || strFileExt.equalsIgnoreCase("pdf") || strFileExt.equalsIgnoreCase("doc") || strFileExt.equalsIgnoreCase("docx") || strFileExt.equalsIgnoreCase("txt") || strFileExt.equalsIgnoreCase("odt") || strFileExt.equalsIgnoreCase("jrxml") || strFileExt.equalsIgnoreCase("xls") || strFileExt.equalsIgnoreCase("xlsx") )
					else{

						strNotify="selected "+formFile.getFileName()+" file should be any of these extensions (.pdf,.doc,.docx,.xls,.xlsx)";
						frmMOUDocs = (DynaActionForm)FormUtils.setFormValues("frmMOUDocs",mouDocumentVO,this,mapping,request);
					}//end ofelse(strFileExt.equalsIgnoreCase("pdf"))
			COMMENTING THESE LINES FOR ALLOWING TO UPLOAD ALL KINDS OF FILES	  */
							
							
				}//end of if(formFile.getFileSize()<=fileSize)
				else{
					strNotify="selected "+formFile.getFileName()+" size  Shold be less than or equal to 10 MB";
				}//end of else part if(formFile.getFileSize()<=fileSize)
			}//end of if(!matcher.find())
			else{
				//updated="selected "+formFile.getFileName()+" file is not in pdf format,Please select pdf file";
				strNotify="selected "+formFile.getFileName()+" file Should not have Special Characters like }!@#$%^&amp;*(])[{+";

			}
			
			//frmMOUDocs.set("updated",updated);
			request.getSession().setAttribute("frmUpload", frmMOUDocs);
			request.setAttribute("notify",strNotify);
		
			//frmMOUDocs.set("caption",strCaption);
			//..............File Upload from Local System Ends...........
			return mapping.findForward(strHospitalMouCertificatesList);
		}//end of try
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strCertificate));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				//HttpServletResponse response)
	
	/**
	 * This method is used to navigate to previous screen when close  button is clicked.
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
			HttpServletResponse response) throws TTKException
	{
		try
		{
			log.debug("Inside the doClose method of UploadMOUDocsAction");
			setLinks(request);
			return mapping.findForward(strCertificatesClose);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCertificate));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				//HttpServletResponse response)
	
	
	public ActionForward doClosepreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		try
		{
			log.debug("Inside the doClosepreAuth method of UploadMOUDocsAction");
			setLinks(request);
			return mapping.findForward(strCertificatesClosePreAuth);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCertificate));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				//HttpServletResponse response)
	
	
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
	public ActionForward doViewCertificateLinkDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		try{
			setLinks(request);
			log.debug("Inside LinkDetailsAction doViewLinkDetails");
			TdsCertificateVO tdsCertificateVO=null;
			StringBuffer sbfCaption= new StringBuffer();
			String strForwardPath=null;
			strForwardPath=strHospitalMouCertificatesList;
			DynaActionForm frmMOUDocs=(DynaActionForm)form;
			sbfCaption = sbfCaption.append(this.buildCaption(request));			
			//if rownumber is found populate the form object
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{				
				tdsCertificateVO=(TdsCertificateVO)((TableData)request.getSession().getAttribute("tableDataMouCertificates")).
				getData().get(Integer.parseInt(request.getParameter("rownum")));
				frmMOUDocs = (DynaActionForm)FormUtils.setFormValues("frmMOUDocs",tdsCertificateVO,
						this,mapping,request);
				log.info(frmMOUDocs.getString("rownum"));
			}// end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			else
			{
				frmMOUDocs.initialize(mapping);
			}// end of else
			frmMOUDocs.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmMOUDocs",frmMOUDocs);
			return this.getForward(strForwardPath,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strCertificate));
		}//end of catch(Exception exp)
	}//end of doViewCertificateLinkDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
	
	/**
	 * This method is used to delete selected record(s)from the db
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
	
		try{
		
			setLinks(request);
			log.debug("Inside AssociateCertificatessAction doDelete");
			StringBuffer sbfDeleteId = new StringBuffer();
			ArrayList <Object>alCertificateDelete=new ArrayList<Object>();
			ArrayList <Object>alAssociatedCertificatesList=null;
			DynaActionForm frmMOUDocs=(DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			String strForwardPath=strHospitalMouCertificatesList;
			sbfCaption = sbfCaption.append(this.buildCaption(request));
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			TableData tableDataMouCertificates = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataMouCertificates")!=null)
			{
				
				tableDataMouCertificates=(TableData)(request.getSession()).getAttribute("tableDataMouCertificates");
			}//end of if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			else
			{
				
				tableDataMouCertificates=new TableData();
			}//end of else
			sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().getAttribute("tableDataMouCertificates")));
			alCertificateDelete.add(String.valueOf(sbfDeleteId));
			frmMOUDocs.initialize(mapping);
			int iCount=hospitalObject.deleteAssocCertificatesInfo(alCertificateDelete,"tableDataMouCertificates");
			log.info("iCount :"+iCount);
			//refresh the data in order to get the new records if any.
			alAssociatedCertificatesList=hospitalObject.getAssocCertificateList(tableDataMouCertificates.getSearchData());
			tableDataMouCertificates.setData(alAssociatedCertificatesList);
			frmMOUDocs.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("tableDataMouCertificates",tableDataMouCertificates);
			request.getSession().setAttribute("frmMOUDocs",frmMOUDocs);
			return this.getForward(strForwardPath,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strCertificate));
		}//end of catch(Exception exp)
	}//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
	
	
	
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
			HttpServletResponse response) throws TTKException
	{
		try{
			log.debug("Inside the doReset method of UploadMOUDocsAction");
			//log.info("Inside the doReset method of UploadMOUDocsAction");
			setLinks(request);
			TdsCertificateVO tdsCertificateVO=null;
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			DynaActionForm frmMOUDocs=(DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			String strForwardPath=null;		
			strForwardPath=strHospitalMouCertificatesList;
			sbfCaption = sbfCaption.append(this.buildCaption(request));
			if(!TTKCommon.checkNull(frmMOUDocs.get("certSeqId")).equals("") )
			{
				tdsCertificateVO=hospitalObject.getCertiDetailInfo(TTKCommon.getLong(frmMOUDocs.getString("certSeqId")));
				frmMOUDocs=(DynaActionForm)FormUtils.setFormValues("frmMOUDocs",tdsCertificateVO,
						this,mapping,request);
			}//end of if(frmMOUDocs.get("certSeqId")!= null )
			else 
			{
				//log.info("inside or operator");
				frmMOUDocs.initialize(mapping);
			}//end of else
			frmMOUDocs.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmMOUDocs",frmMOUDocs);
			
			return this.getForward(strForwardPath,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCertificate));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				//HttpServletResponse response)
	
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
	public ActionForward doViewCertificate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doViewCertificate method of UploadMOUDocsAction");
			setLinks(request);
			TdsCertificateVO tdsCertificateVO=null;
			//DynaActionForm frmMOUDocs=(DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			String strCertPath=null;
			sbfCaption = sbfCaption.append(this.buildCaption(request));
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{				
				tdsCertificateVO=(TdsCertificateVO)((TableData)request.getSession().getAttribute("tableDataMouCertificates")).
				getData().get(Integer.parseInt(request.getParameter("rownum")));
			}
			strCertPath=tdsCertificateVO.getCertPath();
			log.info("strCertPath"+strCertPath);
			File file = new File(strCertPath);
			FileInputStream fis =null;
			if(file.exists())
			{
				fis = new FileInputStream(strCertPath);
			}//end of if(file.exists())
			else
			{
				fis = new FileInputStream("empty.pdf");
			}//end of else
			BufferedInputStream bis = new BufferedInputStream(fis);
			ByteArrayOutputStream boas=new ByteArrayOutputStream();
			int ch;
			while ((ch = bis.read()) != -1)
			{
				boas.write(ch);
			}//end of while ((ch = bis.read()) != -1)
			
			request.setAttribute("boas",boas);
			bis.close();
			return (mapping.findForward(strReportdisplay));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCertificate));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				//HttpServletResponse response)
	
	/**
     * This method is used to list out the document file names from the server 
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
	public ActionForward doBrowse(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		try{
			setLinks(request);
			log.debug("Inside AssociateCertificates doBrowse");
			final DynaActionForm frmMOUDocs = (DynaActionForm) form;
			String strDefaultDir = TTKPropertiesReader.getPropertyValue("AssociateHospDocs");
			ArrayList<Object> alFilePath = new ArrayList<Object>();
			StringBuffer sbfCaption= new StringBuffer();
            final String strEmpanelNumber=(String)request.getSession().getAttribute("EmpanelNumber");
            sbfCaption = sbfCaption.append(this.buildCaption(request));
			// to filter the files to be displayed based on the HospitalEmpanelmentNumber,Financial year
			FilenameFilter docFilterObj = new FilenameFilter() {
			public boolean accept(File dir, String name) {
					return (name.startsWith((strEmpanelNumber)) && name.endsWith(".pdf") );
				}//end of accept(File dir, String name)
			};//end of FilenameFilter docFilterObj = new FilenameFilter() 			
			File defaultDir = new File(strDefaultDir);
			File[] defFileArrObj = defaultDir.listFiles(docFilterObj);
			if(defFileArrObj.length>0)
			{
				alFilePath = this.populateFilesToList(defFileArrObj,alFilePath);
			}//end of if(defFileArrObj.length>0)
			frmMOUDocs.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmMOUDocs",frmMOUDocs);			
			request.setAttribute("alFileList",alFilePath);
			return this.getForward(strCertificateList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strCertificate));
		}//end of catch(Exception exp)
	}//end of doBrowse(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doSelect(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		try{
			setLinks(request);
			log.debug("Inside AssociateCertificationAction doSelect");
			log.info("Inside do select--latest");
			TableData tableDataMouCertificates =TTKCommon.getTableData(request);
			DynaActionForm frmMOUDocs= null;
			frmMOUDocs = (DynaActionForm) request.getSession().getAttribute("frmMOUDocs");
			ArrayList<Object> alAssociatedCertificatesList=null;
			HospitalManager hospitalManager=this.getHospitalManagerObject();
			if(frmMOUDocs!=null){
				frmMOUDocs.set("certPath",((DynaActionForm)form).getString("certPath"));
			}//end of if(request.getSession().getAttribute("frmLinkDetails")!=null)
			else {
				frmMOUDocs = (DynaActionForm)form;
			}//end of else	
			if(((DynaActionForm)form).getString("certPath")!=null){
				frmMOUDocs.set("certPath",((DynaActionForm)form).getString("certPath"));
			}//end of if(((DynaActionForm)form).getString("certPath")!=null)
			else {
				frmMOUDocs.set("certPath","");
			}//end of else
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption = sbfCaption.append(this.buildCaption(request));
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataMouCertificates")!=null)
			{
				tableDataMouCertificates=(TableData)(request.getSession()).getAttribute("tableDataMouCertificates");
			}//end of if(request.getSession().getAttribute("tableDataAssociatedDocuments")!=null)
			else
			{
				tableDataMouCertificates=new TableData();
			}//end of else
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strSortID.equals(""))
			{
				tableDataMouCertificates.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableDataMouCertificates.modifySearchData("sort");//modify the search data
			}// end of if(!strSortID.equals(""))
			else
			{
				tableDataMouCertificates.createTableInfo("AssociatedCertificatesTable",null);
				tableDataMouCertificates.setSearchData(this.populateSearchCriteria(request));
				tableDataMouCertificates.modifySearchData("search");
			}// end of else
			alAssociatedCertificatesList=hospitalManager.getAssocCertificateList(tableDataMouCertificates.getSearchData());
			tableDataMouCertificates.setData(alAssociatedCertificatesList);
			request.getSession().setAttribute("tableDataMouCertificates",tableDataMouCertificates);
			frmMOUDocs.set("caption",sbfCaption.toString());
			//log.info("Caption:" +frmMOUDocs);
			request.getSession().setAttribute("frmMOUDocs",frmMOUDocs);
			return this.getForward(strHospitalMouCertificatesList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strCertificate));
		}//end of catch(Exception exp)
	}//end of doSelect(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to go back to the Empanelment Hospital General screen on click of
	 * close button in emphospconfiguration list screen
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAssoClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		try{
			log.info("Inside the doClose method of ConfigurationAction");
			setLinks(request);
			return mapping.findForward(strHospGenInfo);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCertificate));
		}//end of catch(Exception exp)
	}//end of doAssoClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response) throws Exception
	
	
	/**
	 * This method builds all the files to populate in ArrayList 
	 * @param ArrayList alObj
	 * @return alObj ArrayList
	 */
	private ArrayList<Object> populateFilesToList(File[] fileArrayObj,ArrayList<Object> alObj) throws IOException
	{
		int iAlIndex = alObj.size();
		for(int j=0;j<fileArrayObj.length;j++,iAlIndex++){
			alObj.add(iAlIndex,(String)fileArrayObj[j].getPath().replaceAll("\\\\","\\\\\\\\"));
		}//end of for(intj=0;j<fileArrayObj.length;j++)
		return alObj;
	}//end of  populateFilesToList()
	
	/**
	 * Returns the HospitalManager session object for invoking methods on it.
	 * @return HospitalManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private HospitalManager getHospitalManagerObject() throws TTKException
	{
		HospitalManager hospitalManager = null;
		try
		{
			if(hospitalManager == null)
			{
				InitialContext ctx = new InitialContext();
				hospitalManager = (HospitalManager) ctx.lookup("java:global/TTKServices/business.ejb3/HospitalManagerBean!com.ttk.business.empanelment.HospitalManager");
			}//end if(hospitalManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "strDocument");
		}//end of catch
		return hospitalManager;
	}//end getHospitalManagerObject()
	
	/**
	 * This method  prepares the Caption based on the flow and retunrs it
	 * @param request current HttpRequest
	 * @return String caption built
	 * @throws TTKException
	 */
	private String buildCaption(HttpServletRequest request)throws TTKException
	{
		StringBuffer sbfCaption=new StringBuffer();
		sbfCaption.append(request.getSession().getAttribute("ConfigurationTitle"));
		return sbfCaption.toString();
	}//end of buildCaption(HttpServletRequest request)
	
	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param request The HTTP request we are processing
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(HttpServletRequest request) throws TTKException
	{
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.getWebBoardId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(request)
	
	/**
	 * Returns a string which contains the | separated sequence id's to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the | separated sequence id's to be delete
	 * @throws TTKException If any run time Excepton occures
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableDataMouCertificates)throws TTKException
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append("|").append(String.valueOf(((TdsCertificateVO)tableDataMouCertificates.getData().
								get(Integer.parseInt(strChk[i]))).getCertSeqId()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((TdsCertificateVO)tableDataMouCertificates.getData().
								get(Integer.parseInt(strChk[i]))).getCertSeqId().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId=sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return String.valueOf(sbfDeleteId);
		
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)
	
	private String populateDeleteId2(HttpServletRequest request, TableData tableDataMouCertificates)throws TTKException
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append("|").append(String.valueOf(((MOUDocumentVO)tableDataMouCertificates.getData().get(Integer.parseInt(strChk[i]))).getMouDocSeqID()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((MOUDocumentVO)tableDataMouCertificates.getData().get(Integer.parseInt(strChk[i]))).getMouDocSeqID()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId=sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return String.valueOf(sbfDeleteId);
		
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)
	


public ActionForward  doDefaultUploadDocs(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws TTKException 
{
	try
	{   
		String lPreAuthIntSeqId	=	(String)request.getParameter("lPreAuthIntSeqId");
		String preAuthNoYN		=	(String)request.getParameter("preAuthNoYN");
		log.debug("Inside the doDefaultUploadDocs method of UploadMOUDocsAction");
		request.getSession().setAttribute("lPreAuthIntSeqId", lPreAuthIntSeqId);
		request.setAttribute("preAuthNoYN", preAuthNoYN);
		setOnlineLinks(request);
		String strTable = "";
		StringBuffer sbfCaption= new StringBuffer();	
		String strEmpanelNumber=null;
		DynaActionForm frmMOUDocs= null;
		if( "Y".equals( (String)request.getParameter("Entry") ) ) {
			frmMOUDocs = (DynaActionForm)form;
			frmMOUDocs.initialize(mapping);
		}
		else {
			frmMOUDocs = (DynaActionForm) request.getSession().getAttribute("frmAddHospital");
			request.getSession().setAttribute("frmMOUDocs", frmMOUDocs);
		}//end of else			
		
		ArrayList<Object> alMouCertificatesList = new ArrayList<Object>();
		HospitalManager hospitalObject=null;
		TableData tableDataMouCertificates =null;
		
		
		if(tableDataMouCertificates==null){
			tableDataMouCertificates = new TableData();
		}//end of if(tableData==null) 	
		strTable = "MouUploadFilesTable";
		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
		if(strSortID.equals(""))
		{
			tableDataMouCertificates.createTableInfo(strTable,null);
			tableDataMouCertificates.setSearchData(this.populateSearchCriteria(request));
			tableDataMouCertificates.modifySearchData("search");
		}//end of if(strSortID.equals(""))
		else
		{
			tableDataMouCertificates.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
			tableDataMouCertificates.modifySearchData("sort");//modify the search data	
		}// end of else	
		hospitalObject=this.getHospitalManagerObject();
		if(lPreAuthIntSeqId==null || lPreAuthIntSeqId.equals("null"))
		{
			
		}else{
				alMouCertificatesList = hospitalObject.getPreAuthUploadsList(lPreAuthIntSeqId);
		}
		tableDataMouCertificates.setData(alMouCertificatesList,"search");
		request.getSession().setAttribute("tableDataMouCertificates",tableDataMouCertificates);
		request.getSession().setAttribute("frmMOUDocs",frmMOUDocs);
		return this.getForward(strPreAuthDocsList, mapping, request);
	} // end of try
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp,strCertificate));
	}//end of catch(Exception exp)
} // end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response) throws TTKException 


public ActionForward doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	try{
		log.debug("Inside the doDeleteList method of UploadMOUDocsAction");
		setLinks(request);
		HospitalManager hospitalObject=this.getHospitalManagerObject();
		DynaActionForm frmMOUDocs=(DynaActionForm)form;
		StringBuffer sbfDeleteId = new StringBuffer();
		ArrayList<Object> alMouCertificatesList = new ArrayList<Object>();
		ArrayList alBatchList = null;
		TableData tableDataMouCertificates = null;
		//get the Table Data From the session 
		if(request.getSession().getAttribute("tableDataMouCertificates")!=null)
		{
			tableDataMouCertificates=(TableData)(request.getSession()).getAttribute("tableDataMouCertificates");
		}
		else
		{
			tableDataMouCertificates=new TableData();
		}
		
		sbfDeleteId.append(populateDeleteId2(request, (TableData)request.getSession().getAttribute("tableDataMouCertificates")));
		int iCount=0;
		ArrayList<Object> alDeleteBatch = new ArrayList<Object>();
		alDeleteBatch.add(String.valueOf(sbfDeleteId));
		alDeleteBatch.add("P");
		frmMOUDocs.initialize(mapping); 	
		
		iCount=hospitalObject.deleteDocs(alDeleteBatch);
		String lPreAuthIntSeqId = (String)request.getSession().getAttribute("lPreAuthIntSeqId");
		if(lPreAuthIntSeqId==null || lPreAuthIntSeqId.equals("null"))
		{
			
		}
		else
		{
				alMouCertificatesList = hospitalObject.getPreAuthUploadsList(lPreAuthIntSeqId);
		}
	
		tableDataMouCertificates.setData(alMouCertificatesList,"Delete");
		request.getSession().setAttribute("tableDataMouCertificates",tableDataMouCertificates);
		request.getSession().setAttribute("frmMOUDocs",frmMOUDocs);
		return this.getForward(strPreAuthDocsList, mapping, request);
		
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processExceptions(request, mapping, expTTK);
	}
	catch(Exception exp)
	{
		return this.processExceptions(request, mapping, new TTKException(exp,strCertificate));
	}
}
	
}//end of AssociatedCertficateAction






