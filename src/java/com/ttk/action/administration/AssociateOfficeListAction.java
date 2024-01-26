/**
 * @ (#) AssociateOfficeListAction.java Nov 08, 2005
 * Project       : Vidal Health TPA Services
 * File          : AssociateOfficeListAction.java
 * Author        : Balaji C R B
 * Company       : Span Systems Corporation
 * Date Created  : Oct 16, 2007
 * @author       : Balaji C R B 
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
import com.ttk.business.administration.TtkOfficeManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ProductVO;

/**
 * This class is reusable for searching the list of Asociated Hospitals  in Administration Products and Policies.
 * This class also provides option for removing the Asociated Hospitals.
 */

public class AssociateOfficeListAction extends TTKAction {
	//Getting Logger for this Class file
	private static Logger log = Logger.getLogger( AssociateOfficeListAction.class ); 
	
	// Action mapping forwards.
	private static final String strOfficeList="officelist";
	private static final String strAssociateProduct="associateproduct";
	
	//Exception Message Identifier
	private static final String strAssociateOffice="Associate Office";
	
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
			log.debug("Inside the doDefault method of AssociateOfficeListAction");
			setLinks(request);
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			DynaActionForm frmAssociateOffice = (DynaActionForm)form;
			DynaActionForm frmAssociateProduct = (DynaActionForm)request.getSession().getAttribute("frmAssociateProduct");
			StringBuffer sbfCaption=new StringBuffer();             //get the caption to be displayed
			sbfCaption.append(buildCaption(strActiveSubLink,request));
			String strOffice=(String)request.getParameter("strOfficeType");
			String strRegOffIds = (String)frmAssociateProduct.get("regOfficeSeqIds");
			String strDivOffIds = (String)frmAssociateProduct.get("divOfficeSeqIds");
			String strBranOffIds = (String)frmAssociateProduct.get("branchOfficeSeqIds");
			
			frmAssociateOffice.initialize(mapping);
			frmAssociateOffice.set("sOfficeType",strOffice);
			if("IRO".equals(strOffice))
			{
				frmAssociateOffice.set("sOffIds",strRegOffIds);
				sbfCaption = (new StringBuffer("List of Regional Offices - ").append(sbfCaption));
			}
			else if("IBO".equals(strOffice)){
				frmAssociateOffice.set("sOffIds",strBranOffIds);
				sbfCaption = (new StringBuffer("List of Branch Offices - ").append(sbfCaption));
			}
			else if("IDO".equals(strOffice)){
				frmAssociateOffice.set("sOffIds",strDivOffIds);
				sbfCaption = (new StringBuffer("List of Divisional Offices - ").append(sbfCaption));
			}
			
			frmAssociateOffice.set("caption",sbfCaption.toString());
			TtkOfficeManager ttkOfficeManager = this.getTtkOfficeManagerObject();			
			log.debug("OfficeType :"+strOffice);			
			ArrayList alOfficeList = ttkOfficeManager.getAssOfficeDetails(strOffice,TTKCommon.getWebBoardId(request));
			request.setAttribute("alOfficeList",alOfficeList);			
			return this.getForward(strOfficeList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAssociateOffice));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to Associate the checked record seq ids with pipe concatenated and keep in 
	 * form bean value in session 
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAssociate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doAssociate method of AssociateOfficeListAction");
			setLinks(request);
			//String strOffice=(String)request.getParameter("strOfficeType");
			DynaActionForm frmAssociateOffice = (DynaActionForm)form;
			String strOffice = (String)frmAssociateOffice.get("sOfficeType");
			log.debug("off id " +frmAssociateOffice.get("sOffIds"));
			DynaActionForm frmAssociateProduct = (DynaActionForm)request.getSession().getAttribute("frmAssociateProduct");
			if("IRO".equals(strOffice))
				frmAssociateProduct.set("regOfficeSeqIds",frmAssociateOffice.get("sOffIds"));
			else if("IBO".equals(strOffice))
				frmAssociateProduct.set("branchOfficeSeqIds",frmAssociateOffice.get("sOffIds"));
			else if("IDO".equals(strOffice))
				frmAssociateProduct.set("divOfficeSeqIds",frmAssociateOffice.get("sOffIds"));
			
			frmAssociateProduct.set("frmChanged","changed");
			request.getSession().setAttribute("frmAssociateProduct",frmAssociateProduct);
			TtkOfficeManager ttkOfficeManager = this.getTtkOfficeManagerObject();
			ArrayList alOfficeList = ttkOfficeManager.getAssOfficeDetails(strOffice,TTKCommon.getWebBoardId(request));
			request.setAttribute("alOfficeList",alOfficeList);			
			return this.getForward(strOfficeList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAssociateOffice));
		}//end of catch(Exception exp)
	}//end of doAssociate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to forward to the parent screen
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
			log.debug("Inside the doClose method of AssociateOfficeListAction");	
			setLinks(request);
			DynaActionForm frmAssociateProduct= (DynaActionForm)request.getSession().getAttribute("frmAssociateProduct");
			request.getSession().setAttribute("frmAssociateProduct",frmAssociateProduct);
			return mapping.findForward(strAssociateProduct);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strAssociateOffice));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	/**
     * This method is prepares the Caption based on the flow and retunrs it
     * @param strActiveSubLink current Active sublink
     * @param request current HttpRequest
     * @return String caption built
     * @throws TTKException
     */
    private String buildCaption(String strActiveSubLink,HttpServletRequest request)throws TTKException
    {
        StringBuffer sbfCaption=new StringBuffer();
        ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
       
        ProductVO productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
        sbfCaption.append("[").append(productVO.getCompanyName()).append("]");
        sbfCaption.append("[").append(productVO.getProductName()).append("]");     
        
        return sbfCaption.toString();
    }//end of buildCaption(String strActiveSubLink,HttpServletRequest request)
	
    /**
	 * Returns the TtkOfficeManager session object for invoking methods on it.
	 * @return TtkOfficeManager session object which can be used for method invokation.
	 * @exception throws TTKException.
	 */
	private TtkOfficeManager getTtkOfficeManagerObject() throws TTKException
	{
		TtkOfficeManager ttkOfficeManager = null;
		try
		{
			if(ttkOfficeManager == null)
			{
				InitialContext ctx = new InitialContext();
				ttkOfficeManager = (TtkOfficeManager) ctx.lookup("java:global/TTKServices/business.ejb3/TtkOfficeManagerBean!com.ttk.business.administration.TtkOfficeManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp,"associate");//to clarify
		}//end of catch
		return ttkOfficeManager;
	}//end getTtkOfficeManagerObject()
	
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
                log.debug("Inside ProductPolicyManager: productPolicyManager: " + productPolicyManager);
            }//end if(productPolicyManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "ProductManager");
        }//end of catch
        return productPolicyManager;
    }//end of getProductPolicyManagerObject()
	
} // end of AssociateOfficeListAction()