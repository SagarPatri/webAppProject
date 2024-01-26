<%@page import="com.ttk.dto.preauth.ActivityDetailsVO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.dom4j.Document"%>
<%@page import="org.dom4j.Node"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>

<!DOCTYPE html> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activity Details</title>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
  <script type="text/javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
  <script type="text/javascript" src="/ttk/scripts/claims/activitydetails.js"></script>	
<script type="text/javascript">
var JS_Focus_Disabled =true;
</script>
</head>
<body>
<%
pageContext.setAttribute("haadCategory", Cache.getCacheObject("haadCategory"));
pageContext.setAttribute("category", Cache.getCacheObject("category"));
/* pageContext.setAttribute("denialDescriptions", Cache.getCacheObject("denialDescriptions")); */
pageContext.setAttribute("modifiers", Cache.getCacheObject("modifiers"));
pageContext.setAttribute("unitTypes", Cache.getCacheObject("unitTypes"));
boolean dateStatus=request.getSession().getAttribute("dateStatus")==null?false:new Boolean(request.getSession().getAttribute("dateStatus").toString());
boolean tariffStatus=false;
boolean providerNetAmtStatus=false;
boolean FlagStatus=false;
boolean haadStatus=true;
%>

 <logic:equal name="frmActivityDetails" property="haad_YN" value="Y">
	<%FlagStatus=true;%>			
 		
</logic:equal> 



<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral" scope="session">
<%providerNetAmtStatus=true;%>
</logic:equal>
<logic:equal name="frmActivityDetails" property="tariffYN" value="Y">
<%tariffStatus=true;%>
</logic:equal>


<logic:notEqual value="Y" name="frmActivityDetails" property="overrideYN">
	<%FlagStatus=true;%>
</logic:notEqual>



<logic:equal name="frmActivityDetails" property="haad_YN" value="Y">
		<logic:equal name="frmActivityDetails" property="edit_YN" value="Y">
			<%haadStatus=false;%>
 		</logic:equal>
</logic:equal>

<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				    <td width="57%">Activity Details-<logic:empty name="frmActivityDetails" property="activityDtlSeqId">New</logic:empty><logic:notEmpty name="frmActivityDetails" property="activityDtlSeqId">Edit</logic:notEmpty>[<bean:write name="frmActivityDetails" property="activityCode"/>]</td>
					<td  align="right" class="webBoard"></td>
			  </tr>
		</table>
<div class="contentArea" id="contentArea">
<html:errors/>
<logic:notEmpty name="frmActivityDetails" property="mohPatientShareErrMsg">
			<table align="center" class="errorContainer"  border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/warning.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:write name="frmActivityDetails" property="mohPatientShareErrMsg"/>
				  </td>
				</tr>
			</table>
</logic:notEmpty>
<logic:notEmpty name="successMsg" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:write name="successMsg" scope="request"/>
				  </td>
				</tr>
			</table>
</logic:notEmpty>
<logic:notEmpty name="activityTariffStatus" scope="request">
			<table align="center" class="errorContainer"  border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/warning.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:write name="activityTariffStatus" scope="request"/>
			  	  </td>
				</tr>
			</table>
		</logic:notEmpty>
<logic:notEmpty name="errorMsg" scope="request">
    <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/ttk/images/ErrorIcon.gif" alt="Error" width="16" height="16" align="absmiddle" >&nbsp;
          <bean:write name="errorMsg" scope="request" />
          </td>
      </tr>
    </table>
   </logic:notEmpty>
   <logic:notEmpty name="frmActivityDetails"  property="irdrgWarnMsg">
    <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/ttk/images/ErrorIcon.gif" alt="Error" width="16" height="16" align="absmiddle" >&nbsp;
          <bean:write name="frmActivityDetails" property="irdrgWarnMsg"/>
          </td>
      </tr>
    </table>
   </logic:notEmpty>
	
	<!-- S T A R T : Success Box -->
		<logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:message name="updated" scope="request"/>
				  </td>
				</tr>
			</table>
		</logic:notEmpty>
		 	
