<!DOCTYPE HTML>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/onlineforms/insuranceLogin/insDashBoardNew.js"></script>
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

<html:form action="/InsuranceDashBoardAction.do" >
<body id="pageBody">

<div class="contentArea" id="contentArea">

                  
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">

<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >
<%-- <%= TTKCommon.getNavigation(request)   %> --%>

                <div class="span8">
<!-- <div id="navigateBar">Home &gt; Dashboard</div> -->
                   			<div id="contentOuterSeparator"></div>
								<h5 class="sub_heading">Dashboard</h5>
			                <div id="contentOuterSeparator"></div>
	
				<ul id="categories" class="clr">

  <li class="pusher"></li>
	<li>
      <div>
        <img src="/ttk/images/Insurance/big_hexagon.png" alt="">
       
       <p style="color:#000;" id="underLine"> Portfolio size </p> <br>
        <p id="boldwithoutUnderLine"> Lives Covered </p> <br>
       <p style="color:#000;" id="p1"> PI : <bean:write name="frmInsDashBoard" property="livesCoverdPI"/><br>
        Non PI : <bean:write name="frmInsDashBoard" property="livesCoverdNonPI"/></p><br>
        <p id="boldwithoutUnderLine1">Premium Underwritten </p><br><br>
        <p style="color:#000;" id="p2">PI :<bean:write name="frmInsDashBoard" property="premUnderPI"/> <br>
        Non PI : <bean:write name="frmInsDashBoard" property="premUnderNonPI"/>
        </p>
    </div>
  </li>
    <li>
      <div>
       <img src="/ttk/images/Insurance/big_hexagon.png" alt="">
         <br>
        <p id="claimTrans"> Claims Transaction </p><br><br>
        <p style="color:#000;">Claims Received : <bean:write name="frmInsDashBoard" property="claimsReceived"/><br>
        Claims Paid : <bean:write name="frmInsDashBoard" property="claimsPaid"/></p>
    </div>
  </li>
  <li>
      <div>
        <img src="/ttk/images/Insurance/big_hexagon.png" alt="">
        <br>
         <p id="claimOutgo">Claims Out-Go  </p><br><br>
        <p style="color:#000;">Amount Claimed : <bean:write name="frmInsDashBoard" property="amountClaimed"/><br>
        Amount Paid : <bean:write name="frmInsDashBoard" property="amountPaid"/><br></p>
    </div>
  </li>
  <li>
      <div>
        <img src="/ttk/images/Insurance/big_hexagon.png" alt="">
        <br>
       <p id="cashlessTrans"> Cashless Transactions </p><br><br>
       <p style="color:#000;"> Received : <bean:write name="frmInsDashBoard" property="cashlessReceived"/><br>
        Approved : <bean:write name="frmInsDashBoard" property="cashlessApproved"/><br></p>
    </div>
  </li><li>
      <div>
        <img src="/ttk/images/Insurance/big_hexagon.png" alt="">
        <br>
        <p id="network">Network Coverage</p><br><br>
        <%	String topProvs	=	(String)request.getAttribute("topProvs");
        	if(!"".equals(topProvs) && topProvs!=null)
 		       topProvs	=	topProvs.replace("#13;", "&#13;");
        	else
        		topProvs	=	"";
        %>
        <p id="networkOtherData">Total No. of Providers : <bean:write name="frmInsDashBoard" property="ttlNoOfProviders"/><br></p>
     		&nbsp;&nbsp;&nbsp;&nbsp;<p id="markClass" title="<%= topProvs %>">Top 5 Providers</p>
	 <br>

    </div>
  </li>
  <li class="pusher"></li>
  <li>
      <div>
        <img src="/ttk/images/Insurance/big_hexagon.png" alt="">
        <br><br>
        <p id="network">Claims Ratio </p><br>
		<!-- <p align="left" id="network"><font size="2">Claimed Amt:Policy Prm</font></p> -->
		<br>
        <p style="color:#000;"><bean:write name="frmInsDashBoard" property="claimsPortfolioRatio"/></p>
        
    </div>
  </li>
  
  <li>
      <div>
	  <img src="/ttk/images/Insurance/big_hexagon.png" alt=""/><br>
	  <p id="network">TAT</p><br>
        <p style="color:#000;"><br>
        Cards : <bean:write name="frmInsDashBoard" property="cardsTAT"/><br>
        Cashless : <bean:write name="frmInsDashBoard" property="cashlessTAT"/><br>
        Claims : <bean:write name="frmInsDashBoard" property="claimsTAT"/><br>
        </p>
        
    </div>
  </li>
  <li class="pusher"></li>
</ul>
                </div>
				 <div class="span4">
<div id="contentOuterSeparator"></div>


<div style="margin-top:150px">
<p style="margin-bottom:0px;margin-left:12%"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp; 
<a href="#" onclick="javascript:onCorporate()" > Corporate </a></p>
<p style="margin-bottom: 0px;margin-left:1%;margin-top: -4%;"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onRetail()" > Retail </a> </p>
</div>

		
        <div id="footerInnerSeparator"></div>
<div id="contentOuterSeparator"></div>

     
<div style="margin-top:180px">
<p style="margin-bottom:0px;margin-left:12%"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onMyProfile()" > My Profile </a> </p>
<p style="margin-bottom: 0px;margin-left:1%;margin-top: -4%;"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onReports()" > Reports</a></p>
<p style="margin-bottom:0px;margin-left:12%;margin-top: -4%;"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onLogDetails()" >Log Details </a> </p>
<p style="margin-bottom: 0px;margin-left:1%;margin-top: -4%;"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onChangePwd()" > Change Password </a></p>
</div>
<p> <font size="10"></font> </p>

                </div>

            </div>
			<!--End Main Content Area here-->

    </div>

</div>
</div>
</div>
<script src="/ttk/scripts/bootstrap/css/jquery.min.js" type="text/javascript"></script> 
<script src="/ttk/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<input type="hidden" name="mode" value="">
<input type="hidden" name="tab" value="">
<input type="hidden" name="leftlink" value="">
<input type="hidden" name="sublink" value="">
</body>
</html:form>
