/**
 * @ (#) ICDListAction.java Nov 27, 2006
 * Project      : TTK HealthCare Services
 * File         : ICDListAction.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Nov 27, 2006
 *
 * @author      :  Arun K N
 * Modified by  :
 * Modified date:
 * Reason       :
 */

package com.ttk.action.dataentryenrollment;

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
import com.ttk.action.dataentrypreauth.PackageListAction;
import com.ttk.action.table.TableData;
import com.ttk.business.dataentryenrollment.ParallelMemberManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.CodingClaimsWebBoardHelper;
import com.ttk.common.CodingWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.enrollment.PEDVO;


/**
 * This Action class is used for searching the ICD Code based on ICD code and Description
 * and allows to Select the Required ICD.
 * This is reused in Enrollment->Ped Details, PreAuth/Claims->PED details screen and ICD/PCS screen to select
 * the Required ICD.
 *
 */
public class ICDListAction extends TTKAction {
	private static Logger log = Logger.getLogger( PackageListAction.class );
    //declarations of mode
    private static final String strForward ="Forward";
    private static final String strBackward ="Backward";

    //forward paths in Enrollment Flow
    private static final String strIndICDList="indicdlist";
    private static final String strIngICDList="ingicdlist";
    private static final String strCorICDList="coricdlist";
    private static final String strNcrCDList="ncricdlist";
    private static final String strIndPEDDetail="indpeddetail";
    private static final String strIngPEDDetail="ingpeddetail";
    private static final String strCorPEDDetail="corpeddetail";
    private static final String strNcrPEDDetail="ncrpeddetail";

    //forward paths for the Preauth flow
    private static final String strPreauthIcdList="preauthicdlist";
    private static final String strPreauthIcdPcs="preauthicdpcs";
    private static final String strPreauthPedDetail="preauthpeddetail";
    private static final String strCodePreauthIcdList="codepreauthicdlist";
    private static final String strCodePreauthICDList="codepreauthicdlist";
    private static final String strCodeClaimsICDList="dataentrycodeclaimsicdlist";
    private static final String strCodePreauthMedical="codepreauthmedical";
    private static final String strCodeClaimsMedical="dataentrycodeclaimsmedical";
    
    
    private static final String strCodePreauthIcdPcs="codepreauthicdpcs";
    private static final String strCodePreauthPedDetail="codepreauthpeddetail";
    private static final String strCodeCleanupPreauthIcdList="codecleanuppreauthicdlist";
    private static final String strCodeCleanupPreauthIcdPcs="codecleanuppreauthicdpcs";
    private static final String strCodeCleanupPreauthPedDetail="codecleanuppreauthpeddetail";
    
    //forward paths for the Claims flow
    private static final String strClaimsIcdList="claimsicdlist";
    private static final String strClaimsPedDetail="claimspeddetail";
    private static final String strClaimsICDPcs="claimsicdpcs";
    private static final String strCodeClaimsIcdList="dataentrycodeclaimsicdlist";
    private static final String strCodeClaimsICDPcs="dataentrycodeclaimsicdpcs";
    private static final String strCodeClaimsPedDetail="dataentrycodeclaimspeddetail";
    private static final String strCodeCleanupClaimsIcdList="codecleanupclaimsicdlist";
    private static final String strCodeCleanupClaimsICDPcs="codecleanupclaimsicdpcs";
    private static final String strCodeCleanupClaimsPedDetail="codecleanupclaimspeddetail";
    //declarations of Constants used
    private static final String strEnrollment="Enrollment";
    private static final String strIndividualPolicy="Individual Policy";
    private static final String strGroupPolicy="Ind. Policy as Group";
    private static final String strCorporatePolicy="Corporate Policy";
    private static final String strNonCorporatePolicy="Non-Corporate Policy";
    private static final String strPreAuthorization="Pre-Authorization";
    private static final String strClaims="Claims";
    private static final String strICDPCS="ICDPCS";      //identifier for Preauth/Claims ICDPCS screen flow
    private static final String strPED="PED";            //identifier for Preauth/Claims PED Details screen flow
    private static final String strICD="ICD";

