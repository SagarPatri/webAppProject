<!DOCTYPE HTML>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/onlineforms/insuranceLogin/insreports.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
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
</head>

<html:form action="/OnlineInsDashBoardAction.do" >
<body id="pageBody">
<div class="contentArea" id="contentArea">
                  
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">

<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >
                <div class="span8">
                   			<div id="contentOuterSeparator"></div>
								<h5 class="sub_heading">Reports</h5>
			                <div id="contentOuterSeparator"></div>
			<!-- <ul>
              	<li> <a href="#" onclick="javascript:onClaimsAnalysis()" >Claims Analysis</a></li>
              	<li> <a href="#" onclick="javascript:onTechnicalResults()" >Technical Results</a></li>
              	<li> <a href="#" onclick="javascript:onTreatyStatistics()" >Treaty Statistics for Medical</a></li>
              	<li> <a href="#" onclick="javascript:onBurning()" >Burning Cost as at Report</a></li>
              	<li> <a href="#" onclick="javascript:onHIPA()" >HIPA Sample</a></li>
              	<li> <a href="#" onclick="javascript:onDoctorDailyreport()" >Doctor's Daily report</a></li>
            </ul> -->
                </div>
		<ttk:ReportsList/>
		
		<!-- Reports Lists are displaying from ReportsList.xml under module DashBoard -->
		<div class="span4">
			<div id="contentOuterSeparator"></div>
        </div>
            </div>
			<!--End Main Content Area here-->
  	<div class="row-fluid" align="center">
		<div style="" align="center">
		<p style="margin-bottom:0px;margin-left:12%"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp; <a href="#" onclick="javascript:onBack()" > Back </a></p>
		<br>
		</div>
     </div>
   </div>
</div>
</div>
</div>
</body>
<script src="/ttk/scripts/bootstrap/css/jquery.min.js" type="text/javascript"></script> 
<script src="/ttk/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<input type="hidden" name="mode" value="">
</html:form>
