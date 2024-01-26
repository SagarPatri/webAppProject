/**
 * @ (#) SelectUserAction.java June 09, 2006
 * Project 		: TTK HealthCare Services
 * File 		: SelectUserAction.java
 * Author 		: Lancy A
 * Company 		: Span Systems Corporation
 * Date Created : June 09, 2006
 *
 * @author 		 : Lancy A
 * Modified by 	 :
 * Modified date :
 * Reason 		 :
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
import com.ttk.action.table.TableData;
import com.ttk.business.finance.BankAccountManager;
import com.ttk.business.usermanagement.UserManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.finance.AuthorisedVO;

import formdef.plugin.util.FormUtils;

/**
 * This class is used for displaying the  selected users.
 * This class also provides option for Addition and Updation of users.
 */
public class SelectUserAction extends TTKAction
{
	private static Logger log = Logger.getLogger(SelectUserAction.class );
	//Action mapping
    private static final String strSelectUser="selectuser";
    private static final String strCloseSelectUser="closeselectuser";
    //  Exception Message Identifier
    private static final String strUser="support";
    
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
    public ActionForward doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    						   HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug(" Inside SelectUserAction doAdd");
    		AuthorisedVO authorisedVO = null;
    		DynaActionForm frmSelectUser= (DynaActionForm)form;
    		frmSelectUser.initialize(mapping);
    		authorisedVO = new AuthorisedVO();
    		authorisedVO.setBankAcctSeqID(TTKCommon.getWebBoardId(request));
    		frmSelectUser= (DynaActionForm)FormUtils.setFormValues("frmSelectUser", authorisedVO, this, 
    																mapping, request);
    		request.getSession().setAttribute("frmSelectUser",frmSelectUser);
    		return this.getForward(strSelectUser, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strUser));
    	}//end of catch(Exception exp)
    }//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    public ActionForward doViewUser(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    								HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug(" Inside SelectUserAction doViewUser");
    		DynaActionForm frmSelectUser= (DynaActionForm)form;
    		AuthorisedVO authorisedVO = null;
    		TableData tableData = (TableData) request.getSession().getAttribute("tableData");
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		authorisedVO = (AuthorisedVO)tableData.getRowInfo(Integer.parseInt(request.getParameter("rownum")));
    		authorisedVO = bankAccountManagerObject.getSignatorieDetail(authorisedVO.getAuthSeqID(), 
    											 TTKCommon.getWebBoardId(request), TTKCommon.getUserSeqId(request));
    		frmSelectUser= (DynaActionForm)FormUtils.setFormValues("frmSelectUser", authorisedVO, this, mapping, 
    																request);
    		request.getSession().setAttribute("frmSelectUser",frmSelectUser);
    		return this.getForward(strSelectUser, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strUser));
    	}//end of catch(Exception exp)
    }//end of doViewUser(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug(" Inside SelectUserAction doSave");
        	DynaActionForm frmSelectUser= (DynaActionForm)form;
        	AuthorisedVO authorisedVO = null;
        	BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
			authorisedVO = (AuthorisedVO)FormUtils.getFormValues(frmSelectUser, this, mapping, request);
			authorisedVO.setUpdatedBy(TTKCommon.getUserSeqId(request));	//User Id
			Long lCount=bankAccountManagerObject.saveSignatories(authorisedVO);
            if(lCount>0)
            {
            	if(authorisedVO.getAuthSeqID()!=null)
				{
            		request.setAttribute("updated","message.savedSuccessfully");
            		authorisedVO = bankAccountManagerObject.getSignatorieDetail(authorisedVO.getAuthSeqID(), 
            									 TTKCommon.getWebBoardId(request), TTKCommon.getUserSeqId(request));
				}//end of if(authorisedVO.getAuthSeqID()!=null)
                else
				{
					request.setAttribute("updated","message.addedSuccessfully");
					frmSelectUser.initialize(mapping);
					authorisedVO = new AuthorisedVO();
					authorisedVO.setBankAcctSeqID(TTKCommon.getWebBoardId(request));
				}//end of else
            }//end of if(lCount>0)
            frmSelectUser = (DynaActionForm)FormUtils.setFormValues("frmSelectUser", authorisedVO, this, mapping, 
            														 request);
        	request.getSession().setAttribute("frmSelectUser",frmSelectUser);
            return this.getForward(strSelectUser, mapping, request);
		}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strUser));
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
    		setLinks(request);
    		log.debug(" Inside SelectUserAction doReset");
    		AuthorisedVO authorisedVO = null;
    		BankAccountManager bankAccountManagerObject=this.getBankAccountManagerObject();
    		authorisedVO=new AuthorisedVO();
    		DynaActionForm frmSelectUser= (DynaActionForm)form;
    		if(frmSelectUser.get("authSeqID")!=null && !frmSelectUser.get("authSeqID").equals(""))
    		{
    			authorisedVO = bankAccountManagerObject.getSignatorieDetail(TTKCommon.getLong((String)frmSelectUser.get("authSeqID")), 
    											  TTKCommon.getWebBoardId(request), TTKCommon.getUserSeqId(request));
    		}//end of if(frmSelectUser.get("authSeqID")!=null && !frmSelectUser.get("authSeqID").equals(""))
    		else
    		{
    			frmSelectUser.initialize(mapping);
    			authorisedVO=new AuthorisedVO();
    			authorisedVO.setBankAcctSeqID(TTKCommon.getWebBoardId(request));
    		}//end of else
    		frmSelectUser = (DynaActionForm)FormUtils.setFormValues("frmSelectUser", authorisedVO, this, 
    																mapping, request);
    		request.getSession().setAttribute("frmSelectUser",frmSelectUser);
    		return this.getForward(strSelectUser, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strUser));
    	}//end of catch(Exception exp)
    }//end of doReset(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
    
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
    		log.debug(" Inside SelectUserAction doClose");
    		TableData tableData = (TableData) request.getSession().getAttribute("tableData");
    		UserManager userManagerObject = this.getUserManagerObject();
    		if(tableData.getSearchData().size()>1)
    		{
    			ArrayList alUserList = userManagerObject.getSignatoryList(tableData.getSearchData());
    			tableData.setData(alUserList, "search");
    			//set the table data object to session
    			request.getSession().setAttribute("tableData",tableData);
    		}//end of if(tableData.getSearchData().size()>1)
    		return this.getForward(strCloseSelectUser, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strUser));
    	}//end of catch(Exception exp)	
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    public ActionForward doSelectUserList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    									  HttpServletResponse response) throws Exception{
    	try{
    		setLinks(request);
    		log.debug(" Inside SelectUserAction doSelectUserList");
    		return mapping.findForward("userslist");
    	}
    	catch(TTKException expTTK)
    	{
    		return this.processExceptions(request,mapping,expTTK);
    	}//end of catch(ETTKException expTTK)
    	catch(Exception exp)
    	{
    		return this.processExceptions(request,mapping,new TTKException(exp,strUser));
    	}//end of catch(Exception exp)	
    }//end of doSelectUserList(ActionMapping mapping,ActionForm form,HttpServletRequest request,
    																	//HttpServletResponse response)
    /**
     * Returns the BankAccountManager session object for invoking methods on it.
     * @return BankAccountManager session object which can be used for method invokation
     * @exception throws TTKException
     */

    private BankAccountManager getBankAccountManagerObject() throws TTKException
    {
    	BankAccountManager bankAccountManager = null;
        try
        {
            if(bankAccountManager == null)
            {
                InitialContext ctx = new InitialContext();
                bankAccountManager = (BankAccountManager) ctx.lookup("java:global/TTKServices/business.ejb3/BankAccountManagerBean!com.ttk.business.finance.BankAccountManager");
            }//end of if(bankAccountManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "bank");
        }//end of catch
        return bankAccountManager;
    }//end getBankAccountManagerObject()

    /**
     * Returns the UserManager session object for invoking methods on it.
     * @return UserManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private UserManager getUserManagerObject() throws TTKException
    {
    	UserManager userManager = null;
    	try
        {
            if(userManager == null)
            {
                InitialContext ctx = new InitialContext();
                userManager = (UserManager) ctx.lookup("java:global/TTKServices/business.ejb3/UserManagerBean!com.ttk.business.usermanagement.UserManager");
            }//end if(userManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "userlist");
        }//end of catch
        return userManager;
    }//end getUserManagerObject()
}// end of SelectUserAction.java