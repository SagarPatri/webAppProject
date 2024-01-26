
/**
 * as per Hospital Login
 * @ (#) OnlineCashlessHospAction Mar 24, 2014
 *  Author       :Kishor kumar S h
 * Company      : Rcs Technologies
 * Date Created : Oct 29 ,2014
 *
 * @author       :Kishor kumar S h
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.hospital;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;

import sun.misc.BASE64Encoder;
  





import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.ConfigurationManager;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.business.preauth.MemberHistoryManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.HospPreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.empanelment.HospitalAuditVO;
import com.ttk.dto.empanelment.PreRequisiteVO;
import com.ttk.dto.enrollment.MemberDetailVO;
import com.ttk.dto.hospital.HospPreAuthVO;
//import com.ttk.dto.onlineforms.OnlineInsPolicyVO;
//import com.ttk.dto.preauth.PreAuthVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import com.ttk.dto.preauth.CashlessDetailVO;
import com.ttk.dto.preauth.CashlessVO;

import emiratesid.ae.exceptions.MiddlewareException;
import emiratesid.ae.publicdata.PublicDataParser;
import emiratesid.ae.readersmgt.ATRSetting;
import emiratesid.ae.readersmgt.PCSCReader;
import emiratesid.ae.readersmgt.ReaderManagement;
import emiratesid.ae.utils.Utils;
import formdef.plugin.util.FormUtils;

import java.util.Date;//kocnewhosp1
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.ResultSet;
import java.text.*;//kocnewhosp1

/**
 * This class is used for Searching the List Policies to see the Online Account Info.
 * This also provides deletion and updation of products.
 */
public class OnlineCashlessHospAction extends TTKAction {

    private static final Logger log = Logger.getLogger( OnlineCashlessHospAction.class );

    //Modes.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";

    // Action mapping forwards.
    private static final String strOnlineClaimList="cashlessAdd";
    private static final String strOnlineOptList="onlinoptlist";
    private static final String strShowTests="showtests";
    private static final String strOnlineEnterList="diagenteramounts";
    //Exception Message Identifier
    private static final String strHospSerachInfo="onlinehospitalinfo";
    private static final String strHosClaimDetail="hospclaimdetail";
    private static final String strHosNotify="hospnotify";
    private static final String strOTPVAlidate="otpvalidate";
    private static final String strPreAuthOpt="Cashless Intimation";
    private static final String strMemberVitals="memberVitals";
    private static final String strAddLaboratories="addLaboratories";
    

	 private static final String strReportExp="onlinereport";
	 private static final String strReportdisplay="reportdisplay";
	 private static final String strMemberList="memberSearchList";
	 private static final String strClaimSearchError="hospitalsearch";
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
            log.info("Inside the doDefault method of OnlineCashlessHospAction");
            setOnlineLinks(request);
            
            DynaActionForm frmCashlessAdd =(DynaActionForm)form;
            
            frmCashlessAdd.initialize(mapping);     //reset the form data
            
            SimpleDateFormat dateFormate = new SimpleDateFormat("dd/MM/yyyy");
            
            String date=dateFormate.format(new Date());
            
            frmCashlessAdd.set("treatmentDate", date);
            