    // Added for ICD Screen integration by Unni V Mana
    private static final String strICDSearch="icdsearch";
    private static final String strPedCode="PEDCODE";
	private static final String strMasterPedCode="MASTERPEDCODE";
    // End of Addition
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
    		log.debug("Inside ICDListAction doDefault");
    		DynaActionForm frmICDList=(DynaActionForm)form;
    		String strActiveLink=TTKCommon.getActiveLink(request);
    		String strFlag="";
    		String strICDList=getICDListPath(request);
    		TableData icdListData = new TableData();  //create new table data object
    		// Added for ICD Code screen implementation by UNNI V MANA ON 14-05-2008
    		if("Maintenance".equals(strActiveLink) && "ICD Code".equals(TTKCommon.getActiveSubLink(request)))
    		{
    			if(request.getParameter("screen")==null)
    			{
    				icdListData.createTableInfo("ICDTable",new ArrayList<Object>());     //create the required grid table
    			}//end of if(request.getParameter("screen")==null)
    			else
    			{	
    				icdListData.createTableInfo("ICDListTable",new ArrayList<Object>());	
    				// take frmAddICDCode form from session 
    				DynaActionForm frmAddICDCode = (DynaActionForm) request.getSession().getAttribute("frmAddICDCode");
    				frmAddICDCode.set("icdCode",     request.getParameter("icdCode"));
    				frmAddICDCode.set("description", request.getParameter("description"));
    				frmAddICDCode.set("mostCommon",  request.getParameter("mostCommon"));
    				// put the form back into session
    				request.getSession().setAttribute("frmAddICDCode",frmAddICDCode);
    			}//end of else
    			request.getSession().setAttribute("icdListData",icdListData);
    		}//end of if("Maintenance".equals(strActiveLink) && "ICD Code".equals(TTKCommon.getActiveSubLink(request)))
    		else
    		{
    			icdListData.createTableInfo("ICDListTable",new ArrayList());     //create the required grid table
    			request.getSession().setAttribute("icdListData",icdListData);
    		}//end of else
    		// End of Addition
            
