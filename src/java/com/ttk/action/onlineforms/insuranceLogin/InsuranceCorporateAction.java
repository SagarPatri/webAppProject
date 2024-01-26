package com.ttk.action.onlineforms.insuranceLogin;
/**
 * @ (#) InsuranceCorporateAction.java 21 Aug 2015
 * Project      : TTK HealthCare Services
 * File         : InsuranceCorporateAction.java
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 21 Aug 2015
 *
 * @author       :  Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.Column;
import com.ttk.action.table.TableData;
import com.ttk.business.enrollment.PolicyManager;
import com.ttk.business.onlineforms.insuranceLogin.InsuranceCorporateManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.impl.reports.TTKReportDataSource;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.onlineforms.insuranceLogin.AuthDetailsVO;
import com.ttk.dto.onlineforms.insuranceLogin.AuthorizationVO;
import com.ttk.dto.onlineforms.insuranceLogin.ClaimDetailsVO;
import com.ttk.dto.onlineforms.insuranceLogin.ClaimsVO;
import com.ttk.dto.onlineforms.insuranceLogin.DashBoardVO;
import com.ttk.dto.onlineforms.insuranceLogin.EnrollMemberVO;
import com.ttk.dto.onlineforms.insuranceLogin.InsCorporateVO;
import com.ttk.dto.onlineforms.insuranceLogin.InsGlobalViewVO;
import com.ttk.dto.onlineforms.insuranceLogin.PolicyDetailVO;
import com.ttk.dto.onlineforms.insuranceLogin.ProductTableVO;
import com.ttk.dto.onlineforms.insuranceLogin.RetailVO;
import com.ttk.dto.usermanagement.PersonalInfoVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for Searching the List Policies to see the Online Account Info.
 * This also provides deletion and updation of products.
 */
public class InsuranceCorporateAction extends TTKAction {

    private static final Logger log = Logger.getLogger( InsuranceCorporateAction.class );


    // Action mapping forwards.
    private static final String strOnlinePolicyList="onlinepolicylist";
    
