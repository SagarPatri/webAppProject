/**
 * @ (#) PackageListAction.java May 5, 2006
 * Project      : TTK HealthCare Services
 * File         : PackageListAction.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : May 5, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.preauth;

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
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.CodingClaimsWebBoardHelper;
import com.ttk.common.CodingWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.TariffItemVO;
import formdef.plugin.util.FormUtils;

/**
 * This class is reusable for searching Package Information in pre-auth and claims flow.
 * This class also provides option for selecting the package information.
 */

public class PackageListAction extends TTKAction{

	private static Logger log = Logger.getLogger( PackageListAction.class );

	private static final String strForward ="Forward";
	private static final String strBackward ="Backward";

	private static final String strPreauth="Pre-Authorization";
	private static final String strClaims="Claims";
	private static final String strPackageList="packagelist";
	private static final String strIcdPcsCoding="icdpcscoding";
	private static final String strClaimsPackageList="claimspackagelist";
	private static final String strClaimsIcdPcsCoding="claimsicdpcscoding";
	private static final String strCodePackageList="codepackagelist";
	private static final String strCodeIcdPcsCoding="codeicdpcscoding";
	private static final String strCodeClaimsPackageList="codeclaimspackagelist";
	private static final String strCodeClaimsIcdPcsCoding="codeclaimsicdpcscoding";

