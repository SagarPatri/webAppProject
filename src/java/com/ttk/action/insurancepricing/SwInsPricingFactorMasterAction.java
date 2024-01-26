package com.ttk.action.insurancepricing;



import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.empanelment.InsuranceManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.insurancepricing.InsPricingVO;
import com.ttk.dto.insurancepricing.MasterFactorVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;

public class SwInsPricingFactorMasterAction extends TTKAction  {
	private static Logger log = Logger.getLogger( SwInsPricingFactorMasterAction.class );
	  
	private static final String strForward="Forward";
	private static final String strBackward="Backward";
	private static final String strPricingFactorMaster="pricingFactorMaster";
    private static final String strGroupprofile="groupprofile";
    private static final String strSaveFactorMaster="saveFactorMaster";
    private static final String strSaveproceed="saveproceed";
    private static final String strIncomeprofile="incomeprofile";
    private static final String strInputscreen3="inputscreen3";
    
    
    private static final String strInsError="insurance";
	private static final String strChngCorporate="changecorporate";
	private static final String strRownum="rownum";
	private static final String strProcingTrendMaster="pricingTrendMaster";

	
	
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.info("Inside the doDefault method of InsPricingFactorMasterAction");
    		setLinks(request);
    		DynaActionForm frmSwPricingFactorMaster = (DynaActionForm)form;
    		
    		  InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
    		 ArrayList<MasterFactorVO> factorList= insuranceObject.selectFactorMasterList();
    		 
    	/*	if(factorList==null){
    			log.info("List is null:");
    		}
    		else{
    			log.info("List is not null:");
    		}*/
    		
            ((DynaActionForm)form).initialize(mapping);//reset the form data
            frmSwPricingFactorMaster.set("factorMasterList",factorList);
    		 request.getSession().setAttribute("frmSwPricingFactorMaster",frmSwPricingFactorMaster);
            return this.getForward(strPricingFactorMaster, mapping, request);
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
	

	
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
		
		setLinks(request);
	    DynaActionForm frmSwPricingFactorMaster= (DynaActionForm)form;
	    
	    String[]factIds=request.getParameterValues("factId");
	    ArrayList<MasterFactorVO> factorList= new ArrayList<MasterFactorVO>();

	    for(String factId:factIds){
	    	MasterFactorVO factorVO=new MasterFactorVO();

	    	factorVO.setFactId(factId);
	    	factorVO.setFactor(request.getParameter("factor"+factId));
	    	factorVO.setAverageAge(request.getParameter("averageAge"+factId));

	    	factorList.add(factorVO);

	    }
	    

	    InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
	    
	    Long i= insuranceObject.saveFactorMaster(factorList);
	     factorList= insuranceObject.selectFactorMasterList();
		 
    	/*	if(factorList==null){
    			log.info("List is null:");
    		}
    		else{
    			log.info("List is not null:");
    		}*/
    		
