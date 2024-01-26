<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript">

function onBackToCorporate()
{
	document.forms[1].mode.value="doCorporate";
   	document.forms[1].action="/InsuranceLoginAction.do";
   	document.forms[1].submit();
}

function onBackGlobalFocus()
{
	document.forms[1].mode.value="doBackCorpGlobalFocused";
   	document.forms[1].action="/InsuranceRetailAction.do?strVar=RETAIL";
   	document.forms[1].submit();
}

</script>
<head>
    <meta charset="utf-8">
    <title>Your Name Here - Welcome</title>
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
	<style type="text/css">
	.dropup, .dropdown {
    border: 2px solid #ffffff;
    border-radius: 5px;
	}
	
	.navbar .nav-pills > li > a {
  
    padding: 7px 30px;
   
}
	#categories{
  overflow:hidden;
  width:510px;
  margin:0 auto;
}
.clr:after{
  content:"";
  display:block;
  clear:both;
}
#categories li{
  position:relative;
  list-style-type:none;
  width:27.85714285714286%; /* = (100-2.5) / 3.5 */
  padding-bottom: 32.16760145166612%; /* =  width /0.866 */
  float:left;
  overflow:hidden;
  visibility:hidden;
 
  -webkit-transform: rotate(-60deg) skewY(30deg);
  -ms-transform: rotate(-60deg) skewY(30deg);
  transform: rotate(-60deg) skewY(30deg);
}
#categories li:nth-child(3n+2){
  margin:0 1%;
}
#categories li:nth-child(6n+4){
  margin-left:0.5%;
}
#categories li:nth-child(6n+4), #categories li:nth-child(6n+5), #categories li:nth-child(6n+6) {
	margin-top: -6.9285714285%;
  margin-bottom: -6.9285714285%;
  
  -webkit-transform: translateX(50%) rotate(-60deg) skewY(30deg);
  -ms-transform: translateX(50%) rotate(-60deg) skewY(30deg);
  transform: translateX(50%) rotate(-60deg) skewY(30deg);
}
#categories li:nth-child(6n+4):last-child, #categories li:nth-child(6n+5):last-child, #categories li:nth-child(6n+6):last-child{
  margin-bottom:0%;
}
#categories li *{
  position:absolute;
  visibility:visible;
}
#categories li > div{
  width:100%;
  height:100%;
  text-align:center;
  color:#000;
  overflow:hidden;
  
  -webkit-transform: skewY(-30deg) rotate(60deg);
  -ms-transform: skewY(-30deg) rotate(60deg);
  transform: skewY(-30deg) rotate(60deg);
  
	-webkit-backface-visibility:hidden;
  
}

/* HEX CONTENT */
#categories li img{
  left:-100%; right:-100%;
  width: auto; height:100%;
  margin:0 auto;   
}

#categories div h1, #categories div p{
  width:90%;
  padding:0 5%;
  //background-color:rgb(94, 142, 55); 
  //background-color: rgb(94, 142, 55);
  font-family: 'Raleway', sans-serif;
  
  -webkit-transition: top .2s ease-out, bottom .2s ease-out, .2s padding .2s ease-out;
  -ms-transition: top .2s ease-out, bottom .2s ease-out, .2s padding .2s ease-out;
  transition: top .2s ease-out, bottom .2s ease-out, .2s padding .2s ease-out;
}
#categories li h1{
  bottom:110%;
  font-style:italic;
  font-weight:normal;
  font-size:1.5em;
  padding-top:100%;
  padding-bottom:100%;
}
#categories li h1:after{
	content:'';
  display:block;
  position:absolute;
  bottom:-1px; left:45%;
  width:10%;
  text-align:center;
  z-index:1;
  border-bottom:2px solid #fff;
}
#categories li p{
	padding-top:30%;
	padding-bottom:50%;
}


/* HOVER EFFECT  */

#categories li div:hover h1 {

}

#categories li div:hover p{

}
	</style>
</head>
<html:form action="/InsuranceLoginAction.do" >
<body id="pageBody">

<div class="contentArea" id="contentArea">
                  
<div style="background-image:url('/ttk/images/Insurance/content.png');">


<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<div id="contentOuterSeparator"></div>
<h4 class="sub_heading">Retail - Summary</h4>
<div id="contentOuterSeparator"></div>
</div>



<div id="contentOuterSeparator"></div>

      <div style="width:90%;margin-left:auto;margin-right:auto;">
	

<div class="row-fluid">	
                 <div class="span12" align="center" >

<div class="span5">

