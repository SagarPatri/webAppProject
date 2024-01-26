/**
 * @ (#) AuditSearchAction.java May 11th 2006
 * Project 		: TTK HealthCare Services
 * File 		: AuditSearchAction.java
 * Author 		: Krupa J
 * Company 		: Span Systems Corporation
 * Date Created : May 11th 2006
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
import com.ttk.business.preauth.PreAuthSupportManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.preauth.SupportVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;
import formdef.plugin.util.FormUtils;

/**
 * This class is used for searching of Quality Control.
 * This class also provides option for updation of the QC informations.
 */

public class AuditSearchAction extends TTKAction
{
	private static Logger log = Logger.getLogger(AuditSearchAction.class );

	// Modes of QC Audit list
	private static final String strBackward="Backward";
	private static final String strForward="Forward";

	// Action mapping forwards of QC Audit list
	private static final String strQcAuditList="auditlist";

	// Action mapping forwards of Quality Details
	private static final String strQualityDetails="qualitydetails";
	private static final String strCloseQualityDetails="closequalitydetails";
	
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
			log.debug("Inside AuditSearchAction doDefault");
			DynaActionForm frmAudit=(DynaActionForm)form;
			String strDefaultBranchID  = String.valueOf(((UserSecurityProfile)
											request.getSession().getAttribute("UserSecurityProfile")).getBranchID());
			//create new table data object
			TableData tableData = new TableData();
			//create the required grid table
			tableData.createTableInfo("AuditSearchTable",new ArrayList());
			request.getSession().setAttribute("tableData",tableData);
			((DynaActionForm)form).initialize(mapping);		//reset the form data
			request.getSession().setAttribute("searchparam", null);
			frmAudit.set("officeInfo",strDefaultBranchID);
			return this.getForward(strQcAuditList, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"quality"));
		}//end of catch(Exception exp)
	}//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
			log.debug("Inside AuditSearchAction doSearch");
			setLinks(request);
			TableData  tableData =TTKCommon.getTableData(request);
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
			String strSortID = TTKCommon.checkNull(request.getParameter("sortId"));
			//if the page number or sort id is clicked
			if(!strPageID.equals("") || !strSortID.equals(""))
			{
				if(!strPageID.equals(""))
				{
					tableData.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
					return (mapping.findForward(strQcAuditList));
				}///end of if(!strPageID.equals(""))
				else
				{
					tableData.setSortData(TTKCommon.checkNull(request.getParameter("sortId")));
					tableData.modifySearchData("sort");//modify the search data
				}//end of else
			}//end of if(!strPageID.equals("") || !strSortID.equals(""))
			else
			{
				//create the required grid table
				tableData.createTableInfo("AuditSearchTable",null);
				tableData.setSearchData(this.populateSearchCriteria((DynaActionForm)form,request));
				tableData.modifySearchData("search");
			}//end of else
			ArrayList alEnrollment= preAuthSupportManagerObject.getSupportQCAuditList(tableData.getSearchData());
			tableData.setData(alEnrollment, "search");
			//set the table data object to session
			request.getSession().setAttribute("tableData",tableData);
			//finally return to the grid screen
			return this.getForward(strQcAuditList, mapping, request);

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"quality"));
		}//end of catch(Exception exp)
	}//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to get the next set of records with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
									HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside AuditSearchAction doForward");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			tableData.modifySearchData(strForward);//modify the search data
			ArrayList alEnrollment = preAuthSupportManagerObject.getSupportQCAuditList(tableData.getSearchData());
			tableData.setData(alEnrollment, strForward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strQcAuditList, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"quality"));
		}//end of catch(Exception exp)
	}//end of doForward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * This method is used to get the previous set of records with the given search criteria.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,
										HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside AuditSearchAction doBackward");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			tableData.modifySearchData(strBackward);//modify the search data
			ArrayList alEnrollment = preAuthSupportManagerObject.getSupportQCAuditList(tableData.getSearchData());
			tableData.setData(alEnrollment, strBackward);//set the table data
			request.getSession().setAttribute("tableData",tableData);//set the tableData object to session
			return this.getForward(strQcAuditList, mapping, request);//finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"quality"));
		}//end of catch(Exception exp)
	}//end of doBackward(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
	public ActionForward doViewQuality(ActionMapping mapping,ActionForm form,HttpServletRequest request,
											HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside AuditSearchAction doViewQuality");
			setLinks(request);
			SupportVO supportVO=null;
			TableData tableData = TTKCommon.getTableData(request);
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			DynaActionForm frmQtyDetails = (DynaActionForm)form;
			DynaActionForm frmAudit = (DynaActionForm)request.getSession().getAttribute("frmAudit");
			String strType=(String)frmAudit.get("investType");
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				supportVO=(SupportVO)tableData.getRowInfo(Integer.parseInt((String)request.getParameter("rownum")));
				supportVO=preAuthSupportManagerObject.getQualityDetail(strType,supportVO.getSeqID(),
							TTKCommon.getUserSeqId(request));
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			else
			{
				TTKException expTTK = new TTKException();
				expTTK.setMessage("error.preauthclaimno.required");
				throw expTTK;
			}// end of else
			frmQtyDetails= (DynaActionForm)FormUtils.setFormValues("frmQtyDetails", supportVO, this, mapping, request);
			request.setAttribute("frmQtyDetails",frmQtyDetails);
			return (mapping.findForward(strQualityDetails));
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"quality"));
		}//end of catch(Exception exp)
	}//end of doViewQuality(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
								HttpServletResponse response) throws Exception
	{
		try{
			log.debug("Inside AuditSearchAction doSave");
			setLinks(request);
			SupportVO supportVO=new SupportVO();
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			DynaActionForm frmQtyDetails = (DynaActionForm)form;
			DynaActionForm frmAudit = (DynaActionForm)request.getSession().getAttribute("frmAudit");
			String strType=(String)frmAudit.get("investType");
			supportVO = (SupportVO)FormUtils.getFormValues(frmQtyDetails, this, mapping, request);
			supportVO.setUpdatedBy(TTKCommon.getUserSeqId(request));//User Id
			Long lCount=preAuthSupportManagerObject.saveQualityDetail(supportVO);
			if(lCount>0)
			{
				if(supportVO.getSeqID()!=null)
				{
					request.setAttribute("updated","message.savedSuccessfully");
					supportVO=preAuthSupportManagerObject.getQualityDetail(strType,lCount,
									TTKCommon.getUserSeqId(request));
				}//end of if(supportVO.getSeqID()!=null)
				frmQtyDetails= (DynaActionForm)FormUtils.setFormValues("frmQtyDetails", supportVO,
									this, mapping, request);
				request.setAttribute("frmQtyDetails",frmQtyDetails);
			}//end of if(lCount>0)
			return this.getForward(strQualityDetails, mapping, request);

		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"quality"));
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
			log.debug("Inside AuditSearchAction doReset");
			setLinks(request);
			SupportVO supportVO=new SupportVO();
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			DynaActionForm frmQtyDetails = (DynaActionForm)form;
			DynaActionForm frmAudit = (DynaActionForm)request.getSession().getAttribute("frmAudit");
			String strType=(String)frmAudit.get("investType");
			if(frmQtyDetails.get("seqID")!=null && !frmQtyDetails.get("seqID").equals(""))
			{
				supportVO=preAuthSupportManagerObject.getQualityDetail(strType,
						TTKCommon.getLong((String)frmQtyDetails.get("seqID")),TTKCommon.getUserSeqId(request));
				frmQtyDetails= (DynaActionForm)FormUtils.setFormValues("frmQtyDetails", supportVO,
								this, mapping, request);
			}//end of if(frmQtyDetails.get("seqID")!=null && !frmQtyDetails.get("seqID").equals(""))
			request.setAttribute("frmQtyDetails",frmQtyDetails);
			return this.getForward(strQualityDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"quality"));
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
	public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,
									HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside AuditSearchAction doClose");
			setLinks(request);
			TableData tableData = TTKCommon.getTableData(request);
			PreAuthSupportManager preAuthSupportManagerObject=this.getPreAuthSupportManagerObject();
			if(tableData.getSearchData().size()>1)
			{
				ArrayList alEnrollment = preAuthSupportManagerObject.getSupportQCAuditList(tableData.getSearchData());
				tableData.setData(alEnrollment, "search");
				//set the batch policy data object to session
				request.getSession().setAttribute("tableData",tableData);
			}//end of if(tableData.getSearchData().size()>1)
			return this.getForward(strCloseQualityDetails, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"quality"));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the PreAuthSupportManager session object for invoking methods on it.
	 * @return PreAuthSupportManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthSupportManager getPreAuthSupportManagerObject() throws TTKException
	{
		PreAuthSupportManager preAuthSupportManager = null;
		try
		{
			if(preAuthSupportManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthSupportManager = (PreAuthSupportManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthSupportManagerBean!com.ttk.business.preauth.PreAuthSupportManager");
			}//end of if(preAuthSupportManager == null)
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "support");
		}//end of catch
		return preAuthSupportManager;
	}//end getPreAuthSupportManagerObject()

	/**
	 * this method will add search criteria fields and values to the arraylist and will return it
	 * @param frmAudit DynaActionForm
	 * @return ArrayList contains search parameters
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm frmAudit,HttpServletRequest request)throws TTKException
	{
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(((String)frmAudit.get("investType")));
		alSearchParams.add(TTKCommon.replaceSingleQots((String)frmAudit.get("preAuthClaimNo")));
		alSearchParams.add(((String)frmAudit.get("markedDate")));
		alSearchParams.add((String)frmAudit.get("officeInfo"));
		alSearchParams.add((String)frmAudit.get("qualityStatus"));
		alSearchParams.add(TTKCommon.getUserSeqId(request));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm frmAudit)
}// end of AuditSearchAction