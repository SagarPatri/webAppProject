
/**
 * @ (#) InsuranceFeedbackAction.javaNov 12, 2005
 * Project      : TTK HealthCare Services
 * File         : InsuranceFeedbackAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Nov 12, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.action.empanelment;

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
import com.ttk.business.empanelment.InsuranceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.InsuranceFeedbackVO;
import com.ttk.dto.empanelment.BrokerVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching the feedback dates and 
 * also has the mode add/edit and delete the feedback information.
 */

public class BrokerFeedbackAction extends TTKAction
{
    Logger log = Logger.getLogger( BrokerFeedbackAction.class );
    //string for setting mode
    private static final String strForward="Forward";
    private static final String strBackward="Backward";
        
    //forward links
    private static final String strBroFeedback="brofeedback";
    private static final String strEditFeedbacks="editbrofeedback";
    private static final String strDisplaySummary="displaycompanysummary";
        
    //Exception Message Identifier
    private static final String strBroFeedbackError="insfeedback";
    
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
    		log.debug("Inside the doDefault method of BrokerFeedbackAction");
    		setLinks(request);
    		
    		BrokerVO  brokerVO = (BrokerVO)request.getSession().getAttribute("SelectedOffice");
    		TableData tableData=TTKCommon.getTableData(request);
    		DynaActionForm frmSearchBroFeedback = (DynaActionForm)form;
    		StringBuffer sbfCaption=new StringBuffer();
    		sbfCaption.append("FeedBack Details -");
    		String strBroBranchName="";
    		strBroBranchName=brokerVO.getBranchName();
    		//create new table data object
    		tableData = new TableData();
    		//create the required grid table
    		tableData.createTableInfo("BroFeedbackTable",new ArrayList());
    		request.getSession().setAttribute("tableData",tableData);
    		log.info("FeedBack Details   strBroBranchName:::"+strBroBranchName);
    		log.info("strBroFeedback"+strBroFeedback);
    		frmSearchBroFeedback.initialize(mapping);//reset the form data
    		sbfCaption=sbfCaption.append("[").append(strBroBranchName).append("]");
    		frmSearchBroFeedback.set("caption",String.valueOf(sbfCaption));
    		return this.getForward(strBroFeedback, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp,strBroFeedbackError));
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
    		log.debug("Inside the doSearch method of BrokerFeedbackAction");
    		setLinks(request);
    		//Get the session bean from the beand pool for each thread being excuted.
            InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
            BrokerVO  brokerVO = (BrokerVO)request.getSession().getAttribute("SelectedOffice");
    		TableData tableData=TTKCommon.getTableData(request);
    		DynaActionForm frmSearchBroFeedback = (DynaActionForm)form;
    		StringBuffer sbfCaption=new StringBuffer();
            sbfCaption.append("FeedBack Details -");
            String strBroBranchName="";
            String strCompanyCodeNbr=brokerVO.getCompanyCodeNbr();
            strBroBranchName=brokerVO.getBranchName();
            log.info("strCompanyCodeNbr"+strCompanyCodeNbr);
            Long lbrokerSeqID = null;
            if(brokerVO != null){
            	lbrokerSeqID = brokerVO.getInsuranceSeqID();
            }//end of if(BrokerVO != null)
                
    		//clear the dynaform if visting from left links for the first time
            //else get the dynaform data from session
            if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
            	((DynaActionForm)form).initialize(mapping);//reset the form data
            }//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
            String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
            //if the page number or sort id is clicked
            if(!strPageID.equals("") || !strSortID.equals(""))
            {
                if(!strPageID.equals(""))
                {    
                    tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
                    return (mapping.findForward(strBroFeedback));
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
                tableData.createTableInfo("BroFeedbackTable",null);
                tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,lbrokerSeqID));
                tableData.modifySearchData("search");
            }//end of else
            ArrayList alFeedback=null;
            alFeedback =insuranceObject.getBrokerFeedbackList(tableData.getSearchData());
            tableData.setData(alFeedback,"search");
            //set the table data object to session
            request.getSession().setAttribute("tableData",tableData);
            sbfCaption=sbfCaption.append("[").append(strBroBranchName).append("]");
            frmSearchBroFeedback.set("caption",String.valueOf(sbfCaption));
            //finally return to the grid screen
            return this.getForward(strBroFeedback, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroFeedbackError));
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
    		log.debug("Inside the doBackward method of BrokerFeedbackAction");
    		setLinks(request);
    		InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
    		TableData tableData=TTKCommon.getTableData(request);
    		tableData.modifySearchData(strBackward);//modify the search data
            ArrayList alFeedback = insuranceObject.getBrokerFeedbackList(tableData.getSearchData());
            tableData.setData(alFeedback, strBackward);//set the table data
            request.getSession().setAttribute("tableData",tableData);//set the table data object to session
            return this.getForward(strBroFeedback, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroFeedbackError));
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
    		log.debug("Inside the doBackward method of BrokerFeedbackAction");
    		setLinks(request);
    		InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
    		TableData tableData=TTKCommon.getTableData(request);
    		tableData.modifySearchData(strForward);//modify the search data
            ArrayList alFeedback = insuranceObject.getBrokerFeedbackList(tableData.getSearchData());
            tableData.setData(alFeedback, strForward);//set the table data
            request.getSession().setAttribute("tableData",tableData);//set the table data object to session
            return this.getForward(strBroFeedback, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroFeedbackError));
		}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doAdd method of BrokerFeedbackAction");
    		setLinks(request);
    		BrokerVO  brokerVO = (BrokerVO)request.getSession().getAttribute("SelectedOffice");
    		StringBuffer sbfCaption=new StringBuffer();
            sbfCaption.append("FeedBack Details -");
            String strBroBranchName="";
            strBroBranchName=brokerVO.getBranchName();
    		DynaActionForm frmEditBroFeedback = (DynaActionForm)form;
    		frmEditBroFeedback.initialize(mapping);
    		sbfCaption=sbfCaption.append("Add").append("[").append(strBroBranchName).append("]");
    		frmEditBroFeedback.set("caption",String.valueOf(sbfCaption));
    		return this.getForward(strEditFeedbacks, mapping, request); 
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroFeedbackError));
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
    		log.debug("Inside the doView method of BrokerFeedbackAction");
    		setLinks(request);
    		InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
    		BrokerVO  brokerVO = (BrokerVO)request.getSession().getAttribute("SelectedOffice");
    		StringBuffer sbfCaption=new StringBuffer();
            sbfCaption.append("FeedBack Details -");
            String strBroBranchName="";
            strBroBranchName=brokerVO.getBranchName();
            log.info("Doview broker"+strBroBranchName);
    		DynaActionForm frmEditBroFeedback = (DynaActionForm)form;
            //if rownumber is found populate the form object
            if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
                InsuranceFeedbackVO feedbackVO = (InsuranceFeedbackVO)((TableData)request.getSession().
                		getAttribute("tableData")).getData().get(Integer.parseInt(request.getParameter("rownum")));
                feedbackVO = insuranceObject.getBrokerFeedback(feedbackVO.getFeedbackID());
                frmEditBroFeedback = (DynaActionForm)FormUtils.setFormValues("frmEditBroFeedback",feedbackVO,
                					  this,mapping,request);
                sbfCaption=sbfCaption.append("Edit").append("[").append(strBroBranchName).append("]");
                request.setAttribute("frmEditBroFeedback",frmEditBroFeedback);
            }// end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            frmEditBroFeedback.set("caption",String.valueOf(sbfCaption));
            return this.getForward(strEditFeedbacks, mapping, request); 
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroFeedbackError));
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
    		log.debug("Inside the doSave method of BrokerFeedbackAction");
    		setLinks(request);
    		InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
    		BrokerVO  brokerVO = (BrokerVO)request.getSession().getAttribute("SelectedOffice");
    		StringBuffer sbfCaption=new StringBuffer();
            sbfCaption.append("FeedBack Details -");
            String strBroBranchName="";
            strBroBranchName=brokerVO.getBranchName();
            Long lInsuranceSeqID = null;
            if(brokerVO != null){
            	lInsuranceSeqID = brokerVO.getInsuranceSeqID();
            }//end of if(BrokerVO != null)
    		DynaActionForm frmEditBroFeedback = (DynaActionForm)form;
            InsuranceFeedbackVO feedbackVO = null;
            feedbackVO = (InsuranceFeedbackVO) FormUtils.getFormValues(frmEditBroFeedback, this, mapping, request);
            feedbackVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            feedbackVO.setInsuranceSeqID(lInsuranceSeqID);
            Long lUpdate=insuranceObject.addUpdateBrokerFeedback(feedbackVO);
            if(lUpdate > 0)
            {
                if(!((String)frmEditBroFeedback.get("rownum")).equals(""))
                {
                    ((TableData)request.getSession().getAttribute("tableData")).getData().
                    set(Integer.parseInt((String)frmEditBroFeedback.get("rownum")),feedbackVO);
                    //set the appropriate message
                    request.setAttribute("updated","message.savedSuccessfully");
                    sbfCaption=sbfCaption.append("Edit").append("[").append(strBroBranchName).append("]");
                    request.setAttribute("frmEditBroFeedback",frmEditBroFeedback);
                }//end if(!((String)insFeedbackForm.get("rownum")).equals(""))
                else
                {
                	feedbackVO = new InsuranceFeedbackVO();
                	frmEditBroFeedback.initialize(mapping);
                    //set the appropriate message
                    request.setAttribute("updated","message.addedSuccessfully");
                    sbfCaption=sbfCaption.append("Add").append("[").append(strBroBranchName).append("]");
                    request.setAttribute("frmEditBroFeedback",frmEditBroFeedback);
                }//end of else
            }//end of if(iUpdate > 0)
            frmEditBroFeedback.set("caption",String.valueOf(sbfCaption));
            return this.getForward(strEditFeedbacks, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroFeedbackError));
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
    		log.debug("Inside the doReset method of BrokerFeedbackAction");
    		setLinks(request);
    		DynaActionForm frmEditBroFeedback = (DynaActionForm)form;
    		//get the session bean from the bean pool for each excecuting thread
    		InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
    		BrokerVO  brokerVO = (BrokerVO)request.getSession().getAttribute("SelectedOffice");
            StringBuffer sbfCaption= new StringBuffer();
            sbfCaption.append("FeedBack Details -");
            String strBroBranchName="";
            strBroBranchName=brokerVO.getBranchName();
            InsuranceFeedbackVO feedbackVO = null;
            Long lngFeedbackSeqId=TTKCommon.getLong((String)frmEditBroFeedback.get("feedbackID"));
            if(lngFeedbackSeqId!=null)
            {
            	feedbackVO=insuranceObject.getBrokerFeedback(lngFeedbackSeqId);
            	frmEditBroFeedback = (DynaActionForm)FormUtils.setFormValues("frmEditBroFeedback",
            						  feedbackVO,this,mapping,request);
            	sbfCaption=sbfCaption.append("Edit").append("[").append(strBroBranchName).append("]");
            }// end of if(lngFeedbackSeqId!=null)
            else
            {
            	feedbackVO = new InsuranceFeedbackVO();
            	sbfCaption=sbfCaption.append("Add").append("[").append(strBroBranchName).append("]");
            	frmEditBroFeedback.initialize(mapping);  //initialize the form bean for add mode
            }//end of else
            frmEditBroFeedback.set("caption",String.valueOf(sbfCaption));
            request.setAttribute("frmEditBroFeedback",frmEditBroFeedback);
            return this.getForward(strEditFeedbacks, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroFeedbackError));
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
    		log.debug("Inside the doClose method of BrokerFeedbackAction");
    		setLinks(request);
    		InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
    		BrokerVO  brokerVO = (BrokerVO)request.getSession().getAttribute("SelectedOffice");
    		StringBuffer sbfCaption=new StringBuffer();
            sbfCaption.append("FeedBack Details -");
            String strBroBranchName="";
            strBroBranchName=brokerVO.getBranchName();
            TableData tableData=TTKCommon.getTableData(request);
    		DynaActionForm frmSearchBroFeedback = (DynaActionForm)form;
    		ArrayList  alFeedback=null;
            if(tableData.getSearchData()!= null&& tableData.getSearchData().size()>0)
            {    
                //refresh the data in close mode, in order to get the new records if any.
                alFeedback =insuranceObject.getBrokerFeedbackList(tableData.getSearchData());
                tableData.setData(alFeedback);
                request.getSession().setAttribute("tableData",tableData);
                //reset the forward links after adding the records if any
                tableData.setForwardNextLink();
            }//end of  if(tableData.getSearchData()!= null&& tableData.getSearchData().size()>0)  
            sbfCaption=sbfCaption.append("[").append(strBroBranchName).append("]");
            frmSearchBroFeedback.set("caption",String.valueOf(sbfCaption));
            return this.getForward(strBroFeedback, mapping, request); 
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroFeedbackError));
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
    		log.debug("Inside the doDeleteList method of BrokerFeedbackAction");
    		setLinks(request);
    		InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
    		TableData tableData=TTKCommon.getTableData(request);
    		StringBuffer sbfDeleteId = new StringBuffer("|");
            //populate the delete string which contains the sequence id's to be deleted
            sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().getAttribute("tableData")));
            sbfDeleteId.append("|");
            //delete the feedback Details
            int iCount = insuranceObject.deleteBrokerFeedback(sbfDeleteId.toString(),
            			 TTKCommon.getUserSeqId(request));
            ArrayList alFeedback=null;
            alFeedback = insuranceObject.getBrokerFeedbackList(tableData.getSearchData());
            //fetch the data from previous set of rowcounts, if all the records are deleted for the current set of search criteria  
            if(alFeedback.size()==0||iCount == tableData.getData().size())
            {
                tableData.modifySearchData("DeleteList");//modify the search data
                int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().
                					 get(tableData.getSearchData().size()-2));
                if(iStartRowCount > 0)
                {
                    alFeedback = insuranceObject.getBrokerFeedbackList(tableData.getSearchData());
                }//end of if(iStartRowCount > 0)
            }//end if(iCount == tableData.getData().size())
            else
            {
                alFeedback = insuranceObject.getBrokerFeedbackList(tableData.getSearchData());
            }
            tableData.setData(alFeedback,"DeleteList");
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward(strBroFeedback, mapping, request);  
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroFeedbackError));
		}//end of catch(Exception exp)
    }//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to navigate to Company Summary Details.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewBrokerSummary(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewBrokerSummary method of BrokerFeedbackAction");
    		setLinks(request);
    		return mapping.findForward(strDisplaySummary);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroFeedbackError));
		}//end of catch(Exception exp)
    }//end of doViewCompanySummary(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * Returns a string which contains the comma separated sequence id's to be deleted  
     * @param request HttpServletRequest object which contains information about the selected check boxes
     * @param tableData TableData object which contains the value objects
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
                if(strChk[i]!=null)
                {    
                    //extract the sequence id to be deleted from the value object
                    if(i == 0)
                    {
                        sbfDeleteId.append(String.valueOf(((InsuranceFeedbackVO)tableData.getData().
                        		get(Integer.parseInt(strChk[i]))).getFeedbackID().intValue()));
                    }//end of if(i == 0)
                    else
                    {
                        sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((InsuranceFeedbackVO)tableData.
                        			  getData().get(Integer.parseInt(strChk[i]))).getFeedbackID().intValue()));
                    }//end of else
                }//end of if(strChk[i]!=null)
            }//end of for(int i=0; i<strChk.length;i++) 
        }//end of if(strChk!=null&&strChk.length!=0) 
        return sbfDeleteId.toString();
    }//end of populateDeleteId(HttpServletRequest request, TableData tableData)
    
    /**
     * This method builds all the search parameters to ArrayList and places them in session 
     * @param searchInsFeedbackForm DynaActionForm
     * @param request HttpServletRequest
     * @param lInsuranceSeqID which will have long value
     * @return alSearchParams ArrayList
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm searchInsFeedbackForm, Long lInsuranceSeqID)
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(new SearchCriteria("INS_SEQ_ID",""+lInsuranceSeqID));
        alSearchParams.add(new SearchCriteria("FEEDBACK_RECEIVED_DATE", 
        		(String)searchInsFeedbackForm.get("startdate")));
        alSearchParams.add(new SearchCriteria("FEEDBACK_RECEIVED_DATE", 
        		(String)searchInsFeedbackForm.get("enddate")));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm searchInsFeedbackForm,HttpServletRequest request, Long lInsuranceSeqID)
    
    /**
     * Returns the InsuranceManager session object for invoking methods on it.
     * @return InsuranceManager session object which can be used for method invokation 
     * @exception throws TTKException  
     */
    private InsuranceManager getInsuranceManagerObject() throws TTKException
    {
        InsuranceManager insuranceManager = null;
        try 
        {
            if(insuranceManager == null)
            {
                InitialContext ctx = new InitialContext();
                insuranceManager = (InsuranceManager) ctx.lookup("java:global/TTKServices/business.ejb3/InsuranceManagerBean!com.ttk.business.empanelment.InsuranceManager");
				
            }//end if(insuranceManager == null)
        }//end of try 
        catch(Exception exp) 
        {
            throw new TTKException(exp, strBroFeedbackError);
        }//end of catch
        return insuranceManager;
    }//end getInsuranceManagerObject()   
}// end of InsuranceFeedbackAction class
