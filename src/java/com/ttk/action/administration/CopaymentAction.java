/**
 * @ (#) CopaymentAction.java
 * Project       : TTK HealthCare Services
 * File          : CopaymentAction.java
 * Author        : Balaji C R B
 * Company       : Span Systems Corporation
 * Date Created  : Nov 03, 2008
 * @author       : Balaji C R B
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
import com.ttk.business.empanelment.HospitalManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.HospitalCopayVO;
import com.ttk.dto.empanelment.HospitalVO;

import formdef.plugin.util.FormUtils;


/**
 * This class is used to bring Copyment screen in Administration-Products/Polices 
 */

public class CopaymentAction extends TTKAction {
	 //Getting Logger for this Class file
	private static Logger log = Logger.getLogger( CopaymentAction.class ); 
	
	// Action mapping forwards.	
	private static final String strCopaymentchargesproduct="copaymentchargesproduct"; 
	private static final String strCopaymentchargespolicy="copaymentchargespolicy";
	
	//Exception Message Identifier
	private static final String strCopay="copayment";
	
	/**
	 * This method is used to initialize copayment screen
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
			log.debug("Inside the doDefault method of CopaymentAction");
			setLinks(request);
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);			
			DynaActionForm  frmCopayment = (DynaActionForm)form;
			frmCopayment.initialize(mapping);
			HospitalVO hospitalVO =null;
			StringBuffer sbfCaption = new StringBuffer();
			if(request.getAttribute("hospitalVO")!=null){
				hospitalVO = (HospitalVO)request.getAttribute("hospitalVO");
				log.debug("hospseqid value is " +hospitalVO.getHospSeqId() );
				frmCopayment.set("hospSeqID",hospitalVO.getHospSeqId().toString());
				if(hospitalVO.getCopayAmt()!=null){
					log.debug("copay amt is " + hospitalVO.getCopayAmt());
					frmCopayment.set("copayAmt",hospitalVO.getCopayAmt().toString());
				}//end of if(hospitalVO.getCopayAmt()!=null)
				if(hospitalVO.getCopayPerc()!=null){
					log.debug("copayPerc is " + hospitalVO.getCopayPerc());
					frmCopayment.set("copayPerc",hospitalVO.getCopayPerc().toString());
				}//end of if(hospitalVO.getCopayPerc()!=null)
				frmCopayment.set("caption"," ["+hospitalVO.getHospitalName()+"]");
				log.debug("hospital name is " + hospitalVO.getHospitalName());
			}//end of if(request.getAttribute("lngProdHospSeqID")!=null)
			else {
				sbfCaption.append(" [Network Hospitals]");
				frmCopayment.set("caption",sbfCaption.toString());				
			}//end of else	
			if(request.getAttribute("alHospcopay")!=null){
				ArrayList alHospcopay = (ArrayList)request.getAttribute("alHospcopay");
				request.getSession().setAttribute("alHospcopay",alHospcopay);
			}//end of if(request.getAttribute("alHospcopay"))!=null)
			//request.getSession().setAttribute("frmCopayment",frmCopayment);
			if("Products".equals(strActiveSubLink)){
				return mapping.findForward(strCopaymentchargesproduct);
			}//end of if("Products".equals(strActiveSubLink))
			else {
				return mapping.findForward(strCopaymentchargespolicy);
			}//end of else			
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCopay));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to save Copyament details
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
			log.debug("Inside the doSave method of CopaymentAction");
			setLinks(request);
			HospitalManager hospitalManagerObject=this.getHospitalManagerObject();
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			int iResult=0;
			DynaActionForm  frmCopayment = (DynaActionForm)form;
			frmCopayment.set("prodPolicySeqID",TTKCommon.getWebBoardId(request).toString());
			frmCopayment.set("updatedBy",TTKCommon.getUserSeqId(request).toString());
			HospitalCopayVO hospitalCopayVO = (HospitalCopayVO)FormUtils.getFormValues(frmCopayment, this, mapping, request);
			if(request.getSession().getAttribute("alHospcopay")!=null){	
				ArrayList alHospcopay = (ArrayList)request.getSession().getAttribute("alHospcopay");
				alHospcopay.add(hospitalCopayVO.getProdPolicySeqID());
				alHospcopay.add(hospitalCopayVO.getCopayAmt());
				alHospcopay.add(hospitalCopayVO.getCopayPerc());
				alHospcopay.add(hospitalCopayVO.getUpdatedBy());
				request.getSession().removeAttribute("alHospcopay");
				iResult = hospitalManagerObject.saveAssocHospCopay(alHospcopay);
			}//end of if(request.getAttribute("alHospcopay")!=null)
			if(iResult>0){
				//saved successfully
				request.setAttribute("updated","message.saved");
			}//end of if(iResult>0) 
			if("Products".equals(strActiveSubLink)){
				return mapping.findForward(strCopaymentchargesproduct);
			}//end of if("Products".equals(strActiveSubLink))
			else {
				return mapping.findForward(strCopaymentchargespolicy);
			}//end of else
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCopay));
		}//end of catch(Exception exp)
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to close the Copayment screen and traverse to Hospital list screen
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
			log.debug("Inside the doClose method of CopaymentAction");
			setLinks(request);
			String strActiveSubLink=TTKCommon.getActiveSubLink(request);
			if("Products".equals(strActiveSubLink)){
				return mapping.findForward(strCopaymentchargesproduct);
			}//end of if("Products".equals(strActiveSubLink))
			else {
				return mapping.findForward(strCopaymentchargespolicy);
			}//end of else			
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strCopay));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)	
	
	/**
	 * Returns the HospitalManager session object for invoking methods on it.
	 * @return HospitalManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private HospitalManager getHospitalManagerObject() throws TTKException
	{
		HospitalManager hospitalManager = null;
		try
		{
			if(hospitalManager == null)
			{
				InitialContext ctx = new InitialContext();
				hospitalManager = (HospitalManager) ctx.lookup("java:global/TTKServices/business.ejb3/HospitalManagerBean!com.ttk.business.empanelment.HospitalManager");
			}//end if(hospitalManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "Copay");
		}//end of catch
		return hospitalManager;
	}//end getHospitalManagerObject()
	
} // end of CopaymentAction