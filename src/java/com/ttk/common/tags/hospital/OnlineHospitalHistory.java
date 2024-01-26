/**
 
 * @ (#) OnlineHospHistory.java
 * Project : TTK HealthCare Services
 * File : OnlineHospHistory.java
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

//import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import java.math.BigDecimal;
import java.util.List;
//import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;

import com.ttk.common.TTKCommon;

/**
 *  This class builds the Hospital Claims or Pre-Auth history
 */
public class OnlineHospitalHistory extends TagSupport{
	
	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
//	private static Logger log = Logger.getLogger( OnlineHospitalHistory.class );
	
	public int doStartTag() throws JspException{
		try
		{
			
			Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
			/*log.info("hai trying to print xml");
			log.info(historyDoc.asXML());*/
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			JspWriter out = pageContext.getOut();//Writer object to write the file
			
			  String strActiveLink=TTKCommon.getActiveLink(request);//added as per Hospital Login
	           String strActiveSubLink=TTKCommon.getActiveSubLink(request);//added as per Hospital Login
	           //Code has to be written for displaying Generate Button 
	        //   historyDoc.selectSingleNode("/preauthorizationdetails[0]");

	          //preauthorization details
	           Element preAuthElement=null;Element preAuthShrtFallElement=null;List  preauthorizationdetailslist=null;List  preautShrtFalllist=null;
	           
	           //claimant history
	           Element claimentElement=null;List  claimentlist=null;
	           
	           //ClaimantDetailsHistory
	           List  claimantList=null;Element claimantElement=null;List  claimShrtFallList=null;
	           Element claimShrtFallElement=null;List  claimList=null;Element claimElement=null;
	           //Policy History
	           List  policydetailsList=null; Element policydetailsElement=null;
	           //IC PCS History
	           Element icdpcsElement=null,provisionaldiagnosisElement=null;
	           List  icdpcscodingdetailsList=null,provisionaldiagnosisList=null;
	           String strProcedure="";
	           String strClass="";
	           String strActiveTab=TTKCommon.getActiveLink(request);
	           
	           //Hospital history
	           String strGrade="",strAlt="";
               Element hospitalElement=null;List  hospitalList=null;

	           //Settlement History 
               List  settlementList=null;Element settlementElement=null;
               List  bufferList=null;Element bufferElement=null; 
               //Authorization history
               List  authorizationList=null;
               Element authorizationElement=null;
               List  bufferamountList=null;
               Element bufferamountElement=null;
            //   List  approvalstatusList=null;
            //   Element approvalstatusElement=null;
               //Narration History
               List  narrationdetailsList=null;
               Element narrationdetailsElement=null;//String strClass="";
               
               //ChequedetailsHistory
               List  chequeList=null;
               Element chequeElement=null;
               String strPreAuthShortFallYN="N";
               String strClaimShortFallYN="N";
               
	           if(historyDoc != null)
	           {
	        	   
	        	   //PreAuthorization Details
	        	   if(!((List)historyDoc.selectNodes("//shortfall")).isEmpty())
	               {
	        		   preautShrtFalllist = (List)historyDoc.selectNodes("//shortfall");
	               }
	        	   if(!((List)historyDoc.selectNodes("//preauthorizationdetails")).isEmpty())
	               {
	                   preauthorizationdetailslist = (List)historyDoc.selectNodes("//preauthorizationdetails");
	                   if(preauthorizationdetailslist!=null&& preauthorizationdetailslist.size() > 0 )
	                   {
	                       preAuthElement = (Element)preauthorizationdetailslist.get(0);
	                       if(preautShrtFalllist !=null && preautShrtFalllist.size() >0)
	                       {
	                    	   preAuthShrtFallElement = (Element)preautShrtFalllist.get(0);
	                    	   //out.print("print "+preAuthShrtFallElement.valueOf("@shortfall")+"  "+preAuthShrtFallElement.valueOf("@seq_id"));
		                       if("Y".equals(preAuthShrtFallElement.valueOf("@shortfall")))
		                       {
		                    	   strPreAuthShortFallYN="Y";
		                       }
		                       
		                    	   if(strActiveLink.equalsIgnoreCase("Hospital Information")){
                                	   if(strActiveSubLink.equalsIgnoreCase("Cashless Status")){
                                		  if(preAuthElement.valueOf("@completed").equalsIgnoreCase("Y")) {
                                			  if(!preAuthElement.valueOf("@statusid").equalsIgnoreCase("PCN")){
                                			  out.print("<table align=\"center\" class=\"buttonsContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                                                 out.print("<tr>");
                                                 	out.print("<td class=\"textLabelBold\">");
                                                 	out.print("</td>");
                                                 	out.print("<td class=\"textLabelBold\">");
                                                 	out.print("</td>");
                                                 	out.print("<td class=\"textLabelBold\">");
                                                 	out.print("</td>");
                                                 	out.print("<td align=\"right\">");
                                                 	out.print("<button type=\"button\" name=\"Button\" accesskey=\"g\" id=\"authletter\" class=\"buttons\" style=\"display:\" onMouseout=\"this.className='buttons'\" onMouseover=\"this.className='buttons buttonsHover'\" onClick=\"javascript:onReport();\"><u>G</u>enerate Letter</button>");//&nbsp;
                                                 	out.print("</td>");
                                                 out.print("</tr>");
                                             out.print("</table>");
                                			  			}
                                                  }//end of if(preAuthElement.valueOf("@completed").equalsIgnoreCase("Y"))
                                	  }//end of if(strActiveSubLink.equalsIgnoreCase("Pre-Auth"))
                                  }//end of     if(strActiveLink.equalsIgnoreCase("Hospital Information")){  
		                       }
	                   		
	                       out.print("<fieldset>");
	                               out.print("<legend>Cashless Details</legend>");
	                               out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	                                 out.print("<tr>");
	                                   out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
	                                       out.print("Cashless No.:");
	                                   out.print("</td>");
	                                   out.print("<td width=\"25%\" class=\"textLabelBold\">");
	                                           out.print(preAuthElement.valueOf("@preauthnumber"));
	                                   out.print("</td>");
	                                   out.print("<td class=\"formLabel\" width=\"22%\">");
	                                           out.print("Cashless Type:");
	                                   out.print("</td>");
	                                   out.print("<td class=\"textLabel\" width=\"31%\">");
	                                           out.print(preAuthElement.valueOf("@preauthtype"));
	                                   out.print("</td>");
	                                 out.print("</tr>");
	                                 out.print("<tr>");
	                                   out.print("<td height=\"25\" class=\"formLabel\">Received Date / Time:");
	                                   out.print("</td>");
	                                   out.print("<td class=\"textLabel\">");
	                                           out.print(preAuthElement.valueOf("@recieveddate"));
	                                   out.print("</td>");
	                                   out.print("<td class=\"formLabel\">Admission Date / Time:");
	                                       out.print("</td>");
	                                   out.print("<td class=\"textLabel\">");
	                                       out.print(preAuthElement.valueOf("@admissiondate"));
	                                           out.print("</td>");
	                                 out.print("</tr>");
	                                 out.print("<tr>");
	                                   out.print("<td height=\"25\" class=\"formLabel\">Prev. Approved Amt. (Rs):");
	                                       out.print("</td>");
	                                   out.print("<td class=\"textLabel\">");
	                                       out.print(preAuthElement.valueOf("@prevapprovedamount"));
	                                   out.print("</td>");
	                                   out.print("<td class=\"formLabel\">Requested Amt. (Rs):");
	                                   out.print("</td>");
	                                   out.print("<td class=\"textLabel\">");
	                                       out.print(preAuthElement.valueOf("@requestedamount"));
	                                   out.print("</td>");
	                                 out.print("</tr>");
	                                 out.print("<tr>");
	                                 out.print("<td height=\"25\" class=\"formLabel\">Treating Doctor's Name:");
	                                         out.print("</td>");
	                                 out.print("<td class=\"textLabel\">");
	                                     out.print(preAuthElement.valueOf("@treatingdoctorname"));
	                                 out.print("</td>");
	                                 out.print("<td height=\"20\" class=\"formLabel\">Vidal Health Branch:");
	                                 out.print("</td>");
	                                 out.print("<td class=\"textLabel\">");
	                                     out.print(preAuthElement.valueOf("@ttkbranch"));
	                                         out.print("</td>");
	                                 out.print("</tr>");

	                                 out.print("<tr>");
	                                  out.print("<td height=\"25\" class=\"formLabel\">Probable Date of Discharge:");
	                                      out.print("</td>");
	                                  out.print("<td class=\"textLabel\">");
	                                  out.print(preAuthElement.valueOf("@dateofdischarge"));
	                                          out.print("</td>");
	                                  out.print("<td height=\"20\" class=\"formLabel\">Enhanced:");
	                                  out.print("</td>");
	                                  out.print("<td class=\"textLabel\">");
	                                      out.print(preAuthElement.valueOf("@enhanced"));
	                                  out.print("</td>");
	                                  out.print("</tr>");

	                               /*   if(!TTKCommon.getActiveLink(request).equals("Online Forms"))
	                                  {
		                                  out.print("<tr>");
		                                  out.print("<td height=\"25\" class=\"formLabel\">Workflow:");
		                                      out.print("</td>");
		                                  out.print("<td class=\"textLabel\">");
		                                  out.print(preAuthElement.valueOf("@workflow"));
		                                          out.print("</td>");
		                                  out.print("<td height=\"20\" class=\"formLabel\">");
		                                  out.print("</td>");
		                                  out.print("<td class=\"textLabel\">");
		                                      out.print("");
		                                  out.print("</td>");
		                                  out.print("</tr>");
	                                  }*/

	                                 /* out.print("<tr>");
		                                  out.print("<td height=\"25\" class=\"formLabel\">Remarks:");
		                                      out.print("</td>");
		                                  out.print("<td colspan=\"3\" class=\"textLabel\">");
		                                  	  out.print(preAuthElement.valueOf("@remarks"));
		                                  out.print("</td>");
	                                  out.print("</tr>");*/
	                                  out.print("</table>");
	                                  out.print("</fieldset>");
	                               

	                                  if(strActiveLink.equalsIgnoreCase("Hospital Information")){
	                                	   if(strActiveSubLink.equalsIgnoreCase("Cashless Status")){
	                                		  if(preAuthElement.valueOf("@completed").equalsIgnoreCase("Y")) {
	                                			  out.print("<input type=\"hidden\" name=\"authLtrTypeID\"  id=\"authLtrTypeID\" value=\""+preAuthElement.valueOf("@authtypeid")+"\"/>");
	                                              out.print("<input type=\"hidden\" name=\"preAuthSeqID\"  id=\"preAuthSeqID\" value=\""+preAuthElement.valueOf("@seqid")+"\"/>");
	                                              out.print("<input type=\"hidden\" name=\"statusTypeID\"  id=\"statusTypeID\" value=\""+preAuthElement.valueOf("@statusid")+"\"/>");
	                                              out.print("<input type=\"hidden\" name=\"completedYN\"   id=\"completedYN\" value=\""+preAuthElement.valueOf("@completed")+"\"/>");
	                                              out.print("<input type=\"hidden\" name=\"cignastatusYN\" id=\"cignastatusYN\" value=\""+preAuthElement.valueOf("@cignastatus")+"\"/>");
	                                              out.print("<input type=\"hidden\" name=\"authorizationNo\" id=\"authorizationNo\"  value=\""+preAuthElement.valueOf("@authorizationnumber")+"\"/>");
	                                              out.print("<input type=\"hidden\" name=\"preAuthNo\" id=\"preAuthNo\" value=\""+preAuthElement.valueOf("@preauthnumber")+"\"/>");
	                                        
	                                		  }//end of if(preAuthElement.valueOf("@completed").equalsIgnoreCase("Y"))
		                                	  }//end of if(strActiveSubLink.equalsIgnoreCase("Pre-Auth"))
		                                  }//end of     if(strActiveLink.equalsIgnoreCase("Hospital Information")){  
	                                  
	                               /**
	                                * Claimant Details
	                                */
	                               
                            	   if(strActiveSubLink.equalsIgnoreCase("Cashless Status")){
                            		   if(!((List)historyDoc.selectNodes("//claimantdetails")).isEmpty())
                                       {
                                           claimentlist = (List)historyDoc.selectNodes("//claimantdetails");
                                           if(claimentlist!=null&& claimentlist.size() > 0 )
                                           {
                                               claimentElement = (Element)claimentlist.get(0);
                                               out.print("<fieldset><legend>Member Details</legend>");
                                                   out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                                                        out.print("<tr>");
                                                           out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Enrollment Id:</td>");
                                                           out.print("<td width=\"25%\" class=\"textLabel\">");
                                                           out.print(claimentElement.valueOf("@enrollmentid"));
                                                           out.print("</td>");
                                                           out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
                                                               out.print("Member Name:");
                                                           out.print("</td>");
                                                           out.print("<td class=\"textLabel\" width=\"31%\">");
                                                               out.print(claimentElement.valueOf("@claimantname"));
                                                           out.print("</td>");
                                                 /*      out.print("</tr>");
                                                       out.print("<tr>");
                                                       		out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Diabetes Cover:</td>");
                                                       		out.print("<td width=\"25%\" class=\"textLabel\">");
                                                       		out.print(claimentElement.valueOf("@diabetes_cover_yn"));
                                                            out.print("</td>");
                                                            out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
                                                            out.print("Hypertension Cover:");
                                                        out.print("</td>");
                                                        out.print("<td class=\"textLabel\" width=\"31%\">");
                                                        out.print(claimentElement.valueOf("@hypertension_cover_yn"));
                                                        out.print("</td>");
                                                    out.print("</tr>");*/
                                                     out.print("</table>");
                                               out.print("</fieldset>");
                                           }//end of if(claimentlist!=null&& claimentlist.size() > 0 )
                                       }//end of if(!((List)historyDoc.selectNodes("//claimantdetails")).isEmpty())
                            		   
                            	   }     
                      }//end of if(preauthorizationdetailslist!=null&& preauthorizationdetailslist.size() > 0 )
	               }//end of if(!((List)doc.selectNodes("//preauthorizationdetails")).isEmpty())
	        	   
	        	   
	        	   if(strActiveSubLink.equalsIgnoreCase("Claims Status")){
            		   
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
                           
        	                   if(claimShrtFallList !=null && claimShrtFallList.size() >0)
        	                   {
        	                	   claimShrtFallElement = (Element)claimShrtFallList.get(0);
        		                   if("Y".equals(claimShrtFallElement.valueOf("@shortfall")))
        	                       {	
        		                	   
        		                	   strClaimShortFallYN="Y";
        		                	  
        	                       }//end of if("Y".equals(claimShrtFallElement.valueOf("@shortfall")))
        		                  /* else
        		                   {
        		                	   out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
        			                   out.print("</td>");
        		                   }//end of else
*/        		                   //if(claimElement.valueOf("@rejectamtexist").equalsIgnoreCase("Y"))
        	                   }//end of if(claimShrtFallList !=null && claimShrtFallList.size() >0)
        	                   /*   if("Y".equals(claimantElement.valueOf("@rejectamtexist")))
        	                   {	
        	                	   out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
        	                	   out.print("<a href=\"#\" onClick=\"javascript:online_DisallowedBill('"+claimElement.valueOf("@claimseqid")+"','"+claimantElement.valueOf("@enrollmentid")+"','"+claimElement.valueOf("@claimnumber")+"','"+claimElement.valueOf("@settlementnumber")+"');\"><font size=\"2\"><b>Disallowed Bill</b></font></a>");
        		                   out.print("</td>");
        	                   }//end of if("Y".equals(claimShrtFallElement.valueOf("@shortfall")))
        	                   else
        	                   {
        	                	   out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
        		                   out.print("</td>");
        	                   }//end of else	*/
        	                  
                           if(strActiveLink.equalsIgnoreCase("Hospital Information")){   
                         	  if(strActiveSubLink.equalsIgnoreCase("Claims Status"))  	  {
                         		  if(claimElement.valueOf("@completed").equalsIgnoreCase("Y"))  {
                         			  	out.print("<table align=\"center\" class=\"buttonsContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                         			  	out.print("<tr>");
                         			  	out.print("<td class=\"textLabelBold\">");
                          			  out.print("</td>");
                          			  out.print("<td class=\"textLabelBold\">");
                          			  out.print("</td>");
                          			  out.print("<td class=\"textLabelBold\">");
                          			  out.print("</td>");
                         			  	out.print("<td align=\"right\">");
                         			  	out.print("<button type=\"button\" name=\"Button\" accesskey=\"g\" id=\"authletter\" class=\"buttons\" style=\"display:\" onMouseout=\"this.className='buttons'\" onMouseover=\"this.className='buttons buttonsHover'\" onClick=\"javascript:onReport();\"><u>G</u>enerate Letter</button>");//&nbsp;
                         			  	out.print("<td>");
                                         out.print("</tr>");
                                         out.print("</table>");
                                       }//end of if(claimElement.valueOf("@completed").equalsIgnoreCase("Y"))  {
                       	     }//end of if(strActiveSubLink.equalsIgnoreCase("Claims")) 
                       	  }//end of  if(strActiveLink.equalsIgnoreCase("Hospital Information"))
                           
                           
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
                                        out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
                                            out.print("Age (Yrs):");
                                        out.print("</td>");
                                        out.print("<td class=\"textLabel\">");
                                            out.print(claimantElement.valueOf("@age"));
                                        out.print("</td>");
                                    out.print("</tr>");

                                 /*   out.print("<tr>");
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
                                    out.print("</tr>");*/

                                /*    out.print("<tr>");
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
        	                               out.print("Avail. Buffer Amt. (Rs):");
        	                           out.print("</td>");
        	                           out.print("<td class=\"textLabel\">");
        	                               out.print(claimantElement.valueOf("@availablebuffer"));
        	                           out.print("</td>");
        	                           out.print("<td height=\"20\" class=\"formLabel\">");
        	                               out.print("Cumulative Bonus (Rs):");
        	                           out.print("</td>");
        	                           out.print("<td class=\"textLabel\">");
        	                               out.print(claimantElement.valueOf("@availablebonus"));
        	                           out.print("</td>");
                                   out.print("</tr>");
                                 		*///commented as per Amount not be shown to hospitals
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
        			                 
        			               /*  out.print("<td height=\"20\" class=\"formLabel\">");
        			                     out.print("Claimant Phone No.:");
        			                 out.print("</td>");
        			                 out.print("<td class=\"textLabel\">");
        			                     out.print(claimantElement.valueOf("@claimantphone"));
        			                 out.print("</td>");*/
        		                 out.print("</tr>");
        		                 
        		                /* out.print("<tr>");
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
        	                 out.print("</tr>");*/
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

        	                        out.print("<tr>");
        	                          /* out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
        	                               out.print("Intimation Date:");
        	                           out.print("</td>");
        	                           
        	                           out.print("<td class=\"textLabel\" width=\"25%\">");
        	                               out.print(claimElement.valueOf("@intimationdate"));
        	                           out.print("</td>");
        	                           */
        	                        	out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
        	                        		out.print("Requested Amt. (Rs):");
        	                        	out.print("</td>");
 	                           
        	                        	out.print("<td class=\"textLabel\" width=\"25%\">");
        	                        		out.print(claimElement.valueOf("@requestedamount"));
        	                        	out.print("</td>");
        	                        
        	                        
        	                           out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
        	                           	   out.print("Mode:");
        	                           out.print("</td>");
        	                           out.print("<td class=\"textLabel\" width=\"31%\">");
        	                           		out.print(claimElement.valueOf("@mode"));
        	                           out.print("</td>");
        	                        out.print("</tr>");

        	                      /*  out.print("<tr>");
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
        	                        out.print("</tr>");*/

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
        	                           
        	                           
        	                           /*removed as per the Req
        	                             out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
        	                           	   out.print("Processing Branch:");
        	                           out.print("</td>");
        	                           out.print("<td class=\"textLabel\" width=\"31%\">");
        	                           		out.print(claimElement.valueOf("@processingbranch"));
        	                           out.print("</td>");
        	                        out.print("</tr>");

        	                        out.print("<tr>");*/
        	                           
        	                           
        	                           /*out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
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
                               			posthosp="checked";*/
                               		   if(Integer.parseInt(claimElement.valueOf("@hospitalization"))>0)
                               			hosp="checked";
        	                           /*out.print("<td class=\"textLabel\" width=\"31%\">");
        	                           		out.print("<input type=\"checkbox\" disabled=\"true\" "+prehosp+" >");
        	                           out.print("</td>");
        	                        out.print("</tr>");*/

                               		/*out.print("<tr>");
        	                           out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
        	                           		out.print("Post-Hospitalisation:");
        	                           out.print("</td>");
        	                           out.print("<td class=\"textLabel\" width=\"25%\">");
        	                           		out.print("<input type=\"checkbox\" disabled=\"true\" "+posthosp+" >");
        	                           out.print("</td>");*/
        	                           out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
        	                           		out.print("Hospitalisation :");
        	                           out.print("</td>");
        	                           out.print("<td class=\"textLabel\" width=\"25%\">");
        	                           		out.print("<input type=\"checkbox\" disabled=\"true\" "+hosp+" >");
        	                           out.print("</td>");
        	                        out.print("</tr>");
        	                        //commented as per latest requirement
        	                       /* out.print("<tr>");
                                    out.print("<td height=\"25\" class=\"formLabel\">");
                                        out.print("Remarks:");
                                    out.print("</td>");
                                    out.print("<td class=\"textLabel\">");
                                        out.print(claimElement.valueOf("@claimsremarks"));
                                    out.print("</td>");
                                    out.print("<td>&nbsp;</td>");
                                    out.print("<td class=\"textLabel\">&nbsp;</td>");
                                out.print("</tr>");*/
                            	out.print("</table>");
                                out.print("</fieldset>");
                                   
                                   if(strActiveLink.equalsIgnoreCase("Hospital Information")){   
                                 	  if(strActiveSubLink.equalsIgnoreCase("Claims Status"))  	  {
                                 		  if(claimElement.valueOf("@completed").equalsIgnoreCase("Y"))  {
                                 				out.print("<input type=\"hidden\" name=\"authLtrTypeID\" id=\"authLtrTypeID\" value=\""+claimElement.valueOf("@authtypeid")+"\"/>");
                                            	out.print("<input type=\"hidden\" name=\"claimSeqID\" id=\"claimSeqID\" value=\""+claimElement.valueOf("@seqid")+"\"/>");
                                        		out.print("<input type=\"hidden\" name=\"statusTypeID\" id=\"statusTypeID\" value=\""+claimElement.valueOf("@statusid")+"\"/>");
                                				out.print("<input type=\"hidden\" name=\"completedYN\" id=\"completedYN\" value=\""+claimElement.valueOf("@completed")+"\"/>");
                                				out.print("<input type=\"hidden\" name=\"cignastatusYN\" id=\"cignastatusYN\"  value=\""+claimElement.valueOf("@cignastatus")+"\"/>");
                                				out.print("<input type=\"hidden\" name=\"authorizationNo\" id=\"authorizationNo\"  value=\""+claimElement.valueOf("@authorizationnumber")+"\"/>");
                                				out.print("<input type=\"hidden\" name=\"preAuthNo\" id=\"preAuthNo\" value=\""+claimElement.valueOf("@claimnumber")+"\"/>");
                                   
                                 		  	}//end of if(claimElement.valueOf("@completed").equalsIgnoreCase("Y"))  {
                                 	  	}//end of if(strActiveSubLink.equalsIgnoreCase("Claims")) 
                                   	}//end of  if(strActiveLink.equalsIgnoreCase("Hospital Information"))
                    }//end of if(!((List)historyDoc.selectNodes("//claimdetails")).isEmpty())
         	   }  //end if(strActiveSublink.equals("Claims"))
            	   
	        	   
	        	  	  
            	   /**
            	    * History details of Policy Details
            	    */
            	   
	        	   /*   if(!((List)historyDoc.selectNodes("//policydetails")).isEmpty())
                   {
                       policydetailsList= (List)historyDoc.selectNodes("//policydetails");
                       policydetailsElement = (Element)policydetailsList.get(0);
                       out.print("<fieldset>");
                           out.print("<legend>Policy Details</legend>");
                           out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
                                   out.print("<tr>");
                                       out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
                                           out.print("Policy No.:");
                                       out.print("</td>");
                                       out.print("<td width=\"25%\" class=\"textLabel\">");
                                           out.print(policydetailsElement.valueOf("@policynumber"));
                                       out.print("</td>");
                                       out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
                                           out.print("Healthcare Company:");
                                       out.print("</td>");
                                       out.print("<td class=\"textLabel\" width=\"31%\">");
                                           out.print(policydetailsElement.valueOf("@inscompname"));
                                       out.print("</td>");
                                   out.print("</tr>");

                                   out.print("<tr>");
                                       out.print("<td height=\"25\" class=\"formLabel\">");
                                           out.print("Policy Holder's Name: ");
                                       out.print("</td>");
                                       out.print("<td class=\"textLabel\">");
                                           out.print(policydetailsElement.valueOf("@policyholder"));
                                       out.print("</td>");
                                       out.print("<td class=\"formLabel\">");
                                           out.print("Phone:");
                                       out.print("</td>");
                                       out.print("<td class=\"textLabel\">");
                                           out.print(policydetailsElement.valueOf("@phone"));
                                       out.print("</td>");
                                   out.print("</tr>");

                                   out.print("<tr>");
                                       out.print("<td height=\"25\" class=\"formLabel\">");
                                           out.print("Product / Policy Name:");
                                       out.print("</td>");
                                       out.print("<td class=\"textLabel\">");
                                           out.print(policydetailsElement.valueOf("@productname"));
                                       out.print("</td>");
                                       out.print("<td class=\"formLabel\">");
                                           out.print("Term Status:");
                                       out.print("</td>");
                                       out.print("<td class=\"textLabel\">");
                                           out.print(policydetailsElement.valueOf("@termstatus"));
                                       out.print("</td>");
                                   out.print("</tr>");

                                   out.print("<tr>");
                                       out.print("<td height=\"25\" class=\"formLabel\">");
                                           out.print("Policy Type:");
                                       out.print("</td>");
                                       out.print("<td class=\"textLabel\">");
                                           out.print(policydetailsElement.valueOf("@policytype"));
                                       out.print("</td>");
                                       out.print("<td class=\"formLabel\">");
                                           out.print("Policy Sub Type:");
                                       out.print("</td>");
                                       out.print("<td class=\"textLabel\">");
                                           out.print(policydetailsElement.valueOf("@policysubtype"));
                                       out.print("</td>");
                                   out.print("</tr>");

                                   out.print("<tr>");
                                       out.print("<td height=\"25\" class=\"formLabel\">");
                                           out.print("Policy Start Date:");
                                       out.print("</td>");
                                       out.print("<td class=\"textLabel\">");
                                           out.print(policydetailsElement.valueOf("@startdate"));
                                       out.print("</td>");
                                       out.print("<td class=\"formLabel\">");
                                           out.print("Policy End Date:");
                                       out.print("</td>");
                                       out.print("<td class=\"textLabel\">");
                                           out.print(policydetailsElement.valueOf("@enddate"));
                                       out.print("</td>");
                                   out.print("</tr>");
                                   //added by rekha <!-- added for koc1289_1272  -->
                                if(policydetailsElement.valueOf("@inscompname").equals("CIGNA INSURANCE COMPANY"))   
                                {
                                   out.print("<tr>");
                                   out.print("<td height=\"25\" class=\"formLabel\">");
                                       out.print("Policy Actual Start Date(Long Term Policy):");
                                   out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(policydetailsElement.valueOf("@actualstartdate"));
                                   out.print("</td>");
                                   out.print("<td class=\"formLabel\">");
                                       out.print("Policy Actual End Date(Long Term Policy):");
                                   out.print("</td>");
                                   out.print("<td class=\"textLabel\">");
                                       out.print(policydetailsElement.valueOf("@actualenddate"));
                                   out.print("</td>");
                               out.print("</tr>");
                                }    
                                 //added by rekha <!-- End added for koc1289_1272  -->
                                   
                              
                                   out.print("<tr>");
                                       out.print("<td height=\"25\" class=\"formLabel\">");
                                           out.print("Avail. Buffer Amt. (Rs):");
                                       out.print("</td>");
                                       out.print("<td class=\"textLabel\">");
                                           out.print(policydetailsElement.valueOf("@availablebufferamount"));
                                       out.print("</td>");
                                       out.print("<td class=\"formLabel\">");
                                           out.print("Cumulative Bonus (Rs):");
                                       out.print("</td>");
                                       out.print("<td class=\"textLabel\">");
                                           out.print(policydetailsElement.valueOf("@availablebonus"));
                                       out.print("</td>");
                                   out.print("</tr>");
                                   out.print("<tr>");
                                   out.print("<td height=\"25\" class=\"formLabel\">");
                                   out.print("Zone Code:");
                                   out.print("</td>");
                                   out.print("<td colspan=\"4\" class=\"textLabel\">");
                                   out.print(policydetailsElement.valueOf("@zonecode"));
                                   out.print("</td>");
                                   out.print("</tr>");
                                  
                               out.print("</table>");
                       out.print("</fieldset>");
                   }//end of if(!((List)historyDoc.selectNodes("//policydetails")).isEmpty())  	   */
	        	   
	        	   
	        	   
	        	   
	        	   
	        	   
	        	   /***
	        	    * 
	        	    * Details about IcdPCSCoding details
	        	    */
	        	   
	        	/*	if(!((List)historyDoc.selectNodes("//icdpcscodingdetails/provisionaldiagnosis")).isEmpty())
	               	{
	               		 provisionaldiagnosisList= (List)historyDoc.selectNodes("//icdpcscodingdetails/provisionaldiagnosis");
	               		 provisionaldiagnosisElement = (Element)provisionaldiagnosisList.get(0);
	               		out.print("<fieldset><legend>Ailment Details</legend>");
	               		 out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	    	           		out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
	    	           	 if((strActiveLink.equalsIgnoreCase("Hospital Information")) &&  (strActiveSubLink.equalsIgnoreCase("Pre-Auth")))  	
	    		            {
	    	           			out.print("Provisional Diagnosis:");
	    		            }//end of if(frmHistoryList.get("PreAuthHistoryTypeID").equals("HPR"))
	    	           		else if((strActiveLink.equalsIgnoreCase("Hospital Information")) &&  (strActiveSubLink.equalsIgnoreCase("Claims")))  	
	    		            {
	    	           			out.print("Final Diagnosis:");
	    		            }//end of else if(frmHistoryList.get("PreAuthHistoryTypeID").equals("HCL"))
	    		            out.print("</td>");
	    		            out.print("<td width=\"78%\" class=\"textLabel\">");
	    		            	out.print(provisionaldiagnosisElement.valueOf("@description"));
	    		            out.print("</td>");
	    	            out.print("</table>");
	    	            out.print("</fieldset>");
	    	            //out.print("<br>");

	               	}
	               	if(!(strActiveTab.equals("Online Forms")))
	               	{	
	    	           	if(!((List)historyDoc.selectNodes("//icdpcscodingdetails/record")).isEmpty())
	    	               {
	    	                   icdpcscodingdetailsList = (List)historyDoc.selectNodes("//icdpcscodingdetails/record");
	    	                   //double dbPackagerate=0,dbValidatedamount=0,dbApprovedamount=0;
	    	                   BigDecimal dbApprovedamount=new BigDecimal("0.00");
	    	                   out.print("<fieldset><legend>ICD / PCS Coding Details</legend>");
	    	                   if(icdpcscodingdetailsList!=null&& icdpcscodingdetailsList.size() > 0 )
	    	                   {
	    	
	    	
	    	                               out.print("<table align=\"center\" class=\"gridWithCheckBox zeroMargin\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	    	                               out.print("<tr>");
	    	                                   out.print("<td width=\"26%\" valign=\"top\" class=\"gridHeader\">Ailment</td>");
	    	                                   		   out.print("<td width=\"20%\" valign=\"top\" class=\"gridHeader\">Hospitalization Type</td>");
	    	                                   		   out.print("<td width=\"11%\" valign=\"top\" class=\"gridHeader\">ICD Code</td>");
	    	                                           out.print("<td width=\"22%\" valign=\"top\" class=\"gridHeader\">Package/Procedures</td>");
	    	                                           //out.print("<td width=\"14%\" align=\"right\" valign=\"top\" class=\"gridHeader\">Validated Amt. (Rs)</td>");
	    	                                           out.print("<td width=\"21%\" align=\"right\" valign=\"top\" class=\"gridHeader\">Approved Amt. (Rs)</td>");
	    	                                    out.print("</tr>");
	    	                                   
	    	                                   for(int icdcount=0;icdcount<icdpcscodingdetailsList.size();icdcount++)
	    	                                   {
	    	                                       icdpcsElement = (Element)icdpcscodingdetailsList.get(icdcount);
	    	                                       strClass=icdcount%2==0 ? "gridOddRow" : "gridEvenRow" ;
	    	
	    	
	    	
	    	                                       out.print("<tr class=\""+strClass+"\">");
	    	
	    	                                           out.print("<td>");
	    	                                               out.print(icdpcsElement.valueOf("@ailment")+"&nbsp;");
	    	                                           out.print("</td>");
	    	
	    	                                           out.print("<td>");
	    			                                  	   out.print(icdpcsElement.valueOf("@hospitalizationdescription")+"&nbsp;");
	    			                                  		if(icdpcsElement.valueOf("@hospitalizationtype").equals("REP"))
	    			                                  		{
	    			                                  			out.print(icdpcsElement.valueOf("@frequency")+"&nbsp;");
	    			                                  		}//end of if(icdpcsElement.valueOf("@hospitalizationtype").equals("REP"))
	    		                                  		out.print("</td>");
	    	
	    	                                           out.print("<td>");
	    	                                               out.print(icdpcsElement.valueOf("@icdcode")+"&nbsp;");
	    	                                           out.print("</td>");
	    	                                           out.print("<td>");
	    	                                               strProcedure=TTKCommon.replaceInString(icdpcsElement.valueOf("@pckageorprocedure"),",","<br>");
	    	                                               out.print(strProcedure+"&nbsp;");
	    	                                           out.print("</td>");
	    	//                                           out.print("<td align=\"right\">");
	    	//                                               if(!icdpcsElement.valueOf("@packagerate").equals(""))
	    	//                                               {
	    	//                                                   dbPackagerate+=TTKCommon.getDouble(icdpcsElement.valueOf("@packagerate"));
	    	//                                               }//end of if(!icdpcsElement.valueOf("packagerate").getText().equals(""))
	    	//                                               out.print(icdpcsElement.valueOf("@packagerate")+"&nbsp;");
	    	//                                           out.print("</td>");
	    	//
	    	//                                           out.print("<td align=\"right\">");
	    	//                                               if(!icdpcsElement.valueOf("@validatedamount").equals(""))
	    	//                                               {
	    	//                                                   dbValidatedamount+=TTKCommon.getDouble(icdpcsElement.valueOf("@validatedamount"));
	    	//                                               }//end of if(!icdpcsElement.valueOf("validatedamount").getText().equals(""))
	    	//                                               out.print(icdpcsElement.valueOf("@validatedamount")+"&nbsp;");
	    	//                                           out.print("</td>");
	    	
	    	                                           out.print("<td align=\"right\">");
	    	                                               if(!icdpcsElement.valueOf("@approvedamount").equals(""))
	    	                                               {
	    	                                                   dbApprovedamount=dbApprovedamount.add(TTKCommon.getBigDecimal(icdpcsElement.valueOf("@approvedamount")));
	    	                                               }//end of if(!icdpcsElement.valueOf("approvedamount").getText().equals(""))
	    	                                               out.print(icdpcsElement.valueOf("@approvedamount")+"&nbsp;");
	    	                                           out.print("</td>");
	    	                                       out.print("</tr>");
	    	                                   }//end of for(int icdcount=0;icdcount<icdpcscodingdetailsList.size();icdcount++)
	    	                           out.print("</table>");
	    	
	    	                           out.print("<table width=\"96%\" align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	    	                               out.print("<tr>");
	    	                               out.print("<td width=\"19%\" height=\"25\" align=\"right\" class=\"textLabelBold\">&nbsp;</td>");
	    	                               out.print("<td width=\"10%\" align=\"right\" class=\"textLabelBold\">&nbsp;</td>");
	    	                               out.print("<td width=\"15%\" height=\"25\" align=\"right\" class=\"textLabelBold\">Total (Rs):</td>");
	    	                               out.print("<td width=\"14%\" align=\"right\" class=\"textLabelBold\">");
	    	                               out.print(dbApprovedamount);
	    	                               out.print("</td>");
	    	                               out.print("</tr>");
	    	                           out.print("</table>");
	    	
	    	
	    	                   }//end of if(icdpcscodingdetailsList!=null&& icdpcscodingdetailsList.size() > 0 )
	    	                   out.print("</fieldset>");
	    	               }//end of if(!((List)doc.selectNodes("//icdpcscodingdetails/record")).isEmpty())
	               	}//end of if(!(strActiveTab.equals("Online Forms")))
	        	   */
	        	   
	                /**
	                 * 
	                 * This following code for Hospital History 
	                 */
	   	         
	               
	                    hospitalList = (List)historyDoc.selectNodes("//hospitaldetails");
	                    if(!((List)historyDoc.selectNodes("//hospitaldetails")).isEmpty())
	                    {
	                        hospitalElement = (Element)hospitalList.get(0);

	                        out.print("<fieldset>");
	                            out.print("<legend>Hospital Details</legend>");
	                            out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	                                out.print("<tr>");
	                                    out.print("<td height=\"20\" class=\"formLabel\">");
	                                        out.print("Hospital Name:");
	                                    out.print("</td>");
	                                    out.print("<td  class=\"textLabelBold\">");
	                                           out.print(hospitalElement.valueOf("@hospitalname"));
	                                           out.print("&nbsp;&nbsp;");
	                                           //check for the image and load appropriate image.
	                                          /* if(hospitalElement.valueOf("@hospitalgrade").equals("G"))
	                                           {
	                                               strGrade="GoldStar";
	                                               strAlt="Gold Star";
	                                           }//end of if(hospitalElement.valueOf("hospitalgrade").getText().equals("G"))
	                                           else if(hospitalElement.valueOf("@hospitalgrade").equals("R"))
	                                           {
	                                               strGrade="BlueStar";
	                                               strAlt="Blue Star";
	                                           }//end of else if(hospitalElement.valueOf("hospitalgrade").getText().equals("R"))
	                                           else if(hospitalElement.valueOf("@hospitalgrade").equals("B"))
	                                           {
	                                               strGrade="BlackStar";
	                                               strAlt="Black Star";
	                                           }//end of else if(hospitalElement.valueOf("hospitalgrade").getText().equals("B"))
	                                           if(!strGrade.equals(""))
	                                           {
	                                               out.print("<img src=\"/ttk/images/");
	                                               out.print(strGrade);
	                                               out.print(".gif\" alt=\""+strAlt+"\" width=\"15\" height=\"12\" align=\"absmiddle\">");
	                                           }//end of if(!strGrade.equals(""))*/
	                                    out.print("</td>");

	                                    out.print("<td height=\"20\" class=\"formLabel\">");
	                                         out.print("Empanelment Number:");
	                                    out.print("</td>");
	                                    out.print(" <td class=\"textLabel\">");
	                                        out.print(hospitalElement.valueOf("@empanelnumber"));
	                                    out.print("</td>");
	                                out.print("</tr>");

	                                out.print("<tr>");
	                                    out.print("<td height=\"20\" class=\"formLabel\">");
	                                        out.print("City: ");
	                                    out.print("</td>");
	                                    out.print("<td class=\"textLabel\">");
	                                        out.print(hospitalElement.valueOf("@city"));
	                                    out.print("</td>");
	                                    out.print("<td class=\"formLabel\">");
	                                        out.print("State: ");
	                                    out.print("</td>");
	                                    out.print("<td class=\"textLabel\">");
	                                        out.print(hospitalElement.valueOf("@state"));
	                                    out.print("</td>");
	                                out.print("</tr>");
	                            out.print("</table>");
	                       out.print("</fieldset>");
	                    }//end of if(!((List)historyDoc.selectNodes("//hospitaldetails")).isEmpty())
	                    
	                    /**
	                     * In case of Sublink in hospital Login  is Pre-Auth it takes this Authorization  history
	                     */
	                    if(strActiveSubLink.equalsIgnoreCase("Cashless Status"))  	  {
	                    	
	                    	  if(!((List)historyDoc.selectNodes("//authorizationdetails")).isEmpty())
	                          {
	                              authorizationList= (List)historyDoc.selectNodes("//authorizationdetails");
	                              authorizationElement = (Element)authorizationList.get(0);

	                              out.print("<fieldset>");
	                                  out.print("<legend>Authorization Details</legend>");
	                                  out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	                                      out.print("<tr>");
	                                          out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
	                                              out.print("Authorization No:");
	                                          out.print("</td>");
	                                          out.print("<td class=\"textLabelBold\" width=\"25%\">");
	                                              out.print(authorizationElement.valueOf("@authoriztionnumber"));
	                                          out.print("</td>");
	                                          out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">&nbsp;</td>");
	                                          out.print("<td class=\"textLabelBold\" width=\"31%\">&nbsp;</td>");
	                                       out.print("</tr>");

	                                      /* out.print("<tr>");
	                                           out.print("<td height=\"25\" class=\"formLabel\">");
	                                               out.print("Total Sum Insured (Rs):");
	                                           out.print("</td>");
	                                           out.print("<td class=\"textLabel\">");
	                                               out.print(authorizationElement.valueOf("@totalsuminsured"));
	                                           out.print("</td>");
	                                           out.print("<td>");
	                                               out.print("Bal. Sum Insured (Rs):");
	                                           out.print("</td>");
	                                           out.print("<td class=\"textLabel\">");
	                                               out.print(authorizationElement.valueOf("@availablesuminsured"));
	                                           out.print("</td>");
	                                       out.print("</tr>");

	                                       out.print("<tr>");
	                                           out.print("<td height=\"25\" class=\"formLabel\">");
	                                               out.print("Avail. Cumu. Bonus (Rs):");
	                                           out.print("</td>");
	                                           out.print("<td class=\"textLabel\">");
	                                               out.print(authorizationElement.valueOf("@availablebonus"));
	                                           out.print("</td>");
	                                           out.print("<td class=\"formLabel\">");
	                                               out.print("Requested Amt. (Rs):");
	                                           out.print("</td>");
	                                           out.print("<td class=\"textLabel\">");
	                                               out.print(authorizationElement.valueOf("@requestedamount"));
	                                           out.print("</td>");
	                                       out.print("</tr>");

	                                       out.print("<tr>");
	                                           out.print("<td height=\"25\" class=\"formLabel\">");
	                                               out.print("Prev. Approved Amt. (Rs):");
	                                           out.print("</td>");
	                                           out.print("<td class=\"textLabel\">");
	                                               out.print(authorizationElement.valueOf("@prevapprovedamount"));
	                                           out.print("</td>");
	                                           out.print("<td height=\"20\" class=\"formLabel\">");
	                                               out.print("Approved Amt. (Rs):");
	                                           out.print("</td>");
	                                           out.print("<td class=\"textLabel\">");
	                                               out.print(authorizationElement.valueOf("@approvedamount"));
	                                           out.print("</td>");
	                                      out.print("</tr>");*/
	                                       
	                                       
	                          }//end of if(!((List)historyDoc.selectNodes("//authorizationdetails")).isEmpty())
	                    	/*	if(!TTKCommon.getActiveLink(request).equals("Online Forms"))
	                          	 {

	               	               if(!((List)historyDoc.selectNodes("//bufferamount")).isEmpty())
	               	               {
	               	                   bufferamountList = (List)historyDoc.selectNodes("//bufferamount");
	               	                   bufferamountElement = (Element)bufferamountList.get(0);

	               	                    out.print("<tr>");
	               	                        out.print("<td height=\"20\" colspan=\"4\" class=\"textLabelBold\">");
	               	                            out.print("Buffer Amount");
	               	                        out.print("</td>");
	               	                    out.print("</tr>");

	               	                    out.print("<tr>");
	               	                        out.print("<td height=\"25\" class=\"formLabel\">");
	               	                            out.print("Avail. Buffer Amt. (Rs):");
	               	                        out.print("</td>");
	               	                        out.print("<td class=\"textLabel\">");
	               	                            out.print(bufferamountElement.valueOf("@availablebufferamount"));
	               	                        out.print("</td>");
	               	                        out.print("<td class=\"formLabel\">");
	               	                        out.print("&nbsp;");
	               	                        out.print("</td>");
	               	                        out.print("<td class=\"textLabel\">");
	               	                        out.print("&nbsp;");
	               	                        out.print("</td>");
	               	                    out.print("</tr>");

	               	                    out.print("<tr>");
	               	                        out.print("<td height=\"25\">");
	               	                            out.print("Approved Buffer Amt. (Rs):");
	               	                        out.print("</td>");
	               	                        out.print("<td class=\"textLabel\">");
	               	                            out.print(bufferamountElement.valueOf("@approvedbufferamount"));
	               	                        out.print("</td>");
	               	                        out.print("<td class=\"formLabel\">");
	               	                            out.print("Approved Date / Time:");
	               	                        out.print("</td>");
	               	                        out.print("<td class=\"textLabel\">");
	               	                            out.print(bufferamountElement.valueOf("@bufferapproveddate"));
	               	                        out.print("</td>");
	               	                    out.print("</tr>");
	               	               }//end of if(!((List)historyDoc.selectNodes("//authorizationdetails")).isEmpty())
	                          	 	}//end of if(!TTKCommon.getActiveLink(request).equals("Online Forms"))
	                        */  
	                              if(!((List)historyDoc.selectNodes("//approvalstatus")).isEmpty())
	                              {
	 	          	            	 List  approvalstatusList=null;Element approvalstatusElement=null;

	                                  approvalstatusList= (List)historyDoc.selectNodes("//approvalstatus");
	                                  approvalstatusElement= (Element)approvalstatusList.get(0);

	                                   out.print("<tr>");
	                                       out.print("<td height=\"25\" class=\"textLabelBold\">");
	                                           out.print("Approval Status");
	                                       out.print("</td>");
	                                       
	                                       
	                                     
	                                       //kocnewhosp1, Approved Date and time move up as told by shankar.
	                                       out.print("<td height=\"25\" class=\"formLabel\">");
                                           out.print("&nbsp;");
                                       out.print("</td>");
	                                    out.print("<td height=\"25\" class=\"formLabel\">");
                                           out.print("Approved Date / Time:");
                                       out.print("</td>");
                                       out.print(" <td class=\"textLabel\">");
                                           out.print(approvalstatusElement.valueOf("@approveddate"));
                                       out.print("</td>");
                                       out.print("<td>");
                                       out.print("</td>");
                                       
                                       
	                                   out.print("</tr>");

	                                   out.print("<tr>");
	                                       out.print("<td height=\"25\" class=\"formLabel\">");
	                                           out.print("Status:");
	                                       out.print("</td>");
	                                       out.print("<td class=\"textLabel\">");
	                                           out.print(approvalstatusElement.valueOf("@status"));
	                                       out.print("</td>");
	                                       /*removed as per new Req
	                                        * out.print("<td height=\"20\" class=\"formLabel\">");
	                                           out.print("Authorized By:");
	                                       out.print("</td>");
	                                       out.print("<td class=\"textLabel\">");
	                                           out.print(approvalstatusElement.valueOf("@approvedby"));
	                                       out.print("</td>");
	                                   out.print("</tr>");

	                                   out.print("<tr>");*/
	                                       
	                                       
	                                       //kocnewhosp1
	                                       if(strPreAuthShortFallYN.equalsIgnoreCase("Y"))
	                                   	   {
	                                      
	                	                         out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
	                	                             out.print("<a href=\"#\" onClick=\"javascript:online_Preauth_shortfall('"+preAuthShrtFallElement.valueOf("@seq_id")+"');\"><font size=\"2\">Shortfall History</font></a>");
	                	                            
		                	                             out.print("<img src=\"/ttk/images/GoldStar");
			                                             out.print(".gif\" width=\"25\" height=\"10\" align=\"absmiddle\">");
	                	                         out.print("</td>");
	                	                         out.print("<td width=\"25%\" class=\"textLabelBold\">");
	                	                         out.print("</td>");
	                	                            out.print("<td class=\"formLabel\" width=\"22%\">");
	                	                         out.print("</td>");
	                	                         out.print("<td class=\"textLabel\" width=\"31%\">");
	                	                         out.print("</td>");
	                                   	   }
	                                       /*out.print("<td height=\"25\" class=\"formLabel\">");
	                                           out.print("Approved Date / Time:");
	                                       out.print("</td>");
	                                       out.print("<td class=\"textLabel\">");
	                                           out.print(approvalstatusElement.valueOf("@approveddate"));
	                                       out.print("</td>");
	                                       out.print("<td>");
	                                       out.print("</td>");*/
	                                       
	                                       
	                                       /*out.print("<td>");
	                                           out.print("Reason: ");
	                                       out.print("</td>");
	                                       out.print("<td class=\"textLabel\">");
	                                           out.print(approvalstatusElement.valueOf("@reason"));
	                                       out.print("</td>");*/
	                                   out.print("</tr>");

	                                 /*  out.print("<tr>");
	                                       out.print("<td class=\"formLabel\" height=\"25\">");
	                                           out.print("Remarks:");
	                                       out.print("</td>");
	                                       out.print("<td height=\"25\" colspan=\"3\" class=\"textLabel\">");
	                                           out.print(approvalstatusElement.valueOf("@remarks"));
	                                       out.print("</td>");*/
	                                   out.print("</tr>");
	                                   out.print("</table>");
	                               out.print("</fieldset>");
	                              }//end of if(!((List)historyDoc.selectNodes("//approvalstatus")).isEmpty())
	                    	
	                    }//end of if(strActiveSubLink.equalsIgnoreCase("Pre-Auth")) 
	                    
	                    /**
	                     * In case of Claims sublink in hospital Login it takes this settlement history
	                     */
	                    if(strActiveSubLink.equalsIgnoreCase("Claims Status"))  	
	                    {
	                    	 if(!((List)historyDoc.selectNodes("//settlementdetails")).isEmpty())
	                         {
	                    		 settlementList= (List)historyDoc.selectNodes("//settlementdetails");
	                      	     settlementElement = (Element)settlementList.get(0);

	                             out.print("<fieldset>");
	                                 out.print("<legend>Settlement Details</legend>");
	                                 out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	                                     out.print("<tr>");
	                                         out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
	                                             out.print("Claim Settlement No:");
	                                         out.print("</td>");
	                                         out.print("<td class=\"textLabelBold\" width=\"25%\">");
	                                             out.print(settlementElement.valueOf("@settlementnumber"));
	                                         out.print("</td>");
	                                         out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">&nbsp;</td>");
	                                         out.print("<td class=\"textLabel\" width=\"31%\">&nbsp;</td>");
	                                      out.print("</tr>");

	                                   /*   out.print("<tr>");
	                                          out.print("<td height=\"25\" class=\"formLabel\">");
	                                              out.print("Total Sum Insured (Rs):");
	                                          out.print("</td>");
	                                          out.print("<td class=\"textLabel\">");
	                                              out.print(settlementElement.valueOf("@totalsuminsured"));
	                                          out.print("</td>");
	                                          out.print("<td>");
	                                              out.print("Bal. Sum Insured (Rs):");
	                                          out.print("</td>");
	                                          out.print("<td class=\"textLabel\">");
	                                              out.print(settlementElement.valueOf("@availablesuminsured"));
	                                          out.print("</td>");
	                                      out.print("</tr>");

	                                      out.print("<tr>");
	                                          out.print("<td height=\"25\" class=\"formLabel\">");
	                                              out.print("Avail. Cumu. Bonus (Rs):");
	                                          out.print("</td>");
	                                          out.print("<td class=\"textLabel\">");
	                                              out.print(settlementElement.valueOf("@availablebonus"));
	                                          out.print("</td>");
	                                          out.print("<td class=\"formLabel\">");
	                                              out.print("Requested Amt. (Rs):");
	                                          out.print("</td>");
	                                          out.print("<td class=\"textLabel\">");
	                                              out.print(settlementElement.valueOf("@requestedamount"));
	                                          out.print("</td>");
	                                      out.print("</tr>");

	                                      out.print("<tr>");
	                                          out.print("<td height=\"25\" class=\"formLabel\">");
	                                              out.print("Prev. Approved Amt. (Rs):");
	                                          out.print("</td>");
	                                          out.print("<td class=\"textLabel\">");
	                                              out.print(settlementElement.valueOf("@preauthapprovedamount"));
	                                          out.print("</td>");
	                                          out.print("<td height=\"20\" class=\"formLabel\">");
	                                              out.print("Approved Amt. (Rs):");
	                                          out.print("</td>");
	                                          out.print("<td class=\"textLabel\">");
	                                              out.print(settlementElement.valueOf("@approvedamount"));
	                                          out.print("</td>");
	                                     out.print("</tr>");
	                                     */
	                       }//end of if(!((List)historyDoc.selectNodes("//settlementdetails")).isEmpty())

	                     /*    if(!((List)historyDoc.selectNodes("//bufferamount")).isEmpty())
	                         {
	                      	   bufferList = (List)historyDoc.selectNodes("//bufferamount");
	                      	   bufferElement = (Element)bufferList.get(0);

	          			               out.print("<tr>");
	          			                   out.print("<td height=\"20\" colspan=\"4\" class=\"textLabelBold\">");
	          			                       out.print("Buffer Amount");
	          			                   out.print("</td>");
	          			               out.print("</tr>");

	          			               out.print("<tr>");
	          			                   out.print("<td height=\"25\" class=\"formLabel\">");
	          			                       out.print("Avail. Buffer Amt. (Rs):");
	          			                   out.print("</td>");
	          			                   out.print("<td class=\"textLabel\">");
	          			                       out.print(bufferElement.valueOf("@availablebufferamount"));
	          			                   out.print("</td>");
	          			                   out.print("<td class=\"formLabel\">");
	          			                   out.print("&nbsp;");
	          			                   out.print("</td>");
	          			                   out.print("<td class=\"textLabel\">");
	          			                   out.print("&nbsp;");
	          			                   out.print("</td>");
	          			               out.print("</tr>");
	          			               out.print("<tr>");
	          	                        out.print("<td height=\"25\">");
	          	                            out.print("Approved Buffer Amt. (Rs):");
	          	                        out.print("</td>");
	          	                        out.print("<td class=\"textLabel\">");
	          	                            out.print(bufferElement.valueOf("@approvedbufferamount"));
	          	                        out.print("</td>");
	          	                        out.print("<td class=\"formLabel\">");
	          	                            out.print("Approved Date / Time:");
	          	                        out.print("</td>");
	          	                        out.print("<td class=\"textLabel\">");
	          	                            out.print(bufferElement.valueOf("@bufferapproveddate"));
	          	                        out.print("</td>");
	          	                    out.print("</tr>");
	          	               }//end of if(!((List)historyDoc.selectNodes("//bufferamount")).isEmpty())
						*/
	          	               if(!((List)historyDoc.selectNodes("//approvalstatus")).isEmpty())
	          	               {
	          	            	 List  approvalstatusList=null;Element approvalstatusElement=null;
	          	                   approvalstatusList= (List)historyDoc.selectNodes("//approvalstatus");
	          	                   approvalstatusElement= (Element)approvalstatusList.get(0);

	          	                    out.print("<tr>");
	          	                        out.print("<td height=\"25\" colspan=\"4\" class=\"textLabelBold\">");
	          	                            out.print("Approval Status");
	          	                        out.print("</td>");
	          	                    out.print("</tr>");

	          	                    out.print("<tr>");
	          	                        out.print("<td height=\"25\" class=\"formLabel\">");
	          	                            out.print("Status:");
	          	                        out.print("</td>");
	          	                        out.print("<td class=\"textLabel\">");
	          	                            out.print(approvalstatusElement.valueOf("@status"));
	          	                        out.print("</td>");
	          	                        out.print("<td height=\"20\" class=\"formLabel\">");
	          	                      out.print("&nbsp;");
	          	                            /*out.print("Settled By:");
	          	                        out.print("</td>");
	          	                        out.print("<td class=\"textLabel\">");
	          	                            out.print(approvalstatusElement.valueOf("@approvedby"));
	          	                        out.print("</td>");*/
	          	                    out.print("</tr>");

	          	                    out.print("<tr>");
	          	                        out.print("<td height=\"25\" class=\"formLabel\">");
	          	                            out.print("Settled Date / Time:");
	          	                        out.print("</td>");
	          	                        out.print("<td class=\"textLabel\">");
	          	                            out.print(approvalstatusElement.valueOf("@approveddate"));
	          	                        out.print("</td>");
	          	                      
	          	                        
	          	                    //kocnewhosp1
                                   if(strClaimShortFallYN.equalsIgnoreCase("Y"))
                               	   {
                                  
                                	   out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
            		                   out.print("<a href=\"#\" onClick=\"javascript:online_Claims_shortfall('"+claimShrtFallElement.valueOf("@seq_id")+"');\"><font size=\"2\">Shortfall History</font></a>");
            		                   out.print("<img src=\"/ttk/images/GoldStar");
                                       out.print(".gif\" width=\"25\" height=\"10\" align=\"absmiddle\">");
            		                   out.print("</td>");
            		                   out.print("<td class=\"textLabelBold\" width=\"25%\">");
            		                   out.print("</td>");
            		                   out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
            		                   out.print("</td>");
            		                   out.print("<td class=\"textLabel\" width=\"31%\">");
            		                   out.print("</td>");
                               	   }
	                                       
	          	                    /*  out.print("<td height=\"20\" class=\"formLabel\">");
	          	                            out.print("Reason: ");
	          	                        out.print("</td>");
	          	                        out.print("<td class=\"textLabel\">");
	          	                            out.print(approvalstatusElement.valueOf("@reason"));
	          	                        out.print("</td>");*/
	          	                    out.print("</tr>");

	          	                   /* out.print("<tr>");
	          	                        out.print("<td class=\"formLabel\" height=\"25\">");
	          	                            out.print("Remarks:");
	          	                        out.print("</td>");
	          	                        out.print("<td height=\"25\" colspan=\"3\" class=\"textLabel\">");
	          	                            out.print(approvalstatusElement.valueOf("@remarks"));
	          	                        out.print("</td>");
	          	                   out.print("</tr>");*/
	          	                  out.print("</table>");
	          	                 out.print("</fieldset>");
	          	         }//end of if(!((List)historyDoc.selectNodes("//approvalstatus")).isEmpty())
	               	
	                    }//end of if(strActiveSubLink.equalsIgnoreCase("Claims")) 
	               	
	                    				/*   Naration details Information */                
	     	        	if(!((List)historyDoc.selectNodes("//narrationdetails/record")).isEmpty())
	    	            {
	    	        		narrationdetailsList = (List)historyDoc.selectNodes("//narrationdetails/record");
	    	        		if(narrationdetailsList!=null&& narrationdetailsList.size() > 0 )
	    	                {
	    	        			out.print("<fieldset><legend>Narration Details</legend>");
	    		        			out.print("<table align=\"center\" class=\"gridWithCheckBox zeroMargin\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	    	                        out.print("<tr>");
	    	                            out.print("<td width=\"20%\" valign=\"top\" class=\"gridHeader\">Log Date</td>");
	    	                            out.print("<td width=\"60%\" valign=\"top\" class=\"gridHeader\">Description</td>");
	    	                            out.print("<td width=\"20%\" valign=\"top\" class=\"gridHeader\">User</td>");
	    	                        out.print("</tr>");

	    	                        for(int icdcount=0;icdcount<narrationdetailsList.size();icdcount++)
	                                {
	    	                        	narrationdetailsElement = (Element)narrationdetailsList.get(icdcount);
	                                    strClass=icdcount%2==0 ? "gridOddRow" : "gridEvenRow" ;
	                                    out.print("<tr class=\""+strClass+"\">");
	                                    out.print("<td>");
	                                    	out.print(narrationdetailsElement.valueOf("@referencedate")+"&nbsp;");
	                                    out.print("</td>");
	                                    out.print("<td>");
	                                    	out.print(narrationdetailsElement.valueOf("@remarks")+"&nbsp;");
	                                    out.print("</td>");
	                                    out.print("<td>");
	                                		out.print(narrationdetailsElement.valueOf("@user")+"&nbsp;");
	                                	out.print("</td>");
	                                    out.print("</tr>");
	                                }//end of for(int icdcount=0;icdcount<narrationdetailsList.size();icdcount++)
	    	                        out.print("</table>");
	    	                        out.print("</fieldset>");
	    	                   }//end of if(narrationdetailsList!=null&& narrationdetailsList.size() > 0 )

	    	            }//end of if(!((List)historyDoc.selectNodes("//narrationdetails/record")).isEmpty())
	               	
	     	        				/*   Cheque   details Information */  
	    	        	 if(!((List)historyDoc.selectNodes("//chequeinformation/record")).isEmpty())
	    	               {
	    	            	   chequeList = (List)historyDoc.selectNodes("//chequeinformation/record");
	    	            	   out.print("<fieldset><legend>Cheque Details</legend>");
	    	            	   if(chequeList!=null&& chequeList.size() > 0 ){
	    	            		   out.print("<table align=\"center\" class=\"gridWithCheckBox zeroMargin\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	    	                       out.print("<tr>");
	    	                       out.print("<td width=\"30%\" valign=\"top\" class=\"gridHeader\">Payee Name</td>");
	    	               		   out.print("<td width=\"12%\" valign=\"top\" class=\"gridHeader\">Cheque No.</td>");
	    	               		   out.print("<td width=\"12%\" valign=\"top\" class=\"gridHeader\">Cheque Amt.(Rs)</td>");
	    	               		   out.print("<td width=\"10%\" valign=\"top\" class=\"gridHeader\">TDS Amt.(Rs)</td>");
	    	                       out.print("<td width=\"10%\" valign=\"top\" class=\"gridHeader\">Cheque Date</td>");
	    	                       out.print("<td width=\"11%\" valign=\"top\" class=\"gridHeader\">Cheque Status</td>");
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
	    	                           out.print("</tr>");
	    	                       }//end of for(int chqcount=0;chqcount<chequeList.size();chqcount++)
	    	                       out.print("</table>");

	    	                   }//end of if(chequeList!=null&& chequeList.size() > 0 )
	    	            	   out.print("</fieldset>");
	    	               }//end of if(!((List)historyDoc.selectNodes("//chequeinformation")).isEmpty())	
	        	   
	    	        //Commented Shortfall link as we are showing it in the above table
	    	        	 /*	  if(strPreAuthShortFallYN.equalsIgnoreCase("Y"))
                   	   {
                      out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	                       out.print("<tr>");
	                         out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">");
	                             out.print("<a href=\"#\" onClick=\"javascript:online_Preauth_shortfall('"+preAuthShrtFallElement.valueOf("@seq_id")+"');\"><font size=\"2\">Shortfall History</font></a>");
	                         out.print("</td>");
	                         out.print("<td width=\"25%\" class=\"textLabelBold\">");
	                         out.print("</td>");
	                            out.print("<td class=\"formLabel\" width=\"22%\">");
	                         out.print("</td>");
	                         out.print("<td class=\"textLabel\" width=\"31%\">");
	                         out.print("</td>");
	                       out.print("</tr>");
                      out.print("</table>");
                   	   }
	    	        	
	    	        	  if(strClaimShortFallYN.equalsIgnoreCase("Y"))
	                	   {
	                	   out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
	                	   out.print("<tr>");
	                	   out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
		                   out.print("<a href=\"#\" onClick=\"javascript:online_Claims_shortfall('"+claimShrtFallElement.valueOf("@seq_id")+"');\"><font size=\"2\">Shortfall History</font></a>");
		                   out.print("</td>");
		                   out.print("<td class=\"textLabelBold\" width=\"25%\">");
		                   out.print("</td>");
		                   out.print("<td height=\"20\" class=\"formLabel\" width=\"22%\">");
		                   out.print("</td>");
		                   out.print("<td class=\"textLabel\" width=\"31%\">");
		                   out.print("</td>");
		                   out.print("</tr>");
		                   out.print("</table>");
	                	   }
	                	   */ 
	    	        	 
	           }//end of if(doc != null)

			
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
}//end of class OnlineHospitalHistory
