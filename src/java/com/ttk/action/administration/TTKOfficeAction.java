/**
 * @ (#) TTKOfficeAction.java Mar 21st, 2006
 * Project       : TTK HealthCare Services
 * File          : TTKOfficeAction.java
 * Author        : Krishna K H
 * Company       : Span Systems Corporation
 * Date Created  : Mar 21st, 2006
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.administration;

import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.tree.TreeData;
import com.ttk.business.administration.TtkOfficeManager;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.administration.TpaOfficeVO;
import com.ttk.dto.empanelment.AddressVO;

import formdef.plugin.util.FormUtils;

/**
* This class is used for displaying the office details which will be in tree structure.
* This also provides  updation of office details.
*/
public class TTKOfficeAction extends TTKAction {
	
	private static Logger log = Logger.getLogger( TTKOfficeAction.class );
	
	//mode
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	
	//Exception Message Identifier
	private static final String strTpaofficeExp="tpaoffice";
	
	//	declaration of constants specifying the position of icons to be disabled based on condtions and permissions

    private static final int ROOT_ADD_ICON=0;
    private static final int ROOT_DELETE_ICON=3;
    
    private static final int CHILD_DELETE_ICON=2;
    
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
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the Default method of TTKOfficeAction");
			setLinks(request);
			TtkOfficeManager ttkOfficeManager=this.getTtkOfficeManager();
			TreeData treeData = TTKCommon.getTreeData(request);
			
