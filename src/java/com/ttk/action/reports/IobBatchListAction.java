package com.ttk.action.reports;

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
import com.ttk.business.enrollment.PolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.BatchVO;

public class IobBatchListAction extends TTKAction
{
	private static Logger log = Logger.getLogger(IobBatchListAction.class);

	//Action Forwards
	private static final String strForward="Forward";
    private static final String strBackward="Backward";
	private static final String strBatchList="batchlist";
	private static final String strIOBRptDetails ="iobrptdetails";

	// Exception Message Identifier
	private static final String strReportExp = "report";

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
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.info("Inside the doDefault method of IobBatchListAction");
			setLinks(request);
			
			DynaActionForm frmIobBatchList =(DynaActionForm)form;
			frmIobBatchList.initialize(mapping);
			DynaActionForm frmReportList =(DynaActionForm) request.getSession().getAttribute("frmReportList");
			frmIobBatchList.set("reportName", frmReportList.getString("reportName"));
			if(!frmReportList.getString("batchNo").equals(""))
			{
				String strBatchNo = frmReportList.getString("batchNo");
				frmIobBatchList.set("batchNumber", strBatchNo);
			}// end of if(!frmReportList.getString("batchNo").equals(""))
			//get the table data from session if exists
			TableData tableDataBatchNbr=null;
            if((request.getSession()).getAttribute("tableDataBatchNbr") == null)
            {
            	tableDataBatchNbr = new TableData();
            }// end of if((request.getSession()).getAttribute("tableDataGroupName") == null)
            else
            {
            	tableDataBatchNbr = (TableData)(request.getSession()).getAttribute("tableDataBatchNbr");
            }// end of else
            tableDataBatchNbr= new TableData();
            tableDataBatchNbr.createTableInfo("BatchListTable",null);
            request.getSession().setAttribute("tableDataBatchNbr",tableDataBatchNbr);
			return this.getForward(strBatchList,mapping,request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
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
            log.info("Inside the doSearch of IobBatchListAction");
            TableData tableDataBatchNbr=null;
            if((request.getSession()).getAttribute("tableDataBatchNbr") == null)
            {
            	tableDataBatchNbr = new TableData();
            }// end of if((request.getSession()).getAttribute("tableDataGroupName") == null)
            else
            {
            	tableDataBatchNbr = (TableData)(request.getSession()).getAttribute("tableDataBatchNbr");
            }// end of else

            String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
            //if the page number or sort id is clicked
            if(!strPageID.equals("") || !strSortID.equals(""))
            {
                if(!strPageID.equals(""))
                {
                	tableDataBatchNbr.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.
                    		getParameter("pageId"))));
                    return (mapping.findForward(strBatchList));
                }// end of if(!strPageID.equals(""))
                else
                {
                	tableDataBatchNbr.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
                	tableDataBatchNbr.modifySearchData("sort");//modify the search data
                }// end of else
            }// end of if(!strPageID.equals("") || !strSortID.equals(""))
            else
            {
                //create the required grid table
            	tableDataBatchNbr.createTableInfo("BatchListTable",null);
            	tableDataBatchNbr.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
            	tableDataBatchNbr.modifySearchData("search");
            }// end of else

            PolicyManager policyManagerObject=this.getPolicyManagerObject();
            ArrayList alBatchList=policyManagerObject.getSoftcopyBatchList(tableDataBatchNbr.getSearchData());

            tableDataBatchNbr.setData(alBatchList,"search");
            //set the table data object to session
            request.getSession().setAttribute("tableDataBatchNbr",tableDataBatchNbr);
            return this.getForward(strBatchList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request,mapping,expTTK);
        }//end of catch(ETTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
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
        try
        {
            setLinks(request);
            log.debug("Inside the doBackward of IobBatchListAction");
            TableData tableDataBatchNbr=null;
            if((request.getSession()).getAttribute("tableDataBatchNbr") == null)
            {
            	tableDataBatchNbr = new TableData();
            }// end of if((request.getSession()).getAttribute("tableDataGroupName") == null)
            else
            {
            	tableDataBatchNbr = (TableData)(request.getSession()).getAttribute("tableDataBatchNbr");
            }// end of else
            PolicyManager policyManagerObject=this.getPolicyManagerObject();
            tableDataBatchNbr.modifySearchData(strBackward);//modify the search data
            ArrayList alBatchList = policyManagerObject.getSoftcopyBatchList(tableDataBatchNbr.getSearchData());
            tableDataBatchNbr.setData(alBatchList, strBackward);//set the table data
            //set the table data object to session
            request.getSession().setAttribute("tableDataBatchNbr",tableDataBatchNbr);
            return this.getForward(strBatchList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request,mapping,expTTK);
        }//end of catch(ETTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
        }//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
            setLinks(request);
            log.debug("Inside the doForward of IobBatchListAction");
            TableData tableDataBatchNbr=null;
            if((request.getSession()).getAttribute("tableDataBatchNbr") == null)
            {
            	tableDataBatchNbr = new TableData();
            }// end of if((request.getSession()).getAttribute("tableDataGroupName") == null)
            else
            {
            	tableDataBatchNbr = (TableData)(request.getSession()).getAttribute("tableDataBatchNbr");
            }// end of else
            PolicyManager policyManagerObject=this.getPolicyManagerObject();
            tableDataBatchNbr.modifySearchData(strForward);//modify the search data
            ArrayList alBatchList = policyManagerObject.getSoftcopyBatchList(tableDataBatchNbr.getSearchData());
            tableDataBatchNbr.setData(alBatchList, strForward);//set the table data
            //set the table data object to session
            request.getSession().setAttribute("tableDataBatchNbr",tableDataBatchNbr);
            return this.getForward(strBatchList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request,mapping,expTTK);
        }//end of catch(ETTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request,mapping,new TTKException(exp,strReportExp));
        }//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is will update the selected Batch info in the required form and
     * moves it the next screen.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doSelectBatch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
            setLinks(request);
            log.debug("Inside the doSelectGroup of IobBatchListAction");
            DynaActionForm frmReportList=(DynaActionForm)request.getSession().getAttribute("frmReportList");
            BatchVO batchVO = null;
            if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
            	batchVO = (BatchVO)((TableData)request.getSession().getAttribute("tableDataBatchNbr")).
                getData().get(Integer.parseInt(request.getParameter("rownum")));
            	frmReportList.set("batchNo",batchVO.getBatchNbr());
            }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))	
            	request.getSession().setAttribute("frmReportList",frmReportList);
            return this.getForward(strIOBRptDetails,mapping,request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request,mapping,expTTK);
        }//end of catch(ETTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request,mapping,new TTKException(exp,"groupList"));
        }//end of catch(Exception exp)
    }//end of doSelectBatch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    	log.debug("Inside the doClose of PolicyGroupAction");
        return doSelectBatch(mapping,form,request,response);
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method builds all the search parameters to ArrayList and places them in session
     * @param searchGroupForm DynaActionForm
     * @param request HttpServletRequest
     * @return alSearchParams ArrayList
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmIobBatchList,HttpServletRequest request)
    	throws TTKException
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmIobBatchList.get("batchNumber")));
        alSearchParams.add(TTKCommon.replaceSingleQots((String)frmIobBatchList.get("startDate")));
        alSearchParams.add((String)frmIobBatchList.get("endDate"));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm searchGroupForm,HttpServletRequest request)
    
	/**
     * Returns the PolicyManager session object for invoking DAO methods on it.
     * @return PolicyManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private PolicyManager getPolicyManagerObject() throws TTKException
    {
        PolicyManager policyManager = null;
        try
        {
            if(policyManager == null)
            {
                InitialContext ctx = new InitialContext();
                policyManager = (PolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/PolicyManagerBean!com.ttk.business.enrollment.PolicyManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "policyList");
        }//end of catch
        return policyManager;
    }//end getPolicyManagerObject()
}// end of IobBatchListAction class
