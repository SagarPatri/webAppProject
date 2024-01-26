/**
 * @ (#) ClaimantDetailHistory.java Aug 11, 2006
 * Project 		: TTK HealthCare Services
 * File 		: ClaimantDetailHistory.java
 * Author 		: Raghavendra T M
 * Company 		: Span Systems Corporation
 * Date Created : Aug 11, 2006
 *
 * @author 		: Raghavendra T M
 * Modified by 	:
 * Modified date:
 * Reason 		:
*/

package com.ttk.common.tags.preauth;

import java.util.List;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ttk.common.TTKCommon;

/**
 *  This class builds the Claims Details in the Claim History
 */

public class ClaimantDetailHistory extends TagSupport{

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    private static Logger log = Logger.getLogger( ClaimantDetailHistory.class );

    public int doStartTag() throws JspException{
        try
        {
        	   HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
        	 String strActiveLink=TTKCommon.getActiveLink(request);//added as per Hospital Login
             String strActiveSubLink=TTKCommon.getActiveSubLink(request);//added as per Hospital Login

//           HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
           Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
           JspWriter out = pageContext.getOut();//Writer object to write the file
           List  claimantList=null;
           Element claimantElement=null;
           List  claimShrtFallList=null;
           Element claimShrtFallElement=null;
           List  claimList=null;
           Element claimElement=null;
//           List  approvalstatusList=null;
//           Element approvalstatusElement=null;
           if(historyDoc != null)
           {
        	   if(!((List)historyDoc.selectNodes("//shortfall")).isEmpty())
               {
        		   claimShrtFallList = (List)historyDoc.selectNodes("//shortfall");
               }
        	   if(!((List)historyDoc.selectNodes("//claimdetails")).isEmpty())
               {
                   claimList = (List)historyDoc.selectNodes("//claimdetails");
                   claimElement = (Element)claimList.get(0);
               }    
        	   if(!((List)historyDoc.selectNodes("//claimantdetails")).isEmpty())
               {
                   claimantList= (List)historyDoc.selectNodes("//claimantdetails");
                   claimantElement = (Element)claimantList.get(0);
                   out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                   	out.print("<tr>");
	                   if(claimShrtFallList !=null && claimShrtFallList.size() >0)
	                   {
	                	   claimShrtFallElement = (Element)claimShrtFallList.get(0);
	                	   //out.println("CignaYN--:"+claimShrtFallElement.valueOf("@cigna_yn"));
		                   if("Y".equals(claimShrtFallElement.valueOf("@shortfall")))
	                       {	
		                	   out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
			                   out.print("<a href=\"#\" onClick=\"javascript:online_Claims_shortfall('"+claimShrtFallElement.valueOf("@seq_id")+"','"+claimShrtFallElement.valueOf("@cigna_yn")+"');\"><font size=\"2\">Shortfall</font></a>");
			                   out.print("</td>");
	                       }//end of if("Y".equals(claimShrtFallElement.valueOf("@shortfall")))
		                   else
		                   {
		                	   out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
			                   out.print("</td>");
		                   }//end of else
		                   //if(claimElement.valueOf("@rejectamtexist").equalsIgnoreCase("Y"))
	                   }//end of if(claimShrtFallList !=null && claimShrtFallList.size() >0)
	                   if("Y".equals(claimantElement.valueOf("@rejectamtexist")))
	                   {	
	                	   out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
	                	   out.print("<a href=\"#\" onClick=\"javascript:online_DisallowedBill('"+claimElement.valueOf("@claimseqid")+"','"+claimantElement.valueOf("@enrollmentid")+"','"+claimElement.valueOf("@claimnumber")+"','"+claimElement.valueOf("@settlementnumber")+"');\"><font size=\"2\"><b>Disallowed Bill</b></font></a>");
		                   out.print("</td>");
	                   }//end of if("Y".equals(claimShrtFallElement.valueOf("@shortfall")))
	                   else
	                   {
	                	   out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
		                   out.print("</td>");
	                   }//end of else	
	                   out.print("<td class=\"textLabelBold\" width=\"25%\">");
	                   out.print("</td>");
	                   out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
	                   out.print("</td>");
	                   out.print("<td class=\"textLabel\" width=\"31%\">");
	                   out.print("</td>");
                   	out.print("</tr>");
                   out.print("</table>");
                   out.print("<fieldset>");
                       out.print("<legend>Member Details</legend>");
                       out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                           out.print("<tr>");
                               out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
                                   out.print("Enrollment Id:");
                               out.print("</td>");
                               out.print("<td class=\"textLabelBold\" width=\"25%\">");
                                   out.print(claimantElement.valueOf("@enrollmentid"));
                               out.print("</td>");
                               out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
                               	   out.print("Member Name:");
                               out.print("</td>");
                               out.print("<td class=\"textLabel\" width=\"31%\">");
                               		out.print(claimantElement.valueOf("@claimantname"));
                               out.print("</td>");
                            out.print("</tr>");

                            out.print("<tr>");
                                out.print("<td height=\"25\" class=\"formLabel\">");
                                    out.print("Gender:");
                                out.print("</td>");
                                out.print("<td class=\"textLabel\">");
                                    out.print(claimantElement.valueOf("@gender"));
                                out.print("</td>");
                                out.print("<td>");
                                    out.print("Age (Yrs):");
                                out.print("</td>");
                                out.print("<td class=\"textLabel\">");
                                    out.print(claimantElement.valueOf("@age"));
                                out.print("</td>");
                            out.print("</tr>");

                            out.print("<tr>");
                                out.print("<td height=\"25\" class=\"formLabel\">");
                                    out.print("Date of Inception:");
                                out.print("</td>");
                                out.print("<td class=\"textLabel\">");
                                    out.print(claimantElement.valueOf("@dateofinception"));
                                out.print("</td>");
                                out.print("<td class=\"formLabel\">");
                                    out.print("Date of Exit:");
                                out.print("</td>");
                                out.print("<td class=\"textLabel\">");
                                    out.print(claimantElement.valueOf("@dateofexit"));
                                out.print("</td>");
                            out.print("</tr>");

                            out.print("<tr>");
                                out.print("<td height=\"25\" class=\"formLabel\">");
                                    out.print("Total Sum Insured (Rs):");
                                out.print("</td>");
                                out.print("<td class=\"textLabel\">");
                                    out.print(claimantElement.valueOf("@totalsuminsured"));
                                out.print("</td>");
                                out.print("<td height=\"20\" class=\"formLabel\">");
                                    out.print("Bal. Sum Insured (Rs):");
                                out.print("</td>");
                                out.print("<td class=\"textLabel\">");
                                    out.print(claimantElement.valueOf("@availablesum"));
                                out.print("</td>");
                           out.print("</tr>");
                        
                           out.print("<tr>");
	                           out.print("<td height=\"25\" class=\"formLabel\">");
	                         // out.print("Avail. Buffer Amt. (Rs):");
	                   		    out.print("Total Appr. Buffer Amt. (Rs):");//<!modified for  hyundai buffer cr--->
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\">");
	                             /*  out.print(claimantElement.valueOf("@availablebuffer"));*/
	                           out.print(claimantElement.valueOf("@totalappbufferamount"));
	                           out.print("</td>");
	                           out.print("<td height=\"20\" class=\"formLabel\">");
	                               out.print("Total Utilized Buffer Amt (Rs):");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\">");
	                               out.print(claimantElement.valueOf("@totalutilbufferamount"));
	                           out.print("</td>");
                           out.print("</tr>");

	                      out.print("<tr>");
		                      out.print("<td height=\"25\" class=\"formLabel\">");
		                          out.print("Employee No.:");
		                      out.print("</td>");
		                      out.print("<td class=\"textLabel\">");
		                          out.print(claimantElement.valueOf("@employeeno"));
		                      out.print("</td>");
		                      out.print("<td height=\"20\" class=\"formLabel\">");
		                          out.print("Employee Name:");
		                      out.print("</td>");
		                      out.print("<td class=\"textLabel\">");
		                          out.print(claimantElement.valueOf("@employeename"));
		                      out.print("</td>");
	                      out.print("</tr>");

		                 out.print("<tr>");
			                 out.print("<td height=\"25\" class=\"formLabel\">");
			                     out.print("Relationship:");
			                 out.print("</td>");
			                 out.print("<td class=\"textLabel\">");
			                     out.print(claimantElement.valueOf("@relationship"));
			                 out.print("</td>");
			                 out.print("<td height=\"20\" class=\"formLabel\">");
			                     out.print("Member Phone No.:");
			                 out.print("</td>");
			                 out.print("<td class=\"textLabel\">");
			                     out.print(claimantElement.valueOf("@claimantphone"));
			                 out.print("</td>");
		                 out.print("</tr>");
		                 
		                 out.print("<tr>");
		                 out.print("<td height=\"25\" class=\"formLabel\">");
		                     out.print("Diabetes Cover:");
		                 out.print("</td>");
		                 out.print("<td class=\"textLabel\">");
		                 out.print(claimantElement.valueOf("@diabetes_cover_yn"));
		                 out.print("</td>");
		                 out.print("<td height=\"20\" class=\"formLabel\">");
		                     out.print("Hypertension Cover:");
		                 out.print("</td>");
		                 out.print("<td class=\"textLabel\">");
		                 out.print(claimantElement.valueOf("@hypertension_cover_yn"));
		                 out.print("</td>");
		                 out.print("</tr>");
		                 
		                 out.print("<tr>");
		                 out.print("<td height=\"25\" class=\"formLabel\">");
	                     out.print("Cumulative Bonus (Rs):");
	                     out.print("</td>");
	                     out.print("<td class=\"textLabel\">");
	                     out.print(claimantElement.valueOf("@availablebonus"));
                         out.print("</td>");
                         out.print("</tr>");
	                 out.print("</table>");
                 out.print("</fieldset>");
             }//end of if(!((List)historyDoc.selectNodes("//claimantdetails")).isEmpty())

               if(!((List)historyDoc.selectNodes("//claimdetails")).isEmpty())
               {
                   claimList = (List)historyDoc.selectNodes("//claimdetails");
                   claimElement = (Element)claimList.get(0);
                   String prehosp="",posthosp="",hosp="";
                   out.print("<fieldset>");
                   	out.print("<legend>Claim Details</legend>");
                   		out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	                       out.print("<tr>");
	                           out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
	                               out.print("Claim No.:");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabelBold\" width=\"25%\">");
	                               out.print(claimElement.valueOf("@claimnumber"));
	                           out.print("</td>");
	                           out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
	                           	   out.print("Claim File No.:");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\" width=\"31%\">");
	                           		out.print(claimElement.valueOf("@claimfileno"));
	                           out.print("</td>");
	                        out.print("</tr>");

