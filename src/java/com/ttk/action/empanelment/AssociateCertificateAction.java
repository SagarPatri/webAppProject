/**
 * @ (#) AssociateCertificateAction.java May 06, 2010
 * Project      : TTK HealthCare Services
 * File         :AssociateCertificateAction.java
 * Author       : Swaroop Kaushik D.S
 * Company      : Span Systems Corporation
 * Date Created : May 06, 2010
 *
 * @author       : Swaroop Kaushik D.S
 * Modified by   : Manikanta Kumar G.G.
 * Modified date : May 06, 2010
 * Reason        : To Add Methods (doBrowse,doSelect,populatesFilesToList)
 */

package com.ttk.action.empanelment;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

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
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.MOUDocumentVO;
import com.ttk.dto.empanelment.TdsCertificateVO;

import formdef.plugin.util.FormUtils;


public class AssociateCertificateAction extends TTKAction{

	private static final Logger log = Logger.getLogger( AssociateCertificateAction.class );
	
	//Action mapping forwards.
	private static final String strHospitalAssociatedCertificatesList="hospitalassociatedcertificatelist";
	private static final String strMoudelete="moutarrif";
	private static final String strCertificateList="certificatelist";
	private static final String strCertificatesClose="certificateclose";
	private static final String strReportdisplay="reportdisplay";
	private static final String strHospGenInfo="hospgeninfo";
		
