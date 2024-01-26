/**
 * @ (#) CardPrintingAction.java Feb 15, 2006
 * Project       : TTK HealthCare Services
 * File          : CardPrintingAction.java
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Feb 15, 2006
 * @author       : Bhaskar Sandra
 *
 * Modified by   : Raghavendra T M
 * Modified date : Mar 5, 2006
 * Reason        :
 */

package com.ttk.action.support;

import java.util.ArrayList;
import java.util.HashMap;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.common.messageservices.CommunicationManager;
import com.ttk.business.enrollment.CardPrintingManager;
import com.ttk.business.enrollment.CourierManager;

import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.BatchVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import formdef.plugin.util.FormUtils;

/**
 * This action class used for searching of card printing batches.
 * This class also provides option for creating batches, generate label, print card, cancel batch.
 */

public class CardPrintingAction extends TTKAction {

	private  Logger log = Logger.getLogger( CardPrintingAction.class ); // Getting Logger for this Class file
	
	//	Modes
	private static final String strForward="Forward";
	private static final String strBackward="Backward";
	
	//  Action mapping forwards
	private static final String strCardPrinting="cardprinting";
	private static final String strCreateCardBatch="createcardbatch";
	private static final String strChngCorporate="changecorporate";
	private static final String strCardBatchChangeOffice="changeoffice";
	private static final String strForwardCardDetail="carddetails";

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
			log.debug("Inside CardPrintingAction doDefault");
			setLinks(request);
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			String strForwardList=getForwardPath(request);
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
											request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			TableData tableData =null;
			DynaActionForm frmCardPrinting=(DynaActionForm)form;
			if((request.getSession()).getAttribute("tableDataCardBatch") != null)
				tableData= (TableData)(request.getSession()).getAttribute("tableDataCardBatch");
			else
				tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("CardPrintingTable",new ArrayList());
			request.getSession().setAttribute("tableDataCardBatch",tableData);
			((DynaActionForm)form).initialize(mapping);//reset the form data
			frmCardPrinting.set("PrintStatus","BNP"); //setting default status has (not printed)
			if(strActiveSubLink.equals("Courier"))
			{
				((Column)((ArrayList)tableData.getTitle()).get(3)).setVisibility(false);
			}// end of if(strSubLink.equals("Courier"))
			frmCardPrinting.set("CompanyName", strDefaultBranchID);
			return this.getForward(strForwardList,mapping,request);

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doViewCard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CardPrintingAction doViewCard");
			setLinks(request);
			String strFields ="";
			String strCardType="";
			String strCardTemplate="";
			TableData tableData=null;
			if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableDataCardBatch");
			}//end of if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			else
			{
				tableData = new TableData();
			}//end of else
			CardPrintingManager cardPrintingManagerObject=this.getCardPrintingManagerObject();
			//get the batch No of the Selected record
			Long strBatchNo=((BatchVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")))).
										getBatchSeqID();

			ArrayList alCardDetail = cardPrintingManagerObject.getCardBatchDetail(strBatchNo);
			strFields = (String)alCardDetail.get(0);
			strCardTemplate = (String)alCardDetail.get(1);
			strCardType = (String)alCardDetail.get(2);

			strFields=TTKCommon.replaceInString(strFields,".","<br>");
			request.setAttribute("cardFields",strFields);
			request.setAttribute("cardType",strCardType);
			request.setAttribute("cardTemplate",strCardTemplate);
			//return mapping.findForward(strForwardCardDetail);
			return this.getForward(strForwardCardDetail,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
		}//end of catch(Exception exp)
	}//end of doViewCard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside CardPrintingAction doSearch");
			setLinks(request);
			String strComma="";
			String batchpath = "";
			batchpath = TTKPropertiesReader.getPropertyValue("cardbatchpath");
			DynaActionForm frmCardPrinting=(DynaActionForm)form;
			StringBuffer strCardBatchPath = new StringBuffer();
			ArrayList alCardPrintingList = null;
			TableData tableData=null;
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableDataCardBatch");
			}//end of if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			else
			{
				tableData = new TableData();
			}//end of else
			String strForwardList=getForwardPath(request);
			CardPrintingManager cardPrintingManagerObject=this.getCardPrintingManagerObject();
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
					return (mapping.findForward(strForwardList));					
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("CardPrintingTable",null);
				//setting a visibility of columns has based on print status
				if(strActiveSubLink.equals("Courier"))
				{
					((Column)((ArrayList)tableData.getTitle()).get(3)).setVisibility(false);
				}//end of
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				//sorting based on CARD_BATCH_SEQ_ID in descending order
				tableData.setSortData("0001");
				tableData.setSortColumnName("CARD_BATCH_SEQ_ID");
				tableData.setSortOrder("DESC");
				tableData.modifySearchData("search");
			}//end of else
			alCardPrintingList= cardPrintingManagerObject.getCardPrintingList(tableData.getSearchData());
			tableData.setData(alCardPrintingList, "search");
			//impl for pdf file paths
			if(alCardPrintingList != null)
			{
				for(int i=0 ;i<alCardPrintingList.size() ;i++)
				{
					if(i!=0)
					{
						strComma=",";
					}//end of if(i!=0)
					if(((BatchVO)alCardPrintingList.get(i)).getBatchSeqID()!=null)
					{
						if(((BatchVO)alCardPrintingList.get(i)).getCardTypeID() != null){
							if(((BatchVO)alCardPrintingList.get(i)).getCardTypeID().equals("CCI")){
								strCardBatchPath  = strCardBatchPath.append(strComma+"\""+batchpath+"card_batch_"+
										((BatchVO)alCardPrintingList.get(i)).getBatchSeqID().toString()+".pdf"+"\"");
							}//end of if(((BatchVO)alCardPrintingList.get(i)).getCardTypeID().equals("CCI"))
							else if(((BatchVO)alCardPrintingList.get(i)).getCardTypeID().equals("CCE")){
								strCardBatchPath  = strCardBatchPath.append(strComma+"\""+batchpath+"ecard_batch_"+
										((BatchVO)alCardPrintingList.get(i)).getBatchSeqID().toString()+".pdf"+"\"");
							}//end of if(((BatchVO)alCardPrintingList.get(i)).getCardTypeID().equals("CCE"))
							else{
								strCardBatchPath  = strCardBatchPath.append(strComma+"\""+batchpath+"pcard_batch_"+
										((BatchVO)alCardPrintingList.get(i)).getBatchSeqID().toString()+".pdf"+"\"");
							}//end of else
						}//end of if(((BatchVO)alCardPrintingList.get(i)).getCardTypeID() != null)
						else
						{
							strCardBatchPath  = strCardBatchPath.append(strComma+batchpath+"leftmidlephoto"+".pdf");
						}//end of else
					}//end of if(((BatchVO)alCardPrintingList.get(i)).getBatchSeqID()!=null)
					else
					{
						strCardBatchPath  = strCardBatchPath.append(strComma+batchpath+"leftmidlephoto"+".pdf");
					}//end of else
				}//end of for(int i=0 ;i<alCardPrintingList.size() ;i++)
				frmCardPrinting.set("CardBatchPath",strCardBatchPath.toString());
			}//end of if(alCardPrintingList != null)
			//set the table data object to session
			request.getSession().setAttribute("tableDataCardBatch",tableData);
			//finally return to the grid screen
			return this.getForward(strForwardList,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
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
			log.debug("Inside CardPrintingAction doForward");
			setLinks(request);
			TableData tableData=null;
			String strComma="";
			String batchpath = "";
			batchpath = TTKPropertiesReader.getPropertyValue("cardbatchpath");
			ArrayList alCardPrintingList = null;
			StringBuffer strCardBatchPath = new StringBuffer();
			String strForwardList=getForwardPath(request);
			CardPrintingManager cardPrintingManagerObject=this.getCardPrintingManagerObject();
			//String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			DynaActionForm frmCardPrinting=(DynaActionForm)form;
			if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableDataCardBatch");
			}//end of if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			else
			{
				tableData = new TableData();
			}//end of else
			tableData.modifySearchData(strForward);//modify the search data
			alCardPrintingList = cardPrintingManagerObject.getCardPrintingList(tableData.getSearchData());
			tableData.setData(alCardPrintingList, strForward);//set the table data
			//impl for pdf file paths
			if(alCardPrintingList != null)
			{
				for(int i=0 ;i<alCardPrintingList.size() ;i++)
				{
					if(i!=0)
					{
						strComma=",";
					}//end of if(i!=0)
					if(((BatchVO)alCardPrintingList.get(i)).getBatchSeqID()!=null)
					{
						strCardBatchPath  = strCardBatchPath.append(strComma+"\""+batchpath+"card_batch_"+
										((BatchVO)alCardPrintingList.get(i)).getBatchSeqID().toString()+".pdf"+"\"");
					}//end of if(((BatchVO)alCardPrintingList.get(i)).getBatchSeqID()!=null)
					else
					{
						strCardBatchPath  = strCardBatchPath.append(strComma+batchpath+"leftmidlephoto"+".pdf");
					}//end of else
				}//end of for(int i=0 ;i<alCardPrintingList.size() ;i++)
				frmCardPrinting.set("CardBatchPath",strCardBatchPath.toString());
			}//end of if(alCardPrintingList != null)
			request.getSession().setAttribute("tableDataCardBatch",tableData);//set the table data object to session
			return this.getForward(strForwardList,mapping,request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
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
			log.debug("Inside CardPrintingAction doBackward");
			setLinks(request);
			TableData tableData=null;
			String strComma="";
			DynaActionForm frmCardPrinting=(DynaActionForm)form;
			String batchpath = "";
			batchpath = TTKPropertiesReader.getPropertyValue("cardbatchpath");
			String strForwardList=getForwardPath(request);
			CardPrintingManager cardPrintingManagerObject=this.getCardPrintingManagerObject();
			ArrayList alCardPrintingList = null;
			StringBuffer strCardBatchPath = new StringBuffer();
			if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableDataCardBatch");
			}//end of if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			else
			{
				tableData = new TableData();
			}//end of else
			tableData.modifySearchData(strBackward);//modify the search data
			alCardPrintingList = cardPrintingManagerObject.getCardPrintingList(tableData.getSearchData());
			tableData.setData(alCardPrintingList, strBackward);//set the table data
			//impl for pdf file paths
			if(alCardPrintingList != null)
			{
				for(int i=0 ;i<alCardPrintingList.size() ;i++)
				{
					if(i!=0)
					{
						strComma=",";
					}//end of if(i!=0)
					if(((BatchVO)alCardPrintingList.get(i)).getBatchSeqID()!=null)
					{
						strCardBatchPath  = strCardBatchPath.append(strComma+"\""+batchpath+"card_batch_"+
										((BatchVO)alCardPrintingList.get(i)).getBatchSeqID().toString()+".pdf"+"\"");
					}//end of if(((BatchVO)alCardPrintingList.get(i)).getBatchSeqID()!=null)
					else
					{
						strCardBatchPath  = strCardBatchPath.append(strComma+batchpath+"leftmidlephoto"+".pdf");
					}//end of else
				}//end of for(int i=0 ;i<alCardPrintingList.size() ;i++)
				frmCardPrinting.set("CardBatchPath",strCardBatchPath.toString());
			}//end of if(alCardPrintingList != null)
			request.getSession().setAttribute("tableDataCardBatch",tableData);//set the table data object to session
			return this.getForward(strForwardList,mapping,request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


	/**
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
			log.debug("Inside CardPrintingAction doAdd");
			setLinks(request);
			BatchVO batchVO = new BatchVO();
			UserSecurityProfile userSecurityProfile=(UserSecurityProfile)
														request.getSession().getAttribute("UserSecurityProfile");
			DynaActionForm  frmCreateCardBatch = (DynaActionForm)FormUtils.setFormValues("frmCreateCardBatch",batchVO,
													this, mapping, request);
			request.getSession().setAttribute("frmCreateCardBatch",frmCreateCardBatch);
			if(userSecurityProfile.getBranchID()!=null)
			{
				frmCreateCardBatch.set("officeSeqID", userSecurityProfile.getBranchID().toString());
			}//end of if(userSecurityProfile.getBranchID()!=null)
			else
			{
				frmCreateCardBatch.set("officeSeqID", "");
			}//end of else
			return mapping.findForward(strCreateCardBatch);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
		}//end of catch(Exception exp)
	}//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
									HttpServletResponse response) throws Exception
	{
		try{
			log.debug("Inside CardPrintingAction doSave");
			setLinks(request);
			BatchVO batchVO=null;
			CardPrintingManager cardPrintingManagerObject=this.getCardPrintingManagerObject();
			DynaActionForm frmCreateCardBatch=(DynaActionForm)form;
			batchVO=(BatchVO)FormUtils.getFormValues(frmCreateCardBatch, "frmCreateCardBatch",this, mapping, request);
			batchVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID from session
			int iResult = cardPrintingManagerObject.createCardBatch(batchVO);
			if((iResult!=0))
			{
				//setting updated message in the request
				request.setAttribute("updated","message.addedSuccessfully");
				((DynaActionForm)form).initialize(mapping);//reset the form data
				batchVO = new BatchVO();
			}//end of if(iResult!=0)
			else
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.cardbatch.required");
				throw expTTK;
			}
			DynaActionForm frmCardBatch = (DynaActionForm)FormUtils.setFormValues("frmCreateCardBatch",batchVO,
											this, mapping, request);
			request.getSession().setAttribute("frmCreateCardBatch",frmCardBatch);
			return this.getForward(strCreateCardBatch,mapping,request);

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.info("Inside CardPrintingAction doClose");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			CardPrintingManager cardPrintingManagerObject=this.getCardPrintingManagerObject();
			if(tableData.getSearchData()!= null&& tableData.getSearchData().size()>0)
			{
				ArrayList alCardPrintingList= cardPrintingManagerObject.getCardPrintingList(tableData.getSearchData());
				tableData.setData(alCardPrintingList, "search");
				request.getSession().setAttribute("tableData",tableData);
				//reset the forward links after adding the records if any
				tableData.setForwardNextLink();
			}//end of if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			return this.getForward(strCardPrinting, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"courierdetail"));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to print the card detail.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doCardPrintingDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
												HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CardPrintingAction doCardPrintingDetails");
			setLinks(request);
			//DynaActionForm frmCardPrintingList=(DynaActionForm)form;
			TableData tableData=null;
			ArrayList alCardPrintingList = null;
			if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableDataCardBatch");
			}//end of if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			else
			{
				tableData = new TableData();
			}//end of else
			String strConcatenatedSeqID= this.getConcatenatedSeqID(request,(TableData)
															request.getSession().getAttribute("tableDataCardBatch"));
			CardPrintingManager cardPrintingManagerObject=this.getCardPrintingManagerObject();
			int iResult = cardPrintingManagerObject.setCardBatch(strConcatenatedSeqID ,
							TTKCommon.getUserSeqId(request),TTKCommon.checkNull(request.getParameter("flag")));
			if(iResult!=0)
			{
				//flag determines as follows GL-generate labels,PL-print complete,CB-cancel batch
				if(TTKCommon.checkNull(request.getParameter("flag")).equals("PL"))
				{
					request.setAttribute("updated","message.printCompletedSuccessfully");
				}//end of if(TTKCommon.checkNull(request.getParameter("flag")).equals("PL"))
				else
				{
					request.setAttribute("updated","message.batchCancledSuccessfully");
				}//end of else
				if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
				{
					//refresh the data in cancel mode, in order to get the new records if any.
					alCardPrintingList = cardPrintingManagerObject.getCardPrintingList(tableData.getSearchData());
					tableData.setData(alCardPrintingList);
					request.getSession().setAttribute("tableDataCardBatch",tableData);
					//reset the forward links after adding the records if any
					tableData.setForwardNextLink();
				}//end if
			}//end of if(iResult!=0)
			return this.getForward(strCardPrinting,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
		}//end of catch(Exception exp)
	}//end of doCardPrintingDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	 //HttpServletResponse response)

	/**
	 * This method is used to generate label.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doGenerateLabel(ActionMapping mapping,ActionForm form,HttpServletRequest request,
							HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CardPrintingAction doGenerateLabel");
			setLinks(request);
			//DynaActionForm frmCardPrintingList=(DynaActionForm)form;
			String strConcatenatedSeqID= this.getConSeqID(request,(TableData)
											request.getSession().getAttribute("tableDataCardBatch"));
			String strCardLableURL="/ReportsAction.do?mode=doGenerateReport&parameter="+strConcatenatedSeqID+
									"&fileName=generalreports/CardLable.jrxml&reportID=SupCardLb&reportType=PDF";
			request.setAttribute("CardLableURL",strCardLableURL);
			return this.getForward(strCardPrinting,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
		}//end of catch(Exception exp)
	}//end of doGenerateLabel(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * This method is used to generate dispatch list.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doGenerateDispatchList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
													HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CardPrintingAction doGenerateDispatchList");
			setLinks(request);
			TableData tableData=null;
			//ArrayList alCardPrintingList = null;
			if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableDataCardBatch");
			}//end of if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			else
			{
				tableData = new TableData();
			}//end of else
			//get the batch No of the Selected record
			String strBatchNo=((BatchVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("chkopt")))).
											getBatchNbr();
			//construct the Dispatch List URL for displaying the report
			String strDispatchListURL="/ReportsAction.do?mode=doGenerateReport&parameter=|"+
				   strBatchNo.replaceAll("&","__")+"|&fileName=generalreports/DispatchReport.jrxml&reportID=SupDispRpt&reportType=PDF";
			request.setAttribute("DispatchListURL",strDispatchListURL);
			return this.getForward(strCardPrinting,mapping,request);

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
		}//end of catch(Exception exp)
	}//end of doGenerateDispatchList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * This method is used to change the enrollment type.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeEnrollType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
											HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CardPrintingAction doChangeEnrollType");
			setLinks(request);
			DynaActionForm frmCreateCardBatch=(DynaActionForm)form;
			ArrayList alInsProducts = null;
			if(!(TTKCommon.checkNull(frmCreateCardBatch.getString("insuranceSeqID"))).equals(""))
			{
				HashMap hmInsProducts = (HashMap)request.getSession().getAttribute("hmInsProducts");
				alInsProducts =(ArrayList)hmInsProducts.get(frmCreateCardBatch.getString("enrolTypeID"));
				if((alInsProducts!=null))
				{
					frmCreateCardBatch.set("alInsProducts",alInsProducts);
				}//end of if((alInsProducts!=null))
				else
				{
					frmCreateCardBatch.set("alInsProducts",new ArrayList());
				}//end of else
			}//end of if(!(TTKCommon.checkNull(frmCreateCardBatch.getString("insuranceSeqID"))).equals(""))
			request.getSession().setAttribute("frmCreateCardBatch",frmCreateCardBatch);
			return this.getForward(strCreateCardBatch, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
		}//end of catch(Exception exp)
	}//end of doChangeEnrollType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * This method is used to select corporate.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
											HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CardPrintingAction doChangeCorporate");
			setLinks(request);
			return mapping.findForward(strChngCorporate);
			//return mapping.findForward(strCardBatchChangeOffice);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
		}//end of catch(Exception exp)
	}//end of doChangeCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * This method is used to select the insurance company.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doOfficeChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CardPrintingAction doOfficeChange");
			setLinks(request);

			return mapping.findForward(strCardBatchChangeOffice);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
		}//end of catch(Exception exp)
	}//end of doOfficeChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * This method is used to select card.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSelectCard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CardPrintingAction doSelectCard");
			setLinks(request);
			DynaActionForm frmGeneral = null;
			BatchVO batchVO=null;
			TableData tableData=null;
			if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			{
				tableData= (TableData)(request.getSession()).getAttribute("tableDataCardBatch");
			}//end of if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			else
			{
				tableData = new TableData();
			}//end of else
			String strForwardDetails="couriercarddetails";
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				batchVO = (BatchVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				frmGeneral = (DynaActionForm) request.getSession().getAttribute("frmCourierDetails");
				frmGeneral.set("frmChanged","changed");
				frmGeneral.set("cardBatchSeqID",String.valueOf(batchVO.getBatchSeqID()));
				frmGeneral.set("cardBatchNbr",batchVO.getBatchNbr());
				frmGeneral.set("batchDate",String.valueOf(batchVO.getBatchDate()));
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			request.getSession().removeAttribute("frmCardPrinting");
			request.getSession().removeAttribute("tableDataCardBatch");
			return (mapping.findForward(strForwardDetails));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
		}//end of catch(Exception exp)
	}//end of doSelectCard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

	/**
	 * This method is used to send email
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSendMail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
									 HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CardPrintingAction doSendMail");
			setLinks(request);
			TableData tableData=null;
			if((request.getSession()).getAttribute("tableDataCardBatch") != null){
				tableData= (TableData)(request.getSession()).getAttribute("tableDataCardBatch");
			}//end of 	if((request.getSession()).getAttribute("tableDataCardBatch") != null)
			else{
				tableData = new TableData();
			}//end fo else	
			String strBatchSeqID= String.valueOf(((BatchVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")))).getBatchSeqID());
			//log.info(" batch seq id  "+strBatchSeqID);
        	CommunicationManager communicationManager=this.getCommunicationManagerObject();
        	//CommunicationOptionVO cOptionVO = new CommunicationOptionVO();
        	//communicationManager.sendMessage(cOptionVO);
        	communicationManager.getEnrollNumbers(strBatchSeqID);
//        	communicationManager.sendMessage("EMAIL");
            return this.getForward(strCardPrinting,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"card printing"));
		}//end of catch(Exception exp)
	}//end of doSendMail(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
						sbfConcatenatedSeqId.append(String.valueOf(((BatchVO)tableData.getRowInfo(
								Integer.parseInt(strChOpt[iCounter]))).getBatchSeqID()));
					}//end of if(iCounter==0)
					else
					{
						sbfConcatenatedSeqId.append("|").append(String.valueOf(((BatchVO)tableData.getRowInfo(
								Integer.parseInt(strChOpt[iCounter]))).getBatchSeqID()));
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		sbfConcatenatedSeqId.append("|");
		return sbfConcatenatedSeqId.toString();
	} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)

	/**Returns the String of concatenated string of contact_seq_id delimeted by '|'.
	 * @param HttpServletRequest
	 * @param TableData
	 * @return String
	 */
	private String getConSeqID(HttpServletRequest request,TableData tableData) {
		StringBuffer sbfConcatenatedSeqId=new StringBuffer("");
		String strChOpt[]=request.getParameterValues("chkopt");
		if((strChOpt!=null)&& strChOpt.length!=0)
		{
			for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
			{
				if(strChOpt[iCounter]!=null)
				{
					if(iCounter==0)
					{
						sbfConcatenatedSeqId.append(String.valueOf(((BatchVO)tableData.getRowInfo(
								Integer.parseInt(strChOpt[iCounter]))).getBatchSeqID()));
					}//end of if(iCounter==0)
					else
					{
						sbfConcatenatedSeqId.append(String.valueOf(((BatchVO)tableData.getRowInfo(
								Integer.parseInt(strChOpt[iCounter]))).getBatchSeqID()));
					}//end of else
				} // end of if(strChOpt[iCounter]!=null)
			} //end of for(int iCounter=0;iCounter<strChOpt.length;iCounter++)
		} // end of if((strChOpt!=null)&& strChOpt.length!=0)
		//sbfConcatenatedSeqId.append("|");
		return sbfConcatenatedSeqId.toString();
	} // end of getConcatenatedSeqID(HttpServletRequest request,TableData tableData)

	/**
	 * This method builds all the search parameters to ArrayList and places them in session
	 * @param frmCardPrinting DynaActionForm
	 * @param request HttpServletRequest
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmCardPrinting,HttpServletRequest request)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmCardPrinting.get("BatchNbr")));
		String strOfficeSeqID=TTKCommon.checkNull((String)frmCardPrinting.get("CompanyName")).equals("")? null:
															(String)frmCardPrinting.get("CompanyName");
		alSearchParams.add(strOfficeSeqID);
		alSearchParams.add((String)frmCardPrinting.get("PrintStatus"));
		alSearchParams.add(new Long(TTKCommon.getUserSeqId(request).toString()));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm searchFeedbackForm,HttpServletRequest request)

	/**
	 * Returns the CardPrintingManager session object for invoking methods on it.
	 * @return CardPrintingManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private CardPrintingManager getCardPrintingManagerObject() throws TTKException
	{
		CardPrintingManager cardPrintingManager = null;
		try
		{
			if(cardPrintingManager == null)
			{
				InitialContext ctx = new InitialContext();
				cardPrintingManager = (CardPrintingManager) ctx.lookup("java:global/TTKServices/business.ejb3/CardPrintingManagerBean!com.ttk.business.enrollment.CardPrintingManager");
			}//end of if(cardPrintingManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "CardPrintingManager");
		}//end of catch
		return cardPrintingManager;
	}//end of getCardPrintingManagerObject()

	/**
	 * Returns the CommunicationManager session object for invoking methods on it.
	 * @return CommunicationManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private CommunicationManager getCommunicationManagerObject() throws TTKException
	{
		CommunicationManager communicationManager = null;
		try
		{
			if(communicationManager == null)
			{
				InitialContext ctx = new InitialContext();
				communicationManager = (CommunicationManager) ctx.lookup("java:global/TTKServices/business.ejb3/CommunicationManagerBean!com.ttk.business.common.messageservices.CommunicationManager");
			}//end of if(cardPrintingManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "CardPrintingManager");
		}//end of catch
		return communicationManager;
	}//end of getCommunicationManagerObject()

	/**
	 * This method is used for getting forward path depending upon the flow.
	 * @param request The HTTP request we are processing.
	 * @return String contains the flow to which it needs to forward.
	 * @throws Exception if any error occurs
	 */
	private String getForwardPath(HttpServletRequest request)throws TTKException
	{
		String strForwardList="";
		try
		{
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);

			//	checking of the active sub link to forward to appropriate path
			if(strActiveSubLink.equals("Card Printing"))
			{
				strForwardList=strCardPrinting;
			}// end of if(strSubLink.equals("Card Printing"))
			else if(strActiveSubLink.equals("Courier"))
			{
				strForwardList="couriercardlist";
			}// end of if(strSubLink.equals("Courier"))
		}catch(Exception exp)
		{
			throw new TTKException(exp, "contactAction");
		}//end of catch
		return strForwardList;
	}//end of getForwardPath(HttpServletRequest request)
}//end of CardPrintingAction