package com.ttk.action.onlineforms.insuranceLogin;

/**
 * @ (#) InsuranceLoginAction.java Mar 24, 2008
 * Project      : TTK HealthCare Services
 * File         : InsuranceLoginAction.java
 * Author       : Balakrishna E
 * Company      : Span Systems Corporation
 * Date Created : Mar 24, 2008
 *
 * @author       :  Balakrishna E
 * Modified by   :
 * Modified date :
 * Reason        :
 */

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
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.onlineforms.OnlineInsPolicyVO;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This class is used for Searching the List Policies to see the Online Account Info.
 * This also provides deletion and updation of products.
 */
public class InsuranceLoginAction extends TTKAction {

    private static final Logger log = Logger.getLogger( InsuranceLoginAction.class );

    //Modes.
    private static final String strBackward="Backward";
    private static final String strForward="Forward";

    // Action mapping forwards.
    private static final String strOnlinePolicyList="onlinepolicylist";
    private static final String strCorporateList="corporatelist";
    private static final String strLogdetails = "logdetails";
    //Exception Message Identifier
    private static final String strOnlineAccInfo="onlineaccountinfo";

    private static final String strOnlineInsDashBoard="insDashBoard";
    

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
            log.info("Inside the doDefault method of InsuranceLoginAction");
            setOnlineLinks(request);
            TableData tableData =null;

            DynaActionForm frmOnlineAccountInfo =(DynaActionForm)form;
            frmOnlineAccountInfo.initialize(mapping);     //reset the form data
            //get the tbale data from session if exists
            tableData =TTKCommon.getTableData(request);
            //create new table data object
            tableData = new TableData();
            //create the required grid table
            tableData.createTableInfo("OnlineAccountInfoTable",new ArrayList());
            request.getSession().setAttribute("tableData",tableData);
            //((DynaActionForm)form).initialize(mapping);//reset the form data
            return this.getForward(strOnlinePolicyList, mapping, request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
        }//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
    
    
    
    public ActionForward doCorporate(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doCorporate method of InsuranceLoginAction");
            setOnlineLinks(request);

            DynaActionForm frmOnlineAccountInfo =(DynaActionForm)form;
            frmOnlineAccountInfo.initialize(mapping);     //reset the form data
            //((DynaActionForm)form).initialize(mapping);//reset the form data
           // return this.getForward("insLoginCorp", mapping, request);
            return mapping.findForward("insLoginCorp");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
        }//end of catch(Exception exp)
    }//end of doDashBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
    
    
    public ActionForward doCorporateGlobal(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doCorporateGlobal method of InsuranceLoginAction");
            setOnlineLinks(request);

            DynaActionForm frmOnlineAccountInfo =(DynaActionForm)form;
            frmOnlineAccountInfo.initialize(mapping);     //reset the form data
            //((DynaActionForm)form).initialize(mapping);//reset the form data
           // return this.getForward("insLoginCorp", mapping, request);
            return mapping.findForward("insCorpGlobal");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
        }//end of catch(Exception exp)
    }//end of doCorporateGlobal(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
    
    
     public ActionForward doFocusedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doFocusedView method of InsuranceLoginAction");
            setOnlineLinks(request);

            DynaActionForm frmOnlineAccountInfo =(DynaActionForm)form;
            frmOnlineAccountInfo.initialize(mapping);     //reset the form data
            //((DynaActionForm)form).initialize(mapping);//reset the form data
           // return this.getForward("insLoginCorp", mapping, request);
            return mapping.findForward("focusedView");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
        }//end of catch(Exception exp)
    }//end of doFocusedView(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
    
     
      public ActionForward doFocusProceed(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doFocusProceed method of InsuranceLoginAction");
            setOnlineLinks(request);

            DynaActionForm frmOnlineAccountInfo =(DynaActionForm)form;
            frmOnlineAccountInfo.initialize(mapping);     //reset the form data
            //((DynaActionForm)form).initialize(mapping);//reset the form data
           // return this.getForward("insLoginCorp", mapping, request);
            return mapping.findForward("focusedViewProceed");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
        }//end of catch(Exception exp)
    }//end of doFocusProceed(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
     
      
      public ActionForward doProducts(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doProducts method of InsuranceLoginAction");
            setOnlineLinks(request);

            DynaActionForm frmOnlineAccountInfo =(DynaActionForm)form;
            frmOnlineAccountInfo.initialize(mapping);     //reset the form data
            return mapping.findForward("productsAndPolicies");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
        }//end of catch(Exception exp)
    }//end of doProducts(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
      
      
      
       public ActionForward doRetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doRetail method of InsuranceLoginAction");
            setOnlineLinks(request);

            DynaActionForm frmOnlineAccountInfo =(DynaActionForm)form;
            frmOnlineAccountInfo.initialize(mapping);     //reset the form data
            return mapping.findForward("productRetail");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
        }//end of catch(Exception exp)
    }//end of doRetail(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
       
       
       public ActionForward doRetailGlobal(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doRetailGlobal method of InsuranceLoginAction");
            setOnlineLinks(request);

            DynaActionForm frmOnlineAccountInfo =(DynaActionForm)form;
            frmOnlineAccountInfo.initialize(mapping);     //reset the form data
            return mapping.findForward("retailGlobal");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
        }//end of catch(Exception exp)
    }//end of doRetailGlobal(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
       
       
       
        public ActionForward doRetailFocused(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doRetailFocused method of InsuranceLoginAction");
            setOnlineLinks(request);

            DynaActionForm frmOnlineAccountInfo =(DynaActionForm)form;
            frmOnlineAccountInfo.initialize(mapping);     //reset the form data
            return mapping.findForward("retailFocused");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
        }//end of catch(Exception exp)
    }//end of doRetailFocused(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
        
        
        
          public ActionForward doRetailBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doRetailBack method of InsuranceLoginAction");
            setOnlineLinks(request);

            DynaActionForm frmOnlineAccountInfo =(DynaActionForm)form;
            frmOnlineAccountInfo.initialize(mapping);     //reset the form data
            return mapping.findForward("productRetail");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
        }//end of catch(Exception exp)
    }//end of doRetailBack(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
        
          
            
          public ActionForward doInPatient(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doInPatient method of InsuranceLoginAction");
            setOnlineLinks(request);

            DynaActionForm frmOnlineAccountInfo =(DynaActionForm)form;
            frmOnlineAccountInfo.initialize(mapping);     //reset the form data
            return mapping.findForward("retailInPatient");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
        }//end of catch(Exception exp)
    }//end of doInPatient(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
        
          
          
          
          public ActionForward doDental(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doDental method of InsuranceLoginAction");
            setOnlineLinks(request);

            DynaActionForm frmOnlineAccountInfo =(DynaActionForm)form;
            frmOnlineAccountInfo.initialize(mapping);     //reset the form data
            return mapping.findForward("retailDental");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
        }//end of catch(Exception exp)
    }//end of doDental(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
        
          
      public ActionForward doReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,
                                                     HttpServletResponse response) throws Exception{
        try{
            log.info("Inside the doReports method of InsuranceLoginAction");
            setOnlineLinks(request);

            DynaActionForm frmOnlineAccountInfo =(DynaActionForm)form;
            frmOnlineAccountInfo.initialize(mapping);     //reset the form data
            return mapping.findForward("reports");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processOnlineExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processOnlineExceptions(request, mapping, new TTKException(exp,strOnlineAccInfo));
        }//end of catch(Exception exp)
    }//end of doReports(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            //HttpServletResponse response)
        
          
}//end of InsuranceLoginAction
