package com.ttk.common.tags.onlineforms.insuranceLogin;

/**
 * @ (#) DependentDetails.java 14th July 2015
 * Project       : Dubai TTK
 * File          : DependentDetails.java
 * Author        : Kishor kumar S H
 * Company       : RCS Technologies
 * Date Created  : 14th July 2015
 *
 * @author       : Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 */


import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ttk.dto.onlineforms.insuranceLogin.DependentVO;
import com.ttk.dto.onlineforms.insuranceLogin.InsCorporateVO;

//import org.apache.log4j.Logger;

public class DependentDetails extends TagSupport
{

	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger( DependentDetails.class );
	public int doStartTag() throws JspException
	{
                try
                {
                	
                	InsCorporateVO insCorporateVO	=	(InsCorporateVO)pageContext.getSession().getAttribute("insCorporateVO");
                	ArrayList<DependentVO> arrayList 	=	insCorporateVO.getDependendents();
                	DependentVO dependentVO	=	null;
				   JspWriter out = pageContext.getOut();//Writer object to write the file
				   String gridOddRow="'gridOddRow'";
				   String gridEvenRow="'gridEvenRow'";
				   String trClass	=	"";
	                out.print("<table border='1' align='center' cellpadding='0' cellspacing='0' class='table table-striped'  style='width:300px;height:auto;'");
				   if(arrayList != null)
					{

					   out.print("<tr colspan='2'>");
				        out.print("<th align='center' class='gridHeader'>Name</th>");
				        out.print("<th align='center' class='gridHeader'>Relationship</th>");
				        out.print("<th align='center' class='gridHeader'>Age</th>");
				        out.print("<th align='center' class='gridHeader'>Gender</th>");
				        out.print("</tr>");
				        
					   for(int i=0;i<arrayList.size();i++)
					  {
						dependentVO	=	arrayList.get(i);
			                trClass	=	i%2==0?gridEvenRow:gridOddRow;
					        out.print("<tr class="+trClass+">");
					        out.print("<td align='center'> "+dependentVO.getName()+"  </td>");
					        out.print("<td align='center'> "+dependentVO.getRelation()+"  </td>");
					        out.print("<td align='center'> "+dependentVO.getAge()+"  </td>");
					        out.print("<td align='center'> "+dependentVO.getGender()+"  </td>");
					        out.print("</tr>");
					  }//for
					}//end of if(alFileUploads != null)
				   else{
					   	out.print("<tr colspan='2'>");
				        out.print("<th align='center' class='gridHeader'><font color='red'>No Data</font></th>");
				        out.print("</tr>");
				   }
					 out.print("</table>");
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
}//end of DependentDetails class