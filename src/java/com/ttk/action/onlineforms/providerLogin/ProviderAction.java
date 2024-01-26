package com.ttk.action.onlineforms.providerLogin; 
/**
 * @ (#) ProviderAction.java 13th Nov 2015
 * Project      : TTK HealthCare Services
 * File         : ProviderAction.java
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 13th Nov 2015
 *
 * @author       :  Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.sql.Clob;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.action.DynaActionForm;

import java.io.File;

import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.action.table.onlineforms.providerLogin.OverDueReportTable;
import com.ttk.action.table.onlineforms.providerLogin.PreAuthSearchTable;
import com.ttk.business.onlineforms.insuranceLogin.InsuranceCorporateManager;
import com.ttk.business.onlineforms.providerLogin.OnlineProviderManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.administration.MOUDocumentVO;
import com.ttk.dto.onlineforms.insuranceLogin.DashBoardVO;
import com.ttk.dto.onlineforms.providerLogin.BatchListVO;
import com.ttk.dto.onlineforms.providerLogin.PreAuthSearchVO;
import com.ttk.dto.preauth.ActivityDetailsVO;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for Searching the List Policies to see the Online Account Info.
 * This also provides deletion and updation of products.
 */
public class ProviderAction extends TTKAction {

    private static final Logger log = Logger.getLogger( ProviderAction.class );


    // Action mapping forwards.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";
    private static final String strViewAuthDetails="viewAuthDetails";
    private static final String strShowShortfall="viewShortfall";
    private static final String strPreAuthDashBoard="preAuthDashBoard";
    private static final String strBatchInvDetails="batchInvoiceDetails";
    private static final String strBatchInvReptDetails="batchInvoiceReptDetails";
    private static final String strOverDueReport="overDueReport";
    private static final String strNotify="notifications";
    private static final String strReportExp="report";
    private static final String strReportdisplay="reportdisplay1";
    
