/**
 * @ (#) AccountHeadInfoAction.java Aug 12, 2008
 * Project      : Vidal Health TPA Services
 * File         : AccountHeadInfoAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : Aug 12, 2008
 *
 * @author       :  Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
package com.ttk.action.administration;

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
import com.ttk.business.administration.ProdPolicyConfigManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.AuthAcctheadVO;

public class AccountHeadInfoAction extends TTKAction
{
	private static Logger log = Logger.getLogger( AccountHeadInfoAction.class );
//	Action mapping forwards.
	private static final String strAccountHeadInfo="accountheadinfo";
	private static final String strAccountHeadClose="accountheadclose";
	
	//Exception Message Identifier
	private static final String strProduct="product";
	
	/**
	 * This method is used to display the relationships
	 * Finally it forwards to the appropriate view based on the specified forward mappings
	 *
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return ActionForward Where the control will be forwarded, after this request is processed
	 * @throws Exception if any error occurs
	 */
	public ActionForward doAccheadInfoList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
														HttpServletResponse response) throws Exception{
		try{
			log.debug("Inside the doAccheadInfoList method of AccountHeadInfoAction");
			setLinks(request);
			DynaActionForm frmAccountHeadInfo =(DynaActionForm)form;
			DynaActionForm frmProductGeneralInfo= (DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
			ArrayList<Object> alAccountHeadInfoList = new ArrayList<Object>();
			ArrayList<Object> alGroupList = new ArrayList<Object>();
			StringBuffer sbfCaption=new StringBuffer();
			ProdPolicyConfigManager productPolicyConfigObject=this.getProductPolicyConfigManagerObject();
			Long lngProdPolicySeqId=TTKCommon.getWebBoardId(request);
			alAccountHeadInfoList = productPolicyConfigObject.getAuthAcctheadList(lngProdPolicySeqId);
			if(alAccountHeadInfoList!=null && alAccountHeadInfoList.size()>0)
			{
				AuthAcctheadVO[] objAuthAcctheadArr= (AuthAcctheadVO[])alAccountHeadInfoList.toArray(new AuthAcctheadVO[0]);
				frmAccountHeadInfo.set("authaccheadlist",objAuthAcctheadArr);
			}//end of if
			alGroupList=productPolicyConfigObject.getGroupList(lngProdPolicySeqId);
			request.getSession().setAttribute("alGroupList",alGroupList);
			sbfCaption=sbfCaption.append(" [").append(frmProductGeneralInfo.getString("companyName")).append("] ")
			    .append("[").append(frmProductGeneralInfo.getString("productName")).append("]");
			frmAccountHeadInfo.set("caption",sbfCaption.toString());
			request.getSession().setAttribute("frmAccountHeadInfo",frmAccountHeadInfo);
			return this.getForward(strAccountHeadInfo, mapping, request);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doAccheadInfoList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
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
    							HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside doSave method of AccountHeadInfoAction");
    		ProdPolicyConfigManager productPolicyConfigObject=this.getProductPolicyConfigManagerObject();
    		DynaActionForm frmAccountHeadInfo =(DynaActionForm)form;
    		DynaActionForm frmProductGeneralInfo= (DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
    		StringBuffer sbfCaption= new StringBuffer();
    		ArrayList<Object> alAccHeadInfoList = new ArrayList<Object>();
    		ArrayList<Object> alAcountHeads = new ArrayList<Object>();
    		String[] strWardTypeID=request.getParameterValues("wardTypeID");
    		String[] strIncAcctheadYN=request.getParameterValues("incAcctheadYN");
            String[] strAuthGrpSeqID=request.getParameterValues("authGrpSeqID");
            String[] strShowAcctheadYN=request.getParameterValues("showAcctheadYN");
            AuthAcctheadVO[] objAuthAcctheadArr= (AuthAcctheadVO[]) frmAccountHeadInfo.get("authaccheadlist");
            for (int i = 0; i < objAuthAcctheadArr.length; i++) 
            {
            	objAuthAcctheadArr[i].setWardTypeID(strWardTypeID[i]);
            	objAuthAcctheadArr[i].setIncAcctheadYN(strIncAcctheadYN[i]);
            	objAuthAcctheadArr[i].setAuthGrpSeqID(strAuthGrpSeqID[i]);
            	objAuthAcctheadArr[i].setShowAcctheadYN(strShowAcctheadYN[i]);
            }// end of for(int i=0;i<objRelationshipInfoArr.length;i++)
            frmAccountHeadInfo.set("authaccheadlist", objAuthAcctheadArr);
			request.getSession().setAttribute("frmAccountHeadInfo",frmAccountHeadInfo);
            if(strWardTypeID!=null && strWardTypeID.length>0)
            {
            		StringBuffer sbfLimit=new StringBuffer();
                	for(int iCount=0;iCount<strWardTypeID.length;iCount++)
                    {
                		if(strIncAcctheadYN[iCount].equals("Y"))
                		{sbfLimit.append("|"+TTKCommon.checkNull(strWardTypeID[iCount])+"|"+TTKCommon.checkNull(strIncAcctheadYN[iCount])+"|"
                    			+TTKCommon.checkNull(strAuthGrpSeqID[iCount])+"|"+TTKCommon.checkNull(strShowAcctheadYN[iCount]));
                		}
                	}//end of for loop
                	sbfLimit.append("|");
                	alAccHeadInfoList.add(sbfLimit.toString());
                	alAccHeadInfoList.add(TTKCommon.getWebBoardId(request));
                	//alRelationShipList.add(null);
                	alAccHeadInfoList.add(TTKCommon.getUserSeqId(request));
            }//end of if
            if(alAccHeadInfoList!=null && alAccHeadInfoList.size()>0)
            {
            	int iCount=productPolicyConfigObject.saveSelectedAcctheadInfo(alAccHeadInfoList);
                if(iCount>0)    //if record is updated then requery
                {
                	frmAccountHeadInfo.initialize(mapping);
                	alAcountHeads = productPolicyConfigObject.getAuthAcctheadList(TTKCommon.getWebBoardId(request));
        			if(alAcountHeads!=null && alAcountHeads.size()>0)
        			{
        				frmAccountHeadInfo.set("authaccheadlist",(AuthAcctheadVO[])alAcountHeads.toArray(new AuthAcctheadVO[0]));
        			}//end of if
                	request.setAttribute("updated","message.savedSuccessfully");
                }//end of if(iCount>0)
            }//end of if(alDebitDeposit!=null && alDebitDeposit.size()>0)
            sbfCaption=sbfCaption.append(" [").append(frmProductGeneralInfo.getString("companyName")).append("] ")
		    		.append("[").append(frmProductGeneralInfo.getString("productName")).append("]");
            frmAccountHeadInfo.set("caption",sbfCaption.toString());
            return this.getForward(strAccountHeadInfo, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strProduct));
    	}//end of catch(Exception exp)
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
			log.debug("Inside the doClose method of AccountHeadInfoAction");
			setLinks(request);
			return mapping.findForward(strAccountHeadClose);
		}//end of try
		catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,strProduct));
		}//end of catch(Exception exp)
	}//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	
	/**
	 * Returns the ProdPolicyConfigManager session object for invoking methods on it.
	 * @return ProductPolicyManager session object which can be used for method invokation
	 * @exception throws TTKException
	 */
	private ProdPolicyConfigManager getProductPolicyConfigManagerObject() throws TTKException
	{
		ProdPolicyConfigManager prodPolicyConfigManager = null;
		try
		{
			if(prodPolicyConfigManager == null)
			{
				InitialContext ctx = new InitialContext();
				prodPolicyConfigManager = (ProdPolicyConfigManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProdPolicyConfigManagerBean!com.ttk.business.administration.ProdPolicyConfigManager");
			}//end if
		}//end of try
		catch(Exception exp)
		{
			throw new TTKException(exp, "product");
		}//end of catch
		return prodPolicyConfigManager;
	}//end getProductPolicyConfigManagerObject()
}//end of AccountHeadInfoAction class