	                        out.print("<tr>");
	                            out.print("<td height=\"25\" class=\"formLabel\">");
	                                out.print("Request Type:");
	                            out.print("</td>");
	                            out.print("<td class=\"textLabel\">");
	                                out.print(claimElement.valueOf("@requesttype"));
	                            out.print("</td>");
	                            out.print("<td>&nbsp;</td>");
	                            out.print("<td class=\"textLabel\">&nbsp;</td>");
	                        out.print("</tr>");

	                        out.print("<tr>");
	                           out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
	                               out.print("Claim Type:");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\" width=\"25%\">");
	                               out.print(claimElement.valueOf("@claimtype"));
	                           out.print("</td>");
	                           out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
	                           	   out.print("Claim Sub Type:");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\" width=\"31%\">");
	                           		out.print(claimElement.valueOf("@claimsubtype"));
	                           out.print("</td>");
	                        out.print("</tr>");
	                        //opd_4_hospital                        
	                        
	                        out.print("<tr>");
	                        out.print("<td>&nbsp;</td>");
                            out.print("<td class=\"textLabel\">&nbsp;</td>");
                            out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
                     	   	out.print("Payment To:");
                     	   	out.print("</td>");
                     	   	out.print("<td class=\"textLabel\" width=\"31%\">");
                     		out.print(claimElement.valueOf("@paymentto"));
                     		out.print("</td>");                         
                     		out.print("</tr>");
	                        
                     		//opd_4_hospital 
	                        out.print("<tr>");
	                           out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
	                               out.print("Intimation Date:");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\" width=\"25%\">");
	                               out.print(claimElement.valueOf("@intimationdate"));
	                           out.print("</td>");
	                           out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
	                           	   out.print("Mode:");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\" width=\"31%\">");
	                           		out.print(claimElement.valueOf("@mode"));
	                           out.print("</td>");
	                        out.print("</tr>");

	                        out.print("<tr>");
	                           out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
	                               out.print("Received Date / Time:");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\" width=\"25%\">");
	                               out.print(claimElement.valueOf("@rcvddate"));
	                           out.print("</td>");
	                           out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
	                           	   out.print("Requested Amt. (Rs):");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\" width=\"31%\">");
	                           		out.print(claimElement.valueOf("@requestedamount"));
	                           out.print("</td>");
	                        out.print("</tr>");

	                        out.print("<tr>");
	                           out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
	                               out.print("Treating Doctor's Name:");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\" width=\"25%\">");
	                               out.print(claimElement.valueOf("@doctorname"));
	                           out.print("</td>");
	                           out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
	                           	   out.print("In Patient No.");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\" width=\"31%\">");
	                           		out.print(claimElement.valueOf("@inpatiantname"));
	                           out.print("</td>");
	                        out.print("</tr>");

	                        out.print("<tr>");
	                        	out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
	                        		out.print("Admission Date / Time:");
	                        	out.print("</td>");
	                        	out.print("<td class=\"textLabel\" width=\"25%\">");
	                        		out.print(claimElement.valueOf("@dateofadmission"));
	                        	out.print("</td>");
	                        	out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
	                        		out.print("Discharge Date / Time:");
	                        	out.print("</td>");
	                        	out.print("<td class=\"textLabel\" width=\"31%\">");
                        			out.print(claimElement.valueOf("@dateofdischarge"));
                        		out.print("</td>");
                        	out.print("</tr>");

	                        out.print("<tr>");
	                           out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
	                               out.print("Vidal Health Branch:");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\" width=\"25%\">");
	                               out.print(claimElement.valueOf("@ttkbranch"));
	                           out.print("</td>");
	                           out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
	                           	   out.print("Processing Branch:");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\" width=\"31%\">");
	                           		out.print(claimElement.valueOf("@processingbranch"));
	                           out.print("</td>");
	                        out.print("</tr>");

	                        out.print("<tr>");
	                           out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
	                               out.print("Assigned To:");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\" width=\"25%\">");
	                               out.print(claimElement.valueOf("@assignedto"));
	                           out.print("</td>");
	                           out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
	                           	   out.print("Pre-Hospitalisation :");
	                           out.print("</td>");
	                           if(Integer.parseInt(claimElement.valueOf("@prehospitalization"))>0)
                       			prehosp="checked";
                       		   if(Integer.parseInt(claimElement.valueOf("@posthospitalization"))>0)
                       			posthosp="checked";
                       		   if(Integer.parseInt(claimElement.valueOf("@hospitalization"))>0)
                       			hosp="checked";
	                           out.print("<td class=\"textLabel\" width=\"31%\">");
	                           		out.print("<input type=\"checkbox\" disabled=\"true\" "+prehosp+" >");
	                           out.print("</td>");
	                        out.print("</tr>");

	                        out.print("<tr>");
	                           out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
	                           		out.print("Post-Hospitalisation:");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\" width=\"25%\">");
	                           		out.print("<input type=\"checkbox\" disabled=\"true\" "+posthosp+" >");
	                           out.print("</td>");
	                           out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
	                           		out.print("Hospitalisation :");
	                           out.print("</td>");
	                           out.print("<td class=\"textLabel\" width=\"31%\">");
	                           		out.print("<input type=\"checkbox\" disabled=\"true\" "+hosp+" >");
	                           out.print("</td>");
	                        out.print("</tr>");

	                        out.print("<tr>");
                            out.print("<td height=\"25\" class=\"formLabel\">");
                                out.print("Remarks:");
                            out.print("</td>");
                            out.print("<td class=\"textLabel\">");
                                out.print(claimElement.valueOf("@claimsremarks"));
                            out.print("</td>");
                            out.print("<td>&nbsp;</td>");
                            out.print("<td class=\"textLabel\">&nbsp;</td>");
                        out.print("</tr>");
                        if(strActiveLink.equalsIgnoreCase("Hospital Information")){   
                        	  if(strActiveSubLink.equalsIgnoreCase("Claims"))  	  {
                        		  if(claimElement.valueOf("@completed").equalsIgnoreCase("Y"))  {
                        			  	out.print("</table>");
                        			  	out.print("<table align=\"center\" class=\"buttonsContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                        			  	out.print("<tr>");
                        			  	out.print("<td align=\"center\">");
                        			  	out.print("<button type=\"button\" name=\"Button\" accesskey=\"g\" id=\"authletter\" class=\"buttons\" style=\"display:\" onMouseout=\"this.className='buttons'\" onMouseover=\"this.className='buttons buttonsHover'\" onClick=\"javascript:onReport();\"><u>G</u>enerate Letter</button>");//&nbsp;
                        			  	out.print("<td>");
                                        out.print("</tr>");
                                        out.print("</table>");
                                    	out.print("<input type=\"hidden\" name=\"authLtrTypeID\" id=\"authLtrTypeID\" value=\""+claimElement.valueOf("@authtypeid")+"\"/>");out.print("</br>");
                                    	out.print("<input type=\"hidden\" name=\"claimSeqID\" id=\"claimSeqID\" value=\""+claimElement.valueOf("@seqid")+"\"/>");out.print("</br>");
                                		out.print("<input type=\"hidden\" name=\"statusTypeID\" id=\"statusTypeID\" value=\""+claimElement.valueOf("@statusid")+"\"/>");out.print("</br>");
                        				out.print("<input type=\"hidden\" name=\"completedYN\" id=\"completedYN\" value=\""+claimElement.valueOf("@completed")+"\"/>");out.print("<br>");
                        				out.print("<input type=\"hidden\" name=\"cignastatusYN\" id=\"cignastatusYN\"  value=\""+claimElement.valueOf("@cignastatus")+"\"/>");out.print("<br>");
                        				out.print("<input type=\"hidden\" name=\"authorizationNo\" id=\"authorizationNo\"  value=\""+claimElement.valueOf("@authorizationnumber")+"\"/>");out.print("</br>");
                        				out.print("<input type=\"hidden\" name=\"preAuthNo\" id=\"preAuthNo\" value=\""+claimElement.valueOf("@claimnumber")+"\"/>");out.print("<br>");
                        		  }//end of if(claimElement.valueOf("@completed").equalsIgnoreCase("Y"))  {
                      	     }//end of if(strActiveSubLink.equalsIgnoreCase("Claims")) 
                      	  }//end of  if(strActiveLink.equalsIgnoreCase("Hospital Information"))
                     
                      
                      
                      	
                      out.print("</fieldset>");
                      
                   
        }//end of if(!((List)historyDoc.selectNodes("//claimdetails")).isEmpty())

       }//end of if(historyDoc != null)
    }//end of try block
    catch(Exception exp)
    {
        exp.printStackTrace();
        log.debug("error occured in ClaimsDetails Tag Library!!!!! ");
    }//end of catch block
    return SKIP_BODY;
}//end of doStartTag()
public int doEndTag() throws JspException {
    return EVAL_PAGE;//to process the rest of the page
}//end doEndTag()
}//end of class ClaimantDetailHistory