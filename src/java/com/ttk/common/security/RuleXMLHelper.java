/**
 * @ (#) RuleXmlHelper.java Jun 24, 2006
 * Project       : TTK HealthCare Services
 * File          : RuleXmlHelper.java
 * Author        : Unni V M
 * Company       : Span Systems Corporation
 * Date Created  : Jun 24, 2006
 *
 * @author       :  Unni V M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.security;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.DOMReader;

import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.dto.administration.ErrorLogVO;

/**
 * This Class is used for updating the user entered data in the xml file,
 * for merging the XML files, removing the unwanted nodes based on the business
 * by the Rule Engine related flows from Administration, Enrollment, Pre-Auth and
 * Claims module.
 *
 */
public class RuleXMLHelper {
	private static Logger log = Logger.getLogger(RuleXMLHelper.class);
	
	private static final String strPay="1";
//	private static final String strDontPay="2";
	private static final String strPayConditonally="3";
	private static final String strApply="1";
//	private static final String strDontApply="2";
	private static final String strApplyConditionally="3";
	private static final String strDelimeter="|";
	/**
	 * This method will return  Clause Ids as array of String.
	 *
	 * @param xmldoc  Document from Clause Ids to be retrieved
	 * @return string array of Clause Ids
	 */
	public String [] getClausesNodes(Document xmldoc)
	{
		// parse this xml and returns nodelist which contains nodes
		List list = xmldoc.selectNodes("/clauses/clause[@name !='Global']");
		String [] clauses = new String[(list==null)?0:list.size()];
		for(int i=0; i<list.size(); i++)
		{
			Element element =(Element) list.get(i);
			clauses[i]=element.valueOf("@id");
		}//end of for(int i=0; i<list.size(); i++)
		return clauses;
	}//end of getClausesNodes(Document xmldoc)
	
	/**
	 * This method will return the selected coverages as array of Strings
	 *
	 * @param xmlDoc Document  xml from which coverages to be selected
	 * @return strCoverages String [] coverages names
	 */
	public String [] getCoverageNodes(Document xmlDoc)
	{
		List coverageList=xmlDoc.selectNodes("//clause/coverage[@selected='YES']");
		String [] strCoverages=new String[(coverageList==null)?0:coverageList.size()];
		Element eleCoverage=null;
		if(coverageList!=null)
		{
			for(int iCoverageCnt=0;iCoverageCnt<coverageList.size();iCoverageCnt++)
			{
				eleCoverage=(Element)coverageList.get(iCoverageCnt);
				strCoverages[iCoverageCnt]=eleCoverage.valueOf("@id");
			}//end of for(int iCoverageCnt=0;iCoverageCnt<coverageList.size();iCoverageCnt++)
		}//end of if(coverageList!=null)
		return strCoverages;
	}//end of getCoverageNodes(Document xmlDoc)
	
	/**
	 * This method will update the RuleXml object to be executed in Preauth/Claim based on the
	 * coverages selected by the user
	 *
	 * @param ruleDataDocument Document
	 * @param request HttpServletRequest object
	 * @return ruleDataDocument Document updated Rule Document
	 */
	public Document updateCoverageSelectionStatus(Document ruleDataDocument,
			HttpServletRequest request)throws TTKException
	{
		List coverageList =ruleDataDocument.selectNodes("//clause/coverage");
		if(coverageList!=null)
		{
			Element eleCoverage=null;
			for(Iterator itCoverage=coverageList.iterator();itCoverage.hasNext();)
			{
				eleCoverage=(Element)itCoverage.next();
				if(request.getParameter(eleCoverage.valueOf("@id"))!=null)
				{
					eleCoverage.addAttribute("selected","YES");
				}//end of if(request.getParameter(eleCoverage.valueOf("@id"))==null)
				else
				{
					eleCoverage.addAttribute("selected","NO");
				}//end of else
			}//end of for(Iterator itCoverage=coverageList.iterator();itCoverage.hasNext();)
		}//end of if(coverageList!=null)
		return ruleDataDocument;
	}//end of updateCoverageSelectionStatus(Document combinedRuleDocument,HttpServletRequest request)
//Changes............................on jan 11th 2012 KOC1099
	/**
	 * This method will update the RuleXml object to be executed in Preauth/Claim based on the
	 * remarks entered by the user
	 *
	 * @param ruleDataDocument Document
	 * @param request HttpServletRequest object
	 * @return ruleDataDocument Document updated Rule Document
	 */
	public Document updateRemarks(Document ruleDataDocument,
			HttpServletRequest request)throws TTKException
	{
		
		List displayList =ruleDataDocument.selectNodes("//clause/remarks/display");
		if(displayList!=null)
		{
			Element eleDispay=null;
			for(Iterator itDisplay=displayList.iterator();itDisplay.hasNext();)
			{
				eleDispay=(Element)itDisplay.next();
				if(request.getParameter(eleDispay.valueOf("@id"))!=null)
				{
					eleDispay.getParent().setAttributeValue("value", request.getParameter(eleDispay.valueOf("@id")));
					
					//setAttributeValue("value",request.getParameter(eleDispay.valueOf("@id"));
				}//end of if(request.getParameter(eleDispay.valueOf("@id"))==null)
				else
				{
					eleDispay.setAttributeValue("value","~");
				}//end of else
			}//end of for(Iterator itCoverage=coverageList.iterator();itCoverage.hasNext();)
		}//end of if(displayList!=null)
		displayList=null;
		
		return ruleDataDocument;
	}//end of updateremarks(Document combinedRuleDocument,HttpServletRequest request)
	
	//Changes............................on jan 11th 2012 KOC1099
		
	/**
	 * This method updates the data entered by the user back to the xml file.
	 *
	 * @param xmlDoc document to be updated
	 * @param request HttpServletRequest
	 * @return xmlDoc Document updated document
	 */
	public Document updateRuleDataDocument(Document xmlDoc,HttpServletRequest request)
	{
		//get the conditions list
		String strXpath="//clause/coverage[@allowed='3' and @selected='YES']/condition[@source='XML']";
		List conditionList=xmlDoc.selectNodes(strXpath);
		Element eleCondition=null;
		if(conditionList!=null)
		{
			for(Iterator itCondition=conditionList.iterator();itCondition.hasNext();)
			{
				eleCondition=(Element)itCondition.next();
				eleCondition.addAttribute("fieldData",TTKCommon.checkNull(request.
						getParameter(eleCondition.valueOf("@id"))));
			}//end of for(Iterator itCondition=conditionList.iterator();itCondition.hasNext();)
		}//end of if(conditionList!=null)
		return xmlDoc;
	}//end of updateRuleDataDocument(Document xmlDoc,HttpServletRequest request)
	
	/**
	 * This method is used to clear the current Rule results before evaluating the rules again.
	 *
	 * @param ruleDocument Document object of the Rules for which results to be cleared
	 * @return ruleDocument Document with the results cleared
	 */
	public Document clearRuleResults(Document ruleDocument)
	{
		if(ruleDocument!=null)
		{
			//clear the Overall Confidence Level of Pre-auth/Claim
			ruleDocument.getRootElement().addAttribute("clpercentage","");
			
			//get all the coverage nodes to clear the clpercentage attribute
			List coveragelist=ruleDocument.selectNodes("//coverage");
			if(coveragelist!=null)
			{
				for(Iterator itCoverage=coveragelist.iterator();itCoverage.hasNext();)
				{
					((Element)itCoverage.next()).addAttribute("clpercentage","");
				}//end of for(Iterator itCoverage=coveragelist.iterator();itCoverage.hasNext();)
			}//end of  if(coveragelist!=null)
			//get all the condition nodes to clear the Results
			List conditionList=ruleDocument.selectNodes("//condition");
			if(conditionList!=null)
			{
				for(Iterator itCondition=conditionList.iterator();itCondition.hasNext();)
				{
					((Element)itCondition.next()).addAttribute("result","");
				}//end of for(Iterator itCondition=conditionList.iterator();itCondition.hasNext();)
			}//end of if(conditionList!=null)
		}//end of if(ruleDocument!=null)
		ruleDocument.normalize();
		return ruleDocument;
	}//end of clearRuleResults(Document ruleDocument)
	
	/**
	 * This method prepares the xml document from MasterBaseRule and containing
	 * the selected clauses from the User. Global clause is selected automatically.
	 *
	 * @param xmldoc Document from which Clauses to be filtered
	 * @param strClauseIds  String array of Clause Ids
	 * @return xml Document
	 */
	public Document getClauses(Document xmldoc,String [] strClauseIds)
	{
		Element root = xmldoc.getRootElement();
		StringBuffer sbStrXml = new StringBuffer();
		sbStrXml.append("<clauses>");
		for (Iterator i = root.elementIterator(); i.hasNext();)
		{
			Element element = (Element) i.next();
			// getting the global Clause node automatically
			if(element.valueOf("@name").equalsIgnoreCase("global"))
			{
				sbStrXml.append(element.asXML());
			}//end of if(element.valueOf("@name").equalsIgnoreCase("global"))
			
			// Make selected clauses as allowed and and append them to the XML
			for(int j = 0; j < strClauseIds.length; j++)
			{
				if(strClauseIds[j].equalsIgnoreCase(element.valueOf("@id")))
				{
					element.addAttribute("allowed","YES");
					sbStrXml.append(element.asXML());
					break;
				}//end of if(strClauseIds[j].equalsIgnoreCase(element.valueOf("@id")))
			}//end of for(int j = 0; j < strClauseIds.length; j++)
		}//end of for (Iterator i = root.elementIterator(); i.hasNext();)
		sbStrXml.append("</clauses>");
		Document document = null;
		try{
			//constructing the xml document with these selected clauses
			document = DocumentHelper.parseText(sbStrXml.toString());
		}//end of try
		catch(Exception e)
		{
			log.error("Error while parsing the Document");
		}//end of catch(Exception e)
		return sortDocument(document) ;
	}//end of getClauses(Document xmldoc,String [] strClauseIds)
	
