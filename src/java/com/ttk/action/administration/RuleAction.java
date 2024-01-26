/**
 * @ (#) RuleAction.java Jul 8, 2006
 * Project      : TTK HealthCare Services
 * File         : RuleAction.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jul 8, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.action.administration;

import java.io.Reader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;
import org.dom4j.Node;

import com.ttk.action.TTKAction;
import com.ttk.action.table.TableData;
import com.ttk.business.administration.ProductPolicyManager;
import com.ttk.business.administration.RuleManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.common.security.RuleXMLHelper;
import com.ttk.dto.administration.ProductVO;
import com.ttk.dto.administration.RuleVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.enrollment.PolicyDetailVO;
import com.ttk.xml.administration.Benefit;
import com.ttk.xml.administration.Condition;
import com.ttk.xml.administration.ProviderRules;

import formdef.plugin.util.FormUtils;

public class RuleAction extends TTKAction {
    private static Logger log = Logger.getLogger(RuleAction.class); // Getting Logger for this Class file

    //declaration of forward paths
    private static final String strProductClauseList="productclauselist";
    private static final String strPolicyClauseList="policyclauselist";
    private static final String strProductRulelist="productrulelist";
    private static final String strPolicyRule="policyrule";
    private static final String strProductrule="productrule";
    private static final String strProductRuleVerification="productruleverification";
    private static final String strPolicyRuleVerification="policyruleverification";
    private static final String strFailure="failure";
    
 private static final String strNewProductRules="NewProductRules";
    
    private static final String strNewMOHProductRules="NewMOHProductRules";
  

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
            StringBuffer sbfCaption=new StringBuffer();
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            TableData tableData=TTKCommon.getTableData(request);
            Document ruleDocument = null;       //refernce to Rule which will be processed for Product/Policy
            RuleVO ruleVO=null;
            RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();     //create the instance of Helper
            
            //check for the web board ID
            if(TTKCommon.getWebBoardId(request)==null)
            {
                TTKException expTTK = new TTKException();
                if(strActiveSubLink.equals("Products"))
                {
                    expTTK.setMessage("error.product.required");
                }//end of if(strActiveSubLink.equals("Products"))
                else if(strActiveSubLink.equals("Policies"))
                {
                    expTTK.setMessage("error.policy.required");
                }//end of else if(strActiveSubLink.equals("Policies"))
                throw expTTK;
            }//end of if(TTKCommon.getWebBoardId(request)==null)

            //load the Display nodes to Application scope
            ServletContext sc=servlet.getServletContext();
            HashMap hmDisplayNodes=null;
            HashMap hmCopayResultNodes=null;
            if(sc.getAttribute("RULE_DISPLAY_NODES")==null)
            {
                hmDisplayNodes=ruleXMLHelper.loadDisplayNodes(TTKCommon.getDocument("MasterBaseRules.xml"));
                sc.setAttribute("RULE_DISPLAY_NODES",hmDisplayNodes);
            }//end of if(sc.getAttribute("RULE_DISPLAY_NODES")==null)
            else
            {
                hmDisplayNodes=(HashMap)sc.getAttribute("RULE_DISPLAY_NODES");
            }//end of else
            
            if(sc.getAttribute("RULE_COPAY_RESULT_NODES")==null)
            {
            	hmCopayResultNodes=ruleXMLHelper.loadDisplayNodes(TTKCommon.getDocument("MasterBaseRules.xml"));
                sc.setAttribute("RULE_COPAY_RESULT_NODES",hmCopayResultNodes);
            }//end of if(sc.getAttribute("RULE_COPAY_RESULT_NODES")==null)
            else
            {
            	hmCopayResultNodes=(HashMap)sc.getAttribute("RULE_COPAY_RESULT_NODES");
            }//end of else
            
            //remove the unwanted data from the session
            request.getSession().removeAttribute("tableData");
            request.getSession().removeAttribute("RuleDocument");

            RuleManager ruleManagerObject=this.getRuleManagerObject();

            //get the caption to be displayed
            sbfCaption.append(buildCaption(strActiveSubLink,request));
            
            if(TTKCommon.getActiveSubLink(request).equals("Products"))
            {
                DynaActionForm frmRules= (DynaActionForm)form;
                //get the Rules for the Product
                ArrayList alProductRule=ruleManagerObject.getProductRuleList(TTKCommon.getWebBoardId(request));
                String authType=  ruleManagerObject.getProductAuthority(TTKCommon.getWebBoardId(request));
                //create the tableData for the Rules
                tableData = new TableData();
                tableData.createTableInfo("ProductRulesTable",null);
                tableData.setData(alProductRule,"search");
                request.getSession().setAttribute("tableData",tableData);
                frmRules.set("caption",(sbfCaption.substring(0, sbfCaption.indexOf("[", 2))).toString()+"["+authType+"]");
                return this.getForward(strProductRulelist,mapping,request);
            }//end of if(TTKCommon.getActiveSubLink(request).equals("Products"))
            else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
            {
                DynaActionForm frmDefineRules= (DynaActionForm)form;
                //call the DAO to get the current policyRule
                ruleVO=ruleManagerObject.getProdPolicyRule(TTKCommon.getWebBoardId(request),"P");
                if(ruleVO==null)
                {
                    TTKException expTTK = new TTKException();
                    expTTK.setMessage("error.administration.policyrule.notdefined");
                    throw expTTK;
                }//end of if(ruleVO==null)
                
                
                
                ruleDocument=ruleVO.getRuleDocument();
               
                frmDefineRules = (DynaActionForm)FormUtils.setFormValues("frmDefineRules",ruleVO, this, mapping, request);
                //sbfCaption.append("[").append(policyDetailVO.getPolicyNbr()).append("]");   //build caption
                frmDefineRules.set("caption",(sbfCaption.substring(0, sbfCaption.indexOf("[", 2))).toString()+"["+ruleVO.getProPolAuthority()+"]");
                request.getSession().setAttribute("frmDefineRules",frmDefineRules);
                
                if("MOH".equals(ruleVO.getProPolAuthority())){
               	   return mapping.findForward(strNewMOHProductRules);
                  }
                
                request.getSession().setAttribute("RuleDocument",ruleDocument);
                request.getSession().setAttribute("ProviderRulesXmlConditions",getProviderCopayRulesUnmarshaller(ruleVO.getProviderCopayRulesReader()));
              
                //DynaActionForm frmPoliciesEdit= (DynaActionForm)request.getSession().getAttribute("frmPoliciesEdit");
          //     log.info("healthAuthority"+frmPoliciesEdit.getString("healthAuthority"));
               
               
                return this.getForward(getRulePath(strActiveSubLink),mapping,request);
            }//end of else if(TTKCommon.getActiveSubLink(request).equals("Policies"))
            else
            {
                //forward to failure page
                return (mapping.findForward(strFailure));
            }//end of else
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "product"));
        }//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
                return doDefault(mapping,form,request,response);
    }//end of doChangeWebBoard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
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
            log.debug("RuleAction Action - inside doAdd method....");
            setLinks(request);
            DynaActionForm frmDefineRules= (DynaActionForm)form;
            StringBuffer sbfCaption=new StringBuffer();
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            RuleManager ruleManagerObject=this.getRuleManagerObject();
          String authType=  ruleManagerObject.getProductAuthority(TTKCommon.getWebBoardId(request));
            //initialize the Form bean and remove the unwanted data from the session
            frmDefineRules.initialize(mapping);
            request.getSession().removeAttribute("tableData");
            request.getSession().removeAttribute("RuleDocument");

            //get the caption to be displayed
            sbfCaption.append(buildCaption(strActiveSubLink,request));

            RuleVO ruleVO=new RuleVO();
            ruleVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
          
            frmDefineRules = (DynaActionForm)FormUtils.setFormValues("frmDefineRules",ruleVO,this, mapping, request);
            frmDefineRules.set("benefitRuleCompletedYN","N");
            frmDefineRules.set("caption",sbfCaption.toString());
            request.getSession().setAttribute("frmDefineRules",frmDefineRules);
            
            if("MOH".equals(authType)){
           	 return mapping.findForward(strNewMOHProductRules);
           }
            //While creating new Rule none of the Clause is selected
            String [] strSelectedClauses = null;
            Document baseRuleDocument=TTKCommon.getDocument("MasterBaseRules.xml"); //get the Master Base Rule
            request.setAttribute("Clauses",strSelectedClauses);
            request.getSession().setAttribute("BaseRuleDocument",baseRuleDocument);
            request.getSession().setAttribute("ProviderRulesXmlConditions",null);
            
            //DynaActionForm   frmProductGeneralInfo=(DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
            return this.getForward(strProductClauseList,mapping,request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "product"));
        }//end of catch(Exception exp)
    }//end of doAdd(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to navigate to detail screen to view/edit selected record.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doViewRule(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try{
            log.debug("RuleAction Action - inside doViewRule method....");
            setLinks(request);
            TableData tableData=TTKCommon.getTableData(request);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            DynaActionForm frmDefineRules= (DynaActionForm)form;
            RuleManager ruleManagerObject=this.getRuleManagerObject();
            Document ruleDocument=null;
            StringBuffer sbfCaption=new StringBuffer();

            //get the caption to be displayed
            sbfCaption.append(buildCaption(strActiveSubLink,request));

            if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            {
                RuleVO ruleVO=(RuleVO)tableData.getRowInfo(Integer.parseInt((String)(request).getParameter("rownum")));

                //call the DAO to get the Rule Details for the selected Rule
                ruleVO=ruleManagerObject.getProdPolicyRule(ruleVO.getProdPolicyRuleSeqID(),"R");
                ruleDocument=ruleVO.getRuleDocument();
                frmDefineRules = (DynaActionForm)FormUtils.setFormValues("frmDefineRules",ruleVO,this,mapping,request);
                frmDefineRules.set("caption",sbfCaption.toString()+"["+ruleVO.getProPolAuthority()+"]");
              
                request.getSession().setAttribute("frmDefineRules",frmDefineRules);
               
                if("MOH".equals(ruleVO.getProPolAuthority())){
                	request.setAttribute("ruleBtnType", "Add");
               	   return mapping.findForward(strNewMOHProductRules);
                  }
                
                request.getSession().setAttribute("RuleDocument",ruleDocument);
                
                request.getSession().setAttribute("ProviderRulesXmlConditions",getProviderCopayRulesUnmarshaller(ruleVO.getProviderCopayRulesReader()));
            }//end of if(!(TTKCommon.checkNull(request.getParameter("rownum")).equals("")))
            //DynaActionForm   frmProductGeneralInfo=(DynaActionForm)request.getSession().getAttribute("frmProductGeneralInfo");
            return this.getForward(strProductrule,mapping,request);
       
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "product"));
        }//end of catch(Exception exp)
    }//end of doViewRule(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
            DynaActionForm frmDefineRules= (DynaActionForm)form;
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();     //create the instance of Helper
            RuleVO ruleVO=null;
            PolicyDetailVO policyDetailVO=(PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
            //get the xml rule document from the session and update the it with user entered data
            Document ruleDocument=(Document)request.getSession().getAttribute("RuleDocument");
            ruleDocument=ruleXMLHelper.updateRuleDocument(ruleDocument,null,request);
            ruleVO=(RuleVO)FormUtils.getFormValues(frmDefineRules,this, mapping, request);
            ruleVO.setRuleDocument(ruleDocument);
            //identityValue
            ruleVO.setProviderCopayRulesWriter(getProviderCopayRulesMarshaller(request,ruleDocument));
            
            ruleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            
            long lngProdPolicyRuleSeqId=0;

            StringBuffer sbfCaption=new StringBuffer();             //get the caption to be displayed
            sbfCaption.append(buildCaption(strActiveSubLink,request));

            RuleManager ruleManagerObject=this.getRuleManagerObject();
           if(policyDetailVO!=null) ruleVO.setPolicySeqId(policyDetailVO.getPolicySeqID());
            

            if(ruleVO.getProdPolicyRuleSeqID()!=null)
            {
                //for updating the rule set Seq id to rule seq id and identifier to R
                ruleVO.setSeqID(ruleVO.getProdPolicyRuleSeqID());
                lngProdPolicyRuleSeqId=ruleManagerObject.saveProdPolicyRule(ruleVO,"R");
            }//end of if(ruleVO.getProdPolicyRuleSeqID()!=null)
            else
            {
                //for inserting the rule set Seq id to Product Policy seq id and identifier to P
                ruleVO.setSeqID(TTKCommon.getWebBoardId(request));
                lngProdPolicyRuleSeqId=ruleManagerObject.saveProdPolicyRule(ruleVO,"P");
            }//end of else

            if(lngProdPolicyRuleSeqId>0)
            {
                if(ruleVO.getProdPolicyRuleSeqID()!=null)    //requery after updating the rule
                {
                    ruleVO=ruleManagerObject.getProdPolicyRule(lngProdPolicyRuleSeqId,"R");
                    request.setAttribute("updated","message.savedSuccessfully");
                }//end of if(ruleVO.getProdPolicyRuleSeqID()!=null)
                else                                         //requery after inserting rule
                {
                    ruleVO=ruleManagerObject.getProdPolicyRule(lngProdPolicyRuleSeqId,"R");
                    request.setAttribute("updated","message.addedSuccessfully");
                }//end of else
            }//end of if(lngProdPolicyRuleSeqId>0)

            frmDefineRules = (DynaActionForm)FormUtils.setFormValues("frmDefineRules",ruleVO,this,mapping,request);
           
            
           /* if(strActiveSubLink.equals("Products"))
            {
                sbfCaption.append("[").append(productVO.getProductName()).append("]");
            }
            else
            {
                sbfCaption.append("[").append(policyDetailVO.getPolicyNbr()).append("]");
            }*/
            frmDefineRules.set("caption",sbfCaption.toString());
            request.getSession().setAttribute("frmDefineRules",frmDefineRules);
            request.getSession().setAttribute("RuleDocument",ruleDocument);
            request.getSession().setAttribute("ProviderRulesXmlConditions",getProviderCopayRulesUnmarshaller(ruleVO.getProviderCopayRulesReader()));
            
            
            return this.getForward(getRulePath(strActiveSubLink),mapping,request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "product"));
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
        log.debug("RuleAction Action - inside doClose method....");
        return doDefault(mapping,form,request,response);
    }//end of doClose(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to navigate to Reconfigure screen to select/deselect the clauses
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doReconfigure(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {

            log.debug("RuleAction Action - inside doClose method....");
            setLinks(request);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            //DynaActionForm frmClauseList=(DynaActionForm)form;
            RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();     //create the instance of Helper
            Document ruleDocument=(Document)request.getSession().getAttribute("RuleDocument");
            if(ruleDocument!=null)
            {
                ruleDocument=ruleXMLHelper.updateRuleDocument(ruleDocument,null,request);
            }//end of if(ruleDocument!=null)

            request.getSession().setAttribute("RuleDocument",ruleDocument);
            String [] selectedClauses = ruleXMLHelper.getClausesNodes(ruleDocument);
            request.setAttribute("Clauses",selectedClauses);

            Document baseRuleDocument=TTKCommon.getDocument("MasterBaseRules.xml");   //get the MasterBaseRule
            request.getSession().setAttribute("BaseRuleDocument",baseRuleDocument);

            return this.getForward(getClauseListPath(strActiveSubLink),mapping,request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "product"));
        }//end of catch(Exception exp)
    }//end of doReconfigure(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to navigate to Reconfigure screen to select/deselect the clauses
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doContinueRule(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
            log.debug("RuleAction Action - inside doContinueRule method....");
            setLinks(request);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            DynaActionForm frmDefineRules= (DynaActionForm)form;
            RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();     //create the instance of Helper
            //RuleDocument present in database/session memory
            Document ruleDocument=(Document)request.getSession().getAttribute("RuleDocument");
            String[] strSelectedClauses=request.getParameterValues("chkbox");

            //get the MasterBaseRule
            Document baseRuleDocument=TTKCommon.getDocument("MasterBaseRules.xml");

            /*get the xml document for selected clauses from master base rule and
            merge it with Rule document from database/session memory if exists */
            Document selectedRuleDoc=ruleXMLHelper.getClauses(baseRuleDocument,strSelectedClauses);

            if(ruleDocument!=null)
            {
               selectedRuleDoc=ruleXMLHelper.getMergedRuleDocument(ruleDocument,selectedRuleDoc);
            }//end of if(ruleDocument!=null)

            request.getSession().setAttribute("RuleDocument",selectedRuleDoc);
            frmDefineRules.set("frmChanged","true");
            request.getSession().setAttribute("frmDefineRules",frmDefineRules);
            return this.getForward(getRulePath(strActiveSubLink),mapping,request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "product"));
        }//end of catch(Exception exp)
    }//end of doContinueRule(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


    /**
     * This method is used to Revise Rule from the predefined rules of Product
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doReviseRule(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
            log.debug("RuleAction Action - inside doReviseRule method....");
            setLinks(request);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            StringBuffer sbfCaption=new StringBuffer();             //get the caption to be displayed
            sbfCaption.append(buildCaption(strActiveSubLink,request));
            //user can define new Rule by copying the contents of the this Rule
            DynaActionForm frmDefineRules= (DynaActionForm)form;
            frmDefineRules.initialize(mapping);
            request.getSession().removeAttribute("tableData");  //remove the unwanted data from the session
            RuleVO ruleVO=new RuleVO();
            Document ruleDocument=(Document)request.getSession().getAttribute("RuleDocument");
            ruleVO.setProdPolicySeqID(TTKCommon.getWebBoardId(request));
            ruleVO.setRuleDocument(ruleDocument);
            frmDefineRules = (DynaActionForm)FormUtils.setFormValues("frmDefineRules",ruleVO,this,mapping,request);
            //sbfCaption.append("[").append(productVO.getProductName()).append("]");  //build the caption
            frmDefineRules.set("caption",sbfCaption.toString());
            request.getSession().setAttribute("frmDefineRules",frmDefineRules);
            return this.getForward(getRulePath(strActiveSubLink),mapping,request);
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "product"));
        }//end of catch(Exception exp)
    }//end of doReviseRule(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to verify the defined rules of Product/Policy
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doVerifyRule(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
            log.debug("RuleAction Action - inside doVerifyRule method....");
            setLinks(request);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            //forward to the ruleverification screen
            return mapping.findForward(getRuleVerfiyPath(strActiveSubLink));
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "product"));
        }//end of catch(Exception exp)
    }//end of doVerifyRule(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method is used to navigate back to Rule defination screen when close button is
     * pressed in Verify rule screen.
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward doCloseVerifyRule(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
            log.debug("RuleAction Action - inside doCloseVerifyRule method....");
            setLinks(request);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            //forward to the ruleverification screen
            return mapping.findForward(getRulePath(strActiveSubLink));
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "product"));
        }//end of catch(Exception exp)
    }//end of doVerifyRule(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   
    /**
     * This method is used to navigate to Reconfigure screen to select/deselect the clauses
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward confProviderCopayDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {

            log.debug("RuleAction Action - inside confProviderCopayDetails method....");
            setLinks(request);
           // String strActiveSubLink=TTKCommon.getActiveSubLink(request);
           //request.setAttribute("benefitType", request.getParameter("benefitType"));
            return mapping.findForward("providerCopayDetails");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "product"));
        }//end of catch(Exception exp)
    }//end of doReconfigure(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
    /**
     * This method is used to navigate to Reconfigure screen to select/deselect the clauses
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward mohConfProviderCopayDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {

            log.debug("RuleAction Action - inside confProviderCopayDetails method....");
            setLinks(request);
           // String strActiveSubLink=TTKCommon.getActiveSubLink(request);
           request.getSession().setAttribute("viewBenefitType", request.getParameter("benefitType"));
            return mapping.findForward("mohProviderCopayDetails");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "product"));
        }//end of catch(Exception exp)
    }//end of doReconfigure(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
   
   
    
    
    /**
     * This method is used to navigate to Reconfigure screen to select/deselect the clauses
     * Finally it forwards to the appropriate view based on the specified forward mappings
     *
     * @param mapping The ActionMapping used to select this instance
     * @param form The optional ActionForm bean for this request (if any)
     * @param request The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return ActionForward Where the control will be forwarded, after this request is processed
     * @throws Exception if any error occurs
     */
    public ActionForward confClinicianTypeCopay(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {

            log.debug("RuleAction Action - inside confClinicianTypeCopay method....");
            setLinks(request);
           HttpSession session=request.getSession();
           
           ArrayList   consultTypes=Cache.getCacheObject("consultTypeCode");
           
           HashMap<String,String> hmConfDetails=new HashMap<>();
           
           String confIDValue="";
           
           
           if("V".equals(request.getParameter("opType"))){
        	   
        	   confIDValue=TTKCommon.checkNull(request.getParameter("confIDValue"));
        	   
               }else if("S".equals(request.getParameter("opType"))){
            	   
            	   request.setAttribute("closeWindow","S");
        	   
        	   String checkedIndexes[]=request.getParameterValues("checkedIndex");
        	   if(checkedIndexes!=null){
        		   for(int i=0;i<checkedIndexes.length;i++){
        			  
        			   String checkBoxVal=TTKCommon.checkNull(request.getParameter("checkBoxVal"+checkedIndexes[i]));
        			   String copay=TTKCommon.checkNull(request.getParameter("copay"+checkedIndexes[i]));
        			   String deduct=TTKCommon.checkNull(request.getParameter("deduct"+checkedIndexes[i]));
        			   String minmax=TTKCommon.checkNull(request.getParameter("minmax"+checkedIndexes[i]));
        			   
        			   confIDValue+=checkBoxVal+"@"+copay+"_"+deduct+"_"+minmax+"|";  
        		   }
        		   confIDValue=confIDValue.substring(0, confIDValue.length()-1);
        	   }
               }// if("S".equals(request.getParameter("opType"))){
 
        		  
           if(confIDValue!=null&&!confIDValue.equals("null")&&confIDValue.length()>1){
        	  
        	  
          String configValues[]=confIDValue.split("[|]");
          
          if(configValues!=null&&configValues.length>0){
        	  
        	  for(String configValue:configValues){
        		String onePair[]=configValue.split("[@]");
        		   if(onePair!=null&&onePair.length>0){
        			   hmConfDetails.put(onePair[0], onePair[1]);
        		   }
        	  }
          }
          
           }//if(confIDValue.length()>1){
           
                   
          if(request.getParameter("confID")!=null){
           session.setAttribute("confID", request.getParameter("confID"));           
          }
          
          session.setAttribute("confIDValue", confIDValue);
           StringBuilder tableContent=new StringBuilder();
           String allCheck="";
          if(hmConfDetails.size()==consultTypes.size())allCheck="checked";
           tableContent.append("<tr>");
           tableContent.append("<th align='center' style='width:5%;' class='gridHeader'>Check All<input title='Select All' type='checkbox' class='copayChBox' onclick='checkAllClinicians(this);' "+allCheck+" name='checkAll'></th>");
           tableContent.append("<th align='center' style='width:15%;' class='gridHeader' title='Clinician Type'>Clinician Types</th>");
           tableContent.append("<th align='center' style='width:30%;' class='gridHeader' title='Co-pay Details'>Co-pay Details</th>");
           tableContent.append(" </tr>");
           
           String defSelect=" selected=\"selected\"";
           
           
           for(int i=0;i<consultTypes.size();i++){
        	          	  
        	   tableContent.append("<tr class="+(i%2==0?"gridEvenRow":"gridOddRow")+">");
        	   
        	  CacheObject cacheObject=(CacheObject)consultTypes.get(i);
        	 
      		if( hmConfDetails.containsKey(cacheObject.getCacheId())){
      			

      			String coapy="";
      			String deduct="";
      			String minMax="";
      			
      			String strCopayDetails=hmConfDetails.get(cacheObject.getCacheId());
      			
      			if(strCopayDetails!=null){
      				String arrCopays[]=strCopayDetails.split("[_]");
      				if(arrCopays!=null&&arrCopays.length==3){
      					coapy=arrCopays[0];
      					deduct=arrCopays[1];
      					minMax=arrCopays[2];
      				}
      			}
      			
      			tableContent.append("<td align='center'><input type=\"checkbox\" onclick=\"checkClinician(this);\" class=\"copayChBox\" checked name=\"checkedIndex\" value=\""+i+"\"><input type=\"hidden\" name=\"checkBoxVal"+i+"\" value=\""+cacheObject.getCacheId()+"\"></td>");
      			tableContent.append("<td align='left'>"+cacheObject.getCacheDesc()+"</td>");
          		tableContent.append("<td align='center'>");
          		
          		tableContent.append("<table>");
          		tableContent.append("<tr>");
          		
          		tableContent.append("<td>");
          		tableContent.append("<input onkeyup=\"isNumeric(this);\" title=\"Coapy\"  class=\"CurrenttextBoxTS\" type=\"text\" name=\"copay"+i+"\" id=\"copay"+i+"\" value=\""+coapy+"\" >%");
          		tableContent.append("</td>");
          		
          		tableContent.append("<td>");
          		tableContent.append("or<input onkeyup=\"isNumeric(this);\" title=\"Deductble\"  class=\"CurrenttextBoxTS\" type=\"text\" name=\"deduct"+i+"\" id=\"deduct"+i+"\" value=\""+deduct+"\" >");
          		tableContent.append("</td>");
          		tableContent.append("<td>OMR</td>");
          		tableContent.append("<td>");
          		tableContent.append("<select class=\"selBoxTTS\" id=\"minmax"+i+"\"  name=\"minmax"+i+"\" >");
        		
        		if("MAX".equals(minMax)){
        			tableContent.append( "<option value=\"MIN\">MIN</option><option value=\"MAX\""+defSelect+">MAX</option>");	  
        		}else {
        			tableContent.append( "<option value=\"MIN\""+defSelect+">MIN</option><option value=\"MAX\">MAX</option>");	  
        		}
        		
        		tableContent.append("</select>");
        		tableContent.append("</td>");
        		
        		
        		
          		tableContent.append("</tr>");
          		tableContent.append("</table>");
          		
          		tableContent.append("</td>");
      		
      		}else {
            	tableContent.append("<td align='center'><input type=\"checkbox\" onclick=\"checkClinician(this);\" class=\"copayChBox\" name=\"checkedIndex\" value=\""+i+"\"> <input type=\"hidden\" name=\"checkBoxVal"+i+"\" value=\""+cacheObject.getCacheId()+"\"></td>");
            	tableContent.append("<td align='left'>"+cacheObject.getCacheDesc()+"</td>");
            	
          		tableContent.append("<td align='center'>");
          		
          		tableContent.append("<table>");
          		tableContent.append("<tr>");
          		
          		tableContent.append("<td>");
          		tableContent.append("<input onkeyup=\"isNumeric(this);\" title=\"Copay\" type=\"text\" class=\"textBoxTS\" readonly name=\"copay"+i+"\" id=\"copay"+i+"\" >%</td>");
          		tableContent.append("</td>");
          		
          		tableContent.append("<td>");
          		tableContent.append("or<input onkeyup=\"isNumeric(this);\" title=\"Deductble\" class=\"textBoxTS\" readonly type=\"text\" name=\"deduct"+i+"\" id=\"deduct"+i+"\">");
          		tableContent.append("</td>");
          		tableContent.append("<td>OMR</td>");

          		tableContent.append("<td>");
          		tableContent.append("<select class=\"selBoxTTS\" id=\"minmax"+i+"\"  name=\"minmax"+i+"\" >");
          		tableContent.append( "<option value=\"MIN\">MIN</option><option value=\"MAX\""+defSelect+">MAX</option>");
          		tableContent.append("</select>");
          		
          		tableContent.append("</td>");
          		
          		tableContent.append("</tr>");
          		tableContent.append("</table>");
          		
          		tableContent.append("</td>");
            }
              
      		tableContent.append("</tr>");
        	   
        	  
           }
           request.setAttribute("tableContent", tableContent);
           
           if("S".equals(request.getParameter("opType"))){
           request.setAttribute("successMsg", "Clinician copay details saved successfully");
           }
           
            return mapping.findForward("confClinicianTypeCopay");
        }//end of try
        catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "product"));
        }//end of catch(Exception exp)
    }//end of doReconfigure(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

   
    public ActionForward confConsultationCopay(ActionMapping mapping,ActionForm form,HttpServletRequest request,
            HttpServletResponse response) throws Exception{
        try
        {
        	
           
            log.debug("RuleAction Action - inside confConsultationCopay method....");
            setLinks(request);
           HttpSession session=request.getSession();
           
           ArrayList   consultTypes=Cache.getCacheObject("consultTypeCode");
           
           HashMap<String,String> hmConfDetails=new HashMap<>();
           
           String confIDValue="";
           
           
           if("V".equals(request.getParameter("opType"))){
        	   
        	   confIDValue=TTKCommon.checkNull(request.getParameter("confIDValue"));
        	   
               }else if("S".equals(request.getParameter("opType"))){
            	   
            	   request.setAttribute("closeWindow","S");
        	   
        	   String checkedIndexes[]=request.getParameterValues("checkedIndex");
        	   if(checkedIndexes!=null){
        		   for(int i=0;i<checkedIndexes.length;i++){
        			  
        			   String checkBoxVal=TTKCommon.checkNull(request.getParameter("checkBoxVal"+checkedIndexes[i]));
        			   String copayperclm=TTKCommon.checkNull(request.getParameter("copayperclm"+checkedIndexes[i]));
        			   String discountcopayperclm=TTKCommon.checkNull(request.getParameter("discountcopayperclm"+checkedIndexes[i]));
        			   String deductperclm=TTKCommon.checkNull(request.getParameter("deductperclm"+checkedIndexes[i]));
        			   String minmaxperclm=TTKCommon.checkNull(request.getParameter("minmaxperclm"+checkedIndexes[i]));
        			    
        			   
        			   confIDValue+=checkBoxVal+"@"+copayperclm+"_"+discountcopayperclm+"_"+deductperclm+"_"+minmaxperclm+"|";        			  
        			 
        		   }
        		   confIDValue=confIDValue.substring(0, confIDValue.length()-1);
        	   }
               }// if("S".equals(request.getParameter("opType"))){
          
     
        	if(confIDValue.length()>1){
         
        		   String configValues[]=confIDValue.split("[|]");
        		  if(configValues!=null&&configValues.length>0){
        			  
        			  for(String configValue:configValues){
        	        		String onePair[]=configValue.split("[@]");
        	        		   if(onePair!=null&&onePair.length>0){
        	        			   hmConfDetails.put(onePair[0], onePair[1]);
        	        		   }
        	        	  }  
        		  
        		  }
                 }//if(confIDValue.length()>1){
        	
        	
          if(request.getParameter("confID")!=null){
           session.setAttribute("confID", request.getParameter("confID"));           
          }
          
          session.setAttribute("confIDValue", confIDValue);
           StringBuilder tableContent=new StringBuilder();
          
          String allCheck="";
          if(hmConfDetails.size()==consultTypes.size())allCheck="checked";
  
          tableContent.append("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"gridWithCheckBox\" style=\'width:50%;height:95;\'>");
           tableContent.append("<tr>");
           tableContent.append("<th align='center' style='width:25%;' class='gridHeader'>Check All<input title='Select All' type='checkbox' class='copayChBox' onclick='checkAllClinicians(this);' "+allCheck+" name='checkAll'></th>");
           tableContent.append("<th align='center' style='width:25%;' class='gridHeader' title='Clinician Consultation Type'>Clinician Consultation Type</th>");
           tableContent.append("<th align='center' style='width:50%;' class='gridHeader' title='Co-pay Details'>Co-pay Details</th>");
            tableContent.append(" </tr>");
         
           String defSelect=" selected=\"selected\"";
           
           
           for(int i=0;i<consultTypes.size();i++){
        	  CacheObject cacheObject=(CacheObject)consultTypes.get(i);
        
        	  tableContent.append("<tr class="+(i%2==0?"gridEvenRow":"gridOddRow")+">");

        	  if( hmConfDetails.containsKey(cacheObject.getCacheId()))
        	  {
                  String clmcopay="";
        		  String clmdiscounted="";
        		  String clmdeduct = "";
        		  String clmMinMax="";
        		  String strCopayDetails=hmConfDetails.get(cacheObject.getCacheId());

        		  if(strCopayDetails!=null)
        		  {

        			  String arrclmCopays[]=strCopayDetails.split("[_]");
        			  if(arrclmCopays!=null && arrclmCopays.length==4)
        			  {

        				  clmcopay = arrclmCopays[0];
        				  clmdiscounted=arrclmCopays[1];
        				  clmdeduct=arrclmCopays[2];
        				  clmMinMax=arrclmCopays[3];
        			  }
        		  }

                  tableContent.append("<td align='center'><input type=\"checkbox\" onclick=\"checkClinician(this);\" class=\"copayChBox\" checked name=\"checkedIndex\" value=\""+i+"\"><input type=\"hidden\" name=\"checkBoxVal"+i+"\" value=\""+cacheObject.getCacheId()+"\"></td>");
        		  tableContent.append("<td align='left'>"+cacheObject.getCacheDesc()+"</td>");
        		  tableContent.append("<td align='center'>");
                  tableContent.append("<table>");
        		  tableContent.append("<tr>");
                  tableContent.append("<td>");
        		  tableContent.append("<input onkeyup=\"isNumeric(this);\" title=\"Copayperclm\" type=\"text\" class=\"textBoxTS\" readonly name=\"copayperclm"+i+"\" id=\"copayperclm"+i+"\"  onkeyup=\"javascript:isNumeric(this)\"  onkeypress = \"javascript:ConvertToUpperCase()\" onblur = \"javascript:toUpperCase(this.value,this.id,this.name);\" value=\""+clmcopay+"\">");
        		  tableContent.append("</td>");
        		  tableContent.append("<td>% of</td>");
        		  tableContent.append("<td>");
        		  tableContent.append(" <select  name=\"discountcopayperclm"+i+"\" id=\"discountcopayperclm"+i+"\"  single=\"\" >");
        		
        		  if("DA".equals(clmdiscounted))
        			  tableContent.append( "<option value=\"RA\""+defSelect+">Discounted Gross Amount</option><option value=\"ALA\">Allowed Amount</option>");
        		  else
        			  tableContent.append( "<option value=\"RA\">Discounted Gross Amount</option><option value=\"ALA\""+defSelect+">Allowed Amount</option>");	 
        		  tableContent.append("</select>");
        		  tableContent.append("</td>");
        		  tableContent.append("<td>(or)</td>");
        		  tableContent.append("<td>");

        		  tableContent.append("<input onkeyup=\"isNumeric(this);\" title=\"Deductble\" class=\"textBoxTS\" readonly type=\"text\" name=\"deductperclm"+i+"\" id=\"deductperclm"+i+"\" value=\""+clmdeduct+"\">");
        		  tableContent.append("</td>");
        		  tableContent.append("<td>OMR</td>");
        		  tableContent.append("<td>");
        		  tableContent.append("<select class=\"selBoxTTS\" id=\"minmaxperclm"+i+"\"  name=\"minmaxperclm"+i+"\">");
        	    
        		  if("MAX".equals(clmMinMax)){
        			  tableContent.append( "<option value=\"MAX\""+defSelect+">MAX</option><option value=\"MIN\">MIN</option>");	 	 
        		  }else {
        			 
        			  tableContent.append( "<option value=\"MAX\">MAX</option><option value=\"MIN\""+defSelect+">MIN</option>");	  
        		  }
        		  tableContent.append("</select>");

        		  tableContent.append("</td>");

        		  tableContent.append("</tr>");
        		  tableContent.append("</table>");

        		  tableContent.append("</td>"); 
                 }

        	  else
        	  {

                  tableContent.append("<td align='center'><input type=\"checkbox\" onclick=\"checkClinician(this);\" class=\"copayChBox\" name=\"checkedIndex\" value=\""+i+"\"> <input type=\"hidden\" name=\"checkBoxVal"+i+"\" value=\""+cacheObject.getCacheId()+"\"></td>");
        		  tableContent.append("<td align='left'>"+cacheObject.getCacheDesc()+"</td>");	 
        		  tableContent.append("<td align='center'>");
        		  tableContent.append("<table>");
        		  tableContent.append("<tr>");
        		  tableContent.append("<td>");
        		  tableContent.append("<input onkeyup=\"isNumeric(this);\" title=\"Copayperclaim\" type=\"text\" class=\"textBoxTS\" readonly name=\"copayperclm"+i+"\" id=\"copayperclm"+i+"\"  onkeyup=\"javascript:isNumeric(this)\"  onkeypress = \"javascript:ConvertToUpperCase()\" onblur = \"javascript:toUpperCase(this.value,this.id,this.name);\" value=\"\">");
        		  tableContent.append("</td>");
        		  tableContent.append("<td>% of</td>");
        		  tableContent.append("<td>");
        		  tableContent.append("<select  name=\"discountcopayperclm"+i+"\" id=\"discountcopayperclm"+i+"\"  single=\"\" >");
        		  tableContent.append( "<option value=\"RA\">Discounted Gross Amount</option><option value=\"ALA\">Allowed Amount</option>");
        		  tableContent.append("</select>");
        		  tableContent.append("</td>");
        		  tableContent.append("<td>(or)</td>");
        		  tableContent.append("<td>");
        		  tableContent.append("<input onkeyup=\"isNumeric(this);\" title=\"Deductble\" class=\"textBoxTS\" readonly type=\"text\" name=\"deductperclm"+i+"\" id=\"deductperclm"+i+"\" value=\"\" >");
        		  tableContent.append("</td>");
        		  tableContent.append("<td>OMR</td>");
        		  tableContent.append("<td>");
        		  tableContent.append("<select class=\"selBoxTTS\" id=\"minmaxperclm"+i+"\"  name=\"minmaxperclm"+i+"\">");
        		  tableContent.append( "<option value=\"MAX\">MAX</option><option value=\"MIN\">MIN</option>");
        		  tableContent.append("</select>");
        		  tableContent.append("</td>");
        		  tableContent.append("</tr>");
        		  tableContent.append("</table>");
        		  tableContent.append("</td>");

        	  }
                tableContent.append("</tr>");
      }
           tableContent.append("</table>");
           String tablecontent=tableContent.toString();
           request.setAttribute("tableContent", tablecontent);
           if("S".equals(request.getParameter("opType"))){
        	   request.setAttribute("successMsg", "Clinician copay details saved successfully");
           }

           return mapping.findForward("confConsultationCopay");
        }//end of try
        catch(TTKException expTTK)
        {
        	return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
        	return this.processExceptions(request, mapping, new TTKException(exp, "product"));
        }//end of catch(Exception exp)
    }//end of doReconfigure(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    
    /**
     * Returns the RuleManager session object for invoking methods on it.
     * @return RuleManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private RuleManager getRuleManagerObject() throws TTKException
    {
        RuleManager ruleManager = null;
        try
        {
            if(ruleManager == null)
            {
                InitialContext ctx = new InitialContext();
                ruleManager = (RuleManager) ctx.lookup("java:global/TTKServices/business.ejb3/RuleManagerBean!com.ttk.business.administration.RuleManager");
            }//end if(ruleManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "ruledata");
        }//end of catch
        return ruleManager;
    }//end of getRuleManagerObject()

    /**
     * Returns the ProductPolicyManager session object for invoking methods on it.
     * @return ProductPolicyManager session object which can be used for method invokation
     * @exception throws TTKException
     */
    private ProductPolicyManager getProductPolicyManagerObject() throws TTKException
    {
        ProductPolicyManager productPolicyManager = null;
        try
        {
            if(productPolicyManager == null)
            {
                InitialContext ctx = new InitialContext();
                productPolicyManager = (ProductPolicyManager) ctx.lookup("java:global/TTKServices/business.ejb3/ProductPolicyManagerBean!com.ttk.business.administration.ProductPolicyManager");
            }//end if(productPolicyManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "ProductManager");
        }//end of catch
        return productPolicyManager;
    }//end of getProductPolicyManagerObject()

    /**
     * This method returns the forward path of next view based on the Flow
     *
     * @param strActiveSubLink String current sublink
     * @return strRulePath String forward path for the next view
     */
    private String getRulePath(String strActiveSubLink)
    {
        String strRulePath="";
        if(strActiveSubLink.equals("Products"))
        {
            strRulePath=strProductrule;
        }//end of if(strActiveSubLink.equals("Products"))
        else if(strActiveSubLink.equals("Policies"))
        {
            strRulePath=strPolicyRule;
        }//end of else if(strActiveSubLink.equals("Policies"))
        return strRulePath;
    }//end of getRulePath(String strActiveSubLink)

    /**
     * This method returns the forward path of next view based on the Flow
     *
     * @param strActiveSubLink String current sublink
     * @return strClauseListPath String forward path for the next view
     */
    private String getClauseListPath(String strActiveSubLink)
    {
        String strClauseListPath="";
        if(strActiveSubLink.equals("Products"))
        {
            strClauseListPath=strProductClauseList;
        }//end of if(strActiveSubLink.equals("Products"))
        else if(strActiveSubLink.equals("Policies"))
        {
            strClauseListPath=strPolicyClauseList;
        }//end of else if(strActiveSubLink.equals("Policies"))
        return strClauseListPath;
    }//end of getClauseListPath(String strActiveSubLink)

    /**
     * This method returns the forward path of next view based on the Flow
     *
     * @param strActiveSubLink String current sublink
     * @return strClauseListPath String forward path for the next view
     */
    private String getRuleVerfiyPath(String strActiveSubLink)
    {
        String strRuleVerifyPath="";
        if(strActiveSubLink.equals("Products"))
        {
            strRuleVerifyPath=strProductRuleVerification;
        }//end of if(strActiveSubLink.equals("Products"))
        else if(strActiveSubLink.equals("Policies"))
        {
            strRuleVerifyPath=strPolicyRuleVerification;
        }//end of else if(strActiveSubLink.equals("Policies"))
        return strRuleVerifyPath;
    }//end of getRuleVerfiyPath(String strActiveSubLink)

    /**
     * This method is prepares the Caption based on the flow and retunrs it
     * @param strActiveSubLink current Active sublink
     * @param request current HttpRequest
     * @return String caption built
     * @throws TTKException
     */
    private String buildCaption(String strActiveSubLink,HttpServletRequest request)throws TTKException
    {
        StringBuffer sbfCaption=new StringBuffer();
        //String strMode=TTKCommon.checkNull(request.getParameter("mode"));
        ProductPolicyManager productPolicyManagerObject=this.getProductPolicyManagerObject();
        if(strActiveSubLink.equals("Products"))
        {
            ProductVO productVO=productPolicyManagerObject.getProductDetails(TTKCommon.getWebBoardId(request));
            sbfCaption.append("[").append(productVO.getCompanyName()).append("]");
            sbfCaption.append("[").append(productVO.getProductName()).append("]");
        }//end of if(strActiveSubLink.equals("Products"))
        else if(strActiveSubLink.equals("Policies"))
        {
            PolicyDetailVO policyDetailVO= productPolicyManagerObject.getPolicyDetail(TTKCommon.getWebBoardId(request),
                    TTKCommon.getUserSeqId(request));
            sbfCaption.append("[").append(policyDetailVO.getCompanyName()).append("]");
            sbfCaption.append("[").append(policyDetailVO.getPolicyNbr()).append("]");
        }//end of else if(strActiveSubLink.equals("Policies"))
        return sbfCaption.toString();
    }//end of buildCaption(String strActiveSubLink,HttpServletRequest request)

