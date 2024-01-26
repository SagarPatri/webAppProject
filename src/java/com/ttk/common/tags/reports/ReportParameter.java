/**
 * @ (#) ReportParameter.java July 4, 2006
 * Project       : TTK HealthCare Services
 * File          : ReportParameter.java
 * Author        : Srikanth H M
 * Company       : Span Systems Corporation
 * Date Created  : July 4, 2006
 *
 * @author       : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.reports;

import java.util.ArrayList;
import java.util.List;
//import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.DOMReader;
import org.xml.sax.InputSource;

import com.ttk.common.TTKCommon;
import com.ttk.common.security.Cache;
import com.ttk.dto.common.CacheObject;

//import java.io.InputStream;
//import java.io.StringBufferInputStream;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 *  This class builds the report parameter for generation of reports.
 */

public class ReportParameter extends TagSupport{

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
	private static Logger log = Logger.getLogger( ReportParameter.class );
	public int doStartTag() throws JspException{
		try
		{

//			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			Document parameterDoc = (Document)pageContext.getRequest().getAttribute("parameterDoc");
			JspWriter out = pageContext.getOut();//Writer object to write the file
			List  parameterList=null;
			Element parameterElement=null;
			ArrayList alPolicyStatus=null;
			DOMReader reader=new DOMReader();
			String strMethodName="",strDisplayElement="",strMandatoryID="";
			if(!((List)parameterDoc.selectNodes("//parameter")).isEmpty())
			{
				parameterList= (List)parameterDoc.selectNodes("//parameter/parameterDescription");
				out.println("<fieldset>");
				out.println("<legend>Report Parameters </legend>");
				out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainer\">");
				out.println("<tr>");
				for(int i=0;i<parameterList.size();)
				{
                    strMandatoryID="";  //initialize the mandatory id
                    parameterElement = (Element)((Node)parameterList.get(i));
					DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					//Now use the factory to create a DOM parser (a.k.a. a DocumentBuilder)
					DocumentBuilder parser = factory.newDocumentBuilder();
					log.info("### PARAMETER ### "+parameterElement);
					log.info("### getText ### "+i+parameterElement.getText());
					//parse the xml file
					Document document=reader.read(parser.parse(new InputSource(new StringReader(parameterElement.getText()))));
					CacheObject cacheObject=null;
					if(((Element)document.selectSingleNode("*")).valueOf("@source").equals("cache"))
					{
						strMethodName=((Element)document.selectSingleNode("*")).attribute("method").getValue();
						alPolicyStatus= Cache.getCacheObject(strMethodName);
						StringBuffer strOptValue=new StringBuffer("");
						StringBuffer strOptText =new StringBuffer("");
						if(((Element)document.selectSingleNode("*")).valueOf("@list").equals("none"))
						{							
						}//end of if(((Element)document.selectSingleNode("*")).valueOf("@list").equals("none"))
						else if(((Element)document.selectSingleNode("*")).valueOf("@list").equals("all"))
						{
							 strOptText=new StringBuffer("All");
						}//end of if(((Element)document.selectSingleNode("*")).valueOf("@list").equals("all"))
						else {
						      strOptText=new StringBuffer("Select from list");
						}//end of else
						if(alPolicyStatus!=null && alPolicyStatus.size()>0)
						{
							for(int j=0;j<alPolicyStatus.size();j++)
							{
								cacheObject = (CacheObject)alPolicyStatus.get(j);
								if(((Element)document.selectSingleNode("*")).valueOf("@list").equals("none"))
								{
									 strOptValue.append(cacheObject.getCacheId()).append(",");
									 strOptText.append(cacheObject.getCacheDesc()).append(",");
								}//end of if(((Element)document.selectSingleNode("*")).valueOf("@list").equals("none"))
								else
								{
									strOptValue.append(",").append(cacheObject.getCacheId());
									strOptText.append(",").append(cacheObject.getCacheDesc());
								}//end of else
							}//end of for(int i=0;i<alCategoryTypeID.size();i++)
						}//end of  if(alPolicyStatus!=null && alPolicyStatus.size()>0)
						((Element)document.selectSingleNode("*")).addAttribute("optText",strOptText.toString());
						((Element)document.selectSingleNode("*")).addAttribute("optVal",strOptValue.toString());
					}//end of if(((Element)document.selectSingleNode("*")).valueOf("@source").equals("cache"))
					if(((Element)document.selectSingleNode("*")).valueOf("@mandatory").equals("YES"))
                    {
					    strMandatoryID="mandatory"+i;
                    }
                    strDisplayElement=TTKCommon.buildDisplayElement((Element)document.selectSingleNode("*"),TTKCommon.replaceInString(((Element)document.selectSingleNode("*")).valueOf("@prelabel")," ","_"),"","",strMandatoryID);
                    if(((Element)document.selectSingleNode("*")).valueOf("@prelabel").equals("Batch No."))
                    {	
					  out.println("<td width =\"120px\" class=\"formLabel\">" );
                    }//end of if(((Element)document.selectSingleNode("*")).valueOf("@prelabel").equals("Batch No."))
                    else
                    {
                    	out.println("<td class=\"formLabel\">");
                    }//end of else
					out.println(((Element)document.selectSingleNode("*")).valueOf("@prelabel"));
                    if(!strMandatoryID.equals(""))
                    {
                        out.println("<span class=\"mandatorySymbol\">*</span>");
                    }//end of if(!strMandatoryID.equals(""))
					out.println("</td ><td>");
						out.println(strDisplayElement);
					out.println("</td >");
					i++;
					if(i%2==0)
					{
						out.println("</tr><tr>");
					}//end of if(i%2==0)
				}//end of for(int i=0;i<parameterList.size();)
				out.println("</table></fieldset>");
			}//end of if(!((List)parameterDoc.selectNodes("//parameter")).isEmpty())
		}//end of try block
		catch(Exception exp)
		{
			exp.printStackTrace();
			log.info("error occured in ReportParameter Tag Library!!!!! ");
		}//end of catch block
		return SKIP_BODY;
	}//end of doStartTag()

	public int doEndTag() throws JspException
	{
		return EVAL_PAGE;//to process the rest of the page
	}//end doEndTag()
}//end of class ReportParameter