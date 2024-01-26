
/**
 * @ (#) BankRenewHistory.java 10th July 2015
 * Project       : Dubai TTK
 * File          : BankRenewHistory.java
 * Author        : Kishor kumar S H
 * Company       : RCS Technologies
 * Date Created  : 10th July 2015
 *
 * @author       : Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 */

package com.ttk.common.tags.finance;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ttk.dto.finance.BankGuaranteeVO;
//import org.apache.log4j.Logger;

public class BankRenewHistory extends TagSupport
{

	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger( CitibankEnrolHistory.class );
	public int doStartTag() throws JspException
	{
                try
                {
                    ArrayList<BankGuaranteeVO> listOfBgs = (ArrayList<BankGuaranteeVO>)pageContext.getSession().getAttribute("listOfBgs");
				   JspWriter out = pageContext.getOut();//Writer object to write the file
				   String gridOddRow="'gridOddRow'";
				   String gridEvenRow="'gridEvenRow'";
				   if(listOfBgs != null)
					{
						if(listOfBgs.size()>=1)
						{
			                out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:750px;height:auto;'");
					        out.print("<tr>");
					        out.print("<th align='center' class='gridHeader'>S.No.</th>");
					        out.print("<th align='center' class='gridHeader'>Bank Name</th>");
					        out.print("<th align='center' class='gridHeader'>BG No.</th>");
					        out.print("<th align='center' class='gridHeader'>BG Amt.</th>");
					        out.print("<th align='center' class='gridHeader'>Effective Date</th>");
					        out.print("<th align='center' class='gridHeader'>Expiry Date</th>");
					        out.print("<th align='center' class='gridHeader'>BG Type</th>");
					        out.print("</tr>");
		
					        int i=0;
					        for(BankGuaranteeVO bankGuaranteeVO:listOfBgs){
						        out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
						        out.print("<td align='center'>"+(i+1)+"</td>");
						        out.print("<td align='center'>"+bankGuaranteeVO.getBankerName()+"</td>");
						        out.print("<td align='center'>"+bankGuaranteeVO.getBankGuaranteeNo()+"</td>");
						        out.print("<td align='center'>"+bankGuaranteeVO.getBanGuarAmt()+"</td>");
						        out.print("<td align='center'>"+bankGuaranteeVO.getBankFromDate()+"</td>");
						        out.print("<td align='center'>"+bankGuaranteeVO.getBankToDate()+"</td>");
						        out.print("<td align='center'>"+bankGuaranteeVO.getBgType()+"</td>");
						        out.print("</tr>");
						                i++;
					        }
						}
					}//end of if(listOfBgs != null)
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
}//end of BankRenewHistory class