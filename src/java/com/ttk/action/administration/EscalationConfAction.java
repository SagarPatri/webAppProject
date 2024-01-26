package com.ttk.action.administration;

import java.util.ArrayList;
import java.util.Arrays;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.EscalationLimitVO;
import com.ttk.dto.administration.ProdPolicyLimitVO;

public class EscalationConfAction extends TTKAction{
	
	private static Logger log = Logger.getLogger( EscalationConfAction.class );
	
	//Action mapping forwards.
	private static final String strEscalateConfig="escalateprodconf";
	private static final String strEscalatePolicyConfig="escalatepolicyconf";
	private static final String strEscalateProdClose="escalateprodclose";
	private static final String strEscalatePolicyClose="escalatepolicyclose";
	//Exception Message Identifier
	private static final String strProduct="product";

	
	/**
	 * This method is used to delete the selected records from the list.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doConfigureEscalateLimits(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doConfigureRenewalLimits method of EscalationConfAction");
			setLinks(request);
			DynaActionForm frmEsacalationConf =(DynaActionForm)form;
			ArrayList<Object> alProdPolicyEscalateLimit = new ArrayList<Object>();
			ArrayList<Object> alProdPolicyEscalateLimitclm = new ArrayList<Object>();
			StringBuffer sbfCaption=new StringBuffer();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			
			String strForwardPath=null;
			if("Products".equals(strActiveSubLink)){
				strForwardPath=strEscalateConfig;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strEscalatePolicyConfig;
			}//end of else
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			Long lngProdPolicySeqId=TTKCommon.getWebBoardId(request);
			log.info("Product Policy SeqId : "+lngProdPolicySeqId);
		
			alProdPolicyEscalateLimit = productPolicyObject.getProdPolicyEscalateLimit(lngProdPolicySeqId);
			
			alProdPolicyEscalateLimitclm = productPolicyObject.getProdPolicyEscalateLimitclm(lngProdPolicySeqId);
			log.info("Product Policy size : "+alProdPolicyEscalateLimit.size());
			
			
			
			if(alProdPolicyEscalateLimit!=null && alProdPolicyEscalateLimit.size()>0)
			
			{ frmEsacalationConf.set("prodPolicyEscalateLimit",(EscalationLimitVO[])alProdPolicyEscalateLimit.toArray(new EscalationLimitVO[0]));
			   
			}

			if(alProdPolicyEscalateLimitclm!=null && alProdPolicyEscalateLimitclm.size()>0)
			
			{ frmEsacalationConf.set("prodPolicyEscalateLimitclm",(EscalationLimitVO[])alProdPolicyEscalateLimitclm.toArray(new EscalationLimitVO[0]));
			   
			}
			//Comments the Database hit for Title Display.
			/*ProductVO productVO=productPolicyObject.getProductDetails(TTKCommon.getWebBoardId(request));
            sbfCaption.append("[").append(productVO.getCompanyName()).append("]");            
            sbfCaption.append("[").append(productVO.getProductName()).append("]");
			
			DynaActionForm frmProductList = (DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			sbfCaption.append("[").append(frmProductList.get("companyName")).append("]");
			sbfCaption.append("[").append(frmProductList.get("productName")).append("]");	*/		 
            
           // frmEsacalationConf.set("caption",buildCaption.toString());
			frmEsacalationConf.set("caption",buildCaption(request));       
			request.setAttribute("frmEsacalationConf",frmEsacalationConf);
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
	
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
    		log.debug("Inside RenewalDaysConfAction doSave");
    		ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
    		DynaActionForm frmEsacalationConf =(DynaActionForm)form;
    		StringBuffer sbfCaption= new StringBuffer();
    		ArrayList<Object> alProdPolicyEscalateLimit = new ArrayList<Object>();
    		ArrayList<Object> alProdPolicyEscalateLimitclm = new ArrayList<Object>();	
    		frmEsacalationConf.set("caption",buildCaption(request));
    		String[] strLimitSeqID=request.getParameterValues("limitSeqID");
    		String[] strPatclmtype=request.getParameterValues("patclmtype");
    		String[] strFlag=request.getParameterValues("flag");
    	    String[] strRemType=request.getParameterValues("remType");
            String[] strEscalateDaysIntDays=request.getParameterValues("days");
            
            log.info("strLimitSeqID length "+ Arrays.toString(strLimitSeqID));
            log.info("strPatclmtype length "+ Arrays.toString(strPatclmtype));
            log.info("strFlag length "+ Arrays.toString(strFlag));
            log.info("strRemType length "+ Arrays.toString(strRemType));
            log.info("strEscalateDaysIntDays "+ Arrays.toString(strEscalateDaysIntDays));
           
