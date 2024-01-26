/**
 * @ (#) PreDailyTransferAction.java February 17, 2010
 * Project      : TTK HealthCare Services
 * File         : PreDailyTransferAction.java
 * Author       : Balakrishna E
 * Company      : Span Systems Corporation
 * Date Created : February 17, 2010
 *
 * @author       : 
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.finance;

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
import com.ttk.business.finance.TDSRemittanceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.DailyTransferVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for below purpose:
 * Search the List of TDS claims.
 * Claims can be included or excluded from the daily remittance.
 */
public class PreDailyTransferAction extends TTKAction {

	private static final Logger log = Logger.getLogger(DailyTransferAction.class );

	//Modes.
	private static final String strPageBackward="Backward";
	private static final String strPageForward="Forward";
	
	//Action mapping forwards.
	private static final String strPreDailyTransferList="predailytransferlist";
	
	//Exception Message Identifier
	private static final String strPreDailyTransfer="predailytransfer";
	
	private static final String strDailyTransferTable="DailyTransferTable";
	private static final String strTableData="tableData";
	
	/**
	 * This method is used to initialize the search grid.
	 * Finally it forwards to the appropriate view based on the specified forward mappings.
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
			log.info("Inside the doDefault method of PreDailyTransferAction");
			setLinks(request);
			String strForward = "";
			String strTable = "";
			if("Y".equals(TTKCommon.checkNull(request.getParameter("Entry"))))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if("Y".equals(TTKCommon.checkNull(request.getParameter("Entry"))))
			//get the table data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			strForward = strPreDailyTransferList;
			strTable = strDailyTransferTable;
			tableData.createTableInfo(strTable,new ArrayList<Object>());
			request.getSession().setAttribute(strTableData,tableData);			
			
			DynaActionForm frmPreDailyTransfer= (DynaActionForm)form;
			frmPreDailyTransfer.initialize(mapping);						
			frmPreDailyTransfer.set("sEndDate", TTKCommon.getFormattedDate(new Date()));			
			request.getSession().setAttribute("frmPreDailyTransfer",frmPreDailyTransfer);
			
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreDailyTransfer));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
	
	/**
	 * This method is used to search the data with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings.
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
			log.debug("Inside the doSearch method of DailyTransferAction");
			setLinks(request);
			String strForward = "";
			String strTable = "";
			strForward = strPreDailyTransferList;
			strTable = strDailyTransferTable;
			if("Y".equals(TTKCommon.checkNull(request.getParameter("Entry")))){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if("Y".equals(TTKCommon.checkNull(request.getParameter("Entry"))))
			//get the session bean from the bean pool for each executing thread
			TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
			//get the table data from session if exists
			TableData tableData =TTKCommon.getTableData(request);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!"".equals(strPageID) || !"".equals(strSortID))
			{
				if(!"".equals(strPageID))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return mapping.findForward(strForward);
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
				tableData.createTableInfo(strTable,null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList<Object> alDailyTransferList = new ArrayList<Object>();
			alDailyTransferList = tdsRemittanceManager.getIncludExcludTdsClms(tableData.getSearchData());
			tableData.setData(alDailyTransferList, "search");
			//set the table data object to session
			request.getSession().setAttribute(strTableData,tableData);
			//finally return to the grid screen
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreDailyTransfer));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
							//HttpServletResponse response)
	
	/**
	 * this method will add search criteria fields and values to the arraylist and will return it.
	 * @param frmViewDailyTransfer formbean which contains the search fields
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmViewDailyTransfer) throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();		
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewDailyTransfer.get("sInsuranceCompanyID")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewDailyTransfer.get("sClaimSettleNo")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmViewDailyTransfer.get("tdsClaimsInclExcl")));
		alSearchParams.add((String)frmViewDailyTransfer.get("sStartDate"));
		alSearchParams.add((String)frmViewDailyTransfer.get("sEndDate"));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmProductList)
	
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
			log.debug("Inside the doBackward method of DailyTransferAction");
			setLinks(request);
			String strForward = "";
			strForward = strPreDailyTransferList;
			//get the session bean from the bean pool for each excecuting thread
			TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strPageBackward);
			ArrayList<Object> alDailyTransferList = tdsRemittanceManager.getIncludExcludTdsClms(tableData.getSearchData());
			tableData.setData(alDailyTransferList, strPageBackward);
			request.getSession().setAttribute(strTableData,tableData);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreDailyTransfer));
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
			log.debug("Inside the doForward method of DailyTransferAction");
			setLinks(request);
			String strMapForward = "";
			strMapForward = strPreDailyTransferList;
			//get the session bean from the bean pool for each excecuting thread
			TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
			TableData tableData = TTKCommon.getTableData(request);
			tableData.modifySearchData(strPageForward);
			ArrayList<Object> alDailyTransferList = tdsRemittanceManager.getIncludExcludTdsClms(tableData.getSearchData());
			tableData.setData(alDailyTransferList, strPageForward);
			request.getSession().setAttribute(strTableData,tableData);
			return this.getForward(strMapForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreDailyTransfer));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
	
	/**
	 * Returns the TDSRemittanceManager session object for invoking methods on it.
	 * @return TDSRemittanceManager session object which can be used for method invokation
	 * @throws TTKException if any error occurs
	 */
	private TDSRemittanceManager getTDSRemittanceManagerObject() throws TTKException
	{
		TDSRemittanceManager tdsRemittanceManager = null;
		try
		{
			if(tdsRemittanceManager == null)
			{
				InitialContext ctx = new InitialContext();
				tdsRemittanceManager = (TDSRemittanceManager) ctx.lookup("java:global/TTKServices/business.ejb3/TDSRemittanceManagerBean!com.ttk.business.finance.TDSRemittanceManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strPreDailyTransfer);
		}//end of catch
		return tdsRemittanceManager;
	}//end getTDSRemittanceManagerObject()
	
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
	public ActionForward doIncludeExclude(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws TTKException{
		try{
			log.info("Inside the doGenerateDailyTransfer method of doIncludeExclude");
			setLinks(request);
			DynaActionForm frmPreDailyTransferDetail= (DynaActionForm)form;
			if("Y".equals(TTKCommon.checkNull(request.getParameter("Entry")))){
				frmPreDailyTransferDetail.initialize(mapping);//reset the form data
			}//end of if("Y".equals(TTKCommon.checkNull(request.getParameter("Entry"))))
			String strExclude = "";			
			if(frmPreDailyTransferDetail.get("tdsClaimsInclExcl").toString().equals("IEE"))
			{
				strExclude ="N";
			}//end of if(frmPreDailyTransferDetail.get("tdsClaimsInclExcl").toString().equals("IEE"))
			else if(frmPreDailyTransferDetail.get("tdsClaimsInclExcl").toString().equals("IEI"))
			{
				strExclude ="Y";
			}//end of else  if(frmPreDailyTransferDetail.get("tdsClaimsInclExcl").toString().equals("IEI"))
			else
			{
				strExclude ="Y";
			}//end of else
			
			ArrayList<Object> alPreDailyTranDetList = new ArrayList<Object>();
			frmPreDailyTransferDetail = (DynaActionForm)FormUtils.setFormValues("frmPreDailyTransferDetail", new DailyTransferVO(), 
																					 this, mapping, request);
			StringBuffer sbfId = new StringBuffer("|");
			sbfId.append(populateCheckBoxId(request, (TableData)request.getSession().getAttribute("tableData")));
			sbfId.append("|");
			frmPreDailyTransferDetail.set("frmSelectedIds", sbfId.toString());			
			alPreDailyTranDetList.add(sbfId.toString());
			
			alPreDailyTranDetList.add(strExclude);
			alPreDailyTranDetList.add((Long)TTKCommon.getUserSeqId(request));
			
			//get the session bean from the bean pool for each executing thread
			TDSRemittanceManager tdsRemittanceManager=this.getTDSRemittanceManagerObject();
			// calling business layer to save the bank account detail
			int iUpdate=tdsRemittanceManager.setInclExclTdsClaims(alPreDailyTranDetList);
			log.info("iUpdate :"+iUpdate);	
			TableData tableData = null;
			if(request.getSession().getAttribute("tableData")!=null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableData");
			}//end of if(request.getSession().getAttribute("tableData")!=null)
			ArrayList<Object> alTdsClaismList = this.getTdsClaismList(request);
			ArrayList<Object> alDailyTransferList = null;
			int iCount=TTKCommon.deleteStringLength((String)alTdsClaismList.get(0), "|");
			alDailyTransferList = tdsRemittanceManager.getIncludExcludTdsClms(tableData.getSearchData());
			//fetch the data from previous set of rowcounts, if all the records are deleted for the current 
			//set of search criteria
			if(alDailyTransferList.size() == 0 || iCount == tableData.getData().size())
			{
				tableData.modifySearchData("Delete");//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
																			getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alDailyTransferList = tdsRemittanceManager.getIncludExcludTdsClms(tableData.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if(alHospitalList.size() == 0 || iCount == tableData.getData().size())
			else
			{
				alDailyTransferList = tdsRemittanceManager.getIncludExcludTdsClms(tableData.getSearchData());
			}//end of else
			tableData.setData(alDailyTransferList, "Delete");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			return this.getForward(strPreDailyTransferList, mapping, request);
		}//end of try		
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strPreDailyTransfer));
		}//end of catch(Exception exp)
	}//end of doGenerateDailyTransfer(ActionMapping mapping,ActionForm form,HttpServletRequest request,
							//HttpServletResponse response)
	
	/**	@description This method returns ArrayList of TDS Claims id's and user sequence 
	 * id which have been choosen by using Check box(s).
	 * 	@param HttpServletRequest request
	 * 	@return ArrayList
	 */
	private ArrayList<Object> getTdsClaismList(HttpServletRequest request) {
		ArrayList <Object>alTdsClaismList=new ArrayList<Object>();
		StringBuffer sbfId=new StringBuffer();
		String strChOpt[]=request.getParameterValues("chkopt");
		TableData tableData=(TableData)request.getSession().getAttribute("tableData");
		if((strChOpt!=null)&&strChOpt.length!=0)
		{
			if(request.getParameter("mode").equals("doRemove"))
			{
				for(int intCounter=0;intCounter<strChOpt.length;intCounter++)
				{
					if(strChOpt[intCounter]!=null)
					{
						if(intCounter==0){
							sbfId.append(String.valueOf(((DailyTransferVO)tableData.getRowInfo(Integer.parseInt
									(strChOpt[intCounter]))).getMasterSeqID()));
						}//end of if(intCounter==0)
						else{
							sbfId=sbfId.append("|").append(String.valueOf(((DailyTransferVO)tableData.getRowInfo
									(Integer.parseInt(strChOpt[intCounter]))).getMasterSeqID()));
						}//end of else
					} // end of if(strChOpt[intCounter]!=null)
					
				} //end of for loop
			} //if(request.getParameter("mode").equals(strDisassociate))
			else
			{
				for(int intCounter=0;intCounter<strChOpt.length;intCounter++)
				{
					if(strChOpt[intCounter]!=null)
					{
						if(intCounter==0){
							sbfId.append(String.valueOf(((DailyTransferVO)tableData.getRowInfo(Integer.parseInt
									(strChOpt[intCounter]))).getMasterSeqID()));
						}//end of if(intCounter==0)
						else{
							sbfId=sbfId.append("|").append(String.valueOf(((DailyTransferVO)tableData.getRowInfo(
									Integer.parseInt(strChOpt[intCounter]))).getMasterSeqID()));
						}//end of else
					} // end of if(strChOpt[intCounter]!=null)
				} // end of for loop
			} // end of else
		}//end of if((strChOpt!=null)&&strChOpt.length!=0)		
		alTdsClaismList.add("|"+sbfId+"|");
		alTdsClaismList.add(TTKCommon.getUserSeqId(request));
		return alTdsClaismList;
	}//end of getHospList(HttpServletRequest request)
	
	/**
	 * Returns a string which contains the pipe separated sequence id's 
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the comma separated sequence id's to be deleted
	 */
	private String populateCheckBoxId(HttpServletRequest request, TableData tableData) throws TTKException
	{
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfId = new StringBuffer();
		try{
			if(strChk!=null&&strChk.length!=0)
			{
				//loop through to populate sequence id's and get the value from session for the matching check
				//box value
				for(int i=0; i<strChk.length;i++)
				{
					if(strChk[i]!=null)
					{
						//extract the sequence id from the value object
						if(i == 0)
						{
							sbfId.append(((DailyTransferVO)tableData.getData().get(Integer.parseInt(strChk[i]))).
									getMasterSeqID().intValue());
						}//end of if(i == 0)
						else
						{
							sbfId.append("|").append(((DailyTransferVO)tableData.getData().get(Integer.parseInt(strChk[i]))).
									getMasterSeqID().intValue());
						}//end of else
					}//end of if(strChk[i]!=null)
				}//end of for(int i=0; i<strChk.length;i++)
			}//end of if(strChk!=null&&strChk.length!=0)
		}catch(Exception exp)
		{
			throw new TTKException(exp, strPreDailyTransfer);
		}//end of catch
		return sbfId.toString();
	}//end of populateCheckBoxId(HttpServletRequest request, TableData tableData)
		
}//end of DailyTransferAction