    //  Modes.
   private static final String strBackward="Backward";
   private static final String strForward="Forward";
   private static final String strOnlineInsDashBoard="insDashBoard";
   private static final String strOnlineAccInfo="onlineaccountinfo";
   private static final String strViewClaimDetails="viewClaimDetails";
   private static final String strViewAuthDetails="viewAuthDetails";
   private static final String strViewFocusedDetails="focusedViewProceed";
   private static final String strReportdisplay="reportdisplay";
   
   
   /**
    * This method is used to SHOW THE INSURANCE DASH BOARD.
    * Finally it forwards to the appropriate view based on the specified forward mappings
    *
    * @param mapping The ActionMapping used to select this instance
    * @param form The optional ActionForm bean for this request (if any)
    * @param request The HTTP request we are processing
    * @param response The HTTP response we are creating
    * @return ActionForward Where the control will be forwarded, after this request is processed
    * @throws Exception if any error occurs
    */
   public ActionForward doDashBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                    HttpServletResponse response) throws Exception{
       try{
           log.debug("Inside the doDashBoard method of InsuranceCorporateAction");
           setOnlineLinks(request);

           DynaActionForm frmInsDashBoard =(DynaActionForm)form;
           frmInsDashBoard.initialize(mapping);     //reset the form data
           //((DynaActionForm)form).initialize(mapping);//reset the form data
           
           InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
           DashBoardVO dashBoardVO	=	null;
           dashBoardVO	=	insuranceCorporateManager.getDashBoardDetails((String)request.getSession().getAttribute("insCompCodeWebLogin"));
           
           request.setAttribute("topProvs", dashBoardVO.getTopProvNames());
           frmInsDashBoard = (DynaActionForm)FormUtils.setFormValues("frmInsDashBoard",
        		   dashBoardVO,this,mapping,request);
           request.getSession().setAttribute("frmInsDashBoard", frmInsDashBoard);
           
           return this.getForward(strOnlineInsDashBoard, mapping, request);
       }//end of try
       catch(TTKException expTTK)
       {
           return this.processOnlineExceptions(request, mapping, expTTK);
       }//end of catch(TTKException expTTK)
       catch(Exception exp)
       {
           return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
       }//end of catch(Exception exp)
   }//end of doDashBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
           //HttpServletResponse response)
   
   
   
    public ActionForward doCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		            HttpServletResponse response) throws Exception{
			try{
				log.debug("Inside the doCorporate method of InsuranceCorporateAction");
				setOnlineLinks(request);
				
				DynaActionForm frmInsCorporate =(DynaActionForm)form;
				frmInsCorporate.initialize(mapping);     //reset the form data
				
				TableData tableData=TTKCommon.getTableData(request);
				tableData.createTableInfo("CorporateDataTable",null);//CorporateDataTable
				request.getSession().setAttribute("tableDataCorporateData",tableData);
				request.getSession().setAttribute("insCorporateSeqId",null);//setting to Null B'Coz this seq Id using as input parameter for search Enrollments  
				
		       return this.getForward("insLoginCorp", mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processOnlineExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
			}//end of catch(Exception exp)
    }//end of doDashBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    public ActionForward doCorporateProceed(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.debug("Inside the doCorporateProceed method of InsuranceCorporateAction");
            setOnlineLinks(request);

            DynaActionForm frmInsCorporate =(DynaActionForm)form;
            ArrayList<Object> alOnlineAccList = null;
            TableData tableData=null;
            String strPageID =""; 
			String strSortID ="";	
            InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
			tableData= (TableData) request.getSession().getAttribute("tableDataCorporateData");

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
					return (mapping.findForward("insLoginCorp"));
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
				tableData.createTableInfo("CorporateDataTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,"COR"));						
				tableData.modifySearchData("search");
				//sorting based on investSeqID in descending order													
			}//end of else
			alOnlineAccList= insuranceCorporateManager.getEnrollMemberData(tableData.getSearchData(),"COR");
			tableData.setData(alOnlineAccList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableDataCorporateData",tableData);
			
            return this.getForward("insLoginCorp", mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
        }//end of catch(Exception exp)
    }//end of doCorporateProceed(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
    
    /*
     * Corporate Focused View Default
     */
    
    public ActionForward doCorpFocusedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
	try{
		log.debug("Inside the doCorpFocusedView method of InsuranceCorporateAction");
		setOnlineLinks(request);
		
		request.setAttribute("modeType", "CLM");
		InsCorporateVO insCorporateVO	=	new InsCorporateVO();
		EnrollMemberVO enrollMemberVO	=	null;
		InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();

		DynaActionForm generalForm = (DynaActionForm)form;
		TableData tableData = (TableData) request.getSession().getAttribute("tableDataMemberData");
	
		TableData tableData_Claims = new TableData();
		String strPageID = "";
		String strSortID = "";
		if(!strPageID.equals("") || !strSortID.equals(""))
		{
			if(!strPageID.equals(""))
			{
				tableData_Claims.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return (mapping.findForward("onlineaccountinfo"));
			}///end of if(!strPageID.equals(""))
			else
			{
				tableData_Claims.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableData_Claims.modifySearchData("sort");//modify the search data
			}//end of else
		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
		else
		{
			// create the required grid table
			tableData_Claims.createTableInfo("FocusedViewClaimsTable",null);
			//fetch the data from the data access layer and set the data to table object
			tableData.setSearchData(new ArrayList<>());						
			tableData_Claims.modifySearchData("search");
		}//end of else
		
		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
        {
			enrollMemberVO = (EnrollMemberVO)tableData.getRowInfo(Integer.parseInt((String)(generalForm).get("rownum")));
            //add the selected item to the web board and make it as default selected
            //calling business layer to get the hospital detail
			insCorporateVO = insuranceCorporateManager.getCorpFocusMemberDetails(enrollMemberVO.getEnrollmentId(),tableData_Claims.getSearchData(),"COR");
			request.getSession().setAttribute("insCorporateVO", insCorporateVO);
			request.getSession().setAttribute("insEnrollmentId", enrollMemberVO.getEnrollmentId());//insEnrollmentId
            generalForm.initialize(mapping);
        }//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		//if(alClaimsList!=null)
		
		//create Claims Table
		ArrayList<ClaimsVO> alClaimsList	=	insCorporateVO.getClaimDetails();
		
		tableData_Claims.setData(alClaimsList, "search");
		request.getSession().setAttribute("tableData_Claims",tableData_Claims);
		
		//create blank Authorization table
		TableData tableData_Authorizations = new TableData();
		/*tableData_Authorizations.createTableInfo("FocusedViewAuthorizationsTable",null);
		tableData_Authorizations.setData(new ArrayList<>(), "search");*/
		
		String insEnrollmentId	=	(String)request.getSession().getAttribute("insEnrollmentId");
		if(!strPageID.equals("") || !strSortID.equals(""))
		{
			if(!strPageID.equals(""))
			{
				tableData_Authorizations.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return (mapping.findForward("focusedViewProceed"));
			}///end of if(!strPageID.equals(""))
			else
			{
				tableData_Authorizations.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableData_Authorizations.modifySearchData("sort");//modify the search data
			}//end of else
		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
		else
		{
			// create the required grid table
			tableData_Authorizations.createTableInfo("FocusedViewAuthorizationsTable",null);
			//fetch the data from the data access layer and set the data to table object
			tableData_Authorizations.setSearchData(new ArrayList<>());
			tableData_Authorizations.modifySearchData("search");
		}//end of else

		ArrayList<AuthorizationVO> alAuthorizationsList	=	(ArrayList<AuthorizationVO>)insuranceCorporateManager.getCorpFocusAuthorizationDetails(insEnrollmentId,tableData_Authorizations.getSearchData());
		tableData_Authorizations.setData(alAuthorizationsList, "search");
		
		request.getSession().setAttribute("tableData_Authorizations",tableData_Authorizations);
		request.getSession().setAttribute("enrollmentId", enrollMemberVO.getEnrollmentId());
		
		DynaActionForm frmInsCorporate = setFormValues(insCorporateVO,mapping,request);//Set form values to other tables
		
		request.getSession().setAttribute("frmInsCorporate",frmInsCorporate);

		return this.getForward(strViewFocusedDetails, mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
	}//end of catch(Exception exp)
}//end of doCorpFocusedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
    
    
    
    /**
     * This method takes u to screen where you select Global and Focused View
     */
    
    public ActionForward doCorpGlobalFocusedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
	try{
		log.debug("Inside the doCorpGlobalFocusedView method of InsuranceCorporateAction");
		setOnlineLinks(request);
		
		EnrollMemberVO enrollMemberVO	=	null;

		DynaActionForm generalForm = (DynaActionForm)form;
		TableData tableData = (TableData) request.getSession().getAttribute("tableDataCorporateData");
		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
        {
			enrollMemberVO = (EnrollMemberVO)tableData.getRowInfo(Integer.parseInt((String)(generalForm).get("rownum")));
            //add the selected item to the web board and make it as default selected
            //calling business layer to get the hospital detail
			request.getSession().setAttribute("insCorporateSeqId", enrollMemberVO.getGroupId());//insCorporateSeqId
            generalForm.initialize(mapping);
        }//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		//if(alClaimsList!=null)
		
		return mapping.findForward("globalfocusedView");
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		System.out.println("ErrorL:L"+exp);
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
	}//end of catch(Exception exp)
}//end of doCorpGlobalFocusedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
    
    
    
    public ActionForward doFocusedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		            HttpServletResponse response) throws Exception{
		try{
		log.debug("Inside the doFocusedView method of InsuranceCorporateAction");
		setOnlineLinks(request);
		
		DynaActionForm frmInsRetail =(DynaActionForm)form;
		frmInsRetail.initialize(mapping);     //reset the form data
		
		TableData tableData=new TableData();
		tableData.createTableInfo("MemberDataTable",null);
		request.getSession().setAttribute("tableDataMemberData",tableData);
		request.getSession().setAttribute("frmInsRetail",frmInsRetail);
		frmInsRetail.set("caption", "Corporate > Focused View");
		 return this.getForward("focusedView", mapping, request);
		
		}//end of try
		catch(TTKException expTTK)
			{
			return this.processOnlineExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
		}//end of catch(Exception exp)
    }//end of doFocusedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
    
    
    
    
    /**
     * GLobal view click on the page
     */
    
	    public ActionForward doGlobalView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            HttpServletResponse response) throws Exception{
		try{
		log.debug("Inside the doGlobalView method of InsuranceCorporateAction");
		setOnlineLinks(request);
		
		DynaActionForm frmInsCorporateFocused =(DynaActionForm)form;
		frmInsCorporateFocused.initialize(mapping);
		
		InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
		
		InsGlobalViewVO insGlobalViewVO	=	new  InsGlobalViewVO();
		String insCompCodeWebLogin	=	(String) request.getSession().getAttribute("insCompCodeWebLogin");
		insGlobalViewVO = insuranceCorporateManager.getCorpGlobalDetails(insCompCodeWebLogin,"COR",(String)request.getSession().getAttribute("insCorporateSeqId"));
		request.getSession().setAttribute("insGlobalViewVO", insGlobalViewVO);
		
		frmInsCorporateFocused = setFormValuesForGlobalView(insGlobalViewVO,mapping,request);//Set form values to other tables\

		frmInsCorporateFocused.set("caption", "Corporate > Global View");
		
		request.getSession().setAttribute("frmInsCorporateFocused",frmInsCorporateFocused);
		request.setAttribute("CorOrRet","CORP");
		 return this.getForward("insCorpGlobal", mapping, request);
		
		}//end of try
	catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
	catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
		}//end of catch(Exception exp)
	}//end of doGlobalView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
    
	    
    
    
    public ActionForward doGlobalProceed(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		            HttpServletResponse response) throws Exception{
		try{
		log.debug("Inside the doGlobalProceed method of InsuranceCorporateAction");
		setOnlineLinks(request);
		 
		DynaActionForm frmOnlineAccountInfo =(DynaActionForm)form;
		frmOnlineAccountInfo.initialize(mapping);//reset the form data
		//((DynaActionForm)form).initialize(mapping);//reset the form data
		// return this.getForward("insLoginCorp", mapping, request);
		return mapping.findForward("globalView");
		}//end of try
		catch(TTKException expTTK)
			{
			return this.processOnlineExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
		}//end of catch(Exception exp)
    }//end of doGlobalProceed(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)

    
    /*
     * Focus View only Authorizations
     */
    
    public ActionForward doFocusViewAuthorizations(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
	try{
		log.debug("Inside the doFocusViewAuthorizations method of InsuranceCorporateAction");
		setOnlineLinks(request);
		
		request.setAttribute("modeType", "PAT");
        InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();

		String insEnrollmentId	=	(String)request.getSession().getAttribute("insEnrollmentId");
		
		//create Authorization Table
		TableData tableData_Authorizations = (TableData) request.getSession().getAttribute("tableData_Authorizations");
		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
		if(!strPageID.equals("") || !strSortID.equals(""))
		{
			if(!strPageID.equals(""))
			{
				tableData_Authorizations.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return (mapping.findForward("focusedViewProceed"));
			}///end of if(!strPageID.equals(""))
			else
			{
				tableData_Authorizations.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableData_Authorizations.modifySearchData("sort");//modify the search data
			}//end of else
		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
		else
		{
			// create the required grid table
			tableData_Authorizations.createTableInfo("FocusedViewAuthorizationsTable",null);
			//fetch the data from the data access layer and set the data to table object
			tableData_Authorizations.setSearchData(new ArrayList<>());
			tableData_Authorizations.modifySearchData("search");
		}//end of else

		ArrayList<AuthorizationVO> alAuthorizationsList	=	(ArrayList<AuthorizationVO>)insuranceCorporateManager.getCorpFocusAuthorizationDetails(insEnrollmentId,tableData_Authorizations.getSearchData());
		tableData_Authorizations.setData(alAuthorizationsList, "search");
		request.getSession().setAttribute("tableData_Authorizations",tableData_Authorizations);
		
		TableData tableData_Claims = new TableData();
		tableData_Claims.createTableInfo("FocusedViewClaimsTable",null);//create blank Claims table
		request.getSession().setAttribute("tableData_Claims",tableData_Claims);
		
		DynaActionForm frmInsCorporate = (DynaActionForm)request.getSession().getAttribute("frmInsCorporate");

		request.getSession().setAttribute("frmInsCorporate",frmInsCorporate);
		return mapping.findForward("focusedViewProceed");
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		System.out.println("ErrorL:L"+exp);
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
	}//end of catch(Exception exp)
}//end of doFocusViewAuthorizations(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
    
    
    
    /*
     * Focus View only Claims
     */
    
    public ActionForward doFocusViewClaims(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
	try{
		log.debug("Inside the doFocusViewClaims method of InsuranceCorporateAction");
		setOnlineLinks(request);
		
		request.setAttribute("modeType", "CLM");
        InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();

		String insEnrollmentId	=	(String)request.getSession().getAttribute("insEnrollmentId");
		//if(alClaimsList!=null)
		
		//create Authorization Table
		TableData tableData_Claims = (TableData) request.getSession().getAttribute("tableData_Claims");
		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
		if(!strPageID.equals("") || !strSortID.equals(""))
		{
			if(!strPageID.equals(""))
			{
				tableData_Claims.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
				return (mapping.findForward("focusedViewProceed"));
			}///end of if(!strPageID.equals(""))
			else
			{
				tableData_Claims.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
				tableData_Claims.modifySearchData("sort");//modify the search data
			}//end of else
		}//end of if(!strPageID.equals("") || !strSortID.equals(""))
		else
		{
			// create the required grid table
			tableData_Claims.createTableInfo("FocusedViewClaimsTable",null);
			//fetch the data from the data access layer and set the data to table object
			//tableData_Claims.setSearchData(new ArrayList<>());
			tableData_Claims.modifySearchData("search");
		}//end of else

		ArrayList<ClaimsVO> alAuthorizationsList	=	(ArrayList<ClaimsVO>)insuranceCorporateManager.getCorpFocusClaimsDetails(insEnrollmentId,tableData_Claims.getSearchData());
		tableData_Claims.setData(alAuthorizationsList, "search");
		request.getSession().setAttribute("tableData_Claims",tableData_Claims);
		
		//create blank Authorization table
		TableData tableData_Authorizations = new TableData();
		tableData_Authorizations.createTableInfo("FocusedViewAuthorizationsTable",null);
		request.getSession().setAttribute("tableData_Authorizations",tableData_Authorizations);
		
		DynaActionForm frmInsCorporate = (DynaActionForm)request.getSession().getAttribute("frmInsCorporate");
		request.getSession().setAttribute("frmInsCorporate",frmInsCorporate);
		return mapping.findForward("focusedViewProceed");
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
	}//end of catch(Exception exp)
}//end of doFocusViewClaims(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
    
    
    
    
    public ActionForward doViewClaim(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		            HttpServletResponse response) throws Exception{
			try{
			log.debug("Inside the doViewClaim method of InsuranceCorporateAction");
			setOnlineLinks(request);
			
			DynaActionForm frmClaimsView =(DynaActionForm)form;
			//((DynaActionForm)form).initialize(mapping);//reset the form data
			
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
			
			TableData tableData = (TableData) request.getSession().getAttribute("tableData_Claims");
			ClaimsVO claimsVO	=	null;
			ClaimDetailsVO claimDetailsVO	=	null;
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	        {
				claimsVO	=	 (ClaimsVO)tableData.getRowInfo(Integer.parseInt((String)(frmClaimsView).get("rownum")));
	            //calling business layer to get the hospital detail
				claimDetailsVO = insuranceCorporateManager.getClaimDetails(claimsVO.getClaimNbr());
	        }//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			
			frmClaimsView = (DynaActionForm)FormUtils.setFormValues("frmClaimsView",
					claimDetailsVO,this,mapping,request);
			request.getSession().setAttribute("frmClaimsView", frmClaimsView);
			
			return this.getForward(strViewClaimDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
			{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
			{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
		}//end of catch(Exception exp)
}//end of doViewClaim(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
    
    
    
    public ActionForward doViewAuths(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
	try{
	log.debug("Inside the doViewAuths method of InsuranceCorporateAction");
	setOnlineLinks(request);
	
	DynaActionForm frmAuthView =(DynaActionForm)form;
	//((DynaActionForm)form).initialize(mapping);//reset the form data
	
	InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
	
	TableData tableData = (TableData) request.getSession().getAttribute("tableData_Authorizations");
	AuthorizationVO authorizationVO	=	null;
	AuthDetailsVO authDetailsVO=	null;
	if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    {
		authorizationVO	=	 (AuthorizationVO)tableData.getRowInfo(Integer.parseInt((String)(frmAuthView).get("rownum")));
        //calling business layer to get the hospital detail
		authDetailsVO = insuranceCorporateManager.getAuthDetails(authorizationVO.getAuthNo());
    }//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	
	frmAuthView = (DynaActionForm)FormUtils.setFormValues("frmAuthView",
			authDetailsVO,this,mapping,request);
	frmAuthView.set("preAuthNumber", authorizationVO.getAuthNo());
	request.getSession().setAttribute("frmAuthView", frmAuthView);
	
	return this.getForward(strViewAuthDetails, mapping, request);
}//end of try
catch(TTKException expTTK)
	{
	return this.processOnlineExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
	{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
}//end of catch(Exception exp)
}//end of doViewAuths(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
    
    
    
    /**
     * Do Back To Focused Proceed
     */
    
    public ActionForward doBackClmAndAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
    	log.debug("Inside the doBackClmAndAuth method of InsuranceCorporateAction");
        setOnlineLinks(request);
        String modeType	=	request.getParameter("modeType");
        if("CLM".equals(modeType))
        	request.setAttribute("modeType", "CLM");
        else if("PAT".equals(modeType))
        	request.setAttribute("modeType", "PAT");

		return mapping.findForward("focusedViewProceed");        
    
    }
    
    /**
     * Do Back To Focused Proceed
     */
    public ActionForward doRetailBackClmAndAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
    	log.debug("Inside the doRetailBackClmAndAuth method of InsuranceCorporateAction");
        setOnlineLinks(request);
        String modeType	=	request.getParameter("modeType");
        if("CLM".equals(modeType))
        	request.setAttribute("modeType", "CLM");
        else if("PAT".equals(modeType))
        	request.setAttribute("modeType", "PAT");

		return mapping.findForward("retailFocusedViewProceed");        
    
    }
    
    
    /**
     * DO Focused View Proceed - mEMBER DATA - ENROLLMENT TABLE
     */
    public ActionForward doFocusedProceed(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			            HttpServletResponse response) throws Exception{
			try{
			log.debug("Inside the doFocusedProceed method of InsuranceCorporateAction");
			setOnlineLinks(request);
			
			ArrayList<Object> alOnlineAccList = null;
			TableData tableData=null;
			String strPageID =""; 
			String strSortID ="";	
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
			//tableData=TTKCommon.getTableData(request);
			tableData=(TableData) request.getSession().getAttribute("tableDataMemberData");
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
				return (mapping.findForward("focusedView"));
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
				tableData.createTableInfo("MemberDataTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,"MEM"));						
				tableData.modifySearchData("search");
				//sorting based on investSeqID in descending order													
				}//end of else
			alOnlineAccList= insuranceCorporateManager.getEnrollMemberData(tableData.getSearchData(),"MEM");
			tableData.setData(alOnlineAccList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableDataMemberData",tableData);
			
			return this.getForward("focusedView", mapping, request);
			}//end of try
			catch(TTKException expTTK)
				{
				return this.processOnlineExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
			catch(Exception exp)
				{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
				}//end of catch(Exception exp)
    }//end of doFocusedProceed(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    //HttpServletResponse response)
    
    
    
    /**
     * DO Focused View Proceed Details on Edit of memeber table
     */
    
    public ActionForward doCorpFocusedDetailsView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		            HttpServletResponse response) throws Exception{
			try{
			log.debug("Inside the doCorpFocusedDetailsView method of InsuranceCorporateAction");
			setOnlineLinks(request);
			
			DynaActionForm frmFocusedDetails =(DynaActionForm)form;
			//((DynaActionForm)form).initialize(mapping);//reset the form data
			
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
			
			TableData tableData = TTKCommon.getTableData(request);
			ClaimsVO claimsVO	=	null;
			ClaimDetailsVO claimDetailsVO	=	null;
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		    {
				claimsVO	=	 (ClaimsVO)tableData.getRowInfo(Integer.parseInt((String)(frmFocusedDetails).get("rownum")));
		        //calling business layer to get the hospital detail
				claimDetailsVO = insuranceCorporateManager.getClaimDetails(claimsVO.getClaimNbr());
		    }//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			
			frmFocusedDetails = (DynaActionForm)FormUtils.setFormValues("frmClaimsView",
					claimDetailsVO,this,mapping,request);
			request.getSession().setAttribute("frmFocusedDetails", frmFocusedDetails);
			
			return this.getForward(strViewClaimDetails, mapping, request);
			}//end of try
			catch(TTKException expTTK)
				{
				return this.processOnlineExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
				{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
			}//end of catch(Exception exp)
    }//end of doCorpFocusedDetailsView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
    
    public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside the doForward method of InsuranceCorporateAction");
			TableData tableData = TTKCommon.getTableData(request);
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
            
    		tableData.modifySearchData(strForward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alHospital = insuranceCorporateManager.getEnrollMemberData(tableData.getSearchData(),"COR");
    		tableData.setData(alHospital, strForward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
    		TTKCommon.documentViewer(request);
    		return this.getForward("insLoginCorp", mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    
    
    public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside the doBackward method of InsuranceCorporateAction");
			TableData tableData = TTKCommon.getTableData(request);
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alHospital = insuranceCorporateManager.getEnrollMemberData(tableData.getSearchData(),"COR");
    		tableData.setData(alHospital, strBackward);//set the table data
    		request.getSession().setAttribute("tableData",tableData);//set the table data object to session
    		TTKCommon.documentViewer(request);
    		return this.getForward("insLoginCorp", mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
    }//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    
    
    public ActionForward doForwardFocus(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside the doForwardFocus method of InsuranceCorporateAction");
			TableData tableData = (TableData) request.getSession().getAttribute("tableDataMemberData");
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
            
    		tableData.modifySearchData(strForward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alHospital = insuranceCorporateManager.getEnrollMemberData(tableData.getSearchData(),"MEM");
    		tableData.setData(alHospital, strForward);//set the table data
    		request.getSession().setAttribute("tableDataMemberData",tableData);//set the table data object to session
    		TTKCommon.documentViewer(request);
    		return this.getForward("focusedView", mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
    }//end of doForwardFocus(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    
    
    public ActionForward doBackwardFocus(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside the doBackwardFocus method of InsuranceCorporateAction");
			TableData tableData = (TableData) request.getSession().getAttribute("tableDataMemberData");
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alHospital = insuranceCorporateManager.getEnrollMemberData(tableData.getSearchData(),"MEM");
    		tableData.setData(alHospital, strBackward);//set the table data
    		request.getSession().setAttribute("tableDataMemberData",tableData);//set the table data object to session
    		TTKCommon.documentViewer(request);
    		return this.getForward("focusedView", mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
    }//end of doBackwardFocus(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    
    
    public ActionForward doForwardCorporates(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside the doForwardCorporates method of InsuranceCorporateAction");
			TableData tableData = (TableData) request.getSession().getAttribute("tableDataCorporateData");
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
            
    		tableData.modifySearchData(strForward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alHospital = insuranceCorporateManager.getEnrollMemberData(tableData.getSearchData(),"COR");
    		tableData.setData(alHospital, strForward);//set the table data
    		request.getSession().setAttribute("tableDataCorporateData",tableData);//set the table data object to session
    		TTKCommon.documentViewer(request);
    		return this.getForward("insLoginCorp", mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
    }//end of doForwardCorporates(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    
    
    public ActionForward doBackwardCorporates(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside the doBackwardCorporates method of InsuranceCorporateAction");
			TableData tableData = (TableData) request.getSession().getAttribute("tableDataCorporateData");
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		ArrayList alHospital = insuranceCorporateManager.getEnrollMemberData(tableData.getSearchData(),"COR");
    		tableData.setData(alHospital, strBackward);//set the table data
    		request.getSession().setAttribute("tableDataCorporateData",tableData);//set the table data object to session
    		TTKCommon.documentViewer(request);
    		return this.getForward("insLoginCorp", mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
    }//end of doBackwardCorporates(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    
    
    
    public ActionForward doForwardFocusClaims(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		request.setAttribute("modeType", "CLM");
    		setLinks(request);
    		log.debug("Inside the doForwardFocusClaims method of InsuranceCorporateAction");
			TableData tableData = (TableData) request.getSession().getAttribute("tableData_Claims");
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
            
    		tableData.modifySearchData(strForward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		String insEnrollmentId	=	(String)request.getSession().getAttribute("insEnrollmentId");
    		ArrayList<ClaimsVO> alAuthorizationsList	=	(ArrayList<ClaimsVO>)insuranceCorporateManager.getCorpFocusClaimsDetails(insEnrollmentId,tableData.getSearchData());
    		tableData.setData(alAuthorizationsList, strForward);//set the table data
    		request.getSession().setAttribute("tableData_Claims",tableData);//set the table data object to session
    		TTKCommon.documentViewer(request);
    		return this.getForward("focusedViewProceed", mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
    }//end of doForwardFocusClaims(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    
    
    public ActionForward doBackwardFocusClaims(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		request.setAttribute("modeType", "CLM");
    		setLinks(request);
    		log.debug("Inside the doBackwardFocusClaims method of InsuranceCorporateAction");
			TableData tableData = (TableData) request.getSession().getAttribute("tableData_Claims");
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		String insEnrollmentId	=	(String)request.getSession().getAttribute("insEnrollmentId");
    		ArrayList<ClaimsVO> alAuthorizationsList	=	(ArrayList<ClaimsVO>)insuranceCorporateManager.getCorpFocusClaimsDetails(insEnrollmentId,tableData.getSearchData());
    		tableData.setData(alAuthorizationsList, strBackward);//set the table data
    		request.getSession().setAttribute("tableData_Claims",tableData);//set the table data object to session
    		TTKCommon.documentViewer(request);
    		return this.getForward("focusedViewProceed", mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
    }//end of doBackwardFocusClaims(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    
    
    
    
    public ActionForward doForwardFocusAuths(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		request.setAttribute("modeType", "PAT");
    		setLinks(request);
    		log.debug("Inside the doForwardFocusAuths method of InsuranceCorporateAction");
			TableData tableData = (TableData) request.getSession().getAttribute("tableData_Authorizations");
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
            
    		tableData.modifySearchData(strForward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		String insEnrollmentId	=	(String)request.getSession().getAttribute("insEnrollmentId");
    		ArrayList<AuthorizationVO> alAuthorizationsList	=	(ArrayList<AuthorizationVO>)insuranceCorporateManager.getCorpFocusAuthorizationDetails(insEnrollmentId,tableData.getSearchData());
    		tableData.setData(alAuthorizationsList, strForward);//set the table data
    		request.getSession().setAttribute("tableData_Authorizations",tableData);//set the table data object to session
    		TTKCommon.documentViewer(request);
    		return this.getForward("focusedViewProceed", mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
    }//end of doForwardFocusClaims(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    
    
    public ActionForward doBackwardFocusAuths(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		request.setAttribute("modeType", "PAT");
    		setLinks(request);
    		log.debug("Inside the doBackwardFocusAuths method of InsuranceCorporateAction");
			TableData tableData = (TableData) request.getSession().getAttribute("tableData_Authorizations");
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
    		//fetch the data from the data access layer and set the data to table object
    		String insEnrollmentId	=	(String)request.getSession().getAttribute("insEnrollmentId");
    		ArrayList<AuthorizationVO> alAuthorizationsList	=	(ArrayList<AuthorizationVO>)insuranceCorporateManager.getCorpFocusAuthorizationDetails(insEnrollmentId,tableData.getSearchData());
    		tableData.setData(alAuthorizationsList, strBackward);//set the table data
    		request.getSession().setAttribute("tableData_Authorizations",tableData);//set the table data object to session
    		TTKCommon.documentViewer(request);
    		return this.getForward("focusedViewProceed", mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
    }//end of doBackwardFocusAuths(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    
    
    //RETAIL PART S T A R T S HERE
    /**
     * Do Retail first Page
     */
    public ActionForward doRetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
	try{
		log.debug("Inside the doRetail method of InsuranceCorporateAction");
		setOnlineLinks(request);
		
		DynaActionForm frmInsRetail =(DynaActionForm)form;
		frmInsRetail.initialize(mapping);     //reset the form data
		
		TableData tableData=TTKCommon.getTableData(request);
		tableData.createTableInfo("RetailSearchTable",null);//CorporateDataTable
		request.getSession().setAttribute("tableData",tableData);
       return this.getForward("insRetailSearch", mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
	}//end of catch(Exception exp)
}//end of doRetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
    
    
    /**
     * 
     * Search Retial 
     */
    public ActionForward doRetailSearchProceed(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			            HttpServletResponse response) throws Exception{
			try{
			log.debug("Inside the doRetailSearchProceed method of InsuranceCorporateAction");
			setOnlineLinks(request);
			
			ArrayList<RetailVO> alRetailProductsList = null;
			String strPageID =""; 
			String strSortID ="";	
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
			TableData tableData=TTKCommon.getTableData(request);
			
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
					return (mapping.findForward("insRetailSearch"));
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
				tableData.createTableInfo("RetailSearchTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,"RET"));						
				tableData.modifySearchData("search");
				//sorting based on investSeqID in descending order													
			}//end of else
			alRetailProductsList= insuranceCorporateManager.getRetailList(tableData.getSearchData());
			tableData.setData(alRetailProductsList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			
			return this.getForward("insRetailSearch", mapping, request);
			}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
	    }//end of doRetailSearchProceed(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
   
    
    
    /**
     * This method takes u to screen where you select Global and Focused View for Retails
     */
    
    public ActionForward doRetailGlobalFocusedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
	try{
		log.debug("Inside the doRetailGlobalFocusedView method of InsuranceCorporateAction");
		setOnlineLinks(request);
		
		RetailVO retailVO	=	null;

		DynaActionForm generalForm = (DynaActionForm)form;
		TableData tableData=TTKCommon.getTableData(request);
		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
        {
			retailVO = (RetailVO)tableData.getRowInfo(Integer.parseInt((String)(generalForm).get("rownum")));
            //add the selected item to the web board and make it as default selected
            //calling business layer to get the hospital detail
			request.getSession().setAttribute("insRetailProdSeqId", retailVO.getProductId()+"");//insRetailProdSeqId-converting to String
            generalForm.initialize(mapping);
        }//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		//if(alClaimsList!=null)
		
		return this.getForward("retailGlobalFocusedView", mapping, request);
	}//end of try
	catch(TTKException expTTK)
	{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
		System.out.println("ErrorL:L"+exp);
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
	}//end of catch(Exception exp)
}//end of doRetailGlobalFocusedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
    
    
    
    
    /**
     * GLobal view click on the page for Retails
     */
    
	    public ActionForward doRetailGlobalView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            HttpServletResponse response) throws Exception{
		try{
		log.debug("Inside the doRetailGlobalView method of InsuranceCorporateAction");
		setOnlineLinks(request);
		
		DynaActionForm frmInsCorporateFocused =(DynaActionForm)form;
		frmInsCorporateFocused.initialize(mapping);
		
		InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
		
		InsGlobalViewVO insGlobalViewVO	=	new  InsGlobalViewVO();
		String insRetailProdSeqId	=	(String) request.getSession().getAttribute("insRetailProdSeqId");
		insGlobalViewVO = insuranceCorporateManager.getCorpGlobalDetails((String)request.getSession().getAttribute("insCompCodeWebLogin"),"RET",(String)request.getSession().getAttribute("insRetailProdSeqId"));
		request.getSession().setAttribute("insGlobalViewVO", insGlobalViewVO);
		
		frmInsCorporateFocused = setFormValuesForGlobalView(insGlobalViewVO,mapping,request);//Set form values to other tables
		request.getSession().setAttribute("frmInsCorporateFocused",frmInsCorporateFocused);
		request.setAttribute("CorOrRet","RETAIL");
		 return this.getForward("insRetailGlobal", mapping, request);
		
		}//end of try
	catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
	catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
		}//end of catch(Exception exp)
	}//end of doRetailGlobalView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	    
	    
	    
	    /*
	     * Enter into focus view search
	     */
	    
	    
	    public ActionForward doRetailFocusedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            HttpServletResponse response) throws Exception{
	try{
	log.debug("Inside the doRetailFocusedView method of InsuranceCorporateAction");
	setOnlineLinks(request);
	
	DynaActionForm frmInsRetail =(DynaActionForm)form;
	frmInsRetail.initialize(mapping);     //reset the form data
	
	TableData tableData=new TableData();
	tableData.createTableInfo("MemberDataTable",null);
	frmInsRetail.set("caption", "Retail > Focused View");
	request.getSession().setAttribute("tableRetailDataMemberData",tableData);
	request.getSession().setAttribute("frmInsRetail",frmInsRetail);
	 return this.getForward("retailFocusedView", mapping, request);
	
	}//end of try
	catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
	catch(Exception exp)
	{
	return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
	}//end of catch(Exception exp)
}//end of doRetailFocusedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
    
	    
	    
    /*
     * Retail - Focus View Search for Enrollments
     */
    
    public ActionForward doSearchRetailFocusedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            HttpServletResponse response) throws Exception{
	try{
	log.debug("Inside the doSearchRetailFocusedView method of InsuranceCorporateAction");
	setOnlineLinks(request);
	
	ArrayList<EnrollMemberVO> alOnlineAccList = null;
	TableData tableData=null;
	String strPageID =""; 
	String strSortID ="";	
	InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
	//tableData=TTKCommon.getTableData(request);
	tableData=(TableData) request.getSession().getAttribute("tableRetailDataMemberData");
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
		return (mapping.findForward("focusedView"));
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
		tableData.createTableInfo("MemberDataTable",null);
		tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,"RETMEM"));						
		tableData.modifySearchData("search");
		//sorting based on investSeqID in descending order													
		}//end of else
	alOnlineAccList= insuranceCorporateManager.getRetailMemberList(tableData.getSearchData());
	tableData.setData(alOnlineAccList, "search");
	//set the table data object to session
	request.getSession().setAttribute("tableRetailDataMemberData",tableData);
	
	return this.getForward("retailFocusedView", mapping, request);
	}//end of try
	catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
	catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
}//end of doSearchRetailFocusedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
	    
	    
	    
	    
	    
	    
	    /*
	     * Retail Focused View Default
	     */
	    
	    public ActionForward doRetailFocusedViewDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doRetailFocusedViewDetails method of InsuranceCorporateAction");
			setOnlineLinks(request);
			
			request.setAttribute("modeType", "CLM");
			RetailVO retailVO=	new RetailVO();
			EnrollMemberVO enrollMemberVO	=	null;
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();

			DynaActionForm generalForm = (DynaActionForm)form;
			TableData tableData = (TableData) request.getSession().getAttribute("tableRetailDataMemberData");
		
			TableData tableData_Claims = new TableData();
			String strPageID = "";
			String strSortID = "";
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData_Claims.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward("onlineaccountinfo"));
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData_Claims.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData_Claims.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				// create the required grid table
				tableData_Claims.createTableInfo("FocusedViewClaimsTable",null);
				//fetch the data from the data access layer and set the data to table object
				tableData.setSearchData(new ArrayList<>());						
				tableData_Claims.modifySearchData("search");
			}//end of else
			
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
	        {
				enrollMemberVO = (EnrollMemberVO)tableData.getRowInfo(Integer.parseInt((String)(generalForm).get("rownum")));
	            //add the selected item to the web board and make it as default selected
	            //calling business layer to get the hospital detail
				retailVO = insuranceCorporateManager.getRetailFocusMemberDetails(enrollMemberVO.getEnrollmentId(),tableData_Claims.getSearchData(),"RET");
				request.getSession().setAttribute("retailVO", retailVO);
				request.getSession().setAttribute("insEnrollmentId", enrollMemberVO.getEnrollmentId());//insEnrollmentId
	            generalForm.initialize(mapping);
	        }//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			//if(alClaimsList!=null)
			
			//create Claims Table
			ArrayList<ClaimsVO> alClaimsList	=	retailVO.getClaimDetails();
			
			tableData_Claims.setData(alClaimsList, "search");
			request.getSession().setAttribute("tableData_Claims",tableData_Claims);
			
			//create blank Authorization table
			/*TableData tableData_Authorizations = new TableData();
			tableData_Authorizations.createTableInfo("FocusedViewAuthorizationsTable",null);
			tableData_Authorizations.setData(new ArrayList<>(), "search");
			request.getSession().setAttribute("tableData_Authorizations",tableData_Authorizations);*/
			
			String insEnrollmentId	=	(String)request.getSession().getAttribute("insEnrollmentId");
			
			//create Authorization Table
			TableData tableData_Authorizations = (TableData) (request.getSession().getAttribute("tableData_Authorizations")==null?
					new TableData():request.getSession().getAttribute("tableData_Authorizations"));
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData_Authorizations.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward("retailFocusedViewProceed"));
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData_Authorizations.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData_Authorizations.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				// create the required grid table
				tableData_Authorizations.createTableInfo("FocusedViewAuthorizationsTable",null);
				//fetch the data from the data access layer and set the data to table object
				tableData_Authorizations.setSearchData(new ArrayList<>());
				tableData_Authorizations.modifySearchData("search");
			}//end of else

			ArrayList<AuthorizationVO> alAuthorizationsList	=	(ArrayList<AuthorizationVO>)insuranceCorporateManager.getCorpFocusAuthorizationDetails(insEnrollmentId,tableData_Authorizations.getSearchData());
			tableData_Authorizations.setData(alAuthorizationsList, "search");
			request.getSession().setAttribute("tableData_Authorizations",tableData_Authorizations);

			request.getSession().setAttribute("enrollmentId", enrollMemberVO.getEnrollmentId());
			DynaActionForm frmInsRetail = setRetailFormValues(retailVO,mapping,request);//Set form values to other tables
			
			request.getSession().setAttribute("frmInsRetail",frmInsRetail);

			return this.getForward("retailFocusedViewProceed", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
	}//end of doRetailFocusedViewDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	    
	    
	    
	    
	    
	    /*
	     * Retail Focus View only Authorizations
	     */
	    
	    public ActionForward doRetailFocusViewAuthorizations(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doRetailFocusViewAuthorizations method of InsuranceCorporateAction");
			setOnlineLinks(request);
			
			request.setAttribute("modeType", "PAT");
	        InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();

			String insEnrollmentId	=	(String)request.getSession().getAttribute("insEnrollmentId");
			
			//create Authorization Table
			TableData tableData_Authorizations = (TableData) request.getSession().getAttribute("tableData_Authorizations");
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData_Authorizations.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward("retailFocusedViewProceed"));
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData_Authorizations.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData_Authorizations.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				// create the required grid table
				tableData_Authorizations.createTableInfo("FocusedViewAuthorizationsTable",null);
				//fetch the data from the data access layer and set the data to table object
				tableData_Authorizations.setSearchData(new ArrayList<>());
				tableData_Authorizations.modifySearchData("search");
			}//end of else

			ArrayList<AuthorizationVO> alAuthorizationsList	=	(ArrayList<AuthorizationVO>)insuranceCorporateManager.getCorpFocusAuthorizationDetails(insEnrollmentId,tableData_Authorizations.getSearchData());
			tableData_Authorizations.setData(alAuthorizationsList, "search");
			request.getSession().setAttribute("tableData_Authorizations",tableData_Authorizations);
			
			TableData tableData_Claims = new TableData();
			tableData_Claims.createTableInfo("FocusedViewClaimsTable",null);//create blank Claims table
			request.getSession().setAttribute("tableData_Claims",tableData_Claims);
			
			DynaActionForm frmInsRetail = (DynaActionForm)request.getSession().getAttribute("frmInsRetail");

			request.getSession().setAttribute("frmInsRetail",frmInsRetail);
			return mapping.findForward("retailFocusedViewProceed");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			System.out.println("ErrorL:L"+exp);
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
	}//end of doRetailFocusViewAuthorizations(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	    
	    
	    
	    
	    /*
	     * Focus View only Claims
	     */
	    
	    public ActionForward doRetailFocusViewClaims(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doRetailFocusViewClaims method of InsuranceCorporateAction");
			setOnlineLinks(request);
			
			request.setAttribute("modeType", "CLM");
	        InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();

			String insEnrollmentId	=	(String)request.getSession().getAttribute("insEnrollmentId");
			//if(alClaimsList!=null)
			
			//create Authorization Table
			TableData tableData_Claims = (TableData) request.getSession().getAttribute("tableData_Claims");
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData_Claims.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward("retailFocusedViewProceed"));
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData_Claims.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData_Claims.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				// create the required grid table
				tableData_Claims.createTableInfo("FocusedViewClaimsTable",null);
				//fetch the data from the data access layer and set the data to table object
				//tableData_Claims.setSearchData(new ArrayList<>());
				tableData_Claims.modifySearchData("search");
			}//end of else

			ArrayList<ClaimsVO> alAuthorizationsList	=	(ArrayList<ClaimsVO>)insuranceCorporateManager.getCorpFocusClaimsDetails(insEnrollmentId,tableData_Claims.getSearchData());
			tableData_Claims.setData(alAuthorizationsList, "search");
			request.getSession().setAttribute("tableData_Claims",tableData_Claims);
			
			//create blank Authorization table
			TableData tableData_Authorizations = new TableData();
			tableData_Authorizations.createTableInfo("FocusedViewAuthorizationsTable",null);
			request.getSession().setAttribute("tableData_Authorizations",tableData_Authorizations);
			
			DynaActionForm frmInsRetail = (DynaActionForm)request.getSession().getAttribute("frmInsRetail");
			request.getSession().setAttribute("frmInsRetail",frmInsRetail);
			return mapping.findForward("retailFocusedViewProceed");
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
	}//end of doFocusViewClaims(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	    
	    
	    
	    public ActionForward doRetailViewClaim(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            HttpServletResponse response) throws Exception{
		try{
		log.debug("Inside the doRetailViewClaim method of InsuranceCorporateAction");
		setOnlineLinks(request);

		request.setAttribute("modeType", "CLM");
		DynaActionForm frmClaimsView =(DynaActionForm)form;
		//((DynaActionForm)form).initialize(mapping);//reset the form data
		
		InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
		
		TableData tableData = (TableData) request.getSession().getAttribute("tableData_Claims");
		ClaimsVO claimsVO	=	null;
		ClaimDetailsVO claimDetailsVO	=	null;
		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
        {
			claimsVO	=	 (ClaimsVO)tableData.getRowInfo(Integer.parseInt((String)(frmClaimsView).get("rownum")));
            //calling business layer to get the hospital detail
			claimDetailsVO = insuranceCorporateManager.getClaimDetails(claimsVO.getClaimNbr());
        }//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		
		frmClaimsView = (DynaActionForm)FormUtils.setFormValues("frmClaimsView",
				claimDetailsVO,this,mapping,request);
		request.getSession().setAttribute("frmRetailClaimsView", frmClaimsView);
		
		return this.getForward("viewRetailClaimDetails", mapping, request);
	}//end of try
	catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
	}//end of catch(TTKException expTTK)
	catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
	}//end of catch(Exception exp)
}//end of doRetailViewClaim(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)



public ActionForward doRetailViewAuths(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	        HttpServletResponse response) throws Exception{
		try{
		log.debug("Inside the doRetailViewAuths method of InsuranceCorporateAction");
		setOnlineLinks(request);

		request.setAttribute("modeType", "PAT");
		DynaActionForm frmRetailAuthView =(DynaActionForm)form;
		//((DynaActionForm)form).initialize(mapping);//reset the form data
		
		InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
		
		TableData tableData = (TableData) request.getSession().getAttribute("tableData_Authorizations");
		AuthorizationVO authorizationVO	=	null;
		AuthDetailsVO authDetailsVO=	null;
		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		{
			authorizationVO	=	 (AuthorizationVO)tableData.getRowInfo(Integer.parseInt((String)(frmRetailAuthView).get("rownum")));
		    //calling business layer to get the hospital detail
			authDetailsVO = insuranceCorporateManager.getAuthDetails(authorizationVO.getAuthNo());
		}//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		frmRetailAuthView = (DynaActionForm)FormUtils.setFormValues("frmRetailAuthView",
				authDetailsVO,this,mapping,request);
		frmRetailAuthView.set("preAuthNumber", authorizationVO.getAuthNo());
		request.getSession().setAttribute("frmRetailAuthView", frmRetailAuthView);
		
		return this.getForward("viewRetailAuthDetails", mapping, request);
		}//end of try
	catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
	catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
		}//end of catch(Exception exp)
}//end of doRetailViewAuths(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)


	    
    //RETAIL PART E N D S HERE
    
    
    
    
    //PRODUCTS AND POLICIES S T A R T S HERE

		/*
		 * Product and polices Search screen
		 */
	public ActionForward doProductsAndPolicies(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		        HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doProductsAndPolicies method of InsuranceCorporateAction");
			setOnlineLinks(request);
			
			DynaActionForm frmProdPoliceis =(DynaActionForm)form;
			frmProdPoliceis.initialize(mapping);     //reset the form data
			
			TableData tableData=TTKCommon.getTableData(request);
			tableData.createTableInfo("ProductTable",null);//ProductTable
			
			request.getSession().setAttribute("tableData",tableData);
		   return this.getForward("productPolices", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
	}//end of doDashdoProductsAndPoliciesBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
	
	
	/*
	 * On Proceed Products
	 */
		public ActionForward doProceedProducts(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			            HttpServletResponse response) throws Exception{
			try{
			log.debug("Inside the doProceedProducts method of InsuranceCorporateAction");
			setOnlineLinks(request);
			
			DynaActionForm frmProdPoliceis =(DynaActionForm)form;
			ArrayList<ProductTableVO> alProductsList = null;
			TableData tableData= TTKCommon.getTableData(request);
			String strPageID =""; 
			String strSortID ="";	
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
			
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
				return (mapping.findForward("productPolices"));
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
				tableData.createTableInfo("ProductTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request,"PROD"));						
				tableData.modifySearchData("search");
			//sorting based on investSeqID in descending order													
			}//end of else
			alProductsList= insuranceCorporateManager.getProductsList(tableData.getSearchData());
			tableData.setData(alProductsList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			
			return this.getForward("productPolices", mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
				return this.processOnlineExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
			}//end of catch(Exception exp)
	}//end of doProceedProducts(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	
		
		/*
		 * On click on Products showing policies
		 */
	
		public ActionForward doViewPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	            HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doViewPolicy method of InsuranceCorporateAction");
			setOnlineLinks(request);
			
			DynaActionForm frmProdPoliceis =(DynaActionForm)form;
			//((DynaActionForm)form).initialize(mapping);//reset the form data
			
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
			
			TableData tableData = TTKCommon.getTableData(request);
			ProductTableVO productTableVO	=	null;
			ArrayList<PolicyDetailVO> alPolicyDetails	=	null;
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
		    {
				productTableVO	=	 (ProductTableVO)tableData.getRowInfo(Integer.parseInt((String)(frmProdPoliceis).get("rownum")));
		        //calling business layer to get the hospital detail
				alPolicyDetails = insuranceCorporateManager.getPolicyDetails(productTableVO.getProductSeqId(),(String)request.getSession().getAttribute("insCompCodeWebLogin"));
		    }//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			
			
			request.getSession().setAttribute("frmFocusedDetails", frmProdPoliceis);
			request.setAttribute("alPolicyDetails", alPolicyDetails);
			request.setAttribute("prodId", productTableVO.getProductName());
			request.getSession().setAttribute("prodSeqId", productTableVO.getProductSeqId());
			
			return this.getForward("showPolicyList", mapping, request);
		}//end of try
		catch(TTKException expTTK)
			{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
			{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
		}//end of catch(Exception exp)
}//end of doViewPolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
		
		
		/*
		 * Back to Products Search
		 */
		
		public ActionForward doBackToProdSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
    	log.debug("Inside the doBackToProdSearch method of InsuranceCorporateAction");
        setOnlineLinks(request);
        return this.getForward("productPolices", mapping, request);
		}
		
    //PRODUCTS AND POLICIES E N D S HERE
    
	
		//LOG DETAILS S T A R T S HERE
    /*
     * 
     */
		public ActionForward doLogDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		                HttpServletResponse response) throws Exception{
		try{
		log.debug("Inside the doLogDetails method of InsuranceCorporateAction");
		setOnlineLinks(request);
		
		DynaActionForm frmInsCorporate =(DynaActionForm)form;
		ArrayList alLogList = null;
		InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();

		alLogList	= insuranceCorporateManager.getLogData(TTKCommon.getUserID(request));
		request.setAttribute("alLogList", alLogList);
		
		return this.getForward("insLogData", mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
		return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		return this.processOnlineExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
		}//end of catch(Exception exp)
	}//end of doLogDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
    
    
    /*
     * Show TOBs
     */
    
		public ActionForward doPolicyUploads(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
			String policy_seq_id	=	(String)request.getParameter("policy_seq_id");
			log.info("Inside the doPolicyUploads method of PolicyDetailsAction");
			setLinks(request);
			String strTable = "";
			DynaActionForm frmInsCorporate= (DynaActionForm) form;
			
			ArrayList alPolicyDocs = new ArrayList();
			TableData tablePolicyDocs = new TableData();
			
			//create the required grid table
			strTable = "MouUploadFilesTable";
			
			tablePolicyDocs.createTableInfo(strTable,null);
			 
			PolicyManager policyObject=this.getPolicyManagerObject();
			alPolicyDocs = policyObject.getPolicyDocsUploads(policy_seq_id);
			tablePolicyDocs.setData(alPolicyDocs,"search");
			request.getSession().setAttribute("tablePolicyDocs",tablePolicyDocs);
			request.getSession().setAttribute("tableData",tablePolicyDocs);
			request.getSession().setAttribute("frmInsCorporate",frmInsCorporate);
			
			/*if(iTemp!=null && iTemp.length>0)
	        {*/
	        int strLink[]= {3};

	            for(int i=0;i<strLink.length;i++)
	            {
	                ((Column)((ArrayList)tablePolicyDocs.getTitle()).get(strLink[i])).setVisibility(false);
	            }//end of for(int i=0;i<iTemp.length;i++)
	       // }//end of if(iTemp!=null && iTemp.length>0)
			
			return this.getForward("policyDocsUploads", mapping, request);
		}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
			}//end of catch(Exception exp)
		}//end of doPolicyUploads(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
		
		 /*
	     * Show doEndorsements
	     */
	    
			public ActionForward doEndorsements(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
			{
				try
				{
				log.debug("Inside the doEndorsements method of PolicyDetailsAction");
				setLinks(request);
				DynaActionForm frmInsCorporate= (DynaActionForm) form;
				
				ArrayList alEndorseList = new ArrayList();
				
				InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
				alEndorseList = insuranceCorporateManager.getEndorsements((String)request.getSession().getAttribute("insCompCodeWebLogin"),
						(String)request.getSession().getAttribute("enrollmentId"));
				frmInsCorporate.set("alEndorseList", alEndorseList);
				request.getSession().setAttribute("frmInsCorporate",frmInsCorporate);
				return this.getForward("showEndorsements", mapping, request);
			}//end of try
				catch(TTKException expTTK)
				{
					return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
				}//end of catch(Exception exp)
			}//end of doEndorsements(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

			
		
		public ActionForward doBackFromTOB(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
			log.debug("Inside the doBackFromTOB method of InsuranceCorporateAction");
			DynaActionForm frmInsCorporate= (DynaActionForm) form;
			
			if(!"Retail".equals(TTKCommon.getActiveLink(request))){
				request.setAttribute("modeType", "CLM");
			request.getSession().setAttribute("frmInsCorporate",frmInsCorporate);
			return this.getForward("focusedViewProceed", mapping, request);
			}else{
				request.setAttribute("modeType", "RET");
				request.getSession().setAttribute("frmInsRetail",frmInsCorporate);
				return this.getForward("retailFocusedViewProceed", mapping, request);
			}
		}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
			}//end of catch(Exception exp)
		}//end of doBackFromTOB(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

		
		
    /*
     * 
     */
		public ActionForward doBackFromTOBFromAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
			log.debug("Inside the doBackFromTOBFromAuth method of InsuranceCorporateAction");
			DynaActionForm frmInsCorporate= (DynaActionForm) form;
			
			if(!"Retail".equals(TTKCommon.getActiveLink(request))){
				request.setAttribute("modeType", "CLM");
			request.getSession().setAttribute("frmInsCorporate",frmInsCorporate);
			return this.getForward("focusedViewProceed", mapping, request);
			}else{
				request.setAttribute("modeType", "RET");
				request.getSession().setAttribute("frmInsRetail",frmInsCorporate);
				return this.getForward("retailFocusedViewProceed", mapping, request);
			}
		}//end of try
			catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
			}//end of catch(Exception exp)
		}//end of doBackFromTOBFromAuth(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

		
		/*
	     * Show Policy Benefits
	     */
			public ActionForward doShowPolicyBenefits(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
			{
				try
				{
					log.debug("Inside the doShowPolicyBenefits method of InsuranceCorporateAction");

					DynaActionForm frmProdPoliceis =(DynaActionForm)form;
					InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
			        int prodSeqId	=	(Integer) request.getSession().getAttribute("prodSeqId");
			        String polSeqId	=	(String) request.getParameter("polSeqId");
			        String enrolTypeId	=	(String) request.getParameter("enrolTypeId");
			        
			        
			        String[] strBenefitLimits	= insuranceCorporateManager.getPolicyBenefitDetails(prodSeqId,polSeqId,enrolTypeId,(String)request.getSession().getAttribute("insCompCodeWebLogin"));
			        request.setAttribute("strBenefitLimits", strBenefitLimits);
					return mapping.findForward("showPolicyBenefits");        
					//return this.getForward("showPolicyBenefits", mapping, request);
				}//end of try
				catch(Exception exp)
				{
					return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
				}//end of catch(Exception exp)
			}//end of doShowPolicyBenefits(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

		
		/*
		 * Showing search parameters for Insurance Login Reports
		 */
		public ActionForward doInsReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				log.debug("Inside the doInsReports method of InsuranceCorporateAction");

				DynaActionForm frmInsReports =(DynaActionForm)form;
				frmInsReports.initialize(mapping);
				String xml	=	request.getParameter("xml")==null?"":request.getParameter("xml");
				String xmlId=	request.getParameter("xmlId")==null?"":request.getParameter("xmlId");
				String name	=	request.getParameter("name")==null?"":request.getParameter("name");
				InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
				String insCompCodeWebLogin	=	(String) request.getSession().getAttribute("insCompCodeWebLogin");

				if("TATForCards".equals(xmlId)){
					/*ArrayList<String> tatForCards	=	insuranceCorporateManager.getTATForCards(insCompCodeWebLogin);
					request.setAttribute("tatForCards", tatForCards);
					return mapping.findForward("tatForCards");*/
					 OutputStream  sos = response.getOutputStream();
					 ArrayList<String> tatForCards	=	insuranceCorporateManager.getTATForCards(insCompCodeWebLogin);
					 SimpleDateFormat format=new SimpleDateFormat("-yyyy-MM-dd-HH-mm-sss");
			         String fileID=format.format(new Date());
			         response.setContentType("application/txt");
			         response.setHeader("Content-Disposition", "attachment;filename="+xmlId+fileID+".xls");
			         
			         JasperReport jasperReport,emptyReport;
					 JasperPrint jasperPrint;
					 
					 jasperReport = JasperCompileManager.compileReport(xml);
					 emptyReport = JasperCompileManager.compileReport("reports/insLogin/TATFSINDIVIDUAL_NoRecords.jrxml");//("generalreports/EmptyReprot.jrxml");
					 HashMap<String, String> hashMap = new HashMap();
					 
					 hashMap.put("ZEROTO5", tatForCards.get(0));
					 hashMap.put("5TOTEN", tatForCards.get(1));
					 hashMap.put("ABOVE10", tatForCards.get(2));
					 
					 String str1= tatForCards.get(0);
					 String str2= tatForCards.get(1);
					 String str3= tatForCards.get(2);
					 
					 if((("").equals(str1) && str1.length()<1)&&(("").equals(str2) && str2.length()<1) && (("").equals(str3) && str3.length()<1))
					 {
						 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
					 }else{
					 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,new JREmptyDataSource());
					 }
					 request.setAttribute("reportType","EXL");
					 JRXlsExporter jExcelApiExporter = new JRXlsExporter();
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, sos);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
					 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
					 jExcelApiExporter.exportReport();
					 sos.flush();
					 sos.close();
					 return null;
					 
				}else{
				ArrayList<CacheObject> alCorpList	=	insuranceCorporateManager.getCorporatesList(insCompCodeWebLogin);
				frmInsReports.set("alCorpList",alCorpList);
				frmInsReports.set("reportName",name);
				frmInsReports.set("xmlId",xmlId);
				frmInsReports.set("jrxmlName",xml);
				if("TATFSINDIVIDUAL".equals(xmlId)){
					request.getSession().setAttribute("alINDPolicyList", insuranceCorporateManager.getInduPolicyList(insCompCodeWebLogin));
				}
				request.getSession().setAttribute("frmInsReports", frmInsReports);
				return this.getForward("insReports",mapping,request);  
				}
				//return this.getForward("showPolicyBenefits", mapping, request);
			}//end of try
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
			}//end of catch(Exception exp)
		}//end of doInsReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

		
		/*
		 * On Changing Corporate - get the policies in the corporate
		 */
		public ActionForward doChangeCorp(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				log.debug("Inside the doChangeCorp method of InsuranceCorporateAction");

				DynaActionForm frmInsReports =(DynaActionForm)form;
				String strCorpSeqId	=	frmInsReports.getString("corpName");
				
				InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
				String insCompCodeWebLogin	=	(String) request.getSession().getAttribute("insCompCodeWebLogin");
				ArrayList<CacheObject> alPolicyList	=	insuranceCorporateManager.getPolicyList(insCompCodeWebLogin,strCorpSeqId);
				frmInsReports.set("alPolicyList",alPolicyList);
				request.getSession().setAttribute("frmInsReports", frmInsReports);
				return this.getForward("insReports",mapping,request);  
				//return this.getForward("showPolicyBenefits", mapping, request);
			}//end of try
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
			}//end of catch(Exception exp)
		}//end of doChangeCorp(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

		
		/*
		 * On Changing policy - get the policY Period
		 */
		public ActionForward doChangePolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				log.debug("Inside the doChangePolicy method of InsuranceCorporateAction");

				DynaActionForm frmInsReports =(DynaActionForm)form;
				String strPolicySeqId	=	frmInsReports.getString("policyNo");
				
				InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
				String insCompCodeWebLogin	=	(String) request.getSession().getAttribute("insCompCodeWebLogin");
				ArrayList<CacheObject> alPolicyPeriod	=	insuranceCorporateManager.getPolicyPeriod(strPolicySeqId);
				frmInsReports.set("alPolicyPeriod",alPolicyPeriod);
				request.getSession().setAttribute("frmInsReports", frmInsReports);
				return this.getForward("insReports",mapping,request);  
				//return this.getForward("showPolicyBenefits", mapping, request);
			}//end of try
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
			}//end of catch(Exception exp)
		}//end of doChangePolicy(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

		
		 /**
	     * Generate Insurance login reports
	     */
	    
		public ActionForward doGenerateInsTATClaimReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				log.info("Inside the doGenerateInsTATClaimReports method of InsuranceCorporateAction");


				 DynaActionForm frmInsReports = (DynaActionForm)form;
				 ArrayList<String> alInputParams	=	new ArrayList<>(); 
				 alInputParams.add(0, (String) frmInsReports.get("fromDate"));
				 alInputParams.add(1, (String) frmInsReports.get("toDate"));
				 alInputParams.add(2, (String) (String)request.getSession().getAttribute("insCompCodeWebLogin"));
				 alInputParams.add(3, (String) frmInsReports.get("corpName"));
				 alInputParams.add(4, (String) frmInsReports.get("memberId"));
				 alInputParams.add(5, (String) frmInsReports.get("policyNo"));
				 alInputParams.add(6, (String) frmInsReports.get("policyPeriod"));

				 InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
				 ArrayList<String[]> alClaimTatData	=	insuranceCorporateManager.getClaimTatData(alInputParams);
				 ArrayList<String> alClaimsTatData	=	new ArrayList<>();
				 String[] clmData	=	null;
				 long[] noOfClaims = new long[alClaimTatData.size()];
				 for(int k=0;k<alClaimTatData.size();k++){
					 clmData	=	alClaimTatData.get(k);
					// for(int l=0;l<clmData.length;l++){
						 alClaimsTatData.add(clmData[0]);
						 
						 
							String dateStart = clmData[1]+"/"+clmData[2]+"/"+clmData[3];//claim recieved;
							String dateStop	 = clmData[4]+"/"+clmData[5]+"/"+clmData[6];//claim Approved;
							/*String dateStart = "01/14/2012 09:29:58";//claim recieved;
							String dateStop	 = "01/17/2012 10:31:48";//claim Approved;
							 */
							
							//HH converts hour in 24 hours format (0-23), day calculation
							SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

							Date d1 = null;
							Date d2 = null;
							
							try {
								d1 = format.parse(dateStop);
								d2 = format.parse(dateStart);

								//in milliseconds
								long diff = d2.getTime() - d1.getTime();

								/*long diffSeconds = diff / 1000 % 60;
								long diffMinutes = diff / (60 * 1000) % 60;
								long diffHours = diff / (60 * 60 * 1000) % 24;*/
								long diffDays = diff / (24 * 60 * 60 * 1000);
								noOfClaims[k]	=	diffDays;

							} catch (Exception e) {
								e.printStackTrace();
							}
					 //}
				 }
				 request.setAttribute("noOfClaims", noOfClaims);
				 request.setAttribute("alClaimTatData", alClaimTatData);
				 				 
				 return (mapping.findForward("tatForClaims"));
				//return this.getForward("showPolicyBenefits", mapping, request);
			}//end of try
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
			}//end of catch(Exception exp)
		}//end of doGenerateInsTATClaimReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

		
		public ActionForward doGenerateInsTATPreAuthReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
		{
			try
			{
				log.debug("Inside the doGenerateInsTATPreAuthReports method of InsuranceCorporateAction");


				 DynaActionForm frmInsReports = (DynaActionForm)form;
				 ArrayList<String> alInputParams	=	new ArrayList<>(); 
				 alInputParams.add(0, (String) frmInsReports.get("fromDate"));
				 alInputParams.add(1, (String) frmInsReports.get("toDate"));
				 alInputParams.add(2, (String) (String)request.getSession().getAttribute("insCompCodeWebLogin"));
				 alInputParams.add(3, (String) frmInsReports.get("corpName"));
				 alInputParams.add(4, (String) frmInsReports.get("memberId"));
				 alInputParams.add(5, (String) frmInsReports.get("policyNo"));
				 alInputParams.add(6, (String) frmInsReports.get("policyPeriod"));

				 InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();
				 ArrayList<String[]> alPreAuthTatData	=	insuranceCorporateManager.getPreAuthTatData(alInputParams);
				 ArrayList<String> alClaimsTatData	=	new ArrayList<>();
				 String[] clmData	=	null;
				 long[] noOfPreAuths = new long[alPreAuthTatData.size()];
				 for(int k=0;k<alPreAuthTatData.size();k++){
					 clmData	=	alPreAuthTatData.get(k);
					// for(int l=0;l<clmData.length;l++){
						 alClaimsTatData.add(clmData[0]);
						 
						 
							String dateStart = clmData[1]+"/"+clmData[2]+"/"+clmData[3]+" "+clmData[4]+":"+clmData[5]+":00";//claim recieved;
							String dateStop	 = clmData[6]+"/"+clmData[7]+"/"+clmData[8]+" "+clmData[9]+":"+clmData[10]+":00";;//claim Approved;
							/*String dateStart = "01/14/2012 09:29:58";//claim recieved;
							String dateStop	 = "01/17/2012 10:31:48";//claim Approved;
							 */
							//HH converts hour in 24 hours format (0-23), day calculation
							SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

							Date d1 = null;
							Date d2 = null;
							
							try {
								d1 = format.parse(dateStop);
								d2 = format.parse(dateStart);

								//in milliseconds
								long diff = d2.getTime() - d1.getTime();
								long diffMinutes = diff / (60 * 1000) ;
								/*long diffSeconds = diff / 1000 % 60;
								long diffMinutes = diff / (60 * 1000) % 60;
								long diffHours = diff / (60 * 60 * 1000) % 24;
								long diffDays = diff / (24 * 60 * 60 * 1000);*/
								noOfPreAuths[k]	=	diffMinutes;

							} catch (Exception e) {
								e.printStackTrace();
							}
					 //}
				 }
				 request.setAttribute("noOfPreAuths", noOfPreAuths);
				 request.setAttribute("alPreAuthTatData", alPreAuthTatData);
				 				 
				 return (mapping.findForward("tatForPreAuths"));
				//return this.getForward("showPolicyBenefits", mapping, request);
			}//end of try
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
			}//end of catch(Exception exp)
		}//end of doGenerateInsTATPreAuthReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

		
		public ActionForward doGenerateInsReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	 try{
		 log.debug("Inside the doGenerateInsReports method of ReportsAction");
		 setLinks(request);
		 DynaActionForm frmInsReports = (DynaActionForm)form;
		 OutputStream  sos = response.getOutputStream();
		 String jrxmlName	=	request.getParameter("jrxmlName")==null?"":request.getParameter("jrxmlName");
		 String xmlId	=	request.getParameter("xmlId")==null?"":request.getParameter("xmlId");
		 String reportName	=	request.getParameter("reportName")==null?"":request.getParameter("reportName");
		 String corpNameText	=	request.getParameter("corpNameText")==null?"":request.getParameter("corpNameText");
		 
		 
		 SimpleDateFormat format=new SimpleDateFormat("-yyyy-MM-dd-HH-mm-sss");
         String fileID=format.format(new Date());
         response.setContentType("application/txt");
         response.setHeader("Content-Disposition", "attachment;filename="+xmlId+fileID+".xls");

		 JasperReport jasperReport,emptyReport;
		 JasperPrint jasperPrint;
		 TTKReportDataSource ttkReportDataSource = null;
		 ArrayList<String> alInputParams	=	new ArrayList<>(); 
		 alInputParams.add(0, (String) frmInsReports.get("fromDate"));
		 alInputParams.add(1, (String) frmInsReports.get("toDate"));
		 alInputParams.add(2, (String) (String)request.getSession().getAttribute("insCompCodeWebLogin"));
		 alInputParams.add(3, (String) frmInsReports.get("corpName"));
		 alInputParams.add(4, (String) frmInsReports.get("memberId"));
		 alInputParams.add(5, (String) frmInsReports.get("policyNo"));
		 alInputParams.add(6, (String) frmInsReports.get("policyPeriod"));
		 
		 
		 ttkReportDataSource = new TTKReportDataSource(xmlId,alInputParams);
		 jasperReport = JasperCompileManager.compileReport(jrxmlName);
		 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
		 if("ClaimUtilizationRep".equals(xmlId))
			 emptyReport = JasperCompileManager.compileReport("reports/insLogin/ClaimUtilizationRep_NoRecords.jrxml");
		 else if("AuthUtilizationRep".equals(xmlId))
			 emptyReport = JasperCompileManager.compileReport("reports/insLogin/AuthUtilizationReport_NoRecords.jrxml");
		 else if("TATForClaims".equals(xmlId))
			 emptyReport = JasperCompileManager.compileReport("reports/insLogin/TATForClaims_NoRecords.jrxml");
		 else if("TATForPreAuth".equals(xmlId))
			 emptyReport = JasperCompileManager.compileReport("reports/insLogin/TATForPreAuth_NoRecords.jrxml");
		 else if("TechnicalResultReport".equals(xmlId))
			 emptyReport = JasperCompileManager.compileReport("reports/insLogin/TechnicalResultReport_NoRecords.jrxml");
		 else if("TATFSCORPOARTE".equals(xmlId))
			 emptyReport = JasperCompileManager.compileReport("reports/insLogin/TATFSCORPOARTE_NoRecords.jrxml");
		 else if("TATFSINDIVIDUAL".equals(xmlId))
			 emptyReport = JasperCompileManager.compileReport("reports/insLogin/TATFSINDIVIDUAL_NoRecords.jrxml");
		 else if("TATForCards".equals(xmlId))
			 emptyReport = JasperCompileManager.compileReport("reports/insLogin/TATForCards_NoRecords.jrxml");
		 else if("HIPA".equals(xmlId))
			 emptyReport = JasperCompileManager.compileReport("reports/insLogin/HIPA_NoRecords.jrxml");
		 HashMap<String, String> hashMap = new HashMap();
		 if("TATForClaims".equals(xmlId)){
			 hashMap	=	 getTATClaimData(alInputParams);
			// jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
		 } else if("TATForPreAuth".equals(xmlId)){
			 hashMap	=	 getTATPreAuthData(alInputParams);
		 }
		 hashMap.put("CORPNAMETEXT", corpNameText);
		 hashMap.put("FROMDATE", (String) frmInsReports.get("fromDate"));
		 hashMap.put("TODATE", (String) frmInsReports.get("toDate"));
		 
		 ByteArrayOutputStream boas=new ByteArrayOutputStream();

		 request.setAttribute("reportType","EXL");
		 if(ttkReportDataSource.getResultData().next())
		 {
			 ttkReportDataSource.getResultData().beforeFirst();
			 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);				 
		 }//end of if(ttkReportDataSource.getResultData().next()))
		 else
		 {
			 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
		 }//end of if(ttkReportDataSource.getResultData().next())

		 /*if(request.getParameter("reportType").equals("EXL"))//if the report type is Excel
		 {*/
			 JRXlsExporter jExcelApiExporter = new JRXlsExporter();
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, sos);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			 jExcelApiExporter.exportReport();
			 
		 /*}//end of if(request.getParameter("reportType").equals("EXL"))
		  * 
		  * 
		  * If Report Type is PDF below code works for that
		  * 
		 else//if report type if PDF
		 {
			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);	 
		 }*///end of else
		 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
		// request.setAttribute("boas",boas);
			 sos.flush();
			 sos.close();
		 if(!(ttkReportDataSource.equals("null")))
 		{
 			request.setAttribute("GenerateXL","message.GenerateXLSuccessfully");
 			log.info("report generated sucessfully ");
 		}//end of if(!(reportDataSource.equals("null")))
	 }//end of try
	 
	 catch(Exception exp)
	 {
		 return null;
	 }//end of catch(Exception exp)		
	 return null;
 }//end of doGenerateInsReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

		public HashMap getTATClaimData(ArrayList alInputParams) throws TTKException{
			HashMap<String, Integer> hashMap	=	new HashMap<>();
			
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();

			 ArrayList<String[]> alClaimTatData	=	insuranceCorporateManager.getClaimTatData(alInputParams);
			 ArrayList<String> alClaimsTatData	=	new ArrayList<>();
			 String[] clmData	=	null;
			 long[] noOfClaims = new long[alClaimTatData.size()];
			 for(int k=0;k<alClaimTatData.size();k++){
				 clmData	=	alClaimTatData.get(k);
					 alClaimsTatData.add(clmData[0]);
					 
					 
						String dateStart = clmData[1]+"/"+clmData[2]+"/"+clmData[3];//claim recieved;
						String dateStop	 = clmData[4]+"/"+clmData[5]+"/"+clmData[6];//claim Approved;
						
						//HH converts hour in 24 hours format (0-23), day calculation
						SimpleDateFormat format1 = new SimpleDateFormat("MM/dd/yyyy");

						Date d1 = null;
						Date d2 = null;
						
						try {
							d1 = format1.parse(dateStop);
							d2 = format1.parse(dateStart);

							//in milliseconds
							long diff = d2.getTime() - d1.getTime();

							long diffDays = diff / (24 * 60 * 60 * 1000);
							noOfClaims[k]	=	diffDays;

						} catch (Exception e) {
							e.printStackTrace();
						}
			 }
			 
			 	int zeroTo5		=	0;
				int fiveTo15	=	0;
				int above15		=	0;
				
				int NzeroTo5	=	0;
				int NfiveTo15	=	0;
				int Nabove15	=	0;
				
				for(int k=0;k<noOfClaims.length;k++){
					 clmData	=	(String[])alClaimTatData.get(k);
					 if(clmData[0].equals("MEMBER"))
					 {
						 if(noOfClaims[k]<6)
								zeroTo5++;
						 if(noOfClaims[k]>5 && noOfClaims[k]<=15)
								fiveTo15++;
						 if(noOfClaims[k]>15)
								above15++;
					 }else if(clmData[0].equals("NETWORK")){
						 if(noOfClaims[k]<16)
							 NzeroTo5++;
						 if(noOfClaims[k]>15 && noOfClaims[k]<=30)
							 NfiveTo15++;
						 if(noOfClaims[k]>30)
							 Nabove15++;
					 }
				}
				hashMap.put("ZEROTO5", zeroTo5);
				hashMap.put("FIVETO15", fiveTo15);
				hashMap.put("ABOVE15", above15);
				hashMap.put("NZEROTO5", NzeroTo5);
				hashMap.put("NFIVETO15", NfiveTo15);
				hashMap.put("NABOVE15", Nabove15);
			return hashMap;
		}
		
		public HashMap getTATPreAuthData(ArrayList alInputParams) throws TTKException{
			HashMap<String, Integer> hashMap	=	new HashMap<>();
			
			InsuranceCorporateManager insuranceCorporateManager	=	this.getOnlineAccessManagerObject();

			ArrayList<String[]> alPreAuthTatData	=	insuranceCorporateManager.getPreAuthTatData(alInputParams);
			 ArrayList<String> alClaimsTatData	=	new ArrayList<>();
			 String[] clmData	=	null;
			 long[] noOfPreAuths = new long[alPreAuthTatData.size()];
			 for(int k=0;k<alPreAuthTatData.size();k++){
				 clmData	=	alPreAuthTatData.get(k);
				// for(int l=0;l<clmData.length;l++){
					 alClaimsTatData.add(clmData[0]);
					 
					 
						String dateStart = clmData[1]+"/"+clmData[2]+"/"+clmData[3]+" "+clmData[4]+":"+clmData[5]+":00";//preauth recieved;
						String dateStop	 = clmData[6]+"/"+clmData[7]+"/"+clmData[8]+" "+clmData[9]+":"+clmData[10]+":00";;//preauth Approved;
						/*String dateStart = "01/14/2012 09:29:58";//claim recieved;
						String dateStop	 = "01/17/2012 10:31:48";//claim Approved;
						 */
						//HH converts hour in 24 hours format (0-23), day calculation
						SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

						Date d1 = null;
						Date d2 = null;
						
						try {
							d1 = format.parse(dateStop);
							d2 = format.parse(dateStart);

							//in milliseconds
							long diff = d2.getTime() - d1.getTime();
							//long diffDays = diff / (60 * 1000) ;
							long diffHours = diff / (60 * 60 * 1000) % 24;
							/*System.out.println("diffHours::"+diffHours);
							long diffSeconds = diff / 1000 % 60;
							long diffMinutes = diff / (60 * 1000) % 60;
							long diffHours = diff / (60 * 60 * 1000) % 24;
							long diffDays = diff / (24 * 60 * 60 * 1000);*/
							noOfPreAuths[k]	=	diffHours;

						} catch (Exception e) {
							e.printStackTrace();
						}
				 //}
			 }
			 	int zeroTo5		=	0;
				int fiveTo15	=	0;
				int above15		=	0;
				
				int NzeroTo5	=	0;
				int NfiveTo15	=	0;
				int Nabove15	=	0;
			 for(int k=0;k<noOfPreAuths.length;k++){
				 clmData	=	(String[])alPreAuthTatData.get(k);
				 if(clmData[0].equals("OUT-PATIENT"))
				 {
					 if(noOfPreAuths[k]<=1)
							zeroTo5++;
					 if(noOfPreAuths[k]>1 && noOfPreAuths[k]<=3)
							fiveTo15++;
					 if(noOfPreAuths[k]>3)
							above15++;
				 }else if(clmData[0].equals("IN-PATIENT")){
					 if(noOfPreAuths[k]<=1)
						 NzeroTo5++;
					 if(noOfPreAuths[k]>1 && noOfPreAuths[k]<=3)
						 NfiveTo15++;
					 if(noOfPreAuths[k]>3)
						 Nabove15++;
				 }
				
			}
			 	hashMap.put("ZEROTO5", zeroTo5);
				hashMap.put("FIVETO15", fiveTo15);
				hashMap.put("ABOVE15", above15);
				hashMap.put("NZEROTO5", NzeroTo5);
				hashMap.put("NFIVETO15", NfiveTo15);
				hashMap.put("NABOVE15", Nabove15);
			return hashMap;
		}
		
		
		 /**
	     * Generate Insurance login reports
	     */
	    
		public ActionForward doGenerateInsReportsBackup(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	 try{
		 log.debug("Inside the doGenerateInsReports method of ReportsAction");
		 setLinks(request);
		 DynaActionForm frmInsReports = (DynaActionForm)form;

		 String jrxmlName	=	request.getParameter("jrxmlName")==null?"":request.getParameter("jrxmlName");
		 String xmlId	=	request.getParameter("xmlId")==null?"":request.getParameter("xmlId");
		 String reportName	=	request.getParameter("reportName")==null?"":request.getParameter("reportName");
		 
		 
		 JasperReport jasperReport,emptyReport;
		 JasperPrint jasperPrint;
		 TTKReportDataSource ttkReportDataSource = null;
		 ArrayList<String> alInputParams	=	new ArrayList<>(); 
		 alInputParams.add(0, (String) frmInsReports.get("fromDate"));
		 alInputParams.add(1, (String) frmInsReports.get("toDate"));
		 alInputParams.add(2, (String) (String)request.getSession().getAttribute("insCompCodeWebLogin"));
		 alInputParams.add(3, (String) frmInsReports.get("corpName"));
		 alInputParams.add(4, (String) frmInsReports.get("memberId"));
		 alInputParams.add(5, (String) frmInsReports.get("policyNo"));
		 alInputParams.add(6, (String) frmInsReports.get("policyPeriod"));
		 
		 
		 ttkReportDataSource = new TTKReportDataSource(xmlId,alInputParams);

		 jasperReport = JasperCompileManager.compileReport(jrxmlName);
		 emptyReport = JasperCompileManager.compileReport("generalreports/EmptyReprot.jrxml");
		 HashMap hashMap = new HashMap();
		 ByteArrayOutputStream boas=new ByteArrayOutputStream();

		 request.setAttribute("reportType","EXL");
		 if(ttkReportDataSource.getResultData().next())
		 {
			 ttkReportDataSource.getResultData().beforeFirst();
			 jasperPrint = JasperFillManager.fillReport( jasperReport, hashMap,ttkReportDataSource);				 
		 }//end of if(ttkReportDataSource.getResultData().next()))
		 else
		 {
			 jasperPrint = JasperFillManager.fillReport( emptyReport, hashMap,new JREmptyDataSource());
		 }//end of if(ttkReportDataSource.getResultData().next())
		 
		 /*if(request.getParameter("reportType").equals("EXL"))//if the report type is Excel
		 {*/
			 JRXlsExporter jExcelApiExporter = new JRXlsExporter();
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.JASPER_PRINT,jasperPrint);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, boas);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			 jExcelApiExporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			 jExcelApiExporter.exportReport();
			 
		 /*}//end of if(request.getParameter("reportType").equals("EXL"))
		  * 
		  * 
		  * If Report Type is PDF below code works for that
		  * 
		 else//if report type if PDF
		 {
			 JasperExportManager.exportReportToPdfStream(jasperPrint,boas);	 
		 }*///end of else
		 //keep the byte array out stream in request scope to write in the BinaryStreamServlet
		 request.setAttribute("boas",boas);
		 if(!(ttkReportDataSource.equals("null")))
 		{
 			request.setAttribute("GenerateXL","message.GenerateXLSuccessfully");
 			log.info("report generated sucessfully ");
 		}//end of if(!(reportDataSource.equals("null")))
	 }//end of try
	 catch(TTKException expTTK)
	 {
		 return this.processExceptions(request, mapping, expTTK);
	 }//end of catch(TTKException expTTK)
	 catch(Exception exp)
	 {
		 return this.processExceptions(request, mapping, new TTKException(exp,"onlineaccountinfo"));
	 }//end of catch(Exception exp)		
	 return (mapping.findForward(strReportdisplay));
 }//end of doGenerateInsReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



	    private PolicyManager getPolicyManagerObject() throws TTKException
	    {
	        PolicyManager policyManager = null;
	        try
	        {
	            if(policyManager == null)
	            {
	                InitialContext ctx = new InitialContext();
	                policyManager = (PolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/PolicyManagerBean!com.ttk.business.enrollment.PolicyManager");
	            }//end if(policyManager == null)
	        }//end of try
	        catch(Exception exp)
	        {
	            throw new TTKException(exp, "policydetail");
	        }//end of catch
	        return policyManager;
	    }//end getHospitalManagerObject()
    /**
     * Populates the value object to form element.
     * @param hospDetailVO HospitalDetailVO object.
     * @param mapping The ActionMapping used to select this instance.
     * @param request HttpServletRequest  object which contains hospital information.
     * @return DynaActionForm object.
     */
    private DynaActionForm setFormValues(InsCorporateVO insCorporateVO,ActionMapping mapping,
    		HttpServletRequest request) throws TTKException
    {
        try
        {
            DynaActionForm insCorporateForm = (DynaActionForm)FormUtils.setFormValues("frmInsCorporate",
            		insCorporateVO,this,mapping,request);
            //personnel Info
            if(insCorporateVO.getPersonalInfoVO()!=null)
            {
            	insCorporateForm.set("personalInfoVO", (DynaActionForm)FormUtils.setFormValues("frmPersonalInfo",
                		insCorporateVO.getPersonalInfoVO(),this,mapping,request));
            }//end of if(insCorporateVO.getPersonalInfoVO()!=null)
            else
            {
            	insCorporateForm.set("personalInfoVO", (DynaActionForm)FormUtils.setFormValues("frmPersonalInfo",
                		new PersonalInfoVO(),this,mapping,request));
            }//end of else
            
            
            //Policy Details
            if(insCorporateVO.getPolicyDetailVO()!=null)
            {
            	insCorporateForm.set("policyDetailVO", (DynaActionForm)FormUtils.setFormValues("frmPolicyInfo",
                		insCorporateVO.getPolicyDetailVO(),this,mapping,request));
            }//end of if(insCorporateVO.getPolicyDetailVO()!=null)
            else
            {
            	insCorporateForm.set("policyDetailVO", (DynaActionForm)FormUtils.setFormValues("frmPolicyInfo",
                		new PolicyDetailVO(),this,mapping,request));
            }//end of else
            
            return insCorporateForm;
        }
        catch(Exception exp)
        {
            throw new TTKException(exp,"onlineaccountinfo");
        }//end of catch
    }//end of setFormValues(InsCorporateVO insCorporateVO,ActionMapping mapping,HttpServletRequest request)

    
    
    
    private DynaActionForm setRetailFormValues(RetailVO retailVO,ActionMapping mapping,
    		HttpServletRequest request) throws TTKException
    {
        try
        {
            DynaActionForm insRetailForm = (DynaActionForm)FormUtils.setFormValues("frmInsRetail",
            		retailVO,this,mapping,request);
            //personnel Info
            if(retailVO.getPersonalInfoVO()!=null)
            {
            	insRetailForm.set("personalInfoVO", (DynaActionForm)FormUtils.setFormValues("frmPersonalInfo",
            			retailVO.getPersonalInfoVO(),this,mapping,request));
            }//end of if(insCorporateVO.getPersonalInfoVO()!=null)
            else
            {
            	insRetailForm.set("personalInfoVO", (DynaActionForm)FormUtils.setFormValues("frmPersonalInfo",
                		new PersonalInfoVO(),this,mapping,request));
            }//end of else
            
            
            //Policy Details
            if(retailVO.getPolicyDetailVO()!=null)
            {
            	insRetailForm.set("policyDetailVO", (DynaActionForm)FormUtils.setFormValues("frmPolicyInfo",
            			retailVO.getPolicyDetailVO(),this,mapping,request));
            }//end of if(insCorporateVO.getPolicyDetailVO()!=null)
            else
            {
            	insRetailForm.set("policyDetailVO", (DynaActionForm)FormUtils.setFormValues("frmPolicyInfo",
                		new PolicyDetailVO(),this,mapping,request));
            }//end of else
            
            return insRetailForm;
        }
        catch(Exception exp)
        {
            throw new TTKException(exp,"onlineaccountinfo");
        }//end of catch
    }//end of setFormValues(InsCorporateVO insCorporateVO,ActionMapping mapping,HttpServletRequest request)
    
    
    
    /**
     * Global View Set Form
     */
    private DynaActionForm setFormValuesForGlobalView(InsGlobalViewVO insGlobalViewVO,ActionMapping mapping,
    		HttpServletRequest request) throws TTKException
    {
        try
        {
            DynaActionForm frmInsCorporateFocused = (DynaActionForm)FormUtils.setFormValues("frmInsCorporateFocused",
            		insGlobalViewVO,this,mapping,request);
            
            //Authorization Details
            if(insGlobalViewVO.getAuthorizationVO()!=null)
            {
            	frmInsCorporateFocused.set("authorizationVO", (DynaActionForm)FormUtils.setFormValues("frmAuthDetails",
            			insGlobalViewVO.getAuthorizationVO(),this,mapping,request));
            }//end of if(insCorporateVO.getPolicyDetailVO()!=null)
            else
            {
            	frmInsCorporateFocused.set("authorizationVO", (DynaActionForm)FormUtils.setFormValues("frmAuthDetails",
                		new AuthorizationVO(),this,mapping,request));
            }//end of else
            
            return frmInsCorporateFocused;
        }
        catch(Exception exp)
        {
            throw new TTKException(exp,"onlineaccountinfo");
        }//end of catch
    }//end of setFormValuesForGlobalView(InsGlobalViewVO insGlobalViewVO,ActionMapping mapping,HttpServletRequest request)

    
    /**
     * Do Back to Global focused View
     */
    public ActionForward doBackCorpGlobalFocused(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
    	log.debug("Inside the doBackCorpGlobalFocused method of InsuranceCorporateAction");
        setOnlineLinks(request);
        String strForward;
        if("RETAIL".equals((String)request.getParameter("strVar")))
        	strForward	=	"retailGlobalFocusedView";
        else
        	strForward	=	"globalfocusedView";
        
		return mapping.findForward(strForward);        
    }
    
    /**
     * this method will add search criteria fields and values to the arraylist and will return it
     * @param frmInsCorporate formbean which contains the search fields
     * @return ArrayList contains search parameters
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmInsCorporate,HttpServletRequest request,String strVar) throws TTKException
    {
//      build the column names along with their values to be searched
        ArrayList<Object> alSearchBoxParams=new ArrayList<Object>();
        //prepare the search BOX parameters
      //  Long lngHospSeqId =userSecurityProfile.getHospSeqId();
        if("COR".equals(strVar)){
        	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmInsCorporate.get("corpName")));//0
    	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmInsCorporate.get("policyNumb")));//1
            alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmInsCorporate.get("validPeriod")));//2
        	alSearchBoxParams.add((String)request.getSession().getAttribute("insCompCodeWebLogin"));//3
        }
        else if("MEM".equals(strVar)){
        	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmInsCorporate.getString("insuredName")));//0
    	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmInsCorporate.getString("enrollmentNo")));//1
            alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmInsCorporate.getString("empNo")));//2
        	alSearchBoxParams.add((String)request.getSession().getAttribute("insCorporateSeqId"));//3
        	alSearchBoxParams.add((String)request.getSession().getAttribute("insCompCodeWebLogin"));//4 Ins Comp Code
        }else if("RET".equals(strVar)){
        	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmInsCorporate.getString("prodName")));//0
    	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmInsCorporate.getString("dateFrom")));//1
            alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmInsCorporate.getString("dateTo")));//2
        	alSearchBoxParams.add((String)request.getSession().getAttribute("insCompCodeWebLogin"));//3
        }else if("RETMEM".equals(strVar)){
        	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmInsCorporate.getString("insuredName")));//0
    	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmInsCorporate.getString("enrollmentNo")));//1
            alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmInsCorporate.getString("empNo")));//2
        	alSearchBoxParams.add((String)request.getSession().getAttribute("insRetailProdSeqId"));//3
        }else if("PROD".equals(strVar)){
        	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmInsCorporate.getString("ProdName")));//0
    	 	alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmInsCorporate.getString("ProdCode")));//1
            alSearchBoxParams.add(TTKCommon.replaceSingleQots((String)frmInsCorporate.getString("authType")));//2
        	alSearchBoxParams.add((String)request.getSession().getAttribute("insCompCodeWebLogin"));//3
        }
        
        
    	return alSearchBoxParams;
    }//end of populateSearchCriteria(DynaActionForm frmInsCorporate,HttpServletRequest request) 
    

    
    
    private InsuranceCorporateManager getOnlineAccessManagerObject() throws TTKException
    {
    	InsuranceCorporateManager insuranceCorporateManager = null;
        try
        {
            if(insuranceCorporateManager == null)
            {
                InitialContext ctx = new InitialContext();
                //onlineAccessManager = (OnlineAccessManager) ctx.lookup(OnlineAccessManager.class.getName());
                insuranceCorporateManager = (InsuranceCorporateManager) ctx.lookup("java:global/TTKServices/business.ejb3/InsuranceCorporateManagerBean!com.ttk.business.onlineforms.insuranceLogin.InsuranceCorporateManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "onlineaccountinfo");
        }//end of catch
        return insuranceCorporateManager;
    }//end of getOnlineAccessManagerObject()

          
}//end of InsuranceCorporateAction