			ArrayList<Object> alSearchParam = new ArrayList<Object>();
			DynaActionForm generalForm = (DynaActionForm)form;
			ArrayList<Object> alHeadOffParam = new ArrayList<Object>();
			
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			// To get the Head Office Details
			alHeadOffParam.add(null); 
			alHeadOffParam.add("1");
			alHeadOffParam.add("1");
			alHeadOffParam.add("1");
			//if the page number is clicked, display the appropriate page
			if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
			{
				treeData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				treeData.setSelectedRoot(-1);
				return this.getForward("ttkoffice", mapping, request);
			}//end of if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
			treeData = new TreeData();
			//create the required tree
			treeData.createTreeInfo("TTKOfficeTree",null,true);
			//check the permision and set the tree for not to display respective icon
            this.checkTreePermission(request,treeData);
			request.getSession().setAttribute("treeData",null);
			alSearchParam.add(null);
			alSearchParam.add("2");
			treeData.setSearchData(alSearchParam);
			//get the Office list from database
			treeData.modifySearchData("search");
			//Get the Head Office Details
			ArrayList alHeadOffice = ttkOfficeManager.getOfficeDetails(alHeadOffParam);
			//Get the Zonal/Branch Offices Details
			ArrayList alOffice = ttkOfficeManager.getOfficeDetails(treeData.getSearchData());
			//Set the Head Office details to form
			TpaOfficeVO tpaOfficeVO = (TpaOfficeVO)alHeadOffice.get(0);
			generalForm.set("headOfficeSeqID",tpaOfficeVO.getOfficeSequenceID().toString());
			generalForm.set("headOfficeName",tpaOfficeVO.getOfficeName());
			generalForm.set("headOfficeTypeID",tpaOfficeVO.getOfficeTypeID());
			treeData.setData(alOffice,"search");
			//set the tree data object to session
			request.getSession().setAttribute("treeData",treeData);
			return this.getForward("ttkoffice", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTpaofficeExp));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doShowHide(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doShowHide method of TTKOfficeAction");
			setLinks(request);
			TtkOfficeManager ttkOfficeManager=this.getTtkOfficeManager();
			TreeData treeData = TTKCommon.getTreeData(request);
			
			ArrayList<Object> alSearchParam = new ArrayList<Object>();
			TpaOfficeVO tpaOfficeVO = null;
			int iSelectedRoot = Integer.parseInt((String)request.getParameter("selectedroot"));
			//create the required tree
			ArrayList alNodeOffice = new ArrayList();
			alSearchParam = new ArrayList<Object>();
			tpaOfficeVO = (TpaOfficeVO)treeData.getSelectedObject(request.getParameter("selectedroot"),
																request.getParameter("selectednode"));
			//create search parameter to get the dependent list
			log.debug("tpaOfficeVO.getOfficeSequenceID() -- "+tpaOfficeVO.getOfficeSequenceID());
			alSearchParam.add(tpaOfficeVO.getOfficeSequenceID().toString());
			// parameter to procedure 3->Branch Level records
			alSearchParam.add("2");     
			// since there is no start row count and end row count for node level
			alSearchParam.add("1");     
			alSearchParam.add("25000");  
			//get the Zonal/Branch Offices list from database
			alNodeOffice = ttkOfficeManager.getOfficeDetails(alSearchParam);
			// set the selected root
			treeData.setSelectedRoot(iSelectedRoot);
			((TpaOfficeVO)treeData.getRootData().get(iSelectedRoot)).setOfficeList(alNodeOffice);
			treeData.setSelectedRoot(iSelectedRoot);
			request.getSession().setAttribute("treeData",treeData);
			return this.getForward("ttkoffice", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTpaofficeExp));
		}//end of catch(Exception exp)
	}//end of doShowHide(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
	/**
	 * This method is used to get the previous set of records with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
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
			log.debug("Inside the doBackward method of TTKOfficeAction");
			setLinks(request);
			TtkOfficeManager ttkOfficeManager=this.getTtkOfficeManager();
			TreeData treeData = TTKCommon.getTreeData(request);
			
			treeData.modifySearchData(strBackward);//modify the search data
			ArrayList alOffice = ttkOfficeManager.getOfficeDetails(treeData.getSearchData());
			treeData.setData(alOffice, strBackward);//set the table data
			request.getSession().setAttribute("treeData",treeData);	
			return this.getForward("ttkoffice", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTpaofficeExp));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
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
			log.debug("Inside the doForward method of TTKOfficeAction");
			setLinks(request);
			TtkOfficeManager ttkOfficeManager=this.getTtkOfficeManager();
			TreeData treeData = TTKCommon.getTreeData(request);
			treeData.modifySearchData(strForward);//modify the search data
			ArrayList alOffice = ttkOfficeManager.getOfficeDetails(treeData.getSearchData());
			treeData.setData(alOffice, strForward);//set the table data
			request.getSession().setAttribute("treeData",treeData);
			return this.getForward("ttkoffice", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTpaofficeExp));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doViewOffice(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doViewOffice method of TTKOfficeAction");
			setLinks(request);
			TtkOfficeManager ttkOfficeManager=this.getTtkOfficeManager();
			TreeData treeData = TTKCommon.getTreeData(request);
			
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append("Vidal Health Office - ");
			//This manager is used to get a method, which return a Hash map of cities.
			HospitalManager hospitalManagerObject=this.getHospitalManagerObject();
			
			DynaActionForm frmAddTTKOffice = (DynaActionForm)form;
			String strReportTO="true";
			DynaActionForm frmTTKOffice=(DynaActionForm)request.getSession().getAttribute("frmTTKOffice");
			String strSelectedRoot=TTKCommon.checkNull(request.getParameter("selectedroot"));
			String strSelectedNode=TTKCommon.checkNull(request.getParameter("selectednode"));
			String strHOSeqId=null;
			TpaOfficeVO tpaOfficeVO=new TpaOfficeVO();
			if(strSelectedRoot.equals(""))
			{
				strHOSeqId=frmTTKOffice.getString("headOfficeSeqID");
				strReportTO="false";
				tpaOfficeVO= ttkOfficeManager.getTtkOfficeDetail(TTKCommon.getLong(strHOSeqId));
				sbfCaption.append("Edit").append("[").append(frmTTKOffice.get("headOfficeName")).append("]");
			}//end of if(strSelectedRoot.equals(""))
			else
			{
				treeData.setSelectedRoot(Integer.parseInt(strSelectedRoot));
				Long lngOfficeSeqID=((TpaOfficeVO)treeData.getSelectedObject(strSelectedRoot,
													strSelectedNode)).getOfficeSequenceID();
				tpaOfficeVO= ttkOfficeManager.getTtkOfficeDetail(lngOfficeSeqID);
				sbfCaption.append("Edit").append("[").append(((TpaOfficeVO)treeData.
						  getSelectedObject(strSelectedRoot,null)).getOfficeName());
				if(!strSelectedNode.equals(""))
				{
					sbfCaption.append(" - ").append(((TpaOfficeVO)treeData.getSelectedObject(
							  strSelectedRoot,strSelectedNode)).getOfficeName()).append("]");
				}//end of if(strSelectedNode.equals(""))
				else
				{
					sbfCaption.append("]");
				}//end of else
			}//end of else
			frmAddTTKOffice = (DynaActionForm)FormUtils.setFormValues("frmAddTTKOffice", tpaOfficeVO,
																			 this, mapping, request);
			frmAddTTKOffice.set("addressVO",FormUtils.setFormValues("frmTtkOfficeAddress",
										tpaOfficeVO.getAddressVO(),this,mapping,request));
			frmAddTTKOffice.set("reportTo",strReportTO);
			frmAddTTKOffice.set("caption",sbfCaption.toString());
			HashMap hmCityList = hospitalManagerObject.getCityInfo();
			ArrayList alCityList = new ArrayList();
			if(hmCityList!=null)
			{
				alCityList = (ArrayList)hmCityList.get(tpaOfficeVO.getAddressVO().getStateCode());
			}//end of if(hmCityList!=null)
			frmAddTTKOffice.set("alCityList",alCityList);
			request.getSession().setAttribute("frmAddTTKOffice",frmAddTTKOffice);
			return this.getForward("addttkoffice", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTpaofficeExp));
		}//end of catch(Exception exp)
	}//end of doViewOffice(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
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
			log.debug("Inside the doAdd method of TTKOfficeAction");
			setLinks(request);
			TreeData treeData = TTKCommon.getTreeData(request);
			
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption.append("Vidal Health Office - ");
			
			DynaActionForm frmAddTTKOffice = (DynaActionForm)form;
			DynaActionForm frmTTKOffice=(DynaActionForm)request.getSession().getAttribute("frmTTKOffice");
			String strSelectedRoot=TTKCommon.checkNull(request.getParameter("selectedroot"));
			String strSelectedNode=TTKCommon.checkNull(request.getParameter("selectednode"));
			if(!strSelectedRoot.equals(""))
			{
				treeData.setSelectedRoot(Integer.parseInt(strSelectedRoot));
			}//end of if(!strSelectedRoot.equals(""))
			String strHOSeqId=null;
			TpaOfficeVO tpaOfficeVO=new TpaOfficeVO();
			AddressVO addressVO = new AddressVO();
			tpaOfficeVO.setAddressVO(addressVO);
			if(strSelectedRoot.equals(""))
			{
				strHOSeqId=frmTTKOffice.getString("headOfficeSeqID");
				if(!strHOSeqId.equals(""))
				{
					tpaOfficeVO.setParentOfficeSequenceID(TTKCommon.getLong(strHOSeqId));
				}//end of if(!strHOSeqId.equals(""))
				sbfCaption.append("Add").append("[").append(frmTTKOffice.get("headOfficeName")).append("]");
			}//end of if(strSelectedRoot.equals(""))
			else
			{
				Long lngOfficeSeqID=((TpaOfficeVO)treeData.getSelectedObject(strSelectedRoot,
													strSelectedNode)).getOfficeSequenceID();
				tpaOfficeVO.setParentOfficeSequenceID(lngOfficeSeqID);
				sbfCaption.append("Add").append("[").append(((TpaOfficeVO)treeData.getSelectedObject(
												 strSelectedRoot,null)).getOfficeName()).append("]");
			}//end of else
			tpaOfficeVO.setOfficeTypeID("TBO");
			tpaOfficeVO.setOfficeTypeDesc("Branch Office");
			tpaOfficeVO.setActiveYN("Y");
			frmAddTTKOffice = (DynaActionForm)FormUtils.setFormValues("frmAddTTKOffice",
													tpaOfficeVO, this, mapping, request);
			frmAddTTKOffice.set("addressVO",FormUtils.setFormValues("frmTtkOfficeAddress",
										tpaOfficeVO.getAddressVO(),this,mapping,request));
			frmAddTTKOffice.set("enabled","false");
			frmAddTTKOffice.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmAddTTKOffice",frmAddTTKOffice);
			return this.getForward("addttkoffice", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTpaofficeExp));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside the doSave method of TTKOfficeAction");
			setLinks(request);
			TtkOfficeManager ttkOfficeManager=this.getTtkOfficeManager();
			DynaActionForm frmAddTTKOffice = (DynaActionForm)form;
			String strCaption =(String) frmAddTTKOffice.get("caption");
			TpaOfficeVO tpaOfficeVO=new TpaOfficeVO();
			//get the value from form and store it to the respective VO's
			tpaOfficeVO=(TpaOfficeVO)FormUtils.getFormValues(frmAddTTKOffice,this,mapping,request);
			ActionForm frmTtkOfficeAddress=(ActionForm)frmAddTTKOffice.get("addressVO");
			AddressVO addressVO=(AddressVO)FormUtils.getFormValues(frmTtkOfficeAddress,"frmTtkOfficeAddress",
																						this,mapping,request);
			tpaOfficeVO.setAddressVO(addressVO);
			tpaOfficeVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			Long lngCount=ttkOfficeManager.addUpdateTtkOfficeDetail(tpaOfficeVO);
			log.debug("lngCount value is :"+lngCount);
			if(tpaOfficeVO.getOfficeSequenceID()!=null)
			{
				Cache.refresh("officeInfo");
				Cache.refresh("ttkheadofficelist");
				if(tpaOfficeVO.getOfficeTypeID().equals("THO"))
				{
					//if head office is modified set the value to tree screen
					DynaActionForm frmTTKOffice = (DynaActionForm)request.getSession().getAttribute("frmTTKOffice");
					frmTTKOffice.set("headOfficeName",tpaOfficeVO.getOfficeName());
				}//end of if(tpaOfficeVO.getOfficeTypeID().equals("THO"))
				//set the appropriate message
				request.setAttribute("updated","message.savedSuccessfully");
			}//end of if(tpaOfficeVO.getOfficeSequenceID()!=null)
			else
			{
				//set the appropriate message
				request.setAttribute("updated","message.addedSuccessfully");
				//refresh the cache so that changes are seen in other screens
				Cache.refresh("ttkheadofficelist");       
				Cache.refresh("officeInfo");
				String strParentOfficeSequenceID=(String)frmAddTTKOffice.get("parentOfficeSequenceID");
				tpaOfficeVO=new TpaOfficeVO();
				tpaOfficeVO.setParentOfficeSequenceID(TTKCommon.getLong(strParentOfficeSequenceID));
				tpaOfficeVO.setOfficeTypeID("TBO");
				tpaOfficeVO.setOfficeTypeDesc("Branch Office");
				tpaOfficeVO.setActiveYN("Y");
				tpaOfficeVO.setAddressVO(new AddressVO());
				frmAddTTKOffice = (DynaActionForm)FormUtils.setFormValues("frmAddTTKOffice",
													   tpaOfficeVO, this, mapping, request);
				frmAddTTKOffice.set("addressVO",FormUtils.setFormValues("frmTtkOfficeAddress",
											tpaOfficeVO.getAddressVO(),this,mapping,request));
				frmAddTTKOffice.set("enabled","false");
			}// end of else
			frmAddTTKOffice.set("caption",strCaption);
			frmAddTTKOffice.set("frmChanged","");
			request.getSession().setAttribute("frmAddTTKOffice",frmAddTTKOffice);
			return this.getForward("addttkoffice", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTpaofficeExp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside the doReset method of TTKOfficeAction");
			setLinks(request);
			TtkOfficeManager ttkOfficeManager=this.getTtkOfficeManager();
			//This manager is used to get a method, which return a Hash map of cities.
			HospitalManager hospitalManagerObject=this.getHospitalManagerObject();
			
			ArrayList alCityList = new ArrayList();
			DynaActionForm frmAddTTKOffice = (DynaActionForm)form;
			String strCaption =(String) frmAddTTKOffice.get("caption");
			String strReportTO =(String)frmAddTTKOffice.get("reportTo");
			String strEnabled =(String)frmAddTTKOffice.get("enabled");
			String strparentOfficeSequenceID=(String)frmAddTTKOffice.get("parentOfficeSequenceID");
			TpaOfficeVO tpaOfficeVO=new TpaOfficeVO();
			AddressVO addressVO = new AddressVO();
			tpaOfficeVO.setAddressVO(addressVO);
			tpaOfficeVO.setOfficeTypeID("TBO");
			tpaOfficeVO.setOfficeTypeDesc("Branch Office");
			if(frmAddTTKOffice.get("officeSequenceID")!=null && !((String)frmAddTTKOffice.
													get("officeSequenceID")).equals(""))
			{
				tpaOfficeVO= ttkOfficeManager.getTtkOfficeDetail(TTKCommon.getLong(
								((String)frmAddTTKOffice.get("officeSequenceID"))));
				strparentOfficeSequenceID=String.valueOf(tpaOfficeVO.getParentOfficeSequenceID());
				HashMap hmCityList = hospitalManagerObject.getCityInfo();
				if(hmCityList!=null)
				{
					alCityList = (ArrayList)hmCityList.get(tpaOfficeVO.getAddressVO().getStateCode());
				}//end of if(hmCityList!=null)
			}//end of if(frmAddTTKOffice.get("officeSequenceID")!=null && !
				//((String)frmAddTTKOffice.get("officeSequenceID")).equals(""))
			else
			{
				tpaOfficeVO.setActiveYN("Y");
			}// end of else
			frmAddTTKOffice = (DynaActionForm)FormUtils.setFormValues("frmAddTTKOffice", 
													tpaOfficeVO, this, mapping, request);
			frmAddTTKOffice.set("addressVO",FormUtils.setFormValues("frmTtkOfficeAddress",
										tpaOfficeVO.getAddressVO(),this,mapping,request));
			frmAddTTKOffice.set("enabled",strEnabled);
			frmAddTTKOffice.set("reportTo",strReportTO);
			frmAddTTKOffice.set("caption",strCaption);
			frmAddTTKOffice.set("parentOfficeSequenceID",strparentOfficeSequenceID);
			frmAddTTKOffice.set("alCityList",alCityList);
			request.getSession().setAttribute("frmAddTTKOffice",frmAddTTKOffice);
			return this.getForward("addttkoffice", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTpaofficeExp));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doClose method of TTKOfficeAction");
			setLinks(request);
			TtkOfficeManager ttkOfficeManager=this.getTtkOfficeManager();
			TreeData treeData = TTKCommon.getTreeData(request);
			ArrayList<Object> alSearchParam = new ArrayList<Object>();
			
			int iStartRowCount = Integer.parseInt((String)treeData.getSearchData().
											get(treeData.getSearchData().size()-2));
			int iSelectedRoot = treeData.getSelectedRoot();
			ArrayList alOffice = ttkOfficeManager.getOfficeDetails(treeData.getSearchData());
			//if Branch office under Head office is moved under other zonal office reset 
			//the search data and requery to data base
			if(iStartRowCount!=1 && (alOffice==null ||alOffice.size()==0))
			{
				treeData.modifySearchData("Delete");//modify the search data
				alOffice = ttkOfficeManager.getOfficeDetails(treeData.getSearchData());
			}//end of if(iStartRowCount==1)
			treeData.setData(alOffice, "Delete");//set the table data
			ArrayList alNodeOffice = new ArrayList();
			alSearchParam = new ArrayList<Object>();
			if(iSelectedRoot>=0)
			{
				TpaOfficeVO tpaOfficeVO = (TpaOfficeVO)treeData.getSelectedObject(String.valueOf(iSelectedRoot),null);
				//create search parameter to get the dependent list
				alSearchParam.add(tpaOfficeVO.getOfficeSequenceID().toString());
				// parameter to procedure 3->Branch Level records
				alSearchParam.add("2"); 
				//since there is no start row count and end row count for node level
				alSearchParam.add("1");     
				alSearchParam.add("25000");  
				//get the Zonal/Branch Offices list from database
				alNodeOffice = ttkOfficeManager.getOfficeDetails(alSearchParam);
				tpaOfficeVO.setOfficeList(alNodeOffice);
				treeData.setSelectedRoot(iSelectedRoot);
				request.getSession().setAttribute("treeData",treeData);
			}//end of if(iSelectedRoot>=0)
			return this.getForward("ttkoffice", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTpaofficeExp));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to delete the record from the list.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doDeleteList method of TTKOfficeAction");
			setLinks(request);
			TpaOfficeVO tpaOfficeVO= new TpaOfficeVO();
			TtkOfficeManager ttkOfficeManager=this.getTtkOfficeManager();
			TreeData treeData = TTKCommon.getTreeData(request);
			ArrayList<Object> alSearchParam = new ArrayList<Object>();
			
			int iResult=0;
			alSearchParam = new ArrayList<Object>();
			String strSelectedRoot = (String)request.getParameter("selectedroot");
			String strSelectedNode = (String)request.getParameter("selectednode");
			tpaOfficeVO=(TpaOfficeVO)treeData.getSelectedObject(strSelectedRoot,strSelectedNode);
			alSearchParam.add(tpaOfficeVO.getOfficeSequenceID());
			alSearchParam.add(TTKCommon.getUserSeqId(request));
			iResult= ttkOfficeManager.deleteTtkOffice(alSearchParam);
			ArrayList alOffice = null;
			if(TTKCommon.checkNull(strSelectedNode).equals(""))
			{
				if(iResult == treeData.getRootData().size())
				{
					treeData.modifySearchData("Delete");//modify the search data
					int iStartRowCount = Integer.parseInt((String)treeData.getSearchData().
													get(treeData.getSearchData().size()-2));
					if(iStartRowCount > 0)
					{
						alOffice = ttkOfficeManager.getOfficeDetails(treeData.getSearchData());
					}//end of if(iStartRowCount > 0)
				}//end if(iResult == treeData.getRootData().size())
				else
				{
					alOffice = ttkOfficeManager.getOfficeDetails(treeData.getSearchData());
				}//end of else
				treeData.setData(alOffice, "Delete");
			}//end of if(TTKCommon.checkNull(strSelectedNode).equals(""))
			else
			{
				ArrayList alNodeOffice = new ArrayList();
				alSearchParam = new ArrayList<Object>();
				tpaOfficeVO = (TpaOfficeVO)treeData.getSelectedObject(strSelectedRoot,null);
				//create search parameter to get the dependent list
				alSearchParam.add(tpaOfficeVO.getOfficeSequenceID().toString());
				alSearchParam.add("2");     
				alSearchParam.add("1");     
				alSearchParam.add("25000"); 
				//get the Zonal/Branch Offices list from database
				alNodeOffice = ttkOfficeManager.getOfficeDetails(alSearchParam);
				tpaOfficeVO.setOfficeList(alNodeOffice);
			}//end of else
			//refresh the cache so that changes are seen in other screens
			Cache.refresh("ttkheadofficelist");       
			Cache.refresh("officeInfo");
			request.getSession().setAttribute("treeData",treeData);
			return this.getForward("ttkoffice", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTpaofficeExp));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
	/**
	 * This method is used to when we change the state.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doOnStateChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doOnStateChange method of TTKOfficeAction");
			setLinks(request);
			
			//This manager is used to get a method, which return a Hash map of cities.
			HospitalManager hospitalManagerObject=this.getHospitalManagerObject();
			//check the permision and set the tree for not to display respective icon
			
			DynaActionForm frmAddTTKOffice = (DynaActionForm) form;
			
			//get the value from form and store it to the respective VO's
			
			ActionForm frmTtkOfficeAddress=(ActionForm)frmAddTTKOffice.get("addressVO");
			AddressVO addressVO=(AddressVO)FormUtils.getFormValues(frmTtkOfficeAddress,"frmTtkOfficeAddress",
																						this,mapping,request);
			HashMap hmCityList = hospitalManagerObject.getCityInfo();
			ArrayList alCityList = new ArrayList();
			if(hmCityList!=null)
			{
				alCityList = (ArrayList)hmCityList.get(addressVO.getStateCode());
			}//end of if(hmCityList!=null)
			if(alCityList==null)
			{
				alCityList=new ArrayList();
			}//end of if(alCityList==null)
			frmAddTTKOffice.set("frmChanged","changed");
			frmAddTTKOffice.set("alCityList",alCityList);
			return this.getForward("addttkoffice", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTpaofficeExp));
		}//end of catch(Exception exp)
	}//end of doOnStateChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
	/**
	 * Returns the TtkOfficeManager session object for invoking methods on it.
	 * @return TtkOfficeManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private TtkOfficeManager getTtkOfficeManager() throws TTKException
	{
		TtkOfficeManager ttkOfficeManager = null;
		try
		{
			if(ttkOfficeManager == null)
			{
				InitialContext ctx = new InitialContext();
				ttkOfficeManager = (TtkOfficeManager) ctx.lookup("java:global/TTKServices/business.ejb3/TtkOfficeManagerBean!com.ttk.business.administration.TtkOfficeManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "tpaoffice");
		}//end of catch
		return ttkOfficeManager;
	}//end getTtkOfficeManager()
	
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
			throw new TTKException(exp, "AdminHospitalList");
		}//end of catch
		return hospitalManager;
	}//end getHospitalManagerObject()
	
	/**
     * Check the ACL permission and set the display property of icons.
     * @param request The HTTP request we are processing
     * @param treeData TreeData for which permission has to check
     */
    private void checkTreePermission(HttpServletRequest request,TreeData treeData)
    	throws TTKException
    {
        log.debug("--------- inside check Tree permission --------------");
        
        // check the permision and set the tree for not to display respective icon
        if(TTKCommon.isAuthorized(request,"Add")==false)
        {
            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(ROOT_ADD_ICON,false);
        }//end of if(TTKCommon.isAuthorized(request,"Add")==false)
        if(TTKCommon.isAuthorized(request,"Delete")==false)
        {
            treeData.getTreeSetting().getRootNodeSetting().setIconVisible(ROOT_DELETE_ICON,false);
            treeData.getTreeSetting().getChildNodeSetting().setIconVisible(CHILD_DELETE_ICON,false);
        }// end of if(TTKCommon.isAuthorized(request,"Delete")==false)
        log.debug("---------(Exit) inside check Tree permission --------------");
    }//end of checkTreePermission(HttpServletRequest request,TreeData treeData,String strSwitchType)
}//end of TTKOfficeAction
