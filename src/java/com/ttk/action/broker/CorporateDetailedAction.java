/**
 *   @ (#) CorporateDetailedAction.java Jan 5, 2016
 *   Project 	   : Dubai Project
 *   File          : CorporateDetailedAction.java
 *   Author        : Nagababu K
 *   Company       : RCS
 *   Date Created  : Jan 5, 2015
 *
 *   @author       :  Nagababu K
 *   Modified by   :
 *   Modified date :
 *   Reason        :
 *
 */
package com.ttk.action.broker;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.broker.OnlineBrokerManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.brokerlogin.BrokerVO;

/**
 * This class is used for searching of pre-auth.
 * This class also provides option for deletion of pre-auth.
 */

public class CorporateDetailedAction extends TTKAction
{
	private static Logger log = Logger.getLogger(CorporateDetailedAction.class);
	//declrations of modes
	private static final String strForward="Forward";
	private static final String strBackward="Backward";

	//Action mapping forwards.
	private static final String strPolicyList="policyList";
	private static final String strPolicySelection="policySelection";
	private static final String strMemerSelection="memberSelection";
	 private static final String strCorporateSearch="corporateSearch";
	 private static final String strCorporateDetailedSearch="CorporateDetailedSearch";
	 
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
			setOnlineLinks(request);
			log.debug("Inside CorporateDetailedAction doDefault");
			DynaActionForm frmBroDashBoard=(DynaActionForm)form;
			TableData tableData =TTKCommon.getTableData(request);
			//create new table data object
			tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("BrokerCorporateDetailedTable",new ArrayList<Object>());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)frmBroDashBoard).initialize(mapping);//reset the form data
			return this.getForward(strCorporateDetailedSearch, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strCorporateDetailedSearch));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doPolicyFileDownload(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside CorporateDetailedAction doPolicyFileDownload");
			String fileName=request.getParameter("fileName");
			String filaPath=TTKPropertiesReader.getPropertyValue("policyPrososalsUpload");
			 ByteArrayOutputStream baos=null;
		     OutputStream sos = null;
		     FileInputStream fis = null; 
		     BufferedInputStream bis =null;
			 response.setContentType("application/txt");
		     response.setHeader("Content-Disposition", "attachment;filename="+fileName);
		     File file=new File(filaPath+"/"+fileName);
			if(fileName==null|| fileName.length()<1 || !file.exists()){
				 sos = response.getOutputStream();
				 sos.write("File Not Exist".getBytes());
				 sos.flush(); 
			}else{
		                                                       
                  fis= new FileInputStream(file);
                  bis = new BufferedInputStream(fis);
                  baos=new ByteArrayOutputStream();
                  int ch;
                        while ((ch = bis.read()) != -1) baos.write(ch);
                        sos = response.getOutputStream();
                        baos.writeTo(sos);  
                        baos.flush();      
                        sos.flush(); 
                        try{
                            if(baos!=null)baos.close();                                           
                            if(sos!=null)sos.close();
                            if(bis!=null)bis.close();
                            if(fis!=null)fis.close();
                            }catch(Exception exception){
                            log.error(exception.getMessage(), exception);
                            }     
			}
			return null;
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strMemerSelection));
		}//end of catch(Exception exp)
	}//end of doPolicyFileDownload(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside CorporateDetailedAction doSearch");
			setOnlineLinks(request);
			OnlineBrokerManager brokerManager=this.getBrokerManagerObject();
			//String broUserId=(String)request.getSession().getAttribute("broUserId");
			HttpSession session=request.getSession();
			String brokerCode=(String)session.getAttribute("brokerCode");
			TableData tableData =TTKCommon.getTableData(request);
			BrokerVO	brokerVO=(BrokerVO)session.getAttribute("selectedBrokerVO");
			String corporateID=brokerVO.getCorporateID();
			//clear the dynaform if visting from left links for the first time
			//else get the dynaform data from session
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}// end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return this.getForward(strCorporateDetailedSearch, mapping, request);
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else{
				//create the required grid table
				tableData.createTableInfo("BrokerCorporateDetailedTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,corporateID,brokerCode));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList<Object> alMemberList= brokerManager.getMemberList(tableData.getSearchData());
			tableData.setData(alMemberList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strCorporateDetailedSearch, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strCorporateDetailedSearch));
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
			log.debug("Inside CorporateDetailedAction doForward");
			setOnlineLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			OnlineBrokerManager brokerManager=this.getBrokerManagerObject();
			tableData.modifySearchData(strForward);//modify the search data
			ArrayList<Object> alMemberList = brokerManager.getMemberList(tableData.getSearchData());
			tableData.setData(alMemberList, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strCorporateDetailedSearch, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strCorporateDetailedSearch));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	 
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
    public ActionForward goSelectedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the corporateSearch method of BrokerAction");
            setOnlineLinks(request);
            String strForward="";
            String btnMode=request.getParameter("btnMode");
            if("Summary".equals(btnMode))strForward="CorporateSummary";
            else if("Detailed".equals(btnMode))strForward="CorporateDetailedSearch";
            return mapping.findForward(strForward);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processBrokerExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processBrokerExceptions(request, mapping, new TTKException(exp,strPolicyList));
        }//end of catch(Exception exp)
    }//end of corporateSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
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
	public ActionForward doDetailedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CorporateDetailedAction doDetailedView");
			setOnlineLinks(request);
			HttpSession session=request.getSession();
			OnlineBrokerManager brokerManager=this.getBrokerManagerObject();
			DynaActionForm frmBroDashBoard=(DynaActionForm)form;
			String summaryView=frmBroDashBoard.getString("summaryView");
			//String brokerCode=(String)session.getAttribute("brokerCode");
			BrokerVO	brokerVO=(BrokerVO)session.getAttribute("selectedDetailedBrokerVO");
			//Long groupSeqID=brokerVO.getCorporateSeqID();
			HashMap<String,Object> corporateDetailedMap = brokerManager.getDetailedViewDetails(brokerVO.getEnrolmentID(),summaryView);
             session.setAttribute("corporateDetailedMap", corporateDetailedMap);
             session.setAttribute("frmBroDashBoard", frmBroDashBoard);
			return this.getForward(strMemerSelection, mapping, request); 
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strMemerSelection));
		}//end of catch(Exception exp)
	}//end of doDetailedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doViewPreauthDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CorporateDetailedAction doViewPreauthDetails");
			setOnlineLinks(request);
			HttpSession session=request.getSession();
			OnlineBrokerManager brokerManager=this.getBrokerManagerObject();
			DynaActionForm frmBroDashBoard=(DynaActionForm)form;
			String preAuthNO=frmBroDashBoard.getString("preAuthNO");
			HashMap<String,Object> corporateDetailedMap = brokerManager.getPreauthDetails(preAuthNO);
             session.setAttribute("corporateDetailedMap", corporateDetailedMap);
             session.setAttribute("frmBroDashBoard", frmBroDashBoard);
			return this.getForward(strMemerSelection, mapping, request); 
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strMemerSelection));
		}//end of catch(Exception exp)
	}//end of doViewPreauthDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doViewClaimDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CorporateDetailedAction doViewClaimDetails");
			setOnlineLinks(request);
			HttpSession session=request.getSession();
			OnlineBrokerManager brokerManager=this.getBrokerManagerObject();
			DynaActionForm frmBroDashBoard=(DynaActionForm)form;
			String claimNO=frmBroDashBoard.getString("claimNO");
			HashMap<String,Object> corporateDetailedMap = brokerManager.getClaimDetails(claimNO);
             session.setAttribute("corporateDetailedMap", corporateDetailedMap);
             session.setAttribute("frmBroDashBoard", frmBroDashBoard);
			return this.getForward(strMemerSelection, mapping, request); 
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strMemerSelection));
		}//end of catch(Exception exp)
	}//end of doViewClaimDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doTob(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CorporateDetailedAction doTob");
			setOnlineLinks(request);
			HttpSession session=request.getSession();
			OnlineBrokerManager brokerManager=this.getBrokerManagerObject();
			DynaActionForm frmBroDashBoard=(DynaActionForm)form;
			BrokerVO brokerVO=(BrokerVO)session.getAttribute("selectedBrokerVO");
			HashMap<String,Object> corporateDetailedMap = brokerManager.getTob(brokerVO.getPolicySeqID());
             session.setAttribute("corporateDetailedMap", corporateDetailedMap);
             session.setAttribute("frmBroDashBoard", frmBroDashBoard);
			return this.getForward(strMemerSelection, mapping, request); 
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strMemerSelection));
		}//end of catch(Exception exp)
	}//end of doTob(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
	public ActionForward doEndorsements(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CorporateDetailedAction doEndorsements");
			setOnlineLinks(request);
			HttpSession session=request.getSession();
			OnlineBrokerManager brokerManager=this.getBrokerManagerObject();
			DynaActionForm frmBroDashBoard=(DynaActionForm)form;
			String brokerCode=(String)session.getAttribute("brokerCode");
			BrokerVO	brokerVO=(BrokerVO)session.getAttribute("selectedDetailedBrokerVO");
			HashMap<String,Object> corporateDetailedMap = brokerManager.getEndorsements(brokerCode, brokerVO.getEnrolmentID());
             session.setAttribute("corporateDetailedMap", corporateDetailedMap);
             session.setAttribute("frmBroDashBoard", frmBroDashBoard);
			return this.getForward(strMemerSelection, mapping, request); 
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strMemerSelection));
		}//end of catch(Exception exp)
	}//end of doEndorsements(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
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
			log.debug("Inside CorporateDetailedAction doBackward");
			setOnlineLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			OnlineBrokerManager brokerManager=this.getBrokerManagerObject();
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList<Object> alMemberList = brokerManager.getMemberList(tableData.getSearchData());
			tableData.setData(alMemberList, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
			return this.getForward(strCorporateDetailedSearch, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strCorporateDetailedSearch));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
	public ActionForward doViewMemberDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside CorporateDetailedAction doViewMemberDetails");
			setOnlineLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				BrokerVO brokerVO=(BrokerVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				request.getSession().setAttribute("selectedDetailedBrokerVO", brokerVO);
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward(strMemerSelection);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processBrokerExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processBrokerExceptions(request, mapping, new TTKException(exp,strCorporateDetailedSearch));
		}//end of catch(Exception exp)
	}//end of doViewPolicyDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	  //HttpServletResponse response)
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
    public ActionForward goBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the goBack method of CorporateDetailedAction");
            setOnlineLinks(request);            
            DynaActionForm frmBroDashBoard =(DynaActionForm)form;
            frmBroDashBoard.initialize(mapping);     //reset the form data
            String strFarward="Broker.Home.DashBoard";
           if("SelectionView".equals(request.getParameter("backID")))strFarward=strPolicySelection;
           else if("MemSearch".equals(request.getParameter("backID")))strFarward=strCorporateDetailedSearch;
            return mapping.findForward(strFarward);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processBrokerExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processBrokerExceptions(request, mapping, new TTKException(exp,strPolicyList));
        }//end of catch(Exception exp)
    }//end of goBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmPreAuthList formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmBroDashBoard,String corporateID,String brokerCode)
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmBroDashBoard.getString("employeeName")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmBroDashBoard.getString("enrolmentID")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmBroDashBoard.getString("employeeNO")));
		alSearchParams.add(corporateID);
		alSearchParams.add(brokerCode);
    	return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmProductList)

	/**
	 * Returns the BrokerManager session object for invoking methods on it.
	 * @return BrokerManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private OnlineBrokerManager getBrokerManagerObject() throws TTKException
	{
		OnlineBrokerManager brokerManager = null;
		try
		{
			if(brokerManager == null)
			{
				InitialContext ctx = new InitialContext();
				brokerManager = (OnlineBrokerManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineBrokerManagerBean!com.ttk.business.broker.OnlineBrokerManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strCorporateSearch);
		}//end of catch
		return brokerManager;
	}//end getBrokerManagerObject()
}//end of CorporateDetailedAction