	//Exception Message Identifier
	private static final String strCertificate="certificate";
	
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
			log.debug("Inside the doDefault method of AssociateCertificatesAction");
			setLinks(request);
			String strTable = "";
			DynaActionForm frmAssociateCertificates= null;
			if( "Y".equals( (String)request.getParameter("Entry") ) ) {
				frmAssociateCertificates = (DynaActionForm)form;
				frmAssociateCertificates.initialize(mapping);
			}//end of if(request.getParameter("Entry").equals("Y"))
			else {
				frmAssociateCertificates = (DynaActionForm) request.getSession().getAttribute("frmAssociateCertificates");
				if(frmAssociateCertificates==null){
					frmAssociateCertificates = (DynaActionForm)form;
					frmAssociateCertificates.initialize(mapping);
				}//end of if(frmAssociateDocuments==null)
			}//end of else			
			StringBuffer sbfCaption=new StringBuffer();
			ArrayList<Object> alAssociatedCertificatesList = new ArrayList<Object>();
			HospitalManager hospitalObject=null;
			TableData tableDataAssociateCertificates =TTKCommon.getTableData(request);
			String strForwardPath=null;
			strForwardPath= strHospitalAssociatedCertificatesList;
			sbfCaption = sbfCaption.append(this.buildCaption(request));			
			frmAssociateCertificates.set("caption",sbfCaption.toString());
			//get the table data from session if exists			
			if(tableDataAssociateCertificates==null){
				//create new table data object
				tableDataAssociateCertificates = new TableData();
			}//end of if(tableData==null) 	
			//create the required grid table
			strTable = "AssociatedCertificatesTable";
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(strSortID.equals(""))
			{
				tableDataAssociateCertificates.createTableInfo(strTable,null);
				tableDataAssociateCertificates.setSearchData(this.populateSearchCriteria(request));
				tableDataAssociateCertificates.modifySearchData("search");
			}//end of if(strSortID.equals(""))
			else
			{
				tableDataAssociateCertificates.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableDataAssociateCertificates.modifySearchData("sort");//modify the search data	
			}// end of else	
			hospitalObject=this.getHospitalManagerObject();
			alAssociatedCertificatesList = hospitalObject.getAssocCertificateList(tableDataAssociateCertificates.getSearchData());
			tableDataAssociateCertificates.setData(alAssociatedCertificatesList,"search");
			request.getSession().setAttribute("tableDataAssociateCertificates",tableDataAssociateCertificates);
			request.getSession().setAttribute("frmAssociateCertificates",frmAssociateCertificates);
			return this.getForward(strForwardPath, mapping, request);
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
			HttpServletResponse response) throws TTKException
	{
		try{
			setLinks(request);
			log.debug("Inside AssociateCertificatesAction doSave");
			TdsCertificateVO tdsCertificateVO=null;
			DynaActionForm frmAssociateCertificates=(DynaActionForm)form;
			ArrayList<Object> alAssociatedCertificatesList=null;
			StringBuffer sbfCaption= new StringBuffer();
			String strForwardPath=null;
			String strEmpanelNumber=null;
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			sbfCaption = sbfCaption.append(this.buildCaption(request));
			strForwardPath=strHospitalAssociatedCertificatesList;
			TableData tableDataAssociateCertificates = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataAssociateCertificates")!=null) 
			{
				tableDataAssociateCertificates=(TableData)(request.getSession()).getAttribute("tableDataAssociateCertificates");
			}//end of if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			else
			{
				tableDataAssociateCertificates=new TableData();
				tableDataAssociateCertificates.createTableInfo("AssociatedCertificatesTable",null);
				tableDataAssociateCertificates.setSearchData(this.populateSearchCriteria(request));
				tableDataAssociateCertificates.modifySearchData("search");
			}//end of else
			 tdsCertificateVO=(TdsCertificateVO)FormUtils.getFormValues(frmAssociateCertificates,this, mapping, request);
			 tdsCertificateVO.setHospSeqId(TTKCommon.getWebBoardId(request));
			 tdsCertificateVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			 strEmpanelNumber=(String)request.getSession().getAttribute("EmpanelNumber");
			 tdsCertificateVO.setEmpanelNumber(strEmpanelNumber);
			 int inUpdate=hospitalObject.saveAssocCertificateInfo(tdsCertificateVO);
			if(inUpdate>0)
			{
				if(!frmAssociateCertificates.getString("certSeqId").equals(""))
				{
					//set the appropriate message
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if(frmLinkDetails.get("clauseDocSeqID")!=null)
				else
				{
					//set the appropriate message
					request.setAttribute("updated","message.addedSuccessfully");
				}//end else
			   
				frmAssociateCertificates.initialize(mapping);
				alAssociatedCertificatesList=hospitalObject.getAssocCertificateList(tableDataAssociateCertificates.getSearchData());
				tableDataAssociateCertificates.setData(alAssociatedCertificatesList);
				frmAssociateCertificates.set("caption",sbfCaption.toString());
				request.getSession().setAttribute("tableDataAssociateCertificates",tableDataAssociateCertificates);
				request.getSession().setAttribute("frmAssociateCertificates",frmAssociateCertificates);
			}// end of if(iUpdate>0)
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(TTKException expTTK)
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
			log.debug("Inside the doClose method of AssociateCertificatesAction");
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
			strForwardPath=strHospitalAssociatedCertificatesList;
			DynaActionForm frmAssociateCertificates=(DynaActionForm)form;
			sbfCaption = sbfCaption.append(this.buildCaption(request));			
			//if rownumber is found populate the form object
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{				
				tdsCertificateVO=(TdsCertificateVO)((TableData)request.getSession().getAttribute("tableDataAssociateCertificates")).
				getData().get(Integer.parseInt(request.getParameter("rownum")));
				frmAssociateCertificates = (DynaActionForm)FormUtils.setFormValues("frmAssociateCertificates",tdsCertificateVO,
						this,mapping,request);
				log.info(frmAssociateCertificates.getString("rownum"));
			}// end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			else
			{
				frmAssociateCertificates.initialize(mapping);
			}// end of else
			frmAssociateCertificates.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmAssociateCertificates",frmAssociateCertificates);
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
		if(request.getSession().getAttribute("tableDataMouCertificates")!=null) 
		{
			try{
				setLinks(request);
				log.debug("Inside AssociateCertificatessAction doDelete");
				StringBuffer sbfDeleteId = new StringBuffer();
				ArrayList <Object>alCertificateDelete=new ArrayList<Object>();
				ArrayList <Object>alAssociatedCertificatesList=null;
				DynaActionForm frmMOUDocs=(DynaActionForm)form;
				StringBuffer sbfCaption= new StringBuffer();
				String strForwardPath=strMoudelete;
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
				//System.out.println("TTKCommon.getUserSeqId(request)..."+TTKCommon.getUserSeqId(request));
				alCertificateDelete.add(TTKCommon.getUserSeqId(request));
				frmMOUDocs.initialize(mapping);
				int iCount=hospitalObject.deleteAssocCertificatesInfo(alCertificateDelete,"tableDataMouCertificates");
				log.info("iCount :"+iCount);
				//refresh the data in order to get the new records if any.
				
				hospitalObject=this.getHospitalManagerObject();
				DynaActionForm frmMOUDoc=null;
				String hosp_seq_id=(String) request.getSession().getAttribute("hosp_seq_id_mou_uploads");
				 frmMOUDoc=(DynaActionForm) request.getSession().getAttribute("frmMOUDocs");
				 
				alAssociatedCertificatesList = hospitalObject.getMouUploadsList(hosp_seq_id);
				tableDataMouCertificates.setData(alAssociatedCertificatesList,"search");
				request.getSession().setAttribute("tableDataMouCertificates",tableDataMouCertificates);
				request.setAttribute("frmMOUDocs",frmMOUDocs);
				
				return this.getForward(strForwardPath,mapping,request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request,mapping,expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request,mapping,new TTKException(exp,strCertificate));
			}//end of cat
			
			
			
			
			
		}
		else
		{
		try{
			setLinks(request);
			log.debug("Inside AssociateCertificatessAction doDelete");
			StringBuffer sbfDeleteId = new StringBuffer();
			ArrayList <Object>alCertificateDelete=new ArrayList<Object>();
			ArrayList <Object>alAssociatedCertificatesList=null;
			DynaActionForm frmAssociateCertificates=(DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			String strForwardPath=strHospitalAssociatedCertificatesList;
			sbfCaption = sbfCaption.append(this.buildCaption(request));
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			TableData tableDataAssociateCertificates = null;
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataAssociateCertificates")!=null)
			{
				tableDataAssociateCertificates=(TableData)(request.getSession()).getAttribute("tableDataAssociateCertificates");
			}//end of if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
			else
			{
				tableDataAssociateCertificates=new TableData();
			}//end of else
			sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().getAttribute("tableDataAssociateCertificates")));
			alCertificateDelete.add(String.valueOf(sbfDeleteId));
			frmAssociateCertificates.initialize(mapping);
			int iCount=hospitalObject.deleteAssocCertificatesInfo(alCertificateDelete,"tableDataAssociateCertificates");
			log.info("iCount :"+iCount);
			//refresh the data in order to get the new records if any.
			//alAssociatedCertificatesList=hospitalObject.getAssocCertificateList(tableDataAssociateCertificates.getSearchData());
			tableDataAssociateCertificates.setData(alAssociatedCertificatesList);
			frmAssociateCertificates.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("tableDataAssociateCertificates",tableDataAssociateCertificates);
			request.getSession().setAttribute("frmAssociateCertificates",frmAssociateCertificates);
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
		}//end of else
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
			log.debug("Inside the doReset method of AssociateCertificatesAction");
			//log.info("Inside the doReset method of AssociateCertificatesAction");
			setLinks(request);
			TdsCertificateVO tdsCertificateVO=null;
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			DynaActionForm frmAssociateCertificates=(DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			String strForwardPath=null;		
			strForwardPath=strHospitalAssociatedCertificatesList;
			sbfCaption = sbfCaption.append(this.buildCaption(request));
			if(!TTKCommon.checkNull(frmAssociateCertificates.get("certSeqId")).equals("") )
			{
				tdsCertificateVO=hospitalObject.getCertiDetailInfo(TTKCommon.getLong(frmAssociateCertificates.getString("certSeqId")));
				frmAssociateCertificates=(DynaActionForm)FormUtils.setFormValues("frmAssociateCertificates",tdsCertificateVO,
						this,mapping,request);
			}//end of if(frmAssociateCertificates.get("certSeqId")!= null )
			else 
			{
				//log.info("inside or operator");
				frmAssociateCertificates.initialize(mapping);
			}//end of else
			frmAssociateCertificates.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmAssociateCertificates",frmAssociateCertificates);
			
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
			log.debug("Inside the doViewCertificate method of AssociateCertificatesAction");
			setLinks(request);
			TdsCertificateVO tdsCertificateVO=null;
			//DynaActionForm frmAssociateCertificates=(DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			String strCertPath=null;
			sbfCaption = sbfCaption.append(this.buildCaption(request));
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{				
				tdsCertificateVO=(TdsCertificateVO)((TableData)request.getSession().getAttribute("tableDataAssociateCertificates")).
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
			final DynaActionForm frmAssociateCertificates = (DynaActionForm) form;
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
			frmAssociateCertificates.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmAssociateCertificates",frmAssociateCertificates);			
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
			TableData tableDataAssociateCertificates =TTKCommon.getTableData(request);
			DynaActionForm frmAssociateCertificates= null;
			frmAssociateCertificates = (DynaActionForm) request.getSession().getAttribute("frmAssociateCertificates");
			ArrayList<Object> alAssociatedCertificatesList=null;
			HospitalManager hospitalManager=this.getHospitalManagerObject();
			if(frmAssociateCertificates!=null){
				frmAssociateCertificates.set("certPath",((DynaActionForm)form).getString("certPath"));
			}//end of if(request.getSession().getAttribute("frmLinkDetails")!=null)
			else {
				frmAssociateCertificates = (DynaActionForm)form;
			}//end of else	
			if(((DynaActionForm)form).getString("certPath")!=null){
				frmAssociateCertificates.set("certPath",((DynaActionForm)form).getString("certPath"));
			}//end of if(((DynaActionForm)form).getString("certPath")!=null)
			else {
				frmAssociateCertificates.set("certPath","");
			}//end of else
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption = sbfCaption.append(this.buildCaption(request));
			//get the Table Data From the session 
			if(request.getSession().getAttribute("tableDataAssociateCertificates")!=null)
			{
				tableDataAssociateCertificates=(TableData)(request.getSession()).getAttribute("tableDataAssociateCertificates");
			}//end of if(request.getSession().getAttribute("tableDataAssociatedDocuments")!=null)
			else
			{
				tableDataAssociateCertificates=new TableData();
			}//end of else
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strSortID.equals(""))
			{
				tableDataAssociateCertificates.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableDataAssociateCertificates.modifySearchData("sort");//modify the search data
			}// end of if(!strSortID.equals(""))
			else
			{
				tableDataAssociateCertificates.createTableInfo("AssociatedCertificatesTable",null);
				tableDataAssociateCertificates.setSearchData(this.populateSearchCriteria(request));
				tableDataAssociateCertificates.modifySearchData("search");
			}// end of else
			alAssociatedCertificatesList=hospitalManager.getAssocCertificateList(tableDataAssociateCertificates.getSearchData());
			tableDataAssociateCertificates.setData(alAssociatedCertificatesList);
			request.getSession().setAttribute("tableDataAssociateCertificates",tableDataAssociateCertificates);
			frmAssociateCertificates.set("caption",sbfCaption.toString());
			//log.info("Caption:" +frmAssociateCertificates);
			request.getSession().setAttribute("frmAssociateCertificates",frmAssociateCertificates);
			return this.getForward(strHospitalAssociatedCertificatesList, mapping, request);
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
	private String populateDeleteId(HttpServletRequest request, TableData tableDataAssociateCertificates)throws TTKException
	{
		if(request.getSession().getAttribute("tableDataMouCertificates")!=null) 
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
							sbfDeleteId.append("|").append(String.valueOf(((MOUDocumentVO)tableDataAssociateCertificates.getData().get(Integer.parseInt(strChk[i]))).getMouDocSeqID()));
						}//end of if(i == 0)
						else
						{
							sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((MOUDocumentVO)tableDataAssociateCertificates.getData().get(Integer.parseInt(strChk[i]))).getMouDocSeqID().intValue()));
						}//end of else
					}//end of if(strChk[i]!=null)
				}//end of for(int i=0; i<strChk.length;i++)
				sbfDeleteId=sbfDeleteId.append("|");
			}//end of if(strChk!=null&&strChk.length!=0)
			return String.valueOf(sbfDeleteId);
		}//end of populateDeleteId(HttpServletRequest request, TableData tableData)
				
			
		
		
	else{
		
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
						sbfDeleteId.append("|").append(String.valueOf(((TdsCertificateVO)tableDataAssociateCertificates.getData().
								get(Integer.parseInt(strChk[i]))).getCertSeqId()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((TdsCertificateVO)tableDataAssociateCertificates.getData().
								get(Integer.parseInt(strChk[i]))).getCertSeqId().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
			sbfDeleteId=sbfDeleteId.append("|");
		}//end of if(strChk!=null&&strChk.length!=0)
		return String.valueOf(sbfDeleteId);
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)

}//else
}//end of AssociatedCertficateAction
