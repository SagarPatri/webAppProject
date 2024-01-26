/**
 * @ (#) InvoiceAction.java Nov 4, 2006
 * Project      : TTK HealthCare Services
 * File         : InvoiceAction.java
 * Author       : Arun K.M
 * Company      : Span Systems Corporation
 * Date Created : Nov 4, 2006
 *
 * @author       : Arun K.M
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

/*
 * Created on Nov 4, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ttk.action.finance;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.apache.struts.action.DynaActionForm;
import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.finance.FloatAccountManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.finance.InvoiceVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This class is used for searching of List of Invoice.
 * This class also provides option for generating the xl report.
 */

public class InvoiceAction extends TTKAction
{
	private static Logger log = Logger.getLogger(ViewPaymentAdviceAction.class );
	//Modes of Float List
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	//Action mapping forwards
	private static final String strinvoicedetails="invoicedetails";
	private static final String strInsuranceList="insurancelist";
	private static final String strGroupList="grouplist";
	private static final String strReportdisplay="reportdisplay";
	//Exception Message Identifier
	private static final String strBank="bank";

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
			setLinks(request);
			log.debug("Inside InvoiceAction doDefault");
			TableData  tableData =TTKCommon.getTableData(request);
			DynaActionForm frmInvoice = (DynaActionForm)form;
			String strDefaultBranchID  = String.valueOf((
					(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getBranchID());

            frmInvoice.initialize(mapping);//reset the form data

            //create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("InvoiceTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			String strEnrollmentType = (String)frmInvoice.get("senrollmentType");
			request.getSession().setAttribute("searchparam", strEnrollmentType);
			if(TTKCommon.checkNull(strEnrollmentType).equals(""))
			{
				strEnrollmentType="IND";
			}//end of if(TTKCommon.checkNull(strEnrollmentType).equals(""))
			frmInvoice.set("senrollmentType",strEnrollmentType);
			frmInvoice.set("sbranch", strDefaultBranchID);
			return this.getForward(strinvoicedetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strBank));
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
							HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside InvoiceAction doSearch");
			TableData  tableData =TTKCommon.getTableData(request);
			FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
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
					return (mapping.findForward(strinvoicedetails));					
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("InvoiceTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else

			ArrayList alTransaction= floatAccountManagerObject.getInvoicePolicyList(tableData.getSearchData());
			tableData.setData(alTransaction, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strinvoicedetails, mapping, request);

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strBank));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to a group.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,
						HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside InvoiceAction doSelectGroup");
			DynaActionForm frmInvoice = (DynaActionForm)form;
			frmInvoice.set("frmChanged","changed");
			return mapping.findForward(strGroupList);
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strBank));
		}//end of catch(Exception exp)
	}//end of doSelectGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to a group.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeEnrollment(ActionMapping mapping,ActionForm form,HttpServletRequest request,
							HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside InvoiceAction doChangeEnrollment");
			TableData  tableData =TTKCommon.getTableData(request);
			DynaActionForm frmInvoice = (DynaActionForm)form;
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("InvoiceTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			String strEnrollmentType = (String)frmInvoice.get("senrollmentType");
			((DynaActionForm)form).initialize(mapping);//reset the form data
			request.getSession().setAttribute("searchparam", null);
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);
				strEnrollmentType="IND";
			}
			if(strEnrollmentType.equals(""))
			{
				strEnrollmentType="IND";
			}//end of if(strEnrollmentType.equals(""))
			frmInvoice.set("senrollmentType",strEnrollmentType);
			return this.getForward(strinvoicedetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strBank));
		}//end of catch(Exception exp)
	}//end of doChangeEnrollment(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse
																										//response)

	/**
	 * This method is used to a group.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doClearInsurance(ActionMapping mapping,ActionForm form,HttpServletRequest request,
						HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside InvoiceAction doClearInsurance");
			DynaActionForm frmInvoice = (DynaActionForm)form;
			TableData  tableData =TTKCommon.getTableData(request);
			String strIdentifier=TTKCommon.checkNull(request.getParameter("identifier"));
			if(strIdentifier.equals("Enrollment"))
			{
				frmInvoice.set("groupID","");
				frmInvoice.set("groupName","");
				frmInvoice.set("groupRegnSeqID","");
			}//end of if(strIdentifier.equals("Enrollment"))
			else
			{
				frmInvoice.set("insComp","");
				frmInvoice.set("insCompCode","");
				frmInvoice.set("insSeqID","");
			}//end of else
            tableData.setData(null, "search");  //clear the searched data when insurance company or group is cleared
            request.getSession().setAttribute("tableData",tableData);
			request.getSession().setAttribute("frmInvoice",frmInvoice);
			return (mapping.findForward(strinvoicedetails));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strBank));
		}//end of catch(Exception exp)
	}//end of doClearInsurance(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doInvoiceXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside InvoiceAction doInvoiceXL");
			return (mapping.findForward(strReportdisplay));
		}
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strBank));
		}//end of catch(Exception exp)
	}//end of doInvoiceXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse
																								//response)

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
	public ActionForward doGenerateXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,
							HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside InvoiceAction doGenerateXL");
			DynaActionForm frmInvoice = (DynaActionForm)form;
			FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
			JasperReport jasperReport = null;
			JasperPrint jasperPrint = null;
			TableData  tableData =TTKCommon.getTableData(request);
			ArrayList<Object> alGenerateXL = new ArrayList<Object>();
			String strEnrollmentType = (String)frmInvoice.get("senrollmentType");
			Long lngAddedBy = TTKCommon.getUserSeqId(request);
			TTKReportDataSource reportDataSource = null;
			String strPdfFile = null;
			String strPolicySeqID = getPolicySeqID(request,(TableData)request.getSession().getAttribute("tableData"));
			Date dtCurrentDate = new Date();
			//Call the business layer to generate invoice for the selected policies
            reportDataSource = floatAccountManagerObject.getGenerateInvoiceList(
            															strPolicySeqID,strEnrollmentType,lngAddedBy);

            if(reportDataSource!=null)  //if invoice generated successfully,then generate XL
            {
                if(strEnrollmentType.equalsIgnoreCase("IND"))
                {
                    strPdfFile = TTKPropertiesReader.getPropertyValue("Invoicerptdir")
                    +"\\"+strEnrollmentType+"-"+TTKCommon.getFormattedDateTime(dtCurrentDate)
                    +"-"+TTKCommon.getUserSeqId(request)+".xls";
                    jasperReport = JasperCompileManager.compileReport("generalreports/TpaInd.jrxml");
                }//end of if(strEnrollmentType.equalsIgnoreCase("IND"))
                else
                {
                    strPdfFile = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+"\\"+"GROUP-"
                    +TTKCommon.getFormattedDateTime(dtCurrentDate)+"-"+TTKCommon.getUserSeqId(request)+".xls";
                    jasperReport = JasperCompileManager.compileReport("generalreports/TpaGrp.jrxml");
                }//end of else

                File file = new File(strPdfFile);
                alGenerateXL.add(TTKCommon.getWebBoardId(request));
                alGenerateXL.add(TTKCommon.getUserSeqId(request));
                HashMap hashMap = new HashMap();
                jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,reportDataSource);
                request.setAttribute("reportType","EXL");
                JRXlsExporter exporterXL = new JRXlsExporter();
                exporterXL.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
                exporterXL.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, strPdfFile.toString());
                exporterXL.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                exporterXL.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                exporterXL.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                exporterXL.exportReport();

                if(file.exists())
                {
                    if(strEnrollmentType.equalsIgnoreCase("IND"))
                    {
                        strPdfFile = TTKPropertiesReader.getPropertyValue("Invoicerptdir")+"\\\\"
                        +strEnrollmentType+"-"+TTKCommon.getFormattedDateTime(dtCurrentDate)
                        +"-"+TTKCommon.getUserSeqId(request)+".xls";
                    }//end of if(strEnrollmentType.equalsIgnoreCase("IND"))
                    else
                    {
                        strPdfFile = TTKPropertiesReader.getPropertyValue("Invoicerptdir")
                        +"\\\\"+"GROUP-"+TTKCommon.getFormattedDateTime(dtCurrentDate)+"-"
                        +TTKCommon.getUserSeqId(request)+".xls";
                    }//end of else
                    request.setAttribute("fileName",strPdfFile);
                }//end of if(file.exists())
                request.setAttribute("GenerateXL","message.GenerateXLSuccessfully");
                //Requery to get the pending invoice list
                ArrayList alTransaction= floatAccountManagerObject.getInvoicePolicyList(tableData.getSearchData());
                tableData.setData(alTransaction, "search");
                //set the table data object to session
                request.getSession().setAttribute("tableData",tableData);
            }//end of if(reportDataSource!=null)
           return this.getForward(strinvoicedetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strBank));
		}//end of catch(Exception exp)
	}//end of doGenerateXL(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		setLinks(request);
    		log.debug("Inside InvoiceAction doForward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		tableData.modifySearchData(strForward);//modify the search data
			ArrayList alBankList = floatAccountManagerObject.getInvoicePolicyList(tableData.getSearchData());
			tableData.setData(alBankList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strinvoicedetails, mapping, request);//finally return to the grid screen
		}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strBank));
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
    					HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		TableData  tableData =TTKCommon.getTableData(request);
    		log.debug("Inside InvoiceAction doBackward");
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alBankList = floatAccountManagerObject.getInvoicePolicyList(tableData.getSearchData());
			tableData.setData(alBankList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strinvoicedetails, mapping, request);//finally return to the grid screen
		}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strBank));
		}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to a group.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doChangeOffice(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    					HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside InvoiceAction doChangeOffice");
    		DynaActionForm frmInvoice = (DynaActionForm)form;
    		frmInvoice.set("frmChanged","changed");
    		return mapping.findForward(strInsuranceList);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strBank));
    	}//end of catch(Exception exp)
    }//end of doChangeOffice(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**Returns the String of concatenated string of contact_seq_id delimeted by '|'.
	 * @param HttpServletRequest
	 * @param TableData
	 * @return String
	 */
	private String getPolicySeqID(HttpServletRequest request,TableData tableData) {
		StringBuffer sbfPolicySeqID=new StringBuffer("|");
		String strChOpt[]=request.getParameterValues("chkopt");
		if((strChOpt!=null)&& strChOpt.length!=0)
		{
			for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
			{
				if(strChOpt[iCounter]!=null)
				{
					if(iCounter==0)
					{
						sbfPolicySeqID.append(String.valueOf(((InvoiceVO)tableData.getRowInfo(
																Integer.parseInt(strChOpt[iCounter]))).getSeqID()));
					}//end of if(iCounter==0)
					else
					{
						sbfPolicySeqID.append("|").append(String.valueOf(((InvoiceVO)tableData.getRowInfo(
																Integer.parseInt(strChOpt[iCounter]))).getSeqID()));
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		sbfPolicySeqID.append("|");
		return sbfPolicySeqID.toString();
	} // end of getPolicySeqID(HttpServletRequest request,TableData tableData)

	/**
	 * Returns the ContactManager session object for invoking methods on it.
	 * @return ContactManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private FloatAccountManager getfloatAccountManagerObject() throws TTKException
	{
		FloatAccountManager floatAccountManager = null;
		try
		{
			if(floatAccountManager == null)
			{
				InitialContext ctx = new InitialContext();
				floatAccountManager = (FloatAccountManager) ctx.lookup("java:global/TTKServices/business.ejb3/FloatAccountManagerBean!com.ttk.business.finance.FloatAccountManager");
			}//end of if(contactManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "floataccount");
		}//end of catch
		return floatAccountManager;
	}//end getfloatAccountManagerObject()
	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmInvoice formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmInvoice,HttpServletRequest request)throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();

		alSearchParams.add(TTKCommon.getLong(frmInvoice.getString("insSeqID")));
		alSearchParams.add(TTKCommon.checkNull(frmInvoice.getString("senrollmentType")));
		alSearchParams.add(TTKCommon.checkNull(frmInvoice.getString("groupRegnSeqID")));
		alSearchParams.add(TTKCommon.checkNull(frmInvoice.getString("sbranch")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInvoice.get("sFromDate")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInvoice.get("sToDate")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmFloatAccounts,HttpServletRequest request)
}// end of InvoiceAction.java