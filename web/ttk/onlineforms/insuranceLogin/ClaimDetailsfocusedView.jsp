<!DOCTYPE HTML>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script type="text/javascript">
function onBackClaimDetails()
{
	document.forms[1].reset();
    document.forms[1].mode.value="doBackClmAndAuth";
    document.forms[1].action = "/EditInsuranceCorporateAction.do?modeType=CLM";
    document.forms[1].submit();
}
function onRetailBackClaimDetails()
{
	document.forms[1].reset();
    document.forms[1].mode.value="doRetailBackClmAndAuth";
    document.forms[1].action = "/InsuranceRetailAction.do?modeType=CLM";
    document.forms[1].submit();
}



</script>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">      
	<meta name="author" content="Html5TemplatesDreamweaver.com">
        <!-- Remove this Robots Meta Tag, to allow indexing of site -->
	<META NAME="ROBOTS" CONTENT="NOINDEX, NOFOLLOW"> 
    
    <link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />

</head>
<html:form action="/InsuranceRetailAction.do" >
<div class="contentArea" id="contentArea">
<body id="pageBody">
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">


<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<!-- <div id="navigateBar">Home > Corporate > Detailed > Claim Details</div> -->
	<div id="contentOuterSeparator"></div>
	<h4 class="sub_heading">Claim Details</h4>
	<div id="contentOuterSeparator"></div>
</div>           

       </div>
        <div class="row-fluid" >
        <div style="width: 70%;">
<div class="span8" style="margin:0% 3%">


<table class="table table-striped" style="align:center">
 <tbody>
        <tr>
          <th>Claim Number</th>
          <td><bean:write name="frmClaimsView" property="claimNumber"/></td>
        </tr>
        <tr>
          <th>Claim Received Date</th>
          <td><bean:write name="frmClaimsView" property="clmReceivedDate"/></td>
        </tr>
        <tr>
          <th>Provider name</th>
          <td><bean:write name="frmClaimsView" property="hospName"/></td>
        </tr>
        <tr>
          <th>Encounter Invoice No</th>
          <td><bean:write name="frmClaimsView" property="encounterProviderInvoiceNo"/></td>
        </tr>
        <tr>
          <th>Encounter Date from</th>
          <td><bean:write name="frmClaimsView" property="encounterDateFrom"/></td>
        </tr>
        <tr>
          <th>Encounter Date to</th>
          <td><bean:write name="frmClaimsView" property="encounterDateTo"/></td>
        </tr>
        <tr>
          <th>Claim Settlement ID</th>
          <td><bean:write name="frmClaimsView" property="claimPaymentId"/></td>
        </tr>
        <tr>
          <th>Claim Settlement Date</th>
          <td><bean:write name="frmClaimsView" property="claimPaymentDate"/></td>
        </tr>
        <tr>
          <th>Diagnosis Code</th>
          <td><bean:write name="frmClaimsView" property="diagnosysCode"/></td>
        </tr>
        <tr>
          <th>Short Desc</th>
          <td><bean:write name="frmClaimsView" property="shortDesc"/></td>
        </tr>
        <tr>
          <th>Total Discount Gross Amount</th>
          <td><bean:write name="frmClaimsView" property="totDiscGrossAmount"/></td>
        </tr>
        <tr>
          <th>Deductible Amount</th>
          <td><bean:write name="frmClaimsView" property="deductibleAmount"/></td>
        </tr>
        <tr>
          <th>CoPay Amount</th>
          <td><bean:write name="frmClaimsView" property="copayAmount"/></td>
        </tr>
        <tr>
          <th>Benefit Amount</th>
          <td><bean:write name="frmClaimsView" property="benefitAmount"/></td>
        </tr>
        <tr>
          <th>Claim Reciepent</th>
          <td><bean:write name="frmClaimsView" property="claimRecipient"/></td>
        </tr>
        <tr>
          <th>Encounter type</th>
          <td><bean:write name="frmClaimsView" property="description"/></td>
        </tr>
        <tr>
          <th>Auth Number</th>
          <td><bean:write name="frmClaimsView" property="authNumber"/></td>
        </tr>
        <tr>
          <th>Claim Status</th>
          <td><bean:write name="frmClaimsView" property="clmStatusTypeId"/></td>
        </tr>
        <tr>
          <th>Claim Status Description</th>
          <td><bean:write name="frmClaimsView" property="claimStatusDescription"/></td>
        </tr>
        <tr>
          <th>Payee Name</th>
          <td><bean:write name="frmClaimsView" property="payeeName"/></td>
        </tr>
        <tr>
          <th>Cheque/EFT No</th>
          <td><bean:write name="frmClaimsView" property="chequeEftNumber"/></td>
        </tr>
        <tr>
          <th>Cheque/EFT Date</th>
          <td><bean:write name="frmClaimsView" property="chequeEftDate"/></td>
        </tr>
        <tr>
          <th>Cheque Dispatch Date</th>
          <td><bean:write name="frmClaimsView" property="chequeDispatchDate"/></td>
        </tr>
        <tr>
          <th>Name of Courier</th>
          <td><bean:write name="frmClaimsView" property="nameOfCourier"/></td>
        </tr>
        <tr>
          <th>Courier Consignment No.</th>
          <td><bean:write name="frmClaimsView" property="courierConsignment"/></td>
        </tr>
        
  </tbody>
</table>

</div>
</div>

<div class="span9" align="center">
<p style="margin-bottom:0px;margin-left:12%"><img class="hexagon_small" src="ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<%if("Corporate".equals(TTKCommon.getActiveLink(request))) {%>
<a href="#" onclick="javascript:onBackClaimDetails()">Back </a> 
<%}else if("Retail".equals(TTKCommon.getActiveLink(request))){ %>
<a href="#" onclick="javascript:onRetailBackClaimDetails()">Back </a> 
<%} %></p>
<br />
</div>

</div>
	<!--End Main Content Area here-->
    </div>
</div>
</div>
</body>
<script src="/ttk/scripts/bootstrap/css/jquery.min.js" type="text/javascript"></script> 
<script src="/ttk/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<input type="hidden" name="mode" value="">

</body>
</html:form>
