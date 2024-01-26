/**
 * @ (#) PolicySearchBox.java Feb 3, 2006
 * Project      : TTK HealthCare Services
 * File         : PolicySearchBox.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Feb 3, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.enrollment;

import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;
import com.ttk.common.TTKCommon;
import com.ttk.common.security.Cache;
import com.ttk.dto.administration.EventVO;
import com.ttk.dto.administration.WorkflowVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 * This is the tag lib for loading the search criteria components
 * of Policy search for Individual Policy, Ind. Policy as Group,
 * Corporate Policy and Non-Corporate Policy search screens based on enrollment
 * or endorsement flow
 *
 */
public class PolicySearchBox extends TagSupport {

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    private static Logger log = Logger.getLogger( PolicySearchBox.class );

    //declaration of constants used in this class
    private String strIndPolicy="Individual Policy";
    private String strIndGrpPolicy="Ind. Policy as Group";
    private String strCorporatePolicy="Corporate Policy";
    private String strNonCorporatePolicy="Non-Corporate Policy";
    private String strEnrollment="ENM";
    private String strEndorsement="END";
    /**
     * This method will be executed when customised tag begins
     * @return int
     * @throws JspException
     */
    public int doStartTag() throws JspException
    {
        try
        {
            log.debug("Inside PolicySearchBox Tag library.............");
            JspWriter out = pageContext.getOut(); //Writer object to write the file
            CacheObject cacheObject=null;
            EventVO eventVO=null;
            ArrayList alWorkFlow=null;
            HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();

            //get the WorkFlow Event Details of the User from the session to load the Combo box
            HashMap hmWorkflow= ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getWorkFlowMap();

            //get the reference of the form bean to reload the search Criteria Components
            DynaActionForm frmPolicyList =(DynaActionForm)request.getSession().getAttribute("frmPolicyList");
            String strSwitchType=(String)frmPolicyList.get("switchType");
            String strInsuranceCompany=(String)frmPolicyList.get("sInsuranceCompany");
            String strTtkBranch=(String)frmPolicyList.get("sTtkBranch");
            String strPolicyNumber=(String)frmPolicyList.get("sPolicyNumber");
            String strBatchNumber=(String)frmPolicyList.get("sBatchNumber");
            String strEnrollmentNumber=(String)frmPolicyList.get("sEnrollmentNumber");
            String strEndorsementNumber=(String)frmPolicyList.get("sEndorsementNumber");
            String strCustEndorsementNumber=(String)frmPolicyList.get("sCustEndorsementNumber");
            String strMemberName=(String)frmPolicyList.get("sMemberName");
            String strGroupId=(String)frmPolicyList.get("sGroupId");
            String strGroupName=(String)frmPolicyList.get("sGroupName");
//            String strCorporateName=(String)frmPolicyList.get("sCorporateName");
            String strAgentCode=(String)frmPolicyList.get("sAgentCode");
            String strWorkFlow=(String)frmPolicyList.get("sWorkFlow");
            String strCertificateNumber=(String)frmPolicyList.get("sCertificateNumber");
            String strCustomerCode=(String)frmPolicyList.get("sCustomerCode");
            String strSChemeName=(String)frmPolicyList.get("sSChemeName");
            String strMemberId=(String)frmPolicyList.get("sMemberId");
            //AK
            String strProductName=(String)frmPolicyList.get("ProductName");
            String strproductAuthority=(String)frmPolicyList.get("productAuthority");
                        
            //get the required Cache Data to load select boxes
            ArrayList alInsuranceCompnay=Cache.getCacheObject("insuranceCompany");
            ArrayList alTTKBranch=Cache.getCacheObject("officeInfo");
            //AK
            ArrayList productAuthority=Cache.getCacheObject("productAuthority");
            
            String strEmiratesID=(String)frmPolicyList.get("sEmiratesID");

            //get the WorkFlow Details of the User based on flow
            if(strSwitchType.equals(strEnrollment))
            {
                if(hmWorkflow!=null && hmWorkflow.containsKey(new Long(1)))
                    alWorkFlow=((WorkflowVO)hmWorkflow.get(new Long(1))).getEventVO();
            }//end of if(strSwitchType.equals(strEnrollment))
            else if(strSwitchType.equals(strEndorsement))
            {
                if(hmWorkflow!=null && hmWorkflow.containsKey(new Long(2)))
                    alWorkFlow=((WorkflowVO)hmWorkflow.get(new Long(2))).getEventVO();
            }//end of else if(strSwitchType.equals(strEndorsement))

            String strSubLink=TTKCommon.getActiveSubLink(request);  //get the Active Sublink

            out.print("<table align=\"center\" class=\"searchContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
            out.print("<tr class=\"searchContainerWithTab\">");

            	out.print("<td nowrap>Policy No.:<br>");
                out.print("<input name=\"sPolicyNumber\" type=\"text\" class=\"textBox textBoxMedium\" id=\"search1\" maxlength=\"60\" value=\""+TTKCommon.getHtmlString(strPolicyNumber)+"\"></td>");

                out.print("<td nowrap>Insurance Name<br>");
                out.print("<select name=\"sInsuranceCompany\" id=\"search2\" class=\"selectBox selectBoxMedium\">");
                out.print("<option value=\"\">Any</option>");
                if(alInsuranceCompnay!=null && alInsuranceCompnay.size()>0)
                {
                      for(int i=0;i<alInsuranceCompnay.size();i++)
                        {
                            cacheObject = (CacheObject)alInsuranceCompnay.get(i);
                            out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strInsuranceCompany, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
                        }//end of for(int i=0;i<alInsuranceCompnay.size();i++)
                    }//end of if(alInsuranceCompnay!=null && alInsuranceCompnay.size()>0)
                out.print("</select></td>");


                out.print("<td nowrap>Branch Location:<br>");
                out.print("<select name=\"sTtkBranch\" id=\"search3\" class=\"selectBox selectBoxMedium\">");
                    out.print("<option value=\"\">Any</option>");
                    if(alTTKBranch!=null && alTTKBranch.size()>0)
                    {
                        for(int i=0;i<alTTKBranch.size();i++)
                        {
                            cacheObject = (CacheObject)alTTKBranch.get(i);
                            out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strTtkBranch,cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
                        }//end of for(int i=0;i<alTTKBranch.size();i++)
                    }//end of if(alTTKBranch!=null && alTTKBranch.size()>0)
                out.print("</select></td>");
                
 
                if(strSwitchType.equals(strEnrollment))
                {
                	if(!( strSubLink.equals(strNonCorporatePolicy)))
                    {	
                    	out.print("<td nowrap>Agent Code:<br>");
                        	out.print("<input name=\"sAgentCode\" type=\"text\" id=\"search4\" class=\"textBox textBoxMedium\" maxlength=\"60\" value=\""+TTKCommon.getHtmlString(strAgentCode)+"\"></td>");
                    }//end of if(!( strSubLink.equals(strNonCorporatePolicy)))
                    else
                    {
                    	out.print("<td nowrap>Customer Code:<br>");
                        out.print("<input name=\"sCustomerCode\" type=\"text\" id=\"search4\" class=\"textBox textBoxMedium\" maxlength=\"60\" value=\""+TTKCommon.getHtmlString(strCustomerCode)+"\"></td>");
                    }//end of else
                }//end of if(strSwitchType.equals(strEnrollment))
                else if(strSwitchType.equals(strEndorsement))
                {
                	if(!( strSubLink.equals(strNonCorporatePolicy)))
                    {	
                    	out.print("<td nowrap>Agent Code:<br>");
                        	out.print("<input name=\"sAgentCode\" type=\"text\" id=\"search4\" class=\"textBox textBoxMedium\" maxlength=\"60\" value=\""+TTKCommon.getHtmlString(strAgentCode)+"\"></td>");
                    }//end of if(!( strSubLink.equals(strNonCorporatePolicy)))
                	else
                    {
                		out.print("<td nowrap> Batch No.:<br>");
                        out.print("<input name=\"sBatchNumber\" type=\"text\" id=\"search9\" class=\"textBox textBoxMedium\" maxlength=\"60\" value=\""+TTKCommon.getHtmlString(strBatchNumber)+"\"></td>");
                    }//end of else
                }//end of else if(strSwitchType.equals(strEndorsement))
                

                if(strSwitchType.equals(strEnrollment)){
                	out.print("<td nowrap> Batch No.:<br>");
                    out.print("<input name=\"sBatchNumber\" type=\"text\" id=\"search9\" class=\"textBox textBoxMedium\" maxlength=\"60\" value=\""+TTKCommon.getHtmlString(strBatchNumber)+"\"></td>");
                    out.print("</tr>");
                    out.print("<tr>");
                }//end of if(strSwitchType.equals(strEnrollment))
                else if(strSwitchType.equals(strEndorsement)){
                	if(!( strSubLink.equals(strNonCorporatePolicy))){
                		out.print("<td nowrap> Batch No.:<br>");
                        out.print("<input name=\"sBatchNumber\" type=\"text\" id=\"search9\" class=\"textBox textBoxMedium\" maxlength=\"60\" value=\""+TTKCommon.getHtmlString(strBatchNumber)+"\"></td>");
                        out.print("</tr>");
                        out.print("<tr>");
                	}//end of if(!( strSubLink.equals(strNonCorporatePolicy)))
                	else{
                		/*out.print("<td>");
                        out.print("</td>");*/
                        out.print("</tr>");
                        out.print("<tr>");
                	}//end of else
                }//end of else
                
 
                if(strSwitchType.equals(strEnrollment))
                {
                    out.print("<td nowrap>Enrollment No.:<br>");
                    out.print("<input name=\"sEnrollmentNumber\" type=\"text\" id=\"search5\" class=\"textBox textBoxMedium\" maxlength=\"60\" onkeypress=\"ConvertToUpperCase(event.srcElement);\" value=\""+TTKCommon.getHtmlString(strEnrollmentNumber)+"\"></td>");
                   
                    out.print("<td nowrap>Member Name:<br>");
                    out.print("<input name=\"sMemberName\" type=\"text\" id=\"search6\" class=\"textBox textBoxMedium\" maxlength=\"60\" value=\""+TTKCommon.getHtmlString(strMemberName)+"\"></td>");
                }//end of if(strSwitchType.equals(strEnrollment))
               
                else if(strSwitchType.equals(strEndorsement))
                {
                    out.print("<td nowrap>Endorsement No.:<br>");
                    out.print("<input name=\"sEndorsementNumber\" type=\"text\" id=\"search5\" class=\"textBox textBoxMedium\" maxlength=\"60\" value=\""+TTKCommon.getHtmlString(strEndorsementNumber)+"\"></td>");
                    
                    out.print("<td nowrap>Cust. Endorsement No.:<br>");
                    out.print("<input name=\"sCustEndorsementNumber\" type=\"text\" id=\"search6\" class=\"textBox textBoxMedium\" maxlength=\"60\" value=\""+TTKCommon.getHtmlString(strCustEndorsementNumber)+"\"></td>");
                }//end of else if(strSwitchType.equals(strEndorsement))

                if(strSubLink.equals(strIndGrpPolicy))
                {
                    out.print("<td nowrap>Group Name:<br>");
                    out.print("<input name=\"sGroupName\" type=\"text\" id=\"search7\" class=\"textBox textBoxMedium textBoxDisabled\" readonly=\"true\" value=\""+TTKCommon.getHtmlString(strGroupName)+"\" maxlength=\"60\"></td>");
                }//end of if(strSubLink.equals(strIndGrpPolicy) || strSubLink.equals(strNonCorporatePolicy))
               
                else if(strSubLink.equals(strCorporatePolicy))
                {
                    out.print("<td nowrap>Corp. Name:<br>");
                    out.print("<input name=\"sCorporateName\" type=\"text\" id=\"search7\" class=\"textBox textBoxMedium textBoxDisabled\" readonly=\"true\" value=\""+TTKCommon.getHtmlString(strGroupName)+"\"></td>");
                }//end of else if(strSubLink.equals(strCorporatePolicy))
               
                else if(strSubLink.equals(strNonCorporatePolicy))
                {
                    out.print("<td nowrap>Policy Name:<br>");
                    out.print("<input name=\"sSChemeName\" type=\"text\" id=\"search7\" class=\"textBox textBoxMedium\" maxlength=\"60\" onkeypress=\"ConvertToUpperCase(event.srcElement);\" value=\""+TTKCommon.getHtmlString(strSChemeName)+"\"></td>");
                }//end of else if(strSubLink.equals(strCorporatePolicy))
                
//                if(strSwitchType.equals(strEnrollment)){
                	if(strSubLink.equals(strIndPolicy))
                    {	
    		                out.print("<td nowrap>Workflow:<br>");
    		                    out.print("<select name=\"sWorkFlow\"  id=\"search8\" class=\"selectBox selectBoxMedium\">");
    		                        out.print("<option value=\"\">Any</option>");
    		                        if(alWorkFlow!=null && alWorkFlow.size()>0)
    		                        {
    		                            for(int i=0;i<alWorkFlow.size();i++)
    		                            {
    		                                eventVO = (EventVO)alWorkFlow.get(i);
    		                                out.print("<option value='"+eventVO.getEventSeqID()+"' "+TTKCommon.isSelected(strWorkFlow, String.valueOf(eventVO.getEventSeqID()))+">"+eventVO.getEventName()+"</option>");
    		                            }//end of for(int i=0;i<alWorkFlow.size();i++)
    		                        }//end of if(alWorkFlow!=null && alWorkFlow.size()>0)
    		                    out.print("</select>");
    		                out.print("</td>");
    		                out.print("<td valign=\"bottom\" nowrap><a href=\"#\" accesskey=\"s\"  onClick=\"javascript:onSearch()\" class=\"search\"><img src=\"/ttk/images/SearchIcon.gif\" alt=\"Search\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\">&nbsp;<u>S</u>earch</a></td>");
    		            out.print("</tr>");
                    }//end of if(!(strSubLink.equals(strNonCorporatePolicy)))
                    else
                    {
                    	if(strSubLink.equals(strIndGrpPolicy) || (strSubLink.equals(strCorporatePolicy)))
                    	{
                    		out.print("<td nowrap>Group Id:<br>");
                    		if(strGroupId.equals(""))
                    		{
                    			out.print("<input type=\"text\" name=\"sGroupId\"  class=\"textBox textBoxSmall\"  id=\"search7\" maxlength=\"60\"> ");
                    		}//end of if(strGroupId.equals(""))
                    		else
                    		{
                    			out.print("<input type=\"text\" name=\"sGroupId\"  class=\"textBox textBoxSmall\"  id=\"search7\" maxlength=\"60\" value=\""+TTKCommon.getHtmlString(strGroupId)+"\" > ");
                    		}//end of else
                    		
                    		
                    		out.print("<a href=\"#\" accesskey=\"g\"  onClick=\"javascript:SelectGroup()\" class=\"search\"> ");
							out.print("<img src=\"/ttk/images/EditIcon.gif\" alt=\"Select Group\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\">&nbsp;");
							out.print("</a>");
							out.print("<a href=\"#\" onClick=\"ClearCorporate()\"><img src=\"/ttk/images/DeleteIcon.gif\" alt=\"Clear Group\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\"></a> </td>");
				       
							
						
							if(strSubLink.equals(strCorporatePolicy))
	                    	{
								 if(strSwitchType.equals(strEnrollment))
								 {
									 out.print("<td nowrap>Member Id:<br>");
									 out.print("<input type=\"text\" name=\"sMemberId\"  class=\"textBox textBoxMedium\"  id=\"search11\" maxlength=\"60\"  value=\""+TTKCommon.getHtmlString(strMemberId)+"\" ></td>");
								 }
                    		}
 						  
                    	
                    	}//if(strSubLink.equals(strIndPolicy))
                    	else
                    	{
                    		out.print("<td nowrap>Certificate No.:<br>");
                    		out.print("<input name=\"sCertificateNumber\" type=\"text\" id=\"search7\" class=\"textBox textBoxMedium\" maxlength=\"60\" onkeypress=\"ConvertToUpperCase(event.srcElement);\" value=\""+TTKCommon.getHtmlString(strCertificateNumber)+"\"></td>");
                    	}//end of else
                        out.print("</tr>");	
                        out.print("<tr>");
                        	
                        if(strSubLink.equals(strCorporatePolicy))
                    	{
							 if(strSwitchType.equals(strEnrollment))
							 {
								 out.print("<td nowrap>Resident Card Number:<br>");
								 out.print("<input type=\"text\" name=\"sEmiratesID\"  class=\"textBox textBoxMedium\"  id=\"search11\" maxlength=\"60\"  value=\""+TTKCommon.getHtmlString(strEmiratesID)+"\" ></td>");
							 }
                		}
                        
                        		 						
    	                    out.print("<td nowrap>Workflow:<br>");
    		                    out.print("<select name=\"sWorkFlow\"  id=\"search8\" class=\"selectBox selectBoxMedium\">");
    		                        out.print("<option value=\"\">Any</option>");
    		                        if(alWorkFlow!=null && alWorkFlow.size()>0)
    		                        {
    		                            for(int i=0;i<alWorkFlow.size();i++)
    		                            {
    		                                eventVO = (EventVO)alWorkFlow.get(i);
    		                                out.print("<option value='"+eventVO.getEventSeqID()+"' "+TTKCommon.isSelected(strWorkFlow, String.valueOf(eventVO.getEventSeqID()))+">"+eventVO.getEventName()+"</option>");
    		                            }//end of for(int i=0;i<alWorkFlow.size();i++)
    		                        }//end of if(alWorkFlow!=null && alWorkFlow.size()>0)
    		                    out.print("</select>");
    		                out.print("</td>");
    		                
    		                //AK
    		                if(strSubLink.equals(strCorporatePolicy))
	                    	{
								 if(strSwitchType.equals(strEnrollment))
								 {
									 out.print("<td nowrap>Product Name:<br>");
									 out.print("<input type=\"text\" name=\"ProductName\"  class=\"textBox textBoxMedium\"  id=\"search11\" maxlength=\"60\"  value=\""+TTKCommon.getHtmlString(strProductName)+"\" ></td>");
									
									 
									 out.print("<td nowrap>Product/Policy Authority:<br>");
			    		                out.print("<select name=\"productAuthority\" id=\"search3\" class=\"selectBox selectBoxMedium\">");
			    		                    out.print("<option value=\"\">Select from the list</option>");
			    		                    if(productAuthority!=null && productAuthority.size()>0)
			    		                    {
			    		                        for(int i=0;i<productAuthority.size();i++)
			    		                        {
			    		                            cacheObject = (CacheObject)productAuthority.get(i);
			    		                            out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strproductAuthority,cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
			    		                        }//end of for(int i=0;i<productAuthority.size();i++)
			    		                    }//end of if(productAuthority!=null && productAuthority.size()>0)
			    		                out.print("</select></td>");
								 }
                    		}
    		                
    		                if(strSubLink.equals(strCorporatePolicy))
	                    	{
								 if(strSwitchType.equals(strEndorsement))
								 {
									 out.print("<td nowrap>Product Name:<br>");
									 out.print("<input type=\"text\" name=\"ProductName\"  class=\"textBox textBoxMedium\"  id=\"search11\" maxlength=\"60\"  value=\""+TTKCommon.getHtmlString(strProductName)+"\" ></td>");
									 out.print("<td nowrap>Product/Policy Authority:<br>");
			    		                out.print("<select name=\"productAuthority\" id=\"search3\" class=\"selectBox selectBoxMedium\">");
			    		                    out.print("<option value=\"\">Select from the list</option>");
			    		                    if(productAuthority!=null && productAuthority.size()>0)
			    		                    {
			    		                        for(int i=0;i<productAuthority.size();i++)
			    		                        {
			    		                            cacheObject = (CacheObject)productAuthority.get(i);
			    		                            out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strproductAuthority,cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
			    		                        }//end of for(int i=0;i<productAuthority.size();i++)
			    		                    }//end of if(productAuthority!=null && productAuthority.size()>0)
			    		                out.print("</select></td>");
								 }
                    		}
    		                
    		                
    		                
    		                //Product Authority
    		               /* out.print("<td nowrap>Product Authority:<br>");
    		                out.print("<select name=\"productAuthority\" id=\"search3\" class=\"selectBox selectBoxMedium\">");
    		                    out.print("<option value=\"\">Select from the list</option>");
    		                    if(productAuthority!=null && productAuthority.size()>0)
    		                    {
    		                        for(int i=0;i<productAuthority.size();i++)
    		                        {
    		                            cacheObject = (CacheObject)productAuthority.get(i);
    		                            out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(strproductAuthority,cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
    		                        }//end of for(int i=0;i<productAuthority.size();i++)
    		                    }//end of if(productAuthority!=null && productAuthority.size()>0)
    		                out.print("</select></td>");*/
    		                
    		                //EAK
    		                
    		                out.print("<td valign=\"bottom\" nowrap><a href=\"#\" accesskey=\"s\"  onClick=\"javascript:onSearch()\" class=\"search\"><img src=\"/ttk/images/SearchIcon.gif\" alt=\"Search\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\">&nbsp;<u>S</u>earch</a></td>");
    	                out.print("</tr>");
                    }//end of else
//                }//end of if(strSwitchType.equals(strEnrollment))
                
                
            out.print("</table>");
        }//end of try
        catch(Exception exp)
        {
            throw new JspException("Error: in PolicySearchBox Tag Library!!!" + exp.getMessage());
        }//end of catch(Exception exp)
        return SKIP_BODY;
    }//end of doStartTag()

    /**
     * this method will be executed before  tag closes
     * @return int
     * @throws JspException
     */
    public int doEndTag() throws JspException
    {
        return EVAL_PAGE;//to process the rest of the page
    }//end doEndTag()
}//end of PolicySearchBox