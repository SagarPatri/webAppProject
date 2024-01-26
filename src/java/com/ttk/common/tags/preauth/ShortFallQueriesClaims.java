/**
 * @ (#) ShortFallQueries.java Jul 06, 2006
 * Project      : TTK HealthCare Services
 * File         : ShortFallQueries.java
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : Jul 06, 2006
 *
 * @author      : Krishna K H
 * Modified by  :
 * Modified date:
 * Reason       :
*/
// some changes as per KOC 1179
package com.ttk.common.tags.preauth;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.DOMReader;

import com.ttk.common.TTKCommon;

/**
 *  This class builds the Shortfall checkbox list in the Claims Module
 */

public class ShortFallQueriesClaims extends TagSupport{

	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	
    private static Logger log = Logger.getLogger(ShortFallQueriesClaims.class);

    public int doStartTag() throws JspException{
        try
        {

           HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
           String strShortFallType=(String)pageContext.getRequest().getAttribute("shortfalltype");
           String strActiveLink = TTKCommon.getActiveLink(request);//Pre-Authorization Claims
           JspWriter out = pageContext.getOut();//Writer object to write the file
           DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
           // Now use the factory to create a DOM parser.
           DocumentBuilder parser = factory.newDocumentBuilder();
           //Dom reader object
           DOMReader reader=new DOMReader();
           //parse the xml file
           Document baseDoc=null;
           String strMode="Add";
           String strPermision="";
          // Long lngShortFallSeqID = (Long)request.getAttribute("shortfallSeqID");
           if(request.getAttribute("shortfallSeqID")!=null)
           {
               strMode="Edit";
           }//end of if(request.getAttribute("shortfallSeqID")!=null)
           else
           {
               strMode="Add";
           }//end of else
           if(request.getAttribute("shortFallDoc")!=null)
           {
               out.println("<script>var strMode=\""+strMode+"\";</script>");
               if(request.getAttribute("shortfallSeqID")!=null)
               {
                   strPermision="";
               }//end of if(request.getAttribute("shortfallSeqID")!=null)
               baseDoc =(Document) request.getAttribute("shortFallDoc");
           }//end of if(request.getAttribute("shortFallDoc")!=null)
           else
           {
               out.println("<script>var strMode=\""+strMode+"\";</script>");
               if(strActiveLink.equals("Claims")){
            	   baseDoc=reader.read(parser.parse(new FileInputStream(new File("claimshortfallqueriesnew.xml"))));
               }//end of else if(strActiveLink.equals("Claims"))
           }//end of else

                    
          if(baseDoc==null)
           {
               return SKIP_BODY;
           }//end of if(baseDoc==null)
           
           List listTitle = baseDoc.selectNodes("/shortfall/section[@name='"+strShortFallType+"']/subsection | /shortfall/section[@name='Others']/subsection ");//"+strShortFallType.toLowerCase());
           for(int iTitle=0;iTitle<listTitle.size();iTitle++)
           {
               Element element = (Element)listTitle.get(iTitle);
               out.println("<fieldset>");
               out.println("<legend><img src=\"/ttk/images/c.gif\" alt=\"Expand\" name=\"ped"+iTitle+"\" width=\"16\" height=\"16\" align=\"top\" onClick=\"showhide('Querie"+iTitle+"','ped"+iTitle+"')\">&nbsp;"+element.attributeValue("name")+"</legend>");
               out.println("<table align=\"center\" class=\"formContainerWithoutPad\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"  id=\"Querie"+iTitle+"\" style=\"display:none;\">");

               List listQueries = element.selectNodes("*");
            
              
                       
            for(int iQuerie=0;iQuerie<listQueries.size();iQuerie++)
               {
                   Element elementquerie = (Element)listQueries.get(iQuerie);
                  
                   Element elementdisplay = (Element)listQueries.get(iQuerie);
                   String strComponetValue="";
                   String strComponetValue1="";
                   String strComponetValue2="";
                   String strComponetValue3="";
                 
                   if(!elementquerie.valueOf("@id").contains("Check"))
                   {
                	   out.println("<tr>");
                	   out.println("<td width=\"88%\" class=\"formLabel\">");
                	   if(request.getAttribute("shortfallSeqID")!=null && strMode.endsWith("Edit")){
	                	   if(request.getAttribute("MergeShortFallQuery")!=null)
	                       {
	                		   ArrayList alMergeShortFall=(ArrayList)request.getAttribute("MergeShortFallQuery");
                                                       
	                	   for(int i=0;i<alMergeShortFall.size();i++)
	                		   {
                                                                 
	                			  if(elementquerie.valueOf("@id").equals(alMergeShortFall.get(i))){
	                				   
	                					 if(!"Others.1".equals(alMergeShortFall.get(i)))   
	                				   {
	                					   strComponetValue="YES";
	                				   }
	                				   else{
	                					   HashMap hMap = new HashMap();
	                					   hMap = (HashMap)alMergeShortFall.get(i+2);
	                					   strComponetValue=(String)hMap.get(elementquerie.valueOf("@id"));
	                				   }
		                		   }
	                			 
	                		   }
	                       }
	                   }
	                   elementquerie.addAttribute("jscall","onClick='javascript:onQueryChangeClaims(this);' onblur='javascript:onQueryBlur(this);'");
                	   out.println(TTKCommon.buildDisplayElement(elementquerie,(String)elementquerie.attributeValue("id"),strComponetValue,strPermision,null));
                	   out.println(((Element)listQueries.get(iQuerie)).attributeValue("postlabel"));
                	   out.println("</td>");
                   }
                   else
                   {
                	   out.println("<td width=\"18%\" nowrap align=\"right\" class=\"formLabel\">");
                	   if(request.getAttribute("shortfallSeqID")!=null && strMode.endsWith("Edit")){
                		   if(request.getAttribute("MergeShortFallDisplay")!=null)
	                       {
	                		   ArrayList alMergeShortFall=(ArrayList)request.getAttribute("MergeShortFallDisplay");
	                		   HashMap hMap = null;
	                		   for(int i=0;i<alMergeShortFall.size();i++)
	                		   {
            					   hMap = (HashMap)alMergeShortFall.get(i);
            					   strComponetValue3=(String)hMap.get("id");
            					   if(elementquerie.valueOf("@id").equals(strComponetValue3))
            					   {
            						   strComponetValue="YES";
                					   strComponetValue1=(String)hMap.get("enable");
                					   strComponetValue2=(String)hMap.get("received");
            					   }
	                		   }
	                       }
	                   }
                	   elementquerie.addAttribute("jscall","");
                	   out.println(TTKCommon.buildDisplayElementClaims(elementquerie,(String)elementquerie.attributeValue("id"),strComponetValue,strComponetValue1,strComponetValue2,strPermision,null));
                	   out.println("</td>");
                	   out.println("</tr>");
                   }                    	  
               }
               out.println("</table>");
               out.println("</fieldset>");
           }//end of for(iQuerie=0;iQuerie;iQuerie<listQueries.size();iQuerie++)
    }//end of try block
    catch(Exception exp)
    {
        exp.printStackTrace();
        log.debug("error occured in PolicyHistory Tag Library!!!!! ");
    }//end of catch block
    return SKIP_BODY;
}//end of doStartTag()
    public int doEndTag() throws JspException {
        return EVAL_PAGE;//to process the rest of the page
    }//end doEndTag()
}//end of class ShortFallQueriesClaims