/**
 * @ (#) HospitalConfigurationAction.java Sep 16, 2010
 * Project       : TTK HealthCare Services
 * File          : HospitalConfigurationAction.java
 * Author        : Manikanta Kumar G G
 * Company       : Span Systems Corporation
 * Date Created  : Sep 16, 2010
 *
 * @author       : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.administration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
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

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.ConfigurationManager;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.business.empanelment.LogManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.WebConfigLinkVO;
import com.ttk.dto.administration.WebconfigInsCompInfoVO;
import com.ttk.dto.enrollment.PolicyDetailVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import org.apache.struts.upload.FormFile;

import formdef.plugin.util.FormUtils;

public class HospitalConfigurationAction extends TTKAction 
{
	private static final Logger log = Logger.getLogger( HospitalConfigurationAction.class );
	
	private static final String strConfiguration="shownotify";			
	private static final String strClose="close";
	private static final String strNotify="showinformation";
	private static final String strUploads="showuploads";
	private static final String strLinkDetailsList="linklist";
	private static final String strBrowseList="browselist";
	private static final String strDeleteLink="linkdelete";
	
	/**
	 * This method is used to View the Configuration List Screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws TTKException if any error occurs
	 */
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
			{
				log.info("Inside the doDefault method of HospitalConfigurationAction");
				try
				{
				setLinks(request);			
				DynaActionForm frmhospConfiguration = (DynaActionForm)form;
				frmhospConfiguration.initialize(mapping);
				return this.getForward(strConfiguration, mapping, request);
				}//end of try
				catch(TTKException expTTK)
				{
				return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
				return this.processExceptions(request, mapping, new TTKException(exp,strConfiguration));
				}//end of catch(Exception exp)
			}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	
	/**
	 * This Method Navigates you to screen where user can enter Hospital Information and Save.
	 * */
	public ActionForward doShowNotify(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		log.info("Inside the doDefault method of doShowNotify");
		try
		{
		setLinks(request);			
		DynaActionForm frmhospConfiguration=(DynaActionForm)form;
		//frmhospConfiguration.initialize(mapping);
		
		ConfigurationManager servConfigurationManager=this.getConfManager();
		WebconfigInsCompInfoVO webconfigInsCompInfoVO = new WebconfigInsCompInfoVO();
		//webconfigInsCompInfoVO =(WebconfigInsCompInfoVO)FormUtils.getFormValues(frmhospConfiguration, "frmhospConfiguration",this, mapping, request);
		ArrayList alLinkDetailsList=null;
		webconfigInsCompInfoVO=(WebconfigInsCompInfoVO)servConfigurationManager.getHospInfo();
		String hospInfo	=	"";
		if(webconfigInsCompInfoVO!=null){
		hospInfo		=	webconfigInsCompInfoVO.getHospInfo();
		frmhospConfiguration.set("hospInfo",hospInfo);
		}
		/*frmhospConfiguration= (DynaActionForm)FormUtils.setFormValues("frmhospConfiguration",
				webconfigInsCompInfoVO,this,mapping,request);
		request.getSession().setAttribute("frmhospConfiguration",frmhospConfiguration);*/
		
		
		return this.getForward(strNotify, mapping, request);
		//return mapping.findForward(strNotify);
		}//end of try
		catch(TTKException expTTK)
		{
		return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processExceptions(request, mapping, new TTKException(exp,strNotify));
		}//end of catch(Exception exp)
	}//end of doShowNotify(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	/**
	 * This Method Saves the Hospital Information */
	public ActionForward doSaveNotify(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		try{
			log.debug("Inside the doSave method of HospitalAction");
			setLinks(request);	
			DynaActionForm frmhospConfiguration = (DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append(" - [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			ConfigurationManager servConfigurationManager=this.getConfManager();
			WebconfigInsCompInfoVO webconfigInsCompInfoVO = new WebconfigInsCompInfoVO();
			webconfigInsCompInfoVO =(WebconfigInsCompInfoVO)FormUtils.getFormValues(frmhospConfiguration, "frmhospConfiguration",this, mapping, request);
			webconfigInsCompInfoVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			int iResult = servConfigurationManager.saveWebConfigInsInfo(webconfigInsCompInfoVO);	
			if(iResult>0)
			{
					request.setAttribute("updated","message.saved");
			}//end of if(iResult>0)	
			//webconfigInsCompInfoVO=servConfigurationManager.getWebConfigHospInfo(TTKCommon.getWebBoardId(request));
			//--above line may require when Hosp Information is required to store empanel wise.
			frmhospConfiguration = (DynaActionForm)FormUtils.setFormValues("frmhospConfiguration",
					webconfigInsCompInfoVO, this, mapping, request);
			frmhospConfiguration.set("caption",sbfCaption.toString());
			request.setAttribute("frmhospConfiguration",frmhospConfiguration);
			return this.getForward(strNotify, mapping, request);
			//return mapping.findForward(strNotify);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strNotify));
		}//end of catch(Exception exp)
	}//end of doSaveNotify(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doCloseHosp(ActionMapping mapping,ActionForm form,HttpServletRequest request,
					   HttpServletResponse response) throws TTKException{
		try{
		log.debug("Inside the doClose method of TaxConfigurationAction");
		setLinks(request);
		return this.getForward(strClose, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
		return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processExceptions(request, mapping, new TTKException(exp,strNotify));
		}//end of catch(Exception exp)
		}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	

	/**
	 * This Method Navigates you to screen where user can Upload the files for Hospital Information and Save.
	 * */
	public ActionForward doShowUploads(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		log.info("Inside the doDefault method of doShowUploads");
		try
		{
		setLinks(request);			
		DynaActionForm frmhospConfiguration = (DynaActionForm)form;
		frmhospConfiguration.initialize(mapping);
		
		
		//---------------
		frmhospConfiguration = (DynaActionForm) request.getSession().getAttribute("frmhospConfiguration");
		if(frmhospConfiguration!=null){
			frmhospConfiguration.set("path",((DynaActionForm)form).getString("path"));
		}//end of if(request.getSession().getAttribute("frmhospConfiguration")!=null)
		else {
			frmhospConfiguration = (DynaActionForm)form;
		}//end of else	
		if(((DynaActionForm)form).getString("path")!=null){
			frmhospConfiguration.set("path",((DynaActionForm)form).getString("path"));
		}//end of if(((DynaActionForm)form).getString("path")!=null)
		else {
			frmhospConfiguration.set("path","");
		}//end of else
		TableData tableDataLinkDetails = null;
		//get the Table Data From the session 
		if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
		{
			tableDataLinkDetails=(TableData)(request.getSession()).getAttribute("tableDataLinkDetails");
		}//end of if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
		else
		{
			tableDataLinkDetails=new TableData();
		}//end of else
		StringBuffer sbfCaption= new StringBuffer();
		sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
		ArrayList alLinkDetailsList=null;
		//ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		{
			((DynaActionForm)form).initialize(mapping);//reset the form data
		}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
		if(!strSortID.equals(""))
		{
			tableDataLinkDetails.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
			tableDataLinkDetails.modifySearchData("sort");//modify the search data
		}// end of if(!strSortID.equals(""))
		else
		{
			tableDataLinkDetails.createTableInfo("HospLinkDetailsTable",null);
			tableDataLinkDetails.setSearchData(this.populateSearchCriteria(request));
			tableDataLinkDetails.modifySearchData("search");
		}// end of else
		ConfigurationManager servConfigurationManager=this.getConfManager();
		
		alLinkDetailsList=servConfigurationManager.getWebConfigLinkList(tableDataLinkDetails.getSearchData());
		tableDataLinkDetails.setData(alLinkDetailsList);
		frmhospConfiguration.set("showLinkYN","Y");
		frmhospConfiguration.set("caption",sbfCaption.toString());
		request.getSession().setAttribute("tableDataLinkDetails",tableDataLinkDetails);
		request.setAttribute("frmhospConfiguration",frmhospConfiguration);
		
		
		//------------
		return this.getForward(strUploads, mapping, request);
		//return mapping.findForward(strNotify);
		}//end of try
		catch(TTKException expTTK)
		{
		return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processExceptions(request, mapping, new TTKException(exp,strUploads));
		}//end of catch(Exception exp)
	}//end of doShowUploads(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
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
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside frmhospConfiguration doSelect");
			DynaActionForm frmhospConfiguration= null;
			frmhospConfiguration = (DynaActionForm) request.getSession().getAttribute("frmhospConfiguration");
			if(frmhospConfiguration!=null){
				frmhospConfiguration.set("path",((DynaActionForm)form).getString("path"));
			}//end of if(request.getSession().getAttribute("frmhospConfiguration")!=null)
			else {
				frmhospConfiguration = (DynaActionForm)form;
			}//end of else	
			if(((DynaActionForm)form).getString("path")!=null){
				frmhospConfiguration.set("path",((DynaActionForm)form).getString("path"));
			}//end of if(((DynaActionForm)form).getString("path")!=null)
			else {
				frmhospConfiguration.set("path","");
			}//end of else
			TableData tableDataLinkDetails = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			{
				tableDataLinkDetails=(TableData)(request.getSession()).getAttribute("tableDataLinkDetails");
			}//end of if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			else
			{
				tableDataLinkDetails=new TableData();
			}//end of else
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			ArrayList alLinkDetailsList=null;
			//ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strSortID.equals(""))
			{
				tableDataLinkDetails.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableDataLinkDetails.modifySearchData("sort");//modify the search data
			}// end of if(!strSortID.equals(""))
			else
			{
				tableDataLinkDetails.createTableInfo("HospLinkDetailsTable",null);
				tableDataLinkDetails.setSearchData(this.populateSearchCriteria(request));
				tableDataLinkDetails.modifySearchData("search");
			}// end of else

			
			ConfigurationManager servConfigurationManager=this.getConfManager();
			
			alLinkDetailsList=servConfigurationManager.getWebConfigLinkList(tableDataLinkDetails.getSearchData());
			tableDataLinkDetails.setData(alLinkDetailsList);
			frmhospConfiguration.set("showLinkYN","Y");
			frmhospConfiguration.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("tableDataLinkDetails",tableDataLinkDetails);
			request.setAttribute("frmhospConfiguration",frmhospConfiguration);
			//return this.getForward(strLinkDetailsList, mapping, request);
			return mapping.findForward(strLinkDetailsList);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strLinkDetailsList));
		}//end of catch(Exception exp)
	}//end of doSelect(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	/**
	 * This method is used to upload the files and save in DB*/
	public ActionForward doSaveUploads(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{

			//kocnewhosp1
			setLinks(request);
			int iUpdate=0;
			
			//WebconfigInsCompInfoVO webconfigInsCompInfoVO = new WebconfigInsCompInfoVO();
			
			log.info("Inside frmhospConfiguration doSaveUploads");
			
				DynaActionForm frmhospConfiguration=(DynaActionForm)form;
				WebconfigInsCompInfoVO webconfigInsCompInfoVO = new WebconfigInsCompInfoVO();
				webconfigInsCompInfoVO=(WebconfigInsCompInfoVO)FormUtils.getFormValues(frmhospConfiguration,this, mapping, request);
				webconfigInsCompInfoVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
				webconfigInsCompInfoVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
				webconfigInsCompInfoVO.setShowLinkYN("Y");
				ArrayList alLinkDetailsList=null;
				TableData tableDataLinkDetails = null;
				if(request.getSession().getAttribute("tableDataLinkDetails")!=null) 
				{
					tableDataLinkDetails=(TableData)(request.getSession()).getAttribute("tableDataLinkDetails");
				}//end of if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
				else
				{
					tableDataLinkDetails=new TableData();
				}//end of else
				webconfigInsCompInfoVO=(WebconfigInsCompInfoVO)FormUtils.getFormValues(frmhospConfiguration,this, mapping, request);
				webconfigInsCompInfoVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
				webconfigInsCompInfoVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
				ConfigurationManager servConfigurationManager=this.getConfManager();
				StringBuffer sbfCaption= new StringBuffer();
				sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
				
				//..............File Upload from Local System.................
				
				
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
				webconfigInsCompInfoVO =(WebconfigInsCompInfoVO)FormUtils.getFormValues(frmhospConfiguration, "frmhospConfiguration",this, mapping, request);
				//Get the FormFile object from ActionForm.
				StringBuffer strCaption=new StringBuffer();
				ArrayList<Object> alFileAUploadList = new ArrayList<Object>();
				alFileAUploadList.add(TTKCommon.getUserSeqId(request));//2
				formFile = (FormFile)frmhospConfiguration.get("file");
				//pattern = Pattern.compile( "[^a-zA-Z0-9\\.\\s*]" );
				pattern = Pattern.compile( "[^a-zA-Z0-9_\\.\\-\\s*]" );
				matcher = pattern.matcher( formFile.getFileName() );
				String 	strTimeStamp=((df.format(Calendar.getInstance(tz).getTime())).replaceAll(" ", "_")).replaceAll(":", ""); 
				fileName=strTimeStamp+"_"+formFile.getFileName();
				if(!matcher.find())
				{
					webconfigInsCompInfoVO.setFile(fileName);
					webconfigInsCompInfoVO.setUpdatedBy(new Long(1));
					//	alFileDetails=this.populateSearchCriteria(webconfigInsCompInfoVO);
					String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("hospattachdestination"));
					if(path.equals(""))	{
						path="D:\\\\home\\\\jboss\\\\jboss-as-7.1.1.Final\\\\bin\\\\hospitaldocs\\\\";
					}//
					File folder = new File(path);
					if(!folder.exists()){
						folder.mkdir();
					}
					String finalPath=(path+fileName);
					String strFileExt=formFile.getFileName().substring(formFile.getFileName().lastIndexOf(".")+1,formFile.getFileName().length());

					if(formFile.getFileSize()<=fileSize)
					{

						
						if((strFileExt.equalsIgnoreCase("pdf"))   || (strFileExt.equalsIgnoreCase("doc")) 
                                || (strFileExt.equalsIgnoreCase("docx")) || (strFileExt.equalsIgnoreCase("xls"))   
                                || (strFileExt.equalsIgnoreCase("xlsx")))
						{   
							
								outputStream = new FileOutputStream(new File(finalPath));
								outputStream.write(formFile.getFileData());

								//alFileAUploadList.add(fileName);//3 This just Adds file name
								alFileAUploadList.add(finalPath);//3 This code adds Total path of the file
								//alFileAUploadList.add(webconfigInsCompInfoVO.getRemarks());//4
								

								iUpdate=servConfigurationManager.saveWebConfigLinkInfo(webconfigInsCompInfoVO,alFileAUploadList);
								if(iUpdate>0)
								{
									if(frmhospConfiguration.get("configLinkSeqID")!=null)
									{
										//set the appropriate message
										request.setAttribute("updated","message.savedSuccessfully");
									}//end of if(frmhospConfiguration.get("configLinkSeqID")!=null)
									else
									{
										//set the appropriate message
										request.setAttribute("updated","message.addedSuccessfully");
									}//end else
									frmhospConfiguration.initialize(mapping);
									alLinkDetailsList=servConfigurationManager.getWebConfigLinkList(tableDataLinkDetails.getSearchData());
									tableDataLinkDetails.setData(alLinkDetailsList);
									frmhospConfiguration.set("showLinkYN","Y");
									frmhospConfiguration.set("caption",sbfCaption.toString());
									request.getSession().setAttribute("tableDataLinkDetails",tableDataLinkDetails);
									request.setAttribute("frmhospConfiguration",frmhospConfiguration);
								}// end of if(iUpdate>0)
								
								frmhospConfiguration = (DynaActionForm)FormUtils.setFormValues("frmhospConfiguration",webconfigInsCompInfoVO,this,mapping,request);
							
							
						}//end of if(strFileExt.equalsIgnoreCase("html") || strFileExt.equalsIgnoreCase("mhtml")||strFileExt.equalsIgnoreCase("msg") || strFileExt.equalsIgnoreCase("rar")|| strFileExt.equalsIgnoreCase("zip") || strFileExt.equalsIgnoreCase("pdf") || strFileExt.equalsIgnoreCase("doc") || strFileExt.equalsIgnoreCase("docx") || strFileExt.equalsIgnoreCase("txt") || strFileExt.equalsIgnoreCase("odt") || strFileExt.equalsIgnoreCase("jrxml") || strFileExt.equalsIgnoreCase("xls") || strFileExt.equalsIgnoreCase("xlsx") )
						else{

							strNotify="selected "+formFile.getFileName()+" file should be any of these extensions (.pdf,.doc,.docx,.xls,.xlsx)";
							frmhospConfiguration = (DynaActionForm)FormUtils.setFormValues("frmhospConfiguration",webconfigInsCompInfoVO,this,mapping,request);
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
				
				frmhospConfiguration.set("updated",updated);
				request.getSession().setAttribute("frmUpload", frmhospConfiguration);
				request.setAttribute("updated",updated);
				request.setAttribute("notify",strNotify);
				//frmhospConfiguration.set("caption",strCaption);
				//..............File Upload from Local System Ends............
				
			
			return mapping.findForward(strLinkDetailsList);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strUploads));
		}//end of catch(Exception exp)
	}//end of doSaveUploads(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param request The HTTP request we are processing
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.getWebBoardId(request));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(Long lngMemberSeqId)
	
	public ActionForward doBrowse(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{

		try{
			setLinks(request);
			log.debug("Inside frmhospConfiguration doBrowse");
			DynaActionForm frmhospConfiguration = (DynaActionForm) form; 
			PolicyDetailVO policyDetailVo = (PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
			//String strType = frmhospConfiguration.getString("linkTypeID");
			String strDefaultDir = TTKPropertiesReader.getPropertyValue("WebLoginDocs")+"/default";
			ArrayList<Object> alFilePath = new ArrayList<Object>();
			//in case of links groupid folder should be included in the path
			StringBuffer  strLinks = new StringBuffer();
			strLinks = strLinks.append(TTKPropertiesReader.getPropertyValue("WebLoginDocs"));
			
			//for filtering out image
			FilenameFilter imageFilterObj = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					//return (name.endsWith(".gif") || name.endsWith(".jpg"));
					return name.contains(".");
				}//end of accept(File dir, String name)
			};//end of FilenameFilter imageFilterOBj = new FilenameFilter()
			//for filtering out doc and txt files
			FilenameFilter docFilterObj = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					//return (name.endsWith(".doc") || name.endsWith(".txt") || name.endsWith(".pdf") || name.endsWith(".zip"));
					return name.contains(".");
				}//end of accept(File dir, String name)
			};//end of FilenameFilter docFilterObj = new FilenameFilter() 
			
			//if(strType.equalsIgnoreCase("WLL")){ 
				File dir = new File(strLinks.toString());
				File defaultDir = new File(strDefaultDir);
				if(!dir.exists()){
					log.info(strLinks + " doesn't exists");
					File[] defFileArrObj = defaultDir.listFiles(docFilterObj);
					alFilePath = this.populateFilesToList(defFileArrObj,alFilePath);
				}//end of if(!dir.exists())
				else{
					File[] defFileArrObj = defaultDir.listFiles(docFilterObj);
					alFilePath = this.populateFilesToList(defFileArrObj,alFilePath);
					File[] fileArrObj = dir.listFiles(docFilterObj);
					alFilePath = this.populateFilesToList(fileArrObj,alFilePath);
				}//end of else
			//}//end of else if(strType.equalsIgnoreCase("WLL"))
			request.getSession().setAttribute("frmhospConfiguration",frmhospConfiguration);			
			request.setAttribute("alFileList",alFilePath);
			//return this.getForward(strBrowseList, mapping, request);
			return mapping.findForward(strBrowseList);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strBrowseList));
		}//end of catch(Exception exp)
	}//end of doBrowse(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method builds all the files to populate in ArrayList 
	 * @param ArrayList alObj
	 * @return alObj ArrayList
	 */
	private ArrayList<Object> populateFilesToList(File[] fileArrayObj,ArrayList<Object> alObj) throws IOException{
		int iAlIndex = alObj.size();
		for(int j=0;j<fileArrayObj.length;j++,iAlIndex++){
			alObj.add(iAlIndex,(String)fileArrayObj[j].getPath().replaceAll("\\\\","\\\\\\\\"));
		}//end of for(intj=0;j<fileArrayObj.length;j++)
		return alObj;
	}//end of  populateFilesToList()
	
	/**
	 * This method is called from the struts framework.
	 * This method is used to delete a record.
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
			HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.info("Inside frmhospConfiguration doDelete");
			StringBuffer sbfDeleteId = new StringBuffer();
			ArrayList <Object>alLinkDelete=new ArrayList<Object>();
			DynaActionForm frmhospConfiguration=(DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			ArrayList alLinkDetailsList=null;
			ConfigurationManager servConfigurationManager=this.getConfManager();
			TableData tableDataLinkDetails = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataLinkDetails")!=null) 
			{
				tableDataLinkDetails=(TableData)(request.getSession()).getAttribute("tableDataLinkDetails");
			}//end of if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			else
			{
				tableDataLinkDetails=new TableData();
			}//end of else
			sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().getAttribute("tableDataLinkDetails")));
			alLinkDelete.add(String.valueOf(sbfDeleteId));
			alLinkDelete.add(TTKCommon.getUserSeqId(request));
			frmhospConfiguration.initialize(mapping);
			int iCount=servConfigurationManager.deleteWebConfigLinkInfo(alLinkDelete);
			log.debug("iCount value is :"+iCount);
			//refresh the data in order to get the new records if any.
			alLinkDetailsList=servConfigurationManager.getWebConfigLinkList(tableDataLinkDetails.getSearchData());
			tableDataLinkDetails.setData(alLinkDetailsList);
			frmhospConfiguration.set("showLinkYN","Y");
			frmhospConfiguration.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("tableDataLinkDetails",tableDataLinkDetails);
			request.setAttribute("frmhospConfiguration",frmhospConfiguration);
			return this.getForward(strDeleteLink,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strDeleteLink));
		}//end of catch(Exception exp)
	}//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns a string which contains the | separated sequence id's to be deleted
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the | separated sequence id's to be delete
	 * @throws TTKException If any run time Excepton occures
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableDataLinkDetails)throws TTKException
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
						sbfDeleteId.append("|").append(String.valueOf(((WebconfigInsCompInfoVO)tableDataLinkDetails.getData().
								get(Integer.parseInt(strChk[i]))).getConfigLinkSeqID()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((WebconfigInsCompInfoVO)tableDataLinkDetails.getData().
								get(Integer.parseInt(strChk[i]))).getConfigLinkSeqID().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId=sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return String.valueOf(sbfDeleteId);
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * Returns the ConfigurationManager session object for invoking methods on it.
	 * @return ConfigurationManager session object which can be used for method invocation 
	 * @exception throws TTKException  
	 */
	private ConfigurationManager getConfManager() throws TTKException
	{
		ConfigurationManager ConfManager = null;
		try 
		{
			if(ConfManager == null)
			{
				InitialContext ctx = new InitialContext();
				ConfManager = (ConfigurationManager) ctx.lookup("java:global/TTKServices/business.ejb3/ConfigurationManagerBean!com.ttk.business.administration.ConfigurationManager");
			}//end if
		}//end of try 
		catch(Exception exp) 
		{
			throw new TTKException(exp, strNotify);
		}//end of catch
		return ConfManager;
	}//end getTDSConfManager()
    
}//end of HospitalConfigurationAction
