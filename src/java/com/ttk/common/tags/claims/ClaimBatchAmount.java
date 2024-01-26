/**
 * @ (#) PreAuthShortFfalls.java June 22, 2015
 * Project : ProjectX
 * File : PreAuthShortFfalls.java
 * Author : Nagababu K
 * Company :Vidal
 * Date Created : June 22, 2015
 *
 * @author : Nagababu K
 * Modified by :
 * Modified date :
 * Reason :
*/
package com.ttk.common.tags.claims;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ttk.dto.claims.BatchVO;
//import org.apache.log4j.Logger;

public class ClaimBatchAmount extends TagSupport
{
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger( CitibankEnrolHistory.class );
	public int doStartTag() throws JspException
	{
		try
		{
			ArrayList<BatchVO> listOfAmounts = (ArrayList<BatchVO>)pageContext.getSession().getAttribute("listOfAmounts");
	        JspWriter out = pageContext.getOut();//Writer object to write the file
	       String gridOddRow="'gridOddRow'";
	       String gridEvenRow="'gridEvenRow'";
	       String delImage="<img src='/ttk/images/DeleteIcon.gif' alt='Delete Invoice Number'width='12' height='12' border='0'>";
	        if(listOfAmounts != null)
	        {
	        	if(listOfAmounts.size()>=1){
	                		out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:750px;height:auto;'");
	                			out.print("<tr>");
	                			out.print("<th align='center' class='gridHeader'>S.No.</th>");
	                			out.print("<th align='center' class='gridHeader'>Provider Invoice No.</th>");
	                			out.print("<th align='center' class='gridHeader'>Claim No.</th>");
	                			out.print("<th align='center' class='gridHeader'>Requested Amount</th>");
	                			out.print("<th align='center' class='gridHeader'>Delete</th>");
	                			out.print("</tr>");
	        	
	                			int i=0;
	                			double totalAmount=0;
	                			for(BatchVO batchVO:listOfAmounts){
	                				double amount=batchVO.getRequestedAmount()==null?0:batchVO.getRequestedAmount().doubleValue();
	                				totalAmount+=amount;
	                		out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
	                		out.print("<td align='center'>"+(i+1)+"</td>");
	                		out.print("<td align='center'><a href='#' accesskey='f' title='Edit'  onClick=\"javascript:editRequestedAmount('"+i+"');\">"+batchVO.getProviderInvoiceNO()+"</a></td>"); 
	                		out.print("<td align='center'>"+(batchVO.getClaimNO()==null?"":batchVO.getClaimNO())+"</td>");
	                		out.print("<td align='center'>"+amount+"</td>");
	                		out.print("<td align='center'><a href='#' accesskey='f' title='Delete Invoice Number'  onClick=\"javascript:deleteInvoiceNO('"+batchVO.getClaimSeqID()+"');\">"+delImage+"</a></td>");
	                		out.print("</tr>");
	                		i++;
	                			}
	                	  out.print("<tr class='gridEvenRow'><td></td><td></td><td align='right'>Total Amount:</td><td align='right'><input type='text' name=\"addedTotalAmount\" readonly=\"readonly\" value='"+totalAmount+"'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
	                		out.print("</table>");
	        	}
	        }//end of if(listOfAmounts != null)
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
}//end of ClaimBatchAmount class 
