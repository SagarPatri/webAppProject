/**
 * @ (#) ServTaxConfigurationAction.java Sep 16, 2010
 * Project       : TTK HealthCare Services
 * File          : ServTaxConfigurationAction.java
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

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
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ServTaxRateVO;
import com.ttk.dto.administration.WebconfigInsCompInfoVO;
import com.ttk.business.administration.ProductPolicyManager;//kocnewhosp

import formdef.plugin.util.FormUtils;

/**
 * This class is used for viewing the Revision List Of Service Tax.
 * This also provides addition and updation of  Service Tax.
 */
public class ServTaxConfigurationAction extends TTKAction {
	
	private static final Logger log = Logger.getLogger( ServTaxConfigurationAction.class );
	
	private static final String strServTaxConfiguration="servtaxconfiguration";			
	private static final String strViewDetails="viewdetails";
	private static final String strClose="close";
	private static final String strNotify="shownotify";
	
	/**
	 * This method is used to View the Service tax Revision List Screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws TTKException if any error occurs
	 */
	
	public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		log.debug("Inside the doView method of ServTaxConfigurationAction");
		try
		{
			setLinks(request);			
			DynaActionForm frmservTaxConfiguration = (DynaActionForm)form;
			ArrayList<Object> alServTaxRateList=new ArrayList<Object>();
			TableData tableData =TTKCommon.getTableData(request);
			ConfigurationManager  servConfigurationManager =this.getConfManager();
			if(tableData==null){
				//create new table data object
				tableData = new TableData();
			}//end of if(tableData==null) 	
			//create the required grid table
			String strTable = "ServTaxConfTable";
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
			alServTaxRateList= servConfigurationManager.getServRevisionList();
			tableData.setData(alServTaxRateList,"search");
			request.getSession().setAttribute("tableData",tableData);
			request.getSession().setAttribute("frmservTaxConfiguration",frmservTaxConfiguration);
			return this.getForward(strServTaxConfiguration, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strServTaxConfiguration));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
	
