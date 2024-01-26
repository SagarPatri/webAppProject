/**
 * @ (#) AssignToAction.java Aug 10, 2006
 * Project      : TTK HealthCare Services
 * File         : AssignToAction.java
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : Aug 10, 2006
 *
 * @author      :  Krupa J
 * Modified by  :
 * Modified date:
 * Reason       :
 */

package com.ttk.action.preauth;

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
import com.ttk.business.claims.ClaimManager;
import com.ttk.business.coding.CodingManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.preauth.PreAuthAssignmentVO;
import com.ttk.dto.preauth.PreAuthVO;
import formdef.plugin.util.FormUtils;

/**
 * This class is reusable for assiging the user to claims,pre-auth in Claims and Pre-Auth flow.
 */

public class AssignToAction extends TTKAction{

	private static Logger log = Logger.getLogger(AssignToAction.class);

	//for forwards
	private static final String strAssignPreAuth="assignto";
	private static final String strAssignClaim="assignclaims";
	private static final String strPreauthlist="preauthlist";
	private static final String strClaimslist="claimslist";
	private static final String strCodingPreauth="codingpreauthassign";
	private static final String strCodingPreauthList="codingpreauthlist";
	private static final String strCodingClaimsList="codingclaimslist";
	private static final String strCodingClaims="codingclaimsassign";
	
	//Exception Message Identifier
    private static final String strAssignUsers="assignusers";

