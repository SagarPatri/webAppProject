/**
 * @ (#) IntimationDetailsAction.java March 17th 2008
 * Project 		: TTK HealthCare Services
 * File 		: IntimationDetailsAction.java
 * Author 		: Krupa J
 * Company 		: Span Systems Corporation
 * Date Created : March 17th 2008
 *
 * @author 		: Krupa J
 * Modified by 	:
 * Modified date:
 * Reason 		:
 */
package com.ttk.action.support;

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
import com.ttk.action.table.TableData;
import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.business.support.SupportManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.onlineforms.OnlineIntimationVO;

import formdef.plugin.util.FormUtils;

public class IntimationDetailsAction extends TTKAction
{
	private static Logger log = Logger.getLogger(IntimationDetailsAction.class ); // Getting Logger for this Class file
	private static final String strIntimationDetails="intimationdetails";
	private static final String strIntimationList="closeintimationdetails";
	
	//	Exception Message Identifier
    private static final String strIntimationError="Intimation";
    
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
    public ActionForward doViewIntimationDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    										HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside IntimationDetailsAction doViewIntimationDetails");
    		DynaActionForm frmIntimationDetails= (DynaActionForm) form;
    		OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
    		OnlineIntimationVO onlineIntimationVO=new OnlineIntimationVO();
    		StringBuffer sbfCaption= new StringBuffer();
    		//if rownumber is found populate the form object
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				onlineIntimationVO=(OnlineIntimationVO)((TableData)request.getSession().getAttribute("tableData")).getData().get(
											Integer.parseInt(TTKCommon.checkNull(request.getParameter("rownum"))));
				onlineIntimationVO=onlineAccessObject.selectIntimation(onlineIntimationVO.getIntimationSeqID(),"Pre-Auth");
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			else
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.intimation.required");
				throw expTTK;
			}//end of else
			sbfCaption.append(" [ " +onlineIntimationVO.getMemName()+ " ] ");
			frmIntimationDetails = (DynaActionForm)FormUtils.setFormValues("frmIntimationDetails",onlineIntimationVO,
															this,mapping,request);
			frmIntimationDetails.set("hospitalVO", FormUtils.setFormValues("frmHospital",
					onlineIntimationVO.getOnlineHospitalVO(),this,mapping,request));
			frmIntimationDetails.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmIntimationDetails",frmIntimationDetails);
    		return this.getForward(strIntimationDetails,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strIntimationError));
    	}//end of catch(Exception exp)
    }//end of doViewIntimationDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse

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
			log.debug("Inside IntimationDetailsAction doClose");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			SupportManager supportObject = this.getSupportManagerObject();
			if(tableData.getSearchData()!= null&& tableData.getSearchData().size()>0)
			{
				ArrayList alIntimation = supportObject.getIntimationList(tableData.getSearchData());

				tableData.setData(alIntimation, "Cancel");
				request.getSession().setAttribute("tableData",tableData);
				//reset the forward links after adding the records if any
				tableData.setForwardNextLink();
			}//end of if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			return this.getForward(strIntimationList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strIntimationError));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
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
	public ActionForward doSave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		try {
			log.debug("Inside IntimationDetailsAction doSave");
			setLinks(request);
			OnlineIntimationVO onlineIntimationVO = new OnlineIntimationVO();
			SupportManager supportObject = this.getSupportManagerObject();
			OnlineAccessManager onlineAccessObject = this.getOnlineAccessManager();
			DynaActionForm frmIntimationDetails = (DynaActionForm) form;
			onlineIntimationVO = (OnlineIntimationVO) FormUtils.getFormValues(frmIntimationDetails, this, mapping, request);
			onlineIntimationVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// User Id
			StringBuffer sbfCaption= new StringBuffer();
			Long lCount = supportObject.saveIntimationDetail(onlineIntimationVO);
			if (lCount > 0) 
			{
				request.setAttribute("updated", "message.savedSuccessfully");
				onlineIntimationVO = onlineAccessObject.selectIntimation(onlineIntimationVO.getIntimationSeqID(),"Pre-Auth");
				frmIntimationDetails = (DynaActionForm)FormUtils.setFormValues("frmIntimationDetails",onlineIntimationVO,
						this,mapping,request);
				frmIntimationDetails.set("hospitalVO", FormUtils.setFormValues("frmHospital",
				onlineIntimationVO.getOnlineHospitalVO(),this,mapping,request));
				sbfCaption.append(" [ " +onlineIntimationVO.getMemName()+ " ] ");
				frmIntimationDetails.set("caption",sbfCaption.toString());
				request.setAttribute("frmIntimationDetails",frmIntimationDetails);
			}// end of if(lCount>0)
			return this.getForward(strIntimationDetails, mapping, request);
		}// end of try
		catch (TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}// end of catch(TTKException expTTK)
		catch (Exception exp) 
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strIntimationError));
		}// end of catch(Exception exp)
	}// end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside IntimationDetailsAction doReset");
			setLinks(request);
			OnlineIntimationVO onlineIntimationVO = new OnlineIntimationVO();
			OnlineAccessManager onlineAccessObject = this.getOnlineAccessManager();
			DynaActionForm frmIntimationDetails = (DynaActionForm)form;
			StringBuffer sbfCaption= new StringBuffer();
			if(frmIntimationDetails.get("intimationSeqID")!=null && !frmIntimationDetails.get("intimationSeqID").equals(""))
			{
				onlineIntimationVO=onlineAccessObject.selectIntimation(TTKCommon.getLong(frmIntimationDetails.get("intimationSeqID").toString()),"Pre-Auth");
				sbfCaption.append(" [ " +onlineIntimationVO.getMemName()+ " ] ");
				frmIntimationDetails = (DynaActionForm)FormUtils.setFormValues("frmIntimationDetails",onlineIntimationVO,
						this,mapping,request);
				frmIntimationDetails.set("hospitalVO", FormUtils.setFormValues("frmHospital",
				onlineIntimationVO.getOnlineHospitalVO(),this,mapping,request));
			}//end of if(frmQtyDetails.get("seqID")!=null && !frmQtyDetails.get("seqID").equals(""))
			frmIntimationDetails.set("caption",sbfCaption.toString());
			request.setAttribute("frmIntimationDetails",frmIntimationDetails);
			return this.getForward(strIntimationDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strIntimationError));
		}//end of catch(Exception exp)
	}//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
    /**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private SupportManager getSupportManagerObject() throws TTKException
	{
		SupportManager supportManager = null;
		try
		{
			if(supportManager == null)
			{
				InitialContext ctx = new InitialContext();
				supportManager = (SupportManager) ctx.lookup("java:global/TTKServices/business.ejb3/SupportManagerBean!com.ttk.business.support.SupportManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strIntimationError);
		}//end of catch
		return supportManager;
	}//end of getPreAuthSupportManagerObject()
	/**
	 * Returns the OnlineAccessManager session object for invoking methods on it.
	 * @return OnlineAccessManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private OnlineAccessManager getOnlineAccessManager() throws TTKException
	{
		OnlineAccessManager onlineAccessManager = null;
		try
		{
			if(onlineAccessManager == null)
			{
				InitialContext ctx = new InitialContext();
				onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
			}//end ofif(onlineAccessManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strIntimationError);
		}//end of catch
		return onlineAccessManager;
	}//end getOnlineAccessManager()
}//end of IntimationDetailsAction
