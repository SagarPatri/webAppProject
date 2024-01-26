package com.ttk.action.administration;

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
import com.ttk.dto.administration.WebconfigInsCompInfoVO;

import formdef.plugin.util.FormUtils;

public class InsCompanyInfoAction extends TTKAction
{
	private static Logger log = Logger.getLogger( InsCompanyInfoAction.class );
	//Forwards
	private static final String strWebLogin = "weblogin";
	private static final String strInsCompinfo="inscompinfo";
	//Exception Message Identifier
	private static final String strWebconfig="webconfig";
	
	/**
	 * This method is used to used to veiw the details of the insurance company information.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doViewInsCompInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doViewInsCompInfo method of InsCompanyInfoAction");
			setLinks(request);
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			WebconfigInsCompInfoVO webconfigInsCompInfoVO = null;
			DynaActionForm frmInsCompanyInfo = (DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append(" - [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			webconfigInsCompInfoVO=productpolicyObject.getWebConfigInsInfo(TTKCommon.getWebBoardId(request));
			if(webconfigInsCompInfoVO != null )
			{
				webconfigInsCompInfoVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
				frmInsCompanyInfo = (DynaActionForm)FormUtils.setFormValues("frmInsCompanyInfo",
						webconfigInsCompInfoVO, this, mapping, request);
			}// end of if(webconfigInsCompInfoVO != null )
			else
			{
				frmInsCompanyInfo.initialize(mapping);
			}//end of else
			frmInsCompanyInfo.set("caption",sbfCaption.toString());
			request.setAttribute("frmInsCompanyInfo",frmInsCompanyInfo);		
			return this.getForward(strInsCompinfo, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strWebconfig));
		}//end of catch(Exception exp)
	}//end of doViewInsCompInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
			log.debug("Inside the doSave method of InsCompanyInfoAction");
			setLinks(request);	
			DynaActionForm frmInsCompanyInfo = (DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption=sbfCaption.append(" - [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			ProductPolicyManager productpolicyObject=this.getProductPolicyManager();
			WebconfigInsCompInfoVO webconfigInsCompInfoVO = new WebconfigInsCompInfoVO();
			webconfigInsCompInfoVO =(WebconfigInsCompInfoVO)FormUtils.getFormValues(frmInsCompanyInfo, "frmInsCompanyInfo",this, mapping, request);
			webconfigInsCompInfoVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
			webconfigInsCompInfoVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			int iResult = productpolicyObject.saveWebConfigInsInfo(webconfigInsCompInfoVO);	
			if(iResult>0)
			{
					request.setAttribute("updated","message.saved");
			}//end of if(iResult>0)	
			webconfigInsCompInfoVO=productpolicyObject.getWebConfigInsInfo(TTKCommon.getWebBoardId(request));				
			frmInsCompanyInfo = (DynaActionForm)FormUtils.setFormValues("frmInsCompanyInfo",
					webconfigInsCompInfoVO, this, mapping, request);
			frmInsCompanyInfo.set("caption",sbfCaption.toString());
			request.setAttribute("frmInsCompanyInfo",frmInsCompanyInfo);
			return this.getForward(strInsCompinfo, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strWebconfig));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside the doClose method of InsCompanyInfoAction");
			setLinks(request);
			return this.getForward(strWebLogin, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strWebconfig));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return ProductPolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ProductPolicyManager getProductPolicyManager() throws TTKException
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
			throw new TTKException(exp, strWebconfig);
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManager()
}//end of InsCompanyInfoAction()
