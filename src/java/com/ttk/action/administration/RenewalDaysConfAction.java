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
import com.ttk.dto.administration.ProdPolicyLimitVO;

public class RenewalDaysConfAction extends TTKAction{
	
	 private static Logger log = Logger.getLogger( RenewalDaysConfAction.class );
	
	//Action mapping forwards.
	private static final String strRenewalConfig="renewalconf";
	private static final String strRenewalClose="renewalclose";
	
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
	public ActionForward doConfigureRenewalLimits(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doConfigureRenewalLimits method of RenewalDaysConfAction");
			setLinks(request);
			DynaActionForm frmRenewalDays =(DynaActionForm)form;
			ArrayList<Object> alProdPolicyLimit = new ArrayList<Object>();
			StringBuffer sbfCaption=new StringBuffer();
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			Long lngProdPolicySeqId=TTKCommon.getWebBoardId(request);
			//System.out.println("lngProdPolicySeqId-->"+lngProdPolicySeqId);
			alProdPolicyLimit = productPolicyObject.getProdPolicyLimit(lngProdPolicySeqId);
			if(alProdPolicyLimit!=null && alProdPolicyLimit.size()>0)
			{
				frmRenewalDays.set("prodPolicyLimit",(ProdPolicyLimitVO[])alProdPolicyLimit.toArray(new ProdPolicyLimitVO[0]));
			}//end of if
			//frmPoliciesEdit
			
			DynaActionForm frmProductList = (DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			
			if(frmProductList!=null){
			sbfCaption.append("[").append(frmProductList.get("companyName")).append("]");
			sbfCaption.append("[").append(frmProductList.get("productName")).append("]");				 
			}
            frmRenewalDays.set("caption",sbfCaption.toString());
			request.setAttribute("frmRenewalDays",frmRenewalDays);
			return this.getForward(strRenewalConfig, mapping, request);
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
    		DynaActionForm frmRenewalDays =(DynaActionForm)form;
    		StringBuffer sbfCaption= new StringBuffer();
    		ArrayList<Object> alProdPolicyLimit = new ArrayList<Object>();
    		String[] strLimitSeqID=request.getParameterValues("limitSeqID");
    		//log.info("strLimitSeqIDs length "+ strLimitSeqID.length);
    		String[] strFlag=request.getParameterValues("flag");
    		//log.info("strFlag length "+ strFlag.length);
            String[] strEnrolTypeID=request.getParameterValues("enrolTypeID");
            log.info("strEnrolTypeID length "+ strEnrolTypeID.length);
            String[] strRenewalIntDays=request.getParameterValues("renewalDays");
            //log.info("strRenewalIntDays length "+ strRenewalIntDays.length);
            
            if(strLimitSeqID!=null && strLimitSeqID.length>0)
            {
            	//loop througth the no of members and prepare the ArrayList of premiuminfoVO's
                	alProdPolicyLimit.add(TTKCommon.getWebBoardId(request));
                	StringBuffer sbfLimit=new StringBuffer();
                	for(int iCount=0;iCount<strLimitSeqID.length;iCount++)
                    {
                		sbfLimit.append("|"+TTKCommon.checkNull(strLimitSeqID[iCount])+"|"+TTKCommon.checkNull(strFlag[iCount])+"|"
                    			+TTKCommon.checkNull(strEnrolTypeID[iCount])+"|"+TTKCommon.checkNull(strRenewalIntDays[iCount]));
                		
                	}//end of for loop
                	sbfLimit.append("|");
                	//log.info("Submit String :"+sbfLimit.toString());
                	alProdPolicyLimit.add(sbfLimit.toString());
                	alProdPolicyLimit.add(TTKCommon.getUserSeqId(request));
               
               
            }//end of if
            if(alProdPolicyLimit!=null && alProdPolicyLimit.size()>0)
            {
            	//frmDebitNote.set("debitnote",(DebitNoteDepositVO[])alDebitDeposit.toArray(new DebitNoteDepositVO[0]));
            	//call the Dao to update the premium Info
            	int iCount=productPolicyObject.saveProdPolicyLimit(alProdPolicyLimit);
                
                if(iCount>0)    //if record is updated then requery
                {
                	
                	frmRenewalDays.initialize(mapping);
                	alProdPolicyLimit = productPolicyObject.getProdPolicyLimit(TTKCommon.getWebBoardId(request));
        			if(alProdPolicyLimit!=null && alProdPolicyLimit.size()>0)
        			{
        				frmRenewalDays.set("prodPolicyLimit",(ProdPolicyLimitVO[])alProdPolicyLimit.toArray(new ProdPolicyLimitVO[0]));
        			}//end of if
        			request.setAttribute("updated","message.savedSuccessfully");
                }//end of if(iCount>0)
            }//end of if(alDebitDeposit!=null && alDebitDeposit.size()>0)
            //Comments the Database hit for Title Display.
            /*ProductVO productVO=productPolicyObject.getProductDetails(TTKCommon.getWebBoardId(request));
            sbfCaption.append("[").append(productVO.getCompanyName()).append("]");
            sbfCaption.append("[").append(productVO.getProductName()).append("]");*/
            
            DynaActionForm frmProductList = (DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
            if(frmProductList!=null){
			sbfCaption.append("[").append(frmProductList.get("companyName")).append("]");
			sbfCaption.append("[").append(frmProductList.get("productName")).append("]");	
            }
                        
            frmRenewalDays.set("caption",sbfCaption.toString());
            request.setAttribute("frmRenewalDays",frmRenewalDays);
            return this.getForward(strRenewalConfig, mapping, request);
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
    		DynaActionForm frmRenewalDays =(DynaActionForm)form;
			ArrayList<Object> alProdPolicyLimit = new ArrayList<Object>();
			StringBuffer sbfCaption= new StringBuffer();
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			//Long lngProdPolicySeqId=;
			alProdPolicyLimit = productPolicyObject.getProdPolicyLimit(TTKCommon.getWebBoardId(request));
			if(alProdPolicyLimit!=null && alProdPolicyLimit.size()>0)
			{
				frmRenewalDays.set("prodPolicyLimit",(ProdPolicyLimitVO[])alProdPolicyLimit.toArray(new ProdPolicyLimitVO[0]));
			}//end of if
			//Comments the Database hit for Title Display.
			/*ProductVO productVO=productPolicyObject.getProductDetails(TTKCommon.getWebBoardId(request));
            sbfCaption.append("[").append(productVO.getCompanyName()).append("]");
            sbfCaption.append("[").append(productVO.getProductName()).append("]");*/
            
			DynaActionForm frmProductList = (DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			sbfCaption.append("[").append(frmProductList.get("companyName")).append("]");
			sbfCaption.append("[").append(frmProductList.get("productName")).append("]");
                        
            frmRenewalDays.set("caption",sbfCaption.toString());
			request.setAttribute("frmRenewalDays",frmRenewalDays);
			return this.getForward(strRenewalConfig, mapping, request);
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
			return mapping.findForward(strRenewalClose);
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

}//end of RenewalDaysConfAction
