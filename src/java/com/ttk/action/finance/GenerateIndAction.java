/**
 * @ (#)GenerateIndAction.java 24th Apr 2010
 * Project       : TTK HealthCare Services
 * File          : GenerateIndAction
 * Author        : Manikanta Kumar G G
 * Company       : Span Systems Corporation
 * Date Created  : 24th Apr 2010
 *
 * @author       : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.finance;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.finance.TDSRemittanceManager;
import com.ttk.common.TDSCertificateGenHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.TDSHospitalInfoVO;


public class GenerateIndAction  extends TTKAction{

	//private static final Logger log = Logger.getLogger(GenerateIndAction.class );
	private static final String strGenerateInd ="generateindividual";
	//Modes.
	private static final String strBackward="Backward";
	private static final String strForward="Forward";

	/**
	 * This method is used to search the data with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	@SuppressWarnings("unchecked")
	public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		try{
			setLinks(request);
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			TDSRemittanceManager tdsRemManager=this.getTDSRemManagerObject();
			TableData hospitalData =TTKCommon.getTableData(request);
			DynaActionForm frmCertGenInd =(DynaActionForm)form;
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					hospitalData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strGenerateInd);
				}///end of if(!strPageID.equals(""))
				else
				{
					hospitalData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					hospitalData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				hospitalData.createTableInfo("HospitalSearchTable",null);
				hospitalData.setSearchData(this.populateSearchCriteria((DynaActionForm)form, request));
				hospitalData.modifySearchData("search");
			}//end of else
			ArrayList<Object> alTdsHospList= tdsRemManager.getTdsDeductedHospList(hospitalData.getSearchData());
			
			hospitalData.setData(alTdsHospList, "search");
			if(strActiveSubLink.equals("TDS Configuration"))
			{
				((Column)((ArrayList)hospitalData.getTitle()).get(3)).setVisibility(false);
				((Column)((ArrayList)hospitalData.getTitle()).get(0)).setIsLink(false);
				((Column)((ArrayList)hospitalData.getTitle()).get(0)).setColumnWidth("41%");
				((Column)((ArrayList)hospitalData.getTitle()).get(1)).setColumnWidth("29%");
				((Column)((ArrayList)hospitalData.getTitle()).get(2)).setColumnWidth("30%");
			}
			/*this.setColumnVisiblity(hospitalData,strActiveSubLink);
			this.setColumnWidth(hospitalData,strActiveSubLink);
			this.setLink(hospitalData, strActiveSubLink);*/
			//set the table data object to session
			request.getSession().setAttribute("tableData",hospitalData);
			request.getSession().setAttribute("frmCertGenInd",frmCertGenInd);
			//finally return to the grid screen
			return this.getForward(strGenerateInd, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"finance"));
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
			HttpServletResponse response) throws TTKException{
		try{
			setLinks(request);
			String strForward = "";
			strForward = strGenerateInd;
			//get the session bean from the bean pool for each excecuting thread
			TDSRemittanceManager tdsRemManager=this.getTDSRemManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strBackward);
			ArrayList<Object> alTdsHospList = tdsRemManager.getTdsDeductedHospList(tableData.getSearchData());
			tableData.setData(alTdsHospList, strBackward);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"finance"));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest
		//request,HttpServletResponse response)
	
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
			HttpServletResponse response) throws TTKException{
		try{
			setLinks(request);
			String strMapForward = "";
			strMapForward = strGenerateInd;
			//get the session bean from the bean pool for each excecuting thread
			TDSRemittanceManager tdsRemManager=this.getTDSRemManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strForward);
			ArrayList<Object> alTdsHospList = tdsRemManager.getTdsDeductedHospList(tableData.getSearchData());
			tableData.setData(alTdsHospList, strForward);
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strMapForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"finance"));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	/**
	 * This Method is used for certificate Generation Individually
	 *  Finally it forwards to the appropriate view based on the specified forward mappings
	 *  @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	
	public ActionForward doGenerate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException
	{
		try{
			setLinks(request);
			DynaActionForm frmCertGenInd =(DynaActionForm)form;
			TDSRemittanceManager tdsRemManager=this.getTDSRemManagerObject();
			TableData tableData =TTKCommon.getTableData(request);
			String strConcatenatedSeqID= this.getConcatenatedSeqID(request,(TableData)request.getSession().getAttribute("tableData"));
			int iCount=0;
			ArrayList<Object> alGenerateList = new ArrayList<Object>();
			alGenerateList.add((String)frmCertGenInd.get("sFinancialyear"));
			alGenerateList.add("CGI");
			alGenerateList.add(null);
			alGenerateList.add(TTKCommon.getUserSeqId(request));
			alGenerateList.add(strConcatenatedSeqID);
			alGenerateList.add((String)frmCertGenInd.get("tdsbatchQtr"));
			iCount=tdsRemManager.generateTdsBatchInd(alGenerateList);
			if(iCount>0)
			{
				request.setAttribute("updated","message.saved");
			}//end of if(iCount>0)
			request.getSession().setAttribute("tableData",tableData);
			TDSCertificateGenHelper tdsCertificateGenHelper = new TDSCertificateGenHelper();
			tdsCertificateGenHelper.indTdsCertGeneration();
			return this.getForward(strGenerateInd, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"finance"));
		}//end of catch(Exception exp)
	}//end of doGenerate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**Returns the String of concatenated string of contact_seq_id delimeted by '|'.
	 * @param request The HTTP request we are processing
	 * @param tableData The TableData Object
	 * @return String, which returns the concatenated Seqs IDs
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
						sbfConcatenatedSeqId.append(String.valueOf(((TDSHospitalInfoVO)tableData.getRowInfo(Integer.parseInt(strChOpt[iCounter]))).getEmplNumber()));
					}//end of if(iCounter==0)
					else
					{
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((TDSHospitalInfoVO)tableData.getRowInfo(Integer.parseInt(strChOpt[iCounter]))).getEmplNumber()));
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		sbfConcatenatedSeqId.append("|");
		return sbfConcatenatedSeqId.toString();
	} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)
	
	/**
	 * This method will add search criteria fields and values to the arraylist 
	 * @param frmCertGenInd form bean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 * @throws TTKException
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmCertGenInd,HttpServletRequest request)
	{
		ArrayList<Object> alSearchParams = new ArrayList<Object>();	
		
		alSearchParams.add(((String)frmCertGenInd.get("sFinancialyear")));
		alSearchParams.add(frmCertGenInd.get("sEmpanelmentNo"));
		alSearchParams.add(frmCertGenInd.get("tdsbatchQtr"));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmcertGenInd)
	
	/**
	 * Returns the TDSRemittanceManager session object for invoking methods on it
	 * @return TDSRemittanceManager session object which can be used for method invocation
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
			throw new TTKException(exp, "finance");
		}//end of catch
		return tdsRemManager;
	}//end getTDSRemManagerObject()

}//end of GenerateIndAction
