/**
 * @ (#) HospitalFeedbackAction.javaSep 20, 2005
 * Project      : TTK HealthCare Services
 * File         : HospitalFeedbackAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Sep 20, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   : Nagaraj D V
 * Modified date : Jan 19, 2006
 * Reason        : To reduce the table data related code in action class
 */

package com.ttk.action.empanelment;

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
import org.apache.struts.validator.DynaValidatorForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.FeedbackDetailVO;
import com.ttk.dto.empanelment.StatusVO;

import formdef.plugin.util.FormUtils;

/**
 * This action class is used to search,insert/update and delete hospital feedback information
 */

public class HospitalFeedbackAction extends TTKAction
{
    private static Logger log = Logger.getLogger( HospitalFeedbackAction.class ); 
    
    private static final String strForward="Forward";
    private static final String strBackward="Backward";
        
    //forward links
    private static final String strFeedback="feedbacks";
    private static final String strEditFeedbacks="editfeedbacks";
        
    //Exception Message Identifier
    private static final String strHospFeedbackError="feedback";
    
    
    private static final String strHospitalStatus="feedbacks";
    
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
    		log.debug("Inside the doDefault method of HospitalFeedbackAction");
    		setLinks(request);
    		if(TTKCommon.getWebBoardId(request)==null)
            {
                TTKException expTTK = new TTKException();
                expTTK.setMessage("error.hospital.required");
                throw expTTK;
            }//end of if(TTKCommon.getWebBoardId(request)==null)
    		TableData tableData=TTKCommon.getTableData(request);
    		//create new table data object
            tableData = new TableData();
            //create the required grid table
            tableData.createTableInfo("HospitalFeedbackTable",new ArrayList());
            request.getSession().setAttribute("tableData",tableData);
            ((DynaActionForm)form).initialize(mapping);//reset the form data
            TTKCommon.documentViewer(request);
            
            
            