	/**
	 * This method is used to View the Service tax Revision Details.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws TTKException if any error occurs
	 */
	public ActionForward doViewDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
    {
		 
		 try
		   {
			 log.debug("Inside the doViewDetails method of ServTaxConfigurationAction");
			 setLinks(request);
			 ConfigurationManager servConfigurationManager=this.getConfManager();    //get the business object to call DAO
			 DynaActionForm frmservTaxConfiguration=(DynaActionForm)request.getSession().getAttribute("frmservTaxConfiguration");
			 TableData tableData =(TableData) request.getSession().getAttribute("tableData");
			 ServTaxRateVO servTaxRateVO= new ServTaxRateVO();
			 if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			 {
				 servTaxRateVO=(ServTaxRateVO)tableData.getRowInfo(Integer.parseInt((String)
						 request.getParameter("rownum")));
			 }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			 Long lServTaxSeqId= servTaxRateVO.getServTaxSeqId();
			 servTaxRateVO= servConfigurationManager.getServTaxDetail(lServTaxSeqId);
			 frmservTaxConfiguration= (DynaActionForm)FormUtils.setFormValues("frmservTaxConfiguration",
					 servTaxRateVO, this, mapping, request);
			 String strFromDate = TTKCommon.getFormattedDate(servTaxRateVO.getServRevDateFrom());
			 String strToDate = TTKCommon.getFormattedDate(servTaxRateVO.getServRevDateTo()); 
			 frmservTaxConfiguration.set("revDateFrom",strFromDate );
			 frmservTaxConfiguration.set("revDateTo", strToDate);
			 frmservTaxConfiguration.set("applRatePerc", servTaxRateVO.getApplRatePerc().toString());
			 request.getSession().setAttribute("frmservTaxConfiguration",frmservTaxConfiguration);
			 return this.getForward(strViewDetails,mapping,request);
		   }//end of try
		 catch(TTKException expTTK)
		 {
			 return this.processExceptions(request, mapping, expTTK);
		 }//end of catch(TTKException expTTK)
		 catch(Exception exp)
		 {
			 return this.processExceptions(request, mapping, new TTKException(exp, strServTaxConfiguration));
		 }//end of catch(Exception exp)
	}//end of doViewDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
     * @throws TTKException if any error occurs
     */
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			   HttpServletResponse response) throws TTKException
	{
		try
		{
			log.debug("Inside the doClose method of ServTaxConfigurationAction");
			setLinks(request);
			ConfigurationManager servConfigurationManager=this.getConfManager(); 
			DynaActionForm frmservTaxConfiguration=(DynaActionForm)request.getSession().getAttribute("frmservTaxConfiguration");
			TableData tableData =(TableData) request.getSession().getAttribute("tableData");
			ArrayList<Object> alServTaxRateList= servConfigurationManager.getServRevisionList();
			tableData.setData(alServTaxRateList,"search");
			request.getSession().setAttribute("tableData",tableData);
			request.getSession().setAttribute("frmservTaxConfiguration",frmservTaxConfiguration);
			//return (mapping.findForward(strClose));
			return this.getForward(strClose, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strServTaxConfiguration));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	/**
	 * This method is used to navigate to detail screen to add a record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws TTKException if any error occurs
	 */
	public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			 HttpServletResponse response) throws TTKException
			 {
				try
				{
					log.debug("Inside the doAdd method of ServTaxConfigurationAction");
					setLinks(request);
					DynaActionForm frmservTaxConfiguration = (DynaActionForm)form;
					frmservTaxConfiguration.initialize(mapping);
					return this.getForward(strViewDetails, mapping, request);
				}//end of try
				catch(TTKException expTTK)
				{
					return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request, mapping, new TTKException(exp,strServTaxConfiguration));
				}//end of catch(Exception exp)
	      }//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	 * @throws TTKException if any error occurs
	 */
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		try
		{
			log.debug("Inside the doSave method of ServTaxConfigurationAction");
			setLinks(request);
			ConfigurationManager servConfigurationManager=this.getConfManager();
			DynaActionForm frmservTaxConfiguration=(DynaActionForm)request.getSession().getAttribute("frmservTaxConfiguration");
			ServTaxRateVO servTaxRateVO= (ServTaxRateVO)FormUtils.getFormValues(frmservTaxConfiguration,
								"frmservTaxConfiguration",this, mapping, request);
		
			String strRevDateFrom = (String)frmservTaxConfiguration.get("revDateFrom");
			String strRevDateTo = (String)frmservTaxConfiguration.get("revDateTo");
			Date dtRevDateTo = TTKCommon.getUtilDate(strRevDateTo);
			String strApplRatePerc = request.getParameter("applRatePerc");
			if(TTKCommon.checkNull(strApplRatePerc)!="")
			{
				servTaxRateVO.setApplRatePerc(new BigDecimal(TTKCommon.checkNull(strApplRatePerc)));
			}//end of if(TTKCommon.checkNull(strApplRatePerc[i])!="")
			else
			{
				servTaxRateVO.setApplRatePerc(new BigDecimal(0.00));
			}//end of else
			servTaxRateVO.setReviseDateFrom(strRevDateFrom);
			servTaxRateVO.setRevDateTo(dtRevDateTo);
			servTaxRateVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			long lngServTaxSeqId =servConfigurationManager.saveSerTaxRate(servTaxRateVO);
			//log.info("lServTaxSeqId"+lServTaxSeqId);
			if(lngServTaxSeqId > 0)
			{
			if(!frmservTaxConfiguration.get("servTaxSeqId").equals(""))
			{
			  request.setAttribute("updated","message.savedSuccessfully");
			}//end of if(!frmservTaxConfiguration.get("servTaxSeqId").equals(""))
			else
			{
			  request.setAttribute("updated","message.addedSuccessfully");
			}//end of else
			}//end of if(iResult>0)
			  servTaxRateVO= servConfigurationManager.getServTaxDetail(lngServTaxSeqId);
			  frmservTaxConfiguration= (DynaActionForm)FormUtils.setFormValues("frmservTaxConfiguration",
					servTaxRateVO, this, mapping, request);
			  request.getSession().setAttribute("frmservTaxConfiguration",frmservTaxConfiguration);
			  return this.getForward(strViewDetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
		      return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		      return this.processExceptions(request, mapping, new TTKException(exp,strServTaxConfiguration));
		}//end of catch(Exception exp)
   }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	 * @throws TTKException if any error occurs
	 */
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
    {
		try
		{
			log.debug("Inside the doReset method of ServTaxConfigurationAction");
			setLinks(request);
			ConfigurationManager servConfigurationManager=this.getConfManager();
			DynaActionForm frmservTaxConfiguration=(DynaActionForm)request.getSession().getAttribute("frmservTaxConfiguration");
			ServTaxRateVO servTaxRateVO= null;
			if(!frmservTaxConfiguration.get("servTaxSeqId").equals(""))
			{
				Long lngServTaxSeqId=TTKCommon.getLong(frmservTaxConfiguration.getString("servTaxSeqId"));
				servTaxRateVO=servConfigurationManager.getServTaxDetail(lngServTaxSeqId);
				frmservTaxConfiguration = (DynaActionForm)FormUtils.setFormValues("frmservTaxConfiguration",
						servTaxRateVO, this, mapping, request);
				request.getSession().setAttribute("frmservTaxConfiguration",frmservTaxConfiguration);
			}//end of if(!frmservTaxConfiguration.get("servTaxSeqId").equals(""))
			else
			{
				frmservTaxConfiguration.initialize(mapping);
			}//end of else
			return this.getForward(strViewDetails,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strServTaxConfiguration));
		}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	