            ((DynaActionForm)form).initialize(mapping);//reset the form data
            frmSwPricingFactorMaster.set("factorMasterList",factorList);
    		 request.getSession().setAttribute("frmSwPricingFactorMaster",frmSwPricingFactorMaster);
	    log.info("Action"+i);
	    request.setAttribute("successMsg",
				"Details Updated Successfully");
	    return this.getForward(strSaveFactorMaster, mapping, request);
		
	}
	
	
	//for save()
	public ActionForward doSave1(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    try{
    setLinks(request);
    DynaActionForm frmSwPricing= (DynaActionForm)form;
    InsPricingVO insPricingVO=null;
    String successMsg;String newdata = "N";String pricingNumberAlert = "";
    String singlebutton = request.getParameter("singlebutton");
   

    InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
    insPricingVO = (InsPricingVO)FormUtils.getFormValues(frmSwPricing, this, mapping, request);
    
    insPricingVO.setAddedBy((TTKCommon.getUserSeqId(request)));
 // UPLOAD FILE STARTS
    FormFile formFile1 = (FormFile)frmSwPricing.get("file1");
    FormFile formFile2 = (FormFile)frmSwPricing.get("file2");
    FormFile formFile3 = (FormFile)frmSwPricing.get("file3");
    FormFile formFile4 = (FormFile)frmSwPricing.get("file4");
    FormFile formFile5 = (FormFile)frmSwPricing.get("file5");
    int fileSize = 1*1024*1024;
    
    if(!("").equals(formFile1.getFileName()))
   	{
       	insPricingVO.setAttachmentname1(formFile1.getFileName());
        String lowercaseFileExtn1 = formFile1.getFileName().toLowerCase();checkfileExtention(lowercaseFileExtn1);
        if(fileSize<=formFile1.getFileSize())
		{
        	TTKException expTTK = new TTKException();
			expTTK.setMessage("error.Upload.size1.mb");
			throw expTTK;
		}
   	}
    if(!("").equals(formFile2.getFileName()))
   	{
       	insPricingVO.setAttachmentname2(formFile2.getFileName());
        String lowercaseFileExtn2 = formFile2.getFileName().toLowerCase();checkfileExtention(lowercaseFileExtn2);
        if(fileSize<=formFile2.getFileSize())
		{
        	TTKException expTTK = new TTKException();
			expTTK.setMessage("error.Upload.size2.mb");
			throw expTTK;
		}
   	}
    if(!("").equals(formFile3.getFileName()))
   	{
       	insPricingVO.setAttachmentname3(formFile3.getFileName());
        String lowercaseFileExtn3 = formFile3.getFileName().toLowerCase();checkfileExtention(lowercaseFileExtn3);
        if(fileSize<=formFile3.getFileSize())
		{
        	TTKException expTTK = new TTKException();
			expTTK.setMessage("error.Upload.size3.mb");
			throw expTTK;
		}
   	}
    if(!("").equals(formFile4.getFileName()))
   	{
       	insPricingVO.setAttachmentname4(formFile4.getFileName());
        String lowercaseFileExtn4 = formFile4.getFileName().toLowerCase();checkfileExtention(lowercaseFileExtn4);
        if(fileSize<=formFile4.getFileSize())
		{
        	TTKException expTTK = new TTKException();
			expTTK.setMessage("error.Upload.size4.mb");
			throw expTTK;
		}
   	}
    if(!("").equals(formFile5.getFileName()))
   	{
       	insPricingVO.setAttachmentname5(formFile5.getFileName());
        String lowercaseFileExtn5 = formFile5.getFileName().toLowerCase();checkfileExtention(lowercaseFileExtn5);
        if(fileSize<=formFile5.getFileSize())
		{
        	TTKException expTTK = new TTKException();
			expTTK.setMessage("error.Upload.size5.mb");
			throw expTTK;
		}
   	}
				
		/*	select drop down validation*/
    
			//setSelectModeform(insPricingVO);
		
		if(insPricingVO.getGroupProfileSeqID() == null)
		{
			newdata = "Y";
		}
		
		
			 insPricingVO.setSourceAttchments1(formFile1);	
			 insPricingVO.setSourceAttchments2(formFile2);
			 insPricingVO.setSourceAttchments3(formFile3);
			 insPricingVO.setSourceAttchments4(formFile4);
			 insPricingVO.setSourceAttchments5(formFile5);
			 
		//	 Long lpricingSeqId= insuranceObject.swSavePricingList(insPricingVO);
			 ArrayList al= insuranceObject.swSavePricingList(insPricingVO);
			 Long lpricingSeqId=(Long)al.get(0);
			 String errorCode=(String)al.get(1);
			 String errorMsg=(String)al.get(2);
			 
			 if(lpricingSeqId > 0)successMsg="Details Added Successfully";
    		 else successMsg="Details Updated Successfully";
			 insPricingVO= insuranceObject.swSelectPricingList(lpricingSeqId);
			
			// if(newdata.equalsIgnoreCase("Y")){
					pricingNumberAlert = " Please note the pricing reference number "+insPricingVO.getPricingRefno()+" for future";
			//	}
				
			 
			 frmSwPricing= (DynaActionForm)FormUtils.setFormValues("frmSwPricing",	insPricingVO, this, mapping, request);
			 frmSwPricing.set("newdataentry", newdata);
			 frmSwPricing.set("pricingNumberAlert", pricingNumberAlert);
			 request.getSession().setAttribute("errorCode",errorCode);
			 request.getSession().setAttribute("errorMsg",errorMsg);
			 request.getSession().setAttribute("frmSwPricing",frmSwPricing);
			 request.getSession().setAttribute("ClientCode", insPricingVO.getClientCode());	
			 request.getSession().setAttribute("pricingRefNo", insPricingVO.getPricingRefno());	
			 request.getSession().setAttribute("completeSaveYN",insPricingVO.getCompleteSaveYN());
			 request.getSession().setAttribute("newdataentry", newdata);
			 request.getSession().setAttribute("pricingNumberAlert", pricingNumberAlert);
			 request.getSession().setAttribute("totalNoOfLives",insPricingVO.getTotalCovedLives());
			 request.getSession().setAttribute("TotalMaternityLives",insPricingVO.getTotalLivesMaternity());
			 request.getSession().setAttribute("GroupProfileSeqID",insPricingVO.getGroupProfileSeqID());
			 request.getSession().setAttribute("DentalLivesYN",insPricingVO.getDentalLivesYN());
			 request.getSession().setAttribute("OpticalLivesYN",insPricingVO.getOpticalLivesYN());
			 
    		 
    		// request.setAttribute("successMsg",successMsg);

	if(singlebutton.equalsIgnoreCase("save")){
		 return this.getForward(strGroupprofile, mapping, request);
	}
	else{
		
		 return mapping.findForward(strSaveproceed);
	}
    
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
	//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	log.info("doSearch Factor Master");
    	try{

    		setLinks(request);
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
            String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
            TableData tableData = TTKCommon.getTableData(request);
            InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
            //if the page number or sort id is clicked govind
      /*      if(!strPageID.equals("") || !strSortID.equals(""))
            {
            	if(!strPageID.equals(""))
                {
            		tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
                    return mapping.findForward(strPricingFactorMaster);
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
                tableData.createTableInfo("SwInsurancePricingTable",null);
                tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
                tableData.modifySearchData("search");
            }//end of else govind
            
            */
            ArrayList<MasterFactorVO> factorList= insuranceObject.selectFactorMasterList();
  		  MasterFactorVO factorVO=factorList.get(0);
  		log.info(factorVO.getBenefitDesc());
  		log.info(factorVO.getAverageAge());
           // g tableData.setData(alInsuranceProfileList, "search");
			//set the table data object to session
		// g	request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strPricingFactorMaster, mapping, request);
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
	
	
	
    public ActionForward doEditIncome(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    try{
    setLinks(request);
    DynaActionForm frmSwPricing= (DynaActionForm)form;
   InsPricingVO insPricingVO=null;


    InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
//long Seq_Id =(long)request.getSession().getAttribute("GroupProfileSeqID");
  
    
    insPricingVO=new InsPricingVO();
	TableData tableData = TTKCommon.getTableData(request);

	//Edit flow
	if(!((String)(frmSwPricing).get("rownum")).equals("")) 
	{
		insPricingVO =(InsPricingVO)tableData.getRowInfo(Integer.parseInt((String)(frmSwPricing).
																					get("rownum")));
  
		 insPricingVO= insuranceObject.swSelectPricingList(insPricingVO.getGroupProfileSeqID());
		 frmSwPricing = (DynaActionForm)FormUtils.setFormValues("frmSwPricing",insPricingVO,
																		this,mapping,request);
		//add the selected item to the web board and make it as default selected
			this.addToWebBoard(insPricingVO, request);
		 request.getSession().setAttribute("showflag", "Y");

	}//end of if(!((String)(formTariffItem).get("rownum")).equals(""))
	else if(insPricingVO.getGroupProfileSeqID() > 0)
	{
		insPricingVO.setGroupProfileSeqID((long)request.getSession().getAttribute("GroupProfileSeqID"));
		insPricingVO= insuranceObject.swSelectPricingList(insPricingVO.getGroupProfileSeqID());
		frmSwPricing = (DynaActionForm)FormUtils.setFormValues("frmSwPricing",insPricingVO,
				this,mapping,request);
		frmSwPricing.set("Message","N"); 
	}
	else
	{
		
		frmSwPricing.set("Message",request.getParameter("Message")); 
		frmSwPricing.set("Message","Y"); 
    	TTKException expTTK = new TTKException();
		expTTK.setMessage("error.pricing.required");
		throw expTTK;
	}
	String pricingNumberAlert  = "Please note the pricing reference number "+insPricingVO.getPricingRefno()+" for future";
	request.getSession().setAttribute("pricingNumberAlert", pricingNumberAlert);
	 request.getSession().setAttribute("GroupProfileSeqID",insPricingVO.getGroupProfileSeqID());
	 request.getSession().setAttribute("completeSaveYN",insPricingVO.getCompleteSaveYN());
	 
	 request.getSession().setAttribute("ClientCode",insPricingVO.getClientCode());
	 request.getSession().setAttribute("totalNoOfLives",insPricingVO.getTotalCovedLives());
	 request.getSession().setAttribute("TotalMaternityLives",insPricingVO.getTotalLivesMaternity());
	
	 request.getSession().setAttribute("DentalLivesYN",insPricingVO.getDentalLivesYN());
	 request.getSession().setAttribute("OpticalLivesYN",insPricingVO.getOpticalLivesYN());
	 request.getSession().setAttribute("newdataentry", "N");	



	 request.getSession().setAttribute("frmSwPricing",frmSwPricing);
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
    
    private void addToWebBoard(InsPricingVO insPricingVO, HttpServletRequest request)
	{
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		CacheObject cacheObject = new CacheObject();
		String strPricingRefno=insPricingVO.getPricingRefno();
		
		cacheObject.setCacheId(insPricingVO.getGroupProfileSeqID().toString());
		cacheObject.setCacheDesc(insPricingVO.getPricingRefno());
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		alCacheObject.add(cacheObject);
		//if the object(s) are added to the web board, set the current web board id
		//if(toolbar.getWebBoard().addToWebBoardList(alCacheObject))
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());
	}//end of addToWebBoard(HospitalVO hospitalVO, HttpServletRequest request)
    public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doAdd method of InsuranceCompanyAction");
    		setLinks(request);
    		DynaActionForm frmSwPricing = (DynaActionForm)form;
            UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().
            										 getAttribute("UserSecurityProfile");
            InsPricingVO insPricingVO=new InsPricingVO();
            //code to get the Next Abbreviation for Insurance
            InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
            frmSwPricing.initialize(mapping);
            
            
            insPricingVO.setTrendFactor("6");
            frmSwPricing= (DynaActionForm)FormUtils.setFormValues("frmSwPricing", insPricingVO,
					this, mapping, request);
            
            request.getSession().setAttribute("frmSwPricing",frmSwPricing);
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
    
    
    //web board bikki
    public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
try{
log.debug("Inside the doChangeWebBoard method of ProductSearchAction");
setLinks(request);
//if web board id is found, set it as current web board id
//TTKCommon.setWebBoardId(request);
//get the session bean from the bean pool for each excecuting thread
InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
Long lGroupProfileSeqId=TTKCommon.getWebBoardId(request);
//create a new Product object
InsPricingVO insPricingVO = new InsPricingVO();
DynaActionForm frmSwPricing= (DynaActionForm)form;
frmSwPricing.initialize(mapping);
//get the product details from the Dao object

insPricingVO= insuranceObject.swSelectPricingList(lGroupProfileSeqId);

frmSwPricing = (DynaActionForm)FormUtils.setFormValues("frmSwPricing",insPricingVO, this, mapping, request);

String pricingNumberAlert  = "Please note the pricing reference number "+insPricingVO.getPricingRefno()+" for future";
request.getSession().setAttribute("pricingNumberAlert", pricingNumberAlert);
request.getSession().setAttribute("GroupProfileSeqID",insPricingVO.getGroupProfileSeqID());
request.getSession().setAttribute("completeSaveYN",insPricingVO.getCompleteSaveYN());
request.getSession().setAttribute("ClientCode",insPricingVO.getClientCode());
request.getSession().setAttribute("totalNoOfLives",insPricingVO.getTotalCovedLives());
request.getSession().setAttribute("TotalMaternityLives",insPricingVO.getTotalLivesMaternity());
request.getSession().setAttribute("DentalLivesYN",insPricingVO.getDentalLivesYN());
request.getSession().setAttribute("OpticalLivesYN",insPricingVO.getOpticalLivesYN());
request.getSession().setAttribute("newdataentry", "N");

request.getSession().setAttribute("frmSwPricing",frmSwPricing);
//add the product to session
request.getSession().setAttribute("insPricingVO",insPricingVO);

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
}//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//HttpServletResponse response)
    
    public ActionForward doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doCopyToWebBoard method of InsuranceCompanyAction");
    		setLinks(request);
    		this.populateWebBoard(request);
			return this.getForward(strPricingFactorMaster, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
		}//end of catch(Exception exp)
    }//end of doCopyToWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
				cacheObject.setCacheId(String.valueOf(((InsPricingVO)tableData.getData().
						get(Integer.parseInt(strChk[i]))).getGroupProfileSeqID()));
				cacheObject.setCacheDesc(((InsPricingVO)tableData.getData().get(Integer.parseInt(strChk[i]))).
						getPricingRefno());
				alCacheObject.add(cacheObject);
			}//end of for(int i=0; i<strChk.length;i++)
		}//end of if(strChk!=null&&strChk.length!=0)
		if(toolbar != null)
			toolbar.getWebBoard().addToWebBoardList(alCacheObject);
	}//end of populateWebBoard(HttpServletRequest request)
    

    
    //end web board bikki
	
    public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
