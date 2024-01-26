<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>

<%@ page
	import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper"%>





<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activity Details</title>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
<script language="javascript" src="/ttk/scripts/preauth/activitydetails.js"></script>	

<script type="text/javascript">
var JS_Focus_Disabled =true;
</script>
</head>
<body>
<%
pageContext.setAttribute("category", Cache.getCacheObject("category"));
pageContext.setAttribute("haadCategory", Cache.getCacheObject("haadCategory"));
/* pageContext.setAttribute("denialDescriptions", Cache.getCacheObject("denialDescriptions")); */
pageContext.setAttribute("modifiers", Cache.getCacheObject("modifiers"));
pageContext.setAttribute("unitTypes", Cache.getCacheObject("unitTypes"));
pageContext.setAttribute("activityServiceTypes", Cache.getCacheObject("activityServiceTypes"));
pageContext.setAttribute("activityServiceCodes", Cache.getCacheObject("activityServiceCodes"));

boolean dateStatus=request.getSession().getAttribute("dateStatus")==null?false:(Boolean)request.getSession().getAttribute("dateStatus");
boolean tariffStatus=false;
boolean serviceTypeStatus=false;
boolean FlagStatus=true;
boolean haadStatus=true;

%>

<logic:notEqual value="Y" name="frmActivityDetails" property="overrideYN">
	<%FlagStatus=true;%>
</logic:notEqual>


<logic:equal name="frmActivityDetails" property="haad_YN" value="Y">
		<logic:equal name="frmActivityDetails" property="edit_YN" value="Y">
			<%haadStatus=false;%>
 		</logic:equal>
 </logic:equal>
 
 
 
 <logic:notEqual value="SCD" name="frmActivityDetails" property="activityServiceType">
 <%serviceTypeStatus=true;%>
 </logic:notEqual>
<logic:equal name="frmActivityDetails" property="tariffYN" value="Y">
<%tariffStatus=true;%>
</logic:equal>

<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				    <td width="57%">Activity Details -<logic:empty name="frmActivityDetails" property="activityDtlSeqId">New</logic:empty><logic:notEmpty name="frmActivityDetails" property="activityDtlSeqId">Edit</logic:notEmpty>[<bean:write name="frmActivityDetails" property="activityCode"/>]</td>
					<td  align="right" class="webBoard"></td>
			  </tr>
		</table>
<div class="contentArea" id="contentArea">
<html:errors/>