			request.getSession().setAttribute("frmCashlessAdd", frmCashlessAdd);
            return this.getForward(strOnlineClaimList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineClaimList));
        }//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
            setOnlineLinks(request);
            log.debug("Inside the doSearch method of OnlineClmSearchHospAction");
			OnlineAccessManager onlineAccessManager = null;
			TableData tableData=null;
			String strPageID =""; 
			String strSortID ="";	
			ArrayList alOnlineAccList = null;
			onlineAccessManager = this.getOnlineAccessManagerObject();
			tableData=(TableData)request.getSession().getAttribute("tableData");
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strOnlineClaimList));
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
				tableData.createTableInfo("HospClaimsTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));						
				tableData.modifySearchData("search");
				//sorting based on investSeqID in descending order													
			}//end of else
			alOnlineAccList= onlineAccessManager.getHospClaimsList(tableData.getSearchData());
			tableData.setData(alOnlineAccList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strOnlineClaimList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
        }//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    public ActionForward doDefaultOptDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doDefaultOptDetails method of OnlineCashlessHospAction");
            setOnlineLinks(request);
            TableData tableData =null;

            DynaActionForm frmCashlessAdd =(DynaActionForm)form;
            frmCashlessAdd.initialize(mapping);     //reset the form data
            //get the tbale data from session if exists
           /* tableData =TTKCommon.getTableData(request);
            //create new table data object
            tableData = new TableData();
            //create the required grid table
            tableData.createTableInfo("HospCashlessTable",new ArrayList());
            request.getSession().setAttribute("tableData",tableData);*/
            //((DynaActionForm)form).initialize(mapping);//reset the form data
            return this.getForward(strOnlineOptList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
        }//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	public ActionForward doSearchOpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			setOnlineLinks(request);
			log.debug("Inside the doSearchOpt method of OnlineCashlessHospAction");
			OnlineAccessManager onlineAccessManager = null;
			TableData tableData=null;
			String strPageID =""; 
			String strSortID ="";	
			ArrayList alOnlineAccList = null;
			onlineAccessManager = this.getOnlineAccessManagerObject();
			tableData=(TableData)request.getSession().getAttribute("tableData");
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			{
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strOnlineOptList));
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
				tableData.createTableInfo("HospCashlessTable",null);
				tableData.setSearchData(this.populateSearchCriteriaOpt((DynaActionForm)form,request));	
				tableData.modifySearchData("search");
				//sorting based on investSeqID in descending order													
			}//end of else
			alOnlineAccList= onlineAccessManager.getHospCashlessOptList(tableData.getSearchData());
			tableData.setData(alOnlineAccList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			
			//finally return to the grid screen
			return this.getForward(strOnlineOptList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)


    
    
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
            log.debug("Inside the doBackward method of OnlineClmSearchHospAction");
            setOnlineLinks(request);
            //get the session bean from the bean pool for each excecuting thread
            OnlineAccessManager onlineAccessManager=null;
            TableData tableData =null;
            ArrayList alPolicyList =null;
            onlineAccessManager =this.getOnlineAccessManagerObject();
            tableData = TTKCommon.getTableData(request);
            tableData.modifySearchData(strBackward);
            alPolicyList = onlineAccessManager.getHospClaimsList(tableData.getSearchData());
            tableData.setData(alPolicyList, strBackward);
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward(strOnlineClaimList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
        }//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest
            //request,HttpServletResponse response)

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
            log.debug("Inside the doForward method of OnlineClmSearchHospAction");
            setOnlineLinks(request);
            //get the session bean from the bean pool for each excecuting thread
            OnlineAccessManager onlineAccessManager=null;
            TableData tableData =null;
            ArrayList alPolicyList =null;
            onlineAccessManager =this.getOnlineAccessManagerObject();
            tableData = TTKCommon.getTableData(request);
            tableData.modifySearchData(strForward);
            alPolicyList = onlineAccessManager.getHospClaimsList(tableData.getSearchData());
            tableData.setData(alPolicyList, strForward);
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward(strOnlineClaimList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
        }//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
    
   
    private OnlineAccessManager getOnlineAccessManagerObject() throws TTKException
    {
    	OnlineAccessManager onlineAccessManager = null;
        try
        {
            if(onlineAccessManager == null)
            {
                InitialContext ctx = new InitialContext();
                //onlineAccessManager = (OnlineAccessManager) ctx.lookup(OnlineAccessManager.class.getName());
                onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strHospSerachInfo);
        }//end of catch
        return onlineAccessManager;
    }//end of getOnlineAccessManagerObject()

    
    
    
    
    private ArrayList<Object> populateDashBoardSearch(HttpServletRequest request) throws TTKException
	{
		// build the column names along with their values to be searched
		ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();
		UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
		//prepare the search BOX parameters
		Long lngHospSeqId =userSecurityProfile.getHospSeqId();
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)request.getParameter("sStatus")));
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(null);
		alSearchBoxParams.add(lngHospSeqId);
		return alSearchBoxParams;
	}//end of populateSearchCriteria(DynaActionForm frmPatHospSearch,HttpServletRequest request)  
	
    
    
        /**
	     * this method will add search criteria fields and values to the arraylist and will return it
	     * @param frmClmHospSearch formbean which contains the search fields
	     * @return ArrayList contains search parameters
	     */
	    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmClmHospSearch,HttpServletRequest request) throws TTKException
	    {
	//      build the column names along with their values to be searched
	        ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();
	        UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	        //prepare the search BOX parameters
	      //  Long lngHospSeqId =userSecurityProfile.getHospSeqId();
    	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sClaimNumber")));
    	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sAuthNumber")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sEnrollmentNumber")));
	    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sStatus")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sPolicyNumber")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sDOA")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sDateOfClm")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sClmStartDate")));
			alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sClmEndDate")));   //sPatStartDate,sPatEndDate,sClmStartDate,sClmEndDate
	        alSearchBoxParams.add(userSecurityProfile.getHospSeqId());
	        //new Req
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sPatientName")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmClmHospSearch.getString("sDischargeDate")));
	    	return alSearchBoxParams;
	    }//end of populateSearchCriteria(DynaActionForm frmClmHospSearch,HttpServletRequest request) 
	    
	    
	    /**
	     * this method will add search criteria fields and values to the arraylist and will return it
	     * @param frmCashlessAdd formbean which contains the search fields
	     * @return ArrayList contains search parameters
	     */
	    private ArrayList<Object> populateSearchCriteriaOpt(DynaActionForm frmCashlessAdd,HttpServletRequest request) throws TTKException
	    {
	//      build the column names along with their values to be searched
	        ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();
	        UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	        //prepare the search BOX parameters
	      //  Long lngHospSeqId =userSecurityProfile.getHospSeqId();
    	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCashlessAdd.getString("sCashlessNumber")));
    	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCashlessAdd.getString("sAuthNumber")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCashlessAdd.getString("sMemberName")));
	    	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCashlessAdd.getString("sEnrollmentNumber")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCashlessAdd.getString("sOtpReq")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCashlessAdd.getString("sBillsPending")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCashlessAdd.getString("sStartDate")));
	        alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmCashlessAdd.getString("sEndDate")));
	        alSearchBoxParams.add(userSecurityProfile.getHospSeqId());
	        
	    	return alSearchBoxParams;
	    }//end of populateSearchCriteria(DynaActionForm frmClmHospSearch,HttpServletRequest request) 
	    
	    
	    /**
	     * This method is used to copy the selected records to web-board.
	     * Finally it forwards to the appropriate view based on the specified forward mappings
	     *
	     * @param mapping The ActionMapping used to select this instance
	     * @param form The optional ActionForm bean for this request (if any)
	     * @param request The HTTP request we are processing
	     * @param response The HTTP response we are creating
	     * @return ActionForward Where the control will be forwarded, after this request is processed
	     * @throws Exception if any error occurs
	     */
	    public ActionForward doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    													HttpServletResponse response) throws Exception{
	    	try{
	    		log.debug("Inside the doCopyToWebBoard method of ClaimsAction");
	    		setLinks(request);
	    		this.populateWebBoard(request);
				return this.getForward(strOnlineClaimList, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
			}//end of catch(Exception exp)
	    }//end of doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	    		log.debug("Inside the doView method of ClaimsAction");
	    		setOnlineLinks(request);
	    		//get the tbale data from session if exists
				TableData tableData =TTKCommon.getTableData(request);
	    		DynaActionForm frmCashlessAdd=(DynaActionForm)form;
	    		String rownum=request.getParameter("rownum");
				if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				{
					CashlessVO preAuthVO=(CashlessVO)tableData.getRowInfo(Integer.parseInt((String)
																			(request.getParameter("rownum"))));
					String diagid=""+preAuthVO.getDiagGenSeqId();
					request.setAttribute("diagSeqIdForSelfFund",diagid);
					//frmCashlessAdd.set("diagSeqIdForSelfFund",diagid);
					this.addToWebBoard(preAuthVO, request);
				}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				return mapping.findForward(strHosClaimDetail);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
			}//end of catch(Exception exp)
	    }//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	    /**
	     * This method is used to navigate to detail screen to view selected record.
	     * Finally it forwards to the appropriate view based on the specified forward mappings
	     *
	     * @param mapping The ActionMapping used to select this instance
	     * @param form The optional ActionForm bean for this request (if any)
	     * @param request The HTTP request we are processing
	     * @param response The HTTP response we are creating
	     * @return ActionForward Where the control will be forwarded, after this request is processed
	     * @throws Exception if any error occurs
	     */
	    public ActionForward doViewOpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				HttpServletResponse response) throws Exception {
						try
						{
							setLinks(request);
							log.info("Inside HistoryAction doView");
							Toolbar toolBar = (Toolbar)request.getSession().getAttribute("toolbar");
							OnlineAccessManager onlineAccessManager=null;
							ArrayList alOnlineAccList = null;
							DynaActionForm frmCashlessAdd =(DynaActionForm)form;
				            frmCashlessAdd.initialize(mapping); 
				            onlineAccessManager =this.getOnlineAccessManagerObject();
							UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
							//check if user trying to hit the tab directly with out selecting the hospital
							String strLink = TTKCommon.getActiveSubLink(request);
							
							//DynaActionForm frmHistoryDetail=(DynaActionForm)request.getAttribute("frmHistoryDetail");
							Document historyDoc=null;
							String strFwdHistory = null;
							String strEnrollmentId = "";
							String strWebBoardDesc = "";
							String strEnrollmentID = "";
							StringBuffer strCaption=new StringBuffer();
							String strForward="";
							//DynaActionForm frmHosHistoryDetail=(DynaActionForm)form;
							
							TableData tableData=TTKCommon.getTableData(request);
							
							if(strLink.equals(strPreAuthOpt))//if it is from PreAuth flow
							{
							if(HospPreAuthWebBoardHelper.checkWebBoardId(request)==null)
							{
								TTKException expTTK = new TTKException();
								expTTK.setMessage("error.PreAuthorization.required");
								throw expTTK;
							}//end of if(PreAuthWebBoardHelper.checkWebBoardId(request)==null)
							}
							
								strWebBoardDesc = HospPreAuthWebBoardHelper.getWebBoardDesc(request);
								String enrollmentId=HospPreAuthWebBoardHelper.getEnrollmentId(request);
								
								String seqid=(String) request.getAttribute("diagSeqIdForSelfFund");
								ArrayList alDiagnosysEnrolResult	= onlineAccessManager.getValidateSearchOpt(new Long(seqid));
								ArrayList alDiagnosysTotalResult= onlineAccessManager.getDiagOptTestTotalAmnts(new Long(seqid));
								
								CashlessDetailVO c2	=	new CashlessDetailVO();
								CashlessVO c1	=	new CashlessVO();
								
								
								if(alDiagnosysTotalResult!=null && alDiagnosysTotalResult.size()>0)
								{
									c1=(CashlessVO)alDiagnosysTotalResult.get(0);
									String enrollId=c1.getEnrollmentID();
									String status=c1.getStatus();
									request.getSession().setAttribute("enrollId", enrollId);
									frmCashlessAdd.set("enrollId", enrollId);
								
									if(status.equalsIgnoreCase("N"))
									{
										strForward	=	"otpvalidate";
									}
									if(status.equalsIgnoreCase("Y"))
									{
										strForward	=	"showtests";
									}
								
								}
								if(alDiagnosysEnrolResult!=null && alDiagnosysEnrolResult.size()>0)
								{	
									request.getSession().setAttribute("alDiagnosysEnrolResult",alDiagnosysEnrolResult);	
									request.getSession().setAttribute("alDiagnosysTotalResult",alDiagnosysTotalResult);	
									strForward	=	"cashlessAddTotal";
								}	
							return this.getForward(strForward,mapping,request);
						}//end of try
						catch(TTKException expTTK)
						{
						return this.processExceptions(request, mapping, expTTK);
						}//end of catch(TTKException expTTK)
						catch(Exception exp)
						{
						return this.processExceptions(request, mapping, new TTKException(exp, strHospSerachInfo));
						}//end of catch(Exception exp)
	    }//end of doViewOpt(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	    
	    
	    /**
	     * Populates the web board in the session with the selected items in the grid
	     * @param request HttpServletRequest object which contains information about the selected check boxes
	     * @throws TTKException If any run time Excepton occures
	     */
	    private void populateWebBoard(HttpServletRequest request)throws TTKException
	    {
	    	String[] strChk = request.getParameterValues("chkopt");
	    	TableData tableData = (TableData)request.getSession().getAttribute("tableData");
	    	Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
	    	ArrayList<Object> alCacheObject = new ArrayList<Object>();
	    	CacheObject cacheObject = null;
	    	CashlessVO preAuthVO=null;

	    	if(strChk!=null&&strChk.length!=0)
	    	{
	    		for(int i=0; i<strChk.length;i++)
	    		{
	    			cacheObject = new CacheObject();
	    			preAuthVO=(CashlessVO)tableData.getData().get(Integer.parseInt(strChk[i]));
	    			cacheObject.setCacheId(this.prepareWebBoardId(preAuthVO));
	    			cacheObject.setCacheDesc(preAuthVO.getPreAuthNo());
	    			alCacheObject.add(cacheObject);
	    		}//end of for(int i=0; i<strChk.length;i++)
	    	}//end of if(strChk!=null&&strChk.length!=0)
	    	if(toolbar != null)
	    	{
	    		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
	    	}//end of if(toolbar != null)
	    }//end of populateWebBoard(HttpServletRequest request)

	    /**
	     * Adds the selected item to the web board and makes it as the selected item in the web board
	     * @param  preauthVO  object which contains the information of the preauth
	     * * @param String  strIdentifier whether it is preauth or enhanced preauth
	     * @param request HttpServletRequest
	     * @throws TTKException if any runtime exception occures
	     */
	    private void addToWebBoard(CashlessVO preAuthVO, HttpServletRequest request)throws TTKException
	    {
	    	Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
	    	ArrayList<Object> alCacheObject = new ArrayList<Object>();
	    	CacheObject cacheObject = new CacheObject();
	    	cacheObject.setCacheId(this.prepareWebBoardId(preAuthVO)); //set the cacheID
	    	cacheObject.setCacheDesc(preAuthVO.getPreAuthNo());
	    	alCacheObject.add(cacheObject);
	    	//if the object(s) are added to the web board, set the current web board id
	    	toolbar.getWebBoard().addToWebBoardList(alCacheObject);
	    	toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());

	    	//webboardinvoked attribute will be set as true in request scope
	    	//to avoid the replacement of web board id with old value if it is called twice in same request scope
	    	request.setAttribute("webboardinvoked", "true");
	    }//end of addToWebBoard(HospPreAuthVO preAuthVO, HttpServletRequest request,String strIdentifier)throws TTKException

	    /**
	     * This method prepares the Weboard id for the selected Policy
	     * @param HospPreAuthVO  preAuthVO for which webboard id to be prepared
	     * * @param String  strIdentifier whether it is preauth or enhanced preauth
	     * @return Web board id for the passedVO
	     */
	    private String prepareWebBoardId(CashlessVO preAuthVO)throws TTKException
	    {
	    	StringBuffer sbfCacheId=new StringBuffer();
	    	sbfCacheId.append(preAuthVO.getClaimSeqID()!=null? String.valueOf(preAuthVO.getClaimSeqID()):" ");
	    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getEnrollmentID()).equals("")?" ":preAuthVO.getEnrollmentID());
	    	sbfCacheId.append("~#~").append(preAuthVO.getEnrollDtlSeqID()!=null?String.valueOf(preAuthVO.getEnrollDtlSeqID()):" ");
	    	sbfCacheId.append("~#~").append(preAuthVO.getPolicySeqID()!=null? String.valueOf(preAuthVO.getPolicySeqID()):" ");
	    	sbfCacheId.append("~#~").append(preAuthVO.getMemberSeqID()!=null? String.valueOf(preAuthVO.getMemberSeqID()):" ");
	    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getClaimantName()).equals("")? " ":preAuthVO.getClaimantName());
	    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getBufferAllowedYN()).equals("")? " ":preAuthVO.getBufferAllowedYN());
	    	sbfCacheId.append("~#~").append(preAuthVO.getClmEnrollDtlSeqID()!=null? String.valueOf(preAuthVO.getClmEnrollDtlSeqID()):" ");
	    	sbfCacheId.append("~#~").append(preAuthVO.getAmmendmentYN()!=null? String.valueOf(preAuthVO.getAmmendmentYN()):" ");
	    	sbfCacheId.append("~#~").append(TTKCommon.checkNull(preAuthVO.getCoding_review_yn()).equals("")? " ":preAuthVO.getCoding_review_yn());
	    	return sbfCacheId.toString();
	    }//end of prepareWebBoardId(PreAuthVO preAuthVO,String strIdentifier)throws TTKException



	    
	    
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
	    public ActionForward doDashBoardDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	                                                     HttpServletResponse response) throws Exception{
	        try{
	            log.info("Inside the doDefault method of OnlineClmSearchHospAction");
	            setOnlineLinks(request);
	            TableData tableData =null;

	            DynaActionForm frmClmHospSearch =(DynaActionForm)form;
	            
	            return this.getForward("claimdashboard", mapping, request);//claimdashboard
	        }//end of try
	        catch(TTKException expTTK)
	        {
	            return this.processOnlineExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
	        catch(Exception exp)
	        {
	            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strHospSerachInfo));
	        }//end of catch(Exception exp)
	    }//end of doDashBoardDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
	    public ActionForward doValidate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	                                                    HttpServletResponse response) throws Exception{
	        try{
	            setOnlineLinks(request);
	            log.debug("Inside the doValidate method of OnlineCashlessHospAction");
	            DynaActionForm frmCashlessAdd =(DynaActionForm)form;
	            String strbenifitType = frmCashlessAdd.getString("benifitType");

	            if(strbenifitType==null || strbenifitType.equals(""))
	             {
	            	 TTKException expTTK = new TTKException();
	 				expTTK.setMessage("error.benifitType");
	 				throw expTTK;
	             }
	            //frmCashlessAdd.initialize(mapping);     //reset the form data
				UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
				OnlineAccessManager onlineAccessManager = null;
				CashlessDetailVO cashlessDetailVO	= null;
				ArrayList alOnlineAccList = null;
				onlineAccessManager = this.getOnlineAccessManagerObject();
				String modeType	=	 (String) (frmCashlessAdd.get("modeType")==""?request.getSession().getAttribute("modeType"):frmCashlessAdd.get("modeType"));
				String enrollId	=	(String) frmCashlessAdd.get("enrollId");
				request.getSession().setAttribute("enrollId", enrollId);
				String benifitType	=	(String) frmCashlessAdd.get("benifitType");
				
				
				Long memSeqId = null	;
				
				
				if( frmCashlessAdd.get("lngMemberSeqID")!=null||frmCashlessAdd.get("lngMemberSeqID")!=""){
					
					memSeqId =  (Long) frmCashlessAdd.get("lngMemberSeqID");
				}
				
				
				String flag	=	"";
				alOnlineAccList	= onlineAccessManager.getValidateEnrollId(enrollId,userSecurityProfile.getHospSeqId(),userSecurityProfile.getUSER_SEQ_ID(),frmCashlessAdd.getString("treatmentDate"),memSeqId);
				flag	=	(String)alOnlineAccList.get(1);
				CashlessVO	cv	=	new CashlessVO();
				cv=(CashlessVO)alOnlineAccList.get(0);
				Long memSeqIDForSelfFund	=	null;
				Long diagSeqIdForSelfFund	=	null;
				memSeqIDForSelfFund	=	cv.getMemberSeqID();
				diagSeqIdForSelfFund=	cv.getDiagGenSeqId();
				//this code is to get the member and other details based on the enroll id
				cashlessDetailVO	= onlineAccessManager.geMemberDetailsOnEnrollId(enrollId,benifitType,userSecurityProfile.getHospSeqId(),memSeqId);
				//cashlessDetailVO.setEnrollId(enrollId);
				cashlessDetailVO.setBenifitType(request.getParameter("benifit"));
				
				
				String eligibility =cashlessDetailVO.getEligibility();
				
				long preumiumFlog=0;
				if("YES".equals(eligibility)){
					 preumiumFlog=onlineAccessManager.getCheckEligibilityLog(enrollId,benifitType,userSecurityProfile.getHospSeqId(),memSeqId,userSecurityProfile.getUSER_SEQ_ID());
					 cashlessDetailVO.setEligibilitySedId(String.valueOf(preumiumFlog));
					 
				}
				
				//TOB S T A R T S
				String[] tobBenefits	=	onlineAccessManager.getTobForBenefits(benifitType,enrollId,memSeqId);
				cashlessDetailVO.setCoPay(tobBenefits[0]);
				cashlessDetailVO.setEligibility1(tobBenefits[1]);
				cashlessDetailVO.setDeductible(tobBenefits[2]);
				//request.setAttribute("tobBenefits","tobBenefits");
				//TOB E N D S
				
				frmCashlessAdd = (DynaActionForm)FormUtils.setFormValues("frmCashlessAdd",
						cashlessDetailVO,this,mapping,request);
				
				//NETWORKS VALIDATION
				String netArry[]=	new String[2];
				netArry	=	cashlessDetailVO.getNetworksArray();
				String netArrys[]	=	new String[cashlessDetailVO.getAssNetworksArray().size()];
				Map<String,String> map	=	cashlessDetailVO.getAssNetworksArray();
				
				Set keySet	=	map.keySet();
				Set valSet	=	map.keySet();
				Iterator it		=	 keySet.iterator();
				Iterator it1			=	 valSet.iterator();
				int	temp				=	0;
				int	compTemp			=	0;
				String compToPolicyNet	=	"";
				String compToPolicyNetVal=	"";
				String policyNet		=	"";
				String provNet			=	"";
				if(netArry!=null){
					policyNet	=	netArry[1];
					provNet		=	netArry[0];
				}
				ArrayList<String> networkOrder	=	new ArrayList<String>();
				
				
				
				
				while(it.hasNext() && it1.hasNext()){
					compToPolicyNet	=	(String)(it.next());
					if(compToPolicyNet.equals(policyNet))
						compTemp	=	temp;
					compToPolicyNetVal	=	map.get(it1.next());
					
					

					networkOrder.add(compToPolicyNet);//adding Network names to arrayList of all networks
					netArrys[temp++]=	compToPolicyNetVal;//adding Y/N to array of all networks
					
				}
				if(temp>1)
					temp	=	temp-1;
				
				int tempPol		=	0;
				int tempProv	=	0;
				for(int i=0;i<=networkOrder.size()-1;i++)
				{
					if(policyNet.equals(networkOrder.get(i))){
						tempPol	=	i;
					}
					if(provNet.equals(networkOrder.get(i))){
						tempProv	=	i;
					}
				}
				
				
				
				
				
				if(TTKCommon.checkNull(netArrys[compTemp]).trim().equals("Y") && "YES".equals(TTKCommon.checkNull(cashlessDetailVO.getEligibility()).trim())){//IF SELECTED OTHERS AT EMPANELMENT LEVEL
					
					request.setAttribute("eligibility", "YES");
					frmCashlessAdd.set("eligibility", "YES");
				}
				
				else if(tempProv>=tempPol && "YES".equals(TTKCommon.checkNull(cashlessDetailVO.getEligibility()))){//IF POLICY NETWORK IS GREATER THAN PROVIDER NETWORK
					
					request.setAttribute("eligibility", "YES");
					frmCashlessAdd.set("eligibility", "YES");
				}else if(TTKCommon.checkNull(netArrys[compTemp]).trim().equals("N") && "N".equals(TTKCommon.checkNull(cashlessDetailVO.getEligibility()).trim())&& tempProv==tempPol)//NOW MATERNITY CASE 
					{
					
					request.setAttribute("eligibility", "YES");
					frmCashlessAdd.set("eligibility", "YES");
					}
				
				else if(TTKCommon.checkNull(netArrys[compTemp]).trim().equals("N") && "YES".equals(TTKCommon.checkNull(cashlessDetailVO.getEligibility()))){//IF POLICY NETWORK IS GREATER THAN PROVIDER NETWORK
					
					request.setAttribute("eligibility", "YES");
					frmCashlessAdd.set("eligibility", "YES");
				}
				
				
				
				else {//REJECTION
					
					request.setAttribute("eligibility", "NO");
					frmCashlessAdd.set("eligibility", "NO");
					
					/*if(TTKCommon.checkNull(netArrys[compTemp]).trim().equals("N") && "NO".equals(TTKCommon.checkNull(cashlessDetailVO.getEligibility()).trim())
							&& tempProv==tempPol)//NOW
						frmCashlessAdd.set("reasonForRejection","Benefit is Not Covered for the Policy.");
					
					else if(TTKCommon.checkNull(netArrys[compTemp]).trim().equals("N") && "NO".equals(TTKCommon.checkNull(cashlessDetailVO.getEligibility()).trim())
							&& tempProv<tempPol)
						frmCashlessAdd.set("reasonForRejection","Network is Not Eligible for the Policy.");
					
					else if(TTKCommon.checkNull(netArrys[compTemp]).trim().equals("N") && "YES".equals(TTKCommon.checkNull(cashlessDetailVO.getEligibility()).trim())
							&& tempProv<tempPol)//NOW
						frmCashlessAdd.set("reasonForRejection","Network is Not Eligible for the Policy.");
					
					 if(TTKCommon.checkNull(netArrys[compTemp]).trim().equals("N") && "N".equals(TTKCommon.checkNull(cashlessDetailVO.getEligibility()).trim())
							&& tempProv==tempPol)//NOW MATERNITY CASE
					{
						frmCashlessAdd.set("reasonForRejection","Member is not Eligible for this Benefit");
					}
					else if(tempProv<tempPol)
						frmCashlessAdd.set("reasonForRejection","Benefit is Not Covered for the Policy.");*/
				}
				
				
				//NETWORKS VALIDATION ENDS
				
				
				
				
				request.getSession().setAttribute("memSeqIDForSelfFund", memSeqIDForSelfFund);
				request.getSession().setAttribute("diagSeqIdForSelfFund", diagSeqIdForSelfFund);
				//request.getSession().setAttribute("enrollId", enrollId);
				request.getSession().setAttribute("benifitType", cashlessDetailVO.getBenifitType());
				request.getSession().setAttribute("benifitTypeVal", benifitType);
				request.getSession().setAttribute("modeType", modeType);
				request.getSession().setAttribute("provMemName", cashlessDetailVO.getMemberName());
				request.getSession().setAttribute("tobBenefitsForMemElig", tobBenefits);
				
				request.getSession().setAttribute("eligibilitySedId",  cashlessDetailVO.getEligibilitySedId());
				/*frmCashlessAdd.set("flag", flag);*/
				request.setAttribute("flag",flag);
				request.setAttribute("logicVar","true");
				
				frmCashlessAdd.set("eligibilitySedId", String.valueOf(preumiumFlog));
				
				request.getSession().setAttribute("frmCashlessAdd",frmCashlessAdd);
				//E N D S 
				
				
				//request.setAttribute("eligibility", cashlessDetailVO.getEligibility());
				//finally return to the grid screen
				if(!"ONL1".equals(modeType))
				{
					request.setAttribute("OTP_Done","Done");
				}
				return this.getForward(strOTPVAlidate, mapping, request);
	        }//end of try
	        catch(TTKException expTTK)
	        {
	            return this.processOnlineExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
	        catch(Exception exp)
	        {
	            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineClaimList));
	        }//end of catch(Exception exp)
	    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	                            //HttpServletResponse response)
	    
	    
	    /*
	     * Check Eligibility
	     */
	    
	    //doCheckEligibility
	    /**
	     * 
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
	    public ActionForward doValidateOTP(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	                                                    HttpServletResponse response) throws Exception{
	        try{
	            setOnlineLinks(request);
	            log.debug("Inside the doValidateOTP method of OnlineCashlessHospAction");
	            DynaActionForm frmCashlessAdd =(DynaActionForm)form;
	            //frmCashlessAdd.initialize(mapping);     //reset the form data
				
				UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
				OnlineAccessManager onlineAccessManager = null;
				
				ArrayList alOnlineAccList = null;
				onlineAccessManager = this.getOnlineAccessManagerObject();
				
				Long memSeqIDForSelfFund	=	(Long)request.getSession().getAttribute("memSeqIDForSelfFund");
				Long diagSeqIDForSelfFund	=	(Long)request.getSession().getAttribute("diagSeqIdForSelfFund");
				String flag	=	"";
				
				//this code is to get the member and other details based on the enroll id
				CashlessDetailVO cashlessDetailVO	=	null;
				cashlessDetailVO	= onlineAccessManager.geMemberDetailsOnEnrollId((String)request.getSession().getAttribute("enrollId"),(String)request.getSession().getAttribute("benifitTypeVal"),new Long(0), new Long(0));
				frmCashlessAdd = (DynaActionForm)FormUtils.setFormValues("frmCashlessAdd",
						cashlessDetailVO,this,mapping,request);
				
				alOnlineAccList	= onlineAccessManager.getValidateOTP(diagSeqIDForSelfFund,request.getParameter("otp"),userSecurityProfile.getUSER_SEQ_ID());
				String outdatedYN	=	(String)alOnlineAccList.get(0);
				String blockedYN	=	(String)alOnlineAccList.get(1);
				//String blockedYN	=	"Y";
				String wrongYN		=	(String)alOnlineAccList.get(2);
				String validatedYN	=	(String)alOnlineAccList.get(3);
				String strForward	=	"";
				
				if(outdatedYN.equalsIgnoreCase("Y"))
				{
					strForward	=	"cashlessAdd";
					frmCashlessAdd.set("outdatedOtp", "Entered OTP number is outdated, Please generate new OTP number");
					request.setAttribute("outdated", "outdated");
				}
				if(blockedYN.equalsIgnoreCase("Y"))
				{
					strForward	=	"cashlessAdd";
					frmCashlessAdd.set("blockedOtp", "Entered OTP number is blocked, Please generate new OTP number");
					request.setAttribute("blocked", "blocked");
				}
				if(wrongYN.equalsIgnoreCase("Y"))
				{
					strForward	=	"otpvalidate";
					frmCashlessAdd.set("wrongOtp", "Entered OTP number is Wrong, Please enter valid OTP number");
					request.setAttribute("wrong", "wrong");
					
				}
				if(validatedYN.equalsIgnoreCase("Y"))
				{
					//strForward	=	"showtests";
					strForward		=	strOTPVAlidate;
					request.setAttribute("OTP_Done","Done");
				}
				/*request.getSession().removeAttribute("frmCashlessAdd");*/
				request.getSession().setAttribute("frmCashlessAdd",frmCashlessAdd);
				frmCashlessAdd.set("enrollId", request.getSession().getAttribute("enrollId"));
				frmCashlessAdd.set("benifitType", request.getSession().getAttribute("benifitType"));
				frmCashlessAdd.set("flag", flag);
				request.setAttribute("flag",flag);
				request.setAttribute("logicVar","true");
				
				//finally return to the grid screen
				return this.getForward(strForward, mapping, request);
	        }//end of try
	        catch(TTKException expTTK)
	        {
	            return this.processOnlineExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
	        catch(Exception exp)
	        {
	            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineClaimList));
	        }//end of catch(Exception exp)
	    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
		public ActionForward onCloseReqAmnts(ActionMapping mapping,ActionForm form,HttpServletRequest request,
						   HttpServletResponse response) throws TTKException{
			try{
			log.debug("Inside the onCloseReqAmnts method of doClose");
			 setOnlineLinks(request);

			return this.getForward(strShowTests, mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
			return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
			return this.processExceptions(request, mapping, new TTKException(exp,strShowTests));
			}//end of catch(Exception exp)
			}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
		
	   
		
		public ActionForward doSubmitTests(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                HttpServletResponse response) throws Exception{
	    	try{
	            log.info("Inside the doSubmitTests method of OnlineClmSearchHospAction");
	            setOnlineLinks(request);
	            
	            DynaActionForm frmCashlessAdd = (DynaActionForm)form;
	            frmCashlessAdd.initialize(mapping);     //reset the form data
				log.info("off id " +frmCashlessAdd.get("sOffIds"));
				request.getSession().setAttribute("sOffIds", request.getParameter("sOffIds"));
				
				String sOffIds=request.getParameter("sOffIds");
				/*String splitIds	=	sOffIds.substring(1,sOffIds.length()-1);
				splitIds	=	splitIds.replace("|","','");
				splitIds	=	"'"+splitIds+"'";
				System.out.println("splitIds ::"+splitIds);*/
				
				OnlineAccessManager onlineAccessManager = null;
				
				onlineAccessManager = this.getOnlineAccessManagerObject();
				
				ArrayList alDiagDataList= onlineAccessManager.getDiagDetails(sOffIds);
				request.setAttribute("alDiagDataList",alDiagDataList);	
				request.getSession().setAttribute("alDiagDataListSession",alDiagDataList);
				/*frmCashlessAdd.set("flag", request.getSession().getAttribute("flag"));*/
				frmCashlessAdd.set("enrollId", request.getSession().getAttribute("enrollId"));
	            frmCashlessAdd.set("flag", "true");
	            request.setAttribute("flag","true");
	            frmCashlessAdd.set("logicValidateVar", "logicValidateVar1");
	            request.setAttribute("logicValidateVar","logicValidateVar1");
	            //get the session bean from the bean pool for each excecuting thread
	            return this.getForward(strOnlineEnterList, mapping, request);
	        }//end of try
	        catch(TTKException expTTK)
	        {
	            return this.processOnlineExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
	        catch(Exception exp)
	        {
	            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineClaimList));
	        }//end of catch(Exception exp)
	    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            //HttpServletResponse response)
	    
	    
	    
	    public ActionForward doShowTests(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                HttpServletResponse response) throws Exception{
	    	try{
	            log.info("Inside the doShowTests method of OnlineClmSearchHospAction");
	            setOnlineLinks(request);
				UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
				DynaActionForm frmCashlessAdd = (DynaActionForm)form;
	            frmCashlessAdd.initialize(mapping);     //reset the form data
	            frmCashlessAdd.set("flag", "true");
	            //get the session bean from the bean pool for each excecuting thread
	            String GroupID	=	request.getParameter("GroupID");
	           /* System.out.println("GroupID ::"+GroupID);
	            request.getSession().setAttribute("GroupID", GroupID);*/
	            
	            
	            /*
	             * DynaActionForm frmCashlessAdd = (DynaActionForm)form;
	            frmCashlessAdd.initialize(mapping);
	            TableData tableTestsDetails = null;
	    		//get the Table Data From the session 
	    		if(request.getSession().getAttribute("tableTestsDetails")!=null)
	    		{
	    			tableTestsDetails=(TableData)(request.getSession()).getAttribute("tableTestsDetails");
	    		}//end of if(request.getSession().getAttribute("tableDataLinkDetails")!=null)
	    		else
	    		{
	    			tableTestsDetails=new TableData();
	    		}//end of else
	    		
	    		ArrayList alLinkDetailsList=null;
	    		
	    		
	    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
	    		if(!strSortID.equals(""))
	    		{
	    			tableTestsDetails.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
	    			tableTestsDetails.modifySearchData("sort");//modify the search data
	    		}// end of if(!strSortID.equals(""))
	    		else
	    		{
	    			tableTestsDetails.createTableInfo("DcTestsDetails",null);
	    			tableTestsDetails.setSearchData(this.populateSearchCriteriaNew(request));
	    			tableTestsDetails.modifySearchData("search");
	    		}// end of else
	    		OnlineAccessManager onlineAccessManagerObject = null;
	    		onlineAccessManagerObject = this.getOnlineAccessManagerObject();
	    		Long tempHospSeqId	=	userSecurityProfile.getHospSeqId();
	    		String hospSeqId	=	""+tempHospSeqId;
	    		
	    		alLinkDetailsList=onlineAccessManagerObject.getTestsForDC(hospSeqId);
	    		tableTestsDetails.setData(alLinkDetailsList);
	    		request.getSession().setAttribute("tableTestsDetails",tableTestsDetails);
	    		request.setAttribute("frmCashlessAdd",frmCashlessAdd);
	    		*/
	    		
	    		
	            return this.getForward(strShowTests, mapping, request);
	        }//end of try
	        catch(TTKException expTTK)
	        {
	            return this.processOnlineExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
	        catch(Exception exp)
	        {
	            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineClaimList));
	        }//end of catch(Exception exp)
	    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            //HttpServletResponse response)

	    
	    /**
		 * This method builds all the search parameters to ArrayList and places them in session
		 * @param request The HTTP request we are processing
		 * @return alSearchParams ArrayList
		 */
	    public ActionForward doSubmitEnteredRates(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                HttpServletResponse response) throws Exception{
	    	try{
				UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
	            log.info("Inside the doSubmitEnteredRates method of OnlineCashlessHospAction");
	            setOnlineLinks(request);
	            //get the session bean from the bean pool for each excecuting thread
	           String reqAmnt	=	request.getParameter("reqAmnt");
	           String reqAmnts	=	request.getParameter("reqAmnts");
	           //System.out.println("reqAmnts ::"+reqAmnts);
	           
	           	DynaActionForm frmCashlessAdd =(DynaActionForm)form;
	           	frmCashlessAdd.initialize(mapping);     //reset the form data
	           	//CashlessDetailVO.set("submitRates",TTKCommon.checkNull(reqAmnt));
	           	request.setAttribute("submitRates",reqAmnt);	
	           	request.setAttribute("reqAmnts",reqAmnts);
	           	
	           	//calling a method to save and calc amounts
	           	ArrayList alDiagDataListSession=(ArrayList)request.getSession().getAttribute("alDiagDataListSession");
	           	OnlineAccessManager onlineAccessManager = null;
				onlineAccessManager = this.getOnlineAccessManagerObject();
				Long memSeqIDForSelfFund	=	(Long)request.getSession().getAttribute("memSeqIDForSelfFund");
				Long diagSeqIdForSelfFund	=	(Long)request.getSession().getAttribute("diagSeqIdForSelfFund");
				
				//Long diagSeqIdForSelfFund	=(Long) request.getAttribute("diagSeqIdForSelfFund");
				
				ArrayList alDiagnosysEnrolResult= onlineAccessManager.saveDiagTestAmnts(alDiagDataListSession,reqAmnts,memSeqIDForSelfFund,diagSeqIdForSelfFund,userSecurityProfile.getUSER_SEQ_ID());
				
				//ArrayList alDiagnosysEnrolResult= onlineAccessManager.GetBillDetails(diagSeqIdForSelfFund);
				//ArrayList alDiagnosysTotalResult= onlineAccessManager.getDiagTestTotalAmnts(diagSeqIdForSelfFund);
				
				ArrayList alDiagnosysApprovedResult= onlineAccessManager.SubmitApproveBills(memSeqIDForSelfFund,diagSeqIdForSelfFund,userSecurityProfile.getUSER_SEQ_ID(),userSecurityProfile.getHospSeqId());
				
				ArrayList alDiagnosysTotalResult= onlineAccessManager.getDiagTestTotalAmnts(diagSeqIdForSelfFund);

				String preAuthNumb	=	(String)alDiagnosysApprovedResult.get(0);
				
				request.setAttribute("alDiagnosysEnrolResult",alDiagnosysEnrolResult);	
				request.getSession().setAttribute("alDiagnosysEnrolResult",alDiagnosysEnrolResult);
				request.getSession().setAttribute("alDiagnosysTotalResult",alDiagnosysTotalResult);
	            frmCashlessAdd.set("flag", "true");		
	            request.setAttribute("flag","true");
	            frmCashlessAdd.set("enrollId", request.getSession().getAttribute("enrollId"));
	          
	            request.setAttribute("logicValidateVar","logicValidateVar1");
	            frmCashlessAdd.set("preAuthNumb", preAuthNumb);
	            return this.getForward("cashlessAddTotal", mapping, request);
	        }//end of try
	        catch(TTKException expTTK)
	        {
	            return this.processOnlineExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
	        catch(Exception exp)
	        {
	            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineClaimList));
	        }//end of catch(Exception exp)
	    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            //HttpServletResponse response)
	    
	    
	    
	    /**
		 * This method builds all the search parameters to ArrayList and places them in session
		 * @param request The HTTP request we are processing
		 * @return alSearchParams ArrayList
		 */
	   /* public ActionForward doSubmitApproveBills(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                HttpServletResponse response) throws Exception{
	    	try{
				UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
	            log.info("Inside the doSubmitApproveBills method of OnlineCashlessHospAction");
	            setOnlineLinks(request);
	            
	           	DynaActionForm frmCashlessAdd =(DynaActionForm)form;
	           	frmCashlessAdd.initialize(mapping);     //reset the form data
	           	
	           	//calling a method to save and calc amounts
	           	ArrayList alDiagnosysTotalResult=(ArrayList)request.getSession().getAttribute("alDiagnosysTotalResult");
	           	OnlineAccessManager onlineAccessManager = null;
				onlineAccessManager = this.getOnlineAccessManagerObject();
				Long memSeqIDForSelfFund	=	(Long)request.getSession().getAttribute("memSeqIDForSelfFund");
				Long diagSeqIdForSelfFund	=	(Long)request.getSession().getAttribute("diagSeqIdForSelfFund");
				
				ArrayList alDiagnosysEnrolResult= onlineAccessManager.SubmitApproveBills(memSeqIDForSelfFund,diagSeqIdForSelfFund,userSecurityProfile.getUSER_SEQ_ID(),userSecurityProfile.getHospSeqId());
								
				request.setAttribute("alDiagnosysEnrolResult",alDiagnosysEnrolResult);	
				request.getSession().setAttribute("alDiagnosysEnrolResult",alDiagnosysEnrolResult);
	            frmCashlessAdd.set("enrollId", request.getSession().getAttribute("enrollId"));
	            request.setAttribute("flag","true");
	            frmCashlessAdd.set("logicValidateVar", "logicValidateVar1");
	            request.setAttribute("logicValidateVar","logicValidateVar1");
	            
	            return this.getForward(strOnlineClaimList, mapping, request);
	        }//end of try
	        catch(TTKException expTTK)
	        {
	            return this.processOnlineExceptions(request, mapping, expTTK);
	        }//end of catch(TTKException expTTK)
	        catch(Exception exp)
	        {
	            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineClaimList));
	        }//end of catch(Exception exp)
	    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            //HttpServletResponse response)
	    */
	    
	    
	    
	    
	    /**
		 * This method is used to navigate to Member Vital screen.
		 * Finally it forwards to the appropriate view based on the specified forward mappings
		 *
		 * @param mapping The ActionMapping used to select this instance
		 * @param form The optional ActionForm bean for this request (if any)
		 * @param request The HTTP request we are processing
		 * @param response The HTTP response we are creating
		 * @return ActionForward Where the control will be forwarded, after this request is processed
		 * @throws Exception if any error occurs
		 */
		public ActionForward doMemberVitals(ActionMapping mapping,ActionForm form,HttpServletRequest request,
						   HttpServletResponse response) throws TTKException{
			try{
				log.info("Inside the onMemberVitals method of doMemberVitals");
				 setOnlineLinks(request);
				 DynaActionForm frmCashlessMemberVitals =(DynaActionForm)form;
				 frmCashlessMemberVitals.initialize(mapping);     //reset the form data
				 request.getSession().setAttribute("frmCashlessMemberVitals", frmCashlessMemberVitals);
				return this.getForward(strMemberVitals, mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
			return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
			return this.processExceptions(request, mapping, new TTKException(exp,strOnlineClaimList));
			}//end of catch(Exception exp)
			}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
		
		
		/*
		 * 
		 * Skip Page
		 * 
		 */
		public ActionForward doSkipVitals(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				   HttpServletResponse response) throws TTKException{
		try{
		log.debug("Inside the doSkipVitals method of doClose");
		 setOnlineLinks(request);
		 DynaActionForm frmCashlessMemberVitals =(DynaActionForm)form;
		 frmCashlessMemberVitals.initialize(mapping);     //reset the form data
		 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
		 
		//this code is to get the member and other details based on the enroll id
		OnlineAccessManager onlineAccessManager	=	null;
		onlineAccessManager	=	this.getOnlineAccessManagerObject();
		CashlessDetailVO cashlessDetailVO	=	null;
		cashlessDetailVO	= onlineAccessManager.geMemberDetailsOnEnrollId((String)request.getSession().getAttribute("enrollId"),(String)request.getSession().getAttribute("benifitTypeVal"), new Long(0),new Long(0));
		frmCashlessMemberVitals = (DynaActionForm)FormUtils.setFormValues("frmCashlessMemberVitals",
				cashlessDetailVO,this,mapping,request);
		frmCashlessMemberVitals.set("enrollId", request.getSession().getAttribute("enrollId"));
		frmCashlessMemberVitals.set("providerName", userSecurityProfile.getHospitalName());
		request.setAttribute("frmCashlessMemberVitals", frmCashlessMemberVitals);
		return this.getForward("prescriptionDetails", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
		return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processExceptions(request, mapping, new TTKException(exp,strOnlineClaimList));
		}//end of catch(Exception exp)
		}//end of doSkipVitals(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
		
		
		/*
		 * 
		 * Save Page
		 * 
		 */
		public ActionForward doVitalsSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
				   HttpServletResponse response) throws TTKException{
		try{
		log.info("Inside the doSaveVitals method of doClose");
		 setOnlineLinks(request);
		 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
		 DynaActionForm frmCashlessMemberVitals =(DynaActionForm)form;
		 OnlineAccessManager onlineAccessManager	=	null;
		onlineAccessManager = this.getOnlineAccessManagerObject();
		CashlessDetailVO	cashlessDetailVO	=	new CashlessDetailVO();
		String enrollId=(String) request.getSession().getAttribute("enrollId");
		cashlessDetailVO = (CashlessDetailVO)FormUtils.getFormValues(frmCashlessMemberVitals,"frmCashlessMemberVitals",
				this,mapping,request);
		int iResult	=	0;
		iResult	= onlineAccessManager.saveMemberVitals(enrollId,cashlessDetailVO,userSecurityProfile.getUSER_SEQ_ID());
		if(iResult>0)
			request.setAttribute("updated","message.addedSuccessfully");
		
		//this code is to get the member and other details based on the enroll id
		onlineAccessManager	=	this.getOnlineAccessManagerObject();
		cashlessDetailVO	=	null;
		cashlessDetailVO	= onlineAccessManager.geMemberDetailsOnEnrollId((String)request.getSession().getAttribute("enrollId"),(String)request.getSession().getAttribute("benifitTypeVal"),new Long(0),new Long(0));
		frmCashlessMemberVitals = (DynaActionForm)FormUtils.setFormValues("frmCashlessMemberVitals",
				cashlessDetailVO,this,mapping,request);
		frmCashlessMemberVitals.set("enrollId", request.getSession().getAttribute("enrollId"));
		frmCashlessMemberVitals.set("providerName", userSecurityProfile.getHospitalName());
		request.setAttribute("frmCashlessMemberVitals", frmCashlessMemberVitals);

		return this.getForward("prescriptionDetails", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
		return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processExceptions(request, mapping, new TTKException(exp,strOnlineClaimList));
		}//end of catch(Exception exp)
		}//end of doSaveVitals(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
			
		
		
		
		/**
		 * 
		 * @param mapping The ActionMapping used to select this instance
		 * @param form The optional ActionForm bean for this request (if any)
		 * @param request The HTTP request we are processing
		 * @param response The HTTP response we are creating
		 * @return Selected labs
		 * @throws Exception, if any error occurs
		 */
		public ActionForward doLabsAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                HttpServletResponse response) throws Exception{
		try{
			log.info("Inside the doAddLabs method of OnlineCashlessHospAction");
			setOnlineLinks(request);
			
			DynaActionForm frmCashlessPrescription =(DynaActionForm)form;
			frmCashlessPrescription.initialize(mapping);     //reset the form data
			
			 OnlineAccessManager onlineAccessManager	=	null;
			 onlineAccessManager = this.getOnlineAccessManagerObject();
				
				
			request.getSession().setAttribute("frmCashlessPrescription", frmCashlessPrescription);
			return this.getForward(strAddLaboratories, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineClaimList));
		}//end of catch(Exception exp)
		}//end of doAddLabs(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
		
		
		
		//DownLoad Online PreAuth Form
		public ActionForward doPrintForms(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
			 try{
				 log.debug("Inside the doGeneratePreAuthLetter method of OnlineReportsAction");
				 setOnlineLinks(request);
				 JasperReport jasperReport,emptyReport;
				 JasperPrint jasperPrint;
				 TTKReportDataSource ttkReportDataSource = null;
				 ResultSet rs = null;
				 String jrxmlfile= null;	
				 UserSecurityProfile userSecurityProfile = (UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
				 ttkReportDataSource = new TTKReportDataSource("OnlinePreAuthForm",(String)request.getSession().getAttribute("enrollId"),(String)request.getSession().getAttribute("benifitTypeVal"),userSecurityProfile.getHospSeqId().toString());
				 

				OnlineAccessManager onlineAccessManager = null;
				onlineAccessManager = this.getOnlineAccessManagerObject();

				CashlessDetailVO cashlessDetailVO	=	new CashlessDetailVO();
				cashlessDetailVO	= onlineAccessManager.geMemberDetailsOnEnrollIdNew((String)request.getSession().getAttribute("enrollId"));

					
				 rs = ttkReportDataSource.getResultData();	
				 try
				 {
					 ByteArrayOutputStream boas=new ByteArrayOutputStream();
					 HashMap<String, Object> hashMap = new HashMap<String, Object>();
					 hashMap.put("MEM_GENDER", cashlessDetailVO.getGender());
					 hashMap.put("MEM_NAME", cashlessDetailVO.getMemberName());
					 hashMap.put("MEM_AGE", cashlessDetailVO.getAge());
					 hashMap.put("MEM_DOB", cashlessDetailVO.getMemDob());
					 hashMap.put("MEM_ENROLID", cashlessDetailVO.getEnrollId());
					 hashMap.put("MEM_POLICY", cashlessDetailVO.getPolicyNo());
					 hashMap.put("MEM_MONTHS", cashlessDetailVO.getEligibility());
					 hashMap.put("MEM_EMPLOYEE_NO", cashlessDetailVO.getEnrollmentID());
					 hashMap.put("COMPANY_NAME", cashlessDetailVO.getInsurredName());
					 hashMap.put("MOBILE", cashlessDetailVO.getModeType());
					 
					 
					 if(rs.next()){
							 jrxmlfile = "providerLogin/OnlinePreAuthForm.jrxml";
							 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
							 rs.previous();
							 jasperPrint = JasperFillManager.fillReport(jasperReport, hashMap,new TTKReportDataSource(rs));
					 }//if(rs.next())
					 else {
						 jrxmlfile = "generalreports/EmptyReprot.jrxml";
						 jasperReport = JasperCompileManager.compileReport(jrxmlfile);
						 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new JREmptyDataSource());
					 }//end of else
					 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);
					 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
					 request.setAttribute("boas",boas);
				 }//end of try
				 catch (JRException e)
				 {
					 e.printStackTrace();
				 }//end of catch(JRException e)
				 catch (Exception e)
				 {
					 e.printStackTrace();
				 }//end of catch (Exception e)
				 return (mapping.findForward(strReportdisplay));
			 }//end of try
			 catch(TTKException expTTK)
			 {
				 return this.processExceptions(request, mapping, expTTK);
			 }//end of catch(TTKException expTTK)
			 catch(Exception exp)
			 {
				 return this.processExceptions(request, mapping, new TTKException(exp,strReportExp));
			 }//end of catch(Exception exp)
		 }//end of doPrintForms(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
		 
		
		
	    /**
		 * This method builds all the search parameters to ArrayList and places them in session
		 * @param request The HTTP request we are processing
		 * @return alSearchParams ArrayList
		 */
		private ArrayList<Object> populateSearchCriteriaNew(HttpServletRequest request)
		{
			//build the column names along with their values to be searched
			ArrayList<Object> alSearchParams = new ArrayList<Object>();
			alSearchParams.add(TTKCommon.getWebBoardId(request));
			alSearchParams.add(TTKCommon.getUserSeqId(request));
			return alSearchParams;
		}//end of populateSearchCriteria(Long lngMemberSeqId)
		

		
		
		public ActionForward doVerifyCardDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                 HttpServletResponse response) throws Exception{
		try{
			log.info("Inside the doVerifyCardDetails method of OnlineCashlessHospAction");
			setOnlineLinks(request);
			HttpSession session=request.getSession();
			 OnlineAccessManager onlineAccessManager	=	null;
			 onlineAccessManager = this.getOnlineAccessManagerObject();
			
			
			String certsPath = null;
			 
		    String ef_idn_cn = request.getParameter("ef_idn_cn").replace(" ", "+");
		    String ef_non_mod_data = request.getParameter("ef_non_mod_data");
		    		if(ef_non_mod_data!=null)
		    			ef_non_mod_data	=	ef_non_mod_data.replace(" ", "+");
		    String ef_mod_data = request.getParameter("ef_mod_data");
		    	if(ef_mod_data!=null)
		    		ef_mod_data.replace(" ", "+");
		    String ef_sign_image = request.getParameter("ef_sign_image");
		    	if(ef_sign_image!=null)
		    		ef_sign_image.replace(" ", "+");
		    String ef_photo = request.getParameter("ef_photo");
		    		if(ef_photo!=null)
		    			ef_photo.replace(" ", "+");
		    String ef_root_cert = request.getParameter("ef_root_cert");
		    	if(ef_root_cert!=null)
		    		ef_root_cert.replace(" ", "+");
		    String ef_home_address = request.getParameter("ef_home_address");
		    	if(ef_home_address!=null)
		    		ef_home_address.replace(" ", "+");
		    String ef_work_address = request.getParameter("ef_work_address");
		    	if(ef_work_address!=null)
		    		ef_work_address.replace(" ", "+");
		    
		    certsPath = request.getRealPath("/data_signing_certs");
		    boolean nonMod = false;
		    boolean mod = false;
		    boolean signImage = false;
		    boolean photo = false;
		    boolean homeAddress = false;
		    boolean workAddress = false;
		    PublicDataParser parser = null;
		    try
		    {
		      parser = new PublicDataParser(ef_idn_cn, certsPath);
		      nonMod = parser.parseNonModifiableData(ef_non_mod_data);
		      mod = parser.parseModifiableData(ef_mod_data);
		      photo = parser.parsePhotography(ef_photo);
		      signImage = parser.parseSignatureImage(ef_sign_image);
		      homeAddress = parser.parseHomeAddressData(ef_home_address);
		      workAddress = parser.parseWorkAddressData(ef_work_address);
		      parser.parseRootCertificate(ef_root_cert);
		    } catch (Exception e) {
		      e.printStackTrace();
		      request.setAttribute("eligibleYN","There is some Problem while reading Card");
		      return this.getForward(strOnlineClaimList, mapping, request);
		     // response.sendRedirect("publicDataReport.jsp");
		    }
		    
		    request.getSession().setAttribute("NonMod", Boolean.valueOf(nonMod));
		    request.getSession().setAttribute("Mod", Boolean.valueOf(mod));
		    request.getSession().setAttribute("SignImage", "".equals(ef_sign_image) ? "N/A" : Boolean.valueOf(signImage));
		    request.getSession().setAttribute("Photo", "".equals(ef_photo) ? "N/A" : Boolean.valueOf(photo));
		    request.getSession().setAttribute("HomeAddress", "".equals(ef_home_address) ? "N/A" : Boolean.valueOf(homeAddress));
		    request.getSession().setAttribute("WorkAddress", "".equals(ef_work_address) ? "N/A" : Boolean.valueOf(workAddress));
		    

            
            String[] status=new String[2];
             status[0] = "N";
            
            if(parser.getIdNumber()!=null)
            {
            	status=onlineAccessManager.getEligibilityStatus(parser.getIdNumber());
            	
            }
		    //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		    //request.getSession().setAttribute("FullName", parser.getFullName());
		    request.getSession().setAttribute("IDN", parser.getIdNumber());
		    /*request.getSession().setAttribute("CardNumber", parser.getCardNumber());
		    request.getSession().setAttribute("Title", parser.getTitle());
		    request.getSession().setAttribute("Nationality", parser.getNationality());
		    request.getSession().setAttribute("IssueDate", dateFormat.format(parser.getIssueDate()));
		    request.getSession().setAttribute("ExpiryDate", dateFormat.format(parser.getExpiryDate()));
		    request.getSession().setAttribute("IdType", parser.getIdType());
		    request.getSession().setAttribute("Sex", parser.getSex());
		    request.getSession().setAttribute("DoB", dateFormat.format(parser.getDateOfBirth()));
		    request.getSession().setAttribute("FullName_ar", parser.getArabicFullName());
		    request.getSession().setAttribute("MaritalStatus", parser.getMaritalStatus());
		    request.getSession().setAttribute("Occupation", parser.getOccupation());
		    request.getSession().setAttribute("OccupationField", parser.getOccupationField() == null ? "" : parser.getOccupationField());
		    request.getSession().setAttribute("Title_ar", parser.getArabicTitle());
		    request.getSession().setAttribute("Nationality_ar", parser.getArabicNationality());
		    request.getSession().setAttribute("MotherName", parser.getMotherFullName() == null ? "" : parser.getMotherFullName());
		    request.getSession().setAttribute("MotherName_ar", parser.getMotherFullName_ar() == null ? "" : parser.getMotherFullName_ar());
		    request.getSession().setAttribute("FamilyId", parser.getFamilyID());
		    request.getSession().setAttribute("HusbandIDN", parser.getHusbandIDN());
		    request.getSession().setAttribute("SponsorType", parser.getSponsorType());
		    request.getSession().setAttribute("SponsorName", parser.getSponsorName());
		    request.getSession().setAttribute("SponsorUnifiedNumber", parser.getSponsorUnifiedNumber());
		    request.getSession().setAttribute("ResidencyType", parser.getResidencyType());
		    request.getSession().setAttribute("ResidencyNumber", parser.getResidencyNumber());
		    request.getSession().setAttribute("ResidencyExpiryDate", parser.getResidencyExpiryDate() == null ? "" : dateFormat.format(parser.getResidencyExpiryDate()));
		 */   

		    if (parser.getPhotography() != null)
		      request.getSession().setAttribute("PhotoBase64", new BASE64Encoder().encode(parser.getPhotography()));
		    if (parser.getHolderSignatureImage() != null) {
		      request.getSession().setAttribute("SignaturePhotoBase64", new BASE64Encoder().encode(parser.getHolderSignatureImage()));
		    }
		    //response.sendRedirect("publicDataReport.jsp");
		 
		    
		    DynaActionForm frmCashlessAdd=(DynaActionForm)session.getAttribute("frmCashlessAdd");
		    
		    String strmemseqid=status[1];
		    
		    Long longmemseqid = Long.parseLong(strmemseqid);
		    
		    frmCashlessAdd.set("lngMemberSeqID",longmemseqid);
			 
	  		session.setAttribute("frmCashlessAdd",frmCashlessAdd);
		    
		    
		    request.setAttribute("carNumber", parser.getIdNumber());
		 
		    
		 if(status[0]=="N") request.setAttribute("eligibleYN","Card Details Not Found Alternatively Check With VidalID");
		 request.setAttribute("statusYN",status[0]);
			return this.getForward(strOnlineClaimList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineClaimList));
			}//end of catch(Exception exp)
		}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
		
		
		
	public ActionForward onselectmember(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
			
			DynaActionForm frmCashlessPrescription = (DynaActionForm) form;
			HttpSession session = request.getSession(); 
			String reforward = request.getParameter("reforward");
			
				 
				
				TableData memberListData = new TableData(); 
				
				memberListData.createTableInfo("MemberListTable",
						new ArrayList());
				session.setAttribute("memberListData", memberListData);
				
				session.setAttribute("frmCashlessPrescription", frmCashlessPrescription);
			
				  return mapping.findForward("memberSearchList");
           
			 }
		
		
	 public ActionForward memberSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	   			HttpServletResponse response) throws Exception{
	           try{
	           log.debug("Inside OnlineCashlessHospAction memberSearch");
	                  setLinks(request);
	           DynaActionForm frmPreAuthList = (DynaActionForm) form;
	           String enrollmentId = (String) frmPreAuthList.getString("sEnrollmentId");
	           String emirateId = (String) frmPreAuthList.getString("sEmirateId");
	                  UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	                  Long lngHospSeqId =userSecurityProfile.getHospSeqId();
	                  
	                  
	           PreAuthManager preAuthObject=this.getPreAuthManagerObject();
	           TableData memberListData = null;
	              if((request.getSession()).getAttribute("memberListData") != null)
	               {
	           	   memberListData = (TableData)(request.getSession()).getAttribute("memberListData");
	               }//end of if((request.getSession()).getAttribute("icdListData") != null)
	            else
	             {
	           	 memberListData = new TableData();
	              }//end of else

	            String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
	            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));		
	            //if the page number or sort id is clicked
	         if(!strPageID.equals("") || !strSortID.equals(""))
	          {
	           if(strPageID.equals(""))
	            {
	           	memberListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
	           	memberListData.modifySearchData("sort");//modify the search data                    
	            }///end of if(!strPageID.equals(""))
	          else
	           {
	                   log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
	                   memberListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
	                 return this.getForward(strMemberList, mapping, request);
	              }//end of else
	           }//end of if(!strPageID.equals("") || !strSortID.equals(""))
	       else{
	       	memberListData.createTableInfo("MemberListTable",null);
	       	memberListData.setSearchData(this.populateMembersSearchCriteria((DynaActionForm)form,request));
	       	memberListData.modifySearchData("search");				
	      }//end of else

	        if(enrollmentId.trim().length()<1 && emirateId.trim().length()<1)
 			{	
 				memberListData.createTableInfo("MemberListTable",null);
 				TTKException expTTK = new TTKException();
 				expTTK.setMessage("error.wrong.memberId.or.emirateId");
				throw expTTK;
 			}
	      ArrayList alMemberList=null;
	      alMemberList= preAuthObject.getProviderMemberList(memberListData.getSearchData(),lngHospSeqId);
	      int count = 0;
	      count++;
	      request.getSession().setAttribute("count",count);
	      
	      
	      
	      memberListData.setData(alMemberList, "search");
	      //set the table data object to session
	      
	      
	      request.getSession().setAttribute("memberListData",memberListData);
	      return this.getForward(strMemberList, mapping, request);
	      }//end of try
	      catch(TTKException expTTK)
	      {
	          return this.processExceptions(request, mapping, expTTK);
	       }//end of catch(TTKException expTTK)
	       catch(Exception exp)
	       {
	         return this.processExceptions(request, mapping, new TTKException(exp,strMemberList));
	        }//end of catch(Exception exp)
	      }
	
	
	
	
	 private ArrayList<Object> populateMembersSearchCriteria(DynaActionForm frmPreAuthList,HttpServletRequest request)
	  	{
	  		//build the column names along with their values to be searched
	  		ArrayList<Object> alSearchParams = new ArrayList<Object>();
	  		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sEnrollmentId")));
	  		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmPreAuthList.getString("sEmirateId")));
	          alSearchParams.add(TTKCommon.getUserSeqId(request));
	  		return alSearchParams;
	  	}//end of populateActivityCodeSearchCriteria(DynaActionForm frmProductList,HttpServletRequest request)

	
	
	
	
	 private PreAuthManager getPreAuthManagerObject() throws TTKException
		{
			PreAuthManager preAuthManager = null;
			try
			{
				if(preAuthManager == null)
				{
					InitialContext ctx = new InitialContext();
					preAuthManager = (PreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthManagerBean!com.ttk.business.preauth.PreAuthManager");
				}//end if
			}//end of try
			catch(Exception exp)
			{
				throw new TTKException(exp, strClaimSearchError);
			}//end of catch
			return preAuthManager;
		}//end getPreAuthManagerObject()
	
	
	
	
	
	
	
	 public ActionForward doMemberBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		        HttpServletResponse response) throws Exception{
try{
 log.debug("Inside ClaimAction doMemberBackward");
 setLinks(request);
 UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
 Long lngHospSeqId =userSecurityProfile.getHospSeqId();
 TableData memberListData = (TableData)request.getSession().getAttribute("memberListData");
 PreAuthManager preAuthObject=this.getPreAuthManagerObject();
 memberListData.modifySearchData(strBackward);//modify the search data
 ArrayList alMemberList = preAuthObject.getProviderMemberList(memberListData.getSearchData(),lngHospSeqId);
 memberListData.setData(alMemberList, strBackward);//set the table data
  request.getSession().setAttribute("memberListData",memberListData);   //set the table data object to session
   return this.getForward(strMemberList, mapping, request);   //finally return to the grid screen
 }//end of try
catch(TTKException expTTK)
{
   return this.processExceptions(request, mapping, expTTK);
 }//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strMemberList));
}//end of catch(Exception exp)
}
 
 
 
	public ActionForward doMemberForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
