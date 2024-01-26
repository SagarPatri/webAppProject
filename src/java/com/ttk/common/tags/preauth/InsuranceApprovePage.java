/**
1274 KOC
 * @ (#) InsuranceApprovePage.java
 *  May 26, 2006
 * Project : TTK HealthCare Services
 * File : ClaimantHistory.java
 * Author : Srikanth H M
 * Company : Span Systems Corporation
 * Date Created : May 26, 2006
 *
 * @author : Srikanth H M
 * Modified by :
 * Modified date :
 * Reason :
 */

package com.ttk.common.tags.preauth;

import java.io.File;
import java.util.List;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
//import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;

import com.ttk.dto.common.Toolbar;


/**
 *  This class builds the Claimant History in the Cashless History
 *  
 */

public class InsuranceApprovePage extends TagSupport{

	/**
	 * Comment for <code>serialVersionUID</code>
	 */
	private static final long serialVersionUID = 1L;

	//    private static Logger log = Logger.getLogger( InsuranceApprovePage.class );
	public int doStartTag() throws JspException{
		try
		{

			
			 Toolbar toolBar = null;
		       toolBar = (Toolbar)pageContext.getSession().getAttribute("toolbar");
		       String strURL=toolBar.getDocUrl();
		       String strQueryString=toolBar.getQueryString();
		       StringBuffer strQuery= new StringBuffer();
				strQuery = strQuery.append(strURL).append(strQueryString);
		      
			Document historyDoc=null;
			JspWriter out = pageContext.getOut();//Writer object to write the file
			historyDoc=(Document)pageContext.getSession().getAttribute("ClaimXmlDocument");
			pageContext.getSession().setAttribute("strQuery",strQuery.toString());
			
			Element claimElement=null;List  claimList=null;
			Element insuranceElement=null;List  insuranceList=null;
			Element policyDetailsElement=null;List  policyDetailsList=null;
			Element policyHistoryElement=null;List policyHistorysList=null;
			Element claimentElement=null;List  claimentlist=null;
			Element claimHistoryElement=null;List  claimHistorylist=null;
			Element hospDetailsElement=null;List hospitalizationDetailsList=null;
			Element shrtFallDetailsElement=null;List shrtFallDetailsList=null;
			Element investgationElement=null;List investgationList=null;
			Element billElement=null;List billElementList=null;
			Element billHeaderElement=null;List billHeaderList=null;

			String strClaimStatus="";
			String strClaimStatusCode="";

			String insremarks="";

			if(historyDoc != null)
			{
				
				if(!((List)historyDoc.selectNodes("//CLAIMDETAILS//CLAIM_DATA")).isEmpty())
				{
					claimList = (List)historyDoc.selectNodes("//CLAIMDETAILS//CLAIM_DATA");
					if(claimList!=null&& claimList.size() > 0 )
					{
						claimElement=(Element)claimList.get(0);
						out.print("<fieldset><legend>General Details</legend>");
						out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
						out.print("<tr>");
						out.print("<td  height=\"25\" class=\"formLabel\">Claim No.:</td>");
						out.print("<td class=\"textLabel\">");out.print(claimElement.valueOf("@claimnumber"));out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" >Cashless No.:</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");out.print(claimElement.valueOf("@preauthnumber"));out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">TTK/Vidal Health Id :</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");out.print(claimElement.valueOf("@vidalhealthttkid"));out.print("</td>");
						out.print("</tr>");
						out.print("</table>");
						out.print("</fieldset>");
						//end of first field set

						out.print("<fieldset><legend>Claim Details</legend>");
						out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");

						out.print("<tr>");
						out.print("<td width=\"22%\"  height=\"25\" class=\"formLabel\">Claim Type:</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");	out.print(claimElement.valueOf("@claimtype"));out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\">Claim Category :</td>");
						out.print("<td class=\"textLabel\">");out.print(claimElement.valueOf("@claimcategory"));out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td width=\"22%\"  height=\"25\" class=\"formLabel\">Recieved On :</td>");
						out.print("<td  width=\"25%\" class=\"textLabel\">");out.print(claimElement.valueOf("@receivedon"));out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">Recommended On:</td>");
						out.print("<td class=\"textLabel\" >");out.print(claimElement.valueOf("@recommededon"));out.print("</td>");
						out.print("</tr>");