            log.info("LimitSeqID"+TTKCommon.getWebBoardId(request));;
            if(strLimitSeqID!=null && strLimitSeqID.length>0)
            {
            	//loop througth the no of members and prepare the ArrayList of premiuminfoVO's
                
                	alProdPolicyEscalateLimit.add(TTKCommon.getWebBoardId(request));
                	StringBuffer sbfLimit=new StringBuffer();
                	for(int iCount=0;iCount<strLimitSeqID.length;iCount++)
                    {
                		sbfLimit.append("|"+TTKCommon.checkNull(strLimitSeqID[iCount])+"|"+TTKCommon.checkNull(strPatclmtype[iCount])+"|"+TTKCommon.checkNull(strFlag[iCount])+"|"
                    			+TTKCommon.checkNull("REM"+strRemType[iCount])+"|"+TTKCommon.checkNull(strEscalateDaysIntDays[iCount]));
                		
                	}//end of for loop
                	sbfLimit.append("|");
                	log.info("Submit String :"+sbfLimit.toString());
                	alProdPolicyEscalateLimit.add(sbfLimit.toString());
                	alProdPolicyEscalateLimit.add(TTKCommon.getUserSeqId(request));
               
               
            }//end of if
    //log.info("alProdPolicyEscalateLimit.size()"+alProdPolicyEscalateLimit.size());
            if(alProdPolicyEscalateLimit!=null && alProdPolicyEscalateLimit.size()>0)
            {
            	//frmDebitNote.set("debitnote",(DebitNoteDepositVO[])alDebitDeposit.toArray(new DebitNoteDepositVO[0]));
            	//call the Dao to update the premium Info
                int iCount=productPolicyObject.saveProdPolicyEscalateLimit(alProdPolicyEscalateLimit);
                log.info("iCount::"+iCount);;
                if(iCount>0)    //if record is updated then requery
                {
                	frmEsacalationConf.initialize(mapping);
                	alProdPolicyEscalateLimit = productPolicyObject.getProdPolicyEscalateLimit(TTKCommon.getWebBoardId(request));
        			
        			alProdPolicyEscalateLimitclm = productPolicyObject.getProdPolicyEscalateLimitclm(TTKCommon.getWebBoardId(request));
        			log.info("Product Policy size : "+alProdPolicyEscalateLimit.size());
        			
        			
        			
        			if(alProdPolicyEscalateLimit!=null && alProdPolicyEscalateLimit.size()>0)
        			
        			{ frmEsacalationConf.set("prodPolicyEscalateLimit",(EscalationLimitVO[])alProdPolicyEscalateLimit.toArray(new EscalationLimitVO[0]));
        			   
        			}

        			if(alProdPolicyEscalateLimitclm!=null && alProdPolicyEscalateLimitclm.size()>0)
        			
        			{ frmEsacalationConf.set("prodPolicyEscalateLimitclm",(EscalationLimitVO[])alProdPolicyEscalateLimitclm.toArray(new EscalationLimitVO[0]));
        			   
        			}
        			request.setAttribute("updated","message.savedSuccessfully");
                }//end of if(iCount>0)
            }//end of if(alDebitDeposit!=null && alDebitDeposit.size()>0)
            //Comments the Database hit for Title Display.
            /*ProductVO productVO=productPolicyObject.getProductDetails(TTKCommon.getWebBoardId(request));
            sbfCaption.append("[").append(productVO.getCompanyName()).append("]");
            sbfCaption.append("[").append(productVO.getProductName()).append("]");
            
            DynaActionForm frmProductList = (DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			sbfCaption.append("[").append(frmProductList.get("companyName")).append("]");
			sbfCaption.append("[").append(frmProductList.get("productName")).append("]");*/	
			