	/**
	 * This method merges the Rule Document stored in database/session and the
	 * selected clause from Master Base rule.
	 * @param doc1  stored rule xml document
	 * @param doc2  xml document constructed from the selected clauses
	 * @return document returns the merged Document
	 */
	public Document getMergedRuleDocument1(Document storedClauses, Document selectedClauses)
	{
		//iterating over the PA stored xml document
		Element root = storedClauses.getRootElement();
		
		//remove the Global clause stored in database so that latest Clause present in merged Document
		Node globalClause=storedClauses.selectSingleNode("/clauses/clause[@name='Global']");
		if(globalClause!=null)
		{
			globalClause.detach();
			storedClauses.normalize();
		}//end of if(globalClause!=null)
		
		List list = root.elements();
		for(int i=0; i<list.size(); i++)
		{
			Element element = (Element) list.get(i);
			// checking this element is present in the second xml document
			Node node = selectedClauses.selectSingleNode("/clauses/clause[@id='"+element.valueOf("@id")+"']");
			
			// if match found , remove that element from the second document (ie selectedClauses)
			if(node !=null)
			{
				node.detach();  //removing from second document (ie selectedClauses)
			}//end of if(node !=null)
			else
			{
				// remove from the first document (ie storedClauses)
				Node clauseNode = storedClauses.selectSingleNode("/clauses/clause[@id='"+element.valueOf("@id")+"']");
				if(clauseNode !=null)
					clauseNode.detach();
			}//end of else
		}//end of for(int i=0; i<list.size(); i++)
		
		storedClauses.normalize();
		selectedClauses.normalize();
		
		// getting the root element from the first document (ie storedClauses)
		Element root1 = storedClauses.getRootElement();
		
		// iterating over the second docuemnt (selectedClauses)
		Element root2 = selectedClauses.getRootElement();
		List list2    = root2.elements();
		for(int i=0; i<list2.size(); i++)
		{
			Element element = (Element) list2.get(i);
			element.detach();
			// now add the remaining clause nodes in the second document xml to the first document xml
			root1.add(element);
		}//end of for(int i=0; i<list2.size(); i++)
		
		//returning the merged and sorted document
		return sortDocument(storedClauses) ;
	}//end of getMergedRuleDocument1(Document storedClauses, Document selectedClauses)
	
	/**
	 * This method merges the Rule Document stored in database/session and the
	 * selected clause from Master Base rule.
	 * @param doc1  stored rule xml document
	 * @param doc2  xml document constructed from the selected clauses
	 * @return document returns the merged Document
	 */
	public Document getMergedRuleDocument(Document storedDoc, Document selectedDoc)
	{
		Element eleClause=null;
//		Element eleStoredClause=null;
		Element eleCoverage=null;
		Element eleChild=null;
		List childList=null;
		List coverageList=null;
		List clauseList=null;
		//remove the Global clause stored in database so that latest Clause present in merged Document
		Node globalClause=storedDoc.selectSingleNode("//clause[@name='Global']");
		if(globalClause!=null)
		{
			globalClause.detach();
			storedDoc.normalize();
		}//end of if(globalClause!=null)
		
		Element eleStoredDocRoot=storedDoc.getRootElement();
		clauseList=eleStoredDocRoot.elements("clause");
		for(int iClauseCnt=0;iClauseCnt<clauseList.size();iClauseCnt++)
		{
			eleClause=(Element)clauseList.get(iClauseCnt);
			
			if(selectedDoc.selectSingleNode("//clause[@id='"+eleClause.valueOf("@id")+"']")==null)
			{
				eleClause.detach();	//remove the clause if not present
			}//end of if(selectedDoc.selectSingleNode("//clause[@id='"+eleClause.valueOf("@id")+"']")==null)
			else	//process the coverage
			{
				coverageList=eleClause.elements("coverage");
				for(int iCvrgCnt=0;iCvrgCnt<coverageList.size();iCvrgCnt++)
				{
					eleCoverage=(Element)coverageList.get(iCvrgCnt);
					if(selectedDoc.selectSingleNode("//coverage[@id='"+eleCoverage.valueOf("@id")+"']")==null)
					{
						eleCoverage.detach(); //remove the coverage if it is not present in the latest
					}//end of if(selectedDoc.selectSingleNode("//coverage[@id='"+eleCoverage.valueOf("@id")+"']")==null)
					else	
					{
						//process condtions, text and action nodes
						childList=eleCoverage.elements();
						for(int iChildCnt=0;iChildCnt<childList.size();iChildCnt++)
						{
							eleChild=(Element)childList.get(iChildCnt);
							
							if(eleChild.getName().equals("condition") && (selectedDoc.selectSingleNode("//condition[@id='"+eleChild.valueOf("@id")+"']")==null))
							{
								eleChild.detach();	//remove the condition if it is not present in the latest
							}//end of if(eleChild.getName().equals("condition") && (selectedDoc.selectSingleNode("//condition[@id='"+eleChild.valueOf("@id")+"']")==null))
							else if(eleChild.getName().equals("text") && selectedDoc.selectSingleNode("//text[@id='"+eleChild.valueOf("@id")+"']")==null)
							{
								eleChild.detach();	//remove the text if it is not present in the latest
							}//end of if(eleChild.getName().equals("text") && selectedDoc.selectSingleNode("//text[@id='"+eleChild.valueOf("@id")+"']")==null)
							else if(eleChild.getName().equals("action") && selectedDoc.selectSingleNode("//action[@id='"+eleChild.valueOf("@id")+"']")==null)
							{
								eleChild.detach();	//remove the action if it is not present in the latest
							}//end of if(eleChild.getName().equals("action") && selectedDoc.selectSingleNode("//action[@id='"+eleChild.valueOf("@id")+"']")==null)
							eleChild=null;
						}//end of for(int iChildCnt=0;iChildCnt<childList.size();iChildCnt++)
					}//end of else
					eleCoverage=null;
				}//end of for(int iCvrgCnt=0;iCvrgCnt<coverageList.size();iCvrgCnt++)
			}//end of else
			eleClause=null;
		}//end of for(int iClauseCnt=0;iClauseCnt<clauseList.size();iClauseCnt++)
		storedDoc.normalize();
		selectedDoc.normalize();
		
		Element eleSelectedDocRoot=selectedDoc.getRootElement();
		for(Iterator clauseIterator=eleSelectedDocRoot.elementIterator("clause");clauseIterator.hasNext();)
		{
			eleClause=(Element)clauseIterator.next();
			
			if(storedDoc.selectSingleNode("//clause[@id='"+eleClause.valueOf("@id")+"']")==null)
			{
				//add the new clause to the document to the session/strored xml
				eleStoredDocRoot.add(eleClause.createCopy());
			}//end of if(storedDoc.selectSingleNode("//clause[@id='"+eleClause.valueOf("@id")+"']")==null)
			else	//process the coverage
			{
				for(Iterator coverageIterator=eleClause.elementIterator("coverage");coverageIterator.hasNext();)
				{
					eleCoverage=(Element)coverageIterator.next();
					
					if(storedDoc.selectSingleNode("//coverage[@id='"+eleCoverage.valueOf("@id")+"']")==null)
					{
						//Add the new coverage to the document to the session/strored xml
						((Element)storedDoc.selectSingleNode("//clause[@id='"+eleClause.valueOf("@id")+"']")).add(eleCoverage.createCopy());	//
					}//end of if(storedDoc.selectSingleNode("//coverage[@id='"+eleCoverage.valueOf("@id")+"']")==null)
					else	
					{
						//process condtions, text and action nodes
						for(Iterator itChildIterator=eleCoverage.elementIterator();itChildIterator.hasNext();)
						{
							eleChild=(Element)itChildIterator.next();
							if(eleChild.getName().equals("condition") && storedDoc.selectSingleNode("//condition[@id='"+eleChild.valueOf("@id")+"']")==null)
							{
								((Element)storedDoc.selectSingleNode("//coverage[@id='"+eleCoverage.valueOf("@id")+"']")).add(eleChild.createCopy());
							}//end of if(eleChild.getName().equals("condition") && storedDoc.selectSingleNode("//condition[@id='"+eleChild.valueOf("@id")+"']")==null)
							else if(eleChild.getName().equals("text") && storedDoc.selectSingleNode("//text[@id='"+eleChild.valueOf("@id")+"']")==null)
							{
								((Element)storedDoc.selectSingleNode("//coverage[@id='"+eleCoverage.valueOf("@id")+"']")).add(eleChild.createCopy());
							}//end of else if(eleChild.getName().equals("text") && storedDoc.selectSingleNode("//text[@id='"+eleChild.valueOf("@id")+"']")==null)
							else if(eleChild.getName().equals("action") && storedDoc.selectSingleNode("//action[@id='"+eleChild.valueOf("@id")+"']")==null)
							{
								((Element)storedDoc.selectSingleNode("//coverage[@id='"+eleCoverage.valueOf("@id")+"']")).add(eleChild.createCopy());
							}//end of else if(eleChild.getName().equals("action") && storedDoc.selectSingleNode("//action[@id='"+eleChild.valueOf("@id")+"']")==null)
							eleChild=null;
						}//end of for(Iterator itChildIterator=eleCoverage.elementIterator();itChildIterator.hasNext();)
					}//end of else
				}//end of for(Iterator coverageIterator=eleClause.elementIterator("coverage");coverageIterator.hasNext();)
			}//end of else
		}//end of for(Iterator clauseIterator=eleSelectedDocRoot.elementIterator("clause");clauseIterator.hasNext();)
		storedDoc.normalize();
		selectedDoc.normalize();
		
		//returning the merged and sorted document
		return sortDocument(storedDoc) ;
	}//end of getMergedRuleDocument(Document storedDoc, Document selectedDoc)
	
