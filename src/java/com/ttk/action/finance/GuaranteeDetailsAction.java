/**
 * @ (#) GuaranteeDetailsSearchAction.java June 24,2006
 * Project       : TTK HealthCare Services
 * File          : GuaranteeDetailsSearchAction.java
 * Author        : Arun K.M
 * Company       : Span Systems Corporation
 * Date Created  : June 24, 2006
 *
 * @author       :
 * Modified by   : Harsha Vardhan B N
 * Modified date : June 26, 2006
 * Reason        :
 */

package com.ttk.action.finance;

import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.finance.BankAccountManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.BankGuaranteeVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of List of Guarentee Details.
 * This class also provides option for deletion,Addition and Updation of Guarentee Details.
 */
	public class GuaranteeDetailsAction extends TTKAction {
	private static Logger log = Logger.getLogger( GuaranteeDetailsAction.class );
	//   Modes.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";
    private static final String strDeleteList="DeleteList";
    //  Exception Message Identifier
    private static final String strAddEditBankGuaranteeDetail="AddEditBankGuaranteeDetail";
	//  Action mapping forwards.
    private static final String strGuaranteeList="guaranteelist";
    private static final String strBankGuaranteeDetail="bankguaranteedetail";
    
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
    		log.debug("Inside GuaranteeDetailsAction doDefault");
    		if(TTKCommon.getWebBoardId(request) == null) //if web-board has no components,it gives an error message
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.accountno.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request) == null)
    		TableData  tableData =TTKCommon.getTableData(request);
    		ArrayList alBufferList= new ArrayList();
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		//create new table data object
    		tableData = new TableData();
    		//create the required grid table
    		tableData.createTableInfo("GuaranteeDetailsTable",null);
    		tableData.setSearchData(this.populateSearchCriteria(request));
    		tableData.modifySearchData("search");
    		alBufferList=bankAccountManagerObject.getBankGuaranteeList(tableData.getSearchData());
    		tableData.setData(alBufferList,"search");
    		request.getSession().setAttribute("tableData",tableData);
    		((DynaActionForm)form).initialize(mapping);//reset the form data
    		return this.getForward(strGuaranteeList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAddEditBankGuaranteeDetail));
    	}//end of catch(Exception exp)
    }//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to get the details of the selected record from web-board.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    									  HttpServletResponse response) throws Exception{
    	log.debug("Inside GuaranteeDetailsAction doChangeWebBoard");
    	return doDefault(mapping,form,request,response);
    }//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse
    																								//response)
    
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
    		log.debug("Inside GuaranteeDetailsAction doSearch");
    		TableData  tableData =TTKCommon.getTableData(request);
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
    		if(!strPageID.equals("") || !strSortID.equals(""))
    		{
    			if(!strPageID.equals(""))
    			{
    				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    				return (mapping.findForward(strGuaranteeList));
    			}///end of if(!strPageID.equals(""))
    			else
    			{
    				tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
    				tableData.modifySearchData("sort");//modify the search data
    			}//end of else
    		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
    		else
    		{
    			// create the required grid table
    			tableData.createTableInfo("GuaranteeDetailsTable",null);
    			//fetch the data from the data access layer and set the data to table object
    			tableData.setSearchData(this.populateSearchCriteria(request));
    			tableData.modifySearchData("search");
    		}//end of else
    		ArrayList albnkguarn=bankAccountManagerObject.getBankGuaranteeList(tableData.getSearchData());
    		tableData.setData(albnkguarn, "search");
    		request.getSession().setAttribute("tableData",tableData);
    		//finally return to the grid screen
    		return this.getForward(strGuaranteeList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAddEditBankGuaranteeDetail));
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
    		log.debug("Inside GuaranteeDetailsAction doForward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		tableData.modifySearchData(strForward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alFloat = bankAccountManagerObject.getBankGuaranteeList(tableData.getSearchData());
    		tableData.setData(alFloat, strForward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
    		return this.getForward(strGuaranteeList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAddEditBankGuaranteeDetail));
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
    		log.debug("Inside GuaranteeDetailsAction doBackward");
    		TableData  tableData =TTKCommon.getTableData(request);
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alFloat = bankAccountManagerObject.getBankGuaranteeList(tableData.getSearchData());
    		tableData.setData(alFloat, strBackward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
    		return this.getForward(strGuaranteeList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAddEditBankGuaranteeDetail));
    	}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is called from the struts framework.
     * This method is used to navigate to detail screen to add a record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    						   HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside GuaranteeDetailsAction doAdd");
    		DynaActionForm frmBankGuaranteeDetail= (DynaActionForm)form;
    		BankGuaranteeVO bankGuaranteeVO=null;
    		StringBuffer strCaption= new StringBuffer();
    		frmBankGuaranteeDetail.initialize(mapping);
    		bankGuaranteeVO = new BankGuaranteeVO();
    		bankGuaranteeVO.setAccountSeqID(TTKCommon.getWebBoardId(request));
    		strCaption.append(" - Add [").append(TTKCommon.getWebBoardDesc(request)).append("]");
    		frmBankGuaranteeDetail= (DynaActionForm)FormUtils.setFormValues("frmBankGuaranteeDetail", 
    																		bankGuaranteeVO, this, mapping, request);
    		frmBankGuaranteeDetail.set("caption",strCaption.toString());
    		frmBankGuaranteeDetail.set("bgType", "NEW");
    		request.getSession().setAttribute("listOfBgs", new ArrayList<BankGuaranteeVO>());
    		
    		request.setAttribute("frmBankGuaranteeDetail",frmBankGuaranteeDetail);
    		return this.getForward(strBankGuaranteeDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAddEditBankGuaranteeDetail));
    	}//end of catch(Exception exp)
    }//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
	    public ActionForward doChangeBgType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				   HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside GuaranteeDetailsAction doChangeBgType");
			DynaActionForm frmBankGuaranteeDetail= (DynaActionForm)form;
			BankGuaranteeVO bankGuaranteeVO=null;
			StringBuffer strCaption= new StringBuffer();
			//frmBankGuaranteeDetail.initialize(mapping);
			String bgType	=	frmBankGuaranteeDetail.getString("bgType");
			bankGuaranteeVO = new BankGuaranteeVO();
			bankGuaranteeVO.setAccountSeqID(TTKCommon.getWebBoardId(request));
			strCaption.append(" - Add [").append(TTKCommon.getWebBoardDesc(request)).append("]");
			frmBankGuaranteeDetail= (DynaActionForm)FormUtils.setFormValues("frmBankGuaranteeDetail", 
																		bankGuaranteeVO, this, mapping, request);
			frmBankGuaranteeDetail.set("caption",strCaption.toString());
			request.getSession().setAttribute("frmBankGuaranteeDetail",frmBankGuaranteeDetail);
			return this.getForward(strBankGuaranteeDetail, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
			catch(Exception exp)
		{
				return this.processExceptions(request, mapping, new TTKException(exp, strAddEditBankGuaranteeDetail));
		}//end of catch(Exception exp)
	}//end of doChangeBgType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
    public ActionForward doViewBankGuarantee(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										 HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside GuaranteeDetailsAction doViewBankGuarantee");
    		BankGuaranteeVO bankGuaranteeVO=null;
    		StringBuffer strCaption= new StringBuffer();
    		TableData  tableData =TTKCommon.getTableData(request);
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		DynaActionForm frmBankGuaranteeDetail= (DynaActionForm)form;
    		bankGuaranteeVO = (BankGuaranteeVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
    		bankGuaranteeVO = bankAccountManagerObject.getBankGuaranteeDetail(bankGuaranteeVO.getGuaranteeSeqID(), 
    																		  TTKCommon.getUserSeqId(request));
    		strCaption.append(" - Edit [").append(TTKCommon.getWebBoardDesc(request)).append("]");
    		frmBankGuaranteeDetail= (DynaActionForm)FormUtils.setFormValues("frmBankGuaranteeDetail", 
    																		bankGuaranteeVO, this, mapping, request);
    		//history table data
    		ArrayList<BankGuaranteeVO> listOfBgs	=	new ArrayList<>();
    		listOfBgs	=	bankAccountManagerObject.getBankRenewHistory(bankGuaranteeVO.getGuaranteeSeqID(), 
					  TTKCommon.getUserSeqId(request));
    		request.getSession().setAttribute("listOfBgs", listOfBgs);
    		frmBankGuaranteeDetail.set("bgType", "REN");

    		frmBankGuaranteeDetail.set("caption",strCaption.toString());
    		request.setAttribute("frmBankGuaranteeDetail",frmBankGuaranteeDetail);
    		return this.getForward(strBankGuaranteeDetail, mapping, request);
    	}
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAddEditBankGuaranteeDetail));
    	}//end of catch(Exception exp)
    }//end of doViewBankGuarantee(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse 
    																							//response)
    
    /**
     * This method is used to add/update the record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside GuaranteeDetailsAction doViewBankGuarantee");
    		DynaActionForm frmBankGuaranteeDetail= (DynaActionForm)form;
    		BankGuaranteeVO bankGuaranteeVO=null;
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		StringBuffer strCaption= new StringBuffer();
    		bankGuaranteeVO =(BankGuaranteeVO)FormUtils.getFormValues(frmBankGuaranteeDetail, this, mapping,request);
    		bankGuaranteeVO.setUpdatedBy(TTKCommon.getUserSeqId(request));	//User Id
    		bankGuaranteeVO.setAccountSeqID(TTKCommon.getWebBoardId(request));
    		Long lngGuarSeqId=bankAccountManagerObject.saveBankGuarantee(bankGuaranteeVO);
    		if(lngGuarSeqId>0)
    		{
    			if(bankGuaranteeVO.getGuaranteeSeqID()!=null)
    			{
    				request.setAttribute("updated","message.savedSuccessfully");
    				strCaption.append(" - Edit [").append(TTKCommon.getWebBoardDesc(request)).append("]");
    			}//end of if(bankGuaranteeVO.getGuaranteeSeqID()!=null)
    			else
    			{
    				request.setAttribute("updated","message.addedSuccessfully");
    				strCaption.append(" - Add [").append(TTKCommon.getWebBoardDesc(request)).append("]");
    			}//end of else
    		}//end of if(lngGuarSeqId>0)
    		bankGuaranteeVO = bankAccountManagerObject.getBankGuaranteeDetail(lngGuarSeqId, 
    																		  TTKCommon.getUserSeqId(request));
    		frmBankGuaranteeDetail = (DynaActionForm)FormUtils.setFormValues("frmBankGuaranteeDetail", 
    																		bankGuaranteeVO, this, mapping, request);
    		//history table data
    		ArrayList<BankGuaranteeVO> listOfBgs	=	new ArrayList<>();
    		listOfBgs	=	bankAccountManagerObject.getBankRenewHistory(bankGuaranteeVO.getGuaranteeSeqID(), 
					  TTKCommon.getUserSeqId(request));
    		request.getSession().setAttribute("listOfBgs", listOfBgs);
    		frmBankGuaranteeDetail.set("bgType", "REN");
    		
    		frmBankGuaranteeDetail.set("caption",strCaption.toString());
    		request.setAttribute("frmBankGuaranteeDetail",frmBankGuaranteeDetail);
    		return this.getForward(strBankGuaranteeDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAddEditBankGuaranteeDetail));
    	}//end of catch(Exception exp)
    }//end of doUpdate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to reload the screen when the reset button is pressed.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							 HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside GuaranteeDetailsAction doReset");
    		BankGuaranteeVO bankGuaranteeVO=null;
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		StringBuffer strCaption= new StringBuffer();
    		DynaActionForm frmBankGuaranteeDetail= (DynaActionForm)form;
    		if(frmBankGuaranteeDetail.get("guaranteeSeqID")!=null 
    													&& !frmBankGuaranteeDetail.get("guaranteeSeqID").equals(""))
    		{
    			bankGuaranteeVO = bankAccountManagerObject.getBankGuaranteeDetail(TTKCommon.getLong(
    					frmBankGuaranteeDetail.getString("guaranteeSeqID")),TTKCommon.getUserSeqId(request));
    			strCaption.append(" - Edit [").append(TTKCommon.getWebBoardDesc(request)).append("]");
    		}//end of if(frmBankGuaranteeDetail.get("transSeqId")!=null && 
    															//!frmBankGuaranteeDetail.get("transSeqId").equals(""))
    		else
    		{
    			frmBankGuaranteeDetail.initialize(mapping);
    			bankGuaranteeVO=new BankGuaranteeVO();
    			bankGuaranteeVO.setAccountSeqID(TTKCommon.getWebBoardId(request));
    			strCaption.append(" - Add [").append(TTKCommon.getWebBoardDesc(request)).append("]");
    		}//end of else
    		frmBankGuaranteeDetail = (DynaActionForm)FormUtils.setFormValues("frmBankGuaranteeDetail",
    																		bankGuaranteeVO, this, mapping, request);
    		frmBankGuaranteeDetail.set("caption",strCaption.toString());
    		request.setAttribute("frmBankGuaranteeDetail",frmBankGuaranteeDetail);
    		return this.getForward(strBankGuaranteeDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAddEditBankGuaranteeDetail));
    	}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
    		setLinks(request);
    		log.debug("Inside GuaranteeDetailsAction doClose");
    		TableData  tableData =TTKCommon.getTableData(request);
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
        	if(tableData.getSearchData().size()>1)
			{
        		ArrayList alGuarSearch = bankAccountManagerObject.getBankGuaranteeList(tableData.getSearchData());
        		tableData.setData(alGuarSearch, "Cancel");
        		//set the table data object to session
        		request.getSession().setAttribute("tableData",tableData);
			}//end of if(tableData.getSearchData().size()>1)
			return mapping.findForward(strGuaranteeList);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAddEditBankGuaranteeDetail));
    	}//end of catch(Exception exp)
    }//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is called from the struts framework.
     * This method is used to delete records.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    								  HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside GuaranteeDetailsAction doDeleteList");
            StringBuffer sbfDeleteId = new StringBuffer("|");
            TableData  tableData =TTKCommon.getTableData(request);
            BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
            int iCount=0;
            //populate the delete string which contains the sequence id's to be deleted
            sbfDeleteId.append(populateDeleteId(request,(TableData)request.getSession().getAttribute("tableData")));
            ArrayList<Object> alDeleteList = new ArrayList<Object>();
            alDeleteList.add(sbfDeleteId.toString());
            alDeleteList.add(TTKCommon.getUserSeqId(request));
            //delete the selected Guarantee based on the flow
            iCount=bankAccountManagerObject.deleteBankGuarantee(alDeleteList);
            //refresh the grid with search data in session
            ArrayList alBankList = null;
            //fetch the data from previous set of rowcounts, if all the records are deleted for the current set of 
            																					//search criteria
            if(iCount == tableData.getData().size())
            {
                tableData.modifySearchData(strDeleteList);//modify the search data
                int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(
                																tableData.getSearchData().size()-2));
                if(iStartRowCount > 0)
                {
                	alBankList=bankAccountManagerObject.getBankGuaranteeList(tableData.getSearchData());
                }//end of if(iStartRowCount > 0)
            }//end if(iCount == tableData.getData().size())
            else
            {
            	alBankList=bankAccountManagerObject.getBankGuaranteeList(tableData.getSearchData());
            }//end of else
            tableData.setData(alBankList, strDeleteList);
            if(iCount>0)
            {
                request.setAttribute("cacheId",sbfDeleteId.append("|").toString());
            }//end of if(iCount>0)
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward(strGuaranteeList, mapping, request);
			
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strAddEditBankGuaranteeDetail));
    	}//end of catch(Exception exp)
    }//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
    /**
	 * This method will add search criteria fields and values to the arraylist and will return it
	 * @param frmGuaranteeDetails current instance of form bean
	 * @param request HttpServletRequest object
	 * @param strActiveSubLink current Active sublink
	 * @return alSearchObjects ArrayList of search params
	 * @throws TTKException
	 */
	private ArrayList<Object> populateSearchCriteria(HttpServletRequest request)throws TTKException
	{
		ArrayList<Object> alParameters=new ArrayList<Object>();
		alParameters.add(TTKCommon.getWebBoardId(request));
		alParameters.add(TTKCommon.getUserSeqId(request));
		return alParameters;
	}//end of populateSearchCriteria(HttpServletRequest request)throws TTKException

	/**
	 * This method returns a string which contains the comma separated sequence id's to be deleted,
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param BankListData TableData object which contains the value objects
	 * @return String which contains the comma separated sequence id's to be deleted
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableData)
	{
	    String[] strChk = request.getParameterValues("chkopt");
	    StringBuffer sbfDeleteId = new StringBuffer();
	    if(strChk!=null&&strChk.length!=0)
	    {
	     //loop through to populate delete sequence id's and get the value from session for the matching check box value
	        for(int i=0; i<strChk.length;i++)
	        {
	            log.debug("strChk["+i+"] : "+Integer.parseInt(strChk[i]));
	            if(strChk[i]!=null)
	            {
	                //extract the sequence id to be deleted from the value object
	                if(i == 0)
	                {
	                    sbfDeleteId.append(String.valueOf(((BankGuaranteeVO)tableData.getData().
	                    									get(Integer.parseInt(strChk[i]))).getGuaranteeSeqID()));
	                }// end of if(i == 0)
	                else
	                {
	                    sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((BankGuaranteeVO)tableData.
	                    						getData().get(Integer.parseInt(strChk[i]))).getGuaranteeSeqID()));
	                }// end of else
	            }//end of if(strChk[i]!=null)
	        }//end of for(int i=0; i<strChk.length;i++)
	        sbfDeleteId.append("|");
	    }//end of if(strChk!=null&&strChk.length!=0)
	    return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)

	/**
	 * Returns the BankAccountManager session object for invoking methods on it.
	 * @return BankAccountManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	
	private BankAccountManager getBankAccountManagerObject() throws TTKException
	{
		BankAccountManager bankAccountManager = null;
		try
		{
			if(bankAccountManager == null)
			{
				InitialContext ctx = new InitialContext();
				bankAccountManager = (BankAccountManager) ctx.lookup("java:global/TTKServices/business.ejb3/BankAccountManagerBean!com.ttk.business.finance.BankAccountManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strAddEditBankGuaranteeDetail);
		}//end of catch
		return bankAccountManager;
	}//end of getBankAccountManagerObject()
}//end of class GuaranteeDetailsSearchAction