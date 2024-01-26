/**
 * @ (#) LineItemsAction.java July 20,2006
 * Project       : TTK HealthCare Services
 * File          : LineItemsAction.java
 * Author        : Harsha Vardhan B N
 * Company       : Span Systems Corporation
 * Date Created  : July 20,2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.dataentryclaims;

import java.util.ArrayList;
import java.util.ResourceBundle;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import com.ttk.action.TTKAction;
import com.ttk.business.dataentryclaims.ParallelClaimBillManager;
import com.ttk.business.dataentryclaims.ParallelClaimManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.claims.ClaimBillDetailVO;
import com.ttk.dto.claims.LineItemVO;
import formdef.plugin.util.FormUtils;

/**
 * This class is used for adding line item
 * This class also provides option for deletion of line item.
 */

public class LineItemsAction extends TTKAction {
	private static Logger log = Logger.getLogger( LineItemsAction.class );

	//  Action mapping forwards.
	private static final  String strLineItems="dataentrylineitems";
	private static final  String strBillsList="dataentrybillslist";
	
	

	//Exception Message Identifier
	private static final String strLineItemsError="dataentrylineitems";

	/**
	 * This method is used to search the data with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSearch method of DataEntryLineItemsAction");
			setLinks(request);
			ParallelClaimBillManager claimBillManagerObject=this.getClaimBillManagerObject();
			ClaimBillDetailVO claimBillDetailVO=new ClaimBillDetailVO();
			if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
            {
                TTKException expTTK = new TTKException();
                expTTK.setMessage("error.Claims.required");
                throw expTTK;
            }//end of if(ClaimsWebBoardHelper.checkWebBoardId(request)==null)
			/*if(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y"))
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.Claims.codingflow");
				throw expTTK;
			}//end of if(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y"))
		*/	DynaActionForm frmLineItems=(DynaActionForm)form;
			frmLineItems.initialize(mapping);			
			ArrayList alBills=claimBillManagerObject.getBillLineitemList(ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
			
			if(alBills!=null)
			{
				String BillCompleteYN = "";    		
				for(int i=0;i<alBills.size();i++)
				{
					claimBillDetailVO = (ClaimBillDetailVO)alBills.get(i);
					BillCompleteYN = claimBillDetailVO.getBillsCompleteYN();   
    			
				}
				frmLineItems = (DynaActionForm)FormUtils.setFormValues("frmLineItems",claimBillDetailVO,this,mapping,request);
	    		request.setAttribute("ShowPromoteYN","Y");
			}
			else
			{
				request.setAttribute("ShowPromoteYN","N");
			}
    		request.getSession().setAttribute("frmLineItems",frmLineItems);
			//Forward to bill entry screen
			return (mapping.findForward(strLineItems));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strLineItemsError));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doView method of DataEntryLineItemsAction");
			setLinks(request);
			ParallelClaimBillManager claimBillManagerObject=this.getClaimBillManagerObject();
			response.setContentType("text/xml");
			DynaActionForm frmLineItems=(DynaActionForm)form;
			frmLineItems.set("ammendmentYN",ClaimsWebBoardHelper.getAmmendmentYN(request));
	        ArrayList alBills=claimBillManagerObject.getBillLineitemList(ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
			String xml=getXML(alBills,ClaimsWebBoardHelper.getAmmendmentYN(request),"","");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			request.getSession().setAttribute("frmLineItems",frmLineItems);
			out.println(xml);
			out.flush();
			return null;
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strLineItemsError));
		}//end of catch(Exception exp)
	}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    	log.debug("Inside doChangeWebBoard method of DataEntryLineItemsAction");
    	//ChangeWebBoard method will call doView() method internally.
    	return doSearch(mapping,form,request,response);
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
    
    
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
			log.debug("Inside the doSave method of DataEntryLineItemsAction");
			setLinks(request);
			ClaimBillDetailVO claimBillDetailVO=new ClaimBillDetailVO();
    		ParallelClaimBillManager billManagerObject=this.getClaimBillManagerObject();
    		DynaActionForm frmBillDetail = (DynaActionForm)form;
    		String strXML="";
    		ArrayList<Object> alBills=null;
    		ParallelClaimBillManager claimBillManagerObject=this.getClaimBillManagerObject();

    		if(request.getParameter("flag").equals("bill"))
    		{
	    		claimBillDetailVO =(ClaimBillDetailVO)FormUtils.getFormValues(frmBillDetail, this, mapping, request);
	            claimBillDetailVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
	            claimBillDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));

	            log.debug("---BillSeqID ---"+claimBillDetailVO.getBillSeqID());
	            log.debug("---ClaimSeqID----"+claimBillDetailVO.getClaimSeqID());
	            log.debug("---BillNbr------"+claimBillDetailVO.getBillNbr());
	            log.debug("---BillDate-----"+claimBillDetailVO.getBillDate());
	            log.debug("---BillIssuedBy--"+claimBillDetailVO.getBillIssuedBy());
	            log.debug("---BillWithPrescription--"+claimBillDetailVO.getBillWithPrescription());
	            log.debug("---BillIncludedYN--"+claimBillDetailVO.getBillIncludedYN());
	            log.debug("---UpdatedBy----"+claimBillDetailVO.getUpdatedBy());

	            //call the DAO to save the Bill Details
	            long lngCnt=billManagerObject.saveBillDetail(claimBillDetailVO);
	            log.debug("---lngCnt----"+lngCnt);
	            claimBillDetailVO=claimBillManagerObject.getBillDetail(lngCnt,TTKCommon.getUserSeqId(request));
	            alBills=new ArrayList<Object>();
	            alBills.add(claimBillDetailVO);
	            strXML=getXML(alBills,ClaimsWebBoardHelper.getAmmendmentYN(request),"billsave","");
    		}//end of if(request.getParameter("flag").equals("bill"))
    		if(request.getParameter("flag").equals("item"))
    		{

    			LineItemVO lineItemVO=new LineItemVO();
    			lineItemVO.setBillSeqID(TTKCommon.getLong((String)request.getParameter("billSeqID")));
    			lineItemVO.setLineItemSeqID(TTKCommon.getLong((String)request.getParameter("lineItemSeqID")));
    			lineItemVO.setLineItemNbr(request.getParameter("lineItemNbr"));
    			lineItemVO.setAccountHeadTypeID(request.getParameter("accountHeadTypeID"));
    			String strAccountHeadTypeID=lineItemVO.getAccountHeadTypeID();
    			log.debug("--- strAccountHeadTypeID --"+strAccountHeadTypeID);

    			String str[]=strAccountHeadTypeID.split("#");


    			if(str[1].equals("Y"))
    			{
    				lineItemVO.setRoomTypeID(request.getParameter("roomTypeID"));
    				lineItemVO.setNbrofDays(TTKCommon.getInt(request.getParameter("nbrofDays")));
    			}//end of if(str[1].equals("Y"))
    			if(str[0].equals("VCE"))//added for maternity
    			{
    				lineItemVO.setvaccineTypeID(request.getParameter("vaccineTypeID"));
    				log.info("vaccineTypeID:::"+request.getParameter("vaccineTypeID"));
    			}//end of if(str[1].equals("Y"))

    			lineItemVO.setRequestedAmt(TTKCommon.getBigDecimal(request.getParameter("requestedAmt")));
    			lineItemVO.setDisAllowedAmt(TTKCommon.getBigDecimal(request.getParameter("disAllowedAmt")));
    			lineItemVO.setAllowedAmt(TTKCommon.getBigDecimal(request.getParameter("allowedAmt")));
    			lineItemVO.setRemarks(request.getParameter("remarks"));
    			lineItemVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
    			if(strAccountHeadTypeID.substring(0,strAccountHeadTypeID.indexOf("#")).equals("Y"))
    			{
    				log.debug("---RoomTypeID----"+lineItemVO.getRoomTypeID());
    				log.debug("---NbrofDays----"+lineItemVO.getNbrofDays());
    			}//end of if
    			long lngCnt=billManagerObject.saveLineItemDetail(lineItemVO);
    			lineItemVO=claimBillManagerObject.getLineItem(lngCnt);
    			ArrayList<Object> alLineItem=new ArrayList<Object>();
    			alLineItem.add(lineItemVO);
    			claimBillDetailVO.setBillSeqID(lineItemVO.getBillSeqID());
    			claimBillDetailVO.setLineItemList(alLineItem);
    			alBills=new ArrayList<Object>();
	            alBills.add(claimBillDetailVO);
	            strXML=getXML(alBills,ClaimsWebBoardHelper.getAmmendmentYN(request),"lineitemsave","");
    		}//end of if(request.getParameter("flag").equals("item"))
    		 
            response.setContentType("text/xml");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.println(strXML);
			out.flush();
		}//end of try
		catch(TTKException expTTK)
		{
			processException(request,response,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			processException(request,response,new TTKException(exp,strLineItemsError));
		}//end of catch(Exception exp)
		return null;
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doSaveNext(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		try{
		log.info("Inside the doSaveNext method of LineItemsAction");
		setLinks(request);
		ClaimBillDetailVO claimBillDetailVO=new ClaimBillDetailVO();
		ParallelClaimBillManager billManagerObject=this.getClaimBillManagerObject();
		DynaActionForm frmBillDetail = (DynaActionForm)form;
		String strXML="";
		ArrayList<Object> alBills=null;
		
		ParallelClaimBillManager claimBillManagerObject=this.getClaimBillManagerObject();
		
		if(request.getParameter("flag").equals("bill"))
		{
		claimBillDetailVO =(ClaimBillDetailVO)FormUtils.getFormValues(frmBillDetail, this, mapping, request);
		claimBillDetailVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
		claimBillDetailVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
		
		log.debug("---BillSeqID ---"+claimBillDetailVO.getBillSeqID());
		log.debug("---ClaimSeqID----"+claimBillDetailVO.getClaimSeqID());
		log.debug("---BillNbr------"+claimBillDetailVO.getBillNbr());
		log.debug("---BillDate-----"+claimBillDetailVO.getBillDate());
		log.debug("---BillIssuedBy--"+claimBillDetailVO.getBillIssuedBy());
		log.debug("---BillWithPrescription--"+claimBillDetailVO.getBillWithPrescription());
		log.debug("---BillIncludedYN--"+claimBillDetailVO.getBillIncludedYN());
		log.debug("---UpdatedBy----"+claimBillDetailVO.getUpdatedBy());
		
		//call the DAO to save the Bill Details
		long lngCnt=billManagerObject.saveBillDetail(claimBillDetailVO);
		
		log.debug("---lngCnt----"+lngCnt);
		claimBillDetailVO=claimBillManagerObject.getBillDetail(lngCnt,TTKCommon.getUserSeqId(request));
		alBills=new ArrayList<Object>();
		alBills.add(claimBillDetailVO);
		strXML=getXML(alBills,ClaimsWebBoardHelper.getAmmendmentYN(request),"billsave","");
		}//end of if(request.getParameter("flag").equals("bill"))		
		
		if(request.getParameter("flag").equals("item"))
		{
		
		LineItemVO lineItemVO=new LineItemVO();
		LineItemVO lineItemVO1 = new LineItemVO();
		//added for KOC-1356
		lineItemVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
		lineItemVO.setBillSeqID(TTKCommon.getLong((String)request.getParameter("billSeqID")));
		lineItemVO.setLineItemSeqID(TTKCommon.getLong((String)request.getParameter("lineItemSeqID")));
		lineItemVO.setLineItemNbr(request.getParameter("lineItemNbr"));
		lineItemVO.setAccountHeadTypeID(request.getParameter("accountHeadTypeID"));
		String strAccountHeadTypeID=lineItemVO.getAccountHeadTypeID();
		log.debug("--- strAccountHeadTypeID --"+strAccountHeadTypeID);
		
		String str[]=strAccountHeadTypeID.split("#");
		
		
		if(str[1].equals("Y"))
		{
		lineItemVO.setRoomTypeID(request.getParameter("roomTypeID"));
		lineItemVO.setNbrofDays(TTKCommon.getInt(request.getParameter("nbrofDays")));
		}//end of if(str[1].equals("Y"))
		if(str[0].equals("VCE"))//added for maternity
		{
		lineItemVO.setvaccineTypeID(request.getParameter("vaccineTypeID"));
		log.info("vaccineTypeID:::"+request.getParameter("vaccineTypeID"));
		}//end of if(str[1].equals("Y"))
		
		lineItemVO.setRequestedAmt(TTKCommon.getBigDecimal(request.getParameter("requestedAmt")));
		lineItemVO.setDisAllowedAmt(TTKCommon.getBigDecimal(request.getParameter("disAllowedAmt")));
		lineItemVO.setAllowedAmt(TTKCommon.getBigDecimal(request.getParameter("allowedAmt")));
		lineItemVO.setRemarks(request.getParameter("remarks"));
		lineItemVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
		if(strAccountHeadTypeID.substring(0,strAccountHeadTypeID.indexOf("#")).equals("Y"))
		{
		log.debug("---RoomTypeID----"+lineItemVO.getRoomTypeID());
		log.debug("---NbrofDays----"+lineItemVO.getNbrofDays());
		}//end of if
				
		lineItemVO = billManagerObject.saveLineItemDetailNext(lineItemVO);
		
		log.info("CLM_BILL_SEQ_ID--:"+lineItemVO.getBillSeqID());
		log.info("Current_BILL_DTL_SEQ_ID--:"+lineItemVO.getLineItemSeqID());
		log.info("NextCLM_BILL_DTL_SEQ_ID--:"+lineItemVO.getNextLineItemSeqId());
		//log.info("DESCRIPTION---:"+lineItemVO1.getLineItemNbr());
		//log.info("REQUESTED_AMOUNT--:"+lineItemVO1.getRequestedAmt());
		//log.info("AccYN--:"+lineItemVO.getAccHeadYN());
		//log.info("Nextbillno--:"+lineItemVO.getNextbillNo());
		
		
		/*request.getSession().setAttribute("nextBillDetailSeqId",lineItemVO.getNextLineItemSeqId());
		request.getSession().setAttribute("Nextbillno",lineItemVO.getNextbillNo());
		request.getSession().setAttribute("AccHeadYN",lineItemVO.getAccHeadYN());*/
		if(lineItemVO.getNextLineItemSeqId() != null)
		{
		claimBillDetailVO.setNextbillSeqId(lineItemVO.getNextLineItemSeqId().toString());
		}
		claimBillDetailVO.setNextbillNo(lineItemVO.getNextbillNo());
		//claimBillDetailVO.setBillCompleteYN(lineItemVO.getAccHeadYN());
	
				
		lineItemVO=claimBillManagerObject.getLineItem(lineItemVO.getLineItemSeqID());
		ArrayList<Object> alLineItem=new ArrayList<Object>();
		alLineItem.add(lineItemVO);
		claimBillDetailVO.setBillSeqID(lineItemVO.getBillSeqID());
		claimBillDetailVO.setLineItemList(alLineItem);
		
		alBills=new ArrayList<Object>();
		alBills.add(claimBillDetailVO);
		strXML=getXML(alBills,ClaimsWebBoardHelper.getAmmendmentYN(request),"lineitemsave","");
		}//end of if(request.getParameter("flag").equals("item"))
	
		response.setContentType("text/xml");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.println(strXML);
		out.flush();
		}//end of try
		catch(TTKException expTTK)
		{
		processException(request,response,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
		processException(request,response,new TTKException(exp,strLineItemsError));
		}//end of catch(Exception exp)
		return null;
}//end of doSaveNext(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

/**
	 * This method is used to update the Promote status.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDataEntryPromote(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																		HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSave method of doDataEntryPromote");
			setLinks(request);
			ClaimBillDetailVO claimBillDetailVO=new ClaimBillDetailVO();
    		ParallelClaimBillManager billManagerObject=this.getClaimBillManagerObject();
    		DynaActionForm frmBillDetail = (DynaActionForm)form;
    		String strXML="";
    		ArrayList<Object> alBills=null;
    		ParallelClaimBillManager claimBillManagerObject=this.getClaimBillManagerObject();
    		
    		claimBillDetailVO =(ClaimBillDetailVO)FormUtils.getFormValues(frmBillDetail, this, mapping, request);
    		 log.info("---BillSeqID ---"+claimBillDetailVO.getBillSeqID());
	            log.info("---ClaimSeqID----"+claimBillDetailVO.getClaimSeqID());
	            log.info("---BillNbr------"+claimBillDetailVO.getBillNbr());
	            log.info("---BillDate-----"+claimBillDetailVO.getBillDate());
	            log.info("---BillIssuedBy--"+claimBillDetailVO.getBillIssuedBy());
	            log.info("---BillWithPrescription--"+claimBillDetailVO.getBillWithPrescription());
	            log.info("---BillIncludedYN--"+claimBillDetailVO.getBillIncludedYN());
	            log.info("---UpdatedBy----"+claimBillDetailVO.getUpdatedBy());
    		
    		claimBillDetailVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
    		log.info("---ClaimSeqID-- 23--"+claimBillDetailVO.getClaimSeqID());
    		
    		    		
    		long iResult = billManagerObject.saveDataEntryBillsPromote(claimBillDetailVO);
    		
    		
    		alBills=claimBillManagerObject.getBillLineitemList(ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
    		
    		
    		String BillCompleteYN = "";
    		
    		if(alBills!=null)
    		{
    			for(int i=0;i<alBills.size();i++)
        		{
        			claimBillDetailVO = (ClaimBillDetailVO)alBills.get(i);
        			BillCompleteYN = claimBillDetailVO.getBillsCompleteYN();   
        			
        		}
    		}    		
    		frmBillDetail = (DynaActionForm)FormUtils.setFormValues("frmLineItems",claimBillDetailVO,this,mapping,request);
    		request.getSession().setAttribute("frmLineItems",frmBillDetail);
    					
		}//end of try processException(request,response,expTTK);
		catch(TTKException expTTK)
		{			
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			processException(request,response,new TTKException(exp,strLineItemsError));
		}//end of catch(Exception exp)
		return mapping.findForward(strLineItems);
	}//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * This method is used to update the Revert status.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDataEntryRevert(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																		HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doSave method of doDataEntryRevert");
			setLinks(request);
			ClaimBillDetailVO claimBillDetailVO=new ClaimBillDetailVO();
    		ParallelClaimBillManager billManagerObject=this.getClaimBillManagerObject();
    		DynaActionForm frmBillDetail = (DynaActionForm)form;
    		String strXML="";
    		ArrayList<Object> alBills=null;
    		ParallelClaimBillManager claimBillManagerObject=this.getClaimBillManagerObject();
    		
    		claimBillDetailVO =(ClaimBillDetailVO)FormUtils.getFormValues(frmBillDetail, this, mapping, request);
    		
    		claimBillDetailVO.setClaimSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
    		
    		long iResult = billManagerObject.saveDataEntryBillsRevert(claimBillDetailVO);
    		
    		alBills=claimBillManagerObject.getBillLineitemList(ClaimsWebBoardHelper.getClaimsSeqId(request),TTKCommon.getUserSeqId(request));
    		String BillCompleteYN = "";
    		if(alBills!=null)
    		{
    			for(int i=0;i<alBills.size();i++)
        		{
        			claimBillDetailVO = (ClaimBillDetailVO)alBills.get(i);
        			BillCompleteYN = claimBillDetailVO.getBillsCompleteYN();   
        			
        		}
    		}
    		
    		frmBillDetail = (DynaActionForm)FormUtils.setFormValues("frmLineItems",claimBillDetailVO,this,mapping,request);
    		request.setAttribute("ShowPromoteYN","Y");
    		request.getSession().setAttribute("frmLineItems",frmBillDetail);
    					
		}//end of try processException(request,response,expTTK);
		catch(TTKException expTTK)
		{			
			return this.processExceptions(request, mapping, expTTK);			
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			processException(request,response,new TTKException(exp,strLineItemsError));
		}//end of catch(Exception exp)
		return mapping.findForward(strLineItems);
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
			log.debug("Inside the doClose method of DataEntryLineItemsAction");
			setLinks(request);
			return mapping.findForward(strBillsList);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strLineItemsError));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to delete the selected record(s) in Search Grid.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
																	HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doDeleteList method of DataEntryLineItemsAction");
			setLinks(request);
			ParallelClaimManager claimManagerObject=this.getClaimManagerObject();
			StringBuffer sbfDeleteId = new StringBuffer();
			ArrayList <Object>alLineItemDelete=new ArrayList<Object>();
			String strDeletionFlag=request.getParameter("deletionFlag");
			String strBillId=request.getParameter("billId");

			String delId=request.getParameter("id");
			sbfDeleteId.append("|").append(delId).append("|");
			if(request.getParameter("deletionFlag").equalsIgnoreCase("bill"))
			{
				alLineItemDelete.add("BILL");
			}//end of if(request.getParameter("bill").equalsIgnoreCase("bill"))
			else
			{
				alLineItemDelete.add("LINEITEM");
			}//end else if(request.getParameter("bill").equalsIgnoreCase("bill"))

			alLineItemDelete.add(String.valueOf(sbfDeleteId));
			alLineItemDelete.add(ClaimsWebBoardHelper.getClmEnrollDetailSeqId(request));
			alLineItemDelete.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
			alLineItemDelete.add(TTKCommon.getUserSeqId(request));
			int iCount=claimManagerObject.deleteClaimGeneral(alLineItemDelete);
			log.debug("iCount value is :"+iCount);
			response.setContentType("text/xml");
			String strXML ="";
			strXML= "<?xml version='1.0'?>";
			strXML += "<billdetails deleteFlag='"+strDeletionFlag+"'>";
			if(strDeletionFlag.equals("bill"))
			{
				strXML += "<bill id='"+delId+"'>";
				strXML += "</bill>";
			}//end of if(strDeletionFlag.equals("bill"))
			if(strDeletionFlag.equals("item"))
			{
				strXML += "<bill id='"+strBillId+"'>";
				strXML += "<lineitem billid='"+strBillId+"' id='"+delId+"'/>";
				strXML += "</bill>";
			}//end of if(strDeletionFlag.equals("item"))
			strXML += "</billdetails>";
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();

			out.println(strXML);
			out.flush();
			
		}//end of try
		catch(TTKException expTTK)
		{
			processException(request,response,expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			processException(request,response,new TTKException(exp,strLineItemsError));
		}//end of catch(Exception exp)
		return null;
	}//end of doDeleteList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)
	
	/**
	 * Returns the ParallelClaimManager session object for invoking methods on it.
	 * @return ParallelClaimManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private ParallelClaimManager getClaimManagerObject() throws TTKException
	{
		ParallelClaimManager claimManager = null;
		try
		{
			if(claimManager == null)
			{
				InitialContext ctx = new InitialContext();
				claimManager = (ParallelClaimManager) ctx.lookup("java:global/TTKServices/business.ejb3/ParallelClaimManagerBean!com.ttk.business.dataentryclaims.ParallelClaimManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strLineItems);
		}//end of catch
		return claimManager;
	}//end of getClaimManagerObject()
	
	/**
	 * Returns the ParallelClaimBillManager session object for invoking methods on it.
	 * @return ParallelClaimBillManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private String getXML(ArrayList alBills,String strAmmendament,String strMergFlag,String strErrorDesc) throws TTKException
	{
		ClaimBillDetailVO claimBillDetailVO=null;
		String xml = "<?xml version='1.0'?>";
		xml += "<billdetails ammendament='"+strAmmendament+"' mergflag='"+strMergFlag+"'>";		
        if(!TTKCommon.checkNull(strErrorDesc).equals(""))
        {
        	xml +="<error>"+ strErrorDesc+"</error>";
        }//end of if(!TTKCommon.checkNull(strErrorDesc).equals(""))
		BigDecimal bdReqAmount=new BigDecimal(0);
		BigDecimal bdallowAmount=new BigDecimal(0);
		BigDecimal bdrejAmount=new BigDecimal(0);
		LineItemVO lineItemVO=null;
		if(alBills!=null)
		{

			for(int i=0;i<alBills.size();i++)
			{
				claimBillDetailVO=(ClaimBillDetailVO)alBills.get(i);
				String NextBillSeqId = claimBillDetailVO.getNextbillSeqId();
				String NextBillNo = claimBillDetailVO.getNextbillNo();
				String BillCompleteYN = claimBillDetailVO.getBillCompleteYN();
				
				xml += "<bill id='"+claimBillDetailVO.getBillSeqID()+"' billno='"+claimBillDetailVO.getBillNbr()+"' billdate='"+TTKCommon.getFormattedDate(claimBillDetailVO.getBillDate())+"' billissued='"+TTKCommon.getHtmlString(claimBillDetailVO.getBillIssuedBy())+"' rx='"+claimBillDetailVO.getBillWithPrescription()+"' type='"+claimBillDetailVO.getBillTypeDesc()+"' include='"+claimBillDetailVO.getBillIncludedYN()+"' donorExpYN='"+claimBillDetailVO.getDonorExpYN()+"' completeYN='"+claimBillDetailVO.getCompletedYN()+"' nextbill='"+NextBillSeqId+"' nextbillno='"+NextBillNo+"' nextcompleteYN='"+BillCompleteYN+"'>";//changes for donor expenses
				ArrayList alLineItems=claimBillDetailVO.getLineItemList();
				if(alLineItems!=null&&alLineItems.size()>0)
				{

					for(int j=0;j<alLineItems.size();j++)
					{
						lineItemVO=(LineItemVO)alLineItems.get(j);
						String strAccountHead=lineItemVO.getAccountHeadTypeID();
						String strDisplayFlag[]=strAccountHead.split("#");
						
						xml += "<lineitem billid='"+claimBillDetailVO.getBillSeqID() +"' id='"+lineItemVO.getLineItemSeqID()+"' description='"+lineItemVO.getLineItemNbr()+"' accountHeadTypeID='"+lineItemVO.getAccountHeadTypeID()+"' display='"+strDisplayFlag[1]+"' acchead='"+lineItemVO.getAccountHeadDesc()+"' roomTypeId='"+lineItemVO.getRoomTypeID()+"' vaccineTypeId='"+lineItemVO.getvaccineTypeID()+"' roomdescription='"+lineItemVO.getRoomTypeDesc()+"' days='"+lineItemVO.getNbrofDays()+"' reqAmt='"+lineItemVO.getRequestedAmt()+"' rejdAmt='"+lineItemVO.getDisAllowedAmt()+"' allwdAmt='"+lineItemVO.getAllowedAmt()+"' remarks='"+TTKCommon.getHtmlString(lineItemVO.getRemarks())+"' nextbill='"+NextBillSeqId+"' nextbillno='"+NextBillNo+"' nextcompleteYN='"+BillCompleteYN+"'/>";//added for maternity
						if(claimBillDetailVO.getBillIncludedYN().equals("No"))
						{
							bdReqAmount=bdReqAmount.add(lineItemVO.getRequestedAmt());
							bdallowAmount=bdallowAmount.add(lineItemVO.getAllowedAmt());
							bdrejAmount=bdrejAmount.add(lineItemVO.getDisAllowedAmt());
						}
					}//end of for(int j=0;j<alLineItems.size();j++)
				}//end of if(alLineItems!=null&&alLineItems.size()>0)
				xml += "</bill>";
			}//end of for(int i=0;i<alBills.size();i++)
		}//end of if(alBills!=null)
		xml += "</billdetails>";
		
		return xml;
	}//end of getClaimBillManagerObject()

	/**
	 * Returns the ParallelClaimBillManager session object for invoking methods on it.
	 * @return ParallelClaimBillManager session object which can be used for method invocation
	 * @exception throws TTKException
	 */
	private ParallelClaimBillManager getClaimBillManagerObject() throws TTKException
	{
		ParallelClaimBillManager claimBillManager = null;
		try
		{
			if(claimBillManager == null)
			{
				InitialContext ctx = new InitialContext();
				claimBillManager = (ParallelClaimBillManager) ctx.lookup("java:global/TTKServices/business.ejb3/ParallelClaimBillManagerBean!com.ttk.business.dataentryclaims.ParallelClaimBillManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strLineItems);
		}//end of catch
		return claimBillManager;
	}//end of getClaimBillManagerObject()
	
	/**
	 * This method is used to handle the exceptions rised in the Bill entry screen.
	 * @param request HttpServletRequest object
	 * @param response HttpServletResponse object
	 * @param expTTK TTKException user defined Exception containing error code
	 */
	private void processException1(HttpServletRequest request,HttpServletResponse response,TTKException expTTK)
	{
		PrintWriter out=null;
		ResourceBundle resourceBundle=ResourceBundle.getBundle("ApplicationResources",request.getLocale());
		log.info("Error in saving bill or Claim  :"+resourceBundle.getString(expTTK.getMessage()));
		try
		{
			String strXML=getXML(null,ClaimsWebBoardHelper.getAmmendmentYN(request),"",resourceBundle.
					getString(expTTK.getMessage()));
			response.setContentType("text/xml");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.println(strXML);
			out.flush();
		}//end of try
		catch(IOException ioe)
		{
			log.error("Error while sending response");
		}//end of catch(IOException ioe)
		catch(TTKException e)
		{
			log.error("Error while sending response");
		}//end of catch(TTKException e)
		finally
		{
			if(out!=null)
			{
				out.close();
			}//end of if(out!=null)
		}//end of finally
	}//end of processException(HttpServletRequest request,HttpServletResponse response,TTKException expTTK)
	
	private void processException(HttpServletRequest request,HttpServletResponse response,TTKException expTTK)
	{
		PrintWriter out=null;
		ResourceBundle resourceBundle=ResourceBundle.getBundle("ApplicationResources",request.getLocale());
		log.info("Error in saving bill or Claim  :"+resourceBundle.getString(expTTK.getMessage()));
		if((resourceBundle.getString(expTTK.getMessage())).equalsIgnoreCase("Claim has been Submitted for Processing, Modifications Not allowed."))
		{
			request.setAttribute("revert","error.revert.decoupling");
				
		}
		else
		{
		try
		{
			String strXML=getXML(null,ClaimsWebBoardHelper.getAmmendmentYN(request),"",resourceBundle.
					getString(expTTK.getMessage()));
			response.setContentType("text/xml");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.println(strXML);
			out.flush();
		}//end of try
		catch(IOException ioe)
		{
			log.error("Error while sending response");
		}//end of catch(IOException ioe)
		catch(TTKException e)
		{
			log.error("Error while sending response");
		}//end of catch(TTKException e)
		finally
		{
			if(out!=null)
			{
				out.close();
			}//end of if(out!=null)
		}//end of finally
	}//end of processException(HttpServletRequest request,HttpServletResponse response,TTKException expTTK)
	
	}//end of else
}//end LineItemsAction