   public ActionForward doDefaultPreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           HttpServletResponse response) throws Exception{
	try{
			log.debug("Inside the doDefaultPreAuth method of ProviderAction");
			setOnlineLinks(request);
			
			DynaActionForm frmOnlinePreAuthLog =(DynaActionForm)form;
			frmOnlinePreAuthLog.initialize(mapping);     
			//((DynaActionForm)form).initialize(mapping);//reset the form data
			
			TableData tableData=TTKCommon.getTableData(request);
			tableData.createTableInfo("PreAuthSearchTable",null);//CorporateDataTable
			
			//Hiding Claims related columns from preauth
			int iTemp[]={2,10,12,13,14,15,16};
			if(iTemp!=null && iTemp.length>0)
	        {
	            for(int i=0;i<iTemp.length;i++)
	            {
	                ((Column)((ArrayList)tableData.getTitle()).get(iTemp[i])).setVisibility(false);
	            }//end of for(int i=0;i<iTemp.length;i++)
	        }//end of if(iTemp!=null && iTemp.length>0)
			
			request.getSession().setAttribute("tableData",tableData);

			
			return this.getForward("preauthLogSearch", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
			catch(Exception exp)
		{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,"checkEligibility"));
		}//end of catch(Exception exp)
   }//end of doDefaultPreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
   
   
   
   /**
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
   
   
   public ActionForward doDefaultClaim(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
try{
		log.debug("Inside the doDefaultClaim method of ProviderAction");
		setOnlineLinks(request);
		
		DynaActionForm frmOnlinePreAuthLog =(DynaActionForm)form;
		frmOnlinePreAuthLog.initialize(mapping);     
		//((DynaActionForm)form).initialize(mapping);//reset the form data
		
		TableData tableData=TTKCommon.getTableData(request);
		tableData.createTableInfo("PreAuthSearchTable",null);//CorporateDataTable
		request.getSession().setAttribute("tableData",tableData);
		
		//Hiding preauth  related columns from Claims
		int iTemp[]={0,1,9,8,11};
		if(iTemp!=null && iTemp.length>0)
        {
            for(int i=0;i<iTemp.length;i++)
            {
                ((Column)((ArrayList)tableData.getTitle()).get(iTemp[i])).setVisibility(false);
            }//end of for(int i=0;i<iTemp.length;i++)
        }//end of if(iTemp!=null && iTemp.length>0)
		((Column)((ArrayList)tableData.getTitle()).get(7)).setIsLink(false);
		
		return this.getForward("claimsLogSearch", mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
		catch(Exception exp)
	{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"checkEligibility"));
	}//end of catch(Exception exp)
}//end of doDefaultClaim(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)

   
   
   /**
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
   
   
   public ActionForward doDefaultDashBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
try{
		log.debug("Inside the doDefaultDashBoard method of ProviderAction");
		setOnlineLinks(request);
		
		DynaActionForm frmOnlineDashBoard =(DynaActionForm)form;
		frmOnlineDashBoard.initialize(mapping);     
		//((DynaActionForm)form).initialize(mapping);//reset the form data
		
		
		return this.getForward(strPreAuthDashBoard, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
		catch(Exception exp)
	{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"checkEligibility"));
	}//end of catch(Exception exp)
}//end of doDefaultDashBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)

   
   /**
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
   public ActionForward doSearchPreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           HttpServletResponse response) throws Exception{
		try{
			
			log.debug("Inside the doSearchPreAuth method of ProviderAction");
			setOnlineLinks(request);
			
			DynaActionForm frmOnlinePreAuthLog =(DynaActionForm)form;
			ArrayList<Object> alOnlineAccList = null;
			TableData tableData=null;
			String strPageID =""; 
			String strSortID ="";	
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
			tableData= TTKCommon.getTableData(request);
		
		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		{
			((DynaActionForm)form).initialize(mapping);//reset the form data
		}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
		strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
		//if the page number or sort id is clicked
		if(!strPageID.equals("") || !strSortID.equals(""))
		{
			if(!strPageID.equals(""))
		{
				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return (mapping.findForward("preauthLogSearch"));
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
			tableData.createTableInfo("PreAuthSearchTable",null);
			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,"PAT"));						
			tableData.modifySearchData("search");
		}//end of else
		int iTemp[]={2,10,12,13,14,15,16};
		if(iTemp!=null && iTemp.length>0)
        {
            for(int i=0;i<iTemp.length;i++)
            {
                ((Column)((ArrayList)tableData.getTitle()).get(iTemp[i])).setVisibility(false);
            }//end of for(int i=0;i<iTemp.length;i++)
        }//end of if(iTemp!=null && iTemp.length>0)
		
		alOnlineAccList= onlineProviderManager.getPreAuthSearchList(tableData.getSearchData());
		tableData.setData(alOnlineAccList, "search");
		//set the table data object to session
		request.getSession().setAttribute("tableData",tableData);
		
		return this.getForward("preauthLogSearch", mapping, request);
		}//end of try
	catch(TTKException expTTK)
	{
	return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
	}//end of catch(Exception exp)
}//end of doSearchPreAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)

   
   /**
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
   public ActionForward doSearchClaim(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           HttpServletResponse response) throws Exception{
		try{
			
			log.debug("Inside the doSearchClaim method of ProviderAction");
			setOnlineLinks(request);
			
			DynaActionForm frmOnlineClaimLog =(DynaActionForm)form;
			ArrayList<Object> alOnlineAccList = null;
			TableData tableData=null;
			String strPageID =""; 
			String strSortID ="";	
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
			tableData= TTKCommon.getTableData(request);
		
		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		{
			((DynaActionForm)form).initialize(mapping);//reset the form data
		}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
		strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
		//if the page number or sort id is clicked
		if(!strPageID.equals("") || !strSortID.equals(""))
		{
			if(!strPageID.equals(""))
		{
				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return (mapping.findForward("claimsLogSearch"));
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
			tableData.createTableInfo("PreAuthSearchTable",null);
			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,"CLM"));						
			tableData.modifySearchData("search");
			
		}//end of else
		
		//Hiding preauth  related columns from Claims
		int iTemp[]={0,1,9,8,11};
		if(iTemp!=null && iTemp.length>0)
        {
            for(int i=0;i<iTemp.length;i++)
            {
                ((Column)((ArrayList)tableData.getTitle()).get(iTemp[i])).setVisibility(false);
            }//end of for(int i=0;i<iTemp.length;i++)
        }//end of if(iTemp!=null && iTemp.length>0)
		((Column)((ArrayList)tableData.getTitle()).get(7)).setIsLink(false);
		
		alOnlineAccList= onlineProviderManager.getClaimSearchList(tableData.getSearchData());
		tableData.setData(alOnlineAccList, "search");
		//set the table data object to session
		request.getSession().setAttribute("tableData",tableData);
		
		return this.getForward("claimsLogSearch", mapping, request);
		}//end of try
	catch(TTKException expTTK)
	{
	return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
	}//end of catch(Exception exp)
}//end of doSearchClaim(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
      
   /*
    *on Forward of search PreAuths 
    */
   public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
   		HttpServletResponse response) throws Exception{
   	try{
   		setOnlineLinks(request);
   		log.debug("Inside the doForward method of ProviderAction");
			TableData tableData = (TableData) request.getSession().getAttribute("tableData");
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
           
   		tableData.modifySearchData(strForward);//modify the search data
   		//fetch the data from the data access layer and set the data to table object
   		ArrayList<Object> preauthLogSearch = onlineProviderManager.getPreAuthSearchList(tableData.getSearchData());
   		tableData.setData(preauthLogSearch, strForward);//set the table data
   		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
   		return this.getForward("preauthLogSearch", mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
   }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   
   
   /*
    *on Backward of search PreAuths 
    */
   public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
   		HttpServletResponse response) throws Exception{
   	try{
   		setOnlineLinks(request);
   		log.debug("Inside the doBackward method of ProviderAction");
			TableData tableData = (TableData) request.getSession().getAttribute("tableData");
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
           
   		tableData.modifySearchData(strBackward);//modify the search data
   		//fetch the data from the data access layer and set the data to table object
   		ArrayList<Object> preauthLogSearch = onlineProviderManager.getPreAuthSearchList(tableData.getSearchData());
   		tableData.setData(preauthLogSearch, strBackward);//set the table data
   		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
   		return this.getForward("preauthLogSearch", mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
   }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

 
   /*
    * Do Forward Common for ALL Forwards on provider Login
    */
   public ActionForward doForwardCommon(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	   		HttpServletResponse response) throws Exception{
	   	try{
	   			setOnlineLinks(request);
	   			log.debug("Inside the doForwardCommon method of ProviderAction");
				TableData tableData = (TableData) request.getSession().getAttribute("tableData");
				OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
				String strFowardType	=	request.getParameter("strFowardType");
				String strForwardVar	=	"";
				tableData.modifySearchData(strForward);//modify the search data
				//fetch the data from the data access layer and set the data to table object
				ArrayList<Object> preauthLogSearch=null;
				if("batchReconcil".equals(strFowardType)){
					preauthLogSearch	= onlineProviderManager.getBatchRenconcilList(tableData.getSearchData());
					strForwardVar	=	"batchReconiliation";
				}else if("batchInvoice".equals(strFowardType)){
					preauthLogSearch	= onlineProviderManager.getBatchInvoiceList(tableData.getSearchData());
					strForwardVar	=	"batchInvoice";
				}else if("overDue".equals(strFowardType)){
					ArrayList<BatchListVO> alOverDueReport = null;
					alOverDueReport	= onlineProviderManager.getOverDueList(tableData.getSearchData());
					strForwardVar	=	"overDueReport";
					tableData.setData(alOverDueReport, strForward);//set the table data
					return this.getForward(strForwardVar, mapping, request);
				}
				tableData.setData(preauthLogSearch, strForward);//set the table data
				request.getSession().setAttribute("tableData",tableData);//set the table data object to session
				return this.getForward(strForwardVar, mapping, request);
	   	}//end of try
	   	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
			}//end of catch(Exception exp)
	   }//end of doForwardCommon(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	   
   
   /*
    *on Backward of Common for ALL Forwards on provider Login
    */
   public ActionForward doBackwardCommon(ActionMapping mapping,ActionForm form,HttpServletRequest request,
   		HttpServletResponse response) throws Exception{ 
   	try{
   		setOnlineLinks(request);
   		log.debug("Inside the doBackwardCommon method of ProviderAction");
			TableData tableData = (TableData) request.getSession().getAttribute("tableData");
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
			String strFowardType	=	request.getParameter("strFowardType");
			String strForwardVar	=	"";
   		tableData.modifySearchData(strBackward);//modify the search data
   		//fetch the data from the data access layer and set the data to table object
   		ArrayList<Object> preauthLogSearch=null;
   		if("batchReconcil".equals(strFowardType)){
			preauthLogSearch	= onlineProviderManager.getBatchRenconcilList(tableData.getSearchData());
			strForwardVar	=	"batchReconiliation";
		} else if("batchInvoice".equals(strFowardType)){
			preauthLogSearch	= onlineProviderManager.getBatchInvoiceList(tableData.getSearchData());
			strForwardVar	=	"batchInvoice";
		}else if("overDue".equals(strFowardType)){
			ArrayList<BatchListVO> alOverDueReport = null;
			alOverDueReport	= onlineProviderManager.getOverDueList(tableData.getSearchData());
			strForwardVar	=	"overDueReport";
			tableData.setData(alOverDueReport, strBackward);//set the table data
			return this.getForward(strForwardVar, mapping, request);
		}
   		
   		tableData.setData(preauthLogSearch, strBackward);//set the table data
   		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
   		return this.getForward(strForwardVar, mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
   }//end of doBackwardCommon(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   
   /*
    *on Forward of search PreAuths 
    */
   public ActionForward doForwardClm(ActionMapping mapping,ActionForm form,HttpServletRequest request,
   		HttpServletResponse response) throws Exception{ 
   	try{
   		setOnlineLinks(request);
   		log.debug("Inside the doForwardClm method of ProviderAction");
			TableData tableData = (TableData) request.getSession().getAttribute("tableData");
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
           
   		tableData.modifySearchData(strForward);//modify the search data
   		//fetch the data from the data access layer and set the data to table object
   		ArrayList<Object> alOnlineAccList = onlineProviderManager.getClaimSearchList(tableData.getSearchData());
   		tableData.setData(alOnlineAccList, strForward);//set the table data
   		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
   		return this.getForward("claimsLogSearch", mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
   }//end of doForwardClm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   
   
   /*
    *on Backward of search PreAuths 
    */
   public ActionForward doBackwardClm(ActionMapping mapping,ActionForm form,HttpServletRequest request,
   		HttpServletResponse response) throws Exception{ 
   	try{
   		setOnlineLinks(request);
   		log.debug("Inside the doBackward method of ProviderAction");
			TableData tableData = (TableData) request.getSession().getAttribute("tableData");
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
           
   		tableData.modifySearchData(strBackward);//modify the search data
   		//fetch the data from the data access layer and set the data to table object
   		ArrayList<Object> alOnlineAccList = onlineProviderManager.getClaimSearchList(tableData.getSearchData());
   		tableData.setData(alOnlineAccList, strBackward);//set the table data
   		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
   		return this.getForward("claimsLogSearch", mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
   }//end of doBackwardClm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

 
   
   
   /*
    * Do Forward Common for All Forwards From Grid Table
    */
   
   public ActionForward doCommonForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
   		HttpServletResponse response) throws Exception{
   	try{
   		setOnlineLinks(request);
   		log.debug("Inside the doCommonForward method of ProviderAction");
   		String strVar		=	request.getParameter("strVar");
   		String strVarForward	=	"";

   			TableData tableData = (TableData) request.getSession().getAttribute("tableData");
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
			tableData.modifySearchData(strForward);//modify the search data
			//fetch the data from the data access layer and set the data to table object
			ArrayList<Object> alOnlineAccList	=	null;
			if("".equals(strVar))
			{
				alOnlineAccList = onlineProviderManager.getClaimSearchList(tableData.getSearchData());	
			}
			
   		
			
			tableData.setData(alOnlineAccList, strForward);//set the table data
   		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
   		return this.getForward(strVarForward, mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
   }//end of doCommonForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   
   
		   
   /**on Click on Status link showing the Authorization details 
    * 
   * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
    */
   public ActionForward doViewAuthDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
   		HttpServletResponse response) throws Exception{
   	try{
   		setOnlineLinks(request);
   		HttpSession session = request.getSession();
   		DynaActionForm frmAuthDetailsView = (DynaActionForm)form;
   		UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile");
   		log.debug("Inside the doViewAuthDetails method of ProviderAction");
   			String rownum	=	(String)request.getParameter("rownum");
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
			TableData tableData = (TableData) request.getSession().getAttribute("tableData");
			PreAuthSearchVO preAuthSearchVO	=	null;
			preAuthSearchVO = (PreAuthSearchVO)tableData.getRowInfo(Integer.parseInt(rownum));
			
			
			Object[] ProviderAllResult	=	onlineProviderManager.getAuthDetails((String)userSecurityProfile.getEmpanelNumber(),preAuthSearchVO.getStatusID(),preAuthSearchVO.getPatAuthSeqId(),
					preAuthSearchVO.getPatientCardId());
			PreAuthSearchVO viewAuthDetailsVO				= (PreAuthSearchVO) ProviderAllResult[0];
			ArrayList<DiagnosisDetailsVO> diagnosis  		= (ArrayList<DiagnosisDetailsVO>) ProviderAllResult[1];
			ArrayList<ActivityDetailsVO> activities			= (ArrayList<ActivityDetailsVO>) ProviderAllResult[2];
			
			session.setAttribute("ProviderDiagnosis", diagnosis);
			session.setAttribute("ProviderActivities", activities);
			
			frmAuthDetailsView = (DynaActionForm)FormUtils.setFormValues("frmAuthDetailsView",
					viewAuthDetailsVO, this, mapping, request);
			request.getSession().setAttribute("patAuthSeqId", preAuthSearchVO.getPatAuthSeqId());//using this in showing shortfall details		
            request.getSession().setAttribute("patientCardId", preAuthSearchVO.getPatientCardId());//using this in showing shortfall details
			request.getSession().setAttribute("frmAuthDetailsView", frmAuthDetailsView);
			
   		return this.getForward(strViewAuthDetails, mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
   }//end of doViewAuthDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   
   
   
   /**on Click on back Button from details Page 
    * 
   * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
    */
   public ActionForward doBackToPreAuthSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
   		HttpServletResponse response) throws Exception{
   	try{
   		setOnlineLinks(request);
   		log.debug("Inside the doBackToPreAuthSearch method of ProviderAction");
		TableData tableData = (TableData) request.getSession().getAttribute("tableData");
           
   		//fetch the data from the data access layer and set the data to table object
   		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
   		return this.getForward("preauthLogSearch", mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
   }//end of doBackToPreAuthSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   
   
   
   /**on Click on back Button from details Page 
    * 
   * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
    */
   public ActionForward doBackToPreAuthDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
   		HttpServletResponse response) throws Exception{
   	try{
   		setOnlineLinks(request);
   		log.debug("Inside the doBackToPreAuthDetails method of ProviderAction");
   		DynaActionForm frmAuthDetailsView	=	(DynaActionForm) request.getSession().getAttribute("frmAuthDetailsView");
   		//fetch the data from the data access layer and set the data to table object
   		return this.getForward(strViewAuthDetails, mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
   }//end of doBackToPreAuthDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   
   
   
   

   /**on Click shortfall shows the shortfall details of the pre auth 
    * 
   * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
    */
   public ActionForward doPreAuthShortfall(ActionMapping mapping,ActionForm form,HttpServletRequest request,
   		HttpServletResponse response) throws Exception{
   	try{
   		setOnlineLinks(request);
   		log.debug("Inside the doPreAuthShortfall method of ProviderAction");
   		OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
   		Long patAuthSeqId	=	(Long) request.getSession().getAttribute("patAuthSeqId");
		@SuppressWarnings("unchecked")
		ArrayList<String[]> alShortFallList	=	onlineProviderManager.getShortfallList(patAuthSeqId);
		request.getSession().setAttribute("alShortFallList",alShortFallList);
   		return this.getForward(strShowShortfall, mapping, request);
   	}//end of try
   	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
   }//end of doPreAuthShortfall(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   
   
   /*
    * Do Default Finance DashBoard
    */
   

   public ActionForward doFinanceDashBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           HttpServletResponse response) throws Exception{
	try{
			log.info("Inside the doFinanceDashBoard method of ProviderAction");
			setOnlineLinks(request);

			DynaActionForm frmOnlineValidateFinanceDashBoard =(DynaActionForm)form;
			frmOnlineValidateFinanceDashBoard.initialize(mapping);     
			//((DynaActionForm)form).initialize(mapping);//reset the form data
			
			request.getSession().setAttribute("frmOnlineValidateFinanceDashBoard", frmOnlineValidateFinanceDashBoard);
			return this.getForward("financehDashBoard", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
			catch(Exception exp)
		{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,"checkEligibility"));
		}//end of catch(Exception exp)
   }//end of doFinanceDashBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
   
   
   /**
    * Do Dearch Finance DashBoard
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
   public ActionForward doSearchFinanceDashBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
try{
		log.info("Inside the doSearchFinanceDashBoard method of ProviderAction");
		setOnlineLinks(request);
		UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile");

		DynaActionForm frmOnlineValidateFinanceDashBoard =(DynaActionForm)form;
		//frmOnlineValidateFinanceDashBoard.initialize(mapping);     
		//((DynaActionForm)form).initialize(mapping);//reset the form data
		String fromDate	=	frmOnlineValidateFinanceDashBoard.getString("fromDate");
		String toDate	=	frmOnlineValidateFinanceDashBoard.getString("toDate");
		OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
		String[] strFinanceDashboard	=	onlineProviderManager.getFinanceDahBoard(userSecurityProfile.getEmpanelNumber(),fromDate,toDate);
		request.setAttribute("strFinanceDashboard", strFinanceDashboard);
		
		request.getSession().setAttribute("frmOnlineValidateFinanceDashBoard", frmOnlineValidateFinanceDashBoard);
		return this.getForward("financehDashBoard", mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
		catch(Exception exp)
	{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"checkEligibility"));
	}//end of catch(Exception exp)
}//end of doSearchFinanceDashBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)



   public ActionForward doChequeWiseReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           HttpServletResponse response) throws Exception{
	try{
			log.debug("Inside the doChequeWiseReport method of ProviderAction");
			setOnlineLinks(request);
			
			DynaActionForm frmOnlineFinanceDashBoard =(DynaActionForm)form;
			frmOnlineFinanceDashBoard.initialize(mapping);
			return this.getForward("chequewisereport", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
			catch(Exception exp)
		{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,"checkEligibility"));
		}//end of catch(Exception exp)
   }//end of doChequeWiseReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
   
   
   
   
   public ActionForward doSearchChequeDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
try{
		log.debug("Inside the doSearchChequeDetails method of ProviderAction");
		setOnlineLinks(request);
		DynaActionForm frmOnlineFinanceDashBoard =(DynaActionForm)form;
		String chequeNumber	=	(String) frmOnlineFinanceDashBoard.get("chequeNumber");
		OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
		String[] strChequeDetails	=	onlineProviderManager.getChequeDetails(chequeNumber);
		request.setAttribute("strChequeDetails", strChequeDetails);
		request.getSession().setAttribute("frmOnlineFinanceDashBoard", frmOnlineFinanceDashBoard);
		return this.getForward("chequewisereport", mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
		catch(Exception exp)
	{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"checkEligibility"));
	}//end of catch(Exception exp)
}//end of doSearchChequeDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
   
   
   
   
   
   

   /**
    * Invoice wise Reports
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws Exception
    */

   public ActionForward doInvoiceWiseReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           HttpServletResponse response) throws Exception{
	try{
			log.debug("Inside the doInvoiceWiseReport method of ProviderAction");
			setOnlineLinks(request);
			DynaActionForm frmOnlineFinanceDashBoard =(DynaActionForm)form;
			frmOnlineFinanceDashBoard.initialize(mapping);
			return this.getForward("invoicewisereport", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
			catch(Exception exp)
		{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,"checkEligibility"));
		}//end of catch(Exception exp)
   }//end of doInvoiceWiseReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
   
   
   
   public ActionForward doSearchInvoiceDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
try{
		log.debug("Inside the doSearchInvoiceDetails method of ProviderAction");
		setOnlineLinks(request);
		DynaActionForm frmOnlineFinanceDashBoard =(DynaActionForm)form;
		String chequeNumber	=	(String) frmOnlineFinanceDashBoard.get("chequeNumber");
		UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile");
		OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
		String[] strChequeDetails	=	onlineProviderManager.getInvoiceDetails(chequeNumber,userSecurityProfile.getEmpanelNumber());
		request.setAttribute("strChequeDetails", strChequeDetails);
		request.getSession().setAttribute("frmOnlineFinanceDashBoard", frmOnlineFinanceDashBoard); 
		return this.getForward("invoicewisereport", mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
		catch(Exception exp)
	{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"checkEligibility"));
	}//end of catch(Exception exp)
}//end of doSearchInvoiceDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
   
   
   
   /*
    * Batch Reconciliation
    */
   
   
   public ActionForward doBatchReconciliation(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
try{
		log.debug("Inside the doBatchReconciliation method of ProviderAction");
		setOnlineLinks(request);
		
		DynaActionForm frmOnlinePreAuthLog =(DynaActionForm)form;
		frmOnlinePreAuthLog.initialize(mapping);     
		//((DynaActionForm)form).initialize(mapping);//reset the form data
		
		TableData tableData=TTKCommon.getTableData(request);
		tableData.createTableInfo("BatchReconciliationTable",null);//CorporateDataTable
		
		request.getSession().setAttribute("tableData",tableData);
		return this.getForward("batchReconiliation", mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
		catch(Exception exp)
	{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"checkEligibility"));
	}//end of catch(Exception exp)
}//end of doBatchReconciliation(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)

   
   
   /**										BATCH RECONCILIATION
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
   public ActionForward doSearchBatchReconciliation(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           HttpServletResponse response) throws Exception{
		try{
			
			log.debug("Inside the doSearchBatchReconciliation method of ProviderAction");
			setOnlineLinks(request);
			
			ArrayList<Object> alBatchReconcList = null;
			TableData tableData=null;
			String strPageID =""; 
			String strSortID ="";	
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
			tableData= TTKCommon.getTableData(request);
		
		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		{
			((DynaActionForm)form).initialize(mapping);//reset the form data
		}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
		strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
		//if the page number or sort id is clicked
		if(!strPageID.equals("") || !strSortID.equals(""))
		{
			if(!strPageID.equals(""))
		{
				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return (mapping.findForward("batchReconiliation"));
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
			tableData.createTableInfo("BatchReconciliationTable",null);
			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,"BAT"));						
			tableData.modifySearchData("search");
			
		}//end of else
		
		alBatchReconcList= onlineProviderManager.getBatchRenconcilList(tableData.getSearchData());
		tableData.setData(alBatchReconcList, "search");
		//set the table data object to session
		request.getSession().setAttribute("tableData",tableData);
		
		return this.getForward("batchReconiliation", mapping, request);
		}//end of try
	catch(TTKException expTTK)
	{
	return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
	}//end of catch(Exception exp)
}//end of doSearchBatchReconciliation(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
   
   
	/*
	 * Do Show details of Batch reconciliation - Invoice Details like InProgress, Approved...
	 */
	
public ActionForward doViewBatchInvDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
  		HttpServletResponse response) throws Exception{
  	try{
  		setOnlineLinks(request);
  		log.debug("Inside the doViewBatchInvDetails method of ProviderAction");
  		DynaActionForm frmOnlineFinanceDashBoard =(DynaActionForm)form;
		frmOnlineFinanceDashBoard.initialize(mapping);		
		UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile");
  			String strFlag	=	request.getParameter("strFlag");
  			String rownum	=	(String)request.getParameter("rownum");
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
			TableData tableData = (TableData) request.getSession().getAttribute("tableData");
			BatchListVO batchListVO	=	null;
			batchListVO = (BatchListVO)tableData.getRowInfo(Integer.parseInt(rownum));
			ArrayList<BatchListVO> alBatchInvDtls	=	onlineProviderManager.getBatchInvDetails(batchListVO.getBatchSeqId(),strFlag,userSecurityProfile.getEmpanelNumber());
			//Setting these 3 parameters to pass to download report
			request.setAttribute("batchSeqId", batchListVO.getBatchSeqId());
			request.setAttribute("flag", strFlag);
			request.setAttribute("empnlNo", userSecurityProfile.getEmpanelNumber());
			request.setAttribute("alBatchInvDtls", alBatchInvDtls);
  		return this.getForward(strBatchInvDetails, mapping, request);
  	}//end of try
  	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
  }//end of doViewBatchInvDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



   /*
    * Batch Invoice
    */
   
   
   public ActionForward doBatchInvoice(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           HttpServletResponse response) throws Exception{
try{
		log.debug("Inside the doBatchInvoice method of ProviderAction");
		setOnlineLinks(request);
		
		DynaActionForm frmOnlinePreAuthLog =(DynaActionForm)form;
		frmOnlinePreAuthLog.initialize(mapping);  
		
		TableData tableData=TTKCommon.getTableData(request);
		tableData.createTableInfo("BatchInvoiceTable",null);//CorporateDataTable
		
		request.getSession().setAttribute("tableData",tableData);
		return this.getForward("batchInvoice", mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
		catch(Exception exp)
	{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"checkEligibility"));
	}//end of catch(Exception exp)
}//end of doBatchInvoice(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
   
   
   
   
   
   /**										BATCH INVOICE
    * 
    * @param mapping
    * @param form
    * @param request
    * @param response
    * @return
    * @throws Exception
    */
   public ActionForward doSearchBatchInvoice(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           HttpServletResponse response) throws Exception{
		try{
			
			log.info("Inside the doSearchBatchInvoice method of ProviderAction");
			setOnlineLinks(request);
			
			ArrayList<Object> alBatchInvList = null;
			TableData tableData=null;
			String strPageID =""; 
			String strSortID ="";	
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
			tableData= TTKCommon.getTableData(request);
		
		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		{
			((DynaActionForm)form).initialize(mapping);//reset the form data
		}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
		strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
		//if the page number or sort id is clicked
		if(!strPageID.equals("") || !strSortID.equals(""))
		{
			if(!strPageID.equals(""))
		{
				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return (mapping.findForward("batchInvoice"));
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
			tableData.createTableInfo("BatchInvoiceTable",null);
			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,"BATINV"));						
			tableData.modifySearchData("search");
			
		}//end of else
		
		alBatchInvList= onlineProviderManager.getBatchInvoiceList(tableData.getSearchData());
		tableData.setData(alBatchInvList, "search");
		//set the table data object to session
		request.getSession().setAttribute("tableData",tableData);
		
		return this.getForward("batchInvoice", mapping, request);
		}//end of try
	catch(TTKException expTTK)
	{
	return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
	}//end of catch(Exception exp)
}//end of doSearchBatchInvoice(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
    
   
   
   
   
	/*
	 * Do Show details of Batch - Invoice Details...
	 */
	
public ActionForward doViewBatchInvReportDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
  		HttpServletResponse response) throws Exception{
  	try{
  		setOnlineLinks(request);
  		log.debug("Inside the doViewBatchInvReportDetails method of ProviderAction");
		UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile");
  			String rownum	=	(String)request.getParameter("rownum");
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
			TableData tableData = (TableData) request.getSession().getAttribute("tableData");
			BatchListVO batchListVO	=	null;
			batchListVO = (BatchListVO)tableData.getRowInfo(Integer.parseInt(rownum));
			String[] strBatchInvReptDtls	=	onlineProviderManager.getBatchInvReportDetails(batchListVO.getInvNo(),userSecurityProfile.getEmpanelNumber());
          request.setAttribute("strBatchInvReptDtls", strBatchInvReptDtls);
          
          	//Setting these 2 parameters to pass to download report
			request.setAttribute("invNo", batchListVO.getInvNo());
			request.setAttribute("empnlNo", userSecurityProfile.getEmpanelNumber());
			
  		return this.getForward(strBatchInvReptDetails, mapping, request);
  	}//end of try
  	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
  }//end of doViewBatchInvReportDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


/*
 * Do Default for OverDue Report 
 */

public ActionForward doDefaultOverDue(ActionMapping mapping,ActionForm form,HttpServletRequest request,
        HttpServletResponse response) throws Exception{
try{
		log.debug("Inside the doDefaultOverDue method of ProviderAction");
		setOnlineLinks(request);
		
		DynaActionForm frmOnlineOverDueReport =(DynaActionForm)form;
		frmOnlineOverDueReport.initialize(mapping);     
		
		TableData tableData=TTKCommon.getTableData(request);
		tableData.createTableInfo("OverDueReportTable",null);//OverDueReportTable
		request.getSession().setAttribute("tableData",tableData);
		
		return this.getForward(strOverDueReport, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
		catch(Exception exp)
	{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"checkEligibility"));
	}//end of catch(Exception exp)
}//end of doDefaultOverDue(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)


/*
 * Do Default for Notifications 
 */

public ActionForward doDefaultNotify(ActionMapping mapping,ActionForm form,HttpServletRequest request,
        HttpServletResponse response) throws Exception{
try{
		log.debug("Inside the doDefaultNotify method of ProviderAction");
		setOnlineLinks(request);
		
		DynaActionForm frmOnlineOverDueReport =(DynaActionForm)form;
		frmOnlineOverDueReport.initialize(mapping);     
		
		return this.getForward(strNotify, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
		catch(Exception exp)
	{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"checkEligibility"));
	}//end of catch(Exception exp)
}//end of doDefaultNotify(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)


/**										OverDue Report SEARCH
 * 
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward doSearchOverDueReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           HttpServletResponse response) throws Exception{
		try{
			
			log.debug("Inside the doSearchOverDueReport method of ProviderAction");
			setOnlineLinks(request);
			
			ArrayList<BatchListVO> alOverDueReport = null;
			TableData tableData=null;
			String strPageID =""; 
			String strSortID ="";	
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
			tableData= TTKCommon.getTableData(request);
		
		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		{
			((DynaActionForm)form).initialize(mapping);//reset the form data
		}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
		strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
		strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
		//if the page number or sort id is clicked
		if(!strPageID.equals("") || !strSortID.equals(""))
		{
			if(!strPageID.equals(""))
		{
				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return (mapping.findForward(strOverDueReport));
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
			tableData.createTableInfo("OverDueReportTable",null);
			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,"ODUE"));						
			tableData.modifySearchData("search");
			
		}//end of else
		
		alOverDueReport= onlineProviderManager.getOverDueList(tableData.getSearchData());
		tableData.setData(alOverDueReport, "search");
		//set the table data object to session
		request.getSession().setAttribute("tableData",tableData);
		
		return this.getForward(strOverDueReport, mapping, request);
		}//end of try
	catch(TTKException expTTK)
	{
	return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
	}//end of catch(Exception exp)
}//end of doSearchOverDueReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)


	//LOG DETAILS S T A R T S HERE
   /*
    * 
    */
		public ActionForward doLogDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		                HttpServletResponse response) throws Exception{
		try{
		log.debug("Inside the doLogDetails method of ProviderAction");
		setOnlineLinks(request);
		
		DynaActionForm frmInsCorporate =(DynaActionForm)form;
		ArrayList alLogList = null;
		
		OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
		alLogList	= onlineProviderManager.getLogData(TTKCommon.getUserID(request));
		request.setAttribute("alLogList", alLogList);
		
		return this.getForward("insLogData", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
	}//end of doLogDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
		
		

		//Recent Reports Downloaded S T A R T S HERE
	   /*
	    * 
	    */
			public ActionForward doDefaultRecentReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			                HttpServletResponse response) throws Exception{
			try{
			log.debug("Inside the doDefaultRecentReports method of ProviderAction");
			setOnlineLinks(request);
			
			DynaActionForm frmOnlineFinanceDashBoard =(DynaActionForm)form;
			UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile");
			ArrayList<String[]> alRecentReps = null;
			
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
			alRecentReps	= onlineProviderManager.getRecentReports(userSecurityProfile.getEmpanelNumber(),TTKCommon.getUserSeqId(request));
			request.setAttribute("alRecentReps", alRecentReps);
			return this.getForward("recentReports", mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
			return this.processOnlineExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
			}//end of catch(Exception exp)
		}//end of doDefaultRecentReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
		
		
   /**
    * this method will add search criteria fields and values to the arraylist and will return it
    * @param frmOnlinePreAuthLog formbean which contains the search fields
    * @return ArrayList contains search parameters
    */
   private ArrayList<Object> populateSearchCriteria(DynaActionForm frmOnlinePreAuthLog,HttpServletRequest request,String strIdentifier) throws TTKException
   {
	   
		UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile) request.getSession().getAttribute("UserSecurityProfile");
		

		// build the column names along with their values to be searched
	       ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();
		if("PAT".equals(strIdentifier)){
       //prepare the search BOX parameters
       //Long lngHospSeqId =userSecurityProfile.getHospSeqId();
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("preAuthNumber")));//0
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("fromDate")));//1
	   	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("toDate")));//2
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("patientName")));//3
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("approvalNumber")));//4
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("doctorName")));//5
	   	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("patientCardId")));//6
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("benefitType")));//7
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("status")));//8
	       	alSearchBoxParams.add((String)userSecurityProfile.getEmpanelNumber());//9 Empanel Number
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("preAuthRefNo")));//10
		
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("emirateID")));//11
		}else if("CLM".equals(strIdentifier)){
	       	alSearchBoxParams.add((String)userSecurityProfile.getEmpanelNumber());//0 Empanel Number
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("fromDate")));//1
	   	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("toDate")));//2
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("patientName")));//3
	   	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("patientCardId")));//4
			alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("claimNumber")));//5
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("invoiceNumber")));//6
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("claimType")));//7
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("batchNumber")));//8
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("benefitType")));//9
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("status")));//10
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("chequeNumber")));//11
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("emirateID")));//12
		}else if("BAT".equals(strIdentifier)){
	       	alSearchBoxParams.add((String)userSecurityProfile.getEmpanelNumber());//0 Empanel Number
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("fromDate")));//1
	   	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("toDate")));//2
	   	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("batchNumber")));//3
		}else if("BATINV".equals(strIdentifier)){
	       	alSearchBoxParams.add((String)userSecurityProfile.getEmpanelNumber());//0 Empanel Number
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("fromDate")));//1
	   	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("toDate")));//2
	   	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("batchNumber")));//3
		}else if("ODUE".equals(strIdentifier)){
	       	alSearchBoxParams.add((String)userSecurityProfile.getEmpanelNumber());//0 Empanel Number
	       	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("fromDate")));//1
	   	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("toDate")));//2
	   	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("invoiceNumber")));//3
		}
		
		
		else if("CSR".equals(strIdentifier)){
			
			alSearchBoxParams.add(frmOnlinePreAuthLog.get("claimnumber"));//1
			alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("datefrom")));//2
			alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("fromDate")));//3
			alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("toDate")));//4
			alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("benefitType")));//5
			alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("invoiceNo")));//6
			alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("batchNo")));//7
			alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("status")));//8
			alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmOnlinePreAuthLog.get("calimType")));//9
			
			
			
			
			
			
		}
		
		
		
		
   	return alSearchBoxParams;
   }//end of populateSearchCriteria(DynaActionForm frmOnlinePreAuthLog,HttpServletRequest request) 
   
   
    private OnlineProviderManager getOnlineAccessManagerObject() throws TTKException
    {
    	OnlineProviderManager onlineProviderManager = null;
        try
        {
            if(onlineProviderManager == null)
            {
                InitialContext ctx = new InitialContext();
                //onlineAccessManager = (OnlineAccessManager) ctx.lookup(OnlineAccessManager.class.getName());
                onlineProviderManager = (OnlineProviderManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineProviderManagerBean!com.ttk.business.onlineforms.providerLogin.OnlineProviderManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "onlineproviderinfo");
        }//end of catch
        return onlineProviderManager;
    }//end of getOnlineAccessManagerObject()

     
    
    public ActionForward doCSRDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           HttpServletResponse response) throws Exception{
    	
	try{
			log.debug("Inside the doCSRDefault method of ProviderAction");
			setOnlineLinks(request);
			
			DynaActionForm frmOnlinePreAuthLog =(DynaActionForm)form;
			frmOnlinePreAuthLog.initialize(mapping);     
			 PreAuthSearchVO voObj	=	new PreAuthSearchVO();
			 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
		        //prepare the search BOX parameters
		        String strUserID = userSecurityProfile.getUSER_ID();
		        String strEmpanelNumber = userSecurityProfile.getEmpanelNumber();
		       
		        voObj.setEmpanelId(strEmpanelNumber);
		        
			TableData tableData=TTKCommon.getTableData(request);
			tableData.createTableInfo("ClaimSummarySearchTable",null);//CorporateDataTable
			
			request.getSession().setAttribute("tableData",tableData);

			request.setAttribute("empanelmentId",voObj);
			return mapping.findForward("claimSummarySearch");
		}
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}
			catch(Exception exp)
		{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,"checkEligibility"));
		}
	
}  
    
    
    public ActionForward doSearchCSR(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	           HttpServletResponse response) throws Exception{
    	
    	try {
			
    		log.debug("Inside the doSearchCSR method of ProviderAction");
			setOnlineLinks(request);
			
			
			
			 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
		        //prepare the search BOX parameters
		        String strUserID = userSecurityProfile.getUSER_ID();
		        String strEmpanelNumber = userSecurityProfile.getEmpanelNumber();
			
			ArrayList<Object> claimsummarylist = null;
			TableData tableData=null;
			String strPageID =""; 
			String strSortID ="";	
			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
			tableData= TTKCommon.getTableData(request);
			
			DynaActionForm frmclaimsummary =(DynaActionForm)form;
			
			strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
			{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward("claimSummarySearch"));
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
				tableData.createTableInfo("ClaimSummarySearchTable",null);
				tableData.setSearchData(this.populateSearchCriteria(frmclaimsummary,request,"CSR"));						
				tableData.modifySearchData("search");
			}//end of else
			
			claimsummarylist= onlineProviderManager.getClaimSummarySearchList((tableData.getSearchData()),strEmpanelNumber);
			
			tableData.setData(claimsummarylist, "search");
			
			request.setAttribute("empanelmentId",strEmpanelNumber);
			
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
    		
			return this.getForward("claimSummarySearch", mapping, request);
		} catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}
			catch(Exception exp)
		{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,"checkEligibility"));
		}
		
}  
    
  
    public ActionForward doGenerateClimSummaryReport(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			  HttpServletResponse response) throws TTKException{
    	 try{
	    		log.debug("Inside the doGenerateClimSummaryReport method of HospitalReportsAction");
	    		setLinks(request);
	    		DynaActionForm frmReportDetail =(DynaActionForm)form;
	    		JasperReport mainJasperReport,emptyReport;
	    		TTKReportDataSource mainTtkReportDataSource = null;
	    		JasperPrint mainJasperPrint = null;
	    		 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	    		
	    		 String strEmpanelNumber = userSecurityProfile.getEmpanelNumber();
	    		String strParam = request.getParameter("parameter");
	    		String reportLink = request.getParameter("reportlinkname");
	         	String strParam1=  "|"+strEmpanelNumber+"|"+strParam;
	    		String jrxmlfile=null;
	    		jrxmlfile=request.getParameter("fileName");
	    		
	    		try
	    		{
	    			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
	    			
	    			HashMap<String,String> hashMap = new HashMap<String,String>();
	    			
	    			ByteArrayOutputStream boas=new ByteArrayOutputStream();
	    			
	    			hashMap.put("empanelmentId",request.getParameter("empanelmentId"));
	    			hashMap.put("claimnumber",request.getParameter("claimnumber"));
	    			hashMap.put("fromDate",request.getParameter("fromDate"));
	    			hashMap.put("datefrom1",request.getParameter("datefrom1"));
	    			hashMap.put("toDate",request.getParameter("toDate"));
	    			hashMap.put("benefitType1",request.getParameter("benefitType1"));
	    			hashMap.put("invoiceNo",request.getParameter("invoiceNo"));
	    			hashMap.put("batchNo",request.getParameter("batchNo"));
	    			hashMap.put("status1",request.getParameter("status1"));
	    			hashMap.put("calimType",request.getParameter("calimType"));
	    			hashMap.put("parameter",strParam1.substring(2,strParam1.length()));
	    			
	    			mainTtkReportDataSource = new TTKReportDataSource("providerloginClaimSummary", strParam1);
	    			StringBuilder sb=new StringBuilder();
	    			ResultSet main_report_RS = mainTtkReportDataSource.getResultData();
	    			 /*if(main_report_RS!=null && main_report_RS.next())
	    	            {
                     if(main_report_RS.getString("REJ_REASON")!=null){
	    	            	Clob clb=main_report_RS.getClob("REJ_REASON");

	    	            	Reader r =clb.getCharacterStream();
	    	            	int i;
	    	            	while((i=r.read())!=-1)
	    	            	{
	    	            		char c=(char) i;
	    	            	    sb.append(c);
	    	            	}
	    	            	}
	    	            	main_report_RS.beforeFirst();
	    	            }*/
	    			
	    			mainJasperReport = JasperCompileManager.compileReport(jrxmlfile);
	    			/*hashMap.put("rejectionReason", sb.toString());*/
	    			if (main_report_RS != null && main_report_RS.next()) {
	    				main_report_RS.beforeFirst();
	    				mainJasperPrint = JasperFillManager.fillReport(mainJasperReport, hashMap, mainTtkReportDataSource);
	    				  				
	    			} else {
	    				
	    				mainJasperPrint = JasperFillManager.fillReport(emptyReport,hashMap, new JREmptyDataSource());
	    			}
	    			
	    			if("EXCEL".equals(request.getParameter("reportType")))
		    		{
	    				
	    		JRXlsExporter jExcelApiExporter = new JRXlsExporter();
 				jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,mainJasperPrint);
 				jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
 				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
 				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
 				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
 				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
 				jExcelApiExporter.exportReport();
	    			
		    		}
	    			
	    			request.setAttribute("boas",boas);
	    			frmReportDetail.set("letterPath", reportLink);
	    			
	    		}
	    		catch(Exception exp)
	        	{
	    			return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
	        	}
	    		return (mapping.findForward(strReportdisplay));
	    	}
	    	catch(TTKException expTTK)
	    	{
	    		return this.processExceptions(request, mapping, expTTK);
	    	}
	    	catch(Exception exp)
	    	{
	    		return this.processExceptions(request, mapping, new TTKException(exp, strReportExp));
	    	}
    }
    
    
    
    
    
    public ActionForward doForwardCsr(ActionMapping mapping,ActionForm form,HttpServletRequest request,
       		HttpServletResponse response) throws Exception{
    	try{
       		setOnlineLinks(request);
       		log.debug("Inside the doForwardCsr method of ProviderAction");
    			TableData tableData = (TableData) request.getSession().getAttribute("tableData");
    			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
               
    			 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
 		        //prepare the search BOX parameters
 		        String strUserID = userSecurityProfile.getUSER_ID();
 		        String strEmpanelNumber = userSecurityProfile.getEmpanelNumber();
    			
       		tableData.modifySearchData(strForward);//modify the search data
       		//fetch the data from the data access layer and set the data to table object
       		ArrayList<Object> alOnlineAccList = onlineProviderManager.getClaimSummarySearchList((tableData.getSearchData()),strEmpanelNumber);
       		tableData.setData(alOnlineAccList, strForward);//set the table data
       		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
       		return this.getForward("claimSummarySearch", mapping, request);
       	}//end of try
       	catch(TTKException expTTK)
    		{
    			return this.processExceptions(request, mapping, expTTK);
    		}//end of catch(TTKException expTTK)
    		catch(Exception exp)
    		{
    			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
    		}//end of catch(Exception exp)
    	
    }
    
    
    public ActionForward doBackwardCsr(ActionMapping mapping,ActionForm form,HttpServletRequest request,
       		HttpServletResponse response) throws Exception{
    	try{
       		setOnlineLinks(request);
       		log.debug("Inside the doBackwardCsr method of ProviderAction");
    			TableData tableData = (TableData) request.getSession().getAttribute("tableData");
    			OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
               
    			 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
  		        //prepare the search BOX parameters
  		        String strUserID = userSecurityProfile.getUSER_ID();
  		        String strEmpanelNumber = userSecurityProfile.getEmpanelNumber();
    			
       		tableData.modifySearchData(strBackward);//modify the search data
       		//fetch the data from the data access layer and set the data to table object
       		ArrayList<Object> alOnlineAccList = onlineProviderManager.getClaimSummarySearchList((tableData.getSearchData()),strEmpanelNumber);
       		tableData.setData(alOnlineAccList, strBackward);//set the table data
       		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
       		return this.getForward("claimSummarySearch", mapping, request);
       	}//end of try
       	catch(TTKException expTTK)
    		{
    			return this.processExceptions(request, mapping, expTTK);
    		}//end of catch(TTKException expTTK)
    		catch(Exception exp)
    		{
    			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
    		}//end of catch(Exception exp)
    	
    }
    
    public ActionForward viewActivityDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception 
			{
				try 
				{
					setLinks(request);
					log.debug("Inside ProviderAction viewActivityDetails() ");
					String activityDtlSeqId = (String) request.getParameter("activityDelSeqId");
					OnlineProviderManager onlineProviderManager	=	this.getOnlineAccessManagerObject();
					ArrayList<String[]> observations = onlineProviderManager.getActivityDetails(new Long(activityDtlSeqId));
					request.getSession().setAttribute("observationDetails",	observations);
					return mapping.findForward("viewActivityObservDetails");
				}// end of try
			 	catch(TTKException expTTK)
				{
					return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
				}//end of catch(Exception exp)
	
			}
    
    public ActionForward doFileDownload(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
		log.debug("inside ProviderAction doPreauthFileDownload method");
		
		String fileName=(String)request.getParameter("filename");
 		String filaPath=TTKPropertiesReader.getPropertyValue("ObservationFileUpload");
        
        File file=new File(filaPath+fileName);
         if(!file.exists()){
        	 ActionMessages actionMessages = new ActionMessages();
             ActionMessage actionMessage = new ActionMessage("error.file.not.exist");
             actionMessages.add("global.error",actionMessage);
             saveErrors(request,actionMessages);
        	 
         }
         response.setContentType("application/txt");
         response.setHeader("Content-Disposition", "attachment;filename="+fileName);
 
         ServletOutputStream sos=response.getOutputStream();
         FileInputStream fis=new FileInputStream(file);
           byte[]fileData=new byte[fis.available()];
           fis.read(fileData);
           sos.write(fileData);
           fis.close();
           sos.flush();
           sos.close();
		 return null;
 }

}//end of ProviderAction
