/**
 * @ (#) DebitNoteAction.java Sep 11, 2007
 * Project 	     : TTK HealthCare Services
 * File          : DebitNoteAction.java
 * Author        : Chandrasekaran J
 * Company       : Span Systems Corporation
 * Date Created  : Sep 11, 2007
 *
 * @author       : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
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
import com.ttk.dto.finance.DebitNoteVO;

import formdef.plugin.util.FormUtils;

public class DebitNoteAction extends TTKAction
{
	private static Logger log = Logger.getLogger( FloatAccountAction.class ); // Getting Logger for this Class file
	//Action mapping forwards
	private static final String strDebitList="debitlist";
	private static final String strDebitClaims="claimlist";
	//private static final String strDebitClose="claimslist";
	private static final String strDebitListPayment="debitlistpayment";
	private static final String strDebitDetails="debitdetails";
	private static final String strBackward="Backward";
    private static final String strForward="Forward";

	//  Exception Message Identifier
    private static final String strDebitListExp="debit";
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
    		log.debug("Inside DebitNoteAction doDefault");
    		DynaActionForm frmFloatAccDetails =(DynaActionForm)request.getSession().getAttribute("frmFloatAccDetails");
    		String strForwardPath="";
    		String strActiveTab="";
    		strActiveTab=TTKCommon.getActiveTab(request);
    		StringBuffer sbfCaption= new StringBuffer();
    		if(strActiveTab.equals("Payments"))
    		{
    			sbfCaption.append("Debit Note List - ").append("[").append(TTKCommon.getWebBoardDesc(request)).append("]");
    			strForwardPath=strDebitListPayment;
    		}//end of if(strActiveTab.equals("Payments"))
    		else if(strActiveTab.equals("General"))
    		{
    			sbfCaption.append("Debit Note List - ").append("[").append(frmFloatAccDetails.getString("floatNo")).append("]");
    			strForwardPath=strDebitList;
    		}//end of else if(strActiveTab.equals("General"))
    		
			TableData tableDataDebitNote = null;
			//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataDebitNote")!=null) 
            {
            	tableDataDebitNote=(TableData)(request.getSession()).getAttribute("tableDataDebitNote");
            }//end of if(request.getSession().getAttribute("tableDataCirculars")!=null)
            else
            {
            	tableDataDebitNote=new TableData();
            }//end of else
    		DynaActionForm frmDebitNoteList = (DynaActionForm)form;
    		//create new table data object
    		tableDataDebitNote = new TableData();
    		//create the required grid table
    		tableDataDebitNote.createTableInfo("DebitNoteTable",new ArrayList());
    		((DynaActionForm)form).initialize(mapping);//reset the form data
    		if(strActiveTab.equals("Payments"))
    		{
    			frmDebitNoteList.set("sDebitType","DFL");
    		}
    		frmDebitNoteList.set("caption",String.valueOf(sbfCaption));
    		request.getSession().setAttribute("tableDataDebitNote",tableDataDebitNote);
    		return this.getForward(strForwardPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strDebitListExp));
    	}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    
    
    
    public ActionForward  setDebitType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		   HttpServletResponse response) throws Exception{ 
    	try{
    		setLinks(request);
    		DynaActionForm frmDebitNoteDetails=(DynaActionForm)form;
    		DebitNoteVO debitNoteVO = null;
    	    String debitType	=     frmDebitNoteDetails.getString("debitNoteTypeID");
    	if(debitType!=null  && !debitType.isEmpty() &&  debitType.equals("DFL"))
    	{
    		
    		frmDebitNoteDetails.set("finalDate",TTKCommon.getFormattedDate(TTKCommon.getDate()));
    		frmDebitNoteDetails.set("debitType",debitType);	
    		
    	
    			request.getSession().setAttribute("flag", "true");
    		
    	}
    	else if(debitType.equals("DDT")){
    		
         	frmDebitNoteDetails.set("finalDate","");
         	frmDebitNoteDetails.set("debitType",debitType);	

    	}
    		
    	debitType = "";
    		request.getSession().setAttribute("frmDebitNoteDetails",frmDebitNoteDetails);
    		return this.getForward(strDebitDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strDebitListExp));
    	}//end of catch(Exception exp)
    }
    
    
  
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
    		log.debug("Inside DebitNoteAction doSearch");
    		DynaActionForm frmDebitNoteList = (DynaActionForm)form;
    		DynaActionForm frmFloatAccDetails =(DynaActionForm)request.getSession().getAttribute("frmFloatAccDetails");
    		String strForwardPath="";
    		String strActiveTab="";
    		strActiveTab=TTKCommon.getActiveTab(request);
    		StringBuffer sbfCaption= new StringBuffer();
    		if(strActiveTab.equals("Payments"))
    		{
    			sbfCaption.append("Debit Note List - ").append("[").append(TTKCommon.getWebBoardDesc(request)).append("]");
    			strForwardPath=strDebitListPayment;
    		}//end of if(strActiveTab.equals("Payments"))
    		else if(strActiveTab.equals("General"))
    		{
    			sbfCaption.append("Debit Note List - ").append("[").append(frmFloatAccDetails.getString("floatNo")).append("]");
    			strForwardPath=strDebitList;
    		}//end of else if(strActiveTab.equals("General"))
    		
			
			TableData tableDataDebitNote = null;
			//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataDebitNote")!=null) 
            {
            	tableDataDebitNote=(TableData)(request.getSession()).getAttribute("tableDataDebitNote");
            }//end of if(request.getSession().getAttribute("tableDataCirculars")!=null)
            else
            {
            	tableDataDebitNote=new TableData();
            }//end of else
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
    		if(!strPageID.equals("") || !strSortID.equals(""))
    		{
    			if(!strPageID.equals(""))
    			{
    				tableDataDebitNote.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    				return (mapping.findForward(strDebitList));
    			}///end of if(!strPageID.equals(""))
    			else
    			{
    				tableDataDebitNote.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
    				tableDataDebitNote.modifySearchData("sort");//modify the search data
    			}//end of else
    		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
    		else
    		{
    			// create the required grid table
    			tableDataDebitNote.createTableInfo("DebitNoteTable",null);
    			//fetch the data from the data access layer and set the data to table object
    			tableDataDebitNote.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
    			tableDataDebitNote.modifySearchData("search");
    		}//end of else
    		ArrayList alDebitList=floatAccountManagerObject.getDebitNoteList(tableDataDebitNote.getSearchData());
    		tableDataDebitNote.setData(alDebitList, "search");
    		frmDebitNoteList.set("caption",String.valueOf(sbfCaption));
    		request.getSession().setAttribute("tableDataDebitNote",tableDataDebitNote);
    		//finally return to the grid screen
    		return this.getForward(strForwardPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strDebitListExp));
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
    		log.debug("Inside DebitNoteAction doForward");
    		TableData tableDataDebitNote = null;
    		String strForwardPath="";
    		String strActiveTab="";
    		strActiveTab=TTKCommon.getActiveTab(request);
    		if(strActiveTab.equals("Payments"))
    		{
    			strForwardPath=strDebitListPayment;
    		}//end of if(strActiveTab.equals("Payments"))
    		else if(strActiveTab.equals("General"))
    		{
    			strForwardPath=strDebitList;
    		}//end of else if(strActiveTab.equals("General"))
			//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataDebitNote")!=null) 
            {
            	tableDataDebitNote=(TableData)(request.getSession()).getAttribute("tableDataDebitNote");
            }//end of if(request.getSession().getAttribute("tableDataCirculars")!=null)
            else
            {
            	tableDataDebitNote=new TableData();
            }//end of else
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		tableDataDebitNote.modifySearchData(strForward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alDebitList = floatAccountManagerObject.getDebitNoteList(tableDataDebitNote.getSearchData());
    		tableDataDebitNote.setData(alDebitList, strForward);//set the table data
    		request.getSession().setAttribute("tableDataDebitNote",tableDataDebitNote);//set the table data object to session
    		return this.getForward(strForwardPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strDebitListExp));
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
    		log.debug("Inside DebitNoteAction doBackward");
    		TableData tableDataDebitNote = null;
    		String strForwardPath="";
    		String strActiveTab="";
    		strActiveTab=TTKCommon.getActiveTab(request);
    		if(strActiveTab.equals("Payments"))
    		{
    			strForwardPath=strDebitListPayment;
    		}//end of if(strActiveTab.equals("Payments"))
    		else if(strActiveTab.equals("General"))
    		{
    			strForwardPath=strDebitList;
    		}//end of else if(strActiveTab.equals("General"))
			//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataDebitNote")!=null) 
            {
            	tableDataDebitNote=(TableData)(request.getSession()).getAttribute("tableDataDebitNote");
            }//end of if(request.getSession().getAttribute("tableDataCirculars")!=null)
            else
            {
            	tableDataDebitNote=new TableData();
            }//end of else
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		tableDataDebitNote.modifySearchData(strBackward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alDebitList = floatAccountManagerObject.getDebitNoteList(tableDataDebitNote.getSearchData());
    		tableDataDebitNote.setData(alDebitList, strBackward);//set the table data
    		request.getSession().setAttribute("tableDataDebitNote",tableDataDebitNote);//set the table data object to session
    		return this.getForward(strForwardPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strDebitListExp));
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
			log.debug("Inside the doAdd method of DebitNoteAction");
			setLinks(request);
			DynaActionForm frmFloatAccDetails =(DynaActionForm)request.getSession().getAttribute("frmFloatAccDetails");
			DynaActionForm frmDebitNoteDetails = (DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption.append("Debit Note Details - ").append("Add [").append(frmFloatAccDetails.getString("floatNo")).append("]");
			frmDebitNoteDetails.initialize(mapping);
			frmDebitNoteDetails.set("debitNoteDate",TTKCommon.getFormattedDate(TTKCommon.getDate()));
			frmDebitNoteDetails.set("caption",String.valueOf(sbfCaption));
			return this.getForward(strDebitDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDebitListExp));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doViewDebitNote(ActionMapping mapping,ActionForm form,HttpServletRequest request,
											HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside DebitNoteAction doViewDebitNote");
			DynaActionForm frmFloatAccDetails =(DynaActionForm)request.getSession().getAttribute("frmFloatAccDetails");
			DebitNoteVO debitNoteVO = null;
			FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
			StringBuffer sbfCaption = new StringBuffer();
			// setting the floatAcc details values based on floatAcctSeqID from form/rownum to the appropriate modes
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				debitNoteVO = (DebitNoteVO)((TableData)request.getSession().getAttribute("tableDataDebitNote")).
						 getData().get(Integer.parseInt(TTKCommon.checkNull(request.getParameter("rownum"))));
				debitNoteVO=floatAccountManagerObject.getDebitNoteDetail(debitNoteVO.getDebitNoteSeqID(),
															  new Long(TTKCommon.getUserSeqId(request).toString()));
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			DynaActionForm  frmDebitNoteDetails = (DynaActionForm)FormUtils.setFormValues("frmDebitNoteDetails",
					debitNoteVO, this, mapping, request);
			frmDebitNoteDetails.set("debitType",debitNoteVO.getDebitNoteTypeID());
			sbfCaption.append("Debit Note Details - ").append("Edit [").append(frmFloatAccDetails.getString("floatNo")).append("]");
			frmDebitNoteDetails.set("caption",String.valueOf(sbfCaption));
			request.getSession().setAttribute("frmDebitNoteDetails",frmDebitNoteDetails);
			return this.getForward(strDebitDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strDebitListExp));
		}//end of catch(Exception exp)
	}//end of doViewDebitNote(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doViewDebitNotePayment(ActionMapping mapping,ActionForm form,HttpServletRequest request,
											HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside DebitNoteAction doViewDebitNotePayment");
			DynaActionForm frmClaims =(DynaActionForm)request.getSession().getAttribute("frmClaims");
			DebitNoteVO debitNoteVO = null;
			/*FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
			StringBuffer sbfCaption = new StringBuffer();*/
			// setting the floatAcc details values based on floatAcctSeqID from form/rownum to the appropriate modes
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				debitNoteVO = (DebitNoteVO)((TableData)request.getSession().getAttribute("tableDataDebitNote")).
						 getData().get(Integer.parseInt(TTKCommon.checkNull(request.getParameter("rownum"))));
				
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			
			frmClaims.set("debitSeqID",debitNoteVO.getDebitNoteSeqID().toString());
			frmClaims.set("debitNumber",debitNoteVO.getDebitNoteNbr());
			//sbfCaption.append("Debit Note Details - ").append("Edit [").append(frmFloatAccDetails.getString("floatNo")).append("]");
			//frmDebitNoteDetails.set("caption",String.valueOf(sbfCaption));
			request.getSession().setAttribute("frmClaims",frmClaims);
			return this.getForward(strDebitClaims, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strDebitListExp));
		}//end of catch(Exception exp)
	}//end of doViewDebitNote(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
    		log.debug("Inside DebitNoteAction doSave");
    		DynaActionForm frmFloatAccDetails =(DynaActionForm)request.getSession().getAttribute("frmFloatAccDetails");
    		DynaActionForm frmDebitNoteDetails=(DynaActionForm)form;
    		DebitNoteVO debitNoteVO = null;
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		StringBuffer sbfCaption = new StringBuffer();
    		debitNoteVO=(DebitNoteVO)FormUtils.getFormValues(frmDebitNoteDetails,this, 
    																		   mapping, request);
    		debitNoteVO.setFloatSeqID(TTKCommon.getWebBoardId(request));
    		debitNoteVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
    		Long iResult =floatAccountManagerObject.saveDebitNoteDetail(debitNoteVO);
    		if((iResult > 0))
    		{
    			//setting updated message in the request
    			if(debitNoteVO.getDebitNoteSeqID()!=null)
    			{
    				//finally modify the web board details, if the account no. is changed
    				debitNoteVO = floatAccountManagerObject.getDebitNoteDetail(iResult,
    																			  TTKCommon.getUserSeqId(request));
    				request.setAttribute("updated","message.savedSuccessfully");
    				sbfCaption.append("Debit Note Details - ").append("Edit [").append(frmFloatAccDetails.getString("floatNo")).append("]");
    			}// end of if(!(TTKCommon.checkNull((String)frmFloatAccDetails.get("floatAcctSeqID")).equals("")))
    			else
    			{
    				debitNoteVO = floatAccountManagerObject.getDebitNoteDetail(iResult,
							  TTKCommon.getUserSeqId(request));
    				request.setAttribute("updated","message.addedSuccessfully");
    				sbfCaption.append("Debit Note Details - ").append("Add [").append(frmFloatAccDetails.getString("floatNo")).append("]");
    				
    			}//end of else
    		}//end of if(iResult!=0)
    		frmDebitNoteDetails = (DynaActionForm)FormUtils.setFormValues("frmDebitNoteDetails",
    				debitNoteVO, this, mapping, request);
    	
    		if(frmDebitNoteDetails.get("debitNoteSeqID")!=null || (!(frmDebitNoteDetails.get("debitNoteSeqID").equals(""))))
			{
    			frmDebitNoteDetails.set("debitType",debitNoteVO.getDebitNoteTypeID());
    			frmDebitNoteDetails.set("finalDate",TTKCommon.getFormattedDate(TTKCommon.getDate()));
    			
    			
			}
    		
    		frmDebitNoteDetails.set("caption",String.valueOf(sbfCaption));
    		request.getSession().setAttribute("frmDebitNoteDetails",frmDebitNoteDetails);
    		request.getSession().removeAttribute("flag");
    		return this.getForward(strDebitDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strDebitListExp));
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
    		log.debug("Inside DebitNoteAction doReset");
    		DynaActionForm frmFloatAccDetails =(DynaActionForm)request.getSession().getAttribute("frmFloatAccDetails");
    		DynaActionForm frmDebitNoteDetails=(DynaActionForm)form;
    		DebitNoteVO debitNoteVO = null;
    		FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
    		StringBuffer sbfCaption = new StringBuffer();
    		
    		debitNoteVO = new DebitNoteVO();
    		if(!(TTKCommon.checkNull(frmDebitNoteDetails.getString("debitNoteSeqID")).equals("")))
    		{
    			//calling business layer to get the batch detail
    			debitNoteVO = floatAccountManagerObject.getDebitNoteDetail(Long.parseLong
    														(frmDebitNoteDetails.getString("debitNoteSeqID")),
    														new Long(TTKCommon.getUserSeqId(request).toString()));
    			sbfCaption.append("Debit Note Details - ").append("Edit [").append(frmFloatAccDetails.getString("floatNo")).append("]");
    		}//end of if(frmFloatAccDetail.get("floatAcctSeqID")!=null && !frmFloatAccDetail.get("floatAcctSeqID").equals(""))
    		else
    		{
    			sbfCaption.append("Debit Note Details - ").append("Add [").append(frmFloatAccDetails.getString("floatNo")).append("]");
    		}//end of else
    		 frmDebitNoteDetails =(DynaActionForm)FormUtils.setFormValues("frmDebitNoteDetails",
    				debitNoteVO, this,mapping,request);
    		frmDebitNoteDetails.set("caption",String.valueOf(sbfCaption));
    		request.getSession().setAttribute("frmDebitNoteDetails",frmDebitNoteDetails);
    		return this.getForward(strDebitDetails,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strDebitListExp));
    	}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
	 * This method is used to delete the selected records from the list.
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
			log.debug("Inside the doDeleteList method of DebitNoteAction");
			setLinks(request);
			FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
			TableData tableDataDebitNote= TTKCommon.getTableData(request);
			StringBuffer sbfDeleteId = new StringBuffer("|");
			//populate the delete string which contains user sequence id's to be deleted
			sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().
													getAttribute("tableDataDebitNote")));
			Long lnguserSeqID=TTKCommon.getUserSeqId(request);
			int iCount = floatAccountManagerObject.deleteDebitNote(sbfDeleteId.append("|").toString(),lnguserSeqID);
			ArrayList alDebitList= null;
			if(iCount == tableDataDebitNote.getData().size())
			{
				tableDataDebitNote.modifySearchData("Delete");//modify the search data
				int iStartRowCount = Integer.parseInt((String)tableDataDebitNote.getSearchData().
												get(tableDataDebitNote.getSearchData().size()-2));
				if(iStartRowCount > 0)
				{
					alDebitList = floatAccountManagerObject.getDebitNoteList(tableDataDebitNote.getSearchData());
				}//end of if(iStartRowCount > 0)
			}//end if(iCount == tableData.getData().size())
			else
			{
				alDebitList = floatAccountManagerObject.getDebitNoteList(tableDataDebitNote.getSearchData());
			}//end of else
			tableDataDebitNote.setData(alDebitList, "Delete");
			request.getSession().setAttribute("tableDataDebitNote",tableDataDebitNote);
			return this.getForward(strDebitList, mapping, request);  
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDebitListExp));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			log.debug("Inside the doClose method of DebitNoteAction");
			setLinks(request);
			//Get the session bean from the beand pool for each thread being excuted.
			FloatAccountManager floatAccountManagerObject=this.getFloatAccountManagerObject();
			TableData tableDataDebitNote = null;
			//get the Table Data From the session 
            if(request.getSession().getAttribute("tableDataDebitNote")!=null) 
            {
            	tableDataDebitNote=(TableData)(request.getSession()).getAttribute("tableDataDebitNote");
            }//end of if(request.getSession().getAttribute("tableDataCirculars")!=null)
            else
            {
            	tableDataDebitNote=new TableData();
            }//end of else
			if(tableDataDebitNote.getSearchData() != null && tableDataDebitNote.getSearchData().size() > 0)
			{
				ArrayList alDebitList = floatAccountManagerObject.getDebitNoteList(tableDataDebitNote.getSearchData());
				tableDataDebitNote.setData(alDebitList);
				request.getSession().setAttribute("tableDataDebitNote",tableDataDebitNote);
				//reset the forward links after adding the records if any
				tableDataDebitNote.setForwardNextLink();
			}//end of if(tableDataDebitNote.getSearchData() != null && tableDataDebitNote.getSearchData().size() > 0)
			return this.getForward(strDebitList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDebitListExp));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doCloseDebitPayment(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												   HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doCloseDebitPayment method of DebitNoteAction");
			setLinks(request);
			
			return this.getForward(strDebitClaims, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strDebitListExp));
		}//end of catch(Exception exp)
	}//end of doCloseDebitPayment(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    /**
	 * Returns the ArrayList which contains the populated search criteria elements
	 * @param  frmFloatlist DynaActionForm will contains the values of corresponding fields
	 * @param request HttpServletRequest object which contains the search parameter that is built
	 * @return alSearchParams ArrayList
	 * @throws TTKException
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmDebitNoteList,HttpServletRequest request) throws TTKException
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(TTKCommon.getWebBoardId(request));
        alSearchParams.add((String)frmDebitNoteList.get("sDebitType"));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmDebitNoteList.getString("sDebitNote")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmDebitNoteList.getString("startdate")));
        alSearchParams.add((String)frmDebitNoteList.get("enddate"));
        alSearchParams.add(TTKCommon.getActiveTab(request));
       // alSearchParams.add(TTKCommon.getUserSeqId(request));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmFloatlist)
	
	/**
	 * Returns a string which contains the comma separated sequence id's to be deleted  
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the comma separated sequence id's to be deleted  
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableDataDebitNote)
	{ 
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			//loop through to populate delete sequence id's and get the value from session for the matching check box value
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{    
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append(String.valueOf(((DebitNoteVO)tableDataDebitNote.getData().
							get(Integer.parseInt(strChk[i]))).getDebitNoteSeqID().intValue()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((DebitNoteVO)tableDataDebitNote.
								getData().get(Integer.parseInt(strChk[i]))).getDebitNoteSeqID().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++) 
		}//end of if(strChk!=null&&strChk.length!=0) 
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableDataDebitNote)
	
	/**
	 * Returns the FloatAccountManager session object for invoking methods on it.
	 * @return FloatAccountManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private FloatAccountManager getFloatAccountManagerObject() throws TTKException
	{
		FloatAccountManager floatAccountManager = null;
		try
		{
			if(floatAccountManager == null)
			{
				InitialContext ctx = new InitialContext();
				floatAccountManager = (FloatAccountManager) ctx.lookup("java:global/TTKServices/business.ejb3/FloatAccountManagerBean!com.ttk.business.finance.FloatAccountManager");
			}//end if(hospManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strDebitListExp);
		}//end of catch
		return floatAccountManager;
	}//end getFloatAccountList()
}//end of DebitNoteAction class