	/**
	 * This method is used to update the xml document with the User Entered values from the screen.
	 * This method is reused in Administrion module for defining product and Policy Rules and in
	 * Enrollment module for defining Family and member rules.
	 *
	 * @param ruleDocument Document XML document to be updated
	 * @param strIdentifier Identifier Specifying which Rule is Going to be updated.
	 * @param request HttpServletRequest
	 * @return ruleDocument Document updated Document
	 * @throws TTKException if any run time Exception occures
	 */
	public Document updateRuleDocument(Document ruleDocument,String strIdentifier,
			HttpServletRequest request)throws TTKException{
		//declaration of the variables used
		List coverageList=null;
		List conditionList=null;
		Element eleCoverage=null;
		Element eleCondition=null;
		
		String strAdminCoverageXpath="contains(@module,'P')";
		String strEnrollmentCoverageXpath="contains(@module,'E')";
		String strMemberCoverageXpath="contains(@module,'M')";
		String strCoverageXpath="";
		String strActiveLink=TTKCommon.getActiveLink(request);
		
		if(strActiveLink.equals("Administration"))
		{
			strCoverageXpath=strAdminCoverageXpath;
		}//end of if(strActiveLink.equals("Administration"))
		else if(strActiveLink.equals("Enrollment"))
		{
			if(TTKCommon.checkNull(strIdentifier).equals("FamilyRule"))
			{
				strCoverageXpath=strEnrollmentCoverageXpath;
			}//end of if(TTKCommon.checkNull(strIdentifier).equals("FamilyRule"))
			else if(TTKCommon.checkNull(strIdentifier).equals("MemberRule"))
			{
				strCoverageXpath=strMemberCoverageXpath;
			}//end of else if(TTKCommon.checkNull(strIdentifier).equals("MemberRule"))
		}//end of else if(strActiveLink.equals("Enrollment"))
		
		//update the coverage nodes of selected clause
		coverageList=ruleDocument.selectNodes("//coverage["+strCoverageXpath+"]");
		
		if(coverageList!=null)
		{
			updateCoverages(coverageList,request);
			for(Iterator itCoverageList=coverageList.iterator();itCoverageList.hasNext();)
			{
				eleCoverage=(Element)itCoverageList.next();
				//select the conditions for the present coverage
				if(eleCoverage.selectSingleNode("./condition")!=null)
				{
					conditionList=eleCoverage.selectNodes("./condition");
					for(int iConditionCnt=0;iConditionCnt<conditionList.size();iConditionCnt++)
					{
						eleCondition=(Element)conditionList.get(iConditionCnt);
						//Update the current condition with User Entered values
						updateCondition(eleCondition,eleCoverage,request);
					}//end of for(int iConditionCnt=0;iConditionCnt<conditionList.size();iConditionCnt++)
				}//end of if(eleCoverage.selectSingleNode("./condition["+strConditionXpath+"]")!=null)
				
				if(eleCoverage.selectSingleNode("./text")!=null)
				{
					List textList=eleCoverage.selectNodes("./text");
					Element eleText=null;
					for(Iterator itText=textList.iterator();itText.hasNext();)
					{
						eleText=(Element)itText.next();
						updateText(eleText,request);
					}//end of for(Iterator itText=textList.iterator();itText.hasNext();)
				}//end of if(eleCoverage.selectSingleNode("./text")!=null)
			}//end of for(Iterator itCoverageList=coverageList.iterator();itCoverageList.hasNext();)
		}//end of if(coverageList!=null)
		
		//remove the display nodes from the XML before saving the Rule
		ruleDocument=removeDisplayNodes(ruleDocument);
		ruleDocument.normalize();
		return ruleDocument;
	}//end of updateRuleDocument(Document xmlDoc,String strIdentifier,HttpServletRequest request)
	
	/**
	 * This method will remove all the display nodes from the XML
	 * before saving the Rules.
	 *
	 * @param ruleDocument Documnet from which display nodes to be removed
	 * @return ruleDocument updated Document
	 */
	private Document removeDisplayNodes(Document ruleDocument)
	{
		if(ruleDocument!=null)
		{
			List displayList=ruleDocument.selectNodes("//display");
			List copayResultList=ruleDocument.selectNodes("//copayresult");
			Node displayNode=null;
			Node copayResultNode = null;
			if(displayList!=null && displayList.size()>0)
			{
				for(Iterator itDisplayList=displayList.iterator();itDisplayList.hasNext();)
				{
					displayNode=(Node)itDisplayList.next();
					displayNode.detach();
				}//end of for(Iterator itDisplayList=displayList.iterator();itDisplayList.hasNext();)
				ruleDocument.normalize();
			}//end of if(displayList!=null && displayList.size()>0)
			
			if(copayResultList!=null && copayResultList.size()>0)
			{
				for(Iterator itCopayResultList=copayResultList.iterator();itCopayResultList.hasNext();)
				{
					copayResultNode=(Node)itCopayResultList.next();
					copayResultNode.detach();
				}//end of for(Iterator itCopayResultList=displayList.iterator();itCopayResultList.hasNext();)
				ruleDocument.normalize();
			}//end of if(copayResultList!=null && copayResultList.size()>0)
		}//end of if(ruleDocument!=null)
		return ruleDocument;
	}//end of removeDisplayNodes(Document ruleDocument)
	
	/**
	 * This method will update the Coverage's allowed status when the rules are
	 * saved at Product/Policy, Family and Member level
	 *
	 * @param coverageList
	 * @param request
	 * @throws TTKException
	 */
	/*private void updateCoverages(List coverageList,HttpServletRequest request) throws TTKException
	{
		Element eleCoverage=null;
		if(coverageList!=null)
		{
			for(Iterator itCoverageList=coverageList.iterator();itCoverageList.hasNext();)
			{
				eleCoverage=(Element)itCoverageList.next(); 
				if(request.getParameter(eleCoverage.valueOf("@id"))!=null)
				{
					eleCoverage.addAttribute("allowed",request.getParameter(eleCoverage.valueOf("@id")));
				}//end of if(request.getParameter(eleCoverage.valueOf("@id"))!=null)
			}//end of for(Iterator itCoverageList=coverageList.iterator();itCoverageList.hasNext();)
		}//end of if(coverageList!=null)
	}//end of updateCoverages(List coverageList,HttpServletRequest request)
*/	
	
	private void updateCoverages(List coverageList,HttpServletRequest request) throws TTKException
	{
		Element eleCoverage=null;
		List alDisplay=null;
		String strTargetValue=null;
		Element eleDisplay=null;
		HashMap hmDisplayNodes=(HashMap)request.getSession().getServletContext().getAttribute("RULE_DISPLAY_NODES");
		if(coverageList!=null)
		{
			for(Iterator itCoverageList=coverageList.iterator();itCoverageList.hasNext();)
			{
				String[] strAutoSelectValues=null;
				eleCoverage=(Element)itCoverageList.next();
				if(!TTKCommon.checkNull(request.getParameter(eleCoverage.valueOf("@id"))).equals(""))
				{
					eleCoverage.addAttribute("allowed",request.getParameter(eleCoverage.valueOf("@id")));
					alDisplay=(ArrayList)hmDisplayNodes.get(eleCoverage.valueOf("@id"));
					String strAutoSelectValue=eleCoverage.valueOf("@autoselect");
					if(!TTKCommon.checkNull(strAutoSelectValue).equals("")){
						if(strAutoSelectValue.indexOf("(")!=-1)
						{
							strAutoSelectValues=(strAutoSelectValue.substring(strAutoSelectValue.indexOf("(")+1,
									strAutoSelectValue.lastIndexOf(")"))).split(",");
						}//end of if(strAutoSelectValue.indexOf("(")!=-1)
					}//end of if(!TTKCommon.checkNull(strAutoSelectValue).equals("") )
					
					for(Iterator itDisplayList=alDisplay.iterator();itDisplayList.hasNext();){
						eleDisplay=(Element)itDisplayList.next();
						
						strTargetValue =TTKCommon.checkNull(request.getParameter(eleCoverage.valueOf("@id")));
						
						if(!(eleDisplay.valueOf("@target").equals("")))
						{
							String[] strTargets=eleDisplay.valueOf("@target").split(";");
							for(int iTargetCnt=0;iTargetCnt<strTargets.length;iTargetCnt++)
							{
								String strCurrentTarget=strTargets[iTargetCnt];
								String[] strCurrentTargetParams=strCurrentTarget.split(",");
								try
								{
									int iTargetPos=Integer.parseInt(strCurrentTargetParams[1]);
										
										if(strCurrentTarget.startsWith("autoselect"))
										{
											strAutoSelectValues[iTargetPos-1]= updateTarget(strTargetValue,strAutoSelectValues,
													iTargetPos);
										}//end of  else if(strCurrentTarget.startsWith("autoselect"))
								}//end of try
								catch(ArrayIndexOutOfBoundsException e)
								{
									log.error("ArrayIndexOutOfBoundsException ......value........."+eleDisplay.valueOf("@id"));
								}//end of catch(ArrayIndexOutOfBoundsException e)
								catch(NumberFormatException e)
								{
									log.error("NumberFormatException .......value........"+eleDisplay.valueOf("@id"));
								}//end of catch(NumberFormatException e)
								catch(NullPointerException ne)
								{
									log.error("NullPointerException .......value........"+eleDisplay.valueOf("@id"));
								}//end of catch(NullPointerException ne)
							}//end of for(int iTargetCnt=0;iTargetCnt<strTargets.length;iTargetCnt++)
						}//end of if(!strTarget.equals(""))
					}//end of for(Iterator itDisplayList=alDisplay.iterator();itDisplayList.hasNext();)
					
					if(strAutoSelectValues!=null)
					{
						eleCoverage.attribute("autoselect").setValue(buildTargetFunction(strAutoSelectValue,
								strAutoSelectValues));
					}//end of if(strAutoSelectValues!=null)
				}//end of if(request.getParameter(eleCoverage.valueOf("@id"))!=null)
			}//end of for(Iterator itCoverageList=coverageList.iterator();itCoverageList.hasNext();)
		}//end of if(coverageList!=null)
	}//end of updateCoverages(List coverageList,HttpServletRequest request)
	
