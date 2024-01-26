/**
 * @ (#) ListofBillsAction.java July 23,2006
 * Project       : TTK HealthCare Services
 * File          : ListofBillsAction.java
 * Author        : Arun K.M
 * Company       : Span Systems Corporation
 * Date Created  : July 13, 2006
 *
 * @author       : Arun K.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.claims;

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
import com.ttk.business.claims.ClaimBillManager;
import com.ttk.business.claims.ClaimManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.claims.ClaimBillVO;
import com.ttk.dto.claims.LineItemVO;
import com.ttk.dto.claims.ClaimBillDetailVO;
import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of List of Bills.
 * This class also provides option for deletion of bills.
 */
public class ListofBillsAction extends TTKAction {
    private static Logger log = Logger.getLogger(ListofBillsAction.class);

    //Modes.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";
    private static final String strInclude="Include";
    private static final String strExclude="Exclude";

    //Action mapping forwards.
    private static final String strBilllist="billlist";
    private static final String strbilldetails="billdetails";
    private static final String strBillsummary="billsummary";

    //  Exception Message Identifier
    private static final String strBillListError="bill";

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
    		log.debug("Inside the doDefault method of ListofBillsAction");
    		setLinks(request);
    		if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
            {
                TTKException expTTK = new TTKException();
                expTTK.setMessage("error.Claims.required");
                throw expTTK;
            }//end of if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
    		TableData  tableData =TTKCommon.getTableData(request);
    		StringBuffer sbfCaption=new StringBuffer();
    		DynaActionForm frmListofBills=(DynaActionForm)form;//reset the form data
            frmListofBills.initialize(mapping);
            //create new table data object
            tableData = new TableData();
            //create the required grid table
            tableData.createTableInfo("BillsTable",new ArrayList());
            sbfCaption.append("[").append(ClaimsWebBoardHelper.getClaimantName(request)).append("]");
            request.getSession().setAttribute("tableData",tableData);
            frmListofBills.set("caption",sbfCaption.toString());
            request.getSession().setAttribute("frmListofBills",frmListofBills);
            return this.getForward(strBilllist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBillListError));
		}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    	log.debug("Inside doChangeWebBoard method of ListofBillsAction");
    	//ChangeWebBoard method will call doDefault() method internally.
    	return doDefault(mapping,form,request,response);
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    		log.debug("Inside the doSearch method of ListofBillsAction");
    		setLinks(request);
    		ClaimBillManager billManagerObject=this.getbillManagerObject();
    		TableData  tableData =TTKCommon.getTableData(request);
    		StringBuffer sbfCaption=new StringBuffer();
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
            if(!strPageID.equals("") || !strSortID.equals(""))
            {
                if(!strPageID.equals(""))
                {
                    tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
                    return (mapping.findForward(strBilllist));
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
                tableData.createTableInfo("BillsTable",null);
                //fetch the data from the data access layer and set the data to table object
                tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
                //sorting based on enrol_batch_seq_id in descending order
				tableData.setSortData("0001");
				tableData.setSortColumnName("CLM_BILL_SEQ_ID");
				tableData.setSortOrder("ASC");
                tableData.modifySearchData("search");
            }//end of else
            ArrayList alBill=billManagerObject.getBillList(tableData.getSearchData());
            tableData.setData(alBill, "search");
            request.getSession().setAttribute("tableData",tableData);
            sbfCaption.append("[").append(ClaimsWebBoardHelper.getClaimantName(request)).append("]");
            //finally return to the grid screen
            return this.getForward(strBilllist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBillListError));
		}//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside the doBackward method of ListofBillsAction");
    		setLinks(request);
    		ClaimBillManager billManagerObject=this.getbillManagerObject();
    		TableData  tableData =TTKCommon.getTableData(request);
    		tableData.modifySearchData(strBackward);//modify the search data
            //fetch the data from the data access layer and set the data to table object
            ArrayList alBill = billManagerObject.getBillList(tableData.getSearchData());
            tableData.setData(alBill, strBackward);//set the table data
            request.getSession().setAttribute("tableData",tableData);//set the table data object to session
            return this.getForward(strBilllist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBillListError));
		}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside the doForward method of ListofBillsAction");
    		setLinks(request);
    		ClaimBillManager billManagerObject=this.getbillManagerObject();
    		TableData  tableData =TTKCommon.getTableData(request);
    		tableData.modifySearchData(strForward);//modify the search data
            //fetch the data from the data access layer and set the data to table object
            ArrayList alBill = billManagerObject.getBillList(tableData.getSearchData());
            tableData.setData(alBill, strForward);//set the table data
            request.getSession().setAttribute("tableData",tableData);//set the table data object to session
            return this.getForward(strBilllist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBillListError));
		}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to selected record(s) to be included.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doInclude(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    											HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doInclude method of ListofBillsAction");
    		setLinks(request);
    		ClaimBillManager billManagerObject=this.getbillManagerObject();
    		TableData  tableData =TTKCommon.getTableData(request);
    		StringBuffer sbfIncludeExcludeId = new StringBuffer("|");
            String strAssociateCode="";
            strAssociateCode="INC";
            int iCount=0;

            //populate the delete string which contains the sequence id's to be included/excluded
            sbfIncludeExcludeId.append(populateDeleteId(request,
            											(TableData)request.getSession().getAttribute("tableData")));
             log.debug("Sequence Id to be IncludeExclude !!!  : "+sbfIncludeExcludeId.toString());
             ArrayList<Object> alIncludeExcludeIdList = new ArrayList<Object>();

             alIncludeExcludeIdList.add(sbfIncludeExcludeId.toString());
             alIncludeExcludeIdList.add("APL");
             alIncludeExcludeIdList.add(TTKCommon.getUserSeqId(request));

             iCount=billManagerObject.includeExcludeBill(ClaimsWebBoardHelper.getClaimsSeqId(request),strAssociateCode,
             																	alIncludeExcludeIdList);

             //refresh the grid with search data in session
               ArrayList alBillList = null;
               //fetch the data from previous set of rowcounts, if all the records are deleted for the current
               //set of search criteria
                if(iCount == tableData.getData().size())
                {
                	tableData.modifySearchData(strInclude);//modify the search data
                	int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(
                																tableData.getSearchData().size()-2));
                	if(iStartRowCount > 0)
                	{
                		alBillList=billManagerObject.getBillList(tableData.getSearchData());
                	}//end of if(iStartRowCount > 0)
                }//end if(iCount == tableData.getData().size())
                else
                {
                	alBillList=billManagerObject.getBillList(tableData.getSearchData());
                }//end of else
                tableData.setData(alBillList, strInclude);
                request.getSession().setAttribute("tableData",tableData);
                return this.getForward(strBilllist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBillListError));
		}//end of catch(Exception exp)
    }//end of doInclude(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to selected record(s) to be excluded.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doExclude(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    														HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doExclude method of ListofBillsAction");
    		setLinks(request);
    		ClaimBillManager billManagerObject=this.getbillManagerObject();
    		TableData  tableData =TTKCommon.getTableData(request);
    		StringBuffer sbfIncludeExcludeId = new StringBuffer("|");
            String strAssociateCode="";
            strAssociateCode="EXC";
            int iCount=0;

            //populate the delete string which contains the sequence id's to be included/excluded
            sbfIncludeExcludeId.append(populateDeleteId(request,(TableData)
            														request.getSession().getAttribute("tableData")));
             log.debug("Sequence Id to be IncludeExclude !!!  : "+sbfIncludeExcludeId.toString());
             ArrayList<Object> alIncludeExcludeIdList = new ArrayList<Object>();

             alIncludeExcludeIdList.add(sbfIncludeExcludeId.toString());
             alIncludeExcludeIdList.add("APL");
             alIncludeExcludeIdList.add(TTKCommon.getUserSeqId(request));

             iCount=billManagerObject.includeExcludeBill(ClaimsWebBoardHelper.getClaimsSeqId(request),strAssociateCode,
             												alIncludeExcludeIdList);

             //refresh the grid with search data in session
             ArrayList alBillList = null;
             //fetch the data from previous set of rowcounts, if all the records are deleted for the current set
             //of search criteria
                if(iCount == tableData.getData().size())
                {
                	tableData.modifySearchData(strExclude);//modify the search data
                	int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(
                																tableData.getSearchData().size()-2));
                	if(iStartRowCount > 0)
                	{
                		alBillList=billManagerObject.getBillList(tableData.getSearchData());
                	}//end of if(iStartRowCount > 0)
                }//end if(iCount == tableData.getData().size())
                else
                {
                	alBillList=billManagerObject.getBillList(tableData.getSearchData());
                }//end of else
                tableData.setData(alBillList, strExclude);
                request.getSession().setAttribute("tableData",tableData);
                return this.getForward(strBilllist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBillListError));
		}//end of catch(Exception exp)
    }//end of doExclude(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside the doAdd method of ListofBillsAction");
    		setLinks(request);
    		StringBuffer sbfCaption=new StringBuffer();
    		LineItemVO lineItemVO=null;
    		ClaimBillDetailVO claimBillDetailVO=null;
    		String strAmmendmentYN ="";
            DynaActionForm frmBillDetail = (DynaActionForm)form;
            sbfCaption.append("Add");
            claimBillDetailVO = new ClaimBillDetailVO();
            lineItemVO = new LineItemVO();
            claimBillDetailVO.setLineItemVO(lineItemVO);
            sbfCaption.append("[").append(ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
            												ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]");
            frmBillDetail = (DynaActionForm)FormUtils.setFormValues("frmClaimBillDetails", claimBillDetailVO, this,
            																mapping, request);
            frmBillDetail.set("caption",sbfCaption.toString());
            frmBillDetail.set("lineItemVO",FormUtils.setFormValues("frmBillLineItem",claimBillDetailVO.getLineItemVO(),
            															this,mapping,request));
            frmBillDetail.set("ammendmentYN",strAmmendmentYN);
            request.getSession().setAttribute("frmClaimBillDetails",frmBillDetail);
            return this.getForward(strbilldetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBillListError));
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
    public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    									HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doView method of ListofBillsAction");
    		setLinks(request);
    		StringBuffer sbfCaption=new StringBuffer();
    		ClaimBillVO claimBillVO=null;
    		ClaimBillDetailVO claimBillDetailVO=null;
    		ClaimBillManager billManagerObject=this.getbillManagerObject();
    		TableData  tableData =TTKCommon.getTableData(request);
    		String strAmmendmentYN ="";
            DynaActionForm frmBillDetail = (DynaActionForm)form;
            if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
                sbfCaption.append("Edit");
                claimBillVO =(ClaimBillVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
                strAmmendmentYN = claimBillVO.getAmmendmentYN();
                claimBillDetailVO=billManagerObject.getBillDetail(claimBillVO.getBillSeqID(),
                													TTKCommon.getUserSeqId(request));
            }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            sbfCaption.append("[").append(ClaimsWebBoardHelper.getClaimantName(request)).append("][").
            									append(ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]");
            frmBillDetail = (DynaActionForm)FormUtils.setFormValues("frmClaimBillDetails", claimBillDetailVO, this,
            															mapping, request);
            frmBillDetail.set("caption",sbfCaption.toString());
            frmBillDetail.set("lineItemVO",FormUtils.setFormValues("frmBillLineItem",
            								claimBillDetailVO.getLineItemVO(),this,mapping,request));
            frmBillDetail.set("ammendmentYN",strAmmendmentYN);
            request.getSession().setAttribute("frmClaimBillDetails",frmBillDetail);
            return this.getForward(strbilldetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBillListError));
		}//end of catch(Exception exp)
    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside the doSave method of ListofBillsAction");
    		setLinks(request);
    		LineItemVO lineItemVO=null;
    		ClaimBillDetailVO claimBillDetailVO=null;
    		ClaimBillManager billManagerObject=this.getbillManagerObject();
    		DynaActionForm frmBillDetail = (DynaActionForm)form;
            String strCaption  = frmBillDetail.getString("caption");
            claimBillDetailVO =(ClaimBillDetailVO)FormUtils.getFormValues(frmBillDetail, this, mapping, request);
            DynaActionForm frmLineItem = (DynaActionForm) frmBillDetail.get("lineItemVO");
            lineItemVO=(LineItemVO)FormUtils.getFormValues(frmLineItem, "frmBillLineItem",this, mapping, request);
            claimBillDetailVO.setLineItemVO(lineItemVO);
            claimBillDetailVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
            claimBillDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            //call the DAO to save the Bill Details
            long lngCnt=billManagerObject.saveBillDetail(claimBillDetailVO);
            if(lngCnt>0)
            {
                //requery to get the saved data
                if(claimBillDetailVO.getBillSeqID()!=null)	//after updation
                {
                    claimBillDetailVO=billManagerObject.getBillDetail(lngCnt,TTKCommon.getUserSeqId(request));
                    request.setAttribute("updated","message.savedSuccessfully");
                }
                else	//after insertion
                {
                    //claimBillDetailVO=billManagerObject.getBillDetail(lngCnt,TTKCommon.getUserSeqId(request));
                    request.setAttribute("updated","message.addedSuccessfully");
                    claimBillDetailVO = new ClaimBillDetailVO();
                    claimBillDetailVO.setLineItemVO(new LineItemVO());
                }//end of else
               frmBillDetail = (DynaActionForm)FormUtils.setFormValues("frmClaimBillDetails", claimBillDetailVO,
               																	this, mapping, request);
               frmBillDetail.set("caption",strCaption);//sbfCaption.toString());
               frmBillDetail.set("lineItemVO",FormUtils.setFormValues("frmBillLineItem",
               												claimBillDetailVO.getLineItemVO(),this,mapping,request));
               request.getSession().setAttribute("frmClaimBillDetails",frmBillDetail);
            }//end of if(lngCnt>0)
            return this.getForward(strbilldetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBillListError));
		}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside the doReset method of ListofBillsAction");
    		setLinks(request);
    		StringBuffer sbfCaption=new StringBuffer();
    		LineItemVO lineItemVO=null;
    		ClaimBillDetailVO claimBillDetailVO=null;
    		ClaimBillManager billManagerObject=this.getbillManagerObject();
    		DynaActionForm frmBillDetail = (DynaActionForm)form;
            String strAmmendmentYN = frmBillDetail.getString("ammendmentYN");
            if(frmBillDetail.get("billSeqID")!=null && !frmBillDetail.get("billSeqID").equals(""))
            {
                sbfCaption.append("Edit");
                // calling business layer to get the bank account detail
                claimBillDetailVO=billManagerObject.getBillDetail(TTKCommon.getLong((String)
                									frmBillDetail.get("billSeqID")),TTKCommon.getUserSeqId(request));
            }//end of if(frmBankAcctGeneral.get("accountSeqID")!=null &&
            //!frmBankAcctGeneral.get("accountSeqID").equals(""))
            else
            {
                sbfCaption.append("Add");
                claimBillDetailVO = new ClaimBillDetailVO();
                lineItemVO = new LineItemVO();
                claimBillDetailVO.setLineItemVO(lineItemVO);
            }//end of else if(frmBankAcctGeneral.get("accountSeqID")!=null &&
            //!frmBankAcctGeneral.get("accountSeqID").equals(""))
            sbfCaption.append("[").append(ClaimsWebBoardHelper.getClaimantName(request)).append("][").append(
            												ClaimsWebBoardHelper.getWebBoardDesc(request)).append("]");
            frmBillDetail = (DynaActionForm)FormUtils.setFormValues("frmClaimBillDetails", claimBillDetailVO, this,
            														mapping, request);
            frmBillDetail.set("caption",sbfCaption.toString());
            frmBillDetail.set("lineItemVO",FormUtils.setFormValues("frmBillLineItem",claimBillDetailVO.getLineItemVO(),
            															this,mapping,request));
            frmBillDetail.set("ammendmentYN",strAmmendmentYN);
            request.getSession().setAttribute("frmClaimBillDetails",frmBillDetail);
            return this.getForward(strbilldetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBillListError));
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
    		log.debug("Inside the doClose method of ListofBillsAction");
    		setLinks(request);
    		TableData  tableData =TTKCommon.getTableData(request);
    		ClaimBillManager billManagerObject=this.getbillManagerObject();
    		if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
            {
                //refresh the data in Close mode, in order to get the new records if any.
                ArrayList alListofBills = billManagerObject.getBillList(tableData.getSearchData());
                tableData.setData(alListofBills,"search");
                request.getSession().setAttribute("tableData",tableData);
            }//end of if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
            return this.getForward(strBilllist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBillListError));
		}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to delete the selected record(s) in Search Grid.
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
    		log.debug("Inside the doDeleteList method of ListofBillsAction");
    		setLinks(request);
    		TableData  tableData =TTKCommon.getTableData(request);
    		ClaimManager claimManagerObject=this.getClaimManagerObject();
    		ClaimBillManager billManagerObject=this.getbillManagerObject();
    		StringBuffer sbfDeleteId = new StringBuffer("|");
    		int iCount=0;
    		//populate the delete string which contains the sequence id's to be deleted
    		sbfDeleteId.append(populateDeleteId(request,(TableData)request.getSession().getAttribute("tableData")));
    		log.debug("Sequence Id to be Deleted !!!  : "+sbfDeleteId.toString());
    		ArrayList<Object> alDeleteList = new ArrayList<Object>();
    		alDeleteList.add("BILL");
    		alDeleteList.add(sbfDeleteId.toString());
    		alDeleteList.add(ClaimsWebBoardHelper.getClmEnrollDetailSeqId(request));
    		alDeleteList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
    		alDeleteList.add(TTKCommon.getUserSeqId(request));
    		//delete the selected Bill based on the flow
    		iCount=claimManagerObject.deleteClaimGeneral(alDeleteList);
    		//refresh the grid with search data in session
    		ArrayList alBillList = null;
    		//fetch the data from previous set of rowcounts, if all the records are deleted for the current
    		//set of search criteria
    		if(iCount == tableData.getData().size())
    		{
    			tableData.modifySearchData("DeleteList");//modify the search data
    			int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(
    																		tableData.getSearchData().size()-2));
    			if(iStartRowCount > 0)
    			{
    				alBillList=billManagerObject.getBillList(tableData.getSearchData());
    			}//end of if(iStartRowCount > 0)
    		}//end if(iCount == tableData.getData().size())
    		else
    		{
    			alBillList=billManagerObject.getBillList(tableData.getSearchData());
    		}//end of else
    		tableData.setData(alBillList, "DeleteList");
    		if(iCount>0)
    		{
    			//delete the Bill account from the web board if any
    			request.setAttribute("cacheId",sbfDeleteId.append("|").toString());
    		}//end of if(iCount>0)
    		request.getSession().setAttribute("tableData",tableData);
    		return this.getForward(strBilllist, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBillListError));
		}//end of catch(Exception exp)
    }//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    	//HttpServletResponse response)

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
    public ActionForward doViewBillSummary(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    														HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewBillSummary method of ListofBillsAction");
    		setLinks(request);
    		//go to summary page
            return mapping.findForward(strBillsummary);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBillListError));
		}//end of catch(Exception exp)
    }//end of doViewBillSummary(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    //HttpServletResponse response)

    /**
     * Returns the ClaimBillManager session object for invoking methods on it.
     * @return ClaimBillManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private ClaimBillManager getbillManagerObject() throws TTKException
    {
        ClaimBillManager billManagerObject = null;
        try
        {
            if(billManagerObject == null)
            {
                InitialContext ctx = new InitialContext();
                billManagerObject = (ClaimBillManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimBillManagerBean!com.ttk.business.claims.ClaimBillManager");
            }//end if(billManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "bill");
        }//end of catch
        return billManagerObject;
    }//end of getbillManagerObject()

    /**
     * Returns the ClaimBillManager session object for invoking methods on it.
     * @return ClaimBillManager session object which can be used for method invocation
     * @exception throws TTKException
     */
    private ClaimManager getClaimManagerObject() throws TTKException
    {
    	ClaimManager claimManager = null;
    	try
    	{
    		if(claimManager == null)
    		{
    			InitialContext ctx = new InitialContext();
    			claimManager = (ClaimManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimManagerBean!com.ttk.business.claims.ClaimManager");
    		}//end if
    	}//end of try
    	catch(Exception exp)
    	{
    		throw new TTKException(exp, "claim");
    	}//end of catch
    	return claimManager;
    }//end of getClaimBillManagerObject()


    /**
     * Returns the ArrayList which contains the populated search criteria elements
     * @param  frmBillList DynaActionForm will contains the values of corresponding fields
     * @param request HttpServletRequest object which contains the search parameter that is built
     * @return alSearchParams ArrayList
     * @throws TTKException
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmBillList,HttpServletRequest request)
    																							throws TTKException
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();

        alSearchParams.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmBillList.getString("sBillNo")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmBillList.getString("sBillAmount")));
        alSearchParams.add((String)frmBillList.get("sBillDate"));
        alSearchParams.add(TTKCommon.getUserSeqId(request));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmBillList)

    /**
     * This method returns a string which contains the comma separated sequence id's to be deleted,
     * in Bill flow pipe seperated Bill seq ids  are sent to the called method
     * @param request HttpServletRequest object which contains information about the selected check boxes
     * @param BillListData TableData object which contains the value objects
     * @return String which contains the comma separated sequence id's to be deleted
     */
    private String populateDeleteId(HttpServletRequest request, TableData tableData)
    {
        String[] strChk = request.getParameterValues("chkopt");
        StringBuffer sbfDeleteId = new StringBuffer();
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
                        sbfDeleteId.append(String.valueOf(((ClaimBillVO)tableData.getData().get(
                        									Integer.parseInt(strChk[i]))).getBillSeqID()));
                    }// end of if(i == 0)
                    else
                    {
                        sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((ClaimBillVO)
                        						tableData.getData().get(Integer.parseInt(strChk[i]))).getBillSeqID()));
                    }// end of else
                }//end of if(strChk[i]!=null)
            }//end of for(int i=0; i<strChk.length;i++)
            sbfDeleteId.append("|");
        }//end of if(strChk!=null&&strChk.length!=0)
        log.debug("DELETE IDS !!!!!!! "+sbfDeleteId);
        return sbfDeleteId.toString();
    }//end of billDeleteId(HttpServletRequest request, TableData tableData)
}//end ListofBillsAction