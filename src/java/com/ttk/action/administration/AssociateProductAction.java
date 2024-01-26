/**
 * @ (#) AssociateProductAction.java October 15th 2007
 * Project       : Vidal Health TPA Services
 * File          : AssociateProductAction.java
 * Author        : Krupa J
 * Company       : Span Systems Corporation
 * Date Created  : October 15th 2007
 *
 * @author       :
 * Modified by   : 
 * Modified date :
 * Reason        :
 */

package com.ttk.action.administration;

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
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ProductVO;
import com.ttk.dto.common.Toolbar;

/**
 * This class is used for associated products to insurance companies
 */
public class AssociateProductAction extends TTKAction{
	private static Logger log = Logger.getLogger( AssociateProductAction.class );
	
	//Action mapping forwards.
	private static final String strAssociateProduct="associateproduct";
	private static final String strAssociateOffList="associateofficelist";
	
	//Exception Message Identifier
	private static final String strAssociateProdExp="Associate Product";
	
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
			log.info("Inside doDefault mode of AssociateProductAction");
			DynaActionForm frmAssociateProduct= (DynaActionForm)form;
			frmAssociateProduct.initialize(mapping);
			if(TTKCommon.getWebBoardId(request) == null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.product.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request) == null)
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			ProductVO productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
			//Building the caption
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption.append("Associate Product to Healthcare - [").append(productVO.getCompanyName()).append("]");
			frmAssociateProduct.set("caption",String.valueOf(sbfCaption));
			this.documentViewer(request,productVO);
			return this.getForward(strAssociateProduct, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAssociateProdExp));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to associate product to the insurance company.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		log.debug("Inside AssociateProductAction doChangeWebBoard");
		return doDefault(mapping,form,request,response);
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)
	
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
    		log.debug("Inside AssociateProductAction doReset");
    		ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			ProductVO productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
			DynaActionForm frmAssociateProduct =(DynaActionForm)form;
			frmAssociateProduct.initialize(mapping);
			//Building the caption
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption.append("Associate Product to Healthcare - [").append(productVO.getCompanyName()).append("]");
			frmAssociateProduct.set("caption",String.valueOf(sbfCaption));
    		this.documentViewer(request,productVO);
    		return this.getForward(strAssociateProduct, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strAssociateProdExp));
    	}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doAssociateOffice(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    							 HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside AssociateProductAction doReset");
    		DynaActionForm frmAssociateProduct =(DynaActionForm)form;
    		//log.info("strOfficeType : "+ request.getParameter("strOfficeType"));
    		request.getSession().setAttribute("frmAssociateProduct",frmAssociateProduct);
    		return mapping.findForward(strAssociateOffList);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strAssociateProdExp));
    	}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
	public ActionForward doAssociateExecute(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try
		{
			setLinks(request);
			log.debug("Inside AssociateProductAction doAssociateExecute");
			//get the session bean from the bean pool for each excecuting thread
			ProductPolicyManager productPolicyObject=this.getProductPolicyManagerObject();
			DynaActionForm frmAssociateProd =(DynaActionForm)form;
			ArrayList<Object> alAssociateProd = new ArrayList<Object>();
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			ProductVO productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
			int iCount=0;
			
			/*log.info("ProdPolicy Seq Id :"+TTKCommon.getWebBoardId(request));
			log.info("Head Office Flag :"+frmAssociateProd.get("headOfficeYN").toString());
			log.info("headOfficeEffDate :"+frmAssociateProd.get("headOfficeEffDate").toString());
			log.info("regionalOfficeYN :"+frmAssociateProd.get("regionalOfficeYN").toString());
			log.info("regOfficeSeqIds :"+frmAssociateProd.get("regOfficeSeqIds").toString());
			log.info("regOfficeEffDate :"+frmAssociateProd.get("regOfficeEffDate").toString());
			log.info("divisionalOfficeYN :"+frmAssociateProd.get("divisionalOfficeYN").toString());
			log.info("divOfficeSeqIds :"+frmAssociateProd.get("divOfficeSeqIds").toString());
			log.info("divOfficeEffDate :"+frmAssociateProd.get("divOfficeEffDate").toString());
			log.info("branchOfficeYN :"+frmAssociateProd.get("branchOfficeYN").toString());
			log.info("branchOfficeSeqIds :"+frmAssociateProd.get("branchOfficeSeqIds").toString());
			log.info("branchOfficeEffDate :"+frmAssociateProd.get("branchOfficeEffDate").toString());
			log.info("Enroll Types :"+populateEnrollType(frmAssociateProd,request));
			log.info(TTKCommon.getUserSeqId(request));*/
			
			if(TTKCommon.getWebBoardId(request) != null)
			{
				//if web board id is found, set it as current web board id
				alAssociateProd.add(TTKCommon.getWebBoardId(request));
				alAssociateProd.add((String)frmAssociateProd.get("headOfficeYN"));
				alAssociateProd.add((String)frmAssociateProd.get("headOfficeEffDate"));
				alAssociateProd.add((String)frmAssociateProd.get("regionalOfficeYN"));
				alAssociateProd.add((String)frmAssociateProd.get("regOfficeSeqIds"));
				alAssociateProd.add((String)frmAssociateProd.get("regOfficeEffDate"));
				alAssociateProd.add((String)frmAssociateProd.get("divisionalOfficeYN"));
				alAssociateProd.add((String)frmAssociateProd.get("divOfficeSeqIds"));
				alAssociateProd.add((String)frmAssociateProd.get("divOfficeEffDate"));
				alAssociateProd.add((String)frmAssociateProd.get("branchOfficeYN"));
				alAssociateProd.add((String)frmAssociateProd.get("branchOfficeSeqIds"));
				alAssociateProd.add((String)frmAssociateProd.get("branchOfficeEffDate"));
				alAssociateProd.add(populateEnrollType(frmAssociateProd,request));
				alAssociateProd.add(TTKCommon.getUserSeqId(request));
				iCount =productPolicyObject.getAssociateExecute(alAssociateProd);
				if(iCount>0)   //if process is executed then set message
				{
					request.setAttribute("updated","message.productAssociatedSuccessfully");
					frmAssociateProd.initialize(mapping);
				}//end of if(iCount>0) 
				else	 //if the product has already been associated to the Office 
					request.setAttribute("updated","message.productAlreadyAssociated");
				//Building the caption
				StringBuffer sbfCaption= new StringBuffer();
				sbfCaption.append("Associate Product to Healthcare - [").append(productVO.getCompanyName()).append("]");
				frmAssociateProd.set("caption",String.valueOf(sbfCaption));
				this.documentViewer(request,productVO);
			}//end of if(TTKCommon.getWebBoardId(request) != null)
			else
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.product.required");
				throw expTTK;
			}//end of else
			
			return this.getForward(strAssociateProduct, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAssociateProdExp));
		}//end of catch(Exception exp)
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
//	HttpServletResponse response)
	
	/**
	 * this method will add the policy types and their commissions
	 * @param frmAssociateProd formbean which contains the search fields
	 * @param request HttpServletRequest
	 * @return ArrayList contains search parameters
	 */
	private String populateEnrollType(DynaActionForm frmAssociateProd,HttpServletRequest request)
	{
		StringBuffer sbfEnrolType = new StringBuffer();
		sbfEnrolType.append("|");
		if(frmAssociateProd.get("individualYN").equals("Y"))
		{
			String strInd="IND";
			sbfEnrolType.append(strInd).append("|").append((String)frmAssociateProd.get("indServiceCharges"))
			.append("|");
		}
		if(frmAssociateProd.get("individualAsGroupYN").equals("Y"))
		{
			String strIng="ING";
			sbfEnrolType.append(strIng).append("|").append((String)frmAssociateProd.get("ingServiceCharges"))
			.append("|");
		}
		if(frmAssociateProd.get("corporateYN").equals("Y"))
		{
			String strCor="COR";
			sbfEnrolType.append(strCor).append("|").append((String)frmAssociateProd.get("corServiceCharges"))
			.append("|");
		}
		if(frmAssociateProd.get("nonCorporateYN").equals("Y"))
		{
			String strNcr="NCR";
			sbfEnrolType.append(strNcr).append("|").append((String)frmAssociateProd.get("ncrServiceCharges"))
			.append("|");
		}
		//log.info("Enrollment IDs and Commission value :" +sbfEnrolType);
		return sbfEnrolType.toString();
	}//end of populateEnrollType(DynaActionForm frmProductList)

	/**
	 * This menthod for document viewer information
	 * @param request HttpServletRequest object which contains hospital information.
	 * @param policyDetailVO PolicyDetailVO object which contains policy information.
	 * @exception throws TTKException
	 */
	private void documentViewer(HttpServletRequest request, ProductVO productVO) throws TTKException
	{
//		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<String> alDocviewParams = new ArrayList<String>();
		alDocviewParams.add("leftlink="+TTKCommon.getActiveLink(request));
		alDocviewParams.add("product_seq_id="+productVO.getProdSeqId());
		if(request.getSession().getAttribute("toolbar")!=null)
			((Toolbar)request.getSession().getAttribute("toolbar")).setDocViewParams(alDocviewParams);
	}//end of documentViewer(HttpServletRequest request, ProductVO productVO)
	
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
}
