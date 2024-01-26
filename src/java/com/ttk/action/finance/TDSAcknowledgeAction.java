/**
* @ (#) TDSAcknowledgeAction.java Aug 11, 2009
* Project 		: TTK HealthCare Services
* File 			: TDSAcknowledgeAction.java
* Author 		: Balakrishna Erram
* Company 		: Span Systems Corporation
* Date Created 	: Aug 11, 2009
*
* @author 		: Balakrishna Erram
* Modified by 	:
* Modified date :
* Reason 		:
*/

package com.ttk.action.finance;

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
import com.ttk.business.finance.TDSRemittanceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.AcknowledgmentInfoVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for displaying the details of Configure Properties.
 */
public class TDSAcknowledgeAction extends TTKAction{

	private static final Logger log = Logger.getLogger( TDSAcknowledgeAction.class );

	// Forward Path
	private static final String strTDSAcknowledge="tdsacknowledge";
	private static final String strTDSAckDetails="tdsackdetails";
	private static final String strTDSAttrRateexp="tdsacknowledgeexp";



	/**
	 * This method is used to navigate to  detail screen to edit selected record.
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
															HttpServletResponse response) throws TTKException{
		try{
			log.info("Inside the doDefault method of TDSAcknowledgeAction");
			setLinks(request);

			DynaActionForm frmTDSAcknowledge =(DynaActionForm)form;
			ArrayList<Object> altdsCatRateList=new ArrayList<Object>();

			TableData tableData =TTKCommon.getTableData(request);
			//TDSConfigurationManager  tdsConfigurationManager =this.getTDSConfManager();
			if(tableData==null){
				//create new table data object
				tableData = new TableData();
			}//end of if(tableData==null)
			//create the required grid table
			String strTable = "TDSAcknowledgeTable";
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(strSortID.equals(""))
			{
				tableData.createTableInfo(strTable,null);
				tableData.modifySearchData("search");
			}//end of if(strSortID.equals(""))
			else
			{
				tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableData.modifySearchData("sort");//modify the search data
			}// end of else
			tableData.setData(altdsCatRateList,"search");
			request.getSession().setAttribute("tableData",tableData);
			request.getSession().setAttribute("frmTDSAcknowledge",frmTDSAcknowledge);
			return this.getForward(strTDSAcknowledge , mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTDSAttrRateexp));
		}//end of catch(Exception exp)
	}//end of doViewProperties(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
																	HttpServletResponse response) throws TTKException{
		try{
			log.info("Inside PreAuthAction doSearch");
			setLinks(request);
			TDSRemittanceManager tdsRemManager=this.getTDSRemManagerObject();
			TableData tableData =TTKCommon.getTableData(request);
			DynaActionForm frmTDSAcknowledge =(DynaActionForm)form;

			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strTDSAcknowledge);
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
				tableData.createTableInfo("TDSAcknowledgeTable",null);
				tableData.setSearchData(this.populateSearchCriteria(frmTDSAcknowledge));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList<Object> alAckInfoList= tdsRemManager.getAckInfoList(tableData.getSearchData());
			tableData.setData(alAckInfoList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			request.getSession().setAttribute("frmTDSAcknowledge",frmTDSAcknowledge);
			//finally return to the grid screen
			return this.getForward(strTDSAcknowledge, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTDSAttrRateexp));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to navigate to Acknowledgement details screen to edit selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAcknowledgementDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws TTKException{
		try{
			setLinks(request);
			log.info("Inside the doAdd method of TDSAcknowledgeAction");
			DynaActionForm frmTDSAckDetail =(DynaActionForm)form;
			frmTDSAckDetail.initialize(mapping);
			return this.getForward(strTDSAckDetails , mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTDSAttrRateexp));
		}//end of catch(Exception exp)
	}//end of doAcknowledgementDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	/**
	 * This method is used to save the screen details.
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
															HttpServletResponse response) throws TTKException{
		try{
			setLinks(request);
			log.info("Inside the doSave method of TDSAcknowledgeAction");
			TDSRemittanceManager tdsRemManager=this.getTDSRemManagerObject();
			DynaActionForm frmTDSAckDetail =(DynaActionForm)form;
			int iYear = Integer.parseInt(TTKCommon.checkNull(frmTDSAckDetail.get("financialYear").toString()));

			AcknowledgmentInfoVO ackInfoVO =(AcknowledgmentInfoVO)FormUtils.getFormValues(frmTDSAckDetail,this,mapping,request);
			ackInfoVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			if(iYear < 2009)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.finance.tds.acknowledgementinformation.validyear");
                throw expTTK;
			}//end of if(iYear < 2009)
			if(!"on".equalsIgnoreCase(request.getParameter("chckBoxValueYN"))) {
				//return this.getForward(strTDSAckDetails, mapping, request);
				TTKException expTTK = new TTKException();
				 expTTK.setMessage("error.finance.tds.monthlyremittance.disclaimer");
				 throw expTTK;
			}//end of if(!"on".equalsIgnoreCase(request.getParameter("chckBoxValueYN")))
			int intAckValue = tdsRemManager.saveAckInfo(ackInfoVO);

			if(intAckValue>0) {
				request.setAttribute("updated","message.savedSuccessfully");
				frmTDSAckDetail = (DynaActionForm)FormUtils.setFormValues("frmTDSAckDetail",
						ackInfoVO, this, mapping, request);
			}//end of if(intAckValue>0)
			request.getSession().setAttribute("frmTDSAckDetail",frmTDSAckDetail);
			return this.getForward(strTDSAckDetails , mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTDSAttrRateexp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	/**
	 * This method is used to reset the form values.
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
															HttpServletResponse response) throws TTKException{
		try{
			log.info("Inside the doReset method of TDSAcknowledgeAction");
			setLinks(request);
			DynaActionForm frmTDSAckDetail =(DynaActionForm)form;
			frmTDSAckDetail.initialize(mapping);
			return this.getForward(strTDSAckDetails , mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTDSAttrRateexp));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	/**
	 * This method is used to navigate to  detail screen to edit selected record.
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
			setLinks(request);
			log.info("Inside the doClose method of TDSAcknowledgeAction");

			return this.doDefault(mapping, form, request, response);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strTDSAttrRateexp));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * This method will add search criteria fields and values to the arraylist and will return it
	 * @param frmProductList form bean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmTDSAcknowledge)
	{
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(frmTDSAcknowledge.get("insuranceComp"));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmTDSAcknowledge.get("financeYear")));
		alSearchParams.add(frmTDSAcknowledge.get("tdsQuater"));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmProductList)

	/**
	 * Returns the TDSRemittanceManager session object for invoking methods on it.
	 * @return TDSRemittanceManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private TDSRemittanceManager getTDSRemManagerObject() throws TTKException
	{
		TDSRemittanceManager tdsRemManager = null;
		try
		{
			if(tdsRemManager == null)
			{
				InitialContext ctx = new InitialContext();
				tdsRemManager = (TDSRemittanceManager) ctx.lookup("java:global/TTKServices/business.ejb3/TDSRemittanceManagerBean!com.ttk.business.finance.TDSRemittanceManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strTDSAttrRateexp);
		}//end of catch
		return tdsRemManager;
	}//end getTDSRemManagerObject()

}//end of ConfPropertiesAction class


