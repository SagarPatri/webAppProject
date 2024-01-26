

package com.ttk.action.insurancepricing;



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
import com.ttk.business.claims.ClaimBatchManager;
import com.ttk.business.empanelment.InsuranceManager;
import com.ttk.common.TTKCommon;
import com.ttk.dto.insurancepricing.InsPricingVO;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.claims.BatchVO;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.empanelment.InsuranceDetailVO;
import com.ttk.dto.empanelment.InsuranceVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

/**
* This class is used to search the InsPricing company,add
* that to webboard. and also has the mode add/edit and delete the
* insurance companies at various level.
*/

public class InsPricingAction extends TTKAction {

	private static Logger log = Logger.getLogger( InsPricingAction.class );
  
    private static final String strPricingHome="pricinghome";
    private static final String strGroupprofile="groupprofile";
    private static final String strIncomeprofile="incomeprofile";
    
    private static final String strInsError="insurance";
    
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
    		log.debug("Inside the doDefault method of InsPricingAction");
    		setLinks(request);
    		DynaActionForm frmPricingHome = (DynaActionForm)form;
            //((DynaActionForm)form).initialize(mapping);//reset the form data
    		TableData tableData = TTKCommon.getTableData(request);
    		//remove the selected office from the session
            request.getSession().removeAttribute("SelectedOffice");
            tableData = new TableData();//create new table data object
            //create the required grid table
            tableData.createTableInfo("InsurancePricingTable",new ArrayList());
            request.getSession().setAttribute("tableData",tableData);
            ((DynaActionForm)form).initialize(mapping);//reset the form data
    		if(TTKCommon.checkNull(request.getParameter("Message")).equals("Y"))
			{
    			frmPricingHome.set("Message",request.getParameter("Message")); 
            	TTKException expTTK = new TTKException();
				expTTK.setMessage("error.pricing.required");
				throw expTTK;
				
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		else
    		{
    			frmPricingHome.set("Message","N");  	
    		}
    		long seqid=0;
    		 request.getSession().setAttribute("GroupProfileSeqID",seqid);
    		 request.getSession().setAttribute("frmPricingHome",frmPricingHome);
            return this.getForward(strPricingHome, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
		}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{

    		setLinks(request);
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
            TableData tableData = TTKCommon.getTableData(request);
            InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
            //if the page number or sort id is clicked
            if(!strPageID.equals("") || !strSortID.equals(""))
            {
            	if(!strPageID.equals(""))
                {
            		tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
                    return mapping.findForward(strPricingHome);
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
                tableData.createTableInfo("InsurancePricingTable",null);
                tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
                tableData.modifySearchData("search");
            }//end of else
            ArrayList alInsuranceProfileList= insuranceObject.getInsuranceProfileList(tableData.getSearchData());
            tableData.setData(alInsuranceProfileList, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strPricingHome, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
		}//end of catch(Exception exp)
    }
    
    private ArrayList<Object> populateSearchCriteria(DynaActionForm frmPricingHome,
			HttpServletRequest request)throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		//alSearchParams.add(new SearchCriteria("EMPANELED_DATE", (String)frmSearchInsurance.get("sEmpanelDate")));
		alSearchParams.add(TTKCommon.checkNull(frmPricingHome.get("groupName")));

    	return alSearchParams;
	}
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
    		log.debug("Inside the doAdd method of InsuranceCompanyAction");
    		setLinks(request);
    		DynaActionForm frmPricing = (DynaActionForm)form;
            UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
            										 getAttribute("UserSecurityProfile");
            InsPricingVO insPricingVO=new InsPricingVO();
            //code to get the Next Abbreviation for Insurance
            InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
            frmPricing.initialize(mapping);
            frmPricing= (DynaActionForm)FormUtils.setFormValues("frmPricing", insPricingVO,
					this, mapping, request);
            
            request.getSession().setAttribute("frmPricing",frmPricing);
            return this.getForward(strGroupprofile, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
		}//end of catch(Exception exp)
    }//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    try{
    setLinks(request);
    DynaActionForm frmPricing= (DynaActionForm)form;
    InsPricingVO insPricingVO=null;
    String successMsg;

    InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
    insPricingVO = (InsPricingVO)FormUtils.getFormValues(frmPricing, this, mapping, request);
    insPricingVO.setAddedBy((TTKCommon.getUserSeqId(request)));
               
    Long lpricingSeqId= insuranceObject.savePricingList(insPricingVO);
    		
