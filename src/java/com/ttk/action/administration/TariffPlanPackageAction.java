/**
 * @ (#) TariffPlanPackageAction.java Oct 22, 2005
 * Project      : TTK HealthCare Services
 * File         : TariffPlanPackageAction.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Oct 22, 2005
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.administration;

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
import com.ttk.business.administration.TariffManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.PlanPackageVO;
import com.ttk.dto.administration.RateVO;
import com.ttk.dto.administration.RevisionPlanVO;
import com.ttk.dto.empanelment.InsuranceVO;

/**
 * This class is used for Searching the List of Packages.
 * This also provides deletion and updation of Packages.
 */
public class TariffPlanPackageAction extends TTKAction{

	private static Logger log = Logger.getLogger( TariffPlanPackageAction.class );

	//Modes.
	private static final String strBackward="Backward";
	private static final String strForward="Forward";

	//Forward paths
	private static final String strAdminPlanPackage="adminplanpackage";
	private static final String strHospPlanPackage="hospplanpackage";
	private static final String strAdminManageRate="adminmanagerates";
	private static final String strHospManageRate="hospmanagerates";

	//Exception Message Identifier
	private static final String strPlanpackageExp="planpackage";

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
			log.debug("Inside the doDefault method of TariffPlanPackageAction");
			setLinks(request);
			TableData planPackageData = null;
			String strPlanPackagePath="";
			//get the table data from session if exists
			if((request.getSession()).getAttribute("planPackageData") != null)
			{
				planPackageData = (TableData)(request.getSession()).getAttribute("planPackageData");
			}//end of if((request.getSession()).getAttribute("planPackageData") != null)
			else
			{
				planPackageData = new TableData();
			}//end of else
			//load the forward paths  based on the ActiveLink
			if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			{
				strPlanPackagePath=strHospPlanPackage;
			}//end of if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			else
			{
				strPlanPackagePath=strAdminPlanPackage;
			}//end of else
			//create new table data object
			planPackageData = new TableData();
			//create the required grid table
			planPackageData.createTableInfo("TariffPlanPackageTable",new ArrayList());
			request.getSession().setAttribute("planPackageData",planPackageData);
			//reset the form data
			((DynaActionForm)form).initialize(mapping);
			return this.getForward(strPlanPackagePath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPlanpackageExp));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
			log.debug("Inside the doDefault method of TariffPlanPackageAction");
			setLinks(request);
			TariffManager tariffObject=this.getTariffManagerObject();
			TableData planPackageData = null;
			String strPlanPackagePath="";
			Long lSelRevPlanSeqId=null;
			Long lPlanSeqId=null;
			Long lProdHospSeqId=null;
			String strPackageCode="";
			RevisionPlanVO revisePlanVO=null;
			if(request.getSession().getAttribute("RevisionPlanVO")!=null)
			{
				//get the object from session and assign to the variables
				revisePlanVO=(RevisionPlanVO)request.getSession().getAttribute("RevisionPlanVO");
				lSelRevPlanSeqId=revisePlanVO.getRevPlanSeqId();
				lPlanSeqId=revisePlanVO.getTariffPlanID();
				lProdHospSeqId=revisePlanVO.getProdHospSeqId();
			}//end of  if(request.getSession().getAttribute("RevisionPlanVO")!=null)
			//get the table data from session if exists
			if((request.getSession()).getAttribute("planPackageData") != null)
			{
				planPackageData = (TableData)(request.getSession()).getAttribute("planPackageData");
			}//end of if((request.getSession()).getAttribute("planPackageData") != null)
			else
			{
				planPackageData = new TableData();
			}//end of else
			//load the forward paths  based on the ActiveLink
			if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			{
				strPlanPackagePath=strHospPlanPackage;
			}//end of if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			else
			{
				strPlanPackagePath=strAdminPlanPackage;
			}//end of else
			//if the page number is clicked, display the appropriate page
			if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
			{
				planPackageData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.
																  getParameter("pageId"))));
				return (mapping.findForward(strPlanPackagePath));
			}//end of if(!TTKCommon.checkNull(request.getParameter("pageId")).equals(""))
			//clear the dynaform if visiting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			//create the required grid table based on whether packages available or not available
			DynaActionForm frmPackageList=(DynaActionForm)form;
			ArrayList alPlanPackageList=null;
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					planPackageData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.
																	  getParameter("pageId"))));
					return mapping.findForward(strPlanPackagePath);
				}///end of if(!strPageID.equals(""))
				else
				{
					planPackageData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					//modify the search data
					planPackageData.modifySearchData("sort");
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				strPackageCode=(String)frmPackageList.get("packageCode");
				if(strPackageCode.equals("NAV"))
				{
					planPackageData.createTableInfo("TariffPlanPkgNotAvblTable",null);
				}//end of if(strPackageCode.equals("NAV"))
				else
				{
					planPackageData.createTableInfo("TariffPlanPackageTable",null);
				}//end of else
				planPackageData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,
														  lSelRevPlanSeqId,lPlanSeqId,lProdHospSeqId));
				planPackageData.modifySearchData("search");
			}//end of else
			alPlanPackageList = tariffObject.getPackageList(planPackageData.getSearchData());
			planPackageData.setData(alPlanPackageList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableDataRevisePlan",planPackageData);
			//finally return to the grid screen
			return this.getForward(strPlanPackagePath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPlanpackageExp));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			log.debug("Inside the doBackward method of TariffPlanPackageAction");
			setLinks(request);
			TariffManager tariffObject=this.getTariffManagerObject();
			TableData planPackageData = null;
			String strPlanPackagePath="";
			//get the table data from session if exists
			if((request.getSession()).getAttribute("planPackageData") != null)
			{
				planPackageData = (TableData)(request.getSession()).getAttribute("planPackageData");
			}//end of if((request.getSession()).getAttribute("planPackageData") != null)
			else
			{
				planPackageData = new TableData();
			}//end of else
			//load the forward paths  based on the ActiveLink
			if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			{
				strPlanPackagePath=strHospPlanPackage;
			}//end of if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			else
			{
				strPlanPackagePath=strAdminPlanPackage;
			}//end of else
			//fetch the data from the data access layer and set the data to table object
			ArrayList alPlanPackageList = null;
			planPackageData.modifySearchData(strBackward);
			alPlanPackageList = tariffObject.getPackageList(planPackageData.getSearchData());
			planPackageData.setData(alPlanPackageList, strBackward);
			request.getSession().setAttribute("planPackageData",planPackageData);
			return this.getForward(strPlanPackagePath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPlanpackageExp));
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
			log.debug("Inside the doForward method of TariffPlanPackageAction");
			setLinks(request);
			TariffManager tariffObject=this.getTariffManagerObject();
			TableData planPackageData = null;
			String strPlanPackagePath="";
			//get the table data from session if exists
			if((request.getSession()).getAttribute("planPackageData") != null)
			{
				planPackageData = (TableData)(request.getSession()).getAttribute("planPackageData");
			}//end of if((request.getSession()).getAttribute("planPackageData") != null)
			else
			{
				planPackageData = new TableData();
			}//end of else
			//load the forward paths  based on the ActiveLink
			if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			{
				strPlanPackagePath=strHospPlanPackage;
			}//end of if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			else
			{
				strPlanPackagePath=strAdminPlanPackage;
			}//end of else
			//fetch the data from the data access layer and set the data to table object
			ArrayList alPlanPackageList = null;
			planPackageData.modifySearchData(strForward);
			alPlanPackageList = tariffObject.getPackageList(planPackageData.getSearchData());
			planPackageData.setData(alPlanPackageList, strForward);
			request.getSession().setAttribute("planPackageData",planPackageData);
			return this.getForward(strPlanPackagePath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPlanpackageExp));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
	/**
	 * This method is used to change the status.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														  HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doChangeStatus method of TariffPlanPackageAction");
			setLinks(request);
			TariffManager tariffObject=this.getTariffManagerObject();
			TableData planPackageData = null;
			if((request.getSession()).getAttribute("planPackageData") != null)
			{
				planPackageData = (TableData)(request.getSession()).getAttribute("planPackageData");
			}//end of if((request.getSession()).getAttribute("planPackageData") != null)
			else
			{
				planPackageData = new TableData();
			}//end of else
			String strPlanPackagePath="";
			Long lSelRevPlanSeqId=null;
			/*Long lPlanSeqId=null;
			Long lProdHospSeqId=null;
			String strPackageCode="";*/
			RevisionPlanVO revisePlanVO=null;
			if(request.getSession().getAttribute("RevisionPlanVO")!=null)
			{
				//get the object from session and assign to the variables
				revisePlanVO=(RevisionPlanVO)request.getSession().getAttribute("RevisionPlanVO");
				lSelRevPlanSeqId=revisePlanVO.getRevPlanSeqId();
				//lPlanSeqId=revisePlanVO.getTariffPlanID();
				//lProdHospSeqId=revisePlanVO.getProdHospSeqId();
			}//end of  if(request.getSession().getAttribute("RevisionPlanVO")!=null)
			if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			{
				strPlanPackagePath=strHospPlanPackage;
			}//end of if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			else
			{
				strPlanPackagePath=strAdminPlanPackage;
			}//end of else
			DynaActionForm frmPackageList=(DynaActionForm)form;
            ArrayList<Object> alPackageList=new ArrayList<Object>();
            String strAvailabality="";
            //prepare the parameters required
            String strStatus=(String)frmPackageList.get("packageCode");
            if(strStatus.equals("AVA"))
            {
                strAvailabality="NAV";
            }//end of if(strStatus.equals("AVA"))
            else
            {
                strAvailabality="AVA";
            }//end of else
            String strPackageId=this.populatePackageIds(request,planPackageData);
            //sending the parameters for current revision
            alPackageList.add(lSelRevPlanSeqId);
            alPackageList.add("|"+strPackageId+"|");
            alPackageList.add(TTKCommon.getUserSeqId(request));
            int iCount=tariffObject.updateAvailabilityStatus(alPackageList,strAvailabality);
            //refresh the grid data
            ArrayList alPlanPackageList = null;
            //fetch the records from the criteria found in session
            alPlanPackageList = tariffObject.getPackageList(planPackageData.getSearchData());
            //fetch the data from previous set of rowcounts, if all records status is changed for the 
            	//current set of rowcounts  
            if(alPlanPackageList.size() == 0 || iCount == planPackageData.getData().size())
            {
                int iStartRowCount = 0;
                planPackageData.modifySearchData("Delete");
                iStartRowCount = Integer.parseInt((String)planPackageData.getSearchData().
						   get(planPackageData.getSearchData().size()-2));
                if(iStartRowCount > 0)
                {
                	alPlanPackageList = tariffObject.getPackageList(planPackageData.getSearchData());
                }//end of if(iStartRowCount > 0)
            }//end if else if(strMode.equals("ChangeStatus"))
            planPackageData.setData(alPlanPackageList,"Delete");
            request.getSession().setAttribute("planPackageData",planPackageData);
			return this.getForward(strPlanPackagePath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPlanpackageExp));
		}//end of catch(Exception exp)
	}//end of doChangeStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doViewPlanPackage(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															 HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doViewPlanPackage method of TariffPlanPackageAction");
			setLinks(request);
			TariffManager tariffObject=this.getTariffManagerObject();
			TableData planPackageData = null;
			String strManageRatePath="";
			//get the table data from session if exists
			if((request.getSession()).getAttribute("planPackageData") != null)
			{
				planPackageData = (TableData)(request.getSession()).getAttribute("planPackageData");
			}//end of if((request.getSession()).getAttribute("planPackageData") != null)
			else
			{
				planPackageData = new TableData();
			}//end of else

			//load the forward paths  based on the ActiveLink
			if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			{
				strManageRatePath=strHospManageRate;
			}//end of if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			else
			{
				strManageRatePath=strAdminManageRate;
			}//end of else
			DynaActionForm frmPackageDetail = (DynaActionForm)form;
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				PlanPackageVO planPackageVO=(PlanPackageVO)planPackageData.getRowInfo(Integer.parseInt(
															(String)(frmPackageDetail).get("rownum")));
				request.getSession().setAttribute("planPackageVO",planPackageVO);
				if(planPackageVO.getType().equals("Package"))
				{
					ArrayList alRates=tariffObject.getRates(planPackageVO.getRevPlanSeqId(),
													planPackageVO.getPkgSeqId(),"PKG",null);
					//request.getSession().setAttribute("alRates",alRates);
					request.setAttribute("alRates",alRates);
					frmPackageDetail.set("package","PKG");
				}//end of if(planPackageVO.getType().equals("Package"))
				else
				{
					request.removeAttribute("alRates");
					//setting the form for non-package
					frmPackageDetail.set("medicalPackageYn",planPackageVO.getMedicalPkgYN());
					frmPackageDetail.set("wardCode","Any");
					frmPackageDetail.set("package","NPK");
				}//end of if(planPackageVO.getType().equals("Package"))
				//setting the common form elements for bot the package types
				frmPackageDetail.set("pkgSeqId",planPackageVO.getPkgSeqId().toString());
				frmPackageDetail.set("generalTypeid",planPackageVO.getType());
				frmPackageDetail.set("revisePlanSeqId",planPackageVO.getRevPlanSeqId());
			}//end of row number
			return this.getForward(strManageRatePath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPlanpackageExp));
		}//end of catch(Exception exp)
	}//end of doViewPlanPackage(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * This method is used to display the wardchanges.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doWardChanged(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														 HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doWardChanged method of TariffPlanPackageAction");
			setLinks(request);
			TariffManager tariffObject=this.getTariffManagerObject();
			Long lRevisePlanSeqId=null;
			Long lPkgSeqId=null;
			String strManageRatePath="";
			//load the forward paths  based on the ActiveLink
			if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			{
				strManageRatePath=strHospManageRate;
			}//end of if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			else
			{
				strManageRatePath=strAdminManageRate;
			}//end of else
			DynaActionForm frmPackageRates = (DynaActionForm)form;
			String strWardTypeId=(String)frmPackageRates.getString("wardCode");
			lRevisePlanSeqId=(Long)frmPackageRates.get("revisePlanSeqId");
			lPkgSeqId=TTKCommon.getLong((String)frmPackageRates.get("pkgSeqId"));
			String strPackage=(String)frmPackageRates.get("package");
			//Calling business layer to get the rate of selected ward for nonpackage
			ArrayList alRates=tariffObject.getRates(lRevisePlanSeqId,lPkgSeqId,"NPK",strWardTypeId);
			if(alRates!=null)
			{
				//request.getSession().setAttribute("alRates",alRates);
				request.setAttribute("alRates",alRates);
			}//end of if(alRates!=null)
			request.setAttribute("package",strPackage);
			return this.getForward(strManageRatePath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPlanpackageExp));
		}//end of catch(Exception exp)
	}//end of doWardChanged(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
												  HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSave method of TariffPlanPackageAction");
			setLinks(request);
			TariffManager tariffObject=this.getTariffManagerObject();
			RevisionPlanVO revisePlanVO=null;
			Long lRevisePlanSeqId=null;
			Long lSelRevPlanSeqId=null;
			Long lPkgSeqId=null;
			String strDefaultPlanYn="";
			String strGeneralCode="";
			String strManageRatePath="";
			//load the forward paths  based on the ActiveLink
			if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			{
				strManageRatePath=strHospManageRate;
			}//end of if(TTKCommon.getActiveLink(request).equals("Empanelment"))
			else
			{
				strManageRatePath=strAdminManageRate;
			}//end of else
			if(request.getSession().getAttribute("RevisionPlanVO")!=null)
			{
				//get the object from session and assign to the variables
				revisePlanVO=(RevisionPlanVO)request.getSession().getAttribute("RevisionPlanVO");
				lSelRevPlanSeqId=revisePlanVO.getRevPlanSeqId();
				strDefaultPlanYn=revisePlanVO.getDefaultPlanYn();
			}//end of  if(request.getSession().getAttribute("RevisionPlanVO")!=null)
			if(request.getSession().getAttribute("insuranceVO")!=null)
			{
				InsuranceVO insurenceVO=(InsuranceVO)request.getSession().getAttribute("insuranceVO");
				//get the agreed type or offered type
				strGeneralCode=insurenceVO.getGenTypeID();
			}//end of if(request.getSession().getAttribute("insuranceVO")!=null)
			DynaActionForm frmPackageRates = (DynaActionForm)form;
			String strWardTypeId=(String)frmPackageRates.get("wardCode");
			String strPackage=(String)frmPackageRates.get("package");
			request.setAttribute("strPackage",strPackage);
			RateVO rateVO=new RateVO();
			//loading the VO
			rateVO.setPkgCostSeqIdList((java.lang.Long[])frmPackageRates.get("pkgCostSeqId"));
			rateVO.setPkgDetailSeqIdList((java.lang.Long[])frmPackageRates.get("pkgDetailSeqId"));
			rateVO.setPkgSeqId(TTKCommon.getLong((String)frmPackageRates.get("pkgSeqId")));
			rateVO.setRevPlanSeqId((Long)frmPackageRates.get("revisePlanSeqId"));
			rateVO.setSelRevPlanSeqId(lSelRevPlanSeqId);
			rateVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			//setting package as Available as it can be modified only if it is available
			rateVO.setAvblGnrlTypeId("AVA");
			rateVO.setRates((String[])frmPackageRates.get("rate"));
			//check for the Empanel module and depending on that load the discount
			if(TTKCommon.getActiveSubLink(request).equals("Hospital") && strGeneralCode.equals("ART"))
			{
				rateVO.setDiscountList((String[])frmPackageRates.get("discount"));
			}//end of if(TTKCommon.getActiveSubLink(request).equals("Hospital") && strGeneralCode.equals("ART"))
			//get the RevisePlan seq ID and pkg seq id from the form
			lRevisePlanSeqId=(Long)frmPackageRates.get("revisePlanSeqId");
			lPkgSeqId=TTKCommon.getLong((String)frmPackageRates.get("pkgSeqId"));
			//calling addUpdate rates through the DAO
			int iResult=tariffObject.addUpdateRates(rateVO);
			if(iResult>0)
			{
				request.setAttribute("updated","message.saved");
				ArrayList alRates=new ArrayList();
				//refresh the data after the records are saved
				//if default plan pass the reviseplanseqId of the package selected
				//else passs the selected revisied plan seq id from the session that you got when coming to page
				if(strDefaultPlanYn.equals("Y"))
				{
					if(strPackage.equals("NPK"))
					{
						alRates=tariffObject.getRates(lRevisePlanSeqId,lPkgSeqId,strPackage,strWardTypeId);
					}//end of if(strPackage.equals("NPK"))
					else
					{
						alRates=tariffObject.getRates(lRevisePlanSeqId,lPkgSeqId,strPackage,null);
					}//end of else
				}//end of if(strDefaultPlanYn.equals("Y"))
				else
				{
					if(strPackage.equals("NPK"))
					{
						alRates=tariffObject.getRates(lSelRevPlanSeqId,lPkgSeqId,strPackage,strWardTypeId);
					}//end of if(strPackage.equals("NPK"))
					else
					{
						alRates=tariffObject.getRates(lSelRevPlanSeqId,lPkgSeqId,strPackage,null);
					}//end of else

					//setting the latest Revision Seq Id
					frmPackageRates.set("revisePlanSeqId",lSelRevPlanSeqId);
				}//ens of else
				//request.getSession().setAttribute("alRates",alRates);
				request.setAttribute("alRates",alRates);
			}//end of if(iResult>0)
			return this.getForward(strManageRatePath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPlanpackageExp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside the doClose method of TariffPlanPackageAction");
			setLinks(request);
			RevisionPlanVO revisePlanVO=null;
			if(request.getSession().getAttribute("RevisionPlanVO")!=null)
			{
				//get the object from session and assign to the variables
				revisePlanVO=(RevisionPlanVO)request.getSession().getAttribute("RevisionPlanVO");
			}//end of  if(request.getSession().getAttribute("RevisionPlanVO")!=null)
			String strPath=TTKCommon.getActiveLink(request)+"."+TTKCommon.getActiveSubLink(
											  request)+"."+TTKCommon.getActiveTab(request);

            if(TTKCommon.getActiveLink(request).equals("Administration"))
            {
                if(revisePlanVO.getDefaultPlanYn().equals("Y"))
                {
                    request.setAttribute("child","");
                }//end of if(revisePlanVO.getDefaultPlanYn().equals("Y"))
                else
                {
                    request.setAttribute("child","Revision History");
                }//end of else
            }//end of if(TTKCommon.getActiveLink(request).equals("Administration"))
            else
            {
                request.setAttribute("child","RevisePlan");
            }//end of else
            
            if(strPath.equals("Administration.Tariff Plans.Plans")&& revisePlanVO.getDefaultPlanYn().equals("Y"))
			{
				//for default plan in Admin module
				return mapping.findForward(strPath+".Default");
			}//end of if(strPath.equals("Administration.Tariff Plans.Plans"))
			return (mapping.findForward(strPath));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPlanpackageExp));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the TariffManager session object for invoking methods on it.
	 * @return TariffManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private TariffManager getTariffManagerObject() throws TTKException
	{
		TariffManager tariffObject = null;
		try
		{
			if(tariffObject == null)
			{
				InitialContext ctx = new InitialContext();
				tariffObject = (TariffManager) ctx.lookup("java:global/TTKServices/business.ejb3/TariffManagerBean!com.ttk.business.administration.TariffManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "planpackage");
		}//end of catch
		return tariffObject;
	}//end getTariffManagerObject()

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPlanPackage formbean which contains the search fields
	 * @param request HtttpServletRequest
	 * @param lRevisePlanSeqId Revise plan Seq Id
	 * @param lPlanSeqId Plan Seq Id
	 * @param lProdHospSeqId Product Hospital Seq Id
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmPlanPackage,HttpServletRequest request,
											Long lRevisePlanSeqId,Long lPlanSeqId,Long lProdHospSeqId)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add((String)frmPlanPackage.get("sModified"));
		alSearchParams.add(lPlanSeqId);
		alSearchParams.add(lProdHospSeqId);
		alSearchParams.add(lRevisePlanSeqId);
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPlanPackage.get("sPackageName")));
		alSearchParams.add((String)frmPlanPackage.get("generalCodePlan"));
		alSearchParams.add((String)frmPlanPackage.get("packageCode"));
		request.getSession().setAttribute("searchparam",alSearchParams);
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmTariffPlan)

	/**
	 * Returns a string which contains the pipe separated package sequence id's whose status will be changed
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the pipe separated package sequence id's whose status will be changed
	 */
	private String populatePackageIds(HttpServletRequest request, TableData tableData)
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			//loop through to populate delete sequence id's and get the value from session for the
			//matching check box value
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append(String.valueOf(((PlanPackageVO)tableData.getData().
											get(Integer.parseInt(strChk[i]))).getPkgSeqId().intValue()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((PlanPackageVO)tableData.
												  getData().get(Integer.parseInt(strChk[i]))).getPkgSeqId().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
		}//end of if(strChk!=null&&strChk.length!=0)
		return sbfDeleteId.toString();
	}//end of populatePackageIds(HttpServletRequest request, TableData tableData)

}//end of TariffPlanPackageAction