            frmEsacalationConf.set("caption",buildCaption(request));             
           // frmEsacalationConf.set("caption",sbfCaption.toString());
            request.setAttribute("frmEsacalationConf",frmEsacalationConf);
             String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			  String strForwardPath=null;
            if("Products".equals(strActiveSubLink)){
				strForwardPath=strEscalateConfig;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strEscalatePolicyConfig;
			}//end of else
			return this.getForward(strForwardPath, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strProduct));
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
    		log.debug("Inside RenewalDaysConfAction doReset");
    		DynaActionForm frmEsacalationConf =(DynaActionForm)form;
			ArrayList<Object> alProdPolicyEscalateLimit = new ArrayList<Object>();
			StringBuffer sbfCaption= new StringBuffer();
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			//Long lngProdPolicySeqId=;
			alProdPolicyEscalateLimit = productPolicyObject.getProdPolicyLimit(TTKCommon.getWebBoardId(request));
			if(alProdPolicyEscalateLimit!=null && alProdPolicyEscalateLimit.size()>0)
			{
				frmEsacalationConf.set("prodPolicyLimit",(ProdPolicyLimitVO[])alProdPolicyEscalateLimit.toArray(new ProdPolicyLimitVO[0]));
			}//end of if
			//Comments the Database hit for Title Display.
		
			frmEsacalationConf.set("caption",buildCaption(request));       
			request.setAttribute("frmEsacalationConf",frmEsacalationConf);
			
			
		    String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			  String strForwardPath=null;
             if("Products".equals(strActiveSubLink)){
				strForwardPath=strEscalateProdClose;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strEscalatePolicyClose;
			}//end of else
			return this.getForward(strForwardPath, mapping, request);
    		//return this.getForward(strRenewalConfig, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strProduct));
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
			log.debug("Inside the doClose method of RenewalDaysConfAction");
			setLinks(request);
			 String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			  String strForwardPath=null;
            if("Products".equals(strActiveSubLink)){
				strForwardPath=strEscalateProdClose;
			}//end of if("Products".equals(strActiveSubLink))
			else {
				strForwardPath=strEscalatePolicyClose;
			}//end of else
			return this.getForward(strForwardPath, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	
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
	/*public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
		try{
			log.info("Inside the doChangeWebBoard method of RenewalDaysConfAction");
			setLinks(request);
			//if web board id is found, set it as current web board id
			//TTKCommon.setWebBoardId(request);
			//get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			Long lProdPolicySeqId=TTKCommon.getWebBoardId(request);
			//create a new Product object
			ProductVO productVO = new ProductVO();
			DynaActionForm frmProductDetail = (DynaActionForm)form;
			frmProductDetail.initialize(mapping);
			//get the product details from the Dao object
			productVO=productPolicyObject.getProductDetails(lProdPolicySeqId);
			frmProductDetail = (DynaActionForm)FormUtils.setFormValues("frmProductGeneralInfo",
															productVO, this, mapping, request);
			frmProductDetail.set("caption","Edit");
			request.setAttribute("frmProductGeneralInfo",frmProductDetail);
			//add the product to session
			request.getSession().setAttribute("productVO",productVO);
			this.documentViewer(request,productVO);
			return this.getForward(strRenewalClose, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			//HttpServletResponse response)
*/	
	/**
	 * This menthod for document viewer information
	 * @param request HttpServletRequest object which contains hospital information.
	 * @param policyDetailVO PolicyDetailVO object which contains policy information.
	 * @exception throws TTKException
	 */
	/*private void documentViewer(HttpServletRequest request, ProductVO productVO) throws TTKException
	{
//		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<String> alDocviewParams = new ArrayList<String>();
		alDocviewParams.add("leftlink="+TTKCommon.getActiveLink(request));
		alDocviewParams.add("product_seq_id="+productVO.getProdSeqId());
		if(request.getSession().getAttribute("toolbar")!=null)
		{
			((Toolbar)request.getSession().getAttribute("toolbar")).setDocViewParams(alDocviewParams);
		}//end of if(request.getSession().getAttribute("toolbar")!=null)
	}//end of documentViewer(HttpServletRequest request, ProductVO productVO)
*/	
	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return ProductPolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ProductPolicyManager getProductPolicyManagerObject() throws TTKException
	{
		ProductPolicyManager productPolicyManager = null;
		try
		{
			if(productPolicyManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyManager = (ProductPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProductPolicyManagerBean!com.ttk.business.administration.ProductPolicyManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch
		return productPolicyManager;
	}//end getProductManagerObject()

	
	
	private String buildCaption(HttpServletRequest request)throws Exception
	{
		String strCaption=null;
		StringBuffer sbfCaption= new StringBuffer();
		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
		if(strActiveSubLink.equals("Policies"))
		{
			DynaActionForm frmPolicies = (DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
			sbfCaption.append("Configuration - [ ").append(frmPolicies.get("companyName")).append(" ] [ ").append(frmPolicies.get("policyNbr")).append(" ] ");
		}//end of else if(strActiveSubLink.equals("Policies"))
		if(strActiveSubLink.equals("Products"))
		{
			DynaActionForm frmProductList = (DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			sbfCaption.append("Configuration - [").append(frmProductList.get("companyName")).append("]");
			sbfCaption.append(" [").append(frmProductList.get("productName")).append("]");			
		}//end of if(strActiveSubLink.equals("Products"))
		strCaption=String.valueOf(sbfCaption);
		log.info("strCaption::::::::::::"+strCaption);
		return strCaption;
	}//end of buildCaption(HttpServletRequest request)
	
}//end of RenewalDaysConfAction
