/**
 * @ (#) PreAuthGeneralAction.java May 10, 2006
 * Project       : TTK HealthCare Services
 * File          : PreAuthGeneralAction.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : May 10, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.claims;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.claims.ClaimBatchManager;
import com.ttk.business.claims.ClaimManager;
import com.ttk.common.ClaimBatchWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dao.ResourceManager;
import com.ttk.dto.claims.BatchVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.common.Toolbar;

import formdef.plugin.util.FormUtils;

/**
 * This class is reused for adding pre-auth/claims in pre-auth and claims flow.
 */

public class ClaimBatchGeneralAction extends TTKAction {

	private static Logger log = Logger.getLogger( ClaimBatchGeneralAction.class );

	private static final String strClaimBatchDetails="ClaimsBatchDetails";
	private static final String strSelectEnrollmentID="SelectEnrollmentID";
	
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
			setLinks(request);
			log.debug("Inside ClaimBatchGeneralAction doView");
			HttpSession session=request.getSession();
			DynaActionForm frmClaimBatchGeneral= (DynaActionForm)form;
			StringBuffer strCaption=new StringBuffer();
			BatchVO batchVO=null;
			ClaimBatchManager claimBatchManagerObject=null;
			//TableData tableData =(TableData)session.getAttribute("tableData");
			claimBatchManagerObject=this.getClaimBatchManagerObject();
			batchVO=(BatchVO)session.getAttribute("claimBatchVO");
			session.setAttribute("partnersList", claimBatchManagerObject.getPartnersList());
			if(ClaimBatchWebBoardHelper.checkWebBoardId(request)==null)
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.search.and.select.batch.details");
				frmClaimBatchGeneral.initialize(mapping);
				session.setAttribute("frmClaimBatchGeneral",frmClaimBatchGeneral);
				session.setAttribute("listOfClaims",null);
				throw expTTK;
			}//end of if(ClaimBatchWebBoardHelper.checkWebBoardId(request)==null)
			
					 Object[] batchDetails= claimBatchManagerObject.getClaimBatchDetails(ClaimBatchWebBoardHelper.getBatchSeqId(request));
					 batchVO=(BatchVO)batchDetails[0];
					 
					 ArrayList<BatchVO> listOfClaims=(ArrayList<BatchVO>)batchDetails[1];
						frmClaimBatchGeneral= (DynaActionForm)FormUtils.setFormValues("frmClaimBatchGeneral",	batchVO, this, mapping, request);
						strCaption.append(" Edit");
						strCaption.append(" [ "+batchVO.getBatchNO()+ " ]");
						frmClaimBatchGeneral.set("caption", strCaption.toString());
						if(batchVO.getTotalAmount() != null)
						{
							frmClaimBatchGeneral.set("totalAmount",batchVO.getTotalAmount().toString());
						}
						session.setAttribute("frmClaimBatchGeneral", frmClaimBatchGeneral);
						session.setAttribute("listOfClaims", listOfClaims);
						this.addToWebBoard(batchVO, request);
			return this.getForward(strClaimBatchDetails, mapping, request);
			}//end of try
			catch(TTKException expTTK)
			{
			return this.processExceptions(request, mapping, expTTK);
			}//end of catch(TTKException expTTK)
			catch(Exception exp)
			{
			return this.processExceptions(request, mapping, new TTKException(exp,strClaimBatchDetails));
			}//end of catch(Exception exp)
			}//end of doView(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Adds the selected item to the web board and makes it as the selected item in the web board
	 * @param  preauthVO  object which contains the information of the preauth
	 * * @param String  strIdentifier whether it is preauth or enhanced preauth
	 * @param request HttpServletRequest
	 * @throws TTKException if any runtime exception occures
	 */
	private void addToWebBoard(BatchVO batchVO, HttpServletRequest request)throws TTKException
	{
		Toolbar toolbar = (Toolbar) request.getSession().getAttribute("toolbar");
		ArrayList<Object> alCacheObject = new ArrayList<Object>();
		CacheObject cacheObject = new CacheObject();
		cacheObject.setCacheId(this.prepareWebBoardId(batchVO)); 
		cacheObject.setCacheDesc(batchVO.getBatchNO());
		alCacheObject.add(cacheObject);
		//if the object(s) are added to the web board, set the current web board id
		toolbar.getWebBoard().addToWebBoardList(alCacheObject);
		toolbar.getWebBoard().setCurrentId(cacheObject.getCacheId());

		//webboardinvoked attribute will be set as true in request scope
		//to avoid the replacement of web board id with old value if it is called twice in same request scope
		request.setAttribute("webboardinvoked", "true");
	}//end of addToWebBoard(PreAuthVO preAuthVO, HttpServletRequest request,String strIdentifier)throws TTKException
	
	/**
	 * This method prepares the Weboard id for the selected Policy
	 * @param preAuthVO  preAuthVO for which webboard id to be prepared
	 * * @param String  strIdentifier whether it is preauth or enhanced preauth
	 * @return Web board id for the passedVO
	 */
	private String prepareWebBoardId(BatchVO batchVO)throws TTKException
	{
		StringBuffer sbfCacheId=new StringBuffer();
		sbfCacheId.append(batchVO.getBatchSeqID()!=null? String.valueOf(batchVO.getBatchSeqID()):" ");
		sbfCacheId.append("~#~").append(batchVO.getBatchNO()!=null?batchVO.getBatchNO():" ");
		sbfCacheId.append("~#~").append(batchVO.getSubmissionType()!=null? String.valueOf(batchVO.getSubmissionType()):" ");
		sbfCacheId.append("~#~").append(batchVO.getEncounterTypeId()!=null? String.valueOf(batchVO.getEncounterTypeId()):" ");
		sbfCacheId.append("~#~").append(batchVO.getProviderName()!=null? String.valueOf(batchVO.getProviderName()):" ");
		sbfCacheId.append("~#~").append(batchVO.getClaimType()!=null? String.valueOf(batchVO.getClaimType()):" ");
		sbfCacheId.append("~#~").append(batchVO.getBatchStatus()!=null? String.valueOf(batchVO.getBatchStatus()):" ");
		sbfCacheId.append("~#~").append(batchVO.getProviderName()!=null? String.valueOf(batchVO.getProviderName()):" ");
		sbfCacheId.append("~#~").append(batchVO.getEncounterTypeId()!=null? String.valueOf(batchVO.getEncounterTypeId()):" ");
		sbfCacheId.append("~#~").append(batchVO.getSubmissionType()!=null? String.valueOf(batchVO.getSubmissionType()):" ");
		return sbfCacheId.toString();
	}//end of prepareWebBoardId(BatchVO BatchVO)throws TTKException
	
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
		log.debug("Inside ClaimBatchGenealAction doChangeWebBoard");
		return doView(mapping,form,request,response);
	}//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	//HttpServletResponse response)

	/**
     * This method is called from the struts framework.
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
public ActionForward addBatch(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
try{
setLinks(request);
log.debug("Inside ClaimBatchGeneralAction AddBatch ");
DynaActionForm frmClaimBatchGeneral= (DynaActionForm)form;
ClaimBatchManager claimBatchManagerObject = this.getClaimBatchManagerObject();
request.getSession().setAttribute("partnersList", claimBatchManagerObject.getPartnersList());
if("NO".equals(request.getParameter("initialize"))){
	frmClaimBatchGeneral.set("providerName", "");
	frmClaimBatchGeneral.set("providerID", "");
request.getSession().setAttribute("frmClaimBatchGeneral",frmClaimBatchGeneral);
}else{
frmClaimBatchGeneral.initialize(mapping);
frmClaimBatchGeneral.set("caption","Add[]");
request.getSession().setAttribute("frmClaimBatchGeneral",frmClaimBatchGeneral);
request.getSession().setAttribute("frmAddClaimDetails",null);
request.getSession().setAttribute("batchDetails",null);
}

return this.getForward(strClaimBatchDetails, mapping, request);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strClaimBatchDetails));
}//end of catch(Exception exp)
}//end of addBatch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

/**
 * This method is called from the struts framework.
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




public ActionForward setProviderID(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
	
	try{
		setLinks(request);
		log.debug("Inside ClaimBatchGeneralAction setProviderID");
		DynaActionForm frmClaimBatchGeneral= (DynaActionForm)form;
		String providerID=frmClaimBatchGeneral.getString("providerID");
		String providerLicenceID="";
		Connection con=null;
		PreparedStatement statement=null;
		ResultSet rs=null;
		
	    	con=ResourceManager.getConnection();
	    	if(!"".equals(TTKCommon.checkNull(providerID))){
		  statement=con.prepareStatement("select hi.HOSP_LICENC_NUMB AS PROVIDER_ID from app.tpa_hosp_info hi where hosp_seq_id="+providerID);		
	      rs=statement.executeQuery();
	    	
	      if(rs.next()){
	    	  providerLicenceID=rs.getString("PROVIDER_ID"); 
	      }
	    	}
	      frmClaimBatchGeneral.set("providerLisenceNO", providerLicenceID);
	      request.getSession().setAttribute("frmClaimBatchGeneral",frmClaimBatchGeneral);
	      
	  		if(rs!=null)rs.close();
	  		if(statement!=null)statement.close();
	  		if(con!=null)con.close();			
		
		return this.getForward(strClaimBatchDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
		return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
	
		catch(Exception exp)
		{
		return this.processExceptions(request, mapping, new TTKException(exp,strClaimBatchDetails));
		}//end of catch(Exception exp)
 	
	
}





public ActionForward setNetWorkType(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{
setLinks(request);
log.debug("Inside ClaimBatchGeneralAction setNetWorkType");
DynaActionForm frmClaimBatchGeneral= (DynaActionForm)form;
String claimType=frmClaimBatchGeneral.getString("claimType");
String processType=frmClaimBatchGeneral.getString("processType");
String paymentTo=frmClaimBatchGeneral.getString("paymentTo");
	
	if("PTN".equals(paymentTo)){
		frmClaimBatchGeneral.set("netWorkType", "N");
	}
	else if("PRV".equals(paymentTo)){
		frmClaimBatchGeneral.set("netWorkType", "Y");
	}
	else{
		frmClaimBatchGeneral.set("netWorkType", "Y");
	}

if("DBL".equals(processType)&&"CNH".equals(claimType)&& (paymentTo==null||"".equals(paymentTo)))frmClaimBatchGeneral.set("paymentTo", "PTN");
request.getSession().setAttribute("frmClaimBatchGeneral",frmClaimBatchGeneral);
return this.getForward(strClaimBatchDetails, mapping, request);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strClaimBatchDetails));
}//end of catch(Exception exp)
}//end of setNetWorkType(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
log.debug("Inside ClaimBatchGeneralAction doSave ");
HttpSession session=request.getSession();
DynaActionForm frmClaimBatchGeneral= (DynaActionForm)form;
StringBuffer strCaption=new StringBuffer();
BatchVO batchVO=null;
ClaimBatchManager claimBatchManagerObject=null;
String successMsg;

claimBatchManagerObject=this.getClaimBatchManagerObject();
       batchVO = (BatchVO)FormUtils.getFormValues(frmClaimBatchGeneral, this, mapping, request);
           batchVO.setAddedBy((TTKCommon.getUserSeqId(request)));
           
           
           String strDates=TTKCommon.checkNull(batchVO.getBatchReceiveDate());
           String strTime=TTKCommon.checkNull(batchVO.getReceivedTime());
           String strDay=TTKCommon.checkNull(batchVO.getReceiveDay());
           
           java.util.Date date=TTKCommon.getOracleDateWithTime(strDates,strTime,strDay);
           
           if(date.getTime()>new java.util.Date().getTime()){
                   TTKException ttkExc=new TTKException();
                   ttkExc.setMessage("error.batch.recived.date.validation");
                   throw ttkExc;
           } 
           
		long batchSeqID= claimBatchManagerObject.saveClaimBatchDetails(batchVO);
		
		if(batchVO.getBatchSeqID()==null)successMsg="Batch Details Added Successfully";
		else successMsg="Batch Details Updated Successfully";
		
		 Object[] batchDetails= claimBatchManagerObject.getClaimBatchDetails(batchSeqID);
		 batchVO=(BatchVO)batchDetails[0];
		 ArrayList<BatchVO> listOfClaims=(ArrayList<BatchVO>)batchDetails[1];
			frmClaimBatchGeneral= (DynaActionForm)FormUtils.setFormValues("frmClaimBatchGeneral",	batchVO, this, mapping, request);
			strCaption.append(" Edit");
			strCaption.append(" [ "+batchVO.getBatchNO()+ " ]");
			frmClaimBatchGeneral.set("caption", strCaption.toString());
			session.setAttribute("partnersList", claimBatchManagerObject.getPartnersList());
			session.setAttribute("frmClaimBatchGeneral", frmClaimBatchGeneral);
			session.setAttribute("listOfClaims", listOfClaims);
			this.addToWebBoard(batchVO, request);
			request.setAttribute("successMsg",successMsg);
return this.getForward(strClaimBatchDetails, mapping, request);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strClaimBatchDetails));
}//end of catch(Exception exp)
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
public ActionForward deleteClaimDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	HttpServletResponse response) throws Exception{
try{
setLinks(request);
log.debug("Inside ClaimBatchGeneralAction deleteClaimDetails ");
HttpSession session=request.getSession();
DynaActionForm frmAddClaimDetails= (DynaActionForm)form;
ClaimBatchManager claimBatchManagerObject=null;
String successMsg;
claimBatchManagerObject=this.getClaimBatchManagerObject();
String strBatchSeqID=frmAddClaimDetails.getString("batchSeqID");
String strClaimSeqID=frmAddClaimDetails.getString("claimSeqID");

   claimBatchManagerObject.deleteInvoiceNO(strBatchSeqID, strClaimSeqID);
    successMsg="Deleted Successfully";
	
	 Object[] batchDetails= claimBatchManagerObject.getClaimBatchDetails(new Long(strBatchSeqID));
	// batchVO=(BatchVO)batchDetails[0];
	 ArrayList<BatchVO> listOfClaims=(ArrayList<BatchVO>)batchDetails[1];
	 frmAddClaimDetails.initialize(mapping);
		session.setAttribute("frmAddClaimDetails", frmAddClaimDetails);
		session.setAttribute("listOfClaims", listOfClaims);
		request.setAttribute("successMsg",successMsg);
return this.getForward(strClaimBatchDetails, mapping, request);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strClaimBatchDetails));
}//end of catch(Exception exp)
}//end of deleteClaimDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


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
public ActionForward overrideBatchDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		HttpServletResponse response) throws Exception{
try{
setLinks(request);
log.debug("Inside ClaimBatchGeneralAction overrideBatchDetails");
HttpSession session=request.getSession();
StringBuffer strCaption=new StringBuffer();
DynaActionForm	frmClaimBatchGeneral= (DynaActionForm)session.getAttribute("frmClaimBatchGeneral");
frmClaimBatchGeneral.set("overrideYN", "Y");
frmClaimBatchGeneral.set("batchStatus", "INP");
frmClaimBatchGeneral.set("completedYN", "");

			strCaption.append(" Edit");
			strCaption.append(" [ "+frmClaimBatchGeneral.getString("batchNO")+ " ]");
			frmClaimBatchGeneral.set("caption", strCaption.toString());
			session.setAttribute("frmClaimBatchGeneral", frmClaimBatchGeneral);
			session.setAttribute("listOfClaims", null);
return this.getForward(strClaimBatchDetails, mapping, request);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strClaimBatchDetails));
}//end of catch(Exception exp)
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
public ActionForward addClaimDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	HttpServletResponse response) throws Exception{
try{
setLinks(request);
log.debug("Inside ClaimBatchGeneralAction addRequestedAmount ");
HttpSession session=request.getSession();
DynaActionForm frmAddClaimDetails= (DynaActionForm)form;
BatchVO batchVO=null;
ClaimBatchManager claimBatchManagerObject=null;
String successMsg;

  claimBatchManagerObject=this.getClaimBatchManagerObject();
  
   batchVO = (BatchVO)FormUtils.getFormValues(frmAddClaimDetails, this, mapping, request);
       batchVO.setAddedBy((TTKCommon.getUserSeqId(request)));
       
	long batchSeqID= claimBatchManagerObject.addClaimDetails(batchVO);
	
	if(batchVO.getClaimSeqID()==null)successMsg="Added Successfully";
	else successMsg="Updated Successfully";
	
	 Object[] batchDetails= claimBatchManagerObject.getClaimBatchDetails(batchSeqID);
	
	 ArrayList<BatchVO> listOfClaims=(ArrayList<BatchVO>)batchDetails[1];
	 frmAddClaimDetails.initialize(mapping);
	 session.setAttribute("previousClaimNums", null);
	 session.setAttribute("frmAddClaimDetails", frmAddClaimDetails);
		session.setAttribute("listOfClaims", listOfClaims);
		request.setAttribute("successMsg",successMsg);
return this.getForward(strClaimBatchDetails, mapping, request);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strClaimBatchDetails));
}//end of catch(Exception exp)
}//end of addClaimDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
public ActionForward doSelectEnrollmentID(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	HttpServletResponse response) throws Exception{
try{
	log.debug("Inside ClaimBatchGeneralAction doSelectEnrollmentID ");
setLinks(request);
TableData  tableData =TTKCommon.getTableData(request);
DynaActionForm frmAddClaimDetails= (DynaActionForm)form;
tableData.createTableInfo("EnrollmentSearchTable",new ArrayList());
request.getSession().setAttribute("tableData",tableData);
request.getSession().setAttribute("frmAddClaimDetails",frmAddClaimDetails);
request.getSession().removeAttribute("claimantVO");
return this.getForward(strSelectEnrollmentID, mapping, request);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strClaimBatchDetails));
}//end of catch(Exception exp)
}//end of doSelectEnrollmentID(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
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
public ActionForward editClaimSubmissionDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	HttpServletResponse response) throws Exception{
try{
setLinks(request);
log.debug("Inside ClaimBatchGeneralAction editClaimSubmissionDetails ");
HttpSession session=request.getSession();

DynaActionForm frmAddClaimDetails= (DynaActionForm)form;
String rownum=request.getParameter("rownum");
   ArrayList<BatchVO> listOfClaims= (ArrayList<BatchVO>)session.getAttribute("listOfClaims");
   BatchVO  batchVO2 =listOfClaims.get(new Integer(rownum));
   frmAddClaimDetails.set("claimSeqID",batchVO2.getClaimSeqID().toString() );
   frmAddClaimDetails.set("providerInvoiceNO",batchVO2.getProviderInvoiceNO() );
   frmAddClaimDetails.set("requestedAmount",TTKCommon.checkNull(batchVO2.getRequestedAmount()).toString() );
	session.setAttribute("frmAddClaimDetails", frmAddClaimDetails);
		//this.addToWebBoard(preAuthDetailVO,request,"PAT");
return this.getForward(strClaimBatchDetails, mapping, request);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strClaimBatchDetails));
}//end of catch(Exception exp)
}//end of editClaimSubmissionDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
 	
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
public ActionForward editClaimReSubmissionDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	HttpServletResponse response) throws Exception{
try{
setLinks(request);
log.debug("Inside ClaimBatchGeneralAction editClaimReSubmissionDetails ");
HttpSession session=request.getSession();
ClaimManager claimManagerObject=this.getClaimManagerObject();
DynaActionForm frmAddClaimDetails= (DynaActionForm)form;
String rownum=request.getParameter("rownum");
   ArrayList<BatchVO> listOfClaims= (ArrayList<BatchVO>)session.getAttribute("listOfClaims");
   BatchVO  batchVO2 =listOfClaims.get(new Integer(rownum));
   frmAddClaimDetails.set("claimSeqID",batchVO2.getClaimSeqID().toString() );
   frmAddClaimDetails.set("providerInvoiceNO",batchVO2.getProviderInvoiceNO() );
   frmAddClaimDetails.set("previousClaimNO",batchVO2.getPreviousClaimNOSeqID().toString() );
   frmAddClaimDetails.set("enrollmentID",batchVO2.getEnrollmentID() );
   Map<String,String> previousClaimNums=claimManagerObject.getMemClaimList(batchVO2.getEnrollmentSeqID());
if(previousClaimNums==null)previousClaimNums=new LinkedHashMap<String,String>();
previousClaimNums.put(batchVO2.getPreviousClaimNOSeqID().toString(), batchVO2.getPreviousClaimNO());
	request.getSession().setAttribute("previousClaimNums", previousClaimNums);
   frmAddClaimDetails.set("enrollmentSeqID",TTKCommon.checkNull(batchVO2.getEnrollmentSeqID()).toString());
   frmAddClaimDetails.set("requestedAmount",batchVO2.getRequestedAmount().toString() );
   frmAddClaimDetails.set("resubmissionRemarks",batchVO2.getResubmissionRemarks() );
	session.setAttribute("frmAddClaimDetails", frmAddClaimDetails);
		//this.addToWebBoard(preAuthDetailVO,request,"PAT");
return this.getForward(strClaimBatchDetails, mapping, request);
}//end of try
catch(TTKException expTTK)
{
return this.processExceptions(request, mapping, expTTK);
}//end of catch(TTKException expTTK)
catch(Exception exp)
{
return this.processExceptions(request, mapping, new TTKException(exp,strClaimBatchDetails));
}//end of catch(Exception exp)
}//end of editClaimReSubmissionDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
 	
	/**
	 * Returns the ClaimBatchManager session object for invoking methods on it.
	 * @return ClaimBatchManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ClaimBatchManager getClaimBatchManagerObject() throws TTKException
	{
		ClaimBatchManager claimBatchManager = null;
		try
		{
			if(claimBatchManager == null)
			{
				InitialContext ctx = new InitialContext();
				claimBatchManager = (ClaimBatchManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimBatchManagerBean!com.ttk.business.claims.ClaimBatchManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strClaimBatchDetails);
		}//end of catch
		return claimBatchManager;
	}//end getClaimBatchManagerObject()
	/**
	 * Returns the ClaimManager session object for invoking methods on it.
	 * @return ClaimManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ClaimManager getClaimManagerObject() throws TTKException
	{
		ClaimManager claimManager = null;
		try
		{
			if(claimManager == null)
			{
				InitialContext ctx = new InitialContext();
				claimManager = (ClaimManager) ctx.lookup("java:global/TTKServices/business.ejb3/ClaimManagerBean!com.ttk.business.claims.ClaimManager");
			}//end of if(claimManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strClaimBatchDetails);
		}//end of catch
		return claimManager;
	}//end of getClaimManagerObject()


	}//end of ClaimBatchGeneralAction