						out.print("</tr>");
						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Requested/Claimed Amount :</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");out.print(claimElement.valueOf("@req_claim_amt"));	out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">Recommended Amount:</td>");
						out.print("<td class=\"textLabel\">");out.print(claimElement.valueOf("@recommended"));out.print("</td>");
						out.print("</tr>");       
						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Non Admissable Amount :</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");out.print(claimElement.valueOf("@nonadmissable"));out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">Copay Amount:</td>");
						out.print("<td class=\"textLabel\" >");out.print(claimElement.valueOf("@copay"));out.print("</td>");
						out.print("</tr>");    
						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Voluntry Copay Amount :</td>");
						out.print("<td  width=\"25%\" class=\"textLabel\">");	out.print(claimElement.valueOf("@voluntary_copay"));out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">Payable Amount:</td>");
						out.print("<td class=\"textLabel\" >");out.print(claimElement.valueOf("@payable"));out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Non Payable Amount :</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");out.print(claimElement.valueOf("@notpayable"));	out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">TDS Amount:</td>");
						out.print("<td class=\"textLabel\" >");out.print(claimElement.valueOf("@tds"));out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("</table>");

						out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
						out.print("<tr>");
						out.print("<td width=\"22%\"  height=\"25\" class=\"formLabel\">Deduction Reason :</td>");
						out.print("<td width=\"78%\"  class=\"textLabel\">");
						out.print("<textarea name=\"deductionReason\" class=\"textBox textAreaLongHt\" readonly=\"true\">");
						out.print(claimElement.valueOf("@deduction_reason"));
						out.print("</textarea></td>");
						out.println("<td>&nbsp;</td>");
						out.println("<td>&nbsp;</td>");
						
						out.print("</tr>");
						out.print("</table>");
						out.print("</fieldset>");
					}
				}// end of  if(!((List)historyDoc.selectNodes("//CLAIMDETAILS")).isEmpty())

				if(!((List)historyDoc.selectNodes("//INSURANCEDETAILS//INSCOMP")).isEmpty())
				{
					insuranceList=(List)historyDoc.selectNodes("//INSURANCEDETAILS//INSCOMP");
					if(insuranceList!=null || insuranceList.size()>0)
					{
						insuranceElement=(Element)insuranceList.get(0);
						out.print("<fieldset><legend>Insurance Company Details</legend>");
						out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Insurance Company:</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");
						out.print(insuranceElement.valueOf("@insurancecompany"));
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
						out.print("Controlling Office:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(insuranceElement.valueOf("@controllingoffice"));
						out.print("</td>");
						out.print("</tr>");


						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">City/Town/village:</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");
						out.print(insuranceElement.valueOf("@city"));
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
						out.print("State:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(insuranceElement.valueOf("@state"));
						out.print("</td>");
						out.print("</tr>");

						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Pincode:</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");
						out.print(insuranceElement.valueOf("@pincode"));
						out.print("</td>");
						out.print("</tr>");
						out.print("</table>");
						out.print("</fieldset>");
					}
				}
				//end of Insurance Details


				if(!((List)historyDoc.selectNodes("//POLICYDETAILS//POLICYDATA")).isEmpty())
				{
					policyDetailsList=(List)historyDoc.selectNodes("//POLICYDETAILS//POLICYDATA");
					if(policyDetailsList!=null || policyDetailsList.size()>0)
					{
						policyDetailsElement=(Element)policyDetailsList.get(0);

						String suminsured=policyDetailsElement.valueOf("@suminsured").equals("")?"0.00":policyDetailsElement.valueOf("@suminsured");
						String cummbonus=policyDetailsElement.valueOf("@cummbonus").equals("")?"0.00":policyDetailsElement.valueOf("@cummbonus");
						String  corbuffer=policyDetailsElement.valueOf("@corbuffer").equals("")?"0.00":policyDetailsElement.valueOf("@corbuffer");
						//added for hyundai buffer
						String  medbuffer=policyDetailsElement.valueOf("@medbuffer").equals("")?"0.00":policyDetailsElement.valueOf("@medbuffer");
						String  critbuffer=policyDetailsElement.valueOf("@critbuffer").equals("")?"0.00":policyDetailsElement.valueOf("@critbuffer");
						String  critcorbuffer=policyDetailsElement.valueOf("@critcorbuffer").equals("")?"0.00":policyDetailsElement.valueOf("@critcorbuffer");
						String  critmedbuffer=policyDetailsElement.valueOf("@critmedbuffer").equals("")?"0.00":policyDetailsElement.valueOf("@critmedbuffer");
						//end added for hyundai buffer
						String premzone=policyDetailsElement.valueOf("@premzone").equals("")?"":policyDetailsElement.valueOf("@premzone");
						String payblefrom=policyDetailsElement.valueOf("@payblefrom").equals("")?"":policyDetailsElement.valueOf("@payblefrom");

						out.print("<fieldset><legend>Scheme Details</legend>");
						out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");

						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Primary Beneficiary Name:</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");
						out.print(policyDetailsElement.valueOf("@prm_insured"));
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
						out.print("Customer Id:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(policyDetailsElement.valueOf("@customerid"));
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Scheme No.:</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");
						out.print(policyDetailsElement.valueOf("@policynumber"));
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
						out.print("Scheme Type:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(policyDetailsElement.valueOf("@policytype"));
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Scheme Term From:</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");
						out.print(policyDetailsElement.valueOf("@policystart"));
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
						out.print("Scheme Term To:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(policyDetailsElement.valueOf("@policyend"));
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Product Name:</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");
						out.print(policyDetailsElement.valueOf("@productname"));
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
						out.print("Agent Code:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(policyDetailsElement.valueOf("@agentcode"));
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Dev Officer Code:</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");
						out.print(policyDetailsElement.valueOf("@devoffcode"));
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
						out.print("Operating Officer Code:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(policyDetailsElement.valueOf("@operoffcode"));
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Operating Office:</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");
						out.print(policyDetailsElement.valueOf("@operoffice"));
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
						out.print("Sum Insured:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(suminsured);
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Cumulative Bonus/Buffer :</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");
						out.print(cummbonus);
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
						out.print("Normal Corpus Buffer:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(corbuffer);
						out.print("</td>");
						out.print("</tr>");
						//added for hyundai buffer
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
						out.print("Normal Medical Buffer:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(medbuffer);
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
						out.print("Critical Corpus Buffer:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(critcorbuffer);
						out.print("</td>");
						out.print("</tr>");
						out.print("<tr>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
						out.print("Critical Medical Buffer:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(critmedbuffer);
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
						out.print("Critical Illness Buffer:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(critbuffer);
						out.print("</td>");
						
						out.print("</tr>");
						// end added for hyundai buffer
						out.print("<tr>");
						out.print("<td width=\"22%\" height=\"25\" class=\"formLabel\">Premium Zone :</td>");
						out.print("<td width=\"25%\" class=\"textLabel\">");
						out.print(premzone);
						out.print("</td>");
						out.print("<td height=\"25\" class=\"formLabel\" width=\"22%\">");
						out.print("Payable From:");
						out.print("</td>");
						out.print("<td class=\"textLabel\" width=\"31%\">");
						out.print(payblefrom);
						out.print("</td>");
						out.print("</tr>");
						out.print("</table>");
						out.print("</fieldset>");
					}


				}
				//End of Policy Details

				//Start Of Policy History
				if(	!((List)historyDoc.selectNodes("//POLICYHISTORY//POLHISTORY")).isEmpty())
				{
					policyHistorysList=(List)historyDoc.selectNodes("//POLICYHISTORY//POLHISTORY");
					if(policyHistorysList!=null ||  policyHistorysList.size()>0)
					{
						out.print("<fieldset><legend>Scheme History</legend>");
						out.print("<table align=\"center\" class=\"formContainer\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">");
						out.print("<tr>");
						out.print("<td width=\"5%\" height=\"25\" class=\"formLabel\">SI No</td>");
						out.print("<td width=\"20%\" height=\"25\" class=\"formLabel\">Scheme No. </td>");
						out.print("<td width=\"15%\" height=\"25\" class=\"formLabel\">Scheme Type </td>");
						out.print("<td width=\"20%\" height=\"25\" class=\"formLabel\">Product Name:</td>");
						out.print("<td width=\"10%\" height=\"25\" class=\"formLabel\">From</td>");
						out.print("<td width=\"10%\" height=\"25\" class=\"formLabel\">To </td>");
						out.print("<td width=\"20%\" height=\"25\" class=\"formLabel\">Sum Insured </td>");

						out.print("</tr>");
						for(int elePolicy=0;elePolicy<policyHistorysList.size();elePolicy++)
						{
							policyHistoryElement=(Element)policyHistorysList.get(elePolicy);
							if(policyHistoryElement!=null)
							{
								out.print("<tr>");
								out.print("<td width=\"5%\" class=\"textLabel\">");out.print(elePolicy+1);out.print("</td>");
								out.print("<td width=\"20%\" class=\"textLabel\">");out.print(policyHistoryElement.valueOf("@policynumber"));out.print("</td>");
								out.print("<td width=\"15%\" class=\"textLabel\">");out.print(policyHistoryElement.valueOf("@policytype"));out.print("</td>");
								out.print("<td width=\"20%\" class=\"textLabel\">");out.print(policyHistoryElement.valueOf("@productname"));out.print("</td>");
								out.print("<td width=\"10%\" class=\"textLabel\">");out.print(policyHistoryElement.valueOf("@from"));out.print("</td>");
								out.print("<td width=\"10%\" class=\"textLabel\">");out.print(policyHistoryElement.valueOf("@to"));out.print("</td>");
								out.print("<td width=\"20%\" class=\"textLabel\">");out.print(policyHistoryElement.valueOf("@suminsured"));out.print("</td>");
								out.print("</tr>");
							}
						}//end  of for(int elePolicy=0;elePolicy<policyHistorysList.size();elePolicy++)
						out.print("</table>");
						out.print("</fieldset>");
					}// end of  if(policyHistorysList!=null ||  policyHistorysList.size()>0)

				}//end  of 	if(	!((List)historyDoc.selectNodes("//POLICYHISTORY//POLICY")).isEmpty())

				//end of Policy History    		
				if(!((List)historyDoc.selectNodes("//CLAIMANTDETAILS//CLAIMANT")).isEmpty())
				{
				claimentlist = (List)historyDoc.selectNodes("//CLAIMANTDETAILS//CLAIMANT");
				if(claimentlist!=null&& claimentlist.size() > 0 )
				{
					claimentElement = (Element)claimentlist.get(0);
					String address=claimentElement.valueOf("@address").equals("")?"":claimentElement.valueOf("@address");
					out.print("<fieldset><legend>Member Details</legend>");
					out.print("<table align=\"center\" class=\"formContainer\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">");
					out.print("<tr>");
					out.print("<td height=\"25\" class=\"formLabel\">Member Name:</td>");
					out.print("<td class=\"textLabel\">");	out.print(claimentElement.valueOf("@claimantname"));	out.print("</td>");
					out.print("<td height=\"25\" class=\"formLabel\">Age :</td>");out.print("<td  class=\"textLabel\">");	out.print(claimentElement.valueOf("@age"));out.print("</td>");
					out.print("<td height=\"25\" class=\"formLabel\">Sex :</td>");out.print("<td  class=\"textLabel\">");out.print(claimentElement.valueOf("@sex"));out.print("</td>");
					out.print("<td height=\"25\" class=\"formLabel\">Relation :</td>");	out.print("<td class=\"textLabel\">");out.print(claimentElement.valueOf("@relation"));	out.print("</td>");
					out.print("</tr>");
					out.print("<tr>");
					out.print("<td colspan=\"1\" height=\"25\" class=\"formLabel\">Address :</td>");out.print("<td colspan=\"7\" class=\"textLabel\">");out.print(address);out.print("</td>");
					out.print("</tr>");
					out.print("</table>");
					out.print("</fieldset>");
				}//end of if(claimentlist!=null&& claimentlist.size() > 0 )
				}//end of if(!((List)historyDoc.selectNodes("//claimantdetails")).isEmpty())
				//End of Claiment details
				//Start Of Policy History
				if(	!((List)historyDoc.selectNodes("//CLAIMHISTORY//CLMHISTORY")).isEmpty())
				{
					claimHistorylist=(List)historyDoc.selectNodes("//CLAIMHISTORY//CLMHISTORY");
					if(claimHistorylist!=null ||  claimHistorylist.size()>0)
					{
						out.print("<fieldset><legend>Claim History</legend>");
						out.print("<table align=\"center\" class=\"formContainer\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">");
						out.print("<tr>");
						out.print("<td  height=\"25\" class=\"formLabel\">SI No</td>");
						out.print("<td height=\"25\" class=\"formLabel\">Claim Number </td>");
						out.print("<td height=\"25\" class=\"formLabel\">Date of Admission </td>");
						out.print("<td height=\"25\" class=\"formLabel\">Date Of Discharge</td>");
						out.print("<td height=\"25\" class=\"formLabel\">Provider Name</td>");
						out.print("<td height=\"25\" class=\"formLabel\">Cashless No. </td>");
						out.print("<td height=\"25\" class=\"formLabel\">Claimed(Rs.) </td>");
						out.print("<td height=\"25\" class=\"formLabel\">Approved(Rs.) </td>");
						out.print("<td height=\"25\" class=\"formLabel\">Status </td>");
						out.print("</tr>");
						for(int eleClaim=0;eleClaim<claimHistorylist.size();eleClaim++)
						{
							claimHistoryElement=(Element)claimHistorylist.get(eleClaim);
							if(eleClaim==0)
							{
								strClaimStatus=claimHistoryElement.valueOf("@status");
							}
							if(claimHistoryElement!=null)
							{
								out.print("<tr>");
								out.print("<td rowspan=\"2\" class=\"textLabel\">");out.print(eleClaim+1);out.print("</td>");
								out.print("<td class=\"textLabel\">");out.print(claimHistoryElement.valueOf("@claimnumber"));out.print("</td>");
								out.print("<td class=\"textLabel\">");out.print(claimHistoryElement.valueOf("@doa"));out.print("</td>");
								out.print("<td class=\"textLabel\">");out.print(claimHistoryElement.valueOf("@dod"));out.print("</td>");
								out.print("<td class=\"textLabel\">");out.print(claimHistoryElement.valueOf("@hospname"));out.print("</td>");
								out.print("<td class=\"textLabel\">");out.print(claimHistoryElement.valueOf("@prenumber"));out.print("</td>");
								out.print("<td class=\"textLabel\">");out.print(claimHistoryElement.valueOf("@claimed"));out.print("</td>");
								out.print("<td class=\"textLabel\">");out.print(claimHistoryElement.valueOf("@approved"));out.print("</td>");
								out.print("<td class=\"textLabel\">");out.print(claimHistoryElement.valueOf("@status"));out.print("</td>");
								out.print("</tr>");
								out.print("<tr>");
								if(!claimHistoryElement.valueOf("@ailment").equals(""))
								{
									out.print("<td height=\"25\" class=\"formLabel\">Ailment</td>");
									out.print("<td  colspan=\"2\" class=\"textLabel\">");out.print(claimHistoryElement.valueOf("@ailment"));out.print("</td>");
								}
								if(!claimHistoryElement.valueOf("@rejreason").equals(""))
								{
									out.print("<td height=\"25\" class=\"formLabel\">RejectionReason</td>");
									out.print("<td  colspan=\"4\" class=\"textLabel\">");out.print(claimHistoryElement.valueOf("@rejreason"));out.print("</td>");
								}
								out.print("</tr>");
							}
						}//end  of for(int eleClaim=0;eleClaim<claimHistorylist.size();eleClaim++)
								out.print("</table>");
								out.print("</fieldset>");
					}// end of  if(claimHistorylist!=null ||  claimHistorylist.size()>0)
				}//end  of 	if(	!((List)historyDoc.selectNodes("//CLAIMHISTORY//CLAIM")).isEmpty())


				//HospitalizationDetails

				if(	!((List)historyDoc.selectNodes("//HOSPITLIZATIONDETAILS//HOSPDET")).isEmpty())
				{
				    hospitalizationDetailsList=(List)historyDoc.selectNodes("//HOSPITLIZATIONDETAILS//HOSPDET");
				   if(hospitalizationDetailsList!=null ||  hospitalizationDetailsList.size()>0)
				  {
					hospDetailsElement=(Element)hospitalizationDetailsList.get(0);

					out.print("<fieldset><legend>Hospitalization Details</legend>");
					out.print("<table align=\"center\" class=\"formContainer\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">");
					out.println("<tr>");
					out.print("<td width=\"15%\" height=\"25\" class=\"formLabel\">Intimation Date :</td>");
					out.print("<td class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@intdate"));out.print("</td>");
					out.print("<td class=\"formLabel\">Date Of Diagnosis :</td>");
					out.print("<td class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@datediag"));out.print("</td>");
					out.print("<td class=\"formLabel\">Date Of Admission :</td>");
					out.print("<td class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@admssion"));out.print("</td>");
					out.print("<td class=\"formLabel\">Date Of Discharge :</td>");
					out.print("<td class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@discharge"));out.print("</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.print("<td width=\"15%\" height=\"25\" class=\"formLabel\">Provider Name :</td>");
					out.print("<td colspan=\"7\" class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@hospname"));out.print("</td>");
					out.println("</tr>");

					out.println("<tr>");
					out.print("<td width=\"15%\" height=\"25\" class=\"formLabel\">Provider Code :</td>");
					out.print("<td class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@hospcode"));out.print("</td>");
					out.print("<td class=\"formLabel\">City/Town/Village:</td>");
					out.print("<td class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@city"));out.print("</td>");
					out.print("<td class=\"formLabel\">State :</td>");
					out.print("<td class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@state"));out.print("</td>");
					out.print("<td class=\"formLabel\">Pincode :</td>");
					out.print("<td class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@pincode"));out.print("</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.print("<td width=\"15%\" height=\"25\" class=\"formLabel\">Diagnosis  :</td>");
					out.print("<td colspan=\"7\" class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@diagnosis"));out.print("</td>");
					out.println("</tr>"); 

					out.println("<tr>");
					out.print("<td width=\"15%\" height=\"25\" class=\"formLabel\">Diagnosis Code Level1 :</td>");
					out.print("<td class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@diagclvl1"));out.print("</td>");
					out.print("<td class=\"formLabel\">Diagnosis Code Level2:</td>");
					out.print("<td class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@diagclvl2"));out.print("</td>");
					out.print("<td class=\"formLabel\">Procedure Code Level 1 :</td>");
					out.print("<td class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@procclvl1"));out.print("</td>");
					out.print("<td class=\"formLabel\">Procedure Code Level 2 :</td>");
					out.print("<td class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@procclvl2"));out.print("</td>");
					out.println("</tr>");


					out.println("<tr>");
					out.print("<td width=\"15%\" height=\"25\" class=\"formLabel\">Diagnosis Description Level 1 :</td>");
					out.print("<td colspan=\"7\" class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@diagdlvl1"));out.print("</td>");
					out.println("</tr>");

					out.println("<tr>");
					out.print("<td width=\"15%\" height=\"25\" class=\"formLabel\">Diagnosis Description Level 2 :</td>");
					out.print("<td colspan=\"7\" class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@diagdlvl2"));out.print("</td>");
					out.println("</tr>");

					out.println("<tr>");
					out.print("<td width=\"15%\" height=\"25\" class=\"formLabel\">Procedure Description Level 1:</td>");
					out.print("<td colspan=\"7\" class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@procdlvl1"));out.print("</td>");
					out.println("</tr>");

					out.println("<tr>");
					out.print("<td width=\"15%\" height=\"25\" class=\"formLabel\">Procedure Description Level 2:</td>");
					out.print("<td colspan=\"7\" class=\"textLabel\">");out.print(hospDetailsElement.valueOf("@procdlvl2"));out.print("</td>");
					out.println("</tr>");


					out.print("</table>");
					out.print("</fieldset>");
				 }// end of if(hospitalizationDetailsList!=null ||  hospitalizationDetailsList.size()>0)


			    }//end of if(	!((List)historyDoc.selectNodes("HOSPITLIZATIONDETAILS")).isEmpty())
				if(	!((List)historyDoc.selectNodes("//BILLINGDETAILS//BILLHEADER")).isEmpty())
				{
					billHeaderList=(List)historyDoc.selectNodes("//BILLINGDETAILS//BILLHEADER");
					if(billHeaderList!=null ||  billHeaderList.size()>0)
				  {


					out.print("<fieldset><legend>Bill Details</legend>");
					out.print("<table align=\"center\" class=\"formContainer\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">");
					out.print("<tr>");
					out.print("<td  height=\"25\" class=\"formLabel\">SI No</td>");
					out.print("<td height=\"25\" class=\"formLabel\">Bill No </td>");
					out.print("<td height=\"25\" class=\"formLabel\">Bill Date </td>");
					out.print("<td height=\"25\" class=\"formLabel\">Nature of Expenditure </td>");
					out.print("<td height=\"25\" class=\"formLabel\">Amt Claimed (Rs)</td>");
					out.print("<td height=\"25\" class=\"formLabel\">Amt Settled(Rs)</td>");
					out.print("<td height=\"25\" class=\"formLabel\">Amt Disallowed(Rs)</td>");//fixed
					out.print("<td height=\"25\" class=\"formLabel\">Remarks </td>");

					out.print("</tr>");
					double requestedAmount=0;
					double allowamt=0;
					double rejamt=0;
					for(int billHeader=0;billHeader<billHeaderList.size();billHeader++)
					{
						billHeaderElement=(Element)billHeaderList.get(billHeader);
						StringBuffer sb=new StringBuffer();
						billElementList =(List)billHeaderElement.selectNodes(".//BILLS");
						out.print("<tr>");
						out.print("<td  rowspan=\""+billElementList.size()+"\" class=\"textLabel\">");out.print(billHeader+1);out.print("</td>");
						out.print("<td  rowspan=\""+billElementList.size()+"\" class=\"textLabel\">");out.print(billHeaderElement.valueOf("@billno"));out.print("</td>");
						//out.print("<td  rowspan=\""+billElementList.size()+"\" class=\"textLabel\">");out.print(billHeaderElement.valueOf("@billno"));out.print("</td>");

						for(int eleBill=0;eleBill<billElementList.size();eleBill++)
						{
							billElement=(Element)billElementList.get(eleBill);


							out.print("<td nowrap class=\"textLabel\">");out.print(billHeaderElement.valueOf("@billdate"));out.print("</td>");
							out.print("<td nowrap class=\"textLabel\">");out.print(billElement.valueOf("@acchead"));out.print("</td>");
							out.print("<td nowrap class=\"textLabel\">");out.print(billElement.valueOf("@reqamt"));out.print("</td>");
							String allowedAmt=(billElement.valueOf("@allowamt").equalsIgnoreCase(""))? "0.00" : billElement.valueOf("@allowamt");
							out.print("<td nowrap class=\"textLabel\">");out.print(allowedAmt);out.print("</td>");
							out.print("<td nowrap class=\"textLabel\">");out.print(billElement.valueOf("@rejamt"));out.print("</td>");
							out.print("<td nowrap width=\"20%\" class=\"textLabel\">");out.print(billElement.valueOf("@remarks"));out.print("</td>");
							out.print("</tr>");
							out.print("<tr>");
							requestedAmount=requestedAmount+Double.parseDouble(billElement.valueOf("@reqamt").equalsIgnoreCase("")?"0":billElement.valueOf("@reqamt"));
							allowamt=allowamt+Double.parseDouble(billElement.valueOf("@allowamt").equalsIgnoreCase("")?"0":billElement.valueOf("@allowamt"));
							rejamt=rejamt+Double.parseDouble(billElement.valueOf("@rejamt").equalsIgnoreCase("")?"0":billElement.valueOf("@rejamt"));
						}
							out.print("</tr>");
					}

							out.print("</table>");
							out.print("</fieldset>");

							out.print("<fieldset>");
							out.print("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
							out.print("<tr>");
							out.print("<td class=\"textLabel\">");out.print("Total Requested Amount : ");out.print(requestedAmount);out.print("</td>");
							out.print("<td class=\"textLabel\">");out.print("Total Allowed Amount : ");out.print(allowamt);out.print("</td>");
							out.print("<td class=\"textLabel\">");out.print("Total Rejected : ");out.print(rejamt);out.print("</td>");
							out.print("</tr>");
							out.print("</table>");
							out.print("</fieldset>");



				  }
				}	

				if(	!((List)historyDoc.selectNodes("//SHORTFALLDETAILS")).isEmpty())
				{
				   shrtFallDetailsList=(List)historyDoc.selectNodes("//SHORTFALLDETAILS");
				  if(shrtFallDetailsList!=null ||  shrtFallDetailsList.size()>0)
				  {
					shrtFallDetailsElement=(Element)shrtFallDetailsList.get(0);
					Element insuredElement = (Element)shrtFallDetailsElement.selectSingleNode("//SHORTFALLDETAILS//INSURED");
					Element hospitalElement = (Element)shrtFallDetailsElement.selectSingleNode("//SHORTFALLDETAILS//HOSPITAL");
					Element insurerElement = (Element)shrtFallDetailsElement.selectSingleNode("//SHORTFALLDETAILS//INSURER");
					//Element insuredElement = (Element)shrtFallDetailsElement.selectSingleNode("//");

					out.print("<fieldset><legend>Shortfall Details</legend>");
					out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
					out.println("<tr>");
					out.print("<td width=\"15%\" height=\"25\" class=\"formLabel\">Shortfall Description :</td>");
					out.print("<td colspan=\"7\" class=\"textLabel\">");out.print(shrtFallDetailsElement.valueOf("@description"));out.print("</td>");
					out.println("</tr>");
					out.print("</table>");

					out.print("<table align=\"center\" class=\"formContainer\" border=\"1\" cellspacing=\"0\" cellpadding=\"0\">");


					out.println("<tr>");
					out.print("<td width=\"25%\" height=\"25\" class=\"formLabel\">Beneficiary Shortfall Date</td>");
					out.print("<td width=\"25%\" height=\"25\" class=\"formLabel\">Beneficiary Reminder Date</td>");
					out.print("<td width=\"25%\" height=\"25\" class=\"formLabel\">Beneficiary Final Reminder Date</td>");
					out.print("<td width=\"25%\" height=\"25\" class=\"formLabel\">Beneficiary Reply Recieved Date</td>");
					out.println("</tr>");

					String insuredShortfalldate="";
					String insuredReminderdate="";
					String insuredFinalremdate="";
					String insuredReplyrecdate="";

					String hospShortfalldate="";
					String hospReminderdate="";
					String hospFinalremdate="";
					String hospReplyrecdate="";

					String insurerShortfalldate="";
					String insurerReminderdate="";
					String insurerFinalremdate="";
					String insurerReplyrecdate="";

					insuredShortfalldate=(insuredElement.valueOf("@shortfalldate").equalsIgnoreCase(""))?"NA":insuredElement.valueOf("@shortfalldate");
					insuredReminderdate=(insuredElement.valueOf("@reminderdate").equalsIgnoreCase(""))?"NA":insuredElement.valueOf("@reminderdate");
					insuredFinalremdate=(insuredElement.valueOf("@finalremdate").equalsIgnoreCase(""))?"NA":insuredElement.valueOf("@finalremdate");
					insuredReplyrecdate=(insuredElement.valueOf("@replyrecdate").equalsIgnoreCase(""))?"NA":insuredElement.valueOf("@replyrecdate");

					hospShortfalldate=(hospitalElement.valueOf("@shortfalldate").equalsIgnoreCase(""))?"NA":hospitalElement.valueOf("@shortfalldate");
					hospReminderdate=(hospitalElement.valueOf("@reminderdate").equalsIgnoreCase(""))?"NA":hospitalElement.valueOf("@reminderdate");
					hospFinalremdate=(hospitalElement.valueOf("@finalremdate").equalsIgnoreCase(""))?"NA":hospitalElement.valueOf("@finalremdate");
					hospReplyrecdate=(hospitalElement.valueOf("@replyrecdate").equalsIgnoreCase(""))?"NA":hospitalElement.valueOf("@replyrecdate");

					insurerShortfalldate=(insurerElement.valueOf("@shortfalldate").equalsIgnoreCase(""))?"NA":insurerElement.valueOf("@shortfalldate");
					insurerReminderdate=(insurerElement.valueOf("@reminderdate").equalsIgnoreCase(""))?"NA":insurerElement.valueOf("@reminderdate");
					insurerFinalremdate=(insurerElement.valueOf("@finalremdate").equalsIgnoreCase(""))?"NA":insurerElement.valueOf("@finalremdate");
					insurerReplyrecdate=(insurerElement.valueOf("@replyrecdate").equalsIgnoreCase(""))?"NA":insurerElement.valueOf("@replyrecdate");
					out.println("<tr>");
					out.print("<td width=\"25%\" class=\"textLabel\">");out.print(insuredShortfalldate);out.print("</td>");
					out.print("<td width=\"25%\" class=\"textLabel\">");out.print(insuredReminderdate);out.print("</td>");
					out.print("<td width=\"25%\" class=\"textLabel\">");out.print(insuredFinalremdate);out.print("</td>");
					out.print("<td width=\"25%\" class=\"textLabel\">");out.print(insuredReplyrecdate);out.print("</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.print("<td width=\"25%\" height=\"25\" class=\"formLabel\">HospitalShortfall Date</td>");
					out.print("<td width=\"25%\" height=\"25\" class=\"formLabel\">HospitalReminder Date</td>");
					out.print("<td width=\"25%\" height=\"25\" class=\"formLabel\">HospitalFinal reminder Date</td>");
					out.print("<td width=\"25%\" height=\"25\" class=\"formLabel\">HospitalReply Recieved Date</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.print("<td width=\"25%\" class=\"textLabel\">");out.print(hospShortfalldate);out.print("</td>");
					out.print("<td width=\"25%\" class=\"textLabel\">");out.print(hospReminderdate);out.print("</td>");
					out.print("<td width=\"25%\" class=\"textLabel\">");out.print(hospFinalremdate);out.print("</td>");
					out.print("<td width=\"25%\" class=\"textLabel\">");out.print(hospReplyrecdate);out.print("</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.print("<td width=\"25%\" height=\"25\" class=\"formLabel\">InsurerShortfall Date</td>");
					out.print("<td width=\"25%\" height=\"25\" class=\"formLabel\">InsurerReminder Date</td>");
					out.print("<td width=\"25%\" height=\"25\" class=\"formLabel\">InsurerFinal reminder Date</td>");
					out.print("<td width=\"25%\" height=\"25\" class=\"formLabel\">InsurerReply Recieved Date</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.print("<td width=\"25%\" class=\"textLabel\">");out.print(insurerShortfalldate);out.print("</td>");
					out.print("<td width=\"25%\" class=\"textLabel\">");out.print(insurerReminderdate);out.print("</td>");
					out.print("<td width=\"25%\" class=\"textLabel\">");out.print(insurerFinalremdate);out.print("</td>");
					out.print("<td width=\"25%\" class=\"textLabel\">");out.print(insurerReplyrecdate);out.print("</td>");
					out.println("</tr>");
					out.print("</table>");
					out.print("</fieldset>");

				   }//end of if(shrtFallDetailsList!=null ||  shrtFallDetailsList.size()>0)
				}// end of if(	!((List)historyDoc.selectNodes("//SHORTFALLDETAILS")).isEmpty())

				if(	!((List)historyDoc.selectNodes("//INVESTIGATIONDETAILS//INVESTIGATION")).isEmpty())
				{
				   investgationList=(List)historyDoc.selectNodes("//INVESTIGATIONDETAILS//INVESTIGATION");
				   if(investgationList!=null ||  investgationList.size()>0)
				  {
					investgationElement=(Element)investgationList.get(0);
					out.print("<fieldset><legend>Investigation Details</legend>");
					out.print("<table align=\"center\" class=\"formContainer\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");

					String invname=(investgationElement.valueOf("@invname").equals(""))?"":investgationElement.valueOf("@invname");
					String invdate=(investgationElement.valueOf("@invdate").equals(""))?"":investgationElement.valueOf("@invdate");
					String recommendation=(investgationElement.valueOf("@recommendation").equals(""))?"":investgationElement.valueOf("@recommendation");
					String tparemarks=(investgationElement.valueOf("@tparemarks").equals(""))?"":investgationElement.valueOf("@tparemarks");
					insremarks=(investgationElement.valueOf("@insremarks").equals(""))?"":investgationElement.valueOf("@insremarks");
					
					out.println("<tr>");
					out.print("<td width=\"25%\" height=\"25\" class=\"formLabel\">Investigation Name</td>");
					out.print("<td width=\"25%\" class=\"textLabel\">");out.print(invname);out.print("</td>");
					out.print("<td width=\"25%\" height=\"25\" class=\"formLabel\">Investigation Date</td>");
					out.print("<td width=\"25%\" class=\"textLabel\">");out.print(invdate);out.print("</td>");
					out.println("</tr>");
					out.println("<tr>");
					out.print("<td colspan=\"1\" height=\"25\" class=\"formLabel\">Recommendation </td>");
					out.print("<td colspan=\"3\" class=\"textLabel\">");out.print(recommendation);out.print("</td>");
					out.println("</tr>");
					//added as per 1274 A 
					out.println("<tr>");
					out.print("<td colspan=\"1\" height=\"25\" class=\"formLabel\">Healthcare Administrator Recommendation</td>");
					out.print("<td colspan=\"3\" class=\"textLabel\">");
					out.print((investgationElement.valueOf("@tpa_recommendation").equals(""))?"":investgationElement.valueOf("@tpa_recommendation"));
					out.print("</td>");
					out.println("</tr>");
					//added as per 1274 A 
					out.println("<tr>");
					out.print("<td colspan=\"1\" height=\"25\" class=\"formLabel\">Healthcare Administrator Remarks</td>");
					out.print("<td colspan=\"3\" class=\"textLabel\">");out.print(tparemarks);out.print("</td>");
					out.println("</tr>");
		/*			out.print("<tr>");
					out.print("<td colspan=\"1\" height=\"25\" class=\"formLabel\">Insurer Remarks :</td>");
					out.print(" <td colspan=\"5\" class=\"textLabel\">");
					if(insremarks!=null)
					{
						
						out.print("<textarea name=\"insurerRemarks\" class=\"textBox textAreaLongHt\" maxlength=\"750\">");
						out.print(insremarks);
						out.print("</textarea>");
					}
					else{
					   out.print("<textarea name=\"insurerRemarks\" class=\"textBox textAreaLongHt\" maxlength=\"750\">");out.print("</textarea>");
					}
					out.print("</td>");
					out.print("</tr>");*/
					out.print("</table>");
					out.print("</fieldset>");
				  }// end of  if(investgationList!=null ||  investgationList.size()>0)
				}// end of if(	!((List)historyDoc.selectNodes("//INVESTIGATIONDETAILS")).isEmpty())

				String vidalhealthttkid=claimElement.valueOf("@vidalhealthttkid").equals("")?"":claimElement.valueOf("@vidalhealthttkid");
				String policynumber=policyHistoryElement.valueOf("@policynumber").equals("")?"":policyHistoryElement.valueOf("@policynumber");
				String  enrollNumber=claimElement.valueOf("@enr_number").equals("")?"":claimElement.valueOf("@enr_number");
				out.print("<input type=\"hidden\" name=\"vidalttkId\"  value=\""+vidalhealthttkid+"\"/>");out.print("</br>");
				out.print("<input type=\"hidden\" name=\"policyNo\"  value=\""+policynumber+"\"/>");out.print("<br>");
				//out.print("<input type=\"hidden\" name=\"generalSeqId\"  value=\""+claimElement.valueOf("@vidalhealthttkid")+"\"/>");
				out.print("<input type=\"hidden\" name=\"enrollNumber\"  value=\""+enrollNumber+"\"/>");out.print("<br>");
				
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
}//end of class InsuranceApprovePage 