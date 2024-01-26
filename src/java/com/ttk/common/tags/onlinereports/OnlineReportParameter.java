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

package com.ttk.common.tags.onlinereports;

import java.util.ArrayList;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.log4j.Logger;
import org.apache.oro.text.regex.StringSubstitution;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.DOMReader;

import com.ttk.business.onlineforms.OnlineAccessManager;
import com.ttk.common.TTKCommon;
import com.ttk.common.exception.TTKException;
import com.ttk.common.security.Cache;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.usermanagement.UserSecurityProfile;

import java.io.StringBufferInputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 *  This class builds the report parameter for generation of reports.
 */

public class OnlineReportParameter extends TagSupport{

	/**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    
	private static Logger log = Logger.getLogger( OnlineReportParameter.class ); 
	
	//Exception Message Identifier
	private static final String strFailure="failure";
	
	public int doStartTag() throws JspException{
		try
		{

			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			Document parameterDoc = (Document)pageContext.getRequest().getAttribute("parameterDoc");
			JspWriter out = pageContext.getOut();//Writer object to write the file
			List  parameterList=null;
			Element parameterElement=null;
			ArrayList alPolicyStatus=null;
			DOMReader reader=new DOMReader();
			String strMethodName="",strDisplayElement="",strMandatoryID="";
			UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)
					request.getSession().getAttribute("UserSecurityProfile"));
			
			//Changes as per Hospital Login
			String strLink=TTKCommon.getActiveLink(request);
			String strSubLink=TTKCommon.getActiveSubLink(request);
			
			
			
			
			if(!((List)parameterDoc.selectNodes("//parameter")).isEmpty())
			{
				log.info("inside break1");
				parameterList= (List)parameterDoc.selectNodes("//parameter/parameterDescription");
				out.println("<fieldset>");
				out.println("<legend>Report Parameters </legend>");
				out.print("<table border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" class=\"formContainerWeblogin\">");
				out.println("<tr>");
				/*if(strLink.equalsIgnoreCase("Online Reports"))
				{
					if(strSubLink.equalsIgnoreCase("Hospital"))
					{
						out.println("<td class=\"formLabel\">");
						out.println("Hospital Id :");
						
						out.println("</td ><td>");
						out.print("<input type=\"text\" name=\"hospseqid\"  value=\""+userSecurityProfile.getHospSeqId()+"\" readonly=\"readonly\"/>");
						out.println("</td>");
						//Long strHospitalSeqid=userSecurityProfile.getHospSeqId();
					}
					
				}*/
				
				/*
				<parameter name="Scheme No." isForPrompting="true" class="java.lang.String">
				<parameterDescription><![CDATA[<display prelabel="Scheme No." postlabel="" control="select" type="single" source="OnlinAccessManager" method="getPolicyNumber" valToBePassed="groupID" class="textBoxWeblogin textBoxMediumWeblogin" style="marginBottom" value="" mandatory="YES" jscall="" />]]></parameterDescription>
				</parameter>
				**/
				
				
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
					Document document=reader.read(parser.parse(new StringBufferInputStream(parameterElement.getText())));
					CacheObject cacheObject=null;
					OnlineAccessManager onlineAccessManagerObject=this.getOnlineAccessManagerObject();
					if(((Element)document.selectSingleNode("*")).valueOf("@source").equals("cache"))
					{
						strMethodName=((Element)document.selectSingleNode("*")).attribute("method").getValue();
						alPolicyStatus= Cache.getCacheObject(strMethodName);
						StringBuffer strOptValue=new StringBuffer("");
						StringBuffer strOptText = null;
						if(((Element)document.selectSingleNode("*")).valueOf("@list").equals("all"))
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
								strOptValue.append(",").append(cacheObject.getCacheId());
								strOptText.append(",").append(cacheObject.getCacheDesc());
							}//end of for(int i=0;i<alCategoryTypeID.size();i++)
						}//end of  if(alPolicyStatus!=null && alPolicyStatus.size()>0)
						((Element)document.selectSingleNode("*")).addAttribute("optText",strOptText.toString());
						((Element)document.selectSingleNode("*")).addAttribute("optVal",strOptValue.toString());
					}//end of if(((Element)document.selectSingleNode("*")).valueOf("@source").equals("cache"))
					
					else if(((Element)document.selectSingleNode("*")).valueOf("@source").equals("OnlinAccessManager"))
					{
						//kocb
						if(userSecurityProfile.getLoginType().equals("B"))
						{
							
							strMethodName=((Element)document.selectSingleNode("*")).attribute("method").getValue();
							
							String strUserID = userSecurityProfile.getUSER_ID();
							Long lngUserseqID = userSecurityProfile.getUSER_SEQ_ID();
							alPolicyStatus= onlineAccessManagerObject.getPolicyNumberBro(lngUserseqID,strUserID);
						}//end of if(((Element)document.selectSingleNode("*")).attribute("valToBePa
						
						else
						{
						strMethodName=((Element)document.selectSingleNode("*")).attribute("method").getValue();
						if(((Element)document.selectSingleNode("*")).attribute("valToBePassed").getValue().equals("groupID")){
							String strGroupID = userSecurityProfile.getGroupID();
							alPolicyStatus= onlineAccessManagerObject.getPolicyNumber(strGroupID);
						}//end of if(((Element)document.selectSingleNode("*")).attribute("valToBePassed").getValue().equals("groupID")){
						}
						StringBuffer strOptValue=new StringBuffer("");
						StringBuffer strOptText = null;
						if(((Element)document.selectSingleNode("*")).valueOf("@list").equals("all"))
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
								strOptValue.append(",").append(cacheObject.getCacheId());
								strOptText.append(",").append(cacheObject.getCacheDesc());
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
					out.println("<td class=\"formLabel\">");
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
				
				
				//Changes as per Hospital Login
				if(strLink.equalsIgnoreCase("Online Reports"))
				{
					if(strSubLink.equalsIgnoreCase("Hospital"))
					{
						Long strHospitalSeqid=userSecurityProfile.getHospSeqId();
						out.print("<input type=\"hidden\" name=\"hospseqid\"  value=\""+strHospitalSeqid+"\"/>");out.print("</br>");

					}
					
				}
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
	   			onlineAccessManager = (OnlineAccessManager) ctx.lookup("java:global/TTKServices/business.ejb3/OnlineAccessManagerBean!com.ttk.business.onlineforms.OnlineAccessManager");
	   			log.debug("Inside getOnlineAccessManagerObject: onlineAccessManager: " + onlineAccessManager);
	   		}//end if
	   	}//end of try
	   	catch(Exception exp)
	   	{
	   		throw new TTKException(exp, strFailure);
	   	}//end of catch
	   	return onlineAccessManager;
	   }//end of getOnlineAccessManagerObject()
}//end of class ReportParameter