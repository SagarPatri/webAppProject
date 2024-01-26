/**
* @ (#) BrokerCompanyAction.java Nov 21, 2005
* Project 		: TTK HealthCare Services
* File 			: BrokerCompanyAction.java
* Author 		: Pradeep R
* Company 		: Span Systems Corporation
* Date Created 	: Nov 21, 2005
*
* @author 		: Pradeep R
* Modified by 	: Lancy A
* Modified date : Mar 10, 2006
* Reason 		: Changes in coding standards
*/

package com.ttk.action.empanelment;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ibm.icu.text.SimpleDateFormat;
import com.ttk.action.TTKAction; 
import com.ttk.action.table.TableData;
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.business.empanelment.BrokerManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.BrokerDetailVO;
import com.ttk.dto.empanelment.BrokerVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;
import java.text.*; 
/**
* This class is used to search the insurance company,add
* that to webboard. and also has the mode add/edit and delete the
* insurance companies at various level.
*/

public class BrokerCompanyAction extends TTKAction {

	private static Logger log = Logger.getLogger( BrokerCompanyAction.class );

    //declaration of modes
    private static final String strForward="Forward";
    private static final String strBackward="Backward";
    
    //declaration of forward paths
    private static final String strBrokerList="brokerlist";
    private static final String strCompanySummary="brokersummary";
    private static final String strBrocompanyDetail="editbrocompany";
       