    		if(lpricingSeqId > 0)successMsg="Group Profile Added Successfully";
    		else successMsg="Group Profile Updated Successfully";
    		
    		 insPricingVO= insuranceObject.selectPricingList(lpricingSeqId);
    		 
    		 frmPricing= (DynaActionForm)FormUtils.setFormValues("frmPricing",	insPricingVO, this, mapping, request);
    		 request.getSession().setAttribute("frmPricing",frmPricing);
    		 request.getSession().setAttribute("GroupProfileSeqID",insPricingVO.getGroupProfileSeqID());
    			request.setAttribute("successMsg",successMsg);
    return this.getForward(strGroupprofile, mapping, request);
    }//end of try
    catch(TTKException expTTK)
    {
    return this.processExceptions(request, mapping, expTTK);
    }//end of catch(TTKException expTTK)
    catch(Exception exp)
    {
    return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
    }//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    
    public ActionForward doIncomeProfile(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		try
		{
			log.debug("Inside the doSearch method of CriticalICDListAction");
			setLinks(request);
			 InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();


			  DynaActionForm frmIncomeProfile= (DynaActionForm)form;
			  ArrayList alprofileIncomeList=null;
			
			
			  long Seq_Id=(long)request.getSession().getAttribute("GroupProfileSeqID");
			//ArrayList alprofileIncomeList= insuranceObject.getProfileIncomeList(Seq_ID);
			if(Seq_Id > 0)
			{
				long group_seq_id= (long)request.getSession().getAttribute("GroupProfileSeqID");
			 alprofileIncomeList= insuranceObject.getProfileIncomeListvalue(group_seq_id);
			 frmIncomeProfile.set("Message","N");
			}
			else
			{
				frmIncomeProfile.set("Message",request.getParameter("Message")); 
				frmIncomeProfile.set("Message","Y"); 
		    	TTKException expTTK = new TTKException();
				expTTK.setMessage("error.pricing.required");
				throw expTTK;
				
			}
			frmIncomeProfile.set("profileIncomeList",alprofileIncomeList);  
	
			request.getSession().setAttribute("frmIncomeProfile", frmIncomeProfile);	
			request.getSession().setAttribute("showflag", "N");	

			//finally return to the grid screen
			return this.getForward(strIncomeprofile, mapping, request);
	    }//end of try
	    catch(TTKException expTTK)
	    {
	    return this.processExceptions(request, mapping, expTTK);
	    }//end of catch(TTKException expTTK)
	    catch(Exception exp)
	    {
	    return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
	    }//end of catch(Exception exp)
	    }
    
    public ActionForward doSaveIncome(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.info("Inside CriticalICDListAction doSave");
			 
			InsPricingVO  insPricingVO =null;
			DynaActionForm frmIncomeProfile=(DynaActionForm)form; 
			//DynaActionForm frmIncomeProfile = (DynaActionForm)request.getSession().getAttribute("frmPricing");
			insPricingVO =(InsPricingVO)FormUtils.getFormValues(frmIncomeProfile, "frmIncomeProfile",this, mapping, request);
			 InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject(); 
	
			long group_seq_id=(long)request.getSession().getAttribute("GroupProfileSeqID");
			insPricingVO.setProfileID((String[])frmIncomeProfile.get("profileID"));
			insPricingVO.setProfileGroup((String[])frmIncomeProfile.get("profileGroup"));
			insPricingVO.setProfileValue((String[])frmIncomeProfile.get("profileValue"));
			insPricingVO.setProfilePercentage((String[])frmIncomeProfile.get("profilePercentage"));
			insPricingVO.setTransProfileSeqID((Long[])frmIncomeProfile.get("transProfileSeqID"));
			insPricingVO.setGroupProfileSeqID(group_seq_id);
			
			 
			//insPricingVO.setGroupID((String)frmSearchProcedure.get("sGroupID")); 
			//insPricingVO.setGroupSpecDescList(result);  
			int strTransactionseqId= insuranceObject.saveIncomeProfile(insPricingVO);
			//if(strTransactionseqId>0)
			//{
					request.setAttribute("updated","message.savedSuccessfully");
					request.getSession().setAttribute("showflag", "N");
					 
			//} 
			
			ArrayList alprofileIncomeList= insuranceObject.getProfileIncomeListvalue(group_seq_id);
			
			
			frmIncomeProfile.set("profileIncomeList",alprofileIncomeList);  
			request.getSession().setAttribute("GroupProfileSeqID",group_seq_id);
			request.getSession().setAttribute("frmIncomeProfile",frmIncomeProfile); 
			//return doSearch(mapping ,form, request, response);
			return this.getForward(strIncomeprofile, mapping, request);
		 }//end of try
	    catch(TTKException expTTK)
	    {
	    return this.processExceptions(request, mapping, expTTK);
	    }//end of catch(TTKException expTTK)
	    catch(Exception exp)
	    {
	    return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
	    }//end of catch(Exception exp)
	    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
  
    
	public ActionForward doEditIncome(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    try{
    setLinks(request);
    DynaActionForm frmPricing= (DynaActionForm)form;
   InsPricingVO insPricingVO=null;


    InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();

    long Seq_Id=(long)request.getSession().getAttribute("GroupProfileSeqID");
    
    insPricingVO=new InsPricingVO();
	TableData tableData = TTKCommon.getTableData(request);

	//Edit flow
	if(!((String)(frmPricing).get("rownum")).equals("")) 
	{
		insPricingVO =(InsPricingVO)tableData.getRowInfo(Integer.parseInt((String)(frmPricing).
																					get("rownum")));
  
		 insPricingVO= insuranceObject.selectPricingList(insPricingVO.getGroupProfileSeqID());
		 frmPricing = (DynaActionForm)FormUtils.setFormValues("frmPricing",insPricingVO,
																		this,mapping,request);
		 request.getSession().setAttribute("showflag", "Y");

	}//end of if(!((String)(formTariffItem).get("rownum")).equals(""))
	else if(Seq_Id > 0)
	{
		insPricingVO.setGroupProfileSeqID((long)request.getSession().getAttribute("GroupProfileSeqID"));
		insPricingVO= insuranceObject.selectPricingList(insPricingVO.getGroupProfileSeqID());
		frmPricing = (DynaActionForm)FormUtils.setFormValues("frmPricing",insPricingVO,
				this,mapping,request);
		frmPricing.set("Message","N"); 
	}
	else
	{
		frmPricing.set("Message",request.getParameter("Message")); 
		frmPricing.set("Message","Y"); 
    	TTKException expTTK = new TTKException();
		expTTK.setMessage("error.pricing.required");
		throw expTTK;
	}
	 request.getSession().setAttribute("GroupProfileSeqID",insPricingVO.getGroupProfileSeqID());
	request.getSession().setAttribute("frmPricing",frmPricing);
	return this.getForward(strGroupprofile, mapping, request);
    
    
    }//end of try
    catch(TTKException expTTK)
    {
    return this.processExceptions(request, mapping, expTTK);
    }//end of catch(TTKException expTTK)
    catch(Exception exp)
    {
    return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
    }//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    
    public ActionForward doCloseIncome(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    try{
    setLinks(request);
    DynaActionForm frmPricing= (DynaActionForm)form;
    InsPricingVO insPricingVO=null;
    String successMsg;

    InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
    insPricingVO = (InsPricingVO)FormUtils.getFormValues(frmPricing, this, mapping, request);
    insPricingVO.setAddedBy((TTKCommon.getUserSeqId(request)));
   long lpricingSeqId=  (long)request.getSession().getAttribute("GroupProfileSeqID");    

    		
    		 insPricingVO= insuranceObject.selectPricingList(lpricingSeqId);
    		 
    		 frmPricing= (DynaActionForm)FormUtils.setFormValues("frmPricing",	insPricingVO, this, mapping, request);
    		 request.getSession().setAttribute("frmPricing",frmPricing);
    		 request.getSession().setAttribute("GroupProfileSeqID",lpricingSeqId);
    			//request.setAttribute("successMsg",successMsg);
    return this.getForward(strGroupprofile, mapping, request);
    }//end of try
    catch(TTKException expTTK)
    {
    return this.processExceptions(request, mapping, expTTK);
    }//end of catch(TTKException expTTK)
    catch(Exception exp)
    {
    return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
    }//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    /**
     * Returns the InsuranceManager session object for invoking methods on it.
     * @return InsuranceManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private InsuranceManager getInsuranceManagerObject() throws TTKException
	{
		InsuranceManager insuremanager = null;
		try
		{
			if(insuremanager == null)
			{
				InitialContext ctx = new InitialContext();
				insuremanager = (InsuranceManager) ctx.lookup("java:global/TTKServices/business.ejb3/InsuranceManagerBean!com.ttk.business.empanelment.InsuranceManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strInsError);
		}//end of catch
		return insuremanager;
	} // end of private InsuranceManager getInsManagerObject() throws TTKException
    
   
} //end of InsPricingAction