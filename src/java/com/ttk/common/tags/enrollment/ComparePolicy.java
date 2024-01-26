/**
 * @ (#) ComparePolicy.java Feb 23, 2006
 * Project       : TTK HealthCare Services
 * File          : ComparePolicy.java
 * Author        : Krupa J
 * Company       : Span Systems Corporation
 * Date Created  : Feb 23, 2006
 *
 * @author       : Krupa J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

package com.ttk.common.tags.enrollment;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
//import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;
import com.ttk.dto.enrollment.MemberDetailVO;

public class ComparePolicy extends TagSupport 
{
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
//    private static Logger log = Logger.getLogger( ComparePolicy.class );  
     
    public int doStartTag() throws JspException
    {
        try
        {
            JspWriter out = pageContext.getOut();
            String strSelectedClass="";
            String strCaption1="";
            String strCaption2=""; 
            String strGridClass="";
            MemberDetailVO selectedVO=null;
            MemberDetailVO memberDetailVO=null;
           
            //get the Policy Nos and Enrollment Ids from the session   
            String strPolicyNo=((String)pageContext.getSession().getAttribute("strPolicyNo"));
            String strEnrollmentID=((String)pageContext.getSession().getAttribute("strEnrollmentId"));
            String strPolicyNos[] = strPolicyNo.split("\\|");
            String strEnrollmentIDs[] = strEnrollmentID.split("\\|");      
            HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
            ArrayList alPolicyList=(ArrayList)pageContext.getSession().getAttribute("alPolicy");
            //get the referance of the Form Bean from the session
            DynaActionForm frmOtherPolicy=(DynaActionForm)request.getSession().getAttribute("frmOtherPolicy");
            memberDetailVO=(MemberDetailVO)alPolicyList.get(0);
            String strCaption=(String)frmOtherPolicy.get("caption");
            String strCap[]=strCaption.split("\\]");
            String strCap1[]=strCap[0].split("\\[");
            String strCap2[]=strCap[1].split("\\[");
            
            for(int i=1;i<=alPolicyList.size();i++)
            {
                switch(i)
                {
                
                case 1: strSelectedClass="cnt-pol-1";
                        strGridClass="gridHeaderHighlighted";
                        strCaption1 =strCap1[1]+" (Original) ";
                        strCaption2 = strCap2[1];   
                        selectedVO=(MemberDetailVO)alPolicyList.get(0);
                        break;        
                case 2: strSelectedClass="cnt-pol-2";
                        strGridClass="gridHeader";
                        strCaption1 = strPolicyNos[0];
                        strCaption2 = strEnrollmentIDs[0];
                        selectedVO=(MemberDetailVO)alPolicyList.get(1);
                        break;
                case 3: strSelectedClass="cnt-pol-3";
                        strGridClass="gridHeader";                
                        strCaption1 = strPolicyNos[1];
                        strCaption2 = strEnrollmentIDs[1]; 
                        selectedVO=(MemberDetailVO)alPolicyList.get(2);
                        break;
                }//end of switch(i) 
           
                String strSelectedClass1=comparePolicy(memberDetailVO.getName(), selectedVO.getName());
                String strSelectedClass2=comparePolicy(memberDetailVO.getAge().toString(), selectedVO.getAge().toString());
                String strSelectedClass3=comparePolicy(memberDetailVO.getDOB(), selectedVO.getDOB());
                String strSelectedClass4=comparePolicy(memberDetailVO.getGenderTypeID(), selectedVO.getGenderTypeID());
                String strSelectedClass5=comparePolicy(memberDetailVO.getOccupation(), selectedVO.getOccupation());
                String strSelectedClass6=comparePolicy(memberDetailVO.getDateOfIncept(), selectedVO.getDateOfIncept());
                String strSelectedClass7=comparePolicy(memberDetailVO.getMemberAddressVO().getAddress1(), selectedVO.getMemberAddressVO().getAddress1());
                String strSelectedClass8=comparePolicy(memberDetailVO.getMemberAddressVO().getAddress2(), selectedVO.getMemberAddressVO().getAddress2());
                String strSelectedClass9=comparePolicy(memberDetailVO.getMemberAddressVO().getAddress3(), selectedVO.getMemberAddressVO().getAddress3());
                String strSelectedClass10=comparePolicy(memberDetailVO.getMemberAddressVO().getCityDesc(), selectedVO.getMemberAddressVO().getCityDesc());
                String strSelectedClass11=comparePolicy(memberDetailVO.getMemberAddressVO().getStateName(), selectedVO.getMemberAddressVO().getStateName());
                String strSelectedClass12=comparePolicy(memberDetailVO.getMemberAddressVO().getPinCode(), selectedVO.getMemberAddressVO().getPinCode());
                String strSelectedClass13=comparePolicy(memberDetailVO.getMemberAddressVO().getCountryName(), selectedVO.getMemberAddressVO().getCountryName());
                   
                out.print("<div class="+strSelectedClass+">");
                out.print("<table border=\"0\" cellspacing=\"0\" cellpadding=\"3\">");
                out.print("<tr><td width=\"25%\" align=\"left\" class="+strGridClass+">"+strCaption1+"<br>"+strCaption2+" </td></tr>");     
                out.print("<tr class=\"gridEvenRow\"><td height=\"20\" align=\"left\" class="+ strSelectedClass1+">"+selectedVO.getName()+"&nbsp;</td></tr>");        
                out.print("<tr class=\"gridOddRow\"><td height=\"20\" align=\"left\" class="+strSelectedClass2 +">"+selectedVO.getAge()+"&nbsp;</td></tr>");      
                out.print("<tr class=\"gridEvenRow\"><td height=\"20\" align=\"left\" class="+strSelectedClass3 +">"+selectedVO.getDOB()+"&nbsp;</td></tr>");
                out.print("<tr class=\"gridOddRow\"><td height=\"20\" align=\"left\" class="+strSelectedClass4 +">"+selectedVO.getGenderTypeID()+"&nbsp;</td></tr>");
                out.print("<tr class=\"gridEvenRow\"><td height=\"20\" align=\"left\" class="+strSelectedClass5 +">"+selectedVO.getOccupation()+"&nbsp;</td></tr>");
                out.print("<tr class=\"gridOddRow\"><td height=\"20\" align=\"left\" class="+strSelectedClass6 +">"+selectedVO.getDateOfIncept()+"&nbsp;</td></tr>");
                out.print("<tr class=\"gridEvenRow\"><td height=\"20\" align=\"left\" class="+strSelectedClass7 +">"+selectedVO.getMemberAddressVO().getAddress1()+"&nbsp;</td></tr>");
                out.print("<tr class=\"gridOddRow\"><td height=\"20\" align=\"left\" class="+strSelectedClass8 +">"+selectedVO.getMemberAddressVO().getAddress2()+"&nbsp;</td></tr>");
                out.print("<tr class=\"gridEvenRow\"><td height=\"20\" align=\"left\" class="+strSelectedClass9 +">"+selectedVO.getMemberAddressVO().getAddress3()+"&nbsp;</td></tr>");
                out.print("<tr class=\"gridOddRow\"><td height=\"20\" align=\"left\" class="+strSelectedClass10 +">"+selectedVO.getMemberAddressVO().getCityDesc()+"&nbsp;</td></tr>");
                out.print("<tr class=\"gridEvenRow\"><td height=\"20\" align=\"left\" class="+strSelectedClass11 +">"+selectedVO.getMemberAddressVO().getStateName()+"&nbsp;</td></tr>");
                out.print("<tr class=\"gridOddRow\"><td height=\"20\" align=\"left\" class="+strSelectedClass12 +">"+selectedVO.getMemberAddressVO().getPinCode()+"&nbsp;</td></tr>");
                out.print("<tr class=\"gridEvenRow\"><td height=\"20\" align=\"left\" class="+strSelectedClass13 +">"+selectedVO.getMemberAddressVO().getCountryName()+"&nbsp;</td></tr>");
                out.print("</table>");
                out.print("</div>");               
            }//end of for(int i=1;i<alPolicyList.size();i++)
        }//end of try
        catch(Exception exp)    
        {
            exp.printStackTrace();           
        }//end of catch block
        return SKIP_BODY;
    }//end of doStartTag
    
    /**
     * This method compares the different policy details
     * @param str1 String which contains the original policy details
     * @param str2 String which contains the selected policy details
     * */
    public String comparePolicy(String str1, String str2)
    {
        String strSelectedClass="";
        if(str1.compareToIgnoreCase(str2)!= 0)
        {
             strSelectedClass = "comparisonDiscrepancy";
        }//end of if(str1.compareToIgnoreCase(str2)!= 0)
        return  strSelectedClass;
        
    }//end of comparePolicy(String str1, String str2)
}//end of ComparePolicy