//kocnewhosp
	
	
	
	
	public ActionForward doShowNotify(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		log.debug("Inside the doDefault method of doShowNotify");
		try
		{
		setLinks(request);			
		DynaActionForm frmtdsConfiguration = (DynaActionForm)form;
		frmtdsConfiguration.initialize(mapping);
		//return this.getForward(strNotify, mapping, request);
		return mapping.findForward(strNotify);
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
    
	
	
	public ActionForward doSaveNotify(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		try{
			log.debug("Inside the doSave method of InsCompanyInfoAction");
			setLinks(request);	
			DynaActionForm frmInsCompanyInfo = (DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append(" - [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			ConfigurationManager servConfigurationManager=this.getConfManager();
			WebconfigInsCompInfoVO webconfigInsCompInfoVO = new WebconfigInsCompInfoVO();
			webconfigInsCompInfoVO =(WebconfigInsCompInfoVO)FormUtils.getFormValues(frmInsCompanyInfo, "frmInsCompanyInfo",this, mapping, request);
			webconfigInsCompInfoVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
			webconfigInsCompInfoVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			int iResult = servConfigurationManager.saveWebConfigInsInfo(webconfigInsCompInfoVO);	
			if(iResult>0)
			{
					request.setAttribute("updated","message.saved");
			}//end of if(iResult>0)	
			//webconfigInsCompInfoVO=servConfigurationManager.getWebConfigInsInfo(TTKCommon.getWebBoardId(request));				
			frmInsCompanyInfo = (DynaActionForm)FormUtils.setFormValues("frmInsCompanyInfo",
					webconfigInsCompInfoVO, this, mapping, request);
			frmInsCompanyInfo.set("caption",sbfCaption.toString());
			request.setAttribute("frmInsCompanyInfo",frmInsCompanyInfo);
			//return this.getForward(strNotify, mapping, request);
			return mapping.findForward(strNotify);
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
			return this.processExceptions(request, mapping, new TTKException(exp,strServTaxConfiguration));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
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
			throw new TTKException(exp, strServTaxConfiguration);
		}//end of catch
		return ConfManager;
	}//end getTDSConfManager()
}//end of ServTaxConfigurationAction
