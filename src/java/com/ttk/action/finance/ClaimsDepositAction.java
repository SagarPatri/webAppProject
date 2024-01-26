/**
 * @ (#)  ClaimsDepositAction September 10, 2007
 * Project      : TTK HealthCare Services
 * File         : ClaimsDepositAction
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : September 10, 2007
 *
 * @author       :  Krupa J
 * Modified by   :
 * Modified date :
 * Reason        :
 * Modified by   :
 */

package com.ttk.action.finance;

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
import com.ttk.business.finance.FloatAccountManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.ClaimDepositVO;
import com.ttk.dto.finance.DebitNoteDepositVO;

public class ClaimsDepositAction extends TTKAction{

private static final Logger log = Logger.getLogger( ClaimsDepositAction.class );

	private static final String strClaims="debitNote";

	private static final String strDebitNote="debitnote";
	private static final String strClaimsDeposit="claimsdeposit";

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
	public ActionForward doAssociateClaims(ActionMapping mapping,ActionForm form,HttpServletRequest request,
								   HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside ClaimsDepositAction doAssociateClaims");
			StringBuffer strCaption= new StringBuffer();
			FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
			DynaActionForm frmClaimsDeposit = (DynaActionForm)form;
			DebitNoteDepositVO debitNoteDepositVO =(DebitNoteDepositVO)request.getSession().getAttribute("SelectedDebitNote");
			DynaActionForm frmFloatTrans=(DynaActionForm)request.getSession().getAttribute("frmFloatTrans");
			log.info("Transaction SeqID : "+(String)frmFloatTrans.get("transSeqID"));
			log.info("Debit Note SeqID : " +debitNoteDepositVO.getDebitNoteSeqID());
			log.info("DebitNote TransSeqID : "+debitNoteDepositVO.getDebitNoteTransSeqID());
			ArrayList<Object> alClaimsList=floatAccountManagerObject.getClaimDepositList(debitNoteDepositVO.getDebitNoteSeqID(),TTKCommon.getLong((String)frmFloatTrans.get("transSeqID")));
			if(alClaimsList!= null && alClaimsList.size()>0)
            {
				frmClaimsDeposit.set("claimsdetails",(ClaimDepositVO[])alClaimsList.toArray(new ClaimDepositVO[0]));
            }//end of if(alClaimsList!= null && alClaimsList.size()>0)
			strCaption.append("Claims Deposit Details - ").
			append(" [").append(debitNoteDepositVO.getDebitNoteNbr()).append("]");
			frmClaimsDeposit.set("caption",String.valueOf(strCaption));
			frmClaimsDeposit.set("transAmt",TTKCommon.checkNull(debitNoteDepositVO.getDepositedAmt().toString()));
			request.setAttribute("frmClaimsDeposit",frmClaimsDeposit);
			return this.getForward(strClaimsDeposit, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strClaims));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside ClaimsDepositAction doSave");
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		DynaActionForm frmClaimsDeposit = (DynaActionForm)form;
    		StringBuffer strCaption= new StringBuffer();
    		ArrayList<Object> alClaimsDeposit=new ArrayList<Object>();
    		ClaimDepositVO claimDepositVO=null;
    		DebitNoteDepositVO debitNoteDepositVO =(DebitNoteDepositVO)request.getSession().getAttribute("SelectedDebitNote");
    		DynaActionForm frmFloatTrans=(DynaActionForm)request.getSession().getAttribute("frmFloatTrans");
    		String[] strClaimSeqID=request.getParameterValues("claimSeqID");
            String[] strCurrentAmt=request.getParameterValues("currentAmt");
            String[] strTransTypeID=request.getParameterValues("transTypeID");

