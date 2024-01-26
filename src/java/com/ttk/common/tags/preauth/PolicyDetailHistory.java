/**
 * @ (#) PolicyDetailHistory.java May 25, 2006
 * Project : TTK HealthCare Services
 * File : PolicyDetailHistory.java
 * Author : Srikanth H M
 * Company : Span Systems Corporation
 * Date Created : May 25, 2006
 *
 * @author : Srikanth H M
 * Modified by :
 * Modified date :
 * Reason :
 */

package com.ttk.common.tags.preauth;



import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import oracle.xml.parser.v2.XMLDOMException;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import com.ttk.common.TTKCommon;


/**
 *  This class builds the Policy History Information
 */
public class PolicyDetailHistory extends TagSupport{

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger( PolicyDetailHistory.class );

	public int doStartTag() throws JspException{
		try
		{

			log.debug("## Inside PolicyDetailHistory  ### ");
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			Document historyDoc = (Document)pageContext.getRequest().getAttribute("historyDoc");
			JspWriter out = pageContext.getOut();//Writer object to write the file
			Element memberElement=null,policyElement=null,suminsuredElement=null,cardElement=null,pedElement=null;
			String vipElement=null;
			List  memberlist=null,policylist=null,suminsuredlist=null,cardlist=null, pedlist=null;
			String strClass="";
			if(historyDoc != null)
			{
				if(!((List)historyDoc.selectNodes("/memberpolicyhistory")).isEmpty())
				{

					memberlist= (List)historyDoc.selectNodes("/memberpolicyhistory/member");
					policylist= (List)historyDoc.selectNodes("/memberpolicyhistory/policy");
					if(memberlist!=null&& memberlist.size() > 0 )
					{
						memberElement = (Element)memberlist.get(0);
						out.print("<fieldset><legend>Enrollment Details</legend>");
						out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
						out.print("<tr>");
						out.print("<td width=\"24%\" height=\"25\" class=\"formLabel\">");
						out.print("Enrollment Id:");
						out.print("</td>");
						out.print("<td width=\"33%\" class=\"textLabelBold\">");
						out.print(memberElement.valueOf("@enrollmentid"));
						out.print("</td>");
						out.print("<td width=\"23%\" class=\"formLabel\">");
						out.print("Member Name:");
						
						//changes on dec 10th KOC1136
						try{
						vipElement=memberElement.valueOf("@VIP_YN");
					
						if(vipElement.equalsIgnoreCase("Y")){
							
							out.print("<img src=\"/ttk/images/BlueStar.gif\" alt=\"VipCustomer\" width=\"16\" height=\"16\" border=\"0\" align=\"absmiddle\">");out.print("</a>&nbsp;&nbsp;");
						}
						
						}
						catch(XMLDOMException e)
						{
							log.error("VIP_YN element not found in Cursor return from DataBase");
						}
						//changes on dec 10th KOC1136
						out.print("</td>");
						out.print("<td width=\"20%\" class=\"textLabelBold\">");
						out.print(memberElement.valueOf("@memname"));
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td width=\"24%\" height=\"25\" class=\"formLabel\">");
						out.print("Emirates Id:");
						out.print("</td>");
						out.print("<td width=\"33%\" class=\"textLabelBold\">");
						out.print(memberElement.valueOf("@emiratesid"));
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("Gender:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@gender"));
						out.print("</td>");

						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("Category:");
						out.print("</td>");
						out.print("<td colspan=\"3\" class=\"textLabel\">");
						out.print(memberElement.valueOf("@category"));
						out.print("</td>");
						out.print("</tr>");


						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("DOB:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@memdob"));
						out.print("</td>");
						out.print("<td class=\"formLabel\">");
						out.print("Age:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@age"));
						out.print("</td>");
						out.print("</tr>");

						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("Date of Inception:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@dateofinception"));
						out.print("</td>");
						out.print("<td class=\"formLabel\">");
						out.print("Date of Exit:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@dateofexit"));
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("Certificate No.:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@certificateno"));
						out.print("</td>");
						out.print("<td class=\"formLabel\">");
						out.print("Customer Code:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@customercode"));
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("Member Scheme Sub Type:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@membersubtype"));
						out.print("</td>");
						out.print("<td class=\"formLabel\">");
						out.print("Member Sum Insured :");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@suminsured"));
						out.print("</td>");
						out.print("</tr>");

						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("Balance Sum Insured :");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@availablesuminsured"));
						out.print("</td>");
						if(policylist!=null&& policylist.size() > 0 )
						{
							policyElement = (Element)policylist.get(0);
							if(policyElement.valueOf("@enroltype").equals("COR"))
							{
								out.print("<td class=\"formLabel\">");
								out.print("");
								out.print("</td>");
								out.print("<td class=\"textLabel\">");
								out.print("");
								out.print("</td>");
							}//end of if(policyElement.valueOf("enroltype").equals("COR"))
							else
							{

								out.print("<td class=\"formLabel\">");
								out.print("Cumulative Bonus :");
								out.print("</td>");
								out.print("<td class=\"textLabel\">");
								out.print(memberElement.valueOf("@cumulativebonus"));
								out.print("</td>");
							}//end of else if(policyElement.valueOf("enroltype").equals("COR"))
						}//end of if(policylist!=null&& policylist.size() > 0 )
						out.print("</tr>");

						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("Address 1:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@add_1"));
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("Address 2:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@add_2"));
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("Address 3:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@add_3"));
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("State:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@add_state"));
						out.print("</td>");
						out.print("</tr>");

						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("City:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@add_4"));
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("Pincode:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@add_5"));
						out.print("</td>");

						out.print("</tr>");
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("Phone:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@phone"));
						out.print("</td>");
						out.print("</tr>");

						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("Diabetes Cover:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@diabetes_cover_yn"));
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("Hypertension Cover:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@hypertension_cover_yn"));
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\">");
						out.print("Member Remarks:");
						out.print("</td>");
						out.print("<td class=\"textLabel\">");
						out.print(memberElement.valueOf("@memberremarks"));
						out.print("</td>");
						out.print("</tr>");
						out.print("</table>");
						out.print("</fieldset>");

						out.print("<fieldset><legend>Scheme Information</legend>");
						out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
						if(policylist!=null&& policylist.size() > 0 )
						{

							policyElement = (Element)policylist.get(0);

							out.print("<tr>");
							out.print("<td colspan=\"4\" class=\"formLabel\" height=\"4\">");
							out.print("</td>");
							out.print("</tr>");
							out.print("<tr>");
							out.print("<td height=\"25\" class=\"formLabel\">");
							out.print("Scheme No.:");
							out.print("</td>");
							out.print("<td class=\"textLabelBold\">");
							out.print(policyElement.valueOf("@policynumber"));
							out.print("</td>");
							out.print("<td class=\"formLabel\">");
							out.print("Product / Scheme Name:");
							out.print("</td>");
							out.print("<td class=\"textLabelBold\">");
							out.print(policyElement.valueOf("@productname"));
							out.print("</td>");
							out.print("</tr>");

							out.print("<tr>");
							out.print("<td height=\"25\" class=\"formLabel\">");
							out.print("Scheme Holder's Name:");
							out.print("</td>");
							out.print("<td class=\"textLabel\">");
							out.print(policyElement.valueOf("@insuredname"));
							out.print("</td>");
							out.print("<td class=\"formLabel\">");
							out.print("Insurance Company:");
							out.print("</td>");
							out.print("<td class=\"textLabel\">");
							out.print(policyElement.valueOf("@inscompname"));
							out.print("</td>");
							out.print("</tr>");

							out.print("<tr>");
							out.print("<td height=\"25\" class=\"formLabel\">");
							out.print("Scheme Type:");
							out.print("</td>");
							out.print("<td class=\"textLabel\">");
							out.print(policyElement.valueOf("@policytype"));
							out.print("</td>");
							out.print("<td class=\"formLabel\">");
							out.print("Scheme Sub Type:");
							out.print("</td>");
							out.print("<td class=\"textLabel\">");
							out.print(policyElement.valueOf("@policysubtype"));
							out.print("</td>");
							out.print("</tr>");

							if(policyElement.valueOf("@enroltype").equalsIgnoreCase("COR"))
							{
								out.print("<tr>");
								out.print("<td height=\"25\" class=\"formLabel\">");
								out.print("Corporate Name:");
								out.print("</td>");
								out.print("<td class=\"textLabel\">");
								out.print(policyElement.valueOf("@groupname"));
								out.print("</td>");
								out.print("<td class=\"formLabel\">");
								out.print("");
								out.print("</td>");
								out.print("<td class=\"textLabel\">");
								out.print("");
								out.print("</td>");
								out.print("</tr>");
							}//end of if(policyElement.valueOf("@policysubtype").equalsIgnoreCase("COR"))

							out.print("<tr>");
							out.print("<td height=\"25\" class=\"formLabel\">");
							out.print("Scheme Start Date:");
							out.print("</td>");
							out.print("<td class=\"textLabel\">");
							out.print(policyElement.valueOf("@startdate"));
							out.print("</td>");
							out.print("<td class=\"formLabel\">");
							out.print("Scheme End Date:");
							out.print("</td>");
							out.print("<td class=\"textLabel\">");
							out.print(policyElement.valueOf("@enddate"));
							out.print("</td>");
							out.print("</tr>");
                        // added by rekha koc1289_1272
							   if(policyElement.valueOf("@inscompname").equals("CIGNA INSURANCE COMPANY"))   
	                            {	
							out.print("<tr>");
							out.print("<td height=\"25\" class=\"formLabel\">");
							out.print("Policy Actual Start Date(Long Term Policy):");
							out.print("</td>");
							out.print("<td class=\"textLabel\">");
							out.print(policyElement.valueOf("@actualstartdate"));
							out.print("</td>");
							out.print("<td class=\"formLabel\">");
							out.print("Policy Actual End Date(Long Term Policy):");
							out.print("</td>");
							out.print("<td class=\"textLabel\">");
							out.print(policyElement.valueOf("@actualenddate"));
							out.print("</td>");
							out.print("</tr>");
	                            }
							  //  end added by rekha koc1289_1272


							if(!TTKCommon.getActiveLink(request).equals("Online Forms"))
							{
								out.print("<tr>");
								out.print("<td height=\"25\" class=\"formLabel\">");
								out.print("Term Status:");
								out.print("</td>");
								out.print("<td class=\"textLabel\">");
								out.print(policyElement.valueOf("@termstatus"));
								out.print("</td>");
								out.print("<td class=\"formLabel\">");
								out.print("Scheme Name:");
								out.print("</td>");
								out.print("<td class=\"textLabel\">");
								out.print(policyElement.valueOf("@schemename"));
								out.print("</td>");
								out.print("</tr>");
								out.print("<tr>");
								out.print("<td height=\"25\" class=\"formLabel\">");
								out.print("Total Sum Insured (Rs):");
								out.print("</td>");
								out.print("<td class=\"textLabel\">");
								out.print(policyElement.valueOf("@suminsured"));
								out.print("</td>");
								out.print("<td class=\"formLabel\">");
								out.print("Proposal Date:");
								out.print("</td>");
								out.print("<td class=\"textLabel\">");
								out.print(policyElement.valueOf("@proposaldate"));
								out.print("</td>");
								out.print("</tr>");
							}//end of if(!TTKCommon.getActiveLink(request).equals("Online Forms"))
							if(TTKCommon.getActiveLink(request).equals("Online Forms"))
							{
								out.print("<tr>");
								out.print("<td class=\"formLabel\">");
								out.print("Term Status:");
								out.print("</td>");
								out.print("<td class=\"textLabel\">");
								out.print(policyElement.valueOf("@termstatus"));
								out.print("</td>");
								out.print("</tr>");
							}//end of if(TTKCommon.getActiveLink(request).equals("Online Forms"))


							out.print("<tr>");
							out.print("<td height=\"25\" class=\"formLabel\">");
							out.print("Home Phone:");
							out.print("</td>");
							out.print("<td class=\"textLabel\">");
							out.print(policyElement.valueOf("@resphoneno"));
							out.print("</td>");
							out.print("<td class=\"formLabel\">");
							out.print("Mobile:");
							out.print("</td>");
							out.print("<td class=\"textLabel\">");
							out.print(policyElement.valueOf("@mobileno"));
							out.print("</td>");
							out.print("</tr>");

							out.print("<tr>");
							out.print("<td height=\"25\" class=\"formLabel\">");
							out.print("Zone Code:");
							out.print("</td>");
							out.print("<td colspan=\"4\" class=\"textLabel\">");
							out.print(policyElement.valueOf("@zonecode"));
							out.print("</td>");
							out.print("</tr>");
							out.print("<tr>");
							out.print("<td height=\"25\" class=\"formLabel\">");
							out.print("Remarks:");
							out.print("</td>");
							out.print("<td colspan=\"3\" class=\"textLabel\">");
							out.print(policyElement.valueOf("@remarks"));
							out.print("</td>");
							out.print("</tr>");
							out.print("<tr>");
							out.print("<td height=\"25\" class=\"formLabel\">");
							out.print("Buffer Remarks:");
							out.print("</td>");
							out.print("<td colspan=\"3\" class=\"textLabel\">");
							out.print(policyElement.valueOf("@bufferremarks"));
							out.print("</td>");
							out.print("</tr>");

						}//end of if(policylist!=null&& policylist.size() > 0 )

						out.print("</table>");
						out.print("</fieldset>");

					}//end of if(memberlist!=null&& memberlist.size() > 0 )

					if(!((List)historyDoc.selectNodes("/memberpolicyhistory/member/suminsureddetails/breakup")).isEmpty())
					{
						suminsuredlist = (List)historyDoc.selectNodes("/memberpolicyhistory/member/suminsureddetails/breakup");
						out.print("<fieldset><legend>Sum Insured Details</legend>");
						if(suminsuredlist!=null&& suminsuredlist.size() > 0 )
						{
							out.print("<table align=\"center\" class=\"gridWithCheckBox zeroMargin\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
							out.print("<tr>");
							out.print("<td width=\"26%\" valign=\"top\" class=\"gridHeader\">Breakup</td>");
							out.print("<td width=\"20%\" valign=\"top\" class=\"gridHeader\">Sum Insured</td>");
							out.print("<td width=\"11%\" valign=\"top\" class=\"gridHeader\">Bonus</td>");
							out.print("<td width=\"22%\" valign=\"top\" class=\"gridHeader\">Eff dt/ Enh dt</td>");
							out.print("<td width=\"22%\" valign=\"top\" class=\"gridHeader\">Plan Type</td>");
							out.print("</tr>");

							for(int isumcount=0;isumcount<suminsuredlist.size();isumcount++)
							{
								suminsuredElement = (Element)suminsuredlist.get(isumcount);
								strClass=isumcount%2==0 ? "gridOddRow" : "gridEvenRow" ;

								out.print("<tr class=\""+strClass+"\">");
								out.print("<td>");
								out.print((isumcount+1)+"&nbsp;");
								out.print("</td>");

								out.print("<td>");
								out.print(suminsuredElement.valueOf("@suminsured")+"&nbsp;");
								out.print("</td>");

								out.print("<td>");
								out.print(suminsuredElement.valueOf("@bonus")+"&nbsp;");
								out.print("</td>");

								out.print("<td>");
								out.print(suminsuredElement.valueOf("@effectivedate")+"&nbsp;");
								out.print("</td>");

								out.print("<td>");
								out.print(suminsuredElement.valueOf("@prodplanname")+"&nbsp;");
								out.print("</td>");
								out.print("</tr>");
							}//end of for(int icdcount=0;icdcount<icdpcscodingdetailsList.size();icdcount++)
							out.print("</table>");
						}//end of if(suminsuredlist!=null&& suminsuredlist.size() > 0 )
						out.print("</fieldset>");
					}//end of if(!((List)historyDoc.selectNodes("/memberpolicyhistory/member/suminsureddetails/breakup")).isEmpty())

					if(!((List)historyDoc.selectNodes("/memberpolicyhistory/carddetails")).isEmpty())
					{
						cardlist= (List)historyDoc.selectNodes("/memberpolicyhistory/carddetails");
						if(cardlist!=null&& cardlist.size() > 0 )
						{
							cardElement = (Element)cardlist.get(0);
							out.print("<fieldset><legend>Card Details</legend>");
							out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");

							out.print("<tr>");
							out.print("<td width=\"24%\" height=\"25\" class=\"formLabel\">");
							out.print("No. of Cards Printed:");
							out.print("</td>");
							out.print("<td width=\"33%\" class=\"textLabel\">");
							out.print(cardElement.valueOf("@noofcardsprinted"));
							out.print("</td>");
							out.print("<td width=\"23%\" class=\"formLabel\">");
							out.print("Card Printed Date:");
							out.print("</td>");
							out.print("<td width=\"20%\" class=\"textLabel\">");
							out.print(cardElement.valueOf("@cardprintdate"));
							out.print("</td>");
							out.print("</tr>");

							out.print("<tr>");
							out.print("<td width=\"24%\" height=\"25\" class=\"formLabel\">");
							out.print("Cards Batch NO.:");
							out.print("</td>");
							out.print("<td width=\"33%\" class=\"textLabel\">");
							out.print(cardElement.valueOf("@cardbatchno"));
							out.print("</td>");
							out.print("<td width=\"23%\" class=\"formLabel\">");
							out.print("Courier No.:");
							out.print("</td>");
							out.print("<td width=\"20%\" class=\"textLabel\">");
							out.print(cardElement.valueOf("@courierno"));
							out.print("</td>");
							out.print("</tr>");

							out.print("<tr>");
							out.print("<td width=\"24%\" height=\"25\" class=\"formLabel\">");
							out.print("Courier Date:");
							out.print("</td>");
							out.print("<td width=\"33%\" class=\"textLabel\">");
							out.print(cardElement.valueOf("@courierdate"));
							out.print("</td>");
							out.print("</tr>");

							out.print("</table>");
							out.print("</fieldset>");
						}
					}
				}//end of if(!((List)doc.selectNodes("/memberpolicyhistory")).isEmpty())

				if(!((List)historyDoc.selectNodes("/memberpolicyhistory/peddetails/ped")).isEmpty())
				{
					pedlist = (List)historyDoc.selectNodes("/memberpolicyhistory/peddetails/ped");
					out.print("<fieldset><legend>PED Details</legend>");
					if(pedlist!=null&& pedlist.size() > 0 )
					{
						out.print("<table align=\"center\" class=\"gridWithCheckBox zeroMargin\"  border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
						out.print("<tr>");
						out.print("<td width=\"26%\" valign=\"top\" class=\"gridHeader\">Descripion</td>");
						out.print("<td width=\"11%\" valign=\"top\" class=\"gridHeader\">ICD Code</td>");
						out.print("<td width=\"22%\" valign=\"top\" class=\"gridHeader\">Duration of Illness</td>");
						out.print("<td width=\"21%\" valign=\"top\" class=\"gridHeader\">Remarks</td>");
						out.print("</tr>");

						for(int ipedcount=0;ipedcount<pedlist.size();ipedcount++)
						{
							pedElement = (Element)pedlist.get(ipedcount);
							strClass=ipedcount%2==0 ? "gridOddRow" : "gridEvenRow" ;

							out.print("<tr class=\""+strClass+"\">");
							out.print("<td>");
							out.print(pedElement.valueOf("@peddescription")+"&nbsp;");
							out.print("</td>");
							out.print("<td>");
							out.print(pedElement.valueOf("@icdcode")+"&nbsp;");
							out.print("</td>");
							out.print("<td>");
							out.print(pedElement.valueOf("@duration")+"&nbsp;");
							out.print("</td>");
							out.print("<td>");
							out.print(pedElement.valueOf("@remarks")+"&nbsp;");
							out.print("</td>");
							out.print("</tr>");

						}//end of for(int ipedcount=0;ipedcount<pedlist.size();ipedcount++)
						out.print("</table>");
					}//end of if(pedlist!=null&& pedlist.size() > 0 )
					out.print("</fieldset>");
				}//end of  if(!((List)historyDoc.selectNodes("/memberpolicyhistory/peddetails")).isEmpty())
			}//end of if(doc != null)
		}//end of try block
		catch(Exception exp)
		{
			exp.printStackTrace();
			log.debug("error occured in PolicyDetailHistory Tag Library!!!!! ");
		}//end of catch block
		return SKIP_BODY;
	}//end of doStartTag()
	public int doEndTag() throws JspException {
		return EVAL_PAGE;//to process the rest of the page
	}//end doEndTag()
}//end of class PolicyDetailHistory