
/**
 * @ (#) CircularAction.javaNov 8, 2005
 * Project      : TTK HealthCare Services
 * File         : CircularAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Nov 8, 2005
 *
 * @author       :  Chandrasekaran J
 * Modified by   :  Ramakrishna K M
 * Modified date :  Mar 13, 2006
 * Reason        :  Used Form-Utils
 * Modified by   :  Lancy A
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
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.CircularVO;
import com.ttk.dto.administration.ConfigCopayVO;
import com.ttk.dto.administration.ProductVO;
import com.ttk.dto.common.SearchCriteria;
import com.ttk.dto.preauth.InsOverrideConfVO;
import com.ttk.dto.preauth.UnfreezeUploadFileVO;

import formdef.plugin.util.FormUtils;


/**
 * This class is used for searching of List of Circulars.
 * This class also provides option for deletion,updation of Circulars .
 */
public class InsuranceOverrideAction extends TTKAction
{
	private static Logger log = Logger.getLogger( InsuranceOverrideAction.class );    
	
	private static final String strSuccess="success";
	private static final String strSuccess1="success1";
	private static final String strFailure="fail";
	private static final String strPreAuth="preauth";
	private static final String strPreAuthorization="Pre-Authorization";
	private static final String strClaims="Claims";
	
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
			log.debug("Inside the doDefault method of UploadFileAction");

			setLinks(request);
			DynaActionForm frmInsOverrideConf= (DynaActionForm)form;
			PreAuthManager preAuthManager=this.getPreAuthManagerObject();
			/*OnlineAccessManager onlineAccessObject=this.getOnlineAccessManager();
			TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;*/
			InsOverrideConfVO insOverrideConfVO=new InsOverrideConfVO();
			String strActiveLink=TTKCommon.getActiveLink(request);
            String Mode="";
            Long  lngProdPolicySeqId=null;
			//Long lngPolicyGroupSeqID=null;
			ArrayList<Object> alFileAUploadList = new ArrayList<Object>();
			StringBuffer strCaption=new StringBuffer();

