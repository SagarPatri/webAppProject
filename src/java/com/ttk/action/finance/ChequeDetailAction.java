/**
* @ (#) ChequeDetailAction.java Jun 17th, 2006
* Project 		: TTK HealthCare Services
* File 			: ChequeDetailAction.java
* Author 		: Krishna K H
* Company 		: Span Systems Corporation
* Date Created 	: Jun 17th, 2006
*
* @author 		: Krishna K H
* Modified by 	:
* Modified date :
* Reason 		:
*/

package com.ttk.action.finance;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.business.finance.ChequeManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.BankAddressVO;
import com.ttk.dto.finance.ChequeDetailVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for displaying the Cheque Details.
 * This class also provides option for updating the Cheque Details .
 */
public class ChequeDetailAction extends TTKAction
{
    //action forword
    private static final String strPayments = "payments";
    private static final String strChequeSearch ="chequesearch";
    private static final String strChequedetail ="chequedetail";
    private static Logger log = Logger.getLogger( ChequeDetailAction.class );
 	private static final String strChequeDetail="groupdetail";
	
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
 			log.debug("Inside ChequeDetailAction doDefault");
 			TTKException expTTK = new TTKException();
 			if(TTKCommon.getActiveSubLink(request).equals("Cheque Information"))
 			{
 				expTTK.setMessage("error.cheque.required");
 			}//end of if(TTKCommon.getActiveSubLink(request).equals("Policies"))
 			throw expTTK;
 		}//end of try
 		catch(TTKException expTTK)
 		{
 			return this.processExceptions(request, mapping, expTTK);
 		}//end of catch(TTKException expTTK)
 		catch(Exception exp)
 		{
 			return this.processExceptions(request, mapping, new TTKException(exp, strChequeDetail));
 		}//end of catch(Exception exp)
 	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
 	public ActionForward doViewCheque(ActionMapping mapping,ActionForm form,HttpServletRequest request,
 									  HttpServletResponse response) throws Exception{
 		try{
 			setLinks(request);
 			log.debug("Inside ChequeDetailAction doViewCheque");
 			ChequeDetailVO chequeDetailVO = null;
 			BankAddressVO bankAddressVO = null;
 			StringBuffer strCaption = new StringBuffer();
 			DynaActionForm frmChequeDetail =(DynaActionForm)form;
 			ChequeManager chequeObject=this.getChequeManagerObject();
 			chequeDetailVO = new ChequeDetailVO();
 			bankAddressVO = new BankAddressVO();
 			Long lngFloatSeqID = null;
 			if(TTKCommon.getActiveSubLink(request).equals("Float Account"))
 			{
 				lngFloatSeqID = TTKCommon.getWebBoardId(request);
 				strCaption.append("Cheque Printing Details - ["+TTKCommon.getWebBoardDesc(request)+"]");
 				
 			}
 			if(frmChequeDetail.getString("seqID")!=null)
 			{
 				chequeDetailVO=chequeObject.getChequeDetail(TTKCommon.getLong(frmChequeDetail.getString("seqID")),
 															TTKCommon.getLong(frmChequeDetail.getString("paymentSeqId")),
 																	lngFloatSeqID,TTKCommon.getUserSeqId(request));
 			}
 			if(TTKCommon.getActiveSubLink(request).equals("Cheque Information"))
 			{
 				strCaption.append("Payment Transaction Details - ["+chequeDetailVO.getChequeNo()+"]");
 				
 			}
 			if(chequeDetailVO!=null && chequeDetailVO.getBankAddressVO()!=null)
 			{
 				bankAddressVO=chequeDetailVO.getBankAddressVO();
 			}//end of if(chequeDetailVO!=null && chequeDetailVO.getBankAddressVO()!=null)
 			frmChequeDetail = (DynaActionForm)FormUtils.setFormValues("frmChequeDetail",chequeDetailVO,this,mapping,
 																	   request);
 			frmChequeDetail.set("bankAddressVO", (DynaActionForm)FormUtils.setFormValues("frmFinanceBankAddress",
 																			   bankAddressVO,this,mapping,request));
 			frmChequeDetail.set("status",chequeDetailVO.getStatusTypeID());
 			frmChequeDetail.set("caption",strCaption.toString());
 			
 			
 			request.getSession().setAttribute("frmChequeDetail",frmChequeDetail);
 			return (mapping.findForward(strChequedetail));
 		}//end of try
 		catch(TTKException expTTK)
 		{
 			return this.processExceptions(request, mapping, expTTK);
 		}//end of catch(TTKException expTTK)
 		catch(Exception exp)
 		{
 			return this.processExceptions(request, mapping, new TTKException(exp, strChequeDetail));
 		}//end of catch(Exception exp)
 	}//end of doViewCheque(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
 	
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
    	log.debug("Inside ChequeDetailAction doReset");
    	return doViewCheque(mapping,form,request,response);
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside ChequeDetailAction doSave");
    		ChequeDetailVO chequeDetailVO = null;
    		BankAddressVO bankAddressVO = null;
    		DynaActionForm frmChequeDetail =(DynaActionForm)form;
    		ChequeManager chequeObject=this.getChequeManagerObject();
    		StringBuffer strCaption = new StringBuffer();
    		chequeDetailVO = new ChequeDetailVO();
    		bankAddressVO = new BankAddressVO();
    		//saveCheque
    		//get the value from form and store it to the respective VO's
    		chequeDetailVO=(ChequeDetailVO)FormUtils.getFormValues(frmChequeDetail,this,mapping,request);
    		chequeDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
    		ActionForm chequeAddressForm=(ActionForm)frmChequeDetail.get("bankAddressVO");
    		bankAddressVO=(BankAddressVO)FormUtils.getFormValues(chequeAddressForm,"frmFinanceBankAddress",
    														     this,mapping,request);
    		chequeDetailVO.setBankAddressVO(bankAddressVO);
    		long result=chequeObject.saveCheque(chequeDetailVO);
    		if(result>0)
    		{
    			request.setAttribute("updated","message.savedSuccessfully");
    		}//end of if(result>0)
    		if(TTKCommon.getActiveSubLink(request).equals("Float Account"))
    		{
    			strCaption.append("Cheque Printing Details - ["+TTKCommon.getWebBoardDesc(request)+"]");
    			
    		}
    		else if(TTKCommon.getActiveSubLink(request).equals("Cheque Information"))
    		{
    			strCaption.append("Cheque Details - ["+chequeDetailVO.getChequeNo()+"]");
    		}//end of else if(TTKCommon.getActiveSubLink(request).equals("Cheque Information"))
    		frmChequeDetail.set("status",chequeDetailVO.getStatusTypeID());
    		frmChequeDetail.set("caption",strCaption.toString());
    		request.getSession().setAttribute("frmChequeDetail",frmChequeDetail);
    		return (mapping.findForward("savechequedetails"));
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strChequeDetail));
    	}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    						     HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside ChequeDetailAction doClose");
    		String strForword="";
    		if(TTKCommon.getActiveSubLink(request).equals("Float Account"))
    		{
                strForword=strPayments;
    		}//end of if(TTKCommon.getActiveSubLink(request).equals("Float Account"))
            else
            {
                strForword=strChequeSearch;
            }//end of else
    		return (mapping.findForward(strForword));
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strChequeDetail));
    	}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * Returns the ChequeManager session object for invoking methods on it.
     * @return ChequeManager session object which can be used for method invocation
     * @exception throws TTKException
     */
    private ChequeManager getChequeManagerObject() throws TTKException
    {
        ChequeManager chequeManager = null;
        try
        {
            if(chequeManager == null)
            {
                InitialContext ctx = new InitialContext();
                chequeManager = (ChequeManager) ctx.lookup("java:global/TTKServices/business.ejb3/ChequeManagerBean!com.ttk.business.finance.ChequeManager");
            }//end if(chequeManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "ChequeSearch");
        }//end of catch
        return chequeManager;
    }//end getChequeManagerObject()
}//end of class AddMemberDetailAction


