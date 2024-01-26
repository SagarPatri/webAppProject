<%
/**
 * @ (#) viewShortfallDetails.jsp Nov 19 2015 
 * Project      : TTK HealthCare Services Dubai
 * File         : viewAuthDetails.jsp (Partner)
 * Author       : 
 * Company      : 
 * Date Created : 
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ttk.common.security.Cache" %>

<head>
<link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
    
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/partnerLogin/viewAuthDetails.js"></SCRIPT>
</head>
<%
pageContext.setAttribute("ShortFallStatusID",Cache.getCacheObject("shortfallStatus"));
%>
<html:form action="/ViewPartnerAuthorizationShortfallDetails.do" method="post" enctype="multipart/form-data">
<div class="contentArea" id="contentArea">
<!-- S T A R T : Content/Form Area -->
	<!-- S T A R T : Form Fields -->
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
		<!-- E N D : Success Box -->
		
<h4 class="sub_heading">Partner Shortfall Details</h4>
<div id="contentOuterSeparator"></div>
<html:errors/>

<br>
<table align="center" class="formContainer" cellspacing="0" cellpadding="0" style="width: 99%; border: 0;"> 
<tr>
	<td style="width: 19%" align="left" class="formLabel"><b>Shortfall No.</b></td> 
	<td style="width: 1%" align="left">:</td>
	<td style="width: 80%" align="left">${shortFallData[0]}</td>
</tr>
<tr>
	<td style="width: 19%" align="left" class="formLabel"><b>Pre-Approval Reference No.</b></td> 
	<td style="width: 1%" align="left">:</td>
	<td style="width: 80%" align="left">${shortFallData[15]}</td>
</tr>
<tr>
	<td style="width: 19%" align="left" class="formLabel"><b>Pre-Approval Status</b></td> 
	<td style="width: 1%" align="left">:</td>
	<td style="width: 80%" align="left">${shortFallData[1]}</td>
</tr>
</table> 
<br>
	<fieldset><legend>PRE-APPROVAL REQUEST DETAILS</legend>
		<table style="background: #e3e3e3; border-radius:10px; width:99%; height:auto;" align="center">
		
		<tr>
			<td style="width: 19%" align="left" class="formLabel"><b>Provider Name</b></td> 
			<td style="width: 1%" align="left">:</td>
			<td style="width: 30%" align="left">${shortFallData[3]}</td>
			
			<td style="width: 19%" align="left" class="formLabel"><b>Provider License No</b></td> 
			<td style="width: 1%" align="left">:</td>
			<td style="width: 30%" align="left">${shortFallData[5]}</td>
		</tr>
		<tr>
			<td style="width: 19%" align="left" class="formLabel"><b>Contact No</b></td> 
			<td style="width: 1%" align="left">:</td>
			<td style="width: 30%" align="left">${shortFallData[4]}</td>
			
			<td style="width: 19%" align="left" class="formLabel"><b>Provider Email ID</b></td> 
			<td style="width: 1%" align="left">:</td>
			<td style="width: 30%" align="left">${shortFallData[6]}</td>
		</tr>
		<tr>
			<td style="width: 19%" align="left" class="formLabel"><b>Patient Name</b></td> 
			<td style="width: 1%" align="left">:</td>
			<td align="left" colspan="2">${shortFallData[7]}</td>
		</tr>
		<tr>
			<td style="width: 19%" align="left" class="formLabel"><b>Al Koot ID No.</b></td> 
			<td style="width: 1%" align="left">:</td>
			<td style="width: 30%" align="left">${shortFallData[8]}</td>
			
			<td style="width: 19%" align="left" class="formLabel"><b>Policy No.</b></td> 
			<td style="width: 1%" align="left">:</td>
			<td style="width: 30%" align="left">${shortFallData[12]}</td>
		</tr>
		<tr>
			<td style="width: 19%" align="left" class="formLabel"><b>Date of Birth</b></td> 
			<td style="width: 1%" align="left">:</td>
			<td style="width: 30%" align="left">${shortFallData[9]}</td>
			
			<td style="width: 19%" align="left" class="formLabel"><b>Gender</b></td> 
			<td style="width: 1%" align="left">:</td>
			<td style="width: 30%" align="left">${shortFallData[10]}</td>
		</tr>
		<tr>
			<td style="width: 19%" align="left" class="formLabel"><b>Benefit Type</b></td> 
			<td style="width: 1%" align="left">:</td>
			<td style="width: 30%" align="left">${shortFallData[14]}</td>
			
			<td style="width: 19%" align="left" class="formLabel"><b>Date of Treatment</b></td> 
			<td style="width: 1%" align="left">:</td>
			<td style="width: 30%" align="left">${shortFallData[11]}</td>
		</tr>
		
		
		<%-- <tr>
	    	<td class="formLabel">Shortfall No.</td>
	    	<td class="textLabelBold"> : <bean:write name="frmShortFall" property="shortfallNo"/></td>
	  	</tr>
		<tr>
	  		<td class="formLabel">Status </td>
	    	<td class="textLabelBold"> : 
	    	<html:select property="statusTypeID"  styleClass="selectBox selectBoxMedium" readonly="true"><!--showhideReasonAuth(this);-->
	  				<html:options collection="ShortFallStatusID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
		</tr>
		<tr>
	      	<td nowrap class="formLabel">Reason  </td>
	    	<td nowrap class="textLabelBold"> : <bean:write name="frmShortFall" property="reasonTypeID"/></td>
		</tr>
		
		<tr>
	      	<td nowrap class="formLabel">Remarks</td>
	      	<td colspan="3" valign="bottom" nowrap class="formLabel"> :
	      		 <bean:write name="frmShortFall" property="remarks"/>
	      	</td>
		</tr>
		<tr>
    	     <td class="formLabel">Correspond Date / Time</td>
			    <td class="textLabel"> :
			    	<bean:write name="frmShortFall" property="correspondenceDate"/>
			    	<bean:write name="frmShortFall" property="correspondenceTime"/>
			    	<bean:write name="frmShortFall" property="correspondenceDay"/>
			    </td>
		</tr>
		<tr>
			<td class="formLabel">Correspondence Count: </td>
			 <td class="textLabel"> <bean:write name="frmShortFall" property="correspondenceCount"/> </td>
		</tr>
		
		<tr> <td> <a href="#" onclick="onShortfallDoc('<bean:write name="frmShortFall" property="shortfallNo"/>')"> View </a> Shortfall Document </td></tr>
		 --%>
		 
		 </table>
	</fieldset>	

   	<ttk:OnlineDiagnosisDetails flow="PAT" flag="PATCreate"/>
   	<br>
   	<fieldset> <legend>ADDITIONAL INFORMATION / CLARIFICATION REQUIRED FOR FURTHER PROCESSING</legend>
	<ttk:ProviderShortFallQueries flow="PAT"/>
	</fieldset>
	<br>
   	<fieldset> <legend>Upload Document</legend>
   	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" style="width: 80%">
		<tr>
	    	<td height="20" class="formLabel">Select File to Upload</td>
	    	<td class="textLabelBold"> : <html:file property="file" styleId="file"/> </td>
	  	</tr>
   	</table>
   	</fieldset>
   	<br>

   <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
			<font color=blue><b>Note :</b>  Please send us the scanned copy of any supporting documents wherever applicable.</font> 
			</td>
     	</tr>
</table>
   <!-- E N D : Form Fields -->
		
    	<!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
		    		<button type="button" name="Button" accesskey="b" class="olbtnSmall" onClick="javascript:onBack();"><u>B</u>ack</button>&nbsp;
		    		<logic:notEqual value="Closed" name="${shortFallData[16]}">
	    				<button type="button" name="Button" accesskey="s" class="olbtnSmall" onClick="javascript:onSaveShortfall();"><u>S</u>ave</button>&nbsp;
	    			</logic:notEqual>
		    		<button type="button" name="Button" accesskey="g" class="olbtn" onClick="javascript:onShortfallDoc('${shortFallData[0]}');"><u>V</u>iew Letter</button>&nbsp;
 				</td>
  			</tr>  		
		</table>
		<!-- E N D : Buttons -->
	</div>

	<!-- E N D : Content/Form Area -->
<!--E N D : Content/Form Area -->
<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>

		