			strCaption.append("Insurance Override Conf    -");			
			if(strActiveLink.equals(strPreAuthorization))
			{
				alFileAUploadList.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				alFileAUploadList.add(null);
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+PreAuthWebBoardHelper.getClaimantName(request)+ " ]");
					strCaption.append(" [ "+TTKCommon.getWebBoardDesc(request)+ " ]");
					strCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				alFileAUploadList.add(null);
				alFileAUploadList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+ClaimsWebBoardHelper.getClaimantName(request)+ " ]");
					strCaption.append(" [ "+TTKCommon.getWebBoardDesc(request)+ " ]");
					strCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}
			}//end of else if(strActiveLink.equals(strClaims))
			
			if(strActiveLink.equals(strPreAuthorization))
			{
			    Mode="PRE";	
		    lngProdPolicySeqId = PreAuthWebBoardHelper.getPreAuthSeqId(request);
			}
			else if(strActiveLink.equals(strClaims))
			{
		    lngProdPolicySeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
			    Mode="CLM";
			}
			
			insOverrideConfVO = preAuthManager.getConfigDetails(lngProdPolicySeqId, Mode);
			frmInsOverrideConf = (DynaActionForm)FormUtils.setFormValues("frmInsOverrideConf",insOverrideConfVO,
				     this,mapping,request);
			frmInsOverrideConf.set("caption",strCaption.toString());
			//frmInsOverrideConf.set("remarks","");
			request.getSession().setAttribute("frmInsOverrideConf",frmInsOverrideConf);

			return mapping.findForward(strSuccess);
			//return this.getForward(strSuccess, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strPreAuth));
		}//end of catch(Exception exp)
	}//end of Default(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	
	
	public ActionForward doInsOverrideConfPatClm(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws TTKException{
		try{
			log.debug("Inside the doUnfreezePatClm method of UploadFileAction");
			setLinks(request);
			DynaActionForm frmInsOverrideConf= (DynaActionForm)form;
			PreAuthManager preAuthManager=this.getPreAuthManagerObject(); 
			String strActiveLink=TTKCommon.getActiveLink(request);
			StringBuffer strCaption=new StringBuffer();
			long lngFileData=0;
			String updated="";
			InsOverrideConfVO insOverrideConfVO=null;

			ArrayList<Object> alFileAUploadList = new ArrayList<Object>();
			insOverrideConfVO =(InsOverrideConfVO)FormUtils.getFormValues(frmInsOverrideConf, "frmInsOverrideConf",this, mapping, request);
			strCaption.append("Insurance Override Conf  -");			
			if(strActiveLink.equals(strPreAuthorization))
			{
				alFileAUploadList.add(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				alFileAUploadList.add(null);
				
				if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+PreAuthWebBoardHelper.getClaimantName(request)+ " ]");
					strCaption.append(" [ "+TTKCommon.getWebBoardDesc(request)+ " ]");
					strCaption.append(" [ "+PreAuthWebBoardHelper.getEnrollmentId(request)+ " ]");
				}
				insOverrideConfVO.setPatClmSeqID(PreAuthWebBoardHelper.getPreAuthSeqId(request));
				insOverrideConfVO.setMode("PRE");
			}//end of if(strActiveLink.equals(strPreAuthorization))
			else if(strActiveLink.equals(strClaims))
			{
				alFileAUploadList.add(null);
				alFileAUploadList.add(ClaimsWebBoardHelper.getClaimsSeqId(request));
				//alFileAUploadList.add("CLM");
				if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim())))
				{
					strCaption.append(" [ "+ClaimsWebBoardHelper.getClaimantName(request)+ " ]");
					strCaption.append(" [ "+TTKCommon.getWebBoardDesc(request)+ " ]");
					strCaption.append(" [ "+ClaimsWebBoardHelper.getEnrollmentId(request)+ " ]");
				}
				insOverrideConfVO.setPatClmSeqID(ClaimsWebBoardHelper.getClaimsSeqId(request));
				insOverrideConfVO.setMode("CLM");
			}//end of else if(strActiveLink.equals(strClaims))
			/*alFileAUploadList.add(TTKCommon.getUserSeqId(request));

			lngFileData=preAuthManager.accountinsOverrideConf(alFileAUploadList);*/
			insOverrideConfVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
			int iResult = preAuthManager.saveOverrideConfigDetails(insOverrideConfVO);
			/*if(lngFileData>0)
			{
				updated="PreAuth/Claim Updates Successfully";
			        insOverrideConfVO.setOverrideYN("N");
				insOverrideConfVO.setRemarks("");
				insOverrideConfVO.setFile(null);
			}
*/
			if(iResult>0)
			{
				request.setAttribute("updated","Updated Successfully");
			}//end of if(iResult>0)
		//	frmInsOverrideConf.set("updated",updated);
			 Long  lngProdPolicySeqId=null;
			 String Mode="";
			if(strActiveLink.equals(strPreAuthorization))
			{
			    Mode="PRE";	
		    lngProdPolicySeqId = PreAuthWebBoardHelper.getPreAuthSeqId(request);
			}
			else if(strActiveLink.equals(strClaims))
			{
		    lngProdPolicySeqId = ClaimsWebBoardHelper.getClaimsSeqId(request);
			    Mode="CLM";
			}
			insOverrideConfVO = preAuthManager.getConfigDetails(lngProdPolicySeqId, Mode);
			frmInsOverrideConf = (DynaActionForm)FormUtils.setFormValues("frmInsOverrideConf",insOverrideConfVO,this,mapping,request);
			request.getSession().setAttribute("frmInsOverrideConf", frmInsOverrideConf);
			//request.setAttribute("updated",updated);
			frmInsOverrideConf.set("caption",strCaption.toString());
			return mapping.findForward(strSuccess);
			
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processOnlineExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processOnlineExceptions(request, mapping, new TTKException(exp,strPreAuth));
		}//end of catch(Exception exp)
	}//end of Close(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

	
	/**
	 * Returns a string which contains the comma separated sequence id's to be deleted  
	 * @param request HttpServletRequest object which contains information about the selected check boxes
	 * @param tableData TableData object which contains the value objects
	 * @return String which contains the comma separated sequence id's to be deleted  
	 */
	private String populateDeleteId(HttpServletRequest request, TableData tableData)
	{ 
		String[] strChk = request.getParameterValues("chkopt");
		StringBuffer sbfDeleteId = new StringBuffer();
		if(strChk!=null&&strChk.length!=0)
		{
			//loop through to populate delete sequence id's and get the value from session for the matching check box value
			for(int i=0; i<strChk.length;i++)
			{
				if(strChk[i]!=null)
				{    
					//extract the sequence id to be deleted from the value object
					if(i == 0)
					{
						sbfDeleteId.append(String.valueOf(((CircularVO)tableData.getData().
							get(Integer.parseInt(strChk[i]))).getCircularSeqId().intValue()));
					}//end of if(i == 0)
					else
					{
						sbfDeleteId = sbfDeleteId.append("|").append(String.valueOf(((CircularVO)tableData.
								getData().get(Integer.parseInt(strChk[i]))).getCircularSeqId().intValue()));
					}//end of else
				}//end of if(strChk[i]!=null)
			}//end of for(int i=0; i<strChk.length;i++) 
		}//end of if(strChk!=null&&strChk.length!=0) 
		return sbfDeleteId.toString();
	}//end of populateDeleteId(HttpServletRequest request, TableData tableData)
	
	/**
	 * This method builds all the search parameters to ArrayList and places them in session 
	 * @param searchCircularForm DynaActionForm
	 * @param request HttpServletRequest
	 * @return alSearchParams ArrayList
	 */
	private ArrayList<Object> populateSearchCriteria(DynaActionForm searchCircularForm,HttpServletRequest request,
																	 Long lProdSeqID) throws TTKException
	{
		DynaActionForm formOffice = (DynaActionForm)request.getSession().getAttribute("frmOffice");
		//build the column names along with their values to be searched
		ArrayList<Object> alSearchParams = new ArrayList<Object>();
		alSearchParams.add(new SearchCriteria("INS_SEQ_ID",formOffice.get("insSeqID").toString()));
		alSearchParams.add(new SearchCriteria("PRODUCT_SEQ_ID",lProdSeqID.toString()));
		alSearchParams.add(new SearchCriteria("CIRCULAR_ISSUED_DATE", (String)searchCircularForm.
																			  get("startdate")));
		alSearchParams.add(new SearchCriteria("CIRCULAR_RECEIVED_DATE", (String)searchCircularForm.get("enddate")));
		return alSearchParams;
	}//end of populateSearchCriteria(DynaActionForm searchCircularForm,HttpServletRequest request,
		//Long lProdSeqID) throws TTKException
	
	/**
	 * Returns the ProductPolicyManager session object for invoking methods on it.
	 * @return ProductPolicyManager session object which can be used for method invokation 
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
			throw new TTKException(exp, "strFileUpload");
		}//end of catch
		return preAuthManager;
	}//end getPreAuthManagerObject()
	
	
}//end of  CircularAction class