/**
 * @ (#) TarrifItemAction.java Sep 29, 2005
 * Project       : TTK HealthCare Services
 * File          : TarrifItemAction.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : Sep 29, 2005
 *
 * @author       : Srikanth H M
 * Modified by   : Pradeep R
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.administration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.StringTokenizer;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.TariffManager;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.administration.ProcedureDetailVO;
import com.ttk.dto.administration.TariffItemVO;
import com.ttk.dto.administration.TariffPlanVO;
import com.ttk.dto.administration.TariffUploadVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.empanelment.HospitalDetailVO;

import formdef.plugin.util.FormUtils;

/**
* This class is used for Searching the Tariff Item List.
* This also provides  updation of Tariff Item.
*/
public class TarrifItemAction extends TTKAction
{
	
	//for setting modes
	private static Logger log = Logger.getLogger( TarrifItemAction.class );
	private static final String strForward ="Forward";
	private static final String strBackward ="Backward";
	
	//forwards links
	private static final String strTariffitem		=	"tariffitem";
	private static final String strSearchTariff		=	"searchtariff";
	private static final String strEdittariff		=	"edittariff";
	private static final String strAssociate		=	"associate";
	
	//Exception Message Identifier
	private static final String strTariffExp		=	"tariffitem";
	private static final String strTariffSearch		=	"searchtariff";
	private static final String strEditTariffItem	=	"editTariff";
	private static final String	strUploadTariff		=	"uploadtariff";
	private static final String strUpdatetariff		=	"updateTariff";
	private static final String strShowrTariffs		=	"showTariffs";
	private static final String strShowServiceTypes	=	"showServiceTypes";
	
