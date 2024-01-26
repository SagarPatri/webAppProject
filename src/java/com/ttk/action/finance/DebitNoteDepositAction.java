/**
 * @ (#)  DebitNoteDepositAction.java Sep 10, 2007
 * Project      : TTK HealthCare Services
 * File         : DebitNoteDepositAction.java
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : Sep 10, 2007
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
import com.ttk.dto.finance.DebitNoteDepositVO;

public class DebitNoteDepositAction extends TTKAction{
	
	private static final Logger log = Logger.getLogger( DebitNoteDepositAction.class );
	
	private static final String strDebitNote="debitnote";
	
	private static final String strDebit="debitdeposit";
	private static final String strTransDetails="transdetails";
	//private static final String strClaimsDeposit="claimsdeposit";
	
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
	public ActionForward doViewDebitNotes(ActionMapping mapping,ActionForm form,HttpServletRequest request,
								   HttpServletResponse response) throws Exception{
		try{
			setLinks(request);
			log.debug("Inside DebitNoteDepositAction doAssociateDebitNote");
			StringBuffer strCaption= new StringBuffer();
			FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
			DynaActionForm frmDebitNote = (DynaActionForm)form;
			DynaActionForm frmFloatTrans=(DynaActionForm)request.getSession().getAttribute("frmFloatTrans");
			Long lngTransSeqID =TTKCommon.getLong((String)frmFloatTrans.get("transSeqID"));
			log.info("Transaction SeqID "+lngTransSeqID.toString());
			ArrayList<Object> alDebitList=floatAccountManagerObject.getDebitNoteDepositList(TTKCommon.getWebBoardId(request),TTKCommon.getLong((String)frmFloatTrans.get("transSeqID")));
			if(alDebitList!= null && alDebitList.size()>0)
            {
                frmDebitNote.set("debitnote",(DebitNoteDepositVO[])alDebitList.toArray(new DebitNoteDepositVO[0]));
            }//end of if(alDebitList!= null && alDebitList.size()>0)
			strCaption.append("Transaction Details  - ").append(" [").append(TTKCommon.getWebBoardDesc(request)).append("]").
			append(" [").append((String)frmFloatTrans.get("transNbr")).append("]");
			frmDebitNote.set("caption",String.valueOf(strCaption));
			frmDebitNote.set("transAmt",(String)frmFloatTrans.get("transAmt"));
			frmDebitNote.set("floatTransSeqID",(String)frmFloatTrans.get("transSeqID"));
			request.getSession().setAttribute("frmDebitNote",frmDebitNote);
			return this.getForward(strDebitNote, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strDebit));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside DebitNoteDepositAction doClose");
    		request.getSession().removeAttribute("frmDebitNote");
    		return this.getForward(strTransDetails, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strDebit));
    	}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
     * This method is used to forward to the Claims Deposit Screen on click of the Icon.
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
    		log.debug("Inside DebitNoteDepositAction doAssociateClaims");
    		DynaActionForm frmDebitNote = (DynaActionForm)form;
    		int index=Integer.parseInt((String)frmDebitNote.get("rownum"));
    		DebitNoteDepositVO debitNoteDepositVO[]=(DebitNoteDepositVO[])frmDebitNote.get("debitnote");
    		DebitNoteDepositVO debitNoteSelectedVO=(DebitNoteDepositVO)debitNoteDepositVO[index];
    		log.info("Index value :"+index);
    		log.info("DebitNoteSeqID :"+debitNoteSelectedVO.getDebitNoteSeqID());
    		log.info("Debit Note Trans SeqID :"+debitNoteSelectedVO.getDebitNoteTransSeqID());
    		request.getSession().setAttribute("SelectedDebitNote",debitNoteSelectedVO);
    		return mapping.findForward("claimsdeposit");
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strDebit));
    	}//end of catch(Exception exp)
    }//end of doAssociateClaims(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug("Inside DebitNoteDepositAction doSave");
    		FloatAccountManager floatAccountManagerObject=this.getfloatAccountManagerObject();
    		DynaActionForm frmDebitNote = (DynaActionForm)form;
    		StringBuffer strCaption= new StringBuffer();
    		ArrayList<Object> alDebitDeposit=new ArrayList<Object>();
    		DebitNoteDepositVO debitNoteDepositVO=null;
    		
    		DynaActionForm frmFloatTrans=(DynaActionForm)request.getSession().getAttribute("frmFloatTrans");
    		String[] strDebitSeqID=request.getParameterValues("debitNoteSeqID");
    		log.info("strDebitSeqIDs length "+ strDebitSeqID.length);
            String[] strCurrentAmt=request.getParameterValues("currentAmt");
            String[] strTransactionTypeID=request.getParameterValues("transTypeID");
            if(strDebitSeqID!=null && strDebitSeqID.length>0)
            {
            	//loop througth the no of members and prepare the ArrayList of premiuminfoVO's
                for(int iCount=0;iCount<strDebitSeqID.length;iCount++)
                {
                	debitNoteDepositVO=new DebitNoteDepositVO();
                	debitNoteDepositVO.setDebitNoteSeqID(TTKCommon.checkNull(strDebitSeqID[iCount]).equals("")?
                			null:new Long(strDebitSeqID[iCount]));	
                	debitNoteDepositVO.setTransTypeID(strTransactionTypeID[iCount]);
                	debitNoteDepositVO.setCurrentAmt(TTKCommon.getBigDecimal(strCurrentAmt[iCount]));
                	debitNoteDepositVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
                	alDebitDeposit.add(debitNoteDepositVO);
                }//end of for(int iCount=0;iCount<strDebitSeqID.length;iCount++)
               
            }//end of if
            if(alDebitDeposit!=null && alDebitDeposit.size()>0)
            {
            	//frmDebitNote.set("debitnote",(DebitNoteDepositVO[])alDebitDeposit.toArray(new DebitNoteDepositVO[0]));
            	//call the Dao to update the premium Info
                int iCount=floatAccountManagerObject.saveDebitNoteDepositDetail(alDebitDeposit,TTKCommon.getLong((String)frmFloatTrans.get("transSeqID")));
                
                if(iCount>0)    //if record is updated then requery
                {
                	frmDebitNote.initialize(mapping);
                	frmDebitNote.set("transAmt",(String)frmFloatTrans.get("transAmt"));
                	frmDebitNote.set("floatTransSeqID",(String)frmFloatTrans.get("transSeqID"));
                	strCaption.append("Transaction Details  - ").append(" [").append(TTKCommon.getWebBoardDesc(request)).append("]").
        			append(" [").append((String)frmFloatTrans.get("transNbr")).append("]");
        			frmDebitNote.set("caption",String.valueOf(strCaption));
        			ArrayList<Object> alDebitList =new ArrayList<Object>();
        			debitNoteDepositVO=null;
        			alDebitList=floatAccountManagerObject.getDebitNoteDepositList(TTKCommon.getWebBoardId(request),TTKCommon.getLong((String)frmFloatTrans.get("transSeqID")));
        			if(alDebitList!= null && alDebitList.size()>0)
                    {
                        frmDebitNote.set("debitnote",(DebitNoteDepositVO[])alDebitList.toArray(new DebitNoteDepositVO[0]));
                    }//end of if(alDebitList!= null && alDebitList.size()>0)
        			frmDebitNote.set("totCurAmt",null);
        			request.setAttribute("updated","message.savedSuccessfully");
                }//end of if(iCount>0)
            }//end of if(alDebitDeposit!=null && alDebitDeposit.size()>0)
            request.getSession().setAttribute("frmDebitNote",frmDebitNote);
            return this.getForward(strDebitNote, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strDebit));
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
    	log.debug("Inside the doReset of DebitNoteDepositAction");
        return doViewDebitNotes(mapping,form,request,response);
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
    /**
	 * Returns the ContactManager session object for invoking methods on it.
	 * @return ContactManager session object which can be used for method invokation
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
}//end of DebitNoteDepositAction
