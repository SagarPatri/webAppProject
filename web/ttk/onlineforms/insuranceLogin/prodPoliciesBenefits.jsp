<!DOCTYPE HTML>
<%@page import="com.ttk.dto.onlineforms.insuranceLogin.PolicyDetailVO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<head>
<script type="text/javascript">
function onBackToProdSearch()
{
	document.forms[1].mode.value="doBackToProdSearch";
   	document.forms[1].action="/InsuranceProductPoliciesAction.do";
   	document.forms[1].submit();
}

function showPolicyBenefits()
{
	document.forms[1].mode.value="doShowPolicyBenefits";
   	document.forms[1].action="/InsuranceProductPoliciesAction.do";
   	document.forms[1].submit();
}
</script>
<link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">

    <link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
    <script src="/ttk/scripts/bootstrap/css/jquery.min.js" type="text/javascript"></script> 
	<script src="/ttk/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
</head>

<html:form action="/InsuranceProductPoliciesAction.do" >

<body id="pageBody">
 <div class="contentArea" id="contentArea">                 
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">


<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<div id="contentOuterSeparator"></div>
<h4 class="sub_heading">Policy Benefits</h4>
<html:errors/>
<div id="contentOuterSeparator"></div>
</div>




               
<div class="span12" align="center" style="margin-left:0%;">
<div style="width: 70%;">

<div>

<%
ArrayList alPolicyDetails	=	(ArrayList)request.getSession().getAttribute("alPolicyBenefitDetails")==null?new ArrayList():(ArrayList)request.getSession().getAttribute("alPolicyBenefitDetails");
%>
<fieldset style="width:550px;" align="center"><div id="navigateBar">${strBenefitLimits[7]} - Policy Benefits Details</div>
	<br>
	<table align="center" class="table table-striped"  border="0" cellspacing="0" cellpadding="0" style="width:500px;height:auto;">
	<tr>
		<th> ASSISTANCE_COVER_LIMIT </th>
		<td> ${strBenefitLimits[0]} </td>
	</tr>
	<tr>	
		<th> CHRONIC_LIMIT </th>
		<td> ${strBenefitLimits[1]} </td>
	</tr>
	<tr>	
		<th> DENTAL_LIMIT </th>
		<td> ${strBenefitLimits[2]} </td>
	</tr>
	<tr>	
		<th> MATERNITY_LIMIT </th>
		<td> ${strBenefitLimits[3]} </td>
	</tr>
	<tr>	
		<th> NORMAL_DELIVERY_LIMIT </th>
		<td> ${strBenefitLimits[4]} </td>
	</tr>
	<tr>	
		<th> CESERIAN_DELIVERY_LIMIT </th>
		<td> ${strBenefitLimits[5]} </td>
	</tr>
	<tr>	
		<th> OPTICAL_LIMIT </th>
		<td> ${strBenefitLimits[6]} </td>
	</tr>
   </table>
 </fieldset>


<div style="">
<p style="margin-bottom: 0px; margin-left: 1%; margin-top: -1%;"><img class="hexagon_small" src="ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onBackToProdSearch()" >Back</a> </p>
<br />
</div>

  
</div>
</div>

             </div>
       </div>
			<!--End Main Content Area here-->

</div>
</div>
</div>
</div>
<INPUT TYPE="hidden" NAME="mode" VALUE="">	
</body>
</html:form>