            HospitalManager hospitalObject=this.getHospitalManagerObject();
			HashMap hmReasonInfo = null;
			ArrayList alReasonInfo = null;
    		StatusVO statusVO=null;
    		//clear the dynaform if visiting from left links for the first time
    		//else get the dynaform data from session
    		
    		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
    			((DynaValidatorForm)form).initialize(mapping);//reset the form data
    		}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")
    		    		
    		//call the business layer to get the Status
    		statusVO=hospitalObject.getStatus(TTKCommon.getWebBoardId(request));
    		//set the form bean
    		DynaActionForm  frmSearchFeedback = (DynaActionForm)FormUtils.setFormValues("frmSearchFeedback",statusVO, this, mapping, request);
    		hmReasonInfo=(HashMap)statusVO.getReasonInfo();
    		alReasonInfo=(ArrayList)hmReasonInfo.get(frmSearchFeedback.get("emplStatusTypeId"));
    		//setting the ReasonInfo into the request
    		request.getSession().setAttribute("alReasonInfo",alReasonInfo);
    		request.getSession().setAttribute("reasonInfo",hmReasonInfo);
    		request.getSession().setAttribute("frmSearchFeedback",frmSearchFeedback);
    		//finally return to the grid screen
    		
    		
    		
            return this.getForward(strFeedback, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospFeedbackError));
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
    		log.debug("Inside the doSearch method of HospitalFeedbackAction");
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		TableData tableData=TTKCommon.getTableData(request);
    		//GET THE HOSPITAL INFORMATION FROM THE SESSION
            Long lHospitalSeqId= new Long(TTKCommon.getWebBoardId(request));//get the web board id
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
                    return this.getForward(strFeedback, mapping, request);
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
                tableData.createTableInfo("HospitalFeedbackTable",null);
                tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,lHospitalSeqId));
                tableData.modifySearchData("search");
            }//end of else
            ArrayList alFeedback= hospitalObject.getFeedbackList(tableData.getSearchData());
            tableData.setData(alFeedback, "search");
            //set the table data object to session
            request.getSession().setAttribute("tableData",tableData);
            //finally return to the grid screen
            return this.getForward(strFeedback, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospFeedbackError));
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
    		setLinks(request);
    		log.debug("Inside the doBackward method of HospitalFeedbackAction");
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		TableData tableData=TTKCommon.getTableData(request);
    		tableData.modifySearchData(strBackward);//modify the search data
            ArrayList alFeedback = hospitalObject.getFeedbackList(tableData.getSearchData());
            tableData.setData(alFeedback, strBackward);//set the table data
            request.getSession().setAttribute("tableData",tableData);//set the table data object to session
            TTKCommon.documentViewer(request);
            return this.getForward(strFeedback, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospFeedbackError));
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
    		setLinks(request);
    		log.debug("Inside the doForward method of HospitalFeedbackAction");
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		TableData tableData=TTKCommon.getTableData(request);
    		tableData.modifySearchData(strForward);//modify the search data
            ArrayList alFeedback = hospitalObject.getFeedbackList(tableData.getSearchData());
            tableData.setData(alFeedback, strForward);//set the table data
            request.getSession().setAttribute("tableData",tableData);//set the table data object to session
            TTKCommon.documentViewer(request);
            return this.getForward(strFeedback, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospFeedbackError));
		}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    	log.debug("Inside the doChangeWebBoard method of HospitalFeedbackAction");
    	//ChangeWebBoard method will call doDefault() method internally.
    	return doDefault(mapping,form,request,response);
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		setLinks(request);
    		log.debug("Inside the doAdd method of HospitalFeedbackAction");
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		StringBuffer sbfCaption= new StringBuffer();
            sbfCaption.append("Feedback Details -");
    		DynaActionForm hospitalFeedbackForm = (DynaActionForm)form;
            ArrayList alFeedbackList= hospitalObject.getQAList();
            request.setAttribute("alFeedbackList", alFeedbackList);
            sbfCaption.append("Add ").append("[").append(TTKCommon.getWebBoardDesc(request)).append("]");
            hospitalFeedbackForm.set("caption",String.valueOf(sbfCaption));
            return (mapping.findForward(strEditFeedbacks));
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospFeedbackError));
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
    		setLinks(request);
    		log.debug("Inside the doView method of HospitalFeedbackAction");
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		StringBuffer sbfCaption= new StringBuffer();
            sbfCaption.append("Feedback Details -");
    		DynaActionForm hospitalFeedbackForm = (DynaActionForm)form;
            //if rownumber is found populate the form object
            if(!((String)(hospitalFeedbackForm).get("rownum")).equals(""))
            {
                FeedbackDetailVO feedbackVO = (FeedbackDetailVO)((TableData)request.getSession().
                getAttribute("tableData")).getData().get(Integer.parseInt((String)hospitalFeedbackForm.get("rownum")));
                ArrayList alFeedback=(ArrayList)hospitalObject.getFeedback(feedbackVO.getFeedbackSeqId());
                hospitalFeedbackForm.set("feedBackSeqId", feedbackVO.getFeedbackSeqId().toString());
                hospitalFeedbackForm.set("feedbackdate", feedbackVO.getFeedbackDate());
                hospitalFeedbackForm.set("suggestions",feedbackVO.getSuggestions());
                request.setAttribute("alFeedbackList", alFeedback);
                sbfCaption.append("Edit ").append("[").append(TTKCommon.getWebBoardDesc(request)).append("]");
            }//end  if(!((String)(hospitalFeedbackForm).get("rownum")).equals(""))
            hospitalFeedbackForm.set("caption",String.valueOf(sbfCaption));
            return (mapping.findForward(strEditFeedbacks));
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospFeedbackError));
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
    		setLinks(request);
    		log.debug("Inside the doSave method of HospitalFeedbackAction");
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		StringBuffer sbfCaption= new StringBuffer();
    		sbfCaption.append("Feedback Details -");
    		DynaActionForm hospitalFeedbackForm = (DynaActionForm)form;
    		//GET THE HOSPITAL INFORMATION FROM THE SESSION
            Long lHospitalSeqId= new Long(TTKCommon.getWebBoardId(request));//get the web board id
    		FeedbackDetailVO feedbackVO = new FeedbackDetailVO();
    		ArrayList alFeedback=null;
    		Long lFeedBackSeqId=null;
    		if((String)hospitalFeedbackForm.get("feedBackSeqId")!=null 
    				&& (String)hospitalFeedbackForm.get("feedBackSeqId")!="")
    		{
    			lFeedBackSeqId=new Long(Long.parseLong((String)hospitalFeedbackForm.get("feedBackSeqId")));
    			feedbackVO.setFeedbackSeqId(lFeedBackSeqId);
    		}//end of if((String)hospitalFeedbackForm.get("feedBackSeqId")!=null 
    		          //&& (String)hospitalFeedbackForm.get("feedBackSeqId")!="")
    		feedbackVO.setHospSeqId(lHospitalSeqId);
    		feedbackVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User ID
    		feedbackVO.setFeedbackDate(TTKCommon.getUtilDate((String)hospitalFeedbackForm.get("feedbackdate")));
    		feedbackVO.setSuggestions((String)hospitalFeedbackForm.get("suggestions"));
    		feedbackVO.setQuestions((String[])hospitalFeedbackForm.get("questions"));
    		feedbackVO.setAnswers((String[])hospitalFeedbackForm.get("answers"));
    		Long lngCount=hospitalObject.addUpdateFeedback(feedbackVO);
    		if(lngCount>0)
    		{
    			if(lFeedBackSeqId!=null)
    			{
    				//set the appropriate message
    				request.setAttribute("updated","message.savedSuccessfully");
    			}//end of if(lFeedBackSeqId!=null)
    			else
    			{
    				//set the appropriate message
    				request.setAttribute("updated","message.addedSuccessfully");
    			}//end of else
    		}//end of if(lngCount>0)
    		alFeedback=(ArrayList)hospitalObject.getFeedback(lngCount);
    		log.debug("in edit mode list size.....="+alFeedback.size());
    		hospitalFeedbackForm.set("feedBackSeqId",(lngCount.toString()));
    		hospitalFeedbackForm.set("feedbackdate", feedbackVO.getFeedbackDate());
    		hospitalFeedbackForm.set("suggestions",feedbackVO.getSuggestions());
    		request.setAttribute("alFeedbackList", alFeedback);
    		sbfCaption.append("Edit ").append("[").append(TTKCommon.getWebBoardDesc(request)).append("]");
    		hospitalFeedbackForm.set("caption",String.valueOf(sbfCaption));
    		return this.getForward(strEditFeedbacks, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospFeedbackError));
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
    		setLinks(request);
    		log.debug("Inside the doClose method of HospitalFeedbackAction");
    		TableData tableData=TTKCommon.getTableData(request);
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
            {
                //refresh the data in cancel mode, in order to get the new records if any.
                ArrayList alFeedback = hospitalObject.getFeedbackList(tableData.getSearchData());
                tableData.setData(alFeedback,"search");
                request.getSession().setAttribute("tableData",tableData);
                //reset the forward links after adding the records if any
            }//end of if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
            return this.getForward(strFeedback, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospFeedbackError));
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
    		setLinks(request);
    		log.debug("Inside the doDeleteList method of HospitalFeedbackAction");
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		TableData tableData=TTKCommon.getTableData(request);
    		StringBuffer sbfDeleteId = new StringBuffer("|");
    		//populate the delete string which contains the sequence id's to be deleted
    		sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().getAttribute("tableData")));
    		//delete the feedback Details
    		int iCount = hospitalObject.deleteFeedback(sbfDeleteId.append("|").toString());
    		//refresh the grid with search data in session
    		ArrayList alFeedback = null;//hospitalObject.getFeedbackList(tableData.getSearchData());
    		//fetch the data from previous set of rowcounts, if all the records are deleted for the current set of search criteria  
    		if(iCount == tableData.getData().size())
    		{
    			tableData.modifySearchData("DeleteList");//modify the search data
    			int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
    								 getSearchData().size()-2));
    			if(iStartRowCount > 0)
    			{
    				alFeedback = hospitalObject.getFeedbackList(tableData.getSearchData());
    			}//end of if(iStartRowCount > 0)
    		}//end if(iCount == tableData.getData().size())
    		else
    		{
    			alFeedback = hospitalObject.getFeedbackList(tableData.getSearchData());
    		}//end of else
    		tableData.setData(alFeedback, "DeleteList");
    		request.getSession().setAttribute("tableData",tableData);
    		return this.getForward(strFeedback, mapping, request);   
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospFeedbackError));
		}//end of catch(Exception exp)
    }//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to delete the selected record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside the doDelete method of HospitalFeedbackAction");
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
    		TableData tableData=TTKCommon.getTableData(request);
    		DynaActionForm hospitalFeedbackForm = (DynaActionForm)form;
    		StringBuffer sbfDeleteId = new StringBuffer("|");
    		//populate the delete string which contains the sequence id's to be deleted
    		if(!(request.getParameter("rownum")).equals(""))
    		{
    			sbfDeleteId.append(String.valueOf(((FeedbackDetailVO)((TableData)request.getSession().
    					getAttribute("tableData")).getData().get(Integer.parseInt(request.getParameter("rownum")))).getFeedbackSeqId()));
    		}//end of if(!(request.getParameter("rownum")).equals(""))
    		else //After adding a record and deleting the record this else block is used
    		{
    			sbfDeleteId.append(hospitalFeedbackForm.get("feedBackSeqId"));
    			//this is used if there is only one record
    			int iCount = hospitalObject.deleteFeedback(sbfDeleteId.append("|").toString());
    			log.debug("iCount value is :"+iCount);
    			return this.getForward(strFeedback, mapping, request);       
    		}// end of else
    		
    		//delete the feedback Details
    		int iCount = hospitalObject.deleteFeedback(sbfDeleteId.append("|").toString());
    		//refresh the grid with search data in session
    		ArrayList alFeedback = null;//hospitalObject.getFeedbackList(tableData.getSearchData());
    		//fetch the data from previous set of rowcounts, if all the records are deleted for the current set of search criteria  
    		if(iCount == tableData.getData().size())
    		{
    			tableData.modifySearchData("Delete");//modify the search data
    			int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().
    					get(tableData.getSearchData().size()-2));
    			if(iStartRowCount > 0)
    			{
    				alFeedback = hospitalObject.getFeedbackList(tableData.getSearchData());
    			}//end of if(iStartRowCount > 0)
    		}//end if(iCount == tableData.getData().size())
    		else
    		{
    			alFeedback = hospitalObject.getFeedbackList(tableData.getSearchData());
    		}//end of else
    		tableData.setData(alFeedback,"Delete");
    		request.getSession().setAttribute("tableData",tableData);
    		return this.getForward(strFeedback, mapping, request);    
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospFeedbackError));
		}//end of catch(Exception exp)
    }//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
                        sbfDeleteId.append(String.valueOf(((FeedbackDetailVO)tableData.getData().
                        		get(Integer.parseInt(strChk[i]))).getFeedbackSeqId().intValue()));
                    }//end of if(i == 0)
                    else
                    {
                        sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((FeedbackDetailVO)tableData.
                        		getData().get(Integer.parseInt(strChk[i]))).getFeedbackSeqId().intValue()));
                    }//end of else
                }//end of if(strChk[i]!=null)
            }//end of for(int i=0; i<strChk.length;i++) 
        }//end of if(strChk!=null&&strChk.length!=0) 
        return sbfDeleteId.toString();
    }//end of populateDeleteId(HttpServletRequest request, TableData tableData)
    
    /**
     * This method builds all the search parameters to ArrayList and places them in session 
     * @param searchFeedbackForm DynaActionForm
     * @param request HttpServletRequest
     * @param lHospitalSeqId which will have long value
     * @return alSearchParams ArrayList
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm searchFeedbackForm,Long lHospitalSeqId)
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        alSearchParams.add(new SearchCriteria("HOSP_SEQ_ID",lHospitalSeqId.toString()));
        alSearchParams.add(new SearchCriteria("FEEDBACK_DATE", (String)searchFeedbackForm.get("startdate")));
        alSearchParams.add(new SearchCriteria("FEEDBACK_DATE", (String)searchFeedbackForm.get("enddate")));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm searchFeedbackForm,HttpServletRequest request,Long lHospitalSeqId)
    
     /**
     * Returns the HospitalManager session object for invoking methods on it.
     * @return HospitalManager session object which can be used for method invokation 
     * @exception throws TTKException  
     */
    private HospitalManager getHospitalManagerObject() throws TTKException
    {
        HospitalManager hospitalManager = null;
        try 
        {
            if(hospitalManager == null)
            {
                InitialContext ctx = new InitialContext();
                hospitalManager = (HospitalManager) ctx.lookup("java:global/TTKServices/business.ejb3/HospitalManagerBean!com.ttk.business.empanelment.HospitalManager");
            }//end if(hospitalManager == null)
        }//end of try 
        catch(Exception exp) 
        {
            throw new TTKException(exp, strHospFeedbackError);
        }//end of catch
        return hospitalManager;
    }//end getHospitalManagerObject()   
    
    /**
     * This method is used to load the sub status based on the selected status.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doStatusChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside doStatusChange method of HospitalStatusAction");
    		HashMap hmReasonInfo = null;
			ArrayList alReasonInfo = null;
    		StatusVO statusVO=new StatusVO();
			DynaValidatorForm frmStatus=(DynaValidatorForm)request.getSession().getAttribute("frmSearchFeedback");
			DynaActionForm  frmSearchFeedback = (DynaActionForm)FormUtils.setFormValues("frmSearchFeedback",statusVO, this, mapping, request);
			//set the form bean
			frmSearchFeedback.set("empanelSeqId",(String)frmStatus.get("empanelSeqId"));
			frmSearchFeedback.set("emplStatusTypeId",frmStatus.get("emplStatusTypeId"));
			frmSearchFeedback.set("fromDate","");
			frmSearchFeedback.set("toDate","");
            frmSearchFeedback.set("frmChanged","changed");
            frmSearchFeedback.set("gradeTypeId",(String)frmStatus.get("gradeTypeId"));
			hmReasonInfo=(HashMap)request.getSession().getAttribute("reasonInfo");
			alReasonInfo=(ArrayList)hmReasonInfo.get(frmStatus.get("emplStatusTypeId"));
			//setting the ReasonInfo into the request
			request.getSession().setAttribute("alReasonInfo",alReasonInfo);
			request.getSession().setAttribute("reasonInfo",hmReasonInfo);
			request.getSession().setAttribute("frmSearchFeedback",frmSearchFeedback);
            return this.getForward(strHospitalStatus,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospFeedbackError));
		}//end of catch(Exception exp)
    }//end of doStatusChange(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    

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
    public ActionForward doSaveStatus(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside doSave method of HospitalStatusAction");
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
			HashMap hmReasonInfo = null;
			ArrayList alReasonInfo = null;
    		DynaValidatorForm frmStatus=(DynaValidatorForm)request.getSession().getAttribute("frmSearchFeedback");
			StatusVO statusVO= null;
			statusVO=(StatusVO)FormUtils.getFormValues(frmStatus, "frmSearchFeedback",this, mapping, request);
			statusVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User Id
			if(statusVO.getEmplStatusTypeId().equalsIgnoreCase("EMP")){
				statusVO.setSubStatusCode("EMP");
			}//end of if(statusVO.getEmplStatusTypeId().equalsIgnoreCase("EMP"))
			else{
				statusVO.setSubStatusCode((String)frmStatus.get("subStatusCode"));
			}//end of else
			
			if(statusVO.getEmplStatusTypeId().equalsIgnoreCase("EMP"))
			{
				statusVO.setFromDate(TTKCommon.getUtilDate((String)frmStatus.get("fromDate")));
				statusVO.setToDate(TTKCommon.getUtilDate((String)frmStatus.get("toDate")));
			}//end of if(statusVO.getEmplStatusTypeId().equalsIgnoreCase("EMP"))
			
			else if((statusVO.getEmplStatusTypeId().equalsIgnoreCase("DFC"))||(statusVO.getEmplStatusTypeId().equalsIgnoreCase("DEM"))||(statusVO.getEmplStatusTypeId().equalsIgnoreCase("DNC"))||(statusVO.getEmplStatusTypeId().equalsIgnoreCase("DPW"))||(statusVO.getEmplStatusTypeId().equalsIgnoreCase("DOR"))||(statusVO.getEmplStatusTypeId().equalsIgnoreCase("TDE"))||(statusVO.getEmplStatusTypeId().equalsIgnoreCase("TDO")))                                            
			{
				statusVO.setFromDate(TTKCommon.getUtilDate((String)frmStatus.get("fromDate")));
				statusVO.setToDate(null);
			}//end of else if(statusVO.getEmplStatusTypeId().equalsIgnoreCase("DFC"))
			
			else if(statusVO.getEmplStatusTypeId().equalsIgnoreCase("CLS")) 
			{
				statusVO.setFromDate(TTKCommon.getUtilDate((String)frmStatus.get("fromDate")));
				statusVO.setToDate(null);
			}//end of else if(statusVO.getEmplStatusTypeId().equalsIgnoreCase("CLS"))
			else
			{
				statusVO.setFromDate(null);
				statusVO.setToDate(null);
			}//end of else
			
			//call the business method here for updating the status
			int result=hospitalObject.updateStatus(statusVO);
			
			//Set the appropriate message
			if(result!=0)
			{
				//requery to get the new status information
				statusVO=hospitalObject.getStatus(TTKCommon.getWebBoardId(request));
				request.setAttribute("updated","message.savedSuccessfully");
			}//end of if(result!=0)
			//set the form bean
			DynaActionForm frmSearchFeedback = (DynaActionForm)FormUtils.setFormValues("frmSearchFeedback",statusVO, this, mapping, request);
			hmReasonInfo=(HashMap)request.getSession().getAttribute("reasonInfo");
			alReasonInfo=(ArrayList)hmReasonInfo.get(frmStatus.get("emplStatusTypeId"));
			request.getSession().setAttribute("alReasonInfo",alReasonInfo);
			request.getSession().setAttribute("reasonInfo",hmReasonInfo);
			request.getSession().setAttribute("frmSearchFeedback",frmSearchFeedback);  
			Cache.refresh("providerCodeSearch");
            Cache.refresh("providerCode");
			Cache.refresh("ProviderList");
			return this.getForward(strHospitalStatus, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospFeedbackError));
		}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
}// end of HospitalFeedbackAction class