	/**
	 * This method will update the values defined for the condtion.
	 * If the coverage's allowed status is apply/pay  then default values will be taken
	 * If the coverage's allowed status is apply Conditionally/Pay Condtionally then user defined
	 * values will be updated.
	 * If the coverage allwoed status is Don't Apply/Don't Pay then values will be replaced with null
	 *
	 * @param eleCondition Element Condition node to be updated
	 * @param request HttpServeletRequest current request object
	 * @param strCvrgAllowed Coverage status
	 */
	private void updateCondition(Element eleCondition,Element eleCoverage,HttpServletRequest request)
	{
		List alDisplay=null;
		List alCopayResult=null;
		Element eleDisplay=null;
		Element eleCopayResult=null;
		String strCvrgAllowed=eleCoverage.valueOf("@allowed");
		
		HashMap hmDisplayNodes=(HashMap)request.getSession().getServletContext().getAttribute("RULE_DISPLAY_NODES");
		HashMap hmCopayResultNodes=(HashMap)request.getSession().getServletContext().getAttribute("RULE_COPAY_RESULT_NODES");
		
		if(hmDisplayNodes.get(eleCondition.valueOf("@id"))!=null)
		{
			alDisplay=(ArrayList)hmDisplayNodes.get(eleCondition.valueOf("@id"));
			if(!(((Element)alDisplay.get(0)).valueOf("@control").equals("")))
			{
				//update the operator field
				if(request.getParameter(eleCondition.valueOf("@id")+".operator")!=null)
				{
					eleCondition.attribute("op").setValue(request.getParameter(
							eleCondition.valueOf("@id")+".operator"));
				}//end of if(request.getParameter(eleCondition.valueOf("@id")+".operator")!=null)
				
				String[] strValues=eleCondition.valueOf("@value").split(",");
				String strDynValue=eleCondition.valueOf("@dynValue");
				String strMethodValue=eleCondition.valueOf("@method");
				String strAutoSelectValue=eleCoverage.valueOf("@autoselect");
				String[] strDynValues=null;
				String[] strMethodValues=null;
				String[] strAutoSelectValues=null;
				String strTargetValue=null;
				
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
				for(Iterator itDisplayList=alDisplay.iterator();itDisplayList.hasNext();)
				{
					eleDisplay=(Element)itDisplayList.next();
					
					if(strCvrgAllowed.equals(strApply)||strCvrgAllowed.equals(strPay))
					{
						strTargetValue= eleDisplay.valueOf("@default");
					}//end of if(strCvrgAllowed.equals(strApply)||strCvrgAllowed.equals(strPay))
					else if(strCvrgAllowed.equals(strApplyConditionally)||strCvrgAllowed.equals(strPayConditonally))
					{
						strTargetValue =TTKCommon.checkNull(request.getParameter(eleDisplay.valueOf("@id")));
					}//end of else if(strCvrgAllowed.equals(strApplyConditionally)||strCvrgAllowed.equals(strPayConditonally))
					else
					{
						strTargetValue="";
					}//end of else
					
					if(!(eleDisplay.valueOf("@target").equals("")))
					{
						String[] strTargets=eleDisplay.valueOf("@target").split(";");
						for(int iTargetCnt=0;iTargetCnt<strTargets.length;iTargetCnt++)
						{
							String strCurrentTarget=strTargets[iTargetCnt];
							String[] strCurrentTargetParams=strCurrentTarget.split(",");
							try
							{
								if(strCurrentTarget.startsWith("node"))
								{
									updateTargetNode(eleCondition,strCurrentTargetParams,strTargetValue);
								}//end of if(strCurrentTarget.startsWith("node"))
								else
								{
									int iTargetPos=Integer.parseInt(strCurrentTargetParams[1]);
									
									if(strCurrentTarget.startsWith("value"))
									{
										strValues[iTargetPos-1]=updateTarget(strTargetValue,strValues,iTargetPos);
									}//end of if(strCurrentTarget.startsWith("value"))
									else if(strCurrentTarget.startsWith("dynValue"))
									{
										strDynValues[iTargetPos-1]=updateTarget(strTargetValue,strDynValues,iTargetPos);
									}//end of else if(strCurrentTarget.startsWith("dynValue"))
									else if(strCurrentTarget.startsWith("method"))
									{
										strMethodValues[iTargetPos-1]= updateTarget(strTargetValue,strMethodValues,
												iTargetPos);
									}//end of else if(strCurrentTarget.startsWith("method"))
									else if(strCurrentTarget.startsWith("autoselect"))
									{
										strAutoSelectValues[iTargetPos-1]= updateTarget(strTargetValue,strAutoSelectValues,
												iTargetPos);
									}//end of  else if(strCurrentTarget.startsWith("autoselect"))
									
									else if(strCurrentTarget.startsWith("unit"))
									{
										if(strCvrgAllowed.equals(strApplyConditionally))
										{
											eleCondition.attribute("unit").setValue(strTargetValue);
										}//end of if(strCvrgAllowed.equals(strApplyConditionally))
										else
										{
											eleCondition.attribute("unit").setValue("");
										}//end of else
									}//end of else if(strCurrentTarget.startsWith("unit"))
								}//end of else
							}//end of try
							catch(ArrayIndexOutOfBoundsException e)
							{
								log.error("ArrayIndexOutOfBoundsException ......value........."+eleDisplay.valueOf("@id"));
							}//end of catch(ArrayIndexOutOfBoundsException e)
							catch(NumberFormatException e)
							{
								log.error("NumberFormatException .......value........"+eleDisplay.valueOf("@id"));
							}//end of catch(NumberFormatException e)
							catch(NullPointerException ne)
							{
								log.error("NullPointerException .......value........"+eleDisplay.valueOf("@id"));
							}//end of catch(NullPointerException ne)
						}//end of for(int iTargetCnt=0;iTargetCnt<strTargets.length;iTargetCnt++)
					}//end of if(!strTarget.equals(""))
				}//end of for(Iterator itDisplayList=displayList.iterator();itDisplayList.hasNext();)
				
				//update the value node
				StringBuffer sbfValue=new StringBuffer();
				for(int iValueCnt=0;iValueCnt<strValues.length;iValueCnt++)
				{
					if(iValueCnt==strValues.length-1)
					{
						sbfValue.append(strValues[iValueCnt]);
					}//end of if(iValueCnt==strValues.length-1)
					else
					{
						sbfValue.append(strValues[iValueCnt]).append(",");
					}//end of else
				}//end of for(int iValueCnt=0;iValueCnt<strValues.length;iValueCnt++)
				eleCondition.attribute("value").setValue(sbfValue.toString());
				
				if(strDynValues!=null)
				{
					eleCondition.attribute("dynValue").setValue(buildTargetFunction(strDynValue,strDynValues));
				}//end of if(strDynValues!=null)
				
				if(strMethodValues!=null)
				{
					eleCondition.attribute("method").setValue(buildTargetFunction(strMethodValue,strMethodValues));
				}//end of if(strMethodValues!=null)
				
				if(strAutoSelectValues!=null)
				{
					eleCoverage.attribute("autoselect").setValue(buildTargetFunction(strAutoSelectValue,
							strAutoSelectValues));
				}//end of if(strAutoSelectValues!=null)
			}//end of if(!(eleCondition.selectSingleNode("./display")).valueOf("@control").equals(""))
		}//end of if(hmDisplayNodes.get(eleCondition.valueOf("@id"))!=null)
		
		if(hmCopayResultNodes.get(eleCondition.valueOf("@id"))!=null)
		{
			alCopayResult=(ArrayList)hmCopayResultNodes.get(eleCondition.valueOf("@id"));
			if(!(((Element)alCopayResult.get(0)).valueOf("@control").equals("")))
			{
				//update the operator field
				if(request.getParameter(eleCondition.valueOf("@id")+".operator")!=null)
				{
					eleCondition.attribute("op").setValue(request.getParameter(
							eleCondition.valueOf("@id")+".operator"));
				}//end of if(request.getParameter(eleCondition.valueOf("@id")+".operator")!=null)
				
				String[] strValues=eleCondition.valueOf("@value").split(",");
				String strDynValue=eleCondition.valueOf("@dynValue");
				String strMethodValue=eleCondition.valueOf("@method");
				String strAutoSelectValue=eleCoverage.valueOf("@autoselect");
				String[] strDynValues=null;
				String[] strMethodValues=null;
				String[] strAutoSelectValues=null;
				String strTargetValue=null;
				
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
				for(Iterator itCopayResultList=alCopayResult.iterator();itCopayResultList.hasNext();)
				{
					eleCopayResult=(Element)itCopayResultList.next();
					
					if(strCvrgAllowed.equals(strApply)||strCvrgAllowed.equals(strPay))
					{
						strTargetValue= eleCopayResult.valueOf("@default");
					}//end of if(strCvrgAllowed.equals(strApply)||strCvrgAllowed.equals(strPay))
					else if(strCvrgAllowed.equals(strApplyConditionally)||strCvrgAllowed.equals(strPayConditonally))
					{
						strTargetValue =TTKCommon.checkNull(request.getParameter(eleCopayResult.valueOf("@id")));
					}//end of else if(strCvrgAllowed.equals(strApplyConditionally)||strCvrgAllowed.equals(strPayConditonally))
					else
					{
						strTargetValue="";
					}//end of else
					
					if(!(eleCopayResult.valueOf("@target").equals("")))
					{
						String[] strTargets=eleCopayResult.valueOf("@target").split(";");
						for(int iTargetCnt=0;iTargetCnt<strTargets.length;iTargetCnt++)
						{
							String strCurrentTarget=strTargets[iTargetCnt];
							String[] strCurrentTargetParams=strCurrentTarget.split(",");
							try
							{
								if(strCurrentTarget.startsWith("node"))
								{
									updateTargetNode(eleCondition,strCurrentTargetParams,strTargetValue);
								}//end of if(strCurrentTarget.startsWith("node"))
								else
								{
									int iTargetPos=Integer.parseInt(strCurrentTargetParams[1]);
									
									if(strCurrentTarget.startsWith("value"))
									{
										strValues[iTargetPos-1]=updateTarget(strTargetValue,strValues,iTargetPos);
									}//end of if(strCurrentTarget.startsWith("value"))
									else if(strCurrentTarget.startsWith("dynValue"))
									{
										strDynValues[iTargetPos-1]=updateTarget(strTargetValue,strDynValues,iTargetPos);
									}//end of else if(strCurrentTarget.startsWith("dynValue"))
									else if(strCurrentTarget.startsWith("method"))
									{
										strMethodValues[iTargetPos-1]= updateTarget(strTargetValue,strMethodValues,
												iTargetPos);
									}//end of else if(strCurrentTarget.startsWith("method"))
									else if(strCurrentTarget.startsWith("autoselect"))
									{
										strAutoSelectValues[iTargetPos-1]= updateTarget(strTargetValue,strAutoSelectValues,
												iTargetPos);
									}//end of  else if(strCurrentTarget.startsWith("autoselect"))
									
									else if(strCurrentTarget.startsWith("unit"))
									{
										if(strCvrgAllowed.equals(strApplyConditionally))
										{
											eleCondition.attribute("unit").setValue(strTargetValue);
										}//end of if(strCvrgAllowed.equals(strApplyConditionally))
										else
										{
											eleCondition.attribute("unit").setValue("");
										}//end of else
									}//end of else if(strCurrentTarget.startsWith("unit"))
								}//end of else
							}//end of try
							catch(ArrayIndexOutOfBoundsException e)
							{
								log.error("ArrayIndexOutOfBoundsException ......value........."+eleCopayResult.valueOf("@id"));
							}//end of catch(ArrayIndexOutOfBoundsException e)
							catch(NumberFormatException e)
							{
								log.error("NumberFormatException .......value........"+eleCopayResult.valueOf("@id"));
							}//end of catch(NumberFormatException e)
							catch(NullPointerException ne)
							{
								log.error("NullPointerException .......value........"+eleCopayResult.valueOf("@id"));
							}//end of catch(NullPointerException ne)
						}//end of for(int iTargetCnt=0;iTargetCnt<strTargets.length;iTargetCnt++)
					}//end of if(!strTarget.equals(""))
				}//end of for(Iterator itDisplayList=displayList.iterator();itDisplayList.hasNext();)
				
				//update the value node
				StringBuffer sbfValue=new StringBuffer();
				for(int iValueCnt=0;iValueCnt<strValues.length;iValueCnt++)
				{
					if(iValueCnt==strValues.length-1)
					{
						sbfValue.append(strValues[iValueCnt]);
					}//end of if(iValueCnt==strValues.length-1)
					else
					{
						sbfValue.append(strValues[iValueCnt]).append(",");
					}//end of else
				}//end of for(int iValueCnt=0;iValueCnt<strValues.length;iValueCnt++)
				eleCondition.attribute("value").setValue(sbfValue.toString());
				
				if(strDynValues!=null)
				{
					eleCondition.attribute("dynValue").setValue(buildTargetFunction(strDynValue,strDynValues));
				}//end of if(strDynValues!=null)
				
				if(strMethodValues!=null)
				{
					eleCondition.attribute("method").setValue(buildTargetFunction(strMethodValue,strMethodValues));
				}//end of if(strMethodValues!=null)
				
				if(strAutoSelectValues!=null)
				{
					eleCoverage.attribute("autoselect").setValue(buildTargetFunction(strAutoSelectValue,
							strAutoSelectValues));
				}//end of if(strAutoSelectValues!=null)
			}//end of if(!(((Element)alCopayResult.get(0)).valueOf("@control").equals("")))
		}//end of if(hmCopayResultNodes.get(eleCondition.valueOf("@id"))!=null)
	}//end of updateCondition(Element eleCondition,Element eleCoverage,HttpServletRequest request)
	