            //In case of PreAuth/Claims this is Used by different screens under same Tab
            //So Flag is used to identify the flow
            if(strActiveLink.equals(strPreAuthorization)||strActiveLink.equals(strClaims)||strActiveLink.equals("Coding")||strActiveLink.equals("Code CleanUp")||strActiveLink.equals("DataEntryCoding"))
            {
                if(!TTKCommon.checkNull(request.getParameter("flag")).equals(""))
                {
                    strFlag=request.getParameter("flag");
                }//end of if(!TTKCommon.checkNull(request.getParameter("flag")).equals(""))
                else
                {
                    strFlag=(String)frmICDList.get("flag");
                }//end of else
            }//end of if(strActiveLink.equals(strPreAuthorization)||strActiveLink.equals(strClaims))
            frmICDList.initialize(mapping); //reset the form data
            frmICDList.set("flag",strFlag);
            getCaption(frmICDList,request);
            // Added for ICD Screen integration by UNNI on 16-05-2008
            String strScreen= request.getParameter("screen");
            if(strScreen !=null)
            {
            	return this.getForward(strICDSearch,mapping,request);
            }//end of if(strScreen !=null)
            // End of Addition
            return this.getForward(strICDList,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"icdlist"));
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
    		log.debug("Inside ICDListAction doSearch");
    		setLinks(request);
    		TableData icdListData = null;
    		ParallelMemberManager memberManager=this.getMemberManager();
    		if((request.getSession()).getAttribute("icdListData") != null)
    		{
                icdListData = (TableData)(request.getSession()).getAttribute("icdListData");
    		}//end of if((request.getSession()).getAttribute("icdListData") != null)
            else
            {
                icdListData = new TableData();
            }//end of else
    		String strICDList=getICDListPath(request);
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
            //if the page number or sort id is clicked
            if(!strPageID.equals("") || !strSortID.equals(""))
            {
                if(strPageID.equals(""))
                {
                	icdListData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
                    icdListData.modifySearchData("sort");//modify the search data                    
                }///end of if(!strPageID.equals(""))
                else
                {
                	log.debug("PageId   :"+TTKCommon.checkNull(request.getParameter("pageId")));
                    icdListData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
                    return this.getForward(strICDList, mapping, request);
                }//end of else
            }//end of if(!strPageID.equals("") || !strSortID.equals(""))
            else
            {
            	// Added for ICD Code screen implementation by UNNI V MANA ON 14-05-2008
        		if("Maintenance".equals(TTKCommon.getActiveLink(request)) && "ICD Code".equals(TTKCommon.getActiveSubLink(request)))
        		{
        			
        			if(request.getParameter("screen")==null || "close".equalsIgnoreCase(request.getParameter("screen")))
        			{
        				request.getSession().setAttribute("op","edit");   // here it is an edit operation
            			icdListData.createTableInfo("ICDTable",null);     //create the required grid table
        			}//end of if(request.getParameter("screen")==null || "close".equalsIgnoreCase(request.getParameter("screen")))	
        			else if("masterlist".equalsIgnoreCase(request.getParameter("screen")))
        			{
        				  icdListData.createTableInfo("ICDListTable",null); // for re-using the search table
        			}//end of else if("masterlist".equalsIgnoreCase(request.getParameter("screen")))
        		}//end of if("Maintenance".equals(TTKCommon.getActiveLink(request)) && "ICD Code".equals(TTKCommon.getActiveSubLink(request)))	
            	//create the required grid table
        		else{
        			icdListData.createTableInfo("ICDListTable",null);
        		}//end of else
        		// End of Addition
                icdListData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
                icdListData.modifySearchData("search");
            }//end of else

            //fetch the data from the data access layer and set the data to table object
            //Added for ICD Code screen implementation on UNNI V MANA ON 18-05-2008
            ArrayList alICDList=null;
            if(!"masterlist".equals(request.getParameter("screen"))){
             alICDList= memberManager.getICDList(icdListData.getSearchData());
            }//end of if(!"masterlist".equals(request.getParameter("screen"))){
            else
            {	
             //alICDList= this.getMaintenanceManagerObject().selectICDList((icdListData.getSearchData()));
               alICDList= memberManager.getICDList(icdListData.getSearchData());
            }//end of else
            // End of Addition
            
            icdListData.setData(alICDList, "search");
            //set the table data object to session
            request.getSession().setAttribute("icdListData",icdListData);
            getCaption((DynaActionForm)form,request);
    		//finally return to the grid screen
            return this.getForward(strICDList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"icdlist"));
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
    		log.debug("Inside ICDListAction doForward");
    		setLinks(request);
    		TableData icdListData = null;
    		ParallelMemberManager memberManager=this.getMemberManager();
    		String strICDList=getICDListPath(request);
    		if((request.getSession()).getAttribute("icdListData") != null)
    		{
                icdListData = (TableData)(request.getSession()).getAttribute("icdListData");
    		}//end of if((request.getSession()).getAttribute("icdListData") != null)
            else
            {
                icdListData = new TableData();
            }//end of else
    		icdListData.modifySearchData(strForward);  //modify the search data
            ArrayList alICDList= memberManager.getICDList(icdListData.getSearchData());
            icdListData.setData(alICDList,strForward);
            request.getSession().setAttribute("icdListData",icdListData);
            return this.getForward(strICDList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"icdlist"));
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
    		log.debug("Inside ICDListAction doBackward");
    		setLinks(request);
    		TableData icdListData = null;
    		ParallelMemberManager memberManager=this.getMemberManager();
    		String strICDList=getICDListPath(request);
    		if((request.getSession()).getAttribute("icdListData") != null)
    		{
    			icdListData = (TableData)(request.getSession()).getAttribute("icdListData");
    		}//end of if((request.getSession()).getAttribute("icdListData") != null)
    		else
    		{
    			icdListData = new TableData();
    		}//end of else
    		icdListData.modifySearchData(strBackward);  //modify the search data
    		ArrayList alICDList= memberManager.getICDList(icdListData.getSearchData());
    		icdListData.setData(alICDList,strBackward);
    		request.getSession().setAttribute("icdListData",icdListData);
    		return this.getForward(strICDList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"icdlist"));
		}//end of catch(Exception exp)
}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doSelectICD(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside ICDListAction doSelectICD");
    		setLinks(request);
    		String strActiveLink=TTKCommon.getActiveLink(request);
    		String strFlag="";
    		 DynaActionForm frmICDList=(DynaActionForm)form;
    		 DynaActionForm frmICDPCSCoding=null;
    		 
    		 String diagSeqId = (String)request.getSession().getAttribute("diagSeqId");
    		 long diagnosisSeqID = Long.parseLong(diagSeqId);
    		 
            if(strActiveLink.equals(strPreAuthorization)||strActiveLink.equals(strClaims)||strActiveLink.equals("DataEntryClaims")||strActiveLink.equals("Coding")||strActiveLink.equals("Code CleanUp")||strActiveLink.equals("DataEntryCoding"))
            {
                if(!TTKCommon.checkNull(request.getParameter("flag")).equals(""))
                {
                    strFlag=request.getParameter("flag");
                }//end of if(!TTKCommon.checkNull(request.getParameter("flag")).equals(""))
                else
                {
                    strFlag=(String)frmICDList.get("flag");
                }//end of else
            }//end of if(strActiveLink.equals(strPreAuthorization)||strActiveLink.equals(strClaims))
            String strPEDDetail=getPEDDetailPath(request,strFlag);
    		TableData icdListData = null;
    		if((request.getSession()).getAttribute("icdListData") != null)
    		{
                icdListData = (TableData)(request.getSession()).getAttribute("icdListData");
    		}//end of if((request.getSession()).getAttribute("icdListData") != null)
            else
            {
                icdListData = new TableData();
            }//end of else

    		if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
                //get the ICD selected from the screen
                PEDVO pedVO=(PEDVO)icdListData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
                if(strActiveLink.equals(strEnrollment))     //Enrollment flow -->PED details
                {
                    DynaActionForm frmAddPED= (DynaActionForm)request.getSession().getAttribute("frmAddPED");
                    frmAddPED.set("PEDCodeID",pedVO.getPEDCodeID().toString());
                    frmAddPED.set("ICDCode",pedVO.getICDCode());
                    frmAddPED.set("description",pedVO.getDescription());
                }//end of if(strActiveLink.equals(strEnrollment))

                else if(strActiveLink.equals(strPreAuthorization) ||strActiveLink.equals(strClaims)||strActiveLink.equals("DataEntryClaims"))
                {
                    if(strFlag.equals(strICDPCS))   //Preauth/Claims -->ICDPCS flow
                    {
                    	frmICDPCSCoding=(DynaActionForm)request.getSession().getAttribute("frmICDPCSCoding");
                        if(frmICDPCSCoding!=null)
                        {
                            frmICDPCSCoding.set("description",pedVO.getDescription());
                            frmICDPCSCoding.set("ICDCode",pedVO.getICDCode());
                            frmICDPCSCoding.set("PEDCodeID",pedVO.getPEDCodeID().toString());
                        }//end of if(frmICDPCSCoding!=null)
                    }//end of if(strFlag.equals(strICDPCS))

                    else if(strFlag.equals(strPED)) //Preauth/Claims -->PED details
                    {
                    	DynaActionForm frmAddPED= (DynaActionForm)request.getSession().getAttribute("frmAddPED");
                        frmAddPED.set("PEDCodeID",pedVO.getPEDCodeID().toString());
                        frmAddPED.set("ICDCode",pedVO.getICDCode());
                        frmAddPED.set("description",pedVO.getDescription());
                    }//end of else if(strFlag.equals(strPED))
                }//end of else if(strActiveLink.equals(strPreAuthorization) ||strActiveLink.equals(strClaims)||strActiveLink.equals("DataEntryClaims"))
                else if(strActiveLink.equals("Coding")||strActiveLink.equals("Code CleanUp")||strActiveLink.equals("DataEntryCoding"))
                {
                	if(strFlag.equals(strICDPCS))   //Preauth/Claims -->ICDPCS flow
                    {
                		DynaActionForm frmICDPCSPolicy=(DynaActionForm)request.getSession().getAttribute("frmICDPCSPolicy");
                        if(frmICDPCSPolicy!=null)
                        {
                        	log.debug("inside icdpcs coding frmICDPCSPolicy");
                        	frmICDPCSPolicy.set("sICDName",pedVO.getDescription());
                        	frmICDPCSPolicy.set("sICDCode",pedVO.getICDCode());
                        	frmICDPCSPolicy.set("pEDCodeID",pedVO.getPEDCodeID());  
                        	frmICDPCSPolicy.set("diagSeqId",diagnosisSeqID);
                         	
                        }//end of if(frmICDPCSCoding!=null)
                    }//end of if(strFlag.equals(strICDPCS))

                    else if(strFlag.equals(strPED) || strFlag.equals(strICD)) //Preauth/Claims -->PED details
                    {
                    	log.info("inside icdpcs coding frmICDPCSPolicy else *");
                        DynaActionForm frmAddPED= (DynaActionForm)request.getSession().getAttribute("frmAddPED");
                        frmAddPED.set("PEDCodeID",pedVO.getPEDCodeID().toString());
                        frmAddPED.set("ICDCode",pedVO.getICDCode());
                        frmAddPED.set("description",pedVO.getDescription());
                    }//end of else if(strFlag.equals(strPED))
                }//end of else if(strActiveLink.equals("Coding")||strActiveLink.equals("Code CleanUp"))                
            }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		return this.getForward(strPEDDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"icdlist"));
		}//end of catch(Exception exp)
}//end of doSelectICD(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside ICDListAction doClose");
    		setLinks(request);
    		// Added for ICD Screen implementation by UNNI V MANA on 18-05-2008
    		TableData icdListData = new TableData();
    		icdListData.createTableInfo("ICDTable",new ArrayList());     //create the required grid table
            request.getSession().setAttribute("icdListData",icdListData);
            if("Maintenance".equals(TTKCommon.getActiveLink(request)) && "ICD Code".equals(TTKCommon.getActiveSubLink(request)))
			{	
            	if("close".equalsIgnoreCase(request.getParameter("screen")))
            	{	
            		request.getSession().removeAttribute("icdCode");
            		request.getSession().removeAttribute(strPedCode);
          		  	request.getSession().removeAttribute(strMasterPedCode);
            		DynaActionForm frmICDList = (DynaActionForm) request.getSession().getAttribute("frmICDList");
            		frmICDList.set("caption", "List of ICD");
            		frmICDList.set("sICDCode", "");
            		frmICDList.set("sICDName", "");
            	}//end of if("close".equalsIgnoreCase(request.getParameter("screen")))
			}//end of if("Maintenance".equals(TTKCommon.getActiveLink(request)) && "ICD Code".equals(TTKCommon.getActiveSubLink(request)))
            request.setAttribute("screen", "");
    		// End of Addition
            return doSelectICD( mapping, form, request, response);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"icdlist"));
		}//end of catch(Exception exp)
}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    
    /**
	 * This method returns the ICD list forward path for next view based on the Flow
	 *
	 * @param request The HTTP request we are processing.
	 * @return strForwardPath String forward path for the next view
	 */
	private String getICDListPath(HttpServletRequest request)throws TTKException
	{
		String strICDList=null;
		try
        {
			String strActiveLink=TTKCommon.getActiveLink(request);
	        String strActiveSubLink=TTKCommon.getActiveSubLink(request);
	        String strTab=TTKCommon.getActiveTab(request);
			if(strActiveLink.equals(strEnrollment))     //for Enrollment module
	        {
	            if(strActiveSubLink.equals(strIndividualPolicy))
	            {
	                strICDList=strIndICDList;
	            }//end of if(strActiveSubLink.equals(strIndividualPolicy))
	            else if(strActiveSubLink.equals(strGroupPolicy))
	            {
	                strICDList=strIngICDList;
	            }//end of if(strActiveSubLink.equals(strIndividualPolicy))
	            else if(strActiveSubLink.equals(strCorporatePolicy))
	            {
	                strICDList=strCorICDList;
	            }//end of else if(strActiveSubLink.equals(strCorporatePolicy))
	            else if(strActiveSubLink.equals(strNonCorporatePolicy))
	            {
	                strICDList=strNcrCDList;
	            }//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
	        }//end of if(strActiveLink.equals(strEnrollment))
			else if(strActiveLink.equals(strPreAuthorization))      //for Preauth module
	        {
	            strICDList=strPreauthIcdList;

	        }//end of else if(strActiveLink.equals(strPreAuthorization))

	        else if(strActiveLink.equals(strClaims))                //for Claims module
	        {
	            strICDList=strClaimsIcdList;
	        }//end of else if(strActiveLink.equals(strClaims))
	        else if(strActiveLink.equals("Coding")||(strActiveLink.equals("DataEntryCoding")))
			{
				/*if(strActiveSubLink.equals("PreAuth")){
					if(strTab.equals("Medical"))
					{
						strICDList=strCodePreauthICDList;
					}//end of if(strTab.equals("Medical"))
					else
					{
						strICDList=strCodePreauthIcdList;
					}//end of else
				}//end of if(strActiveSubLink.equals("PreAuth"))
				else*/ if(strActiveSubLink.equals("Claims")){					
					if(strTab.equals("Medical"))
					{
						strICDList=strCodeClaimsICDList;
					}//end of if(strTab.equals("Medical"))
					else
					{
						strICDList=strCodeClaimsIcdList;
					}//end of else
				}//end of else if(strActiveSubLink.equals("Claims"))
			}//end of else if(strActiveLink.equals("Coding"))
	        else if(strActiveLink.equals("Code CleanUp"))
			{
				if(strActiveSubLink.equals("PreAuth")){
					strICDList=strCodeCleanupPreauthIcdList;
				}//end of if(strActiveSubLink.equals("PreAuth"))
				else if(strActiveSubLink.equals("Claims")){
					strICDList=strCodeCleanupClaimsIcdList;
				}//end of else
			}//end of else if(strActiveLink.equals("Code CleanUp"))
			/* UNNI Added on 13-05-2008 for ICD screen integration */
	        else if("Maintenance".equals(strActiveLink))
	        {
	        	if("ICD Code".equals(strActiveSubLink)){
	        		if(!"masterlist".equals(request.getParameter("screen")))
	        		{
	        			strICDList=strICD;
	        		}//end of if(!"masterlist".equals(request.getParameter("screen")))
	        		else
	        		{
	        			strICDList=strICDSearch;
	        		}//end of else
	        	}//end of if("ICD Code".equals(strActiveSubLink))
	        }//end of else if("Maintenance".equals(strActiveLink))
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "icdlist");
        }//end of catch

		return strICDList;
	}//end of getICDListPath(HttpServletRequest request)

	/**
	 * This method is used to manipulate the caption used in the screens.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @throws Exception if any error occurs
	 */    
	private void getCaption(DynaActionForm formAssociateIcdCode,HttpServletRequest request)throws TTKException
	{
		try
		{
			String strLink=TTKCommon.getActiveLink(request);
			String strSubLink=TTKCommon.getActiveSubLink(request);
			StringBuffer sbfCaption= new StringBuffer();
			if(strLink.equals(strPreAuthorization))
			{
				sbfCaption.append("Associate ICD - [ ").append(PreAuthWebBoardHelper.getClaimantName(
					request)).append(" ] [ ").append(PreAuthWebBoardHelper.getWebBoardDesc(request)).append(" ]");
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
    				sbfCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
			else if(strLink.equals(strClaims))
			{
				sbfCaption.append("Associate ICD - [ ").append(ClaimsWebBoardHelper.
								  getClaimantName(request)).append(" ] [ ").append(ClaimsWebBoardHelper.
										  						getWebBoardDesc(request)).append(" ]");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
                	sbfCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of else if(strLink.equals(strPreAuthorization)&& strSubLink.equals(strProcessing))
			if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			{
				if(strSubLink.equals("PreAuth")){
					sbfCaption.append("Associate ICD - [ ").append(CodingWebBoardHelper.getClaimantName(
							request)).append(" ] [ ").append(CodingWebBoardHelper.getWebBoardDesc(request)).append(" ]");
					if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
					{
	                	sbfCaption.append(" [ "+CodingWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingWebBoardHelper.getEnrollmentId(request).trim())))
				}//end of if(strSubLink.equals("PreAuth"))
				else if(strSubLink.equals("Claims")){
					sbfCaption.append("Associate ICD - [ ").append(CodingClaimsWebBoardHelper.
							  getClaimantName(request)).append(" ] [ ").append(CodingClaimsWebBoardHelper.
									  						getWebBoardDesc(request)).append(" ]");
					if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
					{
	                	sbfCaption.append(" [ "+CodingClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
					}//end of if(CodingClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(CodingClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				}//end of else if(strSubLink.equals("Claims"))
			}//end of if(strLink.equals("Coding")||strLink.equals("Code CleanUp"))
			// Added for ICD Code Screen implementation by UNNI V MANA on 14-05-2008
			if("Maintenance".equals(strLink) && "ICD Code".equals(strSubLink))
			{
				DynaActionForm frmAddICDCode=(DynaActionForm)request.getSession().getAttribute("frmAddICDCode");
				if("masterlist".equalsIgnoreCase((request.getParameter("screen")))){
					if(frmAddICDCode.getString("icdCode")!= null &&!frmAddICDCode.getString("icdCode").equals(""))
			   		{
						sbfCaption.append("Associate ICD - [").append(frmAddICDCode.getString("icdCode")).append("]");
			   			//sbfCaption.append(" - [").append(frmAddPCSCode.getString("masterProcCode")).append("]");
			   		}// end of if(frmAddICDCode.getString("masterIcdCode")!= null &&!frmAddICDCode.getString("masterIcdCode").equals(""))
					else
					{
						sbfCaption.append("Associate ICD");
					}//end of else
				}//end of if("masterlist".equalsIgnoreCase((request.getParameter("screen"))))
				else
				{
					sbfCaption.append("List of ICD");
				}//end of else
			}//end of if("Maintenance".equals(strLink) && "ICD Code".equals(strSubLink))
			// End of addition
			formAssociateIcdCode.set("caption",String.valueOf(sbfCaption));
		}
		catch(Exception exp)
		{
			throw new TTKException(exp, "icdlist");
		}//end of catch
	}// end of getCaption(DynaActionForm frmOffice,HttpServletRequest request)
	
	/**
	 * This method returns the PED detail forward path for next view based on the Flow
	 *
	 * @param request The HTTP request we are processing.
	 * @param strFlag the flag which give ICDPCS/PED
	 *
	 * @return strForwardPath String forward path for the next view
	 */
	private String getPEDDetailPath(HttpServletRequest request,String strFlag)throws TTKException
	{
		String strPEDDetail=null;
		try
        {
			String strActiveLink=TTKCommon.getActiveLink(request);
	        String strActiveSubLink=TTKCommon.getActiveSubLink(request);
	        String strTab=TTKCommon.getActiveTab(request);
			if(strActiveLink.equals(strEnrollment))     //for Enrollment module
	        {
	            if(strActiveSubLink.equals(strIndividualPolicy))
	            {
	                strPEDDetail=strIndPEDDetail;
	            }//end of if(strActiveSubLink.equals(strIndividualPolicy))
	            else if(strActiveSubLink.equals(strGroupPolicy))
	            {
	                strPEDDetail=strIngPEDDetail;
	            }//end of if(strActiveSubLink.equals(strIndividualPolicy))
	            else if(strActiveSubLink.equals(strCorporatePolicy))
	            {
	                strPEDDetail=strCorPEDDetail;
	            }//end of else if(strActiveSubLink.equals(strCorporatePolicy))
	            else if(strActiveSubLink.equals(strNonCorporatePolicy))
	            {
	                strPEDDetail=strNcrPEDDetail;
	            }//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
	        }//end of if(strActiveLink.equals(strEnrollment))
			else if(strActiveLink.equals(strPreAuthorization))      //for Preauth module
			{
				if(strFlag.equals(strICDPCS))
				{
					strPEDDetail=strPreauthIcdPcs;
				}//end of if(strFlag.equals(strICDPCS))
				else if(strFlag.equals(strPED))
				{
					strPEDDetail=strPreauthPedDetail;
				}//end of else if(strFlag.equals(strPED))
			}//end of else if(strActiveLink.equals(strPreAuthorization))

	        else if(strActiveLink.equals(strClaims)||strActiveLink.equals("DataEntryClaims"))                //for Claims module
	        {
	            if(strFlag.equals(strICDPCS))
	            {
	                strPEDDetail=strClaimsICDPcs;
	            }//end of if(strFlag.equals(strICDPCS))
	            else if(strFlag.equals(strPED))
	            {
	                strPEDDetail=strClaimsPedDetail;
	            }//end of else if(strFlag.equals(strPED))
	        }//end of else if(strActiveLink.equals(strClaims))
			
	        else if(strActiveLink.equals("Coding")||strActiveLink.equals("DataEntryCoding"))
	        {
	        	/*if(strActiveSubLink.equals("PreAuth")){					
	        		if(strFlag.equals(strICDPCS))
	        		{
	        			strPEDDetail=strCodePreauthIcdPcs;
	        		}//end of if(strFlag.equals(strICDPCS))
	        		else if(strFlag.equals(strPED))
	        		{
	        			strPEDDetail=strCodePreauthPedDetail;
	        		}//end of else if(strFlag.equals(strPED))
	        		else if(strTab.equals("Medical"))
	        		{
	        			strPEDDetail=strCodePreauthMedical;
	        		}//end of else if(strTab.equals("Medical"))		            
	        	}//end of if(strActiveSubLink.equals("PreAuth"))
	        	else*/ if(strActiveSubLink.equals("Claims")){
	        		if(strFlag.equals(strICDPCS))
	        		{
	        			strPEDDetail=strCodeClaimsICDPcs;
	        		}//end of if(strFlag.equals(strICDPCS))
	        		else if(strFlag.equals(strPED))
	        		{
	        			strPEDDetail=strCodeClaimsPedDetail;
	        		}//end of else if(strFlag.equals(strPED))
	        		else if(strTab.equals("Medical"))
	        		{
	        			strPEDDetail=strCodeClaimsMedical;
	        		}//end of else if(strTab.equals("Medical"))
	        	}//end of else if(strActiveSubLink.equals("Claims"))
	        }
	        else if(strActiveLink.equals("Code CleanUp"))
			{
				if(strActiveSubLink.equals("PreAuth")){
					if(strFlag.equals(strICDPCS))
					{
		                strPEDDetail=strCodeCleanupPreauthIcdPcs;
					}//end of if(strFlag.equals(strICDPCS))
		            else if(strFlag.equals(strPED))
		            {
		                strPEDDetail=strCodeCleanupPreauthPedDetail;
		            }//end of else if(strFlag.equals(strPED))
				}//end of if(strActiveSubLink.equals("PreAuth"))
				else if(strActiveSubLink.equals("Claims")){
					if(strFlag.equals(strICDPCS))
					{
		                strPEDDetail=strCodeCleanupClaimsICDPcs;
					}//end ofif(strFlag.equals(strICDPCS))
		            else if(strFlag.equals(strPED))
		            {
		                strPEDDetail=strCodeCleanupClaimsPedDetail;
		            }//end of else if(strFlag.equals(strPED))
				}//end of else if(strActiveSubLink.equals("Claims"))
			}//end of else if(strActiveLink.equals("Code CleanUp"))
			// Added for ICD Screen implementation detail : UNNI V MANA 17-05-2008
	        else if("Maintenance".equals(strActiveLink) && "ICD Code".equals(strActiveSubLink))
			{
	        	strPEDDetail = strICD;
			}//end of else if("Maintenance".equals(strActiveLink) && "ICD Code".equals(strActiveSubLink))
			// End of Addition
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "icdlist");
        }//end of catch
		return strPEDDetail;
	}//end of getPEDDetailPath(HttpServletRequest request,String strFlag)

    /**
     * This method will add search criteria fields and values to the arraylist and will return it
     * @param frmICDList current instance of form bean
     * @param request HttpServletRequest
     * @return alSearchObjects ArrayList of search params
     * @throws TTKException
     */
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmICDList,HttpServletRequest request)
    {
        log.debug("PolicyAction.......... Inside populateSearchCriteria");
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchObjects = new ArrayList<Object>();
        alSearchObjects.add(TTKCommon.replaceSingleQots((String)frmICDList.getString("sICDCode")));
        alSearchObjects.add(TTKCommon.replaceSingleQots((String)frmICDList.getString("sICDName")));
        alSearchObjects.add(TTKCommon.getUserSeqId(request));
        return alSearchObjects;
    }//end of populateSearchCriteria()

    /**
     * Returns the ParallelMemberManager session object for invoking methods on it.
     * @return ParallelMemberManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private ParallelMemberManager getMemberManager() throws TTKException
    {
        ParallelMemberManager memberManager = null;
        try
        {
            if(memberManager == null)
            {
                InitialContext ctx = new InitialContext();
                memberManager = (ParallelMemberManager) ctx.lookup("java:global/TTKServices/business.ejb3/ParallelMemberManagerBean!com.ttk.business.dataentryenrollment.ParallelMemberManager");
            }//end if
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "memberdetail");
        }//end of catch
        return memberManager;
    }//end getMemberManager()      

}//end of ICDListAction