/**
 
 * @ (#) OnlineHospClaimDashBoard.java 
 * Project      : TTK HealthCare Services
 * File         : onlineHospClaimDasBoard.java
 *  Author       :Satya Moganti
 * Company      : Rcs Technologies
 * Date Created : march 24 ,2014
 *
 * @author       :Satya moganti
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.hospital;

//import java.net.URL;
import java.util.List;

//import javax.activation.DataHandler;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

//import org.apache.log4j.Logger;
import org.apache.log4j.Logger;
import org.dom4j.Document;
//import org.dom4j.Element;
import org.dom4j.Node;

import com.ttk.business.onlineforms.OnlineAccessManager;
//import com.ttk.common.TTKCommon;
//import com.ttk.common.TTKPropertiesReader;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.usermanagement.UserSecurityProfile;

/**
 *  This class builds the Online home Details
 */
public class OnlineHospClaimDashBoard extends TagSupport{
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
    private static Logger log = Logger.getLogger( OnlineHospClaimDashBoard.class );
	
	public int doStartTag() throws JspException{
		try
		{			
			
			Document docClaims=null;
			Node claimsNode=null;
		
			
			
			
			OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			JspWriter out = pageContext.getOut();//Writer object to write the file
			
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
			
			
			docClaims= (((Document)pageContext.getSession().getAttribute("onlinehosphomeclaim"))!=null) 
					   ? (Document)pageContext.getSession().getAttribute("onlinehosphomeclaim")
					   : (Document)onlineAccessManagerObject.getHospitalDashBoard(userSecurityProfile.getHospSeqId(),null,null,"Claims");
			
					   
			
	        pageContext.getSession().setAttribute("onlinehosphomeclaim",docClaims);
		
			
			out.print("<table width=\"75%\" align=\"center\"  border=\"0\" cellspacing=\"0\" cellpadding=\"5\">");

	        	 
	 if(!((List)docClaims.selectNodes("//hospital//claims")).isEmpty())		{
	    	
	                List claimList=	docClaims.selectNodes("//hospital//claims");
	                 claimsNode=(Node)claimList.get(0);
		                
	             out.print("<tr>");
	             		out.print("<td width=\"100%\" align=\"left\" >");
	             			out.print("<B>");
	             			   out.print("<legend>");
	             			  out.print("&nbsp;&nbsp;");
	             				out.print("Claim Details From ");out.print(claimsNode.valueOf("@startdate"));out.print(" to "); out.print(claimsNode.valueOf("@enddate"));
	             				out.print("</legend>");
	             			out.print("</B>");
	             		out.print("</td>");
	 	         out.print("</tr>");	                 
	                 
	         out.print("<tr>");   
	         out.print("<td width=\"100%\" align=\"left\" >"); 
	     		          
	                
	    	 out.print("<table align=\"center\" class=\"gridWithCheckBox zeroMargin\"  border=\"1\" cellspacing=\"0\" cellpadding=\"0\">");
	                out.print("<tr>");                
	                    out.print("<td width=\"50%\" valign=\"top\" class=\"gridHeader\" align=\"center\">Request Status</td>");
	                    out.print("<td width=\"50%\" valign=\"top\" class=\"gridHeader\" align=\"center\">Count</td>");
	                out.print("</tr>");
	                
	                
	                
	                /*if(claimsNode.valueOf("@received").equalsIgnoreCase("0") || claimsNode.valueOf("@received")==null )    {
        			pageContext.getOut().print("<tr><td class=\"generalcontent\" colspan=\"2\">&nbsp;&nbsp;No Data Found</td></tr>");
	                }//end of  if(claimsNode.valueOf("@received").equalsIgnoreCase("0") || claimsNode.valueOf("@received")==null )
	                else{*/
	              //above if statement is commented B'coz, colums has to show even if the data is not exist as per the requirement from Shankar
	                out.print("<tr>");
	            		out.print("<td width=\"50%\" height=\"25\" class=\"formLabel_new\"  align=\"left\" id=\"ttl\" >");/*onmouseover=\"javascript:showMe(this)\" onmouseout=\"javascript:hideMe(this)\"*/								
	            		/*out.print("<a href=\"#\" onclick=\"javascript:onDashBoardClaims('')\">");*/
								out.print("Total Received");
								/*out.print("</a>");*/	 					
						out.print("</td>");	
					out.print("<td width=\"50%\" class=\"textLabelBold\" align=\"left\">");
						out.print((claimsNode.valueOf("@received").equalsIgnoreCase(""))?0:claimsNode.valueOf("@received"));
					out.print("</td>");
					out.print("</tr>");
	                
					
					  out.print("<tr>");
	      					out.print("<td width=\"50%\" height=\"25\" class=\"formLabel_new\"  align=\"left\" id=\"inp\" >");/*onmouseover=\"javascript:showMe(this)\" onmouseout=\"javascript:hideMe(this)\"*/	      						
	      					/*out.print("<a href=\"#\" onclick=\"javascript:onDashBoardClaims('INP')\">");*/
	      							out.print("In Progress");
	      						/*out.print("</a>");*/	
	      					out.print("</td>");
	      					out.print("<td width=\"50%\" class=\"textLabelBold\" align=\"left\">");
	      						out.print((claimsNode.valueOf("@progress").equalsIgnoreCase(""))?0:claimsNode.valueOf("@progress"));
	      					out.print("</td>");
	      			out.print("</tr>");
	              
	      			out.print("<tr>");
	      				out.print("<td width=\"50%\" height=\"25\" class=\"formLabel_new\"  align=\"left\" id=\"app\" >");/*onmouseover=\"javascript:showMe(this)\" onmouseout=\"javascript:hideMe(this)\"*/
	      				/*out.print("<a href=\"#\" onclick=\"javascript:onDashBoardClaims('APR')\">");*/
	      						out.print("Approved");
	      					/*out.print("</a>");*/ 	
	      				out.print("</td>");
	      				out.print("<td width=\"50%\" class=\"textLabelBold\" align=\"left\">");
	      					out.print((claimsNode.valueOf("@approved").equalsIgnoreCase(""))?0:claimsNode.valueOf("@approved"));
	      				out.print("</td>");
	      			out.print("</tr>");
	        		
	        		out.print("<tr>");
	    				out.print("<td width=\"50%\" height=\"25\" class=\"formLabel_new\"  align=\"left\" id=\"req\" >");/*onmouseover=\"javascript:showMe(this)\" onmouseout=\"javascript:hideMe(this)\"*/
	    				/*out.print("<a href=\"#\" onclick=\"javascript:onDashBoardClaims('REQ')\">");*/
	      				out.print("Required More Information");
	      				/*out.print("</a>");*/ 
	    				out.print("</td>");
	    				out.print("<td width=\"50%\" class=\"textLabelBold\" align=\"left\">");
	    					out.print((claimsNode.valueOf("@shortfalls").equalsIgnoreCase(""))?0:claimsNode.valueOf("@shortfalls"));
	    				out.print("</td>");
	    			out.print("</tr>");
	    		
	    			out.print("<tr>");
						out.print("<td width=\"50%\" height=\"25\" class=\"formLabel_new\"  align=\"left\" id=\"cls\" >");/*onmouseover=\"javascript:showMe(this)\" onmouseout=\"javascript:hideMe(this)\"
							out.print("<a href=\"#\" onclick=\"javascript:onDashBoardClaims('PCO')\">");*/
								out.print("Closed");
							/*out.print("</a>");*/ 
						out.print("</td>");
						out.print("<td width=\"50%\" class=\"textLabelBold\" align=\"left\">");
							out.print((claimsNode.valueOf("@closed").equalsIgnoreCase(""))?0:claimsNode.valueOf("@closed"));
						out.print("</td>");
					out.print("</tr>");
			
					out.print("<tr>");
						out.print("<td width=\"50%\" height=\"25\" class=\"formLabel_new\"  align=\"left\" id=\"rej\" >");/*onmouseover=\"javascript:showMe(this)\" onmouseout=\"javascript:hideMe(this)\"
*/							/*out.print("<a href=\"#\" onclick=\"javascript:onDashBoardClaims('REJ')\">");*/
								out.print("Rejected");
							/*out.print("</a>"); 	*/
						out.print("</td>");
						out.print("<td width=\"50%\" class=\"textLabelBold\" align=\"left\">");
							out.print((claimsNode.valueOf("@rejected").equalsIgnoreCase(""))?0:claimsNode.valueOf("@rejected"));
						out.print("</td>");
					out.print("</tr>");
					
					out.print("<tr>");
					out.print("<td width=\"50%\" height=\"25\" class=\"formLabel_new\"  align=\"left\" id=\"paid\">"); /*onmouseover=\"javascript:showMe(this)\" onmouseout=\"javascript:hideMe(this)\"
*/						/*out.print("<a href=\"#\" onclick=\"javascript:onDashBoardClaims('PAID')\">");*/
							out.print("Paid");
						/*out.print("</a>"); */	
					out.print("</td>");
					out.print("<td width=\"50%\" class=\"textLabelBold\" align=\"left\">");
						out.print((claimsNode.valueOf("@paid").equalsIgnoreCase(""))?0:claimsNode.valueOf("@paid"));
					out.print("</td>");
				out.print("</tr>");
					
					
					//kocnewhosp
	 		       /* out.print("<tr>");	        
	 		        out.print("<td id=\"ttl_1\" width=\"50%\" height=\"25\" class=\"formLabel_new\"  align=\"left\" colspan=\"2\">");
	 		        out.print("<B><font color=blue>Note :</B> Navigates to the list of Claims received.</font>");
	 		        out.print("</td>");
	 		        
	 		        out.print("<td id=\"ttl_2\" width=\"50%\" height=\"25\" class=\"formLabel_new\"  align=\"left\" colspan=\"2\">");
	 		        out.print("<B><font color=blue>Note :</B> Navigates to the list of Claims which are in progress.</font>");
	 		        out.print("</td>");
	 		        
	 		        out.print("<td id=\"ttl_3\" width=\"50%\" height=\"25\" class=\"formLabel_new\"  align=\"left\" colspan=\"2\">");
	 		        out.print("<B><font color=blue>Note :</B> Navigates to the list of Claims which are Approved.</font>");
	 		        out.print("</td>");
	 		        
	 		        out.print("<td id=\"ttl_4\" width=\"50%\" height=\"25\" class=\"formLabel_new\"  align=\"left\" colspan=\"2\">");
	 		        out.print("<B><font color=blue>Note :</B> Navigates to the list of Claims which are required more information.</font>");
	 		        out.print("</td>");
	 		        
	 		        out.print("<td id=\"ttl_5\" width=\"50%\" height=\"25\" class=\"formLabel_new\"  align=\"left\" colspan=\"2\">");
	 		        out.print("<B><font color=blue>Note :</B> Navigates to the list of Claims which are closed.</font>");
	 		        out.print("</td>");
	 		        
	 		        out.print("<td id=\"ttl_6\" width=\"50%\" height=\"25\" class=\"formLabel_new\"  align=\"left\" colspan=\"2\">");
	 		        out.print("<B><font color=blue>Note :</B> Navigates to the list of Claims which are rejected.</font>");
	 		        out.print("</td>");
	 		        
	 		        out.print("<td id=\"ttl_7\" width=\"50%\" height=\"25\" class=\"formLabel_new\"  align=\"left\" colspan=\"2\">");
	 		        out.print("<B><font color=blue>Note :</B> Navigates to the list of Claims which are paid.</font>");
	 		        out.print("</td>");
	 		        
	 		        out.print("<td id=\"ttl_8\" width=\"50%\" height=\"25\" class=\"formLabel_new\"  align=\"left\" colspan=\"2\">");
	 		        out.print("<B><font color=blue>Note :</B></font> ");
	 		        out.print("</td>");
	 		        
	 		        
	 		        out.print("</tr>");*/
	               // }//end of else part if(claimsNode.valueOf("@received").equalsIgnoreCase("0") || claimsNode.valueOf("@received")==null )
		      out.print("</table>");
		  out.print("</td>");
		  out.print("</tr>");              
	    }//end of if(!((List)dashBoardDoc.selectNodes("//hospital//claims")).isEmpty())
	        	
        	 
	        		             
	                out.print("</table>");
	                
	                /*        if(preauthsNode!=null)    {
	                    out.print("<input type=\"hidden\" name=\"sPatStartDate\" id=\"sPatStartDate\" value=\""+preauthsNode.valueOf("@startdate")+"\"/>");
                    	out.print("<input type=\"hidden\" name=\"sPatEndDate\" id=\"sPatEndDate\" value=\""+preauthsNode.valueOf("@enddate")+"\"/>");
                     }//end of  if(preauthsNode!=null) */
	               if(claimsNode!=null){
	                	out.print("<input type=\"hidden\" name=\"sClmStartDate\" id=\"sClmStartDate\" value=\""+claimsNode.valueOf("@startdate")+"\"/>");
                    	out.print("<input type=\"hidden\" name=\"sClmEndDate\" id=\"sClmEndDate\" value=\""+claimsNode.valueOf("@enddate")+"\"/>");
                
	                 }//end of if(claimsNode!=null)
	                
	             
			
		}//end of try block
		catch(NumberFormatException exp)
		{	
			exp.printStackTrace();
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch block
		return SKIP_BODY;
	}//end of doStartTag()
	public int doEndTag() throws JspException {
		return EVAL_PAGE;//to process the rest of the page
	}//end doEndTag()
	
	 /**
	    * Returns the onlineAccessManager session object for invoking methods on it.
	    * @return onlineAccessManager session object which can be used for method invocation
	    * @exception throws TTKException
	    */
	   private OnlineAccessManager getOnlineAccessManagerObject() throws TTKException
	   {
	   	OnlineAccessManager onlineAccessManager = null;
	   	try
	   	{
	   		if(onlineAccessManager == null)
	   		{
	   			InitialContext ctx = new InitialContext();
	   			//onlineAccessManager = (OnlineAccessManager) ctx.lookup(OnlineAccessManager.class.getName());
                onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");

	   			log.debug("Inside getOnlineAccessManagerObject: onlineAccessManager: " + onlineAccessManager);
	   		}//end if
	   	}//end of try
	   	catch(Exception exp)
	   	{
	   		throw new TTKException(exp, "");
	   	}//end of catch
	   	return onlineAccessManager;
	   }//end of getOnlineAccessManagerObject()
}//end of class OnlineHospHomeDetails
