
/**
 * @ (#) EmpanelmentFinanceDocs.java 14th July 2015
 * Project       : Dubai TTK
 * File          : EmpanelmentFinanceDocs.java
 * Author        : Kishor kumar S H
 * Company       : RCS Technologies
 * Date Created  : 14th July 2015
 *
 * @author       : Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.finance;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ttk.common.TTKCommon;
import com.ttk.common.TTKPropertiesReader;
//import org.apache.log4j.Logger;

public class EmpanelmentFinanceDocs extends TagSupport
{

	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger( EmpanelmentFinanceDocs.class );
	public int doStartTag() throws JspException
	{
                try
                {
                    ArrayList<String> alFileUploads = (ArrayList<String>)pageContext.getSession().getAttribute("alFileUploads");
                    HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
                    String hospOrPtnr =  request.getParameter("hospOrPtr");
                    JspWriter out = pageContext.getOut();//Writer object to write the file
				   String gridOddRow="'gridOddRow'";
				   String gridEvenRow="'gridEvenRow'";
				   if(alFileUploads != null)
					{
						if(alFileUploads.size()>=1)
						{
			                out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:500px;height:auto;'");
					        out.print("<tr>");
					        out.print("<th align='center' class='gridHeader'>S.No.</th>");
					        out.print("<th align='center' class='gridHeader'>Document Uploaded</th>");
					        out.print("</tr>");
		
					        int i=0;
					        for(String file :alFileUploads){
						        out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
						        out.print("<td align='center'>"+(i+1)+"</td>");
						        out.print("<td align='left'> <a href='#' onclick='showFiles(\""+file+"\")'> "+file+" </a></td>");
						        out.print("</tr>");
						                i++;
					        }
						}else{
							 	out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:750px;height:auto;'");
						        out.print("<tr>");
						        out.print("<th align='center' class='gridHeader'>S.No.</th>");
						        out.print("<th align='left' class='gridHeader'>Document Uploaded</th>");
						        out.print("</tr>");
						        out.print("<tr>");
						        out.print("<td align='center'> Finance Documents has not Uploaded for this Hospital.</td>");
						        out.print("</tr>");
						        if("PTR".equals(hospOrPtnr))
						        	out.print("<td align='center'> Finance Documents has not Uploaded for this Partner.</td>");	
						        else
						        	out.print("<td align='center'> Finance Documents has not Uploaded for this Hospital.</td>");
						        out.print("</tr>");
				        }
					}//end of if(alFileUploads != null)
                }//end of try
                catch(Exception exp)
				{
				    exp.printStackTrace();
				}//end of catch block
            return SKIP_BODY;
	        }//end of doStartTag()
	public int doEndTag() throws JspException 
	{
		return EVAL_PAGE;//to process the rest of the page
	}//end doEndTag()
}//end of EmpanelmentFinanceDocs class