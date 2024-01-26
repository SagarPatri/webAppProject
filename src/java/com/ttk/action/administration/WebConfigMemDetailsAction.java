/**
 * @ (#) WebConfigMemDetailsAction.java Jan 4th, 2008
 * Project      : TTK HealthCare Services
 * File         : WebConfigMemDetailsAction.java
 * Author       : Srikant_b
 * Company      : Span Systems Corporation
 * Date Created : Jan 4th, 2008
 * 
 * @author       :
 * Modified by   :Balaji C R B
 * Modified date :February 1,2008
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
import com.ttk.dto.administration.WebConfigMemberVO;
/**
 * This class is used to display Field names to be configured for the member.
 * 
 */

public class WebConfigMemDetailsAction extends TTKAction{
	private static Logger log = Logger.getLogger( WebConfigMemDetailsAction.class );
	
	//	Forwards
	private static final String strWebConfigMemDetails="webconfigmemdetails";
	private static final String strWebConfig="webconfigdetails";
	
	//Exception Message Identifier
	private static final String strMemberDetails="webconfigmemberdetails";
	
	
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
			log.info("Inside the doDefault method of WebConfigMemDetailsAction");
			setLinks(request);	
			if(TTKCommon.getWebBoardId(request)==null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.policy.required");
				throw expTTK;
			}//end of if(TTKCommon.getWebBoardId(request)==null)
			DynaActionForm frmWebconfigMemDetails=(DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			ProductPolicyManager productPolicyManager=this.getProductPolicyManager();
			
			ArrayList<Object> alWebConfigMemList=productPolicyManager.getWebConfigMemberDetail(TTKCommon.getWebBoardId(request));
			if(alWebConfigMemList!= null && alWebConfigMemList.size()>0)
			{
				frmWebconfigMemDetails.set("memberdetails",(WebConfigMemberVO[])alWebConfigMemList.toArray(new WebConfigMemberVO[0]));
				
			}//end of if(alWebConfigMemList!= null && alWebConfigMemList.size()>0)
			sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			frmWebconfigMemDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmWebconfigMemDetails",frmWebconfigMemDetails);
			request.setAttribute("fieldstatusgeneraltypeids",null);
			return this.getForward(strWebConfigMemDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{					
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{					
			return this.processExceptions(request, mapping, new TTKException(exp,strMemberDetails));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			log.info("Inside the doSave method of WebConfigMemDetailsAction");
			setLinks(request);	
			ProductPolicyManager productPolicyManager=this.getProductPolicyManager();
			DynaActionForm frmWebconfigMemDetails = (DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			ArrayList<Object> alWebConfigMemDetails=new ArrayList<Object>();
			ArrayList<Object> alWebConfigMemList = null;
			WebConfigMemberVO webConfigMemberVO=null;
			String strDOBShowField = null;
			String strAgeShowField = null;
			
			String[] strMemberConfigSeqID=request.getParameterValues("memberConfigSeqID");
			String[] strPolicyMemFieldTypeID=request.getParameterValues("policyMemFieldTypeID");
			String[] strPolicySeqID=request.getParameterValues("policySeqID");
			String[] strFieldStatusGenTypeID=request.getParameterValues("fieldStatusGenTypeID");
			String[] strFieldName = request.getParameterValues("fieldName");
			
			for(int i=0;i<strFieldName.length;i++){
				if(strFieldName[i].equals("MEM_DOB")){
					strDOBShowField = strFieldStatusGenTypeID[i];
				}//end of if(strFieldName[i].equals("MEM_DOB"))
				else if(strFieldName[i].equals("MEM_AGE")){
					strAgeShowField = strFieldStatusGenTypeID[i];
				}//end of else if(strFieldName[i].equals("MEM_AGE"))
			}//end of for(int i=0;i<strFieldName.length;i++)
			if(!strDOBShowField.equals("MCE") && !strAgeShowField.equals("MCE")){
				request.setAttribute("doborageeditable","error.administration.policy.webconfig.memberdetails");
				request.setAttribute("fieldstatusgeneraltypeids",strFieldStatusGenTypeID);
				return this.getForward(strWebConfigMemDetails, mapping, request);
			}//end of if(!strDOBShowField.equals("MCE") && !strAgeShowField.equals("MCE")){			
			
			if(strMemberConfigSeqID!=null && strMemberConfigSeqID.length>0)
			{
				//loop througth the no of members and prepare the ArrayList of webConfigMemberVO's
				for(int iCount=0;iCount<strMemberConfigSeqID.length;iCount++)
				{
					webConfigMemberVO=new WebConfigMemberVO();
					webConfigMemberVO.setMemberConfigSeqID(TTKCommon.checkNull(strMemberConfigSeqID[iCount]).equals("")?
							null:new Long(strMemberConfigSeqID[iCount]));
					webConfigMemberVO.setPolicyMemFieldTypeID(strPolicyMemFieldTypeID[iCount]);
					webConfigMemberVO.setPolicySeqID(TTKCommon.getLong(strPolicySeqID[iCount]));
					webConfigMemberVO.setFieldStatusGenTypeID(strFieldStatusGenTypeID[iCount]);
					webConfigMemberVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
					alWebConfigMemDetails.add(webConfigMemberVO);
					
				}//end of for(int iCount=0;iCount<strMemberConfigSeqID.length;iCount++)
			}//end of if
//			log.info("PolicySeqID..........."+webConfigMemberVO.getPolicySeqID());
//			log.info("PolicyMemFieldTypeID..........."+webConfigMemberVO.getPolicyMemFieldTypeID());
//			log.info("FieldStatusGenTypeID..........."+webConfigMemberVO.getFieldStatusGenTypeID());
//			
			if(alWebConfigMemDetails!=null && alWebConfigMemDetails.size()>0)
			{
				int iResult = productPolicyManager.saveWebConfigMemberDetail(alWebConfigMemDetails,TTKCommon.getWebBoardId(request));
				log.info("after saving....."+iResult);
				if(iResult>0)    //if record is updated then requery
				{
					frmWebconfigMemDetails.initialize(mapping);
					sbfCaption=sbfCaption.append("- [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
					frmWebconfigMemDetails.set("caption",sbfCaption.toString());
					webConfigMemberVO=null;
					alWebConfigMemList =new ArrayList<Object>();
					alWebConfigMemList=productPolicyManager.getWebConfigMemberDetail(TTKCommon.getWebBoardId(request));
					if(alWebConfigMemList!= null && alWebConfigMemList.size()>0)
					{
						frmWebconfigMemDetails.set("memberdetails",(WebConfigMemberVO[])alWebConfigMemList.toArray(new WebConfigMemberVO[0]));
					}//end of if(alWebConfigMemList!= null && alWebConfigMemList.size()>0)
					request.setAttribute("updated","message.savedSuccessfully");
				}//end of if(iCount>0)
			}//end of if(alDebitDeposit!=null && alDebitDeposit.size()>0)
			
			request.getSession().setAttribute("frmWebconfigMemDetails",frmWebconfigMemDetails);
			return this.getForward(strWebConfigMemDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strMemberDetails));
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
			log.info("Inside the doReset method of WebConfigMemDetailsAction");
			setLinks(request);
			return doDefault(mapping,form,request,response);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strMemberDetails));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to close the current page and return to previous page.
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
			setLinks(request);
			log.info("Inside doClose method of WebConfigMemDetailsAction");
			request.getSession().removeAttribute("frmWebconfigMemDetails");
			return this.getForward(strWebConfig, mapping, request);
			//return mapping.findForward(strClose);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strMemberDetails));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
			throw new TTKException(exp, strMemberDetails);
		}//end of catch
		return productPolicyManager;
	}//end getProductPolicyManager()
	
}//end of MemberDetailsAction
