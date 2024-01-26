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
import com.ttk.business.finance.FloatAccountManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.InvoiceVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class AssociatePolicyAction extends TTKAction
{
	private static Logger log = Logger.getLogger(AssociatePolicyAction.class );
	//Modes of Float List
	private static final String strBackward="Backward";
	private static final String strForward="Forward";
	//Action mapping forwards
	private static final String strinvoicelist="invoicedetails";
	private static final String strInsuranceList="insurancelist";
	private static final String strClose="associatecloset";
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
			log.debug("Inside AssociatePolicyAction doDefault");
			DynaActionForm frmInvoice = (DynaActionForm)form;
			DynaActionForm frmInvoiceGeneral =(DynaActionForm)request.getSession().getAttribute("frmInvoiceGeneral");
			StringBuffer sbfCaption = new StringBuffer();
			sbfCaption.append("List of UnInvoiced Policies -").append(" [").append(frmInvoiceGeneral.getString("invoiceNbr")).append("]");
			String strDefaultBranchID  = String.valueOf((
					(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getBranchID());

            frmInvoice.initialize(mapping);//reset the form data
            TableData tableDataAssociatePolicy = null;
			//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataAssociatePolicy")!=null) 
            {
            	tableDataAssociatePolicy=(TableData)(request.getSession()).getAttribute("tableDataAssociatePolicy");
            }//end of if(request.getSession().getAttribute("tableDataAssociatePolicy")!=null)
            else
            {
            	tableDataAssociatePolicy=new TableData();
            }//end of else
			//create the required grid table
            tableDataAssociatePolicy.createTableInfo("InvoiceTable",new ArrayList());
			request.getSession().setAttribute("tableDataAssociatePolicy",tableDataAssociatePolicy);
			frmInvoice.set("caption",String.valueOf(sbfCaption));
			frmInvoice.set("sIndividualType", "Y");
			frmInvoice.set("sIndasGroupType", "Y");
			frmInvoice.set("sCorporateType","Y");
			frmInvoice.set("sNonCorpType", "Y");
			frmInvoice.set("sbranch", strDefaultBranchID);
			return this.getForward(strinvoicelist, mapping, request);
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
			log.debug("Inside AssociatePolicyAction doSearch");
			DynaActionForm frmInvoice = (DynaActionForm)form;
			DynaActionForm frmInvoiceGeneral =(DynaActionForm)request.getSession().getAttribute("frmInvoiceGeneral");
			StringBuffer sbfCaption = new StringBuffer();
			sbfCaption.append("List of UnInvoiced Policies -").append(" [").append(frmInvoiceGeneral.getString("invoiceNbr")).append("]");
			TableData tableDataAssociatePolicy = null;
			//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataAssociatePolicy")!=null) 
            {
            	tableDataAssociatePolicy=(TableData)(request.getSession()).getAttribute("tableDataAssociatePolicy");
            }//end of if(request.getSession().getAttribute("tableDataAssociatePolicy")!=null)
            else
            {
            	tableDataAssociatePolicy=new TableData();
            }//end of else
			FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableDataAssociatePolicy.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strinvoicelist));
				}///end of if(!strPageID.equals(""))
				else
				{
					tableDataAssociatePolicy.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableDataAssociatePolicy.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableDataAssociatePolicy.createTableInfo("InvoiceTable",null);
				tableDataAssociatePolicy.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableDataAssociatePolicy.modifySearchData("search");
			}//end of else

			ArrayList alTransaction= floatAccountManagerObject.getInvoicePolicyList(tableDataAssociatePolicy.getSearchData());
			tableDataAssociatePolicy.setData(alTransaction, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableDataAssociatePolicy",tableDataAssociatePolicy);
			//finally return to the grid screen
			frmInvoice.set("caption",String.valueOf(sbfCaption));
			return this.getForward(strinvoicelist, mapping, request);

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
    		log.debug("Inside AssociatePolicyAction doForward");
    		TableData tableDataAssociatePolicy = null;
			//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataAssociatePolicy")!=null) 
            {
            	tableDataAssociatePolicy=(TableData)(request.getSession()).getAttribute("tableDataAssociatePolicy");
            }//end of if(request.getSession().getAttribute("tableDataAssociatePolicy")!=null)
            else
            {
            	tableDataAssociatePolicy=new TableData();
            }//end of else
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		tableDataAssociatePolicy.modifySearchData(strForward);//modify the search data
			ArrayList alBankList = floatAccountManagerObject.getInvoicePolicyList(tableDataAssociatePolicy.getSearchData());
			tableDataAssociatePolicy.setData(alBankList, strForward);//set the table data
			request.getSession().setAttribute("tableDataAssociatePolicy",tableDataAssociatePolicy);//set the tableData object to session
			return this.getForward(strinvoicelist, mapping, request);//finally return to the grid screen
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
    		log.debug("Inside AssociatePolicyAction doBackward");
    		setLinks(request);
    		TableData tableDataAssociatePolicy = null;
			//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataAssociatePolicy")!=null) 
            {
            	tableDataAssociatePolicy=(TableData)(request.getSession()).getAttribute("tableDataAssociatePolicy");
            }//end of if(request.getSession().getAttribute("tableDataAssociatePolicy")!=null)
            else
            {
            	tableDataAssociatePolicy=new TableData();
            }//end of else
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		tableDataAssociatePolicy.modifySearchData(strBackward);//modify the search data
			ArrayList alBankList = floatAccountManagerObject.getInvoicePolicyList(tableDataAssociatePolicy.getSearchData());
			tableDataAssociatePolicy.setData(alBankList, strBackward);//set the table data
			request.getSession().setAttribute("tableDataAssociatePolicy",tableDataAssociatePolicy);//set the tableData object to session
			return this.getForward(strinvoicelist, mapping, request);//finally return to the grid screen
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
    		log.debug("Inside AssociatePolicyAction doChangeOffice");
    		//DynaActionForm frmInvoice = (DynaActionForm)form;
    		//frmInvoice.set("frmChanged","changed");
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
    
    /**
     * This method is used to close the current page and return to previous page.
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
    		setLinks(request);
    		log.debug("Inside doClose of AssociatedClaimsSearchAction");
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		StringBuffer strCaption= new StringBuffer();
    		strCaption.append("Invoice Details  - ").append("Edit");
    		InvoiceVO invoiceVO = null;
    		DynaActionForm frmInvoiceGeneral =(DynaActionForm)request.getSession().getAttribute("frmInvoiceGeneral");
    		invoiceVO=floatAccountManagerObject.getInvoiceDetail(Long.parseLong(frmInvoiceGeneral.getString("seqID"))    ,
					  new Long(TTKCommon.getUserSeqId(request).toString()));
    		frmInvoiceGeneral = (DynaActionForm)FormUtils.setFormValues("frmInvoiceGeneral",
    				invoiceVO, this, mapping, request);
    		frmInvoiceGeneral.set("caption",strCaption.toString());
    		request.getSession().setAttribute("frmInvoiceGeneral",frmInvoiceGeneral);
    		return this.getForward(strClose, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strBank));
		}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to Associate or Exclude the selected claim(s)
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doAssociateExclude(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside doAssociateExclude of AssociatePolicyAction");
    		setLinks(request);
    		ArrayList<Object> alInvoicesList = new ArrayList<Object>();
    		String strSeqIds = "";
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		TableData tableDataAssociatePolicy = null;
			//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataAssociatePolicy")!=null) 
            {
            	tableDataAssociatePolicy=(TableData)(request.getSession()).getAttribute("tableDataAssociatePolicy");
            }//end of if(request.getSession().getAttribute("tableDataAssociatePolicy")!=null)
            else
            {
            	tableDataAssociatePolicy=new TableData();
            }//end of else
    		DynaActionForm frmInvoiceGeneral =(DynaActionForm) request.getSession().getAttribute("frmInvoiceGeneral");
    		strSeqIds = this.getConcatenatedSeqID(request, (TableData)request.getSession().getAttribute("tableDataAssociatePolicy"));
    		
    		alInvoicesList.add(new Long((String)frmInvoiceGeneral.get("seqID")));
    		alInvoicesList.add(strSeqIds);
    		
    		if(request.getParameter("associatedList").equals("DBA")){
    			alInvoicesList.add("DBU");
    		}//end of if(request.getParameter("associatedList").equals("DBA")){
    		else {
    			alInvoicesList.add("DBA");
    		}//end of else    		
    		alInvoicesList.add(TTKCommon.getUserSeqId(request));//user seq id
    		int intResult = floatAccountManagerObject.associatePolicy(alInvoicesList);//to associate/Exclude
    		 //refresh the grid 
    		ArrayList alInvoice = floatAccountManagerObject.getInvoicePolicyList(tableDataAssociatePolicy.getSearchData());
            if(alInvoice.size() == 0 || intResult == tableDataAssociatePolicy.getData().size())
      	    {
            	tableDataAssociatePolicy.modifySearchData("Delete");
                int iStartRowCount = Integer.parseInt((String)tableDataAssociatePolicy.getSearchData().get(tableDataAssociatePolicy.
                					 getSearchData().size()-2));
                if(iStartRowCount > 0)
                 {
                	alInvoice = floatAccountManagerObject.getInvoicePolicyList(tableDataAssociatePolicy.getSearchData());
                 }//end of if(iStartRowCount > 0)
             }//end if(alContact.size() == 0 || iCount == tableData.getData().size())
            tableDataAssociatePolicy.setData(alInvoice, "Delete");
            request.getSession().setAttribute("tableDataAssociatePolicy",tableDataAssociatePolicy);
            return this.getForward(strinvoicelist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strBank));
		}//end of catch(Exception exp)
    }//end of doAssociateExclude()
    
    /**
     * This method is used to Associate or Exclude the selected claim(s)
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doAssociateExcludeAll(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside doAssociateExcludeAll of AssociatePolicyAction");
    		setLinks(request);
    		ArrayList<Object> alInvoicesList = new ArrayList<Object>();
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		TableData tableDataAssociatePolicy = null;
			//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataAssociatePolicy")!=null) 
            {
            	tableDataAssociatePolicy=(TableData)(request.getSession()).getAttribute("tableDataAssociatePolicy");
            }//end of if(request.getSession().getAttribute("tableDataAssociatePolicy")!=null)
            else
            {
            	tableDataAssociatePolicy=new TableData();
            }//end of else
            DynaActionForm frmInvoice = (DynaActionForm)form;
    		DynaActionForm frmInvoiceGeneral =(DynaActionForm) request.getSession().getAttribute("frmInvoiceGeneral");
    		alInvoicesList.add(TTKCommon.getLong(frmInvoice.getString("insSeqID")));
    		alInvoicesList.add(TTKCommon.checkNull(frmInvoice.getString("sIndividualType")));
    		alInvoicesList.add(TTKCommon.checkNull(frmInvoice.getString("sCorporateType")));
    		alInvoicesList.add(TTKCommon.checkNull(frmInvoice.getString("sIndasGroupType")));
    		alInvoicesList.add(TTKCommon.checkNull(frmInvoice.getString("sNonCorpType")));
    		alInvoicesList.add(TTKCommon.getLong(frmInvoice.getString("sbranch")));
    		alInvoicesList.add(TTKCommon.replaceSingleQots((String)frmInvoiceGeneral.get("fromDate")));
    		alInvoicesList.add(TTKCommon.replaceSingleQots((String)frmInvoiceGeneral.get("toDate")));
    		if(request.getParameter("associatedList").equals("DBA")){
    			alInvoicesList.add("DBU");
    		}//end of if(request.getParameter("associatedList").equals("DBA")){
    		else {
    			alInvoicesList.add("DBA");
    		}//end of else 
    		alInvoicesList.add(TTKCommon.getLong(frmInvoiceGeneral.getString("seqID")));
    		alInvoicesList.add(TTKCommon.checkNull(frmInvoiceGeneral.getString("includeOldYN")));
    		alInvoicesList.add(TTKCommon.getUserSeqId(request));//user seq id
    		int intResult = floatAccountManagerObject.associateAll(alInvoicesList);//to associate/Exclude
    		 //refresh the grid 
    		ArrayList alInvoice = floatAccountManagerObject.getInvoicePolicyList(tableDataAssociatePolicy.getSearchData());
    		 if(alInvoice.size() == 0 || intResult == tableDataAssociatePolicy.getData().size())
       	    {
             	tableDataAssociatePolicy.modifySearchData("Delete");
                 int iStartRowCount = Integer.parseInt((String)tableDataAssociatePolicy.getSearchData().get(tableDataAssociatePolicy.
                 					 getSearchData().size()-2));
                 if(iStartRowCount > 0)
                  {
                 	alInvoice = floatAccountManagerObject.getInvoicePolicyList(tableDataAssociatePolicy.getSearchData());
                  }//end of if(iStartRowCount > 0)
              }//end if(alContact.size() == 0 || iCount == tableData.getData().size())
             tableDataAssociatePolicy.setData(alInvoice, "Delete");
             request.getSession().setAttribute("tableDataAssociatePolicy",tableDataAssociatePolicy);
            return this.getForward(strinvoicelist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strBank));
		}//end of catch(Exception exp)
    }//end of doAssociateExcludeAll()
    
    /**Returns the String of concatenated string of contact_seq_id delimeted by '|'.
	 * @param HttpServletRequest
	 * @param TableData
	 * @return String
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
						sbfConcatenatedSeqId.append(String.valueOf(((InvoiceVO)tableData.getRowInfo(
																Integer.parseInt(strChOpt[iCounter]))).getPolicySeqID()));
					}//end of if(iCounter==0)
					else
					{
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((InvoiceVO)tableData.getRowInfo(Integer.
																		parseInt(strChOpt[iCounter]))).getPolicySeqID()));
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		sbfConcatenatedSeqId.append("|");
		return sbfConcatenatedSeqId.toString();
	} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)
    
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
			log.debug("Inside AssociatePolicyAction doClearInsurance");
			DynaActionForm frmInvoice = (DynaActionForm)form;
			TableData tableDataAssociatePolicy = null;
			//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataAssociatePolicy")!=null) 
            {
            	tableDataAssociatePolicy=(TableData)(request.getSession()).getAttribute("tableDataAssociatePolicy");
            }//end of if(request.getSession().getAttribute("tableDataAssociatePolicy")!=null)
            else
            {
            	tableDataAssociatePolicy=new TableData();
            }//end of else
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
			tableDataAssociatePolicy.setData(null, "search");  //clear the searched data when insurance company or group is cleared
            request.getSession().setAttribute("tableDataAssociatePolicy",tableDataAssociatePolicy);
			request.getSession().setAttribute("frmInvoice",frmInvoice);
			return (mapping.findForward(strinvoicelist));
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
		DynaActionForm frmInvoiceGeneral =(DynaActionForm) request.getSession().getAttribute("frmInvoiceGeneral");
		alSearchParams.add(TTKCommon.getLong(frmInvoice.getString("insSeqID")));
		alSearchParams.add(TTKCommon.checkNull(frmInvoice.getString("sIndividualType")));
		alSearchParams.add(TTKCommon.checkNull(frmInvoice.getString("sCorporateType")));
		alSearchParams.add(TTKCommon.checkNull(frmInvoice.getString("sIndasGroupType")));
		alSearchParams.add(TTKCommon.checkNull(frmInvoice.getString("sNonCorpType")));
		alSearchParams.add(TTKCommon.getLong(frmInvoice.getString("sbranch")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInvoiceGeneral.get("fromDate")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmInvoiceGeneral.get("toDate")));
		alSearchParams.add(TTKCommon.checkNull(frmInvoice.getString("associatedList")));
		alSearchParams.add(TTKCommon.getLong(frmInvoiceGeneral.getString("seqID")));
		alSearchParams.add(TTKCommon.checkNull(frmInvoiceGeneral.getString("includeOldYN")));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmFloatAccounts,HttpServletRequest request)
}//end of AssociatePolicyAction class
