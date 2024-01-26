/**
 * @ (#) CustomisedBatchAction.java   July 24, 2008
 * Project        : TTK HealthCare Services
 * File           : CustomisedBatchAction.java
 * Author         : Sanjay.G.Nayaka
 * Company        : Span Systems Corporation
 * Date Created   : July 24, 2008
 *
 * @author        : Sanjay.G.Nayaka
 * Modified by    : 
 * Modified date  :
 * Reason         : 
 */

package com.ttk.action.support;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.enrollment.PolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.enrollment.CardBatchVO;

public class CustomisedBatchAction extends TTKAction{

	private static Logger log = Logger.getLogger(CustomisedBatchAction.class );

	//Action mapping forwards 
	private static final String strCustmItem="custmitemlist";
	private static final String strCardBatch="cardbatchlist";
	
    /**
	 * This method is used to forward the control to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside doDefault  method  of  CustomisedBatchAction");
			setLinks(request);
			request.getSession().removeAttribute("boas");
			request.setAttribute("openreport","");
			return this.getForward(strCustmItem, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"CustomisedBatch"));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to forward the control to cardbatchlist
	 * 
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doCustomisedItem(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside  doCustomisedItem  method of CustomisedBatchAction");
			setLinks(request);
			String strTable = "";
			DynaActionForm frmCardBatch =(DynaActionForm)form;
			frmCardBatch.initialize(mapping);
			if(request.getParameter("caption")!=null)
			{
				frmCardBatch.set("caption",request.getParameter("caption"));
			}//end of if(request.getParameter("caption")!=null)
			//get the table data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			strTable = "CardBatchTable";
			tableData.createTableInfo(strTable,new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			request.setAttribute("openreport","");
		    return this.getForward(strCardBatch, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"CustomisedBatch"));
		}//end of catch(Exception exp)
	}//end of doCustomisedItem(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
    /**
	 * This method is used to forward the control to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside doClose method of  CustomisedBatchAction");
			setLinks(request);
			return this.getForward(strCustmItem, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"CustomisedBatch"));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside the doSearch method of CustomisedBatchAction");
			setLinks(request);
			String strTable = "";
			strTable = "CardBatchTable";
		
			//get the session bean from the bean pool for each excecuting thread
			PolicyManager  policyObject=this.getPolicyManagerObject();
			//get the table data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(strPageID.equals(""))
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strCustmItem);					
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo(strTable,null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alCardBatchList = new ArrayList();
			alCardBatchList = policyObject.getCardBatchList(tableData.getSearchData());
			tableData.setData(alCardBatchList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			request.setAttribute("openreport","");
			//finally return to the grid screen
			return this.getForward(strCardBatch, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"CustomisedBatch"));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to Generate the Certificate.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doGenerateCertficate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try
		{
			log.debug("Inside the doGenerateCertficate method of CustomisedBatchAction");
			setLinks(request);
		    JasperReport jasperReport,emptyReport;
			JasperPrint jasperPrint;
			TTKReportDataSource ttkReportDataSource;
			String strPolicyGroupSeqIds;
			TableData  tableData =TTKCommon.getTableData(request);
			strPolicyGroupSeqIds = getConcatenatedSeqId(request, tableData);
            String jrxmlfile="generalreports/HdfcCertificate.jrxml";
			try
			{
				ttkReportDataSource = new TTKReportDataSource("HDFCCert",strPolicyGroupSeqIds);
				jasperReport = JasperCompileManager.compileReport(jrxmlfile);
				emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
				HashMap hashMap = new HashMap();
				ByteArrayOutputStream boas=new ByteArrayOutputStream();
				if(ttkReportDataSource.getResultData().next())
				{
					ttkReportDataSource.getResultData().beforeFirst();
					jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);					
				}//end of if(!(ttkReportDataSource.getResultData().next()))
				else
				{
					jasperPrint = JasperFillManager.fillReport(emptyReport, hashMap,new JREmptyDataSource());
				}//end of else

				JasperExportManager.exportReportToPdfStream(jasperPrint,boas);	
				request.getSession().setAttribute("boas",boas);
				String strGenerateRpt="/ReportsAction.do?mode=doDisplayReport&"+"reportType=PDF";
				request.setAttribute("openreport",strGenerateRpt);				
			}//end of try			
			catch (Exception e)
			{
				e.printStackTrace();
			}//end of catch (Exception e)
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"CustomisedBatch"));
		}//end of catch(Exception exp)
		return (mapping.findForward(strCardBatch));
	}//end of doGenerateCertficate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	
	/**
	 * Returns array of string which contains the pipe separated PolicyGroupSeqId's 
	 *  
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the pipe separated PolicyGroupSeqId's 
	 *
	 */
	private String  getConcatenatedSeqId(HttpServletRequest request, TableData tableData)
	{
		StringBuffer sbfPolicyGroupSeqIds=new StringBuffer("|");
		String strChk[]=request.getParameterValues("chkopt");
		if(strChk!=null&&strChk.length!=0)
		{
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{
					//extract the sequence id from the value object
					if(i == 0)
					{
						sbfPolicyGroupSeqIds.append(String.valueOf(((CardBatchVO)tableData.getRowInfo(
								Integer.parseInt(strChk[i]))).getPolicyGroupSeqID()));
					}//end of if(i == 0)
					else
					{
						sbfPolicyGroupSeqIds.append("|").append(String.valueOf(((CardBatchVO)tableData.getRowInfo
								(Integer.parseInt(strChk[i]))).getPolicyGroupSeqID()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++)
		}//end of if(strChk!=null&&strChk.length!=0)
		sbfPolicyGroupSeqIds.append("|");
		return sbfPolicyGroupSeqIds.toString();
	}//end of getConcatenatedSeqId(HttpServletRequest request, TableData tableData)
	
	/**
	 * Returns the PolicyManager session object for invoking methods on it.
	 * @return PolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PolicyManager getPolicyManagerObject() throws TTKException
	{
		PolicyManager PolicyManager = null;
		try
		{
			if(PolicyManager == null)
			{
				InitialContext ctx = new InitialContext();
				PolicyManager = (PolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/PolicyManagerBean!com.ttk.business.enrollment.PolicyManager");
			}//end of if(PolicyManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "CustomisedBatch");
		}//end of catch(Exception exp)
		return PolicyManager;
	}//end of getPolicyManagerObject()

	/**
	 * This method will add search criteria fields and values to the arraylist and will return it
	 * @param frmCardBatch formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 * @throws TTKException 
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmCardBatch,HttpServletRequest request)
	{
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
	    alSearchParams.add(frmCardBatch.get("batchNbr"));
		alSearchParams.add(frmCardBatch.get("startDate"));
		alSearchParams.add(frmCardBatch.get("endDate"));
	    return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmCardBatch,HttpServletRequest request)

}//end of CustomisedBatchAction