try{
          log.debug("Inside ClaimAction doMeberForward");
          setLinks(request);
          UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
          Long lngHospSeqId =userSecurityProfile.getHospSeqId();
          TableData memberListData = (TableData)request.getSession().getAttribute("memberListData");
          PreAuthManager preAuthObject=this.getPreAuthManagerObject();
          memberListData.modifySearchData(strForward);//modify the search data
          ArrayList alMemberList = preAuthObject.getProviderMemberList(memberListData.getSearchData(),lngHospSeqId);
          memberListData.setData(alMemberList, strForward);//set the table data
        request.getSession().setAttribute("memberListData",memberListData);   //set the table data object to session
        return this.getForward(strMemberList, mapping, request);   //finally return to the grid screen
        }//end of try
     catch(TTKException expTTK)
         {
          return this.processExceptions(request, mapping, expTTK);
          }//end of catch(TTKException expTTK)
      catch(Exception exp)
       {
          return this.processExceptions(request, mapping, new TTKException(exp,strMemberList));
       }//end of catch(Exception exp)
     }
 
	
		
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
	try{
	log.debug("Inside ClaimsAction closeClinicians");
	
	String strActTab=TTKCommon.getActiveTab(request);
	
	return mapping.findForward(strOnlineClaimList);
	
	}//end of try
	catch(Exception exp)
	{
	return this.processExceptions(request, mapping, new TTKException(exp,strOnlineClaimList));
	}//end of catch(Exception exp)
	}	
		
		
	public ActionForward doSelectMemberId(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
		  	setLinks(request);
		  	log.debug("Inside ClaimGenealAction doSelectMemberId ");
		  	HttpSession session=request.getSession();
		  	PreAuthManager preAuthObject=this.getPreAuthManagerObject();
		  	TableData memberListData = (TableData)session.getAttribute("memberListData");

		  	if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		  	{
		  		
		  		MemberDetailVO MemberDetailVO=(MemberDetailVO)memberListData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
		  		
		  	DynaActionForm frmCashlessAdd=(DynaActionForm)session.getAttribute("frmCashlessAdd");
		  	Long memseqid= MemberDetailVO.getMemberSeqID();
		  	frmCashlessAdd.set("enrollId", MemberDetailVO.getMemberId());
		  	frmCashlessAdd.set("lngMemberSeqID",memseqid);
		 
		  		session.setAttribute("frmCashlessAdd",frmCashlessAdd);
		  		
		  		
		  	}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		  	return this.getForward(strOnlineClaimList, mapping, request);
		  	}//end of try
		  	catch(TTKException expTTK)
		  	{
		  	return this.processExceptions(request, mapping, expTTK);
		  	}//end of catch(TTKException expTTK)
		  	catch(Exception exp)
		  	{
		  	return this.processExceptions(request, mapping, new TTKException(exp,strMemberList));
		  	}
	}
    
    
    
	
	
	
	
	
	
	
	
	
	
	
		
		
}//end of OnlineClmSearchHospAction
