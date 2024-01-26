/** @ (#) EnrollmentRuleDataAction.java Apr 25th, 2006
 * Project       : TTK Healthcare Services
 * File          : EnrollmentRuleDataAction.java
 * Author        : Unni V Mana
 * Company       : Span Systems Corporation
 * Date Created  : 25th Apr 2006

 * Modified by   : Arun K N
 * Modified date : Jun 6th, 2007
 * Reason        : conversion to Dispatch action
 */


package com.ttk.action.enrollment;

import java.util.HashMap;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;

import com.ttk.action.TTKAction;
import com.ttk.action.tree.TreeData;
import com.ttk.business.administration.RuleManager;
import com.ttk.business.enrollment.MemberManagerBean;
import com.ttk.common.TTKCommon;
import com.ttk.common.WebBoardHelper;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.RuleXMLHelper;
import com.ttk.dto.administration.RuleVO;
import com.ttk.dto.enrollment.MemberVO;

import formdef.plugin.util.FormUtils;

/**
 * This action class is used to view/save the Rules to be defined at Family level for
 * all types of policies which will be executed in Preauth/Claims
 *
 */
public class EnrollmentRuleDataAction extends TTKAction {
    private static Logger log = Logger.getLogger( EnrollmentRuleDataAction.class );

    //declaration of other constants used in this class
    private static final String strIndPolicy="Individual Policy";
    private static final String strIndGrpPolicy="Ind. Policy as Group";
    private static final String strCorporatePolicy="Corporate Policy";
    private static final String strNonCorporatePolicy="Non-Corporate Policy";
    private static final String strFamilyRuleIdentfier="G";

    //declaration of forward paths
    private static final String strIndFamilyRule="indfamlilyrule";
    private static final String strGrpFamilyRule="grpfamlilyrule";
    private static final String strCorpFamilyRule="corpfamlilyrule";
    private static final String strNoncorpFamilyRule="noncorpfamlilyrule";