try{
log.debug("Inside PreAuthAction doBackward");
setLinks(request);
TableData tableData = TTKCommon.getTableData(request);
InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
tableData.modifySearchData(strBackward);//modify the search data
ArrayList alInsuranceProfileList= insuranceObject.getSwInsuranceProfileList(tableData.getSearchData());
tableData.setData(alInsuranceProfileList, strBackward);//set the table data
request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
return this.getForward(strPricingFactorMaster, mapping, request);   //finally return to the grid screen
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
}//end of catch(Exception exp)
}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
				try{
				log.debug("Inside PreAuthAction doForward");
				setLinks(request);
				TableData tableData = TTKCommon.getTableData(request);
	            InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
				tableData.modifySearchData(strForward);//modify the search data
			    ArrayList alInsuranceProfileList= insuranceObject.getSwInsuranceProfileList(tableData.getSearchData());
			    tableData.setData(alInsuranceProfileList, strForward);//set the table data
				request.getSession().setAttribute("tableData",tableData);   //set the table data object to session
				return this.getForward(strPricingFactorMaster, mapping, request);   //finally return to the grid screen
				}//end of try
				catch(TTKException expTTK)
				{
				return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
				return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
				}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) 
    
    //upload file
    public ActionForward  doViewUploadDocs(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException {
	    
	    ByteArrayOutputStream baos=null;
	    OutputStream sos = null;
	    FileInputStream fis = null; 
	    BufferedInputStream bis =null;
	    InputStream  iStream = null;
	    String fileExtn = "";
	   
	  try{   
			
			String strFile	=	request.getParameter("filePath");
			String strFileName	=	"fileName.jpeg";
	    	String strFileNoRecords = TTKPropertiesReader.getPropertyValue("GlobalDownload")+"/noRecordsFound.pdf";
	    	String strFileerror = TTKPropertiesReader.getPropertyValue("GlobalDownload")+"/fileImproper.pdf";
	       
	    	InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
	    	long group_seq_id=(long)request.getSession().getAttribute("GroupProfileSeqID");
	    	String filename = request.getParameter("filename");
	    	    
	    	  
	    	  InsPricingVO insPricingVOdoc= insuranceObject.swSelectPricingList(group_seq_id);
		    	  if(filename.equalsIgnoreCase("file1")){
					  iStream= insPricingVOdoc.getInputstreamdoc1();
					  fileExtn = insPricingVOdoc.getAttachmentname1();
			    	  }
		    	  if(filename.equalsIgnoreCase("file2")){
					  iStream= insPricingVOdoc.getInputstreamdoc2();
					  fileExtn = insPricingVOdoc.getAttachmentname2();
			    	  }
		    	  if(filename.equalsIgnoreCase("file3")){
					  iStream= insPricingVOdoc.getInputstreamdoc3();
					  fileExtn = insPricingVOdoc.getAttachmentname3();

			    	  }
		    	  if(filename.equalsIgnoreCase("file4")){
					  iStream= insPricingVOdoc.getInputstreamdoc4();
					  fileExtn = insPricingVOdoc.getAttachmentname4();

			    	  }
		    	  if(filename.equalsIgnoreCase("file5")){
					  iStream= insPricingVOdoc.getInputstreamdoc5();
					  fileExtn = insPricingVOdoc.getAttachmentname5();
			    	  }
		    	 
		    	  
			  if((iStream!=null) && (!iStream.equals("")))
			  {
	           
	        	 bis = new BufferedInputStream(iStream);
		         baos=new ByteArrayOutputStream();
		         String lowercaseFileExtn = fileExtn.toLowerCase();
		         
		         if ((lowercaseFileExtn.endsWith("jpeg")|| (lowercaseFileExtn.endsWith("jpg"))|| (lowercaseFileExtn.endsWith("gif")) ||(lowercaseFileExtn.endsWith("png")))){
		    	          
		        	    InputStream in=iStream;
		        	    ServletOutputStream out = response.getOutputStream();
		        	    byte[] buf = new byte[10*1024];
		        	    int len;
		        	    while ((len = in.read(buf)) > 0) {
		        	    out.write(buf, 0, len);
		        	    }
		        	    in.close();
		        	    out.flush();
		        	    out.close();
		        	    }//end image format
		         else{
		           
		         
		         if(lowercaseFileExtn.endsWith("doc") || lowercaseFileExtn.endsWith("docx"))
		    		{
		    			response.setContentType("application/msword");
		    			response.addHeader("Content-Disposition","attachment; filename="+fileExtn);
		    		}//end of if(fileExtn.endsWith("doc"))
		    		else if(lowercaseFileExtn.endsWith("pdf"))
		    		{
		    			response.setContentType("application/pdf");
		    			response.addHeader("Content-Disposition","attachment; filename="+fileExtn);
		    		}//end of else if(fileExtn.endsWith("pdf"))
		    		else if(lowercaseFileExtn.endsWith("xls") || lowercaseFileExtn.endsWith("xlsx"))
		    		{
		    			response.setContentType("application/vnd.ms-excel");
		    			response.addHeader("Content-Disposition","attachment; filename="+fileExtn);
		    		}//end of else if(fileExtn.endsWith("xls"))
		    		else if(lowercaseFileExtn.endsWith("txt")){
				    		response.setContentType("text/plain");
				    		response.setHeader("Content-Disposition","attachment;filename"+fileExtn);
		    		}
		         
	         
	           int ch;
               while ((ch = bis.read()) != -1) baos.write(ch);
               sos = response.getOutputStream();
               baos.writeTo(sos);  
               baos.flush();      
               sos.flush(); 
		         }//end document format
			  }//end   istream null
			  else{
  				File f = new File(strFileNoRecords);
  	    		if(f.isFile() && f.exists()){
  	    			fis = new FileInputStream(f);
  	    		}//end of if(strFile !="")
  	    		BufferedInputStream bist = new BufferedInputStream(fis);
  	    		baos=new ByteArrayOutputStream();
  	    		int ch;
  	    		while ((ch = bist.read()) != -1)
  	    		{
  	    			baos.write(ch);
  	    		}//end of while ((ch = bis.read()) != -1)
  	    		sos = response.getOutputStream();
  	    		baos.writeTo(sos);
			  }
	           
		            }catch(Exception exp)
		            	{
		            		return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
		            	}//end of catch(Exception exp)
		          finally{
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
	}
    
    
    
	public ActionForward doViewGroupProfile(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    try{
    setLinks(request);
 //   DynaActionForm frmSwPricing= (DynaActionForm)form;
    DynaValidatorForm frmSwPricing = (DynaValidatorForm)form;
   InsPricingVO insPricingVO=null;


    InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();

    /*long Seq_Id=(long)request.getSession().getAttribute("GroupProfileSeqID");
   
    
    insPricingVO=new InsPricingVO();
	TableData tableData = TTKCommon.getTableData(request);
	
	
	if(!(TTKCommon.checkNull(request.getParameter(strRownum)).equals("")))
	{
		insPricingVO = (InsPricingVO)tableData.getRowInfo(Integer.parseInt((String)(frmSwPricing).
																				get(strRownum)));
		//insPricingVO= insuranceObject.swSelectPricingList(insPricingVO.getGroupProfileSeqID());

		//add the selected item to the web board and make it as default selected
		//this.addToWebBoard(insPricingVO, request);
		frmSwPricing.set("Message","N"); 
		//this.valueObjectToForm(productVO, frmProductDetail);				
	}//end if(!(TTKCommon.checkNull(request.getParameter(strRownum)).equals("")))
	else if(TTKCommon.getWebBoardId(request) != null)
	{
		//if web board id is found, set it as current web board id
		Long lProdPolicySeqId=TTKCommon.getWebBoardId(request);
		//get the product details from the Dao object
		//insPricingVO= insuranceObject.swSelectPricingList(lProdPolicySeqId);
		frmSwPricing.set("Message","N"); 
		
	}//end of else if(TTKCommon.getWebBoardId(request) != null)
	

 if(Seq_Id > 0)
	{
		insPricingVO.setGroupProfileSeqID((long)request.getSession().getAttribute("GroupProfileSeqID"));
		insPricingVO= insuranceObject.swSelectPricingList(insPricingVO.getGroupProfileSeqID());
		frmSwPricing = (DynaActionForm)FormUtils.setFormValues("frmSwPricing",insPricingVO,
				this,mapping,request);
		frmSwPricing.set("Message","N"); 
	}
	else
	{
		
		frmSwPricing.set("Message",request.getParameter("Message")); 
		frmSwPricing.set("Message","Y"); 
    	TTKException expTTK = new TTKException();
		expTTK.setMessage("error.pricing.required");
		throw expTTK;
	}
	String pricingNumberAlert  = "Please note the pricing reference number "+insPricingVO.getPricingRefno()+" for future";
	request.getSession().setAttribute("pricingNumberAlert", pricingNumberAlert);
	 request.getSession().setAttribute("GroupProfileSeqID",insPricingVO.getGroupProfileSeqID());
	 request.getSession().setAttribute("completeSaveYN",insPricingVO.getCompleteSaveYN());
	 
	 request.getSession().setAttribute("ClientCode",insPricingVO.getClientCode());
	 request.getSession().setAttribute("totalNoOfLives",insPricingVO.getTotalCovedLives());
	 request.getSession().setAttribute("TotalMaternityLives",insPricingVO.getTotalLivesMaternity());
	
	 request.getSession().setAttribute("DentalLivesYN",insPricingVO.getDentalLivesYN());
	 request.getSession().setAttribute("OpticalLivesYN",insPricingVO.getOpticalLivesYN());
	 request.getSession().setAttribute("newdataentry", "N");	
		
	 frmSwPricing = (DynaValidatorForm)FormUtils.setFormValues("frmSwPricing",insPricingVO,
				this,mapping,request);*/


	 //request.getSession().setAttribute("frmSwPricing",frmSwPricing);
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


			  DynaActionForm frmSwPricingFactorMaster= (DynaActionForm)form;
			 
			  InsPricingVO  insPricingVO =null;
			  
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
	

	private void checkfileExtention(String lowercaseFileExtn) throws TTKException {
		// TODO Auto-generated method stub
    	String flag= "";
        if ((lowercaseFileExtn.endsWith("jpeg")|| (lowercaseFileExtn.endsWith("jpg"))|| (lowercaseFileExtn.endsWith("gif")) ||(lowercaseFileExtn.endsWith("png"))
        	|| (lowercaseFileExtn.endsWith("zip")) || (lowercaseFileExtn.endsWith("pdf")) || (lowercaseFileExtn.endsWith("xls")) || (lowercaseFileExtn.endsWith("xlsx"))
        	|| (lowercaseFileExtn.endsWith("doc")) || (lowercaseFileExtn.endsWith("docx")) || (lowercaseFileExtn.endsWith("txt")) ))
        {
        	flag="success";
        }else{
        	TTKException expTTK = new TTKException();
			expTTK.setMessage("error.Upload.required");
			throw expTTK;
        }
	}
	
	
	public InsPricingVO setSelectModeform(InsPricingVO insPricingVO) throws Exception{
		   
		  
	    String outpatientBenefit = insPricingVO.getOutpatientBenefit(); 
	    String opdeductableserviceYN = insPricingVO.getOpdeductableserviceYN();
	    String alAhlihospital = insPricingVO.getAlAhlihospital();
	    String alAhlihospOPservices = insPricingVO.getAlAhlihospOPservices();
	   
	    
		
		if(outpatientBenefit.equalsIgnoreCase("N") ||outpatientBenefit.equalsIgnoreCase(""))
		{
			
			 insPricingVO.setOpCopayList("");
			 insPricingVO.setOpDeductableList("");
			 insPricingVO.setOpdeductableserviceYN("");
			 insPricingVO.setOpCopaypharmacy("");
			 insPricingVO.setOpInvestigation("");
			 insPricingVO.setOpCopyconsultn("");
			 insPricingVO.setOpCopyothers("");
			 
			 insPricingVO.setAlAhlihospOPservices("");
			 insPricingVO.setOpCopyalahlihosp("");	 
			 insPricingVO.setOpPharmacyAlAhli("");
			 insPricingVO.setOpConsultAlAhli("");
			 insPricingVO.setOpInvestnAlAhli("");
			 insPricingVO.setOpothersAlAhli("");
		}
		else if("Y".equals(outpatientBenefit)){
			
			if("".equals(opdeductableserviceYN)){
				 insPricingVO.setOpCopayList("");
				 insPricingVO.setOpDeductableList("");
				 insPricingVO.setOpCopaypharmacy("");
				 insPricingVO.setOpInvestigation("");
				 insPricingVO.setOpCopyconsultn("");
				 insPricingVO.setOpCopyothers("");
			
			}else if("Y".equals(opdeductableserviceYN))
				{
				 insPricingVO.setOpCopaypharmacy("");
				 insPricingVO.setOpInvestigation("");
				 insPricingVO.setOpCopyconsultn("");
				 insPricingVO.setOpCopyothers("");
				}
			else if("N".equals(opdeductableserviceYN)){
				 insPricingVO.setOpCopayList("");
				 insPricingVO.setOpDeductableList("");
				}
			
			//alahli condition
			  if(("N".equals(alAhlihospital)) || ("".equals(alAhlihospital)))
				{
				     insPricingVO.setAlAhlihospOPservices("");
					 insPricingVO.setOpCopyalahlihosp("");	 
					 insPricingVO.setOpPharmacyAlAhli("");
					 insPricingVO.setOpConsultAlAhli("");
					 insPricingVO.setOpInvestnAlAhli("");
					 insPricingVO.setOpothersAlAhli("");
					
				}	
				if("".equals(alAhlihospOPservices)){
					 insPricingVO.setOpCopyalahlihosp("");	 
					 insPricingVO.setOpPharmacyAlAhli("");
					 insPricingVO.setOpConsultAlAhli("");
					 insPricingVO.setOpInvestnAlAhli("");
					 insPricingVO.setOpothersAlAhli("");
				}else if("Y".equals(alAhlihospOPservices))
					{
					 insPricingVO.setOpPharmacyAlAhli("");
					 insPricingVO.setOpConsultAlAhli("");
					 insPricingVO.setOpInvestnAlAhli("");
					 insPricingVO.setOpothersAlAhli("");
					}
				else if("N".equals(alAhlihospOPservices)){
					 insPricingVO.setOpCopyalahlihosp("");	 
					 }
		
		}
		
		//optial dental maternity
		 if(insPricingVO.getOpticalYN().equalsIgnoreCase("N")){
				insPricingVO.setOpticalLivesYN("N"); 
				insPricingVO.setOpticalLimitList(""); 
				insPricingVO.setOpticalCopayList(""); 
			}else if(insPricingVO.getOpticalYN().equalsIgnoreCase("Y")){
				insPricingVO.setOpticalLivesYN("Y"); 
			}
			if(insPricingVO.getDentalYN().equalsIgnoreCase("N")){
				insPricingVO.setDentalLivesYN("N"); 
				insPricingVO.setDentalLimitList(""); 
				insPricingVO.setDentalcopayList(""); 
				insPricingVO.setOrthodonticsCopay(""); 
			}else if(insPricingVO.getDentalYN().equalsIgnoreCase("Y")){
				insPricingVO.setDentalLivesYN("Y"); 
			}
			if(insPricingVO.getMaternityYN().equalsIgnoreCase("N")){
				insPricingVO.setMaternityLimitList(""); 
			}
		
		return insPricingVO;
	    }
	    
	public ActionForward doChangeCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doChangeCorporate method of BatchPolicyAction");
			setLinks(request);
			DynaActionForm frmBatch = (DynaActionForm)request.getSession().getAttribute("frmBatch");
			//Building the caption
			StringBuffer strAddCaption= new StringBuffer();
			DynaActionForm frmSwPricing=(DynaActionForm)form;
			frmSwPricing.set("caption",strAddCaption.toString());
			request.getSession().setAttribute("frmSwPricing",frmSwPricing);
			return mapping.findForward(strChngCorporate);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
		}//end of catch(Exception exp)
	}//end of doChangeCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
	public ActionForward doDefaultTendMaster(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doDefaultTendMaster method of SwInsPricingFactorMasterAction");
			setLinks(request);
			
			DynaActionForm frmSwPricingFactorMaster = (DynaActionForm)form;
            MasterFactorVO masterFactorVO = null;
            InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();

            masterFactorVO = insuranceObject.getPriceTrendMaster(masterFactorVO);
            
            frmSwPricingFactorMaster= (DynaActionForm)FormUtils.setFormValues("frmSwPricingFactorMaster",masterFactorVO, this, mapping, request);
            
            request.getSession().setAttribute("frmSwPricingFactorMaster", frmSwPricingFactorMaster);
			
            return mapping.findForward(strProcingTrendMaster);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
		}//end of catch(Exception exp)	
			
			
	}// end of doDefaultTendMaster(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	
	
	public ActionForward doSaveTendMaster(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSaveTendMaster method of SwInsPricingFactorMasterAction");
			setLinks(request);
			
			DynaActionForm frmSwPricingFactorMaster = (DynaActionForm)form;
            MasterFactorVO masterFactorVO = new MasterFactorVO();
            InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
            
            masterFactorVO = (MasterFactorVO)FormUtils.getFormValues(frmSwPricingFactorMaster, this, mapping, request);

            int result = insuranceObject.savePriceTrendMaster(masterFactorVO);

            masterFactorVO = insuranceObject.getPriceTrendMaster(masterFactorVO);
            
            frmSwPricingFactorMaster= (DynaActionForm)FormUtils.setFormValues("frmSwPricingFactorMaster",	masterFactorVO, this, mapping, request);
            
            request.getSession().setAttribute("frmSwPricingFactorMaster", frmSwPricingFactorMaster);
            
			return mapping.findForward(strProcingTrendMaster);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
		}//end of catch(Exception exp)	
			
			
	}// end of doDefaultTendMaster(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
	 private ArrayList<Object> populateSearchCriteria(DynaActionForm frmSwPricingHome,
				HttpServletRequest request)throws TTKException
		{
			//build the column names along with their values to be searched
			ArrayList<Object> alSearchParams = new ArrayList<Object>();
			//alSearchParams.add(new SearchCriteria("EMPANELED_DATE", (String)frmSearchInsurance.get("sEmpanelDate")));
			alSearchParams.add(TTKCommon.checkNull(frmSwPricingHome.get("pricingRefno")));
			alSearchParams.add(TTKCommon.checkNull(frmSwPricingHome.get("previousPolicyNo")));
			alSearchParams.add(TTKCommon.checkNull(frmSwPricingHome.get("clientCode")));

	    	return alSearchParams;
		}
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


