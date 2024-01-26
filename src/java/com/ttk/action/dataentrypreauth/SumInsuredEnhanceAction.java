/**
 * @ (#) SumInsuredEnhanceAction.javaMay 12, 2006
 * Project      : TTK HealthCare Services
 * File         : SumInsuredEnhanceAction.java
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : May 12, 2006
 *
 * @author       :  Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.dataentrypreauth;

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
import com.ttk.business.preauth.PreAuthManager;
import com.ttk.common.ClaimsWebBoardHelper;
import com.ttk.common.PreAuthWebBoardHelper;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;


/**
 * This class is reusable for searching list of enhanced sum insured in pre-auth and claims flow.
 */

public class SumInsuredEnhanceAction extends TTKAction
{
    private static Logger log = Logger.getLogger(SumInsuredEnhanceAction.class);

    //Action mapping forwards.
    private static final String strPreauthSumInsured="preauthsuminsured";
    private static final String strClaimsSumInsured="claimssuminsured";
    private static final String strPreauthdetail="preauthdetail";
    private static final String strClaimsdetail="claimsdetail";

    //declerations
    private static final String strPreAuthorization="Pre-Authorization";
    private static final String strClaims="Claims";

    //Exception Message Identifier
    private static final String strSumInsureError="suminsure";

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
    public ActionForward doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside SumInsuredEnhanceAction doSearch");
    		PreAuthManager preAuthObject=this.getPreAuthManagerObject();
    		String strActiveLink=TTKCommon.getActiveLink(request);
    		String strForwards="";
    		String strClaimantname="";
    		String strWebBoardDesc="";
    		StringBuffer strCaption= new StringBuffer();
    		if(strActiveLink.equals(strPreAuthorization))
    		{
    			strClaimantname=PreAuthWebBoardHelper.getClaimantName(request);
    			strWebBoardDesc=PreAuthWebBoardHelper.getWebBoardDesc(request);
    			strCaption.append("List of Sum Insured Enhancements -").append("[").append(strClaimantname).append("]").append("[").append(strWebBoardDesc).append("]");
    			strForwards=strPreauthSumInsured;
    		}//end of if(strActiveLink.equals(strPreAuthorization))
    		else if(strActiveLink.equals(strClaims))
    		{
    			strClaimantname=ClaimsWebBoardHelper.getClaimantName(request);
    			strWebBoardDesc=ClaimsWebBoardHelper.getWebBoardDesc(request);
    			strCaption.append("List of Sum Insured Enhancements -").append("[").append(strClaimantname).append("]").append("[").append(strWebBoardDesc).append("]");
    			strForwards=strClaimsSumInsured;
    		}//end of  else if(strActiveLink.equals(strClaims))
    		DynaActionForm frmSumInsuredList=(DynaActionForm)form;
    		TableData tableDataSumInsured =null;
    		//get the tbale data from session if exists
    		if(request.getSession().getAttribute("tableDataSumInsured")!=null){
    			tableDataSumInsured =(TableData)(request.getSession()).getAttribute("tableDataSumInsured");
    		}//end of if(request.getSession().getAttribute("tableDataSumInsured")!=null)
    		else{
    			tableDataSumInsured=new TableData();
    		}//end of else
    		String strPageID = TTKCommon.checkNull(request.getParameter("pageId"));
    		//if the page number is clicked
    		if(!strPageID.equals(""))
    		{
    			tableDataSumInsured.setCurrentPage(Integer.parseInt(TTKCommon.checkNull(request.getParameter("pageId"))));
    			return mapping.findForward(strForwards);
    		}///end of if(!strPageID.equals(""))
    		//create the required grid table
    		tableDataSumInsured.createTableInfo("SumInsuredEnhanceTable",null);
    		tableDataSumInsured.setSearchData(this.populateSearchCriteria(request));
    		tableDataSumInsured.modifySearchData("search");
    		ArrayList alSumInsuredList= preAuthObject.getSumInsuredEnhancementList(tableDataSumInsured.getSearchData());
    		tableDataSumInsured.setData(alSumInsuredList, "search");
    		//set the table data object to session
    		request.getSession().setAttribute("tableDataSumInsured",tableDataSumInsured);
    		frmSumInsuredList.set("caption",String.valueOf(strCaption));
    		//finally return to the grid screen
    		return this.getForward(strForwards, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strSumInsureError));
    	}//end of catch(Exception exp)
    }//end of doSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to the close screen.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug("Inside SumInsuredEnhanceAction doClose");
    		String strActiveLink=TTKCommon.getActiveLink(request);
    		String strDetails="";
    		if(strActiveLink.equals(strPreAuthorization))
    		{
    			strDetails=strPreauthdetail;
    		}//end of if(strActiveLink.equals(strPreAuthorization))
    		else if(strActiveLink.equals(strClaims))
    		{
    			strDetails=strClaimsdetail;
    		}//end of  else if(strActiveLink.equals(strClaims))
    		//get the tbale data from session if exists
    		return this.getForward(strDetails, mapping, request);   //finally return to the Preauthdetail screen
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request, mapping, expTTK);
    	}//end of catch(TTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request, mapping, new TTKException(exp, strSumInsureError));
    	}//end of catch(Exception exp)
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method will add search criteria fields and values to the arraylist and will return it
     * @param request HttpServletRequest
     * @return ArrayList contains search parameters
     * @throws TTKException
     */
    private ArrayList<Object> populateSearchCriteria(HttpServletRequest request) throws TTKException
    {
        //build the column names along with their values to be searched
        ArrayList<Object> alSearchParams = new ArrayList<Object>();
        String strActiveLink=TTKCommon.getActiveLink(request);
        if(strActiveLink.equals(strPreAuthorization))
        {
            alSearchParams.add(PreAuthWebBoardHelper.getMemberSeqId(request));
        }//end of if(strActiveLink.equals(strPreAuthorization))
        else if(strActiveLink.equals(strClaims))
        {
        	 alSearchParams.add(ClaimsWebBoardHelper.getMemberSeqId(request));
        }//end of else if(strActiveLink.equals(strClaims))
        alSearchParams.add(TTKCommon.getUserSeqId(request));
        return alSearchParams;
    }//end of populateSearchCriteria(DynaActionForm frmProductList)

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
            throw new TTKException(exp, strSumInsureError);
        }//end of catch
        return preAuthManager;
    }//end getPreAuthManagerObject()
}//end of SumInsuredEnhanceAction