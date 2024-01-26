/**
 * @ (#) PreAuthShortFfalls.java June 27, 2015
 * Project : ProjectX
 * File : PreAuthShortFfalls.java
 * Author : Nagababu K
 * Company :Vidal
 * Date Created : June 27, 2015
 *
 * @author : Nagababu K
 * Modified by :
 * Modified date :
 * Reason :
*/
package com.ttk.common.tags.preauth;

import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.action.DynaActionForm;

import com.ttk.common.TTKCommon;
import com.ttk.dto.preauth.DiagnosisDetailsVO;
//import org.apache.log4j.Logger;

public class DiagnosisDetails extends TagSupport
{
	private String flow;
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	//private static Logger log = Logger.getLogger( CitibankEnrolHistory.class );
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException
	{
		try
		{
			
			ArrayList<DiagnosisDetailsVO> diagnosis=null;
			DynaActionForm frmClaimGeneral = null;
			DynaActionForm frmPreAuthGeneral = null;
			String strencountertypeclm=null;
			String strencountertypepat=null;
			String strprovAuthoritycml=null;
			String strprovAuthoritypat=null;
			String strnetworkproviderTypeclm = null;
			String strnetworkproviderTypepat = null;
			String strmodeofclaim=null;
			String preCronTypeID="";			
			String benefitType="";
			String preOneMedicalCondition="";
			String productAuthority ="";
			String strPreAuthRecvTypeID=null;
			String strModeOfClaim=null;
			frmClaimGeneral= (DynaActionForm)pageContext.getSession().getAttribute("frmClaimGeneral");
			frmPreAuthGeneral= (DynaActionForm)pageContext.getSession().getAttribute("frmPreAuthGeneral");
			if("CLM".equals(getFlow()))
			{
		    strencountertypeclm=frmClaimGeneral.getString("encounterTypeId");
		    strprovAuthoritycml = frmClaimGeneral.getString("provAuthority");
		    strnetworkproviderTypeclm= frmClaimGeneral.getString("networkProviderType");
		    strmodeofclaim=frmClaimGeneral.getString("modeOfClaim");
		    preCronTypeID=frmClaimGeneral.getString("preCronTypeID");
		    benefitType=frmClaimGeneral.getString("benefitType");
		    preOneMedicalCondition=frmClaimGeneral.getString("preOneMedicalCondition");
		    productAuthority = (String) frmClaimGeneral.getString("productAuthority");
		    strModeOfClaim = (String) frmClaimGeneral.getString("modeOfClaim");
		    
			}
			if("PAT".equals(getFlow()))
			{
				strencountertypepat=frmPreAuthGeneral.getString("encounterTypeId");
				strprovAuthoritypat=frmPreAuthGeneral.getString("provAuthority");
				strnetworkproviderTypepat=frmPreAuthGeneral.getString("networkProviderType");
				preCronTypeID=frmPreAuthGeneral.getString("preCronTypeID");
				benefitType=frmPreAuthGeneral.getString("benefitType");
				preOneMedicalCondition=frmPreAuthGeneral.getString("preOneMedicalCondition");
				productAuthority = (String) frmPreAuthGeneral.getString("productAuthority");
				strPreAuthRecvTypeID = (String) frmPreAuthGeneral.getString("preAuthRecvTypeID");
			}
			
			if("PAT".equals(getFlow()))
				diagnosis= (ArrayList<DiagnosisDetailsVO>)frmPreAuthGeneral.get("diagnosisList");
			else if("CLM".equals(getFlow()))
				diagnosis= (ArrayList<DiagnosisDetailsVO>)frmClaimGeneral.get("diagnosisList");
			
				JspWriter out = pageContext.getOut();//Writer Object to write the File
	       String gridOddRow="'gridOddRow'";
	       String gridEvenRow="'gridEvenRow'";
	        if(diagnosis != null)
	        {
	        	if(diagnosis.size()>=1){
	                		out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:850px;height:auto;'");
	                			out.print("<tr>");
	                			out.print("<th align='center' class='gridHeader'>ICD Code</th>");
	                			out.print("<th align='center' class='gridHeader'>Diagnosis Desription</th>");
	                			out.print("<th align='center' class='gridHeader'>Diagnosis Type</th>");
	                			out.print("<th align='center' class='gridHeader'>First Diagnosed Date</th>");
	                			
	                			if("CLM".equals(getFlow()))
	                			{
	                				
	                					if("DHA".equals(strprovAuthoritycml))
	                					{
	                						if("ECL".equals(strmodeofclaim) || "PCLM".equals(strmodeofclaim))
	                						{
	                							if("Y".equals(strnetworkproviderTypeclm))
	                							{
	                								if("3".equals(strencountertypeclm) || "4".equals(strencountertypeclm))
	                								{		
	                									out.print("<th align='center' class='gridHeader'>InfoType</th>");
	                									out.print("<th align='center' class='gridHeader'>InfoCode</th>");
	                								}
	                							}
	                						}
	                					}
	                				
	                			}	
	                			else if("PAT".equals(getFlow()))
	                			{ 
	                				
	                					if("DHA".equals(strprovAuthoritypat))
	                					{
	                						if("Y".equals(strnetworkproviderTypepat))
	                						{

	                							if("3".equals(strencountertypepat) || "4".equals(strencountertypepat))
	                							{		
	                								out.print("<th align='center' class='gridHeader'>InfoType</th>");
	                								out.print("<th align='center' class='gridHeader'>InfoCode</th>");
	                							}
	                						}
	                					}
	                			
	                			}
	                			
	                			if(TTKCommon.checkNull(preCronTypeID).length()>1){
	                				out.print("<th align='center' class='gridHeader'>Condition Type</th>");
	                			}
	                				
	                		if(("DAYC".equals(benefitType)||"IPT".equals(benefitType))&&"MOH".equals(productAuthority)){
	                			out.print("<th align='center' class='gridHeader'>Per One Medical Condition Type</th>");
	                		}
	                			out.print("<th align='center' class='gridHeader'>Delete</th>");
	                			out.print("</tr>");
	        	
	                			int i=0;
	                			String imagePath="<img src='/ttk/images/DeleteIcon.gif' alt='Delete Diagnosis Details'width='16' height='16' border='0'>";
	                			String imageDisabledPath="<img src='/ttk/images/DeleteIcon.gif' width='16' height='16' border='0'>";
	                			String principal="Principal";
	                		    String secondary="Secondary"; 
	                			for(DiagnosisDetailsVO detailsVO:diagnosis){
	                		out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
	                		out.print("<td align='center'><a href='#' accesskey='f' title='Edit Diagnosis Details'  onClick=\"javascript:editDiagnosisDetails('"+i+"');\">"+detailsVO.getIcdCode()+"</a></td>");
	                		out.print("<td align='left'>"+detailsVO.getAilmentDescription()+"</td>");
	                		out.print("<td align='center'>"+("Y".equals(detailsVO.getPrimaryAilment())?principal:secondary)+"</td>");
	                		out.print("<td align='left'>"+detailsVO.getFirstDiagnosedDate()+"</td>");
	                		if("CLM".equals(getFlow()))
	                		{
	                			
	                				
	                			if(("DHA").equals(strprovAuthoritycml))
	                			{
	                				if("ECL".equals(strmodeofclaim) || "PCLM".equals(strmodeofclaim))
	                				{
	                					if("Y".equals(strnetworkproviderTypeclm))
	                					{
	                						if("3".equals(strencountertypeclm) || "4".equals(strencountertypeclm))
	                						{  
	                					    if(detailsVO.getInfoType()==null || detailsVO.getInfoType().trim().length()==0)
	                						{
	                							out.print("<td align='left'>"+" "+"</td>"); 
	                						}
	                						else
	                						{
	                							out.print("<td align='left'>"+detailsVO.getInfoType()+"</td>");
	                						}
	                						
	                					    if(detailsVO.getInfoCode()==null  || detailsVO.getInfoCode().trim().length()==0)
	                						out.print("<td align='left'>"+" "+"</td>"); 
	                					    else if(detailsVO.getInfoCode().equals("Y"))
	                							out.print("<td align='left'>"+"Yes"+"</td>");
	                							else if(detailsVO.getInfoCode().equals("N"))
		                							out.print("<td align='left'>"+"No"+"</td>");
	                							else if(detailsVO.getInfoCode().equals("U"))
		                							out.print("<td align='left'>"+"Unknown"+"</td>");
	                							else if(detailsVO.getInfoCode().equals("W"))
		                							out.print("<td align='left'>"+"Clinically Undetermined"+"</td>");
	                							else if(detailsVO.getInfoCode().equals("1"))
		                							out.print("<td align='left'>"+"Unreported/Not used"+"</td>");
	                							else if(detailsVO.getInfoCode().equals("OP"))
		                							out.print("<td align='left'>"+"Outpatient claim"+"</td>");
	                						}
	                					}

	                				}

	                			}
	                		
	                		}
	                		else if("PAT".equals(getFlow()))
	                		{
	                			
	                			
	                			if(("DHA").equals(strprovAuthoritypat))
	                			{
	                				if("Y".equals(strnetworkproviderTypepat))
	                				{

	                					if("3".equals(strencountertypepat) || "4".equals(strencountertypepat))
	                					{
	                						 if(detailsVO.getInfoType()==null || detailsVO.getInfoType().trim().length()==0)
		                						{
		                							out.print("<td align='left'>"+" "+"</td>"); 
		                						}
		                						else
		                						{
		                							out.print("<td align='left'>"+detailsVO.getInfoType()+"</td>");
		                						}
	                						  if(detailsVO.getInfoCode()==null  || detailsVO.getInfoCode().trim().length()==0)
	  	                						out.print("<td align='left'>"+" "+"</td>"); 
	                						  else if(detailsVO.getInfoCode().equals("Y"))
	                							out.print("<td align='left'>"+"Yes"+"</td>");
	                							else if(detailsVO.getInfoCode().equals("N"))
		                							out.print("<td align='left'>"+"No"+"</td>");
	                							else if(detailsVO.getInfoCode().equals("U"))
		                							out.print("<td align='left'>"+"Unknown"+"</td>");
	                							else if(detailsVO.getInfoCode().equals("W"))
		                							out.print("<td align='left'>"+"Clinically Undetermined"+"</td>");
	                							else if(detailsVO.getInfoCode().equals("1"))
		                							out.print("<td align='left'>"+"Unreported/Not used"+"</td>");
	                							else if(detailsVO.getInfoCode().equals("OP"))
		                							out.print("<td align='center'>"+"Outpatient claim"+"</td>");
	                					}

	                				}

	                			}
	                	
	                		}
	                		if(TTKCommon.checkNull(preCronTypeID).length()>1){
	                		if("CH".equals(preCronTypeID))preCronTypeID="Chronic";
	                		else if("PX".equals(preCronTypeID))preCronTypeID="Pre-existing";
	                			out.print("<td align='center'>"+("Y".equals(detailsVO.getPrimaryAilment())?preCronTypeID:"")+"</td>");
                			}
	                		if(("DAYC".equals(benefitType)||"IPT".equals(benefitType))&&"MOH".equals(productAuthority)){
	                			out.print("<td align='center'>"+("Y".equals(detailsVO.getPrimaryAilment())?("Y".equals(preOneMedicalCondition)?"Yes":"No"):"")+"</td>");
	                		}
	                		
	                		if(("DHP").equals(strPreAuthRecvTypeID) || ("ONL1").equals(strPreAuthRecvTypeID) || ("ECL").equals(strModeOfClaim))
	                			out.print("<td align='center'>"+imageDisabledPath+"</td>");
                		    else
                		    	out.print("<td align='center'><a href='#' onClick=\"javascript:deleteDiagnosisDetails('"+i+"');\">"+imagePath+"</a></td>");
	                		out.print("</tr>");
	                		i++;
	                			}
	                		out.print("</table>");
	        	}
	        }//end of if(diagnosis != null)
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
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
}//end of DiagnosisDetails class 