<h3 style="text-align:center;"> Pre-Approval </h3>
<table class="table table-striped" >

	<tr>
	<th> Pre-Approval Report as on Date </th> <th colspan="2" align="center">Data </th>
	</tr>
	<tr> <th> &nbsp; </th> <th> No </th> <th> Amount</th>
	</tr>
      <tbody>

		<!-- as on date -->
        <tr>
          <td>Pre-Auth Received</td>
          <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.preAuthReceivedAsOnDate"/></td>
		   <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.amtPreAuthReceivedAsOnDate"/></td>
        </tr>
       
          <tr>
       <td>Pre-Auth Approved</td>
          <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.preAuthApprovedAsOnDate"/></td>
		   <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.amtPreAuthApprovedAsOnDate"/></td>
        </tr>
       
           <tr>
          <td>Pre-Auth Denied</td>
          <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.preAuthDeniedAsOnDate"/></td>
		   <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.amtPreAuthDeniedAsOnDate"/></td>
        </tr>
       
          <tr>
           <td >Pre-Auth Shortfall</td>
          <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.preAuthShortfallAsOnDate"/></td>
		   <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.amtPreAuthShortfallAsOnDate"/></td>
        </tr>
        
        <!-- month wise -->
        <tr>
        <td colspan="3"> &nbsp; </td>
        </tr>
        <tr>
	<th> Pre-Approval Report for the Month </th> <th colspan="2" align="center"> Data</th>
	</tr>
	<tr> <th> &nbsp; </th> <th> No </th> <th> Amount</th>
	</tr>
        <tr>
          <td>Pre-Auth Received</td>
          <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.preAuthReceivedCurMonth"/></td>
		   <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.amtPreAuthReceivedCurMonth"/></td>
        </tr>
       
          <tr>
       <td>Pre-Auth Approved</td>
          <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.preAuthApprovedCurMonth"/></td>
		   <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.amtPreAuthApprovedCurMonth"/></td>
        </tr>
       
           <tr>
          <td>Pre-Auth Denied</td>
          <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.preAuthDeniedCurMonth"/></td>
		   <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.amtPreAuthDeniedCurMonth"/></td>
        </tr>
       
          <tr>
           <td>Pre-Auth Shortfall</td>
          <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.preAuthShortfallCurMonth"/></td>
		   <td><bean:write name="frmInsCorporateFocused" property="authorizationVO.amtPreAuthShortfallCurMonth"/></td>
        </tr>
        
      </tbody>
    </table></div>
<div class="span1"> &nbsp; </div>
<div class="span5">
<h3 style="text-align:center;"> Enrolment </h3>
<ttk:IsuranceLoginEnrollmentDetails/>
<%TTKCommon.getActiveSubLink(request);
%>
</div></div></div></div><!-- </div></div></div></div></div> -->

<div id="contentOuterSeparator"></div>


<!--  -->
<div style="width:90%;">
<div class="row-fluid">	
<div class="span12" align="center" >
	<div class="span5">
		<h3 style="text-align:center;"> Claims</h3>
		<ttk:IsuranceLoginClaimDetails/>
	</div>
<div class="span1"> &nbsp; </div>
	<div class="span5">
		<h3 style="text-align:center;"> Network</h3>
		<ttk:IsuranceLoginNetworkDetails/>
	</div>
	<%-- <div class="span7">
		<h3 style="text-align:center;"> Call Center </h3>
		<ttk:IsuranceLoginCallCenterDetails/>
	</div> --%>
</div></div></div>

<!--  -->
<%-- <div id="contentOuterSeparator"></div>
<!-- Nework Data -->
<div style="width:90%;">
<div class="row-fluid">	
<div class="span12" align="center" >
	<div class="span5">
		<h3 style="text-align:center;"> Network</h3>
		<ttk:IsuranceLoginNetworkDetails/>
	</div>
</div></div></div> --%>



<div class="row-fluid" >
	<div style="">
	<p style="margin-bottom:0px;margin-left:12%"><img class="hexagon_small" src="ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
	<a href="#" onclick="javascript:onBackGlobalFocus()">Back </a> </p>
	<br />
	</div>
</div>
<!--  -->

<%-- <div class="row-fluid">	

<div class="span12" align="center" >
<div class="span5">
<h3> Claims</h3>
<ttk:IsuranceLoginClaimDetails/>
</div>

<div class="span7">
<h3> Call Center</h3>
<ttk:IsuranceLoginCallCenterDetails/>
</div>

</div>

</div> --%>
	
<script src="/ttk/scripts/bootstrap/css/jquery.min.js" type="text/javascript"></script> 
<script src="/ttk/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<input type="hidden" name="mode" value="">
</body>
</html:form>
