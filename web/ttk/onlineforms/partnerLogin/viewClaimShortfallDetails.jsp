<%
/**
 * @ (#) viewClaimShortfallDetails.jsp Nov 19 2015 
 * Project      : TTK HealthCare Services Dubai
 * File         : viewAuthDetails.jsp
 * Author       : 
 * Company      : RCS Technologies
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
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/partnerLogin/viewClaimDetails.js"></SCRIPT>
</head>
<%
pageContext.setAttribute("ShortFallStatusID",Cache.getCacheObject("shortfallStatus"));
%>
<html:form action="/OnlinePartnerClaimsShortfallAction.do" method="post" enctype="multipart/form-data">
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
	<td style="width: 19%" align="left" class="formLabel"><b>Claim No.</b></td> 
	<td style="width: 1%" align="left">:</td>
	<td style="width: 80%" align="left">${shortFallData[15]}</td>
</tr>
<tr>
	<td style="width: 19%" align="left" class="formLabel"><b>Claim Status</b></td> 
	<td style="width: 1%" align="left">:</td>
	<td style="width: 80%" align="left">${shortFallData[1]}</td>
</tr>
</table> 
<br>
	<fieldset><legend>Claim Request Details</legend>
		<table style="background: #e3e3e3; border-radius:10px; width:99%; height:auto;" align="center">
		
		<tr>
			<td style="width: 19%" align="left" class="formLabel"><b>Provider Name</b></td> 
			<td style="width: 1%" align="left">:</td>
			<td style="width: 30%" align="left">${shortFallData[3]}</td>
			
			<td style="width: 19%" align="left" class="formLabel"><b>Provider License ID</b></td> 
			<td style="width: 1%" align="left">:</td>
			<td style="width: 30%" align="left">${shortFallData[5]}</td>
		</tr>
		<tr>
			<td style="width: 19%" align="left" class="formLabel"><b>Contact No.</b></td> 
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

		