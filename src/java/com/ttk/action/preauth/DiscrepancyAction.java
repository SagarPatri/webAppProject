/**
 * @ (#) DiscrepancyAction.java June 24, 2006
 * Project      : TTK HealthCare Services
 * File         : DiscrepancyAction.java
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : June 24, 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
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
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.preauth.DiscrepancyVO;

/**
 * This class is reusable for listig of Discrepancy in Pre-Auth and Claims flow.
 * This class also provides option for Resolve the discrepancy
 */

public class DiscrepancyAction extends TTKAction
{
    private static Logger log = Logger.getLogger(DiscrepancyAction.class);

    // Action mapping forwards for Pre-Auth
    private static final String strpreauthdiscrepancy="preauthdiscrepancy";

    // Action mapping forwards for Claims
    private static final String strclaimsdiscrepancy="claimsdiscrepancy";

    //Links
	private static final String strPreAuthorization="Pre-Authorization";
	private static final String strClaims="Claims";

	//Exception Message Identifier
    private static final String strDiscrepancyError="discrepancy";

	/**
     * This method is used to naviage the user to discrepancy screen if any.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
	public ActionForward doDiscrepancies(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside DiscrepancyAction doDiscrepancies");
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strDiscrepancy=null;
			if(strActiveLink.equals(strPreAuthorization))
			{
				strDiscrepancy = strpreauthdiscrepancy;
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				strDiscrepancy = strclaimsdiscrepancy;
			}//end of else if(strActiveLink.equals(strClaims))
			//Building the caption
			StringBuffer strCaption= new StringBuffer();
			DynaActionForm frmDiscrepancy= (DynaActionForm) form;
			PreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			ArrayList<Object> alDiscrepancyList = new ArrayList<Object>();
			strCaption.append("Discrepancies  -");			
			if(strActiveLink.equals(strPreAuthorization))
			{
				alDiscrepancyList.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				alDiscrepancyList.add(null);
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+PreAuthWebBoardHelper.getClaimantName(request)+ " ]");
					strCaption.append(" [ "+TTKCommon.getWebBoardDesc(request)+ " ]");
					strCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				alDiscrepancyList.add(null);
				alDiscrepancyList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+ClaimsWebBoardHelper.getClaimantName(request)+ " ]");
					strCaption.append(" [ "+TTKCommon.getWebBoardDesc(request)+ " ]");
					strCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}
			}//end of else if(strActiveLink.equals(strClaims))
			alDiscrepancyList.add(TTKCommon.getUserSeqId(request));
			ArrayList alDiscrepancy =preAuthManagerObject.getDiscrepancyInfo(alDiscrepancyList);
			request.setAttribute("alDiscrepancy",alDiscrepancy);
			
			frmDiscrepancy.set("caption",strCaption.toString());
			request.setAttribute("frmDiscrepancy",frmDiscrepancy);
			return this.getForward(strDiscrepancy, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strDiscrepancyError));
		}//end of catch(Exception exp)
	}//end of doDiscrepancies(ActionMapping mapping,ActionForm form,HttpServletRequest request,
		//HttpServletResponse response)

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
														HttpServletResponse response) throws Exception {
		log.debug("Inside DiscrepancyAction doReset");
        return doDiscrepancies(mapping,form,request,response);
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	 //HttpServletResponse response) throws Exception

	/**
	 * This method is used to Resolve Discrepancy.
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doResolveDiscrepancy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception {
		try
		{
			setLinks(request);
			log.debug("Inside DiscrepancyAction doResolveDiscrepancy");
			String strActiveLink=TTKCommon.getActiveLink(request);
			String strDiscrepancy=null;
			if(strActiveLink.equals(strPreAuthorization))
			{
				strDiscrepancy = strpreauthdiscrepancy;
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				strDiscrepancy = strclaimsdiscrepancy;
			}//end of else if(strActiveLink.equals(strClaims))
			//Building the caption
			StringBuffer strCaption= new StringBuffer();
			DiscrepancyVO discrepancyVO=null;
			DynaActionForm frmDiscrepancy= (DynaActionForm) form;
			PreAuthManager preAuthManagerObject=this.getPreAuthManagerObject();
			ArrayList<Object> alDiscrepancyVO = new ArrayList<Object>();
			String[] strDiscSeqIds=request.getParameterValues("discrepancySeqID");
			String[] strResolvedYN=request.getParameterValues("resolvedYN");
			if(strDiscSeqIds!=null && strDiscSeqIds.length>0)
			{
				for(int iCount=0;iCount<strDiscSeqIds.length;iCount++)
				{
					discrepancyVO=new DiscrepancyVO();
					discrepancyVO.setDiscrepancySeqID(TTKCommon.checkNull(strDiscSeqIds[iCount]).equals("")?
																			null:new Long(strDiscSeqIds[iCount]));
					discrepancyVO.setResolvedYN(TTKCommon.checkNull(strResolvedYN[iCount]));
					discrepancyVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
					alDiscrepancyVO.add(discrepancyVO);
				}//end of for(int iCount=0;iCount<strDiscSeqIds.length;iCount++)
			}//end of if(strDiscSeqIds!=null && strDiscSeqIds.length>0)
			int iResult=preAuthManagerObject.resolveDiscrepancy(alDiscrepancyVO);
			if(iResult>0)
			{
				request.setAttribute("updated","message.resolvedSuccessfully");
			}//end of if(iResult>0)
			ArrayList<Object> alDiscrepancyList = new ArrayList<Object>();
			strCaption.append("Discrepancies  - [ ").append(TTKCommon.getWebBoardDesc(request)).append(" ]");
			if(strActiveLink.equals(strPreAuthorization))
			{
				alDiscrepancyList.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				alDiscrepancyList.add(null);
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				alDiscrepancyList.add(null);
				alDiscrepancyList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}//end of if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
			}//end of else if(strActiveLink.equals(strClaims))
			alDiscrepancyList.add(TTKCommon.getUserSeqId(request));
			ArrayList alDiscrepancy =preAuthManagerObject.getDiscrepancyInfo(alDiscrepancyList);
			request.setAttribute("alDiscrepancy",alDiscrepancy);
			
			frmDiscrepancy.set("caption",strCaption.toString());
			request.setAttribute("frmDiscrepancy",frmDiscrepancy);
			return this.getForward(strDiscrepancy, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request,mapping,expTTK);
		}//end of catch(ETTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request,mapping,new TTKException(exp,strDiscrepancyError));
		}//end of catch(Exception exp)
	}//end of doResolveDiscrepancy(ActionMapping mapping,ActionForm form,HttpServletRequest request,
	 //HttpServletResponse response)

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
            }//end of if(preAuthManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, strDiscrepancyError);
        }//end of catch
        return preAuthManager;
    }//end getPreAuthManagerObject()
}// end of DiscrepancyAction