	private String updateTarget(String strTargetValue,String [] strUpdateTarget,
			int iTargetPos) throws ArrayIndexOutOfBoundsException
			{
		String strResult="";
		if(strUpdateTarget!=null)
		{
			if(strUpdateTarget[iTargetPos-1].startsWith("'"))
			{
				strResult= "'"+strTargetValue+"'";
			}//end of if(strUpdateTarget[iTargetPos-1].startsWith("'"))
			else
			{
				strResult= strTargetValue.equals("")? "null":strTargetValue;
			}//end of else
		}//end of if(strUpdateTarget!=null)
		return strResult;
	}//end of updateTarget(String strTargetValue,String [] strUpdateTarget,int iTargetPos)
	
	/**
	 * This method is used build the target function after updating the function parameters with
	 * the User entered values.
	 *
	 * @param strTargetValue String current value of target functions
	 * @param strTargetValues String[] Array of values to be updated in target function
	 * @return sbfTargetFunction String builded target function
	 */
	private String buildTargetFunction(String strTargetValue,String[] strTargetValues)
	{
		StringBuffer sbfTargetFunction=new StringBuffer();
		sbfTargetFunction.append(strTargetValue.substring(0,strTargetValue.indexOf("(")+1));
		for(int iTargetValCnt=0;iTargetValCnt<strTargetValues.length;iTargetValCnt++)
		{
			if(iTargetValCnt==strTargetValues.length-1)
			{
				sbfTargetFunction.append(strTargetValues[iTargetValCnt]);
			}//end of if(iTargetValCnt==strTargetValues.length-1)
			else
			{
				sbfTargetFunction.append(strTargetValues[iTargetValCnt]).append(",");
			}//end of else
		}//end of for(int iTargetValCnt=0;iTargetValCnt<strTargetValues.length;iTargetValCnt++)
		sbfTargetFunction.append(")");
		return sbfTargetFunction.toString();
	}//end of buildTargetFunction(String strTargetValue,String[] strTargetValues)
	
	/**
	 * This method is used to update the attribute value of an node in the rules.
	 * @param eleCondition Element current processing node
	 * @param strTargetParams String Array of string containing the target node information
	 * @param strTargetvalue value to be updated in the xml.
	 */
	private void updateTargetNode(Element eleCondition,String[] strTargetParams,String strTargetvalue)
	{
		Element targetNode=null;
		try
		{
			if(eleCondition.getDocument()!=null)
			{
				if(eleCondition.getDocument().selectSingleNode("//*[@id='"+strTargetParams[1]+"']")==null)
				{
					return;
				}//end of if(eleCondition.getDocument().selectSingleNode("//*[@id='"+strTargetParams[1]+"']")==null)
				targetNode= (Element)eleCondition.getDocument().selectSingleNode("//*[@id='"+strTargetParams[1]+"']");
				String strTargetAttribute=targetNode.valueOf("@"+strTargetParams[2]+"");
				
				if("value".equals(strTargetParams[2]))
				{
					String[] strTargetAttrValues=strTargetAttribute.split(",");
					int iTargetPos=Integer.parseInt(strTargetParams[3]);
					strTargetAttrValues[iTargetPos-1]=updateTarget(strTargetvalue,strTargetAttrValues,iTargetPos);
					
					//update the value node
					StringBuffer sbfValue=new StringBuffer();
					for(int iValueCnt=0;iValueCnt<strTargetAttrValues.length;iValueCnt++)
					{
						if(iValueCnt==strTargetAttrValues.length-1)
						{
							sbfValue.append(strTargetAttrValues[iValueCnt]);
						}//end of if(iValueCnt==strTargetAttrValues.length-1)
						else
						{
							sbfValue.append(strTargetAttrValues[iValueCnt]).append(",");
						}//end of else
					}//end of for(int iValueCnt=0;iValueCnt<strValues.length;iValueCnt++)
					targetNode.addAttribute(strTargetParams[2],sbfValue.toString());
				}//end of if("value".equals(strTargetParams[2]))
				else
				{
					if(strTargetAttribute.indexOf("(")!=-1)
					{
						String[] strTargetAttrValues=(strTargetAttribute.substring(strTargetAttribute.indexOf("(")+1,
								strTargetAttribute.lastIndexOf(")"))).split(",");
						int iTargetPos=Integer.parseInt(strTargetParams[3]);
						strTargetAttrValues[iTargetPos-1]=updateTarget(strTargetvalue,strTargetAttrValues,iTargetPos);
						
						targetNode.addAttribute(strTargetParams[2],buildTargetFunction(strTargetAttribute,
								strTargetAttrValues));
						
					}//end of if(strTargetAttribute.indexOf("(")!=-1)
				}//end of else
			}//end of if(eleCondition.getDocument()!=null)
		}//end of try
		catch(ArrayIndexOutOfBoundsException e)
		{
			log.error("ArrayIndexOutOfBoundsException ......value........."+eleCondition.valueOf("@id"));
		}//end of catch(ArrayIndexOutOfBoundsException e)
		catch(Exception e)
		{
			log.error("Error while updating the target node from ......."+eleCondition.valueOf("@id"));
		}//end of catch(Exception e)
	}//end of updateTargetNode(Element eleCondition,String[] strTargetParams,String strTargetvalue)
	
	private void updateText(Element eleText,HttpServletRequest request)
	{
		HashMap hmDisplayNodes=(HashMap)request.getSession().getServletContext().getAttribute("RULE_DISPLAY_NODES");
		if(hmDisplayNodes.get(eleText.valueOf("@id"))!=null)
		{
			Element eleDisplay= (Element)((ArrayList)hmDisplayNodes.get(eleText.valueOf("@id"))).get(0);
			// UNNI Added for fixing Bug ID 42647
			eleText.addAttribute("value",TTKCommon.checkNull(request.getParameter(eleDisplay.valueOf("@id"))).replaceAll("\n", "~"));
			// End of Addition
		}//end of if(hmDisplayNodes.get(eleText.valueOf("@id"))!=null)
	}//end of updateText(Element eleText,HttpServletRequest request)
	
	/**
	 * This method is used to get the Rules to be defined at Family or Member level.
	 * It removes the unwanted clauses, coverages and conditions from the Xml Document
	 * and returns the document with Applicable Rules to be defined at that level
	 *
	 * @param doc  Xml Document to be processed
	 * @param strIdentifier String Flow identfier
	 * @return doc Document Processed document.
	 */
	public Document getFamilyMemberRuleDocument(Document doc,String strIdentifier)
	{
		Element eleCoverage=null;
		Element eleCondition=null;
		Element eleClause=null;
		String strCondXpath="";
		String strCoverageIdentifier="";
		
		if(strIdentifier.equals("FamilyRule"))
		{
			strCondXpath="./condition[@module!='E']";   //Xpath for removing conditions other than Family Level
			strCoverageIdentifier="E";
		}//end of if(strIdentifier.equals("FamilyRule"))
		else if(strIdentifier.equals("MemberRule"))
		{
			strCondXpath="./condition[@module!='M']";   //Xpath for removing conditions other than Member Level
			strCoverageIdentifier="M";
		}//end of else if(strIdentifier.equals("MemberRule"))
		else
		{
			return doc;
		}//end of else
		
		if(doc!=null)
		{
			List coverageList=doc.selectNodes("//coverage");
			if(coverageList!=null)
			{
				for(int iCoverageCnt=0;iCoverageCnt<coverageList.size();iCoverageCnt++)
				{
					eleCoverage=(Element)coverageList.get(iCoverageCnt);
					if(!(eleCoverage.valueOf("@allowed").equalsIgnoreCase(strPayConditonally) ||eleCoverage.valueOf("@allowed").equalsIgnoreCase(strApplyConditionally)))
					{
						eleCoverage.detach();       //Remove the Coverage if it is not allowed
					}//end of if(!(eleCoverage.valueOf("@allowed").equalsIgnoreCase("YES")))
					else if(!(eleCoverage.valueOf("@module").contains(strCoverageIdentifier)))
					{
						eleCoverage.detach();   //if no rules are present to define at level remove the coverage
					}//end of else if(!(eleCoverage.valueOf("@module").contains("E")))
					else
					{
						List conditionList=eleCoverage.selectNodes(strCondXpath);
						if(conditionList!=null && conditionList.size()>0)
						{
							for(int iConditionCnt=0;iConditionCnt<conditionList.size();iConditionCnt++)
							{
								eleCondition=(Element)conditionList.get(iConditionCnt);
								eleCondition.detach();
							}//end of for(int iConditionCnt=0;iConditionCnt<conditionList.size();iConditionCnt++)
						}//end of if(conditionList!=null && conditionList.size()>0)
						
						if(eleCoverage.selectNodes("./condition").size()<=0)
						{
							log.info("Coverage is not having any definable conditions....."+eleCoverage.valueOf("@id"));
							eleCoverage.detach();
						}//end of if(eleCoverage.selectNodes("./condition").size()<=0)
					}//end of else
				}//end of  for(int iCoverageCnt=0;iCoverageCnt<coverageList.size();iCoverageCnt++)
			}//end of if(coverageList!=null)
			doc.normalize();
			
			List clauseList=doc.selectNodes("//clause");
			if(clauseList!=null)
			{
				for(int iClauseCnt=0;iClauseCnt<clauseList.size();iClauseCnt++)
				{
					eleClause=(Element)clauseList.get(iClauseCnt);
					if(eleClause.selectNodes("*").size()<=0)
					{
						eleClause.detach();
					}//end of if(eleClause.selectNodes("*").size()<=0)
				}//end of for(int iClauseCnt=0;iClauseCnt<clauseList.size();iClauseCnt++)
			}//end of if(clauseList!=null)
			doc.normalize();
		}//end of if(doc!=null)
		return doc;
	}//end of getFamilyMemberRuleDocument(Document doc,String strIdentifier)
	
