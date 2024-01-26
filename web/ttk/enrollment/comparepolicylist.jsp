<% 
/**
 * @ (#) comparepolicylist.jsp 18th Feb 2006
 * Project      : TTK HealthCare Services
 * File         : comparepolicylist.jsp
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : 18th Feb 2006  
 *
 * @author       :Krupa J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
%>


<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>


<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/enrollment/otherpolicylist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>

<!-- S T A R T : Main Container Table --> 
<table width="100%" id="mainContainerTable" class="mainContainerTable" border="0" cellspacing="0" cellpadding="0"> 
  <tr> 
    <td width="100%" align="center" valign="top">
    
	
	<!-- S T A R T : Content/Form Area -->	
	<html:form action="/ComparePolicyAction.do"> 
	
	<!-- S T A R T : Page Title -->
	<logic:match name="frmOtherPolicy" property="switchType" value="ENM">
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	<logic:match name="frmOtherPolicy" property="switchType" value="END">
		<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
  <tr>
    <td width="100%">Policy Comparison Chart - [<bean:write name="frmOtherPolicy" property="name" />] </td>     
    </tr>
</table>
	<!-- E N D : Page Title --> 
	
    <!-- S T A R T : Form Fields -->
<div class="mainGridContainer">
	<div class="cnt-pol-det">
		<table border="0" cellspacing="0" cellpadding="3">
        	<tr><td align="left" class="gridHeader" width="25%" style="height:26px; ">Policy Details</td></tr>        	
	        <tr class="gridEvenRow"><td height="20" align="left">Name</td></tr>
	        <tr class="gridOddRow"><td height="20" align="left">Age</td></tr>
	        <tr class="gridEvenRow"><td height="20" align="left">Date of Birth</td></tr>
	        <tr class="gridOddRow"><td height="20" align="left">Sex</td></tr>
	        <tr class="gridEvenRow"><td height="20" align="left">Occupation</td></tr>
	        <tr class="gridOddRow"><td height="20" align="left">Date of Inception</td></tr>
	        <tr class="gridEvenRow"><td height="20" align="left">Address 1</td></tr>
	        <tr class="gridOddRow"><td height="20" align="left">Address 2</td></tr>
	        <tr class="gridEvenRow"><td height="20" align="left">Address 3</td></tr>
	        <tr class="gridOddRow"><td height="20" align="left">City</td></tr>
	        <tr class="gridEvenRow"><td height="20" align="left">State</td></tr>
	        <tr class="gridOddRow"><td height="20" align="left">Pincode</td></tr>
	        <tr class="gridEvenRow"><td height="20" align="left">Country</td></tr>
		</table>
	</div>	
<ttk:ComparePolicy />
<!-- E N D : Form Fields -->    
	<!-- E N D : Content/Form Area -->
	</td> 
</table> 
<!-- E N D : Main Container Table --> 


</html:form>

