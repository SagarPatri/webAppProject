/**
 
 * @ (#) OnlinePreAuthHistory.java Sep 27,2007
 * Project s TTK HealthCare Services
 * File : OnlinePreAuthHistory.java
 * Author : Balaji C R B
 * Company : Span Systems Corporation
 * Date Created : MSep 27,2007
 *
 * @author : Balaji C R B
 * Modified by :
 * Modified date :
 * Reason :
 */

package com.ttk.common.tags.onlineforms;

//import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
//import org.apache.log4j.Logger;
import org.dom4j.Document;
//import org.dom4j.DocumentHelper;
import org.dom4j.Element;
//added for KOC-1267
import com.ttk.dto.common.Toolbar;


/**
 *  This class builds the PreAuth Details Pre-Auth History
 */

public class OnlineClaimHistory extends TagSupport{
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
//	private static Logger log = Logger.getLogger( OnlinePreAuthHistory.class );
	
	public int doStartTag() throws JspException{
		try
		{
			
			Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
			//added for KOC-1267
			String LoginType = pageContext.getRequest().getAttribute("LoginType").toString();
			
			String UserId = pageContext.getRequest().getAttribute("UserId").toString();
			
			String rolename ="";
			if(LoginType.equalsIgnoreCase("E"))
			{
				rolename = "EMPLOYEE";
			}
			else if(LoginType.equalsIgnoreCase("I"))
			{
				rolename = "INDIVIDUAL";
			}
			//else if(LoginType.equalsIgnoreCase("H"))
			//{
				//rolename = "HR";
			//}
			else if(LoginType.equalsIgnoreCase("OCI"))
			{
				rolename = "External";
			}
		   Toolbar toolBar = null;
	       toolBar = (Toolbar)pageContext.getSession().getAttribute("toolbar");
	       String strURL=toolBar.getWebDocUrl();
	       String strQueryString=toolBar.getQueryString();
	       StringBuffer strQuery= new StringBuffer();
	       strQuery = strQuery.append(strURL).append(strQueryString);
           //Ended.
			/*log.info("hai trying to print xml");
			log.info(historyDoc.asXML());*/
           //HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			JspWriter out = pageContext.getOut();//Writer object to write the file
					
			Element claimElement=null;
			//Element claimShrtFallElement=null;
			List  claimdetailslist=null;
			//List  claimShrtFalllist=null;
			List  chequeList=null;
	        Element chequeElement=null;
	        String strClass="";
			if(historyDoc != null)
			{
				if(!((List)historyDoc.selectNodes("//claimdetails")).isEmpty())
				{
					claimdetailslist = (List)historyDoc.selectNodes("//claimdetails");
					for(int i=0;(claimdetailslist!=null && i<claimdetailslist.size());i++)
					{
						claimElement = (Element)claimdetailslist.get(i);
						out.print("<fieldset>");
						out.print("<legend>Claim Details</legend>");	
						out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
						out.print("<tr>");
						//added for KOC-1267
						//if(UserId=="")
						//{
							//out.print("<a href=\"#\" id=\"docviewer\" onclick=\"javascript:onDocumentViewer('"+strQuery+"&claimno="+claimElement.valueOf("@claimnumber")+"&dms_reference_number="+claimElement.valueOf("@claim_dms_reference_id")+"&userid="+claimElement.valueOf("@User_Id")+"&roleid="+rolename+"');\"><img src=\"/ttk/images/DocViewIcon.gif\" alt=\"Launch Document Viewer\" width=\"16\" height=\"12\" border=\"0\" align=\"absmiddle\" class=\"icons\"></a>&nbsp;&nbsp;&nbsp;");
							
							//out.print("<a href=\"#\" id=\"docviewer\" onclick=\"javascript:onDocumentViewer('"+strQuery+"&claimno="+claimElement.valueOf("@claimnumber")+"&dms_reference_number="+claimElement.valueOf("@claim_dms_reference_id")+"&userid="+claimElement.valueOf("@User_Id")+"&roleid="+rolename+"');\"><font size=\"2\"><b>VIEW CLAIM DOCUMENT</b></font></a>&nbsp;&nbsp;&nbsp;");
							
						//}
						//added for KOC-1267
						if(!(LoginType.equalsIgnoreCase("H")||LoginType.equalsIgnoreCase("B")))//kocbroker
                        
						{
							if(UserId=="")
							{	//out.print("<a href=\"#\" id=\"docviewer\" onclick=\"javascript:onDocumentViewer('"+strQuery+"&claimno="+claimElement.valueOf("@claimnumber")+"&dms_reference_number="+claimElement.valueOf("@claim_dms_reference_id")+"&userid="+claimElement.valueOf("@User_Id")+"&roleid="+rolename+"');\"><img src=\"/ttk/images/DocViewIcon.gif\" alt=\"Launch Document Viewer\" width=\"16\" height=\"12\" border=\"0\" align=\"absmiddle\" class=\"icons\"></a>&nbsp;&nbsp;&nbsp;");
								out.print("<a href=\"#\" id=\"docviewer\" onclick=\"javascript:onDocumentViewer('"+strQuery+"&claimno="+claimElement.valueOf("@claimnumber")+"&dms_reference_number="+claimElement.valueOf("@claim_dms_reference_id")+"&userid="+claimElement.valueOf("@User_Id")+"&roleid="+rolename+"');\"><font size=\"2\"><b>VIEW CLAIM DOCUMENT</b></font></a>&nbsp;&nbsp;&nbsp;");
							}
							else 
							{
							out.print("<a href=\"#\" id=\"docviewer\" onclick=\"javascript:onDocumentViewer('"+strQuery+"&claimno="+claimElement.valueOf("@claimnumber")+"&dms_reference_number="+claimElement.valueOf("@claim_dms_reference_id")+"&userid="+UserId+"&roleid="+rolename+"');\"><font size=\"2\"><b>VIEW CLAIM DOCUMENT</b></font></a>&nbsp;&nbsp;&nbsp;");
							}
						}
						//added new for kocbroker
						if((claimElement.valueOf("@status").equalsIgnoreCase("Approved")) && LoginType.equalsIgnoreCase("B"))
						{
							out.print("<a href=\"#\" onClick=\"javascript:online_Settlement_letter('"+claimElement.valueOf("@seq_id")+"');\"><font size=\"2\"><b>Claim Settlement Letter</b></font></a>");
						}
						//Ended.
						//else
						//{
							//out.print("<a href=\"#\" id=\"docviewer\" onclick=\"javascript:onDocumentViewer('"+strQuery+"&claimno="+claimElement.valueOf("@claimnumber")+"&dms_reference_number="+claimElement.valueOf("@claim_dms_reference_id")+"&userid="+UserId+"&roleid="+rolename+"');\"><font size=\"2\"><b>VIEW CLAIM DOCUMENT</b></font></a>&nbsp;&nbsp;&nbsp;");
						//}
						//Ended.
						if(claimElement.valueOf("@shortfall").equalsIgnoreCase("Y"))
						{
							out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
							out.print("<a href=\"#\" onClick=\"javascript:online_Claims_shortfall('"+claimElement.valueOf("@seq_id")+"');\"><font size=\"2\"><b>Shortfall</b></font></a>");
							out.print("</td>");
						}//end of  if(claimElement.valueOf("@shortfall").equalsIgnoreCase("Y"))
						else
						{
							out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
							out.print("</td>");
						}
						if(claimElement.valueOf("@rejectamtexist").equalsIgnoreCase("Y"))
						{
							if(!LoginType.equalsIgnoreCase("B")){//kocbroker
							out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
							out.print("<a href=\"#\" onClick=\"javascript:online_DisallowedBill('"+claimElement.valueOf("@seq_id")+"','"+claimElement.valueOf("@enrollmentid")+"','"+claimElement.valueOf("@claimnumber")+"','"+claimElement.valueOf("@settlementnumber")+"');\"><font size=\"2\"><b>Disallowed Bill</b></font></a>");
							out.print("</td>");
							}
						}//end of  if(claimElement.valueOf("@shortfall").equalsIgnoreCase("Y"))
						else
						{
							out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
							out.print("</td>");
						}
						if(claimElement.valueOf("@statustypeid").equalsIgnoreCase("REJ"))
						{
							out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
							out.print("<a href=\"#\" onClick=\"javascript:online_Rejection_clauses('"+claimElement.valueOf("@seq_id")+"');\"><font size=\"2\"><b>Rejection Clauses</b></font></a>");
							out.print("</td>");
						}//end of  if(claimElement.valueOf("@shortfall").equalsIgnoreCase("Y"))
						else
						{
							out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
							out.print("</td>");
						}	
						out.print("</tr>");
						out.print("</table>");
						
						
						out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
						out.print("Claim No.:");
						out.print("</td>");
						out.print("<td width=\"25%\" class=\"textLabelBold\">");
						out.print(claimElement.valueOf("@claimnumber"));
						out.print("</td>");
						out.print("<td class=\"formLabel\" width=\"22%\">");
						out.print("Received Date / Time:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(claimElement.valueOf("@rcvddate"));
						out.print("</td>");
						out.print("</tr>");
						
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">Admission Date / Time:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(claimElement.valueOf("@dateofadmission"));
						out.print("</td>");
						out.print("<td class=\"formLabel\">Discharge Date / Time:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(claimElement.valueOf("@dateofdischarge"));
						out.print("</td>");
						out.print("</tr>");
						
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">Provider Name:");
						out.print("</td>");
						out.print("<td class=\"formLabelBold\">");
						out.print(claimElement.valueOf("@hospitalname"));
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\">Claim Type:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(claimElement.valueOf("@claimtype"));
						out.print("</td>");
						out.print("</tr>");
						
						out.print("<tr>");
						out.print("<td class=\"formLabel\">Claim Settlement No.:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(claimElement.valueOf("@settlementnumber"));
						out.print("</td>");
						out.print("<td height=\"20\" class=\"formLabel\">Settlement Date / Time:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(claimElement.valueOf("@settlementdate"));
						out.print("</td>");
						out.print("</tr>");
						
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">Status:");
						out.print("</td>");
						out.print("<td colspan=\"3\" class=\"textLabel\">");
						out.print(claimElement.valueOf("@status"));
						out.print("</td>");
						out.print("</tr>");
						
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">Requested Amt. (Rs.): ");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(claimElement.valueOf("@requestedamount"));
						out.print("</td>");
						out.print("<td height=\"20\" class=\"formLabel\">Approved Amt. (Rs.):");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(claimElement.valueOf("@approvedamount"));
						out.print("</td>");
						out.print("</tr>");
						
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">Remarks:");
						out.print("</td>");
						out.print("<td colspan=\"3\" class=\"textLabel\">");
						out.print(claimElement.valueOf("@remarks"));
						out.print("</td>");
						out.print("</tr>");
						out.print("</table>");
						
						if(!((List)historyDoc.selectNodes("//chequeinformation/record")).isEmpty())
			            {
			         	   //chequeList = (List)historyDoc.selectNodes("//chequeinformation/record");
							 chequeList = (List)historyDoc.selectNodes("//claimdetails[@id="+(i+1)+"]/chequeinformation/record");
			         	  
			         	   if(chequeList!=null&& chequeList.size() > 0 ){
			         		  out.print("<fieldset><legend>Cheque Details</legend>");
			         		   out.print("<table align=\"center\" class=\"gridWithCheckBox zeroMargin\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
			                    out.print("<tr>");
			                     out.print("<td width=\"30%\" valign=\"top\" class=\"gridHeader\">Payee Name</td>");
			            		 out.print("<td width=\"12%\" valign=\"top\" class=\"gridHeader\">Cheque/NEFT no.</td>"); //kocbroker
			            		  out.print("<td width=\"12%\" valign=\"top\" class=\"gridHeader\">Cheque/NEFT Amt.(Rs)</td>");
			            		  out.print("<td width=\"12%\" valign=\"top\" class=\"gridHeader\">TDS Amt.(Rs)</td>");
			                      out.print("<td width=\"10%\" valign=\"top\" class=\"gridHeader\">Cheque/NEFT Date</td>");
			                      out.print("<td width=\"11%\" valign=\"top\" class=\"gridHeader\">Cheque/NEFT Status</td>");
			                      out.print("<td width=\"11%\" valign=\"top\" class=\"gridHeader\">Consignment No.</td>");//added for ibm4changes
			                       out.print("<td width=\"11%\" valign=\"top\" class=\"gridHeader\">Service Provider Name/Helpdesk Loc.</td>");//added for ibm4changes
			                      out.print("<td width=\"11%\" valign=\"top\" class=\"gridHeader\">Dispatch Date</td>");//added for ibm4changes
			                      out.print("</tr>");
	
			                    for(int chqcount=0;chqcount<chequeList.size();chqcount++)
			                    {
			                 	   chequeElement = (Element)chequeList.get(chqcount);
			                        strClass=chqcount%2==0 ? "gridOddRow" : "gridEvenRow" ;
			                        out.print("<tr class=\""+strClass+"\">");
			                        out.print("<td>");
			                    			out.print(chequeElement.valueOf("@payeename")+"&nbsp;");
			                    	   out.print("</td>");
			                    	   out.print("<td>");
			                    	   		out.print(chequeElement.valueOf("@checknumber")+"&nbsp;");
			                    	   out.print("</td>");
			                    	   out.print("<td>");
			                    	   		out.print(chequeElement.valueOf("@checkamount")+"&nbsp;");
			                    	   out.print("</td>");
			                    	   out.print("<td>");
		                    	   		    out.print(chequeElement.valueOf("@tdsamount")+"&nbsp;");
		                    	       out.print("</td>");
			                    	   out.print("<td>");
			            	   				out.print(chequeElement.valueOf("@checkdate")+"&nbsp;");
			            	   		   out.print("</td>");
			            	   		   out.print("<td>");
			            	   		   		out.print(chequeElement.valueOf("@chequestatus")+"&nbsp;");
			            	   		   out.print("</td>");
			            	   	  	//added for ibm4changes
			            	   		 out.print("<td>");
			 	   		   		     out.print(chequeElement.valueOf("@consignmentno")+"&nbsp;");
			 	   		             out.print("</td>"); 
			 	   		             out.print("<td>");
				   		   		     out.print(chequeElement.valueOf("@providername")+"&nbsp;");
				   		             out.print("</td>"); 
			                	   	      out.print("<td>");
			      	   		   		  out.print(chequeElement.valueOf("@despatchdate")+"&nbsp;");
			      	   		          out.print("</td>"); 
			                	   	//End added for ibm4changes
			                        out.print("</tr>");
			                    }//end of for(int chqcount=0;chqcount<chequeList.size();chqcount++)
			                    out.print("</table>");
			                    out.print("</fieldset>");
			                }//end of if(chequeList!=null&& chequeList.size() > 0 )
			         	   
						}//end of if(!((List)historyDoc.selectNodes("//chequeinformation/record")).isEmpty())
						out.print("</fieldset>");
					}//end of if(doc != null)
				}//end of if(!((List)historyDoc.selectNodes("//claimdetails")).isEmpty())	
				else
				{
					out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
					out.print("<tr>");
					out.print("<td width=\"22%\" height=\"25\" class=\"formLabelBold\">");
					out.print("<font size=\"2\" color=\"#0C48A2\">No Claim details exist for this enrollment id.</font>");
					out.print("</td>");
					out.print("</tr>");
					out.print("</table>");
				}//end of else
			}//end of if(historyDoc != null)
			
		}//end of try block
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch block
		return SKIP_BODY;
	}//end of doStartTag()
	public int doEndTag() throws JspException {
		return EVAL_PAGE;//to process the rest of the page
	}//end doEndTag()
}//end of class PreAuthHistory