	//Emapanelment Tariffs
	private static final String strTariffSearchEmpanelment		=	"searchtariffEmpanelment";
	private static final String strEditTariffItemEmpanelment	=	"editTariffEmpanelment";
	private static final String strUpdatetariffEmpanelment		=	"updateTariffEmpanelment";
	private static final String strUploadTariffEmpanelment		=	"uploadtariffEmpanelment";
	private static final String strShowrTariffsEmpanelment		=	"showTariffsEmpanelment";
	private static final String strShowServiceTypesEmpanelment	=	"showServiceTypesEmpanelment";
	private static final String strDownloadTariffEmpanelment	=	"downloadTariffEmpanelment";
	
	
	
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
			log.debug("Inside the doDefault method of TarifItemAction");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("TariffSearchTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			return this.getForward(strSearchTariff, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	
	public ActionForward doShowUploadTariff(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
		log.info("Inside the doShowUploadTariff method of TarifItemAction");
		setLinks(request);
		((DynaActionForm)form).initialize(mapping);//reset the form data
		if("Administration".equals(TTKCommon.getActiveLink(request)))
			return this.getForward(strUploadTariff, mapping, request);
		else{
			TariffManager tariffItemObject=this.getTariffItemObject();	
			String allnetwork = tariffItemObject.getAllNetworkdata();
			request.getSession().setAttribute("allnetwork", allnetwork);
			return this.getForward(strUploadTariffEmpanelment, mapping, request);
		}
		}//end of try
		catch(TTKException expTTK)
		{
		return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
}//end of doShowUploadTariff(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
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
			log.debug("Inside the doSearch method of TarifItemAction");
			setLinks(request);
			TariffManager tariffItemObject=this.getTariffItemObject();						
			TableData tableData = TTKCommon.getTableData(request);
			//clear the dynaform if visiting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{    
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return this.getForward(strTariffitem, mapping, request);	
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("TariffTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form));
				tableData.modifySearchData("search");
			}//end of else   
			//fetch the data from the data access layer and set the data to table object
			ArrayList alTariffItem= tariffItemObject.getTariffItemList(tableData.getSearchData(),"Administration");
			tableData.setData(alTariffItem, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strTariffitem, mapping, request);	
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	/**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doSearchTariff(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			try{ 
			log.debug("Inside the doSearchTariff method of TarifItemAction");
			setLinks(request);
			TariffManager tariffItemObject=this.getTariffItemObject();						
			TableData tableData = TTKCommon.getTableData(request);
			//clear the dynaform if visiting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
			((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
			if(!strPageID.equals(""))
			{    
			tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
			//return this.getForward(strTariffSearch, mapping, request);
			if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
				return this.getForward(strTariffSearch, mapping, request);
			else
				return this.getForward(strTariffSearchEmpanelment, mapping, request);
			}///end of if(!strPageID.equals(""))
			else
			{
			tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
			tableData.modifySearchData("sort");//modify the search data
			}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
			//create the required grid table
			tableData.createTableInfo("TariffSearchTable",null);
			tableData.setSearchData(this.populateTariffSearchCriteria((DynaActionForm)form,request));
			tableData.modifySearchData("search");
			}//end of else   
			
			//fetch the data from the data access layer and set the data to table object
			ArrayList alTariffItem= tariffItemObject.getTariffSearchList(tableData.getSearchData(),"Administration");
			tableData.setData(alTariffItem, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
				return this.getForward(strTariffSearch, mapping, request);
			else{
				DynaActionForm frmSearchTariffItem	=	(DynaActionForm)form;
				frmSearchTariffItem.set("providerCodeSearch", request.getSession().getAttribute("hospSeqIdforTariff"));
				return this.getForward(strTariffSearchEmpanelment, mapping, request);
			}
			
			}//end of try
			catch(TTKException expTTK)
			{
			return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
			}//end of catch(Exception exp)
}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	/**
     * This method is used to get the details of the selected record from web-board.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	log.info("Inside doChangeWebBoard method of TariffItemAction");
    	//ChangeWebBoard method will call doView() method internally.
    	return doEditTariff(mapping,form,request,response);
    	
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
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
			log.debug("Inside the doBackward method of TarifItemAction");
			setLinks(request);
			TariffManager tariffItemObject=this.getTariffItemObject();	
			TableData tableData = TTKCommon.getTableData(request);
			//modify the search data
			tableData.modifySearchData(strBackward);
			//ArrayList alTariffItem= tariffItemObject.getTariffItemList(tableData.getSearchData(),"Administration");
			ArrayList alTariffItem= tariffItemObject.getTariffSearchList(tableData.getSearchData(),"Administration");

			tableData.setData(alTariffItem, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			//return this.getForward(strTariffitem, mapping, request);
			if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
				return this.getForward(strShowrTariffs, mapping, request);
			else{
				DynaActionForm frmSearchTariffItem	=	(DynaActionForm)form;
				frmSearchTariffItem.set("providerCodeSearch", request.getSession().getAttribute("hospSeqIdforTariff"));
				return this.getForward(strShowrTariffsEmpanelment, mapping, request);
			}
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
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
			log.info("Inside the doForward method of TarifItemAction");
			setLinks(request);
			TariffManager tariffItemObject=this.getTariffItemObject();	
			TableData tableData = TTKCommon.getTableData(request);
			//modify the search data
			tableData.modifySearchData(strForward);
			//ArrayList alTariffItem= tariffItemObject.getTariffItemList(tableData.getSearchData(),"Administration");
			ArrayList alTariffItem= tariffItemObject.getTariffSearchList(tableData.getSearchData(),"Administration");

			tableData.setData(alTariffItem,strForward );//set the table data
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			//return this.getForward(strTariffitem, mapping, request);
			if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
				return this.getForward(strShowrTariffs, mapping, request);
			else{
				DynaActionForm frmSearchTariffItem	=	(DynaActionForm)form;
				frmSearchTariffItem.set("providerCodeSearch", request.getSession().getAttribute("hospSeqIdforTariff"));
				return this.getForward(strShowrTariffsEmpanelment, mapping, request);
			}
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	
	
	
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
	public ActionForward doBackwardSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doBackwardSearch method of TarifItemAction");
			setLinks(request);
			TariffManager tariffItemObject=this.getTariffItemObject();	
			TableData tableData = TTKCommon.getTableData(request);
			//modify the search data
			tableData.modifySearchData(strBackward);
			//ArrayList alTariffItem= tariffItemObject.getTariffItemList(tableData.getSearchData(),"Administration");
			ArrayList alTariffItem= tariffItemObject.getTariffSearchList(tableData.getSearchData(),"Administration");

			tableData.setData(alTariffItem, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			//return this.getForward(strTariffitem, mapping, request);
			if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
				return this.getForward(strTariffSearch, mapping, request);
			else{
				DynaActionForm frmSearchTariffItem	=	(DynaActionForm)form;
				frmSearchTariffItem.set("providerCodeSearch", request.getSession().getAttribute("hospSeqIdforTariff"));
				return this.getForward(strTariffSearchEmpanelment, mapping, request);
			}
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
	}//end of doBackwardSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doForwardSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												HttpServletResponse response) throws Exception{
		try{
			log.info("Inside the doForwardSearch method of TarifItemAction");
			setLinks(request);
			TariffManager tariffItemObject=this.getTariffItemObject();	
			TableData tableData = TTKCommon.getTableData(request);
			//modify the search data
			tableData.modifySearchData(strForward);
			//ArrayList alTariffItem= tariffItemObject.getTariffItemList(tableData.getSearchData(),"Administration");
			ArrayList alTariffItem= tariffItemObject.getTariffSearchList(tableData.getSearchData(),"Administration");

			tableData.setData(alTariffItem,strForward );//set the table data
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			//return this.getForward(strTariffitem, mapping, request);
			if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
				return this.getForward(strTariffSearch, mapping, request);
			else{
				DynaActionForm frmSearchTariffItem	=	(DynaActionForm)form;
				frmSearchTariffItem.set("providerCodeSearch", request.getSession().getAttribute("hospSeqIdforTariff"));
				return this.getForward(strTariffSearchEmpanelment, mapping, request);
			}
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
	}//end of doForwardSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
	public ActionForward doViewTarifitem(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doViewTarifitem method of TarifItemAction");
			setLinks(request);
			TariffManager tariffItemObject=this.getTariffItemObject();	
			TableData tableData = TTKCommon.getTableData(request);
			DynaActionForm frmTariffItem=(DynaActionForm)form;
			TariffItemVO tariffItemVO=new TariffItemVO();
			String strRowNum=(String)frmTariffItem.get("rownum");
			//Edit flow
			if(!((String)(frmTariffItem).get("rownum")).equals("")) 
			{
				tariffItemVO =(TariffItemVO)tableData.getRowInfo(Integer.parseInt((String)(frmTariffItem).
																							get("rownum")));
				tariffItemVO=tariffItemObject.getTariffItemDetail(tariffItemVO.getTariffItemId());
				frmTariffItem = (DynaActionForm)FormUtils.setFormValues("frmTariffItem",tariffItemVO,
																				this,mapping,request);
				frmTariffItem.set("caption","Edit");
				frmTariffItem.set("asscCodes",tariffItemVO.getAssociateProcedureList());	
				frmTariffItem.set("rownum",strRowNum);
			}//end of if(!((String)(formTariffItem).get("rownum")).equals(""))
			request.getSession().setAttribute("frmTariffItem",frmTariffItem);
			return this.getForward(strEdittariff, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
	}//end of doViewTarifitem(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			log.debug("Inside the doAdd method of TarifItemAction");
			setLinks(request);
			DynaActionForm frmTariffItem=(DynaActionForm)form;
			frmTariffItem.initialize(mapping);
			frmTariffItem.set("caption","Add");
			request.getSession().setAttribute("frmTariffItem",frmTariffItem);
			return this.getForward(strEdittariff, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
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
			log.debug("Inside the doSave method of TarifItemAction");
			setLinks(request);
			TariffManager tariffItemObject=this.getTariffItemObject();
			DynaActionForm frmTariffItem = (DynaActionForm)form;
			String strRowNum=(String)frmTariffItem.get("rownum");
			TariffItemVO tariffItemVO=new TariffItemVO();
			tariffItemVO=(TariffItemVO)FormUtils.getFormValues(frmTariffItem,this,mapping,request);
			//selected procedure code is not set to vo if it belongs to NON-Package 
			//and the chekc box value is  checked
			if(!((frmTariffItem.getString("tariffItemType").equals("NPK"))&&frmTariffItem.
												getString("medicalPackageYn").equals("Y")))				
			{
				tariffItemVO.setAssociateProcedure(frmTariffItem.getString("selectedProcedureCode"));
			}//end if(!((formTariffItem.getString("tariffItemType").equals("NPK"))
				//&&formTariffItem.getString("medicalPackageYn").equals("Y")))
			tariffItemVO.setDeleteProcedure(frmTariffItem.getString("deleteSeqId"));	
			tariffItemVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			// if medicalPackageYn checkbox in not selected
			if(request.getParameter("medicalPackageYn")==null)
			{
				tariffItemVO.setMedicalPackageYn("N");
			}//end of if(request.getParameter("medicalPackageYn")==null)
			long lngCount=tariffItemObject.addUpdateTariffItem(tariffItemVO);
			if(lngCount>0)
			{
				//edit flow
				if(!(TTKCommon.checkNull((String)frmTariffItem.get("rownum")).equals("")))
				{  
					request.setAttribute("updated","message.savedSuccessfully");
					//requering after saving the information
					tariffItemVO=tariffItemObject.getTariffItemDetail(lngCount);
					frmTariffItem = (DynaActionForm)FormUtils.setFormValues("frmTariffItem",
														tariffItemVO,this,mapping,request);
					frmTariffItem.set("caption","Edit");
					frmTariffItem.set("asscCodes",tariffItemVO.getAssociateProcedureList());	
					frmTariffItem.set("rownum",strRowNum);
				}//end of if(!(TTKCommon.checkNull((String)formAddPED.get("rownum")).equals("")))
				else
				{
					frmTariffItem.initialize(mapping);
					frmTariffItem.set("caption","Add");
					request.setAttribute("updated","message.addedSuccessfully");
				}//end of else
			}//end of if(lngCount>0)
			request.getSession().setAttribute("frmTariffItem",frmTariffItem);
			return this.getForward(strEdittariff, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to change  the type in the  details screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doTypeChanged(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doTypeChanged method of TarifItemAction");
			setLinks(request);
			DynaActionForm frmTariffForm = (DynaActionForm)form;
			String strTypeChanged=TTKCommon.checkNull(request.getParameter("generalCodePlan"));
			if(strTypeChanged!=null)
			{
				frmTariffForm.set("generalCodePlan",strTypeChanged); 
			}//end of if(strTypeChanged!=null)
			return this.getForward(strEdittariff, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
	}//end of doTypeChanged(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doReset method of TarifItemAction");
			setLinks(request);
			TariffManager tariffItemObject=this.getTariffItemObject();
			DynaActionForm frmTariffItem=(DynaActionForm)form;
			String strRowNum=frmTariffItem.getString("rownum");
			TariffItemVO tariffItemVO =null;
			if(!frmTariffItem.get("tariffItemId").equals(""))
			{
				tariffItemVO =tariffItemVO=tariffItemObject.getTariffItemDetail(TTKCommon.getLong(
														frmTariffItem.getString("tariffItemId")));
				frmTariffItem = (DynaActionForm)FormUtils.setFormValues("frmTariffItem",tariffItemVO,
																				this,mapping,request);
				frmTariffItem.set("caption","Edit");
				frmTariffItem.set("asscCodes",tariffItemVO.getAssociateProcedureList());	
				request.getSession().setAttribute("frmTariffItem",frmTariffItem);
			}//end of if(!frmTariffForm.get("pkgSeqId").equals(""))
			else
			{
				frmTariffItem.initialize(mapping);
				frmTariffItem.set("caption","Add");
			}//end of else 			
			frmTariffItem.set("rownum",strRowNum);
			request.getSession().setAttribute("frmTariffItem",frmTariffItem);
			return this.getForward(strEdittariff, mapping, request); 
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to associate the  selected records .
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAssociate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													   HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doAssociate method of TarifItemAction");
			setLinks(request);
			DynaActionForm frmTariff = (DynaActionForm)form;	
			modifyProcedureCode(request,frmTariff);
			return mapping.findForward(strAssociate);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
	}//end of doAssociate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
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
			log.debug("Inside the doClose method of TarifItemAction");
			setLinks(request);
			TariffManager tariffItemObject=this.getTariffItemObject();
			TableData tableData = TTKCommon.getTableData(request);
			if(tableData.getSearchData().size()>1)	
			{
				ArrayList alTariffItem= tariffItemObject.getTariffItemList(tableData.getSearchData(),"Administration");
				tableData.setData(alTariffItem, "Cancel");
				request.getSession().setAttribute("tableData",tableData);
			}//end of if(tableData.getSearchData().size()>1)	
			return this.getForward(strTariffitem, mapping, request);	
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the ArrayList which contains the populated search criteria elements.
	 * @param frmSearchTariffItem DynaActionForm will contains the values of corresponding fields.
	 * @param request HttpServletRequest object which contains the search parameter that is built.
	 * @return alSearchParams ArrayList.
	 */	
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSearchTariffItem)
	{	
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(new SearchCriteria("NAME", TTKCommon.replaceSingleQots ((String)
														frmSearchTariffItem.get("sName"))));
		alSearchParams.add(new SearchCriteria("TPA_HOSP_TARIFF_ITEM.GENERAL_TYPE_ID", (String)
										frmSearchTariffItem.get("generalCodePlan"),"equals"));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmSearchTariffItem,HttpServletRequest request)
	