	//Exception Message Identifier
    private static final String strPackageError="tariffitem";

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
																HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside PackageListAction doDefault");
			String strPath="";
			TableData packageListData = null;
			if((request.getSession()).getAttribute("packageListData") != null){
				packageListData = (TableData)(request.getSession()).getAttribute("packageListData");
			}//end of if((request.getSession()).getAttribute("packageListData") != null)
			else{
				packageListData = new TableData();
			}//end of else
			packageListData = new TableData();  //create new table data object
			packageListData.createTableInfo("PackageListTable",new ArrayList());     //create the required grid table
			request.getSession().setAttribute("packageListData",packageListData);
			((DynaActionForm)form).initialize(mapping); //reset the form data
			strPath = this.getForwardPath(request);
			getCaption((DynaActionForm)form,request);
			return this.getForward(strPath, mapping, request);
		}// end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strPackageError));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
																HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			String strPath="";
			log.debug("Inside PackageListAction doSearch");
			TariffManager tariffItemObject=this.getTariffItemObject();  //get the session bean from the bean pool
			TableData packageListData = null;
			if((request.getSession()).getAttribute("packageListData") != null){
				packageListData = (TableData)(request.getSession()).getAttribute("packageListData");
			}//end of if((request.getSession()).getAttribute("packageListData") != null)
			else{
				packageListData = new TableData();
			}//end of else
			strPath = this.getForwardPath(request);
			//clear the dynaform if visiting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			getCaption((DynaActionForm)form,request);
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					packageListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(
																					request.getParameter("pageId"))));
					return this.getForward(strPath, mapping, request);
				}///end of if(!strPageID.equals(""))
				else
				{
					packageListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					packageListData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				packageListData.createTableInfo("PackageListTable",null);
				packageListData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				packageListData.modifySearchData("search");
			}//end of else
			//fetch the data from the data access layer and set the data to table object
			ArrayList alTariffItem= tariffItemObject.getTariffItemList(packageListData.getSearchData(),
																						"Pre-Authorization");
			packageListData.setData(alTariffItem, "search");
			//set the table data object to session
			request.getSession().setAttribute("packageListData",packageListData);
			return this.getForward(strPath, mapping, request);
		}// end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strPackageError));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
														HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			String strPath="";
			log.debug("Inside PackageListAction doForward");
			TariffManager tariffItemObject=this.getTariffItemObject();  //get the session bean from the bean pool
			TableData packageListData = null;
			if((request.getSession()).getAttribute("packageListData") != null){
				packageListData = (TableData)(request.getSession()).getAttribute("packageListData");
			}//end of if((request.getSession()).getAttribute("packageListData") != null)
			else{
				packageListData = new TableData();
			}//end of else
			packageListData.modifySearchData(strForward);//modify the search data
			ArrayList alTariffItem= tariffItemObject.getTariffItemList(packageListData.getSearchData(),
																							"Pre-Authorization");
			packageListData.setData(alTariffItem, strForward);//set the table data
			request.getSession().setAttribute("packageListData",packageListData);//set the table data object to session
			//finally return to the grid screen
			strPath = this.getForwardPath(request);
			getCaption((DynaActionForm)form,request);
			return this.getForward(strPath, mapping, request);
		}// end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strPackageError));
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
	public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			String strPath="";
			log.debug("Inside PackageListAction doBackward");
			TariffManager tariffItemObject=this.getTariffItemObject();  //get the session bean from the bean pool
			TableData packageListData = null;
			if((request.getSession()).getAttribute("packageListData") != null){
				packageListData = (TableData)(request.getSession()).getAttribute("packageListData");
			}//end of if((request.getSession()).getAttribute("packageListData") != null)
			else{
				packageListData = new TableData();
			}//end of else
			packageListData.modifySearchData(strBackward);//modify the search data
			ArrayList alTariffItem= tariffItemObject.getTariffItemList(packageListData.getSearchData(),
																								"Pre-Authorization");
			packageListData.setData(alTariffItem, strBackward);//set the table data
			request.getSession().setAttribute("packageListData",packageListData);//set the table data object to session
			//finally return to the grid screen
			strPath = this.getForwardPath(request);
			getCaption((DynaActionForm)form,request);
			return this.getForward(strPath, mapping, request);
		}// end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strPackageError));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to get the select package.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectPackage(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			String strLinks=TTKCommon.getActiveLink(request);
			String strSubLinks=TTKCommon.getActiveSubLink(request);
			String strPath="";
			log.debug("Inside PackageListAction doSelectPackage");
			TableData packageListData = null;
			if((request.getSession()).getAttribute("packageListData") != null){
				packageListData = (TableData)(request.getSession()).getAttribute("packageListData");
			}//end of if((request.getSession()).getAttribute("packageListData") != null)
			else{
				packageListData = new TableData();
			}//end of else
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				TariffItemVO tariffItemVO=(TariffItemVO)packageListData.getRowInfo(Integer.parseInt((String)
																				request.getParameter("rownum")));
				DynaActionForm frmICDPCSCoding= (DynaActionForm)request.getSession().getAttribute("frmICDPCSCoding");
				if(frmICDPCSCoding!=null)
				{
					frmICDPCSCoding.set("tariffItemVO",FormUtils.setFormValues("frmAssociate",tariffItemVO,this,
																								mapping,request));
				}//end of if(frmICDPCSCoding!=null)
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			if(strLinks.equals(strPreauth))
			{
				strPath=strIcdPcsCoding;
			}//end of if(strLinks.equals(strPreauth))
			if(strLinks.equals(strClaims))
			{
				strPath=strClaimsIcdPcsCoding;
			}//end of if(strLinks.equals(strClaims))
			if(strLinks.equals("Coding"))
			{
				if(strSubLinks.equals("PreAuth")){
					strPath=strCodeIcdPcsCoding;
				}//end of if(strSubLinks.equals("PreAuth"))
				else if(strSubLinks.equals("Claims")){
					strPath=strCodeClaimsIcdPcsCoding;
				}//end of else if(strSubLinks.equals("Claims"))
			}//end of if(strLinks.equals("Coding"))
			return this.getForward(strPath, mapping, request);
		}// end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strPackageError));
		}//end of catch(Exception exp)
	}//end of doSelectPackage(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	/**
     * Returns a string which contains the Forward Path
     * @param request HttpServletRequest object which contains information required for buiding the Forward Path
     * @return String which contains the comma separated sequence id's to be deleted
     */
    private String getForwardPath(HttpServletRequest request) throws TTKException{
    	String strLinks=TTKCommon.getActiveLink(request);
    	String strSubLinks=TTKCommon.getActiveSubLink(request);
		String strPath="";
    	try{
    		if(strLinks.equals(strPreauth))
			{
				strPath=strPackageList;
			}//end of if(strLinks.equals(strPreauth))
			if(strLinks.equals(strClaims))
			{
				strPath=strClaimsPackageList;
			}//end of if(strLinks.equals(strClaims))
			if(strLinks.equals("Coding"))
			{
				if(strSubLinks.equals("PreAuth")){
					strPath=strCodePackageList;
				}//end of if(strSubLinks.equals("PreAuth"))
				else if(strSubLinks.equals("Claims")){
					strPath=strCodeClaimsPackageList;
				}//end of else if(strSubLinks.equals("Claims"))
			}//end of if(strLinks.equals("Coding"))
    	}//end of try
    	catch(Exception exp)
        {
            throw new TTKException(exp, strPackageError);
        }//end of catch
        return strPath;
    }//end of getForwardPath(HttpServletRequest request)
    
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
			StringBuffer sbfCaption= new StringBuffer();
			if(strLink.equals(strPreauth))
			{
				sbfCaption.append("List of Packages - [ ").append(PreAuthWebBoardHelper.getClaimantName(
					request)).append(" ] [ ").append(PreAuthWebBoardHelper.getWebBoardDesc(request)).append(" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
			else if(strLink.equals(strClaims))
			{
				sbfCaption.append("List of Packages - [ ").append(ClaimsWebBoardHelper.
								  getClaimantName(request)).append(" ] [ ").append(ClaimsWebBoardHelper.
										  						getWebBoardDesc(request)).append(" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
			if(strLink.equals("Coding"))
			{
				if(strSubLink.equals("PreAuth")){
					sbfCaption.append("List of Packages - [ ").append(CodingWebBoardHelper.getClaimantName(
							request)).append(" ] [ ").append(CodingWebBoardHelper.getWebBoardDesc(request)).append(" ]");
					if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
					{
	                	sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims")){
					sbfCaption.append("List of Packages - [ ").append(CodingClaimsWebBoardHelper.
							  getClaimantName(request)).append(" ] [ ").append(CodingClaimsWebBoardHelper.
									  						getWebBoardDesc(request)).append(" ]");
					if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
					{
	                	sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding"))
			formSearchAssociate.set("caption",String.valueOf(sbfCaption));
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strPackageError);
		}//end of catch
	}// end of getCaption(DynaActionForm frmOffice,HttpServletRequest request)

	/**
	 * Returns the ArrayList which contains the populated search criteria elements.
	 * @param frmPackageSearch DynaActionForm will contains the values of corresponding fields.
	 * @param request HttpServletRequest object which contains the search parameter that is built.
	 * @return alSearchParams ArrayList.
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmPackageSearch,HttpServletRequest request) throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		String strLink=TTKCommon.getActiveLink(request);
		if(strLink.equals(strPreauth)){
			alSearchParams.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
			alSearchParams.add(null);
		}//end of if(strLink.equals(strPreauth))
		else if(strLink.equals(strClaims)){
			alSearchParams.add(null);
			alSearchParams.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
		}//end of else if(strLink.equals(strClaims))
		
		alSearchParams.add(TTKCommon.replaceSingleQots((String)	frmPackageSearch.get("sPackageName")));		
		
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmPackageSearch)

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
			throw new TTKException(exp, strPackageError);
		}//end of catch(Exception exp)
		return tariffItem;
	}//end getTariffItemObject()
}//end of PackageListAction