private HashMap<String,ArrayList<Condition>> getProviderCopayRulesUnmarshaller(Reader reader)throws Exception{
	
	
	HashMap<String,ArrayList<Condition>>    proRulesXmlConditions=null;
if(reader!=null){

	JAXBContext jaxbContext = JAXBContext.newInstance(ProviderRules.class); 
	Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();  
    
	ProviderRules providerRules=(ProviderRules)jaxbUnmarshaller.unmarshal(reader);
	
	if(providerRules!=null){
		
		ArrayList<Benefit> allBenefits=providerRules.getBenefits();
		proRulesXmlConditions=new HashMap<>();
		if(allBenefits!=null){
	
			for(Benefit benefit:allBenefits){
			if(benefit!=null){
				proRulesXmlConditions.put(benefit.getBenefitType(), benefit.getConditions());
			}//if(benefit!=null){
		}//for(Benefit benefit:allBenefits){
		}//if(allBenefits!=null){
	}//if(providerRules!=null){
	 
}//if(reader!=null){

return proRulesXmlConditions;
}
private StringWriter getProviderCopayRulesMarshaller(HttpServletRequest request,Document ruleDocument)throws Exception{
	
	
	HashMap<String,ArrayList<Condition>>  proRulesXmlConditions=(HashMap<String,ArrayList<Condition>>)request.getSession().getAttribute("ProviderRulesXmlConditions");
if(proRulesXmlConditions!=null&&proRulesXmlConditions.size()>0){
	ArrayList<Benefit> allBenefits=new ArrayList<>();
	
	List<Node>  coverageNodes=ruleDocument.selectNodes("//clause[@identityValue !='']/coverage[@identityValue !='' and @allowed=3]");//
	
	for(Node coverageNode:coverageNodes){
		
		ArrayList<Condition> configuredAllConditions =proRulesXmlConditions.get(coverageNode.valueOf("@identityValue"));
		
	if(configuredAllConditions!=null&&configuredAllConditions.size()>0){
		
		Benefit benefit=new Benefit();
		benefit.setBenefitType(coverageNode.valueOf("@identityValue"));
		benefit.setConditions(configuredAllConditions);
		allBenefits.add(benefit);
	}
	}
	
	 StringWriter stringWriter=new StringWriter();
	JAXBContext jaxbContext = JAXBContext.newInstance(ProviderRules.class); 
	Marshaller marshallerObj = jaxbContext.createMarshaller(); 
	marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);	
	ProviderRules providerRules=new ProviderRules();
	providerRules.setBenefits(allBenefits);
	
	

	//ruleDocument
	//File filePath=new File(TTKPropertiesReader.getPropertyValue("ProviderCopayRules"));
	 marshallerObj.marshal(providerRules,stringWriter);
	 //marshallerObj.marshal(providerRules,filePath);
	 return stringWriter;
}else{
	return null;
}
}
}//end of RuleAction.java