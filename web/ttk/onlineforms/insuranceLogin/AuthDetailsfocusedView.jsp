<!DOCTYPE HTML>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/hospitalgeneral.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript">
function onBackClaimDetails()
{
	document.forms[1].reset();
    document.forms[1].mode.value="doBackClmAndAuth";
    document.forms[1].action = "/EditInsuranceCorporateAction.do?modeType=PAT";
    document.forms[1].submit();
}

</script>
<head>
    
    <link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />

</head>
<html:form action="/ViewClaimDetailsAction.do" >
<div class="contentArea" id="contentArea">
<body id="pageBody">
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">


<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<!-- <div id="navigateBar">Home > Corporate > Detailed > Authorization Details</div> -->
	<div id="contentOuterSeparator"></div>
	<h4 class="sub_heading">PreAuth Details</h4>
	<div id="contentOuterSeparator"></div>
</div>           

       </div>
        <div class="row-fluid" >
        <div style="width: 70%;">
<div class="span8" style="margin:0% 30%">

<table class="table table-striped" style="align:center">
 <tbody>
        <tr>
          <th>Pre-Auth Number</th>
          <td><bean:write name="frmAuthView" property="preAuthNumber"/></td>
        </tr>
        <tr>
          <th>Provider name</th>
          <td><bean:write name="frmAuthView" property="hospName"/></td>
        </tr>
        <tr>
          <th>Encounter Date from</th>
          <td><bean:write name="frmAuthView" property="encounterDateFrom"/></td>
        </tr>
        <tr>
          <th>Encounter Date to</th>
          <td><bean:write name="frmAuthView" property="encounterDateTo"/></td>
        </tr>
        <tr>
          <th>Diagnosis Code</th>
          <td><bean:write name="frmAuthView" property="diagnosysCode"/></td>
        </tr>
        <tr>
          <th>Short Desc</th>
          <td><bean:write name="frmAuthView" property="shortDesc"/></td>
        </tr>
        <tr>
          <th>Total Approved Amount</th>
          <td><bean:write name="frmAuthView" property="totApprovedAmount"/></td>
        </tr>
        <tr>
          <th>Deductible Amount</th>
          <td><bean:write name="frmAuthView" property="deductibleAmount"/></td>
        </tr>
        <tr>
          <th>Copay Amount</th>
          <td><bean:write name="frmAuthView" property="copayAmount"/></td>
        </tr>
        <tr>
          <th>Benefit Amount</th>
          <td><bean:write name="frmAuthView" property="benefitAmount"/></td>
        </tr>
        <tr>
          <th>Pre-Auth Benifit</th>
          <td><bean:write name="frmAuthView" property="activityBenifit"/></td>
        </tr>
        <tr>
          <th>Encounter Start Type</th>
          <td><bean:write name="frmAuthView" property="encounterType"/></td>
        </tr>
        <tr>
          <th>Pre-Auth Status</th>
          <td><bean:write name="frmAuthView" property="activityStatus"/></td>
        </tr>
        <tr>
          <th>Pre-Auth Status Desription</th>
          <td><bean:write name="frmAuthView" property="activityStatusDescription"/></td>
        </tr>
        <tr>
          <th>Claim Recieved</th>
          <td><bean:write name="frmAuthView" property="claimReceived"/></td>
  </tbody>
</table>

</div>
</div>
</div>

<div class="span9" align="center">
<p style="margin-bottom:0px;margin-left:12%"><img class="hexagon_small" src="ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onBackClaimDetails()">Back </a> </p>
<br />
</div>


			<!--End Main Content Area here-->

    </div>

</div>
<script src="/ttk/scripts/bootstrap/css/jquery.min.js" type="text/javascript"></script> 
<script src="/ttk/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<input type="hidden" name="mode" value="">

</body>
</html:form>
