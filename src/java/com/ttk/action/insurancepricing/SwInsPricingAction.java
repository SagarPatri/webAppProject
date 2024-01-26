package com.ttk.action.insurancepricing;



import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import com.ttk.action.TTKAction;
import com.ttk.action.finance.Batch;
import com.ttk.action.finance.FileDetail;
import com.ttk.action.finance.MemberData;
import com.ttk.action.finance.MemberDetails;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.business.claims.ClaimBatchManager;
import com.ttk.business.claims.ClaimManager;
import com.ttk.business.empanelment.InsuranceManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
import com.ttk.dto.insurancepricing.AgeMasterVO;
import com.ttk.dto.insurancepricing.InsPricingVO;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ProductVO;
import com.ttk.dto.claims.BatchVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.common.Toolbar;
import com.ttk.dto.empanelment.InsuranceDetailVO;
import com.ttk.dto.empanelment.InsuranceVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import formdef.plugin.util.FormUtils;








import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class SwInsPricingAction extends TTKAction  {
	private static Logger log = Logger.getLogger( SwInsPricingAction.class );
	  
	private static final String strForward="Forward";
	private static final String strBackward="Backward";
	private static final String strPricingHome="pricinghome";
    private static final String strGroupprofile="groupprofile";
    private static final String strSaveproceed="saveproceed";
    private static final String strIncomeprofile="incomeprofile";
    private static final String strInputscreen3="inputscreen3";
    
    
    private static final String strInsError="insurance";
	private static final String strChngCorporate="changecorporate";
	private static final String strRownum="rownum";
	public ActionForward doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    	try{
    		log.debug("Inside the doDefault method of InsPricingAction");
    		setLinks(request);
    		DynaActionForm frmSwPricingHome = (DynaActionForm)form;
            //((DynaActionForm)form).initialize(mapping);//reset the form data
    		TableData tableData = TTKCommon.getTableData(request);
    		//remove the selected office from the session
            request.getSession().removeAttribute("SelectedOffice");
            tableData = new TableData();//create new table data object
            //create the required grid table
            tableData.createTableInfo("SwInsurancePricingTable",new ArrayList());
            request.getSession().setAttribute("tableData",tableData);
            ((DynaActionForm)form).initialize(mapping);//reset the form data
    		if(TTKCommon.checkNull(request.getParameter("Message")).equals("Y"))
			{
    			frmSwPricingHome.set("Message",request.getParameter("Message")); 
            	TTKException expTTK = new TTKException();
				expTTK.setMessage("error.pricing.required");
				throw expTTK;
				
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
    		else
    		{
    			frmSwPricingHome.set("Message","N");  	
    		}
    		long seqid=0;
    		 request.getSession().setAttribute("GroupProfileSeqID",seqid);
    		 request.getSession().setAttribute("frmSwPricingHome",frmSwPricingHome);
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
                tableData.createTableInfo("SwInsurancePricingTable",null);
                tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
                tableData.modifySearchData("search");
            }//end of else
            ArrayList alInsuranceProfileList= insuranceObject.getSwInsuranceProfileList(tableData.getSearchData());
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
	 request.getSession().setAttribute("ClientCode",insPricingVO.getClientCode());
	
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
    
    public ActionForward fetchScreen1(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    try{
    	
    setLinks(request);
    
    String portfloLevel =request.getParameter("portfloLevel");
    String pastPolicyNumber =request.getParameter("previousPolicyNo");
    String pastProductNm =request.getParameter("previousProductName");
    String incomeGroup =request.getParameter("incomeGroup");
    String portVal =request.getParameter("portVal");
    String incVal =request.getParameter("incVal");
    
    // start
    
    String renewalYN =request.getParameter("renewalYN");
    String clientcodeId =request.getParameter("clientcodeId");
    DynaActionForm frmSwPricing= (DynaActionForm)form;
   // String clientName =request.getParameter("clientName");
    String clientName =frmSwPricing.getString("clientName");
    
    String authorityType =request.getParameter("authorityType");
  
    
    String administrativeServiceType =request.getParameter("administrativeServiceType");
    String ast=administrativeServiceType.replace(" ", "+");
    String insuranceCompanyName =request.getParameter("insuranceCompanyName");
    String insuranceCompanyNameOth =request.getParameter("insuranceCompanyNameOth");
    String existCompProd1 =request.getParameter("existCompProd1");
    
    String existCompProd2 =request.getParameter("existCompProd2");
    String coverStartDate =request.getParameter("coverStartDate");
    String coverEndDate =request.getParameter("coverEndDate");
    String totalCovedLives =request.getParameter("totalCovedLives");
    String PricingRefno=request.getParameter("pricingRefno");
    
    
    // end
    
    
     
   InsPricingVO insPricingVO=new InsPricingVO();
   insPricingVO.setPortfloLevel(portfloLevel);
   insPricingVO.setPreviousPolicyNo(pastPolicyNumber);
   insPricingVO.setPreviousProductName(pastProductNm);
   insPricingVO.setIncomeGroup(incomeGroup);
   insPricingVO.setGroupProfileSeqID( (long)request.getSession().getAttribute("GroupProfileSeqID"));//bk

    InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
    InsPricingVO dbinsPricingVO=null;
    dbinsPricingVO= insuranceObject.swFetchScreen1(insPricingVO);
    
    dbinsPricingVO.setRenewalYN(renewalYN);
    dbinsPricingVO.setClientCode(clientcodeId);
    dbinsPricingVO.setClientName(clientName);
    dbinsPricingVO.setAuthorityType(authorityType);
    dbinsPricingVO.setAdministrativeServiceType(ast);
    dbinsPricingVO.setInsuranceCompanyName(insuranceCompanyName);
    dbinsPricingVO.setInsuranceCompanyNameOth(insuranceCompanyNameOth);
    dbinsPricingVO.setExistCompProd1(existCompProd1);;
    dbinsPricingVO.setExistCompProd2(existCompProd2);
    dbinsPricingVO.setCoverStartDate(TTKCommon.getUtilDate(coverStartDate));
    dbinsPricingVO.setCoverEndDate(TTKCommon.getUtilDate(coverEndDate));
   /* insPricingVO.setCoverStartDate(new Date(coverStartDate));
	insPricingVO.setCoverEndDate(new Date(coverEndDate));*/
    dbinsPricingVO.setTotalCovedLives(totalCovedLives);
    
    
    
    dbinsPricingVO.setPortfloLevel(portVal);
    dbinsPricingVO.setPreviousPolicyNo(pastPolicyNumber);
    dbinsPricingVO.setPreviousProductName(pastProductNm);
    dbinsPricingVO.setIncomeGroup(incVal);
    
    dbinsPricingVO.setPricingRefno(PricingRefno);//BK
  //  frmSwPricing.set("pricingRefno", dbinsPricingVO.getPricingRefno());
    
	frmSwPricing = (DynaActionForm)FormUtils.setFormValues("frmSwPricing",dbinsPricingVO,
			this,mapping,request);
	   


	
	 request.setAttribute("fetchData","Y");
	 
	// this.addToWebBoard(dbinsPricingVO,request);//bk
	
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
            
            insPricingVO.setLoadingForInpatient("0");
            insPricingVO.setLoadingForOutpatient("0");
            insPricingVO.setTrendFactor("5");
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
return this.getForward(strPricingHome, mapping, request);   //finally return to the grid screen
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
				return this.getForward(strPricingHome, mapping, request);   //finally return to the grid screen
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

    long Seq_Id=(long)request.getSession().getAttribute("GroupProfileSeqID");
   
    
    insPricingVO=new InsPricingVO();
	TableData tableData = TTKCommon.getTableData(request);
	
	
	if(!(TTKCommon.checkNull(request.getParameter(strRownum)).equals("")))
	{
		insPricingVO = (InsPricingVO)tableData.getRowInfo(Integer.parseInt((String)(frmSwPricing).
																				get(strRownum)));
		insPricingVO= insuranceObject.swSelectPricingList(insPricingVO.getGroupProfileSeqID());

		//add the selected item to the web board and make it as default selected
		this.addToWebBoard(insPricingVO, request);
		frmSwPricing.set("Message","N"); 
		//this.valueObjectToForm(productVO, frmProductDetail);				
	}//end if(!(TTKCommon.checkNull(request.getParameter(strRownum)).equals("")))
	else if(TTKCommon.getWebBoardId(request) != null)
	{
		//if web board id is found, set it as current web board id
		Long lProdPolicySeqId=TTKCommon.getWebBoardId(request);
		//get the product details from the Dao object
		insPricingVO= insuranceObject.swSelectPricingList(lProdPolicySeqId);
		frmSwPricing.set("Message","N"); 
		
	}//end of else if(TTKCommon.getWebBoardId(request) != null)
	
/*
 if(Seq_Id > 0)
	{
		insPricingVO.setGroupProfileSeqID((long)request.getSession().getAttribute("GroupProfileSeqID"));
		insPricingVO= insuranceObject.swSelectPricingList(insPricingVO.getGroupProfileSeqID());
		frmSwPricing = (DynaActionForm)FormUtils.setFormValues("frmSwPricing",insPricingVO,
				this,mapping,request);
		frmSwPricing.set("Message","N"); 
	}*/
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
	
	 
	 request.getSession().setAttribute("ClientCode",insPricingVO.getClientCode());
	
	
	 
		
	 frmSwPricing = (DynaValidatorForm)FormUtils.setFormValues("frmSwPricing",insPricingVO,
				this,mapping,request);


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
    
	
	public ActionForward doIncomeProfile(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try
		{
			setLinks(request);
			
			
			 InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();


			  DynaActionForm frmSwIncomeProfile= (DynaActionForm)form;
			 
			
			  DynaActionForm frmSwPricing=(DynaActionForm)request.getSession().getAttribute("frmSwPricing");
				
			 Object group_seq_id=frmSwPricing.get("groupProfileSeqID");	
			 
			
			 
			if(group_seq_id==null)	{
				frmSwIncomeProfile.initialize(mapping);
				frmSwIncomeProfile.set("Message",request.getParameter("Message")); 
				frmSwIncomeProfile.set("Message","Y"); 
				
		    	TTKException expTTK = new TTKException();
				expTTK.setMessage("error.pricing.required");
				throw expTTK;
			}
			
			// added govind
			  String pricingNumberAlert=  "";
			  if("Y".equalsIgnoreCase(request.getParameter("proceedbutton"))){	 
				 pricingNumberAlert=  (String)request.getSession().getAttribute("pricingNumberAlert");
			   }
			  
			Object[] tableObjects= insuranceObject.getBenefitvalueAfter(new Long(group_seq_id.toString()));
			
			HashMap<String, String> tablesNamesHM=(HashMap<String, String>)tableObjects[0];
			HashMap<String, ArrayList<AgeMasterVO>> tablesDataHM=(HashMap<String, ArrayList<AgeMasterVO>>)tableObjects[1];
			//table-1
			frmSwIncomeProfile.set("table1Data",tablesDataHM.get("table1Data") );
			frmSwIncomeProfile.set("table1HeaderName",tablesNamesHM.get("table1HeaderName") );
			frmSwIncomeProfile.set("table1totalLives",tablesNamesHM.get("table1totalLives") );
			//table-2
			frmSwIncomeProfile.set("table2Data",tablesDataHM.get("table2Data") );
			frmSwIncomeProfile.set("table2HeaderName",tablesNamesHM.get("table2HeaderName") );
			frmSwIncomeProfile.set("table2totalLives",tablesNamesHM.get("table2totalLives") );
			//table-3
			frmSwIncomeProfile.set("table3Data",tablesDataHM.get("table3Data") );
			frmSwIncomeProfile.set("table3HeaderName",tablesNamesHM.get("table3HeaderName") );
			frmSwIncomeProfile.set("table3totalLives",tablesNamesHM.get("table3totalLives") );
			
			//table-4
			frmSwIncomeProfile.set("table4Data",tablesDataHM.get("table4Data") );
			frmSwIncomeProfile.set("table4HeaderName",tablesNamesHM.get("table4HeaderName") );
			frmSwIncomeProfile.set("table4totalLives",tablesNamesHM.get("table4totalLives") );
			
			
			frmSwIncomeProfile.set("pricingNumberAlert", pricingNumberAlert);
			request.getSession().setAttribute("frmSwIncomeProfile", frmSwIncomeProfile);
			
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
    
	
	//for save()
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    int fileSize = 20*1024*1024;
    
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
		
		/*if(insPricingVO.getGroupProfileSeqID() == null)
		{
			newdata = "Y";
		}*/
		
		//bk
       //file-1
		 FileOutputStream outputStream = null;
		  String fileDir="";
		  
		 
			 insPricingVO.setSourceAttchments1(formFile1);	
			 insPricingVO.setSourceAttchments2(formFile2);
			 insPricingVO.setSourceAttchments3(formFile3);
			 insPricingVO.setSourceAttchments4(formFile4);
			 insPricingVO.setSourceAttchments5(formFile5);
			
			
				 
			 insPricingVO.setPreviousPolicyNo(request.getParameter("previousPolicyNo")==null ? "":request.getParameter("previousPolicyNo"));			 
			 insPricingVO.setPreviousProductName(request.getParameter("previousProductName")==null ? "":request.getParameter("previousProductName"));
			 
			 pricingNumberAlert = " Please note the pricing reference number "+insPricingVO.getPricingRefno()+" for future";//bk
			 request.getSession().setAttribute("pricingNumberAlert", pricingNumberAlert);//bk
			
			 String alertStatus="";
			 String alertMsg="";
			 String [] valStr= new String[3];
			 
			 if(singlebutton.equalsIgnoreCase("save")){ 
				
				  valStr= insuranceObject.callDbValidation(insPricingVO);	   
			 }
				
				 if("Y".equals(valStr[0])){
					 
					 alertMsg="Coverage start date entered above in the Corporate details section is less than the end date of the most recent past policy available in the system. Details of the same can be checked in the first table of Projected Claims - Working tab. Do you want to proceed?";
					// alertMsg="Coverage start date entered above in the Corporate details section is less than the end date of the most recent past policy available in the system.  Do you want to proceed?";
						
					 alertStatus="Y";
				 }else   if("Y".equals(valStr[1])){

					 alertMsg="This particular policy number "+insPricingVO.getPreviousPolicyNo()+" is not for "+insPricingVO.getClientName()+". Please enter correct policy number";
					 alertStatus="Y";
					 
				 }else  if("Y".equals(valStr[2])){
					
					 alertMsg="This policy with the chosen product has more than 20% lives with no salary information and therefore past experience for the given income group may not be a true representation of the actual experience. Do you still want to continue?";
					 alertStatus="Y";
					
				 } 
			 		 

	if("Y".equals(alertStatus)){
		request.setAttribute("alertStatus", alertStatus);
		request.setAttribute("alertMsg", alertMsg);
		
		request.getSession().setAttribute("confirmationSaveDetails",insPricingVO );
		request.setAttribute("OLB_JS_FUNCTION","saveConfirmation('"+alertMsg+"');");
		return this.getForward(strGroupprofile, mapping, request);
	}else {
		
	   
		if(singlebutton.equalsIgnoreCase("save")){
			
			ArrayList al= insuranceObject.swSavePricingList(insPricingVO);	//A
			 Long lpricingSeqId=(Long)al.get(0);
			 String errorCode=(String)al.get(1);
			 String errorMsg=(String)al.get(2);
			 if(lpricingSeqId > 0)successMsg="Details Added Successfully";
    		 else successMsg="Details Updated Successfully";
			 
			 
			 insPricingVO= insuranceObject.swSelectPricingList(lpricingSeqId);
			 pricingNumberAlert = " Please note the pricing reference number "+insPricingVO.getPricingRefno()+" for future";
			
			 frmSwPricing= (DynaActionForm)FormUtils.setFormValues("frmSwPricing",	insPricingVO, this, mapping, request);
			 
			 //file-1
			 if(!("").equals(formFile1.getFileName())){
				  
			String pricingNumberAlert1 =insPricingVO.getPricingRefno();
			
			    String attachmentname1=request.getParameter("attachmentname1");
			    if(!("").equals(attachmentname1)){
				  File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir")+"/"+pricingNumberAlert1+"/"+attachmentname1.trim());
				    file.delete();   
				 }
			 
			 	 	 
			  fileDir=TTKPropertiesReader.getPropertyValue("dubaipriceDir");
			  fileDir+=pricingNumberAlert1;		  
	          File file= new File(fileDir);
	          if(!file.exists())file.mkdirs();
	          outputStream = new FileOutputStream(fileDir+"/"+formFile1);
	          outputStream.write(formFile1.getFileData());
	          if (outputStream != null)  outputStream.close();
	          String fileExtn 	=  GetFileExtension(formFile1.toString());
	         
			  }
	
			//file-2
			  if(!("").equals(formFile2.getFileName())){
				  
				String  pricingNumberAlert2 =insPricingVO.getPricingRefno();
				
				String attachmentname2=request.getParameter("attachmentname2");
			    if(!("").equals(attachmentname2)){
			    	 File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir2")+"/"+pricingNumberAlert2+"/"+attachmentname2.trim());
				    file.delete();   
				 }
				 	 
				  fileDir=TTKPropertiesReader.getPropertyValue("dubaipriceDir2");
				  fileDir+=pricingNumberAlert2;
		          File file= new File(fileDir);
		          if(!file.exists())file.mkdirs();
		          outputStream = new FileOutputStream(fileDir+"/"+formFile2);
		          outputStream.write(formFile2.getFileData());
		          if (outputStream != null)  outputStream.close();
		          String fileExtn 	=  GetFileExtension(formFile2.toString());
				  }
	       //file-3
	            if(!("").equals(formFile3.getFileName())){
				  
				 String pricingNumberAlert3 =insPricingVO.getPricingRefno();
				 String attachmentname3=request.getParameter("attachmentname3");
				    if(!("").equals(attachmentname3)){
					  File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir3")+"/"+pricingNumberAlert3+"/"+attachmentname3.trim());
					    file.delete();   
					 }
				 	 
				  fileDir=TTKPropertiesReader.getPropertyValue("dubaipriceDir3");
				  fileDir+=pricingNumberAlert3;
		          File file= new File(fileDir);
		          if(!file.exists())file.mkdirs();
		          outputStream = new FileOutputStream(fileDir+"/"+formFile3);
		          outputStream.write(formFile3.getFileData());
		          if (outputStream != null)  outputStream.close();
		          String fileExtn 	=  GetFileExtension(formFile3.toString());
				  }
	         //file-4
	            if(!("").equals(formFile4.getFileName())){
	  			  
	  			String pricingNumberAlert4 =insPricingVO.getPricingRefno();
	  			
	  			String attachmentname4=request.getParameter("attachmentname4");
			    if(!("").equals(attachmentname4)){
				  File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir4")+"/"+pricingNumberAlert4+"/"+attachmentname4.trim());
				    file.delete();   
				 }
			 	 
	  		  fileDir=TTKPropertiesReader.getPropertyValue("dubaipriceDir4");
	  		  fileDir+=pricingNumberAlert4;
	  	          File file= new File(fileDir);
	  	          if(!file.exists())file.mkdirs();
	  	          outputStream = new FileOutputStream(fileDir+"/"+formFile4);
	  	          outputStream.write(formFile4.getFileData());
	  	          if (outputStream != null)  outputStream.close();
	  	          String fileExtn 	=  GetFileExtension(formFile4.toString());
	  			  }
	         //file-5
	            if(!("").equals(formFile5.getFileName())){
	    			  
	    			String  pricingNumberAlert5 =insPricingVO.getPricingRefno();
	 			 	 
	    			String attachmentname5=request.getParameter("attachmentname5");
				    if(!("").equals(attachmentname5)){
					  File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir5")+"/"+pricingNumberAlert5+"/"+attachmentname5.trim());
					    file.delete();   
					 }
	    			  fileDir=TTKPropertiesReader.getPropertyValue("dubaipriceDir5");
	    			  fileDir+=pricingNumberAlert5;
	    	          File file= new File(fileDir);
	    	          if(!file.exists())file.mkdirs();
	    	          outputStream = new FileOutputStream(fileDir+"/"+formFile5);
	    	          outputStream.write(formFile5.getFileData());
	    	          if (outputStream != null)  outputStream.close();
	    	          String fileExtn 	=  GetFileExtension(formFile5.toString());
	    			  }
	            //end file-5
			 frmSwPricing.set("previousProductName", insPricingVO.getPreviousProductName());
			 frmSwPricing.set("pricingNumberAlert", pricingNumberAlert);
			 
			 request.getSession().setAttribute("pricingNumberAlert", pricingNumberAlert);//bk 
			 
			 frmSwPricing.set("authorityType", insPricingVO.getAuthorityType());
		
			 request.getSession().setAttribute("insCmpNm",insPricingVO.getInsuranceCompanyName());
			 request.getSession().setAttribute("frmSwPricing",frmSwPricing);
			 request.getSession().setAttribute("ClientCode", insPricingVO.getClientCode());	
			 request.getSession().setAttribute("pricingRefNo", insPricingVO.getPricingRefno());	
			
			 request.getSession().setAttribute("GroupProfileSeqID",insPricingVO.getGroupProfileSeqID());//bk
			 request.setAttribute("successMsg", successMsg);
			 
			 this.addToWebBoard(insPricingVO,request);//bk
			 
			  return this.getForward(strGroupprofile, mapping, request);
		    }
		
		else{
			
			 return mapping.findForward(strSaveproceed);
		}
	
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

	//bk
	public ActionForward doViewUploadDocs1(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException {
			
	 ByteArrayOutputStream baos=null;
	 OutputStream sos = null;
	 FileInputStream fis = null; 
	 BufferedInputStream bis =null;
	
	
	try {
		
		setLinks(request);
	    DynaActionForm frmSwPricing= (DynaActionForm)form;
	    // String  pricingNumberAlert=  (String)request.getSession().getAttribute("pricingNumberAlert");
	    /*String pricingNumberAlert=frmSwPricing.getString("pricingRefno");*/
	    String pricingNumberAlert=request.getParameter("pricingrRefNo");
	    
		  
		 String fileName=request.getParameter("fileName1");
		 
		  File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir")+"/"+pricingNumberAlert+"/"+fileName.trim());
		  response.setContentType("application/txt");
	      response.setHeader("Content-Disposition", "attachment;filename="+fileName);                                            
              fis= new FileInputStream(file);
              bis = new BufferedInputStream(fis);
              baos=new ByteArrayOutputStream();
              int ch;
                    while ((ch = bis.read()) != -1) baos.write(ch);
                    sos = response.getOutputStream();
                    baos.writeTo(sos);  
                    baos.flush();      
                    sos.flush();  
		
	} 
	catch(Exception exp)
	{
		return this.processExceptions(request,mapping,new TTKException(exp,strInsError));
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

	//file-2
	public ActionForward doViewUploadDocs2(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException {
			
	 ByteArrayOutputStream baos=null;
	 OutputStream sos = null;
	 FileInputStream fis = null; 
	 BufferedInputStream bis =null;
	try {
		String pricingNumberAlert=request.getParameter("pricingrRefNo");
		 String fileName=request.getParameter("fileName1");
		  //File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir2")+fileName.trim());
		  File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir2")+"/"+pricingNumberAlert+"/"+fileName.trim());
		  response.setContentType("application/txt");
	      response.setHeader("Content-Disposition", "attachment;filename="+fileName);                                            
              fis= new FileInputStream(file);
              bis = new BufferedInputStream(fis);
              baos=new ByteArrayOutputStream();
              int ch;
                    while ((ch = bis.read()) != -1) baos.write(ch);
                    sos = response.getOutputStream();
                    baos.writeTo(sos);  
                    baos.flush();      
                    sos.flush();  
		
	} 
	catch(Exception exp)
	{
		return this.processExceptions(request,mapping,new TTKException(exp,strInsError));
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
	
	//file-3
	public ActionForward doViewUploadDocs3(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException {
			
	 ByteArrayOutputStream baos=null;
	 OutputStream sos = null;
	 FileInputStream fis = null; 
	 BufferedInputStream bis =null;
	 
	try {
		String pricingNumberAlert=request.getParameter("pricingrRefNo");
		 String fileName=request.getParameter("fileName1");
		  //File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir3")+fileName.trim());
		  File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir3")+"/"+pricingNumberAlert+"/"+fileName.trim());
		  response.setContentType("application/txt");
	      response.setHeader("Content-Disposition", "attachment;filename="+fileName);                                            
              fis= new FileInputStream(file);
              bis = new BufferedInputStream(fis);
              baos=new ByteArrayOutputStream();
              int ch;
                    while ((ch = bis.read()) != -1) baos.write(ch);
                    sos = response.getOutputStream();
                    baos.writeTo(sos);  
                    baos.flush();      
                    sos.flush();  
		
	} 
	catch(Exception exp)
	{
		return this.processExceptions(request,mapping,new TTKException(exp,strInsError));
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
	
//file-4
	public ActionForward doViewUploadDocs4(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException {
			
	 ByteArrayOutputStream baos=null;
	 OutputStream sos = null;
	 FileInputStream fis = null; 
	 BufferedInputStream bis =null;
	try {
		String pricingNumberAlert=request.getParameter("pricingrRefNo");
		 String fileName=request.getParameter("fileName1");
		  //File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir4")+fileName.trim());
		  File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir4")+"/"+pricingNumberAlert+"/"+fileName.trim());
		  response.setContentType("application/txt");
	      response.setHeader("Content-Disposition", "attachment;filename="+fileName);                                            
              fis= new FileInputStream(file);
              bis = new BufferedInputStream(fis);
              baos=new ByteArrayOutputStream();
              int ch;
                    while ((ch = bis.read()) != -1) baos.write(ch);
                    sos = response.getOutputStream();
                    baos.writeTo(sos);  
                    baos.flush();      
                    sos.flush();  
		
	} 
	catch(Exception exp)
	{
		return this.processExceptions(request,mapping,new TTKException(exp,strInsError));
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
	
//file-5
	public ActionForward doViewUploadDocs5(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException {
			
	 ByteArrayOutputStream baos=null;
	 OutputStream sos = null;
	 FileInputStream fis = null; 
	 BufferedInputStream bis =null;
	 
	try {
		String pricingNumberAlert=request.getParameter("pricingrRefNo");
		 String fileName=request.getParameter("fileName1");
		  //File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir5")+fileName.trim());
		 File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir5")+"/"+pricingNumberAlert+"/"+fileName.trim());
		  response.setContentType("application/txt");
	      response.setHeader("Content-Disposition", "attachment;filename="+fileName);                                            
              fis= new FileInputStream(file);
              bis = new BufferedInputStream(fis);
              baos=new ByteArrayOutputStream();
              int ch;
                    while ((ch = bis.read()) != -1) baos.write(ch);
                    sos = response.getOutputStream();
                    baos.writeTo(sos);  
                    baos.flush();      
                    sos.flush();  
		
	} 
	catch(Exception exp)
	{
		return this.processExceptions(request,mapping,new TTKException(exp,strInsError));
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
	
	//end bk
	
	
	
	public ActionForward confirmationSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    try{
    setLinks(request);
    DynaActionForm frmSwPricing= (DynaActionForm)form;
    InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
    String successMsg;String pricingNumberAlert = "";
    
    
    InsPricingVO   insPricingVO=(InsPricingVO) request.getSession().getAttribute("confirmationSaveDetails");
    
    //bk-file
 // UPLOAD FILE STARTS
    FormFile formFile1 = (FormFile)frmSwPricing.get("file1");
    FormFile formFile2 = (FormFile)frmSwPricing.get("file2");
    FormFile formFile3 = (FormFile)frmSwPricing.get("file3");
    FormFile formFile4 = (FormFile)frmSwPricing.get("file4");
    FormFile formFile5 = (FormFile)frmSwPricing.get("file5");
    int fileSize = 20*1024*1024;
    
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
		     FileOutputStream outputStream = null;
		     String fileDir="";
			 insPricingVO.setSourceAttchments1(formFile1);	
			 insPricingVO.setSourceAttchments2(formFile2);
			 insPricingVO.setSourceAttchments3(formFile3);
			 insPricingVO.setSourceAttchments4(formFile4);
			 insPricingVO.setSourceAttchments5(formFile5);
    //bk file-end
    
	ArrayList al= insuranceObject.swSavePricingList(insPricingVO);
	 Long lpricingSeqId=(Long)al.get(0);
	 if(lpricingSeqId > 0)successMsg="Details Added Successfully";
	 else successMsg="Details Updated Successfully";
	 
	 
	 insPricingVO= insuranceObject.swSelectPricingList(lpricingSeqId);
	  
	 pricingNumberAlert = " Please note the pricing reference number "+insPricingVO.getPricingRefno()+" for future";
	 
	 frmSwPricing= (DynaActionForm)FormUtils.setFormValues("frmSwPricing",	insPricingVO, this, mapping, request);
	 
	 //bk-file
	//file-1
	 if(!("").equals(formFile1.getFileName())){
		  
	String pricingNumberAlert1 =insPricingVO.getPricingRefno();
	
	    String attachmentname1=request.getParameter("attachmentname1");
	    if(!("").equals(attachmentname1)){
		  File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir")+"/"+pricingNumberAlert1+"/"+attachmentname1.trim());
		    file.delete();   
		 }
	 
	 	 	 
	  fileDir=TTKPropertiesReader.getPropertyValue("dubaipriceDir");
	  fileDir+=pricingNumberAlert1;		  
      File file= new File(fileDir);
      if(!file.exists())file.mkdirs();
      outputStream = new FileOutputStream(fileDir+"/"+formFile1);
      outputStream.write(formFile1.getFileData());
      if (outputStream != null)  outputStream.close();
      String fileExtn 	=  GetFileExtension(formFile1.toString());
     
	  }

	//file-2
	  if(!("").equals(formFile2.getFileName())){
		  
		String  pricingNumberAlert2 =insPricingVO.getPricingRefno();
		
		String attachmentname2=request.getParameter("attachmentname2");
	    if(!("").equals(attachmentname2)){
	    	 File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir2")+"/"+pricingNumberAlert2+"/"+attachmentname2.trim());
		    file.delete();   
		 }
		 	 
		  fileDir=TTKPropertiesReader.getPropertyValue("dubaipriceDir2");
		  fileDir+=pricingNumberAlert2;
          File file= new File(fileDir);
          if(!file.exists())file.mkdirs();
          outputStream = new FileOutputStream(fileDir+"/"+formFile2);
          outputStream.write(formFile2.getFileData());
          if (outputStream != null)  outputStream.close();
          String fileExtn 	=  GetFileExtension(formFile2.toString());
		  }
   //file-3
        if(!("").equals(formFile3.getFileName())){
		  
		 String pricingNumberAlert3 =insPricingVO.getPricingRefno();
		 String attachmentname3=request.getParameter("attachmentname3");
		    if(!("").equals(attachmentname3)){
			  File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir3")+"/"+pricingNumberAlert3+"/"+attachmentname3.trim());
			    file.delete();   
			 }
		 	 
		  fileDir=TTKPropertiesReader.getPropertyValue("dubaipriceDir3");
		  fileDir+=pricingNumberAlert3;
          File file= new File(fileDir);
          if(!file.exists())file.mkdirs();
          outputStream = new FileOutputStream(fileDir+"/"+formFile3);
          outputStream.write(formFile3.getFileData());
          if (outputStream != null)  outputStream.close();
          String fileExtn 	=  GetFileExtension(formFile3.toString());
		  }
     //file-4
        if(!("").equals(formFile4.getFileName())){
			  
			String pricingNumberAlert4 =insPricingVO.getPricingRefno();
			
			String attachmentname4=request.getParameter("attachmentname4");
	    if(!("").equals(attachmentname4)){
		  File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir4")+"/"+pricingNumberAlert4+"/"+attachmentname4.trim());
		    file.delete();   
		 }
	 	 
		  fileDir=TTKPropertiesReader.getPropertyValue("dubaipriceDir4");
		  fileDir+=pricingNumberAlert4;
	          File file= new File(fileDir);
	          if(!file.exists())file.mkdirs();
	          outputStream = new FileOutputStream(fileDir+"/"+formFile4);
	          outputStream.write(formFile4.getFileData());
	          if (outputStream != null)  outputStream.close();
	          String fileExtn 	=  GetFileExtension(formFile4.toString());
			  }
     //file-5
        if(!("").equals(formFile5.getFileName())){
			  
			String  pricingNumberAlert5 =insPricingVO.getPricingRefno();
			 	 
			String attachmentname5=request.getParameter("attachmentname5");
		    if(!("").equals(attachmentname5)){
			  File file=new File(TTKPropertiesReader.getPropertyValue("dubaipriceDir5")+"/"+pricingNumberAlert5+"/"+attachmentname5.trim());
			    file.delete();   
			 }
			  fileDir=TTKPropertiesReader.getPropertyValue("dubaipriceDir5");
			  fileDir+=pricingNumberAlert5;
	          File file= new File(fileDir);
	          if(!file.exists())file.mkdirs();
	          outputStream = new FileOutputStream(fileDir+"/"+formFile5);
	          outputStream.write(formFile5.getFileData());
	          if (outputStream != null)  outputStream.close();
	          String fileExtn 	=  GetFileExtension(formFile5.toString());
			  }
        //end file-5
	 //end bk-file-end

	 frmSwPricing.set("previousProductName", insPricingVO.getPreviousProductName());
	 frmSwPricing.set("pricingNumberAlert", pricingNumberAlert);	
	 frmSwPricing.set("authorityType", insPricingVO.getAuthorityType());
	 
	 request.getSession().setAttribute("insCmpNm",insPricingVO.getInsuranceCompanyName());
	 request.getSession().setAttribute("frmSwPricing",frmSwPricing);
	 request.getSession().setAttribute("ClientCode", insPricingVO.getClientCode());	
	 request.getSession().setAttribute("pricingRefNo", insPricingVO.getPricingRefno());	
	 request.setAttribute("successMsg", successMsg);
	 
	 this.addToWebBoard(insPricingVO,request);//bk
	 
	return this.getForward(strGroupprofile, mapping, request);
    }
    
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
			
/*			if(!(TTKCommon.checkNull((String)frmSwPricing.get("rownum")).equals("")))
			{
				strAddCaption.append("Policy Details - ").append("Edit").append(" [ ").append(frmBatch.get("batchNbr")).append(" ]");
			}//end of if(!(TTKCommon.checkNull((String)frmPolicyDetail.get("rownum")).equals("")))
			else
			{
				strAddCaption.append("Policy Details - ").append("Add").append(" [ ").append(frmBatch.get("batchNbr")).append(" ]");
			}//end of else
*/			
			
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
	
	
	//save screen-2
	public ActionForward doSaveIncome(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
			log.debug("Inside CriticalICDListAction SW doSave");
			try
			{
			setLinks(request);
			DynaActionForm frmSwIncomeProfile=(DynaActionForm)form;
			InsPricingVO insPricingVO=null;
			InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject(); 
			
			DynaActionForm frmSwPricing=(DynaActionForm)request.getSession().getAttribute("frmSwPricing");
			
			HashMap<String, Object> screen2Data=new HashMap<>();
			screen2Data.put("groupProfileSeqID", frmSwPricing.get("groupProfileSeqID"));
		
			String groupProfileSeqID1 = (String)frmSwPricing.get("groupProfileSeqID");
			
				long groupProfileSeqID = Long.parseLong(groupProfileSeqID1);
				request.getSession().setAttribute("GroupProfileSeqID",groupProfileSeqID );
			
			Long userSeqId = TTKCommon.getUserSeqId(request);
			screen2Data.put("userSeqId",userSeqId);
			
			ArrayList<AgeMasterVO>	table1Data=(ArrayList<AgeMasterVO>)frmSwIncomeProfile.get("table1Data");
			if(table1Data!=null){
				ArrayList<AgeMasterVO>	alTable1Data=getTablesData(table1Data,request,"colTotCovrLives");
				screen2Data.put("table1Data", alTable1Data);
			}
			
			ArrayList<AgeMasterVO>	table2Data=(ArrayList<AgeMasterVO>)frmSwIncomeProfile.get("table2Data");
			
			if(table2Data!=null){
				ArrayList<AgeMasterVO>	alTable2Data=getTablesData(table2Data,request,"colTotCovr2Lives");
				screen2Data.put("table2Data", alTable2Data);
			}
			ArrayList<AgeMasterVO>	table3Data=(ArrayList<AgeMasterVO>)frmSwIncomeProfile.get("table3Data");
			if(table3Data!=null){
				ArrayList<AgeMasterVO>	alTable3Data=getTablesData(table3Data,request,"colTotCovr3Lives");
				screen2Data.put("table3Data", alTable3Data);
			}
			ArrayList<AgeMasterVO>	table4Data=(ArrayList<AgeMasterVO>)frmSwIncomeProfile.get("table4Data");
			if(table4Data!=null){
				ArrayList<AgeMasterVO>	alTable4Data=getTablesData(table4Data,request,"colTotCovr4Lives");
				screen2Data.put("table4Data", alTable4Data);
			}
		
			
			Long strTransactionseqId= insuranceObject.swSaveIncomeProfile(screen2Data);
		
			
					request.setAttribute("updated","message.savedSuccessfully");

			Object[] tableObjects= insuranceObject.getBenefitvalueAfter(new Long(frmSwPricing.get("groupProfileSeqID").toString()));
			
			HashMap<String, String> tablesNamesHM=(HashMap<String, String>)tableObjects[0];
			HashMap<String, ArrayList<AgeMasterVO>> tablesDataHM=(HashMap<String, ArrayList<AgeMasterVO>>)tableObjects[1];
			//table-1
			frmSwIncomeProfile.set("table1Data",tablesDataHM.get("table1Data") );
			frmSwIncomeProfile.set("table1HeaderName",tablesNamesHM.get("table1HeaderName") );
			frmSwIncomeProfile.set("table1totalLives",tablesNamesHM.get("table1totalLives") );
			//table-2
			frmSwIncomeProfile.set("table2Data",tablesDataHM.get("table2Data") );
			frmSwIncomeProfile.set("table2HeaderName",tablesNamesHM.get("table2HeaderName") );
			frmSwIncomeProfile.set("table2totalLives",tablesNamesHM.get("table2totalLives") );
			//table-3
			frmSwIncomeProfile.set("table3Data",tablesDataHM.get("table3Data") );
			frmSwIncomeProfile.set("table3HeaderName",tablesNamesHM.get("table3HeaderName") );
			frmSwIncomeProfile.set("table3totalLives",tablesNamesHM.get("table3totalLives") );
			
			//table-4
			frmSwIncomeProfile.set("table4Data",tablesDataHM.get("table4Data") );
			frmSwIncomeProfile.set("table4HeaderName",tablesNamesHM.get("table4HeaderName") );
			frmSwIncomeProfile.set("table4totalLives",tablesNamesHM.get("table4totalLives") );
			
			//request.getSession().setAttribute("GroupProfileSeqID",insPricingVO.getGroupProfileSeqID());//bk
			//System.out.println("GroupProfileSeqID--save--session===>"+insPricingVO.getGroupProfileSeqID());
			
			request.getSession().setAttribute("frmSwIncomeProfile", frmSwIncomeProfile);
			
			if(("save".equalsIgnoreCase(request.getParameter("singlebutton"))) || ("partialsave".equalsIgnoreCase(request.getParameter("singlebutton")))){
				 return this.getForward(strIncomeprofile, mapping, request);
			}
			else{
				
				 return mapping.findForward("incomeprofileProceed");
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
	    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
    public ActionForward doCloseIncome(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    		HttpServletResponse response) throws Exception{
    try{
    setLinks(request);
    DynaActionForm frmSwPricing= (DynaActionForm)form;
    InsPricingVO insPricingVO=null;
    String successMsg;

    InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject();
    insPricingVO = (InsPricingVO)FormUtils.getFormValues(frmSwPricing, this, mapping, request);
    insPricingVO.setAddedBy((TTKCommon.getUserSeqId(request)));
   long lpricingSeqId=  (long)request.getSession().getAttribute("GroupProfileSeqID");    

    		
    		 insPricingVO= insuranceObject.swSelectPricingList(lpricingSeqId);
    		 
    		 frmSwPricing= (DynaActionForm)FormUtils.setFormValues("frmSwPricing",	insPricingVO, this, mapping, request);
    		 request.getSession().setAttribute("frmSwPricing",frmSwPricing);
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
    
  
    
	//end save screen-2
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
	private ArrayList<AgeMasterVO> getTablesData(ArrayList<AgeMasterVO> tableData,HttpServletRequest request,String strParam){
		
		ArrayList<AgeMasterVO> ageMasterVOs=new ArrayList<>();
		
		for(int i=0;i<tableData.size();i++){
			AgeMasterVO ageMasterVO=tableData.get(i);
			ageMasterVO.setColumn4(request.getParameter(strParam+i));
			ageMasterVOs.add(ageMasterVO);
		}
		
		return ageMasterVOs;
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
    
	////////////////upload////////////////////////
	 private static String GetFileExtension(String fname2) {
	        String fileName = fname2;
			String fname = "";
			String ext = "";
			int mid = fileName.lastIndexOf(".");
			fname = fileName.substring(0, mid);
			ext = fileName.substring(mid + 1, fileName.length());
	        return ext;
	    }

		private static void log(String message) {
	            System.out.println(message);
	    }
	 
		public ActionForward doUploadMemDetails (ActionMapping mapping,ActionForm form,HttpServletRequest request,
				 HttpServletResponse response) throws Exception{
			 try{
				 log.debug("Inside ChequeSearchAction class doUploadCheque method");
				 setLinks(request);
				 String strNotify = null;
					DynaActionForm frmSwPricing=(DynaActionForm)request.getSession().getAttribute("frmSwPricing");

					InsuranceManager insuranceObject=(InsuranceManager)this.getInsuranceManagerObject(); 

				 Object[] excelData=null;
				 DynaActionForm frmSwIncomeProfile=(DynaActionForm)form;
				 FormFile formFile = null;
				
				 formFile = (FormFile)frmSwIncomeProfile.get("stmFile");
				
				 if(formFile==null||formFile.getFileSize()==0)
				 {
					// strNotify="Please select the xls or xlsx file";
					 strNotify="Please select the .XL or .XLS file.";
					 request.getSession().setAttribute("notifyerror", strNotify);
				 }
				 else
				 {
					 String[] arr=formFile.getFileName().split("[.]");
					 String fileType=arr[arr.length-1];
					 //if(!("xls".equalsIgnoreCase(fileType)||"xlsx".equalsIgnoreCase(fileType)))
					 if(!("xl".equalsIgnoreCase(fileType)||"xls".equalsIgnoreCase(fileType)))
						 
					 {
						// strNotify="File Type should be xls or xlsx";
						 strNotify="FILE TYPE SHOULD BE .XL OR .XLS .";
						 request.getSession().setAttribute("notifyerror", strNotify);           
					 }
				 }
				 if(formFile.getFileSize()>(1024*1024*1024)) 
				 {
					 strNotify="File Length Lessthan 3GB";	        	  
					 request.getSession().setAttribute("notifyerror", strNotify);
				 }

				 if(strNotify!=null && strNotify.length()!=0)
				 {
					 return (mapping.findForward(strIncomeprofile));
				 }
				 else
					{
						String[] arr=formFile.getFileName().split("[.]");
						String fileType=arr[arr.length-1];
						
						excelData=this.getExcelData(request,formFile,fileType,8);
						  String batchNo=convertExcelDataIntoXml(request,excelData,formFile,fileType,frmSwPricing.get("groupProfileSeqID").toString());
						  String xmlFilePath=TTKPropertiesReader.getPropertyValue("dubaiXmlPricingDir")+batchNo+".xml";
	          	    	  File file2=new File(xmlFilePath);
	          	    	  FileReader fileReader=new FileReader(file2);
	          	    	
				         
	          	    	String pricingmemUpload= insuranceObject.PricingUploadExcel(batchNo,fileReader,(int)file2.length(),new Long(frmSwPricing.get("groupProfileSeqID").toString()));
	          	    	 
	          	   if(pricingmemUpload == null){
	          	    request.setAttribute("successMsg","File uploaded successfully");
	          	    }else{
	          	    	 request.setAttribute("errorMsg",pricingmemUpload);
	          	    }
				  
	          	 Object[] tableObjects= insuranceObject.getBenefitvalueAfter(new Long(frmSwPricing.get("groupProfileSeqID").toString()));
	 			
	 			HashMap<String, String> tablesNamesHM=(HashMap<String, String>)tableObjects[0];
	 			HashMap<String, ArrayList<AgeMasterVO>> tablesDataHM=(HashMap<String, ArrayList<AgeMasterVO>>)tableObjects[1];
	 			//table-1
	 			frmSwIncomeProfile.set("table1Data",tablesDataHM.get("table1Data") );
	 			frmSwIncomeProfile.set("table1HeaderName",tablesNamesHM.get("table1HeaderName") );
	 			frmSwIncomeProfile.set("table1totalLives",tablesNamesHM.get("table1totalLives") );
	 			//table-2
	 			frmSwIncomeProfile.set("table2Data",tablesDataHM.get("table2Data") );
	 			frmSwIncomeProfile.set("table2HeaderName",tablesNamesHM.get("table2HeaderName") );
	 			frmSwIncomeProfile.set("table2totalLives",tablesNamesHM.get("table2totalLives") );
	 			//table-3
	 			frmSwIncomeProfile.set("table3Data",tablesDataHM.get("table3Data") );
	 			frmSwIncomeProfile.set("table3HeaderName",tablesNamesHM.get("table3HeaderName") );
	 			frmSwIncomeProfile.set("table3totalLives",tablesNamesHM.get("table3totalLives") );
	 			
	 			//table-4
	 			frmSwIncomeProfile.set("table4Data",tablesDataHM.get("table4Data") );
	 			frmSwIncomeProfile.set("table4HeaderName",tablesNamesHM.get("table4HeaderName") );
	 			frmSwIncomeProfile.set("table4totalLives",tablesNamesHM.get("table4totalLives") );
	 			
	 			request.getSession().setAttribute("frmSwIncomeProfile", frmSwIncomeProfile);
	          	   
	          	   
				     request.getSession().setAttribute("frmSwPricing",frmSwPricing); 
				     file2.delete();
				     return (mapping.findForward(strIncomeprofile));	 
					}//else loop end

			 }//end of try
			 catch(TTKException expTTK)
			 {
				 return this.processExceptions(request, mapping, expTTK);
			 }//end of catch(TTKException expTTK)
			 catch(Exception exp)
			 {
				 return this.processExceptions(request, mapping, new TTKException(exp,strInsError));
			 }//end of catch(Exception exp)
		 }//
		
		private Object[] getExcelData(HttpServletRequest request,FormFile formFile, String fileType, int column) throws FileNotFoundException, IOException {
			// TODO Auto-generated method stub
			InputStream fis=null;
	    	HSSFSheet sheet = null;// i; // sheet can be used as common for XSSF and HSSF WorkBook
	    	Reader reader		=	null;
	    	Object object[]=new Object[2];
	    	int numclaimsettlementnumber=0;
	 	     ArrayList<ArrayList<String>> excelDatar=new ArrayList<>();
			FileWriter fileWriter=	null;
			HSSFWorkbook workbook = null;
			
			fis = formFile.getInputStream(); 
			workbook =  (HSSFWorkbook) new HSSFWorkbook(fis);
	    	  //log("xls="+wb_hssf.getSheetName(0));
	    	 sheet = workbook.getSheetAt(0);
	    	
	 	        //Initializing the XML document
	 	    	final Pattern REGEX_PATTERN = Pattern.compile("[^\\p{ASCII}]+");
	 	        if(sheet==null)
	 	        	request.getSession().setAttribute("notify", "Please upload proper File");
	 	        else
	 	        {
	 	        	  Iterator<?> rows     = sheet.rowIterator ();
	 	               ArrayList<String> excelDatac;
	 	               
	      	        while (rows.hasNext ()) 
	      	        {
	      	        HSSFRow row =  (HSSFRow) rows.next(); 

	      	            if(row.getRowNum()==0)
	      	            	continue;
	      	          
	      	            Iterator<?> cells = row.cellIterator (); 
	      	            ArrayList<String> rowData = new ArrayList<String>();
	      	            for(short i=0;i<10;i++)
	      	            {
	      	            	HSSFCell cell	=	row.getCell(i);
	      	           
	      	            	
	      	            	if(cell==null)
	      	            		rowData.add("");
	      	            	else
	      	            		{ 
	      	            		switch (cell.getCellType ())
	  	     	                {
	  		     	                case HSSFCell.CELL_TYPE_NUMERIC :
	  		     	               
	  		     	                
	  		     	                {
	  		     	                    // Date CELL TYPE
	  		     	                    if(HSSFDateUtil.isCellDateFormatted((HSSFCell) cell))
	  		     	                     {
	  		     	               
	  		     	                    	rowData.add(new SimpleDateFormat("dd/MM/YYYY").format(cell.getDateCellValue()));
	  		     	           
	  		     	                    }
	  		     	                    else // NUMERIC CELL TYPE
	  		     	                    	rowData.add(cell.getNumericCellValue () + "");
	  	
	  		     	                    break;
	  		     	                }
	  		     	                case HSSFCell.CELL_TYPE_STRING :
	  		     	                {
	  		     	                    // STRING CELL TYPE
	  		     	                    //String richTextString = cell.getStringCellValue().trim().replaceAll("[^\\x00-\\x7F]", "");
	  		     	                	String richTextString = cell.getStringCellValue().trim();
	  		     	                	richTextString	=	REGEX_PATTERN.matcher(richTextString).replaceAll("").trim();
	  		     	                    rowData.add(richTextString);
	  		     	                    break;
	  		     	                }
	  		     	                case HSSFCell.CELL_TYPE_BLANK :
	  		     	                {	//HSSFRichTextString blankCell	=	cell.get.getRichStringCellValue();
	  		     	                	
	  		     	              
	  		     	                    
	  		     	                	String blankCell	=	cell.getStringCellValue().trim().replaceAll("[^\\x00-\\x7F]", "");
	  		     	                	rowData.add(blankCell);
	  		     	                	break;
	  		     	                }
	  		     	                default:
	  		     	                {
	  		     	                    // types other than String and Numeric.
	  		     	                    System.out.println ("Type not supported.");
	  		     	                    break;
	  		     	                }
	  	     	                } // end switch
	  	            		}//else
	      	            	
	      	            }//for
	      	          
	      	          excelDatar.add(rowData);//adding Excel data to ArrayList
	      	        numclaimsettlementnumber++; 
	      	        } //end while
	      	     
	 	        }
	 	        object[0]=numclaimsettlementnumber+"";//adding no. of policies
				object[1]=excelDatar;//adding all rows data
	 		     return object;
		}
		
		
		
		private String convertExcelDataIntoXml(HttpServletRequest request,Object[] objects,FormFile formFile,String fileType,String pricingSeqid) throws JAXBException {
			
				 
			    	String noOfPolicies=(String)objects[0];
					ArrayList<ArrayList<String>> excelDataRows=(ArrayList<ArrayList<String>>)objects[1];
			    	String uploadType="PRI";
                    Long uploadedBy1=(Long) TTKCommon.getUserSeqId(request);
                    String uploadedBy = uploadedBy1.toString();
			    	
			        String batchNo=uploadType+"-"+pricingSeqid+"-"+ new SimpleDateFormat("yyyyMMddHHmmSSS").format(new Date());//policyType+"-"+ new SimpleDateFormat("dd-MM-yyyy-hh-mm-SSS").format(new Date())+"-"+vidalOfficeName+"-"+abrCode+"-"+uploadType+"-"+groupId;
			       
			       String policyFileName =batchNo+"."+fileType;  
			        
			       
			        //prepare the marshaling
			    
			        Batch batch=new Batch();
			      JAXBContext contextObj = JAXBContext.newInstance(Batch.class); 
					Marshaller marshallerObj = contextObj.createMarshaller(); 
					marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
				  //  FileDetail fileDetail=new FileDetail(member_Name,gender,dob,age,matCovered,salary,country_code,policy_number,member_id,relationship) 
				 //  batch.setFileDetail(fileDetail);
			       
				 //write the data into xml file				    
		    		Iterator<ArrayList<String>>rit=excelDataRows.iterator();
		    	
		    		
		    		ArrayList<MemberDetails> memberAl=new ArrayList<>();
		    		
			    		while(rit.hasNext()){
			    			 ArrayList<String> rlist=rit.next();
			    			 MemberDetails memberDetails=new MemberDetails();
			    			 memberDetails.setUploadstatus("Y");
			    			 MemberData memberData=new MemberData();		    			 
			    			 
			    			 memberData.setMember_Name(rlist.get(0)); 
			    			 memberData.setGender(rlist.get(1)); 
			    			 memberData.setDob(rlist.get(2)); 
			    			 memberData.setAge(rlist.get(3)); 
			    			 memberData.setMatCovered(rlist.get(4)); 
			    			 memberData.setRelationship(rlist.get(5));
			    			 memberData.setSalary(rlist.get(6));  
			    			 memberData.setCountry_code(rlist.get(7));  
			    			// memberData.setPolicy_number(rlist.get(7));  
			    			// memberData.setMember_id(rlist.get(8));
			    			
			    			 
			    			 memberDetails.setMemberData(memberData);
			    			 
			    			 memberAl.add(memberDetails);		    		   		   
			    			}//while		    		
			    	
			    		 batch.setMemberDetails(memberAl); 		    			  
		    			  
		    			  File xmlPath=new File(TTKPropertiesReader.getPropertyValue("dubaiXmlPricingDir"));
		    			  if(!xmlPath.exists())xmlPath.mkdirs();
		    			  String xmlFilePath=TTKPropertiesReader.getPropertyValue("dubaiXmlPricingDir")+batchNo+".xml";  
		    			  marshallerObj.marshal(batch,new File(xmlFilePath));		    			  
		    		
                                return batchNo; 
                                
		}            
		
		
		
		
		 public ActionForward doshowTemplate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
					HttpServletResponse response) throws TTKException {
			 	log.debug("Inside ChequeSearchAction class  doshowChequeTemplate");
				ByteArrayOutputStream baos=null;
				OutputStream sos = null;
				FileInputStream fis= null; 
				File file = null;
				BufferedInputStream bis =null;
				try
				{
					setLinks(request);
						response.setContentType("application/txt");
						response.setHeader("Content-Disposition", "attachment;filename=PricingMemTemplate.xls");
		               
					String fileName =	TTKPropertiesReader.getPropertyValue("dubaiXmlPricingDir")+"PricingMemTemplate.xls";
					file = new File(fileName);
					
					fis = new FileInputStream(file);
					bis = new BufferedInputStream(fis);
					baos=new ByteArrayOutputStream();
					int ch;
					while ((ch = bis.read()) != -1) baos.write(ch);
					sos = response.getOutputStream();
					baos.writeTo(sos);  
					baos.flush();      
					sos.flush(); 
		            return (mapping.findForward(strIncomeprofile));
				}//end of try
				catch(TTKException expTTK)
				{
					return this.processExceptions(request, mapping, expTTK);
				}//end of catch(TTKException expTTK)
				catch(Exception exp)
				{
					return this.processExceptions(request,mapping,new TTKException(exp,strInsError));
				}//end of catch(Exception exp)
				finally{
					try{
						if(baos!=null)baos.close();                                           
						if(sos!=null)sos.close();
						if(bis!=null)bis.close();
						if(fis!=null)fis.close();

					}
					catch(Exception exp)
					{
						return this.processExceptions(request,mapping,new TTKException(exp,strInsError));
					}//                 
				}
			}
		
		
	
	
   
} //end of InsPricingAction