    /**
     * This method is used to view the Family Rules to be defined for a particular family.
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
    		log.info("Inside EnrollmentRuleDataAction doDefault method");
    		DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
            RuleVO ruleVO=null;
            RuleManager ruleManagerObject = this.getRuleManagerObject();
            Long lngPolicyGrpSeqID=null;
            Document enrollRuleDocument=null;
            DynaActionForm frmEnrollmentRule=(DynaActionForm)form;
            RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();
            String strFamilyRule=getForwardPath(request);
            //load the Display nodes to Application scope
            ServletContext sc=servlet.getServletContext();
            HashMap hmDisplayNodes=null;
            if(sc.getAttribute("RULE_DISPLAY_NODES")==null)
            {
                hmDisplayNodes=ruleXMLHelper.loadDisplayNodes(TTKCommon.getDocument("MasterBaseRules.xml"));
                sc.setAttribute("RULE_DISPLAY_NODES",hmDisplayNodes);
            }//end of if(sc.getAttribute("RULE_DISPLAY_NODES")==null)
            else
            {
                hmDisplayNodes=(HashMap)sc.getAttribute("RULE_DISPLAY_NODES");
            }//end of if(sc.getAttribute("RULE_DISPLAY_NODES")==null)
            if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
            {
                frmEnrollmentRule.initialize(mapping);  //initialize the form
                MemberVO memberVO=(MemberVO)treeData.getSelectedObject(request.getParameter("selectedroot"),null);
                lngPolicyGrpSeqID=memberVO.getPolicyGroupSeqID();
                frmEnrollmentRule.set("policyGroupSeqID",lngPolicyGrpSeqID.toString()); //load the policy group seq id to form bean
            }//end of if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
            else
            {
                lngPolicyGrpSeqID=TTKCommon.getLong((String)frmEnrollmentRule.get("policyGroupSeqID"));
            }//end of else
//    		remove the unwanted data from session
            request.getSession().removeAttribute("RuleDocument");
            StringBuffer sbfCaption=new StringBuffer(WebBoardHelper.getPolicyNumber(request));

            //get the rule defined for this policy
            ruleVO=ruleManagerObject.getProdPolicyRule(lngPolicyGrpSeqID,strFamilyRuleIdentfier);
            if(ruleVO !=null && ruleVO.getRuleDocument() !=null)
            {
                enrollRuleDocument=ruleVO.getRuleDocument();
                enrollRuleDocument=ruleXMLHelper.getFamilyMemberRuleDocument(enrollRuleDocument,"FamilyRule");
                //check if any enrollment rule exists and clause is allowed at product/policy level to be defined
                if(enrollRuleDocument.selectSingleNode("//clause/coverage[contains(@module,'E')")==null)
                {
                    //this attribute is used in JSP to show the error message.
                    request.setAttribute("display","display");
                    TTKException expTTK = new TTKException();
                    expTTK.setMessage("error.enrollment.enrollmentrule.notapplicable");
                    request.getSession().setAttribute("frmEnrollmentRule",frmEnrollmentRule);
                    frmEnrollmentRule.set("caption",sbfCaption.toString());
                    throw expTTK;
                }//end of if(enrollRuleDocument.selectSingleNode("//clause/coverage[contains(@module,'E')")==null)
            }//end of if(ruleVO !=null && ruleVO.getRuleDocument() !=null)

            else
            {
                request.setAttribute("display","display");  //this attribute is used in JSP to show the error message.
                TTKException expTTK = new TTKException();
                expTTK.setMessage("error.enrollment.enrollmentrule.notapplicable");
                request.getSession().setAttribute("frmEnrollmentRule",frmEnrollmentRule);
                throw expTTK;
            }//end of else
            frmEnrollmentRule =(DynaActionForm)FormUtils.setFormValues("frmEnrollmentRule",ruleVO, this, mapping, request);
            frmEnrollmentRule.set("caption",sbfCaption.toString());
            frmEnrollmentRule.set("policyGroupSeqID",lngPolicyGrpSeqID.toString()); //load the policy group seq id to form bean
            request.getSession().setAttribute("frmEnrollmentRule",frmEnrollmentRule);
            request.getSession().setAttribute("RuleDocument",enrollRuleDocument);
            
            Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);
            String strActiveSubLink=TTKCommon.getActiveSubLink(request);
            MemberManagerBean memberManager  = new MemberManagerBean();
           
            if(strActiveSubLink.trim().equals("Corporate Policy"))
            {
            	 String policy_status ="";
                 policy_status =    memberManager.getPolicyStatus(pol_seq_id);
                 request.getSession().setAttribute("policy_status", policy_status);
              
            }
          
            return this.getForward(strFamilyRule,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"enrollmentrules"));
		}//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)



    /**
     * This method is used to save the family rules.
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
    		log.debug("Inside EnrollmentRuleDataAction doSave method");
    		DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
            this.setLinks(request,strSwitchType);
            DynaActionForm frmEnrollmentRule=(DynaActionForm)form;
            TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
            RuleManager ruleManagerObject = this.getRuleManagerObject();
            RuleVO ruleVO=null;
            Document enrollRuleDocument=null;
            Long lngPolicyGrpSeqID=null;
            String strFamilyRule=getForwardPath(request);
            StringBuffer sbfCaption=new StringBuffer(WebBoardHelper.getPolicyNumber(request));
            if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
            {
                frmEnrollmentRule.initialize(mapping);  //initialize the form
                MemberVO memberVO=(MemberVO)treeData.getSelectedObject(request.getParameter("selectedroot"),null);
                lngPolicyGrpSeqID=memberVO.getPolicyGroupSeqID();
                frmEnrollmentRule.set("policyGroupSeqID",lngPolicyGrpSeqID.toString()); //load the policy group seq id to form bean
            }//end of if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
            else
            {
                lngPolicyGrpSeqID=TTKCommon.getLong((String)frmEnrollmentRule.get("policyGroupSeqID"));
            }//end of else
            RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();
            ruleVO=(RuleVO)FormUtils.getFormValues(frmEnrollmentRule,this, mapping, request);
            ruleVO.setRuleDocument(ruleXMLHelper.updateRuleDocument((Document)request.getSession().getAttribute("RuleDocument"),
                    "FamilyRule",request));

            ruleVO.setSeqID(lngPolicyGrpSeqID);   //set Seq Id to policy group Seq ID
            ruleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));

            //call the DAO to save the Family Rules
            long lngCnt=ruleManagerObject.saveProdPolicyRule(ruleVO,strFamilyRuleIdentfier);
            if(lngCnt>0)
            {
                //Requery to get the updated data from database
                ruleVO=ruleManagerObject.getProdPolicyRule(lngPolicyGrpSeqID,strFamilyRuleIdentfier);
                enrollRuleDocument=ruleVO.getRuleDocument();
                request.setAttribute("updated","message.savedSuccessfully");
                frmEnrollmentRule =(DynaActionForm)FormUtils.setFormValues("frmEnrollmentRule",ruleVO, this,
                        mapping, request);
                frmEnrollmentRule.set("caption",sbfCaption.toString());
                request.getSession().setAttribute("frmEnrollmentRule",frmEnrollmentRule);
                frmEnrollmentRule.set("policyGroupSeqID",lngPolicyGrpSeqID.toString()); //load the policy group seq id to form bean
            }//end of if(lngCnt>0)
            request.getSession().setAttribute("RuleDocument",enrollRuleDocument);
            return this.getForward(strFamilyRule,mapping,request);
    	}//end of try
    	catch(TTKException expTTK)
		{
			return this.processExceptions(request, mapping, expTTK);
		}//end of catch(TTKException expTTK)
		catch(Exception exp)
		{
			return this.processExceptions(request, mapping, new TTKException(exp,"enrollmentrules"));
		}//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)


    /**
     * Returns the RuleManager  object for invoking methods on it.
     * @return RuleManager  object which can be used for method invocation
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
                log.info("Inside RuleManager: RuleManager: " + ruleManager);
            }//end if(productPolicyManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "Enrollment ruledata");
        }//end of catch
        return ruleManager;
    }//end of getRuleManagerObject()

    /**
     * Returns a string which contains the Forward Path
     * @param request HttpServletRequest object which contains information required for buiding the Forward Path
     * @return String which contains the comma separated sequence id's to be deleted
     */
    private String getForwardPath(HttpServletRequest request) throws TTKException{
    	String strActiveSubLink=TTKCommon.getActiveSubLink(request);
        String strForward="";
        try{
        	if(strActiveSubLink.equals(strIndPolicy))
            {
        		strForward=strIndFamilyRule;
            }//end of if(strActiveSubLink.equals(strIndPolicy))
            else if(strActiveSubLink.equals(strIndGrpPolicy))
            {
            	strForward=strGrpFamilyRule;
            }//end of else if(strActiveSubLink.equals(strIndGrpPolicy))
            else if(strActiveSubLink.equals(strCorporatePolicy))
            {
            	strForward=strCorpFamilyRule;
            }//end of else if(strActiveSubLink.equals(strCorporatePolicy))
            else if(strActiveSubLink.equals(strNonCorporatePolicy))
            {
            	strForward=strNoncorpFamilyRule;
            }//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
    	}//end of try
    	catch(Exception exp)
        {
            throw new TTKException(exp, "enrollmentrules");
        }//end of catch
        return strForward;
    }//end of getForwardPath(HttpServletRequest request)
}//end of EnrollmentRuleDataAction
