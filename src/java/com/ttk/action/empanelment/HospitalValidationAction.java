/**
 * @ (#) HospitalValidationAction.java Sep 20, 2005
 * Project      : TTK HealthCare Services
 * File         : HospitalValidationAction.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Sep 20, 2005
 *
 * @author       :  Arun K N
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
import org.apache.struts.validator.DynaValidatorForm;
import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.ValidationDetailVO;

/**
 * This action class is used to search,add/edit hospital validation information 
 */

public class HospitalValidationAction extends TTKAction{
    
    private static Logger log = Logger.getLogger( HospitalValidationAction.class );
   
    //for setting modes
    private static final String strForward ="Forward";
    private static final String strBackward ="Backward";
    private static final String strCancel="Close";
    
    //forward links
    private static final String strValidation="validationlist";
    private static final String strEditValidation="edithospvalidation";
        
    //Exception Message Identifier
    private static final String strHospValidationError="validation";
    
    
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
    		log.debug("Inside doSearch method of HospitalValidationAction");
    		if(TTKCommon.getWebBoardId(request)==null)
            {
                TTKException expTTK = new TTKException();
                expTTK.setMessage("error.hospital.required");
                throw expTTK;
            }//end of if(TTKCommon.getWebBoardId(request)==null)
    		//GET THE HOSPITAL SEQ ID web board id
            Long lHospitalSeqId= TTKCommon.getWebBoardId(request);
            //get the session bean from the bean pool for each excecuting thread
            HospitalManager hospitalObject=this.getHospitalManagerObject();
            TableData   tableData = TTKCommon.getTableData(request);
    		//clear the dynaform if visiting from left links for the first time
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
                    return (mapping.findForward(strValidation));
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
                tableData.createTableInfo("HospValidationTable",null);
                tableData.setSearchData(this.populateSearchCriteria(lHospitalSeqId));
                tableData.modifySearchData("search");
            }//end of else
            //fetch the data from the data access layer and set the data to table object
            ArrayList alHospValidation = hospitalObject.getValidationList(tableData.getSearchData());
            tableData.setData(alHospValidation,"search");
            //set the table data object to session
            request.getSession().setAttribute("tableData",tableData);
            TTKCommon.documentViewer(request);
            //finally return to the grid screen
            return this.getForward(strValidation, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospValidationError));
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
    		log.debug("Inside doBackward method HospitalValidationAction");
    		//fetch the data from the data access layer and set the data to table object
            ArrayList alHospValidation = null;
            //get the session bean from the bean pool for each excecuting thread
            HospitalManager hospitalObject=this.getHospitalManagerObject();
            TableData   tableData = TTKCommon.getTableData(request);
            tableData.modifySearchData(strBackward);//modify the search data
            alHospValidation = hospitalObject.getValidationList(tableData.getSearchData());
            tableData.setData(alHospValidation, strBackward);//set the table data
            //set the table data object to session
            request.getSession().setAttribute("tableData",tableData);
            TTKCommon.documentViewer(request);
            //finally return to the grid screen
            return this.getForward(strValidation, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospValidationError));
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
    		log.debug("Inside doForward method HospitalValidationAction");
    		//fetch the data from the data access layer and set the data to table object
            ArrayList alHospValidation = null;
            //get the session bean from the bean pool for each excecuting thread
            HospitalManager hospitalObject=this.getHospitalManagerObject();
            TableData   tableData = TTKCommon.getTableData(request);
            tableData.modifySearchData(strForward);//modify the search data
            alHospValidation = hospitalObject.getValidationList(tableData.getSearchData());
            tableData.setData(alHospValidation, strForward);//set the table data
            //set the table data object to session
            request.getSession().setAttribute("tableData",tableData);
            TTKCommon.documentViewer(request);
            //finally return to the grid screen
            return this.getForward(strValidation, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospValidationError));
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
    	log.debug("Inside doChangeWebBoard method HospitalValidationAction");
    	//ChangeWebBoard method will call doSearch() method internally.
    	return doSearch(mapping,form,request,response);
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
    		log.debug("Inside doAdd method HospitalValidationAction");
    		DynaActionForm frmHospValidation = (DynaActionForm)form;
    		//get the session bean from the bean pool for each excecuting thread
            StringBuffer sbfCaption= new StringBuffer();
            sbfCaption.append("Validation Details -");
            frmHospValidation.initialize(mapping);// initialize the form bean in add mode
            sbfCaption.append("Add ").append("[").append(TTKCommon.getWebBoardDesc(request)).append("]");
            frmHospValidation.set("caption",String.valueOf(sbfCaption));
            return this.getForward(strEditValidation, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospValidationError));
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
    		log.debug("Inside doView method");
    		DynaActionForm frmHospValidation = (DynaActionForm)form;
    		//get the session bean from the bean pool for each excecuting thread
            HospitalManager hospitalObject=this.getHospitalManagerObject();
            TableData tableData = TTKCommon.getTableData(request);
            StringBuffer sbfCaption= new StringBuffer();
            sbfCaption.append("Validation Details -");
            //if rownumber is found populate the form object
            if(!((String)(frmHospValidation).get("rownum")).equals(""))
            {
                ValidationDetailVO validationVO =(ValidationDetailVO)tableData.getRowInfo(Integer.parseInt((String)
                		(frmHospValidation).get("rownum")));
                validationVO=hospitalObject.getValidationDetail(validationVO.getValidateSeqId());
                frmHospValidation.set("validationReqd", validationVO.getValidationReqYn());
                frmHospValidation.set("visitDone", validationVO.getVisitDoneYn());
                frmHospValidation.set("markedDate",(String)validationVO.getMarkedDate());
                frmHospValidation.set("validationSeqId",validationVO.getValidateSeqId().toString());
                if(validationVO.getVisitDoneYn().equalsIgnoreCase("Y"))
                {
                    frmHospValidation.set("validatedBy", validationVO.getValidatedBy());
                    String strSplitedDate[]=validationVO.getValidatedDate().split(" ");
                    if(strSplitedDate.length==3)
                    {
                        frmHospValidation.set("validatedDate", strSplitedDate[0]);
                        frmHospValidation.set("validatedTime",strSplitedDate[1]);
                        frmHospValidation.set("day",strSplitedDate[2]);
                    }//end of if(strSplitedDate.length==3)
                    frmHospValidation.set("reportRcvd",validationVO.getReportRcvdYn());
                    frmHospValidation.set("validationStatus",validationVO.getValidStatusGeneralTypeId());
                }//end of  if(validationVO.getVisitDoneYn().equalsIgnoreCase("Y"))
                frmHospValidation.set("remarks", validationVO.getRemarks());
                sbfCaption.append("Edit ").append("[").append(TTKCommon.getWebBoardDesc(request)).append("]");
            }//end  if(!((String)(hospValidationForm).get("rownum")).equals(""))
            frmHospValidation.set("caption",String.valueOf(sbfCaption));
            return this.getForward(strEditValidation, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospValidationError));
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
    		log.debug("Inside doSave method");
    		DynaValidatorForm frmHospValidation = (DynaValidatorForm)form;
            //GET THE HOSPITAL SEQ ID web board id
            Long lHospitalSeqId= TTKCommon.getWebBoardId(request);
            //get the session bean from the bean pool for each excecuting thread
            HospitalManager hospitalObject=this.getHospitalManagerObject();
            StringBuffer sbfCaption= new StringBuffer();
            sbfCaption.append("Validation Details -");
            ValidationDetailVO validationVO=new ValidationDetailVO();
            validationVO.setValidateSeqId(TTKCommon.getLong((String)frmHospValidation.get("validationSeqId")));
            validationVO.setValidationReqYn((String)frmHospValidation.get("validationReqd"));
            validationVO.setVisitDoneYn((String)frmHospValidation.get("visitDone"));
            validationVO.setMarkedDate(TTKCommon.getUtilDate((String)frmHospValidation.get("markedDate")));
            if(((String)frmHospValidation.get("visitDone")).equalsIgnoreCase("Y"))
            {
                validationVO.setValidatedBy((String)frmHospValidation.get("validatedBy"));
                validationVO.setValidatedDate(TTKCommon.getOracleDateWithTime((String)
                		frmHospValidation.get("validatedDate"),(String)frmHospValidation.get("validatedTime"),
                		(String)frmHospValidation.get("day")));
                validationVO.setReportRcvdYn((String)frmHospValidation.get("reportRcvd"));
                validationVO.setValidStatusGeneralTypeId((String)frmHospValidation.get("validationStatus"));
            }//end of if(((String)validationForm.get("visitDone")).equalsIgnoreCase("Y"))
            else
            {
                validationVO.setValidatedBy("");
                validationVO.setValidatedDate(null);
            }//end of else
            validationVO.setRemarks((String)frmHospValidation.get("remarks"));
            validationVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id
            validationVO.setHospSeqId(lHospitalSeqId);  //Got the Hospital Seq Id From the web board
            //call business method for updating the record here
            long lResult=hospitalObject.addUpdateValidation(validationVO);
            if(lResult>0)
            {
                if(validationVO.getValidateSeqId()!=null)
                {
                    //set the appropriate message
                    request.setAttribute("updated","message.savedSuccessfully");
                }//end of if(!((String)(frmHospValidation).get("rownum")).equals(""))
                else
                {
                    //set the appropriate message
                    request.setAttribute("updated","message.addedSuccessfully");
                }// end of else
            }//end of if(result!=0)
            validationVO=hospitalObject.getValidationDetail(lResult);
            frmHospValidation.set("validationReqd", validationVO.getValidationReqYn());
            frmHospValidation.set("visitDone", validationVO.getVisitDoneYn());
            frmHospValidation.set("markedDate",(String)validationVO.getMarkedDate());
            frmHospValidation.set("validationSeqId",validationVO.getValidateSeqId().toString());
            if(validationVO.getVisitDoneYn().equalsIgnoreCase("Y"))
            {
                frmHospValidation.set("validatedBy", validationVO.getValidatedBy());
                String strSplitedDate[]=validationVO.getValidatedDate().split(" ");
                if(strSplitedDate.length==3)
                {
                    frmHospValidation.set("validatedDate", strSplitedDate[0]);
                    frmHospValidation.set("validatedTime",strSplitedDate[1]);
                    frmHospValidation.set("day",strSplitedDate[2]);
                }//end of if(strSplitedDate.length==3)
                frmHospValidation.set("reportRcvd",validationVO.getReportRcvdYn());
                frmHospValidation.set("validationStatus",validationVO.getValidStatusGeneralTypeId());
            }//end of  if(validationVO.getVisitDoneYn().equalsIgnoreCase("Y"))
            frmHospValidation.set("remarks", validationVO.getRemarks());
            sbfCaption.append("Edit ").append("[").append(TTKCommon.getWebBoardDesc(request)).append("]");
            frmHospValidation.set("caption",String.valueOf(sbfCaption));
            return this.getForward(strEditValidation, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospValidationError));
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
    		setLinks(request);
    		log.debug("Inside doReset method");
    		DynaActionForm frmHospValidation = (DynaActionForm)form;
    		//get the session bean from the bean pool for each excecuting thread
            HospitalManager hospitalObject=this.getHospitalManagerObject();
            StringBuffer sbfCaption= new StringBuffer();
            sbfCaption.append("Validation Details -");
            ValidationDetailVO validationVO=new ValidationDetailVO();
            Long lngValidationSeqId=TTKCommon.getLong((String)frmHospValidation.get("validationSeqId"));
            if(lngValidationSeqId!=null)
            {
                frmHospValidation.initialize(mapping);  //initialize the form bean for add mode
                validationVO=hospitalObject.getValidationDetail(lngValidationSeqId);
                frmHospValidation.set("validationReqd", validationVO.getValidationReqYn());
                frmHospValidation.set("visitDone", validationVO.getVisitDoneYn());
                frmHospValidation.set("markedDate",(String)validationVO.getMarkedDate());
                frmHospValidation.set("validationSeqId",validationVO.getValidateSeqId().toString());
                if(validationVO.getVisitDoneYn().equalsIgnoreCase("Y"))
                {
                    frmHospValidation.set("validatedBy", validationVO.getValidatedBy());
                    String strSplitedDate[]=validationVO.getValidatedDate().split(" ");
                    if(strSplitedDate.length==3)
                    {
                        frmHospValidation.set("validatedDate", strSplitedDate[0]);
                        frmHospValidation.set("validatedTime",strSplitedDate[1]);
                        frmHospValidation.set("day",strSplitedDate[2]);
                    }//end of if(strSplitedDate.length==3)
                    frmHospValidation.set("reportRcvd",validationVO.getReportRcvdYn());
                    frmHospValidation.set("validationStatus",validationVO.getValidStatusGeneralTypeId());
                }//end of  if(validationVO.getVisitDoneYn().equalsIgnoreCase("Y"))
                frmHospValidation.set("remarks", validationVO.getRemarks());
                sbfCaption.append("Edit ").append("[").append(TTKCommon.getWebBoardDesc(request)).append("]");
            }// end of if(frmHospValidation.get("validationSeqId")!=null)
            else
            {
                sbfCaption.append("Add ").append("[").append(TTKCommon.getWebBoardDesc(request)).append("]");
                frmHospValidation.initialize(mapping);  //initialize the form bean for add mode
            }//end of else
            frmHospValidation.set("caption",String.valueOf(sbfCaption));
            return this.getForward(strEditValidation, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospValidationError));
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
    		log.debug("Inside doClose method");
    		//get the session bean from the bean pool for each excecuting thread
            HospitalManager hospitalObject=this.getHospitalManagerObject();
            TableData   tableData = TTKCommon.getTableData(request);
    		//refresh the data in cancel mode, in order to get the new records if any.
            ArrayList alHospValidation = hospitalObject.getValidationList(tableData.getSearchData());
            tableData.setData(alHospValidation,strCancel);
            request.getSession().setAttribute("tableData",tableData);
            //forward to the grid screen
            return this.getForward(strValidation, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospValidationError));
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
    		log.debug("Inside doDeleteList method");
    		//get the session bean from the bean pool for each excecuting thread
            HospitalManager hospitalManagerObj=this.getHospitalManagerObject();
            TableData tableData = TTKCommon.getTableData(request);
    		ArrayList alHospitalList=new ArrayList();
    		StringBuffer sbfDeleteId = new StringBuffer("|");
    		//populate the delete string which contains the sequence id's to be deleted
    		sbfDeleteId.append(populateDeleteId(request, (TableData)request.getSession().getAttribute("tableData")));
    		//delete the feedback Details
    		int iCount = hospitalManagerObj.deleteValidation(sbfDeleteId.append("|").toString());
    		//fetch the data from previous set of rowcounts, if all the records are deleted for the current set of search criteria  
    		if(iCount == tableData.getData().size())
    		{
    			tableData.modifySearchData("DeleteList");//modify the search data
    			int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
    					getSearchData().size()-2));
    			if(iStartRowCount > 0)
    			{
    				alHospitalList = hospitalManagerObj.getValidationList(tableData.getSearchData());
    			}//end of if(iStartRowCount > 0)
    		}//end if(iCount == tableData.getData().size())
    		else
    		{
    			alHospitalList = hospitalManagerObj.getValidationList(tableData.getSearchData());
    		}//end of else
    		tableData.setData(alHospitalList, "DeleteList");
    		request.getSession().setAttribute("tableData",tableData);
    		return this.getForward(strValidation, mapping, request);  
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospValidationError));
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
    		log.debug("Inside doDelete method");
    		//get the session bean from the bean pool for each excecuting thread
            HospitalManager hospitalManagerObj=this.getHospitalManagerObject();
            TableData tableData = TTKCommon.getTableData(request);
    		DynaActionForm frmHospValidation = (DynaActionForm)form;
    		ArrayList alHospitalList=new ArrayList();
    		StringBuffer sbfDeleteId = new StringBuffer("|");
    		//populate the delete string which contains the sequence id's to be deleted
    		if(!(request.getParameter("rownum")).equals(""))
    		{    
    			sbfDeleteId.append(String.valueOf(((ValidationDetailVO)((TableData)request.getSession().
    					getAttribute("tableData")).getData().get(Integer.parseInt(request.getParameter("rownum")))).getValidateSeqId()));
    		}// end of if(!(request.getParameter("rownum")).equals(""))    
    		else //After adding a record and deleting the record this else block is used
    		{
    			sbfDeleteId.append(frmHospValidation.get("validationSeqId"));
    			//this is used if there is only one record
    			int iCount = hospitalManagerObj.deleteValidation(sbfDeleteId.append("|").toString());
    			log.debug("iCount value is :"+iCount);
    			return this.getForward(strValidation, mapping, request);
    		}// end of else
    		//delete the feedback Details
    		int iCount = hospitalManagerObj.deleteValidation(sbfDeleteId.append("|").toString());
    		//fetch the data from previous set of rowcounts, if all the records are deleted for the current set of search criteria  
    		if(iCount == tableData.getData().size())
    		{
    			tableData.modifySearchData("Delete");//modify the search data
    			int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
    					getSearchData().size()-2));
    			if(iStartRowCount > 0)
    			{
    				alHospitalList = hospitalManagerObj.getValidationList(tableData.getSearchData());
    			}//end of if(iStartRowCount > 0)
    		}//end if(iCount == tableData.getData().size())
    		else
    		{
    			alHospitalList = hospitalManagerObj.getValidationList(tableData.getSearchData());
    		}//end of else
    		tableData.setData(alHospitalList, "Delete");
    		request.getSession().setAttribute("tableData",tableData);
    		return this.getForward(strValidation, mapping, request);  
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strHospValidationError));
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
                        sbfDeleteId .append(String.valueOf(((ValidationDetailVO)tableData.getData().
                        		get(Integer.parseInt(strChk[i]))).getValidateSeqId().intValue()));
                    }//end of if(i == 0)
                    else
                    {
                        sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((ValidationDetailVO)
                        		tableData.getData().get(Integer.parseInt(strChk[i]))).getValidateSeqId().intValue()));
                    }//end of else
                }//end of if(strChk[i]!=null)
            }//end of for(int i=0; i<strChk.length;i++) 
        }//end of if(strChk!=null&&strChk.length!=0) 
        return sbfDeleteId.toString();
    }//end of populateDeleteId(HttpServletRequest request, TableData tableData)
    
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
            }//end if
        }//end of try 
        catch(Exception exp) 
        {
            throw new TTKException(exp, "hospital");
        }//end of catch
        return hospitalManager;
    }//end getHospitalManagerObject()
    
    /**
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmHospValidation formbean which contains the search fields
     * @param lHospitalSeqId Hospital Seq id of selected Hospital
     * @return ArrayList contains search parameters
     */
    private ArrayList<Object> populateSearchCriteria(Long lHospitalSeqId)
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        
        //alSearchParams.add(new String("1"));
        alSearchParams.add(new SearchCriteria("HOSP_SEQ_ID",lHospitalSeqId.toString()));// Got the Hospital Seq Id From Session
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmHospValidation,Long lHospitalSeqId)
}//end of HospitalValidationAction 