	/**
	 * Returns the ArrayList which contains the populated search criteria elements.
	 * @param frmSearchTariffItem DynaActionForm will contains the values of corresponding fields.
	 * @param request HttpServletRequest object which contains the search parameter that is built.
	 * @return alSearchParams ArrayList.
	 */	
	private ArrayList<Object> populateTariffSearchCriteria(DynaActionForm frmSearchTariffItem, HttpServletRequest request)throws Exception
	{	
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchTariffItem.getString("tariffTypeSearch")));//0
		else
			alSearchParams.add("HOSP");//0
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchTariffItem.getString("payerCodeSearch")));//1
		if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
			alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchTariffItem.getString("providerCodeSearch")));//2
		else
			alSearchParams.add(request.getSession().getAttribute("hospSeqIdforTariff"));//2
		
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchTariffItem.getString("networkTypeSearch")));//3
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchTariffItem.getString("corpCodeSearch")));//4
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchTariffItem.getString("priceRefNo")));//5
		alSearchParams.add(TTKCommon.getUserSeqId(request));//6
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchTariffItem.getString("activityCode")));//7
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchTariffItem.getString("serviceName")));//8
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchTariffItem.getString("internalCode")));//9
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchTariffItem.getString("enddate")));//10
	    
        request.getSession().setAttribute("setTariffType", TTKCommon.replaceSingleQots((String)frmSearchTariffItem.getString("tariffTypeSearch")));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmSearchTariffItem,HttpServletRequest request)
	
	
	/**
	 * Returns the TariffManager  session object for invoking methods on it.
	 * @return TariffManager session object which can be used for method invokation 
	 * @exception throws TTKException  
	 */
	private TariffManager getTariffItemObject() throws TTKException
	{
		TariffManager tariffItem = null;
		try 
		{
			if(tariffItem == null)
			{
				InitialContext ctx = new InitialContext();
				tariffItem = (TariffManager) ctx.lookup("java:global/TTKServices/business.ejb3/TariffManagerBean!com.ttk.business.administration.TariffManager");
			}//end of if(user == null)
		}//end of try 
		catch(Exception exp) 
		{
			throw new TTKException(exp, "tariffItem");
		}//end of catch(Exception exp) 
		return tariffItem;
	}//end getTariffItemObject()
	
	/**
	 *  Remove the deleted procedure code from session object
	 *  @param request HttpServletRequest object which contains information about the selected procedure code
	 *  @param alNewAsscCode ArrayList which contains the new associated procedure code     
	 */  
	private void modifyProcedureCode(HttpServletRequest request,DynaActionForm frmTariff)
	{
		ArrayList alasscProcedure=null;   
		alasscProcedure=(ArrayList)frmTariff.get("asscCodes");
		String strDeleteSeqId=request.getParameter("deleteSeqId");
		StringTokenizer stProcedure=null;
		String strKey="";
		ProcedureDetailVO procDetailVO=null;  
		if(strDeleteSeqId!=null&&!strDeleteSeqId.equals(""))
		{	
			stProcedure=new StringTokenizer(strDeleteSeqId,"|");
			while(stProcedure.hasMoreTokens())
			{
				strKey = stProcedure.nextToken();
				if(alasscProcedure != null && alasscProcedure.size() > 0)
					for(int i=(alasscProcedure.size()-1); i >= 0; i--)	
					{	
						procDetailVO=(ProcedureDetailVO)alasscProcedure.get(i);
						if((procDetailVO.getProcedureID().toString()).equals(strKey))                
						{
							alasscProcedure.remove(i);
						}//end of if((procDetailVO.getProcedureID().toString()).equals(strKey)) 
					}//end of for(int i=(alasscProcedure.size()-1); i >= 0; i--)	 	
			}//end of while(stProcedure.hasMoreTokens()) 	    		
			frmTariff.set("asscCodes",alasscProcedure);
		}//end of if(strDeleteSeqId!=null&&!strDeleteSeqId.equals(""))
	}//end of modifyProcedureCode(HttpServletRequest request,DynaActionForm frmTariffForm)

	
	/**
	 * To show the Selected tariff Edit the Selected Tariff
	 */
	public ActionForward doEditTariff(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		
    		setLinks(request);
    		log.debug("Inside the doEditTariff method of TariffItemAction");
    		String strCaption="";
			TariffUploadVO tariffUploadVO	=	null;
			TariffManager tariffItemObject=this.getTariffItemObject();
			
			TableData tableData = TTKCommon.getTableData(request);
            if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
            	((DynaActionForm)form).initialize(mapping);//reset the form data
            }//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))

            DynaActionForm generalForm = (DynaActionForm)form;
            if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
            	tariffUploadVO = (TariffUploadVO)tableData.getRowInfo(Integer.parseInt((String)(generalForm).get("rownum")));
                //add the selected item to the web board and make it as default selected
                //this.addToWebBoard(tariffUploadVO, request);
                
                //calling business layer to get the Tariff detail
                tariffUploadVO = tariffItemObject.getTariffDetail(tariffUploadVO.getTariffSeqId(),tariffUploadVO.getHospSeqId(),tariffUploadVO.getTariffTypeSearch(),TTKCommon.getUserSeqId(request));
                generalForm.initialize(mapping);
                strCaption="Edit";
                //Add the request object to DocumentViewer
                TTKCommon.documentViewer(request);
            }//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            else if(TTKCommon.getWebBoardId(request) != null)//take the tariff_seq_id from web board
            {
                //if web board id is found, set it as current web board id
            	
            	tariffUploadVO = new TariffUploadVO();
            //	tariffUploadVO.setTariffSeqId(TTKCommon.getWebBoardId(request));
                //calling business layer to get the Tariff detail
                tariffUploadVO = tariffItemObject.getTariffDetail(tariffUploadVO.getTariffSeqId(),tariffUploadVO.getHospSeqId(),(String)request.getSession().getAttribute("setTariffType"),TTKCommon.getUserSeqId(request));
                strCaption="Edit";
                //Add the request object to DocumentViewer
                TTKCommon.documentViewer(request);
            }//end of else if(TTKCommon.getWebBoardId(request) != null)
            else
            {
                TTKException expTTK = new TTKException();
                expTTK.setMessage("error.tariff.required");
                throw expTTK;
            	
            }//end of if(strMode.equals("EditTariff"))
        	
            DynaActionForm TariffItemForm = (DynaActionForm)FormUtils.setFormValues("frmSearchTariffItem",tariffUploadVO,this,mapping,request);
            
            TariffItemForm.set("caption",strCaption);
            request.getSession().setAttribute("frmSearchTariffItem",TariffItemForm);
            request.setAttribute("activityCode", tariffUploadVO.getActivityCode());
            request.getSession().setAttribute("TariffDetails",tariffUploadVO);
            if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
            	return this.getForward(strEditTariffItem, mapping, request);
            else
            	return this.getForward(strEditTariffItemEmpanelment, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
    }//end of doEditTariff(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
	public ActionForward doSaveTariffItem(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												 HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSaveTariffItem method of TarifItemAction");
			setLinks(request);
			TariffManager tariffItemObject=this.getTariffItemObject();
			DynaActionForm frmTariffItem = (DynaActionForm)form;
			
			TariffUploadVO tariffUploadVO	=	new TariffUploadVO();
			tariffUploadVO=(TariffUploadVO)FormUtils.getFormValues(frmTariffItem,this,mapping,request);
			
			long lngCount=tariffItemObject.editTariffItem(tariffUploadVO,(TTKCommon.getUserSeqId(request)));
			if(lngCount>0)
			{
				//edit flow
				
					request.setAttribute("updated","Tariff data saved successfully");
					//requering after saving the information
					tariffUploadVO=tariffItemObject.getTariffDetail(tariffUploadVO.getTariffSeqId(),tariffUploadVO.getHospSeqId(),tariffUploadVO.getTariffType(),TTKCommon.getUserSeqId(request));
					frmTariffItem = (DynaActionForm)FormUtils.setFormValues("frmSearchTariffItem",
							tariffUploadVO,this,mapping,request);
					frmTariffItem.set("caption","Edit");
			}//end of if(lngCount>0)
			request.getSession().setAttribute("frmSearchTariffItem",frmTariffItem);
			if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
				return this.getForward(strUpdatetariff, mapping, request);
			else
				return this.getForward(strUpdatetariffEmpanelment, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
	}//end of doSaveTariffItem(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	/**
     * This method is used to copy the selected records to web-board.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside the doCopyToWebBoard method of TarifItemAction");
    		this.populateWebBoard(request);
            return this.getForward(strTariffSearch, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
    }//end of doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * Populates the web board in the session with the selected items in the grid
     * @param request HttpServletRequest object which contains information about the selected check boxes
     */
    private void populateWebBoard(HttpServletRequest request)
    {
        String[] strChk = request.getParameterValues("chkopt");
        TableData tableData = (TableData)request.getSession().getAttribute("tableData");
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        ArrayList<Object> alCacheObject = new ArrayList<Object>();
        CacheObject cacheObject = null;
        if(strChk!=null&&strChk.length!=0)
        {
            //loop through to populate delete sequence id's and get the value from session for the matching check box value
            for(int i=0; i<strChk.length;i++)
            {
                cacheObject = new CacheObject();
                cacheObject.setCacheId(""+((TariffUploadVO)tableData.getData().
                		get(Integer.parseInt(strChk[i]))).getTariffSeqId());
                cacheObject.setCacheDesc(((TariffUploadVO)tableData.getData().
                		get(Integer.parseInt(strChk[i]))).getActivityCode());
                alCacheObject.add(cacheObject);
            }//end of for(int i=0; i<strChk.length;i++)
        }//end of if(strChk!=null&&strChk.length!=0)
        if(toolbar != null)
        {
            toolbar.getWebBoard().addToWebBoardList(alCacheObject);
        }//end of if(toolbar != null)
    }//end of populateWebBoard(HttpServletRequest request)
    
    /**
     * Adds the selected item to the web board and makes it as the selected item in the web board
     * @param tariffUploadVO TariffUploadVO object which contain the information of Tariff
     * @param request HttpServletRequest object which contains information about the selected check boxes
     */
    private void addToWebBoard(TariffUploadVO tariffUploadVO, HttpServletRequest request)
    {
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        CacheObject cacheObject = new CacheObject();
        cacheObject.setCacheId(""+tariffUploadVO.getTariffSeqId());
        cacheObject.setCacheDesc(tariffUploadVO.getActivityCode());
        ArrayList<Object> alCacheObject = new ArrayList<Object>();
        alCacheObject.add(cacheObject);
        //if the object(s) are added to the web board, set the current web board id
        toolbar.getWebBoard().addToWebBoardList(alCacheObject);
        toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
        //set weboardinvoked as true to avoid change of webboard id twice in same request
        request.setAttribute("webboardinvoked","true");
    }//end of addToWebBoard(TariffUploadVO tariffUploadVO, HttpServletRequest request)
	
    
    public ActionForward doShowTariff(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			try{
				log.debug("Inside the doShowTariff method of TarifItemAction");
				setLinks(request);
				
				TableData tableData = TTKCommon.getTableData(request);
				//create new table data object
				tableData = new TableData();
				//create the required grid table
				tableData.createTableInfo("TariffDiscountTable",new ArrayList());
				request.getSession().setAttribute("TariffDiscountTable",tableData);
				((DynaActionForm)form).initialize(mapping);//reset the form data
				
				if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
					return this.getForward(strShowrTariffs, mapping, request);
				else{
					DynaActionForm frmSearchModifyTariffItems	=	(DynaActionForm)form;
					frmSearchModifyTariffItems.set("providerCodeSearch", request.getSession().getAttribute("hospSeqIdforTariff"));
					return this.getForward(strShowrTariffsEmpanelment, mapping, request);
				}
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
			}//end of catch(Exception exp)
    }//end of doShowTariff(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    
    
    public ActionForward doShowServiceTypes(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			try{
				log.info("Inside the doShowServiceTypes method of TarifItemAction");
				setLinks(request);
				DynaActionForm frmSearchModifyTariffItems	=	(DynaActionForm)form;
				frmSearchModifyTariffItems.initialize(mapping);//reset the form data
				
				if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
					return this.getForward(strShowServiceTypes, mapping, request);
				else{
					/*frmSearchModifyTariffItems	=	(DynaActionForm)form;
					frmSearchModifyTariffItems.initialize(mapping);*/
					request.getSession().setAttribute("frmSearchModifyTariffItems", frmSearchModifyTariffItems);
					request.getSession().removeAttribute("frmSearchTariffItem");
					return this.getForward(strShowServiceTypesEmpanelment, mapping, request);
				}
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
			}//end of catch(Exception exp)
    }//end of doShowTariff(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    /**
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward doSearchTariffItems(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
			try{
				log.debug("Inside the doSearchTariffItems method of TarifItemAction");
				setLinks(request);
				TariffManager tariffItemObject=this.getTariffItemObject();						
				TableData tableData = TTKCommon.getTableData(request);
			//clear the dynaform if visiting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			
				String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
				String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
			{    
				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return this.getForward(strShowrTariffs, mapping, request);	
			}///end of if(!strPageID.equals(""))
			else
			{
				tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableData.modifySearchData("sort");//modify the search data
			}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("TariffDiscountTable",null);
				tableData.setSearchData(this.populateTariffSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else   
				//fetch the data from the data access layer and set the data to table object
				ArrayList alTariffItem= tariffItemObject.getTariffSearchList(tableData.getSearchData(),"Administration");
				tableData.setData(alTariffItem, "search");
				//set the table data object to session
				request.getSession().setAttribute("TariffDiscountTable",tableData);
			
				request.setAttribute("alTariffItem",alTariffItem);
				request.setAttribute("showValues","showValues");
				//finally return to the grid screen
				if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
					return this.getForward(strShowrTariffs, mapping, request);
				else
					return this.getForward(strShowrTariffsEmpanelment, mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
			}//end of catch(Exception exp)
	}//end of doSearchTariffItems(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * 
	 * 
	 */
	public ActionForward doModifyTariffItems(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 HttpServletResponse response) throws Exception{
		try{
			/*log.debug("Inside the doModifyTariffItems method of TarifItemAction");
			setLinks(request);
			TariffManager tariffItemObject=this.getTariffItemObject();
			DynaActionForm frmTariffItem = (DynaActionForm)form;
			String selectedIds	=	(String) frmTariffItem.get("selectedIds");
			String tariffTypeSearch	=	(String) frmTariffItem.get("tariffTypeSearch");
			frmTariffItem.initialize(mapping);
			long lngCount=tariffItemObject.editBulkTariffItem(tariffTypeSearch,selectedIds,(TTKCommon.getUserSeqId(request)));
		
			if(lngCount>0)
			{
				//edit flow
				request.setAttribute("updated","Records updated successfully");
				//requering after saving the information
				
			}//end of if(lngCount>0)
			request.getSession().setAttribute("frmSearchTariffItem",frmTariffItem);
			*/		
			

    		setLinks(request);
    		log.debug("Inside the doModifyTariffItems method of TarifItemAction");
    		TariffManager tariffItemObject=this.getTariffItemObject();
			DynaActionForm frmTariffItem = (DynaActionForm)form;
    		TableData  tableData =TTKCommon.getTableData(request);
    		JasperReport jasperReport;
    		ArrayList<Object> alTariffList = new ArrayList<Object>();
    		StringBuffer JrxmlFileName = null;
    		JasperPrint jasperPrint;
    		TTKReportDataSource reportDataSource = null;
    		String strTariffSeqID = getConcatenatedSeqID(request,(TableData)request.getSession().
    													  		   getAttribute("tableData"));
    		String tempTariffTypeSearch	=	"";
    		
    		if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
    			tempTariffTypeSearch	=	frmTariffItem.getString("tariffTypeSearch");
    		else
    			tempTariffTypeSearch	=	"HOSP";
    		
    			alTariffList.add(strTariffSeqID);
    			alTariffList.add(frmTariffItem.getString("discAmount"));
    			alTariffList.add(tempTariffTypeSearch);
    			alTariffList.add(TTKCommon.getUserSeqId(request));
    			float discAmount	=	frmTariffItem.getString("discAmount")==null?0:Float.parseFloat(frmTariffItem.getString("discAmount"));
    			long intResult=tariffItemObject.editBulkTariffItem(tempTariffTypeSearch,strTariffSeqID,discAmount ,(TTKCommon.getUserSeqId(request)));
    			if(intResult>0)
    			{
    				String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    				String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
    				//if the page number or sort id is clicked
    				if(!strPageID.equals("") || !strSortID.equals(""))
    				{
    					if(!strPageID.equals(""))
    				{    
    					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    					return this.getForward(strShowrTariffs, mapping, request);	
    				}///end of if(!strPageID.equals(""))
    				else
    				{
    					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
    					tableData.modifySearchData("sort");//modify the search data
    				}//end of else
    				}//end of if(!strPageID.equals("") || !strSortID.equals(""))
    				else
    				{
    					//create the required grid table
    					tableData.createTableInfo("TariffDiscountTable",null);
    					tableData.setSearchData(this.populateTariffSearchCriteria((DynaActionForm)form,request));
    					tableData.modifySearchData("search");
    				}//end of else   
    					//fetch the data from the data access layer and set the data to table object
    					ArrayList alTariffItem= tariffItemObject.getTariffSearchList(tableData.getSearchData(),"Administration");
    					tableData.setData(alTariffItem, "search");
    					//set the table data object to session
    					request.getSession().setAttribute("TariffDiscountTable",tableData);
    	    			frmTariffItem.initialize(mapping);
    				request.setAttribute("updated","Records Updated Successfully");
    			}//end of if(intResult>0)
			
		//return this.getForward(strShowrTariffs, mapping, request);
		if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
			return this.getForward(strShowrTariffs, mapping, request);
		else
			return this.getForward(strShowrTariffsEmpanelment, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
	}//end of doModifyTariffItems(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	/**
	 * For Modifying Discounts Service type wise
	 * 
	 */
	
	
	public ActionForward doNextServiceTypes(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doNextServiceTypes method of TarifItemAction");
			setLinks(request);
			request.getSession().removeAttribute("alServiceTypes");
			TariffManager tariffItemObject=this.getTariffItemObject();
			DynaActionForm frmTariffItem = (DynaActionForm)form;
			//frmTariffItem.initialize(mapping);
			
			String networkType	=	(String)frmTariffItem.get("networkType");
			
			String providerCode	=	(String)frmTariffItem.get("providerCode");
			String payerCode=(String)frmTariffItem.get("payerCode");
			if("TPA021".equals(payerCode))
			{
				payerCode="TPA033";
			}
			
			String indOrGrp		=	(String)frmTariffItem.get("indOrGrp");
			if("GRP".equals(indOrGrp))
			{
				providerCode	=	(String)frmTariffItem.get("grpProviderCode");
				networkType		=	(String)frmTariffItem.get("grpNetworkType");
			}
			
			String tempNetwork[]=	networkType.split("\\|");
			networkType			=	"";
			for(int k=0;k<tempNetwork.length;k++)
				networkType		=	networkType+","+"'"+tempNetwork[k]+"'";
			networkType			=	networkType.substring(1);
			
			String tempProvider[]=	providerCode.split("\\|");
			providerCode		 =	"";
			for(int k=0;k<tempProvider.length;k++)
				providerCode	=	providerCode+","+"'"+tempProvider[k]+"'";
			providerCode		=	providerCode.substring(1);

			
			ArrayList<String> alServiceTypes	=	tariffItemObject.getAllServiceNames(networkType,providerCode,payerCode);
			request.getSession().setAttribute("frmSearchTariffItem",frmTariffItem);
			
			if(alServiceTypes.size()==0){
				frmTariffItem.initialize(mapping);
				request.setAttribute("warning", "Tariff Not Uploaded");
			}
			
			request.getSession().setAttribute("alServiceTypes", alServiceTypes);
			
			if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
				return this.getForward(strShowServiceTypes, mapping, request);
			else
			{
				frmTariffItem.set("serviceall","");
				return this.getForward(strShowServiceTypesEmpanelment, mapping, request);
			}
			}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
	}//end of doModifyServiceTypes(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	/**
	 * For doModifySaveServiceTypes Service type wise
	 * 
	 */
	public ActionForward doModifySaveServiceTypes(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 HttpServletResponse response) throws Exception{
		try{
			log.info("Inside the doModifySaveServiceTypes method of TarifItemAction");
			setLinks(request);
			
			TariffManager tariffItemObject=this.getTariffItemObject();
			DynaActionForm frmTariffItem = (DynaActionForm)form;
			String tarifFlag	=	frmTariffItem.getString("tariffType");
			String providerCode	=	"|"+frmTariffItem.getString("providerCode")+"|";
			String payerCode=frmTariffItem.getString("payerCode");
			if("TPA021".equals(payerCode))
			       payerCode	=	"|"+"TPA033"+"|";
			else
				   payerCode	=	"|"+frmTariffItem.getString("payerCode")+"|";
			String networkType	=	"|"+frmTariffItem.getString("networkType")+"|";
			String corpCode		=	"|"+frmTariffItem.getString("corpCode")+"|";
			String indOrGrp		=	(String)frmTariffItem.get("indOrGrp");
			
			if("GRP".equals(indOrGrp))
			{
				providerCode	=	(String)"|"+frmTariffItem.get("grpProviderCode")+"|";
				networkType		=	(String)"|"+frmTariffItem.get("grpNetworkType")+"|";
			}
			
			
			//frmTariffItem.initialize(mapping);
			/*String[] serviceId	=	request.getParameterValues("serviceId");
			String[] cnId	=	request.getParameterValues("cnId");
			String[] gnId	=	request.getParameterValues("gnId");
			String[] snId	=	request.getParameterValues("snId");
			String[] bnId	=	request.getParameterValues("bnId");
			String[] wnId	=	request.getParameterValues("wnId");*/
			
			
			/*
			 * 
			 * NEW cODE
			 * 
			  */
			LinkedHashSet networks		=	(LinkedHashSet)request.getSession().getAttribute("networks");
			LinkedHashSet services		=	(LinkedHashSet)request.getSession().getAttribute("services");
			LinkedHashSet serviceIds		=	(LinkedHashSet)request.getSession().getAttribute("serviceIds");
		    String[] netDiscList=	request.getParameterValues("netDiscList");
		    StringBuffer strCnId	=	new StringBuffer();
			/*for(int k=0;k<netDiscList.length;k++)
				System.out.println("discs::"+netDiscList[k]);*/
			
			Iterator it		=	networks.iterator();
			Iterator it1	=	serviceIds.iterator();
		
				
			String[] netArr	=	new String[networks.size()];
			int waste		=	0;
			while(it.hasNext()){
					netArr[waste]	=	(String) it.next();
					waste++;
				}
			       waste =	0;

			String[] serArr	=	new String[services.size()];
			while(it1.hasNext()){
			    	serArr[waste]	=	(String) it1.next();
			    	waste++;
				}
			
			String[] netAndServArr	=	new String[netDiscList.length];//to Keep Network and service in  string array
			
			for(int k=0;k<serArr.length;k++){
				for(int l=0;l<netArr.length;l++){
					strCnId.append("|");
					strCnId.append(serArr[k]);
					strCnId.append("|");
					strCnId.append(netArr[l]);
					strCnId.append("|");
					strCnId.append(",");
				}
			}
			
			netAndServArr	=	strCnId.toString().split(",");
		    strCnId	=	new StringBuffer();
			for(int m=0; m<netAndServArr.length;m++){
				strCnId.append(netAndServArr[m]);
				strCnId.append(netDiscList[m]);
				strCnId.append("|");
				strCnId.append(",");
			}
			strCnId.insert(0, ",");
			 /* NEW cODE
			 */
            frmTariffItem.initialize(mapping);//reset form values
			int iResult		=	tariffItemObject.updateServiceTypesByNetworks(tarifFlag,strCnId,networkType,
					TTKCommon.getUserSeqId(request),providerCode,payerCode,corpCode);
			if(iResult>0)
				request.setAttribute("updated", "Data Updated Successfully");
			request.getSession().removeAttribute("alServiceTypes");
			if("Tariff".equals(TTKCommon.getActiveSubLink(request)))
				return this.getForward(strShowServiceTypes, mapping, request);
			else
			{
				request.getSession().removeAttribute("frmSearchTariffItem");
				return this.getForward(strShowServiceTypesEmpanelment, mapping, request);
			}
			}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
	}//end of doModifySaveServiceTypes(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	
	/**Returns the String of concatenated string of contact_seq_id delimeted by '|'.
	 * @param HttpServletRequest
	 * @param TableData
	 * @return String
	 */
	private String getConcatenatedSeqID(HttpServletRequest request,TableData tableData) {
		StringBuffer sbfConcatenatedSeqId=new StringBuffer("|");
		String strChOpt[]=request.getParameterValues("chkopt");
		if((strChOpt!=null)&& strChOpt.length!=0)
		{
			for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
			{
				if(strChOpt[iCounter]!=null)
				{
					if(iCounter==0)
					{
						sbfConcatenatedSeqId.append(String.valueOf(((TariffUploadVO)tableData.getRowInfo(
																Integer.parseInt(strChOpt[iCounter]))).getTariffSeqId()));
					}//end of if(iCounter==0)
					else
					{
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((TariffUploadVO)tableData.getRowInfo(Integer.
																		parseInt(strChOpt[iCounter]))).getTariffSeqId()));
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		sbfConcatenatedSeqId.append("|");
		return sbfConcatenatedSeqId.toString();
	} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)
	
	
	public ActionForward doShowDownloadTariff(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
        	 	log.debug("Inside the  doShowDownloadTariff method of TarrifItemAction");
        	 	setLinks(request);
    		 	String hospSeqId = (String)request.getSession().getAttribute("hospSeqIdforTariff");
    		 	Long  hospitalSeqId =Long.parseLong(hospSeqId);
    		
    		 	TariffManager tariffItemObject=this.getTariffItemObject();
    			TableData tableData = new TableData(); // create new
    			
    			tableData.createTableInfo("ProTariffdetailsTable",new ArrayList());
    			tableData.modifySearchData("search");
    			ArrayList alMemberList=null;
                alMemberList= tariffItemObject.getProTariffList(hospitalSeqId);	
            //    int size = alMemberList.size();
            //    System.out.println("alMemberList size==="+ size);
                if(alMemberList.size()!=0)
                {
                	TariffPlanVO tariffPlanVO=(TariffPlanVO)alMemberList.get(0);
                }
                 
                 tableData.setData(alMemberList, "search");
                 request.getSession().setAttribute("tableData",tableData);
                 return mapping.findForward(strDownloadTariffEmpanelment);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
}//end of doShowDownloadTariff
	
	
	public ActionForward doChangeServiceTypeYN(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			     setLinks(request);
        	 	 TariffManager tariffItemObject=this.getTariffItemObject();
    			 DynaActionForm frmTariffItem = (DynaActionForm)form;
    			 String strServiceAll = frmTariffItem.getString("serviceall");
    		     frmTariffItem.set("serviceall",strServiceAll);
    			 ArrayList<String> alServiceTypes	=	(ArrayList<String>)request.getAttribute("alServiceTypes");
    			 request.getSession().setAttribute("frmTariffUpdateServiceTypes",frmTariffItem);
                 return mapping.findForward(strShowServiceTypesEmpanelment);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTariffExp));
		}//end of catch(Exception exp)
}//end of doShowDownloadTariff
}//end of class TarrifItemAction