/**
 * @ (#) AssociateProcedureAction.javaOct 19, 2005
 * Project      : Vidal Health TPA Services
 * File         : AssociateProcedureAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Oct 19, 2005
 *
 * @author       : Chandrasekaran J
 * Modified by   : Lancy A
 * Modified date : Mar 11, 2006
 * Reason        : Changes in coding standards
 */

package com.ttk.action.administration;

import java.util.ArrayList;
import java.util.StringTokenizer;

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
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.CodingClaimsWebBoardHelper;
import com.ttk.common.CodingWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ProcedureDetailVO;

/**
 * This class is used for searching the Associated Procedures.
 */
public class AssociateProcedureAction extends TTKAction
{
	private static Logger log = Logger.getLogger( AssociateProcedureAction.class );
	
	//Modes.
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	
	// Action mapping forwards.
	private static final String strAdministrationAssociates="associates";
	private static final String strPreAuthAssociates="preauthassociates";
	private static final String strCodePreAuthAssociates="codepreauthassociates";
	private static final String strClaimsAssociates="claimsassociates";
	private static final String strCodeClaimsAssociates="codeclaimsassociates";
	private static final String strEditTariff="edittariff";
	private static final String strIcsCoding="icdpcscoding";
	private static final String strClaimsIcsCoding="claimsicdpcscoding";
	private static final String strCodeIcsCoding="codeicdpcscoding";
	private static final String strCodeclaimsicdpcs="codeclaimsicdpcs";
	private static final String strPreAuthCleanupAssociates="preauthcleanupassociates";
	private static final String strClaimsCleanupAssociates="claimscleanupassociates";
	private static final String strCloseCleanupPreauth="closecleanuppreauth";
	private static final String strCloseCleanupClaims="closecleanupclaims";
	//string for comparision
	private static final String strAdministration="Administration";
	private static final String strPreAuthorization="Pre-Authorization";
	private static final String strClaims="Claims";
	
