package com.ttk.action.empanelment;



import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.nt.NTEventLogAppender;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.empanelment.ContactManager;
import com.ttk.business.empanelment.PartnerManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.empanelment.AddressVO;
import com.ttk.dto.empanelment.DocumentDetailVO;
import com.ttk.dto.empanelment.NetworkTypeVO;
import com.ttk.dto.empanelment.PartnerAuditVO;
import com.ttk.dto.empanelment.PartnerDetailVO;
import com.ttk.dto.empanelment.PartnerVO;
import com.ttk.dto.empanelment.PreRequisiteVO;
import com.ttk.dto.usermanagement.ContactVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;


public class PartnerSearchAction extends TTKAction {
	
	  private static final String strPartnerlist="partnerlist";
	  private static final String strPtnrSearch="partnersearch";
	    private static final String strEditpartner="editpartner";
	  
	  
	 public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	    		HttpServletResponse response) throws Exception{
	    	try{
	    		log.debug("Inside the doDefault method of PartnerSearchAction");
	    		setLinks(request);
	    		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
	            	((DynaActionForm)form).initialize(mapping);//reset the form data
	            }//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
	    		TableData tableData = TTKCommon.getTableData(request);
	    		String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)request.getSession().
	    				getAttribute("UserSecurityProfile")).getBranchID());
	    		DynaActionForm generalForm = (DynaActionForm)form;
	    		//create new table data object
	    		tableData = new TableData();
	    		//create the required grid table
	    		tableData.createTableInfo("PartnerSearchTable",new ArrayList());
	    		request.getSession().setAttribute("tableData",tableData);
	    		request.getSession().setAttribute("alSubStatus",null);
	    		generalForm.set("officeInfo",strDefaultBranchID);
	    		TTKCommon.documentViewer(request);
	    		return this.getForward(strPartnerlist, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strPtnrSearch));
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
	    		log.debug("Inside the doSearch method of PartnerSearchAction");
	    		if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
	            	((DynaActionForm)form).initialize(mapping);//reset the form data
	            }//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
	    		PartnerManager partnerObject=this.getPartnerManagerObject();
	    		TableData tableData = TTKCommon.getTableData(request);
	    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
	    		String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
	    		if(!strPageID.equals("") || !strSortID.equals(""))
	    		{
	    			if(!strPageID.equals(""))
	    			{
	    				tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
	    				return (mapping.findForward("partnerlist"));
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
	    			tableData.createTableInfo("PartnerSearchTable",null);
	    			//fetch the data from the data access layer and set the data to table object
	    			tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form));
	    			tableData.modifySearchData("search");
	    		}//end of else
	    		ArrayList alPartner=partnerObject.getPartnerList(tableData.getSearchData());
	    		tableData.setData(alPartner, "search");
	    		request.getSession().setAttribute("tableData",tableData);
	    		//finally return to the grid screen
	    		return this.getForward(strPartnerlist, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strPtnrSearch));
			}//end of catch(Exception exp)
	    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	    
	    
	    /**
	     * Returns the HospitalManager session object for invoking methods on it.
	     * @return HospitalManager session object which can be used for method invokation
	     * @exception throws TTKException
	     */
	    private PartnerManager getPartnerManagerObject() throws TTKException
	    {
	    	   PartnerManager ptnrManager = null;
	           try
	           {
	               if(ptnrManager == null)
	               {
	                   InitialContext ctx = new InitialContext();
	                   ptnrManager = (PartnerManager) ctx.lookup("java:global/TTKServices/business.ejb3/PartnerManagerBean!com.ttk.business.empanelment.PartnerManager");
	               }//end if(hospManager == null)
	           }//end of try
	           catch(Exception exp)
	           {
	               throw new TTKException(exp,strPtnrSearch);
	           }//end of catch
	           return ptnrManager;
	    }//end getHospitalManagerObject()
	    
	    
	    /**
	     * Returns the ArrayList which contains the populated search criteria elements
	     * @param frmSearchHospital DynaActionForm will contains the values of corresponding fields
	     * @param request HttpServletRequest object which contains the search parameter that is built
	     * @return alSearchParams ArrayList
	     */
	    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSearchPartner)
	    {
	        //build the column names along with their values to be searched
	    	ArrayList<Object> alSearchParams = new ArrayList<Object>();
			alSearchParams.add(new SearchCriteria("EMPANELED_DATE", (String)frmSearchPartner.get("sEmpanelDate")));
			alSearchParams.add(new SearchCriteria("PARTNER_NAME",TTKCommon.replaceSingleQots((String)
					frmSearchPartner.get("sPartnerName"))));
	    	
       
	        return alSearchParams;
	    }//end of populateSearchCriteria(DynaActionForm frmSearchHospital)

	    
	    
	    /**
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
	    		setLinks(request);
	    		log.debug("Inside the doAdd method of PartnerSearchAction");
				PartnerDetailVO partnerDetailVO=null;
				UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
														 getAttribute("UserSecurityProfile");
				ArrayList alCityList = new ArrayList();
				partnerDetailVO = new PartnerDetailVO();
	    		AddressVO addressVO	=	new AddressVO();
	    	//	addressVO.setStateCode("DOH");//SETTING DUBAI AS DEFAULT
	    		addressVO.setCountryCode("123");
	    		addressVO.setStateCode("DXB");//SETTING DUBAI AS DEFAULT
	    		partnerDetailVO.setAddressVO(addressVO);
	            DynaActionForm partnerForm = setFormValues(partnerDetailVO,mapping,request);
	            if(userSecurityProfile.getBranchID()!=null){
	            	partnerForm.set("tpaOfficeSeqId", userSecurityProfile.getBranchID().toString());
	            }//end of if(userSecurityProfile.getBranchID()!=null)
	            else{
	            	partnerForm.set("tpaOfficeSeqId", "");
	            }//end of else
	            
	            
	            //GETTING ALL PAYERS LIST TO ASSOCIATE DEFAULT
	            PartnerManager partnerObject=this.getPartnerManagerObject();
	           // String strPayersList	=	partnerObject.getAllPayersList();
	            //GETTING ALL DUBAI AREAS AS WE SHOWING DUBAI AND UAE AS DEFAULT
	            HashMap hmCityList = null;
	            hmCityList=partnerObject.getCityInfo("DXB");
	            String countryCode	=	(String)(hmCityList.get("CountryId"));
	            String isdcode	=	(String)(hmCityList.get("isdcode"));
	            String stdcode	=	(String)(hmCityList.get("stdcode"));
	            if(hmCityList!=null){
	            	alCityList = (ArrayList)hmCityList.get(partnerDetailVO.getAddressVO().getStateCode());
	            }//end of if(hmCityList!=null)

	            if(alCityList==null){
	            	alCityList=new ArrayList();
	            }//end of if(alCityList==null)
	            
	            
				partnerForm.set("alCityList",alCityList);
				partnerForm.set("countryCode", countryCode);//SETTING COUNTRY AS DEFAULT
				partnerForm.set("isdCode",isdcode);//SETTING ISD AS DEFAULT
				partnerForm.set("stdCode",stdcode);//SETTING STD AS DEFAULT
			//	partnerForm.set("payerCodes", strPayersList);
				//request.setAttribute("stdCodes","stdCodes");
				partnerForm.set("caption","Add");
	            request.getSession().setAttribute("frmAddPartner",partnerForm);
	            return this.getForward(strEditpartner, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strPtnrSearch));
			}//end of catch(Exception exp)
	    }//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	    
	    /**
	     * Populates the value object to form element.
	     * @param hospDetailVO HospitalDetailVO object.
	     * @param mapping The ActionMapping used to select this instance.
	     * @param request HttpServletRequest  object which contains hospital information.
	     * @return DynaActionForm object.
	     */
	    private DynaActionForm setFormValues(PartnerDetailVO partnerDetailVO,ActionMapping mapping,
	    		HttpServletRequest request) throws TTKException
	    {
	        try
	        {
	            DynaActionForm partnerForm = (DynaActionForm)FormUtils.setFormValues("frmAddPartner",
	            		partnerDetailVO,this,mapping,request);
	            if(partnerDetailVO.getAddressVO()!=null)
	            {
	            	partnerForm.set("addressVO", (DynaActionForm)FormUtils.setFormValues("frmPartnerAddress",
	            			partnerDetailVO.getAddressVO(),this,mapping,request));
	            }//end of if(hospitalDetailVO.getAddressVO()!=null)
	            else
	            {
	            	partnerForm.set("addressVO", (DynaActionForm)FormUtils.setFormValues("frmPartnerAddress",
	                		new AddressVO(),this,mapping,request));
	            }//end of else
	            //partnerForm.set("partnerAuditVO", (DynaActionForm)FormUtils.setFormValues("frmPartnerAuditVO",
	            	//	new PartnerAuditVO(),this,mapping,request));
	            if(partnerDetailVO.getDocumentDetailVO()!=null)
	            {
	            	partnerForm.set("documentDetailVO", (DynaActionForm)FormUtils.setFormValues("frmDocumentDetailVO",
	            			partnerDetailVO.getDocumentDetailVO(),this,mapping,request));
	            }//end of if(hospitalDetailVO.getDocumentDetailVO()!=null)
	            else
	            {
	            	partnerForm.set("documentDetailVO", (DynaActionForm)FormUtils.setFormValues("frmDocumentDetailVO",
	                		new DocumentDetailVO(),this,mapping,request));
	            }//end of else
	            //if change in empanelled hospital name found, for capturing the reference detail informaiton
	            if(partnerDetailVO.getEmplStatusTypeId()!=null && partnerDetailVO.getEmplStatusTypeId().
	            		equalsIgnoreCase("EMP"))
	            {
	                //set the hospital name, pan number and category to session
	                request.getSession().setAttribute("flgPtnrName",partnerDetailVO.getPartnerName());
	                request.getSession().setAttribute("flgPanNumber",partnerDetailVO.getPanNmbr());
	                request.getSession().setAttribute("flgCategory",partnerDetailVO.getCategoryID());
	            }//end of if(hospitalDetailVO.getEmplStatusTypeId()!=null && hospitalDetailVO.getEmplStatusTypeId().equalsIgnoreCase("EMP"))
	            return partnerForm;
	        }
	        catch(Exception exp)
	        {
	            throw new TTKException(exp,strPtnrSearch);
	        }//end of catch
	    }//end of setFormValues(HospitalDetailVO hospitalDetailVO,ActionMapping mapping,HttpServletRequest request)

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
	    		log.debug("Inside the doSave method of PartnerSearchAction");
	    		com.ttk.common.security.Cache.refresh("PartnerList");
				HashMap hmCityList = null;
				ArrayList alCityList = new ArrayList();
				PartnerVO partnerVO=null;
				PartnerDetailVO partnerDetailVO=null;
				PartnerManager partnerObject=this.getPartnerManagerObject();
	            DynaActionForm generalForm = (DynaActionForm)form;
	              Long  lngPtnrSeqId=TTKCommon.getLong(TTKCommon.checkNull((String)generalForm.get("ptnrSeqId")));
	               if(lngPtnrSeqId!=null)
	            {
	                if((request.getSession().getAttribute("flgPtnrName")!=null))
	                {
	                    if((!request.getSession().getAttribute("flgPtnrName").equals(generalForm.get("partnerName")) ||
	                    	!request.getSession().getAttribute("flgPanNumber").equals(generalForm.get("panNmbr")) ||
	                    	!request.getSession().getAttribute("flgCategory").equals(generalForm.get("categoryID")))
	                    		&& (generalForm.get("emplStatusTypeId").toString().equalsIgnoreCase("EMP") || generalForm.get("emplStatusTypeId").toString().equalsIgnoreCase("REN")))
	                        {
	                    		//to make server side validation for Reference Details Screen
	                            generalForm.set("flagValidate","true");
	                            generalForm.set("partnerAuditVO", (DynaActionForm)FormUtils.
	                            		setFormValues("frmPartnerAuditVO",new PartnerAuditVO(),this,mapping,request));
	                            generalForm.set("refdetcaption",request.getSession().getAttribute("refDetPartnerName"));
	                            return this.getForward("referencedetail", mapping, request);
	                        }//if(!request.getSession().getAttribute("flgPtnrName").equals(generalForm.get("hospitalName"))&&generalForm.get("emplStatusTypeId").toString().equalsIgnoreCase("EMP"))
	                }//end of if((request.getSession().getAttribute("flgPtnrName")!=null))
	            }//end of if(lPtnrSeqId!=null)
	             partnerDetailVO = getFormValues(generalForm,mapping,request);
				
				//getting the partner name and splitting the qha ID from name to save
				String tempPtnrname	=	partnerDetailVO.getPartnerName();
				if(tempPtnrname.indexOf('[')>0)
				{
					tempPtnrname	=	tempPtnrname.substring(0, tempPtnrname.indexOf('['));
				}
				partnerDetailVO.setPartnerName(tempPtnrname);
				
	            //update the partner details to data base
					lngPtnrSeqId = partnerObject.addUpdatePartner(partnerDetailVO);
	            //set the appropriate message
	                       if(lngPtnrSeqId > 0)
	            {
	                if(generalForm.get("ptnrSeqId")!=null && !generalForm.get("ptnrSeqId").equals(""))
	                {
	                    request.setAttribute("updated","message.savedSuccessfully");
	                    request.setAttribute("cacheId", ""+lngPtnrSeqId);
	                    request.setAttribute("cacheDesc", partnerDetailVO.getPartnerName());
	                    //finally modify the web board details, if the hospital name is changed
	                    TTKCommon.modifyWebBoardId(request);
	                }//end of if(generalForm.get("hospSeqId")!=null && !generalForm.get("hospSeqId").equals(""))
	                else
	                {
	                    request.setAttribute("updated","message.addedSuccessfully");
	                    partnerVO=new PartnerVO();
	                    partnerVO.setPtnrSeqId(lngPtnrSeqId);
	                    partnerVO.setPartnerName((String)generalForm.get("partnerName"));
	                    //add the details to web board
	                    this.addToWebBoard(partnerVO, request);
	                    //clear the form object in add mode
	                }//end of else
	            }//end of if(lngHospSeqId > 0)
	            partnerDetailVO = partnerObject.getPartnerDetail(lngPtnrSeqId);
	            generalForm.initialize(mapping);
	            hmCityList=partnerObject.getCityInfo();
	            if(partnerDetailVO == null){
	            	//System.out.println("Testing.........");
	            }
	            if(hmCityList!=null){
	            	alCityList = (ArrayList)hmCityList.get(partnerDetailVO.getAddressVO().getStateCode());
	            }//end of if(hmCityList!=null)
	            DynaActionForm partnerForm = setFormValues(partnerDetailVO,mapping,request);
	            partnerForm.set("caption","Edit");
	            partnerForm.set("alCityList",alCityList);
	            request.getSession().setAttribute("PtnrDetails",partnerDetailVO);       
	            request.getSession().setAttribute("frmAddPartner",partnerForm);
				Cache.refresh("PartnerList");
	            return this.getForward(strEditpartner, mapping, request);
	    	}//end of try
	    	catch(TTKException expTTK)
			{
				return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
				return this.processExceptions(request, mapping, new TTKException(exp,strPtnrSearch));
			}//end of catch(Exception exp)
	    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	    
	    
	    /**
	     * Populates the form element to value object .
	     * @param hospDetailVO HospitalDetailVO object.
	     * @param mapping The ActionMapping used to select this instance.
	     * @param request HttpServletRequest  object which contains hospital information.
	     * @return hospDetailVO HospitalDetailVO object.
	     */
	    private PartnerDetailVO getFormValues(DynaActionForm generalForm,ActionMapping mapping,
	    		HttpServletRequest request) throws TTKException
	    {
	        try
	        {
	        	PartnerDetailVO ptnrDetailVO =null;
	            AddressVO addressVO = null;
	            PartnerAuditVO partnerAuditVO=null;
	            DocumentDetailVO docuDetailVO = null;
	            ptnrDetailVO = (PartnerDetailVO)FormUtils.getFormValues(generalForm,"frmAddPartner",this,mapping,request);
	            
	            
	            ActionForm addressForm=(ActionForm)generalForm.get("addressVO");
	         //   ActionForm partnerAuditForm=(ActionForm)generalForm.get("partnerAuditVO");
	            ActionForm documentDetailForm=(ActionForm)generalForm.get("documentDetailVO");
	            addressVO=(AddressVO)FormUtils.getFormValues(addressForm,"frmPartnerAddress",this,mapping,request);
	            docuDetailVO=(DocumentDetailVO)FormUtils.getFormValues(documentDetailForm,"frmDocumentDetailVO",
	            			 this,mapping,request);
	            //To bring country based on state selection along with cities
	            String stateCode	=	addressVO.getStateCode();
	            request.getSession().setAttribute("stateCode", stateCode);
	            
	            ptnrDetailVO.setAddressVO(addressVO);
	       //     partnerAuditVO=(PartnerAuditVO)FormUtils.getFormValues(partnerAuditForm,"frmPartnerAuditVO",
	            			//	 this,mapping,request);
	            ptnrDetailVO.setPartnerAuditVO(partnerAuditVO);
	            ptnrDetailVO.setDocumentDetailVO(docuDetailVO);
	            ptnrDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));////User Id
	            if(generalForm.get("emplStatusTypeId")!=null && generalForm.
	            		get("emplStatusTypeId").toString().equalsIgnoreCase("EMP"))
	            {
	            	//set the hospital name, pan number and category to session
	                request.getSession().setAttribute("flgPtnrName",generalForm.get("partnerName"));
	                request.getSession().setAttribute("flgPanNumber",generalForm.get("panNmbr"));
	                request.getSession().setAttribute("flgCategory",generalForm.get("categoryID"));
	            }//end of if(generalForm.get("emplStatusTypeId")!=null && generalForm.get("emplStatusTypeId").toString().equalsIgnoreCase("EMP"))
	            return ptnrDetailVO;
	        }//end of try
	        catch(Exception exp)
	        {
	            throw new TTKException(exp,strPtnrSearch);
	        }//end of catch
	    }//end of getFormValues(DynaActionForm generalForm,ActionMapping mapping,HttpServletRequest request)

	    private void addToWebBoard(PartnerVO partnerVO, HttpServletRequest request)
	    {
	        Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
	        CacheObject cacheObject = new CacheObject();
	        cacheObject.setCacheId(""+partnerVO.getPtnrSeqId());
	        cacheObject.setCacheDesc(partnerVO.getPartnerName());
	        ArrayList<Object> alCacheObject = new ArrayList<Object>();
	        alCacheObject.add(cacheObject);
	        //if the object(s) are added to the web board, set the current web board id
	        toolbar.getWebBoard().addToWebBoardList(alCacheObject);
	        toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
	        //set weboardinvoked as true to avoid change of webboard id twice in same request
	        request.setAttribute("webboardinvoked","true");
	    }//end of addToWebBoard(HospitalVO hospitalVO, HttpServletRequest request)

	    
		public ActionForward doChangeState(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			try {
				setLinks(request);
				log.debug("Inside the doChangeState method of HospitalSearchAction");
				PartnerManager partnerObject = this.getPartnerManagerObject();
				HashMap hmCityList = null;
				ArrayList alCityList = new ArrayList();
				PartnerDetailVO partnerDetailVO = null;
				DynaActionForm hospitalForm = (DynaActionForm) form;
				partnerDetailVO = getFormValues(hospitalForm, mapping, request);

				String stateCode = "";
				stateCode = (String) request.getSession().getAttribute("stateCode");
				// request.getSession().setAttribute(stateCode, "stateCode");
				hmCityList = partnerObject.getCityInfo(stateCode);
				String countryCode = (String) (hmCityList.get("CountryId"));
				String isdcode = (String) (hmCityList.get("isdcode"));
				String stdcode = (String) (hmCityList.get("stdcode"));
				if (hmCityList != null) {
					alCityList = (ArrayList) hmCityList.get(partnerDetailVO
							.getAddressVO().getStateCode());
				}// end of if(hmCityList!=null)

				if (alCityList == null) {
					alCityList = new ArrayList();
				}// end of if(alCityList==null)
//
			//	hospitalForm.set("frmChanged", "");
				hospitalForm.set("isdCode", isdcode);
				hospitalForm.set("stdCode", stdcode);
				hospitalForm.set("alCityList", alCityList);
				partnerDetailVO.getAddressVO().setCountryCode(countryCode);

				// request.setAttribute("stdCodes","stdCodes");
				return this.getForward(strEditpartner, mapping, request);
			}// end of try
			catch (TTKException expTTK) {
				return this.processExceptions(request, mapping, expTTK);
			}// end of catch(TTKException expTTK)
			catch (Exception exp) {
				return this.processExceptions(request, mapping, new TTKException(
						exp, strPtnrSearch));
			}// end of catch(Exception exp)
		}// end of doChangeState(ActionMapping mapping,ActionForm
			// form,HttpServletRequest request,HttpServletResponse response)

		
		
		public ActionForward doCopyToWebBoard(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			try {
				setLinks(request);
				log.debug("Inside the doStatusChange method of HospitalSearchAction");
				this.populateWebBoard(request);
				return this.getForward(strPartnerlist, mapping, request);
			}// end of try
			catch (TTKException expTTK) {
				return this.processExceptions(request, mapping, expTTK);
			}// end of catch(TTKException expTTK)
			catch (Exception exp) {
				return this.processExceptions(request, mapping, new TTKException(
						exp, strPtnrSearch));
			}// end of catch(Exception exp)
		}// end of doCopyToWebBoard(ActionMapping mapping,ActionForm
			// form,HttpServletRequest request,HttpServletResponse response)

		
		private void populateWebBoard(HttpServletRequest request) {
			String[] strChk = request.getParameterValues("chkopt");
			TableData tableData = (TableData) request.getSession().getAttribute(
					"tableData");
			Toolbar toolbar = (Toolbar) request.getSession()
					.getAttribute("toolbar");
			ArrayList<Object> alCacheObject = new ArrayList<Object>();
			CacheObject cacheObject = null;
			if (strChk != null && strChk.length != 0) {
				// loop through to populate delete sequence id's and get the value
				// from session for the matching check box value
				for (int i = 0; i < strChk.length; i++) {
					cacheObject = new CacheObject();
					cacheObject.setCacheId(""
							+ ((PartnerVO) tableData.getData().get(
									Integer.parseInt(strChk[i]))).getPtnrSeqId());
					cacheObject.setCacheDesc(((PartnerVO) tableData.getData().get(
							Integer.parseInt(strChk[i]))).getPartnerName());
					alCacheObject.add(cacheObject);
				}// end of for(int i=0; i<strChk.length;i++)
			}// end of if(strChk!=null&&strChk.length!=0)
			if (toolbar != null) {
				toolbar.getWebBoard().addToWebBoardList(alCacheObject);
			}// end of if(toolbar != null)
		}// end of populateWebBoard(HttpServletRequest request)

		
		
		/**
		 * This method is used to navigate to detail screen to edit selected record.
		 * Finally it forwards to the appropriate view based on the specified
		 * forward mappings
		 * 
		 * @param mapping
		 *            The ActionMapping used to select this instance
		 * @param form
		 *            The optional ActionForm bean for this request (if any)
		 * @param request
		 *            The HTTP request we are processing
		 * @param response
		 *            The HTTP response we are creating
		 * @return ActionForward Where the control will be forwarded, after this
		 *         request is processed
		 * @throws Exception
		 *             if any error occurs
		 */
		public ActionForward doView(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			try {
				setLinks(request);
				log.debug("Inside the doView method of PartnerSearchAction");
				String strCaption = "";
				HashMap hmCityList = null;
				ArrayList alCityList = new ArrayList();
				PartnerVO partnerVO = null;
				PartnerDetailVO partnerDetailVO = null;
				PartnerManager partnerObject = this.getPartnerManagerObject();
				TableData tableData = TTKCommon.getTableData(request);
				if (TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")) {
					((DynaActionForm) form).initialize(mapping);// reset the form
																// data
				}// end of
					// if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))

				DynaActionForm generalForm = (DynaActionForm) form;
				if (!(TTKCommon.checkNull(request.getParameter("rownum"))
						.equals(""))) {
					partnerVO = (PartnerVO) tableData.getRowInfo(Integer
							.parseInt((String) (generalForm).get("rownum")));
					// add the selected item to the web board and make it as default
					// selected
					this.addToWebBoard(partnerVO, request);
					// calling business layer to get the hospital detail
					partnerDetailVO = partnerObject.getPartnerDetail(partnerVO
							.getPtnrSeqId());
					generalForm.initialize(mapping);
					strCaption = "Edit";
					// Add the request object to DocumentViewer
					TTKCommon.documentViewer(request);
				}// end
					// if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
				else if (TTKCommon.getWebBoardId(request) != null)// take the
																	// hospital_seq_id
																	// from web
																	// board
				{// if web board id is found, set it as current web board id
					partnerVO = new PartnerVO();
					partnerVO.setPtnrSeqId(TTKCommon.getWebBoardId(request));
					// calling business layer to get the hospital detail
					partnerDetailVO = partnerObject.getPartnerDetail(partnerVO
							.getPtnrSeqId());
					strCaption = "Edit";
					// Add the request object to DocumentViewer
					TTKCommon.documentViewer(request);
				}// end of else if(TTKCommon.getWebBoardId(request) != null)
				else {

					TTKException expTTK = new TTKException();
					expTTK.setMessage("error.partner.required");
					throw expTTK;

				}// end of if(strMode.equals("EditHospital"))

				hmCityList = partnerObject.getCityInfo(partnerDetailVO
						.getAddressVO().getStateCode());
				String countryCode = (String) (hmCityList.get("CountryId"));
				if (hmCityList != null) {
					alCityList = (ArrayList) hmCityList.get(partnerDetailVO
							.getAddressVO().getStateCode());
				}// end of if(hmCityList!=null)
				DynaActionForm partnerForm = setFormValues(partnerDetailVO,
						mapping, request);
				partnerForm.set("caption", strCaption);
				partnerForm.set("alCityList", alCityList);
				partnerDetailVO.getAddressVO().setCountryCode(countryCode);
				request.getSession().setAttribute("frmAddPartner", partnerForm);
				request.getSession().setAttribute("refDetPartnerName",
						partnerDetailVO.getPartnerName());
				request.getSession().setAttribute("AuthLicenseNo",
						partnerDetailVO.getIrdaNumber());
				request.getSession().setAttribute("PtnrDetails", partnerDetailVO);
				return this.getForward(strEditpartner, mapping, request);
			}// end of try
			catch (TTKException expTTK) {
				return this.processExceptions(request, mapping, expTTK);
			}// end of catch(TTKException expTTK)
			catch (Exception exp) {
				return this.processExceptions(request, mapping, new TTKException(
						exp, strPtnrSearch));
			}// end of catch(Exception exp)
		}// end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest
			// request,HttpServletResponse response)

		
		public ActionForward doChangeWebBoard(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			log.debug("Inside doChangeWebBoard method of PartnerSearchAction");
			// ChangeWebBoard method will call doView() method internally.
			return doView(mapping, form, request, response);
		}// end of doChangeWebBoard(ActionMapping mapping,ActionForm
			
}
