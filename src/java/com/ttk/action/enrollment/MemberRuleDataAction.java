/**
 * @ (#) MemberRuleDataAction.java Feb 2nd, 2006
 * Project 		: TTK HealthCare Services
 * File 		: MemberRuleDataAction.java
 * Author 		: Arun K N
 * Company 		: Span Systems Corporation
 * Date Created :
 *
 * @author 		: Arun K N
 * Modified by 	: Krupa J
 * Modified date: June 1st, 2007
 * Reason 		: Conversion to Dispatch Action
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
 * This action class is used to view/save the Rules to be defined at Member level for
 * all types of policies which will be executed in Preauth/Claims
 *
 */
public class MemberRuleDataAction extends TTKAction {

    private static Logger log = Logger.getLogger( MemberRuleDataAction.class );

    //declaration of other constants used in this class
    private static final String strCorpMemberDetails="cormemberdetails";
    private static final String strCorpRenewMemberDetails="cormemberrenewdetails";
    private static final String strIndPolicy="Individual Policy";
    private static final String strIndGrpPolicy="Ind. Policy as Group";
    private static final String strCorporatePolicy="Corporate Policy";
    private static final String strNonCorporatePolicy="Non-Corporate Policy";
    private static final String strMemberRuleIdentifier="M";   //identifier to get the MemberRules of the policy

    //declaration of forward paths
    private static final String strIndMemberRule="indmemberrule";
    private static final String strGrpMemberRule="grpmemberrule";
    private static final String strCorpMemberRule="corpmemberrule";
    private static final String strNoncorpMemberRule="noncorpmemberrule";

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
    		log.debug("Inside the doDefault method of MemberRuleDataAction");
    		DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
    		String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
    		this.setLinks(request,strSwitchType);

    		//load the Display nodes to Application scope
            ServletContext sc=servlet.getServletContext();
            HashMap hmDisplayNodes=null;
            RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();
            if(sc.getAttribute("RULE_DISPLAY_NODES")==null)
            {
                hmDisplayNodes=ruleXMLHelper.loadDisplayNodes(TTKCommon.getDocument("MasterBaseRules.xml"));
                sc.setAttribute("RULE_DISPLAY_NODES",hmDisplayNodes);
            }//end of if(sc.getAttribute("RULE_DISPLAY_NODES")==null)
            else
            {
                hmDisplayNodes=(HashMap)sc.getAttribute("RULE_DISPLAY_NODES");
            }//end of else

