<%
/** @ (#) viewClaimDetails.jsp 
 * Project     : TTK Healthcare Services
 * File        : 
 * Author      : 
 * Company     : 
 * Date Created: 
 *
 * @author 		 : 
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
<%@ page import="java.util.ArrayList,com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="org.apache.struts.action.DynaActionForm"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script language="javascript" src="/ttk/scripts/onlineforms/partnerLogin/viewClaimDetails.js"></script>

<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<script>
			var JS_Focus_Disabled =true;
	</script>
	
</head>
<%
	boolean viewmode=false;
	boolean bEnabled=false;


%>	

<html:form action="/ViewOnlinePartnerClaimDetails.do" >
			<!-- S T A R T : Content/Form Area -->
			<!-- <div id="navigateBar">Home > Corporate > Detailed > Claim Details</div> -->
<h4 class="sub_heading">Partner Online Claim Details</h4>
<div class="contentArea" id="contentArea">
<br>

	<!-- S T A R T : Page Title -->
	<html:errors/>
	<!-- E N D : Page Title --> 
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

<span class="fieldGroupTabHeader"> &nbsp;&nbsp;Claim Details&nbsp;&nbsp;&nbsp;</span><br>
	    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0"> <tr> 
	    	<td width="15%" class="formLabel"> Claim No. </td>
	    	<td width="75%"> : <bean:write name="frmOnlineClaimsDetails" property="claimNO"/> </td>
	    </tr> <tr>
	    	<td width="15%" class="formLabel"> Batch No. </td>
	    	<td width="75%"> : <bean:write name="frmOnlineClaimsDetails" property="batchNo"/> </td>
	    </tr>  <tr>
	    	<td width="15%" class="formLabel"> Claim Status </td>
	    	<td width="75%"> : <bean:write name="frmOnlineClaimsDetails" property="status"/> </td>
	    </tr>   
	</table>
	<br>

  		<span class="fieldGroupTabHeader"> &nbsp;&nbsp;Claim Request Details&nbsp;&nbsp;&nbsp;</span>
		  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
		        <td width="15%" class="formLabel">Provider Name </td>
		        <td width="35%" align="left">: <bean:write name="frmOnlineClaimsDetails" property="providerName"/> </td>
		        <td width="15%" class="formLabel">Provider lincese No.</td>
		        <td width="35%" align="left">: <bean:write name="frmOnlineClaimsDetails" property="providerlincese"/> </td>
	      	</tr>
	      	<tr>
		        <td width="15%" class="formLabel">Contact No</td>
		        <td width="35%" align="left"> : <bean:write name="frmOnlineClaimsDetails" property="contactNo"/> </td>
		        <td width="15%" class="formLabel">Provider Email ID </td>
		        <td width="35%" align="left"> : <bean:write name="frmOnlineClaimsDetails" property="emailId"/> </td>
	      	</tr>
	      	<tr>
		        <td width="15%" class="formLabel">Patient Name</td>
		        <td width="35%" align="left"> : <bean:write name="frmOnlineClaimsDetails" property="patientName"/> </td>
	      	</tr>
	    	<tr>
		        <td width="15%" class="formLabel">Al Koot Id No.</td>
		        <td width="35%" align="left"> : <bean:write name="frmOnlineClaimsDetails" property="patientCardId"/> </td>
		        <td width="15%" class="formLabel">Policy No.</td>
		        <td width="35%" align="left"> : <bean:write name="frmOnlineClaimsDetails" property="policyNo"/> </td>
		     </tr> 
	    	<tr>
		        <td width="15%" class="formLabel">Date of Birth</td>
		        <td width="35%" align="left"> : <bean:write name="frmOnlineClaimsDetails" property="mem_Dob"/> </td>
		        <td width="15%" class="formLabel">Gender</td>
		        <td width="35%" align="left"> : <bean:write name="frmOnlineClaimsDetails" property="gender"/> </td>
		     </tr> 
		     <tr>
		        <td width="15%" class="formLabel">Benefit Type</td>
		        <td width="35%" align="left"> : <bean:write name="frmOnlineClaimsDetails" property="benefitType"/> </td>
		        <td width="15%" class="formLabel">Date of Treatment</td>
		        <td width="35%" align="left"> : <bean:write name="frmOnlineClaimsDetails" property="dtOfTreatment"/> </td>
		     </tr> 
		     </table>  
		        
<br>
	<ttk:OnlineDiagnosisDetails flow="CLM" flag="PATCreate"/>
	<br>
		<ttk:OnlineActivityDetails flow="CLM" flag="PATCreate"/>
	<br>
	
	<!-- Shotfall Docs S T A R T S-->
	<logic:notEmpty name="alShortFallList">
		<fieldset>
			<legend>ShortFall details</legend>
			<table align="center" class="gridWithCheckBox"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="gridHeader" align="left">Date of Shortfall</td>
				<td class="gridHeader" align="left">Shortfall No.</td>
				<td class="gridHeader" align="left">Shortfall Type</td>
				<td class="gridHeader" align="left">Status</td>
				<td class="gridHeader" align="left">Reply Received</td>
				<td class="gridHeader" align="left">Remarks</td>
			</tr>
			<% int i=0; %>
				<c:forEach var="alData1" items="${sessionScope.alShortFallList}">
				<%
					String strClass=i%2==0 ? "gridOddRow" : "gridEvenRow" ;
					i++;
				%>
		      <tr class=<%=strClass%>>
		         <c:forEach var="strD" items="${pageScope.alData1}" varStatus="theCount">
		         <td height="20px">
		            <c:set var="test" value="${theCount.index}"/>
		            <%if(!pageContext.getAttribute("test").toString().equals("6")){%>
			            <%if(pageContext.getAttribute("test").toString().equals("1")){%>
			            <a href="#" onclick="javascript:onViewShortfall('<c:out value="${pageScope.alData1[6]}"/>')"><c:out value="${strD}"/></a>
			            <% }else{%>
			              <c:out value="${strD}"/>
			            <% }%>
		            <% }%>
		         </td>
		         </c:forEach>
		     </tr>
		        </c:forEach>
		  </table>
		</fieldset>	
	</logic:notEmpty>
	<!--  Shortfall Docs E N D S -->
    		<BR>


 <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
   	<tr>
        <td width="100%" align="center">
			<button type="button" name="Button" accesskey="b" class="olbtnSmall" onClick="javascript:onBack();"><u>B</u>ack</button>&nbsp;&nbsp;&nbsp;
 			<button type="button" name="Button" accesskey="g" class="olbtn" onClick="javascript:generateClaimLetter();"><u>G</u>enerate Letter</button>&nbsp;&nbsp;&nbsp;
 			
		 </td>
   	</tr>
</table>
		<!-- E N D : Buttons -->
		<BR><BR><BR>
	</div>
<input type="hidden" name="mode" value="">
<html:hidden property="clmSeqId"/>
<html:hidden property="settlementNo"/>
<html:hidden property="clmStatus"/>
	</body>
</html:form>