<logic:notEmpty name="frmActivityDetails"  property="irdrgWarnMsg">
    <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/ttk/images/ErrorIcon.gif" alt="Error" width="16" height="16" align="absmiddle" >&nbsp;
          <bean:write name="frmActivityDetails" property="irdrgWarnMsg"/>
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
<logic:notEmpty name="frmActivityDetails" property="mohPatientShareErrMsg">
			<table align="center" class="errorContainer"  border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/warning.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:write name="frmActivityDetails" property="mohPatientShareErrMsg"/>
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
		<legend>Activity/Service Details</legend>
		<fieldset>
		<logic:equal value="DHP" name="frmPreAuthGeneral" property="preAuthRecvTypeID">
		<logic:notEmpty name="frmActivityDetails" property="activityId">
		<table align="right">
		<tr>
			<td>
			<input type="button" onmouseover="View Document" value="View Document" onclick="onViewDocument();">
			</td>
			
			</tr></table>
			</logic:notEmpty>
			</logic:equal>
		     <legend>General</legend>
				<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">				
			<tr>
			    <td width="22%" class="formLabel">Pre-Auth No.:</td>
			    <td width="30%" class="textLabelBold">
			    <html:text size="30" readonly="true" name="frmActivityDetails" property="preAuthNo" styleClass="disabledBox"  />
			    <html:hidden property="preAuthSeqID" name="frmActivityDetails" />
			    <html:hidden property="activitySeqId" name="frmActivityDetails" />
			    <html:hidden property="activityDtlSeqId" name="frmActivityDetails" />
			    <input type="hidden" name="providerSeqID" value="<bean:write name="frmPreAuthGeneral" property="providerSeqId"/>">			  
			    </td>
			    <td width="22%" class="formLabel">
			    <logic:equal value="Y" name="frmActivityDetails" property="overrideYN">
			      Override
			    </logic:equal>
			    </td>
			    <td>
			     <logic:equal value="Y" name="frmActivityDetails" property="overrideYN">
			       <html:checkbox name="frmActivityDetails" property="overrideYN" styleId="overrideYN"   value="Y" />
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
			    <logic:equal value="IPT" name="frmPreAuthGeneral" property="benefitType">
			    	  <tr>
					  <td class="formLabel">Service Type:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  <html:select property="activityServiceType" name="frmActivityDetails" styleClass="selectBox selectBoxMedium" onchange="onChangeServiceType();">
					  <html:option value="">Select from list</html:option>
					   <html:optionsCollection name="activityServiceTypes" label="cacheDesc" value="cacheId" />
					  </html:select>
					  </td>
					  <td class="formLabel">Service Code:<span class="mandatorySymbol">*</span></td>
					  <td class="textLabel">
					  
					   <html:select property="activityServiceCode" name="frmActivityDetails" styleClass="selectBox selectBoxListLargest" disabled="<%=serviceTypeStatus%>">
					  <html:option value="">Select from list</html:option>
					   <html:optionsCollection name="activityServiceCodes" label="cacheDesc" value="cacheId" />
					  </html:select>
					  </td>
					  </tr>
			   </logic:equal>
			    <logic:equal value="MTI" name="frmPreAuthGeneral" property="benefitType">
			    <logic:equal value="3" name="frmPreAuthGeneral"property="encounterTypeId">
			    	  <tr>
					  <td class="formLabel">Service Type:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  <html:select property="activityServiceType" name="frmActivityDetails" styleClass="selectBox selectBoxMedium" onchange="onChangeServiceType();">
					  <html:option value="">Select from list</html:option>
					   <html:optionsCollection name="activityServiceTypes" label="cacheDesc" value="cacheId" />
					  </html:select>
					  </td>
					  <td class="formLabel">Service Code:<span class="mandatorySymbol">*</span></td>
					  <td class="textLabel">
					  
					   <html:select property="activityServiceCode" name="frmActivityDetails" styleClass="selectBox selectBoxListLargest" disabled="<%=serviceTypeStatus%>">
					  <html:option value="">Select from list</html:option>
					   <html:optionsCollection name="activityServiceCodes" label="cacheDesc" value="cacheId" />
					  </html:select>
					  </td>
					  </tr>
			   </logic:equal>
			   </logic:equal>
			    <logic:equal value="MTI" name="frmPreAuthGeneral" property="benefitType">
			    <logic:equal value="4" name="frmPreAuthGeneral"property="encounterTypeId">
			    	  <tr>
					  <td class="formLabel">Service Type:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  <html:select property="activityServiceType" name="frmActivityDetails" styleClass="selectBox selectBoxMedium" onchange="onChangeServiceType();">
					  <html:option value="">Select from list</html:option>
					   <html:optionsCollection name="activityServiceTypes" label="cacheDesc" value="cacheId" />
					  </html:select>
					  </td>
					  <td class="formLabel">Service Code:<span class="mandatorySymbol">*</span></td>
					  <td class="textLabel">
					  
					   <html:select property="activityServiceCode" name="frmActivityDetails" styleClass="selectBox selectBoxListLargest" disabled="<%=serviceTypeStatus%>">
					  <html:option value="">Select from list</html:option>
					   <html:optionsCollection name="activityServiceCodes" label="cacheDesc" value="cacheId" />
					  </html:select>
					  </td>
					  </tr>
			   </logic:equal>
			   </logic:equal>
			    <logic:equal value="DAYC" name="frmPreAuthGeneral" property="benefitType">
			    	  <tr>
					  <td class="formLabel">Service Type:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  <html:select property="activityServiceType" name="frmActivityDetails" styleClass="selectBox selectBoxMedium" onchange="onChangeServiceType();">
					  <html:option value="">Select from list</html:option>
					   <html:optionsCollection name="activityServiceTypes" label="cacheDesc" value="cacheId" />
					  </html:select>
					  </td>
					  <td class="formLabel">Service Code:<span class="mandatorySymbol">*</span></td>
					  <td class="textLabel">
					  
					   <html:select property="activityServiceCode" name="frmActivityDetails" styleClass="selectBox selectBoxListLargest" disabled="<%=serviceTypeStatus%>">
					  <html:option value="">Select from list</html:option>
					   <html:optionsCollection name="activityServiceCodes" label="cacheDesc" value="cacheId" />
					  </html:select>
					  </td>
					  </tr>
			   </logic:equal>
			   <tr>
			     <td class="formLabel">Start Date:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  <table cellspacing="0" cellpadding="0">
			    	    <tr>

			    	    	<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
			    				<td><html:text name="frmActivityDetails" property="activityStartDate" styleClass="textBox textDate" maxlength="10" readonly="<%=dateStatus%>" /><A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmActivityDetails.activityStartDate',document.frmActivityDetails.activityStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
			    			</logic:equal>
			    			<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
			    				<td>
			    					<html:text name="frmActivityDetails" property="activityStartDate" styleClass="textBox textDate" maxlength="10" readonly="true" disabled="true" /><img src="/ttk/images/CalendarIcon.gif"  name="empDate" width="24" height="17" border="0" align="absmiddle">
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
			  <html:text property="clinicianId" styleId="clinicianId" styleClass="textBox textBoxLarge disabledBox" readonly="true"/>	      	
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
			  <html:text property="clinicianName" styleId="clinicianName" styleClass="textBox textBoxLarge disabledBox" readonly="true"/>	      	
			  </td>		    
			 </logic:equal>	  
			  </tr>
			  
			  
			  
			    <logic:equal value="IPT" property="benefitType" name="frmPreAuthGeneral">
			    
			    <logic:equal value="ACD" property="activityServiceType" name="frmActivityDetails">
				  <tr>				  
					  <td class="formLabel">Activity Code:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  <table>
					   <tr>
					   <logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
				           <td>
				          	 <html:text name="frmActivityDetails" property="activityCode" styleId="activityCode" styleClass="textBox textBoxMedium" onblur="getActivityCodeDetails();"  /><!--onblur="getActivityCodeDetails();"  -->
				           </td>
				           <td>
				          	 <a href="#" accesskey="g"  onClick="javascript:selectActivityCode()" class="search"> <img src="/ttk/images/EditIcon.gif" alt="Select Activity Code" width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
				           </td>
			           </logic:equal>
			           
			            <logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
				           <td>
				          	 <html:text name="frmActivityDetails" property="activityCode" styleId="activityCode" styleClass="textBox textBoxMedium" onblur="getActivityCodeDetails();" readonly="true" />
				           </td>
				           <td>
				          	 <img src="/ttk/images/EditIcon.gif"  width="16" height="16" border="0" align="absmiddle">&nbsp;
				           </td>
			           </logic:equal>
			           </tr>
			         </table>					    
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
				    <td class="formLabel">Category:<span class="mandatorySymbol">*</span></td>
				    <td class="textLabel">
         				  <html:select property="sCategory" name="frmActivityDetails" onchange="javascript:getActivityCodeDetails()" styleClass="selectBox textBoxLarge">
		    			  <html:option value="">Select</html:option>
		    					 <logic:equal value="HAAD" property="provAuthority" name="frmPreAuthGeneral" scope="session">
  									<html:options collection="haadCategory"  property="cacheId" labelProperty="cacheDesc"/>
		     					</logic:equal>
		   						<logic:notEqual value="HAAD" property="provAuthority" name="frmPreAuthGeneral" scope="session">
  									<html:options collection="category"  property="cacheId" labelProperty="cacheDesc"/>
		   					  </logic:notEqual>
      					  </html:select>
       				 </td>
       			 <td></td>
       
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
					  </logic:equal>
					  </logic:equal>
					  
			   
				 <logic:equal value="MTI" property="benefitType" name="frmPreAuthGeneral">
			     <logic:equal value="3" name="frmPreAuthGeneral"property="encounterTypeId">
			    <logic:equal value="ACD" property="activityServiceType" name="frmActivityDetails">
				  <tr>				  
					  <td class="formLabel">Activity Code:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  <table>
					   <tr>
					   		<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
					           <td>
					           <html:text name="frmActivityDetails" property="activityCode" styleId="activityCode" styleClass="textBox textBoxMedium" onblur="getActivityCodeDetails();"  /><!--onblur="getActivityCodeDetails();"  -->
					           </td>
					           <td>
					           <a href="#" accesskey="g"  onClick="javascript:selectActivityCode()" class="search"> <img src="/ttk/images/EditIcon.gif" alt="Select Activity Code" width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
					           </td>
				           	</logic:equal>
				           	
				           	<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
					           <td>
					           <html:text name="frmActivityDetails" property="activityCode" styleId="activityCode" styleClass="textBox textBoxMedium" onblur="getActivityCodeDetails();" readonly="true"  />
					           </td>
					           <td>
					           <img src="/ttk/images/EditIcon.gif"  width="16" height="16" border="0" align="absmiddle">&nbsp;
					           </td>
				           	</logic:equal>
				           	
				           	
			           </tr>
			         </table>					    
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
				    <td class="formLabel">Category:<span class="mandatorySymbol">*</span></td>
				    <td class="textLabel">
         				  <html:select property="sCategory" name="frmActivityDetails" onchange="javascript:getActivityCodeDetails()" styleClass="selectBox textBoxLarge">
		    			  <html:option value="">Select</html:option>
		    					 <logic:equal value="HAAD" property="provAuthority" name="frmPreAuthGeneral" scope="session">
  									<html:options collection="haadCategory"  property="cacheId" labelProperty="cacheDesc"/>
		     					</logic:equal>
		   						<logic:notEqual value="HAAD" property="provAuthority" name="frmPreAuthGeneral" scope="session">
  									<html:options collection="category"  property="cacheId" labelProperty="cacheDesc"/>
		   					  </logic:notEqual>
      					  </html:select>
       				 </td>
       			 <td></td>
       
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
					  </logic:equal>
					  </logic:equal>
					   </logic:equal>
					   
					   
				<logic:equal value="MTI" property="benefitType" name="frmPreAuthGeneral">
			     <logic:equal value="4" name="frmPreAuthGeneral"property="encounterTypeId">
			    <logic:equal value="ACD" property="activityServiceType" name="frmActivityDetails">
				  <tr>				  
				  <td class="formLabel">Activity Code:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  <table>
					   <tr>
					   <logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
					           <td>
					           <html:text name="frmActivityDetails" property="activityCode" styleId="activityCode" styleClass="textBox textBoxMedium" onblur="getActivityCodeDetails();"  /><!--onblur="getActivityCodeDetails();"  -->
					           </td>
					           <td>
					           <a href="#" accesskey="g"  onClick="javascript:selectActivityCode()" class="search"> <img src="/ttk/images/EditIcon.gif" alt="Select Activity Code" width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
					           </td>
					   </logic:equal>        
					   
					    <logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
					           <td>
					           		<html:text name="frmActivityDetails" property="activityCode" styleId="activityCode" styleClass="textBox textBoxMedium" onblur="getActivityCodeDetails();" readonly="true"  />
					           </td>
					           <td>
					          		<img src="/ttk/images/EditIcon.gif"  width="16" height="16" border="0" align="absmiddle">&nbsp;
					           </td>
					   </logic:equal>        
			           </tr>
			         </table>					    
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
				    <td class="formLabel">Category:<span class="mandatorySymbol">*</span></td>
				    <td class="textLabel">
         				  <html:select property="sCategory" name="frmActivityDetails" onchange="javascript:getActivityCodeDetails()" styleClass="selectBox textBoxLarge">
		    			  <html:option value="">Select</html:option>
		    					 <logic:equal value="HAAD" property="provAuthority" name="frmPreAuthGeneral" scope="session">
  									<html:options collection="haadCategory"  property="cacheId" labelProperty="cacheDesc"/>
		     					</logic:equal>
		   						<logic:notEqual value="HAAD" property="provAuthority" name="frmPreAuthGeneral" scope="session">
  									<html:options collection="category"  property="cacheId" labelProperty="cacheDesc"/>
		   					  </logic:notEqual>
      					  </html:select>
       				 </td>
       			 <td></td>
       
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
					  </logic:equal>
					  </logic:equal>
					   </logic:equal>
					  
					  
			    <logic:equal value="DAYC" property="benefitType" name="frmPreAuthGeneral">
			    
			    <logic:equal value="ACD" property="activityServiceType" name="frmActivityDetails">
				  <tr>				  
					  <td class="formLabel">Activity Code:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  <table>
					   <tr>
					   		<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
					           <td>
					           <html:text name="frmActivityDetails" property="activityCode" styleId="activityCode" styleClass="textBox textBoxMedium" onblur="getActivityCodeDetails();"  /><!--onblur="getActivityCodeDetails();"  -->
					           </td>
					           <td>
					           <a href="#" accesskey="g"  onClick="javascript:selectActivityCode()" class="search"> <img src="/ttk/images/EditIcon.gif" alt="Select Activity Code" width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
					           </td>
					        </logic:equal>   
					        
					        <logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
					           <td>
					           <html:text name="frmActivityDetails" property="activityCode" styleId="activityCode" styleClass="textBox textBoxMedium" onblur="getActivityCodeDetails();" readonly="true" />
					           </td>
					           <td>
					           <img src="/ttk/images/EditIcon.gif"  width="16" height="16" border="0" align="absmiddle">&nbsp;
					           </td>
					        </logic:equal>   
			           </tr>
			         </table>					    
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
				    <td class="formLabel">Category:<span class="mandatorySymbol">*</span></td>
				    <td class="textLabel">
         				  <html:select property="sCategory" name="frmActivityDetails" onchange="javascript:getActivityCodeDetails()" styleClass="selectBox textBoxLarge">
		    			  <html:option value="">Select</html:option>
		    					 <logic:equal value="HAAD" property="provAuthority" name="frmPreAuthGeneral" scope="session">
  									<html:options collection="haadCategory"  property="cacheId" labelProperty="cacheDesc"/>
		     					</logic:equal>
		   						<logic:notEqual value="HAAD" property="provAuthority" name="frmPreAuthGeneral" scope="session">
  									<html:options collection="category"  property="cacheId" labelProperty="cacheDesc"/>
		   					  </logic:notEqual>
      					  </html:select>
       				 </td>
       			 <td></td>
       
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
					  </logic:equal>
					  </logic:equal>
			  
			   <logic:notEqual value="IPT" property="benefitType" name="frmPreAuthGeneral">
			    <logic:notEqual value="DAYC" property="benefitType" name="frmPreAuthGeneral">
			    <logic:notEqual value="IPTMTI" name="frmActivityDetails"  property="hmaternityStatus"> 
			    
					  
					   <tr>				  
					  <td class="formLabel">Activity Code:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
					  <table>
					   <tr>
					   		 <logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
					           <td>
					           <html:text name="frmActivityDetails" property="activityCode" styleId="activityCode" styleClass="textBox textBoxMedium" onblur="getActivityCodeDetails();"  /><!--onblur="getActivityCodeDetails();"  -->
					           </td>
					           <td>
					           <a href="#" accesskey="g"  onClick="javascript:selectActivityCode()" class="search"> <img src="/ttk/images/EditIcon.gif" alt="Select Activity Code" width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
					           </td>
			           		</logic:equal>
			           		<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
					           <td>
					           <html:text name="frmActivityDetails" property="activityCode" styleId="activityCode" styleClass="textBox textBoxMedium" onblur="getActivityCodeDetails();" readonly="true"  />
					           </td>
					           <td>
					           <img src="/ttk/images/EditIcon.gif"  width="16" height="16" border="0" align="absmiddle">&nbsp;
					           </td>
			           		</logic:equal>
			           		
			           </tr>
			         </table>						    
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
				    <td class="formLabel">Category:<span class="mandatorySymbol">*</span></td>
				    <td class="textLabel">
           <html:select property="sCategory" name="frmActivityDetails"  styleClass="selectBox textBoxLarge">
		    	 <html:option value="">Select</html:option>
		    	 <logic:equal value="HAAD" property="provAuthority" name="frmPreAuthGeneral" scope="session">
  				<html:options collection="haadCategory"  property="cacheId" labelProperty="cacheDesc"/>
		     </logic:equal>
		     <logic:notEqual value="HAAD" property="provAuthority" name="frmPreAuthGeneral" scope="session">
  				<html:options collection="category"  property="cacheId" labelProperty="cacheDesc"/>
		     </logic:notEqual>
        </html:select>
        </td>
        <td></td>
       
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
					  
					   </logic:notEqual>
					   </logic:notEqual>
					    </logic:notEqual>
					  
					  
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
					  		<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
					  			<html:text name="frmActivityDetails" property="quantity" styleId="quantity"  styleClass="textBox textBoxSmall" onkeyup="calculateNetAmount(this)"  />
					  		</logic:equal>
					  		<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
					  			<html:text name="frmActivityDetails" property="quantity" styleId="quantity"  styleClass="textBox textBoxSmall" onkeyup="calculateNetAmount(this)" readonly="true" disabled="true" />
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
					  	<html:text name="frmActivityDetails" property="internalCode" styleClass="textBox textBoxMedium" onblur="getInternalCodeDetails();" />
					  </td>
			        <td class="formLabel">Posology: </td>
					  <td class="textLabel">					  
					  <html:select property="posology" name="frmActivityDetails" styleClass="selectBox selectBoxMedium">
					  <html:option value="">Select from list</html:option>
					   <html:option value="1">OD</html:option>
                       <html:option value="2">BD</html:option>
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
		  <tr>
		          
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
		  	
		
		        
		        <!-- unit price -->
		        <td class="formLabel">Unit Price:<span class="mandatorySymbol">*</span> </td>
					  <td class="textLabel">
			
					  <% boolean editStatus=true; %>
					  <logic:equal name="frmActivityDetails" property="editStatus" value="Y">
					   <% editStatus=false; %>
					   </logic:equal>
					   
					
					    <logic:equal name="frmActivityDetails" property="activityServiceType" value="ACD">
					   <% editStatus=true; %>
					   </logic:equal>
					    
					    <logic:equal name="frmActivityDetails" property="activityServiceType" value="SCD">
					   <% editStatus=false; %>
					   </logic:equal>
					   
					    <logic:equal name="frmActivityDetails" property="activityServiceType" value="PKD">
					   <% editStatus=false; %>
					   </logic:equal> 
		
					   <logic:equal name="frmActivityDetails" property="overrideYN" value="Y">
					   <% editStatus=false; %>
					   </logic:equal>
					   
					    <logic:equal name="frmActivityDetails" property="overrideYN" value="Y">
					   <% editStatus=false; %>
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
					   
					 
					  <logic:equal value="DBL" name="frmPreAuthGeneral" property="processType">
					   <logic:notEmpty name="frmActivityDetails" property="unitPrice">
					    <html:text name="frmActivityDetails" property="unitPrice" styleId="unitPrice"   styleClass="textBox textBoxMedium"  onkeyup="calculateNetAmount(this)" readonly="<%=editStatus%>" title="<%=irdrgFormulaw%>"/>
					    <input  id="requestedAmountcurrencyType"  class="textBox textBoxTooTiny textBoxDisabled" readonly="readonly" value="<bean:write name="frmPreAuthGeneral" property="requestedAmountcurrencyType"/>">
					   </logic:notEmpty>
					 </logic:equal>
					  <logic:equal value="DBL" name="frmPreAuthGeneral" property="processType">
					 <logic:empty name="frmActivityDetails" property="unitPrice">
					    <html:text name="frmActivityDetails" property="unitPrice" styleId="unitPrice" value="0.00"   styleClass="textBox textBoxMedium"  onkeyup="calculateNetAmount(this)" title="<%=irdrgFormulaw%>" />
					   <input  id="requestedAmountcurrencyType"  class="textBox textBoxTooTiny textBoxDisabled" readonly="readonly" value="<bean:write name="frmPreAuthGeneral" property="requestedAmountcurrencyType"/>">
					   </logic:empty>
					   </logic:equal>
					 
					
					    <logic:equal value="GBL" name="frmPreAuthGeneral" property="processType">
					   <logic:notEmpty name="frmActivityDetails" property="unitPrice">
					    <html:text name="frmActivityDetails" property="unitPrice" styleId="unitPrice"   styleClass="textBox textBoxMedium"  onkeyup="calculateNetAmount(this)" readonly="<%=editStatus%>" title="<%=irdrgFormulaw%>"/>
					   <%--  <input  id="requestedAmountcurrencyType"  class="textBox textBoxTooTiny textBoxDisabled" readonly="readonly" value="<bean:write name="frmPreAuthGeneral" property="requestedAmountcurrencyType"/>"> --%>
					   </logic:notEmpty>
					 </logic:equal>
					  <logic:equal value="GBL" name="frmPreAuthGeneral" property="processType">
					 <logic:empty name="frmActivityDetails" property="unitPrice">
					    <html:text name="frmActivityDetails" property="unitPrice" styleId="unitPrice" value="0.00"   styleClass="textBox textBoxMedium"  onkeyup="calculateNetAmount(this)" title="<%=irdrgFormulaw%>" />
					  <%--  <input  id="requestedAmountcurrencyType"  class="textBox textBoxTooTiny textBoxDisabled" readonly="readonly" value="<bean:write name="frmPreAuthGeneral" property="requestedAmountcurrencyType"/>"> --%>
					   </logic:empty>
					   </logic:equal>
			
					
			          
					  </td>
					  <td class="formLabel">Unit Discount:</td>
					  <td class="textLabel">
					   <logic:notEqual value="Y" name="frmActivityDetails" property="overrideYN">
					  <html:text name="frmActivityDetails" property="discountPerUnit"  styleId="discountPerUnit"  styleClass="textBox textBoxMedium disabledBox" readonly="true"/>
					  </logic:notEqual>
					  <logic:equal value="Y" name="frmActivityDetails" property="overrideYN">
					  <html:text name="frmActivityDetails" property="discountPerUnit"  styleId="discountPerUnit"  styleClass="textBox textBoxMedium" onkeyup="isNumeric(this);"/>
					  </logic:equal>
					  </td>
		 </tr>
		 
		 
		 
		 
		 <logic:equal value="DBL" property="processType" name="frmPreAuthGeneral">
		<tr>
		 <td class="formLabel">Converted Unit Price: </td>
		 <td class="textLabel">
			          <html:text name="frmActivityDetails" property="convertedUnitPrice"   styleClass="textBox textBoxMedium disabledBox" readonly="true" />
			          <input  id="convertedUnitPricecurrencyType"  class="textBox textBoxTooTiny textBoxDisabled" readonly="readonly" value="<bean:write name="frmPreAuthGeneral" property="currencyType"/>">
			          </td>
			          <td> </td>
			          <td> </td>
			          </tr> 
		 
		 </logic:equal>
		 
		 
		 
		 
		 
		 
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
					  
					  
					 <%--  <logic:equal name="frmActivityDetails" property="haad_YN" value="Y">
					  		 <html:text name="frmActivityDetails" property="discount" styleId="discount"  styleClass="textBox textBoxMedium"  onblur="calculateNetAmount(this)" readonly="true"/>
					</logic:equal>
					<logic:notEqual name="frmActivityDetails" property="haad_YN" value="Y">
					  		 <html:text name="frmActivityDetails" property="discount" styleId="discount"  styleClass="textBox textBoxMedium"  onblur="calculateNetAmount(this)" />
					</logic:notEqual> --%>
					  
					
					
					 <logic:equal name="frmActivityDetails" property="haad_YN" value="Y">
					  		 <html:text name="frmActivityDetails" property="discount" styleId="discount"  styleClass="textBox textBoxMedium"  onblur="calculateNetAmount(this)" readonly="true"/>
					</logic:equal>
					<logic:equal name="frmActivityDetails" property="haad_YN" value="N">
					  		 <html:text name="frmActivityDetails" property="discount" styleId="discount"  styleClass="textBox textBoxMedium"  onblur="calculateNetAmount(this)" />
					</logic:equal>
					<logic:empty name="frmActivityDetails" property="haad_YN" >
					  		 <html:text name="frmActivityDetails" property="discount" styleId="discount"  styleClass="textBox textBoxMedium"  onblur="calculateNetAmount(this)" />
					</logic:empty>
					
					
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
				   <%-- <tr>
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
					<td class="formLabel">Provider Requested Amount:</td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="providerRequestedAmt"  styleClass="textBox textBoxMedium disabledBox"  readonly="true" />
					  </td>
					</tr>  
					  
					<tr> <td></td> <td></td> 
					  <td class="formLabel">Allowed:  </td>
					  <td>
					  	<html:checkbox name="frmActivityDetails" property="amountAllowed"   value="Y" />
					  </td>
				   </tr>				  
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
					<td class="formLabel">Provider Requested Amount:</td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="providerRequestedAmt"  styleClass="textBox textBoxMedium disabledBox"  readonly="true" />
					  </td>
				</tr>  
					  
				<tr> <td></td> <td></td> 
					  <td class="formLabel">Allowed:  </td>
					  <td>
					  	<html:checkbox name="frmActivityDetails" property="amountAllowed"   value="Y" />
					  </td>
				</tr>				
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
					  <td class="formLabel">Deductible: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="rdeductible" styleClass="textBox textBoxMedium disabledBox" readonly="true"/>
					  </td>
					  <td class="formLabel">Out Of Pocket: </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="routOfPocket" styleClass="textBox textBoxMedium disabledBox"  readonly="true"/>
					  </td>
				   </tr>
				   <tr>
				    <td class="formLabel">Allowed Amount:  </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="allowedAmount"  styleClass="textBox textBoxMedium disabledBox" readonly="true"  />
					  </td>
					  <td class="formLabel">Dis Allowed Amount:  </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="rdisAllowedAmount"  styleClass="textBox textBoxMedium disabledBox" readonly="true"/>
					  </td>
				   </tr>
				   <tr>
				   
				    <td class="formLabel">Approved Amount:  </td>
					  <td class="textLabel">
					  	<html:text name="frmActivityDetails" property="approvedAmount"  styleClass="textBox textBoxMedium disabledBox" readonly="true" />
					  </td>
					 <td class="formLabel">Additional Disallowances: </td>
					 <td>
					  	<html:text name="frmActivityDetails" property="additionalDisallowances"  styleClass="textBox textBoxMedium" onkeyup="calculateNetAmount(this)"/>
					 </td>
					
				   </tr>
				   <tr>
				   <td class="formLabel">Provider Area Of Coverage Copay: </td>
					
					<td class="textLabel">
					<html:text name="frmActivityDetails" property="providerAreCopay"  readonly="true"  styleClass="textBox textBoxMedium disabledBox"/>
					</td>
					<td></td><td></td>
					</tr>
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
					   <%--  <html:optionsCollection name="denialCodeList" label="cacheDesc" value="cacheId" />  --%>
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
					  <html:select property="activityStatus" name="frmActivityDetails" styleClass="selectBox selectBoxMedium">
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
		
				   <tr>
				   <td align="center" colspan="4">
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
                   <html:hidden property="authType" name="frmActivityDetails" value="PAT"/>
                   <html:hidden property="networkProviderType" name="frmActivityDetails"/>
                   <html:hidden property="denialDescription" name="frmActivityDetails"/>
                   <bean:define id="benefitTypeID" name="frmPreAuthGeneral" property="benefitType" type="java.lang.String"/>
                   <html:hidden property="benefitType" name="frmActivityDetails" value="<%=benefitTypeID%>"/>
                   <html:hidden property="activityCodeSeqId" styleId="activityCodeSeqId"/>
				     <html:hidden property="activityTypeId" styleId="activityTypeId"/>
				    <html:hidden property="activityId" name="frmActivityDetails" />
					<html:hidden property="haad_YN" styleId="haad_YN" name="frmActivityDetails"/>
					  <html:hidden property="tariffYN" styleId="tariffYN" name="frmActivityDetails"/>
					  
				      <html:hidden name="frmPreAuthGeneral"  property="benefitType"/>
				       <html:hidden name="frmPreAuthGeneral"  property="processType"/> 
				       <html:hidden name="frmActivityDetails"  property="hmaternityStatus"/>
				      
				      <input type="hidden" id="conversionRate" value="<bean:write name="frmPreAuthGeneral" property="conversionRate"/>">
				       <html:hidden property="condDenialCode"  name="frmActivityDetails"/>
				       <html:hidden property="payerAuthority"  name="frmActivityDetails"/>
        </fieldset>       		   
	</html:form>		
</div>	 			    
</body>
 <script type="text/javascript">  
 document.forms[1].setAttribute( "autocomplete", "off" );      
  </script>	
</html>