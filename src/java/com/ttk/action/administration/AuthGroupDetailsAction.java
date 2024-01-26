/**
 * @ (#) AuthGroupDetailsAction.java   Aug 12, 2008
 * Project        : Vidal Health TPA Services
 * File           : AuthGroupDetailsAction.java
 * Author         : Sendhil Kumar V
 * Company        : Span Systems Corporation
 * Date Created   : Aug 12, 2008
 *
 * @author        : Sendhil Kumar V
 * Modified by    : 
 * Modified date  :
 * Reason         : 
 */
package com.ttk.action.administration;

/**
 * This class is used to add/update the Auth Group Details.
 */
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.ProdPolicyConfigManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.AuthGroupVO;

import formdef.plugin.util.FormUtils;

public class AuthGroupDetailsAction extends TTKAction
{
	private static Logger log = Logger.getLogger( AuthGroupDetailsAction.class );

	//Action mapping forwards.
	private static final String strAddUpdateAuthGroup="addupdateauthgroup";
	private static final String strAuthGroupClose="authgroupclose";

	//Exception Message Identifier
	private static final String strProduct="product";

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
	public ActionForward onAddGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the onAddGroup method of AuthGroupDetailsAction");
			setLinks(request);
			DynaActionForm frmAuthGroupDetails = (DynaActionForm)form;
			DynaActionForm frmProductDetail =(DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			StringBuffer sbfCaption=new StringBuffer();
			sbfCaption = sbfCaption.append(" Add ").append(this.buildCaption(frmProductDetail));

			frmAuthGroupDetails.initialize(mapping);
			frmAuthGroupDetails.set("caption",String.valueOf(sbfCaption));

			request.setAttribute("frmAuthGroupList",frmAuthGroupDetails);
			return this.getForward(strAddUpdateAuthGroup, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of onAddGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doViewAuthGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doViewAuthGroup method of AuthGroupDetailsAction");
			setLinks(request);
			DynaActionForm frmAuthGroupDetails = (DynaActionForm)form;
			DynaActionForm frmAuthGroupList =(DynaActionForm)request.getSession().getAttribute("frmAuthGroupList");
			//get the session bean from the bean pool for each excecuting thread
			ProdPolicyConfigManager prodPolicyConfigManager=this.getProductPolicyConfigObject();
			TableData tableData = TTKCommon.getTableData(request);
			StringBuffer sbfCaption= new StringBuffer();
			sbfCaption.append(" Edit ").append(frmAuthGroupList.get("caption"));
			//create a new AuthGroup object
			AuthGroupVO authGroupVO=new AuthGroupVO();
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				authGroupVO = (AuthGroupVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
				//get the product details from the Dao object
				authGroupVO=prodPolicyConfigManager.getAuthGroupDetail(authGroupVO.getAuthGroupSeqID());
				frmAuthGroupDetails = (DynaActionForm)FormUtils.setFormValues("frmAuthGroupList",authGroupVO, this, mapping, request);
			}//end if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			else
			{
				frmAuthGroupDetails.initialize(mapping);
			}// end of else
			
			frmAuthGroupDetails.set("caption",sbfCaption.toString());
			request.setAttribute("frmAuthGroupList",frmAuthGroupDetails);
			return this.getForward(strAddUpdateAuthGroup, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doViewAuthGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doSave method of AuthGroupDetailsAction");
			setLinks(request);
			DynaActionForm frmAuthGroupDetails = (DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			String strCaption = "";
			strCaption = (String)frmAuthGroupDetails.get("caption");
			ProdPolicyConfigManager prodPolicyConfigManager = this.getProductPolicyConfigObject();
			AuthGroupVO authGroupVO=new AuthGroupVO();

			authGroupVO=(AuthGroupVO)FormUtils.getFormValues(frmAuthGroupDetails, "frmAuthGroupList",this,mapping, request);
			authGroupVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			
			if(frmAuthGroupDetails.getString("prodPolicySeqID").equals(""))
			{
				Long lngAuthGroupSeqID = TTKCommon.getWebBoardId(request);
				authGroupVO.setProdPolicySeqID(lngAuthGroupSeqID);
			}//end of if(frmAuthGroupDetails.getString("prodPolicySeqID").equals(""))
			
			prodPolicyConfigManager.saveAuthGroup(authGroupVO);
			if(frmAuthGroupDetails.getString("authGroupSeqID").equals(""))
			{
				frmAuthGroupDetails.initialize(mapping);
				sbfCaption.append(strCaption);
				request.setAttribute("updated","message.saved");
			}//end of if(frmPlanDetails.getString("authGroupSeqID").equals(""))
			else
			{
				sbfCaption.append(strCaption);
				request.setAttribute("updated","message.savedSuccessfully");
			}// end of else
			
			frmAuthGroupDetails.set("caption",sbfCaption.toString());
			request.setAttribute("frmAuthGroupList",frmAuthGroupDetails);
			return this.getForward(strAddUpdateAuthGroup, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
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
	public ActionForward doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try{
			log.debug("Inside the doReset method of AuthGroupDetailsAction");
			setLinks(request);
			//get the session bean from the bean pool for each excecuting thread
			DynaActionForm frmAuthGroupDetails = (DynaActionForm)form;
			DynaActionForm frmAuthGroupList =(DynaActionForm)request.getSession().getAttribute("frmAuthGroupList");
			StringBuffer sbfCaption= new StringBuffer();
			ProdPolicyConfigManager prodPolicyConfigManager=this.getProductPolicyConfigObject();
			AuthGroupVO authGroupVO=new AuthGroupVO();
			
			if(frmAuthGroupDetails.get("authGroupSeqID").equals(""))
			{
				frmAuthGroupDetails.initialize(mapping);
				sbfCaption.append(" Add ").append(frmAuthGroupList.get("caption"));
			}//end of if(frmAuthGroupDetails.get("authGroupSeqID").equals(""))
			else
			{
				Long lngAuthGroupSeqID = TTKCommon.getLong(frmAuthGroupDetails.getString("authGroupSeqID"));
				authGroupVO=prodPolicyConfigManager.getAuthGroupDetail(lngAuthGroupSeqID);
				frmAuthGroupDetails = (DynaActionForm)FormUtils.setFormValues("frmAuthGroupList",authGroupVO, this, mapping, request);
				sbfCaption.append(" Edit ").append(frmAuthGroupList.get("caption"));
			}//end of else
			frmAuthGroupDetails.set("caption",sbfCaption.toString());
			request.setAttribute("frmAuthGroupList",frmAuthGroupDetails);
			return this.getForward(strAddUpdateAuthGroup, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		try
		{
			log.debug("Inside the doClose method of AuthGroupDetailsAction");
			setLinks(request);
			return mapping.findForward(strAuthGroupClose);
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
	 * Returns the ProdPolicyConfigManager session object for invoking methods on it.
	 * @return productPolicyConfigManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private ProdPolicyConfigManager getProductPolicyConfigObject() throws TTKException
	{
		ProdPolicyConfigManager productPolicyConfigManager = null;
		try
		{
			if(productPolicyConfigManager == null)
			{
				InitialContext ctx = new InitialContext();
				productPolicyConfigManager = (ProdPolicyConfigManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProdPolicyConfigManagerBean!com.ttk.business.administration.ProdPolicyConfigManager");
			}//end if(productPolicyConfigManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch
		return productPolicyConfigManager;
	}//end of getProductPolicyConfigObject() throws TTKException

	/**
	 * This method  prepares the Caption based on the flow and retunrs it
	 * @param strActiveSubLink current Active sublink
	 * @param request current HttpRequest
	 * @return String caption built
	 * @throws TTKException
	 */
	private String buildCaption(DynaActionForm frmProductDetail)throws TTKException
	{
		StringBuffer sbfCaption=new StringBuffer();
		sbfCaption.append("[").append(frmProductDetail.getString("companyName")).append("]");
		sbfCaption.append("	[").append(frmProductDetail.getString("productName")).append("]");
		return sbfCaption.toString();
	}//end of buildCaption(DynaActionForm frmProductDetail)
}//end of AuthGroupDetailsAction class