	/**
	 * This method is used to Assign the users.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAssign(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.debug("Inside AssignToAction doAssign");
			String strModeValue = "";
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			TableData tableData =TTKCommon.getTableData(request);
			ArrayList <Object>alAssignUserList=new ArrayList<Object>();
			//ArrayList alUserList=new ArrayList();
			ArrayList alUserList=null;
			String strAssignTo=null;
			if(strLink.equals("Pre-Authorization")||strSubLink.equals("PreAuth"))
			{
				strModeValue="PAT";
			}//end of if(strLink.equals("Pre-Authorization")||strSubLink.equals("PreAuth") )
			else if(strLink.equals("Claims")||strSubLink.equals("Claims"))
			{
				strModeValue="CLM";
			}//end of else if(strLink.equals("Claims"))
			//clear the dynaform if visiting from left links for the first time
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			DynaActionForm frmAssign=(DynaActionForm)form;
			DynaActionForm frmGeneral=(DynaActionForm)form;
			PreAuthAssignmentVO preAuthAssignmentVO=null;
			PreAuthVO preAuthVO=null;
			if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			{
				preAuthVO=(PreAuthVO)tableData.getRowInfo(Integer.parseInt((String)(frmGeneral).get("rownum")));
				if(preAuthVO.getAssignUserSeqID()!=null)
				{
					preAuthAssignmentVO=preAuthObject.getAssignTo(preAuthVO.getAssignUserSeqID(),
																		TTKCommon.getUserSeqId(request),strModeValue);
				}//end of if(preAuthVO.getAssignUserSeqID()!=null)
				else
				{
					frmAssign.initialize(mapping);
					preAuthAssignmentVO=new PreAuthAssignmentVO();
					preAuthAssignmentVO.setSelectedPreAuthNos(preAuthVO.getPreAuthNo());
					preAuthAssignmentVO.setPreAuthSeqID(preAuthVO.getPreAuthSeqID());
					preAuthAssignmentVO.setClaimSeqID(preAuthVO.getClaimSeqID());
					preAuthAssignmentVO.setPolicySeqID(preAuthVO.getPolicySeqID());
					preAuthAssignmentVO.setOfficeSeqID(preAuthVO.getOfficeSeqID());
				}//end of else
				if(strLink.equals("Pre-Authorization"))
				{
					alAssignUserList.add(preAuthAssignmentVO.getPreAuthSeqID());
					strAssignTo=strAssignPreAuth;
				}//end of if(strLink.equals("Pre-Authorization"))
				if(strLink.equals("Claims"))
				{
					alAssignUserList.add(preAuthAssignmentVO.getClaimSeqID());
					strAssignTo=strAssignClaim;
				}//end of else if(strLink.equals("Claims"))
				else if(strLink.equals("Coding"))
				{
					if(strSubLink.equals("PreAuth"))
					{
						alAssignUserList.add(preAuthAssignmentVO.getPreAuthSeqID());	
						strAssignTo=strCodingPreauth;
					}
					else if(strSubLink.equals("Claims"))
					{
						alAssignUserList.add(preAuthAssignmentVO.getClaimSeqID());
						strAssignTo=strCodingClaims;
					}
				}
				alAssignUserList.add(preAuthAssignmentVO.getPolicySeqID());
				alAssignUserList.add(preAuthAssignmentVO.getOfficeSeqID());
				alUserList=preAuthObject.getAssignUserList(alAssignUserList,strModeValue);
				frmAssign=(DynaActionForm)FormUtils.setFormValues("frmAssign", preAuthAssignmentVO, this,
																							mapping, request);
				request.getSession().setAttribute("alUserList",alUserList);
				request.setAttribute("frmAssign",frmAssign);
			}//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
			return mapping.findForward(strAssignTo);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAssignUsers));
		}//end of catch(Exception exp)
	}// end of doAssign(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
	 * This method is used to change the Assign Users
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doChangeUsers(ActionMapping mapping,ActionForm form,HttpServletRequest request,
															HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.debug("Inside AssignToAction doChangeUsers");
			String strModeValue = "";
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			ArrayList <Object>alAssignUserList=new ArrayList<Object>();
			ArrayList alUserList=new ArrayList();
			String strAssignTo=null;
			if(strLink.equals("Pre-Authorization")||strSubLink.equals("PreAuth"))
			{
				strModeValue="PAT";
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims")||strSubLink.equals("Claims"))
			{
				strModeValue="CLM";
			}//end of else if(strLink.equals("Claims"))
			//clear the dynaform if visiting from left links for the first time
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			DynaActionForm frmAssign = (DynaActionForm)form;
			PreAuthAssignmentVO preAuthAssignmentVO=(PreAuthAssignmentVO)FormUtils.getFormValues(frmAssign,this,
																								mapping, request);
			if(preAuthAssignmentVO.getOfficeSeqID()!=null)
			{
				if(strLink.equals("Pre-Authorization"))
				{
					alAssignUserList.add(preAuthAssignmentVO.getPreAuthSeqID());
					strAssignTo=strAssignPreAuth;
				}//end of if(strLink.equals("Pre-Authorization"))
				if(strLink.equals("Claims"))
				{
					alAssignUserList.add(preAuthAssignmentVO.getClaimSeqID());
					strAssignTo=strAssignClaim;
				}//end of else if(strLink.equals("Claims"))
				else if(strLink.equals("Coding"))
				{
					if(strSubLink.equals("PreAuth"))
					{
						alAssignUserList.add(preAuthAssignmentVO.getPreAuthSeqID());	
						strAssignTo=strCodingPreauth;
					}
					else if(strSubLink.equals("Claims"))
					{
						alAssignUserList.add(preAuthAssignmentVO.getClaimSeqID());
						strAssignTo=strCodingClaims;
					}
				}//end of if(strLink.equals("Coding"))
				alAssignUserList.add(preAuthAssignmentVO.getPolicySeqID());
				alAssignUserList.add(preAuthAssignmentVO.getOfficeSeqID());
				alUserList=preAuthObject.getAssignUserList(alAssignUserList,strModeValue);
			}//end of if(preAuthAssignmentVO.getOfficeSeqID()!=null)
			request.getSession().setAttribute("alUserList",alUserList);
			frmAssign.set("frmChanged","changed");
			request.setAttribute("frmAssign",frmAssign);
			return  mapping.findForward(strAssignTo);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAssignUsers));
		}//end of catch(Exception exp)
	}// end of doChangeUsers(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
		try
		{
			setLinks(request);
			log.debug("Inside AssignToAction doSave");
			String strModeValue = "";
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			ArrayList <Object>alAssignUserList=new ArrayList<Object>();
			ArrayList alUserList=new ArrayList();
			String strAssignTo=null;
			if(strLink.equals("Pre-Authorization")||strSubLink.equals("PreAuth"))
			{
				strModeValue="PAT";
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims")||strSubLink.equals("Claims"))
			{
				strModeValue="CLM";
			}//end of else if(strLink.equals("Claims"))
			//clear the dynaform if visiting from left links for the first time
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			DynaActionForm frmAssign=(DynaActionForm)form;
			PreAuthAssignmentVO preAuthAssignmentVO=(PreAuthAssignmentVO)FormUtils.getFormValues(frmAssign,this,
																					mapping, request);
			preAuthAssignmentVO.setAssignUserSeqID(null);
			preAuthAssignmentVO.setUpdatedBy(TTKCommon.getUserSeqId(request));// get the Logged in USER_SEQ_ID from session
			//if user is Super User set Flag to Y
			if(TTKCommon.isAuthorized(request,"AssignAll")){
				preAuthAssignmentVO.setSuperUserYN("Y");
			}//end of if(TTKCommon.isAuthorized(request,"AssignAll"))
			else{
				preAuthAssignmentVO.setSuperUserYN("N");
			}//end of else
			Long lngResult = preAuthObject.assignPreAuth(preAuthAssignmentVO);
			if(lngResult >0)
			{
				request.setAttribute("updated","message.saved");
				preAuthAssignmentVO=preAuthObject.getAssignTo(lngResult,preAuthAssignmentVO.getUpdatedBy(),
																					strModeValue);
			}//end of if(lngResult >0)
			if(strLink.equals("Pre-Authorization"))
			{
				alAssignUserList.add(preAuthAssignmentVO.getPreAuthSeqID());
				strAssignTo=strAssignPreAuth;
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals("Claims"))
			{
				alAssignUserList.add(preAuthAssignmentVO.getClaimSeqID());
				strAssignTo=strAssignClaim;
			}//end of else if(strLink.equals("Claims"))
			else if(strLink.equals("Coding"))
			{
				if(strSubLink.equals("PreAuth"))
				{
					alAssignUserList.add(preAuthAssignmentVO.getPreAuthSeqID());	
					strAssignTo=strCodingPreauth;
				}
				else if(strSubLink.equals("Claims"))
				{
					alAssignUserList.add(preAuthAssignmentVO.getClaimSeqID());
					strAssignTo=strCodingClaims;
				}
			}//end of if(strLink.equals("Coding"))
			alAssignUserList.add(preAuthAssignmentVO.getPolicySeqID());
			alAssignUserList.add(preAuthAssignmentVO.getOfficeSeqID());
			alUserList=preAuthObject.getAssignUserList(alAssignUserList,strModeValue);
			request.getSession().setAttribute("alUserList",alUserList);
			frmAssign=(DynaActionForm)FormUtils.setFormValues("frmAssign", preAuthAssignmentVO, this, mapping, request);
			request.setAttribute("frmAssign",frmAssign);
			return  mapping.findForward(strAssignTo);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAssignUsers));
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
														HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.debug("Inside AssignToAction doReset");
			String strModeValue = "";
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			ArrayList <Object>alAssignUserList=new ArrayList<Object>();
			ArrayList alUserList=new ArrayList();
			String strAssignTo=null;
			if(strLink.equals("Pre-Authorization")||strSubLink.equals("PreAuth"))
			{
				strModeValue="PAT";
			}//end of if(strLink.equals("Pre-Authorization"))
			else if(strLink.equals("Claims")||strSubLink.equals("Claims"))
			{
				strModeValue="CLM";
			}//end of else if(strLink.equals("Claims"))
			//clear the dynaform if visiting from left links for the first time
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			DynaActionForm frmAssign = (DynaActionForm)form;
			PreAuthAssignmentVO preAuthAssignmentVO=null;
			if(frmAssign.get("assignUserSeqID")!=null && !frmAssign.get("assignUserSeqID").equals(""))
			{
				preAuthAssignmentVO=preAuthObject.getAssignTo(TTKCommon.getLong(((String)
									frmAssign.get("assignUserSeqID"))),TTKCommon.getUserSeqId(request),strModeValue);
			}//end of if(preAuthVO.getAssignUserSeqID()!=null)
			else
			{
				preAuthAssignmentVO=new PreAuthAssignmentVO();
				preAuthAssignmentVO.setSelectedPreAuthNos((String)frmAssign.getString("selectedPreAuthNos"));
				preAuthAssignmentVO.setPreAuthSeqID(TTKCommon.getLong((String)frmAssign.getString("preAuthSeqID")));
				preAuthAssignmentVO.setClaimSeqID(TTKCommon.getLong((String)frmAssign.getString("claimSeqID")));
				preAuthAssignmentVO.setPolicySeqID(TTKCommon.getLong((String)frmAssign.getString("policySeqID")));
				preAuthAssignmentVO.setOfficeSeqID(TTKCommon.getLong((String)frmAssign.getString("officeSeqID")));
			}//end of else
			if(strLink.equals("Pre-Authorization"))
			{
				alAssignUserList.add(preAuthAssignmentVO.getPreAuthSeqID());
				strAssignTo=strAssignPreAuth;
			}//end of if(strLink.equals("Pre-Authorization"))
			if(strLink.equals("Claims"))
			{
				alAssignUserList.add(preAuthAssignmentVO.getClaimSeqID());
				strAssignTo=strAssignClaim;
			}//end of else if(strLink.equals("Claims"))
			else if(strLink.equals("Coding"))
			{
				if(strSubLink.equals("PreAuth"))
				{
					alAssignUserList.add(preAuthAssignmentVO.getPreAuthSeqID());	
					strAssignTo=strCodingPreauth;
				}
				else if(strSubLink.equals("Claims"))
				{
					alAssignUserList.add(preAuthAssignmentVO.getClaimSeqID());
					strAssignTo=strCodingClaims;
				}
			}//end of if(strLink.equals("Coding"))
			alAssignUserList.add(preAuthAssignmentVO.getPolicySeqID());
			alAssignUserList.add(preAuthAssignmentVO.getOfficeSeqID());
			alUserList=preAuthObject.getAssignUserList(alAssignUserList,strModeValue);
			frmAssign=(DynaActionForm)FormUtils.setFormValues("frmAssign", preAuthAssignmentVO, this, mapping, request);
			request.getSession().setAttribute("alUserList",alUserList);
			request.setAttribute("frmAssign",frmAssign);
			return  mapping.findForward(strAssignTo);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAssignUsers));
		}//end of catch(Exception exp)
	}// end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
															HttpServletResponse response) throws Exception
	{
		try
		{
			setLinks(request);
			log.debug("Inside AssignToAction doClose");
			PreAuthManager preAuthObject=this.getPreAuthManagerObject();
			ClaimManager claimManagerObject=this.getClaimManagerObject();
			CodingManager codingManagerObject=this.getCodingManagerObject();
			String strLink = TTKCommon.getActiveLink(request);
			String strSubLink = TTKCommon.getActiveSubLink(request);
			String strFwdMode = null;
			TableData tableData =TTKCommon.getTableData(request);
			//clear the dynaform if visiting from left links for the first time
			if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y")){
				((DynaActionForm)form).initialize(mapping);//reset the form data
			}//end of if(TTKCommon.checkNull(request.getParameter("Entry")).equals("Y"))
			if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			{
				ArrayList alGeneralList=null;
				if(strLink.equals("Pre-Authorization"))
				{
					strFwdMode = strPreauthlist;
					//refresh the data in cancel mode, in order to get the new records if any.
					alGeneralList= preAuthObject.getPreAuthList(tableData.getSearchData());
				}//end of if(strLink.equals("Pre-Authorization"))
				if(strLink.equals("Claims"))
				{
					strFwdMode = strClaimslist;
					alGeneralList= claimManagerObject.getClaimList(tableData.getSearchData());
				}//end of else if(strLink.equals("Claims"))
				else if(strLink.equals("Coding"))
				{
					if(strSubLink.equals("PreAuth"))
					{
						strFwdMode = strCodingPreauthList;
						//refresh the data in cancel mode, in order to get the new records if any.
						alGeneralList= codingManagerObject.getPreAuthList(tableData.getSearchData());
					}//end of if(strSubLink.equals("PreAuth"))
					else if(strSubLink.equals("Claims"))
					{
						strFwdMode = strCodingClaimsList;
						alGeneralList= codingManagerObject.getClaimList(tableData.getSearchData());
					}//end of else if(strSubLink.equals("Claims"))
				}//end of else if(strLink.equals("Coding"))
				tableData.setData(alGeneralList,"search");
				request.getSession().setAttribute("tableData",tableData);
			}//end of if(tableData.getSearchData() != null && tableData.getSearchData().size() > 0)
			return this.getForward(strFwdMode, mapping, request);   //finally return to the grid screen
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp, strAssignUsers));
		}//end of catch(Exception exp)
	}// end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	/**
	 * Returns the PreAuthManager session object for invoking methods on it.
	 * @return PreAuthManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private PreAuthManager getPreAuthManagerObject() throws TTKException
	{
		PreAuthManager preAuthManager = null;
		try
		{
			if(preAuthManager == null)
			{
				InitialContext ctx = new InitialContext();
				preAuthManager = (PreAuthManager) ctx.lookup("java:global/TTKServices/business.ejb3/PreAuthManagerBean!com.ttk.business.preauth.PreAuthManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strAssignUsers);
		}//end of catch
		return preAuthManager;
	}//end getPreAuthManagerObject()

	/**
	 * Returns the PreAuthManager session object for invoking methods on it.
	 * @return PreAuthManager session object which can be used for method invokation
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
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strAssignUsers);
		}//end of catch
		return claimManager;
	}//end getClaimManagerObject()
	
	/**
	 * Returns the CodingManager session object for invoking methods on it.
	 * @return CodingManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private CodingManager getCodingManagerObject() throws TTKException
	{
		CodingManager codingManager = null;
		try
		{
			if(codingManager == null)
			{
				InitialContext ctx = new InitialContext();
				codingManager = (CodingManager) ctx.lookup("java:global/TTKServices/business.ejb3/CodingManagerBean!com.ttk.business.coding.CodingManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, strAssignUsers);
		}//end of catch
		return codingManager;
	}//end getCodingManagerObject()
}//end of AssignToAction