    //Exception Message Identifier
    private static final String strBroError="broker";
    
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
    		log.debug("Inside the doDefault method of BrokerCompanyAction");
    		setLinks(request);
    		TableData tableData = TTKCommon.getTableData(request);
    		//remove the selected office from the session
            request.getSession().removeAttribute("SelectedOffice");
            tableData = new TableData();//create new table data object
            //create the required grid table
            tableData.createTableInfo("BrokerNewCompanyTable",new ArrayList());
            request.getSession().setAttribute("tableData",tableData);
            ((DynaActionForm)form).initialize(mapping);//reset the form data
            return this.getForward(strBrokerList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroError));
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
    		log.debug("Inside the doSearch method of BrokerCompanyAction");
    		setLinks(request);
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
            TableData tableData = TTKCommon.getTableData(request);
            BrokerManager brokerObject=(BrokerManager)this.getBrokerManagerObject();
            //if the page number or sort id is clicked
            if(!strPageID.equals("") || !strSortID.equals(""))
            {
            	if(!strPageID.equals(""))
                {
            		tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
                    return mapping.findForward(strBrokerList);
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
                tableData.createTableInfo("BrokerNewCompanyTable",null);
                tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
                tableData.modifySearchData("search");
            }//end of else
            ArrayList alCompanies= brokerObject.getBrokerCompanyList(tableData.getSearchData());
            tableData.setData(alCompanies, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strBrokerList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroError));
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
    		log.debug("Inside the doBackward method of BrokerCompanyAction");
    		setLinks(request);
    		TableData tableData = TTKCommon.getTableData(request);
            BrokerManager brokerObject=(BrokerManager)this.getBrokerManagerObject();
    		tableData.modifySearchData(strBackward);//modify the search data
            ArrayList alCompanies = brokerObject.getBrokerCompanyList(tableData.getSearchData());
            tableData.setData(alCompanies, strBackward);//set the table data
            request.getSession().setAttribute("tableData",tableData);//set the table data object to session
            return this.getForward(strBrokerList, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroError));
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
    		log.debug("Inside the doForward method of BrokerCompanyAction");
    		setLinks(request);
    		TableData tableData = TTKCommon.getTableData(request);
            BrokerManager brokerObject=(BrokerManager)this.getBrokerManagerObject();
    		tableData.modifySearchData(strForward);//modify the search data
            ArrayList alCompanies = brokerObject.getBrokerCompanyList(tableData.getSearchData());
            tableData.setData(alCompanies, strForward);//set the table data
            request.getSession().setAttribute("tableData",tableData);//set the table data object to session
            return this.getForward(strBrokerList, mapping, request);//finally return to the grid screen
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroError));
		}//end of catch(Exception exp)
    }//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside the doCopyToWebBoard method of BrokerCompanyAction");
    		setLinks(request);
    		this.populateWebBoard(request);
			return this.getForward(strBrokerList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroError));
		}//end of catch(Exception exp)
    }//end of doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to copy the selected records to web-board and navigate the page to Company Details.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewCompanySummary(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doViewCompanySummary method of BrokerCompanyAction");
    		setLinks(request);
    		TableData tableData = TTKCommon.getTableData(request);
    		DynaActionForm frmSearchBroker=(DynaActionForm)form;
    		BrokerVO brokerVO=null;
            if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
            	brokerVO = (BrokerVO)tableData.getRowInfo(Integer.parseInt((String)(frmSearchBroker).
                			   get("rownum")));
                //add the selected item to the web board and make it as default selected
                this.addToWebBoard(brokerVO, request);
            }//end if()
            return mapping.findForward(strCompanySummary);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroError));
		}//end of catch(Exception exp)
    }//end of doViewCompanySummary(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside the doAdd method of BrokerCompanyAction");
    		setLinks(request);
    		DynaActionForm frmBroCompanyDetails = (DynaActionForm)form;
            BrokerDetailVO broDetailVO=new BrokerDetailVO();
			AddressVO addressVO=new AddressVO();
            StringBuffer sbfCaption=new StringBuffer();
            UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
            										 getAttribute("UserSecurityProfile");
            ArrayList alCityList = new ArrayList();
            //load the Hashmap which will consists of office type to be added
            //based on the present office selected.
            HashMap<Object,Object> hmOfficeType=new HashMap<Object,Object>();
            hmOfficeType.put("IHO","IRO");
            hmOfficeType.put("IRO","IDO");
            hmOfficeType.put("IDO","IBO");
            sbfCaption.append("Add");
            //get the parentcompany's information to add child company
            //contains the information of the selected office
            BrokerVO brokerVO = (BrokerVO)request.getSession().getAttribute("SelectedOffice"); 
            //returns the information of the head office
            BrokerVO headOfficeVO=(BrokerVO)request.getSession().getAttribute("HeadOfficeBro"); 
            addressVO.setCountryCode("123");
			addressVO.setStateCode("DXB");
			broDetailVO.setAddress(addressVO);
            if(brokerVO!=null && headOfficeVO!=null)//for adding regional,divisional and branch offices
            {
            	
                broDetailVO.setParentInsSeqID(brokerVO.getInsuranceSeqID());
                broDetailVO.setOfficeType((String)hmOfficeType.get(brokerVO.getOfficeType()));
                broDetailVO.setRegAuthority(headOfficeVO.getRegAuthority());
                broDetailVO.setCompanyName(headOfficeVO.getCompanyName());
                broDetailVO.setCompanyCode(headOfficeVO.getsCompanyCode());
                broDetailVO.setSectorTypeCode(headOfficeVO.getSectorTypeCode());
                broDetailVO.setCompanyAbbreviation(headOfficeVO.getCompanyAbbreviation());
                broDetailVO.setSectorTypeDesc(headOfficeVO.getSectorTypeDesc());
                sbfCaption.append(" [").append(brokerVO.getBranchName()).append("]");
            }//end of if(BrokerVO!=null)
            else                        //for adding head office set office type as IHO
            {
                broDetailVO.setOfficeType("IHO");
            }//end of else
            broDetailVO.setCompanyStatus("Y");  //in add mode set the insurance office as active by default
            frmBroCompanyDetails=this.setFormValues(broDetailVO,mapping,request);
            frmBroCompanyDetails.set("caption",sbfCaption.toString());//set the caption to displayed in jsp page
            //changebroker
            /*if(userSecurityProfile.getBranchID()!=null){
            	frmBroCompanyDetails.set("TTKBranchCode", userSecurityProfile.getBranchID().toString());
            }//end of if(userSecurityProfile.getBranchID()!=null)
            else{
            	frmBroCompanyDetails.set("TTKBranchCode", "");
            }//end of else
         */          
            frmBroCompanyDetails.set("alCityList",alCityList);
			SimpleDateFormat dateFormate = new SimpleDateFormat("dd/MM/yyyy");
			String date=dateFormate.format(new java.util.Date());
           
			frmBroCompanyDetails.set("empanelmentDate", date);
            request.getSession().setAttribute("frmBroCompanyDetails",frmBroCompanyDetails);
            return this.getForward(strBrocompanyDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroError));
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
    		log.debug("Inside the doEdit method of BrokerCompanyAction");
    		setLinks(request);
    		DynaActionForm frmBroCompanyDetails = (DynaActionForm)form;
    		BrokerDetailVO broDetailVO=null;
            StringBuffer sbfCaption=new StringBuffer();
            BrokerManager brokerObject=(BrokerManager)this.getBrokerManagerObject();
            HospitalManager hospitalObject=this.getHospitalManagerObject();
            HashMap hmCityList = null;
            /*UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
            										 getAttribute("UserSecurityProfile");*/
            ArrayList alCityList = null;
            ArrayList<Object> albroList = null;
            Long lngHeadOffInsSeqId=null;
            //initialize the form bean
            frmBroCompanyDetails.initialize(mapping);
            sbfCaption.append("Edit");
            //get the head off seq id from webboard
            lngHeadOffInsSeqId=new Long(TTKCommon.getWebBoardId(request));//get the web board id

            //get the minimum info from the session to get all the details of company to edit
            // get the minimum info of the selected office to edited
            BrokerVO  brokerVO = (BrokerVO)request.getSession().getAttribute("SelectedOffice");

            //call the DAO to get all details from the minimum info
            broDetailVO=brokerObject.getBrokerCompanyDetail(lngHeadOffInsSeqId,brokerVO.getInsuranceSeqID());
            hmCityList=hospitalObject.getCityInfo();
            albroList = hospitalObject.getBrokerList(broDetailVO.getRegAuthority());
			if(hmCityList!=null){
				alCityList = (ArrayList)hmCityList.get(broDetailVO.getAddress().getStateCode());
			}//end of if(hmCityList!=null)
				
			//set the form bean from the VO
            frmBroCompanyDetails=this.setFormValues(broDetailVO,mapping,request);
            if(!broDetailVO.getOfficeType().equals("IHO")){
            	sbfCaption.append(" [").append(broDetailVO.getCompanyName()).append("-").
            	append(broDetailVO.getCompanyCode()).append("]");
            }//end of if(!broDetailVO.getOfficeType().equals("IHO"))
            frmBroCompanyDetails.set("caption",sbfCaption.toString());
            frmBroCompanyDetails.set("alCityList",alCityList);
           
            
            if(!broDetailVO.getOfficeType().equals("IHO")){
            	
            	frmBroCompanyDetails.set("companyName",broDetailVO.getCompanyName());
            }
            else{
               frmBroCompanyDetails.set("alBrokerList",albroList);
            	frmBroCompanyDetails.set("companyName",broDetailVO.getCompanyCode());
            }
            
            //frmBroCompanyDetails.set("companyName",broDetailVO.getCompanyCode());
            request.getSession().setAttribute("frmBroCompanyDetails",frmBroCompanyDetails);
            return this.getForward(strBrocompanyDetail, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroError));
		}//end of catch(Exception exp)
    }//end of doEdit(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside the doSave method of BrokerCompanyAction");
    		setLinks(request);
    		BrokerManager brokerObject=(BrokerManager)this.getBrokerManagerObject();
			HospitalManager hospitalObject=this.getHospitalManagerObject();
			HashMap hmCityList = null;
            DynaActionForm frmBroCompanyDetails = (DynaActionForm)form;
            BrokerDetailVO broDetailVO=new BrokerDetailVO();
            ArrayList alCityList = new ArrayList();
            ArrayList<Object> albroList = null;
			Long lHeadOffInsSeqId=null;
			StringBuffer sbfCaption=new StringBuffer(); 
			HashMap<Object,Object> hmOfficeType=new HashMap<Object,Object>();
			hmOfficeType.put("IHO","IRO");
	        hmOfficeType.put("IRO","IDO");
	        hmOfficeType.put("IDO","IBO");
            //prepare the vo to add/edit the compnay
            broDetailVO=this.getFormValues(frmBroCompanyDetails,mapping,request);
            //call the DAO method to update the Compnay
            Long lInsSeqId=brokerObject.addUpdateInsuranceCompany(broDetailVO);
            if(lInsSeqId>0)
            {
                if(broDetailVO.getInsuranceSeqID()==null)    //executed when office is added
                {
                    String strCompanyName=broDetailVO.getCompanyName();
                    //initialize the form bean and InsuranceDetailVO
                    frmBroCompanyDetails.initialize(mapping);
                    sbfCaption.append("Add");
                    broDetailVO=new BrokerDetailVO();
                    //get the parentcompany's information to add child company
                    //get the selected office info
                    BrokerVO brokerVO = (BrokerVO)request.getSession().getAttribute("SelectedOffice");
                    //get the head office info
                    BrokerVO headOfficeVO=(BrokerVO)request.getSession().getAttribute("HeadOfficeBro");
                    
                   
                    
                    //for adding regional,divisional and branch offices
                    if(brokerVO!=null && headOfficeVO!=null)      
                    {
                    	broDetailVO.setParentInsSeqID(brokerVO.getInsuranceSeqID());
                        broDetailVO.setOfficeType((String)hmOfficeType.get(brokerVO.getOfficeType()));
                        broDetailVO.setCompanyName(headOfficeVO.getCompanyName());
                        broDetailVO.setSectorTypeCode(headOfficeVO.getSectorTypeCode());
                        broDetailVO.setSectorTypeDesc(headOfficeVO.getSectorTypeDesc());
                        broDetailVO.setCompanyAbbreviation(headOfficeVO.getCompanyAbbreviation());
                        broDetailVO.setBranchName(headOfficeVO.getBranchName());
                        sbfCaption.append(" [").append(brokerVO.getBranchName()).append("]");
                    }//end of if(BrokerVO!=null)
                    else
                    {
                        //to add the head office to web board and refresh the cache
                    	brokerVO=new BrokerVO();
                    	brokerVO.setInsuranceSeqID(lInsSeqId);
                    	brokerVO.setCompanyName(strCompanyName);
                        this.addToWebBoard(brokerVO,request);
                        //refresh the cache so that changes are seen in other screens
                        Cache.refresh("brokerCompany");       
                        return mapping.findForward(strCompanySummary);
                    }//end of else
                    request.setAttribute("updated","message.addedSuccessfully");
                }//end of if(frmCompanyDetails.get("insCompSeqId").equals(""))
                else
                {
                    //get the head off seq id from webboard
                    lHeadOffInsSeqId=new Long(TTKCommon.getWebBoardId(request));//get the web board id

                    //reload the form after updating in edit mode
                    broDetailVO=brokerObject.getBrokerCompanyDetail(lHeadOffInsSeqId,lInsSeqId);
                    //this.valueObjectToForm(broDetailVO,frmCompanyDetails,request);
                    sbfCaption.append("Edit");
                    if(broDetailVO.getOfficeType().equals("IHO"))
                    {
                        Cache.refresh("alBrokerList");
                        request.setAttribute("cacheId",String.valueOf(lInsSeqId));
                        request.setAttribute("cacheDesc",broDetailVO.getCompanyName());
                        // to modify the changes in webboard for chenges made here
                        TTKCommon.modifyWebBoardId(request);     
                    }//end of if(broDetailVO.getOfficeType().equals("IHO"))
                    else
                    {
                        //if office type is other than the Head office add office info to the caption
                        sbfCaption.append(" [").append(broDetailVO.getCompanyName()).append("-").append(
                        										broDetailVO.getCompanyCode()).append("]");
                    }//end of else
                    
                    
                    hmCityList=hospitalObject.getCityInfo();
                    albroList = hospitalObject.getBrokerList(broDetailVO.getRegAuthority());
                    if(hmCityList!=null)
                    		alCityList = (ArrayList)hmCityList.get(broDetailVO.getAddress().getStateCode());
                    request.setAttribute("updated","message.savedSuccessfully");
                }//end of else
                frmBroCompanyDetails=this.setFormValues(broDetailVO,mapping,request);
                frmBroCompanyDetails.set("caption",sbfCaption.toString());
                frmBroCompanyDetails.set("alCityList",alCityList);
                frmBroCompanyDetails.set("companyName",broDetailVO.getCompanyCode());
                frmBroCompanyDetails.set("alBrokerList",albroList);
                request.getSession().setAttribute("frmBroCompanyDetails",frmBroCompanyDetails);
            }//end of if(lInsSeqId>0)
            return this.getForward(strBrocompanyDetail,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroError));
		}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    /**
     * This method is used to load cities based on the selected state.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doChangeState(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doChangeState method of BrokerCompanyAction");
    		setLinks(request);
    		DynaActionForm frmInsCompanyDetails = (DynaActionForm)form;
    		BrokerDetailVO broDetailVO=null;
    		HospitalManager hospitalObject=this.getHospitalManagerObject();
            HashMap hmCityList = null;
            ArrayList alCityList = null;
    		broDetailVO=this.getFormValues(frmInsCompanyDetails,mapping,request);
    		hmCityList=hospitalObject.getCityInfo(broDetailVO.getAddress().getStateCode());
    		String countryCode="";
    		int isdcode=0;
    		int stdcode=0;
    		if(hmCityList!=null){
    		 countryCode	=	(String)(TTKCommon.checkNull(hmCityList.get("CountryId")));
             isdcode	=	TTKCommon.getInt((String)(hmCityList.get("isdcode")));
             stdcode	=	TTKCommon.getInt((String)(hmCityList.get("stdcode")));
    		}
            
    		if(hmCityList!=null){
    			alCityList = (ArrayList)hmCityList.get(broDetailVO.getAddress().getStateCode());
    		}//end of if(hmCityList!=null)
    		if(alCityList==null){
    			alCityList=new ArrayList();
    		}//end of if(alCityList==null)
    		
    		if(broDetailVO.getAddress()!=null)
            {
    			broDetailVO.getAddress().setCountryCode(countryCode);
    			broDetailVO.getAddress().setIsdCode(isdcode);
    			broDetailVO.getAddress().setStdCode(stdcode);
    			frmInsCompanyDetails.set("addressVO", (DynaActionForm)FormUtils.setFormValues("frmBroCompanyAddress",
           			broDetailVO.getAddress(),this,mapping,request));
            }//end of if(insDetailVO.getAddress()!=null)
    		
    		frmInsCompanyDetails.set("frmChanged","changed");
    		broDetailVO.getAddress().setCountryCode(countryCode);
    		/*frmInsCompanyDetails.set("isdCode",isdcode);
    		frmInsCompanyDetails.set("stdCode",stdcode);*/
    		
    		
    		 ArrayList<Object> albroList = null;
    		CacheObject cacheObject = null;
    		Connection con=null;
    		PreparedStatement statement=null;
    		ResultSet resultSet=null;
    		String brokerAuthority=broDetailVO.getRegAuthority();
    	    brokerAuthority=(brokerAuthority==null)?"0":brokerAuthority.trim();
    	    String query = "SELECT BRK_ID,COMPANY_NAME  FROM APP.DHA_INS_BROKER_MASTER WHERE AUTHORITY=? ORDER BY COMPANY_NAME";
    	    try {
    	    	albroList = new ArrayList<Object>();
    //	    	System.out.println("Query for broker..."+query);
   // 	    	System.out.println("brokerAuthority.."+brokerAuthority);
    	    	 con=ResourceManager.getConnection();
    	    		statement=con.prepareStatement(query);
    	    		statement.setString(1, brokerAuthority);
    	    		  resultSet=statement.executeQuery();
    	    		 
    	    		    HashMap<String,String> alBrokerList=new  HashMap<String,String>();
    	    		    if(resultSet!=null){
    	    	       while(resultSet.next()){
    	    	    	   cacheObject = new CacheObject();
    	    	    		cacheObject.setCacheId(TTKCommon.checkNull(resultSet.getString("BRK_ID")));
    						cacheObject.setCacheDesc(TTKCommon.checkNull(resultSet.getString("COMPANY_NAME")));
    						albroList.add(cacheObject);
    	    	    	 
    	    	    	 /*  alBrokerList.put(id, brokerCompanyName);*/
    	    	    	 /*  array.addAll(alBrokerList.entrySet());*/
    	    	       }
    	    		    }
    	    		   
			} catch (Exception exception) {
				log.error(exception.getMessage(), exception);
			}
    	    finally{
    			if(resultSet!=null)resultSet.close();
    			if(statement!=null)statement.close();
    			if(con!=null)con.close();			
    		 }
    				
    	    frmInsCompanyDetails.set("alBrokerList",albroList);
    		frmInsCompanyDetails.set("alCityList",alCityList);
    		
    		return this.getForward(strBrocompanyDetail,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroError));
		}//end of catch(Exception exp)
    }//end of doChangeState(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside the doClose method of BrokerCompanyAction");
    		setLinks(request);
    		return this.getForward(strBrokerList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroError));
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
    		log.debug("Inside the doDeleteList method of BrokerCompanyAction");
    		setLinks(request);
    		BrokerManager brokerObject=(BrokerManager)this.getBrokerManagerObject();
    		TableData tableData = TTKCommon.getTableData(request);
    		String strDeleteId = "";
            //populate the delete string which contains the sequence id's to be deleted
            strDeleteId = populateDeleteId(request, (TableData)request.getSession().getAttribute("tableData"));
            //delete the Insurance Details
            int iCount = brokerObject.deleteInsuranceCompany(strDeleteId);
            //refresh the grid with search data in session
            ArrayList alInsurance = null;
            //fetch the data from previous set of rowcounts, if all the records are deleted for the current set of search criteria
            if(iCount == tableData.getData().size())
            {
            	tableData.modifySearchData("DeleteList");//modify the search data
                int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().get(tableData.
                					 getSearchData().size()-2));
                if(iStartRowCount > 0)
                {
                	alInsurance = brokerObject.getBrokerCompanyList(tableData.getSearchData());
                }//end of if(iStartRowCount > 0)
            }//end if(iCount == tableData.getData().size())
            else
            {
            	alInsurance = brokerObject.getBrokerCompanyList(tableData.getSearchData());
            }//end of else
            tableData.setData(alInsurance, "DeleteList");
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward(strBrokerList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroError));
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
    		log.debug("Inside the doDelete method of BrokerCompanyAction");
    		setLinks(request);
    		BrokerManager brokerObject=(BrokerManager)this.getBrokerManagerObject();
    		TableData tableData = TTKCommon.getTableData(request);
    		String strDeleteId = "";
            strDeleteId = ""+((BrokerVO)((TableData)request.getSession().getAttribute("tableData")).
            			  getData().get(Integer.parseInt(request.getParameter("rownum")))).getInsuranceSeqID();
            //delete the Insurance Details
            int iCount = brokerObject.deleteInsuranceCompany(strDeleteId);
            //refresh the grid with search data in session
            ArrayList alInsurance = null;
            //fetch the data from previous set of rowcounts, if all the records are deleted for the current set of search criteria
            if(iCount == tableData.getData().size())
            {
            	tableData.modifySearchData("Delete");//modify the search data
                int iStartRowCount = Integer.parseInt((String)tableData.getSearchData().
                					 get(tableData.getSearchData().size()-2));
                if(iStartRowCount > 0)
                {
                	alInsurance = brokerObject.getBrokerCompanyList(tableData.getSearchData());
                }//end of if(iStartRowCount > 0)
            }//end if(iCount == tableData.getData().size())
            else
            {
            	alInsurance = brokerObject.getBrokerCompanyList(tableData.getSearchData());
            }//end of else
            tableData.setData(alInsurance, "Delete");
            request.getSession().setAttribute("tableData",tableData);
            return this.getForward(strBrokerList, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strBroError));
		}//end of catch(Exception exp)
    }//end of doDelete(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * Returns a string which contains the comma separated sequence id's to be deleted
     * @param request HttpServletRequest object which contains information about the selected check boxes
     * @param tableData TableData object which contains the value objects
     * @return String which contains the comma separated sequence id's to be deleted
     * @throws TTKException If any run time Excepton occures
     */
    private String populateDeleteId(HttpServletRequest request, TableData tableData)throws TTKException
    {
        String[] strChk = request.getParameterValues("chkopt");
        StringBuffer sbfDeleteId = new StringBuffer();
        if(strChk!=null&&strChk.length!=0)
        {
        	for(int i=0; i<strChk.length;i++)
            {
                if(strChk[i]!=null)
                {
                    //extract the sequence id to be deleted from the value object
                    if(i == 0)
                    {
                        sbfDeleteId.append("|").append(String.valueOf(((BrokerVO)tableData.getData().
                        			get(Integer.parseInt(strChk[i]))).getInsuranceSeqID().intValue()));
                    }//end of if(i == 0)
                    else
                    {
                        sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((BrokerVO)tableData.
                        			  getData().get(Integer.parseInt(strChk[i]))).getInsuranceSeqID().intValue()));
                    }//end of else
                }//end of if(strChk[i]!=null)
            }//end of for(int i=0; i<strChk.length;i++)
            sbfDeleteId=sbfDeleteId.append("|");
        }//end of if(strChk!=null&&strChk.length!=0)
        return sbfDeleteId.toString();
    }//end of populateDeleteId(HttpServletRequest request, TableData tableData)

    /**
     * Returns the BrokerManager session object for invoking methods on it.
     * @return BrokerManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private BrokerManager getBrokerManagerObject() throws TTKException
	{
		BrokerManager bromanager = null;
		try
		{
			if(bromanager == null)
			{
				InitialContext ctx = new InitialContext();
				bromanager = (BrokerManager) ctx.lookup("java:global/TTKServices/business.ejb3/BrokerManagerBean!com.ttk.business.empanelment.BrokerManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strBroError);
		}//end of catch
		return bromanager;
	} // end of private BrokerManager getInsManagerObject() throws TTKException

    /**
	 * Returns the HospitalManager session object for invoking methods on it.
	 * @return HospitalManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private HospitalManager getHospitalManagerObject() throws TTKException
	{
		HospitalManager hospManager = null;
		try
		{
			if(hospManager == null)
			{
				InitialContext ctx = new InitialContext();
				hospManager = (HospitalManager) ctx.lookup("java:global/TTKServices/business.ejb3/HospitalManagerBean!com.ttk.business.empanelment.HospitalManager");
			}//end if(hospManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strBroError);
		}//end of catch
		return hospManager;
	}//end getHospitalManagerObject()

    /**
     * Return the ArrayList populated with Search criteria elements
     * @param DynaActionForm will contain the values that are entered in the corresponding fields
     * @param HttpServletRequest object will contain the searchparameter that is built.
     * @return ArrayList
     * @throws TTKException If any run time Excepton occures
     */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSearchBroker,
			HttpServletRequest request)throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(new SearchCriteria("EMPANELED_DATE", (String)frmSearchBroker.get("sEmpanelDate")));
		alSearchParams.add(new SearchCriteria("INS_COMP_NAME",TTKCommon.replaceSingleQots((String) frmSearchBroker.get("sCompanyName"))));
		/*AK*/
		alSearchParams.add(new SearchCriteria("INS_COMP_CODE_NUMBER",TTKCommon.replaceSingleQots((String) frmSearchBroker.get("sCompanyCode"))));
		alSearchParams.add(new SearchCriteria("BROKER_AUTHORITY",TTKCommon.replaceSingleQots((String) frmSearchBroker.get("regAuthority"))));
		/*END*/
    	return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm userForm)
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
		if(strChk!=null&&strChk.length!=0)
		{
			for(int i=0; i<strChk.length;i++)
			{
				cacheObject = new CacheObject();
				cacheObject.setCacheId(String.valueOf(((BrokerVO)tableData.getData().
						get(Integer.parseInt(strChk[i]))).getInsuranceSeqID()));
				cacheObject.setCacheDesc(((BrokerVO)tableData.getData().get(Integer.parseInt(strChk[i]))).
						getCompanyName());
				alCacheObject.add(cacheObject);
			}//end of for(int i=0; i<strChk.length;i++)
		}//end of if(strChk!=null&&strChk.length!=0)
		if(toolbar != null)
			toolbar.getWebBoard().addToWebBoardList(alCacheObject);
	}//end of populateWebBoard(HttpServletRequest request)

    /**
     * Adds the selected item to the web board and makes it as the selected item in the web board
     * @param  BrokerVO BrokerVO object which contains the information of the Insurance Office
     * @param request HttpServletRequest
     * @throws TTKException if any runtime exception occures
     */
    private void addToWebBoard(BrokerVO brokerVO, HttpServletRequest request)throws TTKException
    {
        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
        CacheObject cacheObject = new CacheObject();
        cacheObject.setCacheId(String.valueOf(brokerVO.getInsuranceSeqID()));
        cacheObject.setCacheDesc(brokerVO.getCompanyName());
        ArrayList<Object> alCacheObject = new ArrayList<Object>();
        alCacheObject.add(cacheObject);
        //if the object(s) are added to the web board, set the current web board id
        toolbar.getWebBoard().addToWebBoardList(alCacheObject);
        toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
        request.setAttribute("webboardinvoked", "true");
    }//end of addToWebBoard(InsuranceDetailVO broDetailVO, HttpServletRequest request)

    /**
     * Populates the value object to form element.
     * @param broDetailVO InsuranceDetailVO object.
     * @param mapping The ActionMapping used to select this instance.
     * @param request HttpServletRequest  current request object
     * @return DynaActionForm object.
     */
    private DynaActionForm setFormValues(BrokerDetailVO broDetailVO,ActionMapping mapping,
    		HttpServletRequest request) throws TTKException
    {
        try
        {
            DynaActionForm frmBroCompanyDetails = (DynaActionForm)FormUtils.setFormValues("frmBroCompanyDetails",
            									   broDetailVO,this,mapping,request);
            if(broDetailVO.getAddress()!=null)
            {
            	frmBroCompanyDetails.set("addressVO", (DynaActionForm)FormUtils.setFormValues("frmBroCompanyAddress",
                		broDetailVO.getAddress(),this,mapping,request));
            }//end of if(broDetailVO.getAddress()!=null)
            else
            {
            	frmBroCompanyDetails.set("addressVO", (DynaActionForm)FormUtils.setFormValues("frmBroCompanyAddress",
                		new AddressVO(),this,mapping,request));
            }//end of else
            return frmBroCompanyDetails;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strBroError);
        }//end of catch
    }//end of setFormValues()

    /**
     * Populates the form elements to value object .
     * @param frmInsCompanyDetails DynaActionForm.
     * @param mapping The ActionMapping used to select this instance.
     * @param request HttpServletRequest  cuurent request object
     * @return broDetailVO InsuranceDetailVO object.
     */
    private BrokerDetailVO getFormValues(DynaActionForm frmBroCompanyDetails,ActionMapping mapping,
    		HttpServletRequest request) throws TTKException
    {
        try
        {
        	BrokerDetailVO broDetailVO=null;
            AddressVO addressVO=null;
            if(request.getParameter("companyStatus")==null)
            {
                frmBroCompanyDetails.set("companyStatus","N");
            }//end of if(request.getParameter("companyStatus")==null)
            broDetailVO=(BrokerDetailVO)FormUtils.getFormValues(frmBroCompanyDetails,"frmBroCompanyDetails",
            		this,mapping,request);
            ActionForm addressForm=(ActionForm)frmBroCompanyDetails.get("addressVO");
            addressVO=(AddressVO)FormUtils.getFormValues(addressForm,"frmBroCompanyAddress",this,mapping,request);
            broDetailVO.setAddress(addressVO);
            broDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            return broDetailVO;
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "broker");
        }//end of catch
    }//end of getFormValues()
} //end of BrokerCompanyAction