	/**
	 * This method is used to remove the unselected Rules during PreAuth/Claims flow
	 * after User clicks on Initiate check to execute the applicable rules.
	 *
	 * @param ruleDataDocument Dom Object
	 * @return ruleDataDocument updated XML DOM object
	 */
	public Document removeRules(Document ruleDataDocument)
	{
		if(ruleDataDocument!=null)
		{
			//remove the unselected coverages from the rule
			List coverageList =ruleDataDocument.selectNodes("//clause/coverage[@selected!='YES']");
			if(coverageList!=null)
			{
				Element eleCoverage=null;
				for(Iterator itCoverage=coverageList.iterator();itCoverage.hasNext();)
				{
					eleCoverage=(Element)itCoverage.next();
					eleCoverage.detach();
				}//end of for(Iterator itCoverage=coverageList.iterator();itCoverage.hasNext();)
			}//end of if(coverageList!=null)
			
			ruleDataDocument.normalize();
			//remove the clauses for which even a single coverage is not selected
			List clauseList=ruleDataDocument.selectNodes("//clause");
			if(clauseList!=null && clauseList.size()>0)
			{
				for(int iClauseCnt=0;iClauseCnt<clauseList.size();iClauseCnt++)
				{
					Element eleClause=(Element)clauseList.get(iClauseCnt);
					if(eleClause.selectSingleNode("./coverage[@selected='YES']")==null)
					{
						eleClause.detach();
					}//end of if(eleClause.selectSingleNode("./coverage[@selected='YES']")==null)
				}//end of for(int iClauseCnt=0;iClauseCnt<ruleClauseList.size();iClauseCnt++)
			}//end of if(clauseList!=null && clauseList.size()>0)
			ruleDataDocument.normalize();
		}//end of if(ruleDataDocument!=null)
		return ruleDataDocument;
	}//end of removeRules(Document ruleDataDocument)
	
	/**
	 * This method sorts the dom4j document based on 'name' attribute and returns the sorted document
	 * @author Unni V Mana
	 *
	 * @param dom4j Document Document to be sorted
	 * @return dom4j Document sorted document
	 */
	public Document sortDocument(Document doc)
	{
		List list = doc.selectNodes("/clauses/clause");
		DocumentHelper.sort(list,"@name");
		StringBuffer sbStrXml = new StringBuffer();
		sbStrXml.append("<clauses>");
		for(int i=0; i<list.size();i++)
		{
			Element element = (Element) list.get(i);
			sbStrXml.append(element.asXML());
		}//end of for(int i=0; i<list.size();i++)
		sbStrXml.append("</clauses>");
		Document document = null;
		try{
			//constructing the xml document with these selected clauses
			document = DocumentHelper.parseText(sbStrXml.toString());
		}//end of try
		catch(Exception e)
		{
			e.printStackTrace();
		}//end of catch(Exception e)
		return document ;
	}//end of sortDocument(Document doc)
	
	/**
	 * This method is used to remove elements from the xml document
	 * @param doc
	 * @param selectedItem
	 * @return Document type
	 */
	public static Document removeElement(Document doc,Object [] selectedItem,HttpServletRequest request)
	{
		List listSection = doc.selectNodes("/shortfall/section");
		boolean found=false;
		for(int i=0; i<listSection.size();i++)
		{
			Element elmtSection = (Element)listSection.get(i);
			/*    if(! elmtSection.valueOf("@name").equalsIgnoreCase("others"))
			 {*/
			List listTitle =null;
			if(elmtSection.selectNodes("*").size()>0)
				listTitle = doc.selectNodes("/shortfall/section[@name='"+elmtSection.valueOf("@name")+"']/"+((Element) elmtSection.selectNodes("*").get(0)).getName()+" | /shortfall/section[@name='Others']/subsection");
			if(listTitle!=null)
				for(int j=0;j<listTitle.size();j++)
				{
					Element elmtSubSection = (Element)listTitle.get(j);
					// getting all query element
					List listQueries = elmtSubSection.selectNodes("*");
					for(int k=0;k<listQueries.size();k++)
					{
						// getting the query element
						Element elemtQuery =(Element) listQueries.get(k);
						String strID = elemtQuery.valueOf("@id");
						// now iterating over the array
						found=false;
						for(int l=0; l<selectedItem.length; l++)
						{
							if(strID.equalsIgnoreCase((String)selectedItem[l]))
							{
								elemtQuery.addAttribute("value",((String)request.getParameter(strID)).replaceAll("\n","</br>"));
								found=true;
								break;
							}//end of if(strID.equalsIgnoreCase((String)selectedItem[l]))
						}//end for
						if(!found)
						{
							elemtQuery.detach();
						}//end of if(!found)
					}//end for
					if(elmtSubSection.selectNodes("*").size()<=0)
					{
						elmtSubSection.detach();
					}//end of if(elmtSubSection.selectNodes("*").size()<=0)
				} //end for
			if(elmtSection.selectNodes("*").size()<=0)
			{
				elmtSection.detach();
			}//end of if(elmtSection.selectNodes("*").size()<=0)
			//}
		}//end for
		doc.normalize();
		return doc;
	}//end of removeElement(Document doc,Object [] selectedItem,HttpServletRequest request)
	// added for Shortfall CR KOC1179
	public static ArrayList getSelectedNodesQuery(Document validationDoc ,String strShortFallType) throws TTKException
	{
		
		log.info("In getSelectedNodes method strShortFallType in selectNodes "+strShortFallType);
		ArrayList<Object> alComponetValue=new ArrayList<Object>();
		HashMap<Object,Object> hMap = new HashMap<Object,Object>();
		try{
			if(validationDoc!=null)
			{
				//select the condition nodes from the validaiton Rules
				List displayList=validationDoc.selectNodes("/shortfall/section[@name='"+strShortFallType+"']/subsection | /shortfall/section[@name='Others']/subsection");
				for(int iTitle=0;iTitle<displayList.size();iTitle++)
				{
					Element element = (Element)displayList.get(iTitle);
					List listQueries = element.selectNodes("//query");
				//	
					for(int iQuerie=0;iQuerie<listQueries.size();iQuerie++)
					{
						Element elementquerie = (Element)listQueries.get(iQuerie);
					//	
					//	
						
						alComponetValue.add((String)elementquerie.attributeValue("id"));
						alComponetValue.add((String)elementquerie.attributeValue("enable"));
						
						if("Others.1".equals((String)elementquerie.attributeValue("id"))) {
							
							hMap.put((String)elementquerie.attributeValue("id"),(String)elementquerie.attributeValue("value"));
							alComponetValue.add(hMap);
						}//end of if("Others.1".equals((String)elementquerie.attributeValue("id")))
						
						
					}//end of for(int iQuerie=0;iQuerie<listQueries.size();iQuerie++)
				}//end of for(int iTitle=0;iTitle<displayList.size();iTitle++)
			}//end of if(validationDoc!=null)
		}//end of try block
		catch(Exception exp)
		{
			exp.printStackTrace();
			log.debug("error occured in PolicyHistory Tag Library!!!!! ");
		}//end of catch block
		//log.info("alComponetValue "+alComponetValue.size()+" "+alComponetValue);
		return alComponetValue;
	}//end of getSelectedNodes(Document validationDoc ,String strShortFallType)
	
	// added for Shortfall CR KOC1179
	public static ArrayList getSelectedNodesDisplay(Document validationDoc ,String strShortFallType) throws TTKException
	{
		
		log.info("In getSelectedNodes method strShortFallType in selectNodes "+strShortFallType);
		ArrayList<Object> alComponetValue=new ArrayList<Object>();
	//	HashMap<Object,Object> hMap = new HashMap<Object,Object>();
		try{
			if(validationDoc!=null)
			{
				//select the condition nodes from the validaiton Rules
				List displayList=validationDoc.selectNodes("/shortfall/section[@name='"+strShortFallType+"']/subsection | /shortfall/section[@name='Others']/subsection");
			//	
				for(int iTitle=0;iTitle<displayList.size();iTitle++)
				{
					Element element = (Element)displayList.get(iTitle);
					List listQueries = element.selectNodes("//display");
				//	
					for(int iQuerie=0;iQuerie<listQueries.size();iQuerie++)
					{
						HashMap<Object,Object> hMap = new HashMap<Object,Object>();
						Element elementquerie = (Element)listQueries.get(iQuerie);
						/*
						
						
						*/
						hMap.put("id",(String)elementquerie.attributeValue("id"));
						hMap.put("enable",(String)elementquerie.attributeValue("enable"));
						hMap.put("received",(String)elementquerie.attributeValue("received"));
						alComponetValue.add(hMap);
						}//end of for(int iQuerie=0;iQuerie<listQueries.size();iQuerie++)
					
				}//end of for(int iTitle=0;iTitle<displayList.size();iTitle++)
			}//end of if(validationDoc!=null)
		}//end of try block
		catch(Exception exp)
		{
			exp.printStackTrace();
			log.debug("error occured in PolicyHistory Tag Library!!!!! ");
		}//end of catch block
		//log.info("alComponetValue "+alComponetValue.size()+" "+alComponetValue);
		return alComponetValue;
	}//end of getSelectedNodes(Document validationDoc ,String strShortFallType)
	