            if(strClaimSeqID!=null && strClaimSeqID.length >0)
            {
            	//loop through the no of members and prepare the ArrayList of premiuminfoVO's
                for(int iCount=0;iCount<strClaimSeqID.length;iCount++)
                {
                	if(!(strCurrentAmt[iCount].equals("")))
                	{
                	log.info("Current amounts length"+strCurrentAmt.length);
                	claimDepositVO=new ClaimDepositVO();
                	claimDepositVO.setClaimSeqID(TTKCommon.checkNull(strClaimSeqID[iCount]).equals("")?
                			null:new Long(strClaimSeqID[iCount]));
                	log.info("Claim seqid"+strClaimSeqID[iCount]);
                	claimDepositVO.setCurrentAmt(TTKCommon.getBigDecimal(strCurrentAmt[iCount]));
                	log.info("Current Amount"+TTKCommon.getBigDecimal(strCurrentAmt[iCount]));
                	claimDepositVO.setTransTypeID(TTKCommon.checkNull(strTransTypeID[iCount]));
                	log.info("Trans Type ID"+TTKCommon.checkNull(strTransTypeID[iCount]));
                	claimDepositVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
                	log.info("User Id"+TTKCommon.getUserSeqId(request));
                	alClaimsDeposit.add(claimDepositVO);
                	}
                }//end of for
            }//end of if
            if(alClaimsDeposit!=null && alClaimsDeposit.size()>0)
            {
            	frmClaimsDeposit.set("claimsdetails",(ClaimDepositVO[])alClaimsDeposit.toArray(new ClaimDepositVO[0]));
            	//call the Dao to update the premium Info
                int iCount=floatAccountManagerObject.saveClaimDepositDetail(alClaimsDeposit,debitNoteDepositVO.getDebitNoteTransSeqID());

                if(iCount>0)    //if record is updated then requery
                {
                	frmClaimsDeposit.initialize(mapping);
                	frmClaimsDeposit.set("transAmt",debitNoteDepositVO.getDepositedAmt().toString());
                	strCaption.append("Claims Deposit Details - ").
        			append(" [").append(debitNoteDepositVO.getDebitNoteNbr()).append("]");
                	frmClaimsDeposit.set("caption",String.valueOf(strCaption));
        			claimDepositVO=null;
        			ArrayList<Object> alClaimsList=floatAccountManagerObject.getClaimDepositList(debitNoteDepositVO.getDebitNoteSeqID(),TTKCommon.getLong((String)frmFloatTrans.get("transSeqID")));
        			if(alClaimsList!= null && alClaimsList.size()>0)
                    {
        				frmClaimsDeposit.set("claimsdetails",(ClaimDepositVO[])alClaimsList.toArray(new ClaimDepositVO[0]));
                    }
        			frmClaimsDeposit.set("totCurAmt",null);
        			request.setAttribute("updated","message.savedSuccessfully");
                }//end of if(iCount>0)
            }//end of if(alDebitDeposit!=null && alDebitDeposit.size()>0)
            request.setAttribute("frmClaimsDeposit",frmClaimsDeposit);
            return this.getForward(strClaimsDeposit, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strClaims));
    	}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to get reset the form
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
    	log.debug("Inside the doReset of ClaimsDepositAction");
        return doAssociateClaims(mapping,form,request,response);
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside ClaimsDepositAction doClose");
    		request.getSession().removeAttribute("SelectedDebitNote");
    		return this.getForward(strDebitNote, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strClaims));
    	}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
	 * Returns the FloatAccountManager session object for invoking methods on it.
	 * @return FloatAccountManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private FloatAccountManager getfloatAccountManagerObject() throws TTKException
	{
		FloatAccountManager floatAccountManager = null;
		try
		{
			if(floatAccountManager == null)
			{
				InitialContext ctx = new InitialContext();
				floatAccountManager = (FloatAccountManager) ctx.lookup("java:global/TTKServices/business.ejb3/FloatAccountManagerBean!com.ttk.business.finance.FloatAccountManager");
			}//end of if(contactManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "floataccount");
		}//end of catch
		return floatAccountManager;
	}//end getfloatAccountManagerObject()
}
