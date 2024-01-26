/**
 * @ (#) ActivityDetails.java July 18, 2015
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
package com.ttk.common.tags.preauth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;
import org.apache.struts.action.DynaActionForm;
import org.dom4j.Document;
import org.dom4j.Node;

import com.ttk.dto.preauth.ActivityDetailsVO;
//import org.apache.log4j.Logger;
import com.ttk.dto.preauth.CeedResponseVo;

public class ActivityDetails extends TagSupport
{
	private String flow;
	/**
	* Comment for <code>serialVersionUID</code>
	*/
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger( ActivityDetails.class );
	
	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException{
	ArrayList<ActivityDetailsVO> alActivityDetails=null;
		try
		{
			String strPreAuthRecvTypeID=null;
			String strModeOfClaim=null;
			
			DynaActionForm frmPreAuthGeneral =(DynaActionForm)pageContext.getSession().getAttribute("frmPreAuthGeneral");
			DynaActionForm frmClaimGeneral =(DynaActionForm)pageContext.getSession().getAttribute("frmClaimGeneral");
			
			if("CLM".equalsIgnoreCase(getFlow()))
			{
				strModeOfClaim = (String) frmClaimGeneral.getString("modeOfClaim");
			}
			
			if("PAT".equalsIgnoreCase(getFlow()))
			{
				strPreAuthRecvTypeID = (String) frmPreAuthGeneral.getString("preAuthRecvTypeID");
			}
			
			if("CLM".equalsIgnoreCase(getFlow()))
			  alActivityDetails=(ArrayList<ActivityDetailsVO>)frmClaimGeneral.get("activityList");
			else if("PAT".equalsIgnoreCase(getFlow()))
			 alActivityDetails= (ArrayList<ActivityDetailsVO>)frmPreAuthGeneral.get("activityList");
				
	        JspWriter out = pageContext.getOut();//Writer Object to write the File
	       String gridOddRow="'gridOddRow'";
	       String gridEvenRow="'gridEvenRow'";
	        if(alActivityDetails != null){
	        	if(alActivityDetails.size()>=1){
	        		if("PAT".equals(getFlow())){
	        	
	                		    out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:100%;height:auto;'>");
	                			out.print("<tr>");
	                			out.print("<th align='center' class='gridHeader' title='Serial Number'>S.No</th>");
	                			out.print("<th align='center' class='gridHeader' title='Activity Code/Service Code'>Act/Serv Code</th>");
	                			
	                			out.print("<th align='center' class='gridHeader' title='Activity Code/Service Code'>Act Code <br>Type</th>");				
	                			
	                			//out.print("<th align='center' class='gridHeader' title='Modifier'>Mod</th>");
	                			//out.print("<th align='center' class='gridHeader' title='Unit Type'>Unt Type</th>");	                			
	                			out.print("<th align='center' class='gridHeader' title='Quantity'>Qty</th>");
	                			out.print("<th align='center' class='gridHeader' title='Approved Quantity'>Apr Qty</th>");
	                			out.print("<th align='center' class='gridHeader' title='Start Date'>Str Date</th>");
		                		out.print("<th align='center' class='gridHeader' title='Provider Requested Amount'>Req Amt</th>");		// new
	                			out.print("<th align='center' class='gridHeader' title='Gross Amount'>Grs Amt</th>");
	                			out.print("<th align='center' class='gridHeader' title='Discount Amount'>Disc Amt</th>");
	                			out.print("<th align='center' class='gridHeader' title='Discount Gross Amount'>Disc Grs</th>");
	                			out.print("<th align='center' class='gridHeader' title='Patient Share'>Ptn Share</th>");
	                			out.print("<th align='center' class='gridHeader' title='Net Amount'>Net Amt</th>");
	                			out.print("<th align='center' class='gridHeader' title='Dis Allowed Amount'>DisAlw Amt</th>");
	                			out.print("<th align='center' class='gridHeader' title='Allowed Amount'>Alw Amt</th>");
	                			out.print("<th align='center' class='gridHeader' title='Denial Code'>Den Code</th>");
	                			//out.print("<th align='center' class='gridHeader' title='Remarks'>Remarks</th>");
	                			//out.print("<th align='center' class='gridHeader' title='CEED Alert Type'>CEED</th>");
	                			out.print("<th align='center' class='gridHeader' title='Observations'>Observ</th>");
	                			out.print("<th align='center' class='gridHeader' title='Delete Activity Code'>Delt</th>");
	                			out.print("<th align='center' class='gridHeader' title='Override Activity Code'>Ovr</th>");
	                	//		out.print("<th align='center' class='gridHeader' title='View Activity Document'>Document</th>");
	                			out.print("</tr>");
	        	
	                			int i=0;
	                			String delImage="<img src='/ttk/images/DeleteIcon.gif' alt='Delete Activity Details' width='12' height='12' border='0'>";
	                			String addImage="<img src='/ttk/images/EditIcon.gif' alt='Add Observ Details' width='16' height='16' border='0' align='absmiddle'>";
	                			String overrideImg="<img src='ttk/images/Overridebutton.gif' alt='Override' width='50' height='17' border='0' align='absbottom'>";
	                			String overriddenImg="<img src='ttk/images/Overridebutton.gif' alt='Overridden' width='50' height='17' border='0' align='absbottom' style='background-color: green;'>";
	                			String viewDoc="<button value='View Document'>View Doc</button>";
	                			String imageDisabledPath="<img src='/ttk/images/DeleteIcon.gif'  width='12' height='12' border='0'>";
	                			String addImage2="<img src='/ttk/images/rsz_observation_red_pencil.jpg' alt='Add Observ Details' width='16' height='16' border='0' align='absmiddle'>";
	                			//CEED S T A R T S
	                			
	                			List<Node> claimEditNode		=	(List<Node>) (pageContext.getSession().getAttribute("claimEditNode")==null? new ArrayList<>():pageContext.getSession().getAttribute("claimEditNode"));
	                			Document ceedResponseDoc		=	(Document) (pageContext.getSession().getAttribute("ceedResponse"));
	                        	
	                			
	                			String reviewOrAlert			=	"";
	                			String reviewOrAlertTitle		=	"";
	                			String[] editDetals				=	new String[7];
	                			if(ceedResponseDoc!=null)
	                				if(claimEditNode.size()==0)
	                    				reviewOrAlert	=	"OK";
	                			
	                			//CEED E N D S
	                for(ActivityDetailsVO activityDetails:alActivityDetails){
	                	//CEED S T A R T S
	                /*	for(Node claimEditNodes : claimEditNode){ 
	        	        	List<Node> claimEditEditNode			=claimEditNodes.selectNodes("./Edit");	//ceedResponseDoc.selectNodes(claimEditNodes.getPath()+"/Edit");
	        	        	for(Node claimEditEditNodes : claimEditEditNode){
	        	        	List<Node> claimActivityEditNode		=claimEditNodes.selectNodes("./ActivityEdit");	//ceedResponseDoc.selectNodes(claimEditNodes.getPath()+"/ActivityEdit");
	        	        	for(Node claimActivityEditNodes : claimActivityEditNode){
	        	        		reviewOrAlert	=	"OK";
	        	        		//CONDITIONS
	        	        		System.out.println("claimActivityEditNodes.valueOf ID::"+claimActivityEditNodes.valueOf("ID"));
	        	        		System.out.println("activityDetails.getActivitySeqId()::"+activityDetails.getActivitySeqId());
	        	        		System.out.println("claimActivityEditNodes.valueOf(EditId)::"+claimActivityEditNodes.valueOf("EditId"));
	        	        		System.out.println("claimEditEditNodes.valueOf (ID)::"+claimEditEditNodes.valueOf("ID"));
	        		        	if(claimActivityEditNodes.valueOf("ID").equals(""+activityDetails.getActivitySeqId()))
	        		        	{
	        		        		if(claimActivityEditNodes.valueOf("EditId").equals(claimEditEditNodes.valueOf("ID")))
	        		        		{
	        		        			reviewOrAlert	=	claimEditEditNodes.valueOf("Rank");
	        		        			if("R".equals(reviewOrAlert))
	        		        				reviewOrAlert	=	"Review";
	        		        			else if("A".equals(reviewOrAlert))
	        		        				reviewOrAlert	=	"Alert";
	        		        			reviewOrAlertTitle=	claimEditEditNodes.valueOf("Comment");
	        		        			editDetals[0]	=	claimEditEditNodes.valueOf("ID");
	        		        			editDetals[1]	=	claimEditEditNodes.valueOf("Type");
	        		        			editDetals[2]	=	claimEditEditNodes.valueOf("SubType");
	        		        			editDetals[3]	=	claimEditEditNodes.valueOf("Code");
	        		        			editDetals[4]	=	claimEditEditNodes.valueOf("Comment");
	        		        			editDetals[5]	=	claimActivityEditNodes.valueOf("ID");
	        		        			editDetals[6]	=	claimActivityEditNodes.valueOf("EditId");
	    		        				}
	    		        			}
	    	        			}
	        	        	}
	        	        }
	                	pageContext.getSession().setAttribute("editDetals", editDetals);
	                */	//CEED E N D S
	                		out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
	                		out.print("<td align='cenetr'>"+(i+1)+"</td>");
	                		out.print("<td align='center'><a href='#' accesskey='a' title=\""+activityDetails.getActivityCodeDesc()+activityDetails.getActivityServiceCodeDesc()+"\" onClick=\"javascript:editActivityDetails('"+activityDetails.getActivityDtlSeqId()+"');\">"+activityDetails.getActivityCode()+activityDetails.getActivityServiceCode()+"</a></td>");
	                		//out.print("<td align='center'>"+activityDetails.getModifier()+"</td>");
	                		//out.print("<td align='center'>"+activityDetails.getUnitType()+"</td>");
	                		
	                		out.print("<td align='center'>"+activityDetails.getActivityTypeCodeDesc()+"</td>");
	                		
	                		out.print("<td align='center'>"+activityDetails.getQuantity()+"</td>");
	                		out.print("<td align='center'>"+(activityDetails.getApprovedQuantity())+"</td>");
	                		out.print("<td align='center'>"+activityDetails.getStartDate()+"</td>");
	                		out.print("<td align='center'>"+activityDetails.getPreauthRequestAmt()+"</td>");
	                		out.print("<td align='center'>"+activityDetails.getGrossAmount()+"</td>");
	                		out.print("<td align='center'>"+activityDetails.getDiscount()+"</td>");
	                		out.print("<td align='center'>"+activityDetails.getDiscountedGross()+"</td>");
	                		out.print("<td align='center'>"+activityDetails.getPatientShare()+"</td>");
	                		out.print("<td align='center'>"+activityDetails.getNetAmount()+"</td>");
	                		out.print("<td align='center'>"+activityDetails.getDisAllowedAmount()+"</td>");
	                		out.print("<td align='center'>"+activityDetails.getApprovedAmount()+"</td>");
	                		
	                		out.print("<td align='center'>");
	                		//out.print("<table>");
	                		String denialDec=activityDetails.getDenialDescription();//denial_desc
	        				String denialCode=activityDetails.getDenialCode();//denial_code
	        				
	        				String []descs=denialDec.split("[;]");
	        				String []codes=denialCode.split("[;]");
	        				if(codes!=null&&codes.length>=1&&descs!=null&&descs.length>=1){
	        				for(int j=0;j<codes.length;j++){
	        					try{
	        					//out.print("<tr style=''><td title=\""+descs[j]+"\">"+codes[j]+"</td></tr>");
	        					if(codes[j].length()>1)	out.println("<div style='text-decoration: underline;color:blue;' title=\""+descs[j]+"\"><a href='#'>"+codes[j]+";</a></div>");
	        						
	        					}catch(Exception e){
	        						System.err.println("Error occurred in ActivityDetails.java cause Activity code description  is not present");
	        						//log.error("Error Activity code description  is not present ", e);
	        						}
	        					}//for(int j=0;j<codes.length;j++)
	        				}//if(codes!=null&&codes.length>=1&&descs!=null&&descs.length>=1)
	        				  
	        				//out.print("</table>");
	        				   out.print("</td>");
							   /*
	        				   if("OK".equals(reviewOrAlert))
	        			           out.print("<td align='center' title=\""+reviewOrAlertTitle+"\">"+reviewOrAlert+"</td>");
	        				   else
	        					   out.print("<td align='center' title=\""+reviewOrAlertTitle+"\"><a href='#' accesskey='o'  onClick=\"javascript:editActivityDetails('"+activityDetails.getActivityDtlSeqId()+"');\">"+reviewOrAlert+"</a></td>");
	        				 */
							 
							 // String remarks=activityDetails.getRemarks()==null?"":activityDetails.getActivityRemarks();
	        				 // String rmrks=(remarks.length()>6)?remarks.substring(0, 5)+"...":remarks;
	        				  
	                		//out.print("<td align='center'><a href='#' accesskey='a' title=\""+remarks+"\" style='text-decoration: none;'>"+rmrks+"</a></td>");
	                		
	                		//	activities.add(new String[]{1sNo+"",2activityCode,3modifiers,4unityType,5quantity,6startDate,7grossAmt,8discount,9discountGross,10patientShare,11netAmt,12approAmt,13denialDec,14remarks,15activitySeqId,16activityDtlSeqId,17allowedAmt,18approvedQuantity,19activityCodeDec,20denialCode,21rmrks,22codesAndDescs});
	                //out.print("<td align='center'><a href='#' accesskey='o'  onClick=\"javascript:addObservs('"+activityDetails.getActivityCodeSeqId()+"');\">"+addImage+"</a></td>");
	        				   if(("Y").equals(activityDetails.getObservationFlag()))
	        		    		{
	        		    			out.print("<td align='center'><a href='#' accesskey='o'  onClick=\"javascript:addObservs('"+activityDetails.getActivityDtlSeqId()+"');\">"+addImage2+"</a></td>");
	        		    		}
	        		    		if(("N").equals(activityDetails.getObservationFlag()))
	        		    		{
	        		    			out.print("<td align='center'><a href='#' accesskey='o'  onClick=\"javascript:addObservs('"+activityDetails.getActivityDtlSeqId()+"');\">"+addImage+"</a></td>");
	        		    		}
	        				   
	                //out.print("<td align='center'><a href='#' accesskey='o'  onClick=\"javascript:deleteActivityDetails('"+activityDetails.getActivityCodeSeqId()+"');\">"+delImage+"</a></td>");
	                
	                if(("DHP").equals(strPreAuthRecvTypeID) || ("ONL1").equals(strPreAuthRecvTypeID))
	                	out.print("<td align='center'>"+imageDisabledPath+"</td>");
	                else
	                	out.print("<td align='center'><a href='#' accesskey='o'  onClick=\"javascript:deleteActivityDetails('"+activityDetails.getActivityDtlSeqId()+"');\">"+delImage+"</a></td>");
	                
	           
	                if("Y".equals(activityDetails.getOverrideYN())){
		               	
		                 if("Y".equals(activityDetails.getOverrideAllowYN()))
		               		 out.print("<td align='center' style='background-color: green;'><a href='#' accesskey='a'  onClick=\"javascript:overrideNotAllow();\">"+overriddenImg+"</a></td>");
		               	 else 
		               		 out.print("<td align='center' style='background-color: green;'><a href='#' accesskey='a'  onClick=\"javascript:overrideActivityDetails('"+activityDetails.getActivityDtlSeqId()+"');\">"+overriddenImg+"</a></td>"); 
		               	 
		               }else{
		               	if("Y".equals(activityDetails.getOverrideAllowYN()))
		               		out.print("<td align='center'><a href='#' accesskey='a'  onClick=\"javascript:overrideNotAllow();\">"+overriddenImg+"</a></td>");
		              	else 
		              	   out.print("<td align='center'><a href='#' accesskey='a'  onClick=\"javascript:overrideActivityDetails('"+activityDetails.getActivityDtlSeqId()+"');\">"+overrideImg+"</a></td>");	
		              }
	                	
	                
	               /* if(activityDetails.getActivityId()!=null&&activityDetails.getActivityId()!=""&&"DHP".equals(frmPreAuthGeneral.getString("preAuthRecvTypeID")))
	                	out.print("<td align='center'><a href='#' accesskey='a'  onClick=\"javascript:onViewDocument('"+activityDetails.getActivityId()+"');\">"+viewDoc+"</a></td>");
	                else out.print("<td align='center'>NO Document</td>");*/
	                out.print("</tr>");
	                		i++;
	                			}//for(ActivityDetailsVO activityDetails:alActivityDetails)<logic:equal value="DHP" name="frmPreAuthGeneral" property="preAuthRecvTypeID">
	               out.print("</table>");
	        		}//if("PAT".equals(getFlow()))
	        		else if("CLM".equals(getFlow())){

            		    out.print("<table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:100%;height:auto;'>");
            			out.print("<tr>");
            			out.print("<th align='center' class='gridHeader' title='Serial Number'>S.No</th>");
            			out.print("<th align='center' class='gridHeader' title='PreAuth Number'>Pre.No</th>");
            			out.print("<th align='center' class='gridHeader' title='Activity Code'>Act Code</th>");  
            			
            			out.print("<th align='center' class='gridHeader' title='Activity Code/Service Code'>Act Code <br>Type</th>");
            			
            			out.print("<th align='center' class='gridHeader' title='Unit Type'>Unt Type</th>");
            			out.print("<th align='center' class='gridHeader' title='Quantity'>Qty</th>");
            			out.print("<th align='center' class='gridHeader' title='Approved Quantity'>Apr Qty</th>");
            			out.print("<th align='center' class='gridHeader' title='Start Date'>Str Date</th>");
            			out.print("<th align='center' class='gridHeader' title='Provider Requested Amount'>Req Amt</th>");
            			out.print("<th align='center' class='gridHeader' title='Gross Amount'>Grs Amt</th>");
            			out.print("<th align='center' class='gridHeader' title='Discount Amount'>Disc Amt</th>");
            			out.print("<th align='center' class='gridHeader' title='Discount Gross Amount'>Disc Grs</th>");
            			out.print("<th align='center' class='gridHeader' title='Patient Share'>Ptn Share</th>");            			
            			out.print("<th align='center' class='gridHeader' title='Net Amount'>Net Amt</th>");
            			out.print("<th align='center' class='gridHeader' title='Dis Allowed Amount'>DisAlw Amt</th>");
            			out.print("<th align='center' class='gridHeader' title='Allowed Amount'>Alw Amt</th>");
            			
            			out.print("<th align='center' class='gridHeader' title='Allowed Amount(+VAT)'>Alw Amt(+VAT)</th>");
            			
            			out.print("<th align='center' class='gridHeader' title='Denial Code'>Den Code</th>");
            			//out.print("<th align='center' class='gridHeader' title='Remarks'>Remarks</th>");
            			out.print("<th align='center' class='gridHeader' title='CEED Alert Type'>CEED</th>");
            			out.print("<th align='center' class='gridHeader' title='Observations'>Observ</th>");
            			out.print("<th align='center' class='gridHeader' title='Delete Activity Code'>Delt</th>");
            			out.print("<th align='center' class='gridHeader' title='Override Activity Code'>Ovr</th>");
            			out.print("</tr>");
    	
            			int i=0;
            			String delImage="<img src='/ttk/images/DeleteIcon.gif' alt='Delete Activity Details' width='12' height='12' border='0'>";
            			String addImage="<img src='/ttk/images/EditIcon.gif' alt='Add Observ Details' width='16' height='16' border='0' align='absmiddle'>";
            			String overrideImg="<img src='ttk/images/Overridebutton.gif' alt='Override' width='50' height='17' border='0' align='absbottom'>";
            			String overriddenImg="<img src='ttk/images/Overridebutton.gif' alt='Overridden' width='50' height='17' border='0' align='absbottom' style='background-color: green;'>";
            			String imageDisabledPath="<img src='/ttk/images/DeleteIcon.gif'  width='12' height='12' border='0'>";	
            			String addImage2="<img src='/ttk/images/rsz_observation_red_pencil.jpg' alt='Add Observ Details' width='16' height='16' border='0' align='absmiddle'>";
            			//List<Node> claimEditNode		=	(List<Node>) (pageContext.getSession().getAttribute("claimEditNode")==null? new ArrayList<>():pageContext.getSession().getAttribute("claimEditNode"));
            			//Document ceedResponseDoc		=	(Document) (pageContext.getSession().getAttribute("ceedResponse"));
                    	HashMap<String,CeedResponseVo> ceedResMapData=(HashMap<String, CeedResponseVo>) pageContext.getSession().getAttribute("ceedResMapData");
            			
            			String reviewOrAlert	=	"";
            			String reviewOrAlertTitle		=	"";
            			//String[] editDetails			=	null;
            			
            for(ActivityDetailsVO activityDetails:alActivityDetails){
            	/*if(ceedResponseDoc!=null)
    				if(claimEditNode.size()==0)
        				reviewOrAlert	=	"OK";
    				else{
    						reviewOrAlert		=	"";
    						reviewOrAlertTitle	=	"";
    				}
            	for(Node claimEditNodes : claimEditNode){
            		
    	        	List<Node> claimEditEditNode			=claimEditNodes.selectNodes("./Edit");		//ceedResponseDoc.selectNodes(claimEditNodes.getPath()+"/Edit");
    	        	if(claimEditEditNode.size()!=0)
    	        		reviewOrAlert		=	"OK";
    	        	for(Node claimEditEditNodes : claimEditEditNode){
    		        	
    	        	List<Node> claimActivityEditNode		=claimEditNodes.selectNodes("./ActivityEdit");	//ceedResponseDoc.selectNodes(claimEditNodes.getPath()+"/ActivityEdit");
    	        	for(Node claimActivityEditNodes : claimActivityEditNode){
    	        		//CONDITIONS
    		        	if(claimActivityEditNodes.valueOf("ID").equals(""+activityDetails.getActivitySeqId()))
    		        	{
    		        		if(claimActivityEditNodes.valueOf("EditId").equals(claimEditEditNodes.valueOf("ID")))
    		        		{

    		        			reviewOrAlert	=	claimEditEditNodes.valueOf("Rank");
    		        			if("R".equals(reviewOrAlert))
    		        				reviewOrAlert	=	"Review";
    		        			else if("A".equals(reviewOrAlert))
    		        				reviewOrAlert	=	"Alert";
    		        			reviewOrAlertTitle=	claimEditEditNodes.valueOf("Comment");
    		        			editDetails		=	new String[7];
    		        			editDetails[0]	=	claimEditEditNodes.valueOf("ID");
    		        			editDetails[1]	=	claimEditEditNodes.valueOf("Type");
    		        			editDetails[2]	=	claimEditEditNodes.valueOf("SubType");
    		        			editDetails[3]	=	claimEditEditNodes.valueOf("Code");
    		        			editDetails[4]	=	claimEditEditNodes.valueOf("Comment");
    		        			editDetails[5]	=	claimActivityEditNodes.valueOf("ID");
    		        			editDetails[6]	=	claimActivityEditNodes.valueOf("EditId");
    		        			pageContext.getSession().setAttribute("editDetails"+claimActivityEditNodes.valueOf("ID"), editDetails);
		        				}
		        			}
	        			}
    	        	}
    	        }
*/            	//pageContext.getSession().setAttribute("editDetails", editDetails);
            	
            		out.print("<tr class="+(i%2==0?gridEvenRow:gridOddRow)+">");
            		out.print("<td align='cenetr'>"+(i+1)+"</td>");
            		out.print("<td align='center'>"+activityDetails.getPreAuthNo()+"</td>");
            		out.print("<td align='center'><a href='#' accesskey='a' title=\""+activityDetails.getActivityCodeDesc()+"\" onClick=\"javascript:editActivityDetails('"+activityDetails.getActivityDtlSeqId()+"');\">"+activityDetails.getActivityCode()+"</a></td>");
            		
            		out.print("<td align='center'>"+activityDetails.getActivityTypeCodeDesc()+"</td>");
            		
            		out.print("<td align='center'>"+activityDetails.getUnitType()+"</td>");
            		out.print("<td align='center'>"+activityDetails.getQuantity()+"</td>");
            		out.print("<td align='center'>"+(activityDetails.getApprovedQuantity())+"</td>");
            		out.print("<td align='center'>"+activityDetails.getStartDate()+"</td>");
            		out.print("<td align='center'>"+activityDetails.getConvertedProviderReqAmt()+"</td>");
            		out.print("<td align='center'>"+activityDetails.getGrossAmount()+"</td>");
            		out.print("<td align='center'>"+activityDetails.getDiscount()+"</td>");
            		out.print("<td align='center'>"+activityDetails.getDiscountedGross()+"</td>");
            		out.print("<td align='center'>"+activityDetails.getPatientShare()+"</td>");            		
            		out.print("<td align='center'>"+activityDetails.getNetAmount()+"</td>");
            		out.print("<td align='center'>"+activityDetails.getDisAllowedAmount()+"</td>");
            		out.print("<td align='center'>"+activityDetails.getApprovedAmount()+"</td>");
            		
            		
            		out.print("<td align='center'>"+activityDetails.getAllowedAmtVatAdded()+"</td>");
            		
            		out.print("<td align='center'>");
            		//out.print("<table>");
            		
            		String denialDec=activityDetails.getDenialDescription();//denial_desc
    				String denialCode=activityDetails.getDenialCode();//denial_code
    				
    				String []descs=denialDec.split("[;]");
    				String []codes=denialCode.split("[;]");
    				if(codes!=null&&codes.length>=1&&descs!=null&&descs.length>=1){
    				for(int j=0;j<codes.length;j++){
    					try{
    					//out.print("<tr style=''><td title=\""+descs[j]+"\">"+codes[j]+"</td></tr>");
    					if(codes[j].length()>1)	out.println("<div style='text-decoration: underline;color:blue;' title=\""+descs[j]+"\"><a href='#'>"+codes[j]+";</a></div>");
    						
    					}catch(Exception e){
    						System.err.println("Error occurred in ActivityDetails.java cause Activity code description  is not present");
    						//log.error("Error Activity code description  is not present ", e);
    						}
    					}//for(int j=0;j<codes.length;j++)
    				}//if(codes!=null&&codes.length>=1&&descs!=null&&descs.length>=1)
    				  
    				//out.print("</table>");
    				   out.print("</td>");
    				   
    				 // String remarks=activityDetails.getRemarks()==null?"":activityDetails.getActivityRemarks();
    				 // String rmrks=(remarks.length()>6)?remarks.substring(0, 5)+"...":remarks;
    				  
            		//out.print("<td align='center'><a href='#' accesskey='a' title=\""+remarks+"\" style='text-decoration: none;'>"+rmrks+"</a></td>");
            		
            		//	activities.add(new String[]{1sNo+"",2activityCode,3modifiers,4unityType,5quantity,6startDate,7grossAmt,8discount,9discountGross,10patientShare,11netAmt,12approAmt,13denialDec,14remarks,15activitySeqId,16activityDtlSeqId,17allowedAmt,18approvedQuantity,19activityCodeDec,20denialCode,21rmrks,22codesAndDescs});
            //out.print("<td align='center'><a href='#' accesskey='o'  onClick=\"javascript:addObservs('"+activityDetails.getActivityCodeSeqId()+"');\">"+addImage+"</a></td>");
    				   if(ceedResMapData!=null&&ceedResMapData.size()>0)
    				   {
    				   CeedResponseVo ceedResponseVo=ceedResMapData.get(String.valueOf(activityDetails.getActivitySeqId()));
    				   if(ceedResponseVo!=null)
    				   {
    				   reviewOrAlert=ceedResponseVo.getReviewOrAlert();
    				   reviewOrAlertTitle=ceedResponseVo.getReviewOrAlertTitle();
    				   }
    				   else{
    					   reviewOrAlert="OK";
    				   }
    				   }
    				   else {
    					   if("1".equals(pageContext.getSession().getAttribute("CEEDResult"))){
    						   reviewOrAlert="OK";   
    					   }
    				   }
    				   if("OK".equals(reviewOrAlert))
    			           out.print("<td align='center' title=\""+reviewOrAlertTitle+"\">"+reviewOrAlert+"</td>");
    				   else
    					   out.print("<td align='center' title=\""+reviewOrAlertTitle+"\"><a href='#' accesskey='o'  onClick=\"javascript:editActivityDetails('"+activityDetails.getActivityDtlSeqId()+"');\">"+reviewOrAlert+"</a></td>");
    				   
    		if(("Y").equals(activityDetails.getObservationFlag()))
    		{
    			out.print("<td align='center'><a href='#' accesskey='o'  onClick=\"javascript:addObservs('"+activityDetails.getActivityDtlSeqId()+"');\">"+addImage2+"</a></td>");
    		}
    		if(("N").equals(activityDetails.getObservationFlag()))
    		{
    			out.print("<td align='center'><a href='#' accesskey='o'  onClick=\"javascript:addObservs('"+activityDetails.getActivityDtlSeqId()+"');\">"+addImage+"</a></td>");
    		}
    				   
            //out.print("<td align='center'><a href='#' accesskey='o'  onClick=\"javascript:deleteActivityDetails('"+activityDetails.getActivityCodeSeqId()+"');\">"+delImage+"</a></td>");
    		if(("ECL").equals(strModeOfClaim))
    			out.print("<td align='center'>"+imageDisabledPath+"</td>");	
    		else
    		out.print("<td align='center'><a href='#' accesskey='o'  onClick=\"javascript:deleteActivityDetails('"+activityDetails.getActivityDtlSeqId()+"');\">"+delImage+"</a></td>");
           
            
            
            
            
            String strParms="'"+activityDetails.getActivityDtlSeqId()+"','"+activityDetails.getGlobalFlagYN()+"','"+activityDetails.getGeneralFlagYN()+"','"+activityDetails.getClinicalFlagYN()+"'";
            String strOnclickEvent=" onClick=\"javascript:overrideActivityDetails("+strParms+");";
            
            if("Y".equals(activityDetails.getOverrideYN())){
            	 if("Y".equals(activityDetails.getOverrideAllowYN()))
            		 out.print("<td align='center' style='background-color: green;'><a href='#' accesskey='a'  onClick=\"javascript:overrideNotAllow();\">"+overriddenImg+"</a></td>");
            	 else 
            		 out.print("<td align='center' style='background-color: green;'><a href='#' accesskey='a' "+strOnclickEvent+" \">"+overriddenImg+"</a></td>"); 
            	 
            }else{
            	if("Y".equals(activityDetails.getOverrideAllowYN()))
            		out.print("<td align='center'><a href='#' accesskey='a'  onClick=\"javascript:overrideNotAllow();\">"+overriddenImg+"</a></td>");
           	   else 
           	   out.print("<td align='center'><a href='#' accesskey='a' "+strOnclickEvent+" \">"+overrideImg+"</a></td>");	
           }
            
            
            
            out.print("</tr>");
            		i++;
            			}//for(ActivityDetailsVO activityDetails:alActivityDetails)
           out.print("</table>");    		
	        		}
	        	}//if(alActivityDetails.size()>=1)
	        }//if(alActivityDetails != null)
		}//end of try
		catch(Exception exp)
        {
			log.error(exp);
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