	// Shortfall CR KOC1179
	/**
	 * This method is used to remove elements from the xml document
	 * @param doc
	 * @param selectedItem
	 * @return Document type
	 */
	public static Document removeElementClaims(Document doc,Object [] selectedItem,HttpServletRequest request)
	{
		
		List listSection = doc.selectNodes("/shortfall/section");
		boolean found=false;
		for(int i=0; i<listSection.size();i++)
		{
			Element elmtSection = (Element)listSection.get(i);
			List listTitle =null;
			if(elmtSection.selectNodes("*").size()>0)	
				listTitle = doc.selectNodes("/shortfall/section[@name='"+elmtSection.valueOf("@name")+"']/"+((Element) elmtSection.selectNodes("*").get(0)).getName()+" | /shortfall/section[@name='Others']/subsection");		
			if(listTitle!=null)
				for(int j=0;j<listTitle.size();j++)
				{
					Element elmtSubSection = (Element)listTitle.get(j);
					// getting all query element
					List listQueries = elmtSubSection.selectNodes("//query");
					List listDisplay = elmtSubSection.selectNodes("//display");
					
					int count=listQueries.size()+listDisplay.size();
					for(int k=0;k<listQueries.size();k++)
					{	
						// getting the query element
					Element elemtQuery =(Element) listQueries.get(k);
						String strID = elemtQuery.valueOf("@id");
					
						if(strID.equalsIgnoreCase("Others.1"))
						{
							// now iterating over the array
							found=false;
							for(int l=0; l<selectedItem.length; l++)
							{
								if(strID.equalsIgnoreCase((String)selectedItem[l]))
								{
									elemtQuery.addAttribute("value",((String)request.getParameter(strID)).replaceAll("\n","</br>"));
									found=true;
									break;
								}//end of if(strID.equalsIgnoreCase((String)selectedItem[l]))
							}//end for
							if(!found)
							{
								elemtQuery.detach();
								
								
							
							}//end of if(!found)
							
						}
						else
						{
						// getting the display element
						Element elemtDisplay =(Element) listDisplay.get(k);
						String strIDDisplay = elemtDisplay.valueOf("@id");
					
						found=false;
						for(int l=0; l<selectedItem.length; l++)
						{
							
							if(strID.equalsIgnoreCase((String)selectedItem[l]))
							{
								elemtQuery.addAttribute("value",((String)request.getParameter(strID)).replaceAll("\n","</br>"));
								elemtDisplay.addAttribute("enable",((String)request.getParameter(strID)).replaceAll("\n","</br>"));
								found=true;
								break;
							}
			
						}//end for
						if(!found)
						{
							elemtQuery.detach();
							
											
							
						}//end of if(!found)
						}
					}
					for(int k=0;k<listDisplay.size();k++)
					{	
						// getting the query element
						Element elemtQuery =(Element) listQueries.get(k);
						String strID = elemtQuery.valueOf("@id");
						
						// getting the display element
						Element elemtDisplay =(Element) listDisplay.get(k);
						String strIDDisplay = elemtDisplay.valueOf("@id");
				
						found=false;
						for(int l=0; l<selectedItem.length; l++)
						{	
						
							if(strID.equalsIgnoreCase((String)selectedItem[l]))
							{
								for(int p=0; p<selectedItem.length;p++)
								{
									if(strIDDisplay.equalsIgnoreCase((String)selectedItem[p]))
									{
										elemtDisplay.addAttribute("value",((String)request.getParameter(strID)).replaceAll("\n","</br>"));
										elemtDisplay.addAttribute("enable",((String)request.getParameter(strID)).replaceAll("\n","</br>"));	
										elemtQuery.addAttribute("received",((String)request.getParameter(strIDDisplay)).replaceAll("\n","</br>"));	
										elemtDisplay.addAttribute("received","RECEIVED".replaceAll("\n","</br>"));								
									}
								}
								found=true;
								break;				
							}
	
						}				
						if(!found)
						{
							elemtDisplay.detach();	
						}//end of if(!found)
	
					}
					if(elmtSubSection.selectNodes("*").size()<=0)
					{
						elmtSubSection.detach();
					}//end of if(elmtSubSection.selectNodes("*").size()<=0)
				} //end for
			if(elmtSection.selectNodes("*").size()<=0)
			{
				elmtSection.detach();
			}//end of if(elmtSection.selectNodes("*").size()<=0)
			//}
		}//end for
	
		doc.normalize();
		return doc;
	}//end of removeElementClaims(Document doc,Object [] selectedItem,HttpServletRequest request)
	
	/**
	 * This method used to create new xml document
	 *
	 * @param doc
	 * @param alClauseNode
	 * @return Document type
	 */
	public Document createNewDocument(Document doc,ArrayList alClauseNode)
	{
		Document document = null;
		StringBuffer sbStrXml = new StringBuffer();
		sbStrXml.append("<clauses>");
		//Element elmtConfigurtation =(Element) doc.selectSingleNode("/clauses/configuration");
		//sbStrXml.append(elmtConfigurtation.asXML());
		for(int i=0 ; i<alClauseNode.size(); i++)
		{
			Element element = (Element) alClauseNode.get(i);
			sbStrXml.append(element.asXML());
		}//end of for(int i=0 ; i<alClauseNode.size(); i++)
		sbStrXml.append("</clauses>");
		try{
			//constructing the xml document with these selected clauses
			document = DocumentHelper.parseText(sbStrXml.toString());
		}catch(Exception e)
		{
		}
		return document;
	}//end of createNewDocument(Document doc,ArrayList alClauseNode)
	
	/**
	 * This method is used to have all the display nodes present in the XML file
	 * as HashMap.
	 *
	 * @param baseRuleDoc Document from which display nodes will be loaded
	 * @return hmDisplayNodes HashMap of display nodes
	 * @throws TTKException
	 */
	public HashMap loadDisplayNodes(Document baseRuleDoc) throws TTKException
	{
		log.info("Inside loadDisplayNodes method-- Constructing Hashmap");
		HashMap<Object,Object> hmDisplayNodes=null;
		List coverageList=null;
		List condtionList=null;
		List textList=null;
		Element eleCoverage=null;
		Element eleCondition=null;
		Element eleText=null;
		if(baseRuleDoc!=null)
		{
			hmDisplayNodes=new HashMap<Object,Object>();
			
			//put the displayNodes of the coverages to Hash map
			
			if(baseRuleDoc.selectSingleNode("//coverage")!=null)
			{
				coverageList=baseRuleDoc.selectNodes("//coverage");
				Iterator itCoverage=coverageList.iterator();
				while(itCoverage.hasNext())
				{
					eleCoverage=(Element)itCoverage.next();
					hmDisplayNodes.put(eleCoverage.valueOf("@id"),getDisplayNodes(eleCoverage.selectNodes("./display")));
				}//end of while(itCoverage.hasNext())
			}//end of if(baseRuleDoc.selectSingleNode("//coverage")!=null)
			
			if(baseRuleDoc.selectSingleNode("//condition")!=null)
			{
				condtionList=baseRuleDoc.selectNodes("//condition");
				Iterator itCondition=condtionList.iterator();
				while(itCondition.hasNext())
				{
					eleCondition=(Element)itCondition.next();
					hmDisplayNodes.put(eleCondition.valueOf("@id"),getDisplayNodes(eleCondition.selectNodes("./display")));
				}//end of while(itCondition.hasNext())
			}//end of if(baseRuleDoc.selectSingleNode("//condition")!=null)
			
			if(baseRuleDoc.selectSingleNode("//text")!=null)
			{
				textList=baseRuleDoc.selectNodes("//text");
				Iterator itText=textList.iterator();
				while(itText.hasNext())
				{
					eleText=(Element)itText.next();
					hmDisplayNodes.put(eleText.valueOf("@id"),getDisplayNodes(eleText.selectNodes("./display")));
				}//end of while(itCondition.hasNext())
			}//end of if(baseRuleDoc.selectSingleNode("//text")!=null)
		}//end of if(baseRuleDoc!=null)
		return hmDisplayNodes;
	}//end of loadDisplayNodes(Document baseRuleDoc)
	
	/**
	 * This method is used to have all the display nodes present in the XML file
	 * as HashMap.
	 *
	 * @param baseRuleDoc Document from which display nodes will be loaded
	 * @return hmDisplayNodes HashMap of display nodes
	 * @throws TTKException
	 */
	public HashMap loadCopayResultNodes(Document baseRuleDoc) throws TTKException
	{
		log.info("Inside loadCopayResultNodes method-- Constructing Hashmap");
		HashMap<Object,Object> hmCopayResultNodes=null;
		List condtionList=null;
		Element eleCondition=null;
		if(baseRuleDoc!=null)
		{
			hmCopayResultNodes=new HashMap<Object,Object>();
			//put the displayNodes of the coverages to Hash map
			
			if(baseRuleDoc.selectSingleNode("//condition")!=null)
			{
				condtionList=baseRuleDoc.selectNodes("//condition");
				Iterator itCondition=condtionList.iterator();
				while(itCondition.hasNext())
				{
					eleCondition=(Element)itCondition.next();
					hmCopayResultNodes.put(eleCondition.valueOf("@id"),getDisplayNodes(eleCondition.selectNodes("./copayresult")));
				}//end of while(itCondition.hasNext())
			}//end of if(baseRuleDoc.selectSingleNode("//condition")!=null)
			
		}//end of if(baseRuleDoc!=null)
		return hmCopayResultNodes;
	}//end of loadCopayResultNodes(Document baseRuleDoc)
	
	/**
	 * This method is used have the given display nodes in the ArrayList
	 * after removing them from the xml file.
	 *
	 * @param displayList List of Display nodes
	 * @return alDisplayNodes of display nodes
	 */
	private ArrayList getDisplayNodes(List displayList)
	{
		ArrayList<Object> alDisplayNodes=null;
		Element eleDisplay=null;
		if(displayList!=null && displayList.size()>0)
		{
			alDisplayNodes=new ArrayList<Object>();
			Iterator itDisplayNodes=displayList.iterator();
			while(itDisplayNodes.hasNext())
			{
				eleDisplay=(Element)itDisplayNodes.next();
				eleDisplay.detach();
				alDisplayNodes.add(eleDisplay);
			}//end of  while(itDisplayNodes.hasNext())
		}//end of if(displayList!=null)
		return alDisplayNodes;
	}//end of getDisplayNodes(List displayList)
	
