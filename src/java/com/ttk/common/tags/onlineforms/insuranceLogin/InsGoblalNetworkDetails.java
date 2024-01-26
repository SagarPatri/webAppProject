package com.ttk.common.tags.onlineforms.insuranceLogin;

/**
 * @ (#) InsGoblalNetworktails.java 14th July 2015
 * Project       : Dubai TTK
 * File          : InsGoblalNetworktails.java
 * Author        : Kishor kumar S H
 * Company       : RCS Technologies
 * Date Created  : 14th July 2015
 *
 * @author       : Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 */


import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.ttk.dto.onlineforms.insuranceLogin.InsGlobalViewVO;
import com.ttk.dto.onlineforms.insuranceLogin.NetworkDetailsVO;

//import org.apache.log4j.Logger;

public class InsGoblalNetworkDetails extends TagSupport
{

	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger( InsGoblalNetworktails.class );
	public int doStartTag() throws JspException
	{
                try
                {
                	
                	InsGlobalViewVO insGlobalViewVO	=	(InsGlobalViewVO)pageContext.getSession().getAttribute("insGlobalViewVO");
                	NetworkDetailsVO networkDetailsVO	=	null;
                	networkDetailsVO	=	insGlobalViewVO.getNetworkDetailsVO();
				   JspWriter out = pageContext.getOut();//Writer object to write the file
				   String gridOddRow="'gridOddRow'";
				   String gridEvenRow="'gridEvenRow'";
				   if(networkDetailsVO != null)
					{
						
			                out.print("<table border='1' align='center' cellpadding='0' cellspacing='0' class='table table-striped'  style='width:500px;height:auto;'");
					   
			                out.print("<tr>");
					        out.print("<th align='center' class='gridHeader'>Network Segment</th>");
					        out.print("<th align='center' class='gridHeader'>Number</th>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Total Number of Providers</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+networkDetailsVO.getTotalNoOfProviders()+"  </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridOddRow+"> Number of CN Providers</td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+networkDetailsVO.getNoOfCnNetwork()+"  </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Number of GN Providers</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+networkDetailsVO.getNoOfGnNetwork()+"  </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Number of SN Providers</td>");
					        out.print("<td align='center' class="+gridEvenRow+">  "+networkDetailsVO.getNoOfSnNetwork()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Number of BN Providers</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+networkDetailsVO.getNoOfBnNetwork()+"  </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Number of VN Providers</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+networkDetailsVO.getNoOfWnNetwork()+"  </td>");
					        out.print("</tr>");
					        out.print("</table>");
					        
					        out.print("<br>");
					        
			                out.print("<table border='1' align='center' cellpadding='0' cellspacing='0' class='table table-striped'  style='width:500px;height:auto;'");
					        out.print("<tr>");
					        out.print("<th align='center' class='gridHeader'>Network Utilization</th>");
					        out.print("<th align='center' class='gridHeader'>Number</th>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Total number of Providers used</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+networkDetailsVO.getTotalNoOfProvidersUsed()+"  </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridOddRow+">Number of Hospitals</td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+networkDetailsVO.getNumberOfHospitals()+"  </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Number of Medical centers</td>");
					        out.print("<td align='center' class="+gridEvenRow+"> "+networkDetailsVO.getNumberOfMedicalcenter()+"  </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridOddRow+">Number of Clinics</td>");
					        out.print("<td align='center' class="+gridOddRow+"> "+networkDetailsVO.getNumberOfClinics()+" </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Number of Pharmacies</td>");
					        out.print("<td align='center' class="+gridEvenRow+">  "+networkDetailsVO.getNumberOfPharmacies()+"  </td>");
					        out.print("</tr>");
					        
					        out.print("<tr>");
					        out.print("<td align='center' class="+gridEvenRow+">Number of Diagnostic centers</td>");
					        out.print("<td align='center' class="+gridEvenRow+">  "+networkDetailsVO.getNumberOfDiagCenters()+"  </td>");
					        out.print("</tr>");

					        out.print("</table>");
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
}//end of InsGoblalNetworktails class