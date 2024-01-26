/**
 * @ (#) PremiumConfigurationDetails.java July 18, 2015
 * Project : ProjectX
 * File : ActivityDetails.java
 * Author : Nagababu K
 * Company :RCS Technologies
 * Date Created : July 18, 2015
 *
 * @author : Nagababu K
 * Modified by :
 * Modified date :
 * Reason :
*/
package com.ttk.common.tags.administration;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;

import com.ttk.common.TTKCommon;
import com.ttk.dto.administration.PremiumConfigurationVO;
import com.ttk.dto.common.CacheObject;
import com.ttk.dto.preauth.ActivityDetailsVO;
//import org.apache.log4j.Logger;

public class PremiumConfigurationDetails extends TagSupport
{
	//private String flow;
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger( PremiumConfigurationDetails.class );
	
	public int doStartTag() throws JspException{
	ArrayList<PremiumConfigurationVO> alProductList=null;
	ArrayList<PremiumConfigurationVO> asoList=null;
	ArrayList<PremiumConfigurationVO> asPlusList=null;
		try
		{
			
			StringBuilder capitationData=new StringBuilder();
   	     StringBuilder nonCapitationData=new StringBuilder();
   	     
   	  HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
   	  
     	DynaActionForm frmPolicyDetails=(DynaActionForm)request.getSession().getAttribute("frmPolicyDetails"); 
  	
     	String strViewmode=" Disabled ";
  	if(TTKCommon.isAuthorized(request,"Edit") || TTKCommon.isAuthorized((HttpServletRequest)pageContext.getRequest(),"Add"))
  	{
  		strViewmode="";
  	}
     	CacheObject cacheObject = null;
   	
   	    alProductList=(ArrayList<PremiumConfigurationVO>)pageContext.getSession().getAttribute("alProductList");
   	   asoList= (ArrayList<PremiumConfigurationVO>)pageContext.getSession().getAttribute("asoDatesList");
    	asPlusList= (ArrayList<PremiumConfigurationVO>)pageContext.getSession().getAttribute("asPlusDatesList");
    	ArrayList allAsoDatesList =(ArrayList<CacheObject>)request.getSession().getAttribute("allAsoDatesList");
    	ArrayList allAsPlusDatesList =(ArrayList<CacheObject>)request.getSession().getAttribute("allAsPlusDatesList");  
   	    
    	
    	String asoEffectivedate = (String) request.getSession().getAttribute("effectiveFromDateAso");
    	
    	String asplusEffectivedate = (String) request.getSession().getAttribute("effectiveFromDateAsplus");
    	
       	 String strasoFromDate="";
       	 String asoToDate="";
       	 String asPlusFromDate="";
       	 String asPlusToDate="";
       	 
    	
    	
         if(asoList!=null){	
    	     for(PremiumConfigurationVO asoVO:asoList){
    	    	 strasoFromDate=asoVO.getAsoFromDate();
     			 asoToDate=asoVO.getAsoToDate(); 
     			 
    	     } 
    	     }
         			     			
         if(asPlusList!=null){				
      for(PremiumConfigurationVO asplusVO:asPlusList){
    	  asPlusFromDate=asplusVO.getAsPlusFromDate();
    		 asPlusToDate=asplusVO.getAsPlusToDate();
    	     }
         }	
       	
         String strParms="'"+strasoFromDate+"','"+asoToDate+"'";
         String strOnclickEvent="onClick=\"javascript:addconfigurationDetailsASO("+strParms+");";  
         String strDeleteEventASO="onClick=\"onEffectivePeriodDeleteASO("+strParms+");";    
       	    
         String strAsoParms="'"+asPlusFromDate+"','"+asPlusToDate+"'";
         String strOnclickEventAso="onClick=\"javascript:addconfigurationDetailsASPlus("+strAsoParms+");";  
         String strDeleteEventASPLUS="onClick=\"onEffectivePeriodDeleteASPLUS("+strAsoParms+");";  
   	    
			
	        JspWriter out = pageContext.getOut();//Writer Object to write the File
	       String gridOddRow="'gridOddRow'";
	       String gridEvenRow="'gridEvenRow'";
	       
	       nonCapitationData.append("<tr>");
	       nonCapitationData.append("<th align='center' class='gridHeader' title='Serial Number'>S.No</th>");
	       nonCapitationData.append("<th align='center' class='gridHeader' title='Minimum Age'>Minimum Age</th>");
	       nonCapitationData.append("<th align='center' class='gridHeader' title='Maximum Age'>Maximum Age </th>");
	       nonCapitationData.append("<th align='center' class='gridHeader' title='MaritalStatus'>MaritalStatus</th>");
	       nonCapitationData.append("<th align='center' class='gridHeader' title='Gender Applicable'>Gender Applicable</th>");
	       nonCapitationData.append("<th align='center' class='gridHeader' title='Applicable To Relation'>Applicable To Relation </th>");
	       nonCapitationData.append("<th align='center' class='gridHeader' title='Salary Band'>Salary Band</th>");
	       nonCapitationData.append("<th align='center' class='gridHeader' title='Gross Premium'>Gross Premium</th>");
	       nonCapitationData.append("<th align='center' class='gridHeader' title='Authority Product ID'>Authority Product ID</th>");
	       nonCapitationData.append("<th align='center' class='gridHeader' title='Audit Log'>Audit Log</th>");
	       nonCapitationData.append("<th align='center' class='gridHeader' title='Delete'>Delete</th>");
	       nonCapitationData.append("</tr>");
		
		
	       capitationData.append("<tr>");
	       capitationData.append("<th align='center' class='gridHeader' title='Serial Number'>S.No</th>");
	       capitationData.append("<th align='center' class='gridHeader' title='Minimum Age'>Minimum Age</th>");
	       capitationData.append("<th align='center' class='gridHeader' title='Maximum Age'>Maximum Age </th>");
	       capitationData.append("<th align='center' class='gridHeader' title='MaritalStatus'>MaritalStatus</th>");
	       capitationData.append("<th align='center' class='gridHeader' title='Gender Applicable'>Gender Applicable</th>");
	       capitationData.append("<th align='center' class='gridHeader' title='Applicable To Relation'>Applicable To Relation </th>");
	       capitationData.append("<th align='center' class='gridHeader' title='Salary Band'>Salary Band</th>");
	      /* capitationData.append("<th align='center' class='gridHeader' title='AsPlus Type'>AsPlus(OP/IP&OP)</th>");*/
	       capitationData.append("<th align='center' class='gridHeader' title='Gross Premium'>Gross Premium</th>");
	       capitationData.append("<th align='center' class='gridHeader' title='Authority Product ID'>Authority Product ID</th>");
	       capitationData.append("<th align='center' class='gridHeader' title='Audit Log'>Audit Log</th>");
		   capitationData.append("<th align='center' class='gridHeader' title='Delete'>Delete</th>");
	       
	       capitationData.append("</tr>");
	       int countCap_Y=1;
	       int conCap_N=1;
	        if(alProductList != null){
	        	if(alProductList.size()>=1){
	        		for(PremiumConfigurationVO premiumconfigobj: alProductList)
	     	       {
	        	if("1".equals(premiumconfigobj.getCapitationYN()))
	        	{
	               
	        		nonCapitationData.append("<tr>");
	        		nonCapitationData.append("<td align='center'><a title='Edit Product' href='javascript:editCapitationYN("+premiumconfigobj.getPremiumConfigSeqId()+")'>"+conCap_N+"</a></td>");
	        		//nonCapitationData.append("<td align='center'>"+conCap_N+"</td>");
	        		nonCapitationData.append("<td align='center'>"+premiumconfigobj.getMinAge()+"</td>");
	        		nonCapitationData.append("<td align='center'>"+premiumconfigobj.getMaxAge()+"</td>");
	        		nonCapitationData.append("<td align='center'>"+premiumconfigobj.getMaritalStatus()+"</td>");
	        		nonCapitationData.append("<td align='center'>"+premiumconfigobj.getGender()+"</td>");
	        	    nonCapitationData.append("<td align='center'>"+premiumconfigobj.getRelation()+"</td>");
	        	    nonCapitationData.append("<td align='center'>"+premiumconfigobj.getSalaryBand()+"</td>");
	        		nonCapitationData.append("<td align='center'>"+premiumconfigobj.getGrossPremium()+"</td>");
	        		nonCapitationData.append("<td align='center'>"+premiumconfigobj.getAuthorityProductId()+"</td>");
	        		nonCapitationData.append("<td align='center'><A title='View Audit Logs' href='javascript:editAuditlogs("+premiumconfigobj.getPremiumConfigSeqId()+");'>View</a></td>");
	        		nonCapitationData.append("<td align='center'><A accessKey=o onclick='javascript:onDeleteIconCapitationYN("+premiumconfigobj.getPremiumConfigSeqId()+");' href='#'><IMG border=0 alt='Delete' title='Delete' src='/ttk/images/DeleteIcon.gif' width=16 height=16></A></td>");
	        		nonCapitationData.append("</tr>");
	        		conCap_N++;	
	        		
            			
	        	}//for(ActivityDetailsVO activityDetails:alActivityDetails)
	        		
	        	 else 
	        	{
	        		  capitationData.append("<tr>");
	        	      capitationData.append("<td align='center'><a title='Edit Product' href='javascript:editCapitationYN("+premiumconfigobj.getPremiumConfigSeqId()+")'>"+countCap_Y+"</a></td>");
	        		// capitationData.append("<td align='center'>"+countCap_Y+"</td>");
	        		 capitationData.append("<td align='center'>"+premiumconfigobj.getMinAge()+"</td>");
	        		 capitationData.append("<td align='center'>"+premiumconfigobj.getMaxAge()+"</td>");
	        		 capitationData.append("<td align='center'>"+premiumconfigobj.getMaritalStatus()+"</td>");
	        		 capitationData.append("<td align='center'>"+premiumconfigobj.getGender()+"</td>");
	        		 capitationData.append("<td align='center'>"+premiumconfigobj.getRelation()+"</td>");
	        		 capitationData.append("<td align='center'>"+premiumconfigobj.getSalaryBand()+"</td>");
	        		/* capitationData.append("<td align='center'>"+premiumconfigobj.getCapitationtype()+"</td>");*/
	        		 capitationData.append("<td align='center'>"+premiumconfigobj.getGrossPremium1()+"</td>");
	        		 capitationData.append("<td align='center'>"+premiumconfigobj.getAuthorityProductId()+"</td>");
	        		 capitationData.append("<td align='center'><A title='View Audit Logs' href='javascript:editAuditlogs("+premiumconfigobj.getPremiumConfigSeqId()+");'>View</a></td>");
		        	 capitationData.append("<td align='center'><A accessKey=o onclick='javascript:onDeleteIconCapitationYN("+premiumconfigobj.getPremiumConfigSeqId()+");' href='#'><IMG border=0 alt='Delete' title='Delete' src='/ttk/images/DeleteIcon.gif' width=16 height=16></A></td>");
	        		 capitationData.append("</tr>");
	        		 countCap_Y++;
	        		}
	     	       }
	        	}	
	     out.print("<fieldset>"); 	
	     out.print("<legend>ASO Policy</legend>");
	     
	     
	        out.print("<table align='center' class='formContainer' border='0' cellspacing='0' cellpadding='0'>");
         out.print("<tr>"); 
         out.print("<td class=\"formLabel\">"); 
         out.print("<b>Select Premium Effective Period</b>"); 
         out.print("&nbsp");
         out.print(":");
         out.print("&nbsp");
     	out.print("<select name=\"premiumEffectivePeriodAso\" class=\"selectBox selectBoxLarge\" onChange=\"javascript:onChangeEffectivePeriodAso();\""+strViewmode+">");
     	out.print("<option value=\"\">Select from list</option>");
     	if(allAsoDatesList!= null && allAsoDatesList.size() > 0)
			{
     	for(int i=0; i < allAsoDatesList.size(); i++)
			{
     		cacheObject = (CacheObject)allAsoDatesList.get(i);
     	  out.print("<option value='"+cacheObject.getCacheId()+"'  "+TTKCommon.isSelected(asoEffectivedate, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
			}
			}
     	out.print("</select>");
     	
     	
     	out.print("</td>");
   	    out.print("<td class=\"formLabel\">"); 
   	    out.print("<b>Add a new Premium Effective Period</b>&nbsp");
   	    out.print("<a href='#' accesskey='a' onClick='javascript:addNewPremiumPeriodAso()'><img src='/ttk/images/AddIcon.gif' ALT='Add a new Premium Effective Period' title='Add a new Premium Effective Period' width=13 height=13 border='0' align='absmiddle'></a>");
   	    out.print("</td>");
   	    out.print("<td>"); 
         out.print("</td>");
     	out.print("</tr>"); 
      	out.print("</table>"); 
	     
      	  out.print("<br>"); 
	     
	     
	     out.print("<table align='center' class='formContainer' border='0' cellspacing='0' cellpadding='0' bgcolor='#EEEEEE'>");
      out.print("<tr>"); 
      out.print("<td class=\"formLabel\">"); 
      out.print("Premium Effective From Date "); 
  	 out.print("<span class=\"mandatorySymbol\">*</span>");
 	 out.print("");
  	 out.print(":");
  	 out.print("");
  	//out.print("<input type=\"text\" name=\"asoFromDate\"  value='"+asoFromDate+"' maxlength=\"10\" class=\"textBox textDate\">");
  	 out.print("<input type=\"text\" name=\"newAsoFromDate\"  value='"+TTKCommon.getHtmlString(strasoFromDate)+"' maxlength=\"10\" class=\"textBox textDate\" readonly=\"true\">" );     
 	if(TTKCommon.isAuthorized(request,"Edit"))
		{
 		out.print("<A NAME=\"CalendarObjectFrmDateNew\" ID=\"CalendarObjectFrmDateNew\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDateNew','frmRenewalDays.newAsoFromDate',document.frmRenewalDays.newAsoFromDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" title=\"Calendar\" name=\"asoFromDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
		}
 	
	/*	out.print("<input type=\"text\" name=\"asoFromDate\" value='10/12/2018' maxlength=\"10\" maxlength=\"10\" class=\"textBox textDate\">"); 
		out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmRenewalDays.asoFromDate','"+TTKCommon.getHtmlString(strasoFromDate)+"','',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");

		*/
      out.print("</td>");
      
      out.print("<td>"); 
      out.print("</td>");
      out.print("<td>"); 
      out.print("</td>");
      out.print("<td>"); 
      out.print("</td>");  
      out.print("<td class=\"formLabel\">"); 
      out.print("Premium Effective To Date "); 
  	
 	 out.print("");
  	 out.print(":");
  	 out.print("");
  	out.print("<input type=\"text\" name=\"newAsoToDate\"  value='"+asoToDate+"' maxlength=\"10\" class=\"textBox textDate\" readonly=\" true\">");
  	// out.print("<input type=\"text\" name=\"asoToDate\"  maxlength=\"10\" class=\"textBox textDate\">");
     
 	if(TTKCommon.isAuthorized(request,"Edit"))
		{
		 out.print("<A NAME=\"CalendarObjectempDate11\" ID=\"CalendarObjectempDate11\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectempDate11','frmRenewalDays.newAsoToDate',document.frmRenewalDays.newAsoToDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" title=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
		}
      out.print("</td>");
      
      
      out.print("<td>"); 
      out.print("</td>");
      
      out.print("<td <a href='#' accesskey='a' "+strOnclickEvent+" \"><img src='/ttk/images/AddIcon.gif' ALT='Add new rule for Capitation-NO' title='Add new rule for Capitation-NO' width=13 height=13 border='0' align='absmiddle'></a>"); 
      out.print("</td>");
      
      out.print("<td <A href='#' accessKey=o "+strDeleteEventASO+" \"><IMG border=0 alt='Delete' title='Delete' src='/ttk/images/DeleteIcon.gif' width=16 height=16></A>");
      out.print("</td>");
    
      out.print("</tr>");
      out.print("</table>");
	     
    
      
       //  out.print("<div  style='margin-left: 8px;overflow: scroll;width: 1150px;height: 350px;'>");
	     out.print("<table align='center' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0' style='overflow:scroll'>");
           out.print(nonCapitationData); 
           out.print("</table>"); 
          // out.print("</div>");
           
          out.println("<table align='center' class='buttonsContainer' border='0' cellspacing='0' cellpadding='0'>");
          out.print("<tr>"); 
          out.print("<td width='100%' align='center'>"); 
          out.print("<button type='button' name='Button2' accesskey='s' class='buttons' onMouseout='this.className='buttons'' onMouseover='this.className='buttons buttonsHover'' onClick='javascript:onSaveAso();'><u>S</u>ave</button>");
          out.print("&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' name='Button2' accesskey='c' class='buttons' onMouseout='this.className='buttons'' onMouseover='this.className='buttons buttonsHover'' onClick='javascript:onClose();'><u>C</u>lose</button>"); 
          out.print("</td>"); 
          out.print("</tr>"); 
   		   out.print("</table>"); 
           out.print("</fieldset>"); 
           
           
           out.print("<fieldset>"); 	
           out.print("<legend>AS Plus(OP/IP&OP) Policy</legend>");
         
           
           

           
           out.print("<table align='center' class='formContainer' border='0' cellspacing='0' cellpadding='0'>");
           out.print("<tr>"); 
           out.print("<td class=\"formLabel\">"); 
           out.print("<b>Select Premium Effective Period</b>"); 
           out.print("&nbsp");
           out.print(":");
           out.print("&nbsp");
          	out.print("<select name=\"premiumEffectivePeriodAsPlus\" class=\"selectBox selectBoxLarge\" onChange=\"javascript:onChangeEffectivePeriodAsplus();\""+strViewmode+">");
          	out.print("<option value=\"\">Select from list</option>");
        	
          	if(allAsPlusDatesList!= null && allAsPlusDatesList.size() > 0)
  			{
        	for(int i=0; i < allAsPlusDatesList.size(); i++)
			{
        		cacheObject = (CacheObject)allAsPlusDatesList.get(i);
        	out.print("<option value='"+cacheObject.getCacheId()+"' "+TTKCommon.isSelected(asplusEffectivedate, cacheObject.getCacheId())+">"+cacheObject.getCacheDesc()+"</option>");
			}
        	out.print("</select>");
			}
          	out.print("</td>");
        	
        	  out.print("<td class=\"formLabel\">"); 
        	  out.print("<b>Add a new Premium Effective Period</b>&nbsp");
        	  out.print("<a href='#' accesskey='a' onClick='javascript:addNewPremiumPeriodAsPlus()'><img src='/ttk/images/AddIcon.gif' ALT='Add a new Premium Effective Period' title='Add a new Premium Effective Period' width=13 height=13 border='0' align='absmiddle'></a>");
        	  out.print("</td>");
        	  out.print("<td>"); 
              out.print("</td>");
        	  out.print("</tr>"); 
           	out.print("</table>"); 
           
            out.print("<br>"); 
           
           out.print("<table align='center' class='formContainer' border='0' cellspacing='0' cellpadding='0' bgcolor='#EEEEEE'>");
           out.print("<tr>"); 
           out.print("<td class=\"formLabel\">"); 
           out.print("Premium Effective From Date "); 
       	out.print("<span class=\"mandatorySymbol\">*</span>");
      	out.print("&nbsp");
       	out.print(":");
       	out.print("&nbsp");
       	out.print("<input type=\"text\" name=\"newAsPlusFromDate\"  value='"+asPlusFromDate+"' maxlength=\"10\" class=\"textBox textDate\">");
      // 	out.print("<input type=\"text\" name=\"asPlusFromDate\"  maxlength=\"10\" class=\"textBox textDate\">");
      	if(TTKCommon.isAuthorized(request,"Edit"))
  		{
  			out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmRenewalDays.newAsPlusFromDate',document.frmRenewalDays.newAsPlusFromDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" title=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
  		}
           out.print("</td>");
           out.print("<td>"); 
           out.print("</td>");
           out.print("<td>"); 
           out.print("</td>");
           out.print("<td>"); 
           out.print("</td>");
           out.print("<td class=\"formLabel\">"); 
           out.print("Premium Effective To Date "); 
       
      	out.print("&nbsp");
       	out.print(":");
       	out.print("&nbsp");
       	out.print("<input type=\"text\" name=\"newAsPlusToDate\"  value='"+asPlusToDate+"' maxlength=\"10\" class=\"textBox textDate\">");
       //	out.print("<input type=\"text\" name=\"asPlusToDate\"  maxlength=\"10\" class=\"textBox textDate\">");
      	if(TTKCommon.isAuthorized(request,"Edit"))
  		{
  			out.print("<A NAME=\"CalendarObjectFrmDate\" ID=\"CalendarObjectFrmDate\" HREF=\"#\" onClick=\"javascript:show_calendar('CalendarObjectFrmDate','frmRenewalDays.newAsPlusToDate',document.frmRenewalDays.newAsPlusToDate.value,'',event,148,178);return false;\" onMouseOver=\"window.status='Calendar';return true;\" onMouseOut=\"window.status='';return true;\"><img src=\"ttk/images/CalendarIcon.gif\" alt=\"Calendar\" title=\"Calendar\" name=\"empDate\" width=\"24\" height=\"17\" border=\"0\" align=\"absmiddle\"></a>");
  		}
           out.print("</td>");
           out.print("<td>"); 
           out.print("</td>");
           out.print("<td <a href='#' accesskey='a' "+strOnclickEventAso+" \"><img src='/ttk/images/AddIcon.gif' ALT='Add new rule for Capitation-YES' title='Add new rule for Capitation-YES' width=13 height=13 border='0' align='absmiddle'></a>"); 
           out.print("</td>");
           out.print("<td <A accessKey=o href='#' "+strDeleteEventASPLUS+" \" ><IMG border=0 alt='Delete' title='Delete' src='/ttk/images/DeleteIcon.gif' width=16 height=16></A>");
           out.print("</td>");
           out.print("</tr>"); 
   		   out.print("</table>");
           
           out.print("<table align='center' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0' style='overflow:scroll'>");
           out.print(capitationData); 
           out.print("</table>");
           
           out.println("<table align='center' class='buttonsContainer' border='0' cellspacing='0' cellpadding='0'>");
           out.print("<tr>"); 
           out.print("<td width='100%' align='center'>"); 
           out.print("<button type='button' name='Button2' accesskey='s' class='buttons' onMouseout='this.className='buttons'' onMouseover='this.className='buttons buttonsHover'' onClick='javascript:onSaveAsPlus();'><u>S</u>ave</button>");
           
           out.print("&nbsp;&nbsp;&nbsp;&nbsp;<button type='button' name='Button2' accesskey='c' class='buttons' onMouseout='this.className='buttons'' onMouseover='this.className='buttons buttonsHover'' onClick='javascript:onClose();'><u>C</u>lose</button>");  
           out.print("</td>"); 
           out.print("</tr>"); 
    		   out.print("</table>"); 
            out.print("</fieldset>"); 
	        }//if(alActivityDetails != null)
	        else
	        {	        
		        out.print("<fieldset>"); 	
			    out.print("<legend>ASO Policy&nbsp;&nbsp;<a href='#' accesskey='a' onClick='javascript:addconfigurationDetailsASO()'><img src='/ttk/images/AddIcon.gif' ALT='Add Capitation-NO' title='Add Capitation-NO' width=13 height=13 border='0' align='absmiddle'></a></legend>");
                out.print("<table align='center' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0'>");
                nonCapitationData.append("<tr>");
                nonCapitationData.append("<td align='center'>NO DATA</td>");
                nonCapitationData.append("</tr>");
	            out.print(nonCapitationData);   
	        	out.print("</table>"); 
		           out.println("<table align='center' class='buttonsContainer' border='0' cellspacing='0' cellpadding='0'>");
		           out.print("<tr>"); 
		           out.print("<td width='100%' align='center'>"); 
		           out.print("<button type='button' name='Button2' accesskey='c' class='buttons' onMouseout='this.className='buttons'' onMouseover='this.className='buttons buttonsHover'' onClick='javascript:onClose();'><u>C</u>lose</button>"); 
		           out.print("</td>"); 
		           out.print("</tr>"); 
		    		   out.print("</table>"); 
		            out.print("</fieldset>"); 
	        	
	        	
		            
		            
		            out.print("<fieldset>"); 	
		            out.print("<legend>AS Plus(OP/IP&OP) Policy&nbsp;&nbsp;<a href='#' accesskey='a' onClick='javascript:javascript:addconfigurationDetailsASPlus()'><img src='/ttk/images/AddIcon.gif' ALT='Add Capitation-Yes' title='Add Capitation-Yes' width=13 height=13 border='0' align='absmiddle'></a></legend>");
		            
		           out.print("<table align='center' class='gridWithCheckBox' border='0' cellSpacing='0' cellPadding='0'>");
		           capitationData.append("<tr>");
		           capitationData.append("<td align='center'>NO DATA</td>");
		           capitationData.append("</tr>");
	        	  out.print(capitationData); 
		           out.print("</table>"); 
		           out.println("<table align='center' class='buttonsContainer' border='0' cellspacing='0' cellpadding='0'>");
		           out.print("<tr>"); 
		           out.print("<td width='100%' align='center'>"); 
		           out.print("<button type='button' name='Button2' accesskey='c' class='buttons' onMouseout='this.className='buttons'' onMouseover='this.className='buttons buttonsHover'' onClick='javascript:onClose();'><u>C</u>lose</button>"); 
		           out.print("</td>"); 
		           out.print("</tr>"); 
		    		   out.print("</table>"); 
		            out.print("</fieldset>"); 
	        }

	        
	        
	        /*   out.print("</fieldset>"); */
	           
	           
	         /*  out.print("<fieldset>"); */
	          
	         
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
	
}//end of DiagnosisDetails class 