	/**
	 * This method is used add the display nodes from Master base rules to the validation Rules before
	 * validating them against the Rule Engine.
	 *
	 * @param validationRuleDoc Document validation rule xml
	 * @param baseRuleDoc Document Masterbase rule xml
	 * @return validationRuleDoc Document updated validation rule xml
	 * @throws TTKException
	 */
	public Document mergeDisplayNodes(Document validationRuleDoc,Document baseRuleDoc)throws TTKException
	{
		if(validationRuleDoc!=null && baseRuleDoc!=null)
		{
			//select the condition nodes from the validaiton Rules
			List conditionList=validationRuleDoc.selectNodes("//condition");
			Element eleCondition=null;
			List displayList=null;
			List copayResultList=null;
			Element eleDisplay=null;
			Element eleCopayResult=null;
			if(conditionList!=null)
			{
				for(Iterator itConditionList=conditionList.iterator();itConditionList.hasNext();)
				{
					eleCondition=(Element)itConditionList.next();
					
					//get the display nodes to be merged from baseRuleDoc
					displayList=baseRuleDoc.selectNodes("//condition[@id='"+eleCondition.valueOf("@id")+"']/display");
					copayResultList=baseRuleDoc.selectNodes("//condition[@id='"+eleCondition.valueOf("@id")+"']/copayresult");
					if(displayList!=null)
					{
						for(Iterator itDisplayList=displayList.iterator();itDisplayList.hasNext();)
						{
							eleDisplay=(Element)itDisplayList.next();
							eleCondition.add(eleDisplay.createCopy());
						}//end of for(Iterator itDisplayList=displayList.iterator();itDisplayList.hasNext();)
					}//end of if(displayList!=null)
					if(copayResultList!=null)
					{
						for(Iterator itCopayResultList=copayResultList.iterator();itCopayResultList.hasNext();)
						{
							eleCopayResult=(Element)itCopayResultList.next();
							eleCondition.add(eleCopayResult.createCopy());
						}//end of for(Iterator itCopayResultList=copayResultList.iterator();itCopayResultList.hasNext();)
					}//end of if(copayResultList!=null)
				}//end of for(Iterator itConditionList=conditionList.iterator();itConditionList.hasNext();)
			}//end of if(conditionList!=null)
			validationRuleDoc.normalize();
		}//end of if(validationRuleDoc!=null && baseRuleDoc!=null)
		
		return validationRuleDoc;
	}//end of mergeDisplayNodes(Document validationRuleDoc,Document baseRuleDoc)
	
	public static ArrayList getSelectedNodes(Document validationDoc ,String strShortFallType) throws TTKException
	{
		log.info("In getSelectedNodes method strShortFallType in selectNodes "+strShortFallType);
		ArrayList<Object> alComponetValue=new ArrayList<Object>();
		HashMap<Object,Object> hMap = new HashMap<Object,Object>();
		try{
			if(validationDoc!=null)
			{
				//select the condition nodes from the validaiton Rules
				List displayList=validationDoc.selectNodes("/shortfall/section[@name='"+strShortFallType+"']/subsection | /shortfall/section[@name='Others']/subsection");
				for(int iTitle=0;iTitle<displayList.size();iTitle++)
				{
					Element element = (Element)displayList.get(iTitle);
					List listQueries = element.selectNodes("*");
					for(int iQuerie=0;iQuerie<listQueries.size();iQuerie++)
					{
						Element elementquerie = (Element)listQueries.get(iQuerie);
						alComponetValue.add((String)elementquerie.attributeValue("id"));
						if("Others.1".equals((String)elementquerie.attributeValue("id"))) {
							hMap.put((String)elementquerie.attributeValue("id"),(String)elementquerie.attributeValue("value"));
							alComponetValue.add(hMap);
						}//end of if("Others.1".equals((String)elementquerie.attributeValue("id")))
					}//end of for(int iQuerie=0;iQuerie<listQueries.size();iQuerie++)
				}//end of for(int iTitle=0;iTitle<displayList.size();iTitle++)
			}//end of if(validationDoc!=null)
		}//end of try block
		catch(Exception exp)
		{
			exp.printStackTrace();
			log.debug("error occured in PolicyHistory Tag Library!!!!! ");
		}//end of catch block
		//log.info("alComponetValue "+alComponetValue.size()+" "+alComponetValue);
		return alComponetValue;
	}//end of getSelectedNodes(Document validationDoc ,String strShortFallType)
	
	/**
	 * This method is used add the display nodes from Master base to the updated xml before
	 * loading in the screen.
	 *
	 * @param validationDoc Document updated xml
	 * @param baseDoc Document Masterbase xml
	 * @return validationDoc Document updated xml
	 * @throws TTKException
	 */
	public static Document MergeShortFallXml(Document validationDoc ,Document missingDocs,String strShortFallType,HttpServletRequest request)throws TTKException
	{
		Document baseDoc=null;
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			// Now use the factory to create a DOM parser.
			DocumentBuilder parser = factory.newDocumentBuilder();
			//Dom reader object
			DOMReader reader=new DOMReader();
			
			if((missingDocs!=null)&&(request.getAttribute("shortfallTypeID").equals("INM")))
			{
				baseDoc=missingDocs;
			}//end of if((missingDocs!=null)&&(request.getAttribute("shortfallTypeID").equals("INM")))
			else{
				if(TTKCommon.getActiveLink(request).equals("Pre-Authorization")){
					baseDoc=reader.read(parser.parse(new FileInputStream(new File("shortfallqueries.xml"))));
				}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
				else if(TTKCommon.getActiveLink(request).equals("Claims") && !strShortFallType.equalsIgnoreCase("Addressing Documents")){
				
					baseDoc=reader.read(parser.parse(new FileInputStream(new File("claimshortfallqueries.xml"))));
				//	
				}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
				else if(TTKCommon.getActiveLink(request).equals("Claims") && strShortFallType.equalsIgnoreCase("Addressing Documents")){
					
					baseDoc=reader.read(parser.parse(new FileInputStream(new File("claimshortfallqueriesnew.xml"))));
				//	
				}//end of if(TTKCommon.getActiveLink(request).equals("Claims") && strShortFallType.equalsIgnoreCase("Addressing Documents"))
		
			}//end of else
			if(validationDoc!=null && baseDoc!=null)
			{
				//select the condition nodes from the validaiton Rules
				List conditionList=baseDoc.selectNodes("/shortfall");
				Element eleCondition=null;
				List displayList=null;
				Element eleDisplay=null;
				if(conditionList!=null)
				{
					for(Iterator itConditionList=conditionList.iterator();itConditionList.hasNext();)
					{
						eleCondition=(Element)itConditionList.next();
						
						//get the display nodes to be merged from baseRuleDoc
						displayList=validationDoc.selectNodes("/shortfall/section[@name='"+
								strShortFallType+"']/subsection | /shortfall/section[@name='Others']/subsection");
						if(displayList!=null)
						{	
							for(Iterator itDisplayList=displayList.iterator();itDisplayList.hasNext();)
							{	
								eleDisplay=(Element)itDisplayList.next();
								eleCondition.add(eleDisplay.createCopy());
							}//end of for(Iterator itDisplayList=displayList.iterator();itDisplayList.hasNext();)
						}//end of if(displayList!=null)
					}//end of for(Iterator itConditionList=conditionList.iterator();itConditionList.hasNext();)
				}//end of if(conditionList!=null)
				baseDoc.normalize();
			}//end of if(validationDoc!=null && baseDoc!=null)
		}catch(Exception exp)
		{
			exp.printStackTrace();
		}//end of catch(Exception exp)
		return baseDoc;
	}//end of MergeShortFallXml(Document validationDoc,Document baseDoc)
	
	
	public ArrayList prepareErrorMessage(ArrayList alValidationError,HttpServletRequest request)throws TTKException
	{
		ArrayList<Object> alBusinessError=new ArrayList<Object>();
		ErrorLogVO validationErrorVO=null;
		StringBuffer sbfActionMessage=new StringBuffer();
		
		for(int i=0; i<alValidationError.size();i++)
		{
			validationErrorVO =(ErrorLogVO)alValidationError.get(i);
			sbfActionMessage= new StringBuffer();
			
			HashMap hmDisplayNodes=(HashMap)request.getSession().getServletContext().getAttribute("RULE_DISPLAY_NODES");
			Element eleDisplay= (Element)((ArrayList)hmDisplayNodes.get(validationErrorVO.getId())).get(0);
			
			sbfActionMessage.append(eleDisplay.valueOf("@prelabel")).append(" ");
			if(eleDisplay.valueOf("@lookup").equals(""))
			{
				sbfActionMessage.append(validationErrorVO.getComputedValue()).append(" ");
			}//end of if(eleDisplay.valueOf("@lookup").equals(""))
			else
			{
				StringTokenizer stFieldValues=new StringTokenizer(validationErrorVO.getComputedValue(),strDelimeter);
				while(stFieldValues.hasMoreTokens())
				{
					sbfActionMessage.append(TTKCommon.getCacheDescription(stFieldValues.nextToken(),
							eleDisplay.valueOf("@lookup")));
					if(stFieldValues.countTokens()>0)
					{
						sbfActionMessage.append(", ");
					}//end of if(stFieldValues.countTokens()>0)
				}//end of while(stFieldValues.hasMoreTokens())
				sbfActionMessage.append(" ");
			}//end of else
			
			sbfActionMessage.append(RuleConfig.getLookupText("unit",validationErrorVO.getUnit())).append(" ");
			sbfActionMessage.append(RuleConfig.getOperatorText(validationErrorVO.getOperatorType(),
					validationErrorVO.getOperator())).append(" ");
			if(eleDisplay.valueOf("@lookup").equals(""))
			{
				sbfActionMessage.append(validationErrorVO.getConfiguredValue()).append(" ");
			}//end of if(eleDisplay.valueOf("@lookup").equals(""))
			else
			{
				StringTokenizer stFieldValues=new StringTokenizer(validationErrorVO.getConfiguredValue(),
						strDelimeter);
				while(stFieldValues.hasMoreTokens())
				{
					sbfActionMessage.append(TTKCommon.getCacheDescription(stFieldValues.nextToken(),
							eleDisplay.valueOf("@lookup")));
					if(stFieldValues.countTokens()>0)
					{
						sbfActionMessage.append(", ");
					}//end of if(stFieldValues.countTokens()>0)
				}//end of while(stFieldValues.hasMoreTokens())
				sbfActionMessage.append(" ");
			}//end of else
			sbfActionMessage.append(RuleConfig.getLookupText("unit",validationErrorVO.getUnit()));
			
			alBusinessError.add(sbfActionMessage.toString());	
		}//end of for(int i=0; i<conditionList.size();i++)
		
		return alBusinessError;
	}//end of prepareErrorMessage(ArrayList alValidationError,HttpServletRequest request)
	
}//end of RuleXMLHelper.java

