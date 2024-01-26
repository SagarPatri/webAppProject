/**
 * @ (#) UserGroupAction.java Jan 20, 2006
 * Project       : TTK HealthCare Services
 * File          : UserGroupAction.java
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Jan 20, 2006
 * @author       : Bhaskar Sandra
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
import com.ttk.action.usermanagement.AssociateUserAction;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.security.GroupVO;
/**
* This class is used for displaying the User Group.
* This also provides  updation of User Group.
*/
public class UserGroupAction extends TTKAction {
	
	private static Logger log = Logger.getLogger( AssociateUserAction.class ); 
	
	//Action mapping forwards.
	private static final String strFrwdUserGroup="usergroup";
	
	//Exception Message Identifier
	private static final String strUserGroupExp="usergroup";
	
	/**
	 * This method is used to display the user group.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doUserGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside doUserGroup method of UserGroupAction");
			setLinks(request);
			if(TTKCommon.getWebBoardId(request)==null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.product.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request)==null)
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			ArrayList alUserGroup=null;
			DynaActionForm frmUserGroup=(DynaActionForm)form;
			alUserGroup=productPolicyManagerObject.getUserGroup(TTKCommon.getWebBoardId(request));
			frmUserGroup.set("usergroup",alUserGroup);
			getCaption(frmUserGroup,request);
			request.setAttribute("frmProductUserGroup",frmUserGroup);
			return mapping.findForward(strFrwdUserGroup);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strUserGroupExp));
		}//end of catch(Exception exp)
	}//end of UserGroup(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.debug("Inside doSave method of UserGroupAction");
			setLinks(request);
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			ArrayList alUserGroup=null;
			DynaActionForm frmUserGroup=(DynaActionForm)form;
			String strConcateSeqId=this.getConcateSeqID(request);
			int iCount=productPolicyManagerObject.saveUserGroup(TTKCommon.getWebBoardId(request),
												strConcateSeqId,TTKCommon.getUserSeqId(request));
			if(iCount>0)
			{
				request.setAttribute("updated","message.saved");
			}//end of if(iCount>0)
			alUserGroup=productPolicyManagerObject.getUserGroup(TTKCommon.getWebBoardId(request));
			frmUserGroup.set("usergroup",alUserGroup);
			getCaption(frmUserGroup,request);
			request.setAttribute("frmProductUserGroup",frmUserGroup);
			return mapping.findForward(strFrwdUserGroup);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strUserGroupExp));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception{
		log.debug("Inside doChangeWebBoard method of UserGroupAction");
		return doUserGroup(mapping,form,request,response);
	}//end of ChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
	/**
	 * This method is used to manipulate the caption.
	 *
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @throws Exception if any error occurs
	 */
	private void getCaption(DynaActionForm frmUserGroup,HttpServletRequest request)throws TTKException
	{
		try
		{
			ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
			ArrayList alUserGroup=null;
			String strCaption ="";
			alUserGroup=productPolicyManagerObject.getUserGroup(TTKCommon.getWebBoardId(request));
			if(alUserGroup!=null && alUserGroup.size()>0)
			{
				strCaption = ((GroupVO)alUserGroup.get(0)).getInsCompName();
			}//end of if(alUserGroup!=null && alUserGroup.size()>0)
			frmUserGroup.set("caption",strCaption);
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strUserGroupExp);
		}//end of catch
	}//end of getCaption(DynaActionForm frmUserGroup,HttpServletRequest request)
	
	/**
	 * This method returns Concatenated String of Tpa office Sequence IDs
	 * @param HttpServletRequest
	 * @return String
	 */
	private String getConcateSeqID(HttpServletRequest request) {
		
		String strArrayOffSeqId[]=request.getParameterValues("officeSeqID");
		String strChkBox[]=request.getParameterValues("chbox");
		StringBuffer sbrOffSeqID=null;
		if(strChkBox!=null && strArrayOffSeqId!=null)
		{
			sbrOffSeqID=new StringBuffer("|");
			for(int i = 0; i < strChkBox.length; i++)
			{
				sbrOffSeqID.append(strChkBox[i]).append("|");
			} //end of for
		}//end of if(strChkBox!=null && strArrayOffSeqId!=null)
		if(sbrOffSeqID!=null)
		{
			return sbrOffSeqID.toString();
		}//end of if(sbrOffSeqID!=null)
		else
		{
			return null;
		}//end of else
	}//end of getConcateSeqID()
	
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
			}//end if(productPolicyManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "ProductManager");
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManagerObject()
	
}//end of UserGroupAction