<html:form action="/SaveActivityDetailsAction.do">
<fieldset>
		<legend>Activity Details</legend>
		<fieldset>
		     <legend>General</legend>
				<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">				
			<tr>
			    <td width="22%" class="formLabel">Claim No.:</td>
			    <td width="30%" class="textLabelBold">
			    <html:text size="30" readonly="true" name="frmActivityDetails" property="claimNo" styleClass="disabledBox"  />
			    <html:hidden property="preAuthSeqID" name="frmActivityDetails" />
			    <html:hidden property="claimSeqID" name="frmActivityDetails" />
			    <html:hidden property="activitySeqId" name="frmActivityDetails" />
			    <html:hidden property="activityDtlSeqId" name="frmActivityDetails" />
			    <input type="hidden" name="providerSeqID" value="<bean:write name="frmClaimGeneral" property="providerSeqId"/>">
			   		  
			    </td>
			    <td width="22%" class="formLabel">
			    <logic:equal value="Y" name="frmActivityDetails" property="overrideYN">
			      Override
			    </logic:equal>
			    </td>
			    <td>
			     <logic:equal value="Y" name="frmActivityDetails" property="overrideYN">
			       <html:checkbox name="frmActivityDetails" property="overrideYN" styleId="overrideYN"  value="Y" />
			    </logic:equal>			   
			    </td>
			    </tr>
			     <logic:equal value="Y" name="frmActivityDetails" property="overrideYN">
			    <tr>
				   <td class="formLabel" colspan="1">Override Remarks: </td>
					  <td class="textLabel" colspan="3">
					  	<html:textarea name="frmActivityDetails" property="overrideRemarks" rows="2" cols="80" styleId="overrideDesc"  />
					  </td>					 
				</tr>
				</logic:equal>			   
			    <tr>
			     <td class="formLabel">Start Date:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  <table cellspacing="0" cellpadding="0">
			    	    <tr>
			    	    	<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral">
			    				<td><html:text name="frmActivityDetails" property="activityStartDate" styleClass="textBox textDate" maxlength="10" readonly="<%=dateStatus%>" /><A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmActivityDetails.activityStartDate',document.frmActivityDetails.activityStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
			    	   		</logic:notEqual>
			    	   		<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral">
			    				<td>
			    					<html:text name="frmActivityDetails" property="activityStartDate" styleClass="textBox textDate" maxlength="10" readonly="true" disabled="true" />
			    					<img src="/ttk/images/CalendarIcon.gif"  name="empDate" width="24" height="17" border="0" align="absmiddle">
			    				</td>	
			    	   		</logic:equal>
			    	   </tr>
			        </table>					  
			        </td>
			        <logic:equal value="Y" name="frmActivityDetails" property="networkProviderType">
			         <td class="formLabel">Clinician Id:</td>
			         <td class="textLabel">
			  <table>
			  <tr>
			  <td>
			  <html:text property="clinicianId" styleId="clinicianId" styleClass="textBox textBoxMedium disabledBox" readonly="true"/>	      	
			  </td>
			  <td>
			       <a href="#" accesskey="g"  onClick="javascript:selectClinician()" class="search"> <img src="/ttk/images/EditIcon.gif" alt="Select Clinician" width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
			  </td>
			  </tr>
			  </table>
			  </td>		    
			</logic:equal>	
			 <logic:equal value="N" name="frmActivityDetails" property="networkProviderType">
			   <td class="formLabel">Clinician Name:</td>
			      <td class="textLabel">
			  <html:text property="clinicianName" styleId="clinicianName" styleClass="textBox textBoxMedium disabledBox" readonly="true"/>	      	
			  </td>		    
			 </logic:equal>	  
			  </tr>
			  
			   <tr>
				  
				    <td class="formLabel">Category:<span class="mandatorySymbol">*</span></td>
				    <td class="textLabel">
          				 <html:select property="sCategory" name="frmActivityDetails" onchange="javascript:getActivityCodeDetails()"  styleClass="selectBox textBoxLarge" >
		    	 			<html:option value="">Select</html:option>
		    				 <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
  								<html:options collection="haadCategory"  property="cacheId" labelProperty="cacheDesc"/>
		     				 </logic:equal>
		   			 		 <logic:notEqual value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
  								<html:options collection="category"  property="cacheId" labelProperty="cacheDesc"/>
		    				 </logic:notEqual>
       					 </html:select>
       			 </td>
       			 <td></td>
       		 </tr>
			  
			  
				  <tr>				  
					  <td class="formLabel">Activity Code:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  <table>
					   <tr>
						   <logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral">
				          	 <td>
				           			<html:text name="frmActivityDetails" property="activityCode" styleId="activityCode" styleClass="textBox textBoxMedium" onblur="getActivityCodeDetails();"/>
				         	 </td>
				          	 <td>
				         	        <a href="#" accesskey="g"  onClick="javascript:selectActivityCode()" class="search"> <img src="/ttk/images/EditIcon.gif" alt="Select Activity Code" width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
				             </td>
				           </logic:notEqual>
				            <logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral">
				          	 <td>
				           			<html:text name="frmActivityDetails" property="activityCode" styleId="activityCode" styleClass="textBox textBoxMedium" onblur="getActivityCodeDetails();" readonly="true" />
				         	 </td>
				          	 <td>
				         	      <img src="/ttk/images/EditIcon.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;
				             </td>
				           </logic:equal>  
			            
			           <td>
			           <div id="activityResult1"><div id="activityResult2"></div></div>
			           </td>
			           </tr>
			         </table>	
					    <html:hidden property="activityCodeSeqId" styleId="activityCodeSeqId"/>
					    <html:hidden property="activityTypeId" styleId="activityTypeId"/>
					  </td>	
					   <td class="formLabel">Modifier:</td>
					  <td class="textLabel">
					  <html:select property="modifier" name="frmActivityDetails" styleClass="selectBox selectBoxMedium">
					  <html:option value="">Select from list</html:option>
					   <html:optionsCollection name="modifiers" label="cacheDesc" value="cacheId" />
					  </html:select>
					  </td>				  
				   </tr>
				   
				  
       
				    <tr>
				   <td class="formLabel" colspan="1">Description: </td>
					  <td class="textLabel" colspan="3">
					  	<html:textarea name="frmActivityDetails" property="activityCodeDesc" rows="2" cols="80" styleId="activityCodeDesc"  />
					  </td>					 
					  </tr>
					  <tr>
				   		<td class="formLabel" colspan="1">VBC Description: </td>
					  	<td class="textLabel" colspan="3">
					  	<html:textarea name="frmActivityDetails" property="VBCDescription" rows="2" cols="80"   />
					  </td>					 
					  </tr>
					  <tr>
					  <td class="formLabel">Unit Type: </td>
					  <td class="textLabel">
					  
					  <logic:equal value="Y" name="frmActivityDetails" property="networkProviderType">				  
					  <html:select property="unitType" name="frmActivityDetails" styleClass="selectBox selectBoxMedium" styleId="UnitType" onchange="calculateNetAmount(this);">
					  <html:option value="">Select from list</html:option>
					   <html:optionsCollection name="unitTypes" label="cacheDesc" value="cacheId" />
					  </html:select>
					  </logic:equal>
					   
					  <logic:notEqual value="Y" name="frmActivityDetails" property="networkProviderType">				  
					  <html:select property="unitType" name="frmActivityDetails" styleClass="selectBox selectBoxMedium" styleId="UnitType">
					  <html:option value="">Select from list</html:option>
					   <html:optionsCollection name="unitTypes" label="cacheDesc" value="cacheId" />
					  </html:select>
					  </logic:notEqual>
					  </td>
					  <td class="formLabel">Duration Of Medication(Days):</td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" maxlength="3" property="medicationDays" onkeyup="isNumeric(this);"  styleClass="textBox textBoxSmall"/>
					  </td>
					  </tr>
					  
					  <tr>
					  <td class="formLabel">Quantity:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					 	<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral">
					  		<html:text name="frmActivityDetails" property="quantity" styleId="quantity"  styleClass="textBox textBoxSmall" onkeyup="calculateNetAmount(this);"  />
					  	</logic:notEqual>
					  	<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral">
					  		<html:text name="frmActivityDetails" property="quantity" styleId="quantity"  styleClass="textBox textBoxSmall" onkeyup="calculateNetAmount(this);" readonly="true" disabled="true" />
					  	</logic:equal>
					  </td>
					  <td class="formLabel">Approved Quantity:</td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="approvedQuantity" styleId="approvedQuantity"  styleClass="textBox textBoxSmall"/>
					  </td>
					  </tr>
					  <tr>
					 <td class="formLabel">Internal Code: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="internalCode" styleClass="textBox textBoxMedium" onblur="getInternalCodeDetails();"/>
					  </td>
			        <td class="formLabel">Posology: </td>
					  <td class="textLabel">					  
					  <html:select property="posology" name="frmActivityDetails" styleClass="selectBox selectBoxMedium">
					  <html:option value="">Select from list</html:option>
					   <html:option value="1">OD</html:option>
                       <html:option value="2">BD</html:option>c
                       <html:option value="3">TDS</html:option>
                       <html:option value="4">QID</html:option>
					  </html:select>
					  </td>
					  </tr>
					  <tr>
					   <td class="formLabel">Package ID: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="packageId" styleClass="textBox textBoxMedium"  />
					  </td>	
					  <td class="formLabel">Bundle ID: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="bundleId" styleClass="textBox textBoxMedium"  />
					  </td>
					  </tr>
					  </table>
					  </fieldset>
		  <fieldset>
		   <legend>Tariff</legend>
		  <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		  
		<!-- start -->  
	<logic:equal name="frmActivityDetails" property="irdrgProcessYN" value="Y">  
		 <logic:equal name="frmActivityDetails" property="activityTypeId" value="9"> 
		 
		 <tr>
			<td class="formLabel">Base Rate: </td>
		 	<td class="textLabel">
			          <html:text name="frmActivityDetails" property="baseRate" styleClass="textBox textBoxMedium disabledBox"      readonly="true" />
			          <html:text name="frmActivityDetails" property="defaultAED" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" value="OMR"/>
			</td>
			<td class="formLabel">Negotiation Factor: </td>
		 	<td class="textLabel">
			          <html:text name="frmActivityDetails" property="negotiationFactor"    styleClass="textBox textBoxMedium disabledBox"      readonly="true" />
			</td>
		</tr>
		
		 <tr>
			<td class="formLabel">IRDRG Code's<br>Relative Weight </td>
		 	<td class="textLabel">
			          <html:text name="frmActivityDetails" property="irdrgRelativeWt"    styleClass="textBox textBoxMedium disabledBox"      readonly="true" />
			</td>
			<td class="formLabel">Types of IRDRG<br>Payment Process </td>
		 	<td class="textLabel">
			          <bean:write name="frmActivityDetails" property="irdrgPymntProcess" />
			</td>
		</tr>
		 <tr>
			<td class="formLabel">Outlier Payment's Threshold<br>Limit(Gap) </td>
		 	<td class="textLabel">
			          <html:text name="frmActivityDetails" property="outlierPmntThresholdLmt"    styleClass="textBox textBoxMedium disabledBox"      readonly="true" />
			          <html:text name="frmActivityDetails" property="aed" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" value="OMR"/>
			</td>
			<td class="formLabel">Margin: </td>
		 	<td class="textLabel">
			          <html:text name="frmActivityDetails" property="margin"	  styleClass="textBox textBoxMedium disabledBox"      readonly="true" />
			          <html:text name="frmActivityDetails" property="defaultPercentage" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" value="%"/>
			</td>
		</tr>
		
		<tr>
			<logic:equal value="N" property="irdrgInlierFlagYN" name="frmActivityDetails">
				<td class="formLabel"> Cost (Sum of the 'Average Cost' of all the Activities): 
					
					
				</td>
			 	<td class="textLabel">
				          <html:text name="frmActivityDetails" property="avgActivityCost"    styleClass="textBox textBoxMedium disabledBox"      readonly="true" />
				          <html:text name="frmActivityDetails" property="aed" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" value="OMR"/>
				</td>
				<td></td>
			 	<td></td>
			 </logic:equal>	
		</tr>
		 
		</logic:equal>
		
		<logic:notEqual name="frmActivityDetails" property="activityTypeId" value="9">
						<logic:equal value="N" property="irdrgInlierFlagYN" name="frmActivityDetails">
							<td class="formLabel"> Average Activity Cost:<br> (Used only in the IRDRG 'Outlier Payment' Process): 
						</td>
			 		<td class="textLabel">
				          <html:text name="frmActivityDetails" property="avgActivityCost"    styleClass="textBox textBoxMedium disabledBox"      readonly="true" />
				          <html:text name="frmActivityDetails" property="aed" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" value="OMR"/>
					</td>
					<td></td>
			 		<td></td>
				 </logic:equal>	
		</logic:notEqual>
		
	</logic:equal>
		  	
		  
		  
		  
		  
		  <tr>
		        <td class="formLabel">Unit Price:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  
                        <% boolean editStatus=true; %>
					  <logic:equal name="frmActivityDetails" property="editStatus" value="Y">			
					  	 <% editStatus=false; %>
			          </logic:equal>
					   
					   <logic:equal name="frmActivityDetails" property="overrideYN" value="Y">
					
					 		<logic:equal value="CTM" name="frmClaimGeneral" property="claimType">
					   		 <% editStatus=false; %>
					   		</logic:equal>
					   </logic:equal>
					   
					    <logic:equal name="frmActivityDetails" property="overrideYN" value="Y">
					
					 		<logic:equal value="CTN" name="frmClaimGeneral" property="claimType">
					   		 <% editStatus=true; %>
					   		</logic:equal>
					   </logic:equal>
					
						 <% String irdrgFormulaw=""; %>
					<logic:equal name="frmActivityDetails" property="irdrgProcessYN" value="Y">
					   		<logic:equal value="N" property="irdrgInlierFlagYN" name="frmActivityDetails">
					   			<% irdrgFormulaw="(IRDRG Relative Weight * Base Rate * Negotiation Factor) + Outlier Payment"; %>
					   		</logic:equal>
					   		<logic:equal value="Y" property="irdrgInlierFlagYN" name="frmActivityDetails">
					   			<% irdrgFormulaw="IRDRG Relative Weight * Base Rate * Negotiation Factor"; %>
					  		 </logic:equal>
					</logic:equal>
					
					
					
					  <logic:equal value="DBL" name="frmClaimGeneral" property="processType">
					   <logic:notEmpty name="frmActivityDetails" property="unitPrice">
					    <html:text name="frmActivityDetails" property="unitPrice" styleId="unitPrice"   styleClass="textBox textBoxMedium"  onkeyup="calculateNetAmount(this)" readonly="<%=editStatus%>" title="<%=irdrgFormulaw%>"/>
					    <input  id="requestedAmountcurrencyType"  class="textBox textBoxTooTiny textBoxDisabled" readonly="readonly" value="<bean:write name="frmClaimGeneral" property="requestedAmountcurrencyType"/>">
					   </logic:notEmpty>
					 </logic:equal>
					  <logic:equal value="DBL" name="frmClaimGeneral" property="processType">
					 <logic:empty name="frmActivityDetails" property="unitPrice">
					    <html:text name="frmActivityDetails" property="unitPrice" styleId="unitPrice" value="0.00"   styleClass="textBox textBoxMedium"  onkeyup="calculateNetAmount(this)" title="<%=irdrgFormulaw%>" />
					   <input  id="requestedAmountcurrencyType"  class="textBox textBoxTooTiny textBoxDisabled" readonly="readonly" value="<bean:write name="frmClaimGeneral" property="requestedAmountcurrencyType"/>">
					   </logic:empty>
					   </logic:equal>
					 
					
					  <logic:equal value="GBL" name="frmClaimGeneral" property="processType">
					   <logic:notEmpty name="frmActivityDetails" property="unitPrice">
					    <html:text name="frmActivityDetails" property="unitPrice" styleId="unitPrice"   styleClass="textBox textBoxMedium"  onkeyup="calculateNetAmount(this)" readonly="<%=editStatus%>" title="<%=irdrgFormulaw%>"/>
					    <input  id="requestedAmountcurrencyType"  class="textBox textBoxTooTiny textBoxDisabled" readonly="readonly" value="<bean:write name="frmClaimGeneral" property="requestedAmountcurrencyType"/>">
					   </logic:notEmpty>
					 </logic:equal>
					  <logic:equal value="GBL" name="frmClaimGeneral" property="processType">
					 <logic:empty name="frmActivityDetails" property="unitPrice">
					    <html:text name="frmActivityDetails" property="unitPrice" styleId="unitPrice" value="0.00"   styleClass="textBox textBoxMedium"  onkeyup="calculateNetAmount(this)" title="<%=irdrgFormulaw%>"/>
					   <input  id="requestedAmountcurrencyType"  class="textBox textBoxTooTiny textBoxDisabled" readonly="readonly" value="<bean:write name="frmClaimGeneral" property="requestedAmountcurrencyType"/>">
					   </logic:empty>
					   </logic:equal>
					 
					   </td> 
					 	 
					
					  <td class="formLabel">Unit Discount:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  <logic:notEqual value="Y" name="frmActivityDetails" property="overrideYN">
					  <html:text name="frmActivityDetails" property="discountPerUnit"  styleId="discountPerUnit"  styleClass="textBox textBoxMedium disabledBox" readonly="true"/>
					  </logic:notEqual>
					  <logic:equal value="Y" name="frmActivityDetails" property="overrideYN">
					  <html:text name="frmActivityDetails" property="discountPerUnit"  styleId="discountPerUnit"  styleClass="textBox textBoxMedium" onkeyup="isNumeric(this);"/>
					  </logic:equal>		  
					  </td>
		 </tr>
		 <tr>
		 <td class="formLabel">Converted Unit Price: </td>
		 <td class="textLabel">
			          <html:text name="frmActivityDetails" property="convertedUnitPrice"   styleClass="textBox textBoxMedium disabledBox" readonly="true" />
			          <input  id="convertedUnitPricecurrencyType"  class="textBox textBoxTooTiny textBoxDisabled" readonly="readonly" value="<bean:write name="frmClaimGeneral" property="currencyType"/>">
			          </td>
			          <td> </td>
			          <td> </td>
			          </tr>

	   </table>	
			</fieldset>
	<fieldset>
		  <legend>Provider Billed Amounts</legend>
		   <logic:notEqual value="Y" name="frmActivityDetails" property="overrideYN">
		  <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		       <tr>
		        <td class="formLabel">Gross Amount:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="grossAmount"  styleClass="textBox textBoxMedium disabledBox"  onkeyup="calculateNetAmount(this)" readonly="true"/>
					  </td>
					   <td class="formLabel">Discount:</td>
					  <td class="textLabel">
					  
				
					 <logic:equal name="frmActivityDetails" property="haad_YN" value="Y">
					  		 <html:text name="frmActivityDetails" property="discount" styleId="discount"  styleClass="textBox textBoxMedium"  onblur="calculateNetAmount(this)" readonly="true"/>
					</logic:equal>
					<logic:notEqual name="frmActivityDetails" property="haad_YN" value="Y">
					<html:text name="frmActivityDetails" property="discount" styleId="discount"  styleClass="textBox textBoxMedium"  onblur="calculateNetAmount(this)"/>
					</logic:notEqual>
					
					
					<%-- 
					 <logic:equal name="frmActivityDetails" property="haad_YN" value="N">
					  		 <html:text name="frmActivityDetails" property="discount" styleId="discount"  styleClass="textBox textBoxMedium"  onblur="calculateNetAmount(this)"/>
					</logic:equal> --%>
					
					<%--   <html:text name="frmActivityDetails" property="discount" styleId="discount"  styleClass="textBox textBoxMedium"  onblur="calculateNetAmount(this)" readonly="true"/> --%>
					 
					  </td>
					  </tr>
					  <tr>
					  <td class="formLabel">Discounted Gross:<span class="mandatorySymbol">*</span> </td>
					   <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="discountedGross" styleClass="textBox textBoxMedium disabledBox"  readonly="true" />
					   </td>
					  <td class="formLabel">Co-Pay: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="copay"  onkeyup="calculateNetAmount(this)" styleClass="textBox textBoxMedium"  />
					  </td>
				   </tr>
				    <tr>				  
					 <%--  <td class="formLabel">Co-Insurance: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="coinsurance"  styleClass="textBox textBoxMedium"  onkeyup="calculateNetAmount(this)" />
					  </td> --%>
					  <td class="formLabel">Deductible: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="deductible"  styleClass="textBox textBoxMedium"  onkeyup="calculateNetAmount(this)"/>
					  </td>
					   <td class="formLabel">Patient Share: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="patientShare"  styleClass="textBox textBoxMedium disabledBox"  readonly="true"/>
					  </td>
					  
				   </tr>
				  <%--  <tr>
				   <td class="formLabel">Out Of Pocket: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="outOfPocket"  styleClass="textBox textBoxMedium"  onkeyup="calculateNetAmount(this)"/>
					  </td>
					  <td class="formLabel">Patient Share: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="patientShare"  styleClass="textBox textBoxMedium disabledBox"  readonly="true"/>
					  </td>
				   </tr> --%>
				   <tr>
				    <td class="formLabel">Net Amount:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="netAmount"  styleClass="textBox textBoxMedium disabledBox"  readonly="true" />
					  </td>
	
					<logic:equal value="ECL" name="frmClaimGeneral" property="modeOfClaim" scope="session">
						 <logic:notEmpty name="frmActivityDetails" property="providerRequestedAmt"  >
                             <td class="formLabel">Provider Requested Amount:<span class="mandatorySymbol">*</span></td>
					  <td class="textLabel">
                                  <html:text name="frmActivityDetails" property="providerRequestedAmt" styleClass="textBox textBoxMedium disabledBox" onkeyup="isNumeric(this);conProReqAmt();" readonly="true" />
                             </td>
                         </logic:notEmpty>	  
                          <logic:empty name="frmActivityDetails" property="providerRequestedAmt">
                             <td class="formLabel">Provider Requested Amount:<span class="mandatorySymbol">*</span></td>
                              <td class="textLabel">
                                  <html:text name="frmActivityDetails" property="providerRequestedAmt" value="0.00" styleClass="textBox textBoxMedium disabledBox" onkeyup="isNumeric(this);conProReqAmt();" readonly="true" />
                             </td>
                         </logic:empty>	   
                      </logic:equal>
                      
                     <%--   <logic:notEqual value="ECL" name="frmClaimGeneral" property="modeOfClaim" scope="session">
                             <td class="formLabel">Provider Requested Amount:<span class="mandatorySymbol">*</span></td>
                              <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="providerRequestedAmt"  styleClass="textBox textBoxMedium" onkeyup="isNumeric(this);conProReqAmt();" />
					  </td>
                      </logic:notEqual> --%>
                      
                       <logic:notEqual value="ECL" name="frmClaimGeneral" property="modeOfClaim" scope="session">
                       <bean:define id="processTypeTemp" property="processType" name="frmClaimGeneral"/>
					    <bean:define id="claimTypeTemp" property="claimType" name="frmClaimGeneral"/>	
                        <bean:define id="networkProviderTypeTemp" property="networkProviderType" name="frmClaimGeneral"/>
                       <%if("CTM".equals(claimTypeTemp)&&"DBL".equals(processTypeTemp)){ %>	
                       
                         <td class="formLabel">Provider Requested Amount(VAT Added):<span class="mandatorySymbol">*</span></td>
                              <td class="textLabel">
					  	<html:text  name="frmActivityDetails" property="providerRequestedAmt"  styleClass="textBox textBoxMedium" styleId="providerRequestedAmt" onkeyup="isNumeric(this);conProReqAmt();" />
					  </td>
                       
                          <%}
			else {%>	
			
			<%if("GBL".equals(processTypeTemp) &&"CTM".equals(claimTypeTemp) && "N".equals(networkProviderTypeTemp)){ %>	
			 <logic:equal value="123" property="providerCountry" name="frmClaimGeneral">
			  <td class="formLabel">Provider Requested Amount:<span class="mandatorySymbol">*</span></td>
                              <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="providerRequestedAmt"  styleClass="textBox textBoxMedium" onkeyup="isNumeric(this);conProReqAmt();" />
					  </td> 
			 </logic:equal>
			  <logic:notEqual value="123" property="providerCountry" name="frmClaimGeneral">
			    <td class="formLabel">Provider Requested Amount(VAT Added):<span class="mandatorySymbol">*</span></td>
                              <td class="textLabel">
					  	<html:text  name="frmActivityDetails" property="providerRequestedAmt"  styleClass="textBox textBoxMedium" styleId="providerRequestedAmt" onkeyup="isNumeric(this);conProReqAmt();" />
					  </td>
			  </logic:notEqual>
			
			 <%}
			else {%>	
			 <td class="formLabel">Provider Requested Amount:<span class="mandatorySymbol">*</span></td>
                              <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="providerRequestedAmt"  styleClass="textBox textBoxMedium" onkeyup="isNumeric(this);conProReqAmt();" />
					  </td> 
			
			<%}%>
			<%}%>
                      
                </logic:notEqual>       
				
					  
				   </tr>
				   <tr>
				   			<td></td>
				   			<td></td>
				   			<td></td>
							<td class="textLabel">
								<html:text property="convertedProviderReqAmt" styleId="convertedProviderReqAmt"  styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" />&nbsp;
								<html:text property="currencyType" styleId="totalAmountCurrency" value="OMR"  styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />
							</td>	
				   </tr>
				   
				   
				   <tr>
				    <td class="formLabel"></td>
					  <td class="textLabel">					  	
					  </td>
					  <td class="formLabel">Allowed:  </td>
					  <td>
					  	<html:checkbox name="frmActivityDetails" property="amountAllowed"   value="Y" />
					  </td>
				   </tr>	
				   
				   
				    <logic:equal value="ECL" name="frmClaimGeneral" property="modeOfClaim" scope="session"> 
					<tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatPercent" readonly="true" />
					</td>
					<td class="formLabel">Activity VAT[OMR]::</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAed" readonly="true" />
					</td>
					</tr>
					<tr>
					<td class="formLabel">Provider Requested Amount[VAT Added]:</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />[OMR]
					</td>
					<td></td><td></td>
					</tr>
					 </logic:equal>  
				    <logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral">
					 <logic:equal value="GBL" property="processType" name="frmClaimGeneral"> 
					  <logic:equal value="CNH" property="claimType" name="frmClaimGeneral">
					 <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					<tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium" styleId="vatPercent" onkeyup="isPositiveNumber(this,'Claim VAT[%]');caluculateActivityVatAED();"/>
					</td>
					<td class="formLabel">Activity VAT[OMR]::</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium" styleId="vatAed" onkeyup="isPositiveNumber(this,'Claim VAT[AED]');caluculateActivityVatPER();" />
					</td>
					</tr>
					</logic:equal>
					 <logic:notEqual value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					 <tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" styleId="vatPercent" disabled="true" />
					</td>
					<td class="formLabel">Activity VAT[OMR]:</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAed" readonly="true" />
					</td>
					</tr>
					 </logic:notEqual>
					 
					<tr>
					<td class="formLabel">Provider Requested Amount[VAT Added]:</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />[OMR]
					</td>
					<td></td><td></td>
					</tr>
					</logic:equal>
					 <logic:equal value="CTM" property="claimType" name="frmClaimGeneral">
					 <logic:equal value="Y" name="frmClaimGeneral" property="networkProviderType">
					 <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					<tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium" styleId="vatPercent" onkeyup="isPositiveNumber(this,'Claim VAT[%]');caluculateActivityVatAED();"/>
					</td>
					<td class="formLabel">Activity VAT[OMR]::</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium" styleId="vatAed" onkeyup="isPositiveNumber(this,'Claim VAT[AED]');caluculateActivityVatPER();" />
					</td>
					</tr>
					</logic:equal>
					 <logic:notEqual value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					 <tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" styleId="vatPercent" disabled="true" />
					</td>
					<td class="formLabel">Activity VAT[OMR]:</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAed" readonly="true" />
					</td>
					</tr>
					 </logic:notEqual>
					 
					<tr>
					<td class="formLabel">Provider Requested Amount[VAT Added]:</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />[OMR]
					</td>
					<td></td><td></td>
					</tr>
					</logic:equal>
					 <logic:equal value="N" name="frmClaimGeneral" property="networkProviderType">
					  <logic:equal value="123" property="providerCountry" name="frmClaimGeneral">
					 <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					<tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium" styleId="vatPercent" onkeyup="isPositiveNumber(this,'Claim VAT[%]');caluculateActivityVatAED();"/>
					</td>
					<td class="formLabel">Activity VAT[OMR]::</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium" styleId="vatAed" onkeyup="isPositiveNumber(this,'Claim VAT[AED]');caluculateActivityVatPER();" />
					</td>
					</tr>
					</logic:equal>
					 <logic:notEqual value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					 <tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" styleId="vatPercent" disabled="true" />
					</td>
					<td class="formLabel">Activity VAT[OMR]:</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAed" readonly="true" />
					</td>
					</tr>
					 </logic:notEqual>
					 
					<tr>
					<td class="formLabel">Provider Requested Amount[VAT Added]:</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />[OMR]
					</td>
					<td></td><td></td>
					</tr>
					</logic:equal>
					</logic:equal>
					</logic:equal>
					</logic:equal>
					 <logic:equal value="DBL" property="processType" name="frmClaimGeneral"> 
					 <logic:equal value="CNH" property="claimType" name="frmClaimGeneral"> 
					 <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					<tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium" styleId="vatPercent" onkeyup="isPositiveNumber(this,'Claim VAT[%]');caluculateActivityVatAED();"/>
					</td>
					<td class="formLabel">Activity VAT[OMR]::</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium" styleId="vatAed" onkeyup="isPositiveNumber(this,'Claim VAT[AED]');caluculateActivityVatPER();" />
					</td>
					</tr>
					</logic:equal>
					 <logic:notEqual value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					 <tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" styleId="vatPercent" disabled="true" />
					</td>
					<td class="formLabel">Activity VAT[OMR]:</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAed" readonly="true" />
					</td>
					</tr>
					 </logic:notEqual>
					 
					<tr>
					<td class="formLabel">Provider Requested Amount[VAT Added]:</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />[OMR]
					</td>
					<td></td><td></td>
					</tr>
					</logic:equal>
					</logic:equal>
					</logic:notEqual>
				   
				   			  
				    </table>
				    </logic:notEqual>
	   <logic:equal value="Y" name="frmActivityDetails" property="overrideYN">
		  <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		       <tr>
		        <td class="formLabel">Gross Amount:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="grossAmount"  styleClass="textBox textBoxMedium disabledBox"  onkeyup="calculateNetAmount(this)" readonly="true"/>
					  </td>
					   <td class="formLabel">Discount:</td>
					  <td class="textLabel">
					  
					  <html:text name="frmActivityDetails" property="discount" styleId="discount"  styleClass="textBox textBoxMedium"  onblur="calculateNetAmount(this)"/>
					 
					  </td>
					  </tr>
					  <tr>
					  <td class="formLabel">Discounted Gross:<span class="mandatorySymbol">*</span> </td>
					   <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="discountedGross" styleClass="textBox textBoxMedium disabledBox"  readonly="true" />
					   </td>
					  <td class="formLabel">Co-Pay: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="copay"  onkeyup="calculateNetAmount(this)" styleClass="textBox textBoxMedium"  />
					  </td>
				   </tr>
				    <tr>				  
					 <%--  <td class="formLabel">Co-Insurance: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="coinsurance"  styleClass="textBox textBoxMedium"  onkeyup="calculateNetAmount(this)" />
					  </td> --%>
					  <td class="formLabel">Deductible: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="deductible"  styleClass="textBox textBoxMedium"  onkeyup="calculateNetAmount(this)"/>
					  </td>
					   <td class="formLabel">Patient Share: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="patientShare"  styleClass="textBox textBoxMedium disabledBox"  readonly="true"/>
					  </td>
				   </tr>
				  <%--  <tr>
				   <td class="formLabel">Out Of Pocket: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="outOfPocket"  styleClass="textBox textBoxMedium"  onkeyup="calculateNetAmount(this)"/>
					  </td>
					  <td class="formLabel">Patient Share: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="patientShare"  styleClass="textBox textBoxMedium disabledBox"  readonly="true"/>
					  </td>
				   </tr> --%>
				   <tr>
				    <td class="formLabel">Net Amount:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="netAmount"  styleClass="textBox textBoxMedium disabledBox"  readonly="true" />
					  </td>
					  
					 <logic:equal value="ECL" name="frmClaimGeneral" property="modeOfClaim" scope="session">
					 	 <logic:notEmpty name="frmActivityDetails" property="providerRequestedAmt">
                             <td class="formLabel">Provider Requested Amount:<span class="mandatorySymbol">*</span></td>
                              <td class="textLabel">
                                  <html:text name="frmActivityDetails" property="providerRequestedAmt" styleClass="textBox textBoxMedium disabledBox" onkeyup="isNumeric(this);conProReqAmt();" readonly="true" />
                             </td>
                         </logic:notEmpty>	
                         
                          <logic:empty name="frmActivityDetails" property="providerRequestedAmt">
                             <td class="formLabel">Provider Requested Amount:<span class="mandatorySymbol">*</span></td>
                              <td class="textLabel">
                                  <html:text name="frmActivityDetails" property="providerRequestedAmt"  value="0.00" styleClass="textBox textBoxMedium disabledBox" onkeyup="isNumeric(this);conProReqAmt();" readonly="true" />
                             </td>
                         </logic:empty>	    
                       
                      </logic:equal>
                      
                     <%--   <logic:notEqual value="ECL" name="frmClaimGeneral" property="modeOfClaim" scope="session">
                             <td class="formLabel">Provider Requested Amount:<span class="mandatorySymbol">*</span></td>
                              <td class="textLabel">
                                  <html:text name="frmActivityDetails" property="providerRequestedAmt" styleClass="textBox textBoxMedium" onkeyup="isNumeric(this);conProReqAmt();" />
                             </td>
                      </logic:notEqual>  --%>
                      
                        <logic:notEqual value="ECL" name="frmClaimGeneral" property="modeOfClaim" scope="session">
                       <bean:define id="processTypeTemp" property="processType" name="frmClaimGeneral"/>
					    <bean:define id="claimTypeTemp" property="claimType" name="frmClaimGeneral"/>	
                        <bean:define id="networkProviderTypeTemp" property="networkProviderType" name="frmClaimGeneral"/>
                       <%if("CTM".equals(claimTypeTemp)&&"DBL".equals(processTypeTemp)){ %>	
                       
                         <td class="formLabel">Provider Requested Amount(VAT Added):<span class="mandatorySymbol">*</span></td>
                              <td class="textLabel">
					  	<html:text  name="frmActivityDetails" property="vatAddedReqAmnt"  styleClass="textBox textBoxMedium" styleId="vatAddedReqAmnt" onkeyup="isNumeric(this);conProReqAmt();" />
					  </td>
                       
                          <%}
			else {%>	
			
			
			<%if("GBL".equals(processTypeTemp) &&"CTM".equals(claimTypeTemp) && "N".equals(networkProviderTypeTemp)){ %>	
			 <logic:equal value="123" property="providerCountry" name="frmClaimGeneral">
			  <td class="formLabel">Provider Requested Amount:<span class="mandatorySymbol">*</span></td>
                              <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="providerRequestedAmt"  styleClass="textBox textBoxMedium" onkeyup="isNumeric(this);conProReqAmt();" />
					  </td> 
			 </logic:equal>
			  <logic:notEqual value="123" property="providerCountry" name="frmClaimGeneral">
			    <td class="formLabel">Provider Requested Amount(VAT Added):<span class="mandatorySymbol">*</span></td>
                              <td class="textLabel">
					  	<html:text  name="frmActivityDetails" property="providerRequestedAmt"  styleClass="textBox textBoxMedium" styleId="providerRequestedAmt" onkeyup="isNumeric(this);conProReqAmt();" />
					  </td>
			  </logic:notEqual>
			
			 <%}
			else {%>	
			 <td class="formLabel">Provider Requested Amount:<span class="mandatorySymbol">*</span></td>
                              <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="providerRequestedAmt"  styleClass="textBox textBoxMedium" onkeyup="isNumeric(this);conProReqAmt();" />
					  </td> 
			
			<%}%>
			
			
			<%}%>
                      
                </logic:notEqual>       
                      
                      
                      
				   </tr>
				   
				     <tr>
				   			<td></td>
				   			<td></td>
				   			<td></td>
							<td class="textLabel">
								<html:text property="convertedProviderReqAmt" styleId="convertedProviderReqAmt"  styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" />&nbsp;
								<html:text property="currencyType" styleId="totalAmountCurrency" value="OMR"  styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />
							</td>	
				   </tr>
				   
				   <tr>
				    <td class="formLabel"></td>
					  <td class="textLabel">					  	
					  </td>
					  <td class="formLabel">Allowed:  </td>
					  <td>
					  	<html:checkbox name="frmActivityDetails" property="amountAllowed"   value="Y" />
					  </td>
				   </tr>	
				   
				   
				   
				     <logic:equal value="ECL" name="frmClaimGeneral" property="modeOfClaim" scope="session"> 
					<tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatPercent" readonly="true" />
					</td>
					<td class="formLabel">Activity VAT[OMR]::</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAed" readonly="true" />
					</td>
					</tr>
					<tr>
					<td class="formLabel">Provider Requested Amount[VAT Added]:</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />[OMR]
					</td>
					<td></td><td></td>
					</tr>
					 </logic:equal>  
				   <logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral">
					 <logic:equal value="GBL" property="processType" name="frmClaimGeneral"> 
					  <logic:equal value="CNH" property="claimType" name="frmClaimGeneral">
					 <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					<tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium" styleId="vatPercent" onkeyup="isPositiveNumber(this,'Claim VAT[%]');caluculateActivityVatAED();"/>
					</td>
					<td class="formLabel">Activity VAT[OMR]::</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium" styleId="vatAed" onkeyup="isPositiveNumber(this,'Claim VAT[AED]');caluculateActivityVatPER();" />
					</td>
					</tr>
					</logic:equal>
					 <logic:notEqual value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					 <tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" styleId="vatPercent" disabled="true" />
					</td>
					<td class="formLabel">Activity VAT[OMR]:</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAed" readonly="true" />
					</td>
					</tr>
					 </logic:notEqual>
					 
					<tr>
					<td class="formLabel">Provider Requested Amount[VAT Added]:</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />[OMR]
					</td>
					<td></td><td></td>
					</tr>
					</logic:equal>
					 <logic:equal value="CTM" property="claimType" name="frmClaimGeneral">
					 <logic:equal value="Y" name="frmClaimGeneral" property="networkProviderType">
					 <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					<tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium" styleId="vatPercent" onkeyup="isPositiveNumber(this,'Claim VAT[%]');caluculateActivityVatAED();"/>
					</td>
					<td class="formLabel">Activity VAT[OMR]::</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium" styleId="vatAed" onkeyup="isPositiveNumber(this,'Claim VAT[AED]');caluculateActivityVatPER();" />
					</td>
					</tr>
					</logic:equal>
					 <logic:notEqual value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					 <tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" styleId="vatPercent" disabled="true" />
					</td>
					<td class="formLabel">Activity VAT[OMR]:</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAed" readonly="true" />
					</td>
					</tr>
					 </logic:notEqual>
					 
					<tr>
					<td class="formLabel">Provider Requested Amount[VAT Added]:</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />[OMR]
					</td>
					<td></td><td></td>
					</tr>
					</logic:equal>
					 <logic:equal value="N" name="frmClaimGeneral" property="networkProviderType">
					  <logic:equal value="123" property="providerCountry" name="frmClaimGeneral">
					 <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					<tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium" styleId="vatPercent" onkeyup="isPositiveNumber(this,'Claim VAT[%]');caluculateActivityVatAED();"/>
					</td>
					<td class="formLabel">Activity VAT[OMR]::</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium" styleId="vatAed" onkeyup="isPositiveNumber(this,'Claim VAT[AED]');caluculateActivityVatPER();" />
					</td>
					</tr>
					</logic:equal>
					 <logic:notEqual value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					 <tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" styleId="vatPercent" disabled="true" />
					</td>
					<td class="formLabel">Activity VAT[OMR]:</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAed" readonly="true" />
					</td>
					</tr>
					 </logic:notEqual>
					 
					<tr>
					<td class="formLabel">Provider Requested Amount[VAT Added]:</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />[OMR]
					</td>
					<td></td><td></td>
					</tr>
					</logic:equal>
					</logic:equal>
					</logic:equal>
					</logic:equal>
					 <logic:equal value="DBL" property="processType" name="frmClaimGeneral"> 
					 <logic:equal value="CNH" property="claimType" name="frmClaimGeneral"> 
					 <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					<tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium" styleId="vatPercent" onkeyup="isPositiveNumber(this,'Claim VAT[%]');caluculateActivityVatAED();"/>
					</td>
					<td class="formLabel">Activity VAT[OMR]::</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium" styleId="vatAed" onkeyup="isPositiveNumber(this,'Claim VAT[AED]');caluculateActivityVatPER();" />
					</td>
					</tr>
					</logic:equal>
					 <logic:notEqual value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					 <tr>
					<td class="formLabel">Activity VAT[%]:</td>
					<td><html:text property="vatPercent" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" styleId="vatPercent" disabled="true" />
					</td>
					<td class="formLabel">Activity VAT[OMR]:</td>
					<td><html:text property="vatAed" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAed" readonly="true" />
					</td>
					</tr>
					 </logic:notEqual>
					 
					<tr>
					<td class="formLabel">Provider Requested Amount[VAT Added]:</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />[OMR]
					</td>
					<td></td><td></td>
					</tr>
					</logic:equal>
					</logic:equal>
					</logic:notEqual>
				   						  
				    </table>
				    </logic:equal> 
			</fieldset>
		<fieldset>
		  <legend>Rule Amounts</legend>
		  <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		      	  <tr>
					  <td class="formLabel">Co-Pay: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="rcopay" styleClass="textBox textBoxMedium disabledBox" readonly="true"  />
					  </td>
					   <logic:equal value="CTM" name="frmClaimGeneral" property="claimType" scope="session">
					  <td class="formLabel">Non Network Co-Pay: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="nonNetworkCopay" onkeyup="isNumeric(this);" styleClass="textBox textBoxMedium"/>(%)
					  </td>
					  </logic:equal>
					  
				   </tr>
				    <tr>				  
					  <td class="formLabel">Deductible: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="rdeductible" styleClass="textBox textBoxMedium disabledBox" readonly="true"/>
					  </td>
					   <td class="formLabel">Co-Insurance: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="rcoinsurance" styleClass="textBox textBoxMedium disabledBox" readonly="true"/>
					  </td>
					 
				   </tr>
				   <tr>
				   <td class="formLabel">Applied Copay:  </td> 
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="appliedCopay"  styleClass="textBox textBoxMedium disabledBox" readonly="true" /> 
					  </td>
					  <td></td><td></td>
				   </tr>
				   <tr>
				    <td class="formLabel">Allowed Amount:  </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="allowedAmount"  styleClass="textBox textBoxMedium disabledBox" readonly="true"  />
					  </td>
					  
					   
					  <td class="formLabel">Out Of Pocket: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="routOfPocket" styleClass="textBox textBoxMedium disabledBox"  readonly="true"/>
					  </td>
					  
				   </tr>
				   <tr>
				    <td class="formLabel">Approved Amount:  </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="approvedAmount"  styleClass="textBox textBoxMedium disabledBox" readonly="true" />
					  </td>	
					  <td class="formLabel">Dis Allowed Amount:  </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="rdisAllowedAmount"  styleClass="textBox textBoxMedium disabledBox" readonly="true"/>
					  </td>
					  </tr>
					 <logic:equal value="CTM" name="frmClaimGeneral" property="claimType" scope="session">
					   
 			   
 			  <logic:equal value="Y" name="frmClaimGeneral" property="enablericopar" scope="session">
					   <tr>
				    <td class="formLabel">Ri copar:  </td> 
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="ricopar"  styleClass="textBox textBoxMedium disabledBox" readonly="true" />(%) 
					  </td>
					   
					   <td></td><td></td>
					   </tr>
			 </logic:equal>
		
					   
					    <logic:equal value="Y" name="frmClaimGeneral" property="enableucr" scope="session">
					   
					  <tr>	
				    	<td class="formLabel">UCR:  </td> 
					  	<td class="textLabel">  
					  		<html:text name="frmActivityDetails" property="ucr" onkeyup="isNumeric(this);"  styleClass="textBox textBoxMedium" />
					 	</td>
					  </tr>
				   </logic:equal>
				
				</logic:equal>
				
				<tr>
					
					<td class="formLabel">Additional Disallowances: </td>
					<td>
					  	<html:text name="frmActivityDetails" property="additionalDisallowances"  styleClass="textBox textBoxMedium" onkeyup="calculateNetAmount(this)" />
					</td>	
					<td class="formLabel">Provider Area Of Coverage Copay: </td>
					
					<td class="textLabel">
					<html:text name="frmActivityDetails" property="providerAreCopay" readonly="true"  styleClass="textBox textBoxMedium disabledBox"/>
					</td>
				</tr>
				
				<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
				 	   <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					<tr>
					<td class="formLabel">VAT to be applied on</td>
					<td><html:text property="appliedVatPercent" styleClass="textBox textBoxMedium" styleId="appliedVatPercent"   onkeyup="isPositiveNumber(this,'VAT to be applied on')"/>
					<html:select property="appliedVatAEDPER" styleId="appliedVatAEDPER"  styleClass="selectBox selectBoxSmall"  >
      		        <html:option value="PER">%</html:option>
      		       
      		        </html:select>&nbsp;
					</td>
					<td class="formLabel">Approved Amount[VAT Added]:</td>
					<td><html:text property="vatAddedApprAmnt" styleClass="textBox textBoxMedium" styleId="vatAddedApprAmnt" readonly="true" disabled="true"/>[OMR]
					</td>
					</tr>
					<tr>
					<td class="formLabel">The Approved Amount :</td>
					<td><html:text property="appliedVatAed" styleClass="textBox textBoxMedium" styleId="appliedVatAed" readonly="true" disabled="true" />[OMR]
					</td>
					
					<td></td><td></td>
					</tr>
					 </logic:equal>  
				 <logic:notEqual value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					<tr>
					<td class="formLabel">VAT to be applied on:</td>
					<td><html:text property="appliedVatPercent" styleClass="textBox textBoxMedium" styleId="appliedVatPercent" readonly="true" disabled="true"/>
					<html:select property="appliedVatAEDPER" styleId="appliedVatAEDPER"  styleClass="selectBox selectBoxSmall"  readonly="true" disabled="true" >
      		 <html:option value=""></html:option>
      		 <html:option value="PER">%</html:option>
      		
      		 </html:select>&nbsp;
					
					</td>
					<td class="formLabel">Approved Amount [VAT Added]:</td>
					<td><html:text property="vatAddedApprAmnt" styleClass="textBox textBoxMedium" styleId="vatAddedApprAmnt" readonly="true" disabled="true" />[OMR]
					</td>
					</tr>
					<tr>
					<td class="formLabel">The Approved Amount :</td>
					<td><html:text property="appliedVatAed" styleClass="textBox textBoxMedium" styleId="appliedVatAed" readonly="true" disabled="true"/>[OMR]
					</td>
					<td></td><td></td>
					</tr>
					 </logic:notEqual>  
				
				</logic:equal>
				
				<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral">
				<logic:equal value="GBL" property="processType" name="frmClaimGeneral"> 
				 <logic:equal value="CNH" property="claimType" name="frmClaimGeneral">
				  <logic:notEqual value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					<tr>
					<td class="formLabel">Applied VAT [%]:</td>
					<td><html:text property="appliedVatPercent" styleClass="textBox textBoxMedium" styleId="vatPercent" readonly="true" disabled="true"/>
					<html:select property="appliedVatAEDPER" styleId="appliedVatAEDPER"  styleClass="selectBox selectBoxSmall"  readonly="true" disabled="true" >
      				 <html:option value=""></html:option>
      				 <html:option value="PER">%</html:option>
      				
      				 </html:select>&nbsp;
					
					</td>
					<td class="formLabel">Applied VAT [OMR]:</td>
					<td><html:text property="appliedVatAed" styleClass="textBox textBoxMedium" styleId="vatAed" readonly="true" disabled="true"/>
					</td>
					</tr>
					<tr>
					<td class="formLabel">Approved Amount[VAT Added]:</td>
					<td><html:text property="vatAddedApprAmnt" styleClass="textBox textBoxMedium" styleId="vatAddedApprAmnt" readonly="true" disabled="true"/>[OMR]
					</td>
					<td></td><td></td>
					</tr>
					 </logic:notEqual> 
				  <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
				  <tr>
					<td class="formLabel">Applied VAT [%]:</td>
					<td><html:text property="appliedVatPercent" styleClass="textBox textBoxMedium" styleId="appliedVatPercent"  onkeyup="isPositiveNumber(this,'Applied VAT[%]')"/>
					<html:select property="appliedVatAEDPER" styleId="appliedVatAEDPER"  styleClass="selectBox selectBoxSmall"  >
      		        <html:option value="PER">%</html:option>
      		       
      		        </html:select>&nbsp;
					
					</td>
					<td class="formLabel">Applied VAT [OMR]:</td>
					<td><html:text property="appliedVatAed" styleClass="textBox textBoxMedium" styleId="appliedVatAed" readonly="true" disabled="true" onkeyup="isPositiveNumber(this,'Applied VAT[AED]')" />
					</td>
					</tr>
					<tr>
					<td class="formLabel">Approved Amount[VAT Added]:</td>
					<td><html:text property="vatAddedApprAmnt" styleClass="textBox textBoxMedium" styleId="vatAddedApprAmnt" readonly="true" disabled="true" />[OMR]
					</td>
					<td></td><td></td>
					</tr>
				  </logic:equal>
				  </logic:equal>
				   <logic:equal value="CTM" property="claimType" name="frmClaimGeneral">
					 <logic:equal value="Y" name="frmClaimGeneral" property="networkProviderType">
				   <logic:notEqual value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					<tr>
					<td class="formLabel">Applied VAT [%]:</td>
					<td><html:text property="appliedVatPercent" styleClass="textBox textBoxMedium" styleId="vatPercent" readonly="true" disabled="true"/>
					<html:select property="appliedVatAEDPER" styleId="appliedVatAEDPER"  styleClass="selectBox selectBoxSmall"  readonly="true" disabled="true" >
      				 <html:option value=""></html:option>
      				 <html:option value="PER">%</html:option>
      				
      				 </html:select>&nbsp;
					
					</td>
					<td class="formLabel">Applied VAT [OMR]:</td>
					<td><html:text property="appliedVatAed" styleClass="textBox textBoxMedium" styleId="vatAed" readonly="true" disabled="true"/>
					</td>
					</tr>
					<tr>
					<td class="formLabel">Approved Amount[VAT Added]:</td>
					<td><html:text property="vatAddedApprAmnt" styleClass="textBox textBoxMedium" styleId="vatAddedApprAmnt" readonly="true" disabled="true"/>[OMR]
					</td>
					<td></td><td></td>
					</tr>
					 </logic:notEqual> 
				  <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
				  <tr>
					<td class="formLabel">Applied VAT [%]:</td>
					<td><html:text property="appliedVatPercent" styleClass="textBox textBoxMedium" styleId="appliedVatPercent"  onkeyup="isPositiveNumber(this,'Applied VAT[%]')"/>
					<html:select property="appliedVatAEDPER" styleId="appliedVatAEDPER"  styleClass="selectBox selectBoxSmall"  >
      		        <html:option value="PER">%</html:option>
      		       
      		        </html:select>&nbsp;
					
					</td>
					<td class="formLabel">Applied VAT [OMR]:</td>
					<td><html:text property="appliedVatAed" styleClass="textBox textBoxMedium" styleId="appliedVatAed" readonly="true" disabled="true" onkeyup="isPositiveNumber(this,'Applied VAT[AED]')" />
					</td>
					</tr>
					<tr>
					<td class="formLabel">Approved Amount[VAT Added]:</td>
					<td><html:text property="vatAddedApprAmnt" styleClass="textBox textBoxMedium" styleId="vatAddedApprAmnt" readonly="true" disabled="true" />[OMR]
					</td>
					<td></td><td></td>
					</tr>
				  </logic:equal>
				  </logic:equal>
				   <logic:equal value="N" name="frmClaimGeneral" property="networkProviderType">
					<logic:equal value="123" property="providerCountry" name="frmClaimGeneral">
				   <logic:notEqual value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					<tr>
					<td class="formLabel">Applied VAT [%]:</td>
					<td><html:text property="appliedVatPercent" styleClass="textBox textBoxMedium" styleId="vatPercent" readonly="true" disabled="true"/>
					<html:select property="appliedVatAEDPER" styleId="appliedVatAEDPER"  styleClass="selectBox selectBoxSmall"  readonly="true" disabled="true" >
      				 <html:option value=""></html:option>
      				 <html:option value="PER">%</html:option>
      				
      				 </html:select>&nbsp;
					
					</td>
					<td class="formLabel">Applied VAT [OMR]:</td>
					<td><html:text property="appliedVatAed" styleClass="textBox textBoxMedium" styleId="vatAed" readonly="true" disabled="true"/>
					</td>
					</tr>
					<tr>
					<td class="formLabel">Approved Amount[VAT Added]:</td>
					<td><html:text property="vatAddedApprAmnt" styleClass="textBox textBoxMedium" styleId="vatAddedApprAmnt" readonly="true" disabled="true"/>[OMR]
					</td>
					<td></td><td></td>
					</tr>
					 </logic:notEqual> 
				  <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
				  <tr>
					<td class="formLabel">Applied VAT [%]:</td>
					<td><html:text property="appliedVatPercent" styleClass="textBox textBoxMedium" styleId="appliedVatPercent"  onkeyup="isPositiveNumber(this,'Applied VAT[%]')"/>
					<html:select property="appliedVatAEDPER" styleId="appliedVatAEDPER"  styleClass="selectBox selectBoxSmall"  >
      		        <html:option value="PER">%</html:option>
      		       
      		        </html:select>&nbsp;
					
					</td>
					<td class="formLabel">Applied VAT [OMR]:</td>
					<td><html:text property="appliedVatAed" styleClass="textBox textBoxMedium" styleId="appliedVatAed" readonly="true" disabled="true" onkeyup="isPositiveNumber(this,'Applied VAT[AED]')" />
					</td>
					</tr>
					<tr>
					<td class="formLabel">Approved Amount[VAT Added]:</td>
					<td><html:text property="vatAddedApprAmnt" styleClass="textBox textBoxMedium" styleId="vatAddedApprAmnt" readonly="true" disabled="true" />[OMR]
					</td>
					<td></td><td></td>
					</tr>
				  </logic:equal>
				  </logic:equal>
				  </logic:equal>
				  </logic:equal>
				  </logic:equal> 
				  
				  
				  <logic:equal value="DBL" property="processType" name="frmClaimGeneral"> 
				  <logic:equal value="CNH" property="claimType" name="frmClaimGeneral">
				  <logic:notEqual value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
					<tr>
					<td class="formLabel">Applied VAT [%]:</td>
					<td><html:text property="appliedVatPercent" styleClass="textBox textBoxMedium" styleId="vatPercent" readonly="true" disabled="true"/>
					<html:select property="appliedVatAEDPER" styleId="appliedVatAEDPER"  styleClass="selectBox selectBoxSmall"  readonly="true" disabled="true" >
      				 <html:option value=""></html:option>
      				 <html:option value="PER">%</html:option>
      				
      				 </html:select>&nbsp;
					
					</td>
					<td class="formLabel">Applied VAT [OMR]:</td>
					<td><html:text property="appliedVatAed" styleClass="textBox textBoxMedium" styleId="vatAed" readonly="true" disabled="true"/>
					</td>
					</tr>
					<tr>
					<td class="formLabel">Approved Amount[VAT Added]:</td>
					<td><html:text property="vatAddedApprAmnt" styleClass="textBox textBoxMedium" styleId="vatAddedApprAmnt" readonly="true" disabled="true"/>[OMR]
					</td>
					<td></td><td></td>
					</tr>
					 </logic:notEqual> 
				  <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" scope="session">
				  <tr>
					<td class="formLabel">Applied VAT [%]:</td>
					<td><html:text property="appliedVatPercent" styleClass="textBox textBoxMedium" styleId="appliedVatPercent"  onkeyup="isPositiveNumber(this,'Applied VAT[%]')"/>
					<html:select property="appliedVatAEDPER" styleId="appliedVatAEDPER"  styleClass="selectBox selectBoxSmall"  >
      		        <html:option value="PER">%</html:option>
      		       
      		        </html:select>&nbsp;
					
					</td>
					<td class="formLabel">Applied VAT [OMR]:</td>
					<td><html:text property="appliedVatAed" styleClass="textBox textBoxMedium" styleId="appliedVatAed" readonly="true" disabled="true" onkeyup="isPositiveNumber(this,'Applied VAT[AED]')" />
					</td>
					</tr>
					<tr>
					<td class="formLabel">Approved Amount[VAT Added]:</td>
					<td><html:text property="vatAddedApprAmnt" styleClass="textBox textBoxMedium" styleId="vatAddedApprAmnt" readonly="true" disabled="true" />[OMR]
					</td>
					<td></td><td></td>
					</tr>
				  </logic:equal>
				  </logic:equal> 
				   </logic:equal>
				   
				   
				 </logic:notEqual>
				
				
				
				
				
				
				
				
				
				
				
				   </table>	
				   
			</fieldset>
			<fieldset>
			<legend>Remarks</legend>  
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">	
		 <tr>			  
		<td class="formLabel">Denial Description: </td>
					  <td class="formLabel">
					  <table>
					  <tr>
					  <td nowrap>
					  <html:select property="denialCode" name="frmActivityDetails" styleClass="selectBox selectBoxListSecondLargest">
					  <html:option value="">Select from list</html:option>
					   <%-- <html:optionsCollection name="denialDescriptions" label="cacheDesc" value="cacheId" /> --%>
					    <html:optionsCollection  property="denialCodeList" label="cacheDesc" value="cacheId" /> 
					  </html:select>
					  </td>
					  <td>
					  &nbsp;
					   <logic:equal value="Y" name="frmActivityDetails" property="overrideYN">	
					  <button type="button" onclick="addDenialDesc();" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'">Add</button>
					  </logic:equal>
					  </td>
					  </tr>
					  </table>
					  </td>	
					  <td>Approve(Y/N):</td>
					  <td class="textLabel">
					  <html:select property="activityStatus" name="frmActivityDetails">
					  <html:option value="Y">YES</html:option>
					  <html:option value="N">NO</html:option>
					  </html:select>
					  </td>				 
				   </tr>
				    <logic:equal value="Y" name="frmActivityDetails" property="overrideYN">				   	
				   <tr>
				   <td colspan="4">
				   <table border='0' align='center' cellpadding='0' cellspacing='0' class='gridWithCheckBox'  style='width:850px;height:auto;'>
           			 <tr>
           			<th align='center' class='gridHeader' style="width: 10%;">Denial Code</th>
           			<th align='center' class='gridHeader'style="width: 85%;">Denial Desription</th>
           			<th align='center' class='gridHeader' style="width: 5%;">Delete</th>
           			</tr>
				   <logic:notEmpty name="activityDenialList" scope="session">
				   <logic:iterate id="DenialList" name="activityDenialList" scope="session">
				   <tr>
	                <td align='center' style="width: 10%;border: 1px solid gray;"><bean:write name="DenialList" property="key"/></td>
	                <td align='left' style="width: 85%;border: 1px solid gray;"><bean:write name="DenialList" property="value"/></td>
	                <td align='center' style="width: 5%;border: 1px solid gray;"><a href='#' onClick="javascript:deleteDenialDesc('<bean:write name="DenialList" property="key"/>');"><img src='/ttk/images/DeleteIcon.gif' alt='Delete Denial Code' width='14' height='14' border='0'></a></td>
                  </tr>
				   </logic:iterate>
				   
				   </logic:notEmpty>
				   </table>
				   </td>
				   </tr>
				   </logic:equal>
		<tr>
		   <td class="formLabel">Remarks:</td>
		   <td class="textLabel">
		   <html:textarea property="activityRemarks" name="frmActivityDetails"  cols="60" rows="2" />
		 </td>
		 <td></td><td></td>
		 </tr>	
		<logic:equal value="Y" name="frmClaimGeneral" property="enableucr" scope="session">
				   <tr>
		   <td class="formLabel">Disallowance/Denial Reason:</td>
		   <td class="textLabel">
		   <html:textarea property="denialRemarks" name="frmActivityDetails"  cols="60" rows="2" />
		 </td>
		 <td></td><td></td>
		 </tr>
		 </logic:equal>
		 
		 
		 <%
		 String actDtlSeqId		=	(String)request.getAttribute("actDtlSeqId");
		 String[] editDetals	=	(String[])	(request.getSession().getAttribute("editDetails"+actDtlSeqId)==null?new String[6]:request.getSession().getAttribute("editDetails"+actDtlSeqId));
		 %>
		 <tr><td colspan="4">
		 	<table class="gridWithCheckBox"  border="0" cellspacing="0" cellpadding="0">
		 	<tr><th align='center' class='gridHeader'> Edit ID </th>
		 		<th align='center' class='gridHeader'> Edit Type </th>
		 		<th align='center' class='gridHeader'> Edit SubType</th>
		 		<th align='center' class='gridHeader'> Edit Code </th>
		 		<th align='center' class='gridHeader'> Edit Comment </th>
		 		<th align='center' class='gridHeader'> Activity Edit ID</th>
		 		<th align='center' class='gridHeader'> Activity Edit Edit ID </th>
		 	</tr>
		  
					    
					   
		 	
		 <tr>



		 	<%for(String strVal: editDetals){ %>
		 	<td><%= strVal==null ? "": strVal%> </td>
		 	<%} %>
		 	</tr>
		 	</table>
		 </td></tr>	
		 
		 
		
				   <tr>
				   <td colspan="4" align="center">
				    <button type="button" name="Button1" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="saveActivityDetails()"><u>S</u>ave</button>&nbsp;
				    <button type="button" name="Button1" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="resetActivityDetails()"><u>R</u>eset</button>&nbsp;
		            <button type="button" onclick="closeActivities();" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"><u>C</u>lose</button>&nbsp;			  
				    </td>
				   </tr>
		</table>
		
			</fieldset>
				   <input type="hidden" name="mode"/>
				   <INPUT TYPE="hidden" NAME="leftlink">
                   <INPUT TYPE="hidden" NAME="sublink">
                   <INPUT TYPE="hidden" NAME="tab">
                   <INPUT TYPE="hidden" NAME="reforward" value="">		
        </fieldset>		
         <html:hidden property="authType" name="frmActivityDetails" value="CLM" />
         <html:hidden property="networkProviderType" name="frmActivityDetails"	 />
         <html:hidden property="denialDescription" name="frmActivityDetails"/>	 
         <html:hidden property="enableucr" styleId="enableucr" name="frmClaimGeneral"/>	 
         <html:hidden property="haad_YN" styleId="haad_YN" name="frmActivityDetails"/>
	     <html:hidden property="tariffYN" styleId="tariffYN" name="frmActivityDetails"/>	
	     
	     <html:hidden property="processType" styleId="processType" name="frmClaimGeneral"/>	
	       <html:hidden property="modeOfClaim" styleId="modeOfClaim" name="frmClaimGeneral"/>	
	    
         <input type="hidden" id="conversionRate" value="<bean:write name="frmClaimGeneral" property="conversionRate"/>">
          <html:hidden property="condDenialCode" styleId="condDenialCode" name="frmActivityDetails"/>	
         <%--   <html:hidden property="provAuthority"  name="frmActivityDetails"/> --%>   
          <html:hidden property="payerAuthority"  name="frmClaimGeneral"/>	
          <html:hidden property="claimType"  name="frmClaimGeneral"/>	
            <html:hidden property="networkProviderType"  name="frmClaimGeneral"/> 
             <html:hidden property="providerCountry"  name="frmClaimGeneral"/> 
           
       
            
	</html:form>		
</div>	 			    
</body>
</html>