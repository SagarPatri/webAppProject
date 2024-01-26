/**
 * @ (#) ProductSearchSyncAction.java Aug 1, 2007
 * Project      : TTK HealthCare Services
 * File         : ProductSearchSyncAction.java
 * Author       : Raghavendra T M
 * Company      : Span Systems Corporation
 * Date Created : Aug 1, 2007
 *
 * @author       : Raghavendra T M
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.administration;

import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.administration.RuleSynchronizationVO;

/**
 * This class is used for Searching the List of Products.
 * This also provides deletion and updation of products.
 */
public class ProductSearchSyncAction extends TTKAction {
	
	private static Logger log = Logger.getLogger( ProductSearchSyncAction.class );
	
	//Modes.
	private static final String strBackward="Backward";
	private static final String strForward="Forward";    
	
	// Action mapping forwards.
	private static final String strPolicySynclist="policysynclist";
	private static final String strSync="prodsync";
	
	//Exception Message Identifier
	private static final String strProduct="product";
	
	private static final String strTableData = "tableDataSync";
	
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
			log.debug("Inside the doDefault method of ProductSearchSyncAction");
			setLinks(request);
			String strForward = "";
			String strTable = "";
			DynaActionForm frmProductSyncList = (DynaActionForm)form;
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			//get the tbale data from session if exists
			TableData tableData =ProductSearchSyncAction.getTableData(request,strTableData); 
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			strForward = strPolicySynclist;
			strTable = "ProductPolicySyncTable";
			tableData.createTableInfo(strTable,new ArrayList());
			//frmProductSyncList.set("caption",buildCaption(request));
			frmProductSyncList.set("policyStatusIDForProduct", "POA");
			request.getSession().setAttribute(strTableData,tableData);
			return this.getForward(strForward, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
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
			log.debug("Inside the doSearch method of ProductSearchSyncAction");
			setLinks(request);
			String strForward = "";
			String strTable = "";
			strForward = strPolicySynclist;
			strTable = "ProductPolicySyncTable";
			//DynaActionForm frmProductSyncList = (DynaActionForm)form;
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			//get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			//get the tbale data from session if exists
			TableData tableData = ProductSearchSyncAction.getTableData(request, strTableData);
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					request.getSession().setAttribute("pageidindex", strPageID);
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
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alProductList = new ArrayList();
			alProductList = productPolicyObject.getSynchPolicyList(tableData.getSearchData());
			tableData.setData(alProductList, "search");
			//frmProductSyncList.set("caption",buildCaption(request));
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
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
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
			log.debug("Inside the doBackward method of ProductSearchSyncAction");
			setLinks(request);
			String strForward = "";
			strForward = strPolicySynclist;
			//DynaActionForm frmProductSyncList = (DynaActionForm)form;
			//get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			TableData tableData = ProductSearchSyncAction.getTableData(request,strTableData);
			tableData.modifySearchData(strBackward);
			ArrayList alProductList = productPolicyObject.getSynchPolicyList(tableData.getSearchData());
			tableData.setData(alProductList, strBackward);
			request.getSession().setAttribute(strTableData,tableData);	
			//frmProductSyncList.set("caption",buildCaption(request));
			return this.getForward(strForward, mapping, request);	
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
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
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doForward method of ProductSearchSyncAction");
			setLinks(request);
			String strMapForward = "";
			//DynaActionForm frmProductSyncList = (DynaActionForm)form;
			strMapForward = strPolicySynclist;
			//get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			TableData tableData = ProductSearchSyncAction.getTableData(request,strTableData);
			tableData.modifySearchData(strForward);
			ArrayList alProductList = productPolicyObject.getSynchPolicyList(tableData.getSearchData());
			tableData.setData(alProductList, strForward);
			request.getSession().setAttribute(strTableData,tableData);	
			//frmProductSyncList.set("caption",buildCaption(request));
			return this.getForward(strMapForward, mapping, request);	
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	/**
	 * This method is used to navigate to clause screen to view selected record.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAddProductSync(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doAddProductSync method of ProductSearchSyncAction");
			String controlname=request.getParameter("strControlName");
			String flag="";
			String status="";
			String syncCheckedStatus = request.getParameter("");
			HashMap flagMap = new HashMap();
			StringTokenizer st = new StringTokenizer(controlname,"|");
			while(st.hasMoreTokens()){
				flag = st.nextToken();
				status=st.nextToken();
				flagMap.put(flag, status);

			}	
			setLinks(request);
			//get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();

			//get the session bean from the bean pool for each excecuting thread
			TableData tableData = ProductSearchSyncAction.getTableData(request,strTableData);
			String[] strProdPolicySeqIDs = populateProductSeqId(request,tableData);
			String strPolicySeqID = strProdPolicySeqIDs[1]+"|";
			String strDenialsyn="";//denial process
			int iProdSync[] = productPolicyObject.synchronizeRuleGenProdCardRule(strProdPolicySeqIDs,TTKCommon.getWebBoardId(request),strDenialsyn,flagMap,TTKCommon.getUserSeqId(request));//denial process
			if(iProdSync[0] > 0)
			{
				request.setAttribute("updated","message.SyncSuccessfully");
				
				if(iProdSync[1] > 0){
					request.setAttribute("updateSyncFlag","message.SyncFlag");
					
				}
			}//end of if(iProdSync > 0)
		char syncCheckedStatusFlag = controlname.charAt(6);
		strPolicySeqID = strPolicySeqID.substring(0);
		request.setAttribute("syncCheckedStatus", String.valueOf(syncCheckedStatusFlag));
		request.setAttribute("strProdPolicySeqID", strPolicySeqID);
		return mapping.findForward(strSync);   
	
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doViewProducts(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	public ActionForward doAddProductSyncAll(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doAddProductSyncAll method of ProductSearchSyncAction");
			setLinks(request);
			//get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			
			int iProdSync = productPolicyObject.synchronizeRuleAllSchemes(TTKCommon.getWebBoardId(request));//denial process
			if(iProdSync > 0)
			{
				request.setAttribute("updated","message.SyncSuccessfully");
			}//end of if(iProdSync > 0)
			return mapping.findForward(strSync);                   
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doViewProducts(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	//denial process
	public ActionForward doAddInsProductSync(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doAddProductSync method of ProductSearchSyncAction");
			setLinks(request);
			//get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			//get the session bean from the bean pool for each excecuting thread
			TableData tableData = ProductSearchSyncAction.getTableData(request,strTableData);
			String[] strProdPolicySeqIDs = populateProductSeqId(request,tableData);
			String strDenialsyn="DEN";//denial process
			int iProdSync = productPolicyObject.synchronizeRule(strProdPolicySeqIDs,TTKCommon.getWebBoardId(request),strDenialsyn);
			if(iProdSync > 0)
			{
				request.setAttribute("updated","message.SyncSuccessfully");
			}//end of if(iProdSync > 0)
			return mapping.findForward(strSync);                   
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doViewProducts(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	//denial process
	
	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return ProductPolicyManager session object which can be used for method invokation 
	 * @exception throws TTKException  
	 */
	private ProductPolicyManager getProductPolicyManagerObject() throws TTKException
	{
		ProductPolicyManager productPolicyManager = null;
		try 
		{
			if(productPolicyManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyManager = (ProductPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProductPolicyManagerBean!com.ttk.business.administration.ProductPolicyManager");
			}//end if
		}//end of try 
		catch(Exception exp) 
		{
			throw new TTKException(exp, "product");
		}//end of catch
		return productPolicyManager;
	}//end getProductManagerObject()
	
	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmProductList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmProductList,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add((Long)TTKCommon.getWebBoardId(request));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmProductList.get("sPolicyNbr")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmProductList.get("sCorporateName")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmProductList.get("sValidFrom")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmProductList.get("sValidDate")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmProductList.get("policyStatusIDForProduct")));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmProductList)
	
	/**
	 * Returns array of string which contains the pipe separated Product Policy Seq ids
	 *  to be synchronize  
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String[] which contains the pipe separated ProdPolicySeq ids
	 *  to be synchronize  
	 */
	private String[] populateProductSeqId(HttpServletRequest request, TableData tableData)
	{
		String[] strProductSeqIds = {"",""};
		String[] strChk=null;
		String chkopt = request.getParameter("chkoptt");
	
		if(chkopt!=null)
		{
			StringTokenizer st = new StringTokenizer(chkopt,"|");

			while(st.hasMoreTokens()){

				String scase= st.nextToken();
				strProductSeqIds[0] = strProductSeqIds[0] +"|"+ ((RuleSynchronizationVO)tableData.getData().
						get(Integer.parseInt(scase))).getProdPolicySeqId();

				strProductSeqIds[1] = strProductSeqIds[1] +"|"+ ((RuleSynchronizationVO)tableData.getData().
						get(Integer.parseInt(scase))).getPolicySeqID();
			}//end of else


		}
		else
		{
		
			strChk = request.getParameterValues("chkopt");


			if(strChk!=null&&strChk.length!=0)
			{

				//loop through to populate delete sequence id's and get the value from session for the matching 
				//check box value
				for(int i=0; i<strChk.length;i++)
				{
					if(strChk[i]!=null)
					{    
						//extract the sequence id to be deleted from the value object
						if(i == 0)
						{

							strProductSeqIds[0] = String.valueOf(((RuleSynchronizationVO)tableData.getData().
									get(Integer.parseInt(strChk[i]))).getProdPolicySeqId());

							strProductSeqIds[1] = String.valueOf(((RuleSynchronizationVO)tableData.getData().
									get(Integer.parseInt(strChk[i]))).getPolicySeqID());

						}//end of if(i==0)
						else
						{
							strProductSeqIds[0] = strProductSeqIds[0] +"|"+ ((RuleSynchronizationVO)tableData.getData().
									get(Integer.parseInt(strChk[i]))).getProdPolicySeqId();
							strProductSeqIds[1] = strProductSeqIds[1] +"|"+ ((RuleSynchronizationVO)tableData.getData().
									get(Integer.parseInt(strChk[i]))).getPolicySeqID();
						}//end of else
					}//end of if(strChk[i]!=null)
				}//end of for(int i=0; i<strChk.length;i++) 
			}//end of if(strChk!=null&&strChk.length!=0) 
		}
		return strProductSeqIds;
	}//end of populateProductSeqId(HttpServletRequest request, TableData tableData)

	/**
     * This method is to build the Caption  
     * @param request current HttpRequest
     * @return String caption built
     * @throws TTKException
     *//*
    private String buildCaption(HttpServletRequest request)throws TTKException
    {
    	StringBuffer sbfCaption=new StringBuffer();
    	DynaActionForm frmProductList = (DynaActionForm)request.getSession().getAttribute("frmProductList");
		sbfCaption.append("List of Policies - [").append(frmProductList.get("companyName")).
		append("] [").append(TTKCommon.getWebBoardDesc(request)).append("]");
    	return sbfCaption.toString();
    }//end of private String buildCaption(HttpServletRequest request)
*/    
    /**
     * This method returns TableData object retreived from session.
     * If null is retruned from session, creates a new one
     *
     * @param HttpServletRequest request
     * @param String table name
     * @return TableData the TableData object
     */
    public static TableData getTableData(HttpServletRequest request, String tableDate)
    {
        TableData tableConfig = null;
        if((request.getSession()).getAttribute(tableDate) == null)
        {
            tableConfig = new TableData();
        }//end of if((request.getSession()).getAttribute(tableDate) == null)
        else
        {
            tableConfig = (TableData)(request.getSession()).getAttribute(tableDate);
        }//end of else

        return tableConfig;
    }//End of getTableConfig(HttpServletRequest request)
	
    public ActionForward generateLetter(ActionMapping mapping,
    		ActionForm form, HttpServletRequest request,
    		HttpServletResponse response) throws Exception {
    	try {/*
    		setLinks(request);
    		DynaActionForm frmPreAuthGeneral = (DynaActionForm) form;
    		String preAuthSeqID = frmPreAuthGeneral.getString("preAuthSeqID");
    		String authNum = frmPreAuthGeneral.getString("authNum");
    		String preauthStatus = frmPreAuthGeneral.getString("preauthStatus");

    		JasperReport mainJasperReport = null;
    	//	JasperReport diagnosisJasperReport = null;
    	//	JasperReport activityJasperReport = null;
    		JasperPrint mainJasperPrint = null;
    		String parameter = request.getParameter("parameterValue");
    		String mainJrxmlfile = "generalreports/ProductSyncLetter.jrxml";
    	//	String diagnosisJrxmlfile = "generalreports/DiagnosisDoc.jrxml";
    	//	String activityJrxmlfile = "generalreports/ActivitiesDoc.jrxml";
    		TTKReportDataSource mainTtkReportDataSource = null;
    	//	TTKReportDataSource diagnosisTtkReportDataSource = null;
    	//	TTKReportDataSource activityTtkReportDataSource = null;

    		ByteArrayOutputStream boas = new ByteArrayOutputStream();

    	//	String authNum	=	request.getParameter("authorizationNo");
    		String strPdfFile = TTKPropertiesReader
    				.getPropertyValue("authorizationrptdir") + authNum + ".pdf";
    		JasperReport emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
    		  // mainJasperReport = JasperCompileManager.compileReport(mainJrxmlfile);
    		HashMap<String, Object> hashMap = new HashMap<String, Object>();
    		//parameter = "|" + preAuthSeqID + "|" + preauthStatus + "|PAT|";
    		mainTtkReportDataSource = new TTKReportDataSource("ProdSyncLetter", parameter);
    	//	diagnosisTtkReportDataSource = new TTKReportDataSource("DiagnosisDetails", parameter);
    	//	activityTtkReportDataSource = new TTKReportDataSource("ActivityDetails", parameter);


    		ResultSet main_report_RS = mainTtkReportDataSource.getResultData();

    		mainJasperReport = JasperCompileManager	.compileReport(mainJrxmlfile);
    	//	diagnosisJasperReport = JasperCompileManager.compileReport(diagnosisJrxmlfile);
    	//	activityJasperReport = JasperCompileManager.compileReport(activityJrxmlfile);

    	//	hashMap.put("diagnosisDataSource", diagnosisTtkReportDataSource);
    	//	hashMap.put("diagnosis", diagnosisJasperReport);
    	//	hashMap.put("activityDataSource", activityTtkReportDataSource);
    	//	hashMap.put("activity", activityJasperReport);
    		// JasperFillManager.fillReport(activityJasperReport, hashMap,
    		// activityTtkReportDataSource);

    		if (main_report_RS == null & !main_report_RS.next()) {
    			mainJasperPrint = JasperFillManager.fillReport(emptyReport,hashMap, new JREmptyDataSource());
    		} else {
    			main_report_RS.beforeFirst();
    			mainJasperPrint = JasperFillManager.fillReport(mainJasperReport, hashMap, mainTtkReportDataSource);
    		}// end of else
    		
    		if(request.getParameter("reportType").equals("EXL")){
    		 JRXlsExporter jExcelApiExporter = new JRXlsExporter();
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,mainJasperPrint);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			 jExcelApiExporter.exportReport();
    		}
    		request.setAttribute("boas", boas);
    		return mapping.findForward("reportdisplay");// This forward goes to
    													// the in web.xml file
    													// BinaryServlet
    	*/
    		

			log.debug("Inside the doGenerateFinanceBatchReport method of ReportsAction");
			setLinks(request);
			JasperReport jasperReport,emptyReport;
			JasperPrint jasperPrint;
			TTKReportDataSource ttkReportDataSource = null;
			String jrxmlfile, strReportID = "" ;
			jrxmlfile = request.getParameter("fileName");
			strReportID = request.getParameter("reportID");
			ttkReportDataSource = new TTKReportDataSource(strReportID,request.getParameter("parameter"));
			jasperReport = JasperCompileManager.compileReport(jrxmlfile);
			emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			ByteArrayOutputStream boas=new ByteArrayOutputStream();
			if(ttkReportDataSource.getResultData().next())
			{
				ttkReportDataSource.getResultData().beforeFirst();
				jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);				 
			}//end of if(ttkReportDataSource.getResultData().next()))
			else
			{
				jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
			}//end of if(ttkReportDataSource.getResultData().next())
			if(request.getParameter("reportType").equals("EXL"))//if the report type is Excel
			{
				JRXlsExporter jExcelApiExporter = new JRXlsExporter();
				jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
			//	jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, "MemberInceptionRulesSynchronizationReport.xls");
			//	jExcelApiExporter.setParameter(JRXlsExporterParameter.SHEET_NAMES, "MemberInceptionRulesSynchronizationReport");
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
				jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
				jExcelApiExporter.exportReport();
			}//end of if(request.getParameter("reportType").equals("EXL"))
			else//if report type if PDF
			{
				JasperExportManager.exportReportToPdfStream(jasperPrint,boas);	 
			}//end of else
			//keep the byte array out stream in request scope to write in the BinaryStreamServlet
			request.setAttribute("boas",boas);
			log.info("report generated sucessfully ");
    		return mapping.findForward("reportdisplay");
    	
    	}// end of try
    	catch (TTKException expTTK) {
    		return this.processExceptions(request, mapping, expTTK);
    	}// end of catch(TTKException expTTK)
    	catch (Exception exp) {
    		return this.processExceptions(request, mapping, new TTKException(
    				exp, ""));
    	}// end of catch(Exception exp)
    }
}//end of ProductSearchSyncAction