    		RuleManager ruleManagerObject = null;
    		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
    		RuleVO ruleVO=null;
    		Long lngMemberSeqID=null;
    		String strMemberName="";
    		DynaActionForm frmMemberRule=(DynaActionForm)form;
    		TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
            if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")) &&
            		!(TTKCommon.checkNull(request.getParameter("selectednode")).equals("")))
            {
                frmMemberRule.initialize(mapping);  //initialize the form
                MemberVO memberVO=(MemberVO)treeData.getSelectedObject(request.getParameter("selectedroot"),
                													   request.getParameter("selectednode"));
                lngMemberSeqID=memberVO.getMemberSeqID();
                strMemberName=memberVO.getName();
                frmMemberRule.set("MemberSeqID",lngMemberSeqID.toString());
                frmMemberRule.set("MemberName",strMemberName);
            }//end of if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
            else
            {
                lngMemberSeqID=TTKCommon.getLong((String)frmMemberRule.get("MemberSeqID"));
                strMemberName=(String)frmMemberRule.get("MemberName");
            }//end of else
    		String strMemberRules=getMemberRulesPath(strActiveSubLink);
    		StringBuffer sbfCaption=new StringBuffer();
    		Document memberRuleDocument=null;
    		//remove the unwanted data from session
            request.getSession().removeAttribute("RuleDocument");

            // getting the member related information from the database
            ruleManagerObject=this.getRuleManagerObject();
            //call member method to get the member based xml
            ruleVO=ruleManagerObject.getProdPolicyRule(lngMemberSeqID,strMemberRuleIdentifier);

            String strMemberInfo[]=strMemberName.split("/");    //to prepare the caption
            sbfCaption.append("[ ").append(WebBoardHelper.getPolicyNumber(request)).append(" ][ ").
            append(strMemberInfo[0]).append("][").append(strMemberInfo[1]).append("]");
            if(ruleVO !=null && ruleVO.getRuleDocument() !=null)
            {
            	memberRuleDocument=ruleVO.getRuleDocument();
                memberRuleDocument=ruleXMLHelper.getFamilyMemberRuleDocument(memberRuleDocument,"MemberRule");
                //check if any enrollment rule exists at product/policy level to be defined
                if(memberRuleDocument.selectSingleNode("//clause/coverage[contains(@module,'M')")==null)
                {
                    TTKException expTTK = new TTKException();
                    request.setAttribute("display","display");//this attribute is used in JSP to show the error message.
                    expTTK.setMessage("error.enrollment.members.notapplicable");
                    frmMemberRule.set("caption",sbfCaption.toString());
                    request.getSession().setAttribute("frmMemberRule",frmMemberRule);
                    throw expTTK;
                }//end of if(enrollRuleDocument.selectSingleNode("//coverage[contains(@module,'E')")==null)
            }//end of if(rulevo !=null && rulevo.getRuleDocument() !=null)
            else
            {
                TTKException expTTK = new TTKException();
                request.setAttribute("display","display");
                expTTK.setMessage("error.enrollment.enrollmentrule.notfound");
                frmMemberRule.set("caption",sbfCaption.toString());
                request.getSession().setAttribute("frmMemberRule",frmMemberRule);
                throw expTTK;
            }//end of else
            frmMemberRule =(DynaActionForm)FormUtils.setFormValues("frmMemberRule",ruleVO, this, mapping, request);
            frmMemberRule.set("caption",sbfCaption.toString());
            frmMemberRule.set("MemberSeqID",lngMemberSeqID.toString());
            frmMemberRule.set("MemberName",strMemberName);
            Long pol_seq_id = WebBoardHelper.getPolicySeqId(request);
            if(strActiveSubLink.trim().equals("Corporate Policy"))
            {
            	 String policy_status ="";
            	 MemberManagerBean  memberManager  = new MemberManagerBean();
                 policy_status =    memberManager.getPolicyStatus(pol_seq_id);
                 request.getSession().setAttribute("policy_status", policy_status);
            }
            request.getSession().setAttribute("frmMemberRule",frmMemberRule);
            request.getSession().setAttribute("RuleDocument",memberRuleDocument);
            return this.getForward(strMemberRules, mapping, request);
    	}//end of try
    	catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "memberrules"));
        }//end of catch(Exception exp)
    }//end of doDefault(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

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
    		log.debug("Inside the doSave method of MemberRuleDataAction");
    		DynaActionForm frmPolicyList=(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
    		String strSwitchType=TTKCommon.checkNull((String)frmPolicyList.get("switchType"));
    		this.setLinks(request,strSwitchType);
    		RuleVO ruleVO=null;
    		RuleXMLHelper ruleXMLHelper=new RuleXMLHelper();
    		DynaActionForm frmMemberRule=(DynaActionForm)form;
    		RuleManager ruleManagerObject = null;
    		Long lngMemberSeqID=null;
    		String strMemberName="";
    		String strActiveSubLink=TTKCommon.getActiveSubLink(request);
    		String strMemberRules=getMemberRulesPath(strActiveSubLink);
    		StringBuffer sbfCaption=new StringBuffer();
    		Document memberRuleDocument=null;
    		TreeData treeData =(TreeData)request.getSession().getAttribute("treeData") ;
            if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")) &&
            		!(TTKCommon.checkNull(request.getParameter("selectednode")).equals("")))
            {
                frmMemberRule.initialize(mapping);  //initialize the form
                MemberVO memberVO=(MemberVO)treeData.getSelectedObject(request.getParameter("selectedroot"),
                													   request.getParameter("selectednode"));
                lngMemberSeqID=memberVO.getMemberSeqID();
                strMemberName=memberVO.getName();
                frmMemberRule.set("MemberSeqID",lngMemberSeqID.toString());
                frmMemberRule.set("MemberName",strMemberName);
            }//end of if(treeData!=null && !(TTKCommon.checkNull(request.getParameter("selectedroot")).equals("")))
            else
            {
                lngMemberSeqID=TTKCommon.getLong((String)frmMemberRule.get("MemberSeqID"));
                strMemberName=(String)frmMemberRule.get("MemberName");
            }//end of else
    		ruleVO=(RuleVO)FormUtils.getFormValues(frmMemberRule,this, mapping, request);
            Document doc=null;
            doc = (Document)request.getSession().getAttribute("RuleDocument");
            Document processDoc =null;
            processDoc = ruleXMLHelper.updateRuleDocument(doc,"MemberRule",request);
            ruleVO.setRuleDocument(processDoc);
            ruleManagerObject=this.getRuleManagerObject();
            ruleVO.setSeqID(lngMemberSeqID);
            ruleVO.setUpdatedBy(TTKCommon.getUserSeqId(request));
            long lngCnt=ruleManagerObject.saveProdPolicyRule(ruleVO,strMemberRuleIdentifier);
            if(lngCnt>0)
            {
                //Requery to get the updated data from database
                ruleVO=ruleManagerObject.getProdPolicyRule(lngMemberSeqID,strMemberRuleIdentifier);
                memberRuleDocument=ruleVO.getRuleDocument();
                request.setAttribute("updated","message.savedSuccessfully");
                String strMemberInfo[]=strMemberName.split("/");    //to prepare the caption
                sbfCaption.append("[ ").append(WebBoardHelper.getPolicyNumber(request)).append(" ][ ").
                append(strMemberInfo[0]).append("][").append(strMemberInfo[1]).append("]");
                frmMemberRule =(DynaActionForm)FormUtils.setFormValues("frmMemberRule",ruleVO,
                													   this, mapping, request);
                frmMemberRule.set("caption",sbfCaption.toString());
                frmMemberRule.set("MemberSeqID",lngMemberSeqID.toString());
                frmMemberRule.set("MemberName",strMemberName);
            }//end of if(lngCnt>0)
            request.getSession().setAttribute("RuleDocument",memberRuleDocument);
            return this.getForward(strMemberRules, mapping, request);
		}//end of try
    	catch(TTKException expTTK)
        {
            return this.processExceptions(request, mapping, expTTK);
        }//end of catch(TTKException expTTK)
        catch(Exception exp)
        {
            return this.processExceptions(request, mapping, new TTKException(exp, "memberrules"));
        }//end of catch(Exception exp)
    }//end of doSave(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)

    /**
     * This method returns the forward path for next view based on the Flow
     *
     * @param strActiveSubLink String current sublink
     * @return strMemberRules String forward path for the next view
     */

    private String getMemberRulesPath(String strActiveSubLink)
    {
        String strMemberRules=null;

        if(strActiveSubLink.equals(strIndPolicy))
        {
            strMemberRules=strIndMemberRule;
        }//end of if(strActiveSubLink.equals(strIndPolicy))
        else if(strActiveSubLink.equals(strIndGrpPolicy))
        {
            strMemberRules=strGrpMemberRule;
        }//end of else if(strActiveSubLink.equals(strIndGrpPolicy))
        else if(strActiveSubLink.equals(strCorporatePolicy))
        {
            strMemberRules=strCorpMemberRule;
        }//end of else if(strActiveSubLink.equals(strCorporatePolicy))
        else if(strActiveSubLink.equals(strNonCorporatePolicy))
        {
            strMemberRules=strNoncorpMemberRule;
        }//end of else if(strActiveSubLink.equals(strNonCorporatePolicy))
        return strMemberRules;
    }//end of getMemberRulesPath(String strActiveSubLink)

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
            }//end if(productPolicyManager == null)
        }//end of try
        catch(Exception exp)
        {
            throw new TTKException(exp, "ruledata");
        }//end of catch
        return ruleManager;
    }//end of getRuleManagerObject()
    
    private String getForwardPath(String strActiveSubLink, String policy_status) {
		// TODO Auto-generated method stub
    	String strForwardPath=null;
    	if(strActiveSubLink.equals(strCorporatePolicy)  &&  policy_status.trim().equals("FTS"))
        {
            strForwardPath=strCorpMemberDetails;

        } 
    	//end of if(strActiveSubLink.equals(strCorporatePolicy))
    	else if(strActiveSubLink.equals(strCorporatePolicy)  &&  policy_status.trim().equals("RTS"))
        {
    		
            strForwardPath=strCorpRenewMemberDetails;
           
        } 
		return strForwardPath;
	}

	
}//end of MemberRuleDataAction.java