	//Exception Message Identifier
	private static final String strAssociateExp="associate";
	
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
			log.debug("Inside the doDefault method of AssociateProcedureAction");
			setLinks(request);
			DynaActionForm formSearchAssociate= (DynaActionForm) form;
			TableData tableDataProcedure=null;
			//Check whether tableDataProcedure is null
			if(request.getSession().getAttribute("tableDataProcedure")!=null) 
			{
				tableDataProcedure= (TableData)(request.getSession()).getAttribute("tableDataProcedure");
			}//end of if(request.getSession().getAttribute("tableDataProcedure")!=null)
			else
			{
				 //Creating the new Instance of the TableData
				tableDataProcedure=new TableData();
			}//end of else
			//create new table data object
			tableDataProcedure = new TableData();
			//create the required grid table
			tableDataProcedure.createTableInfo("AssociateProcedureTable",new ArrayList());
			request.getSession().setAttribute("tableDataProcedure",tableDataProcedure);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			getCaption(formSearchAssociate,request);
			return this.getForward(getForwardMapping(request), mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAssociateExp));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside the doSearch method of AssociateProcedureAction");
			setLinks(request);
			DynaActionForm formSearchAssociate= (DynaActionForm) form;
			TableData tableDataProcedure=null;
			//Check whether tableDataProcedure is null
			if(request.getSession().getAttribute("tableDataProcedure")!=null) 
			{
				tableDataProcedure= (TableData)(request.getSession()).getAttribute("tableDataProcedure");
			}//end of if(request.getSession().getAttribute("tableDataProcedure")!=null)
			else
			{
				//Creating the new Instance of the TableData
				tableDataProcedure=new TableData();
			}//end of else
			TariffManager tariffPlanObject=this.getTariffPlanManagerObject();
			//get the procedure code which are already associated
			String strAsscProcCode=getAsscProcedureCode(request);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(strPageID.equals(""))
				{
					tableDataProcedure.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					//modify the search data
					tableDataProcedure.modifySearchData("sort");					
				}///end of if(!strPageID.equals(""))
				else
				{
					tableDataProcedure.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.
							getParameter("pageId"))));
					return mapping.findForward(getForwardMapping(request));
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableDataProcedure.createTableInfo("AssociateProcedureTable",null);
				tableDataProcedure.setSearchData(this.populateSearchCriteria((DynaActionForm)form, 
																	   strAsscProcCode, request));
				tableDataProcedure.modifySearchData("search");
			}//end of else
			ArrayList alAssociate= tariffPlanObject.getProcedureList(tableDataProcedure.getSearchData());
			tableDataProcedure.setData(alAssociate, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableDataProcedure",tableDataProcedure);
			getCaption(formSearchAssociate,request);
			//finally return to the grid screen
			return this.getForward(getForwardMapping(request), mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAssociateExp));
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
			log.debug("Inside the doBackward method of AssociateProcedureAction");
			setLinks(request);
			TableData tableDataProcedure=null;
			//Check whether tableDataProcedure is null
			if(request.getSession().getAttribute("tableDataProcedure")!=null) 
			{
				tableDataProcedure= (TableData)(request.getSession()).getAttribute("tableDataProcedure");
			}//end of if(request.getSession().getAttribute("tableDataProcedure")!=null)
			else
			{
				//Creating the new Instance of the TableData
				tableDataProcedure=new TableData();
			}//end of else
			TariffManager tariffPlanObject=this.getTariffPlanManagerObject();
			//modify the search data
			tableDataProcedure.modifySearchData(strBackward);
			ArrayList alAssociate = tariffPlanObject.getProcedureList(tableDataProcedure.getSearchData());
			//set the table data
			tableDataProcedure.setData(alAssociate, strBackward);
			//set the table data object to session
			request.getSession().setAttribute("tableDataProcedure",tableDataProcedure);
			//finally return to the grid screen
			return this.getForward(getForwardMapping(request), mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAssociateExp));
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
			log.debug("Inside the doForward method of AssociateProcedureAction");
			setLinks(request);
			TableData tableDataProcedure=null;
			//Check whether tableDataProcedure is null
			if(request.getSession().getAttribute("tableDataProcedure")!=null) 
			{
				tableDataProcedure= (TableData)(request.getSession()).getAttribute("tableDataProcedure");
			}//end of if(request.getSession().getAttribute("tableDataProcedure")!=null)
			else
			{
				//Creating the new Instance of the TableData
				tableDataProcedure=new TableData();
			}//end of else
			TariffManager tariffPlanObject=this.getTariffPlanManagerObject();
			//modify the search data
			tableDataProcedure.modifySearchData(strForward);
			ArrayList alAssociate = tariffPlanObject.getProcedureList(tableDataProcedure.getSearchData());
			//set the table data
			tableDataProcedure.setData(alAssociate, strForward);
			//set the table data object to session
			request.getSession().setAttribute("tableDataProcedure",tableDataProcedure);
			//finally return to the grid screen
			return this.getForward(getForwardMapping(request), mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAssociateExp));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
	/**
	 * This method is used to associate the procedure to previous screen.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAssociateCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														   HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doAssociateCode method of AssociateProcedureAction");
			setLinks(request);
			TableData tableDataProcedure=null;
			//Check whether tableDataProcedure is null
			if(request.getSession().getAttribute("tableDataProcedure")!=null) 
			{
				tableDataProcedure= (TableData)(request.getSession()).getAttribute("tableDataProcedure");
			}//end of if(request.getSession().getAttribute("tableDataProcedure")!=null)
			else
			{
				//Creating the new Instance of the TableData
				tableDataProcedure=new TableData();
			}//end of else
			TariffManager tariffPlanObject=this.getTariffPlanManagerObject();
			//get the procedure code which are already associated
			String strAsscProcCode=getAsscProcedureCode(request);
			ArrayList alNewAsscCode= populateAssociatedCode(request,(TableData)request.getSession().
																getAttribute("tableDataProcedure"));
			int iCount = 0;
			iCount=alNewAsscCode.size();
			//call modifyProcedureCode method to add new selected procedure code to session
			modifyProcedureCode(request,alNewAsscCode);
			//call getAsscProcedureCode to get procedure code which are associated
			strAsscProcCode=getAsscProcedureCode(request);
			ArrayList<Object> alSearchParams = tableDataProcedure.getSearchData();
			if(alSearchParams != null && alSearchParams.size() > 0)
			{
				alSearchParams.set(2, strAsscProcCode);
			}//end of if(alSearchParams != null && alSearchParams.size() > 0)
			ArrayList alAssociate=tariffPlanObject.getProcedureList(tableDataProcedure.getSearchData());
			//fetch the data from previous set of rowcounts, if all records are deleted for the 
			//current set of rowcounts
			if(alAssociate.size() == 0 || iCount == tableDataProcedure.getData().size())
			{
				int iStartRowCount = 0;
				//modify the search data
				tableDataProcedure.modifySearchData("Delete");
				iStartRowCount = Integer.parseInt((String)tableDataProcedure.getSearchData().
										   get(tableDataProcedure.getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alAssociate = tariffPlanObject.getProcedureList(tableDataProcedure.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if
			tableDataProcedure.setData(alAssociate, "Delete");
			//set the table data object to session
			request.getSession().setAttribute("tableDataProcedure",tableDataProcedure);
			return this.getForward(getForwardMapping(request), mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAssociateExp));
		}//end of catch(Exception exp)
	}//end of doAssociateCode(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			log.debug("Inside the doClose method of AssociateProcedureAction");
			setLinks(request);
			String strLink=TTKCommon.getActiveLink(request);
			String strSubLinks=TTKCommon.getActiveSubLink(request);
			String strPath="";
			DynaActionForm frmTariffItem=(DynaActionForm)request.getSession().getAttribute("frmTariffItem");
			DynaActionForm frmICDPCSCoding=(DynaActionForm)request.getSession().getAttribute("frmICDPCSCoding");
			if(strLink.equals(strAdministration))
			{
				modifyDeletedSeqId(request,(ArrayList)frmTariffItem.get("asscCodes"));
				frmTariffItem.set("frmChanged","changed");
				strPath=strEditTariff;
			}// end of if(strLink.equals(strAdministration))
			if(strLink.equals(strPreAuthorization))
			{
				modifyDeletedSeqId(request,(ArrayList)frmICDPCSCoding.get("asscCodes"));
				strPath=strIcsCoding;
			}//end of if(strLink.equals(strPreAuthorization))
			if(strLink.equals(strClaims))
			{
				modifyDeletedSeqId(request,(ArrayList)frmICDPCSCoding.get("asscCodes"));
				strPath=strClaimsIcsCoding;
			}//end of if(strLink.equals(strClaims))
			else if(strLink.equals("Coding"))
			{
				modifyDeletedSeqId(request,(ArrayList)frmICDPCSCoding.get("asscCodes"));
				if(strSubLinks.equals("PreAuth")){
					strPath=strCodeIcsCoding;
				}//end of if(strSubLinks.equals("PreAuth"))
				else if(strSubLinks.equals("Claims")){
					strPath=strCodeclaimsicdpcs;
				}//end of else if(strSubLinks.equals("Claims"))
			}//end of else if(strLink.equals("Coding"))
			else if(strLink.equals("Code CleanUp"))
			{
				modifyDeletedSeqId(request,(ArrayList)frmICDPCSCoding.get("asscCodes"));
				if(strSubLinks.equals("PreAuth")){
					strPath=strCloseCleanupPreauth;
				}//end of if(strSubLinks.equals("PreAuth"))
				else if(strSubLinks.equals("Claims")){
					strPath=strCloseCleanupClaims;
				}//end of else if(strSubLinks.equals("Claims"))
			}//end of else if(strLink.equals("Code CleanUp"))
			return this.getForward(strPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAssociateExp));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to manipulate the action forwards used in the screens.
	 * @param request The HTTP request we are processing
	 * @throws Exception if any error occurs
	 */    
	private String getForwardMapping(HttpServletRequest request)throws TTKException
	{
		String strLink=TTKCommon.getActiveLink(request);
		String strSubLinks=TTKCommon.getActiveSubLink(request);
		String strAssociates="";
		if(strLink.equals(strAdministration))
		{
			strAssociates=strAdministrationAssociates;
		}//end of if(strLink.equals(strAdministration))
		else if(strLink.equals(strPreAuthorization))
		{
			strAssociates=strPreAuthAssociates;
		}//end of else if(strLink.equals(strPreAuthorization))
		else if(strLink.equals(strClaims))
		{
			strAssociates=strClaimsAssociates;
		}//end of else if(strLink.equals(strPreAuthorization))
		else if(strLink.equals("Coding"))
		{
			if(strSubLinks.equals("PreAuth")){
				strAssociates=strCodePreAuthAssociates;
			}//end of if(strSubLinks.equals("PreAuth"))
			else if(strSubLinks.equals("Claims")){
				strAssociates=strCodeClaimsAssociates;
			}//end of else if(strSubLinks.equals("Claims"))
		}//end of else if(strLink.equals("Coding"))
		else if(strLink.equals("Code CleanUp"))
		{
			if(strSubLinks.equals("PreAuth")){
				strAssociates=strPreAuthCleanupAssociates;
			}//end of if(strSubLinks.equals("PreAuth"))
			else if(strSubLinks.equals("Claims")){
				strAssociates=strClaimsCleanupAssociates;
			}//end of else if(strSubLinks.equals("Claims"))
		}//end of else if(strLink.equals("Code CleanUp"))
		return strAssociates;
	}//end of getForwardMapping(HttpServletRequest request)
	
	/**
	 * This method is used to manipulate the caption used in the screens.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @throws Exception if any error occurs
	 */    
	private void getCaption(DynaActionForm formSearchAssociate,HttpServletRequest request)throws TTKException
	{
		try
		{
			String strLink=TTKCommon.getActiveLink(request);
			String strSubLink=TTKCommon.getActiveSubLink(request);
			DynaActionForm formTariffItem = (DynaActionForm)request.getSession().getAttribute("frmTariffItem");
			StringBuffer sbfCaption= new StringBuffer();
			if(strLink.equals(strAdministration))
			{
				sbfCaption.append("Associate Procedures - [ ").append(formTariffItem.
												 get("tariffItemName")).append(" ] ");
			}//end of if(strLink.equals(strAdministration)&& strSubLink.equals(strTariffPlan))
			else if(strLink.equals(strPreAuthorization))
			{
				sbfCaption.append("Associate Procedures - [ ").append(PreAuthWebBoardHelper.getClaimantName(
					request)).append(" ] [ ").append(PreAuthWebBoardHelper.getWebBoardDesc(request)).append(" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
			else if(strLink.equals(strClaims))
			{
				sbfCaption.append("Associate Procedures - [ ").append(ClaimsWebBoardHelper.
								  getClaimantName(request)).append(" ] [ ").append(ClaimsWebBoardHelper.
										  						getWebBoardDesc(request)).append(" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
			if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			{
				if(strSubLink.equals("PreAuth")){
					sbfCaption.append("Associate Procedures - [ ").append(CodingWebBoardHelper.getClaimantName(
							request)).append(" ] [ ").append(CodingWebBoardHelper.getWebBoardDesc(request)).append(" ]");
					if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
					{
	                	sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims")){
					sbfCaption.append("Associate Procedures - [ ").append(CodingClaimsWebBoardHelper.
							  getClaimantName(request)).append(" ] [ ").append(CodingClaimsWebBoardHelper.
									  						getWebBoardDesc(request)).append(" ]");
					if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
					{
	                	sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			formSearchAssociate.set("caption",String.valueOf(sbfCaption));
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strAssociateExp);
		}//end of catch
	}// end of getCaption(DynaActionForm frmOffice,HttpServletRequest request)
	
	/**
	 * Returns the ArrayList which contains the populated search criteria elements.
	 * @param frmSearchAssociate DynaActionForm will contains the values of corresponding fields.
	 * @param strAsscProcCode String which contain already associated procedure codes.
	 * @param request HttpServletRequest object which contains the search parameter that is built.
	 * @return alSearchParams ArrayList.
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSearchAssociate,String strAsscProcCode, 
																		   HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		
		/*alSearchParams.add(new SearchCriteria("PROC_CODE",TTKCommon.replaceSingleQots((String)
												   frmSearchAssociate.get("procedurecode"))));
		alSearchParams.add(new SearchCriteria("PROC_DESCRIPTION",TTKCommon.replaceSingleQots((String)
														  frmSearchAssociate.get("procedurename"))));*/
		
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchAssociate.get("procedurecode")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmSearchAssociate.get("procedurename")));
		alSearchParams.add(strAsscProcCode);
		alSearchParams.add(TTKCommon.getUserSeqId(request));   //current logged in user seq id
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmSearchAssociate,String strAsscProcCode, 
		//HttpServletRequest request)
	
	/**
	 * Returns the TariffManager session object for invoking methods on it.
	 * @return TariffManager session object which can be used for method invokation.
	 * @exception throws TTKException.
	 */
	private TariffManager getTariffPlanManagerObject() throws TTKException
	{
		TariffManager tariffPlanManager = null;
		try
		{
			if(tariffPlanManager == null)
			{
				InitialContext ctx = new InitialContext();
				tariffPlanManager = (TariffManager) ctx.lookup("java:global/TTKServices/business.ejb3/TariffManagerBean!com.ttk.business.administration.TariffManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp,"associate");
		}//end of catch
		return tariffPlanManager;
	}//end getTariffPlanManagerObject()
	
	/**
	 * Returns a String which contain the comma separated procedure code which are associated.
	 * @param request HttpServletRequest object which contain information about associated procedure code.
	 * @return String which contains the comma separated procedure code which are associated.
	 */
	private String getAsscProcedureCode(HttpServletRequest request) throws TTKException
	{
		try
		{
			setLinks(request);
			StringBuffer sbfAsscProcCode= new StringBuffer();
			String strLink=TTKCommon.getActiveLink(request);
			ArrayList alAsscProc=new ArrayList();
			ProcedureDetailVO procDetailVO=null;
			DynaActionForm frmTariffItem=(DynaActionForm)request.getSession().getAttribute("frmTariffItem");
			DynaActionForm frmICDPCSCoding=(DynaActionForm)request.getSession().getAttribute("frmICDPCSCoding");
			DynaActionForm frmICDPCSPolicy=(DynaActionForm)request.getSession().getAttribute("frmICDPCSPolicy");
			if(strLink.equals(strAdministration))
			{
				alAsscProc=(ArrayList)frmTariffItem.get("asscCodes");
			}//END OF if(strLink.equals(strAdministration)&& strSubLink.equals(strTariffPlan))
			else if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			{
				alAsscProc=(ArrayList)frmICDPCSPolicy.get("asscCodes");
			}//end of else if(strLink.equals("coding"))
			else
			{
				alAsscProc=(ArrayList)frmICDPCSCoding.get("asscCodes");
			}// end of else
			if(alAsscProc!=null)
			{
				for(int i=0;i<alAsscProc.size();i++)
				{
					procDetailVO=(ProcedureDetailVO)alAsscProc.get(i);
					if(!sbfAsscProcCode.toString().equals(""))
					{
						sbfAsscProcCode=sbfAsscProcCode.append(",");
					}//end of if(!sbfAsscProcCode.toString().equals(""))
					/*sbfAsscProcCode= sbfAsscProcCode.append("'").append((String)procDetailVO.
															 getProcedureCode()).append("'");*/
					sbfAsscProcCode= sbfAsscProcCode.append((String)procDetailVO.getProcedureCode());
				}//end of for(int i=0;i<alAsscProc.size();i++)
			}//end of if(alAsscProc!=null)
			return sbfAsscProcCode.toString();
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp,"associate");
		}//end of catch
	}//end of getAsscProcedureCode(HttpServletRequest request)
	
	/**
	 *  Adds the selected procedure code to session object
	 *  @param request HttpServletRequest object which contains information about the selected procedure code
	 *  @param alNewAsscCode ArrayList which contains the new associated procedure code
	 * @throws TTKException
	 */
	private void modifyProcedureCode(HttpServletRequest request,ArrayList<Object>  alNewAsscCode) throws TTKException
	{
		setLinks(request);
		String strLink=TTKCommon.getActiveLink(request);
		ArrayList<Object> alAsscProcedure=new ArrayList<Object>();
		DynaActionForm frmTariffItem=(DynaActionForm)request.getSession().getAttribute("frmTariffItem");
		DynaActionForm frmICDPCSCoding=(DynaActionForm)request.getSession().getAttribute("frmICDPCSCoding");
		DynaActionForm frmICDPCSPolicy=(DynaActionForm)request.getSession().getAttribute("frmICDPCSPolicy");
		if(strLink.equals(strAdministration))
		{
			alAsscProcedure=(ArrayList<Object>)frmTariffItem.get("asscCodes");
		}//END OF if(strLink.equals(strAdministration))

		else if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
		{
			alAsscProcedure=(ArrayList<Object>)frmICDPCSPolicy.get("asscCodes");
		}// end of else
		else
		{
			alAsscProcedure=(ArrayList<Object>)frmICDPCSCoding.get("asscCodes");
		}// end of else
		if(alNewAsscCode!=null)
		{
			for(int i=0;i<alNewAsscCode.size();i++)
			{
				alAsscProcedure.add(alNewAsscCode.get(i));
			}//end of for(int i=0;i<alNewAsscCode.size();i++)
			modifyDeletedSeqId(request,alNewAsscCode);//call the modify the deleted procedure code
		}//end of if(alNewAsscCode!=null)
	}//end of modifyProcedureCode(HttpServletRequest request,ArrayList  alNewAsscCode)
	
	/**
	 * Returns ArrayList which contains the procedure code which are associated
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableDataProcedure TableData which contains the value objects
	 * @return ArrayList which contains the associated procedure codes.
	 */
	private ArrayList populateAssociatedCode(HttpServletRequest request, TableData tableDataProcedure)
	{
		String[] strChk = request.getParameterValues("chkopt");
		ArrayList<Object> alNewAsscCode=new ArrayList<Object>();
		if(strChk!=null&&strChk.length!=0)
		{
			for(int i=0; i<strChk.length;i++)
			{
				alNewAsscCode.add(tableDataProcedure.getData().get(Integer.parseInt(strChk[i])));
			}//end of for(int i=0; i<strChk.length;i++)
		}//end of if(strChk!=null&&strChk.length!=0)
		return alNewAsscCode;
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)
	
	/**
	 * Modifies the deleted procedure code
	 * @param request HttpServletRequest object which contains information about the deleted procedure code
	 * @param alNewAsscCode ArrayList which contains the information of procedure code which are associated
	 * @throws TTKException
	 */
	private void modifyDeletedSeqId(HttpServletRequest request,ArrayList alNewAsscCode) throws TTKException
	{
		log.debug("INSIDE THE MODFY DELETED SEQID :");
		String strLink=TTKCommon.getActiveLink(request);
		DynaActionForm frmTariffItem=(DynaActionForm)request.getSession().getAttribute("frmTariffItem");
		DynaActionForm frmICDPCSCoding=(DynaActionForm)request.getSession().getAttribute("frmICDPCSCoding");
		DynaActionForm frmICDPCSPolicy=(DynaActionForm)request.getSession().getAttribute("frmICDPCSPolicy");
		String strDeleteSeqId="";
		if(strLink.equals(strAdministration))
		{
			strDeleteSeqId=(String)frmTariffItem.get("deleteSeqId");
		}// end of if(strLink.equals(strAdministration))
		else if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
		{
			strDeleteSeqId=(String)frmICDPCSPolicy.get("deleteSeqId");
		}//end of else if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
		else
		{
			strDeleteSeqId=(String)frmICDPCSCoding.get("deleteSeqId");
		}//end of else
		StringTokenizer stProcedure=new StringTokenizer(strDeleteSeqId,"|");
		String strNewDeletedId="",strTemp="",strCode="",strKey="";
		ProcedureDetailVO procDetailVO=null;
		boolean bFlag=true;
		if(alNewAsscCode!=null&&alNewAsscCode.size()>0)
		{
			for(int i=0;i<alNewAsscCode.size();i++)
			{
				stProcedure=new StringTokenizer(strDeleteSeqId,"|");
				procDetailVO=(ProcedureDetailVO)alNewAsscCode.get(i);
				strCode= procDetailVO.getProcedureID().toString();
				while(stProcedure.hasMoreTokens())
				{
					bFlag=true;
					strKey=stProcedure.nextToken();
					if(strKey.equals(strCode))
					{
						bFlag=false;
					}//end of if(strKey.equals(strCode))
					if(bFlag)
					{
						strTemp+=strKey+"|";
					}//end of if(bFlag)
				}//end of while
				strNewDeletedId="|"+strTemp;
				strDeleteSeqId=strNewDeletedId;
				strTemp="";
			}//end of for(int i=0;i<alNewAsscCode.size();i++)
		}//end of if(alNewAsscCode!=null)
		else
		{
			strNewDeletedId=strDeleteSeqId;
		}//end of else
		if(strLink.equals(strAdministration))
		{
			frmTariffItem.set("deleteSeqId",strNewDeletedId);
		}// end of if(strLink.equals(strAdministration))
		else if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
		{
			frmICDPCSPolicy.set("deleteSeqId",strNewDeletedId);
		}//end of else
		else
		{
			frmICDPCSCoding.set("deleteSeqId",strNewDeletedId);
		}//end of else
	}//end of modifyDeletedSeqId(HttpServletRequest request,ArrayList alNewAsscCode)
}//end of AssociateProcedureAction