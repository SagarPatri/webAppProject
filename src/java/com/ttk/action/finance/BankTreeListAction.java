/**
 * @ (#) BankTreeListAction.java June 26th, 2006
 * Project 		: TTK HealthCare Services
 * File 		: BankTreeListAction.java
 * Author 		: Raghavendra T M
 * Company 		: Span Systems Corporation
 * Date Created : June 26, 2006
 *
 * @author 		: Raghavendra T M
 * Modified by 	:
 * Modified date:
 * Reason 		:
 */

package com.ttk.action.finance;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

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
import com.ttk.action.empanelment.AsynchronusAction;
import com.ttk.action.tree.TreeData;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.business.finance.BankManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.finance.BankAddressVO;
import com.ttk.dto.finance.BankDetailVO;
import com.ttk.dto.finance.BankVO;

import formdef.plugin.util.FormUtils;


/**
 * This class is used display the Bank List in Tree structure.
 * This class also provides option for Adding and Updating the Bank.
 */
public class BankTreeListAction extends TTKAction
{
	//	Modes
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	//  Action mapping forwards
	private static final String strBanklist ="banklist";
	private static final String strBankDetails ="bankdetails";
	private static final String strBankContacts="bankcontactlist";
	//  Exception Message Identifier
	private static final String strBankDetail="bankdetail";
	private static Logger log = Logger.getLogger( BankTreeListAction.class );

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
			setLinks(request);
			log.debug("Inside BankTreeListAction doDefault");
			TreeData treeData = TTKCommon.getTreeData(request);
			//clear the dynaform if visiting from left links for the first time
			((DynaActionForm)form).initialize(mapping);//reset the form data
			treeData = new TreeData();
			//create the required tree
			treeData.createTreeInfo("BankTree",null,true);
			//set the tree ACL permission
			checkTreePermission(request,treeData);
			//set the tree data object to session
			request.getSession().setAttribute("treeData",treeData);
			return this.getForward(strBanklist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankDetail));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
								  HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside BankTreeListAction doDefault");
			TreeData treeData = TTKCommon.getTreeData(request);
			BankManager bankManagerObject=this.getBankManagerObject();
			//if the page number is clicked, display the appropriate page
			if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
			{
				treeData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				treeData.setSelectedRoot(-1);
				return this.getForward(strBanklist, mapping, request);
			}//end of if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))

			//create the required tree
			treeData.createTreeInfo("BankTree",null,true);
			treeData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
			treeData.modifySearchData("search");

			//set the tree ACL permission
			checkTreePermission(request,treeData);
			//searchparam has been changed to treeSearchpram
			ArrayList alBanks = bankManagerObject.getBankList(treeData.getSearchData());
			treeData.setData(alBanks, "search");
			request.getSession().setAttribute("treeData",treeData);
			return this.getForward(strBanklist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankDetail));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to get the next set of records with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
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
			log.debug("Inside BankTreeListAction doForward");
			TreeData treeData = TTKCommon.getTreeData(request);
			BankManager bankManagerObject=this.getBankManagerObject();
			treeData.modifySearchData(strForward);//modify the search data
			ArrayList alUserGroups = bankManagerObject.getBankList(treeData.getSearchData());
			treeData.setData(alUserGroups, strForward);//set the table data
			request.getSession().setAttribute("treeData",treeData);//set the table data object to session
			return this.getForward(strBanklist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankDetail));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to get the previous set of records with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
									HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside BankTreeListAction doBackward");
			TreeData treeData = TTKCommon.getTreeData(request);
			BankManager bankManagerObject=this.getBankManagerObject();
			treeData.modifySearchData(strBackward);//modify the search data
			ArrayList alUserGroups = bankManagerObject.getBankList(treeData.getSearchData());
			treeData.setData(alUserGroups, strBackward);//set the table data
			request.getSession().setAttribute("treeData",treeData);//set the table data object to session
			return this.getForward(strBanklist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankDetail));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to show/hide the child record details
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doShowHide(ActionMapping mapping,ActionForm form,HttpServletRequest request,
									HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside BankTreeListAction doShowHide");
			DynaActionForm generalForm = (DynaActionForm)form;
			BankManager bankManagerObject=this.getBankManagerObject();
			TreeData treeData = TTKCommon.getTreeData(request);
			BankVO bankVO = null;
			int iSelectedRoot = Integer.parseInt((String)generalForm.get("selectedroot"));
			//create the required tree
			ArrayList alNodeGroups = new ArrayList();
			bankVO = ((BankVO)treeData.getRootData().get(iSelectedRoot));
			alNodeGroups=bankManagerObject.getBankBranchList(bankVO.getBankSeqID(),TTKCommon.getUserSeqId(request));
			// make the selected root
			treeData.setSelectedRoot(iSelectedRoot);
			((BankVO)treeData.getRootData().get(iSelectedRoot)).setBranch(alNodeGroups);
			request.getSession().setAttribute("treeData",treeData);
			return this.getForward(strBanklist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankDetail));
		}//end of catch(Exception exp)
	}//end of doShowHide(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is called from the struts framework.
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
							   HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside BankTreeListAction doAdd");
			DynaActionForm formGeneral = (DynaActionForm)form;
            ArrayList alCityList = new ArrayList();
            HospitalManager hospitalObject=this.getHospitalManagerObject();
            ArrayList alStateList = null;
            HashMap hmCityList = null;
            BankDetailVO bankDetailVO = null;
            BankVO bankVO = null;
            BankAddressVO bankAddressVO = null;
            TreeData treeData = TTKCommon.getTreeData(request);
            String strOffType="Head Office";
            //To get the selected root node and its information from list of Group screen
            int iSelectedRoot =-1;
            if(!TTKCommon.checkNull((String)formGeneral.get("selectedroot")).equals(""))
            {
                iSelectedRoot=Integer.parseInt((String)formGeneral.get("selectedroot"));
            }//end of if(!TTKCommon.checkNull((String)formGeneral.get("selectedroot")).equals(""))
            else
            {
                treeData.setSelectedRoot(iSelectedRoot);
            }//end of else
            bankDetailVO= new BankDetailVO();
            bankVO= new BankVO();
            bankAddressVO=new BankAddressVO();
            bankDetailVO.setBankAddressVO(bankAddressVO);
            if(iSelectedRoot>=0)//setting the banseqid as a head office for branch office
            {
                String strSelectedRoot = TTKCommon.checkNull(request.getParameter("selectedroot"));
                String strSelectedNode = TTKCommon.checkNull(request.getParameter("selectednode"));
                //To get the information of child node
                bankVO=(BankVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
                bankDetailVO.setHeadOfficeTypeID(bankVO.getBankSeqID());
                /*bankAddressVO.setCountryCode("175");//TO SHOW UAE COUNTRY BY DEFAULT intx
                bankAddressVO.setStateCode("DXB");//TO SHOW UAE COUNTRY BY DEFAULT intx
                bankDetailVO.setBankAddressVO(bankAddressVO);*/
                //To set the required information to the Add Bank screen based on selected node
                strOffType = "Branch Office";
            }//end of if(iSelectedRoot>=0)
            bankAddressVO.setCountryCode("123");//TO SHOW UAE COUNTRY BY DEFAULT intx
            bankAddressVO.setStateCode("DXB");//TO SHOW UAE COUNTRY BY DEFAULT intx
            bankAddressVO.setIsdCode(968);//SETTING ISD CODE OF UAE BY DEFAULT
            bankAddressVO.setStdCode(4);//SETTING STD CODE OF DUBAI BY DEFAULT
            bankDetailVO.setBankAddressVO(bankAddressVO);
            DynaActionForm frmBankDetails = (DynaActionForm)FormUtils.setFormValues("frmBankDetails",bankDetailVO,
                                                                                    this,mapping,request);
            frmBankDetails.set("officeTypeDesc",strOffType);
            frmBankDetails.set("bankAddressVO", (DynaActionForm)FormUtils.setFormValues("frmBankAddressInfo",
                                                            bankDetailVO.getBankAddressVO(),this,mapping,request));
           
            alStateList=(ArrayList) hospitalObject.getStateInfo("123");
            hmCityList=hospitalObject.getCityInfo("DXB");
            if(hmCityList!=null){
                alCityList = (ArrayList)hmCityList.get(bankDetailVO.getBankAddressVO().getStateCode());
            }//end of if(hmCityList!=null)

            if(alCityList==null){
                alCityList=new ArrayList();
            }//end of if(alCityList==null)
            frmBankDetails.set("alStateList",alStateList);
            frmBankDetails.set("alCityList",alCityList);
            request.getSession().setAttribute("frmBankDetails",frmBankDetails);
            return this.getForward(strBankDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankDetail));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to navigate to detail screen to edit selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewBank(ActionMapping mapping,ActionForm form,HttpServletRequest request,
									HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside BankTreeListAction doEdit");
			TreeData treeData = TTKCommon.getTreeData(request);
			BankVO bankVO = null;
			BankDetailVO bankDetailVO = null;
			BankManager bankManagerObject=this.getBankManagerObject();
			HashMap hmCityList = null;
			ArrayList alStateList	=	null; 
			ArrayList alCityList = new ArrayList();
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			//Fetch the value of selected id
			DynaActionForm generalForm = (DynaActionForm)form;
			bankVO= new BankVO();
			bankDetailVO= new BankDetailVO();
			String strOfficemode="Head Office";
			String strSelectedRoot = (String)generalForm.get("selectedroot");
			String strSelectedNode = (String)generalForm.get("selectednode");
			//treeData.setSelectedRoot(-1);	//sets the selected rows
			if(strSelectedNode==null)
			{
				strSelectedNode="";
			}//end of if(strSelectedNode==null)
			if(!strSelectedNode.equals(""))
			{
				//Gets the office information
				strOfficemode="Branch Office";
			}//end of if(!strSelectedNode.equals(""))
			else
			{
				strOfficemode="Head Office";
				if(!strSelectedRoot.equals(String.valueOf(treeData.getSelectedRoot())))
					treeData.setSelectedRoot(-1);   //sets the selected rows
			}//end of else
			bankVO=(BankVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode); // from tree with min data
			Long lBankSeqID=bankVO.getBankSeqID();
			//gets all the required information based on selected bankSeqID
			bankDetailVO = (BankDetailVO)bankManagerObject.getBankDetail(lBankSeqID,TTKCommon.getUserSeqId(request));
			//setting the values to form
			DynaActionForm frmBankDetails = (DynaActionForm)FormUtils.setFormValues("frmBankDetails", bankDetailVO, 
																					this, mapping, request);
			frmBankDetails.set("bankAddressVO", (DynaActionForm)FormUtils.setFormValues("frmBankAddressInfo",
															bankDetailVO.getBankAddressVO(),this,mapping,request));
			hmCityList=hospitalObject.getCityInfo(bankDetailVO.getBankAddressVO().getStateCode());
			if(hmCityList!=null)
				alCityList = (ArrayList)hmCityList.get(bankDetailVO.getBankAddressVO().getStateCode());
			alStateList=(ArrayList) hospitalObject.getStateInfo(bankDetailVO.getBankAddressVO().getCountryCode());
			
			String stdcode	=	(String)(hmCityList.get("stdcode"));
			String isdcode	=	(String)(hmCityList.get("isdcode"));
			bankDetailVO.getBankAddressVO().setStdCode(Integer.parseInt(stdcode));
			bankDetailVO.getBankAddressVO().setIsdCode(Integer.parseInt(isdcode));
			frmBankDetails.set("bankAddressVO",FormUtils.setFormValues("frmBankAddressInfo",
					bankDetailVO.getBankAddressVO(),this,mapping,request));
			
			
			frmBankDetails.set("officeTypeDesc",strOfficemode);
			frmBankDetails.set("selectedroot",strSelectedRoot);
			frmBankDetails.set("selectednode",strSelectedNode);
			frmBankDetails.set("alCityList",alCityList);
			frmBankDetails.set("alStateList",alStateList);
			request.getSession().setAttribute("frmBankDetails",frmBankDetails);
			return this.getForward(strBankDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBankDetail));
		}//end of catch(Exception exp)
	}//end of doViewBank(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to navigate to detail screen to delete a selected record.
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
			log.debug("Inside BankTreeListAction doDelete");
			BankVO bankVO = null;
			TreeData treeData = TTKCommon.getTreeData(request);
			BankManager bankManagerObject=this.getBankManagerObject();
			//Fetch the value of selected id and deleting that record
			bankVO= new BankVO();
			BankVO bankParentVO = new BankVO();
			ArrayList alBranchOffice=null;
			ArrayList<Object> alBankDelete=new ArrayList<Object>();
			int iSelectedRoot = Integer.parseInt(request.getParameter("selectedroot"));
			String strSelectedRoot = TTKCommon.checkNull(request.getParameter("selectedroot"));
			String strSelectedNode = TTKCommon.checkNull(request.getParameter("selectednode"));
			//To get the information of child node
			bankVO=(BankVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
			//To get the information of Root node
			bankParentVO=(BankVO)treeData.getSelectedObject(strSelectedRoot,null);
			//treeData.setSelectedRoot(-1);//close the selected root
			alBankDelete.add(bankVO.getBankSeqID());
			alBankDelete.add(TTKCommon.getUserSeqId(request));
			int iDeleteCount=bankManagerObject.deleteBank(alBankDelete);
			if(iDeleteCount>0)
			{
				if(!strSelectedNode.equals(""))
				{
					//requery after deltion of the branch
					alBranchOffice = bankManagerObject.getBankBranchList(bankParentVO.getBankSeqID(),
																		 TTKCommon.getUserSeqId(request));
					((BankVO)treeData.getRootData().get(iSelectedRoot)).setBranch(alBranchOffice);
					treeData.setSelectedRoot(iSelectedRoot);
				}//end of if(!strSelectedNode.equals("") && iDeleteCount>0)
				request.getSession().setAttribute("treeData",treeData);
			}// end of if(iDeleteCount>0)
			return this.getForward(strBanklist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankDetail));
		}//end of catch(Exception exp)
	}//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
								HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside BankTreeListAction doSave");
			BankDetailVO bankDetailVO = null;
			BankAddressVO bankAddressVO = null;
			ArrayList alCityList = new ArrayList();
			ArrayList alStateList	=	null;
			BankManager bankManagerObject=this.getBankManagerObject();
			HashMap hmCityList = null;
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			DynaActionForm frmBankDetails = (DynaActionForm)form;
			bankDetailVO = (BankDetailVO)FormUtils.getFormValues(frmBankDetails, this, mapping, request);
			DynaActionForm frmBankAddressInfo = (DynaActionForm) frmBankDetails.get("bankAddressVO");
			bankAddressVO=(BankAddressVO)FormUtils.getFormValues(frmBankAddressInfo, "frmBankAddressInfo",
																 this, mapping, request);
			bankDetailVO.setBankAddressVO(bankAddressVO);
			bankDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));	//User Id


			//FETCH FILE FROM LOCAL SYSTEM
			FormFile formFile = null;
			formFile = (FormFile)frmBankDetails.get("file");
			InputStream inputStream = null;
			int formFileSize		=	0;
			String finalPath		=	"";
			FileOutputStream outputStream = null;
			if(formFile!=null && formFile.getFileSize()>0) {
				inputStream = formFile.getInputStream();
				formFileSize	=	formFile.getFileSize();

				//COPYNG FILE TO SERVER FOR BACKUP
	    		String path=TTKCommon.checkNull(TTKPropertiesReader.getPropertyValue("bankDocsUpload"));
		        File folder = new File(path);
				if(!folder.exists()){
					folder.mkdir();
				}
				finalPath=(formFile.toString().substring(0, formFile.toString().indexOf("."))+
						new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+
						formFile.toString().substring(formFile.toString().indexOf(".")));

				//above substring to get the extension of the file  at the end
				outputStream = new FileOutputStream(new File(path+finalPath));
				outputStream.write(formFile.getFileData());//Uploaded file backUp

			}//if form file is not null
			/*
			 * Add this line in IMPL CLASS to save image in binary form in DB
			 * cStmtObject.setBinaryStream(FORM_FILE, inputStream, formFileSize);
			 */
			// FETCH FILE FROM LOCAL SYSTEM


			int iCount=bankManagerObject.saveBank(bankDetailVO,inputStream,formFileSize,finalPath);
			if(iCount>0)
			{
				if(bankDetailVO.getBankSeqID()!=null)
				{
					request.setAttribute("updated","message.savedSuccessfully");
					bankDetailVO = bankManagerObject.getBankDetail(bankDetailVO.getBankSeqID(),
																   TTKCommon.getUserSeqId(request));
				}//end of if(bankDetailVO.getBankSeqID()!=null)
				else
				{					

					request.setAttribute("updated","message.addedSuccessfully");
					//frmBankDetails.initialize(mapping);
					bankDetailVO=new BankDetailVO();
					bankAddressVO=new BankAddressVO();
					bankDetailVO.setBankAddressVO(bankAddressVO);
				}//end of else
				DynaActionForm frmBankDetail= (DynaActionForm)FormUtils.setFormValues("frmBankDetails",
											   bankDetailVO, this, mapping, request);
				frmBankDetail.set("bankAddressVO",FormUtils.setFormValues("frmBankAddressInfo",
															bankDetailVO.getBankAddressVO(),this,mapping,request));
				alStateList=(ArrayList) hospitalObject.getStateInfo(bankDetailVO.getBankAddressVO().getCountryCode());
				hmCityList=hospitalObject.getCityInfo(bankDetailVO.getBankAddressVO().getStateCode());
				String stdcode	=	"0";
				String isdcode	=	"0";
				if(hmCityList!=null)
				{
					alCityList = (ArrayList)hmCityList.get(bankDetailVO.getBankAddressVO().getStateCode());
					stdcode	=	(String)(hmCityList.get("stdcode"));
					isdcode	=	(String)(hmCityList.get("isdcode"));
				}//end of if(hmCityList!=null)
				if(alCityList==null)
				{
					alCityList= new ArrayList();
				}//end of if(alCityList==null)


				bankDetailVO.getBankAddressVO().setStdCode(Integer.parseInt(stdcode));
				bankDetailVO.getBankAddressVO().setIsdCode(Integer.parseInt(isdcode));

				frmBankDetail.set("bankAddressVO",FormUtils.setFormValues("frmBankAddressInfo",
						bankDetailVO.getBankAddressVO(),this,mapping,request));

				frmBankDetail.set("alCityList",alCityList);
				frmBankDetail.set("alStateList",alStateList);
				frmBankDetail.set("bankSeqID",frmBankDetails.getString("bankSeqID"));
				frmBankDetail.set("headOfficeTypeID",frmBankDetails.getString("headOfficeTypeID"));
				frmBankDetail.set("officeTypeDesc",frmBankDetails.getString("officeTypeDesc"));
				request.getSession().setAttribute("frmBankDetails",frmBankDetail);
			}//end of if(iCount>0)
			Cache.refresh("bankName");

			//closing the opened objects
			if(outputStream!=null) outputStream.close();
			if(inputStream!=null) inputStream.close();

			return this.getForward(strBankDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankDetail));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to load the sub status based on the selected status.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doStateChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,
									   HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside BankTreeListAction doStateChange");
			BankDetailVO bankDetailVO = null;
			BankAddressVO bankAddressVO = null;
			HashMap hmCityList = null;
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			ArrayList alCityList = new ArrayList();
			DynaActionForm frmBankDetails = (DynaActionForm)form;
			bankDetailVO = (BankDetailVO)FormUtils.getFormValues(frmBankDetails, this, mapping, request);
			DynaActionForm frmBankAddressInfo = (DynaActionForm) frmBankDetails.get("bankAddressVO");
			bankAddressVO=(BankAddressVO)FormUtils.getFormValues(frmBankAddressInfo, "frmBankAddressInfo",
																 this, mapping, request);
			bankDetailVO.setBankAddressVO(bankAddressVO);
			hmCityList=hospitalObject.getCityInfo(bankDetailVO.getBankAddressVO().getStateCode());
			if(hmCityList!=null)
			{
				alCityList = (ArrayList)hmCityList.get(bankDetailVO.getBankAddressVO().getStateCode());
			}//end of if(hmCityList!=null)
			if(alCityList==null)
			{
				alCityList=new ArrayList();
			}//end of if(alCityList==null)

			String stdcode	=	(String)(hmCityList.get("stdcode"));
            bankAddressVO.setStdCode(Integer.parseInt(stdcode));
			frmBankDetails.set("bankAddressVO",FormUtils.setFormValues("frmBankAddressInfo",
					bankDetailVO.getBankAddressVO(),this,mapping,request));

			frmBankDetails.set("frmChanged","changed");
			frmBankDetails.set("alCityList",alCityList);
			return this.getForward(strBankDetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)

		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBankDetail));
		}//end of catch(Exception exp)
	}//end of doStateChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to load the sub status based on the selected status.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeCountry(ActionMapping mapping,ActionForm form,HttpServletRequest request,
									   HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside BankTreeListAction doChangeCountry");
			BankDetailVO bankDetailVO = null;
			BankAddressVO bankAddressVO = null;
			HashMap hmCityList = null;
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			ArrayList alStateList = new ArrayList();
			DynaActionForm frmBankDetails = (DynaActionForm)form;
			bankDetailVO = (BankDetailVO)FormUtils.getFormValues(frmBankDetails, this, mapping, request);
			DynaActionForm frmBankAddressInfo = (DynaActionForm) frmBankDetails.get("bankAddressVO");
			bankAddressVO=(BankAddressVO)FormUtils.getFormValues(frmBankAddressInfo, "frmBankAddressInfo",
																 this, mapping, request);
			bankDetailVO.setBankAddressVO(bankAddressVO);
			alStateList=(ArrayList) hospitalObject.getStateInfo(bankDetailVO.getBankAddressVO().getCountryCode());

			int isdCode	=	hospitalObject.getIsdCode(bankDetailVO.getBankAddressVO().getCountryCode());
			bankAddressVO.setIsdCode(isdCode);
			frmBankDetails.set("bankAddressVO",FormUtils.setFormValues("frmBankAddressInfo",
			bankDetailVO.getBankAddressVO(),this,mapping,request));
			frmBankDetails.set("frmChanged","changed");
			frmBankDetails.set("alStateList",alStateList);
			return this.getForward(strBankDetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)

		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBankDetail));
		}//end of catch(Exception exp)
	}//end of doChangeCountry(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
								 HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside BankTreeListAction doReset");
			BankDetailVO bankDetailVO = null;
			BankAddressVO bankAddressVO = null;
			BankManager bankManagerObject=this.getBankManagerObject();
			HashMap hmCityList = null;
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			ArrayList alCityList = new ArrayList();
			DynaActionForm frmBankDetails = (DynaActionForm)form;
			if(frmBankDetails.get("bankSeqID")!=null && !frmBankDetails.get("bankSeqID").equals(""))
			{
				bankDetailVO = (BankDetailVO)bankManagerObject.getBankDetail(
						TTKCommon.getLong((String)frmBankDetails.get("bankSeqID")),TTKCommon.getUserSeqId(request));
			}//end of if(frmBankDetails.get("bankSeqID")!=null && !frmBankDetails.get("bankSeqID").equals(""))
			else
			{
				bankDetailVO = new BankDetailVO();
				bankAddressVO = new BankAddressVO();
				bankDetailVO.setBankAddressVO(bankAddressVO);
			}//end of else
			DynaActionForm frmBankDetail = (DynaActionForm)FormUtils.setFormValues("frmBankDetails", bankDetailVO,
																					this, mapping, request);
			frmBankDetail.set("bankSeqID",frmBankDetails.getString("bankSeqID"));
			frmBankDetail.set("headOfficeTypeID",frmBankDetails.getString("headOfficeTypeID"));
			frmBankDetail.set("officeTypeDesc",frmBankDetails.getString("officeTypeDesc"));
			frmBankDetail.set("bankAddressVO",FormUtils.setFormValues("frmBankAddressInfo",
															bankDetailVO.getBankAddressVO(),this,mapping,request));
			hmCityList=hospitalObject.getCityInfo();
			if((hmCityList!=null) && bankDetailVO != null){
				alCityList = (ArrayList)hmCityList.get(bankDetailVO.getBankAddressVO().getStateCode());
			}//end of if((hmCityList!=null) && bankDetailVO != null)
			if(alCityList==null){
				alCityList=new ArrayList();
			}//end of if(alCityList==null)
			frmBankDetail.set("alCityList",alCityList);
			request.getSession().setAttribute("frmBankDetails",frmBankDetail);
			return this.getForward(strBankDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankDetail));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to load the sub status based on the selected status.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doContact(ActionMapping mapping,ActionForm form,HttpServletRequest request,
								   HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside BankTreeListAction doContact");
			BankVO bankVO = null;
			TreeData treeData = TTKCommon.getTreeData(request);
			DynaActionForm generalForm = (DynaActionForm)form;
			bankVO=new BankVO();
			bankVO=(BankVO)treeData.getSelectedObject((String)generalForm.get("selectedroot"),
													  (String)generalForm.get("selectednode"));
			String strBankName="[";
			strBankName+=bankVO.getBankName()+"]";
			request.setAttribute("caption",strBankName);
			request.getSession().setAttribute("bankSeqId",bankVO.getBankSeqID().toString());
			return mapping.findForward(strBankContacts);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBankDetail));
		}//end of catch(Exception exp)
	}//end of doContact(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to navigate to previous screen when close button is clicked.
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
								 HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside BankTreeListAction doClose");
			TreeData treeData = TTKCommon.getTreeData(request);
			BankManager bankManagerObject=this.getBankManagerObject();
			BankVO bankVO = null;
			treeData = TTKCommon.getTreeData(request);
			ArrayList alBanks =null;
			if(treeData.getSearchData() != null && treeData.getSearchData().size() > 0)
			{
				alBanks = bankManagerObject.getBankList(treeData.getSearchData());
				treeData.setRootData(alBanks);
				//set the tree data object to session
				request.getSession().setAttribute("treeData",treeData);
				treeData.setForwardNextLink();
				if(treeData.getSelectedRoot()>=0 && alBanks!=null && alBanks.size()>0)
				{
					bankVO = (BankVO)alBanks.get(treeData.getSelectedRoot());
					bankVO.setBranch(bankManagerObject.getBankBranchList(bankVO.getBankSeqID(),
																		 TTKCommon.getUserSeqId(request)));
				}//end of if(treeData.getSelectedRoot()>=0 && alBanks!=null && alBanks.size()>0)
			}// end of if(treeData.getSearchData() != null && treeData.getSearchData().size() > 0)
			return this.getForward(strBanklist, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankDetail));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to display an error message when the General tab is directly clicked without selecting any record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doCheck(ActionMapping mapping,ActionForm form,HttpServletRequest request,
								 HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside BankTreeListAction doCheck");
			TTKException expTTK = new TTKException();
			expTTK.setMessage("error.bankname.required");
			throw expTTK;
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strBankDetail));
		}//end of catch(Exception exp)
	}//end of doCheck(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmBankList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */

	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSearchUser,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchUser.get("BankName")));
		//alSearchParams.add((String)frmSearchUser.get("City"));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmSearchUser,HttpServletRequest request)

	/**
	 * Returns the UserManager session object for invoking methods on it.
	 * @return UserManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private BankManager getBankManagerObject() throws TTKException
	{
		BankManager bankManager = null;
		try
		{
			if(bankManager == null)
			{
				InitialContext ctx = new InitialContext();
				bankManager = (BankManager) ctx.lookup("java:global/TTKServices/business.ejb3/BankManagerBean!com.ttk.business.finance.BankManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "bankdetail");
		}//end of catch
		return bankManager;
	}//end getBankManagerObject()

	/**
	 * Returns the HospitalManager session object for invoking methods on it.
	 * @return HospitalManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private HospitalManager getHospitalManagerObject() throws TTKException
	{
		HospitalManager hospManager = null;
		try
		{
			if(hospManager == null)
			{
				InitialContext ctx = new InitialContext();
				hospManager = (HospitalManager) ctx.lookup("java:global/TTKServices/business.ejb3/HospitalManagerBean!com.ttk.business.empanelment.HospitalManager");
			}//end if(hospManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "bankdetail");
		}//end of catch
		return hospManager;
	}//end getHospitalManagerObject()

	/**
	 * Check the ACL permission and set the display property of icons.
	 * @param request The HTTP request we are processing
	 * @param treeData TreeData for which permission has to check
	 */
	private void checkTreePermission(HttpServletRequest request,TreeData treeData) throws TTKException
	{
		// check the permision and set the tree for not to display respective icon
		if(TTKCommon.isAuthorized(request,"Add")==false)
		{
			treeData.getTreeSetting().getRootNodeSetting().setIconVisible(0,false);
		}//end of if(TTKCommon.isAuthorized(request,"Add")==false)
		if(TTKCommon.isAuthorized(request,"Edit")==false)
		{
			treeData.getTreeSetting().getRootNodeSetting().setIconVisible(1,false);
			treeData.getTreeSetting().getChildNodeSetting().setIconVisible(0,false);
		}//end of if(TTKCommon.isAuthorized(request,"Edit")==false)
		if(TTKCommon.isAuthorized(request,"Delete")==false)
		{
			treeData.getTreeSetting().getChildNodeSetting().setIconVisible(2,false);
		}// end of if(TTKCommon.isAuthorized(request,"Delete")==false)
	}//end of checkTreePermission(HttpServletRequest request,TreeData treeData)
}//end of class BankTreeListAction


