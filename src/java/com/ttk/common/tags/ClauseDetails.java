/**
 * @ (#) ClauseDetails.java Jun 26, 2006
 * Project      : TTK HealthCare Services
 * File         : ClauseDetails.java
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Jun 26, 2006
 *
 * @author       :  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
//import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.RuleConfig;
//import com.ttk.common.tags.enrollment.PolicySearchBox;

/**
 * This Tag library is used to Display the Rules to be defined at Product/Policy level,
 * Family Level and Member Level
 *
 */
public class ClauseDetails extends TagSupport
{
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger( ClauseDetails.class );
	String strFlow="";

	private String strShowStyle="display:";
	private String strHideStyle="display:none;";
//	private String strReadOnly="readOnly";
	private String strDisabled="Disabled";
	private static String CONDITIONAL_PAY="3";
//	private static String PAY="1";
//	private static String  DO_NOT_PAY="2";

	/**
	 * setter for Flow attribute
	 * @param strFlow
	 */
	public void setFlow(String strFlow) {
		this.strFlow = strFlow;
	}//end of setFlow(String strFlow)

	/**
	 * This method will be executed when customised tag begins
	 * @return int
	 * @throws JspException
	 */
	public int doStartTag() throws JspException
	{
		try
		{
			log.debug("Inside ClauseDetails Tag library.............");
			JspWriter out = pageContext.getOut(); //  Writer object to write the  file
			Document ruleDocument=null;
			ruleDocument=(Document)pageContext.getSession().getAttribute("RuleDocument");
			displayRules(ruleDocument,out);
		}//end of try
		catch(Exception exp)
		{
			exp.printStackTrace();
			throw new JspException("Error: in ClauseDetails Tag Library!!!" );
		}//end of catch(Exception exp)
		return SKIP_BODY;
	}//end of doStartTag()

	/**
	 * This method will be executed before  tag closes
	 * @return int
	 * @throws JspException
	 */
	public int doEndTag() throws JspException
	{
		return EVAL_PAGE;//to process the rest of the page
	}//end doEndTag()

	/**
	 * This method is used to display the Rules to be defined at Product/Policy level,
	 * Family Level and Member Level
	 * @param ruleDocument Document XML DOM object containing the Rules to be defined
	 * @param out JspWriter object to write to the response
	 * @throws Exception if any Exception occures while defining the Rules
	 */
	private void displayRules(Document ruleDocument,JspWriter out) throws Exception
	{
		String strPermission="";
		if(!TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit")){
			strPermission=strDisabled;
		}//end of if(!TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Edit"))

		if(ruleDocument!=null)
		{
			//declaration of the variables
			List clauseList=null;
			List coverageList=null;
			Element eleClause=null;
			Element eleCoverage=null;
			clauseList=ruleDocument.selectNodes("/clauses/clause");

			//XPath identifiers for selecting the Rules to be defined at that level
			String strProdCvrgXpath="./coverage[contains(@module,'P')]";
			String strFamilyCvrgXpath="./coverage[contains(@module,'E')]";
			String strMemberCvrgXpath="./coverage[contains(@module,'M')]";

			String strCvrgXpath="";
			HashMap hmDisplayNodes=(HashMap)pageContext.getServletContext().getAttribute("RULE_DISPLAY_NODES");

			if(strFlow.equals("ProdPolicyRule")){
				strCvrgXpath=strProdCvrgXpath;
			}//end of if(strFlow.equals("ProdPolicyRule"))
			else if(strFlow.equals("FamilyRule")){
				strCvrgXpath=strFamilyCvrgXpath;
			}//end of else if(strFlow.equals("FamilyRule"))
			else if(strFlow.equals("MemberRule")) {
				strCvrgXpath=strMemberCvrgXpath;
			}//end of else if(strFlow.equals("MemberRule"))

			if(clauseList!=null && clauseList.size()>0)
			{
				for (int iClauseCnt=0;iClauseCnt<clauseList.size();iClauseCnt++)
				{
					eleClause=(Element)clauseList.get(iClauseCnt);
					//select coverages
					coverageList=eleClause.selectNodes(strCvrgXpath);
					if(coverageList!=null && coverageList.size()>0)
					{
						out.println("<fieldset>");
						out.println("<legend><img src=\"/ttk/images/c.gif\" alt=\"Expand\" ");
						out.println("name=\"clauseimg"+eleClause.valueOf("@id")+"\" width=\"16\" height=\"16\" align=\"top\" ");
						out.println("onClick=\"showhide('clausetab"+eleClause.valueOf("@id")+"','clauseimg"+eleClause.valueOf("@id")+"')\">&nbsp;");
						out.println(eleClause.valueOf("@name")+"</legend>");

						out.println("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" cellspacing=\"1\"");
						out.println("cellpadding=\"3\" style=\"display:none;\" id=\"clausetab"+eleClause.valueOf("@id")+"\">");
						for(int iCoverageCnt=0;iCoverageCnt<coverageList.size();iCoverageCnt++)
						{
							//display the coverage
							eleCoverage=(Element)coverageList.get(iCoverageCnt);
							out.println(displayCoverage(eleCoverage,hmDisplayNodes,strPermission));

							//display the Condtions For the Current Coverage
							out.println(displayConditions(eleCoverage,hmDisplayNodes,strPermission));
						}//end of for(int iCoverageCnt=0;iCoverageCnt<coverageList.size();iCoverageCnt++)
						out.println("</table>");
						out.println("</fieldset>");
					}//end of if(coverageList!=null && coverageList.size()>0)
				}//end of for (int iClauseCnt=0;iClauseCnt<clauseList.size();iClauseCnt++)
			}//end of if(clauseList!=null && clauseList.size()>0)
		}//end of if(clausesDoc!=null)
	}//end of displayRules(Document ruleDocument,JspWriter out)

	/**
	 * This method is used to display the given coverage node.
	 * If the coverage is having the display node under it then user is provided with the radio buttons
	 * to define the corresponding coverage
	 *
	 * @param eleCoverage  Element Coverage to be displayed
	 * @param hmDisplayNodes Hashmap of display nodes
	 * @param strPermission
	 * @return
	 * @throws TTKException if any run time exception occures
	 */
	private String displayCoverage(Element eleCoverage,HashMap hmDisplayNodes,
			String strPermission) throws TTKException
	{
		StringBuffer sbfDisplayCoverage=new StringBuffer();
		sbfDisplayCoverage.append("<tr style=\"background-color:#EBEDF2;\">");
		sbfDisplayCoverage.append("<td class=\"formLabelBold\" width=\"70%\">");
		sbfDisplayCoverage.append(eleCoverage.valueOf("@name"));

		if(strFlow.equals("ProdPolicyRule") && eleCoverage.selectSingleNode("./condition[@module='E']|./condition[@module='M']")!=null)
		{
			sbfDisplayCoverage.append("&nbsp; <img src=\"/ttk/images/ConfigureIcon.gif\" width=\"16\" height=\"16\" ");
			sbfDisplayCoverage.append(" alt=\"Some conditions will be defined at the Family or Member level\"" +
			" align=\"top\"/>");
		}//end of if

		else if(strFlow.equals("FamilyRule") && eleCoverage.selectSingleNode("./condition[@module='M']")!=null)
		{
			sbfDisplayCoverage.append("&nbsp; <img src=\"/ttk/images/ConfigureIcon.gif\" width=\"16\" height=\"16\" ");
			sbfDisplayCoverage.append("alt=\"Some conditions will be defined at the Member level\"" +
			" align=\"top\"/>");
		}//end of else if

		sbfDisplayCoverage.append("</td>");
		sbfDisplayCoverage.append("<td align=\"right\" width=\"30%\" ");
		sbfDisplayCoverage.append("class=\"rulesRadioOptions\" nowrap >");

		if(hmDisplayNodes.get(eleCoverage.valueOf("@id"))!=null)
		{
			Element eleDisplay= (Element)((ArrayList)hmDisplayNodes.get(eleCoverage.valueOf("@id"))).get(0);
			if(strFlow.equals("ProdPolicyRule"))
			{
				sbfDisplayCoverage.append(TTKCommon.buildDisplayElement(eleDisplay,eleCoverage.valueOf("@id"),
						eleCoverage.valueOf("@allowed"),strPermission,null));
			}//end of if(strFlow.equals("ProdPolicyRule"))

			else if(strFlow.equals("FamilyRule"))
			{
				if(eleCoverage.valueOf("@module").contains("P"))
				{
					sbfDisplayCoverage.append(TTKCommon.buildDisplayElement(eleDisplay,eleCoverage.valueOf("@id"),
							eleCoverage.valueOf("@allowed"),strDisabled,null));

					sbfDisplayCoverage.append("<input type=\"hidden\" name=\"").append(eleCoverage.valueOf("@id")).append("\" ");
					sbfDisplayCoverage.append(" value=\"").append(eleCoverage.valueOf("@allowed")).append("\" />");

				}//end of if(eleCoverage.valueOf("@module").contains("P"))
				else
				{
					sbfDisplayCoverage.append(TTKCommon.buildDisplayElement(eleDisplay,eleCoverage.valueOf("@id"),
							eleCoverage.valueOf("@allowed"),strPermission,null));
				}//end of else
			}//end of else if(strFlow.equals("FamilyRule"))

			else if(strFlow.equals("MemberRule"))
			{
				if(!(eleCoverage.valueOf("@module").equalsIgnoreCase("M")))
				{
					sbfDisplayCoverage.append(TTKCommon.buildDisplayElement(eleDisplay,eleCoverage.valueOf("@id"),
							eleCoverage.valueOf("@allowed"),strDisabled,null));

					sbfDisplayCoverage.append("<input type=\"hidden\" name=\"").append(eleCoverage.valueOf("@id")).append("\" ");
					sbfDisplayCoverage.append(" value=\"").append(eleCoverage.valueOf("@allowed")).append("\" />");
				}//end of if(!(eleCoverage.valueOf("@module").equalsIgnoreCase("M")))
				else
				{
					sbfDisplayCoverage.append(TTKCommon.buildDisplayElement(eleDisplay,eleCoverage.valueOf("@id"),
							eleCoverage.valueOf("@allowed"),strPermission,null));
				}//end of else
			}//end of else if(strFlow.equals("MemberRule"))
		}//end of if(hmDisplayNodes.get(eleCoverage.valueOf("@id"))!=null)

		sbfDisplayCoverage.append("<td>");
		sbfDisplayCoverage.append("</tr>");
		return sbfDisplayCoverage.toString();

	}//end of displayCoverage(Element eleCoverage,String strPermission)

    /**
	 * This method is used to display the corresponding Conditions to be defined
	 * for the Coverage based on the Rule definition level
	 *
	 * @param eleCoverage Element Coverage node whose conditions to be defined
	 * @param hmDisplayNodes HashMap of Display Nodes
	 * @param strPermission String Permission to be checked when displaying the Rules
	 * @return  sbfDisplayConditions String Returns html code to be displayed
	 * @throws TTKException  if any run time error occures
	 */
	private String displayConditions(Element eleCoverage,HashMap hmDisplayNodes,
			String strPermission)throws TTKException
	{
		StringBuffer sbfDisplayConditions=new StringBuffer();
		List conditionList=null;
		ArrayList alDisplay=null;
		List textList1=null;
		List textList2=null;
		List textList=null;
		Element eleDisplay=null;
		Element eleCondition=null;
		Element eleText=null;
		String strConditionXPath="";

		if(strFlow.equals("ProdPolicyRule"))
		{
			strConditionXPath="P";          //for selecting the conditions to be defined at Product/Policy Level
		}//end of if(strFlow.equals("ProdPolicyRule"))
		else if(strFlow.equals("FamilyRule"))
		{
			strConditionXPath="E";          //for selecting the conditions to be defined at Family Level
		}//end of else if(strFlow.equals("FamilyRule"))
		else if(strFlow.equals("MemberRule"))
		{
			strConditionXPath="M";          //for selecting the conditions to be defined at Member Level
		}//end of else if(strFlow.equals("MemberRule"))

		if(eleCoverage.selectSingleNode("./condition[@module='"+strConditionXPath+"']|./text[@value!='']")!=null)
		{
			sbfDisplayConditions.append("<tr id=\"coverage").append(eleCoverage.valueOf("@id")).append("\"");
			if(eleCoverage.valueOf("@allowed").equals("~")||eleCoverage.valueOf("@allowed").equals(""))
			{
				if(hmDisplayNodes.get(eleCoverage.valueOf("@id"))!=null)
				{
					eleDisplay= (Element)((ArrayList)hmDisplayNodes.get(eleCoverage.valueOf("@id"))).get(0);
					sbfDisplayConditions.append(" style=\"");
					sbfDisplayConditions.append(eleDisplay.valueOf("@default").equalsIgnoreCase(CONDITIONAL_PAY)? strShowStyle : strHideStyle).append("\">");
				}//end of if(hmDisplayNodes.get(eleCoverage.valueOf("@id"))!=null)
			}//end of if(eleCoverage.valueOf("@allowed").equals("~")||eleCoverage.valueOf("@allowed").equals(""))
			else
			{
				sbfDisplayConditions.append(" style=\"");
				sbfDisplayConditions.append(!(eleCoverage.valueOf("@allowed").equalsIgnoreCase(CONDITIONAL_PAY))? strHideStyle: strShowStyle).append("\">");
			}//end of else

			sbfDisplayConditions.append("<td colspan=\"2\">");
			sbfDisplayConditions.append("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
			sbfDisplayConditions.append("<tr><td class=\"order\" nowrap><ol>");

			//select the conditions for the present coverage
			conditionList=eleCoverage.selectNodes("./condition[@module='"+strConditionXPath+"']");
			if(conditionList!=null)
			{
				for(Iterator itCondtion=conditionList.iterator();itCondtion.hasNext();)
				{
					eleCondition=(Element)itCondtion.next();
					String strSerNO=eleCondition.valueOf("@SerNO");
					String strCondStyleId=eleCondition.valueOf("@styleId");
					
					//displayList=eleCondition.selectNodes("./display");
					alDisplay=(ArrayList)hmDisplayNodes.get(eleCondition.valueOf("@id"));
					if(alDisplay!=null && alDisplay.size()>0)
					{
						String strDynValue=eleCondition.valueOf("@dynValue");
						String strMethodValue=eleCondition.valueOf("@method");
						String strAutoSelectValue=eleCoverage.valueOf("@autoselect");
						String[] strAutoSelectValues=null;
						String [] strDynValues=null;
						String [] strMethodValues=null;

						if(strDynValue.indexOf("(")!=-1)
						{
							strDynValues=(strDynValue.substring(strDynValue.indexOf("(")+1,
									strDynValue.lastIndexOf(")"))).split(",");
						}//end of if(strDynValue.indexOf("(")!=-1)
						if(strMethodValue.indexOf("(")!=-1)
						{
							strMethodValues=(strMethodValue.substring(strMethodValue.indexOf("(")+1,
									strMethodValue.lastIndexOf(")"))).split(",");
						}//end of if(strMethodValue.indexOf("(")!=-1)
						if(strAutoSelectValue.indexOf("(")!=-1)
						{
							strAutoSelectValues=(strAutoSelectValue.substring(strAutoSelectValue.indexOf("(")+1,
									strAutoSelectValue.lastIndexOf(")"))).split(",");
						}//end of if(strAutoSelectValue.indexOf("(")!=-1)
						if("N".equals(strSerNO))
							sbfDisplayConditions.append("<br><br><br><span>");
							
						else{
							if(!"".equals(strCondStyleId))
								sbfDisplayConditions.append("<li id='").append(strCondStyleId).append("'>");
							    else sbfDisplayConditions.append("<li>");								
						}

						for(int iDisplayElementCnt=0;iDisplayElementCnt<alDisplay.size();iDisplayElementCnt++)
						{
							eleDisplay=(Element)alDisplay.get(iDisplayElementCnt);
							String strTargetValue=getTargetValue(eleCondition,eleDisplay,strMethodValues,
									strDynValues,strAutoSelectValues);

							if(iDisplayElementCnt==0)
							{
								sbfDisplayConditions.append(eleDisplay.valueOf("@prelabel")).append("&nbsp;");
								if(eleCondition.valueOf("@configure").equals("NO") ||eleDisplay.
										valueOf("@control").equals(""))
								{
									sbfDisplayConditions.append(RuleConfig.getOperatorText(eleCondition.
											valueOf("@opType"),eleCondition.valueOf("@op"))).append(" ");
								}//end of if(eleDisplay.valueOf("@control").equals(""))
								else
								{
									if(eleCondition.valueOf("@mandatory").equals("YES"))
									{
										sbfDisplayConditions.append("<span class=\"mandatorySymbol\">*</span>&nbsp; ");
									}//end of if(eleCondition.valueOf("@mandatory").equals("YES"))
									sbfDisplayConditions.append(TTKCommon.buildDisplayElement(RuleConfig.
											getOperatorElement(eleCondition.valueOf("@opType")),(eleCondition.valueOf("@id")+".operator"),
											eleCondition.valueOf("@op"),strPermission,null)).append(" ");
								}//end of else

								if(!eleDisplay.valueOf("@control").equals(""))
								{
									sbfDisplayConditions.append(TTKCommon.buildDisplayElement(eleDisplay,
											eleDisplay.valueOf("@id"),strTargetValue,strPermission,null));
									sbfDisplayConditions.append(" ");
								}//end of if(!eleDisplay.valueOf("@control").equals(""))
								sbfDisplayConditions.append(eleDisplay.valueOf("@postlabel"));
							}//end of if(iDisplayElementCnt==0)

							else if(!eleDisplay.valueOf("@target").contains("fieldData"))
							{
								sbfDisplayConditions.append(" ").append(eleDisplay.valueOf("@prelabel"));
								sbfDisplayConditions.append(" ").append(TTKCommon.buildDisplayElement(eleDisplay,
										eleDisplay.valueOf("@id"),strTargetValue,strPermission,null)).append(" ");

								sbfDisplayConditions.append(eleDisplay.valueOf("@postlabel"));
							}//end of else if(!eleDisplay.valueOf("@target").contains("fieldData"))
						}//end of for(int iDisplayElementCnt=0;iDisplayElementCnt<displayList.size();iDisplayElementCnt++)
						if("N".equals(strSerNO))
							sbfDisplayConditions.append("</span>");
							else 
								sbfDisplayConditions.append("</li>");
					}//enf of if(displayList!=null && displayList.size()>0)
				} //end of for(Iterator itCondtion=conditionList.iterator();itCondtion.hasNext();
			}//end of if(conditionList!=null)
			
			if(eleCoverage.selectSingleNode("./text[@value!='']")!=null)
			{
				textList=eleCoverage.selectNodes("./text[@value!='']");
				for(Iterator itText=textList.iterator();itText.hasNext();)
				{
					eleText=(Element)itText.next();
					sbfDisplayConditions.append("<li>");
					
					if(hmDisplayNodes.get(eleText.valueOf("@id"))!=null)
					{
						eleDisplay= (Element)((ArrayList)hmDisplayNodes.get(eleText.valueOf("@id"))).get(0);
						String strTargetValue=(eleText.valueOf("@value").equals("~")? eleDisplay.valueOf("@default"):
							eleText.valueOf("@value"));
						
						sbfDisplayConditions.append(eleDisplay.valueOf("@prelabel")).append("&nbsp;");
						sbfDisplayConditions.append(TTKCommon.buildDisplayElement(eleDisplay,eleDisplay.valueOf("@id"),
								strTargetValue,strPermission,null)).append(" ");
					}//end of if(hmDisplayNodes.get(eleText.valueOf("@id"))!=null)
					else
					{
						sbfDisplayConditions.append(eleText.valueOf("@value"));
					}//end of else
					sbfDisplayConditions.append("</li>");
				}//end of for(Iterator itText=textList.iterator();itText.hasNext())
			}//end of if(eleCoverage.selectSingleNode("./text")!=null)
			sbfDisplayConditions.append("</ol></td></tr></table>");
			sbfDisplayConditions.append("</td>");
			sbfDisplayConditions.append("</tr>");
		}//end of else if(eleCoverage.selectSingleNode("./condition[@module='P']")!=null)
		else if(eleCoverage.selectSingleNode("./text[@value!='']")!=null)
		{
			sbfDisplayConditions.append("<tr id=\"coverage").append(eleCoverage.valueOf("@id")).append("\"");
			if(eleCoverage.valueOf("@allowed").equals("~")||eleCoverage.valueOf("@allowed").equals(""))
			{
				if(hmDisplayNodes.get(eleCoverage.valueOf("@id"))!=null)
				{
					eleDisplay= (Element)((ArrayList)hmDisplayNodes.get(eleCoverage.valueOf("@id"))).get(0);
					sbfDisplayConditions.append(" style=\"");
					sbfDisplayConditions.append(eleDisplay.valueOf("@default").equalsIgnoreCase(CONDITIONAL_PAY)? strShowStyle : strHideStyle).append("\">");
				}//end of if(hmDisplayNodes.get(eleCoverage.valueOf("@id"))!=null)
			}//end of if(eleCoverage.valueOf("@allowed").equals("~")||eleCoverage.valueOf("@allowed").equals(""))
			else
			{
				sbfDisplayConditions.append(" style=\"");
				sbfDisplayConditions.append(!(eleCoverage.valueOf("@allowed").equalsIgnoreCase(CONDITIONAL_PAY))? strHideStyle: strShowStyle).append("\">");
			}//end of else

			sbfDisplayConditions.append("<td colspan=\"2\">");
			sbfDisplayConditions.append("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
			sbfDisplayConditions.append("<tr><td class=\"order\" nowrap><ol>");
			textList1=eleCoverage.selectNodes("./text[@value!='']");
			for(Iterator itText=textList1.iterator();itText.hasNext();)
			{
				eleText=(Element)itText.next();
				sbfDisplayConditions.append("<li>");

				if(hmDisplayNodes.get(eleText.valueOf("@id"))!=null)
				{
					eleDisplay= (Element)((ArrayList)hmDisplayNodes.get(eleText.valueOf("@id"))).get(0);
					String strTargetValue=(eleText.valueOf("@value").equals("~")? eleDisplay.valueOf("@default"):
						eleText.valueOf("@value"));

					sbfDisplayConditions.append(eleDisplay.valueOf("@prelabel")).append("&nbsp;");
					sbfDisplayConditions.append(TTKCommon.buildDisplayElement(eleDisplay,eleDisplay.valueOf("@id"),
							strTargetValue,strPermission,null)).append(" ");
				}//end of if(hmDisplayNodes.get(eleText.valueOf("@id"))!=null)
				else
				{
					sbfDisplayConditions.append(eleText.valueOf("@value"));
				}//end of else
				sbfDisplayConditions.append("</li>");
			}//end of for(Iterator itText=textList.iterator();itText.hasNext())
			sbfDisplayConditions.append("</ol></td></tr></table>");
			sbfDisplayConditions.append("</td>");
			sbfDisplayConditions.append("</tr>");
		}//end of if(eleCoverage.selectSingleNode("./text")!=null)
		else{
			sbfDisplayConditions.append("<tr id=\"coverage").append(eleCoverage.valueOf("@id")).append("\"");
			if(eleCoverage.valueOf("@allowed").equals("~")||eleCoverage.valueOf("@allowed").equals(""))
			{
				if(hmDisplayNodes.get(eleCoverage.valueOf("@id"))!=null)
				{
					eleDisplay= (Element)((ArrayList)hmDisplayNodes.get(eleCoverage.valueOf("@id"))).get(0);
					sbfDisplayConditions.append(" style=\"");
					sbfDisplayConditions.append(eleDisplay.valueOf("@default").equalsIgnoreCase(CONDITIONAL_PAY)? strShowStyle : strHideStyle).append("\">");
				}//end of if(hmDisplayNodes.get(eleCoverage.valueOf("@id"))!=null)
			}//end of if(eleCoverage.valueOf("@allowed").equals("~")||eleCoverage.valueOf("@allowed").equals(""))
			else
			{
				sbfDisplayConditions.append(" style=\"");
				sbfDisplayConditions.append(!(eleCoverage.valueOf("@allowed").equalsIgnoreCase(CONDITIONAL_PAY))? strHideStyle: strShowStyle).append("\">");
			}//end of else

			sbfDisplayConditions.append("<td colspan=\"2\">");
			sbfDisplayConditions.append("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
			sbfDisplayConditions.append("<tr><td class=\"order\" nowrap><ol>");
			
			textList2=eleCoverage.selectNodes("./text[@value='']");
			for(Iterator itText=textList2.iterator();itText.hasNext();)
			{
				eleText=(Element)itText.next();
				sbfDisplayConditions.append("<li>");

				if(hmDisplayNodes.get(eleText.valueOf("@id"))!=null)
				{
					eleDisplay= (Element)((ArrayList)hmDisplayNodes.get(eleText.valueOf("@id"))).get(0);
					String strTargetValue=(eleText.valueOf("@value").equals("~")? eleDisplay.valueOf("@default"):
						eleText.valueOf("@value"));

					sbfDisplayConditions.append(eleDisplay.valueOf("@prelabel")).append("&nbsp;");
					sbfDisplayConditions.append(TTKCommon.buildDisplayElement(eleDisplay,eleDisplay.valueOf("@id"),
							strTargetValue,strPermission,null)).append(" ");
				}//end of if(hmDisplayNodes.get(eleText.valueOf("@id"))!=null)
				else
				{
					sbfDisplayConditions.append(eleText.valueOf("@value"));
				}//end of else
				sbfDisplayConditions.append("</li>");
			}//end of for(Iterator itText=textList.iterator();itText.hasNext())
			sbfDisplayConditions.append("</ol></td></tr></table>");
			sbfDisplayConditions.append("</td>");
			sbfDisplayConditions.append("</tr>");
		}//end of else
		
		log.info("Inside Clause Details - displayConditions method - Display Conditions ");
		//log.info("Inside Clause Details - displayConditions method - Display Conditions "+sbfDisplayConditions);

		return sbfDisplayConditions.toString();
	}//end of displayConditions(Element eleCoverage,String strPermission)

    /**
	 * This method will return the target value for the display node.
	 * It get the target position from the target attribute of the display node.
	 * Retrieves the target value from corresponding position of the Condition node returns it.
	 *
	 * @param eleCondition Element Condtion node from which element needs to be
	 * @param eleDisplay Element Display node for which target value should be retrieved
	 * @param strMethodValues String[] Array of target values from Method attribute
	 * @param strDynValues String[] Array of target values from dynValue attribute
	 * @return strTargetValue String  target value
	 */
	private String getTargetValue(Element eleCondition,Element eleDisplay,String[] strMethodValues,
			String[] strDynValues,String[] strAutoSelectValues)
	{
		String strTargetValue="";
		try
		{
			String strTarget=eleDisplay.valueOf("@target");
			int iTargetPos=getTargetPosition(strTarget);

			if(strTarget.startsWith("value"))
			{
				strTargetValue=eleCondition.valueOf("@value");
			}//end of if(strTarget.startsWith("value"))
			else if(strTarget.startsWith("dynValue"))
			{
				strTargetValue=strDynValues[iTargetPos-1];
				if(strTargetValue.startsWith("'"))
				{
					strTargetValue= strTargetValue.substring(strTargetValue.indexOf("'")+1,
							strTargetValue.lastIndexOf("'"));
				}//end of if(strTargetValue.startsWith("'"))
			}//end of else if(strTarget.startsWith("dynValue"))
			else if(strTarget.startsWith("autoselect"))
			{
				strTargetValue=strAutoSelectValues[iTargetPos-1];
				if(strTargetValue.startsWith("'"))
				{
					strTargetValue= strTargetValue.substring(strTargetValue.indexOf("'")+1,
							strTargetValue.lastIndexOf("'"));
				}//end of if(strTargetValue.startsWith("'"))
			}//end of else if(strTarget.startsWith("autoselect"))
			else if(strTarget.startsWith("method"))
			{
				strTargetValue=strMethodValues[iTargetPos-1];
				if(strTargetValue.startsWith("'"))
				{
					strTargetValue= strTargetValue.substring(strTargetValue.indexOf("'")+1,
							strTargetValue.lastIndexOf("'"));
				}//end of if(strTargetValue.startsWith("'"))
			}//end of else if(strTarget.contains("method"))

			if(strTargetValue.equals("~"))
			{
				strTargetValue=eleDisplay.valueOf("@default");
			}//end of if(strTargetValue.equals("~"))
		}//end of try
		catch(ArrayIndexOutOfBoundsException e)
		{
			log.error("Error while getting the target value for display Element........"+ eleDisplay.valueOf("@id"));
		}//end of catch(ArrayIndexOutOfBoundsException e)
		catch(NumberFormatException ne)
		{
			log.error("Error while getting the target position for display Element........"+ eleDisplay.valueOf("@id"));
		}//end of catch(NumberFormatException ne)
		catch(NullPointerException ne)
		{
			log.error("Error while getting the target value for display Element........"+ eleDisplay.valueOf("@id"));
		}//end of catch(NullPointerException ne)
		return strTargetValue;
	}//end of getTargetValue(Element eleCondition,Element eleDisplay,String[] strMethodValues,String[] strDynValues)

	/**
	 * This method gives the position of the target from where value should be retrieved
	 * @param strTarget String value of the target attribute
	 * @return iTargetPos int position of the target
	 * @throws ArrayIndexOutOfBoundsException
	 * @throws NumberFormatException
	 */
	private int getTargetPosition(String strTarget) throws ArrayIndexOutOfBoundsException,NumberFormatException
	{
		int iTargetPos=-1;
		if(!strTarget.equals(""))
		{
			String [] strTargets=strTarget.split(";");
			String[] strCurrentTargetValues=strTargets[0].split(",");
			iTargetPos=Integer.parseInt(strCurrentTargetValues[1]);
		}//end of if(!strTarget.equals(""))
		return iTargetPos;
	}//end of getTargetPosition(String strTarget)

}//end of ClauseDetails.java